package org.mathpiper;

import java.util.HashMap;

import java.util.Map;

public class Tests {

    private HashMap userFunctionsTestsMap = null;

    private HashMap builtInFunctionsTestsMap = null;

    private HashMap documentationExamplesTestsMap = null;

    public Tests() {

        userFunctionsTestsMap = new HashMap();

        builtInFunctionsTestsMap = new HashMap();

        documentationExamplesTestsMap = new HashMap();

        String[] testString;


        testString = new String[3];
        testString[0] = "83";
        testString[1] = "Verify(AbsN(-1) , 1);\nUnassign(*);\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Abs.java";
        documentationExamplesTestsMap.put("AbsNDocs",testString);

        testString = new String[3];
        testString[0] = "151";
        testString[1] = "\nVerify(False And? False,False);\nVerify(True And? False,False);\nVerify(False And? True,False);\nVerify(True And? True,True);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/And_.java";
        builtInFunctionsTestsMap.put("And?",testString);

        testString = new String[3];
        testString[0] = "83";
        testString[1] = "\nVerify(Atom?('[a,b,c]),False);\nVerify(Atom?('a),True);\nVerify(Atom?(123),True);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Atom_.java";
        builtInFunctionsTestsMap.put("Atom?",testString);

        testString = new String[3];
        testString[0] = "112";
        testString[1] = "\nVerify(Concat('[a,b],'[c,d]), '[a,b,c,d]);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Concatenate.java";
        builtInFunctionsTestsMap.put("Concat",testString);

        testString = new String[3];
        testString[0] = "100";
        testString[1] = "\nVerify(ConcatStrings(\"a\",\"b\",\"c\"),\"abc\");\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/ConcatenateStrings.java";
        builtInFunctionsTestsMap.put("ConcatStrings",testString);

        testString = new String[3];
        testString[0] = "77";
        testString[1] = "\nVerify(Equal?('a,'b),False);\nVerify(Equal?('a,'a),True);\nVerify(Equal?('[a,b],'[a]),False);\nVerify(Equal?('[a,b],'[a,b]),True);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Equal_.java";
        builtInFunctionsTestsMap.put("Equal?",testString);

        testString = new String[3];
        testString[0] = "129";
        testString[1] = "\nVerify(True Equivales? True,True);\nVerify(True Equivales? False,False);\nVerify(False Equivales? True,False);\nVerify(False Equivales? False,True);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Equivales_.java";
        builtInFunctionsTestsMap.put("Equivales?",testString);

        testString = new String[3];
        testString[0] = "186";
        testString[1] = "\n  //Test ExceptionCatch and ExceptionGet.\n  Local(exception);\n  exception := False;\n  ExceptionCatch(Check(False, \"Unspecified\", \"some error\"), exception := ExceptionGet());\n  Verify(exception =? False, False);\n\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/ExceptionCatch.java";
        builtInFunctionsTestsMap.put("ExceptionCatch",testString);

        testString = new String[3];
        testString[0] = "94";
        testString[1] = "\n//Reverse and FlatCopy (and some friends) would segfault in the past if passed a string as argument.\n//I am not opposed to overloading these functions to also work on strings per se, but for now just\n//check that they return an error in stead of segfaulting.\n//\nVerify(ExceptionCatch(FlatCopy(\"abc\"),\"Exception\"), \"Exception\");\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/FlatCopy.java";
        builtInFunctionsTestsMap.put("FlatCopy",testString);

        testString = new String[3];
        testString[0] = "122";
        testString[1] = "\nVerify(FromBase(16,\"1e\"),30);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/FromBase.java";
        builtInFunctionsTestsMap.put("FromBase",testString);

        testString = new String[3];
        testString[0] = "99";
        testString[1] = "\n// FunctionToList and ListToFunction coredumped when their arguments were invalid.\nVerify(FunctionToList(Sqrt(_x)),'[Sqrt,_x]);\n\n{\n  Local(exception);\n\n  exception := False;\n  ExceptionCatch(FunctionToList(1.2), exception := ExceptionGet());\n  Verify(exception =? False, False);\n};\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/FunctionToList.java";
        builtInFunctionsTestsMap.put("FunctionToList",testString);

        testString = new String[3];
        testString[0] = "78";
        testString[1] = "\nRulebaseHoldArguments(\"a\", [_b]);\nVerify(Function?(a('b)),True);\nRetract(\"a\", *);\nVerify(Function?(_a),False);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Function_.java";
        builtInFunctionsTestsMap.put("Function?",testString);

        testString = new String[3];
        testString[0] = "77";
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
        testString[1] = "\nVerify(Integer?(123),True);\nVerify(Integer?(123.123),False);\nVerify(Integer?(_a),False);\nVerify(Integer?([_a]),False);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Integer_.java";
        builtInFunctionsTestsMap.put("Integer?",testString);

        testString = new String[3];
        testString[0] = "78";
        testString[1] = "\nVerify(LessThan?(2,3),True);\nVerify(LessThan?(3,2),False);\n\nVerify(LessThan?(-1e-115, 0), True);\nVerify(LessThan?(-1e-15, 0), True);\nVerify(LessThan?(-1e-10, 0), True);\nVerify(LessThan?(-1e-5, 0), True);\nVerify(LessThan?(-1e-1, 0), True);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/LessThan_.java";
        builtInFunctionsTestsMap.put("LessThan?",testString);

        testString = new String[3];
        testString[0] = "110";
        testString[1] = "\n// FunctionToList and ListToFunction coredumped when their arguments were invalid.\nVerify(ListToFunction('[Sqrt,_x]),Sqrt(_x));\n\n{\n  Local(exception);\n \n  exception := False;\n  ExceptionCatch(ListToFunction(1.2), exception := ExceptionGet());\n  Verify(exception =? False, False);\n};\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/ListToFunction.java";
        builtInFunctionsTestsMap.put("ListToFunction",testString);

        testString = new String[3];
        testString[0] = "83";
        testString[1] = "\nVerify(List?([_a,_b,_c]),True);\nVerify(List?(_a),False);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/List_.java";
        builtInFunctionsTestsMap.put("List?",testString);

        testString = new String[3];
        testString[0] = "147";
        testString[1] = "\n{\n  Verify(Assigned?([]),False);\n  Local(a);\n  Verify(Assigned?(a),False);\n  a:=1;\n  Verify(Assigned?(a),True);\n  Unassign(a);\n  Verify(Assigned?(a),False);\n};\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Local.java";
        builtInFunctionsTestsMap.put("Local",testString);

        testString = new String[3];
        testString[0] = "98";
        testString[1] = "\nVerify(Not?(True),False);\nVerify(Not?(False),True);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Not_.java";
        builtInFunctionsTestsMap.put("Not?",testString);

        testString = new String[3];
        testString[0] = "111";
        testString[1] = "\nVerify(Number?(123),True);\nVerify(Number?(123.123),True);\nVerify(Number?(_a),False);\nVerify(Number?([_a]),False);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Number_.java";
        builtInFunctionsTestsMap.put("Number?",testString);

        testString = new String[3];
        testString[0] = "141";
        testString[1] = "\nVerify(False Or? False,False);\nVerify(True Or? False,True);\nVerify(False Or? True,True);\nVerify(True Or? True,True);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/Or_.java";
        builtInFunctionsTestsMap.put("Or?",testString);

        testString = new String[3];
        testString[0] = "88";
        testString[1] = "\nVerify(PipeFromString(\"(+ _a _b)\") ParseLisp(),_a+_b);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/ParseLisp.java";
        builtInFunctionsTestsMap.put("ParseLisp",testString);

        testString = new String[3];
        testString[0] = "89";
        testString[1] = "\nVerify(PipeFromString(\"_a+_b;\") ParseMathPiper(),_a+_b);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/ParseMathPiper.java";
        builtInFunctionsTestsMap.put("ParseMathPiper",testString);

        testString = new String[3];
        testString[0] = "89";
        testString[1] = "\n Verify(ToAtom(\"_a\"),_a);\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/ToAtom.java";
        builtInFunctionsTestsMap.put("ToAtom",testString);

        testString = new String[3];
        testString[0] = "117";
        testString[1] = "\nVerify(ToBase(16,30),\"1e\");\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/ToBase.java";
        builtInFunctionsTestsMap.put("ToBase",testString);

        testString = new String[3];
        testString[0] = "115";
        testString[1] = "\n Verify(ToString(_a),\"_a\");\n Verify(ToString(_x^2),\"_x^2\");\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/ToString.java";
        builtInFunctionsTestsMap.put("ToString",testString);

        testString = new String[3];
        testString[0] = "208";
        testString[1] = "\n// Reported by Serge: xml tokenizer not general enough\nVerify(XmlExplodeTag(\"<p/>\"),   XmlTag(\"P\",[],\"OpenClose\"));\nVerify(XmlExplodeTag(\"<p / >\"), XmlTag(\"P\",[],\"OpenClose\"));\n\n";
        testString[2] = "/org/mathpiper/builtin/functions/core/XmlExplodeTag.java";
        builtInFunctionsTestsMap.put("XmlExplodeTag",testString);

        testString = new String[3];
        testString[0] = "139";
        testString[1] = "Verify(2*Infinity , Infinity);\nVerify(2 <? Infinity , True);\nUnassign(*);\n";
        testString[2] = "/org/mathpiper/scripts4/a_initialization/miscellaneous/miscellaneousdocs.mpw";
        documentationExamplesTestsMap.put("InfinityDocs",testString);

        testString = new String[3];
        testString[0] = "170";
        testString[1] = "Verify(0*Infinity, Undefined);\nVerify(Sin(Infinity) , Undefined);\nVerify(Undefined+2*Exp(Undefined), Undefined);\nUnassign(*);\n";
        testString[2] = "/org/mathpiper/scripts4/a_initialization/miscellaneous/miscellaneousdocs.mpw";
        documentationExamplesTestsMap.put("UndefinedDocs",testString);

        testString = new String[3];
        testString[0] = "110";
        testString[1] = "Verify((lst := [_a,_b,_c,13,19]) , [_a,_b,_c,13,19]);\nVerify(Nth(lst, 3) , _c);\nVerify(lst[3] , _c);\nVerify(Nth(lst, [3,4,1]) , [_c,13,_a]);\nVerify(Nth(_b*(_a+_c), 2) , _a+_c);\nUnassign(*);\n";
        testString[2] = "/org/mathpiper/scripts4/a_initialization/standard/A_Nth.mpw";
        documentationExamplesTestsMap.put("NthDocs",testString);

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
        testString[1] = "\nNextTest(\"Test arithmetic\");\n\nNextTest(\"Basic calculations\");\nVerify(3 + 2 , 5);\nVerify(3-7, -4);\nVerify(1 =? 2 , 0 =? -1);\nVerify(5 ^ 2 , 25);\n\nVerify(Zero?(0.000),True);\n\nVerify(2/5,Hold(2/5));\nVerify(Zero?(NM(2/5)-0.4),True);\nVerify(Rational?(2),True);\nVerify(Rational?(2/5),True);\nVerify(Rational?(-2/5),True);\nVerify(Rational?(2.0/5),False);\nVerify(Rational?(Pi/2),False);\nVerify(Numerator(2/5),2);\nVerify(Denominator(2/5),5);\n\nVerifyArithmetic(10,5,8);\nVerifyArithmetic(10000000000,5,8);\nVerifyArithmetic(10,50,80);\nVerifyArithmetic(10000,50,88);\n\nVerify(4!,24);\nVerify(BinomialCoefficient(2,1),2);\n\nNextTest(\"Testing math stuff\");\nVerify(1*_a,_a);\nVerify(_a*1,_a);\nVerify(0*_a,0);\nVerify(_a*0,0);\nVerify(_aa-_aa,0);\nVerify(0+_a,_a);\nVerify(_a+0,_a);\n\nVerify(2+3,5);\nVerify(2*3,6);\n\nVerify(2+3*4,14);\nVerify(3*4+2,14);\nVerify(3*(4+2),18);\nVerify((4+2)*3,18);\n\nVerify(15/5,3);\n\nVerify(-2+3,1);\nVerify(-2.01+3.01,1.);\n\n\nTesting(\"IntegerOperations\");\nVerify(1<<10,1024);\nVerify(1024>>10,1);\nVerify(Modulo(10,3),1);\nVerify(Quotient(10,3),3);\nVerify(GcdN(55,10),5);\n\nVerify(Modulo(2,Infinity),2);\nVerify(Modulo([0,1,2,3,4,5,6],2),[0,1,0,1,0,1,0]);\nVerify(Modulo([0,1,2,3,4,5,6],[2,2,2,2,2,2,2]),[0,1,0,1,0,1,0]);\n\nTesting(\"PowerN\");\n// was broken in the gmp version\nVerify(PowerN(19, 0), 1);\nVerify(PowerN(1, -1), 1);\nVerify(PowerN(1, -2), 1);\nVerify(Zero?(PowerN(10, -2)- 0.01),True);\nVerify(PowerN(2, 3), 8);\nNumericEqual(PowerN(2, -3), 0.125,BuiltinPrecisionGet());\n\nTesting(\"Rounding\");\nVerify(Floor(1.2),1);\nVerify(Floor(-1.2),-2);\nVerify(Ceil(1.2),2);\nVerify(Ceil(-1.2),-1);\nVerify(Round(1.49),1);\nVerify(Round(1.51),2);\nVerify(Round(-1.49),-1);\nVerify(Round(-1.51),-2);\n\nTesting(\"Bases\");\nVerify(ToBase(16,255),\"ff\");\nVerify(FromBase(2,\"100\"),4);\n\n// conversion between decimal and binary digits\nVerify(BitsToDigits(2000, 10), 602);\nVerify(DigitsToBits(602, 10), 2000);\n\nLocalSymbols(f,ft)\n{\n  f(x,y):=(Quotient(x,y)*y+Rem(x,y)-x);\n  ft(x,y):=\n  {\n    Verify(f(x,y),0);\n    Verify(f(-x,y),0);\n    Verify(f(x,-y),0);\n    Verify(f(-x,-y),0);\n  };\n  ft(10,4);\n  ft(2.5,1.2);\n};\n\n/* todo:tk:commenting out for the minimal version of the scripts.\nTesting(\"Factorization\");\nVerify(\nEval(Factors(447738843))\n, [[3,1],[17,1],[2729,1],[3217,1]]\n);\n*/\n\n//Exponential notation is now supported in the native arithmetic library too...\nVerify(2e3+1,2001.);\nVerify(2.0e3+1,2001.);\nVerify(2.00e3+1,2001.);\nVerify(2.000e3+1,2001.);\nVerify(2.0000e3+1,2001.);\n\nVerify(1+2e3,2001.);\nVerify(1+2.0e3,2001.);\nVerify(1+2.00e3,2001.);\nVerify(1+2.000e3,2001.);\nVerify(1+2.0000e3,2001.);\n\nNumericEqual(NM(Sqrt(1e4))-100,0,BuiltinPrecisionGet());\nNumericEqual(NM(Sqrt(1.0e4))-100,0,BuiltinPrecisionGet());\n\nVerify(2.0000e3-1,1999.);\n{\n  Local(p);\n  p:=BuiltinPrecisionGet();\n  BuiltinPrecisionSet(12);//TODO this will fail if you drop precision to below 12, for some reason.\n  NumericEqual(RoundToPrecision(10e3*1.2e-3,BuiltinPrecisionGet()),12.,BuiltinPrecisionGet());\n  BuiltinPrecisionSet(p);\n};\nVerify((10e3*1.2e-4)-1.2,0);\n\n//Verify(Zero?(NM(Sin(0.1e1)-Sin(1),30)),True);\n\n\n/*\n{\n  In Dutch they have a saying \"dit verdient geen schoonheidsprijs\" ;-) We need to sort this out.But a passable result, for now.\n   \n  Local(diff);\n  diff := NM(Sin(10e-1)-Sin(1),30);\n//BuiltinPrecisionSet(20);\n//Echo(\"diff = \",diff);\n//Echo(\"diff > -0.00001 = \",diff > -0.00001);\n//Echo(\"diff < 0.00001 = \",diff < 0.00001);\n  Verify(diff >? -0.00001 And? diff <? 0.00001,True);\n};\n*/\n\n/* Jonathan reported a problem with Simplify(-Sqrt(8)/2), which returned some\n * complex expression containing greatest common divisors of square roots.\n * This was fixed by adding some rules dealing with taking the gcd of two objects\n * where at least one is a square root.\n */\nVerify(-Sqrt(8)/2,-Sqrt(2));\nVerify(Sqrt(8)/2,Sqrt(2));\nVerify(Gcd(Sqrt(2),Sqrt(2)),Sqrt(2));\nVerify(Gcd(-Sqrt(2),-Sqrt(2)),Sqrt(2));\nVerify(Gcd(Sqrt(2),-Sqrt(2)),Sqrt(2));\nVerify(Gcd(-Sqrt(2),Sqrt(2)),Sqrt(2));\n\n\n";
        testString[2] = "/org/mathpiper/scripts4/a_initialization/stdarith/arithmetic_test.mpw";
        userFunctionsTestsMap.put("arithmetic",testString);

        testString = new String[3];
        testString[0] = "159";
        testString[1] = "\nVerify((-2)*Infinity,-Infinity);\n\nVerify(Infinity*0,Undefined);\n\n// The following is a classical error: 0*x=0 is only true if\n// x is a number! In this case, it is checked for that the\n// multiplication of 0 with a vector returns a zero vector.\n// This would automatically be caught with type checking.\n// More tests of this ilk are possible: 0*matrix, etcetera.\nVerify(0*[_a,_b,_c],[0,0,0]);\n\nVerify(Undefined*0,Undefined);\n\n";
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
        testString[0] = "79";
        testString[1] = "{\nLocal(precision);\nprecision := BuiltinPrecisionGet();\nBuiltinPrecisionSet(10);\nVerify(MathExpTaylor0(0),1.0);\nVerify(MathExpTaylor0(.2), 1.221402759);\nVerify(MathExpTaylor0(1),  2.718281830);\nVerify(MathExpTaylor0(.234563),  1.264356123);\nBuiltinPrecisionSet(precision);\n};\n";
        testString[2] = "/org/mathpiper/scripts4/base/MathExpTaylor0.mpw";
        userFunctionsTestsMap.put("MathExpTaylor0",testString);

        testString = new String[3];
        testString[0] = "56";
        testString[1] = "\n/* Bug #15 */\nVerify(PowerN(0,0.55), 0);\n// LogN(-1) locks up in gmpnumbers.cpp, will be fixed in scripts\n//FIXME this test should be uncommented eventually\n// Verify(ExceptionCatch(PowerN(-1,-0.5), error), error);\n\n";
        testString[2] = "/org/mathpiper/scripts4/base/PowerN.mpw";
        userFunctionsTestsMap.put("PowerN",testString);

        testString = new String[3];
        testString[0] = "52";
        testString[1] = "Verify(Combinations(10, 4) , 210);\nVerify(BinomialCoefficient(10, 4) , 210);\nUnassign(*);\n";
        testString[2] = "/org/mathpiper/scripts4/combinatorics/Combinations.mpw";
        documentationExamplesTestsMap.put("Combinations;BinomialCoefficientDocs",testString);

        testString = new String[3];
        testString[0] = "62";
        testString[1] = "\nVerify(BinomialCoefficient(0,0),1 );\n\n";
        testString[2] = "/org/mathpiper/scripts4/combinatorics/Combinations.mpw";
        userFunctionsTestsMap.put("BinomialCoefficient",testString);

        testString = new String[3];
        testString[0] = "148";
        testString[1] = "\n/* todo:tk:commenting out for the minimal version of the scripts.\nVerify( Limit(z,2*I) (I*z^4+3*z^2-10*I), Complex(-12,6) );\nKnownFailure( (Limit(n,Infinity) (n^2*I^n)/(n^3+1)) =? 0 );\nVerify( Limit(n,Infinity) n*I^n, Undefined );\n*/\n\nVerify(1/I, -I);\nVerify(I^2, -1);\nVerify(2/(1+I), 1-I);\nVerify(I^3, -I);\nVerify(I^4, 1);\nVerify(I^5, I);\nVerify(1^I, 1);\nVerify(0^I, Undefined);\nVerify(I^(-I), Exp(Pi/2));\nVerify((1+I)^33, 65536+I*65536);\nVerify((1+I)^(-33), (1-I)/131072);\n//Verify(Exp(I*Pi), -1);\n//TestMathPiper((_a+_b*I)*(_c+_d*I), (_a*_c-_b*_d)+I*(_a*_d+_b*_c));\nVerify(Ln(-1), I*Pi);\n//Verify(Ln(3+4*I), Ln(5)+I*ArcTan(4/3));\n\nVerify(Re(2*I-4), -4);\nVerify(Im(2*I-4), 2);\n\n";
        testString[2] = "/org/mathpiper/scripts4/complex/Complex.mpw";
        userFunctionsTestsMap.put("Complex",testString);

        testString = new String[3];
        testString[0] = "44";
        testString[1] = "\n// the following broke evaluation (dr)\nVerify(Conjugate([_a]),[_a]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/complex/Conjugate.mpw";
        userFunctionsTestsMap.put("Conjugate",testString);

        testString = new String[3];
        testString[0] = "44";
        testString[1] = "\n/* Bug #7 */\nVerify(Im(3+I*Infinity), Infinity); /* resolved */\nVerify(Im(3+I*Undefined), Undefined);\n\n";
        testString[2] = "/org/mathpiper/scripts4/complex/Im.mpw";
        userFunctionsTestsMap.put("Im",testString);

        testString = new String[3];
        testString[0] = "156";
        testString[1] = "\nFunction(\"count\",[_from,_to])\n{\n   Local(i);\n   Local(sum);\n   Assign(sum,0);\n   For(i:=from,i<?to,i:=i+1)\n   {\n     Assign(sum,sum+i);\n   };\n   sum;\n};\n\nTesting(\"Function definitions\");\nVerify(count(1,11),55);\n\nRetract(\"count\",*);\n\n";
        testString[2] = "/org/mathpiper/scripts4/deffunc/Function.mpw";
        userFunctionsTestsMap.put("Function",testString);

        testString = new String[3];
        testString[0] = "123";
        testString[1] = "\n{\n  Local(a,b,c,d);\n  MacroRulebaseHoldArguments(foo,[_a,_b]);\n\n  // Simple check\n  foo(_c,_d) <-- [@c,@d];\n  Verify(foo(2,3),Hold([2,3]));\n\n  Macro(\"foo\",[_a]) [@a,a];\n  a:=_A;\n  Verify(foo(_B),[_B,_A]);\n\n  /*\n  Removed from the test because the system now throws exceptions when\\\n  undefined functions are called.\n  Retract(foo,1);\n  Retract(foo,2);\n  Verify(foo(2,3),foo(2,3));\n  Verify(foo(B),foo(B));\n  */\n};\n\n{\n  Local(a,i,tot);\n  a:=100;\n  Retract(\"forloop\",4);\n  Macro(\"forloop\",[_init,_pred,_inc,_body])\n  {\n    @init;\n    While(@pred)\n    {\n      @body;\n      @inc;\n    };\n    True;\n  };\n  tot:=0;\n  forloop(i:=1,i<=?10,i++,tot:=tot+a*i);\n  Verify(i,11);\n  Verify(tot,5500);\n};\n\n{\n  Macro(\"bar\",[_list,...]) Length(@list);\n  Verify(bar(_a,_b,_list,_bar,_list),5);\n};\n\n{\n  Local(x,y,z);\n  RulebaseHoldArguments(\"@\",[_var]);\n  y := _x;\n  Verify(`[@_x,@y],[_x,_x]);\n  z := _u;\n  y:=[@z,@z];\n  Verify(`[@_x,@y],[_x,[@z,@z]]);\n  Verify(`[@_x,`(@y)],[_x,[@_u,@_u]]);\n  y:=Hold(`[@z,@z]);\n\n  Verify(`[@_x,@y],[_x,[_u,_u]]);\n  Verify(`[@_x,`(@y)],[_x,[_u,_u]]);\n  Retract(\"@\",1);\n};\n\n// check that a macro can reach a local from the calling environment.\n{\n  Macro(\"foo\",[_x]) a*(@x);\n  Function(\"bar\",[_x])\n  {\n    Local(a);\n    a:=2;\n    foo(x);\n  };\n  Verify(bar(3),6);\n};\n\n//check that with nested backquotes expansion only expands the top-level expression\n{\n  Local(a,b);\n  a:=2;\n  b:=3;\n  Verify(\n  `{\n     Local(c);\n     c:=@a+@b;\n     `((@c)*(@c));\n  },25);\n};\n\n";
        testString[2] = "/org/mathpiper/scripts4/deffunc/Macro.mpw";
        userFunctionsTestsMap.put("Macro",testString);

        testString = new String[3];
        testString[0] = "65";
        testString[1] = "\nVerify(Apply(\"+\",[2,3]),5);\n{\n  Local(x,y);\n  Verify(Apply('[[x,y],x+y],[2,3]),5);\n  Verify(Apply(Lambda([x,y],x+y),[2,3]),5);\n  Verify(Lambda([x,y],x+y) @ [2,3],5);\n\n  /* Basically the next line is to check that [[x],Length(x)]\n   * behaves in an undesirable way (Length being evaluated\n   * prematurely), so that the next test can then check that\n   * Lambda solves the problem.\n   */\n  Verify(Apply('[[x],Length(x)],[\"aaa\"]),3);\n  Verify(Apply(Lambda([x],Length(x)),[\"aaa\"]),3);\n\n  Verify(_x,_x);\n  Verify(_y,_y);\n\n  Testing(\"ThreadingListables\");\n  x:=[_bb,_cc,_dd];\n  Verify(Sqrt(_aa*x),[Sqrt(_aa*_bb),Sqrt(_aa*_cc),Sqrt(_aa*_dd)]);\n};\n\n";
        testString[2] = "/org/mathpiper/scripts4/functional/Apply.mpw";
        userFunctionsTestsMap.put("Apply",testString);

        testString = new String[3];
        testString[0] = "113";
        testString[1] = "\nBuiltinPrecisionSet(10);\nRetract(\"f\",*);\nRetract(\"f1\",*);\nf(x) := NM(Abs(1/x-1));\nVerify(f(0), Infinity);\nNumericEqual(RoundToN(f(3),BuiltinPrecisionGet()), 0.6666666667,BuiltinPrecisionGet());\nNFunction(\"f1\", \"f\", [_x]);\nVerify(f1(0), Undefined);\nNumericEqual(RoundToN(f1(3),BuiltinPrecisionGet()), 0.6666666667,BuiltinPrecisionGet());\nRetract(\"f\",*);\nRetract(\"f1\",*);\n";
        testString[2] = "/org/mathpiper/scripts4/functional/NFunction.mpw";
        userFunctionsTestsMap.put("NFunction",testString);

        testString = new String[3];
        testString[0] = "48";
        testString[1] = "\n// generate no errors\nVerify(Error?(), False);\nVerify(Error?(\"testing\"), False);\nVerify(Assert(\"testing\") 1=?1, True);\nVerify(Error?(), False);\nVerify(Error?(\"testing\"), False);\nVerify(Assert(\"testing1234\", [1,2,3,4]) 1=?1, True);\nVerify(Error?(), False);\nVerify(Error?(\"testing\"), False);\nVerify(Error?(\"testing1234\"), False);\n\nVerify(PipeToString()DumpErrors(), \"\");\n\n// generate some errors\nVerify(Assert(\"testing\") 1=?0, False);\nVerify(Error?(), True);\nVerify(Error?(\"testing\"), True);\nVerify(Error?(\"testing1234\"), False);\nVerify(Assert(\"testing1234\", [1,2,3,4]) 1=?0, False);\nVerify(Error?(), True);\nVerify(Error?(\"testing\"), True);\nVerify(Error?(\"testing1234\"), True);\n\n// report errors\nVerify(PipeToString()DumpErrors(), \"Error: testing\nError: testing1234: [1, 2, 3, 4]\n\");\n\n// no more errors now\nVerify(Error?(), False);\nVerify(Error?(\"testing\"), False);\nVerify(Error?(\"testing1234\"), False);\n\n// generate some more errors\nVerify(Assert(\"testing\") 1=?0, False);\nVerify(Assert(\"testing1234\", [1,2,3,4]) 1=?0, False);\nVerify(GetError(\"testing1234567\"), False);\n\n// handle errors\nVerify(GetError(\"testing\"), True);\nVerify(Error?(), True);\nVerify(Error?(\"testing\"), True);\nVerify(Error?(\"testing1234\"), True);\n\nVerify(ClearError(\"testing\"), True);\nVerify(Error?(), True);\nVerify(Error?(\"testing\"), False);\nVerify(Error?(\"testing1234\"), True);\n// no more \"testing\" error\nVerify(ClearError(\"testing\"), False);\nVerify(Error?(), True);\nVerify(Error?(\"testing\"), False);\nVerify(Error?(\"testing1234\"), True);\n\nVerify(GetError(\"testing1234\"), [1,2,3,4]);\nVerify(Error?(), True);\nVerify(Error?(\"testing\"), False);\nVerify(Error?(\"testing1234\"), True);\n\nVerify(ClearError(\"testing1234\"), True);\nVerify(Error?(), False);\nVerify(Error?(\"testing\"), False);\nVerify(Error?(\"testing1234\"), False);\nVerify(ClearError(\"testing1234\"), False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/io/Error_.mpw";
        userFunctionsTestsMap.put("error_reporting",testString);

        testString = new String[3];
        testString[0] = "83";
        testString[1] = "\n//Dimensions (Tensor Rank).\n\nVerify(Dimensions(_a),[]);\nVerify(Dimensions([]),[0]);\nVerify(Dimensions([_a,_b]),[2]);\nVerify(Dimensions([[]]),[1,0]);\nVerify(Dimensions([[_a]]),[1,1]);\nVerify(Dimensions([[],_a]),[2]);\nVerify(Dimensions([[_a],_b]),[2]);\nVerify(Dimensions([[],[]]),[2,0]);\nVerify(Dimensions([[],[[]]]),[2]);\nVerify(Dimensions([[_a,_b],[_c]]),[2]);\nVerify(Dimensions([[_a,_b],[_c,_d]]),[2,2]);\nVerify(Dimensions([[_a,_b],[_c,_d],[_e,_f]]),[3,2]);\nVerify(Dimensions([[_a,_b,_c],[_d,_e,_f],[_g,_h,_i]]),[3,3]);\nVerify(Dimensions([[_a,_b,_c],[_d,_e,_f]]),[2,3]);\nVerify(Dimensions([[[_a,_b]],[[_c,_d]]]), [2,1,2]);\nVerify(Dimensions([[[[_a],[_b]]],[[[_c],_d]]]),[2,1,2]);\nVerify(Dimensions([[[[[_a,_b]]]],[[[_c,_d]]]]),[2,1,1]);\nVerify(Dimensions([[[[[_a,_b]]]],[[[_c],[_d]]]]),[2,1]);\nVerify(Dimensions([[[]]]),[1,1,0]);\nVerify(Dimensions([[[_a]]]),[1,1,1]);\nVerify(Dimensions([[[[_a]]],[[[_b]]]]),[2,1,1,1]);\nVerify(Dimensions([[[[_a],[_b]]],[[[_c],[_d]]]]),[2,1,2,1]);\nVerify(Dimensions([[[[_a,_b]]],[[[_c,_d]]]]),[2,1,1,2]);\nVerify(Dimensions([[[[_a,_b]],[[_c,_d]]]]),[1,2,1,2]);\nVerify(Dimensions([[[[[[_a,_b],[_c]]]]],[[[_d],[_e,_f,_g]]]]), [2,1]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/linalg/Dimensions.mpw";
        userFunctionsTestsMap.put("Dimensions",testString);

        testString = new String[3];
        testString[0] = "59";
        testString[1] = "\nVerify(MapSingle(\"!\",[1,2,3,4]),[1,2,6,24]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/lists/MapSingle.mpw";
        userFunctionsTestsMap.put("MapSingle",testString);

        testString = new String[3];
        testString[0] = "45";
        testString[1] = "\n/* Reverse and FlatCopy (and some friends) would segfault in the past if passed a string as argument.\n * I am not opposed to overloading these functions to also work on strings per se, but for now just\n * check that they return an error in stead of segfaulting.\n */\nVerify(ExceptionCatch(Reverse(\"abc\"),\"Exception\"), \"Exception\");\n\n";
        testString[2] = "/org/mathpiper/scripts4/lists/Reverse.mpw";
        userFunctionsTestsMap.put("Reverse",testString);

        testString = new String[3];
        testString[0] = "1";
        testString[1] = "\nVerify(Intersection('[aa,b,c],'[b,c,d]),'[b,c]);\nVerify(Union('[aa,b,c],'[b,c,d]),'[aa,b,c,d]);\nVerify(Difference('[aa,b,c],'[b,c,d]),'[aa]);\n\nNextTest(\"VarList\");\nVerify(VarList(_x^2+_y^3) , [_x , _y]);\nVerify(List(1,2,3),[1 , 2 , 3]);\n\nTesting(\"BubbleSort\");\nVerify(BubbleSort([2,3,1],\"<?\"),[1,2,3]);\nVerify(BubbleSort([2,3,1],\">?\"),[3,2,1]);\n\nTesting(\"HeapSort\");\nVerify(HeapSort([2,3,1],\"<?\"),[1,2,3]);\nVerify(HeapSort([2,1,3],\">?\"),[3,2,1]);\nVerify(HeapSort([7,3,1,2,6],\"<?\"),[1,2,3,6,7]);\nVerify(HeapSort([6,7,1,3,2],\">?\"),[7,6,3,2,1]);\n\nVerify(Type(Sqrt(_x)),\"Sqrt\");\nVerify(ArgumentsCount(Sqrt(_x)),1);\nVerify(Contains?([_a,_b,_c],_b),True);\nVerify(Contains?([_a,_b,_c],_d),False);\n\nVerify(Append([_a,_b,_c],_d),[_a,_b,_c,_d]);\nVerify(RemoveDuplicates([_a,_b,_b,_c]),[_a,_b,_c]);\nVerify(Count([_a,_b,_b,_c],_b),2);\nVerify(VarList(_x*Sqrt(_x)),[_x]);\n\n\n{\n  Local(l);\n  l:=[1,2,3];\n  DestructiveDelete(l,1);\n  Verify(l,[2,3]);\n  DestructiveInsert(l,1,1);\n  Verify(l,[1,2,3]);\n  l[1] := 2;\n  Verify(l,[2,2,3]);\n  l[1] := 1;\n  DestructiveDelete(l,3);\n  Verify(l,[1,2]);\n  DestructiveInsert(l,3,3);\n  Verify(l,[1,2,3]);\n  DestructiveDelete(FlatCopy(l),1);\n  Verify(l,[1,2,3]);\n};\n\nVerify(BuildList(i!,i,1,4,1),[1,2,6,24]);\nVerify(PermutationsList([_a,_b,_c]),[[_a,_b,_c],[_a,_c,_b],[_c,_a,_b],[_b,_a,_c],[_b,_c,_a],[_c,_b,_a]]);\n\nTesting(\"ListOperations\");\nVerify(First('[a,b,c]),'a);\nVerify(Rest('[a,b,c]),'[b,c]);\nVerify(DestructiveReverse('[a,b,c]),'[c,b,a]);\nVerify(ListToFunction('[a,b,c]),'(a(b,c)));\nVerify(FunctionToList('(a(b,c))),'[a,b,c]);\n\nVerify(Delete([_a,_b,_c],2),[_a,_c]);\nVerify(Insert([_a,_c],2,_b),[_a,_b,_c]);\n\nTesting(\"Length\");\nVerify(Length([_a,_b]),2);\nVerify(Length([]),0);\n\nTesting(\"Nth\");\nVerify(Nth([_a,_b],1),_a);\nVerify([_a,_b,_c][2],_b);\n\nTesting(\"Concat\");\nVerify(Concat([_a,_b],[_c,_d]),[_a,_b,_c,_d]);\n//This is simply not true!!! Verify(Hold(Concat([a,b],[c,d])),Concat([a,b],[c,d]));\n\n/* todo:tk:commenting out for minimal version of the scripts.\nTesting(\"Binary searching\");\nVerify(BSearch(100,[[n],n^2-15]), -1);\nVerify(BSearch(100,[[n],n^2-16]), 4);\nVerify(BSearch(100,[[n],n^2-100002]), -1);\nVerify(BSearch(100,[[n],n^2-0]), -1);\nVerify(FindIsq(100,[[n],n^2-15]), 3);\nVerify(FindIsq(100,[[n],n^2-16]), 4);\nVerify(FindIsq(100,[[n],n^2-100002]), 100);\nVerify(FindIsq(100,[[n],n^2-0]), 1);\n*/\n\nVerify(Difference(FuncList(_a*_b/_c*_d), [*,/]), []);\n//Verify(Difference(FuncListArith(0*_x*Sin(_a/_b)*Ln(Cos(_y-_z)+Sin(_a))), [*,'Ln,'Sin]), []);\nVerify(Difference(VarListArith(_x+_a*_y^2-1), [_x,_a,_y^2]), []);\n\nVerify(Difference(FuncList(CUnparsable?({i:=0;While(i<?10){i++; a--; a:=a+i; [];};})), ['CUnparsable?,'Block,:=,'While,<?,++,--,ToAtom(\"+\"),'List]), []);\nVerify(FuncList([1,2,3]),['List]);\nVerify(FuncList([[],[]]),['List]);\nVerify(FuncList([]),['List]);\n\nTesting(\"AssocDelete\");\n{\n  Local(hash);\n  hash:=[[\"A\",1],[\"A\",2],[\"B\",3],[\"B\",4]];\n  AssocDelete(hash,[\"B\",3]);\n  Verify(hash, [[\"A\",1],[\"A\",2],[\"B\",4]]);\n  Verify(AssocDelete(hash,\"A\"),True);\n  Verify(hash, [[\"A\",2],[\"B\",4]]);\n  Verify(AssocDelete(hash,\"C\"),False);\n  Verify(hash, [[\"A\",2],[\"B\",4]]);\n  AssocDelete(hash,\"A\");\n  Verify(hash, [[\"B\",4]]);\n  AssocDelete(hash, [\"A\",2]);\n  AssocDelete(hash,\"A\");\n  Verify(hash, [[\"B\",4]]);\n  Verify(AssocDelete(hash,\"B\"),True);\n  Verify(hash, []);\n  Verify(AssocDelete(hash,\"A\"),False);\n  Verify(hash, []);\n};\nTesting(\"-- Arithmetic Operations\");\nVerify(1+[3,4],[4,5]);\nVerify([3,4]+1,[4,5]);\nVerify([1]+[3,4],Hold([1]+[3,4]));\nVerify([3,4]+[1],Hold([3,4]+[1]));\nVerify([1,2]+[3,4],[4,6]);\nVerify(1-[3,4],[-2,-3]);\nVerify([3,4]-1,[2,3]);\nVerify([1]-[3,4],Hold([1]-[3,4]));\nVerify([3,4]-[1],Hold([3,4]-[1]));\nVerify([1,2]-[3,4],[-2,-2]);\nVerify(2*[3,4],[6,8]);\nVerify([3,4]*2,[6,8]);\nVerify([2]*[3,4],Hold([2]*[3,4]));\nVerify([3,4]*[2],Hold([3,4]*[2]));\nVerify([1,2]*[3,4],[3,8]);\nVerify(2/[3,4],[2/3,1/2]);\nVerify([3,4]/2,[3/2,2]);\nVerify([2]/[3,4],Hold([2]/[3,4]));\nVerify([3,4]/[2],Hold([3,4]/[2]));\nVerify([1,2]/[3,4],[1/3,1/2]);\nVerify(2^[3,4],[8,16]);\nVerify([3,4]^2,[9,16]);\nVerify([2]^[3,4],Hold([2]^[3,4]));\nVerify([3,4]^[2],Hold([3,4]^[2]));\nVerify([1,2]^[3,4],[1,16]);\n\n// non-destructive Reverse operation\n{\n  Local(lst,revlst);\n  lst:=[_a,_b,_c,13,19];\n  revlst:=Reverse(lst);\n  Verify(revlst,[19,13,_c,_b,_a]);\n  Verify(lst,[_a,_b,_c,13,19]);\n};\nVerify(Assigned?(lst),False);\nVerify(Assigned?(revlst),False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/lists/lists_tests.mpw";
        userFunctionsTestsMap.put("lists",testString);

        testString = new String[3];
        testString[0] = "106";
        testString[1] = "\nRulebaseHoldArguments(\"sqrt\", [_x]);\nVerify(_a[2]*Sqrt(_x)/:[Sqrt(_x) <- sqrt(_x)],_a[2]*sqrt(_x));\nRetract(\"sqrt\", *);\n\n";
        testString[2] = "/org/mathpiper/scripts4/localrules/slash_colon_operator.mpw";
        userFunctionsTestsMap.put("/:",testString);

        testString = new String[3];
        testString[0] = "86";
        testString[1] = "\nVerify(IntLog(23^45, 67), 33);\nVerify(IntLog(1, 67), 0);\nVerify(IntLog(2, 67), 0);\nVerify(IntLog(0, 67), 0);\nVerify(IntLog(1, 1), Undefined);\nVerify(IntLog(2, 1), Undefined);\nVerify(IntLog(256^8, 4), 32);\nVerify(IntLog(256^8-1, 4), 31);\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/IntLog.mpw";
        userFunctionsTestsMap.put("IntLog",testString);

        testString = new String[3];
        testString[0] = "45";
        testString[1] = "\nVerify(NextPrime(65537), 65539);\nVerify(NextPrime(97192831),97192841);\nVerify(NextPrime(14987234876128361),14987234876128369);\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/NextPrime.mpw";
        userFunctionsTestsMap.put("NextPrime",testString);

        testString = new String[3];
        testString[0] = "189";
        testString[1] = "\n// you need to use ListToFunction for this one as -1 is actually -(1), eg. a unary function (minus)\n// applied to a positive integer (1). ListToFunction evaluates its arguments, resulting in a negative\n// integer (-1).\nVerify(NthRoot(-1,2),ListToFunction(['NthRoot,-1,2]));\n\nVerify(NthRoot(2,1),Hold(NthRoot(2,1)));\nVerify(NthRoot(2,2),[1,2]);\nVerify(NthRoot(12,2),[2,3]);\nVerify(NthRoot(12,3),[1,12]);\nVerify(NthRoot(27,3),[3,1]);\nVerify(NthRoot(17*13,2),[1,17*13]);\nVerify(NthRoot(17*17*13,2),[17,13]);\nVerify(NthRoot(17*17*17*13,2),[17,17*13]);\nVerify(NthRoot(17*17*17*13,3),[17,13]);\nVerify(NthRoot(17*17*17*17*13*13,2),[17*17*13,1]);\nVerify(NthRoot(17*17*17*17*13*13,3),[17,17*13*13]);\nVerify(NthRoot(17*17*17*17*13*13,4),[17,13*13]);\nVerify(NthRoot(17*17*17*17*13*13,5),[1,17*17*17*17*13*13]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/NthRoot.mpw";
        userFunctionsTestsMap.put("NthRoot",testString);

        testString = new String[3];
        testString[0] = "75";
        testString[1] = "\nVerify(Prime?(65539),True);\nVerify(Prime?(0),False);\nVerify(Prime?(-1),False);\nVerify(Prime?(1),False);\nVerify(Prime?(2),True);\nVerify(Prime?(3),True);\nVerify(Prime?(4),False);\nVerify(Prime?(5),True);\nVerify(Prime?(6),False);\nVerify(Prime?(7),True);\nVerify(Prime?(-60000000000),False);\nVerify(Prime?(6.1),False); \n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/Prime_.mpw";
        userFunctionsTestsMap.put("Prime?",testString);

        testString = new String[3];
        testString[0] = "71";
        testString[1] = "\nVerify(SmallPrime?(137),True);\nVerify(SmallPrime?(138),False);\nVerify(SmallPrime?(65537),True);\nVerify(SmallPrime?(65539),False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/SmallPrime_.mpw";
        userFunctionsTestsMap.put("SmallPrime?",testString);

        testString = new String[3];
        testString[0] = "49";
        testString[1] = "\nVerify(GaussianInteger?(3+4*I),True );\nVerify(GaussianInteger?(5),True);\nVerify(GaussianInteger?(1.1), False );\n\n";
        testString[2] = "/org/mathpiper/scripts4/numbers/gaussianintegers/GaussianInteger_.mpw";
        userFunctionsTestsMap.put("GaussianInteger?",testString);

        testString = new String[3];
        testString[0] = "354";
        testString[1] = "\n/* I stringified the results for now, as that is what the tests used to mean. The correct way to deal with this\n * would be to compare the resulting numbers to accepted precision.\n */\nVerify(PipeToString()Write(Plot2D(_a,-1->1,output: data,points: 4,depth: 0)), \"[[[-1,-1],[-0.5,-0.5],[0.0,0.0],[0.5,0.5],[1,1]]]\");\n\n";
        testString[2] = "/org/mathpiper/scripts4/plots/_2d/plot2d.mpw";
        userFunctionsTestsMap.put("Plot2D",testString);

        testString = new String[3];
        testString[0] = "436";
        testString[1] = "\n/* I stringified the results for now, as that is what the tests used to mean. The correct way to deal with this\n * would be to compare the resulting numbers to accepted precision.\n */\n{\n  Local(result);\n  result:=\"[[[-1,-1,-1],[-1,0,-1],[-1,1,-1],[0,-1,0],[0,0,0],[0,1,0],[1,-1,1],[1,0,1],[1,1,1]]]\";\n  Verify(PipeToString()Write(Plot3DS(_a,-1->1,-1->1,output: data,points: 2)), result);\n};\n\n";
        testString[2] = "/org/mathpiper/scripts4/plots/_3d/plot3ds.mpw";
        userFunctionsTestsMap.put("Plot3DS",testString);

        testString = new String[3];
        testString[0] = "44";
        testString[1] = "\nVerify(Constant?(Pi), True);\nVerify(Constant?(Exp(1)+Sqrt(3)), True);\nVerify(Constant?(_x), False);\nVerify(Constant?(Infinity), True);\nVerify(Constant?(-Infinity), True);\nVerify(Constant?(Undefined), True);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/Constant_.mpw";
        userFunctionsTestsMap.put("Constant?",testString);

        testString = new String[3];
        testString[0] = "29";
        testString[1] = "\n// Problem with FloatIsInt? and gmp\nVerify(FloatIsInt?(3.1415926535e9), False);\nVerify(FloatIsInt?(3.1415926535e10), True);\nVerify(FloatIsInt?(3.1415926535e20), True);\nVerify(FloatIsInt?(0.3e20), True);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/FloatIsInt_.mpw";
        userFunctionsTestsMap.put("FloatIsInt?",testString);

        testString = new String[3];
        testString[0] = "62";
        testString[1] = "\nVerify(HasExpression?(_a*_b+1,1),True);\nVerify(HasExpression?(_a+Sqrt(_b*_c),_c),True);\nVerify(HasExpression?(_a*_b+1,2),False);\nVerify(HasExpressionArithmetic?(_a*_b+1,ToAtom(\"+\")),False);\nVerify(HasExpressionArithmetic?(_a*_b+1,1),True);\nVerify(HasExpressionArithmetic?(_a+Sqrt(_b*_c),_c),False);\nVerify(HasExpressionArithmetic?(_a+Sqrt(_b*_c),Sqrt(_b*_c)),True);\n\nRulebaseHoldArguments(\"f\",[_a]);\nVerify(HasExpression?(_a*_b+f([_b,_c]),'f),False);\nVerify(HasExpressionArithmetic?(_a*_b+f([_b,_c]),_c),False);\nRetract(\"f\",*);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/HasExpression_.mpw";
        userFunctionsTestsMap.put("HasExpression?",testString);

        testString = new String[3];
        testString[0] = "48";
        testString[1] = "\nVerify(HasFunctionArithmetic?(_a*_b+1,ToAtom(\"+\")),True);\nVerify(HasFunctionArithmetic?(_a+Sqrt(_b*_c),'(*)),False);\nVerify(HasFunctionArithmetic?(_a+Sqrt(_b*_c),'Sqrt),True);\n\nRulebaseHoldArguments(\"f\",[_a]);\nVerify(HasFunctionArithmetic?(_a*_b+f([_b,_c]),'List),False);\nRetract(\"f\",*);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/HasFunctionArithmetic_.mpw";
        userFunctionsTestsMap.put("HasFunctionArithmetic?",testString);

        testString = new String[3];
        testString[0] = "53";
        testString[1] = "\nVerify(HasFunction?(_a*_b+1,*),True);\nVerify(HasFunction?(_a+Sqrt(_b*_c),*),True);\nVerify(HasFunction?(_a*_b+1,'List),False);\n\nRulebaseHoldArguments(\"f\",[_a]);\nVerify(HasFunction?('a*'b+f(['b,'c]),'List),True);\nRetract(\"f\",*);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/HasFunction_.mpw";
        userFunctionsTestsMap.put("HasFunction?",testString);

        testString = new String[3];
        testString[0] = "47";
        testString[1] = "\nVerify(Variable?(_a),True);\nVerify(Variable?(Sqrt(_a)),False);\nVerify(Variable?(2),False);\nVerify(Variable?(-2),False);\nVerify(Variable?(2.1),False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/Variable_.mpw";
        userFunctionsTestsMap.put("Variable?",testString);

        testString = new String[3];
        testString[0] = "258";
        testString[1] = "\nVerify(Scalar?(_a),True);\nVerify(Scalar?([_a]),False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/matrix.mpw";
        userFunctionsTestsMap.put("Scalar?",testString);

        testString = new String[3];
        testString[0] = "268";
        testString[1] = "\nVerify(Vector?(1),False);\nVerify(Vector?(_a),False);\nVerify(Vector?(Sqrt(_a)+2),False);\nVerify(Vector?([]),True);\nVerify(Vector?([[]]),False);\nVerify(Vector?([1,2,_a,4]),True);\nVerify(Vector?([1,[2,_a],4]),False);\nVerify(Vector?([[_a,_b,_c]]),False);\n\nTesting(\"-- Vector?(Number?)\");\nVerify(Vector?(Number?,1),False);\nVerify(Vector?(Number?,[]),True);\nVerify(Vector?(Number?,[_a,_b,_c]),False);\nVerify(Vector?(Number?,[_a,2,_c]),False);\nVerify(Vector?(Number?,[2,2.5,4]),True);\nVerify(Vector?(Number?,[Pi,2,3]),False);\nVerify(Vector?(Number?,[[1],[2]]),False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/matrix.mpw";
        userFunctionsTestsMap.put("Vector?",testString);

        testString = new String[3];
        testString[0] = "294";
        testString[1] = "\nVerify(Vector?(1),False);\nVerify(Vector?(_a),False);\nVerify(Vector?(Sqrt(_a)+2),False);\nVerify(Vector?([]),True);\nVerify(Vector?([[]]),False);\nVerify(Vector?([1,2,_a,4]),True);\nVerify(Vector?([1,[2,_a],4]),False);\nVerify(Vector?([[_a,_b,_c]]),False);\n\nTesting(\"-- Vector?(Number?)\");\nVerify(Vector?(Number?,1),False);\nVerify(Vector?(Number?,[]),True);\nVerify(Vector?(Number?,[_a,_b,_c]),False);\nVerify(Vector?(Number?,[_a,2,_c]),False);\nVerify(Vector?(Number?,[2,2.5,4]),True);\nVerify(Vector?(Number?,[Pi,2,3]),False);\nVerify(Vector?(Number?,[[1],[2]]),False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/matrix.mpw";
        userFunctionsTestsMap.put("Vector?",testString);

        testString = new String[3];
        testString[0] = "320";
        testString[1] = "Verify(Matrix?(1),False);\nVerify(Matrix?([]),False);\nVerify(Matrix?([_a,_b]),False);\nVerify(Matrix?([[]]),True);\nVerify(Matrix?([[_a]]),True);\nVerify(Matrix?([[[_a]]]),False);\nVerify(Matrix?([[],_a]),False);\nVerify(Matrix?([[_a],_b]),False);\nVerify(Matrix?([[],[]]),True);\nVerify(Matrix?([[[]],[]]),False);\nVerify(Matrix?([[],[[]]]),False);\nVerify(Matrix?([[_a,_b],[_c]]),False);\nVerify(Matrix?([[_a,_b],[_c,_d]]),True);\nVerify(Matrix?([[_a,_b],[_c,[_d]]]),False);\nVerify(Matrix?([[[]]]), False);\nVerify(Matrix?([[[_a]]]), False);\nVerify(Matrix?([[[[_a]]],[[[_b]]]]),False);\n\nTesting(\"---- Matrix?(Integer?)\");\nVerify(Matrix?(Integer?,[[_a,1]]),False);\nVerify(Matrix?(Integer?,[[1,2]]),True);\nVerify(Matrix?(Integer?,[[1,2/3]]),False);\nVerify(Matrix?(Integer?,[[1,2,3],[4,5,6]]),True);\nVerify(Matrix?(Integer?,[[1,[2],3],[4,5,6]]),False);\nVerify(Matrix?(Integer?,[[1,2,3],[4,5]]),False);\nVerify(Matrix?(Integer?,[[Sqrt(2),2,3],[4,5,6]]),False);\nVerify(Matrix?(Integer?,[[Sqrt(0),2,3],[4,5,6]]),True);\n\n";
        testString[2] = "/org/mathpiper/scripts4/predicates/matrix.mpw";
        userFunctionsTestsMap.put("Matrix?",testString);

        testString = new String[3];
        testString[0] = "355";
        testString[1] = "\nVerify(SquareMatrix?([[]]),False);\nVerify(SquareMatrix?([[_a]]),True);\nVerify(SquareMatrix?([[],[]]),False);\nVerify(SquareMatrix?([[_a,_b]]),False);\nVerify(SquareMatrix?([[_a,_b],[_c,_d]]),True);\nVerify(SquareMatrix?([[_a,_b],[_c,_d],[_e,_f]]),False);\nVerify(SquareMatrix?([[_a,_b,_c],[_d,_e,_f],[_g,_h,_i]]),True);\nVerify(SquareMatrix?([[_a,_b,_c],[_d,_e,_f]]),False);\nVerify(SquareMatrix?([[[_a,_b]],[[_c,_d]]]), False);\n\n";
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
        testString[1] = "\nUnassign(a,b,c);\n\nVerify(Subexpressions(_a*_b+_c), [_a,_b,_c,_a*_b,_a*_b+_c]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/proposed/logic/Subexpressions.mpw";
        userFunctionsTestsMap.put("Subexpressions",testString);

        testString = new String[3];
        testString[0] = "77";
        testString[1] = "\nUnassign(a,b);\n\nVerify(TruthTable(_a And? _b), [[_a,_b,_a And? _b],[True,True,True],[True,False,False],[False,True,False],[False,False,False]]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/proposed/logic/TruthTable.mpw";
        userFunctionsTestsMap.put("TruthTable",testString);

        testString = new String[3];
        testString[0] = "187";
        testString[1] = "\nVerify(RabinMiller(1037),False);\nVerify(RabinMiller(1038),False);\nVerify(RabinMiller(1039),True);\n\n";
        testString[2] = "/org/mathpiper/scripts4/rabinmiller/RabinMiller.mpw";
        userFunctionsTestsMap.put("RabinMiller",testString);

        testString = new String[3];
        testString[0] = "122";
        testString[1] = "\nVerify(Sort([4,7,23,53,-2,1], \">?\"), [53,23,7,4,1,-2]);\nVerify(SortIndexed([4,7,23,53,-2,1], \">?\"), [[53,23,7,4,1,-2],[4,3,2,1,6,5]]);\nVerify(Sort([11,-17,-1,-19,-18,14,4,7,9,-15,13,11,-2,2,7,-19,5,-3,6,4,-1,14,5,-15,11]),\n            [-19,-19,-18,-17,-15,-15,-3,-2,-1,-1,2,4,4,5,5,6,7,7,9,11,11,11,13,14,14]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/sorting/Sort.mpw";
        userFunctionsTestsMap.put("Sort",testString);

        testString = new String[3];
        testString[0] = "65";
        testString[1] = "\nVerify(Abs(Undefined),Undefined);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/Abs.mpw";
        userFunctionsTestsMap.put("Abs",testString);

        testString = new String[3];
        testString[0] = "76";
        testString[1] = "\n// Discovered that Floor didn't handle new exponent notation\nVerify(Floor(1001.1e-1),100);\nVerify(Floor(10.01e1),100);\nVerify(Floor(100.1),100);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/Floor.mpw";
        userFunctionsTestsMap.put("Floor",testString);

        testString = new String[3];
        testString[0] = "124";
        testString[1] = "\nVerify( Gcd( 324 + 1608*I, -11800 + 7900*I ),Complex(-52,16) );\n// I changed from Complex(-4,4) to Complex(4,4) as the GaussianGcd algorithm suddenly returned this instead.\n// However, as it turned out it was a bug in FloorN, introduced when\n// we moved to the new number classes (so the numbers did not get converted\n// to string and back any more). The number got prematurely truncated with\n// this test case (regression test added to regress.yts also).\n//TODO we can expand this with randomized tests\nVerify( Gcd( 7300 + 12*I, 2700 + 100*I), Complex(-4,4) );\n\n\n// Bug was found: gcd sometimes returned 0! Very bad, since the\n// value returned by gcd is usually used to divide out greatest\n// common divisors, and dividing by zero is not a good idea.\nVerify(Gcd(0,0),1);\nVerify(Gcd([0]),1);\n\n\n\nVerifyGaussianGcd(x,y):=\n{\n  Local(gcd);\n  gcd:=Gcd(x,y);\n//  Echo(x/gcd);\n//  Echo(y/gcd);\n  Verify(GaussianInteger?(x/gcd) And? GaussianInteger?(y/gcd),True);\n};\nVerifyGaussianGcd(324 + 1608*I, -11800 + 7900*I);\nVerifyGaussianGcd(7300 + 12*I, 2700 + 100*I);\nVerifyGaussianGcd(120-I*200,-336+50*I);\n\n/* Bug #3 */\nKnownFailure(Gcd(10,3.3) !=? 3.3 And? Gcd(10,3.3) !=? 1);\n/* I don't know what the answer should be, but buth 1 and 3.3 seem */\n/* certainly wrong. */\nVerify(Gcd(-10, 0), 10);\nVerify(Gcd(0, -10), 10);\n\n\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/Gcd.mpw";
        userFunctionsTestsMap.put("Gcd",testString);

        testString = new String[3];
        testString[0] = "64";
        testString[1] = "\nVerify( Lcm([7,11,13,17]), 7*11*13*17 );\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/Lcm.mpw";
        userFunctionsTestsMap.put("Lcm",testString);

        testString = new String[3];
        testString[0] = "123";
        testString[1] = "\n// Modulo generated a stack overflow on floats.\nVerify(Modulo(1.2,3.4),6/5);\n\n//TODO I need to understand why we need to put Eval here. Modulo(-1.2,3.4)-2.2 returns 0/5 where the 0 is not an integer according to the system. Round-off error?\nNumericEqual(NM(Eval(Modulo(-1.2,3.4))),2.2,BuiltinPrecisionGet());\n\nVerify(Modulo(-12/10,34/10),11/5);\n// just a test to see if Verify still gives correct error Verify(NM(Modulo(-12/10,34/10)),11/5);\n\n\n// Make sure Mod works threaded\nVerify(Modulo(2,Infinity),2);\nVerify(Modulo([2,1],[2,2]),[0,1]);\nVerify(Modulo([5,1],4),[1,1]);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/Modulo.mpw";
        userFunctionsTestsMap.put("Modulo",testString);

        testString = new String[3];
        testString[0] = "71";
        testString[1] = "\nVerify(Sqrt(Infinity),Infinity);\n\n// version 1.0.56: Due to MathBitCount returning negative values sometimes, functions depending on\n// proper functioning failed. MathSqrtFloat failed for instance on NM(1/2). It did give the right\n// result for 0.5.\nNumericEqual(NM(Sqrt(500000e-6),20),NM(Sqrt(0.0000005e6),20),20);\nNumericEqual(NM(Sqrt(0.5),20),NM(Sqrt(NM(1/2)),20),20);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/Sqrt.mpw";
        userFunctionsTestsMap.put("Sqrt",testString);

        testString = new String[3];
        testString[0] = "322";
        testString[1] = "\nVerify(.1<?2,True);\nVerify(0.1<?2,True);\nVerify(.3<?2,True);\nVerify(2<?.1,False);\nVerify(2<?0.1,False);\nVerify(2<?.3,False);\nVerify(1e-5 <? 1, True);\nVerify(1e-5 <? 2e-5, True);\nVerify(1e-1 <? 2e-1, True);\nVerify(1e-15 <? 2e-15, True);\nVerify(1e-5 <? 1e-10, False);\nVerify(1e-5 <? 1e-2, True);\nVerify(-1e-5 <? 1e-5, True);\nVerify(-1e-5 <? 1e-6, True);\n\nBuiltinPrecisionSet(32);\nVerify(0.999999999999999999999999999992 <? 1, True);\nBuiltinPrecisionSet(10);\n\nVerify(_a<?0,_a<?0);\n\nVerify(Undefined<?1, False);\n\nVerify(500 <? 0e-1,False);\n\n//Bug reported by Adrian Vontobel: comparison operators should coerce\n//to a real value as much as possible before trying the comparison.\nVerify(0.2*Pi <? 0.7, True);\nVerify(0.2*Pi <? 0.6, False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/comparison_operators.mpw";
        userFunctionsTestsMap.put("<?",testString);

        testString = new String[3];
        testString[0] = "360";
        testString[1] = "\nVerify(.1>?2,False);\nVerify(0.1>?2,False);\nVerify(.3>?2,False);\nVerify(2>?.1,True);\nVerify(2>?0.1,True);\nVerify(2>?.3,True);\nVerify(1e-15 >? 0, True);\nVerify(1e-5 >? 0, True);\nVerify(1e-4 >? 0, True);\nVerify(1e-3 >? 0, True);\nVerify(1e-2 >? 0, True);\nVerify(1e-1 >? 0, True);\nVerify(1e5 >? 0, True);\n\nBuiltinPrecisionSet(32);\nVerify(1.0000000000000000000000000000111 >? 1, True);\nBuiltinPrecisionSet(10);\n\nVerify(_a>?0,_a>?0);\n\nVerify(Undefined>?Undefined, False);\nVerify(Undefined>?1, False);\n\n//Bug reported by Adrian Vontobel: comparison operators should coerce\n//to a real value as much as possible before trying the comparison.\nVerify(0.2*Pi >? 0.5, True);\nVerify(0.2*Pi >? 0.7, False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/comparison_operators.mpw";
        userFunctionsTestsMap.put(">?",testString);

        testString = new String[3];
        testString[0] = "396";
        testString[1] = "\nVerify(1e-5 =? 2e-5, False);\nVerify(1e-5 =? 1e-6, False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/comparison_operators.mpw";
        userFunctionsTestsMap.put("=?",testString);

        testString = new String[3];
        testString[0] = "407";
        testString[1] = "\nVerify((1==1) !=? True, True);\nVerify((_a==_a) !=? True, True);\nVerify((1==2) !=? False, True);\nVerify((_a==2) !=? False, True);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/comparison_operators.mpw";
        userFunctionsTestsMap.put("!=?",testString);

        testString = new String[3];
        testString[0] = "421";
        testString[1] = "\nVerify( 2/3 >=? 1/3, True);\nVerify(1 >=? 1.0, True);\nVerify(-1 >=? -1.0, True);\nVerify(0 >=? 0.0, True);\nVerify(0.0 >=? 0, True);\nVerify(Undefined >=? -4, False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/comparison_operators.mpw";
        userFunctionsTestsMap.put(">=?",testString);

        testString = new String[3];
        testString[0] = "436";
        testString[1] = "\nVerify(1 <=? 1.0, True);\nVerify(-1 <=? -1.0, True);\nVerify(0 <=? 0.0, True);\nVerify(0.0 <=? 0, True);\nVerify(Undefined <=? -4, False);\n\n";
        testString[2] = "/org/mathpiper/scripts4/stubs/comparison_operators.mpw";
        userFunctionsTestsMap.put("<=?",testString);

        testString = new String[3];
        testString[0] = "52";
        testString[1] = "\nVerify(Add([1,2,3,4]), 10);\nVerify(Add([1]), 1);\nVerify(Add([]), 0);\nVerify(Add(1,2,3,4), 10);\nVerify(Add(1), 1);\n\n{\n  Local(list);\n  list:=[1,2,3,4,5];\n  Verify(Add(list)/Length(list), 3);\n  list:=[0];\n  Verify(Add(list)/Length(list), 0);\n  list:=[];\n  Verify(Add(list)/Length(list), Undefined);\n};\n\n";
        testString[2] = "/org/mathpiper/scripts4/sums/Add.mpw";
        userFunctionsTestsMap.put("Add",testString);

        testString = new String[3];
        testString[0] = "81";
        testString[1] = "\nVerify(Minimum(0,1),0);\nVerify(Minimum([]), Undefined);\nVerify(Minimum([_x]), _x);\nVerify(Minimum(_x), _x);\nVerify(Minimum(Exp(_x)), Exp(_x));\nVerify(Minimum([1,2,3]), 1);\n// since Minimum(multiple args) is disabled, comment this out\nVerify(Minimum(1,2,3), 1);\nVerify(Minimum(1,2,0), 0);\nVerify(Minimum(5,2,3,4), 2);\nVerify(Minimum(5,2,0,4), 0);\n\n";
        testString[2] = "/org/mathpiper/scripts4/sums/Minimum.mpw";
        userFunctionsTestsMap.put("Minimum",testString);

        testString = new String[3];
        testString[0] = "79";
        testString[1] = "\n// Product didn't check for correct input\nVerify(Product(10), Product(10));\nVerify(Product(-1), Product(-1));\nVerify(Product(Infinity), Product(Infinity));\nVerify(Product(1 .. 10),3628800);\n\nVerify(Product(i,1,3,i),6);\n\n";
        testString[2] = "/org/mathpiper/scripts4/sums/Product.mpw";
        userFunctionsTestsMap.put("Product",testString);

        testString = new String[3];
        testString[0] = "41";
        testString[1] = "\nVerify( Subfactorial(0), 1 );\nVerify( Subfactorial(21), 18795307255050944540 );\n\n";
        testString[2] = "/org/mathpiper/scripts4/sums/Subfactorial.mpw";
        userFunctionsTestsMap.put("Subfactorial",testString);

        testString = new String[3];
        testString[0] = "93";
        testString[1] = "\nVerify(261! - 261*260!, 0);\nVerify(300! / 250!, 251***300);\n\n// Verify that postfix operators can be applied one after the other\n// without brackets\nVerify((3!) !, 720);\nVerify(3! !, 720);\n\n";
        testString[2] = "/org/mathpiper/scripts4/sums/exclamationpoint_operator.mpw";
        userFunctionsTestsMap.put("Factorial",testString);

        testString = new String[3];
        testString[0] = "72";
        testString[1] = "\nLogicTest([_A],_A And? _A,_A);\nLogicTest([_A],_A And? True, _A);\nLogicTest([_A],_A And? False,False);\nLogicTest([_A],_A Or? True, True);\nLogicTest([_A],_A Or? False,_A);\nLogicTest([_A],_A Or? Not? _A,True);\nLogicTest([_A],_A And? Not? _A,False);\nLogicTest([_A,_B],(_A And? _B) Or? (_A And? _B), _A And? _B);\nLogicTest([_A,_B],_A Or? (_A And? _B), _A And?(_A Or? _B));\nLogicTest([_A,_B],(_A And? _B) And? _A,(_A And? _B) And? _A);\nLogicTest([_A,_B],Not? (_A And? _B) And? _A,(Not? _A Or? Not? _B) And? _A);\nLogicTest([_A,_B],(_A Or? _B) And? Not? _A,_B And? Not? _A);\nLogicTest([_A,_B,_C],(_A Or? _B) And? (Not? _A Or? _C), (_A Or? _B) And? (_C Or? Not? _A));\nLogicTest([_A,_B,_C],(_B Or? _A) And? (Not? _A Or? _C), (_A Or? _B) And? (_C Or? Not? _A));\nLogicTest([_A,_B,_C], _A And? (_A Or? _B Or? _C), _A);\nLogicTest([_A,_B,_C], _A And? (Not? _A Or? _B Or? _C),_A And? (_B Or? _C));\n\n";
        testString[2] = "/org/mathpiper/scripts4/testers/LogicTest.mpw";
        userFunctionsTestsMap.put("LogicTest",testString);

        testString = new String[3];
        testString[0] = "444";
        testString[1] = "\n/* it worketh no more...\nTesting(\"Realistic example\");\nf:=Exp(I*lambda*eta)*w(T*(k+k1+lambda));\ng:=Simplify(Substitute(lambda,0) f+(k+k1)*(Differentiate(lambda)f)+k*k1*Differentiate(lambda)Differentiate(lambda)f );\nVerify(UnparseLatex(g), ...);\n*/\n\n/*\nVerify(\nUnparseLatex(Hold(Cos(A-B)*Sqrt(C+D)-(a+b)*c^d+2*I+Complex(a+b,a-b)/Complex(0,1)))\n,\"$\\\\cos ( A - B)  \\\\times \\\\sqrt{C + D} - ( a + b)  \\\\times c ^{d} + 2 \\\\times \\\\imath  + \\\\frac{a + b + \\\\imath  \\\\times ( a - b) }{\\\\imath } $\"\n);\n*/\n\nVerify(\nUnparseLatex(Hold(Exp(A*B)/C/D/(E+F)*G-(-(a+b)-(c-d))-b^(c^d) -(a^b)^c))\n,\"$\\\\frac{\\\\frac{\\\\frac{\\\\exp ( A \\\\times B) }{C} }{D} }{E + F}  \\\\times G - (  - ( a + b)  - ( c - d) )  - b ^{c ^{d}} - ( a ^{b})  ^{c}$\"\n);\n\n/*\nRulebaseHoldArguments(\"f\",[_x,_y,_z]);\nRulebaseHoldArguments(\"g\",[_x,_y]);\nVerify(\nUnparseLatex(ObjectToMeta(Hold(Cos(A-B)*Sin(a)*f(b,c,d*(e+1))*Sqrt(C+D)-(g(a+b)^(c+d))^(c+d))))\n,\"$\\\\cos ( A - B)  \\\\times \\\\sin a \\\\times f( b, c, d \\\\times ( e + 1) )  \\\\times \\\\sqrt{C + D} - ( g( a + b)  ^{c + d})  ^{c + d}$\"\n);\nRetract(\"f\",*);\nRetract(\"g\",*);\n*/\n\n/* This test is commented out because it throws an exception when orthopoly.mpw is removed from the build process.\n// testing latest features: \\\\times, %, (a/b)^n, BinomialCoefficient(), BesselI, OrthoH\nVerify(\nUnparseLatex(3*2^n+Hold(x*10!) + (x/y)^2 + BinomialCoefficient(x,y) + BesselI(n,x) + Maximum(a,b) + OrthoH(n,x))\n, \"$3\\\\times 2 ^{n} + x\\\\times 10! + ( \\\\frac{x}{y} )  ^{2} + {x \\\\choose y} + I _{n}( x)  + \\\\max ( a, b)  + H _{n}( x) $\"\n);\n*/\n\n/* this fails because of a bug that Differentiate(x) f(y) does not go to 0 */ /*\nVerify(\nUnparseLatex(3*Differentiate(x)f(x,y,z)*Cos(Omega)*Modulo(Sin(a)*4,5/a^b))\n,\"$3 ( \\\\frac{\\\\partial}{\\\\partial x}f( x, y, z) )  ( \\\\cos \\\\Omega )  ( 4 ( \\\\sin a) ) \\\\bmod \\\\frac{5}{a ^{b}} $\"\n);\n*/\n\n\nVerify(\nUnparseLatex(Hold(Not? (c <? 0) And? (a+b)*c>=? -d^e And? (c <=? 0 Or? b+1 >? 0) Or? a !=? 0 And? Not? (p =? q)))\n,\"$ \\\\neg c < 0\\\\wedge ( a + b)  \\\\times c\\\\geq  - d ^{e}\\\\wedge ( c\\\\leq 0\\\\vee b + 1 > 0) \\\\vee a\\\\neq 0\\\\wedge  \\\\neg p = q$\"\n);\n\n\n/* todo:tk:commenting out for the minimal version of the scripts.\nRulebaseHoldArguments(\"f\",[_x]);\nVerify(\nUnparseLatex(Hold(Differentiate(x)f(x)))\n,\"$\\\\frac{d}{d x}f( x) $\");\nRetract(\"f\",*);\n\n\n/*\nRulebaseHoldArguments(\"f\",[_x,_y,_z]);\nVerify(\nUnparseLatex((Differentiate(x)f(x,y,z))*Cos(Omega)*Modulo(Sin(a)*4,5/a^b))\n,\"$( \\\\frac{\\\\partial}{\\\\partial x}f( x, y, z) )  \\\\times \\\\cos \\\\Omega  \\\\times ( 4 \\\\times \\\\sin a) \\\\bmod \\\\frac{5}{a ^{b}} $\"\n);\nRetract(\"f\",*);\n*/\n\n\n/*\nRulebaseHoldArguments(\"g\",[_x]);\nRulebaseHoldArguments(\"theta\",[_x]);\nVerify(\nUnparseLatex(Pi+Exp(1)-Theta-Integrate(x,x1,3/g(Pi))2*theta(x)*Exp(1/x))\n,\"$\\\\pi  + \\\\exp ( 1)  - \\\\Theta  - \\\\int _{x_{1}} ^{\\\\frac{3}{g( \\\\pi ) }  } 2 \\\\times \\\\theta ( x)  \\\\times \\\\exp ( \\\\frac{1}{x} )  dx$\"\n);\nRetract(\"g\",*);\nRetract(\"theta\",*);\n*/\n\n\nVerify(\nUnparseLatex(ObjectToMeta('([a[3]*b[5]-c[1][2],[a,b,c,d]])))\n,\"$( a _{3} \\\\times b _{5} - c _{( 1, 2) }, ( a, b, c, d) ) $\"\n);\n\n\n//Note: this is the only code in the test suite that currently creates new rulebases.\nRulebaseHoldArguments(\"aa\",[_x,_y,_z]);\nBodied(\"aa\", 200);\nRulebaseHoldArguments(\"bar\", [_x,_y]);\nInfix(\"bar\", 100);\nVerify(\nUnparseLatex(ObjectToMeta('(aa(x,y) z + 1 bar y!)))\n,\"$aa( x, y) z + 1\\\\mathrm{ bar }y!$\"\n);\nRetract(\"aa\",*);\nRetract(\"bar\",*);\n\nVerify(\nUnparseLatex('(x^(1/3)+x^(1/2)))\n, \"$\\\\sqrt[3]{x} + \\\\sqrt{x}$\"\n);\n\n/*\nVerify(\nUnparseLatex()\n,\"\"\n);\n*/\n\n/* Bug report from Michael Borcherds. The brackets were missing. */\nVerify(UnparseLatex(Hold(2*x*(-2))), \"$2 \\\\times x \\\\times (  - 2) $\");\n\n\n";
        testString[2] = "/org/mathpiper/scripts4/unparsers/unparselatex.mpw";
        userFunctionsTestsMap.put("UnparseLatex",testString);
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

    public String[] getdocumentationExamplesTestsScript(String testName)
    {
        return (String[]) documentationExamplesTestsMap.get(testName);
    }

    public Map getdocumentationExamplesTestsMap()
    {
        return documentationExamplesTestsMap;
    }
}
