package org.mathrider.piper_me;


import java.io.*;


public class StdFileInput extends StringInput
{
  public static FileLocator locator = null;
  
  public StdFileInput(String aFileName, InputStatus aStatus) throws Exception
  {
    super(new StringBuffer(),aStatus);
    
    
    InputStream stream = locator.getStream(aFileName);
    
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
