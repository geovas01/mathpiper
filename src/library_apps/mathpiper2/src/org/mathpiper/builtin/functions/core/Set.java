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
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.ConsTraverser;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.cons.SublistCons;

/**
 * Used to create sets like List() is used to create lists.
 *
 */
public class Set extends BuiltinFunction
{

    private Set()
    {
    }

    public Set(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {
        ConsPointer allPointer = new ConsPointer();
        allPointer.setCons(aEnvironment.iListAtom.copy(aEnvironment, false));
        ConsTraverser tail = new ConsTraverser(aEnvironment, allPointer);
        tail.goNext(aStackTop);
        ConsTraverser consTraverser = new ConsTraverser(aEnvironment, (ConsPointer) getArgumentPointer(aEnvironment, aStackTop, 1).car());
        consTraverser.goNext(aStackTop);
        while (consTraverser.getCons() != null) {
            
            Cons evaluated = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, consTraverser.getPointer().getCons());
            tail.setCons(evaluated);
            tail.goNext(aStackTop);
            consTraverser.goNext(aStackTop);
        }


        Cons head = SublistCons.getInstance(aEnvironment, AtomCons.getInstance(aEnvironment, aStackTop, "RemoveDuplicates"));

        ((ConsPointer) head.car()).getCons().setCdr(SublistCons.getInstance(aEnvironment, allPointer.getCons()));

        ConsPointer removeDuplicatesResultPointer = new ConsPointer();

        removeDuplicatesResultPointer.setCons(aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, head));
        
        ConsPointer resultPointer = new ConsPointer();

        resultPointer.setCons(aEnvironment.iSetAtom.copy(aEnvironment, false));

        removeDuplicatesResultPointer.goSub(aStackTop, aEnvironment);

        resultPointer.getCons().setCdr(removeDuplicatesResultPointer.cdr());

        getTopOfStackPointer(aEnvironment, aStackTop).setCons(SublistCons.getInstance(aEnvironment,resultPointer.getCons()));
    }
}
