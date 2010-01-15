/*
 * Created on Apr 15, 2007
 */
package org.mathpiper.builtin.library.statdistlib;;

import junit.framework.TestCase;

public class TestMisc extends TestCase {

  public void testDoubleConstants() {
    assertEquals( Math.log(2.0)/Math.log(10.0), misc.d1mach(5), 1e-7);
  }
  
  public void testFPrec() {
    assertEquals(3.1416,misc.fprec(Math.PI, 5),1e-8);
    assertEquals(2.7182818,misc.fprec(Math.E,8),1e-9);
  }
}
