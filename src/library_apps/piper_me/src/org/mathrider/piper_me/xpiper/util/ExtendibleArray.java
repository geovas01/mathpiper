package org.mathrider.piper_me.xpiper.util;

/*
 Copyright (c) Oliver Glier
 
 Atention: This file might be shipped with sources with very different
 licences.
 
 Redistribution and use in source and binary forms, with or without 
 modification, are permitted provided that the following conditions 
 are met:

 1. Redistributions of source code must retain the above copyright notice, 
    this list of conditions and the following disclaimer.
 2. Neither the name of ist authors or of its contributors may be used to 
    endorse or promote products derived from this software without specific
    prior written permission.

 THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 THE REGENTS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
//! Extendible Array
/*<literate>*/
/**
 * Class for a both-sided extendible array. All operations run in constant time.
 * The class can be used for mutable stacks, queues, and deques with random
 * access.<br/> <br/> The implementation maintains two arrays, A and B, which
 * are used as round buffers. B has always twice the size of A. The queue is
 * splitted into three sequences: the first part is in B, the second in A, the
 * third again in B exactly opposite to the first part. This scheme allows us to
 * copy no more than one element per update.
 */
@SuppressWarnings("unchecked")
public class ExtendibleArray<E> {
  /*
   * N is the size of the small array A NTwice is the size of the large array B
   */
  int NTwice, N;

  Object A[], B[];

  /*
   * The masks for quickly computing the remainders of N and NTwice,
   * respectively
   */
  int maskN, maskNTwice;

  /*
   * the indizes for maintaining the queues:
   */
  int p0, q0, p1, q1, p2, q2;

  /*
   * size of the first queue (this is the one in B!)
   */
  int b;

  /*
   * the initial capacity ( must be a power of 2):
   */
  static final int INITIAL_CAPACITY = 32;

  /**
   * debug output of internal structure:
   */
  public void display() {
    System.out.print("Size=" + size());
    System.out.print("  p0=" + p0);
    System.out.print("  q0=" + q0);
    System.out.print("  p1=" + p1);
    System.out.print("  p1=" + q1);
    System.out.print("  p2=" + p2);
    System.out.print("  q2=" + q2);
    System.out.println("  b=" + b);
    System.out.print("Array A=");
    printSubarray(A);
    System.out.print("Array B=");
    printSubarray(B);
  }

  protected void printSubarray(Object[] A) {
    if (A != null) {
      System.out.print("(");
      for (int i = 0; i < A.length; i++) {
        if (i > 0)
          System.out.print(",");
        if (A[i] != null)
          System.out.print(A[i].toString());
        else
          System.out.print("*");
      }
      System.out.println(")");
    } else
      System.out.println("*");
  }

  private void init() {
    NTwice = INITIAL_CAPACITY;
    N = NTwice / 2;
    maskN = N - 1;
    maskNTwice = NTwice - 1;
    /*
     * if initialCapacity==NTwice then the small array 0 will always be
     * non-existent.
     */
    A = null;
    B = new Object[NTwice];
    b = 0;
    p1 = q1 = p0 = q0 = p2 = q2 = 0;
  }

  public ExtendibleArray() {
    init();
  }

  public ExtendibleArray(int size) {
    init();
    for (int i = 0; i < size; i++)
      addLast(null);
  }

  public final int size() {
    if (NTwice == INITIAL_CAPACITY)
      return b;
    else
      return b + N;
  }

  public final E get(int i) {
    if ((i < 0) || (i >= size()))
      throw new java.lang.IndexOutOfBoundsException();
    if ((i < b) || (i >= N))
      return (E) B[(i + p0) & maskNTwice];
    else
      return (E) A[(i + p1 - b) & maskN];
  }

  public final E set(int i, E x) {
    if ((i < 0) || (i >= size()))
      throw new java.lang.IndexOutOfBoundsException();
    if ((i < b) || (i >= N)) {
      int k = (i + p0) & maskNTwice;
      Object old = B[k];
      B[k] = x;
      return (E) old;
    } else {
      int k = (i + p1 - b) & maskN;
      Object old = A[k];
      A[k] = x;
      return (E) old;
    }
  }
  
