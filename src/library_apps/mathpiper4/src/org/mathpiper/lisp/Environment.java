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
 *///}}}

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
package org.mathpiper.lisp;

import org.mathpiper.lisp.variables.GlobalVariable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.mathpiper.Scripts;
import org.mathpiper.lisp.stacks.ArgumentStack;
import org.mathpiper.lisp.collections.OperatorMap;
import org.mathpiper.lisp.cons.AtomCons;

import org.mathpiper.lisp.printers.LispPrinter;
import org.mathpiper.io.MathPiperInputStream;
import org.mathpiper.io.MathPiperOutputStream;
import org.mathpiper.lisp.tokenizers.XmlTokenizer;
import org.mathpiper.io.InputStatus;

import org.mathpiper.io.InputDirectories;
import org.mathpiper.io.StringInputStream;

import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;

import org.mathpiper.lisp.rulebases.MultipleArityRulebase;

import org.mathpiper.lisp.rulebases.MacroRulebase;

import org.mathpiper.lisp.rulebases.ListedRulebase;

import org.mathpiper.lisp.rulebases.SingleArityRulebase;

import org.mathpiper.lisp.rulebases.ListedMacroRulebase;

import org.mathpiper.lisp.printers.MathPiperPrinter;

import org.mathpiper.lisp.variables.LocalVariable;
import org.mathpiper.lisp.variables.LocalVariableFrame;

public final class Environment {

    public ArgumentStack iArgumentStack;
    public Evaluator iLispExpressionEvaluator = new LispExpressionEvaluator();
    public int iPrecision = 10;
    public Cons iTrueAtom;
    public final String iTrueString;
    public Cons iFalseAtom;
    public Cons iListAtom;
    public final String iFalseString;
    public Cons iEndOfFileAtom;
    public String iEndStatementAtom;
    public String iBlockOpenAtom;
    public String iBlockCloseAtom;
    public String iIndexOrNameOpenAtom;
    public String iIndexOrNameCloseAtom;
    public String iNthAtom;
    public String iComplexAtom;
    public String iBracketOpenAtom;
    public String iBracketCloseAtom;
    public String iListOpenAtom;
    public String iListCloseAtom;
    public String iCommaAtom;
    public String iSetAtom;
    public String iBlockAtom;
    public OperatorMap iPrefixOperators = new OperatorMap(this);
    public OperatorMap iInfixOperators = new OperatorMap(this);
    public OperatorMap iPostfixOperators = new OperatorMap(this);
    public OperatorMap iBodiedOperators = new OperatorMap(this);
    public volatile int iEvalDepth = 0;
    public int iMaxEvalDepth = 10000;
    // TODO FIXME
    public LocalVariableFrame iLocalVariablesFrame;
    public boolean iSecure = false;
    public int iLastUniqueId = 1;
    public MathPiperOutputStream iCurrentOutput = null;
    public MathPiperOutputStream iInitialOutput = null;
    public LispPrinter iCurrentPrinter = null;
    private MathPiperInputStream iCurrentInput = null;
    public InputStatus iInputStatus = new InputStatus("ENVIRONMENT_1");
    public MathPiperTokenizer iCurrentTokenizer;
    public MathPiperTokenizer iDefaultTokenizer = new MathPiperTokenizer();
    public MathPiperTokenizer iXmlTokenizer = new XmlTokenizer();
    public Map<String, GlobalVariable> iGlobalState = new HashMap<String, GlobalVariable>();
    public Map iUserRulebases = new HashMap();
    public Map iBuiltinFunctions = new HashMap();
    public Throwable iException = null;
    public InputDirectories iInputDirectories = new InputDirectories();
    public String iPrettyReaderName = null;
    public String iPrettyPrinterName = null;
    public Scripts scripts = new Scripts();
    public static boolean haltEvaluation = false;
    public static String haltEvaluationMessage = "";
    public static boolean saveDebugInformation = false;

