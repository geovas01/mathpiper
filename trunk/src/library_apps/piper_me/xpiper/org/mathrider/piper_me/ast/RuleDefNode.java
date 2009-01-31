package org.mathrider.piper_me.ast;

import org.eninom.collection.ExtendibleArray;
import org.eninom.collection.MutableStack;
import org.eninom.func.CList;

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

  private Rules builder;

  private Var funName;

  private CList<Var> args;
  
  MutableStack<Predicate> predicates = new ExtendibleArray<Predicate>();

  /**
   * The constructor is package-private as it should only be called from a
   * rule-builder instance.
   */
  RuleDefNode(Rules builder, Var funName, CList<Var> args) {
    this.builder = builder;
    this.funName = funName;
    this.args = args;
  }
  
  /**
   * Establish the first predicate.
   */
  public RuleDefNode when(Predicate p) {
    if (predicates.size() != 0)
      throw new IllegalStateException("Predicate list is not empty");
    predicates.push(p);
    return this;
  }
  
  /**
   * Add a predicate to the predicate list.
   */
  public RuleDefNode or(Predicate p) {
    if (predicates.size() == 0)
      throw new IllegalStateException("Predicate list is empty");
    predicates.push(p);
    return this;
  }
  
  /**
   * Establish a rule for the list of collected predicates and return the
   * rule builder object. After that, the list of predicates is empty and
   * the node can be reused in order to define another rule for the same
   * function.
   */
  public RuleDefNode to(Expression e) {
    if (predicates.size() == 0)
      throw new IllegalStateException("Predicate list is empty");
    builder.establish(funName,args,predicates,e);
    predicates = new ExtendibleArray<Predicate>();
    return this;
  }
  
  /**
   * Return to the rule builder object. This is only possible if
   * the list of predicates is empty.
   */
  public Rules endfun() {
    if (predicates.size() != 0)
      throw new IllegalStateException("Predicate list is not empty");
    return builder;
  }
}// `class`
