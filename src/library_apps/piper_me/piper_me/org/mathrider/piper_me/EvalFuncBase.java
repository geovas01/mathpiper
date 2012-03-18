package org.mathpiper.ide.piper_me;



// class EvalFuncBase defines the interface to 'something that can
// evaluate'
abstract class EvalFuncBase
{
  public abstract void Evaluate(LispPtr aResult,LispEnvironment aEnvironment, LispPtr aArguments) throws Exception;
};
