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

//! Core List Implementation
/*<literate>*/
/*
 * This is a destructive list class with lazy evaluation. Its intended
 * use is the implementation of data structures that depend on lazy
 * evaluation of lists or arrays, for instance Hood-Melville-Queues.
 * Another appplication of this class is as a data structure for
 * mutable lists, with special optimization for random access.
 * If an element is set to a lazy node, its value is automatically
 * computed when the element is retrieved. Thus the use of lazy
 * elements is tranparent for readers but some special attention
 * is necessary if a data structure needs a less eager way
 * for dealing with lazy elements.
 * <br />
 * <br />
 * Instances from this class adapt automatically to the access pattern: 
 * When instantiated from an array, the array structure is kept unless
 * the list is traversed via tailing. Each subsequent tailing will split
 * off the first list element and allocate a single node for it. This
 * process is non-reversible, so a list that has been iterated over via
 * offers no fast random access any longer.
 * <br />
 * <br />
 * This class offers low-level thread-safety under concurrent mutations.
 * More specifically, 
 * mutual sharing of sublists is possible, while destructive updates
 * to those sublists will affect all participating lists.
 */
@SuppressWarnings("unchecked")
public final class CList<E> { 
  
	private Object first;
	private volatile Object rest;
  /*
   * This class works best if used without destructive
   * updates and without tailing, since only volatile accesses to the
   * array reference is needed (see above). In many cases, the Java-VM can optimize
   * away synchronization, since in this case the array reference is
   * never mutated after initialization.
   * 
   * We define a special marker object for chunks:
   */
  
	private static final Object chunkmarker = new Object(); 
      
  /*
   * If the rest is null, the list is empty. Otherwise
   * the list's first value is stored in the value-attribute.
   * The rest of the list is either a reference to another object
   * (which can be a list), or a reference to an chunk. A
   * chunk is an object array $c$ of $n+2$ objects. $c[0]$
   * points to the private object chunkmarker, in order to
   * distinguish chunks from any other user-created arrays.
   * For the chunk $c$, $c[1]$ is a reference to a sublist and
   * $c[2..n]$ are list elements. The complete list is then
   * (in order) the value-attribute plus $c[2..n+1]$ concatenated
   * with the sublist $c[1]$.
   */
  private static final CList emptyList = new CList(null);
  
  /**
   * Constructor for empty list:
   */
  static public<E> CList<E> empty() {
    return (CList<E>) emptyList;
  }
  

  /**
   * Constructor for list with exactly one element:
   */
  private CList(E v) {
  	first = v;
  	rest = emptyList;
  }
  
  /**
   * Constructor for appending a single element at front of a list:
   */
  private CList(Object v, Object rest) {
  	first = v;
    if (rest == null)
      this.rest = emptyList;
    else
      this.rest = rest;
  }
  
  /**
   * Constructor for appending a single element at front of a list:
   */
  static public<E> CList<E> append(E v, CList<E> rest) {
    return new CList(v,rest);
  }
  
  /**
   * Constructor for appending a single element at front of a list:
   */
  static public<E> CList<E> append(Lazy<E> v, CList<E> rest) {
    return new CList(v,rest);
  }
  
  /**
   * Constructor for appending a single element at front of a list:
   */
  static public<E> CList<E> append(E v, Lazy<CList<E>> rest) {
    return new CList(v,rest);
  }
  
  /**
   * Constructor for appending a single element at front of a list:
   */
  static public<E> CList<E> append(Lazy<E> v, Lazy<CList<E>> rest) {
    return new CList(v,rest);
  }
  
  /**
   * Constructor for appending a subarray at front of a list:
   */
  private CList(Object[] arr, int pos, int length , Object rest) {
  	first = arr[pos];
  	if (rest == null)
      rest = emptyList;
  	if (length > 1) {
      Object[] chunk = new Object[length+1];
      this.rest = chunk;
      chunk[0] = chunkmarker;
      chunk[1] = rest;
      int j = 2;
      for (int i = pos+1; i < pos + length; i++) {
        chunk[j++] = arr[i];
      }//`for`
    } else {
        this.rest = rest;
    }//`else`
  }
  
  /**
   * Constructor for appending a subarray at front of a list:
   */
  static public<E> CList<E> appendArray(E[] arr, int pos, int length , Lazy<CList<E>> lazyrest) {
    return new CList(arr, pos, length, lazyrest);
  }
  
  /**
   * Constructor for appending a subarray at front of a list:
   */
  static public<E> CList<E> appendArray(E[] arr, int pos, int length , CList<E> rest) {
    return new CList(arr, pos, length, rest);
  }
  
