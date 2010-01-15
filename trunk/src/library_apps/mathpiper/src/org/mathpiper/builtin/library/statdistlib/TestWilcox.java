package org.mathpiper.builtin.library.statdistlib;;
/**
 * Test Wilcox rank-sum distribution functions.
 * 
 * This statistic is what people normally call the Mann-Whitney statistic, Ux.
 * It is related to the rank-sum statistic by Rx = Ux + 1/2 m (m+1), where m
 * is the number of X. It is related to Uy by Ux + Uy = m n, where n is
 * the number of Y.
 *
 * Test values from Lindgren, Table VIII, pp. 582-3, for Rx.
 *
 * @author Peter N. Steinmetz
 * @version 18 May 02
 */

import junit.framework.TestCase;

public class TestWilcox extends TestCase {
  
    public void testCumulative() {
      double Rx = 7;
      int m = 3;
      int n = 4;
      double Ux = Rx - m * (m+1) / 2;
      assertEquals(0.057,wilcox.cumulative(Ux, m, n),1e-3);
      
      // c = 29, m = 6, n = 9 case
      assertEquals(0.013,wilcox.cumulative(8,6,9),1e-3);
      
      // c = 14, m = 4, n = 9 case
      assertEquals(0.017, wilcox.cumulative(4,4,9),1e-3);
      
    }

}