    public Environment(MathPiperOutputStream aCurrentOutput/* TODO FIXME */)
	    throws Throwable {
	
	iPrettyReaderName = "ParseMathPiper";
	
	iCurrentTokenizer = iDefaultTokenizer;
	iInitialOutput = aCurrentOutput;
	iCurrentOutput = aCurrentOutput;

	/*
	 * Assign a default input stream to iInputStream so that various places
	 * in the code that evaluate expressions without using a stream do not
	 * cause code that checks for line numbers to throw a null pointer
	 * exception.
	 */
	InputStatus status = new InputStatus("ENVIRONMENT_2");
	StringInputStream defaultInputStream = new StringInputStream("", status);
	this.iCurrentInput = defaultInputStream;

	iCurrentPrinter = new MathPiperPrinter(iPrefixOperators,
		iInfixOperators, iPostfixOperators, iBodiedOperators);

	iListAtom = new AtomCons("List");
	iTrueAtom = new AtomCons("True");
	iTrueString = (String) iTrueAtom.car();
	iFalseAtom = new AtomCons("False");
	iFalseString = (String) iFalseAtom.car();

	iEndOfFileAtom = new AtomCons("EndOfFile");
	iEndStatementAtom = ";";
	iBlockOpenAtom = ("{");
	iBlockCloseAtom = ("}");
	iIndexOrNameOpenAtom = ("[");
	iIndexOrNameCloseAtom = ("]");
	iNthAtom = ("Nth");
	iComplexAtom = ("Complex");
	iBracketOpenAtom = ("(");
	iBracketCloseAtom = (")");
	iListOpenAtom = ("[");
	iListCloseAtom = ("]");
	iCommaAtom = (",");
	iSetAtom = ("Set");
	iBlockAtom = ("Block");

	iArgumentStack = new ArgumentStack(this, 50000 /* TODO FIXME */);
	// org.mathpiper.builtin.Functions mc = new
	// org.mathpiper.builtin.Functions();
	// mc.addCoreFunctions(this);

	// System.out.println("Classpath: " +
	// System.getProperty("java.class.path"));

    }

    public Map<String, GlobalVariable> getGlobalState() {
	return iGlobalState;
    }

    public Map getUserFunctions() {
	return iUserRulebases;
    }

    public Map getBuiltinFunctions() {
	return iBuiltinFunctions;
    }

    public int getPrecision() {
	return iPrecision;
    }

    public void setPrecision(int aPrecision) throws Throwable {
	iPrecision = aPrecision; // getPrecision in decimal digits
    }

    public void setLocalOrGlobalVariable(int aStackTop, String aVariable,
	    Cons aValue, boolean aGlobalLazyVariable, boolean constant)
	    throws Throwable {
	
	LocalVariable localVariable = getLocalVariable(aStackTop, aVariable);
	
	if (localVariable != null) {
	    localVariable.iValue = aValue;
	    return;
	}

	Object object = iGlobalState.get(aVariable);

	if (object == null) {

	    GlobalVariable globalVariable = new GlobalVariable(aValue);

	    globalVariable.iConstant = constant;

	    iGlobalState.put(aVariable, globalVariable);

	    if (aGlobalLazyVariable) {
		globalVariable.iEvalBeforeReturn = true;
	    }
	    
	} else {
	    GlobalVariable globalVariable = (GlobalVariable) object;
	    
	    if(globalVariable.iConstant == false)
	    {
		globalVariable.iValue = aValue;
	    }
	    else
	    {
		LispError.throwError(this, aStackTop, aVariable + " is a constant, and constants cannot be reassigned.");
	    }
	}
    }

    public Cons getLocalOrGlobalVariable(int aStackTop, String aVariable)
	    throws Throwable {
	Cons aResult;
	LocalVariable localVariable = getLocalVariable(aStackTop, aVariable);
	if (localVariable != null) {
	    aResult = localVariable.iValue;
	    return aResult;
	}

	aResult = getGlobalVariable(aStackTop, aVariable);

	return aResult;
    }

    public Cons getGlobalVariable(int aStackTop, String aVariable)
	    throws Throwable {

	Cons aResult;
	GlobalVariable globalVariable = (GlobalVariable) iGlobalState
		.get(aVariable);
	if (globalVariable != null) {
	    if (globalVariable.iEvalBeforeReturn) {
		aResult = iLispExpressionEvaluator.evaluate(this, aStackTop,
			globalVariable.iValue);
		globalVariable.iValue = aResult;
		globalVariable.iEvalBeforeReturn = false;
		return aResult;
	    } else {
		aResult = globalVariable.iValue;
		return aResult;
	    }
	}
	
	//if(!aVariable.startsWith("\""))
	//{
	//    System.out.println(aVariable);
	//}

	return null;

    }

