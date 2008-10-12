package org.eninom.iterator;

import org.eninom.iterator.ForwardIterator;
import org.eninom.seq.Seq;

public final class IteratorFromSeq<E> implements ForwardIterator<E> {
  private Seq<E> S;
  public IteratorFromSeq(Seq<E> S) { this.S = S; }
  public E next() {
    E x = S.first();
    S = S.rest();
    return x;
  }
  
  public boolean hasNext() {
    return (S.rest() != null);
  }
}//`class`
