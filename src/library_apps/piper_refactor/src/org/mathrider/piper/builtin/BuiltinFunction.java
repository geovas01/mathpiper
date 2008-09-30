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

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.List;
import org.mathrider.piper.builtin.functions.Abs;
import org.mathrider.piper.builtin.functions.Add;
import org.mathrider.piper.builtin.functions.ApplyPure;
import org.mathrider.piper.builtin.functions.Atomize;
import org.mathrider.piper.builtin.functions.BackQuote;
import org.mathrider.piper.builtin.functions.BitAnd;
import org.mathrider.piper.builtin.functions.BitCount;
import org.mathrider.piper.builtin.functions.BitOr;
import org.mathrider.piper.builtin.functions.BitXor;
import org.mathrider.piper.builtin.functions.BitsToDigits;
import org.mathrider.piper.builtin.functions.Bodied;
import org.mathrider.piper.builtin.functions.BuiltinAssoc;
import org.mathrider.piper.builtin.functions.BuiltinPrecisionGet;
import org.mathrider.piper.builtin.functions.BuiltinPrecisionSet;
import org.mathrider.piper.builtin.functions.Ceil;
import org.mathrider.piper.builtin.functions.CharString;
import org.mathrider.piper.builtin.functions.Check;
import org.mathrider.piper.builtin.functions.ClearVar;
import org.mathrider.piper.builtin.functions.CommonLispTokenizer;
import org.mathrider.piper.builtin.functions.Concatenate;
import org.mathrider.piper.builtin.functions.ConcatenateStrings;
import org.mathrider.piper.builtin.functions.CurrentFile;
import org.mathrider.piper.builtin.functions.CurrentLine;
import org.mathrider.piper.builtin.functions.CustomEval;
import org.mathrider.piper.builtin.functions.CustomEvalExpression;
import org.mathrider.piper.builtin.functions.CustomEvalLocals;
import org.mathrider.piper.builtin.functions.CustomEvalResult;
import org.mathrider.piper.builtin.functions.CustomEvalStop;
import org.mathrider.piper.builtin.functions.DebugFile;
import org.mathrider.piper.builtin.functions.DebugLine;
import org.mathrider.piper.builtin.functions.DefLoad;
import org.mathrider.piper.builtin.functions.DefLoadFunction;
import org.mathrider.piper.builtin.functions.DefMacroRuleBase;
import org.mathrider.piper.builtin.functions.DefMacroRuleBaseListed;
import org.mathrider.piper.builtin.functions.DefaultDirectory;
import org.mathrider.piper.builtin.functions.DefaultTokenizer;
import org.mathrider.piper.builtin.functions.Delete;
import org.mathrider.piper.builtin.functions.DestructiveDelete;
import org.mathrider.piper.builtin.functions.DestructiveInsert;
import org.mathrider.piper.builtin.functions.DestructiveReplace;
import org.mathrider.piper.builtin.functions.DestructiveReverse;
import org.mathrider.piper.builtin.functions.DigitsToBits;
import org.mathrider.piper.builtin.functions.Div;
import org.mathrider.piper.builtin.functions.Divide;
import org.mathrider.piper.builtin.functions.DumpBigNumberDebugInfo;
import org.mathrider.piper.builtin.functions.Equals;
import org.mathrider.piper.builtin.functions.Eval;
import org.mathrider.piper.builtin.functions.Exit;
import org.mathrider.piper.builtin.functions.ExitRequested;
import org.mathrider.piper.builtin.functions.ExplodeTag;
import org.mathrider.piper.builtin.functions.ExtraInfoGet;
import org.mathrider.piper.builtin.functions.ExtraInfoSet;
import org.mathrider.piper.builtin.functions.Fac;
import org.mathrider.piper.builtin.functions.FastArcSin;
import org.mathrider.piper.builtin.functions.FastIsPrime;
import org.mathrider.piper.builtin.functions.FastLog;
import org.mathrider.piper.builtin.functions.FastPower;
import org.mathrider.piper.builtin.functions.FileSize;
import org.mathrider.piper.builtin.functions.FindFile;
import org.mathrider.piper.builtin.functions.FindFunction;
import org.mathrider.piper.builtin.functions.FlatCopy;
import org.mathrider.piper.builtin.functions.Floor;
import org.mathrider.piper.builtin.functions.FromBase;
import org.mathrider.piper.builtin.functions.FromFile;
import org.mathrider.piper.builtin.functions.FromString;
import org.mathrider.piper.builtin.functions.FullForm;
import org.mathrider.piper.builtin.functions.GarbageCollect;
import org.mathrider.piper.builtin.functions.Gcd;
import org.mathrider.piper.builtin.functions.GenArrayCreate;
import org.mathrider.piper.builtin.functions.GenArrayGet;
import org.mathrider.piper.builtin.functions.GenArraySet;
import org.mathrider.piper.builtin.functions.GenArraySize;
import org.mathrider.piper.builtin.functions.GenPatternCreate;
import org.mathrider.piper.builtin.functions.GenPatternMatches;
import org.mathrider.piper.builtin.functions.GenericTypeName;
import org.mathrider.piper.builtin.functions.GetCoreError;
import org.mathrider.piper.builtin.functions.GetExactBits;
import org.mathrider.piper.builtin.functions.GetLeftPrecedence;
import org.mathrider.piper.builtin.functions.GetPrecedence;
import org.mathrider.piper.builtin.functions.GetRightPrecedence;
import org.mathrider.piper.builtin.functions.GreaterThan;
import org.mathrider.piper.builtin.functions.Head;
import org.mathrider.piper.builtin.functions.HistorySize;
import org.mathrider.piper.builtin.functions.HoldArg;
import org.mathrider.piper.builtin.functions.If;
import org.mathrider.piper.builtin.functions.InDebugMode;
import org.mathrider.piper.builtin.functions.InFix;
import org.mathrider.piper.builtin.functions.Insert;
import org.mathrider.piper.builtin.functions.IsAtom;
import org.mathrider.piper.builtin.functions.IsBodied;
import org.mathrider.piper.builtin.functions.IsBound;
import org.mathrider.piper.builtin.functions.IsFunction;
import org.mathrider.piper.builtin.functions.IsGeneric;
import org.mathrider.piper.builtin.functions.IsInFix;
import org.mathrider.piper.builtin.functions.IsInteger;
import org.mathrider.piper.builtin.functions.IsList;
import org.mathrider.piper.builtin.functions.IsNumber;
import org.mathrider.piper.builtin.functions.IsPostFix;
import org.mathrider.piper.builtin.functions.IsPreFix;
import org.mathrider.piper.builtin.functions.IsPromptShown;
import org.mathrider.piper.builtin.functions.IsString;
import org.mathrider.piper.builtin.functions.LazyAnd;
import org.mathrider.piper.builtin.functions.LazyOr;
import org.mathrider.piper.builtin.functions.LeftPrecedence;
import org.mathrider.piper.builtin.functions.Length;
import org.mathrider.piper.builtin.functions.LessThan;
import org.mathrider.piper.builtin.functions.Listify;
import org.mathrider.piper.builtin.functions.Load;
import org.mathrider.piper.builtin.functions.LocalSymbols;
import org.mathrider.piper.builtin.functions.MacroNewRule;
import org.mathrider.piper.builtin.functions.MacroNewRulePattern;
import org.mathrider.piper.builtin.functions.MacroRuleBase;
import org.mathrider.piper.builtin.functions.MacroRuleBaseListed;
import org.mathrider.piper.builtin.functions.MacroSetVar;
import org.mathrider.piper.builtin.functions.MathIsSmall;
import org.mathrider.piper.builtin.functions.MathNegate;
import org.mathrider.piper.builtin.functions.MathSign;
import org.mathrider.piper.builtin.functions.MaxEvalDepth;
import org.mathrider.piper.builtin.functions.Mod;
import org.mathrider.piper.builtin.functions.Multiply;
import org.mathrider.piper.builtin.functions.NewLocal;
import org.mathrider.piper.builtin.functions.NewRule;
import org.mathrider.piper.builtin.functions.NewRulePattern;
import org.mathrider.piper.builtin.functions.Not;
import org.mathrider.piper.builtin.functions.Nth;
import org.mathrider.piper.builtin.functions.PatchLoad;
import org.mathrider.piper.builtin.functions.PatchString;
import org.mathrider.piper.builtin.functions.PostFix;
import org.mathrider.piper.builtin.functions.PreFix;
import org.mathrider.piper.builtin.functions.PrettyPrinterGet;
import org.mathrider.piper.builtin.functions.PrettyPrinterSet;
import org.mathrider.piper.builtin.functions.PrettyReaderGet;
import org.mathrider.piper.builtin.functions.PrettyReaderSet;
import org.mathrider.piper.builtin.functions.ProgBody;
import org.mathrider.piper.builtin.functions.Quote;
import org.mathrider.piper.builtin.functions.Read;
import org.mathrider.piper.builtin.functions.ReadCmdLineString;
import org.mathrider.piper.builtin.functions.ReadLisp;
import org.mathrider.piper.builtin.functions.ReadLispListed;
import org.mathrider.piper.builtin.functions.ReadToken;
import org.mathrider.piper.builtin.functions.Replace;
import org.mathrider.piper.builtin.functions.Retract;
import org.mathrider.piper.builtin.functions.RightAssociative;
import org.mathrider.piper.builtin.functions.RightPrecedence;
import org.mathrider.piper.builtin.functions.RuleBase;
import org.mathrider.piper.builtin.functions.RuleBaseArgList;
import org.mathrider.piper.builtin.functions.RuleBaseDefined;
import org.mathrider.piper.builtin.functions.RuleBaseListed;
import org.mathrider.piper.builtin.functions.Secure;
import org.mathrider.piper.builtin.functions.SetExactBits;
import org.mathrider.piper.builtin.functions.SetGlobalLazyVariable;
import org.mathrider.piper.builtin.functions.SetVar;
import org.mathrider.piper.builtin.functions.ShiftLeft;
import org.mathrider.piper.builtin.functions.ShiftRight;
import org.mathrider.piper.builtin.functions.StackSize;
import org.mathrider.piper.builtin.functions.StringMidGet;
import org.mathrider.piper.builtin.functions.StringMidSet;
import org.mathrider.piper.builtin.functions.Stringify;
import org.mathrider.piper.builtin.functions.Subst;
import org.mathrider.piper.builtin.functions.Subtract;
import org.mathrider.piper.builtin.functions.SystemCall;
import org.mathrider.piper.builtin.functions.Tail;
import org.mathrider.piper.builtin.functions.ToBase;
import org.mathrider.piper.builtin.functions.ToFile;
import org.mathrider.piper.builtin.functions.ToStdout;
import org.mathrider.piper.builtin.functions.ToString;
import org.mathrider.piper.builtin.functions.TraceRule;
import org.mathrider.piper.builtin.functions.TraceStack;
import org.mathrider.piper.builtin.functions.TrapError;
import org.mathrider.piper.builtin.functions.UnFence;
import org.mathrider.piper.builtin.functions.UnList;
import org.mathrider.piper.builtin.functions.Use;
import org.mathrider.piper.builtin.functions.While;
import org.mathrider.piper.builtin.functions.Write;
import org.mathrider.piper.builtin.functions.WriteString;
import org.mathrider.piper.builtin.functions.XmlTokenizer;
import org.mathrider.piper.lisp.ConsPointer;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.userfunctions.PiperEvaluator;
import org.mathrider.piper.printers.InfixPrinter;


