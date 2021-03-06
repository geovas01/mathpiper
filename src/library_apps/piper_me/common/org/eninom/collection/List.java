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

package org.eninom.collection;

import org.eninom.iterator.ForwardIterator;

//! List Interface
/*<literate>*/
/**
 * Interface for a mutable, randomly acessible collection. The performance of
 * get(i) is implementation-dependent. 
 */
public interface List<E> extends IterableCollection<E> {
  E get(long i);
  
  /**
   * Two Lists are equal if and only if they have the same size and the sequence of
   * elements are equal. This allows to test different implementations for equality.
   */
  public boolean equals(Object obj);
  
  /**
   * The hashcode of a List is the sum of the hashcodes of its elements.
   */
  public int hashCode();
  
  /**
   * The string representation of a list is defined in Collections.toString(List).
   */
  public String toString();
  
  static public class RandomAccessIterator<E> implements ForwardIterator<E> {
    private int i = 0;
    private List<E> collection;
    public RandomAccessIterator(List<E> collection) {
      super();
      this.collection = collection;
    }
    public boolean hasNext() {
      return i < collection.size();
    }
    
    public E next() {
      return collection.get(i++);
    }
  }
}
