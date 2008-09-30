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

import org.mathrider.piper.builtin.BuiltinFunction;
import org.mathrider.piper.lisp.parsers.XmlTokenizer;
import org.mathrider.piper.io.InputStatus;
import org.mathrider.piper.lisp.UtilityFunctions;;
import org.mathrider.piper.io.InputDirectories;
import org.mathrider.piper.lisp.parsers.Tokenizer;
import org.mathrider.piper.lisp.userfunctions.MultipleArityUserFunction;
import org.mathrider.piper.lisp.userfunctions.MacroUserFunction;
import org.mathrider.piper.lisp.userfunctions.UserFunction;
import org.mathrider.piper.lisp.userfunctions.ListedBranchingUserFunction;
import org.mathrider.piper.lisp.userfunctions.BranchingUserFunction;
import org.mathrider.piper.lisp.userfunctions.ListedMacroUserFunction;
import org.mathrider.piper.printers.InfixPrinter;
import org.mathrider.piper.*;


public class Environment
{
        public ExpressionEvaluator iEvaluator = new LispExpressionEvaluator();
        
	public int iPrecision = 10;

	private TokenHash iTokenHash = new TokenHash();
	public Cons iTrueAtom;
	public Cons iFalseAtom;

	public Cons iEndOfFileAtom;
	public Cons iEndStatementAtom;
	public Cons iProgOpenAtom;
	public Cons iProgCloseAtom;
	public Cons iNthAtom;
	public Cons iBracketOpenAtom;
	public Cons iBracketCloseAtom;
	public Cons iListOpenAtom;
        public Cons iListCloseAtom;
	public Cons iCommaAtom;
	public Cons iListAtom;
	public Cons iProgAtom;

	public Operators iPrefixOperators = new Operators();
	public Operators iInfixOperators = new Operators();
	public Operators iPostfixOperators = new Operators();
	public Operators iBodiedOperators = new Operators();

	public int iEvalDepth = 0;
	public int iMaxEvalDepth = 10000;
	//TODO FIXME

	public ArgumentStack iArgumentStack;

	public LocalVariableFrame iLocalsList;

	public AssociatedHash iGlobalState = new AssociatedHash();

	public boolean iSecure = false;

	public int iLastUniqueId = 1;

	public Output iCurrentOutput = null;
	public Output iInitialOutput = null;

	public Printer iCurrentPrinter = null;
	public Input   iCurrentInput   = null;
	public InputStatus iInputStatus    = new InputStatus();
	public Tokenizer iCurrentTokenizer;
	public Tokenizer iDefaultTokenizer = new Tokenizer();
	public Tokenizer iXmlTokenizer = new XmlTokenizer();

	public AssociatedHash iUserFunctions = new AssociatedHash();

	public String iError = null;

	public DefFiles iDefFiles = new DefFiles();
	public InputDirectories iInputDirectories = new InputDirectories();

	public String iPrettyReader = null;
	public String iPrettyPrinter = null;
        
        AssociatedHash iBuiltinFunctions = new AssociatedHash();

	public Environment(Output aCurrentOutput/*TODO FIXME*/) throws Exception
	{
		iCurrentTokenizer = iDefaultTokenizer;
		iInitialOutput = aCurrentOutput;
		iCurrentOutput = aCurrentOutput;
		iCurrentPrinter = new InfixPrinter(iPrefixOperators, iInfixOperators, iPostfixOperators, iBodiedOperators);

		iTrueAtom = Atom.getInstance(this,"True");
		iFalseAtom = Atom.getInstance(this,"False");

		iEndOfFileAtom    = Atom.getInstance(this,"EndOfFile");
		iEndStatementAtom = Atom.getInstance(this,";");
		iProgOpenAtom     = Atom.getInstance(this,"[");
		iProgCloseAtom    = Atom.getInstance(this,"]");
		iNthAtom          = Atom.getInstance(this,"Nth");
		iBracketOpenAtom  = Atom.getInstance(this,"(");
		iBracketCloseAtom = Atom.getInstance(this,")");
		iListOpenAtom     = Atom.getInstance(this,"{");
		iListCloseAtom    = Atom.getInstance(this,"}");
		iCommaAtom        = Atom.getInstance(this,",");
		iListAtom         = Atom.getInstance(this,"List");
		iProgAtom         = Atom.getInstance(this,"Prog");

		iArgumentStack = new ArgumentStack(50000 /*TODO FIXME*/);
		//org.mathrider.piper.builtin.Functions mc = new org.mathrider.piper.builtin.Functions();
		//mc.addFunctions(this);
                
                BuiltinFunction.addFunctions(this);
		
		pushLocalFrame(true);
	}

