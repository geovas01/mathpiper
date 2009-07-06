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

import java.lang.reflect.Constructor;
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
    public static Object narrow(String argstring) {
	    //System.out.println("XXXXXXX argstring: " + argstring);
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
            return  Boolean.FALSE;
        }

        // Give up -- it's a string
        return argstring;
    }

    // Narrow the the arguments
    public static Object[] narrow(String argstrings[],
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
    public static Class[] getTypes(Object objs[]) {
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
	    
	    if (types[i] == Boolean.class) {
                types[i] = boolean.class;
            }
        }//end for.

        return types;
    }


    public static JavaObject instantiate(String className, String[] parameters) throws Exception
    {
           if (parameters.length < 1) {
            throw new Exception(
                    "Syntax error: must specify at least a method name");
        }

        // The first two tokens are the class and method
        //String className = line[0];
        //String className = javaObject.getClass().getName();

       //String methodName = parameters[0];

        // Narrow the arguments
        Object args[] = narrow(parameters, 1);
        Class types[] = getTypes(args);

        try {
            // Find the specified class
            Class clas = Class.forName(className);

            Constructor constructor = clas.getConstructor(types);

            Object newObject = constructor.newInstance(args);

            JavaObject newObjectWrapper = new JavaObject(newObject);


        } catch (ClassNotFoundException cnfe) {
            throw new Exception(
                    "Can't find class " + className);
        } catch (InstantiationException nsme) {
            throw new Exception(
                    "Can't instantiate " + className);
        } catch (IllegalAccessException iae) {
            throw new Exception(
                    "Not allowed to instantiate " + className);
        } catch (InvocationTargetException ite) {
            // If the method itself throws an exception, we want to save it
            throw (Exception) new Exception(
                    "Exception while executing command").initCause(ite);
        }//end catch.
        return null;
    }

    public String execute(String parameters[]) throws Exception {

        if (parameters.length < 1) {
            throw new Exception(
                    "Syntax error: must specify at least a method name");
        }

        // The first two tokens are the class and method
        //String className = line[0];
        String className = javaObject.getClass().getName();
        String methodName = parameters[0];

        // Narrow the arguments
        Object args[] = narrow(parameters, 1);
        Class types[] = getTypes(args);

        try {
            // Find the specified class
            Class clas = Class.forName(className);
	   
/*
System.out.println("XXXXX " + methodName);
for(Object ob:types)
{
	System.out.println("XXXXX " + ob.toString());
}
*/

            // Find the specified method
            Method method = clas.getDeclaredMethod(methodName, types);

            // Invoke the method on the narrowed arguments
            Object retval = method.invoke(javaObject, args);

            // Return the result of the invocation
            if (retval == null) {
                //The method returned void.
                return "";
            } else {
                return retval.toString();
            }
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
        }//end catch.
        
    }//end class

    public String typeName() {
        return javaObject.getClass().getName();
    }

    public Object getJavaObject() {
        return javaObject;
    }
}

