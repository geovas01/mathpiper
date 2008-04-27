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

package zinger.util.recycling;

import java.util.*;

/**
 * Performs the same task as its superclass, but allows the ability to specify the maximum number
 * of cached instances.  Useful when we know the approximate range of usage and don't want too many
 * objects sitting cached in memory indefinitely after a surge.
 */
public class CappedObjectRecycler extends ObjectRecycler
{
    protected final int cap;

    public CappedObjectRecycler(ObjectGenerator generator, List instances, int cap)
    {
		super(generator, instances);
		this.cap = cap;
	}

    public CappedObjectRecycler(ObjectGenerator generator, int cap)
    {
		this(generator, null, cap);
    }

	/**
	 * Rejects the object if the instance cache exceeds specified size.
	 */
    public boolean recycleObject(final Object obj)
    {
		if(instances.size() < cap)
		{
			return super.recycleObject(obj);
		}
		return false;
    }
}
