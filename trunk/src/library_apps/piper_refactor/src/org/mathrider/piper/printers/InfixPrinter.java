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

package org.mathrider.piper.printers;

import org.mathrider.piper.lisp.Output;
import org.mathrider.piper.lisp.Standard;
import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.Error;
import org.mathrider.piper.lisp.Iterator;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.Tokenizer;
import org.mathrider.piper.lisp.InfixOperator;
import org.mathrider.piper.lisp.Operators;
import org.mathrider.piper.lisp.Printer;


public class InfixPrinter extends Printer
{

	public static int KMaxPrecedence = 60000;

	Operators iPrefixOperators;
	Operators iInfixOperators;
	Operators iPostfixOperators;
	Operators iBodiedOperators;
	char iPrevLastChar;
	Environment iCurrentEnvironment;

	public InfixPrinter(Operators aPrefixOperators,
	                    Operators aInfixOperators,
	                    Operators aPostfixOperators,
	                    Operators aBodiedOperators)
	{
		iPrefixOperators = aPrefixOperators;
		iInfixOperators = aInfixOperators;
		iPostfixOperators = aPostfixOperators;
		iBodiedOperators = aBodiedOperators;
		iPrevLastChar = 0;
	}
	public void Print(Pointer aExpression, Output aOutput, Environment aEnvironment) throws Exception
	{
		iCurrentEnvironment = aEnvironment;
		Print(aExpression, aOutput, KMaxPrecedence);
	}
	public void RememberLastChar(char aChar)
	{
		iPrevLastChar = aChar;
	}
	void Print(Pointer aExpression, Output aOutput, int iPrecedence) throws Exception
	{
		Error.LISPASSERT(aExpression.get() != null);

		String string = aExpression.get().string();
		if (string != null)
		{
			boolean bracket=false;
			if (iPrecedence<KMaxPrecedence &&
			                string.charAt(0) == '-' &&
			                (Tokenizer.isDigit(string.charAt(1)) || string.charAt(1) == '.')
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

		Pointer subList = aExpression.get().subList();
		Error.Check(subList!=null, Error.KLispErrUnprintableToken);
		if (subList.get() == null)
		{
			WriteToken(aOutput,"( )");
		}
		else
		{
			int length = Standard.internalListLength(subList);
			string = subList.get().string();
			InfixOperator prefix  = (InfixOperator)iPrefixOperators.lookUp(string);
			InfixOperator infix   = (InfixOperator)iInfixOperators.lookUp(string);
			InfixOperator postfix = (InfixOperator)iPostfixOperators.lookUp(string);
			InfixOperator bodied  = (InfixOperator)iBodiedOperators.lookUp(string);
			InfixOperator op = null;

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
				Pointer left  = null;
				Pointer right = null;

				if (prefix != null)
				{
					right = subList.get().cdr();
				}
				else if (infix != null)
				{
					left  = subList.get().cdr();
					right = subList.get().cdr().get().cdr();
				}
				else if (postfix != null)
				{
					left = subList.get().cdr();
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
				Iterator iter = new Iterator(subList.get().cdr());
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

					Iterator counter = new Iterator(iter.Ptr());
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
	void WriteToken(Output aOutput,String aString) throws Exception
	{
		if (Tokenizer.isAlNum(iPrevLastChar) && (Tokenizer.isAlNum(aString.charAt(0)) || aString.charAt(0)=='_'))
		{
			aOutput.Write(" ");
		}
		else if (Tokenizer.isSymbolic(iPrevLastChar) && Tokenizer.isSymbolic(aString.charAt(0)))
		{
			aOutput.Write(" ");
		}
		aOutput.Write(aString);
		RememberLastChar(aString.charAt(aString.length()-1));
	}

}
