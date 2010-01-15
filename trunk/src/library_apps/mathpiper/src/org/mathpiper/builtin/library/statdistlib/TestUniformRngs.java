/*
 * Created on Apr 16, 2007
 */
package org.mathpiper.builtin.library.statdistlib;;

import org.apache.log4j.BasicConfigurator;

import DistLib.rng.MarsagliaMulticarry;
import DistLib.rng.SuperDuper;
import DistLib.rng.WichmannHill;
import junit.framework.TestCase;


public class TestUniformRngs extends TestCase {

  uniform unirng;
  
  public void setUp() {
    BasicConfigurator.configure();
    unirng = new uniform(); // presently must invoke constructor to initialize
  }
  
  static double[] expWichmannHill = {0.7061509002529788,
                                      0.7518728943560409,
                                      0.593337134401559};
  // test unconfigured generation
  // which results in Wichmann-Hill
  // value obtain from run with code as of 16 Apr 07
  public void testUnconfigGen() {
    assertEquals(expWichmannHill[0],uniform.random(),1e-17);
    assertEquals(expWichmannHill[1],uniform.random(),1e-17);
    assertEquals(expWichmannHill[2],uniform.random(),1e-17);
  }
  
  public void testOOWichmannHill() {
    WichmannHill wh = new WichmannHill();
    for (int ti=0; ti < expWichmannHill.length; ti++) {
      assertEquals(expWichmannHill[ti],wh.random(),1e-17);
    }    
  }
  
  static final double[] expMarsaglia = { 0.3845714517833132, 0.06812816859877857 };
  
  public void testOOMarsagliaMulticarry() {
    MarsagliaMulticarry mm = new MarsagliaMulticarry();
    for (int ti=0; ti < expMarsaglia.length; ti++) {
      assertEquals(expMarsaglia[ti],mm.random(),1e-17);
    }        
  }
  
  static final double[] expSuperDuper = { 0.11203708176315692,0.41164893061193836};
  
  public void testOOSuperDuper() {
    SuperDuper sd = new SuperDuper();
    for (int ti=0; ti < expMarsaglia.length; ti++) {
      assertEquals(expSuperDuper[ti],sd.random(),1e-17);
    }    
  }
  
}
