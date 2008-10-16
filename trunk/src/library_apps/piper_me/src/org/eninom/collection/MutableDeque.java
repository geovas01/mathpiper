package org.eninom.collection;

public interface MutableDeque<E> extends MutableQueue<E> {
  public void addFirst(E item);
  public E removeLast();
  public E last();
}