    public LocalVariable getLocalVariable(int aStackTop, String aVariable)
	    throws Throwable {
	if (iLocalVariablesFrame == null)
	    LispError.throwError(this, aStackTop, LispError.INVALID_STACK, "");
	// check(iLocalsList.iFirst != null,INVALID_STACK);
	LocalVariable localVariable = iLocalVariablesFrame.iFirst;

	while (localVariable != null) {
	    if (localVariable.iVariable.equals(aVariable)) {
		return localVariable;
	    }
	    localVariable = localVariable.iNext;
	}
	return null;
    }// end method.

    public void unbindAllLocalVariables(int aStackTop) throws Throwable {
	if (iLocalVariablesFrame == null)
	    LispError.throwError(this, aStackTop, LispError.INVALID_STACK, "");

	LocalVariable localVariable = iLocalVariablesFrame.iFirst;

	while (localVariable != null) {
	    localVariable.iValue = null;
	    localVariable = localVariable.iNext;
	}

    }// end method.

    public String getLocalVariables(int aStackTop) throws Throwable {
	if (iLocalVariablesFrame == null) {
	    // LispError.throwError(this, aStackTop, LispError.INVALID_STACK,
	    // "", "INTERNAL"); todo:tk:this error was probably introduced
	    // within a month or two of 10/17/2011.
	    return ("***( NO LOCAL VARIABLES STACK FRAME )***");

	}
	// check(iLocalsList.iFirst != null,INVALID_STACK);

	LocalVariable localVariable = iLocalVariablesFrame.iFirst;

	StringBuilder localVariablesStringBuilder = new StringBuilder();

	localVariablesStringBuilder.append("Local variables: ");

	while (localVariable != null) {
	    localVariablesStringBuilder.append(localVariable.iVariable);

	    localVariablesStringBuilder.append(" -> ");

	    if (localVariable.iValue != null) {
		localVariablesStringBuilder.append(localVariable.iValue
			.toString().trim().replace("  ", "").replace("\n", ""));
	    } else {
		localVariablesStringBuilder.append("unbound");
	    }// end else.

	    localVariablesStringBuilder.append(", ");

	    localVariable = localVariable.iNext;
	}// end while.

	return localVariablesStringBuilder.toString();

    }// end method.

