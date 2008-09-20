package org.mathrider.piper_me.xpiper.core;

/*
Copyright (c) Oliver Glier

Warning: The licence of this file does not imply that the project
of which it is part of has similar licence terms. 

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
//! Core List Implementation
/*<literate>*/
/**
 * This is a list class with Yacas semantics of lists. That means,
 * mutual sharing of sublists is possible, and destructive updates
 * to those sublists will affect all participating lists.
 * This implementation is optimized forcomputing with vectors. 
 * Lists obtained from an array will retain the array structure unless
 * tailing occurs. Once the tail of a list is obtained, the array structure
 * will be rearranged in such way that mutual sharing is possible.
 * That implies t split off the list's first element from the array.
 * As a result, after tailing the entire list, random access costs
 * linear time instead of constant time. This is the same as in
 * the original Yacas list implementation. The purpose of this class
 * is to provide constant time random access for vector processing
 * routines that keep their vector data local, while at the same time
 * stick with Yacas' semantics that arrays are lists. An attempt is
 * made to provide thread safety to a certain degree. The exact meaning
 * is that the restructuring of the array remains invisible to the
 * user, while the user remains responsible for the visibility of
 * destructive updates, in particular happened-before initialization
 * of elements. This class works best if used without destructive
 * updates and without tailing, since only volatile accesses to the
 * array reference is needed. In many cases, the Java-VM can optimize
 * away synchronization, since in this case the array reference is
 * never mutated after initialization.
 * \begin{enumerate} 
 * \item Generate unit tests.
 * \item Add functions.
 * \end{enumerate}
 */
public final class CoreList { 
  
	private Object value;
	private volatile Object rest;
      
  /*
   * If the rest is null, the list is empty. Otherwise
   * the list's first value is stored in the value-attribute.
   * The rest of the list is either a reference to a sublist
   * of the same type, or a reference to an array $c$ of $n+1$ objects.
   * In the latter case, $c[0]$ is a reference to a sublist and
   * $c[1..n]$ are list elements. The complete list is then
   * (in order) the value-attribute plus $c[1..n]$ concatenated
   * with the sublist $c[0]$.
   */
  public static final CoreList emptyList = new CoreList(null); 
  /**
   * Constructor for list with exactly one element:
   */
  private CoreList(Object v) {
  	value = v;
  	rest = emptyList;
  }
  
  /**
   * Constructor for appending one element at front of a list:
   */
  private CoreList(Object v, CoreList rest) {
  	value = v;
    if (rest == null)
      this.rest = emptyList;
    else
      this.rest = rest;
  }
  
  /**
   * Constructor for appending a subarray at front of a list:
   */
  private CoreList(Object[] arr, int pos, int length , CoreList rest) {
  	value = arr[pos];
  	if (rest == null)
      rest = emptyList;
  	if (length > 1) {
      Object[] chunk = new Object[length];
      this.rest = chunk;
      chunk[0] = rest;
      int j = 1;
      for (int i = pos+1; i < pos + length; i++) {
        chunk[j++] = arr[i];
      }//`for`
    } else {
        this.rest = rest;
    }//`else`
  }
  
  /**
   * test for empty list
   */
  public boolean isEmpty() { 
    return this == emptyList; 
   } 
  
  public CoreList push(Object a) {
    return new CoreList(a, this);
  }
  
  public CoreList push(Object[] a, int pos, int length) {
    if (length == 0)
      return this;
    
    return new CoreList(a, pos, length, this);
  }
    
  /**
   * returns the tail of the list. If necessary, the list is restructured
   * so that modifications of the sublists will be shared. Note that
   * subsequent tailing of the list leads to a degenerated random access
   * time for the original(!) list. Lists that are used as vectors should
   * never be tailed, and not be exported to code that does.
   */
  public CoreList tail() {
  	if (rest.getClass() == CoreList.class)
  		return (CoreList) rest;
  	else {
      splitFirstFromTail();
      return (CoreList) rest;
  	}//`else`
  }
  
  /**
   * result is the same as tail().tail()... /<i>n</i>times), except
   * that only the affected parts of the list are restructured.
   * 
   * TODO
   */
  public CoreList tail(int n) {
    throw new UnsupportedOperationException(); 
  }

