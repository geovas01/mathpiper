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

package org.mathpiper.printers;

import org.mathpiper.io.OutputStream;
import org.mathpiper.lisp.UtilityFunctions;
import org.mathpiper.lisp.ConsPointer;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.ConsTraverser;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;
import org.mathpiper.lisp.InfixOperator;
import org.mathpiper.lisp.Operators;
import org.mathpiper.lisp.Printer;


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
	public void print(ConsPointer aExpression, OutputStream aOutput, Environment aEnvironment) throws Exception
	{
		iCurrentEnvironment = aEnvironment;
		Print(aExpression, aOutput, KMaxPrecedence);
	}
	public void rememberLastChar(char aChar)
	{
		iPrevLastChar = aChar;
	}
	void Print(ConsPointer aExpression, OutputStream aOutput, int iPrecedence) throws Exception
	{
		LispError.lispAssert(aExpression.get() != null);

		String string = aExpression.get().string();
		if (string != null)
		{
			boolean bracket=false;
			if (iPrecedence<KMaxPrecedence &&
			                string.charAt(0) == '-' &&
			                (MathPiperTokenizer.isDigit(string.charAt(1)) || string.charAt(1) == '.')
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
			WriteToken(aOutput,aExpression.get().generic().typeName());
			return;
		}

		ConsPointer subList = aExpression.get().subList();
		LispError.check(subList!=null, LispError.KLispErrUnprintableToken);
		if (subList.get() == null)
		{
			WriteToken(aOutput,"( )");
		}
		else
		{
			int length = UtilityFunctions.internalListLength(subList);
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
				ConsPointer left  = null;
				ConsPointer right = null;

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
				ConsTraverser iter = new ConsTraverser(subList.get().cdr());
				if (string == iCurrentEnvironment.iListAtom.string())
				{
					WriteToken(aOutput,"{");
					while (iter.getCons() != null)
					{
						Print(iter.ptr(), aOutput, KMaxPrecedence);
						iter.goNext();
						if (iter.getCons() != null)
							WriteToken(aOutput,",");
					}
					WriteToken(aOutput,"}");
				}
				else if (string == iCurrentEnvironment.iProgAtom.string())
				{
					WriteToken(aOutput,"[");
					while (iter.getCons() != null)
					{
						Print(iter.ptr(), aOutput, KMaxPrecedence);
						iter.goNext();
						WriteToken(aOutput,";");
					}
					WriteToken(aOutput,"]");
				}
				else if (string == iCurrentEnvironment.iNthAtom.string())
				{
					Print(iter.ptr(), aOutput, 0);
					iter.goNext();
					WriteToken(aOutput,"[");
					Print(iter.ptr(), aOutput, KMaxPrecedence);
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

					ConsTraverser counter = new ConsTraverser(iter.ptr());
					int nr=0;

					while (counter.getCons() != null)
					{
						counter.goNext();
						nr++;
					}

					if (bodied != null)
						nr--;
					while (nr-- != 0)
					{
						Print(iter.ptr(), aOutput, KMaxPrecedence);

						iter.goNext();
						if (nr != 0)
							WriteToken(aOutput,",");
					}
					WriteToken(aOutput,")");
					if (iter.getCons() != null)
						Print(iter.ptr(), aOutput, bodied.iPrecedence);

					if (bracket) WriteToken(aOutput,")");
				}
			}
		}
	}
	void WriteToken(OutputStream aOutput,String aString) throws Exception
	{
		if (MathPiperTokenizer.isAlNum(iPrevLastChar) && (MathPiperTokenizer.isAlNum(aString.charAt(0)) || aString.charAt(0)=='_'))
		{
			aOutput.Write(" ");
		}
		else if (MathPiperTokenizer.isSymbolic(iPrevLastChar) && MathPiperTokenizer.isSymbolic(aString.charAt(0)))
		{
			aOutput.Write(" ");
		}
		aOutput.Write(aString);
		rememberLastChar(aString.charAt(aString.length()-1));
	}

}
