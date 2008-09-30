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

package org.mathrider.piper.lisp.parsers;

import org.mathrider.piper.printers.InfixPrinter;
import org.mathrider.piper.lisp.parsers.InfixParser;
import org.mathrider.piper.lisp.Utility;
import org.mathrider.piper.lisp.ConsPointer;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.ConsTraverser;
import org.mathrider.piper.lisp.Atom;
import org.mathrider.piper.lisp.parsers.Tokenizer;
import org.mathrider.piper.lisp.Input;
import org.mathrider.piper.lisp.SubList;
import org.mathrider.piper.lisp.InfixOperator;


public class ParsedInfixExpression
{
	InfixParser iParser;
	boolean iError;
	boolean iEndOfFile;
	String iLookAhead;

	public ConsPointer iResult =  new ConsPointer();
	
	public ParsedInfixExpression(InfixParser aParser)
	{
		iParser = aParser;
		iError = false;
		iEndOfFile = false;
		iLookAhead = null;
	}
	
	public void parse() throws Exception
	{
		readToken();
		if (iEndOfFile)
		{
			iResult.set(iParser.iEnvironment.iEndOfFile.copy(true));
			return;
		}

		readExpression(InfixPrinter.KMaxPrecedence);  // least precedence

		if (iLookAhead != iParser.iEnvironment.iEndStatement.string())
		{
			fail();
		}
		if (iError)
		{
			while (iLookAhead.length() > 0 && iLookAhead != iParser.iEnvironment.iEndStatement.string())
			{
				readToken();
			}
		}

		if (iError)
		{
			iResult.set(null);
		}
		LispError.check(!iError,LispError.KLispErrInvalidExpression);
	}
	
	void readToken() throws Exception
	{
		// Get token.
		iLookAhead = iParser.iTokenizer.nextToken(iParser.iInput,
		                iParser.iEnvironment.getTokenHash());
		if (iLookAhead.length() == 0)
			iEndOfFile=true;
	}
	
	void matchToken(String aToken) throws Exception
	{
		if (aToken != iLookAhead)
			fail();
		readToken();
	}
	