    public String dumpLocalVariablesFrame(int aStackTop) throws Throwable {

	if (iLocalVariablesFrame == null)
	    LispError.throwError(this, aStackTop, LispError.INVALID_STACK, "");

	LocalVariableFrame localVariableFrame = iLocalVariablesFrame;

	StringBuilder stringBuilder = new StringBuilder();

	int functionPositionIndex = 0;

	// int functionBaseIndex = 0;

	while (localVariableFrame != null) {

	    String functionName = localVariableFrame.getFunctionName();

	    if (functionPositionIndex == 0) {
		stringBuilder
			.append("\n\n========================================= Start Of User Function Stack Trace\n");
	    } else {
		stringBuilder
			.append("-----------------------------------------\n");
	    }

	    stringBuilder.append(functionPositionIndex++ + ": ");
	    stringBuilder.append(functionName);
	    stringBuilder.append("\n");

	    LocalVariable localVariable = localVariableFrame.iFirst;

	    // stringBuilder.append("Local variables: ");

	    while (localVariable != null) {

		stringBuilder.append("   " + functionPositionIndex++ + ": -> ");

		stringBuilder.append(localVariable.iVariable);

		stringBuilder.append(" = ");

		Cons value = localVariable.iValue;

		String valueString = Utility.printMathPiperExpression(
			aStackTop, value, this, -1);

		stringBuilder.append(valueString);

		stringBuilder.append("\n");

		/*
		 * if(value != null) {
		 * localVariablesStringBuilder.append(value.trim
		 * ().replace("  ","").replace("\n", "") ); } else {
		 * localVariablesStringBuilder.append("unbound"); }//end else.
		 * 
		 * 
		 * localVariablesStringBuilder.append(", ");
		 */

		localVariable = localVariable.iNext;
	    }// end while.

	    localVariableFrame = localVariableFrame.iNext;

	}// end while

	stringBuilder
		.append("========================================= End Of User Function Stack Trace\n\n");

	return stringBuilder.toString();

	/*
	 * StringBuilder stringBuilder = new StringBuilder();
	 * 
	 * int functionBaseIndex = 0;
	 * 
	 * int functionPositionIndex = 0;
	 * 
	 * 
	 * while (functionBaseIndex <= aStackTop) {
	 * 
	 * if(functionBaseIndex == 0) { stringBuilder.append(
	 * "\n\n========================================= Start Of Stack Trace\n"
	 * ); } else {
	 * stringBuilder.append("-----------------------------------------\n");
	 * }
	 * 
	 * ConsPointer consPointer = getElement(functionBaseIndex, aStackTop,
	 * aEnvironment);
	 * 
	 * int argumentCount = Utility.listLength(aEnvironment, aStackTop,
	 * consPointer);
	 * 
	 * ConsPointer argumentPointer = new ConsPointer();
	 * 
	 * Object car = consPointer.getCons().car();
	 * 
	 * ConsPointer consTraverser = new ConsPointer( consPointer.getCons());
	 * 
	 * stringBuilder.append(functionPositionIndex++ + ": ");
	 * stringBuilder.append(Utility.printMathPiperExpression(aStackTop,
	 * consTraverser, aEnvironment, -1)); stringBuilder.append("\n");
	 * 
	 * consTraverser.goNext(aStackTop, aEnvironment);
	 * 
	 * while(consTraverser.getCons() != null) { stringBuilder.append("   " +
	 * functionPositionIndex++ + ": "); stringBuilder.append("-> " +
	 * Utility.printMathPiperExpression(aStackTop, consTraverser,
	 * aEnvironment, -1)); stringBuilder.append("\n");
	 * 
	 * consTraverser.goNext(aStackTop, aEnvironment); }
	 * 
	 * 
	 * functionBaseIndex = functionBaseIndex + argumentCount;
	 * 
	 * }//end while.
	 * 
	 * stringBuilder.append(
	 * "========================================= End Of User Function Stack Trace\n\n"
	 * );
	 * 
	 * return stringBuilder.toString();
	 */

    }// end method.

    // Dumps the built-in and user stacks.
    public String dumpStacks(Environment aEnvironment, int aStackTop)
	    throws Throwable {
	String dump = aEnvironment.iArgumentStack.dump(aStackTop, aEnvironment)
		+

		"****** THE PROBLEM IS EITHER IMMEDIATELY ABOVE THIS LINE OR IMMEDIATELY BELOW THIS LINE ******"
		+

		aEnvironment.dumpLocalVariablesFrame(aStackTop);

	return dump;

    }

    public void unbindVariable(int aStackTop, String aVariableName)
	    throws Throwable {

	if (aVariableName.equals("*")) {
	    this.unbindAllLocalVariables(aStackTop);

	    // Unassign global variables
	    Set<String> keySet = new HashSet(iGlobalState.keySet());

	    for (String key : keySet) {
		if (!key.startsWith("$") && !key.equals("I")
			&& !key.equals("%") && !key.equals("geogebra")) {
		    // Do not unassign private variables (which are those which
		    // start with a $) or the other listed variables.
		    iGlobalState.remove(key);
		}
	    }
	} else {
	    // Unassign local variable.
	    LocalVariable localVariable = getLocalVariable(aStackTop,
		    aVariableName);
	    if (localVariable != null) {
		localVariable.iValue = null;
		return;
	    }

	    iGlobalState.remove(aVariableName);
	}// end else.

    }

    public void newLocalVariable(String aVariable, Cons aValue, int aStackTop)
	    throws Throwable {
	if (iLocalVariablesFrame == null)
	    LispError.lispAssert(this, aStackTop);
	iLocalVariablesFrame.add(new LocalVariable(this, aVariable, aValue));
    }

