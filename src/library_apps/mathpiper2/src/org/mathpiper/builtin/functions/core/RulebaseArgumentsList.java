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
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.lisp.rulebases.SingleArityRulebase;

/**
 *
 *  
 */
public class RulebaseArgumentsList extends BuiltinFunction
{

    private RulebaseArgumentsList()
    {
    }

    public RulebaseArgumentsList(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer name = new ConsPointer();
        name.setCons(getArgumentPointer(aEnvironment, aStackTop, 1));
        String orig = (String) name.car();
        if( orig == null) LispError.checkArgument(aEnvironment, aStackTop, 1, "RulebaseArgumentsList");
        String oper = Utility.toNormalString(aEnvironment, aStackTop, orig);

        ConsPointer sizearg = new ConsPointer();
        sizearg.setCons(getArgumentPointer(aEnvironment, aStackTop, 2));
        if( sizearg.getCons() == null) LispError.checkArgument(aEnvironment, aStackTop, 2, "RulebaseArgumentsList");
        if(! (sizearg.car() instanceof String)) LispError.checkArgument(aEnvironment, aStackTop, 2, "RulebaseArgumentsList");

        int arity = Integer.parseInt( (String) sizearg.car(), 10);

        SingleArityRulebase userFunc = aEnvironment.getRulebase((String)aEnvironment.getTokenHash().lookUp(oper), arity, aStackTop);
        
        if(userFunc == null) LispError.throwError(aEnvironment, aStackTop, "User function for this arity is not defined.", "RulebaseArgumentsList");

        Cons list = userFunc.argList();
        ConsPointer head = new ConsPointer();
        head.setCons(aEnvironment.iListAtom.copy( aEnvironment, false));
        head.getCons().setCdr(list);
        setTopOfStackPointer(aEnvironment, aStackTop, SublistCons.getInstance(aEnvironment,head.getCons()));
    }
}



/*
%mathpiper_docs,name="RulebaseArgumentsList",categories="Programmer Functions;Programming;Built In"
*CMD RulebaseArgumentsList --- obtain list of arguments
*CORE
*CALL
	RulebaseArgumentsList("operator", arity)

*PARMS
{"operator"} -- string, name of function

{arity} -- integer

*DESC

Returns a list of atoms, symbolic parameters specified in the {Rulebase} call
for the function named {"operator"} with the specific {arity}.

*SEE RulebaseHoldArguments, HoldArgumentNumber, HoldArgument
%/mathpiper_docs
*/