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

package org.mathrider.piper.builtin;

import org.mathrider.piper.builtin.functions.*;
import org.mathrider.piper.exceptions.PiperException;
import org.mathrider.piper.io.InputStatus;
import org.mathrider.piper.*;
//import org.mathrider.piper.parametermatchers.PatternContainer;
//import org.mathrider.piper.lisp.behaviours.BackQuote;
import org.mathrider.piper.lisp.behaviours.LocalSymbol;
//import org.mathrider.piper.lisp.behaviours.Subst;
import org.mathrider.piper.lisp.userfunctions.PiperEvaluator;
import org.mathrider.piper.printers.InfixPrinter;
import org.mathrider.piper.lisp.parsers.InfixParser;
import org.mathrider.piper.io.StdFileOutput;
import org.mathrider.piper.io.StringOutput;
import org.mathrider.piper.io.StringInput;
import org.mathrider.piper.lisp.HashTable;
import org.mathrider.piper.lisp.Standard;
import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.userfunctions.UserFunction;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.parsers.Tokenizer;
import org.mathrider.piper.lisp.Number;
import org.mathrider.piper.lisp.Input;
import org.mathrider.piper.lisp.SubList;
import org.mathrider.piper.lisp.Printer;
import org.mathrider.piper.lisp.Output;
import org.mathrider.piper.lisp.userfunctions.MultiUserFunction;
import org.mathrider.piper.lisp.Cons;
import org.mathrider.piper.lisp.Atom;
import org.mathrider.piper.lisp.Iterator;
import org.mathrider.piper.lisp.BuiltinObject;
import org.mathrider.piper.lisp.parsers.Parser;
import org.mathrider.piper.lisp.DefFile;
import org.mathrider.piper.lisp.InfixOperator;
import org.mathrider.piper.lisp.Operators;
import java.io.*;

