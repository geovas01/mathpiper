/** @file
 * Copyright (C) 2004&ndash;5 John D Lamb (J.D.Lamb@btinternet.com)
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
 * This class represents a PObject that does nothing.
 * It is used so that Calculator Buttons can be blank.
 * This is a singleton class.
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.4 $
 */
public class NullPObject extends PObject {
    private NullPObject(){
	ftooltip = "";
	fshortcut = 0;
    }
    
    public String[] name_array(){
	return fname;
    }

    public static NullPObject instance(){
	if( instance == null )
	    instance = new NullPObject();
	return instance;
    }
    
    private final static String[] fname = { "" };
    /**
     * A null Pobject. Useful for buttons that do nothing.
     */
    private static NullPObject instance = null;
}
