package org.mathrider.piper_me.ast;

import org.eninom.collection.HashMap;
import org.eninom.func.Cons;

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
 * Instances of this class identify variables. In Piper, a variable is a pair of
 * a name and an arity, that is, variables of equal name but distinct arity are
 * different.
 */
public final class Var extends Expression {
  private final String name;

  private final int arity;

  /**
   * The constructor is package-private: Variables are placed in a pool.
   */
  private Var(String name, int arity) {
    this.name = name;
    this.arity = arity;
  }

  public String name() {
    return name;
  }

  public int arity() {
    return arity;
  }

  @Override
  public String toString() {
    return name + "?" + arity;
  }

  static private class Key extends Cons<String, Integer> {
    public Key(String name, int arity) {
      super(name, arity);
    }
  };

  static private HashMap<Key, Var> pool = new HashMap<Key, Var>();

  /**
   * returns variable. Variables are stored in a global pool and identified by
   * name and arity. This method always returns the same object for given name
   * and arity. For compliance with Java ME, weak hashmaps are not used, i.e.
   * the pool is never garbage-collected.
   */
  static public synchronized Var byName(String name, int arity) {
    Key k = new Key(name, arity);
    Var v = pool.get(k);
    if (v == null) {
      v = new Var(name, arity);
      pool.put(k, v);
    }
    return v;
  }

}// `class`
