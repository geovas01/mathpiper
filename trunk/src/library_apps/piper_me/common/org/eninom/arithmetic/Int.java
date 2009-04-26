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

import java.util.Random;

import org.eninom.numerics.*;

//! Integers
/*<literate>*/
/**
It is discouraged to specialize this class outside from its package.
**/
public abstract class Int extends Rational {
  
  /**
   * Users cannot call the default constructor.
   */
  protected Int() {}
  
  public static final Int MINUS_ONE = LargeIntegerNumberImpl.create(-1);  
  public static final Int ZERO = LargeIntegerNumberImpl.create(0);
  public static final Int ONE = LargeIntegerNumberImpl.create(1);
  public static final Int TWO = LargeIntegerNumberImpl.create(2);
  public static final Int THREE = LargeIntegerNumberImpl.create(3);
  public static final Int FOUR = LargeIntegerNumberImpl.create(4);
  public static final Int FIVE = LargeIntegerNumberImpl.create(5);
  
    public static Int create(long value) {
      return LargeIntegerNumberImpl.create(value);
    }
  
    static Int create(BigInteger value) {
      return LargeIntegerNumberImpl.create(value);
    }
    
    @Override
    public boolean isInteger() { return true; }
    
    @Override
    public Int Nominator() {
      return this;
    }
    
    @Override
    public Int Denominator() {
      return ONE;
    }
    
    public static Int createFromByteArray(byte[] bytes) {
      BigInteger x = new BigInteger(bytes);
      long asLong = x.longValue();
      if (BigInteger.valueOf(asLong).equals(x))
        return create(asLong);
      else return new LargeIntegerNumberImpl(x);  
    }
    
    /**
     * returns a faithful representation as a byte array.
     */
    public abstract byte[] toByteArray();
    
    @Override
    public abstract Int add(Int b);
    protected abstract Int add(WordIntegerNumberImpl b);
    protected abstract Int add(LargeIntegerNumberImpl b);
    @Override
    public abstract Int multiply(Int b);
    protected abstract Int multiply(WordIntegerNumberImpl b);
    protected abstract Int multiply(LargeIntegerNumberImpl b);
    public abstract Int subtract(Int b);
    protected abstract Int subtractFrom(WordIntegerNumberImpl  b);
    protected abstract Int subtractFrom(LargeIntegerNumberImpl b);
    @Override
    public abstract int compareTo(Int b);
    protected abstract int compareTo(WordIntegerNumberImpl b);
    protected abstract int compareTo(LargeIntegerNumberImpl b);
    public abstract Int gcd(Int b);
    protected abstract Int gcd(WordIntegerNumberImpl b);
    protected abstract Int gcd(LargeIntegerNumberImpl b);
    public abstract Int intDiv(Int b);
    protected abstract Int intDivFrom(WordIntegerNumberImpl b);
    protected abstract Int intDivFrom(LargeIntegerNumberImpl b);
 
    public abstract int intValue();
           
    @Override
    public Int add(long b)
    {return add(Rational.create(b));  }
    @Override
    public Int multiply(long b)
    {return multiply(Rational.create(b));  }
    @Override
    public Int subtract(long b)
    {return subtract(Rational.create(b));  }
    @Override
    public int compareTo(long b)
    {return compareTo(Rational.create(b));  }
    
    static public Int probablePrime(int bitLength, Random rnd) {
      return create(BigInteger.probablePrime(bitLength, rnd));
    }
}
