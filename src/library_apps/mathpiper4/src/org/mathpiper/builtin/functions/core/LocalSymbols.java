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
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.astprocessors.LocalSymbolSubstitute;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *  
 */
public class LocalSymbols extends BuiltinFunction
{

    private LocalSymbols()
    {
    }

    public LocalSymbols(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        int numberOfArguments = Utility.listLength(aEnvironment, aStackTop, getArgument(aEnvironment, aStackTop, 0));
        int numberOfSymbols = numberOfArguments - 2;

        String atomNames[] = new String[numberOfSymbols];
        String localAtomNames[] = new String[numberOfSymbols];

        int uniqueNumber = aEnvironment.getUniqueId();
        int i;
        for (i = 0; i < numberOfSymbols; i++)
        {
            String atomName = (String) getArgument(aEnvironment, aStackTop, getArgument(aEnvironment, aStackTop, 0), i + 1).car();
            if( atomName == null) LispError.checkArgument(aEnvironment, aStackTop, i + 1);
            atomNames[i] = atomName;
            String newAtomName = "$" + atomName + uniqueNumber;
            String variable = newAtomName;
            localAtomNames[i] = variable;
        }
        LocalSymbolSubstitute substituteBehaviour = new LocalSymbolSubstitute(aEnvironment, atomNames, localAtomNames, numberOfSymbols);

        Cons result = Utility.substitute(aEnvironment, aStackTop, getArgument(aEnvironment, aStackTop, getArgument(aEnvironment, aStackTop, 0), numberOfArguments - 1), substituteBehaviour);
        
        setTopOfStack(aEnvironment, aStackTop, aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, result));
    }
}//end class.




/*
%mathpiper_docs,name="LocalSymbols",categories="Programming Functions;Variables;Built In"
*CMD LocalSymbols --- create unique local symbols with given prefix
*STD
*CALL
	LocalSymbols(var1, var2, ...) body

*PARMS

{var1}, {var2}, ... -- atoms, symbols to be made local

{body} -- expression to execute

*DESC

Given the symbols passed as the first arguments to LocalSymbols a set of local
symbols will be created, and creates unique ones for them, typically of the
form {\$<symbol><number>}, where {symbol} was the symbol entered by the user,
and {number} is a unique number. This scheme was used to ensure that a generated
symbol can not accidentally be entered by a user.

This is useful in cases where a guaranteed free variable is needed,
for example, in the macro-like functions ({For}, {While}, etc.).

*E.G. notest

In> LocalSymbols(a,b)a+b
Result: \$a6+\$b6;

*SEE UniqueConstant
%/mathpiper_docs
*/
