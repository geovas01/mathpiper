package org.mathpiper.ide.piper_me;


class UserStackInformation
{
  public UserStackInformation()
  {
    iRulePrecedence = -1;
    iSide = 0;
  }
  public LispPtr iOperator;
  public LispPtr iExpression;
  public int iRulePrecedence;
  public int iSide; // 0=pattern, 1=body
}
