package org.mathrider.piper_me;

final class StringIntern
{
    // If string not yet in table, insert. Afterwards return the string.
    String LookUp(String s)
  {
    return s.intern();
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
    
  public void GarbageCollect() {}
}
