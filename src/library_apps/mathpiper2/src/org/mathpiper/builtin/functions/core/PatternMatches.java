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

package org.mathpiper.builtin.functions.core;

import org.mathpiper.builtin.BuiltinContainer;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.PatternContainer;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.ConsTraverser;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *  
 */
public class PatternMatches extends BuiltinFunction
{

    private PatternMatches()
    {
    }

    public PatternMatches(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {

        Cons pattern = getArgumentPointer(aEnvironment, aStackTop, 1);
        BuiltinContainer gen = (BuiltinContainer) pattern.car();
        if( gen == null) LispError.checkArgument(aEnvironment, aStackTop, 1, "PatternMatches");
        if(! gen.typeName().equals("\"Pattern\"")) LispError.checkArgument(aEnvironment, aStackTop, 1, "PatternMatches");


        Cons list = getArgumentPointer(aEnvironment, aStackTop, 2);

        PatternContainer patclass = (PatternContainer) gen;

        Cons consTraverser = list;
        if(consTraverser == null) LispError.checkArgument(aEnvironment, aStackTop, 2, "PatternMatches");
        if(! (consTraverser.car() instanceof Cons)) LispError.checkArgument(aEnvironment, aStackTop, 2, "PatternMatches");
        consTraverser = (Cons) consTraverser.car();
        if(consTraverser == null) LispError.checkArgument(aEnvironment, aStackTop, 2, "PatternMatches");
        consTraverser = consTraverser.cdr();

        if( consTraverser == null) LispError.checkArgument(aEnvironment, aStackTop, 2, "PatternMatches");
        boolean matches = patclass.matches(aEnvironment, aStackTop, consTraverser);
        setTopOfStackPointer(aEnvironment, aStackTop, Utility.putBooleanInPointer(aEnvironment, matches));
    }
}
