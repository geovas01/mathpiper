package org.eninom.collection.mutable;

import org.eninom.collection.Collections;
import org.eninom.collection.Set;
import org.eninom.iterator.ForwardIterator;
import org.eninom.seq.Seq;
import org.eninom.seq.SeqFromIterator;

//! Mutable Hashset
/* <literate> */
/**
 * This mutable hashmap is a helper class. It serves as a replacement for the
 * hashmap implementation from the Java collections, which is not present in
 * Java ME.<br />
 * The stored values must not be null. Furthermore, their equal-methods must
 * be consistent to their hash codes.
 */
public final class HashSet<E> implements Set<E> {

  /*
   * The implementation is backed by a map. All operations
   * delegate to the map.
   */
  private HashMap<E, Object> asMap = null;
  
  
  final static int DEFAULT_INITIAL_CAPACITY = HashMap.DEFAULT_INITIAL_CAPACITY;
  
  public HashSet() {
    asMap = new HashMap<E, Object>(DEFAULT_INITIAL_CAPACITY);
  }
  
  public HashSet(long capacity) {
    asMap = new HashMap<E, Object>(capacity);
  }

  public boolean contains(E e) {
    return asMap.containsKey(e);
  }

  public ForwardIterator<E> iterator() {
  return asMap.keyIterator();
  }

  public Seq<E> seq() {
    return SeqFromIterator.create(iterator());
  }

  public long size() {
    return asMap.size();
  }
  
  public void add(E e) {
    asMap.put(e, null);
  }
  
  public void remove(E e) {
    asMap.remove(e);
  }

  public E getSame(E item) {
    return asMap.getSameKey(item);
  }
 
  @Override
  public int hashCode() {
    return Collections.hashCodeForSets(this);
  }

  @Override
  public boolean equals(Object obj) {
    return Collections.equalsForSet(this, obj);
  }
  
  public String toString() {
   return Collections.toStringSorted(this); 
  }
}
