package org.mathpiper.studentschedule.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>StudentScheduleService</code>.
 */
public interface StudentScheduleServiceAsync {
    void findSchedules(String input, AsyncCallback<String> callback)
	    throws IllegalArgumentException;
    
    void courseList(AsyncCallback<String> callback);
}
