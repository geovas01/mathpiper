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
public class Substitute extends BuiltinFunction
{

    private Substitute()
    {
    }

    public Substitute(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        Cons from = getArgument(aEnvironment, aStackTop, 1);
        Cons to = getArgument(aEnvironment, aStackTop, 2);
        Cons body = getArgument(aEnvironment, aStackTop, 3);
        org.mathpiper.lisp.astprocessors.ExpressionSubstitute behaviour = new org.mathpiper.lisp.astprocessors.ExpressionSubstitute(aEnvironment, from, to);
        setTopOfStack(aEnvironment, aStackTop, Utility.substitute(aEnvironment, aStackTop, body, behaviour));
    }
}


/*
%mathpiper_docs,name="Substitute",categories="Mathematics Functions;Expression Manipulation;Built In"
*CMD Substitute --- perform a substitution
*CORE
*CALL
	Substitute(from, to) expr

*PARMS

{from} -- expression to be substituted

{to} -- expression to substitute for "from"

{expr} -- expression in which the substitution takes place

*DESC

This function substitutes every occurrence of "from" in "expr" by
"to". This is a syntactical substitution: only places where "from"
occurs as a subexpression are affected.

Below, the explanation for the last result is that the expression {a+b+c} is internally 
stored as {(a+b)+c}. Hence {a+b} is a subexpression, but {b+c} is not.

*E.G.

In> Substitute(_x, Sine(_y)) _x^2+_x+1;
Result: Sine(_y)^2+Sine(_y)+1;

In> Substitute(ToAtom("+"), ToAtom("-")) _a+_b
Result: _a-_b

In> Substitute(_a+_b, _x) _a+_b+_c;
Result: _x+_c;


In> Substitute(_b+_c, _x) _a+_b+_c;
Result: _a+_b+_c;

Workaround:
In>tmp := Substitute(_c,_x-_b)  _a+_b+_c;
Result: _a+_b+_x-_b

In> tmp
Result: _a+_b+_x-_b

In> Expand(tmp)
Result: _a+_x

*SEE WithValue, /:, Where
%/mathpiper_docs
*/
