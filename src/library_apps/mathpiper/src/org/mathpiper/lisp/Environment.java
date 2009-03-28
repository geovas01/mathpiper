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
package org.mathpiper.lisp;

import org.mathpiper.lisp.stacks.ArgumentStack;
import org.mathpiper.lisp.LispExpressionEvaluator;
import org.mathpiper.lisp.collections.DefFileMap;
import org.mathpiper.lisp.collections.Map;
import org.mathpiper.lisp.collections.TokenMap;
import org.mathpiper.lisp.collections.OperatorMap;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.printers.LispPrinter;
import org.mathpiper.io.MathPiperInputStream;
import org.mathpiper.io.MathPiperOutputStream;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.tokenizers.XmlTokenizer;
import org.mathpiper.io.InputStatus;

import org.mathpiper.io.InputDirectories;

import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;

import org.mathpiper.lisp.userfunctions.MultipleArityUserFunction;

import org.mathpiper.lisp.userfunctions.MacroUserFunction;

import org.mathpiper.lisp.userfunctions.UserFunction;

import org.mathpiper.lisp.userfunctions.ListedBranchingUserFunction;

import org.mathpiper.lisp.userfunctions.BranchingUserFunction;

import org.mathpiper.lisp.userfunctions.ListedMacroUserFunction;

import org.mathpiper.lisp.printers.MathPiperPrinter;
import org.mathpiper.lisp.userfunctions.Evaluator;


public class Environment
{

    public Evaluator iLispExpressionEvaluator = new LispExpressionEvaluator();
    private int iPrecision = 10;
    private TokenMap iTokenHash = new TokenMap();
    public Cons iTrueAtom;
    public final String iTrueString;
    public Cons iFalseAtom;
    public final String iFalseString;
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
    public OperatorMap iPrefixOperators = new OperatorMap();
    public OperatorMap iInfixOperators = new OperatorMap();
    public OperatorMap iPostfixOperators = new OperatorMap();
    public OperatorMap iBodiedOperators = new OperatorMap();
    public volatile int iEvalDepth = 0;
    public int iMaxEvalDepth = 10000;
    //TODO FIXME
    public ArgumentStack iArgumentStack;
    public LocalVariableFrame iLocalVariablesList;
    public boolean iSecure = false;
    public int iLastUniqueId = 1;
    public MathPiperOutputStream iCurrentOutput = null;
    public MathPiperOutputStream iInitialOutput = null;
    public LispPrinter iCurrentPrinter = null;
    public MathPiperInputStream iCurrentInput = null;
    public InputStatus iInputStatus = new InputStatus();
    public MathPiperTokenizer iCurrentTokenizer;
    public MathPiperTokenizer iDefaultTokenizer = new MathPiperTokenizer();
    public MathPiperTokenizer iXmlTokenizer = new XmlTokenizer();
    public Map iGlobalState = new Map();
    public Map iUserFunctions = new Map();
    Map iBuiltinFunctions = new Map();
    public String iError = null;
    public DefFileMap iDefFiles = new DefFileMap();
    public InputDirectories iInputDirectories = new InputDirectories();
    public String iPrettyReader = null;
    public String iPrettyPrinter = null;

    public Environment(MathPiperOutputStream aCurrentOutput/*TODO FIXME*/) throws Exception
    {
        iCurrentTokenizer = iDefaultTokenizer;
        iInitialOutput = aCurrentOutput;
        iCurrentOutput = aCurrentOutput;
        iCurrentPrinter = new MathPiperPrinter(iPrefixOperators, iInfixOperators, iPostfixOperators, iBodiedOperators);

        iTrueAtom = AtomCons.getInstance(this, "True");
        iTrueString = iTrueAtom.string();
        iFalseAtom = AtomCons.getInstance(this, "False");
        iFalseString = iFalseAtom.string();
        iEndOfFileAtom = AtomCons.getInstance(this, "EndOfFile");
        iEndStatementAtom = AtomCons.getInstance(this, ";");
        iProgOpenAtom = AtomCons.getInstance(this, "[");
        iProgCloseAtom = AtomCons.getInstance(this, "]");
        iNthAtom = AtomCons.getInstance(this, "Nth");
        iBracketOpenAtom = AtomCons.getInstance(this, "(");
        iBracketCloseAtom = AtomCons.getInstance(this, ")");
        iListOpenAtom = AtomCons.getInstance(this, "{");
        iListCloseAtom = AtomCons.getInstance(this, "}");
        iCommaAtom = AtomCons.getInstance(this, ",");
        iListAtom = AtomCons.getInstance(this, "List");
        iProgAtom = AtomCons.getInstance(this, "Prog");

        iArgumentStack = new ArgumentStack(50000 /*TODO FIXME*/);
        //org.mathpiper.builtin.Functions mc = new org.mathpiper.builtin.Functions();
        //mc.addFunctions(this);

        BuiltinFunction.addFunctions(this);

        pushLocalFrame(true);
    }

    public TokenMap getTokenHash()
    {
        return iTokenHash;
    }



    public Map getGlobalState()
    {
        return iGlobalState;
    }

