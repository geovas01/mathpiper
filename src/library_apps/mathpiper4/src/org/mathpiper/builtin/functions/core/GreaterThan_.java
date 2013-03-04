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

/**
 *
 *  
 */
public class GreaterThan_ extends BuiltinFunction
{

    private GreaterThan_()
    {
    }

    public GreaterThan_(String functionName)
    {
        this.functionName = functionName;
    }


    LexGreaterThan compare = new LexGreaterThan();

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        compare.Compare(aEnvironment, aStackTop);
    }
}//end class.



/*
%mathpiper_docs,name="GreaterThan?",categories="User Functions;Predicates;Built In"
*CMD GreaterThan? --- comparison predicate
*CORE
*CALL
	GreaterThan?(a,b)

*PARMS
{a}, {b} -- decimal numbers or strings
*DESC
Compare decimal numbers or strings (lexicographically).

*E.G.
In> GreaterThan?(1,1)
Result: False;

In> GreaterThan?("b","a")
Result: True;

*SEE LessThan?, Equal?
%/mathpiper_docs





%mathpiper,name="GreaterThan?",subtype="automatic_test"

Verify(GreaterThan?(2,3),False);
Verify(GreaterThan?(3,2),True);

%/mathpiper

*/
