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

package org.eninom.collection.arrays;

import org.eninom.algorithm.Comparator;
import org.eninom.collection.*;
import org.eninom.func.Function;
import org.eninom.iterator.ForwardIterator;
import org.eninom.seq.Seq;
import org.eninom.seq.SeqFromIterator;

// ! Primitive Arrays
/* <literate> */
/**
 * Routines for creating and copying immutable primitive arrays. The immutable
 * arrays are wrappers around standard arrays and take care of the necessary
 * preventive copying. Note that the immutability of an array does not imply
 * the immutability of its elements.
 */
public final class PrimitiveArrays {
  /**
   * This class has no instances.
   */
  private PrimitiveArrays() {
    
      }
  
  static private abstract class AbstractPrimitiveArray<E> implements
      ImmutableCollection<E>, Function<Long, E>, List<E> {
    public final E get(Long i) {
      return boxedValue(i);
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
     
    public final E get(long i) {
      return boxedValue(i);
    }

    protected abstract E boxedValue(long i);
    protected abstract int hashValueOf(long i);

    public final ForwardIterator<E> iterator() {
      return new List.RandomAccessIterator<E>(this);
    }

    public final Seq<E> seq() {
      return SeqFromIterator.create(iterator());
    }
    
    public boolean contains(E e) {
      return Collections.contains(this, e);
    }
  }// `inner class`

  private static final class MyDoubleArray extends
      AbstractPrimitiveArray<Double> implements DoubleArray {
    
    private final double[] arr;

    public MyDoubleArray() {
      arr = new double[0];
    }

    public MyDoubleArray(double x) {
      arr = new double[1];
      arr[0] = x;
    }

    public MyDoubleArray(double x0, double x1) {
      arr = new double[2];
      arr[0] = x0;
      arr[1] = x1;
    }

    public MyDoubleArray(double x0, double x1, double x2) {
      arr = new double[3];
      arr[0] = x0;
      arr[1] = x1;
      arr[2] = x2;
    }

    public MyDoubleArray(double x0, double x1, double x2,
        double x3) {
      arr = new double[4];
      arr[0] = x0;
      arr[1] = x1;
      arr[2] = x2;
      arr[3] = x3;
    }

    public MyDoubleArray(double[] arr) {
      this.arr = arr.clone();
    }

    public MyDoubleArray(IterableCollection<Double> values) {
      this.arr = new double[(int) values.size()];
      int i = 0;
      ForwardIterator<Double> it = values.iterator();
      while (it.hasNext()) {
        arr[i] = it.next();
        i = i + 1;
      }// `while`
    }

    public long size() {
      return arr.length;
    }

    @Override
    protected Double boxedValue(long i) {
      return arr[(int) i];
    }
    
    @Override
    protected int hashValueOf(long i) {
      long v = Double.doubleToLongBits(arr[(int) i]);
      return (int)(v^(v>>>32));
    }

    public double at(long i) {
      return arr[(int) i];
    }

    public double[] asMutableArray() {
      return arr.clone();
    }
  }// `inner class`

  private static final MyDoubleArray emptyDoubleArray = new MyDoubleArray();
  
  public static DoubleArray emptyDoubleArray() {
    return emptyDoubleArray;
  }

  /**
   * creates an immutable array with a single element
   */
  public static DoubleArray single(double x) {
    return new MyDoubleArray(x);
  }

  /**
   * creates an immutable array with 2 elements
   */
  public static DoubleArray pair(double x0, double x1) {
    return new MyDoubleArray(x0, x1);
  }

  /**
   * creates an immutable array with 3 elements
   */
  public static DoubleArray triple(double x0, double x1, double x2) {
    return new MyDoubleArray(x0, x1, x2);
  }
  
  /**
   * creates an immutable array with 4 elements
   */
  public static DoubleArray quadruple(double x0, double x1,
      double x2, double x3) {
    return new MyDoubleArray(x0, x1, x2, x3);
  }

  /**
   * creates an immutable array from a mutable array. The mutable array is copied. 
   */
  public static DoubleArray fromArray(double[] values) {
    if (values.length == 0)
      return emptyDoubleArray;
    return new MyDoubleArray(values);
  }
  
  /**
   * creates an immutable array from a collection. If the collection was created by the
   * primitive array class, the method returns its argument. 
   */
  public static DoubleArray fromCollection(
      IterableCollection<Double> values) {
    if (values.getClass() == MyDoubleArray.class) {
      return (MyDoubleArray) values;
    } else {
      if (values.size() == 0)
        return emptyDoubleArray;
      return new MyDoubleArray(values);
    }
  }

  private static final class MyIntegerArray extends
      AbstractPrimitiveArray<Integer> implements IntegerArray {
    private final int[] arr;

    public MyIntegerArray() {
      arr = new int[0];
    }

    public MyIntegerArray(int x) {
      arr = new int[1];
      arr[0] = x;
    }

    public MyIntegerArray(int x0, int x1) {
      arr = new int[2];
      arr[0] = x0;
      arr[1] = x1;
    }

    public MyIntegerArray(int x0, int x1, int x2) {
      arr = new int[3];
      arr[0] = x0;
      arr[1] = x1;
      arr[2] = x2;
    }

    public MyIntegerArray(int x0, int x1, int x2, int x3) {
      arr = new int[4];
      arr[0] = x0;
      arr[1] = x1;
      arr[2] = x2;
      arr[3] = x3;
    }

    public MyIntegerArray(int[] arr) {
      this.arr = arr.clone();
    }

    public MyIntegerArray(IterableCollection<Integer> values) {
      this.arr = new int[(int) values.size()];
      int i = 0;
      ForwardIterator<Integer> it = values.iterator();
      while (it.hasNext()) {
        arr[i] = it.next();
        i = i + 1;
      }// `while`
    }

    public long size() {
      return arr.length;
    }

    @Override
    protected Integer boxedValue(long i) {
      return arr[(int) i];
    }
    
    @Override
    protected int hashValueOf(long i) {
      return arr[(int) i];
    }

    public int at(long i) {
      return arr[(int) i];
    }

    public int[] asMutableArray() {
      return arr.clone();
    }
  }// `inner class`

  private static final MyIntegerArray emptyIntegerArray = new MyIntegerArray();

  public static IntegerArray emptyIntegerArray() {
    return emptyIntegerArray;
  }
  
  /**
   * creates an immutable array with a single element
   */
  public static IntegerArray single(int x) {
    return new MyIntegerArray(x);
  }

  /**
   * creates an immutable array with 2 elements
   */
  public static IntegerArray pair(int x0, int x1) {
    return new MyIntegerArray(x0, x1);
  }

  /**
   * creates an immutable array with 3 elements
   */
  public static IntegerArray triple(int x0, int x1, int x2) {
    return new MyIntegerArray(x0, x1, x2);
  }

  /**
   * creates an immutable array with 4 elements
   */
  public static IntegerArray quadruple(int x0, int x1, int x2, int x3) {
    return new MyIntegerArray(x0, x1, x2, x3);
  }

  /**
   * creates an immutable array from a mutable array. The mutable array is copied. 
   */
  public static IntegerArray fromArray(int[] values) {
    if (values.length == 0)
      return emptyIntegerArray;
    return new MyIntegerArray(values);
  }

  /**
   * creates an immutable array from a collection. If the collection was created by the
   * primitive array class, the method returns its argument. 
   */
  public static IntegerArray fromCollection(
      IterableCollection<Integer> values) {
    if (values.getClass() == MyIntegerArray.class) {
      return (MyIntegerArray) values;
    } else {
      if (values.size() == 0)
        return emptyIntegerArray;
      return new MyIntegerArray(values);
    }
  }

  private static final class MyByteArray extends
      AbstractPrimitiveArray<Byte> implements ByteArray {
        
    private final byte[] arr;

    public MyByteArray() {
      arr = new byte[0];
    }

    public MyByteArray(byte x) {
      arr = new byte[1];
      arr[0] = x;
    }

    public MyByteArray(byte x0, byte x1) {
      arr = new byte[2];
      arr[0] = x0;
      arr[1] = x1;
    }

    public MyByteArray(byte x0, byte x1, byte x2) {
      arr = new byte[3];
      arr[0] = x0;
      arr[1] = x1;
      arr[2] = x2;
    }

    public MyByteArray(byte x0, byte x1, byte x2, byte x3) {
      arr = new byte[4];
      arr[0] = x0;
      arr[1] = x1;
      arr[2] = x2;
      arr[3] = x3;
    }

    public MyByteArray(byte[] arr) {
      this.arr = arr.clone();
    }

    public MyByteArray(IterableCollection<Byte> values) {
      this.arr = new byte[(int) values.size()];
      int i = 0;
      ForwardIterator<Byte> it = values.iterator();
      while (it.hasNext()) {
        arr[i] = it.next();
        i = i + 1;
      }// `while`
    }

    public long size() {
      return arr.length;
    }

    @Override
    protected Byte boxedValue(long i) {
      return arr[(int) i];
    }
    
    @Override
    protected int hashValueOf(long i) {
      return arr[(int) i];
    }

    public byte at(long i) {
      return arr[(int) i];
    }

    public byte[] asMutableArray() {
      return arr.clone();
    }
  }// `inner class`

  private static final MyByteArray emptyByteArray = new MyByteArray();
  
  public static ByteArray emptyByteArray() {
    return emptyByteArray;
  }
  
    
  /**
   * creates an immutable array with a single element
   */
  public static ByteArray single(byte x) {
    return new MyByteArray(x);
  }

  /**
   * creates an immutable array with 2 elements
   */
  public static ByteArray pair(byte x0, byte x1) {
    return new MyByteArray(x0, x1);
  }

  /**
   * creates an immutable array with 3 elements
   */
  public static ByteArray triple(byte x0, byte x1, byte x2) {
    return new MyByteArray(x0, x1, x2);
  }

  /**
   * creates an immutable array with 4 elements
   */
  public static ByteArray quadruple(byte x0, byte x1, byte x2,
      byte x3) {
    return new MyByteArray(x0, x1, x2, x3);
  }

  /**
   * creates an immutable array from a mutable array. The mutable array is copied. 
   */
  public static ByteArray fromArray(byte[] values) {
    if (values.length == 0)
      return emptyByteArray;
    return new MyByteArray(values);
  }

  /**
   * creates an immutable array from a collection. If the collection was created by the
   * primitive array class, the method returns its argument. 
   */
  public static ByteArray fromCollection(IterableCollection<Byte> values) {
    if (values.getClass() == MyByteArray.class) {
      return (MyByteArray) values;
    } else {
      if (values.size() == 0)
        return emptyByteArray;
      return new MyByteArray(values);
    }
  }

  private static final class MyLongArray extends
      AbstractPrimitiveArray<Long> implements LongArray {
    private final long[] arr;

    public MyLongArray() {
      arr = new long[0];
    }

    public MyLongArray(long x) {
      arr = new long[1];
      arr[0] = x;
    }

    public MyLongArray(long x0, long x1) {
      arr = new long[2];
      arr[0] = x0;
      arr[1] = x1;
    }

    public MyLongArray(long x0, long x1, long x2) {
      arr = new long[3];
      arr[0] = x0;
      arr[1] = x1;
      arr[2] = x2;
    }

    public MyLongArray(long x0, long x1, long x2, long x3) {
      arr = new long[4];
      arr[0] = x0;
      arr[1] = x1;
      arr[2] = x2;
      arr[3] = x3;
    }

    public MyLongArray(long[] arr) {
      this.arr = arr.clone();
    }

    public MyLongArray(IterableCollection<Long> values) {
      this.arr = new long[(int) values.size()];
      int i = 0;
      ForwardIterator<Long> it = values.iterator();
      while (it.hasNext()) {
        arr[i] = it.next();
        i = i + 1;
      }// `while`
    }

    public long size() {
      return arr.length;
    }

    @Override
    protected Long boxedValue(long i) {
      return arr[(int) i];
    }
    
    @Override
    protected int hashValueOf(long i) {
      long v = arr[(int) i];
      return (int)(v^(v>>>32));
    }

    public long at(long i) {
      return arr[(int) i];
    }

    public long[] asMutableArray() {
      return arr.clone();
    }
  }// `inner class`

  private static final MyLongArray emptyLongArray = new MyLongArray();

  public static LongArray emptyLongArray() {
    return emptyLongArray;
  }
  
  /**
   * creates an immutable array with a single element
   */
  public static LongArray single(long x) {
    return new MyLongArray(x);
  }

  /**
   * creates an immutable array with 2 elements
   */
  public static LongArray pair(long x0, long x1) {
    return new MyLongArray(x0, x1);
  }

  /**
   * creates an immutable array with 3 elements
   */
  public static LongArray triple(long x0, long x1, long x2) {
    return new MyLongArray(x0, x1, x2);
  }

  /**
   * creates an immutable array with 4 elements
   */
  public static LongArray quadruple(long x0, long x1, long x2,
      long x3) {
    return new MyLongArray(x0, x1, x2, x3);
  }
  
  /**
   * creates an immutable array from a mutable array. The mutable array is copied. 
   */
  public static LongArray fromArray(long[] values) {
    if (values.length == 0)
      return emptyLongArray;
    return new MyLongArray(values);
  }

  /**
   * creates an immutable array from a collection. If the collection was created by the
   * primitive array class, the method returns its argument. 
   */
  public static LongArray fromCollection(IterableCollection<Long> values) {
    if (values.getClass() == MyLongArray.class) {
      return (MyLongArray) values;
    } else {
      if (values.size() == 0)
        return emptyLongArray;
      return new MyLongArray(values);
    }
  }

  private static final class MyObjectArray<E> extends
      AbstractPrimitiveArray<E> implements ObjectArray<E> {
    private final Object[] arr;

    public E at(long i) { 
      return get(i);
    }
    
    public MyObjectArray() {
      arr = new Object[0];
    }

    public MyObjectArray(E x) {
      arr = new Object[1];
      arr[0] = x;
    }

    public MyObjectArray(E x0, E x1) {
      arr = new Object[2];
      arr[0] = x0;
      arr[1] = x1;
    }

    public MyObjectArray(E x0, E x1, E x2) {
      arr = new Object[3];
      arr[0] = x0;
      arr[1] = x1;
      arr[2] = x2;
    }

    public MyObjectArray(E x0, E x1, E x2, E x3) {
      arr = new Object[4];
      arr[0] = x0;
      arr[1] = x1;
      arr[2] = x2;
      arr[3] = x3;
    }

    MyObjectArray(Object[] arr) {
      this.arr = arr.clone();
    }
    
    public MyObjectArray(IterableCollection<? extends E> values) {
      this.arr = new Object[(int) values.size()];
      int i = 0;
      ForwardIterator<? extends E> it = values.iterator();
      while (it.hasNext()) {
        arr[i] = it.next();
        i = i + 1;
      }// `while`
    }

    public long size() {
      return arr.length;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected E boxedValue(long i) {
      return (E) arr[(int) i];
    }
    
    @Override
    protected int hashValueOf(long i) {
      if (arr[(int) i] == null)
        return 0;
      else
        return arr[(int) i].hashCode();
    }

    public Object[] asMutableArray() {
      return arr.clone();
    }
  }// `inner class`

  @SuppressWarnings("unchecked")
  private static final MyObjectArray emptyObjectArray = new MyObjectArray();
  
  @SuppressWarnings("unchecked")
  public static<E> ObjectArray<E> emptyObjectArray() {
    return (ObjectArray<E>) emptyObjectArray;
  }
  
  
  /*
   * since the object array is immutable, creation can be co-variant.
   */

  /**
   * creates an immutable array with a single element
   */
  public static <E, F extends E> ObjectArray<E> single(F x) {
    return new MyObjectArray<E>(x);
  }

  /**
   * creates an immutable array with 2 elements
   */
  public static <E, F extends E, G extends E> ObjectArray<E> pair(
      F x0, G x1) {
    return new MyObjectArray<E>(x0, x1);
  }

  /**
   * creates an immutable array with 3 elements
   */
  public static <E, F extends E, G extends E, H extends E> ObjectArray<E> triple(
      F x0, G x1, H x2) {
    return new MyObjectArray<E>(x0, x1, x2);
  }

  /**
   * creates an immutable array with 4 elements
   */
  public static <E, F extends E, G extends E, H extends E, I extends E> ObjectArray<E> quadruple(
      F x0, G x1, H x2, I x3) {
    return new MyObjectArray<E>(x0, x1, x2, x3);
  }

  /**
   * creates an immutable array from a mutable array. The mutable array is copied. 
   */
  @SuppressWarnings("unchecked")
  public static <E> ObjectArray<E> fromArray(E[] values) {
    if (values.length == 0)
      return (ObjectArray<E>) emptyObjectArray;
	    return new MyObjectArray<E>(values);
	  }

  /**
   * creates an immutable array from a collection. If the collection was created by the
   * primitive array class, the method returns its argument. 
   */
  @SuppressWarnings("unchecked")
  public static<E> ObjectArray<E> fromCollection(
      IterableCollection<? extends E> values) {
    if (values.getClass() == MyObjectArray.class) {
      return (MyObjectArray<E>) values;
    } else {
      if (values.size() == 0)
        return (ObjectArray<E>) emptyObjectArray;
      return new MyObjectArray<E>(values);
    }
  }
  
  @SuppressWarnings("unchecked")
  public static<E> ObjectArray<E> sort(ObjectArray<? extends E> a, Comparator<? super E> cmp) {
    
    if (a.size() <= 1)
      return (ObjectArray<E>) a;
       
    Object[] arr = a.asMutableArray();
    sort(arr, cmp);
    
    return new MyObjectArray<E>(arr);
  }
  
  static<E> void sort(Object[] a, Comparator<? super E> cmp) {
    if (a.length <= 1)
      return;
    
    if (a.length < 70)
      insertionSort(a, cmp);
    int n = a.length;
    int p = n  / 2;
    Object[] left = new Object[p];
    for (int i = 0; i < p; i++)
      left[i] = a[i];
    Object[] right = new Object[n - p];
    for (int i = p; i < n; i++)
      right[i-p] = a[i];
    sort(left, cmp);
    sort(right, cmp);
    mergeInto(a, left, right, cmp);
  }
  
  @SuppressWarnings("unchecked")
  static<E> void insertionSort(Object[] a, Comparator<? super E> cmp) {
    int n = a.length;
    if (n <= 1)
      return;
    for (int k = 1; k < n; k++) {
      E x = (E)a[k];
      int i;
      for (i = k-1; i >= 0; i--) {
        if (cmp.compare((E)a[i],x) > 0) {
          a[i+1] = a[i];
        }
        else {
          a[i+1] = x;
          i = -99;
          break;
        }
      }
      if (i != -99)
        a[0] = x;
    }
  }
  
  @SuppressWarnings("unchecked")
  static<E> void mergeInto(Object[] target, Object[] left, Object[] right, Comparator<? super E> cmp) {
    if (target.length != left.length + right.length) {
      throw new IllegalArgumentException("Internal Error: Array lengths do not match!");
    }
    int p1 = 0;
    int p2 = 0;
    int pt = 0;
    
    while ((p1 < left.length) && (p2 < right.length)) {
      if (cmp.compare((E)left[p1], (E)right[p2]) <= 0)
        target[pt++] = left[p1++];
      else
        target[pt++] = right[p2++];
    }
    if (p1 >= left.length) {
      left = right;
      p1 = p2;
    }
    
    while (p1 < left.length)
      target[pt++] = left[p1++];
  }
}// `class`

