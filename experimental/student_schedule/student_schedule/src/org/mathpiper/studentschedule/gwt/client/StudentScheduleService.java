package org.mathpiper.studentschedule.gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface StudentScheduleService extends RemoteService {
    String findSchedules(String name) throws IllegalArgumentException;
    
    String courseList();
}
