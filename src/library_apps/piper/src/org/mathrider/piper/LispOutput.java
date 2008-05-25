package org.mathrider.piper;

/** \class LispOutput : interface an output object should adhere to.
 */
public abstract class LispOutput //tk: Made this interface public.
{
    /// Write out one character.
    public abstract void PutChar(char aChar) throws Exception;

    public void Write(String aString) throws Exception
    {
      int i;
      for (i=0;i<aString.length();i++)
      {
        PutChar(aString.charAt(i));
      }
    }
};
