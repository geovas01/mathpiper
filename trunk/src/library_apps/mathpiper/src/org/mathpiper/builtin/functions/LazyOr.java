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
import org.mathpiper.lisp.cons.ConsTraverser;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.UtilityFunctions;
import org.mathpiper.lisp.cons.SubListCons;

/**
 *
 *  
 */
public class LazyOr extends BuiltinFunction
{

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer nogos = new ConsPointer();
        int nrnogos = 0;

        ConsPointer evaluated = new ConsPointer();

        ConsTraverser consTraverser = new ConsTraverser(getArgumentPointer(aEnvironment, aStackTop, 1).getCons().getSublistPointer());
        consTraverser.goNext();
        while (consTraverser.getCons() != null)
        {
            aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, evaluated, consTraverser.getPointer());
            if (UtilityFunctions.isTrue(aEnvironment, evaluated))
            {
                UtilityFunctions.internalTrue(aEnvironment, getResult(aEnvironment, aStackTop));
                return;
            } else if (!UtilityFunctions.isFalse(aEnvironment, evaluated))
            {
                ConsPointer ptr = new ConsPointer();
                nrnogos++;

                ptr.setCons(evaluated.getCons().copy(false));
                ptr.getCons().getRestPointer().setCons(nogos.getCons());
                nogos.setCons(ptr.getCons());
            }
            consTraverser.goNext();
        }

        if (nogos.getCons() != null)
        {
            if (nrnogos == 1)
            {
                getResult(aEnvironment, aStackTop).setCons(nogos.getCons());
            } else
            {
                ConsPointer ptr = new ConsPointer();

                UtilityFunctions.internalReverseList(ptr, nogos);
                nogos.setCons(ptr.getCons());

                ptr.setCons(getArgumentPointer(aEnvironment, aStackTop, 0).getCons().copy(false));
                ptr.getCons().getRestPointer().setCons(nogos.getCons());
                nogos.setCons(ptr.getCons());
                getResult(aEnvironment, aStackTop).setCons(SubListCons.getInstance(nogos.getCons()));
            }
        //aEnvironment.CurrentPrinter().Print(getResult(aEnvironment, aStackTop), *aEnvironment.CurrentOutput());
        } else
        {
            UtilityFunctions.internalFalse(aEnvironment, getResult(aEnvironment, aStackTop));
        }
    }
}



/*
%mathpiper_docs,name="Or",categories="User Functions;Predicates;Built In"
*CMD Or --- logical disjunction
*CORE
*CALL
	a1 Or a2
Precedence:
*EVAL OpPrecedence("Or")
	Or(a1, a2, a3, ..., aN)

*PARMS

{a}1, ..., {a}N -- boolean expressions (may evaluate to {True} or {False})

*DESC

This function returns {True} if an argument is encountered
that is true (scanning from left to right). The
{Or} operation is "lazy", i.e. it returns {True} as soon as a {True} argument
is found (from left to right). If an argument other than {True} or
{False} is encountered, an unevaluated {Or} expression is returned with all
arguments that didn't evaluate to {True} or {False} yet.

*E.G.

	In> True Or False
	Out> True;
	In> False Or a
	Out> Or(a);
	In> Or(False,a,b,True)
	Out> True;

*SEE And, Not
%/mathpiper_docs
*/