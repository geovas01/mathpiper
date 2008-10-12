package org.eninom.seq;

public interface Seq<E> {
  E first();
  Seq<E> rest();
}//`interface`
