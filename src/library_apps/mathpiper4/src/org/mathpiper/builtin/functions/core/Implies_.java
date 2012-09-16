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
import org.mathpiper.lisp.cons.SublistCons;

/**
 *
 *  
 */
public class Implies_ extends BuiltinFunction
{
    
    private Implies_()
    {
    }

    public Implies_(String functionName)
    {
        this.functionName = functionName;
    }

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
	
	Cons argument1Cons = getArgument(aEnvironment, aStackTop, 1);
	Cons argument1EvaluatedCons = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, argument1Cons);
	
	Cons argument2Cons = getArgument(aEnvironment, aStackTop, 2);
	Cons argument2EvaluatedCons = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, argument2Cons);
	
	//Implement logic for implies.
        if (Utility.isFalse(aEnvironment, argument1EvaluatedCons, aStackTop))
        {
            setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));
            return;
        } else if (Utility.isTrue(aEnvironment, argument1EvaluatedCons, aStackTop) && Utility.isTrue(aEnvironment, argument2EvaluatedCons, aStackTop))
        {
            setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));
            return;
        } else if (Utility.isTrue(aEnvironment, argument1EvaluatedCons, aStackTop) && Utility.isFalse(aEnvironment, argument2EvaluatedCons, aStackTop))
        {
            setTopOfStack(aEnvironment, aStackTop, Utility.getFalseAtom(aEnvironment));
            return;
        }
        
        

        //Return unevaluated.
        Cons result = getArgument(aEnvironment, aStackTop, 0).copy(false);
        
        Cons ptr = result;
        
        ptr.setCdr(argument1EvaluatedCons.copy(false));
        
        ptr = ptr.cdr();
        
        ptr.setCdr(argument2EvaluatedCons.copy(false));
        
        setTopOfStack(aEnvironment, aStackTop, SublistCons.getInstance(aEnvironment, result));
        

    }
}



/*
%mathpiper_docs,name="Implies?",categories="User Functions;Predicates;Built In"
*CMD Implies? --- logical conjunction
*CORE
*CALL
	a1 Implies? a2

	Implies?(a1, a2)

*PARMS

{a}1, {a}2 -- boolean values (may evaluate to {True} or {False})

*DESC

This function implements the truth table for implication:

{True} Implies? {True} returns {True}.

{True} Implies? {False} returns {False}.

{False} Implies? {True} returns {True}.

{False} Implies? {False} returns {True}.

*E.G.

In> True Implies? True
Result: True;

*SEE Or?, Not?, And?
%/mathpiper_docs





%mathpiper,name="Implies?",subtype="automatic_test"

Verify(True Implies? True,True);
Verify(True Implies? False,False);
Verify(False Implies? True,True);
Verify(False Implies? False,True);

%/mathpiper

*/