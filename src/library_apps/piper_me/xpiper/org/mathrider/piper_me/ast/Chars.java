package org.mathrider.piper_me.ast;

public class Chars extends Expression {
  
  private String value;

  
  public Chars(String value) {
    super();
    this.value = value;
  }

  public String text() {
    return value;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final Chars other = (Chars) obj;
    if (value == null) {
      if (other.value != null)
        return false;
    } else if (!value.equals(other.value))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    return value;
  }
  
  public static final Chars FALSE = new Chars("false");
  public static final Chars TRUE = new Chars("true");
  
}//`class`
