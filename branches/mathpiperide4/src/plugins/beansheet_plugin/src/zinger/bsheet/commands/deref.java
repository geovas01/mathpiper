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

import java.awt.*;

import zinger.bsheet.*;

public class deref
{
	public static Object invoke(Interpreter env, CallStack callStack, int x, int y) throws EvalError
	{
		BSHTableModel model = CommandUtil.getModel(env);

		Object col = env.get("col");
		Object row = env.get("row");
		if(col != null && row != null && col instanceof Number && row instanceof Number)
		{
			model.requestDependency(x, y, ((Number)col).intValue(), ((Number)row).intValue());
		}
		return model.evalValueAt(x, y);
	}

	public static Object invoke(Interpreter env, CallStack callStack, Point coord, int dx, int dy) throws EvalError
	{
		return deref.invoke(env, callStack, coord.x + dx, coord.y + dy);
	}

	public static Object invoke(Interpreter env, CallStack callStack, Point coord) throws EvalError
	{
		return deref.invoke(env, callStack, coord.x, coord.y);
	}
}
