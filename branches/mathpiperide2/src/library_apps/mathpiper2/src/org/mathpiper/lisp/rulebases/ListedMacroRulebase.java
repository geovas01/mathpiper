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
package org.mathpiper.lisp.rulebases;

import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.ConsTraverser;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;

public class ListedMacroRulebase extends MacroRulebase {

    public ListedMacroRulebase(Environment aEnvironment, int aStackTop, ConsPointer aParameters, String functionName) throws Exception {
        super(aEnvironment, aStackTop, aParameters, functionName);
    }


    @Override
    public boolean isArity(int aArity) {
        return (arity() <= aArity);
    }


    @Override
    public Cons evaluate(Environment aEnvironment, int aStackTop, Cons aArguments) throws Exception {
        Cons aResult;

        Cons newArgs = null;

        ConsTraverser consTraverser = new ConsTraverser(aEnvironment, aArguments);

        Cons ptr = null;

        int arity = arity();

        int i = 0;

        while (i < arity && consTraverser.getCons() != null) {

            if(i == 0)
            {
                ptr = consTraverser.getCons().copy(aEnvironment, false);
                newArgs = ptr;
            }
            else
            {
                Cons nextCons = consTraverser.getCons().copy(aEnvironment, false);
                ptr.setCdr(nextCons);
                ptr = nextCons;
            }

            i++;

            consTraverser.goNext(aStackTop);
        }

        if (consTraverser.cdr() == null) {
            Cons nextCons = consTraverser.getCons().copy(aEnvironment, false);
            ptr.setCdr(nextCons);
            ptr = nextCons;
            i++;
            consTraverser.goNext(aStackTop);
            if(consTraverser.getCons() != null) LispError.lispAssert(aEnvironment, aStackTop);
        } else {
            ConsPointer head = new ConsPointer();
            head.setCons(aEnvironment.iListAtom.copy(aEnvironment, false));
            head.getCons().setCdr(consTraverser.getCons());
            Cons nextCons = SublistCons.getInstance(aEnvironment, head.getCons());
            ptr.setCdr(nextCons);
            ptr = nextCons;
        }
        aResult = super.evaluate(aEnvironment, aStackTop, newArgs);
        return aResult;
    }


}
