package org.mathrider;

public interface ResponseListener
{
    void response(String response);
    void response(String[] response);
    boolean remove();
}// end interface.
