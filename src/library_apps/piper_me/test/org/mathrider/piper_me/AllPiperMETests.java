package org.mathrider.piper_me;


import junit.framework.*;
import junit.textui.*;

public class AllPiperMETests {

  public static Test suite() {
    TestSuite suite = new TestSuite();
   return suite;
  }
  
  public static void main(String[] args) {
    TestRunner.run(suite());
  }
}

