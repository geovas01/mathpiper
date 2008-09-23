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

package org.mathrider.piper;


import org.mathrider.piper.lisp.userfunctions.PiperEvaluator;
import org.mathrider.piper.printers.InfixPrinter;
import org.mathrider.piper.parsers.InfixParser;
import org.mathrider.piper.io.StdFileOutput;
import org.mathrider.piper.io.StringOutput;
import org.mathrider.piper.io.StringInput;
import org.mathrider.piper.lisp.HashTable;
import org.mathrider.piper.lisp.Standard;
import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.userfunctions.UserFunction;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.Tokenizer;
import org.mathrider.piper.lisp.Number;
import org.mathrider.piper.lisp.Input;
import org.mathrider.piper.lisp.SubList;
import org.mathrider.piper.lisp.Printer;
import org.mathrider.piper.lisp.Output;
import org.mathrider.piper.lisp.userfunctions.MultiUserFunction;
import org.mathrider.piper.lisp.Cons;
import org.mathrider.piper.lisp.Atom;
import org.mathrider.piper.lisp.Iterator;
import org.mathrider.piper.lisp.GenericClass;
import org.mathrider.piper.lisp.Parser;
import org.mathrider.piper.lisp.DefFile;
import org.mathrider.piper.lisp.InfixOperator;
import org.mathrider.piper.lisp.Operators;
import java.io.*;

public class BuiltinFunctions
{
	public void addFunctions(Environment aEnvironment)
	{
		aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence,"While");
		aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence,"Rule");
		aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence,"MacroRule");
		aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence,"RulePattern");
		aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence,"MacroRulePattern");
		aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence,"FromFile");
		aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence,"FromString");
		aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence,"ToFile");
		aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence,"ToString");
		aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence,"ToStdout");
		aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence,"TraceRule");
		aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence,"Subst");
		aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence,"LocalSymbols");
		aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence,"BackQuote");
		aEnvironment.iPrefixOperators.SetOperator(0,"`");
		aEnvironment.iPrefixOperators.SetOperator(0,"@");
		aEnvironment.iPrefixOperators.SetOperator(0,"_");
		aEnvironment.iInfixOperators.SetOperator(0,"_");

		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispQuote(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "Hold");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispEval(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Eval");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispWrite(),1, PiperEvaluator.Variable|PiperEvaluator.Function),
		        "Write");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispWriteString(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "WriteString");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispFullForm(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FullForm");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispDefaultDirectory(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DefaultDirectory");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispFromFile(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "FromFile");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispFromString(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "FromString");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispRead(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Read");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispReadToken(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "ReadToken");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispToFile(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "ToFile");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispToString(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "ToString");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispToStdout(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "ToStdout");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispLoad(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Load");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispSetVar(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "Set");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispMacroSetVar(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "MacroSet");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispClearVar(),1, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "Clear");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispClearVar(),1, PiperEvaluator.Variable|PiperEvaluator.Function),
		        "MacroClear");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispNewLocal(),1, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "Local");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispNewLocal(),1, PiperEvaluator.Variable|PiperEvaluator.Function),
		        "MacroLocal");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispHead(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Head");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispNth(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathNth");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispTail(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Tail");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispDestructiveReverse(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DestructiveReverse");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispLength(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Length");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispList(),1, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "List");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispUnList(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "UnList");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispListify(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Listify");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispConcatenate(),1, PiperEvaluator.Variable|PiperEvaluator.Function),
		        "Concat");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispConcatenateStrings(),1, PiperEvaluator.Variable|PiperEvaluator.Function),
		        "ConcatStrings");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispDelete(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Delete");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispDestructiveDelete(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DestructiveDelete");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispInsert(),3, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Insert");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispDestructiveInsert(),3, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DestructiveInsert");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispReplace(),3, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Replace");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispDestructiveReplace(),3, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DestructiveReplace");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispAtomize(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Atom");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispStringify(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "String");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispCharString(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "CharString");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispFlatCopy(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FlatCopy");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispProgBody(),1, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "Prog");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispWhile(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "While");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispIf(),2, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "If");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispCheck(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "Check");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispTrapError(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "TrapError");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispGetCoreError(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "GetCoreError");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispPreFix(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Prefix");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispInFix(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Infix");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispPostFix(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Postfix");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispBodied(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Bodied");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispRuleBase(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "RuleBase");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispMacroRuleBase(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MacroRuleBase");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispRuleBaseListed(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "RuleBaseListed");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispMacroRuleBaseListed(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MacroRuleBaseListed");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispDefMacroRuleBase(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "DefMacroRuleBase");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispDefMacroRuleBaseListed(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "DefMacroRuleBaseListed");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispHoldArg(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "HoldArg");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispNewRule(),5, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "Rule");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispMacroNewRule(),5, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MacroRule");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispUnFence(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "UnFence");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispRetract(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Retract");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispNot(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathNot");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispNot(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Not");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispLazyAnd(),1, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "MathAnd");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispLazyAnd(),1, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "And");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispLazyOr(),1, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "MathOr");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispLazyOr(),1, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "Or");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispEquals(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Equals");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispEquals(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "=");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispLessThan(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "LessThan");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispGreaterThan(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "GreaterThan");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispIsFunction(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsFunction");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispIsAtom(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsAtom");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispIsNumber(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsNumber");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispIsInteger(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsInteger");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispIsList(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsList");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispIsString(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsString");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispIsBound(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "IsBound");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispMultiply(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathMultiply");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispAdd(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathAdd");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispSubtract(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathSubtract");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispDivide(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathDivide");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PiperBuiltinPrecisionSet(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Builtin'Precision'Set");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispGetExactBits(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathGetExactBits");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispSetExactBits(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathSetExactBits");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispBitCount(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathBitCount");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispMathSign(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathSign");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispMathIsSmall(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathIsSmall");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispMathNegate(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathNegate");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispFloor(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathFloor");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispCeil(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathCeil");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispAbs(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathAbs");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispMod(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathMod");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispDiv(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathDiv");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispBitsToDigits(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "BitsToDigits");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispDigitsToBits(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DigitsToBits");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispGcd(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathGcd");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispSystemCall(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "SystemCall");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispFastArcSin(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FastArcSin");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispFastLog(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FastLog");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispFastPower(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FastPower");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispShiftLeft(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "ShiftLeft");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispShiftRight(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "ShiftRight");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispFromBase(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FromBase");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispToBase(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "ToBase");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispMaxEvalDepth(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MaxEvalDepth");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispDefLoad(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DefLoad");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispUse(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Use");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispRightAssociative(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "RightAssociative");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispLeftPrecedence(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "LeftPrecedence");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispRightPrecedence(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "RightPrecedence");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispIsBodied(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsBodied");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispIsInFix(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsInfix");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispIsPreFix(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsPrefix");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispIsPostFix(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsPostfix");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispGetPrecedence(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "OpPrecedence");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispGetLeftPrecedence(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "OpLeftPrecedence");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispGetRightPrecedence(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "OpRightPrecedence");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PiperBuiltinPrecisionGet(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Builtin'Precision'Get");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispBitAnd(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "BitAnd");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispBitOr(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "BitOr");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispBitXor(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "BitXor");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispSecure(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "Secure");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispFindFile(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FindFile");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispFindFunction(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FindFunction");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispIsGeneric(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsGeneric");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispGenericTypeName(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "GenericTypeName");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new GenArrayCreate(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Array'Create");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new GenArraySize(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Array'Size");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new GenArrayGet(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Array'Get");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new GenArraySet(),3, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Array'Set");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispCustomEval(),4, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "CustomEval");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispCustomEvalExpression(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "CustomEval'Expression");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispCustomEvalResult(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "CustomEval'Result");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispCustomEvalLocals(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "CustomEval'Locals");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispCustomEvalStop(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "CustomEval'Stop");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispTraceRule(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "TraceRule");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispTraceStack(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "TraceStack");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispReadLisp(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "LispRead");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispReadLispListed(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "LispReadListed");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispType(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Type");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PiperStringMidGet(),3, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "StringMid'Get");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PiperStringMidSet(),3, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "StringMid'Set");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new GenPatternCreate(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Pattern'Create");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new GenPatternMatches(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Pattern'Matches");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispRuleBaseDefined(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "RuleBaseDefined");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispDefLoadFunction(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DefLoadFunction");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispRuleBaseArgList(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "RuleBaseArgList");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispNewRulePattern(),5, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "RulePattern");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispMacroNewRulePattern(),5, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MacroRulePattern");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispSubst(),3, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Subst");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispLocalSymbols(),1, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "LocalSymbols");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispFastIsPrime(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FastIsPrime");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispFac(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathFac");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispApplyPure(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "ApplyPure");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PiperPrettyReaderSet(),1, PiperEvaluator.Variable|PiperEvaluator.Function),
		        "PrettyReader'Set");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PiperPrettyPrinterSet(),1, PiperEvaluator.Variable|PiperEvaluator.Function),
		        "PrettyPrinter'Set");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PiperPrettyPrinterGet(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "PrettyPrinter'Get");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PiperPrettyReaderGet(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "PrettyReader'Get");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispGarbageCollect(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "GarbageCollect");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispSetGlobalLazyVariable(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "SetGlobalLazyVariable");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispPatchLoad(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "PatchLoad");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispPatchString(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "PatchString");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PiperExtraInfoSet(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "ExtraInfo'Set");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PiperExtraInfoGet(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "ExtraInfo'Get");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispDefaultTokenizer(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DefaultTokenizer");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispCommonLispTokenizer(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "CommonLispTokenizer");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispXmlTokenizer(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "XmlTokenizer");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispExplodeTag(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "XmlExplodeTag");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PiperBuiltinAssoc(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Builtin'Assoc");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispCurrentFile(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "CurrentFile");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispCurrentLine(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "CurrentLine");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispBackQuote(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "`");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispDumpBigNumberDebugInfo(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathDebugInfo");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispInDebugMode(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "InDebugMode");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispDebugFile(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DebugFile");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispDebugLine(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DebugLine");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispVersion(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Version");

		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispExit(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Exit");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispExitRequested(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsExitRequested");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispHistorySize(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "HistorySize");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispStackSize(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "StaSiz");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispIsPromptShown(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsPromptShown");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispReadCmdLineString(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "ReadCmdLineString");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispTime(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "GetTime");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LispFileSize(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FileSize");


	}


	/// Construct a BigNumber from one of the arguments.
	/// \param x (on output) the constructed bignumber
	/// \param aEnvironment the current environment
	/// \param aStackTop the index of the top of the stack
	/// \param aArgNr the index of the argument to be converted
	public static BigNumber getNumber(Environment aEnvironment, int aStackTop, int aArgNr) throws Exception
	{
		BigNumber x = PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, aArgNr).get().number(aEnvironment.precision());
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,x != null,aArgNr);
		return x;
	}

	static void multiFix(Environment aEnvironment, int aStackTop, Operators aOps) throws Exception
	{
		// Get operator
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
		String orig = PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

		Pointer precedence = new Pointer();
		aEnvironment.iEvaluator.eval(aEnvironment, precedence, PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2));
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,precedence.get().string() != null, 2);
		int prec = Integer.parseInt(precedence.get().string(),10);
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,prec <= InfixPrinter.KMaxPrecedence, 2);
		aOps.SetOperator(prec,Standard.symbolName(aEnvironment,orig));
		Standard.internalTrue(aEnvironment,PiperEvalCaller.RESULT(aEnvironment, aStackTop));
	}
	public static void singleFix(int aPrecedence, Environment aEnvironment, int aStackTop, Operators aOps) throws Exception
	{
		// Get operator
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
		String orig = PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
		aOps.SetOperator(aPrecedence,Standard.symbolName(aEnvironment,orig));
		Standard.internalTrue(aEnvironment,PiperEvalCaller.RESULT(aEnvironment, aStackTop));
	}

	public static InfixOperator operatorInfo(Environment aEnvironment,int aStackTop, Operators aOperators) throws Exception
	{
		// Get operator
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);

		Pointer evaluated = new Pointer();
		evaluated.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).get());

		String orig = evaluated.get().string();
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
		//
		InfixOperator op = (InfixOperator)aOperators.lookUp(Standard.symbolName(aEnvironment,orig));
		return op;
	}

	/// Execute the Piper commands \c Set and \c MacroSet.
	/// The argument \a aMacroMode determines whether the first argument
	/// should be evaluated. The real work is done by
	/// Environment::SetVariable() .
	/// \sa LispSetVar(), LispMacroSetVar()
	static void internalSetVar(Environment aEnvironment, int aStackTop, boolean aMacroMode, boolean aGlobalLazyVariable) throws Exception
	{
		String varstring=null;
		if (aMacroMode)
		{
			Pointer result = new Pointer();
			aEnvironment.iEvaluator.eval(aEnvironment, result, PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1));
			varstring = result.get().string();
		}
		else
		{
			varstring = PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
		}
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,varstring != null,1);
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,!Standard.isNumber(varstring,true),1);

		Pointer result = new Pointer();
		aEnvironment.iEvaluator.eval(aEnvironment, result, PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2));
		aEnvironment.setVariable(varstring, result, aGlobalLazyVariable);
		Standard.internalTrue(aEnvironment,PiperEvalCaller.RESULT(aEnvironment, aStackTop));
	}

	public static void internalDelete(Environment aEnvironment, int aStackTop, boolean aDestructive) throws Exception
	{
		Pointer evaluated = new Pointer();
		evaluated.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).get());
		LispError.CHK_ISLIST_CORE(aEnvironment,aStackTop,evaluated,1);

		Pointer copied = new Pointer();
		if (aDestructive)
		{
			copied.set(evaluated.get().subList().get());
		}
		else
		{
			Standard.internalFlatCopy(copied,evaluated.get().subList());
		}

		Pointer index = new Pointer();
		index.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2).get());
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get() != null, 2);
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get().string() != null, 2);
		int ind = Integer.parseInt(index.get().string(),10);
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ind>0,2);

		Iterator iter = new Iterator(copied);
		while (ind>0)
		{
			iter.GoNext();
			ind--;
		}
		LispError.CHK_CORE(aEnvironment, aStackTop,iter.GetObject() != null, LispError.KLispErrListNotLongEnough);
		Pointer next = new Pointer();
		next.set(iter.GetObject().cdr().get());
		iter.Ptr().set(next.get());
		PiperEvalCaller.RESULT(aEnvironment, aStackTop).set(SubList.getInstance(copied.get()));
	}


	public static void internalInsert(Environment aEnvironment, int aStackTop, boolean aDestructive) throws Exception
	{
		Pointer evaluated = new Pointer();
		evaluated.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).get());
		LispError.CHK_ISLIST_CORE(aEnvironment,aStackTop,evaluated,1);

		Pointer copied = new Pointer();
		if (aDestructive)
		{
			copied.set(evaluated.get().subList().get());
		}
		else
		{
			Standard.internalFlatCopy(copied,evaluated.get().subList());
		}

		Pointer index = new Pointer();
		index.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2).get());
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get() != null, 2);
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get().string() != null, 2);
		int ind = Integer.parseInt(index.get().string(),10);
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ind>0,2);

		Iterator iter = new Iterator(copied);
		while (ind>0)
		{
			iter.GoNext();
			ind--;
		}

		Pointer toInsert = new Pointer();
		toInsert.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 3).get());
		toInsert.get().cdr().set(iter.GetObject());
		iter.Ptr().set(toInsert.get());
		PiperEvalCaller.RESULT(aEnvironment, aStackTop).set(SubList.getInstance(copied.get()));
	}






	public static void internalReplace(Environment aEnvironment, int aStackTop, boolean aDestructive) throws Exception
	{
		Pointer evaluated = new Pointer();
		evaluated.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).get());
		// Ok, so lets not check if it is a list, but it needs to be at least a 'function'
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.get().subList() != null, 1);

		Pointer index = new Pointer();
		index.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2).get());
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get() != null, 2);
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get().string() != null, 2);
		int ind = Integer.parseInt(index.get().string(),10);

		Pointer copied = new Pointer();
		if (aDestructive)
		{
			copied.set(evaluated.get().subList().get());
		}
		else
		{
			Standard.internalFlatCopy(copied,evaluated.get().subList());
		}
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ind>0,2);

		Iterator iter = new Iterator(copied);
		while (ind>0)
		{
			iter.GoNext();
			ind--;
		}

		Pointer toInsert = new Pointer();
		toInsert.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 3).get());
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.Ptr() != null, 2);
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.Ptr().get() != null, 2);
		toInsert.get().cdr().set(iter.Ptr().get().cdr().get());
		iter.Ptr().set(toInsert.get());
		PiperEvalCaller.RESULT(aEnvironment, aStackTop).set(SubList.getInstance(copied.get()));
	}


	/// Implements the Piper functions \c RuleBase and \c MacroRuleBase .
	/// The real work is done by Environment::DeclareRuleBase().
	public static void internalRuleBase(Environment aEnvironment, int aStackTop,  boolean aListed) throws Exception
	{
		//TESTARGS(3);

		// Get operator
		Pointer args = new Pointer();
		String orig=null;

		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
		orig = PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
		args.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2).get());

		// The arguments
		LispError.CHK_ISLIST_CORE(aEnvironment,aStackTop,args,2);

		// Finally define the rule base
		aEnvironment.declareRuleBase(Standard.symbolName(aEnvironment,orig),
		                             args.get().subList().get().cdr(),aListed);

		// Return true
		Standard.internalTrue(aEnvironment,PiperEvalCaller.RESULT(aEnvironment, aStackTop));
	}

	public static void internalNewRule(Environment aEnvironment, int aStackTop) throws Exception
	{
		//TESTARGS(6);

		int arity;
		int precedence;

		Pointer ar = new Pointer();
		Pointer pr = new Pointer();
		Pointer predicate = new Pointer();
		Pointer body = new Pointer();
		String orig=null;

		// Get operator
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
		orig = PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
		ar.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2).get());
		pr.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 3).get());
		predicate.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 4).get());
		body.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 5).get());

		// The arity
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ar.get() != null, 2);
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ar.get().string() != null, 2);
		arity = Integer.parseInt(ar.get().string(),10);

		// The precedence
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,pr.get() != null, 3);
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,pr.get().string() != null, 3);
		precedence = Integer.parseInt(pr.get().string(),10);

		// Finally define the rule base
		aEnvironment.defineRule(Standard.symbolName(aEnvironment,orig),
		                        arity,
		                        precedence,
		                        predicate,
		                        body );

		// Return true
		Standard.internalTrue(aEnvironment,PiperEvalCaller.RESULT(aEnvironment, aStackTop));
	}




	void internalDefMacroRuleBase(Environment aEnvironment, int aStackTop, boolean aListed) throws Exception
	{
		// Get operator
		Pointer args = new Pointer();
		Pointer body = new Pointer();
		String orig=null;

		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
		orig = PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

		// The arguments
		args.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2).get());
		LispError.CHK_ISLIST_CORE(aEnvironment,aStackTop,args,2);

		// Finally define the rule base
		aEnvironment.declareMacroRuleBase(Standard.symbolName(aEnvironment,orig),
		                                  args.get().subList().get().cdr(),aListed);

		// Return true
		Standard.internalTrue(aEnvironment,PiperEvalCaller.RESULT(aEnvironment, aStackTop));
	}




	public void internalNewRulePattern(Environment aEnvironment, int aStackTop, boolean aMacroMode) throws Exception
	{
		int arity;
		int precedence;

		Pointer ar = new Pointer();
		Pointer pr = new Pointer();
		Pointer predicate = new Pointer();
		Pointer body = new Pointer();
		String orig=null;

		// Get operator
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
		orig = PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
		ar.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2).get());
		pr.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 3).get());
		predicate.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 4).get());
		body.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 5).get());

		// The arity
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ar.get() != null, 2);
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ar.get().string() != null, 2);
		arity = Integer.parseInt(ar.get().string(),10);

		// The precedence
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,pr.get() != null, 3);
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,pr.get().string() != null, 3);
		precedence = Integer.parseInt(pr.get().string(),10);

		// Finally define the rule base
		aEnvironment.defineRulePattern(Standard.symbolName(aEnvironment,orig),
		                               arity,
		                               precedence,
		                               predicate,
		                               body );

		// Return true
		Standard.internalTrue(aEnvironment,PiperEvalCaller.RESULT(aEnvironment, aStackTop));
	}

        
