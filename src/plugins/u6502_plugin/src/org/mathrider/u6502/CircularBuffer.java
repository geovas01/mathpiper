/* {{{ License.
UASM65 - Understandable Assembler for the 6500 series Microprocessor.
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

public class CircularBuffer
{
	private int[] array;
	private int head;
	private int tail;
	
	public CircularBuffer()
	{
		super();
		array = new int[1000];
		head = tail = 0;
	}//end constructor.
	
	
	public void put(int val)
	{
		array[tail++] = val;
		
		if(tail == array.length)
		{
			tail = 0;
		}
		
	}//end method.
	
	
	public int get()
	{
		int val = array[head++];
		
		if(head == array.length)
		{
			head = 0;
		}
		
		return val;
		
	}//end method.
	
	
	public boolean isEmpty()
	{
		if(head == tail)
		{
			return(true);
		}
		else
		{
			return(false);
		}
	}//end method.
	
}//end class

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
