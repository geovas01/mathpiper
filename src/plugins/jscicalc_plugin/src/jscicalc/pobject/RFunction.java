/** @file
 * Copyright (C) 2004 John D Lamb (J.D.Lamb@btinternet.com)
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
import jscicalc.OObject;

/**
 * A function such as SquareRoot acts the value on its right.
 * This class specifies what is needed for a RFunction and allows Parser
 * to handle a DFunction without knowing the exact type.
 * @see Parser
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.4 $
 */
public abstract class RFunction extends SFunction {
    /**
     * This function carries out an operation like SquareRoot.
     * @param x The first value (rght of symbol)
     * @return The result of the operation
     */
    public abstract double function( double x ); 
    /**
     * This function carries out an operation like SquareRoot.
     * @param x The first value (rght of symbol)
     * @return The result of the operation
     */
    public abstract OObject function( OObject x ); 
}
