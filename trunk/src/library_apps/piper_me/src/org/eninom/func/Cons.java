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

package org.eninom.func;

import java.util.NoSuchElementException;
import org.eninom.iterator.*;

//!Lisp-like Cons
/*<literate>*/
/**
 * This is a pair of two constant objects. More specifically, it suffices
 * that the elements' hash value and equality relation is constant
 * throughout their lifetime. <br />
 *  <br />
 *  It is possible to subclass <i>Cons</i>, however, all critical methods
 *  are made final to prevent interface breakage.
 *  <br />
 *  <br />
 *  Our implementation of <i>Cons</i> linearizes to the right in such
 * way that lists are stored in chunks of 4 elements.
 * <br />
 * <br />
 * second(cons(a,b)) == b is not necessarily true if b is a cons. On the other
 * hand, it is true if b is not a cons, and first(cons(a,b)) == ais  also always
 * true.
 */
@SuppressWarnings("unchecked")
public class Cons<A, B> {
  
  private final static int MAX_H = 4;

  private Object e0, e1, e2, e3;

  private Cons next = null;

  private int length = 0;

  /**
   * Since a Cons must have exactly two elements, we use the one-element
   * constructor for removing the first element from a triple.
   */
  public <C> Cons(Cons<C, ? extends Cons<A, B>> list) {
    if (list.length < 3)
      throw new IllegalArgumentException();
    length = list.length - 1;
    int k = length % MAX_H;
    if (k == 0)
      list = list.next;

    next = list.next;

    switch (k) {
    case 0:
      e0 = list.e0;
    case 3:
      e1 = list.e1;
    case 2:
      e2 = list.e2;
    case 1:
      e3 = list.e3;
    }
  }

  /**
   * Constructor for a pair of elements.
   */
  public Cons(A a, B b) {
    if ((b == null) || !(b.getClass() == this.getClass())) {
      e2 = a;
      e3 = b;
      length = 2;
    } else {
      Cons list = (Cons) b;
      length = list.length + 1;
      int k = list.length % MAX_H;
      switch (k) {
      case 3:
        e1 = list.e1;
      case 2:
        e2 = list.e2;
      case 1:
        e3 = list.e3;
      case 0:
      }
      switch (k) {
      case 3:
        e0 = a;
        next = list.next;
        break;
      case 2:
        e1 = a;
        next = list.next;
        break;
      case 1:
        e2 = a;
        next = list.next;
        break;
      case 0:
        e3 = a;
        if (length > 0)
          next = list;
        break;
      }
    }// `else`
  }

  /**
   * Constructor for an element and a <i>Cons</i>.
   */
  public Cons(A a, Cons b) {
    if (b == null) {
      e2 = a;
      e3 = b;
      length = 2;
    } else {
      Cons list = (Cons) b;
      length = list.length + 1;
      int k = list.length % MAX_H;
      switch (k) {
      case 3:
        e1 = list.e1;
      case 2:
        e2 = list.e2;
      case 1:
        e3 = list.e3;
      case 0:
      }
      switch (k) {
      case 3:
        e0 = a;
        next = list.next;
        break;
      case 2:
        e1 = a;
        next = list.next;
        break;
      case 1:
        e2 = a;
        next = list.next;
        break;
      case 0:
        e3 = a;
        if (length > 0)
          next = list;
        break;
      }
    }// `else`
  }

  /**
   * Constructor for a triple.
   */
  protected Cons(Object a, Object b, Object c) {

    length = 3;

    if ((c != null) && (c.getClass() == this.getClass())) {
      Cons list = (Cons) c;
      int k = list.length % MAX_H;
      length = 2 + list.length;

      switch (k) {
      case 0:
        e2 = a;
        e3 = b;
        next = list;
        break;
      case 1:
        e1 = a;
        e2 = b;
        e3 = list.e3;
        next = list.next;
        break;
      case 2:
        e0 = a;
        e1 = b;
        e2 = list.e2;
        e3 = list.e3;
        next = list.next;
        break;
      case 3:
        e3 = a;
        next = new Cons(b, list);
        break;
      }// `switch`
    } else {
      e1 = a;
      e2 = b;
      e3 = c;
    }// `else`
  }

  /**
   * Constructor for a quadruple.
   */
  protected Cons(Object a, Object b, Object c, Object d) {

    length = 4;

    if ((d != null) && (d.getClass() == this.getClass())) {
      Cons list = (Cons) d;
      int k = list.length % MAX_H;
      length = 3 + list.length;

      switch (k) {
      case 0:
        e1 = a;
        e2 = b;
        e3 = c;
        next = list;
        break;
      case 1:
        e0 = a;
        e1 = b;
        e2 = c;
        e3 = list.e3;
        next = list.next;
        break;
      case 2:
        e3 = a;
        next = new Cons(b, c, list);
        break;
      case 3:
        e2 = a;
        e3 = b;
        next = new Cons(c, list);
        break;
      }// `switch`
    } else {
      e0 = a;
      e1 = b;
      e2 = c;
      e3 = d;
    }// `else`
  }

  /**
   * Constructor for a quintuple.
   */
  protected Cons(Object a, Object b, Object c, Object d, Object e) {

    length = 5;

    if ((e != null) && (e.getClass() == this.getClass())) {
      Cons list = (Cons) e;
      int k = list.length % MAX_H;
      length = 4 + list.length;

      switch (k) {
      case 0:
        e0 = a;
        e1 = b;
        e2 = c;
        e3 = d;
        next = list;
        break;
      case 1:
        e3 = a;
        next = new Cons(b, c, d, list);
        break;
      case 2:
        e2 = a;
        e3 = b;
        next = new Cons(c, d, list);
        break;
      case 3:
        e1 = a;
        e2 = b;
        e3 = c;
        next = new Cons(d, list);
        break;
      }// `switch`
    } else {
      e3 = a;
      next = new Cons(b, c, d, e);
    }// `else`
  }

