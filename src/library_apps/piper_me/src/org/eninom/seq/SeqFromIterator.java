package org.eninom.seq;

import org.eninom.func.Function;
import org.eninom.func.Lazy;
import org.eninom.iterator.ForwardIterator;

public class SeqFromIterator<E> implements Seq<E> {

  public static <E> SeqFromIterator<E> create(
      ForwardIterator<E> it) {
    if (!it.hasNext()) {
      return null;
    } else {
      return new SeqFromIterator(null, it);
    }
  }

  private static Function<ForwardIterator, SeqFromIterator> restCons = 
    new Function<ForwardIterator, SeqFromIterator>() {
    @Override
    public SeqFromIterator get(ForwardIterator it) {
      return new SeqFromIterator(it.next(),it);
    }
  };

  private final E firstValue;
  private final Lazy<ForwardIterator, SeqFromIterator> restSeq;

  private SeqFromIterator(E first, ForwardIterator<E> it) {
    firstValue = first;
    if (it.hasNext())
      restSeq = new Lazy<ForwardIterator, SeqFromIterator>(
        restCons, it);
    else
      restSeq = null;
  }

  public E first() {
    return (E) restSeq.value().firstValue;
  }

  public Seq<E> rest() {
    if (restSeq.value().restSeq == null)
      return null;
    else
      return restSeq.value();
  }
  
  public static class IntIter implements ForwardIterator<Integer> {
    public IntIter(int max) {
      this.max = max;
    }
    int max;
    int cnt = 0;
    
    public boolean hasNext() {
     return (cnt < max);
    }
    
    public Integer next() {
      return cnt++;
    }
  }
  
  public static void main(String[] args) {
    Seq<Integer> S = SeqFromIterator.create(new IntIter(10));
    while (S != null) {
      Seq<Integer> T = S;
      while (T != null) {
        System.out.print(T.first()+" ");
        T = T.rest();
      }
      S = S.rest();
      System.out.println();
    }
  }
}//`class`
