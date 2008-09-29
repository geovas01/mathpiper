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
package org.mathrider.piper.builtin.functions;

import org.mathrider.piper.builtin.BuiltinFunction;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.ConsTraverser;
import org.mathrider.piper.lisp.ConsPointer;
import org.mathrider.piper.lisp.Standard;
import org.mathrider.piper.lisp.SubList;

/**
 *
 * @author 
 */
public class LazyAnd extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer nogos = new ConsPointer();
        int nrnogos = 0;
        ConsPointer evaluated = new ConsPointer();

        ConsTraverser iter = new ConsTraverser(argument(aEnvironment, aStackTop, 1).get().subList());
        iter.goNext();
        while (iter.getCons() != null)
        {
            aEnvironment.iEvaluator.evaluate(aEnvironment, evaluated, iter.ptr());
            if (Standard.isFalse(aEnvironment, evaluated))
            {
                Standard.internalFalse(aEnvironment, result(aEnvironment, aStackTop));
                return;
            } else if (!Standard.isTrue(aEnvironment, evaluated))
            {
                ConsPointer ptr = new ConsPointer();
                nrnogos++;
                ptr.set(evaluated.get().copy(false));
                ptr.get().cdr().set(nogos.get());
                nogos.set(ptr.get());
            }

            iter.goNext();
        }

        if (nogos.get() != null)
        {
            if (nrnogos == 1)
            {
                result(aEnvironment, aStackTop).set(nogos.get());
            } else
            {
                ConsPointer ptr = new ConsPointer();

                Standard.internalReverseList(ptr, nogos);
                nogos.set(ptr.get());

                ptr.set(argument(aEnvironment, aStackTop, 0).get().copy(false));
                ptr.get().cdr().set(nogos.get());
                nogos.set(ptr.get());
                result(aEnvironment, aStackTop).set(SubList.getInstance(nogos.get()));

            //aEnvironment.CurrentPrinter().Print(result(aEnvironment, aStackTop), *aEnvironment.CurrentOutput());
            }
        } else
        {
            Standard.internalTrue(aEnvironment, result(aEnvironment, aStackTop));
        }
    }
}
