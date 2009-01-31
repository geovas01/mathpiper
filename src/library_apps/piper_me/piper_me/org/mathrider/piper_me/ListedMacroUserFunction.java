package org.mathrider.piper_me;


class ListedMacroUserFunction extends MacroUserFunction
{

  public ListedMacroUserFunction(LispPtr  aParameters) throws Exception
  {
    super(aParameters);
  }
  public boolean IsArity(int aArity)
  {
    return (Arity() <= aArity);
  }
  public void Evaluate(LispPtr aResult, LispEnvironment aEnvironment, LispPtr aArguments) throws Exception
  {
	  LispPtr newArgs = new LispPtr();
    LispIterator iter = new LispIterator(aArguments);
    LispPtr ptr =  newArgs;
    int arity = Arity();
    int i=0;
    while (i < arity && iter.GetObject() != null)
    {
        ptr.setNext(iter.GetObject().Copy(false));
        ptr = (ptr.getNext());
        i++;
        iter.GoNext();
    }
    if (iter.GetObject().getNext() == null)
    {
        ptr.setNext(iter.GetObject().Copy(false));
        ptr = (ptr.getNext());
        i++;
        iter.GoNext();
        LispError.LISPASSERT(iter.GetObject() == null);
    }
    else
    {
    	LispPtr head = new LispPtr();
        head.setNext(aEnvironment.iList.Copy(false));
        head.getNext().setNext(iter.GetObject());
        ptr.setNext(LispSubList.New(head.getNext()));
    }
    super.Evaluate(aResult, aEnvironment, newArgs);
  }
}

