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
 * One- or two-way dependecies collection.
 * This class implements tracking of forward or forward and backward dependencies using
 * of points on a two-dimensional plane using conventional <code>x</code> and <code>y</code>
 * coordinates and <code>Point</code> objects.  A <code>Dependencies</code> object can be set
 * to keep track of backward dependencies or not at creation time.
 *
 * @see #Dependencies(int, int, boolean)
 *
 * @author Alexey Zinger (inline_four@yahoo.com)
 */
public class Dependencies
{
	/**
	 * Forward dependencies grid.
	 */
    private final Set[][] grid;

    /**
     * Backward dependencies grid.
     * May be <code>null</code>.
     *
     * @see #Dependencies(int, int, boolean)
     */
    private final Set[][] backwardGrid;

	/**
	 * Constructs an instance with given dimensions.
	 *
	 * @param width
	 * width of two-dimensional plane
	 *
	 * @param height
	 * height of two-dimensional plane
	 *
	 * @param doBackwardDependencies
	 * if <code>true</code>, <code>backwardGrid</code> will be created and dependencies
	 * will be kept in a two-way form (forward and backward dependencies).  Otherwise,
	 * only forward dependencies will be tracked.
	 *
	 * @see #backwardGrid
	 */
    public Dependencies(int width, int height, boolean doBackwardDependencies)
    {
        this.grid = new Set[width][height];
        this.backwardGrid = doBackwardDependencies ? new Set[width][height] : null;
    }

	/**
	 * Adds a dependency.
	 * If backward dependencies are being tracked, a backward dependency is also added.
	 */
    public void addDependency(int causeX, int causeY, int effectX, int effectY)
    {
        this.getDependencies(causeX, causeY, false, true).add(new Point(effectX, effectY));
        if(this.backwardGrid != null)
        {
            this.getDependencies(effectX, effectY, true, true).add(new Point(causeX, causeY));
        }
    }

	/**
	 * Removes a dependency.
	 * If backward dependencies are being tracked, a backward dependency is also removed.
	 */
    public void removeDependency(int causeX, int causeY, int effectX, int effectY)
    {
        Set dependencies = this.getDependencies(causeX, causeY, false, false);
        if(dependencies != null)
        {
            dependencies.remove(new Point(effectX, effectY));
        }
        if(this.backwardGrid != null)
        {
            dependencies = this.getDependencies(effectX, effectY, true, false);
            if(dependencies != null)
            {
                dependencies.remove(new Point(causeX, causeY));
            }
        }
    }

    /**
     * Returns a set of dependencies for a given point.
     *
     * @param backward
     * if <code>true</code>, the set of backward dependencies is returned if available,
     * otherwise a set of forward dependencies is returned.
     *
     * @param mutable
     * if <code>true</code>, an internal
     * non-<code>null</code> instance of <code>Set</code> is guaranteed to be returned.
     * Even if it's <code>false</code>, an internal <code>Set</code> may be returned and therefore
     * should not be modified if modifications to it are not meant to be persisted in this instance.
     * In this case, use <code>getDependencies(int, int, boolean)</code>
     *
     * @see #getDependencies(int, int, boolean)
     */
    protected Set getDependencies(int x, int y, boolean backward, boolean mutable)
    {
        Set[][] grid = backward ? this.backwardGrid : this.grid;
        if(grid == null)
        {
            throw new UnsupportedOperationException();
        }
        if(grid[x][y] == null && mutable)
        {
            grid[x][y] = new HashSet();
        }
        return grid[x][y];
    }

	/**
	 * Returns a set of unmodifiable dependencies for a given point.
	 * This method calls <code>getDependencies(int, int, boolean, boolean)</code>.  The
	 * resulting <code>Set</code>, however, is wrapped in an unmodifiable proxy.
	 *
	 * @see #getDependencies(int, int, boolean, boolean)
	 */
    public Set getDependencies(int x, int y, boolean backward)
    {
        Set dependencies = this.getDependencies(x, y, backward, false);
        if(dependencies != null)
        {
            dependencies = Collections.unmodifiableSet(dependencies);
        }
        return dependencies;
    }
}