    public Map getUserFunctions()
    {
        return iUserFunctions;
    }
    
   public Map getBuiltinFunctions()
    {
        return iBuiltinFunctions;
    }

    public int getPrecision()
    {
        return iPrecision;
    }

    public void setPrecision(int aPrecision) throws Exception
    {
        iPrecision = aPrecision;    // getPrecision in decimal digits
    }


    public ConsPointer findLocalVariable(String aVariable) throws Exception
    {
        LispError.check(iLocalVariablesList != null, LispError.KLispErrInvalidStack);
        //    check(iLocalsList.iFirst != null,KLispErrInvalidStack);
        LocalVariable t = iLocalVariablesList.iFirst;

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

    public void setGlobalVariable(String aVariable, ConsPointer aValue, boolean aGlobalLazyVariable) throws Exception
    {
        ConsPointer localVariable = findLocalVariable(aVariable);
        if (localVariable != null)
        {
            localVariable.setCons(aValue.getCons());
            return;
        }
        GlobalVariable globalVariable = new GlobalVariable(aValue);
        iGlobalState.setAssociation(globalVariable, aVariable);
        if (aGlobalLazyVariable)
        {
            globalVariable.setEvalBeforeReturn(true);
        }
    }

    public void getGlobalVariable(String aVariable, ConsPointer aResult) throws Exception
    {
        aResult.setCons(null);
        ConsPointer localVariable = findLocalVariable(aVariable);
        if (localVariable != null)
        {
            aResult.setCons(localVariable.getCons());
            return;
        }
        GlobalVariable globalVariable = (GlobalVariable) iGlobalState.lookUp(aVariable);
        if (globalVariable != null)
        {
            if (globalVariable.iEvalBeforeReturn)
            {
                iLispExpressionEvaluator.evaluate(this, aResult, globalVariable.iValue);
                globalVariable.iValue.setCons(aResult.getCons());
                globalVariable.iEvalBeforeReturn = false;
                return;
            } else
            {
                aResult.setCons(globalVariable.iValue.getCons());
                return;
            }
        }
    }

    public void unsetLocalVariable(String aString) throws Exception
    {
        ConsPointer localVariable = findLocalVariable(aString);
        if (localVariable != null)
        {
            localVariable.setCons(null);
            return;
        }
        iGlobalState.release(aString);
    }

    public void pushLocalFrame(boolean aFenced)
    {
        if (aFenced)
        {
            LocalVariableFrame newLocalVariableFrame = new LocalVariableFrame(iLocalVariablesList, null);
            iLocalVariablesList = newLocalVariableFrame;
        } else
        {
            LocalVariableFrame newLocalVariableFrame = new LocalVariableFrame(iLocalVariablesList, iLocalVariablesList.iFirst);
            iLocalVariablesList = newLocalVariableFrame;
        }
    }

    public void popLocalFrame() throws Exception
    {
        LispError.lispAssert(iLocalVariablesList != null);
        LocalVariableFrame nextLocalVariableFrame = iLocalVariablesList.iNext;
        iLocalVariablesList.delete();
        iLocalVariablesList = nextLocalVariableFrame;
    }

    public void newLocalVariable(String aVariable, Cons aValue) throws Exception
    {
        LispError.lispAssert(iLocalVariablesList != null);
        iLocalVariablesList.add(new LocalVariable(aVariable, aValue));
    }

    class LocalVariable
    {

        public LocalVariable(String aVariable, Cons aValue)
        {
            iNext = null;
            iVariable = aVariable;
            iValue.setCons(aValue);

        }
        LocalVariable iNext;
        String iVariable;
        ConsPointer iValue = new ConsPointer();
    }

    class LocalVariableFrame
    {

        public LocalVariableFrame(LocalVariableFrame aNext, LocalVariable aFirst)
        {
            iNext = aNext;
            iFirst = aFirst;
            iLast = aFirst;
        }

        void add(LocalVariable aNew)
        {
            aNew.iNext = iFirst;
            iFirst = aNew;
        }

        void delete()
        {
            LocalVariable t = iFirst;
            LocalVariable next;
            while (t != iLast)
            {
                next = t.iNext;
                t = next;
            }
        }
        LocalVariableFrame iNext;
        LocalVariable iFirst;
        LocalVariable iLast;
    }

    public int getUniqueId()
    {
        return iLastUniqueId++;
    }

    public void holdArgument(String aOperator, String aVariable) throws Exception
    {
        MultipleArityUserFunction multipleArityUserFunc = (MultipleArityUserFunction) iUserFunctions.lookUp(aOperator);
        LispError.check(multipleArityUserFunc != null, LispError.KLispErrInvalidArg);
        multipleArityUserFunc.holdArgument(aVariable);
    }

    public void retractFunction(String aOperator, int aArity) throws Exception
    {
        MultipleArityUserFunction multipleArityUserFunc = (MultipleArityUserFunction) iUserFunctions.lookUp(aOperator);
        if (multipleArityUserFunc != null)
        {
            multipleArityUserFunc.deleteRuleDatabaseEntry(aArity);
        }
    }

    public UserFunction getUserFunction(ConsPointer aArguments) throws Exception
    {
        MultipleArityUserFunction multipleArityUserFunc = (MultipleArityUserFunction) iUserFunctions.lookUp(aArguments.getCons().string());
        if (multipleArityUserFunc != null)
        {
            int arity = UtilityFunctions.listLength(aArguments) - 1;
            return multipleArityUserFunc.getUserFunction(arity);
        }
        return null;
    }

    public UserFunction getUserFunction(String aName, int aArity) throws Exception
    {
        MultipleArityUserFunction multipleArityUserFunc = (MultipleArityUserFunction) iUserFunctions.lookUp(aName);
        if (multipleArityUserFunc != null)
        {
            return multipleArityUserFunc.getUserFunction(aArity);
        }
        return null;
    }

    public void unFenceRule(String aOperator, int aArity) throws Exception
    {
        MultipleArityUserFunction multiUserFunc = (MultipleArityUserFunction) iUserFunctions.lookUp(aOperator);

        LispError.check(multiUserFunc != null, LispError.KLispErrInvalidArg);
        UserFunction userFunc = multiUserFunc.getUserFunction(aArity);
        LispError.check(userFunc != null, LispError.KLispErrInvalidArg);
        userFunc.unFence();
    }

    public MultipleArityUserFunction getMultipleArityUserFunction(String aOperator) throws Exception
    {
        // Find existing multiuser func.
        MultipleArityUserFunction multipleArityUserFunction = (MultipleArityUserFunction) iUserFunctions.lookUp(aOperator);

        // If none exists, add one to the user functions list
        if (multipleArityUserFunction == null)
        {
            MultipleArityUserFunction newMultipleArityUserFunction = new MultipleArityUserFunction();
            iUserFunctions.setAssociation(newMultipleArityUserFunction, aOperator);
            multipleArityUserFunction = (MultipleArityUserFunction) iUserFunctions.lookUp(aOperator);
            LispError.check(multipleArityUserFunction != null, LispError.KLispErrCreatingUserFunction);
        }
        return multipleArityUserFunction;
    }

    public void declareRulebase(String aOperator, ConsPointer aParametersPointer, boolean aListed) throws Exception
    {
        MultipleArityUserFunction multipleArityUserFunction = getMultipleArityUserFunction(aOperator);

        // add an operator with this arity to the multiuserfunc.
        BranchingUserFunction newBranchingUserFunction;
        if (aListed)
        {
            newBranchingUserFunction = new ListedBranchingUserFunction(aParametersPointer);
        } else
        {
            newBranchingUserFunction = new BranchingUserFunction(aParametersPointer);
        }
        multipleArityUserFunction.addRuleDatabaseEntry(newBranchingUserFunction);
    }

    public void defineRule(String aOperator, int aArity,
            int aPrecedence, ConsPointer aPredicate,
            ConsPointer aBody) throws Exception
    {
        // Find existing multiuser func.
        MultipleArityUserFunction multipleArityUserFunction = (MultipleArityUserFunction) iUserFunctions.lookUp(aOperator);
        LispError.check(multipleArityUserFunction != null, LispError.KLispErrCreatingRule);

        // Get the specific user function with the right arity
        UserFunction userFunction = (UserFunction) multipleArityUserFunction.getUserFunction(aArity);
        LispError.check(userFunction != null, LispError.KLispErrCreatingRule);

        // Declare a new evaluation rule
        if (UtilityFunctions.isTrue(this, aPredicate))
        {
            //        printf("FastPredicate on %s\n",aOperator->String());
            userFunction.declareRule(aPrecedence, aBody);
        } else
        {
            userFunction.declareRule(aPrecedence, aPredicate, aBody);
        }
    }

    public void declareMacroRulebase(String aOperator, ConsPointer aParameters, boolean aListed) throws Exception
    {
        MultipleArityUserFunction multipleArityUserFunc = getMultipleArityUserFunction(aOperator);

        MacroUserFunction newMacroUserFunction;

        if (aListed)
        {
            newMacroUserFunction = new ListedMacroUserFunction(aParameters);
        } else
        {
            newMacroUserFunction = new MacroUserFunction(aParameters);
        }
        multipleArityUserFunc.addRuleDatabaseEntry(newMacroUserFunction);
    }

    public void defineRulePattern(String aOperator, int aArity, int aPrecedence, ConsPointer aPredicate, ConsPointer aBody) throws Exception
    {
        // Find existing multiuser func.
        MultipleArityUserFunction multipleArityUserFunc = (MultipleArityUserFunction) iUserFunctions.lookUp(aOperator);
        LispError.check(multipleArityUserFunc != null, LispError.KLispErrCreatingRule);

        // Get the specific user function with the right arity
        UserFunction userFunction = multipleArityUserFunc.getUserFunction(aArity);
        LispError.check(userFunction != null, LispError.KLispErrCreatingRule);

        // Declare a new evaluation rule
        userFunction.declarePattern(aPrecedence, aPredicate, aBody);
    }
    
    /**
     * Write data to the current output.
     * @param aString
     * @throws java.lang.Exception
     */
    public void write(String aString) throws Exception
    {
        iCurrentOutput.write(aString);
    }
}

