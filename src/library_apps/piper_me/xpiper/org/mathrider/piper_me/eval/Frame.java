package org.mathrider.piper_me.eval;

import org.eninom.collection.HashMap;
import org.eninom.func.Cons;
import org.eninom.iterator.ForwardIterator;

import org.mathrider.piper_me.ast.*;

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

//! Frame
/*<literate>*/
/**
 * This class represents a frame. Frames are created when a block is executed,
 * its parent frame is always the frame of the enclosing execution.
 * Example: With the execution of [w = 3; f(x,y) = [v = 2; x + w * v * y]],
 * f is defined as the expression [v = 2; x + w * v * y]. If f is imported
 * by another frame G and then evaluted for x=4 and y=6, a frame H with
 * x=4 and y=6 and v=2 and parent G is created f's body
 * [v = 2; x + w * v * y] and then x + w * v * y is evaluated with the
 * values from H. The binding of w to 3 from the original outer block of
 * f's definion is lost, and instead the value of w is taken from frame G or
 * its parents.<br /><br />
 * 
 * A value can be bound to a variable either locally or globally. 
 * A global value id visible snd can be extended by child frames, however,
 * redifinions or extensions do not traverse back to parent frames. They
 * can be imported explicitely by other frames. However, frames are not
 * programmatically accessible. Instead, global definitons can be imported
 * from a block. This implies executing the block and afterwards importing
 * all or some of its global values.<br />
 * A local value is invisible for any other frame. It is possible to use
 * both, local and global values, as arguments for function calls.<br /><br />
 * 
 * This class is exporatory. A more sophisticated interpreter might use
 * a less straightforward organization of stackframes.
 */
public final class Frame {

  private Frame parent;

  /*
   * Values are stored in a hashmap. More sophisticated interpreters should
   * compile this map into an plain array and resolve variables to indices.
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
   * import all global definitions from another frame (module)
   */
  public void importGlobal(Frame frame) {
    ForwardIterator<Cons<Var, Expression>> it = 
      frame.overwrites.iterator();
    
    while(it.hasNext()) {
      Cons<Var, Expression> mapping = it.next();
      overwrites.put(mapping.first(), mapping.second());
    }
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
    int depth = 0;
    while ((e == null) && (frame != null)) {
      depth++;
      e = frame.overwrites.get(v);
      frame = frame.parent;
    }//`while`
    if (e == null)
      e = v;
    
    if (depth > 4)
      parent.parent.overwrites.put(v, e);
    
    return e;
  }
}// `class`
