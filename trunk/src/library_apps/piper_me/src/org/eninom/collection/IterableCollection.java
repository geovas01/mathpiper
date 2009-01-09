

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

import org.eninom.iterator.*;
import org.eninom.seq.Seq;

//! Iterable Interface
/*<literate>*/
/**
 * Interface for an iterable collection. An iterable collection
 * can deliver a sequence of its elements. We do not consider
 * iterable collections as sequences by itself, since it might
 * discard the costs of constructing a view of the rest of
 * the elements when iterating through them.
 * Implementations must compute meaningful hashcodes and equality
 * tests that work for pairs of collections of the same type,
 * i.e. the concrete instance type are the same and the generic
 * type parameters have a common ancestor with meaningful equality
 * tests among them. We do not specify the concrete value of
 * the hash code in order to give implementations the chance
 * to provide implementations that do not touch every element,
 * as this makes sense for very large collections. Likewise,
 * we do not require that different implementations of this
 * interface can meaningfully test equality among each others
 * instances, so that implementations are free to involve
 * extra-fields apart from the list of elements.<br />
 * Having said this, the class <i>AbstractIterableCollection</i>
 * provides default implementations for hash value and equality
 * tests.
 */
public interface IterableCollection<E> {
  public int size();
  public Seq<E> seq();
  public ForwardIterator<E> iterator();
}