package org.mathpiper.studentschedule.gwt.server;

import java.io.IOException;

import org.mathpiper.studentschedule.gwt.client.StudentScheduleService;
import org.mathpiper.studentschedule.gwt.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import clojure.lang.RT;
import clojure.lang.Var;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class StudentScheduleServiceImpl extends RemoteServiceServlet implements
	StudentScheduleService {
    
    private Var findSchedules;
    
    private Var courseList;;
    
    
    public StudentScheduleServiceImpl()
    {
	super();
	
	try {
	    //RT.loadResourceScript("org/mathpiper/studentschedule/gwt-test.clj");
	    //report = RT.var("org.mathpiper.studentschedule.gwt-test", "print-report");
	    
	    RT.loadResourceScript("org/mathpiper/studentschedule/completescheduleengine/student_schedules_api.clj");
	    findSchedules = RT.var("org.mathpiper.studentschedule.completescheduleengine.student_schedules_api", "find-schedules");
	    
	    courseList = RT.var("org.mathpiper.studentschedule.completescheduleengine.student_schedules_api", "course-list");
	    //String courseListString = (String) courseList.invoke();
	    
	    
	    //System.out.println(courseListString);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
    }

    public String findSchedules(String input) throws IllegalArgumentException {
	// Verify that the input is valid. 
	/*if (!FieldVerifier.isValidName(input)) {
	    // If the input is not valid, throw an IllegalArgumentException back to
	    // the client.
	    throw new IllegalArgumentException(
		    "Name must be at least 4 characters long");
	}*/
	
	

	String userAgent = getThreadLocalRequest().getHeader("User-Agent");

	// Escape data from the client to avoid cross-site script vulnerabilities.
	input = escapeHtml(input);
	
	System.out.println("XXX " + input);
	
	userAgent = escapeHtml(userAgent);
	
	String result = (String) findSchedules.invoke(input);

	return result;
    }//end method.
    
    
    
    public String courseList() {
	return (String) courseList.invoke();
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
