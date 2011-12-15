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

import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;

/**
 * A rule with a predicate (the rule matches if the predicate evaluates to True.)
 */
class PredicateRule extends Rule {

    protected int iPrecedence;
    protected Cons iBody;
    protected Cons iPredicate;


    public PredicateRule(Environment aEnvironment, int aPrecedence, Cons aPredicate, Cons aBody) {
        iBody = aBody;
        iPrecedence = aPrecedence;
        iPredicate = aPredicate;

    }


    protected PredicateRule(Environment aEnvironment) {
    }


    private PredicateRule() {
    }


    /**
     *  Return true if the rule matches.
     * 
     * @param aEnvironment
     * @param aArguments
     * @return
     * @throws java.lang.Exception
     */
    // iPredicate is evaluated in \a Environment. If the result
    /// IsTrue(), this function returns true
    public boolean matches(Environment aEnvironment, int aStackTop, Cons[] aArguments) throws Exception {
        
        Cons pred = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, iPredicate);
        return Utility.isTrue(aEnvironment, pred, aStackTop);
    }

    /// Access #iPrecedence.

    public int getPrecedence() {
        return iPrecedence;
    }

    /// Access #iBody.

    public Cons getBody() {
        return iBody;
    }


    public Cons getPredicate() {
        return this.iPredicate;
    }

}
