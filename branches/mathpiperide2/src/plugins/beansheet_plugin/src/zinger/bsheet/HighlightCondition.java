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

import java.awt.*;

public class HighlightCondition implements ConditionalIterator.Condition
{
	protected final BSHTableModel model;
	protected final ConditionalIterator.Condition baseCondition;
	protected final Appearance trueAppearance;
	protected final Appearance falseAppearance;

	public HighlightCondition(BSHTableModel model, ConditionalIterator.Condition baseCondition, Appearance trueAppearance, Appearance falseAppearance)
	{
		this.model = model;
		this.baseCondition = baseCondition;
		this.trueAppearance = trueAppearance;
		this.falseAppearance = falseAppearance;
	}

	public boolean isSatisfied(Object objectValue)
	{
		boolean result = this.baseCondition.isSatisfied(objectValue);
		Appearance appearance = (result ? this.trueAppearance : this.falseAppearance);
		appearance.applyTo(this.model.getAppearance(((Point)objectValue).x, ((Point)objectValue).y, true));
		return result;
	}
}
