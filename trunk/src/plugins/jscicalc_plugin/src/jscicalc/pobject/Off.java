/** @file
 * Copyright (C) 2004 John D Lamb (J.D.Lamb@btinternet.com)
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
 * Information for Off button.
 * @see OffButton
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.4 $
 */
public class Off extends PObject {
    public Off(){
    }

    public String[] name_array(){
	return fname;
    }

    public static void main( String args[] ){
	Off o = new Off();
	javax.swing.JOptionPane.showMessageDialog( null, o.name() );
    }
    
    private final static String[] fname
	= { "O", "F", "F" };
}
