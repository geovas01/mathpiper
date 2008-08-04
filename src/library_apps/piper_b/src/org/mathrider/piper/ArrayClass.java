package org.mathrider.piper;


class ArrayClass extends GenericClass
{
  public ArrayClass(int aSize,LispObject aInitialItem)
  {
    iArray = new LispObject[aSize];
    for (int i = 0; i < aSize; i++)
    	iArray[i] = aInitialItem;
  }
  public String Send(LispArgList aArgList)
  {
    return null;
  }
  public String TypeName()
  {
    return "\"Array\"";
  }

  public int Size()
  {
    return iArray.length;
  }
  public LispObject GetElement(int aItem) throws Exception
  {
    LispError.LISPASSERT(aItem>0 && aItem<=iArray.length);
    return iArray[aItem-1];
  }
  public void SetElement(int aItem,LispObject aObject) throws Exception
  {
    LispError.LISPASSERT(aItem>0 && aItem<=iArray.length);
    iArray[aItem-1] = aObject;
  }
  LispObject[] iArray;
}
