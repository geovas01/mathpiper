/*
 * Created on Apr 15, 2007
 */
package org.mathpiper.builtin.library.statdistlib;;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests extends TestSuite {

  public static Test suite() {
    TestSuite suite = new TestSuite("DistLib Tests");
    //$JUnit-BEGIN$
    suite.addTestSuite(TestUniformRngs.class);
    suite.addTestSuite(TestWilcox.class);
    suite.addTestSuite(TestTukey.class);
    suite.addTestSuite(TestNormal.class);
    suite.addTestSuite(TestMisc.class);
    //$JUnit-END$
    return suite;
  }
}
