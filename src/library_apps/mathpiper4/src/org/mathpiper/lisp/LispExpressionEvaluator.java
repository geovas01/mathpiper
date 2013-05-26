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

import java.util.Map;

import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;

import org.mathpiper.lisp.rulebases.SingleArityRulebase;

/**
 * The basic evaluator for Lisp expressions.
 * 
 */
public class LispExpressionEvaluator extends Evaluator {

	/**
	 * <p>
	 * First, the evaluation depth is checked. An error is raised if the maximum
	 * evaluation depth is exceeded. The next step is the actual evaluation.
	 * aExpression is a Cons, so we can distinguish three cases:
	 * </p>
	 * <ol>
	 * <li value="1">
	 * <p>
	 * If aExpression is a string starting with " , it is simply copied in
	 * aResult. If it starts with another character (this includes the case
	 * where it represents a number), the environment is checked to see whether
	 * a variable with this name exists. If it does, its value is copied in
	 * aResult, otherwise aExpression is copied.
	 * </p>
	 * 
	 * <li value="2">
	 * <p>
	 * If aExpression is a list, the head of the list is examined. If the head
	 * is not a string. ApplyFast() is called. If the head is a string, it is
	 * checked against the core commands (if there is a check, the corresponding
	 * evaluator is called). Then it is checked against the list of user
	 * function with getRulebase(). Again, the corresponding evaluator is called
	 * if there is a check. If all fails, ReturnUnEvaluated() is called.
	 * </p>
	 * 
	 * <li value="3">
	 * <p>
	 * Otherwise (i.e. if aExpression is a getJavaObject object), it is copied
	 * in aResult.
	 * </p>
	 * </ol>
	 * 
	 * <p>
	 * Note: The result of this operation must be a unique (copied) element! Eg.
	 * its Next might be set...
	 * </p>
	 * 
	 * @param aEnvironment
	 *            the Lisp environment, in which the evaluation should take
	 *            place
	 * @param aResult
	 *            the result of the evaluation
	 * @param aExpression
	 *            the expression to evaluate
	 * @throws java.lang.Exception
	 */
	public Cons evaluate(Environment aEnvironment, int aStackTop,
			Cons aExpression) throws Throwable {

		if (aExpression == null) {
			LispError.lispAssert(aEnvironment, aStackTop);
		}

		aEnvironment.iEvalDepth++;
		if (aEnvironment.iEvalDepth >= aEnvironment.iMaxEvalDepth) {

			if (aEnvironment.iEvalDepth >= aEnvironment.iMaxEvalDepth)
				LispError.throwError(aEnvironment, aStackTop,
						LispError.MAXIMUM_RECURSE_DEPTH_REACHED,
						"Maximum recursed depth set to "
								+ aEnvironment.iMaxEvalDepth + ".");
			// }
		}

		if (Environment.haltEvaluation == true) {
			Environment.haltEvaluation = false;

			LispError.raiseError(Environment.haltEvaluationMessage,
					aEnvironment.getCurrentInput().iStatus.getLineNumber(), -1,
					aEnvironment.getCurrentInput().iStatus.getLineIndex(),
					aStackTop, aEnvironment);
		}

		// evaluate an atom: find the bound value (treat it as a variable)
		if (aExpression.car() instanceof String) {

			String atomName = (String) aExpression.car();

			if (atomName.charAt(0) == '\"') {
				// Handle string atoms.
				aEnvironment.iEvalDepth--;
				return aExpression.copy(false);
			}

			if (Character.isDigit(atomName.charAt(0))) {
				// Handle number atoms.
				aEnvironment.iEvalDepth--;
				return aExpression.copy(false);
			}

			Cons val = aEnvironment.getLocalOrGlobalVariable(aStackTop,
					atomName);
			if (val != null) {
				aEnvironment.iEvalDepth--;
				return val.copy(false);
			}

			/*
			 * Map metaDataMap = aExpression.getCons().getMetadataMap(); int
			 * lineNumber = aEnvironment.iCurrentInput.iStatus.getLineNumber();
			 * int startIndex = -1; int endIndex =
			 * aEnvironment.iCurrentInput.iStatus.getLineIndex(); if
			 * (metaDataMap != null) { lineNumber = (Integer)
			 * metaDataMap.get("lineNumber"); startIndex = (Integer)
			 * metaDataMap.get("startIndex"); endIndex = (Integer)
			 * metaDataMap.get("endIndex"); }
			 * LispError.raiseError("Problem with atom ***(" + atomName +
			 * ")***, <wrong code: " + Utility.printLispExpression(-1,
			 * aExpression, aEnvironment, 50) + ">,", "[Internal]", lineNumber,
			 * startIndex, endIndex, aStackTop, aEnvironment);
			 */

			// Handle unbound variables.

			aEnvironment.iEvalDepth--;
			return aExpression.copy(false);

		}

		if (aExpression.car() instanceof Cons) {
			Cons functionAndArgumentsList = (Cons) aExpression.car();
			Cons head = functionAndArgumentsList;
			if (head != null) {

				String functionName;

				if (head.car() instanceof String) {

					functionName = (String) head.car();

					// Built-in function handler.
					BuiltinFunctionEvaluator builtinInFunctionEvaluator = (BuiltinFunctionEvaluator) aEnvironment
							.getBuiltinFunctions().get(functionName);
					if (builtinInFunctionEvaluator != null) {

						aEnvironment.iEvalDepth--;
						return builtinInFunctionEvaluator.evaluate(
								aEnvironment, aStackTop,
								functionAndArgumentsList);
					}

					// User function handler.
					SingleArityRulebase userFunction;
					userFunction = getUserFunction(aEnvironment, aStackTop,
							functionAndArgumentsList);
					if (userFunction != null) {

						aEnvironment.iEvalDepth--;
						return userFunction.evaluate(aEnvironment, aStackTop,
								functionAndArgumentsList);
					}

					if (functionName.equals("FreeOf?")) {

						aEnvironment.iEvalDepth--;

						return Utility.returnUnEvaluated(aStackTop,
								functionAndArgumentsList, aEnvironment);
					}
					Map metaDataMap = functionAndArgumentsList.getMetadataMap();

					int lineNumber = aEnvironment.getCurrentInput().iStatus
							.getLineNumber();
					int startIndex = -1;
					int endIndex = aEnvironment.getCurrentInput().iStatus
							.getLineIndex();

					if (metaDataMap != null) {
						lineNumber = (Integer) metaDataMap.get("lineNumber");
						startIndex = (Integer) metaDataMap.get("startIndex");
						endIndex = (Integer) metaDataMap.get("endIndex");
					}

					LispError
							.raiseError(
									"Problem with function ***("
											+ functionName
											+ ")***, <wrong code: "
											+ Utility.printLispExpression(-1,
													functionAndArgumentsList,
													aEnvironment, 50)
											+ ">, <the "
											+ (Utility.listLength(aEnvironment,
													aStackTop,
													functionAndArgumentsList) - 1)
											+ " parameter version of this function is not defined (MAKE SURE THE FUNCTION IS SPELLED CORRECTLY).>",
									lineNumber, startIndex, endIndex,
									aStackTop, aEnvironment);

				} else {
					// Pure function handler.
					Cons operator = functionAndArgumentsList;
					Cons args2 = functionAndArgumentsList.cdr();

					aEnvironment.iEvalDepth--;
					return Utility.applyPure(aStackTop, operator, args2,
							aEnvironment);
				}
			}
		}

		aEnvironment.iEvalDepth--;

		return aExpression.copy(false);
	}

