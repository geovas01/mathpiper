package org.mathrider.piper_b.j2me;


import java.io.*;

class StdFileOutput extends LispOutput
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
