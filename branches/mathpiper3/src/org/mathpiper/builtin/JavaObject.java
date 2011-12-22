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

import java.util.ArrayList;
import java.util.List;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;


public class JavaObject extends BuiltinContainer {

    private Object javaObject;

    public JavaObject(Object javaObject) {
        this.javaObject = javaObject;
    }

    public String typeName() {
        return javaObject.getClass().getName();
    }//end method.


    public Object getObject() {
        return javaObject;
    }//end method.

    public static List lispListToJavaList(Environment aEnvironment, int aStackBase,Cons lispList) throws Exception {
        if(! Utility.isList(lispList)) LispError.throwError(aEnvironment, aStackBase, LispError.NOT_A_LIST, "");

        lispList = lispList.cdr();

        ArrayList javaList = new ArrayList();

        while (lispList != null) {

            Object item = lispList.car();
            //item = narrow(item);
            javaList.add(item);

            lispList = lispList.cdr();

        }//end while.

        return javaList;
    }//end method.


    public static double[] lispListToJavaDoubleArray(Environment aEnvironment, int aStackBase, Cons lispList) throws Exception {
        if(! Utility.isList(lispList)) LispError.throwError(aEnvironment, aStackBase, LispError.NOT_A_LIST, "");

        lispList = lispList.cdr(); //Remove List designator.

        double[] values = new double[Utility.listLength(aEnvironment, aStackBase, lispList)];

        int index = 0;
        while (lispList != null) {

            Object item = lispList.car();

            if(!( item instanceof String)) LispError.throwError(aEnvironment, aStackBase, LispError.INVALID_ARGUMENT, "");
            String itemString = (String) item;

            try {
                values[index++] = Double.parseDouble(itemString);
            } catch (NumberFormatException nfe) {
                LispError.raiseError("Can not convert into a double.", aStackBase, aEnvironment);
            }//end try/catch.

            lispList = lispList.cdr();

        }//end while.

        return values;

    }//end method.
 

}//end class.

