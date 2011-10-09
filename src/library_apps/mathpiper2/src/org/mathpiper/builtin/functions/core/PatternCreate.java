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
import org.mathpiper.lisp.cons.ConsTraverser;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.ConsPointer;

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


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer patternPointer = new ConsPointer();
        patternPointer.setCons(getArgumentPointer(aEnvironment, aStackTop, 1).getCons());
        
        Cons postPredicatePointer = getArgumentPointer(aEnvironment, aStackTop, 2).getCons();

        ConsTraverser patternPointerTraverser = new ConsTraverser(aEnvironment, patternPointer);
        if(patternPointerTraverser.getCons() == null) LispError.checkArgument(aEnvironment, aStackTop, 1, "PatternCreate");
        if(! (patternPointerTraverser.car() instanceof ConsPointer)) LispError.checkArgument(aEnvironment, aStackTop, 1, "PatternCreate");
        patternPointerTraverser.goSub(aStackTop);
        if(patternPointerTraverser.getCons() == null) LispError.checkArgument(aEnvironment, aStackTop, 1, "PatternCreate");
        patternPointerTraverser.goNext(aStackTop);

        patternPointer = patternPointerTraverser.getPointer();


        org.mathpiper.lisp.parametermatchers.ParametersPatternMatcher matcher = new org.mathpiper.lisp.parametermatchers.ParametersPatternMatcher(aEnvironment, aStackTop, patternPointer.getCons(), postPredicatePointer);
        PatternContainer patternContainer = new PatternContainer(matcher);
        getTopOfStackPointer(aEnvironment, aStackTop).setCons(BuiltinObjectCons.getInstance(aEnvironment, aStackTop, patternContainer));
    }
}
