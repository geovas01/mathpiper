// Bean Sheet
// Copyright (C) 2004-2005 Alexey Zinger
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

package zinger.bsheet.module;

import bsh.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class CommandImportModule implements Module
{
	protected URL[] cpURLs;
	protected String[] importCommands;

	public void init(Properties config)
	{
		String importCommandsStr = config.getProperty("importCommands");
		if(importCommandsStr == null)
		{
			return;
		}
		try
		{
			this.cpURLs = ModuleFactory.INSTANCE.getCPURLs(config);
		}
		catch(ModuleLoadException ex)
		{
			ex.printStackTrace();
		}
		this.importCommands = importCommandsStr.split("\\s");
	}

	public void addContext(ModuleContext context)
	{
		if(this.importCommands == null)
		{
			return;
		}
		Interpreter bsh = context.getBSH();
		bsh.getNameSpace().getClassManager().setClassLoader(this.getClass().getClassLoader());
		if(this.cpURLs != null)
		{
			for(int i = 0; i < this.cpURLs.length; ++i)
			{
				try
				{
					bsh.getClassManager().addClassPath(this.cpURLs[i]);
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
		}
		for(int i = 0; i < this.importCommands.length; ++i)
		{
			bsh.getNameSpace().importCommands(this.importCommands[i]);
		}
	}

	public void removeContext(ModuleContext context)
	{
	}

	protected String getShortURLStr(String urlStr)
	{
		int slashPos = urlStr.lastIndexOf('/');
		if(slashPos == urlStr.length() - 1)
		{
			slashPos = urlStr.lastIndexOf('/', slashPos - 1);
		}
		return urlStr.substring(slashPos + 1);
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("Command Import (");
		for(int i = 0; i < this.cpURLs.length; ++i)
		{
			if(i > 0)
			{
				sb.append(", ");
			}
			sb.append(this.getShortURLStr(this.cpURLs[i].toString()));
		}
		sb.append(')');
		return sb.toString();
	}
}
