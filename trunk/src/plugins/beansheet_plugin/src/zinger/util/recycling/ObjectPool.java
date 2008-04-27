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

/**
 * Allows for reuse of instances without having to constantly instantiate new ones and discard old ones.
 * Whenever there is a high turn-over rate of certain type of objects, relying on instantiation and
 * garbage collection can significantly slow down the system.  Instantiation is a fairly expensive
 * process and garbage collection usually doesn't happen until it has to, which means memory gets
 * eaten away at until the last moment when everything slows down to let GC to kick in.
 * <code>ObjectPool</code> works by simply caching instances.  Instantiation and preparation
 * of instances is delegated to an implementation of <code>ObjectGenerator</code>.
 * <p>
 * Historically, before this interface was added, users had to explicitly refer to <code>ObjectRecycler</code>
 * explicitly.  A better practice now is to only type your references as <code>ObjectPool</code>.
 *
 * @see zinger.util.recycling.ObjectRecycler
 */
public interface ObjectPool
{
	/**
	 * Gets an instance out of the pool with default initialization.  This is equivalent to calling
	 * <code>getObject(null)</code>.
	 *
	 * @see #getObject(java.lang.Object)
	 */
	public Object getObject() throws IllegalArgumentException;

	/**
	 * Gets an instance out of the pool and initializes it using the specified parameter object.
	 *
	 * @param arg Initialization parameter.  If <code>null</code>, default initialziation is used.
	 */
	public Object getObject(Object arg) throws IllegalArgumentException;

	/**
	 * Returns an object into the pool.
	 * By calling this method, the user guarantees not to perform any more operation on the
	 * passed-in instance.  The pool does not guarantee the passed-in instance will be accepted,
	 * or if accepted that it will ever be returned by <code>getObject()</code> or <code>getObject(arg)</code>.
	 *
	 * @see #getObject()
	 * @see #getObject(java.lang.Object)
	 */
	public boolean recycleObject(Object obj);

    /**
     * Clears existing cache.  This could be useful if a memory monitor was connected to this instance
     * and ran in the background.  It could know to discard cache when activity is down and memory usage
     * is high.
     */
    public void clearCache();
}
