/** @file
 * Copyright (C) 2004&ndash;5 John D Lamb (J.D.Lamb@btinternet.com)
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
 * Objects for addition and subtraction and also change of sign. The change of sign
 * is eventually done in most cases by converting to Uplus or Uminus, though signs
 * are handled directly for exponents, which must be integer.
 * @see Uplus
 * @see Uminus
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.4 $
 */
public abstract class AFunction extends PObject {
    /**
     * This function carries out an operation like plus or minus.
     * @param x The first value (left of symbol)
     * @param y The second value (right of symbol)
     * @return The result of the operation
     */
    public abstract double function( double x, double y ); 
    /**
     * This function carries out a change of sign (or does nothing) (unary plus
     * or unary minus).
     * @param x The value (right of symbol)
     * @return The result of the operation
     */
    public abstract double function( double x ); 
    /**
     * This function carries out an operation like plus or minus.
     * @param x The first value (left of symbol)
     * @param y The second value (right of symbol)
     * @return The result of the operation
     */
    public abstract OObject function( OObject x, OObject y ); 
    /**
     * This function carries out a change of sign (or does nothing) (unary plus
     * or unary minus).
     * @param x The value (right of symbol)
     * @return The result of the operation
     */
    public abstract OObject function( OObject x ); 
}
