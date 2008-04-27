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
import java.util.*;

import zinger.bsheet.*;

public class contains
{
	public static final contains INSTANCE = new contains();

	protected contains()
	{
	}

	public static boolean invoke(Interpreter env,
	                             CallStack callStack,
	                             int col,
	                             int row,
	                             String text,
	                             boolean caseInsensitive) throws EvalError
	{
		return contains.INSTANCE.cellContains(env, callStack, col, row, text, caseInsensitive);
	}

	public static boolean invoke(Interpreter env,
	                             CallStack callStack,
	                             Point p,
	                             int dx,
	                             int dy,
	                             String text,
	                             boolean caseInsensitive) throws EvalError
	{
		return contains.invoke(env, callStack, p.x + dx, p.y + dy, text, caseInsensitive);
	}

	public static boolean invoke(Interpreter env,
	                             CallStack callStack,
	                             Point p,
	                             String text,
	                             boolean caseInsensitive) throws EvalError
	{
		return contains.invoke(env, callStack, p.x, p.y, text, caseInsensitive);
	}

	public boolean cellContains(Interpreter env,
	                            CallStack callStack,
	                            int col,
	                            int row,
	                            String text,
	                            boolean caseInsensitive) throws EvalError
	{
		return this.cellContains(env,
		                         callStack,
		                         col,
		                         row,
		                         text,
		                         caseInsensitive,
		                         true,
		                         true,
		                         true);
	}

	/**
	 * @since 0.9.2
	 */
	public boolean cellContains(Interpreter env,
	                            CallStack callStack,
	                            int col,
	                            int row,
	                            String text,
	                            boolean caseInsensitive,
	                            boolean testRaw,
	                            boolean testEval,
	                            boolean testFormatted) throws EvalError
	{
		if(caseInsensitive)
		{
			text = text.toLowerCase();
		}

		BSHTableModel model = CommandUtil.getModel(env);
		Object raw = model.getValueAt(row, col);
		Object eval = deref.invoke(env, callStack, col, row);
		Object formatted = model.formatValueAt(eval, col, row);

		return testRaw && this.testText(raw, text, caseInsensitive) ||
		       testEval && eval != raw && this.testText(eval, text, caseInsensitive) ||
		       testFormatted && formatted != eval && this.testText(formatted, text, caseInsensitive);
	}

	public boolean testText(Object obj, String text, boolean toLower)
	{
		if(obj == null)
		{
			return text == null;
		}

		String objText = obj.toString();
		if(toLower)
		{
			objText = objText.toLowerCase();
		}

		return objText.indexOf(text) > -1;
	}
}
