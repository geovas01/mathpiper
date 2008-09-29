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

import org.mathrider.piper.exceptions.PiperException;
import org.mathrider.piper.io.InputStatus;
import org.mathrider.piper.builtin.BigNumber;
import org.mathrider.piper.io.InputDirectories;
import org.mathrider.piper.lisp.behaviours.SubstBase;
import org.mathrider.piper.lisp.parsers.Tokenizer;
import org.mathrider.piper.lisp.userfunctions.MultiUserFunction;
import org.mathrider.piper.printers.InfixPrinter;
import org.mathrider.piper.lisp.parsers.InfixParser;
import org.mathrider.piper.io.JarInputFile;
import org.mathrider.piper.io.StdFileInput;
import org.mathrider.piper.io.StringOutput;
import org.mathrider.piper.*;


public class Standard //Note:tk: made this class public so that zipfile could be accessed.
{
	static int log2_table_size = 32;

	// A lookup table of Ln(n)/Ln(2) for n = 1 .. 32.
	// With this we don't have to use math.h if all we need is to convert the number of digits from one base to another. This is also faster.
	// Generated by: PrintList(N(Ln(1 .. 32)/Ln(2)), ",") at precision 40
	static double log2_table[] =
	        {
	                0.,
	                1.,
	                1.5849625007211561814537389439478165087598,
	                2.,
	                2.3219280948873623478703194294893901758648,
	                2.5849625007211561814537389439478165087598,
	                2.807354922057604107441969317231830808641,
	                3.,
	                3.1699250014423123629074778878956330175196,
	                3.3219280948873623478703194294893901758648,
	                3.4594316186372972561993630467257929587032,
	                3.5849625007211561814537389439478165087598,
	                3.7004397181410921603968126542566947336284,
	                3.807354922057604107441969317231830808641,
	                3.9068905956085185293240583734372066846246,
	                4.,
	                4.0874628412503394082540660108104043540112,
	                4.1699250014423123629074778878956330175196,
	                4.2479275134435854937935194229068344226935,
	                4.3219280948873623478703194294893901758648,
	                4.3923174227787602888957082611796473174008,
	                4.4594316186372972561993630467257929587032,
	                4.5235619560570128722941482441626688444988,
	                4.5849625007211561814537389439478165087598,
	                4.6438561897747246957406388589787803517296,
	                4.7004397181410921603968126542566947336284,
	                4.7548875021634685443612168318434495262794,
	                4.807354922057604107441969317231830808641,
	                4.8579809951275721207197733246279847624768,
	                4.9068905956085185293240583734372066846246,
	                4.9541963103868752088061235991755544235489,
	                5.
	        };

	public static java.util.zip.ZipFile zipFile = null;

	public static boolean isNumber(String ptr, boolean aAllowFloat)
	{
		int pos = 0;
		if (ptr.charAt(pos) == '-' || ptr.charAt(pos) == '+')
			pos++;

		int nrDigits=0;
		int index=0;
		if (pos+index == ptr.length())
			return false;
		while(ptr.charAt(pos+index) >= '0' && ptr.charAt(pos+index) <= '9')
		{
			nrDigits++;
			index++;
			if (pos+index == ptr.length())
				return true;
		}
		if (ptr.charAt(pos+index) == '.')
		{
			if (!aAllowFloat)
				return false;
			index++;
			if (pos+index == ptr.length())
				return true;
			while(ptr.charAt(pos+index) >= '0' && ptr.charAt(pos+index) <= '9')
			{
				nrDigits++;
				index++;
				if (pos+index == ptr.length())
					return true;
			}
		}
		if (nrDigits == 0)
			return false;
		if (ptr.charAt(pos+index) == 'e' || ptr.charAt(pos+index) == 'E')
		{
			if (!aAllowFloat)
				return false;
			if (!BigNumber.NumericSupportForMantissa())
				return false;
			index++;
			if (pos+index == ptr.length())
				return true;
			if (ptr.charAt(pos+index) == '-' || ptr.charAt(pos+index) == '+') index++;
			while(ptr.charAt(pos+index) >= '0' && ptr.charAt(pos+index) <= '9')
			{
				index++;
				if (pos+index == ptr.length())
					return true;
			}
		}
		if (ptr.length() != (pos+index)) return false;
		return true;
	}

	public static int internalListLength(ConsPointer aOriginal) throws Exception
	{
		ConsTraverser iter = new ConsTraverser(aOriginal);
		int length = 0;
		while (iter.getObject() != null)
		{
			iter.goNext();
			length++;
		}
		return length;
	}

