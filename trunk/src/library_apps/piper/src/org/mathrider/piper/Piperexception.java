package org.mathrider.piper;


public class Piperexception extends Exception //Note:tk: made this class public so that clients can use this exception.
{
  public Piperexception(String message)
  {
    super(message);
  }
}
