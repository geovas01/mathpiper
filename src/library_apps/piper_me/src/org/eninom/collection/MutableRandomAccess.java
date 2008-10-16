package org.eninom.collection;

public interface MutableRandomAccess<E> extends RandomAccess<E> {
  E set(int i, E e);
}
