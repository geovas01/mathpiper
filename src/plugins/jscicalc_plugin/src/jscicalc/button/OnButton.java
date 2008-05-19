/** @file
 * Copyright (C) 2004&ndash;5 John D Lamb (J.D.Lamb@btinternet.com)
 * Copyright (C) 20007  John D Lamb (J.D.Lamb@btinternet.com)
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

package jscicalc.button;
import jscicalc.CalculatorApplet;
import jscicalc.pobject.On;
import jscicalc.complex.Complex;

/**
 * Button with two functions. One is to switch the calculator on, the other to
 * clear the current expression (or the most recently evaluated since EqualsButton
 * and its subclasses leave the expression visible). In fact both functions are
 * always carried out because switching a calculator on when it is already on has
 * no effect and when we sitch on we always want a clear display.
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.4 $
 */
public class OnButton extends CalculatorButton {

    /**
     * Constructor. Sets PObject to On.
     * @param applet The <em>controller</em> object.
     */
    public OnButton( CalculatorApplet applet ){
	this.applet = applet;
	this.pobject = new On();
	setText();
	setTextSize();
	tooltip = "switches the calculator on and clears the display";
	shortcut = 'o';
	setToolTipText();
	addActionListener( this );
    }

    /**
     * Ask CalculatorApplet to set its state to on and clear the DisplayLabel.
     * @param actionEvent The event that generated this method call: usually a button
     * press or called when CalculatorApplet responded to the key associated with
     * this button
     */
    public void actionPerformed( java.awt.event.ActionEvent actionEvent ){
	synchronized( applet ){
	    if( getApplet().getMode() != 0 ){
		getApplet().setMode( 0 );
		getApplet().setShift( false );
	    }
	    getApplet().setOn( true );
	    getApplet().clear();
	    getApplet().setValue( new Complex( 0 ) );
	    getApplet().updateDisplay( true, true );
	    getApplet().requestFocusInWindow();
	}
    }
    
    private static final long serialVersionUID = 1L;   
}
