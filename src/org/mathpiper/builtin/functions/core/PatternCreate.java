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

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.PatternContainer;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *  
 */
public class PatternCreate extends BuiltinFunction
{

    private PatternCreate()
    {
    }

    public PatternCreate(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackBase) throws Exception
    {

        Cons pattern = getArgument(aEnvironment, aStackBase, 1);
        
        Cons postPredicate = getArgument(aEnvironment, aStackBase, 2);

        Cons patternTraverser = pattern;
        if(patternTraverser == null) LispError.checkArgument(aEnvironment, aStackBase, 1);
        if(! (patternTraverser.car() instanceof Cons)) LispError.checkArgument(aEnvironment, aStackBase, 1);
        patternTraverser = (Cons) patternTraverser.car();
        if(patternTraverser == null) LispError.checkArgument(aEnvironment, aStackBase, 1);
        patternTraverser = patternTraverser.cdr();

        pattern = patternTraverser;


        org.mathpiper.lisp.parametermatchers.ParametersPatternMatcher matcher = new org.mathpiper.lisp.parametermatchers.ParametersPatternMatcher(aEnvironment, aStackBase, pattern, postPredicate);
        PatternContainer patternContainer = new PatternContainer(matcher);
        setTopOfStack(aEnvironment, aStackBase, BuiltinObjectCons.getInstance(aEnvironment, aStackBase, patternContainer));
    }
}
