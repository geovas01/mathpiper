package org.mathrider.piper_me.eval;

/*
 (C) Oliver Glier 2008. This file belongs to Piper-ME/XPiper, which
 is part of a modified version of Yacas ((C) Ayal Pinkus et al.)

 Currently, the licence terms of this file is simple: With this (AND ONLY THIS)
 file you can do whatever you want to do. Piper-ME/XPiper evolves from
 Yacas with two goals in mind: a) port it to the Java-ME API, and b) become
 a complete redesign of the Yacas core engine. When these efforts are complete,
 we hope we can distribute the Piper engine under GPL with linking exception.
 For the time being, it is not possible to grant such an exception for 
 Piper-ME/XPiper due to its reliance of the original Yacas code.
 */

import org.eninom.collection.mutable.HashMap;

import org.mathrider.piper_me.types.Chars;
import org.mathrider.piper_me.types.Expression;
import org.mathrider.piper_me.types.Var;

//! Begin Rule-Definition
/*<literate>*/
/**
 * A node for the beginning of a function defintion. The node provides builder
 * methods for completion of the functions' expression.
 */
public class RuleDefNode {

  private final Frame frame;

  private final Var funName;

  private final Var[] args;

  private HashMap<Var, Expression> store;

  private Expression predicate = null;

  /**
   * The constructor is package-private as it should only be called from a
   * rule-builder instance.
   */
  RuleDefNode(Frame frame, Var funName, Var[] args,
      HashMap<Var, Expression> store) {
    this.frame = frame;
    this.funName = funName;
    this.args = args;
    this.store = store;
  }

  /**
   * Establish the predicate.
   */
  public RuleDefNode when(Expression p) {
    if (predicate != null) {
      throw new IllegalStateException("unused predicate " + p);
    }
    predicate = p;
    return this;
  }

  /**
   * Establish a rule for a predicate and return the rule builder object. After
   * that, the builder can be reused in order to define another rule for the
   * same pattern.
   */
  public RuleDefNode to(Expression e) {
    if (predicate == null) {
      throw new IllegalStateException("undefined predicate");
    }
    compileRuleExtension(predicate, e);
    predicate = null;
    return this;
  }

  /**
   * Return to the frame. This is only possible if no predicate is defined.
   */
  public Frame end() {
    if (predicate != null) {
      throw new IllegalStateException("unused predicate " + predicate);
    }
    return frame;
  }

  /**
   * Get old expression and append the code (AST) for the new transformation
   * before the old ones. If the predicate is always true, the old expression
   * might be discarded.
   */
  private void compileRuleExtension(Expression predicate,
      Expression expr) {
    if (predicate != Chars.TRUE) {  
      Expression oldExpr = frame.value(funName);
      // TODO: combine old an new expression ...
    }
    store.put(funName, expr);
  }
}// `class`
