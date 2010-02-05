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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.mathpiper.lisp.stacks.ArgumentStack;
import org.mathpiper.lisp.collections.DefFileMap;
import org.mathpiper.lisp.collections.MathPiperMap;
import org.mathpiper.lisp.collections.TokenMap;
import org.mathpiper.lisp.collections.OperatorMap;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.printers.LispPrinter;
import org.mathpiper.io.MathPiperInputStream;
import org.mathpiper.io.MathPiperOutputStream;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.tokenizers.XmlTokenizer;
import org.mathpiper.io.InputStatus;

import org.mathpiper.io.InputDirectories;

import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;

import org.mathpiper.lisp.userfunctions.MultipleArityUserFunction;

import org.mathpiper.lisp.userfunctions.MacroUserFunction;


import org.mathpiper.lisp.userfunctions.ListedBranchingUserFunction;

import org.mathpiper.lisp.userfunctions.SingleArityBranchingUserFunction;

import org.mathpiper.lisp.userfunctions.ListedMacroUserFunction;

import org.mathpiper.lisp.printers.MathPiperPrinter;

import org.mathpiper.lisp.localvariables.LocalVariable;
import org.mathpiper.lisp.localvariables.LocalVariableFrame;

public class Environment {

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
    public Cons iComplexAtom;
    public Cons iBracketOpenAtom;
    public Cons iBracketCloseAtom;
    public Cons iListOpenAtom;
    public Cons iListCloseAtom;
    public Cons iCommaAtom;
    public Cons iListAtom;
    public Cons iSetAtom;
    public Cons iProgAtom;
    public OperatorMap iPrefixOperators = new OperatorMap(this);
    public OperatorMap iInfixOperators = new OperatorMap(this);
    public OperatorMap iPostfixOperators = new OperatorMap(this);
    public OperatorMap iBodiedOperators = new OperatorMap(this);
    public volatile int iEvalDepth = 0;
    public int iMaxEvalDepth = 10000;
    //TODO FIXME
    public ArgumentStack iArgumentStack;
    public LocalVariableFrame iLocalVariablesFrame;
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
    public MathPiperMap iGlobalState = new MathPiperMap();
    public MathPiperMap iUserFunctions = new MathPiperMap();
    MathPiperMap iBuiltinFunctions = new MathPiperMap();
    public String iError = null;
    public DefFileMap iDefFiles = new DefFileMap();
    public InputDirectories iInputDirectories = new InputDirectories();
    public String iPrettyReaderName = null;
    public String iPrettyPrinterName = null;

    public Environment(MathPiperOutputStream aCurrentOutput/*TODO FIXME*/) throws Exception {
        iCurrentTokenizer = iDefaultTokenizer;
        iInitialOutput = aCurrentOutput;
        iCurrentOutput = aCurrentOutput;
        iCurrentPrinter = new MathPiperPrinter(iPrefixOperators, iInfixOperators, iPostfixOperators, iBodiedOperators);

        iTrueAtom = AtomCons.getInstance(this, "True");
        iTrueString = (String) iTrueAtom.car();
        iFalseAtom = AtomCons.getInstance(this, "False");
        iFalseString = (String) iFalseAtom.car();
        iEndOfFileAtom = AtomCons.getInstance(this, "EndOfFile");
        iEndStatementAtom = AtomCons.getInstance(this, ";");
        iProgOpenAtom = AtomCons.getInstance(this, "[");
        iProgCloseAtom = AtomCons.getInstance(this, "]");
        iNthAtom = AtomCons.getInstance(this, "Nth");
        iComplexAtom = AtomCons.getInstance(this, "Complex");
        iBracketOpenAtom = AtomCons.getInstance(this, "(");
        iBracketCloseAtom = AtomCons.getInstance(this, ")");
        iListOpenAtom = AtomCons.getInstance(this, "{");
        iListCloseAtom = AtomCons.getInstance(this, "}");
        iCommaAtom = AtomCons.getInstance(this, ",");
        iListAtom = AtomCons.getInstance(this, "List");
        iSetAtom = AtomCons.getInstance(this, "Set");
        iProgAtom = AtomCons.getInstance(this, "Prog");

        iArgumentStack = new ArgumentStack(this, 50000 /*TODO FIXME*/);
        //org.mathpiper.builtin.Functions mc = new org.mathpiper.builtin.Functions();
        //mc.addCoreFunctions(this);

        //System.out.println("Classpath: " + System.getProperty("java.class.path"));
        
        BuiltinFunction.addCoreFunctions(this);
        List failList = BuiltinFunction.addOptionalFunctions(this, "org/mathpiper/builtin/functions/optional/");

        pushLocalFrame(true, "<START>");
    }

