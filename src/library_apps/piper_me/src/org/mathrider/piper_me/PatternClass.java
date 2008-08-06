package org.mathrider.piper_me;


/// Wrapper for YacasPatternPredicateBase.
/// This class allows a YacasPatternPredicateBase to be put in a
/// LispGenericObject.
class PatternClass extends GenericClass
{
  public PatternClass(PiperPatternPredicateBase aPatternMatcher)
  {
    iPatternMatcher = aPatternMatcher;
  }

  public boolean Matches(LispEnvironment  aEnvironment, LispPtr aArguments) throws Exception
  {
    LispError.LISPASSERT(iPatternMatcher != null);
    boolean result;
    result = iPatternMatcher.Matches(aEnvironment, aArguments);
    return result;
  }
  public boolean Matches(LispEnvironment  aEnvironment, LispPtr[] aArguments) throws Exception
  {
    LispError.LISPASSERT(iPatternMatcher != null);
    boolean result;
    result = iPatternMatcher.Matches(aEnvironment, aArguments);
    return result;
  }
  //From GenericClass
  public String Send(LispArgList aArgList)
  {
    return null;
  }
  public String TypeName()
  {
      return "\"Pattern\"";
  }

  protected PiperPatternPredicateBase iPatternMatcher;
}


