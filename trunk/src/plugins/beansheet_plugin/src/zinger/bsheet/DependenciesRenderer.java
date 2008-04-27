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

import java.awt.*;
import java.util.*;

import javax.swing.*;

/**
 * Renders cell dependencies using single lines connecting centers of dependent cells.
 *
 * @author Alexey Zinger (inline_four@yahoo.com)
 */
public class DependenciesRenderer implements ZTable.ExtraRenderer
{
	private final JTable table;

	public DependenciesRenderer(JTable table)
	{
		this.table = table;
	}

	public void paint(Graphics g)
	{
		int columnCount = this.table.getColumnCount();
		int rowCount = this.table.getRowCount();
		int modelColumn;
		Point to;
		Set childDependencies;
		for(int column = 0; column < columnCount; ++column)
		{
			modelColumn = this.table.convertColumnIndexToModel(column);
			for(int row = 0; row < rowCount; ++row)
			{
				childDependencies = this.getDependencies().getDependencies(modelColumn, row, false);
				if(childDependencies != null)
				{
					for(Iterator iterator = childDependencies.iterator(); iterator.hasNext(); )
					{
						to = (Point)iterator.next();
						this.paintDependency(g, row, column, to.y, this.table.convertColumnIndexToView(to.x));
					}
				}
			}
		}
	}

	protected void paintDependency(Graphics g, int fromRow, int fromColumn, int toRow, int toColumn)
	{
		Rectangle fromRect = this.table.getCellRect(fromRow, fromColumn, false);
		Rectangle toRect = this.table.getCellRect(toRow, toColumn, false);
		this.drawArrow(g,
		               fromRect.x + fromRect.width / 2,
		               fromRect.y + fromRect.height / 2,
		               toRect.x + toRect.width / 2,
		               toRect.y + toRect.height / 2);
	}

	protected Dependencies getDependencies()
	{
		return ((BSHTableModel)this.table.getModel()).dependencies;
	}

	protected void drawArrow(Graphics g,
	                         int fromX,
	                         int fromY,
	                         int toX,
	                         int toY)
	{
		g.drawLine(fromX,
		           fromY,
		           toX,
		           toY);
	}
}
