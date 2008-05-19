/** @file
 * Copyright (C) 2005 John D Lamb (J.D.Lamb@btinternet.com)
 * Copyright (C) 2007, 2008 John D Lamb (J.D.Lamb@btinternet.com)
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
import jscicalc.Notation;
import jscicalc.pobject.Pol;

/**
 * Button used to toggle scientific notation on or off.
 * 
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.4 $
 */
public class PolButton extends CalculatorButton {

    /**
     * Cosntructor. Sets PObject to Pol.
     * @param applet The <em>controller</em> object
     * behave
     */
    public PolButton( CalculatorApplet applet ){
	//super( applet );
	this.applet = applet;
	this.pobject = new Pol();
	setText();
	tooltip = pobject.tooltip();
	shortcut = pobject.shortcut();
	setTextSize();
	setToolTipText();
	addActionListener( this );
    }

    public void actionPerformed( java.awt.event.ActionEvent actionEvent ){
	synchronized( applet ){
	    if( getApplet().getMode() != 0 ){
		getApplet().setMode( pobject );
		getApplet().requestFocusInWindow();
		return;
	    }
	    getApplet().getNotation().toggle( Notation.POLAR );
	    //System.out.println( "pol" );
	    getApplet().setShift( false );
	    getApplet().updateDisplay( false, true );
	}
    }
    
    private static final long serialVersionUID = 1L;   
}