public abstract class BuiltinFunction
{
	public abstract void eval(Environment aEnvironment,int aStackTop) throws Exception;

	public static ConsPointer result(Environment aEnvironment,int aStackTop) throws Exception
	{
		return aEnvironment.iArgumentStack.getElement(aStackTop);
	}
	
	public static ConsPointer argumentPointer(Environment aEnvironment,int aStackTop, int argumentPosition)  throws Exception
	{
		return aEnvironment.iArgumentStack.getElement(aStackTop+argumentPosition);
	}

	public static ConsPointer argumentPointer(ConsPointer cur, int n) throws Exception
	{
		LispError.lispAssert(n>=0);

		ConsPointer loop = cur;
		while(n != 0)
		{
			n--;
			loop = loop.get().cdr();
		}
		return loop;
	}
        
        
     public static void addFunctions(Environment aEnvironment)
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
                new PiperEvaluator(new org.mathrider.piper.builtin.functions.List(), 1, PiperEvaluator.Variable | PiperEvaluator.Macro),
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
                new PiperEvaluator(new org.mathrider.piper.builtin.functions.Type(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
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
                new PiperEvaluator(new org.mathrider.piper.builtin.functions.Time(), 1, PiperEvaluator.Fixed | PiperEvaluator.Macro),
                "GetTime");
        aEnvironment.builtinCommands().setAssociation(
                new PiperEvaluator(new FileSize(), 1, PiperEvaluator.Fixed | PiperEvaluator.Function),
                "FileSize");


    }


}
