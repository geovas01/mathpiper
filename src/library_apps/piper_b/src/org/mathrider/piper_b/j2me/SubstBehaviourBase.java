package org.mathrider.piper_b.j2me;


/** Behaviour for substituting sub-expressions.
 */
interface SubstBehaviourBase
{
  public boolean Matches(LispPtr aResult, LispPtr aElement) throws Exception;
};
