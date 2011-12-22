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


import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;

public class ListedMacroRulebase extends MacroRulebase {

    public ListedMacroRulebase(Environment aEnvironment, int aStackBase, Cons aParameters, String functionName) throws Exception {
        super(aEnvironment, aStackBase, aParameters, functionName);
    }


    @Override
    public boolean isArity(int aArity) {
        return (arity() <= aArity);
    }


    @Override
    public void evaluate(Environment aEnvironment, int aStackBase, Cons aArguments) throws Exception {

        Cons newArgs = null;

        Cons  consTraverser = aArguments;

        Cons ptr = null;

        int arity = arity();

        int i = 0;

        while (i < arity && consTraverser != null) {

            if(i == 0)
            {
                ptr = consTraverser.copy(false);
                newArgs = ptr;
            }
            else
            {
                Cons nextCons = consTraverser.copy(false);
                ptr.setCdr(nextCons);
                ptr = nextCons;
            }

            i++;

            consTraverser = consTraverser.cdr();
        }

        if (consTraverser.cdr() == null) {
            Cons nextCons = consTraverser.copy(false);
            ptr.setCdr(nextCons);
            ptr = nextCons;
            i++;
            consTraverser = consTraverser.cdr();
            if(consTraverser != null) LispError.lispAssert(aEnvironment, aStackBase);
        } else {
            Cons head = aEnvironment.iListAtom.copy(false);
            head.setCdr(consTraverser);
            Cons nextCons = SublistCons.getInstance(aEnvironment, head);
            ptr.setCdr(nextCons);
            ptr = nextCons;
        }
        super.evaluate(aEnvironment, aStackBase, newArgs);

        return;
    }


}
