/*
 * This Software is free and can be freely distributed,
 * provided that any derived work contains the following 
 * copyright note:
 *
 * Copyright 2006, Oliver Glier
 */

package org.eninom.collection;

public final class RandomAccessStack<E> extends ExtendibleArray<E>
{
    public E pop() { return removeFirst(); }
    public E top() { return get(0); }
    public void push(E item) { addFirst(item);}
}