    public TokenMap getTokenHash() {
        return iTokenHash;
    }

    public MathPiperMap getGlobalState() {
        return iGlobalState;
    }

    public MathPiperMap getUserFunctions() {
        return iUserFunctions;
    }

    public MathPiperMap getBuiltinFunctions() {
        return iBuiltinFunctions;
    }

    public int getPrecision() {
        return iPrecision;
    }

    public void setPrecision(int aPrecision) throws Exception {
        iPrecision = aPrecision;    // getPrecision in decimal digits
    }

    public void setGlobalVariable(String aVariable, ConsPointer aValue, boolean aGlobalLazyVariable) throws Exception {
        ConsPointer localVariable = getLocalVariable(aVariable);
        if (localVariable != null) {
            localVariable.setCons(aValue.getCons());
            return;
        }
        GlobalVariable globalVariable = new GlobalVariable(this,aValue);
        iGlobalState.setAssociation(globalVariable, aVariable);
        if (aGlobalLazyVariable) {
            globalVariable.setEvalBeforeReturn(true);
        }
    }

    public void getGlobalVariable(String aVariable, ConsPointer aResult) throws Exception {
        aResult.setCons(null);
        ConsPointer localVariable = getLocalVariable(aVariable);
        if (localVariable != null) {
            aResult.setCons(localVariable.getCons());
            return;
        }
        GlobalVariable globalVariable = (GlobalVariable) iGlobalState.lookUp(aVariable);
        if (globalVariable != null) {
            if (globalVariable.iEvalBeforeReturn) {
                iLispExpressionEvaluator.evaluate(this, aResult, globalVariable.iValue);
                globalVariable.iValue.setCons(aResult.getCons());
                globalVariable.iEvalBeforeReturn = false;
                return;
            } else {
                aResult.setCons(globalVariable.iValue.getCons());
                return;
            }
        }
    }


    public ConsPointer getLocalVariable(String aVariable) throws Exception {
        LispError.check(this, iLocalVariablesFrame != null, LispError.INVALID_STACK, "INTERNAL");
        //    check(iLocalsList.iFirst != null,INVALID_STACK);
        LocalVariable localVariable = iLocalVariablesFrame.iFirst;

        while (localVariable != null) {
            if (localVariable.iVariable == aVariable) {
                return localVariable.iValue;
            }
            localVariable = localVariable.iNext;
        }
        return null;
    }//end method.



    public void unbindAllLocalVariables() throws Exception{
        LispError.check(this, iLocalVariablesFrame != null, LispError.INVALID_STACK, "INTERNAL");

        LocalVariable localVariable = iLocalVariablesFrame.iFirst;

        while (localVariable != null) {
            localVariable.iValue.setCons(null);
            localVariable = localVariable.iNext;
        }
        
    }//end method.
    public String getLocalVariables() throws Exception {
        LispError.check(this, iLocalVariablesFrame != null, LispError.INVALID_STACK, "INTERNAL");
        //    check(iLocalsList.iFirst != null,INVALID_STACK);

        LocalVariable localVariable = iLocalVariablesFrame.iFirst;

        StringBuilder localVariablesStringBuilder = new StringBuilder();

        localVariablesStringBuilder.append("Local variables: ");

        while (localVariable != null) {
            localVariablesStringBuilder.append(localVariable.iVariable);

            localVariablesStringBuilder.append(" -> ");

            String value = localVariable.iValue.toString();
            if(value != null)
            {
                localVariablesStringBuilder.append(value.trim().replace("  ","").replace("\n", "") );
            }
            else
            {
                localVariablesStringBuilder.append("unbound");
            }//end else.

            localVariablesStringBuilder.append(", ");

            localVariable = localVariable.iNext;
        }//end while.

        return localVariablesStringBuilder.toString();

    }//end method.

