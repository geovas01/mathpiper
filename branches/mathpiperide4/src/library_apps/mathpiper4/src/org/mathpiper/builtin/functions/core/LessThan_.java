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
public class LessThan_ extends BuiltinFunction
{

    private LessThan_()
    {
    }

    public LessThan_(String functionName)
    {
        this.functionName = functionName;
    }


    LexLessThan compare = new LexLessThan();

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        compare.Compare(aEnvironment, aStackTop);
    }
}//end class.




/*
%mathpiper_docs,name="LessThan?",categories="User Functions;Predicates;Built In"
*CMD LessThan? --- comparison predicate
*CORE
*CALL
	LessThan?(a,b)

*PARMS
{a}, {b} -- decimal numbers or strings
*DESC
Compare decimal numbers or strings (lexicographically).

*E.G.
In> LessThan?(1,1)
Result: False;
In> LessThan?("a","b")
Result: True;

*SEE GreaterThan?, Equal?
%/mathpiper_docs





%mathpiper,name="LessThan?",subtype="automatic_test"

Verify(LessThan?(2,3),True);
Verify(LessThan?(3,2),False);

Verify(LessThan?(-1e-115, 0), True);
Verify(LessThan?(-1e-15, 0), True);
Verify(LessThan?(-1e-10, 0), True);
Verify(LessThan?(-1e-5, 0), True);
Verify(LessThan?(-1e-1, 0), True);

%/mathpiper

*/
