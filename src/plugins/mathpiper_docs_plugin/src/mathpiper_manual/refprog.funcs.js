function findFileForFunctionInrefprog(seach) {
var funcs_refprogchapter1 = new Array(
);
var funcs_refprogchapter2 = new Array(
"/*"
, "*/"
, "//"
, "Prog"
, "["
, "]"
, "Bodied"
, "Infix"
, "Postfix"
, "Prefix"
, "IsBodied"
, "IsInfix"
, "IsPostfix"
, "IsPrefix"
, "OpPrecedence"
, "OpLeftPrecedence"
, "OpRightPrecedence"
, "RightAssociative"
, "LeftPrecedence"
, "RightPrecedence"
, "RuleBase"
, "RuleBaseListed"
, "Rule"
, "HoldArg"
, "Retract"
, "UnFence"
, "HoldArgNr"
, "RuleBaseArgList"
, "MacroSet"
, "MacroClear"
, "MacroLocal"
, "MacroRuleBase"
, "MacroRuleBaseListed"
, "MacroRule"
, "Backquoting"
, "DefMacroRuleBase"
, "DefMacroRuleBaseListed"
, "ExtraInfoSet, ExtraInfo'Get"
, "GarbageCollect"
, "FindFunction"
, "Secure"
);
var funcs_refprogchapter3 = new Array(
"MultiplyNum"
, "CachedConstant"
, "NewtonNum"
, "SumTaylorNum"
, "IntPowerNum"
, "BinSplitNum"
, "BinSplitData"
, "BinSplitFinal"
, "SetExactBitsN"
, "MathGetExactBits"
, "InNumericMode"
, "NonN"
, "IntLog"
, "IntNthRoot"
, "NthRoot"
, "ContFracList"
, "ContFracEval"
, "GuessRational"
, "NearRational"
, "BracketRational"
, "TruncRadian"
, "BuiltinPrecisionSet"
, "BuiltinPrecisionGet"
);
var funcs_refprogchapter4 = new Array(
"Check"
, "TrapError"
, "GetCoreError"
, "Assert"
, "DumpErrors"
, "ClearErrors"
, "IsError"
, "GetError"
, "ClearError"
, "GetErrorTableau"
, "CurrentFile"
, "CurrentLine"
);
var funcs_refprogchapter5 = new Array(
"NotN"
, "AndN"
, "OrN"
, "BitAnd"
, "BitOr"
, "BitXor"
, "Equals"
, "GreaterThan"
, "LessThan"
, "Math..."
, "Fast..."
, "ShiftLeft"
, "ShiftRight"
, "IsPromptShown"
, "GetTime"
);
var funcs_refprogchapter6 = new Array(
"IsGeneric"
, "GenericTypeName"
, "ArrayCreate"
, "ArraySize"
, "ArrayGet"
, "ArraySet"
, "ArrayCreateFromList"
, "ArrayToList"
);
var funcs_refprogchapter7 = new Array(
"Verify"
, "TestMathPiper"
, "LogicVerify"
, "LogicTest"
, "KnownFailure"
, "RoundTo"
, "VerifyArithmetic"
, "RandVerifyArithmetic"
, "VerifyDiv"
);
var funcs_refprogchapter8 = new Array(
);
var funcs_refprogchapter9 = new Array(
);
var funcs_refprogchapter10 = new Array(
);
  if (containsFunc(funcs_refprogchapter1,seach))
    return "refprogchapter1.html";
  if (containsFunc(funcs_refprogchapter2,seach))
    return "refprogchapter2.html";
  if (containsFunc(funcs_refprogchapter3,seach))
    return "refprogchapter3.html";
  if (containsFunc(funcs_refprogchapter4,seach))
    return "refprogchapter4.html";
  if (containsFunc(funcs_refprogchapter5,seach))
    return "refprogchapter5.html";
  if (containsFunc(funcs_refprogchapter6,seach))
    return "refprogchapter6.html";
  if (containsFunc(funcs_refprogchapter7,seach))
    return "refprogchapter7.html";
  if (containsFunc(funcs_refprogchapter8,seach))
    return "refprogchapter8.html";
  if (containsFunc(funcs_refprogchapter9,seach))
    return "refprogchapter9.html";
  if (containsFunc(funcs_refprogchapter10,seach))
    return "refprogchapter10.html";
  return "";
}
