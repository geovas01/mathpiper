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

import java.util.NoSuchElementException;

import org.eninom.func.Cons;
import org.eninom.iterator.ForwardIterator;
import org.eninom.seq.Seq;
import org.eninom.seq.SeqFromIterator;

//! Mutable Hashmap
/*<literate>*/
/**
ATTENTION: This is work in progress and basically untested! <br />
<br />
This is a mutable hashmap. We need it in our implementations
of low-level immutable data structures, for instance for traversal.
<br />
The key values must not be null. Furthermore, their
equal-methods must be consistent to their hash codes.
*/
public final class HashMap<Key,Value> 
implements IterableCollection<Cons<Key,Value>>{
  private int size = 0;
  private Object[] table;
  private int hashSum = 0;
  private class List extends ExtendibleArray<Object> {}
  
  private static int DEFAULT_INITIAL_CAPACITY = 1513;
  
  public HashMap() {
   table = new Object[2*DEFAULT_INITIAL_CAPACITY]; 
  }
  
  public HashMap(int initialCapacity) {
    table = new Object[2*initialCapacity]; 
   }
  
  /**
   * The key value must not be null. Furthermore, its
   * equal-method must be consistent to its hash code.
   */
  public void put(Key a, Value b) {
    if (size * 4 > table.length) {
      rehash(5 * table.length);
    }
    int hc = a.hashCode();
    if (hc < 0)
      hc = -hc;
    int k = 2*(hc % (table.length / 2));
    if (table[k] == null) {
      table[k] = a;
      table[k+1] = b;
    } else {
      List list = null;
      if (table[k].getClass() == List.class) {
        list = (List)table[k];
        for (int i = 0; i < list.size(); i = i+ 2) {
          if (a.equals(list.get(i))) {
            list.set(i,a);
            list.set(i+1,b);
            return;
          }//`if`
        }//`for`
      } else {
        if (a.equals(table[k])) {
          table[k] = a;
          table[k+1] = b;
          return;
        }
        list = new List();
        list.addLast(table[k]);
        list.addLast(table[k+1]);
        table[k] = list;
        table[k+1] = null;
      }//`else`
      list.addLast(a);
      list.addLast(b);
    }//else
    hashSum = hashSum + hc;
    size = size + 1;
  }
  
  public void remove(Key a) {
    if ((size * 6 < table.length) && 
        (DEFAULT_INITIAL_CAPACITY * 6 < table.length)){
      rehash(table.length / 4);
    }
    int hc = a.hashCode();
    if (hc < 0)
      hc = -hc;
    int k = 2*(hc % (table.length / 2));
    if (table[k] == null) {
      return;
    }
    else if (table[k].getClass() != List.class) {
      if (a.equals(table[k])) {
        table[k] = null;
        table[k+1] = null;
        hashSum = hashSum - hc;
        size = size - 1;
      } //`if`
    } else {
      List list = (List)table[k];
      for (int i = 0; i < list.size(); i = i+ 2) {
        if (a.equals(list.get(i))) {
          Object value = list.removeLast();
          Object key = list.removeLast();
          if (i < list.size()) {
            list.set(i, key);
            list.set(i+1, value);
          }//`if`
          if (list.size() == 0) {
            table[k] = null;
            table[k + 1] = null;
          }//`if`
          hashSum = hashSum - hc;
          size = size - 1;
        }//`if`
      }//`for`
    }//`else`
  }
  
  public boolean contains(Key a) {
    int hc = a.hashCode();
    if (hc < 0)
      hc = -hc;
    int k = 2*(hc % (table.length / 2));
    if (table[k] == null) 
      return false;
    else if (table[k].getClass() != List.class) 
      return a.equals(table[k]);
    else {
      List list = 
        (List)table[k];
        for (int i = 0; i < list.size(); i = i+ 2) {
          if (a.equals(list.get(i)))
          return true;
        }//`for`
        return false;
    }//`else`
  }
  
  public Value get(Key a) {
    int hc = a.hashCode();
    if (hc < 0)
      hc = -hc;
    int k = 2*(hc % (table.length / 2));
    if (table[k] == null) 
      return (Value)table[k+1];
    else if (table[k].getClass() != List.class) 
      if (a.equals(table[k]))
        return (Value)table[k+1];
      else return null;
    else {
      List list = (List)table[k];
        for (int i = 0; i < list.size(); i = i+ 2) {
          if (a.equals(list.get(i)))
          return (Value)list.get(i+1);
        }//`for`
        return null;
    }//`else`
  }
  
  public int size() {
    return size;
  }
  
  public void assertInvariant() {
    int trueSize = 0;
    int trueHashCode = 0;
    for (int i = 0; i < table.length; i = i + 2) {
      if (table[i] != null) {
        if (table[i].getClass() != List.class) {
          trueSize = trueSize + 1;
          int hc = table[i].hashCode();
          if (hc < 0) 
            hc = -hc;
          
          if (2*(hc % (table.length / 2)) != i)
            throw new IllegalStateException("Wrong table position.");
            
          trueHashCode = trueHashCode + hc;
        } else {
          List list = (List) table[i];
          trueSize = trueSize + list.size() / 2;
          for (int k = 0; k < list.size(); k = k + 2) {
            int hc = list.get(k).hashCode();
            if (hc < 0) 
              hc = -hc;
            if (2*(hc % (table.length / 2)) != i)
              throw new IllegalStateException("Wrong table position.");
            trueHashCode = trueHashCode + hc;
          }//`for`
        }//`else`
      }//`if`
    }//`for`
    if (size != trueSize)
      throw new IllegalStateException("Wrong size.");
    if (hashSum == trueHashCode)
      throw new IllegalStateException("Wrong hash.");
  }
  
  private class Iterator implements  ForwardIterator<Cons<Key, Value>> {
    
    int listPos = 0;
    int tablePos = 0;
    
    public Iterator() {
     tablePos = advanceTablePos(0);
    }
    
    public Cons<Key, Value> next() {
      if (!hasNext())
        throw new NoSuchElementException();
      
      if (table[tablePos].getClass() != List.class) {
        Cons<Key, Value> result = new Cons<Key, Value>(
            (Key) table[tablePos],
            (Value) table[tablePos+1]); 
         tablePos = advanceTablePos(tablePos + 2);
         return result;
      } else {
        List list = (List)table[tablePos];
        Cons<Key, Value> result = new Cons<Key, Value>(
            (Key) list.get(listPos),
            (Value) list.get(listPos+1));
        listPos = listPos + 2;
        if (listPos >= list.size()) {
          listPos = 0;
          tablePos = advanceTablePos(tablePos + 2);
        }//`if`
        return result;
      }//`else`
    }
    
    public boolean hasNext() {
      return (tablePos < table.length);
    }
  }
  
  private int advanceTablePos(int tablePos) {
    while ((tablePos < table.length) && (table[tablePos] == null)) {
      tablePos = tablePos + 2;
    }//`while`
    return tablePos;
  }
  
  public ForwardIterator<Cons<Key, Value>> iterator() {
    return new Iterator();
  }
  
  public Seq<Cons<Key, Value>> seq() {
    return SeqFromIterator.create(iterator());
  }
  
  private void rehash(int newCapacity) {
    HashMap<Key, Value> newMap = new HashMap<Key, Value>(newCapacity);
    int tablePos = advanceTablePos(0);
    int listPos = 0;
    int cnt = 0;
    while (tablePos < table.length) {
      cnt++;
      if (table[tablePos].getClass() != List.class) {
        newMap.put((Key) table[tablePos],
            (Value) table[tablePos+1]); 
         tablePos = advanceTablePos(tablePos + 2); 
      } else {
        List list = (List)table[tablePos];
        newMap.put((Key) (Key) list.get(listPos),
            (Value) list.get(listPos+1));
        listPos = listPos + 2;
        if (listPos >= list.size()) {
          listPos = 0;
          tablePos = advanceTablePos(tablePos + 2);
        }//`if`
      }//`else`
    }//`while`
    this.table = newMap.table;
    this.hashSum = newMap.hashSum;   
  };
  
  public int hashCode() {
    return hashSum;
  }
  
  public boolean equals(Object other) {
    
    if (this == other)
      return true;
    
    if (other == null)
      return false;
    
    if (other.getClass() != HashMap.class)
      return false;
    
    HashMap<Object,Object> map2 = (HashMap<Object,Object>) other;
    
    if (map2.size != this.size) {
      return false;
    }
    
    if (map2.hashSum != this.hashSum) {
      return false;
    }
    
    ForwardIterator<Cons<Key,Value>> it = this.iterator();
    while (it.hasNext()) {
      Cons<Key,Value> pair = it.next();
      Object key = pair.first();
      Object value = pair.rest();
      if (!map2.contains(key)) {
        return false;
      }
      Object value2 = map2.get(key);
      if ((value == null) ^ (value2 == null)) {
        return false;
      }
      else if (!value.equals(value2)) {;
        return false;
      }
    }//`for`
    return true;
  }
  
  public String toString() {
    return Collections.printToString(this);
  }
  
  /**
   * Debug string: Needs impleentation still.
   */
  public String debug() {
    StringBuffer s = new StringBuffer();
    s.append(size+" c:"+table.length/2+" hc:"+hashSum+" [");
    for (int i = 0; i < table.length; i++) {
      if (i > 0)
        s.append(",");
      s.append(table[i]);
    }
    s.append("]");
    return s.toString();
  }
}//`class`
