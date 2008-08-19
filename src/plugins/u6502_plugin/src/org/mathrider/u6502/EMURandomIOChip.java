/* {{{ License.

Copyright (C) 2008 Ted Kosan
                                                                                      
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */ //}}}

package org.mathrider.u6502;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class EMURandomIOChip implements IOChip
{

	private java.util.Random rnd = new java.util.Random( 839344 );

	
	
	public EMURandomIOChip()
	{
		super();
		
	}//Constructor.
	

	
	public int read(int location)
	{	

		return rnd.nextInt(256);

	}//end method.
	
	
	
	public void write(int location, int value)
	{

		
	}//end method.
	
	


	
}//end class.

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
