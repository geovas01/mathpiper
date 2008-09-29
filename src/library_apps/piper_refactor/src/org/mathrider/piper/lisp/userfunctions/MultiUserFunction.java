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


import org.mathrider.piper.lisp.*;
import java.util.*;

/// Set of ArityUserFunction's.
/// By using this class, you can associate multiple functions (with
/// different arities) to one name. A specific ArityUserFunction
/// can be selected by providing its name. Additionally, the name of
/// the file in which the function is defined, can be specified.

public class MultiUserFunction
{

	/// Set of ArityUserFunction's provided by this MultiUserFunction.
	ArrayList iFunctions = new ArrayList();//<ArityUserFunction*>

	/// File to read for the definition of this function.
	public DefFile iFileToOpen;

	/// Constructor.
	public MultiUserFunction()
	{
		iFileToOpen = null;
	}

	/// Return user function with given arity.
	public UserFunction UserFunc(int aArity) throws Exception
	{
		int i;
		//Find function body with the right arity
		int nrc=iFunctions.size();
		for (i=0;i<nrc;i++)
		{
			LispError.lispAssert(iFunctions.get(i) != null);
			if (((ArityUserFunction)iFunctions.get(i)).IsArity(aArity))
			{
				return (ArityUserFunction)iFunctions.get(i);
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
			LispError.lispAssert(iFunctions.get(i) != null);
			((ArityUserFunction)iFunctions.get(i)).HoldArgument(aVariable);
		}
	}

	/// Add another ArityUserFunction to #iFunctions.
	public  void DefineRuleBase(ArityUserFunction aNewFunction) throws Exception
	{
		int i;
		//Find function body with the right arity
		int nrc=iFunctions.size();
		for (i=0;i<nrc;i++)
		{
			LispError.lispAssert(((ArityUserFunction)iFunctions.get(i)) != null);
			LispError.lispAssert(aNewFunction != null);
			LispError.check(!((ArityUserFunction)iFunctions.get(i)).IsArity(aNewFunction.Arity()),LispError.KLispErrArityAlreadyDefined);
			LispError.check(!aNewFunction.IsArity(((ArityUserFunction)iFunctions.get(i)).Arity()),LispError.KLispErrArityAlreadyDefined);
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
			LispError.lispAssert(((ArityUserFunction)iFunctions.get(i)) != null);
			if (((ArityUserFunction)iFunctions.get(i)).IsArity(aArity))
			{
				iFunctions.remove(i);
				return;
			}
		}
	}


}
