/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mathpiper.ui.gui.calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JApplet;

/**
 *
 * @author tkosan
 */
public class Applet extends JApplet {



   public void init(){
     setLayout(new BorderLayout(1, 2));
     setBackground(Color.white);

     add("Center", new CalculatorPanel());

   }

  public void start(){
     System.out.println("Applet starting.");
  }

  public void stop(){
     System.out.println("Applet stopping.");
  }

  public void destroy(){
     System.out.println("Destroy method called.");
  }

  


}
