package org.mathrider.piper_me.ast;

import org.eninom.func.CList;
import org.mathrider.piper_me.eval.Evaluator;

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

//! Begin Rule-Definition
/*<literate>*/
/**
 * A node for the beginning of a function defintion. The node provides builder
 * methods for completion of the functions' AST.
 */
public class RuleDefNode {

  private Evaluator eval;

  private Var funName;

  private CList<Var> args;

  /**
   * The constructor is package-private as it should only be called from a
   * rule-builder instance.
   */
  RuleDefNode(Evaluator eval, Var funName, CList<Var> args) {
    this.eval = eval;
    this.funName = funName;
    this.args = args;
  }
}// `class`
