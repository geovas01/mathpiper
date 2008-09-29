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

package org.mathrider.piper.lisp.userfunctions;

import org.mathrider.piper.lisp.ConsPointer;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.ConsTraverser;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.SubList;


public class ListedBranchingUserFunction extends BranchingUserFunction
{
	public ListedBranchingUserFunction(ConsPointer  aParameters) throws Exception
	{
		super(aParameters);
	}
	
	public boolean IsArity(int aArity)
	{
		return (Arity() <= aArity);
	}
	
	public void evaluate(ConsPointer aResult, Environment aEnvironment, ConsPointer aArguments) throws Exception
	{
		ConsPointer newArgs = new ConsPointer();
		ConsTraverser iter = new ConsTraverser(aArguments);
		ConsPointer ptr =  newArgs;
		int arity = Arity();
		int i=0;
		while (i < arity && iter.getCons() != null)
		{
			ptr.set(iter.getCons().copy(false));
			ptr = (ptr.get().cdr());
			i++;
			iter.goNext();
		}
		if (iter.getCons().cdr().get() == null)
		{
			ptr.set(iter.getCons().copy(false));
			ptr = (ptr.get().cdr());
			i++;
			iter.goNext();
			LispError.lispAssert(iter.getCons() == null);
		}
		else
		{
			ConsPointer head = new ConsPointer();
			head.set(aEnvironment.iList.copy(false));
			head.get().cdr().set(iter.getCons());
			ptr.set(SubList.getInstance(head.get()));
		}
		super.evaluate(aResult, aEnvironment, newArgs);
	}
}


