/*
 * This Software is free and can be freely distributed,
 * provided that any derived work contains the following 
 * copyright note:
 *
 * Copyright 2006, Oliver Glier
 */

package org.eninom.collection;

public class RandomAccessQueue<E>
{
    ExtendibleArray<E> queue;

    public RandomAccessQueue() { queue = new  ExtendibleArray<E>(); }

    //public Iterator<E> iterator() { return queue.iterator(); }
    public final int size() {return queue.size();}
    public final void enqueueTail(E item) { queue.addLast(item); }
    public final E dequeueHead() { return queue.removeFirst(); }
    public final E getHead() { return queue.get(0); }
    
    public E get (int pos) { return queue.get(pos); }
}
