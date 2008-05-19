/** @file
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

package jscicalc;

/**
 * This class works like an enumerated type and an object of it stores the states of
 * the calculator for handling display output.
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.3 $
 */
public class Notation {
    /**
     * Standard test function.
     */
    public static void main( String args[] ){
	Notation t = new Notation();
	System.out.println( "t is " + (t.scientific() ? "scientific" : "standard") );
	System.out.println( "t is " + (t.standard() ? "standard" : "scientific") );
	t.setScientific();
	System.out.println( "t is " + (t.standard() ? "standard" : "scientific") );
	t.setStandard();
	System.out.println( "t is " + (t.standard() ? "standard" : "scientific") );
	t.toggle( Notation.SCIENTIFIC );
	System.out.println( "t is " + (t.standard() ? "standard" : "scientific") );
    }
    /**
     * Constructor. Sets the notation its default of standard, rectangular, not complex
     */
    public Notation(){
	value = 0; // standard, rectangular, not complex by default
    }

    /**
     * Should we use scientific notation?
     * @return <em>true</em> for scientific notation, <em>false</em> otherwise
     */
    public boolean scientific(){
	return !standard();
    }
    /**
     * Should we use standard notation?
     * @return <em>true</em> for standard notation, <em>false</em> otherwise
     */
    public boolean standard(){
	return (value & SCIENTIFIC) == 0;
    }
    /**
     * Sets scientific notation
     */
    public void setScientific(){
	value |= SCIENTIFIC;
    }
    /**
     * Sets standard notation
     */
    public void setStandard(){
	value &= ~SCIENTIFIC;
    }
    /**
     * Should we use scientific notation?
     * @return <em>true</em> for scientific notation, <em>false</em> otherwise
     */
    public boolean polar(){
	return !rectangular();
    }
    /**
     * Should we use scientific notation?
     * @return <em>true</em> for scientific notation, <em>false</em> otherwise
     */
    public boolean rectangular(){
	return (value & POLAR) == 0;
    }
    /**
     * Sets standard notation
     */
    public void setPolar(){
	value |= POLAR;
    }
    /**
     * Sets standard notation
     */
    public void setRectangular(){
	value &= ~POLAR;
    }
    /**
     * Should we use scientific notation?
     * @return <em>true</em> for scientific notation, <em>false</em> otherwise
     */
    public boolean complex(){
	return !nonComplex();
    }
    /**
     * Should we use scientific notation?
     * @return <em>true</em> for scientific notation, <em>false</em> otherwise
     */
    public boolean nonComplex(){
	return (value & COMPLEX) == 0;
    }
    /**
     * Sets standard notation
     */
    public void setComplex(){
	value |= COMPLEX;
    }
    /**
     * Sets standard notation
     */
    public void setNonComplex(){
	value &= ~COMPLEX;
    }
    /**
     * Toggles notation. Useful, for example, for switching between one notation and
     * another without explicitly identifying the starting notation first
     * @param v The type: Notation.SCIENTIFIC, Notation.POLAR or Notation.COMPLEX
     */
    public void toggle( int v ){
	switch( v ){
	    case SCIENTIFIC:
	    case POLAR:
	    case COMPLEX:
		value ^= v;
		return;
	    default:
		System.out.println( "Warning: unknown toggle" );
		return;
	}
    }

    /**
     * Used to store the enumerated type.
     */
    private int value;

    /**
     * Enumerated value for scientific notation.
     */
    public static final int SCIENTIFIC = 1; 
    /**
     * Enumerated value for polar notation.
     */
    public static final int POLAR = 2; 
    /**
     * Enumerated value for complex notation.
     */
    public static final int COMPLEX = 4; 
}
