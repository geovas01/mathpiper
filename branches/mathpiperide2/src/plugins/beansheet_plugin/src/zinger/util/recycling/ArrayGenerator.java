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

import java.lang.reflect.Array;

public class ArrayGenerator implements ObjectGenerator
{
    public static final int NO_PREPARATION = 0;
    public static final int CHECK_TYPE = 1;
    public static final int RESET_COMPONENTS = 2;

    protected final Class componentType;
    protected final int length;

    protected final int preparationType;

    public ArrayGenerator(final Class componentType, final int length, final int preparationType)
    {
        this.componentType = componentType;
        this.length = length;
        this.preparationType = preparationType;
    }

    public ArrayGenerator(final Class componentType, final int length)
    {
        this(componentType, length, NO_PREPARATION);
    }

    public Object newObject()
    {
        return Array.newInstance(componentType, length);
    }

    public boolean prepareObject(final Object obj, final Object arg) throws IllegalArgumentException
    {
        if(preparationType >= CHECK_TYPE)
        {
            final Class type = obj.getClass().getComponentType();
            if(type == null || !type.isAssignableFrom(componentType))
            {
                return false;
            }
            final Object[] array = (Object[])obj;
            if(array.length != length)
            {
                return false;
            }
            try
            {
                for(int i = 0; i < length; i++)
                {
                    array[i] = arg;
                }
            }
            catch(final ClassCastException ex)
            {
                throw new IllegalArgumentException();
            }
        }
        return true;
    }
}
