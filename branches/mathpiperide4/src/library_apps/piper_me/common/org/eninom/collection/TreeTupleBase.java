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

//Immutable Tuples Base Class
/*<literate>*/
/**
 * This class is package-private. It is used for the implementation of immutable
 * array-based data structures. A Tree-tuple impements a list of element. The
 * number of elements is fixed. Replacement of an element produces a new tuple
 * in logarithmic time. <br /> <br />
 * Typically, this base class is used for the implementation of immutable lists
 * of fixed length, or immutable hashtables or queues. The underlying implementation
 * is a balanced tree with nodes of large arity, the height of the tree is
 * small.
 */
class TreeTupleBase<E> {
  static private final int ds = 4;
  static private final int arity = 16;
    
  private final long size;
  
  private final Object t0, t1, t2, t3, t4, t5, t6, t7,
              t8, t9, t10, t11, t12, t13, t14, t15;
 
  public long adressableSize() {
    return size;
  }
  
  /**
   * constructs a tuple with a given size All elements are null. When the
   * constructor is called, actually no tree is constructed.
   */
  public TreeTupleBase(long size) {
    this.size = size;
    t0 = t1 = t2 = t3 = t4 = t5 = t6 = t7 =
    t8 = t9 = t10 = t11 = t12 = t13 = t14 = t15 = null;
    
  }
  
  private TreeTupleBase(long size, Object e0, Object e1, Object e2, Object e3,
      Object e4, Object e5, Object e6, Object e7, Object e8, Object e9, Object e10,
      Object e11, Object e12, Object e13, Object e14, Object e15) {
    this.size = size;
    t0 = e0; t1 = e1; t2 = e2; t3 = e3;
    t4 = e4; t5 = e5; t6 = e6; t7 = e7;
    t8 = e8; t9 = e9; t10 = e10; t11 = e11; 
    t12 = e12; t13 = e13; t14 = e14; t15 = e15;
  }
  
  protected E get(long i) {
    
    if ((i < 0) || (i >= size)) {
      throw new IndexOutOfBoundsException();
    }
    
    return get(this,i);
  }
  
  @SuppressWarnings("unchecked")
  static private final<E> E get(TreeTupleBase<E> tree, long i) {
    long capacity = arity;
    while (capacity < tree.size) {
      capacity = capacity << ds;
    }

    while (tree != null) {
      long k = (i * arity) / capacity;
      Object t = null;
      switch((int) k) {
        case 0: t = tree.t0; break;
        case 1: t = tree.t1; break;
        case 2: t = tree.t2; break;
        case 3: t = tree.t3; break;
        case 4: t = tree.t4; break;
        case 5: t = tree.t5; break;
        case 6: t = tree.t6; break;
        case 7: t = tree.t7; break;
        case 8: t = tree.t8; break;
        case 9: t = tree.t9; break;
        case 10: t = tree.t10; break;
        case 11: t = tree.t11; break;
        case 12: t = tree.t12; break;
        case 13: t = tree.t13; break;
        case 14: t = tree.t14; break;
        case 15: t = tree.t15; break;
      }//`switch`
        if ((capacity == arity) || (t == null)) {
          return (E)t;
        } else {
          tree = (TreeTupleBase<E>)t;
          capacity = capacity >> ds;
          i = i - k * capacity;
        }//`else`
      }//`while`
      return null;
      
  }
  
  /*
   * constructs a new tuple from another by replacing one element.
   */
  public TreeTupleBase(TreeTupleBase<E> x, long i, E e) {
    
    if ((i < 0) || (i >= x.size)) {
      throw new IndexOutOfBoundsException("Index: "+i);
    }

    size = x.size;
        
    long capacity = 1;
    while (capacity < size) {
      capacity = capacity << ds;
    }
    
    Object t = e;
    long k = (i * arity) / capacity;
    
    Object e0 = x.t0, e1 = x.t1, e2 = x.t2, e3 = x.t3, e4 = x.t4, e5 = x.t5,
    e6 = x.t6, e7 = x.t7, e8 = x.t8, e9 = x.t9, e10 = x.t10, e11 = x.t11,
    e12 = x.t12, e13 = x.t13, e14 = x.t14, e15 = x.t15;
    
    if (size > arity) {
      switch((int)k) {
      case 0: t = e0; break;
      case 1: t = e1; break;
      case 2: t = e2; break;
      case 3: t = e3; break;
      case 4: t = e4; break;
      case 5: t = e5; break;
      case 6: t = e6; break;
      case 7: t = e7; break;
      case 8: t = e8; break;
      case 9: t = e9; break;
      case 10: t = e10; break;
      case 11: t = e11; break;
      case 12: t = e12; break;
      case 13: t = e13; break;
      case 14: t = e14; break;
      case 15: t = e15; break;
      }//`switch`
      long subCapacity = capacity >> ds;
      t = replace(t, subCapacity, i - k * subCapacity, e);
    }
    
    switch((int) k) {
    case 0: e0 = t; break;
    case 1: e1 = t; break;
    case 2: e2 = t; break;
    case 3: e3 = t; break;
    case 4: e4 = t; break;
    case 5: e5 = t; break;
    case 6: e6 = t; break;
    case 7: e7 = t; break;
    case 8: e8 = t; break;
    case 9: e9 = t; break;
    case 10: e10 = t; break;
    case 11: e11 = t; break;
    case 12: e12 = t; break;
    case 13: e13 = t; break;
    case 14: e14 = t; break;
    case 15: e15 = t; break;
    }//`switch`
    t0 = e0; t1 = e1; t2 = e2; t3 = e3;
    t4 = e4; t5 = e5; t6 = e6; t7 = e7;
    t8 = e8; t9 = e9; t10 = e10; t11 = e11;
    t12 = e12; t13 = e13; t14 = e14; t15 = e15;
  }
  