public class Functions
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
		        new PiperEvaluator(new Quote(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "Hold");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Eval(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Eval");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Write(),1, PiperEvaluator.Variable|PiperEvaluator.Function),
		        "Write");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new WriteString(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "WriteString");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new FullForm(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FullForm");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new DefaultDirectory(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DefaultDirectory");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new FromFile(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "FromFile");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new FromString(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "FromString");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Read(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Read");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new ReadToken(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "ReadToken");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new ToFile(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "ToFile");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new ToString(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "ToString");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new ToStdout(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "ToStdout");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Load(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Load");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new SetVar(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "Set");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new MacroSetVar(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "MacroSet");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new ClearVar(),1, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "Clear");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new ClearVar(),1, PiperEvaluator.Variable|PiperEvaluator.Function),
		        "MacroClear");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new NewLocal(),1, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "Local");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new NewLocal(),1, PiperEvaluator.Variable|PiperEvaluator.Function),
		        "MacroLocal");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Head(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Head");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Nth(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathNth");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Tail(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Tail");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new DestructiveReverse(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DestructiveReverse");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Length(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Length");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new List(),1, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "List");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new UnList(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "UnList");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Listify(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Listify");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Concatenate(),1, PiperEvaluator.Variable|PiperEvaluator.Function),
		        "Concat");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new ConcatenateStrings(),1, PiperEvaluator.Variable|PiperEvaluator.Function),
		        "ConcatStrings");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Delete(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Delete");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new DestructiveDelete(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DestructiveDelete");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Insert(),3, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Insert");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new DestructiveInsert(),3, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DestructiveInsert");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Replace(),3, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Replace");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new DestructiveReplace(),3, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DestructiveReplace");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Atomize(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Atom");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Stringify(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "String");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new CharString(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "CharString");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new FlatCopy(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FlatCopy");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new ProgBody(),1, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "Prog");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new While(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "While");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new If(),2, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "If");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Check(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "Check");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new TrapError(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "TrapError");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new GetCoreError(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "GetCoreError");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PreFix(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Prefix");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new InFix(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Infix");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PostFix(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Postfix");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Bodied(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Bodied");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new RuleBase(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "RuleBase");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new MacroRuleBase(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MacroRuleBase");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new RuleBaseListed(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "RuleBaseListed");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new MacroRuleBaseListed(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MacroRuleBaseListed");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new DefMacroRuleBase(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "DefMacroRuleBase");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new DefMacroRuleBaseListed(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "DefMacroRuleBaseListed");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new HoldArg(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "HoldArg");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new NewRule(),5, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "Rule");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new MacroNewRule(),5, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MacroRule");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new UnFence(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "UnFence");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Retract(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Retract");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Not(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathNot");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Not(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Not");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LazyAnd(),1, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "MathAnd");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LazyAnd(),1, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "And");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LazyOr(),1, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "MathOr");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LazyOr(),1, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "Or");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Equals(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Equals");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Equals(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "=");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LessThan(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "LessThan");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new GreaterThan(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "GreaterThan");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new IsFunction(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsFunction");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new IsAtom(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsAtom");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new IsNumber(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsNumber");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new IsInteger(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsInteger");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new IsList(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsList");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new IsString(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsString");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new IsBound(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "IsBound");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Multiply(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathMultiply");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Add(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathAdd");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Subtract(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathSubtract");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Divide(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathDivide");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PiperBuiltinPrecisionSet(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Builtin'Precision'Set");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new GetExactBits(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathGetExactBits");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new SetExactBits(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathSetExactBits");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new BitCount(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathBitCount");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new MathSign(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathSign");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new MathIsSmall(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathIsSmall");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new MathNegate(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathNegate");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Floor(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathFloor");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Ceil(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathCeil");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Abs(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathAbs");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Mod(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathMod");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Div(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathDiv");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new BitsToDigits(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "BitsToDigits");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new DigitsToBits(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DigitsToBits");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Gcd(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathGcd");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new SystemCall(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "SystemCall");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new FastArcSin(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FastArcSin");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new FastLog(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FastLog");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new FastPower(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FastPower");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new ShiftLeft(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "ShiftLeft");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new ShiftRight(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "ShiftRight");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new FromBase(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FromBase");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new ToBase(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "ToBase");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new MaxEvalDepth(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MaxEvalDepth");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new DefLoad(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DefLoad");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Use(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Use");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new RightAssociative(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "RightAssociative");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LeftPrecedence(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "LeftPrecedence");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new RightPrecedence(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "RightPrecedence");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new IsBodied(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsBodied");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new IsInFix(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsInfix");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new IsPreFix(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsPrefix");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new IsPostFix(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsPostfix");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new GetPrecedence(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "OpPrecedence");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new GetLeftPrecedence(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "OpLeftPrecedence");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new GetRightPrecedence(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "OpRightPrecedence");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PiperBuiltinPrecisionGet(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Builtin'Precision'Get");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new BitAnd(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "BitAnd");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new BitOr(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "BitOr");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new BitXor(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "BitXor");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Secure(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "Secure");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new FindFile(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FindFile");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new FindFunction(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FindFunction");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new IsGeneric(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsGeneric");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new GenericTypeName(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
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
		        new PiperEvaluator(new CustomEval(),4, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "CustomEval");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new CustomEvalExpression(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "CustomEval'Expression");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new CustomEvalResult(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "CustomEval'Result");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new CustomEvalLocals(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "CustomEval'Locals");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new CustomEvalStop(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "CustomEval'Stop");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new TraceRule(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "TraceRule");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new TraceStack(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "TraceStack");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new ReadLisp(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "LispRead");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new ReadLispListed(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "LispReadListed");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Type(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
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
		        new PiperEvaluator(new RuleBaseDefined(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "RuleBaseDefined");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new DefLoadFunction(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DefLoadFunction");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new RuleBaseArgList(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "RuleBaseArgList");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new NewRulePattern(),5, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "RulePattern");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new MacroNewRulePattern(),5, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MacroRulePattern");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Subst(),3, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Subst");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new LocalSymbols(),1, PiperEvaluator.Variable|PiperEvaluator.Macro),
		        "LocalSymbols");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new FastIsPrime(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FastIsPrime");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Fac(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathFac");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new ApplyPure(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
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
		        new PiperEvaluator(new GarbageCollect(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "GarbageCollect");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new SetGlobalLazyVariable(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "SetGlobalLazyVariable");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PatchLoad(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "PatchLoad");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PatchString(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "PatchString");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PiperExtraInfoSet(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "ExtraInfo'Set");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PiperExtraInfoGet(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "ExtraInfo'Get");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new DefaultTokenizer(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DefaultTokenizer");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new CommonLispTokenizer(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "CommonLispTokenizer");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new XmlTokenizer(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "XmlTokenizer");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new ExplodeTag(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "XmlExplodeTag");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new PiperBuiltinAssoc(),2, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Builtin'Assoc");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new CurrentFile(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "CurrentFile");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new CurrentLine(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "CurrentLine");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new BackQuote(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "`");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new DumpBigNumberDebugInfo(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "MathDebugInfo");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new InDebugMode(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "InDebugMode");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new DebugFile(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DebugFile");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new DebugLine(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "DebugLine");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Version(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Version");

		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Exit(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "Exit");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new ExitRequested(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsExitRequested");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new HistorySize(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "HistorySize");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new StackSize(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "StaSiz");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new IsPromptShown(),0, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "IsPromptShown");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new ReadCmdLineString(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "ReadCmdLineString");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new Time(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro),
		        "GetTime");
		aEnvironment.builtinCommands().setAssociation(
		        new PiperEvaluator(new FileSize(),1, PiperEvaluator.Fixed|PiperEvaluator.Function),
		        "FileSize");


	}


	/// Construct a BigNumber from one of the arguments.
	/// \param x (on output) the constructed bignumber
	/// \param aEnvironment the current environment
	/// \param aStackTop the index of the top of the stack
	/// \param aArgNr the index of the argument to be converted
	public static BigNumber getNumber(Environment aEnvironment, int aStackTop, int aArgNr) throws Exception
	{
		BigNumber x = BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, aArgNr).get().number(aEnvironment.precision());
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,x != null,aArgNr);
		return x;
	}

	public static void multiFix(Environment aEnvironment, int aStackTop, Operators aOps) throws Exception
	{
		// Get operator
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
		String orig = BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

		Pointer precedence = new Pointer();
		aEnvironment.iEvaluator.eval(aEnvironment, precedence, BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 2));
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,precedence.get().string() != null, 2);
		int prec = Integer.parseInt(precedence.get().string(),10);
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,prec <= InfixPrinter.KMaxPrecedence, 2);
		aOps.SetOperator(prec,Standard.symbolName(aEnvironment,orig));
		Standard.internalTrue(aEnvironment,BuiltinFunction.RESULT(aEnvironment, aStackTop));
	}
	public static void singleFix(int aPrecedence, Environment aEnvironment, int aStackTop, Operators aOps) throws Exception
	{
		// Get operator
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
		String orig = BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
		aOps.SetOperator(aPrecedence,Standard.symbolName(aEnvironment,orig));
		Standard.internalTrue(aEnvironment,BuiltinFunction.RESULT(aEnvironment, aStackTop));
	}

	public static InfixOperator operatorInfo(Environment aEnvironment,int aStackTop, Operators aOperators) throws Exception
	{
		// Get operator
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);

		Pointer evaluated = new Pointer();
		evaluated.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get());

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
	public static void internalSetVar(Environment aEnvironment, int aStackTop, boolean aMacroMode, boolean aGlobalLazyVariable) throws Exception
	{
		String varstring=null;
		if (aMacroMode)
		{
			Pointer result = new Pointer();
			aEnvironment.iEvaluator.eval(aEnvironment, result, BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1));
			varstring = result.get().string();
		}
		else
		{
			varstring = BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
		}
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,varstring != null,1);
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,!Standard.isNumber(varstring,true),1);

		Pointer result = new Pointer();
		aEnvironment.iEvaluator.eval(aEnvironment, result, BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 2));
		aEnvironment.setVariable(varstring, result, aGlobalLazyVariable);
		Standard.internalTrue(aEnvironment,BuiltinFunction.RESULT(aEnvironment, aStackTop));
	}

	public static void internalDelete(Environment aEnvironment, int aStackTop, boolean aDestructive) throws Exception
	{
		Pointer evaluated = new Pointer();
		evaluated.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get());
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
		index.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 2).get());
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
		BuiltinFunction.RESULT(aEnvironment, aStackTop).set(SubList.getInstance(copied.get()));
	}


	public static void internalInsert(Environment aEnvironment, int aStackTop, boolean aDestructive) throws Exception
	{
		Pointer evaluated = new Pointer();
		evaluated.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get());
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
		index.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 2).get());
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
		toInsert.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 3).get());
		toInsert.get().cdr().set(iter.GetObject());
		iter.Ptr().set(toInsert.get());
		BuiltinFunction.RESULT(aEnvironment, aStackTop).set(SubList.getInstance(copied.get()));
	}






	public static void internalReplace(Environment aEnvironment, int aStackTop, boolean aDestructive) throws Exception
	{
		Pointer evaluated = new Pointer();
		evaluated.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get());
		// Ok, so lets not check if it is a list, but it needs to be at least a 'function'
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.get().subList() != null, 1);

		Pointer index = new Pointer();
		index.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 2).get());
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
		toInsert.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 3).get());
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.Ptr() != null, 2);
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.Ptr().get() != null, 2);
		toInsert.get().cdr().set(iter.Ptr().get().cdr().get());
		iter.Ptr().set(toInsert.get());
		BuiltinFunction.RESULT(aEnvironment, aStackTop).set(SubList.getInstance(copied.get()));
	}


	/// Implements the Piper functions \c RuleBase and \c MacroRuleBase .
	/// The real work is done by Environment::DeclareRuleBase().
	public static void internalRuleBase(Environment aEnvironment, int aStackTop,  boolean aListed) throws Exception
	{
		//TESTARGS(3);

		// Get operator
		Pointer args = new Pointer();
		String orig=null;

		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
		orig = BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
		args.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 2).get());

		// The arguments
		LispError.CHK_ISLIST_CORE(aEnvironment,aStackTop,args,2);

		// Finally define the rule base
		aEnvironment.declareRuleBase(Standard.symbolName(aEnvironment,orig),
		                             args.get().subList().get().cdr(),aListed);

		// Return true
		Standard.internalTrue(aEnvironment,BuiltinFunction.RESULT(aEnvironment, aStackTop));
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
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
		orig = BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
		ar.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 2).get());
		pr.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 3).get());
		predicate.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 4).get());
		body.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 5).get());

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
		Standard.internalTrue(aEnvironment,BuiltinFunction.RESULT(aEnvironment, aStackTop));
	}




	public static void internalDefMacroRuleBase(Environment aEnvironment, int aStackTop, boolean aListed) throws Exception
	{
		// Get operator
		Pointer args = new Pointer();
		Pointer body = new Pointer();
		String orig=null;

		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
		orig = BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

		// The arguments
		args.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 2).get());
		LispError.CHK_ISLIST_CORE(aEnvironment,aStackTop,args,2);

		// Finally define the rule base
		aEnvironment.declareMacroRuleBase(Standard.symbolName(aEnvironment,orig),
		                                  args.get().subList().get().cdr(),aListed);

		// Return true
		Standard.internalTrue(aEnvironment,BuiltinFunction.RESULT(aEnvironment, aStackTop));
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
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
		orig = BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
		LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
		ar.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 2).get());
		pr.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 3).get());
		predicate.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 4).get());
		body.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 5).get());

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
		Standard.internalTrue(aEnvironment,BuiltinFunction.RESULT(aEnvironment, aStackTop));
	}

        
