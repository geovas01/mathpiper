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

package org.mathpiper.ide.u6502;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ConfigurationPort implements IOChip
{

	private int register = 0;
	
	private String label = "";


	public ConfigurationPort(String label)
	{
		super();
		
		this.label = label;

	}//end constructor.
	
	public ConfigurationPort()
	{
		this("");
		
	}//end constructor.
	







	public int read(int location)
	{
		//System.out.println("XXXXX " + location);
		if(location > 0)
		{
			return(65);
		}
		else
		{
			return register;
		}
	}//end method.



	public void write(int location, int value)
	{
		register = value;


	}//end method.
	
	
	public void reset()
	{
	}//end method.



}//end class.

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
