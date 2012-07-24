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
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *  
 */
public class Local extends BuiltinFunction
{

    private Local()
    {
    }

    public Local(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        if (getArgument(aEnvironment, aStackTop, 1).car() instanceof Cons) {

            Cons subList = (Cons) getArgument(aEnvironment, aStackTop, 1).car();
            
            Cons consTraverser = subList;
            consTraverser = consTraverser.cdr();

            int nr = 1;
            while (consTraverser != null)
            {
                String variable = (String) consTraverser.car();
                if(variable == null) LispError.checkArgument(aEnvironment, aStackTop, nr);
                // printf("Variable %s\n",variable.String());
                aEnvironment.newLocalVariable(variable, null, aStackTop);
                consTraverser = consTraverser.cdr();
                nr++;
            }
        }
         setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));
    }
}



/*
%mathpiper_docs,name="Local",categories="User Functions;Variables;Built In"
*CMD Local --- declare new local variables
*CORE
*CALL
	Local(var, ...)

*PARMS

{var} -- name of variable to be declared as local

*DESC

All variables in the argument list are declared as local
variables. The arguments are not evaluated. The value True is
returned.

By default, all variables in MathPiper are global. This means that the
variable has the same value everywhere. But sometimes it is useful to
have a private copy of some variable, either to prevent the outside
world from changing it or to prevent accidental changes to the outside
world. This can be achieved by declaring the variable local. Now only
expressions within the {Prog} block (or its
syntactic equivalent, the {[  ]} block) can access
and change it. Functions called within this block cannot access the
local copy unless this is specifically allowed with {UnFence}.

*E.G.

In> a := 3;
Result: 3;

In> [ a := 4; a; ];
Result: 4;
In> a;
Result: 4;

In> [ Local(a); a := 5; a; ];
Result: 5;
In> a;
Result: 4;

In the car block, {a} is not declared local and
hence defaults to be a global variable. Indeed, changing the variable
inside the block also changes the value of {a}
outside the block. However, in the second block {a}
is defined to be local and now the value outside the block stays the
same, even though {a} is assigned the value 5 inside
the block.

*SEE LocalSymbols, Prog, [], UnFence
%/mathpiper_docs
*/


/*
%mathpiper_docs,name="MacroLocal",categories="Programmer Functions;Programming;Built In"
*CMD MacroLocal --- define rules in functions
*CORE
*DESC

This function has the same effect as its non-macro counterpart, except
that its arguments are evaluated before the required action is performed.
This is useful in macro-like procedures or in functions that need to define new
rules based on parameters.

Make sure that the arguments of {Macro}... commands evaluate to expressions that would normally be used in the non-macro version!

*SEE Bind, Unbind, Local, RulebaseHoldArguments, RuleHoldArguments, `, MacroBind, MacroUnbind, MacroRulebase, MacroRulebaseListed, RuleEvaluateArguments
%/mathpiper_docs





%mathpiper,name="Local",subtype="automatic_test"

{
  Verify(Bound?([]),False);
  Local(a);
  Verify(Bound?(a),False);
  a:=1;
  Verify(Bound?(a),True);
  Unbind(a);
  Verify(Bound?(a),False);
};

%/mathpiper
*/