  /**
   * Constructor for appending a subarray at front of a list:
   */
  static public<E> CList<E> appendArray(Lazy<E>[] arr, int pos, int length , Lazy<CList<E>> lazyrest) {
    return new CList(arr, pos, length, lazyrest);
  }
  
  /**
   * Constructor for appending a subarray at front of a list:
   */
  static public<E> CList<E> appendArray(Lazy<E>[] arr, int pos, int length , CList<E> rest) {
    return new CList(arr, pos, length, rest);
  }
  
  /**
   * Constructor for appending an array at front of a list:
   */
  static public<E> CList<E> appendArray(E[] arr, Lazy<CList<E>> lazyrest) {
    return new CList(arr, 0, arr.length, lazyrest);
  }
  
  /**
   * Constructor for appending a subarray at front of a list:
   */
  static public<E> CList<E> appendArray(E[] arr, CList<E> rest) {
    return new CList(arr, 0, arr.length, rest);
  }
  
  /**
   * Constructor for appending a subarray at front of a list:
   */
  static public<E> CList<E> appendArray(Lazy<E>[] arr, Lazy<CList<E>> lazyrest) {
    return new CList(arr, 0, arr.length, lazyrest);
  }
  
  /**
   * Constructor for appending a subarray at front of a list:
   */
  static public<E> CList<E> appendArray(Lazy<E>[] arr, CList<E> rest) {
    return new CList(arr, 0, arr.length, rest);
  }

 
  /**
   * test for empty list
   */
  public boolean isEmpty() { 
    return this == emptyList; 
   } 
    
  private final boolean isChunk(final Object c) {
    if(c.getClass() != Object[].class)
      return false;
      Object[] arr = (Object[]) c;
      if (arr.length < 1)
        return false;
      Object e = arr[0];
      return (e == chunkmarker);
  }
  /**
   * returns the tail of the list. If necessary, the list is restructured
   * so that modifications of the sublists will be shared.
   */
  public CList<E> rest() {
  	if (!isChunk(rest)) {
      if (rest.getClass() == Lazy.class)
        return (CList<E>)((Lazy) rest).value();
  		return (CList<E>)rest;
    }
  	else {
      splitFirstFromTail();
      return (CList<E>) rest;
  	}//`else`
  }
  
  /**
   * result is the same as rest().rest()... /<i>n</i>times), except
   * that only the affected parts of the list are restructured.
   * 
   * TODO
   */
  public CList rest(int n) {
    //TODO
    return null;
  }

  /**
   * The split method is synchronized. This reflects the user's
   * ecpectation that read-only accesses (like tailing) should
   * not need user-level synchronization during absence of writes.  
   */
  private synchronized void splitFirstFromTail() {
    Object restLocalCopy = this.rest;
    if (!isChunk(rest)) {
      return;
    }//`if`
    Object[] chunk = (Object[]) restLocalCopy;
    /*
     * We use exponential splitting in order to obtain amortized constant
     * time for obtaining the rest of a list.
     */
    int N = chunk.length - 2;
    int m = 1;
    while (m * 2 <= N) {
      m = 2 * m;
    }//`while`
    Object rest = chunk[1];
    if (rest.getClass() == Lazy.class) {
      Lazy lazy = (Lazy) rest;
      if (!lazy.blackhole())
        rest = lazy.value();
    }
    while (m > 1) {
      rest = new CList((E[])chunk, m+1, N-m+1, rest);
      N = m-1;
      m = m / 2;
    }//`while`
    this.rest = new CList(chunk[2], rest);
  }
  
  /**
   * Prints the internal structure into a string
   */
  public String debug() {
  	CList list = this;
    StringBuffer s = new StringBuffer();
    s.append('(');
    while (!list.isEmpty()) {
      s.append(list.first.toString());
      Object restLocalCopy = list.rest;
      if (!isChunk(restLocalCopy)) {
        if (restLocalCopy.getClass() == CList.class)
      	  list = (CList) restLocalCopy;
        else {
          list = emptyList;
          s.append("."+restLocalCopy.toString());
        }
      } else {
      	Object[] chunk = (Object[]) restLocalCopy;
        for (int i = 2; i < chunk.length; i++) {
          s.append(' ');
          s.append(chunk[i].toString());
        }//`for`
        Object next = chunk[1];
        if (next.getClass() == CList.class) {
          list = (CList) next;
        } else {
          list = emptyList;
          s.append("."+next.toString());
        }//`else`
      }//`else`
      if (restLocalCopy != null)
        s.append(")->(");
    }//`while`
    s.append(')');
    return s.toString();
  }
  
