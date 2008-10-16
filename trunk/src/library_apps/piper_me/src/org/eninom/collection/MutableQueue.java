package org.eninom.collection;

public interface MutableQueue<E> extends IterableCollection<E> {
  public void addLast(E item);
  public E removeFirst();
  public E first();
}
