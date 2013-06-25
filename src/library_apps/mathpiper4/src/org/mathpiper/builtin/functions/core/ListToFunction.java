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
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;


/**
 *
 *  
 */
public class ListToFunction extends BuiltinFunction
{

    private ListToFunction()
    {
    }

    public ListToFunction(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        if(getArgument(aEnvironment, aStackTop, 1) == null) LispError.checkArgument(aEnvironment, aStackTop, 1);
        if(! (getArgument(aEnvironment, aStackTop, 1).car() instanceof Cons)) LispError.checkArgument(aEnvironment, aStackTop, 1);
        Cons atom = (Cons) getArgument(aEnvironment, aStackTop, 1).car();
        if( atom == null) LispError.checkArgument(aEnvironment, aStackTop, 1);
        if(! (((String)atom.car()).equals((String)aEnvironment.iListAtom.car()))) LispError.checkArgument(aEnvironment, aStackTop, 1);
      
        Cons tail = Utility.tail(aEnvironment, aStackTop, getArgument(aEnvironment, aStackTop, 1));
        
        /*
        if(((String) tail.car()).startsWith("_"))
        {
            tail.setCar(((String) tail.car()).substring(1,((String) tail.car()).length()));
        }
        */
        
        setTopOfStack(aEnvironment, aStackTop, tail);
    }
}



/*
%mathpiper_docs,name="ListToFunction",categories="Programming Functions;Lists (Operations);Built In"
*CMD ListToFunction --- convert a list to a function application
*CORE
*CALL
	ListToFunction(list)

*PARMS

{list} -- list to be converted

*DESC

This command converts a list to a function application. The car
entry of "list" is treated as a function atom, and the following entries
are the arguments to this function. So the function referred to in the
car element of "list" is applied to the other elements.

Note that "list" is evaluated before the function application is
formed, but the resulting expression is left unevaluated. The functions 
{ListToFunction()} and {Hold()} both stop the process of evaluation.

*E.G.

In> ListToFunction([Cos, x]);
Result: Cos(x);

In> ListToFunction([f]);
Result: f();

In> ListToFunction([Taylor,x,0,5,Cos(x)]);
Result: Taylor(x,0,5)Cos(x);

In> Eval(%);
Result: 1-x^2/2+x^4/24;

*SEE List, FunctionToList, Hold
%/mathpiper_docs





%mathpiper,name="ListToFunction",subtype="automatic_test"

// FunctionToList and ListToFunction coredumped when their arguments were invalid.
Verify(ListToFunction('[Sqrt,_x]),Sqrt(_x));

{
  Local(exception);
 
  exception := False;
  ExceptionCatch(ListToFunction(1.2), exception := ExceptionGet());
  Verify(exception =? False, False);
};

%/mathpiper
*/