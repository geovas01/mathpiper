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

package org.mathrider.piper.lisp.behaviours;

import org.mathrider.piper.lisp.Cons;
import org.mathrider.piper.lisp.Standard;
import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.SubList;

/** subst behaviour for backquote mechanism as in LISP.
 * When typing `(...) all occurrences of @a will be
 * replaced with:
 * 1) a evaluated if a is an atom
 * 2) function call with function name replaced by evaluated
 *    head of function if a is a function. For instance, if
 *    a is f(x) and f is g, then f(x) gets replaced by g(x)
 */
public class BackQuote implements SubstBase
{
	Environment iEnvironment;

	public BackQuote(Environment aEnvironment)
	{
		iEnvironment = aEnvironment;
	}
	public boolean matches(Pointer aResult, Pointer aElement) throws Exception
	{
		if (aElement.get().subList() == null) return false;
		Cons ptr = aElement.get().subList().get();
		if (ptr == null) return false;
		if (ptr.string() == null) return false;

		if (ptr.string().equals("`"))
		{
			aResult.set(aElement.get());
			return true;
		}

		if (!ptr.string().equals("@"))
			return false;
		ptr = ptr.cdr().get();
		if (ptr == null)
			return false;
		if (ptr.string() != null)
		{
			Pointer cur = new Pointer();
			cur.set(ptr);
			iEnvironment.iEvaluator.eval(iEnvironment, aResult, cur);
			return true;
		}
		else
		{
			ptr = ptr.subList().get();
			Pointer cur = new Pointer();
			cur.set(ptr);
			Pointer args = new Pointer();
			args.set(ptr.cdr().get());
			Pointer result = new Pointer();
			iEnvironment.iEvaluator.eval(iEnvironment, result, cur);
			result.get().cdr().set(args.get());
			Pointer result2 = new Pointer();
			result2.set(SubList.getInstance(result.get()));
			Standard.internalSubstitute(aResult, result2,this);
			return true;
		}
		//      return false;
	}

};
