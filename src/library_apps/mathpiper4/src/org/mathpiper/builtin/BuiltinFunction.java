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
package org.mathpiper.builtin;

import org.mathpiper.builtin.functions.core.*;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.unparsers.MathPiperUnparser;

import org.mathpiper.lisp.cons.Cons;

public abstract class BuiltinFunction {

    protected String functionName = "";

    public abstract void evaluate(Environment aEnvironment, int aStackTop) throws Throwable;

    public static Cons getTopOfStack(Environment aEnvironment, int aStackTop) throws Throwable {
        return aEnvironment.iArgumentStack.getElement(aStackTop, aStackTop, aEnvironment);
    }

    public static void setTopOfStack(Environment aEnvironment, int aStackTop, Cons cons) throws Throwable {
        aEnvironment.iArgumentStack.setElement(aStackTop, aStackTop, aEnvironment, cons);
    }

    public static Cons getArgument(Environment aEnvironment, int aStackTop, int argumentPosition) throws Throwable {
        return aEnvironment.iArgumentStack.getElement(aStackTop + argumentPosition, aStackTop, aEnvironment);
    }

    public static Cons getArgument(Environment aEnvironment, int aStackTop, Cons cur, int n) throws Throwable {
        if(n < 0)LispError.lispAssert(aEnvironment, aStackTop);

        Cons loop = cur;
        while (n != 0) {
            n--;
            loop = loop.cdr();
        }
        return loop;
    }

    public void plugIn(Environment aEnvironment) throws Throwable {
    }//end method.

