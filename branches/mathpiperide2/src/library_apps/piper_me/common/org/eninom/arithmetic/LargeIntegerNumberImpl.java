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

//! Internal Representation
/*<literate>*/
/**
Representation of large Numbers
**/
final class LargeIntegerNumberImpl extends Int
{      
    private final BigInteger value;
    
    protected LargeIntegerNumberImpl(BigInteger a) {
      value = a;
    }
   
    static public Int create(BigInteger a) {
        if ((a.compareTo(WordIntegerNumberImpl.MIN_VALUE)>=0) 
        && (a.compareTo(WordIntegerNumberImpl.MAX_VALUE)<=0))
          return WordIntegerNumberImpl.create(a.longValue());
        else 
          return new LargeIntegerNumberImpl(a);
    }
    
    static public Int create(long a) {
        if ((a >= WordIntegerNumberImpl.MIN_VALUE_AS_LONG) 
        && (a <= WordIntegerNumberImpl.MAX_VALUE_AS_LONG))
          return WordIntegerNumberImpl.create(a);
        else 
          return new LargeIntegerNumberImpl(BigInteger.valueOf(a));
    }
    
    BigInteger getValue() {
        return value;
    }
    
    @Override
    public int intValue() {
      return value.intValue();
    }
    
    //double dispatch:
    @Override
    public Rational add(Rational b) { 
        if (b == Rational.ZERO) return this;
        return b.add(this); 
    }
    @Override
    public Int add(Int b) { 
        if (b == Rational.ZERO) return this;
        return b.add(this); 
    }
    
    @Override
    public Rational multiply(Rational b) { 
        if (b == Rational.ONE) return this;
        return b.multiply(this); 
    }
    @Override
    public Int multiply(Int b) { 
        if (b == Rational.ONE) return this;
        return b.multiply(this); 
    }
    
      @Override
      public Rational subtract(Rational b) { 
        if (b == Rational.ZERO) return this;
        return b.subtractFrom(this); 
    }
    
    @Override
    public Int subtract(Int b) { 
          if (b == Rational.ZERO) return this;
          return b.subtractFrom(this); 
    }
    
    @Override
    public Int subtractFrom(Int b) { 
        if (this == Rational.ZERO) return b;
        return b.subtractFrom(this); 
  }
      
    @Override
    public int compareTo(Rational b) {
        return -b.compareTo(this);
    }
    
    @Override
    public int compareTo(Int b) {
        return -b.compareTo(this);
    }
       
    //implementation: 
    
    @Override
    public byte[] toByteArray() {
      return value.toByteArray();
    }
    
    @Override
    public Int add(WordIntegerNumberImpl b) {
      return create(value.add(BigInteger.valueOf(b.getValue())));
    }
     
    @Override
    public Int add(LargeIntegerNumberImpl b) {
      return create(value.add(b.getValue()));
    }
  
    @Override
    public Rational add(RationalNumberImpl b) {
      return b.add(this);
    }
     
    @Override
    public Int multiply(WordIntegerNumberImpl b) {
        return create(value.multiply(BigInteger.valueOf(b.getValue())));
      }
       
      @Override
      public Int multiply(LargeIntegerNumberImpl b) {
        return create(value.multiply(b.getValue()));
      }
    
      @Override
      public Rational multiply(RationalNumberImpl b) {
        return b.multiply(this);
      }
      
     @Override
    public Int subtractFrom(WordIntegerNumberImpl b) {
       return new LargeIntegerNumberImpl(
           BigInteger.valueOf(b.getValue()).subtract(value));
    }
     
    @Override
    public Int subtractFrom(LargeIntegerNumberImpl b) {
       return new LargeIntegerNumberImpl(b.getValue().subtract(value));
    }
     
    @Override
    public Rational subtractFrom(RationalNumberImpl b) {
      return b.subtract(this);
    }
    
    @Override
    public Rational inverseZ()
    {
    	return Rational.createFraction(BigInteger.ONE,value);
    }
    
    @Override
    public Rational inverse()
    {
      if (this == ZERO)
        throw new ArithmeticException("Division by Zero");
      return Rational.createFraction(BigInteger.ONE,value);
    }
    
    @Override
    public int compareTo(WordIntegerNumberImpl b) {
      if ((Object) this == b)
        return 0;
      return value.compareTo(BigInteger.valueOf(b.getValue()));
    }
    
    @Override
    public int compareTo(LargeIntegerNumberImpl b) {
      if ((Object) this == b)
        return 0;
      return value.compareTo(b.getValue());
    }
    
    @Override
    public int compareTo(RationalNumberImpl b) {
      if ((Object) this == b)
        return 0;
      return -b.compareTo(this);
    }
   
    @Override
    public Int gcd(Int b)
    { return b.gcd(this); }
    
    @Override
    public Int gcd(WordIntegerNumberImpl b) 
    { return create(value.gcd(BigInteger.valueOf(b.getValue()))); }
    
    @Override
    public Int gcd(LargeIntegerNumberImpl b)
    { return create(value.gcd(b.getValue())); } 
    
    @Override
    public Int intDiv(Int b)
    { 
      return b.intDivFrom(this);   
    }
    
    @Override
    public Int intDivFrom(WordIntegerNumberImpl b) 
    { 
      return create(BigInteger.valueOf(b.getValue()).divide(value)); 
    }
    
    @Override
    public Int intDivFrom(LargeIntegerNumberImpl b)
    { 
      return create(b.getValue().divide(value)); 
    } 
    
    @Override
    public String toString() {
      String str = value.toString();
      if ((value.compareTo(WordIntegerNumberImpl.MIN_VALUE)>=0) 
        && (value.compareTo(WordIntegerNumberImpl.MAX_VALUE)<=0))
          str = str + "REPORT ERROR: BigInteger has wordsize!";
      return  str;
    }
    
    @Override
    public int hashCode() {
      return value.hashCode();
    }
}
