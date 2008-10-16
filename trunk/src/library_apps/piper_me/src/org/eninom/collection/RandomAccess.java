package org.eninom.collection;

public interface RandomAccess<E> extends IterableCollection<E> {
  E get(int i);
}
