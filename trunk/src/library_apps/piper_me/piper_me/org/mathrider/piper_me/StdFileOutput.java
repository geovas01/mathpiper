package org.mathrider.piper_me;


import java.io.*;

public class StdFileOutput extends LispOutput
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
