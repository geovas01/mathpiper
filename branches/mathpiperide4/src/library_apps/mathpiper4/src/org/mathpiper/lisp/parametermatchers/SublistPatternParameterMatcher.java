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
package org.mathpiper.lisp.parametermatchers;

import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.Cons;

/// Class for matching against a list of PatternParameterMatcher objects.
public class SublistPatternParameterMatcher extends PatternParameterMatcher {

    protected PatternParameterMatcher[] iMatchers;

    protected int iNumberOfMatchers;


    public SublistPatternParameterMatcher(PatternParameterMatcher[] aMatchers, int aNrMatchers) {
        iMatchers = aMatchers;
        iNumberOfMatchers = aNrMatchers;
    }


    public boolean argumentMatches(Environment aEnvironment, int aStackTop, Cons aExpression, Cons[] arguments) throws Throwable {

        if (!(aExpression.car() instanceof Cons)) {
            return false;
        }

        Cons consTraverser = aExpression;

        consTraverser = (Cons) consTraverser.car();

        for (int i = 0; i < iNumberOfMatchers; i++) {

            if (consTraverser == null) {
                return false;
            }

            if (!iMatchers[i].argumentMatches(aEnvironment, aStackTop, consTraverser, arguments)) {
                return false;
            }

            consTraverser = consTraverser.cdr();
        }

        if (consTraverser != null) {
            return false;
        }
        
        return true;
    }


    public String getType()
    {
        return "Sublist";
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for(int x = 0; x < iMatchers.length; x++)
        {
            PatternParameterMatcher matcher = iMatchers[x];

            stringBuilder.append(matcher.getType());
            
            stringBuilder.append(": ");
            
            stringBuilder.append(matcher.toString());

            stringBuilder.append(", ");
            
        }

        return stringBuilder.toString();
    }

}
