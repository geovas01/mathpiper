package org.mathrider.piper_b.j2me;

/** subst behaviour for changing the local variables to have unique
 * names.
 */
class LocalSymbolBehaviour implements SubstBehaviourBase
{
    public LocalSymbolBehaviour(LispEnvironment aEnvironment,
                         String[] aOriginalNames,
                         String[] aNewNames, int aNrNames)
    {
      iEnvironment = aEnvironment;
      iOriginalNames = aOriginalNames;
      iNewNames = aNewNames;
      iNrNames = aNrNames;
    }
    public boolean Matches(LispPtr aResult, LispPtr aElement) throws Exception
    {
      String name = aElement.getNext().String();
      if (name == null)
          return false;

      int i;
      for (i=0;i<iNrNames;i++)
      {
        if (name == iOriginalNames[i])
        {
          aResult.setNext(LispAtom.New(iEnvironment,iNewNames[i]));
          return true;
        }
      }
      return false;
    }

    LispEnvironment iEnvironment;
    String[] iOriginalNames;
    String[] iNewNames;
    int iNrNames;
};