	public TokenHash getTokenHash()
	{
		return iTokenHash;
	}

	public int precision()
	{
		return iPrecision;
	}

	public void setPrecision(int aPrecision) throws Exception
	{
		iPrecision = aPrecision;    // precision in decimal digits
	}



	public AssociatedHash getBuiltinFunctions()
	{
		return iBuiltinFunctions;
	}
	


	public ConsPointer findLocal(String aVariable) throws Exception
	{
		LispError.check(iLocalsList != null,LispError.KLispErrInvalidStack);
		//    check(iLocalsList.iFirst != null,KLispErrInvalidStack);
		LispLocalVariable t = iLocalsList.iFirst;

		while (t != null)
		{
			if (t.iVariable == aVariable)
			{
				return t.iValue;
			}
			t = t.iNext;
		}
		return null;
	}

	public void setVariable(String aVariable, ConsPointer aValue, boolean aGlobalLazyVariable) throws Exception
	{
		ConsPointer local = findLocal(aVariable);
		if (local != null)
		{
			local.set(aValue.get());
			return;
		}
		GlobalVariable global = new GlobalVariable(aValue);
		iGlobalState.setAssociation(global, aVariable);
		if (aGlobalLazyVariable)
		{
			global.SetEvalBeforeReturn(true);
		}
	}

	public void getVariable(String aVariable,ConsPointer aResult) throws Exception
	{
		aResult.set(null);
		ConsPointer local = findLocal(aVariable);
		if (local != null)
		{
			aResult.set(local.get());
			return;
		}
		GlobalVariable l = (GlobalVariable)iGlobalState.lookUp(aVariable);
		if (l != null)
		{
			if (l.iEvalBeforeReturn)
			{
				iEvaluator.evaluate(this, aResult, l.iValue);
				l.iValue.set(aResult.get());
				l.iEvalBeforeReturn = false;
				return;
			}
			else
			{
				aResult.set(l.iValue.get());
				return;
			}
		}
	}

	public void unsetVariable(String aString) throws Exception
	{
		ConsPointer local = findLocal(aString);
		if (local != null)
		{
			local.set(null);
			return;
		}
		iGlobalState.release(aString);
	}

	public void pushLocalFrame(boolean aFenced)
	{
		if (aFenced)
		{
			LocalVariableFrame newFrame =
			        new LocalVariableFrame(iLocalsList, null);
			iLocalsList = newFrame;
		}
		else
		{
			LocalVariableFrame newFrame =
			        new LocalVariableFrame(iLocalsList, iLocalsList.iFirst);
			iLocalsList = newFrame;
		}
	}

	public void popLocalFrame() throws Exception
	{
		LispError.lispAssert(iLocalsList != null);
		LocalVariableFrame nextFrame = iLocalsList.iNext;
		iLocalsList.delete();
		iLocalsList = nextFrame;
	}

	public void newLocal(String aVariable,Cons aValue) throws Exception
	{
		LispError.lispAssert(iLocalsList != null);
		iLocalsList.add(new LispLocalVariable(aVariable, aValue));
	}


	class LispLocalVariable
	{
		public LispLocalVariable(String aVariable, Cons aValue)
		{
			iNext = null;
			iVariable = aVariable;
			iValue.set(aValue);

		}
		LispLocalVariable iNext;
		String iVariable;
		ConsPointer iValue = new ConsPointer();
	}

	class LocalVariableFrame
	{

		public LocalVariableFrame(LocalVariableFrame aNext, LispLocalVariable aFirst)
		{
			iNext = aNext;
			iFirst = aFirst;
			iLast = aFirst;
		}
		void add(LispLocalVariable aNew)
		{
			aNew.iNext = iFirst;
			iFirst = aNew;
		}
		void delete()
		{
			LispLocalVariable t = iFirst;
			LispLocalVariable next;
			while (t != iLast)
			{
				next = t.iNext;
				t = next;
			}
		}


		LocalVariableFrame iNext;
		LispLocalVariable iFirst;
		LispLocalVariable iLast;
	}


	public int getUniqueId()
	{
		return iLastUniqueId++;
	}


	public void holdArgument(String  aOperator, String aVariable) throws Exception
	{
		MultipleArityUserFunction multiUserFunc = (MultipleArityUserFunction)iUserFunctions.lookUp(aOperator);
		LispError.check(multiUserFunc != null,LispError.KLispErrInvalidArg);
		multiUserFunc.holdArgument(aVariable);
	}

	public void retract(String aOperator,int aArity) throws Exception
	{
		MultipleArityUserFunction multiUserFunc = (MultipleArityUserFunction)iUserFunctions.lookUp(aOperator);
		if (multiUserFunc != null)
		{
			multiUserFunc.deleteBase(aArity);
		}
	}

