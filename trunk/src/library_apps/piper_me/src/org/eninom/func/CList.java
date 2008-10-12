package org.eninom.func;

import org.eninom.seq.Seq;
//! Core List Implementation
/*<literate>*/
/**
 * This is a list class with Yacas semantics of lists. That means,
 * mutual sharing of sublists is possible, and destructive updates
 * to those sublists will affect all participating lists. This
 * implementation is optimized for vector processing. Lists obtained
 * from an array will retain the array structure unless tailing
 * occurs. Once the tail of a list is obtained, the array structure
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
 * \item implement tail(i)
 * \item add lazy elements
 * \item add iterator and seq (seq in a way that does not destroy
 * subsequent order in memory)
 * \end{enumerate}
 */
public final class CList implements Seq<Object> { 
  
	private Object first;
	private volatile Object rest;
	
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
  public static final CList emptyList = new CList(null); 
  /**
   * Constructor for list with exactly one element:
   */
  private CList(Object v) {
  	first = v;
  	rest = emptyList;
  }
  
  /**
   * Constructor for appending one element at front of a list:
   */
  public CList(Object v, Object rest) {
  	first = v;
    if (rest == null)
      this.rest = emptyList;
    else
      this.rest = rest;
  }
  
  /**
   * Constructor for appending a subarray at front of a list:
   */
  public CList(Object[] arr, int pos, int length , Object rest) {
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
        Object e = arr[i];
        if (e.getClass() == Lazy.class) {
          Lazy lazy = (Lazy) e;
          if (!lazy.blackhole())
            e = lazy.value();
        }
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
   * so that modifications of the sublists will be shared. Note that
   * subsequent tailing of the list leads to a degenerated random access
   * time for the original(!) list. Lists that are used as vectors should
   * never be tailed, and not be exported to code that does.
   */
  public CList rest() {
  	if (!isChunk(rest)) {
      if (rest.getClass() == Lazy.class)
        return (CList)((Lazy) rest).value();
  		return (CList) rest;
    }
  	else {
      splitFirstFromTail();
      return (CList) rest;
  	}//`else`
  }
  
  /**
   * result is the same as tail().tail()... /<i>n</i>times), except
   * that only the affected parts of the list are restructured.
   * 
   * TODO
   */
  public CList rest(int n) {
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
      rest = new CList(chunk, m+1, N-m+1, rest);
      N = m-1;
      m = m / 2;
    }//`while`
    this.rest = new CList(chunk[2],rest);
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
  public Object first() {
    return get(0);
  }
  
  /**
   * Get the value at position <i>i</i>.
   */
  public Object get(int i) {
    CList list = this;
    while (!list.isEmpty()) {
      if (i == 0) {
        if (list.first.getClass() == Lazy.class)
          return ((Lazy) list.first).value();
        else
          return list.first;
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
            return ((Lazy) e).value();
          else
            return e;
        }
        i = i - chunk.length-1;
      }//`else`
    }//`while`
    throw new IndexOutOfBoundsException();
  }
  
  /**
   * Destructively set element at position <i>i</i> to value 
   * <i>e</i>. The old value is returned.
   */
  public Object set(int i, Object e) {
    CList list = this;
    while (!list.isEmpty()) {
      if (i == 0) {
        Object x = list.first;
        list.first = e;
        return x;
      }//` if`
      Object restLocalCopy = list.rest;
      if (!isChunk(restLocalCopy)) {
        list = (CList) restLocalCopy;
        i = i - 1;
      } else {
        Object[] chunk = (Object[]) restLocalCopy;
        list = (CList) chunk[1];
        if(i <= chunk.length) {
          Object x = chunk[i];
          chunk[i+1] = e;
          return x;
        }//`if`
        i = i - chunk.length-1;
      }//`else`
    }//`while`
    throw new IndexOutOfBoundsException();
  }

  
  /*
   * the test program's performance depends heavily on tuning
   * Java's garbage collection (sometimes factor 10 slower!).
   * With \\
   * \\
   * -ms90M -mx90M -XX:NewSize=80M -server \\
   * \\
   * I had fast and stable througput on a 2-core processor.
  */
  static Function<Long,CList> listRest = new Function<Long,CList>(){
    
   private static final int chunk_size = 16;
   public CList get(Long n) {
      Lazy<Long,CList> C = new Lazy<Long,CList>(listRest,n+chunk_size);
      Object[] arr = new Object[chunk_size];
      for (int i = 0; i < chunk_size; i++)
        arr[i] = n+i;
      
      return new CList(arr, 0, chunk_size, C);
    }
  
  };
  
  static class Printer extends Thread {
    
    public void run() {
      CList numbers = listRest.get(0L);
  
    while (true) {
      long time = System.currentTimeMillis();
      for (int i = 0; i < 10000000; i++) {
        numbers = numbers.rest();
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
