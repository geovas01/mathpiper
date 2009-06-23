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

import org.mathpiper.builtin.functions.Abs;
import org.mathpiper.builtin.functions.Add;
import org.mathpiper.builtin.functions.ApplyPure;
import org.mathpiper.builtin.functions.Atom;
import org.mathpiper.builtin.functions.BackQuote;
import org.mathpiper.builtin.functions.BitAnd;
import org.mathpiper.builtin.functions.BitCount;
import org.mathpiper.builtin.functions.BitOr;
import org.mathpiper.builtin.functions.BitXor;
import org.mathpiper.builtin.functions.BitsToDigits;
import org.mathpiper.builtin.functions.Bodied;
import org.mathpiper.builtin.functions.BuiltinAssoc;
import org.mathpiper.builtin.functions.BuiltinPrecisionGet;
import org.mathpiper.builtin.functions.BuiltinPrecisionSet;
import org.mathpiper.builtin.functions.Ceil;
import org.mathpiper.builtin.functions.CharString;
import org.mathpiper.builtin.functions.Check;
import org.mathpiper.builtin.functions.Clear;
import org.mathpiper.builtin.functions.CommonLispTokenizer;
import org.mathpiper.builtin.functions.Concatenate;
import org.mathpiper.builtin.functions.ConcatenateStrings;
import org.mathpiper.builtin.functions.CurrentFile;
import org.mathpiper.builtin.functions.CurrentLine;
import org.mathpiper.builtin.functions.CustomEval;
import org.mathpiper.builtin.functions.CustomEvalExpression;
import org.mathpiper.builtin.functions.CustomEvalLocals;
import org.mathpiper.builtin.functions.CustomEvalResult;
import org.mathpiper.builtin.functions.CustomEvalStop;
import org.mathpiper.builtin.functions.DebugFile;
import org.mathpiper.builtin.functions.DebugLine;
import org.mathpiper.builtin.functions.DefLoad;
import org.mathpiper.builtin.functions.DefLoadFunction;
import org.mathpiper.builtin.functions.DefMacroRulebase;
import org.mathpiper.builtin.functions.DefMacroRulebaseListed;
import org.mathpiper.builtin.functions.DefaultDirectory;
import org.mathpiper.builtin.functions.DefaultTokenizer;
import org.mathpiper.builtin.functions.Delete;
import org.mathpiper.builtin.functions.DestructiveDelete;
import org.mathpiper.builtin.functions.DestructiveInsert;
import org.mathpiper.builtin.functions.DestructiveReplace;
import org.mathpiper.builtin.functions.DestructiveReverse;
import org.mathpiper.builtin.functions.DigitsToBits;
import org.mathpiper.builtin.functions.Div;
import org.mathpiper.builtin.functions.Divide;
import org.mathpiper.builtin.functions.DumpBigNumberDebugInfo;
import org.mathpiper.builtin.functions.Equals;
import org.mathpiper.builtin.functions.Eval;
import org.mathpiper.builtin.functions.Exit;
import org.mathpiper.builtin.functions.ExitRequested;
import org.mathpiper.builtin.functions.XmlExplodeTag;
import org.mathpiper.builtin.functions.ExtraInfoGet;
import org.mathpiper.builtin.functions.ExtraInfoSet;
import org.mathpiper.builtin.functions.Fac;
import org.mathpiper.builtin.functions.FastArcSin;
import org.mathpiper.builtin.functions.FastIsPrime;
import org.mathpiper.builtin.functions.FastLog;
import org.mathpiper.builtin.functions.FastPower;
import org.mathpiper.builtin.functions.FileSize;
import org.mathpiper.builtin.functions.FindFile;
import org.mathpiper.builtin.functions.FindFunction;
import org.mathpiper.builtin.functions.FlatCopy;
import org.mathpiper.builtin.functions.Floor;
import org.mathpiper.builtin.functions.FromBase;
import org.mathpiper.builtin.functions.FromFile;
import org.mathpiper.builtin.functions.FromString;
import org.mathpiper.builtin.functions.FullForm;
import org.mathpiper.builtin.functions.GarbageCollect;
import org.mathpiper.builtin.functions.Gcd;
import org.mathpiper.builtin.functions.GenArrayCreate;
import org.mathpiper.builtin.functions.GenArrayGet;
import org.mathpiper.builtin.functions.GenArraySet;
import org.mathpiper.builtin.functions.GenArraySize;
import org.mathpiper.builtin.functions.GenPatternCreate;
import org.mathpiper.builtin.functions.GenPatternMatches;
import org.mathpiper.builtin.functions.GenericTypeName;
import org.mathpiper.builtin.functions.GetCoreError;
import org.mathpiper.builtin.functions.GetExactBits;
import org.mathpiper.builtin.functions.OpLeftPrecedence;
import org.mathpiper.builtin.functions.OpPrecedence;
import org.mathpiper.builtin.functions.OpRightPrecedence;
import org.mathpiper.builtin.functions.GreaterThan;
import org.mathpiper.builtin.functions.Head;
import org.mathpiper.builtin.functions.HistorySize;
import org.mathpiper.builtin.functions.HoldArg;
import org.mathpiper.builtin.functions.If;
import org.mathpiper.builtin.functions.InDebugMode;
import org.mathpiper.builtin.functions.Infix;
import org.mathpiper.builtin.functions.Insert;
import org.mathpiper.builtin.functions.IsAtom;
import org.mathpiper.builtin.functions.IsBodied;
import org.mathpiper.builtin.functions.IsBound;
import org.mathpiper.builtin.functions.IsFunction;
import org.mathpiper.builtin.functions.IsGeneric;
import org.mathpiper.builtin.functions.IsInfix;
import org.mathpiper.builtin.functions.IsInteger;
import org.mathpiper.builtin.functions.IsList;
import org.mathpiper.builtin.functions.IsNumber;
import org.mathpiper.builtin.functions.IsPostfix;
import org.mathpiper.builtin.functions.IsPrefix;
import org.mathpiper.builtin.functions.IsPromptShown;
import org.mathpiper.builtin.functions.IsString;
import org.mathpiper.builtin.functions.And;
import org.mathpiper.builtin.functions.Or;
import org.mathpiper.builtin.functions.LeftPrecedence;
import org.mathpiper.builtin.functions.Length;
import org.mathpiper.builtin.functions.LessThan;
import org.mathpiper.builtin.functions.Listify;
import org.mathpiper.builtin.functions.Load;
import org.mathpiper.builtin.functions.LocalSymbols;
import org.mathpiper.builtin.functions.MacroRule;
import org.mathpiper.builtin.functions.MacroNewRulePattern;
import org.mathpiper.builtin.functions.MacroRulebase;
import org.mathpiper.builtin.functions.MacroRulebaseListed;
import org.mathpiper.builtin.functions.MacroSet;
import org.mathpiper.builtin.functions.MathIsSmall;
import org.mathpiper.builtin.functions.MathNegate;
import org.mathpiper.builtin.functions.MathSign;
import org.mathpiper.builtin.functions.MaxEvalDepth;
import org.mathpiper.builtin.functions.Mod;
import org.mathpiper.builtin.functions.Multiply;
import org.mathpiper.builtin.functions.Local;
import org.mathpiper.builtin.functions.Rule;
import org.mathpiper.builtin.functions.NewRulePattern;
import org.mathpiper.builtin.functions.Not;
import org.mathpiper.builtin.functions.Nth;
import org.mathpiper.builtin.functions.PatchLoad;
import org.mathpiper.builtin.functions.PatchString;
import org.mathpiper.builtin.functions.Postfix;
import org.mathpiper.builtin.functions.Prefix;
import org.mathpiper.builtin.functions.PrettyPrinterGet;
import org.mathpiper.builtin.functions.PrettyPrinterSet;
import org.mathpiper.builtin.functions.PrettyReaderGet;
import org.mathpiper.builtin.functions.PrettyReaderSet;
import org.mathpiper.builtin.functions.Prog;
import org.mathpiper.builtin.functions.Hold;
import org.mathpiper.builtin.functions.Read;
import org.mathpiper.builtin.functions.ReadCmdLineString;
import org.mathpiper.builtin.functions.LispRead;
import org.mathpiper.builtin.functions.LispReadListed;
import org.mathpiper.builtin.functions.ReadToken;
import org.mathpiper.builtin.functions.Replace;
import org.mathpiper.builtin.functions.Retract;
import org.mathpiper.builtin.functions.RightAssociative;
import org.mathpiper.builtin.functions.RightPrecedence;
import org.mathpiper.builtin.functions.Rulebase;
import org.mathpiper.builtin.functions.RulebaseArgList;
import org.mathpiper.builtin.functions.RulebaseDefined;
import org.mathpiper.builtin.functions.RulebaseListed;
import org.mathpiper.builtin.functions.Secure;
import org.mathpiper.builtin.functions.SetExactBits;
import org.mathpiper.builtin.functions.SetGlobalLazyVariable;
import org.mathpiper.builtin.functions.Set;
import org.mathpiper.builtin.functions.ShiftLeft;
import org.mathpiper.builtin.functions.ShiftRight;
import org.mathpiper.builtin.functions.StackSize;
import org.mathpiper.builtin.functions.StringMidGet;
import org.mathpiper.builtin.functions.StringMidSet;
import org.mathpiper.builtin.functions.Stringify;
import org.mathpiper.builtin.functions.Subst;
import org.mathpiper.builtin.functions.Subtract;
import org.mathpiper.builtin.functions.SystemCall;
import org.mathpiper.builtin.functions.Tail;
import org.mathpiper.builtin.functions.ToBase;
import org.mathpiper.builtin.functions.ToFile;
import org.mathpiper.builtin.functions.ToStdout;
import org.mathpiper.builtin.functions.ToString;
import org.mathpiper.builtin.functions.TraceRule;
import org.mathpiper.builtin.functions.TraceStack;
import org.mathpiper.builtin.functions.TrapError;
import org.mathpiper.builtin.functions.UnFence;
import org.mathpiper.builtin.functions.UnList;
import org.mathpiper.builtin.functions.Use;
import org.mathpiper.builtin.functions.While;
import org.mathpiper.builtin.functions.Write;
import org.mathpiper.builtin.functions.WriteString;
import org.mathpiper.builtin.functions.XmlTokenizer;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Environment;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.functions.optional.Trace;
import org.mathpiper.lisp.printers.MathPiperPrinter;


