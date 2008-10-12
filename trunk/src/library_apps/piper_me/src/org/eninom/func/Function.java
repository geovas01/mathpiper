package org.eninom.func;

//! Abstract Function
/*<literate>*/
/**
 * interface for functions. We use only unary functions (Though some convenience
 * wrappers exist for binary functions and operations, otherwise use cons,
 * lists, or custom-objects).<br />
 * <br />
 * In order to maintain simplicity, we don't provide support for cyclic data
 * structures and lazy evaluation. Recursion, however, is supported by a special
 * recursion operator.
 */
public abstract class Function<M, N> {

  /**
   * This function must be implemented by any concrete subclass. It takes an
   * argument of the function's domain and returns its image under the function.
   */
  public abstract N get(M e);

}// `class`