	void readExpression(int depth) throws Exception
	{
		readAtom();

		for(;;)
		{
			//Handle special case: a[b]. a is matched with lowest precedence!!
			if (iLookAhead == iParser.iEnvironment.iProgOpen.string())
			{
				// Match opening bracket
				matchToken(iLookAhead);
				// Read "index" argument
				readExpression(InfixPrinter.KMaxPrecedence);
				// Match closing bracket
				if (iLookAhead != iParser.iEnvironment.iProgClose.string())
				{
					LispError.raiseError("Expecting a ] close bracket for program block, but got "+iLookAhead+" instead");
					return;
				}
				matchToken(iLookAhead);
				// Build into Ntn(...)
				String theOperator = iParser.iEnvironment.iNth.string();
				insertAtom(theOperator);
				combine(2);
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
							        iParser.iEnvironment.getTokenHash().lookUp(iLookAhead.substring(0,len));

							//printf("trunc %s\n",lookUp.String());
							op = (InfixOperator)iParser.iInfixOperators.lookUp(lookUp);
							//if (op) printf("FOUND\n");
							if (op != null)
							{
								String toLookUp = iLookAhead.substring(len,origlen);
								String lookUpRight =
								        iParser.iEnvironment.getTokenHash().lookUp(toLookUp);

								//printf("right: %s (%d)\n",lookUpRight.String(),origlen-len);

								if (iParser.iPrefixOperators.lookUp(lookUpRight) != null)
								{
									//printf("ACCEPT %s\n",lookUp.String());
									iLookAhead = lookUp;
									Input input = iParser.iInput;
									int newPos = input.position()-(origlen-len);
									input.setPosition(newPos);
									//printf("Pushhback %s\n",&input.startPtr()[input.position()]);
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
				getOtherSide(2,upper);
			}
		}
	}
	
	void readAtom() throws Exception
	{
		InfixOperator op;
		// parse prefix operators
		op = (InfixOperator)iParser.iPrefixOperators.lookUp(iLookAhead);
		if (op != null)
		{
			String theOperator = iLookAhead;
			matchToken(iLookAhead);
			{
				readExpression(op.iPrecedence);
				insertAtom(theOperator);
				combine(1);
			}
		}
		// Else parse brackets
		else if (iLookAhead == iParser.iEnvironment.iBracketOpen.string())
		{
			matchToken(iLookAhead);
			readExpression(InfixPrinter.KMaxPrecedence);  // least precedence
			matchToken(iParser.iEnvironment.iBracketClose.string());
		}
		//parse lists
		else if (iLookAhead == iParser.iEnvironment.iListOpen.string())
		{
			int nrargs=0;
			matchToken(iLookAhead);
			while (iLookAhead != iParser.iEnvironment.iListClose.string())
			{
				readExpression(InfixPrinter.KMaxPrecedence);  // least precedence
				nrargs++;

				if (iLookAhead == iParser.iEnvironment.iComma.string())
				{
					matchToken(iLookAhead);
				}
				else if (iLookAhead != iParser.iEnvironment.iListClose.string())
				{
					LispError.raiseError("Expecting a } close bracket for a list, but got "+iLookAhead+" instead");
					return;
				}
			}
			matchToken(iLookAhead);
			String theOperator = iParser.iEnvironment.iList.string();
			insertAtom(theOperator);
			combine(nrargs);

		}
		// parse prog bodies
		else if (iLookAhead == iParser.iEnvironment.iProgOpen.string())
		{
			int nrargs=0;

			matchToken(iLookAhead);
			while (iLookAhead != iParser.iEnvironment.iProgClose.string())
			{
				readExpression(InfixPrinter.KMaxPrecedence);  // least precedence
				nrargs++;

				if (iLookAhead == iParser.iEnvironment.iEndStatement.string())
				{
					matchToken(iLookAhead);
				}
				else
				{
					LispError.raiseError("Expecting ; end of statement in program block, but got "+iLookAhead+" instead");
					return;
				}
			}
			matchToken(iLookAhead);
			String theOperator = iParser.iEnvironment.iProg.string();
			insertAtom(theOperator);

			combine(nrargs);
		}
		// Else we have an atom.
		else
		{
			String theOperator = iLookAhead;
			matchToken(iLookAhead);

			int nrargs=-1;
			if (iLookAhead == iParser.iEnvironment.iBracketOpen.string())
			{
				nrargs=0;
				matchToken(iLookAhead);
				while (iLookAhead != iParser.iEnvironment.iBracketClose.string())
				{
					readExpression(InfixPrinter.KMaxPrecedence);  // least precedence
					nrargs++;

					if (iLookAhead == iParser.iEnvironment.iComma.string())
					{
						matchToken(iLookAhead);
					}
					else if (iLookAhead != iParser.iEnvironment.iBracketClose.string())
					{
						LispError.raiseError("Expecting ) closing bracket for sub-expression, but got "+iLookAhead+" instead");
						return;
					}
				}
				matchToken(iLookAhead);

				op = (InfixOperator)iParser.iBodiedOperators.lookUp(theOperator);
				if (op != null)
				{
					readExpression(op.iPrecedence); // InfixPrinter.KMaxPrecedence
					nrargs++;
				}
			}
			insertAtom(theOperator);
			if (nrargs>=0)
				combine(nrargs);

		}

		// parse postfix operators

		while ((op = (InfixOperator)iParser.iPostfixOperators.lookUp(iLookAhead)) != null)
		{
			insertAtom(iLookAhead);
			matchToken(iLookAhead);
			combine(1);
		}
	}
	
	void getOtherSide(int aNrArgsToCombine, int depth) throws Exception
	{
		String theOperator = iLookAhead;
		matchToken(iLookAhead);
		readExpression(depth);
		insertAtom(theOperator);
		combine(aNrArgsToCombine);
	}
	
	void combine(int aNrArgsToCombine) throws Exception
	{
		ConsPointer subList = new ConsPointer();
		subList.set(SubList.getInstance(iResult.get()));
		ConsTraverser iter = new ConsTraverser(iResult);
		int i;
		for (i=0;i<aNrArgsToCombine;i++)
		{
			if (iter.getCons() == null)
			{
				fail();
				return;
			}
			iter.goNext();
		}
		if (iter.getCons() == null)
		{
			fail();
			return;
		}
		subList.get().cdr().set(iter.getCons().cdr().get());
		iter.getCons().cdr().set(null);

		Utility.internalReverseList(subList.get().subList().get().cdr(),
		                                 subList.get().subList().get().cdr());
		iResult.set(subList.get());
	}
	
	void insertAtom(String aString) throws Exception
	{
		ConsPointer ptr = new ConsPointer();
		ptr.set(Atom.getInstance(iParser.iEnvironment,aString));
		ptr.get().cdr().set(iResult.get());
		iResult.set(ptr.get());
	}
	
	void fail()  throws Exception // called when parsing fails, raising an exception
	{
		iError = true;
		if (iLookAhead != null)
		{
			LispError.raiseError("Error parsing expression, near token "+iLookAhead);
		}
		LispError.raiseError("Error parsing expression");
	}

};
