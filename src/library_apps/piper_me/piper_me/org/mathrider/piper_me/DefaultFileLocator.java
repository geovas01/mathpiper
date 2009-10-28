package org.mathrider.piper_me;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class DefaultFileLocator implements org.mathrider.piper_me.FileLocator, org.mathrider.piper_me.OutputFileLocator  
{
  public InputStream getStream(String name) {
    InputStream is = getClass().getResourceAsStream(name);
    if (is == null)
      is = getClass().getResourceAsStream("/" + name);
    if (is == null) {
      try {
        is = new BufferedInputStream(new FileInputStream(name));
      } catch (FileNotFoundException e) {
        is = null;
      }
    }
    return is;
  }

  public OutputStream getOutputStream(String name) throws Exception {
    return new FileOutputStream(name);
  }
}