    public void unbindVariable(String aVariableName) throws Exception {

        if(aVariableName.equals("*"))
        {
            this.unbindAllLocalVariables();


            //Unbind global variables
            Set<String> keySet = new HashSet(iGlobalState.getMap().keySet());

            for(String key : keySet)
            {
                if(!key.startsWith("$") 
			&& !key.equals("I") 
			&& !key.equals("%") 
			&& !key.equals("LoadResult")
			&& !key.equals("geogebra"))
                {
                    //Do not unbind private variables (which are those which start with a $) or the other listed variables.
                    iGlobalState.release(key);
                }
            }
        }
        else
        {
            //Unbind local variable.
            ConsPointer localVariable = getLocalVariable(aVariableName);
            if (localVariable != null) {
                localVariable.setCons(null);
                return;
            }

            iGlobalState.release(aVariableName);
        }//end else.

    }

    public void newLocalVariable(String aVariable, Cons aValue) throws Exception {
        LispError.lispAssert(iLocalVariablesFrame != null);
        iLocalVariablesFrame.add(new LocalVariable(this, aVariable, aValue));
    }

    public void pushLocalFrame(boolean aFenced, String functionName) {
        if (aFenced) {
            LocalVariableFrame newLocalVariableFrame = new LocalVariableFrame(iLocalVariablesFrame, null, functionName);
            iLocalVariablesFrame = newLocalVariableFrame;
        } else {
            LocalVariableFrame newLocalVariableFrame = new LocalVariableFrame(iLocalVariablesFrame, iLocalVariablesFrame.iFirst, functionName);
            iLocalVariablesFrame = newLocalVariableFrame;
        }
    }

    public void popLocalFrame() throws Exception {
        LispError.lispAssert(iLocalVariablesFrame != null);
        LocalVariableFrame nextLocalVariableFrame = iLocalVariablesFrame.iNext;
        iLocalVariablesFrame.delete();
        iLocalVariablesFrame = nextLocalVariableFrame;
    }

    public int getUniqueId() {
        return iLastUniqueId++;
    }

    public void holdArgument(String aOperator, String aVariable) throws Exception {
        MultipleArityUserFunction multipleArityUserFunc = (MultipleArityUserFunction) iUserFunctions.lookUp(aOperator);
        LispError.check(this, multipleArityUserFunc != null, LispError.INVALID_ARGUMENT, "INTERNAL");
        multipleArityUserFunc.holdArgument(aVariable);
    }

    public void retractFunction(String aOperator, int aArity) throws Exception {
        MultipleArityUserFunction multipleArityUserFunc = (MultipleArityUserFunction) iUserFunctions.lookUp(aOperator);
        if (multipleArityUserFunc != null) {
            multipleArityUserFunc.deleteRulebaseEntry(aArity);
        }
    }

    public SingleArityBranchingUserFunction getUserFunction(ConsPointer aArguments) throws Exception {
        MultipleArityUserFunction multipleArityUserFunc = (MultipleArityUserFunction) iUserFunctions.lookUp( (String) aArguments.car());
        if (multipleArityUserFunc != null) {
            int arity = Utility.listLength(this, aArguments) - 1;
            return multipleArityUserFunc.getUserFunction(arity);
        }
        return null;
    }

    public SingleArityBranchingUserFunction getUserFunction(String aName, int aArity) throws Exception {
        MultipleArityUserFunction multipleArityUserFunc = (MultipleArityUserFunction) iUserFunctions.lookUp(aName);
        if (multipleArityUserFunc != null) {
            return multipleArityUserFunc.getUserFunction(aArity);
        }
        return null;
    }

    public void unFenceRule(String aOperator, int aArity) throws Exception {
        MultipleArityUserFunction multiUserFunc = (MultipleArityUserFunction) iUserFunctions.lookUp(aOperator);

        LispError.check(this, multiUserFunc != null, LispError.INVALID_ARGUMENT, "INTERNAL");
        SingleArityBranchingUserFunction userFunc = multiUserFunc.getUserFunction(aArity);
        LispError.check(this, userFunc != null, LispError.INVALID_ARGUMENT, "INTERNAL");
        userFunc.unFence();
    }

