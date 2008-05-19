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
import jscicalc.Base;
import jscicalc.pobject.*;
import jscicalc.complex.Complex;
import jscicalc.OObject;

/**
 * EqualsButton requests that CalculatorApplet object evaluates the current
 * expression (shown in EntryLabel) and show the result in DisplayPanel.
 * Additionally it asks for the last object in the Parser (last PObject object of
 * expression) and depending on the value it gets may add an Ans Object to the
 * expression before asking that it be evaluated.
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.6 $
 */
public class EqualsButton extends CalculatorButton {

    /**
     * Constructor used by HexButton, DecButton, OctButton and BinButton. Although
     * they all use the EqualsButton.actionListener(), we should still setup the
     * objects before adding the ActionListener and so we don&rsquo;t use
     * EqualsButton( CalculatorApplet ).
     */
    protected EqualsButton(){
    }

    /**
     * The standard constructor. It knows about the applet.
     * @param applet The <em>controller</em> object.
     */
    public EqualsButton( CalculatorApplet applet ){
	this.applet = applet;
	this.pobject = new Equals();
	setText();
	setTextSize();
	tooltip = "evaluates the expression you have just typed";
	shortcut = '=';
	setToolTipText();
	changeBase = ChangeBase.NONE;
	addActionListener( this );
    }

    /**
     * The EqualsButton action is to ask the CalculatorApplet object to evaluate
     * the current expression (regardless or the caret position (see EntrlLabel),
     * possibly adding an Ans object if that is needed and the user could reasonably
     * have implied it.
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
	    if( !getApplet().getParser().isEmpty() ){
		PObject p = getApplet().getParser().getLast();
		OObject o = getApplet().getValue();
		if( !(o instanceof jscicalc.Error) && ( p instanceof RFunction ||
							p instanceof DFunction ||
							p instanceof MFunction ||
							p instanceof AFunction ) ){
		    Ans ans = new Ans();
		    ans.setValue( o );
		    getApplet().insert( ans );
		    getApplet().updateDisplay( true, true );
		}
		getApplet().pushHistory();
		OObject value = getApplet().getParser()
		    .evaluate( getApplet().getAngleType() );
		getApplet().setValue( value );
	    }
	    getApplet().setShift( false );
	    getApplet().newExpression();
	    setBase();
	    getApplet().updateDisplay( false, true );
	    getApplet().requestFocusInWindow();
	}
    }
    
    /**
     * Used by actionPerformed() to set the Base of the calculator, which
     * is stored in the CalculatorApplet object and displayed in the ExtraPanel.
     * Note that the function uses a local ChangeBase object and so can be NONE,
     * indicating no change of base. Thus the default action here is to do nothing.
     */
    private void setBase(){
	switch( changeBase ){
	case DECIMAL:
	    getApplet().setBase( Base.DECIMAL );
	    break;
	case BINARY:
	    getApplet().setBase( Base.BINARY );
	    break;
	case OCTAL:
	    getApplet().setBase( Base.OCTAL );
	    break;
	case HEXADECIMAL:
	    getApplet().setBase( Base.HEXADECIMAL );
	    break;
	default:
	}
    }

    /**
     * An enum indicating whether actionPerformed() should change base and if
     * so to what.
     */
    protected ChangeBase changeBase;
    
    /**
     * What Base can we change to (NONE means no change)
     */
    protected enum ChangeBase {
	BINARY, OCTAL, DECIMAL, HEXADECIMAL, NONE;
    }
    private static final long serialVersionUID = 1L;   
}
