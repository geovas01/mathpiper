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
import org.mathrider.piper.builtin.PatternContainer;
import org.mathrider.piper.lisp.BuiltinObject;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.ConsTraverser;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.ConsPointer;

/**
 *
 *  
 */
public class GenPatternCreate extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer pattern = new ConsPointer();
        pattern.set(argumentPointer(aEnvironment, aStackTop, 1).get());
        ConsPointer postpredicate = new ConsPointer();
        postpredicate.set(argumentPointer(aEnvironment, aStackTop, 2).get());

        ConsTraverser iter = new ConsTraverser(pattern);
        LispError.checkArgumentCore(aEnvironment, aStackTop, iter.getCons() != null, 1);
        LispError.checkArgumentCore(aEnvironment, aStackTop, iter.getCons().subList() != null, 1);
        iter.goSub();
        LispError.checkArgumentCore(aEnvironment, aStackTop, iter.getCons() != null, 1);
        iter.goNext();

        ConsPointer ptr = iter.ptr();


        org.mathrider.piper.parametermatchers.Pattern matcher = new org.mathrider.piper.parametermatchers.Pattern(aEnvironment, ptr, postpredicate);
        PatternContainer p = new PatternContainer(matcher);
        result(aEnvironment, aStackTop).set(BuiltinObject.getInstance(p));
    }
}
