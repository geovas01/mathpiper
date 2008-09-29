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
import org.mathrider.piper.lisp.Utility;
import org.mathrider.piper.lisp.ConsPointer;
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
	public boolean matches(ConsPointer aResult, ConsPointer aElement) throws Exception
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
			ConsPointer cur = new ConsPointer();
			cur.set(ptr);
			iEnvironment.iEvaluator.evaluate(iEnvironment, aResult, cur);
			return true;
		}
		else
		{
			ptr = ptr.subList().get();
			ConsPointer cur = new ConsPointer();
			cur.set(ptr);
			ConsPointer args = new ConsPointer();
			args.set(ptr.cdr().get());
			ConsPointer result = new ConsPointer();
			iEnvironment.iEvaluator.evaluate(iEnvironment, result, cur);
			result.get().cdr().set(args.get());
			ConsPointer result2 = new ConsPointer();
			result2.set(SubList.getInstance(result.get()));
			Utility.internalSubstitute(aResult, result2,this);
			return true;
		}
		//      return false;
	}

};
