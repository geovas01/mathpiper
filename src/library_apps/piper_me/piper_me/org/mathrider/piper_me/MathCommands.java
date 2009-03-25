package org.mathrider.piper_me;

import org.mathrider.piper_me.arithmetic.BigNumber;
import org.eninom.collection.mutable.ExtendibleArray;
import java.io.*;

class MathCommands
{
  public void AddCommands(LispEnvironment aEnvironment)
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

    aEnvironment.CoreCommands().put(
         "Hold",
         new PiperEvaluator(new LispQuote(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "Eval",
         new PiperEvaluator(new LispEval(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Write",
         new PiperEvaluator(new LispWrite(),1, PiperEvaluator.Variable|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "WriteString",
         new PiperEvaluator(new LispWriteString(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "FullForm",
         new PiperEvaluator(new LispFullForm(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "DefaultDirectory",
         new PiperEvaluator(new LispDefaultDirectory(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "FromFile",
         new PiperEvaluator(new LispFromFile(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "FromString",
         new PiperEvaluator(new LispFromString(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "Read",
         new PiperEvaluator(new LispRead(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "ReadToken",
         new PiperEvaluator(new LispReadToken(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "ToFile",
         new PiperEvaluator(new LispToFile(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "ToString",
         new PiperEvaluator(new LispToString(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "ToStdout",
         new PiperEvaluator(new LispToStdout(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "Load",
         new PiperEvaluator(new LispLoad(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Set",
         new PiperEvaluator(new LispSetVar(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "MacroSet",
         new PiperEvaluator(new LispMacroSetVar(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "Clear",
         new PiperEvaluator(new LispClearVar(),1, PiperEvaluator.Variable|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "MacroClear",
         new PiperEvaluator(new LispClearVar(),1, PiperEvaluator.Variable|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Local",
         new PiperEvaluator(new LispNewLocal(),1, PiperEvaluator.Variable|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "MacroLocal",
         new PiperEvaluator(new LispNewLocal(),1, PiperEvaluator.Variable|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Head",
         new PiperEvaluator(new LispHead(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "MathNth",
         new PiperEvaluator(new LispNth(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Tail",
         new PiperEvaluator(new LispTail(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "DestructiveReverse",
         new PiperEvaluator(new LispDestructiveReverse(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Length",
         new PiperEvaluator(new LispLength(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "List",
         new PiperEvaluator(new LispList(),1, PiperEvaluator.Variable|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "UnList",
         new PiperEvaluator(new LispUnList(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Listify",
         new PiperEvaluator(new LispListify(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Concat",
         new PiperEvaluator(new LispConcatenate(),1, PiperEvaluator.Variable|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "ConcatStrings",
         new PiperEvaluator(new LispConcatenateStrings(),1, PiperEvaluator.Variable|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Delete",
         new PiperEvaluator(new LispDelete(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "DestructiveDelete",
         new PiperEvaluator(new LispDestructiveDelete(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Insert",
         new PiperEvaluator(new LispInsert(),3, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "DestructiveInsert",
         new PiperEvaluator(new LispDestructiveInsert(),3, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Replace",
         new PiperEvaluator(new LispReplace(),3, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "DestructiveReplace",
         new PiperEvaluator(new LispDestructiveReplace(),3, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Atom",
         new PiperEvaluator(new LispAtomize(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "String",
         new PiperEvaluator(new LispStringify(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "CharString",
         new PiperEvaluator(new LispCharString(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "FlatCopy",
         new PiperEvaluator(new LispFlatCopy(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Prog",
         new PiperEvaluator(new LispProgBody(),1, PiperEvaluator.Variable|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "While",
         new PiperEvaluator(new LispWhile(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "If",
         new PiperEvaluator(new LispIf(),2, PiperEvaluator.Variable|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "Check",
         new PiperEvaluator(new LispCheck(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "TrapError",
         new PiperEvaluator(new LispTrapError(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "GetCoreError",
         new PiperEvaluator(new LispGetCoreError(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Prefix",
         new PiperEvaluator(new LispPreFix(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Infix",
         new PiperEvaluator(new LispInFix(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Postfix",
         new PiperEvaluator(new LispPostFix(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Bodied",
         new PiperEvaluator(new LispBodied(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "RuleBase",
         new PiperEvaluator(new LispRuleBase(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "MacroRuleBase",
         new PiperEvaluator(new LispMacroRuleBase(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "RuleBaseListed",
         new PiperEvaluator(new LispRuleBaseListed(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "MacroRuleBaseListed",
         new PiperEvaluator(new LispMacroRuleBaseListed(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "DefMacroRuleBase",
         new PiperEvaluator(new LispDefMacroRuleBase(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "DefMacroRuleBaseListed",
         new PiperEvaluator(new LispDefMacroRuleBaseListed(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "HoldArg",
         new PiperEvaluator(new LispHoldArg(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "Rule",
         new PiperEvaluator(new LispNewRule(),5, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "MacroRule",
         new PiperEvaluator(new LispMacroNewRule(),5, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "UnFence",
         new PiperEvaluator(new LispUnFence(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Retract",
         new PiperEvaluator(new LispRetract(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "NotN",
         new PiperEvaluator(new LispNot(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Not",
         new PiperEvaluator(new LispNot(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "AndN",
         new PiperEvaluator(new LispLazyAnd(),1, PiperEvaluator.Variable|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "And",
         new PiperEvaluator(new LispLazyAnd(),1, PiperEvaluator.Variable|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "OrN",
         new PiperEvaluator(new LispLazyOr(),1, PiperEvaluator.Variable|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "Or",
         new PiperEvaluator(new LispLazyOr(),1, PiperEvaluator.Variable|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "Equals",
         new PiperEvaluator(new LispEquals(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "=",
         new PiperEvaluator(new LispEquals(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "LessThan",
         new PiperEvaluator(new LispLessThan(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "GreaterThan",
         new PiperEvaluator(new LispGreaterThan(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "IsFunction",
         new PiperEvaluator(new LispIsFunction(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "IsAtom",
         new PiperEvaluator(new LispIsAtom(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "IsNumber",
         new PiperEvaluator(new LispIsNumber(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "IsInteger",
         new PiperEvaluator(new LispIsInteger(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "IsList",
         new PiperEvaluator(new LispIsList(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "IsString",
         new PiperEvaluator(new LispIsString(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "IsBound",
         new PiperEvaluator(new LispIsBound(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "MultiplyN",
         new PiperEvaluator(new LispMultiply(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "AddN",
         new PiperEvaluator(new LispAdd(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "SubtractN",
         new PiperEvaluator(new LispSubtract(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "DivideN",
         new PiperEvaluator(new LispDivide(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "BuiltinPrecisionSet",
         new PiperEvaluator(new PiperBuiltinPrecisionSet(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "GetExactBitsN",
         new PiperEvaluator(new LispGetExactBits(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "SetExactBitsN",
         new PiperEvaluator(new LispSetExactBits(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "MathBitCount",
         new PiperEvaluator(new LispBitCount(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "MathSign",
         new PiperEvaluator(new LispMathSign(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "MathIsSmall",
         new PiperEvaluator(new LispMathIsSmall(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "MathNegate",
         new PiperEvaluator(new LispMathNegate(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "FloorN",
         new PiperEvaluator(new LispFloor(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "CeilN",
         new PiperEvaluator(new LispCeil(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "AbsN",
         new PiperEvaluator(new LispAbs(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "ModN",
         new PiperEvaluator(new LispMod(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "DivN",
         new PiperEvaluator(new LispDiv(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "BitsToDigits",
         new PiperEvaluator(new LispBitsToDigits(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "DigitsToBits",
         new PiperEvaluator(new LispDigitsToBits(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "GcdN",
         new PiperEvaluator(new LispGcd(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "SystemCall",
         new PiperEvaluator(new LispSystemCall(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "FastArcSin",
         new PiperEvaluator(new LispFastArcSin(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "FastLog",
         new PiperEvaluator(new LispFastLog(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "FastPower",
         new PiperEvaluator(new LispFastPower(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "ShiftLeft",
         new PiperEvaluator(new LispShiftLeft(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "ShiftRight",
         new PiperEvaluator(new LispShiftRight(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "FromBase",
         new PiperEvaluator(new LispFromBase(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "ToBase",
         new PiperEvaluator(new LispToBase(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "MaxEvalDepth",
         new PiperEvaluator(new LispMaxEvalDepth(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "DefLoad",
         new PiperEvaluator(new LispDefLoad(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Use",
         new PiperEvaluator(new LispUse(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "RightAssociative",
         new PiperEvaluator(new LispRightAssociative(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "LeftPrecedence",
         new PiperEvaluator(new LispLeftPrecedence(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "RightPrecedence",
         new PiperEvaluator(new LispRightPrecedence(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "IsBodied",
         new PiperEvaluator(new LispIsBodied(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "IsInfix",
         new PiperEvaluator(new LispIsInFix(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "IsPrefix",
         new PiperEvaluator(new LispIsPreFix(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "IsPostfix",
         new PiperEvaluator(new LispIsPostFix(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "OpPrecedence",
         new PiperEvaluator(new LispGetPrecedence(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "OpLeftPrecedence",
         new PiperEvaluator(new LispGetLeftPrecedence(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "OpRightPrecedence",
         new PiperEvaluator(new LispGetRightPrecedence(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "BuiltinPrecisionGet",
         new PiperEvaluator(new PiperBuiltinPrecisionGet(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "BitAnd",
         new PiperEvaluator(new LispBitAnd(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "BitOr",
         new PiperEvaluator(new LispBitOr(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "BitXor",
         new PiperEvaluator(new LispBitXor(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Secure",
         new PiperEvaluator(new LispSecure(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "FindFile",
         new PiperEvaluator(new LispFindFile(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "FindFunction",
         new PiperEvaluator(new LispFindFunction(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "IsGeneric",
         new PiperEvaluator(new LispIsGeneric(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "GenericTypeName",
         new PiperEvaluator(new LispGenericTypeName(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "ArrayCreate",
         new PiperEvaluator(new GenArrayCreate(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "ArraySize",
         new PiperEvaluator(new GenArraySize(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "ArrayGet",
         new PiperEvaluator(new GenArrayGet(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "ArraySet",
         new PiperEvaluator(new GenArraySet(),3, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "CustomEval",
         new PiperEvaluator(new LispCustomEval(),4, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "CustomEval'Expression",
         new PiperEvaluator(new LispCustomEvalExpression(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "CustomEval'Result",
         new PiperEvaluator(new LispCustomEvalResult(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "CustomEval'Locals",
         new PiperEvaluator(new LispCustomEvalLocals(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "CustomEval'Stop",
         new PiperEvaluator(new LispCustomEvalStop(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "TraceRule",
         new PiperEvaluator(new LispTraceRule(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "TraceStack",
         new PiperEvaluator(new LispTraceStack(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "LispRead",
         new PiperEvaluator(new LispReadLisp(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "LispReadListed",
         new PiperEvaluator(new LispReadLispListed(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Type",
         new PiperEvaluator(new LispType(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "StringMidGet",
         new PiperEvaluator(new PiperStringMidGet(),3, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "StringMidSet",
         new PiperEvaluator(new PiperStringMidSet(),3, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Pattern'Create",
         new PiperEvaluator(new GenPatternCreate(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Pattern'Matches",
         new PiperEvaluator(new GenPatternMatches(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "RuleBaseDefined",
         new PiperEvaluator(new LispRuleBaseDefined(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "DefLoadFunction",
         new PiperEvaluator(new LispDefLoadFunction(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "RuleBaseArgList",
         new PiperEvaluator(new LispRuleBaseArgList(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "RulePattern",
         new PiperEvaluator(new LispNewRulePattern(),5, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "MacroRulePattern",
         new PiperEvaluator(new LispMacroNewRulePattern(),5, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Subst",
         new PiperEvaluator(new LispSubst(),3, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "LocalSymbols",
         new PiperEvaluator(new LispLocalSymbols(),1, PiperEvaluator.Variable|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "FastIsPrime",
         new PiperEvaluator(new LispFastIsPrime(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "MathFac",
         new PiperEvaluator(new LispFac(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "ApplyPure",
         new PiperEvaluator(new LispApplyPure(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "PrettyReader'Set",
         new PiperEvaluator(new PiperPrettyReaderSet(),1, PiperEvaluator.Variable|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "PrettyPrinter'Set",
         new PiperEvaluator(new PiperPrettyPrinterSet(),1, PiperEvaluator.Variable|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "PrettyPrinter'Get",
         new PiperEvaluator(new PiperPrettyPrinterGet(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "PrettyReader'Get",
         new PiperEvaluator(new PiperPrettyReaderGet(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "GarbageCollect",
         new PiperEvaluator(new LispGarbageCollect(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "SetGlobalLazyVariable",
         new PiperEvaluator(new LispSetGlobalLazyVariable(),2, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "PatchLoad",
         new PiperEvaluator(new LispPatchLoad(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "PatchString",
         new PiperEvaluator(new LispPatchString(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "ExtraInfoSet",
         new PiperEvaluator(new PiperExtraInfoSet(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "ExtraInfoGet",
         new PiperEvaluator(new PiperExtraInfoGet(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "DefaultTokenizer",
         new PiperEvaluator(new LispDefaultTokenizer(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "CommonLispTokenizer",
         new PiperEvaluator(new LispCommonLispTokenizer(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "XmlTokenizer",
         new PiperEvaluator(new LispXmlTokenizer(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "XmlExplodeTag",
         new PiperEvaluator(new LispExplodeTag(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Builtin'Assoc",
         new PiperEvaluator(new PiperBuiltinAssoc(),2, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "CurrentFile",
         new PiperEvaluator(new LispCurrentFile(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "CurrentLine",
         new PiperEvaluator(new LispCurrentLine(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "`",
         new PiperEvaluator(new LispBackQuote(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "MathDebugInfo",
         new PiperEvaluator(new LispDumpBigNumberDebugInfo(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "InDebugMode",
         new PiperEvaluator(new LispInDebugMode(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "DebugFile",
         new PiperEvaluator(new LispDebugFile(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "DebugLine",
         new PiperEvaluator(new LispDebugLine(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "Version",
         new PiperEvaluator(new LispVersion(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));

    aEnvironment.CoreCommands().put(
         "Exit",
         new PiperEvaluator(new LispExit(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "IsExitRequested",
         new PiperEvaluator(new LispExitRequested(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "HistorySize",
         new PiperEvaluator(new LispHistorySize(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "StaSiz",
         new PiperEvaluator(new LispStackSize(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "IsPromptShown",
         new PiperEvaluator(new LispIsPromptShown(),0, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "ReadCmdLineString",
         new PiperEvaluator(new LispReadCmdLineString(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));
    aEnvironment.CoreCommands().put(
         "GetTime",
         new PiperEvaluator(new LispTime(),1, PiperEvaluator.Fixed|PiperEvaluator.Macro));
    aEnvironment.CoreCommands().put(
         "FileSize",
         new PiperEvaluator(new LispFileSize(),1, PiperEvaluator.Fixed|PiperEvaluator.Function));


  }


  /// Construct a BigNumber from one of the arguments.
  /// \param x (on output) the constructed bignumber
  /// \param aEnvironment the current environment
  /// \param aStackTop the index of the top of the stack
  /// \param aArgNr the index of the argument to be converted
  public static BigNumber GetNumber(LispEnvironment aEnvironment, int aStackTop, int aArgNr) throws Exception
  {
    BigNumber x = PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, aArgNr).getNext().Number(aEnvironment.Precision());
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,x != null,aArgNr);
    return x;
  }

  static void MultiFix(LispEnvironment aEnvironment, int aStackTop, LispOperators aOps) throws Exception
  {
    // Get operator
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).getNext() != null, 1);
    String orig = PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).getNext().String();
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
 
    LispPtr precedence = new LispPtr();
    aEnvironment.iEvaluator.Eval(aEnvironment, precedence, PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2));
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,precedence.getNext().String() != null, 2);
    int prec = Integer.parseInt(precedence.getNext().String(),10);
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,prec <= InfixPrinter.KMaxPrecedence, 2);
    aOps.SetOperator(prec,LispStandard.SymbolName(aEnvironment,orig));
    LispStandard.InternalTrue(aEnvironment,PiperEvalCaller.RESULT(aEnvironment, aStackTop));
  }
  public static void SingleFix(int aPrecedence, LispEnvironment aEnvironment, int aStackTop, LispOperators aOps) throws Exception
  {
    // Get operator
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).getNext() != null, 1);
    String orig = PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).getNext().String();
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
    aOps.SetOperator(aPrecedence,LispStandard.SymbolName(aEnvironment,orig));
    LispStandard.InternalTrue(aEnvironment,PiperEvalCaller.RESULT(aEnvironment, aStackTop));
  }

  public static LispInFixOperator OperatorInfo(LispEnvironment aEnvironment,int aStackTop, LispOperators aOperators) throws Exception
  {
    // Get operator
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).getNext() != null, 1);

    LispPtr evaluated = new LispPtr();
    evaluated.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).getNext());

    String orig = evaluated.getNext().String();
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
    //
    LispInFixOperator op = (LispInFixOperator)aOperators.get(LispStandard.SymbolName(aEnvironment,orig));
    return op;
  }

  /// Execute the Piper commands \c Set and \c MacroSet.
  /// The argument \a aMacroMode determines whether the first argument
  /// should be evaluated. The real work is done by
  /// LispEnvironment::SetVariable() .
  /// \sa LispSetVar(), LispMacroSetVar()
  static void InternalSetVar(LispEnvironment aEnvironment, int aStackTop, boolean aMacroMode, boolean aGlobalLazyVariable) throws Exception
  {
    String varstring=null;
    if (aMacroMode)
    {
    	LispPtr result = new LispPtr();
      aEnvironment.iEvaluator.Eval(aEnvironment, result, PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1));
      varstring = result.getNext().String();
    }
    else
    {
      varstring = PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).getNext().String();
    }
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,varstring != null,1);
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,!LispStandard.IsNumber(varstring,true),1);
 
    LispPtr result = new LispPtr();
    aEnvironment.iEvaluator.Eval(aEnvironment, result, PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2));
    aEnvironment.SetVariable(varstring, result, aGlobalLazyVariable);
    LispStandard.InternalTrue(aEnvironment,PiperEvalCaller.RESULT(aEnvironment, aStackTop));
  }

  public static void InternalDelete(LispEnvironment aEnvironment, int aStackTop, boolean aDestructive) throws Exception
  {
	LispPtr evaluated = new LispPtr();
    evaluated.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).getNext());
    LispError.CHK_ISLIST_CORE(aEnvironment,aStackTop,evaluated,1);

    LispPtr copied = new LispPtr();
    if (aDestructive)
    {
      copied.setNext(evaluated.getNext().SubList().getNext());
    }
    else
    {
      LispStandard.InternalFlatCopy(copied,evaluated.getNext().SubList());
    }

    LispPtr index = new LispPtr();
    index.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2).getNext());
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext() != null, 2);
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext().String() != null, 2);
    int ind = Integer.parseInt(index.getNext().String(),10);
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ind>0,2);

    LispIterator iter = new LispIterator(copied);
    while (ind>0)
    {
      iter.GoNext();
      ind--;
    }
    LispError.CHK_CORE(aEnvironment, aStackTop,iter.GetObject() != null, LispError.KLispErrListNotLongEnough);
    LispPtr next = new LispPtr();
    next.setNext(iter.GetObject().getNext());
    iter.Ptr().setNext(next.getNext());
    PiperEvalCaller.RESULT(aEnvironment, aStackTop).setNext(LispSubList.New(copied.getNext()));
  }


  public static void InternalInsert(LispEnvironment aEnvironment, int aStackTop, boolean aDestructive) throws Exception
  {
	  LispPtr evaluated = new LispPtr();
    evaluated.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).getNext());
    LispError.CHK_ISLIST_CORE(aEnvironment,aStackTop,evaluated,1);

    LispPtr copied = new LispPtr();
    if (aDestructive)
    {
        copied.setNext(evaluated.getNext().SubList().getNext());
    }
    else
    {
        LispStandard.InternalFlatCopy(copied,evaluated.getNext().SubList());
    }
 
    LispPtr index = new LispPtr();
    index.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2).getNext());
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext() != null, 2);
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext().String() != null, 2);
    int ind = Integer.parseInt(index.getNext().String(),10);
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ind>0,2);

    LispIterator iter = new LispIterator(copied);
    while (ind>0)
    {
      iter.GoNext();
      ind--;
    }

    LispPtr toInsert = new LispPtr();
    toInsert.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 3).getNext());
    toInsert.getNext().setNext(iter.GetObject());
    iter.Ptr().setNext(toInsert.getNext());
    PiperEvalCaller.RESULT(aEnvironment, aStackTop).setNext(LispSubList.New(copied.getNext()));
  }






  public static void InternalReplace(LispEnvironment aEnvironment, int aStackTop, boolean aDestructive) throws Exception
  {
	  LispPtr evaluated = new LispPtr();
    evaluated.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).getNext());
    // Ok, so lets not check if it is a list, but it needs to be at least a 'function'
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.getNext().SubList() != null, 1);

    LispPtr index = new LispPtr();
    index.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2).getNext());
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext() != null, 2);
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext().String() != null, 2);
    int ind = Integer.parseInt(index.getNext().String(),10);

    LispPtr copied = new LispPtr();
    if (aDestructive)
    {
      copied.setNext(evaluated.getNext().SubList().getNext());
    }
    else
    {
      LispStandard.InternalFlatCopy(copied,evaluated.getNext().SubList());
    }
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ind>0,2);

    LispIterator iter = new LispIterator(copied);
    while (ind>0)
    {
      iter.GoNext();
      ind--;
    }

    LispPtr toInsert = new LispPtr();
    toInsert.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 3).getNext());
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.Ptr() != null, 2);
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.Ptr().getNext() != null, 2);
    toInsert.getNext().setNext(iter.Ptr().getNext().getNext());
    iter.Ptr().setNext(toInsert.getNext());
    PiperEvalCaller.RESULT(aEnvironment, aStackTop).setNext(LispSubList.New(copied.getNext()));
  }


  /// Implements the Piper functions \c RuleBase and \c MacroRuleBase .
  /// The real work is done by LispEnvironment::DeclareRuleBase().
  public static void InternalRuleBase(LispEnvironment aEnvironment, int aStackTop,  boolean aListed) throws Exception
  {
    //TESTARGS(3);
 
    // Get operator
	  LispPtr args = new LispPtr();
    String orig=null;
 
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).getNext() != null, 1);
    orig = PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).getNext().String();
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
    args.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2).getNext());
 
    // The arguments
    LispError.CHK_ISLIST_CORE(aEnvironment,aStackTop,args,2);

    // Finally define the rule base
    aEnvironment.DeclareRuleBase(LispStandard.SymbolName(aEnvironment,orig),
                                 args.getNext().SubList().getNext(),aListed);
 
    // Return true
    LispStandard.InternalTrue(aEnvironment,PiperEvalCaller.RESULT(aEnvironment, aStackTop));
  }

  public static void InternalNewRule(LispEnvironment aEnvironment, int aStackTop) throws Exception
  {
    //TESTARGS(6);

    int arity;
    int precedence;

    LispPtr ar = new LispPtr();
    LispPtr pr = new LispPtr();
    LispPtr predicate = new LispPtr();
    LispPtr body = new LispPtr();
    String orig=null;
 
    // Get operator
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).getNext() != null, 1);
    orig = PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).getNext().String();
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
    ar.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2).getNext());
    pr.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 3).getNext());
    predicate.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 4).getNext());
    body.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 5).getNext());
 
    // The arity
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ar.getNext() != null, 2);
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ar.getNext().String() != null, 2);
    arity = Integer.parseInt(ar.getNext().String(),10);

    // The precedence
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,pr.getNext() != null, 3);
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,pr.getNext().String() != null, 3);
    precedence = Integer.parseInt(pr.getNext().String(),10);
 
    // Finally define the rule base
    aEnvironment.DefineRule(LispStandard.SymbolName(aEnvironment,orig),
                            arity,
                            precedence,
                            predicate,
                            body );

    // Return true
    LispStandard.InternalTrue(aEnvironment,PiperEvalCaller.RESULT(aEnvironment, aStackTop));
  }




  void InternalDefMacroRuleBase(LispEnvironment aEnvironment, int aStackTop, boolean aListed) throws Exception
  {
    // Get operator
	  LispPtr args = new LispPtr();
    String orig=null;
 
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).getNext() != null, 1);
    orig = PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).getNext().String();
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

    // The arguments
    args.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2).getNext());
    LispError.CHK_ISLIST_CORE(aEnvironment,aStackTop,args,2);

    // Finally define the rule base
    aEnvironment.DeclareMacroRuleBase(LispStandard.SymbolName(aEnvironment,orig),
                                 args.getNext().SubList().getNext(),aListed);
 
    // Return true
    LispStandard.InternalTrue(aEnvironment,PiperEvalCaller.RESULT(aEnvironment, aStackTop));
  }




  public void InternalNewRulePattern(LispEnvironment aEnvironment, int aStackTop, boolean aMacroMode) throws Exception
  {
    int arity;
    int precedence;

    LispPtr ar = new LispPtr();
    LispPtr pr = new LispPtr();
    LispPtr predicate = new LispPtr();
    LispPtr body = new LispPtr();
    String orig=null;
 
    // Get operator
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).getNext() != null, 1);
    orig = PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).getNext().String();
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
    ar.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2).getNext());
    pr.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 3).getNext());
    predicate.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 4).getNext());
    body.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 5).getNext());
 
    // The arity
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ar.getNext() != null, 2);
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ar.getNext().String() != null, 2);
    arity = Integer.parseInt(ar.getNext().String(),10);

    // The precedence
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,pr.getNext() != null, 3);
    LispError.CHK_ARG_CORE(aEnvironment,aStackTop,pr.getNext().String() != null, 3);
    precedence = Integer.parseInt(pr.getNext().String(),10);
 
    // Finally define the rule base
    aEnvironment.DefineRulePattern(LispStandard.SymbolName(aEnvironment,orig),
                            arity,
                            precedence,
                            predicate,
                            body );

    // Return true
    LispStandard.InternalTrue(aEnvironment,PiperEvalCaller.RESULT(aEnvironment, aStackTop));
  }



  class LispQuote extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      RESULT(aEnvironment, aStackTop).setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext().Copy(false));
    }
  }

  class LispEval extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      aEnvironment.iEvaluator.Eval(aEnvironment, RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 1));
    }
  }

  class LispWrite extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr subList = ARGUMENT(aEnvironment, aStackTop, 1).getNext().SubList();
      if (subList != null)
      {
        LispIterator iter = new LispIterator(subList);
        iter.GoNext();
        while (iter.GetObject() != null)
        {
          aEnvironment.iCurrentPrinter.Print(iter.Ptr(),aEnvironment.iCurrentOutput,aEnvironment);
          iter.GoNext();
        }
      }
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispWriteString extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).getNext()!= null,1);
      String str = ARGUMENT(aEnvironment, aStackTop, 1).getNext().String();
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
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispFullForm extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      RESULT(aEnvironment, aStackTop).setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      LispPrinter printer = new LispPrinter();
      printer.Print(RESULT(aEnvironment, aStackTop), aEnvironment.iCurrentOutput, aEnvironment);
      aEnvironment.iCurrentOutput.Write("\n");
    }
  }

  class LispDefaultDirectory extends PiperEvalCaller
  {
    @SuppressWarnings("unchecked")
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      // Get file name
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).getNext() != null, 1);
      String orig = ARGUMENT(aEnvironment, aStackTop, 1).getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
      String oper = LispStandard.InternalUnstringify(orig);
      ExtendibleArray<String> dir = (ExtendibleArray<String>) aEnvironment.iInputDirectories;
      dir.add(oper);
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispFromFile extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispError.CHK_CORE(aEnvironment, aStackTop,aEnvironment.iSecure == false, LispError.KLispErrSecurityBreach);
      LispPtr evaluated = new LispPtr();
      aEnvironment.iEvaluator.Eval(aEnvironment, evaluated, ARGUMENT(aEnvironment, aStackTop, 1));

      // Get file name
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.getNext() != null, 1);
      String orig = evaluated.getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

      String hashedname = aEnvironment.HashTable().LookUpUnStringify(orig);

      InputStatus oldstatus = aEnvironment.iInputStatus;
      LispInput previous = aEnvironment.iCurrentInput;
      try
      {
        aEnvironment.iInputStatus.SetTo(hashedname);
        LispInput input = // new StdFileInput(hashedname, aEnvironment.iInputStatus);
          LispStandard.OpenInputFile(aEnvironment, aEnvironment.iInputDirectories, hashedname, aEnvironment.iInputStatus);
        aEnvironment.iCurrentInput = input;
        // Open file
        LispError.CHK_CORE(aEnvironment, aStackTop,input != null, LispError.KLispErrFileNotFound);

        // Evaluate the body
        aEnvironment.iEvaluator.Eval(aEnvironment, RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 2));
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
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr evaluated = new LispPtr();
      aEnvironment.iEvaluator.Eval(aEnvironment, evaluated, ARGUMENT(aEnvironment, aStackTop, 1));

      // Get file name
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.getNext() != null, 1);
      String orig = evaluated.getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
      String oper = LispStandard.InternalUnstringify(orig);

      InputStatus oldstatus = aEnvironment.iInputStatus;
      aEnvironment.iInputStatus.SetTo("String");
      StringInput newInput = new StringInput(new StringBuffer(oper),aEnvironment.iInputStatus);

      LispInput previous = aEnvironment.iCurrentInput;
      aEnvironment.iCurrentInput = newInput;
      try
      {
        // Evaluate the body
        aEnvironment.iEvaluator.Eval(aEnvironment, RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 2));
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
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
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
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispTokenizer tok = aEnvironment.iCurrentTokenizer;
      String result;
      result = tok.NextToken(aEnvironment.iCurrentInput, aEnvironment.HashTable());

      if (result.length() == 0)
      {
          RESULT(aEnvironment, aStackTop).setNext(aEnvironment.iEndOfFile.Copy(false));
          return;
      }
      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,result));
    }
  }

  class LispToFile extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispError.CHK_CORE(aEnvironment, aStackTop,aEnvironment.iSecure == false, LispError.KLispErrSecurityBreach);

      LispPtr evaluated = new LispPtr();
      aEnvironment.iEvaluator.Eval(aEnvironment, evaluated, ARGUMENT(aEnvironment, aStackTop, 1));

      // Get file name
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.getNext() != null, 1);
      String orig = evaluated.getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
      String oper = LispStandard.InternalUnstringify(orig);

      // Open file for writing
      OutputStream localFP = aEnvironment.getOutputFileLocator().getOutputStream(oper);
      LispError.CHK_CORE(aEnvironment, aStackTop,localFP != null, LispError.KLispErrFileNotFound);
      StdFileOutput newOutput = new StdFileOutput(localFP);

      LispOutput previous = aEnvironment.iCurrentOutput;
      aEnvironment.iCurrentOutput = newOutput;

      try
      {
        aEnvironment.iEvaluator.Eval(aEnvironment, RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 2));
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
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      StringBuffer oper = new StringBuffer();
      StringOutput newOutput = new StringOutput(oper);
      LispOutput previous = aEnvironment.iCurrentOutput;
      aEnvironment.iCurrentOutput = newOutput;
      try
      {
        // Evaluate the body
        aEnvironment.iEvaluator.Eval(aEnvironment, RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 1));

        //Return the result
        RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,aEnvironment.HashTable().LookUpStringify(oper.toString())));
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
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispOutput previous = aEnvironment.iCurrentOutput;
      aEnvironment.iCurrentOutput = aEnvironment.iInitialOutput;
      try
      {
        aEnvironment.iEvaluator.Eval(aEnvironment, RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 1));
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
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispError.CHK_CORE(aEnvironment, aStackTop,aEnvironment.iSecure == false, LispError.KLispErrSecurityBreach);

      LispPtr evaluated = new LispPtr();
      evaluated.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());

      // Get file name
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.getNext() != null, 1);
      String orig = evaluated.getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

      LispStandard.InternalLoad(aEnvironment,orig);
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispSetVar extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      MathCommands.InternalSetVar(aEnvironment, aStackTop, false, false);
    }
  }

  class LispMacroSetVar extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      MathCommands.InternalSetVar(aEnvironment, aStackTop, true, false);
    }
  }

  class LispSetGlobalLazyVariable extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      MathCommands.InternalSetVar(aEnvironment, aStackTop, false, true);
    }
  }

  class LispClearVar extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr subList = ARGUMENT(aEnvironment, aStackTop, 1).getNext().SubList();
      if (subList != null)
      {
        LispIterator iter = new LispIterator(subList);
        iter.GoNext();
        int nr=1;
        while (iter.GetObject() != null)
        {
          String str;
          str = iter.GetObject().String();
          LispError.CHK_ARG_CORE(aEnvironment,aStackTop,str != null, nr);
          aEnvironment.UnsetVariable(str);
          iter.GoNext();
          nr++;
        }
      }
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispNewLocal extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr subList = ARGUMENT(aEnvironment, aStackTop, 1).getNext().SubList();
      if (subList!= null)
      {
        LispIterator iter = new LispIterator(subList);
        iter.GoNext();

        int nr = 1;
        while (iter.GetObject() != null)
        {
          String variable = iter.GetObject().String();
          LispError.CHK_ARG_CORE(aEnvironment,aStackTop,variable != null,nr);
    // printf("Variable %s\n",variable.String());
          aEnvironment.NewLocal(variable,null);
          iter.GoNext();
          nr++;
        }
      }
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispHead extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispStandard.InternalNth(RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 1),1);
    }
  }

  class LispNth extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      String str;
      str = ARGUMENT(aEnvironment, aStackTop, 2).getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,str != null,2);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,LispStandard.IsNumber(str,false),2);
      int index = Integer.parseInt(str);
      LispStandard.InternalNth(RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 1), index);
    }
  }

  class LispTail extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr first = new LispPtr();
      LispStandard.InternalTail(first, ARGUMENT(aEnvironment, aStackTop, 1));
      LispStandard.InternalTail(RESULT(aEnvironment, aStackTop), first);
      LispPtr head = new LispPtr();
      head.setNext(aEnvironment.iList.Copy(false));
      head.getNext().setNext(RESULT(aEnvironment, aStackTop).getNext().SubList().getNext());
      RESULT(aEnvironment, aStackTop).getNext().SubList().setNext(head.getNext());
    }
  }

  class LispDestructiveReverse extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr reversed = new LispPtr();
      reversed.setNext(aEnvironment.iList.Copy(false));
      LispStandard.InternalReverseList(reversed.getNext(), ARGUMENT(aEnvironment, aStackTop, 1).getNext().SubList().getNext());
      RESULT(aEnvironment, aStackTop).setNext(LispSubList.New(reversed.getNext()));
    }
  }

  class LispLength extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispPtr subList = ARGUMENT(aEnvironment, aStackTop, 1).getNext().SubList();
      if (subList != null)
      {
        int num = LispStandard.InternalListLength(subList.getNext());
        RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,""+num));
        return;
      }
      String string = ARGUMENT(aEnvironment, aStackTop, 1).getNext().String();
      if (LispStandard.InternalIsString(string))
      {
        int num = string.length()-2;
        RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,""+num));
        return;
      }
      GenericClass gen = ARGUMENT(aEnvironment, aStackTop, 1).getNext().Generic();
      if (gen != null)
        if (gen.TypeName().equals("\"Array\""))
        {
          int size=((ArrayClass)gen).Size();
          RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,""+size));
          return;
        }
    //  CHK_ISLIST_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1),1);
    }
  }

  class LispList extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr all = new LispPtr();
      all.setNext(aEnvironment.iList.Copy(false));
      LispIterator tail = new LispIterator(all);
      tail.GoNext();
      LispIterator iter = new LispIterator(ARGUMENT(aEnvironment, aStackTop, 1).getNext().SubList());
      iter.GoNext();
      while (iter.GetObject() != null)
      {
    	  LispPtr evaluated = new LispPtr();
        aEnvironment.iEvaluator.Eval(aEnvironment,evaluated,iter.Ptr());
        tail.Ptr().setNext(evaluated.getNext());
        tail.GoNext();
        iter.GoNext();
      }
      RESULT(aEnvironment, aStackTop).setNext(LispSubList.New(all.getNext()));
    }
  }

  class LispUnList extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).getNext() != null, 1);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).getNext().SubList() != null, 1);
      LispObject subList = ARGUMENT(aEnvironment, aStackTop, 1).getNext().SubList().getNext();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,subList != null, 1);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,subList.String() == aEnvironment.iList.String(),1);
      LispStandard.InternalTail(RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 1));
    }
  }

  class LispListify extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).getNext().SubList() != null, 1);
      LispPtr head = new LispPtr();
      head.setNext(aEnvironment.iList.Copy(false));
      head.getNext().setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext().SubList().getNext());
      RESULT(aEnvironment, aStackTop).setNext(LispSubList.New(head.getNext()));
    }
  }

  class LispConcatenate extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr all = new LispPtr();
      all.setNext(aEnvironment.iList.Copy(false));
      LispIterator tail = new LispIterator(all);
      tail.GoNext();
      int arg = 1;

      LispIterator iter = new LispIterator(ARGUMENT(aEnvironment, aStackTop, 1).getNext().SubList());
      iter.GoNext();
      while (iter.GetObject() != null)
      {
        LispError.CHK_ISLIST_CORE(aEnvironment,aStackTop,iter.Ptr(),arg);
        LispStandard.InternalFlatCopy(tail.Ptr(),iter.Ptr().getNext().SubList().getNext());
        while (tail.GetObject() != null)
          tail.GoNext();
        iter.GoNext();
        arg++;
      }
      RESULT(aEnvironment, aStackTop).setNext(LispSubList.New(all.getNext()));
    }
  }

  class LispConcatenateStrings extends PiperEvalCaller
  {
    void ConcatenateStrings(StringBuffer aStringBuffer, LispEnvironment aEnvironment, int aStackTop) throws Exception
    {
      aStringBuffer.append('\"');
      int arg=1;
 
      LispIterator iter = new LispIterator(ARGUMENT(aEnvironment, aStackTop, 1).getNext().SubList());
      iter.GoNext();
      while (iter.GetObject() != null)
      {
        LispError.CHK_ISSTRING_CORE(aEnvironment,aStackTop,iter.Ptr(),arg);
        String thisString = iter.GetObject().String();
        String toAppend = thisString.substring(1,thisString.length()-1);
        aStringBuffer.append(toAppend);
        iter.GoNext();
        arg++;
      }
      aStringBuffer.append('\"');
    }
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      StringBuffer strBuffer = new StringBuffer("");
      ConcatenateStrings(strBuffer,aEnvironment, aStackTop);
      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,strBuffer.toString()));
    }
  }

  class LispDelete extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      MathCommands.InternalDelete(aEnvironment, aStackTop,false);
    }
  }

  class LispDestructiveDelete extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      MathCommands.InternalDelete(aEnvironment, aStackTop,true);
    }
  }

  class LispInsert extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      MathCommands.InternalInsert(aEnvironment, aStackTop,false);
    }
  }

  class LispDestructiveInsert extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      MathCommands.InternalInsert(aEnvironment, aStackTop,true);
    }
  }

  class LispReplace extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      MathCommands.InternalReplace(aEnvironment, aStackTop,false);
    }
  }

  class LispDestructiveReplace extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      MathCommands.InternalReplace(aEnvironment, aStackTop,true);
    }
  }

  class LispAtomize extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr evaluated = new LispPtr();
      evaluated.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());

      // Get operator
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.getNext() != null, 1);
      String orig = evaluated.getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,aEnvironment.HashTable().LookUpUnStringify(orig)));
    }
  }

  class LispStringify extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr evaluated = new LispPtr();
      evaluated.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());

      // Get operator
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.getNext() != null, 1);
      String orig = evaluated.getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,aEnvironment.HashTable().LookUpStringify(orig)));
    }
  }

  class LispCharString extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      String str;
      str = ARGUMENT(aEnvironment, aStackTop, 1).getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,str != null,2);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,LispStandard.IsNumber(str,false),2);
      char asciiCode = (char)Integer.parseInt(str,10);
      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,"\""+asciiCode+"\""));
    }
  }

  class LispFlatCopy extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr copied = new LispPtr();
      LispStandard.InternalFlatCopy(copied,ARGUMENT(aEnvironment, aStackTop, 1).getNext().SubList());
      RESULT(aEnvironment, aStackTop).setNext(LispSubList.New(copied.getNext()));
    }
  }

  class LispProgBody extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      // Allow accessing previous locals.
      aEnvironment.PushLocalFrame(false);
      try
      {
        LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
 
        // Evaluate args one by one.

        LispIterator iter = new LispIterator(ARGUMENT(aEnvironment, aStackTop, 1).getNext().SubList());
        iter.GoNext();
        while (iter.GetObject() != null)
        {
          aEnvironment.iEvaluator.Eval(aEnvironment, RESULT(aEnvironment, aStackTop), iter.Ptr());
          iter.GoNext();
        }
      }
      catch (Exception e) { throw e; }
      finally
      {
        aEnvironment.PopLocalFrame();
      }
    }
  }

  class LispWhile extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispPtr arg1 = ARGUMENT(aEnvironment, aStackTop, 1);
      LispPtr arg2 = ARGUMENT(aEnvironment, aStackTop, 2);
 
      LispPtr predicate = new LispPtr();
      aEnvironment.iEvaluator.Eval(aEnvironment, predicate, arg1);

      while (LispStandard.IsTrue(aEnvironment,predicate))
      {
    	  LispPtr evaluated = new LispPtr();
          aEnvironment.iEvaluator.Eval(aEnvironment, evaluated, arg2);
          aEnvironment.iEvaluator.Eval(aEnvironment, predicate, arg1);

      }
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,LispStandard.IsFalse(aEnvironment,predicate),1);
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispIf extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      int nrArguments = LispStandard.InternalListLength(ARGUMENT(aEnvironment, aStackTop, 0));
      LispError.CHK_CORE(aEnvironment,aStackTop,nrArguments == 3 || nrArguments == 4,LispError.KLispErrWrongNumberOfArgs);

      LispPtr predicate = new LispPtr();
      aEnvironment.iEvaluator.Eval(aEnvironment, predicate, ARGUMENT(aEnvironment, aStackTop, 1));

      if (LispStandard.IsTrue(aEnvironment,predicate))
      {
        aEnvironment.iEvaluator.Eval(aEnvironment, RESULT(aEnvironment, aStackTop), Argument(ARGUMENT(aEnvironment, aStackTop, 0),2));
      }
      else
      {
          LispError.CHK_ARG_CORE(aEnvironment,aStackTop,LispStandard.IsFalse(aEnvironment,predicate),1);
          if (nrArguments == 4)
          {
            aEnvironment.iEvaluator.Eval(aEnvironment, RESULT(aEnvironment, aStackTop), Argument(ARGUMENT(aEnvironment, aStackTop, 0),3));
          }
          else
          {
            LispStandard.InternalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
          }
      }
    }
  }

  class LispCheck extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr pred = new LispPtr();
      aEnvironment.iEvaluator.Eval(aEnvironment, pred, ARGUMENT(aEnvironment, aStackTop, 1));
      if (!LispStandard.IsTrue(aEnvironment,pred))
      {
    	  LispPtr evaluated = new LispPtr();
          aEnvironment.iEvaluator.Eval(aEnvironment, evaluated, ARGUMENT(aEnvironment, aStackTop, 2));
          LispError.CHK_ISSTRING_CORE(aEnvironment,aStackTop,evaluated,2);
          throw new Exception(evaluated.getNext().String());
      }
      RESULT(aEnvironment, aStackTop).setNext(pred.getNext());
    }
  }

  class LispTrapError extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      try
      {
        aEnvironment.iEvaluator.Eval(aEnvironment, RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 1));
      }
      catch (Exception e)
      {
        aEnvironment.iError = e.toString();
        aEnvironment.iEvaluator.Eval(aEnvironment, RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 2));
        aEnvironment.iError = null;
      }
    }
  }

  class LispGetCoreError extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,aEnvironment.HashTable().LookUpStringify(aEnvironment.iError)));
    }
  }

  class LispPreFix extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      MathCommands.MultiFix(aEnvironment, aStackTop, aEnvironment.iPrefixOperators);
    }
  }

  class LispInFix extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      MathCommands.MultiFix(aEnvironment, aStackTop, aEnvironment.iInfixOperators);
    }
  }

  class LispPostFix extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      int nrArguments = LispStandard.InternalListLength(ARGUMENT(aEnvironment, aStackTop, 0));
      if (nrArguments == 2)
      {
        MathCommands.SingleFix(0, aEnvironment, aStackTop, aEnvironment.iPostfixOperators);
      }
      else
      {
        MathCommands.MultiFix(aEnvironment, aStackTop, aEnvironment.iPostfixOperators);
      }
    }
  }

  class LispBodied extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      MathCommands.MultiFix(aEnvironment, aStackTop, aEnvironment.iBodiedOperators);
    }
  }

  class LispRuleBase extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      InternalRuleBase(aEnvironment, aStackTop, false);
    }
  }

  class LispMacroRuleBase extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      InternalRuleBase(aEnvironment, aStackTop, false);
    }
  }

  class LispRuleBaseListed extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      InternalRuleBase(aEnvironment, aStackTop, true);
    }
  }

  class LispMacroRuleBaseListed extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      InternalRuleBase(aEnvironment, aStackTop, true);
    }
  }

  class LispDefMacroRuleBase extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      InternalDefMacroRuleBase(aEnvironment, aStackTop, false);
    }
  }

  class LispDefMacroRuleBaseListed extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      InternalDefMacroRuleBase(aEnvironment, aStackTop, true);
    }
  }

  class LispHoldArg extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      // Get operator
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).getNext() != null, 1);
      String orig = ARGUMENT(aEnvironment, aStackTop, 1).getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

      // The arguments
      String tohold = ARGUMENT(aEnvironment, aStackTop, 2).getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,tohold != null, 2);
      aEnvironment.HoldArgument(LispStandard.SymbolName(aEnvironment,orig), tohold);
      // Return true
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispNewRule extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      InternalNewRule(aEnvironment, aStackTop);
    }
  }

  class LispMacroNewRule extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      InternalNewRule(aEnvironment, aStackTop);
    }
  }

  class LispUnFence extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      // Get operator
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).getNext() != null, 1);
      String orig = ARGUMENT(aEnvironment, aStackTop, 1).getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

      // The arity
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 2).getNext() != null, 2);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 2).getNext().String() != null, 2);
      int arity = Integer.parseInt(ARGUMENT(aEnvironment, aStackTop, 2).getNext().String(),10);

      aEnvironment.UnFenceRule(LispStandard.SymbolName(aEnvironment,orig), arity);
 
      // Return true
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispRetract extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      // Get operator
    	LispPtr evaluated = new LispPtr();
      evaluated.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());

      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.getNext() != null, 1);
      String orig = evaluated.getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
      String oper = LispStandard.SymbolName(aEnvironment,orig);
 
      LispPtr arity = new LispPtr();
      arity.setNext(ARGUMENT(aEnvironment, aStackTop, 2).getNext());
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,arity.getNext().String() != null, 2);
      int ar = Integer.parseInt(arity.getNext().String(),10);
      aEnvironment.Retract(oper, ar);
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispNot extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr evaluated = new LispPtr();
      evaluated.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      if (LispStandard.IsTrue(aEnvironment, evaluated) || LispStandard.IsFalse(aEnvironment, evaluated))
      {
        LispStandard.InternalNot(RESULT(aEnvironment, aStackTop), aEnvironment, evaluated);
      }
      else
      {
    	  LispPtr ptr = new LispPtr();
        ptr.setNext(ARGUMENT(aEnvironment, aStackTop, 0).getNext().Copy(false));
        ptr.getNext().setNext(evaluated.getNext());
        RESULT(aEnvironment, aStackTop).setNext(LispSubList.New(ptr.getNext()));
      }
    }
  }

  class LispLazyAnd extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr nogos = new LispPtr();
      int nrnogos=0;
      LispPtr evaluated = new LispPtr();

      LispIterator iter = new LispIterator(ARGUMENT(aEnvironment, aStackTop, 1).getNext().SubList());
      iter.GoNext();
      while (iter.GetObject() != null)
      {
          aEnvironment.iEvaluator.Eval(aEnvironment, evaluated, iter.Ptr());
          if (LispStandard.IsFalse(aEnvironment, evaluated))
          {
              LispStandard.InternalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
              return;
          }
          else if (!LispStandard.IsTrue(aEnvironment, evaluated))
          {
        	  LispPtr ptr = new LispPtr();
              nrnogos++;
              ptr.setNext(evaluated.getNext().Copy(false));
              ptr.getNext().setNext(nogos.getNext());
              nogos.setNext(ptr.getNext());
          }
 
          iter.GoNext();
      }

      if (nogos.getNext() != null)
      {
          if (nrnogos == 1)
          {
              RESULT(aEnvironment, aStackTop).setNext(nogos.getNext());
          }
          else
          {
        	  LispPtr ptr = new LispPtr();

              LispStandard.InternalReverseList(ptr, nogos);
              nogos.setNext(ptr.getNext());

              ptr.setNext(ARGUMENT(aEnvironment, aStackTop, 0).getNext().Copy(false));
              ptr.getNext().setNext(nogos.getNext());
              nogos.setNext(ptr.getNext());
              RESULT(aEnvironment, aStackTop).setNext(LispSubList.New(nogos.getNext()));

              //aEnvironment.CurrentPrinter().Print(RESULT(aEnvironment, aStackTop), *aEnvironment.CurrentOutput());
          }
      }
      else
      {
          LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
      }
    }
  }

  class LispLazyOr extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr nogos = new LispPtr();
      int nrnogos=0;

      LispPtr evaluated = new LispPtr();

      LispIterator iter = new LispIterator(ARGUMENT(aEnvironment, aStackTop, 1).getNext().SubList());
      iter.GoNext();
      while (iter.GetObject() != null)
      {
        aEnvironment.iEvaluator.Eval(aEnvironment, evaluated, iter.Ptr());
        if (LispStandard.IsTrue(aEnvironment, evaluated))
        {
          LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
          return;
        }
        else if (!LispStandard.IsFalse(aEnvironment, evaluated))
        {
        	LispPtr ptr = new LispPtr();
          nrnogos++;

          ptr.setNext(evaluated.getNext().Copy(false));
          ptr.getNext().setNext(nogos.getNext());
          nogos.setNext(ptr.getNext());
        }
        iter.GoNext();
      }

      if (nogos.getNext() != null)
      {
        if (nrnogos == 1)
        {
          RESULT(aEnvironment, aStackTop).setNext(nogos.getNext());
        }
        else
        {
        	LispPtr ptr = new LispPtr();

          LispStandard.InternalReverseList(ptr, nogos);
          nogos.setNext(ptr.getNext());

          ptr.setNext(ARGUMENT(aEnvironment, aStackTop, 0).getNext().Copy(false));
          ptr.getNext().setNext(nogos.getNext());
          nogos.setNext(ptr.getNext());
          RESULT(aEnvironment, aStackTop).setNext(LispSubList.New(nogos.getNext()));
        }
        //aEnvironment.CurrentPrinter().Print(RESULT(aEnvironment, aStackTop), *aEnvironment.CurrentOutput());
      }
      else
      {
        LispStandard.InternalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
      }
    }
  }

  class LispEquals extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr evaluated1 = new LispPtr();
      evaluated1.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      LispPtr evaluated2 = new LispPtr();
      evaluated2.setNext(ARGUMENT(aEnvironment, aStackTop, 2).getNext());

      LispStandard.InternalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop),
                      LispStandard.InternalEquals(aEnvironment, evaluated1, evaluated2));
    }
  }

  abstract class LispLexCompare2
  {
    abstract boolean lexfunc(String f1, String f2, StringIntern aHashTable,int aPrecision);
    abstract boolean numfunc(BigNumber n1, BigNumber n2);

    void Compare(LispEnvironment aEnvironment, int aStackTop) throws Exception
    {
    	LispPtr result1 = new LispPtr();
    	LispPtr result2 = new LispPtr();
      result1.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      result2.setNext(PiperEvalCaller.ARGUMENT(aEnvironment, aStackTop, 2).getNext());
      boolean cmp;
      BigNumber n1 = result1.getNext().Number(aEnvironment.Precision());
      BigNumber n2 = result2.getNext().Number(aEnvironment.Precision());
      if (n1 != null && n2 != null)
      {
        cmp =numfunc(n1,n2);
      }
      else
      {
        String str1;
        String str2;
        str1 = result1.getNext().String();
        str2 = result2.getNext().String();
        LispError.CHK_ARG_CORE(aEnvironment,aStackTop,str1 != null ,1);
        LispError.CHK_ARG_CORE(aEnvironment,aStackTop,str2 != null, 2);
      // the precision argument is ignored in "lex" functions
        cmp =lexfunc(str1,str2,
                              aEnvironment.HashTable(),
                              aEnvironment.Precision());
      }
 
      LispStandard.InternalBoolean(aEnvironment,PiperEvalCaller.RESULT(aEnvironment, aStackTop), cmp);
    }
  }


  class LexLessThan extends LispLexCompare2
  {
    boolean lexfunc(String f1, String f2, StringIntern aHashTable,int aPrecision)
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
    boolean lexfunc(String f1, String f2, StringIntern aHashTable,int aPrecision)
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
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      compare.Compare(aEnvironment,aStackTop);
    }
  }

  class LispGreaterThan extends PiperEvalCaller
  {
    LexGreaterThan compare = new LexGreaterThan();
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      compare.Compare(aEnvironment,aStackTop);
    }
  }

  class LispIsFunction extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr result = new LispPtr();
      result.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      LispStandard.InternalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop),
                      result.getNext().SubList()!=null);
    }
  }

  class LispIsAtom extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr result = new LispPtr();
      result.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      String s = result.getNext().String();
      LispStandard.InternalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop),s!=null);
    }
  }

  class LispIsNumber extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispPtr result = new LispPtr();
      result.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      LispStandard.InternalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), result.getNext().Number(aEnvironment.Precision()) != null);
    }
  }

  class LispIsInteger extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispPtr result = new LispPtr();
      result.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());

      BigNumber num = result.getNext().Number(aEnvironment.Precision());
      if (num == null)
      {
        LispStandard.InternalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
      }
      else
      {
        LispStandard.InternalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), num.IsInt());
      }
    }
  }

  class LispIsList extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispPtr result = new LispPtr();
      result.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      LispStandard.InternalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop),LispStandard.InternalIsList(result));
    }
  }

  class LispIsString extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr result = new LispPtr();
      result.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      LispStandard.InternalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop),
                      LispStandard.InternalIsString(result.getNext().String()));
    }
  }

  class LispIsBound extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      String str = ARGUMENT(aEnvironment, aStackTop, 1).getNext().String();
      if (str != null)
      {
    	  LispPtr val = new LispPtr();
        aEnvironment.GetVariable(str,val);
        if (val.getNext() != null)
        {
          LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
          return;
        }
      }
      LispStandard.InternalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispMultiply extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber y = GetNumber(aEnvironment, aStackTop, 2);
      BigNumber z = x.multiply(y,aEnvironment.Precision());
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

/// Corresponds to the Piper function \c MathAdd.
/// If called with one argument (unary plus), this argument is
/// converted to BigNumber. If called with two arguments (binary plus),
/// both argument are converted to a BigNumber, and these are added
/// together at the current precision. The sum is returned.
/// \sa GetNumber(), BigNumber::Add()
  class LispAdd extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      int length = LispStandard.InternalListLength(ARGUMENT(aEnvironment, aStackTop, 0));
      if (length == 2)
      {
        BigNumber x;
        x = MathCommands.GetNumber(aEnvironment, aStackTop, 1);
        RESULT(aEnvironment, aStackTop).setNext(new LispNumber(x));
        return;
      }
      else
      {
        BigNumber x = MathCommands.GetNumber(aEnvironment, aStackTop, 1);
        BigNumber y = MathCommands.GetNumber(aEnvironment, aStackTop, 2);
        BigNumber z = x.add(y,aEnvironment.Precision());
        RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
        return;
      }
    }
  }

  class LispSubtract extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      int length = LispStandard.InternalListLength(ARGUMENT(aEnvironment, aStackTop, 0));
      if (length == 2)
      {
        BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
        BigNumber z = x.negate(x.GetPrecision());
        RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
        return;
      }
      else
      {
        BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
        BigNumber y = GetNumber(aEnvironment, aStackTop, 2);
        BigNumber yneg = y.negate(y.GetPrecision());
        BigNumber z = x.add(yneg,aEnvironment.Precision());
        RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
        return;
      }
    }
  }

  class LispDivide extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber y = GetNumber(aEnvironment, aStackTop, 2);
      BigNumber z = BigNumber.create(aEnvironment.Precision());
      // if both arguments are integers, then BigNumber::Divide would perform an integer divide, but we want a float divide here.
      if (x.IsInt() && y.IsInt())
      {
        // why can't we just say BigNumber temp; ?
        BigNumber tempx = x.becomeFloat();  // coerce x to float
        BigNumber tempy = y.becomeFloat();  // coerce x to float
        z = tempx.divide(tempy,aEnvironment.Precision());
      }
      else
      {
        z = x.divide(y,aEnvironment.Precision());
      }
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
      return;
    }
  }

  class PiperBuiltinPrecisionSet extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr index = new LispPtr();
      index.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext() != null, 1);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext().String() != null, 1);

      int ind = Integer.parseInt(index.getNext().String(),10);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ind>0,1);
      aEnvironment.SetPrecision(ind);
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispGetExactBits extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber z = BigNumber.create(aEnvironment.Precision(),
        (x.IsInt())
      ? x.BitCount()  // for integers, return the bit count
        : LispStandard.digits_to_bits((long)(x.GetPrecision()), 10)   // for floats, return the precision
        );
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }
 

  class LispSetExactBits extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber y = GetNumber(aEnvironment, aStackTop, 2);
      BigNumber z = x;
 
    // do nothing for integers
    if (!(z.IsInt()))
      z = z.setPrecision((int)(LispStandard.bits_to_digits((long)(y.Double()), 10)));
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispBitCount extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber z = BigNumber.create(aEnvironment.Precision(),x.BitCount());
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispMathSign extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber z = BigNumber.create(aEnvironment.Precision(),x.Sign());
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispMathIsSmall extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      LispStandard.InternalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), x.IsSmall());
    }
  }

  class LispMathNegate extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber z = x.negate(aEnvironment.Precision());
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispFloor extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber z = x.floor(aEnvironment.Precision());
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispCeil extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber z = x.negate(aEnvironment.Precision());
      z = z.floor(aEnvironment.Precision());
      z = z.negate(z.GetPrecision());
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispAbs extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber z = x;
      if (x.Sign()<0)
        z = x.negate(x.GetPrecision());
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispMod extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber y = GetNumber(aEnvironment, aStackTop, 2);
      BigNumber z = x.mod(y,aEnvironment.Precision());
      
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispDiv extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber y = GetNumber(aEnvironment, aStackTop, 2);
      if (x.IsInt() && y.IsInt())
      {  // both integer, perform integer division
          BigNumber z = BigNumber.create(aEnvironment.Precision());
          z = x.divide(y,aEnvironment.Precision());
          RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
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
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber y = GetNumber(aEnvironment, aStackTop, 2);
      long result = 0;  // initialize just in case
      if (x.IsInt() && x.IsSmall() && y.IsInt() && y.IsSmall())
      {
        // bits_to_digits uses unsigned long, see numbers.h
        int base = (int)y.Double();
        result = LispStandard.bits_to_digits((long)(x.Double()), base);
      }
      else
      {
        throw new Piperexception("BitsToDigits: error: arguments ("+x.Double()+", "+y.Double()+") must be small integers");
      }
      BigNumber z = BigNumber.create(aEnvironment.Precision(),(long)result);
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispDigitsToBits extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber y = GetNumber(aEnvironment, aStackTop, 2);
      long result = 0;  // initialize just in case
      if (x.IsInt() && x.IsSmall() && y.IsInt() && y.IsSmall())
      {
        // bits_to_digits uses unsigned long, see numbers.h
        int base = (int)y.Double();
        result = LispStandard.digits_to_bits((long)(x.Double()), base);
      }
      else
      {
        throw new Piperexception("BitsToDigits: error: arguments ("+x.Double()+", "+y.Double()+") must be small integers");
      }
      BigNumber z = BigNumber.create(aEnvironment.Precision(),(long)result);
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispGcd extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber y = GetNumber(aEnvironment, aStackTop, 2);
      BigNumber z = x.gcd(y, aEnvironment.Precision());
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispSystemCall extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).getNext() != null, 1);
      String orig = ARGUMENT(aEnvironment, aStackTop, 1).getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
      String oper = LispStandard.InternalUnstringify(orig);
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
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x;
      x = GetNumber(aEnvironment, aStackTop, 1);
      double result = Math.asin(x.Double());
      BigNumber z = BigNumber.create(aEnvironment.Precision(),result);
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispFastLog extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x;
      x = GetNumber(aEnvironment, aStackTop, 1);
      double result = Math.log(x.Double());
      BigNumber z = BigNumber.create(aEnvironment.Precision(),result);
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispFastPower extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x, y;
      x = GetNumber(aEnvironment, aStackTop, 1);
      y = GetNumber(aEnvironment, aStackTop, 2);
      double result = Math.pow(x.Double(), y.Double());
      BigNumber z = BigNumber.create(aEnvironment.Precision(),result);
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispShiftLeft extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber n = GetNumber(aEnvironment, aStackTop, 2);
      long nrToShift = n.Long();
      BigNumber z = x.shiftLeft((int)nrToShift,aEnvironment.Precision());
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispShiftRight extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber n = GetNumber(aEnvironment, aStackTop, 2);
      long nrToShift = n.Long();
      BigNumber z = x.shiftRight((int)nrToShift,aEnvironment.Precision());
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispFromBase extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      // Get the base to convert to:
      // Evaluate first argument, and store result in oper
    	LispPtr oper = new LispPtr();
      oper.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      // Check that result is a number, and that it is in fact an integer
      BigNumber num = oper.getNext().Number(aEnvironment.Precision());
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,num != null,1);
    // check that the base is an integer between 2 and 32
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,num.IsInt(), 1);

      // Get a short platform integer from the first argument
      int base = (int)(num.Double());

      // Get the number to convert
      LispPtr fromNum = new LispPtr();
      fromNum.setNext(ARGUMENT(aEnvironment, aStackTop, 2).getNext());
      String str2;
      str2 = fromNum.getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,str2 != null,2);

      // Added, unquote a string
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,LispStandard.InternalIsString(str2),2);
      str2 = aEnvironment.HashTable().LookUpUnStringify(str2);

      // convert using correct base
      BigNumber z = BigNumber.create(str2,aEnvironment.Precision(),base);
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispToBase extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      // Get the base to convert to:
      // Evaluate first argument, and store result in oper
    	LispPtr oper = new LispPtr();
      oper.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      // Check that result is a number, and that it is in fact an integer
      BigNumber num = oper.getNext().Number(aEnvironment.Precision());
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,num != null,1);
    // check that the base is an integer between 2 and 32
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,num.IsInt(), 1);

      // Get a short platform integer from the first argument
      int base = (int)(num.Long());

      // Get the number to convert
      BigNumber x = GetNumber(aEnvironment, aStackTop, 2);

      // convert using correct base
      String str;
      str = x.ToString(aEnvironment.Precision(),base);
      // Get unique string from hash table, and create an atom from it.

      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,aEnvironment.HashTable().LookUpStringify(str)));
    }
  }

  class LispMaxEvalDepth extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr index = new LispPtr();
      index.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext() != null, 1);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext().String() != null, 1);

      int ind = Integer.parseInt(index.getNext().String(),10);
      aEnvironment.iMaxEvalDepth = ind;
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispDefLoad extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispError.CHK_CORE(aEnvironment, aStackTop,aEnvironment.iSecure == false, LispError.KLispErrSecurityBreach);

      LispPtr evaluated = new LispPtr();
      evaluated.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());

      // Get file name
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.getNext() != null, 1);
      String orig = evaluated.getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

      LispStandard.LoadDefFile(aEnvironment, orig);
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispUse extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr evaluated = new LispPtr();
      evaluated.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());

      // Get file name
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.getNext() != null, 1);
      String orig = evaluated.getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

      LispStandard.InternalUse(aEnvironment,orig);
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispRightAssociative extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      // Get operator
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).getNext() != null, 1);
      String orig = ARGUMENT(aEnvironment, aStackTop, 1).getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
      aEnvironment.iInfixOperators.SetRightAssociative(LispStandard.SymbolName(aEnvironment,orig));
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispLeftPrecedence extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      // Get operator
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).getNext() != null, 1);
      String orig = ARGUMENT(aEnvironment, aStackTop, 1).getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

      LispPtr index = new LispPtr();
      aEnvironment.iEvaluator.Eval(aEnvironment, index, ARGUMENT(aEnvironment, aStackTop, 2));
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext() != null, 2);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext().String() != null, 2);
      int ind = Integer.parseInt(index.getNext().String(),10);

      aEnvironment.iInfixOperators.SetLeftPrecedence(LispStandard.SymbolName(aEnvironment,orig),ind);
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispRightPrecedence extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      // Get operator
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).getNext() != null, 1);
      String orig = ARGUMENT(aEnvironment, aStackTop, 1).getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);

      LispPtr index = new LispPtr();
      aEnvironment.iEvaluator.Eval(aEnvironment, index, ARGUMENT(aEnvironment, aStackTop, 2));
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext() != null, 2);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext().String() != null, 2);
      int ind = Integer.parseInt(index.getNext().String(),10);

      aEnvironment.iInfixOperators.SetRightPrecedence(LispStandard.SymbolName(aEnvironment,orig),ind);
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispIsBodied extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispInFixOperator op = MathCommands.OperatorInfo(aEnvironment, aStackTop, aEnvironment.iBodiedOperators);
      LispStandard.InternalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), op != null);
    }
  }

  class LispIsInFix extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispInFixOperator op = MathCommands.OperatorInfo(aEnvironment, aStackTop, aEnvironment.iInfixOperators);
      LispStandard.InternalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), op != null);
    }
  }

  class LispIsPreFix extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispInFixOperator op = MathCommands.OperatorInfo(aEnvironment, aStackTop, aEnvironment.iPrefixOperators);
      LispStandard.InternalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), op != null);
    }
  }

  class LispIsPostFix extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispInFixOperator op = MathCommands.OperatorInfo(aEnvironment, aStackTop, aEnvironment.iPostfixOperators);
      LispStandard.InternalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), op != null);
    }
  }

  class LispGetPrecedence extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispInFixOperator op = MathCommands.OperatorInfo(aEnvironment, aStackTop, aEnvironment.iInfixOperators);
      if (op == null)
      {  // also need to check for a postfix or prefix operator
        op = MathCommands.OperatorInfo(aEnvironment, aStackTop, aEnvironment.iPrefixOperators);
        if (op == null)
        {
          op = MathCommands.OperatorInfo(aEnvironment, aStackTop, aEnvironment.iPostfixOperators);
          if (op == null)
          {  // or maybe it's a bodied function
            op = MathCommands.OperatorInfo(aEnvironment, aStackTop, aEnvironment.iBodiedOperators);
            LispError.CHK_CORE(aEnvironment,aStackTop,op!=null, LispError.KLispErrIsNotInFix);
          }
        }
      }
      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,""+op.iPrecedence));
    }
  }

  class LispGetLeftPrecedence extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispInFixOperator op = MathCommands.OperatorInfo(aEnvironment, aStackTop, aEnvironment.iInfixOperators);
      if (op == null)
      {  // infix and postfix operators have left precedence
        op = MathCommands.OperatorInfo(aEnvironment, aStackTop, aEnvironment.iPostfixOperators);
        LispError.CHK_CORE(aEnvironment,aStackTop,op!=null, LispError.KLispErrIsNotInFix);
      }
      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,""+op.iLeftPrecedence));
    }
  }

  class LispGetRightPrecedence extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispInFixOperator op = MathCommands.OperatorInfo(aEnvironment, aStackTop, aEnvironment.iInfixOperators);
      if (op == null)
      {   // bodied, infix and prefix operators have right precedence
        op = MathCommands.OperatorInfo(aEnvironment, aStackTop, aEnvironment.iPrefixOperators);
        if (op == null)
        {   // or maybe it's a bodied function
          op = MathCommands.OperatorInfo(aEnvironment, aStackTop, aEnvironment.iBodiedOperators);
          LispError.CHK_CORE(aEnvironment,aStackTop,op!=null, LispError.KLispErrIsNotInFix);
        }
      }
      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,""+op.iRightPrecedence));
    }
  }

  class PiperBuiltinPrecisionGet extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      // decimal precision
      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,""+aEnvironment.Precision()));
    }
  }

  class LispBitAnd extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber y = GetNumber(aEnvironment, aStackTop, 2);
      BigNumber z = x.bitAnd(y,aEnvironment.Precision());
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispBitOr extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber y = GetNumber(aEnvironment, aStackTop, 2);
      BigNumber z = x.bitOr(y, aEnvironment.Precision());
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispBitXor extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      BigNumber y = GetNumber(aEnvironment, aStackTop, 2);
      BigNumber z = x.bitXor(y, aEnvironment.Precision());
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispSecure extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      boolean prevSecure = aEnvironment.iSecure;
      aEnvironment.iSecure = true;
      try
      {
        aEnvironment.iEvaluator.Eval(aEnvironment, RESULT(aEnvironment, aStackTop), ARGUMENT(aEnvironment, aStackTop, 1));
      }
      catch (Exception e) { throw e; }
      finally { aEnvironment.iSecure = prevSecure; }
    }
  }

  class LispFindFile extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispError.CHK_CORE(aEnvironment, aStackTop,aEnvironment.iSecure == false, LispError.KLispErrSecurityBreach);
 
      LispPtr evaluated = new LispPtr();
      evaluated.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());

      // Get file name
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.getNext() != null, 1);
      String orig = evaluated.getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
      String oper = LispStandard.InternalUnstringify(orig);

      String filename = LispStandard.InternalFindFile(oper, aEnvironment.iInputDirectories);
      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,aEnvironment.HashTable().LookUpStringify(filename)));
    }
  }

  class LispFindFunction extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispError.CHK_CORE(aEnvironment, aStackTop,aEnvironment.iSecure == false, LispError.KLispErrSecurityBreach);
 
      LispPtr evaluated = new LispPtr();
      evaluated.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());

      // Get file name
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.getNext() != null, 1);
      String orig = evaluated.getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
      String oper = LispStandard.InternalUnstringify(orig);

      LispMultiUserFunction multiUserFunc =
          aEnvironment.MultiUserFunction(aEnvironment.HashTable().LookUp(oper));
      if (multiUserFunc != null)
      {
        LispDefFile def = multiUserFunc.iFileToOpen;
        if (def != null)
        {
          RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,def.iFileName));
          return;
        }
      }
      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,"\"\""));
    }
  }

  class LispIsGeneric extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr evaluated = new LispPtr();
      evaluated.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      LispStandard.InternalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop), evaluated.getNext().Generic() != null);
    }
  }

  class LispGenericTypeName extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr evaluated = new LispPtr();
      evaluated.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,evaluated.getNext().Generic() != null,1);
      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,evaluated.getNext().Generic().TypeName()));
    }
  }

  class GenArrayCreate extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr sizearg = new LispPtr();
      sizearg.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());

      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.getNext() != null, 1);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.getNext().String() != null, 1);

      int size = Integer.parseInt(sizearg.getNext().String(),10);

      LispPtr initarg = new LispPtr();
      initarg.setNext(ARGUMENT(aEnvironment, aStackTop, 2).getNext());
 
      ArrayClass array = new ArrayClass(size,initarg.getNext());
      RESULT(aEnvironment, aStackTop).setNext(LispGenericClass.New(array));
    }
  }

  class GenArraySize extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr evaluated = new LispPtr();
      evaluated.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());

      GenericClass gen = evaluated.getNext().Generic();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen != null,1);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen.TypeName().equals("\"Array\""),1);
      int size=((ArrayClass)gen).Size();
      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,""+size));
    }
  }

  class GenArrayGet extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr evaluated = new LispPtr();
      evaluated.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());

      GenericClass gen = evaluated.getNext().Generic();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen != null,1);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen.TypeName().equals("\"Array\""),1);

      LispPtr sizearg = new LispPtr();
      sizearg.setNext(ARGUMENT(aEnvironment, aStackTop, 2).getNext());

      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.getNext() != null, 2);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.getNext().String() != null, 2);

      int size = Integer.parseInt(sizearg.getNext().String(),10);

      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,size>0 && size<=((ArrayClass)gen).Size(),2);
      LispObject object = ((ArrayClass)gen).GetElement(size);

      RESULT(aEnvironment, aStackTop).setNext(object.Copy(false));
    }
  }

  class GenArraySet extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr evaluated = new LispPtr();
      evaluated.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());

      GenericClass gen = evaluated.getNext().Generic();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen != null,1);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen.TypeName().equals("\"Array\""),1);

      LispPtr sizearg = new LispPtr();
      sizearg.setNext(ARGUMENT(aEnvironment, aStackTop, 2).getNext());

      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.getNext() != null, 2);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.getNext().String() != null, 2);

      int size = Integer.parseInt(sizearg.getNext().String(),10);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,size>0 && size<=((ArrayClass)gen).Size(),2);

      LispPtr obj = new LispPtr();
      obj.setNext(ARGUMENT(aEnvironment, aStackTop, 3).getNext());
      ((ArrayClass)gen).SetElement(size,obj.getNext());
      LispStandard.InternalTrue( aEnvironment, RESULT(aEnvironment, aStackTop));
    }
  }

  class LispCustomEval extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      aEnvironment.iCurrentOutput.Write("Function not yet implemented : CustomEval");////TODO fixme
      throw new Piperexception("Function not yet supported");
    }
  }

  class LispCustomEvalExpression extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      aEnvironment.iCurrentOutput.Write("Function not yet implemented : CustomEvalExpression");////TODO fixme
      throw new Piperexception("Function not yet supported");
    }
  }

  class LispCustomEvalResult extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      aEnvironment.iCurrentOutput.Write("Function not yet implemented : CustomEvalResult");////TODO fixme
      throw new Piperexception("Function not yet supported");
    }
  }

  class LispCustomEvalLocals extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispCustomEvalLocals");////TODO fixme
      throw new Piperexception("Function not yet supported");
    }
  }

  class LispCustomEvalStop extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispCustomEvalStop");////TODO fixme
      throw new Piperexception("Function not yet supported");
    }
  }

  class LispTraceRule extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispTraceRule");////TODO fixme
      throw new Piperexception("Function not yet supported");
    }
  }

  class LispTraceStack extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      aEnvironment.iCurrentOutput.Write("Function not yet implemented : TraceStack");////TODO fixme
      throw new Piperexception("Function not yet supported");
    }
  }

  class LispReadLisp extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispParser parser = new LispParser(aEnvironment.iCurrentTokenizer,
                        aEnvironment.iCurrentInput,
                        aEnvironment);
      // Read expression
      parser.Parse(RESULT(aEnvironment, aStackTop));
    }
  }

  class LispReadLispListed extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispParser parser = new LispParser(aEnvironment.iCurrentTokenizer,
                        aEnvironment.iCurrentInput,
                        aEnvironment);
      parser.iListed = true;
      // Read expression
      parser.Parse(RESULT(aEnvironment, aStackTop));
    }
  }

  class LispType extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr evaluated = new LispPtr();
      evaluated.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      LispPtr subList = evaluated.getNext().SubList();
      LispObject head = null;
      if (subList == null)
      {
        RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,"\"\""));
        return;
      }
      head = subList.getNext();
      if (head.String() == null)
      {
        RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,"\"\""));
        return;
      }
      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,aEnvironment.HashTable().LookUpStringify(head.String())));
      return;
    }
  }

  class PiperStringMidGet extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr evaluated = new LispPtr();
      evaluated.setNext(ARGUMENT(aEnvironment, aStackTop, 3).getNext());
      LispError.CHK_ISSTRING_CORE(aEnvironment,aStackTop,evaluated,3);
      String orig = evaluated.getNext().String();
 
      LispPtr index = new LispPtr();
      index.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext() != null, 1);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext().String() != null, 1);
      int from = Integer.parseInt(index.getNext().String(),10);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,from>0,1);
 
      index.setNext(ARGUMENT(aEnvironment, aStackTop, 2).getNext());
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext() != null, 2);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext().String() != null, 2);
      int count = Integer.parseInt(index.getNext().String(),10);

 
      String str = "\""+orig.substring(from,from+count)+"\"";
      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,str));
    }
  }

  class PiperStringMidSet extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr evaluated = new LispPtr();
      evaluated.setNext(ARGUMENT(aEnvironment, aStackTop, 3).getNext());
      LispError.CHK_ISSTRING_CORE(aEnvironment,aStackTop,evaluated,3);
      String orig = evaluated.getNext().String();
      LispPtr index = new LispPtr();
      index.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext() != null, 1);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,index.getNext().String() != null, 1);
      int from = Integer.parseInt(index.getNext().String(),10);

      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,from>0,1);
 
      LispPtr ev2 = new LispPtr();
      ev2.setNext(ARGUMENT(aEnvironment, aStackTop, 2).getNext());
      LispError.CHK_ISSTRING_CORE(aEnvironment,aStackTop,ev2,2);
      String replace = ev2.getNext().String();

      LispError.CHK_CORE(aEnvironment, aStackTop,from+replace.length()-2<orig.length(), LispError.KLispErrInvalidArg);
      String str;
      str = orig.substring(0,from);
      str = str + replace.substring(1,replace.length()-1);
