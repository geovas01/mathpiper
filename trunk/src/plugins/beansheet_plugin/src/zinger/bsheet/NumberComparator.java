// Bean Sheet
// Copyright (C) 2005 Alexey Zinger
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

public class NumberComparator implements Comparator
{
	public static final NumberComparator INSTANCE = new NumberComparator();

	public static final int BYTE_PRECISION = 0;
	public static final int SHORT_PRECISION = 1;
	public static final int INT_PRECISION = 2;
	public static final int LONG_PRECISION = 3;
	public static final int FLOAT_PRECISION = 4;
	public static final int DOUBLE_PRECISION = 5;

	protected NumberComparator()
	{
	}

	/**
	 * Compares 2 objects as numbers.
	 *
	 * @throws java.lang.ClassCastException if one of the objects is not an instance of <code>Number</code>
	 * @throws java.lang.IllegalArgumentException if one of the objects is <code>null</code>
	 *
	 * @see #compareNumbers(java.lang.Number, java.lang.Number)
	 */
	public int compare(Object o1, Object o2) throws ClassCastException, IllegalArgumentException
	{
		return this.compareNumbers((Number)o1, (Number)o2);
	}

	/**
	 * Compares 2 numbers.
	 *
	 * @throws java.lang.IllegalArgumentException if one of the objects is <code>null</code>
	 *
	 * @return positive integer if <code>n1</code> is less than <code>n2</code>,
	 * negative integer if <code>n1</code> is greater than <code>n2</code>,
	 * 0 if <code>n1</code> is equal to <code>n2</code>
	 *
	 * @see #compare(java.lang.Object, java.lang.Object)
	 * @see #compareNumbers(java.lang.Number, java.lang.Number, int)
	 */
	public int compareNumbers(Number n1, Number n2) throws IllegalArgumentException
	{
		return this.compareNumbers(n1, n2, Math.max(this.getPrecision(n1), this.getPrecision(n2)));
	}

	/**
	 * Compares 2 numbers using specified number precision.
	 *
	 * @throws java.lang.IllegalArgumentException if one of the objects is <code>null</code>
	 *
	 * @return positive integer if <code>n1</code> is less than <code>n2</code>,
	 * negative integer if <code>n1</code> is greater than <code>n2</code>,
	 * 0 if <code>n1</code> is equal to <code>n2</code>
	 *
	 * @see #compareNumbers(java.lang.Number, java.lang.Number)
	 */
	public int compareNumbers(Number n1, Number n2, int precision) throws IllegalArgumentException
	{
		switch(precision)
		{
			case NumberComparator.BYTE_PRECISION:
			return (int)n1.byteValue() - (int)n2.byteValue();

			case NumberComparator.SHORT_PRECISION:
			return (int)n1.shortValue() - (int)n2.shortValue();

			case NumberComparator.INT_PRECISION:
			int i1 = n1.intValue();
			int i2 = n2.intValue();
			if(i1 < i2)
			{
				return -1;
			}
			if(i1 > i2)
			{
				return 1;
			}
			return 0;

			case NumberComparator.LONG_PRECISION:
			long l1 = n1.longValue();
			long l2 = n2.longValue();
			if(l1 < l2)
			{
				return -1;
			}
			if(l1 > l2)
			{
				return 1;
			}
			return 0;

			case NumberComparator.FLOAT_PRECISION:
			float f1 = n1.floatValue();
			float f2 = n2.floatValue();
			if(f1 < f2)
			{
				return -1;
			}
			if(f1 > f2)
			{
				return 1;
			}
			return 0;

			case NumberComparator.DOUBLE_PRECISION:
			double d1 = n1.doubleValue();
			double d2 = n2.doubleValue();
			if(d1 < d2)
			{
				return -1;
			}
			if(d1 > d2)
			{
				return 1;
			}
			return 0;

			default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Determines a precision index of a number object.
	 */
	public int getPrecision(Number n)
	{
		if(n instanceof Byte)
		{
			return NumberComparator.BYTE_PRECISION;
		}
		if(n instanceof Short)
		{
			return NumberComparator.SHORT_PRECISION;
		}
		if(n instanceof Integer)
		{
			return NumberComparator.INT_PRECISION;
		}
		if(n instanceof Long)
		{
			return NumberComparator.LONG_PRECISION;
		}
		if(n instanceof Float)
		{
			return NumberComparator.FLOAT_PRECISION;
		}
		if(n instanceof Double)
		{
			return NumberComparator.DOUBLE_PRECISION;
		}
		return -1;
	}

	/**
	 * Converts a number object to a specified precision.
	 * If the object is already of the specified precision, the same object is returned,
	 * otherwise a new one is instantiated.
	 */
	public Number convertToPrecision(Number n, int precision)
	{
		if(precision == this.getPrecision(n))
		{
			return n;
		}
		switch(precision)
		{
			case NumberComparator.BYTE_PRECISION:
			return new Byte(n.byteValue());

			case NumberComparator.SHORT_PRECISION:
			return new Short(n.shortValue());

			case NumberComparator.INT_PRECISION:
			return new Integer(n.intValue());

			case NumberComparator.LONG_PRECISION:
			return new Long(n.longValue());

			case NumberComparator.FLOAT_PRECISION:
			return new Float(n.floatValue());

			case NumberComparator.DOUBLE_PRECISION:
			return new Double(n.doubleValue());

			default:
			return null;
		}
	}

	public boolean equals(Object obj)
	{
		return obj instanceof NumberComparator;
	}
}
