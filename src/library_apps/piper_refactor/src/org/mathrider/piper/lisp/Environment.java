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

import org.mathrider.piper.printers.InfixPrinter;
import org.mathrider.piper.*;


public class Environment
{
        public EvaluatorBase iEvaluator = new BasicEvaluator();
        
	public int iPrecision = 10;

	public HashTable iHashTable = new HashTable();
	public Cons iTrue;
	public Cons iFalse;

	public Cons iEndOfFile;
	public Cons iEndStatement;
	public Cons iProgOpen;
	public Cons iProgClose;
	public Cons iNth;
	public Cons iBracketOpen;
	public Cons iBracketClose;
	public Cons iListOpen;
        public Cons iListClose;
	public Cons iComma;
	public Cons iList;
	public Cons iProg;

	public Operators iPrefixOperators = new Operators();
	public Operators iInfixOperators = new Operators();
	public Operators iPostfixOperators = new Operators();
	public Operators iBodiedOperators = new Operators();

	public int iEvalDepth = 0;
	public int iMaxEvalDepth = 10000;
	//TODO FIXME

	public PiperArgStack iStack;

	public LocalVariableFrame iLocalsList;

	public Global iGlobals = new Global();

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

	public UserFunctions iUserFunctions = new UserFunctions();

	public String iError = null;

	public DefFiles iDefFiles = new DefFiles();
	public InputDirectories iInputDirectories = new InputDirectories();

	public String iPrettyReader = null;
	public String iPrettyPrinter = null;
        
        PiperBuiltinCommands iBuiltinCommands = new PiperBuiltinCommands();

	public Environment(Output aCurrentOutput/*TODO FIXME*/) throws Exception
	{
		iCurrentTokenizer = iDefaultTokenizer;
		iInitialOutput = aCurrentOutput;
		iCurrentOutput = aCurrentOutput;
		iCurrentPrinter = new InfixPrinter(iPrefixOperators, iInfixOperators, iPostfixOperators, iBodiedOperators);

		iTrue = Atom.getInstance(this,"True");
		iFalse = Atom.getInstance(this,"False");

		iEndOfFile    = Atom.getInstance(this,"EndOfFile");
		iEndStatement = Atom.getInstance(this,";");
		iProgOpen     = Atom.getInstance(this,"[");
		iProgClose    = Atom.getInstance(this,"]");
		iNth          = Atom.getInstance(this,"Nth");
		iBracketOpen  = Atom.getInstance(this,"(");
		iBracketClose = Atom.getInstance(this,")");
		iListOpen     = Atom.getInstance(this,"{");
		iListClose    = Atom.getInstance(this,"}");
		iComma        = Atom.getInstance(this,",");
		iList         = Atom.getInstance(this,"List");
		iProg         = Atom.getInstance(this,"Prog");

		iStack = new PiperArgStack(50000 /*TODO FIXME*/);
		MathCommands mc = new MathCommands();
		mc.AddCommands(this);
		mc=null;
		pushLocalFrame(true);
	}

	public HashTable hashTable()
	{
		return iHashTable;
	}

	public int precision()
	{
		return iPrecision;
	}

	public void setPrecision(int aPrecision) throws Exception
	{
		iPrecision = aPrecision;    // precision in decimal digits
	}







	public PiperBuiltinCommands builtinCommands()
	{
		return iBuiltinCommands;
	}
	

	







