package org.mathpiper.studentschedule.gwt.server;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.mathpiper.studentschedule.gwt.client.StudentScheduleService;
import org.mathpiper.studentschedule.gwt.shared.ArgumentException;
import org.mathpiper.studentschedule.gwt.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import clojure.lang.RT;
import clojure.lang.Var;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class StudentScheduleServiceImpl extends RemoteServiceServlet implements
	StudentScheduleService {

    private final static Logger LOGGER = Logger
	    .getLogger(StudentScheduleServiceImpl.class.getName());

    private Var findSchedules;

    private Var courseList;

    private Var getSections;

    static private FileHandler fileTxt;

    static private SimpleFormatter formatterTxt;

    public StudentScheduleServiceImpl() {
	super();

	try {

	    // RT.loadResourceScript("org/mathpiper/studentschedule/gwt-test.clj");
	    // report = RT.var("org.mathpiper.studentschedule.gwt-test",
	    // "print-report");

	    RT.loadResourceScript("org/mathpiper/studentschedule/completescheduleengine/student_schedules_api.clj");
	    findSchedules = RT
		    .var("org.mathpiper.studentschedule.completescheduleengine.student_schedules_api",
			    "find-schedules");

	    courseList = RT
		    .var("org.mathpiper.studentschedule.completescheduleengine.student_schedules_api",
			    "course-list");

	    getSections = RT
		    .var("org.mathpiper.studentschedule.completescheduleengine.student_schedules_api",
			    "get-sections");
	    // String courseListString = (String) courseList.invoke();

	    // System.out.println(courseListString);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    public String findSchedules(String input) throws ArgumentException {
	// Verify that the input is valid.
	/*
	 * if (!FieldVerifier.isValidName(input)) { // If the input is not
	 * valid, throw an IllegalArgumentException back to // the client. throw
	 * new IllegalArgumentException(
	 * "Name must be at least 4 characters long"); }
	 */

	LOGGER.info("Host:" + getThreadLocalRequest().getRemoteHost() + ", Address:"
			+ getThreadLocalRequest().getRemoteAddr() + ", " + input);

	String result = "";

	try {
	    result = (String) findSchedules.invoke(input);
	} catch (Throwable e) {
	    if (e instanceof ArgumentException) {
		LOGGER.info("Host:" + getThreadLocalRequest().getRemoteHost() + ", Address:"
			+ getThreadLocalRequest().getRemoteAddr() + ", "
			+ input + e.getMessage());
		throw ((ArgumentException)e);
	    } else {
		
		StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                e.printStackTrace(printWriter);
                
		LOGGER.severe("Host:" + getThreadLocalRequest().getRemoteHost() + ", Address:"
			+ getThreadLocalRequest().getRemoteAddr() + ", " + ", "
			+ input + stringWriter.toString());

		throw new ArgumentException(
			"Error on the server. Check the server log for the error message.");
	    }
	}

	return result;
    }// end method.

    public String courseList() {
	return (String) courseList.invoke();
    }

    public String getSections(String name) throws IllegalArgumentException {
	return (String) getSections.invoke(name);
    }

    /**
     * Escape an html string. Escaping data received from the client helps to
     * prevent cross-site script vulnerabilities.
     * 
     * @param html
     *            the html string to escape
     * @return the escaped string
     */
    private String escapeHtml(String html) {
	if (html == null) {
	    return null;
	}
	return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
		.replaceAll(">", "&gt;");
    }

    public void init(ServletConfig config) throws ServletException {
	//ServletContext sc = config.getServletContext();

	//String webAppPath = sc.getRealPath("/");

	    // Create Logger.
	    Logger logger = Logger.getLogger("");
	    logger.setLevel(Level.INFO);
	    Handler consoleHandler = new ConsoleHandler();
	    logger.addHandler(consoleHandler); 
	    
	    
	    /*fileTxt = new FileHandler(webAppPath + "WEB-INF" + File.separator
		    + "logs" + File.separator + "log.txt");

	    // Create text Formatter.
	    formatterTxt = new SimpleFormatter();
	    fileTxt.setFormatter(formatterTxt);
	    logger.addHandler(fileTxt);*/

	    super.init(config);
	

    }
}
