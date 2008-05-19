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
import jscicalc.pobject.Del;

/**
 * Delete button.
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.5 $
 */
public class DelButton extends CalculatorButton {

    /**
     * Constructor. Sets PObject to Del.
     * @param applet The <em>controller</em> object.
     */
    public DelButton( CalculatorApplet applet ){
	this.applet = applet;
	this.pobject = new Del();
	setText();
	setTextSize();
	tooltip
	    = "deletes the last part of the expression you are typing [backspace]";
	shortcut = 0;
	setToolTipText();
	addActionListener( this );
    }

    /**
     * If the caret position is nonzero, deletes the PObject to the left of the
     * caret position in DisplayLabel.
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
	    if( !getApplet().getOn() )
		return;
	    getApplet().delete();
	    getApplet().updateDisplay( true, true );
	    getApplet().requestFocusInWindow();
	}
    }

    private static final long serialVersionUID = 1L;   
}