	public static void internalReverseList(ConsPointer aResult, ConsPointer aOriginal)
	{
		ConsPointer iter = new ConsPointer(aOriginal);
		ConsPointer previous = new ConsPointer();
		ConsPointer tail = new ConsPointer();
		tail.set(aOriginal.get());

		while (iter.get() != null)
		{
			tail.set(iter.get().cdr().get());
			iter.get().cdr().set(previous.get());
			previous.set(iter.get());
			iter.set(tail.get());
		}
		aResult.set(previous.get());
	}

	public static void returnUnEvaluated(ConsPointer aResult,ConsPointer aArguments, Environment aEnvironment) throws Exception
	{
		ConsPointer full = new ConsPointer();
		full.set(aArguments.get().copy(false));
		aResult.set(SubList.getInstance(full.get()));

		ConsTraverser iter = new ConsTraverser(aArguments);
		iter.goNext();

		while (iter.getObject() != null)
		{
			ConsPointer next = new ConsPointer();
			aEnvironment.iEvaluator.eval(aEnvironment, next, iter.ptr());
			full.get().cdr().set(next.get());
			full.set(next.get());
			iter.goNext();
		}
		full.get().cdr().set(null);
	}




	public static void internalApplyString(Environment aEnvironment, ConsPointer aResult,
	                                       String aOperator,ConsPointer aArgs) throws Exception
	{
		LispError.check(internalIsString(aOperator),LispError.KLispErrNotString);

		Cons head =
		        Atom.getInstance(aEnvironment,symbolName(aEnvironment, aOperator));
		head.cdr().set(aArgs.get());
		ConsPointer body = new ConsPointer();
		body.set(SubList.getInstance(head));
		aEnvironment.iEvaluator.eval(aEnvironment, aResult, body);
	}

	public static void internalApplyPure(ConsPointer oper,ConsPointer args2,ConsPointer aResult, Environment aEnvironment) throws Exception
	{
		LispError.check(oper.get().subList() != null,LispError.KLispErrInvalidArg);
		LispError.check(oper.get().subList().get() != null,LispError.KLispErrInvalidArg);
		ConsPointer oper2 = new ConsPointer();
		oper2.set(oper.get().subList().get().cdr().get());
		LispError.check(oper2.get() != null,LispError.KLispErrInvalidArg);

		ConsPointer body = new ConsPointer();
		body.set(oper2.get().cdr().get());
		LispError.check(body.get() != null,LispError.KLispErrInvalidArg);

		LispError.check(oper2.get().subList() != null,LispError.KLispErrInvalidArg);
		LispError.check(oper2.get().subList().get() != null,LispError.KLispErrInvalidArg);
		oper2.set(oper2.get().subList().get().cdr().get());

		aEnvironment.pushLocalFrame(false);
		try
		{
			while (oper2.get() != null)
			{
				LispError.check(args2.get() != null,LispError.KLispErrInvalidArg);

				String var = oper2.get().string();
				LispError.check(var != null,LispError.KLispErrInvalidArg);
				ConsPointer newly = new ConsPointer();
				newly.set(args2.get().copy(false));
				aEnvironment.newLocal(var,newly.get());
				oper2.set(oper2.get().cdr().get());
				args2.set(args2.get().cdr().get());
			}
			LispError.check(args2.get() == null,LispError.KLispErrInvalidArg);
			aEnvironment.iEvaluator.eval(aEnvironment, aResult, body);
		}
		catch (PiperException e) { throw e; }
		finally { aEnvironment.popLocalFrame(); }

	}

	public static void internalTrue(Environment aEnvironment, ConsPointer aResult) throws Exception
	{
		aResult.set(aEnvironment.iTrue.copy(false));
	}

	public static void internalFalse(Environment aEnvironment, ConsPointer aResult) throws Exception
	{
		aResult.set(aEnvironment.iFalse.copy(false));
	}

	public static void internalBoolean(Environment aEnvironment, ConsPointer aResult, boolean aValue) throws Exception
	{
		if (aValue)
		{
			internalTrue(aEnvironment, aResult);
		}
		else
		{
			internalFalse(aEnvironment, aResult);
		}
	}

	public static void internalNth(ConsPointer aResult, ConsPointer aArg, int n) throws Exception
	{
		LispError.check(aArg.get() != null,LispError.KLispErrInvalidArg);
		LispError.check(aArg.get().subList() != null,LispError.KLispErrInvalidArg);
		LispError.check(n>=0,LispError.KLispErrInvalidArg);
		ConsTraverser iter = new ConsTraverser(aArg.get().subList());

		while (n>0)
		{
			LispError.check(iter.getObject() != null,LispError.KLispErrInvalidArg);
			iter.goNext();
			n--;
		}
		LispError.check(iter.getObject() != null,LispError.KLispErrInvalidArg);
		aResult.set(iter.getObject().copy(false));
	}