	public Pointer findLocal(String aVariable) throws Exception
	{
		Error.Check(iLocalsList != null,Error.KLispErrInvalidStack);
		//    Check(iLocalsList.iFirst != null,KLispErrInvalidStack);
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

	public void setVariable(String aVariable, Pointer aValue, boolean aGlobalLazyVariable) throws Exception
	{
		Pointer local = findLocal(aVariable);
		if (local != null)
		{
			local.set(aValue.get());
			return;
		}
		GlobalVariable global = new GlobalVariable(aValue);
		iGlobals.setAssociation(global, aVariable);
		if (aGlobalLazyVariable)
		{
			global.SetEvalBeforeReturn(true);
		}
	}

	public void getVariable(String aVariable,Pointer aResult) throws Exception
	{
		aResult.set(null);
		Pointer local = findLocal(aVariable);
		if (local != null)
		{
			aResult.set(local.get());
			return;
		}
		GlobalVariable l = (GlobalVariable)iGlobals.lookUp(aVariable);
		if (l != null)
		{
			if (l.iEvalBeforeReturn)
			{
				iEvaluator.eval(this, aResult, l.iValue);
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
		Pointer local = findLocal(aString);
		if (local != null)
		{
			local.set(null);
			return;
		}
		iGlobals.release(aString);
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
		Error.LISPASSERT(iLocalsList != null);
		LocalVariableFrame nextFrame = iLocalsList.iNext;
		iLocalsList.delete();
		iLocalsList = nextFrame;
	}

	public void newLocal(String aVariable,Cons aValue) throws Exception
	{
		Error.LISPASSERT(iLocalsList != null);
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
		Pointer iValue = new Pointer();
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
		MultiUserFunction multiUserFunc = (MultiUserFunction)iUserFunctions.lookUp(aOperator);
		Error.Check(multiUserFunc != null,Error.KLispErrInvalidArg);
		multiUserFunc.HoldArgument(aVariable);
	}

	public void retract(String aOperator,int aArity) throws Exception
	{
		MultiUserFunction multiUserFunc = (MultiUserFunction)iUserFunctions.lookUp(aOperator);
		if (multiUserFunc != null)
		{
			multiUserFunc.DeleteBase(aArity);
		}
	}

	public UserFunction userFunction(Pointer aArguments) throws Exception
	{
		MultiUserFunction multiUserFunc =
		        (MultiUserFunction)iUserFunctions.lookUp(aArguments.get().string());
		if (multiUserFunc != null)
		{
			int arity = Standard.internalListLength(aArguments)-1;
			return  multiUserFunc.UserFunc(arity);
		}
		return null;
	}

	public UserFunction userFunction(String aName,int aArity) throws Exception
	{
		MultiUserFunction multiUserFunc = (MultiUserFunction)iUserFunctions.lookUp(aName);
		if (multiUserFunc != null)
		{
			return  multiUserFunc.UserFunc(aArity);
		}
		return null;
	}

	public void unFenceRule(String aOperator,int aArity) throws Exception
	{
		MultiUserFunction multiUserFunc = (MultiUserFunction)iUserFunctions.lookUp(aOperator);

		Error.Check(multiUserFunc != null, Error.KLispErrInvalidArg);
		UserFunction userFunc = multiUserFunc.UserFunc(aArity);
		Error.Check(userFunc != null, Error.KLispErrInvalidArg);
		userFunc.UnFence();
	}

	public MultiUserFunction multiUserFunction(String aOperator) throws Exception
	{
		// Find existing multiuser func.
		MultiUserFunction multiUserFunc = (MultiUserFunction)iUserFunctions.lookUp(aOperator);

		// If none exists, add one to the user functions list
		if (multiUserFunc == null)
		{
			MultiUserFunction newMulti = new MultiUserFunction();
			iUserFunctions.setAssociation(newMulti, aOperator);
			multiUserFunc = (MultiUserFunction)iUserFunctions.lookUp(aOperator);
			Error.Check(multiUserFunc != null, Error.KLispErrCreatingUserFunction);
		}
		return multiUserFunc;
	}


	public void declareRuleBase(String aOperator, Pointer aParameters, boolean aListed) throws Exception
	{
		MultiUserFunction multiUserFunc = multiUserFunction(aOperator);

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
		multiUserFunc.DefineRuleBase(newFunc);
	}

	public void defineRule(String aOperator,int aArity,
	                       int aPrecedence, Pointer aPredicate,
	                       Pointer aBody) throws Exception
	{
		// Find existing multiuser func.
		MultiUserFunction multiUserFunc =
		        (MultiUserFunction)iUserFunctions.lookUp(aOperator);
		Error.Check(multiUserFunc != null, Error.KLispErrCreatingRule);

		// Get the specific user function with the right arity
		UserFunction userFunc = (UserFunction)multiUserFunc.UserFunc(aArity);
		Error.Check(userFunc != null, Error.KLispErrCreatingRule);

		// Declare a new evaluation rule


		if (Standard.isTrue(this, aPredicate))
		{
			//        printf("FastPredicate on %s\n",aOperator->String());
			userFunc.DeclareRule(aPrecedence, aBody);
		}
		else
			userFunc.DeclareRule(aPrecedence, aPredicate,aBody);
	}

	public void declareMacroRuleBase(String aOperator, Pointer aParameters, boolean aListed) throws Exception
	{
		MultiUserFunction multiUserFunc = multiUserFunction(aOperator);
		MacroUserFunction newFunc;
		if (aListed)
		{
			newFunc = new ListedMacroUserFunction(aParameters);
		}
		else
		{
			newFunc = new MacroUserFunction(aParameters);
		}
		multiUserFunc.DefineRuleBase(newFunc);
	}

	public void defineRulePattern(String aOperator,int aArity, int aPrecedence, Pointer aPredicate, Pointer aBody) throws Exception
	{
		// Find existing multiuser func.
		MultiUserFunction multiUserFunc = (MultiUserFunction)iUserFunctions.lookUp(aOperator);
		Error.Check(multiUserFunc != null, Error.KLispErrCreatingRule);

		// Get the specific user function with the right arity
		UserFunction userFunc = multiUserFunc.UserFunc(aArity);
		Error.Check(userFunc != null, Error.KLispErrCreatingRule);

		// Declare a new evaluation rule
		userFunc.DeclarePattern(aPrecedence, aPredicate,aBody);
	}


}

