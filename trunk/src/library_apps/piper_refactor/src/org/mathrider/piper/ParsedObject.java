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

import org.mathrider.piper.printers.InfixPrinter;
import org.mathrider.piper.lisp.parsers.InfixParser;
import org.mathrider.piper.lisp.Standard;
import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.Iterator;
import org.mathrider.piper.lisp.Atom;
import org.mathrider.piper.lisp.parsers.Tokenizer;
import org.mathrider.piper.lisp.Input;
import org.mathrider.piper.lisp.SubList;
import org.mathrider.piper.lisp.InfixOperator;


public class ParsedObject
{
	InfixParser iParser;
	boolean iError;
	boolean iEndOfFile;
	String iLookAhead;

	public Pointer iResult =  new Pointer();
	
	public ParsedObject(InfixParser aParser)
	{
		iParser = aParser;
		iError = false;
		iEndOfFile = false;
		iLookAhead = null;
	}
	
	public void Parse() throws Exception
	{
		ReadToken();
		if (iEndOfFile)
		{
			iResult.set(iParser.iEnvironment.iEndOfFile.copy(true));
			return;
		}

		ReadExpression(InfixPrinter.KMaxPrecedence);  // least precedence

		if (iLookAhead != iParser.iEnvironment.iEndStatement.string())
		{
			Fail();
		}
		if (iError)
		{
			while (iLookAhead.length() > 0 && iLookAhead != iParser.iEnvironment.iEndStatement.string())
			{
				ReadToken();
			}
		}

		if (iError)
		{
			iResult.set(null);
		}
		LispError.Check(!iError,LispError.KLispErrInvalidExpression);
	}
	
	void ReadToken() throws Exception
	{
		// Get token.
		iLookAhead = iParser.iTokenizer.nextToken(iParser.iInput,
		                iParser.iEnvironment.hashTable());
		if (iLookAhead.length() == 0)
			iEndOfFile=true;
	}
	
	void MatchToken(String aToken) throws Exception
	{
		if (aToken != iLookAhead)
			Fail();
		ReadToken();
	}
	
	void ReadExpression(int depth) throws Exception
	{
		ReadAtom();

		for(;;)
		{
			//Handle special case: a[b]. a is matched with lowest precedence!!
			if (iLookAhead == iParser.iEnvironment.iProgOpen.string())
			{
				// Match opening bracket
				MatchToken(iLookAhead);
				// Read "index" argument
				ReadExpression(InfixPrinter.KMaxPrecedence);
				// Match closing bracket
				if (iLookAhead != iParser.iEnvironment.iProgClose.string())
				{
					LispError.RaiseError("Expecting a ] close bracket for program block, but got "+iLookAhead+" instead");
					return;
				}
				MatchToken(iLookAhead);
				// Build into Ntn(...)
				String theOperator = iParser.iEnvironment.iNth.string();
				InsertAtom(theOperator);
				Combine(2);
			}
			else
			{
				InfixOperator op = (InfixOperator)iParser.iInfixOperators.lookUp(iLookAhead);
				if (op == null)
				{
					//printf("op [%s]\n",iLookAhead.String());
					if (Tokenizer.isSymbolic(iLookAhead.charAt(0)))
					{
						int origlen = iLookAhead.length();
						int len = origlen;
						//printf("IsSymbolic, len=%d\n",len);

						while (len>1)
						{
							len--;
							String lookUp =
							        iParser.iEnvironment.hashTable().lookUp(iLookAhead.substring(0,len));

							//printf("trunc %s\n",lookUp.String());
							op = (InfixOperator)iParser.iInfixOperators.lookUp(lookUp);
							//if (op) printf("FOUND\n");
							if (op != null)
							{
								String toLookUp = iLookAhead.substring(len,origlen);
								String lookUpRight =
								        iParser.iEnvironment.hashTable().lookUp(toLookUp);

								//printf("right: %s (%d)\n",lookUpRight.String(),origlen-len);

								if (iParser.iPrefixOperators.lookUp(lookUpRight) != null)
								{
									//printf("ACCEPT %s\n",lookUp.String());
									iLookAhead = lookUp;
									Input input = iParser.iInput;
									int newPos = input.Position()-(origlen-len);
									input.SetPosition(newPos);
									//printf("Pushhback %s\n",&input.StartPtr()[input.Position()]);
									break;
								}
								else op=null;
							}
						}
						if (op == null) return;
					}
					else
					{
						return;
					}




					//              return;
				}
				if (depth < op.iPrecedence)
					return;
				int upper=op.iPrecedence;
				if (op.iRightAssociative == 0)
					upper--;
				GetOtherSide(2,upper);
			}
		}
	}
	
