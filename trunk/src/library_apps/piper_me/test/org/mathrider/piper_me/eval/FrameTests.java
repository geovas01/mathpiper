package org.mathrider.piper_me.eval;

import org.mathrider.piper_me.ast.*;

import junit.framework.TestCase;

public class FrameTests extends TestCase {
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();    
  }
  
  static public void testRuleDefSyntax() {
    Frame frame = new Frame(null);
    Var plus = Var.byName("plus",2);
    Var A = Var.byName("A",0);
    Var B = Var.byName("B",0);
    Var B2 = Var.byName("B",0);
    assertTrue(B == B2);
    Expression P1 = new Expression();
    Expression P2 = new Expression();
    Expression P3 = new Expression();
    Expression e1 = new Expression();
    Expression e2 = new Expression();
    Var[] args = {A, B};
    frame.
      extendGlobal(plus, args).when(P1).to(e1).when(P3).to(e2).end().
      extendLocal(plus, args).when(P2).to(e2).end();
  }
  
  static public void testVariableResolution() {
    Frame outer = new Frame(null);
    Frame inbetween = new Frame(outer);
    Frame inner = new Frame(inbetween);
   
    Var A = Var.byName("A",0);
    Var B = Var.byName("B",0);
    Expression e1 = new Expression();
    Expression e2 = new Expression();
    Expression e3 = new Expression();
    
    outer.setGlobal(A, e1);
    assertEquals(e1, outer.value(A));
    assertEquals(e1, inbetween.value(A));
    assertEquals(e1, inner.value(A));
   
    inbetween.setLocal(A, e2);
    assertEquals(e1, outer.value(A));
    assertEquals(e2, inbetween.value(A));
    assertEquals(e1, inner.value(A));
   
    outer.setGlobal(B, e3);
    assertEquals(e1, outer.value(A));
    assertEquals(e2, inbetween.value(A));
    assertEquals(e1, inner.value(A));
    assertEquals(e3, outer.value(B));
    assertEquals(e3, inbetween.value(B));
    assertEquals(e3, inner.value(B));
    
    
    inner.setGlobal(B, e1);
    inbetween.importGlobal(inner);
    assertEquals(e2, inbetween.value(A));
    assertEquals(e1, inbetween.value(B));
   
  }
}
