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
import org.mathrider.piper.lisp.ConsTraverser;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.ConsPointer;
import org.mathrider.piper.lisp.Standard;

/**
 *
 * @author 
 */
public class GenPatternMatches extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer pattern = new ConsPointer();
        pattern.set(argument(aEnvironment, aStackTop, 1).get());
        BuiltinContainer gen = pattern.get().generic();
        LispError.checkArgumentCore(aEnvironment, aStackTop, gen != null, 1);
        LispError.checkArgumentCore(aEnvironment, aStackTop, gen.typeName().equals("\"Pattern\""), 1);

        ConsPointer list = new ConsPointer();
        list.set(argument(aEnvironment, aStackTop, 2).get());

        PatternContainer patclass = (PatternContainer) gen;

        ConsTraverser iter = new ConsTraverser(list);
        LispError.checkArgumentCore(aEnvironment, aStackTop, iter.getCons() != null, 2);
        LispError.checkArgumentCore(aEnvironment, aStackTop, iter.getCons().subList() != null, 2);
        iter.goSub();
        LispError.checkArgumentCore(aEnvironment, aStackTop, iter.getCons() != null, 2);
        iter.goNext();

        ConsPointer ptr = iter.ptr();
        LispError.checkArgumentCore(aEnvironment, aStackTop, ptr != null, 2);
        boolean matches = patclass.matches(aEnvironment, ptr);
        Standard.internalBoolean(aEnvironment, result(aEnvironment, aStackTop), matches);
    }
}
