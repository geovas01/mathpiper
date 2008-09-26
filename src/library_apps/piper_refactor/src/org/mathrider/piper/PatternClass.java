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

import org.mathrider.piper.parametermatchers.Pattern;
import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.ArgList;


/// Wrapper for Pattern.
/// This class allows a Pattern to be put in a
/// LispGenericObject.
public class PatternClass extends GenericClassContainer
{
	protected Pattern iPatternMatcher;
	
	public PatternClass(Pattern aPatternMatcher)
	{
		iPatternMatcher = aPatternMatcher;
	}

	public boolean matches(Environment  aEnvironment, Pointer aArguments) throws Exception
	{
		LispError.LISPASSERT(iPatternMatcher != null);
		boolean result;
		result = iPatternMatcher.matches(aEnvironment, aArguments);
		return result;
	}
	
	public boolean matches(Environment  aEnvironment, Pointer[] aArguments) throws Exception
	{
		LispError.LISPASSERT(iPatternMatcher != null);
		boolean result;
		result = iPatternMatcher.matches(aEnvironment, aArguments);
		return result;
	}
	
	//From GenericClassContainer
	public String send(ArgList aArgList)
	{
		return null;
	}
	
	public String typeName()
	{
		return "\"Pattern\"";
	}

}


