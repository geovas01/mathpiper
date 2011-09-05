function findFileForFunctionInref(seach) {
var funcs_refchapter1 = new Array(
);
var funcs_refchapter2 = new Array(
"+"
, "-"
, "*"
, "/"
, "^"
, "Div"
, "Mod"
, "Gcd"
, "Lcm"
, "<<"
, ">>"
, "FromBase"
, "ToBase"
, "N"
, "Rationalize"
, "ContFrac"
, "Decimal"
, "Floor"
, "Ceil"
, "Round"
, "Min"
, "Max"
, "Numer"
, "Denom"
, "Pslq"
);
var funcs_refchapter3 = new Array(
"<"
, ">"
, "<="
, ">="
, "IsZero"
, "IsRational"
);
var funcs_refchapter4 = new Array(
"Sin"
, "Cos"
, "Tan"
, "ArcSin"
, "ArcCos"
, "ArcTan"
, "Exp"
, "Ln"
, "Sqrt"
, "Abs"
, "Sign"
, "D"
, "Curl"
, "Diverge"
, "Integrate"
, "Limit"
);
var funcs_refchapter5 = new Array(
"Random, RandomSeed"
, "RngCreate"
, "RngSeed"
, "Rng"
, "RandomIntegerMatrix"
, "RandomIntegerVector"
, "RandomPoly"
);
var funcs_refchapter6 = new Array(
"Add"
, "Sum"
, "Factorize"
, "Taylor"
, "InverseTaylor"
, "ReversePoly"
, "BigOh"
, "LagrangeInterpolant"
);
var funcs_refchapter7 = new Array(
"!"
, "!!"
, "***"
, "Subfactorial"
, "Bin"
, "Eulerian"
, "LeviCivita"
, "Permutations"
);
var funcs_refchapter8 = new Array(
"Gamma"
, "Zeta"
, "Bernoulli"
, "Euler"
, "LambertW"
);
var funcs_refchapter9 = new Array(
"Complex"
, "Re"
, "Im"
, "I"
, "Conjugate"
, "Arg"
);
var funcs_refchapter10 = new Array(
"LaplaceTransform"
);
var funcs_refchapter11 = new Array(
"Simplify"
, "RadSimp"
, "FactorialSimplify"
, "LnExpand"
, "LnCombine"
, "TrigSimpCombine"
);
var funcs_refchapter12 = new Array(
"Solve"
, "OldSolve"
, "SuchThat"
, "Eliminate"
, "PSolve"
, "MatrixSolve"
);
var funcs_refchapter13 = new Array(
"Newton"
, "FindRealRoots"
, "NumRealRoots"
, "MinimumBound"
, "MaximumBound"
);
var funcs_refchapter14 = new Array(
"CanProve"
);
var funcs_refchapter15 = new Array(
"OdeSolve"
, "OdeTest"
, "OdeOrder"
);
var funcs_refchapter16 = new Array(
"Dot, ."
, "InProduct"
, "CrossProduct"
, "Outer, o"
, "ZeroVector"
, "BaseVector"
, "Identity"
, "ZeroMatrix"
, "Diagonal"
, "DiagonalMatrix"
, "OrthogonalBasis"
, "OrthonormalBasis"
, "Normalize"
, "Transpose"
, "Determinant"
, "Trace"
, "Inverse"
, "Minor"
, "CoFactor"
, "MatrixPower"
, "SolveMatrix"
, "CharacteristicEquation"
, "EigenValues"
, "EigenVectors"
, "Sparsity"
, "Cholesky"
);
var funcs_refchapter17 = new Array(
"IsScalar"
, "IsVector"
, "IsMatrix"
, "IsSquareMatrix"
, "IsHermitian"
, "IsOrthogonal"
, "IsDiagonal"
, "IsLowerTriangular"
, "IsUpperTriangular"
, "IsSymmetric"
, "IsSkewSymmetric"
, "IsUnitary"
, "IsIdempotent"
);
var funcs_refchapter18 = new Array(
"JacobianMatrix"
, "VandermondeMatrix"
, "HessianMatrix"
, "HilbertMatrix"
, "HilbertInverseMatrix"
, "ToeplitzMatrix"
, "WronskianMatrix"
, "SylvesterMatrix"
);
var funcs_refchapter19 = new Array(
"Expand"
, "Degree"
, "Coef"
, "Content"
, "PrimitivePart"
, "LeadingCoef"
, "Monic"
, "SquareFree"
, "Horner"
, "ExpandBrackets"
, "EvaluateHornerScheme"
);
var funcs_refchapter20 = new Array(
"OrthoP"
, "OrthoH"
, "OrthoG"
, "OrthoL"
, "OrthoT"
, "OrthoU"
, "OrthoPSum"
, "OrthoHSum"
, "OrthoLSum"
, "OrthoGSum"
, "OrthoTSum"
, "OrthoUSum"
, "OrthoPoly"
, "OrthoPolySum"
);
var funcs_refchapter21 = new Array(
"Head"
, "Tail"
, "Length"
, "Map"
, "MapSingle"
, "MakeVector"
, "Select"
, "Nth"
, "DestructiveReverse"
, "Reverse"
, "List"
, "UnList"
, "Listify"
, "Concat"
, "Delete"
, "Insert"
, "DestructiveDelete"
, "DestructiveInsert"
, "Replace"
, "DestructiveReplace"
, "FlatCopy"
, "Contains"
, "Find"
, "Append"
, "DestructiveAppend"
, "RemoveDuplicates"
, "Push"
, "Pop"
, "PopFront"
, "PopBack"
, "Swap"
, "Count"
, "Intersection"
, "Union"
, "Difference"
, "FillList"
, "Drop"
, "Take"
, "Partition"
, "Assoc"
, "AssocIndices"
, "AssocDelete"
, "Flatten"
, "UnFlatten"
, "Type"
, "NrArgs"
, "VarList"
, "VarListArith"
, "VarListSome"
, "FuncList"
, "FuncListArith"
, "FuncListSome"
, "BubbleSort"
, "HeapSort"
, "PrintList"
, "Table"
, "TableForm"
, "GlobalPop"
, "GlobalPush"
);
var funcs_refchapter22 = new Array(
":"
, "@"
, "/@"
, ".."
, "NFunction"
, "Where"
, "AddTo"
);
var funcs_refchapter23 = new Array(
"MaxEvalDepth"
, "Hold"
, "Eval"
, "While"
, "Until"
, "If"
, "SystemCall"
, "Function"
, "Macro"
, "Use"
, "For"
, "ForEach"
, "Apply"
, "MapArgs"
, "Subst"
, "WithValue"
, "/:"
, "/::"
, "TraceStack"
, "TraceExp"
, "TraceRule"
, "Time"
);
var funcs_refchapter24 = new Array(
"!="
, "="
, "Not"
, "And"
, "Or"
, "IsFreeOf"
, "IsZeroVector"
, "IsNonObject"
, "IsEven"
, "IsOdd"
, "IsEvenFunction"
, "IsOddFunction"
, "IsFunction"
, "IsAtom"
, "IsString"
, "IsNumber"
, "IsList"
, "IsNumericList"
, "IsBound"
, "IsBoolean"
, "IsNegativeNumber"
, "IsNegativeInteger"
, "IsPositiveNumber"
, "IsPositiveInteger"
, "IsNotZero"
, "IsNonZeroInteger"
, "IsInfinity"
, "IsPositiveReal"
, "IsNegativeReal"
, "IsConstant"
, "IsGaussianInteger"
, "MatchLinear"
, "HasExpr"
, "HasExprArith"
, "HasExprSome"
, "HasFunc"
, "HasFuncArith"
, "HasFuncSome"
);
var funcs_refchapter25 = new Array(
"%"
, "True"
, "False"
, "EndOfFile"
);
var funcs_refchapter26 = new Array(
"Infinity"
, "Pi"
, "Undefined"
, "GoldenRatio"
, "Catalan"
, "gamma"
);
var funcs_refchapter27 = new Array(
":="
, "Set"
, "Clear"
, "Local"
, "++"
, "--"
, "Object"
, "SetGlobalLazyVariable"
, "UniqueConstant"
, "LocalSymbols"
);
var funcs_refchapter28 = new Array(
"FullForm"
, "Echo"
, "PrettyForm"
, "EvalFormula"
, "TeXForm"
, "CForm"
, "IsCFormable"
, "Write"
, "WriteString"
, "Space"
, "NewLine"
, "FromFile"
, "FromString"
, "ToFile"
, "ToString"
, "Read"
, "ToStdout"
, "ReadCmdLineString"
, "LispRead"
, "LispReadListed"
, "ReadToken"
, "Load"
, "Use"
, "DefLoad"
, "FindFile"
, "PatchLoad"
, "Nl"
, "V, InVerboseMode"
, "Plot2D"
, "Plot3DS"
, "XmlExplodeTag"
, "DefaultTokenizer"
, "XmlTokenizer"
, "OMForm"
, "OMRead"
, "OMDef"
);
var funcs_refchapter29 = new Array(
"StringMidSet"
, "StringMidGet"
, "String"
, "Atom"
, "ConcatStrings"
, "PatchString"
);
var funcs_refchapter30 = new Array(
"BernoulliDistribution"
, "BinomialDistribution"
, "tDistribution"
, "PDF"
, "ChiSquareTest"
);
var funcs_refchapter31 = new Array(
"IsPrime"
, "IsSmallPrime"
, "IsComposite"
, "IsCoprime"
, "IsSquareFree"
, "IsPrimePower"
, "NextPrime"
, "IsTwinPrime"
, "IsIrregularPrime"
, "IsCarmichaelNumber"
, "Factors"
, "IsAmicablePair"
, "Factor"
, "Divisors"
, "DivisorsSum"
, "ProperDivisors"
, "ProperDivisorsSum"
, "Moebius"
, "CatalanNumber"
, "FermatNumber"
, "HarmonicNumber"
, "StirlingNumber1"
, "StirlingNumber2"
, "DivisorsList"
, "SquareFreeDivisorsList"
, "MoebiusDivisorsList"
, "SumForDivisors"
, "RamanujanSum"
, "Cyclotomic"
, "PAdicExpand"
, "IsQuadraticResidue"
, "LegendreSymbol"
, "JacobiSymbol"
, "GaussianFactors"
, "GaussianNorm"
, "IsGaussianUnit"
, "IsGaussianPrime"
, "GaussianGcd"
);
var funcs_refchapter32 = new Array(
);
  if (containsFunc(funcs_refchapter1,seach))
    return "refchapter1.html";
  if (containsFunc(funcs_refchapter2,seach))
    return "refchapter2.html";
  if (containsFunc(funcs_refchapter3,seach))
    return "refchapter3.html";
  if (containsFunc(funcs_refchapter4,seach))
    return "refchapter4.html";
  if (containsFunc(funcs_refchapter5,seach))
    return "refchapter5.html";
  if (containsFunc(funcs_refchapter6,seach))
    return "refchapter6.html";
  if (containsFunc(funcs_refchapter7,seach))
    return "refchapter7.html";
  if (containsFunc(funcs_refchapter8,seach))
    return "refchapter8.html";
  if (containsFunc(funcs_refchapter9,seach))
    return "refchapter9.html";
  if (containsFunc(funcs_refchapter10,seach))
    return "refchapter10.html";
  if (containsFunc(funcs_refchapter11,seach))
    return "refchapter11.html";
  if (containsFunc(funcs_refchapter12,seach))
    return "refchapter12.html";
  if (containsFunc(funcs_refchapter13,seach))
    return "refchapter13.html";
  if (containsFunc(funcs_refchapter14,seach))
    return "refchapter14.html";
  if (containsFunc(funcs_refchapter15,seach))
    return "refchapter15.html";
  if (containsFunc(funcs_refchapter16,seach))
    return "refchapter16.html";
  if (containsFunc(funcs_refchapter17,seach))
    return "refchapter17.html";
  if (containsFunc(funcs_refchapter18,seach))
    return "refchapter18.html";
  if (containsFunc(funcs_refchapter19,seach))
    return "refchapter19.html";
  if (containsFunc(funcs_refchapter20,seach))
    return "refchapter20.html";
  if (containsFunc(funcs_refchapter21,seach))
    return "refchapter21.html";
  if (containsFunc(funcs_refchapter22,seach))
    return "refchapter22.html";
  if (containsFunc(funcs_refchapter23,seach))
    return "refchapter23.html";
  if (containsFunc(funcs_refchapter24,seach))
    return "refchapter24.html";
  if (containsFunc(funcs_refchapter25,seach))
    return "refchapter25.html";
  if (containsFunc(funcs_refchapter26,seach))
    return "refchapter26.html";
  if (containsFunc(funcs_refchapter27,seach))
    return "refchapter27.html";
  if (containsFunc(funcs_refchapter28,seach))
    return "refchapter28.html";
  if (containsFunc(funcs_refchapter29,seach))
    return "refchapter29.html";
  if (containsFunc(funcs_refchapter30,seach))
    return "refchapter30.html";
  if (containsFunc(funcs_refchapter31,seach))
    return "refchapter31.html";
  if (containsFunc(funcs_refchapter32,seach))
    return "refchapter32.html";
  return "";
}
