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
package org.mathpiper.lisp.astprocessors;

import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.Utility;

import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.SublistCons;

/** ASTProcessor behaviour for backquote mechanism as in LISP.
 * When typing `(...) all occurrences of @a will be
 * replaced with:
 * 1) a evaluated if a is an atom
 * 2) function call with function name replaced by evaluated
 *    head of function if a is a function. For instance, if
 *    a is f(x) and f is g, then f(x) gets replaced by g(x)
 */
public class BackQuoteSubstitute implements ASTProcessor {

    Environment iEnvironment;


    public BackQuoteSubstitute(Environment aEnvironment) {
        iEnvironment = aEnvironment;
    }


    public Cons matches(Environment aEnvironment, int aStackTop, Cons aElement) throws Throwable {
        if (!(aElement instanceof SublistCons)) {
            return null;
        }

        Cons ptr = (Cons) aElement.car();
        if (ptr == null) {
            return null;
        }

        if (!(ptr.car() instanceof String)) {
            return null;
        }

        if (ptr.car().equals("`")) {
            return aElement;
        }

        if (!ptr.car().equals("@")) {
            return null;
        }

        ptr = ptr.cdr();

        if (ptr == null) {
            return null;
        }

        if (ptr.car() instanceof String) {

            Cons cur = ptr;
            return iEnvironment.iLispExpressionEvaluator.evaluate(iEnvironment, aStackTop, cur);
        } else {
            ptr = (Cons) ptr.car();

            Cons cur = ptr;

            Cons args = ptr.cdr();

            Cons result = iEnvironment.iLispExpressionEvaluator.evaluate(iEnvironment, aStackTop, cur);
            result.setCdr(args);

            Cons result2 = SublistCons.getInstance(aEnvironment, result);
            return Utility.substitute(aEnvironment, aStackTop, result2, this);
        }
        //      return false;
    }

};