	void ReadAtom() throws Exception
	{
		InfixOperator op;
		// Parse prefix operators
		op = (InfixOperator)iParser.iPrefixOperators.lookUp(iLookAhead);
		if (op != null)
		{
			String theOperator = iLookAhead;
			MatchToken(iLookAhead);
			{
				ReadExpression(op.iPrecedence);
				InsertAtom(theOperator);
				Combine(1);
			}
		}
		// Else parse brackets
		else if (iLookAhead == iParser.iEnvironment.iBracketOpen.string())
		{
			MatchToken(iLookAhead);
			ReadExpression(InfixPrinter.KMaxPrecedence);  // least precedence
			MatchToken(iParser.iEnvironment.iBracketClose.string());
		}
		//Parse lists
		else if (iLookAhead == iParser.iEnvironment.iListOpen.string())
		{
			int nrargs=0;
			MatchToken(iLookAhead);
			while (iLookAhead != iParser.iEnvironment.iListClose.string())
			{
				ReadExpression(InfixPrinter.KMaxPrecedence);  // least precedence
				nrargs++;

				if (iLookAhead == iParser.iEnvironment.iComma.string())
				{
					MatchToken(iLookAhead);
				}
				else if (iLookAhead != iParser.iEnvironment.iListClose.string())
				{
					LispError.RaiseError("Expecting a } close bracket for a list, but got "+iLookAhead+" instead");
					return;
				}
			}
			MatchToken(iLookAhead);
			String theOperator = iParser.iEnvironment.iList.string();
			InsertAtom(theOperator);
			Combine(nrargs);

		}
		// Parse prog bodies
		else if (iLookAhead == iParser.iEnvironment.iProgOpen.string())
		{
			int nrargs=0;

			MatchToken(iLookAhead);
			while (iLookAhead != iParser.iEnvironment.iProgClose.string())
			{
				ReadExpression(InfixPrinter.KMaxPrecedence);  // least precedence
				nrargs++;

				if (iLookAhead == iParser.iEnvironment.iEndStatement.string())
				{
					MatchToken(iLookAhead);
				}
				else
				{
					LispError.RaiseError("Expecting ; end of statement in program block, but got "+iLookAhead+" instead");
					return;
				}
			}
			MatchToken(iLookAhead);
			String theOperator = iParser.iEnvironment.iProg.string();
			InsertAtom(theOperator);

			Combine(nrargs);
		}
		// Else we have an atom.
		else
		{
			String theOperator = iLookAhead;
			MatchToken(iLookAhead);

			int nrargs=-1;
			if (iLookAhead == iParser.iEnvironment.iBracketOpen.string())
			{
				nrargs=0;
				MatchToken(iLookAhead);
				while (iLookAhead != iParser.iEnvironment.iBracketClose.string())
				{
					ReadExpression(InfixPrinter.KMaxPrecedence);  // least precedence
					nrargs++;

					if (iLookAhead == iParser.iEnvironment.iComma.string())
					{
						MatchToken(iLookAhead);
					}
					else if (iLookAhead != iParser.iEnvironment.iBracketClose.string())
					{
						LispError.RaiseError("Expecting ) closing bracket for sub-expression, but got "+iLookAhead+" instead");
						return;
					}
				}
				MatchToken(iLookAhead);

				op = (InfixOperator)iParser.iBodiedOperators.lookUp(theOperator);
				if (op != null)
				{
					ReadExpression(op.iPrecedence); // InfixPrinter.KMaxPrecedence
					nrargs++;
				}
			}
			InsertAtom(theOperator);
			if (nrargs>=0)
				Combine(nrargs);

		}

		// Parse postfix operators

		while ((op = (InfixOperator)iParser.iPostfixOperators.lookUp(iLookAhead)) != null)
		{
			InsertAtom(iLookAhead);
			MatchToken(iLookAhead);
			Combine(1);
		}
	}
	
	void GetOtherSide(int aNrArgsToCombine, int depth) throws Exception
	{
		String theOperator = iLookAhead;
		MatchToken(iLookAhead);
		ReadExpression(depth);
		InsertAtom(theOperator);
		Combine(aNrArgsToCombine);
	}
	
	void Combine(int aNrArgsToCombine) throws Exception
	{
		Pointer subList = new Pointer();
		subList.set(SubList.getInstance(iResult.get()));
		Iterator iter = new Iterator(iResult);
		int i;
		for (i=0;i<aNrArgsToCombine;i++)
		{
			if (iter.GetObject() == null)
			{
				Fail();
				return;
			}
			iter.GoNext();
		}
		if (iter.GetObject() == null)
		{
			Fail();
			return;
		}
		subList.get().cdr().set(iter.GetObject().cdr().get());
		iter.GetObject().cdr().set(null);

		Standard.internalReverseList(subList.get().subList().get().cdr(),
		                                 subList.get().subList().get().cdr());
		iResult.set(subList.get());
	}
	
	void InsertAtom(String aString) throws Exception
	{
		Pointer ptr = new Pointer();
		ptr.set(Atom.getInstance(iParser.iEnvironment,aString));
		ptr.get().cdr().set(iResult.get());
		iResult.set(ptr.get());
	}
	
	void Fail()  throws Exception // called when parsing fails, raising an exception
	{
		iError = true;
		if (iLookAhead != null)
		{
			LispError.RaiseError("Error parsing expression, near token "+iLookAhead);
		}
		LispError.RaiseError("Error parsing expression");
	}

};
