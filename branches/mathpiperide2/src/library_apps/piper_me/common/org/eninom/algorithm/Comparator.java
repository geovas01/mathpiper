package org.eninom.algorithm;

import org.eninom.arithmetic.Rational;

public interface Comparator<E> {
  public int compare(E a, E b);
  
  final static public Comparator<String> StringLexicographic = 
    new Comparator<String>() {
      public int compare(String a, String b) {
        return a.compareTo(b);
      }
  };
  
  final static public Comparator<Rational> RationalNatural = 
    new Comparator<Rational>() {
      public int compare(Rational a, Rational b) {
        return a.compareTo(b);
      }
  };
}
