package org.eninom.immutable;

import org.eninom.collection.*;
import org.eninom.iterator.*;
import org.eninom.seq.Seq;
import org.eninom.func.Cons;

//! Immutable Stack
/*<literate>*/
/**
Immutable Stack.
*/
public final class IStack<E> extends Cons<E,IStack<E>> 
implements IterableCollection<E>, Seq<E> {
  
	private static final Object bottom = new Object() {
		@Override
		public String toString() {
			return "BOTTOM";
		}
	};
	
	public IStack() {
		super((E) bottom, null, null);
	}
	
	//push
	public IStack(E e, IStack<E> S) {
		super(e,S);
	}
	
	public IStack(E a, E b,  IStack<E> S) {
		super(a,b,S);
	}
	
	public IStack(E a, E b, E c,  IStack<E> S) {
		super(a,b,c, S);
	}
	
	public IStack(E a, E b, E c, E d,  IStack<E> S) {
		super(a,b,c, d, S);
	}
	
	//pop
	public IStack(IStack<E> S) {
		super(S);
	}
	
	public int size() {
		return super.size()-3;
	}

  public IStack<E> push(E e) {
	  return new IStack<E>(e,this);
  }
  
  public IStack<E> push(E a, E b) {
	  return new IStack<E>(a,b,this);
  }
  
  public IStack<E> push(E a, E b, E c) {
	  return new IStack<E>(a,b,c,this);
  }
  
  public IStack<E> push(E a, E b, E c, E d) {
	  return new IStack<E>(a,b,c,d,this);
  }
  
  
  public IStack<E> pop() {
	  return new IStack<E>(this);
  }
  
//TODO: optimize (already in Cons):
  public IStack<E> pop(int sz) {
	  if (sz <= 0) 
		  return this; 
	  else
		  return this.pop().pop(sz-1);
  }
  
  private E superGet(int p) {
  	return (E) super.get(p);
  }
  
  public E get(int p) {
	if ((p >= size()) || (p < 0))
	  throw new IllegalAccessError();
	
	return (E) super.get(p);
  }
  
  public E top() {
	return get(0);
  }
  
  public String toString() {
	StringBuilder str = new StringBuilder();
	str.append("[");
  ForwardIterator<E> it = iterator();
	for (int i = 0; i < size(); i++) {
	  E item = it.next();
	  if (item == null)
		str.append("null");
	  else
		str.append(item.toString());
	  if (i < size() - 1)
		str.append(", ");
	}//`for`
	str.append("]");
	return str.toString();
  }
  
  public IStack<E> rest() {
  	IStack<E> s = pop();
  	if (s.superGet(0) == bottom)
  		return null;
  	else
  		return s;
  }
  
  //TODO: optimize (don't wrap seq)
  public ForwardIterator<E> iterator() {
	  return new IteratorFromSeq<E>(seq());
  }
  
  public Seq<E> seq() {
    return this;
  }
  
  public static void main(String[] args) {
    IStack<Integer> S = new IStack<Integer>();
    S = S.push(1);
    S = S.push(2);
    S = S.push(3);
    System.out.println(S);
    while(S != null) {
      System.out.print(S.get(0)+" ");
       S = S.rest();
    }
  }
}
