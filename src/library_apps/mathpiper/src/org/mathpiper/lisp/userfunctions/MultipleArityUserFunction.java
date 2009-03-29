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

package org.mathpiper.lisp.userfunctions;


import org.mathpiper.lisp.DefFile;
import org.mathpiper.lisp.*;
import java.util.*;



/**
 * Holds a set of {@link UserFunction} which are associated with one function name.
 * A specific UserFunction can be selected by providing its name.  The
 * name of the file in which the function is defined can also be specified.
 */
public class MultipleArityUserFunction
{

	/// Set of UserFunction's provided by this MultipleArityUserFunction.
	List<UserFunction> iFunctions = new ArrayList();//

	/// File to read for the definition of this function.
	public DefFile iFileToOpen;
    
    public String iFileLocation;

	/// Constructor.
	public MultipleArityUserFunction()
	{
		iFileToOpen = null;
	}

	/// Return user function with given arity.
	public UserFunction getUserFunction(int aArity) throws Exception
	{
		int i;
		//Find function body with the right arity
		int nrc=iFunctions.size();
		for (i=0;i<nrc;i++)
		{
			LispError.lispAssert(iFunctions.get(i) != null);
			if (((UserFunction)iFunctions.get(i)).isArity(aArity))
			{
				return (UserFunction)iFunctions.get(i);
			}
		}

		// if function not found, just unaccept!
		// User-defined function not found! Returning null
		return null;
	}

	/// Specify that some argument should be held.
	public void holdArgument(String aVariable) throws Exception
	{
		int i;
		for (i=0;i<iFunctions.size();i++)
		{
			LispError.lispAssert(iFunctions.get(i) != null);
			((UserFunction)iFunctions.get(i)).holdArgument(aVariable);
		}
	}

	/// Add another UserFunction to #iFunctions.
	public  void addRulebaseEntry(UserFunction aNewFunction) throws Exception
	{
		int i;
		//Find function body with the right arity
		int nrc=iFunctions.size();
		for (i=0;i<nrc;i++)
		{
			LispError.lispAssert(((UserFunction)iFunctions.get(i)) != null);
			LispError.lispAssert(aNewFunction != null);
			LispError.check(!((UserFunction)iFunctions.get(i)).isArity(aNewFunction.arity()),LispError.KLispErrArityAlreadyDefined);
			LispError.check(!aNewFunction.isArity(((UserFunction)iFunctions.get(i)).arity()),LispError.KLispErrArityAlreadyDefined);
		}
		iFunctions.add(aNewFunction);
	}

	/// Deletet user function with given arity.
	public  void deleteRulebaseEntry(int aArity) throws Exception
	{
		int i;
		//Find function body with the right arity
		int nrc=iFunctions.size();
		for (i=0;i<nrc;i++)
		{
			LispError.lispAssert(((UserFunction)iFunctions.get(i)) != null);
			if (((UserFunction)iFunctions.get(i)).isArity(aArity))
			{
				iFunctions.remove(i);
				return;
			}
		}
	}


    public Iterator getFunctions()
    {
        return this.iFunctions.iterator();
    }


}
