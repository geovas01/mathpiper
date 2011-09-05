package org.mathpiper.ide.piper_me;


class LispOperators extends LispAssociatedHash // <LispInFixOperator>
{
  public void SetOperator(int aPrecedence,String aString)
  {
    LispInFixOperator op = new LispInFixOperator(aPrecedence);
    put(aString, op);
  }
  public void SetRightAssociative(String aString) throws Exception
  {
    LispInFixOperator op = (LispInFixOperator)get(aString);
    LispError.Check(op != null,LispError.KLispErrNotAnInFixOperator);
    op.SetRightAssociative();
  }
  public void SetLeftPrecedence(String aString,int aPrecedence) throws Exception
  {
    LispInFixOperator op = (LispInFixOperator)get(aString);
    LispError.Check(op != null,LispError.KLispErrNotAnInFixOperator);
    op.SetLeftPrecedence(aPrecedence);
  }
  public void SetRightPrecedence(String aString,int aPrecedence) throws Exception
  {
    LispInFixOperator op = (LispInFixOperator)get(aString);
    LispError.Check(op != null,LispError.KLispErrNotAnInFixOperator);
    op.SetRightPrecedence(aPrecedence);
  }
}
