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


class ListedBranchingUserFunction extends BranchingUserFunction
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
			ptr.Set(iter.GetObject().Copy(false));
			ptr = (ptr.Get().Next());
			i++;
			iter.GoNext();
		}
		if (iter.GetObject().Next().Get() == null)
		{
			ptr.Set(iter.GetObject().Copy(false));
			ptr = (ptr.Get().Next());
			i++;
			iter.GoNext();
			LispError.LISPASSERT(iter.GetObject() == null);
		}
		else
		{
			LispPtr head = new LispPtr();
			head.Set(aEnvironment.iList.Copy(false));
			head.Get().Next().Set(iter.GetObject());
			ptr.Set(LispSubList.New(head.Get()));
		}
		super.Evaluate(aResult, aEnvironment, newArgs);
	}
}


