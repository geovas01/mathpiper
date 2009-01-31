package org.mathrider.piper_me;

import org.eninom.collection.HashMap;

/** LispAssociatedHash allows you to associate arbitrary
 * information with a string in the above hash table. You can
 * specify what type of information to link to the string, and
 * this class then stores that information for a string. It is
 * in a sense a way to extend the string object without modifying
 * the string class itself. This class does not own the strings it
 * points to, but instead relies on the fact that the strings
 * are maintained in a hash table (like LispHashTable above).
 */
class LispAssociatedHash {
  HashMap<String, Object> map = new HashMap<String, Object>();

  public void put(String key, Object v) {
    map.put(key,v);
  }

  public Object get(String key) {
    return map.get(key);
  }

  public void remove(String key) {
    map.remove(key);
  }
}

