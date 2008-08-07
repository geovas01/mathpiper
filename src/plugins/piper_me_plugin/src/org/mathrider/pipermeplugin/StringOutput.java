//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathrider.pipermeplugin;

import java.io.*;
import org.mathrider.piper_me.LispOutput;

class StringOutput extends LispOutput
{
	StringBuffer stringBuffer;
	
	public StringOutput()
	{
		this.stringBuffer = new java.lang.StringBuffer();
	}
	
	public void PutChar(char aChar)
	{
		this.stringBuffer.append(aChar);
	}

	    
	/*public void setStringBuffer(StringBuffer stringBuffer)
	{
		this.stringBuffer = stringBuffer;
	}//end method.*/
	
	
	public String toString()
	{
		if(this.stringBuffer.length() != 0)
		{
			String outputMessage = this.stringBuffer.toString();
			this.stringBuffer.delete(0,this.stringBuffer.length());
			return outputMessage;
		}
		else
		{
			return null;
		}//end else.


	}//end method.
	    
}//end class.
		

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
