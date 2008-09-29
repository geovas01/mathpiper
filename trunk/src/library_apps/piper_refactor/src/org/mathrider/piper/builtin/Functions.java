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

import org.mathrider.piper.builtin.functions.StringMidSet;
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
import org.mathrider.piper.lisp.ConsPointer;
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
import org.mathrider.piper.lisp.ConsTraverser;
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
        aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence, "While");
        aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence, "Rule");
        aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence, "MacroRule");
        aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence, "RulePattern");
        aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence, "MacroRulePattern");
        aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence, "FromFile");
        aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence, "FromString");
        aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence, "ToFile");
        aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence, "ToString");
        aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence, "ToStdout");
        aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence, "TraceRule");
        aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence, "Subst");
        aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence, "LocalSymbols");
        aEnvironment.iBodiedOperators.SetOperator(InfixPrinter.KMaxPrecedence, "BackQuote");
        aEnvironment.iPrefixOperators.SetOperator(0, "`");
        aEnvironment.iPrefixOperators.SetOperator(0, "@");
        aEnvironment.iPrefixOperators.SetOperator(0, "_");
        aEnvironment.iInfixOperators.SetOperator(0, "_");

        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Quote(), 1, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "Hold");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Eval(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Eval");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Write(), 1, PiperEvaluator.Variable | PiperEvaluator.Function),
                "Write");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new WriteString(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "WriteString");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new FullForm(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "FullForm");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new DefaultDirectory(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "DefaultDirectory");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new FromFile(), 2, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "FromFile");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new FromString(), 2, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "FromString");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Read(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Read");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new ReadToken(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "ReadToken");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new ToFile(), 2, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "ToFile");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new ToString(), 1, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "ToString");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new ToStdout(), 1, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "ToStdout");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Load(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Load");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new SetVar(), 2, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "Set");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new MacroSetVar(), 2, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "MacroSet");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new ClearVar(), 1, PiperEvaluator.Variable | PiperEvaluator.Macro),
                "Clear");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new ClearVar(), 1, PiperEvaluator.Variable | PiperEvaluator.Function),
                "MacroClear");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new NewLocal(), 1, PiperEvaluator.Variable | PiperEvaluator.Macro),
                "Local");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new NewLocal(), 1, PiperEvaluator.Variable | PiperEvaluator.Function),
                "MacroLocal");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Head(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Head");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Nth(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathNth");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Tail(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Tail");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new DestructiveReverse(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "DestructiveReverse");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Length(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Length");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new List(), 1, PiperEvaluator.Variable | PiperEvaluator.Macro),
                "List");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new UnList(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "UnList");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Listify(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Listify");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Concatenate(), 1, PiperEvaluator.Variable | PiperEvaluator.Function),
                "Concat");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new ConcatenateStrings(), 1, PiperEvaluator.Variable | PiperEvaluator.Function),
                "ConcatStrings");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Delete(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Delete");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new DestructiveDelete(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "DestructiveDelete");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Insert(), 3, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Insert");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new DestructiveInsert(), 3, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "DestructiveInsert");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Replace(), 3, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Replace");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new DestructiveReplace(), 3, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "DestructiveReplace");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Atomize(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Atom");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Stringify(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "String");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new CharString(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "CharString");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new FlatCopy(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "FlatCopy");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new ProgBody(), 1, PiperEvaluator.Variable | PiperEvaluator.Macro),
                "Prog");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new While(), 2, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "While");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new If(), 2, PiperEvaluator.Variable | PiperEvaluator.Macro),
                "If");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Check(), 2, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "Check");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new TrapError(), 2, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "TrapError");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new GetCoreError(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "GetCoreError");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new PreFix(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Prefix");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new InFix(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Infix");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new PostFix(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Postfix");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Bodied(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Bodied");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new RuleBase(), 2, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "RuleBase");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new MacroRuleBase(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MacroRuleBase");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new RuleBaseListed(), 2, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "RuleBaseListed");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new MacroRuleBaseListed(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MacroRuleBaseListed");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new DefMacroRuleBase(), 2, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "DefMacroRuleBase");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new DefMacroRuleBaseListed(), 2, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "DefMacroRuleBaseListed");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new HoldArg(), 2, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "HoldArg");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new NewRule(), 5, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "Rule");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new MacroNewRule(), 5, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MacroRule");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new UnFence(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "UnFence");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Retract(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Retract");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Not(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathNot");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Not(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Not");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new LazyAnd(), 1, PiperEvaluator.Variable | PiperEvaluator.Macro),
                "MathAnd");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new LazyAnd(), 1, PiperEvaluator.Variable | PiperEvaluator.Macro),
                "And");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new LazyOr(), 1, PiperEvaluator.Variable | PiperEvaluator.Macro),
                "MathOr");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new LazyOr(), 1, PiperEvaluator.Variable | PiperEvaluator.Macro),
                "Or");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Equals(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Equals");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Equals(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "=");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new LessThan(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "LessThan");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new GreaterThan(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "GreaterThan");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new IsFunction(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "IsFunction");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new IsAtom(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "IsAtom");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new IsNumber(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "IsNumber");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new IsInteger(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "IsInteger");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new IsList(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "IsList");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new IsString(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "IsString");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new IsBound(), 1, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "IsBound");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Multiply(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathMultiply");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Add(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathAdd");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Subtract(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathSubtract");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Divide(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathDivide");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new BuiltinPrecisionSet(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Builtin'Precision'Set");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new GetExactBits(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathGetExactBits");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new SetExactBits(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathSetExactBits");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new BitCount(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathBitCount");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new MathSign(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathSign");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new MathIsSmall(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathIsSmall");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new MathNegate(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathNegate");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Floor(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathFloor");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Ceil(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathCeil");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Abs(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathAbs");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Mod(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathMod");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Div(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathDiv");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new BitsToDigits(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "BitsToDigits");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new DigitsToBits(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "DigitsToBits");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Gcd(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathGcd");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new SystemCall(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "SystemCall");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new FastArcSin(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "FastArcSin");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new FastLog(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "FastLog");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new FastPower(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "FastPower");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new ShiftLeft(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "ShiftLeft");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new ShiftRight(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "ShiftRight");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new FromBase(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "FromBase");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new ToBase(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "ToBase");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new MaxEvalDepth(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MaxEvalDepth");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new DefLoad(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "DefLoad");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Use(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Use");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new RightAssociative(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "RightAssociative");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new LeftPrecedence(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "LeftPrecedence");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new RightPrecedence(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "RightPrecedence");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new IsBodied(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "IsBodied");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new IsInFix(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "IsInfix");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new IsPreFix(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "IsPrefix");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new IsPostFix(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "IsPostfix");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new GetPrecedence(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "OpPrecedence");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new GetLeftPrecedence(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "OpLeftPrecedence");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new GetRightPrecedence(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "OpRightPrecedence");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new BuiltinPrecisionGet(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Builtin'Precision'Get");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new BitAnd(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "BitAnd");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new BitOr(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "BitOr");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new BitXor(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "BitXor");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Secure(), 1, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "Secure");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new FindFile(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "FindFile");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new FindFunction(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "FindFunction");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new IsGeneric(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "IsGeneric");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new GenericTypeName(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "GenericTypeName");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new GenArrayCreate(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Array'Create");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new GenArraySize(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Array'Size");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new GenArrayGet(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Array'Get");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new GenArraySet(), 3, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Array'Set");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new CustomEval(), 4, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "CustomEval");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new CustomEvalExpression(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "CustomEval'Expression");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new CustomEvalResult(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "CustomEval'Result");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new CustomEvalLocals(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "CustomEval'Locals");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new CustomEvalStop(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "CustomEval'Stop");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new TraceRule(), 2, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "TraceRule");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new TraceStack(), 1, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "TraceStack");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new ReadLisp(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "LispRead");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new ReadLispListed(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "LispReadListed");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Type(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Type");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new StringMidGet(), 3, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "StringMid'Get");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new StringMidSet(), 3, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "StringMid'Set");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new GenPatternCreate(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Pattern'Create");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new GenPatternMatches(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Pattern'Matches");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new RuleBaseDefined(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "RuleBaseDefined");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new DefLoadFunction(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "DefLoadFunction");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new RuleBaseArgList(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "RuleBaseArgList");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new NewRulePattern(), 5, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "RulePattern");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new MacroNewRulePattern(), 5, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MacroRulePattern");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Subst(), 3, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Subst");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new LocalSymbols(), 1, PiperEvaluator.Variable | PiperEvaluator.Macro),
                "LocalSymbols");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new FastIsPrime(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "FastIsPrime");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Fac(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathFac");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new ApplyPure(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "ApplyPure");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new PrettyReaderSet(), 1, PiperEvaluator.Variable | PiperEvaluator.Function),
                "PrettyReader'Set");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new PrettyPrinterSet(), 1, PiperEvaluator.Variable | PiperEvaluator.Function),
                "PrettyPrinter'Set");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new PrettyPrinterGet(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "PrettyPrinter'Get");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new PrettyReaderGet(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "PrettyReader'Get");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new GarbageCollect(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "GarbageCollect");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new SetGlobalLazyVariable(), 2, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "SetGlobalLazyVariable");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new PatchLoad(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "PatchLoad");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new PatchString(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "PatchString");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new ExtraInfoSet(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "ExtraInfo'Set");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new ExtraInfoGet(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "ExtraInfo'Get");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new DefaultTokenizer(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "DefaultTokenizer");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new CommonLispTokenizer(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "CommonLispTokenizer");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new XmlTokenizer(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "XmlTokenizer");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new ExplodeTag(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "XmlExplodeTag");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new BuiltinAssoc(), 2, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Builtin'Assoc");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new CurrentFile(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "CurrentFile");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new CurrentLine(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "CurrentLine");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new BackQuote(), 1, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "`");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new DumpBigNumberDebugInfo(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "MathDebugInfo");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new InDebugMode(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "InDebugMode");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new DebugFile(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "DebugFile");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new DebugLine(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "DebugLine");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new org.mathrider.piper.builtin.functions.Version(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Version");

        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Exit(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "Exit");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new ExitRequested(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "IsExitRequested");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new HistorySize(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "HistorySize");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new StackSize(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "StaSiz");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new IsPromptShown(), 0, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "IsPromptShown");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new ReadCmdLineString(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "ReadCmdLineString");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new Time(), 1, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "GetTime");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new FileSize(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
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
        LispError.checkArgumentCore(aEnvironment, aStackTop, x != null, aArgNr);
        return x;
    }

    public static void multiFix(Environment aEnvironment, int aStackTop, Operators aOps) throws Exception
    {
        // Get operator
        LispError.checkArgumentCore(aEnvironment, aStackTop, BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
        String orig = BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
        LispError.checkArgumentCore(aEnvironment, aStackTop, orig != null, 1);

        ConsPointer precedence = new ConsPointer();
        aEnvironment.iEvaluator.evaluate(aEnvironment, precedence, BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 2));
        LispError.checkArgumentCore(aEnvironment, aStackTop, precedence.get().string() != null, 2);
        int prec = Integer.parseInt(precedence.get().string(), 10);
        LispError.checkArgumentCore(aEnvironment, aStackTop, prec <= InfixPrinter.KMaxPrecedence, 2);
        aOps.SetOperator(prec, Standard.symbolName(aEnvironment, orig));
        Standard.internalTrue(aEnvironment, BuiltinFunction.RESULT(aEnvironment, aStackTop));
    }

    public static void singleFix(int aPrecedence, Environment aEnvironment, int aStackTop, Operators aOps) throws Exception
    {
        // Get operator
        LispError.checkArgumentCore(aEnvironment, aStackTop, BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
        String orig = BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
        LispError.checkArgumentCore(aEnvironment, aStackTop, orig != null, 1);
        aOps.SetOperator(aPrecedence, Standard.symbolName(aEnvironment, orig));
        Standard.internalTrue(aEnvironment, BuiltinFunction.RESULT(aEnvironment, aStackTop));
    }

    public static InfixOperator operatorInfo(Environment aEnvironment, int aStackTop, Operators aOperators) throws Exception
    {
        // Get operator
        LispError.checkArgumentCore(aEnvironment, aStackTop, BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);

        ConsPointer evaluated = new ConsPointer();
        evaluated.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get());

        String orig = evaluated.get().string();
        LispError.checkArgumentCore(aEnvironment, aStackTop, orig != null, 1);
        //
        InfixOperator op = (InfixOperator) aOperators.lookUp(Standard.symbolName(aEnvironment, orig));
        return op;
    }

    /// Execute the Piper commands \c Set and \c MacroSet.
    /// The argument \a aMacroMode determines whether the first argument
    /// should be evaluated. The real work is done by
    /// Environment::SetVariable() .
    /// \sa LispSetVar(), LispMacroSetVar()
    public static void internalSetVar(Environment aEnvironment, int aStackTop, boolean aMacroMode, boolean aGlobalLazyVariable) throws Exception
    {
        String varstring = null;
        if (aMacroMode)
        {
            ConsPointer result = new ConsPointer();
            aEnvironment.iEvaluator.evaluate(aEnvironment, result, BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1));
            varstring = result.get().string();
        } else
        {
            varstring = BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
        }
        LispError.checkArgumentCore(aEnvironment, aStackTop, varstring != null, 1);
        LispError.checkArgumentCore(aEnvironment, aStackTop, !Standard.isNumber(varstring, true), 1);

        ConsPointer result = new ConsPointer();
        aEnvironment.iEvaluator.evaluate(aEnvironment, result, BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 2));
        aEnvironment.setVariable(varstring, result, aGlobalLazyVariable);
        Standard.internalTrue(aEnvironment, BuiltinFunction.RESULT(aEnvironment, aStackTop));
    }

    public static void internalDelete(Environment aEnvironment, int aStackTop, boolean aDestructive) throws Exception
    {
        ConsPointer evaluated = new ConsPointer();
        evaluated.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get());
        LispError.checkIsListCore(aEnvironment, aStackTop, evaluated, 1);

        ConsPointer copied = new ConsPointer();
        if (aDestructive)
        {
            copied.set(evaluated.get().subList().get());
        } else
        {
            Standard.internalFlatCopy(copied, evaluated.get().subList());
        }

        ConsPointer index = new ConsPointer();
        index.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 2).get());
        LispError.checkArgumentCore(aEnvironment, aStackTop, index.get() != null, 2);
        LispError.checkArgumentCore(aEnvironment, aStackTop, index.get().string() != null, 2);
        int ind = Integer.parseInt(index.get().string(), 10);
        LispError.checkArgumentCore(aEnvironment, aStackTop, ind > 0, 2);

        ConsTraverser iter = new ConsTraverser(copied);
        while (ind > 0)
        {
            iter.goNext();
            ind--;
        }
        LispError.checkCore(aEnvironment, aStackTop, iter.getCons() != null, LispError.KLispErrListNotLongEnough);
        ConsPointer next = new ConsPointer();
        next.set(iter.getCons().cdr().get());
        iter.ptr().set(next.get());
        BuiltinFunction.RESULT(aEnvironment, aStackTop).set(SubList.getInstance(copied.get()));
    }

    public static void internalInsert(Environment aEnvironment, int aStackTop, boolean aDestructive) throws Exception
    {
        ConsPointer evaluated = new ConsPointer();
        evaluated.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get());
        LispError.checkIsListCore(aEnvironment, aStackTop, evaluated, 1);

        ConsPointer copied = new ConsPointer();
        if (aDestructive)
        {
            copied.set(evaluated.get().subList().get());
        } else
        {
            Standard.internalFlatCopy(copied, evaluated.get().subList());
        }

        ConsPointer index = new ConsPointer();
        index.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 2).get());
        LispError.checkArgumentCore(aEnvironment, aStackTop, index.get() != null, 2);
        LispError.checkArgumentCore(aEnvironment, aStackTop, index.get().string() != null, 2);
        int ind = Integer.parseInt(index.get().string(), 10);
        LispError.checkArgumentCore(aEnvironment, aStackTop, ind > 0, 2);

        ConsTraverser iter = new ConsTraverser(copied);
        while (ind > 0)
        {
            iter.goNext();
            ind--;
        }

        ConsPointer toInsert = new ConsPointer();
        toInsert.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 3).get());
        toInsert.get().cdr().set(iter.getCons());
        iter.ptr().set(toInsert.get());
        BuiltinFunction.RESULT(aEnvironment, aStackTop).set(SubList.getInstance(copied.get()));
    }

    public static void internalReplace(Environment aEnvironment, int aStackTop, boolean aDestructive) throws Exception
    {
        ConsPointer evaluated = new ConsPointer();
        evaluated.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get());
        // Ok, so lets not check if it is a list, but it needs to be at least a 'function'
        LispError.checkArgumentCore(aEnvironment, aStackTop, evaluated.get().subList() != null, 1);

        ConsPointer index = new ConsPointer();
        index.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 2).get());
        LispError.checkArgumentCore(aEnvironment, aStackTop, index.get() != null, 2);
        LispError.checkArgumentCore(aEnvironment, aStackTop, index.get().string() != null, 2);
        int ind = Integer.parseInt(index.get().string(), 10);

        ConsPointer copied = new ConsPointer();
        if (aDestructive)
        {
            copied.set(evaluated.get().subList().get());
        } else
        {
            Standard.internalFlatCopy(copied, evaluated.get().subList());
        }
        LispError.checkArgumentCore(aEnvironment, aStackTop, ind > 0, 2);

        ConsTraverser iter = new ConsTraverser(copied);
        while (ind > 0)
        {
            iter.goNext();
            ind--;
        }

        ConsPointer toInsert = new ConsPointer();
        toInsert.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 3).get());
        LispError.checkArgumentCore(aEnvironment, aStackTop, iter.ptr() != null, 2);
        LispError.checkArgumentCore(aEnvironment, aStackTop, iter.ptr().get() != null, 2);
        toInsert.get().cdr().set(iter.ptr().get().cdr().get());
        iter.ptr().set(toInsert.get());
        BuiltinFunction.RESULT(aEnvironment, aStackTop).set(SubList.getInstance(copied.get()));
    }


    /// Implements the Piper functions \c RuleBase and \c MacroRuleBase .
    /// The real work is done by Environment::DeclareRuleBase().
    public static void internalRuleBase(Environment aEnvironment, int aStackTop, boolean aListed) throws Exception
    {
        //TESTARGS(3);

        // Get operator
        ConsPointer args = new ConsPointer();
        String orig = null;

        LispError.checkArgumentCore(aEnvironment, aStackTop, BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
        orig = BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
        LispError.checkArgumentCore(aEnvironment, aStackTop, orig != null, 1);
        args.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 2).get());

        // The arguments
        LispError.checkIsListCore(aEnvironment, aStackTop, args, 2);

        // Finally define the rule base
        aEnvironment.declareRuleBase(Standard.symbolName(aEnvironment, orig),
                args.get().subList().get().cdr(), aListed);

        // Return true
        Standard.internalTrue(aEnvironment, BuiltinFunction.RESULT(aEnvironment, aStackTop));
    }

    public static void internalNewRule(Environment aEnvironment, int aStackTop) throws Exception
    {
        //TESTARGS(6);

        int arity;
        int precedence;

        ConsPointer ar = new ConsPointer();
        ConsPointer pr = new ConsPointer();
        ConsPointer predicate = new ConsPointer();
        ConsPointer body = new ConsPointer();
        String orig = null;

        // Get operator
        LispError.checkArgumentCore(aEnvironment, aStackTop, BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
        orig = BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
        LispError.checkArgumentCore(aEnvironment, aStackTop, orig != null, 1);
        ar.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 2).get());
        pr.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 3).get());
        predicate.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 4).get());
        body.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 5).get());

        // The arity
        LispError.checkArgumentCore(aEnvironment, aStackTop, ar.get() != null, 2);
        LispError.checkArgumentCore(aEnvironment, aStackTop, ar.get().string() != null, 2);
        arity = Integer.parseInt(ar.get().string(), 10);

        // The precedence
        LispError.checkArgumentCore(aEnvironment, aStackTop, pr.get() != null, 3);
        LispError.checkArgumentCore(aEnvironment, aStackTop, pr.get().string() != null, 3);
        precedence = Integer.parseInt(pr.get().string(), 10);

        // Finally define the rule base
        aEnvironment.defineRule(Standard.symbolName(aEnvironment, orig),
                arity,
                precedence,
                predicate,
                body);

        // Return true
        Standard.internalTrue(aEnvironment, BuiltinFunction.RESULT(aEnvironment, aStackTop));
    }

    public static void internalDefMacroRuleBase(Environment aEnvironment, int aStackTop, boolean aListed) throws Exception
    {
        // Get operator
        ConsPointer args = new ConsPointer();
        ConsPointer body = new ConsPointer();
        String orig = null;

        LispError.checkArgumentCore(aEnvironment, aStackTop, BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
        orig = BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
        LispError.checkArgumentCore(aEnvironment, aStackTop, orig != null, 1);

        // The arguments
        args.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 2).get());
        LispError.checkIsListCore(aEnvironment, aStackTop, args, 2);

        // Finally define the rule base
        aEnvironment.declareMacroRuleBase(Standard.symbolName(aEnvironment, orig),
                args.get().subList().get().cdr(), aListed);

        // Return true
        Standard.internalTrue(aEnvironment, BuiltinFunction.RESULT(aEnvironment, aStackTop));
    }

    public static void internalNewRulePattern(Environment aEnvironment, int aStackTop, boolean aMacroMode) throws Exception
    {
        int arity;
        int precedence;

        ConsPointer ar = new ConsPointer();
        ConsPointer pr = new ConsPointer();
        ConsPointer predicate = new ConsPointer();
        ConsPointer body = new ConsPointer();
        String orig = null;

        // Get operator
        LispError.checkArgumentCore(aEnvironment, aStackTop, BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get() != null, 1);
        orig = BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 1).get().string();
        LispError.checkArgumentCore(aEnvironment, aStackTop, orig != null, 1);
        ar.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 2).get());
        pr.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 3).get());
        predicate.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 4).get());
        body.set(BuiltinFunction.ARGUMENT(aEnvironment, aStackTop, 5).get());

        // The arity
        LispError.checkArgumentCore(aEnvironment, aStackTop, ar.get() != null, 2);
        LispError.checkArgumentCore(aEnvironment, aStackTop, ar.get().string() != null, 2);
        arity = Integer.parseInt(ar.get().string(), 10);

        // The precedence
        LispError.checkArgumentCore(aEnvironment, aStackTop, pr.get() != null, 3);
        LispError.checkArgumentCore(aEnvironment, aStackTop, pr.get().string() != null, 3);
        precedence = Integer.parseInt(pr.get().string(), 10);

        // Finally define the rule base
        aEnvironment.defineRulePattern(Standard.symbolName(aEnvironment, orig),
                arity,
                precedence,
                predicate,
                body);

        // Return true
        Standard.internalTrue(aEnvironment, BuiltinFunction.RESULT(aEnvironment, aStackTop));
    }
}
