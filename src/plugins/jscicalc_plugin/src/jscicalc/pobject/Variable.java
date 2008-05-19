/** @file
 * Copyright (C) 2003&ndash;4 John D Lamb (J.D.Lamb@btinternet.com)
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

package jscicalc.pobject;

/**
 * This class represents anything that might be a variable. Typically a variable is
 * represented by a single letter.
 * This is more unusual amongst PObject classes because its constructor takes
 * and argument. The argument is used as the symbol and shortcut for the numeral.
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.2 $
 */
public class Variable extends PObject { 
    
    /**
     * Each variable is represented by a character.
     * @param c The character reprenting this Variable
     */
    public Variable( char c ){
	this.c = c;
	ftooltip = "";
	fshortcut = c;
    }
    
    /**
     * Useful to create a representation.
     * @return The character reprenting this Variable
     */
    public char get(){
	return c;
    }
    
    /**
     * Useful to create a representation.
     * @return A String reprenting this Variable
     */
    public String name(){
	return Character.toString( get() );
    }

    /**
     * Useful to create a representation.
     * @return An array of String objects reprenting this Variable
     */
    public String[] name_array(){
	String[] string = new String[2];
	string[0] = "<i>" + Character.toString( c );
	string[1] = "</i><font color=\"white\" size=\"-1\">.</font>"; // italic correction
	return string;
    }

    /**
     * Each numeral is represented by a character.
     */
    private final char c;
}
