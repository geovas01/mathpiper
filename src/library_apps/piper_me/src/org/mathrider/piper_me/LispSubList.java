package org.mathrider.piper_me;


class LispSubList extends LispObject
{
  public static LispSubList New(LispObject aSubList)
  {
    return new LispSubList(aSubList);
  }
    public LispPtr SubList()
  {
    return iSubList;
  }
  public String String()
  {
    return null;
  }
  public LispObject Copy(boolean aRecursed) throws Exception
  {
    //TODO recursed copy needs to be implemented still
    LispError.LISPASSERT(aRecursed == false);
    LispObject copied = new LispSubList(iSubList.getNext());
    return copied;
  }
    public LispObject SetExtraInfo(LispPtr aData)
  {
//TODO FIXME
/*
    LispObject* result = NEW LispAnnotatedObject<LispSubList>(this);
    result->SetExtraInfo(aData);
    return result;
*/
return null;
  }
    LispSubList(LispObject aSubList)
  {
    iSubList.setNext(aSubList);
  }
    LispPtr iSubList = new LispPtr();
};
