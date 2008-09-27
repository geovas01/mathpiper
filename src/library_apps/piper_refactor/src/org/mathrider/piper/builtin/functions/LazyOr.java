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
import org.mathrider.piper.lisp.Iterator;
import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.Standard;
import org.mathrider.piper.lisp.SubList;

/**
 *
 * @author 
 */
public class LazyOr extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        Pointer nogos = new Pointer();
        int nrnogos = 0;

        Pointer evaluated = new Pointer();

        Iterator iter = new Iterator(ARGUMENT(aEnvironment, aStackTop, 1).get().subList());
        iter.GoNext();
        while (iter.GetObject() != null)
        {
            aEnvironment.iEvaluator.eval(aEnvironment, evaluated, iter.Ptr());
            if (Standard.isTrue(aEnvironment, evaluated))
            {
                Standard.internalTrue(aEnvironment, RESULT(aEnvironment, aStackTop));
                return;
            } else if (!Standard.isFalse(aEnvironment, evaluated))
            {
                Pointer ptr = new Pointer();
                nrnogos++;

                ptr.set(evaluated.get().copy(false));
                ptr.get().cdr().set(nogos.get());
                nogos.set(ptr.get());
            }
            iter.GoNext();
        }

        if (nogos.get() != null)
        {
            if (nrnogos == 1)
            {
                RESULT(aEnvironment, aStackTop).set(nogos.get());
            } else
            {
                Pointer ptr = new Pointer();

                Standard.internalReverseList(ptr, nogos);
                nogos.set(ptr.get());

                ptr.set(ARGUMENT(aEnvironment, aStackTop, 0).get().copy(false));
                ptr.get().cdr().set(nogos.get());
                nogos.set(ptr.get());
                RESULT(aEnvironment, aStackTop).set(SubList.getInstance(nogos.get()));
            }
        //aEnvironment.CurrentPrinter().Print(RESULT(aEnvironment, aStackTop), *aEnvironment.CurrentOutput());
        } else
        {
            Standard.internalFalse(aEnvironment, RESULT(aEnvironment, aStackTop));
        }
    }
}
