/** @file
 * Copyright (C) 2004&ndash;5 John D Lamb (J.D.Lamb@btinternet.com)
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
import jscicalc.pobject.RCL;

/**
 * This button creates a Container with the value currently stored in the
 * calculator memory and adds it to the current expression at the carat poition.
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.4 $
 */
public class RCLButton extends CalculatorButton {

    /**
     * Constructor. Sets PObject to RCL.
     * @param applet The <em>controller</em> object.
     */
    public RCLButton( CalculatorApplet applet ){
	this.applet = applet;
	this.pobject = new RCL();
	setText();
	setTextSize();
	tooltip = "use to recall the value in memory";
	shortcut = 'R';
	setToolTipText();
	addActionListener( this );
    }

    /**
     * Gets memory value from CalculatorApplet and asks CalculatorApplet to
     * place this value in a RCL Container in the current expression.
     * @param actionEvent The event that generated this method call: usually a button
     * press or called when CalculatorApplet responded to the key associated with
     * this button
     */
    public void actionPerformed( java.awt.event.ActionEvent actionEvent ){
	synchronized( applet ){
	    if( getApplet().getMode() != 0 ){
		getApplet().setMode( pobject );
		getApplet().requestFocusInWindow();
		return;
	    }
	    ((RCL)pobject).setValue( getApplet().getMemory() );
	    if( pobject == null )
		return;
	    add( pobject );
	    getApplet().updateDisplay( true, true );
	    if( getApplet().getShift() )
		getApplet().setShift( false );
	}
	getApplet().requestFocusInWindow();
    }

    private static final long serialVersionUID = 1L;   
}
