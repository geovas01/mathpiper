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
import org.mathrider.piper.lisp.Atom;
import org.mathrider.piper.lisp.Cons;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.ConsPointer;
import org.mathrider.piper.lisp.Standard;

/**
 *
 * @author 
 */
public class BuiltinAssoc extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        // key to find
        ConsPointer key = new ConsPointer();
        key.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

        // assoc-list to find it in
        ConsPointer list = new ConsPointer();
        list.set(ARGUMENT(aEnvironment, aStackTop, 2).get());

        Cons t;

        //Check that it is a compound object
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, list.get().subList() != null, 2);
        t = list.get().subList().get();
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, t != null, 2);
        t = t.cdr().get();

        while (t != null)
        {
            if (t.subList() != null)
            {
                Cons sub = t.subList().get();
                if (sub != null)
                {
                    sub = sub.cdr().get();
                    ConsPointer temp = new ConsPointer();
                    temp.set(sub);
                    if (Standard.internalEquals(aEnvironment, key, temp))
                    {
                        RESULT(aEnvironment, aStackTop).set(t);
                        return;
                    }
                }
            }
            t = t.cdr().get();
        }
        RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment, "Empty"));
    }
}
