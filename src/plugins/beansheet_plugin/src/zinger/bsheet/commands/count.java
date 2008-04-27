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

public class count
{
	public static count INSTANCE = new count();

	protected count()
	{
	}

	public int getCount(Interpreter env, CallStack callStack, Iterator iterator) throws EvalError
	{
		int count = 0;
		for(; iterator.hasNext(); iterator.next())
		{
			++count;
		}
		return count;
	}

	public static int invoke(Interpreter env, CallStack callStack, Iterator iterator) throws EvalError
	{
		return count.INSTANCE.getCount(env, callStack, iterator);
	}
}