/***************************************************************************************************************
 * Classes which implement builtin functions.
 * 
 */

	class LispQuote extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			RESULT(aEnvironment, aStackTop).set(ARGUMENT(aEnvironment, aStackTop, 1).get().copy(false));
		}
	}

	class LispEval extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iEvaluator.eval(aEnvironment, RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 1));
		}
	}

	class LispWrite extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer subList = ARGUMENT(aEnvironment, aStackTop, 1).get().subList();
			if (subList != null)
			{
				Iterator iter = new Iterator(subList);
				iter.GoNext();
				while (iter.GetObject() != null)
				{
					aEnvironment.iCurrentPrinter.Print(iter.Ptr(),aEnvironment.iCurrentOutput,aEnvironment);
					iter.GoNext();
				}
			}
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispWriteString extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).get()!= null,1);
			String str = ARGUMENT(aEnvironment, aStackTop, 1).get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,str != null,1);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,str.charAt(0) == '\"',1);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,str.charAt(str.length()-1) == '\"',1);

			int i=1;
			int nr=str.length()-1;
			//((*str)[i] != '\"')
			for (i=1;i<nr;i++)
			{
				aEnvironment.iCurrentOutput.PutChar(str.charAt(i));
			}
			// pass last printed character to the current printer
			aEnvironment.iCurrentPrinter.RememberLastChar(str.charAt(nr-1));  // hacky hacky
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispFullForm extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			RESULT(aEnvironment, aStackTop).set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			Printer printer = new Printer();
			printer.Print(RESULT(aEnvironment, aStackTop), aEnvironment.iCurrentOutput, aEnvironment);
			aEnvironment.iCurrentOutput.Write("\n");
		}
	}

	class LispDefaultDirectory extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			// Get file name
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
			String orig = ARGUMENT(aEnvironment, aStackTop, 1).get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
			String oper = Standard.internalUnstringify(orig);
			aEnvironment.iInputDirectories.add(oper);
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispFromFile extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			LispError.CHK_CORE(aEnvironment, aStackTop,aEnvironment.iSecure == false, LispError.KLispErrSecurityBreach);
			Pointer evaluated = new Pointer();
			aEnvironment.iEvaluator.eval(aEnvironment, evaluated, ARGUMENT(aEnvironment, aStackTop, 1));

			// Get file name
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.get() != null, 1);
			String orig = evaluated.get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

			String hashedname = aEnvironment.hashTable().lookUpUnStringify(orig);

			InputStatus oldstatus = aEnvironment.iInputStatus;
			Input previous = aEnvironment.iCurrentInput;
			try
			{
				aEnvironment.iInputStatus.setTo(hashedname);
				Input input = // new StdFileInput(hashedname, aEnvironment.iInputStatus);
				        Standard.openInputFile(aEnvironment, aEnvironment.iInputDirectories, hashedname, aEnvironment.iInputStatus);
				aEnvironment.iCurrentInput = input;
				// Open file
				LispError.CHK_CORE(aEnvironment, aStackTop,input != null, LispError.KLispErrFileNotFound);

				// Evaluate the body
				aEnvironment.iEvaluator.eval(aEnvironment, RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 2));
			}
			catch (Exception e)
			{
				throw e;
			}
			finally
			{
				aEnvironment.iCurrentInput = previous;
				aEnvironment.iInputStatus.RestoreFrom(oldstatus);
			}
			//Return the result
		}
	}

	class LispFromString extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer evaluated = new Pointer();
			aEnvironment.iEvaluator.eval(aEnvironment, evaluated, ARGUMENT(aEnvironment, aStackTop, 1));

			// Get file name
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.get() != null, 1);
			String orig = evaluated.get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
			String oper = Standard.internalUnstringify(orig);

			InputStatus oldstatus = aEnvironment.iInputStatus;
			aEnvironment.iInputStatus.setTo("String");
			StringInput newInput = new StringInput(new StringBuffer(oper),aEnvironment.iInputStatus);

			Input previous = aEnvironment.iCurrentInput;
			aEnvironment.iCurrentInput = newInput;
			try
			{
				// Evaluate the body
				aEnvironment.iEvaluator.eval(aEnvironment, RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 2));
			}
			catch (Exception e)
			{
				throw e;
			}
			finally
			{
				aEnvironment.iCurrentInput = previous;
				aEnvironment.iInputStatus.RestoreFrom(oldstatus);
			}

			//Return the result
		}
	}

	class LispRead extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			InfixParser parser = new InfixParser(aEnvironment.iCurrentTokenizer,
			                                     aEnvironment.iCurrentInput,
			                                     aEnvironment,
			                                     aEnvironment.iPrefixOperators,
			                                     aEnvironment.iInfixOperators,
			                                     aEnvironment.iPostfixOperators,
			                                     aEnvironment.iBodiedOperators);
			// Read expression
			parser.Parse(RESULT(aEnvironment, aStackTop));
		}
	}

	class LispReadToken extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Tokenizer tok = aEnvironment.iCurrentTokenizer;
			String result;
			result = tok.nextToken(aEnvironment.iCurrentInput, aEnvironment.hashTable());

			if (result.length() == 0)
			{
				RESULT(aEnvironment, aStackTop).set(aEnvironment.iEndOfFile.copy(false));
				return;
			}
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,result));
		}
	}

	class LispToFile extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			LispError.CHK_CORE(aEnvironment, aStackTop,aEnvironment.iSecure == false, LispError.KLispErrSecurityBreach);

			Pointer evaluated = new Pointer();
			aEnvironment.iEvaluator.eval(aEnvironment, evaluated, ARGUMENT(aEnvironment, aStackTop, 1));

			// Get file name
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.get() != null, 1);
			String orig = evaluated.get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
			String oper = Standard.internalUnstringify(orig);

			// Open file for writing
			FileOutputStream localFP = new FileOutputStream(oper);
			LispError.CHK_CORE(aEnvironment, aStackTop,localFP != null, LispError.KLispErrFileNotFound);
			StdFileOutput newOutput = new StdFileOutput(localFP);

			Output previous = aEnvironment.iCurrentOutput;
			aEnvironment.iCurrentOutput = newOutput;

			try
			{
				aEnvironment.iEvaluator.eval(aEnvironment, RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 2));
			}
			catch (Exception e) { throw e; }
			finally
			{
				aEnvironment.iCurrentOutput = previous;
			}
		}
	}

	class LispToString extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			StringBuffer oper = new StringBuffer();
			StringOutput newOutput = new StringOutput(oper);
			Output previous = aEnvironment.iCurrentOutput;
			aEnvironment.iCurrentOutput = newOutput;
			try
			{
				// Evaluate the body
				aEnvironment.iEvaluator.eval(aEnvironment, RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 1));

				//Return the result
				RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,aEnvironment.hashTable().lookUpStringify(oper.toString())));
			}
			catch (Exception e) { throw e; }
			finally
			{
				aEnvironment.iCurrentOutput = previous;
			}
		}
	}

	class LispToStdout extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Output previous = aEnvironment.iCurrentOutput;
			aEnvironment.iCurrentOutput = aEnvironment.iInitialOutput;
			try
			{
				aEnvironment.iEvaluator.eval(aEnvironment, RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 1));
			}
			catch (Exception e) { throw e; }
			finally
			{
				aEnvironment.iCurrentOutput = previous;
			}
		}
	}

	class LispLoad extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			LispError.CHK_CORE(aEnvironment, aStackTop,aEnvironment.iSecure == false, LispError.KLispErrSecurityBreach);

			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

			// Get file name
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.get() != null, 1);
			String orig = evaluated.get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

			Standard.internalLoad(aEnvironment,orig);
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispSetVar extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BuiltinFunctions.internalSetVar(aEnvironment, aStackTop, false, false);
		}
	}

	class LispMacroSetVar extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BuiltinFunctions.internalSetVar(aEnvironment, aStackTop, true, false);
		}
	}

	class LispSetGlobalLazyVariable extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BuiltinFunctions.internalSetVar(aEnvironment, aStackTop, false, true);
		}
	}

	class LispClearVar extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer subList = ARGUMENT(aEnvironment, aStackTop, 1).get().subList();
			if (subList != null)
			{
				Iterator iter = new Iterator(subList);
				iter.GoNext();
				int nr=1;
				while (iter.GetObject() != null)
				{
					String str;
					str = iter.GetObject().string();
					LispError.CHK_ARG_CORE(aEnvironment,aStackTop,str != null, nr);
					aEnvironment.unsetVariable(str);
					iter.GoNext();
					nr++;
				}
			}
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispNewLocal extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer subList = ARGUMENT(aEnvironment, aStackTop, 1).get().subList();
			if (subList!= null)
			{
				Iterator iter = new Iterator(subList);
				iter.GoNext();

				int nr = 1;
				while (iter.GetObject() != null)
				{
					String variable = iter.GetObject().string();
					LispError.CHK_ARG_CORE(aEnvironment,aStackTop,variable != null,nr);
					// printf("Variable %s\n",variable.String());
					aEnvironment.newLocal(variable,null);
					iter.GoNext();
					nr++;
				}
			}
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispHead extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Standard.internalNth(RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 1),1);
		}
	}

	class LispNth extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			String str;
			str = ARGUMENT(aEnvironment, aStackTop, 2).get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,str != null,2);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,Standard.isNumber(str,false),2);
			int index = Integer.parseInt(str);
			Standard.internalNth(RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 1), index);
		}
	}

	class LispTail extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer first = new Pointer();
			Standard.internalTail(first, ARGUMENT(aEnvironment, aStackTop, 1));
			Standard.internalTail(RESULT(aEnvironment, aStackTop), first);
			Pointer head = new Pointer();
			head.set(aEnvironment.iList.copy(false));
			head.get().cdr().set(RESULT(aEnvironment, aStackTop).get().subList().get());
			RESULT(aEnvironment, aStackTop).get().subList().set(head.get());
		}
	}

	class LispDestructiveReverse extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer reversed = new Pointer();
			reversed.set(aEnvironment.iList.copy(false));
			Standard.internalReverseList(reversed.get().cdr(), ARGUMENT(aEnvironment, aStackTop, 1).get().subList().get().cdr());
			RESULT(aEnvironment, aStackTop).set(SubList.getInstance(reversed.get()));
		}
	}

	class LispLength extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer subList = ARGUMENT(aEnvironment, aStackTop, 1).get().subList();
			if (subList != null)
			{
				int num = Standard.internalListLength(subList.get().cdr());
				RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,""+num));
				return;
			}
			String string = ARGUMENT(aEnvironment, aStackTop, 1).get().string();
			if (Standard.internalIsString(string))
			{
				int num = string.length()-2;
				RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,""+num));
				return;
			}
			GenericClassContainer gen = ARGUMENT(aEnvironment, aStackTop, 1).get().generic();
			if (gen != null)
				if (gen.TypeName().equals("\"Array\""))
				{
					int size=((ArrayClass)gen).Size();
					RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,""+size));
					return;
				}
			//  CHK_ISLIST_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1),1);
		}
	}

	class LispList extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer all = new Pointer();
			all.set(aEnvironment.iList.copy(false));
			Iterator tail = new Iterator(all);
			tail.GoNext();
			Iterator iter = new Iterator(ARGUMENT(aEnvironment, aStackTop, 1).get().subList());
			iter.GoNext();
			while (iter.GetObject() != null)
			{
				Pointer evaluated = new Pointer();
				aEnvironment.iEvaluator.eval(aEnvironment,evaluated,iter.Ptr());
				tail.Ptr().set(evaluated.get());
				tail.GoNext();
				iter.GoNext();
			}
			RESULT(aEnvironment, aStackTop).set(SubList.getInstance(all.get()));
		}
	}

	class LispUnList extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).get().subList() != null, 1);
			Cons subList = ARGUMENT(aEnvironment, aStackTop, 1).get().subList().get();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,subList != null, 1);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,subList.string() == aEnvironment.iList.string(),1);
			Standard.internalTail(RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 1));
		}
	}

	class LispListify extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).get().subList() != null, 1);
			Pointer head = new Pointer();
			head.set(aEnvironment.iList.copy(false));
			head.get().cdr().set(ARGUMENT(aEnvironment, aStackTop, 1).get().subList().get());
			RESULT(aEnvironment, aStackTop).set(SubList.getInstance(head.get()));
		}
	}

	class LispConcatenate extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer all = new Pointer();
			all.set(aEnvironment.iList.copy(false));
			Iterator tail = new Iterator(all);
			tail.GoNext();
			int arg = 1;

			Iterator iter = new Iterator(ARGUMENT(aEnvironment, aStackTop, 1).get().subList());
			iter.GoNext();
			while (iter.GetObject() != null)
			{
				LispError.CHK_ISLIST_CORE(aEnvironment,aStackTop,iter.Ptr(),arg);
				Standard.internalFlatCopy(tail.Ptr(),iter.Ptr().get().subList().get().cdr());
				while (tail.GetObject() != null)
					tail.GoNext();
				iter.GoNext();
				arg++;
			}
			RESULT(aEnvironment, aStackTop).set(SubList.getInstance(all.get()));
		}
	}

	class LispConcatenateStrings extends PiperEvalCaller
	{
		void ConcatenateStrings(StringBuffer aStringBuffer, Environment aEnvironment, int aStackTop) throws Exception
		{
			aStringBuffer.append('\"');
			int arg=1;

			Iterator iter = new Iterator(ARGUMENT(aEnvironment, aStackTop, 1).get().subList());
			iter.GoNext();
			while (iter.GetObject() != null)
			{
				LispError.CHK_ISSTRING_CORE(aEnvironment,aStackTop,iter.Ptr(),arg);
				String thisString = iter.GetObject().string();
				String toAppend = thisString.substring(1,thisString.length()-1);
				aStringBuffer.append(toAppend);
				iter.GoNext();
				arg++;
			}
			aStringBuffer.append('\"');
		}
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			StringBuffer strBuffer = new StringBuffer("");
			ConcatenateStrings(strBuffer,aEnvironment, aStackTop);
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,strBuffer.toString()));
		}
	}

	class LispDelete extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BuiltinFunctions.internalDelete(aEnvironment, aStackTop,false);
		}
	}

	class LispDestructiveDelete extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BuiltinFunctions.internalDelete(aEnvironment, aStackTop,true);
		}
	}

	class LispInsert extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BuiltinFunctions.internalInsert(aEnvironment, aStackTop,false);
		}
	}

	class LispDestructiveInsert extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BuiltinFunctions.internalInsert(aEnvironment, aStackTop,true);
		}
	}

	class LispReplace extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BuiltinFunctions.internalReplace(aEnvironment, aStackTop,false);
		}
	}

	class LispDestructiveReplace extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BuiltinFunctions.internalReplace(aEnvironment, aStackTop,true);
		}
	}

	class LispAtomize extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

			// Get operator
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.get() != null, 1);
			String orig = evaluated.get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,aEnvironment.hashTable().lookUpUnStringify(orig)));
		}
	}

	class LispStringify extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

			// Get operator
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.get() != null, 1);
			String orig = evaluated.get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,aEnvironment.hashTable().lookUpStringify(orig)));
		}
	}

	class LispCharString extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			String str;
			str = ARGUMENT(aEnvironment, aStackTop, 1).get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,str != null,2);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,Standard.isNumber(str,false),2);
			char asciiCode = (char)Integer.parseInt(str,10);
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,"\""+asciiCode+"\""));
		}
	}

	class LispFlatCopy extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer copied = new Pointer();
			Standard.internalFlatCopy(copied,ARGUMENT(aEnvironment, aStackTop, 1).get().subList());
			RESULT(aEnvironment, aStackTop).set(SubList.getInstance(copied.get()));
		}
	}

	class LispProgBody extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			// Allow accessing previous locals.
			aEnvironment.pushLocalFrame(false);
			try
			{
				Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));

				// Evaluate args one by one.

				Iterator iter = new Iterator(ARGUMENT(aEnvironment, aStackTop, 1).get().subList());
				iter.GoNext();
				while (iter.GetObject() != null)
				{
					aEnvironment.iEvaluator.eval(aEnvironment, RESULT(aEnvironment, aStackTop), iter.Ptr());
					iter.GoNext();
				}
			}
			catch (Exception e) { throw e; }
			finally
			{
				aEnvironment.popLocalFrame();
			}
		}
	}

	class LispWhile extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer arg1 = ARGUMENT(aEnvironment, aStackTop, 1);
			Pointer arg2 = ARGUMENT(aEnvironment, aStackTop, 2);

			Pointer predicate = new Pointer();
			aEnvironment.iEvaluator.eval(aEnvironment, predicate, arg1);

			while (Standard.isTrue(aEnvironment,predicate))
			{
				Pointer evaluated = new Pointer();
				aEnvironment.iEvaluator.eval(aEnvironment, evaluated, arg2);
				aEnvironment.iEvaluator.eval(aEnvironment, predicate, arg1);

			}
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,Standard.isFalse(aEnvironment,predicate),1);
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispIf extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			int nrArguments = Standard.internalListLength(ARGUMENT(aEnvironment, aStackTop, 0));
			LispError.CHK_CORE(aEnvironment,aStackTop,nrArguments == 3 || nrArguments == 4,LispError.KLispErrWrongNumberOfArgs);

			Pointer predicate = new Pointer();
			aEnvironment.iEvaluator.eval(aEnvironment, predicate, ARGUMENT(aEnvironment, aStackTop, 1));

			if (Standard.isTrue(aEnvironment,predicate))
			{
				aEnvironment.iEvaluator.eval(aEnvironment, RESULT(aEnvironment, aStackTop), argument(ARGUMENT(aEnvironment, aStackTop, 0),2));
			}
			else
			{
				LispError.CHK_ARG_CORE(aEnvironment,aStackTop,Standard.isFalse(aEnvironment,predicate),1);
				if (nrArguments == 4)
				{
					aEnvironment.iEvaluator.eval(aEnvironment, RESULT(aEnvironment, aStackTop), argument(ARGUMENT(aEnvironment, aStackTop, 0),3));
				}
				else
				{
					Standard.internalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
				}
			}
		}
	}

	class LispCheck extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer pred = new Pointer();
			aEnvironment.iEvaluator.eval(aEnvironment, pred, ARGUMENT(aEnvironment, aStackTop, 1));
			if (!Standard.isTrue(aEnvironment,pred))
			{
				Pointer evaluated = new Pointer();
				aEnvironment.iEvaluator.eval(aEnvironment, evaluated, ARGUMENT(aEnvironment, aStackTop, 2));
				LispError.CHK_ISSTRING_CORE(aEnvironment,aStackTop,evaluated,2);
				throw new Exception(evaluated.get().string());
			}
			RESULT(aEnvironment, aStackTop).set(pred.get());
		}
	}

	class LispTrapError extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			try
			{
				aEnvironment.iEvaluator.eval(aEnvironment, RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 1));
			}
			catch (Exception e)
			{
				aEnvironment.iError = e.toString();
				aEnvironment.iEvaluator.eval(aEnvironment, RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 2));
				aEnvironment.iError = null;
			}
		}
	}

	class LispGetCoreError extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,aEnvironment.hashTable().lookUpStringify(aEnvironment.iError)));
		}
	}

	class LispPreFix extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BuiltinFunctions.multiFix(aEnvironment, aStackTop, aEnvironment.iPrefixOperators);
		}
	}

	class LispInFix extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BuiltinFunctions.multiFix(aEnvironment, aStackTop, aEnvironment.iInfixOperators);
		}
	}

	class LispPostFix extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			int nrArguments = Standard.internalListLength(ARGUMENT(aEnvironment, aStackTop, 0));
			if (nrArguments == 2)
			{
				BuiltinFunctions.singleFix(0, aEnvironment, aStackTop, aEnvironment.iPostfixOperators);
			}
			else
			{
				BuiltinFunctions.multiFix(aEnvironment, aStackTop, aEnvironment.iPostfixOperators);
			}
		}
	}

	class LispBodied extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BuiltinFunctions.multiFix(aEnvironment, aStackTop, aEnvironment.iBodiedOperators);
		}
	}

	class LispRuleBase extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			internalRuleBase(aEnvironment, aStackTop, false);
		}
	}

	class LispMacroRuleBase extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			internalRuleBase(aEnvironment, aStackTop, false);
		}
	}

	class LispRuleBaseListed extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			internalRuleBase(aEnvironment, aStackTop, true);
		}
	}

	class LispMacroRuleBaseListed extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			internalRuleBase(aEnvironment, aStackTop, true);
		}
	}

	class LispDefMacroRuleBase extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			internalDefMacroRuleBase(aEnvironment, aStackTop, false);
		}
	}

	class LispDefMacroRuleBaseListed extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			internalDefMacroRuleBase(aEnvironment, aStackTop, true);
		}
	}

	class LispHoldArg extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			// Get operator
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
			String orig = ARGUMENT(aEnvironment, aStackTop, 1).get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

			// The arguments
			String tohold = ARGUMENT(aEnvironment, aStackTop, 2).get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,tohold != null, 2);
			aEnvironment.holdArgument(Standard.symbolName(aEnvironment,orig), tohold);
			// Return true
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispNewRule extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			internalNewRule(aEnvironment, aStackTop);
		}
	}

	class LispMacroNewRule extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			internalNewRule(aEnvironment, aStackTop);
		}
	}

	class LispUnFence extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			// Get operator
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
			String orig = ARGUMENT(aEnvironment, aStackTop, 1).get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

			// The arity
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 2).get() != null, 2);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 2).get().string() != null, 2);
			int arity = Integer.parseInt(ARGUMENT(aEnvironment, aStackTop, 2).get().string(),10);

			aEnvironment.unFenceRule(Standard.symbolName(aEnvironment,orig), arity);

			// Return true
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispRetract extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			// Get operator
			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.get() != null, 1);
			String orig = evaluated.get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
			String oper = Standard.symbolName(aEnvironment,orig);

			Pointer arity = new Pointer();
			arity.set(ARGUMENT(aEnvironment, aStackTop, 2).get());
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,arity.get().string() != null, 2);
			int ar = Integer.parseInt(arity.get().string(),10);
			aEnvironment.retract(oper, ar);
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispNot extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			if (Standard.isTrue(aEnvironment, evaluated) || Standard.isFalse(aEnvironment, evaluated))
			{
				Standard.internalNot(RESULT(aEnvironment, aStackTop), aEnvironment, evaluated);
			}
			else
			{
				Pointer ptr = new Pointer();
				ptr.set(ARGUMENT(aEnvironment, aStackTop, 0).get().copy(false));
				ptr.get().cdr().set(evaluated.get());
				RESULT(aEnvironment, aStackTop).set(SubList.getInstance(ptr.get()));
			}
		}
	}

	class LispLazyAnd extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer nogos = new Pointer();
			int nrnogos=0;
			Pointer evaluated = new Pointer();

			Iterator iter = new Iterator(ARGUMENT(aEnvironment, aStackTop, 1).get().subList());
			iter.GoNext();
			while (iter.GetObject() != null)
			{
				aEnvironment.iEvaluator.eval(aEnvironment, evaluated, iter.Ptr());
				if (Standard.isFalse(aEnvironment, evaluated))
				{
					Standard.internalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
					return;
				}
				else if (!Standard.isTrue(aEnvironment, evaluated))
				{
					Pointer ptr = new Pointer();
					nrnogos++;
					ptr.set(evaluated.get().copy(false));
					ptr.get().cdr().set(nogos.get());
					nogos.set(ptr.get());
				}

				iter.GoNext();
			}

			if (nogos.get() != null)
			{
				if (nrnogos == 1)
				{
					RESULT(aEnvironment, aStackTop).set(nogos.get());
				}
				else
				{
					Pointer ptr = new Pointer();

					Standard.internalReverseList(ptr, nogos);
					nogos.set(ptr.get());

					ptr.set(ARGUMENT(aEnvironment, aStackTop, 0).get().copy(false));
					ptr.get().cdr().set(nogos.get());
					nogos.set(ptr.get());
					RESULT(aEnvironment, aStackTop).set(SubList.getInstance(nogos.get()));

					//aEnvironment.CurrentPrinter().Print(RESULT(aEnvironment, aStackTop), *aEnvironment.CurrentOutput());
				}
			}
			else
			{
				Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
			}
		}
	}

	class LispLazyOr extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer nogos = new Pointer();
			int nrnogos=0;

			Pointer evaluated = new Pointer();

			Iterator iter = new Iterator(ARGUMENT(aEnvironment, aStackTop, 1).get().subList());
			iter.GoNext();
			while (iter.GetObject() != null)
			{
				aEnvironment.iEvaluator.eval(aEnvironment, evaluated, iter.Ptr());
				if (Standard.isTrue(aEnvironment, evaluated))
				{
					Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
					return;
				}
				else if (!Standard.isFalse(aEnvironment, evaluated))
				{
					Pointer ptr = new Pointer();
					nrnogos++;

					ptr.set(evaluated.get().copy(false));
					ptr.get().cdr().set(nogos.get());
					nogos.set(ptr.get());
				}
				iter.GoNext();
			}

			if (nogos.get() != null)
			{
				if (nrnogos == 1)
				{
					RESULT(aEnvironment, aStackTop).set(nogos.get());
				}
				else
				{
					Pointer ptr = new Pointer();

					Standard.internalReverseList(ptr, nogos);
					nogos.set(ptr.get());

					ptr.set(ARGUMENT(aEnvironment, aStackTop, 0).get().copy(false));
					ptr.get().cdr().set(nogos.get());
					nogos.set(ptr.get());
					RESULT(aEnvironment, aStackTop).set(SubList.getInstance(nogos.get()));
				}
				//aEnvironment.CurrentPrinter().Print(RESULT(aEnvironment, aStackTop), *aEnvironment.CurrentOutput());
			}
			else
			{
				Standard.internalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
			}
		}
	}

	class LispEquals extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer evaluated1 = new Pointer();
			evaluated1.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			Pointer evaluated2 = new Pointer();
			evaluated2.set(ARGUMENT(aEnvironment, aStackTop, 2).get());

			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop),
			                             Standard.internalEquals(aEnvironment, evaluated1, evaluated2));
		}
	}

	abstract class LispLexCompare2
	{
		abstract boolean lexfunc(String f1, String f2, HashTable aHashTable,int aPrecision);
		abstract boolean numfunc(BigNumber n1, BigNumber n2);

		void Compare(Environment aEnvironment, int aStackTop) throws Exception
		{
			Pointer result1 = new Pointer();
			Pointer result2 = new Pointer();
			result1.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).get());
			result2.set(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2).get());
			boolean cmp;
			BigNumber n1 = result1.get().number(aEnvironment.precision());
			BigNumber n2 = result2.get().number(aEnvironment.precision());
			if (n1 != null && n2 != null)
			{
				cmp =numfunc(n1,n2);
			}
			else
			{
				String str1;
				String str2;
				str1 = result1.get().string();
				str2 = result2.get().string();
				LispError.CHK_ARG_CORE(aEnvironment,aStackTop,str1 != null ,1);
				LispError.CHK_ARG_CORE(aEnvironment,aStackTop,str2 != null, 2);
				// the precision argument is ignored in "lex" functions
				cmp =lexfunc(str1,str2,
				             aEnvironment.hashTable(),
				             aEnvironment.precision());
			}

			Standard.internalBoolean(aEnvironment,PiperEvalCaller.RESULT(aEnvironment, aStackTop), cmp);
		}
	}


	class LexLessThan extends LispLexCompare2
	{
		boolean lexfunc(String f1, String f2, HashTable aHashTable,int aPrecision)
		{
			return f1.compareTo(f2)<0;
		}
		boolean numfunc(BigNumber n1, BigNumber n2)
		{
			return n1.LessThan(n2) && !n1.Equals(n2);
		}
	}
	class LexGreaterThan extends LispLexCompare2
	{
		boolean lexfunc(String f1, String f2, HashTable aHashTable,int aPrecision)
		{
			return f1.compareTo(f2)>0;
		}
		boolean numfunc(BigNumber n1, BigNumber n2)
		{
			return !(n1.LessThan(n2) || n1.Equals(n2));
		}
	}


	class LispLessThan extends PiperEvalCaller
	{
		LexLessThan compare = new LexLessThan();
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			compare.Compare(aEnvironment,aStackTop);
		}
	}

	class LispGreaterThan extends PiperEvalCaller
	{
		LexGreaterThan compare = new LexGreaterThan();
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			compare.Compare(aEnvironment,aStackTop);
		}
	}

	class LispIsFunction extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer result = new Pointer();
			result.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop),
			                             result.get().subList()!=null);
		}
	}

	class LispIsAtom extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer result = new Pointer();
			result.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			String s = result.get().string();
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop),s!=null);
		}
	}

	class LispIsNumber extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer result = new Pointer();
			result.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), result.get().number(aEnvironment.precision()) != null);
		}
	}

	class LispIsInteger extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer result = new Pointer();
			result.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

			BigNumber num = result.get().number(aEnvironment.precision());
			if (num == null)
			{
				Standard.internalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
			}
			else
			{
				Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), num.IsInt());
			}
		}
	}

	class LispIsList extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer result = new Pointer();
			result.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop),Standard.internalIsList(result));
		}
	}

	class LispIsString extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer result = new Pointer();
			result.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop),
			                             Standard.internalIsString(result.get().string()));
		}
	}

	class LispIsBound extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			String str = ARGUMENT(aEnvironment, aStackTop, 1).get().string();
			if (str != null)
			{
				Pointer val = new Pointer();
				aEnvironment.getVariable(str,val);
				if (val.get() != null)
				{
					Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
					return;
				}
			}
			Standard.internalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispMultiply extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber y = getNumber(aEnvironment, aStackTop, 2);
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.Multiply(x,y,aEnvironment.precision());
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	/// Corresponds to the Piper function \c MathAdd.
	/// If called with one argument (unary plus), this argument is
	/// converted to BigNumber. If called with two arguments (binary plus),
	/// both argument are converted to a BigNumber, and these are added
	/// together at the current precision. The sum is returned.
	/// \sa getNumber(), BigNumber::Add()
	class LispAdd extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			int length = Standard.internalListLength(ARGUMENT(aEnvironment, aStackTop, 0));
			if (length == 2)
			{
				BigNumber x;
				x = BuiltinFunctions.getNumber(aEnvironment, aStackTop, 1);
				RESULT(aEnvironment, aStackTop).set(new Number(x));
				return;
			}
			else
			{
				BigNumber x = BuiltinFunctions.getNumber(aEnvironment, aStackTop, 1);
				BigNumber y = BuiltinFunctions.getNumber(aEnvironment, aStackTop, 2);
				int bin = aEnvironment.precision();
				BigNumber z = new BigNumber(bin);
				z.Add(x,y,aEnvironment.precision());
				RESULT(aEnvironment, aStackTop).set(new Number(z));
				return;
			}
		}
	}

	class LispSubtract extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			int length = Standard.internalListLength(ARGUMENT(aEnvironment, aStackTop, 0));
			if (length == 2)
			{
				BigNumber x = getNumber(aEnvironment, aStackTop, 1);
				BigNumber z = new BigNumber(x);
				z.Negate(x);
				RESULT(aEnvironment, aStackTop).set(new Number(z));
				return;
			}
			else
			{
				BigNumber x = getNumber(aEnvironment, aStackTop, 1);
				BigNumber y = getNumber(aEnvironment, aStackTop, 2);
				BigNumber yneg = new BigNumber(y);
				yneg.Negate(y);
				BigNumber z = new BigNumber(aEnvironment.precision());
				z.Add(x,yneg,aEnvironment.precision());
				RESULT(aEnvironment, aStackTop).set(new Number(z));
				return;
			}
		}
	}

	class LispDivide extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber y = getNumber(aEnvironment, aStackTop, 2);
			BigNumber z = new BigNumber(aEnvironment.precision());
			// if both arguments are integers, then BigNumber::Divide would perform an integer divide, but we want a float divide here.
			if (x.IsInt() && y.IsInt())
			{
				// why can't we just say BigNumber temp; ?
				BigNumber tempx = new BigNumber(aEnvironment.precision());
				tempx.SetTo(x);
				tempx.BecomeFloat(aEnvironment.precision());  // coerce x to float
				BigNumber tempy = new BigNumber(aEnvironment.precision());
				tempy.SetTo(y);
				tempy.BecomeFloat(aEnvironment.precision());  // coerce x to float
				z.Divide(tempx, tempy,aEnvironment.precision());
			}
			else
			{
				z.Divide(x, y,aEnvironment.precision());
			}
			RESULT(aEnvironment, aStackTop).set(new Number(z));
			return;
		}
	}

	class PiperBuiltinPrecisionSet extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer index = new Pointer();
			index.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get() != null, 1);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get().string() != null, 1);

			int ind = Integer.parseInt(index.get().string(),10);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ind>0,1);
			aEnvironment.setPrecision(ind);
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispGetExactBits extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.SetTo(
			        (x.IsInt())
			        ? x.BitCount()  // for integers, return the bit count
			        : Standard.digits_to_bits((long)(x.GetPrecision()), 10)   // for floats, return the precision
			);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}


	class LispSetExactBits extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber y = getNumber(aEnvironment, aStackTop, 2);
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.SetTo(x);

			// do nothing for integers
			if (!(z.IsInt()))
				z.Precision((int)(Standard.bits_to_digits((long)(y.Double()), 10)));
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispBitCount extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.SetTo(x.BitCount());
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispMathSign extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.SetTo(x.Sign());
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispMathIsSmall extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), x.IsSmall());
		}
	}

	class LispMathNegate extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.Negate(x);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispFloor extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.Floor(x);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispCeil extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.Negate(x);
			z.Floor(z);
			z.Negate(z);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispAbs extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.SetTo(x);
			if (x.Sign()<0)
				z.Negate(x);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispMod extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber y = getNumber(aEnvironment, aStackTop, 2);
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.Mod(x,y);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispDiv extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber y = getNumber(aEnvironment, aStackTop, 2);
			if (x.IsInt() && y.IsInt())
			{  // both integer, perform integer division
				BigNumber z = new BigNumber(aEnvironment.precision());
				z.Divide(x,y,aEnvironment.precision());
				RESULT(aEnvironment, aStackTop).set(new Number(z));
				return;
			}
			else
			{
				throw new Exception("LispDiv: error: both arguments must be integer");
			}
		}
	}

	class LispBitsToDigits extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber y = getNumber(aEnvironment, aStackTop, 2);
			long result = 0;  // initialize just in case
			if (x.IsInt() && x.IsSmall() && y.IsInt() && y.IsSmall())
			{
				// bits_to_digits uses unsigned long, see numbers.h
				int base = (int)y.Double();
				result = Standard.bits_to_digits((long)(x.Double()), base);
			}
			else
			{
				throw new PiperException("BitsToDigits: error: arguments ("+x.Double()+", "+y.Double()+") must be small integers");
			}
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.SetTo((long)result);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispDigitsToBits extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber y = getNumber(aEnvironment, aStackTop, 2);
			long result = 0;  // initialize just in case
			if (x.IsInt() && x.IsSmall() && y.IsInt() && y.IsSmall())
			{
				// bits_to_digits uses unsigned long, see numbers.h
				int base = (int)y.Double();
				result = Standard.digits_to_bits((long)(x.Double()), base);
			}
			else
			{
				throw new PiperException("BitsToDigits: error: arguments ("+x.Double()+", "+y.Double()+") must be small integers");
			}
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.SetTo((long)result);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispGcd extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber y = getNumber(aEnvironment, aStackTop, 2);
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.Gcd(x,y);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispSystemCall extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
			String orig = ARGUMENT(aEnvironment, aStackTop, 1).get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
			String oper = Standard.internalUnstringify(orig);
			String ls_str;
			Process ls_proc = Runtime.getRuntime().exec(oper);
			// get its output (your input) stream
			BufferedReader ls_in = new BufferedReader(new InputStreamReader(ls_proc.getInputStream()));

			while ((ls_str = ls_in.readLine()) != null)
			{
				aEnvironment.iCurrentOutput.Write(ls_str);
				aEnvironment.iCurrentOutput.Write("\n");
			}
		}
	}

	class LispFastArcSin extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x;
			x = getNumber(aEnvironment, aStackTop, 1);
			double result = Math.asin(x.Double());
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.SetTo(result);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispFastLog extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x;
			x = getNumber(aEnvironment, aStackTop, 1);
			double result = Math.log(x.Double());
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.SetTo(result);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispFastPower extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x, y;
			x = getNumber(aEnvironment, aStackTop, 1);
			y = getNumber(aEnvironment, aStackTop, 2);
			double result = Math.pow(x.Double(), y.Double());
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.SetTo(result);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispShiftLeft extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber n = getNumber(aEnvironment, aStackTop, 2);
			long nrToShift = n.Long();
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.ShiftLeft(x,(int)nrToShift);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispShiftRight extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber n = getNumber(aEnvironment, aStackTop, 2);
			long nrToShift = n.Long();
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.ShiftRight(x,(int)nrToShift);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispFromBase extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			// Get the base to convert to:
			// Evaluate first argument, and store result in oper
			Pointer oper = new Pointer();
			oper.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			// Check that result is a number, and that it is in fact an integer
			BigNumber num = oper.get().number(aEnvironment.precision());
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,num != null,1);
			// check that the base is an integer between 2 and 32
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,num.IsInt(), 1);

			// Get a short platform integer from the first argument
			int base = (int)(num.Double());

			// Get the number to convert
			Pointer fromNum = new Pointer();
			fromNum.set(ARGUMENT(aEnvironment, aStackTop, 2).get());
			String str2;
			str2 = fromNum.get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,str2 != null,2);

			// Added, unquote a string
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,Standard.internalIsString(str2),2);
			str2 = aEnvironment.hashTable().lookUpUnStringify(str2);

			// convert using correct base
			BigNumber z = new BigNumber(str2,aEnvironment.precision(),base);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispToBase extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			// Get the base to convert to:
			// Evaluate first argument, and store result in oper
			Pointer oper = new Pointer();
			oper.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			// Check that result is a number, and that it is in fact an integer
			BigNumber num = oper.get().number(aEnvironment.precision());
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,num != null,1);
			// check that the base is an integer between 2 and 32
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,num.IsInt(), 1);

			// Get a short platform integer from the first argument
			int base = (int)(num.Long());

			// Get the number to convert
			BigNumber x = getNumber(aEnvironment, aStackTop, 2);

			// convert using correct base
			String str;
			str = x.ToString(aEnvironment.precision(),base);
			// Get unique string from hash table, and create an atom from it.

			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,aEnvironment.hashTable().lookUpStringify(str)));
		}
	}

	class LispMaxEvalDepth extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer index = new Pointer();
			index.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get() != null, 1);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get().string() != null, 1);

			int ind = Integer.parseInt(index.get().string(),10);
			aEnvironment.iMaxEvalDepth = ind;
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispDefLoad extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			LispError.CHK_CORE(aEnvironment, aStackTop,aEnvironment.iSecure == false, LispError.KLispErrSecurityBreach);

			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

			// Get file name
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.get() != null, 1);
			String orig = evaluated.get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

			Standard.loadDefFile(aEnvironment, orig);
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispUse extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

			// Get file name
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.get() != null, 1);
			String orig = evaluated.get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

			Standard.internalUse(aEnvironment,orig);
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispRightAssociative extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			// Get operator
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
			String orig = ARGUMENT(aEnvironment, aStackTop, 1).get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
			aEnvironment.iInfixOperators.SetRightAssociative(Standard.symbolName(aEnvironment,orig));
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispLeftPrecedence extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			// Get operator
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
			String orig = ARGUMENT(aEnvironment, aStackTop, 1).get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

			Pointer index = new Pointer();
			aEnvironment.iEvaluator.eval(aEnvironment, index, ARGUMENT(aEnvironment, aStackTop, 2));
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get() != null, 2);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get().string() != null, 2);
			int ind = Integer.parseInt(index.get().string(),10);

			aEnvironment.iInfixOperators.SetLeftPrecedence(Standard.symbolName(aEnvironment,orig),ind);
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispRightPrecedence extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			// Get operator
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
			String orig = ARGUMENT(aEnvironment, aStackTop, 1).get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

			Pointer index = new Pointer();
			aEnvironment.iEvaluator.eval(aEnvironment, index, ARGUMENT(aEnvironment, aStackTop, 2));
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get() != null, 2);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get().string() != null, 2);
			int ind = Integer.parseInt(index.get().string(),10);

			aEnvironment.iInfixOperators.SetRightPrecedence(Standard.symbolName(aEnvironment,orig),ind);
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispIsBodied extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			InfixOperator op = BuiltinFunctions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iBodiedOperators);
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), op != null);
		}
	}

	class LispIsInFix extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			InfixOperator op = BuiltinFunctions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iInfixOperators);
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), op != null);
		}
	}

	class LispIsPreFix extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			InfixOperator op = BuiltinFunctions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iPrefixOperators);
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), op != null);
		}
	}

	class LispIsPostFix extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			InfixOperator op = BuiltinFunctions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iPostfixOperators);
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), op != null);
		}
	}

	class LispGetPrecedence extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			InfixOperator op = BuiltinFunctions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iInfixOperators);
			if (op == null)
			{  // also need to check for a postfix or prefix operator
				op = BuiltinFunctions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iPrefixOperators);
				if (op == null)
				{
					op = BuiltinFunctions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iPostfixOperators);
					if (op == null)
					{  // or maybe it's a bodied function
						op = BuiltinFunctions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iBodiedOperators);
						LispError.CHK_CORE(aEnvironment,aStackTop,op!=null, LispError.KLispErrIsNotInFix);
					}
				}
			}
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,""+op.iPrecedence));
		}
	}

	class LispGetLeftPrecedence extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			InfixOperator op = BuiltinFunctions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iInfixOperators);
			if (op == null)
			{  // infix and postfix operators have left precedence
				op = BuiltinFunctions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iPostfixOperators);
				LispError.CHK_CORE(aEnvironment,aStackTop,op!=null, LispError.KLispErrIsNotInFix);
			}
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,""+op.iLeftPrecedence));
		}
	}

	class LispGetRightPrecedence extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			InfixOperator op = BuiltinFunctions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iInfixOperators);
			if (op == null)
			{   // bodied, infix and prefix operators have right precedence
				op = BuiltinFunctions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iPrefixOperators);
				if (op == null)
				{   // or maybe it's a bodied function
					op = BuiltinFunctions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iBodiedOperators);
					LispError.CHK_CORE(aEnvironment,aStackTop,op!=null, LispError.KLispErrIsNotInFix);
				}
			}
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,""+op.iRightPrecedence));
		}
	}

	class PiperBuiltinPrecisionGet extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			// decimal precision
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,""+aEnvironment.precision()));
		}
	}

	class LispBitAnd extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber y = getNumber(aEnvironment, aStackTop, 2);
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.BitAnd(x,y);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispBitOr extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber y = getNumber(aEnvironment, aStackTop, 2);
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.BitOr(x,y);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispBitXor extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber y = getNumber(aEnvironment, aStackTop, 2);
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.BitXor(x,y);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispSecure extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			boolean prevSecure = aEnvironment.iSecure;
			aEnvironment.iSecure = true;
			try
			{
				aEnvironment.iEvaluator.eval(aEnvironment, RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 1));
			}
			catch (Exception e) { throw e; }
			finally { aEnvironment.iSecure = prevSecure; }
		}
	}

	class LispFindFile extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			LispError.CHK_CORE(aEnvironment, aStackTop,aEnvironment.iSecure == false, LispError.KLispErrSecurityBreach);

			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

			// Get file name
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.get() != null, 1);
			String orig = evaluated.get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
			String oper = Standard.internalUnstringify(orig);

			String filename = Standard.internalFindFile(oper, aEnvironment.iInputDirectories);
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,aEnvironment.hashTable().lookUpStringify(filename)));
		}
	}

	class LispFindFunction extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			LispError.CHK_CORE(aEnvironment, aStackTop,aEnvironment.iSecure == false, LispError.KLispErrSecurityBreach);

			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

			// Get file name
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.get() != null, 1);
			String orig = evaluated.get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
			String oper = Standard.internalUnstringify(orig);

			MultiUserFunction multiUserFunc =
			        aEnvironment.multiUserFunction(aEnvironment.hashTable().lookUp(oper));
			if (multiUserFunc != null)
			{
				DefFile def = multiUserFunc.iFileToOpen;
				if (def != null)
				{
					RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,def.iFileName));
					return;
				}
			}
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,"\"\""));
		}
	}

	class LispIsGeneric extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), evaluated.get().generic() != null);
		}
	}

	class LispGenericTypeName extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.get().generic() != null,1);
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,evaluated.get().generic().TypeName()));
		}
	}

	class GenArrayCreate extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer sizearg = new Pointer();
			sizearg.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.get() != null, 1);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.get().string() != null, 1);

			int size = Integer.parseInt(sizearg.get().string(),10);

			Pointer initarg = new Pointer();
			initarg.set(ARGUMENT(aEnvironment, aStackTop, 2).get());

			ArrayClass array = new ArrayClass(size,initarg.get());
			RESULT(aEnvironment, aStackTop).set(GenericClass.getInstance(array));
		}
	}

	class GenArraySize extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

			GenericClassContainer gen = evaluated.get().generic();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen != null,1);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen.TypeName().equals("\"Array\""),1);
			int size=((ArrayClass)gen).Size();
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,""+size));
		}
	}

	class GenArrayGet extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

			GenericClassContainer gen = evaluated.get().generic();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen != null,1);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen.TypeName().equals("\"Array\""),1);

			Pointer sizearg = new Pointer();
			sizearg.set(ARGUMENT(aEnvironment, aStackTop, 2).get());

			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.get() != null, 2);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.get().string() != null, 2);

			int size = Integer.parseInt(sizearg.get().string(),10);

			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,size>0 && size<=((ArrayClass)gen).Size(),2);
			Cons object = ((ArrayClass)gen).GetElement(size);

			RESULT(aEnvironment, aStackTop).set(object.copy(false));
		}
	}

	class GenArraySet extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

			GenericClassContainer gen = evaluated.get().generic();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen != null,1);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen.TypeName().equals("\"Array\""),1);

			Pointer sizearg = new Pointer();
			sizearg.set(ARGUMENT(aEnvironment, aStackTop, 2).get());

			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.get() != null, 2);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.get().string() != null, 2);

			int size = Integer.parseInt(sizearg.get().string(),10);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,size>0 && size<=((ArrayClass)gen).Size(),2);

			Pointer obj = new Pointer();
			obj.set(ARGUMENT(aEnvironment, aStackTop, 3).get());
			((ArrayClass)gen).SetElement(size,obj.get());
			Standard.internalTrue( aEnvironment, RESULT(aEnvironment, aStackTop));
		}
	}

	class LispCustomEval extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : CustomEval");////TODO fixme
			throw new PiperException("Function not yet supported");
		}
	}

	class LispCustomEvalExpression extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : CustomEvalExpression");////TODO fixme
			throw new PiperException("Function not yet supported");
		}
	}

	class LispCustomEvalResult extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : CustomEvalResult");////TODO fixme
			throw new PiperException("Function not yet supported");
		}
	}

	class LispCustomEvalLocals extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispCustomEvalLocals");////TODO fixme
			throw new PiperException("Function not yet supported");
		}
	}

	class LispCustomEvalStop extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispCustomEvalStop");////TODO fixme
			throw new PiperException("Function not yet supported");
		}
	}

	class LispTraceRule extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispTraceRule");////TODO fixme
			throw new PiperException("Function not yet supported");
		}
	}

	class LispTraceStack extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : TraceStack");////TODO fixme
			throw new PiperException("Function not yet supported");
		}
	}

	class LispReadLisp extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Parser parser = new Parser(aEnvironment.iCurrentTokenizer,
			                                   aEnvironment.iCurrentInput,
			                                   aEnvironment);
			// Read expression
			parser.Parse(RESULT(aEnvironment, aStackTop));
		}
	}

	class LispReadLispListed extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Parser parser = new Parser(aEnvironment.iCurrentTokenizer,
			                                   aEnvironment.iCurrentInput,
			                                   aEnvironment);
			parser.iListed = true;
			// Read expression
			parser.Parse(RESULT(aEnvironment, aStackTop));
		}
	}

	class LispType extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			Pointer subList = evaluated.get().subList();
			Cons head = null;
			if (subList == null)
			{
				RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,"\"\""));
				return;
			}
			head = subList.get();
			if (head.string() == null)
			{
				RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,"\"\""));
				return;
			}
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,aEnvironment.hashTable().lookUpStringify(head.string())));
			return;
		}
	}

	class PiperStringMidGet extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 3).get());
			LispError.CHK_ISSTRING_CORE(aEnvironment,aStackTop,evaluated,3);
			String orig = evaluated.get().string();

			Pointer index = new Pointer();
			index.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get() != null, 1);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get().string() != null, 1);
			int from = Integer.parseInt(index.get().string(),10);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,from>0,1);

			index.set(ARGUMENT(aEnvironment, aStackTop, 2).get());
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get() != null, 2);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get().string() != null, 2);
			int count = Integer.parseInt(index.get().string(),10);


			String str = "\""+orig.substring(from,from+count)+"\"";
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,str));
		}
	}

	class PiperStringMidSet extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 3).get());
			LispError.CHK_ISSTRING_CORE(aEnvironment,aStackTop,evaluated,3);
			String orig = evaluated.get().string();
			Pointer index = new Pointer();
			index.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get() != null, 1);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.get().string() != null, 1);
			int from = Integer.parseInt(index.get().string(),10);

			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,from>0,1);

			Pointer ev2 = new Pointer();
			ev2.set(ARGUMENT(aEnvironment, aStackTop, 2).get());
			LispError.CHK_ISSTRING_CORE(aEnvironment,aStackTop,ev2,2);
			String replace = ev2.get().string();

			LispError.CHK_CORE(aEnvironment, aStackTop,from+replace.length()-2<orig.length(), LispError.KLispErrInvalidArg);
			String str;
			str = orig.substring(0,from);
			str = str + replace.substring(1,replace.length()-1);
			//System.out.println("from="+from+replace.length()-2);
			str = str + orig.substring(from+replace.length()-2,orig.length());
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,str));
		}
	}

	class GenPatternCreate extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer pattern = new Pointer();
			pattern.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			Pointer postpredicate = new Pointer();
			postpredicate.set(ARGUMENT(aEnvironment, aStackTop, 2).get());

			Iterator iter = new Iterator(pattern);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.GetObject() != null,1);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.GetObject().subList() != null,1);
			iter.GoSub();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.GetObject() != null,1);
			iter.GoNext();

			Pointer ptr = iter.Ptr();


			PiperPatternPredicateBase matcher =
			        new PiperPatternPredicateBase(aEnvironment, ptr,postpredicate);
			PatternClass p = new PatternClass(matcher);
			RESULT(aEnvironment, aStackTop).set(GenericClass.getInstance(p));
		}
	}

	class GenPatternMatches extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer pattern = new Pointer();
			pattern.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			GenericClassContainer gen = pattern.get().generic();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen != null,1);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen.TypeName().equals("\"Pattern\""),1);

			Pointer list = new Pointer();
			list.set(ARGUMENT(aEnvironment, aStackTop, 2).get());

			PatternClass patclass = (PatternClass)gen;

			Iterator iter = new Iterator(list);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.GetObject() != null,2);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.GetObject().subList() != null,2);
			iter.GoSub();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.GetObject() != null,2);
			iter.GoNext();

			Pointer ptr = iter.Ptr();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ptr != null,2);
			boolean matches = patclass.Matches(aEnvironment,ptr);
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop),matches);
		}
	}

	class LispRuleBaseDefined extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer name = new Pointer();
			name.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			String orig = name.get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
			String oper = Standard.internalUnstringify(orig);

			Pointer sizearg = new Pointer();
			sizearg.set(ARGUMENT(aEnvironment, aStackTop, 2).get());
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.get() != null, 2);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.get().string() != null, 2);

			int arity = Integer.parseInt(sizearg.get().string(),10);

			UserFunction userFunc = aEnvironment.userFunction(aEnvironment.hashTable().lookUp(oper),arity);
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop),userFunc != null);
		}
	}

	class LispDefLoadFunction extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer name = new Pointer();
			name.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			String orig = name.get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
			String oper = Standard.internalUnstringify(orig);

			MultiUserFunction multiUserFunc =
			        aEnvironment.multiUserFunction(aEnvironment.hashTable().lookUp(oper));
			if (multiUserFunc != null)
			{
				if (multiUserFunc.iFileToOpen!=null)
				{
					DefFile def = multiUserFunc.iFileToOpen;
					if (!def.iIsLoaded)
					{
						multiUserFunc.iFileToOpen=null;
						Standard.internalUse(aEnvironment,def.iFileName);
					}
				}
			}
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispRuleBaseArgList extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer name = new Pointer();
			name.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			String orig = name.get().string();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
			String oper = Standard.internalUnstringify(orig);

			Pointer sizearg = new Pointer();
			sizearg.set(ARGUMENT(aEnvironment, aStackTop, 2).get());
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.get() != null, 2);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.get().string() != null, 2);

			int arity = Integer.parseInt(sizearg.get().string(),10);

			UserFunction userFunc = aEnvironment.userFunction(aEnvironment.hashTable().lookUp(oper),arity);
			LispError.CHK_CORE(aEnvironment, aStackTop,userFunc != null, LispError.KLispErrInvalidArg);

			Pointer list = userFunc.ArgList();
			Pointer head = new Pointer();
			head.set(aEnvironment.iList.copy(false));
			head.get().cdr().set(list.get());
			RESULT(aEnvironment, aStackTop).set(SubList.getInstance(head.get()));
		}
	}

	class LispNewRulePattern extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			internalNewRulePattern(aEnvironment, aStackTop, false);
		}
	}

	class LispMacroNewRulePattern extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			internalNewRulePattern(aEnvironment, aStackTop, true
			                      );
		}
	}

	class LispSubst extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer from = new Pointer(),to = new Pointer(),body = new Pointer();
			from.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			to  .set(ARGUMENT(aEnvironment, aStackTop, 2).get());
			body.set(ARGUMENT(aEnvironment, aStackTop, 3).get());
			SubstBehaviour behaviour = new SubstBehaviour(aEnvironment,from, to);
			Standard.internalSubstitute(RESULT(aEnvironment, aStackTop), body, behaviour);
		}
	}

	class LispLocalSymbols extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			int nrArguments = Standard.internalListLength(ARGUMENT(aEnvironment, aStackTop, 0));
			int nrSymbols = nrArguments-2;

			String names[] = new String[nrSymbols];
			String localnames[] = new String[nrSymbols];

			int uniquenumber = aEnvironment.getUniqueId();
			int i;
			for (i=0;i<nrSymbols;i++)
			{
				String atomname = argument(ARGUMENT(aEnvironment, aStackTop, 0), i+1).get().string();
				LispError.CHK_ARG_CORE(aEnvironment,aStackTop,atomname != null, i+1);
				names[i] = atomname;
				int len = atomname.length();
				String newname = "$"+atomname+uniquenumber;
				String variable = aEnvironment.hashTable().lookUp(newname);
				localnames[i] = variable;
			}
			LocalSymbolBehaviour behaviour = new LocalSymbolBehaviour(aEnvironment,names,localnames,nrSymbols);
			Pointer result = new Pointer();
			Standard.internalSubstitute(result, argument(ARGUMENT(aEnvironment, aStackTop, 0), nrArguments-1), behaviour);
			aEnvironment.iEvaluator.eval(aEnvironment, RESULT(aEnvironment, aStackTop), result);
		}
	}

	class LispFastIsPrime extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			//TODO fixme this routine should actually be called SlowIsPrime ;-)
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			long n = x.Long();
			long result = 1;

			// We only want people to pass in small integers
			if (n>65538)
				result = 0;

			int i=2;
			int max = (int)(1+Math.sqrt(n));
			//System.out.println("n = "+n+" max = "+max);
			while (i<=max && result == 1)
			{
				//System.out.println(""+n+"%"+i+" = "+(n%i));
				if ((n%i) == 0)
					result = 0;
				i++;
			}

			BigNumber z = new BigNumber(aEnvironment.precision());
			z.SetTo(result);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	class LispFac extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).get().number(0) != null,1);
			Pointer arg = ARGUMENT(aEnvironment, aStackTop, 1);

			//TODO fixme I am sure this can be optimized still
			int nr = (int)arg.get().number(0).Long();
			LispError.Check(nr>=0,LispError.KLispErrInvalidArg);
			BigNumber fac = new BigNumber("1",10,10);
			int i;
			for (i=2;i<=nr;i++)
			{
				BigNumber m = new BigNumber(""+i,10,10);
				m.Multiply(fac,m,0);
				fac = m;
			}
			RESULT(aEnvironment, aStackTop).set(new Number(fac));
		}
	}

	class LispApplyPure extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer oper = new Pointer();
			oper.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			Pointer args = new Pointer();
			args.set(ARGUMENT(aEnvironment, aStackTop, 2).get());

			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,args.get().subList() != null,2);
			LispError.CHK_CORE(aEnvironment, aStackTop,args.get().subList().get() != null,2);

			// Apply a pure string
			if (oper.get().string() != null)
			{
				Standard.internalApplyString(aEnvironment, RESULT(aEnvironment, aStackTop),
				                                 oper.get().string(),
				                                 args.get().subList().get().cdr());
			}
			else
			{   // Apply a pure function {args,body}.
				Pointer args2 = new Pointer();
				args2.set(args.get().subList().get().cdr().get());
				LispError.CHK_ARG_CORE(aEnvironment,aStackTop,oper.get().subList() != null,1);
				LispError.CHK_ARG_CORE(aEnvironment,aStackTop,oper.get().subList().get() != null,1);
				Standard.internalApplyPure(oper,args2,RESULT(aEnvironment, aStackTop),aEnvironment);
			}
		}
	}


	class PiperPrettyReaderSet extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			int nrArguments = Standard.internalListLength(ARGUMENT(aEnvironment, aStackTop, 0));
			if (nrArguments == 1)
			{
				aEnvironment.iPrettyReader = null;
			}
			else
			{
				LispError.CHK_CORE(aEnvironment, aStackTop,nrArguments == 2,LispError.KLispErrWrongNumberOfArgs);
				Pointer oper = new Pointer();
				oper.set(ARGUMENT(aEnvironment, aStackTop, 0).get());
				oper.goNext();
				LispError.CHK_ISSTRING_CORE(aEnvironment,aStackTop,oper,1);
				aEnvironment.iPrettyReader = oper.get().string();
			}
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class PiperPrettyReaderGet extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			if (aEnvironment.iPrettyReader == null)
				RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,"\"\""));
			else
				RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,aEnvironment.iPrettyReader));
		}
	}

	class PiperPrettyPrinterSet extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			int nrArguments = Standard.internalListLength(ARGUMENT(aEnvironment, aStackTop, 0));
			if (nrArguments == 1)
			{
				aEnvironment.iPrettyPrinter = null;
			}
			else
			{
				LispError.CHK_CORE(aEnvironment, aStackTop,nrArguments == 2,LispError.KLispErrWrongNumberOfArgs);
				Pointer oper = new Pointer();
				oper.set(ARGUMENT(aEnvironment, aStackTop, 0).get());
				oper.goNext();
				LispError.CHK_ISSTRING_CORE(aEnvironment,aStackTop,oper,1);
				aEnvironment.iPrettyPrinter = oper.get().string();
			}
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class PiperPrettyPrinterGet extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			if (aEnvironment.iPrettyPrinter == null)
				RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,"\"\""));
			else
				RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,aEnvironment.iPrettyPrinter));
		}
	}

	class LispGarbageCollect extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.hashTable().garbageCollect();
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispPatchLoad extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : PatchLoad");//TODO FIXME
			throw new PiperException("Function not yet supported");
		}
	}

	class LispPatchString extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : PatchString");//TODO FIXME
			throw new PiperException("Function not yet supported");
		}
	}

	class PiperExtraInfoSet extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer object = new Pointer();
			object.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

			Pointer info = new Pointer();
			info.set(ARGUMENT(aEnvironment, aStackTop, 2).get());

			RESULT(aEnvironment, aStackTop).set( object.get().setExtraInfo(info) );
		}
	}

	class PiperExtraInfoGet extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer object = new Pointer();
			object.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

			Pointer result = object.get().extraInfo();
			if (result == null)
			{
				Standard.internalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
			}
			else if (result.get() == null)
			{
				Standard.internalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
			}
			else
			{
				RESULT(aEnvironment, aStackTop).set(result.get());
			}
		}
	}

	class LispDefaultTokenizer extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentTokenizer = aEnvironment.iDefaultTokenizer;
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispCommonLispTokenizer extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispCommonLispTokenizer");//TODO FIXME
			throw new PiperException("Function not yet supported");
		}
	}

	class LispXmlTokenizer extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentTokenizer = aEnvironment.iXmlTokenizer;
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispExplodeTag extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer out = new Pointer();
			out.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			LispError.CHK_ISSTRING_CORE(aEnvironment,aStackTop,out,1);

			String str = out.get().string();
			int strInd = 0;
			strInd++;
			if (str.charAt(strInd) != '<')
			{
				RESULT(aEnvironment, aStackTop).set(out.get());
				return;
			}
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,str.charAt(strInd) == '<',1);
			strInd++;
			String type = "\"Open\"";

			if (str.charAt(strInd) == '/')
			{
				type = "\"Close\"";
				strInd++;
			}
			String tag = new String();

			tag = tag + "\"";
			while (Tokenizer.isAlpha(str.charAt(strInd)))
			{
				char c = str.charAt(strInd);
				strInd++;
				if (c >= 'a' && c <= 'z')
					c = (char)(c + ('A'-'a'));
				tag = tag + c;
			}
			tag = tag + "\"";

			Cons info = null;

			while (str.charAt(strInd) == ' ') strInd++;
			while (str.charAt(strInd) != '>' && str.charAt(strInd) != '/')
			{
				String name = new String();
				name = name + "\"";

				while (Tokenizer.isAlpha(str.charAt(strInd)))
				{
					char c = str.charAt(strInd);
					strInd++;
					if (c >= 'a' && c <= 'z')
						c = (char)(c + ('A'-'a'));
					name = name + c;
				}
				name = name + "\"";
				LispError.CHK_ARG_CORE(aEnvironment,aStackTop,str.charAt(strInd) == '=',1);
				strInd++;
				LispError.CHK_ARG_CORE(aEnvironment,aStackTop,str.charAt(strInd) == '\"',1);
				String value = new String();

				value = value + (str.charAt(strInd));
				strInd++;
				while (str.charAt(strInd) != '\"')
				{
					value = value + (str.charAt(strInd));
					strInd++;
				}
				value = value + (str.charAt(strInd));
				strInd++;

				//printf("[%s], [%s]\n",name.String(),value.String());
				{
					Cons ls = Atom.getInstance(aEnvironment,"List");
					Cons nm = Atom.getInstance(aEnvironment,name);
					Cons vl = Atom.getInstance(aEnvironment,value);
					nm.cdr().set(vl);
					ls.cdr().set(nm);
					Cons newinfo =  SubList.getInstance(ls);
					newinfo.cdr().set(info);
					info = newinfo;
				}
				while (str.charAt(strInd) == ' ') strInd++;

				//printf("End is %c\n",str[0]);
			}
			if (str.charAt(strInd) == '/')
			{
				type = "\"OpenClose\"";
				strInd++;
				while (str.charAt(strInd) == ' ') strInd++;
			}

			{
				Cons ls = Atom.getInstance(aEnvironment,"List");
				ls.cdr().set(info);
				info = SubList.getInstance(ls);
			}

			Cons xm = Atom.getInstance(aEnvironment,"XmlTag");
			Cons tg = Atom.getInstance(aEnvironment,tag);
			Cons tp = Atom.getInstance(aEnvironment,type);
			info.cdr().set(tp);
			tg.cdr().set(info);
			xm.cdr().set(tg);
			RESULT(aEnvironment, aStackTop).set(SubList.getInstance(xm));

		}
	}

	class PiperBuiltinAssoc extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			// key to find
			Pointer key = new Pointer();
			key.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

			// assoc-list to find it in
			Pointer list = new Pointer();
			list.set(ARGUMENT(aEnvironment, aStackTop, 2).get());

			Cons t;

			//Check that it is a compound object
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,list.get().subList() != null, 2);
			t = list.get().subList().get();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,t != null, 2);
			t = t.cdr().get();

			while (t != null)
			{
				if (t.subList() != null)
				{
					Cons sub = t.subList().get();
					if (sub != null)
					{
						sub = sub.cdr().get();
						Pointer temp = new Pointer();
						temp.set(sub);
						if(Standard.internalEquals(aEnvironment,key,temp))
						{
							RESULT(aEnvironment, aStackTop).set(t);
							return;
						}
					}
				}
				t = t.cdr().get();
			}
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,"Empty"));
		}
	}

	class LispCurrentFile extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,aEnvironment.hashTable().lookUpStringify(aEnvironment.iInputStatus.FileName())));
		}
	}

	class LispCurrentLine extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,""+aEnvironment.iInputStatus.LineNumber()));
		}
	}

	class LispBackQuote extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BackQuoteBehaviour behaviour = new BackQuoteBehaviour(aEnvironment);
			Pointer result = new Pointer();
			Standard.internalSubstitute(result, ARGUMENT(aEnvironment, aStackTop,  1), behaviour);
			aEnvironment.iEvaluator.eval(aEnvironment, RESULT(aEnvironment, aStackTop), result);
		}
	}

	class LispDumpBigNumberDebugInfo extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			x.DumpDebugInfo(aEnvironment.iCurrentOutput);
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispInDebugMode extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Standard.internalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispDebugFile extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			throw new Exception("Cannot call DebugFile in non-debug version of Piper");
		}
	}

	class LispDebugLine extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			throw new Exception("Cannot call DebugLine in non-debug version of Piper");
		}
	}

	class LispVersion extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			RESULT(aEnvironment,aStackTop).set(Atom.getInstance(aEnvironment,"\"" + CVersion.version + "\""));
		}
	}


	class LispExit extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Runtime.getRuntime().exit(0);
		}
	}

	class LispExitRequested extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Standard.internalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	class LispHistorySize extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispHistorySize");//TODO FIXME
			throw new PiperException("Function not yet supported");
		}
	}

	class LispStackSize extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispStackSize");//TODO FIXME
			throw new PiperException("Function not yet supported");
		}
	}

	class LispIsPromptShown extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispIsPromptShown");//TODO FIXME
			throw new PiperException("Function not yet supported");
		}
	}

	class LispReadCmdLineString extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispReadCmdLineString");//TODO FIXME
			throw new PiperException("Function not yet supported");
		}
	}

	class LispTime extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			long starttime = System.currentTimeMillis();
			Pointer res = new Pointer();
			aEnvironment.iEvaluator.eval(aEnvironment, res, ARGUMENT(aEnvironment,aStackTop,1));
			long endtime = System.currentTimeMillis();
			double timeDiff;
			timeDiff = endtime-starttime;
			timeDiff /= 1000.0;
			RESULT(aEnvironment,aStackTop).set(Atom.getInstance(aEnvironment,""+timeDiff));
		}
	}

	class LispFileSize extends PiperEvalCaller
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer fnameObject = new Pointer();
			fnameObject.set(ARGUMENT(aEnvironment,aStackTop,1).get());
			LispError.CHK_ISSTRING_CORE(aEnvironment,aStackTop,fnameObject,1);
			String fname = Standard.internalUnstringify(fnameObject.get().string());
			String hashedname = aEnvironment.hashTable().lookUp(fname);

			long fileSize = 0;
			InputStatus oldstatus = new InputStatus(aEnvironment.iInputStatus);
			aEnvironment.iInputStatus.setTo(hashedname);
			try
			{
				// Open file
				Input newInput = // new StdFileInput(hashedname, aEnvironment.iInputStatus);
				        Standard.openInputFile(aEnvironment, aEnvironment.iInputDirectories, hashedname, aEnvironment.iInputStatus);

				LispError.Check(newInput != null, LispError.KLispErrFileNotFound);
				fileSize = newInput.StartPtr().length();
			}
			catch (Exception e)
			{
				throw e;
			}
			finally
			{
				aEnvironment.iInputStatus.RestoreFrom(oldstatus);
			}
			RESULT(aEnvironment,aStackTop).set(Atom.getInstance(aEnvironment,""+fileSize));
		}
	}


}

