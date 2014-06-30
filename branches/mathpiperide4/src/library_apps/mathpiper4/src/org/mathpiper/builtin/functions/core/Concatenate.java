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
import org.mathpiper.lisp.cons.SublistCons;

/**
 *
 *  
 */
public class Concatenate extends BuiltinFunction
{

    private Concatenate()
    {
    }

    public Concatenate(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        Cons all = aEnvironment.iListAtom.copy(false);

        //ConsTraverser tail = new ConsTraverser(aEnvironment, all);
        //tail.goNext(aStackTop);

        Cons tail = all;

        int arg = 1;

        Cons consTraverser =  (Cons) getArgument(aEnvironment, aStackTop, 1).car();
        consTraverser = consTraverser.cdr();

        while (consTraverser != null)
        {
            LispError.checkIsList(aEnvironment, aStackTop, consTraverser, arg);

            Cons result = Utility.flatCopy(aEnvironment, aStackTop, ((Cons) consTraverser.car()).cdr());

           tail.setCdr(result);

            while (tail.cdr() != null)
            {
                tail = tail.cdr();
            }

            consTraverser = consTraverser.cdr();

            arg++;
        }
        setTopOfStack(aEnvironment, aStackTop, SublistCons.getInstance(aEnvironment,all));
    }
}



/*
%mathpiper_docs,name="Concat",categories="Programming Functions;Lists (Operations);Built In"
*CMD Concat --- concatenate lists
*CORE
*CALL
	Concat(list1, list2, ...)

*PARMS

{list1}, {list2}, ... -- lists to concatenate

*DESC

The lists "list1", "list2", ... are evaluated and
concatenated. The resulting big list is returned.

*E.G.

In> Concat([_a,_b], '[_c,_d]);
Result: [_a,_b,_c,_d];

In> Concat([5], [_a,_b,_c], [["Blue"]]);
Result: [5,_a,_b,_c,["Blue"]]

*SEE ConcatStrings, :, Insert
%/mathpiper_docs




%mathpiper,name="Concat",subtype="automatic_test"

Verify(Concat([_a,_b],[_c,_d]), [_a,_b,_c,_d]);

%/mathpiper

*/
