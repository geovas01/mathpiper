package org.mathrider.piper_me;


import org.mathrider.piper_me.eval.FrameTests;

import junit.framework.*;
import junit.textui.*;

public class AllPiperMETests {

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(FrameTests.class);
   return suite;
  }
  
  public static void main(String[] args) {
    TestRunner.run(suite());
  }
}

