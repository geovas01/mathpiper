//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathpiper.ide.mathpiperdocsplugin;

public class FunctionInfo
{
	private String name;
	private String description;
	
	public FunctionInfo()
	{
	}
	
	public FunctionInfo(String name, String description)
	{
		this.name = name;
		this.description = description;
	}//end constructor.
	
	public void setName(String name)
	{
		this.name = name;
	}//end method.
	
	public void setDescription(String description)
	{
		this.description = description;
	}//end method.
	
	public String getDescription()
	{
		return description;
	}//end method.
	
	
	public String toString()
	{
		return(this.name);
	}//end method.
	
}//end class

/* {{{ License.
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

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
