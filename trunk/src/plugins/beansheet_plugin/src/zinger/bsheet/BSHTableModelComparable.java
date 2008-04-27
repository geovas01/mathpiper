// Bean Sheet
// Copyright (C) 2004-2006 Alexey Zinger
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

import java.util.*;

/**
 * Data row wrapper used for sorting.
 * Sorting <code>BSHTableModel</code> data works by storing raw data in the wrapper objects and then
 * comparing evaluated values.  Comparisons are made in the order of columns provided using
 * custom comparators, if present.
 *
 * @see zinger.bsheet.BSHTableModel#sort(int, int, int[], java.util.Comparator[])
 */
public class BSHTableModelComparable implements Comparable
{
	protected final BSHTableModel model;
	protected final int originalRowIndex;
	protected final int[] columns;
	protected final Comparator[] comparators;

	protected final Object[] data;

	/**
	 * Constructs comparison data row wrapper.
	 * Calls <code>populateData()</code>.
	 *
	 * @see #populateData()
	 */
	public BSHTableModelComparable(BSHTableModel model, int originalRowIndex, int[] columns, Comparator[] comparators)
	{
		this.model = model;
		this.originalRowIndex = originalRowIndex;
		this.columns = columns;
		this.comparators = comparators;

		this.data = new Object[this.columns.length];
		this.populateData();
	}

	/**
	 * Populates data wrapper with data from <code>BSHTableModel</code> object.
	 *
	 * @see #getModel()
	 */
	protected void populateData()
	{
		for(int i = 0; i < this.data.length; ++i)
		{
			this.data[i] = model.getValueAt(this.getOriginalRowIndex(), this.columns[i]);
		}
	}

	public BSHTableModel getModel()
	{
		return this.model;
	}

	public int getOriginalRowIndex()
	{
		return this.originalRowIndex;
	}

	public int compareTo(Object o) throws ClassCastException
	{
		BSHTableModelComparable c2 = (BSHTableModelComparable)o;

		int comparison = 0;

		for(int i = 0; i < this.columns.length && comparison == 0; ++i)
		{
			Object o1 = this.getComparisonValue(i);
			Object o2 = c2.getComparisonValue(i);

			try
			{
				if(o1 != null && o2 != null && o1 instanceof Number && o2 instanceof Number)
				{
					int precision = Math.max(NumberComparator.INSTANCE.getPrecision((Number)o1),
											 NumberComparator.INSTANCE.getPrecision((Number)o2));
					o1 = NumberComparator.INSTANCE.convertToPrecision((Number)o1, precision);
					o2 = NumberComparator.INSTANCE.convertToPrecision((Number)o2, precision);
				}

				if(i < this.comparators.length && this.comparators[i] != null)
				{
					comparison = this.comparators[i].compare(o1, o2);
				}
				else if(o1 != null && o2 != null && o1 instanceof Comparable)
				{
					comparison = ((Comparable)o1).compareTo(o2);
				}
				else if(o1 == null && o2 != null)
				{
					comparison = -1;
				}
				else if(o1 != null && o2 == null)
				{
					comparison = 1;
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				comparison = 0;
			}
		}
		if(comparison == 0)
		{
			comparison = this.getOriginalRowIndex() - c2.getOriginalRowIndex();
		}
		return comparison;
	}

	/**
	 * Returns value used for comparison evaluation.
	 * Calls <code>evalValueAt(int, int)</code> method in the model.
	 *
	 * @param valueIndex internal value index
	 *
	 * @see zinger.bsheet.BSHTableModel#evalValueAt(int, int)
	 */
	public Object getComparisonValue(int valueIndex)
	{
		return this.getModel().evalValueAt(this.columns[valueIndex], this.getOriginalRowIndex());
	}

	public Object getDataValue(int valueIndex)
	{
		return this.data[valueIndex];
	}
}
