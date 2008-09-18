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

import org.mathrider.piper.lisp.LispPtr;
import org.mathrider.piper.lisp.LispEnvironment;
import org.mathrider.piper.lisp.LispParser;
import org.mathrider.piper.lisp.LispTokenizer;
import org.mathrider.piper.lisp.LispInput;
import org.mathrider.piper.lisp.LispOperators;


public class InfixParser extends LispParser
{
	public LispOperators iPrefixOperators;
	public LispOperators iInfixOperators;
	public LispOperators iPostfixOperators;
	public LispOperators iBodiedOperators;

	public InfixParser(LispTokenizer aTokenizer, LispInput aInput,
	                   LispEnvironment aEnvironment,
	                   LispOperators aPrefixOperators,
	                   LispOperators aInfixOperators,
	                   LispOperators aPostfixOperators,
	                   LispOperators aBodiedOperators)
	{
		super( aTokenizer,  aInput, aEnvironment);
		iPrefixOperators = aPrefixOperators;
		iInfixOperators = aInfixOperators;
		iPostfixOperators = aPostfixOperators;
		iBodiedOperators = aBodiedOperators;
	}

	public void Parse(LispPtr aResult) throws Exception
	{
		ParseCont(aResult);
	}

	public void ParseCont(LispPtr aResult) throws Exception
	{
		ParsedObject object = new ParsedObject(this);
		object.Parse();
		aResult.set(object.iResult.get());
		return ;
	}

}