	public static void internalTail(ConsPointer aResult, ConsPointer aArg) throws Exception
	{
		LispError.check(aArg.get() != null,LispError.KLispErrInvalidArg);
		LispError.check(aArg.get().subList() != null,LispError.KLispErrInvalidArg);

		ConsPointer iter = aArg.get().subList();

		LispError.check(iter.get() != null,LispError.KLispErrInvalidArg);
		aResult.set(SubList.getInstance(iter.get().cdr().get()));
	}

	public static boolean isTrue(Environment aEnvironment, ConsPointer aExpression) throws Exception
	{
		LispError.lispAssert(aExpression.get() != null);
		return aExpression.get().string() == aEnvironment.iTrue.string();
	}

	public static boolean isFalse(Environment aEnvironment, ConsPointer aExpression) throws Exception
	{
		LispError.lispAssert(aExpression.get() != null);
		return aExpression.get().string() == aEnvironment.iFalse.string();
	}

	public static String symbolName(Environment aEnvironment, String aSymbol)
	{
		if (aSymbol.charAt(0) == '\"')
		{
			return aEnvironment.hashTable().lookUpUnStringify(aSymbol);
		}
		else
		{
			return aEnvironment.hashTable().lookUp(aSymbol);
		}
	}

	public static boolean internalIsList(ConsPointer aPtr) throws Exception
	{
		if (aPtr.get() == null)
			return false;
		if (aPtr.get().subList() == null)
			return false;
		if (aPtr.get().subList().get() == null)
			return false;
		//TODO this StrEqual is far from perfect. We could pass in a Environment object...
		if (!aPtr.get().subList().get().string().equals("List"))
			return false;
		return true;
	}

	public static boolean internalIsString(String aOriginal)
	{
		if (aOriginal != null)
			if (aOriginal.charAt(0) == '\"')
				if (aOriginal.charAt(aOriginal.length()-1) == '\"')
					return true;
		return false;
	}

	public static void internalNot(ConsPointer aResult, Environment aEnvironment, ConsPointer aExpression) throws Exception
	{
		if (isTrue(aEnvironment, aExpression))
		{
			internalFalse(aEnvironment,aResult);
		}
		else
		{
			LispError.check(isFalse(aEnvironment, aExpression),LispError.KLispErrInvalidArg);
			internalTrue(aEnvironment,aResult);
		}
	}

	public static void internalFlatCopy(ConsPointer aResult, ConsPointer aOriginal) throws Exception
	{
		ConsTraverser orig = new ConsTraverser(aOriginal);
		ConsTraverser res = new ConsTraverser(aResult);

		while (orig.getObject() != null)
		{
			res.ptr().set(orig.getObject().copy(false));
			orig.goNext();
			res.goNext();
		}
	}


	public static boolean internalEquals(Environment aEnvironment, ConsPointer aExpression1, ConsPointer aExpression2) throws Exception
	{
		// Handle pointers to same, or null
		if (aExpression1.get() == aExpression2.get())
		{
			return true;
		}

		BigNumber n1 = aExpression1.get().number(aEnvironment.precision());
		BigNumber n2 = aExpression2.get().number(aEnvironment.precision());
		if (!(n1 == null && n2 == null) )
		{
			if (n1 == n2)
			{
				return true;
			}
			if (n1 == null) return false;
			if (n2 == null) return false;
			if (n1.Equals(n2)) return true;
			return false;
		}

		//Pointers to strings should be the same
		if (aExpression1.get().string() != aExpression2.get().string())
		{
			return false;
		}

		// Handle same sublists, or null
		if (aExpression1.get().subList() == aExpression2.get().subList())
		{
			return true;
		}

		// Now check the sublists
		if (aExpression1.get().subList() != null)
		{
			if (aExpression2.get().subList() == null)
			{
				return false;
			}
			ConsTraverser iter1 = new ConsTraverser(aExpression1.get().subList());
			ConsTraverser iter2 = new ConsTraverser(aExpression2.get().subList());

			while (iter1.getObject() != null && iter2.getObject() != null)
			{
				// compare two list elements
				if (!internalEquals(aEnvironment, iter1.ptr(),iter2.ptr()))
				{
					return false;
				}

				// Step to cdr
				iter1.goNext();
				iter2.goNext();
			}
			// Lists don't have the same length
			if (iter1.getObject() != iter2.getObject())
				return false;

			// Same!
			return true;
		}

		// expressions sublists are not the same!
		return false;
	}


