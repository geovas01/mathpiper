/** @file
 * Copyright (C) 2005 John D Lamb (J.D.Lamb@btinternet.com)
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
 * Boolean functions have lower priority than plus/minus or multiplication/division
 * and are handled by this class.
 * @see AFunction
 * @see MFunction
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.4 $
 */
public abstract class BoolFunction extends PObject {
    /**
     * Carry out a boolean operation like x AND y.
     * @param x The first value (left of symbol)
     * @param y The second value (left of symbol)
     * @return The result of the operation
     */
    public abstract double function( double x, double y ); 
    /**
     * Carry out a boolean operation like x AND y.
     * @param x The first value (left of symbol)
     * @param y The second value (left of symbol)
     * @return The result of the operation
     */
    public abstract OObject function( OObject x, OObject y ); 
}
