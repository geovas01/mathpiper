/*
(C) Oliver Glier 2008

Eninom-Lib is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.
 
Eninom-Lib is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent
modules, and to copy and distribute the resulting executable under
terms of your choice, provided that you also meet, for each linked
independent module, the terms and conditions of the license of that
module.  An independent module is a module which is not derived from
or based on this library.  If you modify this library, you may extend
this exception to your version of the library, but you are not
obligated to do so.  If you do not wish to do so, delete this
exception statement from your version. */


package org.eninom.arithmetic;

import org.eninom.numerics.*;

//! Rational Numbers
/*<literate>*/
/**
It is discouraged to specialize this class outside from its package.
**/
public abstract class Rational {
	
  public static final Int MINUS_ONE = Int.MINUS_ONE;  
  public static final Int ZERO = Int.ZERO;
  public static final Int ONE = Int.ONE;
  public static final Int TWO = Int.TWO;
  
  protected Rational() { // users cannot instantiate this class
  }
       
  public static Int create(long value) {
    return LargeIntegerNumberImpl.create(value);
  }
  
   static Int create(BigInteger value) {
    return LargeIntegerNumberImpl.create(value);
  }
  
  public static Rational create(long nom, long denom) { 
      return RationalNumberImpl.createFraction(create(nom),create(denom));
  }
  
  static Rational createFraction(BigInteger nom, BigInteger denom) {  
    return RationalNumberImpl.createFraction(create(nom),create(denom));
  }
  
    public abstract Rational add(Rational b);
    public abstract Rational multiply(Rational b);
    public abstract Rational subtract(Rational b);
    public abstract int compareTo(Rational b);
    public abstract Rational inverseZ();
    public abstract Rational inverse();
    
    public Rational divideZ(Rational b)
    {
    	return multiply(b.inverseZ());
    }
    
    public Rational divide(Rational b)
    {
      return multiply(b.inverseZ());
    }
    
    public Rational add(long b)
    {return add(Rational.create(b));  }
    public Rational multiply(long b)
    {return multiply(Rational.create(b));  }
    public Rational subtract(long b)
    {return subtract(Rational.create(b));  }
    public Rational divide(long b)
    {return divide(Rational.create(b));  }
    public int compareTo(long b)
    {return compareTo(Rational.create(b));  }
    
   
    
    public abstract boolean isInteger();
    public abstract Int Nominator();
    public abstract Int Denominator();
    
    /**
     * Test if number is zero.
     */
    final public boolean isZero() {
      /*
       * We test for identity since there are no public constructors
       * for rational numbers and the methods ensure there is only one
       * object with zero-value.
       */
      if (this == ZERO) {
          return true;
      } else {
          return false;
      }
    }
    
    /**
     * returns -1/1 for negative/positive numbers and 0 for zero.
     */
    public int signum() {
    		int cmp = this.compareTo(ZERO);
    		if (cmp > 0)
    			return 1;
    		else if (cmp == 0)
    			return 0;
    		else return -1;
    }
    
    public boolean equals(Rational b) {
        return  (this.compareTo(b) == 0);
    }
    
    @Override
    public boolean equals(Object b) {
      if (!(b instanceof Rational)) {
        return false;        
      } else
       return  (this.compareTo((Rational) b) == 0);
    }
    
  // implementation part: not meant as a stable user interface.
  // (mostly method dispatch to concrete specializations which
  // are subject to change!!!)
    
  // abstract definition of concrete binary operations:
    
    protected abstract Rational add(Int b);
    protected abstract Rational add(RationalNumberImpl b);
    protected abstract Rational multiply(Int b);
    protected abstract Rational multiply(RationalNumberImpl b);
    protected abstract Rational subtractFrom(Int b);
    protected abstract Rational subtractFrom(RationalNumberImpl b);
    protected abstract int compareTo(Int b);
    protected abstract int compareTo(RationalNumberImpl b);
}//`class`
