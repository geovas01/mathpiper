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
	public boolean Matches(LispPtr aResult, LispPtr aElement) throws Exception
	{
		if (aElement.Get().SubList() == null) return false;
		LispObject ptr = aElement.Get().SubList().Get();
		if (ptr == null) return false;
		if (ptr.String() == null) return false;

		if (ptr.String().equals("`"))
		{
			aResult.Set(aElement.Get());
			return true;
		}

		if (!ptr.String().equals("@"))
			return false;
		ptr = ptr.Next().Get();
		if (ptr == null)
			return false;
		if (ptr.String() != null)
		{
			LispPtr cur = new LispPtr();
			cur.Set(ptr);
			iEnvironment.iEvaluator.Eval(iEnvironment, aResult, cur);
			return true;
		}
		else
		{
			ptr = ptr.SubList().Get();
			LispPtr cur = new LispPtr();
			cur.Set(ptr);
			LispPtr args = new LispPtr();
			args.Set(ptr.Next().Get());
			LispPtr result = new LispPtr();
			iEnvironment.iEvaluator.Eval(iEnvironment, result, cur);
			result.Get().Next().Set(args.Get());
			LispPtr result2 = new LispPtr();
			result2.Set(LispSubList.New(result.Get()));
			LispStandard.InternalSubstitute(aResult, result2,this);
			return true;
		}
		//      return false;
	}

};
