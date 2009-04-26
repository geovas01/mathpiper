package org.eninom.collection;

public interface Set<E> extends IterableCollection<E> {
  
  /**
   * Two Sets are equal if and only if they have the same size and each element
   * of one list has an equal element in the other list.
   * This allows to test different implementations for equality.
   */
  public boolean equals(Object obj);
  
  /**
   * The hashcode of a set the sum of the hashcodes of its elements.
   */
  public int hashCode();
  
  /**
   * Different implementations of this interface are allowed to have different
   * string representations. However, the string representation must be
   * independent from platform-specific behavior and it must be reproducable
   * across different runs of the same program.
   */
  public String toString();
  
  
  /**
   * Returns the stored representation of the given item.
   */
  public E getSame(E item);
}
