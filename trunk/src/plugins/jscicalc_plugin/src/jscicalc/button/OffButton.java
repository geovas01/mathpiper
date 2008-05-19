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
import jscicalc.pobject.Off;

/**
 * Button to switch calculator off. It doesn&rsquo;t make the calculator disappear because
 * we&rsquo;re emulating a real calculator. Instead it makes the display disappear and
 * prevents buttons other than OnButton from functioning.
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.5 $
 */
public class OffButton extends CalculatorButton {

    /**
     * Constructor. Sets PObject to Off.
     * @param applet The <em>controller</em> object.
     */
    public OffButton( CalculatorApplet applet ){
	this.applet = applet;
	this.pobject = new Off();
	setText();
	setTextSize();
	tooltip = "use to switch calculator off: memory and<br>" +
	    " statistics memory are <i>not</i> cleared";
	shortcut = 'Q';
	setToolTipText();
	addActionListener( this );
    }

    /**
     * Ask CalculatorApplet to change state to off.
     * @param actionEvent The event that generated this method call: usually a button
     * press or called when CalculatorApplet responded to the key associated with
     * this button
     */
    public void actionPerformed( java.awt.event.ActionEvent actionEvent ){
	synchronized( applet ){
	    getApplet().setOn( false );
	    getApplet().clearHistory();
	    getApplet().setShift( false );
	    getApplet().updateDisplay( true, true );
	}
	getApplet().requestFocusInWindow();
    }

    private static final long serialVersionUID = 1L;   
}
