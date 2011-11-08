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

    public abstract void evaluate(Environment aEnvironment, int aStackTop) throws Exception;

    public static Cons getTopOfStackPointer(Environment aEnvironment, int aStackTop) throws Exception {
        return aEnvironment.iArgumentStack.getElement(aStackTop, aStackTop, aEnvironment);
    }

    public static void setTopOfStackPointer(Environment aEnvironment, int aStackTop, Cons cons) throws Exception {
        aEnvironment.iArgumentStack.setElement(aStackTop, aStackTop, aEnvironment, cons);
    }

    public static Cons getArgumentPointer(Environment aEnvironment, int aStackTop, int argumentPosition) throws Exception {
        return aEnvironment.iArgumentStack.getElement(aStackTop + argumentPosition, aStackTop, aEnvironment);
    }

    public static Cons getArgumentPointer(Environment aEnvironment, int aStackTop, Cons cur, int n) throws Exception {
        if(n < 0)LispError.lispAssert(aEnvironment, aStackTop);

        Cons loop = cur;
        while (n != 0) {
            n--;
            loop = loop.cdr();
        }
        return loop;
    }

    public void plugIn(Environment aEnvironment) throws Exception {
    }//end method.

    public static void addCoreFunctions(Environment aEnvironment) {
        String functionNameInit = "";


        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "While");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "RuleHoldArguments");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "RuleEvaluateArguments");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "RulePatternHoldArguments");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "RulePatterrnEvaluateArguments");
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

        functionNameInit = "AbsN"; aEnvironment.getBuiltinFunctions().setAssociation( new BuiltinFunctionEvaluator(new Abs(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function), functionNameInit);
        functionNameInit = "AddN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Add(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "And?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new And_(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),functionNameInit); //Alias.
        functionNameInit = "ApplyFast"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ApplyFast(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ArrayCreate"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ArrayCreate(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ArrayGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ArrayGet(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ArraySet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ArraySet(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ArraySize"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ArraySize(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Atom?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Atom_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "`"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new BackQuote(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "Bind"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Bind(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro), functionNameInit);
        functionNameInit = "BitAnd"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new BitAnd(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MathBitCount"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new BitCount(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "BitOr"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new BitOr(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "BitXor"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new BitXor(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "BitsToDigits"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new BitsToDigits(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Bodied"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Bodied(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Bodied?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Bodied_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Bound?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Bound_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "Break"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Break(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "BuiltinAssoc"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new BuiltinAssoc(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "BuiltinPrecisionGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new BuiltinPrecisionGet(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "BuiltinPrecisionSet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new BuiltinPrecisionSet(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "CeilN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Ceil(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Check"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Check(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "CommonLispTokenizer"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new CommonLispTokenizer(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Concat"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Concatenate(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ConcatStrings"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ConcatenateStrings(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Constant"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Constant(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "Continue"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Continue(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "CurrentFile"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new CurrentFile(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "CurrentLine"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new CurrentLine(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "CustomEval"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new CustomEval(functionNameInit), 4, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "CustomEvalExpression"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new CustomEvalExpression(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "CustomEvalLocals"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new CustomEvalLocals(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "CustomEvalResult"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new CustomEvalResult(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "CustomEvalStop"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new CustomEvalStop(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DebugFile"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DebugFile(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DebugLine"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DebugLine(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DebugMode?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DebugMode_(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Decimal?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Decimal_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MacroRulebaseHoldArguments"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MacroRulebaseHoldArguments(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "MacroRulebaseListedHoldArguments"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MacroRulebaseListedHoldArguments(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "DefaultDirectory"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DefaultDirectory(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function), functionNameInit);
        functionNameInit = "DefaultTokenizer"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DefaultTokenizer(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Delete"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Delete(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DestructiveDelete"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DestructiveDelete(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DestructiveInsert"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DestructiveInsert(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DestructiveReplace"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DestructiveReplace(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DestructiveReverse"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DestructiveReverse(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DigitsToBits"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DigitsToBits(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DivideN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Divide(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DumpNumber"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DumpNumber(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Equal?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Equal_(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "=?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Equal_(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit); //Alias.
        functionNameInit = "Eval"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Eval(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function), functionNameInit);
        functionNameInit = "ExceptionCatch"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ExceptionCatch(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "ExceptionGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ExceptionGet(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "IsExitRequested"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ExitRequested(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ExpressionToString"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ExpressionToString(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MathFac"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Factorial(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FastArcCos"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FastArcCos(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FastArcSin"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FastArcSin(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FastArcTan"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FastArcTan(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FastCos"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FastCos(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FastIsPrime"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FastIsPrime(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FastLog"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FastLog(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FastPower"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FastPower(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FastSin"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FastSin(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FastTan"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FastTan(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FindFunction"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FindFunction(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "First"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new First(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FlatCopy"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FlatCopy(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FloorN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Floor(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FromBase"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FromBase(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FunctionToList"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FunctionToList(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Function?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Function_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "GarbageCollect"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new GarbageCollect(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "GcdN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Gcd(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "GenericTypeName"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new GenericTypeName(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Generic?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Generic_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "GetExactBitsN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new GetExactBits(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "GlobalVariablesGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new GlobalVariablesGet(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "GreaterThan?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new GreaterThan_(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "HistorySize"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new HistorySize(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Hold"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Hold(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro), functionNameInit);
        functionNameInit = "HoldArgument"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new HoldArgument(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "If"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new If(functionNameInit), 2, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "Infix"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Infix(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Infix?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Infix_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Insert"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Insert(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Integer?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Integer_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "LeftPrecedenceGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new LeftPrecedenceGet(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "LeftPrecedenceSet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new LeftPrecedenceSet(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Length"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Length(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "LessThan?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new LessThan_(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "LispForm"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new LispForm(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function), functionNameInit);
        functionNameInit = "LispRead"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new LispRead(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "LispReadListed"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new LispReadListed(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "List"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new List(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "ListToFunction"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ListToFunction(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "List?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new List_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "LoadScript"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new LoadScript(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Local"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Local(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "MacroLocal"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Local(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "LocalSymbols"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new LocalSymbols(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "MacroBind"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MacroBind(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "RuleEvaluateArguments"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RuleEvaluateArguments(functionNameInit), 5, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "RulePatterrnEvaluateArguments"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RulePatterrnEvaluateArguments(functionNameInit), 5, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "RulebaseEvaluateArguments"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RulebaseEvaluateArguments(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "RulebaseListedEvaluateArguments"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RulebaseListedEvaluateArguments(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MathIsSmall"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MathIsSmall(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MathNegate"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MathNegate(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MathSign"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MathSign(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MaxEvalDepth"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MaxEvalDepth(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MetaEntries"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MetaEntries(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MetaGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MetaGet(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MetaKeys"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MetaKeys(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MetaSet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MetaSet(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MetaValues"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MetaValues(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ModuloN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Modulo(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MultiplyN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Multiply(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Not?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Not_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit); //Alias.
        functionNameInit = "MathNth"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Nth(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Number?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Number_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Or?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Or_(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),functionNameInit); //Alias.
        functionNameInit = "PatchString"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PatchString(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "PatternCreate"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PatternCreate(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "PatternMatches"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PatternMatches(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "PipeFromString"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PipeFromString(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro), functionNameInit);
        functionNameInit = "PipeToStdout"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PipeToStdout(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro), functionNameInit);
        functionNameInit = "PipeToString"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PipeToString(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro), functionNameInit);
        functionNameInit = "Postfix"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Postfix(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Postfix?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Postfix_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "PrecedenceGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PrecedenceGet(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Prefix"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Prefix(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Prefix?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Prefix_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "PrettyPrinterGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PrettyPrinterGet(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "PrettyPrinterSet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PrettyPrinterSet(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "PrettyReaderGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PrettyReaderGet(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "PrettyReaderSet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PrettyReaderSet(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Prog"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Prog(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "PromptShown?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PromptShown_(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "QuotientN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Quotient(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Read"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Read(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function), functionNameInit);
        functionNameInit = "ReadToken"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ReadToken(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function), functionNameInit);
        functionNameInit = "Replace"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Replace(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Rest"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Rest(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Retract"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Retract(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "RightAssociativeSet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RightAssociativeSet(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "RightPrecedenceGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RightPrecedenceGet(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "RightPrecedenceSet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RightPrecedenceSet(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "RoundToN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RoundToN(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "RuleHoldArguments"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RuleHoldArguments(functionNameInit), 5, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "RulePatternHoldArguments"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RulePatternHoldArguments(functionNameInit), 5, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "RulebaseHoldArguments"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RulebaseHoldArguments(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "RulebaseArgumentsList"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RulebaseArgumentsList(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "RulebaseDefined"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RulebaseDefined(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "RulebaseDump"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RulebaseDump(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "RulebaseListedHoldArguments"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RulebaseListedHoldArguments(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "Secure"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Secure(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "SetExactBitsN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new SetExactBits(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "SetGlobalLazyVariable"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new SetGlobalLazyVariable(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "ShiftLeft"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ShiftLeft(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ShiftRight"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ShiftRight(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "StaSiz"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new StackSize(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "StackTrace"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new StackTrace(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "StackTraceOff"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new StackTraceOff(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "StackTraceOn"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new StackTraceOn(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "StringMidGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new StringMidGet(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "StringMidSet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new StringMidSet(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "StringToUnicode"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new StringToUnicode(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "String?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new String_(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Subst"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Subst(functionNameInit), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "SubtractN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Subtract(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "SysOut"; aEnvironment.getBuiltinFunctions().setAssociation( new BuiltinFunctionEvaluator(new SysOut(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "SystemTimer"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new SystemTimer(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Time"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Time(aEnvironment, functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "ToAtom"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ToAtom(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ToBase"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ToBase(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ToString"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ToString(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "TraceExcept"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new TraceExcept(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, functionNameInit);
        functionNameInit = "TraceOff"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new TraceOff(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "TraceOn"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new TraceOn(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "TraceRule"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new TraceRule(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "TraceSome"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new TraceSome(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, functionNameInit);
        functionNameInit = "TraceStack"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new TraceStack(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "Type"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Type(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "UnFence"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new UnFence(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Unbind"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Unbind(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "MacroUnbind"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Unbind(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "UnicodeToString"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new UnicodeToString(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Version"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Version(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "While"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new While(functionNameInit), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "Write"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Write(functionNameInit), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function), functionNameInit);
        functionNameInit = "WriteString"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new WriteString(functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function), functionNameInit);
        functionNameInit = "XmlExplodeTag"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new XmlExplodeTag(aEnvironment, functionNameInit), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "XmlTokenizer"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new XmlTokenizer(functionNameInit), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);


        /*name = "Return"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.core.Return(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function), name);*/



    }//end method.
}//end class.

