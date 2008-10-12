package org.eninom.func;

/**
 * WORK IN PROGRESS. Should be able to evaluate lazy,
 * combinators, clist, lambda abstaction etc without
 * resorting to get(). 
 */
public class Evaluator {
  public static <M,N> N evaluate(Function<M,N> f, M arg) {
    return f.get(arg);
  }
}
