package org.mathrider.piper_b.j2me;

/**
 * class LispIterator works almost like LispPtr, but doesn't enforce
 * reference counting, so it should be slightly faster. This one
 * should be used in stead of LispPtr if you are going to traverse
 * a lisp expression in a non-destructive way.
 */
class LispIterator
{
  public LispIterator(LispPtr aPtr)
  {
    iPtr = aPtr;
  }
  public LispObject GetObject()
  {
    return iPtr.getNext();
  }
  public LispPtr Ptr()
  {
    return iPtr;
  }
  public void GoNext() throws Exception
  {
    LispError.Check(iPtr.getNext() != null,LispError.KLispErrListNotLongEnough);
    iPtr = (iPtr.getNext());
  }
  public void GoSub() throws Exception
  {
    LispError.Check(iPtr.getNext() != null,LispError.KLispErrInvalidArg);
    LispError.Check(iPtr.getNext().SubList() != null,LispError.KLispErrNotList);
    iPtr = iPtr.getNext().SubList();
  }
  LispPtr iPtr;
};

