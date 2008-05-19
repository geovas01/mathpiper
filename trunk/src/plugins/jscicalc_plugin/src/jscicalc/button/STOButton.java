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
 * This button creates a Container with the value currently stored in the
 * calculator memory and puts it in memory. Probably could be derived from
 * EqualsButton.
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.6 $
 */
public class STOButton extends CalculatorButton {

    /**
     * Constructor. Sets PObject to STO.
     * @param applet The <em>controller</em> object.
     */
    public STOButton( CalculatorApplet applet ){
	this.applet = applet;
	this.pobject = new STO();
	setText();
	setTextSize();
	tooltip = "evaluates current expression and stores in memory";
	shortcut = 'S';
	setToolTipText();
	addActionListener( this );
    }

    /**
     * Evaluates current expression and stores in memory. If no expression (i.e.
     * expression was evaluated on last button press, takes value from
     * CalculatorApplet instead. In either case, asks CalculatorApplet to store
     * in memory, possibly changing state of ExtraPanel to show a nonzero
     * memory value.
     * @param actionEvent The event that generated this method call: usually a button
     * press or called when CalculatorApplet responded to the key associated with
     * this button
     */
    public void actionPerformed( java.awt.event.ActionEvent actionEvent ){
	synchronized( applet ){
	    if( !(getApplet().getValue() instanceof Complex) ){
		getApplet().requestFocusInWindow();
		return;
	    }
	    getApplet().pushHistory();
	    Complex value = (Complex)(getApplet().getValue());
	    if( getApplet().getMode() != 0 ){
		getApplet().setMode( pobject );
		getApplet().requestFocusInWindow();
		return;
	    }
	    OObject o = getApplet().getValue();
	    if( !getApplet().getParser().isEmpty() ){
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
		o = getApplet().getParser().evaluate( getApplet().getAngleType() );
	    } else {
		o = value;
	    }
	    getApplet().setValue( o );
	    if( !(o instanceof jscicalc.Error) )
		getApplet().setMemory( o );
	    getApplet().updateDisplay( false, true );
	    if( getApplet().getShift() )
		getApplet().setShift( false );
	    getApplet().newExpression();
	    getApplet().requestFocusInWindow();
	}
    }

    private static final long serialVersionUID = 1L;   
}
