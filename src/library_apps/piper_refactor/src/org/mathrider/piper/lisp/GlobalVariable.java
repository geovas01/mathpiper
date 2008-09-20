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


/// Value of a Lisp global variable.
/// The only special feature of this class is the attribute
/// #iEvalBeforeReturn, which defaults to #LispFalse. If this
/// attribute is set to #LispTrue, the value in #iValue needs to be
/// evaluated to get the value of the Lisp variable.
/// \sa LispEnvironment::GetVariable()

public class GlobalVariable
{
	Pointer iValue = new Pointer();
	boolean iEvalBeforeReturn;

	public GlobalVariable(GlobalVariable aOther)
	{
		iValue = aOther.iValue;
		iEvalBeforeReturn = aOther.iEvalBeforeReturn;
	}
	public GlobalVariable(Pointer aValue)
	{
		iValue.set(aValue.get());
		iEvalBeforeReturn = false;
	}
	public  void SetEvalBeforeReturn(boolean aEval)
	{
		iEvalBeforeReturn = aEval;
	}

}
