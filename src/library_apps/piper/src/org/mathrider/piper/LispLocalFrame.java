package org.mathrider.piper;


// Local lisp stack, unwindable by the exception handler
class LispLocalFrame
{
  public LispLocalFrame(LispEnvironment aEnvironment, boolean aFenced)
  {
    iEnvironment = aEnvironment;
    iEnvironment.PushLocalFrame(aFenced);
  }
  public void Delete() throws Exception
  {
    iEnvironment.PopLocalFrame();
  }

  LispEnvironment iEnvironment;
}
