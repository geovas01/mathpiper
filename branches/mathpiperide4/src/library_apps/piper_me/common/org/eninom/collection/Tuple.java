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

import org.eninom.collection.List;
import org.eninom.collection.arrays.ObjectArray;
import org.eninom.iterator.ForwardIterator;
import org.eninom.seq.Seq;
import org.eninom.seq.SeqFromIterator;

//! Immutable Tuple
/*<literate>*/
/**
 * This class provides a fixed-size immutable array with update operation.
 * Setting an element to a value returns a new array. 
 */
public final class Tuple<E> extends TreeTupleBase<E> implements ImmutableCollection<E>, List<E>, ObjectArray<E> {
  
  private Tuple(long sz) {
	  super(sz);
  }
  
  @SuppressWarnings("unchecked")
  static final private Tuple emptyTuple = new Tuple(0);
  
  /**
   * Construct a new tuple of given size.
   */
  @SuppressWarnings("unchecked")
  static public<E> Tuple<E> create(long size) {
    if (size == 0)
      return emptyTuple;
    else
      return new Tuple<E>(size);
  }
  
  private Tuple(Tuple<E> x, long i, E e) {
	  super(x,i,e);
  }
  
  public E at(long i) {
    return super.get(i);
  }
  
  public E get(long i) {
    return super.get(i);
  }
  
  public E get(Long i) {
    return at(i);
  }
   
  /**
   * returns a tuple whith the <i>i</i>th element replaced. 
   */
  public Tuple<E> set(long i, E e) {
    return new Tuple<E>(this,i,e);
  }
    
  public Seq<E> seq() {
   return SeqFromIterator.create(iterator());
  }
  
  public ForwardIterator<E> iterator() {
   return new List.RandomAccessIterator<E>(this);
  }
  
  public Object[] asMutableArray() {
    return super.asMutableArray();
  }

  public boolean contains(E e) {
    return Collections.contains(this, e);
  }

  public long size() {
    return super.adressableSize();
  }
  
  @Override
  public int hashCode() {
    return Collections.hashCodeForLists(this);
  }
  
  @Override
  public boolean equals(Object obj) {
    return Collections.equalsForList(this, obj);
  }
  
  public String toString() {
   return Collections.toStringIterationOrder(this); 
  }
  
  /**
   * TODO: Optimize in order to achieve linear time.
   */
  static public<E> Tuple<E> fromCollection(IterableCollection<? extends E> C) {
    Tuple<E> tuple = create(C.size());
    ForwardIterator<? extends E> it = C.iterator();
    long i = 0;
    while (it.hasNext()) {
     tuple = tuple.set(i, it.next());
     i = i + 1;
    }
    return tuple;
  }
}//`class`
