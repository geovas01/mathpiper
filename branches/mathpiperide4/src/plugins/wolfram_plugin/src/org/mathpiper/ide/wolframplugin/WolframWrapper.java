//Copyright (C) 2008 Ted Kosan.
/* {{{ License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *///}}}

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:

package org.mathpiper.ide.wolframplugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.mathpiperide.ResponseListener;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.ConnectionInfo;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;


public class WolframWrapper implements Runnable {

    private static WolframWrapper wolframInstance = null;

    private StringBuffer responseBuffer;
    private Pattern inputPromptPattern;
    private InputStream inputStream;
    private OutputStream outputStream;
    private String response;
    private String startMessage;
    private String fileSearchWolframAppendResponse;
    private String fileSearchLispAppendResponse;
    private ArrayList<ResponseListener> responseListeners;
    private boolean keepRunning;
    private String prompt;
    private ArrayList<ResponseListener> removeListeners;
    private Thread thread;

    private String hostname = "";
    private String username = "";
    private String password = "";
    
    Connection conn;
    
    Session sess;
    
    private WolframWrapper() throws Throwable {
    }

    /** Creates a new instance of WolframWrapper */
    protected WolframWrapper(String hostname, String username, String password) throws Throwable {
    	    
    	this.hostname = hostname;
    	this.username = username;
    	this.password = password;

	responseListeners = new ArrayList<ResponseListener>();
	removeListeners = new ArrayList<ResponseListener>();
	ArrayList command = new ArrayList();
	// command.add("C:\\Program Files\\Wolfram-5.15.0\\bin\\wolfram.bat");
	// command.add("/usr/bin/wolfram");
	// command.add(jEdit.getProperty("wolfram.path"));

	conn = new Connection(hostname);

	conn.connect();

	boolean isAuthenticated = conn.authenticateWithPassword(username, password);

	if (isAuthenticated == false)
	    throw new IOException("Authentication failed.");

	sess = conn.openSession();

	sess.execCommand("wolfram");

	inputStream = new StreamGobbler(sess.getStdout());

	outputStream = sess.getStdin();

	responseBuffer = new StringBuffer();
	inputPromptPattern = Pattern.compile("\\nIn\\[[0-9]+\\]:=");
	startMessage = getResponse();

	thread = new Thread(this, "wolframpi");
	thread.start();

    }// end constructor.

    public String getStartMessage() {
	return startMessage;
    }// end method.

    public String getPrompt() {
	return prompt;
    }// end method.

    public static WolframWrapper getInstance(String hostname, String username, String password) throws Throwable {
	if (wolframInstance == null) {
	    wolframInstance = new WolframWrapper(hostname, username, password);
	}
	return wolframInstance;
    }// end method.
    
    public static WolframWrapper getInstance() throws Throwable {
	if (wolframInstance == null) {
	    throw new Exception("WolframWrapper has not been initialized yet.");
	}
	return wolframInstance;
    }// end method.

    public synchronized void send(String send) throws Throwable {
	outputStream.write(send.getBytes());
	outputStream.flush();
    }// end send.

    public void run() {
	keepRunning = true;

	String response;

	while (keepRunning == true) {
	    try {
		response = getResponse();
		notifyListeners(response);
	    } catch (IOException ioe) {
		notifyListeners(ioe.toString());
	    } catch (Throwable e) {
		e.printStackTrace();
	    }
	}// end while.
	
	sess.close();
	
	conn.close();

    }// end method.

    public void stop() {
	keepRunning = false;
	thread.interrupt();
    }// end method.

    protected String getResponse() throws Throwable {
	boolean keepChecking = true;

	mainLoop: while (keepChecking) {
	    int serialAvailable = inputStream.available();
	    if (serialAvailable == 0) {
		try {
		    Thread.sleep(100);
		} catch (InterruptedException ie) {
		    System.out.println("Wolfram session interrupted.");
		    keepChecking = false;
		}
		continue mainLoop;
	    }// end while

	    byte[] bytes = new byte[serialAvailable];

	    inputStream.read(bytes, 0, serialAvailable);
	    responseBuffer.append(new String(bytes));
	    response = responseBuffer.toString();
	    // System.out.println("SSSSS " + response);
	    Matcher matcher = inputPromptPattern.matcher(response);
	    if (matcher.find()) {
		// System.out.println("PPPPPP found end");
		responseBuffer.delete(0, responseBuffer.length());
		int promptIndex = response.lastIndexOf("In[");

		prompt = response.substring(promptIndex, response.length());
		response = response.substring(0, promptIndex);
		keepChecking = false;

	    }// end if.

	}// end while.

	return response;

    }// end method

    public void addResponseListener(ResponseListener listener) {
	responseListeners.add(listener);
    }// end method.

    public void removeResponseListener(ResponseListener listener) {
	responseListeners.remove(listener);
    }// end method.

    protected void notifyListeners(String response) {
	// notify listeners.
	for (ResponseListener listener : responseListeners) {
	    listener.response(response);

	    if (listener.remove()) {
		removeListeners.add(listener);
	    }// end if.
	}// end for.

	// Remove certain listeners.
	for (ResponseListener listener : removeListeners) {

	    if (listener.remove()) {
		responseListeners.remove(listener);
	    }// end if.
	}// end for.

	removeListeners.clear();

    }// end method.

    public void putFile(File file) {

	Connection Conn = null;
	try {
	    Conn = new Connection(hostname);
	    ConnectionInfo info = Conn.connect();
	    Conn.authenticateWithPassword(username, password);
	    SCPClient scpClient = Conn.createSCPClient();
	    System.out.println("XXX " + file.toString());
	    scpClient.put(file.toString(), "/home/tkosan");

	    Conn.close();
	} catch (Exception e) {
	    try {
		Conn.close();
	    } catch (Exception e1) {
		e1.printStackTrace();
	    }
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {
	try {
	    WolframWrapper wl = new WolframWrapper();
	    wl.addResponseListener(new ResponseListener() {
		public void response(String response) {
		    System.out.println(response);
		}

		public void response(java.util.HashMap response) {
		}

		public boolean remove() {
		    return false;
		}
	    });

	    wl.send("3+3\n");
	    File file = new File("/home/tkosan/temp/test.m");
	    wl.putFile(file);
	    wl.send("<<test.m\n");
	    wl.send("4+3\n");
	    JOptionPane.showConfirmDialog(null,"OK to exit",
                    "Exit wait", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
	    wl.stop();
	    System.out.println("exit");
//	    System.exit(0);
	} catch (Throwable e) {
	    e.printStackTrace();
	}

    }

}// end class.