  /**
   * The split method is synchronized. This reflects the user's
   * ecpectation that read-only accesses (like tailing) should
   * not need user-level synchronization during absence of writes.  
   */
  private synchronized void splitFirstFromTail() {
    Object restLocalCopy = this.rest;
    /*
     * Note that it it is not necessary to obtain a local copy 
     * of the reference to rest of the list. The method is synchronized
     * and no unsynchronized method modifies this refernce in the
     * current implementation. We do, however, obtain a local copy
     * to be prepared if we find out that synchronizing read access is
     * up to the user and we drop the method's synchronized modifier
     * in favor of efficiency.\\ 
     *\\
     * If he chunk points to a proper list, nothing needs to be done.
     */
    if (restLocalCopy.getClass() == CoreList.class) {
      return;
    }//`if`
    Object[] chunk = (Object[]) restLocalCopy;
    /*
     * We use logarithmic splitting in order to obtain amortized constant
     * time for obtaining the rest of a list.
     */
    int N = chunk.length - 1;
    int m = 1;
    while (m * 2 <= N) {
      m = 2 * m;
    }//`while`
    CoreList rest = (CoreList) chunk[0];
    while (m > 1) {
    rest = new CoreList(chunk, m, N-m+1,  (CoreList) rest);
    N = m-1;
    m = m / 2;
    }//`while`
    this.rest = new CoreList(chunk[1],rest);
  }
  
  /**
   * Prints the internal structure into a string
   */
  public String debug() {
  	CoreList list = this;
    StringBuffer s = new StringBuffer();
    s.append('(');
    while (!list.isEmpty()) {
      s.append(list.value.toString());
      Object restLocalCopy = list.rest;
      if (restLocalCopy.getClass() == CoreList.class)
      	list = (CoreList) restLocalCopy;
      else {
      	Object[] chunk = (Object[]) restLocalCopy;
      	list = (CoreList) chunk[0];
        for (int i = 1; i < chunk.length; i++) {
          s.append(' ');
          s.append(chunk[i].toString());
        }//`for`
      }//`else`
      if (restLocalCopy != null)
        s.append(")->(");
    }//`while`
    s.append(')');
    return s.toString();
  }
  
  public String toString() {
    CoreList list = this;
    StringBuffer s = new StringBuffer();
    s.append('(');
    while (!list.isEmpty()) {
      s.append(list.value.toString());
      Object restLocalCopy = list.rest;
      if (restLocalCopy.getClass() == CoreList.class)
        list = (CoreList) restLocalCopy;
      else {
        Object[] chunk = (Object[]) restLocalCopy;
        list = (CoreList) chunk[0];
        for (int i = 1; i < chunk.length; i++) {
          s.append(' ');
          s.append(chunk[i].toString());
        }//`for`
      }//`else`
      if (!list.isEmpty())
        s.append(' ');
    }//`while`
    s.append(')');
    return s.toString();
  }
  
  /**
   * Get the value at position <i>i</i>.
   */
  public Object get(int i) {
    CoreList list = this;
    while (!list.isEmpty()) {
      if (i == 0) return list.value;
      Object restLocalCopy = list.rest;
      if (restLocalCopy.getClass() == CoreList.class) {
        list = (CoreList) restLocalCopy;
        i = i - 1;
      } else {
        Object[] chunk = (Object[]) restLocalCopy;
        list = (CoreList) chunk[0];
        if(i < chunk.length) {
          return chunk[i];
        }
        i = i - chunk.length;
      }//`else`
    }//`while`
    throw new IndexOutOfBoundsException();
  }
  
  /**
   * Destructively set element at position <i>i</i> to value 
   * <i>e</i>. The old value ist returned.
   */
  public Object set(int i, Object e) {
    CoreList list = this;
    while (!list.isEmpty()) {
      if (i == 0) {
        Object x = list.value;
        list.value = e;
        return x;
      }//` if`
      Object restLocalCopy = list.rest;
      if (restLocalCopy.getClass() == CoreList.class) {
        list = (CoreList) restLocalCopy;
        i = i - 1;
      } else {
        Object[] chunk = (Object[]) restLocalCopy;
        list = (CoreList) chunk[0];
        if(i < chunk.length) {
          Object x = chunk[i];
          chunk[i] = e;
          return x;
        }//`if`
        i = i - chunk.length;
      }//`else`
    }//`while`
    throw new IndexOutOfBoundsException();
  }

  public static void main(String[] args) {
    int N = 10;
    Integer[] arr = new Integer[N];
    for (int i = 0; i < N; i++) {
      arr[i] = new Integer(i);
    }//`for`
    CoreList c = new CoreList(arr, 0, N, emptyList);
    System.out.println(c.debug());
    for (int i = 0; i < N; i++) {
      c.set(i, new Integer(((Integer)c.get(i)).intValue()+100));
      System.out.println(c);
    }//`for`
    System.out.println(c.debug());
    CoreList d = c;
    while (!d.isEmpty()) {
      d.set(0, new Integer(((Integer)d.get(0)).intValue()+100));
      System.out.println(c);
      d = d.tail();
    }//`while`
    System.out.println(c.debug());
  }
}//`class`
