/*
 * This Software is free and can be freely distributed,
 * provided that any derived work contains the following 
 * copyright note:
 *
 * Copyright 2006, Oliver Glier
 */

package org.eninom.collection;


public final class RandomAccessDeque<E> extends RandomAccessQueue<E>
{
    public void enqueueHead(E item) { queue.addFirst(item); }
    public E dequeueTail() { return queue.removeLast(); }
    public E getTail() { return queue.get(queue.size()-1); }
}
