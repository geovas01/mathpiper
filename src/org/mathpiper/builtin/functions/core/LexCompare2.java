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

import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.NumberCons;

/**
 *
 *  
 */
abstract public class LexCompare2
{

    abstract boolean lexFunction(String f1, String f2, int aPrecision);

    abstract boolean numFunction(BigNumber n1, BigNumber n2);

    void Compare(Environment aEnvironment, int aStackTop) throws Exception
    {
        Cons argument1 = BuiltinFunction.getArgumentPointer(aEnvironment, aStackTop, 1);

        Cons argument2 = BuiltinFunction.getArgumentPointer(aEnvironment, aStackTop, 2);


        //LispError.check(argument1.getCons() instanceof NumberCons || argument1.getCons() instanceof AtomCons, "The first argument must be a non-complex decimal number or a string.","LexCompare2");
        //LispError.check(argument2.getCons() instanceof NumberCons || argument2.getCons() instanceof AtomCons, "The second argument must be a non-complex decimal number or a string.","LexCompare2");

        if(! (argument1 instanceof NumberCons) && ! (argument1 instanceof AtomCons)) LispError.checkArgumentTypeWithError(aEnvironment, aStackTop, 1, "The first argument must be a non-complex decimal number or a string.");
        if(! (argument2 instanceof NumberCons) && ! (argument2 instanceof AtomCons)) LispError.checkArgumentTypeWithError(aEnvironment, aStackTop, 2, "The second argument must be a non-complex decimal number or a string.");


        boolean cmp;
        BigNumber n1 = (BigNumber) argument1.getNumber(aEnvironment.iPrecision, aEnvironment);
        BigNumber n2 = (BigNumber) argument2.getNumber(aEnvironment.iPrecision, aEnvironment);

        if (n1 != null && n2 != null)
        {
            cmp = numFunction(n1, n2);
        } else
        {

            String str1;
            String str2;
            str1 =  (String) argument1.car();
            str2 = (String) argument2.car();
            if( str1 == null) LispError.checkArgument(aEnvironment, aStackTop, 1);
            if( str2 == null) LispError.checkArgument(aEnvironment, aStackTop, 2);
            // the getPrecision argument is ignored in "lex" functions
            cmp = lexFunction(str1, str2, aEnvironment.iPrecision);
        }

        BuiltinFunction.setTopOfStackPointer(aEnvironment, aStackTop, Utility.putBooleanInPointer(aEnvironment, cmp));
    }
}
