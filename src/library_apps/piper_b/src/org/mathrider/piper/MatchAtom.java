package org.mathrider.piper;


/// Class for matching an expression to a given atom.
class MatchAtom extends PiperParamMatcherBase
{
  public MatchAtom(String aString)
  {
    iString = aString;
  }
  public boolean ArgumentMatches(LispEnvironment  aEnvironment,
		  LispPtr  aExpression,
                                      LispPtr[]  arguments) throws Exception
  {
    // If it is a floating point, don't even bother comparing
    if (aExpression.getNext() != null)
      if (aExpression.getNext().Number(0) != null)
        if (!aExpression.getNext().Number(0).IsInt())
          return false;
 
    return (iString == aExpression.getNext().String());
  }
  protected String iString;
}
