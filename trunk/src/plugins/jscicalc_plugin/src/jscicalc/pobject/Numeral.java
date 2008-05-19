/** @file
 * Copyright (C) 2003&ndash;4 John D Lamb (J.D.Lamb@btinternet.com)
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

package jscicalc.pobject;

/**
 * This class represents anything that might be a numeral. This includes
 * 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, A, B, C, D, E, F and a base point.
 * This is more unusual amongst PObject classes because its constructor takes
 * and argument. The argument is used as the symbol and shortcut for the numeral.
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.5 $
 */
public class Numeral extends PObject { 
    
    /**
     * Each numeral is represented by a character.
     * @param c The character reprenting this Numeral
     */
    public Numeral( char c ){
	this.c = c;
	ftooltip = "";
	fshortcut = c;
    }
    
    /**
     * Useful to create a representation.
     * @return The character reprenting this Numeral
     */
    public char get(){
	return c;
    }
    
    /**
     * Useful to create a representation.
     * @return A String reprenting this Numeral
     */
    public String name(){
	return Character.toString( get() );
    }

    /**
     * Useful to create a representation.
     * @return An array of String objects reprenting this Numeral
     */
    public String[] name_array(){
	String[] string = new String[1];
	string[0] = Character.toString( c );
	return string;
    }

    /**
     * Each numeral is represented by a character.
     */
    private final char c;
}