	public UserFunction userFunction(ConsPointer aArguments) throws Exception
	{
		MultipleArityUserFunction multiUserFunc =
		        (MultipleArityUserFunction)iUserFunctions.lookUp(aArguments.get().string());
		if (multiUserFunc != null)
		{
			int arity = UtilityFunctions.internalListLength(aArguments)-1;
			return  multiUserFunc.userFunction(arity);
		}
		return null;
	}

	public UserFunction userFunction(String aName,int aArity) throws Exception
	{
		MultipleArityUserFunction multiUserFunc = (MultipleArityUserFunction)iUserFunctions.lookUp(aName);
		if (multiUserFunc != null)
		{
			return  multiUserFunc.userFunction(aArity);
		}
		return null;
	}

	public void unFenceRule(String aOperator,int aArity) throws Exception
	{
		MultipleArityUserFunction multiUserFunc = (MultipleArityUserFunction)iUserFunctions.lookUp(aOperator);

		LispError.check(multiUserFunc != null, LispError.KLispErrInvalidArg);
		UserFunction userFunc = multiUserFunc.userFunction(aArity);
		LispError.check(userFunc != null, LispError.KLispErrInvalidArg);
		userFunc.unFence();
	}

	public MultipleArityUserFunction multiUserFunction(String aOperator) throws Exception
	{
		// Find existing multiuser func.
		MultipleArityUserFunction multiUserFunc = (MultipleArityUserFunction)iUserFunctions.lookUp(aOperator);

		// If none exists, add one to the user functions list
		if (multiUserFunc == null)
		{
			MultipleArityUserFunction newMulti = new MultipleArityUserFunction();
			iUserFunctions.setAssociation(newMulti, aOperator);
			multiUserFunc = (MultipleArityUserFunction)iUserFunctions.lookUp(aOperator);
			LispError.check(multiUserFunc != null, LispError.KLispErrCreatingUserFunction);
		}
		return multiUserFunc;
	}


	public void declareRuleBase(String aOperator, ConsPointer aParameters, boolean aListed) throws Exception
	{
		MultipleArityUserFunction multiUserFunc = multiUserFunction(aOperator);

		// add an operator with this arity to the multiuserfunc.
		BranchingUserFunction newFunc;
		if (aListed)
		{
			newFunc = new ListedBranchingUserFunction(aParameters);
		}
		else
		{
			newFunc = new BranchingUserFunction(aParameters);
		}
		multiUserFunc.defineRuleBase(newFunc);
	}

	public void defineRule(String aOperator,int aArity,
	                       int aPrecedence, ConsPointer aPredicate,
	                       ConsPointer aBody) throws Exception
	{
		// Find existing multiuser func.
		MultipleArityUserFunction multiUserFunc =
		        (MultipleArityUserFunction)iUserFunctions.lookUp(aOperator);
		LispError.check(multiUserFunc != null, LispError.KLispErrCreatingRule);

		// Get the specific user function with the right arity
		UserFunction userFunc = (UserFunction)multiUserFunc.userFunction(aArity);
		LispError.check(userFunc != null, LispError.KLispErrCreatingRule);

		// Declare a new evaluation rule


		if (UtilityFunctions.isTrue(this, aPredicate))
		{
			//        printf("FastPredicate on %s\n",aOperator->String());
			userFunc.declareRule(aPrecedence, aBody);
		}
		else
			userFunc.declareRule(aPrecedence, aPredicate,aBody);
	}

	public void declareMacroRuleBase(String aOperator, ConsPointer aParameters, boolean aListed) throws Exception
	{
		MultipleArityUserFunction multiUserFunc = multiUserFunction(aOperator);
		MacroUserFunction newFunc;
		if (aListed)
		{
			newFunc = new ListedMacroUserFunction(aParameters);
		}
		else
		{
			newFunc = new MacroUserFunction(aParameters);
		}
		multiUserFunc.defineRuleBase(newFunc);
	}

	public void defineRulePattern(String aOperator,int aArity, int aPrecedence, ConsPointer aPredicate, ConsPointer aBody) throws Exception
	{
		// Find existing multiuser func.
		MultipleArityUserFunction multiUserFunc = (MultipleArityUserFunction)iUserFunctions.lookUp(aOperator);
		LispError.check(multiUserFunc != null, LispError.KLispErrCreatingRule);

		// Get the specific user function with the right arity
		UserFunction userFunc = multiUserFunc.userFunction(aArity);
		LispError.check(userFunc != null, LispError.KLispErrCreatingRule);

		// Declare a new evaluation rule
		userFunc.declarePattern(aPrecedence, aPredicate,aBody);
	}


}

