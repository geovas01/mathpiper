package org.mathrider.piper_me;

import org.eninom.collection.HashMap;

final class LispHashTable
{
    // If string not yet in table, insert. Afterwards return the string.
    String LookUp(String aString)
  {
    String s = iHashtable.get(aString);
    if (s == null) {
      iHashtable.put(aString,aString);
      return aString;
    }
    else return s;
  }
    String LookUpStringify(String aString)
  {
    aString = "\""+aString+"\"";
    return LookUp(aString);
  }
    
    String LookUpUnStringify(String aString)
  {
    aString = aString.substring(1,aString.length()-1);
    return LookUp(aString);
  }

    // GarbageCollect
    void GarbageCollect()
  {
    //TODO FIXME
  }
  HashMap<String, String> iHashtable = new HashMap<String, String>();
}
