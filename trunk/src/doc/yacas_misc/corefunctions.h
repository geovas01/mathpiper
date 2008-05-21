    1 //
    2 // declare the core functions that have special syntax
    3 //
    4 
    5 OPERATOR(bodied,KMaxPrecedence,While)
    6 OPERATOR(bodied,KMaxPrecedence,Rule)
    7 OPERATOR(bodied,KMaxPrecedence,MacroRule)
    8 OPERATOR(bodied,KMaxPrecedence,RulePattern)
    9 OPERATOR(bodied,KMaxPrecedence,MacroRulePattern)
   10 OPERATOR(bodied,KMaxPrecedence,FromFile)
   11 OPERATOR(bodied,KMaxPrecedence,FromString)
   12 OPERATOR(bodied,KMaxPrecedence,ToFile)
   13 OPERATOR(bodied,KMaxPrecedence,ToString)
   14 OPERATOR(bodied,KMaxPrecedence,ToStdout)
   15 OPERATOR(bodied,KMaxPrecedence,TraceRule)
   16 OPERATOR(bodied,KMaxPrecedence,Subst)
   17 OPERATOR(bodied,KMaxPrecedence,LocalSymbols)
   18 OPERATOR(bodied,KMaxPrecedence,BackQuote)
   19 OPERATOR(prefix,0,`)
   20 OPERATOR(prefix,0,@)
   21 OPERATOR(prefix,0,_)
   22 OPERATOR(infix,0,_)
   23 
   24 
   25 //
   26 // Evaluation direction.
   27 //
   28 CORE_KERNEL_FUNCTION("Hold",LispQuote,1,YacasEvaluator::Macro | YacasEvaluator::Fixed)
   29 CORE_KERNEL_FUNCTION("Eval",LispEval,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
   30 
   31 //
   32 // Input/output functions
   33 //
   34 CORE_KERNEL_FUNCTION("Write",LispWrite,1,YacasEvaluator::Function | YacasEvaluator::Variable)
   35 CORE_KERNEL_FUNCTION("WriteString",LispWriteString,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
   36 CORE_KERNEL_FUNCTION("FullForm",LispFullForm,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
   37 CORE_KERNEL_FUNCTION("DefaultDirectory",LispDefaultDirectory,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
   38 CORE_KERNEL_FUNCTION("FromFile",LispFromFile,2,YacasEvaluator::Macro | YacasEvaluator::Fixed)
   39 CORE_KERNEL_FUNCTION("FromString",LispFromString,2,YacasEvaluator::Macro | YacasEvaluator::Fixed)
   40 CORE_KERNEL_FUNCTION("Read",LispRead,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
   41 CORE_KERNEL_FUNCTION("ReadToken",LispReadToken,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
   42 CORE_KERNEL_FUNCTION("ToFile",LispToFile,2,YacasEvaluator::Macro | YacasEvaluator::Fixed)
   43 CORE_KERNEL_FUNCTION("ToString",LispToString,1,YacasEvaluator::Macro | YacasEvaluator::Fixed)
   44 CORE_KERNEL_FUNCTION("ToStdout",LispToStdout,1,YacasEvaluator::Macro | YacasEvaluator::Fixed)
   45 CORE_KERNEL_FUNCTION("Load",LispLoad,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
   46 
   47 //
   48 // Variable setting/clearing
   49 //
   50 CORE_KERNEL_FUNCTION("Set",LispSetVar,2,YacasEvaluator::Macro | YacasEvaluator::Fixed)
   51 CORE_KERNEL_FUNCTION("MacroSet",LispMacroSetVar,2,YacasEvaluator::Macro | YacasEvaluator::Fixed)
   52 // MacroClear is the same as Clear, but with its arguments evaluated first
   53 CORE_KERNEL_FUNCTION("Clear",LispClearVar,1,YacasEvaluator::Macro | YacasEvaluator::Variable)
   54 CORE_KERNEL_FUNCTION("MacroClear",LispClearVar,1,YacasEvaluator::Function | YacasEvaluator::Variable)
   55 
   56 CORE_KERNEL_FUNCTION("Local",LispNewLocal,1,YacasEvaluator::Macro | YacasEvaluator::Variable)
   57 CORE_KERNEL_FUNCTION("MacroLocal",LispNewLocal,1,YacasEvaluator::Function | YacasEvaluator::Variable)
   58 
   59 //
   60 // List and compound object manipulation
   61 //
   62 CORE_KERNEL_FUNCTION("Head",LispHead,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
   63 CORE_KERNEL_FUNCTION("MathNth",LispNth,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
   64 CORE_KERNEL_FUNCTION("Tail",LispTail,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
   65 CORE_KERNEL_FUNCTION("DestructiveReverse",LispDestructiveReverse,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
   66 CORE_KERNEL_FUNCTION("Length",LispLength,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
   67 CORE_KERNEL_FUNCTION("List",LispList,1,YacasEvaluator::Macro | YacasEvaluator::Variable)
   68 CORE_KERNEL_FUNCTION("UnList",LispUnList,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
   69 CORE_KERNEL_FUNCTION("Listify",LispListify,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
   70 CORE_KERNEL_FUNCTION("Concat",LispConcatenate,1,YacasEvaluator::Function | YacasEvaluator::Variable)
   71 CORE_KERNEL_FUNCTION("ConcatStrings",LispConcatenateStrings,1,YacasEvaluator::Function | YacasEvaluator::Variable)
   72 
   73 CORE_KERNEL_FUNCTION("Delete",LispDelete,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
   74 CORE_KERNEL_FUNCTION("DestructiveDelete",LispDestructiveDelete,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
   75 CORE_KERNEL_FUNCTION("Insert",LispInsert,3,YacasEvaluator::Function | YacasEvaluator::Fixed)
   76 CORE_KERNEL_FUNCTION("DestructiveInsert",LispDestructiveInsert,3,YacasEvaluator::Function | YacasEvaluator::Fixed)
   77 CORE_KERNEL_FUNCTION("Replace",LispReplace,3,YacasEvaluator::Function | YacasEvaluator::Fixed)
   78 CORE_KERNEL_FUNCTION("DestructiveReplace",LispDestructiveReplace,3,YacasEvaluator::Function | YacasEvaluator::Fixed)
   79 CORE_KERNEL_FUNCTION("Atom",LispAtomize,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
   80 CORE_KERNEL_FUNCTION("String",LispStringify,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
   81 CORE_KERNEL_FUNCTION("CharString",LispCharString,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
   82 CORE_KERNEL_FUNCTION("FlatCopy",LispFlatCopy,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
   83 
   84 //???CORE_KERNEL_FUNCTION("",LispNoCacheConcatenateStrings)
   85 
   86 //
   87 // Program control flow
   88 //
   89 CORE_KERNEL_FUNCTION("Prog",LispProgBody,1,YacasEvaluator::Macro | YacasEvaluator::Variable)
   90 CORE_KERNEL_FUNCTION("While",LispWhile,2,YacasEvaluator::Macro | YacasEvaluator::Fixed)
   91 CORE_KERNEL_FUNCTION("If",LispIf,2,YacasEvaluator::Macro | YacasEvaluator::Variable)
   92 //
   93 // Error handling
   94 //
   95 CORE_KERNEL_FUNCTION("Check",LispCheck,2,YacasEvaluator::Macro | YacasEvaluator::Fixed)
   96 CORE_KERNEL_FUNCTION("TrapError",LispTrapError,2,YacasEvaluator::Macro | YacasEvaluator::Fixed)
   97 CORE_KERNEL_FUNCTION("GetCoreError",LispGetCoreError,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
   98 
   99 //
  100 // User function definition
  101 //
  102 CORE_KERNEL_FUNCTION("Prefix",LispPreFix,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  103 CORE_KERNEL_FUNCTION("Infix",LispInFix,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  104 CORE_KERNEL_FUNCTION("Postfix",LispPostFix,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  105 CORE_KERNEL_FUNCTION("Bodied",LispBodied,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  106 CORE_KERNEL_FUNCTION("RuleBase",LispRuleBase,2,YacasEvaluator::Macro | YacasEvaluator::Fixed)
  107 CORE_KERNEL_FUNCTION("MacroRuleBase",LispMacroRuleBase,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  108 CORE_KERNEL_FUNCTION("RuleBaseListed",LispRuleBaseListed,2,YacasEvaluator::Macro | YacasEvaluator::Fixed)
  109 CORE_KERNEL_FUNCTION("MacroRuleBaseListed",LispMacroRuleBaseListed,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  110 CORE_KERNEL_FUNCTION("DefMacroRuleBase",LispDefMacroRuleBase,2,YacasEvaluator::Macro | YacasEvaluator::Fixed)
  111 CORE_KERNEL_FUNCTION("DefMacroRuleBaseListed",LispDefMacroRuleBaseListed,2,YacasEvaluator::Macro | YacasEvaluator::Fixed)
  112 CORE_KERNEL_FUNCTION("HoldArg",LispHoldArg,2,YacasEvaluator::Macro | YacasEvaluator::Fixed)
  113 CORE_KERNEL_FUNCTION("Rule",LispNewRule,5,YacasEvaluator::Macro | YacasEvaluator::Fixed)
  114 CORE_KERNEL_FUNCTION("MacroRule",LispMacroNewRule,5,YacasEvaluator::Function | YacasEvaluator::Fixed)
  115 CORE_KERNEL_FUNCTION("UnFence",LispUnFence,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  116 CORE_KERNEL_FUNCTION("Retract",LispRetract,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  117 //
  118 // Predicates
  119 //
  120 CORE_KERNEL_FUNCTION("MathNot",LispNot,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  121 CORE_KERNEL_FUNCTION_ALIAS("Not",LispNot,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  122 CORE_KERNEL_FUNCTION("MathAnd",LispLazyAnd,1,YacasEvaluator::Macro | YacasEvaluator::Variable)
  123 CORE_KERNEL_FUNCTION_ALIAS("And",LispLazyAnd,1,YacasEvaluator::Macro | YacasEvaluator::Variable)
  124 CORE_KERNEL_FUNCTION("MathOr",LispLazyOr,1,YacasEvaluator::Macro | YacasEvaluator::Variable)
  125 CORE_KERNEL_FUNCTION_ALIAS("Or",LispLazyOr,1,YacasEvaluator::Macro | YacasEvaluator::Variable)
  126 CORE_KERNEL_FUNCTION("Equals",LispEquals,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  127 CORE_KERNEL_FUNCTION_ALIAS("=",LispEquals,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  128 CORE_KERNEL_FUNCTION("LessThan",LispLessThan,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  129 CORE_KERNEL_FUNCTION("GreaterThan",LispGreaterThan,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  130 CORE_KERNEL_FUNCTION("IsFunction",LispIsFunction,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  131 CORE_KERNEL_FUNCTION("IsAtom",LispIsAtom,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  132 CORE_KERNEL_FUNCTION("IsNumber",LispIsNumber,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  133 CORE_KERNEL_FUNCTION("IsInteger",LispIsInteger,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  134 CORE_KERNEL_FUNCTION("IsList",LispIsList,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  135 CORE_KERNEL_FUNCTION("IsString",LispIsString,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  136 CORE_KERNEL_FUNCTION("IsBound",LispIsBound,1,YacasEvaluator::Macro | YacasEvaluator::Fixed)
  137 //
  138 // Math functions (REQUIRING number inputs);.
  139 //
  140 CORE_KERNEL_FUNCTION("MathMultiply",LispMultiply,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  141 CORE_KERNEL_FUNCTION("MathAdd",LispAdd,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  142 CORE_KERNEL_FUNCTION("MathSubtract",LispSubtract,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  143 CORE_KERNEL_FUNCTION("MathDivide",LispDivide,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  144 CORE_KERNEL_FUNCTION("Builtin'Precision'Set",YacasBuiltinPrecisionSet,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  145 CORE_KERNEL_FUNCTION("MathGetExactBits",LispGetExactBits,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  146 CORE_KERNEL_FUNCTION("MathSetExactBits",LispSetExactBits,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  147 CORE_KERNEL_FUNCTION("MathBitCount",LispBitCount,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  148 CORE_KERNEL_FUNCTION("MathSign",LispMathSign,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  149 CORE_KERNEL_FUNCTION("MathIsSmall",LispMathIsSmall,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  150 CORE_KERNEL_FUNCTION("MathNegate",LispMathNegate,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  151 CORE_KERNEL_FUNCTION("MathFloor",LispFloor,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  152 CORE_KERNEL_FUNCTION("MathCeil",LispCeil,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  153 CORE_KERNEL_FUNCTION("MathAbs",LispAbs,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  154 CORE_KERNEL_FUNCTION("MathMod",LispMod,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  155 CORE_KERNEL_FUNCTION("MathDiv",LispDiv,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  156 CORE_KERNEL_FUNCTION("BitsToDigits",LispBitsToDigits,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  157 CORE_KERNEL_FUNCTION("DigitsToBits",LispDigitsToBits,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  158 CORE_KERNEL_FUNCTION("MathGcd",LispGcd,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  159 CORE_KERNEL_FUNCTION("SystemCall",LispSystemCall,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  160 CORE_KERNEL_FUNCTION("FastArcSin",LispFastArcSin,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  161 CORE_KERNEL_FUNCTION("FastLog",LispFastLog,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  162 CORE_KERNEL_FUNCTION("FastPower",LispFastPower,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  163 CORE_KERNEL_FUNCTION("ShiftLeft",LispShiftLeft,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  164 CORE_KERNEL_FUNCTION("ShiftRight",LispShiftRight,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  165 CORE_KERNEL_FUNCTION("FromBase",LispFromBase,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  166 CORE_KERNEL_FUNCTION("ToBase",LispToBase,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  167 CORE_KERNEL_FUNCTION("MaxEvalDepth",LispMaxEvalDepth,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  168 CORE_KERNEL_FUNCTION("DefLoad",LispDefLoad,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  169 CORE_KERNEL_FUNCTION("Use",LispUse,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  170 CORE_KERNEL_FUNCTION("RightAssociative",LispRightAssociative,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  171 CORE_KERNEL_FUNCTION("LeftPrecedence",LispLeftPrecedence,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  172 CORE_KERNEL_FUNCTION("RightPrecedence",LispRightPrecedence,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  173 CORE_KERNEL_FUNCTION("IsBodied",LispIsBodied,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  174 CORE_KERNEL_FUNCTION("IsInfix",LispIsInFix,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  175 CORE_KERNEL_FUNCTION("IsPrefix",LispIsPreFix,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  176 CORE_KERNEL_FUNCTION("IsPostfix",LispIsPostFix,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  177 CORE_KERNEL_FUNCTION("OpPrecedence",LispGetPrecedence,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  178 CORE_KERNEL_FUNCTION("OpLeftPrecedence",LispGetLeftPrecedence,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  179 CORE_KERNEL_FUNCTION("OpRightPrecedence",LispGetRightPrecedence,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  180 CORE_KERNEL_FUNCTION("Builtin'Precision'Get",YacasBuiltinPrecisionGet,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
  181 CORE_KERNEL_FUNCTION("BitAnd",LispBitAnd,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  182 CORE_KERNEL_FUNCTION("BitOr",LispBitOr,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  183 CORE_KERNEL_FUNCTION("BitXor",LispBitXor,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  184 CORE_KERNEL_FUNCTION("Secure",LispSecure,1,YacasEvaluator::Macro | YacasEvaluator::Fixed)
  185 CORE_KERNEL_FUNCTION("FindFile",LispFindFile,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  186 CORE_KERNEL_FUNCTION("FindFunction",LispFindFunction,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  187 // Generic object support
  188 CORE_KERNEL_FUNCTION("IsGeneric",LispIsGeneric,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  189 CORE_KERNEL_FUNCTION("GenericTypeName",LispGenericTypeName,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  190 CORE_KERNEL_FUNCTION("Array'Create",GenArrayCreate,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  191 CORE_KERNEL_FUNCTION("Array'Size",GenArraySize,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  192 CORE_KERNEL_FUNCTION("Array'Get",GenArrayGet,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  193 CORE_KERNEL_FUNCTION("Array'Set",GenArraySet,3,YacasEvaluator::Function | YacasEvaluator::Fixed)
  194 CORE_KERNEL_FUNCTION("CustomEval",LispCustomEval,4,YacasEvaluator::Macro | YacasEvaluator::Fixed)
  195 CORE_KERNEL_FUNCTION("CustomEval'Expression",LispCustomEvalExpression,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
  196 CORE_KERNEL_FUNCTION("CustomEval'Result",LispCustomEvalResult,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
  197 CORE_KERNEL_FUNCTION("CustomEval'Locals",LispCustomEvalLocals,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
  198 CORE_KERNEL_FUNCTION("CustomEval'Stop",LispCustomEvalStop,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
  199 
  200 CORE_KERNEL_FUNCTION("TraceRule",LispTraceRule,2,YacasEvaluator::Macro | YacasEvaluator::Fixed)
  201 CORE_KERNEL_FUNCTION("TraceStack",LispTraceStack,1,YacasEvaluator::Macro | YacasEvaluator::Fixed)
  202 CORE_KERNEL_FUNCTION("LispRead",LispReadLisp,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
  203 CORE_KERNEL_FUNCTION("LispReadListed",LispReadLispListed,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
  204 CORE_KERNEL_FUNCTION("Type",LispType,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  205 CORE_KERNEL_FUNCTION("StringMid'Get",YacasStringMidGet,3,YacasEvaluator::Function | YacasEvaluator::Fixed)
  206 CORE_KERNEL_FUNCTION("StringMid'Set",YacasStringMidSet,3,YacasEvaluator::Function | YacasEvaluator::Fixed)
  207 // Pattern matching
  208 CORE_KERNEL_FUNCTION("Pattern'Create",GenPatternCreate,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  209 CORE_KERNEL_FUNCTION("Pattern'Matches",GenPatternMatches,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  210 CORE_KERNEL_FUNCTION("RuleBaseDefined",LispRuleBaseDefined,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  211 CORE_KERNEL_FUNCTION("DefLoadFunction",LispDefLoadFunction,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  212 CORE_KERNEL_FUNCTION("RuleBaseArgList",LispRuleBaseArgList,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  213 CORE_KERNEL_FUNCTION("RulePattern",LispNewRulePattern,5,YacasEvaluator::Macro | YacasEvaluator::Fixed)
  214 CORE_KERNEL_FUNCTION("MacroRulePattern",LispMacroNewRulePattern,5,YacasEvaluator::Function | YacasEvaluator::Fixed)
  215 CORE_KERNEL_FUNCTION("Subst",LispSubst,3,YacasEvaluator::Function | YacasEvaluator::Fixed)
  216 CORE_KERNEL_FUNCTION("LocalSymbols",LispLocalSymbols,1,YacasEvaluator::Macro | YacasEvaluator::Variable)
  217 CORE_KERNEL_FUNCTION("FastIsPrime",LispFastIsPrime,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  218 CORE_KERNEL_FUNCTION("MathFac",LispFac,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  219 CORE_KERNEL_FUNCTION("ApplyPure",LispApplyPure,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  220 CORE_KERNEL_FUNCTION("PrettyReader'Set",YacasPrettyReaderSet,1,YacasEvaluator::Function | YacasEvaluator::Variable)
  221 CORE_KERNEL_FUNCTION("PrettyReader'Get",YacasPrettyReaderGet,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
  222 CORE_KERNEL_FUNCTION("PrettyPrinter'Set",YacasPrettyPrinterSet,1,YacasEvaluator::Function | YacasEvaluator::Variable)
  223 CORE_KERNEL_FUNCTION("PrettyPrinter'Get",YacasPrettyPrinterGet,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
  224 CORE_KERNEL_FUNCTION("GarbageCollect",LispGarbageCollect,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
  225 CORE_KERNEL_FUNCTION("SetGlobalLazyVariable",LispSetGlobalLazyVariable,2,YacasEvaluator::Macro | YacasEvaluator::Fixed)
  226 CORE_KERNEL_FUNCTION("PatchLoad",LispPatchLoad,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  227 CORE_KERNEL_FUNCTION("PatchString",LispPatchString,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  228 CORE_KERNEL_FUNCTION("ExtraInfo'Set",YacasExtraInfoSet,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  229 CORE_KERNEL_FUNCTION("ExtraInfo'Get",YacasExtraInfoGet,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  230 CORE_KERNEL_FUNCTION("DefaultTokenizer",LispDefaultTokenizer,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
  231 CORE_KERNEL_FUNCTION("CommonLispTokenizer",LispCommonLispTokenizer,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
  232 CORE_KERNEL_FUNCTION("XmlTokenizer",LispXmlTokenizer,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
  233 CORE_KERNEL_FUNCTION("XmlExplodeTag",LispExplodeTag,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  234 CORE_KERNEL_FUNCTION("Builtin'Assoc",YacasBuiltinAssoc,2,YacasEvaluator::Function | YacasEvaluator::Fixed)
  235 CORE_KERNEL_FUNCTION("CurrentFile",LispCurrentFile,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
  236 CORE_KERNEL_FUNCTION("CurrentLine",LispCurrentLine,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
  237 CORE_KERNEL_FUNCTION("`",LispBackQuote,1,YacasEvaluator::Macro | YacasEvaluator::Fixed)
  238 
  239 //
  240 // Debugging functions
  241 //
  242 CORE_KERNEL_FUNCTION("MathDebugInfo",LispDumpBigNumberDebugInfo,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  243 CORE_KERNEL_FUNCTION("InDebugMode",LispInDebugMode,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
  244 CORE_KERNEL_FUNCTION("DebugFile",LispDebugFile,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  245 CORE_KERNEL_FUNCTION("DebugLine",LispDebugLine,1,YacasEvaluator::Function | YacasEvaluator::Fixed)
  246 
  247 //
  248 // Information functions
  249 //
  250 CORE_KERNEL_FUNCTION("Version",LispVersion,0,YacasEvaluator::Function | YacasEvaluator::Fixed)
  251 

