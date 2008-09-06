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

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:

package org.mathrider.piper;

import java.net.*;
import java.io.*;

class CDataReader
{
	BufferedReader in;

	public CDataReader()
	{
	}
	public int Open(URL source)
	{
		String mark = null;
		in = null;
		try
		{
			//      URL source = new URL(getCodeBase(), fileName);
			//TODO remove?      in = new DataInputStream(source.openStream());
			in = new BufferedReader(new InputStreamReader(source.openStream()));


			mark = in.readLine();
			//      while(null != (aLine = in.readLine()))
			//        System.out.println(aLine);
		}
		catch(Exception e)
		{
			in = null;
			//       e.printStackTrace();
		}

		//System.out.println("File type: "+mark+" version "+dataFormatVersion);
		if (in != null)
			return 1;
		return 0;
	}
	public String ReadLine()
	{
		try
		{
			String mark = in.readLine();
			return mark;
		}
		catch (Exception e)
		{
		}
		return null;
	}

	public void Close()
	{
		try
		{
			if (in != null)
			{
				in.close();
			}
		}
		catch (Exception e)
		{
		}
		in = null;
	}

};
