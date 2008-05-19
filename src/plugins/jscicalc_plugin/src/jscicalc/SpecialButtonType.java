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

/**
 * This enumerated class is used by CalculatorPanel and CalculatorApplet to
 * deal with &lsquo;special buttons&rsquo;.
 * In particular CalculatorPanel uses it to identify
 * all its subclasses and CalculatorApplet asks CalculatorPanel for an instance
 * corresponding to each value of this type. If you want to add a CalculatorPanel
 * create an extra value here.
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.5 $
 */
public enum SpecialButtonType {
    NONE, SHIFT, STAT, SHIFT_STAT, HEX, SHIFT_HEX;
    
    public String toString(){
	switch( this ){
	case NONE:
	    return "NONE";
	case STAT:
	    return "STAT";
	case SHIFT:
	case SHIFT_STAT:
	case SHIFT_HEX:
	    return "SHIFT";
	case HEX:
	    return "HEX";
	}
	throw new AssertionError( "Unknown SpecialButtonType" );
    }
}
