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
package org.mathpiper.lisp.rulebases;

import org.mathpiper.builtin.BuiltinContainer;
import org.mathpiper.builtin.PatternContainer;

import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.parametermatchers.ParametersPatternMatcher;

/**
 * A rule which matches if the corresponding {@link PatternContainer} matches.
 */
public class PatternRule extends Rule {

    protected int iPrecedence;
    protected Cons iBody;
    protected Cons iPredicate;    
    protected PatternContainer iPattern; //The pattern that decides whether this rule matches or not.

    /**
     * 
     * @param aPrecedence precedence of the rule
     * @param aPredicate getObject object of type PatternContainer
     * @param aBody body of the rule
     */
    public PatternRule(Environment aEnvironment, int aStackTop, int aPrecedence, Cons aPredicate, Cons aBody) throws Exception {
        iPattern = null;
        iPrecedence = aPrecedence;
        iPredicate = aPredicate;

        BuiltinContainer gen = (BuiltinContainer) aPredicate.car();
        if(gen == null) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "");
        if(! gen.typeName().equals("\"Pattern\"")) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "Type is not <pattern>.");

        iPattern = (PatternContainer) gen;
        iBody = aBody;
    }

    //Return true if the corresponding pattern matches.
    public boolean matches(Environment aEnvironment, int aStackTop, Cons[] aArguments) throws Exception {
        return iPattern.matches(aEnvironment, aStackTop, aArguments);
    }

    //Access iPrecedence.
    public int getPrecedence() {
        return iPrecedence;
    }

    public Cons getPredicatePointer() {
        return this.iPredicate;
    }

    public ParametersPatternMatcher getPattern() {
        return iPattern.getPattern();
    }

    //Access iBody
    public Cons getBodyPointer() {
        return iBody;
    }
}
