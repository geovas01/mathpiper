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

package org.mathpiper.builtin.functions;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.UtilityFunctions;
import org.mathpiper.lisp.behaviours.LocalSymbolSubstitute;

/**
 *
 *  
 */
public class LocalSymbols extends BuiltinFunction
{

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        int numberOfArguments = UtilityFunctions.listLength(getArgumentPointer(aEnvironment, aStackTop, 0));
        int numberOfSymbols = numberOfArguments - 2;

        String atomNames[] = new String[numberOfSymbols];
        String localAtomNames[] = new String[numberOfSymbols];

        int uniqueNumber = aEnvironment.getUniqueId();
        int i;
        for (i = 0; i < numberOfSymbols; i++)
        {
            String atomName = (String) getArgumentPointer(getArgumentPointer(aEnvironment, aStackTop, 0), i + 1).getCons().string();
            LispError.checkArgument(aEnvironment, aStackTop, atomName != null, i + 1);
            atomNames[i] = atomName;
            String newAtomName = "$" + atomName + uniqueNumber;
            String variable = (String) aEnvironment.getTokenHash().lookUp(newAtomName);
            localAtomNames[i] = variable;
        }
        LocalSymbolSubstitute substituteBehaviour = new LocalSymbolSubstitute(aEnvironment, atomNames, localAtomNames, numberOfSymbols);
        ConsPointer result = new ConsPointer();
        UtilityFunctions.substitute(result, getArgumentPointer(getArgumentPointer(aEnvironment, aStackTop, 0), numberOfArguments - 1), substituteBehaviour);
        aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, getResult(aEnvironment, aStackTop), result);
    }
}
