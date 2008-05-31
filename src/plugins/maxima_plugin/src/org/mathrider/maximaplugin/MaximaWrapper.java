package org.mathrider.maximaplugin;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mathrider.ResponseListener;

import java.io.*;


import errorlist.*;

/**
 *
 * @author tk
 */


 public class MaximaWrapper implements Runnable 
 { 

//	private static org.gjt.sp.jedit.bsh.Interpreter bshInstance;
	private static MaximaWrapper maximaInstance = null;
	
	private StringBuffer responseBuffer;
	private Pattern inputPromptPattern;
	private InputStream inputStream;
	private OutputStream outputStream;
	private String response;
	private String startMessage;
	private ArrayList<ResponseListener> responseListeners;
	private boolean keepRunning;
	private String prompt;

    /** Creates a new instance of MaximaWrapper */
    protected MaximaWrapper() throws IOException
	{
		responseListeners = new ArrayList<ResponseListener>();
		ArrayList command = new ArrayList();
		command.add("C:\\Program Files\\Maxima-5.15.0\\bin\\maxima.bat");
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		Process maximaProcess = processBuilder.start();
		inputStream = maximaProcess.getInputStream();
		outputStream = maximaProcess.getOutputStream();
		responseBuffer = new StringBuffer();
		inputPromptPattern = Pattern.compile("\\n\\(%i[0-9]+\\)");
		startMessage = getResponse();
		new Thread(this,"maxima").start();
 
    }//end constructor.
	
	public String getStartMessage()
	{
		return startMessage;
	}//end method.
	
	public String getPrompt()
	{
		return prompt;
	}//end method.
	
   public static MaximaWrapper getInstance() throws IOException
   {
      if(maximaInstance == null) {
         maximaInstance = new MaximaWrapper();
      }
      return maximaInstance;
   }//end method.
   
   
   
    public synchronized void send(String send) throws IOException
    {
        outputStream.write(send.getBytes());
        outputStream.flush();
        //new Thread( max ).start();
        //return getResponse();
    }//end send.
	
	
	public void run()
	{
		keepRunning = true;
		
		String response;
		
		while(keepRunning == true)
		{
			try
			{
				response = getResponse();
				notifyListeners(response);
			}catch(IOException ioe)
			{
				notifyListeners(ioe.toString());
			}
		}//end while.
		
	}//end method.
	
	public void stop()
	{
		keepRunning = false;
	}//end method.
   
   
	protected String getResponse() throws IOException
    {
	    boolean keepChecking = true;
		
		mainLoop: while(keepChecking)
        {
             int serialAvailable = inputStream.available();
             if (serialAvailable == 0)
             {
				 try
				 {
					 Thread.sleep(100);
				 }
				 catch(InterruptedException ie)
				 {
					 System.out.println("Maxima session interrupted.");
				 }
                continue mainLoop;
             }//end while

               byte[] bytes = new byte[serialAvailable];
                
				inputStream.read( bytes, 0, serialAvailable );
               responseBuffer.append(new String(bytes));
               response = responseBuffer.toString();
               //System.out.println("SSSSS " + str);
               Matcher matcher = inputPromptPattern.matcher(response);
               if(matcher.find())
               {
                  //System.out.println("PPPPPP found end");
                  responseBuffer.delete(0,responseBuffer.length());
				   int promptIndex = response.lastIndexOf("(%");
				   prompt = response.substring(promptIndex,response.length());
				  	response = response.substring(0,promptIndex);
                   keepChecking = false;
                    
               }//end if.

                //System.out.println("NNNNNNN " + response);

				//self.terminalOutputStream.write( bytes )
				
				//event = utility.DataEvent( self, bytes, self.packetMode )
				
				//self.notifyListeners( event )

        }//end while.
		
		return response;
        
    }//end method
	
	public void addResponseListener(ResponseListener listener)
	{
		responseListeners.add(listener);
	}//end method.
	
	protected void notifyListeners(String response)
	{
		//java.util.Iterator listeners = responseListeners.iterator();
		for(ResponseListener listener : responseListeners)
		{
			listener.response(response);
		}//end for.
		
	}//end method.



    
}//end class.

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
