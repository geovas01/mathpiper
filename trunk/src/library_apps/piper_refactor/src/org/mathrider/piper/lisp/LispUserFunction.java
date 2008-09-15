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

package org.mathrider.piper.lisp;

import org.mathrider.piper.*;


/// Abstract class providing the basic user function API.
/// Instances of this class are associated to the name of the function
/// via an associated hash table. When obtained, they can be used to
/// evaluate the function with some arguments.

public abstract class LispUserFunction extends EvalFuncBase
{
	boolean iFenced;
	boolean iTraced;
	
	public LispUserFunction()
	{
		iFenced = true;
		iTraced = false;
	}
	public abstract void Evaluate(LispPtr aResult,LispEnvironment aEnvironment, LispPtr aArguments) throws Exception;
	public abstract void HoldArgument(String aVariable);
	public abstract void DeclareRule(int aPrecedence, LispPtr aPredicate, LispPtr aBody) throws Exception;
	public abstract void DeclareRule(int aPrecedence, LispPtr aBody) throws Exception;
	public abstract void DeclarePattern(int aPrecedence, LispPtr aPredicate, LispPtr aBody) throws Exception;
	public abstract LispPtr ArgList();

	public void UnFence()
	{
		iFenced = false;
	}
	
	public boolean Fenced()
	{
		return iFenced;
	}

	public void Trace()
	{
		iTraced = true;
	}
	
	public void UnTrace()
	{
		iTraced = false;
	}
	
	public boolean Traced()
	{
		return iTraced;
	}

};
