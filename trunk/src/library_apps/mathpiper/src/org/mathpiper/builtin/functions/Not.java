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
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.UtilityFunctions;
import org.mathpiper.lisp.cons.SubListCons;

/**
 *
 *  
 */
public class Not extends BuiltinFunction
{

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer evaluated = new ConsPointer();
        evaluated.setCons(getArgumentPointer(aEnvironment, aStackTop, 1).getCons());
        if (UtilityFunctions.isTrue(aEnvironment, evaluated) || UtilityFunctions.isFalse(aEnvironment, evaluated))
        {
            UtilityFunctions.internalNot(getResult(aEnvironment, aStackTop), aEnvironment, evaluated);
        } else
        {
            ConsPointer ptr = new ConsPointer();
            ptr.setCons(getArgumentPointer(aEnvironment, aStackTop, 0).getCons().copy(false));
            ptr.cdr().setCons(evaluated.getCons());
            getResult(aEnvironment, aStackTop).setCons(SubListCons.getInstance(ptr.getCons()));
        }
    }
}



/*
%mathpiper_docs,name="Not",categories="User Functions;Predicates;Built In"
*CMD Not --- logical negation
*CORE
*CALL
	Not expr

*PARMS

{expr} -- a boolean expression

*DESC

Not returns the logical negation of the argument expr. If {expr} is
{False} it returns {True}, and if {expr} is {True}, {Not expr} returns {False}.
If the argument is neither {True} nor {False}, it returns the entire
expression with evaluated arguments.

*E.G.

	In> Not True
	Out> False;
	In> Not False
	Out> True;
	In> Not(a)
	Out> Not a;

*SEE And, Or
%/mathpiper_docs
*/