package org.mathpiper;

import java.util.HashMap;

import java.util.Map;

public class Scripts {

    private HashMap scriptMap = null;

    public Scripts() {

        scriptMap = new HashMap();

        String[] scriptString;


        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(MathPiperInitLoad,[]);\nRuleHoldArguments(MathPiperInitLoad, 0, 1, True)\n{\n    /* This is the basic initialization file for MathPiper. It gets loaded\n     * each time MathPiper is started. All the basic files are loaded.\n     */\n\n\n    StandardOperatorsLoad();\n\n    Constant('True);\n    \n    Constant('False);\n    \n    Constant('Empty);\n    \n    Constant('Infinity);\n    \n    Constant('Undefined);\n    \n    Constant('List);\n    \n    Constant('Block);\n\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/mathpiperinit/mathpiperinit.mpw, (MathPiperInitLoad)";
        scriptMap.put("MathPiperInitLoad",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Implementation of Nth that allows extending. */\nRulebaseHoldArguments(\"Nth\",[_alist,_aindex]);\nRuleHoldArguments(\"Nth\",2,10,\n    And?(Equal?(Function?(alist),True),\n            Equal?(Integer?(aindex),True),\n            Not?(Equal?(First(FunctionToList(alist)),'Nth))\n            ))\n     MathNth(alist,aindex);\n\n\n\n\nRuleHoldArguments(\"Nth\",2,14,\n     And?(Equal?(String?(alist),True),List?(aindex))\n    )\n{\n  Local(result);\n  result:=\"\";\n  ForEach(i,aindex) { result := result ~ StringMidGet(i,1,alist); };\n  result;\n};\n\nRuleHoldArguments(\"Nth\",2,15,Equal?(String?(alist),True))\n{\n  StringMidGet(aindex,1,alist);\n};\n\n\nRuleHoldArguments(\"Nth\",2,20,Equal?(List?(aindex),True))\n{\n  Map([[ii],alist[ii]],[aindex]);\n};\n\nRuleHoldArguments(\"Nth\",2,30,\n   And?(\n           Equal?(Generic?(alist),True),\n           Equal?(GenericTypeName(alist),\"Array\"),\n           Equal?(Integer?(aindex),True)\n          )\n    )\n{\n  ArrayGet(alist,aindex);\n};\n\n\n\nRuleHoldArguments(\"Nth\",2,40,Equal?(String?(aindex),True))\n{\n  Local(as);\n  as := Assoc(aindex,alist);\n  Decide(Not?(Equal?(as,Empty)),Assign(as,Nth(as,2)));\n  as;\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/standard/A_Nth.mpw, (Nth)";
        scriptMap.put("Nth",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"ArgumentsCount\",[_aLeft]) Length(FunctionToList(aLeft))-1;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/standard/ArgumentsCount.mpw, (ArgumentsCount)";
        scriptMap.put("ArgumentsCount",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"Denominator\",*);\n\n1 # Denominator(_x / _y)      <-- y;\n1 # Denominator(-_x/ _y)      <-- y;\n2 # Denominator(x_Number?)   <-- 1;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/standard/Denominator.mpw, (Denominator)";
        scriptMap.put("Denominator",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # NonObject?(Object(_x)) <-- False;\n20 # NonObject?(_x)         <-- True;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/standard/NonObject_.mpw, (NonObject?)";
        scriptMap.put("NonObject?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"Numerator\",*);\n\n1 # Numerator(_x / _y)      <-- x;\n1 # Numerator(-_x/ _y)      <-- -x;\n2 # Numerator(x_Number?)   <-- x;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/standard/Numerator.mpw, (Numerator)";
        scriptMap.put("Numerator",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\na_NonNegativeInteger? & b_NonNegativeInteger? <-- BitAnd(a,b);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/standard/ampersand_operator.mpw, (&)";
        scriptMap.put("&",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(\"==\",[_left,_right]);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/standard/equals_equals_operator.mpw, (==)";
        scriptMap.put("==",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(\"!==\",[_left,_right]);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/standard/exclamationpoint_equals_equals_operator.mpw, (!==)";
        scriptMap.put("!==",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"--\",[_aVar])\n{\n   MacroAssign(aVar,SubtractN(Eval(aVar),1));\n\n   Eval(aVar);\n};\n\nUnFence(\"--\",1);\n\nHoldArgument(\"--\",\"aVar\");\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/standard/minus_minus_operator.mpw, (--)";
        scriptMap.put("--",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//\"+-;/-;*-;^-;:=-;:=+\" These were in the def list.\n\n/* See the documentation on the assignment of the precedence of the rules.\n */\n\n/* Some very basic functions that are used always any way... */\n\n/* Implementation of numeric mode */\n\n//Retract(\"NM\",*);\n\nLocalSymbols(numericMode)\n{\n\n  numericMode := False;\n\n  // NM function: evaluate numerically with given precision.\n  LocalSymbols(previousNumericMode, previousPrecision, numericResult) \n  Macro(\"NM\",[_expression, _precision])\n  {\n    //Decide(InVerboseMode(),\n    //    [Tell(\"NM\",[expression,precision]); Tell(\"  \",[@expression,@precision]);]\n    //);\n    \n    // we were in non-numeric mode\n    Local(previousNumericMode, previousPrecision, numericResult, exception);\n\n    previousPrecision := BuiltinPrecisionGet();\n    //Decide(InVerboseMode(),Tell(\"  \",previousPrecision));\n    BuiltinPrecisionSet(@precision+5);\n\n    AssignCachedConstantsN();\n\n    previousNumericMode := numericMode;\n    numericMode         := True;\n    exception         := False;\n\n    //ExceptionCatch(Assign(numericResult, Eval(@expression)),Assign(exception,ExceptionGet()));\n\n    ExceptionCatch( numericResult:=Eval(@expression), exception := ExceptionGet() );\n    \n    //SysOut(numericResult,\" \",@expression,\" \",Eval(@expression));\n    //Decide(InVerboseMode(),Tell(\"  1\",numericResult));\n\n    Decide(Assigned?(numericResult) And? Decimal?(numericResult), numericResult := RoundToN(numericResult, @precision));\n    //Decide(InVerboseMode(),Tell(\"  2\",numericResult));\n\n    numericMode := previousNumericMode;\n\n    Decide(Not? numericMode, { ClearCachedConstantsN(); } );\n\n    BuiltinPrecisionSet(previousPrecision);\n\n    Check(exception =? False, exception[\"type\"], exception[\"message\"]);\n\n    numericResult;\n\n  };\n\n\n\n\n  // NM function: evaluate numerically with default precision.\n  LocalSymbols(precision,heldExpression) \n  Macro(\"NM\",[_expression])\n  {\n     Local(precision, heldExpression);\n     precision      :=  BuiltinPrecisionGet();\n     heldExpression :=  Hold(@expression);\n\n     `NM(@heldExpression, @precision);\n  };\n\n  \n  // NonNM function.\n  LocalSymbols(result) \n  Macro(\"NonNM\",[_expression])\n  {\n    Local(result);\n    GlobalPush(numericMode);\n    numericMode := False;\n    result      := (@expression);\n    numericMode := GlobalPop();\n    result;\n  };\n\n\n  // NumericMode? function.\n  Function(\"NumericMode?\",[]) numericMode;\n\n}; //LocalSymbols(numericMode)\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/standard/numeric.mpw, (NM;NonNM;NumericMode?)";
        scriptMap.put("NM",scriptString);
        scriptMap.put("NonNM",scriptString);
        scriptMap.put("NumericMode?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\na_NonNegativeInteger? % b_PositiveInteger? <-- Modulo(a,b);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/standard/percent_operator.mpw, (%)";
        scriptMap.put("%",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"++\",[_aVar])\n{\n   MacroAssign(aVar,AddN(Eval(aVar),1));\n\n   Eval(aVar);\n};\n\nUnFence(\"++\",1);\n\nHoldArgument(\"++\",\"aVar\");\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/standard/plus_plus_operator.mpw, (++)";
        scriptMap.put("++",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n    RulebaseHoldArguments(\"'\", [_expression]);\n\n    RuleHoldArguments(\"'\", 1, 100, True) `(Hold(@expression));\n\n    HoldArgument(\"'\",\"expression\");\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/standard/single_quote_operator.mpw, (')";
        scriptMap.put("'",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n    RulebaseHoldArguments(\"''\", [_expression]);\n\n    RuleHoldArguments(\"''\", 1, 100, True) `(Hold(@expression));\n\n    HoldArgument(\"''\",\"expression\");\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/standard/single_quote_single_quote_operator.mpw, ('')";
        scriptMap.put("''",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\na_NonNegativeInteger? | b_NonNegativeInteger? <-- BitOr(a,b);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/standard/vertical_bar_operator.mpw, (|)";
        scriptMap.put("|",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Multiplication */\n\n50  # x_Number? * y_Number? <-- MultiplyN(x,y);\n100 #  1  * _x  <-- x;\n100 # _x  *  1  <-- x;\n100 # (_f  * _x)::(f=? -1)  <-- -x;\n100 # (_x  * _f)::(f=? -1)  <-- -x;\n\n100  # ((_x)*(_x ^ _m)) <-- x^(m+1);\n100  # ((_x ^ _m)*(_x)) <-- x^(m+1);\n100  # ((_x ^ _n)*(_x ^ _m)) <-- x^(m+n);\n\n100 # Tan(_x)*Cos(_x) <-- Sin(x);\n\n100 # Sinh(_x)*Csch(_x) <-- 1;\n\n100 # Cosh(_x)*Sech(_x) <-- 1;\n\n100 # Cos(_x)*Tan(_x) <-- Sin(x);\n\n100 # Coth(_x)*Sinh(_x) <-- Cosh(x);\n\n100 # Tanh(_x)*Cosh(_x) <-- Sinh(x);\n\n105 # (f_NegativeNumber? * _x)  <-- -(-f)*x;\n105 # (_x * f_NegativeNumber?)  <-- -(-f)*x;\n\n95 # x_Matrix? * y_Matrix? <--\n{\n   Local(i,j,k,row,result);\n   result:=ZeroMatrix(Length(x),Length(y[1]));\n   For(i:=1,i<=?Length(x),i++)\n   For(j:=1,j<=?Length(y),j++)\n   For(k:=1,k<=?Length(y[1]),k++)\n   {\n     row:=result[i];\n     row[k]:= row[k]+x[i][j]*y[j][k];\n   };\n   result;\n};\n\n\n96 # x_Matrix? * y_List? <--\n{\n   Local(i,result);\n   result:=[];\n   For(i:=1,i<=?Length(x),i++)\n   { DestructiveInsert(result,i,Dot(x[i], y)); };\n   result;\n};\n\n\n97 # (x_List? * y_NonObject?)::Not?(List?(y)) <-- y*x;\n98 # (x_NonObject? * y_List?)::Not?(List?(x)) <--\n{\n   Local(i,result);\n   result:=[];\n   For(i:=1,i<=?Length(y),i++)\n   { DestructiveInsert(result,i,x * y[i]); };\n   result;\n};\n\n\n50  # _x * Undefined <-- Undefined;\n50  # Undefined * _y <-- Undefined;\n\n\n100  # 0 * y_Infinity? <-- Undefined;\n100  # x_Infinity? * 0 <-- Undefined;\n\n101 # 0    * (_x) <-- 0;\n101 # (_x) *    0 <-- 0;\n\n100 # x_Number? * (y_Number? * _z) <-- (x*y)*z;\n100 # x_Number? * (_y * z_Number?) <-- (x*z)*y;\n\n100 # (_x * _y) * _y <-- x * y^2;\n100 # (_x * _y) * _x <-- y * x^2;\n100 # _y * (_x * _y) <-- x * y^2;\n100 # _x * (_x * _y) <-- y * x^2;\n100 # _x * (_y / _z) <-- (x*y)/z;\n// fractions\n100 # (_y / _z) * _x <-- (x*y)/z;\n100 # (_x * y_Number?)::Not?(Number?(x)) <-- y*x;\n\n100 # (_x) * (_x) ^ (n_Constant?) <-- x^(n+1);\n100 # (_x) ^ (n_Constant?) * (_x) <-- x^(n+1);\n100 # (_x * _y)* _x ^ n_Constant? <-- y * x^(n+1);\n100 # (_y * _x)* _x ^ n_Constant? <-- y * x^(n+1);\n100 # Sqrt(_x) * (_x) ^ (n_Constant?) <-- x^(n+1/2);\n100 # (_x) ^ (n_Constant?) * Sqrt(_x) <-- x^(n+1/2);\n100 # Sqrt(_x) * (_x) <-- x^(3/2);\n100 # (_x) * Sqrt(_x) <-- x^(3/2);\n\n105 # x_Number? * -(_y) <-- (-x)*y;\n105 # (-(_x)) * (y_Number?) <-- (-y)*x;\n\n106 # _x * -(_y) <-- -(x*y);\n106 # (- _x) * _y <-- -(x*y);\n\n150 # (_x) ^ (n_Constant?) * (_x) <-- x^(n-1);\n\n250  # x_Number? * y_Infinity? <-- Sign(x)*y;\n250  # x_Infinity? * y_Number? <-- Sign(y)*x;\n\n\n/* Note: this rule MUST be past all the transformations on\n * matrices, since they are lists also.\n */\n230 # (aLeft_List? * aRight_List?)::(Length(aLeft)=?Length(aRight)) <--\n         Map(\"*\",[aLeft,aRight]);\n// fractions\n242 # (x_Integer? / y_Integer?) * (v_Integer? / w_Integer?) <-- (x*v)/(y*w);\n243 #  x_Integer? * (y_Integer? / z_Integer?) <--  (x*y)/z;\n243 #  (y_Integer? / z_Integer?) * x_Integer? <--  (x*y)/z;\n\n400 # (_x) * (_x) <-- x^2;\n\n400 # x_RationalOrNumber? * Sqrt(y_RationalOrNumber?)  <-- Sign(x)*Sqrt(x^2*y);\n400 # Sqrt(y_RationalOrNumber?) * x_RationalOrNumber?  <-- Sign(x)*Sqrt(x^2*y);\n400 # Sqrt(y_RationalOrNumber?) * Sqrt(x_RationalOrNumber?)  <-- Sqrt(y*x);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/stdarith/asterisk_operator.mpw, (*)";
        scriptMap.put("*",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Faster version of raising power to 0.5 */\n50 # _x ^ (1/2) <-- Sqrt(x);\n50 # (x_PositiveNumber? ^ (1/2))::Integer?(SqrtN(x)) <-- SqrtN(x);\n58 # 1 ^ n_Infinity? <-- Undefined;\n59 # _x ^ 1 <-- x;\n59 # 1 ^ _n <-- 1;\n59 # x_Zero? ^ y_Zero? <-- Undefined;\n60 # (x_Zero? ^ n_RationalOrNumber?)::(n>?0) <-- 0;\n60 # (x_Zero? ^ n_RationalOrNumber?)::(n<?0) <-- Infinity;\n// This is to fix:\n// In> 0.0000^2\n// Result: 0.0000^2;\n// In> 0.0^2/2\n// Result: 0.0^2/2;\n//60 # (x_Number? ^ n_RationalOrNumber?)::(x+1=1) <-- 0;\n\n59 # _x ^ Undefined <-- Undefined;\n59 # Undefined ^ _x <-- Undefined;\n\n/* Regular raising to the power. */\n61 # Infinity ^ (y_NegativeNumber?) <-- 0;\n61 # (-Infinity) ^ (y_NegativeNumber?) <-- 0;\n//61 # x_PositiveNumber? ^ y_PositiveNumber? <-- PowerN(x,y);\n//61 # x_PositiveNumber? ^ y_NegativeNumber? <-- (1/PowerN(x,-y));\n// integer powers are very fast\n61 # x_PositiveNumber? ^ y_PositiveInteger? <-- MathIntPower(x,y);\n61 # x_PositiveNumber? ^ y_NegativeInteger? <-- 1/MathIntPower(x,-y);\n65 # (x_PositiveNumber? ^ y_Number?)::NumericMode?() <-- Exp(y*Ln(x));\n\n90 # (-_x)^m_Even? <-- x^m;\n91 # (x_Constant? ^ (m_Odd? / p_Odd?))::(NegativeNumber?(Re(NM(Eval(x))))) <--\n     -((-x)^(m/p));\n92 # (x_NegativeNumber? ^ y_Number?)::NumericMode?() <-- Exp(y*Ln(x));\n\n\n70  # (_x ^ m_RationalOrNumber?) ^ n_RationalOrNumber? <-- x^(n*m);\n\n80 # (x_Number?/y_Number?) ^ n_PositiveInteger? <-- x^n/y^n;\n80 # (x_Number?/y_Number?) ^ n_NegativeInteger? <-- y^(-n)/x^(-n);\n80 # x_NegativeNumber? ^ n_Even? <-- (-x)^n;\n80 # x_NegativeNumber? ^ n_Odd? <-- -((-x)^n);\n\n\n100  # ((x_Number?)^(n_Integer?/(_m)))::(n>?1) <-- MathIntPower(x,n)^(1/m);\n\n100 # Sqrt(_n)^(m_Even?) <-- n^(m/2);\n\n100 # Abs(_a)^n_Even? <-- a^n;\n100 # Abs(_a)^n_Odd? <-- Sign(a)*a^n;\n\n\n200 # x_Matrix? ^ n_PositiveInteger? <-- x*(x^(n-1));\n204 # (xlist_List? ^ nlist_List?)::(Length(xlist)=?Length(nlist)) <--\n         Map(\"^\",[xlist,nlist]);\n205 # (xlist_List? ^ n_Constant?)::(Not?(List?(n))) <--\n         Map('[[xx],xx^n],[xlist]);\n206 # (_x ^ n_List?)::(Not?(List?(x))) <-- Map('[[xx],x^xx],[n]);\n249 # x_Infinity? ^ 0 <-- Undefined;\n250 # Infinity ^ (_n) <-- Infinity;\n250 # Infinity ^ (_x_Complex?) <-- Infinity;\n250 # ((-Infinity) ^ (n_Number?))::(Even?(n)) <-- Infinity;\n250 # ((-Infinity) ^ (n_Number?))::(Odd?(n)) <-- -Infinity;\n\n250 # (x_Number? ^ Infinity)::(x>? -1 And? x <? 1) <-- 0;\n250 # (x_Number? ^ Infinity)::(x>? 1) <-- Infinity;\n\n// these Magnitude(x)s should probably be changed to Abs(x)s\n\n250 # (x_Complex? ^ Infinity)::(Magnitude(x) >? 1) <-- Infinity;\n250 # (x_Complex? ^ Infinity)::(Magnitude(x) <? -1) <-- -Infinity;\n250 # (x_Complex? ^ Infinity)::(Magnitude(x) >? -1 And? Magnitude(x) <? 1) <-- 0;\n\n250 # (x_Number? ^ -Infinity)::(x>? -1 And? x <? 1) <-- Infinity;\n250 # (x_Number? ^ -Infinity)::(x<? -1) <-- 0;\n250 # (x_Number? ^ -Infinity)::(x>? 1) <-- 0;\n\n255 # (x_Complex? ^ Infinity)::(Abs(x) =? 1) <-- Undefined;\n255 # (x_Complex? ^ -Infinity)::(Abs(x) =? 1) <-- Undefined;\n\n\n\n400 # _x ^ 0 <-- 1;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/stdarith/caret_operator.mpw, (^)";
        scriptMap.put("^",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Subtraction arity 1 */\n\n//50 # -0 <-- 0;\n51 # -Undefined <-- Undefined;\n54 # - (- _x)      <-- x;\n55 # (- (x_Number?)) <-- SubtractN(0,x);\n100 # _x - n_Constant?*(_x)   <-- (1-n)*x;\n100 # n_Constant?*(_x) - _x   <-- (n-1)*x;\n\n100 # Sinh(_x)^2-Cosh(_x)^2         <-- 1;\n100 # Sinh(_x)-Cosh(_x)                <-- Exp(-x);\n\n110 # - (_x - _y) <-- y-x;\n111 # - (x_Number? / _y) <-- (-x)/y;        \nLocalSymbols(x)\n{\n  200 # - (x_List?) <-- MapSingle(\"-\",x);\n};\n\n/* Subtraction arity 2 */\n50  # x_Number? - y_Number? <-- SubtractN(x,y);\n50  # x_Number? - y_Number? <-- SubtractN(x,y);\n60  # Infinity - Infinity <-- Undefined;\n100 # 0 - _x <-- -x;\n100 # _x - 0 <-- x;\n100 # _x - _x <-- 0;\n\n107 # -( (-(_x))/(_y)) <-- x/y;\n107 # -( (_x)/(-(_y))) <-- x/y;\n\n110 # _x - (- _y) <-- x + y;\n110 # _x - (y_NegativeNumber?) <-- x + (-y);\n111 # (_x + _y)- _x <-- y;\n111 # (_x + _y)- _y <-- x;\n112 # _x - (_x + _y) <-- - y;\n112 # _y - (_x + _y) <-- - x;\n113 # (- _x) - _y <-- -(x+y);\n113 # (x_NegativeNumber?) - _y <-- -((-x)+y);\n113 # (x_NegativeNumber?)/_y - _z <-- -((-x)/y+z);\n\n\n/* TODO move to this precedence everywhere? */\nLocalSymbols(x,y,xarg,yarg)\n{\n  10 # ((x_List?) - (y_List?))::(Length(x)=?Length(y)) <--\n  {\n    Map('[[xarg,yarg],xarg-yarg],[x,y]);\n  };\n};\n\n240 # (x_List? - y_NonObject?)::Not?(List?(y)) <-- -(y-x);\n\n241 # (x_NonObject? - y_List?)::Not?(List?(x)) <--\n{\n   Local(i,result);\n   result:=[];\n   For(i:=1,i<=?Length(y),i++)\n   { DestructiveInsert(result,i,x - y[i]); };\n   result;\n};\n\n250 # z_Infinity? - Complex(_x,_y) <-- Complex(-x+z,-y);\n250 # Complex(_x,_y) - z_Infinity? <-- Complex(x-z,y);\n\n251 # z_Infinity? - _x <-- z;\n251 # _x - z_Infinity? <-- -z;\n\n250 # Undefined - _y <-- Undefined;\n250 # _x - Undefined <-- Undefined;\n// fractions\n210 # x_Number? - (y_Number? / z_Number?) <--(x*z-y)/z;\n210 # (y_Number? / z_Number?) - x_Number? <--(y-x*z)/z;\n210 # (x_Number? / v_Number?) - (y_Number? / z_Number?) <--(x*z-y*v)/(v*z);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/stdarith/minus_operator.mpw, (-)";
        scriptMap.put("-",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Addition */\n\n100 # + _x  <-- x;\n\n50 # x_Number? + y_Number? <-- AddN(x,y);\n\n100 # 0 + _x    <-- x;\n100 # _x + 0    <-- x;\n100 # _x + _x   <-- 2*x;\n100 # _x + n_Constant?*(_x)   <-- (n+1)*x;\n100 # n_Constant?*(_x) + _x   <-- (n+1)*x;\n100 # Sinh(_x)+Cosh(_x)                <-- Exp(x);\n\n101 # _x + - _y <-- x-y;\n101 # _x + (- _y)/(_z) <-- x-(y/z);\n101 # (- _y)/(_z) + _x  <-- x-(y/z);\n101 # (- _x) + _y <-- y-x;\n102 # _x + y_NegativeNumber? <-- x-(-y);\n102 # _x + y_NegativeNumber? * _z <-- x-((-y)*z);\n102 # _x + (y_NegativeNumber?)/(_z) <-- x-((-y)/z);\n102 # (y_NegativeNumber?)/(_z) + _x  <-- x-((-y)/z);\n102 # (x_NegativeNumber?) + _y <-- y-(-x);\n// fractions\n150 # _n1 / _d + _n2 / _d <-- (n1+n2)/d;\n\n200 # (x_Number? + _y)::Not?(Number?(y)) <-- y+x;\n200 # ((_y + x_Number?) + _z)::Not?(Number?(y) Or? Number?(z)) <-- (y+z)+x;\n200 # ((x_Number? + _y) + z_Number?)::Not?(Number?(y)) <-- y+(x+z);\n200 # ((_x + y_Number?) + z_Number?)::Not?(Number?(x)) <-- x+(y+z);\n// fractions\n210 # x_Number? + (y_Number? / z_Number?) <--(x*z+y)/z;\n210 # (y_Number? / z_Number?) + x_Number? <--(x*z+y)/z;\n210 # (x_Number? / v_Number?) + (y_Number? / z_Number?) <--(x*z+y*v)/(v*z);\n\n\n//  220 # + x_List?          <-- MapSingle(\"+\",x);        // this rule is never active\n\n220 # (xlist_List? + ylist_List?)::(Length(xlist)=?Length(ylist)) <-- Map(\"+\",[xlist,ylist]);\n\nSumListSide(_x, y_List?) <--\n{\n   Local(i,result);\n   result:=[];\n   For(i:=1,i<=?Length(y),i++)\n   { DestructiveInsert(result,i,x + y[i]); };\n   result;\n};\n\n240 # (x_List? + _y)::Not?(List?(y)) <-- SumListSide(y,x);\n241 # (_x + y_List?)::Not?(List?(x)) <-- SumListSide(x,y);\n\n250 # z_Infinity? + Complex(_x,_y) <-- Complex(x+z,y);\n250 # Complex(_x,_y) + z_Infinity? <-- Complex(x+z,y);\n\n251 # z_Infinity? + _x <-- z;\n251 # _x + z_Infinity? <-- z;\n\n\n250 # Undefined + _y <-- Undefined;\n250 # _x + Undefined <-- Undefined;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/stdarith/plus_operator.mpw, (+;SumListSide)";
        scriptMap.put("+",scriptString);
        scriptMap.put("SumListSide",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Division */\n\n50 # 0 / 0 <-- Undefined;\n\n52 # x_PositiveNumber? / 0 <-- Infinity;\n52 # x_NegativeNumber? / 0 <-- -Infinity;\n55 # (_x / y_Number?)::(Zero?(y)) <-- Undefined;\n55 # 0 / _x <-- 0;\n// unnecessary rule (see #100 below). TODO: REMOVE\n//55 # x_Number? / y_NegativeNumber? <-- (-x)/(-y);\n\n56 # (x_NonZeroInteger? / y_NonZeroInteger?)::(GcdN(x,y) >? 1) <--\n     {\n       Local(gcd);\n       Assign(x,x);\n       Assign(y,y);\n       Assign(gcd,GcdN(x,y));\n       QuotientN(x,gcd)/QuotientN(y,gcd);\n     };\n\n57 # ((x_NonZeroInteger? * _expr) / y_NonZeroInteger?)::(GcdN(x,y) >? 1) <--\n     {\n       Local(gcd);\n       Assign(x,x);\n       Assign(y,y);\n       Assign(gcd,GcdN(x,y));\n       (QuotientN(x,gcd)*expr)/QuotientN(y,gcd);\n     };\n\n57 # ((x_NonZeroInteger?) / (y_NonZeroInteger? * _expr))::(GcdN(x,y) >? 1) <--\n     {\n       Local(gcd);\n       Assign(x,x);\n       Assign(y,y);\n       Assign(gcd,GcdN(x,y));\n       QuotientN(x,gcd)/(QuotientN(y,gcd)*expr);\n     };\n\n57 # ((x_NonZeroInteger? * _p) / (y_NonZeroInteger? * _q))::(GcdN(x,y) >? 1) <--\n     {\n       Local(gcd);\n       Assign(x,x);\n       Assign(y,y);\n       Assign(gcd,GcdN(x,y));\n       (QuotientN(x,gcd)*p)/(QuotientN(y,gcd)*q);\n     };\n\n60 # (x_Decimal? / y_Number?)  <-- DivideN(x,y);\n60 # (x_Number?  / y_Decimal?) <-- DivideN(x,y); \n60 # (x_Number?  / y_Number?)::(NumericMode?()) <-- DivideN(x,y);\n\n\n90 # x_Infinity? / y_Infinity? <-- Undefined;\n95  # x_Infinity? / y_Number? <-- Sign(y)*x;\n95  # x_Infinity? / y_Complex? <-- Infinity;\n\n90 # Undefined / _y <-- Undefined;\n90 # _y / Undefined <-- Undefined;\n\n\n100 # _x / _x <-- 1;\n100 # _x /  1 <-- x;\n100 # (_x / y_NegativeNumber?) <-- -x/(-y);\n100 # (_x / - _y) <-- -x/y;\n\n100 # Tan(_x)/Sin(_x) <-- (1/Cos(x));\n\n100 # 1/Csch(_x)        <-- Sinh(x);\n\n100 # Sin(_x)/Tan(_x) <-- Cos(x);\n\n100 # Sin(_x)/Cos(_x) <-- Tan(x);\n\n100 # 1/Sech(_x) <-- Cosh(x);\n\n100 # 1/Sec(_x)                <-- Cos(x);\n\n100 # 1/Csc(_x)                <-- Sin(x);\n\n100 # Cos(_x)/Sin(_x) <-- (1/Tan(x));\n\n100 # 1/Cot(_x) <-- Tan(x);\n\n100 # 1/Coth(_x)        <-- Tanh(x);\n\n100 # Sinh(_x)/Cosh(_x) <-- Tanh(x);\n\n150 # (_x) / (_x) ^ (n_Constant?) <-- x^(1-n);\n150 # Sqrt(_x) / (_x) ^ (n_Constant?) <-- x^(1/2-n);\n150 # (_x) ^ (n_Constant?) / Sqrt(_x) <-- x^(n-1/2);\n150 # (_x) / Sqrt(_x) <-- Sqrt(x);\n\n// fractions\n200 # (_x / _y)/ _z <-- x/(y*z);\n230 # _x / (_y / _z) <-- (x*z)/y;\n\n240 # (xlist_List? / ylist_List?)::(Length(xlist)=?Length(ylist)) <--\n         Map(\"/\",[xlist,ylist]);\n\n\n250 # (x_List? / _y)::(Not?(List?(y))) <--\n{\n   Local(i,result);\n   result:=[];\n   For(i:=1,i<=?Length(x),i++)\n   { DestructiveInsert(result,i,x[i] / y); };\n   result;\n};\n\n250 # (_x / y_List?)::(Not?(List?(x))) <--\n{\n   Local(i,result);\n   result:=[];\n   For(i:=1,i<=?Length(y),i++)\n   { DestructiveInsert(result,i,x/y[i]); };\n   result;\n};\n\n250 # _x / Infinity <-- 0;\n250 # _x / (-Infinity) <-- 0;\n\n\n400 # 0 / _x <-- 0;\n\n400 # x_RationalOrNumber? / Sqrt(y_RationalOrNumber?)  <-- Sign(x)*Sqrt(x^2/y);\n400 # Sqrt(y_RationalOrNumber?) / x_RationalOrNumber?  <-- Sign(x)*Sqrt(y/(x^2));\n400 # Sqrt(y_RationalOrNumber?) / Sqrt(x_RationalOrNumber?)  <-- Sqrt(y/x);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/stdarith/slash_operator.mpw, (/)";
        scriptMap.put("/",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\n\nRulebaseHoldArguments(StandardOperatorsLoad,[]);\nRuleHoldArguments(StandardOperatorsLoad, 0, 1, True)\n{\n    /* stdopers is loaded immediately after MathPiper is started. It contains\n     * the definitions of the infix operators, so the parser can already\n     * parse expressions containing these operators, even though the\n     * function hasn't been defined yet.\n     */\n\n    Infix(\"=?\",90);\n    Infix(\"And?\",1000);\n    RightAssociativeSet(\"And?\");\n    Infix(\"Or?\", 1010);\n    Prefix(\"Not?\", 100);\n    Infix(\"<?\",90);\n    Infix(\">?\",90);\n    Infix(\"<=?\",90);\n    Infix(\">=?\",90);\n    Infix(\"!=?\",90);\n\n    Infix(\":=\",22000);\n    RightAssociativeSet(\":=\");\n    \n    RulebaseHoldArguments(\":\",[_left,_right]);\n    HoldArgument(\":\",\"left\");\n    Infix(\":\",0);\n    RightAssociativeSet(\":\");\n    \n    RulebaseHoldArguments(\"->\",[_left,_right]);\n    HoldArgument(\"->\",\"left\");\n    Infix(\"->\",950);\n\n    Infix(\"+\",70);\n    Infix(\"-\",70);\n    RightPrecedenceSet(\"-\",40);\n    Infix(\"/\",30);\n    Infix(\"*\",40);\n    Infix(\"^\",20);\n    LeftPrecedenceSet(\"^\",19); //Added to make expressions like x^n^2 unambiguous.\n    RightAssociativeSet(\"^\");\n    Prefix(\"+\",50);\n    Prefix(\"-\",50);\n    RightPrecedenceSet(\"-\",40);\n    Bodied(\"For\",60000);\n    Bodied(\"Until\",60000);\n    Postfix(\"++\",5);\n    Postfix(\"--\",5);\n    Bodied(\"ForEach\",60000);\n    Infix(\"<<\",10);\n    Infix(\">>\",10);\n    Bodied(\"Differentiate\",60000);\n    Bodied(\"Deriv\",60000);\n    Infix(\"X\",30);\n    Infix(\".\",30);\n    Infix(\"o\",30);\n    Postfix(\"!\", 30);\n    Postfix(\"!!\", 30);\n    Infix(\"***\", 50);\n    Bodied(\"Integrate\",60000);\n\n    Bodied(\"Limit\",60000);\n\n    Bodied(\"EchoTime\", 60000);\n\n    Bodied(\"Repeat\", 60000);\n\n\n\n    /* functional operators */\n    Infix(\"~\",70);\n    RightAssociativeSet(\"~\");\n    Infix(\"@\",600);\n    Infix(\"/@\",600);\n    Infix(\"..\",600);\n\n    Bodied(\"Taylor\",60000);\n    Bodied(\"Taylor1\",60000);\n    Bodied(\"Taylor2\",60000);\n    Bodied(\"Taylor3\",60000);\n    Bodied(\"InverseTaylor\",60000);\n\n    Infix(\"<--\",22000);\n    Infix(\"#\",21900);\n\n    Bodied(\"TSum\",60000);\n    Bodied(\"TExplicitSum\",60000);\n    Bodied(\"TD\",5);  /* Tell the MathPiper interpreter that TD is to be used as TD(i)f */\n\n    /* Operator to be used for non-evaluating comparisons */\n    Infix(\"==\",90);\n    Infix(\"!==\",90);\n\n    /* Operators needed for propositional logic theorem prover */\n    Infix(\"Implies?\",10000); /* implication. */\n    Infix(\"Equivales?\",10000); /* equivalence. */\n\n\n    Bodied(\"If\",5);\n    Infix(\"Else\",60000);\n    RightAssociativeSet(\"Else\");\n    /* Bitwise operations we REALLY need. Perhaps we should define them\n       also as MathPiper operators?\n     */\n    Infix(\"&\",50);\n    Infix(\"|\",50);\n    Infix(\"%\",50);\n\n    /* local pattern replacement operators */\n    Infix(\"/:\",20000);\n    Infix(\"/::\",20000);\n    Infix(\"<-\",10000);\n\n    /* Operators used for manual layout */\n    Infix(\"<>\", PrecedenceGet(\"=?\"));\n    Infix(\"<=>\", PrecedenceGet(\"=?\"));\n\n    /* Operators for Solve: Where and AddTo */\n    Infix(\"Where\", 11000);\n    Infix(\"AddTo\", 2000);\n\n    Bodied(\"Function\",60000);\n    Bodied(\"Macro\",60000);\n\n    Bodied(\"Assert\", 60000);\n\n    // Defining very simple functions, in scripts that can be converted to plugin.\n    Bodied(\"Defun\",0);\n\n    Bodied(\"TemplateFunction\",60000);\n\n    Bodied(\"Taylor3TaylorCoefs\",0);\n\n    Infix(\":*:\",3);\n\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/a_initialization/stdopers/stdopers.mpw, (StandardOperatorsLoad)";
        scriptMap.put("StandardOperatorsLoad",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Assoc : given an assoc list like for example l:=[[a,2],[b,3]],\n   Assoc(b,l) will return [b,3]. if the key is not in the list,\n   it will return the atom Empty.\n*/\n\nFunction(\"Assoc\",[_key,_list])  BuiltinAssoc(key,list);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/assoc/Assoc.mpw, (Assoc)";
        scriptMap.put("Assoc",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// Delete an element of an associative list.\nLocalSymbols(hash, key, element, hashexpr)\n{\n\n/// AssocDelete(hash,[\"key\", value])\n10 # AssocDelete(hash_List?, element_List?) <--\n{\n        Local(index);\n        index := Find(hash, element);\n        Decide(\n                index >? 0,\n                DestructiveDelete(hash, index)\n        );\n        index>?0;        // return False if nothing found\n\n};\n\n\n/// AssocDelete(hash, \"key\")\n20 # AssocDelete(hash_List?, key_String?) <--\n{\n        AssocDelete(hash, BuiltinAssoc(key, hash));\n};\n\n30 # AssocDelete(hash_List?, Empty) <-- False;\n\n//HoldArgument(\"AssocDelete\", \"hash\");\n//UnFence(\"AssocDelete\", 1);\n//UnFence(\"AssocDelete\", 2);\n\n};        // LocalSymbols(hash, ...)\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/assoc/AssocDelete.mpw, (AssocDelete)";
        scriptMap.put("AssocDelete",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nAssocIndices(associndiceslist_List?) <--\n  DestructiveReverse(MapSingle(\"First\",associndiceslist));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/assoc/AssocIndices.mpw, (AssocIndices)";
        scriptMap.put("AssocIndices",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nAssocValues(assocvalueslist_List?) <--\n  DestructiveReverse(MapSingle(Lambda([x],First(Rest(x))),assocvalueslist));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/assoc/AssocValues.mpw, (AssocValues)";
        scriptMap.put("AssocValues",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nDefun(\"CosN\",[_x])Trigonometry(x,0.0,1.0,1.0);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/base/CosN.mpw, (CosN)";
        scriptMap.put("CosN",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "// jak: This seems to be less accurate than MathExpTaylor0 for numbers which are greater than 1.\n/// ExpN(x). Algorithm: for x<0, divide 1 by ExpN(-x); for x>1, compute ExpN(x/2)^2 recursively; for 0<x<1, use the Taylor series.\n// (This is not optimal; it would be much better to use SumTaylorNum and DoublingMinus1 from elemfuncs.mpi. But this should be debugged for now, since ExpN is important for many algorithms.)\n/// TODO FIXME: No precision tracking yet. (i.e. the correct number of digits is not always there in the answer)\n\nDefun(\"ExpN\", [_x])\n        Decide(Equal?(x,0),1,\n         Decide(LessThan?(x,0),DivideN(1, ExpN(MathNegate(x))),\n          Decide(GreaterThan?(x,1), MathExpDoubling(MathExpTaylor0(MathMul2Exp(x,MathNegate(MathBitCount(x)))), MathBitCount(x)), MathExpTaylor0(x)\n        )));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/base/ExpN.mpw, (ExpN)";
        scriptMap.put("ExpN",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// Identity transformation, compute Exp(x) from value=Exp(x/2^n) by squaring the value n times\nDefun(\"MathExpDoubling\", [_value, _n])\n{\n        Local(shift, result);\n        Assign(shift, n);\n        Assign(result, value);\n        While (GreaterThan?(shift,0))        // will lose 'shift' bits of precision here\n        {\n                Assign(result, MultiplyN(result, result));\n                Assign(shift, AddN(shift,MathNegate(1)));\n        };\n        result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/base/MathExpDoubling.mpw, (MathExpDoubling)";
        scriptMap.put("MathExpDoubling",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n// simple Taylor expansion, use only for 0<=x<1\nDefun(\"MathExpTaylor0\",[_x])\n{\n  Check(x<=?1 And? x>=?0,\"\",\" Argument must be between 0 and 1 inclusive.\");\n  Local(i,aResult,term,eps);\n  // Exp(x)=Sum(i=0 to Inf)  x^(i) /(i)!\n  // Which incrementally becomes the algorithm:\n  //\n  // i <- 0\n  Assign(i,0);\n  // sum <- 1\n  Assign(aResult,1.0);\n  // term <- 1\n  Assign(term,1.0);\n  Assign(eps,MathIntPower(10,MathNegate(BuiltinPrecisionGet())));\n  // While (term>?epsilon)\n  \n  While(GreaterThan?(AbsN(term),eps))\n  {\n    //   i <- i+1\n    Assign(i,AddN(i,1));\n    //   term <- term*x/(i)\n    Assign(term,DivideN(MultiplyN(term,x),i));\n    //   sum <- sum+term\n    Assign(aResult,AddN(aResult,term));\n  };\n  aResult;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/base/MathExpTaylor0.mpw, (MathExpTaylor0)";
        scriptMap.put("MathExpTaylor0",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n// power function for non-integer argument y -- use ExpN and LogN\n/* Serge, I disabled this one for now, until we get a compiled version of LogN that does not hang in\n   an infinite loop. The C++ version of LogN never terminates, so I mapped LogN to your InternalLnNum\n   which of course does a much better job of it. Corollary is that this function can be defined when we also\n   have InternalLnNum in this file.\nDefun(\"MathFloatPower\", [_x,_y])\n        Decide(Integer?(y), False, ExpN(MultiplyN(y,LogN(x))));\n*/\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/base/MathFloatPower.mpw, (MathFloatPower)";
        scriptMap.put("MathFloatPower",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n// power x^y only for integer y (perhaps negative)\nDefun(\"MathIntPower\", [_x,_y])\n        Decide(Equal?(x,0),0,Decide(Equal?(x,1),1,\n         Decide(Integer?(y),Decide(LessThan?(y,0), // negative power, need to convert x to float to save time, since x^(-n) is never going to be integer anyway\n          DivideN(1, PositiveIntPower(AddN(x,0.),MathNegate(y))),\n           // now the positive integer y calculation - note that x might still be integer\n          PositiveIntPower(x,y)\n         ),        // floating-point calculation is absent, return False\n         False)\n        ));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/base/MathIntPower.mpw, (MathIntPower)";
        scriptMap.put("MathIntPower",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n// MathMul2Exp: multiply x by 2^n quickly (for integer n)\n// this should really be implemented in the core as a call to BigNumber::ShiftRight or ShiftLeft\nDefun(\"MathMul2Exp\", [_x,_n])        // avoid roundoff by not calculating 1/2^n separately\n        Decide(GreaterThan?(n,0), MultiplyN(x, MathIntPower(2,n)), DivideN(x, MathIntPower(2,MathNegate(n))));\n// this doesnt work because ShiftLeft/Right dont yet work on floats\n//        Decide(GreaterThan?(n,0), ShiftLeft(x,n), ShiftRight(x,n)\n//        );\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/base/MathMul2Exp.mpw, (MathMul2Exp)";
        scriptMap.put("MathMul2Exp",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nDefun(\"MathPi\",[])\n{\n  // Newton's method for finding pi:\n  // x[0] := 3.1415926\n  // x[n+1] := x[n] + Sin(x[n])\n  Local(initialPrec,curPrec,result,aPrecision);\n  Assign(aPrecision,BuiltinPrecisionGet());\n        Assign(initialPrec, aPrecision);        // target precision of first iteration, will be computed below\n  Assign(curPrec, 40);  // precision of the initial guess\n  Assign(result, 3.141592653589793238462643383279502884197169399);    // initial guess\n\n        // optimize precision sequence\n        While (GreaterThan?(initialPrec, MultiplyN(curPrec,3)))\n  {\n                Assign(initialPrec, FloorN(DivideN(AddN(initialPrec,2),3)));\n  };\n        Assign(curPrec, initialPrec);\n  While (Not?(GreaterThan?(curPrec, aPrecision)))\n  {\n                 // start of iteration code\n    // Get Sin(result)\n    BuiltinPrecisionSet(curPrec);\n    Assign(result,AddN(result,SinN(result)));\n    // Calculate new result: result := result + Sin(result);\n                // end of iteration code\n                // decide whether we are at end of loop now\n                Decide(Equal?(curPrec, aPrecision),        // if we are exactly at full precision, it's the last iteration\n    {\n                        Assign(curPrec, AddN(aPrecision,1));        // terminate loop\n    },\n    {\n                        Assign(curPrec, MultiplyN(curPrec,3));        // precision triples at each iteration\n                        // need to guard against overshooting precision\n                         Decide(GreaterThan?(curPrec, aPrecision),\n      {\n                                Assign(curPrec, aPrecision);        // next will be the last iteration\n      });\n                });\n  };\n  BuiltinPrecisionSet(aPrecision);\n  result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/base/MathPi.mpw, (MathPi)";
        scriptMap.put("MathPi",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n// first define the binary exponentiation algorithm, MathIntPower.\n// Later, the PowerN function will be defined through IntPower and MathLn/ExpN. Note that ExpN uses IntPower.\n\n// power x^n only for non-negative integer n\nDefun(\"PositiveIntPower\", [_x,_n])\n{\n  Local(result,unit);\n  Decide(LessThan?(n,0), False,\n  {\n        Assign(unit,1);         // this is a constant, initial value of the power\n        Assign(result, unit);\n        Decide(Equal?(n,0),unit,\n         Decide(Equal?(n,1),x,\n          {\n                While(GreaterThan?(n,0))\n                {\n                        Decide(\n                                Equal?(BitAnd(n,1), 1),\n//                                Decide(\n//                                        Equal?(result,unit), // if result is already assigned\n//                                        Assign(result, x), // avoid multiplication\n                                        Assign(result, MultiplyN(result,x))\n//                                )\n                        );\n                        Assign(x, MultiplyN(x,x));\n                        Assign(n,ShiftRight(n,1));\n                };\n                result;\n          }\n         )\n        );\n  });\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/base/PositiveIntPower.mpw, (PositiveIntPower)";
        scriptMap.put("PositiveIntPower",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n// power function that works for all real x, y\n/// FIXME: No precision tracking yet.\n\n/* Serge, as MathFloatPower cannot be defined yet, I made the \"avoid PowerN(num,float) explicit :-)\n*/\nDefun(\"PowerN\", [_x,_y])\n// avoid PowerN(0,float)\n        Decide(Equal?(x,0),0, Decide(Equal?(x,1),1,\n          Decide(Integer?(y), MathIntPower(x,y), False/*MathFloatPower(x,y)*/)\n        ));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/base/PowerN.mpw, (PowerN)";
        scriptMap.put("PowerN",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nDefun(\"SinN\",[_x])Trigonometry(x,1.0,x,x);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/base/SinN.mpw, (SinN)";
        scriptMap.put("SinN",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nDefun(\"TanN\",[_x])DivideN(SinN(x),CosN(x));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/base/TanN.mpw, (TanN)";
        scriptMap.put("TanN",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nDefun(\"Trigonometry\",[_x,_i,_sum,_term])\n{\n  Local(x2,orig,eps,previousPrec,newPrec);\n  Assign(previousPrec,BuiltinPrecisionGet());\n  Assign(newPrec,AddN(BuiltinPrecisionGet(),2));\n  Assign(x2,MultiplyN(x,x));\n  BuiltinPrecisionSet(newPrec);\n  Assign(eps,MathIntPower(10,MathNegate(previousPrec)));\n  While(GreaterThan?(AbsN(term),eps))\n  {\n    Assign(term,MultiplyN(term,x2));\n    Assign(i,AddN(i,1.0));\n    Assign(term,DivideN(term,i));\n    Assign(i,AddN(i,1.0));\n    Assign(term,DivideN(MathNegate(term),i));\n    BuiltinPrecisionSet(previousPrec);\n    Assign(sum, AddN(sum, term));\n    BuiltinPrecisionSet(newPrec);\n  };\n  BuiltinPrecisionSet(previousPrec);\n  sum;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/base/Trigonometry.mpw, (Trigonometry)";
        scriptMap.put("Trigonometry",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Binomials -- now using partial factorial for speed */\n// BinomialCoefficient(n,m) = BinomialCoefficient(n, n-m)\n10 # BinomialCoefficient(0,0)                 <-- 1;\n10 # BinomialCoefficient(n_PositiveInteger?,m_NonNegativeInteger?)::(2*m <=? n) <-- ((n-m+1) *** n) / m!;\n15 # BinomialCoefficient(n_PositiveInteger?,m_NonNegativeInteger?)::(2*m >? n And? m <=? n) <-- BinomialCoefficient(n, n-m);\n20 # BinomialCoefficient(n_Integer?,m_Integer?) <-- 0;\n\nCombinations(n,m) := BinomialCoefficient(n,m);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/combinatorics/Combinations.mpw, (Combinations;BinomialCoefficient)";
        scriptMap.put("Combinations",scriptString);
        scriptMap.put("BinomialCoefficient",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"PermutationsList\",[_result,_list])\n{\n  Decide(Length(list) =? 0,\n  {\n    result;\n  },\n  {\n    Local(head);\n    Local(newresult);\n    Local(i);\n    head:=list[1];\n    newresult:=[];\n    ForEach(item,result)\n    {\n      For(i:=Length(item)+1,i>?0,i--)\n      {\n        DestructiveInsert(newresult,1,Insert(item,i,head));\n      };\n    };\n    newresult:=DestructiveReverse(newresult);\n    PermutationsList(newresult,Rest(list));\n  });\n};\n\n\nFunction(\"PermutationsList\",[_list])\n{\n  PermutationsList([[]],list);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/combinatorics/PermutationsList.mpw, (PermutationsList)";
        scriptMap.put("PermutationsList",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # Arg(Complex(Cos(_x),Sin(_x))) <-- x;\n10 # Arg(x_Zero?) <-- Undefined;\n15 # Arg(x_PositiveReal?) <-- 0;\n15 # Arg(x_NegativeReal?) <-- Pi;\n20 # Arg(Complex(r_Zero?,i_Constant?)) <-- Sign(i)*Pi/2;\n30 # Arg(Complex(r_PositiveReal?,i_Constant?)) <-- ArcTan(i/r);\n40 # Arg(Complex(r_NegativeReal?,i_PositiveReal?)) <-- Pi+ArcTan(i/r);\n50 # Arg(Complex(r_NegativeReal?,i_NegativeReal?)) <-- ArcTan(i/r)-Pi;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/complex/Arg.mpw, (Arg)";
        scriptMap.put("Arg",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n0 # Complex(_r,i_Zero?) <-- r;\n2 # Complex(Complex(_r1,_i1),_i2) <-- Complex(r1,i1+i2);\n2 # Complex(_r1,Complex(_r2,_i2)) <-- Complex(r1-i2,r2);\n\n6 # Complex(Undefined,_x) <-- Undefined;\n6 # Complex(_x,Undefined) <-- Undefined;\n\n\n/* Addition */\n\n110 # Complex(_r1,_i1) + Complex(_r2,_i2) <-- Complex(r1+r2,i1+i2);\n300 # Complex(_r,_i) + x_Constant? <-- Complex(r+x,i);\n300 # x_Constant? + Complex(_r,_i) <-- Complex(r+x,i);\n\n110 # - Complex(_r,_i) <-- Complex(-r,-i);\n\n300 # Complex(_r,_i) - x_Constant? <-- Complex(r-x,i);\n300 # x_Constant? - Complex(_r,_i) <-- Complex((-r)+x,-i);\n111 # Complex(_r1,_i1) - Complex(_r2,_i2) <-- Complex(r1-r2,i1-i2);\n\n/* Multiplication */\n110 # Complex(_r1,_i1) * Complex(_r2,_i2) <-- Complex(r1*r2-i1*i2,r1*i2+r2*i1);\n/* right now this is slower than above\n110 # Complex(_r1,_i1) * Complex(_r2,_i2) <--\n[        // the Karatsuba trick\n        Local(A,B);\n        A:=r1*r2;\n        B:=i1*i2;\n        Complex(A-B,(r1+i1)*(r2+i2)-A-B);\n];\n*/\n\n\n// Multiplication in combination with complex numbers in the light of infinity\n250 # Complex(r_Zero?,_i) * x_Infinity? <-- Complex(0,i*x);\n250 # Complex(_r,i_Zero?) * x_Infinity? <-- Complex(r*x,0);\n251 # Complex(_r,_i) * x_Infinity? <-- Complex(r*x,i*x);\n\n250 # x_Infinity? * Complex(r_Zero?,_i) <-- Complex(0,i*x);\n250 # x_Infinity? * Complex(_r,i_Zero?) <-- Complex(r*x,0);\n251 # x_Infinity? * Complex(_r,_i) <-- Complex(r*x,i*x);\n\n\n300 # Complex(_r,_i) * y_Constant? <-- Complex(r*y,i*y);\n300 # y_Constant? * Complex(_r,_i) <-- Complex(r*y,i*y);\n\n330 # Complex(_r,_i) * (y_Constant? / _z) <-- (Complex(r*y,i*y))/z;\n330 # (y_Constant? / _z) * Complex(_r,_i) <-- (Complex(r*y,i*y))/z;\n\n\n110 # x_Constant? / Complex(_r,_i) <-- (x*Conjugate(Complex(r,i)))/(r^2+i^2);\n\n\n300 # Complex(_r,_i) / y_Constant? <-- Complex(r/y,i/y);\n\n110 # (_x ^ Complex(_r,_i)) <-- Exp(Complex(r,i)*Ln(x));\n\n\n110 # (Complex(_r,_i) ^ x_RationalOrNumber?)::(Not?(Integer?(x))) <-- Exp(x*Ln(Complex(r,i)));\n\n// This is commented out because it used PowerN so (2*I)^(-10) became a floating-point number. Now everything is handled by binary algorithm below\n//120 # Complex(r_Zero?,_i) ^ n_Integer? <-- [1,I,-1,-I][1+Modulo(n,4)] * i^n;\n\n123 # Complex(_r, _i) ^ n_NegativeInteger? <-- 1/Complex(r, i)^(-n);\n\n124 # Complex(_r, _i) ^ (p_Zero?) <-- 1;        // cannot have Complex(0,0) here\n\n125 # Complex(_r, _i) ^ n_PositiveInteger? <--\n{\n        // use binary method\n        Local(result, x);\n        x:=Complex(r,i);\n        result:=1;\n        While(n >? 0)\n        {\n                If((n&1) =? 1)\n                {\n                  result := result*x;\n                };\n                x := x*x;\n                n := n>>1;\n        };\n        result;\n};\n\n\n/*[        // this method is disabled b/c it suffers from severe roundoff errors\n  Local(rr,ii,count,sign);\n  rr:=r^n;\n  ii:=0;\n  For(count:=1,count<=?n,count:=count+2) [\n    sign:=Decide(Zero?(Modulo(count-1,4)),1,-1);\n    ii:=ii+sign*BinomialCoefficient(n,count)*i^count*r^(n-count);\n    Decide(count<?n,\n      rr:=rr-sign*BinomialCoefficient(n,count+1)*i^(count+1)*r^(n-count-1));\n  ];\n  Complex(rr,ii);\n];\n*/\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/complex/Complex.mpw, (Complex)";
        scriptMap.put("Complex",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nComplexII?(_c) <-- (ImII(c) !=? 0);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/complex/ComplexII_.mpw, (ComplexII?)";
        scriptMap.put("ComplexII?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* All things you can request a real and imaginary part for are complex */\n1 # Complex?(x_RationalOrNumber?)     <-- True;\n2 # Complex?(Complex(_r,_i)) <-- True;\n3 # Complex?(_x)             <-- False;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/complex/Complex_.mpw, (Complex?)";
        scriptMap.put("Complex?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nLocalSymbols(a,x)\n{\nFunction(\"Conjugate\",[_a])\n  SubstituteApply(a,[[x],Type(x)=?\"Complex\"],[[x],Complex(x[1],-(x[2]))]);\n}; // LocalSymbols(a,x)\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/complex/Conjugate.mpw, (Conjugate)";
        scriptMap.put("Conjugate",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//\n// II is the imaginary number Sqrt(-1), and remains that way.\n// The difference is it isn't converted to the form Complex(x,y).\n//\n\nConstant('II);\n\n10 # II^n_NegativeInteger? <-- (-II)^(-n);\n20 # (II^_n)::(Even?(n) =? True) <-- (-1)^(n>>1);\n20 # (II^_n)::(Odd?(n)  =? True) <--  II*(-1)^(n>>1);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/complex/II.mpw, (II)";
        scriptMap.put("II",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Imaginary parts */\n110 # Im(Complex(_r,_i)) <-- i;\n120 # Im(Undefined) <-- Undefined;\n300 # Im(_x) <-- 0;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/complex/Im.mpw, (Im)";
        scriptMap.put("Im",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nImII(_c) <-- NN(c)[2];\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/complex/ImII.mpw, (ImII)";
        scriptMap.put("ImII",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"Magnitude\",[_x]) {\n        Sqrt(Re(x)^2 + Im(x)^2);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/complex/Magnitude.mpw, (Magnitude)";
        scriptMap.put("Magnitude",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nLocalSymbols(complexReduce) {\n\n  Assign(complexReduce,\n    Hold(\n    [\n      Exp(x_ComplexII?) <- Exp(ReII(x))*(Cos(ImII(x))+II*Sin(ImII(x)))\n    ]));\n\n  NN(_c) <--\n  {\n    Local(result);\n    c := (c /:: complexReduce);\n    result := Coef(Expand(c,II),II,[0,1]);\n    result;\n  };\n\n}; //LocalSymbols(complexReduce)\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/complex/NN.mpw, (NN)";
        scriptMap.put("NN",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/*Real parts */\n110 # Re(Complex(_r,_i)) <-- r;\n120 # Re(Undefined) <-- Undefined;\n300 # Re(_x) <-- x;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/complex/Re.mpw, (Re)";
        scriptMap.put("Re",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nReII(_c) <-- NN(c)[1];\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/complex/ReII.mpw, (ReII)";
        scriptMap.put("ReII",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* def file definitions.\nI\nCachedConstant\nAssignCachedConstants\nClearCachedConstants\n*/\n\n/* Definition of constants. */\n\n/* TODO:\n * There is a problem with defining I this way: if I is used, but the\n * file \"complex\" has not been loaded, the interpreter can not deal\n * with \"Complex\".\n *\n * Note:tk:10/9/09: Perhaps use SetGlobalLazyVariable(I,Hold(Complex(0,1)));\n */\n\nAssignGlobalLazy(I,Complex(0,1));\n\n//////////////////////////////////////////////////\n/// Cached constants support and definition of Pi\n//////////////////////////////////////////////////\n\n//TODO: here we wrap the entire file in LocalSymbols, this is inefficient in that it slows loading of this file. Needs optimization.\nLocalSymbols(CacheOfConstantsN) {\n\n/// declare a new cached constant Catom and its associated function Catom().\n/// Catom() will call Cfunc() at current precision to evaluate Catom if it has not yet been cached at that precision. (note: any arguments to Cfunc() must be included)\nRulebaseHoldArguments(\"CachedConstant\", [_Ccache, _Catom, _Cfunc]);\nUnFence(\"CachedConstant\", 3);        // not sure if this is useful\nHoldArgument(\"CachedConstant\", \"Cfunc\");\nHoldArgument(\"CachedConstant\", \"Ccache\");        // name of the cache\n// check syntax: must be called on an atom and a function\nRuleHoldArguments(\"CachedConstant\", 3, 10, And?(Atom?(Catom), Function?(Cfunc)))\n{\n    Local(Cname,CfunctionName);\n    Assign(Cname, ToString(Catom));        // this is for later conveniences\n    Assign(CfunctionName,ConcatStrings(\"Internal\",Cname));\n    \n            Decide(        // create the cache it if it does not already exist\n                    `(Not? Assigned?(@Ccache)),\n                    `(Assign(@Ccache, []))\n            );\n    //        Write([\"debug step 0: \", Ccache, Eval(Ccache), Catom, Cfunc, Cname]);\n            // check that the constant is not already defined\n            Decide(\n              Equal?(BuiltinAssoc(Cname, Eval(Ccache)), Empty),        // the constant is not already defined, so need to define \"Catom\" and the corresponding function \"Catom\"()\n              {        // e.g. Catom evaluates to Pi, Ccache to a name e.g. CacheOfConstantsN, which is bound to a hash\n                    MacroUnassign(Catom);\n    //                Write([\"debug step 1: \", Cachename, Ccache, Eval(Ccache)]);\n                    // add the new constant to the cache\n    //                MacroAssign(Cachename, Insert(Eval(Ccache), 1, [Cname, 0, 0]));\n                    DestructiveInsert(Eval(Ccache), 1, [Cname, 0, 0]);\n    //                Write([\"debug step 2: \", Cachename, Ccache, Eval(Ccache)]);\n                    // define the new function \"Catom\"()\n                    // note: this should not use NM() because it may be called from inside NM() itself\n    \n                    RulebaseEvaluateArguments(CfunctionName, []);\n                    `( RuleHoldArguments(@CfunctionName, 0, 1024, True)\n                    {\n                            Local(newprec, newC, cachedC);\n                            Assign(newprec, BuiltinPrecisionGet());\n                            // fetch the cache entry for this constant\n                            // note that this procedure will store the name of the cache here in this statement as Eval(Ccache)\n                            Assign(cachedC, BuiltinAssoc(@Cname, @Ccache));\n                            Decide( MathNth(cachedC, 2) !=? newprec,\n                              {        // need to recalculate at current precision\n                                    Decide(Equal?(InVerboseMode(),True), Echo(\"CachedConstant: Info: constant \", @Cname, \" is being recalculated at precision \", newprec));\n                                    Assign(newC, RoundTo(Eval(@Cfunc),newprec));\n                                    DestructiveReplace(cachedC, 2, newprec);\n                                    DestructiveReplace(cachedC, 3, newC);\n                                    newC;\n                              },\n                              // return cached value of Catom\n                              MathNth(cachedC, 3)\n                            );\n                    });\n    \n                    // calculate Catom at current precision for the first time\n    //                Eval(ListToFunction([Catom]));        // \"Cname\"();\n                    // we do not need this until the constant is used; it will just slow us down\n              },\n              // the constant is defined\n              Echo(\"CachedConstant: Warning: constant \", Catom, \" already defined\")\n            );\n    };\n    \n    RuleHoldArguments(\"CachedConstant\", 3, 20, True)\n            Echo(\"CachedConstant: Error: \", Catom, \" must be an atom and \", Cfunc, \" must be a function.\");\n    \n    /// assign numerical values to all cached constants: using fixed cache \"CacheOfConstantsN\"\n    // this is called from NM()\n    Function(\"AssignCachedConstantsN\", [])\n    {\n            Local(var,fname);\n            ForEach(var, AssocIndices(CacheOfConstantsN))\n            {\n                MacroUnassign(ToAtom(var));\n        		Assign(fname,ConcatStrings(\"Internal\",var));\n        		Assign(var,ToAtom(var));\n                // this way the routine InternalPi() will be actually called only when the variable Pi is used, etcetera.\n        		`AssignGlobalLazy((@var), ListToFunction([ToAtom(fname)]));\n            };\n    };\n    UnFence(\"AssignCachedConstantsN\", 0);\n    \n    \n    \n    /// clear values from all cached constants: using fixed cache \"CacheOfConstantsN\"\n    // this is called from NM()\n    Function(\"ClearCachedConstantsN\", [])\n    {\n            Local(centry);\n            ForEach(centry, CacheOfConstantsN)\n            {\n                MacroUnassign(ToAtom(centry[1]));\n                Constant(ToAtom(centry[1]));\n            };\n    };\n    UnFence(\"ClearCachedConstantsN\", 0);\n    \n    \n    \n    Function(\"CachedConstant?\", [_name])\n    {\n    	Check(String?(name), \"Argument\", \"The argument must be a string.\");\n    	\n        Contains?(AssocIndices(CacheOfConstantsN), name);\n    };    \n    \n    \n    /// declare some constants now\n    CachedConstant(CacheOfConstantsN, 'Pi,\n    // it seems necessary to precompute Pi to a few more digits\n    // so that Cos(0.5*Pi)=0 at precision 10\n    // FIXME: find a better solution\n    {        \n        Local(result,oldprec);\n        Assign(oldprec,BuiltinPrecisionGet());\n        Decide(Equal?(InVerboseMode(),True), Echo(\"Recalculating Pi at precision \",oldprec+5));\n        BuiltinPrecisionSet(BuiltinPrecisionGet()+5);\n        result := MathPi();\n        Decide(Equal?(InVerboseMode(),True),Echo(\"Switching back to precision \",oldprec));\n        BuiltinPrecisionSet(oldprec);\n        result;\n    }\n    \n    );\n    \n    \n    CachedConstant(CacheOfConstantsN, 'gamma, GammaConstNum());\n    \n    CachedConstant(CacheOfConstantsN, 'GoldenRatio, NM( (1+Sqrt(5))/2 ) );\n    \n    CachedConstant(CacheOfConstantsN, 'Catalan, CatalanConstNum() );\n\n}; // LocalSymbols(CacheOfConstantsN)\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/constants/constants.mpw, (I;CachedConstant;CachedConstant?;AssignCachedConstantsN;ClearCachedConstantsN)";
        scriptMap.put("I",scriptString);
        scriptMap.put("CachedConstant",scriptString);
        scriptMap.put("CachedConstant?",scriptString);
        scriptMap.put("AssignCachedConstantsN",scriptString);
        scriptMap.put("ClearCachedConstantsN",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(\"Else\",[_ifthen,_otherwise]);\n\n0 # (If(_predicate) _body Else _otherwise)::(Eval(predicate) =? True) <-- Eval(body);\n\n0 # (If(_predicate) _body Else _otherwise)::(Eval(predicate) =? False) <-- Eval(otherwise);\n\n1 # (If(_predicate) _body Else _otherwise) <--\n    ListToFunction([ToAtom(\"Else\"),\n            ListToFunction([ToAtom(\"If\"), (Eval(predicate)), body]),\n            otherwise]);\n\nHoldArgument(\"Else\",\"ifthen\");\n\nHoldArgument(\"Else\",\"otherwise\");\n\nUnFence(\"Else\",2);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/contolflow/Else.mpw, (Else)";
        scriptMap.put("Else",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Defining a For function */\nTemplateFunction(\"For\",[_start,_predicate,_increment,_body])\n{\n  Eval(start);\n  While (Equal?(Eval(predicate),True))\n  {\n    Eval(body);\n    Eval(increment);\n  };\n};\nUnFence(\"For\",4);\nHoldArgumentNumber(\"For\",4,1);\nHoldArgumentNumber(\"For\",4,2);\nHoldArgumentNumber(\"For\",4,3);\nHoldArgumentNumber(\"For\",4,4);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/contolflow/For.mpw, (For)";
        scriptMap.put("For",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"ForEach\" , *);\n\n/*TODO remove? Not yet. If the code above (ForEachExperimental) can be made to work we can do away with this version. */\nTemplateFunction(\"ForEach\",[_item,_listOrString,_body])\n{\n  Decide(And?(Equal?(Generic?(listOrString),True),\n         Equal?(GenericTypeName(listOrString),\"Array\")\n         ),\n    `ForEachInArray(@item,listOrString,@body),\n    {\n\n      MacroLocal(item);\n      \n      Decide(String?(listOrString),\n      {\n          \n          Local(index, stringLength);\n          \n          stringLength := Length(listOrString);\n          \n          index := 1;\n          While(index <=? stringLength )\n          {\n             MacroAssign(item,listOrString[index] );\n             \n             Eval(body);\n             \n             index++;\n          };\n\n      },\n      {\n          Local(foreachtail);\n          Assign(foreachtail,listOrString);\n          While(Not?(Equal?(foreachtail,[])))\n          {\n            MacroAssign(item,First(foreachtail));\n            Eval(body);\n            Assign(foreachtail,Rest(foreachtail));\n          };\n      });\n    });\n};\nUnFence(\"ForEach\",3);\nHoldArgumentNumber(\"ForEach\",3,1);\nHoldArgumentNumber(\"ForEach\",3,3);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/contolflow/ForEach.mpw, (ForEach)";
        scriptMap.put("ForEach",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(\"If\",[_predicate,_body]);\n\n(If(True) _body) <-- Eval(body);\n\nHoldArgument(\"If\",\"body\");\n\nUnFence(\"If\",2);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/contolflow/If.mpw, (If)";
        scriptMap.put("If",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Lambda was introduced as a form of pure function that can be passed on to the function Apply as a first argument.\n * The original method, passing it in as a list, had the disadvantage that the list was evaluated, which caused the\n * arguments to be evaluated too. This resulted in unwanted behaviour sometimes (expressions being prematurely evaluated\n * in the body of the pure function). The arguments to Lambda are not evaluated.\n */\nMacroRulebaseHoldArguments(\"Lambda\",[_args,_body]);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/contolflow/Lambda.mpw, (Lambda)";
        scriptMap.put("Lambda",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nTemplateFunction(\"Until\",[_predicate,_body])\n{\n  Eval(body);\n  While (Equal?(Eval(predicate),False))\n  {\n    Eval(body);\n  };\n  True;\n};\nUnFence(\"Until\",2);\nHoldArgumentNumber(\"Until\",2,1);\nHoldArgumentNumber(\"Until\",2,2);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/contolflow/Until.mpw, (Until)";
        scriptMap.put("Until",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nLocalSymbols(Verbose) {\n\n  Assign(Verbose,False);\n\n\n  Function(\"V\",[_aNumberBody])\n  {\n    Local(prevVerbose,result);\n    Assign(prevVerbose,Verbose);\n    Assign(Verbose,True);\n    Assign(result,Eval(aNumberBody));\n    Assign(Verbose,prevVerbose);\n    result;\n  };\n\n\n  Function(\"InVerboseMode\",[]) Verbose;\n\n}; // LocalSymbols(Verbose)\n\nHoldArgument(\"V\",\"aNumberBody\");\nUnFence(\"V\",1);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/debug/verbose_mode.mpw, (V;InVerboseMode)";
        scriptMap.put("V",scriptString);
        scriptMap.put("InVerboseMode",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(\"AssignArray\", [_listOrAssociationList, _indexOrKey, _newValue]);\n\nUnFence(\"AssignArray\", 3);\n\n\n// Assign association lists.\nRuleHoldArguments(\"AssignArray\", 3, 1, String?(indexOrKey))\n{\n    Local(keyValuePair);\n\n    keyValuePair := Assoc(indexOrKey, listOrAssociationList);\n\n    Decide(keyValuePair =? Empty,\n\n        DestructiveInsert(listOrAssociationList, 1 , [indexOrKey, newValue]),\n\n        DestructiveReplace(keyValuePair, 2 , newValue)\n    );\n\n    True;\n};\n\n\n\n// Assign generic arrays.\nRuleHoldArguments(\"AssignArray\",3,1, And?( Equal?(Generic?(listOrAssociationList), True), Equal?(GenericTypeName(listOrAssociationList), \"Array\")))\n{\n    ArraySet(listOrAssociationList, indexOrKey, newValue);\n};\n\n\nRuleHoldArguments(\"AssignArray\",3 , 2, True)\n{\n    DestructiveReplace(listOrAssociationList, indexOrKey, newValue);\n\n    True;\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/deffunc/AssignArray.mpw, (AssignArray)";
        scriptMap.put("AssignArray",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Set of functions to define very simple functions. There are scripts that can\n   be compiled to plugins. So MathPiper either loads the plugin, or loads the\n   scripts at this point. The functions in these plugins need to be defined with\n   these \"Defun\" functions.\n */\nMacroRulebaseHoldArguments(\"Defun\",[_func,_args,_body]);\nRuleHoldArguments(\"Defun\",3,0,True)\n{\n  Local(nrargs);\n  Assign(nrargs,Length(@args));\n  Retract(@func, `(@nrargs));\n  RulebaseHoldArguments(@func,@args);\n  Local(fn,bd);\n  Assign(fn,Hold(@func)); Assign(bd,Hold(@body));\n  `RuleHoldArguments(@fn, @nrargs, 0,True)(@bd);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/deffunc/Defun.mpw, (Defun)";
        scriptMap.put("Defun",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Defining a macro-like function that declares a function\n * with only one rule.\n */\nRulebaseHoldArguments(\"Function\",[_oper,_args,_body]);\n\n\n\n// function with variable number of arguments: Function(\"func\",[x,y, ...])body;\nRuleHoldArguments(\"Function\",3,2047,\n        And?(GreaterThan?(Length(args), 1), Equal?( MathNth(args, Length(args)), ToAtom(\"...\") ))\n)\n{\n  DestructiveDelete(args,Length(args));        // remove trailing \"...\"\n  Retract(oper,Length(args));\n  RulebaseListedEvaluateArguments(oper,args);\n  RuleEvaluateArguments(oper,Length(args),1025,True) body;        // at precedence 1025, for flexibility\n};\n\n// function with a fixed number of arguments\nRuleHoldArguments(\"Function\",3,2048,True)\n{\n  Retract(oper,Length(args));\n  RulebaseEvaluateArguments(oper,args);\n  RuleEvaluateArguments(oper,Length(args),1025,True) body;\n};\n\n\n/// shorthand function declarations\nRulebaseHoldArguments(\"Function\",[_oper]);\n// function with variable number of arguments: Function() f(x,y, ...)\nRuleHoldArguments(\"Function\",1,2047,\n        And?(Function?(oper), GreaterThan?(Length(oper), 1), Equal?( MathNth(oper, Length(oper)), ToAtom(\"...\") ))\n)\n{\n        Local(args);\n        Assign(args,Rest(FunctionToList(oper)));\n        DestructiveDelete(args,Length(args));        // remove trailing \"...\"\n        Decide(RulebaseDefined(Type(oper),Length(args)),\n                False,        // do nothing\n                RulebaseListedEvaluateArguments(Type(oper),args)\n        );\n};\n\n\n// function with a fixed number of arguments\nRuleHoldArguments(\"Function\",1,2048,\n        And?(Function?(oper))\n)\n{\n        Local(args);\n        Assign(args,Rest(FunctionToList(oper)));\n        Decide(RulebaseDefined(Type(oper),Length(args)),\n                False,        // do nothing\n                RulebaseEvaluateArguments(Type(oper),args)\n        );\n};\n\n\nHoldArgument(\"Function\",\"oper\");\nHoldArgument(\"Function\",\"args\");\nHoldArgument(\"Function\",\"body\");\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/deffunc/Function.mpw, (Function)";
        scriptMap.put("Function",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"HoldArgumentNumber\",[_function,_arity,_index])\n{\n  Local(args);\n  args:=RulebaseArgumentsList(function,arity);\n/* Echo([\"holdnr \",args]); */\n  ApplyFast(\"HoldArgument\",[function,args[index]]);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/deffunc/HoldArgumentNumber.mpw, (HoldArgumentNumber)";
        scriptMap.put("HoldArgumentNumber",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(\"Macro\",[_oper,_args,_body]);\nHoldArgument(\"Macro\",\"oper\");\nHoldArgument(\"Macro\",\"args\");\nHoldArgument(\"Macro\",\"body\");\n\n// macro with variable number of arguments: Macro(\"func\",[x,y, ...])body;\nRuleHoldArguments(\"Macro\",3,2047,\n        And?(GreaterThan?(Length(args), 1), Equal?( MathNth(args, Length(args)), ToAtom(\"...\") ))\n)\n{\n  DestructiveDelete(args,Length(args));        // remove trailing \"...\"\n  Retract(oper,Length(args));\n  `MacroRulebaseListedHoldArguments(@oper,@args);\n  RuleEvaluateArguments(oper,Length(args),1025,True) body;        // at precedence 1025, for flexibility\n};\n\n// macro with a fixed number of arguments\nRuleHoldArguments(\"Macro\",3,2048,True)\n{\n  Retract(oper,Length(args));\n  `MacroRulebaseHoldArguments(@oper,@args);\n  RuleEvaluateArguments(oper,Length(args),1025,True) body;\n};\n\nRulebaseHoldArguments(\"Macro\",[_oper]);\n// macro with variable number of arguments: Macro() f(x,y, ...)\nRuleHoldArguments(\"Macro\",1,2047,\n        And?(Function?(oper), GreaterThan?(Length(oper), 1), Equal?( MathNth(oper, Length(oper)), ToAtom(\"...\") ))\n)\n{\n        Local(args,name);\n        Assign(args,Rest(FunctionToList(oper)));\n        DestructiveDelete(args,Length(args));        // remove trailing \"...\"\n  Assign(name,Type(oper));\n        Decide(RulebaseDefined(Type(oper),Length(args)),\n                False,        // do nothing\n                `MacroRulebaseListedHoldArguments(@name,@args)\n        );\n};\n// macro with a fixed number of arguments\nRuleHoldArguments(\"Macro\",1,2048,\n        And?(Function?(oper))\n)\n{\n        Local(args,name);\n        Assign(args,Rest(FunctionToList(oper)));\n  Assign(name,Type(oper));\n        Decide(RulebaseDefined(Type(oper),Length(args)),\n                False,        // do nothing\n                {\n      `MacroRulebaseHoldArguments(@name,@args);\n    }\n        );\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/deffunc/Macro.mpw, (Macro)";
        scriptMap.put("Macro",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(\"TemplateFunction\",[_oper,_args,_body]);\n\nHoldArgument(\"TemplateFunction\",\"oper\");\n\nHoldArgument(\"TemplateFunction\",\"args\");\n\nHoldArgument(\"TemplateFunction\",\"body\");\n\nRuleHoldArguments(\"TemplateFunction\",3,2047,True)\n{\n  Retract(oper,Length(args));\n  \n  Local(arglist);\n  \n  arglist:=FlatCopy(args);\n  \n  DestructiveAppend(arglist,[args,ListToFunction(['Hold,body])]);\n  \n  arglist:=ApplyFast(\"LocalSymbols\",arglist);\n\n  RulebaseEvaluateArguments(oper,arglist[1]);\n  \n  RuleEvaluateArguments(oper,Length(args),1025,True) arglist[2];\n\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/deffunc/TemplateFunction.mpw, (TemplateFunction)";
        scriptMap.put("TemplateFunction",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\nRulebaseHoldArguments(\"Unholdable\",[_var]);\n\nHoldArgument(\"Unholdable\",\"var\");\n\nUnFence(\"Unholdable\",1);\n\nRuleHoldArguments(\"Unholdable\",1,10,Equal?(Type(Eval(var)),\"Eval\"))\n{\n    MacroAssign(var,Eval(Eval(var)));\n    //Echo([\"unheld\",var,Eval(var)]);\n};\n\n\nRuleHoldArguments(\"Unholdable\",1,20,True)\n{\n    //Echo([\"held\"]);\n  True;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/deffunc/Unholdable.mpw, (Unholdable)";
        scriptMap.put("Unholdable",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(\":=\",[_aLeftAssign, _aRightAssign]);\nUnFence(\":=\", 2);\nHoldArgument(\":=\", \"aLeftAssign\");\nHoldArgument(\":=\", \"aRightAssign\");\n\n\n\n// assign a variable\nRuleHoldArguments(\":=\", 2, 0, Atom?(aLeftAssign))\n{\n    Check( Not? Number?(aLeftAssign), \"Argument\", \"Only a variable can be placed on the left side of an := operator.\" );\n\n    MacroAssign(aLeftAssign,Eval(aRightAssign));\n\n    Eval(aLeftAssign);\n};\n\n\n\n// assign lists\nRuleHoldArguments(\":=\",2 , 0, List?(aLeftAssign))\n{\n    //Map(\":=\",[aLeftAssign,Eval(aRightAssign)]);\n    \n    Local(index, right, result);\n    \n    Assign(result, []);\n    \n    Assign(index, 1);\n    \n    Assign(right, Eval(aRightAssign));\n    \n    While(index <=? Length(aLeftAssign))\n    {\n    	MacroAssign(aLeftAssign[index], right[index]);\n    	\n    	DestructiveAppend(result, right[index]);\n    	\n    	index++;\n    };\n    \n    result;\n};\n\n\n\n// a[x] := ... assigns to an array element\nRuleHoldArguments(\":=\", 2, 10, Function?(aLeftAssign) And? (First(FunctionToList(aLeftAssign)) =? 'Nth))\n{\n    Local(listOrAssociationList, indexOrKey, argumentsList);\n\n    Assign(argumentsList, (FunctionToList(aLeftAssign)));\n\n    Assign(argumentsList, Rest(argumentsList));\n\n    Assign(listOrAssociationList, Eval(First(argumentsList)));\n\n    Assign(argumentsList, Rest(argumentsList));\n\n    Assign(indexOrKey, Eval(First(argumentsList)));\n\n    AssignArray(listOrAssociationList, indexOrKey, Eval(aRightAssign));\n};\n\n\n\n// f(x):=... defines a new function\nRuleHoldArguments(\":=\", 2, 30, Function?(aLeftAssign) And? Not?(Equal?(aLeftAssign[0], ToAtom(\":=\"))) )\n{\n    Check( Not? Equal?(aLeftAssign[0], ToAtom(\"/\")), \"Argument\", \"Only a variable can be placed on the left side of an := operator.\" );\n\n    Local(oper,args,arity);\n\n    Assign(oper,ToString(aLeftAssign[0]));\n\n    Assign(args,Rest(FunctionToList(aLeftAssign)));\n\n    Decide(\n        And?(GreaterThan?(Length(args), 1), Equal?( MathNth(args, Length(args)), ToAtom(\"...\") )),\n        // function with variable number of arguments\n        {\n            DestructiveDelete(args, Length(args)); // remove trailing \"...\"\n\n            Assign(arity, Length(args));\n\n            Retract(oper, arity);\n\n            RulebaseListedEvaluateArguments(oper, MapSingle(Lambda([x],ToAtom(ConcatStrings(\"_\",ToString(x)))), args));\n        },\n        // function with a fixed number of arguments\n        {\n            Assign(arity, Length(args));\n\n            Retract(oper, arity);\n\n            RulebaseEvaluateArguments(oper, MapSingle(Lambda([x],ToAtom(ConcatStrings(\"_\",ToString(x)))), args));\n        }\n    );\n\n    Unholdable(aRightAssign);\n\n    RuleEvaluateArguments(oper, arity, 1025, True) aRightAssign;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/deffunc/colon_equals_operator.mpw, (:=)";
        scriptMap.put(":=",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nEquationLeft(_symbolicEquation)::(Type(symbolicEquation) =? \"==\")  <--\n{\n    Local(listForm);\n    \n    listForm := FunctionToList(symbolicEquation);\n    \n    listForm[2];\n};\n";
        scriptString[2] = "/org/mathpiper/scripts4/equations/EquationLeft.mpw, (EquationLeft)";
        scriptMap.put("EquationLeft",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nEquationRight(_symbolicEquation)::(Type(symbolicEquation) =? \"==\")  <--\n{\n    Local(listForm);\n    \n    listForm := FunctionToList(symbolicEquation);\n    \n    listForm[3];\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/equations/EquationRight.mpw, (EquationRight)";
        scriptMap.put("EquationRight",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # Apply(_applyoper,_applyargs):: (Or?(String?(applyoper), List?(applyoper))) <-- ApplyFast(applyoper,applyargs);\n20 # Apply(applyoper_Atom?,_applyargs) <-- ApplyFast(ToString(applyoper),applyargs);\n\n30 # Apply(Lambda(_args,_body),_applyargs) <-- `ApplyFast(Hold([@args,@body]),applyargs);\nUnFence(\"Apply\",2);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/functional/Apply.mpw, (Apply)";
        scriptMap.put("Apply",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* NFunction(\"newfunc\", \"oldfunc\" [arglist]) will define a wrapper function\naround  \"oldfunc\", called \"newfunc\", which will return \"oldfunc(arglist)\"\nonly when all arguments are numbers and will return unevaluated\n\"newfunc(arglist)\" otherwise. */\nLocalSymbols(NFunctionNumberize)\n{\nNFunction(newname_String?, oldname_String?, arglist_List?) <-- {\n        RulebaseEvaluateArguments(newname, arglist);\n        RuleEvaluateArguments(newname, Length(arglist), 0,        // check whether all args are numeric\n                ListToFunction(['NumericList?, arglist])\n        )\n\n                /* this is the rule defined for the new function.\n                // this expression should evaluate to the body of the rule.\n                // the body looks like this:\n                // NFunctionNumberize(oldname(arglist))\n                */\n                        NFunctionNumberize(ListToFunction([ToAtom(\"@\"), oldname, arglist]));\n                        // cannot use bare '@' b/c get a syntax error\n\n};\n\n// this function is local to NFunction.\n// special handling for numerical errors: return Undefined unless given a number.\n10 # NFunctionNumberize(x_Number?) <-- x;\n20 # NFunctionNumberize(x_Atom?) <-- Undefined;\n// do nothing unless given an atom\n\n};        // LocalSymbols()\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/functional/NFunction.mpw, (NFunction;NFunctionNumberize;NFunctionNumberize)";
        scriptMap.put("NFunction",scriptString);
        scriptMap.put("NFunctionNumberize",scriptString);
        scriptMap.put("NFunctionNumberize",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(\"@\",[_func,_arg]);\nRuleHoldArguments(\"@\",2,1,List?(arg)) Apply(func,arg);\nRuleHoldArguments(\"@\",2,2,True       ) Apply(func,[arg]);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/functional/atsign_operator.mpw, (@)";
        scriptMap.put("@",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/*\n.. operator is implemented with the Table function.\n*/\n10 # (countfrom_Integer? .. countto_Integer?)::(countfrom <=? countto)\n   <-- BuildList(i,i,countfrom,countto,1);\n20 # (countfrom_Integer? .. countto_Integer?)\n   <-- BuildList(i,i,countfrom,countto,-1);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/functional/dot_dot_operator.mpw, (..)";
        scriptMap.put("..",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"/@\",[_func,_lst]) Apply(\"MapSingle\",[func,lst]);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/functional/slash_atsign_operator.mpw, (/@)";
        scriptMap.put("/@",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\n\n/* a ~ b will now return unevaluated (rather than cause error of invalid argument in Concat) if neither a nor b is a list and if one of them is not a string\n*/\nRulebaseHoldArguments(\"~\",[_head,_tail]);\nRuleHoldArguments(\"~\",2,20,List?(head) And? Not? List?(tail) ) Concat(head,[tail]);\nRuleHoldArguments(\"~\",2,30,List?(tail) ) Concat([head],tail);\nRuleHoldArguments(\"~\",2,10,String?(tail) And? String?(head)) ConcatStrings(head,tail);\nUnFence(\"~\",2);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/functional/tilde_operator.mpw, (~)";
        scriptMap.put("~",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// post an error if assertion fails\n(Assert(_errorclass, _errorobject) _predicate) <--\n{\n        CheckErrorTableau();\n        Decide(Equal?(predicate, True),        // if it does not evaluate to True, its an error\n                True,\n                {        // error occurred, need to post errorobject\n                        DestructiveAppend(GetErrorTableau(), [errorclass, errorobject]);\n                        False;\n                }\n        );\n};\n\n/// interface\n(Assert(_errorclass) _predicate) <-- Assert(errorclass, True) predicate;\n\n/// interface\n(Assert() _predicate) <-- Assert(\"generic\", True) predicate;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/io/Assert.mpw, (Assert)";
        scriptMap.put("Assert",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// The new default pretty-printer: DefaultPrint\nFunction(\"DefaultPrint\", [_x])\n{\n        DumpErrors();\n        WriteString(\"Result: \");\n        Write(x);\n        WriteString(\";\n\");\n};\nHoldArgument(\"DefaultPrint\", \"x\");\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/io/DefaultPrint.mpw, (DefaultPrint)";
        scriptMap.put("DefaultPrint",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// print all errors and clear the tableau\nDumpErrors() <--\n{\n        Local(errorobject, errorword);\n        CheckErrorTableau();\n        ForEach(errorobject, GetErrorTableau())\n        {        // errorobject might be e.g. [\"critical\", [\"bad bad\", -1000]]\n                Decide(\n                        List?(errorobject),\n                        {\n                                Decide( // special case: error class \"warning\"\n                                        Length(errorobject) >? 0 And? errorobject[1] =? \"warning\",\n                                        {\n                                                errorword := \"Warning\";\n                                                errorobject[1] := \"\";        // dont print the word \"warning\" again\n                                        },\n                                        errorword := \"Error: \"        // important hack: insert \": \" here but not after \"Warning\"\n                                );\n\n                                Decide(        // special case: [\"errorclass\", True]\n                                        Length(errorobject)=?2 And? errorobject[2]=?True,\n                                        Echo(errorword, errorobject[1]),\n                                        {\n                                                Echo(errorword, errorobject[1], \": \",\n                                                        PrintList(Rest(errorobject)));\n                                        }\n                                );\n                        },\n                        // errorobject is not a list: just print it\n                        Echo(\"Error: \", errorobject)\n                );\n        };\n        ClearErrors();\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/io/DumpErrors.mpw, (DumpErrors)";
        scriptMap.put("DumpErrors",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"EchoInternal\",*);\n\n10 # EchoInternal(string_String?) <--\n{\n  WriteString(string);\n};\n\n20 # EchoInternal(_item) <--\n{\n  Write(item);Space();\n};\n\n\n\n\n\n\n//Retract(\"Echo\",*);\n\nRulebaseListedHoldArguments(\"Echo\",[_firstParameter, _parametersList]);\n\n//Handle no option call.\n5 # Echo(_firstParameter) <-- Echo(firstParameter, []);\n\n\n//Main routine.  It will automatically accept 1 or more option calls because the\n//options come in a list.\n10 # Echo(_firstParameter, parametersList_List?) <--\n{\n    EchoInternal(firstParameter);\n    ForEach(item,parametersList) EchoInternal(item);\n    NewLine();\n    \n};\n\n\n//Handle a single option call because the option does not come in a list for some reason.\n20 # Echo(_firstParameter, _secondParameter) <-- Echo(firstParameter, [secondParameter]);\n\n\n//No argument Echo simply prints a newline.\nEcho() := NewLine();\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/io/Echo.mpw, (Echo;EchoInternal)";
        scriptMap.put("Echo",scriptString);
        scriptMap.put("EchoInternal",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// check for errors\nError?() <--\n{\n        CheckErrorTableau();\n        Length(GetErrorTableau())>?0;\n};\n\n/// check for errors of a given kind\nError?(errorclass_String?) <--\n{\n        CheckErrorTableau();\n        GetErrorTableau()[errorclass] !=? Empty;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/io/Error_.mpw, (Error?)";
        scriptMap.put("Error?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* def file definitions\nClearErrors\nGetError\n*/\n\n//////////////////////////////////////////////////\n/// ErrorTableau, Assert, Error? --- global error reporting\n//////////////////////////////////////////////////\n\nLocalSymbols(ErrorTableau) {\n\n  /// global error tableau. Its entries do not have to be lists.\n  Assign(ErrorTableau, []);\n\n  GetErrorTableau() := ErrorTableau;\n\n  ClearErrors() <-- Assign(ErrorTableau, []);\n\n  /// aux function to check for corrupt tableau\n  CheckErrorTableau() <--\n  Decide(\n    Not? List?(ErrorTableau),\n    Assign(ErrorTableau, [[\"general\", \"corrupted ErrorTableau\"]])\n  );\n\n}; // LocalSymbols(ErrorTableau)\n\n\n/// obtain error object\nGetError(errorclass_String?) <--\n{\n        Local(error);\n        error := GetErrorTableau()[errorclass];\n        Decide(\n                error !=? Empty,\n                error,\n                False\n        );\n};\n\n\n/// delete error\nClearError(errorclass_String?) <-- AssocDelete(GetErrorTableau(), errorclass);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/io/GetErrorTableau.mpw, (GetErrorTableau;ClearError;ClearErrors;GetError;CheckErrorTableau)";
        scriptMap.put("GetErrorTableau",scriptString);
        scriptMap.put("ClearError",scriptString);
        scriptMap.put("ClearErrors",scriptString);
        scriptMap.put("GetError",scriptString);
        scriptMap.put("CheckErrorTableau",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\n/* A reference print implementation. Expand at own leisure.\n *\n * This file implements Print, a scripted expression printer.\n */\n\n\n/* 60000 is the maximum precedence allowed for operators */\n10 # Print(_x) <--\n{\n  Print(x,60000);\n  NewLine();\n  DumpErrors();\n};\n\n/* Print an argument within an environment of precedence n */\n10 # Print(x_Atom?,_n) <-- Write(x);\n10 # Print(_x,_n)::(Infix?(Type(x))And? ArgumentsCount(x) =? 2) <--\n{\n  Local(bracket);\n  bracket:= (PrecedenceGet(Type(x)) >? n);\n  Decide(bracket,WriteString(\"(\"));\n  Print(x[1],LeftPrecedenceGet(Type(x)));\n  Write(x[0]);\n  Print(x[2],RightPrecedenceGet(Type(x)));\n  Decide(bracket,WriteString(\")\"));\n};\n\n10 # Print(_x,_n)::(Prefix?(Type(x)) And? ArgumentsCount(x) =? 1) <--\n{\n  Local(bracket);\n  bracket:= (PrecedenceGet(Type(x)) >? n);\n  Write(x[0]);\n  Decide(bracket,WriteString(\"(\"));\n  Print(x[1],RightPrecedenceGet(Type(x)));\n  Decide(bracket,WriteString(\")\"));\n};\n\n10 # Print(_x,_n)::(Postfix?(Type(x))And? ArgumentsCount(x) =? 1) <--\n{\n  Local(bracket);\n  bracket:= (PrecedenceGet(Type(x)) >? n);\n  Decide(bracket,WriteString(\"(\"));\n  Print(x[1],LeftPrecedenceGet(Type(x)));\n  Write(x[0]);\n  Decide(bracket,WriteString(\")\"));\n};\n\n20 # Print(_x,_n)::(Type(x) =? \"List\") <--\n{\n  WriteString(\"[\");\n  PrintArg(x);\n  WriteString(\"]\");\n};\n\n20 # Print(_x,_n)::(Type(x) =? \"Block\") <--\n{\n  WriteString(\"[\");\n  PrintArgBlock(Rest(FunctionToList(x)));\n  WriteString(\"]\");\n};\n20 # Print(_x,_n)::(Type(x) =? \"Nth\") <--\n{\n  Print(x[1],0);\n  WriteString(\"[\");\n  Print(x[2],60000);\n  WriteString(\"]\");\n};\n\n100 # Print(x_Function?,_n) <--\n {\n   Write(x[0]);\n   WriteString(\"(\");\n   PrintArg(Rest(FunctionToList(x)));\n   WriteString(\")\");\n };\n\n\n/* Print the arguments of an ordinary function */\n10 # PrintArg([]) <-- True;\n\n20 # PrintArg(_list) <--\n{\n  Print(First(list),60000);\n  PrintArgComma(Rest(list));\n};\n10 # PrintArgComma([]) <-- True;\n20 # PrintArgComma(_list) <--\n{\n  WriteString(\",\");\n  Print(First(list),60000);\n  PrintArgComma(Rest(list));\n};\n\n\n18 # Print(Complex(0,1),_n)   <-- {WriteString(\"I\");};\n19 # Print(Complex(0,_y),_n)  <-- {WriteString(\"I*\");Print(y,4);};\n19 # Print(Complex(_x,1),_n)  <-- {Print(x,7);WriteString(\"+I\");};\n20 # Print(Complex(_x,_y),_n) <-- {Print(x,7);WriteString(\"+I*\");Print(y,4);};\n\n\n/* Tail-recursive printing the body of a compound statement */\n10 # PrintArgBlock([]) <-- True;\n20 # PrintArgBlock(_list) <--\n{\n   Print(First(list),60000);\n   WriteString(\";\");\n   PrintArgBlock(Rest(list));\n};\n\n\n\n\n\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/io/Print.mpw, (Print;PrintArg;PrintArgComma;PrintArgBlock)";
        scriptMap.put("Print",scriptString);
        scriptMap.put("PrintArg",scriptString);
        scriptMap.put("PrintArgComma",scriptString);
        scriptMap.put("PrintArgBlock",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"TableForm\",[_list])\n{\n  Local(i);\n  ForEach(i,list)\n  {\n    Write(i);\n    NewLine();\n  };\n  True;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/io/TableForm.mpw, (TableForm)";
        scriptMap.put("TableForm",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "Macro(\"Tell\",[_id])    {Echo(<<,@id,>>);};\nMacro(\"Tell\",[_id,_x])  {Echo(<<,@id,>>,Hold(@x),\": \",Eval(@x));};\n";
        scriptString[2] = "/org/mathpiper/scripts4/io/Tell.mpw, (Tell)";
        scriptMap.put("Tell",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "Macro(\"TellNow\",[_id])    {SysOut(\"<< \",@id,\" >>\");};\nMacro(\"TellNow\",[_id,_x])  {SysOut(\"<< \",@id,\" >> \",Hold(@x),\": \",Eval(@x));};\n";
        scriptString[2] = "/org/mathpiper/scripts4/io/TellNow.mpw, (TellNow)";
        scriptMap.put("TellNow",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"CartesianProduct\",*);\n\nCartesianProduct(xList_List?, yList_List?) <--\n{\n    Local(cartesianProduct);\n    \n    cartesianProduct := [];\n    \n    ForEach(x, xList)\n    {\n        ForEach(y, yList)\n        {\n            cartesianProduct := DestructiveAppend(cartesianProduct, [x,y]);\n        \n        };\n    };\n\n    cartesianProduct;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/linalg/CartesianProduct.mpw, (CartesianProduct)";
        scriptMap.put("CartesianProduct",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"Dimensions\",*);\n\n/* Code that returns the list of the dimensions of a tensor or matrix\n   Code submitted by Dirk Reusch.\n */\n\nLocalSymbols(x,i,n,m,aux,dim,result)\n{\n 1 # Dimensions(x_List?) <--\n    {\n      Local(i,n,m,aux,dim,result);\n      result:=List(Length(x));\n      Decide(Length(x)>?0 And? Length(Select(x, \"List?\"))=?Length(x),\n        {\n          n:=Length(x);\n              dim:=MapSingle(\"Dimensions\",x);\n              m:=Minimum(MapSingle(\"Length\",dim));\n              For(i:=1,i<=?m,i++)\n              {\n                aux:=BuildList(dim[j][i],j,1,n,1);\n                Decide(Minimum(aux)=?Maximum(aux),\n                   result:=DestructiveAppend(result,dim[1][i]),\n                   i:=m+1\n            );\n          };\n        }\n      );\n      result;\n    };\n\n 2 # Dimensions(_x) <-- List();\n};  // LocalSymbols\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/linalg/Dimensions.mpw, (Dimensions)";
        scriptMap.put("Dimensions",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nTranspose(matrix_List?)::(Length(Dimensions(matrix))>?1) <--\n{\n  Local(i,j,result);\n  result:=ZeroMatrix(Length(matrix[1]),Length(matrix));\n  For(i:=1,i<=?Length(matrix),i++)\n    For(j:=1,j<=?Length(matrix[1]),j++)\n      result[j][i]:=matrix[i][j];\n  result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/linalg/Transpose.mpw, (Transpose)";
        scriptMap.put("Transpose",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n5  # ZeroMatrix(n_NonNegativeInteger?) <--  ZeroMatrix(n,n);\n\n10 # ZeroMatrix(n_NonNegativeInteger?,m_NonNegativeInteger?) <--\n{\n  Local(i,result);\n  result:=[];\n  For(i:=1,i<=?n,i++)\n    DestructiveInsert(result,i,ZeroVector(m));\n  result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/linalg/ZeroMatrix.mpw, (ZeroMatrix)";
        scriptMap.put("ZeroMatrix",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"ZeroVector\",[_n])\n{\n    Local(i,result);\n    result:=[];\n    For(i:=1,i<=?n,i++)\n    {\n      DestructiveInsert(result,1,0);\n    };\n    result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/linalg/ZeroVector.mpw, (ZeroVector)";
        scriptMap.put("ZeroVector",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"MatrixColumn\",[_matrix,_col])\n{\n  Local(m);\n  m:=matrix[1];\n\n  Check(col >? 0, \"Argument\", \"MatrixColumn: column index out of range\");\n  Check(col <=? Length(m), \"Argument\", \"MatrixColumn: column index out of range\");\n\n  Local(i,result);\n  result:=[];\n  For(i:=1,i<=?Length(matrix),i++)\n    DestructiveAppend(result,matrix[i][col]);\n\n  result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/linalg/rowandcolumnoperations/MatrixColumn.mpw, (MatrixColumn)";
        scriptMap.put("MatrixColumn",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nMatrixColumnAugment( M_Matrix?, v_Vector? )::(Length(v)=?Dimensions(M)[2]) <--\n[\n    Decide( Not? Assigned?(iDebug), iDebug := False );\n    Decide( iDebug, Tell(MatrixColumnAugment,[M,v]) );\n    Local(mRows,nCols,newMat,ir);\n    Local(MT,MA);\n    MT := Transpose(M);\n    MT := MatrixRowStack(MT,v);\n    MA := Transpose(MT);\n];\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/linalg/rowandcolumnoperations/MatrixColumnAugment.mpw, (MatrixColumnSwap)";
        scriptMap.put("MatrixColumnSwap",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nMatrixColumnReplace( M_Matrix?, jCol_PositiveInteger?, v_Vector? )::(Length(v)=?Dimensions(M)[2]) <--\n{\n    Decide( Not? Assigned?(iDebug), iDebug := False );\n    Decide( iDebug, Tell(MatrixColumnReplace,[M,jCol,v]) );\n    Local(mRows,nCols,MT);\n    [mRows,nCols] := Dimensions(M);\n    Decide( jCol <=? nCols,\n       { MT:=Transpose(M); DestructiveReplace(MT,jCol,v); M:=Transpose(MT); }\n    );\n    M;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/linalg/rowandcolumnoperations/MatrixColumnReplace.mpw, (MatrixColumnReplace)";
        scriptMap.put("MatrixColumnReplace",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nMatrixColumnSwap( M_Matrix?, jCol1_PositiveInteger?, jCol2_PositiveInteger? )::\n                          (And?(jCol1<=?Dimensions(M)[2],jCol2<=?Dimensions(M)[2])) <--\n{\n    Decide( Not? Assigned?(iDebug), iDebug := False );\n    Decide( iDebug, Tell(MatrixColumnSwap,[M,jCol1,jCol2]) );\n    Local(MT);\n    MT := Transpose(M);\n    MT := MatrixRowSwap(MT,jCol1,jCol2);\n    M  := Transpose(MT);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/linalg/rowandcolumnoperations/MatrixColumnSwap.mpw, (MatrixColumnSwap)";
        scriptMap.put("MatrixColumnSwap",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"MatrixRow\",[_matrix,_row])\n{\n  Check(row >? 0, \"Argument\", \"MatrixRow: row index out of range\");\n  Check(row <=? Length(matrix), \"Argument\", \"MatrixRow: row index out of range\");\n\n  Local(result);\n  result:=matrix[row];\n\n  result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/linalg/rowandcolumnoperations/MatrixRow.mpw, (MatrixRow)";
        scriptMap.put("MatrixRow",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nMatrixRowReplace( M_Matrix?, iRow_PositiveInteger?, v_Vector? )::(Length(v)=?Length(M[1])) <--\n{\n    Decide( Not? Assigned?(iDebug), iDebug := False );\n    Decide( iDebug, Tell(MatrixRowReplace,[M,iRow,v]) );\n    Local(mRows,nCols);\n    [mRows,nCols] := Dimensions(M);\n    Decide( iRow <=? mRows, DestructiveReplace(M,iRow,v) );\n    M;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/linalg/rowandcolumnoperations/MatrixRowReplace.mpw, (MatrixRowReplace)";
        scriptMap.put("MatrixRowReplace",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nMatrixRowStack( M_Matrix?, v_Vector? )::(Length(v)=?Dimensions(M)[1]) <--\n{\n    Decide( Not? Assigned?(iDebug), iDebug := False );\n    Decide( iDebug, Tell(MatrixRowStack,[M,v]) );\n    Local(mRows,nCols,newMat,ir);\n    [mRows,nCols] := Dimensions(M);\n    newMat        := ZeroMatrix(mRows+1,nCols);\n    For(ir:=1,ir<?mRows+1,ir++)\n       {  MatrixRowReplace(newMat,ir,MatrixRow(M,ir));  };\n    MatrixRowReplace(newMat,mRows+1,v);\n    newMat;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/linalg/rowandcolumnoperations/MatrixRowStack.mpw, (MatrixRowStack)";
        scriptMap.put("MatrixRowStack",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nMatrixRowSwap( M_Matrix?, iRow1_PositiveInteger?, iRow2_PositiveInteger? )::\n                          (And?(iRow1<=?Dimensions(M)[1],iRow2<=?Dimensions(M)[1])) <--\n{\n    Decide( Not? Assigned?(iDebug), iDebug := False );\n    Decide( iDebug, Tell(MatrixRowSwap,[M,iRow1,iRow2]) );\n    Local(row1,row2);\n    Decide( iRow1 !=? iRow2,\n       {\n         row1 := MatrixRow(M,iRow1);         row2 := MatrixRow(M,iRow2);\n         DestructiveReplace(M,iRow1,row2);   DestructiveReplace(M,iRow2,row1);\n       }\n    );\n    M;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/linalg/rowandcolumnoperations/MatrixRowSwap.mpw, (MatrixRowSwap)";
        scriptMap.put("MatrixRowSwap",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"Append\",[_list,_element])\n{\n  Check(List?(list), \"Argument\", \"The first argument must be a list.\");\n\n  Insert(list,Length(list)+1,element);\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/Append.mpw, (Append)";
        scriptMap.put("Append",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Juan: TemplateFunction (as defined in the file \"deffunc\")\n * also makes the arguments to the function local symbols.\n * Use HoldArgumentNumber to specify the index of a variable to hold\n * (since they are defined as local symbols).\n */\n\nTemplateFunction(\"BuildList\",[_body,_var,_countfrom,_countto,_step])\n  {\n    MacroLocal(var);\n    Local(result,nr,ii);\n    result:=[];\n    nr := (countto - countfrom) / step;\n    ii := 0;\n    While( ii <=? nr )\n      {\n       MacroAssign( var, countfrom + ii * step );\n       DestructiveInsert( result,1,Eval(body) );\n       Assign(ii,AddN(ii,1));\n      };\n    DestructiveReverse(result);\n  };\nHoldArgumentNumber(\"BuildList\",5,1); /* body */\nHoldArgumentNumber(\"BuildList\",5,2); /* var */\nUnFence(\"BuildList\",5);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/BuildList.mpw, (BuildList)";
        scriptMap.put("BuildList",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"Contains?\",[_list,_element])\n{\n  Local(result);\n  Assign(result,False);\n  While(And?(Not?(result), Not?(Equal?(list, []))))\n  {\n    Decide(Equal?(First(list),element),\n      Assign(result, True),\n      Assign(list, Rest(list))\n      );\n  };\n  result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/Contains.mpw, (Contains?)";
        scriptMap.put("Contains?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"Count\",[_list,_element])\n{\n   Local(result);\n   Assign(result,0);\n   ForEach(item,list) Decide(Equal?(item, element), Assign(result,AddN(result,1)));\n   result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/Count.mpw, (Count)";
        scriptMap.put("Count",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"DestructiveAppend\",[_list,_element])\n{\n  DestructiveInsert(list,Length(list)+1,element);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/DestructiveAppend.mpw, (DestructiveAppend)";
        scriptMap.put("DestructiveAppend",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"DestructiveAppendList\",[_list,_toadd])\n{\n  Local(i,nr);\n  nr:=Length(toadd);\n  For(i:=1,i<=?nr,i++)\n  {\n    DestructiveAppend(list,toadd[i]);\n  };\n  True;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/DestructiveAppendList.mpw, (DestructiveAppendList)";
        scriptMap.put("DestructiveAppendList",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"Difference\",[_list1,_list2])\n{\n  Local(l2,index,result);\n  l2:=FlatCopy(list2);\n  result:=FlatCopy(list1);\n  ForEach(item,list1)\n  {\n    Assign(index,Find(l2,item));\n    Decide(index>?0,\n      {\n        DestructiveDelete(l2,index);\n        DestructiveDelete(result,Find(result,item));\n      }\n      );\n  };\n  result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/Difference.mpw, (Difference)";
        scriptMap.put("Difference",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* ���� Drop ���� */\n\n/* Needs to check the parameters */\n\n/*\n * Drop( list, n ) gives 'list' with its first n elements dropped\n * Drop( list, -n ) gives 'list' with its last n elements dropped\n * Drop( list, [m,n] ) gives 'list' with elements m through n dropped\n */\n\nRulebaseHoldArguments(\"Drop\", [_lst, _range]);\n\nRuleHoldArguments(\"Drop\", 2, 1, List?(range))\n    Concat(Take(lst,range[1]-1), Drop(lst, range[2]));\n\nRuleHoldArguments(\"Drop\", 2, 2, range >=? 0)\n    Decide( range =? 0 Or? lst =? [], lst, Drop( Rest(lst), range-1 ));\n\nRuleHoldArguments(\"Drop\", 2, 2, range <? 0)\n    Take( lst, Decide(AbsN(range) <? Length(lst), Length(lst) + range, 0 ) );\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/Drop.mpw, (Drop)";
        scriptMap.put("Drop",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"FillList\", [_aItem, _aLength])\n{\n  Local(i, aResult);\n  aResult:=[];\n  For(i:=0, i<?aLength, i++)\n    DestructiveInsert(aResult,1,aItem);\n  aResult;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/FillList.mpw, (FillList)";
        scriptMap.put("FillList",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # Find( list_List?, _element ) <--\n  {\n     Local(result,count);\n     result := -1;\n     count  := 1;\n     While( And?( result <? 0, Not? ( Equal? (list, []) )))\n     {\n       Decide(Equal?(First(list), element), result := count );\n       list := Rest(list);\n       count++;\n     };\n     result;\n  };\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/Find.mpw, (Find)";
        scriptMap.put("Find",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "  \n10 # FindAll( list_List?, _element ) <--\n  {\n     Local(results,count);\n     results := [];\n     count   := 1;\n     While( Not? list =? [] )\n     {\n       Decide(Equal?( First(list), element), DestructiveAppend(results,count) );\n       list := Rest(list);\n       count++;\n     };\n     results;\n  };\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/FindAll.mpw, (FindAll)";
        scriptMap.put("FindAll",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # FindFirst( list_List?, _element ) <--\n  {\n     Local(result,count);\n     result := -1;\n     count  := 1;\n     While( And?( result <? 0, Not? ( Equal? (list, []) )))\n     {\n       Decide(Equal?(First(list), element), result := count );\n       list := Rest(list);\n       count++;\n     };\n     result;\n  };\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/FindFirst.mpw, (FindFirst)";
        scriptMap.put("FindFirst",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//////////////////////////////////////////////////\n/// FuncList --- list all function atoms used in an expression\n//////////////////////////////////////////////////\n/// like VarList except collects functions\n\n10 # FuncList(expr_Atom?) <-- [];\n20 # FuncList(expr_Function?) <--\nRemoveDuplicates(\n        Concat(\n                [First(FunctionToList(expr))],\n                Apply(\"Concat\",\n                        MapSingle(\"FuncList\", Rest(FunctionToList(expr)))\n                )\n        )\n);\n\n/*\nThis is like FuncList except only looks at arguments of a given list of functions. All other functions become \"opaque\".\n\n*/\n10 # FuncList(expr_Atom?, looklist_List?) <-- [];\n// a function not in the looking list - return its type\n20 # FuncList(expr_Function?, looklist_List?)::(Not? Contains?(looklist, ToAtom(Type(expr)))) <-- [ToAtom(Type(expr))];\n// a function in the looking list - traverse its arguments\n30 # FuncList(expr_Function?, looklist_List?) <--\nRemoveDuplicates(\n        Concat(\n                [First(FunctionToList(expr))],\n                {        // gave up trying to do it using Map and MapSingle... so writing a loop now.\n                        // obtain a list of functions, considering only functions in looklist\n                        Local(item, result);\n                        result := [];\n                        ForEach(item, expr) result := Concat(result, FuncList(item, looklist));\n                        result;\n                }\n        )\n);\n\nHoldArgumentNumber(\"FuncList\", 1, 1);\nHoldArgumentNumber(\"FuncList\", 2, 1);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/FuncList.mpw, (FuncList)";
        scriptMap.put("FuncList",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* FuncListArith() is defined to only look at arithmetic operations +, -, *, /.  */\n\nFuncListArith(expr) := FuncList(expr, [ToAtom(\"+\"), ToAtom(\"-\"), *, /]);\n\nHoldArgumentNumber(\"FuncListArith\", 1, 1);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/FuncListArith.mpw, (FuncListArith)";
        scriptMap.put("FuncListArith",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"Intersection\",*);\n\n10 # Intersection( LoL_List? )::(AllSatisfy?(\"List?\",LoL)) <--\n{\n  //Decide(InVerboseMode(),Tell(\"Intersection_listOfLists\",LoL));\n  Local(nLists,L0,L1,ii,result,LI);\n  nLists := Length(LoL);\n  //Tell(\"  \",nLists);\n  Decide( nLists =? 1,\n    {\n        result := LoL[1];\n    },\n    {\n      L0 := FlatCopy(LoL[1]);\n      For( ii:=2,ii<=?nLists,ii++)\n      {\n          L1 := FlatCopy(LoL[ii]);\n          //Decide(InVerboseMode(),Tell(\"    \",[ii,L0,L1]));\n          LI := Intersection(L0,L1);\n          //Decide(InVerboseMode(),Tell(\"  -->\",LI));\n          L0 := FlatCopy(LI);\n      };\n      //Decide(InVerboseMode(),Tell(\"  result \",L0));\n      result := L0;\n    }\n  );\n  result;\n};\n  \n\n11 # Intersection(list1_List?,list2_List?) <--\n{\n    //Decide(InVerboseMode(),Tell(\"Intersection_pairOfLists\",[list1,list2]));\n    Local(l2,index,result);\n    l2:=FlatCopy(list2);\n    result:=[];\n    ForEach(item,list1)\n    {\n        Assign(index, Find(l2,item));\n        Decide(index>?0,\n          {\n             DestructiveDelete(l2,index);\n             DestructiveInsert(result,1,item);\n          }\n        );\n    };\n    DestructiveReverse(result);\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/Intersection.mpw, (Intersection)";
        scriptMap.put("Intersection",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nLast(LL_List?)::(Length(LL)>?0) <-- First(Reverse(LL));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/Last.mpw, (Last)";
        scriptMap.put("Last",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Another Macro... hack for /: to work. */\nMacro(\"MacroMapArgs\",[_expr,_oper])\n{\n  Local(ex,tl,op);\n  Assign(op,@oper);\n  Assign(ex,FunctionToList(@expr));\n  Assign(tl,Rest(ex));\n\n   ListToFunction(Concat([ex[1]],\n     `MacroMapSingle(@op,Hold(@tl)))\n   );\n};\n\nUnFence(\"MacroMapArgs\",2);\nHoldArgument(\"MacroMapArgs\",\"oper\");\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/MacroMapArgs.mpw, (MacroMapArgs)";
        scriptMap.put("MacroMapArgs",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Another Macro... hack for /: to work. */\nTemplateFunction(\"MacroMapSingle\",[_func,_list])\n{\n  Local(mapsingleresult);\n  mapsingleresult:=[];\n\n  ForEach(mapsingleitem,list)\n  {\n    DestructiveInsert(mapsingleresult,1,\n      `ApplyFast(func,[Hold(Hold(@mapsingleitem))]));\n  };\n  DestructiveReverse(mapsingleresult);\n};\nUnFence(\"MacroMapSingle\",2);\nHoldArgument(\"MacroMapSingle\",\"func\");\nHoldArgument(\"MacroMapSingle\",\"list\");\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/MacroMapSingle.mpw, (MacroMapSingle)";
        scriptMap.put("MacroMapSingle",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nLocalSymbols(func,lists,mapsingleresult,mapsingleitem)\n{\n  TemplateFunction(\"Map\",[_func,_lists])\n  {\n    Local(mapsingleresult,mapsingleitem);\n    mapsingleresult:=[];\n    lists:=Transpose(lists);\n    ForEach(mapsingleitem,lists)\n    {\n      DestructiveInsert(mapsingleresult,1,Apply(func,mapsingleitem));\n    };\n    DestructiveReverse(mapsingleresult);\n  };\n  UnFence(\"Map\",2);\n  HoldArgument(\"Map\",\"func\");\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/Map.mpw, (Map)";
        scriptMap.put("Map",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nTemplateFunction(\"MapArgs\",[_expr,_oper])\n{\n  Assign(expr,FunctionToList(expr));\n   ListToFunction(Concat([expr[1]],\n     Apply(\"MapSingle\",[oper,Rest(expr)])\n   ) );\n};\nUnFence(\"MapArgs\",2);\nHoldArgument(\"MapArgs\",\"oper\");\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/MapArgs.mpw, (MapArgs)";
        scriptMap.put("MapArgs",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nTemplateFunction(\"MapSingle\",[_func,_list])\n{\n  Local(mapsingleresult);\n  mapsingleresult:=[];\n\n  ForEach(mapsingleitem,list)\n  {\n    DestructiveInsert(mapsingleresult,1,\n      Apply(func,[mapsingleitem]));\n  };\n  DestructiveReverse(mapsingleresult);\n};\nUnFence(\"MapSingle\",2);\nHoldArgument(\"MapSingle\",\"func\");\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/MapSingle.mpw, (MapSingle)";
        scriptMap.put("MapSingle",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Partition */\n\n/* Partition( list, n ) partitions 'list' into non-overlapping sublists of length n */\n\nPartition(lst, len):=\n        Decide( Length(lst) <? len Or? len =? 0, [],\n                Concat( [Take(lst,len)], Partition(Drop(lst,len), len) ));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/Partition.mpw, (Partition)";
        scriptMap.put("Partition",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"Pop\",[_stack,_index])\n{\n  Local(result);\n  result:=stack[index];\n  DestructiveDelete(stack,index);\n  result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/Pop.mpw, (Pop)";
        scriptMap.put("Pop",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"PopBack\",[_stack])  Pop(stack,Length(stack));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/PopBack.mpw, (PopBack)";
        scriptMap.put("PopBack",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"PopFront\",[_stack]) Pop(stack,1);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/PopFront.mpw, (PopFront)";
        scriptMap.put("PopFront",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//////////////////////////////////////////////////\n/// Print a list using a padding string\n//////////////////////////////////////////////////\n\n10 # PrintList(list_List?) <-- PrintList(list, \", \");\n10 # PrintList([], padding_String?) <-- \"\";\n20 # PrintList(list_List?, padding_String?) <-- PipeToString() {\n        Local(i);\n        ForEach(i, list) {\n                Decide(Not?(Equal?(i, First(list))), WriteString(padding));\n                Decide(String?(i), WriteString(i), Decide(List?(i), WriteString(\"[\" ~ PrintList(i, padding) ~ \"]\"), Write(i)));\n        };\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/PrintList.mpw, (PrintList)";
        scriptMap.put("PrintList",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"Push\",[_stack,_element])\n{\n  DestructiveInsert(stack,1,element);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/Push.mpw, (Push)";
        scriptMap.put("Push",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRemove(list, expression) :=\n{\n   Local(result);\n   Assign(result,[]);\n   ForEach(item,list)\n   Decide(item !=? expression, DestructiveAppend(result,item));\n   result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/Remove.mpw, (Remove)";
        scriptMap.put("Remove",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"RemoveDuplicates\",[_list])\n{\n   Local(result);\n   Assign(result,[]);\n   ForEach(item,list)\n     Decide(Not?(Contains?(result,item)),DestructiveAppend(result,item));\n   result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/RemoveDuplicates.mpw, (RemoveDuplicates)";
        scriptMap.put("RemoveDuplicates",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n// Non-destructive Reverse operation\nReverse(list):=DestructiveReverse(FlatCopy(list));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/Reverse.mpw, (Reverse)";
        scriptMap.put("Reverse",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nLocalSymbols(predicate,list,result,item)\n{\n  Function(\"Select\",[_list,_predicate])\n  {\n    Local(result);\n    result:=[];\n    ForEach(item,list)\n    {\n      Decide(Apply(predicate,[item]),DestructiveAppend(result,item));\n    };\n    result;\n  };\n  HoldArgument(\"Select\",\"predicate\");\n  UnFence(\"Select\",2);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/Select.mpw, (Select)";
        scriptMap.put("Select",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"Swap\",[\"list\",_index1,_index2])\n{\n  Local(item1,item2);\n  item1:=list[index1];\n  item2:=list[index2];\n  list[index1] := item2;\n  list[index2] := item1;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/Swap.mpw, (Swap)";
        scriptMap.put("Swap",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/*  Take  */\n\n/* Needs to check the parameters */\n\n/*\n * Take( list, n ) gives the first n elements of 'list'\n * Take( list, -n ) gives the last n elements of 'list'\n * Take( list, [m,n] ) elements m through n of 'list'\n */\n\nRulebaseHoldArguments(\"Take\", [_lst, _range]);\n\nRuleHoldArguments(\"Take\", 2, 1, List?(range))\n    Take( Drop(lst, range[1] -1), range[2] - range[1] + 1);\n\nRuleHoldArguments(\"Take\", 2, 2, range >=? 0)\n    Decide( Length(lst)=?0 Or? range=?0, [],\n        Concat([First(lst)], Take(Rest(lst), range-1)));\n\nRuleHoldArguments(\"Take\", 2, 2, range <? 0)\n    Drop( lst, Decide(AbsN(range) <? Length(lst), Length(lst) + range, 0 ));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/Take.mpw, (Take)";
        scriptMap.put("Take",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"Union\",[_list1,_list2])\n{\n  RemoveDuplicates(Concat(list1,list2));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/Union.mpw, (Union)";
        scriptMap.put("Union",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* VarList: return the variables this expression depends on. */\nVarList(_expr) <-- VarList(expr,\"Variable?\");\n\nFunction(\"VarList\",[_expr,_filter])\n{\n  RemoveDuplicates(VarListAll(expr,filter));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/VarList.mpw, (VarList)";
        scriptMap.put("VarList",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/*\n * Rulebase for VarListAll: recursively traverse an expression looking\n * up all variables the expression depends on.\n */\n \n \n/* Accept any variable. */\nVarListAll(_expr) <-- VarListAll(expr,\"Variable?\");\n\n\n\n10 # VarListAll(_expr,_filter)::(Apply(filter,[expr]) =? True) <--\n     [expr];\n\n\n\n/* Otherwise check all leafs of a function. */\n20 # VarListAll(expr_Function?,_filter) <--\n{\n    Local(item,result, flatlist);\n    \n    Assign(flatlist,Rest(FunctionToList(expr)));\n    \n    Assign(result,[]);\n    \n    ForEach(item,flatlist) Assign(result,Concat(result,VarListAll(item,filter)));\n    \n    result;\n};\n\n\n\n/* Else it doesn't depend on any variable. */\n30 # VarListAll(_expr,_filter) <-- [];\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/VarListAll.mpw, (VarListAll)";
        scriptMap.put("VarListAll",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// VarListArith --- obtain arithmetic variables\n// currently the VarList(x,y) semantic is convoluted so let's introduce a new name; but in principle this needs to be cleaned up\nVarListArith(expr) := VarListSome(expr, [ToAtom(\"+\"), ToAtom(\"-\"), *, /]);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/VarListArith.mpw, (VarListArith)";
        scriptMap.put("VarListArith",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// VarListSome is just like FuncList(x,y)\n\n10 # VarListSome([], _looklist) <-- [];\n// an atom should be a variable to qualify\n10 # VarListSome(expr_Variable?, _looklist) <-- [expr];\n15 # VarListSome(expr_Atom?, _looklist) <-- [];\n// a function not in the looking list - return it whole\n20 # VarListSome(expr_Function?, looklist_List?)::(Not? Contains?(looklist, ToAtom(Type(expr)))) <-- [expr];\n// a function in the looking list - traverse its arguments\n30 # VarListSome(expr_Function?, looklist_List?) <--\nRemoveDuplicates(\n                {        // obtain a list of functions, considering only functions in looklist\n                        Local(item, result);\n                        result := [];\n                        ForEach(item, expr) result := Concat(result, VarListSome(item, looklist));\n                        result;\n                }\n);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/VarListSome.mpw, (VarListSome)";
        scriptMap.put("VarListSome",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//////////////////////////////////////////////////\n/// Global stack operations on variables\n//////////////////////////////////////////////////\n\n\nLocalSymbols(GlobalStack, x)\n{\n  GlobalStack := [];\n\n        GlobalPop(x_Atom?) <--\n        {\n                Check(Length(GlobalStack)>?0, \"Invariant\", \"GlobalPop: Error: empty GlobalStack\");\n                MacroAssign(x, PopFront(GlobalStack));\n                Eval(x);\n        };\n\n        HoldArgumentNumber(\"GlobalPop\", 1, 1);\n\n        GlobalPop() <--\n        {\n                Check(Length(GlobalStack)>?0, \"Invariant\", \"GlobalPop: Error: empty GlobalStack\");\n                PopFront(GlobalStack);\n        };\n\n        GlobalPush(_x) <--\n        {\n                Push(GlobalStack, x);\n                x;\n        };\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/lists/global_stack.mpw, (GlobalPush;GlobalPop)";
        scriptMap.put("GlobalPush",scriptString);
        scriptMap.put("GlobalPop",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n// (a or b) and (c or d) -> (a and c) or (a and d) or (b and c) or (b and d)\n20 # (list_List? AddTo _rest) <--\n{\n  Local(res);\n  res:=[];\n  ForEach(item,list)\n  {\n    res := Concat(res,item AddTo rest);\n  };\n  res;\n};\n30 # (_aitem AddTo list_List?) <--\n{\n  MapSingle([[orig],aitem And? orig],list);\n};\n40 # (_aitem AddTo _b) <-- aitem And? b;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/localrules/AddTo.mpw, (AddTo)";
        scriptMap.put("AddTo",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(\"Where\",[_left,_right]);\n\nUnFence(\"Where\",2);\n\n10 # (_body Where var_Atom? == _value) <-- \n`{ \n    //Local(@var);\n    //@var := @value;\n    //@body;\n    \n    Eval(Substitute(@var, @value) @body);\n};\n     \n     \n20 # (_body Where (_a And? _b)) <--\n{\n  Assign(body,`(@body Where @a));\n  `(@body Where @b);\n};\n\n30 # (_body Where []) <-- [];\n\n40 # (_body Where list_List?)::List?(list[1])\n     <--\n     {\n       Local(head,rest);\n       head:=First(list);\n       rest:=Rest(list);\n       rest:= `(@body Where @rest);\n       `(@body Where @head) ~ rest;\n     };\n\n50 # (_body Where list_List?)\n     <--\n     {\n       Local(head,rest);\n       While (list !=? [])\n       {\n          head:=First(list);\n          body := `(@body Where @head);\n          list:=Rest(list);\n        };\n        body;\n     };\n\n\n60 # (_body Where _var == _value) <-- Substitute(var,value)body;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/localrules/Where.mpw, (Where)";
        scriptMap.put("Where",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(\"<-\",[_left,_right]);\nHoldArgument(\"<-\",\"left\");\nHoldArgument(\"<-\",\"right\");\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/localrules/lessthan_minus_operator.mpw, (<-)";
        scriptMap.put("<-",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nLocalSymbols(localResult) {\n\n  localResult := True;\n  \n  \n  10 # LocPredicate(exp_Atom?) <--\n  {\n    Local(patternsList, result);\n    \n    patternsList := patterns;\n    \n    result := False;\n    \n    While (patternsList !=? [])\n    {\n      Decide(First(First(patternsList)) =? exp,\n      {\n        Decide(InVerboseMode() =? True,  Echo(Last(First(patternsList))));\n        \n        Assign(localResult,Eval(First(Rest(First(patternsList)))));\n        \n        result := True;\n        \n        patternsList:=[];\n      },\n      {\n        patternsList := Rest(patternsList);\n      });\n    };\n    \n    result;\n  };\n  \n  \n\n  10 # LocPredicate(exp_Function?) <--\n  {\n    Local(patternsList, result, head);\n    \n    patternsList := patterns;\n    \n    result := False;\n    \n    While (patternsList !=? [])\n    {\n      Assign(head, First(First(patternsList)));\n      \n      Decide(Not?(Atom?(head)) And? exp[0] =? head[1] And? PatternMatch?(head[2], exp),\n      {\n        Decide(InVerboseMode() =? True, Echo(Last(First(patternsList))));\n        \n        Assign(localResult, Eval(First(Rest(First(patternsList)))));\n        \n        Assign(result, True);\n        \n        Assign(patternsList, []);\n      },\n      {\n        Assign(patternsList, Rest(patternsList));\n      });\n    };\n    \n    result;\n  };\n  \n  \n  \n  20 # LocPredicate(_exp) <-- False;\n\n  \n  \n  LocChange(_exp) <-- localResult;\n  \n  \n}; // LocalSymbols(localResult)\n\nUnFence(\"LocPredicate\",1);\n\nUnFence(\"LocChange\",1);\n\n\n//============== PatternCompile ===============================\n//Convert a local rule into a rule which has its pattern replaced by a ParametersPatternMatcher.\n\n10 # PatternCompile([_pat, _post, _exp]) <-- [ [pat[0], PatternCreate(pat, post)], exp, \"UNNAMED\" ];\n\n\n20 # PatternCompile([pat_Function?, _exp]) <-- [ [pat[0], PatternCreate(pat,True)], exp, \"UNNAMED\" ];\n\n\n30 # PatternCompile([pat_Atom?, _exp]) <-- [ pat, exp, \"UNNAMED\" ];\n\n\n/*\n    40 # PatternCompile(pat_Function? <- _exp) <-- [ [pat[0],PatternCreate(pat,True)],exp ];\n    todo:tk:this rule was not handling post predicates so I replaced it with a new version that does.\n    I suspect that the other rules for this Rulebase have problems too. \n*/\n40 # PatternCompile(pat_Function? <- _exp) <-- \n{\n    Local(justPattern, postPredicate, ruleName);\n    \n    justPattern := pat;\n    \n    Decide(Type(justPattern) =? \"::\",\n        {\n            //A post predicate was submitted.\n	        justPattern := pat[1];\n	        postPredicate := pat[2];\n        },\n        {\n            //No post predicate was submitted.\n            postPredicate := True;\n        }\n    );\n            \n            \n            \n    Decide(Type(justPattern) =? \"#\",\n        {\n           //A name has been given to the rule.\n           ruleName := pat[1];\n           justPattern := pat[2];\n           \n           Decide(Function?(justPattern),\n             {\n               [ [justPattern[0], PatternCreate(justPattern, postPredicate)], exp , ruleName];\n             },\n             {\n               //Atom.\n               [ justPattern, exp, ruleName ];\n             }\n           \n           );\n        },\n        {\n           ruleName := \"UNNAMED\";\n           justPattern := pat;\n           [ [justPattern[0], PatternCreate(justPattern, postPredicate)], exp , ruleName];\n        }\n    );\n            \n};\n\n\n\n50 # PatternCompile(pat_Atom? <- _exp) <-- [ pat, exp, \"UNNAMED\" ];\n\n\n\n\n/*\n Convert\n*/\nPatternsCompile(patterns) :=\n{\n  MapSingle(\"PatternCompile\", patterns);\n};\n\n\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/localrules/localpatterns.mpw, (PatternsCompile;PatternCompile;LocPredicate;LocChange)";
        scriptMap.put("PatternsCompile",scriptString);
        scriptMap.put("PatternCompile",scriptString);
        scriptMap.put("LocPredicate",scriptString);
        scriptMap.put("LocChange",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\n\n10 # (_expression /:: _patterns) <--\n{\n  Local(old);\n  \n  Assign(patterns, PatternsCompile(patterns));\n  \n  Assign(old, expression);\n  \n  Assign(expression, MacroSubstituteApply(expression,\"LocPredicate\",\"LocChange\"));\n  \n  While (expression !=? old)\n  {\n    Assign(old, expression);\n    \n    Assign(expression, MacroSubstituteApply(expression,\"LocPredicate\",\"LocChange\"));\n  };\n  \n  expression;\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/localrules/slash_colon_colon_operator.mpw, (/::)";
        scriptMap.put("/::",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\n10 # (_expression /: _patterns) <--\n{\n  Assign(patterns, PatternsCompile(patterns));\n  \n  MacroSubstituteApply(expression,\"LocPredicate\",\"LocChange\");\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/localrules/slash_colon_operator.mpw, (/:)";
        scriptMap.put("/:",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"DisassembleExpression\",*);\n\n10 # DisassembleExpression( _expr ) <--\n{\n    Local(vars);\n    vars := MultiExpressionList( expr );\n    DisassembleExpression( expr, vars );\n};\n\n10 # DisassembleExpression( _expr, vars_List? ) <--\n{\n    Local(mexpr,func,termList,result,powers,coeffs);\n    mexpr    := MakeMultiNomial(expr,vars);\n    func     := Lambda([x,y],Decide(y!=?0,DestructiveAppend(termList,[x,y])));\n    termList := [];\n    ScanMultiNomial(func,mexpr);\n    result   := Concat([vars],Transpose(termList));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/multivar/DisassembleExpression.mpw, (DisassembleExpression)";
        scriptMap.put("DisassembleExpression",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/*\n  Groebner : Calculate the Groebner basis of a set of polynomials.\n  Nice example of its power is\n\nIn> TableForm(Groebner([x*(y-1),y*(x-1)]))\n x*y-x\n x*y-y\n y-x\n y^2-y\n\nIn> Factor(y^2-y)\nResult: y*(y-1);\n\nFrom which you can see that x = y, and x^2 = x so x is 0 or 1.\n\n*/\n\nGroebner(f_List?) <--\n{\n  Local(vars,i,j,S,nr,r);\n  nr:=Length(f);\n  vars:=VarList(f);\n  For(i:=1,i<=?nr,i++)\n  {\n    f[i] := MakeMultiNomial(f[i],vars);\n  };\n  S:=[];\n  For(i:=1,i<?nr,i++)\n  For(j:=i+1,j<=?nr,j++)\n  {\n    r := (MultiDivide(MultiS(f[i],f[j],f[i]),f)[2]);\n    Decide(NormalForm(r) !=? 0, S:= r~S);\n    f:=Concat(f,S);\n    S:=[];\n    nr:=Length(f);\n  };\n  MapSingle(\"NormalForm\",Concat(f));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/multivar/Groebner.mpw, (Groebner)";
        scriptMap.put("Groebner",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//From 0Solve5/ListTerms5.mpw, 7 Nov 2010\n\n//Retract(\"ListTerms\",*);\n//Retract(\"terms\",*);\n\n10 # ListTerms(_expr) <--\n{\n    Decide(InVerboseMode(),Tell(\"ListTerms\",expr));\n    Local(termList);\n    Local(op,x2,x3);\n    termList := [];\n    Decide(Function?(expr),\n      {\n         [op,x2,x3] := FunctionToList(expr);\n         Decide(InVerboseMode(),Tell(\"          \",[op,x2,x3])); \n         terms(op,x2,x3);\n      },\n      {\n         Push(termList,expr);\n      }\n    );\n    termList;\n};\n\n\n10 # terms(_op,_x2,_x3)::(op=?ToAtom(\"+\") Or? op=?ToAtom(\"-\")) <--\n{\n    Decide(InVerboseMode(),{Tell(\"   terms10\",op);Tell(\"         \",[x2,x3]);});\n    Local(sgn);\n    Decide(op=?ToAtom(\"+\"),sgn:=1,sgn:=-1);\n    Push(termList,sgn*x3);\n    Decide(InVerboseMode(),Tell(\"         \",termList));\n    Decide(Function?(x2),\n      {\n         Local(L);\n         L := FunctionToList(x2);\n         Decide(InVerboseMode(),Tell(\"               \",L));\n         Decide(Length(L)=?3,terms(L[1],L[2],L[3]),Push(termList,x2));\n      },\n      {\n         Push(termList,x2);\n      }\n    );\n};\nUnFence(\"terms\",3);\n\n\n20 # terms(_op,_x2,_x3) <--\n{\n    Decide(InVerboseMode(),{Tell(\"   terms20\",op);Tell(\"         \",[x2,x3]);});\n    Local(F);\n    F := ListToFunction([op,x2,x3]);\n    Push(termList,F);\n    Decide(InVerboseMode(),Tell(\"         \",termList));\n    termList;\n};\nUnFence(\"terms\",3);\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/multivar/ListTerms.mpw, (ListTerms;terms)";
        scriptMap.put("ListTerms",scriptString);
        scriptMap.put("terms",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nMM(_expr) <--  MM(expr,MultiExpressionList(expr));\nMM(_expr,_vars) <--  MakeMultiNomial(expr,vars);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/multivar/MM.mpw, (MM)";
        scriptMap.put("MM",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nMultiDivTerm(MultiNomial(_vars,_term1),MultiNomial(_vars,_term2)) <--\n{\n  Local(lm1,lm2);\n  Assign(lm1,MultiLeadingTerm(MultiNomial(vars,term1)) );\n  Assign(lm2,MultiLeadingTerm(MultiNomial(vars,term2)) );\n  CreateTerm(vars,[lm1[1]-lm2[1],lm1[2] / lm2[2]]);\n};\nMultiS(_g,_h,MultiNomial(_vars,_terms)) <--\n{\n  Local(gamma);\n\n  gamma :=Maximum(MultiDegree(g),MultiDegree(h));\n  Local(result,topterm);\n  topterm := MM(Product(vars^gamma));\n\n  result :=\n    MultiDivTerm(topterm,MultiLT(g))*g -\n    MultiDivTerm(topterm,MultiLT(h))*h;\n\n  result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/multivar/MultiDivTerm.mpw, (MultiDivTerm;MultiS)";
        scriptMap.put("MultiDivTerm",scriptString);
        scriptMap.put("MultiS",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"MultiDivide\",*);\n\n/*************************************************************\n  MultiDivide :\n  input\n    f - a multivariate polynomial\n    g[1 .. n] - a list of polynomials to divide by\n  output\n    [q[1 .. n],r] such that f = q[1]*g[1] + ... + q[n]*g[n] + r\n\n  Basically quotient and remainder after division by a group of\n  polynomials.\n\n  110709    Corrected error in if-Select statement  (hao)\n**************************************************************/\n\n20 # MultiDivide(_f,g_List?) <--\n{\n  Decide(InVerboseMode(),Tell(\"MultiDivide_1\",[f,g]));\n  Local(i,v,q,r,nr);\n  v  := MultiExpressionList(f+Sum(g));\n  f  := MakeMultiNomial(f,v);\n  nr := Length(g);\n  For(i:=1,i<=?nr,i++)\n  {\n    g[i] := MakeMultiNomial(g[i],v);\n  };\n  Decide(Not? Multi?(f),Break());\n  [q,r] := MultiDivide(f,g);\n  q     := MapSingle(\"NormalForm\",q);\n  r     := NormalForm(r);\n  [q,r];\n};\n\n\n10 # MultiDivide(f_Multi?,g_List?) <--\n{\n  Decide(InVerboseMode(),Tell(\"MultiDivide_2\",[f,g]));\n  Local(i,nr,q,r,p,v,finished);\n  nr := Length(g);\n  v  := MultiVars(f);\n  q  := FillList(0,nr);\n  r  := 0;\n  p  := f;\n  finished := MultiZero(p);\n  Local(plt,glt);\n  While (Not? finished)\n  {\n    plt := MultiLT(p);  //  MultiLT computes the multi-LeadingTerm\n    For(i:=1,i<=?nr,i++)\n    {\n      glt := MultiLT(g[i]);\n\n      If(MultiLM(glt) =? MultiLM(plt) Or? MultiTermLess([MultiLM(glt),1], [MultiLM(plt),1]))\n      {\n        //   corrected if-select statement  110708    hso\n        If(Select(MultiLM(plt)-MultiLM(glt),'[[n],n<?0]) =? [] )\n        {\n          Local(ff,ltbefore,ltafter);\n          ff := CreateTerm(v,[MultiLM(plt)-MultiLM(glt),MultiLC(plt)/MultiLC(glt)]);\n          Decide(InVerboseMode(),Tell(\"      \",NormalForm(ff)));\n          q[i] := q[i] + ff;\n          ltbefore := MultiLeadingTerm(p);\n          p := p - ff*g[i];\n          ltafter := MultiLeadingTerm(p);\n          If(ltbefore[1] =? ltafter[1])\n          {\n            ltafter := MultiLT(p);\n            p       := p-ltafter;\n          };\n//          Echo(ltbefore,MultiLeadingTerm(p));\n          i := nr + 2;\n        };\n      };\n    };\n\n    Decide(i =? nr + 1,\n      {\n        r := r + LocalSymbols(a,b)(Substitute('a,'b)plt);\n        p := p - LocalSymbols(a,b)(Substitute('a,'b)plt);\n      }\n    );\n    finished := MultiZero(p);\n  };\n  [q,r];\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/multivar/MultiDivide.mpw, (MultiDivide)";
        scriptMap.put("MultiDivide",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//TODO optimize this! keeps on converting to and from internal format!\n\n10 # MultiGcd( 0,_g) <-- g;\n10 # MultiGcd(_f, 0) <-- f;\n\n20 # MultiGcd(_f,_g) <--\n{\n  Local(v);\n  v:=MultiExpressionList(f+g);  //hier\n  NormalForm(MultiGcd(MakeMultiNomial(f,v),MakeMultiNomial(g,v)));\n};\n\n\n5 # MultiGcd(f_Multi?,g_Multi?)::(MultiTermLess([MultiLM(f),1],[MultiLM(g),1])) <--\n{\n//Echo(\"lesser\");\n  MultiGcd(g,f);\n};\n\n5  # MultiGcd(MultiNomial(_vars,_terms),g_Multi?)::(MultiLM(MultiNomial(vars,terms)) =? MultiLM(g))\n     <-- CreateTerm(vars,[FillList(0,Length(vars)),1]);\n\n5  # MultiGcd(MultiNomial(_vars,_terms),g_Multi?)::( Select(MultiLM(MultiNomial(vars,terms))-MultiLM(g), '[[n],n<?0] ) !=? [])\n     <-- CreateTerm(vars,[FillList(0,Length(vars)),1]);\n\n5  # MultiGcd(MultiNomial(_vars,_terms),g_Multi?)::(NormalForm(g) =? 0)\n     <-- CreateTerm(vars,[FillList(0,Length(vars)),1]);\n10 # MultiGcd(f_Multi?,g_Multi?) <--\n{\n  LocalSymbols(a)\n  {\n    Assign(f,Substitute('a,'a)f);\n    Assign(g,Substitute('a,'a)g);\n  };\n  Local(new);\n  While(g !=? 0)\n  {\n//Echo(\"before f\",f,NormalForm(f));\n//Echo(\"before g\",g,NormalForm(g));\n    Assign(new, MultiDivide(f,[g]));\n//Echo(\"new g\",NormalForm(new[1][1]),NormalForm(new[2]));\nDecide(new[1][1]=?0,\n{\n  g:=MakeMultiNomial(1,MultiVars(f));\n//Echo(\"PRIM \",MultiPrimitivePart(g));\n  new[2]:=0;\n});\n    Assign(new, new[2]);\n    Assign(f,g);\n    Assign(g,new);\n\n//Echo(\"after f\",f,NormalForm(f));\n//Echo(\"after g\",g,NormalForm(g));\n  };\n  MultiPrimitivePart(f);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/multivar/MultiGcd.mpw, (MultiGcd)";
        scriptMap.put("MultiGcd",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n// The basic container for multivariates\nRulebaseHoldArguments(\"MultiNomial\",[_vars,_terms]);\n\n// using the sparse tree driver for multivariate polynomials\n//LoadScriptOnce(\"multivar.rep/sparsenomial.mpi\");\n//LoadScriptOnce(\"multivar.rep/partialdensenomial.mpi\");\n\n/*Decide(Assigned?(MultiNomialDriver),\n  `LoadScriptOnce(@MultiNomialDriver),\n  LoadScriptOnce(\"multivar.rep/sparsenomial.mpi\"));\n*/\n\n// Code that can build the internal representation of a multivariate polynomial\n//LoadScriptOnce(\"multivar.rep/makemulti.mpi\");\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/multivar/MultiNomial.mpw, (MultiNomial)";
        scriptMap.put("MultiNomial",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nMultiSimp(_expr) <--\n{\n  Local(vars);\n  vars:=MultiExpressionList(expr);\n//Echo([\"step1 \",MM(expr,vars)]);\n  MultiSimp2(MM(expr,vars));\n};\n\n10 # MultiSimp2(_a / _b) <--\n{\n  Local(c1,c2,gcd,cmn,vars);\n\n\n  c1 := MultiContentTerm(a);\n  c2 := MultiContentTerm(b);\n  gcd:=Gcd(c1[2],c2[2]);\n  c1[2] := c1[2]/gcd;\n  c2[2] := c2[2]/gcd;\n\n  cmn:=Minimum(c1[1],c2[1]);\n  c1[1] := c1[1] - cmn;\n  c2[1] := c2[1] - cmn;\n\n  vars:=MultiVars(a);\n  Check(vars =? MultiVars(b), \"Argument\", \"incompatible Multivars to simplify\");\n\n  (NormalForm(CreateTerm(vars,c1))/NormalForm(CreateTerm(vars,c2)))\n    *(NormalForm(MultiPrimitivePart(a))/NormalForm(MultiPrimitivePart(b)));\n};\n\n20 # MultiSimp2(expr_Multi?) <--\n{\n  NormalForm(MultiContent(expr))*NormalForm(MultiPrimitivePart(expr));\n};\n30 # MultiSimp2(_expr) <-- expr;\n\nMultiContent(multi_Multi?)\n<--\n{\n  Local(least,gcd);\n  Assign(least, MultiDegree(multi));\n  Assign(gcd,MultiLeadingCoef(multi));\n  ScanMultiNomial(\"MultiContentScan\",multi);\n  CreateTerm(MultiVars(multi),MultiContentTerm(multi));\n};\n\nMultiContentTerm(multi_Multi?)\n<--\n{\n  Local(least,gcd);\n  Assign(least, MultiDegree(multi));\n  Assign(gcd,MultiLeadingCoef(multi));\n  ScanMultiNomial(\"MultiContentScan\",multi);\n  [least,gcd];\n};\n\nMultiContentScan(_coefs,_fact) <--\n{\n  Assign(least,Minimum([least,coefs]));\n  Assign(gcd,Gcd(gcd,fact));\n};\nUnFence(\"MultiContentScan\",2);\n\nMultiPrimitivePart(MultiNomial(vars_List?,_terms))\n<--\n{\n  Local(cont);\n  Assign(cont,MultiContentTerm(MultiNomial(vars,terms)));\n  Assign(cont,CreateTerm(vars,[-cont[1],1/Rationalize(cont[2])]));\n  MultiNomialMultiply(MultiNomial(vars,terms), cont);\n};\n\n10 # MultiRemoveGcd(x_Multi?/y_Multi?) <--\n{\n  Local(gcd);\n  Assign(gcd,MultiGcd(x,y));\n  Assign(x,MultiDivide(x,[gcd])[1][1]);\n  Assign(y,MultiDivide(y,[gcd])[1][1]);\n  x/y;\n};\n20 # MultiRemoveGcd(_x) <-- x;\n\n\n\n5 # MultiDegree(MultiNomial(_vars,_term))::(Not?(List?(term))) <-- [];\n10 # MultiDegree(MultiNomial(_vars,[])) <-- FillList(-Infinity,Length(vars));\n20 # MultiDegree(MultiNomial(_vars,_terms))\n   <-- (MultiLeadingTerm(MultiNomial(vars,terms))[1]);\n\n\n10 # MultiLeadingCoef(MultiNomial(_vars,_terms))\n   <-- (MultiLeadingTerm(MultiNomial(vars,terms))[2]);\n\n10 # MultiLeadingMono(MultiNomial(_vars,[])) <-- 0;\n20 # MultiLeadingMono(MultiNomial(_vars,_terms))\n   <-- Product(vars^(MultiDegree(MultiNomial(vars,terms))));\n\n20 # MultiLeadingTerm(_m) <-- MultiLeadingCoef(m) * MultiLeadingMono(m);\n\nMultiVars(MultiNomial(_vars,_terms)) <-- vars;\n\n20 # MultiLT(multi_Multi?)\n   <-- CreateTerm(MultiVars(multi),MultiLeadingTerm(multi));\n\n10 # MultiLM(multi_Multi?) <-- MultiDegree(multi);\n\n10 # MultiLC(MultiNomial(_vars,[])) <-- 0;\n20 # MultiLC(multi_Multi?) <-- MultiLeadingCoef(multi);\n\nDropZeroLC(multi_Multi?) <-- MultiDropLeadingZeroes(multi);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/multivar/MultiSimp.mpw, (MultiSimp;MultiSimp2;MultiContent;MultiContentTerm;MultiContentScan;MultiPrimitivePart;MultiRemoveGcd;MultiDegree;MultiDegree;MultiLeadingCoef;MultiLeadingMono;MultiLeadingTerm;MultiVars;MultiLT;MultiLM;MultiLC;DropZeroLC)";
        scriptMap.put("MultiSimp",scriptString);
        scriptMap.put("MultiSimp2",scriptString);
        scriptMap.put("MultiContent",scriptString);
        scriptMap.put("MultiContentTerm",scriptString);
        scriptMap.put("MultiContentScan",scriptString);
        scriptMap.put("MultiPrimitivePart",scriptString);
        scriptMap.put("MultiRemoveGcd",scriptString);
        scriptMap.put("MultiDegree",scriptString);
        scriptMap.put("MultiDegree",scriptString);
        scriptMap.put("MultiLeadingCoef",scriptString);
        scriptMap.put("MultiLeadingMono",scriptString);
        scriptMap.put("MultiLeadingTerm",scriptString);
        scriptMap.put("MultiVars",scriptString);
        scriptMap.put("MultiLT",scriptString);
        scriptMap.put("MultiLM",scriptString);
        scriptMap.put("MultiLC",scriptString);
        scriptMap.put("DropZeroLC",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"ReassembleListTerms\",*);\n\n10 # ReassembleListTerms( disassembly_List? ) <--\n{\n    Local(vars,lst,powers,coeffs,ii,pows,coef,term);\n    vars   := disassembly[1];\n    powers := disassembly[2];\n    coeffs := disassembly[3];\n    lst    := [];\n    For(ii:=1,ii<=?Length(powers),ii++)\n    {\n        pows := powers[ii];\n        coef := coeffs[ii];\n        //Tell(\"     \",[pows,coef]);\n        term  := coef*Product(vars^pows);\n        //Tell(\"          \",term);\n        DestructiveAppend(lst,term);\n    };\n    lst;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/multivar/ReassembleListTerms.mpw, (ReassembleListTerms)";
        scriptMap.put("ReassembleListTerms",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\n/* code pertaining to creating the internal format for multivariate\n   polynomials (the inverse of NormalForm\n\n- MultiExpressionList(x)\n  extract all variable-like sub-expressions from the main expression,\n  including functions, which can then get treated as if they were\n  a variable.\n- MultiExpression?(x)\n  determing whether 'x' can be a 'variable' for a multiNomial\n- Multi?(x)\n  returns True if 'x' is a multivariate expression in internal format.\n  Useful for transformation rules.\n */\n\nMultiExpressionList(_expr) <-- VarList(expr,\"MultiExpression?\");\n10 # MultiExpression?(_x + _y) <-- False;\n10 # MultiExpression?(_x - _y) <-- False;\n10 # MultiExpression?(   - _y) <-- False;\n10 # MultiExpression?(_x * _y) <-- False;\n10 # MultiExpression?(_x / _y) <-- False;\n10 # MultiExpression?(_x ^ y_PositiveInteger?) <-- False;\n11 # MultiExpression?(_x ^ _y)::(PositiveInteger?(Simplify(y))) <-- False;\n//10 # MultiExpression?(x_Constant?) <-- False;\n10 # MultiExpression?(x_MultiConstant?) <-- False;\n\n//TODO: shouldn't this be more global? The problem right now is\n// that Constant?/Variable? take Pi to be a constant...\nMultiConstant?(_n) <-- (VarList(n,\"IsVr\")=?[]);\n10 # IsVr(n_Number?) <-- False;\n10 # IsVr(n_Function?) <-- False;\n10 # IsVr(n_String?) <-- False;\n20 # IsVr(_n) <-- True;\n100 # MultiExpression?(_x) <-- True;\n\n10 # Multi?(MultiNomial(vars_List?,_terms)) <-- True;\n20 # Multi?(_anything) <-- False;\n\n\n\nLocalSymbols(a,vars,pow)  {\n  20 #  MultiSingleFactor(_vars,_a,_pow) <--\n    {\n      Local(term);\n      term:=[FillList(0,Length(vars)),1];\n      term[1][Find(vars,a)] := pow;\n      CreateTerm(vars,term);\n    };\n};\nLocalSymbols(x,y,vars)  {\n10 #  MakeMultiNomial(x_MultiConstant?,vars_List?) <--\n      CreateTerm(vars,[FillList(0,Length(vars)),x]);\n20 #  MakeMultiNomial(_x,vars_List?)::(Contains?(vars,x)) <-- MultiSingleFactor(vars,x,1);\n30 #  MakeMultiNomial(_x + _y,vars_List?) <--\n      MakeMultiNomial(x,vars) + MakeMultiNomial(y,vars);\n30 #  MakeMultiNomial(_x * _y,vars_List?) <--\n      MakeMultiNomial(x,vars) * MakeMultiNomial(y,vars);\n30 #  MakeMultiNomial(- _x,vars_List?) <-- -MakeMultiNomial(x,vars);\n30 #  MakeMultiNomial(_x - _y,vars_List?) <--\n      MakeMultiNomial(x,vars) - MakeMultiNomial(y,vars);\n30 #  MakeMultiNomial(MultiNomial(_vars,_terms),vars_List?)\n      <-- MultiNomial(vars,terms);\n\n// This rule would accept almost all terms, assuming them to be const.\n100 #  MakeMultiNomial(_x,vars_List?) <--\n      {\n      CreateTerm(vars,[FillList(0,Length(vars)),x]);\n      };\n\n};\n\nLocalSymbols(x,y,z,vars,gcd,a,a)  {\n  20 #  MakeMultiNomial(_x / (_y / _z),vars_List?)\n     <-- MakeMultiNomial((x*z) / y,vars_List?);\n  20 #  MakeMultiNomial((_x / _y) / _z,vars_List?)\n     <-- MakeMultiNomial((x*z) / y,vars_List?);\n  25 #  MakeMultiNomial(_x / y_Constant?,vars_List?)\n     <-- MakeMultiNomial(1/y,vars)*MakeMultiNomial(x,vars);\n  30 #  MakeMultiNomial(_x / _y,vars_List?) <--\n        {\n          Local(result);\n//Echo(\"1...\",x);\n//Echo(\"2...\",y);\n          Assign(result,MultiRemoveGcd(MakeMultiNomial(x,vars)/MakeMultiNomial(y,vars)));\n//Echo(\"3...\",result);\n          result;\n        };\n  };\n\n\nMultiNomial(_vars,_x) + MultiNomial(_vars,_y) <--\n           MultiNomialAdd(MultiNomial(vars,x), MultiNomial(vars,y));\nMultiNomial(_vars,_x) * MultiNomial(_vars,_y) <--\n           MultiNomialMultiply(MultiNomial(vars,x), MultiNomial(vars,y));\nMultiNomial(_vars,_x) - MultiNomial(_vars,_y) <--\n        MultiNomialAdd(MultiNomial(vars,x), MultiNomialNegate(MultiNomial(vars,y)));\n                      - MultiNomial(_vars,_y) <--\n        MultiNomialNegate(MultiNomial(vars,y));\nMultiNomial(_vars,_x) / MultiNomial(_vars,_x) <-- MakeMultiNomial(1, vars);\n\n\nLocalSymbols(x,n,vars)  {\n30 #  MakeMultiNomial(_x ^ n_Integer?,vars_List?)::(Contains?(vars,x)) <--\n      MultiSingleFactor(vars,x,n);\n40 #  MakeMultiNomial(_x ^ n_PositiveInteger?,vars_List?) <--\n      {\n        Local(mult,result);\n        Assign(mult,MakeMultiNomial(x,vars));\n        Assign(result,MakeMultiNomial(1,vars));\n        While(n>?0)\n        {\n          Decide(n&1 !=? 0, Assign(result, MultiNomialMultiply(result,mult)));\n          Assign(n,n>>1);\n          Decide(n!=?0,Assign(mult,MultiNomialMultiply(mult,mult)));\n        };\n        result;\n      };\n\n  15 #  MakeMultiNomial(_x ^ _n,vars_List?)::(Not?(Integer?(n)) And? Integer?(Simplify(n))) <--\n        MakeMultiNomial( x ^  Simplify(n),vars);\n\n  50 #  MakeMultiNomial(_x ^ (_n),vars_List?)::(Contains?(vars,x)) <--\n        {\n          Assign(n,Simplify(n));\n          Decide(Integer?(n),\n            MultiSingleFactor(vars,x,n),\n            MultiSingleFactor(vars,x^n,1)\n              );\n        };\n};\n\n\nx_Multi? + (y_Multi?/z_Multi?) <-- ((x*z+y)/z);\n(y_Multi?/z_Multi?) + x_Multi? <-- ((x*z+y)/z);\n(y_Multi?/z_Multi?) + (x_Multi?/w_Multi?) <-- ((y*w+x*z)/(z*w));\n(y_Multi?/z_Multi?) - (x_Multi?/w_Multi?) <-- ((y*w-x*z)/(z*w));\n(y_Multi?/z_Multi?) * (x_Multi?/w_Multi?) <-- ((y*x)/(z*w));\n(y_Multi?/z_Multi?) / (x_Multi?/w_Multi?) <-- ((y*w)/(z*x));\nx_Multi? - (y_Multi?/z_Multi?) <-- ((x*z-y)/z);\n(y_Multi?/z_Multi?) - x_Multi? <-- ((y-x*z)/z);\n(a_Multi?/(c_Multi?/b_Multi?))    <-- ((a*b)/c);\n((a_Multi?/c_Multi?)/b_Multi?)    <-- (a/(b*c));\n((a_Multi?/b_Multi?) * c_Multi?)  <-- ((a*c)/b);\n(a_Multi? * (c_Multi?/b_Multi?))  <-- ((a*c)/b);\n- ((a_Multi?)/(b_Multi?))         <-- (-a)/b;\n\n\nMultiNomialMultiply(\n     MultiNomial(_vars,_terms1)/MultiNomial(_vars,_terms2),\n     MultiNomial(_vars,_terms3)/MultiNomial(_vars,_terms4)) <--\n{\n  MultiNomialMultiply(MultiNomial(vars,terms1),MultiNomial(vars,terms3))/\n  MultiNomialMultiply(MultiNomial(vars,terms2),MultiNomial(vars,terms4));\n};\nMultiNomialMultiply(\n     MultiNomial(_vars,_terms1)/MultiNomial(_vars,_terms2),\n     MultiNomial(_vars,_terms3)) <--\n{\n  MultiNomialMultiply(MultiNomial(vars,terms1),MultiNomial(vars,terms3))/\n  MultiNomial(vars,terms2);\n};\nMultiNomialMultiply(\n     MultiNomial(_vars,_terms3),\n     MultiNomial(_vars,_terms1)/MultiNomial(_vars,_terms2)) <--\n{\n  MultiNomialMultiply(MultiNomial(vars,terms1),MultiNomial(vars,terms3))/\n  MultiNomial(vars,terms2);\n};\n\n10 # MultiNomialMultiply(_a,_b) <--\n{\n  Echo([\"ERROR!\",a,b]);\n  Echo([\"ERROR!\",Type(a),Type(b)]);\n};\n\n\n\n\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/multivar/makemulti/MakeMultiNomial.mpw, (MakeMultiNomial;MultiExpressionList;MultiExpression?;MultiConstant?;IsVr;MultiSingleFactor;Multi?;MultiNomialMultiply)";
        scriptMap.put("MakeMultiNomial",scriptString);
        scriptMap.put("MultiExpressionList",scriptString);
        scriptMap.put("MultiExpression?",scriptString);
        scriptMap.put("MultiConstant?",scriptString);
        scriptMap.put("IsVr",scriptString);
        scriptMap.put("MultiSingleFactor",scriptString);
        scriptMap.put("Multi?",scriptString);
        scriptMap.put("MultiNomialMultiply",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\n/* Implementation of MultiNomials based on sparse representation\n   in the sparsetree.mpi code. This is the real driver, using\n   the sparse trees just for representation.\n */\n//LoadScriptOnce(\"multivar.rep/sparsetree.mpi\");\n\nLocalSymbols(NormalMultiNomial) {\n\nCreateTerm(_vars,[_coefs,_fact])\n  <-- MultiNomial(vars,CreateSparseTree(coefs,fact));\n\n/************************************************************\n\nAdding and multiplying multivariate polynomials\n\n************************************************************/\nMultiNomialAdd(MultiNomial(_vars,_x), MultiNomial(_vars,_y))\n    <-- MultiNomial(vars,AddSparseTrees(Length(vars),x,y));\nMultiNomialMultiplyAdd(MultiNomial(_vars,_x), MultiNomial(_vars,_y),_coefs,_fact)\n    <-- MultiNomial(vars,MultiplyAddSparseTrees(Length(vars),x,y,coefs,fact));\nMultiNomialNegate(MultiNomial(_vars,_terms))\n    <--\n    {\n      SparseTreeMap(Hold([[coefs,list],-list]),Length(vars),terms);\n      MultiNomial(vars,terms);\n    };\nMultiNomialMultiply(MultiNomial(_vars,_x),_multi2)\n    <--\n    {\n      Local(result);\n      Assign(result,MakeMultiNomial(0,vars));\n      SparseTreeScan(\"muadm\",Length(vars),x);\n      result;\n    };\nmuadm(_coefs,_fact) <--\n{\n  Assign(result,MultiNomialMultiplyAdd(result, multi2,coefs,fact));\n};\nUnFence(\"muadm\",2);\n\n\n/* NormalForm: done as an explicit loop in stead of using SparseTreeScan\n   for speed. This routine is a lot faster!\n */\n\nRulebaseHoldArguments(\"NormalForm\",[_expression]);\nRuleHoldArguments(\"NormalForm\",1,1000,True) expression;\n0 # NormalForm(UniVariate(_var,_first,_coefs)) <--\n    ExpandUniVariate(var,first,coefs);\n10 # NormalForm(x_Multi?/y_Multi?) <-- NormalForm(x)/NormalForm(y);\n20 # NormalForm(MultiNomial(_vars,_list) )\n    <-- NormalMultiNomial(vars,list,1);\n\n\n\n\n10 # NormalMultiNomial([],_term,_prefact) <-- prefact*term;\n20 # NormalMultiNomial(_vars,_list,_prefact)\n    <--\n    {\n      Local(first,rest,result);\n      Assign(first,First(vars));\n      Assign(rest,Rest(vars));\n      Assign(result,0);\n      ForEach(item,list)\n      {\n        Assign(result,result+NormalMultiNomial(rest,item[2],prefact*first^(item[1])));\n      };\n      result;\n    };\n\n}; // LocalSymbols\n\nMultiLeadingTerm(MultiNomial(_vars,_terms))\n    <--\n    {\n      Local(coefs,fact);\n      Assign(coefs,MultiDegreeScanHead(terms,Length(vars)));\n      [coefs,fact];\n    };\n10 # MultiDegreeScanHead(_tree,0)\n   <--\n   {\n     Assign(fact,tree);\n     [];\n   };\n10 # MultiDegreeScanHead(_tree,1)\n   <--\n   {\n     Assign(fact,tree[1][2]);\n     [tree[1][1]];\n   };\n20 # MultiDegreeScanHead(_tree,_depth)\n   <--\n   {\n     (tree[1][1])~MultiDegreeScanHead(tree[1][2],depth-1);\n   };\nUnFence(\"MultiDegreeScanHead\",2);\n\nScanMultiNomial(_op,MultiNomial(vars_List?,_terms))\n    <-- SparseTreeScan(op,Length(vars),terms);\nUnFence(\"ScanMultiNomial\",2);\n\n\nMultiDropLeadingZeroes(MultiNomial(_vars,_terms))\n    <--\n    {\n      MultiDropScan(terms,Length(vars));\n      MultiNomial(vars,terms);\n    };\n10 # MultiDropScan(0,0) <-- True;\n10 # MultiDropScan([_n,0],0) <-- True;\n20 # MultiDropScan(_n,0)\n   <--\n   {\n     False;\n   };\n30 # MultiDropScan(_tree,_depth)\n   <--\n   {\n     Local(i);\n     For(i:=1,i<=?Length(tree),i++)\n     {\n       If(MultiDropScan(tree[i][2],depth-1))\n       {\n         DestructiveDelete(tree,i);\n         i--;\n       }\n       Else\n       {\n         i:=Length(tree);\n       };\n     };\n     (tree =? []);\n   };\nUnFence(\"MultiDropScan\",2);\n\n\nMultiTermLess([_deg1,_fact1],[_deg2,_fact2]) <--\n  {\n    Local(deg);\n    Assign(deg, deg1-deg2);\n    While(deg !=? [] And? First(deg) =? 0) { Assign(deg, Rest(deg));};\n\n    ((deg =? []) And? (fact1-fact2 <? 0)) Or?\n    ((deg !=? []) And? (deg[1] <? 0));\n  };\n\n20 # MultiZero(multi_Multi?) <--\n{\n  CheckMultiZero(DropZeroLC(multi));\n};\n10 # CheckMultiZero(MultiNomial(_vars,[])) <-- True;\n20 # CheckMultiZero(MultiNomial(_vars,_terms)) <-- False;\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/multivar/sparsenomial/sparsenomial.mpw, (CreateTerm;MultiNomialAdd;MultiNomialNegate;MultiNomialMultiply;NormalForm;MultiLeadingTerm;MultiDegreeScanHead;ScanMultiNomial;MultiDropLeadingZeroes;MultiDropScan;MultiTermLess;MultiZero;CheckMultiZero;MultiNomialMultiplyAdd;muadm;NormalMultiNomial)";
        scriptMap.put("CreateTerm",scriptString);
        scriptMap.put("MultiNomialAdd",scriptString);
        scriptMap.put("MultiNomialNegate",scriptString);
        scriptMap.put("MultiNomialMultiply",scriptString);
        scriptMap.put("NormalForm",scriptString);
        scriptMap.put("MultiLeadingTerm",scriptString);
        scriptMap.put("MultiDegreeScanHead",scriptString);
        scriptMap.put("ScanMultiNomial",scriptString);
        scriptMap.put("MultiDropLeadingZeroes",scriptString);
        scriptMap.put("MultiDropScan",scriptString);
        scriptMap.put("MultiTermLess",scriptString);
        scriptMap.put("MultiZero",scriptString);
        scriptMap.put("CheckMultiZero",scriptString);
        scriptMap.put("MultiNomialMultiplyAdd",scriptString);
        scriptMap.put("muadm",scriptString);
        scriptMap.put("NormalMultiNomial",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* def file definitions\nCreateSparseTree\nSparseTreeMap\nSparseTreeScan\nAddSparseTrees\nMultiplyAddSparseTrees\nSparseTreeGet\n*/\n\n/* Implementation of a sparse tree of Multidimensional matrix elements.\n*/\n\n10 # SparseTreeGet([],_tree) <-- tree;\n20 # SparseTreeGet(_key,_tree) <--\n{\n  SparseTreeGet2(Rest(key),Assoc(First(key),tree));\n};\n10 # SparseTreeGet2(_key,Empty) <-- 0;\n20 # SparseTreeGet2(_key,_item) <-- SparseTreeGet(key,First(Rest(item)));\n\n10 # SparseTreeSet([_i],_tree,_newvalue)\n   <--\n{\n  Local(Current,assoc,result);\n  Assign(assoc,Assoc(i,tree));\n  If(assoc=?Empty)\n  {\n    Assign(Current,0);\n    Assign(result,Eval(newvalue));\n    AddSparseTrees(1,tree,CreateSparseTree([i],result));\n  }\n  Else\n  {\n    Assign(Current,assoc[2]);\n    Assign(result,Eval(newvalue));\n    assoc[2] := result;\n  };\n  result;\n};\n20 # SparseTreeSet(_key,_tree,_newvalue) <--\n{\n  SparseTreeSet2(Rest(key),Assoc(First(key),tree));\n};\n10 # SparseTreeSet2(_key,Empty) <-- 0;\n20 # SparseTreeSet2(_key,_item)\n   <-- SparseTreeSet(key,First(Rest(item)),newvalue);\nUnFence(\"SparseTreeSet\",3);\nUnFence(\"SparseTreeSet2\",2);\n\n\nLocalSymbols(SparseTreeMap2,SparseTreeScan2,Muaddterm,MuMuaddterm,\n              meradd,meraddmap) {\n\n10 # CreateSparseTree([],_fact) <-- fact;\n\n20 # CreateSparseTree(_coefs,_fact)\n    <-- CreateSparseTree(First(coefs),Rest(coefs),fact);\n10 # CreateSparseTree(_first,[],_fact) <-- [[first,fact]];\n20 # CreateSparseTree(_first,_coefs,_fact)\n    <-- [[first,CreateSparseTree(First(coefs),Rest(coefs),fact)]];\n\n10 # SparseTreeMap(_op,_depth,_list) <-- SparseTreeMap2(list,depth,[]);\n10 # SparseTreeMap2(_list,1,_coefs)\n   <--\n   ForEach(item,list)\n   {\n     item[2] := ApplyFast(op,[Concat(coefs,[item[1]]),item[2]]);\n   };\n20 # SparseTreeMap2(_list,_depth,_coefs)\n   <--\n   ForEach(item,list)\n   {\n     SparseTreeMap2(item[2],AddN(depth,-1),Concat(coefs,[item[1]]));\n   };\nUnFence(\"SparseTreeMap\", 3);\n{Local(fn);fn:=ToString('SparseTreeMap2);`UnFence(@fn,3);};\n\n10 # SparseTreeScan(_op,_depth,_list) <-- SparseTreeScan2(list,depth,[]);\n10 # SparseTreeScan2(_list,0,_coefs)  <-- ApplyFast(op,[coefs,list]);\n20 # SparseTreeScan2(_list,_depth,_coefs)\n   <--\n   ForEach(item,list)\n   {\n     SparseTreeScan2(item[2],AddN(depth,-1),Concat(coefs,[item[1]]));\n   };\nUnFence(\"SparseTreeScan\", 3);\n{Local(fn);fn:=ToString('SparseTreeScan2);`UnFence(@fn,3);};\n\n\n\n5  # AddSparseTrees(0,_x,_y) <-- x+y;\n10 # AddSparseTrees(_depth,_x,_y) <--\n{\n  Local(i,t1,t2,inspt);\n  Assign(t1,x);\n  Assign(i,1);\n  Assign(t2,y);\n  Assign(inspt,[]);\n  While(t1 !=? [] And? t2 !=? [])\n  {\n    Muaddterm(First(t1),First(t2));\n  };\n  While(t2 !=? [])\n  {\n    Assign(x,DestructiveAppend(x,First(t2)));\n    Assign(t2,Rest(t2));\n  };\n  While(inspt !=? [])\n  {\n    Assign(i,First(inspt));\n    Assign(x,DestructiveInsert(x,i[2],i[1]));\n    Assign(inspt,Rest(inspt));\n  };\n  x;\n};\n\n10 # Muaddterm([_pow,_list1],[_pow,_list2]) <--\n{\n  If(depth=?1)\n    { t1[1][2] := list1+list2; }\n  Else\n    { t1[1][2] := AddSparseTrees(AddN(depth,-1),list1,list2);};\n  Assign(t2,Rest(t2));\n};\n20 # Muaddterm(_h1,_h2)::(h1[1]<?h2[1]) <--\n{\n  Assign(inspt,[h2,i]~inspt);\n  Assign(t2,Rest(t2));\n};\n30 # Muaddterm(_h1,_h2)<--\n{\n  Assign(t1,Rest(t1));\n  Assign(i,AddN(i,1));\n};\n{Local(fn);fn:=ToString('Muaddterm);`UnFence(@fn,2);};\n\n5  # MultiplyAddSparseTrees(0,_x,_y,[],_fact) <-- x+fact*y;\n10 # MultiplyAddSparseTrees(_depth,_x,_y,_coefs,_fact)\n    <--\n{\n  Local(i,t1,t2,inspt,term);\n  Assign(t1,x);\n  Assign(i,1);\n  Assign(t2,y);\n  Assign(inspt,[]);\n  While(t1 !=? [] And? t2 !=? [])\n  {\n    MuMuaddterm(First(t1),First(t2),coefs);\n  };\n\n  While(t2 !=? [])\n  {\n    Assign(term,First(t2));\n    Assign(x,DestructiveAppend(x,meradd(First(t2),coefs)));\n    Assign(t2,Rest(t2));\n  };\n  While(inspt !=? [])\n  {\n    Assign(i,First(inspt));\n    Assign(x,DestructiveInsert(x,i[2],i[1]));\n    Assign(inspt,Rest(inspt));\n  };\n  x;\n};\n\n10 # meradd([_ord,rest_List?],_coefs) <--\n{\n  Local(head);\n  Assign(head,First(coefs));\n  Assign(coefs,Rest(coefs));\n  [ord+head,meraddmap(rest,coefs)];\n};\n20 # meradd([_ord,_rest],_coefs) <--\n{\n   [ord+First(coefs),rest*fact];\n};\n\n10 # meraddmap(list_List?,_coefs) <--\n{\n  Local(result);\n  Assign(result,[]);\n  ForEach(item,list)\n  {\n    DestructiveAppend(result,meradd(item,coefs));\n  };\n  result;\n};\n{Local(fn);fn:=ToString('meradd);`UnFence(@fn,2);};\n{Local(fn);fn:=ToString('meraddmap);`UnFence(@fn,2);};\n\n10 # MuMuaddterm([_pow1,_list1],[_pow2,_list2],_coefs)::(pow1=?pow2+coefs[1]) <--\n{\n  If(depth=?1)\n    { t1[1][2] := list1+fact*list2; }\n  Else\n    {\n      t1[1] := [pow1,MultiplyAddSparseTrees(AddN(depth,-1),list1,list2,Rest(coefs),fact)];\n    };\n  Assign(t2,Rest(t2));\n};\n20 # MuMuaddterm(_h1,_h2,_coefs)::(h1[1]<?h2[1]+coefs[1]) <--\n{\n//Echo([\"inspt \",h1,h2,coefs]);\n  Assign(inspt,[meradd(First(t2),coefs),i]~inspt);\n  Assign(t2,Rest(t2));\n};\n30 # MuMuaddterm(_h1,_h2,_coefs)<--\n{\n  Assign(t1,Rest(t1));\n  Assign(i,AddN(i,1));\n};\n{Local(fn);fn:=ToString('MuMuaddterm);`UnFence(@fn,3);};\n\n\n}; // LocalSymbols\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/multivar/sparsetree/sparsetree.mpw, (CreateSparseTree;SparseTreeMap;SparseTreeScan;AddSparseTrees;MultiplyAddSparseTrees;SparseTreeGet;SparseTreeGet2;SparseTreeSet;SparseTreeSet2;SparseTreeMap2;SparseTreeScan2;Muaddterm;meradd;meraddmap;MuMuaddterm)";
        scriptMap.put("CreateSparseTree",scriptString);
        scriptMap.put("SparseTreeMap",scriptString);
        scriptMap.put("SparseTreeScan",scriptString);
        scriptMap.put("AddSparseTrees",scriptString);
        scriptMap.put("MultiplyAddSparseTrees",scriptString);
        scriptMap.put("SparseTreeGet",scriptString);
        scriptMap.put("SparseTreeGet2",scriptString);
        scriptMap.put("SparseTreeSet",scriptString);
        scriptMap.put("SparseTreeSet2",scriptString);
        scriptMap.put("SparseTreeMap2",scriptString);
        scriptMap.put("SparseTreeScan2",scriptString);
        scriptMap.put("Muaddterm",scriptString);
        scriptMap.put("meradd",scriptString);
        scriptMap.put("meraddmap",scriptString);
        scriptMap.put("MuMuaddterm",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//////////////////////////////////////////////////\n/// BracketRational: find two rational approximations\n//////////////////////////////////////////////////\n\n/// Return a list of two rational numbers r1, r2 such that r1<r<r2 and |r2-r1| < eps*|r|\nBracketRational(r,eps):=\n{\n        Local(n,cflist, r1, r2);\n        cflist := ContFracList(r);\n        n:=2;\n        r1 := ContFracEval(Take(cflist,n));\n        r2 := -r1;\n        // find two successive approximations and check that they differ by less than |eps*r|\n        While (n<?Length(cflist) And? ( Abs(NM(Eval(r2-r1))) >? Abs(NM(Eval(eps*r)) ) ) )\n        {\n                r2 := r1;\n                n++;\n                r1 := ContFracEval(Take(cflist,n));\n        };\n        // now r1 and r2 are some rational numbers.\n        // decide whether the search was successful.\n        Decide(\n                n=?Length(cflist),\n                [],        // return empty list if not enough precision\n                Decide(NM(Eval(r-r1))>?0,\n                        [r1, r2],        // successive approximations are always bracketing, we only need to decide their order\n                        [r2, r1]\n                )\n        );\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/newly/BracketRational.mpw, (BracketRational)";
        scriptMap.put("BracketRational",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//////////////////////////////////////////////////\n/// continued fractions for polynomials\n//////////////////////////////////////////////////\n\n/// main interface\n10 # ContFrac(_n) <-- ContFrac(n, 6);\n50 # ContFrac(_n,_depth) <-- ContFracEval(ContFracList(n, depth), rest);\n\n40 # ContFrac(n_CanBeUni,_depth)::(Length(VarList(n)) =? 1) <--\n{\n  ContFracDoPoly(n,depth,VarList(n)[1]);\n};\n\n5  # ContFracDoPoly(_exp,0,_var) <-- rest;\n5  # ContFracDoPoly(0,0,_var) <-- rest;\n10 # ContFracDoPoly(_exp,_depth,_var) <--\n{\n  Local(content,exp2,first,second);\n  first:=Coef(exp,var,0);\n  exp:=exp-first;\n  content:=Content(exp);\n  exp2:=DivPoly(1,PrimitivePart(exp),var,5+3*depth)-1;\n  second:=Coef(exp2,0);\n  exp2 := exp2 - second;\n  first+content/((1+second)+ContFracDoPoly(exp2,depth-1,var));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/newly/ContFrac.mpw, (ContFrac;ContFracDoPoly)";
        scriptMap.put("ContFrac",scriptString);
        scriptMap.put("ContFracDoPoly",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//////////////////////////////////////////////////\n/// ContFracEval: evaluate continued fraction from the list of coefficients\n//////////////////////////////////////////////////\n/// Each coefficient is either a single expression or a list of 2 expressions, giving the term and the numerator of the current level in the fraction.\n/// ContFracEval([[a0, b0], [a1, b1], ...]) = a0+b0/(a1+b1/(...))\n/// ContFracEval([a0, a1, ...]) = a0+1/(a1+1/(...))\n\n10 # ContFracEval([], _rest) <-- rest;\n// finish recursion here\n10 # ContFracEval([[_n, _m]], _rest) <-- n+m+rest;\n15 # ContFracEval([_n], _rest) <-- n+rest;\n/// Continued fractions with nontrivial numerators\n20 # ContFracEval(list_List?, _rest)::(List?(First(list))) <-- First(First(list)) + Rest(First(list)) / ContFracEval(Rest(list), rest);\n/// Continued fractions with unit numerators\n30 # ContFracEval(list_List?, _rest) <-- First(list) + 1 / ContFracEval(Rest(list), rest);\n\n/// evaluate continued fraction: main interface\nContFracEval(list_List?) <-- ContFracEval(list, 0);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/newly/ContFracEval.mpw, (ContFracEval)";
        scriptMap.put("ContFracEval",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/////////////////////////////////////////////////\n/// Continued fractions stuff\n/////////////////////////////////////////////////\n\n/// compute the list of continued fraction coefficients for a given number\n/// if order is not given, computes to the end\n10 # ContFracList(_n) <-- ContFracList(n, Infinity);\n/// compute list of given length\n10 # ContFracList(_n, _depth)::(depth <=? 0) <-- [];\n20 # ContFracList(n_Integer?, _depth) <-- [n];\n// prevent infinite loop when in numeric mode\n30 # ContFracList(n_Number?, _depth)::NumericMode?() <-- NonNM(ContFracList(Rationalize(n), depth));\n\n40 # ContFracList(n_Number?, _depth) <-- ContFracList(Rationalize(n), depth);\n\n/* n/m = Quotient(n,m) + 1/( m/Modulo(n,m) ) */\n35 # ContFracList((n_NegativeInteger?) / (m_Integer?), _depth) <-- Push( ContFracList(m/Modulo(n,m), depth-1) , Quotient(n,m)-1);\n\n40 # ContFracList((n_Integer?) / (m_Integer?), _depth) <-- Push( ContFracList(m/Modulo(n,m), depth-1) , Quotient(n,m));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/newly/ContFracList.mpw, (ContFracList)";
        scriptMap.put("ContFracList",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # Decimal( n_Integer? ) <-- [n,[0]];\n10 # Decimal( (n_PositiveInteger?) / (d_PositiveInteger?) ) <--\n{\n  Local(result,rev,first,period,repeat,static);\n  result:=[Quotient(n,d)];\n  Decimal(result,Modulo(n,d),d,350);\n  rev:=DecimalFindPeriod(result);\n  first:=rev[1];\n  period:=rev[2];\n  repeat:=result[first .. (first+period-1)];\n  static:=result[1 .. (first-1)];\n  DestructiveAppend(static,repeat);\n};\n20 # Decimal(_n/_m)::((n/m)<?0) <-- \"-\"~Decimal(-n/m);\n\n10 # Decimal(_result , _n , _d,_count ) <--\n{\n  While(count>?0)\n  {\n    DestructiveAppend(result,Quotient(10*n,d));\n    n:=Modulo(10*n,d);\n    count--;\n  };\n};\n\nDecimalFindPeriod(_list) <--\n{\n  Local(period,nr,reversed,first,i);\n  reversed:=Rest(DestructiveReverse(FlatCopy(Rest(list))));\n  nr:=Length(reversed)>>1;\n  period:=1;\n  first:=reversed[1];\n\n  For(i:=1,i<?nr,i++)\n  {\n    Decide(reversed[i+1] =? first And? DecimalMatches(reversed,i),\n      {\n        period:=i;\n        i:=nr;\n      }\n      );\n  };\n\n  first:=Length(list)-period;\n  While(first>?1 And? list[first] =? list[first+period]) first--;\n  first++;\n\n  [first,period];\n};\n\nDecimalMatches(_reversed,_period) <--\n{\n  Local(nr,matches,first);\n  nr:=0;\n  matches:=True;\n  first:=1;\n  While((nr<?100) And? matches)\n  {\n    matches := (matches And?\n       (reversed[first .. (first+period-1)] =? reversed[(first+period) .. (first+2*period-1)]));\n    first:=first+period;\n    nr:=nr+period;\n  };\n  matches;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/newly/Decimal.mpw, (Decimal;DecimalFindPeriod;DecimalMatches)";
        scriptMap.put("Decimal",scriptString);
        scriptMap.put("DecimalFindPeriod",scriptString);
        scriptMap.put("DecimalMatches",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n1 # FreeOf?([],_expr) <-- True;\n2 # FreeOf?(var_List?, _expr) <-- And?(FreeOf?(First(var),expr), FreeOf?(Rest(var),expr));\n\n4 # FreeOf?(_var,[]) <-- True;\n5 # FreeOf?(_var,expr_List?) <-- And?(FreeOf?(var,First(expr)), FreeOf?(var,Rest(expr)));\n\n/* Accept any variable. */\n10 # FreeOf?(_expr,_expr) <-- False;\n\n/* Otherwise check all leafs of a function. */\n11 # FreeOf?(_var,expr_Function?) <-- FreeOf?(var,Rest(FunctionToList(expr)));\n\n/* Else it doesn't depend on any variable. */\n12 # FreeOf?(_var,_expr) <-- True;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/newly/FreeOf_.mpw, (FreeOf?)";
        scriptMap.put("FreeOf?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// guess the rational number behind an imprecise number\n/// prec parameter is the max number of digits you can have in the denominator\nGuessRational(_x) <-- GuessRational(x, Floor(1/2*BuiltinPrecisionGet()));\nGuessRational(x_RationalOrNumber?, prec_Integer?) <-- {\n        Local(denomestimate, cf, i);\n        denomestimate := 1;\n        cf := ContFracList(x);\n        For(i:=2, i<=?Length(cf) And? denomestimate <? 10^prec, i++)\n                {        // estimate the denominator\n                        denomestimate := denomestimate * Decide(\n                                cf[i] =? 1,\n                                Decide(\n                                        i+2<=?Length(cf),        // have at least two more terms, do a full estimate\n                                        RoundTo(NM(Eval(cf[i]+1/(cf[i+1]+1/cf[i+2]))), 3),\n                                        // have only one more term\n                                        RoundTo(NM(Eval(cf[i]+1/cf[i+1])), 3)\n                                ),\n                                // term is not 1, use the simple estimate\n                                cf[i]\n                        );\n                };\n        Decide(denomestimate <? 10^prec,\n                //Decide(InVerboseMode(), Echo([\"GuessRational: all \", i, \"terms are within limits\"])),\n                i--        // do not use the last term\n        );\n        i--;        // loop returns one more number\n        //Decide(InVerboseMode(), Echo([\"GuessRational: using \", i, \"terms of the continued fraction\"]));\n        ContFracEval(Take(cf, i));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/newly/GuessRational.mpw, (GuessRational)";
        scriptMap.put("GuessRational",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"NearRational\",*);\n\n//////////////////////////////////////////////////\n/// NearRational, GuessRational\n//////////////////////////////////////////////////\n\n/// find rational number with smallest num./denom. near a given number x\n/// See: HAKMEM, MIT AI Memo 239, 02/29/1972, Item 101C\n\n10 # NearRational(_x) <-- NearRational(x, Floor(1/2*BuiltinPrecisionGet()));\n\n15 # NearRational(x_RationalOrNumber?, prec_Integer?) <-- \n{\n        Local(x1, x2, i,  oldprec);\n        oldprec := BuiltinPrecisionGet();\n    BuiltinPrecisionSet(prec + 8);        // 8 guard digits (?)\n        x1 := ContFracList(NM(Eval(x+10^(-prec))));\n        x2 := ContFracList(NM(Eval(x-10^(-prec))));\n\n    /*\n    Decide(InVerboseMode(),\n      [\n         Echo(\"NearRational: x      = \", NM(Eval(x           ))));\n         Echo(\"NearRational: xplus  = \", NM(Eval(x+10^(-prec)))));\n         Echo(\"NearRational: xmin   = \", NM(Eval(x-10^(-prec)))));\n         Echo(\"NearRational: Length(x1) = \", Length(x1),\" \",x1));\n         Echo(\"NearRational: Length(x2) = \", Length(x2),\" \",x1));\n      ]\n    );\n    */\n    \n        // find where the continued fractions for \"x1\" and \"x2\" differ\n        // prepare result in \"x1\" and length of result in \"i\"\n        For (i:=1, i<=?Length(x1) And? i<=?Length(x2) And? x1[i]=?x2[i], i++ ) True;\n        Decide(\n                i>?Length(x1),\n                // \"x1\" ended but matched, so use \"x2\" as \"x1\"\n                x1:=x2,\n                Decide(\n                        i>?Length(x2),\n                // \"x2\" ended but matched, so use \"x1\"\n                        True,\n                // neither \"x1\" nor \"x2\" ended and there is a mismatch at \"i\"\n                // apply recipe: select the smalest of the differing terms\n                        x1[i]:=Minimum(x1[i],x2[i])\n                )\n        );\n        // recipe: x1dd 1 to the lx1st term unless it's the lx1st in the originx1l sequence\n        //Ayal added this line, i could become bigger than Length(x1)!\n        //Decide(InVerboseMode(), Echo([\"NearRational: using \", i, \"terms of the continued fraction\"]));\n        Decide(i>?Length(x1),i:=Length(x1));\n        x1[i] := x1[i] + Decide(i=?Length(x1), 0, 1);\n        BuiltinPrecisionSet(oldprec);\n        ContFracEval(Take(x1, i));\n};\n\n\n20 # NearRational(_z, prec_Integer?)::\n      (And?(Im(z)!=?0,RationalOrNumber?(Im(z)),RationalOrNumber?(Re(z)))) <--\n{\n    Local(rr,ii);\n    rr := Re(z);\n    ii := Im(z);\n    Complex( NearRational(rr,prec), NearRational(ii,prec) );\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/newly/NearRational.mpw, (NearRational)";
        scriptMap.put("NearRational",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"NewLine\",*);\n\n10 # NewLine()  <-- WriteN(Nl(),1);\n20 # NewLine(n_PositiveInteger?) <--  WriteN(Nl(),n);\n30 # NewLine(_n) <-- Check(False, \"Argument\", \"The argument must be a positive integer  \");\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/newly/NewLine.mpw, (NewLine)";
        scriptMap.put("NewLine",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nNl():= UnicodeToString(10);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/newly/Nl.mpw, (Nl)";
        scriptMap.put("Nl",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Lagrangian power series reversion. Copied\n   from Knuth seminumerical algorithms */\n\nReversePoly(_f,_g,_var,_newvar,_degree) <--\n{\n  Local(orig,origg,G,V,W,U,n,initval,firstder,j,k,newsum);\n  orig:=MakeUni(f,var);\n  origg:=MakeUni(g,var);\n  initval:=Coef(orig,0);\n  firstder:=Coef(orig,1);\n  V:=Coef(orig,1 .. Degree(orig));\n  V:=Concat(V,FillList(0,degree));\n  G:=Coef(origg,1 .. Degree(origg));\n  G:=Concat(G,FillList(0,degree));\n  W:=FillList(0,Length(V)+2);\n  W[1]:=G[1]/firstder;\n  U:=FillList(0,Length(V)+2);\n  U[1]:=1/firstder;\n  n:=1;\n  While(n<?degree-1)\n  {\n    n++;\n    For(k:=0,k<?n-1,k++)\n    {\n      newsum:=U[k+1];\n      For(j:=2,j<=?k+1,j++)\n      {\n        newsum:=newsum-U[k+2-j]*V[j];\n      };\n      U[k+1]:=newsum/firstder;\n    };\n    newsum:=0;\n    For(k:=2,k<=?n,k++)\n    {\n      newsum:=newsum - k*U[n+1-k]*V[k];\n    };\n    U[n]:=newsum/firstder;\n    newsum:=0;\n    For(k:=1,k<=?n,k++)\n    {\n      newsum:=newsum + k*U[n+1-k]*G[k]/n;\n    };\n    W[n]:=newsum;\n  };\n  DestructiveInsert(W,1,Coef(origg,0));\n  Substitute(newvar,newvar-initval)\n    NormalForm(UniVariate(newvar,0,W));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/newly/ReversePoly.mpw, (ReversePoly)";
        scriptMap.put("ReversePoly",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nSpace() := WriteN(\" \",1);\nSpace(n):= WriteN(\" \",n);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/newly/Space.mpw, (Space)";
        scriptMap.put("Space",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nUniqueConstant() <--\n{\n  Local(result);\n  result := ToString(LocalSymbols(C)(C));\n  ToAtom(StringMidGet(2,Length(result)-1,result));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/newly/UniqueConstant.mpw, (UniqueConstant)";
        scriptMap.put("UniqueConstant",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nTemplateFunction(\"WithValue\",[_var,_val,_expr])\n{\n  Decide(List?(var),\n     ApplyFast(\"MacroLocal\",var),\n     MacroLocal(var)\n    );\n  ApplyFast(\":=\",[var,val]);\n  Eval(expr);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/newly/WithValue.mpw, (WithValue)";
        scriptMap.put("WithValue",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nWriteN(string,n) :=\n{\n  Local(i);\n  For(i:=1,i<=?n,i++) WriteString(string);\n  True;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/newly/WriteN.mpw, (WriteN)";
        scriptMap.put("WriteN",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// Return integer part of the logarithm of x in given base. Use only integer arithmetic.\n10 # IntLog(_x, _base):: (base<=?1) <-- Undefined;\n\n/// Use variable steps to speed up operation for large numbers x\n20 # IntLog(_x, _base) <--\n{\n        Local(result, step, oldstep, factor, oldfactor);\n        result := 0;\n        oldstep := step := 1;\n        oldfactor := factor := base;\n        // first loop: increase step\n        While (x >=? factor)\n        {\n                oldfactor := factor;\n                factor := factor*factor;\n                oldstep := step;\n                step := step*2;\n        };\n        Decide(x >=? base,\n          {\n                step := oldstep;\n                result := step;\n                x := Quotient(x, oldfactor);\n          },\n          step := 0\n        );\n        // second loop: decrease step\n        While (step >? 0 And? x !=? 1)\n        {\n                step := Quotient(step,2);        // for each step size down to 1, divide by factor if x is up to it\n                factor := base^step;\n                Decide(\n                        x >=? factor,\n                        {\n                                x:=Quotient(x, factor);\n                                result := result + step;\n                        }\n                );\n        };\n        result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/numbers/IntLog.mpw, (IntLog)";
        scriptMap.put("IntLog",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// obtain the real next prime number -- use primality testing\n1# NextPrime(_i) <--\n{\n        Until(Prime?(i)) i := NextPseudoPrime(i);\n        i;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/numbers/NextPrime.mpw, (NextPrime)";
        scriptMap.put("NextPrime",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// obtain next number that has good chances of being prime (not divisible by 2,3)\n1# NextPseudoPrime(i_Integer?)::(i<=?1) <-- 2;\n2# NextPseudoPrime(2) <-- 3;\n//2# NextPseudoPrime(3) <-- 5;\n3# NextPseudoPrime(i_Odd?) <--\n{\n        // this sequence generates numbers not divisible by 2 or 3\n        i := i+2;\n        Decide(Modulo(i,3)=?0, i:=i+2, i);\n/* commented out because it slows things down without a real advantage\n// this works only for odd i>=5\n        i := Decide(\n                Modulo(-i,3)=0,\n                i + 2,\n                i + 2*Modulo(-i, 3)\n        );\n        // now check if divisible by 5\n        Decide(\n                Modulo(i,5)=0,\n                NextPseudoPrime(i),\n                i\n        );\n*/\n};\n// this works only for even i>=4\n4# NextPseudoPrime(i_Even?) <-- NextPseudoPrime(i-1);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/numbers/NextPseudoPrime.mpw, (NextPseudoPrime)";
        scriptMap.put("NextPseudoPrime",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* def file definitions\nNthRoot\nNthRootCalc\nNthRootList\nNthRootSave\nNthRootRestore\nNthRootClear\n\n*/\n\n//////\n// $Id: nthroot.mpi,v 1.5 2007/05/17 11:56:45 ayalpinkus Exp $\n// calculation/simplifaction of nth roots of nonnegative integers\n// NthRoot         - interface function\n// NthRootCalc    - actually calculate/simplifies\n// NthRootList    - list table entries for a given n\n// NthRootRestore - get a root from lookup table\n// NthRootSave    - save a root in lookup table\n// NthRootClear   - clear lookup table\n//////\n\n// LocalSymbols(m,n,r,\n//                NthRootTable,\n//                NthRootCalc,\n//                NthRootList,\n//                NthRootRestore,\n//                NthRootSave,\n//                NthRootClear)\nLocalSymbols(m,n,r,\n             NthRootTable)\n{\n\n// interface function for nth root of m\n// m>=0, n>1, integers\n// m^(1/n) --> f*(r^(1/n))\nNthRoot(m_NonNegativeInteger?,n_Integer?)::(n>?1) <--\n{\n   Local(r);\n   r:=NthRootRestore(m,n);\n   Decide(Length(r)=?0,\n   {\n      r:=NthRootCalc(m,n);\n      NthRootSave(m,n,r);\n   });\n   r;\n};\n\n// internal functions\nFunction(\"NthRootCalc\",[_m,_n])\n{\n   Local(i,j,f,r,in);\n   Assign(i,2);\n   Assign(j,Ceil(FastPower(m,NM(1.0/n))+1));\n   Assign(f,1);\n   Assign(r,m);\n   // for large j (approx >4000)\n   // using Factors instead of the\n   // following.  would this be\n   // faster in general?\n//Echo(\"i j \",i,\" \",j);\n   While(LessThan?(i,j))\n   {\n      Assign(in,PowerN(i,n));\n//Echo(\"r in mod \",r, \" \",in,\" \",ModuloN(r,in));\n      While(Equal?(ModuloN(r,in),0))\n      {\n         Assign(f,MultiplyN(f,i));\n         Assign(r,QuotientN(r,in));\n      };\n      While(Equal?(ModuloN(r,i),0))   //\n         Assign(r,QuotientN(r,i));         //\n      //Assign(i,NextPrime(i));\n      Assign(i,NextPseudoPrime(i));\n      Assign(j,Ceil(FastPower(r,NM(1.0/n))+1));\n   };\n   //List(f,r);\n   List(f,QuotientN(m,PowerN(f,n))); //\n};\n\n// lookup table utilities\nFunction(\"NthRootList\",[_n])\n{\n   Decide(Length(NthRootTable)>?0,\n   {\n      Local(p,xx);\n      p:=Select(NthRootTable, '[[xx],First(xx)=?n]);\n      Decide(Length(p)=?1,Rest(p[1]),List());\n   },\n   List());\n};\n\nFunction(\"NthRootRestore\",[_m,_n])\n{\n   Local(p);\n   p:=NthRootList(n);\n   Decide(Length(p)>?0,\n   {\n      Local(r,xx);\n      r:=Select(p, '[[xx],First(xx)=?m]);\n      Decide(Length(r)=?1,First(Rest(r[1])),List());\n   },\n   List());\n};\n\nFunction(\"NthRootSave\",[_m,_n,_r])\n{\n   Local(p);\n   p:=NthRootList(n);\n   Decide(Length(p)=?0,\n   // create power list and save root\n   DestructiveInsert(NthRootTable,1,List(n,List(m,r))),\n   {\n      Local(rr,xx);\n      rr:=Select(p, '[[xx],First(xx)=?m]);\n      Decide(Length(rr)=?0,\n      {\n         // save root only\n         DestructiveAppend(p,List(m,r));\n      },\n      // already saved\n      False);\n   });\n};\n\n//TODO why is NthRootTable both lazy global and protected with LocalSymbols?\nFunction(\"NthRootClear\",[]) AssignGlobalLazy(NthRootTable,List());\n\n// create empty table\nNthRootClear();\n\n}; // LocalSymbols(m,n,r,NthRootTable);\n\n//////\n//////\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/numbers/NthRoot.mpw, (NthRoot)";
        scriptMap.put("NthRoot",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"NumberToRep\",*);\n\n\n10 # NumberToRep( N_Number? ) <--\n{\n    //Decide(InVerboseMode(),Tell(NumberToRep,N));\n    Local(oldPrec,sgn,assoc,typ,val,prec,rep);\n    oldPrec  := BuiltinPrecisionGet();\n    BuiltinPrecisionSet(300);\n    /*   NOTE: the above arbitrary 'magic number' is used because it is \n     *   currently necessary to set BuiltinPrecision to a value large \n     *   enough to handle any forseeable input.  Of course, even 300\n     *   might not be enough!  I am looking for a way to base the \n     *   setting directly on the input number itself.                   */\n     \n    sgn      := Sign(N); \n    assoc    := DumpNumber(Abs(N));\n    //Decide(InVerboseMode(),[ Tell(\"   \",assoc); Tell(\"   \",sgn); ]);\n    typ := Assoc(\"type\",assoc)[2];\n    //Decide(InVerboseMode(),Tell(\"   \",typ));\n    Decide( typ =? \"BigDecimal\",\n       {\n          rep := [ sgn*Assoc(\"unscaledValue\",assoc)[2],\n                   Assoc(\"precision\",    assoc)[2],\n                   Assoc(\"scale\",        assoc)[2] \n                 ];\n       },\n       {\n          Local(val,prec);\n          val  := Assoc(\"value\",assoc)[2];\n          prec := Length(ToString(val));\n          rep := [ sgn*val, prec ];\n       }\n    );\n    //Decide(InVerboseMode(),Tell(\"   \",rep));\n    BuiltinPrecisionSet(oldPrec);\n    rep;\n};\n\n\n\n12 # NumberToRep( N_Complex? ) <-- \n{\n    Decide(Zero?(Re(N)),\n        [NumberToRep(0.0),NumberToRep(Im(N))],\n        [NumberToRep(Re(N)),NumberToRep(Im(N))]\n    );\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/numbers/NumberToRep.mpw, (NumberToRep)";
        scriptMap.put("NumberToRep",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Returns whether n is a prime^m. */\n10 # PrimePower?(n_Prime?) <-- True;\n10 # PrimePower?(0) <-- False;\n10 # PrimePower?(1) <-- False;\n20 # PrimePower?(n_PositiveInteger?) <-- (GetPrimePower(n)[2] >? 1);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/numbers/PrimePower_.mpw, (PrimePower?)";
        scriptMap.put("PrimePower?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n2 # Prime?(_n)::(Not? Integer?(n) Or? n<=?1) <-- False;\n3 # Prime?(n_Integer?)::(n<=?FastIsPrime(0)) <-- SmallPrime?(n);\n\n/* Fast pseudoprime testing: if n is a prime, then 24 divides (n^2-1) */\n5 # Prime?(n_PositiveInteger?)::(n >? 4 And? Modulo(n^2-1,24)!=?0) <-- False;\n\n/* Determine if a number is prime, using Rabin-Miller primality\n   testing. Code submitted by Christian Obrecht\n */\n10 # Prime?(n_PositiveInteger?) <-- RabinMiller(n);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/numbers/Prime_.mpw, (Prime?)";
        scriptMap.put("Prime?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// Product of small primes <= 257. Computed only once.\nLocalSymbols(p, q)\n{\n        // p:= 1;\n        ProductPrimesTo257() := 2*3* {\n                Decide(\n                        Integer?(p),\n                        p,\n                p := Product(Select( 5 .. 257, [[q], Modulo(q^2,24)=?1 And? SmallPrime?(q)]))\n                );\n//                p;\n        };\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/numbers/ProductPrimesTo257.mpw, (ProductPrimesTo257)";
        scriptMap.put("ProductPrimesTo257",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"Rationalize\",*);\n\n10 # Rationalize(aNumber_List?) <-- Rationalize /@ aNumber;\n\n20 # Rationalize( _aNumber ) <--\n{\n    Local(result,n,d);\n    result:=SubstituteApply(aNumber,[[x],Number?(x) And? Not?(Integer?(x))],\"RationalizeNumber\");\n    Decide(InVerboseMode(),Tell(\"\",result));\n    Decide(Length(VarList(aNumber))=?0,\n      {\n        n:=Numerator(result);\n        Decide(Type(n)=?\"Numerator\",n:=result);\n        d:=Denominator(result);\n        Decide(Type(d)=?\"Denominator\",d:=1);\n        result := n*(1/d);\n      }\n    );\n    result;\n};\n";
        scriptString[2] = "/org/mathpiper/scripts4/numbers/Rationalize.mpw, (Rationalize)";
        scriptMap.put("Rationalize",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"RationalizeNumber\",[_x])\n{\n  Check(Number?(x), \"Argument\", \"RationalizeNumber: Error: \" ~ (PipeToString()Write(x)) ~\" is not a number\");\n  Local(n,i,bip,m);\n  n   := 1;\n  i   := 0;\n  bip := BuiltinPrecisionGet();\n  // We can not take for granted that the internal representation is rounded properly...\n  While(i<=?bip And? Not?(FloatIsInt?(x)))\n  {\n    n := n*10; \n    x := x*10;\n    i := i+1;\n  };\n  m := Floor(x+0.5);\n  (m/n);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/numbers/RationalizeNumber.mpw, (RationalizeNumber)";
        scriptMap.put("RationalizeNumber",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"RepToNumber\",*);\n\n\n10 # RepToNumber( rep_ListOfLists? ) <--\n{\n    //Decide(InVerboseMode(),[Tell(RepToNumberZ,rep); Tell(\"     complex\");]);\n    RepToNumber(rep[1])+I*RepToNumber(rep[2]);\n};\n\n\n12 # RepToNumber( rep_List? ) <--\n{\n    //Decide(InVerboseMode(),Tell(RepToNumber,rep));\n    Local(bigInt,precision,scale,strBI,sgn,index,ans);\n    Local(first,secnd,third,LS,numStr);\n    precision := rep[2];\n    scale     := 0;\n    bigInt    := rep[1];\n    precision := rep[2];\n    sgn       := Sign(bigInt);\n    Decide( Length(rep) >? 2, scale := rep[3] );\n    strBI     := ToString(Abs(bigInt));\n    LS        := Length(strBI);\n    //Decide(InVerboseMode(),[Tell(\"   \",[bigInt,precision,scale,sgn]);Tell(\"   \",strBI);]);\n    Decide( Length(rep)=?2,\n       { numStr := strBI; },\n       {\n           index := precision-scale;\n           first := strBI[1];\n           secnd := StringMidGet(2,LS-1,strBI);   \n           third := ToString(index-1);\n           //Decide(InVerboseMode(),Tell(\"     \",[index,first,secnd,third]));\n           If( index >? 0 )\n               {\n                   If( index <? precision )\n                       {\n                           //Decide(InVerboseMode(),Tell(\"   index < precision \"));\n                           numStr := ConcatStrings(first,\".\",secnd,\"E\",third);\n                       }\n                   Else If( index >=? precision )\n                       {\n                           //Decide(InVerboseMode(),Tell(\"   index >=? precision \"));\n                           numStr := ConcatStrings(first,\".\",secnd,\"E+\",third);\n                       };\n               }\n           Else If( index <? 0 )\n               {\n                   //Decide(InVerboseMode(),Tell(\"   index < 0 \"));\n                   numStr := ConcatStrings(first,\".\",secnd,\"E\",third);\n               }\n           Else\n               {\n                   //Decide(InVerboseMode(),Tell(\"   index = 0 \"));\n                   first  := \"0.\" ; \n                   secnd  := strBI;\n                   numStr := ConcatStrings(first,secnd);\n               };\n        }\n     );\n     ans := sgn * ToAtom(numStr);\n     //Decide(InVerboseMode(),Tell(\"     \",ans));\n     ans;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/numbers/RepToNumber.mpw, (RepToNumber)";
        scriptMap.put("RepToNumber",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"RoundToPlace\",*);\n\n10 # RoundToPlace( N_Decimal?, place_Integer? ) <--\n{\n    //Decide(InVerboseMode(),Tell(\"RoundToPlace_D\",[N,place]));\n    Local(rep,sgn,oldInt,oldPrec,oldScale,oldPlaces,strOInt,LS);\n    Local(newInt,newScale,newRep,ans);\n    sgn      := Sign(N);\n    rep      := NumberToRep( Abs(N) );\n    oldInt   := rep[1];\n    oldPrec  := rep[2];\n    oldScale := rep[3];\n    oldPlaces:= oldPrec - oldScale;\n    strOInt  := ToString(oldInt);\n    LS       := Length(strOInt);\n    //Decide(InVerboseMode(),\n    //   [\n    //    Tell(\"   \",rep);\n    //    Tell(\"         \",oldInt);\n    //    Tell(\"         \",strOInt);\n    //    Tell(\"         \",LS);\n    //    Tell(\"         \",[place,oldPrec]);\n    //    Tell(\"         \",oldPlaces);\n    //   ]\n    //);\n    Decide(oldPlaces+place>?0,\n        ans := RoundToPrecision(N,oldPlaces+place),\n        ans := 0.\n    );\n    ans;\n};\n\n\n15 # RoundToPlace( N_Integer?, place_Integer? )::(place <=? 0) <--\n{\n    //Decide(InVerboseMode(),Tell(\"RoundToPlace_I\",[N,place]));\n    Local(oldRep,oldPrec,decN,newDecN,ans);\n    oldRep   := NumberToRep(N);\n    oldPrec  := oldRep[2];\n    decN     := N*1.0;\n    newDecN  := RoundToPlace( decN, place );\n    //Decide(InVerboseMode(),Tell(\"    \",oldRep));\n    //Decide(InVerboseMode(),Tell(\"   \",oldPrec));\n    //Decide(InVerboseMode(),Tell(\"   \",place));\n    //Decide(InVerboseMode(),Tell(\"   \",newDecN));\n    Decide( place <=? oldPrec, \n        ans := Round(newDecN),\n        ans := Round(newDecN*10^(place-oldPrec))\n    );\n    ans;\n};\n\n\n\n20 # RoundToPlace( N_Complex?, place_Integer? )::(Not? Integer?(N)) <--\n{\n    //Decide(InVerboseMode(),Tell(\"RoundToPlace_C\",[N,place]));\n    Local(rr,ii);\n    rr := Re(N);\n    ii := Im(N);\n    Complex(RoundToPlace(rr,place),RoundToPlace(ii,place));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/numbers/RoundToPlace.mpw, (RoundToPlace)";
        scriptMap.put("RoundToPlace",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"RoundToPrecision\",*);\n\n10 # RoundToPrecision( N_Decimal?, newPrec_PositiveInteger? ) <--\n{\n    //Decide(InVerboseMode(),Tell(\"RoundToPrecision_D\",[N,newPrec]));\n    Local(rep,sgn,oldInt,oldPrec,oldScale,strOInt,LS,BIP0);\n    Local(newInt,newScale,newRep,ans);\n    BIP0     := BuiltinPrecisionGet();\n    sgn      := Sign(N);\n    rep      := NumberToRep( Decide(sgn<?0,-N,N) );\n    oldInt   := rep[1];\n    oldPrec  := rep[2];\n    oldScale := rep[3];\n    Decide( newPrec >? oldPrec, BuiltinPrecisionSet(newPrec) );\n    strOInt  := ToString(oldInt);\n    LS       := Length(strOInt);\n    //Decide(InVerboseMode(),\n    //   [\n    //      Tell(\"   \",rep);\n    //      Tell(\"         \",oldInt);\n    //      Tell(\"         \",strOInt);\n    //      Tell(\"         \",LS);\n    //      Tell(\"         \",[newPrec,oldPrec]);\n    //   ]\n    //);\n    \n    Local(first,secnd,rem,ad);\n    If( newPrec =? oldPrec )\n        { ans := N; }\n    Else If( newPrec <? oldPrec )\n        {\n            first    := StringMidGet(1, newPrec, strOInt); \n            secnd    := StringMidGet(newPrec+1, LS-newPrec, strOInt);\n            rem      := ToAtom(ConcatStrings(\".\",secnd));\n            ad       := Decide(rem>=?0.5, 1, 0 );\n            newInt   := sgn * ( ToAtom(first) + ad );\n            newScale := oldScale - ( oldPrec - newPrec ); \n            newRep   := [newInt,newPrec,newScale];\n            ans      := RepToNumber(newRep);\n            //Decide(InVerboseMode(),\n            //   [\n            //      Tell(\"         \",[first,secnd]);\n            //      Tell(\"         \",[rem,ad]);\n            //      Tell(\"         \",newRep);\n            //      Tell(\"         \",ans);\n            //   ]\n            //);\n        }\n    Else\n        {\n            //Decide(InVerboseMode(),Tell(\"   newPrec >? oldPrec \"));\n            Local(diffPrec);\n            diffPrec := oldPrec - newPrec;\n            newInt   := sgn * ToAtom(strOInt) * 10^(-diffPrec) ;\n            newScale := oldScale - diffPrec;\n            newRep   := [newInt,newPrec,newScale];\n            //Decide(InVerboseMode(),[Tell(\"         \",diffPrec);Tell(\"         \",newRep);]);\n            ans      := RepToNumber(newRep);\n        };\n    BuiltinPrecisionSet(BIP0);\n    ans;\n};\n\n\n15 # RoundToPrecision( N_Integer?, newPrec_PositiveInteger? ) <--\n{\n    //Decide(InVerboseMode(),Tell(\"RoundToPrecision_I\",[N,newPrec]));\n    Local(oldRep,oldPrec,decN,newDecN,ans);\n    oldRep   := NumberToRep(N);\n    oldPrec  := oldRep[2];\n    decN     := N*1.0;\n    newDecN  := RoundToPrecision( decN, newPrec );\n    //Decide(InVerboseMode(),Tell(\"    \",oldRep));\n    //Decide(InVerboseMode(),Tell(\"   \",oldPrec));\n    //Decide(InVerboseMode(),Tell(\"   \",newPrec));\n    //Decide(InVerboseMode(),Tell(\"   \",newDecN));\n    Decide( newPrec <=? oldPrec, \n        ans := Round(newDecN),\n        ans := Round(newDecN*10^(newPrec-oldPrec))\n    );\n    ans;\n};\n\n\n20 # RoundToPrecision( N_Complex?, newPrec_PositiveInteger? ) <--\n{\n    //Decide(InVerboseMode(),Tell(\"RoundToPrecision_C\",[N,newPrec]));\n    Local(rr,ii);\n    rr := Re(N);\n    ii := Im(N);\n    Complex(RoundToPrecision(rr,newPrec),RoundToPrecision(ii,newPrec));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/numbers/RoundToPrecision.mpw, (RoundToPrecision)";
        scriptMap.put("RoundToPrecision",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Returns whether n is a small by a lookup table, very fast.\nThe largest prime number in the table is returned by FastIsPrime(0). */\n\n2 # SmallPrime?(0) <-- False;\n3 # SmallPrime?(n_Integer?) <-- (FastIsPrime(n)>?0);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/numbers/SmallPrime_.mpw, (SmallPrime?)";
        scriptMap.put("SmallPrime?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # GaussianGcd(n_GaussianInteger?,m_GaussianInteger?) <--\n{\n        Decide(NM(Abs(m))=?0,n, GaussianGcd(m,n - m*Round(n/m) ) );\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/numbers/gaussianintegers/GaussianGcd.mpw, (GaussianGcd)";
        scriptMap.put("GaussianGcd",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n5  # GaussianInteger?(x_List?)        <-- False;\n\n// ?????? why is the following rule needed?\n// 5  # GaussianInteger?(ProductPrimesTo257)        <-- False;\n\n10 # GaussianInteger?(x_Complex?)          <-- (Integer?(Re(x)) And? Integer?(Im(x)));\n// to catch GaussianInteger?(x+2) from Apart\n15 # GaussianInteger?(_x)        <-- False;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/numbers/gaussianintegers/GaussianInteger_.mpw, (GaussianInteger?)";
        scriptMap.put("GaussianInteger?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//RulebaseHoldArguments(\"_\",[a]);\nRulebaseHoldArguments(\"::\",[_a,_b]);\n\nRulebaseHoldArguments(\"DefinePattern\",[_leftOperand, _rightOperand, _rulePrecedence, _postPredicate]);\n\n\n\nRuleHoldArguments(\"DefinePattern\",4,9,Equal?(Type(leftOperand),\"::\"))\n{\n    DefinePattern(leftOperand[1], rightOperand, rulePrecedence, leftOperand[2]);\n};\n\n\n\nRuleHoldArguments(\"DefinePattern\",4,10,True)\n{\n      Local(patternFlat,patternVariables, pattern, patternOperator, arity);\n      \n      Assign(patternFlat, FunctionToList(leftOperand)); //Turn the pattern into a list.\n      \n      Assign(patternVariables, Rest(patternFlat)); //Remove the function name from the list.\n      \n      Assign(patternOperator,ToString(First(patternFlat))); //Obtain the function name.\n      \n      Assign(arity,Length(patternVariables)); //Obtain the arity of the function.\n      \n      //DefLoadFunction(patternOperator);  //Load the function if it exists.\n    \n      /*\n            If the function does not exist, create it.\n      */\n      Decide(Not?(RulebaseDefined(patternOperator,arity)),\n         {\n          RulebaseEvaluateArguments(patternOperator,MakeVector(_arg,arity));\n         }\n        );\n    \n      Assign(pattern,PatternCreate(patternVariables,postPredicate));\n    \n      RulePatternEvaluateArguments(patternOperator,arity,rulePrecedence, pattern)rightOperand;\n    \n      True;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/patterns/DefinePattern.mpw, (DefinePattern)";
        scriptMap.put("DefinePattern",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(\"MakeVector\",[_vec,_dimension]);\nRuleHoldArguments(\"MakeVector\",2,1,True)\n{\n    Local(res,i);\n    res:=[];\n    i:=1;\n    Assign(dimension,AddN(dimension,1));\n    While(LessThan?(i,dimension))\n    {\n      DestructiveInsert(res,1,ToAtom(ConcatStrings(ToString(vec),ToString(i))));\n      Assign(i,AddN(i,1));\n    };\n    DestructiveReverse(res);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/patterns/MakeVector.mpw, (MakeVector)";
        scriptMap.put("MakeVector",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(\"<--\",[_leftOperand,_rightOperand]);\n\n\n\nRuleHoldArguments(\"<--\",2,1,Equal?(Type(leftOperand),\"#\"))\n{\n  DefinePattern(leftOperand[2],rightOperand,leftOperand[1],True);\n};\n\n\n\nRuleHoldArguments(\"<--\",2,2,Function?(leftOperand))\n{\n DefinePattern(leftOperand,rightOperand,0,True);\n};\n\nHoldArgument(\"<--\",\"leftOperand\");\nHoldArgument(\"<--\",\"rightOperand\");\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/patterns/lessthan_negative_negative_operator.mpw, (<--)";
        scriptMap.put("<--",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// utility function: convert options lists of the form\n/// \"[key=value, key=value]\" into a hash of the same form.\n/// The argument list is kept unevaluated using \"HoldArgumentNumber()\".\n/// Note that symbolic values of type atom are automatically converted to strings, e.g. ListToHash([a: b]) returns [[\"a\", \"b\"]]\nOptionsListToHash(list) :=\n{\n        Local(item, result);\n        result := [];\n        ForEach(item, list)\n                Decide(\n                        Function?(item) And? (Type(item) =? \":\" ) And? Atom?(item[1]),\n                        result[ToString(item[1])] := Decide(\n                                Atom?(item[2]) And? Not? Number?(item[2]) And? Not? String?(item[2]),\n                                ToString(item[2]),\n                                item[2]\n                        ),\n                        Echo([\"OptionsListToHash: Error: item \", item, \" is not of the format a: b.\"])\n                );\n        \n        result;\n};\n\nHoldArgumentNumber(\"OptionsListToHash\", 1, 1);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/plots/OptionsListToHash.mpw, (OptionsListToHash)";
        scriptMap.put("OptionsListToHash",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # RemoveRepeated([]) <-- [];\n10 # RemoveRepeated([_x]) <-- [x];\n20 # RemoveRepeated(list_List?) <-- {\n        Local(i, done);\n        done := False;\n        For(i:=0, Not? done, i++)\n        {\n                While(i<?Length(list) And? list[i]=?list[i+1])\n                        DestructiveDelete(list, i);\n                Decide(i=?Length(list), done := True);\n        };\n        list;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/plots/RemoveRepeated.mpw, (RemoveRepeated)";
        scriptMap.put("RemoveRepeated",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// service function. WriteDataItem([1,2,3], []) will output \"1 2 3\" on a separate line.\n/// Writes data points to the current output stream, omits non-numeric values.\nWriteDataItem(tuple_List?, _optionshash) <--\n{\n  Local(item);\n  Decide(        // do not write anything if one of the items is not a number\n          NumericList?(tuple),\n        ForEach(item,tuple)\n        {\n                Write(item);\n                Space();\n        }\n  );\n  NewLine();\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/plots/WriteDataItem.mpw, (WriteDataItem)";
        scriptMap.put("WriteDataItem",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//////////////////////////////////////////////////\n/// Backends for 2D plotting\n//////////////////////////////////////////////////\n\n\n/// List of all defined backends and their symbolic labels.\n/// Add any new backends here\n\nLocalSymbols(options)\n{\n    options  := [\n        [\"default\", \"data\"],\n        [\"data\", \"Plot2DData\"],\n        [\"java\", \"Plot2DJava\"],\n    [\"geogebra\", \"Plot2DGeoGebra\"],\n    [\"jfreechart\", \"Plot2DJFreeChart\"],\n];\n    \n\nPlot2DOutputs() := options;\n\n};\n\n/*\n        How backends work:\n        Plot2D<backend>(values, optionsHash)\n        optionsHash is a hash that contains all plotting options:\n        [\"xrange\"] - a list of [x1, x2], [\"xname\"] - name of the variable to plot, [\"yname\"] - array of string representations of the function(s), and perhaps other options relevant to the particular backend.\n        [values] is a list of lists of pairs of the form [[[x1, y1], [x2, y2], ...], [[x1, z1], [x2, z2], ...], ...] corresponding to the functions y(x), z(x), ... to be plotted. The abscissa points x[i] are not the same for all functions.\n        The backend should prepare the graph of the function(s). The \"datafile\" backend Plot2Ddatafile(values, optionsHash) may be used to output all data to file(s), in which case the file name should be given by the value optionsHash[\"filename\"]. Multiple files are created with names obtained by appending numbers to the filename.\n        Note that the \"data\" backend does not do anything and simply returns the data.\n        The backend Plot2Ddatafile takes care not to write \"Infinity\" or \"Undefined\" data points (it just ignores them). Custom backends should either use Plot2Ddatafile or take care of this themselves.\n*/\n\n/// trivial backend: return data list (do not confuse with Plot2Dgetdata() defined in the main code which is the middle-level plotting routine)\nPlot2DData(values_List?, _optionsHash) <-- values;\n\n/// The Java back-end generates a call-list that the Java graph plotter can handle\nPlot2DJava(values_List?, _optionsHash) <--\n{\n  Local(result,count);\n  count := 0;\n  result:=\"$plot2d:\";\n\n  result := result~\" pensize 2.0 \";\n  ForEach(function,values)\n  {\n    result := result~ColorForGraphNr(count);\n    count++;\n    result:=result~\" lines2d \"~ToString(Length(function));\n\n    function:=Select(function, Lambda([item],item[2] !=? Undefined));\n\n    ForEach(item,function)\n    {\n      result := result~\" \"~ToString(item[1])~\" \"~ToString(item[2])~\" \";\n    };\n  };\n  WriteString(result~\"$\");\n  True;\n};\n\n10 # ColorForGraphNr(0) <-- \" pencolor 64 64 128 \";\n10 # ColorForGraphNr(1) <-- \" pencolor 128 64 64 \";\n10 # ColorForGraphNr(2) <-- \" pencolor 64 128 64 \";\n20 # ColorForGraphNr(_count) <-- ColorForGraphNr(Modulo(count,3));\n\n\n\n\n//GeoGebra backend.\nPlot2DGeogebra(values_List?, _optionsHash) <--\n{\n  Local(result,count);\n  count := 0;\n  result:=\"\";\n\n\n  ForEach(function,values)\n  {\n\n    function:=Select(function, Lambda([item],item[2] !=? Undefined));\n\n    ForEach(item,function)\n    {\n      result := result~\"(\"~ToString(item[1])~\",\"~ToString(item[2])~\")\"~Nl();\n    };\n  };\n  WriteString(result);\n  True;\n};\n\n\n\n\n//JFreeChart backend.\n//Retract(\"Plot2DJFreeChart\", *);\nPlot2DJFreeChart(values_List?, _optionsHash) <--\n{\n    Local(rangeList, domainList, function, allProcessedFunctionData, lineChartCallListForm);\n    \n    \n    \n    //Remove Plot2Ds options so that they dont get passed through to LineChart();\n    ForEach(name, [\"xrange\", \"xname\", \"yname\", \"output\", \"precision\", \"points\", \"depth\"])\n    {\n        AssocDelete(optionsHash, name);\n    };\n    \n    \n    \n    //Convert [x,y] pairs into [x,x,x,...] [y,y,y,...] form.\n    allProcessedFunctionData := [];\n    \n    ForEach(function,values)\n    {\n        rangeList := [];\n        \n        domainList := [];\n        \n        function := Select(function, Lambda([item],item[2] !=? Undefined));\n        \n        ForEach(item,function)\n        {\n            rangeList := Append(rangeList, item[1]);\n          \n            domainList := Append(domainList, item[2]);\n        };\n        \n        allProcessedFunctionData := Append(allProcessedFunctionData, rangeList);\n        allProcessedFunctionData := Append(allProcessedFunctionData, domainList);\n    \n    };\n\n\n    \n    //Put LineChart() function call into list form so it can be manipulated.\n    lineChartCallListForm := [LineChart, allProcessedFunctionData ];\n    \n    \n    \n    //Add any options to the list.\n    ForEach(key, AssocIndices(optionsHash))\n    {\n        lineChartCallListForm := Append(lineChartCallListForm, Apply(\":\", [key, optionsHash[key]]));\n    };\n    \n    \n    \n    //Call the LineChart() function.\n    Eval(ListToFunction(lineChartCallListForm));\n    \n\n};\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/plots/_2d/backends.mpw, (Plot2DOutputs;Plot2DData;Plot2DJava;ColorForGraphNr;Plot2DGeogebra;Plot2DJFreeChart)";
        scriptMap.put("Plot2DOutputs",scriptString);
        scriptMap.put("Plot2DData",scriptString);
        scriptMap.put("Plot2DJava",scriptString);
        scriptMap.put("ColorForGraphNr",scriptString);
        scriptMap.put("Plot2DGeogebra",scriptString);
        scriptMap.put("Plot2DJFreeChart",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"Plot2D\", *);\n\n//////////////////////////////////////////////////\n/// Plot2D --- adaptive two-dimensional plotting\n//////////////////////////////////////////////////\n\n/// definitions of backends\n//LoadScriptOnce(\"plots.rep/backends_2d.mpi\");\n\n/*\n        Plot2D is an interface for various backends (Plot2D...). It calls\nPlot2Dgetdata to obtain the list of points and values, and then it calls\nPlot2D<backend> on that data.\n\n        Algorithm for Plot2Dgetdata:\n        1) Split the given interval into Quotient(points+3, 4) subintervals, and split each subinterval into 4 parts.\n        2) For each of the parts: evaluate function values and call Plot2Dadaptive\n        3) concatenate resulting lists and return\n*/\n\nLocalSymbols(var, func, range, option, optionslist, delta, optionshash, c, fc, allvalues)\n{\n\n// declaration of Plot2D with variable number of arguments\nFunction() Plot2D(_func);\nFunction() Plot2D(_func, _range);\nFunction() Plot2D(_func, _range, _options, ...);\n\n/// interface routines\n1 # Plot2D(_func) <-- (\"Plot2D\" @ [func, -5->5]);\n2 # Plot2D(_func, _range) <-- (\"Plot2D\" @ [func, range, []]);\n3 # Plot2D(_func, _range, option_Function?):: (Type(option) =? \":\" )  <-- (\"Plot2D\" @ [func, range, [option]]);\n\n/// Plot a single function\n5 # Plot2D(_func, _range, optionslist_List?)::(Not? List?(func)) <-- (\"Plot2D\" @ [[func], range, optionslist]);\n\n/// Top-level 2D plotting routine:\n/// plot several functions sharing the same xrange and other options\n4 # Plot2D(funclist_List?, _range, optionslist_List?) <--\n{\n        Local(var, func, delta, optionshash, c, fc, allvalues);\n        allvalues := [];\n        optionshash := \"OptionsListToHash\" @ [optionslist];\n    \n    \n        // this will be a string - name of independent variable\n        optionshash[\"xname\"] := \"\";\n        // this will be a list of strings - printed forms of functions being plotted\n        optionshash[\"yname\"] := [];\n        // parse range\n        \n        /*\n        Default plotting range is {-5->5}. Range can also be specified as {x= -5->5} (note the mandatory space separating \"{=}\" and \"{-}\");\n        currently the variable name {x} is ignored in this case.\n        Decide(\n                Type(range) =? \"==\",        // variable also specified -- ignore for now, store in options\n                {\n                        // store alternative variable name\n                        optionshash[\"xname\"] := ToString(range[1]);\n                        range := range[2];\n                }\n        );\n        Verify(PipeToString()Write(Plot2D(b,b: -1->1,output: data,points: 4)), \"[[[-1,-1],[-0.5,-0.5],[0.0,0.0],[0.5,0.5],[1,1]]]\");\n        */\n        Decide(\n                Type(range) =? \"->\",        // simple range\n                range := NM(Eval([range[1], range[2]]))\n        );\n        // set default option values\n        Decide(\n                optionshash[\"points\"] =? Empty,\n                optionshash[\"points\"] := 23\n        );\n        Decide(\n                optionshash[\"depth\"] =? Empty,\n                optionshash[\"depth\"] := 5\n        );\n        Decide(\n                optionshash[\"precision\"] =? Empty,\n                optionshash[\"precision\"] := 0.0001\n        );\n        Decide(\n                optionshash[\"output\"] =? Empty Or? String?(optionshash[\"output\"]) And? Plot2DOutputs()[optionshash[\"output\"]] =? Empty,\n                optionshash[\"output\"] := Plot2DOutputs()[\"default\"]\n        );\n        // a \"filename\" parameter is required when using data file\n        Decide(\n                optionshash[\"output\"] =? \"datafile\" And? optionshash[\"filename\"] =? Empty,\n                optionshash[\"filename\"] := \"output.data\"\n        );\n    \n        // we will divide each subinterval in 4 parts, so divide number of points by 4 now\n        optionshash[\"points\"] := NM(Eval(Quotient(optionshash[\"points\"]+3, 4)));\n    \n        // in case it is not a simple number but an unevaluated expression\n        optionshash[\"precision\"] := NM(Eval(optionshash[\"precision\"]));\n    \n        // store range in options\n        optionshash[\"xrange\"] := [range[1], range[2]];\n    \n        // compute the separation between grid points\n        delta := NM(Eval( (range[2] - range[1]) / (optionshash[\"points\"]) ));\n\n        // check that the input parameters are valid (all numbers)\n        Check(Number?(range[1]) And? Number?(range[2]) And? Number?(optionshash[\"points\"]) And? Number?(optionshash[\"precision\"]),\n                \"Argument\",\n                \"Plot2D: Error: plotting range \"\n                ~(PipeToString()Write(range))\n                ~\" and/or the number of points \"\n                ~(PipeToString()Write(optionshash[\"points\"]))\n                ~\" and/or precision \"\n                ~(PipeToString()Write(optionshash[\"precision\"]))\n                ~\" is not numeric\"\n        );\n        // loop over functions in the list\n        ForEach(func, funclist)\n        {\n                // obtain name of variable\n                var := VarList(func);        // variable name in a one-element list\n                \n                func := MetaToObject(func);\n                \n                Check(Length(var)<=?1, \"Argument\", \"Plot2D: Error: expression is not a function of one variable: \"\n                        ~(PipeToString()Write(func))\n                );\n                // Allow plotting of constant functions\n                Decide(Length(var)=?0, var:=[_dummy]);\n                // store variable name if not already done so\n                Decide(\n                        optionshash[\"xname\"] =? \"\",\n                        optionshash[\"xname\"] := ToString(VarList(var)[1])\n                );\n                // store function name in options\n                DestructiveAppend(optionshash[\"yname\"], PipeToString()Write(func));\n                \n                // compute the first point to see if it's okay\n                c := range[1];\n                \n                fc := NM(Eval(Apply([var, func], [c])));\n                \n                Check(Number?(fc) Or? fc=?Infinity Or? fc=? -Infinity Or? fc=?Undefined,\n                        \"Argument\", \n                        \"Plot2D: Error: cannot evaluate function \"\n                        ~(PipeToString()Write(func))\n                        ~\" at point \"\n                        ~(PipeToString()Write(c))\n                        ~\" to a number, instead got \"\n                        ~(PipeToString()Write(fc))\n                        ~\"\"\n                );\n                \n                // compute all other data points\n                DestructiveAppend(allvalues,  Plot2Dgetdata(func, var, c, fc, delta, optionshash) );\n        \n                Decide(InVerboseMode(), Echo([\"Plot2D: using \", Length(allvalues[Length(allvalues)]), \" points for function \", func]), True);\n        };\n    \n        // call the specified output backend\n        Plot2DOutputs()[optionshash[\"output\"]] @ [allvalues, optionshash];\n};\n\n//HoldArgument(\"Plot2D\", \"range\");\n//HoldArgument(\"Plot2D\", \"options\");\nHoldArgumentNumber(\"Plot2D\", 2, 2);\nHoldArgumentNumber(\"Plot2D\", 3, 2);\nHoldArgumentNumber(\"Plot2D\", 3, 3);\n\n\n\n//Retract(\"Plot2Dgetdata\", *);\n/// this is the middle-level plotting routine; it generates the initial\n/// grid, calls the adaptive routine, and gathers data points.\n/// func must be just one function (not a list)\nPlot2Dgetdata(_func, _var, _xinit, _yinit, _deltax, _optionshash) <--\n{\n        Local(i, a, fa, b, fb, c, fc, result);\n        // initialize list by first points (later will always use Rest() to exclude first points of subintervals)\n        result := [ [c,fc] := [xinit, yinit] ];\n        \n        For(i:=0, i<?optionshash[\"points\"], i++)\n        {\n                [a,fa] := [c, fc];        // this is to save time but here a = xinit + i*deltax\n                // build subintervals\n                [b, c] := NM(Eval([xinit + (i+1/2)*deltax, xinit + (i+1)*deltax]));        // this is not computed using \"a\" to reduce roundoff error\n                [fb, fc] := NM(Eval(MapSingle([var, func], [b, c])));\n                result := Concat(result,\n                        Rest(Plot2Dadaptive(func, var, [a,b,c], [fa, fb, fc], optionshash[\"depth\"],\n                                // since we are dividing into \"points\" subintervals, we need to relax precision\n                                optionshash[\"precision\"]*optionshash[\"points\"] )));\n        };\n    \n        result;\n};\n\n//////////////////////////////////////////////////\n/// Plot2Dadaptive --- core routine to collect data\n//////////////////////////////////////////////////\n/*\n        Plot2Dadaptive returns a list of pairs of coordinates [ [x1,y1], [x2,y2],...]\n        All arguments except f() and var must be numbers. var is a one-element list containing the independent variable. The \"a,b,c\" and \"fa, fb, fc\" arguments are values of the function that are already computed -- we don't want to recompute them once more.\n        See documentation (Algorithms.chapt.txt) for the description of the algorithm.\n*/\n\n//Retract(\"Plot2Dadaptive\", *);\nPlot2Dadaptive(_func, _var, [_a,_b,_c], [_fa, _fb, _fc], _depth, _epsilon) <--\n{\n        Local(a1, b1, fa1, fb1);\n\n        a1 := NM(Eval((a+b)/2));\n        b1 := NM(Eval((b+c)/2));\n        [fa1, fb1] := NM(Eval(MapSingle([var, func], [a1, b1])));\n        Decide(\n                depth<=?0 Or?\n                (\n                  // condition for the values not to oscillate too rapidly\n                  signchange(fa, fa1, fb) + signchange(fa1, fb, fb1) + signchange(fb, fb1, fc) <=? 2\n                  And?\n                  // condition for the values not to change too rapidly\n                  NM(Eval(Abs( (fa-5*fa1+9*fb-7*fb1+2*fc)/24 ) ))        // this is the Simpson quadrature for the (fb,fb1) subinterval (using points b,b1,c), subtracted from the 4-point Newton-Cotes quadrature for the (fb,fb1) subinterval (using points a, a1, b, b1)\n                          <=? NM(Eval( epsilon*(        // the expression here will be nonnegative because we subtract the minimum value\n                    //(fa+fc+2*fb+4*(fa1+fb1))/12        // this is 1/4 of the Simpson quadrature on the whole interval\n                        (5*fb+8*fb1-fc)/12        // this is the Simpson quadrature for the (fb,f1) subinterval\n                        - Minimum([fa,fa1,fb,fb1,fc]) ) ) )\n                ),\n                // okay, do not refine any more\n                [[a,fa], [a1,fa1], [b,fb], [b1,fb1], [c,fc]],\n                // not okay, need to refine more\n                Concat(\n                        // recursive call on two halves of the interval; relax precision by factor of 2\n                        Plot2Dadaptive(func, var, [a, a1, b], [fa, fa1, fb], depth-1, epsilon*2),        // Rest() omits the pair [b, fb]\n                        Rest(Plot2Dadaptive(func, var, [b, b1, c], [fb, fb1, fc], depth-1, epsilon*2))\n                )\n        );\n};\n\n};        // LocalSymbols()\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/plots/_2d/plot2d.mpw, (Plot2D;Plot2Dgetdata;Plot2Dadaptive)";
        scriptMap.put("Plot2D",scriptString);
        scriptMap.put("Plot2Dgetdata",scriptString);
        scriptMap.put("Plot2Dadaptive",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//////////////////////////////////////////////////\n/// Backends for 3D plotting\n//////////////////////////////////////////////////\n\n/// List of all defined backends and their symbolic labels.\n/// Add any new backends here\nPlot3DSoutputs() := [\n        [\"default\", \"data\"],\n        [\"data\", \"Plot3DSdata\"],\n];\n\n/*\n        How backends work:\n        Plot3DS<backend>(values, optionshash)\n        optionshash is a hash that contains all plotting options:\n        [\"xrange\"] - a list of [x1, x2], [\"xname\"] - name of the variable to plot, same for \"yrange\";\n        [\"zname\"] - array of string representations of the function(s), and perhaps other options relevant to the particular backend.\n        [values] is a list of lists of triples of the form [[[x1, y1, z1], [x2, y2, z2], ...], [[x1, y1, t1], [x2, y2, t2], ...], ...] corresponding to the functions z(x,y), t(x,y), ... to be plotted. The points x[i], y[i] are not necessarily the same for all functions.\n        The backend should prepare the graph of the function(s). The \"datafile\" backend Plot3DSdatafile(values, optionshash) may be used to output all data to file(s), in which case the file name should be given by the value optionshash[\"filename\"]. Multiple files are created with names obtained by appending numbers to the filename.\n        Note that the \"data\" backend does not do anything and simply returns the data.\n        The backend Plot3DSdatafile takes care not to write \"Infinity\" or \"Undefined\" data points (it just ignores them). Custom backends should either use Plot3DSdatafile to prepare a file, or take care of this themselves.\n*/\n\n/// trivial backend: return data list (do not confuse with Plot3DSgetdata() defined in the main code which is the middle-level plotting routine)\nPlot3DSdata(values_List?, _optionshash) <-- values;\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/plots/_3d/backends.mpw, (Plot3DSoutputs;Plot3DSdata)";
        scriptMap.put("Plot3DSoutputs",scriptString);
        scriptMap.put("Plot3DSdata",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//////////////////////////////////////////////////\n/// Plot3DS --- adaptive three-dimensional surface plotting\n//////////////////////////////////////////////////\n\n/// definitions of backends\n//LoadScriptOnce(\"plots.rep/backends_3d.mpi\");\n\n/*\n        Plot3DS is an interface for various backends (Plot3DS...). It calls\nPlot3DSgetdata to obtain the list of points and values, and then it calls\nPlot3DS<backend> on that data.\n\n        Algorithm for Plot3DSgetdata:\n        1) Split the given square into Quotient(Sqrt(points)+1, 2) subsquares, and split each subsquare into 4 parts.\n        2) For each of the parts: evaluate function values and call Plot3DSadaptive\n        3) concatenate resulting lists and return\n*/\n\n        LocalSymbols(var, func, xrange, yrange, option, optionslist, xdelta, ydelta, optionshash, cx, cy, fc, allvalues)\n{\n\n// declaration of Plot3DS with variable number of arguments\nFunction() Plot3DS(_func);\nFunction() Plot3DS(_func, _xrange, _yrange);\nFunction() Plot3DS(_func, _xrange, _yrange, _options, ...);\n\n\n/// interface routines\n1 # Plot3DS(_func) <-- (\"Plot3DS\" @ [func, -5->5, -5->5]);\n2 # Plot3DS(_func, _xrange, _yrange) <-- (\"Plot3DS\" @ [func, xrange, yrange, []]);\n3 # Plot3DS(_func, _xrange, _yrange, option_Function?):: (Type(option) =? \":\" ) <-- (\"Plot3DS\" @ [func, xrange, yrange, [option]]);\n\n/// Plot a single function\n5 # Plot3DS(_func, _xrange, _yrange, optionslist_List?)::(Not? List?(func)) <-- (\"Plot3DS\" @ [[func], xrange, yrange, optionslist]);\n\n/// Top-level 3D plotting routine:\n/// plot several functions sharing the same ranges and other options\n4 # Plot3DS(funclist_List?, _xrange, _yrange, optionslist_List?) <--\n{\n        Local(var, func, xdelta, ydelta, optionshash, cx, cy, fc, allvalues);\n        // this will be a list of all computed values\n        allvalues := [];\n        optionshash := \"OptionsListToHash\" @ [optionslist];\n        // this will be a string - name of independent variable\n        optionshash[\"xname\"] := \"\";\n        optionshash[\"yname\"] := \"\";\n        // this will be a list of strings - printed forms of functions being plotted\n        optionshash[\"zname\"] := [];\n        // parse range\n        /*\n        Default plotting range is {-5->5} in both coordinates.\n        A range can also be specified with a variable name, e.g. {x= -5->5} (note the mandatory space separating \"{=}\" and \"{-}\").\n        The variable name {x} should be the same as that used in the function {f(x,y)}.\n        If ranges are not given with variable names, the first variable encountered in the function {f(x,y)} is associated with the first of the two ranges.\n        Decide(\n                Type(xrange) =? \"==\",        // variable also specified -- ignore for now, store in options\n                {\n                        // store alternative variable name\n                        optionshash[\"xname\"] := ToString(xrange[1]);\n                        xrange := xrange[2];\n                }\n        );\n        Decide(\n                Type(yrange) =? \"==\" ,        // variable also specified -- ignore for now, store in options\n                {\n                        // store alternative variable name\n                        optionshash[\"yname\"] := ToString(yrange[1]);\n                        yrange := yrange[2];\n                }\n        );\n        Verify(PipeToString()Write(Plot3DS(x1,x1: -1->1,x2: -1->1,output: data,points: 2)), result);\n        */\n        Decide(\n                Type(xrange) =? \":\",        // simple range\n                xrange := NM(Eval([xrange[1], xrange[2]]))\n        );\n        Decide(\n                Type(yrange) =? \":\",        // simple range\n                yrange := NM(Eval([yrange[1], yrange[2]]))\n        );\n        // set default option values\n        Decide(\n                optionshash[\"points\"] =? Empty,\n                optionshash[\"points\"] := 10        // default # of points along each axis\n        );\n        Decide(\n                optionshash[\"xpoints\"] =? Empty,\n                optionshash[\"xpoints\"] := optionshash[\"points\"]\n        );\n        Decide(\n                optionshash[\"ypoints\"] =? Empty,\n                optionshash[\"ypoints\"] := optionshash[\"points\"]\n        );\n\n        Decide(\n                optionshash[\"depth\"] =? Empty,\n                optionshash[\"depth\"] := 2\n        );\n        Decide(\n                optionshash[\"precision\"] =? Empty,\n                optionshash[\"precision\"] := 0.0001\n        );\n        Decide(\n                optionshash[\"hidden\"] =? Empty Or? Not? Boolean?(optionshash[\"hidden\"]),\n                optionshash[\"hidden\"] := True\n        );\n        Decide(\n                optionshash[\"output\"] =? Empty Or? String?(optionshash[\"output\"]) And? Plot3DSoutputs()[optionshash[\"output\"]] =? Empty,\n                optionshash[\"output\"] := Plot3DSoutputs()[\"default\"]\n        );\n        // a \"filename\" parameter is required when using data file\n        Decide(\n                optionshash[\"output\"] =? \"datafile\" And? optionshash[\"filename\"] =? Empty,\n                optionshash[\"filename\"] := \"output.data\"\n        );\n        optionshash[\"used depth\"] := optionshash[\"depth\"];\n        // we will divide each subsquare in 4 parts, so divide number of points by 2 now\n        optionshash[\"xpoints\"] := NM(Eval(Quotient(optionshash[\"xpoints\"]+1, 2)));\n        optionshash[\"ypoints\"] := NM(Eval(Quotient(optionshash[\"ypoints\"]+1, 2)));\n        // in case it is not a simple number but an unevaluated expression\n        optionshash[\"precision\"] := NM(Eval(optionshash[\"precision\"]));\n        // store range in options\n        optionshash[\"xrange\"] := [xrange[1], xrange[2]];\n        optionshash[\"yrange\"] := [yrange[1], yrange[2]];\n        // compute the separation between grid points\n        xdelta := NM(Eval( (xrange[2] - xrange[1]) / (optionshash[\"xpoints\"]) ) );\n        ydelta := NM(Eval( (yrange[2] - yrange[1]) / (optionshash[\"ypoints\"]) ) );\n        // check that the input parameters are valid (all numbers)\n        Check(NumericList?([xrange[1], xrange[2], optionshash[\"xpoints\"], optionshash[\"ypoints\"], optionshash[\"precision\"]]),\n                \"Argument\", \n                \"Plot3DS: Error: plotting ranges \"\n                ~(PipeToString()Write(xrange, yrange))\n                ~\" and/or the number of points \"\n                ~(PipeToString()Write(optionshash[\"xpoints\"], optionshash[\"ypoints\"]))\n                ~\" and/or precision \"\n                ~(PipeToString()Write(optionshash[\"precision\"]))\n                ~\" is not numeric\"\n        );\n        // loop over functions in the list\n        ForEach(func, funclist)\n        {\n                // obtain name of variable\n                var := VarList(func);        // variable names in a list\n                \n                func := MetaToObject(func);\n                \n                Check(Length(var)<=?2, \"Argument\", \"Plot3DS: Error: expression is not a function of at most two variables: \"\n                        ~(PipeToString()Write(func))\n                );\n                // Allow plotting of constant functions\n                Decide(Length(var)=?0, var:=[_dummy, _dummy]);\n                Decide(Length(var)=?1, var:=[var[1], _dummy]);\n                // store variable name if not already done so\n                Decide(\n                        optionshash[\"xname\"] =? \"\",\n                        optionshash[\"xname\"] := ToString(var[1])\n                );\n                Decide(\n                        optionshash[\"yname\"] =? \"\",\n                        optionshash[\"yname\"] := ToString(var[2])\n                );\n                // store function name in options\n                DestructiveAppend(optionshash[\"zname\"], PipeToString()Write(func));\n                // compute the first point to see if it's okay\n                cx := xrange[1]; cy := yrange[1];\n                fc := NM(Eval(Apply([var, func], [cx, cy])));\n                Check(Number?(fc) Or? fc=?Infinity Or? fc=? -Infinity Or? fc=?Undefined,\n                        \"Argument\", \n                        \"Plot3DS: Error: cannot evaluate function \"\n                        ~(PipeToString()Write(func))\n                        ~\" at point \"\n                        ~(PipeToString()Write(cx, cy))\n                        ~\" to a number, instead got \"\n                        ~(PipeToString()Write(fc))\n                        ~\"\"\n                );\n                // compute all other data points\n                DestructiveAppend(allvalues, RemoveRepeated(HeapSort( Plot3DSgetdata(func, var, [cx, cy, fc], [xdelta, ydelta], optionshash), Hold([[x,y],x[1]<?y[1] Or? x[1] =? y[1] And? x[2] <=? y[2] ] ) )) );\n                Decide(InVerboseMode(), Echo([\"Plot3DS: using \", Length(allvalues[Length(allvalues)]), \" points for function \", func]), True);\n                Decide(InVerboseMode(), Echo([\"Plot3DS: max. used \", 2^(optionshash[\"depth\"] - optionshash[\"used depth\"]), \"subdivisions for \", func]), True);\n        };\n        // call the specified output backend\n        Plot3DSoutputs()[optionshash[\"output\"]] @ [allvalues, optionshash];\n};\n\nHoldArgumentNumber(\"Plot3DS\", 3, 2);\nHoldArgumentNumber(\"Plot3DS\", 3, 3);\nHoldArgumentNumber(\"Plot3DS\", 4, 2);\nHoldArgumentNumber(\"Plot3DS\", 4, 3);\nHoldArgumentNumber(\"Plot3DS\", 4, 4);\n\n/// this is the middle-level plotting routine; it generates the initial\n/// grid, calls the adaptive routine, and gathers data points.\n/// func must be just one function (not a list).\n/// initvalues is a list with values [x,y,f]; deltas is a list [deltax, deltay].\nPlot3DSgetdata(_func, _var, _initvalues, _deltas, _optionshash) <--\n{\n        Local(i, j, xa, ya, fa, xb, yb, fb, result, rowcache);\n        // compute all grid points in the 0th row in the y direction;\n        // store this array in a temporary cache;\n        // also store this in the final list (\"result\");\n        // then, go in the y direction and compute the 1th row; call the adaptive routine and add all points it gives along the way. update the row cache along the way.\n        // in cases when depth=0, the adaptive routine gives no extra points, and we must make sure that the \"result\" array contains the grid in exact order\n        rowcache := [initvalues];\n        For(i:=1, i<=?optionshash[\"ypoints\"], i++)\n        {\n                ya := NM(Eval(initvalues[2]+i*deltas[2]));\n                DestructiveAppend(rowcache, [initvalues[1], ya, NM(Eval(Apply([var, func], [initvalues[1], ya])))]);\n        };\n        result := rowcache;\n        // now loop over the x direction\n        For(i:=1, i<=?optionshash[\"xpoints\"], i++)\n        {\n                // start the next row\n                // the 0th point\n                xa := NM(Eval(initvalues[1]+i*deltas[1]));\n                ya := initvalues[2];\n                fa := NM(Eval(Apply([var, func], [xa, ya])));\n                DestructiveAppend(result, [xa, ya, fa]);\n                // now loop at each grid point in y direction\n                For(j:=1, j<=?optionshash[\"ypoints\"], j++)\n                {        // now we need to plot the data inside the following square:\n                        //        p  b\n                        //        r  a\n                        // xa, ya, fa are the values at the point a; the points p and r are stored as rowcache[j+1] and rowcache[j]. We just need to compute the point q and update the rowcache with the value at point a, and update xa, ya, fa also with b.\n                        yb := NM(Eval(initvalues[2] + j*deltas[2]));\n                        fb := NM(Eval(Apply([var, func], [xa, yb])));\n                        result := Concat(result, Plot3DSadaptive(func, var, [rowcache[j][1], ya, xa, yb, rowcache[j][3], rowcache[j+1][3], fa, fb], optionshash[\"depth\"],\n                                // since we are dividing into \"points\" subintervals, we need to relax precision\n                                optionshash[\"precision\"] * optionshash[\"xpoints\"] * optionshash[\"ypoints\"], optionshash ));\n                        // update rowcache\n                        rowcache[j] := [xa, ya, fa];\n                        ya := yb;\n                        fa := fb;\n                        DestructiveAppend(result, [xa, ya, fa]);\n                };\n        };\n\n        result;\n};\n\n//////////////////////////////////////////////////\n/// Plot3DSadaptive --- core routine to collect data\n//////////////////////////////////////////////////\n/*\n        Plot3DSadaptive returns a list of triples of coordinates [ [x1,y1,z1], [x2,y2,z2],...] inside a given square. The corners of the square are not returned (they are already computed).\n        All arguments except f() and var must be numbers. var is a two-element list containing the independent variables. The \"square\" argument contains the values of the function that have been already computed -- we don't want to recompute them once more.\n        square = [x1, y1, x2, y2, f11, f12, f21, f22]\n\n        So the routine will return the list f13, f31, f33, f32, f23 and any points returned by recursive calls on subsquares.\n        See the Algo book for the description of the algorithm.\n*/\n\n10 # Plot3DSadaptive(_func, _var, _square, 0, _epsilon, _optionshash) <-- [];\n20 # Plot3DSadaptive(_func, _var, [_x1, _y1, _x2, _y2, _f11, _f12, _f21, _f22], _depth, _epsilon, _optionshash) <--\n{\n        Local(x3, y3, f13, f31, f33, f32, f23, result);\n\n        // if we are here, it means we used one more recursion level\n        optionshash[\"used depth\"] := depth-1;\n        // bisection\n        x3 := NM(Eval((x1+x2)/2));\n        y3 := NM(Eval((y1+y2)/2));\n        // compute new values\n        // use the confusing Map semantics: the list of all x's separately from the list of all y's\n        f13 := NM(Eval(Apply([var, func], [x1, y3])));\n        f31 := NM(Eval(Apply([var, func], [x3, y1])));\n        f33 := NM(Eval(Apply([var, func], [x3, y3])));\n        f32 := NM(Eval(Apply([var, func], [x3, y2])));\n        f23 := NM(Eval(Apply([var, func], [x2, y3])));\n        result := [[x1,y3,f13], [x3, y1, f31], [x3, y3, f33], [x3, y2, f32], [x2, y3, f23]];\n/*\ny2        12  32  22\n\n        13  33  23\n\ny1        11  31  21\n\n        x1      x2\n*/\n        Decide(\n                // condition for the values not to oscillate too rapidly\n                signchange(f11,f13,f12) + signchange(f13,f12,f32) + signchange(f12,f32,f22) <=? 2 And? signchange(f22,f23,f21) + signchange(f23,f21,f31) + signchange(f21,f31,f11) <=? 2 And? \n                \n                // condition for the values not to change too rapidly\n                NM(Eval(Abs( (f11-f23)/2-(f12-f21)/3+(f22-f13)/6+2*(f32-f33)/3 )))\n                        <=? NM(Eval( epsilon*(        // the expression here will be nonnegative because we subtract the minimum value\n                        // cubature normalized to 1\n                (f11 + f12 + f21 + f22)/12 + 2*f33/3\n                - Minimum([f11, f12, f21, f22, f13, f31, f33, f32, f23]) ) ) )\n                ,\n                // okay, do not refine any more\n                result,\n                // not okay, need to refine more\n                Concat(\n                        // first, give the extra points,\n                        result,\n                        // then perform recursive calls on four quarters of the original square; relax precision by factor of 4\n                        Plot3DSadaptive(func, var, [x1, y1, x3, y3, f11, f13, f31, f33], depth-1, epsilon*4, optionshash),\n                        Plot3DSadaptive(func, var, [x1, y3, x3, y2, f13, f12, f33, f32], depth-1, epsilon*4, optionshash),\n                        Plot3DSadaptive(func, var, [x3, y1, x2, y3, f31, f33, f21, f23], depth-1, epsilon*4, optionshash),\n                        Plot3DSadaptive(func, var, [x3, y3, x2, y2, f33, f32, f23, f22], depth-1, epsilon*4, optionshash)\n                )\n        );\n};\n\n};        // LocalSymbols()\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/plots/_3d/plot3ds.mpw, (Plot3DS;Plot3DSgetdata;Plot3DSadaptive)";
        scriptMap.put("Plot3DS",scriptString);
        scriptMap.put("Plot3DSgetdata",scriptString);
        scriptMap.put("Plot3DSadaptive",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// utility function: check whether the derivative changes sign in given 3 numbers, return 0 or 1. Also return 1 when one of the arguments is not a number.\nsignchange(x,y,z) :=\nDecide(\n        Number?(x) And? Number?(y) And? Number?(z)\n        And? Not? (\n                x>?y And? y<?z Or? \n                \n                x<?y And? y>?z\n        )\n, 0, 1); \n\n";
        scriptString[2] = "/org/mathpiper/scripts4/plots/signchange.mpw, (signchange)";
        scriptMap.put("signchange",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # AllSatisfy?(pred_String?,lst_List?) <-- Apply(\"And?\",(MapSingle(pred,lst)));\n\n20 # AllSatisfy?(_pred,_lst) <-- False;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/AllSatisfy_.mpw, (AllSatisfy?)";
        scriptMap.put("AllSatisfy?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n0 # BooleanType?(True) <-- True;\n0 # BooleanType?(False) <-- True;\n1 # BooleanType?(_anythingElse) <-- False;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/BooleanType_.mpw, (BooleanType?)";
        scriptMap.put("BooleanType?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction (\"Boolean?\", [_x])\n        (x=?True) Or? (x=?False) Or? Function?(x) And? Contains?([\"=?\", \">?\", \"<?\", \">=?\", \"<=?\", \"!=?\", \"And?\", \"Not?\", \"Or?\", \"Implies?\", \"Equivales?\"], Type(x));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/Boolean_.mpw, (Boolean?)";
        scriptMap.put("Boolean?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nConstant?(_n) <-- (VarList(n) =? []);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/Constant_.mpw, (Constant?)";
        scriptMap.put("Constant?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"Equation?\",*);\n\n10 # Equation?(expr_Atom?) <-- False;\n\n12 # Equation?(_expr) <-- FunctionToList(expr)[1] =? == ;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/Equation_.mpw, (Equation?)";
        scriptMap.put("Equation?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nEvenFunction?(f,x):= (f =? Eval(Substitute(x,-x)f));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/EvenFunction_.mpw, (EvenFunction?)";
        scriptMap.put("EvenFunction?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nEven?(n) := Integer?(n) And? ( BitAnd(n,1)  =? 0 );\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/Even_.mpw, (Even?)";
        scriptMap.put("Even?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// TODO FIXME document this: FloatIsInt? returns True if the argument is integer after removing potential trailing\n/// zeroes after the decimal point\n// but in fact this should be a call to BigNumber::IsIntValue()\nFloatIsInt?(_x) <--\n   {\n     x:=NM(Eval(x));\n     Local(prec,result,n);\n     Assign(prec,BuiltinPrecisionGet());\n     Decide(Zero?(x),Assign(n,2),\n     Decide(x>?0,\n       Assign(n,2+FloorN(NM(FastLog(x)/FastLog(10)))),\n       Assign(n,2+FloorN(NM(FastLog(-x)/FastLog(10))))\n       ));\n     BuiltinPrecisionSet(n+prec);\n     Assign(result,Zero?(RoundTo(x-Floor(x),prec)) Or? Zero?(RoundTo(x-Ceil(x),prec)));\n     BuiltinPrecisionSet(prec);\n     result;\n   };\n//  \n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/FloatIsInt_.mpw, (FloatIsInt?)";
        scriptMap.put("FloatIsInt?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// Analyse arithmetic expressions\n\nHasExpressionArithmetic?(expr, atom) := HasExpressionSome?(expr, atom, [ToAtom(\"+\"), ToAtom(\"-\"), ToAtom(\"*\"), ToAtom(\"/\")]);\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/HasExpressionArithmetic_.mpw, (HasExpressionArithmetic?)";
        scriptMap.put("HasExpressionArithmetic?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// Same except only look at function arguments for functions in a given list\nHasExpressionSome?(_expr, _atom, _looklist):: Equal?(expr, atom) <-- True;\n// an atom contains itself\n15 # HasExpressionSome?(expr_Atom?, _atom, _looklist) <-- Equal?(expr, atom);\n// a list contains an atom if one element contains it\n// we test for lists now because lists are also functions\n// first take care of the empty list:\n19 # HasExpressionSome?([], _atom, _looklist) <-- False;\n20 # HasExpressionSome?(expr_List?, _atom, _looklist) <-- HasExpressionSome?(First(expr), atom, looklist) Or? HasExpressionSome?(Rest(expr), atom, looklist);\n// a function contains an atom if one of its arguments contains it\n// first deal with functions that do not belong to the list: return False since we have already checked it at #15\n25 # HasExpressionSome?(expr_Function?, _atom, _looklist)::(Not? Contains?(looklist, ToAtom(Type(expr)))) <-- False;\n// a function contains an atom if one of its arguments contains it\n30 # HasExpressionSome?(expr_Function?, _atom, _looklist) <-- HasExpressionSome?(Rest(FunctionToList(expr)), atom, looklist);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/HasExpressionSome_.mpw, (HasExpressionSome?)";
        scriptMap.put("HasExpressionSome?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// HasExpression? --- test for an expression containing a subexpression\n/// for checking dependence on variables, this may be faster than using VarList or FreeOf? and this also can be used on non-variables, e.g. strings or numbers or other atoms or even on non-atoms\n// an expression contains itself -- check early\n10 # HasExpression?(_expr, _atom):: Equal?(expr, atom) <-- True;\n// an atom contains itself\n15 # HasExpression?(expr_Atom?, _atom) <-- Equal?(expr, atom);\n// a list contains an atom if one element contains it\n// we test for lists now because lists are also functions\n// first take care of the empty list:\n19 # HasExpression?([], _atom) <-- False;\n20 # HasExpression?(expr_List?, _atom) <-- HasExpression?(First(expr), atom) Or? HasExpression?(Rest(expr), atom);\n// a function contains an atom if one of its arguments contains it\n30 # HasExpression?(expr_Function?, _atom) <-- HasExpression?(Rest(FunctionToList(expr)), atom);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/HasExpression_.mpw, (HasExpression?)";
        scriptMap.put("HasExpression?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// Analyse arithmetic expressions\n\nHasFunctionArithmetic?(expr, atom) := HasFunctionSome?(expr, atom, [ToAtom(\"+\"), ToAtom(\"-\"), *, /]);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/HasFunctionArithmetic_.mpw, (HasFunctionArithmetic?)";
        scriptMap.put("HasFunctionArithmetic?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// function name given as string.\n10 # HasFunctionSome?(_expr, string_String?, _looklist) <-- HasFunctionSome?(expr, ToAtom(string), looklist);\n/// function given as atom.\n// atom contains no functions\n10 # HasFunctionSome?(expr_Atom?, atom_Atom?, _looklist) <-- False;\n// a list contains the function List so we test it together with functions\n// a function contains itself, or maybe an argument contains it\n\n// first deal with functions that do not belong to the list: return top level function\n15 # HasFunctionSome?(expr_Function?, atom_Atom?, _looklist)::(Not? Contains?(looklist, ToAtom(Type(expr)))) <-- Equal?(First(FunctionToList(expr)), atom);\n// function belongs to the list - check its arguments\n20 # HasFunctionSome?(expr_Function?, atom_Atom?, _looklist) <-- Equal?(First(FunctionToList(expr)), atom) Or? ListHasFunctionSome?(Rest(FunctionToList(expr)), atom, looklist);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/HasFunctionSome_.mpw, (HasFunctionSome?)";
        scriptMap.put("HasFunctionSome?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// HasFunction? --- test for an expression containing a function\n/// function name given as string.\n10 # HasFunction?(_expr, string_String?) <-- HasFunction?(expr, ToAtom(string));\n/// function given as atom.\n// atom contains no functions\n10 # HasFunction?(expr_Atom?, atom_Atom?) <-- False;\n// a list contains the function List so we test it together with functions\n// a function contains itself, or maybe an argument contains it\n20 # HasFunction?(expr_Function?, atom_Atom?) <-- Equal?(First(FunctionToList(expr)), atom) Or? ListHasFunction?(Rest(FunctionToList(expr)), atom);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/HasFunction_.mpw, (HasFunction?)";
        scriptMap.put("HasFunction?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # Infinity?(Infinity) <-- True;\n10 # Infinity?(-(_x)) <-- Infinity?(x);\n\n// This is just one example, we probably need to extend this further to include all\n// cases for f*Infinity where f can be guaranteed to not be zero\n11 # Infinity?(Sign(_x)*y_Infinity?) <-- True;\n\n60000 # Infinity?(_x) <-- False;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/Infinity_.mpw, (Infinity?)";
        scriptMap.put("Infinity?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n19 # ListHasFunctionSome?([], _atom, _looklist) <-- False;\n20 # ListHasFunctionSome?(expr_List?, atom_Atom?, _looklist) <-- HasFunctionSome?(First(expr), atom, looklist) Or? ListHasFunctionSome?(Rest(expr), atom, looklist);\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/ListHasFunctionSome_.mpw, (ListHasFunctionSome?)";
        scriptMap.put("ListHasFunctionSome?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// ListHasFunction? --- test for one of the elements of a list to contain a function\n/// this is mainly useful to test whether a list has nested lists, \n/// i.e. ListHasFunction?([1,2,3], List)=False and ListHasFunction?([1,2,[3]], List)=True.\n// need to exclude the List atom itself, so don't use FunctionToList\n19 # ListHasFunction?([], _atom) <-- False;\n20 # ListHasFunction?(expr_List?, atom_Atom?) <-- HasFunction?(First(expr), atom) Or? ListHasFunction?(Rest(expr), atom);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/ListHasFunction_.mpw, (ListHasFunction?)";
        scriptMap.put("ListHasFunction?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"CanBeMonomial\",*);\n//Retract(\"Monomial?\",*);\n\n10 # CanBeMonomial(_expr)::(Type(expr)=?\"UniVariate\") <-- False;\n\n10 # CanBeMonomial(_expr)<--Not? (HasFunction?(expr,ToAtom(\"+\")) Or? HasFunction?(expr,ToAtom(\"-\")));\n\n10 # Monomial?(expr_CanBeMonomial) <-- \n{\n    Local(r);\n    Decide( RationalFunction?(expr),\n        r := (VarList(Denominator(expr)) =? []),\n        r := True\n    );\n};\n\n15 # Monomial?(_expr) <-- False;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/Monomial_.mpw, (Monomial?;CanBeMonomial)";
        scriptMap.put("Monomial?",scriptString);
        scriptMap.put("CanBeMonomial",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nNegativeInteger?(x):= Integer?(x) And? x <? 0;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/NegativeInteger_.mpw, (NegativeInteger?)";
        scriptMap.put("NegativeInteger?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nNegativeNumber?(x):= Number?(x) And? x <? 0;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/NegativeNumber_.mpw, (NegativeNumber?)";
        scriptMap.put("NegativeNumber?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* See if a number, when evaluated, would be a positive real value */\n\nNegativeReal?(_r) <--\n{\n  r:=NM(Eval(r));\n  (Number?(r) And? r <=? 0);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/NegativeReal_.mpw, (NegativeReal?)";
        scriptMap.put("NegativeReal?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nNonNegativeInteger?(x):= Integer?(x) And? x >=? 0;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/NonNegativeInteger_.mpw, (NonNegativeInteger?)";
        scriptMap.put("NonNegativeInteger?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nNonNegativeNumber?(x):= Number?(x) And? x >=? 0;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/NonNegativeNumber_.mpw, (NonNegativeNumber?)";
        scriptMap.put("NonNegativeNumber?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nNonZeroInteger?(x) := (Integer?(x) And? x !=? 0);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/NonZeroInteger_.mpw, (NonZeroInteger?)";
        scriptMap.put("NonZeroInteger?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # NoneSatisfy?(pred_String?,lst_List?) <-- Not? Apply(\"Or?\",(MapSingle(pred,lst)));\n\n20 # NoneSatisfy?(_pred,_lst) <-- True;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/NoneSatisfy_.mpw, (NoneSatisfy?)";
        scriptMap.put("NoneSatisfy?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/*\n10 # NotZero?(x_Number?)       <-- ( RoundTo(x,BuiltinPrecisionGet()) !=? 0);\n*/\n\n\n10 # NotZero?(x_Number?) <-- ( AbsN(x)  >=? PowerN(10, -BuiltinPrecisionGet()));\n10 # NotZero?(x_Infinity?) <-- True;\n60000 # NotZero?(_x) <-- False;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/NotZero_.mpw, (NotZero?)";
        scriptMap.put("NotZero?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n// check that all items in the list are numbers\nNumericList?(_arglist) <-- List?(arglist) And?\n        (\"And?\" @ (MapSingle(Hold([[x],Number?(NM(Eval(x)))]), arglist)));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/NumericList_.mpw, (NumericList?)";
        scriptMap.put("NumericList?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nOddFunction?(f,x):= (f =? Eval(-Substitute(x,-x)f));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/OddFunction_.mpw, (OddFunction?)";
        scriptMap.put("OddFunction?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nOdd?(n)  := Integer?(n) And? ( BitAnd(n,1)  =? 1 );\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/Odd_.mpw, (Odd?)";
        scriptMap.put("Odd?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n// why do we need this? Why doesn't x=?1 not work?\n10 # One?(x_Number?) <-- Zero?(SubtractN(x,1));\n60000 # One?(_x) <-- False;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/One_.mpw, (One?)";
        scriptMap.put("One?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"PolynomialOverIntegers?\",*);\n\n10 # PolynomialOverIntegers?(expr_Function?) <-- \n{\n    Local(x,vars);\n    vars := VarList(expr);\n    Decide(Length(vars)>?1,vars:=HeapSort(vars,\"GreaterThan?\"));    \n    x := vars[1];\n    PolynomialOverIntegers?(expr,x);\n};\n\n15 # PolynomialOverIntegers?(_expr) <-- False;\n\n\n10 # PolynomialOverIntegers?(_expr,_var)::(CanBeUni(var,expr)) <--\n{\n    Decide( AllSatisfy?(\"Integer?\",Coef(expr,var,0 .. Degree(expr,var))),\n        True,\n        False\n      );\n};\n\n15 # PolynomialOverIntegers?(_expr,_var) <-- False;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/PolynomialOverIntegers_.mpw, (PolynomialOverIntegers?)";
        scriptMap.put("PolynomialOverIntegers?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\n10 # Polynomial?( expr_String? ) <-- False;\n\n15 # Polynomial?( expr_Constant? ) <-- True;\n \n20 # Polynomial?(_expr) <-- \n{\n    Local(x,vars);\n    vars := VarList(expr);\n    Decide(Length(vars)>?1,vars:=HeapSort(vars,\"GreaterThan?\"));    \n    x := vars[1];\n    Polynomial?(expr,x);\n};\n\n25 # Polynomial?(_expr) <-- False;\n\n\n10 # Polynomial?(_expr,_var)::(CanBeUni(var,expr)) <-- True;\n\n15 # Polynomial?(_expr,_var) <-- False;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/Polynomial_.mpw, (Polynomial?)";
        scriptMap.put("Polynomial?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nPositiveInteger?(x):= Integer?(x) And? x >? 0;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/PositiveInteger_.mpw, (PositiveInteger?)";
        scriptMap.put("PositiveInteger?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nPositiveNumber?(x):= Number?(x) And? x >? 0;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/PositiveNumber_.mpw, (PositiveNumber?)";
        scriptMap.put("PositiveNumber?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* See if a number, when evaluated, would be a positive real value */\nPositiveReal?(_r) <--\n{\n  r:=NM(Eval(r));\n  (Number?(r) And? r >=? 0);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/PositiveReal_.mpw, (PositiveReal?)";
        scriptMap.put("PositiveReal?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"RationalFunction?\",*);\n\n10 # RationalFunction?(_expr)::(Length(VarList(expr))=?0) <-- False;\n\n15 # RationalFunction?(_expr) <-- RationalFunction?(expr,VarList(expr));\n\n10 # RationalFunction?(expr_RationalOrNumber?,_var) <-- False;\n\n15 # RationalFunction?(_expr,var_Atom?)::(Type(expr)=?\"/\" Or? Type(-expr)=?\"/\") <--\n{\n    Decide(Polynomial?(Numerator(expr),var) And? Polynomial?(Denominator(expr),var),\n        Contains?(VarList(Denominator(expr)),var),\n        False\n    );\n};\n\n20 # RationalFunction?(_expr,vars_List?)::(Type(expr)=?\"/\" Or? Type(-expr)=?\"/\") <--\n{\n    Decide(Polynomial?(Numerator(expr),vars) And? Polynomial?(Denominator(expr),vars),\n        Intersection(vars, VarList(expr)) !=? [],\n        False\n    );\n};\n\n60000 # RationalFunction?(_expr,_var) <-- False;\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/RationalFunction_.mpw, (RationalFunction?)";
        scriptMap.put("RationalFunction?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # RationalOrNumber?(x_Number?) <-- True;\n10 # RationalOrNumber?(x_Number? / y_Number?) <-- True;\n10 # RationalOrNumber?(-(x_Number? / y_Number?)) <-- True;\n60000 # RationalOrNumber?(_x) <-- False;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/RationalOrNumber_.mpw, (RationalOrNumber?)";
        scriptMap.put("RationalOrNumber?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* changed definition of Rational?, Nobbi 030529\nFunction(\"Rational?\",[_aLeft]) Type(aLeft) = \"/\";\n\nFunction(\"RationalNumeric?\",[_aLeft])\n    Type(aLeft) = \"/\" And?\n    Number?(aLeft[1]) And?\n    Number?(aLeft[2]);\n\nRationalOrNumber?(_x) <-- (Number?(x) Or? RationalNumeric?(x));\n\n10 # RationalOrInteger?(x_Integer?) <-- True;\n10 # RationalOrInteger?(x_Integer? / y_Integer?) <-- True;\n20 # RationalOrInteger?(_x) <-- False;\n\n*/\n\n10 # Rational?(x_Integer?) <-- True;\n10 # Rational?(x_Integer? / y_Integer?) <-- True;\n10 # Rational?(-(x_Integer? / y_Integer?)) <-- True;\n60000 # Rational?(_x) <-- False;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/Rational_.mpw, (Rational?)";
        scriptMap.put("Rational?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n// an expression free of the variable -- obviously not a sum of terms in it\n10 # SumOfTerms?(_var,expr_FreeOf?(var)) <-- False;\n\n// an Atom cannot be a sum of terms\n12 # SumOfTerms?(_var,expr_Atom?()) <-- False;\n\n// after being \"Listified\", expr is a sum of terms if headed by \"+\" or \"-\"\n14 # SumOfTerms?(_var,expr_List?())::(expr[1]=?ToAtom(\"+\") Or? expr[1]=?ToAtom(\"-\")) <-- True;\n\n// after being \"Listified\", an expr headed by \"*\" is not considered a sum \n// of terms unless one or the other operand is free of the variable\n16 # SumOfTerms?(_var,expr_List?())::(expr[1]=?ToAtom(\"*\")) <-- Or?(FreeOf?(var,expr[2]),FreeOf?(var,expr[3]));\n\n// after being \"Listified\", an expr headed by \"/\" is not considered a sum \n// of terms unless the denominator (only) is free of the variable\n18 # SumOfTerms?(_var,expr_List?())::(expr[1]=?ToAtom(\"/\")) <-- FreeOf?(var,expr[3]);\n\n// after being \"Listified\", any other expression is not a sum of terms\n20 # SumOfTerms?(_var,expr_List?()) <-- False;\n\n// if we get to this point, FunctionToList the expression and try again\n22 # SumOfTerms?(_var,_expr) <-- SumOfTerms?(var,FunctionToList(expr));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/SumOfTerms_.mpw, (SumOfTerms?)";
        scriptMap.put("SumOfTerms?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nVariable?(_expr) <-- (Atom?(expr) And? Not?(expr =? True) And? Not?(expr =? False) And? Not?(expr =? Infinity) And? Not?(expr =? -Infinity) And? Not?(expr =? Undefined) And? Not?(Number?(NM(Eval(expr)))));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/Variable_.mpw, (Variable?)";
        scriptMap.put("Variable?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"ZeroVector?\",[_aList]) aList =? ZeroVector(Length(aList));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/ZeroVector_.mpw, (ZeroVector?)";
        scriptMap.put("ZeroVector?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//10 # Zero?(x_Number?) <-- (DivideN( Round( MultiplyN(x, 10^BuiltinPrecisionGet()) ), 10^BuiltinPrecisionGet() ) = 0);\n\n// these should be calls to MathSign() and the math library should do this. Or it should be just MathEquals(x,0).\n// for now, avoid underflow and avoid Zero?(10^(-BuiltinPrecisionGet())) returning True.\n10 # Zero?(x_Number?) <-- ( MathSign(x) =? 0 Or? AbsN(x)  <? PowerN(10, -BuiltinPrecisionGet()));\n60000 # Zero?(_x) <-- False;\n\n//Note:tk:moved here from univariate.rep.\n20 # Zero?(UniVariate(_var,_first,_coefs)) <-- ZeroVector?(coefs);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/Zero_.mpw, (Zero?)";
        scriptMap.put("Zero?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* def file definitions\nScalar?\nMatrix?\nVector?\nSquareMatrix?\n*/\n\nLocalSymbols(p,x)\n{\n// test for a scalar\nFunction(\"Scalar?\",[_x]) Not?(List?(x));\n\n\n\n// test for a vector\nFunction(\"Vector?\",[_x])\n   Decide(List?(x),\n   Length(Select(x, \"List?\"))=?0,\n   False);\n\n// test for a vector w/ element test p\nFunction(\"Vector?\",[_p,_x])\n{\n   Decide(List?(x),\n   {\n      Local(i,n,result);\n      n:=Length(x);\n      i:=1;\n      result:=True;\n      While(i<=?n And? result)\n      {\n         result:=Apply(p,[x[i]]);\n         i++;\n      };\n      result;\n   },\n   False);\n};\n\n// test for a matrix (dr)\nFunction(\"Matrix?\",[_x])\nDecide(List?(x) And? Length(x)>?0,\n{\n   Local(n);\n   n:=Length(x);\n   Decide(Length(Select(x, \"Vector?\"))=?n,\n   MapSingle(\"Length\",x)=?Length(x[1])+ZeroVector(n),\n   False);\n},\nFalse);\n\n// test for a matrix w/ element test p (dr)\nFunction(\"Matrix?\",[_p,_x])\nDecide(Matrix?(x),\n{\n   Local(i,j,m,n,result);\n   m:=Length(x);\n   n:=Length(x[1]);\n   i:=1;\n   result:=True;\n   While(i<=?m And? result)\n   {\n      j:=1;\n      While(j<=?n And? result)\n      {\n         result:=Apply(p,[x[i][j]]);\n         j++;\n      };\n      i++;\n   };\n   result;\n},\nFalse);\n\n/* remove? (dr)\nSquareMatrix?(_x) <--\n[\n   Local(d);\n   d:=Dimensions(x);\n   Length(d)=2 And? d[1]=?d[2];\n];\n*/\n\n// test for a square matrix (dr)\nFunction(\"SquareMatrix?\",[_x]) Matrix?(x) And? Length(x)=?Length(x[1]);\n// test for a square matrix w/ element test p (dr)\nFunction(\"SquareMatrix?\",[_p,_x]) Matrix?(p,x) And? Length(x)=?Length(x[1]);\n\n}; // LocalSymbols(p,x)\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/predicates/matrix.mpw, (Scalar?;Matrix?;Vector?;SquareMatrix?)";
        scriptMap.put("Scalar?",scriptString);
        scriptMap.put("Matrix?",scriptString);
        scriptMap.put("Vector?",scriptString);
        scriptMap.put("SquareMatrix?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Evaluates distribution dst at point x\n   known distributions are:\n   1. Discrete distributions\n   -- BernoulliDistribution(p)\n   -- BinomialDistribution(p,n)\n   -- DiscreteUniformDistribution(a,b)\n   -- PoissonDistribution(l)\n   -- HypergeometricDistribution(N, M)\n   2. Continuous distributions\n   -- ExponentialDistribution(l)\n   -- NormalDistrobution(a,s)\n   -- ContinuousUniformDistribution(a,b)\n   -- tDistribution(m)\n   -- GammaDistribution(m)\n   -- ChiSquareDistribution(m)\n\n  DiscreteDistribution(domain,probabilities) represent arbitrary\n  distribution with finite number of possible values; domain list\n  contains possible values such that\n  Pr(X=domain[i])=probabilities[i].\n  TODO: Should domain contain numbers only?\n*/\n\n\n/* Evaluates Cumulative probability function CDF(x)=Pr(X<x) */\n\n//Retract(\"CDF\", *);\n\n//Discrete distributions.\n\n10 # CDF(BernoulliDistribution(_p), x_Number?) <-- Decide(x<=?0,0,Decide(x>?0 And? x<=?1, p,1));\n11 # CDF(BernoulliDistribution(_p), _x) <-- Hold(Decide(x<=?0,0,Decide(x>?0 And? x<=?1, p,1)));\n\n10 # CDF(BinomialDistribution(_p,_n),m_Number?)::(m<?0) <-- 0;\n10 # CDF(BinomialDistribution(_p,n_Integer?),m_Number?)::(m>?n) <-- 1;\n10 # CDF(BinomialDistribution(_p,_n),_m) <-- Sum @ [ i, 0, Floor(m), PMF(BinomialDistribution(p,n),i)];\n\n10 # CDF(DiscreteUniformDistribution( a_Number?, b_Number?), x_Number?)::(x<=?a) <-- 0;\n10 # CDF(DiscreteUniformDistribution( a_Number?, b_Number?), x_Number?)::(x>?b) <-- 1;\n10 # CDF(DiscreteUniformDistribution( a_Number?, b_Number?), x_Number?)::(a<?x And? x<=?b) <-- (x-a)/(b-a+1);\n11 # CDF(DiscreteUniformDistribution( _a, _b), _x) <--Hold(Decide(x<=?a,0,Decide(x<=?b,(x-a)/(b-a),1)));\n\n10 # CDF(PoissonDistribution(_l), x_Number?)::(x<=?0) <-- 0;\n10 # CDF(PoissonDistribution(_l), _x) <-- Sum @ [i,0,x,PMF(PoissonDistribution(l),i)];\n\n10 # CDF(ChiSquareDistribution(_m), _x) <-- IncompleteGamma(m/2,x/2)/Gamma(x/2);\n10 # CDF(DiscreteDistribution( dom_List?, prob_List?), _x)   <--\n      {\n         Local(i,cdf,y);\n\n         i := 1;\n         cdf:=0;\n         y:=dom[i];\n         While(y<?x) {cdf:=cdf+prob[i];i++;};\n         cdf;\n      };\n\n10 # CDF(HypergeometricDistribution( N_Number?, M_Number?, n_Number?), x_Number?)::(M <=? N And? n <=? N) <-- \n{\n    Sum @ [i,0,x,PMF(HypergeometricDistribution(N, M, n),i)];\n};\n\n\n//Continuous distributions.\n\n10 # CDF(NormalDistribution(_m,_s), _x) <-- 1/2 + 1/2 * ErrorFunction((x - m)/(s*Sqrt(2))); //See http://en.wikipedia.org/wiki/Normal_distribution.\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/probability/CDF.mpw, (CDF)";
        scriptMap.put("CDF",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Evaluates distribution dst at point x\n   known distributions are:\n   1. Discrete distributions\n   -- BernoulliDistribution(p)\n   -- BinomialDistribution(p,n)\n   -- DiscreteUniformDistribution(a,b)\n   -- PoissonDistribution(l)\n   2. Continuous distributions\n   -- ExponentialDistribution(l)\n   -- NormalDistrobution(a,s)\n   -- ContinuousUniformDistribution(a,b)\n   -- tDistribution(m)\n   -- GammaDistribution(m)\n   -- ChiSquareDistribution(m)\n\n  DiscreteDistribution(domain,probabilities) represent arbitrary\n  distribution with finite number of possible values; domain list\n  contains possible values such that\n  Pr(X=domain[i])=probabilities[i].\n  TODO: Should domain contain numbers only?\n*/\n\n\n\n10 # Expectation(BernoulliDistribution(_p)) <-- 1-p;\n\n10 # Expectation(BinomialDistribution(_p,_n)) <-- n*p;\n\n10 # Expectation( DiscreteDistribution( dom_List?, prob_List?))::( Length(dom)=?Length(prob) And? Simplify(Sum(prob))=?1) <-- Sum @ [i,1,Length(dom),dom[i]*prob[i]];\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/probability/Expectation.mpw, (Expectation)";
        scriptMap.put("Expectation",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Evaluates distribution dst at point x\n   known distributions are:\n   1. Discrete distributions\n   -- BernoulliDistribution(p)\n   -- BinomialDistribution(p,n)\n   -- DiscreteUniformDistribution(a,b)\n   -- PoissonDistribution(l)\n   2. Continuous distributions\n   -- ExponentialDistribution(l)\n   -- NormalDistrobution(a,s)\n   -- ContinuousUniformDistribution(a,b)\n   -- tDistribution(m)\n   -- GammaDistribution(m)\n   -- ChiSquareDistribution(m)\n\n  DiscreteDistribution(domain,probabilities) represent arbitrary\n  distribution with finite number of possible values; domain list\n  contains possible values such that\n  Pr(X=domain[i])=probabilities[i].\n  TODO: Should domain contain numbers only?\n*/\n\n//Retract(\"PDF\", *);\n\n\n10 # PDF(ExponentialDistribution(_l), _x) <-- Decide(x<?0,0,l*Exp(-l*x));\n\n10 # PDF(NormalDistribution(_m,_s),_x) <-- Exp(-(x-m)^2/(2*s^2))/Sqrt(2*Pi*s^2); //See http://en.wikipedia.org/wiki/Normal_distribution.\n\n10 # PDF(ContinuousUniformDistribution(_a,_b),x)::(a<?b) <-- Decide(x<?a Or? x>?b,0,1/(b-a));\n\n10 # PDF(DiscreteDistribution( dom_List?, prob_List?), _x)::( Length(dom)=?Length(prob) And? Simplify(Add(prob))=?1) <--\n    {\n      Local(i);\n      i:=Find(dom,x);\n      Decide(i =? -1,0,prob[i]);\n    };\n10 # PDF( ChiSquareDistribution( _m),x_RationalOrNumber?)::(x<=?0) <-- 0;\n20 # PDF( ChiSquareDistribution( _m),_x) <-- x^(m/2-1)*Exp(-x/2)/2^(m/2)/Gamma(m/2);\n\n10 # PDF(tDistribution(_m),x) <-- Gamma((m+1)/2)*(1+x^2/m)^(-(m+1)/2)/Gamma(m/2)/Sqrt(Pi*m);\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/probability/PDF.mpw, (PDF)";
        scriptMap.put("PDF",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Evaluates distribution dst at point x\n   known distributions are:\n   1. Discrete distributions\n   -- BernoulliDistribution(p)\n   -- BinomialDistribution(p,n)\n   -- DiscreteUniformDistribution(a,b)\n   -- PoissonDistribution(l)\n   \n   2. Continuous distributions\n   -- ExponentialDistribution(l)\n   -- NormalDistrobution(a,s)\n   -- ContinuousUniformDistribution(a,b)\n   -- tDistribution(m)\n   -- GammaDistribution(m)\n   -- ChiSquareDistribution(m)\n\n  DiscreteDistribution(domain,probabilities) represent arbitrary\n  distribution with finite number of possible values; domain list\n  contains possible values such that\n  Pr(X=domain[i])=probabilities[i].\n  TODO: Should domain contain numbers only?\n*/\n\n//Retract(\"PMF\", *);\n\n10 # PMF(BernoulliDistribution(_p),0) <-- p;\n10 # PMF(BernoulliDistribution(_p),1) <-- 1-p;\n10 # PMF(BernoulliDistribution(_p),x_Number?)::(x !=? 0 And? x !=? 1) <-- 0;\n10 # PMF(BernoulliDistribution(_p),_x) <-- Hold(Decide(x=?0,p,Decide(x=?1,1-p,0)));\n\n10 # PMF(BinomialDistribution(_p,_n),_k) <-- BinomialCoefficient(n,k)*p^k*(1-p)^(n-k);\n\n10 # PMF(DiscreteUniformDistribution(_a,_b), x_Number?) <-- Decide(x<?a Or? x>?b, 0 ,1/(b-a+1));\n11 # PMF(DiscreteUniformDistribution(_a,_b), _x) <-- Hold(Decide(x<?a Or? x>?b, 0 ,1/(b-a+1)));\n\n10 # PMF(PoissonDistribution(_l), n_Number?) <-- Decide(n<?0,0,Exp(-l)*l^n/n!);\n11 # PMF(PoissonDistribution(_l),_n) <-- Exp(-l)*l^n/n!;\n\n10 # PMF(GeometricDistribution(_p),_n) <--Decide(n<?0,0,p*(1-p)^n);\n\n\n\n10 # PMF(DiscreteDistribution( dom_List?, prob_List?), _x)::( Length(dom)=?Length(prob) And? Simplify(Add(prob))=?1) <--\n    {\n      Local(i);\n      i:=Find(dom,x);\n      Decide(i =? -1,0,prob[i]);\n    };\n    \n\n\n10 # PMF(HypergeometricDistribution( N_Number?, M_Number?, n_Number?), x_Number?)::(M <=? N And? n <=? N) <-- (BinomialCoefficient(M,x) * BinomialCoefficient(N-M, n-x))/BinomialCoefficient(N,n);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/probability/PMF.mpw, (PMF)";
        scriptMap.put("PMF",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"Manipulate\",*);\n\nRulebaseHoldArguments(\"Manipulate\",[_symbolicEquation]);\nHoldArgument(\"Manipulate\",\"symbolicEquation\");\n10 # Manipulate(_symbolicEquation)::HasFunction?(Eval(symbolicEquation), \"==\") <--\n{\n    Local(listForm, operator, operand, left, right, leftManipulated, rightManipulated, operandIndex, equationIndex, leftOrder, rightOrder);\n\n    listForm := FunctionToList(symbolicEquation);\n    \n    operator := listForm[1];\n    \n    Decide(HasFunction?(Eval(listForm[2]),\"==\" ), {operandIndex := 3; equationIndex := 2; }, { operandIndex := 2; equationIndex := 3;});\n    \n    operand := listForm[operandIndex];\n    equation := Eval(listForm[equationIndex]);\n    left := EquationLeft(equation);\n    right := EquationRight(equation);\n    \n    Decide(operandIndex =? 3, { leftOrder := `([left,operand]);rightOrder := `([right,operand]);},  {leftOrder := `([operand,left]); rightOrder := `([operand,right]);});\n    \n    \n    leftManipulated := ExpandBrackets(Simplify(Apply(ToString(operator), leftOrder)));\n    rightManipulated := ExpandBrackets(Simplify(Apply(ToString(operator), rightOrder)));   \n    \n    leftManipulated == rightManipulated;\n\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/equations/Manipulate.mpw, (Manipulate)";
        scriptMap.put("Manipulate",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\nControlChart(data) :=\n{\n    A2 := .577;\n    D3 := 0;\n    D4 := 2.144;\n\n    means := [];\n    meansPoints := [];\n    \n    ranges := [];\n    rangesPoints := [];\n    \n    index := 1;\n    ForEach(group, data)\n    {\n        groupMean := Mean(group);\n        means := NM(Append(means, groupMean));\n        meansPoints := NM(Append(meansPoints,[index, groupMean] ));\n        \n\n        groupRange := Range(group);\n        ranges := NM(Append(ranges, groupRange));\n        rangesPoints := NM(Append(rangesPoints,[index, groupRange] ));\n        \n        index++;\n    };\n    \n    xBarBar := NM(Mean(means));\n    \n    rBar := NM(Mean(ranges));\n    \n    xBarUCL := NM(xBarBar + A2*rBar);\n    \n    xBarLCL := NM(xBarBar - A2*rBar);\n    \n    rUCL := NM(D4*rBar);\n    \n    rLCL := NM(D3*rBar);\n};\n\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/geogebra/ControlChart.mpw, (ControlChart)";
        scriptMap.put("ControlChart",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"GeoGebra\",*);\n\nLocalSymbols(options)\n{\n    options  := [];\n    \n    Local(updateObjects);\n    \n    updateObjects := \"\";\n    \n    options[\"updateObjects\"] := updateObjects;\n    \n    \n\nGeoGebra() := options;\n\n\nGeoGebra(list) := (options := list);\n\n\n\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/geogebra/GeoGebra.mpw, (GeoGebra)";
        scriptMap.put("GeoGebra",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"GeoGebraHistogram\",*);\n\nGeoGebraHistogram(classBoundaries, data) := \n{\n    Local(command);\n    //todo:tk: a check must be made to make sure that all data items fit into the class boundaries.\n    // If they don't, GeoGebra will not accept them.\n    \n    command := PatchString(\"Histogram[<?Write(classBoundaries);?>,<?Write(data);?>]\");\n    JavaCall(geogebra, \"evalCommand\", command);\n};\n\n\n\n\nGeoGebraHistogram(data) := \n{\n    Local(command, classBoundaries, noDuplicatesSorted, largestValue, smallestValue, x, numberOfUniqueValues);\n    \n    noDuplicatesSorted := HeapSort(RemoveDuplicates(data), \"<?\" );\n    \n    smallestValue := Floor(noDuplicatesSorted[1]);\n    \n    numberOfUniqueValues := Length(noDuplicatesSorted);\n    \n    largestValue := Ceil(noDuplicatesSorted[Length(noDuplicatesSorted)]);\n    \n    classBoundaries := NM(BuildList(x,x,smallestValue-.5,largestValue+.5,1));\n    \n    command := PatchString(\"Histogram[<?Write(classBoundaries);?>,<?Write(data);?>]\");\n    JavaCall(geogebra, \"evalCommand\", command);\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/geogebra/GeoGebraHistogram.mpw, (GeoGebraHistogram)";
        scriptMap.put("GeoGebraHistogram",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "//Retract(\"GeoGebraPlot\",*);\n\nRulebaseListedHoldArguments(\"GeoGebraPlot\",[_arg1,_arg2]);\n\n\n\n5 # GeoGebraPlot(_arg1) <-- GeoGebraPlot(arg1,[]);  //Handle single argument call.\n\n\n20 # GeoGebraPlot(function_Function?, options_List?)::(Not? List?(function)) <--\n{\n    Local(command);\n    \n    function := (Substitute(==,=) function);\n    \n    command := ConcatStrings(PipeToString()Write(function));\n    \n    JavaCall(geogebra,\"evalCommand\",command);\n};\n\n\n\n\n10 # GeoGebraPlot(list_List?, _options)::(NumericList?(list) )  <--\n{\n    Decide(List?(options), options := OptionsToAssociativeList(options), options := OptionsToAssociativeList([options]));\n\n    Local(length, index, labelIndex, pointTemplate, segmentCommandTemplate, segmentElementTemplate, command, code, x, y, pointSize);\n    \n    length := Length(list);\n    \n    Decide(Odd?(length), list := Append(list,list[length])); //Make list even for line drawing.\n    \n    Decide(options[\"pointSize\"] !=? Empty, pointSize := ToString(options[\"pointSize\"]), pointSize := \"1\");\n    \n    index := 1;\n    \n    labelIndex := 1;\n    \n    pointTemplate := \"<element type=\\\"point\\\" label=\\\"A<?Write(labelIndex);?>\\\"> <show object=\\\"true\\\" label=\\\"false\\\"/>?<objColor r=\\\"0\\\" g=\\\"0\\\" b=\\\"255\\\" alpha=\\\"0.0\\\"/>        <layer val=\\\"0\\\"/>        <animation step=\\\"0.1\\\" speed=\\\"1\\\" type=\\\"0\\\" playing=\\\"false\\\"/>        <coords x=\\\"<?Write(x);?>\\\" y=\\\"<?Write(y);?>\\\" z=\\\"1.0\\\"/>        <pointSize val=\\\"<?Write(ToAtom(pointSize));?>\\\"/></element>\";\n    segmentCommandTemplate := \"<command name=\\\"Segment\\\"><input a0=\\\"A1\\\" a1=\\\"A2\\\"/><output a0=\\\"a\\\"/>\";\n    segmentElementTemplate := \"<element type=\\\"segment\\\" label=\\\"a<?Write(labelIndex-1);?>\\\"><lineStyle thickness=\\\"2\\\" type=\\\"0\\\"/><show object=\\\"true\\\" label=\\\"false\\\"/><layer val=\\\"0\\\"/><coords x=\\\"-1.0\\\" y=\\\"1.0\\\" z=\\\"0.0\\\"/><lineStyle thickness=\\\"2\\\" type=\\\"0\\\"/><eqnStyle style=\\\"implicit\\\"/><outlyingIntersections val=\\\"false\\\"/><keepTypeOnTransform val=\\\"true\\\"/></element>\";\n    \n    \n    //todo:tk: this does not seem to be working JavaCall(geogebra, \"setRepaintingActive\", \"false\");\n    //JavaCall(geogebra, \"setLayerVisible\", \"0\", \"False\");\n    \n    While(index <? length+1)\n    {\n        x := list[index];\n        index++;\n        y := list[index];\n        index++;\n        \n        \n        code := PatchString(pointTemplate);\n\n        \n        JavaCall(geogebra,\"evalXML\",code);\n        \n        Decide(options[\"lines\"] =? True And? labelIndex >? 1,\n        {\n            \n            command := PatchString(\"a<?Write(labelIndex-1);?> = Segment[A<?Write(labelIndex-1);?>,A<?Write(labelIndex);?>]\");\n            JavaCall(geogebra, \"evalCommand\", command);\n\n            \n            code := PatchString(segmentElementTemplate);\n            JavaCall(geogebra,\"evalXML\",code);\n        }\n        );\n       \n        labelIndex++;\n    }; //end while.\n    \n     //todo:tk: this does not seem to be working  JavaCall(geogebra, \"setRepaintingActive\", \"true\");\n     //JavaCall(geogebra, \"setLayerVisible\", \"0\", \"True\");\n\n};\n\n\n5 # GeoGebraPlot(list_List?, _options)::(Matrix?(list)) <--\n{\n    Local(flatList);\n    \n    flatList := [];\n    \n    ForEach(subList,list)\n    {\n        DestructiveAppend(flatList,subList[1]);\n        DestructiveAppend(flatList, subList[2]);\n    };\n    \n    GeoGebraPlot(flatList, options);\n\n};\n\n//HoldArgument(\"GeoGebraPlot\",\"arg2\");\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/geogebra/GeoGebraPlot.mpw, (GeoGebraPlot)";
        scriptMap.put("GeoGebraPlot",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "//Retract(\"GeoGebraPoint\",*);\n\n10 # GeoGebraPoint(name_String?, x_Number?, y_Number?)  <--\n{\n    Local(command);\n    \n    command := PatchString(\"<?Write(ToAtom(name));?>=(<?Write(x);?>,<?Write(y);?>)\");\n    \n    JavaCall(geogebra,\"evalCommand\",command);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/geogebra/GeoGebraPoint.mpw, (GeoGebraPoint)";
        scriptMap.put("GeoGebraPoint",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"ggbLine\", *);\n\nggbLine(point1Label, point2Label) :=\n{\n    Local(command);\n    \n    command := PatchString(\"Line[<?Write(ToAtom(point1Label));?>,<?Write(ToAtom(point2Label));?>]\");\n    \n    \n    JavaCall(geogebra,\"evalCommand\",command);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/geogebra/ggbLine.mpw, (GgbLine)";
        scriptMap.put("GgbLine",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nDistance(PointA_Point?,PointB_Point?) <--\n{\n    Local(x1,x2,y1,y2,distance);\n\n    x1 := PointA[1];\n    x2 := PointB[1];\n    y1 := PointA[2];\n    y2 := PointB[2];\n    \n    distance := Sqrt((x2 - x1)^2 + (y2 - y1)^2);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/geometry/Distance.mpw, (Distance)";
        scriptMap.put("Distance",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nMidpoint(PointA_Point?,PointB_Point?) <-- \n{\n    Local(x1,x2,y1,y2,midpointX,midpointY);\n    \n    x1 := PointA[1];\n    x2 := PointB[1];\n    y1 := PointA[2];\n    y2 := PointB[2];\n\n    midpointX := (x1 + x2)/2;\n    midpointY := (y1 + y2)/2;\n    \n    [midpointX,midpointY];\n\n};\n\n\n\nMidpoint(segment_Segment?) <-- \n{\n    Local(x1,x2,y1,y2,midpointX,midpointY);\n    \n    x1 := segment[1][1];\n    x2 := segment[2][1];\n    y1 := segment[1][2];\n    y2 := segment[2][2];\n\n    midpointX := (x1 + x2)/2;\n    midpointY := (y1 + y2)/2;\n    \n    [midpointX,midpointY];\n\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/geometry/Midpoint.mpw, (Midpoint)";
        scriptMap.put("Midpoint",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nPoint(x,y) := List(x,y);\n\nPoint(x,y,z) := List(x,y,z);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/geometry/Point.mpw, (Point)";
        scriptMap.put("Point",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nPoint?(p) := Decide(List?(p) And? (Length(p) =? 2 Or? Length(p) =? 3),True,False);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/geometry/Point_.mpw, (Point?)";
        scriptMap.put("Point?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nSegment(PointA_Point?,PointB_Point?) <--\n{\n    Local(x1,x2,y1,y2);\n    \n    x1 := PointA[1];\n    x2 := PointB[1];\n    y1 := PointA[2];\n    y2 := PointB[2];\n\n    [[x1,y1],[x2,y2]];\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/geometry/Segment.mpw, (Segment)";
        scriptMap.put("Segment",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nSegment?(list_List?) <--\n{\n    Decide(List?(list[1]) And? Length(list[1])=?2 And? List?(list[2]) And? Length(list[2])=?2,True,False);\n\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/geometry/Segment_.mpw, (Segment?)";
        scriptMap.put("Segment?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # Slope(PointA_Point?,PointB_Point?) <--\n{\n    Local(x1,x2,y1,y2,slope);\n\n    x1 := PointA[1];\n    x2 := PointB[1];\n    y1 := PointA[2];\n    y2 := PointB[2];\n    \n    slope := (y2 - y1)/(x2 - x1);\n};\n\n\n\n10 # Slope(segment_List?)::(Length(segment) =? 2 And? Length(segment[1]) =? 2 And? Length(segment[2]) =? 2) <--\n{\n    Local(x1,x2,y1,y2,slope);\n    \n    x1 := segment[1][1]; //PointA[1];\n    x2 := segment[2][1]; //PointB[1];\n    \n    \n    y1 := segment[1][2]; //PointA[2];\n    y2 := segment[2][2]; //PointB[2];\n\n    slope := (y2 - y1)/(x2 - x1);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/geometry/Slope.mpw, (Slope)";
        scriptMap.put("Slope",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nBooleanList(elements, integerBitPattern) :=\n{\n    Local(stringBitPattern, leadingDigitsCount, atomBitPattern);\n    \n    Check(Integer?(elements) And? elements >? 0, \"Argument\", \"The first argument must be an integer that is > 0.\");\n    \n    Check(Integer?(integerBitPattern) And? integerBitPattern >=? 0, \"Argument\", \"The second argument must be an integer that is >= 0.\");\n    \n    stringBitPattern := ToBase(2,integerBitPattern);\n    \n    leadingDigitsCount := elements - Length(stringBitPattern);\n    \n   \n    Decide(leadingDigitsCount >? 0, stringBitPattern := Concat(FillList(\"0\",leadingDigitsCount),  StringToList(stringBitPattern)));\n    \n    atomBitPattern := MapSingle(\"ToAtom\", stringBitPattern);\n    \n    atomBitPattern /: [0 <- False, 1 <- True];\n    \n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/logic/BooleanList.mpw, (BooleanList)";
        scriptMap.put("BooleanList",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nBooleanLists(elements) :=\n{\n    Local(numberOfPatterns);\n    \n    Check(elements >? 0, \"Argument\", \"The argument must be > 0.\");\n    \n    numberOfPatterns := 2^elements - 1;\n    \n    BuildList(BooleanList(elements, pattern), pattern,0, numberOfPatterns, 1);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/logic/BooleanLists.mpw, (BooleanLists)";
        scriptMap.put("BooleanLists",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nSubexpressions(expression) :=\n{\n    Local(subexpressions, uniqueSubexpressions, variables, functions, sortedFunctions);\n    \n     subexpressions := SubexpressionsHelper(expression,[]);\n     \n     uniqueSubexpressions := RemoveDuplicates(subexpressions);\n     \n     variables := VarList(uniqueSubexpressions);\n     \n     functions := Select(uniqueSubexpressions, \"Function?\");\n\n     sortedFunctions := Sort(functions,Lambda([x,y],Length(FunctionListAllHelper(x)) + Length(VarList(x)) <? Length(FunctionListAllHelper(y)) + Length(VarList(y))));\n     \n     Concat(variables, sortedFunctions);\n};\n\n\n\nSubexpressionsHelper(expression, list) :=\n{\n\n    Local(first, rest);\n    \n    \n    If((Not? Atom?(expression)) And? (Length(expression) !=? 0))\n    {\n    \n        first := First(expression);\n        \n        list := SubexpressionsHelper(first, list);\n        \n        rest := Rest(expression);\n        \n        Decide(Length(rest) !=? 0, rest := First(rest));\n     \n        Decide(Length(rest) !=? 0, list := SubexpressionsHelper(rest, list));\n    };\n    \n    Append(list, expression);\n};\n\n\n\n//Similar to FuncList, but does not remove duplicates.\nFunctionListAllHelper(expression) :=\n{\n    If(Atom?(expression))\n    {\n       []; \n    }\n    Else If(Function?(expression))\n    {\n    \n      Concat( [First(FunctionToList(expression))],\n         Apply(\"Concat\", MapSingle(\"FunctionListAllHelper\", Rest(FunctionToList(expression)))));\n    }\n    Else\n    {\n        Check(False, \"Argument\", \"The argument must be an atom or a function.\");\n    \n    };\n\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/logic/Subexpressions.mpw, (Subexpressions)";
        scriptMap.put("Subexpressions",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nTruthTable(booleanExpression) :=\n{\n    Local(resultList, variables, booleanPatterns, subexpressions, substitutionList, evaluation);\n    \n    resultList := [];\n    \n    variables := VarList(booleanExpression);\n    \n    booleanPatterns := Reverse(BooleanLists(Length(variables)));\n    \n    subexpressions := Subexpressions(booleanExpression);\n    \n    DestructiveAppend(resultList, subexpressions);\n    \n\n    ForEach(booleanPattern, booleanPatterns)\n    {\n        substitutionList := Map(\"==\", [variables, booleanPattern]);\n        \n        evaluation := `(subexpressions Where @substitutionList);\n        \n        DestructiveAppend(resultList, evaluation);\n    \n    };\n    \n    resultList;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/logic/TruthTable.mpw, (TruthTable)";
        scriptMap.put("TruthTable",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/*\n    The algorithm this function uses is on pp. 299-300 of\n    \"Discrete Mathematics and Its Applications\" (fourth edition)\n    by Kenneth H. Rosen.\n*/\nCombinationsList(inputList, r) :=\n{\n    Local(n,manipulatedIndexes,totalCombinations,combinationsList,combinationsLeft,combination,i,j,currentIndexes);\n    \n    Check(List?(inputList) And? Length(inputList) >=? 1, \"Argument\", \"The first argument must be a list with 1 or more elements.\");\n    \n    n := Length(inputList);\n    \n    Check(r <=? n , \"Argument\", \"The second argument must be <=? the length of the list.\");\n    \n    manipulatedIndexes := 1 .. r; \n    \n    totalCombinations := Combinations(n,r);\n    \n    combinationsLeft := totalCombinations;\n    \n    combinationsList := [];\n    \n    While(combinationsLeft >? 0)\n    {\n        combination := [];\n      \n        If(combinationsLeft =? totalCombinations)\n        { \n          combinationsLeft := combinationsLeft - 1;\n          \n          currentIndexes := manipulatedIndexes;\n        }\n        Else\n        {\n            i := r;\n        \n            While(manipulatedIndexes[i] =? n - r + i)\n            {\n              i--;\n            };\n            \n            manipulatedIndexes[i] := manipulatedIndexes[i] + 1;\n            \n            For(j := i + 1, j <=? r, j++) \n            {\n              manipulatedIndexes[j] := manipulatedIndexes[i] + j - i;\n            };\n        \n            combinationsLeft := combinationsLeft - 1;\n            \n            currentIndexes := manipulatedIndexes;\n        };\n\n        For(i := 1, i <=? Length(currentIndexes), i++) \n        {\n            combination := Append(combination,(inputList[currentIndexes[i]]));\n        };\n      \n        combinationsList := Append(combinationsList,combination);\n    };\n    \n    combinationsList;\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/miscellaneous/CombinationsList.mpw, (CombinationsList)";
        scriptMap.put("CombinationsList",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"ElementCount\",*);\n\nElementCount(list) :=\n{   \n    If(Length(list) =? 0)\n    {\n        0;\n    }\n    Else If(Atom?(list))\n    {\n        1;\n    }\n    Else\n    {\n        ElementCount(First(list)) + ElementCount(Rest(list));\n    };\n\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/miscellaneous/ElementCount.mpw, (ElementCount)";
        scriptMap.put("ElementCount",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nListOfLists?(listOfLists) :=\n{\n    Local(result);\n    \n    result := True;\n    \n    If(Not? List?(listOfLists))\n    {\n        result := False;\n    }\n    Else \n    {\n        ForEach(list, listOfLists)\n        {\n            Decide(Not? List?(list), result := False);\n        };\n    };\n    \n    result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/miscellaneous/ListOfLists_.mpw, (ListOfLists?)";
        scriptMap.put("ListOfLists?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\n//Retract(\"ListToString\", *);\n\n\n10 # ListToString(list_List?)::(Length(list) =? 0) <-- \"\";\n\n\n\n20 # ListToString(list_List?) <--\n{\n    Local(resultString, character);\n    \n    resultString := \"\";\n    \n    ForEach(element, list)\n    {\n        Decide(String?(element), character := element, character := ToString(element));\n        \n        resultString := resultString ~ character;\n    };\n    \n    resultString;\n\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/miscellaneous/ListToString.mpw, (ListToString)";
        scriptMap.put("ListToString",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"NumberLineZoom\", *);\n\n//Retract(\"ZoomInOnce\", *);\n\nLocalSymbols(ZoomInOnce)\n{\n\n    10 # NumberLinePrintZoom(_lowValue, _highValue, divisions_PositiveInteger?, depth_PositiveInteger?)::(lowValue <? highValue) <-- \n    {\n    \n        Local(numbers, stepAmount, zoomIndexes, nextZoomIndex, outputWidth, numbersString, output, randomStep, randomZoomNumber, iteration);\n        \n        iteration := 1;\n        \n        While(iteration <=? depth)\n        {\n            [numbers, stepAmount] := ZoomInOnce(lowValue, highValue, divisions);\n            \n            zoomIndexes := [];\n            \n            outputWidth := 0;\n            \n            numbersString := \"\";\n            \n            ForEach(number, numbers)\n            {\n                output := PipeToString() Write(number);\n                \n                zoomIndexes := Append(zoomIndexes, Length(output));\n                \n                numbersString := numbersString ~ output ~ PipeToString() Space(3);\n                \n                outputWidth := outputWidth + Length(output) + 3;\n            \n            };\n            \n            randomStep := RandomInteger(divisions);\n            \n            randomZoomNumber := Sum(Take(zoomIndexes, randomStep));\n            \n            Decide(randomStep =? 1, nextZoomIndex := randomZoomNumber + 1, nextZoomIndex := 3*(randomStep-1) + randomZoomNumber + 1);\n         \n            Decide(iteration >? 1, Echo(ListToString(FillList(\"-\", outputWidth-3)))); \n            \n            Echo(numbersString);\n            \n            Decide(iteration !=? depth,{Space(nextZoomIndex);Echo(\"|\");});\n            \n            lowValue := numbers[randomStep];\n            \n            highValue := numbers[randomStep+1];\n            \n            iteration++;\n            \n        };\n    \n    };\n    \n    \n    \n    \n    ZoomInOnce(_lowValue, _highValue, divisions_PositiveInteger?)::(lowValue <? highValue) <--\n    {\n        Local(stepAmount, x, numbers);\n        \n        stepAmount := Decide(Decimal?(lowValue) Or? Decimal?(highValue), NM((highValue-lowValue)/divisions), (highValue-lowValue)/divisions);\n        \n        x := lowValue;\n        \n        numbers := [];\n        \n        While(x <=? highValue)\n        {\n        \n            numbers := Append(numbers, x);\n            \n            x := x + stepAmount;\n        \n        };\n        \n        [numbers, stepAmount];\n    \n    };\n\n\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/miscellaneous/NumberLinePrintZoom.mpw, (NumberLinePrintZoom;ZoomInOnce)";
        scriptMap.put("NumberLinePrintZoom",scriptString);
        scriptMap.put("ZoomInOnce",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "OptionsToAssociativeList(optionList) :=\n{\n    Local(associativeList, key, value);\n    \n    associativeList := [];\n    \n    ForEach(option, optionList)\n    {\n        Decide(option[0] =? : ,\n        {\n            Decide(String?(option[1]), key := option[1], key := ToString(option[1]));\n            Decide(String?(option[2]) Or? Number?(option[2]) Or? Constant?(option[2]), value := option[2], value := ToString(option[2]));\n            \n            associativeList := [key, value] ~ associativeList;\n        \n        });\n    \n    };\n    associativeList;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/miscellaneous/OptionsToAssociativeList.mpw, (OptionsToAssociativeList)";
        scriptMap.put("OptionsToAssociativeList",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"PadLeft\", *);\n\n10 # PadLeft(number_Number?, totalDigits_Integer?) <--\n{\n    Local(integerString, padAmount, resultString);\n    \n    integerString := ToString(number);\n    \n    padAmount := totalDigits - Length(integerString);\n    \n    Decide(padAmount >? 0,\n        resultString := ListToString(FillList(0, padAmount)) ~ integerString,\n        resultString := integerString );\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/miscellaneous/PadLeft.mpw, (PadLeft)";
        scriptMap.put("PadLeft",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"StringToList\", *);\n\n10 # StringToList(string_String?)::(Length(string) =? 0) <-- [];\n\n\n20 # StringToList(string_String?) <--\n{\n    Local(resultList);\n    \n    resultList := [];\n    \n    ForEach(character, string)\n    {\n        resultList := Append(resultList, character);\n    };\n    \n    resultList;\n\n};\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/miscellaneous/StringToList.mpw, (StringToList)";
        scriptMap.put("StringToList",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "//Retract(\"StringToNumber\",*);\n\nStringToNumber( str_String? ) <-- FromBase(10,str);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/miscellaneous/StringToNumber.mpw, (StringToNumber)";
        scriptMap.put("StringToNumber",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nVerifyNumeric(expression1, expression2, optionsList) :=\n{\n    Local(variablesList1, variablesList2, numericValue1, numericValue2, numericDifference, optionsVariableNamesList, optionsValuesList, associativeList);\n \n    variablesList1 := VarList(expression1);\n    \n    variablesList2 := VarList(expression2);\n    \n    If(Length(variablesList1) =? 0 And? Length(variablesList2) =? 0)\n    {\n        numericValue1 := NM(expression1);\n        \n        numericValue2 := NM(expression2);\n    }\n    Else\n    {\n        optionsList := HeapSort(optionsList, Lambda([x,y],LessThan?(x[1],y[1])));\n        \n        associativeList := OptionsToAssociativeList(optionsList);\n        \n        optionsVariableNamesList := MapSingle(\"ToAtom\", AssocIndices(associativeList));\n        \n        optionsValuesList := MapSingle(\"ToAtom\", AssocValues(associativeList));\n        \n        variablesList1 := HeapSort(variablesList1,\"LessThan?\");\n        \n        variablesList2 := HeapSort(variablesList2,\"LessThan?\");\n        \n        Check(variablesList1 =? variablesList2 And? variablesList1 =? optionsVariableNamesList, \"Argument\", \"Both expressions and the options list must have the same variable names and the same number of variables.\");\n        \n        numericValue1 := NM(WithValue(variablesList1, optionsValuesList, expression1));\n        \n        numericValue2 := NM(WithValue(variablesList2, optionsValuesList, expression2 ));\n        \n        Echo(Map(\":\",[variablesList1, optionsValuesList]));\n        \n        NewLine();\n    };\n    \n    Echo(expression1, \"-> \", numericValue1);\n    \n    NewLine();\n    \n    Echo(expression2, \"-> \", numericValue2);\n    \n    numericDifference := NM(numericValue1 - numericValue2);\n        \n    NewLine();\n    \n    Echo(\"Difference between the numeric values: \", numericDifference);\n    \n    numericDifference;\n};\n\n\n\n\nVerifyNumeric(expression1, expression2) :=\n{\n    VerifyNumeric(expression1, expression2, []);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/miscellaneous/VerifyNumeric.mpw, (VerifyNumeric)";
        scriptMap.put("VerifyNumeric",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Note:tk:Perhaps create a function called Value that obtains the value that is assigned to a variable\n// without evaluating the variable first.\n\n\n    Retract(\"+$\", *);\n    Retract(\"-$\", *);\n    Retract(\"/$\", *);\n    Retract(\"*$\", *);\n    Retract(\"^$\", *);\n\n\n    \n\n    Infix(\"+$\",70);\n    Infix(\"-$\",70);\n    RightPrecedenceSet(\"-$\",40);\n    Infix(\"/$\",30);\n    Infix(\"*$\",40);\n    Infix(\"^$\",20);\n    LeftPrecedenceSet(\"^$\",19); //Added to make expressions like x^n^2 unambiguous.\n    RightAssociativeSet(\"^$\");\n    Prefix(\"+$\",50);\n    Prefix(\"-$\",50);\n    RightPrecedenceSet(\"-$\",40);\n    \n    \n    RulebaseEvaluateArguments(\"+$\",[lhs, rhs]);\n    RulebaseEvaluateArguments(\"-$\",[lhs, rhs]);\n    RulebaseEvaluateArguments(\"/$\",[lhs, rhs]);\n    RulebaseEvaluateArguments(\"*$\",[lhs, rhs]);\n    RulebaseEvaluateArguments(\"^$\",[lhs, rhs]);\n    RulebaseEvaluateArguments(\"+$\",[operand]);\n    RulebaseEvaluateArguments(\"-$\",[operand]);\n\n    \n\n\nPosition(subTerm, term) :=\n{\n    Local(positionList);\n    \n    Check(Function?(term), \"Argument\", \"The argument must be an expression that contains a function.\");\n    \n    positionList := PositionHelper(subTerm, term,[False]);\n    \n    positionList := Rest(positionList);\n    \n    Rest(Reverse(positionList));\n\n};\n\n\n\n\nPositionHelper(subTerm, term, list) :=\n{   \n    Local(argPosition);\n\n    If(Atom?(term) And? (subTerm =? term))\n    {\n        list[1] := True;\n        \n        list;\n    }\n    Else If(Atom?(term) )\n    {\n        list;\n    }\n    Else\n    {\n        argPosition := Length(term);\n        \n        While(argPosition >? 0)\n        {  \n            list := PositionHelper(subTerm, term[argPosition], list);\n            \n            Decide(list[1] =? True, {list := Append(list, argPosition);argPosition := -1;});\n        \n            argPosition--;\n        };\n        \n        list;\n    };\n};\n\n\n\n\nOccurrence(expression, variable) :=\n{\n    Check(Function?(expression), \"Argument\", \"The argument must be an expression that contains a function.\");\n\n    Count(VarListAll(expression), variable);\n};\n\n\n\nSingleOccurrence?(expression, variable) :=\n{\n    Occurrence(expression, variable) =? 1;\n};\n\n\n\n\n//====================================================================\nRetract(\"SolveEquation\",*);\n\n\n\nRulebaseListedHoldArguments(\"SolveEquation\",[_equation, _variable, _optionsList]);\n\n//Handle no options call.\n5 # SolveEquation(_equation, _variable) <-- SolveEquation(equation, variable, []);\n\n\n//Main routine.  It will automatically accept two or more option calls because the\n//options come in a list.\n10 # SolveEquation(_equation, _variable, optionsList_List?) <--\n{\n    Local(options, path, steps);\n    \n    options := OptionsToAssociativeList(optionsList);\n    \n    Check(Occurrence(equation, variable) >? 0, \"Argument\", \"The variable \" ~ ToString(variable) ~ \" does not occur in the equation.\");\n    \n    Check(Occurrence(equation, variable) =? 1, \"Argument\", \"Equations with multiple occurrences of the unknown are not currently supported.\");\n    \n    equation := AddDollarSigns(equation);\n     \n    steps := Isolate(variable, equation);\n};\n\n\n//Handle a single option call because the option does not come in a list for some reason.\n20 # SolveEquation(_equation, _variable, _singleOption) <-- SolveEquation(equation, variable, [singleOption]);\n\n\n\n//===================================================================\n\n\n\n\n\n\n\nIsolate(variable, equation) :=\n{\n    Local(result, rest, currentEquation, path, showTree);\n\n    result := [];\n    \n    path := Position(variable, equation);\n    \n    DestructiveAppend(result, [equation,\"Equation\", \"The original equation.\", variable]);\n    \n    rest := [equation];\n\n    ForEach(pathNumber, path)\n    {\n        currentEquation := First(rest);\n    \n        rest := Isolax(pathNumber, currentEquation);\n        \n        DestructiveAppend(result, rest);\n    };\n\n    \n    result;\n};\n\n\n\n\n//================================================\n\n\nRetract(\"StepsView\",*);\n\n\n\nRulebaseListedHoldArguments(\"StepsView\",[_steps, _optionsList]);\n\n//Handle no options call.\n5 # StepsView(_steps) <-- StepsView(steps, []);\n\n\n//Main routine.  It will automatically accept two or more option calls because the\n//options come in a list.\n10 # StepsView(_steps, optionsList_List?) <--\n{\n    Local(result, currentEquation, options, message, treeScale, latexScale);\n    \n    result := [];\n        \n    options := OptionsToAssociativeList(optionsList);\n    \n    If(Number?(options[\"Scale\"]))\n    {\n        treeScale := options[\"Scale\"];\n    }\n    Else\n    {\n        treeScale := 1.5;\n    };\n    \n    latexScale := treeScale * 17;\n    \n    message := \n\"\\\\footnotesize \\\\mbox{\nThe dominant operator on the left side of an equation is the\\\\\\\\\noperator that has the lowest precedence. This is always the top \\\\\\\\\noperator on the left side of the equation's expression tree.\\\\\\\\\n\\\\vspace{.75in}\nThe expression tree of an equation that has only one occurrence\\\\\\\\\nof the variable to be isolated can be solved using the following\\\\\\\\\nprocedure:\\\\\\\\\n\\\\vspace{.75in}\n1) Identify the operator that is at the top of the left side of the\\\\\\\\\n\\\\hspace{.5in}tree (it is highlighted in the trees below).\\\\\\\\\n\\\\vspace{.75in}\n2) Identify the operand of this operator that does not contain the\\\\\\\\\n\\\\hspace{.75in}variable to be isolated.\\\\\\\\\n\\\\vspace{.75in}\n3) Remove the top operator (along with the operand from step 2)\\\\\\\\\n\\\\hspace{.5in}from the left side of the tree, and add the inverse of\\\\\\\\\n\\\\hspace{.5in}this operator (along with the operand from step 2) to\\\\\\\\\n\\\\hspace{.5in}the top of the right side of the tree.\\\\\\\\\n\\\\vspace{.75in}\n}\";\n    \n    DestructiveAppend(result, LatexView(message, Scale: latexScale));\n    \n\n    \n    message := \"\\\\text{Solve}\\\\ \" ~ ToString(ToAtom(ListToString(Remove(StringToList(UnparseLatex(TagLeftSideDominantFunction(RemoveDollarSigns(steps[1][1])))),\"$\"))) ) ~ \"\\\\ \\\\text{for}\\\\ \" ~ ToString(steps[1][4]) ~ \".\";\n    DestructiveAppend(result, LatexView(message, Scale: latexScale));\n    \n    \n    Decide(options[\"ShowTree\"] =? True, DestructiveAppend(result, TreeView(TagLeftSideDominantFunction(RemoveDollarSigns(steps[1][1])), Scale: treeScale, Resizable: False, IncludeExpression: False)));\n    \n    \n    steps := Rest(steps);\n    \n\n    ForEach(rest, steps)\n    {   \n        If(Not? List?(rest[1]))\n        {\n            result := Compose(result, rest[1], rest[3], rest[4], Length(rest) =? 4, options[\"ShowTree\"], treeScale, latexScale);\n        }\n        Else\n        {\n            result := Compose(result, rest[1][1], rest[3][1], rest[4][1], Length(rest) =? 4, options[\"ShowTree\"],  treeScale, latexScale);\n            \n            result := Compose(result, rest[1][2], rest[3][2], rest[4][2], Length(rest) =? 4, options[\"ShowTree\"],  treeScale, latexScale);\n        };\n        \n    };\n\n    \n    result;\n};\n\n\n//Handle a single option call because the option does not come in a list for some reason.\n20 # StepsView(_steps, _singleOption) <-- StepsView(steps, [singleOption]);\n\n\n\n//====================================================\n\n\n\n\nCompose(result, newExpression, explanation, beforeSimplification, lengthFour?, showTree?, treeScale, latexScale) :=\n{\n    DestructiveAppend(result, LatexView(\" \\\\vspace{.75in}\", Scale: latexScale));\n\n    DestructiveAppend(result, LatexView(ToLatex(explanation),Scale: latexScale));\n    \n    If(lengthFour?)\n    {\n        DestructiveAppend(result, LatexView(\" \\\\vspace{.10in}\", Scale: latexScale));\n        DestructiveAppend(result, LatexView(TexFormNoDollarSigns(beforeSimplification),Scale: latexScale));\n    };\n    \n    DestructiveAppend(result, LatexView(\" \\\\vspace{.10in}\", Scale: latexScale));\n    \n    \n    DestructiveAppend(result, LatexView(ToAtom(ListToString(Remove(StringToList(UnparseLatex(TagLeftSideDominantFunction(RemoveDollarSigns(newExpression)))),\"$\"))), Scale: latexScale ));\n    \n    \n    Decide(showTree? =? True, DestructiveAppend(result, TreeView(TagLeftSideDominantFunction(RemoveDollarSigns(newExpression)), Scale: treeScale, Resizable: False, IncludeExpression: False)));\n    \n    result;   \n};\n\n\nTagLeftSideDominantFunction(expression) :=\n{\n    If(Function?(expression) And? Function?(Cdr(Car(expression))))\n    {\n        MetaSet(Car(Cdr(Car(expression))),\"HighlightColor\",\"GREEN\");\n        \n        MetaSet(Car(Cdr(Car(expression))),\"HighlightNodeShape\",\"RECTANGLE\");\n    };\n    \n    expression;\n};\n\n\n\n\n\nRemoveDollarSigns(equation) :=\n{\n    equation := Substitute(ToAtom(\"+$\"),ToAtom(\"+\")) equation;\n    equation := Substitute(ToAtom(\"-$\"),ToAtom(\"-\")) equation;\n    equation := Substitute(ToAtom(\"*$\"),ToAtom(\"*\")) equation;\n    equation := Substitute(ToAtom(\"/$\"),ToAtom(\"/\")) equation;\n    equation := Substitute(ToAtom(\"^$\"),ToAtom(\"^\")) equation;\n};\n\n\nAddDollarSigns(equation) :=\n{\n    equation := Substitute(ToAtom(\"+\"),ToAtom(\"+$\")) equation;\n    equation := Substitute(ToAtom(\"-\"),ToAtom(\"-$\")) equation;\n    equation := Substitute(ToAtom(\"*\"),ToAtom(\"*$\")) equation;\n    equation := Substitute(ToAtom(\"/\"),ToAtom(\"/$\")) equation;\n    equation := Substitute(ToAtom(\"^\"),ToAtom(\"^$\")) equation;\n};\n\n\n\nTexFormNoDollarSigns(equation) := \n{\n    ToAtom(ListToString(Remove(StringToList(UnparseLatex(RemoveDollarSigns(equation))),\"$\")));\n};\n\n\n\n\nToLatex(string) :=\n{\n    Local(oldList, newList);\n    \n    oldList := StringToList(string);\n    \n    oldList := Remove(oldList, \"$\");\n    \n    newList := [];\n\n    ForEach(character, oldList)\n    {\n        DestructiveAppend(newList, Decide(character =? \" \", \"\\\\ \", character));\n    };\n    \n    ListToString(newList);\n};\n\n\n\n\nIsolax(side, equation) :=\n{\n    \n    `(@side : equation) /: [\n    \n     1 : -$ _lhs == _rhs <- [lhs == -$ rhs, \"UnaryMinus\", \"\\\\text{Multiply both sides by }\" ~ ToString(-1) ~ \"\\\\text{:}\", -1 *$ -$ lhs == rhs *$ -1],\n     \n     1 : _term1 +$ _term2 == _rhs <- [term1 == rhs -$ term2, \"Addition1\", \"\\\\text{Subtract }\" ~ ToString(term2) ~ \"\\\\text{ from both sides:}\", -$ term2 +$ term1 +$ term2 == rhs -$ term2],\n     2 : _term1 +$ _term2 == _rhs <- [term2 == rhs -$ term1, \"Addition2\", \"\\\\text{Subtract }\" ~ ToString(term1) ~ \"\\\\text{ from both sides:}\", -$ term1 +$ term1 +$ term2 == rhs -$ term1],\n     \n     1 : _term1 -$ _term2 == _rhs <- [term1 == rhs +$ term2, \"Subtraction1\", \"\\\\text{Add }\" ~ ToString(term2) ~ \"\\\\text{ to both sides:}\", term2 +$ term1 -$ term2 == rhs +$ term2],\n     2 : _term1 -$ _term2 == _rhs <- [term2 == term1 -$ rhs, \"Subtraction2\", \"\\\\text{Add }\" ~ ToString(term1) ~ \"\\\\text{ to both sides:}\", term1 +$ term1 -$ term2 == rhs +$ term1],\n     \n     1 : _term1 *$ _term2 == _rhs <- [term1 == rhs /$ term2, \"Multiplication1\", \"\\\\text{Divide both sides by }\" ~ ToString(term2) ~ \"\\\\text{:}\", (term1 *$ term2) /$ term2 == rhs /$ term2],\n     2 : _term1 *$ _term2 == _rhs <- [term2 == rhs /$ term1, \"Multiplication2\", \"\\\\text{Divide both sides by }\" ~ ToString(term1) ~ \"\\\\text{:}\", (term1 *$ term2) /$ term1 == rhs /$ term1],\n     \n     1 : _term1 /$ _term2 == _rhs <- [term1 == rhs *$ term2, \"Division1\", \"\\\\text{Multiply both sides by }\" ~ ToString(term2) ~ \"\\\\text{:}\", term2 *$ (term1 /$ term2) == rhs *$ term2],\n     2 : _term1 /$ _term2 == _rhs <- [[rhs *$ term2 == term1, term2 == term1 /$ rhs], \"Division2\", [\"\\\\text{Multiply both sides by }\" ~ ToString(term2) ~ \"\\\\text{ and exchange sides:}\", \"\\\\text{Divide both sides by }\" ~ ToString(TexFormNoDollarSigns(rhs)) ~ \"\\\\text{:}\"], [(term1 /$ term2) *$ term2 == rhs *$ term2, (rhs *$ term2) /$ rhs == term1 /$ rhs]],\n     \n     \n     \n     //1 : _term1 ^$ 2 == _rhs <- [term1 == Sqrt(rhs), \"Exponentiation1a\", \"\\\\text{Take the square root of both sides:}\", Sqrt(term1 ^$ 2) == Sqrt(rhs)],\n     \n     1 : _term1 ^$ _term2 == _rhs <- [term1 == rhs^$(1/$term2), \"Exponentiation1b\", \"\\\\text{Take root }\" ~ ToString(term2) ~ \"\\\\text{ of both sides:}\", (term1 ^$ term2)^$(1/term2) == rhs^$(1/$ term2)],\n    //\"Exponentiation2\" # 2 :: _term1 ^ _term2 == _rhs <- term2 == Log(rhs)//Log(base(term1), rhs)\n                      \n    ];                 \n};\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/prolog/press.mpw, (Position;PositionHelper;Occurrence;SingleOccurrence?;SolveEquation;Isolate;RemoveDollarSigns;AddDollarSigns;TexFormNoDollarSigns;Isolax;StepsView;ToLatex)";
        scriptMap.put("Position",scriptString);
        scriptMap.put("PositionHelper",scriptString);
        scriptMap.put("Occurrence",scriptString);
        scriptMap.put("SingleOccurrence?",scriptString);
        scriptMap.put("SolveEquation",scriptString);
        scriptMap.put("Isolate",scriptString);
        scriptMap.put("RemoveDollarSigns",scriptString);
        scriptMap.put("AddDollarSigns",scriptString);
        scriptMap.put("TexFormNoDollarSigns",scriptString);
        scriptMap.put("Isolax",scriptString);
        scriptMap.put("StepsView",scriptString);
        scriptMap.put("ToLatex",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* \n    This function was adapted from the Javascript version of function\n    that is located here:\n    \n    http://www.fourmilab.ch/rpkp/experiments/analysis/chiCalc.js\n    http://www.fourmilab.ch/rpkp/experiments/analysis/chiCalc.html\n    \n    The following JavaScript functions for calculating normal and\n    chi-square probabilities and critical values were adapted by\n    John Walker from C implementations\n    written by Gary Perlman of Wang Institute, Tyngsboro, MA\n    01879. Both the original C code and this JavaScript edition\n    are in the public domain. \n*/\n\n\n\n/*  CRITCHI  --  Compute critical chi-square value to\n                     produce given p.  We just do a bisection\n                     search for a value within CHI_EPSILON,\n                     relying on the monotonicity of pochisq().  \n*/\n\n\nAlphaToChiSquareScore(p, df) :=\n{\n    Local(ChiEpsilon, ChiMax, minchisq, maxchisq, chisqval, result);\n    \n    ChiEpsilon := 0.000001;   /* Accuracy of critchi approximation */\n    \n    ChiMax := 99999.0;        /* Maximum chi-square value */\n    \n    minchisq := 0.0;\n    \n    maxchisq := ChiMax;\n    \n    p := NM(p);\n    \n    If( p <=? 0.0 Or? p >=? 1.0)\n    {\n    \n        If(p <=? 0.0) \n        {\n            result := maxchisq;\n        } \n        Else \n        {\n            If(p >=? 1.0) \n            {\n                result := 0.0;\n            };\n        };\n    \n    }\n    Else\n    {\n        chisqval := NM(df / SqrtN(p));\n        \n        /* fair first value */\n        While ((maxchisq - minchisq) >? ChiEpsilon) \n        {\n            If(ChiSquareScoreToAlpha(chisqval, df) <? p) \n            {\n                maxchisq := chisqval;\n            } \n            Else \n            {\n                minchisq := chisqval;\n            };\n            chisqval := (maxchisq + minchisq) * 0.5;\n        };\n        \n        result := chisqval;\n    \n    };\n    \n    NM(result);\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/AlphaToChiSquareScore.mpw, (AlphaToChiSquareScore)";
        scriptMap.put("AlphaToChiSquareScore",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\nAnovaCompletelyRandomizedBlock(levelsList, alpha) :=\n{\n    Check(Matrix?(levelsList), \"Argument\", \"The first argument must be a list of equal-length lists.\");\n    \n    Check(alpha >=? 0 And? alpha <=? 1, \"Argument\", \"The second argument must be a number between 0 and 1.\");\n    \n    Local(\n        topOfSummary,\n        anovaBlockTableRow1,\n        criticalFScore,\n        anovaBlockTableRow3,\n        anovaBlockTableRow2,\n        lengthsList,\n        summaryTableRow,\n        sumsList,\n        meanSquareWithin,\n        topOfPage,\n        htmlJavaString,\n        index,\n        variancesList,\n        grandMean,\n        row,\n        topOfAnovaBlock,\n        result,\n        fScoreBlock,\n        criticalFScoreBlock,\n        blockMeansList,\n        sumOfSquaresWithin,\n        meanSquareBetween,\n        sumOfSquaresBetween,\n        fScore,\n        summaryTableRows,\n        meansList,\n        sumOfSquaresBlock,\n        b,\n        blockSummaryTableRow,\n        bottomOfAnovaBlock,\n        sumOfSquaresWithin,\n        bottomOfPage,\n        k,\n        sumOfSquaresTotal,\n        meanSquareBlock,\n        bottomOfSummary\n    );\n    \n    meansList := [];\n    \n    variancesList := [];\n    \n    sumsList := [];\n    \n    lengthsList := [];\n    \n    \n    //ANOVA calculations.\n    ForEach(levelList, levelsList)\n    {\n        meansList := meansList ~ NM(Mean(levelList));\n        \n        variancesList := variancesList ~ NM(UnbiasedVariance(levelList));\n        \n        sumsList := sumsList ~ NM(Sum(levelList));\n\n        lengthsList := lengthsList ~ Length(levelList);\n    };\n    \n    sumOfSquaresWithin := Sum((lengthsList - 1) * variancesList);\n\n    grandMean := NM(Mean(meansList));\n    \n    sumOfSquaresBetween := Sum(lengthsList*(meansList - grandMean)^2);\n    \n    \n    \n    //Block calculations.\n    blockMeansList := [];\n    \n    index := 1;\n    \n    While(index <=? Length(First(levelsList)) )\n    {\n        row := MatrixColumn(levelsList, index);\n        \n        blockMeansList := Append(blockMeansList,NM(Mean(row)));\n    \n        index++;\n    };\n    \n    b := Length(blockMeansList);\n    \n    k := Length(levelsList);\n    \n    sumOfSquaresBlock := Sum(j,1,b, k*(blockMeansList[j] - grandMean)^2);\n    \n    sumOfSquaresTotal := NM(sumOfSquaresWithin + sumOfSquaresBetween);\n    \n    sumOfSquaresWithin := NM(sumOfSquaresTotal - sumOfSquaresBetween - sumOfSquaresBlock);\n    \n    meanSquareBetween := NM(sumOfSquaresBetween/(k - 1));\n    \n    meanSquareWithin := NM(sumOfSquaresWithin/((k - 1)*(b - 1)));\n    \n    fScore := NM(meanSquareBetween/meanSquareWithin);\n    \n    meanSquareBlock := NM(sumOfSquaresBlock/(b - 1));\n    \n    fScoreBlock := NM(meanSquareBlock/meanSquareWithin);\n    \n    criticalFScore := ProbabilityToFScore(k - 1, (k - 1)*(b - 1), 1-alpha);\n    \n    criticalFScoreBlock := ProbabilityToFScore(b - 1, (k - 1)*(b - 1), 1-alpha);\n    \n    \n    \n    topOfPage :=\n\"\n    <html>\n        <title>\n            Anova: Completely Randomized Block\n        </title>\n        \n        <body>\n\";\n    \n    topOfSummary :=\n\"\n            <h2>Anova: Completely Randomized Block</h2>\n            \n            <TABLE BORDER>\n                <CAPTION align=\\\"left\\\"> <h3>Summary</h3>  </CAPTION>\n                \n                <TR> <TH> Level </TH> <TH> Count</TH> <TH> Sum </TH> <TH> Mean </TH> <TH> Variance </TH> </TR>\n\";\n\n    \n    summaryTableRows := \"\";\n    \n    summaryTableRow := \"<TR> <TD> <?Write(ToAtom(ToString(Level)~ToString(index)));?> </TD> <TD align=\\\"right\\\"> <?Write(lengthsList[index]);?> </TD> <TD> <?Write(sumsList[index]);?> </TD>  <TD> <?Write(meansList[index]);?> </TD> <TD> <?Write(variancesList[index]);?> </TD> </TR>\"~Nl();\n    \n    \n    //Data summary.\n    index := 1;\n    \n    While(index <=? Length(levelsList))\n    {\n        summaryTableRows := summaryTableRows ~ PatchString(summaryTableRow);\n    \n        index++;\n    };\n    \n\n    //Block summary.\n    \n    blockSummaryTableRow := \"<TR> <TD> <?Write(ToAtom(\\\"Block\\\"~ToString(index)));?> </TD> <TD align=\\\"right\\\"> <?Write(Length(row));?> </TD> <TD> <?Write(NM(Sum(row)));?> </TD>  <TD> <?Write(NM(Mean(row)));?> </TD> <TD> <?Write(NM(UnbiasedVariance(row)));?> </TD> </TR>\"~Nl();\n\n    index := 1;\n    \n    While(index <=? Length(First(levelsList)) )\n    {\n        row := MatrixColumn(levelsList, index);\n        \n        summaryTableRows := summaryTableRows ~ PatchString(blockSummaryTableRow);\n    \n        index++;\n    };\n    \n    \n    \n    \n    \n    bottomOfSummary :=\n\"\n            </TABLE>\n\";\n    \n\n\n    topOfAnovaBlock :=\n\"\n            <br \\>\n            <br \\>\n            \n            <TABLE BORDER>\n                <CAPTION align=\\\"left\\\"> <h3>ANOVA: Completely Randomized Block</h3>  </CAPTION>\n                \n                <TR> <TH> Source of Variation </TH> <TH> Sum of Squares </TH> <TH> Degrees of Freedom </TH> <TH> Mean Square </TH> <TH> F </TH> <TH> F Critical </TH> </TR>\n\";\n\n    \n    \n    anovaBlockTableRow1 := PatchString(\"<TR> <TD> <?Write(ToAtom(\\\"Between Levels\\\"));?> </TD> <TD > <?Write(sumOfSquaresBetween);?> </TD> <TD> <?Write(k - 1);?> </TD>   <TD > <?Write(meanSquareBetween);?> </TD><TD> <?Write(fScore);?> </TD> <TD> <?Write(criticalFScore);?> </TD> </TR>\"~Nl());\n    \n    anovaBlockTableRow2 := PatchString(\"<TR> <TD> <?Write(ToAtom(\\\"Between Blocks\\\"));?> </TD> <TD > <?Write(sumOfSquaresBlock);?> </TD> <TD> <?Write(b - 1);?> </TD>   <TD > <?Write(meanSquareBlock);?> </TD> <TD> <?Write(fScoreBlock);?> </TD> <TD> <?Write(criticalFScoreBlock);?> </TD> </TR>\"~Nl());\n\n    anovaBlockTableRow3 := PatchString(\"<TR> <TD> <?Write(ToAtom(\\\"Within Levels\\\"));?> </TD> <TD > <?Write(sumOfSquaresWithin);?> </TD> <TD> <?Write(b - 1);?> </TD>   <TD > <?Write(meanSquareWithin);?> </TD></TR>\"~Nl());\n\n    bottomOfAnovaBlock :=\n\"\n            </TABLE>\n\";\n\n\n    \n    bottomOfPage :=\n\"\n        </body>         \n    </html>\n\";\n    \n    htmlJavaString := JavaNew(\"java.lang.String\",\n                topOfPage ~\n                topOfSummary ~\n                summaryTableRows ~\n                bottomOfSummary ~\n                topOfAnovaBlock ~\n                anovaBlockTableRow1 ~\n                anovaBlockTableRow2 ~\n                anovaBlockTableRow3 ~\n                bottomOfAnovaBlock ~\n                bottomOfPage);\n                \n                \n                \n     result := [];\n     \n     result[\"html\"] := htmlJavaString;\n     \n     result[\"sumOfSquaresWithin\"] := sumOfSquaresWithin;\n     \n     result[\"sumOfSquaresBetween\"] := sumOfSquaresBetween;\n     \n     result[\"sumOfSquaresBlock\"] := sumOfSquaresBlock;\n     \n     result[\"sumOfSquaresTotal\"] := sumOfSquaresTotal;\n     \n     result[\"meanSquareBetween\"] := meanSquareBetween;\n     \n     result[\"meanSquareWithin\"] := meanSquareWithin;\n     \n     result[\"meanSquareBlock\"] := meanSquareBlock;\n     \n     result[\"fScore\"] := fScore;\n     \n     result[\"criticalFScore\"] := criticalFScore;\n     \n     result[\"fScoreBlock\"] := fScoreBlock;\n     \n     result[\"criticalFScoreBlock\"] := criticalFScoreBlock;\n     \n     result;\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/AnovaCompletelyRandomizedBlock.mpw, (AnovaCompletelyRandomizedBlock)";
        scriptMap.put("AnovaCompletelyRandomizedBlock",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"AnovaSingleFactor\",*);\n\nAnovaSingleFactor(levelsList, alpha) :=\n{\n    Check(ListOfLists?(levelsList), \"Argument\", \"The first argument must be a list of lists.\");\n    \n    Check(alpha >=? 0 And? alpha <=? 1, \"Argument\", \"The second argument must be a number between 0 and 1.\");\n    \n    Local(\n        anovaTableRow1,\n        anovaTableRow2,\n        anovaTableRow3,\n        anovaTableTotal,\n        bottomOfAnova,\n        bottomOfPage,\n        bottomOfSummary,\n        criticalFScore,\n        degreesOfFreedomBetween,\n        degreesOfFreedomWithin,\n        fScore,\n        grandMean,\n        htmlJavaString,\n        index,\n        lengthsList,\n        meansList,\n        meanSquareBetween,\n        meanSquareWithin,\n        result,\n        summaryTableRow,\n        summaryTableRows,\n        sumOfSquaresBetween,\n        sumOfSquaresTotal,\n        sumOfSquaresWithin,\n        sumsList,\n        topOfAnova,\n        topOfPage,\n        topOfSummary,\n        variancesList);\n\n    meansList := [];\n    \n    variancesList := [];\n    \n    sumsList := [];\n    \n    lengthsList := [];\n    \n    ForEach(levelList, levelsList)\n    {\n        meansList := meansList ~ NM(Mean(levelList));\n        \n        variancesList := variancesList ~ NM(UnbiasedVariance(levelList));\n        \n        sumsList := sumsList ~ NM(Sum(levelList));\n\n        lengthsList := lengthsList ~ Length(levelList);\n    };\n    \n    sumOfSquaresWithin := Sum((lengthsList - 1) * variancesList);\n\n    grandMean := NM(Mean(Flatten(levelsList, \"List\")));\n    \n    sumOfSquaresBetween := Sum(lengthsList*(meansList - grandMean)^2);\n    \n    sumOfSquaresTotal := NM(sumOfSquaresWithin + sumOfSquaresBetween);\n    \n    degreesOfFreedomBetween := (Length(levelsList)-1);\n    \n    degreesOfFreedomWithin := (ElementCount(levelsList) - Length(levelsList));\n    \n    meanSquareBetween := NM(sumOfSquaresBetween/degreesOfFreedomBetween);\n    \n    meanSquareWithin := NM(sumOfSquaresWithin/degreesOfFreedomWithin);\n    \n    fScore := NM(meanSquareBetween/meanSquareWithin);\n    \n    criticalFScore := ProbabilityToFScore(degreesOfFreedomBetween, degreesOfFreedomWithin, 1-alpha);\n    \n    topOfPage :=\n\"\n    <html>\n        <title>\n            Anova: Single Factor\n        </title>\n        \n        <body>\n\";\n    \n    topOfSummary :=\n\"\n            <h2>Anova: Single Factor</h2>\n            \n            <TABLE BORDER>\n                <CAPTION align=\\\"left\\\"> <h3>Summary</h3>  </CAPTION>\n                \n                <TR> <TH> Level </TH> <TH> Count</TH> <TH> Sum </TH> <TH> Mean </TH> <TH> Variance </TH> </TR>\n\";\n\n    \n    summaryTableRows := \"\";\n    \n    summaryTableRow := \"<TR> <TD> <?Write(ToAtom(\\\"Level\\\"~ToString(index)));?> </TD> <TD align=\\\"right\\\"> <?Write(lengthsList[index]);?> </TD> <TD> <?Write(sumsList[index]);?> </TD>  <TD> <?Write(meansList[index]);?> </TD> <TD> <?Write(variancesList[index]);?> </TD> </TR>\"~Nl();\n    \n    index := 1;\n    While(index <=? Length(levelsList))\n    {\n        summaryTableRows := summaryTableRows ~ PatchString(summaryTableRow);\n    \n        index++;\n    };\n    \n    \n    bottomOfSummary :=\n\"\n            </TABLE>\n\";\n    \n\n\n    topOfAnova :=\n\"\n            <br \\>\n            <br \\>\n            \n            <TABLE BORDER>\n                <CAPTION align=\\\"left\\\"> <h3>ANOVA</h3>  </CAPTION>\n                \n                <TR> <TH> Source of Variation </TH> <TH> Sum of Squares </TH> <TH> Degrees of Freedom </TH> <TH> Mean Square Between </TH> <TH> F </TH> <TH> F Critical </TH> </TR>\n\";\n\n    \n    \n    anovaTableRow1 := PatchString(\"<TR> <TD> <?Write(ToAtom(\\\"Between Levels\\\"));?> </TD> <TD > <?Write(sumOfSquaresBetween);?> </TD> <TD> <?Write(degreesOfFreedomBetween);?> </TD>   <TD > <?Write(meanSquareBetween);?> </TD><TD> <?Write(fScore);?> </TD> <TD> <?Write(criticalFScore);?> </TD> </TR>\"~Nl());\n    \n    anovaTableRow2 := PatchString(\"<TR> <TD> <?Write(ToAtom(\\\"Within Levels\\\"));?> </TD> <TD > <?Write(sumOfSquaresWithin);?> </TD> <TD> <?Write(degreesOfFreedomWithin);?> </TD>   <TD > <?Write(meanSquareWithin);?> </TD></TR>\"~Nl());\n    \n    anovaTableTotal := PatchString(\"<TR> <TD> Total </TD> <TD> <?Write(sumOfSquaresTotal);?> </TD> <TD> <?Write(degreesOfFreedomBetween + degreesOfFreedomWithin);?> </TD> </TR>\");\n    \n    bottomOfAnova :=\n\"\n            </TABLE>\n\";\n\n\n    \n    bottomOfPage :=\n\"\n        </body>         \n    </html>\n\";\n    \n    htmlJavaString := JavaNew(\"java.lang.String\",\n                topOfPage ~ \n                topOfSummary ~\n                summaryTableRows ~\n                bottomOfSummary ~\n                topOfAnova ~\n                anovaTableRow1 ~ \n                anovaTableRow2 ~\n                anovaTableTotal ~ \n                bottomOfAnova ~\n                bottomOfPage);\n                \n                \n                \n     result := [];\n     \n     result[\"html\"] := htmlJavaString;\n     \n     result[\"sumOfSquaresWithin\"] := sumOfSquaresWithin;\n     \n     result[\"sumOfSquaresBetween\"] := sumOfSquaresBetween;\n     \n     result[\"sumOfSquaresTotal\"] := sumOfSquaresTotal;\n     \n     result[\"degreesOfFreedomBetween\"] := degreesOfFreedomBetween;\n     \n     result[\"degreesOfFreedomWithin\"] := degreesOfFreedomWithin;\n     \n     result[\"meanSquareBetween\"] := meanSquareBetween;\n     \n     result[\"meanSquareWithin\"] := meanSquareWithin;\n     \n     result[\"fScore\"] := fScore;\n     \n     result[\"criticalFScore\"] := criticalFScore;\n     \n     result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/AnovaSingleFactor.mpw, (AnovaSingleFactor)";
        scriptMap.put("AnovaSingleFactor",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\n//Retract(\"BinomialDistributionMean\", *);\n\nBinomialDistributionMean(probability,numberOfTrials) :=\n{\n\n    Check(RationalOrNumber?(probability) And? p >=? 0 And? p <=? 1, \"Argument\", \"The first argument must be a number between 0 and 1.\");\n    \n    Check(Integer?(numberOfTrials) And? numberOfTrials >=? 0, \"Argument\", \"The second argument must be an integer which is greater than 0.\");\n    \n    numberOfTrials * probability;\n};\n        \n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/BinomialDistributionMean.mpw, (BinomialDistributionMean)";
        scriptMap.put("BinomialDistributionMean",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\n//Retract(\"BinomialDistributionStandardDeviation\", *);\n\nBinomialDistributionStandardDeviation(probability,numberOfTrials) :=\n{\n\n    Check(RationalOrNumber?(probability) And? p >=? 0 And? p <=? 1, \"Argument\", \"The first argument must be a number between 0 and 1.\");\n    \n    Check(Integer?(numberOfTrials) And? numberOfTrials >=? 0, \"Argument\", \"The second argument must be an integer which is greater than 0.\");\n    \n    SqrtN(numberOfTrials * probability * (1 - probability));\n};\n        \n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/BinomialDistributionStandardDeviation.mpw, (BinomialDistributionStandardDeviation)";
        scriptMap.put("BinomialDistributionStandardDeviation",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* \n    This function was adapted from the Javascript version of function\n    that is located here:\n    \n    http://www.fourmilab.ch/rpkp/experiments/analysis/chiCalc.js\n    http://www.fourmilab.ch/rpkp/experiments/analysis/chiCalc.html\n    \n    The following JavaScript functions for calculating normal and\n    chi-square probabilities and critical values were adapted by\n    John Walker from C implementations\n    written by Gary Perlman of Wang Institute, Tyngsboro, MA\n    01879. Both the original C code and this JavaScript edition\n    are in the public domain. \n*/\n\n\n/*  POCHISQ  --  probability of chi-square value\n\n          Adapted from:\n                  Hill, I. D. and Pike, M. C.  Algorithm 299\n                  Collected Algorithms for the CACM 1967 p. 243\n          Updated for rounding errors based on remark in\n                  ACM TOMS June 1985, page 185\n*/\n\nChiSquareScoreToAlpha(score, degreesOfFreedom) :=\n{\n    Local(a, y, s, e, c, z, LogSqrtPi, ISqrtPi,result);                     \n    \n    y := 0;\n    \n    LogSqrtPi := 0.5723649429247000870717135; /* log(sqrt(pi)) */\n    \n    ISqrtPi := 0.5641895835477562869480795;   /* 1 / sqrt(pi) */\n    \n    If(score <=? 0.0 Or? degreesOfFreedom <? 1) \n    {\n        result := 1.0;\n    }\n    Else\n    {\n        a := NM(0.5 * score);\n        \n        If(degreesOfFreedom >? 1) \n        {\n            y := Decide(-a <? -20, 0, ExpN(-a));\n        };\n        \n        s := Decide(Even?(degreesOfFreedom), y , (2.0 * ZScoreToProbability(-SqrtN(score))));\n        \n        If(degreesOfFreedom >? 2) \n        {\n            score := 0.5 * (degreesOfFreedom - 1.0);\n            \n            z := Decide(Even?(degreesOfFreedom), 1.0, 0.5);\n            \n            If(a >? 20) \n            {\n                e := Decide(Even?(degreesOfFreedom), 0.0, LogSqrtPi);\n                \n                c := LogN(a);\n                \n                While(z <=? score) \n                {\n                    e := LogN(z) + e;\n                    s := s + Decide(c * z - a - e <? -20, 0, ExpN(c * z - a - e));\n                    z := z + 1.0;\n                };\n                result := s;\n            } \n            Else \n            {\n                e := Decide(Even?(degreesOfFreedom) , 1.0, (ISqrtPi / SqrtN(a)));\n                \n                c := 0.0;\n                \n                While(z <=? score) \n                {\n                    e := e * (a / z);\n                    c := c + e;\n                    z := z + 1.0;\n                };\n                \n                result := c * y + s;\n            };\n        }\n        Else\n        {\n            result := s;\n        };\n    \n    };\n    \n    NM(result);\n};\n\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/ChiSquareScoreToAlpha.mpw, (ChiSquareScoreToAlpha)";
        scriptMap.put("ChiSquareScoreToAlpha",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nCoefficientOfDetermination(x,y) :=\n{   \n    Check(List?(x), \"Argument\", \"The first argument must be a list.\");\n    \n    Check(List?(y), \"Argument\", \"The second argument must be a list.\");\n    \n    Check(Length(x) =? Length(y), \"Argument\", \"The lists for argument 1 and argument 2 must have the same length.\");\n    \n    NM(CorrelationCoefficient(x,y)^2);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/CoefficientOfDetermination.mpw, (CoefficientOfDetermination)";
        scriptMap.put("CoefficientOfDetermination",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"ConfidenceIntervalOfTheMean\",*);\n\nConfidenceIntervalOfTheMean(sampleMean,standardDeviation,standardDeviationIsKnown,sampleSize,confidenceLevel) :=\n{\n    Check(Boolean?(standardDeviationIsKnown), \"Argument\", \"The third argument must be True or False.\");\n    \n    Local(criticalZScore,criticalTScore,standardErrorOfTheMean,upperLimitValue,lowerLimitValue,resultList);\n    \n    resultList := [];\n    \n    Decide(sampleSize >=? 30 Or? standardDeviationIsKnown =? True,\n    {\n        criticalZScore := NM(ConfidenceLevelToZScore(confidenceLevel));\n        \n        resultList[\"criticalZScore\"] := criticalZScore;\n\n        standardErrorOfTheMean := NM(StandardErrorOfTheMean(standardDeviation,sampleSize));\n        \n        lowerLimitValue := NM(sampleMean - criticalZScore * standardErrorOfTheMean);\n        \n        upperLimitValue := NM(sampleMean + criticalZScore * standardErrorOfTheMean);\n\n        \n        Decide(InVerboseMode(),\n            {\n                Echo(\"Using the normal distribution.\");\n                \n                Echo(\"Critical z-score: \", criticalZScore);\n                \n                Echo(\"Standard error of the mean: \", standardErrorOfTheMean);\n            });\n    },\n    {   \n        criticalTScore := OneTailAlphaToTScore(sampleSize - 1, NM((1 - confidenceLevel)/2));\n        \n        resultList[\"criticalTScore\"] := criticalTScore;\n        \n        standardErrorOfTheMean := NM(StandardErrorOfTheMean(standardDeviation,sampleSize));\n        \n        lowerLimitValue := NM(sampleMean - criticalTScore * standardErrorOfTheMean);\n        \n        upperLimitValue := NM(sampleMean + criticalTScore * standardErrorOfTheMean);\n        \n        \n        Decide(InVerboseMode(),\n            {\n                Echo(\"Using the t-distribution.\");\n                \n                Echo(\"Critical t-score: \", criticalTScore);\n                \n                Echo(\"Standard error of the mean: \", standardErrorOfTheMean);\n            });\n    \n    });\n    \n    resultList[\"upperLimit\"] := upperLimitValue;\n    \n    resultList[\"lowerLimit\"] := lowerLimitValue;\n\n    resultList;\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/ConfidenceIntervalOfTheMean.mpw, (ConfidenceIntervalOfTheMean)";
        scriptMap.put("ConfidenceIntervalOfTheMean",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"ConfidenceIntervalOfTheProportion\",*);\n\nConfidenceIntervalOfTheProportion(numberOfSuccesses,sampleSize,confidenceLevel) :=\n{\n    Check(Integer?(numberOfSuccesses) And? numberOfSuccesses >=? 0, \"Argument\", \"The first argument must be an integer which is >=?0\");\n    \n    Check(Integer?(sampleSize) And? sampleSize >=? 0, \"Argument\", \"The second argument must be an integer which is >=?0\");\n    \n    Local(criticalZScore,approximateStandardErrorOfTheProportion,upperLimit,lowerLimit,resultList,proportion);\n    \n    resultList := [];\n    \n    criticalZScore := ConfidenceLevelToZScore(confidenceLevel);\n    \n    resultList[\"criticalZScore\"] := criticalZScore;\n    \n    proportion := NM(numberOfSuccesses/sampleSize);\n    \n    approximateStandardErrorOfTheProportion := Sqrt((proportion*(1 - proportion))/sampleSize);\n    \n    upperLimit := NM(proportion + criticalZScore * approximateStandardErrorOfTheProportion);\n    \n    lowerLimit := NM(proportion - criticalZScore * approximateStandardErrorOfTheProportion);\n    \n    Decide(InVerboseMode(),\n        {\n            Echo(\"Critical z-score: \", criticalZScore);\n            \n            Echo(\"Proportion: \", proportion);\n            \n            Echo(\"Standard error of the proportion: \", NM(approximateStandardErrorOfTheProportion));\n        });\n    \n    resultList[\"upperLimit\"] := upperLimit;\n    \n    resultList[\"lowerLimit\"] := lowerLimit;\n    \n    resultList;\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/ConfidenceIntervalOfTheProportion.mpw, (ConfidenceIntervalOfTheProportion)";
        scriptMap.put("ConfidenceIntervalOfTheProportion",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\n//Retract(\"ConfidenceLevelToZScore\",*);\n\nConfidenceLevelToZScore(probability) :=\n{\n    //Shift the probability higher to turn it into a confidence interval.\n    probability := probability + (1 - probability)/2;\n    \n    ProbabilityToZScore(probability);\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/ConfidenceLevelToZScore.mpw, (ConfidenceLevelToZScore)";
        scriptMap.put("ConfidenceLevelToZScore",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nControlChartConstants(n) :=\n{   \n    Check(n >=? 2 And? n <=? 15, \"Argument\", \"The argument n must be 2 <=? n <=? 20.\");\n    \n    Local(result, table);\n    \n    result := [];\n    \n    n--;\n    \n    table := [\n        [1.880, 1.128, 0.000, 3.267],\n        [1.023, 1.693, 0.000, 2.574],\n        [0.729, 2.059, 0.000, 2.282],\n        [0.577, 2.326, 0.000, 2.114],\n        [0.483, 2.534, 0.000, 2.004],\n        [0.419, 2.704, 0.076, 1.924],\n        [0.373, 2.847, 0.136, 1.864],\n        [0.337, 2.970, 0.184, 1.816],\n        [0.308, 3.078, 0.223, 1.777],\n        [0.285, 3.173, 0.256, 1.744],\n        [0.266, 3.258, 0.283, 1.717],\n        [0.249, 3.336, 0.307, 1.693],\n        [0.235, 3.407, 0.328, 1.672],\n        [0.223, 3.472, 0.347, 1.653],\n        [0.212, 3.532, 0.363, 1.637],\n        [0.203, 3.588, 0.378, 1.622],\n        [0.194, 3.640, 0.391, 1.608],\n        [0.187, 3.689, 0.403, 1.597],\n        [0.180, 3.735, 0.415, 1.585],\n    ];\n\n    result[\"D4\"] := table[n][4];\n    \n    result[\"D3\"] := table[n][3];\n    \n    result[\"d2\"] := table[n][2];\n        \n    result[\"A2\"] := table[n][1];\n    \n    result;\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/ControlChartConstants.mpw, (ControlChartConstants)";
        scriptMap.put("ControlChartConstants",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nCorrelationCoefficient(x,y) :=\n{   \n    Check(List?(x), \"Argument\", \"The first argument must be a list.\");\n    \n    Check(List?(y), \"Argument\", \"The second argument must be a list.\");\n    \n    Check(Length(x) =? Length(y), \"Argument\", \"The lists for argument 1 and argument 2 must have the same length.\");\n    \n    Local(n);\n        \n    n := Length(x);\n    \n    NM((n*Sum(x*y)-Sum(x)*Sum(y))/Sqrt((n*Sum(x^2)-(Sum(x))^2)*(n*Sum(y^2)-(Sum(y)^2))) );\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/CorrelationCoefficient.mpw, (CorrelationCoefficient)";
        scriptMap.put("CorrelationCoefficient",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nCorrelationMatrix(dataLists) := \n{\n    Local(namesList, correlationMatrix);\n    \n    ForEach(dataList, dataLists)\n    {\n        Check(Matrix?(dataLists), \"Argument\", \"All lists must have the same number of elements.\");\n    };\n\n    namesList := MatrixColumn(dataLists,1);\n    \n    namesList := \"\" ~ namesList;\n    \n    ForEach(dataList, dataLists)\n    {\n        PopFront(dataList);\n    };\n    \n    correlationMatrix := ZeroMatrix(Length(dataLists)+1);\n    \n    ForEach(rowIndex, 1 .. Length(dataLists) + 1)\n    {\n        ForEach(columnIndex, 1 .. Length(dataLists) + 1)\n        {\n            If(rowIndex >=? 2 And? columnIndex >=? 2)\n            {\n                correlationMatrix[rowIndex][columnIndex] := NM(CorrelationCoefficient(dataLists[rowIndex - 1],dataLists[columnIndex - 1]),2);\n            }\n            Else If(rowIndex =? 1)\n            {\n                correlationMatrix[rowIndex][columnIndex] := namesList[columnIndex];\n            }\n            Else\n            {\n                correlationMatrix[rowIndex][columnIndex] := namesList[rowIndex];\n            };\n        };\n    };\n    \n    correlationMatrix;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/CorrelationMatrix.mpw, (CorrelationMatrix)";
        scriptMap.put("CorrelationMatrix",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nD2Value(k,n) :=\n{\n    Check(k >=? 0 And? k <=? 15, \"Argument\", \"The first argument k must be 0 <=? k <=? 15.\");\n    \n    Check(n >=? 2 And? n <=? 15, \"Argument\", \"The second argument n must be 2 <=? n <=? 15.\");\n    \n    n--;\n    \n    If(k =? 0)\n    {\n        [1.128,1.693,2.059,2.326,2.534,2.704,2.847,2.970,3.078,3.173,3.259,3.336,3.407,3.472][n];\n    }\n    Else\n    {\n        [\n            [1.414, 1.912, 2.239, 2.481, 2.673, 2.830, 2.963, 3.078, 3.179, 3.269, 3.350, 3.424, 3.491, 3.553],\n            [1.279, 1.805, 2.151, 2.405, 2.604, 2.768, 2.906, 3.025, 3.129, 3.221, 3.305, 3.380, 3.449, 3.513],\n            [1.231, 1.769, 2.120, 2.379, 2.581, 2.747, 2.886, 3.006, 3.112, 3.205, 3.289, 3.366, 3.435, 3.499],\n            [1.206, 1.750, 2.105, 2.366, 2.570, 2.736, 2.877, 2.997, 3.103, 3.197, 3.282, 3.358, 3.428, 3.492],\n            [1.191, 1.739, 2.096, 2.358, 2.563, 2.730, 2.871, 2.992, 3.098, 3.192, 3.277, 3.354, 3.424, 3.488],\n            [1.181, 1.731, 2.090, 2.353, 2.558, 2.726, 2.867, 2.988, 3.095, 3.189, 3.274, 3.351, 3.421, 3.486],\n            [1.173, 1.726, 2.085, 2.349, 2.555, 2.723, 2.864, 2.986, 3.092, 3.187, 3.272, 3.349, 3.419, 3.484],\n            [1.168, 1.721, 2.082, 2.346, 2.552, 2.720, 2.862, 2.984, 3.090, 3.185, 3.270, 3.347, 3.417, 3.482],\n            [1.164, 1.718, 2.080, 2.344, 2.550, 2.719, 2.860, 2.982, 3.089, 3.184, 3.269, 3.346, 3.416, 3.481],\n            [1.160, 1.716, 2.077, 2.342, 2.549, 2.717, 2.859, 2.981, 3.088, 3.183, 3.268, 3.345, 3.415, 3.480],\n            [1.157, 1.714, 2.076, 2.340, 2.547, 2.716, 2.858, 2.980, 3.087, 3.182, 3.267, 3.344, 3.415, 3.479],\n            [1.155, 1.712, 2.074, 2.344, 2.546, 2.715, 2.857, 2.979, 3.086, 3.181, 3.266, 3.343, 3.414, 3.479],\n            [1.153, 1.710, 2.073, 2.338, 2.545, 2.714, 2.856, 2.978, 3.085, 3.180, 3.266, 3.343, 3.413, 3.478],\n            [1.151, 1.709, 2.072, 2.337, 2.545, 2.714, 2.856, 2.978, 3.085, 3.180, 3.265, 3.342, 3.413, 3.478],\n            [1.150, 1.708, 2.071, 2.337, 2.544, 2.713, 2.855, 2.977, 3.084, 3.179, 3.265, 3.342, 3.412, 3.477]\n        ][k][n];\n    };\n    \n};\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/D2Value.mpw, (D2Value)";
        scriptMap.put("D2Value",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/*\nThis function came from http://www.johndcook.com/blog/2009/01/19/stand-alone-error-function-erf/\n\n\"This problem is typical in two ways: Abramowitz & Stegun has a solution, and \nyou've got to know a little background before you can use it.\n\nThe formula given in Abramowitz & Stegun is only good for x <=? 0. That's no problem if you \nknow that the error function is an odd function, i.e. erf(-x) = -erf(x). \nBut if you're an engineer who has never heard of the error function but \nneeds to use it, it may take a while to figure out how to handle negative inputs.\n\nOne other thing that someone just picking up A&S might not know is the \nbest way to evaluate polynomials. The formula appears as \n1 - (a1t1 + a2t2 + a3t3 + a4t4 + a5t5)exp(-x2), which is absolutely correct. \nBut directly evaluating an nth order polynomial takes O(n2) operations, \nwhile the factorization used in the code above uses O(n) operations. This \ntechnique is known as Horner's method. John D. Cook.\"\n\n*/\n\n//Retract(\"ErrorFunction\",*);\n\nErrorFunction(x) :=\n{\n    Local(a1,a2,a3,a4,a5,p,sign,t,y);\n    //constants\n    a1 :=  0.254829592;\n    a2 := -0.284496736;\n    a3 :=  1.421413741;\n    a4 := -1.453152027;\n    a5 :=  1.061405429;\n    p  :=  0.3275911;\n\n    //Save the sign of x\n    sign := 1;\n    \n    Decide(x <? 0, sign := -1);\n    \n    x := AbsN(x);\n\n    // Abramowitz & Stegun 7.1.26\n    t := 1.0/(1.0 + p*x);\n    y := NM(1.0 - (((((a5*t + a4)*t) + a3)*t + a2)*t + a1)*t*Exp(-x*x));\n\n    sign*y;\n};\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/ErrorFunction.mpw, (ErrorFunction)";
        scriptMap.put("ErrorFunction",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nMode(list) :=\n{\n    Check(Length(list) >? 0 And? NumericList?(list), \"Argument\", \"Argument must be a nonempty numeric list.\");\n    \n    Local(noDuplicatesList, countsList, sortedList, highestCountsList, resultList);\n    \n    noDuplicatesList := RemoveDuplicates(list);\n    \n    countsList := [];\n    \n    ForEach(element, noDuplicatesList)\n    {\n        countsList := Append(countsList, [Count(list, element), element] );\n    };\n    \n    sortedList := HeapSort(countsList,Lambda([x,y],x[1] >? y[1]));\n    \n    highestCountsList := Select(sortedList, Lambda([x],x[1] =? sortedList[1][1]));\n    \n    resultList := MapSingle(Lambda([x],x[2]), highestCountsList);\n\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/Mode.mpw, (Mode)";
        scriptMap.put("Mode",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"Permutations\", *);\n\nPermutations(n) :=\n{\n    Check(Integer?(n), \"Argument\", \"Argument must be an integer\");\n\n    n!;\n};\n\n\nPermutations(n, r) :=\n{\n    Check(Integer?(n), \"Argument\", \"Argument 1 must be an integer\");\n    \n    Check(Integer?(r), \"Argument\", \"Argument 2 must be an integer\");\n    \n    n! /(n-r)!;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/Permutations.mpw, (Permutations)";
        scriptMap.put("Permutations",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* \n    This function was adapted from the Javascript version of function\n    that is located here:\n    \n    http://www.fourmilab.ch/rpkp/experiments/analysis/zCalc.js\n    http://www.fourmilab.ch/rpkp/experiments/analysis/zCalc.html?\n    \n    The following JavaScript functions for calculating normal and\n    chi-square probabilities and critical values were adapted by\n    John Walker from C implementations\n    written by Gary Perlman of Wang Institute, Tyngsboro, MA\n    01879. Both the original C code and this JavaScript edition\n    are in the public domain. \n*/\n\n\n/* We just do a bisection\nsearch for a value within CHI_EPSILON,\nrelying on the monotonicity of pochisq(). */\n\n//Retract(\"ProbabilityToZScore\",*);\n\nProbabilityToZScore(probability) :=\n{\n    Local(ZMAX,ZEPSILON,minimumZ,maximumZ,zValue,probabilityValue);\n    \n    probability := NM(probability);\n    \n    Check(probability >=? 0.0 And? probability <=? 1.0, \"Argument\", \"The argument must be between 0 and 1.\");\n    \n    ZMAX := 6; // Maximum �z value.\n    \n    ZEPSILON := 0.000001; /* Accuracy of z approximation */\n    \n    minimumZ := -ZMAX;\n    \n    maximumZ := ZMAX;\n    \n    zValue := 0.0;\n\n    While ((maximumZ - minimumZ) >? ZEPSILON) \n    {\n        probabilityValue := ZScoreToProbability(zValue);\n        \n        If(probabilityValue >? probability) \n        {\n            maximumZ := zValue;\n        } \n        Else \n        {\n            minimumZ := zValue;\n        };\n        \n        zValue := (maximumZ + minimumZ) * 0.5;\n    };\n    \n    zValue;\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/ProbabilityToZScore.mpw, (ProbabilityToZScore)";
        scriptMap.put("ProbabilityToZScore",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"Quartile\",*);\n\nQuartile(list) :=\n{\n    sortedList := HeapSort(list,\"<?\");\n    \n    secondQuartile := Median(sortedList);\n    \n    Decide(Odd?(Length(sortedList)),\n    {   \n        secondQuartileIndex := Find(sortedList, secondQuartile);\n        \n        leftList := Take(sortedList, secondQuartileIndex-1);\n        rightList := Take(sortedList, -(Length(sortedList) - (secondQuartileIndex) ) );\n    },\n    {\n\n        leftList := Take(sortedList, Length(sortedList)/2);\n        rightList := Take(sortedList, -Length(sortedList)/2);\n    }\n    );\n    \n    firstQuartile := Median(leftList);\n    \n    thirdQuartile := Median(rightList);\n    \n    interquartileRange := thirdQuartile - firstQuartile;\n    \n    [firstQuartile, secondQuartile, thirdQuartile, interquartileRange];\n\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/Quartile.mpw, (Quartile)";
        scriptMap.put("Quartile",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"RandomPick\",*);\n\n\nRandomPick(list) :=\n{\n    Check(List?(list), \"Argument\", \"Argument must be a list.\");\n    \n    Check(Length(list) >? 0, \"Argument\", \"The number of elements in the list must be greater than 0.\");\n    \n    Local(pickPosition);\n    \n    pickPosition := RandomInteger(Length(list));\n\n    list[pickPosition];\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/RandomPick.mpw, (RandomPick)";
        scriptMap.put("RandomPick",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"RandomPickVector\", *);\n\nRandomPickVector(list, count) :=\n{\n    Check(List?(list), \"Argument\", \"Argument 1 must be a list.\");\n    \n    Check(Integer?(count), \"Argument\", \"Argument 2 must be an integer.\");\n    \n    BuildList(RandomPick(list),x,1,count,1);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/RandomPickVector.mpw, (RandomPickVector)";
        scriptMap.put("RandomPickVector",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"RandomPickWeighted\",*);\n\nRandomPickWeighted(list) :=\n{\n\n    Check(List?(list), \"Argument\", \"Argument must be a list.\");\n    \n    Local(element, probabilities, items, lastWeight, randomNumber, result);\n    \n    probabilities := 0;\n    \n    items := [];\n    \n    lastWeight := 0;\n    \n    \n    \n    //Make sure that the probabilities sum to 1.\n    ForEach(element,list)\n    {        \n        probability := element[2];\n\n        probabilities := probabilities + probability;\n    };\n    \n    Check(probabilities =? 1, \"Argument\", \"The probabilities must sum to 1.\");\n    \n    \n    \n    //Place items in a list and associate it with a subrange in the range between 0 and 1.\n    ForEach(element,list)\n    {\n        probability := element[2];\n        \n        item := element[1];\n        \n        items := Append(items, [item, [lastWeight, lastWeight := lastWeight + NM(probability)]] );\n    };\n    \n    \n    \n    //Pick the item which is in the randomly determined range.\n    randomNumber := Random();\n    \n    ForEach(itemData,items)\n    {\n        Decide(randomNumber >=? itemData[2][1] And? randomNumber <=? itemData[2][2], result := itemData[1] );\n    };\n    \n    \n    \n    result;\n\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/RandomPickWeighted.mpw, (RandomPickWeighted)";
        scriptMap.put("RandomPickWeighted",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRange(list) :=\n{\n    Check(Length(list) >? 0 And? NumericList?(list), \"Argument\", \"Argument must be a nonempty numeric list.\");\n    \n    Maximum(list) - Minimum(list);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/Range.mpw, (Range)";
        scriptMap.put("Range",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRegressionLine(x,y) :=\n{   \n    Check(List?(x), \"Argument\", \"The first argument must be a list.\");\n    \n    Check(List?(y), \"Argument\", \"The second argument must be a list.\");\n    \n    Check(Length(x) =? Length(y), \"Argument\", \"The lists for argument 1 and argument 2 must have the same length.\");\n    \n    Local(n,a,b,xMean,yMean,line,result);\n    \n    n := Length(x);\n    \n    b := NM((n*Sum(x*y) - Sum(x)*Sum(y))/(n*Sum(x^2)-(Sum(x))^2));\n    \n    xMean := NM(Mean(x));\n    \n    yMean := NM(Mean(y));\n    \n    a := NM(yMean - b*xMean);\n    \n    line := a + b*Hold(x);\n    \n    result := [];\n    \n    result[\"xMean\"] := xMean;\n    \n    result[\"yMean\"] := yMean;\n    \n    result[\"line\"] := line;\n    \n    result[\"yIntercept\"] := a;\n    \n    result[\"slope\"] := b;\n    \n    result[\"count\"] := n;\n    \n    result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/RegressionLine.mpw, (RegressionLine)";
        scriptMap.put("RegressionLine",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRegressionLineConfidenceInterval(x,y,xValue,confidenceLevel) :=\n{   \n    Check(List?(x), \"Argument\", \"The first argument must be a list.\");\n    \n    Check(List?(y), \"Argument\", \"The second argument must be a list.\");\n    \n    Check(Length(x) =? Length(y), \"Argument\", \"The lists for argument 1 and argument 2 must have the same length.\");\n    \n    Check(confidenceLevel >=?0 And? confidenceLevel <=?1, \"Argument\", \"The confidence level must be >=? 0 and <=? 1.\");\n    \n    Local(n,a,b,xMean,part,result,criticalTScore,standardErrorOfTheEstimate/* regressionLine, todo:tk:causes an error if it is not global. */);\n    \n    regressionLine := RegressionLine(x,y);\n    \n    n := regressionLine[\"count\"];\n    \n    f(x) := {Eval(regressionLine[\"line\"]);};\n    \n    criticalTScore := OneTailAlphaToTScore(n-2, NM((1 - confidenceLevel)/2));\n    \n    standardErrorOfTheEstimate := StandardErrorOfTheEstimate(x,y);\n\n    xMean := regressionLine[\"xMean\"];\n\n    part := NM(criticalTScore * standardErrorOfTheEstimate * Sqrt(1/n + ((xValue - xMean)^2)/(Sum(x^2) - Sum(x)^2/n)));\n    \n    result := [];\n    \n    result[\"upper\"] := f(xValue) + part;\n    \n    result[\"lower\"] := f(xValue) - part;\n    \n    result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/RegressionLineConfidenceLevel.mpw, (RegressionLineConfidenceInterval)";
        scriptMap.put("RegressionLineConfidenceInterval",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\n\n//Retract(\"Repeat\",*);\n\n\n/*\n    These variables need to be declared as local symbols because\n    body is unfenced and expressions in the body could see them\n    otherwise.\n*/\nLocalSymbols(count, iterations, body){\n\n\nRulebaseHoldArguments(\"Repeat\",[_iterations,_body]);\n\n/*\n  A Rule function needed to be used here because 10 # xxx <--\n  notation did not work if Bodied was executed before the\n  function was defined.  Bodied is evaluated in stdopers.mpw\n  because it needs to be evaluated for the parser to parse\n  Retract correctly.\n*/\n\nRuleHoldArguments(\"Repeat\",2,10,Integer?(iterations) And? iterations >? 0)\n{\n    Local(count);\n\n    count := 0;\n\n    While (iterations >? 0)\n    {\n        Eval(body);\n        iterations--;\n        count++;\n    };\n\n    count;\n\n};\n\n\n\n\n\nRulebaseHoldArguments(\"Repeat\",[_body]);\n\n\nRuleHoldArguments(\"Repeat\",1,20,True)\n{\n    Local(count);\n\n    count := 0;\n    While (True)\n    {\n        Eval(body);\n        count++;\n    };\n\n    count;\n};\n\n};//end LocalSymbols\n\nUnFence(\"Repeat\",2);\nHoldArgumentNumber(\"Repeat\",2,2);\nUnFence(\"Repeat\",1);\nHoldArgumentNumber(\"Repeat\",1,1);\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/Repeat.mpw, (Repeat)";
        scriptMap.put("Repeat",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"Sample\",*);\n\nSample(list, sampleSize) :=\n{\n    Check(List?(list), \"Argument\", \"The first argument must be a list.\");\n    \n    Check(Integer?(sampleSize) And? sampleSize >? 0, \"Argument\", \"The second argument must be an integer which is greater than 0.\");\n    \n    list := Shuffle(list);\n\n    Take(list, sampleSize);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/Sample.mpw, (Sample)";
        scriptMap.put("Sample",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"SampleSizeForTheMean\",*);\n\nSampleSizeForTheMean(standardDeviation,confidenceLevel,marginOfError) :=\n{\n    Local(minimumSampleSize);\n    \n    zScore := ConfidenceLevelToZScore(confidenceLevel);\n    \n    minimumSampleSize := NM(((zScore*standardDeviation)/marginOfError)^2);\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/SampleSizeForTheMean.mpw, (SampleSizeForTheMean)";
        scriptMap.put("SampleSizeForTheMean",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"SampleSizeForTheProportion\",*);\n\nSampleSizeForTheProportion(probabilityOfSuccess,confidenceLevel,marginOfError) :=\n{\n    Check(probabilityOfSuccess >=?0 And? probabilityOfSuccess <=? 1, \"Argument\", \"The first argument must be between 0 and 1.\");\n\n    Local(minimumSampleSize,zScore);\n    \n    zScore := ConfidenceLevelToZScore(confidenceLevel);\n    \n    minimumSampleSize := NM(probabilityOfSuccess*(1 - probabilityOfSuccess)*(zScore/marginOfError)^2);\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/SampleSizeForTheProportion.mpw, (SampleSizeForTheProportion)";
        scriptMap.put("SampleSizeForTheProportion",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nScheffeTest(levelsList, alpha) :=\n{\n    Check(ListOfLists?(levelsList), \"Argument\", \"The first argument must be a list of lists.\");\n    \n    Check(alpha >=? 0 And? alpha <=? 1, \"Argument\", \"The second argument must be a number between 0 and 1.\");\n    \n    Local(  result,\n            topOfSummary,\n            pairsList,\n            xBarB,\n            xBarA,\n            summaryTableRow,\n            ssw,\n            nA,\n            scheffeStatisticCalculated,\n            nB,\n            summaryList,\n            topOfPage,\n            htmlJavaString,\n            summaryTableRows,\n            meansList,\n            index,b,\n            pairList,\n            a,\n            bottomOfPage,\n            k,\n            countsList,\n            oneComparisonList,\n            scheffeStatistic,\n            bottomOfSummary,\n            resultList);\n    \n    anova := AnovaSingleFactor(levelsList, alpha);\n    \n    k := Length(levelsList);\n    \n    scheffeStatisticCalculated := (k-1)*anova[\"criticalFScore\"];\n    \n    resultList := [];\n    \n    resultList[\"scheffeStatisticCalculated\"] := scheffeStatisticCalculated;\n    \n    meansList := [];\n    \n    countsList := [];\n    \n    ForEach(levelList,levelsList)\n    {\n        meansList := meansList ~ NM(Mean(levelList));\n    \n        countsList := countsList ~ Length(levelList);\n    };\n    \n    pairsList := CombinationsList(1 .. Length(levelsList),2);\n    \n    summaryList := [];\n    \n    index := 1;\n    \n    ForEach(pairList, pairsList)\n    {   \n        a := pairList[1];\n        \n        b := pairList[2];\n        \n        xBarA := meansList[a];\n        \n        nA := countsList[a];\n        \n        xBarB := meansList[b];\n        \n        nB := countsList[b];\n        \n        ssw := anova[\"sumOfSquaresWithin\"];\n        \n        scheffeStatistic := ScheffeStatistic(xBarA,nA,xBarB,nB,ssw,k,countsList);\n        \n        oneComparisonList := [];\n        \n        oneComparisonList[\"conclusion\"] := Decide(scheffeStatistic <=? scheffeStatisticCalculated, \"No Difference\", \"Difference\");\n        \n        oneComparisonList[\"scheffeStatistic\"] := scheffeStatistic;\n        \n        oneComparisonList[\"pair\"] := pairList;\n        \n        summaryList[\"pair\" ~ ToString(index)] := oneComparisonList;\n        \n        index++;\n    };\n    \n    resultList[\"summary\"] := summaryList;\n    \n    \n    \n    \n    topOfPage :=\n\"\n    <html>\n        <title>\n            Scheffe Test Summary\n        </title>\n        \n        <body>\n\";\n    \n    topOfSummary :=\n\"\n            <h2>Scheffe Test Summary</h2>\n            \n            <TABLE BORDER>\n                <CAPTION align=\\\"left\\\"> <h3>Summary</h3>  </CAPTION>\n                \n                <TR> <TH> Sample Pair</TH> <TH> Measured Scheffe Statistic </TH> <TH> Calculated Scheffe Statistic </TH> <TH> Conclusion </TH> </TR>\n\";\n\n    \n    summaryTableRows := \"\";\n    \n    summaryTableRow := \"<TR> <TD align=\\\"center\\\"> <?Write(ToAtom(ToString(pairList[1]) ~ \\\" and \\\" ` ToString(pairList[2])));?> </TD> <TD align=\\\"right\\\"> <?Write(summary[\\\"scheffeStatistic\\\"]);?> </TD> <TD align=\\\"right\\\"> <?Write(resultList[\\\"scheffeStatisticCalculated\\\"]);?> </TD>  <TD> <?Write(ToAtom(summary[\\\"conclusion\\\"]));?> </TD> </TR>\"~Nl();\n    \n\n    \n    ForEach(summary, Reverse(resultList[\"summary\"]))\n    {\n        summary := summary[2];\n\n        pairList := summary[\"pair\"];\n        \n        summaryTableRows := summaryTableRows ~ PatchString(summaryTableRow);\n    \n        index++;\n    };\n    \n    \n    bottomOfSummary :=\n\"\n            </TABLE>\n\";\n    \n    \n    bottomOfPage :=\n\"\n        </body>         \n    </html>\n\";\n    \n    htmlJavaString := JavaNew(\"java.lang.String\",\n                topOfPage ~\n                topOfSummary ~\n                summaryTableRows ~\n                bottomOfSummary ~\n                bottomOfPage);    \n    \n    \n    \n    \n    resultList[\"html\"] := htmlJavaString;\n    \n    \n    DestructiveReverse(resultList);\n\n};\n\n\n\n\n\n\nScheffeStatistic(xBarA,nA,xBarB,nB,ssw,k,countsList) :=\n{\n    NM(((xBarA-xBarB)^2)/((ssw/Sum(i,1,k,(countsList[i] - 1))*(1/nA + 1/nB))));   \n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/ScheffeTest.mpw, (ScheffeTest)";
        scriptMap.put("ScheffeTest",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"Shuffle\",*);\n\n/*\n This function is based on the Fisher-Yates/Knuth shuffle algorithm\n which is described here at \n http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle .\n*/\nShuffle(list) :=\n{\n    Check(List?(list), \"Argument\", \"Argument must be a list.\");\n    \n    Local(index, randomIndex, temporary);\n    \n    list := FlatCopy(list);\n    \n    index := Length(list);\n    \n    While(index >? 1)\n    {\n        randomIndex := RandomInteger(1,index);\n        \n        temporary := list[randomIndex];\n        \n        list[randomIndex] := list[index];\n        \n        list[index] := temporary;\n        \n        index--;\n    };      \n    \n    list;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/Shuffle.mpw, (Shuffle)";
        scriptMap.put("Shuffle",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"ShuffledDeckNoSuits\",*);\n\n\nShuffledDeckNoSuits() := \n{\n   Shuffle(Concat(1 .. 13, 1 .. 13, 1 .. 13, 1 .. 13));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/ShuffledDeckNoSuits.mpw, (ShuffledDeckNoSuits)";
        scriptMap.put("ShuffledDeckNoSuits",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nStandardErrorOfTheEstimate(xList,yList) :=\n{   \n    Check(List?(xList), \"Argument\", \"The first argument must be a list.\");\n    \n    Check(List?(yList), \"Argument\", \"The second argument must be a list.\");\n    \n    Check(Length(xList) =? Length(yList), \"Argument\", \"The lists for argument 1 and argument 2 must have the same length.\");\n    \n    Local(n,a,b,regressionLine);\n    \n    regressionLine := RegressionLine(xList,yList);\n    \n    n := regressionLine[\"count\"];\n    \n    a := regressionLine[\"yIntercept\"];\n    \n    b := regressionLine[\"slope\"];\n        \n    NM(Sqrt((Sum(yList^2) - a*Sum(yList) - b*Sum(xList*yList))/(n-2)));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/StandardErrorOfTheEstimate.mpw, (StandardErrorOfTheEstimate)";
        scriptMap.put("StandardErrorOfTheEstimate",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"StandardErrorOfTheMean\",*);\n\nStandardErrorOfTheMean(sigma, sampleSize) :=\n{\n    Check(sigma >? 0, \"Argument\", \"The first argument must be a number which is greater than 0.\");\n    \n    Check(Integer?(sampleSize) And? sampleSize >? 0, \"Argument\", \"The second argument must be an integer which is greater than 0.\");\n    \n    sigma/Sqrt(sampleSize);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/StandardErrorOfTheMean.mpw, (StandardErrorOfTheMean)";
        scriptMap.put("StandardErrorOfTheMean",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"StandardErrorOfTheProportion\",*);\n\nStandardErrorOfTheProportion(meanOfSampleProportions, sampleSize) :=\n{\n    Check(RationalOrNumber?(meanOfSampleProportions), \"Argument\", \"The first argument must be a number.\");\n    \n    Check(Integer?(sampleSize) And? sampleSize >? 0, \"Argument\", \"The second argument must be an integer which is greater than 0.\");\n    \n    Sqrt((meanOfSampleProportions*(1 - meanOfSampleProportions))/sampleSize);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/StandardErrorOfTheProportion.mpw, (StandardErrorOfTheProportion)";
        scriptMap.put("StandardErrorOfTheProportion",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nStandardErrorOfTheSlope(xList,yList) :=\n{   \n    Check(List?(xList), \"Argument\", \"The first argument must be a list.\");\n    \n    Check(List?(yList), \"Argument\", \"The second argument must be a list.\");\n    \n    Check(Length(xList) =? Length(yList), \"Argument\", \"The lists for argument 1 and argument 2 must have the same length.\");\n    \n    Local(standardErrorOfTheEstimate,n,xMean);\n    \n    standardErrorOfTheEstimate := StandardErrorOfTheEstimate(xList,yList);\n    \n    n := Length(xList);\n    \n    xMean := Mean(xList);\n    \n    NM(standardErrorOfTheEstimate/Sqrt(Sum(xList^2) - n*xMean^2));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/StandardErrorOfTheSlope.mpw, (StandardErrorOfTheSlope)";
        scriptMap.put("StandardErrorOfTheSlope",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"Subset?\",*);\n\nSubset?(bigList, littleList) :=\n{\n    Local(result);\n    result := True;\n    \n    ForEach(element, littleList)\n    {\n        Decide(Not? Contains?(bigList,element), result := False);\n    };\n\n    result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/Subset_.mpw, (Subset?)";
        scriptMap.put("Subset?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"ValueToZScore\",*);\n\nValueToZScore(value,mean,standardDeviation) :=\n{\n    (value - mean)/standardDeviation;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/ValueToZScore.mpw, (ValueToZScore)";
        scriptMap.put("ValueToZScore",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\nWeightedMean(list) :=\n{\n\n    Check(List?(list), \"Argument\", \"Argument must be a list.\");\n    \n    Local( values, lastWeight, weights );\n    \n    values := [];\n    \n    weights := [];\n    \n    \n    ForEach(element,list)\n    {        \n        Check(List?(element), \"Argument\", \"Values and their associated weights must be in a list.\");\n        \n        Check(Length(element) =? 2, \"Argument\", \"Each value and its associated weight must be in a two element list.\");\n        \n        values := values ~ element[1];\n\n        weights := weights ~ element[2];\n    };\n    \n    Sum(values * weights)/Sum(weights);\n\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/WeightedMean.mpw, (WeightedMean)";
        scriptMap.put("WeightedMean",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* \n    This function was adapted from the Javascript version of function\n    that is located here:\n    \n    http://www.fourmilab.ch/rpkp/experiments/analysis/zCalc.js\n    http://www.fourmilab.ch/rpkp/experiments/analysis/zCalc.html?\n    \n    The following JavaScript functions for calculating normal and\n    chi-square probabilities and critical values were adapted by\n    John Walker from C implementations\n    written by Gary Perlman of Wang Institute, Tyngsboro, MA\n    01879. Both the original C code and this JavaScript edition\n    are in the public domain. \n*/\n\n\n\n/* \n    POZ -- probability of normal z value\n\n    Adapted from a polynomial approximation in:\n    Ibbetson D, Algorithm 209\n    Collected Algorithms of the CACM 1963 p. 616\n    Note:\n    This routine has six digit accuracy, so it is only useful for absolute\n    z values <?:= 6. For z values > to 6.0, poz() returns 1.0.\n*/\n\nZScoreToProbability(zScore) :=\n{\n    zScore := NM(zScore);\n    \n    Local( y, x, w, ZMAX, result);\n    \n    ZMAX := 6; // Maximum �z value\n    \n    If(zScore =? 0.0)\n    {\n        x := 0.0;\n    }\n    Else\n    {\n        y := 0.5 * AbsN(zScore);\n        \n        If(y >? ZMAX * 0.5)\n        {\n            x := 1.0;\n        }\n        Else If(y <? 1.0)\n        {\n            w := y * y;\n            x := ((((((((0.000124818987 * w\n            - 0.001075204047) * w + 0.005198775019) * w\n            - 0.019198292004) * w + 0.059054035642) * w\n            - 0.151968751364) * w + 0.319152932694) * w\n            - 0.531923007300) * w + 0.797884560593) * y * 2.0;\n        }\n        Else\n        {\n            y := y - 2.0;\n            \n            x := (((((((((((((-0.000045255659 * y\n            + 0.000152529290) * y - 0.000019538132) * y\n            - 0.000676904986) * y + 0.001390604284) * y\n            - 0.000794620820) * y - 0.002034254874) * y\n            + 0.006549791214) * y - 0.010557625006) * y\n            + 0.011630447319) * y - 0.009279453341) * y\n            + 0.005353579108) * y - 0.002141268741) * y\n            + 0.000535310849) * y + 0.999936657524;\n        };\n    };\n    \n        \n    Decide(zScore >? 0.0 , result := (x + 1.0) * 0.5 , result := (1.0 - x) * 0.5);\n    \n    result;\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/ZScoreToProbability.mpw, (ZScoreToProbability)";
        scriptMap.put("ZScoreToProbability",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nZScoreToValue(zScore, mean, standardDeviation) :=\n{\n    -((-mean)/standardDeviation - zScore)*standardDeviation;\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/proposed/statistics/ZScoreToValue.mpw, (ZScoreToValue)";
        scriptMap.put("ZScoreToValue",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/*\n * File `rabinmiller.mpi is an implementation of the\n *           Rabin-Miller primality test.\n */\n\n\n/*\n * FastModularPower(a, b, n) computes a^b (mod n) efficiently.\n * This function is called by IsStronglyProbablyPrime.\n */\n\nFastModularPower(a_PositiveInteger?, b_PositiveInteger?, n_PositiveInteger?) <--\n{\n  Local(p, j, r);\n  p := a;\n  j := b;\n  r := 1;\n\n  While (j >? 0)\n    {\n      Decide(Odd?(j), r := ModuloN(r*p, n));\n      p := ModuloN(p*p, n);\n      j := ShiftRight(j, 1);\n    };\n  r;\n};\n\n\n/*\n * An integer n is `strongly-probably-prime for base b if\n *\n *                   b^q = 1 (mod n) or\n * b^(q*2^i) = -1 (mod n) for some i such that 0 <= i < r\n *\n *    where q and r are such that n-1 = q*2^r and q is odd.\n *\n * If an integer is not strongly-probably-prime for a given\n * base b, then it is composed. The reciprocal is false.\n * Composed strongly-probably-prime numbers for base b\n * are called `strong pseudoprimes for base b.\n */\n// this will return a pair [root, True/False]\nIsStronglyProbablyPrime(b_PositiveInteger?, n_PositiveInteger?) <--\n{\n  Local(m, q, r, a, flag, i, root);\n  m := n-1;\n  q := m;\n  r := 0;\n  root := 0;        // will be the default return value of the \"root\"\n  While (Even?(q))\n  {\n    q := ShiftRight(q, 1);\n    r++;\n  };\n\n  a := FastModularPower(b, q, n);\n  flag := (a =? 1 Or? a =? m);\n  i := 1;\n\n  While (Not?(flag) And? (i <? r))\n  {\n        root := a;        // this is the value of the root if flag becomes true now\n    a := ModuloN(a*a, n);\n    flag := (a =? m);\n    i++;\n  };\n\n  [root, flag];        // return a root of -1 (or 0 if not found)\n};\n\n\n/*\n * For numbers less than 3.4e14, exhaustive computations have\n * shown that there is no strong pseudoprime simultaneously for\n * bases 2, 3, 5, 7, 11, 13 and 17.\n * Function RabinMillerSmall is based on the results of these\n * computations.\n */\n\n10 # RabinMillerSmall(1) <-- False;\n\n10 # RabinMillerSmall(2) <-- True;\n\n20 # RabinMillerSmall(n_Even?) <-- False;\n\n20 # RabinMillerSmall(3) <-- True;\n\n30 # RabinMillerSmall(n_PositiveInteger?) <--\n{\n  Local(continue, prime, i, primetable, pseudotable, root);\n  continue := True;\n  prime := True;\n  i := 1;\n  primetable := [2, 3, 5, 7, 11, 13, 17];\n  pseudotable := [2047, 1373653, 25326001, 3215031751, 2152302898747,\n                  3474749660383, 34155071728321];\n  // if n is strongly probably prime for all bases up to and including primetable[i], then n is actually prime unless it is >= pseudotable[i].\n  While (continue And? prime And? (i <? 8))\n  {        // we do not really need to collect the information about roots of -1 here, so we do not do anything with root\n    [root, prime] := IsStronglyProbablyPrime(primetable[i], n);\n    //Decide(InVerboseMode() And? prime, Echo(\"RabinMiller: Info: \", n, \"is spp base\", primetable[i]));\n    continue := (n >=? pseudotable[i]);\n\n    i++;\n  };\n  // the function returns \"Overflow\" when we failed to check (i.e. the number n was too large)\n  Decide(continue And? (i =? 8), Overflow, prime);\n};\n\n\n/*\n * RabinMillerProbabilistic(n, p) tells whether n is prime.\n * If n is actually prime, the result will always be `True.\n * If n is composed the probability to obtain the wrong\n * result is less than 4^(-p).\n */\n// these 4 rules are not really used now because RabinMillerProbabilistic is only called for large enough n\n10 # RabinMillerProbabilistic(1, _p) <-- False;\n\n10 # RabinMillerProbabilistic(2, _p) <-- True;\n\n20 # RabinMillerProbabilistic(n_Even?, _p) <-- False;\n\n20 # RabinMillerProbabilistic(3, _p) <-- True;\n\n30 # RabinMillerProbabilistic(n_PositiveInteger?, p_PositiveInteger?) <--\n{\n  Local(k, prime, b, rootsofminus1, root);\n  k := 1+IntLog(IntLog(n,2),4)+p;        // find k such that Ln(n)*4^(-k) < 4^(-p)\n  b := 1;\n  prime := True;\n  rootsofminus1 := [0];        // accumulate the set of roots of -1 modulo n\n  While (prime And? k>?0)\n    {\n      b := NextPseudoPrime(b);        // use only prime bases, as suggested by Davenport; weak pseudo-primes are good enough\n      [root, prime] := IsStronglyProbablyPrime(b, n);\n          Decide(prime, rootsofminus1 := Union(rootsofminus1, [root]));\n          Decide(Length(rootsofminus1)>?3, prime := False);\n          //Decide(InVerboseMode() And? prime, Echo(\"RabinMiller: Info: \", n, \"is spp base\", b));\n          Decide( // this whole Decide() clause is only working when InVerboseMode() is in effect and the test is terminated in the unusual way\n                  InVerboseMode() And? Length(rootsofminus1)>?3,\n                  {        // we can actually find a factor of n now\n                        Local(factor);\n                        rootsofminus1 := Difference(rootsofminus1,[0]);\n                        //Echo(\"RabinMiller: Info: \", n, \"is composite via roots of -1 ; \", rootsofminus1);\n                        factor := Gcd(n, Decide(\n                                rootsofminus1[1]+rootsofminus1[2]=?n,\n                                rootsofminus1[1]+rootsofminus1[3],\n                                rootsofminus1[1]+rootsofminus1[2]\n                        ));\n                        Echo(n, \" = \", factor, \" * \", n/factor);\n                }\n          );\n      k--;\n    };\n  prime;\n};\n\n\n/*\n * This is the frontend function, which uses RabinMillerSmall for\n * ``small'' numbers and RabinMillerProbabilistic for bigger ones.\n *\n * The probability to err is set to 1e-25, hopeing this is less\n * than the one to step on a rattlesnake in northern Groenland. :-)\n */\n\nRabinMiller(n_PositiveInteger?) <--\n{\n        //Decide(InVerboseMode(), Echo(\"RabinMiller: Info: Testing \", n));\n        Decide(\n                n <? 34155071728321,\n                RabinMillerSmall(n),\n                RabinMillerProbabilistic(n, 40)        // 4^(-40)\n        );\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/rabinmiller/RabinMiller.mpw, (RabinMiller;FastModularPower;IsStronglyProbablyPrime;RabinMillerSmall;RabinMillerProbabilistic)";
        scriptMap.put("RabinMiller",scriptString);
        scriptMap.put("FastModularPower",scriptString);
        scriptMap.put("IsStronglyProbablyPrime",scriptString);
        scriptMap.put("RabinMillerSmall",scriptString);
        scriptMap.put("RabinMillerProbabilistic",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"RandomInteger\", *);\n\n\n10 # RandomInteger(_n) <--\n{\n    Check(PositiveInteger?(n), \"Argument\", \"The argument must be a positive integer.\");\n\n    CeilN(Random() * n);\n};\n\n\n\n10 # RandomInteger(_lowerBoundInclusive, _upperBoundInclusive) <--\n{\n    Check(Integer?(lowerBoundInclusive) And? Integer?(upperBoundInclusive), \"Argument\", \"Both arguments must be integers.\");\n\n    Check(lowerBoundInclusive <? upperBoundInclusive, \"Argument\", \"The first argument must be less than the second argument.\");\n\n    FloorN(lowerBoundInclusive + Random() * (upperBoundInclusive + 1 - lowerBoundInclusive) );\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/random/RandomInteger.mpw, (RandomInteger)";
        scriptMap.put("RandomInteger",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRandomIntegerList(_count,_coefmin,_coefmax) <--\n  BuildList(FloorN(coefmin+Random()*(coefmax+1-coefmin)),i,1,count,1);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/random/RandomIntegerList.mpw, (RandomIntegerList)";
        scriptMap.put("RandomIntegerList",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRandomIntegerMatrix(_rows,_cols,_coefmin,_coefmax) <--\n        BuildMatrix([[i,j], FloorN(coefmin+Random()*(coefmax+1-coefmin))], rows, cols );\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/random/RandomIntegerMatrix.mpw, (RandomIntegerMatrix)";
        scriptMap.put("RandomIntegerMatrix",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"RandomInterestingPolynomial\",*);\n//Retract(\"NewRandomPoly\",*);\n//Retract(\"RandomIrreducibleQuadratic\",*);\n//Retract(\"RandomIrreducibleQuadraticWithComplexRoots\",*);\n//Retract(\"RandomIrreducibleQuadraticWithRealRoots\",*);\n\n\n10 # RandomInterestingPolynomial( deg_PositiveInteger?, _var ) <--\n{\n    RandomSeed( SystemTimer() );   //  randomize random number generator\n    NewRandomPoly(deg,var);   \n};\n\n\n10 # NewRandomPoly( _deg, _var )::(Equal?(deg,1)) <--\n{\n    Local(p,i1,i2);\n    i1 := RandomInteger(1,10);\n    i2 := RandomInteger(-10,10);\n    p  := NormalForm(UniVariate(var,0,[i2,i1]));\n};\n\n\n10 # NewRandomPoly( _deg, _var )::(Equal?(deg,2)) <--\n{\n    Local(ii,i1,i2,p,quadPoly);\n    p := FillList(0,2);\n    For(ii:=1,ii<=?2,ii++)\n    {\n        i1 := RandomInteger(10);\n        i2 := RandomInteger(-10,10);\n        Decide( i1 >? 1, i2 := i1*i2 );\n        p[ii] := NormalForm(UniVariate(var,0,[i2,i1]));\n    };\n    quadPoly := ExpandBrackets(p[1]*p[2]);\n    quadPoly := Simplify(Quotient(quadPoly,LeadingCoef(quadPoly)));\n};\n\n\n10 # RandomIrreducibleQuadratic( _var ) <--\n{\n    Local(ii,coeffs,discrim,u,p,f);\n    // Use random integers for coefficients a2 and a1.  Then select a0 \n    // in one of two ways:\n    //  (1) so that discriminant is negative integer,  or\n    //  (2) so that discriminant is positive integer but not square.\n    Decide(RandomInteger(2)=?1,\n        RandomIrreducibleQuadraticWithComplexRoots(var),\n        RandomIrreducibleQuadraticWithRealRoots(var)\n    );\n};\n\n\n10 # RandomIrreducibleQuadraticWithRealRoots(_var) <--\n{\n    Local(coeffs,ijk);\n    coeffs    := FillList(1,3);\n    coeffs[2] := RandomInteger(-10,10);\n    coeffs[3] := RandomInteger(1,10);\n    ijk := Floor(coeffs[2]^2 / (4*coeffs[3]));\n    coeffs[1] := RandomInteger(-10,ijk);\n    discrim := coeffs[2]^2-4*coeffs[1]*coeffs[3];\n    NormalForm(UniVariate(var,0,coeffs));\n};\n\n\n10 # RandomIrreducibleQuadraticWithComplexRoots(_var) <--\n{\n    Local(coeffs,ijk);\n    coeffs := [1,RandomInteger(-10,10),RandomInteger(1,10)];\n    coeffs[1] := Ceil(NM(coeffs[2]^2/(4*coeffs[3]))) + RandomInteger(1,5);\n    NormalForm(UniVariate(var,0,coeffs));\n};\n\n\n10 # NewRandomPoly( _deg, _var )::(Equal?(deg,3)) <--\n{\n    Local(ii,i1,i2,i3,p,CubicPoly);\n    p := FillList(1,3);\n    Decide( RandomInteger(3) =? 1,\n      {\n        For(ii:=1,ii<=?3,ii++)\n        {\n          i1 := RandomInteger(2);\n          i2 := RandomInteger(-10,10);\n          Decide( i1 >? 1, i2 := i1*i2 );\n          p[ii] := NormalForm(UniVariate(var,0,[i2,i1]));\n        };\n      },\n      {\n          i1 := RandomInteger(2);\n          i2 := RandomInteger(-10,10);\n          Decide( i1 >? 1, i2 := i1*i2 );\n          p[1] := NormalForm(UniVariate(var,0,[i2,i1]));\n          p[2] := RandomIrreducibleQuadratic(var);\n      }\n    );\n    CubicPoly := ExpandBrackets(Product(p));\n};\n\n\n10 # NewRandomPoly( _deg, _var )::(Equal?(deg,4)) <--\n{\n    Local(ii,i1,i2,i3,i4,p,QuarticPoly);\n    p := FillList(1,4);\n    Decide( RandomInteger(2) =? 1,\n      {\n          p[1] := NewRandomPoly(3,x);\n          i1 := RandomInteger(2);\n          i2 := RandomInteger(-10,10);\n          Decide( i1 >? 1, i2 := i1*i2 );\n          p[2] := NormalForm(UniVariate(var,0,[i2,i1]));\n      },\n      {\n          p[1] := NewRandomPoly(2,x);\n          p[2] := NewRandomPoly(2,x);\n      }\n    );\n    QuarticPoly := ExpandBrackets(Product(p));\n};\n\n\n10 # NewRandomPoly( _deg, _var )::(Equal?(deg,5)) <--\n{\n    Local(ii,i1,i2,i3,i4,p,QuinticPoly);\n    p := FillList(1,4);\n    p[1] := NewRandomPoly(1,x);\n    p[2] := RandomIrreducibleQuadraticWithRealRoots(x);\n    p[3] := RandomIrreducibleQuadraticWithComplexRoots(x);\n    QuinticPoly := ExpandBrackets(Product(p));\n};\n\n\n11 # NewRandomPoly( deg_PositiveInteger?, _var )::(deg >? 5) <--\n{\n    Local(p,n,m);\n    p := [];\n    m := deg;\n    Until( m <? 3 )\n    {\n        n := RandomInteger(2,Floor(NM(deg/2)));\n        Tell(\"     \",[m,n]);\n        Push(p,NewRandomPoly(n,var));\n        m := m - n;\n    };\n    Tell(\"      \",m);\n    Decide( m >? 0, Push(p,NewRandomPoly(m,x)));\n    Expand(Product(p));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/random/RandomInterestingPolynomial.mpw, (RandomInterestingPolynomial;NewRandomPoly;RandomIrreducibleQuadratic;RandomIrreducibleQuadraticWithRealRoots;RandomIrreducibleQuadraticWithComplexRoots)";
        scriptMap.put("RandomInterestingPolynomial",scriptString);
        scriptMap.put("NewRandomPoly",scriptString);
        scriptMap.put("RandomIrreducibleQuadratic",scriptString);
        scriptMap.put("RandomIrreducibleQuadraticWithRealRoots",scriptString);
        scriptMap.put("RandomIrreducibleQuadraticWithComplexRoots",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Generate a random polynomial */\n\nRandomPoly(_var,_degree,_coefmin,_coefmax) <--\n  NormalForm(UniVariate(var,0,RandomIntegerList(degree+1,coefmin,coefmax)));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/random/RandomPoly.mpw, (RandomPoly)";
        scriptMap.put("RandomPoly",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* def file definitions\n\nRandomSeed\nRandom\nRng\nRngSeed\nRngCreate\n*/\n\n/*\nRandom number generators implemented in an object-oriented manner.\n\nOld interface (still works):\n\n        RandomSeed(123);\n        Random(); Random();\n\nIt provides only one global RNG with a globally assigned seed.\n\nNew interface allows creating many RNG objects:\n\n        r1:=RngCreate();        // create a default RNG object, assign structure to r1\n        r2:=RngCreate(12345);        // create RNG object with given seed\n        r3:=RngCreate(seed: 0, engine: advanced, dist: gauss);         // extended options: specify seed, type of RNG engine and the type of statistical distribution\n        Rng(r1); Rng(r1); Rng(r2);        // generate some floating-point numbers\n        RngSeed(r1, 12345);        // r1 is re-initialized with given seed, r2 is unaffected\n\nMore \"RNG engines\" and \"RNG distribution adaptors\" can be defined later (at run time).\n\nRngCreate() will return an object of the following structure:\n        [SomeDist, SomeEngine, state ]\n\nhere SomeEngine is a function atom that describes the RNG engine,\nSomeDist is a function atom that specifies the distribution adaptor,\nand state is a \"RNG state object\", e.g. a list of all numbers that specify the current RNG state (seeds, temporaries, etc.).\n\nRngSeed(r1, seed) expects an integer seed.\nIt will re-initialize the RNG object r1 with the given seed.\n\nThe \"RNG engine API\": calling RngCreate with engine: SomeEngine expects that:\n        SomeEngine(seed_Integer?) will create and initialize a state object with given seed and return the new state object (a list). SomeEngine can assume that \"seed\" is a positive integer.\n        SomeEngine(state1_List?) will update the RNG state object state1 and return the pair [new state object, new number].\n\nThe \"RNG distribution adaptor API\": calling RngCreate with distribution: SomeDist expects that:\n        SomeDist(r1) will update the RNG object r1 and return the pair [new state object, new number]. r1 is a full RNG object, not just a state object.\n\n\n*/\n\n//////////////////////////////////////////////////\n/// lists of defined RNG entities\n//////////////////////////////////////////////////\n\n/// The idea is that options must be easy to type, but procedure names could be long.\n\nLocalSymbols(knownRNGEngines, knownRNGDists) {\n  knownRNGEngines :=\n  [\n    [ \"default\", \"RNGEngineLCG2\"],\n    [ \"advanced\", \"RNGEngineLEcuyer\"],\n  ];\n\n  knownRNGDists :=\n  [\n    [\"default\", \"FlatRNGDist\"],\n    [\"flat\", \"FlatRNGDist\"],\n  //        [\"uniform\", \"FlatRNGDist\"],        // we probably don't need this alias...\n    [\"gauss\", \"GaussianRNGDist\"],\n  ];\n\n  KnownRNGDists() := knownRNGDists;\n  KnownRNGEngines() := knownRNGEngines;\n};\n\n\n//////////////////////////////////////////////////\n/// RNG object API\n//////////////////////////////////////////////////\n\nFunction() RngCreate();\nFunction() RngCreate(_seed, ...);\n//HoldArgument(\"RngCreate\", \"seed\");        // this is needed to prevent evaluation of = and also to prevent substitution of variables, e.g. if \"seed\" is defined\n//UnFence(\"RngCreate\", 0);\n//UnFence(\"RngCreate\", 1);\nFunction() RngSeed(_r, _seed);\n//UnFence(\"RngSeed\", 2);\n/// accessor for RNG objects\nFunction() Rng(_r);\n//UnFence(\"Rng\", 1);\n\n\nRngCreate() <-- RngCreate(0);\n\n10 # RngCreate(aseed_Integer?) <-- (RngCreate @ [seed: aseed]);\n\n// a single option given: convert explicitly to a list\n20 # RngCreate(_key: _value) <-- `(RngCreate([@key: value]));\n\n// expect a list of options\n30 # RngCreate(options_List?) <--\n{\n        options := OptionsListToHash @ [options];\n\n        // check options and assign defaults\n        Decide(\n                options[\"seed\"] =? Empty Or? options[\"seed\"] <=? 0,\n                options[\"seed\"] := 76544321        // some default seed out of the blue sky\n        );\n        Decide(\n                options[\"engine\"] =? Empty Or? Not? (Assert(\"warning\", [\"RngCreate: invalid engine\", options[\"engine\"]]) KnownRNGEngines()[options[\"engine\"] ] !=? Empty),\n                options[\"engine\"] := \"default\"\n        );\n        Decide(\n                options[\"dist\"] =? Empty Or? Not? (Assert(\"warning\", [\"RngCreate: invalid distribution\", options[\"dist\"]]) KnownRNGDists()[options[\"dist\"] ] !=? Empty),\n                options[\"dist\"] := \"default\"\n        );\n\n        // construct a new RNG object\n        // a RNG object has the form [\"SomeDist\", \"SomeEngine\", [state]]\n        [\n                KnownRNGDists()[options[\"dist\"] ], KnownRNGEngines()[options[\"engine\"] ],\n                // initialize object with given seed using \"SomeEngine\"(seed)\n                KnownRNGEngines()[options[\"engine\"] ] @ [ options[\"seed\"] ]\n        ];\n};\n\n/// accessor function: will call SomeDist(r) and update r\nRng(_r) <--\n{\n        Local(state, result);\n        [state, result] := (r[1] @ [r]);        // this calls SomeDist(r)\n        DestructiveReplace(r, 3, state);        // update RNG object\n        result;        // return floating-point number\n};\n\n/// set seed: will call SomeEngine(r, seed) and update r\nRngSeed(_r, seed_Integer?) <--\n{\n        Local(state);\n        (Assert(\"warning\", [\"RngSeed: seed must be positive\", seed]) seed >? 0\n        ) Or? (seed:=76544321);\n        state := (r[2] @ [seed]);        // this calls SomeEngine(r)\n        DestructiveReplace(r, 3, state);        // update object\n        True;\n};\n\n//////////////////////////////////////////////////\n/// RNG distribution adaptors\n//////////////////////////////////////////////////\n\n/// trivial distribution adaptor: flat distribution, simply calls SomeEngine(r)\n/* we have to return whole objects; we can't use references b/c the core\nfunction ApplyFast will not work properly on references, i.e. if r =? [\"\", \"\", [1]] so that\nr[3] =? [1], then LCG2(r[3]) modifies r[3], but LCG2 @ r[3] or\nApplyFast(\"LCG2\", [r[3]]) do not actually modify r[3].\n*/\n\n// return pair [state, number]\nFlatRNGDist(_r) <-- (r[2] @ [r[3]]);        // this calls SomeEngine(state)\n\n/// Gaussian distribution adaptor, returns a complex number with normal distribution with unit variance, i.e. Re and Im are independent and both have unit variance\n/* Gaussian random number, Using the Box-Muller transform, from Knuth,\n   \"The Art of Computer Programming\",\n   Volume 2 (Seminumerical algorithms, third edition), section 3.4.1\n */\nGaussianRNGDist(_rng) <--\n{\n        // a Gaussian distributed complex number p + I*q is made up of two uniformly distributed numbers x,y according to the formula:\n        // a:=2*x-1, b:=2*y-1, m:=a^2+b^2; p =? a*Sqrt(-2*Ln(m)/m); q:=b*Sqrt(-2*Ln(m)/m);\n        // here we need to make sure that m is nonzero and strictly less than 1.\n        Local(a,b,m, newstate, rnumber);\n        newstate := rng[3];        // this will be updated at the end\n        m:=0;\n        While(m=?0 Or? m>=?1)        // repeat generating new x,y  - should not take more than one iteration really\n        {\n                [newstate, rnumber] := (rng[2] @ [newstate]);\n                a:=2*rnumber-1;\n                [newstate, rnumber] := (rng[2] @ [newstate]);\n                b:=2*rnumber-1;\n                m:=a*a+b*b;\n        };\n        [newstate, (a+I*b)*SqrtN(-2*DivideN(InternalLnNum(m),m))];\n};\n\n\n//////////////////////////////////////////////////\n/// RNG engines\n//////////////////////////////////////////////////\n\n/// default RNG engine: the LCG generator\n\n// first method: initialize a state object with given seed\nRNGEngineLCG1(seed_Integer?) <-- [seed];\n// second method: update state object and return new number\nRNGEngineLCG1(state_List?) <-- LCG1(state);\n\n// first method: initialize a state object with given seed\nRNGEngineLCG2(seed_Integer?) <-- [seed];\n// second method: update state object and return new number\nRNGEngineLCG2(state_List?) <-- LCG2(state);\n\n// first method: initialize a state object with given seed\nRNGEngineLCG3(seed_Integer?) <-- [seed];\n// second method: update state object and return new number\nRNGEngineLCG3(state_List?) <-- LCG3(state);\n\n// first method: initialize a state object with given seed\nRNGEngineLCG4(seed_Integer?) <-- [seed];\n// second method: update state object and return new number\nRNGEngineLCG4(state_List?) <-- LCG4(state);\n\n/// parameters from P. Hellekalek, 1994; see G. S. Fishman, Math. Comp. vol. 54, 331 (1990)\nLCG1(state) := RandomLCG(state, 2147483647,950706376,0);\nLCG2(state) := RandomLCG(state, 4294967296,1099087573,0);\nLCG3(state) := RandomLCG(state, 281474976710656,68909602460261,0);\nLCG4(state) := RandomLCG(state, 18014398509481984,2783377640906189,0);\n\n/// Linear congruential generator engine: backend\n// state is a list with one element\nRandomLCG(_state, _im, _ia, _ic) <--\n[\n        DestructiveReplace(state,1, ModuloN(state[1]*ia+ic,im)),\n        DivideN(state[1], im)        // division should never give 1\n];\n\n/// Advanced RNG engine due to LEcuyer et al.\n/// RNG from P. Lecuyer et al (2000). Period approximately 2^191\n// state information: 6 32-bit integers, corresponding to [x3,x2,x1,y3,y2,y1]\n\n// first method: initialize a state object with given seed\nRNGEngineLEcuyer(aseed_Integer?) <--\n{\n        // use LCG2 as auxiliary RNG to fill the seeds\n        Local(rngaux, result);\n        rngaux := (RngCreate @ [aseed]);\n        // this will be the state vector\n        result:=ZeroVector(6);\n        // fill the state object with random numbers\n        Local(i);\n        For(i:=1, i<=?6, i++)\n        {\n                Rng(rngaux);\n                result[i] := rngaux[3][1];        // hack to get the integer part\n        };\n        // return the state object\n        result;\n};\n\n// second method: update state object and return a new random number (floating-point)\nRNGEngineLEcuyer(state_List?) <--\n{\n        Local(newstate, result);\n        newstate := [\n                Modulo(1403580*state[2]-810728*state[3], 4294967087), state[1], state[2],\n                Modulo(527612*state[4]-1370589*state[6], 4294944433), state[4], state[5]\n        ];\n        result:=Modulo(state[1]-state[4], 4294967087);\n        [\n                newstate,\n                DivideN(Decide(result=?0, 4294967087, result), 4294967088)\n        ];\n};\n\n//////////////////////////////////////////////////\n/// old interface: using one global RNG object\n//////////////////////////////////////////////////\n/* this is a little slower but entirely equivalent to the code below\nGlobalRNG := RngCreate(76544321);\nRandom() := Rng(GlobalRNG);\nRandomSeed(seed) := RngSeed(GlobalRNG, seed);\n*/\n\nLocalSymbols(RandSeed) {\n  // initial seed should be nonzero\n  RandSeed := SystemTimer(); //Was 76544321.\n\n  /// assign random seed\n  Function(\"RandomSeed\", [_seed]) Assign(RandSeed, seed);\n\n  /// Linear congruential generator\n  RandomLCG(_im, _ia, _ic) <--\n  {\n    RandSeed:=ModuloN(RandSeed*ia+ic,im);\n    DivideN(RandSeed,im);        // should never give 1\n  };\n}; // LocalSymbols(RandSeed)\n\n\nFunction(\"Random1\",[]) RandomLCG(4294967296,1103515245,12345);\nFunction(\"Random6\",[]) RandomLCG(1771875,2416,374441);\n/// parameters from P. Hellekalek, 1994; see G. S. Fishman, Math. Comp. vol. 54, 331 (1990)\nFunction(\"Random2\",[]) RandomLCG(2147483647,950706376,0);\nFunction(\"Random3\",[]) RandomLCG(4294967296,1099087573,0);\nFunction(\"Random4\",[]) RandomLCG(281474976710656,68909602460261,0);\nFunction(\"Random5\",[]) RandomLCG(18014398509481984,2783377640906189,0);\n\n// select one of them\nFunction(\"Random\",[]) Random3();\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/random/random.mpw, (RandomSeed;Random;Rng;RngSeed;RngCreate;FlatRNGDist;GaussianRNGDist;RNGEngineLCG1;RNGEngineLCG2;RNGEngineLCG3;RNGEngineLCG4;RandomLCG;RNGEngineLEcuyer)";
        scriptMap.put("RandomSeed",scriptString);
        scriptMap.put("Random",scriptString);
        scriptMap.put("Rng",scriptString);
        scriptMap.put("RngSeed",scriptString);
        scriptMap.put("RngCreate",scriptString);
        scriptMap.put("FlatRNGDist",scriptString);
        scriptMap.put("GaussianRNGDist",scriptString);
        scriptMap.put("RNGEngineLCG1",scriptString);
        scriptMap.put("RNGEngineLCG2",scriptString);
        scriptMap.put("RNGEngineLCG3",scriptString);
        scriptMap.put("RNGEngineLCG4",scriptString);
        scriptMap.put("RandomLCG",scriptString);
        scriptMap.put("RNGEngineLEcuyer",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"Combine\",*);\n\n10 # Combine(expr_Zero?) <-- 0;\n\n20 # Combine(_expr) <-- \n{\n    Local(L);\n    L := ReassembleListTerms(DisassembleExpression(expr));\n    UnFlatten(L,\"+\",0);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/simplify/Combine.mpw, (Combine)";
        scriptMap.put("Combine",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nEliminate(_var,_replace,_function) <-- Simplify(Substitute(var,replace)function);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/simplify/Eliminate.mpw, (Eliminate)";
        scriptMap.put("Eliminate",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nLocalSymbols(AssembleTerms, AssembleTermsRecursive)\n{\n\n    AssembleTerms(list) :=\n    {\n        Check(List?(list), \"Argument\", \"The argument must be a list.\");\n\n        Decide(Length(list) =? 1,\n            First(list),\n            AssembleTermsRecursive(Reverse(list)) \n        );\n    };\n\n\n    AssembleTermsRecursive(list) :=\n    {\n        Decide(Type(list[1]) =? \"-\" Or? NegativeNumber?(list[1]) Or? Type(list[1]) =? \"/\" And? (Type(Numerator(list[1])) =? \"-\" Or? NegativeNumber?(Numerator(list[1]))),\n            Decide(Length(list) =? 2,\n                ListToFunction([ToAtom(\"-\"), list[2], -list[1]] ),\n                ListToFunction([ToAtom(\"-\"), AssembleTermsRecursive(Rest(list)), -First(list)] )\n            ),\n            Decide(Length(list) =? 2,\n                ListToFunction([ToAtom(\"+\"), list[2], list[1]] ),\n                ListToFunction([ToAtom(\"+\"), AssembleTermsRecursive(Rest(list)), First(list)] )\n            )\n        );\n    };\n\n\n10 # ExpandBrackets(xx_Zero?) <-- 0;\n\n20 # ExpandBrackets(_xx)::(Type(xx)=?\"/\" Or? Type(-xx)=?\"/\") <--\n{\n    Local(N,D,t);\n    N := ReassembleListTerms(DisassembleExpression(Numerator(xx)));\n    D := ExpandBrackets(Denominator(xx));\n    AssembleTerms(MapSingle('[[t], t / D], N));\n};\n\n\n30 # ExpandBrackets(_xx) <-- AssembleTerms(ReassembleListTerms(DisassembleExpression(xx)));\n\n};\n\n\n\n//ExpandBrackets(_xx) <-- SimpExpand(SimpImplode(SimpFlatten(xx)));\n//ExpandBrackets(x) := NormalForm(MM(x));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/simplify/ExpandBrackets.mpw, (ExpandBrackets)";
        scriptMap.put("ExpandBrackets",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//////////////////////////////////////////////////\n/// ExpandFrac --- normalize rational functions (no simplification)\n//////////////////////////////////////////////////\n\n5 # ExpandFrac(expr_List?) <-- MapSingle(\"ExpandFrac\", expr);\n\n// expression does not contain fractions\n10 # ExpandFrac(_expr)::Not?(HasFunctionSome?(expr, \"/\", [ToAtom(\"+\"), ToAtom(\"-\"), *, /, ^])) <-- expr;\n15 # ExpandFrac(a_RationalOrNumber?) <-- a;\n20 # ExpandFrac(_expr) <-- ExpandFraccombine(GetNumerDenom(expr));\n\nExpandFraccombine([_a, _b]) <-- a/b;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/simplify/ExpandFrac.mpw, (ExpandFrac;ExpandFraccombine)";
        scriptMap.put("ExpandFrac",scriptString);
        scriptMap.put("ExpandFraccombine",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(\"DoFlatten\",[_doflattenx]);\nUnFence(\"DoFlatten\",1);\n\n10 # DoFlatten(_doflattenx)::(Type(doflattenx)=?flattenoper) <--\n     Apply(\"Concat\",MapSingle(\"DoFlatten\",Rest(FunctionToList(doflattenx))));\n20 # DoFlatten(_doflattenx) <-- [ doflattenx ];\n\n\nFunction(\"Flatten\",[_body,_flattenoper])\n{\n  DoFlatten(body);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/simplify/Flatten.mpw, (Flatten;DoFlatten)";
        scriptMap.put("Flatten",scriptString);
        scriptMap.put("DoFlatten",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// GetNumerDenom(x) returns a pair of expressions representing normalized numerator and denominator; GetNumerDenom(x, a) multiplies the numerator by the number a\nGetNumerDenom(_expr, _a) <-- GetNumerDenom(expr)*[a,1];\n\n// on expressions that are not fractions, we return unit denominator\n10 # GetNumerDenom(_expr)::Not?(HasFunctionSome?(expr, \"/\", [ToAtom(\"+\"), ToAtom(\"-\"), *, /, ^])) <-- [expr, 1];\n// rational numbers are not simplified\n15 # GetNumerDenom(a_RationalOrNumber?) <-- [a, 1];\n// arithmetic\n20 # GetNumerDenom(_a + _b) <-- ExpandFracadd(GetNumerDenom(a), GetNumerDenom(b));\n20 # GetNumerDenom(_a - _b) <-- ExpandFracadd(GetNumerDenom(a), GetNumerDenom(b, -1));\n20 # GetNumerDenom(- _a) <-- GetNumerDenom(a, -1);\n20 # GetNumerDenom(+ _a) <-- GetNumerDenom(a);\n20 # GetNumerDenom(_a * _b) <-- ExpandFracmultiply(GetNumerDenom(a), GetNumerDenom(b));\n20 # GetNumerDenom(_a / _b) <-- ExpandFracdivide(GetNumerDenom(a), GetNumerDenom(b));\n// integer powers\n20 # GetNumerDenom(_a ^ b_Integer?)::(b >? 1) <-- ExpandFracmultiply(GetNumerDenom(a), GetNumerDenom(a^(b-1)));\n20 # GetNumerDenom(_a ^ b_Integer?)::(b <? -1) <-- ExpandFracdivide(GetNumerDenom(1), GetNumerDenom(a^(-b)));\n20 # GetNumerDenom(_a ^ b_Integer?)::(b =? -1) <-- ExpandFracdivide(GetNumerDenom(1), GetNumerDenom(a));\n// non-integer powers are not considered to be rational functions\n25 # GetNumerDenom(_a ^ _b) <-- [a^b, 1];\n\n// arithmetic on fractions; not doing any simplification here, whereas we might want to\nExpandFracadd([_a, _b], [_c, _d]) <-- [a*d+b*c, b*d];\nExpandFracmultiply([_a, _b], [_c, _d]) <-- [a*c, b*d];\nExpandFracdivide([_a, _b], [_c, _d]) <-- [a*d, b*c];\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/simplify/GetNumerDenom.mpw, (GetNumerDenom;ExpandFracadd;ExpandFracmultiply;ExpandFracdivide)";
        scriptMap.put("GetNumerDenom",scriptString);
        scriptMap.put("ExpandFracadd",scriptString);
        scriptMap.put("ExpandFracmultiply",scriptString);
        scriptMap.put("ExpandFracdivide",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(\"SimpAdd\",[_x,_y]);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/simplify/SimpAdd.mpw, (SimpAdd)";
        scriptMap.put("SimpAdd",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(\"SimpDiv\",[_x,_y]);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/simplify/SimpDiv.mpw, (SimpDiv)";
        scriptMap.put("SimpDiv",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # SimpExpand(SimpAdd(_x,_y)) <-- SimpExpand(x) + SimpExpand(y);\n10 # SimpExpand(SimpMul(_x,_y)) <-- SimpExpand(x) * SimpExpand(y);\n10 # SimpExpand(SimpDiv(_x,_y)) <-- SimpExpand(x) / SimpExpand(y);\n20 # SimpExpand(_x) <-- x;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/simplify/SimpExpand.mpw, (SimpExpand)";
        scriptMap.put("SimpExpand",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # SimpFlatten((_x)+(_y)) <-- SimpAdd(SimpFlatten(x),SimpFlatten(y));\n10 # SimpFlatten((_x)-(_y)) <-- SimpAdd(SimpFlatten(x),SimpMul(-1,SimpFlatten(y)));\n10 # SimpFlatten(    -(_y)) <-- SimpMul(-1,SimpFlatten(y));\n\n10 # SimpFlatten((_x)*(_y)) <-- SimpMul(SimpFlatten(x),SimpFlatten(y));\n10 # SimpFlatten((_x)/(_y)) <-- SimpDiv(SimpFlatten(x),SimpFlatten(y));\n10 # SimpFlatten((_x)^(n_PositiveInteger?)) <--\n     SimpMul(SimpFlatten(x),SimpFlatten(x^(n-1)));\n\n100 # SimpFlatten(_x) <--\n{\n  x;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/simplify/SimpFlatten.mpw, (SimpFlatten)";
        scriptMap.put("SimpFlatten",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Distributed multiplication rule */\n10 # SimpImplode(SimpMul(SimpAdd(_x,_y),_z)) <--\n     SimpImplode(SimpAdd(SimpImplode(SimpMul(x,z)),\n                 SimpImplode(SimpMul(y,z))));\n10 # SimpImplode(SimpMul(_z,SimpAdd(_x,_y))) <--\n     SimpImplode(SimpAdd(SimpImplode(SimpMul(z,x)),\n                 SimpImplode(SimpMul(z,y))));\n/* Distributed division rule  */\n10 # SimpImplode(SimpDiv(SimpAdd(_x,_y),_z)) <--\n     SimpImplode(SimpAdd(SimpImplode(SimpDiv(x,z)),\n     SimpImplode(SimpDiv(y,z))));\n\n\n\n20 # SimpImplode(SimpAdd(_x,_y)) <--\n     SimpAdd(SimpImplode(x),SimpImplode(y));\n20 # SimpImplode(SimpMul(_x,_y)) <--\n     SimpMul(SimpImplode(x),SimpImplode(y));\n20 # SimpImplode(SimpDiv(_x,_y)) <--\n     SimpDiv(SimpImplode(x),SimpImplode(y));\n30 # SimpImplode(_x) <-- x;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/simplify/SimpImplode.mpw, (SimpImplode)";
        scriptMap.put("SimpImplode",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(\"SimpMul\",[_x,_y\"]);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/simplify/SimpMul.mpw, (SimpMul)";
        scriptMap.put("SimpMul",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"Simplify\",*);\n\n10 # Simplify(expr_List?) <-- MapSingle(\"Simplify\",expr);\n\n15 # Simplify(Complex(_r,_i)) <-- Complex(Simplify(r),Simplify(i));\n\n20 # Simplify((_xex) =? (_yex)) <-- (Simplify(xex-yex) =? 0);\n20 # Simplify((_xex) >? (_yex)) <-- (Simplify(xex-yex) >? 0);\n20 # Simplify((_xex) <? (_yex)) <-- (Simplify(xex-yex) <? 0);\n20 # Simplify((_xex) >=? (_yex)) <-- (Simplify(xex-yex) >=? 0);\n20 # Simplify((_xex) <=? (_yex)) <-- (Simplify(xex-yex) <=? 0);\n20 # Simplify((_xex) !=? (_yex)) <-- (Simplify(xex-yex) !=? 0);\n\n// conditionals\n25 # Simplify(If(_a) _b) <-- \"If\" @ [Simplify(a), Simplify(b)];\n25 # Simplify(_a Else _b) <-- \"Else\" @ [Simplify(a), Simplify(b)];\n\n// otherwise\n40 # Simplify(_expr)::(Type(expr)=?\"Ln\") <--\n{\n    //Decide(InVerboseMode(),Tell(\"Simplify_Ln\",expr));\n    LnCombine(expr);\n};\n\n40 # Simplify(_expr)::(Type(expr)=?\"Exp\") <--\n{\n    //Decide(InVerboseMode(),Tell(\"Simplify_Exp\",expr));\n    expr;\n};\n\n50 # Simplify(_expr) <-- \n{\n    //Decide(InVerboseMode(),Tell(\"Simplify_other\",expr));\n    MultiSimp(Eval(expr));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/simplify/Simplify.mpw, (Simplify)";
        scriptMap.put("Simplify",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # UnFlatten([],_op,_identity) <-- identity;\n20 # UnFlatten(list_List?,_op,_identity) <--\n     Apply(op,[First(list),UnFlatten(Rest(list),op,identity)]);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/simplify/UnFlatten.mpw, (UnFlatten)";
        scriptMap.put("UnFlatten",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"BubbleSort\",*);\n\nFunction(\"BubbleSort\",[_list,_compare])\n{\n  Local(i,j,length,left,right);\n  \n  //Tell(\"BubbleSort\");\n  //Tell(\"  \",list);\n  //Tell(\"  \",compare);\n\n  list:=FlatCopy(list);\n  length:=Length(list);\n  //Tell(\"  \",length);\n\n  For (j:=length,j>?1,j--)\n  {\n    For(i:=1,i<?j,i++)\n    {\n      left:=list[i];\n      right:=list[i+1];\n      //Tell(\"    \",[i,j,left,right]);\n      //Tell(\"      \",Apply(compare,[left,right]));\n      Decide(Not?(Apply(compare,[left,right])),\n        {\n          DestructiveInsert(DestructiveDelete(list,i),i+1,left);\n        }\n      );\n      //Tell(\"   \",list);\n    };\n  };\n  list;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/sorting/BubbleSort.mpw, (BubbleSort)";
        scriptMap.put("BubbleSort",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"BubbleSortIndexed\",*);\n\nBubbleSortIndexed( L_List?, _compare ) <--\n{\n  Local(list,indices,pairs,sortedPairs);\n\n  list    := FlatCopy(L);\n  indices := BuildList(i,i,1,Length(list),1);\n  pairs   := Transpose(list~[indices]);\n  sortedPairs := Decide( Apply(compare,[3,5]), \n     BubbleSort( pairs, Lambda([X,Y],X[1] <? Y[1]) ),\n     BubbleSort( pairs, Lambda([X,Y],X[1] >? Y[1]) )\n  );\n  Transpose(sortedPairs);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/sorting/BubbleSortIndexed.mpw, (BubbleSortIndexed)";
        scriptMap.put("BubbleSortIndexed",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"HeapSort\",*);\n//Retract(\"HeapSort1\",*);\n\n\n//*------ Because HeapSort is a Recursive function, and acts on its \n//*       original data, we have created a two-stage proces, so that the\n//*       input list is not changed.\n\nHeapSort(L_List?, _compare) <-- \n{\n   Local(list);\n   list := FlatCopy(L);\n   HeapSort1(list, ArrayCreate(Length(list), 0), 1, Length(list), compare);\n};\n\n// this will sort \"list\" and mangle \"tmplist\"\n1 # HeapSort1(_L, _tmplist, _first, _last, _compare):: (last - first <=? 2) <-- SmallSort(L, first, last, compare);\n\n2 # HeapSort1(_L, _tmplist, _first, _last, _compare) <--\n{       // See: J. W. J. Williams, Algorithm 232 (Heapsort), Com. of ACM, vol. 7, no. 6, p. 347 (1964)\n        // sort two halves recursively, then merge two halves\n        // cannot merge in-place efficiently, so need a second list\n        Local(mid, ileft, iright, pleft);\n        //list := FlatCopy(L);\n        mid  := first+((last-first)>>1);\n        HeapSort1(L, tmplist, first, mid, compare);\n        HeapSort1(L, tmplist, mid+1, last, compare);\n        // copy the lower part to temporary array\n        For(ileft := first,  ileft <=? mid, ileft++)\n                tmplist[ileft] := L[ileft];\n        For(\n                {ileft := first; pleft := first; iright := mid+1;},\n                ileft <=? mid,        // if the left half is finished, we don't have to do any more work\n                pleft++        // one element is stored at each iteration\n        )        // merge two halves\n                // elements before pleft have been stored\n                // the smallest element of the right half is at iright\n                // the smallest element of the left half is at ileft, access through tmplist\n        Decide(        // we copy an element from ileft either if it is smaller or if the right half is finished; it is unnecessary to copy the remainder of the right half since the right half stays in the \"list\"\n                iright>?last Or? Apply(compare,[tmplist[ileft],L[iright]]),\n                {        // take element from ileft\n                        L[pleft] := tmplist[ileft];\n                        ileft++;\n                },\n                {        // take element from iright\n                        L[pleft] := L[iright];\n                        iright++;\n                }\n        );\n        L;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/sorting/HeapSort.mpw, (HeapSort)";
        scriptMap.put("HeapSort",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"HeapSortIndexed\",*);\n//Retract(\"HeapSortIndexed1\",*);\n\nHeapSortIndexed( L_List?, _compare ) <-- HeapSortIndexed1( FlatCopy(L), compare );\n\nHeapSortIndexed1( L_List?, _compare ) <--\n{\n  Local(list,indices,pairs,sortedPairs);\n\n  list    := FlatCopy(L);\n  indices := BuildList(i,i,1,Length(list),1);\n  pairs   := Transpose(list~[indices]);\n  SortedPairs := [];\n  sortedPairs := Decide( Apply(compare,[3,5]), \n     HeapSort( pairs, Lambda([X,Y],X[1] <? Y[1]) ),\n     HeapSort( pairs, Lambda([X,Y],X[1] >? Y[1]) )\n  );\n  Transpose(sortedPairs);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/sorting/HeapSortIndexed.mpw, (HeapSortIndexed)";
        scriptMap.put("HeapSortIndexed",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// This is a fast in-place sorting of a short list (or array!)\n/// It is used to speed up the final steps of a HeapSort.\n/// SmallSort sorts up to 3 elements, HeapSort sorts 4 and more elements\n\nSmallSort(_list, _first, _last, _compare):: (last=?first) <-- list;\n\nSmallSort(_list, _first, _last, _compare):: (last=?first+1) <--\n{\n        Local(temp);\n        temp := list[first];\n        Decide(\n                Apply(compare,[temp,list[last]]),\n                list,\n                {\n                        list[first] := list[last];\n                        list[last] := temp;\n                }        //Swap(list, first, last)\n        );\n        list;\n};\n\nSmallSort(_list, _first, _last, _compare):: (last=?first+2) <--\n{\n        Local(temp);\n        temp := list[first];\n        Decide(\n                Apply(compare,[list[first+1],temp]),\n                {\n                        list[first] := list[first+1];\n                        list[first+1] := temp;\n                }        //Swap(list, first, first+1)        // x>y, z\n        );\n        // x<y, z\n        temp := list[last];\n        Decide(\n                Apply(compare,[list[first],temp]),\n                Decide(        // z>x<y\n                        Apply(compare,[list[first+1],temp]),\n                        list,\n                        {\n                                list[last] := list[first+1];\n                                list[first+1] := temp;\n                        }        //Swap(list, first+1, last)        // 1, 3, 2\n                ),\n                {        // 2, 3, 1 -> 1, 2, 3\n                        list[last] := list[first+1];\n                        list[first+1] := list[first];\n                        list[first] := temp;\n                }\n        );\n        list;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/sorting/SmallSort.mpw, (SmallSort)";
        scriptMap.put("SmallSort",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"SmallSortIndexed\",*);\n\n/// This is a fast in-place sorting of a short list (or array!)\n/// It is used to speed up the final steps of a HeapSort.\n/// SmallSort sorts up to 3 elements, HeapSort sorts 4 and more elements\n\nSmallSortIndexed(_list, _first, _last, _compare)::(last=?first) <-- list;\n\nSmallSortIndexed(_list, _first, _last, _compare)::(last=?first+1) <--\n{\n        Local(temp);\n        temp := list[first];\n        Decide(\n                Apply(compare,[temp[1],list[last][1]]),\n                list,\n                {\n                        list[first] := list[last];\n                        list[last] := temp;\n                }        //Swap(list, first, last)\n        );\n        list;\n};\n\nSmallSortIndexed(_list, _first, _last, _compare)::(last=?first+2) <--\n{\n        Local(temp);\n        temp := list[first];\n        Decide(\n                Apply(compare,[list[first+1][1],temp[1]]),\n                {\n                        list[first] := list[first+1];\n                        list[first+1] := temp;\n                }        //Swap(list, first, first+1)        // x>y, z\n        );\n        // x<y, z\n        temp := list[last];\n        Decide(\n                Apply(compare,[list[first][1],temp[1]]),\n                Decide(        // z>x<y\n                        Apply(compare,[list[first+1][1],temp[1]]),\n                        list,\n                        {\n                                list[last] := list[first+1];\n                                list[first+1] := temp;\n                        }        //Swap(list, first+1, last)        // 1, 3, 2\n                ),\n                {        // 2, 3, 1 -> 1, 2, 3\n                        list[last] := list[first+1];\n                        list[first+1] := list[first];\n                        list[first] := temp;\n                }\n        );\n        list;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/sorting/SmallSortIndexed.mpw, (SmallSortIndexed)";
        scriptMap.put("SmallSortIndexed",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"Sort\",*);\n//Retract(\"SortIndexed\",*);\n\n/*-------------------------------------------------------------------\n *  Sort()\n *  SortIndexed()\n *    Gathering all sorts of Sorts under one roof.\n *    Currently:  Bubble Sort and Heap Sort, optional: indexing\n *      Set up so Sort() chooses BubbleSort if Length(List) < 20,\n *                           and HeapSort   if Length(List) >= 20\n *\n *  Indexed sorts return [SortedList,IndexList].\n *  First version.   111124\n *-------------------------------------------------------------------*/\n \n10 # Sort( L_List? )::(Length(L) <? 20) <-- BubbleSort( L, \"<?\" );\n\n15 # Sort( L_List?, _compare )::(Length(L) <? 20) <-- \n                                                    BubbleSort( L, compare );\n\n20 # Sort( L_List? ) <-- HeapSort( L, \"<?\" );\n\n25 # Sort( L_List?, _compare ) <--   HeapSort( L, compare );\n \n10 # SortIndexed( L_List? )::(Length(L) <? 20) <-- BubbleSortIndexed( L, \"<?\" );\n\n15 # SortIndexed( L_List?, _compare )::(Length(L) <? 20) <-- BubbleSortIndexed( L, compare );\n\n20 # SortIndexed( L_List? ) <-- HeapSortIndexed( L, \"<?\" );\n\n25 # SortIndexed( L_List?, _compare ) <-- HeapSortIndexed( L, compare );\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/sorting/Sort.mpw, (Sort;SortIndexed)";
        scriptMap.put("Sort",scriptString);
        scriptMap.put("SortIndexed",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nGeometricMean(list) := \n{\n    Check(List?(list), \"Argument\", \"Argument must be a list.\");\n    \n    Product(list)^(1/Length(list));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/GeometricMean.mpw, (GeometricMean)";
        scriptMap.put("GeometricMean",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nMean(list) := \n{\n    Check(List?(list), \"Argument\", \"Argument must be a list.\");\n    \n    Sum(list)/Length(list);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/Mean.mpw, (Mean)";
        scriptMap.put("Mean",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nMedian(list) :=\n{\n    Check(List?(list), \"Argument\", \"Argument must be a list.\");\n    \n    Local(sx,n,n2); // s[orted]x\n    \n    sx := HeapSort(list,\"<?\");\n    \n    n := Length(list);\n    \n    n2 := (n>>1);\n    \n    Decide(Modulo(n,2) =? 1, sx[n2+1], (sx[n2]+sx[n2+1])/2);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/Median.mpw, (Median)";
        scriptMap.put("Median",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nStandardDeviation(list) := \n{\n    Check(List?(list), \"Argument\", \"Argument must be a list.\");\n\n    Sqrt(UnbiasedVariance(list));\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/StandardDeviation.mpw, (StandardDeviation)";
        scriptMap.put("StandardDeviation",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nUnbiasedVariance(list) := \n{\n    Check(List?(list), \"Argument\", \"Argument must be a list.\");\n    \n    Sum((list - Mean(list))^2)/(Length(list)-1);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/UnbiasedVariance.mpw, (UnbiasedVariance)";
        scriptMap.put("UnbiasedVariance",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nVariance(list) := \n{\n    Check(List?(list), \"Argument\", \"Argument must be a list.\");\n\n    Sum((list - Mean(list))^2)/Length(list);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/Variance.mpw, (Variance)";
        scriptMap.put("Variance",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Guard against distribution objects with senseless parameters\n   Anti-nominalism */\n\nBernoulliDistribution(p_RationalOrNumber?)::(p<?0 Or? p>?1) <-- Undefined;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/distributions/BernoulliDistribution.mpw, (BernoulliDistribution)";
        scriptMap.put("BernoulliDistribution",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Guard against distribution objects with senseless parameters\n   Anti-nominalism */\n\nBinomialDistribution(_p, _n)::\n        (Decide(RationalOrNumber?(p),p<?0 Or? p>?1, False)\n         Or? (Constant?(n) And? Not? PositiveInteger?(n)) )\n        <-- Undefined;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/distributions/BinomialDistribution.mpw, (BinomialDistribution)";
        scriptMap.put("BinomialDistribution",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Guard against distribution objects with senseless parameters\n   Anti-nominalism */\n\nChiSquareDistribution(m_RationalOrNumber?)::(m<=?0) <-- Undefined;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/distributions/ChiSquareDistribution.mpw, (ChiSquareDistribution)";
        scriptMap.put("ChiSquareDistribution",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Guard against distribution objects with senseless parameters\n   Anti-nominalism */\n\nContinuousUniformDistribution(a_RationalOrNumber?, b_RationalOrNumber?)::(a>=?b)\n   <-- Undefined;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/distributions/ContinuousUniformDistribution.mpw, (ContinuousUniformDistribution)";
        scriptMap.put("ContinuousUniformDistribution",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Guard against distribution objects with senseless parameters\n   Anti-nominalism */\n\nDiscreteDistribution( dom_RationalOrNumber? , prob_RationalOrNumber?) <-- Undefined;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/distributions/DiscreteDistribution.mpw, (DiscreteDistribution)";
        scriptMap.put("DiscreteDistribution",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Guard against distribution objects with senseless parameters\n   Anti-nominalism */\n\nDiscreteUniformDistribution(a_RationalOrNumber?, b_RationalOrNumber?)::(a>=?b)\n   <-- Undefined;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/distributions/DiscreteUniformDistribution.mpw, (DiscreteUniformDistribution)";
        scriptMap.put("DiscreteUniformDistribution",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Guard against distribution objects with senseless parameters\n   Anti-nominalism */\n\nExponentialDistribution(l_RationalOrNumber?)::(l<?0) <-- Undefined;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/distributions/ExponentialDistribution.mpw, (ExponentialDistribution)";
        scriptMap.put("ExponentialDistribution",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Guard against distribution objects with senseless parameters\n   Anti-nominalism */\n\n//Retract(\"FDistribution\",*);\nFDistribution(_n1, _n2) <-- Undefined;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/distributions/FDistribution.mpw, (FDistribution)";
        scriptMap.put("FDistribution",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Guard against distribution objects with senseless parameters\n   Anti-nominalism */\n\nGeometricDistribution(p_RationalOrNumber?)::(p<?0 Or? p>?1) <-- Undefined;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/distributions/GeometricDistribution.mpw, (GeometricDistribution)";
        scriptMap.put("GeometricDistribution",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Guard against distribution objects with senseless parameters\n   Anti-nominalism */\n\nHypergeometricDistribution(N_RationalOrNumber?, M_RationalOrNumber?, n_RationalOrNumber?)::(M >? N Or? n >? N)\n   <-- Undefined;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/distributions/HypergeometricDistribution.mpw, (HypergeometricDistribution)";
        scriptMap.put("HypergeometricDistribution",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Guard against distribution objects with senseless parameters\n   Anti-nominalism */\n\nNormalDistribution( _m , s2_RationalOrNumber?)::(s2<=?0) <-- Undefined;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/distributions/NormalDistribution.mpw, (NormalDistribution)";
        scriptMap.put("NormalDistribution",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Guard against distribution objects with senseless parameters\n   Anti-nominalism */\n\nPoissonDistribution(l_RationalOrNumber?)::(l<=?0) <-- Undefined;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/distributions/PoissonDistribution.mpw, (PoissonDistribution)";
        scriptMap.put("PoissonDistribution",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Guard against distribution objects with senseless parameters\n   Anti-nominalism */\n\ntDistribution(m_RationalOrNumber?)::(Not? PositiveInteger?(m)) <-- Undefined;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/distributions/tDistribution.mpw, (tDistribution)";
        scriptMap.put("tDistribution",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\n/* ChiSquare's CDF is computed as IncompleteGamma(x,dof/2)/Gamma(dof/2); */\n\nRetract(\"ChiSquareTest\",*);\n\n\n10 # ChiSquareTest( observedFrequenciesMatrix_Matrix?, expectedFrequenciesMatrix_Matrix?) <--\n{\n\n    Local(observedFrequenciesList, expectedFrequenciesList);\n    \n    observedFrequenciesList := Flatten(observedFrequenciesMatrix,\"List\");\n    \n    expectedFrequenciesList := Flatten(expectedFrequenciesMatrix,\"List\");\n    \n    Check(Length(observedFrequenciesList) >? 0, \"Argument\", \"The first argument must be a nonempty matrix.\");\n    \n    Check(Length(expectedFrequenciesList) >? 0, \"Argument\", \"The second argument must be a nonempty matrix.\");\n    \n    Check(Length(expectedFrequenciesList) =? Length(expectedFrequenciesList), \"Argument\", \"The matrices must be of equal length.\");\n\n    Local( numerator, chi2, pValue, categoriesCount, degreesOfFreedom, resultList);\n   \n    resultList := [];\n\n    categoriesCount := Length(observedFrequenciesList);\n\n    numerator := (observedFrequenciesList - expectedFrequenciesList)^2; //threading\n\n    chi2 := Sum(i,1,categoriesCount,numerator[i]/(expectedFrequenciesList[i]));\n\n    degreesOfFreedom := (Dimensions(observedFrequenciesMatrix)[1] - 1)*(Dimensions(observedFrequenciesMatrix)[2] - 1);\n\n    pValue := 1-NM(IncompleteGamma(degreesOfFreedom/2,chi2/2)/Gamma(degreesOfFreedom/2));\n\n    resultList[\"degreesOfFreedom\"] := degreesOfFreedom;\n    \n    resultList[\"pValue\"] := pValue;\n   \n    resultList[\"chiSquareScore\"] := chi2;\n    \n    NM(resultList);\n};\n\n\n\n\n20 # ChiSquareTest( observedFrequenciesList_List?, expectedFrequenciesList_List?) <--\n{\n    Check(Length(observedFrequenciesList) >? 0, \"Argument\", \"The first argument must be a nonempty list.\");\n    \n    Check(Length(expectedFrequenciesList) >? 0, \"Argument\", \"The second argument must be a nonempty list.\");\n    \n    Check(Length(expectedFrequenciesList) =? Length(expectedFrequenciesList), \"Argument\", \"The lists must be of equal length.\");\n\n    Local( numerator, chi2, pValue, categoriesCount, degreesOfFreedom, resultList);\n   \n    resultList := [];\n\n    categoriesCount := Length(observedFrequenciesList);\n\n    numerator := (observedFrequenciesList - expectedFrequenciesList)^2; //threading\n\n    chi2 := Sum(i,1,categoriesCount,numerator[i]/(expectedFrequenciesList[i]));\n\n    degreesOfFreedom := categoriesCount - 1;\n\n    pValue := 1-NM(IncompleteGamma(degreesOfFreedom/2,chi2/2)/Gamma(degreesOfFreedom/2));\n\n    resultList[\"degreesOfFreedom\"] := degreesOfFreedom;\n    \n    resultList[\"pValue\"] := pValue;\n   \n    resultList[\"chiSquareScore\"] := chi2;\n    \n    NM(resultList);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/hypothesystest/ChiSquareTest.mpw, (ChiSquareTest)";
        scriptMap.put("ChiSquareTest",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\n/* Finds the rengression of y onto x, that is\n   y == alpha + beta * x\n\n   Example usage:\n   x := 1 .. 5;\n   y := (2.34*x+2) +(2*Random()-1);\n   ans :=Regress(x,y);\n\n   To find the residuals, we do\n   (y-alpha-beta*x) /: ans\n */\n\n\nRegress(x,y) :=\n{\n Local(xy,x2,i,mx,my);\n\n mx := Mean(x);\n my := Mean(y);\n xy := Add((x-mx)*(y-my));\n x2 := Add((x-mx)^2);\n [alpha <- (my-xy*mx/x2) , beta <-  xy/x2];\n};\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/statistics/regression/Regress.mpw, (Regress)";
        scriptMap.put("Regress",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n2 # ArcCos(x_Number?)::NumericMode?() <-- InternalPi()/2-ArcSin(x);\n\n /* TODO check! */\n200 # ArcCos(0) <-- Pi/2;\n200 # ArcCos(1/2) <-- Pi/3;\n200 # ArcCos(Sqrt(1/2)) <-- Pi/4;\n200 # ArcCos(Sqrt(3/4)) <-- Pi/6;\n200 # ArcCos(1) <-- 0;\n200 # ArcCos(_n)::(n =? -1) <-- Pi;\n200 # ArcCos(_n)::(-n =? Sqrt(3/4)) <-- 5/6*Pi;\n200 # ArcCos(_n)::(-n =? Sqrt(1/2)) <-- 3/4*Pi;\n200 # ArcCos(_n)::(-n =? 1/2) <-- 2/3*Pi;\n\n200 # ArcCos(Undefined) <-- Undefined;\n\nArcCos(xlist_List?) <-- MapSingle(\"ArcCos\",xlist);\n\n110 # ArcCos(Complex(_r,_i)) <--\n    (- I)*Ln(Complex(r,i) + (Complex(r,i)^2 - 1)^(1/2));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/ArcCos.mpw, (ArcCos)";
        scriptMap.put("ArcCos",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # ArcCosh(_x)::(NumericMode?() And? (Number?(x) Or? Type(x) =? \"Complex\")) <-- NM(Eval( Ln(x+Sqrt(x^2-1)) ));\n\n200 # ArcCosh(Infinity) <-- Infinity;\n200 # ArcCosh(-Infinity) <-- Infinity+I*Pi/2;\n200 # ArcCosh(Undefined) <-- Undefined;\n\nArcCosh(xlist_List?) <-- MapSingle(\"ArcCosh\",xlist);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/ArcCosh.mpw, (ArcCosh)";
        scriptMap.put("ArcCosh",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n2 # ArcSin(x_Number?)::(NumericMode?() And? Abs(x)<=?1) <-- ArcSinNum(x);\n/// complex ArcSin\n3 # ArcSin(x_Number?)::NumericMode?() <-- Sign(x)*(Pi/2+I*ArcCosh(x));\n\n110 # ArcSin(Complex(_r,_i)) <--\n    (- I) * Ln((I*Complex(r,i)) + ((1-(Complex(r,i)^2))^(1/2)));\n\n150 # ArcSin(- _x)::(Not? Constant?(x))             <-- -ArcSin(x);\n160 # (ArcSin(x_Constant?))::(NegativeNumber?(NM(Eval(x)))) <-- -ArcSin(-x);\n\n200 # ArcSin(0) <-- 0;\n200 # ArcSin(1/2) <-- Pi/6;\n200 # ArcSin(Sqrt(1/2)) <-- Pi/4;\n200 # ArcSin(Sqrt(3/4)) <-- Pi/3;\n200 # ArcSin(1) <-- Pi/2;\n200 # ArcSin(_n)::(n =? -1) <-- -Pi/2;\n200 # ArcSin(_n)::(-n =? Sqrt(3/4)) <-- -Pi/3;\n200 # ArcSin(_n)::(-n =? Sqrt(1/2)) <-- -Pi/4;\n200 # ArcSin(_n)::(-n =? 1/2) <-- -Pi/6;\n\nArcSin(xlist_List?) <-- MapSingle(\"ArcSin\",xlist);\n\n200 # ArcSin(Undefined) <-- Undefined;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/ArcSin.mpw, (ArcSin)";
        scriptMap.put("ArcSin",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # ArcSinh(_x)::(NumericMode?() And? (Number?(x) Or? Type(x) =? \"Complex\")) <-- NM(Eval( Ln(x+Sqrt(x^2+1)) ));\n\n200 # ArcSinh(Infinity) <-- Infinity;\n200 # ArcSinh(-Infinity) <-- -Infinity;\n200 # ArcSinh(Undefined) <-- Undefined;\n\nArcSinh(xlist_List?) <-- MapSingle(\"ArcSinh\",xlist);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/ArcSinh.mpw, (ArcSinh)";
        scriptMap.put("ArcSinh",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n5 # (ArcTan(x_Constant?))::(NegativeNumber?(NM(Eval(x))))   <-- -ArcTan(-x);\n\n//TODO fix! 4 # ArcTan(Tan(_x))           <-- x;\n4 # ArcTan(-Tan(_x))           <-- -ArcTan(Tan(x));\n110 # ArcTan(Complex(_r,_i)) <--\n     (- I*0.5)*Ln(Complex(1,Complex(r,i))/ Complex(1, - Complex(r,i)));\n\n150 # ArcTan(- _x)::(Not? Constant?(x))             <-- -ArcTan(x);\n160 # (ArcTan(x_Constant?))::(NegativeNumber?(NM(Eval(x)))) <-- -ArcTan(-x);\n\n200 # ArcTan(Sqrt(3)) <-- Pi/3;\n200 # ArcTan(-Sqrt(3)) <-- -Pi/3;\n200 # ArcTan(1) <-- Pi/4;\n200 # ArcTan(0) <-- 0;\n200 # ArcTan(_n)::(n =? -1) <-- -Pi/4;\n\n200 # ArcTan(Infinity) <-- Pi/2;\n200 # ArcTan(-Infinity) <-- -Pi/2;\n200 # ArcTan(Undefined) <-- Undefined;\n\nArcTan(xlist_List?) <-- MapSingle(\"ArcTan\",xlist);\n\n2 # ArcTan(x_Number?)::NumericMode?() <-- ArcTanNum(x);\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/ArcTan.mpw, (ArcTan)";
        scriptMap.put("ArcTan",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # ArcTanh(_x)::(NumericMode?() And? (Number?(x) Or? Type(x) =? \"Complex\")) <-- NM(Eval( Ln((1+x)/(1-x))/2 ));\n\n200 # ArcTanh(Infinity) <-- Infinity+I*Pi/2;\n200 # ArcTanh(-Infinity) <-- -Infinity-I*Pi/2;        // this is a little silly b/c we don't support correct branch cuts yet\n200 # ArcTanh(Undefined) <-- Undefined;\n\nArcTanh(xlist_List?) <-- MapSingle(\"ArcTanh\",xlist);\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/ArcTanh.mpw, (ArcTanh)";
        scriptMap.put("ArcTanh",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n1 # CosMap( _n )::(Not?(RationalOrNumber?(n))) <-- ListToFunction([ToAtom(\"Cos\"),n*Pi]);\n2 # CosMap( _n )::(n<?0) <-- CosMap(-n);\n2 # CosMap( _n )::(n>?2) <-- CosMap(Modulo(n,2));\n3 # CosMap( _n )::(n>?1) <-- CosMap(2-n);\n4 # CosMap( _n )::(n>?1/2) <-- -CosMap(1-n);\n\n5 # CosMap( 0 ) <-- 1;\n5 # CosMap( 1/6 ) <-- Sqrt(3)/2;\n5 # CosMap( 1/4 ) <-- Sqrt(2)/2;\n5 # CosMap( 1/3 ) <-- 1/2;\n5 # CosMap( 1/2 ) <-- 0;\n5 # CosMap( 2/5 ) <-- (Sqrt(5)-1)/4;\n\n10 # CosMap(_n) <-- ListToFunction([ToAtom(\"Cos\"),n*Pi]);\n\n\n\n2 # Cos(x_Number?)::NumericMode?() <-- CosNum(x);\n4 # Cos(ArcCos(_x))           <-- x;\n4 # Cos(ArcSin(_x)) <-- Sqrt(1-x^2);\n4 # Cos(ArcTan(_x)) <-- 1/Sqrt(1+x^2);\n5 # Cos(- _x)::(Not? Constant?(x))                 <-- Cos(x);\n6 # (Cos(x_Constant?))::(NegativeNumber?(NM(Eval(x))))   <-- Cos(-x);\n// must prevent it from looping\n\n110 # Cos(Complex(_r,_i)) <--\n    (Exp(I*Complex(r,i)) + Exp(- I*Complex(r,i))) / (2) ;\n\n6 # Cos(x_Infinity?) <-- Undefined;\n6 # Cos(Undefined) <-- Undefined;\n\n200 # Cos(v_CanBeUni(Pi))::(Not?(NumericMode?()) And? Degree(v,Pi) <? 2 And? Coef(v,Pi,0) =? 0) <--\n      CosMap(Coef(v,Pi,1));\n\n400 # Cos(x_RationalOrNumber?) <--\n    {\n     Local(ll);\n     ll:= FloorN(NM(Eval(x/Pi)));\n     Decide(Even?(ll),x:=(x - Pi*ll),x:=(-x + Pi*(ll+1)));\n     ListToFunction(['Cos,x]);\n     };\n\n400 # Cos(x_RationalOrNumber?) <--\n    {\n     Local(ll);\n     ll:= FloorN(NM(Eval(Abs(x)/Pi)));\n     Decide(Even?(ll),x:=(Abs(x) - Pi*ll),x:=(-Abs(x) + Pi*(ll+1)));\n     ListToFunction(['Cos,x]);\n     };\n\n\n\n\nCos(xlist_List?) <-- MapSingle(\"Cos\",xlist);\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/Cos.mpw, (Cos;CosMap)";
        scriptMap.put("Cos",scriptString);
        scriptMap.put("CosMap",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n5   # Cosh(- _x)        <-- Cosh(x);\n\n// this is never activated\n\n//100 # Cosh(I*_x)        <-- Cos(x);\n\n\n200 # Cosh(0)                <-- 1;\n200 # Cosh(Infinity)        <-- Infinity;\n200 # Cosh(-Infinity)        <-- Infinity;\n200 # Cosh(ArcCosh(_x)) <-- x;\n200 # Cosh(ArcSinh(_x)) <-- Sqrt(1+x^2);\n200 # Cosh(ArcTanh(_x)) <-- 1/Sqrt(1-x^2);\n\n200 # Cosh(Undefined) <-- Undefined;\n\nCosh(xlist_List?) <-- MapSingle(\"Cosh\",xlist);\n\n2 # Cosh(_x)::(NumericMode?() And? (Number?(x) Or? Type(x) =? \"Complex\")) <-- NM(Eval( (Exp(x)+Exp(-x))/2 ));\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/Cosh.mpw, (Cosh)";
        scriptMap.put("Cosh",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n100 # Cot(_x) <-- 1/Tan(x);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/Cot.mpw, (Cot)";
        scriptMap.put("Cot",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n100 # Coth(_x)                <-- 1/Tanh(x);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/Coth.mpw, (Coth)";
        scriptMap.put("Coth",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n100 # Csc(_x)                <-- 1/Sin(x);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/Csc.mpw, (Csc)";
        scriptMap.put("Csc",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n100 # Csch(_x)                <-- 1/Sinh(x);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/Csch.mpw, (Csch)";
        scriptMap.put("Csch",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n2 # Exp(x_Number?)::NumericMode?() <-- ExpNum(x);\n4 # Exp(Ln(_x))           <-- x;\n110 # Exp(Complex(_r,_i)) <--  Exp(r)*(Cos(i) + I*Sin(i));\n200 # Exp(0) <-- 1;\n200 # Exp(-Infinity) <-- 0;\n200 # Exp(Infinity) <-- Infinity;\n200 # Exp(Undefined) <-- Undefined;\n\nExp(xlist_List?) <-- MapSingle(\"Exp\",xlist);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/Exp.mpw, (Exp)";
        scriptMap.put("Exp",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n2 # Ln(0)                    <-- -Infinity;\n2 # Ln(1)                    <-- 0;\n2 # Ln(Infinity)                    <-- Infinity;\n2 # Ln(Undefined)                   <-- Undefined;\n\n/* 2 # Ln(-Infinity)                    <-- 0; */\n2 # Ln(x_NegativeNumber?)::NumericMode?() <-- Complex(Ln(-x), Pi);\n3 # Ln(x_Number?)::(NumericMode?() And? x>=?1) <-- InternalLnNum(x);\n4 # Ln(Exp(_x))              <-- x;\n\n3 # Ln(Complex(_r,_i)) <-- Complex(Ln(Abs(Complex(r,i))), Arg(Complex(r,i)));\n4 # Ln(x_NegativeNumber?) <-- Complex(Ln(-x), Pi);\n5 # Ln(x_Number?)::(NumericMode?() And? x<?1) <-- - InternalLnNum(DivideN(1, x));\n\nLn(xlist_List?) <-- MapSingle(\"Ln\",xlist);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/Ln.mpw, (Ln)";
        scriptMap.put("Ln",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n100 # Sec(_x)                <-- 1/Cos(x);\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/Sec.mpw, (Sec)";
        scriptMap.put("Sec",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n100 # Sech(_x) <-- 1/Cosh(x);\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/Sech.mpw, (Sech)";
        scriptMap.put("Sech",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\n1 # SinMap( _n )::(Not?(RationalOrNumber?(n))) <-- ListToFunction([ToAtom(\"Sin\"),n*Pi]);\n2 # SinMap( _n )::(n<?0) <-- -SinMap(-n);\n2 # SinMap( _n )::(n>?2) <-- SinMap(Modulo(n,2));\n3 # SinMap( _n )::(n>?1) <-- SinMap(n-2);\n4 # SinMap( _n )::(n>?1/2) <-- SinMap(1-n);\n\n5 # SinMap( n_Integer? ) <-- 0;\n5 # SinMap( 1/6 ) <-- 1/2;\n5 # SinMap( 1/4 ) <-- Sqrt(2)/2;\n5 # SinMap( 1/3 ) <-- Sqrt(3)/2;\n5 # SinMap( 1/2 ) <-- 1;\n5 # SinMap( 1/10) <-- (Sqrt(5)-1)/4;\n\n10 # SinMap(_n) <-- ListToFunction([ToAtom(\"Sin\"),n*Pi]);\n\n\n\n\n2 # Sin(x_Number?)::NumericMode?() <-- SinNum(x);\n4 # Sin(ArcSin(_x))           <-- x;\n4 # Sin(ArcCos(_x)) <-- Sqrt(1-x^2);\n4 # Sin(ArcTan(_x)) <-- x/Sqrt(1+x^2);\n5 # Sin(- _x)::(Not? Constant?(x))                 <-- -Sin(x);\n6 # (Sin(x_Constant?))::(NegativeNumber?(NM(Eval(x))))   <-- -Sin(-x);\n\n// must prevent it from looping\n6 # Sin(x_Infinity?)                 <-- Undefined;\n6 # Sin(Undefined) <-- Undefined;\n\n110 # Sin(Complex(_r,_i)) <--\n    (Exp(I*Complex(r,i)) - Exp(- I*Complex(r,i))) / (I*2) ;\n\n200 # Sin(v_CanBeUni(Pi))::(Not?(NumericMode?()) And? Degree(v,Pi) <? 2 And? Coef(v,Pi,0) =? 0) <--\n{\n  SinMap(Coef(v,Pi,1));\n};\n\n\nSin(xlist_List?) <-- MapSingle(\"Sin\",xlist);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/Sin.mpw, (Sin;SinMap)";
        scriptMap.put("Sin",scriptString);
        scriptMap.put("SinMap",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n2 # Sinh(_x)::(NumericMode?() And? (Number?(x) Or? Type(x) =? \"Complex\")) <-- NM(Eval( (Exp(x)-Exp(-x))/2 ));\n5   # Sinh(- _x)        <-- -Sinh(x);\n\n5   # Sinh(- _x)        <-- -Sinh(x);\n\n\n//100 # Sinh(I*_x)        <-- I*Sin(x);\n\n\n200 # Sinh(0)                <-- 0;\n200 # Sinh(Infinity)        <-- Infinity;\n200 # Sinh(-Infinity)        <-- -Infinity;\n200 # Sinh(ArcSinh(_x)) <-- x;\n200 # Sinh(ArcCosh(_x)) <-- Sqrt((x-1)/(x+1))*(x+1);\n200 # Sinh(ArcTanh(_x)) <-- x/Sqrt(1-x^2);\n\n200 # Sinh(Undefined) <-- Undefined;\n\n/* Threading of standard analytic functions */\nSinh(xlist_List?) <-- MapSingle(\"Sinh\",xlist);\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/Sinh.mpw, (Sinh)";
        scriptMap.put("Sinh",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n1 # TanMap( _n )::(Not?(RationalOrNumber?(n))) <-- ListToFunction([ToAtom(\"Tan\"),n*Pi]);\n2 # TanMap( _n )::(n<?0) <-- -TanMap(-n);\n2 # TanMap( _n )::(n>?1) <-- TanMap(Modulo(n,1));\n4 # TanMap( _n )::(n>?1/2) <-- -TanMap(1-n);\n\n5 # TanMap( 0 ) <-- 0;\n5 # TanMap( 1/6 ) <-- 1/3*Sqrt(3);\n5 # TanMap( 1/4 ) <-- 1;\n5 # TanMap( 1/3 ) <-- Sqrt(3);\n5 # TanMap( 1/2 ) <-- Infinity;\n\n10 # TanMap(_n) <-- ListToFunction([ToAtom(\"Tan\"),n*Pi]);\n\n\n\n\n2 # Tan(x_Number?)::NumericMode?() <-- TanNum(x);\n4 # Tan(ArcTan(_x))           <-- x;\n4 # Tan(ArcSin(_x)) <-- x/Sqrt(1-x^2);\n4 # Tan(ArcCos(_x)) <-- Sqrt(1-x^2)/x;\n5 # Tan(- _x)::(Not? Constant?(x))                  <-- -Tan(x);\n6 # (Tan(x_Constant?))::(NegativeNumber?(NM(Eval(x))))   <-- -Tan(-x);\n\n// must prevent it from looping\n6 # Tan(Infinity) <-- Undefined;\n6 # Tan(Undefined) <-- Undefined;\n\n110 # Tan(Complex(_r,_i)) <-- Sin(Complex(r,i))/Cos(Complex(r,i));\n\n200 # Tan(v_CanBeUni(Pi))::(Not?(NumericMode?()) And? Degree(v,Pi) <? 2 And? Coef(v,Pi,0) =? 0) <--\n      TanMap(Coef(v,Pi,1));\n\n\nTan(xlist_List?) <-- MapSingle(\"Tan\",xlist);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/Tan.mpw, (Tan;TanMap)";
        scriptMap.put("Tan",scriptString);
        scriptMap.put("TanMap",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n2 # Tanh(_x)::(NumericMode?() And? (Number?(x) Or? Type(x) =? \"Complex\")) <-- NM(Eval( Sinh(x)/Cosh(x) ));\n\n200 # Tanh(0)           <-- 0;\n200 # Tanh(Infinity)    <-- 1;\n200 # Tanh(-Infinity)   <-- -1;\n200 # Tanh(ArcTanh(_x)) <-- x;\n200 # Tanh(ArcSinh(_x)) <-- x/Sqrt(1+x^2);\n200 # Tanh(ArcCosh(_x)) <-- Sqrt((x-1)/(x+1))*(x+1)/x;\n\n200 # Tanh(Undefined) <-- Undefined;\n\n/* Threading of standard analytic functions */\nTanh(xlist_List?) <-- MapSingle(\"Tanh\",xlist);\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/Tanh.mpw, (Tanh)";
        scriptMap.put("Tanh",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nArcCosN(x) :=\n{\n    FastArcCos(x);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/elemfuncs/ArcCosN.mpw, (ArcCosN)";
        scriptMap.put("ArcCosN",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n\nDefun(\"ArcSinN\",[_int1])\n{\n  Local(result,eps);\n        Assign(result,FastArcSin(int1));\n  Local(x,q,s,c);\n  Assign(q,SubtractN(SinN(result),int1));\n  Assign(eps,MathIntPower(10,MathNegate(BuiltinPrecisionGet())));\n  While(GreaterThan?(AbsN(q),eps))\n  {\n        Assign(s,SubtractN(int1,SinN(result)));\n    Assign(c,CosN(result));\n    Assign(q,DivideN(s,c));\n    Assign(result,AddN(result,q));\n  };\n  result;\n};\n\n/*\nArcSinN(x) :=\n[\n    FastArcSin(x);\n];*/\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/elemfuncs/ArcSinN.mpw, (ArcSinN)";
        scriptMap.put("ArcSinN",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nArcTanN(x) :=\n{\n    FastArcTan(x);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/elemfuncs/ArcTanN.mpw, (ArcTanN)";
        scriptMap.put("ArcTanN",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/** This file contains routines for numerical evaluation of elementary functions:\n *  PowerN, ExpN, SinN etc.\n *  It is assumed that the arguments are real (not complex) floating-point or integer numbers. (The [NumericMode?()] flag does not have to be set.)\n *  The result is an exact integer or a floating-point number correct to BuiltinPrecisionGet() digits.\n *\n *  If a better optimized version of these functions is available through the kernel,\n *  then the kernel version will automatically shadow these functions.\n *  These implementations are not necessarily the best optimized versions.\n */\n\n/// ArcTan(x), Taylor series for ArcTan(x)/x, use only with -1/2<x<1/2\nArcTanNTaylor(x) :=\n{\n        Local(numterms);\n        numterms := QuotientN(BuiltinPrecisionGet()*1068, -643*MathBitCount(x))+1;\n        // (P*Ln(10))/(-2*Ln(x)); use Ln(x)<=?B(x)*Ln(2), only good for |x|<1/2\n        // here -Ln(x) must be positive\n        x*SumTaylorNum(-MultiplyN(x,x), [[k], 1/(2*k+1)], numterms);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/elemfuncs/ArcTanNTaylor.mpw, (ArcTanNTaylor)";
        scriptMap.put("ArcTanNTaylor",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/** This file contains routines for numerical evaluation of elementary functions:\n *  PowerN, ExpN, SinN etc.\n *  It is assumed that the arguments are real (not complex) floating-point or integer numbers. (The [NumericMode?()] flag does not have to be set.)\n *  The result is an exact integer or a floating-point number correct to BuiltinPrecisionGet() digits.\n *\n *  If a better optimized version of these functions is available through the kernel,\n *  then the kernel version will automatically shadow these functions.\n *  These implementations are not necessarily the best optimized versions.\n */\n\n//[BisectSqrt(N)] computes the integer part of $ Sqrt(N) $ for integer $N$.\n// BisectSqrt() works only on integers\n        //sqrt(1) = 1, sqrt(0) = 0\n10 # BisectSqrt(0) <-- 0;\n10 # BisectSqrt(1) <-- 1;\n\n20 # BisectSqrt(N_PositiveInteger?) <--\n{\n  Local(l2,u,v,u2,v2,uv2,n);\n\n  // Find highest set bit, l2\n  u  := N;\n  l2 := MathBitCount(u)-1;\n\n  // 1<<(l2/2) now would be a good under estimate\n  // for the square root. 1<<(l2/2) is definitely\n  // set in the result. Also it is the highest\n  // set bit.\n  l2 := l2>>1;\n\n  // initialize u and u2 (u2==u^2).\n  u  := 1 << l2;\n  u2 := u << l2;\n\n  // Now for each lower bit:\n  While( l2 !=? 0 )\n  {\n          l2--;\n     // Get that bit in v, and v2 == v^2.\n      v  := 1<<l2;\n      v2 := v<<l2;\n\n      // uv2 == 2*u*v, where 2==1<<1, and\n      // v==1<<l2, thus 2*u*v ==\n      // (1<<1)*u*(1<<l2) == u<<(l2+1)\n      uv2 := u<<(l2 + 1);\n\n      // n = (u+v)^2  = u^2 + 2*u*v + v^2\n      //   = u2+uv2+v2\n       n := u2 + uv2 + v2;\n\n      // if n (possible new best estimate for\n      // sqrt(N)^2 is smaller than N, then the\n      // bit l2 is set in the result, and\n      // add v to u.\n      If( n <=? N )\n      {\n        u  := u+v;  // u <- u+v\n        u2 := n;    // u^2 <- u^2 + 2*u*v + v^2\n      };\n    };\n    u; // return result, accumulated in u.\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/elemfuncs/BisectSqrt.mpw, (BisectSqrt)";
        scriptMap.put("BisectSqrt",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// BitsToDigits(n,base) and DigitsToBits(n,base). Enough to compute at low precision.\n// this is now a call to the kernel functions, so leave as a reference implementation\nBitsToDigits(n, base) := FloorN(0.51+n*NM(Ln(2)/Ln(base),10));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/elemfuncs/BitsToDigits.mpw, (BitsToDigits)";
        scriptMap.put("BitsToDigits",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/** This file contains routines for numerical evaluation of elementary functions:\n *  PowerN, ExpN, SinN etc.\n *  It is assumed that the arguments are real (not complex) floating-point or integer numbers. (The [NumericMode?()] flag does not have to be set.)\n *  The result is an exact integer or a floating-point number correct to BuiltinPrecisionGet() digits.\n *\n *  If a better optimized version of these functions is available through the kernel,\n *  then the kernel version will automatically shadow these functions.\n *  These implementations are not necessarily the best optimized versions.\n */\n\n/// Identity transformation, compute 1-Cos(x) from value=1-Cos(x/2^n)\n\n//Changed CosNDoubling1 to CosNDoubling. Note:tk.\nCosNDoubling(value, n) :=\n{\n        Local(shift, result);\n        shift := n;\n        result := value;\n        While (shift>?0)        // lose 'shift' bits of precision here\n        {\n                result := MultiplyN(MathMul2Exp(result, 1), 2 - result);\n                shift--;\n        };\n        result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/elemfuncs/CosNDoubling.mpw, (CosNDoubling)";
        scriptMap.put("CosNDoubling",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/** This file contains routines for numerical evaluation of elementary functions:\n *  PowerN, ExpN, SinN etc.\n *  It is assumed that the arguments are real (not complex) floating-point or integer numbers. (The [NumericMode?()] flag does not have to be set.)\n *  The result is an exact integer or a floating-point number correct to BuiltinPrecisionGet() digits.\n *\n *  If a better optimized version of these functions is available through the kernel,\n *  then the kernel version will automatically shadow these functions.\n *  These implementations are not necessarily the best optimized versions.\n */\n\n/// Cos(x), Taylor series\nCosNTaylor(x) :=\n{\n        Local(numterms, prec, Bx);\n        prec := QuotientN(BuiltinPrecisionGet()*3919, 1702); // P*Ln(10)\n        Bx := -QuotientN(MathBitCount(x)*1143, 1649)-2; // -Ln(x)-2\n        numterms := QuotientN( QuotientN( prec-1, QuotientN( MathBitCount( prec-1)*1588, 2291)+Bx), 2)+1;\n        // (P*Ln(10)-1)/(Ln(P*Ln(10)-1)-Ln(x)-2); use Ln(x)<=?B(x)*Ln(2)\n        SumTaylorNum(MultiplyN(x,x), 1, [[k], -1/(2*k*(2*k-1))], numterms);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/elemfuncs/CosNTaylor.mpw, (CosNTaylor)";
        scriptMap.put("CosNTaylor",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/** This file contains routines for numerical evaluation of elementary functions:\n *  PowerN, ExpN, SinN etc.\n *  It is assumed that the arguments are real (not complex) floating-point or integer numbers. (The [NumericMode?()] flag does not have to be set.)\n *  The result is an exact integer or a floating-point number correct to BuiltinPrecisionGet() digits.\n *\n *  If a better optimized version of these functions is available through the kernel,\n *  then the kernel version will automatically shadow these functions.\n *  These implementations are not necessarily the best optimized versions.\n */\n\n/// BitsToDigits(n,base) and DigitsToBits(n,base). Enough to compute at low precision.\n// this is now a call to the kernel functions, so leave as a reference implementation\nDigitsToBits(n, base) := FloorN(0.51+n*NM(Ln(base)/Ln(2),10));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/elemfuncs/DigitsToBits.mpw, (DigitsToBits)";
        scriptMap.put("DigitsToBits",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/** This file contains routines for numerical evaluation of elementary functions:\n *  PowerN, ExpN, SinN etc.\n *  It is assumed that the arguments are real (not complex) floating-point or integer numbers. (The [NumericMode?()] flag does not have to be set.)\n *  The result is an exact integer or a floating-point number correct to BuiltinPrecisionGet() digits.\n *\n *  If a better optimized version of these functions is available through the kernel,\n *  then the kernel version will automatically shadow these functions.\n *  These implementations are not necessarily the best optimized versions.\n */\n\n/// Identity transformation, compute Exp(x)-1 from value=Exp(x/2^n)-1\n\nExpNDoubling1(value, n) :=\n{\n        Local(shift, result);\n        shift := n;\n        result := value;\n        While (shift>?0)        // lose 'shift' bits of precision here\n        {\n                result := MathMul2Exp(result, 1) + MultiplyN(result, result);\n                shift--;\n        };\n        result;\n};\n\n/// Identity transformation, compute Exp(x) from value=Exp(x/2^n)\n/*\nExpNDoubling(value, n) :=\n[\n        Local(shift, result);\n        shift := n;\n        result := value;\n        While (shift>?0)        // lose 'shift' bits of precision here\n        [\n                result := MultiplyN(result, result);\n                shift--;\n        ];\n        result;\n];\n*/\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/elemfuncs/ExpNDoubling.mpw, (ExpNDoubling)";
        scriptMap.put("ExpNDoubling",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/** This file contains routines for numerical evaluation of elementary functions:\n *  PowerN, ExpN, SinN etc.\n *  It is assumed that the arguments are real (not complex) floating-point or integer numbers. (The [NumericMode?()] flag does not have to be set.)\n *  The result is an exact integer or a floating-point number correct to BuiltinPrecisionGet() digits.\n *\n *  If a better optimized version of these functions is available through the kernel,\n *  then the kernel version will automatically shadow these functions.\n *  These implementations are not necessarily the best optimized versions.\n */\n\n/// Compute Exp(x)-1 from the Taylor series for (Exp(x)-1)/x.\n//Note:tk:changed name from ExpNTaylor1 to ExpNTaylor.\nExpNTaylor(x) :=\n{\n        Local(numterms, prec, Bx);\n        prec := QuotientN(BuiltinPrecisionGet()*3919, 1702); // P*Ln(10)\n        Bx := -QuotientN(MathBitCount(x)*1143, 1649)-2; // -Ln(x)-2\n        numterms := QuotientN( prec-1, QuotientN( MathBitCount( prec-1)*1588, 2291)+Bx)+1;\n        // (P*Ln(10)-1)/(Ln(P*Ln(10)-1)-Ln(x)-2); use Ln(x)<=?B(x)*Ln(2)\n        x*SumTaylorNum(x, 1, [[k], 1/(k+1)], numterms);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/elemfuncs/ExpNTaylor.mpw, (ExpNTaylor)";
        scriptMap.put("ExpNTaylor",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/** This file contains routines for numerical evaluation of elementary functions:\n *  PowerN, ExpN, SinN etc.\n *  It is assumed that the arguments are real (not complex) floating-point or integer numbers. (The [NumericMode?()] flag does not have to be set.)\n *  The result is an exact integer or a floating-point number correct to BuiltinPrecisionGet() digits.\n *\n *  If a better optimized version of these functions is available through the kernel,\n *  then the kernel version will automatically shadow these functions.\n *  These implementations are not necessarily the best optimized versions.\n */\n\n///  MathBitCount: count number of bits in an integer or a float number.\n/*  MathBitCount is now implemented through BigNumber::BitCount() */\n/*  so this stays here as a reference implementation */\n10 # MathBitCount(0) <-- 1;\n20 # MathBitCount(_x):: (x<?0) <-- MathBitCount(-x);\n\n30 # MathBitCount(_value) <--\n{\n        Local(nbits);\n        nbits:=0;\n          Decide(value<?1,\n        {        // float value <? 1, need to multiply by 2\n                nbits := 1;\n                While(value<?1)\n                {\n                        nbits--;\n                        value := MathMul2Exp(value,1);\n                };\n        },\n        {        // need to divide by 2\n                While(value>=?1)\n                {\n                        nbits++;\n                        value := MathMul2Exp(value, -1);\n                };\n        });\n        nbits;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/elemfuncs/MathBitCount.mpw, (MathBitCount)";
        scriptMap.put("MathBitCount",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/** This file contains routines for numerical evaluation of elementary functions:\n *  PowerN, ExpN, SinN etc.\n *  It is assumed that the arguments are real (not complex) floating-point or integer numbers. (The [NumericMode?()] flag does not have to be set.)\n *  The result is an exact integer or a floating-point number correct to BuiltinPrecisionGet() digits.\n *\n *  If a better optimized version of these functions is available through the kernel,\n *  then the kernel version will automatically shadow these functions.\n *  These implementations are not necessarily the best optimized versions.\n */\n\n/// Compute Ln(x) from Ln(x^(2^(1/n)))\nMathLnDoubling(value, n) :=\n{\n        Local(shift, result);\n        shift := n;\n        result := value;\n        While (shift>?0)        // lose 'shift' bits of precision here\n        {\n                result := MultiplyN(result, result);\n                shift--;\n        };\n        result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/elemfuncs/MathLnDoubling.mpw, (MathLnDoubling)";
        scriptMap.put("MathLnDoubling",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/** This file contains routines for numerical evaluation of elementary functions:\n *  PowerN, ExpN, SinN etc.\n *  It is assumed that the arguments are real (not complex) floating-point or integer numbers. (The [NumericMode?()] flag does not have to be set.)\n *  The result is an exact integer or a floating-point number correct to BuiltinPrecisionGet() digits.\n *\n *  If a better optimized version of these functions is available through the kernel,\n *  then the kernel version will automatically shadow these functions.\n *  These implementations are not necessarily the best optimized versions.\n */\n\n/// Ln(x), Taylor series for Ln(1+y)/y, use only with 1/2<x<3/2\nMathLnTaylor(x) :=\n{\n        Local(numterms, y);\n        y := x-1;\n        numterms := QuotientN(BuiltinPrecisionGet()*2136, -643*MathBitCount(y))+1;\n        // (P*Ln(10))/(-Ln(y)); use Ln(y)<=?B(y)*Ln(2), only good for |y|<1/2\n        // here -Ln(y) must be positive\n        y*SumTaylorNum(-y, [[k], 1/(k+1)], numterms);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/elemfuncs/MathLnTaylor.mpw, (MathLnTaylor)";
        scriptMap.put("MathLnTaylor",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/** This file contains routines for numerical evaluation of elementary functions:\n *  PowerN, ExpN, SinN etc.\n *  It is assumed that the arguments are real (not complex) floating-point or integer numbers. (The [NumericMode?()] flag does not have to be set.)\n *  The result is an exact integer or a floating-point number correct to BuiltinPrecisionGet() digits.\n *\n *  If a better optimized version of these functions is available through the kernel,\n *  then the kernel version will automatically shadow these functions.\n *  These implementations are not necessarily the best optimized versions.\n */\n\n// This function is *only* for float and positive A!\n// The answer is only obtained at the current precision.\nMathSqrtFloat(_A) <--\n{\n        Local(bitshift, a0, x0, x0sq, targetbits, subtargetbits, gotbits, targetprec);\n        bitshift := ShiftRight(MathBitCount(A)-1,1);\n        // this is how many bits of precision we need\n        targetprec := BuiltinPrecisionGet();\n        // argument reduction: a0 is between 1 and 4 and has the full target precision\n        a0 := MathMul2Exp(A, -bitshift*2);        // this bit shift would be wrong for integer A\n        BuiltinPrecisionSet(10);        // enough to compute at this point\n        // cannot get more target bits than 1 + (the bits in A)\n        // if this is less than the requested precision, the result will be silently less precise, but c'est la vie\n        targetbits := Minimum(DigitsToBits(targetprec, 10), 1+GetExactBitsN(A));\n        // initial approximation\n        x0 := DivideN(14+22*a0, 31+5*a0);\n        // this approximation gives at least 7 bits (relative error < 0.005) of Sqrt(a0) for 1 <= a0 <= 4\n        gotbits := 7;\n        // find the conditions for the last 2 iterations to be done in almost optimal precision\n        subtargetbits := QuotientN(targetbits+8, 9);\n        Decide(gotbits >=? subtargetbits, subtargetbits := QuotientN(targetbits+2, 3));\n        Decide(gotbits >=? subtargetbits, subtargetbits := targetbits*4);\n//        Echo(\"debug: subtargetbits=\", subtargetbits, \"a0=\", a0, \"targetbits=\", targetbits, \"bitshift=\", bitshift, \"targetprec=\", targetprec);\n        // now perform Halley iterations until we get at least subtargetbits, then start with subtargetbits and perform further Halley iterations\n        While(gotbits <? targetbits)\n        {\n                gotbits := 3*gotbits+1;        // Halley iteration; get 3n+2 bits, allow 1 bit for roundoff\n                // check for suboptimal last iterations\n                Decide(gotbits >=? subtargetbits,\n                {        // it could be very suboptimal to continue with our value of gotbits, so we curb precision for the last 2 iterations which dominate the calculation time at high precision\n                        gotbits := subtargetbits;\n                        subtargetbits := targetbits*4;        // make sure that the above condition never becomes true again\n                });\n                BuiltinPrecisionSet(BitsToDigits(gotbits, 10)+2);        // guard digits\n                x0 := SetExactBitsN(x0, gotbits+6);        // avoid roundoff\n                x0sq := MultiplyN(x0, x0);\n// this gives too much roundoff error                x0 := MultiplyN(x0, DivideN(3*a0+x0sq, a0+3*x0sq));\n// rather use this equivalent formula:\n                x0 := AddN(x0, MultiplyN(x0*2, DivideN(a0-x0sq, a0+3*x0sq)));\n//                Echo(\"debug: \", gotbits, x0, GetExactBitsN(x0), BuiltinPrecisionGet());\n        };\n        // avoid truncating a precise result in x0 by calling BuiltinPrecisionSet() too soon\n        x0 := SetExactBitsN(MathMul2Exp(x0, bitshift), gotbits);\n        BuiltinPrecisionSet(targetprec);\n//        Echo(\"debug: answer=\", x0);\n        x0;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/elemfuncs/MathSqrtFloat.mpw, (MathSqrtFloat)";
        scriptMap.put("MathSqrtFloat",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/** This file contains routines for numerical evaluation of elementary functions:\n *  PowerN, ExpN, SinN etc.\n *  It is assumed that the arguments are real (not complex) floating-point or integer numbers. (The [NumericMode?()] flag does not have to be set.)\n *  The result is an exact integer or a floating-point number correct to BuiltinPrecisionGet() digits.\n *\n *  If a better optimized version of these functions is available through the kernel,\n *  then the kernel version will automatically shadow these functions.\n *  These implementations are not necessarily the best optimized versions.\n */\n\n/// Compute Sin(x), Taylor series for Sin(x)/x\nSinNTaylor(x) :=\n{\n        Local(numterms, prec, Bx);\n        prec := QuotientN(BuiltinPrecisionGet()*3919, 1702); // P*Ln(10)\n        Bx := -QuotientN(MathBitCount(x)*1143, 1649)-2; // -Ln(x)-2\n        numterms := QuotientN( QuotientN( prec+Bx, QuotientN( MathBitCount( prec+Bx)*1588, 2291)+Bx)+1, 2)+1;\n        // (P*Ln(10)-Ln(x)-2)/(Ln(P*Ln(10)-Ln(x)-2)-Ln(x)-2); use Ln(x)<=?B(x)*Ln(2)\n        x*SumTaylorNum(MultiplyN(x,x), 1, [[k], -1/(2*k*(2*k+1))], numterms);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/elemfuncs/SinNTaylor.mpw, (SinNTaylor)";
        scriptMap.put("SinNTaylor",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/** This file contains routines for numerical evaluation of elementary functions:\n *  PowerN, ExpN, SinN etc.\n *  It is assumed that the arguments are real (not complex) floating-point or integer numbers. (The [NumericMode?()] flag does not have to be set.)\n *  The result is an exact integer or a floating-point number correct to BuiltinPrecisionGet() digits.\n *\n *  If a better optimized version of these functions is available through the kernel,\n *  then the kernel version will automatically shadow these functions.\n *  These implementations are not necessarily the best optimized versions.\n */\n\n/// Identity transformation, compute Sin(x) from value=Sin(x/3^n)\n\nSinNTripling(value, n) :=\n{\n        Local(shift, result);\n        shift := n;\n        result := value;\n        While (shift>?0)        // lose 'shift' bits of precision here\n        {        // Sin(x)*(3-4*Sin(x)^2)\n                result := MultiplyN(result, 3 - MathMul2Exp(MultiplyN(result,result), 2) );\n                shift--;\n        };\n        result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/elemfuncs/SinNTripling.mpw, (SinNTripling)";
        scriptMap.put("SinNTripling",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/** This file contains routines for numerical evaluation of elementary functions:\n *  PowerN, ExpN, SinN etc.\n *  It is assumed that the arguments are real (not complex) floating-point or integer numbers. (The [NumericMode?()] flag does not have to be set.)\n *  The result is an exact integer or a floating-point number correct to BuiltinPrecisionGet() digits.\n *\n *  If a better optimized version of these functions is available through the kernel,\n *  then the kernel version will automatically shadow these functions.\n *  These implementations are not necessarily the best optimized versions.\n */\n\n/// SqrtN(x).\nSqrtN(x) := MathSqrt1(x);        // to have another function is easier for debugging\n\n/// Compute square root(x) with nonnegative x. FIXME: No precision tracking yet.\n10 # MathSqrt1(0) <-- 0;\n/// negative or non-numeric arguments give error message\n100 # MathSqrt1(_x) <-- { Echo(\"SqrtN: invalid argument: \", x); False;};\n\n// this is too slow at the moment\n30 # MathSqrt1(x_PositiveNumber?) <-- x*NewtonNum([[r], r+r*(1-x*r^2)/2], FastPower(x,-0.5), 4, 2);\n\n30 # MathSqrt1(x_PositiveNumber?) <-- MathSqrtFloat(x);\n\n// for integers, we need to compute Sqrt(x) to (the number of bits in x) + 1 bits to figure out whether Sqrt(x) is itself an integer. If Sqrt(x) for integer x is exactly equal to an integer, we should return the integer answer rather than the float answer. For this answer, the current precision might be insufficient, therefore we compute with potentially more digits. This is slower but we assume this is what the user wants when calling SqrtN() on an integer.\n20 # MathSqrt1(x_Integer?):: (GreaterThan?(x,0)) <--\n{\n        Local(result);\n        Decide(ModuloN(x,4)<?2 And? ModuloN(x,3)<?2 And? ModuloN(x+1,5)<?3,\n                // now the number x has a nonzero chance of being an exact square\n                {\n                        // check whether increased precision would be at all necessary\n//                        Echo(\"checking integer case\");\n                        GlobalPush(BuiltinPrecisionGet());\n                        Decide(MathBitCount(x)+3>?DigitsToBits(BuiltinPrecisionGet(), 10),\n                          BuiltinPrecisionSet(BitsToDigits(MathBitCount(x), 10)+1));\n                                // need one more digit to decide whether Sqrt(x) is integer\n                        // otherwise the current precision is sufficient\n\n                        // convert x to float and use the float routine\n                        result := MathSqrtFloat(x+0.);\n                        // decide whether result is integer: decrease precision and compare\n                        Decide(FloatIsInt?(SetExactBitsN(result, GetExactBitsN(result)-3)), result:= Floor(result+0.5));\n                        BuiltinPrecisionSet(GlobalPop());\n                },\n                // now the number x cannot be an exact square; current precision is sufficient\n                result := MathSqrtFloat(x+0.)\n        );\n        // need to set the correct precision on the result - will have no effect on integer answers\n        SetExactBitsN(result, DigitsToBits(BuiltinPrecisionGet(),10));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/elemfuncs/SqrtN.mpw, (SqrtN;MathSqrt1)";
        scriptMap.put("SqrtN",scriptString);
        scriptMap.put("MathSqrt1",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// low-level numerical calculations of elementary functions.\n/// These are only called if NumericMode?() returns True\n\nArcSinNum(x) :=\n{\n        // need to be careful when |x| close to 1\n        Decide(\n                239*Abs(x) >=? 169,        // 169/239 is a good enough approximation of 1/Sqrt(2)\n                // use trigonometric identity to avoid |x| close to 1\n                Sign(x)*(InternalPi()/2-ArcSinN(Sqrt(1-x^2))),\n                ArcSinN(x)\n        );\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/numerical/ArcSinNum.mpw, (ArcSinNum)";
        scriptMap.put("ArcSinNum",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// low-level numerical calculations of elementary functions.\n/// These are only called if NumericMode?() returns True\n\nArcTanNum(x) :=\n{\n        // using trigonometric identities is faster for now\n        Decide(\n                Abs(x)>?1,\n                Sign(x)*(InternalPi()/2-ArcSin(1/Sqrt(x^2+1))),\n                ArcSin(x/Sqrt(x^2+1))\n        );\n};\n\n\n\n\n\n/* old methods -- slower for now\n/// numerical evaluation of ArcTan using continued fractions: top level\n2 # ArcTan(x_Number?)::NumericMode?() <--\nSign(x) *\n// now we need to compute ArcTan of a nonnegative number Abs(x)\n[\n        Local(nterms, y);\n        y := Abs(x);\n        // use identities to improve convergence -- see essays book\n        Decide(\n                y>?1,\n                y:=1/y        // now y <=? 1\n        // we shall know that the first identity was used because Abs(x) > 1 still\n        );\n        // use the second identity\n        y := y/(1+Sqrt(1+y^2));        // now y <=? Sqrt(2)-1\n        // find the required number of terms in the continued fraction\n        nterms := 1/y;        // this needs to be calculated at full precision\n        // see essays book on the choice of the number of terms (added 2 \"guard terms\").\n        // we need Hold() because otherwise, if NumericMode?() returns True, NM(..., 5) will not avoid the full precision calculation of Ln().\n        // the value of x should not be greater than 1 here!\n        nterms := 2 + Ceil( NM(Hold(Ln(10)/(Ln(4)+2*Ln(nterms))), 5) * BuiltinPrecisionGet() );\n        Decide(        // call the actual routine\n                Abs(x)>?1,\n                Pi/2-2*MyArcTan(y, nterms),        // this is for |x|>1\n                2*MyArcTan(y, nterms)\n                // MyArcTan(x, nterms)\n        );\n];\n*/\n\n\n\n/// numerical evaluation of ArcTan using continued fractions: low level\n\n// evaluation using recursion -- slightly faster but lose some digits to roundoff errors and needs large recursion depth\n/*\n10 # ContArcTan(_x,_n,_n) <-- (2*n-1);\n20 # ContArcTan(_x,_n,_m) <--\n[\n  (2*n-1) + (n*x)^2/ContArcTan(x,n+1,m);\n];\n\nMyArcTan(x,n) :=\n[\n  x/ContArcTan(x,1,n);\n];\n*/\n/*\n/// evaluate n terms of the continued fraction for ArcTan(x) without recursion.\n/// better control of roundoff errors\nMyArcTan(x, n) :=\n[\n        Local(i, p, q, t);\n        // initial numerator and denominator\n        p:=1;\n        q:=1;\n        // start evaluating from the last term upwards\n        For(i:=n, i>=?1, i--)\n        [\n                //[p,q] := [p + q*(i*x)^2/(4*i^2-1), p];\n        //        t := p*(2*i-1) + q*(i*x)^2; then have to start with p:=2*n+1\n                t := p + q*(i*x)^2/(4*i^2-1);\n                q := p;\n                p := t;\n        ];\n        // answer is x/(p/q)\n        x*q/p;\n];\n*/\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/numerical/ArcTanNum.mpw, (ArcTanNum)";
        scriptMap.put("ArcTanNum",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// low-level numerical calculations of elementary functions.\n/// These are only called if NumericMode?() returns True\n\n/* The BrentLn() algorithm is currently slower in internal math but should be asymptotically faster. */\n\nLocalSymbols(BrentCacheOfConstantsN)\n{\n    CachedConstant(BrentCacheOfConstantsN, Ln2, InternalLnNum(2));        // this is only useful for BrentLn\n};\n\n// compute Ln(x_Integer?) using the AGM sequence. See: Brent paper rpb028 (1975).\n// this is currently faster than LogN(n) for precision > 40 digits\n10 # BrentLn(x_Integer?)::(BuiltinPrecisionGet()>?40) <--\n{\n        Local(y, n, k, eps);\n        n := BuiltinPrecisionGet();        // decimal digits\n        // initial power of x\n        k := 1 + Quotient(IntLog(4*10^n, x), 2);        // now x^(2*k)>4*10^n\n        BuiltinPrecisionSet(n+5);        // guard digits\n        eps := DivideN(1, 10^n);        // precision\n        y := PowerN(x, k);        // not yet divided by 4\n        // this is Brent's AGM times y. This way we work around the MathPiper limitation of fixed precision, at cost of slightly slower initial iterations\n        y := DivideN(InternalPi()*y, (2*k)*ArithmeticGeometricMean(4, y, eps));\n        BuiltinPrecisionSet(n);\n        RoundTo(y, n);        // do not return a more precise number than we really have\n};\n\n15 # BrentLn(x_Integer?)  <-- LogN(x);\n\n/// calculation of Ln(x) using Brent's AGM sequence - use precomputed Pi and Ln2.\n20 # BrentLn(_x)::(x<?1) <-- -BrentLn(1/x);\n\n// this is currently faster than LogN() for precision > 85 digits and numbers >2\n30 # BrentLn(_x)::(BuiltinPrecisionGet()>?85) <--\n{\n        Local(y, n, n1, k, eps);\n  NM({\n    n := BuiltinPrecisionGet();        // decimal digits\n    // effective precision is n+Ln(n)/Ln(10)\n    n1 := n + IntLog(n,10);        // Ln(2) < 7050/10171\n    // initial power of 2\n    k := 2 + Quotient(n1*28738, 2*8651)        // Ln(10)/Ln(2) < 28738/8651; now 2^(2*k)>4*10^n1\n    // find how many binary digits we already have in x, and multiply by a sufficiently large power of 2 so that y=x*2^k is larger than 2*10^(n1/2)\n    - IntLog(Floor(x), 2);\n    // now we need k*Ln(2)/Ln(10) additional digits to compensate for cancellation at the final subtraction\n    BuiltinPrecisionSet(n1+2+Quotient(k*3361, 11165));        // Ln(2)/Ln(10) < 3361/11165\n    eps := DivideN(1, 10^(n1+1));        // precision\n    y := x*2^(k-2);        // divided already by 4\n    // initial values for AGM\n    // this is Brent's AGM times y. This way we work around the MathPiper limitation of fixed precision, at cost of slightly slower initial iterations\n    y:=InternalPi()*y/(2*ArithmeticGeometricMean(1,y,eps)) - k*Ln2();\n    BuiltinPrecisionSet(n);\n  });\n        y;        // do not return a more precise number than we really have\n};\n\n40 # BrentLn(x_Number?) <-- LogN(x);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/numerical/BrentLn.mpw, (BrentLn)";
        scriptMap.put("BrentLn",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// low-level numerical calculations of elementary functions.\n/// These are only called if NumericMode?() returns True\n\nCosNum(x) :=\n{\n  Decide(x<?0 Or? 113*x>?710, x:=TruncRadian(x));\n  CosN(x);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/numerical/CosNum.mpw, (CosNum)";
        scriptMap.put("CosNum",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// low-level numerical calculations of elementary functions.\n/// These are only called if NumericMode?() returns True\n\n// large positive x\n10 # ExpNum(x_Number?):: (x >? MathExpThreshold()) <-- {\n        Local(i, y);\n        i:=0;\n        For(i:=0, x >? MathExpThreshold(), i++)\n                x := DivideN(x, 2.);\n        For(y:= ExpN(x), i>?0, i--)\n                y := MultiplyN(y, y);\n        y;\n\n};\n// large negative x\n20 # ExpNum(x_Number?):: (2*x <? -MathExpThreshold()) <-- DivideN(1, ExpNum(-x));\n// other values of x\n30 # ExpNum(x_Number?) <-- ExpN(x);\n\n\n//CachedConstant(Exp1, ExpN(1));        // Exp1 is useless so far\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/numerical/ExpNum.mpw, (ExpNum)";
        scriptMap.put("ExpNum",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// low-level numerical calculations of elementary functions.\n/// These are only called if NumericMode?() returns True\n\n// natural logarithm: this should be called only for real x>1\n//InternalLnNum(x) := LogN(x);\n// right now the fastest algorithm is Halley's method for Exp(x)=a\n// when internal math is fixed, we may want to use Brent's method (below)\n// this method is using a cubically convergent Newton iteration for Exp(x/2)-a*Exp(-x/2)=0:\n// x := x - 2 * (Exp(x)-a) / (Exp(x)+a) = x-2+4*a/(Exp(x)+a)\nInternalLnNum(x_Number?)::(x>=?1) <-- NewtonLn(x);\n\nInternalLnNum(x_Number?)::(0<?x And? x<?1) <-- - InternalLnNum(DivideN(1,x));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/numerical/InternalLnNum.mpw, (InternalLnNum)";
        scriptMap.put("InternalLnNum",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// low-level numerical calculations of elementary functions.\n/// These are only called if NumericMode?() returns True\n\n//  LogN in the original C++ code hangs! Scripted implementation is much better\nLogN(x) := InternalLnNum(x);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/numerical/LogN.mpw, (LogN)";
        scriptMap.put("LogN",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// low-level numerical calculations of elementary functions.\n/// These are only called if NumericMode?() returns True\n\nNewtonLn(x) :=\n        LocalSymbols(y)\n{\n// we need ExpN instead of Exp to avoid NM() which is used in the definition of Exp.\n// and we need ExpNum() instead of ExpN so that it is faster for large arguments and to avoid the error generated when core functions like ExpN are called on symbolic arguments.\n        NewtonNum([['y], 4*x/(ExpNum('y)+x)-2+'y],\n        // initial guess is obtained as Ln(x^2)/Ln(2) * (Ln(2)/2)\n                DivideN(794*IntLog(Floor(x*x), 2), 2291), 10, 3);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/numerical/NewtonLn.mpw, (NewtonLn)";
        scriptMap.put("NewtonLn",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// low-level numerical calculations of elementary functions.\n/// These are only called if NumericMode?() returns True\n\nSinNum(x) :=\n{\n  Decide(x<?0 Or? 113*x>?710, x:=TruncRadian(x));        // 710/113 is close to 2*Pi\n  SinN(x);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/numerical/SinNum.mpw, (SinNum)";
        scriptMap.put("SinNum",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "TanNum(x) :=\n{\n  Decide(x<?0 Or? 113*x>?710, x:=TruncRadian(x));\n  TanN(x);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/numerical/TanNum.mpw, (TanNum)";
        scriptMap.put("TanNum",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// low-level numerical calculations of elementary functions.\n/// These are only called if NumericMode?() returns True\n\n/* TruncRadian truncates the radian r so it is between 0 and 2*Pi.\n * It calculates r mod 2*Pi using the required precision.\n */\nTruncRadian(_r) <--\n{\n  Local(twopi);\n  // increase precision by the number of digits of r before decimal point; enough to evaluate Abs(r) with 1 digit of precision\n  NM({\n    r:=Eval(r);\n    twopi:=2*InternalPi();\n    r:=r-FloorN(r/twopi)*twopi;\n  }, BuiltinPrecisionGet() + IntLog(Ceil(Abs(NM(Eval(r), 1))), 10));\n  r;\n};\nHoldArgument(\"TruncRadian\",\"r\");\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/numerical/TruncRadian.mpw, (TruncRadian)";
        scriptMap.put("TruncRadian",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* def file definitions\nMathExpThreshold\nSetMathExpThreshold\n*/\n\n/// low-level numerical calculations of elementary functions.\n/// These are only called if NumericMode?() returns True\n\n//////////////////////////////////////////////////\n/// Exponent\n//////////////////////////////////////////////////\n\nLocalSymbols(mathExpThreshold) {\n  // improve convergence of Exp(x) for large x\n  mathExpThreshold := Decide(Not? Assigned?(mathExpThreshold), 500);\n\n  MathExpThreshold() := mathExpThreshold;\n  SetMathExpThreshold(threshold) := {mathExpThreshold:= threshold; };\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/numerical/expthreshold.mpw, (MathExpThreshold;SetMathExpThreshold)";
        scriptMap.put("MathExpThreshold",scriptString);
        scriptMap.put("SetMathExpThreshold",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// coded by Serge Winitzki. See essays documentation for algorithms.\n\n//////////////////////////////////////////////////\n/// Numerical method: Newton-like superconvergent iteration\n//////////////////////////////////////////////////\n\n// Newton's method, generalized, with precision control and diagnostics\n\n/// auxiliary utility: compute the number of common decimal digits of x and y (using relative precision)\nCommondigits(x,y) :=\n{\n        Local(diff);\n        diff := Abs(x-y);\n        Decide(\n                diff=?0,\n                Infinity,\n                // use approximation Ln(2)/Ln(10) >? 351/1166\n                Quotient(IntLog(FloorN(DivideN(Maximum(Abs(x), Abs(y)), diff)), 2)*351, 1166)\n        );         // this many decimal digits in common\n};\n\n///interface\nNewtonNum(_func, _x0) <-- NewtonNum(func, x0, 5);        // default prec0\nNewtonNum(_func, _x0, _prec0) <-- NewtonNum(func, x0, prec0, 2);\n\n// func is the function to iterate, i.e. x = func(x).\n// prec0 is the initial precision necessary to get convergence started.\n// order is the order of convergence of the given sequence (e.g. 2 or 3).\n// x0 must be close enough so that x1 has a few common digits with x0 after at most 5 iterations.\nNewtonNum(_func, _xinit, _prec0, _order) <--\n{\n        Check(prec0>=?4, \"Argument\", \"NewtonNum: Error: initial precision must be at least 4\");\n        Check(Integer?(order) And? order>?1, \"Argument\", \"NewtonNum: Error: convergence order must be an integer and at least 2\");\n        Local(x0, x1, prec, exactdigits, intpart, initialtries);\n  NM({\n    x0 := xinit;\n    prec := BuiltinPrecisionGet();\n    intpart := IntLog(Ceil(Abs(x0)), 10);        // how many extra digits for numbers like 100.2223\n    // intpart must be set to 0 if we have true floating-point semantics of BuiltinPrecisionSet()\n    BuiltinPrecisionSet(2+prec0-intpart);        // 2 guard digits\n    x1 := (func @ x0);        // let's run one more iteration by hand\n    // first, we get prec0 exact digits\n    exactdigits := 0;\n    initialtries := 5;        // stop the loop the the initial value is not good\n    While(exactdigits*order <? prec0 And? initialtries>?0)\n    {\n      initialtries--;\n      x0 := x1;\n      x1 := (func @ x0);\n      exactdigits := Commondigits(x0, x1);\n  //                Decide(InVerboseMode(), Echo(\"NewtonNum: Info: got\", exactdigits, \"exact digits at prec. \", BuiltinPrecisionGet()));\n    };\n    // need to check that the initial precision is achieved\n    Decide(\n      Assert(\"value\", [\"NewtonNum: Error: need a more accurate initial value than\", xinit])\n        exactdigits >=? 1,\n    {\n    exactdigits :=Minimum(exactdigits, prec0+2);\n    // run until get prec/order exact digits\n    intpart := IntLog(Ceil(Abs(x1)), 10);        // how many extra digits for numbers like 100.2223\n    While(exactdigits*order <=? prec)\n    {\n      exactdigits := exactdigits*order;\n      BuiltinPrecisionSet(2+Minimum(exactdigits, Quotient(prec,order)+1)-intpart);\n      x0 := x1;\n      x1 := (func @ x0);\n  //                Decide(InVerboseMode(), Echo(\"NewtonNum: Info: got\", Commondigits(x0, x1), \"exact digits at prec. \", BuiltinPrecisionGet()));\n    };\n    // last iteration by hand\n    BuiltinPrecisionSet(2+prec);\n    x1 := RoundTo( (func @ x1), prec);\n    },\n    // did not get a good initial value, so return what we were given\n    x1 := xinit\n    );\n    BuiltinPrecisionSet(prec);\n  });\n        x1;\n};\n\n\n/*\nexample: logarithm function using cubically convergent Newton iteration for\nExp(x/2)-a*Exp(-x/2)=0:\n\nx := x - 2 * (Exp(x)-a) / (Exp(x)+a)\n\nLN(x_Number?)::(x>?1 ) <--\n        LocalSymbols(y)\n[\n// initial guess is obtained as Ln(x^2)/Ln(2) * (Ln(2)/2)\n        NewtonNum([[y],4*x/(Exp(y)+x)-2+y], NM(794/2291*IntLog(Floor(x*x),2),5), 10, 3);\n];\n*/\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stdfuncs/nummethods/NewtonNum.mpw, (NewtonNum)";
        scriptMap.put("NewtonNum",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # Abs(Infinity)         <-- Infinity; //Note:tk:moved here from stdfuncts.\n\n10 # Abs(n_Number?) <-- AbsN(n);\n10 # Abs(n_PositiveNumber?/m_PositiveNumber?) <-- n/m;\n10 # Abs(n_NegativeNumber?/m_PositiveNumber?) <-- (-n)/m;\n10 # Abs(n_PositiveNumber?/m_NegativeNumber?) <-- n/(-m);\n10 # Abs( Sqrt(_x)) <-- Sqrt(x);\n10 # Abs(-Sqrt(_x)) <-- Sqrt(x);\n10 # Abs(Complex(_r,_i)) <-- Sqrt(r^2 + i^2);\n10 # Abs(n_Infinity?) <-- Infinity;\n10 # Abs(Undefined) <-- Undefined;\n20 # Abs(n_List?) <-- MapSingle(\"Abs\",n);\n\n100 # Abs(_a^_n) <-- Abs(a)^n;\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stubs/Abs.mpw, (Abs)";
        scriptMap.put("Abs",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n// Canonicalise an expression so its terms are grouped to the right\n// ie a+(b+(c+d))\n// This doesn't preserve order of terms, when doing this would cause more\n// subtractions and nested parentheses than necessary.\n1 # CanonicalAdd((_a+_b)+_c) <-- CanonicalAdd(CanonicalAdd(a)+\n                                              CanonicalAdd(CanonicalAdd(b)+\n                                                           CanonicalAdd(c)));\n1 # CanonicalAdd((_a-_b)+_c) <-- CanonicalAdd(CanonicalAdd(a)+\n                                              CanonicalAdd(CanonicalAdd(c)-\n                                                           CanonicalAdd(b)));\n1 # CanonicalAdd((_a+_b)-_c) <-- CanonicalAdd(CanonicalAdd(a)+\n                                              CanonicalAdd(CanonicalAdd(b)-\n                                                           CanonicalAdd(c)));\n1 # CanonicalAdd((_a-_b)-_c) <-- CanonicalAdd(CanonicalAdd(a)-\n                                              CanonicalAdd(CanonicalAdd(b)+\n                                                           CanonicalAdd(c)));\n2 # CanonicalAdd(_a)         <-- a;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stubs/CanonicalAdd.mpw, (CanonicalAdd)";
        scriptMap.put("CanonicalAdd",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n5 # Ceil(Infinity) <-- Infinity;\n5 # Ceil(-Infinity) <-- -Infinity;\n5 # Ceil(Undefined) <-- Undefined;\n\n10 # Ceil(x_RationalOrNumber?)\n   <--\n   {\n     x:=NM(x);\n     Local(prec,result,n);\n     Assign(prec,BuiltinPrecisionGet());\n     Decide(Zero?(x),Assign(n,2),\n     Decide(x>?0,\n       Assign(n,2+FloorN(NM(FastLog(x)/FastLog(10)))),\n       Assign(n,2+FloorN(NM(FastLog(-x)/FastLog(10))))\n       ));\n     Decide(n>?prec,BuiltinPrecisionSet(n));\n     Assign(result,CeilN(x));\n     BuiltinPrecisionSet(prec);\n     result;\n   };\n//   CeilN (NM(x));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stubs/Ceil.mpw, (Ceil)";
        scriptMap.put("Ceil",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Expand expands polynomials.\n */\n10 # Expand(expr_CanBeUni) <-- NormalForm(MakeUni(expr));\n20 # Expand(_expr) <-- expr;\n\n10 # Expand(expr_CanBeUni(var),_var) <-- NormalForm(MakeUni(expr,var));\n20 # Expand(_expr,_var) <-- expr;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stubs/Expand.mpw, (Expand)";
        scriptMap.put("Expand",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n5 # Floor(Infinity) <-- Infinity;\n5 # Floor(-Infinity) <-- -Infinity;\n5 # Floor(Undefined) <-- Undefined;\n\n\n10 # Floor(x_RationalOrNumber?)\n   <--\n   {\n     x:=NM(Eval(x));\n//Echo(\"x = \",x);\n     Local(prec,result,n);\n     Assign(prec,BuiltinPrecisionGet());\n     Decide(Zero?(x),\n       Assign(n,2),\n       Decide(x>?0,\n         Assign(n,2+FloorN(NM(FastLog(x)/FastLog(10)))),\n         Assign(n,2+FloorN(NM(FastLog(-x)/FastLog(10))))\n       ));\n     Decide(n>?prec,BuiltinPrecisionSet(n));\n//Echo(\"Before\");\n     Assign(result,FloorN(x));\n//Echo(\"After\");\n     BuiltinPrecisionSet(prec);\n     result;\n   };\n\n//     FloorN(NM(x));\n\n\n//todo:tk:should this be removed because it is no longer needed?\n/* Changed by Nobbi before redefinition of Rational\n10 # Floor(x_Number?) <-- FloorN(x);\n10 # Ceil (x_Number?) <-- CeilN (x);\n10 # Round(x_Number?) <-- FloorN(x+0.5);\n\n20 # Floor(x_Rational?):: (Number?(Numerator(x)) And? Number?(Denominator(x))) <-- FloorN(NM(x));\n20 # Ceil (x_Rational?):: (Number?(Numerator(x)) And? Number?(Denominator(x))) <-- CeilN (NM(x));\n20 # Round(x_Rational?):: (Number?(Numerator(x)) And? Number?(Denominator(x))) <-- FloorN(NM(x+0.5));\n*/\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stubs/Floor.mpw, (Floor)";
        scriptMap.put("Floor",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n0 # Gcd(0,0) <-- 1;\n1 # Gcd(0,_m) <-- Abs(m);\n1 # Gcd(_n,0) <-- Abs(n);\n1 # Gcd(_m,_m) <-- Abs(m);\n2 # Gcd(_n,1) <-- 1;\n2 # Gcd(1,_m) <-- 1;\n2 # Gcd(n_Integer?,m_Integer?) <-- GcdN(n,m);\n3 # Gcd(_n,_m)::(GaussianInteger?(m) And? GaussianInteger?(n) )<-- GaussianGcd(n,m);\n\n4 # Gcd(-(_n), (_m)) <-- Gcd(n,m);\n4 # Gcd( (_n),-(_m)) <-- Gcd(n,m);\n4 # Gcd(Sqrt(n_Integer?),Sqrt(m_Integer?)) <-- Sqrt(Gcd(n,m));\n4 # Gcd(Sqrt(n_Integer?),m_Integer?) <-- Sqrt(Gcd(n,m^2));\n4 # Gcd(n_Integer?,Sqrt(m_Integer?)) <-- Sqrt(Gcd(n^2,m));\n\n5 # Gcd(n_Rational?,m_Rational?) <--\n{\n  Gcd(Numerator(n),Numerator(m))/Lcm(Denominator(n),Denominator(m));\n};\n\n\n10 # Gcd(list_List?)::(Length(list)>?2) <--\n    {\n      Local(first);\n      first:=Gcd(list[1],list[2]);\n      Gcd(first~Rest(Rest(list)));\n    };\n14 # Gcd([0]) <-- 1;\n15 # Gcd([_head]) <-- head;\n\n20 # Gcd(list_List?)::(Length(list)=?2) <-- Gcd(list[1],list[2]);\n\n30 # Gcd(n_CanBeUni,m_CanBeUni)::(Length(VarList(n*m))=?1) <--\n{\n  Local(vars);\n  vars:=VarList(n+m);\n  NormalForm(Gcd(MakeUni(n,vars),MakeUni(m,vars)));\n};\n\n100 # Gcd(n_Constant?,m_Constant?) <-- 1;\n110 # Gcd(_m,_n) <--\n{\n  Echo(\"Not simplified\");\n};\n\n\n//Note:tk:moved here from univar.rep.\n0 # Gcd(n_UniVar?,m_UniVar?)::\n    (n[1] =? m[1] And? Degree(n) <? Degree(m)) <-- Gcd(m,n);\n\n1 # Gcd(nn_UniVar?,mm_UniVar?)::\n    (nn[1] =? mm[1] And? Degree(nn) >=? Degree(mm)) <--\n{\n   UniVariate(nn[1],0,\n                UniGcd(Concat(ZeroVector(nn[2]),nn[3]),\n                       Concat(ZeroVector(mm[2]),mm[3])));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stubs/Gcd.mpw, (Gcd)";
        scriptMap.put("Gcd",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Least common multiple */\n5  # Lcm(a_Integer?,b_Integer?) <-- Quotient(a*b,Gcd(a,b));\n\n10 # Lcm(list_List?)::(Length(list)>?2) <--\n{\n        Local(first);\n        first:=Lcm(list[1],list[2]);\n        Lcm(first~Rest(Rest(list)));\n};\n\n10 # Lcm(list_List?)::(Length(list)=?2) <-- Lcm(list[1],list[2]);\n\n// We handle lists with just one element to avoid special-casing \n// all the calls. \n10 # Lcm(list_List?)::(Length(list)=?1) <-- Lcm(list[1],1);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stubs/Lcm.mpw, (Lcm)";
        scriptMap.put("Lcm",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n////////////////////// Log rules stuff //////////////////////\n\n// LnCombine is nice and simple now\nLnCombine(_a) <-- DoLnCombine(CanonicalAdd(a));\n\n// Combine single terms.  This can always be done without a recursive call.\n1 # DoLnCombine(Ln(_a))              <-- Ln(a);\n1 # DoLnCombine(Ln(_a)*_b)           <-- Ln(a^b);\n1 # DoLnCombine(_b*Ln(_a))           <-- Ln(a^b);\n\n// Deal with the first two terms so they are both simple logs if at all\n// possible.  This involves converting a*Ln(b) to Ln(b^a) and moving log terms\n// to the start of expressions.  One of either of these operations always takes\n// us to a strictly simpler form than we started in, so we can get away with\n// calling DoLnCombine again with the partly simplified argument.\n\n// TODO: Make this deal with division everywhere it deals with multiplication\n\n// first term is a log multiplied by something\n2 # DoLnCombine(Ln(_a)*_b+_c)        <-- DoLnCombine(Ln(a^b)+c);\n2 # DoLnCombine(Ln(_a)*_b-_c)        <-- DoLnCombine(Ln(a^b)-c);\n2 # DoLnCombine(_b*Ln(_a)+_c)        <-- DoLnCombine(Ln(a^b)+c);\n2 # DoLnCombine(_b*Ln(_a)-_c)        <-- DoLnCombine(Ln(a^b)-c);\n\n// second term of a two-term expression is a log multiplied by something\n2 # DoLnCombine(_a+(_c*Ln(_b)))      <-- DoLnCombine(a+Ln(b^c));\n2 # DoLnCombine(_a-(_c*Ln(_b)))      <-- DoLnCombine(a-Ln(b^c));\n2 # DoLnCombine(_a+(Ln(_b)*_c))      <-- DoLnCombine(a+Ln(b^c));\n2 # DoLnCombine(_a-(Ln(_b)*_c))      <-- DoLnCombine(a-Ln(b^c));\n\n// second term of a three-term expression is a log multiplied by something\n2 # DoLnCombine(_a+((Ln(_b)*_c)+_d)) <-- DoLnCombine(a+(Ln(b^c)+d));\n2 # DoLnCombine(_a+((Ln(_b)*_c)-_d)) <-- DoLnCombine(a+(Ln(b^c)-d));\n2 # DoLnCombine(_a-((Ln(_b)*_c)+_d)) <-- DoLnCombine(a-(Ln(b^c)+d));\n2 # DoLnCombine(_a-((Ln(_b)*_c)-_d)) <-- DoLnCombine(a-(Ln(b^c)-d));\n\n2 # DoLnCombine(_a+((_c*Ln(_b))+_d)) <-- DoLnCombine(a+(Ln(b^c)+d));\n2 # DoLnCombine(_a+((_c*Ln(_b))-_d)) <-- DoLnCombine(a+(Ln(b^c)-d));\n2 # DoLnCombine(_a-((_c*Ln(_b))+_d)) <-- DoLnCombine(a-(Ln(b^c)+d));\n2 # DoLnCombine(_a-((_c*Ln(_b))-_d)) <-- DoLnCombine(a-(Ln(b^c)-d));\n\n// Combine the first two terms if they are logs, otherwise move one or both to\n// the front, then recurse on the remaining possibly-log-containing portion.\n// (the code makes more sense than this comment)\n3 # DoLnCombine(Ln(_a)+Ln(_b))       <-- Ln(a*b);\n3 # DoLnCombine(Ln(_a)-Ln(_b))       <-- Ln(a/b);\n3 # DoLnCombine(Ln(_a)+(Ln(_b)+_c))  <-- DoLnCombine(Ln(a*b)+c);\n3 # DoLnCombine(Ln(_a)+(Ln(_b)-_c))  <-- DoLnCombine(Ln(a*b)-c);\n3 # DoLnCombine(Ln(_a)-(Ln(_b)+_c))  <-- DoLnCombine(Ln(a/b)-c);\n3 # DoLnCombine(Ln(_a)-(Ln(_b)-_c))  <-- DoLnCombine(Ln(a/b)+c);\n\n// We know that at least one of the first two terms isn't a log\n4 # DoLnCombine(Ln(_a)+(_b+_c))      <-- b+DoLnCombine(Ln(a)+c);\n4 # DoLnCombine(Ln(_a)+(_b-_c))      <-- b+DoLnCombine(Ln(a)-c);\n4 # DoLnCombine(Ln(_a)-(_b+_c))      <-- DoLnCombine(Ln(a)-c)-b;\n4 # DoLnCombine(Ln(_a)-(_b-_c))      <-- DoLnCombine(Ln(a)+c)-b;\n\n4 # DoLnCombine(_a+(Ln(_b)+_c))      <-- a+DoLnCombine(Ln(b)+c);\n4 # DoLnCombine(_a+(Ln(_b)-_c))      <-- a+DoLnCombine(Ln(b)-c);\n4 # DoLnCombine(_a-(Ln(_b)+_c))      <-- a-DoLnCombine(Ln(b)+c);\n4 # DoLnCombine(_a-(Ln(_b)-_c))      <-- a-DoLnCombine(Ln(b)-c);\n\n// If we get here we know that neither of the first two terms is a log\n5 # DoLnCombine(_a+(_b+_c))          <-- a+(b+DoLnCombine(c));\n\n// Finished\n6 # DoLnCombine(_a)                  <-- a;\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stubs/LnCombine.mpw, (LnCombine;DoLnCombine)";
        scriptMap.put("LnCombine",scriptString);
        scriptMap.put("DoLnCombine",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n////////////////////// Log rules stuff //////////////////////\n\n// LnExpand\n1 # LnExpand(Ln(x_Integer?))\n                            <-- Add(Map([[n,m],m*Ln(n)],Transpose(Factors(x))));\n1 # LnExpand(Ln(_a*_b))     <-- LnExpand(Ln(a))+LnExpand(Ln(b));\n1 # LnExpand(Ln(_a/_b))     <-- LnExpand(Ln(a))-LnExpand(Ln(b));\n1 # LnExpand(Ln(_a^_n))     <-- LnExpand(Ln(a))*n;\n2 # LnExpand(_a)            <-- a;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stubs/LnExpand.mpw, (LnExpand)";
        scriptMap.put("LnExpand",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"Modulo\",*);\n\n0 # Modulo(_n,m_RationalOrNumber?)::(m<?0) <-- `Hold(Modulo(@n,@m));\n\n1 # Modulo(n_NegativeInteger?,m_PositiveInteger?) <--\n{\n  Local(result);\n  result := ModuloN(n,m);\n  Decide(result <? 0,result := result + m);\n  result;\n};\n1 # Modulo(n_PositiveInteger?,m_PositiveInteger?) <-- ModuloN(n,m);\n2 # Modulo(0,_m) <-- 0;\n2 # Modulo(n_PositiveInteger?,Infinity) <-- n;\n3 # Modulo(n_Integer?,m_Integer?) <-- ModuloN(n,m);\n4 # Modulo(n_Number?,m_Number?) <-- NonNM(Modulo(Rationalize(n),Rationalize(m)));\n\n5 # Modulo(n_RationalOrNumber?,m_RationalOrNumber?)/*_(n>?0 And? m>?0)*/ <--\n{\n  Local(n1,n2,m1,m2);\n  n1:=Numerator(n);\n  n2:=Denominator(n);\n  m1:=Numerator(m);\n  m2:=Denominator(m);\n  Modulo(n1*m2,m1*n2)/(n2*m2);\n};\n\n6 # Modulo(n_List?,m_List?) <-- Map(\"Modulo\",[n,m]);\n7 # Modulo(n_List?,_m) <-- Map(\"Modulo\",[n,FillList(m,Length(n))]);\n\n\n30 # Modulo(n_CanBeUni,m_CanBeUni) <--\n{\n  Local(vars);\n  vars:=VarList(n+m);\n  NormalForm(Modulo(MakeUni(n,vars),MakeUni(m,vars)));\n};\n\n\n//Note:tk:moved here from univariate.rep.\n0 # Modulo(n_UniVar?,m_UniVar?)::(Degree(n) <? Degree(m)) <-- n;\n1 # Modulo(n_UniVar?,m_UniVar?)::\n    (n[1] =? m[1] And? Degree(n) >=? Degree(m)) <--\n{\n    UniVariate(n[1],0,\n               UniDivide(Concat(ZeroVector(n[2]),n[3]),\n                         Concat(ZeroVector(m[2]),m[3]))[2]);\n};\n\n10 # Modulo(n_CanBeUni, m_CanBeUni, vars_List?)::(Length(vars)=?1) <--\n{\n    NormalForm(Modulo(MakeUni(n,vars),MakeUni(m,vars)));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stubs/Modulo.mpw, (Modulo)";
        scriptMap.put("Modulo",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(\"Object\",[_pred,_x]);\nRuleHoldArguments(\"Object\",2,0,Apply(pred,[x])=?True) x;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stubs/Object.mpw, (Object)";
        scriptMap.put("Object",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Integer divisions */\n0 # Quotient(n_Integer?,m_Integer?) <-- QuotientN(n,m);\n1 # Quotient(0  ,_m) <-- 0;\n2 # Quotient(n_RationalOrNumber?,m_RationalOrNumber?) <--\n{\n  Local(n1,n2,m1,m2,sgn1,sgn2);\n  n1:=Numerator(n);\n  n2:=Denominator(n);\n  m1:=Numerator(m);\n  m2:=Denominator(m);\n  sgn1 := Sign(n1*m2);\n  sgn2 := Sign(m1*n2);\n  sgn1*sgn2*Floor(DivideN(sgn1*n1*m2,sgn2*m1*n2));\n};\n30 # Quotient(n_CanBeUni,m_CanBeUni)::(Length(VarList(n*m))=?1) <--\n{\n\n  Local(vars,nl,ml);\n  vars:=VarList(n*m);\n  nl := MakeUni(n,vars);\n  ml := MakeUni(m,vars);\n  NormalForm(Quotient(nl,ml));\n};\n\n\n//Note:tk:moved here from univariate.rep.\n0 # Quotient(n_UniVar?,m_UniVar?)::(Degree(n) <? Degree(m)) <-- 0;\n1 # Quotient(n_UniVar?,m_UniVar?)::\n    (n[1] =? m[1] And? Degree(n) >=? Degree(m)) <--\n{\n    UniVariate(n[1],0,\n               UniDivide(Concat(ZeroVector(n[2]),n[3]),\n                         Concat(ZeroVector(m[2]),m[3]))[1]);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stubs/Quotient.mpw, (Quotient)";
        scriptMap.put("Quotient",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Note:tk:this was not listed in the def file.\n0 # Rem(n_Number?,m_Number?) <-- n-m*Quotient(n,m);\n30 # Rem(n_CanBeUni,m_CanBeUni) <-- Modulo(n,m);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stubs/Rem.mpw, (Rem)";
        scriptMap.put("Rem",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n5 # Round(Infinity) <-- Infinity;\n5 # Round(-Infinity) <-- -Infinity;\n5 # Round(Undefined) <-- Undefined;\n\n10 # Round(x_RationalOrNumber?) <-- FloorN(NM(x+0.5));\n10 # Round(x_List?) <-- MapSingle(\"Round\",x);\n\n20 # Round(x_Complex?)::(RationalOrNumber?(Re(x)) And? RationalOrNumber?(Im(x)) )\n                <-- FloorN(NM(Re(x)+0.5)) + FloorN(NM(Im(x)+0.5))*I;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stubs/Round.mpw, (Round)";
        scriptMap.put("Round",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # Sign(n_PositiveNumber?) <-- 1;\n10 # Sign(n_Zero?) <-- 0;\n20 # Sign(n_Number?) <-- -1;\n15 # Sign(n_Infinity?)::(n <? 0) <-- -1;\n15 # Sign(n_Infinity?)::(n >? 0) <-- 1;\n15 # Sign(n_Number?/m_Number?) <-- Sign(n)*Sign(m);\n20 # Sign(n_List?) <-- MapSingle(\"Sign\",n);\n\n100 # Sign(_a)^n_Even? <-- 1;\n100 # Sign(_a)^n_Odd? <-- Sign(a);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stubs/Sign.mpw, (Sign)";
        scriptMap.put("Sign",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n0 # Sqrt(0) <-- 0;\n0 # Sqrt(Infinity) <--  Infinity;\n0 # Sqrt(-Infinity) <-- Complex(0,Infinity);\n0 # Sqrt(Undefined) <--  Undefined;\n1 # Sqrt(x_PositiveInteger?)::(Integer?(SqrtN(x))) <-- SqrtN(x);\n2 # Sqrt(x_PositiveNumber?)::NumericMode?() <-- SqrtN(x);\n2 # Sqrt(x_NegativeNumber?) <-- Complex(0,Sqrt(-x));\n/* 3 # Sqrt(x_Number?/y_Number?) <-- Sqrt(x)/Sqrt(y); */\n3 # Sqrt(x_Complex?)::NumericMode?() <-- x^(1/2);\n/* Threading  */\nSqrt(xlist_List?) <-- MapSingle(\"Sqrt\",xlist);\n\n90 # (Sqrt(x_Constant?))::(NegativeNumber?(NM(x))) <-- Complex(0,Sqrt(-x));\n\n110 # Sqrt(Complex(_r,_i)) <-- Exp(Ln(Complex(r,i))/2);\n\n\n400 # Sqrt(x_Integer?)::Integer?(SqrtN(x)) <-- SqrtN(x);\n400 # Sqrt(x_Integer?/y_Integer?)::(Integer?(SqrtN(x)) And? Integer?(SqrtN(y))) <-- SqrtN(x)/SqrtN(y);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stubs/Sqrt.mpw, (Sqrt)";
        scriptMap.put("Sqrt",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* def file definitions\n=\n<?\n>?\n<=?\n>=?\n!=?\n\n*/\n\n/* Comparison operators. They call the internal comparison routines when\n * both arguments are numbers. The value Infinity is also understood.\n*/\n\n// Undefined is a very special case as we return False for everything\n1 # Undefined <?  _x  <--  False;\n1 # Undefined <=? _x  <--  False;\n1 # Undefined >?  _x  <--  False;\n1 # Undefined >=? _x  <--  False;\n1 # _x <?  Undefined  <--  False;\n1 # _x <=? Undefined  <--  False;\n1 # _x >?  Undefined  <--  False;\n1 # _x >=? Undefined  <--  False;\n\n\n// If n and m are numbers, use the standard LessThan function immediately\n5 # (n_Number? <? m_Number?) <-- LessThan?(n-m,0);\n\n\n// If n and m are symbolic after a single evaluation, see if they can be coerced in to a real-valued number.\nLocalSymbols(nNum,mNum)\n{\n  10 # (_n <? _m)::{nNum:=NM(Eval(n)); mNum:=NM(Eval(m));Number?(nNum) And? Number?(mNum);} <-- LessThan?(nNum-mNum,0);\n};\n\n// Deal with Infinity\n20 #  (Infinity <? _n)::(Not?(Infinity?(n)))  <-- False;\n20 #  (-Infinity <? _n)::(Not?(Infinity?(n))) <-- True;\n20 #  (_n <? Infinity)::(Not?(Infinity?(n)))  <-- True;\n20 #  (_n <? -Infinity)::(Not?(Infinity?(n))) <-- False;\n\n// Lots of known identities go here\n30 # (_n1/_n2) <? 0  <--  (n1 <? 0) !=? (n2 <? 0);\n30 # (_n1*_n2) <? 0  <--  (n1 <? 0) !=? (n2 <? 0);\n\n// This doesn't sadly cover the case where a and b have opposite signs\n30 # ((_n1+_n2) <? 0)::((n1 <? 0) And? (n2 <? 0))  <--  True;\n30 # ((_n1+_n2) <? 0)::((n1 >? 0) And? (n2 >? 0))  <--  False;\n30 #  _x^a_Odd?  <? 0  <--  x <? 0;\n30 #  _x^a_Even? <? 0  <--  False; // This is wrong for complex x\n\n// Add other functions here!  Everything we can compare to 0 should be here.\n40 # ((Sqrt(_x))::(x >? 0)) <? 0          <--  False;\n\n40 # (Sin(_x) <? 0)::(Not?(Even?(NM(x/Pi))) And? Even?(NM(Floor(x/Pi)))) <-- False;\n40 # (Sin(_x) <? 0)::(Not?(Odd? (NM(x/Pi))) And? Odd? (NM(Floor(x/Pi)))) <-- True;\n\n40 # Cos(_x) <? 0 <-- Sin(Pi/2-x) <? 0;\n\n40 # (Tan(_x) <? 0)::(Not?(Even?(NM(2*x/Pi))) And? Even?(NM(Floor(2*x/Pi)))) <-- False;\n40 # (Tan(_x) <? 0)::(Not?(Odd? (NM(2*x/Pi))) And? Odd? (NM(Floor(2*x/Pi)))) <-- True;\n\n// Functions that need special treatment with more than one of the comparison\n// operators as they always return true or false.  For these we must define\n// both the `<?' and `>=?' operators.\n40 # (Complex(_a,_b) <?  0)::(b!=?0) <--  False;\n40 # (Complex(_a,_b) >=? 0)::(b!=?0) <--  False;\n40 # ((Sqrt(_x))::(x <? 0)) <?  0      <--  False;\n40 # ((Sqrt(_x))::(x <? 0)) >=? 0      <--  False;\n\n// Deal with negated terms\n50 # -(_x) <? 0 <-- Not?((x<?0) Or? (x=?0));\n\n// Define each of [>?,<=?,>=?] in terms of <?\n50 # _n >?  _m <-- m <? n;\n50 # _n <=? _m <-- m >=? n;\n50 # _n >=? _m <-- Not?(n<?m);\n\n\nFunction(\"!=?\",[_aLeft,_aRight]) Not?(aLeft=?aRight);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stubs/comparison_operators.mpw, (<?;>?;<=?;>=?;!=?)";
        scriptMap.put("<?",scriptString);
        scriptMap.put(">?",scriptString);
        scriptMap.put("<=?",scriptString);
        scriptMap.put(">=?",scriptString);
        scriptMap.put("!=?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRulebaseHoldArguments(\"->\",[_left,_right]);\n\nHoldArgument(\"->\",\"left\");\n\n//HoldArgument(\"->\",\"right\");\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stubs/minus_greaterthan_operator.mpw, (->)";
        scriptMap.put("->",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* def file definitions\n<<\n>>\n*/\n\n/* Shifting operators */\n\nn_Integer? << m_Integer? <-- ShiftLeft(n,m);\nn_Integer? >> m_Integer? <-- ShiftRight(n,m);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/stubs/shifting_operators.mpw, (<<;>>)";
        scriptMap.put("<<",scriptString);
        scriptMap.put(">>",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/*Extremely hacky workaround, MacroSubstituteApply is actually the same as SubstituteApply,\n  but without re-evaluating its arguments. I could not just change SubstituteApply, as\n  it changed behaviour such that tests started to break.\n */\n \n \n \nFunction(\"MacroSubstituteApply\", [_body, _predicate, _change])\n{\n  `MacroSubstituteApply((Hold(@body)));\n};\n\nHoldArgument(\"MacroSubstituteApply\", \"predicate\");\n\nHoldArgument(\"MacroSubstituteApply\", \"change\");\n\nUnFence(\"MacroSubstituteApply\", 3);\n\nRulebaseHoldArguments(\"MacroSubstituteApply\", [_body]);\n\nUnFence(\"MacroSubstituteApply\", 1);\n\n\n\nRuleHoldArguments(\"MacroSubstituteApply\", 1, 1, `ApplyFast(predicate, [Hold(Hold(@body))]) =? True)\n{\n  `ApplyFast(change, [Hold(Hold(@body))]);\n};\n\n\n\nRuleHoldArguments(\"MacroSubstituteApply\", 1, 2, `Function?(Hold(@body)))\n{\n  `ApplyFast(\"MacroMapArgs\", [Hold(Hold(@body)), \"MacroSubstituteApply\"]);\n};\n\n\n\nRuleHoldArguments(\"MacroSubstituteApply\", 1, 3, True)\n{\n `Hold(@body);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/substitute/MacroSubstituteApply.mpw, (MacroSubstituteApply)";
        scriptMap.put("MacroSubstituteApply",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"SubstituteApply\", [_body, _predicate, _change])\n{\n  SubstituteApply(body);\n};\n\nHoldArgument(\"SubstituteApply\", \"predicate\");\n\nHoldArgument(\"SubstituteApply\", \"change\");\n\nUnFence(\"SubstituteApply\", 3);\n\nRulebaseHoldArguments(\"SubstituteApply\", [_body]);\n\nUnFence(\"SubstituteApply\", 1);\n\n\n\nRuleHoldArguments(\"SubstituteApply\", 1, 1, Apply(predicate, [body]) =? True)\n{\n  Apply(change,[body]);\n};\n\n\n\nRuleHoldArguments(\"SubstituteApply\", 1, 2, Function?(body))\n{\n  Apply(\"MapArgs\",[body,\"SubstituteApply\"]);\n};\n\n\n\nRuleHoldArguments(\"SubstituteApply\", 1, 3, True) body;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/substitute/SubstituteApply.mpw, (SubstituteApply)";
        scriptMap.put("SubstituteApply",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction() Add(_val, ...);\n\n10 # Add([]) <-- 0;\n20 # Add(values_List?) <--\n{\n   Local(i, sum);\n   sum:=0;\n   ForEach(i, values) { sum := sum + i; };\n   sum;\n};\n\n// Add(1) should return 1\n30 # Add(_value) <-- value;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/sums/Add.mpw, (Add)";
        scriptMap.put("Add",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/*  this is disabled because some functions seem to implicitly define Min / Max with a different number of args, and then MathPiper is confused if it hasn't loaded all the Function() declarations beforehand.\nFIXME\n/// Min, Max with many arguments\n*/\n\n//Retract(\"Maximum\", 1);\n//Retract(\"Maximum\", 2);\n//Retract(\"Maximum\", 3);\n\n//Function() Maximum(list);\n\n//Function() Maximum(l1, l2);\n\nFunction() Maximum(_l1, _l2, _l3, ...);\n\n\n\n10 # Maximum(_l1, _l2, l3_List?) <-- Maximum(Concat([l1, l2], l3));\n20 # Maximum(_l1, _l2, _l3) <-- Maximum([l1, l2, l3]);\n/**/\n\n10 # Maximum(l1_List?,l2_List?) <-- Map(\"Maximum\",[l1,l2]);\n\n\n20 # Maximum(l1_RationalOrNumber?,l2_RationalOrNumber?) <-- Decide(l1>?l2,l1,l2);\n\n\n30 # Maximum(l1_Constant?,l2_Constant?) <-- Decide(NM(Eval(l1-l2))>?0,l1,l2);\n\n// Max on empty lists\n10 # Maximum([]) <-- Undefined;\n\n\n20 # Maximum(list_List?) <--\n{\n  Local(result);\n  result:= list[1];\n  ForEach(item,Rest(list)) result:=Maximum(result,item);\n  result;\n};\n\n\n30 # Maximum(_x) <-- x;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/sums/Maximum.mpw, (Maximum)";
        scriptMap.put("Maximum",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/*  this is disabled because some functions seem to implicitly define Min / Max with a different number of args, \nand then MathPiper is confused if it hasn't loaded all the Function() declarations beforehand.\nFIXME\n/// Min, Max with many arguments\n*/\n\n//Retract(\"Minimum\", 1);\n//Retract(\"Minimum\", 2);\n//Retract(\"Minimum\", 3);\n\n//Function() Minimum(list);\n\n//Function() Minimum(l1, l2)\n\nFunction() Minimum(_l1, _l2, _l3, ...);\n\n10 # Minimum(_l1, _l2, l3_List?) <-- Minimum(Concat([l1, l2], l3));\n20 # Minimum(_l1, _l2, _l3) <-- Minimum([l1, l2, l3]);\n\n10 # Minimum(l1_List?,l2_List?) <-- Map(\"Minimum\",[l1,l2]);\n\n20 # Minimum(l1_RationalOrNumber?,l2_RationalOrNumber?) <-- Decide(l1<?l2,l1,l2);\n\n30 # Minimum(l1_Constant?,l2_Constant?) <-- Decide(NM(Eval(l1-l2))<?0,l1,l2);\n\n//Min on an empty list.\n10 # Minimum([]) <-- Undefined;\n\n20 # Minimum(list_List?) <--\n{\n  Local(result);\n  result:= list[1];\n  ForEach(item,Rest(list)) result:=Minimum(result,item);\n  result;\n};\n\n30 # Minimum(_x) <-- x;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/sums/Minimum.mpw, (Minimum)";
        scriptMap.put("Minimum",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"Product\",[_sumvar,_sumfrom,_sumto,_sumbody])\n{\n   Local(sumi,sumsum);\n   sumsum:=1;\n   For(sumi:=sumfrom,sumi<=?sumto And? sumsum!=?0,sumi++)\n       {\n        MacroLocal(sumvar);\n        MacroAssign(sumvar,sumi);\n        sumsum:=sumsum*Eval(sumbody);\n       };\n   sumsum;\n};\nUnFence(\"Product\",4);\nHoldArgument(\"Product\",\"sumvar\");\nHoldArgument(\"Product\",\"sumbody\");\n\nProduct(sumlist_List?) <--\n{\n   Local(sumi,sumsum);\n   sumsum:=1;\n   ForEach(sumi,sumlist)\n   {\n     sumsum:=sumsum*sumi;\n   };\n   sumsum;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/sums/Product.mpw, (Product)";
        scriptMap.put("Product",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"Subfactorial\",[_n])\n{\n        n! * Sum(k,0,n,(-1)^(k)/k!);\n};\n\n30 # Subfactorial(n_List?) <-- MapSingle(\"Subfactorial\",n);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/sums/Subfactorial.mpw, (Subfactorial)";
        scriptMap.put("Subfactorial",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Sums */\n\n\nRulebaseHoldArguments(\"Sum\",[_sumvararg,_sumfromarg,_sumtoarg,_sumbodyarg]);\n\n\n\n\n10  # Sum(_sumvar,sumfrom_Number?,sumto_Number?,_sumbody)::(sumfrom>?sumto) <-- 0;\n\n20 # Sum(_sumvar,sumfrom_Number?,sumto_Number?,_sumbody)::(sumto<?sumfrom) <--\n     ApplyFast(\"Sum\",[sumvar,sumto,sumfrom,sumbody]);\n30 # Sum(_sumvar,sumfrom_Number?,sumto_Number?,_sumbody) <--\nLocalSymbols(sumi,sumsum){\n   Local(sumi,sumsum);\n   sumsum:=0;\n   For(sumi:=sumfrom,sumi<=?sumto,sumi++)\n       {\n        MacroLocal(sumvar);\n        MacroAssign(sumvar,sumi);\n        sumsum:=sumsum+Eval(sumbody);\n       };\n   sumsum;\n};\n\nUnFence(\"Sum\",4);\nHoldArgument(\"Sum\",\"sumvararg\");\nHoldArgument(\"Sum\",\"sumbodyarg\");\n\n\n\n40 # Sum([]) <-- 0;\n\n50 # Sum(values_List?) <--\n{\n   Local(i, sum);\n   sum:=0;\n   ForEach(i, values) { sum := sum + i; };\n   sum;\n};\n\n\n\n\n/* todo:tk:commenting out for the minimal version of the scripts.\n//============== SumFunc\nLocalSymbols(c,d,expr,from,to,summand,sum,predicate,n,r,var,x) {\n\n// Attempt to Sum series\n\nFunction() SumFunc(_k, _from, _to, _summand, _sum, _predicate );\nFunction() SumFunc(_k, _from, _to, _summand, _sum);\nHoldArgument(\"SumFunc\",\"predicate\");\nHoldArgument(\"SumFunc\",\"sum\");\nHoldArgument(\"SumFunc\",\"summand\");\n\n// Difference code does not work\nSumFunc(_sumvar,sumfrom_Integer?,_sumto,_sumbody,_sum) <--\n{\n        // Take the given answer and create 2 rules, one for an exact match\n        // for sumfrom, and one which will catch sums starting at a different\n        // index and subtract off the difference\n\n        `(40 # Sum(@sumvar,@sumfrom,@sumto,@sumbody )        <-- Eval(@sum) );\n        `(41 # Sum(@sumvar,p_Integer?,@sumto,@sumbody)::(p >? @sumfrom)\n             <--\n             {\n                  Local(sub);\n                  (sub := Eval(ListToFunction(['Sum,sumvararg,@sumfrom,p-1,sumbodyarg])));\n                  Simplify(Eval(@sum) - sub );\n             });\n};\n\nSumFunc(_sumvar,sumfrom_Integer?,_sumto,_sumbody,_sum,_condition) <--\n{\n\n        `(40 # Sum(@sumvar,@sumfrom,@sumto,@sumbody)::(@condition)    <-- Eval(@sum) );\n        `(41 # Sum(@sumvar,p_Integer?,@sumto,@sumbody )::(@condition And? p >? @sumfrom)\n             <--\n             {\n                  Local(sub);\n                  `(sub := Eval(ListToFunction(['Sum,sumvararg,@sumfrom,p-1,sumbodyarg])));\n                  Simplify(Eval(@sum) - sub );\n             });\n};\n\n// Some type of canonical form is needed so that these match when\n// given in a different order, like x^k/k! vs. (1/k!)*x^k\n// works !\nSumFunc(_k,1,_n,_c + _d,\n  Eval(ListToFunction(['Sum,sumvararg,1,n,c])) +\n  Eval(ListToFunction(['Sum,sumvararg,1,n,d]))\n);\nSumFunc(_k,1,_n,_c*_expr,Eval(c*ListToFunction(['Sum,sumvararg,1,n,expr])), FreeOf?(k,c) );\nSumFunc(_k,1,_n,_expr/_c,Eval(ListToFunction(['Sum,sumvararg,1,n,expr])/c), FreeOf?(k,c) );\n\n// this only works when the index=1\n// If the limit of the general term is not zero, then the series diverges\n// We need something like IsUndefined(term), because this croaks when limit return Undefined\n//SumFunc(_k,1,Infinity,_expr,Infinity,Eval(Abs(ListToFunction(['Limit,sumvararg,Infinity,expr])) >? 0));\nSumFunc(_k,1,Infinity,1/_k,Infinity);\n\nSumFunc(_k,1,_n,_c,c*n,FreeOf?(k,c) );\nSumFunc(_k,1,_n,_k, n*(n+1)/2 );\n//SumFunc(_k,1,_n,_k^2, n*(n+1)*(2*n+1)/6 );\n//SumFunc(_k,1,_n,_k^3, (n*(n+1))^2 / 4 );\nSumFunc(_k,1,_n,_k^_p,(Bernoulli(p+1,n+1) - Bernoulli(p+1))/(p+1), Integer?(p) );\nSumFunc(_k,1,_n,2*_k-1, n^2 );\nSumFunc(_k,1,_n,HarmonicNumber(_k),(n+1)*HarmonicNumber(n) - n );\n\n// Geometric series! The simplest of them all ;-)\nSumFunc(_k,0,_n,(r_FreeOf?(k))^(_k), (1-r^(n+1))/(1-r) );\n\n// Infinite Series\n// this allows Zeta a complex argument, which is not supported yet\nSumFunc(_k,1,Infinity,1/(_k^_d), Zeta(d), FreeOf?(k,d) );\nSumFunc(_k,1,Infinity,_k^(-_d), Zeta(d), FreeOf?(k,d) );\n\nSumFunc(_k,0,Infinity,_x^(2*_k+1)/(2*_k+1)!,Sinh(x) );\nSumFunc(_k,0,Infinity,(-1)^k*_x^(2*_k+1)/(2*_k+1)!,Sin(x) );\nSumFunc(_k,0,Infinity,_x^(2*_k)/(2*_k)!,Cosh(x) );\nSumFunc(_k,0,Infinity,(-1)^k*_x^(2*_k)/(2*_k)!,Cos(x) );\nSumFunc(_k,0,Infinity,_x^(2*_k+1)/(2*_k+1),ArcTanh(x) );\nSumFunc(_k,0,Infinity,1/(_k)!,Exp(1) );\nSumFunc(_k,0,Infinity,_x^_k/(_k)!,Exp(x) );\n40 # Sum(_var,_from,Infinity,_expr)::( `(Limit(@var,Infinity)(@expr)) =? Infinity) <-- Infinity;\n\nSumFunc(_k,1,Infinity,1/BinomialCoefficient(2*_k,_k), (2*Pi*Sqrt(3)+9)/27 );\nSumFunc(_k,1,Infinity,1/(_k*BinomialCoefficient(2*_k,_k)), (Pi*Sqrt(3))/9 );\nSumFunc(_k,1,Infinity,1/(_k^2*BinomialCoefficient(2*_k,_k)), Zeta(2)/3 );\nSumFunc(_k,1,Infinity,1/(_k^3*BinomialCoefficient(2*_k,_k)), 17*Zeta(4)/36 );\nSumFunc(_k,1,Infinity,(-1)^(_k-1)/_k, Ln(2) );\n\n};\n\n\n*/\n";
        scriptString[2] = "/org/mathpiper/scripts4/sums/Sum.mpw, (Sum;SumFunc)";
        scriptMap.put("Sum",scriptString);
        scriptMap.put("SumFunc",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// partial factorial\nn1_RationalOrNumber? *** n2_RationalOrNumber? <--\n{\n        Check(n2-n1 <=? 65535, \"Argument\", \"Partial factorial: Error: the range \" ~ ( PipeToString() Write(n2-n1) ) ~ \" is too large, you may want to avoid exact calculation\");\n        Decide(n2-n1<?0,\n                1,\n                Factorialpartial(n1, n2)\n        );\n};\n\n/// recursive routine to evaluate \"partial factorial\" a*(a+1)*...*b\n// TODO lets document why the >>1 as used here is allowed (rounding down? What is the idea behind this algorithm?)\n2# Factorialpartial(_a, _b):: (b-a>=?4) <-- Factorialpartial(a, a+((b-a)>>1)) * Factorialpartial(a+((b-a)>>1)+1, b);\n3# Factorialpartial(_a, _b):: (b-a>=?3) <-- a*(a+1)*(a+2)*(a+3);\n4# Factorialpartial(_a, _b):: (b-a>=?2) <-- a*(a+1)*(a+2);\n5# Factorialpartial(_a, _b):: (b-a>=?1) <-- a*(a+1);\n6# Factorialpartial(_a, _b):: (b-a>=?0) <-- a;\n";
        scriptString[2] = "/org/mathpiper/scripts4/sums/asterisk_asterisk_asterisk_operator.mpw, (***;Factorialpartial)";
        scriptMap.put("***",scriptString);
        scriptMap.put("Factorialpartial",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/// even/odd double factorial: product of even or odd integers up to n\n1# (n_PositiveInteger?)!! :: (n <=? 3) <-- n;\n2# (n_PositiveInteger?)!! <--\n{\n        Check(n<=?65535, \"Argument\", \"Double factorial: Error: the argument \" ~ ( PipeToString() Write(n) ) ~ \" is too large, you may want to avoid exact calculation\");\n        Factorialdouble(2+Modulo(n, 2), n);\n};\n// special cases\n3# (_n)!! :: (n =? -1 Or? n =? 0) <-- 1;\n\n// the purpose of this mess \"Quotient(a+b,2)+1+Modulo(Quotient(a+b,2)+1-a, 2)\" is to obtain the smallest integer which is >= Quotient(a+b,2)+1 and is also odd or even when a is odd or even; we need to add at most 1 to (Quotient(a+b,2)+1)\n2# Factorialdouble(_a, _b)::(b-a >=? 6) <-- Factorialdouble(a, Quotient(a+b,2)) * Factorialdouble(Quotient(a+b,2)+1+Modulo(Quotient(a+b,2)+1-a, 2), b);\n3# Factorialdouble(_a, _b)::(b-a >=? 4) <-- a*(a+2)*(a+4);\n4# Factorialdouble(_a, _b)::(b-a >=? 2) <-- a*(a+2);\n5# Factorialdouble(_a, _b) <-- a;\n\n/// double factorial for lists is threaded\n30 # (n_List?)!! <-- MapSingle(\"!!\",n);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/sums/exclamationpoint_exclamationpoint_operator.mpw, (!!;Factorialdouble)";
        scriptMap.put("!!",scriptString);
        scriptMap.put("Factorialdouble",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Factorials */\n\n10 # 0! <-- 1;\n10 # (Infinity)! <-- Infinity;\n20 # ((n_PositiveInteger?)!) <-- {\n        Check(n <=? 65535, \"Argument\", \"Factorial: Error: the argument \" ~ ( PipeToString() Write(n) ) ~ \" is too large, you may want to avoid exact calculation\");\n        MathFac(n);\n};\n\n25 # ((x_Constant?)!)::(FloatIsInt?(x) And? x>?0) <-- (Round(x)!);\n\n30 # ((x_Number?)!)::NumericMode?() <-- InternalGammaNum(x+1);\n\n40 # (n_List?)! <-- MapSingle(\"!\",n);\n\n/* formulae for half-integer factorials:\n\n(+(2*z+1)/2)! = Sqrt(Pi)*(2*z+1)! / (2^(2*z+1)*z!) for z >= 0\n(-(2*z+1)/2)! = Sqrt(Pi)*(-1)^z*z!*2^(2*z) / (2*z)! for z >= 0\n\nDouble factorials are more efficient:\n        (2*n-1)!! := 1*3*...*(2*n-1) = (2*n)! / (2^n*n!)\n        (2*n)!! := 2*4*...*(2*n) = 2^n*n!\n\n*/\n/* // old version - not using double factorials\nHalfIntegerFactorial(n_Odd?):: (n>?0) <--\n        Sqrt(Pi) * ( n! / ( 2^n*((n-1)/2)! ) );\nHalfIntegerFactorial(n_Odd?):: (n<?0)  <--\n        Sqrt(Pi) * ( (-1)^((-n-1)/2)*2^(-n-1)*((-n-1)/2)! / (-n-1)! );\n*/\n// new version using double factorials\nHalfIntegerFactorial(n_Odd?):: (n>?0) <--\n        Sqrt(Pi) * ( n!! / 2^((n+1)/2) );\nHalfIntegerFactorial(n_Odd?):: (n<?0)  <--\n        Sqrt(Pi) * ( (-1)^((-n-1)/2)*2^((-n-1)/2) / (-n-2)!! );\n//HalfIntegerFactorial(n_Odd?):: (n= -1)  <-- Sqrt(Pi);\n\n/* Want to also compute (2.5)! */\n40 # (n_RationalOrNumber?)! ::(Denominator(Rationalize(n))=?2) <-- HalfIntegerFactorial(Numerator(Rationalize(n)));\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/sums/exclamationpoint_operator.mpw, (!;HalfIntegerFactorial)";
        scriptMap.put("!",scriptString);
        scriptMap.put("HalfIntegerFactorial",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"BenchCall\",[_expr])\n{\n  Echo(\"In&gt \",expr);\n  WriteString(\"<font color=ff0000>\");\n  Eval(expr);\n  WriteString(\"</font>\");\n  True;\n};\nHoldArgument(\"BenchCall\",\"expr\");\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/testers/BenchCall.mpw, (BenchCall)";
        scriptMap.put("BenchCall",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"BenchShow\",[_expr])\n{\n  Echo(\"In&gt \",expr);\n  WriteString(\"<font color=ff0000> \");\n  Echo(\"Out&gt \",Eval(expr),\"</font>\");\n  True;\n};\nHoldArgument(\"BenchShow\",\"expr\");\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/testers/BenchShow.mpw, (BenchShow)";
        scriptMap.put("BenchShow",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"EqualAsSets\",*);\n\n10 # EqualAsSets( A_List?, B_List? )::(Length(A)=?Length(B)) <--\n{\n    Local(Acopy,b,nba,result);\n    Acopy  := FlatCopy(A);\n    result := True;\n    ForEach(b,B)\n     {\n         nba := Find(Acopy,b);\n         Decide( nba <? 0, { result := False; Break(); } );\n         DestructiveDelete(Acopy,nba);\n     };\n     Decide( Not? result, result := Length(Acopy)=?0 );\n     result;\n};\n\n20 # EqualAsSets( _A, _B ) <-- False;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/testers/EqualAsSets.mpw, (EqualAsSets)";
        scriptMap.put("EqualAsSets",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"KnownFailure\",[_expr])\n{\n  Local(rfail);\n  Echo(\"Known failure: \", expr);\n  Assign(rfail,Eval(expr));\n  Decide(rfail,Echo([\"Failure resolved!\"]));\n};\nHoldArgument(\"KnownFailure\",\"expr\");\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/testers/KnownFailure.mpw, (KnownFailure)";
        scriptMap.put("KnownFailure",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* LogicTest compares the truth tables of two expressions. */\nLocalSymbols(TrueFalse)\n{\n  RulebaseHoldArguments(\"TrueFalse\",[_var,_expr]);\n  10 # TrueFalse(var_Atom?,_expr) <-- `[(@expr) Where (@var)==False,(@expr) Where (@var)==True];\n  20 # TrueFalse([],_expr) <-- `(@expr);\n  30 # TrueFalse(var_List?,_expr) <--\n  `{ \n    Local(t,h);\n    Assign(h,First(@var));\n    Assign(t,Rest(@var));\n    TrueFalse(h,TrueFalse(t,@expr));\n  };\n  \n  HoldArgument(\"TrueFalse\",\"var\");\n  HoldArgument(\"TrueFalse\",\"expr\");\n\n  Macro(\"LogicTest\",[_vars,_expr1,_expr2]) Verify(TrueFalse((@vars),(@expr1)), TrueFalse((@vars),(@expr2)));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/testers/LogicTest.mpw, (LogicTest;TrueFalse)";
        scriptMap.put("LogicTest",scriptString);
        scriptMap.put("TrueFalse",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"LogicVerify\",[_aLeft,_aRight])\n{\n  Decide(aLeft !=? aRight,\n    Verify(CanProve(aLeft  Implies?  aRight),True)\n  );\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/testers/LogicVerify.mpw, (LogicVerify)";
        scriptMap.put("LogicVerify",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"NextTest\",[_aLeft])\n{\n// curline++;\n  WriteString(\"Test suite for \"~aLeft~\" : \");\n  NewLine();\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/testers/NextTest.mpw, (NextTest)";
        scriptMap.put("NextTest",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"NumericEqual\",*);\n\n10 # NumericEqual(left_Decimal?, right_Decimal?, precision_PositiveInteger?) <--\n{\n    Decide(InVerboseMode(),Tell(\"NumericEqual\",[left,right]));\n    Local(repL,repR,precL,precR,newL,newR,plo,phi,replo,rephi);\n    Local(newhi,newrepL,newlo,newrepR,ans);\n    repL     := NumberToRep(left);\n    repR     := NumberToRep(right);\n    precL    := repL[2];\n    precR    := repR[2];\n    Decide(InVerboseMode(),Tell(\"  \",[precL,precR,precision]));\n    newL     := RoundToPrecision(left,  precision );\n    newR     := RoundToPrecision(right, precision );\n    Decide(InVerboseMode(),Tell(\"  \",[newL,newR]));\n    newrepL  := NumberToRep( newL );\n    newrepR  := NumberToRep( newR );\n    Decide(InVerboseMode(),Tell(\"  \",[newrepL,newrepR]));\n    ans      := Verify( newrepL[1] - newrepR[1], 0 );\n    Decide(InVerboseMode(),Tell(\"        \",ans));\n    ans;\n};\n\n\n15 # NumericEqual(left_Integer?, right_Integer?, precision_PositiveInteger?) <--\n{\n    Decide(InVerboseMode(),Tell(\"NumericEqualInt\",[left,right]));\n    left =? right;\n};\n\n\n20 # NumericEqual(left_Number?, right_Number?, precision_PositiveInteger?) <--\n{\n    Decide(InVerboseMode(),Tell(\"NumericEqualNum\",[left,right]));\n    Local(nI,nD,repI,repD,precI,precD,intAsDec,newDec,newrepI,newrepD,ans);\n    Decide( Integer?(left), {nI:=left; nD:=right;}, {nI:=right; nD:=left;});\n    // the integer can be converted to the equivalent decimal at any precision\n    repI  := NumberToRep(nI);\n    repD  := NumberToRep(nD);\n    precI := repI[2];\n    precD := repD[2];\n    intAsDec := RoundToPrecision(1.0*nI,precision);\n    newDec   := RoundToPrecision( nD,   precision );\n    newrepI  := NumberToRep( intAsDec );\n    newrepD  := NumberToRep( newDec   );\n    Decide(InVerboseMode(),\n      {\n          Tell(\"        \",[nI,nD]);\n          Tell(\"    \",[repI,repD]);\n          Tell(\"  \",[precI,precD]);\n          Tell(\"       \",[intAsDec,newDec]);\n          Tell(\"       \",[newrepI,newrepD]);\n       }\n    );\n    ans      := Verify( newrepI[1] - newrepD[1], 0 );\n    Decide(InVerboseMode(),Tell(\"        \",ans));\n    ans;\n};\n\n\n25 # NumericEqual(left_Complex?, right_Complex?, precision_PositiveInteger?) <--\n{\n    Decide(InVerboseMode(),Tell(\"NumericEqualC\",[left,right]));\n    Local(rrL,iiL,rrR,iiR,ans);\n    rrL := Re(left);\n    iiL := Im(left);\n    rrR := Re(right);\n    iiR := Im(right);\n    Decide(InVerboseMode(),\n      {\n         Tell(\"  \",[left,right]);\n         Tell(\"  \",[rrL,rrR]);\n         Tell(\"  \",[iiL,iiR]);\n      }\n    );\n    ans := (NumericEqual(rrL,rrR,precision) And? NumericEqual(iiL,iiR,precision));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/testers/NumericEqual.mpw, (NumericEqual)";
        scriptMap.put("NumericEqual",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nRandVerifyArithmetic(_n)<--\n{\n While(n>?0)\n {\n   n--;\n   VerifyArithmetic(FloorN(300*Random()),FloorN(80*Random()),FloorN(90*Random()));\n };\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/testers/RandVerifyArithmetic.mpw, (RandVerifyArithmetic)";
        scriptMap.put("RandVerifyArithmetic",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Functions that aid in testing */\n\n/* Round to specified number of digits */\n10 # RoundTo(x_Number?, precision_PositiveInteger?) <--\n{\n  Local(oldPrec,result);\n\n  oldPrec:=BuiltinPrecisionGet();\n\n  BuiltinPrecisionSet(precision);\n\n  Assign(result,DivideN( Round( MultiplyN(x, 10^precision) ), 10^precision ));\n\n  BuiltinPrecisionSet(oldPrec);\n\n  result;\n};\n\n\n\n// complex numbers too\n10 # RoundTo(Complex(r_Number?, i_Number?), precision_PositiveInteger?) <-- Complex(RoundTo(r, precision), RoundTo(i, precision));\n\n\n\n\n// Infinities, rounding does not apply.\n20 # RoundTo( Infinity,precision_PositiveInteger?) <--  Infinity;\n\n20 # RoundTo(-Infinity,precision_PositiveInteger?) <-- -Infinity;\n\n\n\n/* ------   moved to separate file (already present but empty!) ---\n\nMacro(\"NumericEqual\",[_left,_right,_precision])\n[\n  Verify(RoundTo((@left)-(@right),@precision),0);\n];\n\n*/\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/testers/RoundTo.mpw, (RoundTo)";
        scriptMap.put("RoundTo",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n// print current file and line\nShowLine() :=\n{\n    Echo(\"File: \", CurrentFile(),\", Line: \", CurrentLine());\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/testers/ShowLine.mpw, (ShowLine)";
        scriptMap.put("ShowLine",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"TestEquivalent\",*);\n//Retract(\"TestTwoLists\",*);\n\nMacro(\"TestEquivalent\",[_left,_right])\n{\n    Local(leftEval,rightEval,diff,vars,isEquiv);\n    Decide(InVerboseMode(),{Tell(TestEquivalent,[left,right]);});\n    leftEval  := @left;\n    rightEval := @right;\n    Decide(InVerboseMode(),\n      { NewLine(); Tell(\"    \",leftEval); Tell(\"   \",rightEval); });\n    Decide( List?(leftEval),\n      {\n          Decide( List?(rightEval),\n            {\n                // both are lists\n                Decide(InVerboseMode(),Tell(\"     both are lists \"));\n                isEquiv := TestTwoLists(leftEval,rightEval);\n            },\n            isEquiv := False\n          );\n      },\n      {\n          Decide( List?(rightEval), \n            isEquiv := False,\n            {\n                // neither is a list, so check equality of diff\n                Decide(InVerboseMode(),Tell(\"     neither is list \"));\n                Decide(Equation?(leftEval),\n                  {\n                      Decide(Equation?(rightEval),\n                        {\n                            Decide(InVerboseMode(),Tell(\"      both are equations\"));\n                            Local(dLHs,dRHS);\n                            dLHS := Simplify(EquationLeft(leftEval) - EquationLeft(rightEval));\n                            dRHS := Simplify(EquationRight(leftEval) - EquationRight(rightEval));\n                            Decide(InVerboseMode(),Tell(\"      \",[dLHS,dRHS]));\n                            isEquiv := dLHS=?0 And? dRHS=?0;\n                        },\n                        isEquiv := False\n                      );\n                  },\n                  {\n                     Decide(Equation?(rightEval),\n                        isEquiv := False,\n                        {\n                            Decide(InVerboseMode(),Tell(\"      neither is equation\"));\n                            diff := Simplify(leftEval - rightEval);\n                            vars := VarList(diff);\n                            Decide(InVerboseMode(),\n                              {\n                                 Tell(\"    \",[leftEval,rightEval]);\n                                 Tell(\"    \",vars);\n                                 Tell(\"    \",diff);\n                              }\n                            );\n                            isEquiv   := ( Zero?(diff) Or? ZeroVector?(diff) );\n                        }\n                      );\n                   }\n                );\n            }\n          );\n      }\n    );\n    Decide(InVerboseMode(),Tell(\"     Equivalence = \",isEquiv));\n    Decide( Not? isEquiv,\n      {\n                  WriteString(\"******************\");          NewLine();\n                  WriteString(\"L.H.S. evaluates to: \");\n                  Write(leftEval);                            NewLine();\n                  WriteString(\"which differs from   \");\n                  Write(rightEval);                           NewLine();\n                  WriteString(\" by                  \"); \n                  Write(diff);                                NewLine();\n                  WriteString(\"******************\");          NewLine();\n      }\n    );\n    isEquiv;\n};\n\n\n10 # TestTwoLists( L1_List?, L2_List? ) <--\n{\n    Decide(InVerboseMode(),{Tell(\"   TestTwoLists\");Tell(\"     \",L1);Tell(\"     \",L2);});\n    Decide(Length(L1)=?1 And? Length(L2)=?1,\n      {\n          TestEquivalent(L1[1],L2[1]);\n      },\n      {\n          EqualAsSets(L1,L2);\n      }\n    );\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/testers/TestEquivalent.mpw, (TestEquivalent;TestTwoLists)";
        scriptMap.put("TestEquivalent",scriptString);
        scriptMap.put("TestTwoLists",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Testing MathPiper functionality by checking expressions against correct\n   answer.\n   Use with algebraic expressions only, since we need Simplify() for that to work.\n */\n\n/*\nMacro (\"TestMathPiper\", [_expr, _ans])\n{\n        Local(diff,exprEval, ansEval);\n  exprEval:= @expr;\n  ansEval:= @ans;\n\n        diff := Simplify(exprEval - ansEval);\n                Decide(Simplify(diff)=0, True,\n                        {\n                          WriteString(\"******************\");\n                          NewLine();\n                          ShowLine();\n                          Write(Hold(@expr));\n                          WriteString(\" evaluates to \");\n                          NewLine();\n                          Write(exprEval);\n                          NewLine();\n                          WriteString(\" which differs from \");\n                          NewLine();\n                          Write(ansEval);\n                          NewLine();\n                          WriteString(\" by \");\n                          NewLine();\n                          Write(diff);\n                          NewLine();\n                          WriteString(\"******************\");\n                          NewLine();\n                          False;\n                         }\n                        );\n};\n*/\n\n\n\nFunction (\"TestMathPiper\", [_expr, _ans])\n{\n        Local(diff);\n        diff := Simplify(Eval(expr)-Eval(ans));\n                Decide(Simplify(diff)=?0, True,\n                        {\n                          WriteString(\"******************\");\n                          NewLine();\n                          ShowLine();\n                          Write(expr);\n                          WriteString(\" evaluates to \");\n                          NewLine();\n                          Write(Eval(expr));\n                          NewLine();\n                          WriteString(\" which differs from \");\n                          NewLine();\n                          Write(Eval(ans));\n                          NewLine();\n                          WriteString(\" by \");\n                          NewLine();\n                          Write(diff);\n                          NewLine();\n                          WriteString(\"******************\");\n                          NewLine();\n                          False;\n                         }\n                        );\n};\n\nHoldArgument(\"TestMathPiper\", \"expr\");\nHoldArgument(\"TestMathPiper\", \"ans\");\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/testers/TestMathPiper.mpw, (TestMathPiper)";
        scriptMap.put("TestMathPiper",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"Testing\",[_aLeft])\n{\n    WriteString(\"--\");\n    WriteString(aLeft);\n    NewLine();\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/testers/Testing.mpw, (Testing)";
        scriptMap.put("Testing",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/*\nMacro(\"Verify\",[_aLeft,_aRight])\n{\n\n        Local(result);\n        result := @aLeft;        // to save time\n  If(Not?(Equal?(result,@aRight)),\n    {\n      WriteString(\"******************\");\n      NewLine();\n      ShowLine();\n      NewLine();\n      Write(Hold(@aLeft));\n      NewLine();\n      WriteString(\" evaluates to \");\n      NewLine();\n          Write(result);\n          WriteString(\" which differs from \");\n      NewLine();\n      Write(Hold(@aRight));\n      NewLine();\n      WriteString(\"******************\");\n      NewLine();\n      False;\n    },\n    True\n  );\n};\n*/\n\n\nFunction(\"Verify\",[_aLeft,_aRight])\n{\n\n        Local(result);\n        result := Eval(aLeft);        // to save time\n  Decide(Not?(Equal?(result,aRight)),\n    {\n      WriteString(\"******************\");\n      NewLine();\n      ShowLine();\n      NewLine();\n      Write(aLeft);\n      NewLine();\n      WriteString(\" evaluates to \");\n      NewLine();\n          Write(result);\n      NewLine();\n          WriteString(\" which differs from \");\n      NewLine();\n      Write(aRight);\n      NewLine();\n      WriteString(\"******************\");\n      NewLine();\n      False;\n    },\n    True\n  );\n};\nHoldArgument(\"Verify\",\"aLeft\");\nUnFence(\"Verify\",2);\n/*\nHoldArgument(\"Verify\",\"aRight\");\n*/\n\nMacro(\"Verify\", [_a,_b,_message])\n{\n        Echo(\"test \", @message);\n        Verify(@a, @b);\n};\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/testers/Verify.mpw, (Verify)";
        scriptMap.put("Verify",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nLocalSymbols(f1,f2)\n{\n  // f1 and f2 are used inside VerifyArithmetic\n  f1(x,n,m):=(x^n-1)*(x^m-1);\n  f2(x,n,m):=x^(n+m)-(x^n)-(x^m)+1;\n\n  VerifyArithmetic(x,n,m):=\n  {\n    Verify(f1(x,n,m),f2(x,n,m));\n  };\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/testers/VerifyArithmetic.mpw, (VerifyArithmetic)";
        scriptMap.put("VerifyArithmetic",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nVerifyDiv(_u,_v) <--\n{\n  Local(q,r);\n  q:=Quotient(u,v);\n  r:=Rem(u,v);\n\n  Verify(Expand(u),Expand(q*v+r));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/testers/VerifyDiv.mpw, (VerifyDiv)";
        scriptMap.put("VerifyDiv",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"VerifySolve\",*);\n//Retract(\"VerifySolveEqual\",*);\n\nVerifySolve(_e1, _e2) <--\nDecide(VerifySolveEqual(Eval(e1), Eval(e2)), \n    True,\n    { \n      WriteString(\"******************\");    NewLine();\n      ShowLine();                           NewLine();\n      Write(e1);                            NewLine();\n      WriteString(\" evaluates to \");        NewLine();\n      Write(Eval(e1));                      NewLine();\n      WriteString(\" which differs from \");  NewLine();\n      Write(e2);                            NewLine();\n      WriteString(\"******************\");    NewLine();\n      False; \n    }); \nHoldArgumentNumber(\"VerifySolve\", 2, 1);\n\n10 # VerifySolveEqual([], []) <-- True;\n\n20 # VerifySolveEqual([], e2_List?) <-- False;\n\n30 # VerifySolveEqual(e1_List?, e2_List?) <--\n{\n  Local(i, found);\n  found := False;\n  i := 0;\n  While(i <? Length(e2) And? Not? found) {\n    i++;\n    found := VerifySolveEqual(First(e1), e2[i]);\n  };\n  Decide(found, VerifySolveEqual(Rest(e1), Delete(e2, i)), False);\n};\n\n40 # VerifySolveEqual(_l1 == _r1, _l2 == _r2) \n<-- Equal?(l1,l2) And? Simplify(r1-r2)=?0;\n\n50 # VerifySolveEqual(_e1, _e2) <-- Simplify(e1-e2) =? 0;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/testers/VerifySolve.mpw, (VerifySolve;VerifySolveEqual)";
        scriptMap.put("VerifySolve",scriptString);
        scriptMap.put("VerifySolveEqual",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # BigOh(UniVariate(_var,_first,_coefs),_var,_degree) <--\n    {\n     While(first+Length(coefs)>=?(degree+1) And? Length(coefs)>?0) DestructiveDelete(coefs,Length(coefs));\n     UniVariate(var,first,coefs);\n    };\n20 # BigOh(_uv,_var,_degree)::CanBeUni(uv,var) <-- NormalForm(BigOh(MakeUni(uv,var),var,degree));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/BigOh.mpw, (BigOh)";
        scriptMap.put("BigOh",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* CanBeUni returns whether the function can be converted to a\n * univariate, with respect to a variable.\n */\nFunction(\"CanBeUni\",[_expression]) CanBeUni(UniVarList(expression),expression);\n\n\n/* Accepting an expression as being convertable to univariate */\n\n/* Dealing wiht a list of variables. The poly should be expandable\n * to each of these variables (smells like tail recursion)\n */\n10 # CanBeUni([],_expression) <-- True;\n20 # CanBeUni(var_List?,_expression) <--\n    CanBeUni(First(var),expression) And? CanBeUni(Rest(var),expression);\n\n/* Atom can always be a polynom to any variable */\n30 # CanBeUni(_var,expression_Atom?) <-- True;\n35 # CanBeUni(_var,expression_FreeOf?(var)) <-- True;\n\n/* Other patterns supported. */\n40 # CanBeUni(_var,_x + _y) <-- CanBeUni(var,x) And? CanBeUni(var,y);\n40 # CanBeUni(_var,_x - _y) <-- CanBeUni(var,x) And? CanBeUni(var,y);\n40 # CanBeUni(_var,   + _y) <-- CanBeUni(var,y);\n40 # CanBeUni(_var,   - _y) <-- CanBeUni(var,y);\n40 # CanBeUni(_var,_x * _y) <-- CanBeUni(var,x) And? CanBeUni(var,y);\n40 # CanBeUni(_var,_x / _y) <-- CanBeUni(var,x) And? FreeOf?(var,y);\n/* Special case again: raising powers */\n40 # CanBeUni(_var,_x ^ y_Integer?)::(y >=? 0 And? CanBeUni(var,x)) <-- True;\n41 # CanBeUni(_var,(x_FreeOf?(var)) ^ (y_FreeOf?(var))) <-- True;\n50 # CanBeUni(_var,UniVariate(_var,_first,_coefs)) <-- True;\n1000 # CanBeUni(_var,_f)::(Not?(FreeOf?(var,f))) <-- False;\n1001 # CanBeUni(_var,_f) <-- True;\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/CanBeUni.mpw, (CanBeUni)";
        scriptMap.put("CanBeUni",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n5 # Coef(uv_UniVar?,order_List?) <--\n{\n  Local(result);\n  result:=[];\n  ForEach(item,order)\n  {\n    DestructiveAppend(result,Coef(uv,item));\n  };\n  result;\n};\n\n10 # Coef(uv_UniVar?,order_Integer?)::(order<?uv[2]) <-- 0;\n10 # Coef(uv_UniVar?,order_Integer?)::(order>=?uv[2]+Length(uv[3])) <-- 0;\n20 # Coef(uv_UniVar?,order_Integer?) <-- uv[3][(order-uv[2])+1];\n30 # Coef(uv_CanBeUni,_order)::(Integer?(order) Or? List?(order)) <-- Coef(MakeUni(uv),order);\n\nFunction(\"Coef\",[_expression,_var,_order])\n    NormalForm(Coef(MakeUni(expression,var),order));\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/Coef.mpw, (Coef)";
        scriptMap.put("Coef",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//:::  Collect terms of a polynomial-like expression on powers of var,\n//     starting with power 0.\n\n//Retract(\"CollectOn\",*);\n\n10 # CollectOn(_var,_expr)::(CanBeUni(var,expr)) <--\n{\n    Decide(InVerboseMode(),Echo(\"<< Collect on:  \",var,\" in expression \",expr));\n    \n    Local(u,a);\n    u := MakeUni(expr,var);\n    Decide( u[2] >? 0, \n      { a := FillList(0,u[2]); u[3] := Concat(a,u[3]); u[2] := 0; }\n    );\n    u[3];\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/CollectOn.mpw, (CollectOn)";
        scriptMap.put("CollectOn",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # Content(UniVariate(_var,_first,_coefs)) <-- Gcd(coefs)*var^first;\n20 # Content(poly_CanBeUni) <-- NormalForm(Content(MakeUni(poly)));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/Content.mpw, (Content)";
        scriptMap.put("Content",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"Degree\",*);\n\nRulebaseHoldArguments(\"Degree\",[_expr]);\nRuleHoldArguments(\"Degree\",1,0, UniVar?(expr))\n{\n\n  Local(i,min,max);\n  min:=expr[2];\n  max:=min+Length(expr[3]);\n  i:=max;\n  While(i >=? min And? Zero?(Coef(expr,i))) i--;\n  i;\n};\n\n10 # Degree(poly_CanBeUni)                           <-- Degree(MakeUni(poly));\n\n10 # Degree(_poly,_var)::(CanBeUni(var,poly))         <-- Degree(MakeUni(poly,var));\n\n20 # Degree(_poly,_var)::(Type(poly)=?\"Sqrt\")          <-- Degree(poly^2,var)/2;\n\n20 # Degree(_poly,_var)::(FunctionToList(poly)[1]=? ^) <--\n{\n    Local(ex,pwr,deg);\n    ex  := FunctionToList(poly)[3];\n    pwr := 1/ex;\n    //Tell(\"     \",[ex,pwr]);\n    deg := Degree(poly^pwr,var);\n    //Tell(\"     \",deg);\n    deg*ex;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/Degree.mpw, (Degree)";
        scriptMap.put("Degree",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nDivPoly(_A,_B,_var,_deg) <--\n{\n  Local(a,b,c,i,j,denom);\n  b:=MakeUni(B,var);\n  denom:=Coef(b,0);\n\n  If(denom =? 0)\n  {\n    Local(f);\n    f:=Content(b);\n    b:=PrimitivePart(b);\n    A:=Simplify(A/f);\n    denom:=Coef(b,0);\n  };\n  a:=MakeUni(A,var);\n\n  c:=FillList(0,deg+1);\n  For(i:=0,i<=?deg,i++)\n  {\n    Local(sum,j);\n    sum:=0;\n    For(j:=0,j<?i,j++)\n    {\n      sum := sum + c[j+1]*Coef(b,i-j);\n    };\n    c[i+1] := (Coef(a,i)-sum) / denom;\n  };\n  NormalForm(UniVariate(var,0,c));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/DivPoly.mpw, (DivPoly)";
        scriptMap.put("DivPoly",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nDropEndZeroes(list):=\n{\n  Local(end);\n  end:=Length(list);\n  While(list[end] =? 0)\n  {\n    DestructiveDelete(list,end);\n    end:=end-1;\n  };\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/DropEndZeroes.mpw, (DropEndZeroes)";
        scriptMap.put("DropEndZeroes",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"ExpandUniVariate\",[_var,_first,_coefs])\n{\n  Local(result,i);\n  result:=0;\n  For(i:=Length(coefs),i>?0,i--)\n  {\n    Local(term);\n    term:=NormalForm(coefs[i])*var^(first+i-1);\n    result:=result+term;\n  };\n  result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/ExpandUniVariate.mpw, (ExpandUniVariate)";
        scriptMap.put("ExpandUniVariate",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nHorner(_e,_v) <--\n{\n  Local(uni,coefs,result);\n  uni := MakeUni(e,v);\n  coefs:=DestructiveReverse(uni[3]);\n  result:=0;\n\n  While(coefs !=? [])\n  {\n    result := result*v;\n    result := result+First(coefs);\n    coefs  := Rest(coefs);\n  };\n  result:=result*v^uni[2];\n  result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/Horner.mpw, (Horner)";
        scriptMap.put("Horner",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//todo:tk:moved here form Coef.mpw.\n10 # LeadingCoef(uv_UniVar?) <-- Coef(uv,Degree(uv));\n\n20 # LeadingCoef(uv_CanBeUni) <--\n{\n  Local(uvi);\n  uvi:=MakeUni(uv);\n  Coef(uvi,Degree(uvi));\n};\n10 # LeadingCoef(uv_CanBeUni(var),_var) <--\n{\n  Local(uvi);\n  uvi:=MakeUni(uv,var);\n  Coef(uvi,var,Degree(uvi));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/LeadingCoef.mpw, (LeadingCoef)";
        scriptMap.put("LeadingCoef",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"MakeUni\",[_expression]) MakeUni(expression,UniVarList(expression));\n\n/* Convert normal form to univariate expression */\nRulebaseHoldArguments(\"MakeUni\",[_expression,_var]);\n\n5 # MakeUni(_expr,[]) <-- UniVariate(dummyvar,0,[expression]);\n6 # MakeUni(_expr,var_List?) <--\n{\n  Local(result,item);\n  result:=expression;\n  ForEach(item,var)\n  {\n    result:=MakeUni(result,item);\n  };\n  result;\n};\n\n10 # MakeUni(UniVariate(_var,_first,_coefs),_var) <--\n    UniVariate(var,first,coefs);\n\n20 # MakeUni(UniVariate(_v,_first,_coefs),_var) <--\n{\n  Local(reslist,item);\n  reslist:=[];\n  ForEach(item,expression[3])\n  {\n    Decide(FreeOf?(var,item),\n      DestructiveAppend(reslist,item),\n      DestructiveAppend(reslist,MakeUni(item,var))\n      );\n  };\n  UniVariate(expression[1],expression[2],reslist);\n};\n\n\nLocalSymbols(a,b,var,expression)\n{\n  20 # MakeUni(expression_FreeOf?(var),_var)\n       <-- UniVariate(var,0,[expression]);\n  30 # MakeUni(_var,_var) <-- UniVariate(var,1,[1]);\n  30 # MakeUni(_a + _b,_var) <-- MakeUni(a,var) + MakeUni(b,var);\n  30 # MakeUni(_a - _b,_var) <-- MakeUni(a,var) - MakeUni(b,var);\n  30 # MakeUni(   - _b,_var) <--                - MakeUni(b,var);\n  30 # MakeUni(_a * _b,_var) <-- MakeUni(a,var) * MakeUni(b,var);\n  1 # MakeUni(_a ^ n_Integer?,_var) <-- MakeUni(a,var) ^ n;\n  30 # MakeUni(_a / (b_FreeOf?(var)),_var) <-- MakeUni(a,var) * (1/b);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/MakeUni.mpw, (MakeUni)";
        scriptMap.put("MakeUni",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # Monic(UniVariate(_var,_first,_coefs)) <--\n{\n  DropEndZeroes(coefs);\n  UniVariate(var,first,coefs/coefs[Length(coefs)]);\n};\n20 # Monic(poly_CanBeUni) <-- NormalForm(Monic(MakeUni(poly)));\n\n30 # Monic(_poly,_var)::CanBeUni(poly,var) <-- NormalForm(Monic(MakeUni(poly,var)));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/Monic.mpw, (Monic)";
        scriptMap.put("Monic",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # PrimitivePart(UniVariate(_var,_first,_coefs)) <--\n        UniVariate(var,0,coefs/Gcd(coefs));\n20 # PrimitivePart(poly_CanBeUni) <-- NormalForm(PrimitivePart(MakeUni(poly)));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/PrimitivePart.mpw, (PrimitivePart)";
        scriptMap.put("PrimitivePart",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* Repeated squares multiplication\n TODO put somewhere Else!!!\n */\n10 # RepeatedSquaresMultiply(_a,- (n_Integer?)) <-- 1/RepeatedSquaresMultiply(a,n);\n\n15 #  RepeatedSquaresMultiply(UniVariate(_var,_first,[_coef]),(n_Integer?)) <--\n      UniVariate(var,first*n,[coef^n]);\n20 # RepeatedSquaresMultiply(_a,n_Integer?) <--\n{\n  Local(m,b);\n  Assign(m,1);\n  Assign(b,1);\n  While(m<=?n) Assign(m,(ShiftLeft(m,1)));\n  Assign(m, ShiftRight(m,1));\n  While(m>?0)\n  {\n    Assign(b,b*b);\n    Decide(Not?(Equal?(BitAnd(m,n), 0)),Assign(b,b*a));\n    Assign(m, ShiftRight(m,1));\n  };\n  b;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/RepeatedSquaresMultiply.mpw, (RepeatedSquaresMultiply)";
        scriptMap.put("RepeatedSquaresMultiply",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* division algo: (for zero-base univariates:) */\nFunction(\"UniDivide\",[_u,_v])\n{\n  Local(m,n,q,r,k,j);\n  m := Length(u)-1;\n  n := Length(v)-1;\n  While (m>?0 And? Zero?(u[m+1])) m--;\n  While (n>?0 And? Zero?(v[n+1])) n--;\n  q := ZeroVector(m-n+1);\n  r := FlatCopy(u);  /*  (m should be >= n) */\n  For(k:=m-n,k>=?0,k--)\n  {\n    q[k+1] := r[n+k+1]/v[n+1];\n    For (j:=n+k-1,j>=?k,j--)\n    {\n      r[j+1] := r[j+1] - q[k+1]*v[j-k+1];\n    };\n  };\n  Local(end);\n  end:=Length(r);\n  While (end>?n)\n  {\n    DestructiveDelete(r,end);\n    end:=end-1;\n  };\n\n  [q,r];\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/UniDivide.mpw, (UniDivide)";
        scriptMap.put("UniDivide",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"UniGcd\",[_u,_v])\n{\n  Local(l,div,mod,m);\n\n  DropEndZeroes(u);\n  DropEndZeroes(v);\n/*\n  Decide(Length(v)>?Length(u),\n    [\n      Locap(swap);\n      swap:=u;\n      u:=v;\n      v:=swap;\n    ] );\n  Decide(Length(u)=Length(v) And? v[Length(v)] >? u[Length(u)],\n    [\n      Locap(swap);\n      swap:=u;\n      u:=v;\n      v:=swap;\n    ] );\n  */\n\n\n  l:=UniDivide(u,v);\n\n  div:=l[1];\n  mod:=l[2];\n\n  DropEndZeroes(mod);\n  m := Length(mod);\n\n/* Echo([\"v,mod = \",v,mod]); */\n/*  Decide(m <=? 1, */\n  Decide(m =? 0,\n     v,\n/*     v/v[Length(v)], */\n     UniGcd(v,mod));\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/UniGCD.mpw, (UniGcd)";
        scriptMap.put("UniGcd",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"UniTaylor\",[_taylorfunction,_taylorvariable,_taylorat,_taylororder])\n{\n  Local(n,result,dif,polf);\n  result:=[];\n  {\n    MacroLocal(taylorvariable);\n    MacroAssign(taylorvariable,taylorat);\n    DestructiveAppend(result,Eval(taylorfunction));\n  };\n  dif:=taylorfunction;\n  polf:=(taylorvariable-taylorat);\n  For(n:=1,n<=?taylororder,n++)\n  {\n    dif:= Deriv(taylorvariable) dif;\n    MacroLocal(taylorvariable);\n    MacroAssign(taylorvariable,taylorat);\n    DestructiveAppend(result,(Eval(dif)/n!));\n  };\n  UniVariate(taylorvariable,0,result);\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/UniTaylor.mpw, (UniTaylor)";
        scriptMap.put("UniTaylor",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Note:tk:since this is used in more than one univariate function, I am publishing it as a def.\n\nUniVarList(expr) := VarList(expr);\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/UniVarList.mpw, (UniVarList)";
        scriptMap.put("UniVarList",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n10 # UniVar?(UniVariate(_var,_first,_coefs)) <-- True;\n20 # UniVar?(_anything) <-- False;\n\n200 # aLeft_UniVar? ^ aRight_PositiveInteger? <--\n      RepeatedSquaresMultiply(aLeft,aRight);\n\n\n200 # aLeft_UniVar? - aRight_UniVar? <--\n{\n  Local(from,result);\n  Local(curl,curr,left,right);\n\n  curl:=aLeft[2];\n  curr:=aRight[2];\n  left:=aLeft[3];\n  right:=aRight[3];\n  result:=[];\n  from:=Minimum(curl,curr);\n\n  While(curl<?curr And? left !=? [])\n  {\n    DestructiveAppend(result,First(left));\n    left:=Rest(left);\n    curl++;\n  };\n  While(curl<?curr)\n  {\n    DestructiveAppend(result,0);\n    curl++;\n  };\n  While(curr<?curl And? right !=? [])\n  {\n    DestructiveAppend(result,-First(right));\n    right:=Rest(right);\n    curr++;\n  };\n  While(curr<?curl)\n  {\n    DestructiveAppend(result,0);\n    curr++;\n  };\n  While(left !=? [] And? right !=? [])\n  {\n    DestructiveAppend(result,First(left)-First(right));\n    left  := Rest(left);\n    right := Rest(right);\n  };\n\n\n  While(left !=? [])\n  {\n    DestructiveAppend(result,First(left));\n    left  := Rest(left);\n  };\n  While(right !=? [])\n  {\n    DestructiveAppend(result,-First(right));\n    right := Rest(right);\n  };\n\n  UniVariate(aLeft[1],from,result);\n};\n\n\n\n/*TODO this can be made twice as fast!*/\n\n201 # (aLeft_UniVar? * _aRight)::((FreeOf?(aLeft[1],aRight))) <--\n{\n    aRight*aLeft;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/UniVar_.mpw, (UniVar?)";
        scriptMap.put("UniVar?",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Auxiliary function.\nShiftUniVar(UniVariate(_var,_first,_coefs),_fact,_shift)\n   <--\n   {\n//Echo(\"fact, coefs = \",fact,coefs);\n     UniVariate(var,first+shift,fact*coefs);\n   };\n\n\n\nRulebaseHoldArguments(\"UniVariate\",[_var,_first,_coefs]);\n\nRuleHoldArguments(\"UniVariate\",3,10,Length(coefs)>?0 And? coefs[1]=?0)\n  UniVariate(var,first+1,Rest(coefs));\nRuleHoldArguments(\"UniVariate\",3,1000,Complex?(var) Or? List?(var))\n    ExpandUniVariate(var,first,coefs);\n\n500 # UniVariate(_var,_f1,_c1) + UniVariate(_var,_f2,_c2) <--\n{\n  Local(from,result);\n  Local(curl,curr,left,right);\n\n  Assign(curl, f1);\n  Assign(curr, f2);\n  Assign(left, c1);\n  Assign(right, c2);\n  Assign(result, []);\n  Assign(from, Minimum(curl,curr));\n\n  While(And?(LessThan?(curl,curr),left !=? []))\n  {\n    DestructiveAppend(result,First(left));\n    Assign(left,Rest(left));\n    Assign(curl,AddN(curl,1));\n  };\n  While(LessThan?(curl,curr))\n  {\n    DestructiveAppend(result,0);\n    Assign(curl,AddN(curl,1));\n  };\n  While(And?(LessThan?(curr,curl), right !=? []))\n  {\n    DestructiveAppend(result,First(right));\n    Assign(right,Rest(right));\n    Assign(curr,AddN(curr,1));\n  };\n  While(LessThan?(curr,curl))\n  {\n    DestructiveAppend(result,0);\n    Assign(curr,AddN(curr,1));\n  };\n  While(And?(left !=? [], right !=? []))\n  {\n    DestructiveAppend(result,First(left)+First(right));\n    Assign(left, Rest(left));\n    Assign(right, Rest(right));\n  };\n  While(left !=? [])\n  {\n    DestructiveAppend(result,First(left));\n    Assign(left, Rest(left));\n  };\n  While(right !=? [])\n  {\n    DestructiveAppend(result,First(right));\n    Assign(right, Rest(right));\n  };\n\n  UniVariate(var,from,result);\n};\n\n\n200 # UniVariate(_var,_first,_coefs) + a_Number? <--\n      UniVariate(var,first,coefs) + UniVariate(var,0,[a]);\n200 # a_Number? + UniVariate(_var,_first,_coefs) <--\n      UniVariate(var,first,coefs) + UniVariate(var,0,[a]);\n\n\n200 # - UniVariate(_var,_first,_coefs) <-- UniVariate(var,first,-coefs);\n\n\n200 # (_factor * UniVariate(_var,_first,_coefs))::((FreeOf?(var,factor))) <--\n  UniVariate(var,first,coefs*factor);\n\n200 # (UniVariate(_var,_first,_coefs)/_factor)::((FreeOf?(var,factor))) <--\n  UniVariate(var,first,coefs/factor);\n\n\n\n200 # UniVariate(_var,_f1,_c1) * UniVariate(_var,_f2,_c2) <--\n{\n  Local(i,j,n,shifted,result);\n  Assign(result,MakeUni(0,var));\n//Echo(\"c1 = \",var,f1,c1);\n//Echo(\"c2 = \",var,f2,c2);\n  Assign(n,Length(c1));\n  For(i:=1,i<=?n,i++)\n  {\n//Echo(\"before = \",result);\n//Echo(\"parms = \",var,c1,c2,f1,f2,f1+i-1);\n    Assign(result,result+ShiftUniVar(UniVariate(var,f2,c2),MathNth(c1,i),f1+i-1));\n//Echo(\"after = \",result);\n  };\n//Echo(\"result = \",result);\n  result;\n};\n\n\n\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/UniVariate.mpw, (UniVariate;ShiftUniVar)";
        scriptMap.put("UniVariate",scriptString);
        scriptMap.put("ShiftUniVar",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/*\nNote:tk:I am publishing this function as a def because\nbut it seems like it was meant to be a published function.\n*/\n\n// Add a term into a termlist: this function assumes that\n//  1) the list of terms is sorted in decreasing order of exponents\n//  2) there are not two terms with the same exponent.\n//  3) There is no term with cero coefficient\n// This assumptions are preserved.\n\n// The parameter begining tell us where to begin the search\n// (it is used for increasing the efficency of the algorithms!)\n// The function returns the position at which the new term is added plus 1.\n// (to be used as begining for sucesive AddTerm calls\n\nFunction(\"AddTerm\",[_termlist,_term,_begining])\n{\n Local(l,i);\n l := Length(termlist);\n Decide(term[2]!=?0,\n {\n  i:=begining;\n// Fix-me: search by using binary search ?\n  Decide(l>=?1, While ((i<=?l) And? (term[1]<?termlist[i][1])) i++);\n  Decide(i>?l, {DestructiveAppend(termlist,term);i++;},\n          Decide(term[1]=?termlist[i][1],\n             { Local(nc);\n               nc:=termlist[i][2]+term[2];\n                 Decide(nc!=?0,DestructiveReplace(termlist,i,[term[1],nc]),\n                          {DestructiveDelete(termlist,i);i--;});\n             },  DestructiveInsert(termlist,i,term))\n     );\n }\n  );\n i+1;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/sparse/AddTerm.mpw, (AddTerm)";
        scriptMap.put("AddTerm",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/*\nNote:tk:I am publishing this function as a def because\nbut it seems like it was meant to be a published function.\n*/\n\nFunction(\"AddTerms\",[_terms1,_terms2])\n{\n  Local(result,begining,t);\n  begining :=1;\n  ForEach (t,terms2)\n     begining :=AddTerm(terms1,t,begining);\n  terms1;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/sparse/AddTerms.mpw, (AddTerms)";
        scriptMap.put("AddTerms",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n// Implements the division of polynomials!\n\nFunction(\"DivTermList\",[_a,_b])\n{\n Local(q,nq,t,c,begining);\n q := [];\n // a[1][1] is the degree of a, b[1][1] is the degree of b\n While ((a!=?[]) And? a[1][1]>=?b[1][1])\n  {\n     begining := 1;\n     Decide(InVerboseMode(),Echo(\"degree=\",a[1][1]));\n     nq := [a[1][1]-b[1][1],a[1][2]/b[1][2]]; // a new term of the quotient\n     DestructiveAppend(q,nq);\n     // We compute a:= a - nq* b\n     ForEach (t,b)\n       begining := AddTerm(a,[t[1]+nq[1],-t[2]*nq[2]],begining);\n   };\n   // a is the rest at the end\n q;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/sparse/DivTermList.mpw, (DivTermList)";
        scriptMap.put("DivTermList",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"ExpandSparseUniVar\",[_s])\n{\n Local(result,t,var,termlist);\n result :=0;\n var := s[1];\n termlist := s[2];\n ForEach (t,termlist)\n {\n   Local(term);\n   term := NormalForm(t[2]*var^t[1]);\n   result := result + term;\n };\n result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/sparse/ExpandSparseUniVar.mpw, (ExpandSparseUniVar)";
        scriptMap.put("ExpandSparseUniVar",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n//Retract(\"MakeSparseUniVar\",*);\n\n10 # MakeSparseUniVar(poly_CanBeUni,var_Atom?) <--\n{\n    Decide(InVerboseMode(),Tell(\"MakeSparseUniVar\",[var,poly]));\n    Local(uni,first,coeffs,n,c,lc,termlist,term);\n    uni    := MakeUni(poly,var);\n    Decide(InVerboseMode(),Tell(\"      \",uni));\n    first  := uni[2];\n    coeffs := (uni[3]);\n    Decide(InVerboseMode(),{Tell(\"    \",first); Tell(\"   \",coeffs);});\n    termlist := [];\n    lc       := Length(coeffs);\n    For(n:=0,n<?lc,n++)\n    {\n        c    := coeffs[n+1];\n        term := [n+first,c];\n        Decide(InVerboseMode(),Tell(\"        \",term));\n        Decide(c !=? 0, Push(termlist,term));\n    };\n    Decide(InVerboseMode(),Tell(\"   \",[var,termlist]));\n    [var,termlist];\n};\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/sparse/MakeSparseUniVar.mpw, (MakeSparseUniVar)";
        scriptMap.put("MakeSparseUniVar",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/*\nNote:tk:I am publishing this function as a def because\nbut it seems like it was meant to be a published function.\n*/\n\n// Multiply a list of terms by a Single term.\n\nFunction(\"MultiplySingleTerm\",[_termlist,_term])\n{\n Local(result,t);\n result:=[];\n Decide(term[2]!=?0,\n       ForEach (t,termlist)\n         DestructiveAppend(result,[t[1]+term[1],t[2]*term[2]]) );\n result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/sparse/MultiplySingleTerm.mpw, (MultiplySingleTerm)";
        scriptMap.put("MultiplySingleTerm",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\nFunction(\"MultiplyTerms\",[_terms1,_terms2])\n{\n Local(result,t1,t2,begining);\n result:=[];\n ForEach (t1,terms1)\n {\n   begining :=1;\n   ForEach (t2,terms2)\n     begining := AddTerm(result,[t1[1]+t2[1],t1[2]*t2[2]],1);\n };\n result;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/sparse/MultiplyTerms.mpw, (MultiplyTerms)";
        scriptMap.put("MultiplyTerms",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/*\nNote:tk:I am publishing this function as a def because\nbut it seems like it was meant to be a published function.\n*/\n\n// SparseUniVariate(variable,termlist) implements an internal representation\n// for univariate polynomials\n// termlist is the list of terms in the form [exponent,coeficient]\n\nRulebaseHoldArguments(\"SparseUniVar\",[_var,_termlist]);\n\n300 # SparseUniVar(_var,_terms1) * SparseUniVar(_var,_terms2) <--\nSparseUniVar(var, MultiplyTerms(terms1,terms2));\n\n300 # SparseUniVar(_var,_terms1) + SparseUniVar(_var,_terms2) <--\nSparseUniVar(var, AddTerms(terms1,terms2));\n\n300 # SparseUniVar(_var,_terms1) - SparseUniVar(_var,_terms2) <--\nSparseUniVar(var, SubstractTerms(terms1,terms2));\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/sparse/SparseUniVar.mpw, (SparseUniVar)";
        scriptMap.put("SparseUniVar",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/*\nNote:tk:I am publishing this function as a def because\nbut it seems like it was meant to be a published function.\n*/\n\nFunction(\"SubtractTerms\",[_terms1,_terms2])\n{\n  Local(result,t);\n  begining :=1 ;\n  ForEach (t,terms2)\n     begining := AddTerm(terms1,[t[1],-t[2]],1);\n  terms1;\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/univar/sparse/SubtractTerms.mpw, (SubtractTerms)";
        scriptMap.put("SubtractTerms",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* def file definitions\nEvalFormula\n*/\n\n\n/*\nTODO:\n- Func(a=b) prematurely evaluates a=b\n- clean up the code!\n  - document the code!!!\n- prefix/postfix currently not used!!!\n- some rules for rendering the formula are slooooww....\n\n- bin, derivative, sqrt, integral, summation, limits,\n      ___\n     / a |\n \\  /  -\n  \\/   b\n\n   /\n   |\n   |\n   |\n   /\n\n  d\n --- f( x )\n d x\n\n   2\n  d\n ----  f( x )\n    2\n d x\n\n  Infinity\n    ___\n    \\\n     \\    n\n     /   x\n    /__\n    n = 0\n                 Sin(x)\n     lim         ------\n x -> Infinity    x\n\n\n\n*/\n\n/*\nNLog(str):=\n[\n  WriteString(str);\n  NewLine();\n];\n*/\n\n\n\n\nCharList(length,item):=\n{\n  Local(line,i);\n  line:=\"\";\n  For(Assign(i,0),LessThan?(i,length),Assign(i,AddN(i,1)))\n    Assign(line, line~item);\n  line;\n};\n\n\n\n\nCharField(width,height) := ArrayCreate(height,CharList(width,\" \"));\n\n\n\n\nWriteCharField(charfield):=\n{\n  Local(i,len);\n  len:=Length(charfield);\n  For(Assign(i,1),i<=?len,Assign(i,AddN(i,1)))\n  {\n    WriteString(charfield[i]);\n    NewLine();\n  };\n  True;\n};\n\n\n\n\nColumnFilled(charfield,column):=\n{\n  Local(i,result,len);\n  result:=False;\n  len:=Length(charfield);\n  For(Assign(i, 1),(result =? False) And? (i<=?len),Assign(i,AddN(i,1)))\n  {\n    Decide(StringMidGet(column,1,charfield[i]) !=? \" \",result:=True);\n  };\n  result;\n};\n\n\n\n\nWriteCharField(charfield,width):=\n{\n  Local(pos,length,len);\n  Assign(length, Length(charfield[1]));\n  Assign(pos, 1);\n  While(pos<=?length)\n  {\n    Local(i,thiswidth);\n    Assign(thiswidth, width);\n    Decide(thiswidth>?(length-pos)+1,\n      {\n        Assign(thiswidth, AddN(SubtractN(length,pos),1));\n      },\n      {\n        While (thiswidth>?1 And? ColumnFilled(charfield,pos+thiswidth-1))\n        {\n          Assign(thiswidth,SubtractN(thiswidth,1));\n        };\n        Decide(thiswidth =? 1, Assign(thiswidth, width));\n      }\n    );\n    len:=Length(charfield);\n    For(Assign(i, 1),i<=?len,Assign(i,AddN(i,1)))\n    {\n      WriteString(StringMidGet(pos,thiswidth,charfield[i]));\n      NewLine();\n    };\n    Assign(pos, AddN(pos, thiswidth));\n    NewLine();\n  };\n  True;\n};\n\n\n\n\nPutString(charfield,x,y,string):=\n{\n  cf[y] := StringMidSet(x,string,cf[y]);\n  True;\n};\n\n\n\n\nMakeOper(x,y,width,height,oper,args,base):=\n{\n  Local(result);\n  Assign(result,ArrayCreate(7,0));\n  ArraySet(result,1,x);\n  ArraySet(result,2,y);\n  ArraySet(result,3,width);\n  ArraySet(result,4,height);\n  ArraySet(result,5,oper);\n  ArraySet(result,6,args);\n  ArraySet(result,7,base);\n  result;\n};\n\n\n\n\nMoveOper(f,x,y):=\n{\n  f[1]:=AddN(f[1], x); /* move x */\n  f[2]:=AddN(f[2], y); /* move y */\n  f[7]:=AddN(f[7], y); /* move base */\n};\n\n\n\n\nAlignBase(i1,i2):=\n{\n  Local(base);\n  Assign(base, Maximum(i1[7],i2[7]));\n  MoveOper(i1,0,SubtractN(base,(i1[7])));\n  MoveOper(i2,0,SubtractN(base,(i2[7])));\n};\n\n\n\n\n10 # BuildArgs([]) <-- Formula(ToAtom(\" \"));\n\n\n\n\n20 # BuildArgs([_head]) <-- head;\n\n\n\n\n30 # BuildArgs(_any)    <--\n     {\n        Local(item1,item2,comma,base,newitem);\n        Assign(item1, any[1]);\n        Assign(item2, any[2]);\n        Assign(comma, Formula(ToAtom(\",\")));\n        Assign(base, Maximum(item1[7],item2[7]));\n        MoveOper(item1,0,SubtractN(base,(item1[7])));\n        MoveOper(comma,AddN(item1[3],1),base);\n\n        MoveOper(item2,comma[1]+comma[3]+1,SubtractN(base,(item2[7])));\n        Assign(newitem, MakeOper(0,0,AddN(item2[1],item2[3]),Maximum(item1[4],item2[4]),\"Func\",[item1,comma,item2],base));\n        BuildArgs(newitem~Rest(Rest(any)));\n      };\n\n\n\n\nFormulaBracket(f):=\n{\n  Local(left,right);\n  Assign(left, Formula(ToAtom(\"(\")));\n  Assign(right, Formula(ToAtom(\")\")));\n  left[4]:=f[4];\n  right[4]:=f[4];\n  MoveOper(left,f[1],f[2]);\n  MoveOper(f,2,0);\n  MoveOper(right,f[1]+f[3]+1,f[2]);\n  MakeOper(0,0,right[1]+right[3],f[4],\"Func\",[left,f,right],f[7]);\n};\n\n\n\n\n/* RulebaseHoldArguments(\"Formula\",[_f]); */\n\n1 # Formula(f_Atom?) <--\n  MakeOper(0,0,Length(ToString(f)),1,\"ToAtom\",ToString(f),0);\n\n\n\n\n2 # Formula(_xx ^ _yy) <--\n{\n  Local(l,r);\n  Assign(l, BracketOn(Formula(xx),xx,LeftPrecedenceGet(\"^\")));\n  Assign(r, BracketOn(Formula(yy),yy,RightPrecedenceGet(\"^\")));\n  MoveOper(l,0,r[4]);\n  MoveOper(r,l[3],0);\n  MakeOper(0,0,AddN(l[3],r[3]),AddN(l[4],r[4]),\"Func\",[l,r],l[2]+l[4]-1);\n};\n\n\n\n\n10 # FormulaArrayItem(xx_List?) <--\n{\n  Local(sub,height);\n  sub := [];\n  height := 0;\n  ForEach(item,xx)\n  {\n    Local(made);\n    made := FormulaBracket(Formula(item));\n    Decide(made[4] >? height,Assign(height,made[4]));\n    DestructiveAppend(sub,made);\n  };\n  MakeOper(0,0,0,height,\"List\",sub,height>>1);\n};\n\n\n\n20 # FormulaArrayItem(_item) <-- Formula(item);\n\n\n\n\n2 # Formula(xx_List?) <--\n{\n  Local(sub,width,height);\n  sub:=[];\n  width := 0;\n  height := 1;\n\n  ForEach(item,xx)\n  {\n    Local(made);\n    made := FormulaArrayItem(item);\n\n    Decide(made[3] >? width,Assign(width,made[3]));\n    MoveOper(made,0,height);\n    Assign(height,AddN(height,AddN(made[4],1)));\n    DestructiveAppend(sub,made);\n  };\n\n  Local(thislength,maxlength);\n  maxlength:=0;\n  ForEach(item,xx)\n  {\n    thislength:=0;\n    If(List?(item)) {thislength:=Length(item);};\n    If(maxlength<?thislength) {maxlength:=thislength;};\n  };\n\n  Decide(maxlength>?0,\n  {\n    Local(i,j);\n    width:=0;\n    For(j:=1,j<=?maxlength,j++)\n    {\n      Local(w);\n      w := 0;\n      For(i:=1,i<=?Length(sub),i++)\n      {\n        If(List?(xx[i]) And? j<=?Length(xx[i]))\n          Decide(sub[i][6][j][3] >? w,w := sub[i][6][j][3]);\n      };\n\n      For(i:=1,i<=?Length(sub),i++)\n      {\n        If(List?(xx[i]) And? j<=?Length(xx[i]))\n          MoveOper(sub[i][6][j],width,0);\n      };\n      width := width+w+1;\n    };\n    For(i:=1,i<=?Length(sub),i++)\n    {\n      sub[i][3] := width;\n    };\n  }\n  );\n\n  sub := MakeOper(0,0,width,height,\"List\",sub,height>>1);\n  FormulaBracket(sub);\n};\n\n\n\n\n\n2 # Formula(_xx / _yy) <--\n{\n  Local(l,r,dash,width);\n/*\n  Assign(l, BracketOn(Formula(xx),xx,LeftPrecedenceGet(\"/\")));\n  Assign(r, BracketOn(Formula(yy),yy,RightPrecedenceGet(\"/\")));\n*/\n  Assign(l, Formula(xx));\n  Assign(r, Formula(yy));\n  Assign(width, Maximum(l[3],r[3]));\n  Assign(dash, Formula(ToAtom(CharList(width,\"-\"))));\n  MoveOper(dash,0,l[4]);\n  MoveOper(l,(SubtractN(width,l[3])>>1),0);\n  MoveOper(r,(SubtractN(width,r[3])>>1),AddN(dash[2], dash[4]));\n  MakeOper(0,0,width,AddN(r[2], r[4]),\"Func\",[l,r,dash],dash[2]);\n};\n\n\n\n\nRulebaseHoldArguments(\"BracketOn\",[_op,_f,_prec]);\n\nRuleHoldArguments(\"BracketOn\",3,1,Function?(f) And? ArgumentsCount(f) =? 2\n     And? Infix?(Type(f)) And? PrecedenceGet(Type(f)) >? prec)\n{\n FormulaBracket(op);\n};\n\n\n\n\nRuleHoldArguments(\"BracketOn\",3,2,True)\n{\n  op;\n};\n\n\n\n\n\n10 # Formula(f_Function?)::(ArgumentsCount(f) =? 2 And? Infix?(Type(f))) <--\n{\n  Local(l,r,oper,width,height,base);\n  Assign(l, Formula(f[1]));\n  Assign(r, Formula(f[2]));\n\n  Assign(l, BracketOn(l,f[1],LeftPrecedenceGet(Type(f))));\n  Assign(r, BracketOn(r,f[2],RightPrecedenceGet(Type(f))));\n\n  Assign(oper, Formula(f[0]));\n  Assign(base, Maximum(l[7],r[7]));\n  MoveOper(oper,AddN(l[3],1),SubtractN(base,(oper[7])));\n  MoveOper(r,oper[1] + oper[3]+1,SubtractN(base,(r[7])));\n  MoveOper(l,0,SubtractN(base,(l[7])));\n  Assign(height, Maximum(AddN(l[2], l[4]),AddN(r[2], r[4])));\n\n  MakeOper(0,0,AddN(r[1], r[3]),height,\"Func\",[l,r,oper],base);\n};\n\n\n\n\n11 # Formula(f_Function?) <--\n{\n  Local(head,args,all);\n  Assign(head, Formula(f[0]));\n  Assign(all, Rest(FunctionToList(f)));\n\n  Assign(args, FormulaBracket(BuildArgs(MapSingle(\"Formula\",Apply(\"Hold\",[all])))));\n  AlignBase(head,args);\n  MoveOper(args,head[3],0);\n\n  MakeOper(0,0,args[1]+args[3],Maximum(head[4],args[4]),\"Func\",[head,args],head[7]);\n};\n\n\n\nRulebaseHoldArguments(\"RenderFormula\",[\"cf\",_f,_x,_y]);\n\n/*\n/   /  /\n\\   |  |\n    \\  |\n       \\\n*/\n\nRuleHoldArguments(\"RenderFormula\",4,1,f[5] =? \"ToAtom\" And? f[6] =? \"(\" And? f[4] >? 1)\n{\n  Local(height,i);\n  Assign(x, AddN(x,f[1]));\n  Assign(y, AddN(y,f[2]));\n  Assign(height, SubtractN(f[4],1));\n\n  cf[y] := StringMidSet(x, \"/\", cf[y]);\n  cf[AddN(y,height)] := StringMidSet(x, \"\\\\\", cf[AddN(y,height)]);\n  For (Assign(i,1),LessThan?(i,height),Assign(i,AddN(i,1)))\n    cf[AddN(y,i)] := StringMidSet(x, \"|\", cf[AddN(y,i)]);\n};\n\n\n\n\nRuleHoldArguments(\"RenderFormula\",4,1,f[5] =? \"ToAtom\" And? f[6] =? \")\" And? f[4] >? 1)\n{\n  Local(height,i);\n  Assign(x, AddN(x,f[1]));\n  Assign(y, AddN(y,f[2]));\n  Assign(height, SubtractN(f[4],1));\n  cf[y] := StringMidSet(x, \"\\\\\", cf[y]);\n  cf[y+height] := StringMidSet(x, \"/\", cf[y+height]);\n  For (Assign(i,1),LessThan?(i,height),Assign(i,AddN(i,1)))\n    cf[AddN(y,i)] := StringMidSet(x, \"|\", cf[AddN(y,i)]);\n};\n\n\n\n\nRuleHoldArguments(\"RenderFormula\",4,5,f[5] =? \"ToAtom\")\n{\n  cf[AddN(y, f[2]) ]:=\n    StringMidSet(AddN(x,f[1]),f[6],cf[AddN(y, f[2]) ]);\n};\n\n\n\n\nRuleHoldArguments(\"RenderFormula\",4,6,True)\n{\n  ForEach(item,f[6])\n  {\n    RenderFormula(cf,item,AddN(x, f[1]),AddN(y, f[2]));\n  };\n};\n\n\n\n\nLocalSymbols(formulaMaxWidth) {\n  SetFormulaMaxWidth(width):=\n  {\n    formulaMaxWidth := width;\n  };\n  FormulaMaxWidth() := formulaMaxWidth;\n  SetFormulaMaxWidth(60);\n}; // LocalSymbols(formulaMaxWidth)\n\n\n\n\nFunction(\"UnparseMath2D\",[_ff])\n{\n  Local(cf,f);\n\n  f:=Formula(ff);\n\n  cf:=CharField(f[3],f[4]);\n  RenderFormula(cf,f,1,1);\n\n  NewLine();\n  WriteCharField(cf,FormulaMaxWidth());\n\n  DumpErrors();\n  True;\n};\n/*\nHoldArgument(\"UnparseMath2D\",\"ff\");\n*/\n\n\n\n\nEvalFormula(f):=\n{\n  Local(result);\n  result:= ListToFunction([ToAtom(\"=?\"),f,Eval(f)]);\n  UnparseMath2D(result);\n  True;\n};\nHoldArgument(\"EvalFormula\",\"f\");\n\n/*\n[x,y,width,height,oper,args,base]\n*/\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/unparsers/UnparseMath2D.mpw, (UnparseMath2D;EvalFormula;BuildArgs;FormulaArrayItem)";
        scriptMap.put("UnparseMath2D",scriptString);
        scriptMap.put("EvalFormula",scriptString);
        scriptMap.put("BuildArgs",scriptString);
        scriptMap.put("FormulaArrayItem",scriptString);

        scriptString = new String[3];
        scriptString[0] = null;
        scriptString[1] = "\n/* def file definitions\nUnparseLatex\nUnparseLatexMaxPrec\nTexForm\n*/\n\n/* UnparseLatex: convert MathPiper objects to Latex math mode strings */\n\n/* version 0.4 */\n\n/* Changelog\n        0.1        basic functionality\n        0.2 fixed bracketing of Exp, added all infix ops and math functions\n        0.3 fixed bracketing of lists, changed bracketing of math functions, modified Latex representation of user-defined functions (up to two-letter functions are in italics), added Latex Greek letters\n        0.4 added nth roots, Sum, Limit, Integrate, hyperbolics, set operations, Abs, Max, Min, \"==\", \":=\", Infinity; support indexed expressions A[i] and matrices.\n        0.4.1 bugfixes for [] operator, support for multiple indices a[1][2][3]\n        0.4.2 fix for variable names ending on digits \"a2\" represented as $a_2$\n        0.4.3 bugfixes: complex I, indeterminate integration; relaxed bracketing of Sin()-like functions; implemented $TeX$ and $LaLatex$ correctly now (using \\textrm{})\n        0.4.4 use ordinary instead of partial derivative if expression has only one variable\n        0.4.5 fixes for bracketing of Sum(); added <> to render as \\sim and <=> to render as \\approx; added BinomialCoefficient()\n        0.4.6 moved the <> and <=> operators to initialization.rep/stdopers.mpi\n        0.4.7 added Product() i.e. Product()\n        0.4.8 added Differentiate(x,n), Deriv(x,n),  Implies? , and fixed errors with ArcSinh, ArcCosh, ArcTanh\n        0.4.9 fixed omission: (fraction)^n was not put in brackets\n        0.4.10 cosmetic change: insert \\cdot between numbers in cases like 2*10^n\n        0.4.11 added DumpErrors() to TexForm for the benefit of TeXmacs notebooks\n        0.4.12 implement the % operation as Mod\n        0.4.13 added Bessel{I,J,K,Y}, Ortho{H,P,T,U}, with a general framework for usual two-argument functions of the form $A_n(x)$; fix for Max, Min\n        0.4.14 added mathematical notation for Floor(), Ceil()\n        0.4.15 added Block() represented by ( )\n        0.4.16 added Zeta()\n*/\n\n/* To do:\n        0. Find and fix bugs.\n        1. The current bracketing approach has limitations: can't omit extra brackets sometimes. \" sin a b\" is ambiguous, so need to do either \"sin a sin b\" or \"(sin a) b\" Hold((a*b)*Sqrt(x)). The current approach is *not* to bracket functions unless the enveloping operation is more binding than multiplication. This produces \"sin a b\" for both Sin(a*b) and Sin(a)*b but this is the current mathematical practice.\n        2. Need to figure out how to deal with variable names such as \"alpha3\"\n*/\n\n//Retract(\"UnparseLatex\", *);\n\n/// TeXmacs unparser\n//TexForm(_expr) <-- {DumpErrors();WriteString(UnparseLatex(expr));NewLine();};\n\nRulebaseHoldArguments(\"UnparseLatex\",[_expression]);\nRulebaseHoldArguments(\"UnparseLatex\",[_expression, _precedence]);\n\n/* Boolean predicate */\n\n\n/* this function will put TeX brackets around the string if predicate holds */\n\nFunction(\"UnparseLatexBracketIf\", [_predicate, _string])\n{\n        Check(Boolean?(predicate) And? String?(string), \"Argument\", \"UnparseLatex internal error: non-boolean and/or non-string argument of UnparseLatexBracketIf\");\n        Decide(predicate, ConcatStrings(\"( \", string, \") \"), string);\n};\n\n\n\n\nFunction(\"UnparseLatexMatrixBracketIf\", [_predicate, _string])\n{\n        Check(Boolean?(predicate) And? String?(string), \"Argument\", \"UnparseLatex internal error: non-boolean and/or non-string argument of UnparseLatexMatrixBracketIf\");\n        Decide(predicate, ConcatStrings(\"\\\\left[ \", string, \"\\\\right]\"), string);\n};\n\n\n\n/* First, we convert UnparseLatex(x) to UnparseLatex(x, precedence). The enveloping precedence will determine whether we need to bracket the results. So UnparseLatex(x, UnparseLatexMaxPrec()) will always print \"x\", while UnparseLatex(x,-UnparseLatexMaxPrec()) will always print \"(x)\".\n*/\n\nUnparseLatexMaxPrec() := 60000;         /* This precedence will never be bracketed. It is equal to KMaxPrec */\n\n/// main front-end\n100 # UnparseLatex(_x) <-- ConcatStrings(\"$\", UnparseLatex(x, UnparseLatexMaxPrec()), \"$\");\n\n/* Replace numbers and variables -- never bracketed except explicitly */\n\n110 # UnparseLatex(x_Number?, _p) <-- ToString(x);\n/* Variables */\n\n200 # UnparseLatex(x_Atom?, _p) <-- UnparseLatexLatexify(ToString(MetaToObject(x)));\n\n\n/* Strings must be quoted but not bracketed */\n100 # UnparseLatex(x_String?, _p) <--\n{\n    Local(characterList);\n\n    characterList := [];\n    ForEach(character, x)\n    {\n        Decide(character !=? \" \", DestructiveAppend(characterList, character), DestructiveAppend(characterList, \"\\\\hspace{2 mm}\"));\n    };\n    ConcatStrings(\"\\\\mathrm{''\", ListToString(characterList), \"''}\");\n};\n\n\n\n/* FunctionToList(...) can generate lists with atoms that would otherwise result in unparsable expressions. */\n100 # UnparseLatex(x_Atom?, _p)::(Infix?(ToString(x))) <-- ConcatStrings(\"\\\\mathrm{\", ToString(x), \"}\");\n\n\n/* Lists: make sure to have matrices processed before them. Enveloping precedence is irrelevant because lists are always bracketed. List items are never bracketed. Note that UnparseLatexFinishList({a,b}) generates \",a,b\" */\n\n100 # UnparseLatex(x_List?, _p)::(Length(x) =? 0) <-- UnparseLatexBracketIf(True, \"\");\n110 # UnparseLatex(x_List?, _p) <-- UnparseLatexBracketIf(True, ConcatStrings(UnparseLatex(First(x), UnparseLatexMaxPrec()), UnparseLatexFinishList(Rest(x)) ) );\n100 # UnparseLatexFinishList(x_List?)::(Length(x) =? 0) <-- \"\";\n110 # UnparseLatexFinishList(x_List?) <-- ConcatStrings(\", \", UnparseLatex(First(x), UnparseLatexMaxPrec()), UnparseLatexFinishList(Rest(x)));\n\n/* Replace operations */\n\n\n        /* Template for \"regular\" binary infix operators:\n100 # UnparseLatex(_x + _y, _p) <-- UnparseLatexBracketIf(p<?PrecedenceGet(\"+\"), ConcatStrings(UnparseLatex(x, LeftPrecedenceGet(\"+\")), \" + \", UnparseLatex(y, RightPrecedenceGet(\"+\")) ) );\n        */\n        \n// special cases: things like x*2 and 2*10^x look ugly without a multiplication symbol\n// cases like x*2\n115 # UnparseLatex(_expr * n_Number?, _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"*\"), ConcatStrings(UnparseLatex(expr, LeftPrecedenceGet(\"*\")), \"\\\\times \", UnparseLatex(n, RightPrecedenceGet(\"*\")) ) );\n// cases like a*20! and a*10^x\n\n\n116 # UnparseLatex(_n * _expr, _p):: (Function?(expr) And? Contains?([\"^\", \"!\", \"!!\"], Type(expr)) And? Number?(FunctionToList(expr)[2])) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"*\"), ConcatStrings(UnparseLatex(n, LeftPrecedenceGet(\"*\")), \"\\\\times \", UnparseLatex(expr, RightPrecedenceGet(\"*\")) ) );\n\n        /* generic binary ops here */\n119 # UnparseLatex(expr_Function?, _p)::(ArgumentsCount(expr) =? 2 And? Infix?(Type(expr)) And? Not? Type(expr) =? \"^\" And? MetaGet(expr[0],\"HighlightColor\") !=? Empty) <-- UnparseLatexBracketIf(p <? PrecedenceGet(Type(expr)), ConcatStrings(UnparseLatex(FunctionToList(expr)[2], LeftPrecedenceGet(Type(expr))), \"\\\\colorbox{\",MetaGet(expr[0],\"HighlightColor\"),\"}{\", UnparseLatexLatexify(Type(expr)), \"}\", UnparseLatex(FunctionToList(expr)[3], RightPrecedenceGet(Type(expr))) ) );\n120 # UnparseLatex(expr_Function?, _p)::(ArgumentsCount(expr) =? 2 And? Infix?(Type(expr)) ) <-- UnparseLatexBracketIf(p <? PrecedenceGet(Type(expr)), ConcatStrings(UnparseLatex(FunctionToList(expr)[2], LeftPrecedenceGet(Type(expr))), UnparseLatexLatexify(Type(expr)), UnparseLatex(FunctionToList(expr)[3], RightPrecedenceGet(Type(expr))) ) );\n\n        /* Template for \"regular\" unary prefix operators:\n100 # UnparseLatex(+ _y, _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"+\"), ConcatStrings(\" + \", UnparseLatex(y, RightPrecedenceGet(\"+\")) ) );\n        Note that RightPrecedenceGet needs to be defined for prefix ops or Else we won't be able to tell combined prefix/infix ops like \"-\" from strictly prefix ops.\n        */\n\n        /* generic unary ops here */\n        /* prefix */\n119 # UnparseLatex(expr_Function?, _p)::(ArgumentsCount(expr) =? 1 And? Prefix?(Type(expr)) And? MetaGet(expr[0],\"HighlightColor\") !=? Empty) <-- UnparseLatexBracketIf(p <? PrecedenceGet(Type(expr)), ConcatStrings(\"\\\\colorbox{\",MetaGet(expr[0],\"HighlightColor\"),\"}{\", UnparseLatexLatexify(Type(expr)), \"}\", UnparseLatex(FunctionToList(expr)[2], RightPrecedenceGet(Type(expr)))) );\n120 # UnparseLatex(expr_Function?, _p)::(ArgumentsCount(expr) =? 1 And? Prefix?(Type(expr))) <-- UnparseLatexBracketIf(p <? PrecedenceGet(Type(expr)), ConcatStrings(UnparseLatexLatexify(Type(expr)), UnparseLatex(FunctionToList(expr)[2], RightPrecedenceGet(Type(expr)))) );\n\n\n        /* postfix */\n120 # UnparseLatex(expr_Function?, _p)::(ArgumentsCount(expr) =? 1 And? Postfix?(Type(expr))) <-- UnparseLatexBracketIf(p <? LeftPrecedenceGet(Type(expr)), ConcatStrings(\n        UnparseLatex(FunctionToList(expr)[2], LeftPrecedenceGet(Type(expr))),\n        UnparseLatexLatexify(Type(expr))\n) );\n\n        /* fraction and its operands are never bracketed except when they are under power */\n98 # UnparseLatex(_x / _y, _p)::(Assigned?(operatorMetaMap) And? MetaGet(operatorMetaMap,\"HighlightColor\") !=? Empty) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(\"\\\\colorbox{\",MetaGet(operatorMetaMap,\"HighlightColor\"),\"}{\\\\frac \\\\textcolor{Black}{\", UnparseLatex(x, UnparseLatexMaxPrec()), \"} \\\\textcolor{Black}{\", UnparseLatex(y, UnparseLatexMaxPrec()), \"}} \") );\n100 # UnparseLatex(_x / _y, _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(\"\\\\frac{\", UnparseLatex(x, UnparseLatexMaxPrec()), \"}{\", UnparseLatex(y, UnparseLatexMaxPrec()), \"} \") );\n\n        /* power's argument is never bracketed but it must be put in braces. Chained powers must be bracketed. Powers of 1/n are displayed as roots. */\n100 # UnparseLatex(_x ^ (1/2), _p) <-- ConcatStrings(\"\\\\sqrt{\", UnparseLatex(x, UnparseLatexMaxPrec()), \"}\");\n101 # UnparseLatex(_x ^ (1/_y), _p) <-- ConcatStrings(\"\\\\sqrt[\", UnparseLatex(y, UnparseLatexMaxPrec()), \"]{\", UnparseLatex(x, UnparseLatexMaxPrec()), \"}\");\n\n\n120 # UnparseLatex(_x ^ _y, _p) <-- UnparseLatexBracketIf(p <=? PrecedenceGet(\"^\"), ConcatStrings(UnparseLatex(x, PrecedenceGet(\"^\")), \" ^{\", UnparseLatex(y, UnparseLatexMaxPrec()), \"}\" ) );\n\n100 # UnparseLatex(If(_pred) _body, _p) <-- \"\\\\textrm{if }(\" ~ UnparseLatex(pred,60000) ~ \") \" ~ UnparseLatex(body,60000);\n100 # UnparseLatex(_left Else _right, _p) <-- UnparseLatex(left,60000) ~ \"\\\\textrm{ Else }\" ~ UnparseLatex(right,60000);\n\n\n/* functions */\n\n\nLocalSymbols(UnparseLatexRegularOps, UnparseLatexRegularPrefixOps, UnparseLatexGreekLetters, UnparseLatexSpecialNames) {\n\n        /* addition, subtraction, multiplication, all comparison and logical operations are \"regular\" */\n\n  UnparseLatexRegularOps :=\n  [\n    [\"+\",\" + \"],\n    [\"-\",\" - \"],\n    [\"*\",\" \\\\times \"],\n    [\":=\",\" := \"], //\\\\equiv \"],\n    [\"==\",\" = \"],\n    [\"=?\",\" = \"],\n    [\"!=?\",\"\\\\neq \"],\n    [\"<=?\",\"\\\\leq \"],\n    [\">=?\",\"\\\\geq \"],\n    [\"<?\",\" < \"],\n    [\">?\",\" > \"],\n    [\"And?\",\"\\\\wedge \"],\n    [\"Or?\", \"\\\\vee \"],\n    [\"<>\", \"\\\\sim \"],\n    [\"<=>\", \"\\\\approx \"],\n    [\"Implies?\", \"\\\\Rightarrow \"],\n    [\"Equivales?\", \"\\\\equiv \"],\n    [\"%\", \"\\\\bmod \"],\n  ];\n\n  UnparseLatexRegularPrefixOps := [ [\"+\",\" + \"], [\"-\",\" - \"], [\"Not?\",\" \\\\neg \"] ];\n\n\n\n    /* Unknown function: precedence 200. Leave as is, never bracket the function itself and bracket the argumentPointer(s) automatically since it's a list. Other functions are precedence 100 */\n\n  UnparseLatexGreekLetters := [\"Gamma\", \"Delta\", \"Theta\", \"Lambda\", \"Xi\", \"Pi\", \"Sigma\", \"Upsilon\", \"Phi\", \"Psi\", \"Omega\", \"alpha\", \"beta\", \"gamma\", \"delta\", \"epsilon\", \"zeta\", \"eta\", \"theta\", \"iota\", \"kappa\", \"lambda\", \"mu\", \"nu\", \"xi\", \"pi\", \"rho\", \"sigma\", \"tau\", \"upsilon\", \"phi\", \"chi\", \"psi\", \"omega\", \"varpi\", \"varrho\", \"varsigma\", \"varphi\", \"varepsilon\"];\n  UnparseLatexSpecialNames := [\n    [\"I\", \"\\\\imath \"],        // this prevents a real uppercase I, use BesselI instead\n    [\"Pi\", \"\\\\pi \"],        // this makes it impossible to have an uppercase Pi... hopefully it's not needed\n    [\"Infinity\", \"\\\\infty \"],\n    [\"TeX\", \"\\\\textrm{\\\\TeX\\\\/}\"],\n    [\"LaTeX\", \"\\\\textrm{\\\\LaTeX\\\\/}\"],\n    [\"Maximum\", \"\\\\max \"],        // this replaces these function names\n    [\"Minimum\", \"\\\\min \"],\n    [\"Block\", \" \"],\n    [\"Zeta\", \"\\\\zeta \"],\n  ];\n\n\n  /* this function will take a user-defined variable or function name and output either this name unmodified if it's only 2 characters long, or the name in normal text if it's longer, or a Latex Greek letter code */\n  Function(\"UnparseLatexLatexify\", [_string])\n  {\n    Check(String?(string), \"Argument\", \"UnparseLatex internal error: non-string argument of UnparseLatexLatexify\");\n    /* Check if it's a greek letter or a special name */\n    Decide(Contains?(AssocIndices(UnparseLatexSpecialNames), string), UnparseLatexSpecialNames[string],\n    Decide(Contains?(UnparseLatexGreekLetters, string), ConcatStrings(\"\\\\\", string, \" \"),\n    Decide(Contains?(AssocIndices(UnparseLatexRegularOps), string), UnparseLatexRegularOps[string],\n    Decide(Contains?(AssocIndices(UnparseLatexRegularPrefixOps), string), UnparseLatexRegularPrefixOps[string],\n    Decide(Length(string) >=? 2 And? Number?(ToAtom(StringMidGet(2, Length(string)-1, string))), ConcatStrings(StringMidGet(1,1,string), \"_{\", StringMidGet(2, Length(string)-1, string), \"}\"),\n    Decide(Length(string) >? 2, ConcatStrings(\"\\\\mathrm{ \", string, \" }\"),\n    string\n    ))))));\n  };\n\n};\n\n/* */\n\n/* Unknown bodied function */\n\n200 # UnparseLatex(x_Function?, _p):: (Bodied?(Type(x))) <-- {\n        Local(func, args, lastarg);\n        func := Type(x);\n        args := Rest(FunctionToList(x));\n        lastarg := PopBack(args);\n        UnparseLatexBracketIf(p <? PrecedenceGet(func), ConcatStrings(\n          UnparseLatexLatexify(func), UnparseLatex(args, UnparseLatexMaxPrec()),  UnparseLatex(lastarg, PrecedenceGet(func))\n        ));\n};\n\n/* Unknown infix function : already done above\n210 # UnparseLatex(x_Function?, _p)::(Infix?(Type(x))) <-- ConcatStrings(UnparseLatexLatexify(Type(x)), UnparseLatex(Rest(FunctionToList(x)), UnparseLatexMaxPrec()) );\n*/\n/* Unknown function that is not prefix, infix or postfix */\n220 # UnparseLatex(x_Function?, _p) <-- ConcatStrings(UnparseLatexLatexify(Type(x)), UnparseLatex(Rest(FunctionToList(x)), UnparseLatexMaxPrec()) );\n\n        /* Never bracket Sqrt or its arguments */\n100 # UnparseLatex(Sqrt(_x), _p) <-- ConcatStrings(\"\\\\sqrt{\", UnparseLatex(x, UnparseLatexMaxPrec()), \"}\");\n\n        /* Always bracket Exp's arguments */\n100 # UnparseLatex(Exp(_x), _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(\"\\\\exp \", UnparseLatexBracketIf(True, UnparseLatex(x, UnparseLatexMaxPrec())) ) );\n\n\nLocalSymbols(UnparseLatexMathFunctions, UnparseLatexMathFunctions2) {\n\n  /* Sin, Cos, etc. and their argument is bracketed when it's a sum or product but not bracketed when it's a power. */\n  /// supported MathPiper functions: \"mathematical\" functions of one argument which sometimes do not require parentheses (e.g. $\\sin x$ )\n  UnparseLatexMathFunctions := [ [\"Cos\",\"\\\\cos \"], [\"Sin\",\"\\\\sin \"], [\"Tan\",\"\\\\tan \"], [\"Cosh\",\"\\\\cosh \"], [\"Sinh\",\"\\\\sinh \"], [\"Tanh\",\"\\\\tanh \"], [\"Ln\",\"\\\\ln \"], [\"ArcCos\",\"\\\\arccos \"], [\"ArcSin\",\"\\\\arcsin \"], [\"ArcTan\",\"\\\\arctan \"], [\"ArcCosh\",\"\\\\mathrm{arccosh}\\\\, \"], [\"ArcSinh\",\"\\\\mathrm{arcsinh}\\\\, \"], [\"ArcTanh\",\"\\\\mathrm{arctanh}\\\\, \"],\n  [\"Erf\", \"\\\\mathrm{erf}\\\\, \"], [\"Erfc\", \"\\\\mathrm{erfc}\\\\, \"],\n  ];\n\n  /// supported MathPiper functions: functions of two arguments of the form $A_n(x)$\n  UnparseLatexMathFunctions2 := [\n  [\"BesselI\", \"I \"], [\"BesselJ\", \"J \"],\n  [\"BesselK\", \"K \"], [\"BesselY\", \"Y \"],\n  [\"OrthoH\", \"H \"], [\"OrthoP\", \"P \"],\n  [\"OrthoT\", \"T \"], [\"OrthoU\", \"U \"],\n  ];\n\n  // generic two-argument functions of the form $A(x,y)$ where just the name has to be changed: handle this using the usual naming conversion scheme (UnparseLatexSpecialNames)\n\n  /* Precedence of 120 because we'd like to process other functions like sqrt or exp first */\n\n  // generic math functions of one argument\n  120 # UnparseLatex(expr_Function?, _p):: (ArgumentsCount(expr) =? 1 And? Contains?(AssocIndices(UnparseLatexMathFunctions), Type(expr)) ) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"*\"), ConcatStrings(UnparseLatexMathFunctions[Type(expr)], UnparseLatex( FunctionToList(expr)[2], PrecedenceGet(\"*\")) ) );\n\n  /// math functions two arguments of the form $A_n(x)$\n  120 # UnparseLatex(expr_Function?, _p):: (ArgumentsCount(expr) =? 2 And? Contains?(AssocIndices(UnparseLatexMathFunctions2), Type(expr)) ) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"*\"),\n    ConcatStrings(\n    UnparseLatexMathFunctions2[Type(expr)],\n    \"_{\",\n    UnparseLatex( FunctionToList(expr)[2], UnparseLatexMaxPrec()),        // never bracket the subscript\n    \"}\",\n    UnparseLatexBracketIf(True, UnparseLatex(FunctionToList(expr)[3], UnparseLatexMaxPrec()) ) // always bracket the function argument\n    )\n  );\n\n}; // LocalSymbols(UnparseLatexMathFunctions, UnparseLatexMathFunctions2)\n\n\n/* Complex numbers */\n100 # UnparseLatex(Complex(0, 1), _p) <-- UnparseLatex(Hold(I), p);\n100 # UnparseLatex(Complex(_x, 0), _p) <-- UnparseLatex(x, p);\n110 # UnparseLatex(Complex(_x, 1), _p) <-- UnparseLatex(x + Hold(I), p);\n110 # UnparseLatex(Complex(0, _y), _p) <-- UnparseLatex(Hold(I)*y, p);\n120 # UnparseLatex(Complex(_x, _y), _p) <-- UnparseLatex(x + Hold(I)*y, p);\n\n/* Abs(), Floor(), Ceil() are displayed as special brackets */\n\n100 # UnparseLatex(Abs(_x), _p) <-- ConcatStrings(\"\\\\left| \", UnparseLatex(x, UnparseLatexMaxPrec()), \"\\\\right| \");\n100 # UnparseLatex(Floor(_x), _p) <-- ConcatStrings(\"\\\\left\\\\lfloor \", UnparseLatex(x, UnparseLatexMaxPrec()), \"\\\\right\\\\rfloor \");\n100 # UnparseLatex(Ceil(_x), _p) <-- ConcatStrings(\"\\\\left\\\\lceil \", UnparseLatex(x, UnparseLatexMaxPrec()), \"\\\\right\\\\rceil \");\n\n/* Some functions which are displayed as infix: Mod, Union, Intersection, Difference, Contains? */\n\n100 # UnparseLatex(Modulo(_x, _y), _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(UnparseLatex(x, PrecedenceGet(\"/\")), \"\\\\bmod \", UnparseLatex(y, PrecedenceGet(\"/\")) ) );\n\n100 # UnparseLatex(Union(_x, _y), _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(UnparseLatex(x, PrecedenceGet(\"/\")), \"\\\\cup \", UnparseLatex(y, PrecedenceGet(\"/\")) ) );\n\n100 # UnparseLatex(Intersection(_x, _y), _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(UnparseLatex(x, PrecedenceGet(\"/\")), \"\\\\cap \", UnparseLatex(y, PrecedenceGet(\"/\")) ) );\n\n100 # UnparseLatex(Difference(_x, _y), _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(UnparseLatex(x, PrecedenceGet(\"/\")), \"\\\\setminus \", UnparseLatex(y, PrecedenceGet(\"/\")) ) );\n\n/* \"Contains?\" is displayed right to left */\n\n100 # UnparseLatex(Contains?(_x, _y), _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(UnparseLatex(y, PrecedenceGet(\"/\")), \"\\\\in \", UnparseLatex(x, PrecedenceGet(\"/\")) ) );\n\n/// Binomial coefficients: always bracketed\n100 # UnparseLatex(BinomialCoefficient(_n, _m), _p) <-- UnparseLatexBracketIf(False, ConcatStrings(\"{\", UnparseLatex(n, UnparseLatexMaxPrec()), \" \\\\choose \", UnparseLatex(m, UnparseLatexMaxPrec()), \"}\" )\n);\n\n/* Some functions with limits: Lim, Sum, Integrate */\n/* todo:tk:commenting out for the minimal version of the scripts.\n100 # UnparseLatex(Sum(_x, _x1, _x2, _expr), _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(\"\\\\sum _{\", UnparseLatex(x, UnparseLatexMaxPrec()), \" = \", UnparseLatex(x1, UnparseLatexMaxPrec()), \"} ^{\", UnparseLatex(x2, UnparseLatexMaxPrec()), \"} \", UnparseLatex(expr, PrecedenceGet(\"*\")) ) );\n\n100 # UnparseLatex(Sum(_expr), _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(\"\\\\sum \", UnparseLatex(expr, PrecedenceGet(\"*\")) ) );\n\n\n100 # UnparseLatex(Product(_x, _x1, _x2, _expr), _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(\"\\\\prod _{\", UnparseLatex(x, UnparseLatexMaxPrec()), \" = \", UnparseLatex(x1, UnparseLatexMaxPrec()), \"} ^{\", UnparseLatex(x2, UnparseLatexMaxPrec()), \"} \", UnparseLatex(expr, PrecedenceGet(\"*\")) ) );\n\n100 # UnparseLatex(Integrate(_x, _x1, _x2) _expr, _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(\n\"\\\\int _{\", UnparseLatex(x1, UnparseLatexMaxPrec()), \"} ^{\", UnparseLatex(x2, UnparseLatexMaxPrec()), \" } \", UnparseLatex(expr, PrecedenceGet(\"*\")), \" d\", UnparseLatex(x, UnparseLatexMaxPrec())\n) );\n/*\n\n/* indeterminate integration */\n/* todo:tk:commenting out for the minimal version of the scripts.\n100 # UnparseLatex(Integrate(_x) _expr, _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(\n\"\\\\int \", UnparseLatex(expr, PrecedenceGet(\"*\")), \" d\", UnparseLatex(x, UnparseLatexMaxPrec())\n) );\n\n100 # UnparseLatex(Limit(_x, _x1) _expr, _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(\"\\\\lim _{\", UnparseLatex(x, UnparseLatexMaxPrec()), \"\\\\rightarrow \", UnparseLatex(x1, UnparseLatexMaxPrec()), \"} \", UnparseLatex(expr, PrecedenceGet(\"/\")) ) );\n*/\n\n\n/* Derivatives */\n\n/* Use partial derivative only when the expression has several variables */\n/* todo:tk:commenting out for the minimal version of the scripts.\n100 # UnparseLatex(Deriv(_x) _y, _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"-\"), ConcatStrings(\n        Decide(Length(VarList(y))>?1, \"\\\\frac{\\\\partial}{\\\\partial \", \"\\\\frac{d}{d \"\n        ), UnparseLatex(x, PrecedenceGet(\"^\")), \"}\", UnparseLatex(y, PrecedenceGet(\"/\")) ) );\n\n100 # UnparseLatex(Deriv(_x, _n) _y, _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"-\"), ConcatStrings(\n        Decide(\n                Length(VarList(y))>?1,\n                \"\\\\frac{\\\\partial^\" ~ UnparseLatex(n, UnparseLatexMaxPrec()) ~ \"}{\\\\partial \",\n                \"\\\\frac{d^\" ~ UnparseLatex(n, UnparseLatexMaxPrec()) ~ \"}{d \"\n        ), UnparseLatex(x, PrecedenceGet(\"^\")), \" ^\", UnparseLatex(n, UnparseLatexMaxPrec()), \"}\", UnparseLatex(y, PrecedenceGet(\"/\")) ) );\n100 # UnparseLatex(Differentiate(_x) _y, _p) <-- UnparseLatex(Deriv(x) y, p);\n100 # UnparseLatex(Differentiate(_x, _n) _y, _p) <-- UnparseLatex(Deriv(x, n) y, p);\n*/\n\n\n\n/* Indexed expressions */\n\n/* This seems not to work because x[i] is replaced by Nth(x,i) */\n/*\n100 # UnparseLatex(_x [ _i ], _p) <-- ConcatStrings(UnparseLatex(x, UnparseLatexMaxPrec()), \" _{\", UnparseLatex(i, UnparseLatexMaxPrec()), \"}\");\n*/\n/* Need to introduce auxiliary function, or Else have trouble with arguments of Nth being lists */\nRulebaseHoldArguments(\"UnparseLatexNth\",[_x,_y]);\n100 # UnparseLatex(Nth(Nth(_x, i_List?), _j), _p) <-- UnparseLatex(UnparseLatexNth(x, Append(i,j)), p);\n100 # UnparseLatex(UnparseLatexNth(Nth(_x, i_List?), _j), _p) <-- UnparseLatex(UnparseLatexNth(x, Append(i,j)), p);\n110 # UnparseLatex(Nth(Nth(_x, _i), _j), _p) <-- UnparseLatex(UnparseLatexNth(x, List(i,j)), p);\n120 # UnparseLatex(Nth(_x, _i), _p) <-- ConcatStrings(UnparseLatex(x, UnparseLatexMaxPrec()), \" _{\", UnparseLatex(i, UnparseLatexMaxPrec()), \"}\");\n120 # UnparseLatex(UnparseLatexNth(_x, _i), _p) <-- ConcatStrings(UnparseLatex(x, UnparseLatexMaxPrec()), \" _{\", UnparseLatex(i, UnparseLatexMaxPrec()), \"}\");\n\n/* Matrices are always bracketed. Precedence 80 because lists are at 100. */\n\n80 # UnparseLatex(M_Matrix?, _p) <-- UnparseLatexMatrixBracketIf(True, UnparseLatexPrintMatrix(M));\n\nFunction(\"UnparseLatexPrintMatrix\", [_M])\n{\n/*\n        Want something like \"\\begin{array}{cc} a & b \\\\ c & d \\\\ e & f \\end{array}\"\n        here, \"cc\" is alignment and must be given for each column\n*/\n        Local(row, col, result, ncol);\n        result := \"\\\\begin{array}{\";\n        ForEach(col, M[1]) result:=ConcatStrings(result, \"c\");\n        result := ConcatStrings(result, \"}\");\n\n        ForEach(row, 1 .. Length(M)) {\n                ForEach(col, 1 .. Length(M[row])) {\n                        result := ConcatStrings( result, \" \", UnparseLatex(M[row][col], UnparseLatexMaxPrec()), Decide(col =? Length(M[row]), Decide(row =? Length(M), \"\", \" \\\\\\\\\"), \" &\"));\n                };\n        };\n\n        ConcatStrings(result, \" \\\\end{array} \");\n};\n\n";
        scriptString[2] = "/org/mathpiper/scripts4/unparsers/unparselatex.mpw, (UnparseLatex;UnparseLatexFinishList;UnparseLatexFinishList)";
        scriptMap.put("UnparseLatex",scriptString);
        scriptMap.put("UnparseLatexFinishList",scriptString);
        scriptMap.put("UnparseLatexFinishList",scriptString);
    }

    public String[] getScript(String functionName)
    {
        return (String[]) scriptMap.get(functionName);
    }

    public Map getMap()
    {
        return  scriptMap;
    }
}
