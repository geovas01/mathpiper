package org.mathrider;

public interface ResponseListener
{
    void response(String response);
    void response(java.util.HashMap response);
    boolean remove();
}// end interface.
