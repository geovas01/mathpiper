package org.mathrider.piper_me;


import org.mathrider.piper_me.ast.ASTTests;

import junit.framework.*;
import junit.textui.*;

public class AllPiperMETests {

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(ASTTests.class);
   return suite;
  }
  
  public static void main(String[] args) {
    TestRunner.run(suite());
  }
}

