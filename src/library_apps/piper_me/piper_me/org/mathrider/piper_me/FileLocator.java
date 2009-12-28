package org.mathpiper.ide.piper_me;

import java.io.InputStream;

public interface FileLocator {
  InputStream getStream(String name) throws Exception;
}
