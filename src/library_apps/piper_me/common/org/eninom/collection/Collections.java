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

import org.eninom.iterator.*;
import org.eninom.seq.Seq;
import org.eninom.algorithm.Comparator;
import org.eninom.collection.arrays.PrimitiveArrays;
import org.eninom.collection.mutable.HashSet;

//! Routines for Collections 
/*<literate>*/
/**
This class provides several routines for dealing with collections
**/
final public class Collections {
  /**
   * This class has no instances.
   */
  private Collections() {}  
  
  public static<E> boolean contains(IterableCollection<E> collection, E e) {
    ForwardIterator<E> it = collection.iterator();
    for (int i = 0; i < collection.size(); i++) {
      E item = it.next();
      if (item == null) {
        if (e == null)
          return true;
      }
      else if (item.equals(e))
        return true;
    }//`for`
    return false;
  }
  
  /**
   * Contains an element equal to <i>e</i>, if such an element exists.
   * Otherwise, null is returned. It is required that <i>e</i> is not null.
   */
  public static<E> E find(IterableCollection<E> collection, E e) {
    if (e == null)
      throw new NullPointerException("Cannot search for null element");
    ForwardIterator<E> it = collection.iterator();
    for (int i = 0; i < collection.size(); i++) {
      E item = it.next();
      if (item.equals(e))
        return item;
    }//`for`
    return null;
  }  
  
  /**
   * The following class can be used in order to make any set immutable.
   * This only works if the decorator gets complete ownership of the set.
   * The class is only package-visible. 
   */
  final static class ImmutableDecoratorForSet<E> implements ImmutableSet<E>{

    Set<E> set;
       
    public ImmutableDecoratorForSet(Set<E> set) {
      this.set = set;
    }

    public boolean contains(E e) {
      return set.contains(e);
    }

    public ForwardIterator<E> iterator() {
      return set.iterator();
    }

    public Seq<E> seq() {
      return set.seq();
    }

    public long size() {
      return set.size();
    }

    public E getSame(E item) {
      return set.getSame(item);
    }    
    
    public int hashCode() {
      return set.hashCode();
    }
    
    public boolean equals(Object obj) {
      return set.equals(obj);
    }
    
    public String toString() {
      return set.toString();
    }
  }//`inner class`
  
  /**
   * turns a collection into an immutable set.
   */
  public static<E> ImmutableSet<E> createSet(IterableCollection<E> items) {
    HashSet<E> set = new HashSet<E>(items.size()*3);
    ForwardIterator<E> iter = items.iterator();
    while (iter.hasNext())
      set.add(iter.next());
    return new ImmutableDecoratorForSet<E>(set);
  }

  /**
   * Performs a default equality test between a set and another object.
   * Use this or a functionally equivalent method in all implementations of
   * the set interface.
   */
  @SuppressWarnings("unchecked")
public static <E> boolean equalsForSet(Set<? extends E> a, Object obj)
   {
    if (a == obj) {
      return true;
    }
    
    if (( a == null) || (obj == null))
      return false;
    
    if (!(obj instanceof Set)) {
      return false;
    }
    
    Set<? extends E> b = (Set<? extends E>) obj;
    
    if (a.size() != b.size())
      return false;
    
    HashSet<E> elements = new HashSet<E>();
    
    ForwardIterator<? extends E> iterA = a.iterator();
    while(iterA.hasNext())
      elements.add(iterA.next());
    
    ForwardIterator<? extends E> iterB = b.iterator();
    while(iterB.hasNext())
      if(!elements.contains(iterB.next()))
          return false;
    
    return true;
  }
  
  
  /**
   * Performs a default equality test between a list and another object.
   * Use this or a functionally equivalent method in all implementations of
   * the list interface.
   */
  @SuppressWarnings("unchecked")
  public static <E> boolean equalsForList(List<E> c,
      Object obj) {
    
    if (c == obj) {
      return true;
    }
    
    if (( c == null) || (obj == null))
      return false;
    
    if (!(obj instanceof List)) {
      return false;
    }
    IterableCollection<Object> d = (IterableCollection<Object>) obj;
    if (c.size() != d.size()) {
      return false;
    }
    long n = c.size();
    ForwardIterator<E> it1 = c.iterator();
    ForwardIterator<Object> it2 = d.iterator();
    for (int i = 0; i < n; i++) {
      Object o1 = it1.next();
      Object o2 = it2.next();
      if (o1 != o2) {
        if ((o1 == null) || (o2 == null) || (!o1.equals(o2)))
            return false;
      }
    }
    return true;
  }

  /**
   * Computes a default hash value of a set. Use this or a functionally 
   * equivalent method in all implementations of the set interface.
   */
  final public static <E> int hashCodeForSets(Set<E> c) {
    ForwardIterator<E> it = c.iterator();
    int s = 0;
    while (it.hasNext()) {
      Object value = it.next();
      if (value != null)
        s = s + value.hashCode();
    }
    return s;
  }
  
