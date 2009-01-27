package org.mathrider.piper_me.xpiper.ast;

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
This class is the builder for an abstract syntax tree. In order to define a
function, this class must  be instantiated. The constructor will need an
interpreter context for making the definitions permanent.  
*/
public final class Fun {
  /**
   * This class returns the node for a defintion of a function with the given name.
   * The returned node provides further methods for completion of the definition. 
   */
  public FunDefNode def(Var FunName) {
    return new FunDefNode(); //TODO
  }
}//`class`
