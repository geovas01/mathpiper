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


public class LispParser
{
	public LispTokenizer iTokenizer;
	public LispInput iInput;
	public LispEnvironment iEnvironment;
	public boolean iListed;
	
	public LispParser(LispTokenizer aTokenizer, LispInput aInput,
	                  LispEnvironment aEnvironment)
	{
		iTokenizer = aTokenizer;
		iInput = aInput;
		iEnvironment = aEnvironment;
		iListed = false;
	}
	
	public void Parse(LispPtr aResult ) throws Exception
	{
		aResult.set(null);

		String token;
		// Get token.
		token = iTokenizer.nextToken(iInput,iEnvironment.hashTable());
		if (token.length() == 0) //TODO FIXME either token == null or token.length() == 0?
		{
			aResult.set(LispAtom.getInstance(iEnvironment,"EndOfFile"));
			return;
		}
		ParseAtom(aResult,token);
	}

	void ParseList(LispPtr aResult) throws Exception
	{
		String token;

		LispPtr iter = aResult;
		if (iListed)
		{
			aResult.set(LispAtom.getInstance(iEnvironment,"List"));
			iter  = (aResult.get().cdr()); //TODO FIXME
		}
		for (;;)
		{
			//Get token.
			token = iTokenizer.nextToken(iInput,iEnvironment.hashTable());
			// if token is empty string, error!
			LispError.Check(token.length() > 0,LispError.KInvalidToken); //TODO FIXME
			// if token is ")" return result.
			if (token == iEnvironment.hashTable().lookUp(")"))
			{
				return;
			}
			// else parse simple atom with Parse, and append it to the
			// results list.

			ParseAtom(iter,token);
			iter = (iter.get().cdr()); //TODO FIXME
		}
	}

	void ParseAtom(LispPtr aResult,String aToken) throws Exception
	{
		// if token is empty string, return null pointer (no expression)
		if (aToken.length() == 0) //TODO FIXME either token == null or token.length() == 0?
			return;
		// else if token is "(" read in a whole array of objects until ")",
		//   and make a sublist
		if (aToken == iEnvironment.hashTable().lookUp("("))
		{
			LispPtr subList = new LispPtr();
			ParseList(subList);
			aResult.set(LispSubList.getInstance(subList.get()));
			return;
		}
		// else make a simple atom, and return it.
		aResult.set(LispAtom.getInstance(iEnvironment,aToken));
	}
	
}

