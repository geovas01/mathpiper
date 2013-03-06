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
public class Equivales_ extends BuiltinFunction
{
    
    private Equivales_()
    {
    }

    public Equivales_(String functionName)
    {
        this.functionName = functionName;
    }

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
	
	Cons argument1Cons = getArgument(aEnvironment, aStackTop, 1);
	Cons argument1EvaluatedCons = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, argument1Cons);
	
	Cons argument2Cons = getArgument(aEnvironment, aStackTop, 2);
	Cons argument2EvaluatedCons = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, argument2Cons);
	
	//Implement logic for equivales (See "A Logical Approach to Discrete Math", Gries and Schneider, Springer, 1993.)
        if (Utility.isTrue(aEnvironment, argument1EvaluatedCons, aStackTop) && Utility.isTrue(aEnvironment, argument2EvaluatedCons, aStackTop))
        {
            setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));
            return;
        } else if (Utility.isTrue(aEnvironment, argument1EvaluatedCons, aStackTop) && Utility.isFalse(aEnvironment, argument2EvaluatedCons, aStackTop))
        {
            setTopOfStack(aEnvironment, aStackTop, Utility.getFalseAtom(aEnvironment));
            return;
        } else if (Utility.isFalse(aEnvironment, argument1EvaluatedCons, aStackTop) && Utility.isTrue(aEnvironment, argument2EvaluatedCons, aStackTop))
        {
            setTopOfStack(aEnvironment, aStackTop, Utility.getFalseAtom(aEnvironment));
            return;
        } else if (Utility.isFalse(aEnvironment, argument1EvaluatedCons, aStackTop) && Utility.isFalse(aEnvironment, argument2EvaluatedCons, aStackTop))
        {
            setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));
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
%mathpiper_docs,name="Equivales?",categories="Programming Functions;Predicates;Built In"
*CMD Implies? --- logical conjunction
*CORE
*CALL
	a1 Equivales? a2

	Equivales?(a1, a2)

*PARMS

{a}1, {a}2 -- boolean values (may evaluate to {True} or {False})

*DESC

This function implements the truth table for equivalence:

{True} Equivales? {True} returns {True}.

{True} Equivales? {False} returns {False}.

{False} Equivales? {True} returns {False}.

{False} Equivales? {False} returns {True}.

*E.G.

In> True Equivales? False
Result: False

*SEE Or?, Not?, And?, Implies?
%/mathpiper_docs





%mathpiper,name="Equivales?",subtype="automatic_test"

Verify(True Equivales? True,True);
Verify(True Equivales? False,False);
Verify(False Equivales? True,False);
Verify(False Equivales? False,True);

%/mathpiper

*/