	public static void internalSubstitute(ConsPointer aTarget, ConsPointer aSource, SubstBase aBehaviour) throws Exception
	{
		Cons object = aSource.get();
		LispError.lispAssert(object != null);
		if (!aBehaviour.matches(aTarget,aSource))
		{
			ConsPointer oldList = object.subList();
			if (oldList != null)
			{
				ConsPointer newList = new ConsPointer();
				ConsPointer next = newList;
				while (oldList.get() != null)
				{
					internalSubstitute(next, oldList, aBehaviour);
					oldList = oldList.get().cdr();
					next = next.get().cdr();
				}
				aTarget.set(SubList.getInstance(newList.get()));
			}
			else
			{
				aTarget.set(object.copy(false));
			}
		}
	}

	public static String internalUnstringify(String aOriginal) throws Exception
	{
		LispError.check(aOriginal != null,LispError.KLispErrInvalidArg);
		LispError.check(aOriginal.charAt(0) == '\"',LispError.KLispErrInvalidArg);
		int nrc=aOriginal.length()-1;
		LispError.check(aOriginal.charAt(nrc) == '\"',LispError.KLispErrInvalidArg);
		return aOriginal.substring(1,nrc);
	}




	public static void doInternalLoad(Environment aEnvironment,Input aInput) throws Exception
	{
		Input previous = aEnvironment.iCurrentInput;
		try
		{
			aEnvironment.iCurrentInput = aInput;
			// TODO make "EndOfFile" a global thing
			// read-parse-eval to the end of file
			String eof = aEnvironment.hashTable().lookUp("EndOfFile");
			boolean endoffile = false;
			InfixParser parser = new InfixParser(new Tokenizer(),
			                                     aEnvironment.iCurrentInput, aEnvironment,
			                                     aEnvironment.iPrefixOperators, aEnvironment.iInfixOperators,
			                                     aEnvironment.iPostfixOperators, aEnvironment.iBodiedOperators);
			ConsPointer readIn =new ConsPointer();
			while (!endoffile)
			{
				// Read expression
				parser.parse(readIn);

				LispError.check(readIn.get() != null, LispError.KLispErrReadingFile);
				// check for end of file
				if (readIn.get().string() == eof)
				{
					endoffile = true;
				}
				// Else evaluate
				else
				{
					ConsPointer result = new ConsPointer();
					aEnvironment.iEvaluator.eval(aEnvironment, result, readIn);
				}
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			aEnvironment.iCurrentInput = previous;
		}
	}



	public static void internalLoad(Environment aEnvironment,String aFileName) throws Exception
	{
		String oper = internalUnstringify(aFileName);

		String hashedname = aEnvironment.hashTable().lookUp(oper);

		InputStatus oldstatus = new InputStatus(aEnvironment.iInputStatus);
		aEnvironment.iInputStatus.setTo(hashedname);
		try
		{
			// Open file
			Input newInput = // new StdFileInput(hashedname, aEnvironment.iInputStatus);
			        openInputFile(aEnvironment, aEnvironment.iInputDirectories, hashedname, aEnvironment.iInputStatus);

			LispError.check(newInput != null, LispError.KLispErrFileNotFound);
			doInternalLoad(aEnvironment,newInput);
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			aEnvironment.iInputStatus.restoreFrom(oldstatus);
		}
	}

	public static void internalUse(Environment aEnvironment,String aFileName) throws Exception
	{
		DefFile def = aEnvironment.iDefFiles.File(aFileName);
		if (!def.IsLoaded())
		{
			def.SetLoaded();
			internalLoad(aEnvironment,aFileName);
		}
	}

	public static String printExpression(ConsPointer aExpression,
	                              Environment aEnvironment,
	                              int aMaxChars) throws Exception
	{
		StringBuffer result = new StringBuffer();
		StringOutput newOutput = new StringOutput(result);
		InfixPrinter infixprinter = new InfixPrinter(aEnvironment.iPrefixOperators,
		                            aEnvironment.iInfixOperators,
		                            aEnvironment.iPostfixOperators,
		                            aEnvironment.iBodiedOperators);
		infixprinter.Print(aExpression, newOutput, aEnvironment);
		if (aMaxChars > 0 && result.length()>aMaxChars)
		{
			result.delete(aMaxChars,result.length());
			result.append((char)'.');
			result.append((char)'.');
			result.append((char)'.');
		}
		return result.toString();
	}


	public static Input openInputFile(String aFileName, InputStatus aInputStatus) throws Exception
	{
		try
		{
			if (zipFile != null)
			{
				java.util.zip.ZipEntry e = zipFile.getEntry(aFileName);
				if (e != null)
				{
					java.io.InputStream s = zipFile.getInputStream(e);
					return new StdFileInput(s, aInputStatus);
				}
			}

			if (aFileName.substring(0,4).equals("jar:"))
			{
				return new JarInputFile(aFileName, aInputStatus);
			}
			else
			{
				return new StdFileInput(aFileName, aInputStatus);
			}
		}
		catch (Exception e)
		{
		}
		return null;
	}

	public static Input openInputFile(Environment aEnvironment,
	                                      InputDirectories aInputDirectories, String aFileName,
	                                      InputStatus aInputStatus) throws Exception
	{
		String othername = aFileName;
		int i = 0;
		Input f = openInputFile(othername, aInputStatus);
		while (f == null && i<aInputDirectories.size())
		{
			othername = ((String)aInputDirectories.get(i)) + aFileName;
			f = openInputFile(othername, aInputStatus);
			i++;
		}
		return f;
	}


	public static String internalFindFile(String aFileName, InputDirectories aInputDirectories) throws Exception
	{
		InputStatus inputStatus = new InputStatus();
		String othername = aFileName;
		int i = 0;
		Input f = openInputFile(othername, inputStatus);
		if (f != null) return othername;
		while (i<aInputDirectories.size())
		{
			othername = ((String)aInputDirectories.get(i)) + aFileName;
			f = openInputFile(othername, inputStatus);
			if (f != null) return othername;
			i++;
		}
		return "";
	}


	private static void doLoadDefFile(Environment aEnvironment, Input aInput,
	                                  DefFile def) throws Exception
	{
		Input previous = aEnvironment.iCurrentInput;
		try
		{
			aEnvironment.iCurrentInput = aInput;
			String eof = aEnvironment.hashTable().lookUp("EndOfFile");
			String end = aEnvironment.hashTable().lookUp("}");
			boolean endoffile = false;

			Tokenizer tok = new Tokenizer();

			while (!endoffile)
			{
				// Read expression
				String token = tok.nextToken(aEnvironment.iCurrentInput, aEnvironment.hashTable());

				// check for end of file
				if (token == eof || token == end)
				{
					endoffile = true;
				}
				// Else evaluate
				else
				{
					String str = token;
					MultiUserFunction multiUser = aEnvironment.multiUserFunction(str);
					if (multiUser.iFileToOpen!=null)
					{
						throw new PiperException("["+str+"]"+"] : def file already chosen: "+multiUser.iFileToOpen.iFileName);
					}
					multiUser.iFileToOpen = def;
				}
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			aEnvironment.iCurrentInput = previous;
		}
	}


	public static void loadDefFile(Environment aEnvironment, String aFileName) throws Exception
	{
		LispError.lispAssert(aFileName!=null);

		String flatfile = internalUnstringify(aFileName) + ".def";
		DefFile def = aEnvironment.iDefFiles.File(aFileName);

		String hashedname = aEnvironment.hashTable().lookUp(flatfile);

		InputStatus oldstatus = aEnvironment.iInputStatus;
		aEnvironment.iInputStatus.setTo(hashedname);

		{
			Input newInput = // new StdFileInput(hashedname, aEnvironment.iInputStatus);
			        openInputFile(aEnvironment, aEnvironment.iInputDirectories, hashedname, aEnvironment.iInputStatus);
			LispError.check(newInput != null, LispError.KLispErrFileNotFound);
			doLoadDefFile(aEnvironment, newInput,def);
		}
		aEnvironment.iInputStatus.restoreFrom(oldstatus);
	}



	//////////////////////////////////////////////////
	///// bits_to_digits and digits_to_bits implementation
	//////////////////////////////////////////////////

	// lookup table for transforming the number of digits


	// report the table size
	int log2_table_range()
	{
		return log2_table_size;
	}


	// table look-up of small integer logarithms, for converting the number of digits to binary and back
	static double log2_table_lookup(int n) throws Exception
	{
		if (n<=log2_table_size && n>=2)
			return log2_table[n-1];
		else
		{
			throw new PiperException("log2_table_lookup: error: invalid argument "+n);
		}
	}

	// convert the number of digits in given base to the number of bits, and back.
	// need to round the number of digits.
	// to make sure that there is no hysteresis, we round upwards on digits_to_bits but round down on bits_to_digits
	public static long digits_to_bits(long digits, int base) throws Exception
	{
		return (long)Math.ceil(((double)digits)*log2_table_lookup(base));
	}

	public static long bits_to_digits(long bits, int base) throws Exception
	{
		return (long)Math.floor(((double)bits)/log2_table_lookup(base));
	}

}