  /**
   * Computes a default hash value of a list. Use this or a functionally 
   * equivalent method in all implementations of the list interface.
   */
  final public static <E> int hashCodeForLists(List<E> c) {
    ForwardIterator<E> it = c.iterator();
    int s = 0;
    while (it.hasNext()) {
      Object value = it.next();
      int x = 0;
      if (value != null)
        x = value.hashCode();
       s = 12343 * s + x; 
    }
    return s;
  }
  
  /**
   * Returns a string representation of the given collection with elements 
   * in iteration order. Use this if the collection is sorted (tree) or has
   * a deterministic order (stack).
   */
  public static<E> String toStringIterationOrder(List<E> collection) {
    StringBuilder str = new StringBuilder();
    str.append("[");
    ForwardIterator<E> it = collection.iterator();
    for (int i = 0; i < collection.size(); i++) {
      E item = it.next();
      if (item == null)
      str.append("<null>");
      else
      str.append(item.toString());
      if (i < collection.size() - 1)
      str.append(", ");
    }//`for`
    str.append("]");
    return str.toString();
  }
  
  /**
   * Returns a string representation of the given collection with element
   * strings in lexicograhic order. Use this if the collection has an
   * unpredictable iteration order (hashset).
   */
  public static<E> String toStringSorted(IterableCollection<E> set) {
    
    Stack<String> elementStr = Stack.create();
    ForwardIterator<E> it = set.iterator();
    while (it.hasNext()) {
      E item = it.next();
      if (item == null)
        elementStr.push("<null>");
      else
        elementStr.push(item.toString());
    }
    
    elementStr = Stack.fromCollection(sort(elementStr, 
        Comparator.StringLexicographic));
    
    StringBuilder str = new StringBuilder();
    str.append("[");
    ForwardIterator<String> elementStrIt = elementStr.iterator();
    for (int i = 0; i < set.size(); i++) {
      str.append(elementStrIt.next());
      if (i < set.size() - 1)
      str.append(", ");
    }//`for`
    str.append("]");
    return str.toString();
  }
  
  /**
   * Returns a list with the same elements in sorted order. If the number of
   * elements is small enough (less than 2^20) the returned list is guaranteed
   * to have the access time of an array (with a small constant factor for
   * one additional method call). In general, random access of the returned list
   * is guaranteed to be O(log n).
   * The sorting algorithm needs O(log n) time and O(log n) stack size. Both
   * limits are maximum, not average.
   */
  static<E> List<E> sort(IterableCollection<? extends E> C, 
      Comparator<? super E> cmp) 
  {
    List<E> list = sort(C, cmp, SORT_LIST_IMPL_THRESHOLD);
    if (C.size() < SORT_LIST_IMPL_THRESHOLD)
      return list;
    else
      return Tuple.fromCollection(list);
  }
  
  private static final int SORT_LIST_IMPL_THRESHOLD = 1024*1024;
  
  /**
   * Returns a list with the same elements in sorted order. If the number of
   * elements is small enough (less than the given threshold) the returned list
   * is guaranteed to have the access time of an array (with a small constant
   * factor for one additional method call). If the size is larger than the
   * given threshold, then the list representation may have linear access
   * time!
   * The threshold must be in the range 0..Integer.MAX_VALUE/2-1
   * The sorting algorithm needs O(log n) time and O(log n) stack size. Both
   * limits are maximum, not average.
   */
  static<E> List<E> sort(IterableCollection<? extends E> C, 
      Comparator<? super E> cmp, int threshold) 
  {
    if ((threshold < 0) || (threshold >= Integer.MAX_VALUE))
      throw new IllegalArgumentException("threshold for list implementation " +
          "in sorter" +
          "must be within 0..Integer.MAX_VALUE/2-1");
    long n = C.size();
    if (n <= 1)
      return PrimitiveArrays.fromCollection(C);
    
    if (n <= threshold)
      return PrimitiveArrays.sort(PrimitiveArrays.fromCollection(C), cmp);
    
    long p = n / 2;
    List<E> S = Stack.fromCollection(C);
    List<E> left = sort(Range.create(S, 0, p), cmp, threshold);
    List<E> right = sort(Range.create(S, p, n), cmp, threshold);
    List<E> L = merge(left, right, cmp);
    return L;
  }
  
  static<E> Stack<E> merge(List<E> left, List<E> right, Comparator<? super E> cmp) {
    Stack<E> S = Stack.create();
    LookAheadIterator<E> it1 = new LookAheadIterator<E>(left.iterator());
    LookAheadIterator<E> it2 = new LookAheadIterator<E>(right.iterator());
    
    while (it1.hasNext() && it2.hasNext()) {
      if (cmp.compare(it1.peek(), it2.peek()) <= 0)
        S = S.push(it1.next());
      else
        S = S.push(it2.next());
    }
    if (!it1.hasNext())
      it1 = it2;
    
    while (it1.hasNext()) {
      S = S.push(it1.next());
    }
    
    return S.reverse();
  }
}
