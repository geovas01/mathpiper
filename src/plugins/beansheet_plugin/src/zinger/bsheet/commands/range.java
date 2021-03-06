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
import java.util.regex.*;

import zinger.bsheet.*;

public class range
{
	public static final range INSTANCE = new range();

	public final Pattern rangePattern;

	protected range()
	{
		this.rangePattern = Pattern.compile("([A-Za-z]+)(\\d+):([A-Za-z]+)(\\d+)");
	}

	public Iterator getRangeIterator(Interpreter env, CallStack callStack, String rangeString) throws EvalError
	{
		Matcher matcher = this.rangePattern.matcher(rangeString.toUpperCase());
		if(matcher.matches())
		{
			BSHTableModel model = CommandUtil.getModel(env);
			return new RangeIterator(model.findColumn(matcher.group(1)),
			                         Integer.parseInt(matcher.group(2)),
			                         model.findColumn(matcher.group(3)),
			                         Integer.parseInt(matcher.group(4)));
		}
		throw new IllegalArgumentException();
	}

	public static Iterator invoke(Interpreter env, CallStack callStack, String rangeString) throws EvalError
	{
		return range.INSTANCE.getRangeIterator(env, callStack, rangeString);
	}
}
