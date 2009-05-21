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
import java.lang.reflect.Type;
import java.util.Locale;
import org.mathpiper.builtin.ArgumentList;


public class JavaObject extends BuiltinContainer
{
    private Object javaObject;
    public JavaObject(Object javaObject)
    {
        this.javaObject = javaObject;
    }

	public String send(ArgumentList aArgList)
	{
        //todo:tk:this code is currently only experimental.
        String methodName = (String)aArgList.getArgument(0);
        try {
	    //Class<?> c = Class.forName((String)aArgList.getArgument(0));
            Class<?> c = javaObject.getClass();

	    //Object javaObject = c.newInstance();

	    Method[] allMethods = c.getDeclaredMethods();
	    for (Method m : allMethods) {
		String mname = m.getName();

        Type genericReturnType = m.getGenericReturnType();
		if (!mname.startsWith(methodName) ){ //|| (genericReturnType != boolean.class)) {
		    continue;
		}
 		Type[] pType = m.getGenericParameterTypes();
 		/*if ((pType.length != 1)  || Locale.class.isAssignableFrom(pType[0].getClass())) {
 		    continue;
 		}*/

        //Involking method.
		try {
		    m.setAccessible(true);
		    //Object o = m.invoke(t, new Locale(args[1], args[2], args[3]));
             Object o = m.invoke(javaObject, aArgList.getArgument(1),aArgList.getArgument(2));
		    //out.format("%s() returned %b%n", mname, (Boolean) o);

		// Handle any exceptions thrown by method to be invoked.
		} catch (InvocationTargetException x) {
		    Throwable cause = x.getCause();
		    //err.format("invocation of %s failed: %s%n", mname, cause.getMessage());
		}
	    }

        // production code should handle these exceptions more gracefully
	} catch (IllegalAccessException x) {
	    x.printStackTrace();
	}


		return null;
	}
	public String typeName()
	{
		return "Not implemented yet.";
	}

    public Object getJavaObject() {
        return javaObject;
    }

    


}

