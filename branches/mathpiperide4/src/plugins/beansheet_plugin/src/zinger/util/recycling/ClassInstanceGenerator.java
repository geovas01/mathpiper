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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class ClassInstanceGenerator implements ObjectGenerator
{
    public static final Class[] EMPTY_CLASS_ARRAY = new Class[0];

    protected final Constructor constructor;
    protected final Object[] args;

    public ClassInstanceGenerator(final Constructor constructor, final Object[] args) throws IllegalArgumentException
    {
        if(Modifier.isAbstract(constructor.getDeclaringClass().getModifiers()))
        {
            throw new IllegalArgumentException("Class cannot be abstract.");
        }

        if(!Modifier.isPublic(constructor.getModifiers()))
        {
            throw new IllegalArgumentException("Constructor supplied is not public.");
        }

        final Class[] argTypes = constructor.getParameterTypes();
        if(argTypes.length != args.length)
        {
            throw new IllegalArgumentException("Wrong number of constructor arguments supplied (given " + args.length + ", must be " + argTypes.length);
        }
        for(int i = 0; i < args.length; i++)
        {
            if(args[i] != null && !argTypes[i].isAssignableFrom(args[i].getClass()))
            {
                throw new IllegalArgumentException("Wrong parameter type: " + args[i]);
            }
        }

        this.constructor = constructor;
        this.args = args;
    }

    public ClassInstanceGenerator(final Class c) throws IllegalArgumentException
    {
        this(getDefaultConstructor(c), EMPTY_CLASS_ARRAY);
    }

    protected static Constructor getDefaultConstructor(final Class c) throws IllegalArgumentException
    {
        try
        {
            return c.getConstructor(EMPTY_CLASS_ARRAY);
        }
        catch(final NoSuchMethodException ex)
        {
            throw new IllegalArgumentException(c + " does not have parameterless constructor.");
        }
        catch(final SecurityException ex)
        {
            throw new IllegalArgumentException(c + " does not give access to its parameterless constructor.");
        }
    }

    public Object newObject()
    {
        try
        {
            return constructor.newInstance(args);
        }
        catch(final IllegalAccessException ex) {} // should never happen
        catch(final InstantiationException ex) {} // should never happen
        catch(final InvocationTargetException ex) // need a better way to handle this
        {
            ex.printStackTrace();
        }
        catch(final ExceptionInInitializerError ex) // need a better way to handle this
        {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * An empty implementation for the sake of not having to declare this class abstract.
     */
    public boolean prepareObject(final Object obj, final Object arg)
    {
        return true;
    }
}
