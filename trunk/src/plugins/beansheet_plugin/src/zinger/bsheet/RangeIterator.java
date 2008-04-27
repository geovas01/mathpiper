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

/**
 * Iterates through cell addresses of a specified rectangle.
 *
 * @see #next()
 *
 * @author Alexey Zinger (inline_four@yahoo.com)
 */
public class RangeIterator implements Iterator
{
	protected final int leftCol;
	protected final int topRow;
	protected final int rightCol;
	protected final int bottomRow;

	protected int col;
	protected int row;

	/**
	 * Constructs an instance ready for iteration of the range specified by its top left and bottom right corners.
	 */
	public RangeIterator(int leftCol, int topRow, int rightCol, int bottomRow)
	{
		this.leftCol = leftCol;
		this.topRow = topRow;
		this.rightCol = rightCol;
		this.bottomRow = bottomRow;

		this.col = leftCol - 1;
		this.row = topRow;
	}

	public boolean hasNext()
	{
		return this.row < this.bottomRow || this.row == this.bottomRow && this.col < this.rightCol;
	}

	/**
	 * Advances through the specified rectangular range.
	 * Advancements are made left to right and top to bottom.
	 *
	 * @return a <code>Point</code>, whose <code>x</code> property represents a model column index
	 * and <code>y</code> property represents a row index
	 *
	 * @throws java.util.NoSuchElementException if the end of the range (bottom right corner)
	 * has already been reached prior to the call
	 */
	public Object next() throws NoSuchElementException
	{
		if(this.col < this.rightCol)
		{
			++this.col;
		}
		else if(this.row < this.bottomRow)
		{
			this.col = this.leftCol;
			++this.row;
		}
		else
		{
			throw new NoSuchElementException();
		}
		return new Point(col, row);
	}

	/**
	 * @throws java.lang.UnsupportedOperationException always
	 */
	public void remove() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}
}
