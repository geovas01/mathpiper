package org.mathpiper.ide.piper_me;


/** Behaviour for substituting sub-expressions.
 */
interface SubstBehaviourBase
{
  public boolean Matches(LispPtr aResult, LispPtr aElement) throws Exception;
};
