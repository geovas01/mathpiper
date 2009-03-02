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

import org.mathrider.piper_me.types.*;

//! Frame
/*<literate>*/
/**
 * This class represents a frame for dynamic scoping. 
 * A value can be bound to a variable either locally or globally. 
 * To global value, dynamic scopng applis, while local variables
 * are only visible within a block (and unvisible in inner blocks).
 */
public final class Frame {

  private Frame parent;

  /*
   * Values are stored in a hashmap.
   */
  private HashMap<Var, Expression> 
      locals = new HashMap<Var, Expression>(8),
      overwrites = new HashMap<Var, Expression>(4);
  /**
   * The constructor needs a reference to the parent frame. This one can be
   * null.
   */
  Frame(Frame parent) {
    this.parent = parent;
  }

  /**
   * Extend a rule locally. The new left-hand-side (pattern-predicate) 
   * will considered before the old left-hand-sides. A local rule is
   * not visible in child frames. It is possible the
   * value of a local extension to a function argument.
   */
  public RuleDefNode extendLocal(Var fun, Var[] args) {
    return new RuleDefNode(this, fun, args, locals);
  }
  
  /**
   * Extend a rule locally. The new left-hand-side (pattern-predicate) 
   * will considered before the old left-hand-sides.
   */
  public RuleDefNode extendGlobal(Var fun, Var[] args) {
    return new RuleDefNode(this, fun, args, overwrites);
  }
  
  /**
   * set a value locally. This can be used in order to import expressions
   * from other frames.
   */
  public void setLocal(Var v, Expression e) {
    locals.put(v, e);
  }
  
  /**
   * set a value globally. This can be used in order to import expressions
   * from other frames.
   */
  public void setGlobal(Var v, Expression e) {
    overwrites.put(v, e);
  }
  
  
  /**
   * Return an expression. First, the local definitions are examined. If the
   * value is not defined there, the global definitions of the frame and
   * recursively of the parent frames are examined. If nothing is found,
   * the variable is returned. 
   */
  public Expression value(Var v) {
    Frame frame = this;
    Expression e = locals.get(v);
    /*
     * We don't use recursion in order to prevent stack overflow. We also
     * count the depth until we find a definition. If the depth is larger
     * than a certain value, we cash the definition in the global store
     * of some closer parent.  Note that the modification of the store
     * is unsynchronized. For a multithreaded interpreter it might be
     * necessary to introduce "buffer-frames" before the first frame of a
     * sub-thread.
     */
    while ((e == null) && (frame != null)) {
      e = frame.overwrites.get(v);
      frame = frame.parent;
    }//`while`
    if (e == null)
      e = v;
    
    
    return e;
  }
  
  /**
   * deletes the parent frame for context-free execution:
   */
  public void deleteContext() {
    parent = null;
  }
}// `class`
