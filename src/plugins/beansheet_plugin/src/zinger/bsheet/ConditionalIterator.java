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

import java.util.*;

public class ConditionalIterator implements Iterator
{
	public static interface Condition
	{
		public boolean isSatisfied(Object contextValue);
	}

	protected final Iterator iterator;
	protected final ConditionalIterator.Condition condition;

	protected boolean notEnd = true;
	protected Object nextValue;

	public ConditionalIterator(Iterator iterator, ConditionalIterator.Condition condition)
	{
		this.iterator = iterator;
		this.condition = condition;
		this.fetchNext();
	}

	protected void fetchNext() throws NoSuchElementException
	{
		while(this.iterator.hasNext())
		{
			this.nextValue = this.iterator.next();
			if(this.condition.isSatisfied(this.nextValue))
			{
				return;
			}
		}
		this.notEnd = false;
	}

	public boolean hasNext()
	{
		return this.notEnd;
	}

	public Object next() throws NoSuchElementException
	{
		try
		{
			return this.nextValue;
		}
		finally
		{
			this.fetchNext();
		}
	}

	public void remove() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}
}
