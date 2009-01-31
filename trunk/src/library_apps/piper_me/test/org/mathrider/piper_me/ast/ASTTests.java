package org.mathrider.piper_me.ast;



import org.eninom.func.CList;
import org.mathrider.piper_me.eval.Evaluator;

import junit.framework.TestCase;

public class ASTTests extends TestCase {
  
  
  private static Evaluator eval = null;
  private final static CList<Var> noArgs = CList.empty();
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    eval = new Evaluator();    
  }
  
  static public void testRuleDef() {
    Rules ruleBase = new Rules(eval);
    Var plus = new Var();
    Var A = new Var();
    Var B = new Var();
    Var[] args = {A, B};
    CList<Var> argsList = CList.appendArray(args, noArgs);
    assertEquals(A, argsList.first());
    assertEquals(B, argsList.rest().first());
    assertEquals(noArgs, argsList.rest().rest());
    
    RuleDefNode defBegin = ruleBase.def(plus, argsList);
    assertNotNull(defBegin);
  }
}
