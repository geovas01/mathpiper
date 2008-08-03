package org.mathrider.piper_b.j2me;

/** class LispPtr. This class is a smart pointer type class to Lisp
 *  objects that can be inserted into linked lists. They do the actual
 *  reference counting, and consequent destruction of the object if
 *  nothing points to it. LispPtr is used in LispObject as a pointer
 *  to the next object, and in diverse parts of the built-in internal
 *  functions to hold temporary values.
 */

public class LispPtr {
	
	public LispPtr()
	{
	    iNext = null;
	}
	
    public LispPtr(LispPtr aOther)
    {
 	   iNext = aOther.iNext;
    }
	  
    public LispObject getNext()
    {
     return iNext;
    }
	    
	LispObject iNext;
	  
	public void setNext(LispObject aNext)
	  {
		iNext = aNext;
	  }
}
