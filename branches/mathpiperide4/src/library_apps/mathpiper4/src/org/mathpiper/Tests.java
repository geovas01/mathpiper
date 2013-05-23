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
        testString[0] = "151";
        testString[1] = "\nVerify(False And? False,False);\nVerify(True And? False,False);\nVerify(False And? True,False);\nVerify(True And? True,True);\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\And_.java";
        builtInFunctionsTestsMap.put("And?",testString);

        testString = new String[3];
        testString[0] = "83";
        testString[1] = "\nVerify(Atom?([a,b,c]),False);\nVerify(Atom?(a),True);\nVerify(Atom?(123),True);\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\Atom_.java";
        builtInFunctionsTestsMap.put("Atom?",testString);

        testString = new String[3];
        testString[0] = "112";
        testString[1] = "\nVerify(Concat([a,b],[c,d]), [a,b,c,d]);\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\Concatenate.java";
        builtInFunctionsTestsMap.put("Concat",testString);

        testString = new String[3];
        testString[0] = "100";
        testString[1] = "\nVerify(ConcatStrings(\"a\",\"b\",\"c\"),\"abc\");\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\ConcatenateStrings.java";
        builtInFunctionsTestsMap.put("ConcatStrings",testString);

        testString = new String[3];
        testString[0] = "77";
        testString[1] = "\nVerify(Equal?(a,b),False);\nVerify(Equal?(a,a),True);\nVerify(Equal?([a,b],[a]),False);\nVerify(Equal?([a,b],[a,b]),True);\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\Equal_.java";
        builtInFunctionsTestsMap.put("Equal?",testString);

        testString = new String[3];
        testString[0] = "129";
        testString[1] = "\nVerify(True Equivales? True,True);\nVerify(True Equivales? False,False);\nVerify(False Equivales? True,False);\nVerify(False Equivales? False,True);\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\Equivales_.java";
        builtInFunctionsTestsMap.put("Equivales?",testString);

        testString = new String[3];
        testString[0] = "174";
        testString[1] = "\n  //Test ExceptionCatch and ExceptionGet.\n  Local(exception);\n  exception := False;\n  ExceptionCatch(Check(False, \"Unspecified\", \"some error\"), exception := ExceptionGet());\n  Verify(exception =? False, False);\n\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\ExceptionCatch.java";
        builtInFunctionsTestsMap.put("ExceptionCatch",testString);

        testString = new String[3];
        testString[0] = "94";
        testString[1] = "\n//Reverse and FlatCopy (and some friends) would segfault in the past if passed a string as argument.\n//I am not opposed to overloading these functions to also work on strings per se, but for now just\n//check that they return an error in stead of segfaulting.\n//\nVerify(ExceptionCatch(FlatCopy(\"abc\"),\"Exception\"), \"Exception\");\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\FlatCopy.java";
        builtInFunctionsTestsMap.put("FlatCopy",testString);

        testString = new String[3];
        testString[0] = "122";
        testString[1] = "\nVerify(FromBase(16,\"1e\"),30);\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\FromBase.java";
        builtInFunctionsTestsMap.put("FromBase",testString);

        testString = new String[3];
        testString[0] = "78";
        testString[1] = "\nRulebaseHoldArguments(\"a\", [b]);\nVerify(Function?(a(b)),True);\nRetract(\"a\", 1);\nVerify(Function?(a),False);\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\Function_.java";
        builtInFunctionsTestsMap.put("Function?",testString);

        testString = new String[3];
        testString[0] = "95";
        testString[1] = "\n// FunctionToList and ListToFunction coredumped when their arguments were invalid.\nVerify(FunctionToList(Cos(x)),[Cos,x]);\n\n{\n  Local(exception);\n\n  exception := False;\n  ExceptionCatch(FunctionToList(1.2), exception := ExceptionGet());\n  Verify(exception =? False, False);\n};\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\FunctionToList.java";
        builtInFunctionsTestsMap.put("FunctionToList",testString);

        testString = new String[3];
        testString[0] = "77";
        testString[1] = "\nVerify(GreaterThan?(2,3),False);\nVerify(GreaterThan?(3,2),True);\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\GreaterThan_.java";
        builtInFunctionsTestsMap.put("GreaterThan?",testString);

        testString = new String[3];
        testString[0] = "125";
        testString[1] = "\nVerify(True Implies? True,True);\nVerify(True Implies? False,False);\nVerify(False Implies? True,True);\nVerify(False Implies? False,True);\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\Implies_.java";
        builtInFunctionsTestsMap.put("Implies?",testString);

        testString = new String[3];
        testString[0] = "98";
        testString[1] = "\nVerify(Integer?(123),True);\nVerify(Integer?(123.123),False);\nVerify(Integer?(a),False);\nVerify(Integer?([a]),False);\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\Integer_.java";
        builtInFunctionsTestsMap.put("Integer?",testString);

        testString = new String[3];
        testString[0] = "78";
        testString[1] = "\nVerify(LessThan?(2,3),True);\nVerify(LessThan?(3,2),False);\n\nVerify(LessThan?(-1e-115, 0), True);\nVerify(LessThan?(-1e-15, 0), True);\nVerify(LessThan?(-1e-10, 0), True);\nVerify(LessThan?(-1e-5, 0), True);\nVerify(LessThan?(-1e-1, 0), True);\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\LessThan_.java";
        builtInFunctionsTestsMap.put("LessThan?",testString);

        testString = new String[3];
        testString[0] = "83";
        testString[1] = "\nVerify(List?([a,b,c]),True);\nVerify(List?(a),False);\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\List_.java";
        builtInFunctionsTestsMap.put("List?",testString);

        testString = new String[3];
        testString[0] = "100";
        testString[1] = "\n// FunctionToList and ListToFunction coredumped when their arguments were invalid.\nVerify(ListToFunction([Cos,x]),Cos(x));\n\n{\n  Local(exception);\n \n  exception := False;\n  ExceptionCatch(ListToFunction(1.2), exception := ExceptionGet());\n  Verify(exception =? False, False);\n};\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\ListToFunction.java";
        builtInFunctionsTestsMap.put("ListToFunction",testString);

        testString = new String[3];
        testString[0] = "147";
        testString[1] = "\n{\n  Verify(Assigned?([]),False);\n  Local(a);\n  Verify(Assigned?(a),False);\n  a:=1;\n  Verify(Assigned?(a),True);\n  Unassign(a);\n  Verify(Assigned?(a),False);\n};\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\Local.java";
        builtInFunctionsTestsMap.put("Local",testString);

        testString = new String[3];
        testString[0] = "98";
        testString[1] = "\nVerify(Not?(True),False);\nVerify(Not?(False),True);\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\Not_.java";
        builtInFunctionsTestsMap.put("Not?",testString);

        testString = new String[3];
        testString[0] = "111";
        testString[1] = "\nVerify(Number?(123),True);\nVerify(Number?(123.123),True);\nVerify(Number?(a),False);\nVerify(Number?([a]),False);\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\Number_.java";
        builtInFunctionsTestsMap.put("Number?",testString);

        testString = new String[3];
        testString[0] = "141";
        testString[1] = "\nVerify(False Or? False,False);\nVerify(True Or? False,True);\nVerify(False Or? True,True);\nVerify(True Or? True,True);\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\Or_.java";
        builtInFunctionsTestsMap.put("Or?",testString);

        testString = new String[3];
        testString[0] = "88";
        testString[1] = "\nVerify(PipeFromString(\"(+ a b)\") ParseLisp(),a+b);\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\ParseLisp.java";
        builtInFunctionsTestsMap.put("ParseLisp",testString);

        testString = new String[3];
        testString[0] = "89";
        testString[1] = "\nVerify(PipeFromString(\"a+b;\") ParseMathPiper(),a+b);\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\ParseMathPiper.java";
        builtInFunctionsTestsMap.put("ParseMathPiper",testString);

        testString = new String[3];
        testString[0] = "89";
        testString[1] = "\n Verify(ToAtom(\"a\"),a);\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\ToAtom.java";
        builtInFunctionsTestsMap.put("ToAtom",testString);

        testString = new String[3];
        testString[0] = "117";
        testString[1] = "\nVerify(ToBase(16,30),\"1e\");\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\ToBase.java";
        builtInFunctionsTestsMap.put("ToBase",testString);

        testString = new String[3];
        testString[0] = "115";
        testString[1] = "\n Verify(ToString(a),\"a\");\n Verify(ToString(x^2),\"x^2\");\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\ToString.java";
        builtInFunctionsTestsMap.put("ToString",testString);

        testString = new String[3];
        testString[0] = "208";
        testString[1] = "\n// Reported by Serge: xml tokenizer not general enough\nVerify(XmlExplodeTag(\"<p/>\"),   XmlTag(\"P\",[],\"OpenClose\"));\nVerify(XmlExplodeTag(\"<p / >\"), XmlTag(\"P\",[],\"OpenClose\"));\n\n";
        testString[2] = "\\org\\mathpiper\\builtin\\functions\\core\\XmlExplodeTag.java";
        builtInFunctionsTestsMap.put("XmlExplodeTag",testString);

        testString = new String[3];
        testString[0] = "1";
        testString[1] = "\n/* Numerical testers - all confirmed with various programs */\nTesting(\" *** TESTING NUMERICS *** \");\n\nBuiltinPrecisionSet(10);   //Echo(\"BIP set to \",10);\nNumericEqual(NM(Sqrt(2),6), 1.41421,6);\nNumericEqual(NM(NM(1+Pi,20)-Pi,20),1,20);     \n// Should \"NM\" have \"HoldArgument\" in some way, so inner \"NM\" is \n// evaluated with outer precision 20?\n\nTesting(\"Pi\");\n/* \n   Got the first digits of Pi from the following page:\n   http://www.cecm.sfu.ca/projects/ISC/dataB/isc/C/pi10000.txt\n   Just checking that MathPiper agrees.\n   First, however, we need to set BuiltinPrecision way higher than 10 !!\n*/\nBuiltinPrecisionSet(90);//Echo(\"BIP set to \",90);\n\n\nNumericEqual(\nRoundToPrecision(NM(Pi), 50)\n, 3.14159265358979323846264338327950288419716939937511\n, 50);\nNumericEqual(NM(Pi,70),3.141592653589793238462643383279502884197169399375105820974944592307816,70);\nBuiltinPrecisionSet(10);//Echo(\"BIP set to \",10);\n\nTesting(\"Trig, Hyperbolic, Log, and Exp functions\");\nNumericEqual( NM(Sec(2),10), -2.402997962, 9);\nNumericEqual( NM(Csc(2),9),  1.09975017,9);\nNumericEqual( NM(Cot(2),9), -0.457657554, 9);\nNumericEqual( NM(Sinh(2),10), 3.626860408,10); \n    // matter of discussion whether rounding should be to nearest\n\nNumericEqual( NM(ArcSin(2), 9), Complex(1.570796327,1.316957897),9);\nNumericEqual( NM(ArcCos(2),9), Complex(0,-1.316957897),9);\nNumericEqual( NM(ArcTan(2*I), 12), NM(Complex(1.5707963267950,0.54930614433405),12),11); \n    // calculating to precision+1 because RoundToPrecision rounds... cluttering the last digit with round-off\nNumericEqual( NM(ArcSinh(2), 10), 1.443635475,9);\nNumericEqual( NM(ArcCosh(2), 10), 1.316957897,9);\nNumericEqual( NM(ArcCosh(-2), 8), Complex(-1.3169579,3.14159265),8);\nNumericEqual( NM(ArcTanh(2), 9), Complex(0.549306144,1.570796327),9);\n\n/* more Numerical tests - all confirmed with Maple */\nBuiltinPrecisionSet(55);//Echo(\"BIP set to \",55);\n/*\n (hso,100311) BuiltinPrecisionSet MUST specify a value higher\n than any of the precisions to be used in tests below.\n Otherwise, false errors are reported.  I have changed\n the value from 50 to 55, to satisfy this requirement.\n*/\nNumericEqual(\nRoundToPrecision(NM(Sin(2.0)), 49)\n, 0.9092974268256816953960198659117448427022549714479\n,48);\n\nNumericEqual(\nRoundToPrecision(NM(Sin(2.0)), 50)\n, 0.90929742682568169539601986591174484270225497144789\n,49);\n\nNumericEqual(\nRoundToPrecision(NM(Sin(2.0)), 51)\n, 0.90929742682568169539601986591174484270225497144789\n,50);\n\nNumericEqual(\nRoundToPrecision(NM(Cos(20.0)), 49)\n, 0.4080820618133919860622678609276449570992995103163\n, 48);\n\nNumericEqual(\nRoundToPrecision(NM(Tan(20.0)), 49)\n, 2.2371609442247422652871732477303491783724839749188\n, 48);  \n\nNumericEqual(\nRoundToPrecision(NM(Exp(10.32),54), 54)\n, 30333.2575962246035600343483350109621778376486335450125\n,48);   \n\nNumericEqual(\nRoundToPrecision(NM(Ln(10.32/4.07)), 50)\n, 0.93044076059891305468974486564632598071134270468002\n, 50);   \n\nNumericEqual(\nRoundToPrecision(NM(1.3^10.32), 48)\n, 14.99323664825717956473936947123246987802978985306\n, 48);\n\nNumericEqual(\nRoundToPrecision(NM(Sqrt(5.3),51), 51)\n, 2.302172886644267644194841586420201850185830282633675\n,51); \n\nNumericEqual(\nRoundToPrecision(NM(Sqrt(25.3)), 50)\n, 5.0299105359837166789719353820984438468186649281130\n,50);\n\nNumericEqual(\nRoundToPrecision(NM(PowerN(13, -23)), 50)\n, 0.23949855470974885180294666343025235387321690490245e-25\n, 50);\n\n\n/* todo:tk:commenting out for the minimal version of the scripts.\nTesting(\"Newton\");\nNumericEqual(\nRoundToPrecision({Local(x);x:=Newton(x*Exp(x)-4,x,1,10^(-49)); NM(x*Exp(x));}, 49)\n, 4\n,49);\n\nVerify(Newton(x^2+1,x,1,0.1,-3,3), Fail);\nNumericEqual(Newton(x^2-1,x,1,0.1,-3,3), 1,BuiltinPrecisionGet());\n*/\n\n\nTesting(\"Trig functions compounded with their Inverses\");\nNumericEqual(\nRoundToPrecision(NM(ArcSin(0.32)), 49)\n, 0.3257294872946301593103199105324500784354180998122808003\n,49);\n\nNumericEqual(\nRoundToPrecision(NM(Sin(NM(ArcSin(0.1234567)))), 49)\n, 0.1234567\n,49);\n\n/* ArcSin(x) for x close to 1 */\n\nNumericEqual(\nRoundToPrecision(NM( (1-Sin(NM(ArcSin(1-10^(-25)))))*10^25), 25)\n, 1\n, 25);\n\nNumericEqual(\nNM(ArcSin(NM(Sin(1.234567),50)),50)\n, NM(1.234567,50)\n, 49);  // calculating to precision+1 because RoundToPrecision rounds... cluttering the last digit with round-off\n\nNumericEqual(\nRoundToPrecision(NM(ArcCos(0.32)), 49)\n, 1.2450668395002664599210017811073013636631665998753\n, 49);\n\nNumericEqual(\nRoundToPrecision(NM(ArcTan(0.32)), 49)\n, 0.3097029445424561999173808103924156700884366304804\n, 49);\n\nNumericEqual(\nRoundToPrecision(NM(Cos(NM(ArcCos(0.1234567)))), 49)\n, 0.1234567\n, 49);\n\nNumericEqual(\nRoundToPrecision(NM(ArcCos(NM(Cos(1.234567)))), 49)\n, 1.234567\n, 49);\n\nNumericEqual(\nRoundToPrecision(NM(Tan(NM(ArcTan(20)))), 46)        // large roundoff error on Tan() calculation due to subtraction from Pi/2 -- unavoidable loss of precision\n, 20\n, 46);\n//KnownFailure(\nNumericEqual(\nRoundToPrecision(NM(Tan(NM(ArcTan(500000)))), 38)\n, 500000\n//)\n, 38);\n\nBuiltinPrecisionSet(60); //Echo(\"BIP set to \",60); // obviously, 50 is not enough for the following\n//KnownFailure(\nNumericEqual(\nRoundToPrecision(NM((Pi/2-ArcTan(NM(Tan(NM(Pi/2)-10^(-24)))))*10^24 ), 25)\n, 1\n//)\n, 25);\n\n/// special functions\nBuiltinPrecisionSet(50); //Echo(\"BIP set to \",50); // needs a pretty high value for Gamma\n\n\n/* todo:tk:commenting out for the minimal version of the scripts.\nTesting(\"Gamma function\");\nTestMathPiper(\nGamma(10.5)\n, (654729075*Sqrt(Pi))/1024\n);\n\nTestMathPiper(\nGamma(9/2)\n, (105*Sqrt(Pi))/16\n);\n\nTestMathPiper(\nGamma(-10.5)\n, (-2048*Sqrt(Pi))/13749310575\n);\n\nTestMathPiper(\nGamma(-7/2)\n, (16*Sqrt(Pi))/105\n);\n\nNumericEqual(RoundToPrecision(NM( InternalGammaNum(10.5) ), 13), 1133278.3889487855673, 13);\n\nKnownFailure(\nNumericEqual(RoundToPrecision(NM( InternalGammaNum(-11.5) ), 20), 0.00000002295758104824, 20)\n);\n// our gamma is wrong\n\nKnownFailure(\nNumericEqual(RoundToPrecision(NM( InternalGammaNum(-12.5) ), 20), -0.00000000183660648386, 20)\n);\n//our gamma is wrong\n\n// Check for one example that NM(Gamma(x)) returns the same as InternalGammaNum\nNumericEqual(RoundToPrecision(NM( Gamma(10.5) ), 13), 1133278.3889487855673, 13);\n\nTesting(\"Zeta function\");\nKnownFailure(\nNumericEqual(    \nRoundToPrecision(NM( Zeta(-11.5) ), 18), 0.020396978715942792,18)\n);\n\nTestMathPiper(\nZeta(40)\n, (261082718496449122051*Pi^40)/20080431172289638826798401128390556640625\n);\n\nTestMathPiper(\nZeta(-11)\n, 691/32760\n);\n\nTestMathPiper(\nZeta(-12)\n, 0\n);\n\nNumericEqual(\nRoundToPrecision(NM(Zeta(40)), 19)\n, 1.0000000000009094948\n,19);\n\nNumericEqual(\nRoundToPrecision(NM(Zeta(1.5)), 19)\n, 2.6123753486854883433\n,19);\n\n// test correctness of Zeta(3)\nNumericEqual(\nRoundToPrecision(InternalZetaNum(3)-NM(Zeta(3)), 20)\n, 0\n,20);\n\nTesting(\"Bernoulli number\");\nTestMathPiper(\nBernoulli(40)\n, -261082718496449122051/13530\n);\n\nTesting(\"Continued Fraction\");\nVerify(\nContFracList(355/113)\n, [3,7,16]\n);\n\nVerify(\nContFracList(-24, 4)\n, [-24]\n);\n\nVerify(\nContFracList(-355/113)\n, [-4,1,6,16]\n);\n\n\n/*\n//BuiltinPrecisionSet(7);//Echo(\"BIP set to \",7);\n\nVerify(\nNearRational(NM(Pi),3)\n, 201/64\n);\n\n/*\n  For the NearRational test, perhaps better would be a real test that\n  checks that the result is correct up to the required number of digits\n  accuracy.\n*/\nBuiltinPrecisionSet(10);//Echo(\"BIP set to \",10);\nVerify(\nNearRational(NM(Pi))\n, 355/113,\n);\n\n\n/* todo:tk:commenting out for the minimal version of the scripts.\n// Lambert's W function\nTesting(\"Lambert W function\");\nBuiltinPrecisionSet(20);\n\nNumericEqual(\nNM(RoundToPrecision(LambertW(-0.24),20)),-0.33576116478890275173,20);\n\nNumericEqual(\nNM(RoundToPrecision(LambertW(10),20)),1.7455280027406993831,20);\n\n// Bessel Functions\nTesting(\"Bessel functions\");\nBuiltinPrecisionSet(50);//Echo(\"BIP set to \",50);\nNumericEqual( NM(BesselJ(0,.5)), RoundToPrecision(.93846980724081290422840467359971262556892679709682,50),50 );\nNumericEqual( NM(BesselJ(0,.9)), RoundToPrecision(.80752379812254477730240904228745534863542363027564,50),50 );\nNumericEqual( NM(BesselJ(0,.99999)), RoundToPrecision(.76520208704756659155313775543958045290339472808482,50),50 );\nNumericEqual( NM(BesselJ(10,.75)), RoundToPrecision(.000000000014962171311759681469871248362168283485781647202136,50),50 );\nNumericEqual( NM(BesselJ(5,1)), RoundToPrecision(.00024975773021123443137506554098804519815836777698007,50),50 );\nNumericEqual( NM(BesselJ(4,2)), RoundToPrecision(.033995719807568434145759211288531044714832968346313,50),50 );\nNumericEqual( NM(BesselJ(10,3)), RoundToPrecision( .000012928351645715883777534530802580170743420832844164,50),50 );\n\nNumericEqual( NM(BesselJ(11,11)), RoundToPrecision( .20101400990926940339478738551009382430831534125484,50),50 );\nNumericEqual( NM(BesselJ(-11,11)), RoundToPrecision( -.20101400990926940339478738551009382430831534125484,50),50 );\nNumericEqual( RoundToPrecision(NM(BesselJ(1,10)),50), RoundToPrecision( .043472746168861436669748768025859288306272867118594, 50),50 );\nNumericEqual( NM(BesselJ(10,10)), RoundToPrecision( .20748610663335885769727872351875342803274461128682, 50 ),50 );\nNumericEqual( RoundToPrecision(NM(BesselJ(1,3.6)),50), RoundToPrecision( .095465547177876403845706744226060986019432754908851, 50 ),50) ;\n\nBuiltinPrecisionSet(20);//Echo(\"BIP set to \",20);\nVerify( RoundToPrecision(NM(Erf(Sqrt(0.8)),20),19),\nRoundToPrecision(.79409678926793169113034892342, 19)\n);\n\nVerify( RoundToPrecision(NM(Erf(50*I+20)/10^910,22),19),\nRoundToPrecision(1.09317119002909585408+I*0.00475463306931818955275, 19)\n);\n\n// testing GammaConstNum against Maple\nTesting(\"Gamma constant\");\nBuiltinPrecisionSet(41);//Echo(\"BIP set to \",41);\nNumericEqual(RoundToPrecision(Internalgamma(),40), 0.5772156649015328606065120900824024310422,BuiltinPrecisionGet());\nBuiltinPrecisionSet(20);//Echo(\"BIP set to \",20);\n\nVerify(gamma,ToAtom(\"gamma\"));\n\nNumericEqual(RoundToPrecision(Internalgamma()+0,19), 0.5772156649015328606,19);\n\nNumericEqual(RoundToPrecision(NM(1/2+gamma+Pi), 19), 4.2188083184913260991,19);\n\n// From GSL 1.0\n//NumericEqual( NM(PolyLog(2,-0.001),20), -0.00099975011104865108, 20 );\n// PolyLog I didn't write PolyLog, but it seems to not always calculate correctly up to the last digit.\nVerify( RoundToPrecision(NM(PolyLog(2,-0.001)+0.00099975011104865108,20),20),0);\n\n*/\n\n// Round-off errors\nNM({\n  Local(a,b);\n  a:= 77617;\n  b:= 33096;\n  // this expression gives a wrong answer on any hardware floating-point platform\n  NumericEqual( 333.75*b^6 + a^2*(11*a^2*b^2-b^6-121*b^4-2)+5.5*b^8 +a/(2*b), -0.827396,6);\n},40);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\a_initialization\\miscellaneous\\numerics_tests.mpw";
        userFunctionsTestsMap.put("numerics",testString);

        testString = new String[3];
        testString[0] = "38";
        testString[1] = "\nVerify(0&1, 0);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\a_initialization\\standard\\ampersand_operator.mpw";
        userFunctionsTestsMap.put("&",testString);

        testString = new String[3];
        testString[0] = "37";
        testString[1] = "\nVerify(0%1, 0);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\a_initialization\\standard\\percent_operator.mpw";
        userFunctionsTestsMap.put("%",testString);

        testString = new String[3];
        testString[0] = "38";
        testString[1] = "\nVerify(0|1, 1);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\a_initialization\\standard\\vertical_bar_operator.mpw";
        userFunctionsTestsMap.put("|",testString);

        testString = new String[3];
        testString[0] = "1";
        testString[1] = "\nNextTest(\"Test arithmetic\");\n\nNextTest(\"Basic calculations\");\nVerify(3 + 2 , 5);\nVerify(3-7, -4);\nVerify(1 =? 2 , 0 =? -1);\nVerify(5 ^ 2 , 25);\n\nVerify(Zero?(0.000),True);\n\nVerify(2/5,Hold(2/5));\nVerify(Zero?(NM(2/5)-0.4),True);\nVerify(Rational?(2),True);\nVerify(Rational?(2/5),True);\nVerify(Rational?(-2/5),True);\nVerify(Rational?(2.0/5),False);\nVerify(Rational?(Pi/2),False);\nVerify(Numerator(2/5),2);\nVerify(Denominator(2/5),5);\n\nVerifyArithmetic(10,5,8);\nVerifyArithmetic(10000000000,5,8);\nVerifyArithmetic(10,50,80);\nVerifyArithmetic(10000,50,88);\n\nVerify(4!,24);\nVerify(BinomialCoefficient(2,1),2);\n\nNextTest(\"Testing math stuff\");\nVerify(1*a,a);\nVerify(a*1,a);\nVerify(0*a,0);\nVerify(a*0,0);\nVerify(aa-aa,0);\n\nVerify(2+3,5);\nVerify(2*3,6);\n\nVerify(2+3*4,14);\nVerify(3*4+2,14);\nVerify(3*(4+2),18);\nVerify((4+2)*3,18);\n\nVerify(15/5,3);\n\nVerify(-2+3,1);\nVerify(-2.01+3.01,1.);\n\nVerify(0+a,a);\nVerify(a+0,a);\nVerify(aa-aa,0);\n\nTesting(\"IntegerOperations\");\nVerify(1<<10,1024);\nVerify(1024>>10,1);\nVerify(Modulo(10,3),1);\nVerify(Quotient(10,3),3);\nVerify(GcdN(55,10),5);\n\nVerify(Modulo(2,Infinity),2);\nVerify(Modulo([0,1,2,3,4,5,6],2),[0,1,0,1,0,1,0]);\nVerify(Modulo([0,1,2,3,4,5,6],[2,2,2,2,2,2,2]),[0,1,0,1,0,1,0]);\n\nTesting(\"PowerN\");\n// was broken in the gmp version\nVerify(PowerN(19, 0), 1);\nVerify(PowerN(1, -1), 1);\nVerify(PowerN(1, -2), 1);\nVerify(Zero?(PowerN(10, -2)- 0.01),True);\nVerify(PowerN(2, 3), 8);\nNumericEqual(PowerN(2, -3), 0.125,BuiltinPrecisionGet());\n\nTesting(\"Rounding\");\nVerify(Floor(1.2),1);\nVerify(Floor(-1.2),-2);\nVerify(Ceil(1.2),2);\nVerify(Ceil(-1.2),-1);\nVerify(Round(1.49),1);\nVerify(Round(1.51),2);\nVerify(Round(-1.49),-1);\nVerify(Round(-1.51),-2);\n\nTesting(\"Bases\");\nVerify(ToBase(16,255),\"ff\");\nVerify(FromBase(2,\"100\"),4);\n\n// conversion between decimal and binary digits\nVerify(BitsToDigits(2000, 10), 602);\nVerify(DigitsToBits(602, 10), 2000);\n\nLocalSymbols(f,ft)\n{\n  f(x,y):=(Quotient(x,y)*y+Rem(x,y)-x);\n  ft(x,y):=\n  {\n    Verify(f(x,y),0);\n    Verify(f(-x,y),0);\n    Verify(f(x,-y),0);\n    Verify(f(-x,-y),0);\n  };\n  ft(10,4);\n  ft(2.5,1.2);\n};\n\n/* todo:tk:commenting out for the minimal version of the scripts.\nTesting(\"Factorization\");\nVerify(\nEval(Factors(447738843))\n, [[3,1],[17,1],[2729,1],[3217,1]]\n);\n*/\n\n//Exponential notation is now supported in the native arithmetic library too...\nVerify(2e3+1,2001.);\nVerify(2.0e3+1,2001.);\nVerify(2.00e3+1,2001.);\nVerify(2.000e3+1,2001.);\nVerify(2.0000e3+1,2001.);\n\nVerify(1+2e3,2001.);\nVerify(1+2.0e3,2001.);\nVerify(1+2.00e3,2001.);\nVerify(1+2.000e3,2001.);\nVerify(1+2.0000e3,2001.);\n\nNumericEqual(NM(Sqrt(1e4))-100,0,BuiltinPrecisionGet());\nNumericEqual(NM(Sqrt(1.0e4))-100,0,BuiltinPrecisionGet());\n\nVerify(2.0000e3-1,1999.);\n{\n  Local(p);\n  p:=BuiltinPrecisionGet();\n  BuiltinPrecisionSet(12);//TODO this will fail if you drop precision to below 12, for some reason.\n  NumericEqual(RoundToPrecision(10e3*1.2e-3,BuiltinPrecisionGet()),12.,BuiltinPrecisionGet());\n  BuiltinPrecisionSet(p);\n};\nVerify((10e3*1.2e-4)-1.2,0);\n\nVerify(Zero?(NM(Sin(0.1e1)-Sin(1),30)),True);\n{\n  /* In Dutch they have a saying \"dit verdient geen schoonheidsprijs\" ;-) We need to sort this out.\n   * But a passable result, for now.\n   */\n  Local(diff);\n  diff := NM(Sin(10e-1)-Sin(1),30);\n//BuiltinPrecisionSet(20);\n//Echo(\"diff = \",diff);\n//Echo(\"diff > -0.00001 = \",diff > -0.00001);\n//Echo(\"diff < 0.00001 = \",diff < 0.00001);\n  Verify(diff >? -0.00001 And? diff <? 0.00001,True);\n};\n\n\n/* Jonathan reported a problem with Simplify(-Sqrt(8)/2), which returned some\n * complex expression containing greatest common divisors of square roots.\n * This was fixed by adding some rules dealing with taking the gcd of two objects\n * where at least one is a square root.\n */\nVerify(-Sqrt(8)/2,-Sqrt(2));\nVerify(Sqrt(8)/2,Sqrt(2));\nVerify(Gcd(Sqrt(2),Sqrt(2)),Sqrt(2));\nVerify(Gcd(-Sqrt(2),-Sqrt(2)),Sqrt(2));\nVerify(Gcd(Sqrt(2),-Sqrt(2)),Sqrt(2));\nVerify(Gcd(-Sqrt(2),Sqrt(2)),Sqrt(2));\n\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\a_initialization\\stdarith\\arithmetic_test.mpw";
        userFunctionsTestsMap.put("arithmetic",testString);

        testString = new String[3];
        testString[0] = "159";
        testString[1] = "\nVerify((-2)*Infinity,-Infinity);\n\nVerify(Infinity*0,Undefined);\n\n// The following is a classical error: 0*x=0 is only true if\n// x is a number! In this case, it is checked for that the\n// multiplication of 0 with a vector returns a zero vector.\n// This would automatically be caught with type checking.\n// More tests of this ilk are possible: 0*matrix, etcetera.\nVerify(0*[a,b,c],[0,0,0]);\n\nVerify(Undefined*0,Undefined);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\a_initialization\\stdarith\\asterisk_operator.mpw";
        userFunctionsTestsMap.put("*",testString);

        testString = new String[3];
        testString[0] = "120";
        testString[1] = "\nVerify(1^Infinity,Undefined);\n\n// Matrix operations failed: a^2 performed the squaring on each element.\nVerify([[1,2],[3,4]]^2,[[7,10],[15,22]]);\n\n// Check that raising powers still works on lists/vectors (dotproduct?) correctly.\nVerify([2,3]^2,[4,9]);\n\nVerify(0.0000^(24),0);\n\n// expansion of negative powers of fractions\nVerify( (-1/2)^(-10), 1024);\n\nVerify( I^(Infinity), Undefined );\nVerify( I^(-Infinity), Undefined );\n\nVerify( 2^(-10), 1/1024 );\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\a_initialization\\stdarith\\caret_operator.mpw";
        userFunctionsTestsMap.put("^",testString);

        testString = new String[3];
        testString[0] = "167";
        testString[1] = "\nVerify(Infinity/Infinity,Undefined);\nVerify(0.0/Sqrt(2),0);\nVerify(0.0000000000/Sqrt(2),0);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\a_initialization\\stdarith\\slash_operator.mpw";
        userFunctionsTestsMap.put("/",testString);

        testString = new String[3];
        testString[0] = "79";
        testString[1] = "{\nLocal(precision);\nprecision := BuiltinPrecisionGet();\nBuiltinPrecisionSet(10);\nVerify(MathExpTaylor0(0),1.0);\nVerify(MathExpTaylor0(.2), 1.221402759);\nVerify(MathExpTaylor0(1),  2.718281830);\nVerify(MathExpTaylor0(.234563),  1.264356123);\nBuiltinPrecisionSet(precision);\n};\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\base\\MathExpTaylor0.mpw";
        userFunctionsTestsMap.put("MathExpTaylor0",testString);

        testString = new String[3];
        testString[0] = "56";
        testString[1] = "\n/* Bug #15 */\nVerify(PowerN(0,0.55), 0);\n// LogN(-1) locks up in gmpnumbers.cpp, will be fixed in scripts\n//FIXME this test should be uncommented eventually\n// Verify(ExceptionCatch(PowerN(-1,-0.5), error), error);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\base\\PowerN.mpw";
        userFunctionsTestsMap.put("PowerN",testString);

        testString = new String[3];
        testString[0] = "60";
        testString[1] = "\nVerify(BinomialCoefficient(0,0),1 );\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\combinatorics\\Combinations.mpw";
        userFunctionsTestsMap.put("BinomialCoefficient",testString);

        testString = new String[3];
        testString[0] = "52";
        testString[1] = "\nTestMathPiper(Arg(Exp(2*I*Pi/3)),2*Pi/3);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\complex\\Arg.mpw";
        userFunctionsTestsMap.put("Arg",testString);

        testString = new String[3];
        testString[0] = "148";
        testString[1] = "\n/* todo:tk:commenting out for the minimal version of the scripts.\nVerify( Limit(z,2*I) (I*z^4+3*z^2-10*I), Complex(-12,6) );\nKnownFailure( (Limit(n,Infinity) (n^2*I^n)/(n^3+1)) =? 0 );\nVerify( Limit(n,Infinity) n*I^n, Undefined );\n*/\n\nVerify(1/I, -I);\nVerify(I^2, -1);\nVerify(2/(1+I), 1-I);\nVerify(I^3, -I);\nVerify(I^4, 1);\nVerify(I^5, I);\nVerify(1^I, 1);\nVerify(0^I, Undefined);\nVerify(I^(-I), Exp(Pi/2));\nVerify((1+I)^33, 65536+I*65536);\nVerify((1+I)^(-33), (1-I)/131072);\nVerify(Exp(I*Pi), -1);\nTestMathPiper((a+b*I)*(c+d*I), (a*c-b*d)+I*(a*d+b*c));\nVerify(Ln(-1), I*Pi);\nVerify(Ln(3+4*I), Ln(5)+I*ArcTan(4/3));\n\nVerify(Re(2*I-4), -4);\nVerify(Im(2*I-4), 2);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\complex\\Complex.mpw";
        userFunctionsTestsMap.put("Complex",testString);

        testString = new String[3];
        testString[0] = "44";
        testString[1] = "\n// the following broke evaluation (dr)\nVerify(Conjugate([a]),[a]);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\complex\\Conjugate.mpw";
        userFunctionsTestsMap.put("Conjugate",testString);

        testString = new String[3];
        testString[0] = "44";
        testString[1] = "\n/* Bug #7 */\nVerify(Im(3+I*Infinity), Infinity); /* resolved */\nVerify(Im(3+I*Undefined), Undefined);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\complex\\Im.mpw";
        userFunctionsTestsMap.put("Im",testString);

        testString = new String[3];
        testString[0] = "156";
        testString[1] = "\nFunction(\"count\",[from,to])\n{\n   Local(i);\n   Local(sum);\n   Assign(sum,0);\n   For(i:=from,i<?to,i:=i+1)\n   {\n     Assign(sum,sum+i);\n   };\n   sum;\n};\n\nTesting(\"Function definitions\");\nVerify(count(1,11),55);\n\nRetract(\"count\",2);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\deffunc\\Function.mpw";
        userFunctionsTestsMap.put("Function",testString);

        testString = new String[3];
        testString[0] = "123";
        testString[1] = "\n{\n  Local(a,b,c,d);\n  MacroRulebaseHoldArguments(foo,[a,b]);\n\n  // Simple check\n  foo(_c,_d) <-- [@c,@d];\n  Verify(foo(2,3),Hold([2,3]));\n\n  Macro(\"foo\",[a]) [@a,a];\n  a:=A;\n  Verify(foo(B),[B,A]);\n\n  /*\n  Removed from the test because the system now throws exceptions when\\\n  undefined functions are called.\n  Retract(foo,1);\n  Retract(foo,2);\n  Verify(foo(2,3),foo(2,3));\n  Verify(foo(B),foo(B));\n  */\n};\n\n{\n  Local(a,i,tot);\n  a:=100;\n  Retract(forloop,4);\n  Macro(forloop,[init,pred,inc,body])\n  {\n    @init;\n    While(@pred)\n    {\n      @body;\n      @inc;\n    };\n    True;\n  };\n  tot:=0;\n  forloop(i:=1,i<=?10,i++,tot:=tot+a*i);\n  Verify(i,11);\n  Verify(tot,5500);\n};\n\n{\n  Macro(\"bar\",[list,...]) Length(@list);\n  Verify(bar(a,b,list,bar,list),5);\n};\n\n{\n  Local(x,y,z);\n  RulebaseHoldArguments(\"@\",[x]);\n  y:=x;\n  Verify(`[@x,@y],[x,x]);\n  z:=u;\n  y:=[@z,@z];\n  Verify(`[@x,@y],[x,[@z,@z]]);\n  Verify(`[@x,`(@y)],[x,[@u,@u]]);\n  y:=Hold(`[@z,@z]);\n\n  Verify(`[@x,@y],[x,[u,u]]);\n  Verify(`[@x,`(@y)],[x,[u,u]]);\n  Retract(\"@\",1);\n};\n\n// check that a macro can reach a local from the calling environment.\n{\n  Macro(foo,[x]) a*(@x);\n  Function(bar,[x])\n  {\n    Local(a);\n    a:=2;\n    foo(x);\n  };\n  Verify(bar(3),6);\n};\n\n//check that with nested backquotes expansion only expands the top-level expression\n{\n  Local(a,b);\n  a:=2;\n  b:=3;\n  Verify(\n  `{\n     Local(c);\n     c:=@a+@b;\n     `((@c)*(@c));\n  },25);\n};\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\deffunc\\Macro.mpw";
        userFunctionsTestsMap.put("Macro",testString);

        testString = new String[3];
        testString[0] = "65";
        testString[1] = "\nVerify(Apply(\"+\",[2,3]),5);\n{\n  Local(x,y);\n  Verify(Apply([[x,y],x+y],[2,3]),5);\n  Verify(Apply(Lambda([x,y],x+y),[2,3]),5);\n  Verify(Lambda([x,y],x+y) @ [2,3],5);\n\n  /* Basically the next line is to check that [[x],Length(x)]\n   * behaves in an undesirable way (Length being evaluated\n   * prematurely), so that the next test can then check that\n   * Lambda solves the problem.\n   */\n  Verify(Apply([[x],Length(x)],[\"aaa\"]),Length);\n  Verify(Apply(Lambda([x],Length(x)),[\"aaa\"]),3);\n\n  Verify(x,x);\n  Verify(y,y);\n\n  Testing(\"ThreadingListables\");\n  x:=[bb,cc,dd];\n  Verify(Sin(aa*x),[Sin(aa*bb),Sin(aa*cc),Sin(aa*dd)]);\n};\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\functional\\Apply.mpw";
        userFunctionsTestsMap.put("Apply",testString);

        testString = new String[3];
        testString[0] = "113";
        testString[1] = "\nBuiltinPrecisionSet(10);\nRetract(\"f\",1);\nRetract(\"f1\",1);\nf(x) := NM(Abs(1/x-1));\nVerify(f(0), Infinity);\nNumericEqual(RoundToN(f(3),BuiltinPrecisionGet()), 0.6666666667,BuiltinPrecisionGet());\nNFunction(\"f1\", \"f\", [x]);\nVerify(f1(0), Undefined);\nNumericEqual(RoundToN(f1(3),BuiltinPrecisionGet()), 0.6666666667,BuiltinPrecisionGet());\nRetract(\"f\",1);\nRetract(\"f1\",1);\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\functional\\NFunction.mpw";
        userFunctionsTestsMap.put("NFunction",testString);

        testString = new String[3];
        testString[0] = "48";
        testString[1] = "\n// generate no errors\nVerify(Error?(), False);\nVerify(Error?(\"testing\"), False);\nVerify(Assert(\"testing\") 1=?1, True);\nVerify(Error?(), False);\nVerify(Error?(\"testing\"), False);\nVerify(Assert(\"testing1234\", [1,2,3,4]) 1=?1, True);\nVerify(Error?(), False);\nVerify(Error?(\"testing\"), False);\nVerify(Error?(\"testing1234\"), False);\n\nVerify(PipeToString()DumpErrors(), \"\");\n\n// generate some errors\nVerify(Assert(\"testing\") 1=?0, False);\nVerify(Error?(), True);\nVerify(Error?(\"testing\"), True);\nVerify(Error?(\"testing1234\"), False);\nVerify(Assert(\"testing1234\", [1,2,3,4]) 1=?0, False);\nVerify(Error?(), True);\nVerify(Error?(\"testing\"), True);\nVerify(Error?(\"testing1234\"), True);\n\n// report errors\nVerify(PipeToString()DumpErrors(), \"Error: testing\nError: testing1234: [1, 2, 3, 4]\n\");\n\n// no more errors now\nVerify(Error?(), False);\nVerify(Error?(\"testing\"), False);\nVerify(Error?(\"testing1234\"), False);\n\n// generate some more errors\nVerify(Assert(\"testing\") 1=?0, False);\nVerify(Assert(\"testing1234\", [1,2,3,4]) 1=?0, False);\nVerify(GetError(\"testing1234567\"), False);\n\n// handle errors\nVerify(GetError(\"testing\"), True);\nVerify(Error?(), True);\nVerify(Error?(\"testing\"), True);\nVerify(Error?(\"testing1234\"), True);\n\nVerify(ClearError(\"testing\"), True);\nVerify(Error?(), True);\nVerify(Error?(\"testing\"), False);\nVerify(Error?(\"testing1234\"), True);\n// no more \"testing\" error\nVerify(ClearError(\"testing\"), False);\nVerify(Error?(), True);\nVerify(Error?(\"testing\"), False);\nVerify(Error?(\"testing1234\"), True);\n\nVerify(GetError(\"testing1234\"), [1,2,3,4]);\nVerify(Error?(), True);\nVerify(Error?(\"testing\"), False);\nVerify(Error?(\"testing1234\"), True);\n\nVerify(ClearError(\"testing1234\"), True);\nVerify(Error?(), False);\nVerify(Error?(\"testing\"), False);\nVerify(Error?(\"testing1234\"), False);\nVerify(ClearError(\"testing1234\"), False);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\io\\Error_.mpw";
        userFunctionsTestsMap.put("error_reporting",testString);

        testString = new String[3];
        testString[0] = "83";
        testString[1] = "\n//Dimensions (Tensor Rank).\n\nVerify(Dimensions(a),[]);\nVerify(Dimensions([]),[0]);\nVerify(Dimensions([a,b]),[2]);\nVerify(Dimensions([[]]),[1,0]);\nVerify(Dimensions([[a]]),[1,1]);\nVerify(Dimensions([[],a]),[2]);\nVerify(Dimensions([[a],b]),[2]);\nVerify(Dimensions([[],[]]),[2,0]);\nVerify(Dimensions([[],[[]]]),[2]);\nVerify(Dimensions([[a,b],[c]]),[2]);\nVerify(Dimensions([[a,b],[c,d]]),[2,2]);\nVerify(Dimensions([[a,b],[c,d],[e,f]]),[3,2]);\nVerify(Dimensions([[a,b,c],[d,e,f],[g,h,i]]),[3,3]);\nVerify(Dimensions([[a,b,c],[d,e,f]]),[2,3]);\nVerify(Dimensions([[[a,b]],[[c,d]]]), [2,1,2]);\nVerify(Dimensions([[[[a],[b]]],[[[c],d]]]),[2,1,2]);\nVerify(Dimensions([[[[[a,b]]]],[[[c,d]]]]),[2,1,1]);\nVerify(Dimensions([[[[[a,b]]]],[[[c],[d]]]]),[2,1]);\nVerify(Dimensions([[[]]]),[1,1,0]);\nVerify(Dimensions([[[a]]]),[1,1,1]);\nVerify(Dimensions([[[[a]]],[[[b]]]]),[2,1,1,1]);\nVerify(Dimensions([[[[a],[b]]],[[[c],[d]]]]),[2,1,2,1]);\nVerify(Dimensions([[[[a,b]]],[[[c,d]]]]),[2,1,1,2]);\nVerify(Dimensions([[[[a,b]],[[c,d]]]]),[1,2,1,2]);\nVerify(Dimensions([[[[[[a,b],[c]]]]],[[[d],[e,f,g]]]]), [2,1]);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\linalg\\Dimensions.mpw";
        userFunctionsTestsMap.put("Dimensions",testString);

        testString = new String[3];
        testString[0] = "1";
        testString[1] = "\nVerify(Intersection([aa,b,c],[b,c,d]),[b,c]);\nVerify(Union([aa,b,c],[b,c,d]),[aa,b,c,d]);\nVerify(Difference([aa,b,c],[b,c,d]),[aa]);\n\nNextTest(\"VarList\");\nVerify(VarList(x^2+y^3) , [x , y]);\nVerify(List(1,2,3),[1 , 2 , 3]);\n\nTesting(\"BubbleSort\");\nVerify(BubbleSort([2,3,1],\"<?\"),[1,2,3]);\nVerify(BubbleSort([2,3,1],\">?\"),[3,2,1]);\n\nTesting(\"HeapSort\");\nVerify(HeapSort([2,3,1],\"<?\"),[1,2,3]);\nVerify(HeapSort([2,1,3],\">?\"),[3,2,1]);\nVerify(HeapSort([7,3,1,2,6],\"<?\"),[1,2,3,6,7]);\nVerify(HeapSort([6,7,1,3,2],\">?\"),[7,6,3,2,1]);\n\nVerify(Type(Cos(x)),\"Cos\");\nVerify(ArgumentsCount(Cos(x)),1);\nVerify(Contains?([a,b,c],b),True);\nVerify(Contains?([a,b,c],d),False);\n\nVerify(Append([a,b,c],d),[a,b,c,d]);\nVerify(RemoveDuplicates([a,b,b,c]),[a,b,c]);\nVerify(Count([a,b,b,c],b),2);\nVerify(VarList(x*Cos(x)),[x]);\n\n\n{\n  Local(l);\n  l:=[1,2,3];\n  DestructiveDelete(l,1);\n  Verify(l,[2,3]);\n  DestructiveInsert(l,1,1);\n  Verify(l,[1,2,3]);\n  l[1] := 2;\n  Verify(l,[2,2,3]);\n  l[1] := 1;\n  DestructiveDelete(l,3);\n  Verify(l,[1,2]);\n  DestructiveInsert(l,3,3);\n  Verify(l,[1,2,3]);\n  DestructiveDelete(FlatCopy(l),1);\n  Verify(l,[1,2,3]);\n};\n\nVerify(BuildList(i!,i,1,4,1),[1,2,6,24]);\nVerify(PermutationsList([a,b,c]),[[a,b,c],[a,c,b],[c,a,b],[b,a,c],[b,c,a],[c,b,a]]);\n\nTesting(\"ListOperations\");\nVerify(First([a,b,c]),a);\nVerify(Rest([a,b,c]),[b,c]);\nVerify(DestructiveReverse([a,b,c]),[c,b,a]);\nVerify(ListToFunction([a,b,c]),Hold(a(b,c)));\nVerify(FunctionToList(Hold(a(b,c))),[a,b,c]);\n\nVerify(Delete([a,b,c],2),[a,c]);\nVerify(Insert([a,c],2,b),[a,b,c]);\n\nTesting(\"Length\");\nVerify(Length([a,b]),2);\nVerify(Length([]),0);\n\nTesting(\"Nth\");\nVerify(Nth([a,b],1),a);\nVerify([a,b,c][2],b);\n\nTesting(\"Concat\");\nVerify(Concat([a,b],[c,d]),[a,b,c,d]);\n//This is simply not true!!! Verify(Hold(Concat([a,b],[c,d])),Concat([a,b],[c,d]));\n\n/* todo:tk:commenting out for minimal version of the scripts.\nTesting(\"Binary searching\");\nVerify(BSearch(100,[[n],n^2-15]), -1);\nVerify(BSearch(100,[[n],n^2-16]), 4);\nVerify(BSearch(100,[[n],n^2-100002]), -1);\nVerify(BSearch(100,[[n],n^2-0]), -1);\nVerify(FindIsq(100,[[n],n^2-15]), 3);\nVerify(FindIsq(100,[[n],n^2-16]), 4);\nVerify(FindIsq(100,[[n],n^2-100002]), 100);\nVerify(FindIsq(100,[[n],n^2-0]), 1);\n*/\n\nVerify(Difference(FuncList(a*b/c*d), [*,/]), []);\nVerify(Difference(FuncListArith(0*x*Sin(a/b)*Ln(Cos(y-z)+Sin(a))), [*,Ln,Sin]), []);\nVerify(Difference(VarListArith(x+a*y^2-1), [x,a,y^2]), []);\n\nVerify(Difference(FuncList(CUnparsable?({i:=0;While(i<?10){i++; a--; a:=a+i; [];};})), [CUnparsable?,Block,:=,While,<?,++,--,ToAtom(\"+\"),List]), []);\nVerify(FuncList([1,2,3]),[List]);\nVerify(FuncList([[],[]]),[List]);\nVerify(FuncList([]),[List]);\n\nTesting(\"AssocDelete\");\n{\n  Local(hash);\n  hash:=[[\"A\",1],[\"A\",2],[\"B\",3],[\"B\",4]];\n  AssocDelete(hash,[\"B\",3]);\n  Verify(hash, [[\"A\",1],[\"A\",2],[\"B\",4]]);\n  Verify(AssocDelete(hash,\"A\"),True);\n  Verify(hash, [[\"A\",2],[\"B\",4]]);\n  Verify(AssocDelete(hash,\"C\"),False);\n  Verify(hash, [[\"A\",2],[\"B\",4]]);\n  AssocDelete(hash,\"A\");\n  Verify(hash, [[\"B\",4]]);\n  AssocDelete(hash, [\"A\",2]);\n  AssocDelete(hash,\"A\");\n  Verify(hash, [[\"B\",4]]);\n  Verify(AssocDelete(hash,\"B\"),True);\n  Verify(hash, []);\n  Verify(AssocDelete(hash,\"A\"),False);\n  Verify(hash, []);\n};\nTesting(\"-- Arithmetic Operations\");\nVerify(1+[3,4],[4,5]);\nVerify([3,4]+1,[4,5]);\nVerify([1]+[3,4],Hold([1]+[3,4]));\nVerify([3,4]+[1],Hold([3,4]+[1]));\nVerify([1,2]+[3,4],[4,6]);\nVerify(1-[3,4],[-2,-3]);\nVerify([3,4]-1,[2,3]);\nVerify([1]-[3,4],Hold([1]-[3,4]));\nVerify([3,4]-[1],Hold([3,4]-[1]));\nVerify([1,2]-[3,4],[-2,-2]);\nVerify(2*[3,4],[6,8]);\nVerify([3,4]*2,[6,8]);\nVerify([2]*[3,4],Hold([2]*[3,4]));\nVerify([3,4]*[2],Hold([3,4]*[2]));\nVerify([1,2]*[3,4],[3,8]);\nVerify(2/[3,4],[2/3,1/2]);\nVerify([3,4]/2,[3/2,2]);\nVerify([2]/[3,4],Hold([2]/[3,4]));\nVerify([3,4]/[2],Hold([3,4]/[2]));\nVerify([1,2]/[3,4],[1/3,1/2]);\nVerify(2^[3,4],[8,16]);\nVerify([3,4]^2,[9,16]);\nVerify([2]^[3,4],Hold([2]^[3,4]));\nVerify([3,4]^[2],Hold([3,4]^[2]));\nVerify([1,2]^[3,4],[1,16]);\n\n// non-destructive Reverse operation\n{\n  Local(lst,revlst);\n  lst:=[a,b,c,13,19];\n  revlst:=Reverse(lst);\n  Verify(revlst,[19,13,c,b,a]);\n  Verify(lst,[a,b,c,13,19]);\n};\nVerify(Assigned?(lst),False);\nVerify(Assigned?(revlst),False);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\lists\\lists_tests.mpw";
        userFunctionsTestsMap.put("lists",testString);

        testString = new String[3];
        testString[0] = "59";
        testString[1] = "\nVerify(MapSingle(\"!\",[1,2,3,4]),[1,2,6,24]);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\lists\\MapSingle.mpw";
        userFunctionsTestsMap.put("MapSingle",testString);

        testString = new String[3];
        testString[0] = "45";
        testString[1] = "\n/* Reverse and FlatCopy (and some friends) would segfault in the past if passed a string as argument.\n * I am not opposed to overloading these functions to also work on strings per se, but for now just\n * check that they return an error in stead of segfaulting.\n */\nVerify(ExceptionCatch(Reverse(\"abc\"),\"Exception\"), \"Exception\");\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\lists\\Reverse.mpw";
        userFunctionsTestsMap.put("Reverse",testString);

        testString = new String[3];
        testString[0] = "106";
        testString[1] = "\nRulebaseHoldArguments(\"sin\", [x]);\nVerify(a[2]*Sin(x)/:[Sin(_x) <- sin(x)],a[2]*sin(x));\nRetract(\"sin\", 1);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\localrules\\slash_colon_operator.mpw";
        userFunctionsTestsMap.put("/:",testString);

        testString = new String[3];
        testString[0] = "48";
        testString[1] = "\n// Actually, the following Groebner test is just to check that the program doesn't crash on this,\n// more than on the exact result (which is hopefully correct also ;-) )\nVerify(Groebner([x*(y-1),y*(x-1)]),[x*y-x,x*y-y,y-x,y^2-y]);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\multivar\\Groebner.mpw";
        userFunctionsTestsMap.put("Groebner",testString);

        testString = new String[3];
        testString[0] = "188";
        testString[1] = "\nNextTest(\"Test arithmetic: NormalForm\");\n\nTestMathPiper(NormalForm(MM((x+y)^5)),y^5+5*x*y^4+10*x^2*y^3+10*x^3*y^2+5*x^4*y+x^5);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\multivar\\sparsenomial\\sparsenomial.mpw";
        userFunctionsTestsMap.put("NormalForm",testString);

        testString = new String[3];
        testString[0] = "49";
        testString[1] = "\nVerify(GaussianInteger?(3+4*I),True );\nVerify(GaussianInteger?(5),True);\nVerify(GaussianInteger?(1.1), False );\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\numbers\\gaussianintegers\\GaussianInteger_.mpw";
        userFunctionsTestsMap.put("GaussianInteger?",testString);

        testString = new String[3];
        testString[0] = "85";
        testString[1] = "\nVerify(IntLog(23^45, 67), 33);\nVerify(IntLog(1, 67), 0);\nVerify(IntLog(2, 67), 0);\nVerify(IntLog(0, 67), 0);\nVerify(IntLog(1, 1), Undefined);\nVerify(IntLog(2, 1), Undefined);\nVerify(IntLog(256^8, 4), 32);\nVerify(IntLog(256^8-1, 4), 31);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\numbers\\IntLog.mpw";
        userFunctionsTestsMap.put("IntLog",testString);

        testString = new String[3];
        testString[0] = "45";
        testString[1] = "\nVerify(NextPrime(65537), 65539);\nVerify(NextPrime(97192831),97192841);\nVerify(NextPrime(14987234876128361),14987234876128369);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\numbers\\NextPrime.mpw";
        userFunctionsTestsMap.put("NextPrime",testString);

        testString = new String[3];
        testString[0] = "189";
        testString[1] = "\n// you need to use ListToFunction for this one as -1 is actually -(1), eg. a unary function (minus)\n// applied to a positive integer (1). ListToFunction evaluates its arguments, resulting in a negative\n// integer (-1).\nVerify(NthRoot(-1,2),ListToFunction([NthRoot,-1,2]));\n\nVerify(NthRoot(2,1),Hold(NthRoot(2,1)));\nVerify(NthRoot(2,2),[1,2]);\nVerify(NthRoot(12,2),[2,3]);\nVerify(NthRoot(12,3),[1,12]);\nVerify(NthRoot(27,3),[3,1]);\nVerify(NthRoot(17*13,2),[1,17*13]);\nVerify(NthRoot(17*17*13,2),[17,13]);\nVerify(NthRoot(17*17*17*13,2),[17,17*13]);\nVerify(NthRoot(17*17*17*13,3),[17,13]);\nVerify(NthRoot(17*17*17*17*13*13,2),[17*17*13,1]);\nVerify(NthRoot(17*17*17*17*13*13,3),[17,17*13*13]);\nVerify(NthRoot(17*17*17*17*13*13,4),[17,13*13]);\nVerify(NthRoot(17*17*17*17*13*13,5),[1,17*17*17*17*13*13]);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\numbers\\NthRoot.mpw";
        userFunctionsTestsMap.put("NthRoot",testString);

        testString = new String[3];
        testString[0] = "75";
        testString[1] = "\nVerify(Prime?(65539),True);\nVerify(Prime?(0),False);\nVerify(Prime?(-1),False);\nVerify(Prime?(1),False);\nVerify(Prime?(2),True);\nVerify(Prime?(3),True);\nVerify(Prime?(4),False);\nVerify(Prime?(5),True);\nVerify(Prime?(6),False);\nVerify(Prime?(7),True);\nVerify(Prime?(-60000000000),False);\nVerify(Prime?(6.1),False); \n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\numbers\\Prime_.mpw";
        userFunctionsTestsMap.put("Prime?",testString);

        testString = new String[3];
        testString[0] = "71";
        testString[1] = "\nVerify(SmallPrime?(137),True);\nVerify(SmallPrime?(138),False);\nVerify(SmallPrime?(65537),True);\nVerify(SmallPrime?(65539),False);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\numbers\\SmallPrime_.mpw";
        userFunctionsTestsMap.put("SmallPrime?",testString);

        testString = new String[3];
        testString[0] = "346";
        testString[1] = "\n/* I stringified the results for now, as that is what the tests used to mean. The correct way to deal with this\n * would be to compare the resulting numbers to accepted precision.\n */\nVerify(PipeToString()Write(Plot2D(a,-1->1,output: data,points: 4,depth: 0)), \"[[[-1,-1],[-0.5,-0.5],[0.0,0.0],[0.5,0.5],[1,1]]]\");\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\plots\\_2d\\plot2d.mpw";
        userFunctionsTestsMap.put("Plot2D",testString);

        testString = new String[3];
        testString[0] = "433";
        testString[1] = "\n/* I stringified the results for now, as that is what the tests used to mean. The correct way to deal with this\n * would be to compare the resulting numbers to accepted precision.\n */\n{\n  Local(result);\n  result:=\"[[[-1,-1,-1],[-1,0,-1],[-1,1,-1],[0,-1,0],[0,0,0],[0,1,0],[1,-1,1],[1,0,1],[1,1,1]]]\";\n  Verify(PipeToString()Write(Plot3DS(a,-1->1,-1->1,output: data,points: 2)), result);\n};\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\plots\\_3d\\plot3ds.mpw";
        userFunctionsTestsMap.put("Plot3DS",testString);

        testString = new String[3];
        testString[0] = "44";
        testString[1] = "\nVerify(Constant?(Pi), True);\nVerify(Constant?(Exp(1)+Sqrt(3)), True);\nVerify(Constant?(x), False);\nVerify(Constant?(Infinity), True);\nVerify(Constant?(-Infinity), True);\nVerify(Constant?(Undefined), True);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\predicates\\Constant_.mpw";
        userFunctionsTestsMap.put("Constant?",testString);

        testString = new String[3];
        testString[0] = "29";
        testString[1] = "\n// Problem with FloatIsInt? and gmp\nVerify(FloatIsInt?(3.1415926535e9), False);\nVerify(FloatIsInt?(3.1415926535e10), True);\nVerify(FloatIsInt?(3.1415926535e20), True);\nVerify(FloatIsInt?(0.3e20), True);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\predicates\\FloatIsInt_.mpw";
        userFunctionsTestsMap.put("FloatIsInt?",testString);

        testString = new String[3];
        testString[0] = "62";
        testString[1] = "\nVerify(HasExpression?(a*b+1,1),True);\nVerify(HasExpression?(a+Sin(b*c),c),True);\nVerify(HasExpression?(a*b+1,2),False);\nVerify(HasExpressionArithmetic?(a*b+1,ToAtom(\"+\")),False);\nVerify(HasExpressionArithmetic?(a*b+1,1),True);\nVerify(HasExpressionArithmetic?(a+Sin(b*c),c),False);\nVerify(HasExpressionArithmetic?(a+Sin(b*c),Sin(b*c)),True);\n\nRulebaseHoldArguments(\"f\",[a]);\nVerify(HasExpression?(a*b+f([b,c]),f),False);\nVerify(HasExpressionArithmetic?(a*b+f([b,c]),c),False);\nRetract(\"f\",1);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\predicates\\HasExpression_.mpw";
        userFunctionsTestsMap.put("HasExpression?",testString);

        testString = new String[3];
        testString[0] = "53";
        testString[1] = "\nVerify(HasFunction?(a*b+1,*),True);\nVerify(HasFunction?(a+Sin(b*c),*),True);\nVerify(HasFunction?(a*b+1,List),False);\n\nRulebaseHoldArguments(\"f\",[a]);\nVerify(HasFunction?(a*b+f([b,c]),List),True);\nRetract(\"f\",1);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\predicates\\HasFunction_.mpw";
        userFunctionsTestsMap.put("HasFunction?",testString);

        testString = new String[3];
        testString[0] = "48";
        testString[1] = "\nVerify(HasFunctionArithmetic?(a*b+1,ToAtom(\"+\")),True);\nVerify(HasFunctionArithmetic?(a+Sin(b*c),*),False);\nVerify(HasFunctionArithmetic?(a+Sin(b*c),Sin),True);\n\nRulebaseHoldArguments(\"f\",[a]);\nVerify(HasFunctionArithmetic?(a*b+f([b,c]),List),False);\nRetract(\"f\",1);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\predicates\\HasFunctionArithmetic_.mpw";
        userFunctionsTestsMap.put("HasFunctionArithmetic?",testString);

        testString = new String[3];
        testString[0] = "258";
        testString[1] = "\nVerify(Scalar?(a),True);\nVerify(Scalar?([a]),False);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\predicates\\matrix.mpw";
        userFunctionsTestsMap.put("Scalar?",testString);

        testString = new String[3];
        testString[0] = "268";
        testString[1] = "\nVerify(Vector?(1),False);\nVerify(Vector?(a),False);\nVerify(Vector?(Sin(a)+2),False);\nVerify(Vector?([]),True);\nVerify(Vector?([[]]),False);\nVerify(Vector?([1,2,a,4]),True);\nVerify(Vector?([1,[2,a],4]),False);\nVerify(Vector?([[a,b,c]]),False);\n\nTesting(\"-- Vector?(Number?)\");\nVerify(Vector?(Number?,1),False);\nVerify(Vector?(Number?,[]),True);\nVerify(Vector?(Number?,[a,b,c]),False);\nVerify(Vector?(Number?,[a,2,c]),False);\nVerify(Vector?(Number?,[2,2.5,4]),True);\nVerify(Vector?(Number?,[Pi,2,3]),False);\nVerify(Vector?(Number?,[[1],[2]]),False);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\predicates\\matrix.mpw";
        userFunctionsTestsMap.put("Vector?",testString);

        testString = new String[3];
        testString[0] = "294";
        testString[1] = "\nVerify(Vector?(1),False);\nVerify(Vector?(a),False);\nVerify(Vector?(Sin(a)+2),False);\nVerify(Vector?([]),True);\nVerify(Vector?([[]]),False);\nVerify(Vector?([1,2,a,4]),True);\nVerify(Vector?([1,[2,a],4]),False);\nVerify(Vector?([[a,b,c]]),False);\n\nTesting(\"-- Vector?(Number?)\");\nVerify(Vector?(Number?,1),False);\nVerify(Vector?(Number?,[]),True);\nVerify(Vector?(Number?,[a,b,c]),False);\nVerify(Vector?(Number?,[a,2,c]),False);\nVerify(Vector?(Number?,[2,2.5,4]),True);\nVerify(Vector?(Number?,[Pi,2,3]),False);\nVerify(Vector?(Number?,[[1],[2]]),False);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\predicates\\matrix.mpw";
        userFunctionsTestsMap.put("Vector?",testString);

        testString = new String[3];
        testString[0] = "320";
        testString[1] = "Verify(Matrix?(1),False);\nVerify(Matrix?([]),False);\nVerify(Matrix?([a,b]),False);\nVerify(Matrix?([[]]),True);\nVerify(Matrix?([[a]]),True);\nVerify(Matrix?([[[a]]]),False);\nVerify(Matrix?([[],a]),False);\nVerify(Matrix?([[a],b]),False);\nVerify(Matrix?([[],[]]),True);\nVerify(Matrix?([[[]],[]]),False);\nVerify(Matrix?([[],[[]]]),False);\nVerify(Matrix?([[a,b],[c]]),False);\nVerify(Matrix?([[a,b],[c,d]]),True);\nVerify(Matrix?([[a,b],[c,[d]]]),False);\nVerify(Matrix?([[[]]]), False);\nVerify(Matrix?([[[a]]]), False);\nVerify(Matrix?([[[[a]]],[[[b]]]]),False);\n\nTesting(\"---- Matrix?(Integer?)\");\nVerify(Matrix?(Integer?,[[a,1]]),False);\nVerify(Matrix?(Integer?,[[1,2]]),True);\nVerify(Matrix?(Integer?,[[1,2/3]]),False);\nVerify(Matrix?(Integer?,[[1,2,3],[4,5,6]]),True);\nVerify(Matrix?(Integer?,[[1,[2],3],[4,5,6]]),False);\nVerify(Matrix?(Integer?,[[1,2,3],[4,5]]),False);\nVerify(Matrix?(Integer?,[[Sin(1),2,3],[4,5,6]]),False);\nVerify(Matrix?(Integer?,[[Sin(0),2,3],[4,5,6]]),True);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\predicates\\matrix.mpw";
        userFunctionsTestsMap.put("Matrix?",testString);

        testString = new String[3];
        testString[0] = "355";
        testString[1] = "\nVerify(SquareMatrix?([[]]),False);\nVerify(SquareMatrix?([[a]]),True);\nVerify(SquareMatrix?([[],[]]),False);\nVerify(SquareMatrix?([[a,b]]),False);\nVerify(SquareMatrix?([[a,b],[c,d]]),True);\nVerify(SquareMatrix?([[a,b],[c,d],[e,f]]),False);\nVerify(SquareMatrix?([[a,b,c],[d,e,f],[g,h,i]]),True);\nVerify(SquareMatrix?([[a,b,c],[d,e,f]]),False);\nVerify(SquareMatrix?([[[a,b]],[[c,d]]]), False);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\predicates\\matrix.mpw";
        userFunctionsTestsMap.put("SquareMatrix?",testString);

        testString = new String[3];
        testString[0] = "47";
        testString[1] = "\nVerify(Variable?(a),True);\nVerify(Variable?(Sin(a)),False);\nVerify(Variable?(2),False);\nVerify(Variable?(-2),False);\nVerify(Variable?(2.1),False);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\predicates\\Variable_.mpw";
        userFunctionsTestsMap.put("Variable?",testString);

        testString = new String[3];
        testString[0] = "60";
        testString[1] = "\nVerify(BooleanList(8, 36), [False,False,True,False,False,True,False,False]);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\proposed\\logic\\BooleanList.mpw";
        userFunctionsTestsMap.put("BooleanList",testString);

        testString = new String[3];
        testString[0] = "50";
        testString[1] = "\nVerify(BooleanLists(2), [[False,False],[False,True],[True,False],[True,True]]);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\proposed\\logic\\BooleanLists.mpw";
        userFunctionsTestsMap.put("BooleanLists",testString);

        testString = new String[3];
        testString[0] = "102";
        testString[1] = "\nUnassign(a,b,c);\n\nVerify(Subexpressions(a*b+c), [a,b,c,a*b,a*b+c]);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\proposed\\logic\\Subexpressions.mpw";
        userFunctionsTestsMap.put("Subexpressions",testString);

        testString = new String[3];
        testString[0] = "77";
        testString[1] = "\nUnassign(a,b);\n\nVerify(TruthTable(a And? b), [[a,b,a And? b],[True,True,True],[True,False,False],[False,True,False],[False,False,False]]);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\proposed\\logic\\TruthTable.mpw";
        userFunctionsTestsMap.put("TruthTable",testString);

        testString = new String[3];
        testString[0] = "186";
        testString[1] = "\nVerify(RabinMiller(1037),False);\nVerify(RabinMiller(1038),False);\nVerify(RabinMiller(1039),True);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\rabinmiller\\RabinMiller.mpw";
        userFunctionsTestsMap.put("RabinMiller",testString);

        testString = new String[3];
        testString[0] = "77";
        testString[1] = "\nTestMathPiper( Simplify((x+y)*(x-y)-(x+y)^2), -2*y^2-2*x*y );\nTestMathPiper( Simplify(1+x+x+3*y-4*(x+y+2)), -2*x-y-7 );\nTestMathPiper( Simplify((1+I)^4), -4 );\nTestMathPiper( Simplify((x-y)/(x*y)), 1/y-1/x );\n//See below, now handled with II KnownFailure(TestMathPiper( Simplify((x+I)^4), x^4+4*x^3*I-6*x^2-4*x*I+1 ));\n\nTestMathPiper( Simplify((xx+II)^4), xx^4+4*xx^3*II-6*xx^2-4*xx*II+1 );\n\n/* todo:tk:commenting out for the minimal version of the scripts.\nTestMathPiper( Simplify(Differentiate(x,4)Exp(-x^2/2)), Exp(-x^2/2)*(x^4-6*x^2+3));\n*/\n\nTestMathPiper( Simplify(1),1);\nTestMathPiper( Simplify(1/x ), 1/x );\nTestMathPiper( Simplify( 1/(1/x+1) ),x/(x+1) );\nTestMathPiper( Simplify(1/(1/(1/x+1)+1) ),(x+1)/(2*x+1) );\nTestMathPiper( Simplify(1/(1/(1/(1/x+1)+1)+1) ),(2*x+1)/(3*x+2) );\nTestMathPiper( Simplify(1/(1/(1/(1/(1/x+1)+1)+1)+1) ),(3*x+2)/(5*x+3) );\nTestMathPiper( Simplify(1/(1/(1/(1/(1/(1/x+1)+1)+1)+1)+1) ),(5*x+3)/(8*x+5) );\n\n\n\n/*Serge: these are not handled yet ;-)\nTestMathPiper( Simplify((x^2-y^2)/(x+y)), x-y );\n*/\n\nTestMathPiper(ExpandFrac(x+y/x+1/3),(x^2+y+x/3)/x);\n\n// this did not work until the latest fix to ExpandBrackets using MM()\nVerify(\nExpandBrackets(x*(a+b)*y*z)\n, x*a*y*z+x*b*y*z\n);\n\nVerify(\nExpandBrackets(ExpandFrac((x+1)/(x-1)+1/x))\n, Hold(x^2/(x^2-x)+(2*x)/(x^2-x)-1/(x^2-x))\n);\n\n// these used to fail. Added by Serge, resolved by Ayal\nVerify({Local(a);a:=0.1;Simplify(a*b);}, 0.1*b);\nVerify({Local(a);a:=0.1;Simplify(a/b);}, 0.1/b);\n\n\n\n\nVerify({Local(a);a:=0.1;Simplify((a*b*c)/(a*c*b));},1);\n\n\nLocalSymbols(p,a,x)\n{\n  p := a+2-(a+1);\n  Verify(Simplify(x^p),x);\n};\n\n\n\n/* todo:tk:commenting out for the minimal version of the scripts.\nLocalSymbols(f,p,a,b,x,n,simple,u,v)\n{\n  simple := [\n            Exp(_a)*Exp(_b) <- Exp(a+b),\n            Exp(_a)*_u*Exp(_b) <- u*Exp(a+b),\n            _u*Exp(_a)*Exp(_b) <- u*Exp(a+b),\n            Exp(_a)*Exp(_b)*_u <- u*Exp(a+b),\n            _u*Exp(_a)*_v*Exp(_b) <- u*v*Exp(a+b),\n            Exp(_a)*_u*Exp(_b)*_v <- u*v*Exp(a+b),\n            _u*Exp(_a)*Exp(_b)*_v <- u*v*Exp(a+b),\n            _u*Exp(_a)*_v*Exp(_b)*_w <- u*v*w*Exp(a+b)\n          ];\n\n  a := Simplify(Exp(x)*(Differentiate(x) x*Exp(-x)));\n  b := Exp(x)*Exp(-x)-Exp(x)*x*Exp(-x);\n\n  a:= (a /: simple);\n  b:= (b /: simple);\n\n  Verify(Simplify(a-(1-x)),0);\n  Verify(Simplify(b-(1-x)),0);\n\n};\n*/\n\n\n// The following line catches a bug reported where Simplify\n// would go into an infinite loop. It doesn't check the correctness\n// of the returned value as such, but merely the fact that this\n// simplification terminates in the first place.\n//\n// The problem was caused by a gcd calculation (from the multivariate\n// code) not terminating.\nVerify( Simplify((a^2+b^2)/(2*a)), (a^2+b^2)/(2*a) );\n\n\n/* Bug #10: I don't know what we want to return, but '0' is definitely wrong! */\nVerify(Simplify(x^(-2)/(1-x)^3) !=? 0, True);\n\n\n\n\n/* In MatchLinear and MatchPureSquare, the matched coefficients were\n * assigned to global variables that were not protected with LocalSymbols.\n */\n/* todo:tk:commenting out for the minimal version of the scripts.\n{\n  Local(a,b,A);\n  a:=mystr;\n  A:=mystr;\n  \n  //The real test here is that no error is generated due to the fact that variables a or A are set.\n   \n  Verify(Simplify((Integrate(x,a,b)Sin(x))-Cos(a)+Cos(b)),0);\n};\n*/\n\n\n/* Bug reported by Michael Borcherds: Simplify(((4*x)-2.25)/2)\n   returned some expression with three calls to Gcd, which was technically\n   correct, but not the intended simplification.\n */\nVerify(Zero?(Simplify(Simplify(((4*x)-2.25)/2)-(2*x-2.25/2))),True);\n\n\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\simplify\\Simplify.mpw";
        userFunctionsTestsMap.put("Simplify",testString);

        testString = new String[3];
        testString[0] = "122";
        testString[1] = "\nVerify(Sort([4,7,23,53,-2,1], \">?\"), [53,23,7,4,1,-2]);\nVerify(SortIndexed([4,7,23,53,-2,1], \">?\"), [[53,23,7,4,1,-2],[4,3,2,1,6,5]]);\nVerify(Sort([11,-17,-1,-19,-18,14,4,7,9,-15,13,11,-2,2,7,-19,5,-3,6,4,-1,14,5,-15,11]),\n            [-19,-19,-18,-17,-15,-15,-3,-2,-1,-1,2,4,4,5,5,6,7,7,9,11,11,11,13,14,14]);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\sorting\\Sort.mpw";
        userFunctionsTestsMap.put("Sort",testString);

        testString = new String[3];
        testString[0] = "84";
        testString[1] = "\n/* Bug #11 */\nVerify(ArcCos(Cos(beta)) !=? beta, True);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stdfuncs\\ArcCos.mpw";
        userFunctionsTestsMap.put("ArcCos",testString);

        testString = new String[3];
        testString[0] = "87";
        testString[1] = "\nNumericEqual(\nRoundToN(NM(ArcSin(0.0000000321232123),50),50)\n, 0.000000032123212300000005524661243020493367846793163005802\n,50);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stdfuncs\\ArcSin.mpw";
        userFunctionsTestsMap.put("ArcSin",testString);

        testString = new String[3];
        testString[0] = "103";
        testString[1] = "\nVerify(Cos(0),1);\nVerify(Cos(2*Pi), 1);\nVerify(Cos(4*Pi), 1);\nVerify(NM(Cos(Pi/6)), NM(Sqrt(3/4)));\nNumericEqual(NM(Cos(Pi/2),BuiltinPrecisionGet()+1), 0,BuiltinPrecisionGet());\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stdfuncs\\Cos.mpw";
        userFunctionsTestsMap.put("Cos",testString);

        testString = new String[3];
        testString[0] = "11";
        testString[1] = "\nVerify(Cot(x),1/Tan(x));\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stdfuncs\\Cot.mpw";
        userFunctionsTestsMap.put("Cot",testString);

        testString = new String[3];
        testString[0] = "48";
        testString[1] = "\n/* Here follow some tests for MathBitCount. These were written while creating\n   the Java version, fixing BitCount in the process.\n */\nVerify(MathBitCount(3),2);\nVerify(MathBitCount(3.0),2);\n\nVerify(MathBitCount(4),3);\nVerify(MathBitCount(4.0),3);\n\nVerify(MathBitCount(0),0);\nVerify(MathBitCount(0.0),0);\n\nVerify(MathBitCount(0.5),0);\nVerify(MathBitCount(0.25),-1);\nVerify(MathBitCount(0.125),-2);\nVerify(MathBitCount(0.0125),-6);\n\nVerify(MathBitCount(-3),2);\nVerify(MathBitCount(-3.0),2);\n\nVerify(MathBitCount(-4),3);\nVerify(MathBitCount(-4.0),3);\n\nVerify(MathBitCount(-0),0);\nVerify(MathBitCount(-0.0),0);\n\nVerify(MathBitCount(-0.5),0);\nVerify(MathBitCount(-0.25),-1);\nVerify(MathBitCount(-0.125),-2);\nVerify(MathBitCount(-0.0125),-6);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stdfuncs\\elemfuncs\\MathBitCount.mpw";
        userFunctionsTestsMap.put("MathBitCount",testString);

        testString = new String[3];
        testString[0] = "31";
        testString[1] = "\nVerify(ExpNum(0),1);\nNumericEqual(ExpNum(0e-1),1,BuiltinPrecisionGet());\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stdfuncs\\numerical\\ExpNum.mpw";
        userFunctionsTestsMap.put("ExpNum",testString);

        testString = new String[3];
        testString[0] = "22";
        testString[1] = "\nVerify(InternalLnNum(1), 0);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stdfuncs\\numerical\\InternalLnNum.mpw";
        userFunctionsTestsMap.put("InternalLnNum",testString);

        testString = new String[3];
        testString[0] = "44";
        testString[1] = "\n// LogN used to hang on *all* input\nVerify(LogN(2)!=?0,True);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stdfuncs\\numerical\\LogN.mpw";
        userFunctionsTestsMap.put("LogN",testString);

        testString = new String[3];
        testString[0] = "159";
        testString[1] = "\nBuiltinPrecisionSet(22);\n\nNumericEqual(NewtonNum([[x], x+Sin(x)], 3, 5, 3), 3.14159265358979323846,20);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stdfuncs\\nummethods\\NewtonNum.mpw";
        userFunctionsTestsMap.put("NewtonNum",testString);

        testString = new String[3];
        testString[0] = "12";
        testString[1] = "\nVerify(Sech(x),1/Cosh(x));\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stdfuncs\\Sech.mpw";
        userFunctionsTestsMap.put("Sech",testString);

        testString = new String[3];
        testString[0] = "90";
        testString[1] = "\nVerify(NM(Sin(a)),Sin(a));\n\nVerify(Sin(2*Pi), 0);\nVerify(Sin(3*Pi/2)+1, 0);\nVerify(Sin(Pi/2), 1);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stdfuncs\\Sin.mpw";
        userFunctionsTestsMap.put("Sin",testString);

        testString = new String[3];
        testString[0] = "65";
        testString[1] = "\nVerify(Abs(Undefined),Undefined);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stubs\\Abs.mpw";
        userFunctionsTestsMap.put("Abs",testString);

        testString = new String[3];
        testString[0] = "322";
        testString[1] = "\nVerify(.1<?2,True);\nVerify(0.1<?2,True);\nVerify(.3<?2,True);\nVerify(2<?.1,False);\nVerify(2<?0.1,False);\nVerify(2<?.3,False);\nVerify(1e-5 <? 1, True);\nVerify(1e-5 <? 2e-5, True);\nVerify(1e-1 <? 2e-1, True);\nVerify(1e-15 <? 2e-15, True);\nVerify(1e-5 <? 1e-10, False);\nVerify(1e-5 <? 1e-2, True);\nVerify(-1e-5 <? 1e-5, True);\nVerify(-1e-5 <? 1e-6, True);\n\nBuiltinPrecisionSet(32);\nVerify(0.999999999999999999999999999992 <? 1, True);\nBuiltinPrecisionSet(10);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stubs\\comparison_operators.mpw";
        userFunctionsTestsMap.put("<?",testString);

        testString = new String[3];
        testString[0] = "349";
        testString[1] = "\nVerify(.1>?2,False);\nVerify(0.1>?2,False);\nVerify(.3>?2,False);\nVerify(2>?.1,True);\nVerify(2>?0.1,True);\nVerify(2>?.3,True);\nVerify(1e-15 >? 0, True);\nVerify(1e-5 >? 0, True);\nVerify(1e-4 >? 0, True);\nVerify(1e-3 >? 0, True);\nVerify(1e-2 >? 0, True);\nVerify(1e-1 >? 0, True);\nVerify(1e5 >? 0, True);\n\nBuiltinPrecisionSet(32);\nVerify(1.0000000000000000000000000000111 >? 1, True);\nBuiltinPrecisionSet(10);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stubs\\comparison_operators.mpw";
        userFunctionsTestsMap.put(">?",testString);

        testString = new String[3];
        testString[0] = "375";
        testString[1] = "\nVerify(1e-5 =? 2e-5, False);\nVerify(1e-5 =? 1e-6, False);\n\nVerify(A<?0,A<?0);\n\nVerify(A>?0,A>?0);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stubs\\comparison_operators.mpw";
        userFunctionsTestsMap.put("=?",testString);

        testString = new String[3];
        testString[0] = "390";
        testString[1] = "\nVerify((1==1) !=? True, True);\nVerify((a==a) !=? True, True);\nVerify((1==2) !=? False, True);\nVerify((a==2) !=? False, True);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stubs\\comparison_operators.mpw";
        userFunctionsTestsMap.put("!=?",testString);

        testString = new String[3];
        testString[0] = "403";
        testString[1] = "\nVerify(A<?0,A<?0);\nVerify(Undefined<?1, False);\n\nVerify(500 <? 0e-1,False);\n\n//Bug reported by Adrian Vontobel: comparison operators should coerce\n//to a real value as much as possible before trying the comparison.\nVerify(0.2*Pi <? 0.7, True);\nVerify(0.2*Pi <? 0.6, False);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stubs\\comparison_operators.mpw";
        userFunctionsTestsMap.put("<?",testString);

        testString = new String[3];
        testString[0] = "421";
        testString[1] = "\nVerify(A>?0,A>?0);\nVerify(Undefined>?Undefined, False);\nVerify(Undefined>?1, False);\n\n//Bug reported by Adrian Vontobel: comparison operators should coerce\n//to a real value as much as possible before trying the comparison.\nVerify(0.2*Pi >? 0.5, True);\nVerify(0.2*Pi >? 0.7, False);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stubs\\comparison_operators.mpw";
        userFunctionsTestsMap.put(">?",testString);

        testString = new String[3];
        testString[0] = "438";
        testString[1] = "\nVerify( 2/3 >=? 1/3, True);\nVerify(1 >=? 1.0, True);\nVerify(-1 >=? -1.0, True);\nVerify(0 >=? 0.0, True);\nVerify(0.0 >=? 0, True);\nVerify(Undefined >=? -4, False);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stubs\\comparison_operators.mpw";
        userFunctionsTestsMap.put(">=?",testString);

        testString = new String[3];
        testString[0] = "453";
        testString[1] = "\nVerify(1 <=? 1.0, True);\nVerify(-1 <=? -1.0, True);\nVerify(0 <=? 0.0, True);\nVerify(0.0 <=? 0, True);\nVerify(Undefined <=? -4, False);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stubs\\comparison_operators.mpw";
        userFunctionsTestsMap.put("<=?",testString);

        testString = new String[3];
        testString[0] = "76";
        testString[1] = "\nTestMathPiper(Expand((1+x)^2),x^2+2*x+1);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stubs\\Expand.mpw";
        userFunctionsTestsMap.put("Expand",testString);

        testString = new String[3];
        testString[0] = "76";
        testString[1] = "\n// Discovered that Floor didn't handle new exponent notation\nVerify(Floor(1001.1e-1),100);\nVerify(Floor(10.01e1),100);\nVerify(Floor(100.1),100);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stubs\\Floor.mpw";
        userFunctionsTestsMap.put("Floor",testString);

        testString = new String[3];
        testString[0] = "121";
        testString[1] = "\nVerify( Gcd( 324 + 1608*I, -11800 + 7900*I ),Complex(-52,16) );\n// I changed from Complex(-4,4) to Complex(4,4) as the GaussianGcd algorithm suddenly returned this instead.\n// However, as it turned out it was a bug in FloorN, introduced when\n// we moved to the new number classes (so the numbers did not get converted\n// to string and back any more). The number got prematurely truncated with\n// this test case (regression test added to regress.yts also).\n//TODO we can expand this with randomized tests\nVerify( Gcd( 7300 + 12*I, 2700 + 100*I), Complex(-4,4) );\n\n\n// Bug was found: gcd sometimes returned 0! Very bad, since the\n// value returned by gcd is usually used to divide out greatest\n// common divisors, and dividing by zero is not a good idea.\nVerify(Gcd(0,0),1);\nVerify(Gcd([0]),1);\n\n\n\nVerifyGaussianGcd(x,y):=\n{\n  Local(gcd);\n  gcd:=Gcd(x,y);\n//  Echo(x/gcd);\n//  Echo(y/gcd);\n  Verify(GaussianInteger?(x/gcd) And? GaussianInteger?(y/gcd),True);\n};\nVerifyGaussianGcd(324 + 1608*I, -11800 + 7900*I);\nVerifyGaussianGcd(7300 + 12*I, 2700 + 100*I);\nVerifyGaussianGcd(120-I*200,-336+50*I);\n\n/* Bug #3 */\nKnownFailure(Gcd(10,3.3) !=? 3.3 And? Gcd(10,3.3) !=? 1);\n/* I don't know what the answer should be, but buth 1 and 3.3 seem */\n/* certainly wrong. */\nVerify(Gcd(-10, 0), 10);\nVerify(Gcd(0, -10), 10);\n\n\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stubs\\Gcd.mpw";
        userFunctionsTestsMap.put("Gcd",testString);

        testString = new String[3];
        testString[0] = "64";
        testString[1] = "\nVerify( Lcm([7,11,13,17]), 7*11*13*17 );\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stubs\\Lcm.mpw";
        userFunctionsTestsMap.put("Lcm",testString);

        testString = new String[3];
        testString[0] = "120";
        testString[1] = "\n// Modulo generated a stack overflow on floats.\nVerify(Modulo(1.2,3.4),6/5);\n\n//TODO I need to understand why we need to put Eval here. Modulo(-1.2,3.4)-2.2 returns 0/5 where the 0 is not an integer according to the system. Round-off error?\nNumericEqual(NM(Eval(Modulo(-1.2,3.4))),2.2,BuiltinPrecisionGet());\n\nVerify(Modulo(-12/10,34/10),11/5);\n// just a test to see if Verify still gives correct error Verify(NM(Modulo(-12/10,34/10)),11/5);\n\n\n// Make sure Mod works threaded\nVerify(Modulo(2,Infinity),2);\nVerify(Modulo([2,1],[2,2]),[0,1]);\nVerify(Modulo([5,1],4),[1,1]);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stubs\\Modulo.mpw";
        userFunctionsTestsMap.put("Modulo",testString);

        testString = new String[3];
        testString[0] = "71";
        testString[1] = "\nVerify(Sqrt(Infinity),Infinity);\n\n// version 1.0.56: Due to MathBitCount returning negative values sometimes, functions depending on\n// proper functioning failed. MathSqrtFloat failed for instance on NM(1/2). It did give the right\n// result for 0.5.\nNumericEqual(NM(Sqrt(500000e-6),20),NM(Sqrt(0.0000005e6),20),20);\nNumericEqual(NM(Sqrt(0.5),20),NM(Sqrt(NM(1/2)),20),20);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\stubs\\Sqrt.mpw";
        userFunctionsTestsMap.put("Sqrt",testString);

        testString = new String[3];
        testString[0] = "52";
        testString[1] = "\nVerify(Add([1,2,3,4]), 10);\nVerify(Add([1]), 1);\nVerify(Add([]), 0);\nVerify(Add(1,2,3,4), 10);\nVerify(Add(1), 1);\n\n{\n  Local(list);\n  list:=[1,2,3,4,5];\n  Verify(Add(list)/Length(list), 3);\n  list:=[0];\n  Verify(Add(list)/Length(list), 0);\n  list:=[];\n  Verify(Add(list)/Length(list), Undefined);\n};\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\sums\\Add.mpw";
        userFunctionsTestsMap.put("Add",testString);

        testString = new String[3];
        testString[0] = "93";
        testString[1] = "\nVerify(261! - 261*260!, 0);\nVerify(300! / 250!, 251***300);\n\n// Verify that postfix operators can be applied one after the other\n// without brackets\nVerify((3!) !, 720);\nVerify(3! !, 720);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\sums\\exclamationpoint_operator.mpw";
        userFunctionsTestsMap.put("Factorial",testString);

        testString = new String[3];
        testString[0] = "81";
        testString[1] = "\nVerify(Minimum(0,1),0);\nVerify(Minimum([]), Undefined);\nVerify(Minimum([x]), x);\nVerify(Minimum(x), x);\nVerify(Minimum(Exp(x)), Exp(x));\nVerify(Minimum([1,2,3]), 1);\n// since Minimum(multiple args) is disabled, comment this out\nVerify(Minimum(1,2,3), 1);\nVerify(Minimum(1,2,0), 0);\nVerify(Minimum(5,2,3,4), 2);\nVerify(Minimum(5,2,0,4), 0);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\sums\\Minimum.mpw";
        userFunctionsTestsMap.put("Minimum",testString);

        testString = new String[3];
        testString[0] = "79";
        testString[1] = "\n// Product didn't check for correct input\nVerify(Product(10), Product(10));\nVerify(Product(-1), Product(-1));\nVerify(Product(Infinity), Product(Infinity));\nVerify(Product(1 .. 10),3628800);\n\nVerify(Product(i,1,3,i),6);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\sums\\Product.mpw";
        userFunctionsTestsMap.put("Product",testString);

        testString = new String[3];
        testString[0] = "41";
        testString[1] = "\nVerify( Subfactorial(0), 1 );\nVerify( Subfactorial(21), 18795307255050944540 );\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\sums\\Subfactorial.mpw";
        userFunctionsTestsMap.put("Subfactorial",testString);

        testString = new String[3];
        testString[0] = "69";
        testString[1] = "\nLogicTest([A],A And? A,      A);\nLogicTest([A],A And? True,   A);\nLogicTest([A],A And? False,  False);\nLogicTest([A],A Or?  True,   True);\nLogicTest([A],A Or?  False,  A);\nLogicTest([A],A Or?  Not? A,  True);\nLogicTest([A],A And? Not? A,  False);\nLogicTest([A,B],(A And? B) Or? (A And? B),             A And? B);\nLogicTest([A,B],A Or? (A And? B),                     A And?(A Or? B));\nLogicTest([A,B],(A And? B) And? A,                    (A And? B) And? A);\nLogicTest([A,B],Not? (A And? B) And? A,                (Not? A Or? Not? B) And? A);\nLogicTest([A,B],(A Or? B) And? Not? A,            B And? Not? A);\nLogicTest([A,B,C],(A Or? B) And? (Not? A Or? C),     (A Or? B) And? (C Or? Not? A));\nLogicTest([A,B,C],(B Or? A) And? (Not? A Or? C),     (A Or? B) And? (C Or? Not? A));\nLogicTest([A,B,C], A And? (A Or? B Or? C),       A);\nLogicTest([A,B,C], A And? (Not? A Or? B Or? C),  A And? (B Or? C));\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\testers\\LogicTest.mpw";
        userFunctionsTestsMap.put("LogicTest",testString);

        testString = new String[3];
        testString[0] = "42";
        testString[1] = "\nVerify(CanBeUni(x^(-1)),False);\n\n/* Bug report from Magnus Petursson regarding determinants of matrices that have symbolic entries */\n/* todo:tk:commenting out for the minimal version of the scripts.\n//Verify(CanBeUni(Determinant([[a,b],[c,d]])),True);\n*/\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\univar\\CanBeUni.mpw";
        userFunctionsTestsMap.put("CanBeUni",testString);

        testString = new String[3];
        testString[0] = "68";
        testString[1] = "\n// Coef accepted non-integer arguments as second argument, and\n// crashed on it.\nVerify(Coef(3*Pi,Pi),Coef(3*Pi,Pi));\nVerify(Coef(3*Pi,x), Coef(3*Pi,x));\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\univar\\Coef.mpw";
        userFunctionsTestsMap.put("Coef",testString);

        testString = new String[3];
        testString[0] = "48";
        testString[1] = "\nTestMathPiper(Content(1/2*x+1/2),1/2);\n\nTestMathPiper(Content(1/2*x+1/3),1/6);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\univar\\Content.mpw";
        userFunctionsTestsMap.put("Content",testString);

        testString = new String[3];
        testString[0] = "97";
        testString[1] = "\n// Univariates in Pi did not get handled well, due to Pi being\n// considered a constant, non-variable.\nVerify(Degree(Pi,Pi),1);\nVerify(Degree(2*Pi,Pi),1);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\univar\\Degree.mpw";
        userFunctionsTestsMap.put("Degree",testString);

        testString = new String[3];
        testString[0] = "50";
        testString[1] = "\nTestMathPiper(PrimitivePart(1/2*x+1/2),x+1);\n\nTestMathPiper(PrimitivePart(1/2*x+1/3),3*x+2);\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\univar\\PrimitivePart.mpw";
        userFunctionsTestsMap.put("PrimitivePart",testString);

        testString = new String[3];
        testString[0] = "443";
        testString[1] = "\n/* it worketh no more...\nTesting(\"Realistic example\");\nf:=Exp(I*lambda*eta)*w(T*(k+k1+lambda));\ng:=Simplify(Substitute(lambda,0) f+(k+k1)*(Differentiate(lambda)f)+k*k1*Differentiate(lambda)Differentiate(lambda)f );\nVerify(UnparseLatex(g), ...);\n*/\n\nVerify(\nUnparseLatex(Hold(Cos(A-B)*Sqrt(C+D)-(a+b)*c^d+2*I+Complex(a+b,a-b)/Complex(0,1)))\n,\"$\\\\cos ( A - B)  \\\\times \\\\sqrt{C + D} - ( a + b)  \\\\times c ^{d} + 2 \\\\times \\\\imath  + \\\\frac{a + b + \\\\imath  \\\\times ( a - b) }{\\\\imath } $\"\n);\n\nVerify(\nUnparseLatex(Hold(Exp(A*B)/C/D/(E+F)*G-(-(a+b)-(c-d))-b^(c^d) -(a^b)^c))\n,\"$\\\\frac{\\\\frac{\\\\frac{\\\\exp ( A \\\\times B) }{C} }{D} }{E + F}  \\\\times G - (  - ( a + b)  - ( c - d) )  - b ^{c ^{d}} - ( a ^{b})  ^{c}$\"\n);\n\nVerify(\nUnparseLatex(Hold(Cos(A-B)*Sin(a)*f(b,c,d*(e+1))*Sqrt(C+D)-(g(a+b)^(c+d))^(c+d)))\n,\"$\\\\cos ( A - B)  \\\\times \\\\sin a \\\\times f( b, c, d \\\\times ( e + 1) )  \\\\times \\\\sqrt{C + D} - ( g( a + b)  ^{c + d})  ^{c + d}$\"\n);\n\n\n/* This test is commented out because it throws an exception when orthopoly.mpw is removed from the build process.\n// testing latest features: \\\\times, %, (a/b)^n, BinomialCoefficient(), BesselI, OrthoH\nVerify(\nUnparseLatex(3*2^n+Hold(x*10!) + (x/y)^2 + BinomialCoefficient(x,y) + BesselI(n,x) + Maximum(a,b) + OrthoH(n,x))\n, \"$3\\\\times 2 ^{n} + x\\\\times 10! + ( \\\\frac{x}{y} )  ^{2} + {x \\\\choose y} + I _{n}( x)  + \\\\max ( a, b)  + H _{n}( x) $\"\n);\n*/\n\n/* this fails because of a bug that Differentiate(x) f(y) does not go to 0 */ /*\nVerify(\nUnparseLatex(3*Differentiate(x)f(x,y,z)*Cos(Omega)*Modulo(Sin(a)*4,5/a^b))\n,\"$3 ( \\\\frac{\\\\partial}{\\\\partial x}f( x, y, z) )  ( \\\\cos \\\\Omega )  ( 4 ( \\\\sin a) ) \\\\bmod \\\\frac{5}{a ^{b}} $\"\n);\n*/\n\n\nVerify(\nUnparseLatex(Hold(Not? (c <? 0) And? (a+b)*c>=? -d^e And? (c <=? 0 Or? b+1 >? 0) Or? a !=? 0 And? Not? (p =? q)))\n,\"$ \\\\neg c < 0\\\\wedge ( a + b)  \\\\times c\\\\geq  - d ^{e}\\\\wedge ( c\\\\leq 0\\\\vee b + 1 > 0) \\\\vee a\\\\neq 0\\\\wedge  \\\\neg p = q$\"\n);\n\n\n/* todo:tk:commenting out for the minimal version of the scripts.\nRulebaseHoldArguments(\"f\",[x]);\nVerify(\nUnparseLatex(Hold(Differentiate(x)f(x)))\n,\"$\\\\frac{d}{d x}f( x) $\");\nRetract(\"f\",1);\n\n\nRulebaseHoldArguments(\"f\",[x,y,z]);\nVerify(\nUnparseLatex((Differentiate(x)f(x,y,z))*Cos(Omega)*Modulo(Sin(a)*4,5/a^b))\n,\"$( \\\\frac{\\\\partial}{\\\\partial x}f( x, y, z) )  \\\\times \\\\cos \\\\Omega  \\\\times ( 4 \\\\times \\\\sin a) \\\\bmod \\\\frac{5}{a ^{b}} $\"\n);\nRetract(\"f\",3);\n\nRulebaseHoldArguments(\"g\",[x]);\nRulebaseHoldArguments(\"theta\",[x]);\nVerify(\nUnparseLatex(Pi+Exp(1)-Theta-Integrate(x,x1,3/g(Pi))2*theta(x)*Exp(1/x))\n,\"$\\\\pi  + \\\\exp ( 1)  - \\\\Theta  - \\\\int _{x_{1}} ^{\\\\frac{3}{g( \\\\pi ) }  } 2 \\\\times \\\\theta ( x)  \\\\times \\\\exp ( \\\\frac{1}{x} )  dx$\"\n);\nRetract(\"g\",1);\nRetract(\"theta\",1);\n*/\n\n\nVerify(\nUnparseLatex([a[3]*b[5]-c[1][2],[a,b,c,d]])\n,\"$( a _{3} \\\\times b _{5} - c _{( 1, 2) }, ( a, b, c, d) ) $\"\n);\n\n\n//Note: this is the only code in the test suite that currently creates new rulebases.\nRulebaseHoldArguments(\"aa\",[x,y,z]);\nBodied(\"aa\", 200);\nRulebaseHoldArguments(\"bar\", [x,y]);\nInfix(\"bar\", 100);\nVerify(\nUnparseLatex(aa(x,y) z + 1 bar y!)\n,\"$aa( x, y) z + 1\\\\mathrm{ bar }y!$\"\n);\nRetract(\"aa\",3);\nRetract(\"bar\",2);\n\nVerify(\nUnparseLatex(x^(1/3)+x^(1/2))\n, \"$\\\\sqrt[3]{x} + \\\\sqrt{x}$\"\n);\n\n/*\nVerify(\nUnparseLatex()\n,\"\"\n);\n*/\n\n/* Bug report from Michael Borcherds. The brackets were missing. */\nVerify(UnparseLatex(Hold(2*x*(-2))), \"$2 \\\\times x \\\\times (  - 2) $\");\n\n\n";
        testString[2] = "\\org\\mathpiper\\scripts4\\unparsers\\unparselatex.mpw";
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
}
