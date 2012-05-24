package org.mathpiper.studentschedule.gwt.server;

import java.io.IOException;

import org.mathpiper.studentschedule.gwt.client.GreetingService;
import org.mathpiper.studentschedule.gwt.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import clojure.lang.RT;
import clojure.lang.Var;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
	GreetingService {
    
    private Var report;
    
    
    public GreetingServiceImpl()
    {
	super();
	
	try {
	    RT.loadResourceScript("org/mathpiper/studentschedule/gwt-test.clj");
	    report = RT.var("org.mathpiper.studentschedule.gwt-test", "print-report");
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
    }

    public String greetServer(String input) throws IllegalArgumentException {
	// Verify that the input is valid. 
	if (!FieldVerifier.isValidName(input)) {
	    // If the input is not valid, throw an IllegalArgumentException back to
	    // the client.
	    throw new IllegalArgumentException(
		    "Name must be at least 4 characters long");
	}

	String serverInfo = getServletContext().getServerInfo();
	String userAgent = getThreadLocalRequest().getHeader("User-Agent");

	// Escape data from the client to avoid cross-site script vulnerabilities.
	input = escapeHtml(input);
	userAgent = escapeHtml(userAgent);
	
	Long result = (Long) report.invoke("Siva");

	return "Hello, " + input + "!<br><br>I am running " + serverInfo
		+ ".<br><br>It looks like you are using:<br>" + userAgent + "XXX " + result;
    }

    /**
     * Escape an html string. Escaping data received from the client helps to
     * prevent cross-site script vulnerabilities.
     * 
     * @param html the html string to escape
     * @return the escaped string
     */
    private String escapeHtml(String html) {
	if (html == null) {
	    return null;
	}
	return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
		.replaceAll(">", "&gt;");
    }
}
