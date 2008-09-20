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

import org.mathrider.piper.*;


public class LispEnvironment
{
        public LispEvaluatorBase iEvaluator = new BasicEvaluator();
        
	public int iPrecision = 10;

	public LispHashTable iHashTable = new LispHashTable();
	public LispObject iTrue;
	public LispObject iFalse;

	public LispObject iEndOfFile;
	public LispObject iEndStatement;
	public LispObject iProgOpen;
	public LispObject iProgClose;
	public LispObject iNth;
	public LispObject iBracketOpen;
	public LispObject iBracketClose;
	public LispObject iListOpen;
        public 	LispObject iListClose;
	public LispObject iComma;
	public LispObject iList;
	public LispObject iProg;

	public LispOperators iPrefixOperators = new LispOperators();
	public LispOperators iInfixOperators = new LispOperators();
	public LispOperators iPostfixOperators = new LispOperators();
	public LispOperators iBodiedOperators = new LispOperators();

	public int iEvalDepth = 0;
	public int iMaxEvalDepth = 10000;
	//TODO FIXME

	public PiperArgStack iStack;

	public LocalVariableFrame iLocalsList;

	public LispGlobal iGlobals = new LispGlobal();

	public boolean iSecure = false;

	public int iLastUniqueId = 1;

	public LispOutput iCurrentOutput = null;
	public LispOutput iInitialOutput = null;

	public LispPrinter iCurrentPrinter = null;
	public LispInput   iCurrentInput   = null;
	public InputStatus iInputStatus    = new InputStatus();
	public LispTokenizer iCurrentTokenizer;
	public LispTokenizer iDefaultTokenizer = new LispTokenizer();
	public LispTokenizer iXmlTokenizer = new XmlTokenizer();

	public LispUserFunctions iUserFunctions = new LispUserFunctions();

	public String iError = null;

	public LispDefFiles iDefFiles = new LispDefFiles();
	public InputDirectories iInputDirectories = new InputDirectories();

	public String iPrettyReader = null;
	public String iPrettyPrinter = null;

	public LispEnvironment(LispOutput aCurrentOutput/*TODO FIXME*/) throws Exception
	{
		iCurrentTokenizer = iDefaultTokenizer;
		iInitialOutput = aCurrentOutput;
		iCurrentOutput = aCurrentOutput;
		iCurrentPrinter = new InfixPrinter(iPrefixOperators, iInfixOperators, iPostfixOperators, iBodiedOperators);

		iTrue = LispAtom.getInstance(this,"True");
		iFalse = LispAtom.getInstance(this,"False");

		iEndOfFile    = LispAtom.getInstance(this,"EndOfFile");
		iEndStatement = LispAtom.getInstance(this,";");
		iProgOpen     = LispAtom.getInstance(this,"[");
		iProgClose    = LispAtom.getInstance(this,"]");
		iNth          = LispAtom.getInstance(this,"Nth");
		iBracketOpen  = LispAtom.getInstance(this,"(");
		iBracketClose = LispAtom.getInstance(this,")");
		iListOpen     = LispAtom.getInstance(this,"{");
		iListClose    = LispAtom.getInstance(this,"}");
		iComma        = LispAtom.getInstance(this,",");
		iList         = LispAtom.getInstance(this,"List");
		iProg         = LispAtom.getInstance(this,"Prog");

		iStack = new PiperArgStack(50000 /*TODO FIXME*/);
		MathCommands mc = new MathCommands();
		mc.AddCommands(this);
		mc=null;
		pushLocalFrame(true);
	}

	public LispHashTable hashTable()
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







	public PiperCoreCommands coreCommands()
	{
		return iCoreCommands;
	}
	PiperCoreCommands iCoreCommands = new PiperCoreCommands();

	







