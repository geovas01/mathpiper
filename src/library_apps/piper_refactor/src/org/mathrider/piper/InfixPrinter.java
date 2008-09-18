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

import org.mathrider.piper.lisp.LispOutput;
import org.mathrider.piper.lisp.LispStandard;
import org.mathrider.piper.lisp.LispPtr;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.LispIterator;
import org.mathrider.piper.lisp.LispEnvironment;
import org.mathrider.piper.lisp.LispTokenizer;
import org.mathrider.piper.lisp.LispInfixOperator;
import org.mathrider.piper.lisp.LispOperators;
import org.mathrider.piper.lisp.LispPrinter;


public class InfixPrinter extends LispPrinter
{

	static int KMaxPrecedence = 60000;

	LispOperators iPrefixOperators;
	LispOperators iInfixOperators;
	LispOperators iPostfixOperators;
	LispOperators iBodiedOperators;
	char iPrevLastChar;
	LispEnvironment iCurrentEnvironment;

	public InfixPrinter(LispOperators aPrefixOperators,
	                    LispOperators aInfixOperators,
	                    LispOperators aPostfixOperators,
	                    LispOperators aBodiedOperators)
	{
		iPrefixOperators = aPrefixOperators;
		iInfixOperators = aInfixOperators;
		iPostfixOperators = aPostfixOperators;
		iBodiedOperators = aBodiedOperators;
		iPrevLastChar = 0;
	}
	public void Print(LispPtr aExpression, LispOutput aOutput, LispEnvironment aEnvironment) throws Exception
	{
		iCurrentEnvironment = aEnvironment;
		Print(aExpression, aOutput, KMaxPrecedence);
	}
	public void RememberLastChar(char aChar)
	{
		iPrevLastChar = aChar;
	}
	void Print(LispPtr aExpression, LispOutput aOutput, int iPrecedence) throws Exception
	{
		LispError.LISPASSERT(aExpression.get() != null);

		String string = aExpression.get().string();
		if (string != null)
		{
			boolean bracket=false;
			if (iPrecedence<KMaxPrecedence &&
			                string.charAt(0) == '-' &&
			                (LispTokenizer.isDigit(string.charAt(1)) || string.charAt(1) == '.')
			   )
			{
				bracket=true;
			}
			if (bracket) WriteToken(aOutput,"(");
			WriteToken(aOutput,string);
			if (bracket) WriteToken(aOutput,")");
			return;
		}

		if (aExpression.get().generic() != null)
		{
			//TODO display genericclass
			WriteToken(aOutput,aExpression.get().generic().TypeName());
			return;
		}

		LispPtr subList = aExpression.get().subList();
		LispError.Check(subList!=null, LispError.KLispErrUnprintableToken);
		if (subList.get() == null)
		{
			WriteToken(aOutput,"( )");
		}
		else
		{
			int length = LispStandard.internalListLength(subList);
			string = subList.get().string();
			LispInfixOperator prefix  = (LispInfixOperator)iPrefixOperators.lookUp(string);
			LispInfixOperator infix   = (LispInfixOperator)iInfixOperators.lookUp(string);
			LispInfixOperator postfix = (LispInfixOperator)iPostfixOperators.lookUp(string);
			LispInfixOperator bodied  = (LispInfixOperator)iBodiedOperators.lookUp(string);
			LispInfixOperator op = null;

			if (length!=2)
			{
				prefix=null;
				postfix=null;
			}
			if (length!=3)
			{
				infix=null;
			}
			if (prefix != null)   op=prefix;
			if (postfix != null)  op=postfix;
			if (infix != null)    op=infix;

			if (op != null)
			{
				LispPtr left  = null;
				LispPtr right = null;

				if (prefix != null)
				{
					right = subList.get().next();
				}
				else if (infix != null)
				{
					left  = subList.get().next();
					right = subList.get().next().get().next();
				}
				else if (postfix != null)
				{
					left = subList.get().next();
				}

				if (iPrecedence < op.iPrecedence)
				{
					WriteToken(aOutput,"(");
				}
				else
				{
					//Vladimir?    aOutput.Write(" ");
				}
				if (left != null)
					Print(left, aOutput,op.iLeftPrecedence);
				WriteToken(aOutput,string);
				if (right != null)
					Print(right, aOutput,op.iRightPrecedence);
				if (iPrecedence < op.iPrecedence)
					WriteToken(aOutput,")");
			}
			else
			{
				LispIterator iter = new LispIterator(subList.get().next());
				if (string == iCurrentEnvironment.iList.string())
				{
					WriteToken(aOutput,"{");
					while (iter.GetObject() != null)
					{
						Print(iter.Ptr(), aOutput, KMaxPrecedence);
						iter.GoNext();
						if (iter.GetObject() != null)
							WriteToken(aOutput,",");
					}
					WriteToken(aOutput,"}");
				}
				else if (string == iCurrentEnvironment.iProg.string())
				{
					WriteToken(aOutput,"[");
					while (iter.GetObject() != null)
					{
						Print(iter.Ptr(), aOutput, KMaxPrecedence);
						iter.GoNext();
						WriteToken(aOutput,";");
					}
					WriteToken(aOutput,"]");
				}
				else if (string == iCurrentEnvironment.iNth.string())
				{
					Print(iter.Ptr(), aOutput, 0);
					iter.GoNext();
					WriteToken(aOutput,"[");
					Print(iter.Ptr(), aOutput, KMaxPrecedence);
					WriteToken(aOutput,"]");
				}
				else
				{
					boolean bracket = false;
					if (bodied != null)
					{
						//printf("%d > %d\n",iPrecedence, bodied.iPrecedence);
						if (iPrecedence < bodied.iPrecedence)
							bracket = true;
					}
					if (bracket) WriteToken(aOutput,"(");
					if (string != null)
					{
						WriteToken(aOutput,string);
					}
					else
					{
						Print(subList,aOutput,0);
					}
					WriteToken(aOutput,"(");

					LispIterator counter = new LispIterator(iter.Ptr());
					int nr=0;

					while (counter.GetObject() != null)
					{
						counter.GoNext();
						nr++;
					}

					if (bodied != null)
						nr--;
					while (nr-- != 0)
					{
						Print(iter.Ptr(), aOutput, KMaxPrecedence);

						iter.GoNext();
						if (nr != 0)
							WriteToken(aOutput,",");
					}
					WriteToken(aOutput,")");
					if (iter.GetObject() != null)
						Print(iter.Ptr(), aOutput, bodied.iPrecedence);

					if (bracket) WriteToken(aOutput,")");
				}
			}
		}
	}
	void WriteToken(LispOutput aOutput,String aString) throws Exception
	{
		if (LispTokenizer.isAlNum(iPrevLastChar) && (LispTokenizer.isAlNum(aString.charAt(0)) || aString.charAt(0)=='_'))
		{
			aOutput.Write(" ");
		}
		else if (LispTokenizer.isSymbolic(iPrevLastChar) && LispTokenizer.isSymbolic(aString.charAt(0)))
		{
			aOutput.Write(" ");
		}
		aOutput.Write(aString);
		RememberLastChar(aString.charAt(aString.length()-1));
	}

}
