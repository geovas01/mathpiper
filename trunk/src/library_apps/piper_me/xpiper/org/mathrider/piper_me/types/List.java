package org.mathrider.piper_me.types;

import org.eninom.func.CList;

public class List extends Expression {
  
  private CList<Expression> list;
  

  public List(CList<Expression> list) {
    super();
    this.list = list;
  }


  public CList<Expression> values() {
    return list;
  }


  @Override
  public String toString() {
    return list.toString();
  }
}//`class`