  public final int length() {
    return length;
  }

  /**
   * It is allowed to override the <i>get()</i> method.
   */
  public final Object getElement(int p) {
    if ((p >= length) || (p < 0))
      throw new IllegalArgumentException();

    int k = length % MAX_H;
    if (k > 0) {
      p = p + MAX_H - k;
    }
    Cons node = this;
    while (p >= MAX_H) {
      p = p - MAX_H;
      node = node.next;
    }// `while`
    switch (p) {
    case 0:
      return node.e0;
    case 1:
      return node.e1;
    case 2:
      return node.e2;
    case 3:
      return node.e3;
    default:
      return null;
    }// `switch`
  }

  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append("(");
    ForwardIterator<Object> it = objectIterator();
    for (int i = 0; i < length - 1; i++) {
      Object item = it.next();
      if (item == null)
        str.append("null");
      else
        str.append(item.toString());
      if (i < length - 2)
        str.append(" ");
    }// `for`
    Object item = it.next();
    if (item != null) {
      str.append(" . ");
      str.append(item.toString());
    }
    str.append(")");
    return str.toString();
  }

  public String debug() {
    StringBuilder str = new StringBuilder();
    Cons node = this;
    while (node != null) {
      str.append(node.length);
      str.append("[");
      str.append(node.e0);
      str.append(',');
      str.append(node.e1);
      str.append(',');
      str.append(node.e2);
      str.append(',');
      str.append(node.e3);
      str.append("]");
      node = node.next;
      if (node != null)
        str.append("->");
    }
    return str.toString();
  }

  private static class ConsIterator implements ForwardIterator<Object> {
    int remaining;

    Cons node;

    public ConsIterator(int remaining, Cons node) {
      super();
      this.remaining = remaining;
      this.node = node;
    }

    public boolean hasNext() {
      return remaining > 0;
    }

    public Object next() {
      if (!hasNext())
        throw new NoSuchElementException();
      int k = remaining % MAX_H;
      remaining = remaining - 1;
      switch (k) {
      case 0:
        return node.e0;
      case 1:
        Object e = node.e3;
        node = node.next;
        return e;
      case 2:
        return node.e2;
      case 3:
        return node.e1;
      }
      return null;
    }
  }// `inner class`

  final public ForwardIterator<Object> objectIterator() {
    return new ConsIterator(length, this);
  }

  final public A first() {
    return (A) getElement(0);
  }

  final public B second() {
    if (length == 2)
      return (B) getElement(1);
    else
      return (B) new Cons(this);
  }
  
  /*
   * The hash value is a lazy variable. When computed, we ensure that its value
   * is not zero, because zero indicates an uncomputed hash value. Since
   * integers are atomic and primitive, we don't need to synchronize access
   * (both, atomicity and primitiveness is needed for correctness). In the worst
   * case, two threads compute the hash value at the same time, this only occurs
   * as long both run on different processors with different local copies of
   * the hash value.
   */
  private int hash;

  /**
   * Returns the hash code. The hashcode depends on the hashcodes of elements
   * which are stored in the <i>Cons</i>-Object.
   */
  public final int hashCode() {

    if (hash != 0)
      return hash;

    int sum = 0;
    // TODO: use explicit stack instead of recursion for
    // traversal
    if (next != null)
      sum = next.hashCode();
    if (e0 != null)
      sum = sum + e0.hashCode();
    if (e1 != null)
      sum = sum + e1.hashCode();
    if (e2 != null)
      sum = sum + e2.hashCode();
    if (e3 != null)
      sum = sum + e3.hashCode();

    if (sum == 0)
      sum = -1;
    hash = sum;
    return hash;
  }

  /**
   * The <i>equals</i>-method compares two <i>Cons</i> structurally. Deep
   * structures are traversed in such way that each substructure is only visited
   * once, thus avoiding potentially exponential traversal time. Elements
   * of two different classes are always considered unequal.
   */
  final public boolean equals(Object other) {
    if (this == other)
      return true;
    
    
    if (other == null)
      return false;
    
    if (!(other.getClass() == this.getClass()))
      return false;

    Cons<A, B> b = (Cons<A, B>) other;

    if ((length != b.length) || (hashCode() != b.hashCode()))
      return false;

    Cons<A, B> a = this;

    // TODO: We should traverse the recursive structure
    //in order to avoid exponential time (for instance
    //by traversing a list where 2 pointers refer to the
    //next element).

    
    while (a != null) {
      if (a.getClass() != b.getClass())
         return false;
      if (!elementEquals(a.e0, b.e0))
        return false;
      if (!elementEquals(a.e1, b.e1))
        return false;
      if (!elementEquals(a.e2, b.e2))
        return false;
      if (!elementEquals(a.e3, b.e3))
        return false;

      a = a.next;
      b = b.next;
    }// `while`
    return true;
  }

  final private boolean elementEquals(Object a, Object b) {
    if (a == b)
      return true;
    if ((a == null) || (b == null))
      return false;
    if (a.getClass() != b.getClass())
      return false;
    
    return a.equals(b);
  }
}//`class`
