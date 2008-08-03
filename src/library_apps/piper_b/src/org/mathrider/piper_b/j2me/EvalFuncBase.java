package org.mathrider.piper_b.j2me;



// class EvalFuncBase defines the interface to 'something that can
// evaluate'
abstract class EvalFuncBase
{
  public abstract void Evaluate(LispPtr aResult,LispEnvironment aEnvironment, LispPtr aArguments) throws Exception;
};