/***************************************************************************************************************
 * Classes which implement builtin functions.
 * 
 */

























	public class IsInteger extends BuiltinFunction
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

	public class IsList extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer result = new Pointer();
			result.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop),Standard.internalIsList(result));
		}
	}

	public class IsString extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer result = new Pointer();
			result.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop),
			                             Standard.internalIsString(result.get().string()));
		}
	}

	public class IsBound extends BuiltinFunction
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

	public class Multiply extends BuiltinFunction
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
	public class Add extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			int length = Standard.internalListLength(ARGUMENT(aEnvironment, aStackTop, 0));
			if (length == 2)
			{
				BigNumber x;
				x = Functions.getNumber(aEnvironment, aStackTop, 1);
				RESULT(aEnvironment, aStackTop).set(new Number(x));
				return;
			}
			else
			{
				BigNumber x = Functions.getNumber(aEnvironment, aStackTop, 1);
				BigNumber y = Functions.getNumber(aEnvironment, aStackTop, 2);
				int bin = aEnvironment.precision();
				BigNumber z = new BigNumber(bin);
				z.Add(x,y,aEnvironment.precision());
				RESULT(aEnvironment, aStackTop).set(new Number(z));
				return;
			}
		}
	}

	public class Subtract extends BuiltinFunction
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

	public class Divide extends BuiltinFunction
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

	public class PiperBuiltinPrecisionSet extends BuiltinFunction
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

	public class GetExactBits extends BuiltinFunction
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


	public class SetExactBits extends BuiltinFunction
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

	public class BitCount extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.SetTo(x.BitCount());
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	public class MathSign extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.SetTo(x.Sign());
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	public class MathIsSmall extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), x.IsSmall());
		}
	}

	public class MathNegate extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.Negate(x);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	public class Floor extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			BigNumber z = new BigNumber(aEnvironment.precision());
			z.Floor(x);
			RESULT(aEnvironment, aStackTop).set(new Number(z));
		}
	}

	public class Ceil extends BuiltinFunction
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

	public class Abs extends BuiltinFunction
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

	public class Mod extends BuiltinFunction
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

	public class Div extends BuiltinFunction
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

	public class BitsToDigits extends BuiltinFunction
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

	public class DigitsToBits extends BuiltinFunction
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

	public class Gcd extends BuiltinFunction
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

	public class SystemCall extends BuiltinFunction
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

	public class FastArcSin extends BuiltinFunction
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

	public class FastLog extends BuiltinFunction
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

	public class FastPower extends BuiltinFunction
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

	public class ShiftLeft extends BuiltinFunction
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

	public class ShiftRight extends BuiltinFunction
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

	public class FromBase extends BuiltinFunction
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

	public class ToBase extends BuiltinFunction
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

	public class MaxEvalDepth extends BuiltinFunction
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

	public class DefLoad extends BuiltinFunction
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

	public class Use extends BuiltinFunction
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

	public class RightAssociative extends BuiltinFunction
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

	public class LeftPrecedence extends BuiltinFunction
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

	public class RightPrecedence extends BuiltinFunction
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

	public class IsBodied extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			InfixOperator op = Functions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iBodiedOperators);
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), op != null);
		}
	}

	public class IsInFix extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			InfixOperator op = Functions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iInfixOperators);
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), op != null);
		}
	}

	public class IsPreFix extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			InfixOperator op = Functions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iPrefixOperators);
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), op != null);
		}
	}

	public class IsPostFix extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			InfixOperator op = Functions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iPostfixOperators);
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), op != null);
		}
	}

	public class GetPrecedence extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			InfixOperator op = Functions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iInfixOperators);
			if (op == null)
			{  // also need to check for a postfix or prefix operator
				op = Functions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iPrefixOperators);
				if (op == null)
				{
					op = Functions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iPostfixOperators);
					if (op == null)
					{  // or maybe it's a bodied function
						op = Functions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iBodiedOperators);
						LispError.CHK_CORE(aEnvironment,aStackTop,op!=null, LispError.KLispErrIsNotInFix);
					}
				}
			}
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,""+op.iPrecedence));
		}
	}

	public class GetLeftPrecedence extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			InfixOperator op = Functions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iInfixOperators);
			if (op == null)
			{  // infix and postfix operators have left precedence
				op = Functions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iPostfixOperators);
				LispError.CHK_CORE(aEnvironment,aStackTop,op!=null, LispError.KLispErrIsNotInFix);
			}
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,""+op.iLeftPrecedence));
		}
	}

	public class GetRightPrecedence extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			InfixOperator op = Functions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iInfixOperators);
			if (op == null)
			{   // bodied, infix and prefix operators have right precedence
				op = Functions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iPrefixOperators);
				if (op == null)
				{   // or maybe it's a bodied function
					op = Functions.operatorInfo(aEnvironment, aStackTop, aEnvironment.iBodiedOperators);
					LispError.CHK_CORE(aEnvironment,aStackTop,op!=null, LispError.KLispErrIsNotInFix);
				}
			}
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,""+op.iRightPrecedence));
		}
	}

	public class PiperBuiltinPrecisionGet extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			// decimal precision
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,""+aEnvironment.precision()));
		}
	}

	public class BitAnd extends BuiltinFunction
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

	public class BitOr extends BuiltinFunction
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

	public class BitXor extends BuiltinFunction
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

	public class Secure extends BuiltinFunction
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

	public class FindFile extends BuiltinFunction
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

	public class FindFunction extends BuiltinFunction
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

	public class IsGeneric extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), evaluated.get().generic() != null);
		}
	}

	public class GenericTypeName extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.get().generic() != null,1);
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,evaluated.get().generic().typeName()));
		}
	}

	public class GenArrayCreate extends BuiltinFunction
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

			Array array = new Array(size,initarg.get());
			RESULT(aEnvironment, aStackTop).set(BuiltinObject.getInstance(array));
		}
	}

	public class GenArraySize extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

			BuiltinContainer gen = evaluated.get().generic();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen != null,1);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen.typeName().equals("\"Array\""),1);
			int size=((Array)gen).size();
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,""+size));
		}
	}

	public class GenArrayGet extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

			BuiltinContainer gen = evaluated.get().generic();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen != null,1);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen.typeName().equals("\"Array\""),1);

			Pointer sizearg = new Pointer();
			sizearg.set(ARGUMENT(aEnvironment, aStackTop, 2).get());

			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.get() != null, 2);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.get().string() != null, 2);

			int size = Integer.parseInt(sizearg.get().string(),10);

			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,size>0 && size<=((Array)gen).size(),2);
			Cons object = ((Array)gen).getElement(size);

			RESULT(aEnvironment, aStackTop).set(object.copy(false));
		}
	}

	public class GenArraySet extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer evaluated = new Pointer();
			evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

			BuiltinContainer gen = evaluated.get().generic();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen != null,1);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen.typeName().equals("\"Array\""),1);

			Pointer sizearg = new Pointer();
			sizearg.set(ARGUMENT(aEnvironment, aStackTop, 2).get());

			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.get() != null, 2);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.get().string() != null, 2);

			int size = Integer.parseInt(sizearg.get().string(),10);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,size>0 && size<=((Array)gen).size(),2);

			Pointer obj = new Pointer();
			obj.set(ARGUMENT(aEnvironment, aStackTop, 3).get());
			((Array)gen).setElement(size,obj.get());
			Standard.internalTrue( aEnvironment, RESULT(aEnvironment, aStackTop));
		}
	}

	public class CustomEval extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : CustomEval");////TODO fixme
			throw new PiperException("Function not yet supported");
		}
	}

	public class CustomEvalExpression extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : CustomEvalExpression");////TODO fixme
			throw new PiperException("Function not yet supported");
		}
	}

	public class CustomEvalResult extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : CustomEvalResult");////TODO fixme
			throw new PiperException("Function not yet supported");
		}
	}

	public class CustomEvalLocals extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispCustomEvalLocals");////TODO fixme
			throw new PiperException("Function not yet supported");
		}
	}

	public class CustomEvalStop extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispCustomEvalStop");////TODO fixme
			throw new PiperException("Function not yet supported");
		}
	}

	public class TraceRule extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispTraceRule");////TODO fixme
			throw new PiperException("Function not yet supported");
		}
	}

	public class TraceStack extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : TraceStack");////TODO fixme
			throw new PiperException("Function not yet supported");
		}
	}

	public class ReadLisp extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Parser parser = new Parser(aEnvironment.iCurrentTokenizer,
			                                   aEnvironment.iCurrentInput,
			                                   aEnvironment);
			// Read expression
			parser.parse(RESULT(aEnvironment, aStackTop));
		}
	}

	public class ReadLispListed extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Parser parser = new Parser(aEnvironment.iCurrentTokenizer,
			                                   aEnvironment.iCurrentInput,
			                                   aEnvironment);
			parser.iListed = true;
			// Read expression
			parser.parse(RESULT(aEnvironment, aStackTop));
		}
	}

	public class Type extends BuiltinFunction
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

	public class PiperStringMidGet extends BuiltinFunction
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

	public class PiperStringMidSet extends BuiltinFunction
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

	public class GenPatternCreate extends BuiltinFunction
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


			org.mathrider.piper.parametermatchers.Pattern matcher =  new org.mathrider.piper.parametermatchers.Pattern(aEnvironment, ptr,postpredicate);
			PatternContainer p = new PatternContainer(matcher);
			RESULT(aEnvironment, aStackTop).set(BuiltinObject.getInstance(p));
		}
	}

	public class GenPatternMatches extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer pattern = new Pointer();
			pattern.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			BuiltinContainer gen = pattern.get().generic();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen != null,1);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen.typeName().equals("\"Pattern\""),1);

			Pointer list = new Pointer();
			list.set(ARGUMENT(aEnvironment, aStackTop, 2).get());

			PatternContainer patclass = (PatternContainer)gen;

			Iterator iter = new Iterator(list);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.GetObject() != null,2);
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.GetObject().subList() != null,2);
			iter.GoSub();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.GetObject() != null,2);
			iter.GoNext();

			Pointer ptr = iter.Ptr();
			LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ptr != null,2);
			boolean matches = patclass.matches(aEnvironment,ptr);
			Standard.internalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop),matches);
		}
	}

	public class RuleBaseDefined extends BuiltinFunction
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

	public class DefLoadFunction extends BuiltinFunction
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

	public class RuleBaseArgList extends BuiltinFunction
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

	public class NewRulePattern extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			internalNewRulePattern(aEnvironment, aStackTop, false);
		}
	}

	public class MacroNewRulePattern extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			internalNewRulePattern(aEnvironment, aStackTop, true
			                      );
		}
	}

	public class Subst extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Pointer from = new Pointer(),to = new Pointer(),body = new Pointer();
			from.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
			to  .set(ARGUMENT(aEnvironment, aStackTop, 2).get());
			body.set(ARGUMENT(aEnvironment, aStackTop, 3).get());
			org.mathrider.piper.lisp.behaviours.Subst behaviour = new org.mathrider.piper.lisp.behaviours.Subst(aEnvironment,from, to);
			Standard.internalSubstitute(RESULT(aEnvironment, aStackTop), body, behaviour);
		}
	}

	public class LocalSymbols extends BuiltinFunction
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
			LocalSymbol behaviour = new LocalSymbol(aEnvironment,names,localnames,nrSymbols);
			Pointer result = new Pointer();
			Standard.internalSubstitute(result, argument(ARGUMENT(aEnvironment, aStackTop, 0), nrArguments-1), behaviour);
			aEnvironment.iEvaluator.eval(aEnvironment, RESULT(aEnvironment, aStackTop), result);
		}
	}

	public class FastIsPrime extends BuiltinFunction
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

	public class Fac extends BuiltinFunction
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

	public class ApplyPure extends BuiltinFunction
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


	public class PiperPrettyReaderSet extends BuiltinFunction
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

	public class PiperPrettyReaderGet extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			if (aEnvironment.iPrettyReader == null)
				RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,"\"\""));
			else
				RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,aEnvironment.iPrettyReader));
		}
	}

	public class PiperPrettyPrinterSet extends BuiltinFunction
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

	public class PiperPrettyPrinterGet extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			if (aEnvironment.iPrettyPrinter == null)
				RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,"\"\""));
			else
				RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,aEnvironment.iPrettyPrinter));
		}
	}

	public class GarbageCollect extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.hashTable().garbageCollect();
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	public class PatchLoad extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : PatchLoad");//TODO FIXME
			throw new PiperException("Function not yet supported");
		}
	}

	public class PatchString extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : PatchString");//TODO FIXME
			throw new PiperException("Function not yet supported");
		}
	}

	public class PiperExtraInfoSet extends BuiltinFunction
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

	public class PiperExtraInfoGet extends BuiltinFunction
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

	public class DefaultTokenizer extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentTokenizer = aEnvironment.iDefaultTokenizer;
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	public class CommonLispTokenizer extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispCommonLispTokenizer");//TODO FIXME
			throw new PiperException("Function not yet supported");
		}
	}

	public class XmlTokenizer extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentTokenizer = aEnvironment.iXmlTokenizer;
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	public class ExplodeTag extends BuiltinFunction
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

	public class PiperBuiltinAssoc extends BuiltinFunction
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

	public class CurrentFile extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,aEnvironment.hashTable().lookUpStringify(aEnvironment.iInputStatus.fileName())));
		}
	}

	public class CurrentLine extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment,""+aEnvironment.iInputStatus.lineNumber()));
		}
	}

	public class BackQuote extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			org.mathrider.piper.lisp.behaviours.BackQuote behaviour = new org.mathrider.piper.lisp.behaviours.BackQuote(aEnvironment);
			Pointer result = new Pointer();
			Standard.internalSubstitute(result, ARGUMENT(aEnvironment, aStackTop,  1), behaviour);
			aEnvironment.iEvaluator.eval(aEnvironment, RESULT(aEnvironment, aStackTop), result);
		}
	}

	public class DumpBigNumberDebugInfo extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = getNumber(aEnvironment, aStackTop, 1);
			x.DumpDebugInfo(aEnvironment.iCurrentOutput);
			Standard.internalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	public class InDebugMode extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Standard.internalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	public class DebugFile extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			throw new Exception("Cannot call DebugFile in non-debug version of Piper");
		}
	}

	public class DebugLine extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			throw new Exception("Cannot call DebugLine in non-debug version of Piper");
		}
	}

	public class Version extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			RESULT(aEnvironment,aStackTop).set(Atom.getInstance(aEnvironment,"\"" + org.mathrider.piper.Version.version + "\""));
		}
	}


	public class Exit extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Runtime.getRuntime().exit(0);
		}
	}

	public class ExitRequested extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			Standard.internalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
		}
	}

	public class HistorySize extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispHistorySize");//TODO FIXME
			throw new PiperException("Function not yet supported");
		}
	}

	public class StackSize extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispStackSize");//TODO FIXME
			throw new PiperException("Function not yet supported");
		}
	}

	public class IsPromptShown extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispIsPromptShown");//TODO FIXME
			throw new PiperException("Function not yet supported");
		}
	}

	public class ReadCmdLineString extends BuiltinFunction
	{
		public void eval(Environment aEnvironment,int aStackTop) throws Exception
		{
			aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispReadCmdLineString");//TODO FIXME
			throw new PiperException("Function not yet supported");
		}
	}

	public class Time extends BuiltinFunction
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

	public class FileSize extends BuiltinFunction
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
				aEnvironment.iInputStatus.restoreFrom(oldstatus);
			}
			RESULT(aEnvironment,aStackTop).set(Atom.getInstance(aEnvironment,""+fileSize));
		}
	}


}

