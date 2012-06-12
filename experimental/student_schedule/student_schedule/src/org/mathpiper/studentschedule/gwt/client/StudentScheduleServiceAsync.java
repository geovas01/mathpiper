package org.mathpiper.studentschedule.gwt.client;

import org.mathpiper.studentschedule.gwt.shared.ArgumentException;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>StudentScheduleService</code>.
 */
public interface StudentScheduleServiceAsync {
    void findSchedules(String input, AsyncCallback<String> callback);
    
    void sectionsInformation(String input, AsyncCallback<String> callback);
    
    void courseList(AsyncCallback<String> callback);
    
    void getSections(String name, AsyncCallback<String> callback);
}
