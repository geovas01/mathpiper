package org.mathpiper.ide.piper_me;

import java.io.OutputStream;

public interface OutputFileLocator {
  OutputStream getOutputStream(String name) throws Exception;
}
