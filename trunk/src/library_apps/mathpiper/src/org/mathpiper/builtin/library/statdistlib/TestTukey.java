/*
 * Created on Apr 15, 2007
 */
package org.mathpiper.builtin.library.statdistlib;;

import junit.framework.TestCase;


public class TestTukey extends TestCase {

  // test values computed in Mac OS X version of R
  // where nmeans argument in R is number of treatments here, cc
  // nranges argument in R is number of groups here, rr
  public void testQuantiles() {
    assertEquals(2.385785,tukey.quantile(0.1, 3, 8, 5),1e-6);
    assertEquals(5.196567,tukey.quantile(0.7, 5, 13, 8),1e-6);
  }
}
