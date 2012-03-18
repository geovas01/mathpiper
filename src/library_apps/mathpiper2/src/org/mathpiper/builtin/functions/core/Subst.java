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
public class Subst extends BuiltinFunction
{

    private Subst()
    {
    }

    public Subst(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        Cons from = getArgument(aEnvironment, aStackTop, 1);
        Cons to = getArgument(aEnvironment, aStackTop, 2);
        Cons body = getArgument(aEnvironment, aStackTop, 3);
        org.mathpiper.lisp.substitute.ExpressionSubstitute behaviour = new org.mathpiper.lisp.substitute.ExpressionSubstitute(aEnvironment, from, to);
        setTopOfStack(aEnvironment, aStackTop, Utility.substitute(aEnvironment, aStackTop, body, behaviour));
    }
}


/*
%mathpiper_docs,name="Subst",categories="User Functions;Expression Manipulation;Built In"
*CMD Subst --- perform a substitution
*CORE
*CALL
	Subst(from, to) expr

*PARMS

{from} -- expression to be substituted

{to} -- expression to substitute for "from"

{expr} -- expression in which the substitution takes place

*DESC

This function substitutes every occurrence of "from" in "expr" by
"to". This is a syntactical substitution: only places where "from"
occurs as a subexpression are affected.

*E.G.

In> Subst(x, Sin(y)) x^2+x+1;
Result: Sin(y)^2+Sin(y)+1;
In> Subst(a+b, x) a+b+c;
Result: x+c;
In> Subst(b+c, x) a+b+c;
Result: a+b+c;

The explanation for the last result is that the expression {a+b+c} is internally stored as {(a+b)+c}. Hence {a+b} is a subexpression, but {b+c} is not.

*SEE WithValue, /:, Where
%/mathpiper_docs
*/