//System.out.println("from="+from+replace.length()-2);
      str = str + orig.substring(from+replace.length()-2,orig.length());
      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,str));
    }
  }

  class GenPatternCreate extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr pattern = new LispPtr();
      pattern.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      LispPtr postpredicate = new LispPtr();
      postpredicate.setNext(ARGUMENT(aEnvironment, aStackTop, 2).getNext());

      LispIterator iter = new LispIterator(pattern);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.GetObject() != null,1);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.GetObject().SubList() != null,1);
      iter.GoSub();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.GetObject() != null,1);
      iter.GoNext();

      LispPtr ptr = iter.Ptr();


      PiperPatternPredicateBase matcher =
          new PiperPatternPredicateBase(aEnvironment, ptr,postpredicate);
      PatternClass p = new PatternClass(matcher);
      RESULT(aEnvironment, aStackTop).setNext(LispGenericClass.New(p));
    }
  }

  class GenPatternMatches extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr pattern = new LispPtr();
      pattern.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      GenericClass gen = pattern.getNext().Generic();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen != null,1);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,gen.TypeName().equals("\"Pattern\""),1);

      LispPtr list = new LispPtr();
      list.setNext(ARGUMENT(aEnvironment, aStackTop, 2).getNext());

      PatternClass patclass = (PatternClass)gen;

      LispIterator iter = new LispIterator(list);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.GetObject() != null,2);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.GetObject().SubList() != null,2);
      iter.GoSub();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,iter.GetObject() != null,2);
      iter.GoNext();

      LispPtr ptr = iter.Ptr();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ptr != null,2);
      boolean matches = patclass.Matches(aEnvironment,ptr);
      LispStandard.InternalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop),matches);
    }
  }

  class LispRuleBaseDefined extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr name = new LispPtr();
      name.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      String orig = name.getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
      String oper = LispStandard.InternalUnstringify(orig);

      LispPtr sizearg = new LispPtr();
      sizearg.setNext(ARGUMENT(aEnvironment, aStackTop, 2).getNext());
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.getNext() != null, 2);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.getNext().String() != null, 2);

      int arity = Integer.parseInt(sizearg.getNext().String(),10);

      LispUserFunction userFunc = aEnvironment.UserFunction(aEnvironment.HashTable().LookUp(oper),arity);
      LispStandard.InternalBoolean(aEnvironment,RESULT(aEnvironment, aStackTop),userFunc != null);
    }
  }

  class LispDefLoadFunction extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr name = new LispPtr();
      name.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      String orig = name.getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
      String oper = LispStandard.InternalUnstringify(orig);

      LispMultiUserFunction multiUserFunc =
          aEnvironment.MultiUserFunction(aEnvironment.HashTable().LookUp(oper));
      if (multiUserFunc != null)
      {
        if (multiUserFunc.iFileToOpen!=null)
        {
          LispDefFile def = multiUserFunc.iFileToOpen;
          if (!def.iIsLoaded)
          {
            multiUserFunc.iFileToOpen=null;
            LispStandard.InternalUse(aEnvironment,def.iFileName);
          }
        }
      }
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispRuleBaseArgList extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr name = new LispPtr();
      name.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      String orig = name.getNext().String();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,orig != null, 1);
      String oper = LispStandard.InternalUnstringify(orig);

      LispPtr sizearg = new LispPtr();
      sizearg.setNext(ARGUMENT(aEnvironment, aStackTop, 2).getNext());
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.getNext() != null, 2);
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,sizearg.getNext().String() != null, 2);

      int arity = Integer.parseInt(sizearg.getNext().String(),10);

      LispUserFunction userFunc = aEnvironment.UserFunction(aEnvironment.HashTable().LookUp(oper),arity);
      LispError.CHK_CORE(aEnvironment, aStackTop,userFunc != null, LispError.KLispErrInvalidArg);

      LispPtr list = userFunc.ArgList();
      LispPtr head = new LispPtr();
      head.setNext(aEnvironment.iList.Copy(false));
      head.getNext().setNext(list.getNext());
      RESULT(aEnvironment, aStackTop).setNext(LispSubList.New(head.getNext()));
    }
  }

  class LispNewRulePattern extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      InternalNewRulePattern(aEnvironment, aStackTop, false);
    }
  }

  class LispMacroNewRulePattern extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      InternalNewRulePattern(aEnvironment, aStackTop, true
      );
    }
  }

  class LispSubst extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr from = new LispPtr(),to = new LispPtr(),body = new LispPtr();
      from.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      to  .setNext(ARGUMENT(aEnvironment, aStackTop, 2).getNext());
      body.setNext(ARGUMENT(aEnvironment, aStackTop, 3).getNext());
      SubstBehaviour behaviour = new SubstBehaviour(aEnvironment,from, to);
      LispStandard.InternalSubstitute(RESULT(aEnvironment, aStackTop), body, behaviour);
    }
  }

  class LispLocalSymbols extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      int nrArguments = LispStandard.InternalListLength(ARGUMENT(aEnvironment, aStackTop, 0));
      int nrSymbols = nrArguments-2;

      String names[] = new String[nrSymbols];
      String localnames[] = new String[nrSymbols];

      int uniquenumber = aEnvironment.GetUniqueId();
      int i;
      for (i=0;i<nrSymbols;i++)
      {
        String atomname = Argument(ARGUMENT(aEnvironment, aStackTop, 0), i+1).getNext().String();
        LispError.CHK_ARG_CORE(aEnvironment,aStackTop,atomname != null, i+1);
        names[i] = atomname;

        String newname = "$"+atomname+uniquenumber;
        String variable = aEnvironment.HashTable().LookUp(newname);
        localnames[i] = variable;
      }
      LocalSymbolBehaviour behaviour = new LocalSymbolBehaviour(aEnvironment,names,localnames,nrSymbols);
      LispPtr result = new LispPtr();
      LispStandard.InternalSubstitute(result, Argument(ARGUMENT(aEnvironment, aStackTop, 0), nrArguments-1), behaviour);
      aEnvironment.iEvaluator.Eval(aEnvironment, RESULT(aEnvironment, aStackTop), result);
    }
  }

  class LispFastIsPrime extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      //TODO fixme this routine should actually be called SlowIsPrime ;-)
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
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
 
      BigNumber z = BigNumber.create(aEnvironment.Precision(),result);
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(z));
    }
  }

  class LispFac extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,ARGUMENT(aEnvironment, aStackTop, 1).getNext().Number(0) != null,1);
      LispPtr arg = ARGUMENT(aEnvironment, aStackTop, 1);
 
      //TODO fixme I am sure this can be optimized still
      int nr = (int)arg.getNext().Number(0).Long();
      LispError.Check(nr>=0,LispError.KLispErrInvalidArg);
      BigNumber fac = BigNumber.create("1",10,10);
      int i;
      for (i=2;i<=nr;i++)
      {
        fac = BigNumber.create(10,i).multiply(fac,10);
      }
      RESULT(aEnvironment, aStackTop).setNext(new LispNumber(fac));
    }
  }

  class LispApplyPure extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr oper = new LispPtr();
      oper.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      LispPtr args = new LispPtr();
      args.setNext(ARGUMENT(aEnvironment, aStackTop, 2).getNext());

      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,args.getNext().SubList() != null,2);
      LispError.CHK_CORE(aEnvironment, aStackTop,args.getNext().SubList().getNext() != null,2);

      // Apply a pure string
      if (oper.getNext().String() != null)
      {
        LispStandard.InternalApplyString(aEnvironment, RESULT(aEnvironment, aStackTop),
                    oper.getNext().String(),
                    args.getNext().SubList().getNext());
      }
      else
      {   // Apply a pure function {args,body}.
    	  LispPtr args2 = new LispPtr();
        args2.setNext(args.getNext().SubList().getNext().getNext());
        LispError.CHK_ARG_CORE(aEnvironment,aStackTop,oper.getNext().SubList() != null,1);
        LispError.CHK_ARG_CORE(aEnvironment,aStackTop,oper.getNext().SubList().getNext() != null,1);
        LispStandard.InternalApplyPure(oper,args2,RESULT(aEnvironment, aStackTop),aEnvironment);
      }
    }
  }


  class PiperPrettyReaderSet extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      int nrArguments = LispStandard.InternalListLength(ARGUMENT(aEnvironment, aStackTop, 0));
      if (nrArguments == 1)
      {
        aEnvironment.iPrettyReader = null;
      }
      else
      {
        LispError.CHK_CORE(aEnvironment, aStackTop,nrArguments == 2,LispError.KLispErrWrongNumberOfArgs);
        LispPtr oper = new LispPtr();
        oper.setNext(ARGUMENT(aEnvironment, aStackTop, 0).getNext());
        oper = oper.getNext();
        LispError.CHK_ISSTRING_CORE(aEnvironment,aStackTop,oper,1);
        aEnvironment.iPrettyReader = oper.getNext().String();
      }
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class PiperPrettyReaderGet extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      if (aEnvironment.iPrettyReader == null)
        RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,"\"\""));
      else
        RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,aEnvironment.iPrettyReader));
    }
  }

  class PiperPrettyPrinterSet extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      int nrArguments = LispStandard.InternalListLength(ARGUMENT(aEnvironment, aStackTop, 0));
      if (nrArguments == 1)
      {
        aEnvironment.iPrettyPrinter = null;
      }
      else
      {
        LispError.CHK_CORE(aEnvironment, aStackTop,nrArguments == 2,LispError.KLispErrWrongNumberOfArgs);
        LispPtr oper = new LispPtr();
        oper.setNext(ARGUMENT(aEnvironment, aStackTop, 0).getNext());
        oper = oper.getNext();
        LispError.CHK_ISSTRING_CORE(aEnvironment,aStackTop,oper,1);
        aEnvironment.iPrettyPrinter = oper.getNext().String();
      }
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class PiperPrettyPrinterGet extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      if (aEnvironment.iPrettyPrinter == null)
        RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,"\"\""));
      else
        RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,aEnvironment.iPrettyPrinter));
    }
  }

  class LispGarbageCollect extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      aEnvironment.HashTable().GarbageCollect();
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispPatchLoad extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      aEnvironment.iCurrentOutput.Write("Function not yet implemented : PatchLoad");//TODO FIXME
      throw new Piperexception("Function not yet supported");
    }
  }

  class LispPatchString extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      aEnvironment.iCurrentOutput.Write("Function not yet implemented : PatchString");//TODO FIXME
      throw new Piperexception("Function not yet supported");
    }
  }

  class PiperExtraInfoSet extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr object = new LispPtr();
      object.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());

      LispPtr info = new LispPtr();
      info.setNext(ARGUMENT(aEnvironment, aStackTop, 2).getNext());

      RESULT(aEnvironment, aStackTop).setNext( object.getNext().SetExtraInfo(info) );
    }
  }

  class PiperExtraInfoGet extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr object = new LispPtr();
      object.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());

      LispPtr result = object.getNext().ExtraInfo();
      if (result == null)
      {
        LispStandard.InternalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
      }
      else if (result.getNext() == null)
      {
        LispStandard.InternalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
      }
      else
      {
        RESULT(aEnvironment, aStackTop).setNext(result.getNext());
      }
    }
  }

  class LispDefaultTokenizer extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      aEnvironment.iCurrentTokenizer = aEnvironment.iDefaultTokenizer;
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispCommonLispTokenizer extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispCommonLispTokenizer");//TODO FIXME
      throw new Piperexception("Function not yet supported");
    }
  }

  class LispXmlTokenizer extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      aEnvironment.iCurrentTokenizer = aEnvironment.iXmlTokenizer;
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispExplodeTag extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr out = new LispPtr();
      out.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
      LispError.CHK_ISSTRING_CORE(aEnvironment,aStackTop,out,1);

      String str = out.getNext().String();
      int strInd = 0;
      strInd++;
      if (str.charAt(strInd) != '<')
      {
        RESULT(aEnvironment, aStackTop).setNext(out.getNext());
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
      while (LispTokenizer.IsAlpha(str.charAt(strInd)))
      {
        char c = str.charAt(strInd);
        strInd++;
        if (c >= 'a' && c <= 'z')
            c = (char)(c + ('A'-'a'));
        tag = tag + c;
      }
      tag = tag + "\"";

      LispObject info = null;

      while (str.charAt(strInd) == ' ') strInd++;
      while (str.charAt(strInd) != '>' && str.charAt(strInd) != '/')
      {
        String name = new String();
        name = name + "\"";

        while (LispTokenizer.IsAlpha(str.charAt(strInd)))
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
          LispObject ls = LispAtom.New(aEnvironment,"List");
          LispObject nm = LispAtom.New(aEnvironment,name);
          LispObject vl = LispAtom.New(aEnvironment,value);
          nm.setNext(vl);
          ls.setNext(nm);
          LispObject newinfo =  LispSubList.New(ls);
          newinfo.setNext(info);
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
      LispObject ls = LispAtom.New(aEnvironment,"List");
      ls.setNext(info);
      info = LispSubList.New(ls);
    }

    LispObject xm = LispAtom.New(aEnvironment,"XmlTag");
    LispObject tg = LispAtom.New(aEnvironment,tag);
    LispObject tp = LispAtom.New(aEnvironment,type);
    info.setNext(tp);
    tg.setNext(info);
    xm.setNext(tg);
    RESULT(aEnvironment, aStackTop).setNext(LispSubList.New(xm));

    }
  }

  class PiperBuiltinAssoc extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      // key to find
    	LispPtr key = new LispPtr();
      key.setNext(ARGUMENT(aEnvironment, aStackTop, 1).getNext());
 
      // assoc-list to find it in
      LispPtr list = new LispPtr();
      list.setNext(ARGUMENT(aEnvironment, aStackTop, 2).getNext());

      LispObject t;

      //Check that it is a compound object
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,list.getNext().SubList() != null, 2);
      t = list.getNext().SubList().getNext();
      LispError.CHK_ARG_CORE(aEnvironment,aStackTop,t != null, 2);
      t = t.getNext();

      while (t != null)
      {
        if (t.SubList() != null)
        {
          LispObject sub = t.SubList().getNext();
          if (sub != null)
          {
            sub = sub.getNext();
            LispPtr temp = new LispPtr();
            temp.setNext(sub);
            if(LispStandard.InternalEquals(aEnvironment,key,temp))
            {
              RESULT(aEnvironment, aStackTop).setNext(t);
              return;
            }
          }
        }
        t = t.getNext();
      }
      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,"Empty"));
    }
  }

  class LispCurrentFile extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,aEnvironment.HashTable().LookUpStringify(aEnvironment.iInputStatus.FileName())));
    }
  }

  class LispCurrentLine extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      RESULT(aEnvironment, aStackTop).setNext(LispAtom.New(aEnvironment,""+aEnvironment.iInputStatus.LineNumber()));
    }
  }

  class LispBackQuote extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BackQuoteBehaviour behaviour = new BackQuoteBehaviour(aEnvironment);
      LispPtr result = new LispPtr();
      LispStandard.InternalSubstitute(result, ARGUMENT(aEnvironment, aStackTop,  1), behaviour);
      aEnvironment.iEvaluator.Eval(aEnvironment, RESULT(aEnvironment, aStackTop), result);
    }
  }

  class LispDumpBigNumberDebugInfo extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      BigNumber x = GetNumber(aEnvironment, aStackTop, 1);
      aEnvironment.iCurrentOutput.Write(x.debug()+"\n");
      LispStandard.InternalTrue(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispInDebugMode extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispStandard.InternalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispDebugFile extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      throw new Exception("Cannot call DebugFile in non-debug version of Piper");
    }
  }

  class LispDebugLine extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      throw new Exception("Cannot call DebugLine in non-debug version of Piper");
    }
  }

  class LispVersion extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      RESULT(aEnvironment,aStackTop).setNext(LispAtom.New(aEnvironment,"\""+CVersion.version+"\""));
    }
  }


  class LispExit extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      Runtime.getRuntime().exit(0);
    }
  }

  class LispExitRequested extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      LispStandard.InternalFalse(aEnvironment,RESULT(aEnvironment, aStackTop));
    }
  }

  class LispHistorySize extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispHistorySize");//TODO FIXME
      throw new Piperexception("Function not yet supported");
    }
  }

  class LispStackSize extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispStackSize");//TODO FIXME
      throw new Piperexception("Function not yet supported");
    }
  }

  class LispIsPromptShown extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispIsPromptShown");//TODO FIXME
      throw new Piperexception("Function not yet supported");
    }
  }

  class LispReadCmdLineString extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      aEnvironment.iCurrentOutput.Write("Function not yet implemented : LispReadCmdLineString");//TODO FIXME
      throw new Piperexception("Function not yet supported");
    }
  }

  class LispTime extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
      long starttime = System.currentTimeMillis();
      LispPtr res = new LispPtr();
      aEnvironment.iEvaluator.Eval(aEnvironment, res, ARGUMENT(aEnvironment,aStackTop,1));
      long endtime = System.currentTimeMillis();
      double timeDiff;
      timeDiff = endtime-starttime;
      timeDiff /= 1000.0;
      RESULT(aEnvironment,aStackTop).setNext(LispAtom.New(aEnvironment,""+timeDiff));
    }
  }

  class LispFileSize extends PiperEvalCaller
  {
    public void Eval(LispEnvironment aEnvironment,int aStackTop) throws Exception
    {
    	LispPtr fnameObject = new LispPtr();
      fnameObject.setNext(ARGUMENT(aEnvironment,aStackTop,1).getNext());
      LispError.CHK_ISSTRING_CORE(aEnvironment,aStackTop,fnameObject,1);
      String fname = LispStandard.InternalUnstringify(fnameObject.getNext().String());
      String hashedname = aEnvironment.HashTable().LookUp(fname);

      long fileSize = 0;
      InputStatus oldstatus = new InputStatus(aEnvironment.iInputStatus);
      aEnvironment.iInputStatus.SetTo(hashedname);
      try
      {
        // Open file
        LispInput newInput = // new StdFileInput(hashedname, aEnvironment.iInputStatus);
            LispStandard.OpenInputFile(aEnvironment, aEnvironment.iInputDirectories, hashedname, aEnvironment.iInputStatus);
 
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
      RESULT(aEnvironment,aStackTop).setNext(LispAtom.New(aEnvironment,""+fileSize));
    }
  }


}

