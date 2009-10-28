package org.mathrider.piper_me.tests;

import org.mathrider.piper_me.DefaultFileLocator;
import org.mathrider.piper_me.PiperInterpreter;
import org.mathrider.piper_me.Piperexception;

public class SimpleApp {
  public static void main(String[] args) {
    try {
      PiperInterpreter piper = new PiperInterpreter(new DefaultFileLocator());
      System.out.println(piper.evaluate("2+3"));    
    } catch (Piperexception e) {
      e.printStackTrace();
    }
    
  }
}
