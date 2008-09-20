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

package org.mathrider.piper;

import org.mathrider.piper.lisp.LispObject;
import org.mathrider.piper.lisp.LispStandard;
import org.mathrider.piper.lisp.LispPtr;
import org.mathrider.piper.lisp.LispEnvironment;
import org.mathrider.piper.lisp.LispSubList;

/** subst behaviour for backquote mechanism as in LISP.
 * When typing `(...) all occurrences of @a will be
 * replaced with:
 * 1) a evaluated if a is an atom
 * 2) function call with function name replaced by evaluated
 *    head of function if a is a function. For instance, if
 *    a is f(x) and f is g, then f(x) gets replaced by g(x)
 */
public class BackQuoteBehaviour implements SubstBehaviourBase
{
	LispEnvironment iEnvironment;

	public BackQuoteBehaviour(LispEnvironment aEnvironment)
	{
		iEnvironment = aEnvironment;
	}
	public boolean matches(LispPtr aResult, LispPtr aElement) throws Exception
	{
		if (aElement.get().subList() == null) return false;
		LispObject ptr = aElement.get().subList().get();
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
			LispPtr cur = new LispPtr();
			cur.set(ptr);
			iEnvironment.iEvaluator.eval(iEnvironment, aResult, cur);
			return true;
		}
		else
		{
			ptr = ptr.subList().get();
			LispPtr cur = new LispPtr();
			cur.set(ptr);
			LispPtr args = new LispPtr();
			args.set(ptr.cdr().get());
			LispPtr result = new LispPtr();
			iEnvironment.iEvaluator.eval(iEnvironment, result, cur);
			result.get().cdr().set(args.get());
			LispPtr result2 = new LispPtr();
			result2.set(LispSubList.getInstance(result.get()));
			LispStandard.internalSubstitute(aResult, result2,this);
			return true;
		}
		//      return false;
	}

};
