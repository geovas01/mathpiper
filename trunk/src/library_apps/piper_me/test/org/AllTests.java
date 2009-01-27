package org;

import org.eninom.collection.HashMapTest;

import junit.framework.*;
import junit.textui.*;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(HashMapTest.class);
    //... 
   return suite;
  }
  
  public static void main(String[] args) {
    TestRunner.run(suite());
  }
}
