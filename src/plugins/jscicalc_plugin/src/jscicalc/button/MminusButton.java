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
import jscicalc.pobject.*;
import jscicalc.complex.Complex;
import jscicalc.OObject;

/**
 * Button to subtract from memory. This class could probably be better derived from
 * EqualsButton.
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.6 $
 */
public class MminusButton extends CalculatorButton {

    /**
     * Constructor. Sets PObject to Mminus.
     * @param applet The <em>controller</em> object.
     */
    public MminusButton( CalculatorApplet applet ){
	this.applet = applet;
	this.pobject = new Mminus();
	setText();
	setTextSize();
	tooltip = "subtracts current expression or most<br>recent " +
	    "result from memory value";
	shortcut = 'M';
	setToolTipText();
	addActionListener( this );
    }
    
    /**
     * Evaluates the current expression and subtracts the value from memory.
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
	    OObject m = getApplet().getMemory();
	    getApplet().pushHistory();
	    OObject o = getApplet().getValue();
	    if( !getApplet().getParser().isEmpty() ){
		//System.out.println( "__________" );
		PObject p = getApplet().getParser().getLast();
		if( !(o instanceof jscicalc.Error) && ( p instanceof RFunction ||
							p instanceof DFunction ||
							p instanceof MFunction ||
							p instanceof AFunction ) ){
		    Ans ans = new Ans();
		    ans.setValue( o );
		    getApplet().insert( ans );
		    getApplet().updateDisplay( true, true );
		}
	    } else {
		//System.out.println( "**********" );
		Ans ans = new Ans();
		ans.setValue( o );
		getApplet().insert( ans );
		getApplet().updateDisplay( true, true );
	    }
	    o = getApplet().getParser().evaluate( getApplet().getAngleType() );
	    if( !(o instanceof jscicalc.Error) ){
		OObject q =  m.subtract( o );
		if( !(q instanceof jscicalc.Error) )
		    getApplet().setMemory( q );
		getApplet().setValue( o );
		getApplet().updateDisplay( false, true );
	    }
	    getApplet().setShift( false );
	    getApplet().newExpression();
	    getApplet().requestFocusInWindow();
	}
    }
    private static final long serialVersionUID = 1L;   
}
