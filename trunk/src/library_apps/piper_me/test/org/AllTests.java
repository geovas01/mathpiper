package org;

import org.eninom.collection.HashMapTest;
import org.mathrider.piper_me.AllPiperMETests;

import junit.framework.*;
import junit.textui.*;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite();
/*
 * First, we add all relevant tests for classes from <b>eninom.lib</b>.
 */
    suite.addTestSuite(HashMapTest.class);
    
/*
 * Second, we add the Piper-ME specific unit tests.
 */
    suite.addTest(AllPiperMETests.suite());
    return suite;
  }
  
  public static void main(String[] args) {
    TestRunner.run(suite());
  }
}
