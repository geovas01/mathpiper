package org.mathrider.piper;


import java.io.*;

public class StdFileOutput extends LispOutput //tk: made this class public.
{
    public StdFileOutput(OutputStream aFile)
    {
      iFile = aFile;
    }
    public void PutChar(char aChar) throws Exception
    {
      iFile.write(aChar);
    }
    OutputStream iFile;
};
