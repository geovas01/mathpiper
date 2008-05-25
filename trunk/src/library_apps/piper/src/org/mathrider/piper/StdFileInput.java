package org.mathrider.piper;


import java.io.*;


class StdFileInput extends StringInput
{
  public StdFileInput(String aFileName, InputStatus aStatus) throws Exception
  {
    super(new StringBuffer(),aStatus);
    //System.out.println("YYYYYY " + aFileName);//Note:tk: remove.
    FileInputStream stream = new FileInputStream(aFileName);
    int c;
    while (true)
    {
      c = stream.read();
      if (c == -1)
        break;
      iString.append((char)c);
    }
  }
  public StdFileInput(InputStream aStream, InputStatus aStatus) throws Exception
  {
    super(new StringBuffer(),aStatus);
    int c;
    while (true)
    {
      c = aStream.read();
      if (c == -1)
        break;
      iString.append((char)c);
    }
  }

}