  @SuppressWarnings("unchecked")
  static private Object replace(Object other, long capacity, long i, Object e) {
    
    if (capacity == 1) {
      return e;
    }
    
    Object e0 = null, e1 = null, e2 = null, e3 = null, e4 = null, e5 = null,
    e6 = null, e7 = null, e8 = null, e9 = null, e10 = null, e11 = null,
    e12 = null, e13 = null, e14 = null, e15 = null;
    
    long k = (i * arity) / capacity;
    long subCapacity = capacity >> ds;
    Object t = e;
    if (other == null) {
      t = null;
      if (e == null) {
        return null;
      }
      
    } else {
      TreeTupleBase tree = (TreeTupleBase) other;
      e0 = tree.t0; 
      e1 = tree.t1; 
      e2 = tree.t2; 
      e3 = tree.t3;
      e4 = tree.t4; 
      e5 = tree.t5; 
      e6 = tree.t6; 
      e7 = tree.t7;
      e8 = tree.t8; 
      e9 = tree.t9; 
      e10 = tree.t10; 
      e11 = tree.t11;
      e12 = tree.t12; 
      e13 = tree.t13;
      e14 = tree.t14; 
      e15 = tree.t15;
      switch((int) k) {
      case 0: t = tree.t0; break;
      case 1: t = tree.t1; break;
      case 2: t = tree.t2; break;
      case 3: t = tree.t3; break;
      case 4: t = tree.t4; break;
      case 5: t = tree.t5; break;
      case 6: t = tree.t6; break;
      case 7: t = tree.t7; break;
      case 8: t = tree.t8; break;
      case 9: t = tree.t9; break;
      case 10: t = tree.t10; break;
      case 11: t = tree.t11; break;
      case 12: t = tree.t12; break;
      case 13: t = tree.t13; break;
      case 14: t = tree.t14; break;
      case 15: t = tree.t15; break;
      }//`switch`
      
    }
    t = replace(t, subCapacity, i - k * subCapacity, e);
    
    switch((int) k) {
    
    case 0: e0 = t; break;
    case 1: e1 = t; break;
    case 2: e2 = t; break;
    case 3: e3 = t; break;
    case 4: e4 = t; break;
    case 5: e5 = t; break;
    case 6: e6 = t; break;
    case 7: e7 = t; break;
    case 8: e8 = t; break;
    case 9: e9 = t; break;
    case 10: e10 = t; break;
    case 11: e11 = t; break;
    case 12: e12 = t; break;
    case 13: e13 = t; break;
    case 14: e14 = t; break;
    case 15: e15 = t; break;
    }//`switch`
    
    if ((e == null) && (e0 == null) && (e1 == null)
        && (e2 == null) && (e3 == null) && (e4 == null)
        && (e5 == null) && (e6 == null) && (e7 == null)
        && (e8 == null) && (e9 == null) && (e10 == null) 
        && (e11 == null) && (e12 == null) && (e13 == null)
        && (e14 == null) && (e15 == null)) {
      return null;
    }
    return new TreeTupleBase(capacity, e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15);
  }
  
  
  public String debug() {
    StringBuilder str = new StringBuilder();
    str.append('[');
    str.append(size);
    long s = arity;
    int h = 0;
    while (s <= size) {
      s = s * arity;
      h = h + 1;
    }
    str.append(" t0:"); str.append(subtreeToDbgStr(t0));
    str.append(" t1:"); str.append(subtreeToDbgStr(t1));
    str.append(" t2:"); str.append(subtreeToDbgStr(t2));
    str.append(" t3:"); str.append(subtreeToDbgStr(t3));
    
    if (arity > 4) {
      str.append(" t4:"); str.append(subtreeToDbgStr(t4));
      str.append(" t5:"); str.append(subtreeToDbgStr(t5));
      str.append(" t6:"); str.append(subtreeToDbgStr(t6));
      str.append(" t7:"); str.append(subtreeToDbgStr(t7));
    }
    
    if (arity > 8) {
      str.append(" t8:"); str.append(subtreeToDbgStr(t8));
      str.append(" t9:"); str.append(subtreeToDbgStr(t9));
      str.append(" t10:"); str.append(subtreeToDbgStr(t10));
      str.append(" t11:"); str.append(subtreeToDbgStr(t11));
      str.append(" t12:"); str.append(subtreeToDbgStr(t12));
      str.append(" t13:"); str.append(subtreeToDbgStr(t13));
      str.append(" t14:"); str.append(subtreeToDbgStr(t14));
      str.append(" t15:"); str.append(subtreeToDbgStr(t15));
    }
    
    str.append(']');
    return str.toString();
  }
  
  @SuppressWarnings("unchecked")
  private String subtreeToDbgStr(Object t) {
    if (t == null) {
      return null;
    }
       
    if (!(t instanceof TreeTupleBase)) {
      return("!"+t);
    } else {
      return ((TreeTupleBase) t).debug();
    }
  }
  
  protected Object[] asMutableArray() {
    if (size > Integer.MAX_VALUE)
      throw new IllegalStateException("tuple too large: Cannot cast size() to int");
    Object[] arr = new Object[(int) size];
    for (int i = 0; i < size; i++) {
      arr[i] = this.get(i);
    }
    return arr;
  }
}//`class`
