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
import org.mathrider.piper.lisp.Iterator;
import org.mathrider.piper.lisp.Environment;


/// Class for matching against a list of PiperParamMatcherBase objects.
public class MatchSubList extends PiperParamMatcherBase
{
	protected PiperParamMatcherBase[] iMatchers;
	protected int iNrMatchers;
	
	public MatchSubList(PiperParamMatcherBase[] aMatchers, int aNrMatchers)
	{
		iMatchers = aMatchers;
		iNrMatchers = aNrMatchers;
	}

	public boolean argumentMatches(Environment  aEnvironment,
	                               Pointer  aExpression,
	                               Pointer[]  arguments) throws Exception
	{
		if (aExpression.get().subList() == null)
			return false;
		int i;

		Iterator iter = new Iterator(aExpression);
		iter.GoSub();

		for (i=0;i<iNrMatchers;i++)
		{
			Pointer  ptr = iter.Ptr();
			if (ptr == null)
				return false;
			if (iter.GetObject() == null)
				return false;
			if (!iMatchers[i].argumentMatches(aEnvironment,ptr,arguments))
				return false;
			iter.GoNext();
		}
		if (iter.GetObject() != null)
			return false;
		return true;
	}

	
}
