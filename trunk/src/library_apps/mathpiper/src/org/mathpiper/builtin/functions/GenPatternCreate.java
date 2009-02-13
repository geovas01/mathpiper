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

package org.mathpiper.builtin.functions;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.PatternContainer;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.ConsTraverser;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.ConsPointer;

/**
 *
 *  
 */
public class GenPatternCreate extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer pattern = new ConsPointer();
        pattern.setCons(getArgumentPointer(aEnvironment, aStackTop, 1).getCons());
        ConsPointer postpredicate = new ConsPointer();
        postpredicate.setCons(getArgumentPointer(aEnvironment, aStackTop, 2).getCons());

        ConsTraverser consTraverser = new ConsTraverser(pattern);
        LispError.checkArgument(aEnvironment, aStackTop, consTraverser.getCons() != null, 1);
        LispError.checkArgument(aEnvironment, aStackTop, consTraverser.getCons().getSubList() != null, 1);
        consTraverser.goSub();
        LispError.checkArgument(aEnvironment, aStackTop, consTraverser.getCons() != null, 1);
        consTraverser.goNext();

        ConsPointer ptr = consTraverser.ptr();


        org.mathpiper.lisp.parametermatchers.Pattern matcher = new org.mathpiper.lisp.parametermatchers.Pattern(aEnvironment, ptr, postpredicate);
        PatternContainer p = new PatternContainer(matcher);
        getResult(aEnvironment, aStackTop).setCons(BuiltinObjectCons.getInstance(p));
    }
}