public abstract class BuiltinFunction
{
	public abstract void evaluate(Environment aEnvironment,int aStackTop) throws Exception;

	public static ConsPointer getResult(Environment aEnvironment,int aStackTop) throws Exception
	{
		return aEnvironment.iArgumentStack.getElement(aStackTop);
	}
	
	public static ConsPointer getArgumentPointer(Environment aEnvironment,int aStackTop, int argumentPosition)  throws Exception
	{
		return aEnvironment.iArgumentStack.getElement(aStackTop+argumentPosition);
	}

	public static ConsPointer getArgumentPointer(ConsPointer cur, int n) throws Exception
	{
		LispError.lispAssert(n>=0);

		ConsPointer loop = cur;
		while(n != 0)
		{
			n--;
			loop = loop.getCons().getRestPointer();
		}
		return loop;
	}
        
        
     public static void addFunctions(Environment aEnvironment)
    {
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "While");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "Rule");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "MacroRule");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "RulePattern");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "MacroRulePattern");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "FromFile");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "FromString");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "ToFile");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "ToString");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "ToStdout");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "TraceRule");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "Subst");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "LocalSymbols");
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "BackQuote");
        aEnvironment.iPrefixOperators.setOperator(0, "`");
        aEnvironment.iPrefixOperators.setOperator(0, "@");
        aEnvironment.iPrefixOperators.setOperator(0, "_");
        aEnvironment.iInfixOperators.setOperator(0, "_");

        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Hold(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "Hold");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Eval(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Eval");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Write(), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),
                "Write");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new WriteString(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "WriteString");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new FullForm(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "FullForm");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new DefaultDirectory(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "DefaultDirectory");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new FromFile(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "FromFile");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new FromString(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "FromString");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Read(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Read");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new ReadToken(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ReadToken");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new ToFile(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "ToFile");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new ToString(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "ToString");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new ToStdout(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "ToStdout");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Load(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Load");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Set(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "Set");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new MacroSet(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "MacroSet");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Clear(), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),
                "Clear");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Clear(), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),
                "MacroClear");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Local(), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),
                "Local");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Local(), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),
                "MacroLocal");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Head(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Head");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Nth(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "MathNth");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Tail(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Tail");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new DestructiveReverse(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "DestructiveReverse");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Length(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Length");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.List(), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),
                "List");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new UnList(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "UnList");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Listify(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Listify");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Concatenate(), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),
                "Concat");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new ConcatenateStrings(), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),
                "ConcatStrings");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Delete(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Delete");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new DestructiveDelete(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "DestructiveDelete");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Insert(), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Insert");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new DestructiveInsert(), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "DestructiveInsert");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Replace(), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Replace");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new DestructiveReplace(), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "DestructiveReplace");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Atom(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Atom");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Stringify(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "String");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new CharString(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "CharString");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new FlatCopy(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "FlatCopy");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Prog(), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),
                "Prog");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new While(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "While");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new If(), 2, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),
                "If");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Check(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "Check");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new TrapError(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "TrapError");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new GetCoreError(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "GetCoreError");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Prefix(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Prefix");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Infix(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Infix");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Postfix(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Postfix");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Bodied(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Bodied");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Rulebase(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "RuleBase");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new MacroRulebase(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "MacroRuleBase");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new RulebaseListed(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "RuleBaseListed");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new MacroRulebaseListed(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "MacroRuleBaseListed");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new DefMacroRulebase(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "DefMacroRuleBase");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new DefMacroRulebaseListed(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "DefMacroRuleBaseListed");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new HoldArg(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "HoldArg");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Rule(), 5, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "Rule");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new MacroRule(), 5, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "MacroRule");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new UnFence(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "UnFence");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Retract(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Retract");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Not(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "NotN");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Not(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Not"); //Alias.
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new And(), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),
                "AndN");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new And(), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),
                "And"); //Alias.
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Or(), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),
                "OrN");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Or(), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),
                "Or"); //Alias.
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Equals(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Equals");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Equals(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "="); //Alias.
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new LessThan(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "LessThan");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new GreaterThan(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "GreaterThan");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new IsFunction(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "IsFunction");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new IsAtom(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "IsAtom");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new IsNumber(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "IsNumber");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new IsInteger(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "IsInteger");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new IsList(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "IsList");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new IsString(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "IsString");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new IsBound(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "IsBound");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Multiply(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "MultiplyN");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Add(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "AddN");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Subtract(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "SubtractN");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Divide(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "DivideN");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new BuiltinPrecisionSet(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "BuiltinPrecisionSet");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new GetExactBits(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "GetExactBitsN");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new SetExactBits(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "SetExactBitsN");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new BitCount(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "MathBitCount");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new MathSign(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "MathSign");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new MathIsSmall(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "MathIsSmall");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new MathNegate(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "MathNegate");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Floor(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "FloorN");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Ceil(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "CeilN");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Abs(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "AbsN");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Mod(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ModN");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Div(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "DivN");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new BitsToDigits(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "BitsToDigits");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new DigitsToBits(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "DigitsToBits");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Gcd(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "GcdN");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new SystemCall(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "SystemCall");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new FastArcSin(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "FastArcSin");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new FastLog(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "FastLog");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new FastPower(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "FastPower");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new ShiftLeft(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ShiftLeft");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new ShiftRight(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ShiftRight");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new FromBase(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "FromBase");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new ToBase(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ToBase");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new MaxEvalDepth(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "MaxEvalDepth");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new DefLoad(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "DefLoad");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Use(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Use");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new RightAssociative(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "RightAssociative");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new LeftPrecedence(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "LeftPrecedence");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new RightPrecedence(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "RightPrecedence");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new IsBodied(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "IsBodied");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new IsInfix(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "IsInfix");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new IsPrefix(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "IsPrefix");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new IsPostfix(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "IsPostfix");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new OpPrecedence(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "OpPrecedence");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new OpLeftPrecedence(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "OpLeftPrecedence");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new OpRightPrecedence(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "OpRightPrecedence");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new BuiltinPrecisionGet(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "BuiltinPrecisionGet");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new BitAnd(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "BitAnd");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new BitOr(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "BitOr");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new BitXor(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "BitXor");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Secure(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "Secure");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new FindFile(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "FindFile");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new FindFunction(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "FindFunction");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new IsGeneric(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "IsGeneric");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new GenericTypeName(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "GenericTypeName");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new GenArrayCreate(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ArrayCreate");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new GenArraySize(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ArraySize");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new GenArrayGet(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ArrayGet");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new GenArraySet(), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ArraySet");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new CustomEval(), 4, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "CustomEval");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new CustomEvalExpression(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "CustomEval'Expression");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new CustomEvalResult(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "CustomEval'Result");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new CustomEvalLocals(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "CustomEval'Locals");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new CustomEvalStop(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "CustomEval'Stop");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new TraceRule(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "TraceRule");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new TraceStack(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "TraceStack");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new LispRead(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "LispRead");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new LispReadListed(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "LispReadListed");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.Type(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Type");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new StringMidGet(), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "StringMidGet");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new StringMidSet(), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "StringMidSet");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new GenPatternCreate(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Pattern'Create");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new GenPatternMatches(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Pattern'Matches");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new RulebaseDefined(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "RuleBaseDefined");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new DefLoadFunction(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "DefLoadFunction");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new RulebaseArgList(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "RuleBaseArgList");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new NewRulePattern(), 5, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "RulePattern");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new MacroNewRulePattern(), 5, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "MacroRulePattern");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Subst(), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Subst");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new LocalSymbols(), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Macro),
                "LocalSymbols");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new FastIsPrime(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "FastIsPrime");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Fac(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "MathFac");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new ApplyPure(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ApplyPure");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new PrettyReaderSet(), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),
                "PrettyReader'Set");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new PrettyPrinterSet(), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),
                "PrettyPrinter'Set");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new PrettyPrinterGet(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "PrettyPrinter'Get");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new PrettyReaderGet(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "PrettyReader'Get");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new GarbageCollect(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "GarbageCollect");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new SetGlobalLazyVariable(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "SetGlobalLazyVariable");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new PatchLoad(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "PatchLoad");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new PatchString(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "PatchString");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new ExtraInfoSet(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ExtraInfoSet");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new ExtraInfoGet(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ExtraInfo'Get");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new DefaultTokenizer(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "DefaultTokenizer");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new CommonLispTokenizer(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "CommonLispTokenizer");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new XmlTokenizer(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "XmlTokenizer");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new XmlExplodeTag(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "XmlExplodeTag");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new BuiltinAssoc(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Builtin'Assoc");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new CurrentFile(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "CurrentFile");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new CurrentLine(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "CurrentLine");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new BackQuote(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "`");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new DumpBigNumberDebugInfo(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "MathDebugInfo");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new InDebugMode(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "InDebugMode");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new DebugFile(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "DebugFile");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new DebugLine(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "DebugLine");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.Version(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Version");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Exit(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Exit");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new ExitRequested(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "IsExitRequested");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new HistorySize(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "HistorySize");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new StackSize(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "StaSiz");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new IsPromptShown(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "IsPromptShown");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new ReadCmdLineString(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ReadCmdLineString");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.Time(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "GetTime");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new FileSize(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "FileSize");
	
        //Note:tk:The functions below this point need to have documentation created for them.
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.optional.TraceOn(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "TraceOn");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.optional.TraceOff(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "TraceOff");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.optional.ViewEnvironment(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ViewEnvironment");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.optional.ViewSimulator(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ViewSimulator");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.optional.SimulatorPlot(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "SimulatorPlot");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.optional.SetPlotColor(), 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "SetPlotColor");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.optional.SetPlotWidth(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "SetPlotWidth");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.optional.SysOut(), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),
                "SysOut");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.CurrentTime(), 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "CurrentTime");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.optional.Maxima(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Maxima");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.optional.MacroExpand(), 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "MacroExpand");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new org.mathpiper.builtin.functions.optional.JavaCall(), 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),
                "JavaCall");
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(new Trace(), 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro),
                "Trace");
    }//end method.


}//end class.
