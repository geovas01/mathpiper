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
import org.mathpiper.lisp.LispError;
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


    public void evaluate(Environment aEnvironment, int aStackBase) throws Exception
    {

        Cons pattern = getArgument(aEnvironment, aStackBase, 1);
        BuiltinContainer gen = (BuiltinContainer) pattern.car();
        if( gen == null) LispError.checkArgument(aEnvironment, aStackBase, 1);
        if(! gen.typeName().equals("\"Pattern\"")) LispError.checkArgument(aEnvironment, aStackBase, 1);


        Cons list = getArgument(aEnvironment, aStackBase, 2);

        PatternContainer patclass = (PatternContainer) gen;

        Cons consTraverser = list;
        if(consTraverser == null) LispError.checkArgument(aEnvironment, aStackBase, 2);
        if(! (consTraverser.car() instanceof Cons)) LispError.checkArgument(aEnvironment, aStackBase, 2);
        consTraverser = (Cons) consTraverser.car();
        if(consTraverser == null) LispError.checkArgument(aEnvironment, aStackBase, 2);
        consTraverser = consTraverser.cdr();

        if( consTraverser == null) LispError.checkArgument(aEnvironment, aStackBase, 2);
        boolean matches = patclass.matches(aEnvironment, aStackBase, consTraverser);
        setTopOfStack(aEnvironment, aStackBase, Utility.getBooleanAtom(aEnvironment, matches));
    }
}