	SingleArityRulebase getUserFunction(Environment aEnvironment,
			int aStackTop, Cons subList) throws Throwable {
		Cons head = subList;

		if (!(head.car() instanceof String))
			LispError.throwError(aEnvironment, aStackTop,
					"No function name specified.");

		String functionName = (String) head.car();

		SingleArityRulebase userFunc = (SingleArityRulebase) aEnvironment
				.getRulebase(aStackTop, subList);

		// if (userFunc == null && functionName.equals("f")) {
		// int xx = 1;
		// }

		if (userFunc != null) {
			return userFunc;

		} else {

			// System.out.println(functionName);

			Utility.loadLibraryFunction(functionName, aEnvironment, aStackTop);

			userFunc = (SingleArityRulebase) aEnvironment.getRulebase(
					aStackTop, subList);

		}

		return userFunc;

	}// end method.
	/*
	 * void TracedStackEvaluator::PushFrame() { UserStackInformation *op = NEW
	 * UserStackInformation; objs.Append(op); }
	 * 
	 * void TracedStackEvaluator::PopFrame() { LISPASSERT (objs.Size() > 0); if
	 * (objs[objs.Size()-1]) { delete objs[objs.Size()-1]; objs[objs.Size()-1] =
	 * null; } objs.Delete(objs.Size()-1); }
	 * 
	 * void TracedStackEvaluator::ResetStack() { while (objs.Size()>0) {
	 * PopFrame(); } }
	 * 
	 * UserStackInformation& TracedStackEvaluator::StackInformation() { return
	 * *(objs[objs.Size()-1]); }
	 * 
	 * TracedStackEvaluator::~TracedStackEvaluator() { ResetStack(); }
	 * 
	 * void TracedStackEvaluator::ShowStack(Environment aEnvironment,
	 * LispOutput& aOutput) { LispLocalEvaluator local(aEnvironment,NEW
	 * BasicEvaluator);
	 * 
	 * LispInt i; LispInt from=0; LispInt upto = objs.Size();
	 * 
	 * for (i=from;i<upto;i++) { LispChar str[20]; #ifdef YACAS_DEBUG
	 * aEnvironment.write(objs[i].iFileName); aEnvironment.write("(");
	 * InternalIntToAscii(str,objs[i].iLine); aEnvironment.write(str);
	 * aEnvironment.write(") : "); aEnvironment.write("Debug> "); #endif
	 * InternalIntToAscii(str,i); aEnvironment.write(str);
	 * aEnvironment.write(": ");
	 * aEnvironment.CurrentPrinter().Print(objs[i].iOperator,
	 * *aEnvironment.CurrentOutput(),aEnvironment);
	 * 
	 * LispInt internal; internal = (null !=
	 * aEnvironment.CoreCommands().LookUp(objs[i].iOperator.String())); if
	 * (internal) { aEnvironment.write(" (Internal function) "); } else { if
	 * (objs[i].iRulePrecedence>=0) { aEnvironment.write(" (Rule # ");
	 * InternalIntToAscii(str,objs[i].iRulePrecedence); aEnvironment.write(str);
	 * if (objs[i].iSide) aEnvironment.write(" in body) "); else
	 * aEnvironment.write(" in pattern) "); } else
	 * aEnvironment.write(" (User function) "); } if (!!objs[i].iExpression) {
	 * aEnvironment.write("\n      "); if
	 * (aEnvironment.iEvalDepth>(aEnvironment.iMaxEvalDepth-10)) { LispString
	 * expr; PrintExpression(expr, objs[i].iExpression,aEnvironment,60);
	 * aEnvironment.write(expr.c_str()); } else { LispPtr getSubList =
	 * objs[i].iExpression.SubList(); if (!!getSubList && !!getSubList) {
	 * LispString expr; LispPtr out(objs[i].iExpression); PrintExpression(expr,
	 * out,aEnvironment,60); aEnvironment.write(expr.c_str()); } } }
	 * aEnvironment.write("\n"); } }
	 * 
	 * void TracedStackEvaluator::Eval(Environment aEnvironment, ConsPointer
	 * aResult, ConsPointer aExpression) { if
	 * (aEnvironment.iEvalDepth>=aEnvironment.iMaxEvalDepth) {
	 * ShowStack(aEnvironment, *aEnvironment.CurrentOutput());
	 * CHK2(aEnvironment.iEvalDepth<aEnvironment.iMaxEvalDepth,
	 * MAXIMUM_RECURSE_DEPTH_REACHED); }
	 * 
	 * LispPtr getSubList = aExpression.SubList(); LispString * str = null; if
	 * (getSubList) { Cons head = getSubList; if (head) { str = head.String();
	 * if (str) { PushFrame(); UserStackInformation& st = StackInformation();
	 * st.iOperator = (LispAtom::New(aEnvironment,str.c_str())); st.iExpression
	 * = (aExpression); #ifdef YACAS_DEBUG if (aExpression.iFileName) {
	 * st.iFileName = aExpression.iFileName; st.iLine = aExpression.iLine; }
	 * #endif } } } BasicEvaluator::Eval(aEnvironment, aResult, aExpression); if
	 * (str) { PopFrame(); } }
	 * 
	 * void TracedEvaluator::Eval(Environment aEnvironment, ConsPointer aResult,
	 * ConsPointer aExpression) { if(!aEnvironment.iDebugger)
	 * RaiseError("Internal error: debugging failing");
	 * if(aEnvironment.iDebugger.Stopped()) RaiseError("");
	 * 
	 * REENTER: errorStr.ResizeTo(1); errorStr[0] = '\0';
	 * LispTrap(aEnvironment.iDebugger.Enter(aEnvironment,
	 * aExpression),errorOutput,aEnvironment);
	 * if(aEnvironment.iDebugger.Stopped()) RaiseError(""); if (errorStr[0]) {
	 * aEnvironment.write(errorStr.c_str()); aEnvironment.iEvalDepth=0; goto
	 * REENTER; }
	 * 
	 * errorStr.ResizeTo(1); errorStr[0] = '\0';
	 * LispTrap(BasicEvaluator::Eval(aEnvironment, aResult,
	 * aExpression),errorOutput,aEnvironment);
	 * 
	 * if (errorStr[0]) { aEnvironment.write(errorStr.c_str());
	 * aEnvironment.iEvalDepth=0; aEnvironment.iDebugger.Error(aEnvironment);
	 * goto REENTER; }
	 * 
	 * if(aEnvironment.iDebugger.Stopped()) RaiseError("");
	 * 
	 * aEnvironment.iDebugger.Leave(aEnvironment, aResult, aExpression);
	 * if(aEnvironment.iDebugger.Stopped()) RaiseError(""); }
	 * 
	 * YacasDebuggerBase::~YacasDebuggerBase() { }
	 * 
	 * void DefaultDebugger::Start() { }
	 * 
	 * void DefaultDebugger::Finish() { }
	 * 
	 * void DefaultDebugger::Enter(Environment aEnvironment, ConsPointer
	 * aExpression) { LispLocalEvaluator local(aEnvironment,NEW BasicEvaluator);
	 * iTopExpr = (aExpression.Copy()); LispPtr result;
	 * defaultEval.Eval(aEnvironment, result, iEnter); }
	 * 
	 * void DefaultDebugger::Leave(Environment aEnvironment, ConsPointer
	 * aResult, ConsPointer aExpression) { LispLocalEvaluator
	 * local(aEnvironment,NEW BasicEvaluator); LispPtr result; iTopExpr =
	 * (aExpression.Copy()); iTopResult = (aResult);
	 * defaultEval.Eval(aEnvironment, result, iLeave); }
	 * 
	 * LispBoolean DefaultDebugger::Stopped() { return iStopped; }
	 * 
	 * void DefaultDebugger::Error(Environment aEnvironment) {
	 * LispLocalEvaluator local(aEnvironment,NEW BasicEvaluator); LispPtr
	 * result; defaultEval.Eval(aEnvironment, result, iException); }
	 */
}// end class.

