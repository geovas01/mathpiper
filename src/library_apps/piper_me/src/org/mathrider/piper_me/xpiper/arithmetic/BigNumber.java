package org.mathrider.piper_me.xpiper.arithmetic;

/*
(C) Oliver Glier 2008. This file belongs to Piper-ME/XPiper, which
is part of a modified version of Yacas ((C) Ayal Pinkus et al.)

Currently, the licence terms of this file is simple: With this (AND ONLY THIS)
file you can do whatever you want to do. Piper-ME/XPiper evolves from
Yacas with two goals in mind: a) port it to the Java-ME API, and b) become
a complete redesign of the Yacas core engine. When these efforts are complete,
we hope we can distribute the Piper engine under GPL with linking exception.
For the time being, it is not possible to grant such an exception for 
Piper-ME/XPiper due to its reliance of the original Yacas code.
*/


import org.mathrider.piper_me.xpiper.arithmetic.oldImpl.*;


//! Base class for Piper Numbers
/*<literate>*/
/**
This abstract class defines the methods of Piper numbers. It
also implements the constructor routines that choose the
right implementation.<br />
<br />
Currently, the constructors can a switch between the
original number algorithms from Yacas and the new ones
for Piper. 
*/
public abstract class BigNumber
{
  /**
   * Flag for using Piper numbers. Set this before
   * you construct any numbers or leave it default.
   */
  static public boolean usePiperNumbers = false;
  
  public BigNumber() {};

  /**
   * create number from string.
   */
  static public BigNumber create( String aString,int aBasePrecision,int aBase/*=10*/) {
	  return YacasBigNumber.create(aString, aBasePrecision, aBase);
  }
  
  /**
   * create number with given precision and value ???.
   */
  static public BigNumber create(int aPrecision/* = 20*/)
  {
	return YacasBigNumber.create(aPrecision);
  }
  
  /**
   * create number from long integer.
   */
  static public BigNumber create(int pPrecision, long value) {
	  return YacasBigNumber.create(pPrecision, value);
  }
  
  /**
   * create number from integer.
   */
  static public BigNumber create(int pPrecision, int value) {
	  return YacasBigNumber.create(pPrecision, value);
  }
  
  /**
   * create number from double.
   */
  static public BigNumber create(int pPrecision, double value) {
	  return YacasBigNumber.create(pPrecision, value);
  }
  
  /**
   * test if number is float.
   */
  public abstract boolean IsFloat(String aString,int aBase);
  

  /**
   * convert to string.
   */
  public abstract String ToString(int aPrecision, int aBase/*=10*/);
  
 /**
   * Give approximate representation as a double number
   */
  public abstract double Double();
  
 /**
   * convert to long (what if too large?) 
   */
  public abstract long Long();
  
  /**
   * test if equals
   */
  public abstract boolean Equals( BigNumber aOther);
  
  /**
   * test if less. (replace with compare???)
   */
  public abstract boolean LessThan( BigNumber aOther);
  
  /**
   * test if integer
   */
  public abstract boolean IsInt();
  
  /**
   * test if small ???
   */
  public abstract boolean IsSmall();
  
  /**
   * turn into float ???
   */
  public abstract BigNumber becomeFloat();
  
  /**
   * multiply
   */
  public abstract BigNumber multiply(BigNumber b, int aPrecision);
	
  /**
   * negate. Strange this throws an exception, but multiplication
   * with -1 not???
   */
  public abstract BigNumber negate(int aPrecision ) throws Exception;
  
  /**
   * greatest common divisor
   */
  public abstract BigNumber gcd(BigNumber b, int aPrecision) throws Exception;
  
  /**
   * addition
   */
  public abstract BigNumber add(BigNumber b, int aPrecision);
	
  /**
   * division. Throws exception if division by zero
   */
  public abstract BigNumber divide(BigNumber b, int aPrecision);
	
  /**
   * remainder 
   */
  public abstract BigNumber mod(BigNumber b, int aPrecision) throws Exception;
  
  /**
   * next smallest integer
   */
  public abstract BigNumber floor(int aPrecesion);
	
  /**
   * set precision.
   */
  public abstract BigNumber setPrecision(int aPrecision);
	
  /**
   * bitwise shift.
   */
  public abstract BigNumber shiftLeft(int aNrToShift, int aPrecision ) throws Exception;
    
  /**
   * bitwise shift.
   */
  public abstract BigNumber shiftRight(int aNrToShift, int aPrecision ) throws Exception;
  
  
  /**
   * bitwise and.
   */
  public abstract BigNumber bitAnd(BigNumber b, int aPrecision ) throws Exception;
  
  /**
   * bitwise or.
   */
  public abstract BigNumber bitOr(BigNumber b, int aPrecision ) throws Exception;
  
  /**
   * bitwise xor.
   */
  public abstract BigNumber bitXor(BigNumber b, int aPrecision ) throws Exception;
  
  /**
   * bitwise not.
   */
  public abstract BigNumber bitNot(int aPrecision ) throws Exception;
  
  public abstract long BitCount();
  
    /// Give sign (-1, 0, 1)
  public abstract int Sign();
  public abstract int GetPrecision();
  
  /**
   * For debugging purpose only: return internal representation
   * as string.
   */
  public abstract String debug() throws Exception;
  
}