    public void pushLocalFrame(boolean aFenced, String functionName) {
	if (aFenced) {
	    LocalVariableFrame newLocalVariableFrame = new LocalVariableFrame(
		    iLocalVariablesFrame, null, functionName);
	    iLocalVariablesFrame = newLocalVariableFrame;
	} else {
	    LocalVariableFrame newLocalVariableFrame = new LocalVariableFrame(
		    iLocalVariablesFrame, iLocalVariablesFrame.iFirst,
		    functionName);
	    iLocalVariablesFrame = newLocalVariableFrame;
	}
    }

    public void popLocalFrame(int aStackTop) throws Throwable {
	if (iLocalVariablesFrame == null)
	    LispError.lispAssert(this, aStackTop);
	LocalVariableFrame nextLocalVariableFrame = iLocalVariablesFrame.iNext;
	iLocalVariablesFrame.delete();
	iLocalVariablesFrame = nextLocalVariableFrame;
    }

    public int getUniqueId() {
	return iLastUniqueId++;
    }

    public void holdArgument(int aStackTop, String aOperator, String aVariable,
	    Environment aEnvironment) throws Throwable {
	MultipleArityRulebase multipleArityUserFunc = (MultipleArityRulebase) iUserRulebases
		.get(aOperator);
	if (multipleArityUserFunc == null)
	    LispError.throwError(this, aStackTop, LispError.INVALID_ARGUMENT,
		    aOperator);
	multipleArityUserFunc.holdArgument(aVariable, aStackTop, aEnvironment);
    }

    public void retractRule(String aOperator, int aArity, int aStackTop,
	    Environment aEnvironment) throws Throwable {
	MultipleArityRulebase multipleArityUserFunc = (MultipleArityRulebase) iUserRulebases
		.get(aOperator);

	if (multipleArityUserFunc != null) {
	    multipleArityUserFunc.deleteRulebaseEntry(aArity, aStackTop,
		    aEnvironment);
	}
    }

    public SingleArityRulebase getRulebase(int aStackTop, Cons aArguments)
	    throws Throwable {
	MultipleArityRulebase multipleArityUserFunc = (MultipleArityRulebase) iUserRulebases
		.get((String) aArguments.car());
	if (multipleArityUserFunc != null) {
	    int arity = Utility.listLength(this, aStackTop, aArguments) - 1;
	    return multipleArityUserFunc
		    .getUserFunction(arity, aStackTop, this);
	}
	return null;
    }

    public SingleArityRulebase getRulebase(String aName, int aArity,
	    int aStackTop) throws Throwable {
	MultipleArityRulebase multipleArityUserFunc = (MultipleArityRulebase) iUserRulebases
		.get(aName);
	if (multipleArityUserFunc != null) {
	    return multipleArityUserFunc.getUserFunction(aArity, aStackTop,
		    this);
	}
	return null;
    }

    public void unfenceRule(int aStackTop, String aOperator, int aArity)
	    throws Throwable {
	MultipleArityRulebase multiUserFunc = (MultipleArityRulebase) iUserRulebases
		.get(aOperator);

	if (multiUserFunc == null)
	    LispError.throwError(this, aStackTop, LispError.INVALID_ARGUMENT,
		    aOperator);
	SingleArityRulebase userFunc = multiUserFunc.getUserFunction(aArity,
		aStackTop, this);
	if (userFunc == null)
	    LispError.throwError(this, aStackTop, LispError.INVALID_ARGUMENT,
		    aOperator);
	userFunc.unFence();
    }

    public MultipleArityRulebase getMultipleArityRulebase(int aStackTop,
	    String aOperator, boolean create) throws Throwable {
	// Find existing multiuser func. Todo:tk:a user function name is added
	// to the list even if a non-existing function
	// is being executed or looked for by FindFunction();
	MultipleArityRulebase multipleArityUserRulebase = (MultipleArityRulebase) iUserRulebases
		.get(aOperator);

	// If none exists, add one to the user rulebases list
	if (multipleArityUserRulebase == null && create == true) {
	    multipleArityUserRulebase = new MultipleArityRulebase();
	    multipleArityUserRulebase.functionName = aOperator;
	    iUserRulebases.put(aOperator, multipleArityUserRulebase);

	}
	return multipleArityUserRulebase;
    }

