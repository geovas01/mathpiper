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
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *  
 */
public class ApplyFast extends BuiltinFunction
{
    
    private ApplyFast()
    {
    }

    public ApplyFast(String functionName)
    {
        this.functionName = functionName;
    }

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {

        Cons oper = getArgumentPointer(aEnvironment, aStackTop, 1);

        Cons args = getArgumentPointer(aEnvironment, aStackTop, 2);

        if(! (args.car() instanceof Cons)) LispError.checkArgument(aEnvironment, aStackTop, 2, "ApplyFast");
        if(((Cons) args.car()) == null) LispError.throwError(aEnvironment, aStackTop, 2);

        // Apply a pure string
        if (oper.car() instanceof String)
        {
            
            Cons result = Utility.applyString(aEnvironment, aStackTop, (String) oper.car(), ((Cons) args.car()).cdr());
            setTopOfStackPointer(aEnvironment, aStackTop,result);
        } else
        {   // Apply a pure function {args,body}.

            Cons args2 = ((Cons) args.car()).cdr();
            if(! (oper.car() instanceof Cons)) LispError.checkArgument(aEnvironment, aStackTop, 1, "ApplyFast");
            if( (Cons) oper.car() == null) LispError.checkArgument(aEnvironment, aStackTop, 1, "ApplyFast");
            
            setTopOfStackPointer(aEnvironment, aStackTop, Utility.applyPure(aStackTop, oper, args2, aEnvironment));
        }
    }
}



/*
%mathpiper_docs,name="ApplyFast",categories="User Functions;Functional Operators",access="private"
*CMD ApplyFast --- a fast built-in version of the Apply function
*CALL
	ApplyFast(fn, arglist)

*PARMS

{fn} -- function to apply

{arglist} -- list of arguments

*DESC
This function is a fast built-in version of the Apply function.

*E.G.
In> ApplyFast("+", {5,9});
Result: 14

In> ApplyFast({{x,y}, x-y^2}, {Cos(a), Sin(a)});
Result: Cos(a)-Sin(a)^2

In>  ApplyFast(Lambda({x,y}, x-y^2), {Cos(a), Sin(a)});
Result: Cos(a)-Sin(a)^2

*SEE Apply
%/mathpiper_docs
*/
