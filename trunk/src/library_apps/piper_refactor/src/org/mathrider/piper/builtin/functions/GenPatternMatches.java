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

import org.mathrider.piper.builtin.BuiltinContainer;
import org.mathrider.piper.builtin.BuiltinFunction;
import org.mathrider.piper.builtin.PatternContainer;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.Iterator;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.Standard;

/**
 *
 * @author 
 */
public class GenPatternMatches extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        Pointer pattern = new Pointer();
        pattern.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
        BuiltinContainer gen = pattern.get().generic();
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, gen != null, 1);
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, gen.typeName().equals("\"Pattern\""), 1);

        Pointer list = new Pointer();
        list.set(ARGUMENT(aEnvironment, aStackTop, 2).get());

        PatternContainer patclass = (PatternContainer) gen;

        Iterator iter = new Iterator(list);
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, iter.GetObject() != null, 2);
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, iter.GetObject().subList() != null, 2);
        iter.GoSub();
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, iter.GetObject() != null, 2);
        iter.GoNext();

        Pointer ptr = iter.Ptr();
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, ptr != null, 2);
        boolean matches = patclass.matches(aEnvironment, ptr);
        Standard.internalBoolean(aEnvironment, RESULT(aEnvironment, aStackTop), matches);
    }
}
