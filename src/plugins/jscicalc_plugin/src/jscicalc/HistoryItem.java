/** @file
 * Copyright (C) 2005 John D Lamb (J.D.Lamb@btinternet.com)
 * Copyright (C) 2007  John D Lamb (J.D.Lamb@btinternet.com)
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
import jscicalc.pobject.PObject;

/**
 * Used by CalculatorApplet so that UpButton and DownButton can work. Stores
 * all the information needed on current item so that it can be recalled later.
 *
 * I&rsquo;m not sure what the impact of not storing stat mode is. Probably it allows
 * some strange, but not problematic, possibilities.
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.4 $
 */
public class HistoryItem {
    /**
     * Constructor. Stores what we may need to recall.
     * @param list A list that can be transferred to Parser object
     * @param angleType Degrees or Radians
     * @param base The Base: we wouldn&rsquo;t want to parse a hexadecimal as binary
     * @param notation The notation type in use: this might not
     * be necessary.
     */
    HistoryItem( java.util.LinkedList<PObject> list, AngleType angleType,
		 Base base, Notation notation ){
	this.list = list;
	this.angleType = angleType;
	this.base = base;
	this.notation = notation;
    }
    /**
     * A list that Parser can use
     */
    public java.util.LinkedList<PObject> list;
    /**
     * Degrees or radians
     */
    public AngleType angleType;
    /**
     * Base.DECIMAL, Base.BINARY, Base.OCTAL or Base.HEXADECIMAL
     */
    public Base base;
    /**
     * Notation used for results
     */
    public Notation notation;
}