  public final void addFirst(E x) {
    if (NTwice != INITIAL_CAPACITY) {
      /*
       * move one item from A to B:
       */
      q1--;
      q1 &= maskN;
      p2--;
      p2 &= maskNTwice;
      B[p2] = A[q1];
      A[q1] = null;
    }
    /*
     * add item at front:
     */
    p0--;
    p0 &= maskNTwice;
    B[p0] = x;

    b++;
    if (p0 == q2)
      /*
       * B is full => A is empty We make B to A and create empty B
       */
      makeNewB();
  }

  public final void addLast(E x) {
    if (NTwice != INITIAL_CAPACITY) {
      /*
       * move one item from A to B
       */
      B[q0++] = A[p1];
      A[p1++] = null;
      q0 &= maskNTwice;
      p1 &= maskN;
    }
    /**
     * add item at tail:
     */
    B[q2++] = x;
    q2 &= maskNTwice;
    b++;

    if (p0 == q2)
      /*
       * B is full => A is empty make B to A and create empty B
       */
      makeNewB();
  }

  public final boolean add(E x) {
    addLast(x);
    return true;
  }

  private void makeNewB() {
    N = NTwice;
    NTwice = 2 * NTwice;
    maskN = N - 1;
    maskNTwice = NTwice - 1;
    A = B;
    B = new Object[NTwice];
    b = 0;
    p1 = q1 = p0;
    p0 = q0 = 0;
    p2 = q2 = N;
  }

  public final E removeFirst() {
    if (size() <= 0)
      throw new java.util.NoSuchElementException();
    if (NTwice != INITIAL_CAPACITY) {
      if (b == 0)
        /*
         * B is empty => A is full make A to B and create empty A
         */

        makeNewA(); // 

      if (NTwice != INITIAL_CAPACITY) {
        /*
         * move one item from B to A
         */
        A[q1++] = B[p2];
        B[p2++] = null;
        q1 &= maskN;
        p2 &= maskNTwice;
      }
      /*
       * remove item from front
       */
      b--;
      Object x = B[p0];
      B[p0++] = null;
      p0 &= maskNTwice;
      return (E) x;
    } else if (p0 != q2) {
      /*
       * remove item from front
       */
      b--;
      Object x = B[p0];
      B[p0++] = null;
      p0 &= maskNTwice;
      return (E) x;
    } else
      return null;
  }

  public final E removeLast() {
    if (size() <= 0)
      throw new java.util.NoSuchElementException();
    if (NTwice != INITIAL_CAPACITY) {
      if (b == 0)
        /*
         * B is empty => A is full. make A to B and create empty A:
         */
        makeNewA();

      if (NTwice != INITIAL_CAPACITY) {
        /*
         * move one item from B to A
         */
        p1--;
        p1 &= maskN;
        q0--;
        q0 &= maskNTwice;
        A[p1] = B[q0];
        B[q0] = null;
      }
      /*
       * remove item from tail
       */
      b--;
      q2--;
      q2 &= maskNTwice;
      Object x = B[q2];
      B[q2] = null;
      return (E) x;
    } else if (p0 != q2) {
      /*
       * remove item from tail
       */
      b--;
      q2--;
      q2 &= maskNTwice;
      Object x = B[q2];
      B[q2] = null;
      return (E) x;
    } else
      return null;
  }

  private void makeNewA() {
    /**
     * make A to B and create empty A
     */
    NTwice = N;
    N = N / 2;
    maskN = N - 1;
    maskNTwice = NTwice - 1;
    B = A;
    p0 = q2 = p1;
    q0 = p2 = (p1 + N) % NTwice;
    p1 = q1 = 0;

    if (NTwice != INITIAL_CAPACITY) {
      A = new Object[N];
      b = N;
    } else {
      A = null;
      b = NTwice;
    }
  }

  /**
   * copy to standard array:
   */
  public final Object[] toArray() {
    int n = this.size();
    Object[] A = new Object[n];
    for (int i = 0; i < n; i++)
      A[i] = get(i);
    return A;
  }
  
  public void insert(int i, E x) {
    if (i == size()) {
      add(x);
      return;
    }
    int n = size()-1;
    Object tmp = get(n);
    addLast((E)tmp);
    for (int j = n; j > i; j--)
      set(j,get(j-1));
    set(i,x);
  }
  
}// `class`
