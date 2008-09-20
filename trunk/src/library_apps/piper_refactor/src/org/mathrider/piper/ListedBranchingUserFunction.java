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

import org.mathrider.piper.lisp.LispPtr;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.LispIterator;
import org.mathrider.piper.lisp.LispEnvironment;
import org.mathrider.piper.lisp.LispSubList;


public class ListedBranchingUserFunction extends BranchingUserFunction
{
	public ListedBranchingUserFunction(LispPtr  aParameters) throws Exception
	{
		super(aParameters);
	}
	
	public boolean IsArity(int aArity)
	{
		return (Arity() <= aArity);
	}
	
	public void Evaluate(LispPtr aResult, LispEnvironment aEnvironment, LispPtr aArguments) throws Exception
	{
		LispPtr newArgs = new LispPtr();
		LispIterator iter = new LispIterator(aArguments);
		LispPtr ptr =  newArgs;
		int arity = Arity();
		int i=0;
		while (i < arity && iter.GetObject() != null)
		{
			ptr.set(iter.GetObject().copy(false));
			ptr = (ptr.get().cdr());
			i++;
			iter.GoNext();
		}
		if (iter.GetObject().cdr().get() == null)
		{
			ptr.set(iter.GetObject().copy(false));
			ptr = (ptr.get().cdr());
			i++;
			iter.GoNext();
			LispError.LISPASSERT(iter.GetObject() == null);
		}
		else
		{
			LispPtr head = new LispPtr();
			head.set(aEnvironment.iList.copy(false));
			head.get().cdr().set(iter.GetObject());
			ptr.set(LispSubList.getInstance(head.get()));
		}
		super.Evaluate(aResult, aEnvironment, newArgs);
	}
}


