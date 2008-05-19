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
import jscicalc.pobject.*;

/**
 * CalculatorPanel that gets shown when you press the shift button.
 * Handles inverses of simple scientific functions and less common scientific 
 * functions.
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.5 $
 */
public class ShiftPanel extends CalculatorPanel {
    /**
     * Constructor. See CalculatorPanel.createCalculatorPanel() to see what
     * this does.
     * @param applet The CalculatorApplet object needed for the buttons and
     * DisplayPanel
     * @param sbt Used to identify what to construct
     * @param colour What colour should the panel background be?
     */
    public ShiftPanel( CalculatorApplet applet, SpecialButtonType sbt,
		       java.awt.Color colour ){
	super( applet, sbt, colour );
	if( sbt != SpecialButtonType.SHIFT && sbt != SpecialButtonType.SHIFT_STAT )
	    throw new RuntimeException( "ShiftPanel instantiated wrongly." );
    }

    /**
     * Defines all the standard buttons for this panel. This is where
     * you add new functions or change which button is associated with each
     * function.
     */
    protected void setButtons(){
	buttons().elementAt( 1 ).setPObject( new Factorial() );
	buttons().elementAt( 2 ).setPObject( new ASin( AngleType.DEGREES ) );
	buttons().elementAt( 3 ).setPObject( new TenX() );
	buttons().elementAt( 4 ).setPObject( new Permutation() );
	buttons().elementAt( 6 ).setPObject( new Cube() );
	buttons().elementAt( 7 ).setPObject( new ACos( AngleType.DEGREES ) );
	buttons().elementAt( 8 ).setPObject( new Exp() );
	buttons().elementAt( 9 ).setPObject( new Conjugate() );
	buttons().elementAt( 11 ).setPObject( new CubeRoot() );
	buttons().elementAt( 12 ).setPObject( new ATan( AngleType.DEGREES ) );
	buttons().elementAt( 13 ).setPObject( new Root() );
	buttons().elementAt( 24 ).setPObject( new Pi() );
	createKeyMap();
    }
    private static final long serialVersionUID = 1L;   
}
