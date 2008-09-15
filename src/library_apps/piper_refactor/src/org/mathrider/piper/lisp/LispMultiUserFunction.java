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


import java.util.*;

/// Set of LispArityUserFunction's.
/// By using this class, you can associate multiple functions (with
/// different arities) to one name. A specific LispArityUserFunction
/// can be selected by providing its name. Additionally, the name of
/// the file in which the function is defined, can be specified.

public class LispMultiUserFunction
{

	/// Set of LispArityUserFunction's provided by this LispMultiUserFunction.
	ArrayList iFunctions = new ArrayList();//<LispArityUserFunction*>

	/// File to read for the definition of this function.
	public LispDefFile iFileToOpen;

	/// Constructor.
	public LispMultiUserFunction()
	{
		iFileToOpen = null;
	}

	/// Return user function with given arity.
	public LispUserFunction UserFunc(int aArity) throws Exception
	{
		int i;
		//Find function body with the right arity
		int nrc=iFunctions.size();
		for (i=0;i<nrc;i++)
		{
			LispError.LISPASSERT(iFunctions.get(i) != null);
			if (((LispArityUserFunction)iFunctions.get(i)).IsArity(aArity))
			{
				return (LispArityUserFunction)iFunctions.get(i);
			}
		}

		// if function not found, just unaccept!
		// User-defined function not found! Returning null
		return null;
	}

	/// Specify that some argument should be held.
	public void HoldArgument(String aVariable) throws Exception
	{
		int i;
		for (i=0;i<iFunctions.size();i++)
		{
			LispError.LISPASSERT(iFunctions.get(i) != null);
			((LispArityUserFunction)iFunctions.get(i)).HoldArgument(aVariable);
		}
	}

	/// Add another LispArityUserFunction to #iFunctions.
	public  void DefineRuleBase(LispArityUserFunction aNewFunction) throws Exception
	{
		int i;
		//Find function body with the right arity
		int nrc=iFunctions.size();
		for (i=0;i<nrc;i++)
		{
			LispError.LISPASSERT(((LispArityUserFunction)iFunctions.get(i)) != null);
			LispError.LISPASSERT(aNewFunction != null);
			LispError.Check(!((LispArityUserFunction)iFunctions.get(i)).IsArity(aNewFunction.Arity()),LispError.KLispErrArityAlreadyDefined);
			LispError.Check(!aNewFunction.IsArity(((LispArityUserFunction)iFunctions.get(i)).Arity()),LispError.KLispErrArityAlreadyDefined);
		}
		iFunctions.add(aNewFunction);
	}

	/// Delete tuser function with given arity.
	public  void DeleteBase(int aArity) throws Exception
	{
		int i;
		//Find function body with the right arity
		int nrc=iFunctions.size();
		for (i=0;i<nrc;i++)
		{
			LispError.LISPASSERT(((LispArityUserFunction)iFunctions.get(i)) != null);
			if (((LispArityUserFunction)iFunctions.get(i)).IsArity(aArity))
			{
				iFunctions.remove(i);
				return;
			}
		}
	}


}
