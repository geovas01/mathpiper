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

import org.mathrider.piper.io.InputStream;
import org.mathrider.piper.exceptions.PiperException;
import org.mathrider.piper.io.InputStatus;
import org.mathrider.piper.builtin.BigNumber;
import org.mathrider.piper.builtin.BuiltinFunction;
import org.mathrider.piper.io.InputDirectories;
import org.mathrider.piper.lisp.behaviours.SubstBase;
import org.mathrider.piper.lisp.parsers.Tokenizer;
import org.mathrider.piper.lisp.userfunctions.MultipleArityUserFunction;
import org.mathrider.piper.printers.InfixPrinter;
import org.mathrider.piper.lisp.parsers.InfixParser;
import org.mathrider.piper.io.JarFileInputStream;
import org.mathrider.piper.io.StandardFileInputStream;
import org.mathrider.piper.io.StringOutputStream;


public class UtilityFunctions
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
		while (iter.getCons() != null)
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

		while (iter.getCons() != null)
		{
			ConsPointer next = new ConsPointer();
			aEnvironment.iEvaluator.evaluate(aEnvironment, next, iter.ptr());
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
		aEnvironment.iEvaluator.evaluate(aEnvironment, aResult, body);
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
			aEnvironment.iEvaluator.evaluate(aEnvironment, aResult, body);
		}
		catch (PiperException e) { throw e; }
		finally { aEnvironment.popLocalFrame(); }

	}

	public static void internalTrue(Environment aEnvironment, ConsPointer aResult) throws Exception
	{
		aResult.set(aEnvironment.iTrueAtom.copy(false));
	}

	public static void internalFalse(Environment aEnvironment, ConsPointer aResult) throws Exception
	{
		aResult.set(aEnvironment.iFalseAtom.copy(false));
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
			LispError.check(iter.getCons() != null,LispError.KLispErrInvalidArg);
			iter.goNext();
			n--;
		}
		LispError.check(iter.getCons() != null,LispError.KLispErrInvalidArg);
		aResult.set(iter.getCons().copy(false));
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
		return aExpression.get().string() == aEnvironment.iTrueAtom.string();
	}

	public static boolean isFalse(Environment aEnvironment, ConsPointer aExpression) throws Exception
	{
		LispError.lispAssert(aExpression.get() != null);
		return aExpression.get().string() == aEnvironment.iFalseAtom.string();
	}

	public static String symbolName(Environment aEnvironment, String aSymbol)
	{
		if (aSymbol.charAt(0) == '\"')
		{
			return aEnvironment.getTokenHash().lookUpUnStringify(aSymbol);
		}
		else
		{
			return aEnvironment.getTokenHash().lookUp(aSymbol);
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

		while (orig.getCons() != null)
		{
			res.ptr().set(orig.getCons().copy(false));
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

			while (iter1.getCons() != null && iter2.getCons() != null)
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
			if (iter1.getCons() != iter2.getCons())
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




	public static void doInternalLoad(Environment aEnvironment,InputStream aInput) throws Exception
	{
		InputStream previous = aEnvironment.iCurrentInput;
		try
		{
			aEnvironment.iCurrentInput = aInput;
			// TODO make "EndOfFile" a global thing
			// read-parse-evaluate to the end of file
			String eof = aEnvironment.getTokenHash().lookUp("EndOfFile");
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
					aEnvironment.iEvaluator.evaluate(aEnvironment, result, readIn);
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

		String hashedname = aEnvironment.getTokenHash().lookUp(oper);

		InputStatus oldstatus = new InputStatus(aEnvironment.iInputStatus);
		aEnvironment.iInputStatus.setTo(hashedname);
		try
		{
			// Open file
			InputStream newInput = // new StandardFileInputStream(hashedname, aEnvironment.iInputStatus);
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
		StringOutputStream newOutput = new StringOutputStream(result);
		InfixPrinter infixprinter = new InfixPrinter(aEnvironment.iPrefixOperators,
		                            aEnvironment.iInfixOperators,
		                            aEnvironment.iPostfixOperators,
		                            aEnvironment.iBodiedOperators);
		infixprinter.print(aExpression, newOutput, aEnvironment);
		if (aMaxChars > 0 && result.length()>aMaxChars)
		{
			result.delete(aMaxChars,result.length());
			result.append((char)'.');
			result.append((char)'.');
			result.append((char)'.');
		}
		return result.toString();
	}


	public static InputStream openInputFile(String aFileName, InputStatus aInputStatus) throws Exception
	{
		try
		{
			if (zipFile != null)
			{
				java.util.zip.ZipEntry e = zipFile.getEntry(aFileName);
				if (e != null)
				{
					java.io.InputStream s = zipFile.getInputStream(e);
					return new StandardFileInputStream(s, aInputStatus);
				}
			}

			if (aFileName.substring(0,4).equals("jar:"))
			{
				return new JarFileInputStream(aFileName, aInputStatus);
			}
			else
			{
				return new StandardFileInputStream(aFileName, aInputStatus);
			}
		}
		catch (Exception e)
		{
		}
		return null;
	}

	public static InputStream openInputFile(Environment aEnvironment,
	                                      InputDirectories aInputDirectories, String aFileName,
	                                      InputStatus aInputStatus) throws Exception
	{
		String othername = aFileName;
		int i = 0;
		InputStream f = openInputFile(othername, aInputStatus);
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
		InputStream f = openInputFile(othername, inputStatus);
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


	private static void doLoadDefFile(Environment aEnvironment, InputStream aInput,
	                                  DefFile def) throws Exception
	{
		InputStream previous = aEnvironment.iCurrentInput;
		try
		{
			aEnvironment.iCurrentInput = aInput;
			String eof = aEnvironment.getTokenHash().lookUp("EndOfFile");
			String end = aEnvironment.getTokenHash().lookUp("}");
			boolean endoffile = false;

			Tokenizer tok = new Tokenizer();

			while (!endoffile)
			{
				// Read expression
				String token = tok.nextToken(aEnvironment.iCurrentInput, aEnvironment.getTokenHash());

				// check for end of file
				if (token == eof || token == end)
				{
					endoffile = true;
				}
				// Else evaluate
				else
				{
					String str = token;
					MultipleArityUserFunction multiUser = aEnvironment.multiUserFunction(str);
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

		String hashedname = aEnvironment.getTokenHash().lookUp(flatfile);

		InputStatus oldstatus = aEnvironment.iInputStatus;
		aEnvironment.iInputStatus.setTo(hashedname);

		{
			InputStream newInput = // new StandardFileInputStream(hashedname, aEnvironment.iInputStatus);
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
        
        
        
        //************************* The following methods were taken from the Functions class.
        
            /**
     * Construct a {@link BigNumber}.
     * @param aEnvironment the current {@link Environment}.
     * @param aStackTop points to the the top of the argument stack.
     * @param aArgNr the index of the argument to be converted.
     * @return a BigNumber.
     * @throws java.lang.Exception
     */
    public static BigNumber getNumber(Environment aEnvironment, int aStackTop, int aArgNr) throws Exception
    {
        BigNumber x = BuiltinFunction.argumentPointer(aEnvironment, aStackTop, aArgNr).get().number(aEnvironment.precision());
        LispError.checkArgumentCore(aEnvironment, aStackTop, x != null, aArgNr);
        return x;
    }

    public static void multiFix(Environment aEnvironment, int aStackTop, Operators aOps) throws Exception
    {
        // Get operator
        LispError.checkArgumentCore(aEnvironment, aStackTop, BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 1).get() != null, 1);
        String orig = BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 1).get().string();
        LispError.checkArgumentCore(aEnvironment, aStackTop, orig != null, 1);

        ConsPointer precedence = new ConsPointer();
        aEnvironment.iEvaluator.evaluate(aEnvironment, precedence, BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 2));
        LispError.checkArgumentCore(aEnvironment, aStackTop, precedence.get().string() != null, 2);
        int prec = Integer.parseInt(precedence.get().string(), 10);
        LispError.checkArgumentCore(aEnvironment, aStackTop, prec <= InfixPrinter.KMaxPrecedence, 2);
        aOps.SetOperator(prec, UtilityFunctions.symbolName(aEnvironment, orig));
        UtilityFunctions.internalTrue(aEnvironment, BuiltinFunction.result(aEnvironment, aStackTop));
    }

    public static void singleFix(int aPrecedence, Environment aEnvironment, int aStackTop, Operators aOps) throws Exception
    {
        // Get operator
        LispError.checkArgumentCore(aEnvironment, aStackTop, BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 1).get() != null, 1);
        String orig = BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 1).get().string();
        LispError.checkArgumentCore(aEnvironment, aStackTop, orig != null, 1);
        aOps.SetOperator(aPrecedence, UtilityFunctions.symbolName(aEnvironment, orig));
        UtilityFunctions.internalTrue(aEnvironment, BuiltinFunction.result(aEnvironment, aStackTop));
    }

    public static InfixOperator operatorInfo(Environment aEnvironment, int aStackTop, Operators aOperators) throws Exception
    {
        // Get operator
        LispError.checkArgumentCore(aEnvironment, aStackTop, BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 1).get() != null, 1);

        ConsPointer evaluated = new ConsPointer();
        evaluated.set(BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 1).get());

        String orig = evaluated.get().string();
        LispError.checkArgumentCore(aEnvironment, aStackTop, orig != null, 1);
        //
        InfixOperator op = (InfixOperator) aOperators.lookUp(UtilityFunctions.symbolName(aEnvironment, orig));
        return op;
    }
        
      /**
     * Sets a variable in the current {@link Environment}.
     * @param aEnvironment holds the execution environment of the program.
     * @param aStackTop 
     * @param aMacroMode boolean which determines whether the first argument should be evaluated.
     * @param aGlobalLazyVariable
     * @throws java.lang.Exception
     */
    public static void internalSetVar(Environment aEnvironment, int aStackTop, boolean aMacroMode, boolean aGlobalLazyVariable) throws Exception
    {
        String variableString = null;
        if (aMacroMode)
        {
            ConsPointer result = new ConsPointer();
            aEnvironment.iEvaluator.evaluate(aEnvironment, result, BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 1));
            variableString = result.get().string();
        } else
        {
            variableString = BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 1).get().string();
        }
        LispError.checkArgumentCore(aEnvironment, aStackTop, variableString != null, 1);
        LispError.checkArgumentCore(aEnvironment, aStackTop, !UtilityFunctions.isNumber(variableString, true), 1);

        ConsPointer result = new ConsPointer();
        aEnvironment.iEvaluator.evaluate(aEnvironment, result, BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 2));
        aEnvironment.setVariable(variableString, result, aGlobalLazyVariable); //Variable setting is deligated to Environment.
        UtilityFunctions.internalTrue(aEnvironment, BuiltinFunction.result(aEnvironment, aStackTop));
    }

    public static void internalDelete(Environment aEnvironment, int aStackTop, boolean aDestructive) throws Exception
    {
        ConsPointer evaluated = new ConsPointer();
        evaluated.set(BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 1).get());
        LispError.checkIsListCore(aEnvironment, aStackTop, evaluated, 1);

        ConsPointer copied = new ConsPointer();
        if (aDestructive)
        {
            copied.set(evaluated.get().subList().get());
        } else
        {
            UtilityFunctions.internalFlatCopy(copied, evaluated.get().subList());
        }

        ConsPointer index = new ConsPointer();
        index.set(BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 2).get());
        LispError.checkArgumentCore(aEnvironment, aStackTop, index.get() != null, 2);
        LispError.checkArgumentCore(aEnvironment, aStackTop, index.get().string() != null, 2);
        int ind = Integer.parseInt(index.get().string(), 10);
        LispError.checkArgumentCore(aEnvironment, aStackTop, ind > 0, 2);

        ConsTraverser iter = new ConsTraverser(copied);
        while (ind > 0)
        {
            iter.goNext();
            ind--;
        }
        LispError.checkCore(aEnvironment, aStackTop, iter.getCons() != null, LispError.KLispErrListNotLongEnough);
        ConsPointer next = new ConsPointer();
        next.set(iter.getCons().cdr().get());
        iter.ptr().set(next.get());
        BuiltinFunction.result(aEnvironment, aStackTop).set(SubList.getInstance(copied.get()));
    }

    public static void internalInsert(Environment aEnvironment, int aStackTop, boolean aDestructive) throws Exception
    {
        ConsPointer evaluated = new ConsPointer();
        evaluated.set(BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 1).get());
        LispError.checkIsListCore(aEnvironment, aStackTop, evaluated, 1);

        ConsPointer copied = new ConsPointer();
        if (aDestructive)
        {
            copied.set(evaluated.get().subList().get());
        } else
        {
            UtilityFunctions.internalFlatCopy(copied, evaluated.get().subList());
        }

        ConsPointer index = new ConsPointer();
        index.set(BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 2).get());
        LispError.checkArgumentCore(aEnvironment, aStackTop, index.get() != null, 2);
        LispError.checkArgumentCore(aEnvironment, aStackTop, index.get().string() != null, 2);
        int ind = Integer.parseInt(index.get().string(), 10);
        LispError.checkArgumentCore(aEnvironment, aStackTop, ind > 0, 2);

        ConsTraverser iter = new ConsTraverser(copied);
        while (ind > 0)
        {
            iter.goNext();
            ind--;
        }

        ConsPointer toInsert = new ConsPointer();
        toInsert.set(BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 3).get());
        toInsert.get().cdr().set(iter.getCons());
        iter.ptr().set(toInsert.get());
        BuiltinFunction.result(aEnvironment, aStackTop).set(SubList.getInstance(copied.get()));
    }

    public static void internalReplace(Environment aEnvironment, int aStackTop, boolean aDestructive) throws Exception
    {
        ConsPointer evaluated = new ConsPointer();
        evaluated.set(BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 1).get());
        // Ok, so lets not check if it is a list, but it needs to be at least a 'function'
        LispError.checkArgumentCore(aEnvironment, aStackTop, evaluated.get().subList() != null, 1);

        ConsPointer index = new ConsPointer();
        index.set(BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 2).get());
        LispError.checkArgumentCore(aEnvironment, aStackTop, index.get() != null, 2);
        LispError.checkArgumentCore(aEnvironment, aStackTop, index.get().string() != null, 2);
        int ind = Integer.parseInt(index.get().string(), 10);

        ConsPointer copied = new ConsPointer();
        if (aDestructive)
        {
            copied.set(evaluated.get().subList().get());
        } else
        {
            UtilityFunctions.internalFlatCopy(copied, evaluated.get().subList());
        }
        LispError.checkArgumentCore(aEnvironment, aStackTop, ind > 0, 2);

        ConsTraverser iter = new ConsTraverser(copied);
        while (ind > 0)
        {
            iter.goNext();
            ind--;
        }

        ConsPointer toInsert = new ConsPointer();
        toInsert.set(BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 3).get());
        LispError.checkArgumentCore(aEnvironment, aStackTop, iter.ptr() != null, 2);
        LispError.checkArgumentCore(aEnvironment, aStackTop, iter.ptr().get() != null, 2);
        toInsert.get().cdr().set(iter.ptr().get().cdr().get());
        iter.ptr().set(toInsert.get());
        BuiltinFunction.result(aEnvironment, aStackTop).set(SubList.getInstance(copied.get()));
    }


    /// Implements the Piper functions \c RuleBase and \c MacroRuleBase .
    /// The real work is done by Environment::DeclareRuleBase().
    public static void internalRuleBase(Environment aEnvironment, int aStackTop, boolean aListed) throws Exception
    {
        //TESTARGS(3);

        // Get operator
        ConsPointer args = new ConsPointer();
        String orig = null;

        LispError.checkArgumentCore(aEnvironment, aStackTop, BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 1).get() != null, 1);
        orig = BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 1).get().string();
        LispError.checkArgumentCore(aEnvironment, aStackTop, orig != null, 1);
        args.set(BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 2).get());

        // The arguments
        LispError.checkIsListCore(aEnvironment, aStackTop, args, 2);

        // Finally define the rule base
        aEnvironment.declareRuleBase(UtilityFunctions.symbolName(aEnvironment, orig),
                args.get().subList().get().cdr(), aListed);

        // Return true
        UtilityFunctions.internalTrue(aEnvironment, BuiltinFunction.result(aEnvironment, aStackTop));
    }

    public static void internalNewRule(Environment aEnvironment, int aStackTop) throws Exception
    {
        //TESTARGS(6);

        int arity;
        int precedence;

        ConsPointer ar = new ConsPointer();
        ConsPointer pr = new ConsPointer();
        ConsPointer predicate = new ConsPointer();
        ConsPointer body = new ConsPointer();
        String orig = null;

        // Get operator
        LispError.checkArgumentCore(aEnvironment, aStackTop, BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 1).get() != null, 1);
        orig = BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 1).get().string();
        LispError.checkArgumentCore(aEnvironment, aStackTop, orig != null, 1);
        ar.set(BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 2).get());
        pr.set(BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 3).get());
        predicate.set(BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 4).get());
        body.set(BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 5).get());

        // The arity
        LispError.checkArgumentCore(aEnvironment, aStackTop, ar.get() != null, 2);
        LispError.checkArgumentCore(aEnvironment, aStackTop, ar.get().string() != null, 2);
        arity = Integer.parseInt(ar.get().string(), 10);

        // The precedence
        LispError.checkArgumentCore(aEnvironment, aStackTop, pr.get() != null, 3);
        LispError.checkArgumentCore(aEnvironment, aStackTop, pr.get().string() != null, 3);
        precedence = Integer.parseInt(pr.get().string(), 10);

        // Finally define the rule base
        aEnvironment.defineRule(UtilityFunctions.symbolName(aEnvironment, orig),
                arity,
                precedence,
                predicate,
                body);

        // Return true
        UtilityFunctions.internalTrue(aEnvironment, BuiltinFunction.result(aEnvironment, aStackTop));
    }

    public static void internalDefMacroRuleBase(Environment aEnvironment, int aStackTop, boolean aListed) throws Exception
    {
        // Get operator
        ConsPointer args = new ConsPointer();
        ConsPointer body = new ConsPointer();
        String orig = null;

        LispError.checkArgumentCore(aEnvironment, aStackTop, BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 1).get() != null, 1);
        orig = BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 1).get().string();
        LispError.checkArgumentCore(aEnvironment, aStackTop, orig != null, 1);

        // The arguments
        args.set(BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 2).get());
        LispError.checkIsListCore(aEnvironment, aStackTop, args, 2);

        // Finally define the rule base
        aEnvironment.declareMacroRuleBase(UtilityFunctions.symbolName(aEnvironment, orig),
                args.get().subList().get().cdr(), aListed);

        // Return true
        UtilityFunctions.internalTrue(aEnvironment, BuiltinFunction.result(aEnvironment, aStackTop));
    }

    public static void internalNewRulePattern(Environment aEnvironment, int aStackTop, boolean aMacroMode) throws Exception
    {
        int arity;
        int precedence;

        ConsPointer ar = new ConsPointer();
        ConsPointer pr = new ConsPointer();
        ConsPointer predicate = new ConsPointer();
        ConsPointer body = new ConsPointer();
        String orig = null;

        // Get operator
        LispError.checkArgumentCore(aEnvironment, aStackTop, BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 1).get() != null, 1);
        orig = BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 1).get().string();
        LispError.checkArgumentCore(aEnvironment, aStackTop, orig != null, 1);
        ar.set(BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 2).get());
        pr.set(BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 3).get());
        predicate.set(BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 4).get());
        body.set(BuiltinFunction.argumentPointer(aEnvironment, aStackTop, 5).get());

        // The arity
        LispError.checkArgumentCore(aEnvironment, aStackTop, ar.get() != null, 2);
        LispError.checkArgumentCore(aEnvironment, aStackTop, ar.get().string() != null, 2);
        arity = Integer.parseInt(ar.get().string(), 10);

        // The precedence
        LispError.checkArgumentCore(aEnvironment, aStackTop, pr.get() != null, 3);
        LispError.checkArgumentCore(aEnvironment, aStackTop, pr.get().string() != null, 3);
        precedence = Integer.parseInt(pr.get().string(), 10);

        // Finally define the rule base
        aEnvironment.defineRulePattern(UtilityFunctions.symbolName(aEnvironment, orig),
                arity,
                precedence,
                predicate,
                body);

        // Return true
        UtilityFunctions.internalTrue(aEnvironment, BuiltinFunction.result(aEnvironment, aStackTop));
    }

}