	public LispPtr findLocal(String aVariable) throws Exception
	{
		LispError.Check(iLocalsList != null,LispError.KLispErrInvalidStack);
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

	public void setVariable(String aVariable, LispPtr aValue, boolean aGlobalLazyVariable) throws Exception
	{
		LispPtr local = findLocal(aVariable);
		if (local != null)
		{
			local.set(aValue.get());
			return;
		}
		LispGlobalVariable global = new LispGlobalVariable(aValue);
		iGlobals.setAssociation(global, aVariable);
		if (aGlobalLazyVariable)
		{
			global.SetEvalBeforeReturn(true);
		}
	}

	public void getVariable(String aVariable,LispPtr aResult) throws Exception
	{
		aResult.set(null);
		LispPtr local = findLocal(aVariable);
		if (local != null)
		{
			aResult.set(local.get());
			return;
		}
		LispGlobalVariable l = (LispGlobalVariable)iGlobals.lookUp(aVariable);
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
		LispPtr local = findLocal(aString);
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
		LispError.LISPASSERT(iLocalsList != null);
		LocalVariableFrame nextFrame = iLocalsList.iNext;
		iLocalsList.delete();
		iLocalsList = nextFrame;
	}

	public void newLocal(String aVariable,LispObject aValue) throws Exception
	{
		LispError.LISPASSERT(iLocalsList != null);
		iLocalsList.add(new LispLocalVariable(aVariable, aValue));
	}


	class LispLocalVariable
	{
		public LispLocalVariable(String aVariable, LispObject aValue)
		{
			iNext = null;
			iVariable = aVariable;
			iValue.set(aValue);

		}
		LispLocalVariable iNext;
		String iVariable;
		LispPtr iValue = new LispPtr();
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
		LispMultiUserFunction multiUserFunc = (LispMultiUserFunction)iUserFunctions.lookUp(aOperator);
		LispError.Check(multiUserFunc != null,LispError.KLispErrInvalidArg);
		multiUserFunc.HoldArgument(aVariable);
	}

	public void retract(String aOperator,int aArity) throws Exception
	{
		LispMultiUserFunction multiUserFunc = (LispMultiUserFunction)iUserFunctions.lookUp(aOperator);
		if (multiUserFunc != null)
		{
			multiUserFunc.DeleteBase(aArity);
		}
	}

	public LispUserFunction userFunction(LispPtr aArguments) throws Exception
	{
		LispMultiUserFunction multiUserFunc =
		        (LispMultiUserFunction)iUserFunctions.lookUp(aArguments.get().string());
		if (multiUserFunc != null)
		{
			int arity = LispStandard.internalListLength(aArguments)-1;
			return  multiUserFunc.UserFunc(arity);
		}
		return null;
	}

	public LispUserFunction userFunction(String aName,int aArity) throws Exception
	{
		LispMultiUserFunction multiUserFunc = (LispMultiUserFunction)iUserFunctions.lookUp(aName);
		if (multiUserFunc != null)
		{
			return  multiUserFunc.UserFunc(aArity);
		}
		return null;
	}

	public void unFenceRule(String aOperator,int aArity) throws Exception
	{
		LispMultiUserFunction multiUserFunc = (LispMultiUserFunction)iUserFunctions.lookUp(aOperator);

		LispError.Check(multiUserFunc != null, LispError.KLispErrInvalidArg);
		LispUserFunction userFunc = multiUserFunc.UserFunc(aArity);
		LispError.Check(userFunc != null, LispError.KLispErrInvalidArg);
		userFunc.UnFence();
	}

	public LispMultiUserFunction multiUserFunction(String aOperator) throws Exception
	{
		// Find existing multiuser func.
		LispMultiUserFunction multiUserFunc = (LispMultiUserFunction)iUserFunctions.lookUp(aOperator);

		// If none exists, add one to the user functions list
		if (multiUserFunc == null)
		{
			LispMultiUserFunction newMulti = new LispMultiUserFunction();
			iUserFunctions.setAssociation(newMulti, aOperator);
			multiUserFunc = (LispMultiUserFunction)iUserFunctions.lookUp(aOperator);
			LispError.Check(multiUserFunc != null, LispError.KLispErrCreatingUserFunction);
		}
		return multiUserFunc;
	}


	public void declareRuleBase(String aOperator, LispPtr aParameters, boolean aListed) throws Exception
	{
		LispMultiUserFunction multiUserFunc = multiUserFunction(aOperator);

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
	                       int aPrecedence, LispPtr aPredicate,
	                       LispPtr aBody) throws Exception
	{
		// Find existing multiuser func.
		LispMultiUserFunction multiUserFunc =
		        (LispMultiUserFunction)iUserFunctions.lookUp(aOperator);
		LispError.Check(multiUserFunc != null, LispError.KLispErrCreatingRule);

		// Get the specific user function with the right arity
		LispUserFunction userFunc = (LispUserFunction)multiUserFunc.UserFunc(aArity);
		LispError.Check(userFunc != null, LispError.KLispErrCreatingRule);

		// Declare a new evaluation rule


		if (LispStandard.isTrue(this, aPredicate))
		{
			//        printf("FastPredicate on %s\n",aOperator->String());
			userFunc.DeclareRule(aPrecedence, aBody);
		}
		else
			userFunc.DeclareRule(aPrecedence, aPredicate,aBody);
	}

	public void declareMacroRuleBase(String aOperator, LispPtr aParameters, boolean aListed) throws Exception
	{
		LispMultiUserFunction multiUserFunc = multiUserFunction(aOperator);
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

	public void defineRulePattern(String aOperator,int aArity, int aPrecedence, LispPtr aPredicate, LispPtr aBody) throws Exception
	{
		// Find existing multiuser func.
		LispMultiUserFunction multiUserFunc = (LispMultiUserFunction)iUserFunctions.lookUp(aOperator);
		LispError.Check(multiUserFunc != null, LispError.KLispErrCreatingRule);

		// Get the specific user function with the right arity
		LispUserFunction userFunc = multiUserFunc.UserFunc(aArity);
		LispError.Check(userFunc != null, LispError.KLispErrCreatingRule);

		// Declare a new evaluation rule
		userFunc.DeclarePattern(aPrecedence, aPredicate,aBody);
	}


}

