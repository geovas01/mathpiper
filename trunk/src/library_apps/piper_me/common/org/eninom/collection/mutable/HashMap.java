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

package org.eninom.collection.mutable;

import java.util.NoSuchElementException;

import org.eninom.collection.Set;
import org.eninom.collection.Collections;
import org.eninom.func.Cons;
import org.eninom.iterator.ForwardIterator;
import org.eninom.seq.Seq;
import org.eninom.seq.SeqFromIterator;

// ! Mutable Hashmap
/* <literate> */
/**
 * This mutable hashmap is a helper class. It serves as a replacement for
 * the hashmap implementation from the Java collections, which is not
 * present in Java ME.<br />
 * The key values must not be null. Furthermore, their equal-methods must be
 * consistent to their hash codes. Note that for the equality and hash value of
 * maps, iteration order is irrelevant. The Function-interface is not
 * implemented since functions must be immutable.
 */
@SuppressWarnings("unchecked")
public final class HashMap<Key, Value> implements
    Set<Cons<Key, Value>> {
  private int size = 0;
  private Object[] table;

  /*
   * Wrap extendible array class so that the class can be used to differentiate
   * between user-objects and lists. It is essential that this class is private.
   */
  private class List {
    ExtendibleArray<Object> arr = new ExtendibleArray<Object>();

    @Override
    public String toString() {
      return arr.toString();
    }

    @Override
    public boolean equals(Object obj) {
      throw new IllegalStateException(
          "Internal class is incomparable. This exception points " +
          "to an implementation error of the enclosing class.");
    }

    @Override
    public int hashCode() {
      throw new IllegalStateException(
          "Internal class has no meaningful hashcode. This " +
          "exception points to an implementation error of the " +
          "enclosing class.");
    }
  }

  public final static int DEFAULT_INITIAL_CAPACITY = 1513;

  public HashMap() {
    table = new Object[2 * DEFAULT_INITIAL_CAPACITY];
  }

  public HashMap(long initialCapacity) {
    if (initialCapacity > Integer.MAX_VALUE / 2)
      throw new IllegalArgumentException("Capacity too large "+initialCapacity);
    table = new Object[(int)(2 * initialCapacity)];
  }

  /**
   * The key value must not be null. Furthermore, its equal-method must be
   * consistent with the hash values.
   */
  final public void put(Key a, Value b) {
    if (size * 4 > table.length) {
      rehash(5 * table.length);
    }
    int hc = a.hashCode();
    if (hc < 0) {
      hc = -hc;
    }
    int k = 2 * (hc % (table.length / 2));
    if (table[k] == null) {
      table[k] = a;
      table[k + 1] = b;
    } else {
      List list = null;
      if (table[k].getClass() == List.class) {
        list = (List) table[k];
        for (int i = 0; i < list.arr.size(); i = i + 2) {
          if (a.equals(list.arr.get(i))) {
            list.arr.set(i, a);
            list.arr.set(i + 1, b);
            return;
          }// `if`
        }// `for`
      } else {
        if (a.equals(table[k])) {
          table[k] = a;
          table[k + 1] = b;
          return;
        }
        list = new List();
        list.arr.addLast(table[k]);
        list.arr.addLast(table[k + 1]);
        table[k] = list;
        table[k + 1] = null;
      }// `else`
      list.arr.addLast(a);
      list.arr.addLast(b);
    }// else
    size = size + 1;
  }

  final public void remove(Key a) {
    if ((size * 8 < table.length)
        && (DEFAULT_INITIAL_CAPACITY * 8 < table.length)) {
      rehash(table.length / 4);
    }
    int hc = a.hashCode();
    if (hc < 0) {
      hc = -hc;
    }
    int k = 2 * (hc % (table.length / 2));
    if (table[k] == null) {
      return;
    } else if (table[k].getClass() != List.class) {
      if (a.equals(table[k])) {
        table[k] = null;
        table[k + 1] = null;
        size = size - 1;
      } // `if`
    } else {
      List list = (List) table[k];
      for (int i = 0; i < list.arr.size(); i = i + 2) {
        if (a.equals(list.arr.get(i))) {
          Object value = list.arr.removeLast();
          Object key = list.arr.removeLast();
          if (i < list.arr.size()) {
            list.arr.set(i, key);
            list.arr.set(i + 1, value);
          }// `if`
          if (list.arr.size() == 2) {
            table[k] = list.arr.get(0);
            table[k + 1] = list.arr.get(1);
          }// `if`
          size = size - 1;
        }// `if`
      }// `for`
    }// `else`
  }

  final public boolean containsKey(Key a) {
    int hc = a.hashCode();
    if (hc < 0) {
      hc = -hc;
    }
    int k = 2 * (hc % (table.length / 2));
    if (table[k] == null) {
      return false;
    } else if (table[k].getClass() != List.class) {
      return a.equals(table[k]);
    } else {
      List list = (List) table[k];
      for (int i = 0; i < list.arr.size(); i = i + 2) {
        if (a.equals(list.arr.get(i))) {
          return true;
        }
      }// `for`
      return false;
    }// `else`
  }

  final public Value get(Key a) {
    int hc = a.hashCode();
    if (hc < 0) {
      hc = -hc;
    }
    int k = 2 * (hc % (table.length / 2));
    if (table[k] == null) {
      return (Value) table[k + 1];
    } else if (table[k].getClass() != List.class) {
      if (a.equals(table[k])) {
        return (Value) table[k + 1];
      } else {
        return null;
      }
    } else {
      List list = (List) table[k];
      for (int i = 0; i < list.arr.size(); i = i + 2) {
        if (a.equals(list.arr.get(i))) {
          return (Value) list.arr.get(i + 1);
        }
      }// `for`
      return null;
    }// `else`
  }
  
  final public Key getSameKey(Key a) {
    int hc = a.hashCode();
    if (hc < 0) {
      hc = -hc;
    }
    int k = 2 * (hc % (table.length / 2));
    if (table[k] == null) {
      return (Key) table[k];
    } else if (table[k].getClass() != List.class) {
      if (a.equals(table[k])) {
        return (Key) table[k];
      } else {
        return null;
      }
    } else {
      List list = (List) table[k];
      for (int i = 0; i < list.arr.size(); i = i + 2) {
        if (a.equals(list.arr.get(i))) {
          return (Key) list.arr.get(i);
        }
      }// `for`
      return null;
    }// `else`
  }

  final public long size() {
    return size;
  }

  final public void assertInvariant() {
    int trueSize = 0;
    int trueHashCode = 0;
    for (int i = 0; i < table.length; i = i + 2) {
      if (table[i] != null) {
        if (table[i].getClass() != List.class) {
          trueSize = trueSize + 1;
          int hc = table[i].hashCode();
          if (hc < 0) {
            hc = -hc;
          }

          if (2 * (hc % (table.length / 2)) != i) {
            throw new IllegalStateException(
                "Wrong table position.");
          }

          trueHashCode = trueHashCode + hc;
        } else {
          List list = (List) table[i];
          trueSize = trueSize + (int) list.arr.size() / 2;
          for (int k = 0; k < list.arr.size(); k = k + 2) {
            int hc = list.arr.get(k).hashCode();
            if (hc < 0) {
              hc = -hc;
            }
            if (2 * (hc % (table.length / 2)) != i) {
              throw new IllegalStateException(
                  "Wrong table position.");
            }
            trueHashCode = trueHashCode + hc;
          }// `for`
        }// `else`
      }// `if`
    }// `for`
    if (size != trueSize) {
      throw new IllegalStateException("Wrong size.");
    }
  }

  private class Iterator implements
      ForwardIterator<Cons<Key, Value>> {

    int listPos = 0;
    int tablePos = 0;

    public Iterator() {
      tablePos = advanceTablePos(0);
    }

    public Cons<Key, Value> next() {
      if (!hasNext()) {
        throw new NoSuchElementException("No next element.");
      }

      if (table[tablePos].getClass() != List.class) {
        Cons<Key, Value> result = new Cons<Key, Value>(
            (Key) table[tablePos], (Value) table[tablePos + 1]);
        tablePos = advanceTablePos(tablePos + 2);
        return result;
      } else {
        List list = (List) table[tablePos];
        Cons<Key, Value> result = new Cons<Key, Value>(
            (Key) list.arr.get(listPos), (Value) list.arr
                .get(listPos + 1));
        listPos = listPos + 2;
        if (listPos >= list.arr.size()) {
          listPos = 0;
          tablePos = advanceTablePos(tablePos + 2);
        }// `if`
        return result;
      }// `else`
    }

    public boolean hasNext() {
      return (tablePos < table.length);
    }
  }
  
  private class KeyIterator implements
  ForwardIterator<Key> {

int listPos = 0;
int tablePos = 0;

public KeyIterator() {
  tablePos = advanceTablePos(0);
}

public Key next() {
  if (!hasNext()) {
    throw new NoSuchElementException("No next element.");
  }

  if (table[tablePos].getClass() != List.class) {
    Key result = (Key) table[tablePos];
    tablePos = advanceTablePos(tablePos + 2);
    return result;
  } else {
    List list = (List) table[tablePos];
    Key result = (Key) list.arr.get(listPos);
    listPos = listPos + 2;
    if (listPos >= list.arr.size()) {
      listPos = 0;
      tablePos = advanceTablePos(tablePos + 2);
    }// `if`
    return result;
  }// `else`
}

public boolean hasNext() {
  return (tablePos < table.length);
}
}


  private int advanceTablePos(int tablePos) {
    while ((tablePos < table.length) && (table[tablePos] == null)) {
      tablePos = tablePos + 2;
    }// `while`
    return tablePos;
  }

  final public ForwardIterator<Cons<Key, Value>> iterator() {
    return new Iterator();
  }
  
  final public ForwardIterator<Key> keyIterator() {
    return new KeyIterator();
  }

  public final Seq<Cons<Key, Value>> seq() {
    return SeqFromIterator.create(iterator());
  }

  private void rehash(int newCapacity) {
    HashMap<Key, Value> newMap = new HashMap<Key, Value>(
        newCapacity);
    int tablePos = advanceTablePos(0);
    int listPos = 0;
    int cnt = 0;
    while (tablePos < table.length) {
      cnt++;
      if (table[tablePos].getClass() != List.class) {
        newMap.put((Key) table[tablePos],
            (Value) table[tablePos + 1]);
        tablePos = advanceTablePos(tablePos + 2);
      } else {
        List list = (List) table[tablePos];
        newMap.put((Key) list.arr.get(listPos), (Value) list.arr
            .get(listPos + 1));
        listPos = listPos + 2;
        if (listPos >= list.arr.size()) {
          listPos = 0;
          tablePos = advanceTablePos(tablePos + 2);
        }// `if`
      }// `else`
    }// `while`
    this.table = newMap.table;
  };

  /**
   * Debug string
   */
  public String debug() {
    StringBuffer s = new StringBuffer();
    s.append(size + " c:" + table.length / 2 + " [");
    for (int i = 0; i < table.length; i++) {
      if (i > 0) {
        s.append(",");
      }
      s.append(table[i]);
    }
    s.append("]");
    return s.toString();
  }

  public boolean contains(Cons<Key, Value> e) {
    Key k = e.first();
    if (k == null)
      return false;
    
    if (!containsKey(k))
      return false;
    
    Value v = get(k);
    if (v == null)
      return e.second() == null;
    else
    return v.equals(e.second());
  }

  public Cons<Key, Value> getSame(Cons<Key, Value> e) {
    Key k = getSameKey(e.first());
    if (k == null)
      return null;

    Value v = get(k);
    
    if (((v == null) && (e.second() == null)) 
        || ((v != null) && (e.second() != null) 
            && v.equals(e.second())))
      return new Cons<Key, Value>(k,v);
    else return null;
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
}// `class`
