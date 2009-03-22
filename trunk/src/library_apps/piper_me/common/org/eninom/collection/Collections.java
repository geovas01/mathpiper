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
  
  public static<E> E find(IterableCollection<E> collection, E e) {
    if (e == null)
      throw new NullPointerException("Cannot searh for null element");
    ForwardIterator<E> it = collection.iterator();
    for (int i = 0; i < collection.size(); i++) {
      E item = it.next();
      if (item.equals(e))
        return item;
    }//`for`
    return null;
  }  
  
  public static<E> String printToString(IterableCollection<E> collection) {
    StringBuilder str = new StringBuilder();
    str.append("[");
    ForwardIterator<E> it = collection.iterator();
    for (int i = 0; i < collection.size(); i++) {
      E item = it.next();
      if (item == null)
      str.append("null");
      else
      str.append(item.toString());
      if (i < collection.size() - 1)
      str.append(", ");
    }//`for`
    str.append("]");
    return str.toString();
  }
  
  /**
   * Checks it two sets are extensionally equal
   */
  public static <E> boolean equalSets(Set<? extends E> a, Set<? extends E> b)
   {
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
   * Performs a default equality test between an iterable collection
   * and another object.
   */
  @SuppressWarnings("unchecked")
  public static <E> boolean equals(IterableCollection<E> c,
      Object obj) {
    
    if (c == obj) {
      return true;
    }
    
    if (( c == null) || (obj == null))
      return false;
    
    if (c.getClass() != (obj.getClass())) {
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
   * Computes a default hash value of an iterable collection.
   */
  final public static <E> int hashCode(IterableCollection<E> c) {
    ForwardIterator<E> it = c.iterator();
    int s = 0;
    while (it.hasNext()) {
      Object value = it.next();
      if (value != null)
        s = s + value.hashCode();
    }
    return s;
  }
}
