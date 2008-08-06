package org.mathrider.piper_me;


abstract class LispArgList
{
  public abstract int NrArguments();
  public abstract String GetArgument(int aIndex);
  public abstract boolean Compare(int aIndex, String aString);
}
