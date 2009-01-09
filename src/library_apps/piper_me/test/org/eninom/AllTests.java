package org.eninom;

import junit.framework.*;
import junit.textui.*;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite();
    //suite.addTestSuite(MultiSetTest.class);
    //... 
   return suite;
  }
  
  public static void main(String[] args) {
    TestRunner.run(suite());
  }
}
