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
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *  
 */
public class String_ extends BuiltinFunction
{

    private String_()
    {
    }

    public String_(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackBase) throws Exception
    {
        Cons result = getArgument(aEnvironment, aStackBase, 1);

        boolean resultBoolean ;
         if( result.car() instanceof String  )
         {
             resultBoolean = Utility.isString( (String) result.car() );

         }
        else{
            resultBoolean = false;
        }
        setTopOfStack(aEnvironment, aStackBase, Utility.getBooleanAtom(aEnvironment, resultBoolean));
                
    }
}



/*
%mathpiper_docs,name="String?",categories="User Functions;Predicates;Built In"
*CMD String? --- test for an string
*CORE
*CALL
	String?(expr)

*PARMS

{expr} -- expression to test

*DESC

This function tests whether "expr" is a string. A string is a text
within quotes, e.g. {"duh"}.

*E.G.

In> String?("duh");
Result: True;
In> String?(duh);
Result: False;

*SEE Atom?, Number?
%/mathpiper_docs
*/