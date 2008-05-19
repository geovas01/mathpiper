/** @file
 * Copyright (C) 2005 John D Lamb (J.D.Lamb@btinternet.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package jscicalc;

/**
 * This litle applet contains a button that when pressed launches a calculator
 * in a JFrame. It illustrates at least one possible way to use the calculator:
 * as an object that appears when required so you can calculate a value.
 * Since we&rsquo;ve found a value, we can then ask CalculatorApplet to send the value
 * to some application of interest (typically a JTextField). In fact this was the
 * original purpose of the calculator.
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.5 $
 */
public class CalcButtonApplet extends javax.swing.JApplet
    implements java.awt.event.ActionListener {

    /**
     * Applet method. Create a calculator on the event-dispatching thread.
     */
    public void init(){
	try {
	    javax.swing.SwingUtilities
		.invokeAndWait( new Runnable() {
			public void run(){ setup(); }
		    } );
	} catch( Exception e ){
	    System.err.println( "InterruptedException" );
	}
    }
    
    /**
     * Sets up the calculator using CalculatorApplet.
     */
    public void setup(){
	frame = CalculatorApplet.createFrame();
	frame.setVisible( false );
	frame.setDefaultCloseOperation( javax.swing.JFrame.HIDE_ON_CLOSE );
	// set up GUI
	getContentPane().removeAll();
	/* set up objects */
	getContentPane().setLayout( new java.awt.BorderLayout() );
	javax.swing.JButton button = new javax.swing.JButton( "calculator" );
	button.addActionListener( this );
	getContentPane().add( button );	
    }

    /**
     * The calculator is set up but initially invisible. Pressing the button changes
     * the state of the calculator to visible or back to invisible.
     * @param actionEvent The event of the button being pressed
     */
    public void actionPerformed( java.awt.event.ActionEvent actionEvent ){
	frame.setVisible( !frame.isVisible() );
    }

    /**
     * A JFrame to hold the calculator.
     */
    private javax.swing.JFrame frame;
}
