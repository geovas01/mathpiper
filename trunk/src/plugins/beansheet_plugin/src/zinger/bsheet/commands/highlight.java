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

import javax.swing.event.*;

import zinger.bsheet.*;

public class highlight
{
	public static final highlight INSTANCE = new highlight();

	protected highlight()
	{
	}

	public static Iterator invoke(Interpreter env,
	                              CallStack callStack,
	                              Iterator iterator,
	                              String expression,
	                              Appearance trueAppearance,
	                              Appearance falseAppearance) throws EvalError
	{
		return highlight.INSTANCE.getConditionalIterator(env, callStack, iterator, expression, trueAppearance, falseAppearance);
	}

	public static Iterator invoke(Interpreter env,
	                              CallStack callStack,
	                              Iterator iterator,
	                              String expression,
	                              Color trueBgColor,
	                              Color falseBgColor) throws EvalError
	{
		Appearance trueAppearance = new Appearance();
		trueAppearance.setBgColor(trueBgColor);

		Appearance falseAppearance = new Appearance();
		falseAppearance.setBgColor(falseBgColor);

		return highlight.invoke(env, callStack, iterator, expression, trueAppearance, falseAppearance);
	}

	public Iterator getConditionalIterator(Interpreter env, CallStack callStack, Iterator iterator, String expression, Appearance trueAppearance, Appearance falseAppearance) throws EvalError
	{
		final BSHTableModel model = CommandUtil.getModel(env);
		return new ConditionalIterator(iterator, this.getCondition(env, callStack, expression, trueAppearance, falseAppearance))
		{
			protected void fetchNext() throws NoSuchElementException
			{
				boolean fireEventIfEnd = this.notEnd;
				super.fetchNext();
				if(fireEventIfEnd && !this.notEnd)
				{
					model.fireTableChanged(new TableModelEvent(model));
				}
			}
		};
	}

	public ConditionalIterator.Condition getCondition(Interpreter env, CallStack callStack, String expression, Appearance trueAppearance, Appearance falseAppearance) throws EvalError
	{
		return new HighlightCondition(CommandUtil.getModel(env), condition.INSTANCE.getCondition(env, callStack, expression), trueAppearance, falseAppearance);
	}
}
