package org.mathpiper.builtin.functions.optional.javareflection;

/** Used by Import.  One for each (import) expression. **/
public interface Importer {
  public Class classNamed(String name);
  public void reset();
}

