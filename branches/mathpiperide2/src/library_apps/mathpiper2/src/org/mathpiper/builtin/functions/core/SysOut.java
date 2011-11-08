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
package org.mathpiper.builtin.functions.core;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.io.StringOutput;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *
 */
public class SysOut extends BuiltinFunction {
    
    private SysOut()
    {
    }

    public SysOut(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {
        StringOutput out = new StringOutput();
        if (getArgumentPointer(aEnvironment, aStackTop, 1).car() instanceof Cons) {

            Cons subList = (Cons) getArgumentPointer(aEnvironment, aStackTop, 1).car();
            
            Cons  consTraverser = subList;
            consTraverser = consTraverser.cdr();
            while (consTraverser != null)
            {
                aEnvironment.iCurrentPrinter.print(aStackTop, consTraverser, out, aEnvironment);
                consTraverser = consTraverser.cdr();
            }
        }
        String output = out.toString();
        output = output.replace("\"", "");
        System.out.println(output);
        aEnvironment.iCurrentOutput.write(output);
        aEnvironment.iCurrentOutput.write("\n");
        setTopOfStackPointer(aEnvironment, aStackTop, Utility.putTrueInPointer(aEnvironment));

    }//end method.


}//end class.




/*
%mathpiper_docs,name="SysOut",categories="User Functions;Built In;Input/Output",access="experimental"
*CMD SysOut --- similar to the Write function, except a copy of the output is also sent to Java's System.out stream
*CALL
    SysOut()

*DESC
If a function prints side effect output, the output is not displayed until the function returns.  If a function
throws an exception, the output may not be displayed at all.  Therefore, sometimes it is desireable to see
the output as it is printed instead of waiting until the function returns.  SysOut
is similar to the Write function, except it also sends a copy of its side effect output to Java's System.out
stream so that it can be viewed immediately.

*SEE Write
%/mathpiper_docs
*/