    public MultipleArityUserFunction getMultipleArityUserFunction(String aOperator, boolean create) throws Exception {
        // Find existing multiuser func.  Todo:tk:a user function name is added to the list even if a non-existing function
        // is being executed or looked for by FindFunction();
        MultipleArityUserFunction multipleArityUserFunction = (MultipleArityUserFunction) iUserFunctions.lookUp(aOperator);

        // If none exists, add one to the user functions list
        if (multipleArityUserFunction == null && create == true) {
            MultipleArityUserFunction newMultipleArityUserFunction = new MultipleArityUserFunction();
            iUserFunctions.setAssociation(newMultipleArityUserFunction, aOperator);
            multipleArityUserFunction = (MultipleArityUserFunction) iUserFunctions.lookUp(aOperator);
            LispError.check(this, multipleArityUserFunction != null, LispError.CREATING_USER_FUNCTION, "INTERNAL");
        }
        return multipleArityUserFunction;
    }

    public void declareRulebase(String aOperator, ConsPointer aParametersPointer, boolean aListed) throws Exception {
        MultipleArityUserFunction multipleArityUserFunction = getMultipleArityUserFunction(aOperator, true);

        // add an operator with this arity to the multiuserfunc.
        SingleArityBranchingUserFunction newBranchingUserFunction;
        if (aListed) {
            newBranchingUserFunction = new ListedBranchingUserFunction(this, aParametersPointer, aOperator);
        } else {
            newBranchingUserFunction = new SingleArityBranchingUserFunction(this, aParametersPointer, aOperator);
        }
        multipleArityUserFunction.addRulebaseEntry(this, newBranchingUserFunction);
    }

    public void defineRule(String aOperator, int aArity,
            int aPrecedence, ConsPointer aPredicate,
            ConsPointer aBody) throws Exception {
        // Find existing multiuser func.
        MultipleArityUserFunction multipleArityUserFunction = (MultipleArityUserFunction) iUserFunctions.lookUp(aOperator);
        LispError.check(this, multipleArityUserFunction != null, LispError.CREATING_RULE, "INTERNAL");

        // Get the specific user function with the right arity
        SingleArityBranchingUserFunction userFunction = (SingleArityBranchingUserFunction) multipleArityUserFunction.getUserFunction(aArity);
        LispError.check(this, userFunction != null, LispError.CREATING_RULE, "INTERNAL");

        // Declare a new evaluation rule
        if (Utility.isTrue(this, aPredicate)) {
            //        printf("FastPredicate on %s\n",aOperator->String());
            userFunction.declareRule(aPrecedence, aBody);
        } else {
            userFunction.declareRule(aPrecedence, aPredicate, aBody);
        }
    }

    public void declareMacroRulebase(String aFunctionName, ConsPointer aParameters, boolean aListed) throws Exception {
        MultipleArityUserFunction multipleArityUserFunc = getMultipleArityUserFunction(aFunctionName, true);

        MacroUserFunction newMacroUserFunction;

        if (aListed) {
            newMacroUserFunction = new ListedMacroUserFunction(this, aParameters, aFunctionName);
        } else {
            newMacroUserFunction = new MacroUserFunction(this, aParameters, aFunctionName);
        }
        multipleArityUserFunc.addRulebaseEntry(this, newMacroUserFunction);
    }

    public void defineRulePattern(String aOperator, int aArity, int aPrecedence, ConsPointer aPredicate, ConsPointer aBody) throws Exception {
        // Find existing multiuser func.
        MultipleArityUserFunction multipleArityUserFunc = (MultipleArityUserFunction) iUserFunctions.lookUp(aOperator);
        LispError.check(this, multipleArityUserFunc != null, LispError.CREATING_RULE, "INTERNAL");

        // Get the specific user function with the right arity
        SingleArityBranchingUserFunction userFunction = multipleArityUserFunc.getUserFunction(aArity);
        LispError.check(this, userFunction != null, LispError.CREATING_RULE, "INTERNAL");

        // Declare a new evaluation rule
        userFunction.declarePattern(aPrecedence, aPredicate, aBody);
    }

    /**
     * Write data to the current output.
     * @param aString
     * @throws java.lang.Exception
     */
    public void write(String aString) throws Exception {
        iCurrentOutput.write(aString);
    }



    public void resetArgumentStack() throws Exception
    {
        this.iArgumentStack.reset();
    }//end method.




}//end class.

