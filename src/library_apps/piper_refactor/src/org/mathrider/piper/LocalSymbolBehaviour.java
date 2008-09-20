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

import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.Atom;
import org.mathrider.piper.lisp.Environment;

/** subst behaviour for changing the local variables to have unique
 * names.
 */
public class LocalSymbolBehaviour implements SubstBehaviourBase
{
	Environment iEnvironment;
	String[] iOriginalNames;
	String[] iNewNames;
	int iNrNames;
	
	public LocalSymbolBehaviour(Environment aEnvironment,
	                            String[] aOriginalNames,
	                            String[] aNewNames, int aNrNames)
	{
		iEnvironment = aEnvironment;
		iOriginalNames = aOriginalNames;
		iNewNames = aNewNames;
		iNrNames = aNrNames;
	}
	public boolean matches(Pointer aResult, Pointer aElement) throws Exception
	{
		String name = aElement.get().string();
		if (name == null)
			return false;

		int i;
		for (i=0;i<iNrNames;i++)
		{
			if (name == iOriginalNames[i])
			{
				aResult.set(Atom.getInstance(iEnvironment,iNewNames[i]));
				return true;
			}
		}
		return false;
	}

};
