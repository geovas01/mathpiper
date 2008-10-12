package org.eninom.collection;

import org.eninom.iterator.*;
import org.eninom.seq.Seq;

public interface IterableCollection<E> {
  public int size();
  public Seq<E> seq();
  public ForwardIterator<E> iterator();
}
