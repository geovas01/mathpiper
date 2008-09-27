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
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.Standard;
import org.mathrider.piper.lisp.SubList;

/**
 *
 * @author 
 */
public class Concatenate extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        Pointer all = new Pointer();
        all.set(aEnvironment.iList.copy(false));
        Iterator tail = new Iterator(all);
        tail.GoNext();
        int arg = 1;

        Iterator iter = new Iterator(ARGUMENT(aEnvironment, aStackTop, 1).get().subList());
        iter.GoNext();
        while (iter.GetObject() != null)
        {
            LispError.CHK_ISLIST_CORE(aEnvironment, aStackTop, iter.Ptr(), arg);
            Standard.internalFlatCopy(tail.Ptr(), iter.Ptr().get().subList().get().cdr());
            while (tail.GetObject() != null)
            {
                tail.GoNext();
            }
            iter.GoNext();
            arg++;
        }
        RESULT(aEnvironment, aStackTop).set(SubList.getInstance(all.get()));
    }
}