  public String toString() {
    CList list = this;
    StringBuffer s = new StringBuffer();
    s.append('(');
    while (!list.isEmpty()) {
      s.append(list.first.toString());
      Object restLocalCopy = list.rest;
      if (!isChunk(restLocalCopy)) {
        if (restLocalCopy.getClass() == CList.class)
          list = (CList) restLocalCopy;
        else {
          list = emptyList;
          s.append("."+restLocalCopy.toString());
        }
      } else {
        Object[] chunk = (Object[]) restLocalCopy;
        for (int i = 2; i < chunk.length; i++) {
          s.append(' ');
          s.append(chunk[i].toString());
        }//`for`
        Object next = chunk[1];
        if (next.getClass() == CList.class) {
          list = (CList) next;
        } else {
          list = emptyList;
          s.append("."+next.toString());
        }//`else`
      }//`else`
      if (!list.isEmpty())
        s.append(' ');
    }//`while`
    s.append(')');
    return s.toString();
  }
  
  /**
   * Get the value at first position.
   */
  public E first() {
    return (E) get(0);
  }
  
  /**
   * Get the value at position <i>i</i>.
   */
  public E get(int i) {
    CList list = this;
    while (!list.isEmpty()) {
      if (i == 0) {
        if (list.first.getClass() == Lazy.class)
          return ((E)((Lazy) list.first).value());
        else
          return (E) list.first;
      }
      Object restLocalCopy = list.rest;
      if (!isChunk(restLocalCopy)) {
        list = (CList) restLocalCopy;
        i = i - 1;
      } else {
        Object[] chunk = (Object[]) restLocalCopy;
        list = (CList) chunk[1];
        if(i <= chunk.length) {
          Object e = chunk[i+1];
          if (e.getClass() == Lazy.class)
            return (E) ((Lazy) e).value();
          else
            return (E) e;
        }
        i = i - chunk.length-1;
      }//`else`
    }//`while`
    throw new IndexOutOfBoundsException();
  }
  
  /**
   * Destructively set element at position <i>i</i> to value 
   * <i>e</i>.
   */
  private void setObject(int i, Object e) {
    CList list = this;
    while (!list.isEmpty()) {
      if (i == 0) {
        list.first = e;
      }//` if`
      Object restLocalCopy = list.rest;
      if (!isChunk(restLocalCopy)) {
        list = (CList) restLocalCopy;
        i = i - 1;
      } else {
        Object[] chunk = (Object[]) restLocalCopy;
        list = (CList) chunk[1];
        if(i <= chunk.length) {
          chunk[i+1] = e;
        }//`if`
        i = i - chunk.length-1;
      }//`else`
    }//`while`
    throw new IndexOutOfBoundsException();
  }
  
  /**
   * Destructively set element at position <i>i</i> to value 
   * <i>e</i>.
   */
  public void set(int i, E e) {
    setObject(i, e);
  }
  
  /**
   * Destructively set element at position <i>i</i> to value 
   * <i>e</i>.
   */
  public void set(int i, Lazy<E> e) {
    setObject(i, e);
  }

  
  /*
   * We provide a small demonstration for lazy-evaluated lists:
   *  We define an infinite
   * list of integers, in chunks of 16 subsequent integers each.
   * 
   * 
   * On multiprocessors, the program's performance depends heavily 
   * on the tuning parameters of the garbage collector. The newsize
   * of the generational GC should be large, otherwise
   * the performance can drop dramatically (often factor 10 slower!).
   * With \\
   * \\
   * -ms390M -mx390M -XX:NewSize=250M -server \\
   * \\
   * the program performed well and with stable througput on an AMD 2-core
   * processor.
  */
  private static Function<Long,CList<Long>> listRest = new Function<Long,CList<Long>>(){
    
   private static final int chunk_size = 16;
   public CList get(Long n) {
      Lazy<CList<Long>> C = new Lazy<CList<Long>>(listRest, (Long) (n+chunk_size));
      Long[] arr = new Long[chunk_size];
      for (int i = 0; i < chunk_size; i++)
        arr[i] = n+i;
      
      return new CList<Long>(arr, 0, chunk_size, C);
    }
  
  };
  
  static class Printer extends Thread {
    
    public void run() {
      CList numbers = listRest.get(0L);
  
    while (true) {
      long time = System.currentTimeMillis();
      for (int i = 0; i < 10000000; i++) {
        numbers = (CList) numbers.rest();
      }
      System.out.println(numbers.get(0));
      System.out.println("ellapsed time (ms): "+ 
          (System.currentTimeMillis() - time) );
    }//`while`
    }
  }
  
  public static void main(String[] args) {
    new Printer().start();
    new Printer().start();
  }
}//`class`
