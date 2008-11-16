package org.mathrider;

public interface ResponseListener
{
    void response(String response);
    void response(Object[] response);
    boolean remove();
}// end interface.
