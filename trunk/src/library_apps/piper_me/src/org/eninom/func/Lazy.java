package org.eninom.func;

public final class Lazy<M,N> {
  
  private static Object blackhole = new Object();
  
  private Function<M,N> f;
  private M arg;
  public Lazy(Function<M, N> f, M arg) {
    super();
    this.f = f;
    this.arg = arg;
  }
  
  private volatile Object result = blackhole;
  
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
  
  public boolean blackhole() {
    return result == blackhole;
  }
}//`class`
