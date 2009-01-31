package org.mathrider.piper_me.ast;

import org.eninom.collection.IterableCollection;
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

//! AST Builder
/*<literate>*/
/**
 * This class is the builder for an abstract syntax tree. In order to define a
 * function, this class must be instantiated.
 */
public final class Rules {

  Evaluator eval;

  /**
   * The constructor needs an interpreter context for making the definitions
   * permanent.
   */
  Rules(Evaluator eval) {
    this.eval = eval;
  }

  /**
   * This class returns the node for a defintion of a function rule with the
   * given name and arguments. The returned node provides further methods for
   * completion of the definition.
   */
  public RuleDefNode def(Var funName, CList<Var> args) {
    return new RuleDefNode(this, funName, args);
  }
  
  /**
   * 
   */
  void establish(Var funName, CList<Var> args, IterableCollection<Predicate> predicates, Expression e) {
    //TODO
  }
}// `class`
