package org.mathrider.piper_b.j2me;

/*  Implements a bothsided extendible array. This is an array in which 
	    element access takes time O(1), as well as adding and removing new 
	    elements at first/last position.
	 
	  Memory requirement can be up to 5 additional (references to) items 
	  per item, but is better on average. It is also relatively easy to 
	  avoid this memory overhead by another level of indirection 
	  (using buckets).

	  It is, in general, also not possible to use this class for
	  stacking the call frames in the runtime system of a functional 
	  programming language. For instance, if you have closures, 
	  stacks should be persistent. You can, however, use this
	  stack for stacking program counters etc. of a byte code 
	  interpreter.
	 
	  ----
	 
	  This class is only marginally slower than ArrayList<T> of the
	  Java API. Its main advantages are:
	 
	  () bothsided extendible
	  () guaranteed O(1) worstcase performance (except memory allocation), 
	    which is important for interactive or multithreaded or distributed 
	    enviroments
	  () portable to other imperative languages

	 Notes on threadsafety:
	 
	   1.Access to mutable static data is synchronized.
	   2.Instances of ExtendibleArray are *not* synchronized.
	   3.Except for 2 (shared instances) -- which needs 
	     explicit synchronization -- the code here is threadsafe
	 
	 If you port this Code to C++, you must decide first wether it
	 should be more Java like (use polymorphism and a base object),
	 or more like STL. (STL already provides a data structure with
	 similar functionality).
	 */


	/* Changelog
	26-29.6.05 : -added conversion to plain arrays
	             -added cleanup to array pooling
	*/

	public class ExtendibleArray {
//	 N is the size of the small array A
//	 NTwice is the size of the large array B
	  int NTwice,N;
	  
	  Object A[], B[];

//	 The masks for quickly computing the remainders
//	 of N and NTwice, respectively
	  int maskN, maskNTwice;

//	 the indizes for maintaining the queues:
	  int p0,q0,p1,q1,p2,q2;

//	 size of the first queue (this is the one in B!)
	  int b;

//	the initial capacity ( must be a power of 2):
	  static final int INITIAL_CAPACITY = 32; 


//	========== Debugging and Memory Management : ==========

	 /*
	   The implementation maintains two arrays, A and B, which
	   are used as round buffers. B has always twice the size
	   of A. The queue is splitted into three sequences: the
	   first part is in B, the second in A, the third again in
	   B exactly opposite to the first part. This scheme allows
	   us to copy no more than one element per update.
	  */

//	 debug output of internal structure:

	  public void display()
	  {
	      System.out.print("Size="+size());
	      System.out.print("  p0="+p0);
	      System.out.print("  q0="+q0);
	      System.out.print("  p1="+p1);
	      System.out.print("  p1="+q1);
	      System.out.print("  p2="+p2);
	      System.out.print("  q2="+q2);
	      System.out.println("  b="+b);
	      System.out.print("Array A=");
	      printSubarray(A);
	      System.out.print("Array B=");
	      printSubarray(B);
	  }

	  protected void printSubarray(Object[] A)
	  {
	      if (A != null)
	      {
	        System.out.print("(");
	        for (int i = 0; i < A.length; i++)
		{
		    if (i>0) System.out.print(",");
		    if (A[i]!=null)
	             System.out.print(A[i].toString());
	            else System.out.print("*");  
		}
		System.out.println(")");
	      }
	      else System.out.println("*"); 
	  }

	 // memory management:

	 // we always keep a small number (cache_size) of released arrays
	 // in order to quickly reclaim them again.

	    private static final int POOLSIZE = 3;
	    private static final int POOL_MAX_ARRAYSIZE = 100;
	    private static final int POOL_MIN_ARRAYSIZE = 30;
	    private static int pool_free_candidate = 0;
	    private static Object[][] pool = null;

	    private static synchronized Object[] claimArray(int size)
	    {
		//(only arrays which's size is within the range are in the pool)
		if  ((size > POOL_MAX_ARRAYSIZE) ||
		     (size < POOL_MIN_ARRAYSIZE))
		    return new Object[size];

		// create pool if non-existent:
	        if (pool == null)
		    pool = new Object[POOLSIZE][];

	        // find appropriate array in pool:
	        for (int i = 0; i < POOLSIZE; i++)
		    if ((pool[i]!=null)&&(pool[i].length==size))
		    {
	                Object[] p = pool[i];
	                pool[i] = null;
	                pool_free_candidate = i;
	                return p;
		    }
			
	        // no appropriate array was found in the pool,
	        //--> create new one:
	    
		return new Object[size];
	     }
	    
	     private static synchronized void releaseArray(Object[] arr)
	    {
		if  ((arr.length > POOL_MAX_ARRAYSIZE) ||
		     (arr.length < POOL_MIN_ARRAYSIZE))
		    return; // do nothing, garbage collector will take care of it
		
		// create pool if non-existent:
	        if (pool == null)
		      pool = new Object[POOLSIZE][];

	        // clean array (allows GC to release objects):
	        /*this is only important in GC languages. Also, if any
	          destructors/finalizers need to be called, the user must 
	          do this.
	        */
	        for (int i=0; i<arr.length; i++)
	            arr[i] = null;
	        
	        // we simply overwrite one of the other entries and increment
	        // the pool_free_index by one. Better strategies might be
		// employed in future.
	        pool[pool_free_candidate] = arr;
	        pool_free_candidate = (pool_free_candidate + 1) % POOLSIZE;
	    }


//	========== Initialization and Construction : ==========

	  private void init()
	  {
		 NTwice =  INITIAL_CAPACITY;
		 N = NTwice / 2;
	         maskN = N - 1;
	         maskNTwice = NTwice - 1;
		 //if initialCapacity==NTwice then the small array 0 
	         //will always be non-existent.
		 A = null;
		 B = claimArray(NTwice);
		 b = 0;
		 p1 = q1 = p0 = q0 = p2 = q2 = 0;
	  }

	  public ExtendibleArray() { init(); }
	  
	  public ExtendibleArray(int size) 
	  { init(); 
	    for (int i = 0; i<size;i++)
	      addLast(null);
	  }

	  public int size()
	  {
		 if (NTwice == INITIAL_CAPACITY) return b;
		 else return b + N;
	  }

	  public Object get(int i)
	  {
	    if ((i < 0) || (i >= size()))
	               throw new java.lang.IndexOutOfBoundsException();
	    if ((i < b) || (i >= N))
	      return B[(i + p0) & maskNTwice];
	    else
	      return  A[(i + p1 - b) & maskN];
	  }

	  public Object set(int i, Object x)
	  {
	        if ((i < 0) || (i >= size()))
	               throw new java.lang.IndexOutOfBoundsException();
		if ((i < b) || (i >= N))
		{ 
		     int k = (i + p0) & maskNTwice; 
	             Object old =  B[k];
	             B[k] = x;
	             return old;
	         }
		 else
	         {  
	           int k = (i + p1 - b) & maskN;
	           Object old = A[k];
	           A[k] = x;
	           return old;
	         }
	  }

	  public void addFirst(Object x)
	  {
		 if (NTwice != INITIAL_CAPACITY)
		 { // move one item from A to B
	                q1--; q1 &= maskN;
	                p2--; p2 &= maskNTwice;
	                B[p2] = A[q1];
	                A[q1] = null;
		 }
		 // add item at front:
	     p0--; p0 &= maskNTwice;
	     B[p0] = x;

	     b++;
	     if (p0 == q2) //B is full => A is empty 
		 makeNewB(); // make B to A and create empty B
	  }

	  public void addLast(Object x)
	    {
		 if (NTwice != INITIAL_CAPACITY)
		 { // move one item from A to B
			B[q0++] = A[p1];
	        A[p1++] = null;
	        q0 &= maskNTwice;
	        p1 &= maskN;
		 }
		 // add item at tail:
		 
		 B[q2++] = x;
	     q2 &= maskNTwice;
		 b++;

		 if (p0 == q2) //B is full => A is empty 
		   makeNewB(); // make B to A and create empty B
	    }
	  
	    public final void add(Object x) { addLast(x); }

	    public void add(int i, Object x) {
	    	if (i == size()) {
	    		add(x);
	    		return;
	    	}
	    	//System.out.println(size()+" "+i);
	    	int n = size()-1;
	    	Object tmp = get(n);
	    	addLast(tmp);
	    	for (int j = n; j > i; j--)
	    		set(j,get(j-1));
	    	set(i,x);
	    }

	    private void makeNewB()
	    {
			if (NTwice != INITIAL_CAPACITY)
			   releaseArray(A);
	        N = NTwice;
			NTwice = 2 * NTwice;
			maskN = N - 1;
			maskNTwice = NTwice - 1;
			A = B;
			B = claimArray(NTwice);
			b = 0;
			p1 = q1 = p0;
			p0 = q0 = 0;
			p2 = q2 = N;
		 }

	  public Object removeFirst()
	  {
	      if (size()<=0)
	        throw new java.util.NoSuchElementException();
	      if (NTwice != INITIAL_CAPACITY)
		 {
			if (b == 0)
			// B is empty => A is full
			  makeNewA(); // make A to B and create empty A
			 
			if (NTwice !=  INITIAL_CAPACITY)
			{ // move one item from B to A
			  A[q1++] = B[p2];
	          B[p2++] = null;
	          q1 &= maskN;
	          p2 &= maskNTwice;
			}
			// remove item from front
			b--;
			Object x = B[p0];
	        B[p0++] = null;
	        p0 &= maskNTwice;
			return x;
		 }
		 else if (p0 != q2)
		 { // remove item from front
			b--;
			Object x = B[p0];
	        B[p0++] = null;
	        p0 &= maskNTwice;
			return x;
		 }
		 else return null;
	  }

	  public Object removeLast()
	  {
	     if (size()<=0)
	           throw new java.util.NoSuchElementException();
		 if (NTwice != INITIAL_CAPACITY)
		 {
			if (b == 0)
			// B is empty => A is full
			  makeNewA(); // make A to B and create empty A
			 
			if (NTwice !=  INITIAL_CAPACITY)
			{ // move one item from B to A
	              p1--; p1 &= maskN;
	              q0--; q0 &= maskNTwice;
		          A[p1] = B[q0];
	              B[q0] = null;
			}
			// remove item from tail
			b--;
	        q2--; q2 &= maskNTwice;
			Object x =  B[q2];
	        B[q2] = null;
			return x;
		 }
		 else if (p0 != q2)
		 { // remove item from tail
			b--;
	        q2--; q2 &= maskNTwice;
			Object x = B[q2];
	        B[q2] = null;
			return x;
		 }
		 else return null;
	  }

	    private void makeNewA()
	    { // make A to B and create empty A
		  NTwice = N;
		  N = N / 2;
	      maskN = N - 1;
	      maskNTwice = NTwice - 1;
		  releaseArray(B);
		  B = A;
		  p0 = q2 = p1;
		  q0 = p2 = (p1 + N) % NTwice;
		  p1 = q1 = 0;
	          
		  if (NTwice != INITIAL_CAPACITY)
		  {
		    A = claimArray(N);
	        b = N;
	      }
		  else {
		    A = null;
		    b = NTwice;
	      }	  
	    }

	     //copy to standard array:
	     
	     public Object[] toArray()
	     {
	       int n = this.size();
	       Object[] A = new Object[n];
	       for (int i = 0; i < n; i++)
	         A[i] = get(i);
	       return A;
	     }
	}

