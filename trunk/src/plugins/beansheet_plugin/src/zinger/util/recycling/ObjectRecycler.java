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
 * Basic implementation of <code>ObjectPool</code> interface.
 * This implementation uses an internal collection to manage pooled instances.  It is not thread-safe
 * and does not guaranteed how large it will grow.
 */
public class ObjectRecycler implements ObjectPool
{
    /**
     * Instance cache.
     */
    protected final List instances;

    protected final ObjectGenerator generator;

    public ObjectRecycler(ObjectGenerator generator, List instances)
    {
		this.instances = instances == null ? new LinkedList() : instances;
        this.generator = generator;
    }

    public ObjectRecycler(ObjectGenerator generator)
    {
		this(generator, null);
	}

    public void instantiate(final int nInstances)
    {
        for(int i = 0; i < nInstances; i++)
        {
            recycleObject(generator.newObject());
        }
    }

    /**
     * Calls <code>getObject(null)</code>.
     * @see #getObject(java.lang.Object)
     */
    public final Object getObject() throws IllegalArgumentException
    {
        return getObject(null);
    }

    /**
     * Gets an instance either out of the cache, or a newly generated one and
     * asks it to be prepared for reuse.
     * @see zinger.util.recycling.ObjectGenerator#prepareObject(java.lang.Object, java.lang.Object)
     */
    public Object getObject(final Object arg) throws IllegalArgumentException
    {
        Object obj;
		do
		{
			obj = instances.isEmpty() ? generator.newObject() : instances.remove(0);
		}
		while(!generator.prepareObject(obj, arg));

        return obj;
    }

    /**
     * Puts <code>obj</code> into the cache.  It is the user's responsibility to make sure only
     * the objects that were generated this instance are returned.  If <code>getObject</code>
     * method encounters a bad instance from the cache, it will discard it and go on to the next one.
     * @see #getObject(java.lang.Object)
     * @return whether the object was recycled
     */
    public boolean recycleObject(final Object obj)
    {
        instances.add(0, obj);
        return true;
    }

    public void clearCache()
    {
        instances.clear();
    }
}
