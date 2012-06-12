package org.mathpiper.studentschedule.gwt.client;

import org.mathpiper.studentschedule.gwt.shared.ArgumentException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface StudentScheduleService extends RemoteService {
    String findSchedules(String name) throws ArgumentException;
    
    String sectionsInformation(String name) throws ArgumentException;
    
    String courseList();
    
    String getSections(String name) throws ArgumentException;
}
