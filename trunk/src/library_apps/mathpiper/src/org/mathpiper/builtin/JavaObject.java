/* {{{ License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */ //}}}

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
package org.mathpiper.builtin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.mathpiper.builtin.ArgumentList;

public class JavaObject extends BuiltinContainer {

    private Object javaObject;

    public JavaObject(Object javaObject) {
        this.javaObject = javaObject;
    }

    public String send(ArgumentList aArgList) {
        return null;
    }


    // Narrow a type from String to the
    // narrowest possible type
    protected Object narrow(String argstring) {
        // Try integer
        try {
            return Integer.valueOf(argstring);
        } catch (NumberFormatException nfe) {
        }

        // Try double
        try {
            return Double.valueOf(argstring);
        } catch (NumberFormatException nfe) {
        }

        // Try boolean
        if (argstring.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        } else if (argstring.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }

        // Give up -- it's a string
        return argstring;
    }

    // Narrow the the arguments
    protected Object[] narrow(String argstrings[],
            int startIndex) {
        Object narrowed[] =
                new Object[argstrings.length - startIndex];

        for (int i = 0; i < narrowed.length; ++i) {
            narrowed[i] = narrow(argstrings[startIndex + i]);
        }

        return narrowed;
    }

    // Get an array of the types of the give
    // array of objects
    protected Class[] getTypes(Object objs[]) {
        Class types[] = new Class[objs.length];

        for (int i = 0; i < objs.length; ++i) {
            types[i] = objs[i].getClass();

            // Convert wrapper types (like Double)
            // to primitive types (like double)

            if (types[i] == Double.class) {
                types[i] = double.class;
            }
            if (types[i] == Integer.class) {
                types[i] = int.class;
            }
        }

        return types;
    }

    public String execute(String line[]) throws Exception {

        if (line.length < 1) {
            throw new Exception(
                    "Syntax error: must specify at least a method name");
        }

        // The first two tokens are the class and method
        //String className = line[0];
        String className = javaObject.getClass().getName();
        String methodName = line[0];

        // Narrow the arguments
        Object args[] = narrow(line, 1);
        Class types[] = getTypes(args);

        try {
            // Find the specified class
            Class clas = Class.forName(className);

            // Find the specified method
            Method method = clas.getDeclaredMethod(methodName, types);

            // Invoke the method on the narrowed arguments
            Object retval = method.invoke(javaObject, args);

            // Return the result of the invocation
            return retval.toString();
        } catch (ClassNotFoundException cnfe) {
            throw new Exception(
                    "Can't find class " + className);
        } catch (NoSuchMethodException nsme) {
            throw new Exception(
                    "Can't find method " + methodName + " in " + className);
        } catch (IllegalAccessException iae) {
            throw new Exception(
                    "Not allowed to call method " + methodName + " in " + className);
        } catch (InvocationTargetException ite) {
            // If the method itself throws an exception, we want to save it
            throw (Exception) new Exception(
                    "Exception while executing command").initCause(ite);
        }
    }

    public String typeName() {
        return javaObject.getClass().getName();
    }

    public Object getJavaObject() {
        return javaObject;
    }
}

