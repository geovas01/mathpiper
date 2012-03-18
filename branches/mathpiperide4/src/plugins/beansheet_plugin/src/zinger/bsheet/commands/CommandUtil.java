// Bean Sheet
// Copyright (C) 2004 Alexey Zinger
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

package zinger.bsheet.commands;

import bsh.*;

import java.util.*;

import zinger.bsheet.*;

public class CommandUtil
{
	private CommandUtil()
	{
	}

	public static BSHTableModel getModel(Interpreter env) throws EvalError
	{
		return (BSHTableModel)env.get("model");
	}

	public static List toList(Iterator iter, List list)
	{
		if(list == null)
		{
			list = new ArrayList();
		}
		while(iter.hasNext())
		{
			list.add(iter.next());
		}
		return list;
	}
}
