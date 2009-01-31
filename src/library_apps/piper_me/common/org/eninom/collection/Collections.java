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

//! Routines for Collections 
/*<literate>*/
/**
This class provides several routines for dealing with collections
**/
final public class Collections {
  /**
   * This class has no instances.
   */
  private Collections() {
   
      }
  
  public static<E> String printToString(IterableCollection<E> collection) {
    StringBuilder str = new StringBuilder();
    str.append("[");
    ForwardIterator<E> it = collection.iterator();
    for (int i = 0; i < collection.size(); i++) {
      E item = it.next();
      if (item == null)
      str.append("null");
      else
      str.append(item.toString());
      if (i < collection.size() - 1)
      str.append(", ");
    }//`for`
    str.append("]");
    return str.toString();
  }  
}
