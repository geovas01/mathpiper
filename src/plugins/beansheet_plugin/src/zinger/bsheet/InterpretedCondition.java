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

package zinger.bsheet;

import bsh.*;

public class InterpretedCondition implements ConditionalIterator.Condition
{
	protected final BSHTableModel model;
	protected final String expression;

	public InterpretedCondition(BSHTableModel model, String expression)
	{
		this.model = model;
		this.expression = expression;
	}

	public boolean isSatisfied(Object objectValue)
	{
		try
		{
			Interpreter bsh = this.model.bsh;
			bsh.set("val", objectValue);
			Object result = bsh.eval(this.expression);
			return result != null
			    && result instanceof Boolean
			    && ((Boolean)result).booleanValue();
		}
		catch(EvalError ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
}
