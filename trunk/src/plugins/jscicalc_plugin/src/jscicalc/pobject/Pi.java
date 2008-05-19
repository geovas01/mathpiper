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

/**
 * Container to hold Pi.
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.6 $
 */
public class Pi extends Container {
    /**
     * Constructor. Sets a tooltip and shortcut. Also stores the value of Pi in
     * the container.
     */
    public Pi(){
	d = new Double( Math.PI );
	c = new Complex( StrictMath.PI );
	ftooltip = "<i>&#960;</i>";
	fshortcut = 'p';
    }

    public String[] name_array(){
	return fname;
    }

    public static void main( String args[] ){
	Pi o = new Pi();
	StringBuilder s = new StringBuilder( "<html>" );
	s.append( o.name() );
	s.append( "</html>" );
	javax.swing.JOptionPane.showMessageDialog( null, s.toString() );
    }
    
    private final static String[] fname = { "<i>&#960;</i>" };
}
