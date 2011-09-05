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

import java.util.NoSuchElementException;

import org.eninom.algorithm.Comparator;
import org.eninom.collection.ImmutableCollection;
import org.eninom.iterator.ForwardIterator;
import org.eninom.seq.Seq;

// ! Immutable Stack
/* <literate> */
/**
 * Immutable Stack. If you push an element to stack and subsequently 
 * pop it, you might not get back your original stack in terms of object 
 * identity. The reason is that we allow the implementation to pack
 * subsequent elements together into blocks, and by doing so, no
 * reference to the original stack is kept.
 */
@SuppressWarnings("unchecked")
public final class Stack<E> implements
ImmutableCollection<E>, List<E>, Seq<E> {

  private final static int MAX_H = 4;

  private final Object a0, a1, a2, a3;

  private final Stack next;

  private final int length;
  
  
  /*
   * We use a default empty stack, so creation of a stack needs zero object
   * allocations. Note, however, that popping from a one element stack returns
   * an empty stack, but not neccessarily the default empty stack.
   */
  static private Stack emptyStack = new Stack();

  public static <F> Stack<F> create() {
    return emptyStack;
  }

  private Stack() {
    a0 = a1 = a2 = a3 = null;
    next = null;
    length = 0;
  }

  // push
  private Stack(E a, Stack<E> S) {
 Object e0 = null, e1 = null, e2 = null, e3 = null;
    
    Stack listNext = null; 
      length = S.length + 1;
      int k = S.length % MAX_H;
      switch (k) {
      case 3:
        e1 = S.a1;
      case 2:
        e2 = S.a2;
      case 1:
        e3 = S.a3;
      case 0:
      }
      switch (k) {
      case 3:
        e0 = a;
        listNext = S.next;
        break;
      case 2:
        e1 = a;
        listNext = S.next;
        break;
      case 1:
        e2 = a;
        listNext = S.next;
        break;
      case 0:
        e3 = a;
        if (length > 0)
          listNext = S;
        break;
      }
    a0 = e0; a1 = e1; a2 = e2; a3 = e3;
    next = listNext;
  }

  /**
   * The one-element constructor is used for removing the top element.
   */
  private Stack(Stack<E> S) {
Object e0 = null, e1 = null, e2 = null, e3 = null;
    
    if (S.length < 1)
      throw new IllegalArgumentException();
    length = S.length - 1;
    int k = length % MAX_H;
    if (k == 0)
      S = S.next;

    next = S.next;

    switch (k) {
    case 0:
      e0 = S.a0;
    case 3:
      e1 = S.a1;
    case 2:
      e2 = S.a2;
    case 1:
      e3 = S.a3;
    }
    
    a0 = e0; a1 = e1; a2 = e2; a3 = e3;
  }

  /**
   * Size of the stack.
   */
  public long size() {
    return length;
  }

  /**
   * Returns the stack with a new element added on top. If you push an element to
   * stack and subsequently pop it, you might not get back your original stack
   * in terms of object identity.
   */
  public Stack<E> push(E e) {
    return new Stack<E>(e, this);
  }

  /**
   * Returns the stack without the top element. If you push an element to stack
   * and subsequently pop it, you might not get back your original stack in
   * terms of object identity.
   */
  public Stack<E> pop() {
    int s = (int) size();
    if (s == 0) {
      throw new IllegalStateException(
          "Cannot pop argument from an empty stack");
    }
    if (s == 1)
      return emptyStack;
    else
      return new Stack<E>(this);
  }

  /**
   * Returns the stack without <i>n</i> top elements. TODO: optimize (already
   * in Cons):
   */
  public Stack<E> pop(int n) {
    if (n <= 0) {
      return this;
    } else {
      return this.pop().pop(n - 1);
    }
  }
  
  public E get(long p) {
    if ((p >= size()) || (p < 0)) {
      throw new IndexOutOfBoundsException(
          "Index is negative or exceeds stacks size " + p + ".");
    }

    long k = length % MAX_H;
    if (k > 0) {
      p = p + MAX_H - k;
    }
    Stack node = this;
    while (p >= MAX_H) {
      p = p - MAX_H;
      node = node.next;
    }// `while`
    switch ((int) p) {
    case 0:
      return (E) node.a0;
    case 1:
      return (E) node.a1;
    case 2:
      return (E) node.a2;
    case 3:
      return (E) node.a3;
    default:
      return null;
    }// `switch`
  }

  public E top() {
    if (size() == 0) {
      throw new IllegalStateException(
          "Cannot get element from an empty stack");
    }
    return first();
  }

  public Stack<E> rest() {
    /*
     * Note that the contract of {\em Seq} requires to return null at the end of
     * the sequence.
     */
    if (length == 1) {
      return null;
    } else {
      return pop();
    }
  }

  static private class MyIterator<E> implements ForwardIterator<E> {

    
    Stack node;
    int i;
    
    public MyIterator(Stack S) {
      i = 0;
      if (S.length == 0)
        node = null;
      else
        node = S;
    }
    
    public boolean hasNext() {
      return ((node != null) && (i < node.length));
    }

    public E next() {
      if (!hasNext())
        throw new NoSuchElementException("No next element.");
      
      E result = (E) node.get(i);
      
      i = i + 1;
      
      if ((node.length - i) % 4 == 0) {
        node = node.next;
        i = 0;
      }
      
      return result;
    }
    
  };
  
  
  public ForwardIterator<E> iterator() {
    return new MyIterator<E>(this);
  }

  public Seq<E> seq() {
    return this;
  }

  public boolean contains(E e) {
    return Collections.contains(this, e);
  }
  
  public String toString() {
   return Collections.toStringIterationOrder(this); 
  }
  
  public int hashCode() {
    return Collections.hashCodeForLists(this);
  }
  
  public boolean equals(Object obj) {
    return Collections.equalsForList(this, obj);
  }
  
  public Stack<E> sort(Comparator<? super E> cmp) {
    return null;
  }
  
  final public E first() {
    return get(0);
  }
  
  public Stack<E> reverse() {
    return reverse(this);
  }
  
  /**
   * creates a stack from a given collection. Argument and result will
   * have the same iteration order. 
   */
  static public<E> Stack<E> fromCollection(IterableCollection<? extends E> C) {
    if (C.getClass() == Stack.class)
      return (Stack<E>) C;
    
    return reverse(reverse(C));
  }
  
  static public<E> Stack<E> reverse(IterableCollection<? extends E> C) {
    Stack<E> S = create();
    ForwardIterator<? extends E> it = C.iterator();
    while(it.hasNext())
      S = S.push(it.next());
    return S;
  }
}
