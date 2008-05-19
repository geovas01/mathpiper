/** @file
 * Copyright (C) 2005 John D Lamb (J.D.Lamb@btinternet.com)
 * Copyright (C) 2007 John D Lamb (J.D.Lamb@btinternet.com)
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
import jscicalc.pobject.*;

/**
 * CalculatorPanel that gets shown initially. It contains standard numeral buttons,
 * simple scientific functions, memory and arithmetic functions. 
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.6 $
 */
public class PlainPanel extends CalculatorPanel {
    /**
     * Constructor. See CalculatorPanel.createCalculatorPanel() to see what
     * this does.
     * @param applet The CalculatorApplet object needed for the buttons and
     * DisplayPanel
     * @param sbt Used to identify what to construct
     * @param colour What colour should the panel background be?
     */
    public PlainPanel( CalculatorApplet applet, SpecialButtonType sbt,
		       java.awt.Color colour ){
	super( applet, sbt, colour );
	if( sbt != SpecialButtonType.NONE && sbt != SpecialButtonType.STAT )
	    throw new RuntimeException( "PlainPanel instantiated wrongly." );
    }

    /**
     * Defines all the standard buttons for this panel. This is where
     * you add new functions or change which button is associated with each
     * function.
     */
    protected void setButtons(){
	buttons.elementAt( 1 ).setPObject( new Inverse() );
	buttons.elementAt( 2 ).setPObject( new Sin( AngleType.DEGREES ) );
	buttons.elementAt( 3 ).setPObject( new Log() );
	buttons.elementAt( 4 ).setPObject( new Combination() );
	buttons.elementAt( 6 ).setPObject( new Square() );
	buttons.elementAt( 7 ).setPObject( new Cos( AngleType.DEGREES ) );
	buttons.elementAt( 8 ).setPObject( new Ln() );
	buttons.elementAt( 9 ).setPObject( new I() );
	buttons.elementAt( 11 ).setPObject( new SquareRoot() );
	buttons.elementAt( 12 ).setPObject( new Tan( AngleType.DEGREES ) );
	buttons.elementAt( 13 ).setPObject( new Power() );
	buttons.elementAt( 14 ).setPObject( new Variable( 'x' ) );
	buttons.elementAt( 16 ).setPObject( new Numeral( '7' ) );
	buttons.elementAt( 17 ).setPObject( new Numeral( '4' ) );
	buttons.elementAt( 18 ).setPObject( new Numeral( '1' ) );
	buttons.elementAt( 19 ).setPObject( new Numeral( '0' ) );
	buttons.elementAt( 21 ).setPObject( new Numeral( '8' ) );
	buttons.elementAt( 22 ).setPObject( new Numeral( '5' ) );
	buttons.elementAt( 23 ).setPObject( new Numeral( '2' ) );
	buttons.elementAt( 24 ).setPObject( new Numeral( '.' ) );
	buttons.elementAt( 26 ).setPObject( new Numeral( '9' ) );
	buttons.elementAt( 27 ).setPObject( new Numeral( '6' ) );
	buttons.elementAt( 28 ).setPObject( new Numeral( '3' ) );
	buttons.elementAt( 29 ).setPObject( new E() );
	buttons.elementAt( 31 ).setPObject( new LParen() );
	buttons.elementAt( 32 ).setPObject( new Multiply() );
	buttons.elementAt( 33 ).setPObject( new Add() );
	buttons.elementAt( 36 ).setPObject( new RParen() );
	buttons.elementAt( 37 ).setPObject( new Divide() );
	buttons.elementAt( 38 ).setPObject( new Subtract() );
	createKeyMap();
    }
    private static final long serialVersionUID = 1L;   
}
