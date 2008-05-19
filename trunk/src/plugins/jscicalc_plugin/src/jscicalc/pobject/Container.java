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
import jscicalc.complex.Complex;
import jscicalc.OObject;

/**
 * This class is used for various PObjects like Ans and Mean that contain
 * a specific double value. For example, when you press the Mean or Ans button
 * on the calculator you get an expression that should be replaced with a double
 * when the expression is evaluated. This class holds the code to handle this.
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.5 $
 */
public abstract class Container extends PObject {
    /**
     * Constructor: initialises the contained value to zero.
     */
    public Container(){
	error = false;
	d = new Double( 0 );
	c = new Complex();
    }
    
    /**
     * We need to can extract the double.
     * @return stored value
     */
    public double dvalue(){
	return d;
    }
    /**
     * We need to can extract the value.
     * @return stored value
     */
    public OObject value(){
	return c;
    }

    /**
     * If we can&rsquo;t store a sensible double, we set flag to say so and can read it
     * here.
     * @return <em>true</em> if the value stored is not sensible.
     */
    public boolean error(){
	return error;
    }

    /**
     * Storage for the double.
     */
    protected double d;
    /**
     * Storage for the OObject result.
     */
    protected OObject c;
    /**
     * error flag.
     */
    protected boolean error;
}