    public void defineRulebase(int aStackTop, String aOperator,
	    Cons aParameters, boolean aListed) throws Throwable {

	MultipleArityRulebase multipleArityUserFunction = getMultipleArityRulebase(
		aStackTop, aOperator, true);

	// add an operator with this arity to the multiuserfunc.
	SingleArityRulebase newBranchingRulebase;
	if (aListed) {
	    newBranchingRulebase = new ListedRulebase(this, aStackTop,
		    aParameters, aOperator);
	} else {
	    newBranchingRulebase = new SingleArityRulebase(this, aStackTop,
		    aParameters, aOperator);
	}
	multipleArityUserFunction.addRulebaseEntry(this, aStackTop,
		newBranchingRulebase);
    }

    public void defineRule(int aStackTop, String aOperator, int aArity,
	    int aPrecedence, Cons aPredicate, Cons aBody) throws Throwable {
	// Find existing multiuser rule.
	MultipleArityRulebase multipleArityRulebase = (MultipleArityRulebase) iUserRulebases
		.get(aOperator);
	if (multipleArityRulebase == null)
	    LispError.throwError(this, aStackTop, LispError.CREATING_RULE,
		    aOperator);

	// Get the specific user function with the right arity
	SingleArityRulebase rulebase = (SingleArityRulebase) multipleArityRulebase
		.getUserFunction(aArity, aStackTop, this);
	if (rulebase == null)
	    LispError.throwError(this, aStackTop, LispError.CREATING_RULE,
		    aOperator);

	// Declare a new evaluation rule
	if (Utility.isTrue(this, aPredicate, aStackTop)) {
	    // printf("FastPredicate on %s\n",aOperator->String());
	    rulebase.defineTrueRule(aStackTop, aPrecedence, aBody);
	} else {
	    rulebase.definePredicateRule(aStackTop, aPrecedence, aPredicate,
		    aBody);
	}
    }

    public void defineMacroRulebase(int aStackTop, String aFunctionName,
	    Cons aParameters, boolean aListed) throws Throwable {

	MultipleArityRulebase multipleArityRulebase = getMultipleArityRulebase(
		aStackTop, aFunctionName, true);

	MacroRulebase newMacroRulebase;

	if (aListed) {
	    newMacroRulebase = new ListedMacroRulebase(this, aStackTop,
		    aParameters, aFunctionName);
	} else {
	    newMacroRulebase = new MacroRulebase(this, aStackTop, aParameters,
		    aFunctionName);
	}
	multipleArityRulebase.addRulebaseEntry(this, aStackTop,
		newMacroRulebase);
    }

    public void definePatternRule(int aStackTop, String aOperator, int aArity,
	    int aPrecedence, Cons aPredicate, Cons aBody) throws Throwable {
	// Find existing multiuser rulebase.
	MultipleArityRulebase multipleArityRulebase = (MultipleArityRulebase) iUserRulebases
		.get(aOperator);
	if (multipleArityRulebase == null)
	    LispError.throwError(this, aStackTop, LispError.CREATING_RULE,
		    aOperator);

	// Get the specific user function with the right arity
	SingleArityRulebase rulebase = multipleArityRulebase.getUserFunction(
		aArity, aStackTop, this);
	if (rulebase == null) {
	    LispError.throwError(this, aStackTop, LispError.CREATING_RULE,
		    aOperator);
	}

	// Declare a new evaluation rule
	rulebase.definePatternRule(aStackTop, aPrecedence, aPredicate, aBody);
    }

    /**
     * Write data to the current output.
     * 
     * @param aString
     * @throws java.lang.Exception
     */
    public void write(String aString) throws Throwable {
	iCurrentOutput.write(aString);
    }

    /**
     * @return the iCurrentInput
     */
    public MathPiperInputStream getCurrentInput() {
	return iCurrentInput;
    }

    /**
     * @param iCurrentInput
     *            the iCurrentInput to set
     */
    public void setCurrentInput(MathPiperInputStream iCurrentInput) {
	this.iCurrentInput = iCurrentInput;
    }

    public void resetArgumentStack(int aStackTop) throws Throwable {
	this.iArgumentStack.reset(aStackTop, this);
    }// end method.

}// end class.

