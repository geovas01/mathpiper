package org.mathpiper;

import java.util.HashMap;

import java.util.Map;

public class Tests {

    private HashMap userFunctionsTestsMap = null;

    private HashMap builtInFunctionsTestsMap = null;

    public Tests() {

        userFunctionsTestsMap = new HashMap();

        builtInFunctionsTestsMap = new HashMap();

        String[] testString;


        testString = new String[3];
        testString[0] = "147";
        testString[1] = "\nVerify(False And? False,False);\nVerify(True And? False,False);\nVerify(False And? True,False);\nVerify(True And? True,True);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/And_.java";
        builtInFunctionsTestsMap.put("And?",testString);

        testString = new String[3];
        testString[0] = "82";
        testString[1] = "\nVerify(Atom?([a,b,c]),False);\nVerify(Atom?(a),True);\nVerify(Atom?(123),True);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Atom_.java";
        builtInFunctionsTestsMap.put("Atom?",testString);

        testString = new String[3];
        testString[0] = "111";
        testString[1] = "\nVerify(Concat([a,b],[c,d]), [a,b,c,d]);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Concatenate.java";
        builtInFunctionsTestsMap.put("Concat",testString);

        testString = new String[3];
        testString[0] = "100";
        testString[1] = "\nVerify(ConcatStrings(\"a\",\"b\",\"c\"),\"abc\");\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/ConcatenateStrings.java";
        builtInFunctionsTestsMap.put("ConcatStrings",testString);

        testString = new String[3];
        testString[0] = "77";
        testString[1] = "\nVerify(Equal?(a,b),False);\nVerify(Equal?(a,a),True);\nVerify(Equal?([a,b],[a]),False);\nVerify(Equal?([a,b],[a,b]),True);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Equal_.java";
        builtInFunctionsTestsMap.put("Equal?",testString);

        testString = new String[3];
        testString[0] = "129";
        testString[1] = "\nVerify(True Equivales? True,True);\nVerify(True Equivales? False,False);\nVerify(False Equivales? True,False);\nVerify(False Equivales? False,True);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Equivales_.java";
        builtInFunctionsTestsMap.put("Equivales?",testString);

        testString = new String[3];
        testString[0] = "174";
        testString[1] = "\n  //Test ExceptionCatch and ExceptionGet.\n  Local(exception);\n  exception := False;\n  ExceptionCatch(Check(False, \"Unspecified\", \"some error\"), exception := ExceptionGet());\n  Verify(exception =? False, False);\n\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/ExceptionCatch.java";
        builtInFunctionsTestsMap.put("ExceptionCatch",testString);

        testString = new String[3];
        testString[0] = "92";
        testString[1] = "\n//Reverse and FlatCopy (and some friends) would segfault in the past if passed a string as argument.\n//I am not opposed to overloading these functions to also work on strings per se, but for now just\n//check that they return an error in stead of segfaulting.\n//\nVerify(ExceptionCatch(FlatCopy(\"abc\"),\"Exception\"), \"Exception\");\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/FlatCopy.java";
        builtInFunctionsTestsMap.put("FlatCopy",testString);

        testString = new String[3];
        testString[0] = "122";
        testString[1] = "\nVerify(FromBase(16,\"1e\"),30);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/FromBase.java";
        builtInFunctionsTestsMap.put("FromBase",testString);

        testString = new String[3];
        testString[0] = "88";
        testString[1] = "\n// FunctionToList and ListToFunction coredumped when their arguments were invalid.\nVerify(FunctionToList(Cos(x)),[Cos,x]);\n\n{\n  Local(exception);\n\n  exception := False;\n  ExceptionCatch(FunctionToList(1.2), exception := ExceptionGet());\n  Verify(exception =? False, False);\n};\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/FunctionToList.java";
        builtInFunctionsTestsMap.put("FunctionToList",testString);

        testString = new String[3];
        testString[0] = "77";
        testString[1] = "\nRulebaseHoldArguments(\"a\", [b]);\nVerify(Function?(a(b)),True);\nRetract(\"a\", 1);\nVerify(Function?(a),False);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Function_.java";
        builtInFunctionsTestsMap.put("Function?",testString);

        testString = new String[3];
        testString[0] = "76";
        testString[1] = "\nVerify(GreaterThan?(2,3),False);\nVerify(GreaterThan?(3,2),True);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/GreaterThan_.java";
        builtInFunctionsTestsMap.put("GreaterThan?",testString);

        testString = new String[3];
        testString[0] = "125";
        testString[1] = "\nVerify(True Implies? True,True);\nVerify(True Implies? False,False);\nVerify(False Implies? True,True);\nVerify(False Implies? False,True);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Implies_.java";
        builtInFunctionsTestsMap.put("Implies?",testString);

        testString = new String[3];
        testString[0] = "98";
        testString[1] = "\nVerify(Integer?(123),True);\nVerify(Integer?(123.123),False);\nVerify(Integer?(a),False);\nVerify(Integer?([a]),False);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Integer_.java";
        builtInFunctionsTestsMap.put("Integer?",testString);

        testString = new String[3];
        testString[0] = "77";
        testString[1] = "\nVerify(LessThan?(2,3),True);\nVerify(LessThan?(3,2),False);\n\nVerify(LessThan?(-1e-115, 0), True);\nVerify(LessThan?(-1e-15, 0), True);\nVerify(LessThan?(-1e-10, 0), True);\nVerify(LessThan?(-1e-5, 0), True);\nVerify(LessThan?(-1e-1, 0), True);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/LessThan_.java";
        builtInFunctionsTestsMap.put("LessThan?",testString);

        testString = new String[3];
        testString[0] = "89";
        testString[1] = "\nVerify(PipeFromString(\"(+ a b)\") LispRead(),a+b);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/LispRead.java";
        builtInFunctionsTestsMap.put("LispRead",testString);

        testString = new String[3];
        testString[0] = "96";
        testString[1] = "\n// FunctionToList and ListToFunction coredumped when their arguments were invalid.\nVerify(ListToFunction([Cos,x]),Cos(x));\n\n{\n  Local(exception);\n \n  exception := False;\n  ExceptionCatch(ListToFunction(1.2), exception := ExceptionGet());\n  Verify(exception =? False, False);\n};\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/ListToFunction.java";
        builtInFunctionsTestsMap.put("ListToFunction",testString);

        testString = new String[3];
        testString[0] = "82";
        testString[1] = "\nVerify(List?([a,b,c]),True);\nVerify(List?(a),False);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/List_.java";
        builtInFunctionsTestsMap.put("List?",testString);

        testString = new String[3];
        testString[0] = "144";
        testString[1] = "\n{\n  Verify(Bound?([]),False);\n  Local(a);\n  Verify(Bound?(a),False);\n  a:=1;\n  Verify(Bound?(a),True);\n  Unassign(a);\n  Verify(Bound?(a),False);\n};\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Local.java";
        builtInFunctionsTestsMap.put("Local",testString);

        testString = new String[3];
        testString[0] = "96";
        testString[1] = "\nVerify(Not?(True),False);\nVerify(Not?(False),True);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Not_.java";
        builtInFunctionsTestsMap.put("Not?",testString);

        testString = new String[3];
        testString[0] = "111";
        testString[1] = "\nVerify(Number?(123),True);\nVerify(Number?(123.123),True);\nVerify(Number?(a),False);\nVerify(Number?([a]),False);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Number_.java";
        builtInFunctionsTestsMap.put("Number?",testString);

        testString = new String[3];
        testString[0] = "139";
        testString[1] = "\nVerify(False Or? False,False);\nVerify(True Or? False,True);\nVerify(False Or? True,True);\nVerify(True Or? True,True);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Or_.java";
        builtInFunctionsTestsMap.put("Or?",testString);

        testString = new String[3];
        testString[0] = "89";
        testString[1] = "\n Verify(ToAtom(\"a\"),a);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/ToAtom.java";
        builtInFunctionsTestsMap.put("ToAtom",testString);

        testString = new String[3];
        testString[0] = "117";
        testString[1] = "\nVerify(ToBase(16,30),\"1e\");\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/ToBase.java";
        builtInFunctionsTestsMap.put("ToBase",testString);

        testString = new String[3];
        testString[0] = "97";
        testString[1] = "\n Verify(ToString(a),\"a\");\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/ToString.java";
        builtInFunctionsTestsMap.put("ToString",testString);

        testString = new String[3];
        testString[0] = "207";
        testString[1] = "\n// Reported by Serge: xml tokenizer not general enough\nVerify(XmlExplodeTag(\"<p/>\"),   XmlTag(\"P\",[],\"OpenClose\"));\nVerify(XmlExplodeTag(\"<p / >\"), XmlTag(\"P\",[],\"OpenClose\"));\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/XmlExplodeTag.java";
        builtInFunctionsTestsMap.put("XmlExplodeTag",testString);

        testString = new String[3];
        testString[0] = "1";
        testString[1] = "\nTesting(\"From: calculus.mpt\");\n\nTesting(\"UnaryFunctionInverses\");\nVerify(Sin(ArcSin(a)),a);\nVerify(Cos(ArcCos(a)),a);\n//TODO ??? Verify(Tan(ArcTan(a)),a);\n//TODO ??? this is not always the correct answer! Verify(ArcTan(Tan(a)),a);\nVerify(Tan(Pi/2),Infinity);\nVerify(Tan(Pi),0);\nVerify( Sin(x)/Cos(x), Tan(x) );\nVerify( TrigSimpCombine(Sin(x)^2 + Cos(x)^2), 1 );\nVerify( Sinh(x)-Cosh(x), Exp(-x));\nVerify( Sinh(x)+Cosh(x), Exp(x) );\nVerify( Sinh(x)/Cosh(x), Tanh(x) );\nVerify( Sinh(Infinity), Infinity);\nVerify( Sinh(x)*Csch(x), 1);\nVerify( 1/Coth(x), Tanh(x) );\nVerify(2+I*3,Complex(2,3));\nVerify(Magnitude(I+1),Sqrt(2));\nVerify(Re(2+I*3),2);\nVerify(Im(2+I*3),3);\n// Shouldn't these be in linalg.yts?\nVerify(ZeroVector(3),[0,0,0]);\nVerify(BaseVector(2,3),[0,1,0]);\nVerify(Identity(3),[[1,0,0],[0,1,0],[0,0,1]]);\n\nTesting(\"Checking comment syntax supported\");\n{\n  Local(a);\n  /* something here */\n  a:= 3;\n  // test 1\n\n  // /* test2 */\n\n  /* // test3 */\n\n  //Echo([a, Nl()]);\n\n  // Check parsing\n  a==-b; // This would generate a parse error in Yacas versions 1.0.54 and earlier\n};\n\nTesting(\"Yacas tutorials and journal entries.\");\nVerify(1+1,2);\nVerify(\"This text\",\"This text\");\nVerify(2+3,5);\nVerify(3*4,12);\nVerify(-(3*4),-12);\nVerify(2+3*4,14);\nVerify(6/3,2);\nVerify(1/3,1/3);\nVerify(Number?(N(1/3)),True);\nVerify(Sin(Pi),0);\nVerify(Minimum(5,1,3,-5,10),-5);\nVerify(Sqrt(2),Sqrt(2));\nVerify([1,2,3],[1,2,3]);\nVerify([a,b,c][2],b);\nVerify(\"abc\"[2],\"b\");\nVerify(x^(1/2),Sqrt(x));\n\n\n// Yacas used to not simplify the following, due to Pi being\n// considered constant. The expression was thus not expanded\n// as a univariate polynomial in Pi\nTestMathPiper(2*Pi/3,(Pi-Pi/3));\nTestMathPiper(( a*(Sqrt(Pi))^2/2), (a*Pi)/2);\nTestMathPiper(( 3*(Sqrt(Pi))^2/2), (3*Pi)/2);\nTestMathPiper(( a*(Sqrt(b ))^2/2), (a*b)/2);\n\nTestMathPiper(Sin(Pi-22),-Sin(22-Pi));\nTestMathPiper(Cos(Pi-22), Cos(22-Pi));\n\n\nVerify( Infinity + I, Complex(Infinity,1) );\nVerify( Infinity - I, Complex(Infinity,-1) );\nVerify( I - Infinity,Complex(-Infinity,1) );\nVerify( I + Infinity, Complex(Infinity,1) );\nVerify( I*Infinity,Complex(0,Infinity)); //Changed Ayal: I didn't like the old definition\nVerify( -I*Infinity,Complex(0,-Infinity)); //Changed Ayal: I didn't like the old definition\nVerify( Infinity*I,Complex(0,Infinity)); //Changed Ayal: I didn't like the old definition\nVerify( Infinity^I,Undefined);//Changed Ayal: I didn't like the old definition (it is undefined, right?)\nVerify( (2*I)^Infinity, Infinity );\nVerify( Infinity/I,Infinity );\nVerify( Sign(Infinity), 1 );\nVerify( Sign(-Infinity), -1 );\n// bugs with complex numbers\nVerify((1+I)^0, 1);\nVerify((-I)^0, 1);\nVerify((2*I)^(-10), -1/1024);\nVerify((-I)^(-10), -1);\nVerify((1-I)^(-10), Complex(0,1/32));\nVerify((1-I)^(+10), Complex(0,-32));\nVerify((1+2*I)^(-10), Complex(237/9765625,3116/9765625));\nVerify((1+2*I)^(+10), Complex(237,-3116));\n\n// - and ! operators didn't get handled correctly in the\n// parser/pretty printer (did you fix this, Serge?)\nVerify(PipeToString()Write((-x)!),\"(-x)!\");\n\n\n// some interesting interaction between the rules...\nVerify(x*x*x,x^3,);\nVerify(x+x+x,3*x);\nVerify(x+x-x+x,2*x);\n\n\n// numbers are too small because of wrong precision handling\nLocal(OriginalPrecision);\nOriginalPrecision := BuiltinPrecisionGet();\nBuiltinPrecisionSet(30);\nVerify(0.00000000000000000005421010862 =? 0, False);        // 2^(-64)\nVerify(0.00000000000000000005421010862 / 1 =? 0, False);\nVerify(0.00000000000000000005421010862 / 2 =? 0, False);\nVerify(0.00000000000000000001 =? 0, False);\nVerify(0.00000000000000000001 / 2 =? 0, False);\nVerify(0.00000000000000000000000000001 =? 0, False);\nVerify(0.000000000000000000000000000001 =? 0, False);\nVerify((0.0000000000000000000000000000000000000001 =? 0), False);\n// I added another one, the code will currently say that 0.0000...00001=0 is True\n// for a sufficient amount of zeroes, regardless of precision. Either that is good\n// or that is bad, but the above tests didn't go far enough. This one makes it\n// more explicit, unless we move over to a 128-bits system ;-)\nVerify((0.0000000000000000000000000000000000000000000000001 =? 0), False);\nBuiltinPrecisionSet(OriginalPrecision);\n\n\n// With the changes in numerics, RoundTo seems to have been broken. This line demonstrates the problem.\n// The last digit is suddenly rounded down (it used to be 4, correctly, and then gets rounded down to 3).\n// KnownFailure(RoundTo(RoundTo(N(Cot(2),9),9),N(Cot(2),9),9)=0);\n\n\n// Bug that was introduced when going to the new numeric setup where\n// numbers were not converted to strings any more. In the situation\n// -n*10^-m where n and m positive integers, the number got truncated\n// prematurely, resulting in a wrong rounding.\n{\n  Local(n,m,nkeep,lcl);\n  n:=7300 + 12*I;\n  m:=2700 + 100*I;\n  nkeep:=n;\n  n:=m;\n  m:=nkeep - m*Round(nkeep/m);\n  lcl:=Re(N(n/m))+0.5;\n  Verify(FloorN(lcl),-3);\n};\n\n\n/* Bug reported by Adrian Vontobel. */\n{\n  Local(A1,A2);\n  A1:=Pi*20^2; // 400*Pi\n  A2:=Pi*18^2; // 324*Pi\n  Verify(Minimum(A1,A2), 324*Pi);\n  Verify(Maximum(A1,A2), 400*Pi);\n};\n\n//======================== The following tests are from sturm.mpt.\n\nBuiltinPrecisionSet(6);\n\nTesting(\"From: sturm.mpt\");\n/*\n TESTS:\n - random-test code for roots, be generating random roots and\n   multiplicities.\n - find an example where bisection is needed, or better, a group\n   of examples where bisection is needed, for tests\n*/\n\n\nVerifyZero(x) := (Abs(x)<?10^ -BuiltinPrecisionGet());\n\nTryRandomPoly(deg,coefmin,coefmax):=\n{\n  Local(Coefs,monicCoefs,p,mp,roots,px);\n  Coefs := Table(FloorN(coefmin+Random()*(coefmax-coefmin)),i,1,deg+1,1);\n  p  := Add(Coefs*x^(0 .. deg));\n  mp := Monic(p);\n  monicCoefs := Coef(mp,x,0 .. deg);\n  //Verify(Maximum(Abs(monicCoefs)) <=? MaximumBound(mp), True);\n  //Verify(Minimum(Abs(monicCoefs)) >?  MinimumBound(mp), True);\n  // HSO:  I don't think these two tests are useful, \n  //       and the second seems incorrect.\n  roots:=FindRealRoots(mp);\n  px := (mp Where x==roots);\n  Verify(Dot(px, px) <? 0.01, True);\n};\nTryRandomPoly(5,5,1000);\n\n\nTryRandomRoots(deg,coefmin,coefmax):=\n{\n  Local(coefs,p,roots,px,mult,sqf);\n  coefs:=RemoveDuplicates(Table(FloorN(coefmin+Random()*(coefmax-coefmin)),i,1,deg+1,1));\n  deg:=Length(coefs)-1;\n  mult:=1+Abs(Table(FloorN(coefmin+Random()*(coefmax-coefmin)),i,1,deg+1,1));\n  p:=Product((x-coefs)^mult);\n  p:=Rationalize(p);\n  sqf := SquareFree(p);\n  roots:=FindRealRoots(sqf);\n  //Verify(Length(roots) =? Length(coefs));\n  //Verify(Length(RemoveDuplicates(roots)) =? Length(coefs));\n  px := (p Where x==roots);\n  Verify(Dot(px, px) <? 0.01, True);\n  \n};\nTryRandomRoots(3,-10,10);\n\n\n\n{\n  Local(p);\n  p := FindRealRoots((x+2)^9*(x-4)^5*(x-1)^4)-[-2.,1.,4.];\n  Verify(VerifyZero(Dot(p,p)),True);\n};\n\n// Bounds on coefficients\nVerify(MinimumBound(4),-Infinity);\nVerify(MaximumBound(4),Infinity);\n\n// RealRootsCount\nVerify(RealRootsCount(x^2-1),2);\nVerify(RealRootsCount(x^2+1),0);\nVerify(FindRealRoots((x^2+20)*(x^2+10)),[]);\nVerify(RealRootsCount((x^2+20)*(x^2+10)),0);\nVerify(Difference(FindRealRoots(Expand((x*(x-10)^3*(x+2)^2))),[0,-2.,10.]),[]);\n\n// Simple test on Squarefree\nTestMathPiper(Monic(SquareFree((x-1)^2*(x-3)^3)),Monic((x-1)*(x-3)));\n\n// Check the rare case where the bounds finder lands on\n// exactly a root\n{\n  Local(p);\n  p:=FindRealRoots((x+4)*(x-6),1,7)-[-4.,6.];\n  Verify(VerifyZero(Dot(p, p)),True);\n};\n\n{\n  Local(p);\n  p:=Expand((x-3.1)*(x+6.23));\n  p:=FindRealRoots(p)-[-6.23,3.1];\n  Verify(VerifyZero(Dot(p, p)),True);\n};\n\nVerify(BuiltinPrecisionGet(),6);\n{\n  Local(res);\n  res:=FindRealRoots(Expand((x-3.1)*(x+6.23)))-[-6.23,3.1];\n  Verify(VerifyZero(Dot(res, res)) , True);\n};\n\n//====================== end of strum.mpt tests.\n\n";
        testString[2] = "/org/mathpiper/scripts4/a_initialization/miscellaneous/miscellaneous_tests.mpw";
        userFunctionsTestsMap.put("miscellaneous",testString);

        testString = new String[3];
        testString[0] = "1";
        testString[1] = "\n/* Numerical testers - all confirmed with various programs */\nTesting(\" *** TESTING NUMERICS *** \");\n\nBuiltinPrecisionSet(10);   //Echo(\"BIP set to \",10);\nNumericEqual(N(Sqrt(2),6), 1.41421,6);\nNumericEqual(N(N(1+Pi,20)-Pi,20),1,20);     \n// Should \"N\" have \"HoldArgument\" in some way, so inner \"N\" is \n// evaluated with outer precision 20?\n\nTesting(\"Pi\");\n/* \n   Got the first digits of Pi from the following page:\n   http://www.cecm.sfu.ca/projects/ISC/dataB/isc/C/pi10000.txt\n   Just checking that MathPiper agrees.\n   First, however, we need to set BuiltinPrecision way higher than 10 !!\n*/\nBuiltinPrecisionSet(90);//Echo(\"BIP set to \",90);\n\n\nNumericEqual(\nRoundToPrecision(N(Pi), 50)\n, 3.14159265358979323846264338327950288419716939937511\n, 50);\nNumericEqual(N(Pi,70),3.141592653589793238462643383279502884197169399375105820974944592307816,70);\nBuiltinPrecisionSet(10);//Echo(\"BIP set to \",10);\n\nTesting(\"Trig, Hyperbolic, Log, and Exp functions\");\nNumericEqual( N(Sec(2),10), -2.402997962, 9);\nNumericEqual( N(Csc(2),9),  1.09975017,9);\nNumericEqual( N(Cot(2),9), -0.457657554, 9);\nNumericEqual( N(Sinh(2),10), 3.626860408,10); \n    // matter of discussion whether rounding should be to nearest\n\nNumericEqual( N(ArcSin(2), 9), Complex(1.570796327,1.316957897),9);\nNumericEqual( N(ArcCos(2),9), Complex(0,-1.316957897),9);\nNumericEqual( N(ArcTan(2*I), 12), N(Complex(1.5707963267950,0.54930614433405),12),11); \n    // calculating to precision+1 because RoundToPrecision rounds... cluttering the last digit with round-off\nNumericEqual( N(ArcSinh(2), 10), 1.443635475,9);\nNumericEqual( N(ArcCosh(2), 10), 1.316957897,9);\nNumericEqual( N(ArcCosh(-2), 8), Complex(-1.3169579,3.14159265),8);\nNumericEqual( N(ArcTanh(2), 9), Complex(0.549306144,1.570796327),9);\n\n/* more Numerical tests - all confirmed with Maple */\nBuiltinPrecisionSet(55);//Echo(\"BIP set to \",55);\n/*\n (hso,100311) BuiltinPrecisionSet MUST specify a value higher\n than any of the precisions to be used in tests below.\n Otherwise, false errors are reported.  I have changed\n the value from 50 to 55, to satisfy this requirement.\n*/\nNumericEqual(\nRoundToPrecision(N(Sin(2.0)), 49)\n, 0.9092974268256816953960198659117448427022549714479\n,48);\n\nNumericEqual(\nRoundToPrecision(N(Sin(2.0)), 50)\n, 0.90929742682568169539601986591174484270225497144789\n,49);\n\nNumericEqual(\nRoundToPrecision(N(Sin(2.0)), 51)\n, 0.90929742682568169539601986591174484270225497144789\n,50);\n\nNumericEqual(\nRoundToPrecision(N(Cos(20.0)), 49)\n, 0.4080820618133919860622678609276449570992995103163\n, 48);\n\nNumericEqual(\nRoundToPrecision(N(Tan(20.0)), 49)\n, 2.2371609442247422652871732477303491783724839749188\n, 48);  \n\nNumericEqual(\nRoundToPrecision(N(Exp(10.32),54), 54)\n, 30333.2575962246035600343483350109621778376486335450125\n,48);   \n\nNumericEqual(\nRoundToPrecision(N(Ln(10.32/4.07)), 50)\n, 0.93044076059891305468974486564632598071134270468002\n, 50);   \n\nNumericEqual(\nRoundToPrecision(N(1.3^10.32), 48)\n, 14.99323664825717956473936947123246987802978985306\n, 48);\n\nNumericEqual(\nRoundToPrecision(N(Sqrt(5.3),51), 51)\n, 2.302172886644267644194841586420201850185830282633675\n,51); \n\nNumericEqual(\nRoundToPrecision(N(Sqrt(25.3)), 50)\n, 5.0299105359837166789719353820984438468186649281130\n,50);\n\nNumericEqual(\nRoundToPrecision(N(PowerN(13, -23)), 50)\n, 0.23949855470974885180294666343025235387321690490245e-25\n, 50);\n\nTesting(\"Newton\");\nNumericEqual(\nRoundToPrecision({Local(x);x:=Newton(x*Exp(x)-4,x,1,10^(-49)); N(x*Exp(x));}, 49)\n, 4\n,49);\n\nVerify(Newton(x^2+1,x,1,0.1,-3,3), Fail);\nNumericEqual(Newton(x^2-1,x,1,0.1,-3,3), 1,BuiltinPrecisionGet());\n\nTesting(\"Trig functions compounded with their Inverses\");\nNumericEqual(\nRoundToPrecision(N(ArcSin(0.32)), 49)\n, 0.3257294872946301593103199105324500784354180998122808003\n,49);\n\nNumericEqual(\nRoundToPrecision(N(Sin(N(ArcSin(0.1234567)))), 49)\n, 0.1234567\n,49);\n\n/* ArcSin(x) for x close to 1 */\n\nNumericEqual(\nRoundToPrecision(N( (1-Sin(N(ArcSin(1-10^(-25)))))*10^25), 25)\n, 1\n, 25);\n\nNumericEqual(\nN(ArcSin(N(Sin(1.234567),50)),50)\n, N(1.234567,50)\n, 49);  // calculating to precision+1 because RoundToPrecision rounds... cluttering the last digit with round-off\n\nNumericEqual(\nRoundToPrecision(N(ArcCos(0.32)), 49)\n, 1.2450668395002664599210017811073013636631665998753\n, 49);\n\nNumericEqual(\nRoundToPrecision(N(ArcTan(0.32)), 49)\n, 0.3097029445424561999173808103924156700884366304804\n, 49);\n\nNumericEqual(\nRoundToPrecision(N(Cos(N(ArcCos(0.1234567)))), 49)\n, 0.1234567\n, 49);\n\nNumericEqual(\nRoundToPrecision(N(ArcCos(N(Cos(1.234567)))), 49)\n, 1.234567\n, 49);\n\nNumericEqual(\nRoundToPrecision(N(Tan(N(ArcTan(20)))), 46)        // large roundoff error on Tan() calculation due to subtraction from Pi/2 -- unavoidable loss of precision\n, 20\n, 46);\n//KnownFailure(\nNumericEqual(\nRoundToPrecision(N(Tan(N(ArcTan(500000)))), 38)\n, 500000\n//)\n, 38);\n\nBuiltinPrecisionSet(60); //Echo(\"BIP set to \",60); // obviously, 50 is not enough for the following\n//KnownFailure(\nNumericEqual(\nRoundToPrecision(N((Pi/2-ArcTan(N(Tan(N(Pi/2)-10^(-24)))))*10^24 ), 25)\n, 1\n//)\n, 25);\n\n/// special functions\nBuiltinPrecisionSet(50); //Echo(\"BIP set to \",50); // needs a pretty high value for Gamma\n\nTesting(\"Gamma function\");\nTestMathPiper(\nGamma(10.5)\n, (654729075*Sqrt(Pi))/1024\n);\n\nTestMathPiper(\nGamma(9/2)\n, (105*Sqrt(Pi))/16\n);\n\nTestMathPiper(\nGamma(-10.5)\n, (-2048*Sqrt(Pi))/13749310575\n);\n\nTestMathPiper(\nGamma(-7/2)\n, (16*Sqrt(Pi))/105\n);\n\nNumericEqual(RoundToPrecision(N( InternalGammaNum(10.5) ), 13), 1133278.3889487855673, 13);\n\nKnownFailure(\nNumericEqual(RoundToPrecision(N( InternalGammaNum(-11.5) ), 20), 0.00000002295758104824, 20)\n);\n// our gamma is wrong\n\nKnownFailure(\nNumericEqual(RoundToPrecision(N( InternalGammaNum(-12.5) ), 20), -0.00000000183660648386, 20)\n);\n//our gamma is wrong\n\n// Check for one example that N(Gamma(x)) returns the same as InternalGammaNum\nNumericEqual(RoundToPrecision(N( Gamma(10.5) ), 13), 1133278.3889487855673, 13);\n\nTesting(\"Zeta function\");\nKnownFailure(\nNumericEqual(    \nRoundToPrecision(N( Zeta(-11.5) ), 18), 0.020396978715942792,18)\n);\n\nTestMathPiper(\nZeta(40)\n, (261082718496449122051*Pi^40)/20080431172289638826798401128390556640625\n);\n\nTestMathPiper(\nZeta(-11)\n, 691/32760\n);\n\nTestMathPiper(\nZeta(-12)\n, 0\n);\n\nNumericEqual(\nRoundToPrecision(N(Zeta(40)), 19)\n, 1.0000000000009094948\n,19);\n\nNumericEqual(\nRoundToPrecision(N(Zeta(1.5)), 19)\n, 2.6123753486854883433\n,19);\n\n// test correctness of Zeta(3)\nNumericEqual(\nRoundToPrecision(InternalZetaNum(3)-N(Zeta(3)), 20)\n, 0\n,20);\n\nTesting(\"Bernoulli number\");\nTestMathPiper(\nBernoulli(40)\n, -261082718496449122051/13530\n);\n\nTesting(\"Continued Fraction\");\nVerify(\nContFracList(355/113)\n, [3,7,16]\n);\n\nVerify(\nContFracList(-24, 4)\n, [-24]\n);\n\nVerify(\nContFracList(-355/113)\n, [-4,1,6,16]\n);\n\n//BuiltinPrecisionSet(7);//Echo(\"BIP set to \",7);\n\nVerify(\nNearRational(N(Pi),3)\n, 201/64\n);\n\n/*\n  For the NearRational test, perhaps better would be a real test that\n  checks that the result is correct up to the required number of digits\n  accuracy.\n*/\nBuiltinPrecisionSet(10);//Echo(\"BIP set to \",10);\nVerify(\nNearRational(N(Pi))\n, 355/113,\n);\n\n// Lambert's W function\nTesting(\"Lambert W function\");\nBuiltinPrecisionSet(20);\n\nNumericEqual(\nN(RoundToPrecision(LambertW(-0.24),20)),-0.33576116478890275173,20);\n\nNumericEqual(\nN(RoundToPrecision(LambertW(10),20)),1.7455280027406993831,20);\n\n// Bessel Functions\nTesting(\"Bessel functions\");\nBuiltinPrecisionSet(50);//Echo(\"BIP set to \",50);\nNumericEqual( N(BesselJ(0,.5)), RoundToPrecision(.93846980724081290422840467359971262556892679709682,50),50 );\nNumericEqual( N(BesselJ(0,.9)), RoundToPrecision(.80752379812254477730240904228745534863542363027564,50),50 );\nNumericEqual( N(BesselJ(0,.99999)), RoundToPrecision(.76520208704756659155313775543958045290339472808482,50),50 );\nNumericEqual( N(BesselJ(10,.75)), RoundToPrecision(.000000000014962171311759681469871248362168283485781647202136,50),50 );\nNumericEqual( N(BesselJ(5,1)), RoundToPrecision(.00024975773021123443137506554098804519815836777698007,50),50 );\nNumericEqual( N(BesselJ(4,2)), RoundToPrecision(.033995719807568434145759211288531044714832968346313,50),50 );\nNumericEqual( N(BesselJ(10,3)), RoundToPrecision( .000012928351645715883777534530802580170743420832844164,50),50 );\n\nNumericEqual( N(BesselJ(11,11)), RoundToPrecision( .20101400990926940339478738551009382430831534125484,50),50 );\nNumericEqual( N(BesselJ(-11,11)), RoundToPrecision( -.20101400990926940339478738551009382430831534125484,50),50 );\nNumericEqual( RoundToPrecision(N(BesselJ(1,10)),50), RoundToPrecision( .043472746168861436669748768025859288306272867118594, 50),50 );\nNumericEqual( N(BesselJ(10,10)), RoundToPrecision( .20748610663335885769727872351875342803274461128682, 50 ),50 );\nNumericEqual( RoundToPrecision(N(BesselJ(1,3.6)),50), RoundToPrecision( .095465547177876403845706744226060986019432754908851, 50 ),50) ;\n\nBuiltinPrecisionSet(20);//Echo(\"BIP set to \",20);\nVerify( RoundToPrecision(N(Erf(Sqrt(0.8)),20),19),\nRoundToPrecision(.79409678926793169113034892342, 19)\n);\n\nVerify( RoundToPrecision(N(Erf(50*I+20)/10^910,22),19),\nRoundToPrecision(1.09317119002909585408+I*0.00475463306931818955275, 19)\n);\n\n// testing GammaConstNum against Maple\nTesting(\"Gamma constant\");\nBuiltinPrecisionSet(41);//Echo(\"BIP set to \",41);\nNumericEqual(RoundToPrecision(Internalgamma(),40), 0.5772156649015328606065120900824024310422,BuiltinPrecisionGet());\nBuiltinPrecisionSet(20);//Echo(\"BIP set to \",20);\n\nVerify(gamma,ToAtom(\"gamma\"));\n\nNumericEqual(RoundToPrecision(Internalgamma()+0,19), 0.5772156649015328606,19);\n\nNumericEqual(RoundToPrecision(N(1/2+gamma+Pi), 19), 4.2188083184913260991,19);\n\n// From GSL 1.0\n//NumericEqual( N(PolyLog(2,-0.001),20), -0.00099975011104865108, 20 );\n// PolyLog I didn't write PolyLog, but it seems to not always calculate correctly up to the last digit.\nVerify( RoundToPrecision(N(PolyLog(2,-0.001)+0.00099975011104865108,20),20),0);\n\n// Round-off errors\nN({\n  Local(a,b);\n  a:= 77617;\n  b:= 33096;\n  // this expression gives a wrong answer on any hardware floating-point platform\n  NumericEqual( 333.75*b^6 + a^2*(11*a^2*b^2-b^6-121*b^4-2)+5.5*b^8 +a/(2*b), -0.827396,6);\n},40);\n\n";
        testString[2] = "/org/mathpiper/scripts4/a_initialization/miscellaneous/numerics_tests.mpw";
        userFunctionsTestsMap.put("numerics",testString);

        testString = new String[3];
        testString[0] = "38";
        testString[1] = "\nVerify(0&1, 0);\n\n";
        testString[2] = "/org/mathpiper/scripts4/a_initialization/standard/ampersand_operator.mpw";
        userFunctionsTestsMap.put("&",testString);

        testString = new String[3];
        testString[0] = "37";
        testString[1] = "\nVerify(0%1, 0);\n\n";
        testString[2] = "/org/mathpiper/scripts4/a_initialization/standard/percent_operator.mpw";
        userFunctionsTestsMap.put("%",testString);

        testString = new String[3];
        testString[0] = "38";
        testString[1] = "\nVerify(0|1, 1);\n\n";
        testString[2] = "/org/mathpiper/scripts4/a_initialization/standard/vertical_bar_operator.mpw";
        userFunctionsTestsMap.put("|",testString);

        testString = new String[3];
        testString[0] = "1";
        testString[1] = "\nNextTest(\"Test arithmetic\");\n\nNextTest(\"Basic calculations\");\nVerify(3 + 2 , 5);\nVerify(3-7, -4);\nVerify(1 =? 2 , 0 =? -1);\nVerify(5 ^ 2 , 25);\n\nVerify(Zero?(0.000),True);\n\nVerify(2/5,Hold(2/5));\nVerify(Zero?(N(2/5)-0.4),True);\nVerify(Rational?(2),True);\nVerify(Rational?(2/5),True);\nVerify(Rational?(-2/5),True);\nVerify(Rational?(2.0/5),False);\nVerify(Rational?(Pi/2),False);\nVerify(Numerator(2/5),2);\nVerify(Denominator(2/5),5);\n\nVerifyArithmetic(10,5,8);\nVerifyArithmetic(10000000000,5,8);\nVerifyArithmetic(10,50,80);\nVerifyArithmetic(10000,50,88);\n\nVerify(4!,24);\nVerify(BinomialCoefficient(2,1),2);\n\nNextTest(\"Testing math stuff\");\nVerify(1*a,a);\nVerify(a*1,a);\nVerify(0*a,0);\nVerify(a*0,0);\nVerify(aa-aa,0);\n\nVerify(2+3,5);\nVerify(2*3,6);\n\nVerify(2+3*4,14);\nVerify(3*4+2,14);\nVerify(3*(4+2),18);\nVerify((4+2)*3,18);\n\nVerify(15/5,3);\n\nVerify(-2+3,1);\nVerify(-2.01+3.01,1.);\n\nVerify(0+a,a);\nVerify(a+0,a);\nVerify(aa-aa,0);\n\nTesting(\"IntegerOperations\");\nVerify(1<<10,1024);\nVerify(1024>>10,1);\nVerify(Modulo(10,3),1);\nVerify(Quotient(10,3),3);\nVerify(GcdN(55,10),5);\n\nVerify(Modulo(2,Infinity),2);\nVerify(Modulo([0,1,2,3,4,5,6],2),[0,1,0,1,0,1,0]);\nVerify(Modulo([0,1,2,3,4,5,6],[2,2,2,2,2,2,2]),[0,1,0,1,0,1,0]);\n\nTesting(\"PowerN\");\n// was broken in the gmp version\nVerify(PowerN(19, 0), 1);\nVerify(PowerN(1, -1), 1);\nVerify(PowerN(1, -2), 1);\nVerify(Zero?(PowerN(10, -2)- 0.01),True);\nVerify(PowerN(2, 3), 8);\nNumericEqual(PowerN(2, -3), 0.125,BuiltinPrecisionGet());\n\nTesting(\"Rounding\");\nVerify(Floor(1.2),1);\nVerify(Floor(-1.2),-2);\nVerify(Ceil(1.2),2);\nVerify(Ceil(-1.2),-1);\nVerify(Round(1.49),1);\nVerify(Round(1.51),2);\nVerify(Round(-1.49),-1);\nVerify(Round(-1.51),-2);\n\nTesting(\"Bases\");\nVerify(ToBase(16,255),\"ff\");\nVerify(FromBase(2,\"100\"),4);\n\n// conversion between decimal and binary digits\nVerify(BitsToDigits(2000, 10), 602);\nVerify(DigitsToBits(602, 10), 2000);\n\nLocalSymbols(f,ft)\n{\n  f(x,y):=(Quotient(x,y)*y+Rem(x,y)-x);\n  ft(x,y):=\n  {\n    Verify(f(x,y),0);\n    Verify(f(-x,y),0);\n    Verify(f(x,-y),0);\n    Verify(f(-x,-y),0);\n  };\n  ft(10,4);\n  ft(2.5,1.2);\n};\n\nTesting(\"Factorization\");\nVerify(\nEval(Factors(447738843))\n, [[3,1],[17,1],[2729,1],[3217,1]]\n);\n\n\n//Exponential notation is now supported in the native arithmetic library too...\nVerify(2e3+1,2001.);\nVerify(2.0e3+1,2001.);\nVerify(2.00e3+1,2001.);\nVerify(2.000e3+1,2001.);\nVerify(2.0000e3+1,2001.);\n\nVerify(1+2e3,2001.);\nVerify(1+2.0e3,2001.);\nVerify(1+2.00e3,2001.);\nVerify(1+2.000e3,2001.);\nVerify(1+2.0000e3,2001.);\n\nNumericEqual(N(Sqrt(1e4))-100,0,BuiltinPrecisionGet());\nNumericEqual(N(Sqrt(1.0e4))-100,0,BuiltinPrecisionGet());\n\nVerify(2.0000e3-1,1999.);\n{\n  Local(p);\n  p:=BuiltinPrecisionGet();\n  BuiltinPrecisionSet(12);//TODO this will fail if you drop precision to below 12, for some reason.\n  NumericEqual(RoundToPrecision(10e3*1.2e-3,BuiltinPrecisionGet()),12.,BuiltinPrecisionGet());\n  BuiltinPrecisionSet(p);\n};\nVerify((10e3*1.2e-4)-1.2,0);\n\nVerify(Zero?(N(Sin(0.1e1)-Sin(1),30)),True);\n{\n  /* In Dutch they have a saying \"dit verdient geen schoonheidsprijs\" ;-) We need to sort this out.\n   * But a passable result, for now.\n   */\n  Local(diff);\n  diff := N(Sin(10e-1)-Sin(1),30);\n//BuiltinPrecisionSet(20);\n//Echo(\"diff = \",diff);\n//Echo(\"diff > -0.00001 = \",diff > -0.00001);\n//Echo(\"diff < 0.00001 = \",diff < 0.00001);\n  Verify(diff >? -0.00001 And? diff <? 0.00001,True);\n};\n\n\n/* Jonathan reported a problem with Simplify(-Sqrt(8)/2), which returned some\n * complex expression containing greatest common divisors of square roots.\n * This was fixed by adding some rules dealing with taking the gcd of two objects\n * where at least one is a square root.\n */\nVerify(-Sqrt(8)/2,-Sqrt(2));\nVerify(Sqrt(8)/2,Sqrt(2));\nVerify(Gcd(Sqrt(2),Sqrt(2)),Sqrt(2));\nVerify(Gcd(-Sqrt(2),-Sqrt(2)),Sqrt(2));\nVerify(Gcd(Sqrt(2),-Sqrt(2)),Sqrt(2));\nVerify(Gcd(-Sqrt(2),Sqrt(2)),Sqrt(2));\n\n\n";
        testString[2] = "/org/mathpiper/scripts4/a_initialization/stdarith/arithmetic_test.mpw";
        userFunctionsTestsMap.put("arithmetic",testString);

        testString = new String[3];
        testString[0] = "159";
        testString[1] = "\nVerify((-2)*Infinity,-Infinity);\n\nVerify(Infinity*0,Undefined);\n\n// The following is a classical error: 0*x=0 is only true if\n// x is a number! In this case, it is checked for that the\n// multiplication of 0 with a vector returns a zero vector.\n// This would automatically be caught with type checking.\n// More tests of this ilk are possible: 0*matrix, etcetera.\nVerify(0*[a,b,c],[0,0,0]);\n\nVerify(Undefined*0,Undefined);\n\n";
        testString[2] = "/org/mathpiper/scripts4/a_initialization/stdarith/asterisk_operator.mpw";
        userFunctionsTestsMap.put("*",testString);

        testString = new String[3];
        testString[0] = "120";
        testString[1] = "\nVerify(1^Infinity,Undefined);\n\n// Matrix operations failed: a^2 performed the squaring on each element.\nVerify([[1,2],[3,4]]^2,[[7,10],[15,22]]);\n\n// Check that raising powers still works on lists/vectors (dotproduct?) correctly.\nVerify([2,3]^2,[4,9]);\n\nVerify(0.0000^(24),0);\n\n// expansion of negative powers of fractions\nVerify( (-1/2)^(-10), 1024);\n\nVerify( I^(Infinity), Undefined );\nVerify( I^(-Infinity), Undefined );\n\nVerify( 2^(-10), 1/1024 );\n\n";
        testString[2] = "/org/mathpiper/scripts4/a_initialization/stdarith/caret_operator.mpw";
        userFunctionsTestsMap.put("^",testString);

        testString = new String[3];
        testString[0] = "167";
        testString[1] = "\nVerify(Infinity/Infinity,Undefined);\nVerify(0.0/Sqrt(2),0);\nVerify(0.0000000000/Sqrt(2),0);\n\n";
        testString[2] = "/org/mathpiper/scripts4/a_initialization/stdarith/slash_operator.mpw";
        userFunctionsTestsMap.put("/",testString);

        testString = new String[3];
        testString[0] = "78";
        testString[1] = "{\nLocal(precision);\nprecision := BuiltinPrecisionGet();\nBuiltinPrecisionSet(10);\nVerify(MathExpTaylor0(0),1.0);\nVerify(MathExpTaylor0(.2), 1.221402759);\nVerify(MathExpTaylor0(1),  2.718281830);\nVerify(MathExpTaylor0(.234563),  1.264356123);\nBuiltinPrecisionSet(precision);\n};\n";
        testString[2] = "/org/mathpiper/scripts4/base/MathExpTaylor0.mpw";
        userFunctionsTestsMap.put("MathExpTaylor0",testString);

        testString = new String[3];
        testString[0] = "54";
        testString[1] = "\n/* Bug #15 */\nVerify(PowerN(0,0.55), 0);\n// LogN(-1) locks up in gmpnumbers.cpp, will be fixed in scripts\n//FIXME this test should be uncommented eventually\n// Verify(ExceptionCatch(PowerN(-1,-0.5), error), error);\n\n";
        testString[2] = "/org/mathpiper/scripts4/base/PowerN.mpw";
        userFunctionsTestsMap.put("PowerN",testString);

        testString = new String[3];
        testString[0] = "60";
        testString[1] = "\nVerify(Easter(1777), [3, 30]);\nVerify(Easter(1961), [4, 2]);\nVerify(Easter(2009), [4, 12]);\nVerify(Easter(2011), [4, 24]);\n\n\n";
        testString[2] = "/org/mathpiper/scripts4/calendar/Easter.mpw";
        userFunctionsTestsMap.put("Easter",testString);

        testString = new String[3];
        testString[0] = "60";
        testString[1] = "\nVerify(BinomialCoefficient(0,0),1 );\n\n";
        testString[2] = "/org/mathpiper/scripts4/combinatorics/Combinations.mpw";
        userFunctionsTestsMap.put("BinomialCoefficient",testString);

        testString = new String[3];
        testString[0] = "50";
        testString[1] = "\nTestMathPiper(Arg(Exp(2*I*Pi/3)),2*Pi/3);\n\n";
        testString[2] = "/org/mathpiper/scripts4/complex/Arg.mpw";
        userFunctionsTestsMap.put("Arg",testString);

        testString = new String[3];
        testString[0] = "146";
        testString[1] = "\nVerify( Limit(z,2*I) (I*z^4+3*z^2-10*I), Complex(-12,6) );\nKnownFailure( (Limit(n,Infinity) (n^2*I^n)/(n^3+1)) =? 0 );\nVerify( Limit(n,Infinity) n*I^n, Undefined );\n\nVerify(1/I, -I);\nVerify(I^2, -1);\nVerify(2/(1+I), 1-I);\nVerify(I^3, -I);\nVerify(I^4, 1);\nVerify(I^5, I);\nVerify(1^I, 1);\nVerify(0^I, Undefined);\nVerify(I^(-I), Exp(Pi/2));\nVerify((1+I)^33, 65536+I*65536);\nVerify((1+I)^(-33), (1-I)/131072);\nVerify(Exp(I*Pi), -1);\nTestMathPiper((a+b*I)*(c+d*I), (a*c-b*d)+I*(a*d+b*c));\nVerify(Ln(-1), I*Pi);\nVerify(Ln(3+4*I), Ln(5)+I*ArcTan(4/3));\n\nVerify(Re(2*I-4), -4);\nVerify(Im(2*I-4), 2);\n\n";
        testString[2] = "/org/mathpiper/scripts4/complex/Complex.mpw";
        userFunctionsTestsMap.put("Complex",testString);

        testString = new String[3];
        testString[0] = "43";
        testString[1] = "\n// the following broke evaluation (dr)\nVerify(Conjugate([a]),[a]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/complex/Conjugate.mpw";
        userFunctionsTestsMap.put("Conjugate",testString);

        testString = new String[3];
        testString[0] = "42";
        testString[1] = "\n/* Bug #7 */\nVerify(Im(3+I*Infinity), Infinity); /* resolved */\nVerify(Im(3+I*Undefined), Undefined);\n\n";
        testString[2] = "/org/mathpiper/scripts4/complex/Im.mpw";
        userFunctionsTestsMap.put("Im",testString);

        testString = new String[3];
        testString[0] = "139";
        testString[1] = "\nFunction(\"count\",[from,to])\n{\n   Local(i);\n   Local(sum);\n   Assign(sum,0);\n   For(i:=from,i<?to,i:=i+1)\n   {\n     Assign(sum,sum+i);\n   };\n   sum;\n};\n\nTesting(\"Function definitions\");\nVerify(count(1,11),55);\n\nRetract(\"count\",2);\n\n";
        testString[2] = "/org/mathpiper/scripts4/deffunc/Function.mpw";
        userFunctionsTestsMap.put("Function",testString);

        testString = new String[3];
        testString[0] = "121";
        testString[1] = "\n{\n  Local(a,b,c,d);\n  MacroRulebaseHoldArguments(foo,[a,b]);\n\n  // Simple check\n  foo(_c,_d) <-- [@c,@d];\n  Verify(foo(2,3),Hold([2,3]));\n\n  Macro(\"foo\",[a]) [@a,a];\n  a:=A;\n  Verify(foo(B),[B,A]);\n\n  /*\n  Removed from the test because the system now throws exceptions when\\\n  undefined functions are called.\n  Retract(foo,1);\n  Retract(foo,2);\n  Verify(foo(2,3),foo(2,3));\n  Verify(foo(B),foo(B));\n  */\n};\n\n{\n  Local(a,i,tot);\n  a:=100;\n  Retract(forloop,4);\n  Macro(forloop,[init,pred,inc,body])\n  {\n    @init;\n    While(@pred)\n    {\n      @body;\n      @inc;\n    };\n    True;\n  };\n  tot:=0;\n  forloop(i:=1,i<=?10,i++,tot:=tot+a*i);\n  Verify(i,11);\n  Verify(tot,5500);\n};\n\n{\n  Macro(\"bar\",[list,...]) Length(@list);\n  Verify(bar(a,b,list,bar,list),5);\n};\n\n{\n  Local(x,y,z);\n  RulebaseHoldArguments(\"@\",[x]);\n  y:=x;\n  Verify(`[@x,@y],[x,x]);\n  z:=u;\n  y:=[@z,@z];\n  Verify(`[@x,@y],[x,[@z,@z]]);\n  Verify(`[@x,`(@y)],[x,[@u,@u]]);\n  y:=Hold(`[@z,@z]);\n\n  Verify(`[@x,@y],[x,[u,u]]);\n  Verify(`[@x,`(@y)],[x,[u,u]]);\n  Retract(\"@\",1);\n};\n\n// check that a macro can reach a local from the calling environment.\n{\n  Macro(foo,[x]) a*(@x);\n  Function(bar,[x])\n  {\n    Local(a);\n    a:=2;\n    foo(x);\n  };\n  Verify(bar(3),6);\n};\n\n//check that with nested backquotes expansion only expands the top-level expression\n{\n  Local(a,b);\n  a:=2;\n  b:=3;\n  Verify(\n  `{\n     Local(c);\n     c:=@a+@b;\n     `((@c)*(@c));\n  },25);\n};\n\n";
        testString[2] = "/org/mathpiper/scripts4/deffunc/Macro.mpw";
        userFunctionsTestsMap.put("Macro",testString);

        testString = new String[3];
        testString[0] = "119";
        testString[1] = "\nTestMathPiper(Deriv(x)Ln(x),1/x);\nTestMathPiper(Deriv(x)Exp(x),Exp(x));\nTestMathPiper(Deriv(x)(x^4+x^3+x^2+x+1),4*x^3+3*x^2+2*x+1);\nTestMathPiper(Deriv(x)Sin(x),Cos(x));\nTestMathPiper(Deriv(x)Cos(x),-Sin(x));\nTestMathPiper(Deriv(x)Sinh(x),Cosh(x));\nTestMathPiper(Deriv(x)Cosh(x),Sinh(x));\nTestMathPiper(Deriv(x)ArcCos(x),-1/Sqrt(1-x^2));\nTestMathPiper(Deriv(x)ArcSin(x),1/Sqrt(1-x^2));\nTestMathPiper(Deriv(x)ArcTan(x),1/(x^2+1));\nTestMathPiper(Deriv(x)Sech(x),-Sech(x)*Tanh(x));\n\n";
        testString[2] = "/org/mathpiper/scripts4/deriv/Deriv.mpw";
        userFunctionsTestsMap.put("Deriv",testString);

        testString = new String[3];
        testString[0] = "94";
        testString[1] = "\nVerify(Differentiate(x) a,0);\nVerify(Differentiate(x) x,1);\nVerify(Differentiate(x) (x+x),2);\nVerify(Differentiate(x) (x*x),2*x);\nVerify(Differentiate(x) Differentiate(x) Sin(x),-Sin(x));\n\nVerify({Local(poly); poly := x^2; Differentiate(x) poly;}, 2*x);\n\n{\n  /*\n      Differentiate does not currently work when the expression is in a local variable.\n      This test actually fails but the failure happens to return a 0 which matches\n      the expected value.\n      THIS HAS BEEN CORRECTED.\n  */\n  Local(z);\n  // This function satisfies Laplaces eqn: Differentiate(x,2)z + Differentiate(y,2)z = 0\n  z:= ArcTan((2*x*y)/(x^2 - y^2));\n  Verify(Simplify((Differentiate(x,2) z) + Differentiate(y,2) z), 0 );\n};\n\nVerify( Differentiate(x,0) Sin(x), Sin(x) );\n\nRulebaseHoldArguments(\"f\",[x,t]);\nVerify(Differentiate(t) Integrate(x,a,b) f(x,t), Integrate(x,a,b) Deriv(t)f(x,t));\nRetract(\"f\",2);\n\n\n/* Bug #6 */\nKnownFailure((Differentiate(z) Conjugate(z)) =? Undefined);\n\n";
        testString[2] = "/org/mathpiper/scripts4/deriv/Differentiate.mpw";
        userFunctionsTestsMap.put("Differentiate",testString);

        testString = new String[3];
        testString[0] = "45";
        testString[1] = "\n/* One place where we forgot to change Sum to Add */\nTestMathPiper(Diverge([x*y,x*y,x*y],[x,y,z]),x+y);\n\n";
        testString[2] = "/org/mathpiper/scripts4/deriv/Diverge.mpw";
        userFunctionsTestsMap.put("Diverge",testString);

        testString = new String[3];
        testString[0] = "326";
        testString[1] = "\n\nTestPoly(poly,requiredResult):=\n{\n//Echo(poly);\n  Local(realResult);\n  realResult:=BinaryFactors(poly);\n  Verify(Length(realResult),Length(requiredResult));\n\n//Echo(requiredResult,realResult);\n  Local(intersection);\n  intersection:=[];\n  ForEach(item1,requiredResult)\n    ForEach(item2,realResult)\n    {\n      If(Simplify(item1-item2) =? [0,0],\n        intersection := (item1~intersection));\n    };\n  Verify(Length(realResult),Length(intersection/*Intersection(requiredResult,realResult)*/));\n  Verify(Simplify(poly-FW(realResult)),0);\n};\n\n// Simple factorizations\nTestPoly((x+1)*(x-1),[[x+1,1],[x-1,1]]);\n\n// Simple with multiple factors\nTestPoly((x+1)^2,[[x+1,2]]);\n\n// Test: term with lowest power not zero power\nTestPoly(x^2*(x+1)*(x-1),[[x,2],[x+1,1],[x-1,1]]);\nTestPoly(x^3*(x+1)*(x-1),[[x,3],[x+1,1],[x-1,1]]);\n\n// Variable different from x\nTestPoly((y+1)*(y-1),[[y+1,1],[y-1,1]]);\n\n// Test from Wester 1994 test\nTestPoly(Differentiate(x)(x+1)^20,[[20,1],[x+1,19]]);\n\n// From regression test, and verify that polys with unfactorizable parts works\nTestPoly((x^6-1),[[x^4+x^2+1,1],[x+1,1],[x-1,1]]);\n\n// Non-monic polynomials\nTestPoly((x+13)^2*(3*x-5)^3,[[27,1],[x+13,2],[x-5/3,3]]);\nTestPoly((x+13)^2*(4*x-5)^3,[[64,1],[x+13,2],[x-5/4,3]]);\n\n// Heavy: binary coefficients\nTestPoly((x+1024)*(x+2048),[[x+1024,1],[x+2048,1]]);\nTestPoly((x+1024)^2*(x+2048)^3,[[x+1024,2],[x+2048,3]]);\nTestPoly((16*x+1024)*(x+2048),[[16,1],[x+64,1],[x+2048,1]]);\nTestPoly((x+1024)*(x+2047),[[x+1024,1],[x+2047,1]]);\nTestPoly((x+1024)*(x+2049),[[x+1024,1],[x+2049,1]]);\n\nTestPoly((x+1024)*(x-2047),[[x+1024,1],[x-2047,1]]);\nTestPoly((x-1024)*(x+2047),[[x-1024,1],[x+2047,1]]);\nTestPoly((x-1024)*(x-2047),[[x-1024,1],[x-2047,1]]);\n\n// Rational coefficients\nTestPoly((x+4/7)*(x-5/9),[[x+4/7,1],[x-5/9,1]]);\n\n// More than two factors ;-)\nTestPoly((x+1)*(x-2)*(x+3)*(x-4)*(x+5)*(x-6),[[x+1,1],[x-2,1],[x+3,1],[x-4,1],[x+5,1],[x-6,1]]);\n\n\n";
        testString[2] = "/org/mathpiper/scripts4/factors/BinaryFactors.mpw";
        userFunctionsTestsMap.put("BinaryFactors",testString);

        testString = new String[3];
        testString[0] = "189";
        testString[1] = "\nRulebaseHoldArguments(\"f\",[x]);\nf(x):=Eval(Factor(x))=x;\nVerify(f(703), True);\nVerify(f(485), True);\nVerify(f(170410240), True);\nRetract(\"f\",1);\n\n// This was returning FWatom(Sin(x))\nVerify( Factor(Sin(x)), Factor(Sin(x)) );\n\n";
        testString[2] = "/org/mathpiper/scripts4/factors/Factor.mpw";
        userFunctionsTestsMap.put("Factor",testString);

        testString = new String[3];
        testString[0] = "52";
        testString[1] = "\n// Factoring 2*x^2 used to generate an error\nVerify(Factor(2*x^2),2*x^2);\n\n";
        testString[2] = "/org/mathpiper/scripts4/factors/YacasFactor.mpw";
        userFunctionsTestsMap.put("Factor",testString);

        testString = new String[3];
        testString[0] = "242";
        testString[1] = "\nTesting(\"UnivariatePolynomialFactorization\");\n\nVerify(Factors(x^2-4),[[x-2,1],[x+2,1]]);\nVerify(Factors(x^2+2*x+1),[[x+1,2]]);\nVerify(Factors(-9*x^2+45*x-36),[[-9,1],[x-1,1],[x-4,1]]);\nVerify(Factors(9*x^2-1),[[3,1],[x+1/3,1],[3*x-1,1]]);\nVerify(Factors(4*x^3+12*x^2-40*x),[[4,1],[x,1],[x-2,1],[x+5,1]]);\nVerify(Factors(32*x^3+32*x^2-70*x-75),[[32,1],[x-3/2,1],[x+5/4,2]]);\nVerify(Factors(3*x^3-12*x^2-2*x+8),[[x-4,1],[3*x^2-2,1]]);\nVerify(Factors(x^3+3*x^2-25*x-75),[[x+3,1],[x+5,1],[x-5,1]]);\nVerify(Factors(2*x^3-30*x^2+12*x^4),[[12,1],[x,2],[x-3/2,1],[x+5/3,1]]);\nVerify(Factors(5*x^7-20*x^6+25*x^5-20*x^4+25*x^3-20*x^2+20*x),[[5,1],[x,1],[x-2,2],[x^2+x+1,1],[x^2-x+1,1]]);\nVerify(Factors((2/5)*x^2-2*x-(12/5)), [[2/5,1],[x+1,1],[x-6,1]]);\n//Verify(Factors(.4*x^2-2*x-2.4), [[2/5,1],[x-6,1],[x+1,1]]); //Hangs.\nVerify(xFactorsBinomial(x^3+1), [[x+1,1],[x^2-x+1,1]]);\nVerify(xFactorsBinomial(x^4-1), [[x^2+1,1],[x-1,1],[x+1,1]]);\nVerify(xFactorsBinomial(x^5-1), [[x-1,1],[x^4+x^3+x^2+x+1,1]]);\nVerify(xFactorsBinomial(x^5+1), [[x+1,1],[x^4-x^3+x^2-x+1,1]]);\n\n\nTesting(\"BivariatePolynomialFactorization\");\n\nVerify(Factors(-7*x-14*y),[[-7,1],[x+2*y,1]]);\nVerify(xFactorsBinomial(x^2-a^2),[[x-a,1],[a+x,1]]);\nVerify(xFactors(a^2+2*a*b+b^2),[[a+b,2]]);\nVerify(xFactorsBinomial(x^3-y^3),[[x-y,1],[y^2+y*x+x^2,1]]);\nVerify(xFactorsBinomial(x^3+a^3),[[a+x,1],[a^2-a*x+x^2,1]]);\nVerify(xFactorsBinomial(x^6-a^6),[[a+x,1],[a^2-a*x+x^2,1],[x-a,1],[a^2+a*x+x^2,1]]);\nVerify(xFactors(3*x^2-x*y-10*y^2),[[3*x+5*y,1],[x-2*y,1]]);\n\n/* Bug #17 */\nVerify(Assoc(x-1, xFactorsBinomial(x^6-1))[2], 1);\n\n\n";
        testString[2] = "/org/mathpiper/scripts4/factors/YacasFactors.mpw";
        userFunctionsTestsMap.put("Factors",testString);

        testString = new String[3];
        testString[0] = "280";
        testString[1] = "\nTesting(\"BinomialPolynomialFactorization\");\n\n{\n    Local(n,poly,a,b,result,prod,ok);\n    a := 2;\n    b := 3;\n    For(n:=2,n<=?12,n++)\n    {\n        poly   := ExpandBrackets(a^n*x^n-b^n);\n        result := xFactorsBinomial(poly);\n        prod   := ExpandBrackets(FW(result));\n        Verify(a^n*x^n-b^n,prod);\n        \n        poly   := ExpandBrackets(a^n*x^n+b^n);\n        result := xFactorsBinomial(poly);\n        prod   := ExpandBrackets(FW(result));\n        Verify(a^n*x^n+b^n,prod);\n    };\n};\n\n\n";
        testString[2] = "/org/mathpiper/scripts4/factors/xFactorsBinomial.mpw";
        userFunctionsTestsMap.put("xFactorsBinomial",testString);

        testString = new String[3];
        testString[0] = "65";
        testString[1] = "\nVerify(Apply(\"+\",[2,3]),5);\n{\n  Local(x,y);\n  Verify(Apply([[x,y],x+y],[2,3]),5);\n  Verify(Apply(Lambda([x,y],x+y),[2,3]),5);\n  Verify(Lambda([x,y],x+y) @ [2,3],5);\n\n  /* Basically the next line is to check that [[x],Length(x)]\n   * behaves in an undesirable way (Length being evaluated\n   * prematurely), so that the next test can then check that\n   * Lambda solves the problem.\n   */\n  Verify(Apply([[x],Length(x)],[\"aaa\"]),Length);\n  Verify(Apply(Lambda([x],Length(x)),[\"aaa\"]),3);\n\n  Verify(x,x);\n  Verify(y,y);\n\n  Testing(\"ThreadingListables\");\n  x:=[bb,cc,dd];\n  Verify(Sin(aa*x),[Sin(aa*bb),Sin(aa*cc),Sin(aa*dd)]);\n};\n\n";
        testString[2] = "/org/mathpiper/scripts4/functional/Apply.mpw";
        userFunctionsTestsMap.put("Apply",testString);

        testString = new String[3];
        testString[0] = "96";
        testString[1] = "\nLocalSymbols(f,x,n)\n{\n  RulebaseHoldArguments(f,[n]);\n\n  f(_n) <-- Apply(\"Differentiate\",[x,n, x^n]);\n\n  Verify(f(10),(10!));\n\n  Retract(f,1);\n};\n\n";
        testString[2] = "/org/mathpiper/scripts4/functional/Apply.mpw";
        userFunctionsTestsMap.put("Apply",testString);

        testString = new String[3];
        testString[0] = "103";
        testString[1] = "\nBuiltinPrecisionSet(10);\nRetract(\"f\",1);\nRetract(\"f1\",1);\nf(x) := N(Abs(1/x-1));\nVerify(f(0), Infinity);\nNumericEqual(RoundToN(f(3),BuiltinPrecisionGet()), 0.6666666667,BuiltinPrecisionGet());\nNFunction(\"f1\", \"f\", [x]);\nVerify(f1(0), Undefined);\nNumericEqual(RoundToN(f1(3),BuiltinPrecisionGet()), 0.6666666667,BuiltinPrecisionGet());\nRetract(\"f\",1);\nRetract(\"f1\",1);\n";
        testString[2] = "/org/mathpiper/scripts4/functional/NFunction.mpw";
        userFunctionsTestsMap.put("NFunction",testString);

        testString = new String[3];
        testString[0] = "200";
        testString[1] = "\n\n// verify that unknown integrals don't simplify\nVerify(Integrate(x,a,b)Exp(Sin(x)),Integrate(x,a,b)Exp(Sin(x)));\nVerify(Integrate(x    )Exp(Sin(x)),Integrate(x    )Exp(Sin(x)));\n\n// Verify that Yacas cannot integrate these expressions.\n// Yacas needs to return the integration unevaluated, or\n// return a correct answer (if it happens to be able to do\n// these integrals in the future).\nTestNonIntegrable(_expr) <-- Verify(Type(expr) =? \"Integrate\",True);\n\n// The following two used to get the interpreter into an infinite\n// loop. Fixed in version 1.0.51\n// FIXED!!! TestNonIntegrable(Integrate(x)(x*Ln(x)));\nTestNonIntegrable(Integrate(x)Sin(Exp(x)));\nVerify(Integrate(x) x^(-1),Ln(x)); // Well done Jonathan! ;-)\nVerify(Integrate(x) 1/x,Ln(x) );\n\nVerify(Integrate(x) 1/x^2, -x^ -1 );\nVerify(Integrate(x) 6/x^2, (-6)*x^-1);\nVerify(Integrate(x) 3/Sin(x),3*Ln(1/Sin(x)-Cos(x)/Sin(x)) );\n\nVerify(Integrate(x) Ln(x), x*Ln(x)-x );\nVerify(Integrate(x) x^5000, x^5001/5001 );\nVerify(Integrate(x) 1/Tan(x), Ln(Sin(x)) );\nVerify(Integrate(x) 1/Cosh(x)^2, Tanh(x) );\nVerify(Integrate(x) 1/Sqrt(3-x^2), ArcSin(x/Sqrt(3)) );\nVerify(Integrate(x) Erf(x), x*Erf(x)+1/(Exp(x^2)*Sqrt(Pi)) );\nVerify(Integrate(x) Sin(x)/(2*y+4),(-Cos(x))/(2*y+4));\nVerify(Integrate(x, [logAbs -> True])1/x, Ln(Abs(x)));\nVerify(Integrate(x,a,b, [logAbs -> True])1/x, Ln(Abs(b))-Ln(Abs(a)));\n\n\nTestNonIntegrable(Integrate(x) x^(1/x));\nTestNonIntegrable(Integrate(x) x^(Sin(x)));\nTestNonIntegrable(Integrate(x) Exp(x^2));\nTestNonIntegrable(Integrate(x) Sin(x^2));\n\nTestMathPiper(Integrate(x,0,A)Sin(x),1 - Cos(A));\nTestMathPiper(Integrate(x,0,A)x^2,(A^3)/3);\nTestMathPiper(Integrate(x,0,A)Sin(B*x),1/B-Cos(A*B)/B);\nTestMathPiper(Integrate(x,0,A)(x^2+2*x+1)/(x+1),(A^2)/2+A);\nTestMathPiper(Integrate(x,0,A)(x+1)/(x^2+2*x+1),Ln(A+1));\n\n// Check that threaded integration works\nVerify((Integrate(x,0,1) [1,x*x,1+x])-[1,1/3,3/2],[0,0,0]);\n\n\n// Test MatchLinear: code heavily used with integration\nLocalSymbols(TestMatchLinearTrue,TestMatchLinearFalse) {\n\n  TestMatchLinearTrue(_var,_expr,_expected) <--\n  {\n    Local(a,b);\n    Verify(MatchLinear(var,expr),True);\n    a:=Simplify(Matcheda()-expected[1]);\n    b:=Simplify(Matchedb()-expected[2]);\n    `TestMathPiper(@a,0);\n    `TestMathPiper(@b,0);\n  };\n  TestMatchLinearFalse(_var,_expr) <--\n  {\n    Local(a,b);\n    Verify(MatchLinear(var,expr),False);\n  };\n\n  TestMatchLinearTrue(x,(R+1)*x+(T-1),[(R+1),(T-1)]);\n  TestMatchLinearTrue(x,x+T,[1,T]);\n  TestMatchLinearTrue(x,a*x+b,[a,b]);\n  TestMatchLinearFalse(x,Sin(x)*x+(T-1));\n  TestMatchLinearFalse(x,x+Sin(x));\n};\n\nVerify(Integrate(x) z^100, z^100*x );\nVerify(Integrate(x) x^(-1),Ln(x) );\n\nVerify( Integrate(x) x^5000, x^5001/5001 );\n\nVerify( Integrate(x) Sin(x)/2, (-Cos(x))/2 );\n\nVerify((Integrate(x,a,b)Cos(x)^2) - ((b-Sin((-2)*b)/2)/2-(a-Sin((-2)*a)/2)/2), 0);\n\n/* Bug #9 */\nVerify((Integrate(x,-1,1) 1/x), 0); /* or maybe Undefined? */\nVerify((Integrate(x,-1,1) 1/x^2), Infinity);\n\n/* Bug #18 */\n//Changed, see next line TestMathPiper(Integrate(x) x^(1/2), 2/3*x^(3/2));\nTestMathPiper(Integrate(x) x^(1/2), (2/3)*Sqrt(x)^(3));\n\n// This one ended in an infinite loop because 1 is an even function, and the indefinite integrator\n// then kept on calling itself because the left and right boundaries were equal to zero.\nVerify(Integrate(x,0,0)1,0);\n\n// This code verifies that if integrating over a zero domain, the result\n// is zero.\nVerify(Integrate(x,1,1)Sin(Exp(x^2)),0);\n\n";
        testString[2] = "/org/mathpiper/scripts4/integrate/Integrate.mpw";
        userFunctionsTestsMap.put("Integrate",testString);

        testString = new String[3];
        testString[0] = "48";
        testString[1] = "\n// generate no errors\nVerify(Error?(), False);\nVerify(Error?(\"testing\"), False);\nVerify(Assert(\"testing\") 1=?1, True);\nVerify(Error?(), False);\nVerify(Error?(\"testing\"), False);\nVerify(Assert(\"testing1234\", [1,2,3,4]) 1=?1, True);\nVerify(Error?(), False);\nVerify(Error?(\"testing\"), False);\nVerify(Error?(\"testing1234\"), False);\n\nVerify(PipeToString()DumpErrors(), \"\");\n\n// generate some errors\nVerify(Assert(\"testing\") 1=?0, False);\nVerify(Error?(), True);\nVerify(Error?(\"testing\"), True);\nVerify(Error?(\"testing1234\"), False);\nVerify(Assert(\"testing1234\", [1,2,3,4]) 1=?0, False);\nVerify(Error?(), True);\nVerify(Error?(\"testing\"), True);\nVerify(Error?(\"testing1234\"), True);\n\n// report errors\nVerify(PipeToString()DumpErrors(), \"Error: testing\nError: testing1234: [1, 2, 3, 4]\n\");\n\n// no more errors now\nVerify(Error?(), False);\nVerify(Error?(\"testing\"), False);\nVerify(Error?(\"testing1234\"), False);\n\n// generate some more errors\nVerify(Assert(\"testing\") 1=?0, False);\nVerify(Assert(\"testing1234\", [1,2,3,4]) 1=?0, False);\nVerify(GetError(\"testing1234567\"), False);\n\n// handle errors\nVerify(GetError(\"testing\"), True);\nVerify(Error?(), True);\nVerify(Error?(\"testing\"), True);\nVerify(Error?(\"testing1234\"), True);\n\nVerify(ClearError(\"testing\"), True);\nVerify(Error?(), True);\nVerify(Error?(\"testing\"), False);\nVerify(Error?(\"testing1234\"), True);\n// no more \"testing\" error\nVerify(ClearError(\"testing\"), False);\nVerify(Error?(), True);\nVerify(Error?(\"testing\"), False);\nVerify(Error?(\"testing1234\"), True);\n\nVerify(GetError(\"testing1234\"), [1,2,3,4]);\nVerify(Error?(), True);\nVerify(Error?(\"testing\"), False);\nVerify(Error?(\"testing1234\"), True);\n\nVerify(ClearError(\"testing1234\"), True);\nVerify(Error?(), False);\nVerify(Error?(\"testing\"), False);\nVerify(Error?(\"testing1234\"), False);\nVerify(ClearError(\"testing1234\"), False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/io/Error_.mpw";
        userFunctionsTestsMap.put("error_reporting",testString);

        testString = new String[3];
        testString[0] = "373";
        testString[1] = "\nVerify( Limit(x,Infinity) Sin(x), Undefined );\nVerify( Limit(x,Infinity) Cos(x), Undefined );\nVerify( Limit(x,Infinity) Tan(x), Undefined );\nVerify( Limit(x,Infinity) Gamma(x), Infinity );\nVerify( Limit(x,Infinity) Abs(x), Infinity );\nVerify( Limit(x,Infinity) x!, Infinity);\n\nVerify(Limit(x,Infinity) (-x^2+1)/(x+2),-Infinity);\nVerify(Limit(x,-Infinity)Exp(2*x),0);\nVerify(Limit(x,Infinity)(1+1/x)^x,Exp(1));\nVerify(Limit(x,Infinity)(1+2/x)^x,Exp(2));\nVerify(Limit(x,Infinity)(1+1/x)^(2*x),Exp(2));\nVerify(Limit(x,Infinity)-2*x,-Infinity);\nVerify(Limit(x,Infinity)(x^2+1)/(-x^3+1),0);\nVerify(Limit(x,0)1/x,Undefined);\nVerify(Limit(x,0,Right)1/x,Infinity);\nVerify(Limit(x,0,Left)1/x,-Infinity);\n\nVerify( Limit(n, Infinity) (n+1)/(2*n+3)*I, Complex(0,1/2) );\nVerify( Limit(x, Infinity) x*I, Complex(0,Infinity) ); //Changed Ayal: I didn't like the old definition\n\nVerify( Limit(n,Infinity) n*I^n, Undefined );\n\nVerify( Limit(x,0,Right) Ln(x)*Sin(x), 0 );\nKnownFailure( Limit(k,Infinity) ((k-phi)/k)^(k+1/2) =? Exp(-phi) );\n\n// tests adapted from mpreduce\n\nVerify(Limit(x,0)Sin(x)/x, 1);\nVerify(Limit(x,0)Sin(x)^2/x, 0);\nVerify(Limit(x,1)Sin(x)/x, Sin(1));\n/* This is actually a tricky one - for complex it should bring\n   infinity but for real it's undefined. BTW, reduce seems\n   to get it wrong */\nVerify(Limit(x,0)1/x, Undefined);\nVerify(Limit(x,0)(Sin(x)-x)/x^3, -1/6);\nVerify(Limit(x,Infinity)x*Sin(1/x), 1);\n/* reduce seems to get it wrong */\nVerify(Limit(x,0)Sin(x)/x^2, Undefined);\nVerify(Limit(x,Infinity)x^2*Sin(1/x), Infinity);\n\n// tests adapted from mpreduce\n\nVerify(Limit(x,2)x^2-6*x+4,-4);\nVerify(Limit(x,-1)(x+3)*(2*x-1)/(x^2+3*x-2), 3/2);\nVerify(Limit(h,0)(Sqrt(4+h)-2)/h, 1/4);\nVerify(Limit(x,4)(Sqrt(x)-2)/(4-x), -1/4);\nVerify(Limit(x,2)(x^2-4)/(x-2), 4);\nVerify(Limit(x,-1)1/(2*x-5), -1/7);\nVerify(Limit(x,1)Sqrt(x)/(x+1), 1/2);\nVerify(Limit(x,Infinity)(2*x+5)/(3*x-2), 2/3);\nVerify(Limit(x,1)(1/(x+3)-2/(3*x+5))/(x-1), 1/32);\nVerify(Limit(x,0)Sin(3*x)/x, 3);\nVerify(Limit(x,0)(1-Cos(x))/x^2, 1/2);\nVerify(Limit(x,0)(6*x-Sin(2*x))/(2*x+3*Sin(4*x)), 2/7);\nVerify(Limit(x,0)(1-2*Cos(x)+Cos(2*x))/x^2, -1);\nVerify(Simplify(Limit(x,0)(3*Sin(Pi*x) - Sin(3*Pi*x))/x^3), 4*Pi^3);\nVerify(Limit(x,0)(Cos(a*x)-Cos(b*x))/x^2, (-a^2 + b^2)/2);\nVerify(Limit(x,0)(Exp(x)-1)/x, 1);\nVerify(Limit(x,0)(a^x-b^x)/x, Ln(a) - Ln(b));\n\n\n\n// tests adapted from mpreduce\n\n\nVerify(Limit(x,0)Sinh(2*x)^2/Ln(1+x^2), 4);\n// The limit seems to hang mathpiper\n//Verify(Limit(x,Infinity)x^2*(Exp(1/x)-1)*(Ln(x+2)-Ln(x)),2);\n// another tricky problem with the result depending on the sign of\n// alpha; I'm not sure how we should deal with it\n//Limit(x,Infinity)x^alpha*Ln(x+1)^2/Ln(x);\n\n\n//Seems to hang mathpiper: Verify(Limit(x,0)(2*Cosh(x)-2-x^2)/Ln(1+x^2)^2, 1/12);\nVerify(Limit(x,0)(x*Sinh(x)-2+2*Cosh(x))/(x^4+2*x^2), 1);\nVerify(Limit(x,0)(2*Sinh(x)-Tanh(x))/(Exp(x)-1), 1);\n//This test passes, but it takes a long time to do so: Verify(Limit(x,0)x*Tanh(x)/(Sqrt(1-x^2)-1), -2);\nVerify(Limit(x,0)(2*Ln(1+x)+x^2-2*x)/x^3, 2/3);\n\nVerify(Limit(x,0)(Exp(5*x)-2*x)^(1/x), Exp(3));\nVerify(Limit(x,Infinity)Ln(Ln(x))/Ln(x)^2, 0);\n\n\n\n// tests adapted from mpreduce\n\nVerify(Limit(x,0)(Exp(x)-1)/x, 1);\nVerify(Limit(x,1)((1-x)/Ln(x))^2, 1);\nVerify(Limit(x,0)x/(Exp(x)-1), 1);\nVerify(Limit(x,0)x/Ln(x), 0);\nVerify(Limit(x,Infinity)Ln(1+x)/Ln(x), 1);\nVerify(Limit(x,Infinity)Ln(x)/Sqrt(x), 0);\nVerify(Limit(x,0,Right)Sqrt(x)/Sin(x), Infinity);\nVerify(Limit(x,0)x*Ln(x), 0);\nVerify(Limit(x,0)Ln(x)/Ln(2*x), 1);\nVerify(Limit(x,0)x*Ln(x)*(1+x), 0);\nVerify(Limit(x,Infinity)Ln(x)/x,0);\nVerify(Limit(x,Infinity)Ln(x)/Sqrt(x),0);\nVerify(Limit(x,Infinity)Ln(x), Infinity);\nVerify(Limit(x,0)Ln(x+1)/Sin(x), 1);\n//Seems to hang mathpiper: Verify(Limit(x,0)Ln(x+1/x)*Sin(x), 0);\nVerify(Limit(x,0)-Ln(1+x)*(x+2)/Sin(x), -2);\nVerify(Limit(x,Infinity)Ln(x+1)^2/Sqrt(x), 0);\nVerify(Limit(x,Infinity)(Ln(x+1)-Ln(x)), 0);\nVerify(Limit(x,Infinity)-Ln(x+1)^2/Ln(Ln(x)), -Infinity);\n\n\n/* Regression on bug reports from docs/bugs.txt */\n/* Bug #1 */\n/* Can't test: 'Limit(x,0)Differentiate(x,2)Sin(x)/x' never terminates */\n\n/* Bug #2 */\nKnownFailure((Limit(x,Infinity) x^n/Ln(x)) =? Infinity);\nKnownFailure((Limit(x,0,Right) x^(Ln(a)/(1+Ln(x)))) =? a);\nVerify((Limit(x,0) (x+1)^(Ln(a)/x)), a);\n/* Note paren's around bodied operators like Limit, D, Integrate;\n   otherwise it's parsed as Limit (... = ...) */\n\n/* Bug #5 */\n/* How can we test for this? */\n/* Bug says: Limit(n,Infinity) Sqrt(n+1)-Sqrt(n) floods stack */\n/* but 'MaxEvalDepth reached' exits Yacas, even inside ExceptionCatch */\n\n/* Bug #12 */\nKnownFailure((Limit(n, Infinity) n^5/2^n) =? 0);\n\n/* Bug #14 */\nVerify((Limit(x,Infinity) Zeta(x)), 1);\n// Actually, I changed the Factorial(x) to (x!)\nVerify((Limit(x,Infinity) (x!)), Infinity);\n\n\n\n\n";
        testString[2] = "/org/mathpiper/scripts4/limit/Limit.mpw";
        userFunctionsTestsMap.put("Limit",testString);

        testString = new String[3];
        testString[0] = "55";
        testString[1] = "\n// There was a bug, reported by Sebastian Ferraro, which caused the determinant\n// to return \"Undefined\" when one of the elements of the diagonal of a matrix\n// was zero. This was due to the numeric determinant algorithm applying\n// Gaussian elimination, but taking the elements on the diagonal as pivot points.\nVerify(Zero?(Determinant( [[1,-1,0,0],[0,0,-1,1],[1,0,0,1],[0,1,1,0]] )),True);\n\n";
        testString[2] = "/org/mathpiper/scripts4/linalg/Deteminant.mpw";
        userFunctionsTestsMap.put("Determinant",testString);

        testString = new String[3];
        testString[0] = "83";
        testString[1] = "\n//Dimensions (Tensor Rank).\n\nVerify(Dimensions(a),[]);\nVerify(Dimensions([]),[0]);\nVerify(Dimensions([a,b]),[2]);\nVerify(Dimensions([[]]),[1,0]);\nVerify(Dimensions([[a]]),[1,1]);\nVerify(Dimensions([[],a]),[2]);\nVerify(Dimensions([[a],b]),[2]);\nVerify(Dimensions([[],[]]),[2,0]);\nVerify(Dimensions([[],[[]]]),[2]);\nVerify(Dimensions([[a,b],[c]]),[2]);\nVerify(Dimensions([[a,b],[c,d]]),[2,2]);\nVerify(Dimensions([[a,b],[c,d],[e,f]]),[3,2]);\nVerify(Dimensions([[a,b,c],[d,e,f],[g,h,i]]),[3,3]);\nVerify(Dimensions([[a,b,c],[d,e,f]]),[2,3]);\nVerify(Dimensions([[[a,b]],[[c,d]]]), [2,1,2]);\nVerify(Dimensions([[[[a],[b]]],[[[c],d]]]),[2,1,2]);\nVerify(Dimensions([[[[[a,b]]]],[[[c,d]]]]),[2,1,1]);\nVerify(Dimensions([[[[[a,b]]]],[[[c],[d]]]]),[2,1]);\nVerify(Dimensions([[[]]]),[1,1,0]);\nVerify(Dimensions([[[a]]]),[1,1,1]);\nVerify(Dimensions([[[[a]]],[[[b]]]]),[2,1,1,1]);\nVerify(Dimensions([[[[a],[b]]],[[[c],[d]]]]),[2,1,2,1]);\nVerify(Dimensions([[[[a,b]]],[[[c,d]]]]),[2,1,1,2]);\nVerify(Dimensions([[[[a,b]],[[c,d]]]]),[1,2,1,2]);\nVerify(Dimensions([[[[[[a,b],[c]]]]],[[[d],[e,f,g]]]]), [2,1]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/linalg/Dimensions.mpw";
        userFunctionsTestsMap.put("Dimensions",testString);

        testString = new String[3];
        testString[0] = "109";
        testString[1] = "\n// vector . vector\nVerify(Dot([],[]),0);\nVerify(Dot([],a),Hold(Dot([],a)));\nVerify(Dot(a,[]),Hold(Dot(a,[])));\nVerify(Dot([a],[]),Hold(Dot([a],[])));\nVerify(Dot([],[a]),Hold(Dot([],[a])));\nVerify(Dot([a],[b]),a*b);\nVerify(Dot([a],[b,c]),Hold(Dot([a],[b,c])));\nVerify(Dot([a,b],[c]),Hold(Dot([a,b],[c])));\nVerify(Dot([a,b],[c,d]),a*c+b*d);\nVerify(Dot([a,b],[c,[d]]),Hold(Dot([a,b],[c,[d]])));\nVerify(Dot([a,[b]],[c,d]),Hold(Dot([a,[b]],[c,d])));\nVerify(Dot([a,b],[c,d,e]),Hold(Dot([a,b],[c,d,e])));\nVerify(Dot([a,b,c],[d,e]),Hold(Dot([a,b,c],[d,e])));\nVerify(Dot([1,2,3],[4,5,6]),32);\n\n// matrix . vector\nVerify(Dot([[]],[]),[0]);\nVerify(Dot([[]],[1]),Hold(Dot([[]],[1])));\nVerify(Dot([[],[]],[]),[0,0]);\nVerify(Dot([[a]],[b]),[a*b]);\nVerify(Dot([[a],[b]],[c]),[a*c,b*c]);\nVerify(Dot([[1],[2]],[2]),[2,4]);\nVerify(Dot([[1,2,3],[4,5,6]],[7,8,9]),[50,122]);\n\n// vector . matrix\nVerify(Dot([],[[]]),Hold(Dot([],[[]])));\nVerify(Dot([],[[],[]]),Hold(Dot([],[[],[]])));\nVerify(Dot([1],[[]]),Hold(Dot([1],[[]])));\nVerify(Dot([1],[[],[]]),Hold(Dot([1],[[],[]])));\nVerify(Dot([a,b],[[c],[d]]),[a*c+b*d]);\nVerify(Dot([1,2,3],[[4,5],[6,7],[8,9]]),[40,46]);\n\n// matrix . matrix\nVerify(Dot([[]],[[]]),Hold(Dot([[]],[[]])));\nVerify(Dot([[a]],[[]]),Hold(Dot([[a]],[[]])));\nVerify(Dot([[]],[[b]]),Hold(Dot([[]],[[b]])));\nVerify(Dot([[1,2],[3,4],[5,6]],[[1,2,3],[4,5,6]]),[[9,12,15],[19,26,33],[29,40,51]]);\nVerify(Dot([[1,2,3],[4,5,6]],[[1,2],[3,4],[5,6]]),[[22,28],[49,64]]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/linalg/Dot.mpw";
        userFunctionsTestsMap.put("Dot",testString);

        testString = new String[3];
        testString[0] = "66";
        testString[1] = "\n//Verify(MatrixPower(,),);\nVerify(MatrixPower(a,0),Hold(MatrixPower(a,0)));\nVerify(MatrixPower(a,n),Hold(MatrixPower(a,n)));\nVerify(MatrixPower([a],0),Hold(MatrixPower([a],0)));\nVerify(MatrixPower([a],n),Hold(MatrixPower([a],n)));\nVerify(MatrixPower([[a]],0),[[1]]);\nVerify(MatrixPower([[a]],1),[[a]]);\nVerify(MatrixPower([[a]],-1),[[1/a]]);\nVerify(MatrixPower([[a]],3/5),Hold(MatrixPower([[a]],3/5)));\nVerify(MatrixPower([[a]],10),[[a^10]]);\nVerify(MatrixPower([[a]],-10),[[1/a^10]]);\nVerify(MatrixPower([[a]],n),Hold(MatrixPower([[a]],n)));\n\nVerify(MatrixPower([[1,2],[3,4]],0),[[1,0],[0,1]]);\nVerify(MatrixPower([[1,2],[3,4]],1),[[1,2],[3,4]]);\nVerify(MatrixPower([[1,2],[3,4]],2),[[7,10],[15,22]]);\nVerify(MatrixPower([[1,2],[3,4]],3),[[37,54],[81,118]]);\nVerify(MatrixPower([[1,2],[3,4]],4),[[199,290],[435,634]]);\nVerify(MatrixPower([[1,2],[3,4]],5),[[1069,1558],[2337,3406]]);\nVerify(MatrixPower([[1,2],[3,4]],7),[[30853,44966],[67449,98302]]);\nVerify(MatrixPower([[1,2],[3,4]],13),[[741736909,1081027478],[1621541217,2363278126]]);\n\nVerify(MatrixPower([[1,2],[3,4]],-1),[[-2,1],[3/2,-1/2]]);\nVerify(MatrixPower([[1,2],[3,4]],-2),[[11/2,-5/2],[-15/4,7/4]]);\nVerify(MatrixPower([[1,2],[3,4]],-3),[[-59/4,27/4],[81/8,-37/8]]);\nVerify(MatrixPower([[1,2],[3,4]],-4),[[317/8,-145/8],[-435/16,199/16]]);\nVerify(MatrixPower([[1,2],[3,4]],-5),[[-1703/16,779/16],[2337/32,-1069/32]]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/linalg/MatrixPower.mpw";
        userFunctionsTestsMap.put("MatrixPower",testString);

        testString = new String[3];
        testString[0] = "61";
        testString[1] = "\nVerify(Outer([],[]),[]);\nVerify(Outer([[]],[]),Hold(Outer([[]],[])));\nVerify(Outer([],[[]]),Hold(Outer([],[[]])));\nVerify(Outer([[]],[[]]),Hold(Outer([[]],[[]])));\nVerify(Outer(a,b),Hold(Outer(a,b)));\nVerify(Outer([a],[b]),[[a*b]]);\nVerify(Outer([a,b],[c]),[[a*c],[b*c]]);\nVerify(Outer([a],[b,c]),[[a*b,a*c]]);\nVerify(Outer([a,b],[c,d,e]),[[a*c,a*d,a*e],[b*c,b*d,b*e]]);\nVerify(Outer([a,b,c],[d,e]),[[a*d,a*e],[b*d,b*e],[c*d,c*e]]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/linalg/Outer.mpw";
        userFunctionsTestsMap.put("Outer",testString);

        testString = new String[3];
        testString[0] = "62";
        testString[1] = "\nVerify(Trace(a),Hold(Trace(a)));\nVerify(Trace([]),0);\nVerify(Trace([a,b]),a+b);\nVerify(Trace([[]]),0);\nVerify(Trace([[a]]),a);\nVerify(Trace([[],a]),[]);\nVerify(Simplify(Trace([[a],b])-[a+b]),[0]);\nVerify(Trace([[],[]]),0);\nVerify(Trace([[],[[]]]),Hold([]+[[]]));     // bug in list addition?\nVerify(Trace([[a,b],[c]]),Hold([a,b]+[c])); // bug in list addition?\nVerify(Trace([[a,b],[c,d]]),a+d);\nVerify(Trace([[a,b],[c,d],[e,f]]),a+d);\nVerify(Trace([[a,b,c],[d,e,f],[g,h,i]]),a+e+i);\nVerify(Trace([[a,b,c],[d,e,f]]),a+e);\nVerify(Trace([[[a,b]],[[c,d]]]),a);\nVerify(Trace([[[[a],[b]]],[[[c],d]]]),[a]);\nVerify(Trace([[[[[a,b]]]],[[[c,d]]]]),[[a,b]]);\nVerify(Trace([[[[[a,b]]]],[[[c],[d]]]]),[[[a,b]]]);\nVerify(Trace([[[]]]),0);\nVerify(Trace([[[a]]]),a);\nVerify(Trace([[[[a]]],[[[b]]]]),a);\nVerify(Trace([[[[a],[b]]],[[[c],[d]]]]),a);\nVerify(Trace([[[[a,b]]],[[[c,d]]]]),a);\nVerify(Trace([[[[a,b]],[[c,d]]]]),a);\nVerify(Trace([[[[[[a,b],[c]]]]],[[[d],[e,f,g]]]]),[[[[a, b], [c]]]]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/linalg/Trace.mpw";
        userFunctionsTestsMap.put("Trace",testString);

        testString = new String[3];
        testString[0] = "1";
        testString[1] = "\nTesting(\"LeviCivita\");\nVerify(LeviCivita([1,2,3]),1);\nVerify(LeviCivita([2,1,3]),-1);\nVerify(LeviCivita([1,1,3]),0);\n\nTesting(\"VectorProducts\");\n{\n  Local(l,m,n);\n  l:=[1,0,0];\n  m:=[0,1,0];\n  n:=[0,0,1];\n\n  Verify(l X m, [0,0,1]);\n  Verify(m X n, [1,0,0]);\n  Verify(n X l, [0,1,0]);\n  Verify(l X n, [0,-1,0]);\n\n  Verify(Dot(l, m), 0);\n  Verify(Dot(m, n), 0);\n  Verify(Dot(n, l), 0);\n\n  Verify(Dot(l, l), 1);\n};\n\n\n{\n  Local(a,b);\n\n/* Strangeness: change variable below into a, and the crossproducts\n * later on fail!\n */\n  a:=[1,2,3];\n  b:=[3,1,5];\n  Verify( Dot(a, b) , 20);\n  Verify(CrossProduct([1,2,3] , [4,2,5]) , [4,7,-6]);\n};\nVerify(aa,Hold(aa));\n\n{\n  Local(a,b);\n  NextTest(\"Inproduct\");\n  a:=[1,2,3];\n  b:=[3,1,5];\n  Verify( Dot(a, b) , 20);\n};\n\nVerify(CrossProduct([1,2,3] , [4,2,5]) , [4,7,-6]);\nVerify([1,2,3] X [4,2,5],[4,7,-6]);\nUnassign(a,b);\n\nNextTest(\"Identity matrices\");\nVerify(Identity(4),\n   [ [1,  0,  0,  0] ,\n     [0,  1,  0,  0] ,\n     [0,  0,  1,  0] ,\n     [0,  0,  0,  1] ]);\n\n\nNextTest(\"Check linear algebra\");\n/* Normalize */\nTesting(\"Normalize\");\nVerify(Normalize([3,4]),[3/5,4/5]);\n/* DiagonalMatrix */\nTesting(\"DiagonalMatrix\");\nVerify(DiagonalMatrix([2,3,4]),[[2,0,0],[0,3,0],[0,0,4]]);\n/* ZeroMatrix */\nTesting(\"ZeroMatrix\");\nVerify(ZeroMatrix(2,3),[[0,0,0],[0,0,0]]);\n/* Transpose */\nTesting(\"Transpose\");\nVerify(Transpose([[a,b],[c,d]]),[[a,c],[b,d]]);\n/* Determinant */\nTesting(\"Determinant\");\nVerify(Determinant([[2,3],[3,1]]),-7);\nVerify( Determinant(ToeplitzMatrix(1 .. 10)), -2816 );\n// check that Determinant gives correct symbolic result\nTestMathPiper(Determinant([[a,b],[c,d]]),a*d-b*c);\n\n{\n  Local(ll);\n  ll:=[ [1,2,3],\n        [2,-1,4],\n        [3,4,3]\n      ];\n  /* CoFactor */\n  Testing(\"CoFactor\");\n  Verify(N(CoFactor(ll,1,2)),6);\n  /* Minor */\n  Testing(\"Minor\");\n  Verify(N(Minor(ll,1,2)),-6);\n  /* Inverse */\n  Testing(\"Inverse\");\n  Verify(Inverse(ll)*ll,Identity(3));\n  /* SolveMatrix */\n  Testing(\"SolveMatrix\");\n  Verify(ll*SolveMatrix(ll,[1,2,3]),[1,2,3]);\n  /* Trace */\n  Testing(\"Trace\");\n  Verify(Trace(ll),1-1+3);\n  /* Vector? */\n  Verify(List?(ll),True);\n  Verify(List?([1,2,3]),True);\n  /* Matrix? */\n  Verify(Matrix?(ll),True);\n  Unassign(ll);\n};\n\n{\n  Local(A);\n  Verify( Symmetric?(Identity(10)), True );\n  Verify( Orthogonal?(2*Identity(10)), False );\n  A := [[1,2,2],[2,1,-2],[-2,2,-1]];\n  Verify( Orthogonal?(A/3), True );\n  Verify( Symmetric?(Identity(10)), True );\n  Verify( Symmetric?([[1]]),True );\n  A := [[1,0,0,0,1],[0,2,0,0,0],[0,0,3,0,0],[0,0,0,4,0],[1,0,0,0,5]];\n  Verify( Symmetric?(A),True );\n  A := [[0,2,0,0,1],[0,0,3,0,0],[0,0,0,4,0],[1,0,0,0,5]];\n  Verify( Symmetric?(A),False);\n  A := [[0,-1],[1,0]];\n  Verify( SkewSymmetric?(A), True );\n  Verify( SkewSymmetric?(Identity(10)), False );\n  Verify( SkewSymmetric?(ZeroMatrix(10,10)), True );\n  Verify( Idempotent?(Identity(20)), True );\n  Verify( Idempotent?(ZeroMatrix(10,10)), True );\n};\n\nVerify( VandermondeMatrix([1,2,3,4]),[[1,1,1,1],[1,2,3,4],[1,4,9,16],[1,8,27,64]]);\n\nVerify( JacobianMatrix( [x^4*y,Cos(y)], [ x, y]), [[4*x^3*y,x^4],[0,-Sin(y)]] );\n\nVerify( WronskianMatrix( [Sin(x),Cos(x)], x) , [[Sin(x),Cos(x)],[Cos(x),-Sin(x)]] );\n\nVerify( Determinant(HilbertMatrix(5)), 1/266716800000 );\n\nVerify( HilbertMatrix(6)*HilbertInverseMatrix(6), Identity(6) );\n\nVerify( FrobeniusNorm([[1,2],[3,4]]), Sqrt(30) );\n\nVerify( Norm([1,2,3]), Sqrt(14) );\n\nVerify( OrthogonalBasis([[1,1,0],[2,0,1],[2,2,1]]) , [[1,1,0],[1,-1,1],[-1/3,1/3,2/3]] );\nVerify( OrthogonalBasis([[1,0,1,0],[1,1,1,0],[0,1,0,1]]), [[1,0,1,0],[0,1,0,0],[0,0,0,1]] );\nVerify( OrthonormalBasis([[1,0,1,0],[1,1,1,0],[0,1,0,1]]),\n        [[Sqrt(1/2),0,Sqrt(1/2),0],[0,1,0,0],[0,0,0,1]] );\nVerify( OrthonormalBasis([[1,1,1],[0,1,1],[0,0,1]]),\n        [[Sqrt(1/3),Sqrt(1/3),Sqrt(1/3)],\n        [-Sqrt(2/3),Sqrt(1/6),Sqrt(1/6)],\n        [0,-Sqrt(1/2),Sqrt(1/2)]] );\n\n{\n  Local(A,b);\n  A:=[[1,2,4],[1,3,9],[1,4,16]];\n  b:=[2,4,7];\n  Verify( MatrixSolve(A,b) , [1,(-1)/2,1/2] );\n  A:=[[2,4,-2,-2],[1,2,4,-3],[-3,-3,8,-2],[-1,1,6,-3]];\n  b:=[-4,5,7,7];\n  Verify( MatrixSolve(A,b), [1,2,3,4] );\n};\n\n{\n  Local(A,R);\n  A:=[[4,-2,4,2],[-2,10,-2,-7],[4,-2,8,4],[2,-7,4,7]];\n  R:=Cholesky(A);\n  Verify( R, [[2,-1,2,1],[0,3,0,-2],[0,0,2,1],[0,0,0,1]] );\n  Verify( A, Transpose(R)*R );\n};\n\n{\n  Local(A,L,U);\n  A:=[[2,1,1],[2,2,-1],[4,-1,6]];\n  [L,U] := LU(A);\n  Verify( L*U, A );\n};\n\n";
        testString[2] = "/org/mathpiper/scripts4/linalg/linear_algebra_tests.mpw";
        userFunctionsTestsMap.put("linear_algebra",testString);

        testString = new String[3];
        testString[0] = "58";
        testString[1] = "\nVerify(MapSingle(\"!\",[1,2,3,4]),[1,2,6,24]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/lists/MapSingle.mpw";
        userFunctionsTestsMap.put("MapSingle",testString);

        testString = new String[3];
        testString[0] = "43";
        testString[1] = "\n/* Reverse and FlatCopy (and some friends) would segfault in the past if passed a string as argument.\n * I am not opposed to overloading these functions to also work on strings per se, but for now just\n * check that they return an error in stead of segfaulting.\n */\nVerify(ExceptionCatch(Reverse(\"abc\"),\"Exception\"), \"Exception\");\n\n";
        testString[2] = "/org/mathpiper/scripts4/lists/Reverse.mpw";
        userFunctionsTestsMap.put("Reverse",testString);

        testString = new String[3];
        testString[0] = "1";
        testString[1] = "\nVerify(Intersection([aa,b,c],[b,c,d]),[b,c]);\nVerify(Union([aa,b,c],[b,c,d]),[aa,b,c,d]);\nVerify(Difference([aa,b,c],[b,c,d]),[aa]);\n\nNextTest(\"VarList\");\nVerify(VarList(x^2+y^3) , [x , y]);\nVerify(List(1,2,3),[1 , 2 , 3]);\n\nTesting(\"BubbleSort\");\nVerify(BubbleSort([2,3,1],\"<?\"),[1,2,3]);\nVerify(BubbleSort([2,3,1],\">?\"),[3,2,1]);\n\nTesting(\"HeapSort\");\nVerify(HeapSort([2,3,1],\"<?\"),[1,2,3]);\nVerify(HeapSort([2,1,3],\">?\"),[3,2,1]);\nVerify(HeapSort([7,3,1,2,6],\"<?\"),[1,2,3,6,7]);\nVerify(HeapSort([6,7,1,3,2],\">?\"),[7,6,3,2,1]);\n\nVerify(Type(Cos(x)),\"Cos\");\nVerify(ArgumentsCount(Cos(x)),1);\nVerify(Contains?([a,b,c],b),True);\nVerify(Contains?([a,b,c],d),False);\n\nVerify(Append([a,b,c],d),[a,b,c,d]);\nVerify(RemoveDuplicates([a,b,b,c]),[a,b,c]);\nVerify(Count([a,b,b,c],b),2);\nVerify(VarList(x*Cos(x)),[x]);\n\n\n{\n  Local(l);\n  l:=[1,2,3];\n  DestructiveDelete(l,1);\n  Verify(l,[2,3]);\n  DestructiveInsert(l,1,1);\n  Verify(l,[1,2,3]);\n  l[1] := 2;\n  Verify(l,[2,2,3]);\n  l[1] := 1;\n  DestructiveDelete(l,3);\n  Verify(l,[1,2]);\n  DestructiveInsert(l,3,3);\n  Verify(l,[1,2,3]);\n  DestructiveDelete(FlatCopy(l),1);\n  Verify(l,[1,2,3]);\n};\n\nVerify(Table(i!,i,1,4,1),[1,2,6,24]);\nVerify(PermutationsList([a,b,c]),[[a,b,c],[a,c,b],[c,a,b],[b,a,c],[b,c,a],[c,b,a]]);\n\nTesting(\"ListOperations\");\nVerify(First([a,b,c]),a);\nVerify(Rest([a,b,c]),[b,c]);\nVerify(DestructiveReverse([a,b,c]),[c,b,a]);\nVerify(ListToFunction([a,b,c]),Hold(a(b,c)));\nVerify(FunctionToList(Hold(a(b,c))),[a,b,c]);\n\nVerify(Delete([a,b,c],2),[a,c]);\nVerify(Insert([a,c],2,b),[a,b,c]);\n\nTesting(\"Length\");\nVerify(Length([a,b]),2);\nVerify(Length([]),0);\n\nTesting(\"Nth\");\nVerify(Nth([a,b],1),a);\nVerify([a,b,c][2],b);\n\nTesting(\"Concat\");\nVerify(Concat([a,b],[c,d]),[a,b,c,d]);\n//This is simply not true!!! Verify(Hold(Concat([a,b],[c,d])),Concat([a,b],[c,d]));\n\n\nTesting(\"Binary searching\");\nVerify(BSearch(100,[[n],n^2-15]), -1);\nVerify(BSearch(100,[[n],n^2-16]), 4);\nVerify(BSearch(100,[[n],n^2-100002]), -1);\nVerify(BSearch(100,[[n],n^2-0]), -1);\nVerify(FindIsq(100,[[n],n^2-15]), 3);\nVerify(FindIsq(100,[[n],n^2-16]), 4);\nVerify(FindIsq(100,[[n],n^2-100002]), 100);\nVerify(FindIsq(100,[[n],n^2-0]), 1);\n\nVerify(Difference(FuncList(a*b/c*d), [*,/]), []);\nVerify(Difference(FuncListArith(0*x*Sin(a/b)*Ln(Cos(y-z)+Sin(a))), [*,Ln,Sin]), []);\nVerify(Difference(VarListArith(x+a*y^2-1), [x,a,y^2]), []);\n\nVerify(Difference(FuncList(CFormable?({i:=0;While(i<?10){i++; a--; a:=a+i; [];};})), [CFormable?,Prog,:=,While,<?,++,--,ToAtom(\"+\"),List]), []);\nVerify(FuncList([1,2,3]),[List]);\nVerify(FuncList([[],[]]),[List]);\nVerify(FuncList([]),[List]);\n\nTesting(\"AssocDelete\");\n{\n  Local(hash);\n  hash:=[[\"A\",1],[\"A\",2],[\"B\",3],[\"B\",4]];\n  AssocDelete(hash,[\"B\",3]);\n  Verify(hash, [[\"A\",1],[\"A\",2],[\"B\",4]]);\n  Verify(AssocDelete(hash,\"A\"),True);\n  Verify(hash, [[\"A\",2],[\"B\",4]]);\n  Verify(AssocDelete(hash,\"C\"),False);\n  Verify(hash, [[\"A\",2],[\"B\",4]]);\n  AssocDelete(hash,\"A\");\n  Verify(hash, [[\"B\",4]]);\n  AssocDelete(hash, [\"A\",2]);\n  AssocDelete(hash,\"A\");\n  Verify(hash, [[\"B\",4]]);\n  Verify(AssocDelete(hash,\"B\"),True);\n  Verify(hash, []);\n  Verify(AssocDelete(hash,\"A\"),False);\n  Verify(hash, []);\n};\nTesting(\"-- Arithmetic Operations\");\nVerify(1+[3,4],[4,5]);\nVerify([3,4]+1,[4,5]);\nVerify([1]+[3,4],Hold([1]+[3,4]));\nVerify([3,4]+[1],Hold([3,4]+[1]));\nVerify([1,2]+[3,4],[4,6]);\nVerify(1-[3,4],[-2,-3]);\nVerify([3,4]-1,[2,3]);\nVerify([1]-[3,4],Hold([1]-[3,4]));\nVerify([3,4]-[1],Hold([3,4]-[1]));\nVerify([1,2]-[3,4],[-2,-2]);\nVerify(2*[3,4],[6,8]);\nVerify([3,4]*2,[6,8]);\nVerify([2]*[3,4],Hold([2]*[3,4]));\nVerify([3,4]*[2],Hold([3,4]*[2]));\nVerify([1,2]*[3,4],[3,8]);\nVerify(2/[3,4],[2/3,1/2]);\nVerify([3,4]/2,[3/2,2]);\nVerify([2]/[3,4],Hold([2]/[3,4]));\nVerify([3,4]/[2],Hold([3,4]/[2]));\nVerify([1,2]/[3,4],[1/3,1/2]);\nVerify(2^[3,4],[8,16]);\nVerify([3,4]^2,[9,16]);\nVerify([2]^[3,4],Hold([2]^[3,4]));\nVerify([3,4]^[2],Hold([3,4]^[2]));\nVerify([1,2]^[3,4],[1,16]);\n\n// non-destructive Reverse operation\n{\n  Local(lst,revlst);\n  lst:=[a,b,c,13,19];\n  revlst:=Reverse(lst);\n  Verify(revlst,[19,13,c,b,a]);\n  Verify(lst,[a,b,c,13,19]);\n};\nVerify(Bound?(lst),False);\nVerify(Bound?(revlst),False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/lists/lists_tests.mpw";
        userFunctionsTestsMap.put("lists",testString);

        testString = new String[3];
        testString[0] = "72";
        testString[1] = "\nLocalSymbols(st)\n{\n  st:=NewStack();\n  Verify(IsOnStack(st,\"c\"),False);\n  PushStackFrame(st,fenced);\n    AddToStack(st,\"a\");\n    AddToStack(st,\"b\");\n    Verify(IsOnStack(st,\"a\"),True);\n    Verify(IsOnStack(st,\"c\"),False);\n    Verify(FindOnStack(st,\"a\"),[]);\n    FindOnStack(st,\"b\")[\"set\"]:=True;\n    Verify(FindOnStack(st,\"b\"),[[\"set\",True]]);\n    PushStackFrame(st,unfenced);\n      AddToStack(st,\"c\");\n      Verify(IsOnStack(st,\"c\"),True);\n      Verify(IsOnStack(st,\"a\"),True);\n    PopStackFrame(st);\n\n    PushStackFrame(st,fenced);\n      AddToStack(st,\"c\");\n      Verify(IsOnStack(st,\"c\"),True);\n      Verify(IsOnStack(st,\"a\"),False);\n    PopStackFrame(st);\n\n  PopStackFrame(st);\n  Verify(StackDepth(st),0);\n};\n\n";
        testString[2] = "/org/mathpiper/scripts4/lists/scopestack/scopestack.mpw";
        userFunctionsTestsMap.put("scopestack",testString);

        testString = new String[3];
        testString[0] = "110";
        testString[1] = "\nRulebaseHoldArguments(\"sin\", [x]);\nVerify(a[2]*Sin(x)/:[Sin(_x) <- sin(x)],a[2]*sin(x));\nRetract(\"sin\", 1);\n\n";
        testString[2] = "/org/mathpiper/scripts4/localrules/slash_colon_operator.mpw";
        userFunctionsTestsMap.put("/:",testString);

        testString = new String[3];
        testString[0] = "101";
        testString[1] = "\nNextTest(\"CNF\");\n\n\n/*\n    The main point is that CNF should return an answer in CNF, that is,\n    as a conjunction of disjuncts.\n*/\nVerify(CNF(A And? A),      A);\nVerify(CNF(A And? True),   A);\n\n\nVerify(CNF(A And? False),  False);\nVerify(CNF(A Or?  True),   True);\nVerify(CNF(A Or?  False),  A);\nVerify(CNF(A Or?  Not? A),  True);\nVerify(CNF(A And? Not? A),  False);\n\n\nVerify(CNF((A And? B) Or? (A And? B)),             A And? B);\nVerify(CNF(A Or? (A And? B)),                     A And?(A Or? B));\nVerify(CNF((A  Implies?  B) And? A),                     (Not? A Or? B)And? A);\nVerify(CNF((A And? B) And? A),                    (A And? B) And? A);\nVerify(CNF(Not? (A And? B) And? A),                (Not? A Or? Not? B) And? A);\n\n";
        testString[2] = "/org/mathpiper/scripts4/logic/CNF.mpw";
        userFunctionsTestsMap.put("CNF",testString);

        testString = new String[3];
        testString[0] = "124";
        testString[1] = "\n\nNextTest(\"Propositional logic theorem prover: CanProve\");\n\nVerify(CanProve(( (a Implies? b) And? (b Implies? c)  Implies?  (a Implies? c) )),True);\nVerify(CanProve((((a Implies? b) And? (b Implies? c)) Implies?  (a Implies? c) )),True);\nVerify(CanProve(( (a Implies? b) And?((b Implies? c)  Implies?  (a Implies? c)))),((Not? a Or? b)And?(Not? a Or? (b Or? c))) );\n//KnownFailure(BadOutput + WhenPreviousLine + IsUncommented);\n//And *my* previous line (the KnownFailure) aborts.  (witnessed by no report from next line).\nVerify(CanProve( True ),True);\n\nVerify(CanProve(a Or? Not? a)                   ,True);\nVerify(CanProve(True Or? a)                 ,True);\nVerify(CanProve(False Or? a)                ,a   );\nVerify(CanProve(a And? Not? a)                   ,False);\nVerify(CanProve(a Or? b Or? (a And? b))               ,a Or? b  );\n\n/* Two theorems from the Pure Implicational Calculus (PIC), in which the\n * only operator is [material] implication.  From the first, all other\n * theorems in PIC can be proved using only the two transformation rules:\n * 1. Rule of substitution.  Uniform replacement in theorems yields theorems.\n * 2. Rule of detachment, or modus ponens.  If 'a' and 'a Implies? b' are theorems, then 'b' is a theorem.\n *\n * 1. Lukasiewicz, Jan, \"The Shortest Axiom of the Implicational Calculus\n *    of Propositions,\" Proceedings of the Royal Irish Academy, vol. 52,\n *    Sec. A, No. 3 (1948).  [ Can you say \"Polish Notation\"? ]\n * 2. Meredith, David, \"On a Property of Certain Propositional Formulae,\"\n *    Notre Dame Journal of Formal Logic, vol. XIV, No. 1, January 1973.\n */\nVerify(CanProve(        /* 1. CCCpqrCCrpCsp */\n     ((p Implies? q)  Implies?  r)  Implies?  ((r Implies? p)  Implies?  (s Implies? p))\n     ), True);\nVerify(CanProve(        /* 2. CCpCqrCqCpr */\n     (p  Implies?  (q Implies? r))  Implies?  (q  Implies?  (p Implies? r))\n     ), True);\n\n\nVerify(CanProve((A Or? B) And? Not? A),            B And? Not? A);\nVerify(CanProve((A Or? B) And? (Not? A Or? C)),     (A Or? B) And? (C Or? Not? A));\nVerify(CanProve((B Or? A) And? (Not? A Or? C)),     (A Or? B) And? (C Or? Not? A));\nVerify(CanProve( A And? (A Or? B Or? C)),       A);\nVerify(CanProve( A And? (Not? A Or? B Or? C)),  A And? (B Or? C));\n\n// this is a test of contradication, A==3 should kick A==2 out as they're contradictory\nVerify(CanProve( A==3 And? (A==2 Or? B Or? C)),  A-3==0 And? (B Or? C));\n//TODO Verify(CanProve( A==3 And? (A<?2  Or? B Or? C)),  A-3==0 And? (B Or? C));\n//TODO Verify(CanProve( A==3 And? (A>?2  Or? B Or? C)),  (A-3==0) And? (((A-2) >? 0) Or? B Or? C));\n\nVerify(CanProve(Not?(Not? (p_2-NULL==0))Or? Not?(p_2-NULL==0)), True);\n\nLogicVerify(CanProve(P Or? (Not? P And? Not? Q)),P Or? Not? Q);\n\nLogicVerify(CanProve(P Or? (Not? P And? Not? Q)),P Or? Not? Q);\nLogicVerify(CanProve(A>?0 And? A<=?0),False);\nLogicVerify(CanProve(A>?0 Or? A<=?0),True);\n\n";
        testString[2] = "/org/mathpiper/scripts4/logic/CanProve.mpw";
        userFunctionsTestsMap.put("CanProve",testString);

        testString = new String[3];
        testString[0] = "47";
        testString[1] = "\n// Actually, the following Groebner test is just to check that the program doesn't crash on this,\n// more than on the exact result (which is hopefully correct also ;-) )\nVerify(Groebner([x*(y-1),y*(x-1)]),[x*y-x,x*y-y,y-x,y^2-y]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/multivar/Groebner.mpw";
        userFunctionsTestsMap.put("Groebner",testString);

        testString = new String[3];
        testString[0] = "188";
        testString[1] = "\nNextTest(\"Test arithmetic: NormalForm\");\n\nTestMathPiper(NormalForm(MM((x+y)^5)),y^5+5*x*y^4+10*x^2*y^3+10*x^3*y^2+5*x^4*y+x^5);\n\n";
        testString[2] = "/org/mathpiper/scripts4/multivar/sparsenomial/sparsenomial.mpw";
        userFunctionsTestsMap.put("NormalForm",testString);

        testString = new String[3];
        testString[0] = "39";
        testString[1] = "\nVerify( AmicablePair?(200958394875 ,209194708485 ), True );\nVerify( AmicablePair?(220,284),True );\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/AmicablePair_.mpw";
        userFunctionsTestsMap.put("AmicablePair?",testString);

        testString = new String[3];
        testString[0] = "66";
        testString[1] = "\nVerify( CarmichaelNumber?( [561,1105,1729,2465,2821,6601,8911] ),[True,True,True,True,True,True,True] );\nVerify( CarmichaelNumber?( [0,1,2,1727,2463,2823,6603] ),[False,False,False,False,False,False,False] );\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/CarmichaelNumber_.mpw";
        userFunctionsTestsMap.put("CarmichaelNumber?",testString);

        testString = new String[3];
        testString[0] = "40";
        testString[1] = "\nVerify( CatalanNumber(6), 132 );\nVerify( CatalanNumber(10), 16796 );\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/CatalanNumber.mpw";
        userFunctionsTestsMap.put("CatalanNumber",testString);

        testString = new String[3];
        testString[0] = "43";
        testString[1] = "\nVerify( Composite?(100), True );\nVerify( Composite?(1), False );\nVerify( Composite?(37), False );\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/Composite_.mpw";
        userFunctionsTestsMap.put("Composite?",testString);

        testString = new String[3];
        testString[0] = "42";
        testString[1] = "\nVerify( Coprime?(11,13), True );\nVerify( Coprime?(1 .. 10), False );\nVerify( Coprime?([9,40]), True );\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/Coprime_.mpw";
        userFunctionsTestsMap.put("Coprime?",testString);

        testString = new String[3];
        testString[0] = "12";
        testString[1] = "\nVerify( DigitalRoot(18), 9 );\nVerify( DigitalRoot(15), 6 );\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/DigitalRoot.mpw";
        userFunctionsTestsMap.put("DigitalRoot",testString);

        testString = new String[3];
        testString[0] = "54";
        testString[1] = "\nVerify( Divisors(180), 18 );\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/Divisors.mpw";
        userFunctionsTestsMap.put("Divisors",testString);

        testString = new String[3];
        testString[0] = "53";
        testString[1] = "\nVerify(HarmonicNumber(5), 137/60 );\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/HarmonicNumber.mpw";
        userFunctionsTestsMap.put("HarmonicNumber",testString);

        testString = new String[3];
        testString[0] = "81";
        testString[1] = "\nVerify(IntLog(23^45, 67), 33);\nVerify(IntLog(1, 67), 0);\nVerify(IntLog(2, 67), 0);\nVerify(IntLog(0, 67), 0);\nVerify(IntLog(1, 1), Undefined);\nVerify(IntLog(2, 1), Undefined);\nVerify(IntLog(256^8, 4), 32);\nVerify(IntLog(256^8-1, 4), 31);\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/IntLog.mpw";
        userFunctionsTestsMap.put("IntLog",testString);

        testString = new String[3];
        testString[0] = "111";
        testString[1] = "\nVerify(IntNthRoot(65537^33, 11), 281487861809153);\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/IntNthRoot.mpw";
        userFunctionsTestsMap.put("IntNthRoot",testString);

        testString = new String[3];
        testString[0] = "59";
        testString[1] = "\nVerify( IrregularPrime?(37), True );\nVerify( IrregularPrime?(59), True );\nVerify( IrregularPrime?(1), False );\nVerify( IrregularPrime?(11), False );\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/IrregularPrime_.mpw";
        userFunctionsTestsMap.put("IrregularPrime?",testString);

        testString = new String[3];
        testString[0] = "45";
        testString[1] = "\nVerify(NextPrime(65537), 65539);\nVerify(NextPrime(97192831),97192841);\nVerify(NextPrime(14987234876128361),14987234876128369);\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/NextPrime.mpw";
        userFunctionsTestsMap.put("NextPrime",testString);

        testString = new String[3];
        testString[0] = "185";
        testString[1] = "\n// you need to use ListToFunction for this one as -1 is actually -(1), eg. a unary function (minus)\n// applied to a positive integer (1). ListToFunction evaluates its arguments, resulting in a negative\n// integer (-1).\nVerify(NthRoot(-1,2),ListToFunction([NthRoot,-1,2]));\n\nVerify(NthRoot(2,1),Hold(NthRoot(2,1)));\nVerify(NthRoot(2,2),[1,2]);\nVerify(NthRoot(12,2),[2,3]);\nVerify(NthRoot(12,3),[1,12]);\nVerify(NthRoot(27,3),[3,1]);\nVerify(NthRoot(17*13,2),[1,17*13]);\nVerify(NthRoot(17*17*13,2),[17,13]);\nVerify(NthRoot(17*17*17*13,2),[17,17*13]);\nVerify(NthRoot(17*17*17*13,3),[17,13]);\nVerify(NthRoot(17*17*17*17*13*13,2),[17*17*13,1]);\nVerify(NthRoot(17*17*17*17*13*13,3),[17,17*13*13]);\nVerify(NthRoot(17*17*17*17*13*13,4),[17,13*13]);\nVerify(NthRoot(17*17*17*17*13*13,5),[1,17*17*17*17*13*13]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/NthRoot.mpw";
        userFunctionsTestsMap.put("NthRoot",testString);

        testString = new String[3];
        testString[0] = "11";
        testString[1] = "\nVerify(Perfect?(2305843008139952128), True );\nVerify(Perfect?(137438691328),True );\nVerify(Perfect?(234325),False );\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/Perfect_.mpw";
        userFunctionsTestsMap.put("Perfect?",testString);

        testString = new String[3];
        testString[0] = "69";
        testString[1] = "\nVerify(Prime?(65539),True);\nVerify(Prime?(0),False);\nVerify(Prime?(-1),False);\nVerify(Prime?(1),False);\nVerify(Prime?(2),True);\nVerify(Prime?(3),True);\nVerify(Prime?(4),False);\nVerify(Prime?(5),True);\nVerify(Prime?(6),False);\nVerify(Prime?(7),True);\nVerify(Prime?(-60000000000),False);\nVerify(Prime?(6.1),False); \n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/Prime_.mpw";
        userFunctionsTestsMap.put("Prime?",testString);

        testString = new String[3];
        testString[0] = "16";
        testString[1] = "\nVerify(Repunit(3), 111 );\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/Repunit.mpw";
        userFunctionsTestsMap.put("Repunit",testString);

        testString = new String[3];
        testString[0] = "64";
        testString[1] = "\nVerify(SmallPrime?(137),True);\nVerify(SmallPrime?(138),False);\nVerify(SmallPrime?(65537),True);\nVerify(SmallPrime?(65539),False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/SmallPrime_.mpw";
        userFunctionsTestsMap.put("SmallPrime?",testString);

        testString = new String[3];
        testString[0] = "38";
        testString[1] = "\nVerify( TwinPrime?(71), True );\nVerify( TwinPrime?(1), False );\nVerify( TwinPrime?(22), False );\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/TwinPrime_.mpw";
        userFunctionsTestsMap.put("TwinPrime?",testString);

        testString = new String[3];
        testString[0] = "121";
        testString[1] = "\nNextTest(\"Gaussian Integers\");\n\n/* TestGaussianFactors: test if Gaussian Factors Really works!\nComputes in r the product of the factors, and checks if each\none is a Gaussian prime and if r is associated to z (i.e. if r/z\nis a Gaussian Unit */\nTestGaussianFactors(z_GaussianInteger?) <--\n{\n Local(r,gfactors,Ok);\n// Echo(\"TestGaussianFactors: factoring \",z);\n gfactors := GaussianFactors(z);\n// Echo(gfactors);\n Ok := True;\n r :=1;\n ForEach(p,gfactors)\n {\n  r  := r*p[1]^p[2];\n  Ok := Ok And? GaussianPrime?(p[1]);\n };\n// Echo(r);\n Ok := Ok And? GaussianUnit?(r/z);\n If(Ok,True,Echo(\"FAILED: GaussianFactors(\", z, \")=\", gfactors, \" which is wrong.\"));\n};\n\nTestGaussianFactors((9!)+1);\nTestGaussianFactors(2+3*I);\nTestGaussianFactors(-1+2*I);\nTestGaussianFactors(17);\nTestGaussianFactors(41);\n\nVerify(GaussianFactors(157+28*I), [[Complex(5,2),1],[Complex(-29,6),1]]);\nVerify(GaussianFactors(1), []);        // is this the correct behavior? why not [[]] or [[1,1]]?\nVerify(GaussianFactors(-1), []);        // is this the correct behavior?\nVerify(GaussianFactors(I), []);        // is this the correct behavior?\nVerify(GaussianFactors(0), []);        // is this the correct behavior?\nVerify(GaussianFactors(2), [[Complex(1,1),1],[Complex(1,-1),1]]);\nVerify(GaussianFactors(-2), [[Complex(1,1),1],[Complex(1,-1),1]]);\nVerify(GaussianFactors(3), [[3,1]]);\nVerify(GaussianFactors(3*I), [[3,1]]);\nVerify(GaussianFactors(4), [[Complex(1,1),2],[Complex(1,-1),2]]);\nVerify(GaussianFactors(-5*I), [[Complex(2,1),1],[Complex(2,-1),1]]);\nVerify(GaussianFactors(Complex(1,1)^11*163^4),[[Complex(1,1),11],[163,4]]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/gaussianintegers/GaussianFactors.mpw";
        userFunctionsTestsMap.put("GaussianFactors",testString);

        testString = new String[3];
        testString[0] = "47";
        testString[1] = "\nVerify(GaussianInteger?(3+4*I),True );\nVerify(GaussianInteger?(5),True);\nVerify(GaussianInteger?(1.1), False );\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/gaussianintegers/GaussianInteger_.mpw";
        userFunctionsTestsMap.put("GaussianInteger?",testString);

        testString = new String[3];
        testString[0] = "75";
        testString[1] = "\nVerify(GaussianPrime?(5+2*I),True );\nVerify(GaussianPrime?(13), False );\nVerify(GaussianPrime?(0), False );\nVerify(GaussianPrime?(3.5), False );\nVerify(GaussianPrime?(2+3.1*I), False );\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/gaussianintegers/GaussianPrime_.mpw";
        userFunctionsTestsMap.put("GaussianPrime?",testString);

        testString = new String[3];
        testString[0] = "385";
        testString[1] = "\nVerify( OdeTest(yPRIMEPRIME+y,       OdeSolve(yPRIMEPRIME+y==0)     ), 0 );\nVerify( OdeTest(yPRIME/5-Sin(x), OdeSolve(yPRIME/5==Sin(x)) ), 0 );\nVerify( OdeTest(x*yPRIME - 1,    OdeSolve(x*yPRIME==1) ), 0 );\n\n";
        testString[2] = "/org/mathpiper/scripts4/odesolver/odesolver.mpw";
        userFunctionsTestsMap.put("OdeSolve",testString);

        testString = new String[3];
        testString[0] = "933";
        testString[1] = "\nNextTest(\"Testing orthogonal polynomials\");\n/* Symbolic calculations */\nTestMathPiper(OrthoG(3, 1/5, x), 88/125*x^3-12/25*x);\nTestMathPiper(OrthoG(9, 1/2, x), 12155/128*x^9-6435/32*x^7+9009/64*x^5-1155/32*x^3+315/128*x);\nTestMathPiper(OrthoH(4, x), 16*x^4-48*x^2+12);\nTestMathPiper(OrthoH(10, x), 1024*x^10-23040*x^8+161280*x^6-403200*x^4+302400*x^2-30240);\nTestMathPiper(OrthoL(4, 1/3, x), x^4/24-13/18*x^3+65/18*x^2-455/81*x+455/243);\nTestMathPiper(OrthoP(3,1/2,5/2,x), 21/2*x^3-7*x^2-35/16*x+7/8);\nTestMathPiper(OrthoP(7,x), (429*x^7-693*x^5+315*x^3-35*x)/16);\nTestMathPiper(OrthoT(15, x), 16384*x^15-61440*x^13+92160*x^11-70400*x^9+28800*x^7-6048*x^5+560*x^3-15*x);\nTestMathPiper(OrthoU(16, x), 65536*x^16-245760*x^14+372736*x^12-292864*x^10+126720*x^8-29568*x^6+3360*x^4-144*x^2+1);\n/* Numerical calculations */\nTestMathPiper(OrthoP(100, 1), 1);\nTestMathPiper(OrthoL(50,5/3,5/2), 956329424993407752478497541911420551314045339353541114044036291602395886513403153686689293955/143232645897909553890691033589829981069003266848814603996731044282564768594296559565258358784);\nTestMathPiper(OrthoP(15,1/7,1/9,2/3), 3891107589471727673898835091294644097395/16032477875245178148605931130545427636128);\n\n";
        testString[2] = "/org/mathpiper/scripts4/orthopoly/orthopoly.mpw";
        userFunctionsTestsMap.put("ortho_poly",testString);

        testString = new String[3];
        testString[0] = "271";
        testString[1] = "\nVerify(\nCForm(Hold(Cos(A-B)*Sin(a)*func(b,c,d*(e+Pi))*Sqrt(Abs(C)+D)-(g(a+b)^(c+d))^(c+d)))\n,\"cos(A - B) * sin(a) * func(b, c, d * ( e + Pi) ) * sqrt(fabs(C) + D) - pow(pow(g(a + b), c + d), c + d)\"\n);\n\nVerify(\nCForm(Hold({i:=0;While(i<?10){i++; a:=a+Floor(i);};}))\n, \"{\n  i = 0;\n  while(i <? 10)\n    {\n      ++(i);\n      a = a + floor(i);\n      }\n    ;\n    ;\n  }\n\"\n);\n\n/* Check that we can still force numbers to be floats in stead of integers if we want to */\nVerify(\nCForm(Hold({i:=0.;While(i<?10.){i++; a:=a+Floor(i);};}))\n, \"{\n  i = 0.;\n  while(i <? 10.)\n    {\n      ++(i);\n      a = a + floor(i);\n      }\n    ;\n    ;\n  }\n\"\n);\n\n";
        testString[2] = "/org/mathpiper/scripts4/outputforms/CForm.mpw";
        userFunctionsTestsMap.put("CForm",testString);

        testString = new String[3];
        testString[0] = "315";
        testString[1] = "\n/* Jitse's bug report, extended with the changes that do not coerce integers to floats automatically\n   any more (just enter a dot and the number becomes float if that is what is intended).\n */\nVerify(CForm(4), \"4\");\nVerify(CForm(4.), \"4.\");\nVerify(CForm(0), \"0\");\nVerify(CForm(0.), \"0.\");\n\n";
        testString[2] = "/org/mathpiper/scripts4/outputforms/CForm.mpw";
        userFunctionsTestsMap.put("CForm",testString);

        testString = new String[3];
        testString[0] = "104";
        testString[1] = "\nVerify(\nCFormable?(e+Pi*Cos(A-B)/3-Floor(3.14)*2)\n, True\n);\nVerify(\nCFormable?(e+Pi*Cos(A-B)/3-Floor(3.14)*2+badfunc(x+y))\n, False\n);\nVerify(\nCFormable?(e+Pi*Cos(A-B)/3-Floor(3.14)*2+badfunc(x+y), [badfunc])\n, True\n);\nVerify(\nCFormable?({i:=0;While(i<?10){i++; a:=a+i;};})\n, True\n);\nVerify(\nCFormable?({i:=0;While(i<?10){i++; a:=a+i; [];};})\n, False\n);\n\n\n";
        testString[2] = "/org/mathpiper/scripts4/outputforms/CFormable_.mpw";
        userFunctionsTestsMap.put("CFormable?",testString);

        testString = new String[3];
        testString[0] = "1189";
        testString[1] = "\n//Converting to and from OpenMath expressions\".\n\nMacro(OMTest1,[expr])\n{\n  Local(string,result);\n  string:=PipeToString() OMForm(@expr);\n  result:=PipeFromString(string)OMRead();\n//  Echo(Hold(@expr),`Hold(@result));\n  Verify(Hold(@expr),`Hold(@result));\n};\n\nOMTest1(2+3);\nOMTest1(2*a+3*Sin(Cos(a*x+b)));\n\n";
        testString[2] = "/org/mathpiper/scripts4/outputforms/openmath.mpw";
        userFunctionsTestsMap.put("open_math",testString);

        testString = new String[3];
        testString[0] = "428";
        testString[1] = "\n/* it worketh no more...\nTesting(\"Realistic example\");\nf:=Exp(I*lambda*eta)*w(T*(k+k1+lambda));\ng:=Simplify(Subst(lambda,0) f+(k+k1)*(Differentiate(lambda)f)+k*k1*Differentiate(lambda)Differentiate(lambda)f );\nVerify(TeXForm(g), ...);\n*/\n\nVerify(\nTeXForm(Hold(Cos(A-B)*Sqrt(C+D)-(a+b)*c^d+2*I+Complex(a+b,a-b)/Complex(0,1)))\n,\"$\\\\cos ( A - B)  \\\\cdot \\\\sqrt{C + D} - ( a + b)  \\\\cdot c ^{d} + 2 \\\\cdot \\\\imath  + \\\\frac{a + b + \\\\imath  \\\\cdot ( a - b) }{\\\\imath } $\"\n);\n\nVerify(\nTeXForm(Hold(Exp(A*B)/C/D/(E+F)*G-(-(a+b)-(c-d))-b^(c^d) -(a^b)^c))\n,\"$\\\\frac{\\\\frac{\\\\frac{\\\\exp ( A \\\\cdot B) }{C} }{D} }{E + F}  \\\\cdot G - (  - ( a + b)  - ( c - d) )  - b ^{c ^{d}} - ( a ^{b})  ^{c}$\"\n);\n\nVerify(\nTeXForm(Hold(Cos(A-B)*Sin(a)*f(b,c,d*(e+1))*Sqrt(C+D)-(g(a+b)^(c+d))^(c+d)))\n,\"$\\\\cos ( A - B)  \\\\cdot \\\\sin a \\\\cdot f( b, c, d \\\\cdot ( e + 1) )  \\\\cdot \\\\sqrt{C + D} - ( g( a + b)  ^{c + d})  ^{c + d}$\"\n);\n\n\n/* This test is commented out because it throws an exception when orthopoly.mpw is removed from the build process.\n// testing latest features: \\\\cdot, %, (a/b)^n, BinomialCoefficient(), BesselI, OrthoH\nVerify(\nTeXForm(3*2^n+Hold(x*10!) + (x/y)^2 + BinomialCoefficient(x,y) + BesselI(n,x) + Maximum(a,b) + OrthoH(n,x))\n, \"$3\\\\cdot 2 ^{n} + x\\\\cdot 10! + ( \\\\frac{x}{y} )  ^{2} + {x \\\\choose y} + I _{n}( x)  + \\\\max ( a, b)  + H _{n}( x) $\"\n);\n*/\n\n/* this fails because of a bug that Differentiate(x) f(y) does not go to 0 */ /*\nVerify(\nTeXForm(3*Differentiate(x)f(x,y,z)*Cos(Omega)*Modulo(Sin(a)*4,5/a^b))\n,\"$3 ( \\\\frac{\\\\partial}{\\\\partial x}f( x, y, z) )  ( \\\\cos \\\\Omega )  ( 4 ( \\\\sin a) ) \\\\bmod \\\\frac{5}{a ^{b}} $\"\n);\n*/\n\n\nRulebaseHoldArguments(\"f\",[x]);\nVerify(\nTeXForm(Hold(Differentiate(x)f(x)))\n,\"$\\\\frac{d}{d x}f( x) $\");\nRetract(\"f\",1);\n\nVerify(\nTeXForm(Hold(Not? (c<?0) And? (a+b)*c>=? -d^e And? (c<=?0 Or? b+1>?0) Or? a!=?0 And? Not? (p=?q)))\n,\"$ \\\\neg c < 0\\\\wedge ( a + b)  \\\\cdot c\\\\geq  - d ^{e}\\\\wedge ( c\\\\leq 0\\\\vee b + 1 > 0) \\\\vee a\\\\neq 0\\\\wedge  \\\\neg p = q$\"\n);\n\nRulebaseHoldArguments(\"f\",[x,y,z]);\nVerify(\nTeXForm((Differentiate(x)f(x,y,z))*Cos(Omega)*Modulo(Sin(a)*4,5/a^b))\n,\"$( \\\\frac{\\\\partial}{\\\\partial x}f( x, y, z) )  \\\\cdot \\\\cos \\\\Omega  \\\\cdot ( 4 \\\\cdot \\\\sin a) \\\\bmod \\\\frac{5}{a ^{b}} $\"\n);\nRetract(\"f\",3);\n\nRulebaseHoldArguments(\"g\",[x]);\nRulebaseHoldArguments(\"theta\",[x]);\nVerify(\nTeXForm(Pi+Exp(1)-Theta-Integrate(x,x1,3/g(Pi))2*theta(x)*Exp(1/x))\n,\"$\\\\pi  + \\\\exp ( 1)  - \\\\Theta  - \\\\int _{x_{1}} ^{\\\\frac{3}{g( \\\\pi ) }  } 2 \\\\cdot \\\\theta ( x)  \\\\cdot \\\\exp ( \\\\frac{1}{x} )  dx$\"\n);\nRetract(\"g\",1);\nRetract(\"theta\",1);\n\nVerify(\nTeXForm([a[3]*b[5]-c[1][2],[a,b,c,d]])\n,\"$( a _{3} \\\\cdot b _{5} - c _{( 1, 2) }, ( a, b, c, d) ) $\"\n);\n\n\n//Note: this is the only code in the test suite that currently creates new rulebases.\nRulebaseHoldArguments(\"aa\",[x,y,z]);\nBodied(\"aa\", 200);\nRulebaseHoldArguments(\"bar\", [x,y]);\nInfix(\"bar\", 100);\nVerify(\nTeXForm(aa(x,y) z + 1 bar y!)\n,\"$aa( x, y) z + 1\\\\mathrm{ bar }y!$\"\n);\nRetract(\"aa\",3);\nRetract(\"bar\",2);\n\nVerify(\nTeXForm(x^(1/3)+x^(1/2))\n, \"$\\\\sqrt[3]{x} + \\\\sqrt{x}$\"\n);\n\n/*\nVerify(\nTeXForm()\n,\"\"\n);\n*/\n\n/* Bug report from Michael Borcherds. The brackets were missing. */\nVerify(TeXForm(Hold(2*x*(-2))), \"$2 \\\\cdot x \\\\cdot (  - 2) $\");\n\n\n";
        testString[2] = "/org/mathpiper/scripts4/outputforms/texform.mpw";
        userFunctionsTestsMap.put("TeXForm",testString);

        testString = new String[3];
        testString[0] = "333";
        testString[1] = "\n/* I stringified the results for now, as that is what the tests used to mean. The correct way to deal with this\n * would be to compare the resulting numbers to accepted precision.\n */\nVerify(PipeToString()Write(Plot2D(a,-1:1,output->data,points->4,depth->0)), \"[[[-1,-1],[-0.5,-0.5],[0.0,0.0],[0.5,0.5],[1,1]]]\");\nVerify(PipeToString()Write(Plot2D(b,b-> -1:1,output->data,points->4)), \"[[[-1,-1],[-0.5,-0.5],[0.0,0.0],[0.5,0.5],[1,1]]]\");\n\n";
        testString[2] = "/org/mathpiper/scripts4/plots/_2d/plot2d.mpw";
        userFunctionsTestsMap.put("Plot2D",testString);

        testString = new String[3];
        testString[0] = "422";
        testString[1] = "\n/* I stringified the results for now, as that is what the tests used to mean. The correct way to deal with this\n * would be to compare the resulting numbers to accepted precision.\n */\n{\n  Local(result);\n  result:=\"[[[-1,-1,-1],[-1,0,-1],[-1,1,-1],[0,-1,0],[0,0,0],[0,1,0],[1,-1,1],[1,0,1],[1,1,1]]]\";\n  Verify(PipeToString()Write(Plot3DS(a,-1:1,-1:1,output->data,points->2)), result);\n  Verify(PipeToString()Write(Plot3DS(x1,x1 -> -1:1,x2 -> -1:1,output->data,points->2)), result);\n};\n\n";
        testString[2] = "/org/mathpiper/scripts4/plots/_3d/plot3ds.mpw";
        userFunctionsTestsMap.put("Plot3DS",testString);

        testString = new String[3];
        testString[0] = "42";
        testString[1] = "\nVerify(Constant?(Pi), True);\nVerify(Constant?(Exp(1)+Sqrt(3)), True);\nVerify(Constant?(x), False);\nVerify(Constant?(Infinity), True);\nVerify(Constant?(-Infinity), True);\nVerify(Constant?(Undefined), True);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/Constant_.mpw";
        userFunctionsTestsMap.put("Constant?",testString);

        testString = new String[3];
        testString[0] = "29";
        testString[1] = "\n// Problem with FloatIsInt? and gmp\nVerify(FloatIsInt?(3.1415926535e9), False);\nVerify(FloatIsInt?(3.1415926535e10), True);\nVerify(FloatIsInt?(3.1415926535e20), True);\nVerify(FloatIsInt?(0.3e20), True);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/FloatIsInt_.mpw";
        userFunctionsTestsMap.put("FloatIsInt?",testString);

        testString = new String[3];
        testString[0] = "57";
        testString[1] = "\nVerify(HasExpression?(a*b+1,1),True);\nVerify(HasExpression?(a+Sin(b*c),c),True);\nVerify(HasExpression?(a*b+1,2),False);\nVerify(HasExpressionArithmetic?(a*b+1,ToAtom(\"+\")),False);\nVerify(HasExpressionArithmetic?(a*b+1,1),True);\nVerify(HasExpressionArithmetic?(a+Sin(b*c),c),False);\nVerify(HasExpressionArithmetic?(a+Sin(b*c),Sin(b*c)),True);\n\nRulebaseHoldArguments(\"f\",[a]);\nVerify(HasExpression?(a*b+f([b,c]),f),False);\nVerify(HasExpressionArithmetic?(a*b+f([b,c]),c),False);\nRetract(\"f\",1);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/HasExpression_.mpw";
        userFunctionsTestsMap.put("HasExpression?",testString);

        testString = new String[3];
        testString[0] = "44";
        testString[1] = "\nVerify(HasFunctionArithmetic?(a*b+1,ToAtom(\"+\")),True);\nVerify(HasFunctionArithmetic?(a+Sin(b*c),*),False);\nVerify(HasFunctionArithmetic?(a+Sin(b*c),Sin),True);\n\nRulebaseHoldArguments(\"f\",[a]);\nVerify(HasFunctionArithmetic?(a*b+f([b,c]),List),False);\nRetract(\"f\",1);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/HasFunctionArithmetic_.mpw";
        userFunctionsTestsMap.put("HasFunctionArithmetic?",testString);

        testString = new String[3];
        testString[0] = "49";
        testString[1] = "\nVerify(HasFunction?(a*b+1,*),True);\nVerify(HasFunction?(a+Sin(b*c),*),True);\nVerify(HasFunction?(a*b+1,List),False);\n\nRulebaseHoldArguments(\"f\",[a]);\nVerify(HasFunction?(a*b+f([b,c]),List),True);\nRetract(\"f\",1);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/HasFunction_.mpw";
        userFunctionsTestsMap.put("HasFunction?",testString);

        testString = new String[3];
        testString[0] = "39";
        testString[1] = "\nVerify(Hermitian?([[0,I],[-I,0]]),True);\nVerify(Hermitian?([[0,I],[-I,1]]),True);\nVerify(Hermitian?([[0,I],[-2*I,0]]),False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/Hermitian_.mpw";
        userFunctionsTestsMap.put("Hermitian?",testString);

        testString = new String[3];
        testString[0] = "41";
        testString[1] = "\nVerify(Unitary?([[0,I],[-I,0]]),True);\nVerify(Unitary?([[0,I],[-I,1]]),False);\nVerify(Unitary?([[0,I],[-2*I,0]]),False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/Unitary_.mpw";
        userFunctionsTestsMap.put("Unitary?",testString);

        testString = new String[3];
        testString[0] = "47";
        testString[1] = "\nVerify(Variable?(a),True);\nVerify(Variable?(Sin(a)),False);\nVerify(Variable?(2),False);\nVerify(Variable?(-2),False);\nVerify(Variable?(2.1),False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/Variable_.mpw";
        userFunctionsTestsMap.put("Variable?",testString);

        testString = new String[3];
        testString[0] = "246";
        testString[1] = "\nVerify(Scalar?(a),True);\nVerify(Scalar?([a]),False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/matrix.mpw";
        userFunctionsTestsMap.put("Scalar?",testString);

        testString = new String[3];
        testString[0] = "256";
        testString[1] = "\nVerify(Vector?(1),False);\nVerify(Vector?(a),False);\nVerify(Vector?(Sin(a)+2),False);\nVerify(Vector?([]),True);\nVerify(Vector?([[]]),False);\nVerify(Vector?([1,2,a,4]),True);\nVerify(Vector?([1,[2,a],4]),False);\nVerify(Vector?([[a,b,c]]),False);\n\nTesting(\"-- Vector?(Number?)\");\nVerify(Vector?(Number?,1),False);\nVerify(Vector?(Number?,[]),True);\nVerify(Vector?(Number?,[a,b,c]),False);\nVerify(Vector?(Number?,[a,2,c]),False);\nVerify(Vector?(Number?,[2,2.5,4]),True);\nVerify(Vector?(Number?,[Pi,2,3]),False);\nVerify(Vector?(Number?,[[1],[2]]),False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/matrix.mpw";
        userFunctionsTestsMap.put("Vector?",testString);

        testString = new String[3];
        testString[0] = "282";
        testString[1] = "\nVerify(Vector?(1),False);\nVerify(Vector?(a),False);\nVerify(Vector?(Sin(a)+2),False);\nVerify(Vector?([]),True);\nVerify(Vector?([[]]),False);\nVerify(Vector?([1,2,a,4]),True);\nVerify(Vector?([1,[2,a],4]),False);\nVerify(Vector?([[a,b,c]]),False);\n\nTesting(\"-- Vector?(Number?)\");\nVerify(Vector?(Number?,1),False);\nVerify(Vector?(Number?,[]),True);\nVerify(Vector?(Number?,[a,b,c]),False);\nVerify(Vector?(Number?,[a,2,c]),False);\nVerify(Vector?(Number?,[2,2.5,4]),True);\nVerify(Vector?(Number?,[Pi,2,3]),False);\nVerify(Vector?(Number?,[[1],[2]]),False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/matrix.mpw";
        userFunctionsTestsMap.put("Vector?",testString);

        testString = new String[3];
        testString[0] = "308";
        testString[1] = "Verify(Matrix?(1),False);\nVerify(Matrix?([]),False);\nVerify(Matrix?([a,b]),False);\nVerify(Matrix?([[]]),True);\nVerify(Matrix?([[a]]),True);\nVerify(Matrix?([[[a]]]),False);\nVerify(Matrix?([[],a]),False);\nVerify(Matrix?([[a],b]),False);\nVerify(Matrix?([[],[]]),True);\nVerify(Matrix?([[[]],[]]),False);\nVerify(Matrix?([[],[[]]]),False);\nVerify(Matrix?([[a,b],[c]]),False);\nVerify(Matrix?([[a,b],[c,d]]),True);\nVerify(Matrix?([[a,b],[c,[d]]]),False);\nVerify(Matrix?([[[]]]), False);\nVerify(Matrix?([[[a]]]), False);\nVerify(Matrix?([[[[a]]],[[[b]]]]),False);\n\nTesting(\"---- Matrix?(Integer?)\");\nVerify(Matrix?(Integer?,[[a,1]]),False);\nVerify(Matrix?(Integer?,[[1,2]]),True);\nVerify(Matrix?(Integer?,[[1,2/3]]),False);\nVerify(Matrix?(Integer?,[[1,2,3],[4,5,6]]),True);\nVerify(Matrix?(Integer?,[[1,[2],3],[4,5,6]]),False);\nVerify(Matrix?(Integer?,[[1,2,3],[4,5]]),False);\nVerify(Matrix?(Integer?,[[Sin(1),2,3],[4,5,6]]),False);\nVerify(Matrix?(Integer?,[[Sin(0),2,3],[4,5,6]]),True);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/matrix.mpw";
        userFunctionsTestsMap.put("Matrix?",testString);

        testString = new String[3];
        testString[0] = "343";
        testString[1] = "\nVerify(SquareMatrix?([[]]),False);\nVerify(SquareMatrix?([[a]]),True);\nVerify(SquareMatrix?([[],[]]),False);\nVerify(SquareMatrix?([[a,b]]),False);\nVerify(SquareMatrix?([[a,b],[c,d]]),True);\nVerify(SquareMatrix?([[a,b],[c,d],[e,f]]),False);\nVerify(SquareMatrix?([[a,b,c],[d,e,f],[g,h,i]]),True);\nVerify(SquareMatrix?([[a,b,c],[d,e,f]]),False);\nVerify(SquareMatrix?([[[a,b]],[[c,d]]]), False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/matrix.mpw";
        userFunctionsTestsMap.put("SquareMatrix?",testString);

        testString = new String[3];
        testString[0] = "60";
        testString[1] = "\nVerify(BooleanList(8, 36), [False,False,True,False,False,True,False,False]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/proposed/logic/BooleanList.mpw";
        userFunctionsTestsMap.put("BooleanList",testString);

        testString = new String[3];
        testString[0] = "50";
        testString[1] = "\nVerify(BooleanLists(2), [[False,False],[False,True],[True,False],[True,True]]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/proposed/logic/BooleanLists.mpw";
        userFunctionsTestsMap.put("BooleanLists",testString);

        testString = new String[3];
        testString[0] = "102";
        testString[1] = "\nUnassign(a,b,c);\n\nVerify(Subexpressions(a*b+c), [a,b,c,a*b,a*b+c]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/proposed/logic/Subexpressions.mpw";
        userFunctionsTestsMap.put("Subexpressions",testString);

        testString = new String[3];
        testString[0] = "77";
        testString[1] = "\nUnassign(a,b);\n\nVerify(TruthTable(a And? b), [[a,b,a And? b],[True,True,True],[True,False,False],[False,True,False],[False,False,False]]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/proposed/logic/TruthTable.mpw";
        userFunctionsTestsMap.put("TruthTable",testString);

        testString = new String[3];
        testString[0] = "374";
        testString[1] = "\nVerifyPslq(left,right):=\n{\n  If(left=?right,\n    Verify(True,True),\n    `Verify(@left,-(@right)));\n};\n\nVerifyPslq(Pslq([ Pi+2*Exp(1) , Pi , Exp(1) ],20),[1,-1,-2]);\nVerifyPslq(Pslq([ 2*Pi+3*Exp(1) , Pi , Exp(1) ],20),[1,-2,-3]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/pslq/Pslq.mpw";
        userFunctionsTestsMap.put("Pslq",testString);

        testString = new String[3];
        testString[0] = "186";
        testString[1] = "\nVerify(RabinMiller(1037),False);\nVerify(RabinMiller(1038),False);\nVerify(RabinMiller(1039),True);\n\n";
        testString[2] = "/org/mathpiper/scripts4/rabinmiller/RabinMiller.mpw";
        userFunctionsTestsMap.put("RabinMiller",testString);

        testString = new String[3];
        testString[0] = "135";
        testString[1] = "\nNextTest(\"Testing simplifying nested radicals\");\n\nTestMathPiper(RadSimp(Sqrt(9+4*Sqrt(2))), 1+Sqrt(8));\nTestMathPiper(RadSimp(Sqrt(5+2*Sqrt(6))+Sqrt(5-2*Sqrt(6))),Sqrt(12));\nTestMathPiper(RadSimp(Sqrt(14+3*Sqrt(3+2*Sqrt(5-12*Sqrt(3-2*Sqrt(2)))))), 3+Sqrt(2));\n\nTestMathPiper(RadSimp(Sqrt(3+2*Sqrt(2))),1+Sqrt(2));\nTestMathPiper(RadSimp(Sqrt(5+2*Sqrt(6))),Sqrt(2)+Sqrt(3));\n\n//FAILS??? TestMathPiper(RadSimp(Sqrt(5*Sqrt(3)+6*Sqrt(2))),Sqrt(Sqrt(27))+Sqrt(Sqrt(12)));\n//??? TestMathPiper(RadSimp(Sqrt(12+2*Sqrt(6)+2*Sqrt(14)+2*Sqrt(21))),Sqrt(2)+Sqrt(3)+Sqrt(7));\n\n";
        testString[2] = "/org/mathpiper/scripts4/radsimp/RadSimp.mpw";
        userFunctionsTestsMap.put("RadSimp",testString);

        testString = new String[3];
        testString[0] = "74";
        testString[1] = "\nTestMathPiper( Simplify((x+y)*(x-y)-(x+y)^2), -2*y^2-2*x*y );\nTestMathPiper( Simplify(1+x+x+3*y-4*(x+y+2)), -2*x-y-7 );\nTestMathPiper( Simplify((1+I)^4), -4 );\nTestMathPiper( Simplify((x-y)/(x*y)), 1/y-1/x );\n//See below, now handled with II KnownFailure(TestMathPiper( Simplify((x+I)^4), x^4+4*x^3*I-6*x^2-4*x*I+1 ));\n\nTestMathPiper( Simplify((xx+II)^4), xx^4+4*xx^3*II-6*xx^2-4*xx*II+1 );\n\nTestMathPiper( Simplify(Differentiate(x,4)Exp(-x^2/2)), Exp(-x^2/2)*(x^4-6*x^2+3));\n\nTestMathPiper( Simplify(1),1);\nTestMathPiper( Simplify(1/x ), 1/x );\nTestMathPiper( Simplify( 1/(1/x+1) ),x/(x+1) );\nTestMathPiper( Simplify(1/(1/(1/x+1)+1) ),(x+1)/(2*x+1) );\nTestMathPiper( Simplify(1/(1/(1/(1/x+1)+1)+1) ),(2*x+1)/(3*x+2) );\nTestMathPiper( Simplify(1/(1/(1/(1/(1/x+1)+1)+1)+1) ),(3*x+2)/(5*x+3) );\nTestMathPiper( Simplify(1/(1/(1/(1/(1/(1/x+1)+1)+1)+1)+1) ),(5*x+3)/(8*x+5) );\n\n\n\n/*Serge: these are not handled yet ;-)\nTestMathPiper( Simplify((x^2-y^2)/(x+y)), x-y );\n*/\n\nTestMathPiper(ExpandFrac(x+y/x+1/3),(x^2+y+x/3)/x);\n\n// this did not work until the latest fix to ExpandBrackets using MM()\nVerify(\nExpandBrackets(x*(a+b)*y*z)\n, x*a*y*z+x*b*y*z\n);\n\nVerify(\nExpandBrackets(ExpandFrac((x+1)/(x-1)+1/x))\n, Hold(x^2/(x^2-x)+(2*x)/(x^2-x)-1/(x^2-x))\n);\n\n// these used to fail. Added by Serge, resolved by Ayal\nVerify({Local(a);a:=0.1;Simplify(a*b);}, 0.1*b);\nVerify({Local(a);a:=0.1;Simplify(a/b);}, 0.1/b);\n\n\n\n\nVerify({Local(a);a:=0.1;Simplify((a*b*c)/(a*c*b));},1);\n\n\nLocalSymbols(p,a,x)\n{\n  p := a+2-(a+1);\n  Verify(Simplify(x^p),x);\n};\n\n\nLocalSymbols(f,p,a,b,x,n,simple,u,v)\n{\n  simple := [\n            Exp(_a)*Exp(_b) <- Exp(a+b),\n            Exp(_a)*_u*Exp(_b) <- u*Exp(a+b),\n            _u*Exp(_a)*Exp(_b) <- u*Exp(a+b),\n            Exp(_a)*Exp(_b)*_u <- u*Exp(a+b),\n            _u*Exp(_a)*_v*Exp(_b) <- u*v*Exp(a+b),\n            Exp(_a)*_u*Exp(_b)*_v <- u*v*Exp(a+b),\n            _u*Exp(_a)*Exp(_b)*_v <- u*v*Exp(a+b),\n            _u*Exp(_a)*_v*Exp(_b)*_w <- u*v*w*Exp(a+b)\n          ];\n\n  a := Simplify(Exp(x)*(Differentiate(x) x*Exp(-x)));\n  b := Exp(x)*Exp(-x)-Exp(x)*x*Exp(-x);\n\n  a:= (a /: simple);\n  b:= (b /: simple);\n\n  Verify(Simplify(a-(1-x)),0);\n  Verify(Simplify(b-(1-x)),0);\n\n};\n\n\n// The following line catches a bug reported where Simplify\n// would go into an infinite loop. It doesn't check the correctness\n// of the returned value as such, but merely the fact that this\n// simplification terminates in the first place.\n//\n// The problem was caused by a gcd calculation (from the multivariate\n// code) not terminating.\nVerify( Simplify((a^2+b^2)/(2*a)), (a^2+b^2)/(2*a) );\n\n\n/* Bug #10: I don't know what we want to return, but '0' is definitely wrong! */\nVerify(Simplify(x^(-2)/(1-x)^3) !=? 0, True);\n\n\n/* In MatchLinear and MatchPureSquare, the matched coefficients were\n * assigned to global variables that were not protected with LocalSymbols.\n */\n{\n  Local(a,b,A);\n  a:=mystr;\n  A:=mystr;\n  /* The real test here is that no error is generated due to the\n   * fact that variables a or A are set.\n   */\n  Verify(Simplify((Integrate(x,a,b)Sin(x))-Cos(a)+Cos(b)),0);\n};\n\n\n/* Bug reported by Michael Borcherds: Simplify(((4*x)-2.25)/2)\n   returned some expression with three calls to Gcd, which was technically\n   correct, but not the intended simplification.\n */\nVerify(Zero?(Simplify(Simplify(((4*x)-2.25)/2)-(2*x-2.25/2))),True);\n\n\n\n";
        testString[2] = "/org/mathpiper/scripts4/simplify/Simplify.mpw";
        userFunctionsTestsMap.put("Simplify",testString);

        testString = new String[3];
        testString[0] = "303";
        testString[1] = "\nTestMathPiper(FactorialSimplify((n+1)! / n!),n+1);\nTestMathPiper(FactorialSimplify((n-k+2)! / (n-k)!),(n-k+2)*(n-k+1));\nTestMathPiper(FactorialSimplify(2^(n+2)/2^n),4);\nTestMathPiper(FactorialSimplify((-1)^(n+1)/(-1)^n),-1);\nTestMathPiper(FactorialSimplify((n+1)! / n! + (n-k+2)! / (n-k)!),n+1 + (n-k+2)*(n-k+1));\n\nTestMathPiper(FactorialSimplify((n+1)! / n! + (-1)^(n+1)/(-1)^n),n);\n\n/* And now for the piece de resistance: an example from\n   the book \"A=B\"\n */\n\nTestMathPiper(FactorialSimplify(\n  (\n    (n+1)! / (2*k! *(n+1-k)!) -\n    n! / (k! * (n-k)!)        +\n    n! / (2*k! * (n-k)!)      -\n    n! / (2*(k-1)! * (n-k+1)!)\n  )*(k! *(n+1-k)!)/(n!)\n),0);\n\n";
        testString[2] = "/org/mathpiper/scripts4/simplify/factorial/FactorialSimplify.mpw";
        userFunctionsTestsMap.put("FactorialSimplify",testString);

        testString = new String[3];
        testString[0] = "92";
        testString[1] = "\n/* Bug reported by Adrian Vontobel: when assigning an expression to a variable,\n * it did not get re-evaluated in the calling environment when passing it in to Newton.\n * The resulting value was \"Undefined\", instead of the expected 1.5 .\n */\nNumericEqual({  Local(expr); expr := 1800*x/1.5 - 1800; Newton(expr, x,2,0.001); },1.5,3);\n\n";
        testString[2] = "/org/mathpiper/scripts4/solve/Newton.mpw";
        userFunctionsTestsMap.put("Newton",testString);

        testString = new String[3];
        testString[0] = "13";
        testString[1] = "\n/* Linear equations */\nTesting(\"     Linear\");\n\nVerifySolve(PSolve(x,x), 0);\nVerifySolve(PSolve(x+3*Sin(b)-1,x), 1-3*Sin(b));\nVerifySolve(PSolve(2*x-a,x), a/2);\nVerifySolve(PSolve(2*x-a,a), 2*x);\n\n/* Quadratic equations */\nTesting(\"     Quadratic\");\n\nVerifySolve(PSolve(x^2,x), 0);\nVerifySolve(PSolve(4*x^2-1,x), [1/2,-1/2]);\nVerifySolve(PSolve(x^2+1,x), [I,-I]);\nVerifySolve(PSolve(x^2-3*x+2,x), [1,2]);\n\n/* Cubic equations */\nTesting(\"     Cubic\");\n\nVerifySolve(PSolve(x^3,x), 0);\nVerifySolve(PSolve(x^3-1,x), [1, Exp(2/3*Pi*I), Exp(-2/3*Pi*I)]);\nVerifySolve(PSolve(x^3+1,x), [-1, Exp(1/3*Pi*I), Exp(-1/3*Pi*I)]);\nVerifySolve(PSolve(x^3-3*x^2+2*x,x), [0,2,1]);\n\n/* Quartic equations */\nTesting(\"     Quartic\");\n\nVerifySolve(PSolve(x^4,x), 0);\nVerifySolve(PSolve(16*x^4-1,x), [1/2, -1/2, 1/2*I, -1/2*I]);\nVerifySolve(PSolve(x^4-x,x), [0, 1, Exp(2/3*Pi*I), Exp(-2/3*Pi*I)]);\nVerifySolve(PSolve(x^4+x,x), [0, -1, Exp(1/3*Pi*I), Exp(-1/3*Pi*I)]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/solve/PSolve.mpw";
        userFunctionsTestsMap.put("PSolve",testString);

        testString = new String[3];
        testString[0] = "381";
        testString[1] = "\nVerifySolve(Solve(a+x*y==z,x),[x==(z-a)/y]);\n\n// check that solving systems of equations works, at least at the\n// level of simple back-substitutions\nVerifySolve(Solve([a+x*y==z],[x]),[[x==(z-a)/y]]);\n\n// check linear systems\nVerifySolve(Solve([2*x-2*y+z==(-7),3*x+y+2*z==(-2),5*x+3*y-3*z==(-7)], [x,y,z]), [x==(-2),y==2,z==1]);\nVerifySolve(Solve([3*x-2*y+z==1,x-y-z==2,6*x-4*y+2*z==3], [x,y,z]), []);\nVerifySolve(Solve([2*x+3*y==6,4*x+6*y==12], [x,y]), [x==3-(3*y)/2,y==y]);\n\n{\n  Local(eq,res);\n  eq:=[a-b==c,b==c];\n  res:=Solve(eq,[a,b]);\n  Verify(eq Where res,[c==c,c==c]);\n};\n\nVerifySolve(Solve(a+x*y == z, x), [ x == (z-a)/y ]);\n\nVerifySolve(Solve(x^2-3*x+2, x), [ x == 1, x == 2 ]);\n\nVerifySolve(Solve(2^n == 32, n), [ n == Ln(32)/Ln(2) ]);\n            /* Of course, Ln(32)/Ln(2) = 5 */\n\nVerifySolve(Solve(ArcTan(x^4) == Pi/4, x), [ x == 1, x == -1, x == I, x == -I ]);\n\nVerifySolve(Solve(Exp(x)/(1-Exp(x)) == a, x), [x == Ln(a/(a+1))]);\nVerifySolve(Solve(Exp(x)/(1-Exp(x)) == a, a), [a == Exp(x)/(1-Exp(x))]);\n\n//VerifySolve(Solve(x^5 == 1, x),\n//            [ x == 1, x == Exp(2/5*I*Pi), x == Exp(4/5*I*Pi),\n//              x == Exp(-2/5*I*Pi), x == Exp(-4/5*I*Pi)]);\n\nVerifySolve(Solve(Sqrt(x) == 1, x),  [ x == 1 ]);\nVerifySolve(Solve(Sqrt(x) == -1, x), [ ]);\nVerifySolve(Solve(Sqrt(x) == I, x),  [ x == -1 ]);\nVerifySolve(Solve(Sqrt(x) == -I, x), [ ]);\nVerifySolve(Solve(Sqrt(x) == 0, x),  [ x == 0 ]);\n\n\n/* The following equations have in fact infinitely many solutions */\n\nVerifySolve(Solve(Sin(x), x), [ x == 0, x == Pi ]);\n\nVerifySolve(Solve(Sin(x), x), [ x == 0, x == Pi ]);\n\nVerifySolve(Solve(Cos(a)^2 == 1/2, a),\n            [ a == Pi/4, a == 3/4*Pi, a == -3/4*Pi, a == -Pi/4 ]);\n\n/* The solution could have nicer form, but it is correct, apart from periodicity */\nVerifySolve(Solve(Sin(a*Pi)^2-Sin(a*Pi)/2 == 1/2, a), [a==Pi/(2*Pi),a==-Pi/(6*Pi),a==(Pi+Pi/6)/Pi]);\n\nVerify(Error?(), False);\n\n/* This equation can be solved (the solution is x>0), but the current\n * code does not do this. The least we can expect is that no spurious\n * solutions are returned. */\nVerifySolve(Solve(0^x == 0, x), []);\nVerify(ClearError(\"SolveFails\"), True);\nVerify(Error?(), False);\n\n/* This equation could be solved using the Lambert W function */\nVerifySolve(Solve(x^x == 1, x), []);\nVerify(ClearError(\"SolveFails\"), True);\nVerify(Error?(), False);\n\n/* Another equation which cannot be solved at the moment */\nVerifySolve(Solve(BesselJ(1,x), x), []);\nVerify(ClearError(\"SolveFails\"), True);\nVerify(Error?(), False);\n\n/* And another one */\nVerifySolve(Solve(Exp(x)+Cos(x) == 3, x), []);\nVerify(ClearError(\"SolveFails\"), True);\nVerify(Error?(), False);\n\n/* This equation could be solved if we knew that x >= 0 */\nVerifySolve(Solve(Sqrt(x) == a, x),  [ ]);\nVerify(ClearError(\"SolveFails\"), True);\nVerify(Error?(), False);\n\n/* Test the type-checking mechanism */\nVerifySolve(Solve(2*x == 1, 1), []);\nVerify(ClearError(\"SolveTypeError\"), True);\nVerify(Error?(), False);\n\n/* This command is clearly nonsense, but it used to send Yacas in an\n * infinite recursion, which should never happen.  Note that 'Differentiate(y)x == 0'\n * is parsed as 'Differentiate(y)(x==0)'. */\nVerifySolve(Solve(Differentiate(y)(x == 0), x),  [ ]);\nVerify(ClearError(\"SolveFails\"), True);\nVerify(Error?(), False);\n\n\n/* Yacas has difficulties with more complicated equations, like the\n * biquadratic x^4 - 3*x^2 + 2. */\n\n/* Added the ability to Solve and Where to handle expressions more complex than just variables.\n   One can now Solve for say x[1], or Sin(x) (it only uses a simple comparison for now though).\n   The following test just assures that that will never break.\n */\nVerify(Simplify(x[1]-4*x[2]+x[3] Where (Solve([x[1]-4*x[2]+x[3]==0],[x[2]]))),[0]);\n\n\n/*TODO MatrixSolve */\n\n// moved from xSingle_test_solve.mpw\nVerifySolve(Solve(3/(x^2+x-2)-1/(x^2-1)-7/(2*(x^2+3*x+2))==0, x), [x==3]);\nVerifySolve(Solve(3/(x^2+x-2)-1/(x^2-1)==7/(2*(x^2+3*x+2)), x), [x==3]);\n\n// moved from xTestSolve.mpw\n\n// Tricky one; the solution should depend on a being 0\n// FIXME: the equation leaves the error set to SolveFails\n//VerifySolve(Solve(a == 0,x),[]);\n\nVerifySolve(Solve(0,x),[x==x]);\nVerifySolve(Solve(x-5,x),[x==5]);\nVerifySolve(Solve(x-a,x),[x==a]);\nVerifySolve(Solve(12*x+5==29,x),[x==2]);\nVerifySolve(Solve(5*x-15==5*(x-3),x),[x==x]);\nVerifySolve(Solve(5*x-15==5*(x-4),x),[]);\nVerifySolve(Solve(x^2-4,x),[x==2,x==(-2)]);\n\n//VerifySolve(Solve(x^2-a^2,x),[x==a,x==(-a)]);\n\nVerifySolve(Solve(2*x^2+9*x==18,x),[x==(-6),x==3/2]);\nVerifySolve(Solve(5*x^2==25*x,x),[x==0,x==5]);\nVerifySolve(Solve(2*x/5-x/3==2,x),[x==30]);\nVerifySolve(Solve(2/x-3/2==7/(2*x),x),[x==(-1)]);\nVerifySolve(Solve(2/(x-3)-3/(x+3)==12/(x^2-9),x),[]);\nVerifySolve(Solve(3/(x^2+x-2)-1/(x^2-1)==7/(2*(x^2+3*x+2)),x),[x==3]);\nVerifySolve(Solve(1+1/x==6/x^2,x),[x==2,x==(-3)]);\nVerifySolve(Solve(Sqrt(x)-3,x),[x==9]);\nVerifySolve(Solve(Sqrt(x-3),x),[x==3]);\nVerifySolve(Solve(Sqrt(x-3)==2,x),[x==7]);\n\n//VerifySolve(Solve(Sqrt(2*x)==Sqrt(x+1),x), [x==1]);\n//VerifySolve(Solve(Sqrt(x)==x,x),[x==1,x==0]);\n//VerifySolve(Solve(Sqrt(x+2)-2*x==1,x),[x==1/4]);\n//VerifySolve(Solve(Sqrt(x+2)+2*x==1,x),[x==(5 - Sqrt(41))/8]);\n//VerifySolve(Solve(Sqrt(9*x^2+4)-3*x==1,x),[x==1/2]);\n\nVerifySolve(Solve(Sqrt(x+1)-Sqrt(x)==-2,x),[]);\n\n//VerifySolve(Solve(Sqrt(3*x-5)-Sqrt(2*x+3)==-1,x),[x==3]);\n\nVerifySolve(Solve(Exp(x)==4,x),[x==Ln(4)]);\nVerifySolve(Solve(Exp(x)==Abs(a),x),[x==Ln(Abs(a))]);\nVerifySolve(Solve(Ln(x)==4,x),[x==Exp(4)]);\nVerifySolve(Solve(Ln(x)==a,x),[x==Exp(a)]);\nVerifySolve(Solve((x+6)/2-(3*x+36)/4==4,x),[x==-40]);\nVerifySolve(Solve((x-3)*(x-4)==x^2-2,x),[x==2]);\nVerifySolve(Solve(a*x-2*b*c==d,x),[x==(2*b*c+d)/a]);\nVerifySolve(Solve((36-4*x)/(x^2-9)-(2+3*x)/(3-x)==(3*x-2)/(x+3),x),[x==-2]);\n\n//VerifySolve(Solve((x^2-1)^(1/3)==2,x),[x==3,x==(-3)]);\n\nVerifySolve(Solve(x^4-53*x^2+196==0,x),[x==(-7),x==(-2),x==2,x==7]);\nVerifySolve(Solve(x^3-8==0,x),[x==2,x==-1+I*Sqrt(3),x==-1-I*Sqrt(3)]);\n\n//VerifySolve(Solve(x^(2/3)+x^(1/3)-2==0,x),[x==1,x==(-8)]);\n//VerifySolve(Solve(Sqrt(x)-(1/4)*x==1,x),[x==4]);\n//VerifySolve(Solve((1/4)*x-Sqrt(x)==-1,x),[x==4]);\n\nVerifySolve(Solve([x-y==1,3*x+2*y==13],[x,y]),[x==3,y==2]);\nVerifySolve(Solve([x-y-1==0,2*y+3*x-13==0],[x,y]),[x==3,y==2]); \n\n";
        testString[2] = "/org/mathpiper/scripts4/solve/solve.mpw";
        userFunctionsTestsMap.put("Solve",testString);

        testString = new String[3];
        testString[0] = "113";
        testString[1] = "\nVerify(Sort([4,7,23,53,-2,1], \">?\"), [53,23,7,4,1,-2]);\nVerify(SortIndexed([4,7,23,53,-2,1], \">?\"), [[53,23,7,4,1,-2],[4,3,2,1,6,5]]);\nVerify(Sort([11,-17,-1,-19,-18,14,4,7,9,-15,13,11,-2,2,7,-19,5,-3,6,4,-1,14,5,-15,11]),\n            [-19,-19,-18,-17,-15,-15,-3,-2,-1,-1,2,4,4,5,5,6,7,7,9,11,11,11,13,14,14]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/sorting/Sort.mpw";
        userFunctionsTestsMap.put("Sort",testString);

        testString = new String[3];
        testString[0] = "73";
        testString[1] = "\nVerify(Bernoulli(24), -236364091/2730);\n\n";
        testString[2] = "/org/mathpiper/scripts4/specfunc/Bernoulli.mpw";
        userFunctionsTestsMap.put("Bernoulli",testString);

        testString = new String[3];
        testString[0] = "44";
        testString[1] = "\n// should return unevaluaed\nVerify( BesselJ(0,x), BesselJ(0,x) );\n\n";
        testString[2] = "/org/mathpiper/scripts4/specfunc/BesselJ.mpw";
        userFunctionsTestsMap.put("BesselJ",testString);

        testString = new String[3];
        testString[0] = "61";
        testString[1] = "\nVerify(Gamma(1/2), Sqrt(Pi));\n\n";
        testString[2] = "/org/mathpiper/scripts4/specfunc/Gamma.mpw";
        userFunctionsTestsMap.put("Gamma",testString);

        testString = new String[3];
        testString[0] = "82";
        testString[1] = "\n/* Bug #11 */\nVerify(ArcCos(Cos(beta)) !=? beta, True);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stdfuncs/ArcCos.mpw";
        userFunctionsTestsMap.put("ArcCos",testString);

        testString = new String[3];
        testString[0] = "83";
        testString[1] = "\nNumericEqual(\nRoundToN(N(ArcSin(0.0000000321232123),50),50)\n, 0.000000032123212300000005524661243020493367846793163005802\n,50);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stdfuncs/ArcSin.mpw";
        userFunctionsTestsMap.put("ArcSin",testString);

        testString = new String[3];
        testString[0] = "101";
        testString[1] = "\nVerify(Cos(0),1);\nVerify(Cos(2*Pi), 1);\nVerify(Cos(4*Pi), 1);\nVerify(N(Cos(Pi/6)), N(Sqrt(3/4)));\nNumericEqual(N(Cos(Pi/2),BuiltinPrecisionGet()+1), 0,BuiltinPrecisionGet());\n\n";
        testString[2] = "/org/mathpiper/scripts4/stdfuncs/Cos.mpw";
        userFunctionsTestsMap.put("Cos",testString);

        testString = new String[3];
        testString[0] = "11";
        testString[1] = "\nVerify(Cot(x),1/Tan(x));\n\n";
        testString[2] = "/org/mathpiper/scripts4/stdfuncs/Cot.mpw";
        userFunctionsTestsMap.put("Cot",testString);

        testString = new String[3];
        testString[0] = "12";
        testString[1] = "\nVerify(Sech(x),1/Cosh(x));\n\n";
        testString[2] = "/org/mathpiper/scripts4/stdfuncs/Sech.mpw";
        userFunctionsTestsMap.put("Sech",testString);

        testString = new String[3];
        testString[0] = "88";
        testString[1] = "\nVerify(N(Sin(a)),Sin(a));\n\nVerify(Sin(2*Pi), 0);\nVerify(Sin(3*Pi/2)+1, 0);\nVerify(Sin(Pi/2), 1);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stdfuncs/Sin.mpw";
        userFunctionsTestsMap.put("Sin",testString);

        testString = new String[3];
        testString[0] = "48";
        testString[1] = "\n/* Here follow some tests for MathBitCount. These were written while creating\n   the Java version, fixing BitCount in the process.\n */\nVerify(MathBitCount(3),2);\nVerify(MathBitCount(3.0),2);\n\nVerify(MathBitCount(4),3);\nVerify(MathBitCount(4.0),3);\n\nVerify(MathBitCount(0),0);\nVerify(MathBitCount(0.0),0);\n\nVerify(MathBitCount(0.5),0);\nVerify(MathBitCount(0.25),-1);\nVerify(MathBitCount(0.125),-2);\nVerify(MathBitCount(0.0125),-6);\n\nVerify(MathBitCount(-3),2);\nVerify(MathBitCount(-3.0),2);\n\nVerify(MathBitCount(-4),3);\nVerify(MathBitCount(-4.0),3);\n\nVerify(MathBitCount(-0),0);\nVerify(MathBitCount(-0.0),0);\n\nVerify(MathBitCount(-0.5),0);\nVerify(MathBitCount(-0.25),-1);\nVerify(MathBitCount(-0.125),-2);\nVerify(MathBitCount(-0.0125),-6);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stdfuncs/elemfuncs/MathBitCount.mpw";
        userFunctionsTestsMap.put("MathBitCount",testString);

        testString = new String[3];
        testString[0] = "31";
        testString[1] = "\nVerify(ExpNum(0),1);\nNumericEqual(ExpNum(0e-1),1,BuiltinPrecisionGet());\n\n";
        testString[2] = "/org/mathpiper/scripts4/stdfuncs/numerical/ExpNum.mpw";
        userFunctionsTestsMap.put("ExpNum",testString);

        testString = new String[3];
        testString[0] = "22";
        testString[1] = "\nVerify(InternalLnNum(1), 0);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stdfuncs/numerical/InternalLnNum.mpw";
        userFunctionsTestsMap.put("InternalLnNum",testString);

        testString = new String[3];
        testString[0] = "44";
        testString[1] = "\n// LogN used to hang on *all* input\nVerify(LogN(2)!=?0,True);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stdfuncs/numerical/LogN.mpw";
        userFunctionsTestsMap.put("LogN",testString);

        testString = new String[3];
        testString[0] = "86";
        testString[1] = "\nVerify(IntPowerNum(3*10^100, 0, MultiplyN,1), 1);\nVerify(IntPowerNum(3, 3, MultiplyN,1), 27);\nVerify(IntPowerNum(HilbertMatrix(2), 4, *,  Identity(2)), [[289/144,29/27],[29/27,745/1296]]);\nVerify(IntPowerNum(3,100,[[x,y],Modulo(x*y,7)],1), 4);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stdfuncs/nummethods/IntPowerNum.mpw";
        userFunctionsTestsMap.put("IntPowerNum",testString);

        testString = new String[3];
        testString[0] = "158";
        testString[1] = "\nBuiltinPrecisionSet(22);\n\nNumericEqual(NewtonNum([[x], x+Sin(x)], 3, 5, 3), 3.14159265358979323846,20);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stdfuncs/nummethods/NewtonNum.mpw";
        userFunctionsTestsMap.put("NewtonNum",testString);

        testString = new String[3];
        testString[0] = "237";
        testString[1] = "\nBuiltinPrecisionSet(22);\nNumericEqual(RoundTo(SumTaylorNum(1, [[k],1/k!], [[k],1/k], 21),21), 2.718281828459045235359,21);\nNumericEqual(RoundTo(SumTaylorNum(1, [[k],1/k!], 21),21), 2.718281828459045235359,21);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stdfuncs/nummethods/SumTaylorNum.mpw";
        userFunctionsTestsMap.put("SumTaylorNum",testString);

        testString = new String[3];
        testString[0] = "63";
        testString[1] = "\nVerify(Abs(Undefined),Undefined);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/Abs.mpw";
        userFunctionsTestsMap.put("Abs",testString);

        testString = new String[3];
        testString[0] = "76";
        testString[1] = "\nTestMathPiper(Expand((1+x)^2),x^2+2*x+1);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/Expand.mpw";
        userFunctionsTestsMap.put("Expand",testString);

        testString = new String[3];
        testString[0] = "75";
        testString[1] = "\n// Discovered that Floor didn't handle new exponent notation\nVerify(Floor(1001.1e-1),100);\nVerify(Floor(10.01e1),100);\nVerify(Floor(100.1),100);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/Floor.mpw";
        userFunctionsTestsMap.put("Floor",testString);

        testString = new String[3];
        testString[0] = "119";
        testString[1] = "\nVerify( Gcd( 324 + 1608*I, -11800 + 7900*I ),Complex(-52,16) );\n// I changed from Complex(-4,4) to Complex(4,4) as the GaussianGcd algorithm suddenly returned this instead.\n// However, as it turned out it was a bug in FloorN, introduced when\n// we moved to the new number classes (so the numbers did not get converted\n// to string and back any more). The number got prematurely truncated with\n// this test case (regression test added to regress.yts also).\n//TODO we can expand this with randomized tests\nVerify( Gcd( 7300 + 12*I, 2700 + 100*I), Complex(-4,4) );\n\n\n// Bug was found: gcd sometimes returned 0! Very bad, since the\n// value returned by gcd is usually used to divide out greatest\n// common divisors, and dividing by zero is not a good idea.\nVerify(Gcd(0,0),1);\nVerify(Gcd([0]),1);\n\n\n\nVerifyGaussianGcd(x,y):=\n{\n  Local(gcd);\n  gcd:=Gcd(x,y);\n//  Echo(x/gcd);\n//  Echo(y/gcd);\n  Verify(GaussianInteger?(x/gcd) And? GaussianInteger?(y/gcd),True);\n};\nVerifyGaussianGcd(324 + 1608*I, -11800 + 7900*I);\nVerifyGaussianGcd(7300 + 12*I, 2700 + 100*I);\nVerifyGaussianGcd(120-I*200,-336+50*I);\n\n/* Bug #3 */\nKnownFailure(Gcd(10,3.3) !=? 3.3 And? Gcd(10,3.3) !=? 1);\n/* I don't know what the answer should be, but buth 1 and 3.3 seem */\n/* certainly wrong. */\nVerify(Gcd(-10, 0), 10);\nVerify(Gcd(0, -10), 10);\n\n\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/Gcd.mpw";
        userFunctionsTestsMap.put("Gcd",testString);

        testString = new String[3];
        testString[0] = "63";
        testString[1] = "\nVerify( Lcm([7,11,13,17]), 7*11*13*17 );\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/Lcm.mpw";
        userFunctionsTestsMap.put("Lcm",testString);

        testString = new String[3];
        testString[0] = "112";
        testString[1] = "\n// Modulo generated a stack overflow on floats.\nVerify(Modulo(1.2,3.4),6/5);\n\n//TODO I need to understand why we need to put Eval here. Modulo(-1.2,3.4)-2.2 returns 0/5 where the 0 is not an integer according to the system. Round-off error?\nNumericEqual(N(Eval(Modulo(-1.2,3.4))),2.2,BuiltinPrecisionGet());\n\nVerify(Modulo(-12/10,34/10),11/5);\n// just a test to see if Verify still gives correct error Verify(N(Modulo(-12/10,34/10)),11/5);\n\n\n// Make sure Mod works threaded\nVerify(Modulo(2,Infinity),2);\nVerify(Modulo([2,1],[2,2]),[0,1]);\nVerify(Modulo([5,1],4),[1,1]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/Modulo.mpw";
        userFunctionsTestsMap.put("Modulo",testString);

        testString = new String[3];
        testString[0] = "67";
        testString[1] = "\nVerify(Sqrt(Infinity),Infinity);\n\n// version 1.0.56: Due to MathBitCount returning negative values sometimes, functions depending on\n// proper functioning failed. MathSqrtFloat failed for instance on N(1/2). It did give the right\n// result for 0.5.\nNumericEqual(N(Sqrt(500000e-6),20),N(Sqrt(0.0000005e6),20),20);\nNumericEqual(N(Sqrt(0.5),20),N(Sqrt(N(1/2)),20),20);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/Sqrt.mpw";
        userFunctionsTestsMap.put("Sqrt",testString);

        testString = new String[3];
        testString[0] = "314";
        testString[1] = "\nVerify(.1<?2,True);\nVerify(0.1<?2,True);\nVerify(.3<?2,True);\nVerify(2<?.1,False);\nVerify(2<?0.1,False);\nVerify(2<?.3,False);\nVerify(1e-5 <? 1, True);\nVerify(1e-5 <? 2e-5, True);\nVerify(1e-1 <? 2e-1, True);\nVerify(1e-15 <? 2e-15, True);\nVerify(1e-5 <? 1e-10, False);\nVerify(1e-5 <? 1e-2, True);\nVerify(-1e-5 <? 1e-5, True);\nVerify(-1e-5 <? 1e-6, True);\n\nBuiltinPrecisionSet(32);\nVerify(0.999999999999999999999999999992 <? 1, True);\nBuiltinPrecisionSet(10);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/comparison_operators.mpw";
        userFunctionsTestsMap.put("<?",testString);

        testString = new String[3];
        testString[0] = "341";
        testString[1] = "\nVerify(.1>?2,False);\nVerify(0.1>?2,False);\nVerify(.3>?2,False);\nVerify(2>?.1,True);\nVerify(2>?0.1,True);\nVerify(2>?.3,True);\nVerify(1e-15 >? 0, True);\nVerify(1e-5 >? 0, True);\nVerify(1e-4 >? 0, True);\nVerify(1e-3 >? 0, True);\nVerify(1e-2 >? 0, True);\nVerify(1e-1 >? 0, True);\nVerify(1e5 >? 0, True);\n\nBuiltinPrecisionSet(32);\nVerify(1.0000000000000000000000000000111 >? 1, True);\nBuiltinPrecisionSet(10);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/comparison_operators.mpw";
        userFunctionsTestsMap.put(">?",testString);

        testString = new String[3];
        testString[0] = "367";
        testString[1] = "\nVerify(1e-5 =? 2e-5, False);\nVerify(1e-5 =? 1e-6, False);\n\nVerify(A<?0,A<?0);\n\nVerify(A>?0,A>?0);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/comparison_operators.mpw";
        userFunctionsTestsMap.put("=?",testString);

        testString = new String[3];
        testString[0] = "382";
        testString[1] = "\nVerify((1==1) !=? True, True);\nVerify((a==a) !=? True, True);\nVerify((1==2) !=? False, True);\nVerify((a==2) !=? False, True);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/comparison_operators.mpw";
        userFunctionsTestsMap.put("!=?",testString);

        testString = new String[3];
        testString[0] = "395";
        testString[1] = "\nVerify(A<?0,A<?0);\nVerify(Undefined<?1, False);\n\nVerify(500 <? 0e-1,False);\n\n//Bug reported by Adrian Vontobel: comparison operators should coerce\n//to a real value as much as possible before trying the comparison.\nVerify(0.2*Pi <? 0.7, True);\nVerify(0.2*Pi <? 0.6, False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/comparison_operators.mpw";
        userFunctionsTestsMap.put("<?",testString);

        testString = new String[3];
        testString[0] = "413";
        testString[1] = "\nVerify(A>?0,A>?0);\nVerify(Undefined>?Undefined, False);\nVerify(Undefined>?1, False);\n\n//Bug reported by Adrian Vontobel: comparison operators should coerce\n//to a real value as much as possible before trying the comparison.\nVerify(0.2*Pi >? 0.5, True);\nVerify(0.2*Pi >? 0.7, False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/comparison_operators.mpw";
        userFunctionsTestsMap.put(">?",testString);

        testString = new String[3];
        testString[0] = "430";
        testString[1] = "\nVerify( 2/3 >=? 1/3, True);\nVerify(1 >=? 1.0, True);\nVerify(-1 >=? -1.0, True);\nVerify(0 >=? 0.0, True);\nVerify(0.0 >=? 0, True);\nVerify(Undefined >=? -4, False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/comparison_operators.mpw";
        userFunctionsTestsMap.put(">=?",testString);

        testString = new String[3];
        testString[0] = "445";
        testString[1] = "\nVerify(1 <=? 1.0, True);\nVerify(-1 <=? -1.0, True);\nVerify(0 <=? 0.0, True);\nVerify(0.0 <=? 0, True);\nVerify(Undefined <=? -4, False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/comparison_operators.mpw";
        userFunctionsTestsMap.put("<=?",testString);

        testString = new String[3];
        testString[0] = "51";
        testString[1] = "\nVerify(Add([1,2,3,4]), 10);\nVerify(Add([1]), 1);\nVerify(Add([]), 0);\nVerify(Add(1,2,3,4), 10);\nVerify(Add(1), 1);\n\n{\n  Local(list);\n  list:=[1,2,3,4,5];\n  Verify(Add(list)/Length(list), 3);\n  list:=[0];\n  Verify(Add(list)/Length(list), 0);\n  list:=[];\n  Verify(Add(list)/Length(list), Undefined);\n};\n\n";
        testString[2] = "/org/mathpiper/scripts4/sums/Add.mpw";
        userFunctionsTestsMap.put("Add",testString);

        testString = new String[3];
        testString[0] = "80";
        testString[1] = "\nVerify(Minimum(0,1),0);\nVerify(Minimum([]), Undefined);\nVerify(Minimum([x]), x);\nVerify(Minimum(x), x);\nVerify(Minimum(Exp(x)), Exp(x));\nVerify(Minimum([1,2,3]), 1);\n// since Minimum(multiple args) is disabled, comment this out\nVerify(Minimum(1,2,3), 1);\nVerify(Minimum(1,2,0), 0);\nVerify(Minimum(5,2,3,4), 2);\nVerify(Minimum(5,2,0,4), 0);\n\n";
        testString[2] = "/org/mathpiper/scripts4/sums/Minimum.mpw";
        userFunctionsTestsMap.put("Minimum",testString);

        testString = new String[3];
        testString[0] = "78";
        testString[1] = "\n// Product didn't check for correct input\nVerify(Product(10), Product(10));\nVerify(Product(-1), Product(-1));\nVerify(Product(Infinity), Product(Infinity));\nVerify(Product(1 .. 10),3628800);\n\nVerify(Product(i,1,3,i),6);\n\n";
        testString[2] = "/org/mathpiper/scripts4/sums/Product.mpw";
        userFunctionsTestsMap.put("Product",testString);

        testString = new String[3];
        testString[0] = "41";
        testString[1] = "\nVerify( Subfactorial(0), 1 );\nVerify( Subfactorial(21), 18795307255050944540 );\n\n";
        testString[2] = "/org/mathpiper/scripts4/sums/Subfactorial.mpw";
        userFunctionsTestsMap.put("Subfactorial",testString);

        testString = new String[3];
        testString[0] = "194";
        testString[1] = "\nVerify( Sum(k,1,n,k), n*(n+1)/2 );\nVerify( Simplify(Sum(k,1,n,k^3)), Simplify( (n*(n+1))^2 / 4 ) );\nVerify( Sum(k,1,Infinity,1/k^2), Zeta(2) );\nVerify( Sum(k,1,Infinity,1/k), Infinity );\nVerify( Sum(i,1,Infinity,1/i), Infinity );\nVerify( Sum(k,1,Infinity,Sqrt(k)), Infinity );\nVerify( Sum(k,2,Infinity,x^k/k!), Exp(x)-(x+1) );\nVerify( Sum(k,1,n,Sin(a)+Sin(b)+p),(Sin(a)+Sin(b)+p)*n );\n\n";
        testString[2] = "/org/mathpiper/scripts4/sums/Sum.mpw";
        userFunctionsTestsMap.put("Sum",testString);

        testString = new String[3];
        testString[0] = "108";
        testString[1] = "\nRulebaseHoldArguments(\"jn\",[x]); //Temporary function used for testing. It is retracted at the end of all the test code.\n\n/* bug reported by Jonathan:\n   All functions that do not have Taylor Expansions about\n   the given point go into infinite loops.\n */\nVerify(Taylor(x,0,5) Ln(x),Undefined);\nVerify(Taylor(x,0,5) 1/x,Undefined);\nVerify(Taylor(x,0,5) 1/Sin(x),Undefined);\n\n// Black-box testing\n\nVerify(Taylor2(x,0,9) Sin(x), x - x^3/6 + x^5/120 - x^7/5040 + x^9/362880);\nVerify(Taylor2(x,0,6) Cos(x), 1 - x^2/2 + x^4/24 - x^6/720);\nVerify(Taylor2(x,0,6) Exp(x),\n       1 + x + x^2/2 + x^3/6 + x^4/24 + x^5/120 + x^6/720);\nVerify(Taylor2(x,1,6) 1/x,\n       1 - (x-1) + (x-1)^2 - (x-1)^3 + (x-1)^4 - (x-1)^5 + (x-1)^6);\nVerify(Taylor2(x,1,6) Ln(x),\n       (x-1) - (x-1)^2/2 + (x-1)^3/3 - (x-1)^4/4 + (x-1)^5/5 - (x-1)^6/6);\nVerify(Taylor2(x,0,6) x/(Exp(x)-1),\n       1 - x/2 + x^2/12 - x^4/720 + x^6/30240);\nVerify(Taylor2(x,0,6) Sin(x)^2+Cos(x)^2, 1);\nTestMathPiper(Taylor2(x,0,14) Sin(Tan(x)) - Tan(Sin(x)),\n          -1/30*x^7 - 29/756*x^9 - 1913/75600*x^11 - 95/7392*x^13);\nTestMathPiper((Taylor2(t,a+1,2) Exp(c*t)),\n          Exp(c*(a+1)) + c*Exp(c*(a+1))*(t-a-1)\n                       + c^2*Exp(c*(a+1))*(t-a-1)^2/2);\n\n// Consistency checks\n\nTestMathPiper(Taylor2(x,0,7) (Sin(x)+Cos(x)),\n          (Taylor2(x,0,7) Sin(x)) + (Taylor2(x,0,7) Cos(x)));\nTestMathPiper(Taylor2(x,0,7) (a*Sin(x)),\n          a * (Taylor2(x,0,7) Sin(x)));\nTestMathPiper(Taylor2(x,0,7) (Sin(x)-Cos(x)),\n          (Taylor2(x,0,7) Sin(x)) - (Taylor2(x,0,7) Cos(x)));\nTestMathPiper(Taylor2(x,0,7) (Sin(x)*Cos(x)),\n          Taylor2(x,0,7) ((Taylor2(x,0,7) Sin(x)) * (Taylor2(x,0,7) Cos(x))));\nTestMathPiper(Taylor2(x,0,7) (Sin(x)/Ln(1+x)),\n          Taylor2(x,0,7) ((Taylor2(x,0,8) Sin(x)) / Taylor2(x,0,8) Ln(1+x)));\nTestMathPiper(Taylor2(t,0,7) (Sin(t)^2),\n          Taylor2(t,0,7) ((Taylor2(t,0,7) Sin(t))^2));\nTestMathPiper(Taylor2(x,0,7) Cos(Ln(x+1)),\n          Taylor2(x,0,7) (Subst(y,Taylor2(x,0,7)Ln(x+1)) Cos(y)));\n\n100 # TaylorLPSCompOrder(_x, jn(_x)) <-- 5;\n100 # TaylorLPSCompCoeff(_x, jn(_x), _k) <-- ToAtom(\"jn\"~ToString(k));\n\nVerify(Taylor2(t,0,8) jn(t), jn5*t^5 + jn6*t^6 + jn7*t^7 + jn8*t^8);\nVerify((Taylor2(x,0,10) Exp(jn(x))),\n       1 + jn5*x^5 + jn6*x^6 + jn7*x^7 + jn8*x^8\n         + jn9*x^9 + (jn10+jn5^2/2)*x^10);\n\n\n\n\n// Some examples of power series\nLocalSymbols(p1,p2,p3,p4,p0,pj,pp,pju0,pj40,pj50,pj51,pj52,pj53,pj54,pc24,pc35,pc46,pc57,pc68) {\np1 := TaylorLPS(0, [1,1,1/2,1/6], x, Exp(x));\np2 := TaylorLPS(1, [1,0,-1/6,0,1/120,0], t, Sin(t));\np3 := TaylorLPS(0, [a0,a1,a2,a3], x, jn(x));\np4 := TaylorLPS(-2, [1,0,-1/2,0,1/24], x, Cos(x)/x^2);\np0 := TaylorLPS(Infinity, [], x, 0); // special case: zero\n\n// TaylorLPS should not evaluate\n\nVerify(p1, Hold(TaylorLPS(0, [1,1,1/2,1/6], x, Exp(x))));\n\n// TaylorLPSCoeffs can get pre-computed coefficients\n\nVerify(TaylorLPSCoeffs(p1, 0, 3), [1,1,1/2,1/6]);\nVerify(TaylorLPSCoeffs(p1, -3, -1), [0,0,0]);\nVerify(TaylorLPSCoeffs(p2, -1, 3), [0,0,1,0,-1/6]);\nVerify(TaylorLPSCoeffs(p3, 0, 3), [a0,a1,a2,a3]);\nVerify(TaylorLPSCoeffs(p4, -1, 1), [0,-1/2,0]);\nVerify(TaylorLPSCoeffs(p0, 1, 5), [0,0,0,0,0]);\n\n// Conversion to power series\n\nVerify(TaylorLPSPowerSeries(p1, 3, x), 1+x+x^2/2+x^3/6);\nVerify(TaylorLPSPowerSeries(p2, 4, t), t-t^3/6);\nVerify(TaylorLPSPowerSeries(p3, 3, s), a0+a1*s+a2*s^2+a3*s^3);\nVerify([TaylorLPSPowerSeries(p4, 2, x), ClearError(\"singularity\")],\n       [Undefined, True]);\nVerify(TaylorLPSPowerSeries(p0, 3, x), 0);\n\n// Construction of new LPS\n\nVerify(TaylorLPSConstruct(x, 1), TaylorLPS(Undefined, [], x, 1));\n\n// TaylorLPSCoeffs can compute new coefficients in-place\n\nVerify(TaylorLPSCoeffs(p1, 0, 4), [1,1,1/2,1/6,1/24]);\nVerify(p1, TaylorLPS(0, [1,1,1/2,1/6,1/24], x, Exp(x)));\np1 := TaylorLPS(0, [1,1,1/2,1/6], x, Exp(x));\n\nVerify(TaylorLPSCoeffs(TaylorLPSConstruct(x, 1), 0, 7),\n       [1, 0, 0, 0, 0, 0, 0, 0]);\nVerify(TaylorLPSCoeffs(TaylorLPSConstruct(x, 0), 0, 7),\n       [0, 0, 0, 0, 0, 0, 0, 0]);\nVerify(TaylorLPSCoeffs(TaylorLPSConstruct(x, 1/x), 0, 7),\n       [0, 0, 0, 0, 0, 0, 0, 0]);\nVerify(TaylorLPSCoeffs(TaylorLPSConstruct(x, x^2), 0, 7),\n       [0, 0, 1, 0, 0, 0, 0, 0]);\nVerify(TaylorLPSCoeffs(TaylorLPSConstruct(x, Exp(x)), 0, 7),\n       [1, 1, 1/2, 1/6, 1/24, 1/120, 1/720, 1/5040]);\nVerify(TaylorLPSCoeffs(TaylorLPSConstruct(x, Ln(1+x)), 0, 7),\n       [0, 1, -1/2, 1/3, -1/4, 1/5, -1/6, 1/7]);\nVerify(TaylorLPSCoeffs(TaylorLPSConstruct(x, Sin(x)), 0, 7),\n       [0, 1, 0, -1/6, 0, 1/120, 0, -1/5040]);\nVerify(TaylorLPSCoeffs(TaylorLPSConstruct(x, Cos(x)), 0, 7),\n       [1, 0, -1/2, 0, 1/24, 0, -1/720, 0]);\n\n// Check order of power series\n\nVerify(TaylorLPSGetOrder(p1), [0,True]);\nVerify(TaylorLPSGetOrder(TaylorLPSConstruct(x, Cos(x))), [0,True]);\nVerify(TaylorLPSGetOrder(TaylorLPSConstruct(x, Sin(x))), [1,True]);\nVerify(TaylorLPSGetOrder(TaylorLPSConstruct(x, x-Sin(x))), [1,False]);\nVerify(TaylorLPSGetOrder(TaylorLPSConstruct(x, 1/x)), [-1,True]);\n\n// User-defined power series\n\npju0 := TaylorLPS(Undefined, [], x, jn(x));\npj40 := TaylorLPS(5, [], x, jn(x));\npj50 := TaylorLPS(5, [], x, jn(x));\npj51 := TaylorLPS(5, [jn5], x, jn(x));\npj52 := TaylorLPS(5, [jn5,jn6], x, jn(x));\npj53 := TaylorLPS(5, [jn5,jn6,jn7], x, jn(x));\npj54 := TaylorLPS(5, [jn5,jn6,jn7,jn8], x, jn(x));\n\npc24 := [0,0,0];\npc35 := [0,0,jn5];\npc46 := [0,jn5,jn6];\npc57 := [jn5,jn6,jn7];\npc68 := [jn6,jn7,jn8];\n\ntlc(_a,_b,_c) <-- TaylorLPSCoeffs(a,b,c);  // abbreviation\n\npj := FlatCopy(pju0); Verify(tlc(pj,2,4), pc24); Verify(pj, pj50);\npj := FlatCopy(pju0); Verify(tlc(pj,3,5), pc35); Verify(pj, pj51);\npj := FlatCopy(pju0); Verify(tlc(pj,4,6), pc46); Verify(pj, pj52);\npj := FlatCopy(pju0); Verify(tlc(pj,5,7), pc57); Verify(pj, pj53);\npj := FlatCopy(pju0); Verify(tlc(pj,6,8), pc68); Verify(pj, pj54);\n\npj := FlatCopy(pj40); Verify(tlc(pj,2,4), pc24); Verify(pj, pj50);\npj := FlatCopy(pj40); Verify(tlc(pj,3,5), pc35); Verify(pj, pj51);\npj := FlatCopy(pj40); Verify(tlc(pj,4,6), pc46); Verify(pj, pj52);\npj := FlatCopy(pj40); Verify(tlc(pj,5,7), pc57); Verify(pj, pj53);\npj := FlatCopy(pj40); Verify(tlc(pj,6,8), pc68); Verify(pj, pj54);\n\npj := FlatCopy(pj50); Verify(tlc(pj,2,4), pc24); Verify(pj, pj50);\npj := FlatCopy(pj50); Verify(tlc(pj,3,5), pc35); Verify(pj, pj51);\npj := FlatCopy(pj50); Verify(tlc(pj,4,6), pc46); Verify(pj, pj52);\npj := FlatCopy(pj50); Verify(tlc(pj,5,7), pc57); Verify(pj, pj53);\npj := FlatCopy(pj50); Verify(tlc(pj,6,8), pc68); Verify(pj, pj54);\n\npj := FlatCopy(pj51); Verify(tlc(pj,2,4), pc24); Verify(pj, pj51);\npj := FlatCopy(pj51); Verify(tlc(pj,3,5), pc35); Verify(pj, pj51);\npj := FlatCopy(pj51); Verify(tlc(pj,4,6), pc46); Verify(pj, pj52);\npj := FlatCopy(pj51); Verify(tlc(pj,5,7), pc57); Verify(pj, pj53);\npj := FlatCopy(pj51); Verify(tlc(pj,6,8), pc68); Verify(pj, pj54);\n\npj := FlatCopy(pj52); Verify(tlc(pj,2,4), pc24); Verify(pj, pj52);\npj := FlatCopy(pj52); Verify(tlc(pj,3,5), pc35); Verify(pj, pj52);\npj := FlatCopy(pj52); Verify(tlc(pj,4,6), pc46); Verify(pj, pj52);\npj := FlatCopy(pj52); Verify(tlc(pj,5,7), pc57); Verify(pj, pj53);\npj := FlatCopy(pj52); Verify(tlc(pj,6,8), pc68); Verify(pj, pj54);\n\npj := FlatCopy(pj53); Verify(tlc(pj,2,4), pc24); Verify(pj, pj53);\npj := FlatCopy(pj53); Verify(tlc(pj,3,5), pc35); Verify(pj, pj53);\npj := FlatCopy(pj53); Verify(tlc(pj,4,6), pc46); Verify(pj, pj53);\npj := FlatCopy(pj53); Verify(tlc(pj,5,7), pc57); Verify(pj, pj53);\npj := FlatCopy(pj53); Verify(tlc(pj,6,8), pc68); Verify(pj, pj54);\n\npj := FlatCopy(pj54); Verify(tlc(pj,2,4), pc24); Verify(pj, pj54);\npj := FlatCopy(pj54); Verify(tlc(pj,3,5), pc35); Verify(pj, pj54);\npj := FlatCopy(pj54); Verify(tlc(pj,4,6), pc46); Verify(pj, pj54);\npj := FlatCopy(pj54); Verify(tlc(pj,5,7), pc57); Verify(pj, pj54);\npj := FlatCopy(pj54); Verify(tlc(pj,6,8), pc68); Verify(pj, pj54);\n\n// Addition\n\npp := TaylorLPS(Undefined, [], x,\n                 TaylorLPSAdd(FlatCopy(p1), FlatCopy(p3)));\nVerify(TaylorLPSCoeffs(pp, 0, 3), [1+a0,1+a1,1/2+a2,1/6+a3]);\nVerify(pp, TaylorLPS(0, [1+a0,1+a1,1/2+a2,1/6+a3], x,\n                      TaylorLPSAdd(p1,p3)));\n\npp := TaylorLPS(0, [1+a0], x,\n                 TaylorLPSAdd(FlatCopy(p1), FlatCopy(p3)));\nVerify(TaylorLPSCoeffs(pp, 0, 3), [1+a0,1+a1,1/2+a2,1/6+a3]);\nVerify(pp, TaylorLPS(0, [1+a0,1+a1,1/2+a2,1/6+a3], x,\n                      TaylorLPSAdd(p1,p3)));\n\npp := TaylorLPSConstruct(x, 1+Ln(x+1));\nVerify(TaylorLPSCoeffs(pp, 0, 4), [1, 1, -1/2, 1/3, -1/4]);\nVerify(pp, TaylorLPS(0, [1,1,-1/2,1/3,-1/4], x, TaylorLPSAdd(pp2,pp1))\n           Where [pp1 == TaylorLPS(0, [1,0,0,0,0], x, 1),\n                  pp2 == TaylorLPS(1, [1,-1/2,1/3,-1/4], x, Ln(x+1))]);\n\npp := TaylorLPSConstruct(a, Exp(a)+jn(a));\nVerify(TaylorLPSCoeffs(pp, -1, 5), [0, 1, 1, 1/2, 1/6, 1/24, 1/120+jn5]);\nVerify(pp, TaylorLPS(0, [1, 1, 1/2, 1/6, 1/24, 1/120+jn5],\n                      a, TaylorLPSAdd(pp1,pp2))\n           Where [pp1 == TaylorLPS(0, [1,1,1/2,1/6,1/24,1/120], a, Exp(a)),\n                  pp2 == TaylorLPS(5, [jn5], a, jn(a))]);\n\n// Scalar multiplication\n\npp := TaylorLPS(Undefined, [], x, TaylorLPSScalarMult(5, FlatCopy(p1)));\nVerify(TaylorLPSCoeffs(pp, 0, 3), [5,5,5/2,5/6]);\nVerify(pp, TaylorLPS(0, [5,5,5/2,5/6], x, TaylorLPSScalarMult(5,p1)));\n\npp := TaylorLPS(0, [5,5], x, TaylorLPSScalarMult(5, FlatCopy(p1)));\nVerify(TaylorLPSCoeffs(pp, 0, 3), [5,5,5/2,5/6]);\nVerify(pp, TaylorLPS(0, [5,5,5/2,5/6], x, TaylorLPSScalarMult(5,p1)));\n\npp := TaylorLPSConstruct(t, (-2)*Sin(t));\nVerify(TaylorLPSCoeffs(pp, -1, 4), [0, 0, -2, 0, 1/3, 0]);\nVerify(pp, TaylorLPS(1, [-2,0,1/3,0], t, TaylorLPSScalarMult(-2, pp1))\n           Where pp1 == TaylorLPS(1, [1,0,-1/6,0], t, Sin(t)));\n\n// Subtraction\n\npp := TaylorLPSConstruct(x, Exp(x)-Cos(x));\n      // zero order term cancels!\nVerify(TaylorLPSCoeffs(pp, 0, 4), [0, 1, 1, 1/6, 0]);\nVerify(pp, TaylorLPS(1, [1,1,1/6,0], x, TaylorLPSAdd(pp1, pp2))\n           Where pp1 == TaylorLPS(0, [1,1,1/2,1/6,1/24], x, Exp(x))\n           Where pp2 == TaylorLPS(0, [-1,0,1/2,0,-1/24], x,\n                                   TaylorLPSScalarMult(-1, pp3))\n           Where pp3 == TaylorLPS(0, [1,0,-1/2,0,1/24], x, Cos(x)));\n\n// Multiplication\n\npp := TaylorLPS(Undefined, [], x,\n                 TaylorLPSMultiply(FlatCopy(p1), FlatCopy(p3)));\nVerify(TaylorLPSCoeffs(pp, 0, 2), [a0, a1+a0, a2+a1+1/2*a0]);\nVerify(pp, TaylorLPS(0, [a0, a1+a0, a2+a1+1/2*a0], x,\n                      TaylorLPSMultiply(p1,p3)));\n\npp := TaylorLPS(0, [a0], x,\n                 TaylorLPSMultiply(FlatCopy(p1), FlatCopy(p3)));\nVerify(TaylorLPSCoeffs(pp, 0, 2), [a0, a1+a0, a2+a1+1/2*a0]);\nVerify(pp, TaylorLPS(0, [a0, a1+a0, a2+a1+1/2*a0], x,\n                      TaylorLPSMultiply(p1,p3)));\n\npp := TaylorLPSConstruct(x, x^2*Ln(x+1));\nVerify(TaylorLPSCoeffs(pp, 0, 4), [0, 0, 0, 1, -1/2]);\nVerify(pp, TaylorLPS(3, [1,-1/2], x, TaylorLPSMultiply(pp1,pp2))\n           Where [pp1 == TaylorLPS(2, [1,0], x, x^2),\n                  pp2 == TaylorLPS(1, [1,-1/2], x, Ln(x+1))]);\n\n// Inversion\n\npp := TaylorLPS(Undefined, [], x, TaylorLPSInverse(FlatCopy(p1)));\nVerify(TaylorLPSCoeffs(pp, 0, 3), [1,-1,1/2,-1/6]);\nVerify(pp, TaylorLPS(0, [1,-1,1/2,-1/6], x, TaylorLPSInverse(p1)));\n\npp := TaylorLPS(Undefined, [], t, TaylorLPSInverse(FlatCopy(p2)));\nVerify(TaylorLPSCoeffs(pp, 0, 2), [0,1/6,-0]);\nVerify(pp, TaylorLPS(-1, [1,0,1/6,0], t, TaylorLPSInverse(p2)));\n\npp := TaylorLPS(Undefined, [], x, TaylorLPSInverse(FlatCopy(p0)));\nVerify({TaylorLPSCoeffs(pp, 0, 0); ClearError(\"div-by-zero\");}, True);\n\npp := TaylorLPSConstruct(x, 1/jn(x));\nVerify(TaylorLPSCoeffs(pp, -7, -4), [0,0,1/jn5,-jn6/jn5^2]);\nVerify(pp, TaylorLPS(-5, [1/jn5,-jn6/jn5^2], x, TaylorLPSInverse(pp1))\n           Where pp1 == TaylorLPS(5, [jn5,jn6], x, jn(x)));\n\npp := TaylorLPSConstruct(x, 1/(Cos(x)^2+Sin(x)^2-1));\nVerify({TaylorLPSCoeffs(pp, 0, 5); ClearError(\"maybe-div-by-zero\");}, True);\n\n// Division\n\npp := TaylorLPSConstruct(x, Exp(x)/Cos(x));\nVerify(TaylorLPSCoeffs(pp, 0, 4), [1, 1, 1, 2/3, 1/2]);\nVerify(pp, TaylorLPS(0, [1,1,1,2/3,1/2], x, TaylorLPSMultiply(pp1, pp2))\n           Where pp1 == TaylorLPS(0, [1,1,1/2,1/6,1/24], x, Exp(x))\n           Where pp2 == TaylorLPS(0, [1,0,1/2,0,5/24], x,\n                                   TaylorLPSInverse(pp3))\n           Where pp3 == TaylorLPS(0, [1,0,-1/2,0,1/24], x, Cos(x)));\n\n// Raising to a natural power\n\n// No tests (TaylorLPSPower is not implemented yet)\n\n// Composition\n\nVerify(TaylorLPSConstruct(x, Ln(Sin(x))),\n       TaylorLPS(Undefined, [], x, TaylorLPSCompose(pp1,pp2))\n       Where [pp1 == TaylorLPS(Undefined, [], x, Ln(x)),\n              pp2 == TaylorLPS(1, [], x, Sin(x))]);\n\nVerify(TaylorLPSConstruct(x, Ln(Cos(x))),\n       TaylorLPS(Undefined, [], x, TaylorLPSCompose(pp1,pp2))\n       Where [pp1 == TaylorLPS(Undefined, [], x, Ln(1+x)),\n              pp2 == TaylorLPS(Undefined, [], x, TaylorLPSAdd(pp3,pp4)),\n              pp3 == TaylorLPS(0, [1], x, Cos(x)),\n              pp4 == TaylorLPS(Undefined, [], x, -1)]);\n\npp := TaylorLPS(Undefined, [], x,\n                 TaylorLPSCompose(FlatCopy(p1), FlatCopy(p2)));\nVerify(TaylorLPSCoeffs(pp, 0, 3), [1, 1, 1/2, 0]);\nVerify(pp, TaylorLPS(0, [1,1,1/2,0], x, TaylorLPSCompose(p1,p2)));\n\n}; // LocalSymbols(p*)\n\n\n\nRetract(\"jn\",1);\n\n";
        testString[2] = "/org/mathpiper/scripts4/sums/Taylor.mpw";
        userFunctionsTestsMap.put("Taylor",testString);

        testString = new String[3];
        testString[0] = "91";
        testString[1] = "\nVerify(261! - 261*260!, 0);\nVerify(300! / 250!, 251***300);\n\n// Verify that postfix operators can be applied one after the other\n// without brackets\nVerify((3!) !, 720);\nVerify(3! !, 720);\n\n";
        testString[2] = "/org/mathpiper/scripts4/sums/exclamationpoint_operator.mpw";
        userFunctionsTestsMap.put("Factorial",testString);

        testString = new String[3];
        testString[0] = "37";
        testString[1] = "\nRulebaseHoldArguments(\"v\",[x]); //A function for testing purposes. It is retracted at the end of the testing code.\nRulebaseHoldArguments(\"X\",[x]); //A function for testing purposes. It is retracted at the end of the testing code.\n\nTestMathPiper(TSimplify( TSum([j]) Delta(i,j)*v(j) ),v(i));\nTestMathPiper(TSimplify( TSum([j,i]) Delta(i,j)*Delta(i,j) ), Ndim);\nTestMathPiper(TSimplify( TSum([j,i]) Delta(i,j)*Delta(j,i) ), Ndim);\nTestMathPiper(TSimplify( TSum([j]) Delta(i,j)*Delta(j,k) ), Delta(i,k));\nTestMathPiper(TSimplify( TSum([i]) v(i)*v(i) ), TSum([i])(v(i)^2));\nRetract(\"v\",1);\nRulebaseHoldArguments(\"v\",[ii]);\nf(i,j):=v(i)*v(j);\nTestMathPiper(f(i,i),v(i)^2);\nTestMathPiper(TSimplify( TSum([i]) f(i,i) ),TSum([i])(v(i)^2));\nTestMathPiper(TSimplify( TSum([j]) Delta(i,j)*f(j,k) ),v(i)*v(k));\n\nTestMathPiper(TSimplify(TSum([i,j]) Delta(i,j)*f(i,j) ),  TSum([j])v(j)^2);\nTestMathPiper(TSimplify(TSum([i])X(j)*TD(i)X(i)),  Ndim*X(j));\nTestMathPiper(TSimplify(TSum([i]) TD(i)(X(i)*X(j)) ), Ndim*X(j)+X(j));\nTestMathPiper(TSimplify(TSum([i]) X(i)*TD(i)X(j) ), X(j));\nTestMathPiper(TSimplify(TSum([i])TD(i)v(i)),  TSum([i])TD(i)v(i));\n\nTestMathPiper(TSimplify(TSum([i,j])TD(i)TD(j)(X(i)*X(j))), Ndim+Ndim^2);\nTestMathPiper(TSimplify(TSum([i])TD(i)(X(i)*X(j)*X(j))),  Ndim*X(j)^2+2*X(j)^2);\nTestMathPiper(TSimplify(TSum([i,j,k])TD(i)TD(j)TD(k)(X(i)*X(j)*X(k))),  3*Ndim^2+2*Ndim+Ndim^3);\n\n\nRetract(\"v\",1);\nRetract(\"X\",1);\n\n";
        testString[2] = "/org/mathpiper/scripts4/tensor/TSum.mpw";
        userFunctionsTestsMap.put("tensors",testString);

        testString = new String[3];
        testString[0] = "68";
        testString[1] = "\nLogicTest([A],A And? A,      A);\nLogicTest([A],A And? True,   A);\nLogicTest([A],A And? False,  False);\nLogicTest([A],A Or?  True,   True);\nLogicTest([A],A Or?  False,  A);\nLogicTest([A],A Or?  Not? A,  True);\nLogicTest([A],A And? Not? A,  False);\nLogicTest([A,B],(A And? B) Or? (A And? B),             A And? B);\nLogicTest([A,B],A Or? (A And? B),                     A And?(A Or? B));\nLogicTest([A,B],(A And? B) And? A,                    (A And? B) And? A);\nLogicTest([A,B],Not? (A And? B) And? A,                (Not? A Or? Not? B) And? A);\nLogicTest([A,B],(A Or? B) And? Not? A,            B And? Not? A);\nLogicTest([A,B,C],(A Or? B) And? (Not? A Or? C),     (A Or? B) And? (C Or? Not? A));\nLogicTest([A,B,C],(B Or? A) And? (Not? A Or? C),     (A Or? B) And? (C Or? Not? A));\nLogicTest([A,B,C], A And? (A Or? B Or? C),       A);\nLogicTest([A,B,C], A And? (Not? A Or? B Or? C),  A And? (B Or? C));\n\n";
        testString[2] = "/org/mathpiper/scripts4/testers/LogicTest.mpw";
        userFunctionsTestsMap.put("LogicTest",testString);

        testString = new String[3];
        testString[0] = "115";
        testString[1] = "\nVerify( LaplaceTransform(t,s,1), 1/s );\nVerify( LaplaceTransform(t,s,t^3), 6/s^4);\nVerify( LaplaceTransform(t,s,t), 1/s^2 );\nVerify( LaplaceTransform(t,s,t*Exp(4*t)), 1/(4*(s/4-1))^2 );\nVerify( LaplaceTransform(t,s,Exp(4*t)*Cos(4*t)), (s-4)/(16*(((s-4)/4)^2+1)) );\nVerify( LaplaceTransform(t,s,t^3*Cosh(t)) Where s==2, 82/27 );\n\n";
        testString[2] = "/org/mathpiper/scripts4/transforms/laplace/LaplaceTransform.mpw";
        userFunctionsTestsMap.put("LaplaceTransform",testString);

        testString = new String[3];
        testString[0] = "518";
        testString[1] = "\nTestMathPiper(TrigSimpCombine(Exp(A*X)),Exp(A*X));\nTestMathPiper(TrigSimpCombine(x^Sin(a*x+b)),x^Sin(a*x+b));\n\n";
        testString[2] = "/org/mathpiper/scripts4/trigsimp/TrigSimpCombine.mpw";
        userFunctionsTestsMap.put("TrigSimpCombine",testString);

        testString = new String[3];
        testString[0] = "42";
        testString[1] = "\nVerify(CanBeUni(x^(-1)),False);\n\n/* Bug report from Magnus Petursson regarding determinants of matrices that have symbolic entries */\nVerify(CanBeUni(Determinant([[a,b],[c,d]])),True);\n\n";
        testString[2] = "/org/mathpiper/scripts4/univar/CanBeUni.mpw";
        userFunctionsTestsMap.put("CanBeUni",testString);

        testString = new String[3];
        testString[0] = "66";
        testString[1] = "\n// Coef accepted non-integer arguments as second argument, and\n// crashed on it.\nVerify(Coef(3*Pi,Pi),Coef(3*Pi,Pi));\nVerify(Coef(3*Pi,x), Coef(3*Pi,x));\n\n";
        testString[2] = "/org/mathpiper/scripts4/univar/Coef.mpw";
        userFunctionsTestsMap.put("Coef",testString);

        testString = new String[3];
        testString[0] = "45";
        testString[1] = "\nTestMathPiper(Content(1/2*x+1/2),1/2);\n\nTestMathPiper(Content(1/2*x+1/3),1/6);\n\n";
        testString[2] = "/org/mathpiper/scripts4/univar/Content.mpw";
        userFunctionsTestsMap.put("Content",testString);

        testString = new String[3];
        testString[0] = "97";
        testString[1] = "\n// Univariates in Pi did not get handled well, due to Pi being\n// considered a constant, non-variable.\nVerify(Degree(Pi,Pi),1);\nVerify(Degree(2*Pi,Pi),1);\n\n";
        testString[2] = "/org/mathpiper/scripts4/univar/Degree.mpw";
        userFunctionsTestsMap.put("Degree",testString);

        testString = new String[3];
        testString[0] = "47";
        testString[1] = "\nTestMathPiper(PrimitivePart(1/2*x+1/2),x+1);\n\nTestMathPiper(PrimitivePart(1/2*x+1/3),3*x+2);\n\n";
        testString[2] = "/org/mathpiper/scripts4/univar/PrimitivePart.mpw";
        userFunctionsTestsMap.put("PrimitivePart",testString);

        testString = new String[3];
        testString[0] = "232";
        testString[1] = "\nNextTest(\"Cyclotomic Polynomials\");\n\nVerify(Cyclotomic(1,x),x-1);\nVerify(Cyclotomic(5,x),x^4+x^3+x^2+x+1);\nVerify(Cyclotomic(8,z),z^4+1);\nVerify(Cyclotomic(10,y),y^4-y^3+y^2-y+1);\nVerify(Cyclotomic(15,x),x^8-x^7+x^5-x^4+x^3-x+1);\n\n";
        testString[2] = "/org/mathpiper/scripts4/univar/cyclotomic/Cyclotomic.mpw";
        userFunctionsTestsMap.put("Cyclotomic",testString);
    }

    public String[] getUserFunctionScript(String testName)
    {
        return (String[]) userFunctionsTestsMap.get(testName);
    }

    public Map getUserFunctionsMap()
    {
        return userFunctionsTestsMap;
    }

    public String[] getBuiltInFunctionScript(String testName)
    {
        return (String[]) builtInFunctionsTestsMap.get(testName);
    }

    public Map getBuiltInFunctionsMap()
    {
        return builtInFunctionsTestsMap;
    }
}
