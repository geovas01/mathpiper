package org.eninom.collection;


public interface MutableStack<E> extends IterableCollection<E> {
  public E pop();
  public E top();
  public void push(E item);
}
