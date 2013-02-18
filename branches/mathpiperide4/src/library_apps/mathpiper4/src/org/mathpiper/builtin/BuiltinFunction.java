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
import org.mathpiper.lisp.printers.MathPiperPrinter;

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


        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "While");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "RuleHoldArguments");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "RuleEvaluateArguments");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "RulePatternHoldArguments");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "RulePatternEvaluateArguments");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "PipeFromFile");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "PipeFromString");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "PipeToFile");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "PipeToString");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "PipeToStdout");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "TraceRule");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "Subst");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "LocalSymbols");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "BackQuote");
        
        aEnvironment.iPrefixOperators.setOperator(0, "`");
        aEnvironment.iPrefixOperators.setOperator(0, "'");
        aEnvironment.iPrefixOperators.setOperator(0, "@");
        aEnvironment.iPrefixOperators.setOperator(0, "_");
        aEnvironment.iInfixOperators.setOperator(0, "_");

        functionNameInit = "AbsN"; aEnvironment.iBuiltinFunctions.put( functionNameInit, new BuiltinFunctionEvaluator(new Abs(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "AddN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Add(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "And?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new And_(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro)); //Alias.
        functionNameInit = "ApplyFast"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ApplyFast(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "ArrayCreate"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ArrayCreate(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "ArrayGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ArrayGet(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "ArraySet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ArraySet(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "ArraySize"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ArraySize(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Atom?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Atom_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "`"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new BackQuote(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "Assign"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Assign(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "BitAnd"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new BitAnd(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "MathBitCount"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new BitCount(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "BitOr"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new BitOr(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "BitXor"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new BitXor(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "BitsToDigits"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new BitsToDigits(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Bodied"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Bodied(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Bodied?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Bodied_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Assigned?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Assigned_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "Break"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Break(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "BuiltinAssoc"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new BuiltinAssoc(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "BuiltinPrecisionGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new BuiltinPrecisionGet(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "BuiltinPrecisionSet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new BuiltinPrecisionSet(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "CeilN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Ceil(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Check"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Check(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "CommonLispTokenizer"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new CommonLispTokenizer(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Concat"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Concatenate(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function));
        functionNameInit = "ConcatStrings"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ConcatenateStrings(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Constant"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Constant(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "Continue"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Continue(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "CurrentFile"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new CurrentFile(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "CurrentLine"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new CurrentLine(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "CustomEval"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new CustomEval(functionNameInit), 4, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "CustomEvalExpression"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new CustomEvalExpression(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "CustomEvalLocals"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new CustomEvalLocals(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "CustomEvalResult"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new CustomEvalResult(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "CustomEvalStop"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new CustomEvalStop(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "DebugFile"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DebugFile(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "DebugLine"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DebugLine(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "DebugMode?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DebugMode_(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Decimal?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Decimal_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "MacroRulebaseHoldArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MacroRulebaseHoldArguments(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "MacroRulebaseListedHoldArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MacroRulebaseListedHoldArguments(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "DefaultDirectory"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DefaultDirectory(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "DefaultTokenizer"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DefaultTokenizer(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Delete"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Delete(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "DestructiveDelete"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DestructiveDelete(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "DestructiveInsert"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DestructiveInsert(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "DestructiveReplace"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DestructiveReplace(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "DestructiveReverse"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DestructiveReverse(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "DigitsToBits"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DigitsToBits(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "DivideN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Divide(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "DumpNumber"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new DumpNumber(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Equal?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Equal_(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "=?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Equal_(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function)); //Alias.
        functionNameInit = "Equivales?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Equivales_(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "Eval"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Eval(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "ExceptionCatch"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ExceptionCatch(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "ExceptionGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ExceptionGet(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "IsExitRequested"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ExitRequested(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "ExpressionToString"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ExpressionToString(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "MathFac"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Factorial(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "FastArcCos"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FastArcCos(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "FastArcSin"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FastArcSin(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "FastArcTan"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FastArcTan(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "FastCos"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FastCos(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "FastIsPrime"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FastIsPrime(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "FastLog"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FastLog(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "FastPower"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FastPower(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "FastSin"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FastSin(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "FastTan"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FastTan(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "FindFunction"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FindFunction(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "First"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new First(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "FlatCopy"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FlatCopy(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "FloorN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Floor(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "FromBase"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FromBase(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "FunctionToList"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new FunctionToList(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Function?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Function_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "GcdN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Gcd(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "GenericTypeName"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new GenericTypeName(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Generic?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Generic_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "GetExactBitsN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new GetExactBits(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "State"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new State(functionNameInit), 0, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function));
        functionNameInit = "GreaterThan?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new GreaterThan_(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "HistorySize"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new HistorySize(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Hold"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Hold(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "HoldArgument"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new HoldArgument(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "Decide"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Decide(functionNameInit), 2, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "Implies?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Implies_(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "Infix"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Infix(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Infix?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Infix_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Insert"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Insert(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Integer?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Integer_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "LeftPrecedenceGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new LeftPrecedenceGet(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "LeftPrecedenceSet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new LeftPrecedenceSet(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Length"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Length(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "LessThan?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new LessThan_(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "LispForm"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new LispForm(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "List"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new List(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "ListToFunction"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ListToFunction(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "List?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new List_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "LoadScript"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new LoadScript(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "LoadLibraryFunction"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new LoadLibraryFunction(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Local"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Local(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "LocalSymbols"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new LocalSymbols(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "MacroLocal"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Local(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function));
        functionNameInit = "MacroAssign"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MacroAssign(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "ParseMathPiper"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ParseMathPiper(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "RuleEvaluateArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RuleEvaluateArguments(functionNameInit), 5, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "RulePatternEvaluateArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RulePatternEvaluateArguments(functionNameInit), 5, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "RulebaseEvaluateArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RulebaseEvaluateArguments(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "RulebaseListedEvaluateArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RulebaseListedEvaluateArguments(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "MathIsSmall"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MathIsSmall(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "MathNegate"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MathNegate(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "MathSign"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MathSign(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "MaxEvalDepth"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MaxEvalDepth(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "MetaEntries"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MetaEntries(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "MetaGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MetaGet(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "MetaKeys"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MetaKeys(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "MetaSet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MetaSet(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "MetaValues"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new MetaValues(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "ModuloN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Modulo(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "MultiplyN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Multiply(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Not?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Not_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function)); //Alias.
        functionNameInit = "MathNth"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Nth(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Number?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Number_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Or?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Or_(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro)); //Alias.
        functionNameInit = "ParseLisp"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ParseLisp(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "ParseLispListed"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ParseLispListed(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "ParserGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ParserGet(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "ParserSet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ParserSet(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function));
        functionNameInit = "PatchString"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new PatchString(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "PatternCreate"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new PatternCreate(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "PatternMatches"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new PatternMatches(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "PipeFromString"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new PipeFromString(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "PipeToStdout"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new PipeToStdout(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "PipeToString"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new PipeToString(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "Postfix"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Postfix(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Postfix?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Postfix_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "PrecedenceGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new PrecedenceGet(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Prefix"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Prefix(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Prefix?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Prefix_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "PrettyPrinterGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new PrettyPrinterGet(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "PrettyPrinterSet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new PrettyPrinterSet(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Block"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Block(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "PromptShown?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new PromptShown_(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "QuotientN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Quotient(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Read"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Read(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "ReadToken"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ReadToken(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Replace"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Replace(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Rest"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Rest(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Retract"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Retract(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "RightAssociativeSet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RightAssociativeSet(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "RightPrecedenceGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RightPrecedenceGet(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "RightPrecedenceSet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RightPrecedenceSet(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "RoundToN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RoundToN(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "RuleHoldArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RuleHoldArguments(functionNameInit), 5, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "RulePatternHoldArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RulePatternHoldArguments(functionNameInit), 5, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "RulebaseHoldArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RulebaseHoldArguments(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "RulebaseArgumentsList"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RulebaseArgumentsList(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "RulebaseDefined"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RulebaseDefined(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "RulebaseDump"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RulebaseDump(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "RulebaseListedHoldArguments"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new RulebaseListedHoldArguments(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "Secure"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Secure(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "SetExactBitsN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new SetExactBits(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "SetGlobalLazyVariable"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new SetGlobalLazyVariable(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "ShiftLeft"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ShiftLeft(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "ShiftRight"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ShiftRight(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "StaSiz"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new StackSize(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "StackTrace"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new StackTrace(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "StackTraceOff"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new StackTraceOff(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "StackTraceOn"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new StackTraceOn(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "StringMidGet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new StringMidGet(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "StringMidSet"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new StringMidSet(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "StringToUnicode"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new StringToUnicode(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "String?"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new String_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Subst"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Subst(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "SubtractN"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Subtract(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "SysOut"; aEnvironment.iBuiltinFunctions.put( functionNameInit, new BuiltinFunctionEvaluator(new SysOut(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function));
        functionNameInit = "SystemTimer"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new SystemTimer(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Time"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new Time(aEnvironment, functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "ToAtom"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ToAtom(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "ToBase"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ToBase(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "ToString"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new ToString(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "TraceExcept"; aEnvironment.iBuiltinFunctions.put(functionNameInit, new BuiltinFunctionEvaluator(new TraceExcept(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, functionNameInit);
        functionNameInit = "TraceOff"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new TraceOff(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "TraceOn"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new TraceOn(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "TraceRule"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new TraceRule(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "TraceSome"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new TraceSome(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, functionNameInit);
        functionNameInit = "TraceStack"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new TraceStack(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "Type"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new Type(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "UnFence"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new UnFence(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Unassign"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new Unassign(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "MacroUnassign"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new Unassign(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function));
        functionNameInit = "UnicodeToString"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new UnicodeToString(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "Version"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new Version(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "While"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new While(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        functionNameInit = "Write"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new Write(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function));
        functionNameInit = "WriteString"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new WriteString(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "XmlExplodeTag"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new XmlExplodeTag(aEnvironment, functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
        functionNameInit = "XmlTokenizer"; aEnvironment.getBuiltinFunctions().put(functionNameInit, new BuiltinFunctionEvaluator(new XmlTokenizer(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));


        /*name = "Return"; aEnvironment.getBuiltinFunctions().put(new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.core.Return(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function), name);*/



    }//end method.
}//end class.