    public static void addCoreFunctions(Environment aEnvironment) {
        String functionNameInit = "";


        aEnvironment.iBodiedOperators.setOperator(MathPiperUnparser.KMaxPrecedence, "While");
        aEnvironment.iBodiedOperators.setOperator(MathPiperUnparser.KMaxPrecedence, "RuleHoldArguments");
        aEnvironment.iBodiedOperators.setOperator(MathPiperUnparser.KMaxPrecedence, "RuleEvaluateArguments");
        aEnvironment.iBodiedOperators.setOperator(MathPiperUnparser.KMaxPrecedence, "RulePatternHoldArguments");
        aEnvironment.iBodiedOperators.setOperator(MathPiperUnparser.KMaxPrecedence, "RulePatternEvaluateArguments");
        aEnvironment.iBodiedOperators.setOperator(MathPiperUnparser.KMaxPrecedence, "PipeFromFile");
        aEnvironment.iBodiedOperators.setOperator(MathPiperUnparser.KMaxPrecedence, "PipeFromString");
        aEnvironment.iBodiedOperators.setOperator(MathPiperUnparser.KMaxPrecedence, "PipeToFile");
        aEnvironment.iBodiedOperators.setOperator(MathPiperUnparser.KMaxPrecedence, "PipeToString");
        aEnvironment.iBodiedOperators.setOperator(MathPiperUnparser.KMaxPrecedence, "PipeToStdout");
        aEnvironment.iBodiedOperators.setOperator(MathPiperUnparser.KMaxPrecedence, "TraceRule");
        aEnvironment.iBodiedOperators.setOperator(MathPiperUnparser.KMaxPrecedence, "Substitute");
        aEnvironment.iBodiedOperators.setOperator(MathPiperUnparser.KMaxPrecedence, "LocalSymbols");
        aEnvironment.iBodiedOperators.setOperator(MathPiperUnparser.KMaxPrecedence, "BackQuote");
        
        aEnvironment.iPrefixOperators.setOperator(0, "`");
        aEnvironment.iPrefixOperators.setOperator(0, "'");
        aEnvironment.iPrefixOperators.setOperator(21000, "''");
        aEnvironment.iPrefixOperators.setOperator(0, "@");
        aEnvironment.iInfixOperators.setOperator(21000, "::");

        functionNameInit = "AbsN"; aEnvironment.iBuiltinFunctions.put( functionNameInit, new BuiltinFunctionEvaluator(new Abs(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "AddN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Add(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "And?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new And_(functionNameInit), 1, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments)); //Alias.
        functionNameInit = "ApplyFast"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ApplyFast(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "ArcCosineD"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ArcCosineD(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "ArcSineD"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ArcSineD(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "ArcTangentD"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ArcTangentD(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "ArrayCreate"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ArrayCreate(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "ArrayGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ArrayGet(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "ArraySet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ArraySet(functionNameInit), 3, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "ArraySize"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ArraySize(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Atom?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Atom_(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "`"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new BackQuote(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "Assign"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Assign(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "BitAnd"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new BitAnd(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "BitCount"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new BitCount(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "BitOr"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new BitOr(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "BitXor"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new BitXor(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "BitsToDigits"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new BitsToDigits(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Bodied"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Bodied(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Bodied?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Bodied_(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Assigned?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Assigned_(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "Break"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Break(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "BuiltinAssoc"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new BuiltinAssoc(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "BuiltinPrecisionGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new BuiltinPrecisionGet(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "BuiltinPrecisionSet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new BuiltinPrecisionSet(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Car"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Car(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Cdr"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Cdr(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "CeilN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Ceil(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Check"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Check(functionNameInit), 3, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "CommonLispTokenizer"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new CommonLispTokenizer(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Concat"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Concatenate(functionNameInit), 1, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "ConcatStrings"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ConcatenateStrings(functionNameInit), 1, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Constant"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Constant(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Continue"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Continue(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "CosineD"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new CosineD(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "CurrentFile"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new CurrentFile(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "CurrentLine"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new CurrentLine(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "CustomEval"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new CustomEval(functionNameInit), 4, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "CustomEvalExpression"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new CustomEvalExpression(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "CustomEvalLocals"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new CustomEvalLocals(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "CustomEvalResult"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new CustomEvalResult(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "CustomEvalStop"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new CustomEvalStop(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "DebugFile"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DebugFile(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "DebugLine"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DebugLine(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "DebugMode?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DebugMode_(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Decimal?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Decimal_(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "MacroRulebaseHoldArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MacroRulebaseHoldArguments(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "MacroRulebaseListedHoldArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MacroRulebaseListedHoldArguments(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "DefaultDirectory"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DefaultDirectory(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "DefaultTokenizer"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DefaultTokenizer(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Delete"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Delete(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "DestructiveDelete"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DestructiveDelete(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "DestructiveInsert"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DestructiveInsert(functionNameInit), 3, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "DestructiveReplace"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DestructiveReplace(functionNameInit), 3, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "DestructiveReverse"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DestructiveReverse(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "DigitsToBits"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DigitsToBits(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "DivideN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Divide(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "DumpNumber"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DumpNumber(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Equal?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Equal_(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "=?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Equal_(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments)); //Alias.
        functionNameInit = "Equivales?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Equivales_(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "Eval"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Eval(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "ExceptionCatch"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ExceptionCatch(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "ExceptionGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ExceptionGet(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "IsExitRequested"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ExitRequested(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "MathFac"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Factorial(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "FastIsPrime"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FastIsPrime(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "FastLog"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FastLog(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "FastPower"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FastPower(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "FindFunction"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FindFunction(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "First"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new First(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "FlatCopy"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FlatCopy(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "FloorN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Floor(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "FromBase"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FromBase(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "FunctionToList"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FunctionToList(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Function?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Function_(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "GcdN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Gcd(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "GenericTypeName"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new GenericTypeName(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Generic?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Generic_(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "GetExactBitsN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new GetExactBits(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "State"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new State(functionNameInit), 0, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "GreaterThan?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new GreaterThan_(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "HistorySize"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new HistorySize(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Hold"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Hold(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "'"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Hold(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "''"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Hold(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "HoldArgument"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new HoldArgument(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "Decide"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Decide(functionNameInit), 2, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "Implies?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Implies_(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "Infix"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Infix(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Infix?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Infix_(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Insert"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Insert(functionNameInit), 3, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Integer?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Integer_(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "LeftPrecedenceGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new LeftPrecedenceGet(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "LeftPrecedenceSet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new LeftPrecedenceSet(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Length"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Length(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "LessThan?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new LessThan_(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "List"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new List(functionNameInit), 1, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "ListToFunction"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ListToFunction(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "List?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new List_(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "LoadScript"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new LoadScript(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "LoadLibraryFunction"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new LoadLibraryFunction(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Local"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Local(functionNameInit), 1, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "LocalSymbols"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new LocalSymbols(functionNameInit), 1, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "MacroLocal"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Local(functionNameInit), 1, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "MacroAssign"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MacroAssign(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "ParseMathPiper"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ParseMathPiper(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "RuleEvaluateArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RuleEvaluateArguments(functionNameInit), 5, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "RulePatternEvaluateArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RulePatternEvaluateArguments(functionNameInit), 5, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "RulebaseEvaluateArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RulebaseEvaluateArguments(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "RulebaseListedEvaluateArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RulebaseListedEvaluateArguments(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "MathIsSmall"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MathIsSmall(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "MathNegate"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MathNegate(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "MathSign"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MathSign(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "MaxEvalDepth"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MaxEvalDepth(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "MetaEntries"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MetaEntries(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "MetaGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MetaGet(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "MetaKeys"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MetaKeys(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "MetaSet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MetaSet(functionNameInit), 3, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "MetaValues"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MetaValues(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "ModuloN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Modulo(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "MultiplyN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Multiply(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Not?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Not_(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments)); //Alias.
        functionNameInit = "MathNth"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Nth(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Number?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Number_(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Or?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Or_(functionNameInit), 1, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments)); //Alias.
        functionNameInit = "ParseLisp"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ParseLisp(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "ParseLispListed"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ParseLispListed(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "ParserGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ParserGet(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "ParserSet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ParserSet(functionNameInit), 1, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "PatchString"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new PatchString(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "PatternCreate"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new PatternCreate(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "PatternMatch?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new PatternMatch_(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "PipeFromString"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new PipeFromString(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "PipeToStdout"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new PipeToStdout(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "PipeToString"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new PipeToString(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "Postfix"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Postfix(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Postfix?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Postfix_(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "PrecedenceGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new PrecedenceGet(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Prefix"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Prefix(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Prefix?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Prefix_(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "UnparserGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new UnparserGet(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "UnparserSet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new UnparserSet(functionNameInit), 1, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Block"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Block(functionNameInit), 1, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "PromptShown?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new PromptShown_(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "QuotientN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Quotient(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "ParseMathPiperToken"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ParseMathPiperToken(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Replace"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Replace(functionNameInit), 3, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Rest"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Rest(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Retract"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Retract(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "RightAssociativeSet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RightAssociativeSet(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "RightPrecedenceGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RightPrecedenceGet(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "RightPrecedenceSet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RightPrecedenceSet(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "RoundToN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RoundToN(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "RuleHoldArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RuleHoldArguments(functionNameInit), 5, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "RulePatternHoldArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RulePatternHoldArguments(functionNameInit), 5, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "RulebaseHoldArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RulebaseHoldArguments(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "RulebaseArgumentsList"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RulebaseArgumentsList(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "RulebaseDefined"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RulebaseDefined(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "RulebaseDump"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RulebaseDump(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "RulebaseListedHoldArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RulebaseListedHoldArguments(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "Secure"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Secure(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "SetExactBitsN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new SetExactBits(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "AssignGlobalLazy"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new AssignGlobalLazy(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "ShiftLeft"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ShiftLeft(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "ShiftRight"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ShiftRight(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "SineD"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new SineD(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "StaSiz"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new StackSize(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "StackTrace"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new StackTrace(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "StackTraceOff"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new StackTraceOff(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "StackTraceOn"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new StackTraceOn(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "StringMidGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new StringMidGet(functionNameInit), 3, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "StringMidSet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new StringMidSet(functionNameInit), 3, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "StringToUnicode"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new StringToUnicode(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "String?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new String_(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Substitute"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Substitute(functionNameInit), 3, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "SubtractN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Subtract(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "MetaToObject"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MetaToObject(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "SysOut"; aEnvironment.iBuiltinFunctions.put( functionNameInit, new BuiltinFunctionEvaluator(new SysOut(functionNameInit), 1, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "SystemTimer"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new SystemTimer(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "TangentD"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new TangentD(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Time"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Time(aEnvironment, functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "ToAtom"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ToAtom(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "ToBase"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ToBase(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "ToString"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ToString(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "TraceExcept"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new TraceExcept(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        aEnvironment.iBodiedOperators.setOperator(MathPiperUnparser.KMaxPrecedence, functionNameInit);
        functionNameInit = "TraceOff"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new TraceOff(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "TraceOn"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new TraceOn(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "TraceRule"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new TraceRule(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "TraceSome"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new TraceSome(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        aEnvironment.iBodiedOperators.setOperator(MathPiperUnparser.KMaxPrecedence, functionNameInit);
        functionNameInit = "TraceStack"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new TraceStack(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "Type"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new Type(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "UnFence"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new UnFence(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Unassign"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new Unassign(functionNameInit), 1, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "MacroUnassign"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new Unassign(functionNameInit), 1, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "UnicodeToString"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new UnicodeToString(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "UnparseLisp"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new UnparseLisp(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "ObjectToMeta"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ObjectToMeta(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "Version"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new Version(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "While"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new While(functionNameInit), 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        functionNameInit = "Write"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new Write(functionNameInit), 1, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "WriteString"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new WriteString(functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "XmlExplodeTag"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new XmlExplodeTag(aEnvironment, functionNameInit), 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
        functionNameInit = "XmlTokenizer"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new XmlTokenizer(functionNameInit), 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));


        /*name = "Return"; aEnvironment.getBuiltinFunctions().put(new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.core.Return(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function), name);*/



    }//end method.
}//end class.

