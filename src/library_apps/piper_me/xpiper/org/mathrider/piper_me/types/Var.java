package org.mathrider.piper_me.types;

import org.eninom.collection.HashMap;

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

//! Variable
/*<literate>*/
/**
 * Variables are essentially unique symbols for assigning values to. Our
 * variables have no associated arity: If a variable is assigned a value,
 * the assignment affects all associated and currently defined arities,
 * including 0.
 */
public final class Var extends Expression {
  
  private final String name;

  /**
   * The constructor is package-private: Variables are placed in a pool.
   */
  private Var(String name) {
    this.name = name;
  }

  public String name() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }

  static private HashMap<String, Var> pool = new HashMap<String, Var>();

  /**
   * Returns variable. Each name has a unique associated variable. Currenly,
   * variables are never removed from memory. Doing so would involve close
   * cooperation with the parser and the environment to keep track of used
   * variable names. 
   */
  static public synchronized Var byName(String name) {

    Var v = pool.get(name);
    if (v == null) {
      v = new Var(name);
      pool.put(name, v);
    }
    return v;
  }
}// `class`
