/*
(C) Oliver Glier 2008

Eninom-Lib is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.
 
Eninom-Lib is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent
modules, and to copy and distribute the resulting executable under
terms of your choice, provided that you also meet, for each linked
independent module, the terms and conditions of the license of that
module.  An independent module is a module which is not derived from
or based on this library.  If you modify this library, you may extend
this exception to your version of the library, but you are not
obligated to do so.  If you do not wish to do so, delete this
exception statement from your version. */

package org.eninom.collection;


//! Abstract Iterable Collection
/*<literate>*/
/**
 * This abstract class provides a default implementation
 * for the computation of hash values and equality tests.
 * The equality test does not consider the exact instance type,
 * so that instances of two different concrete classes using
 * these methods can be compared with each other and are
 * equal if and only if the order of their elements is
 * the same.<br /> 
 * <br />
 * Deriving from this class is considered as an implementation
 * detail. When two concrete classes derive from this class,
 * no assumptions can be made about the compatibility
 * of cross-class equality tests and hash values, or results
 * of <i> instanceof AbstractIterableCollection</i> for
 * future releases, except if stated explicitely by the
 * authors of the derived classes. 
 */
public abstract class AbstractIterableCollection<E> implements
    IterableCollection<E> {
  
  @Override
  public int hashCode() {
    return Collections.hashCode(this);
  }

  @Override
  public boolean equals(Object obj) {
    return Collections.equals(this, obj);
  }
  
  @Override
  final public String toString() {
    return Collections.printToString(this);
  }
}
