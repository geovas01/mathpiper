/*
(C) Oliver Glier 2008

Eninom-Lib is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.
 
Eninom-Lib is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent
modules, and to copy and distribute the resulting executable under
terms of your choice, provided that you also meet, for each linked
independent module, the terms and conditions of the license of that
module.  An independent module is a module which is not derived from
or based on this library.  If you modify this library, you may extend
this exception to your version of the library, but you are not
obligated to do so.  If you do not wish to do so, delete this
exception statement from your version. */

package org.eninom.func;

//!Chunk for Lazy-Evalution
/*<literate>*/
/**
 * This chunk holds and controls lazy-evaluated values. An
 * instance of this class is partially thread-safe: It is possible
 * that multiple threads access a lazy object concurrently, but
 * for efficiency reasons, no provision is made that prevents
 * from evaluating the function more than once. However, a thread
 * won't reevaluate if it can see the result of a previous
 * evaluation, and processor caches will be synchronized in
 * such way that only complete results become visible. As an
 * example, Concurrent Haskell has simililar behavior. 
 */
@SuppressWarnings("unchecked")
public final class Lazy<M,N> {
  
  /*
   * Define a marker object for black holes. black holes
   * are place holders for unevaluated objects.
   */
  private static Object blackhole = new Object();
  
  /*
   * An unevaluated lazy value consists of a function and
   * the argument it applies to:
   */
  private Function<M,N> f;
  private M arg;
  
  /**
   * The constructor takes a function and an argument. Both
   * are applied when the value is accessed the first time.
   */
  public Lazy(Function<M, N> f, M arg) {
    super();
    this.f = f;
    this.arg = arg;
  }
  
  /*
   * We initialize the result as a black hole. The result
   * is a <i>volatile</i> object in order to synchronize
   * processor caches. Note that this is a result of Java's
   * memory model and does not translate to C++, though
   * similar keywords exist there.
   */
  private volatile Object result = blackhole;
  
  /**
   * Computes the value.
   */
  public N value() {
    if (result == blackhole)
      synchronized(this) {
        if (result == blackhole) {
          result = f.get(arg);
          f = null;
          arg = null;
        }//`if`
      }//`synchronized`
    f = null;
    arg = null;
    return (N) result;
  }
  
  /**
   * Returns true if the value is still blackholed.
   */
  public boolean blackhole() {
    return result == blackhole;
  }
  
  /*
   * Note: One might be tempted to use blackholing for detecting
   * cycles during evaluation. This, however, does only work in
   * single-threaded use.
   */
}//`class`
