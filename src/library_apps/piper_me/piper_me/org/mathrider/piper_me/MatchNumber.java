package org.mathrider.piper_me;

import org.mathrider.piper_me.arithmetic.BigNumber;

/// Class for matching an expression to a given number.
class MatchNumber extends PiperParamMatcherBase
{
  public MatchNumber(BigNumber aNumber)
  {
    iNumber = aNumber;
  }
  public boolean ArgumentMatches(LispEnvironment  aEnvironment,
		  LispPtr  aExpression,
                                      LispPtr[]  arguments) throws Exception
  {
    if (aExpression.getNext().Number(aEnvironment.Precision()) != null)
      return iNumber.Equals(aExpression.getNext().Number(aEnvironment.Precision()));
    return false;
  }
  protected BigNumber iNumber;
}
