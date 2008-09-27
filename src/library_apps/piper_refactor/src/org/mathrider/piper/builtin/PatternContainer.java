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

package org.mathrider.piper.builtin;

//import org.mathrider.piper.parametermatchers.PatternContainer;
import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.ArgList;


/**
 * Allows a org.mathrider.piper.parametermatchers.Pattern to be placed into a org.mathrider.piper.lisp.BuiltinObject.
 * @author
 */
public class PatternContainer extends BuiltinContainer
{
	protected org.mathrider.piper.parametermatchers.Pattern iPatternMatcher;
	
	public PatternContainer(org.mathrider.piper.parametermatchers.Pattern aPatternMatcher)
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
	
	//From BuiltinContainer
	public String send(ArgList aArgList)
	{
		return null;
	}
	
	public String typeName()
	{
		return "\"Pattern\"";
	}

}


