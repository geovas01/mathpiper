package org.eninom.func;

//! Abstract Function
/*<literate>*/
/**
 * interface for functions. We use only unary functions (Though some convenience
 * wrappers exist for binary functions and operations, otherwise use cons,
 * lists, or custom-objects).
 */
public interface Function<M, N> {

  /**
   * This function must be implemented by any concrete subclass. It takes an
   * argument of the function's domain and returns its image under the function.
   */
  public N get(M e);
}// `interface`
