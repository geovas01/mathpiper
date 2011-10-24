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
import org.mathpiper.lisp.cons.ConsPointer;
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
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "Rule");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "MacroRule");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "RulePattern");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "MacroRulePattern");
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
        functionNameInit = "AddN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Add("AddN"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "And?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new And_("And?"), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),functionNameInit); //Alias.
        functionNameInit = "ApplyFast"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ApplyFast("ApplyFast"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ArrayCreate"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ArrayCreate("ArrayCreate"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ArrayGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ArrayGet("ArrayGet"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ArraySet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ArraySet("ArraySet"), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ArraySize"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ArraySize("ArraySize"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Atom?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Atom_("Atom?"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "`"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new BackQuote("`"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "Bind"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Bind("Bind"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro), functionNameInit);
        functionNameInit = "BitAnd"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new BitAnd("BitAnd"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MathBitCount"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new BitCount("MathBitCount"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "BitOr"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new BitOr("BitOr"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "BitXor"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new BitXor("BitXor"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "BitsToDigits"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new BitsToDigits("BitsToDigits"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Bodied"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Bodied("Bodied"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Bodied?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Bodied_("Bodied?"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Bound?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Bound_("Bound?"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "Break"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Break("Break"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "BuiltinAssoc"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new BuiltinAssoc("BuiltinAssoc"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "BuiltinPrecisionGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new BuiltinPrecisionGet("BuiltinPrecisionGet"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "BuiltinPrecisionSet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new BuiltinPrecisionSet("BuiltinPrecisionSet"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "CeilN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Ceil("CeilN"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Check"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Check("Check"), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "CommonLispTokenizer"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new CommonLispTokenizer("CommonLispTokenizer"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Concat"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Concatenate("Concat"), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ConcatStrings"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ConcatenateStrings("ConcatStrings"), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Constant"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Constant("Constant"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "Continue"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Continue("Continue"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "CurrentFile"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new CurrentFile("CurrentFile"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "CurrentLine"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new CurrentLine("CurrentLine"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "CustomEval"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new CustomEval("CustomEval"), 4, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "CustomEvalExpression"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new CustomEvalExpression("CustomEvalExpression"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "CustomEvalLocals"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new CustomEvalLocals("CustomEvalLocals"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "CustomEvalResult"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new CustomEvalResult("CustomEvalResult"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "CustomEvalStop"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new CustomEvalStop("CustomEvalStop"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DebugFile"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DebugFile("DebugFile"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DebugLine"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DebugLine("DebugLine"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DebugMode?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DebugMode_("DebugMode?"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Decimal?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Decimal_("Decimal?"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DefMacroRulebase"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DefMacroRulebase("DefMacroRulebase"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "DefMacroRulebaseListed"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DefMacroRulebaseListed("DefMacroRulebaseListed"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "DefaultDirectory"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DefaultDirectory("DefaultDirectory"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function), functionNameInit);
        functionNameInit = "DefaultTokenizer"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DefaultTokenizer("DefaultTokenizer"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Delay"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Delay("Delay"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Delete"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Delete("Delete"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DestructiveDelete"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DestructiveDelete("DestructiveDelete"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DestructiveInsert"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DestructiveInsert("DestructiveInsert"), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DestructiveReplace"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DestructiveReplace("DestructiveReplace"), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DestructiveReverse"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DestructiveReverse("DestructiveReverse"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DigitsToBits"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DigitsToBits("DigitsToBits"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DivideN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Divide("DivideN"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "DumpNumber"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new DumpNumber("DumpNumber"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Equal?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Equal_("Equal?"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "=?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Equal_("=?"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit); //Alias.
        functionNameInit = "Eval"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Eval("Eval"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function), functionNameInit);
        functionNameInit = "ExceptionCatch"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ExceptionCatch("ExceptionCatch"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "ExceptionGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ExceptionGet("ExceptionGet"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "IsExitRequested"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ExitRequested("IsExitRequested"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ExpressionToString"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ExpressionToString("ExpressionToString"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MathFac"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Factorial("MathFac"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FastArcCos"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FastArcCos("FastArcCos"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FastArcSin"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FastArcSin("FastArcSin"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FastArcTan"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FastArcTan("FastArcTan"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FastCos"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FastCos("FastCos"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FastIsPrime"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FastIsPrime("FastIsPrime"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FastLog"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FastLog("FastLog"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FastPower"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FastPower("FastPower"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FastSin"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FastSin("FastSin"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FastTan"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FastTan("FastTan"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FindFunction"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FindFunction("FindFunction"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "First"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new First("First"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FlatCopy"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FlatCopy("FlatCopy"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FloorN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Floor("FloorN"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FromBase"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FromBase("FromBase"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "FunctionToList"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new FunctionToList("FunctionToList"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Function?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Function_("Function?"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "GarbageCollect"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new GarbageCollect("GarbageCollect"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "GcdN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Gcd("GcdN"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "GenericTypeName"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new GenericTypeName("GenericTypeName"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Generic?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Generic_("Generic?"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "GetExactBitsN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new GetExactBits("GetExactBitsN"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "GlobalVariablesGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new GlobalVariablesGet("GlobalVariablesGet"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "GreaterThan?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new GreaterThan_("GreaterThan?"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "HistorySize"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new HistorySize("HistorySize"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Hold"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Hold("Hold"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro), functionNameInit);
        functionNameInit = "HoldArgument"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new HoldArgument("HoldArgument"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "If"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new If("If"), 2, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "Infix"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Infix("Infix"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Infix?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Infix_("Infix?"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Insert"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Insert("Insert"), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Integer?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Integer_("Integer?"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "LeftPrecedenceGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new LeftPrecedenceGet("LeftPrecedenceGet"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "LeftPrecedenceSet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new LeftPrecedenceSet("LeftPrecedenceSet"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Length"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Length("Length"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "LessThan?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new LessThan_("LessThan?"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "LispForm"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new LispForm("LispForm"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function), functionNameInit);
        functionNameInit = "LispRead"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new LispRead("LispRead"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "LispReadListed"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new LispReadListed("LispReadListed"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "List"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new List("List"), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "ListToFunction"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ListToFunction("ListToFunction"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "List?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new List_("List?"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "LoadScript"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new LoadScript("LoadScript"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Local"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Local("Local"), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "MacroLocal"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Local("MacroLocal"), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "LocalSymbols"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new LocalSymbols("LocalSymbols"), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "MacroBind"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MacroBind("MacroBind"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "MacroRule"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MacroRule("MacroRule"), 5, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MacroRulePattern"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MacroRulePattern("MacroRulePattern"), 5, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MacroRulebase"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MacroRulebase("MacroRulebase"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MacroRulebaseListed"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MacroRulebaseListed("MacroRulebaseListed"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MathIsSmall"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MathIsSmall("MathIsSmall"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MathNegate"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MathNegate("MathNegate"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MathSign"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MathSign("MathSign"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MaxEvalDepth"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MaxEvalDepth("MaxEvalDepth"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MetaEntries"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MetaEntries("MetaEntries"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MetaGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MetaGet("MetaGet"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MetaKeys"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MetaKeys("MetaKeys"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MetaSet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MetaSet("MetaSet"), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MetaValues"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new MetaValues("MetaValues"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ModuloN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Modulo("ModuloN"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "MultiplyN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Multiply("MultiplyN"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Not?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Not_("Not?"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit); //Alias.
        functionNameInit = "MathNth"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Nth("MathNth"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Number?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Number_("Number?"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Or?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Or_("Or?"), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),functionNameInit); //Alias.
        functionNameInit = "PatchString"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PatchString("PatchString"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "PatternCreate"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PatternCreate("PatternCreate"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "PatternMatches"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PatternMatches("PatternMatches"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "PipeFromString"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PipeFromString("PipeFromString"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro), functionNameInit);
        functionNameInit = "PipeToStdout"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PipeToStdout("PipeToStdout"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro), functionNameInit);
        functionNameInit = "PipeToString"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PipeToString("PipeToString"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro), functionNameInit);
        functionNameInit = "Postfix"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Postfix("Postfix"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Postfix?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Postfix_("Postfix?"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "PrecedenceGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PrecedenceGet("PrecedenceGet"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Prefix"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Prefix("Prefix"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Prefix?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Prefix_("Prefix?"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "PrettyPrinterGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PrettyPrinterGet("PrettyPrinterGet"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "PrettyPrinterSet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PrettyPrinterSet("PrettyPrinterSet"), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "PrettyReaderGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PrettyReaderGet("PrettyReaderGet"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "PrettyReaderSet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PrettyReaderSet("PrettyReaderSet"), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Prog"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Prog("Prog"), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "PromptShown?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new PromptShown_("PromptShown?"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "QuotientN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Quotient("QuotientN"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Read"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Read("Read"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function), functionNameInit);
        functionNameInit = "ReadToken"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ReadToken("ReadToken"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function), functionNameInit);
        functionNameInit = "Replace"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Replace("Replace"), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Rest"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Rest("Rest"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Retract"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Retract("Retract"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "RightAssociativeSet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RightAssociativeSet("RightAssociativeSet"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "RightPrecedenceGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RightPrecedenceGet("RightPrecedenceGet"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "RightPrecedenceSet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RightPrecedenceSet("RightPrecedenceSet"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "RoundToN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RoundToN("RoundToN"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Rule"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Rule("Rule"), 5, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "RulePattern"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RulePattern("RulePattern"), 5, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "Rulebase"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Rulebase("Rulebase"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "RulebaseArgumentsList"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RulebaseArgumentsList("RulebaseArgumentsList"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "RulebaseDefined"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RulebaseDefined("RulebaseDefined"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "RulebaseDump"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RulebaseDump("RulebaseDump"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "RulebaseListed"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new RulebaseListed("RulebaseListed"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "Secure"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Secure("Secure"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "Set"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Set("Set"), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "SetExactBitsN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new SetExactBits("SetExactBitsN"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "SetGlobalLazyVariable"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new SetGlobalLazyVariable("SetGlobalLazyVariable"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "ShiftLeft"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ShiftLeft("ShiftLeft"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ShiftRight"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ShiftRight("ShiftRight"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "StaSiz"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new StackSize("StaSiz"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "StackTrace"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new StackTrace("StackTrace"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "StackTraceOff"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new StackTraceOff("StackTraceOff"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "StackTraceOn"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new StackTraceOn("StackTraceOn"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "StringMidGet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new StringMidGet("StringMidGet"), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "StringMidSet"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new StringMidSet("StringMidSet"), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "StringToUnicode"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new StringToUnicode("StringToUnicode"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "String?"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new String_("String?"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Subst"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Subst("Subst"), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "SubtractN"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Subtract("SubtractN"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "SysOut"; aEnvironment.getBuiltinFunctions().setAssociation( new BuiltinFunctionEvaluator(new SysOut("SysOut"), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "SystemTimer"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new SystemTimer("SystemTimer"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Time"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Time(aEnvironment, "Time"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "ToAtom"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ToAtom("ToAtom"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ToBase"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ToBase("ToBase"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "ToString"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new ToString("ToString"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "TraceExcept"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new TraceExcept("TraceExcept"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, functionNameInit);
        functionNameInit = "TraceOff"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new TraceOff("TraceOff"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "TraceOn"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new TraceOn("TraceOn"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "TraceRule"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new TraceRule("TraceRule"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "TraceSome"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new TraceSome("TraceSome"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, functionNameInit);
        functionNameInit = "TraceStack"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new TraceStack("TraceStack"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "Type"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Type("Type"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "UnFence"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new UnFence("UnFence"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Unbind"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Unbind("Unbind"), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "MacroUnbind"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Unbind("MacroUnbind"), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "UnicodeToString"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new UnicodeToString("UnicodeToString"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "Version"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Version("Version"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "While"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new While("While"), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),functionNameInit);
        functionNameInit = "Write"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new Write("Write"), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function), functionNameInit);
        functionNameInit = "WriteString"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new WriteString("WriteString"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function), functionNameInit);
        functionNameInit = "XmlExplodeTag"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new XmlExplodeTag(aEnvironment, "XmlExplodeTag"), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);
        functionNameInit = "XmlTokenizer"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new XmlTokenizer("XmlTokenizer"), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),functionNameInit);


        /*name = "Return"; aEnvironment.getBuiltinFunctions().setAssociation(new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.core.Return(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function), name);*/



    }//end method.
}//end class.

