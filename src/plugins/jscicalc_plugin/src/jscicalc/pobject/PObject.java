/** @file
 * Copyright (C) 2004&ndash;5, 2007, 2008 John D Lamb (J.D.Lamb@btinternet.com)
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
import jscicalc.GObject;

/**
 * This class represents any token that can be parsed as input. Since tokens are also
 * represented as buttons on the calculator, there are different representations
 * of the given by name() and short_name().
 * The functions tooltip() and shortcut() give a tooltip and shortcut key for this
 * token/object/button.
 *
 * This is one of the most important classes for the calculator because it
 * encapsulates all the information needed for things you can put in an expression.
 * It contains a name suitable for both CalculatorButton and for part of
 * and Expression used in DisplayLabel. It also contains a shortcut key
 * (used to record waht keys it should respond to) and a tooltip text for
 * its CalculatorButton. Through a hierarchy of subclasses it also 
 * holds functions associated with it. For example, Plus is a PObject containing
 * a functions that adds together two doubles.
 *
 * @see Parser, GObject
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.5 $
 */
public abstract class PObject extends GObject {
        /**
     * The long version of the name. Used for the display and CalculatorButton.
     * @return long version of name.
     */
    public String name(){
	StringBuilder s = new StringBuilder();
	for( String t : name_array() ){
	    s.append( t );
	}
	return s.toString();
    }

    /**
     * The short version of the name. Used for the key.
     * @return short version of name.
     */
    public String shortName(){
	StringBuilder s = new StringBuilder();
	for( String t : name_array() ){
	    if( !t.equals( " " ) )
		s.append( t );
	}
	return s.toString();
    }

    /**
     * Array used to construct name() and shortName() String objects.
     * @return an array
     */
    public abstract String[] name_array();
    /**
     * A tooltip.
     * @return a tooltip
     */ 
    public String tooltip(){
	return ftooltip;
    }
    /**
     * A shortcut key character.
     * @return a character representing a shortcut key
     */ 
    public char shortcut(){
	return fshortcut;
    }
    /**
     * The tooltip string.
     */
    protected String ftooltip;
    /**
     * The shortcut key character
     */
    protected char fshortcut;
}
