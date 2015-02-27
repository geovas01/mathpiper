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
public class PatternMatch_ extends BuiltinFunction
{

    private PatternMatch_()
    {
    }

    public PatternMatch_(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        Cons patternMatcher = getArgument(aEnvironment, aStackTop, 1);
        
        BuiltinContainer gen = (BuiltinContainer) patternMatcher.car();
        
        if( gen == null) LispError.checkArgument(aEnvironment, aStackTop, 1);
        
        if(! gen.typeName().equals("\"Pattern\"")) LispError.checkArgument(aEnvironment, aStackTop, 1);

        Cons expressionToMatch = getArgument(aEnvironment, aStackTop, 2);

        PatternContainer patternContainer = (PatternContainer) gen;

        Cons consTraverser = expressionToMatch;
        
        if(consTraverser == null) LispError.checkArgument(aEnvironment, aStackTop, 2);
        
        if(! (consTraverser.car() instanceof Cons)) LispError.checkArgument(aEnvironment, aStackTop, 2);
        
        consTraverser = (Cons) consTraverser.car();
        
        if(consTraverser == null) LispError.checkArgument(aEnvironment, aStackTop, 2);
        
        consTraverser = consTraverser.cdr();

        //  Todo:tk:The following check was removed because it was preventing functions with no arguments from being matched.
        // if( consTraverser == null) LispError.checkArgument(aEnvironment, aStackTop, 2);
        
        boolean matches = patternContainer.matches(aEnvironment, aStackTop, consTraverser);
        
        setTopOfStack(aEnvironment, aStackTop, Utility.getBooleanAtom(aEnvironment, matches));
    }
}
