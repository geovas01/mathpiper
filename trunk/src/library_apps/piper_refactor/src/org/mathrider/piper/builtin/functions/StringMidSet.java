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

import org.mathrider.piper.builtin.*;
import org.mathrider.piper.lisp.Atom;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.ConsPointer;

/**
 *
 * @author 
 */
public class StringMidSet extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer evaluated = new ConsPointer();
        evaluated.set(ARGUMENT(aEnvironment, aStackTop, 3).get());
        LispError.CHK_ISSTRING_CORE(aEnvironment, aStackTop, evaluated, 3);
        String orig = evaluated.get().string();
        ConsPointer index = new ConsPointer();
        index.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, index.get() != null, 1);
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, index.get().string() != null, 1);
        int from = Integer.parseInt(index.get().string(), 10);

        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, from > 0, 1);

        ConsPointer ev2 = new ConsPointer();
        ev2.set(ARGUMENT(aEnvironment, aStackTop, 2).get());
        LispError.CHK_ISSTRING_CORE(aEnvironment, aStackTop, ev2, 2);
        String replace = ev2.get().string();

        LispError.CHK_CORE(aEnvironment, aStackTop, from + replace.length() - 2 < orig.length(), LispError.KLispErrInvalidArg);
        String str;
        str = orig.substring(0, from);
        str = str + replace.substring(1, replace.length() - 1);
        //System.out.println("from="+from+replace.length()-2);
        str = str + orig.substring(from + replace.length() - 2, orig.length());
        RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment, str));
    }
}
