package org.mathpiper;

import java.util.HashMap;

import java.util.Map;

public class Scripts {

    private HashMap scriptMap = null;

    public Scripts() {

        scriptMap = new HashMap();

        String[] scriptString;


        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(MathPiperInitLoad,{});RuleHoldArguments(MathPiperInitLoad, 0, 1, True)[  StandardOperatorsLoad(); Constant(True);];";
        scriptMap.put("MathPiperInitLoad",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Nth\",{alist,aindex});RuleHoldArguments(\"Nth\",2,10, And?(Equal?(Function?(alist),True), Equal?(Integer?(aindex),True), Not?(Equal?(First(FunctionToList(alist)),Nth)) )) MathNth(alist,aindex);RuleHoldArguments(\"Nth\",2,14, And?(Equal?(String?(alist),True),List?(aindex)) )[ Local(result); result:=\"\"; ForEach(i,aindex) [ result := result ~ StringMidGet(i,1,alist); ]; result;];RuleHoldArguments(\"Nth\",2,15,Equal?(String?(alist),True))[ StringMidGet(aindex,1,alist);];RuleHoldArguments(\"Nth\",2,20,Equal?(List?(aindex),True))[ Map({{ii},alist[ii]},{aindex});];RuleHoldArguments(\"Nth\",2,30, And?( Equal?(Generic?(alist),True), Equal?(GenericTypeName(alist),\"Array\"), Equal?(Integer?(aindex),True) ) )[ ArrayGet(alist,aindex);];RuleHoldArguments(\"Nth\",2,40,Equal?(String?(aindex),True))[ Local(as); as := Assoc(aindex,alist); If (Not?(Equal?(as,Empty)),Bind(as,Nth(as,2))); as;];";
        scriptMap.put("Nth",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"ArgumentsCount\",{aLeft}) Length(FunctionToList(aLeft))-1;";
        scriptMap.put("ArgumentsCount",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1 # Denominator(_x / _y) <-- y;1 # Denominator(-_x/ _y) <-- y;2 # Denominator(x_Number?) <-- 1;";
        scriptMap.put("Denominator",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # NonObject?(Object(_x)) <-- False;20 # NonObject?(_x) <-- True;";
        scriptMap.put("NonObject?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1 # Numerator(_x / _y) <-- x;1 # Numerator(-_x/ _y) <-- -x;2 # Numerator(x_Number?) <-- x;";
        scriptMap.put("Numerator",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "a_NonNegativeInteger? & b_NonNegativeInteger? <-- BitAnd(a,b);";
        scriptMap.put("&",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"==\",{left,right});";
        scriptMap.put("==",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"!==\",{left,right});";
        scriptMap.put("!==",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"--\",{aVar})[ MacroBind(aVar,SubtractN(Eval(aVar),1)); Eval(aVar);];UnFence(\"--\",1);HoldArgument(\"--\",aVar);";
        scriptMap.put("--",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(numericMode)[ numericMode := False;  LocalSymbols(previousNumericMode, previousPrecision, numericResult)  Macro(\"N\",{expression, precision}) [      Local(previousNumericMode, previousPrecision, numericResult, exception); previousPrecision := BuiltinPrecisionGet();  BuiltinPrecisionSet(@precision+5); AssignCachedConstantsN(); previousNumericMode := numericMode; numericMode := True; exception := False;  ExceptionCatch( numericResult:=Eval(@expression), exception := ExceptionGet() );  If(Decimal?(numericResult), numericResult := RoundToN(numericResult, @precision));  numericMode := previousNumericMode; If(Not? numericMode, [ ClearCachedConstantsN(); ] ); BuiltinPrecisionSet(previousPrecision); Check(exception =? False, exception[\"type\"], exception[\"message\"]); numericResult; ];  LocalSymbols(precision,heldExpression)  Macro(\"N\",{expression}) [ Local(precision, heldExpression); precision := BuiltinPrecisionGet(); heldExpression := Hold(@expression); `N(@heldExpression, @precision); ];   LocalSymbols(result)  Macro(\"NonN\",{expression}) [ Local(result); GlobalPush(numericMode); numericMode := False; result := (@expression); numericMode := GlobalPop(); result; ];  Function(\"InNumericMode\",{}) numericMode;]; ";
        scriptMap.put("N",scriptString);
        scriptMap.put("NonN",scriptString);
        scriptMap.put("InNumericMode",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "a_NonNegativeInteger? % b_PositiveInteger? <-- Modulo(a,b);";
        scriptMap.put("%",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"++\",{aVar})[ MacroBind(aVar,AddN(Eval(aVar),1)); Eval(aVar);];UnFence(\"++\",1);HoldArgument(\"++\",aVar);";
        scriptMap.put("++",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " RulebaseHoldArguments(\"'\", {expression}); RuleHoldArguments(\"'\", 1, 100, True) `(Hold(@expression)); HoldArgument(\"'\",expression);";
        scriptMap.put("'",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "a_NonNegativeInteger? | b_NonNegativeInteger? <-- BitOr(a,b);";
        scriptMap.put("|",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "50 # x_Number? * y_Number? <-- MultiplyN(x,y);100 # 1 * _x <-- x;100 # _x * 1 <-- x;100 # (_f * _x)_(f=? -1) <-- -x;100 # (_x * _f)_(f=? -1) <-- -x;100 # ((_x)*(_x ^ _m)) <-- x^(m+1);100 # ((_x ^ _m)*(_x)) <-- x^(m+1);100 # ((_x ^ _n)*(_x ^ _m)) <-- x^(m+n);100 # Tan(_x)*Cos(_x) <-- Sin(x);100 # Sinh(_x)*Csch(_x) <-- 1;100 # Cosh(_x)*Sech(_x) <-- 1;100 # Cos(_x)*Tan(_x) <-- Sin(x);100 # Coth(_x)*Sinh(_x) <-- Cosh(x);100 # Tanh(_x)*Cosh(_x) <-- Sinh(x);105 # (f_NegativeNumber? * _x) <-- -(-f)*x;105 # (_x * f_NegativeNumber?) <-- -(-f)*x;95 # x_Matrix? * y_Matrix? <--[ Local(i,j,k,row,result); result:=ZeroMatrix(Length(x),Length(y[1])); For(i:=1,i<=?Length(x),i++) For(j:=1,j<=?Length(y),j++) For(k:=1,k<=?Length(y[1]),k++) [ row:=result[i]; row[k]:= row[k]+x[i][j]*y[j][k]; ]; result;];96 # x_Matrix? * y_List? <--[ Local(i,result); result:={}; For(i:=1,i<=?Length(x),i++) [ DestructiveInsert(result,i,Dot(x[i], y)); ]; result;];97 # (x_List? * y_NonObject?)_Not?(List?(y)) <-- y*x;98 # (x_NonObject? * y_List?)_Not?(List?(x)) <--[ Local(i,result); result:={}; For(i:=1,i<=?Length(y),i++) [ DestructiveInsert(result,i,x * y[i]); ]; result;];50 # _x * Undefined <-- Undefined;50 # Undefined * _y <-- Undefined;100 # 0 * y_Infinity? <-- Undefined;100 # x_Infinity? * 0 <-- Undefined;101 # 0 * (_x) <-- 0;101 # (_x) * 0 <-- 0;100 # x_Number? * (y_Number? * _z) <-- (x*y)*z;100 # x_Number? * (_y * z_Number?) <-- (x*z)*y;100 # (_x * _y) * _y <-- x * y^2;100 # (_x * _y) * _x <-- y * x^2;100 # _y * (_x * _y) <-- x * y^2;100 # _x * (_x * _y) <-- y * x^2;100 # _x * (_y / _z) <-- (x*y)/z;100 # (_y / _z) * _x <-- (x*y)/z;100 # (_x * y_Number?)_Not?(Number?(x)) <-- y*x;100 # (_x) * (_x) ^ (n_Constant?) <-- x^(n+1);100 # (_x) ^ (n_Constant?) * (_x) <-- x^(n+1);100 # (_x * _y)* _x ^ n_Constant? <-- y * x^(n+1);100 # (_y * _x)* _x ^ n_Constant? <-- y * x^(n+1);100 # Sqrt(_x) * (_x) ^ (n_Constant?) <-- x^(n+1/2);100 # (_x) ^ (n_Constant?) * Sqrt(_x) <-- x^(n+1/2);100 # Sqrt(_x) * (_x) <-- x^(3/2);100 # (_x) * Sqrt(_x) <-- x^(3/2);105 # x_Number? * -(_y) <-- (-x)*y;105 # (-(_x)) * (y_Number?) <-- (-y)*x;106 # _x * -(_y) <-- -(x*y);106 # (- _x) * _y <-- -(x*y);150 # (_x) ^ (n_Constant?) * (_x) <-- x^(n-1);250 # x_Number? * y_Infinity? <-- Sign(x)*y;250 # x_Infinity? * y_Number? <-- Sign(y)*x;230 # (aLeft_List? * aRight_List?)_(Length(aLeft)=?Length(aRight)) <-- Map(\"*\",{aLeft,aRight});242 # (x_Integer? / y_Integer?) * (v_Integer? / w_Integer?) <-- (x*v)/(y*w);243 # x_Integer? * (y_Integer? / z_Integer?) <-- (x*y)/z;243 # (y_Integer? / z_Integer?) * x_Integer? <-- (x*y)/z;400 # (_x) * (_x) <-- x^2;400 # x_RationalOrNumber? * Sqrt(y_RationalOrNumber?) <-- Sign(x)*Sqrt(x^2*y);400 # Sqrt(y_RationalOrNumber?) * x_RationalOrNumber? <-- Sign(x)*Sqrt(x^2*y);400 # Sqrt(y_RationalOrNumber?) * Sqrt(x_RationalOrNumber?) <-- Sqrt(y*x);";
        scriptMap.put("*",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "50 # _x ^ (1/2) <-- Sqrt(x);50 # (x_PositiveNumber? ^ (1/2))_Integer?(SqrtN(x)) <-- SqrtN(x);58 # 1 ^ n_Infinity? <-- Undefined;59 # _x ^ 1 <-- x;59 # 1 ^ _n <-- 1;59 # x_Zero? ^ y_Zero? <-- Undefined;60 # (x_Zero? ^ n_RationalOrNumber?)_(n>?0) <-- 0;60 # (x_Zero? ^ n_RationalOrNumber?)_(n<?0) <-- Infinity;59 # _x ^ Undefined <-- Undefined;59 # Undefined ^ _x <-- Undefined;61 # Infinity ^ (y_NegativeNumber?) <-- 0;61 # (-Infinity) ^ (y_NegativeNumber?) <-- 0;61 # x_PositiveNumber? ^ y_PositiveInteger? <-- MathIntPower(x,y);61 # x_PositiveNumber? ^ y_NegativeInteger? <-- 1/MathIntPower(x,-y);65 # (x_PositiveNumber? ^ y_Number?)_InNumericMode() <-- Exp(y*Ln(x));90 # (-_x)^m_Even? <-- x^m;91 # (x_Constant? ^ (m_Odd? / p_Odd?))_(NegativeNumber?(Re(N(Eval(x))))) <-- -((-x)^(m/p));92 # (x_NegativeNumber? ^ y_Number?)_InNumericMode() <-- Exp(y*Ln(x));70 # (_x ^ m_RationalOrNumber?) ^ n_RationalOrNumber? <-- x^(n*m);80 # (x_Number?/y_Number?) ^ n_PositiveInteger? <-- x^n/y^n;80 # (x_Number?/y_Number?) ^ n_NegativeInteger? <-- y^(-n)/x^(-n);80 # x_NegativeNumber? ^ n_Even? <-- (-x)^n;80 # x_NegativeNumber? ^ n_Odd? <-- -((-x)^n);100 # ((x_Number?)^(n_Integer?/(_m)))_(n>?1) <-- MathIntPower(x,n)^(1/m);100 # Sqrt(_n)^(m_Even?) <-- n^(m/2);100 # Abs(_a)^n_Even? <-- a^n;100 # Abs(_a)^n_Odd? <-- Sign(a)*a^n;200 # x_Matrix? ^ n_PositiveInteger? <-- x*(x^(n-1));204 # (xlist_List? ^ nlist_List?)_(Length(xlist)=?Length(nlist)) <-- Map(\"^\",{xlist,nlist});205 # (xlist_List? ^ n_Constant?)_(Not?(List?(n))) <-- Map({{xx},xx^n},{xlist});206 # (_x ^ n_List?)_(Not?(List?(x))) <-- Map({{xx},x^xx},{n});249 # x_Infinity? ^ 0 <-- Undefined;250 # Infinity ^ (_n) <-- Infinity;250 # Infinity ^ (_x_Complex?) <-- Infinity;250 # ((-Infinity) ^ (n_Number?))_(Even?(n)) <-- Infinity;250 # ((-Infinity) ^ (n_Number?))_(Odd?(n)) <-- -Infinity;250 # (x_Number? ^ Infinity)_(x>? -1 And? x <? 1) <-- 0;250 # (x_Number? ^ Infinity)_(x>? 1) <-- Infinity;250 # (x_Complex? ^ Infinity)_(Magnitude(x) >? 1) <-- Infinity;250 # (x_Complex? ^ Infinity)_(Magnitude(x) <? -1) <-- -Infinity;250 # (x_Complex? ^ Infinity)_(Magnitude(x) >? -1 And? Magnitude(x) <? 1) <-- 0;250 # (x_Number? ^ -Infinity)_(x>? -1 And? x <? 1) <-- Infinity;250 # (x_Number? ^ -Infinity)_(x<? -1) <-- 0;250 # (x_Number? ^ -Infinity)_(x>? 1) <-- 0;255 # (x_Complex? ^ Infinity)_(Abs(x) =? 1) <-- Undefined;255 # (x_Complex? ^ -Infinity)_(Abs(x) =? 1) <-- Undefined;400 # _x ^ 0 <-- 1;";
        scriptMap.put("^",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "51 # -Undefined <-- Undefined;54 # - (- _x) <-- x;55 # (- (x_Number?)) <-- SubtractN(0,x);100 # _x - n_Constant?*(_x) <-- (1-n)*x;100 # n_Constant?*(_x) - _x <-- (n-1)*x;100 # Sinh(_x)^2-Cosh(_x)^2 <-- 1;100 # Sinh(_x)-Cosh(_x) <-- Exp(-x);110 # - (_x - _y) <-- y-x;111 # - (x_Number? / _y) <-- (-x)/y; LocalSymbols(x)[ 200 # - (x_List?) <-- MapSingle(\"-\",x);];50 # x_Number? - y_Number? <-- SubtractN(x,y);50 # x_Number? - y_Number? <-- SubtractN(x,y);60 # Infinity - Infinity <-- Undefined;100 # 0 - _x <-- -x;100 # _x - 0 <-- x;100 # _x - _x <-- 0;107 # -( (-(_x))/(_y)) <-- x/y;107 # -( (_x)/(-(_y))) <-- x/y;110 # _x - (- _y) <-- x + y;110 # _x - (y_NegativeNumber?) <-- x + (-y);111 # (_x + _y)- _x <-- y;111 # (_x + _y)- _y <-- x;112 # _x - (_x + _y) <-- - y;112 # _y - (_x + _y) <-- - x;113 # (- _x) - _y <-- -(x+y);113 # (x_NegativeNumber?) - _y <-- -((-x)+y);113 # (x_NegativeNumber?)/_y - _z <-- -((-x)/y+z);LocalSymbols(x,y,xarg,yarg)[ 10 # ((x_List?) - (y_List?))_(Length(x)=?Length(y)) <-- [ Map({{xarg,yarg},xarg-yarg},{x,y}); ];];240 # (x_List? - y_NonObject?)_Not?(List?(y)) <-- -(y-x);241 # (x_NonObject? - y_List?)_Not?(List?(x)) <--[ Local(i,result); result:={}; For(i:=1,i<=?Length(y),i++) [ DestructiveInsert(result,i,x - y[i]); ]; result;];250 # z_Infinity? - Complex(_x,_y) <-- Complex(-x+z,-y);250 # Complex(_x,_y) - z_Infinity? <-- Complex(x-z,y);251 # z_Infinity? - _x <-- z;251 # _x - z_Infinity? <-- -z;250 # Undefined - _y <-- Undefined;250 # _x - Undefined <-- Undefined;210 # x_Number? - (y_Number? / z_Number?) <--(x*z-y)/z;210 # (y_Number? / z_Number?) - x_Number? <--(y-x*z)/z;210 # (x_Number? / v_Number?) - (y_Number? / z_Number?) <--(x*z-y*v)/(v*z);";
        scriptMap.put("-",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "100 # + _x <-- x;50 # x_Number? + y_Number? <-- AddN(x,y);100 # 0 + _x <-- x;100 # _x + 0 <-- x;100 # _x + _x <-- 2*x;100 # _x + n_Constant?*(_x) <-- (n+1)*x;100 # n_Constant?*(_x) + _x <-- (n+1)*x;100 # Sinh(_x)+Cosh(_x) <-- Exp(x);101 # _x + - _y <-- x-y;101 # _x + (- _y)/(_z) <-- x-(y/z);101 # (- _y)/(_z) + _x <-- x-(y/z);101 # (- _x) + _y <-- y-x;102 # _x + y_NegativeNumber? <-- x-(-y);102 # _x + y_NegativeNumber? * _z <-- x-((-y)*z);102 # _x + (y_NegativeNumber?)/(_z) <-- x-((-y)/z);102 # (y_NegativeNumber?)/(_z) + _x <-- x-((-y)/z);102 # (x_NegativeNumber?) + _y <-- y-(-x);150 # _n1 / _d + _n2 / _d <-- (n1+n2)/d;200 # (x_Number? + _y)_Not?(Number?(y)) <-- y+x;200 # ((_y + x_Number?) + _z)_Not?(Number?(y) Or? Number?(z)) <-- (y+z)+x;200 # ((x_Number? + _y) + z_Number?)_Not?(Number?(y)) <-- y+(x+z);200 # ((_x + y_Number?) + z_Number?)_Not?(Number?(x)) <-- x+(y+z);210 # x_Number? + (y_Number? / z_Number?) <--(x*z+y)/z;210 # (y_Number? / z_Number?) + x_Number? <--(x*z+y)/z;210 # (x_Number? / v_Number?) + (y_Number? / z_Number?) <--(x*z+y*v)/(v*z);220 # (xlist_List? + ylist_List?)_(Length(xlist)=?Length(ylist)) <-- Map(\"+\",{xlist,ylist});SumListSide(_x, y_List?) <--[ Local(i,result); result:={}; For(i:=1,i<=?Length(y),i++) [ DestructiveInsert(result,i,x + y[i]); ]; result;];240 # (x_List? + _y)_Not?(List?(y)) <-- SumListSide(y,x);241 # (_x + y_List?)_Not?(List?(x)) <-- SumListSide(x,y);250 # z_Infinity? + Complex(_x,_y) <-- Complex(x+z,y);250 # Complex(_x,_y) + z_Infinity? <-- Complex(x+z,y);251 # z_Infinity? + _x <-- z;251 # _x + z_Infinity? <-- z;250 # Undefined + _y <-- Undefined;250 # _x + Undefined <-- Undefined;";
        scriptMap.put("+",scriptString);
        scriptMap.put("SumListSide",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "50 # 0 / 0 <-- Undefined;52 # x_PositiveNumber? / 0 <-- Infinity;52 # x_NegativeNumber? / 0 <-- -Infinity;55 # (_x / y_Number?)_(Zero?(y)) <-- Undefined;55 # 0 / _x <-- 0;56 # (x_NonZeroInteger? / y_NonZeroInteger?)_(GcdN(x,y) >? 1) <-- [ Local(gcd); Bind(x,x); Bind(y,y); Bind(gcd,GcdN(x,y)); QuotientN(x,gcd)/QuotientN(y,gcd); ];57 # ((x_NonZeroInteger? * _expr) / y_NonZeroInteger?)_(GcdN(x,y) >? 1) <-- [ Local(gcd); Bind(x,x); Bind(y,y); Bind(gcd,GcdN(x,y)); (QuotientN(x,gcd)*expr)/QuotientN(y,gcd); ];57 # ((x_NonZeroInteger?) / (y_NonZeroInteger? * _expr))_(GcdN(x,y) >? 1) <-- [ Local(gcd); Bind(x,x); Bind(y,y); Bind(gcd,GcdN(x,y)); QuotientN(x,gcd)/(QuotientN(y,gcd)*expr); ];57 # ((x_NonZeroInteger? * _p) / (y_NonZeroInteger? * _q))_(GcdN(x,y) >? 1) <-- [ Local(gcd); Bind(x,x); Bind(y,y); Bind(gcd,GcdN(x,y)); (QuotientN(x,gcd)*p)/(QuotientN(y,gcd)*q); ];60 # (x_Decimal? / y_Number?) <-- DivideN(x,y);60 # (x_Number? / y_Decimal?) <-- DivideN(x,y); 60 # (x_Number? / y_Number?)_(InNumericMode()) <-- DivideN(x,y);90 # x_Infinity? / y_Infinity? <-- Undefined;95 # x_Infinity? / y_Number? <-- Sign(y)*x;95 # x_Infinity? / y_Complex? <-- Infinity;90 # Undefined / _y <-- Undefined;90 # _y / Undefined <-- Undefined;100 # _x / _x <-- 1;100 # _x / 1 <-- x;100 # (_x / y_NegativeNumber?) <-- -x/(-y);100 # (_x / - _y) <-- -x/y;100 # Tan(_x)/Sin(_x) <-- (1/Cos(x));100 # 1/Csch(_x) <-- Sinh(x);100 # Sin(_x)/Tan(_x) <-- Cos(x);100 # Sin(_x)/Cos(_x) <-- Tan(x);100 # 1/Sech(_x) <-- Cosh(x);100 # 1/Sec(_x) <-- Cos(x);100 # 1/Csc(_x) <-- Sin(x);100 # Cos(_x)/Sin(_x) <-- (1/Tan(x));100 # 1/Cot(_x) <-- Tan(x);100 # 1/Coth(_x) <-- Tanh(x);100 # Sinh(_x)/Cosh(_x) <-- Tanh(x);150 # (_x) / (_x) ^ (n_Constant?) <-- x^(1-n);150 # Sqrt(_x) / (_x) ^ (n_Constant?) <-- x^(1/2-n);150 # (_x) ^ (n_Constant?) / Sqrt(_x) <-- x^(n-1/2);150 # (_x) / Sqrt(_x) <-- Sqrt(x);200 # (_x / _y)/ _z <-- x/(y*z);230 # _x / (_y / _z) <-- (x*z)/y;240 # (xlist_List? / ylist_List?)_(Length(xlist)=?Length(ylist)) <-- Map(\"/\",{xlist,ylist});250 # (x_List? / _y)_(Not?(List?(y))) <--[ Local(i,result); result:={}; For(i:=1,i<=?Length(x),i++) [ DestructiveInsert(result,i,x[i] / y); ]; result;];250 # (_x / y_List?)_(Not?(List?(x))) <--[ Local(i,result); result:={}; For(i:=1,i<=?Length(y),i++) [ DestructiveInsert(result,i,x/y[i]); ]; result;];250 # _x / Infinity <-- 0;250 # _x / (-Infinity) <-- 0;400 # 0 / _x <-- 0;400 # x_RationalOrNumber? / Sqrt(y_RationalOrNumber?) <-- Sign(x)*Sqrt(x^2/y);400 # Sqrt(y_RationalOrNumber?) / x_RationalOrNumber? <-- Sign(x)*Sqrt(y/(x^2));400 # Sqrt(y_RationalOrNumber?) / Sqrt(x_RationalOrNumber?) <-- Sqrt(y/x);";
        scriptMap.put("/",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(StandardOperatorsLoad,{});RuleHoldArguments(StandardOperatorsLoad, 0, 1, True)[  Infix(\"=?\",90); Infix(\"And?\",1000); RightAssociativeSet(\"And?\"); Infix(\"Or?\", 1010); Prefix(\"Not?\", 100); Infix(\"<?\",90); Infix(\">?\",90); Infix(\"<=?\",90); Infix(\">=?\",90); Infix(\"!=?\",90); Infix(\":=\",10000); RightAssociativeSet(\":=\");  RulebaseHoldArguments(\":\",{left,right}); Infix(\":\",70); Infix(\"+\",70); Infix(\"-\",70); RightPrecedenceSet(\"-\",40); Infix(\"/\",30); Infix(\"*\",40); Infix(\"^\",20); LeftPrecedenceSet(\"^\",19);  RightAssociativeSet(\"^\"); Prefix(\"+\",50); Prefix(\"-\",50); RightPrecedenceSet(\"-\",40); Bodied(\"For\",60000); Bodied(\"Until\",60000); Postfix(\"++\",5); Postfix(\"--\",5); Bodied(\"ForEach\",60000); Infix(\"<<\",10); Infix(\">>\",10); Bodied(\"Differentiate\",60000); Bodied(\"Deriv\",60000); Infix(\"X\",30); Infix(\".\",30); Infix(\"o\",30); Postfix(\"!\", 30); Postfix(\"!!\", 30); Infix(\"***\", 50); Bodied(\"Integrate\",60000); Bodied(\"Limit\",60000); Bodied(\"EchoTime\", 60000); Bodied(\"Repeat\", 60000); Infix(\"->\",600);  Infix(\"~\",70); RightAssociativeSet(\"~\"); Infix(\"@\",600); Infix(\"/@\",600); Infix(\"..\",600); Bodied(\"Taylor\",60000); Bodied(\"Taylor1\",60000); Bodied(\"Taylor2\",60000); Bodied(\"Taylor3\",60000); Bodied(\"InverseTaylor\",60000); Infix(\"<--\",10000); Infix(\"#\",9900); Bodied(\"TSum\",60000); Bodied(\"TExplicitSum\",60000); Bodied(\"TD\",5);   Infix(\"==\",90); Infix(\"!==\",90);  Infix(\"Implies?\",10000);  Infix(\"Equivales?\",10000);  Bodied(\"if\",5); Infix(\"else\",60000); RightAssociativeSet(\"else\");  Infix(\"&\",50); Infix(\"|\",50); Infix(\"%\",50);  Infix(\"/:\",20000); Infix(\"/::\",20000); Infix(\"<-\",10000);  Infix(\"<>\", PrecedenceGet(\"=?\")); Infix(\"<=>\", PrecedenceGet(\"=?\"));  Infix(\"Where\", 11000); Infix(\"AddTo\", 2000); Bodied(\"Function\",60000); Bodied(\"Macro\",60000); Bodied(Assert, 60000);  Bodied(\"Defun\",0); Bodied(\"TemplateFunction\",60000); Bodied(\"Taylor3TaylorCoefs\",0); Infix(\":*:\",3);];";
        scriptMap.put("StandardOperatorsLoad",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ArrayCreateFromList(list):=[ Local(result,i); result:=ArrayCreate(Length(list),0); i:=1; While (list !=? {}) [ result[i]:=First(list); i++; list:=Rest(list); ]; result;];";
        scriptMap.put("ArrayCreateFromList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ArrayToList(array):= (array[1 .. ArraySize(array) ]);";
        scriptMap.put("ArrayToList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Assoc\",{key,list}) BuiltinAssoc(key,list);";
        scriptMap.put("Assoc",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(hash, key, element, hashexpr)[10 # AssocDelete(hash_List?, element_List?) <--[ Local(index); index := Find(hash, element); If( index >? 0, DestructiveDelete(hash, index) ); index>?0; ];20 # AssocDelete(hash_List?, key_String?) <--[ AssocDelete(hash, BuiltinAssoc(key, hash));];30 # AssocDelete(hash_List?, Empty) <-- False;]; ";
        scriptMap.put("AssocDelete",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "AssocIndices(associndiceslist_List?) <-- DestructiveReverse(MapSingle(\"First\",associndiceslist));";
        scriptMap.put("AssocIndices",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "AssocValues(assocvalueslist_List?) <-- DestructiveReverse(MapSingle(Lambda({x},First(Rest(x))),assocvalueslist));";
        scriptMap.put("AssocValues",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"CosN\",{x})Trigonometry(x,0.0,1.0,1.0);";
        scriptMap.put("CosN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"ExpN\", {x}) If(Equal?(x,0),1, If(LessThan?(x,0),DivideN(1, ExpN(MathNegate(x))), If(GreaterThan?(x,1), MathExpDoubling(MathExpTaylor0(MathMul2Exp(x,MathNegate(MathBitCount(x)))), MathBitCount(x)), MathExpTaylor0(x) )));";
        scriptMap.put("ExpN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"MathExpDoubling\", {value, n})[ Local(shift, result); Bind(shift, n); Bind(result, value); While (GreaterThan?(shift,0))  [ Bind(result, MultiplyN(result, result)); Bind(shift, AddN(shift,MathNegate(1))); ]; result;];";
        scriptMap.put("MathExpDoubling",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"MathExpTaylor0\",{x})[ Check(x<=?1 And? x>=?0,\"\",\" Argument must be between 0 and 1 inclusive.\"); Local(i,aResult,term,eps);     Bind(i,0);  Bind(aResult,1.0);  Bind(term,1.0); Bind(eps,MathIntPower(10,MathNegate(BuiltinPrecisionGet())));   While(GreaterThan?(AbsN(term),eps)) [  Bind(i,AddN(i,1));  Bind(term,DivideN(MultiplyN(term,x),i));  Bind(aResult,AddN(aResult,term)); ]; aResult;];";
        scriptMap.put("MathExpTaylor0",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "";
        scriptMap.put("MathFloatPower",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"MathIntPower\", {x,y}) If(Equal?(x,0),0,If(Equal?(x,1),1, If(Integer?(y),If(LessThan?(y,0),  DivideN(1, PositiveIntPower(AddN(x,0.),MathNegate(y))),  PositiveIntPower(x,y) ),  False) ));";
        scriptMap.put("MathIntPower",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"MathMul2Exp\", {x,n})  If(GreaterThan?(n,0), MultiplyN(x, MathIntPower(2,n)), DivideN(x, MathIntPower(2,MathNegate(n))));";
        scriptMap.put("MathMul2Exp",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"MathPi\",{})[    Local(initialPrec,curPrec,result,aPrecision); Bind(aPrecision,BuiltinPrecisionGet()); Bind(initialPrec, aPrecision);  Bind(curPrec, 40);  Bind(result, 3.141592653589793238462643383279502884197169399);   While (GreaterThan?(initialPrec, MultiplyN(curPrec,3))) [ Bind(initialPrec, FloorN(DivideN(AddN(initialPrec,2),3))); ]; Bind(curPrec, initialPrec); While (Not?(GreaterThan?(curPrec, aPrecision))) [   BuiltinPrecisionSet(curPrec); Bind(result,AddN(result,SinN(result)));    If (Equal?(curPrec, aPrecision),  [ Bind(curPrec, AddN(aPrecision,1));  ], [ Bind(curPrec, MultiplyN(curPrec,3));   If (GreaterThan?(curPrec, aPrecision), [ Bind(curPrec, aPrecision);  ]); ]); ]; BuiltinPrecisionSet(aPrecision); result;];";
        scriptMap.put("MathPi",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"PositiveIntPower\", {x,n})[ Local(result,unit); If(LessThan?(n,0), False, [ Bind(unit,1);  Bind(result, unit); If(Equal?(n,0),unit, If(Equal?(n,1),x, [ While(GreaterThan?(n,0)) [ If( Equal?(BitAnd(n,1), 1), Bind(result, MultiplyN(result,x)) ); Bind(x, MultiplyN(x,x)); Bind(n,ShiftRight(n,1)); ]; result; ] ) ); ]);];";
        scriptMap.put("PositiveIntPower",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"PowerN\", {x,y}) If(Equal?(x,0),0, If(Equal?(x,1),1, If(Integer?(y), MathIntPower(x,y), False) ));";
        scriptMap.put("PowerN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"SinN\",{x})Trigonometry(x,1.0,x,x);";
        scriptMap.put("SinN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"TanN\",{x})DivideN(SinN(x),CosN(x));";
        scriptMap.put("TanN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"Trigonometry\",{x,i,sum,term})[ Local(x2,orig,eps,previousPrec,newPrec); Bind(previousPrec,BuiltinPrecisionGet()); Bind(newPrec,AddN(BuiltinPrecisionGet(),2)); Bind(x2,MultiplyN(x,x)); BuiltinPrecisionSet(newPrec); Bind(eps,MathIntPower(10,MathNegate(previousPrec))); While(GreaterThan?(AbsN(term),eps)) [ Bind(term,MultiplyN(term,x2)); Bind(i,AddN(i,1.0)); Bind(term,DivideN(term,i)); Bind(i,AddN(i,1.0)); Bind(term,DivideN(MathNegate(term),i)); BuiltinPrecisionSet(previousPrec); Bind(sum, AddN(sum, term)); BuiltinPrecisionSet(newPrec); ]; BuiltinPrecisionSet(previousPrec); sum;];";
        scriptMap.put("Trigonometry",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Easter(year) := [ Check(PositiveInteger?(year), \"Argument\", \"The argument must be a positive integer\"); Local(a,b,c,d,e,f,g,h,i,k,L,m,month,day); a := Modulo(year, 19); b := Quotient(year, 100); c := Modulo(year, 100); d := Quotient(b, 4); e := Modulo(b, 4); f := Quotient(b + 8, 25); g := Quotient(b - f + 1, 3); h := Modulo(19*a + b - d - g + 15, 30); i := Quotient(c, 4); k := Modulo(c, 4); L := Modulo(32 + 2*e + 2*i - h - k, 7); m := Quotient(a + 11*h + 22*L, 451); month := Quotient(h + L - 7*m + 114, 31); day := Modulo(h + L - 7*m + 114, 31) + 1; { month, day };];";
        scriptMap.put("Easter",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # BinomialCoefficient(0,0) <-- 1;10 # BinomialCoefficient(n_PositiveInteger?,m_NonNegativeInteger?)_(2*m <=? n) <-- ((n-m+1) *** n) / m!;15 # BinomialCoefficient(n_PositiveInteger?,m_NonNegativeInteger?)_(2*m >? n And? m <=? n) <-- BinomialCoefficient(n, n-m);20 # BinomialCoefficient(n_Integer?,m_Integer?) <-- 0;Combinations(n,m) := BinomialCoefficient(n,m);";
        scriptMap.put("Combinations",scriptString);
        scriptMap.put("BinomialCoefficient",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"PermutationsList\",{result,list})[ If(Length(list) =? 0, [ result; ], [ Local(head); Local(newresult); Local(i); head:=list[1]; newresult:={}; ForEach(item,result) [ For(i:=Length(item)+1,i>?0,i--) [ DestructiveInsert(newresult,1,Insert(item,i,head)); ]; ]; newresult:=DestructiveReverse(newresult); PermutationsList(newresult,Rest(list)); ]);];Function(\"PermutationsList\",{list})[ PermutationsList({{}},list);];";
        scriptMap.put("PermutationsList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Arg(Complex(Cos(_x),Sin(_x))) <-- x;10 # Arg(x_Zero?) <-- Undefined;15 # Arg(x_PositiveReal?) <-- 0;15 # Arg(x_NegativeReal?) <-- Pi;20 # Arg(Complex(r_Zero?,i_Constant?)) <-- Sign(i)*Pi/2;30 # Arg(Complex(r_PositiveReal?,i_Constant?)) <-- ArcTan(i/r);40 # Arg(Complex(r_NegativeReal?,i_PositiveReal?)) <-- Pi+ArcTan(i/r);50 # Arg(Complex(r_NegativeReal?,i_NegativeReal?)) <-- ArcTan(i/r)-Pi;";
        scriptMap.put("Arg",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "0 # Complex(_r,i_Zero?) <-- r;2 # Complex(Complex(_r1,_i1),_i2) <-- Complex(r1,i1+i2);2 # Complex(_r1,Complex(_r2,_i2)) <-- Complex(r1-i2,r2);6 # Complex(Undefined,_x) <-- Undefined;6 # Complex(_x,Undefined) <-- Undefined;110 # Complex(_r1,_i1) + Complex(_r2,_i2) <-- Complex(r1+r2,i1+i2);300 # Complex(_r,_i) + x_Constant? <-- Complex(r+x,i);300 # x_Constant? + Complex(_r,_i) <-- Complex(r+x,i);110 # - Complex(_r,_i) <-- Complex(-r,-i);300 # Complex(_r,_i) - x_Constant? <-- Complex(r-x,i);300 # x_Constant? - Complex(_r,_i) <-- Complex((-r)+x,-i);111 # Complex(_r1,_i1) - Complex(_r2,_i2) <-- Complex(r1-r2,i1-i2);110 # Complex(_r1,_i1) * Complex(_r2,_i2) <-- Complex(r1*r2-i1*i2,r1*i2+r2*i1);250 # Complex(r_Zero?,_i) * x_Infinity? <-- Complex(0,i*x);250 # Complex(_r,i_Zero?) * x_Infinity? <-- Complex(r*x,0);251 # Complex(_r,_i) * x_Infinity? <-- Complex(r*x,i*x);250 # x_Infinity? * Complex(r_Zero?,_i) <-- Complex(0,i*x);250 # x_Infinity? * Complex(_r,i_Zero?) <-- Complex(r*x,0);251 # x_Infinity? * Complex(_r,_i) <-- Complex(r*x,i*x);300 # Complex(_r,_i) * y_Constant? <-- Complex(r*y,i*y);300 # y_Constant? * Complex(_r,_i) <-- Complex(r*y,i*y);330 # Complex(_r,_i) * (y_Constant? / _z) <-- (Complex(r*y,i*y))/z;330 # (y_Constant? / _z) * Complex(_r,_i) <-- (Complex(r*y,i*y))/z;110 # x_Constant? / Complex(_r,_i) <-- (x*Conjugate(Complex(r,i)))/(r^2+i^2);300 # Complex(_r,_i) / y_Constant? <-- Complex(r/y,i/y);110 # (_x ^ Complex(_r,_i)) <-- Exp(Complex(r,i)*Ln(x));110 # (Complex(_r,_i) ^ x_RationalOrNumber?)_(Not?(Integer?(x))) <-- Exp(x*Ln(Complex(r,i)));123 # Complex(_r, _i) ^ n_NegativeInteger? <-- 1/Complex(r, i)^(-n);124 # Complex(_r, _i) ^ (p_Zero?) <-- 1; 125 # Complex(_r, _i) ^ n_PositiveInteger? <--[  Local(result, x); x:=Complex(r,i); result:=1; While(n >? 0) [ if ((n&1) =? 1) [ result := result*x; ]; x := x*x; n := n>>1; ]; result;];";
        scriptMap.put("Complex",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ComplexII?(_c) <-- (ImII(c) !=? 0);";
        scriptMap.put("ComplexII?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1 # Complex?(x_RationalOrNumber?) <-- True;2 # Complex?(Complex(_r,_i)) <-- True;3 # Complex?(_x) <-- False;";
        scriptMap.put("Complex?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(a,x)[Function(\"Conjugate\",{a}) Substitute(a,{{x},Type(x)=?\"Complex\"},{{x},Complex(x[1],-(x[2]))});]; ";
        scriptMap.put("Conjugate",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # II^n_NegativeInteger? <-- (-II)^(-n);20 # (II^_n)_(Even?(n) =? True) <-- (-1)^(n>>1);20 # (II^_n)_(Odd?(n) =? True) <-- II*(-1)^(n>>1);";
        scriptMap.put("II",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "110 # Im(Complex(_r,_i)) <-- i;120 # Im(Undefined) <-- Undefined;300 # Im(_x) <-- 0;";
        scriptMap.put("Im",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ImII(_c) <-- NN(c)[2];";
        scriptMap.put("ImII",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Magnitude\",{x}) [ Sqrt(Re(x)^2 + Im(x)^2);];";
        scriptMap.put("Magnitude",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(complexReduce) [ Bind(complexReduce, Hold( { Exp(x_ComplexII?) <- Exp(ReII(x))*(Cos(ImII(x))+II*Sin(ImII(x))) })); NN(_c) <-- [ Local(result); c := (c /:: complexReduce); result := Coef(Expand(c,II),II,{0,1}); result; ];]; ";
        scriptMap.put("NN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "110 # Re(Complex(_r,_i)) <-- r;120 # Re(Undefined) <-- Undefined;300 # Re(_x) <-- x;";
        scriptMap.put("Re",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ReII(_c) <-- NN(c)[1];";
        scriptMap.put("ReII",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SetGlobalLazyVariable(I,Complex(0,1));LocalSymbols(CacheOfConstantsN) [RulebaseHoldArguments(\"CachedConstant\", {Ccache, Catom, Cfunc});UnFence(\"CachedConstant\", 3); HoldArgument(\"CachedConstant\", Cfunc);HoldArgument(\"CachedConstant\", Ccache); RuleHoldArguments(\"CachedConstant\", 3, 10, And?(Atom?(Catom), Function?(Cfunc)))[ Local(Cname,CfunctionName); Bind(Cname, ToString(Catom));  Bind(CfunctionName,ConcatStrings(\"Internal\",Cname)); If(  Atom?(Eval(Ccache)), MacroBind(Eval(Ccache), {}) );  If( Equal?(BuiltinAssoc(Cname, Eval(Ccache)), Empty),  [  MacroUnbind(Catom);  DestructiveInsert(Eval(Ccache), 1, {Cname, 0, 0});   RulebaseEvaluateArguments(CfunctionName, {}); `( RuleHoldArguments(@CfunctionName, 0, 1024, True) [ Local(newprec, newC, cachedC); Bind(newprec, BuiltinPrecisionGet());   Bind(cachedC, BuiltinAssoc(@Cname, @Ccache)); If( MathNth(cachedC, 2) !=? newprec, [  If(Equal?(InVerboseMode(),True), Echo(\"CachedConstant: Info: constant \", @Cname, \" is being recalculated at precision \", newprec)); Bind(newC, RoundTo(Eval(@Cfunc),newprec)); DestructiveReplace(cachedC, 2, newprec); DestructiveReplace(cachedC, 3, newC); newC; ],  MathNth(cachedC, 3) ); ]);   ],  Echo(\"CachedConstant: Warning: constant \", Catom, \" already defined\") );];RuleHoldArguments(\"CachedConstant\", 3, 20, True) Echo(\"CachedConstant: Error: \", Catom, \" must be an atom and \", Cfunc, \" must be a function.\");Function(\"AssignCachedConstantsN\", {})[ Local(var,fname); ForEach(var, AssocIndices(CacheOfConstantsN)) [ MacroUnbind(ToAtom(var)); Bind(fname,ConcatStrings(\"Internal\",var)); Bind(var,ToAtom(var));  `SetGlobalLazyVariable((@var), ListToFunction({ToAtom(fname)})); ];];UnFence(\"AssignCachedConstantsN\", 0);Function(\"ClearCachedConstantsN\", {})[ Local(centry); ForEach(centry, CacheOfConstantsN) MacroUnbind(ToAtom(centry[1]));];UnFence(\"ClearCachedConstantsN\", 0);CachedConstant(CacheOfConstantsN, Pi,[ Local(result,oldprec); Bind(oldprec,BuiltinPrecisionGet());If(Equal?(InVerboseMode(),True), Echo(\"Recalculating Pi at precision \",oldprec+5)); BuiltinPrecisionSet(BuiltinPrecisionGet()+5); result := MathPi();If(Equal?(InVerboseMode(),True),Echo(\"Switching back to precision \",oldprec)); BuiltinPrecisionSet(oldprec); result;]);CachedConstant(CacheOfConstantsN, gamma, GammaConstNum());CachedConstant(CacheOfConstantsN, GoldenRatio, N( (1+Sqrt(5))/2 ) );CachedConstant(CacheOfConstantsN, Catalan, CatalanConstNum() );]; ";
        scriptMap.put("I",scriptString);
        scriptMap.put("CachedConstant",scriptString);
        scriptMap.put("AssignCachedConstantsN",scriptString);
        scriptMap.put("ClearCachedConstantsN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TemplateFunction(\"For\",{start,predicate,increment,body})[ Eval(start); While (Equal?(Eval(predicate),True)) [ Eval(body); Eval(increment); ];];UnFence(\"For\",4);HoldArgumentNumber(\"For\",4,1);HoldArgumentNumber(\"For\",4,2);HoldArgumentNumber(\"For\",4,3);HoldArgumentNumber(\"For\",4,4);";
        scriptMap.put("For",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TemplateFunction(\"ForEach\",{item,listOrString,body})[ If(And?(Equal?(Generic?(listOrString),True), Equal?(GenericTypeName(listOrString),\"Array\") ), `ForEachInArray(@item,listOrString,@body), [ MacroLocal(item);  If(String?(listOrString), [  Local(index, stringLength);  stringLength := Length(listOrString);  index := 1; While(index <=? stringLength ) [ MacroBind(item,listOrString[index] );  Eval(body);  index++; ]; ], [ Local(foreachtail); Bind(foreachtail,listOrString); While(Not?(Equal?(foreachtail,{}))) [ MacroBind(item,First(foreachtail)); Eval(body); Bind(foreachtail,Rest(foreachtail)); ]; ]); ]);];UnFence(\"ForEach\",3);HoldArgumentNumber(\"ForEach\",3,1);HoldArgumentNumber(\"ForEach\",3,3);";
        scriptMap.put("ForEach",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(i,nr)[ TemplateFunction(\"ForEachInArray\",{item,list,body}) [ Local(i,nr); MacroLocal(item); Bind(i,1); Bind(nr,Length(list)); While(i<=?nr) [ MacroBind(item,list[i]); Eval(body); Bind(i,AddN(i,1)); ]; ];];UnFence(\"ForEachInArray\",3);HoldArgumentNumber(\"ForEachInArray\",3,1);HoldArgumentNumber(\"ForEachInArray\",3,3);";
        scriptMap.put("ForEachInArray",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MacroRulebaseHoldArguments(\"Lambda\",{args,body});";
        scriptMap.put("Lambda",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TemplateFunction(\"Until\",{predicate,body})[ Eval(body); While (Equal?(Eval(predicate),False)) [ Eval(body); ]; True;];UnFence(\"Until\",2);HoldArgumentNumber(\"Until\",2,1);HoldArgumentNumber(\"Until\",2,2);";
        scriptMap.put("Until",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"else\",{ifthen,otherwise});0 # (if (_predicate) _body else _otherwise)_(Eval(predicate) =? True) <-- Eval(body);0 # (if (_predicate) _body else _otherwise)_(Eval(predicate) =? False) <-- Eval(otherwise);1 # (if (_predicate) _body else _otherwise) <-- ListToFunction({ToAtom(\"else\"), ListToFunction({ToAtom(\"if\"), (Eval(predicate)), body}), otherwise});HoldArgument(\"else\",ifthen);HoldArgument(\"else\",otherwise);UnFence(\"else\",2);";
        scriptMap.put("else",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"if\",{predicate,body});(if(True) _body) <-- Eval(body);HoldArgument(\"if\",body);UnFence(\"if\",2);";
        scriptMap.put("if",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(TraceStart,TraceEnter,TraceLeave,DebugStart,DebugEnter, DebugLeave,ProfileStart,ProfileEnter,result, WriteLines,ClearScreenString,DebugFileLoaded, DebugFileLines, DebugNrLines, debugstepoverfile, debugstepoverline) [TraceStart() := [indent := 0;];TraceEnter() :=[ indent++; Space(2*indent); Echo(\"Enter \",CustomEvalExpression());];TraceLeave() :=[ Space(2*indent); Echo(\"Leave \",CustomEvalResult()); indent--;];Macro(TraceExp,{expression})[ TraceStart(); CustomEval(TraceEnter(),TraceLeave(),CustomEvalStop(),@expression);];DebugStart():=[ debugging:=True; debugstopdepth := -1; breakpoints:={}; filebreakpoints := {}; debugstopped:=False; debugverbose:=False; debugcallstack:={}; breakpredicate:=False;];DebugRun():= [debugging:=False;True;];DebugStep():=[debugging:=False;nextdebugging:=True;];DebugStepOver():=[ debugging:=False; debugstepoverfile := DebugFile(CustomEvalExpression()); debugstepoverline := DebugLine(CustomEvalExpression()); debugstopdepth := Length(debugcallstack);];DebugBreakAt(file,line):=[ Check(DebugMode?(), \"Mode\", \"DebugBreakAt only supported in the debug build of MathPiper\"); If(filebreakpoints[file] =? Empty,filebreakpoints[file]:={}); DestructiveAppend(filebreakpoints[file],line);];DebugRemoveBreakAt(file,line):=[ Check(DebugMode?(), \"Mode\", \"DebugRemoveBreakAt only supported in the debug build of MathPiper\"); If(filebreakpoints[file] =? Empty,filebreakpoints[file]:={}); filebreakpoints[file] := Difference(filebreakpoints[file],{line});];DebugStop():=[debugging:=False;debugstopped:=True;CustomEvalStop();];DebugVerbose(verbose):=[debugverbose:=verbose;];DebugAddBreakpoint(fname_String?) <-- [ breakpoints := fname~breakpoints;];Macro(DebugBreakIf,{predicate})[ breakpredicate:= Hold(@predicate);];BreakpointsClear() <--[ breakpredicate:=False; breakpoints := {};];Macro(DebugLocals,{})[ Echo(\"\"); Echo(\"*************** Current locals on the stack ****************\"); ForEach(item,CustomEvalLocals()) [ Echo(\" \",item,\" : \",Eval(item)); ]; Echo(\"\");];DebugCallstack() <--[ Echo(\"\"); Echo(\"*************** Function call stack ****************\"); ForEach(item,debugcallstack) [ if(Function?(item)) Echo(\" Function \",Type(item),\" : \",item) else Echo(\" Variable \",item); ]; Echo(\"\");];Macro(DebugEnter,{})[ debugcallstack := CustomEvalExpression()~debugcallstack;  If(debugging =? False And? Eval(breakpredicate) =? True, [ breakpredicate:=False; debugging:=True; ]); If(debugging =? False And? DebugMode?(), [ Local(file,line); file := DebugFile(CustomEvalExpression()); If(filebreakpoints[file] !=? Empty, [ line := DebugLine(CustomEvalExpression()); If(Not?(file =? debugstepoverfile And? line =? debugstepoverline) And? Contains?(filebreakpoints[file],line), [ debugging:=True; ] ); ]); ]);  If(debugging =? False And? Function?(CustomEvalExpression()) And? Contains?(breakpoints,Type(CustomEvalExpression())), debugging:=True); nextdebugging:=False; If (debugging, [ If(DebugMode?(),DebugShowCode()); Echo(\">>> \",CustomEvalExpression()); While(debugging) [ Echo(\"DebugResult: \",Eval(PipeFromString(ReadCmdLineString(\"Debug> \")~\";\")Read()));  If(IsExitRequested(),debugging:=False); ]; ]); debugging:=nextdebugging; If(IsExitRequested(),debugstopped:=True);];Macro(DebugLeave,{})[ If(debugging =? False And? debugstopdepth >=? 0 And? Length(debugcallstack) =? debugstopdepth, [ debugstepoverline := -1; debugging := True; debugstopdepth := -1; ]); debugcallstack := Rest(debugcallstack); If(debugverbose,Echo(CustomEvalResult(),\" <-- \",CustomEvalExpression()));];Macro(Debug,{expression})PipeToStdout()[ DebugStart(); CustomEval(DebugEnter(),DebugLeave(),If(debugstopped,Check(False, \"Debug\", \"\"),[debugging:=True;debugcallstack := Rest(debugcallstack);]),@expression);];ProfileStart():=[ profilefn:={};];10 # ProfileEnter()_(Function?(CustomEvalExpression())) <--[ Local(fname); fname:=Type(CustomEvalExpression()); If(profilefn[fname]=?Empty,profilefn[fname]:=0); profilefn[fname] := profilefn[fname]+1;];Macro(Profile,{expression})[ ProfileStart(); CustomEval(ProfileEnter(),True,CustomEvalStop(),@expression); ForEach(item,profilefn) Echo(\"Function \",item[1],\" called \",item[2],\" times\");];Macro(EchoTime,{expression})[ Local(result); Echo(Time()Bind(result, @expression), \"seconds taken.\"); result;];ClearScreenString := UnicodeToString(27)~\"[2J\"~UnicodeToString(27)~\"[1;1H\";WriteLines(filename,lines,from,nrlines,breakpoints,current):=[ Local(i,nr); nr:=Length(lines); WriteString(ClearScreenString); Echo(\"File \",filename,\" at line \",current); For(i:=from,i<?from+nrlines And? i<?nr,i++) [ if (current =? i) WriteString(\">\") else WriteString(\" \"); if (Contains?(breakpoints,i)) WriteString(\"*\") else WriteString(\" \"); WriteString(\"| \"); Echo(lines[i][1]); ];];DebugFileLoaded := \"\";DebugFileLines := {};DebugNrLines:=20;DebugShowCode():=[ False;];]; ";
        scriptMap.put("TraceExp",scriptString);
        scriptMap.put("Debug",scriptString);
        scriptMap.put("Profile",scriptString);
        scriptMap.put("DebugRun",scriptString);
        scriptMap.put("DebugStep",scriptString);
        scriptMap.put("DebugStepOver",scriptString);
        scriptMap.put("DebugBreakAt",scriptString);
        scriptMap.put("DebugRemoveBreakAt",scriptString);
        scriptMap.put("DebugStop",scriptString);
        scriptMap.put("DebugVerbose",scriptString);
        scriptMap.put("DebugAddBreakpoint",scriptString);
        scriptMap.put("BreakpointsClear",scriptString);
        scriptMap.put("DebugCallstack",scriptString);
        scriptMap.put("DebugBreakIf",scriptString);
        scriptMap.put("DebugLocals",scriptString);
        scriptMap.put("EchoTime",scriptString);
        scriptMap.put("DebugShowCode",scriptString);
        scriptMap.put("ProfileEnter",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(Verbose) [ Bind(Verbose,False); Function(\"V\",{aNumberBody}) [ Local(prevVerbose,result); Bind(prevVerbose,Verbose); Bind(Verbose,True); Bind(result,Eval(aNumberBody)); Bind(Verbose,prevVerbose); result; ]; Function(\"InVerboseMode\",{}) Verbose;]; HoldArgument(\"V\",aNumberBody);UnFence(\"V\",1);";
        scriptMap.put("V",scriptString);
        scriptMap.put("InVerboseMode",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"AssignArray\", {listOrAssociationList, indexOrKey, newValue});UnFence(\"AssignArray\", 3);RuleHoldArguments(\"AssignArray\", 3, 1, String?(indexOrKey))[ Local(keyValuePair); keyValuePair := Assoc(indexOrKey, listOrAssociationList); If(keyValuePair =? Empty, DestructiveInsert(listOrAssociationList, 1 , {indexOrKey, newValue}), DestructiveReplace(keyValuePair, 2 , newValue) ); True;];RuleHoldArguments(\"AssignArray\",3,1, And?( Equal?(Generic?(listOrAssociationList), True), Equal?(GenericTypeName(listOrAssociationList), \"Array\")))[ ArraySet(listOrAssociationList, indexOrKey, newValue);];RuleHoldArguments(\"AssignArray\",3 , 2, True)[ DestructiveReplace(listOrAssociationList, indexOrKey, newValue); True;];";
        scriptMap.put("AssignArray",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MacroRulebaseHoldArguments(\"Defun\",{func,args,body});RuleHoldArguments(\"Defun\",3,0,True)[ Local(nrargs); Bind(nrargs,Length(@args)); Retract(@func, `(@nrargs)); RulebaseHoldArguments(@func,@args); Local(fn,bd); Bind(fn,Hold(@func)); Bind(bd,Hold(@body)); `RuleHoldArguments(@fn, @nrargs, 0,True)(@bd);];";
        scriptMap.put("Defun",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Function\",{oper,args,body});RuleHoldArguments(\"Function\",3,2047, And?(GreaterThan?(Length(args), 1), Equal?( MathNth(args, Length(args)), ToAtom(\"...\") )))[ DestructiveDelete(args,Length(args));  Retract(oper,Length(args)); RulebaseListedEvaluateArguments(oper,args); RuleEvaluateArguments(oper,Length(args),1025,True) body; ];RuleHoldArguments(\"Function\",3,2048,True)[ Retract(oper,Length(args)); RulebaseEvaluateArguments(oper,args); RuleEvaluateArguments(oper,Length(args),1025,True) body;];RulebaseHoldArguments(\"Function\",{oper});RuleHoldArguments(\"Function\",1,2047, And?(Function?(oper), GreaterThan?(Length(oper), 1), Equal?( MathNth(oper, Length(oper)), ToAtom(\"...\") )))[ Local(args); Bind(args,Rest(FunctionToList(oper))); DestructiveDelete(args,Length(args));  If(RulebaseDefined(Type(oper),Length(args)), False,  RulebaseListedEvaluateArguments(Type(oper),args) );];RuleHoldArguments(\"Function\",1,2048, And?(Function?(oper)))[ Local(args); Bind(args,Rest(FunctionToList(oper))); If(RulebaseDefined(Type(oper),Length(args)), False,  RulebaseEvaluateArguments(Type(oper),args) );];HoldArgument(\"Function\",oper);HoldArgument(\"Function\",args);HoldArgument(\"Function\",body);";
        scriptMap.put("Function",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"HoldArgumentNumber\",{function,arity,index})[ Local(args); args:=RulebaseArgumentsList(function,arity); ApplyFast(\"HoldArgument\",{function,args[index]});];";
        scriptMap.put("HoldArgumentNumber",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Macro\",{oper,args,body});HoldArgument(\"Macro\",oper);HoldArgument(\"Macro\",args);HoldArgument(\"Macro\",body);RuleHoldArguments(\"Macro\",3,2047, And?(GreaterThan?(Length(args), 1), Equal?( MathNth(args, Length(args)), ToAtom(\"...\") )))[ DestructiveDelete(args,Length(args));  Retract(oper,Length(args)); `MacroRulebaseListedHoldArguments(@oper,@args); RuleEvaluateArguments(oper,Length(args),1025,True) body; ];RuleHoldArguments(\"Macro\",3,2048,True)[ Retract(oper,Length(args)); `MacroRulebaseHoldArguments(@oper,@args); RuleEvaluateArguments(oper,Length(args),1025,True) body;];RulebaseHoldArguments(\"Macro\",{oper});RuleHoldArguments(\"Macro\",1,2047, And?(Function?(oper), GreaterThan?(Length(oper), 1), Equal?( MathNth(oper, Length(oper)), ToAtom(\"...\") )))[ Local(args,name); Bind(args,Rest(FunctionToList(oper))); DestructiveDelete(args,Length(args));  Bind(name,Type(oper)); If(RulebaseDefined(Type(oper),Length(args)), False,  `MacroRulebaseListedHoldArguments(@name,@args) );];RuleHoldArguments(\"Macro\",1,2048, And?(Function?(oper)))[ Local(args,name); Bind(args,Rest(FunctionToList(oper))); Bind(name,Type(oper)); If(RulebaseDefined(Type(oper),Length(args)), False,  [ `MacroRulebaseHoldArguments(@name,@args); ] );];";
        scriptMap.put("Macro",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"TemplateFunction\",{oper,args,body});HoldArgument(\"TemplateFunction\",oper);HoldArgument(\"TemplateFunction\",args);HoldArgument(\"TemplateFunction\",body);RuleHoldArguments(\"TemplateFunction\",3,2047,True)[ Retract(oper,Length(args)); Local(arglist); arglist:=FlatCopy(args); DestructiveAppend(arglist,{args,ListToFunction({Hold,body})}); arglist:=ApplyFast(\"LocalSymbols\",arglist); RulebaseEvaluateArguments(oper,arglist[1]); RuleEvaluateArguments(oper,Length(args),1025,True) arglist[2];];";
        scriptMap.put("TemplateFunction",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Unholdable\",{var});HoldArgument(\"Unholdable\",var);UnFence(\"Unholdable\",1);RuleHoldArguments(\"Unholdable\",1,10,Equal?(Type(Eval(var)),\"Eval\"))[ MacroBind(var,Eval(Eval(var))); ];RuleHoldArguments(\"Unholdable\",1,20,True)[  True;];";
        scriptMap.put("Unholdable",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\":=\",{aLeftAssign, aRightAssign});UnFence(\":=\", 2);HoldArgument(\":=\", aLeftAssign);HoldArgument(\":=\", aRightAssign);RuleHoldArguments(\":=\", 2, 0, Atom?(aLeftAssign))[ Check( Not? Number?(aLeftAssign), \"Argument\", \"Only a variable can be placed on the left side of an := operator.\" ); MacroBind(aLeftAssign,Eval(aRightAssign)); Eval(aLeftAssign);];RuleHoldArguments(\":=\",2 , 0, List?(aLeftAssign))[ Map(\":=\",{aLeftAssign,Eval(aRightAssign)});];RuleHoldArguments(\":=\", 2, 10, Function?(aLeftAssign) And? (First(FunctionToList(aLeftAssign)) =? Nth))[ Local(listOrAssociationList, indexOrKey, argumentsList); Bind(argumentsList, (FunctionToList(aLeftAssign))); Bind(argumentsList, Rest(argumentsList)); Bind(listOrAssociationList, Eval(First(argumentsList))); Bind(argumentsList, Rest(argumentsList)); Bind(indexOrKey, Eval(First(argumentsList))); AssignArray(listOrAssociationList, indexOrKey, Eval(aRightAssign));];RuleHoldArguments(\":=\", 2, 30, Function?(aLeftAssign) And? Not?(Equal?(aLeftAssign[0], ToAtom(\":=\"))) )[ Check( Not? Equal?(aLeftAssign[0], ToAtom(\"/\")), \"Argument\", \"Only a variable can be placed on the left side of an := operator.\" ); Local(oper,args,arity); Bind(oper,ToString(aLeftAssign[0])); Bind(args,Rest(FunctionToList(aLeftAssign))); If( And?(GreaterThan?(Length(args), 1), Equal?( MathNth(args, Length(args)), ToAtom(\"...\") )),  [ DestructiveDelete(args, Length(args));  Bind(arity, Length(args)); Retract(oper, arity); RulebaseListedEvaluateArguments(oper, args); ],  [ Bind(arity, Length(args)); Retract(oper, arity); RulebaseEvaluateArguments(oper, args); ] ); Unholdable(aRightAssign); RuleEvaluateArguments(oper, arity, 1025, True) aRightAssign;];";
        scriptMap.put(":=",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Curl\", {aFunc, aBasis});RuleHoldArguments(\"Curl\", 2, 1, Length(aBasis)=?Length(aFunc)) { Apply(\"Differentiate\",{aBasis[2],aFunc[3]})-Apply(\"Differentiate\",{aBasis[3],aFunc[2]}), Apply(\"Differentiate\",{aBasis[3],aFunc[1]})-Apply(\"Differentiate\",{aBasis[1],aFunc[3]}), Apply(\"Differentiate\",{aBasis[1],aFunc[2]})-Apply(\"Differentiate\",{aBasis[2],aFunc[1]}) };";
        scriptMap.put("Curl",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # (Deriv(_var,1)_func) <-- Deriv(var)func;5 # (Deriv(_var,0)_func) <-- func;10 # (Deriv(_var,n_PositiveInteger?)_func) <-- Deriv(var)Deriv(var,n-1)func;10 # (Deriv(_var,n_NegativeInteger?)_func) <-- Check(0, \"Math\", \"Negative derivative\");0 # (Deriv(_var) (_var)) <-- 1;1 # (Deriv(_var)func_Atom?) <-- 0;2 # (Deriv(_var)_x + _y) <-- (Deriv(var)x) + (Deriv(var)y);2 # (Deriv(_var)- (_x) ) <-- -Deriv(var)x;2 # (Deriv(_var)_x - _y) <-- (Deriv(var)x) - (Deriv(var)y);2 # (Deriv(_var)_x * _y) <-- (x*Deriv(var)y) + (Deriv(var)x)*y;2 # (Deriv(_var)Sin(_x)) <-- (Deriv(var)x)*Cos(x);2 # (Deriv(_var)Sinh(_x))<-- (Deriv(var)x)*Cosh(x);2 # (Deriv(_var)Cosh(_x))<-- (Deriv(var)x)*Sinh(x);2 # (Deriv(_var)Cos(_x)) <-- -(Deriv(var)x)*Sin(x);2 # (Deriv(_var)Csc(_x)) <-- -(Deriv(var)x)*Csc(x)*Cot(x);2 # (Deriv(_var)Csch(_x)) <-- -(Deriv(var)x)*Csch(x)*Coth(x);2 # (Deriv(_var)Sec(_x)) <-- (Deriv(var)x)*Sec(x)*Tan(x);2 # (Deriv(_var)Sech(_x)) <-- -(Deriv(var)x)*Sech(x)*Tanh(x);2 # (Deriv(_var)Cot(_x)) <-- -(Deriv(var)x)*Csc(x)^2;2 # (Deriv(_var)Coth(_x)) <-- (Deriv(var)x)*Csch(x)^2;2 # (Deriv(_var)Tan(_x)) <-- ((Deriv(var) x) / (Cos(x)^2));2 # (Deriv(_var)Tanh(_x)) <-- (Deriv(var)x)*Sech(x)^2;2 # (Deriv(_var)Exp(_x)) <-- (Deriv(var)x)*Exp(x);2 # (Deriv(_var)(_x / _y))_(FreeOf?(var,y)) <-- (Deriv(var) x) / y;3 # (Deriv(_var)(_x / _y)) <-- (y* (Deriv(var) x) - x* (Deriv(var) y))/ (y^2);2 # (Deriv(_var)Ln(_x)) <-- ((Deriv(var) x) / x);2 # (Deriv(_var)(_x ^ _n))_(RationalOrNumber?(n) Or? FreeOf?(var, n)) <-- n * (Deriv(var) x) * (x ^ (n - 1));2 # (Deriv(_var)(Abs(_x))) <-- (x/Abs(x))*(Deriv(var)x);2 # (Deriv(_var)(Sign(_x))) <-- 0;2 # (Deriv(_var)(if(_cond)(_body))) <-- ListToFunction({ToAtom(\"if\"),cond,Deriv(var)body});2 # (Deriv(_var)((_left) else (_right))) <-- ListToFunction({ToAtom(\"else\"), (Deriv(var)left), (Deriv(var)right) } );3 # (Deriv(_var)(_x ^ _n)) <-- (x^n)*Deriv(var)(n*Ln(x));2 # (Deriv(_var)ArcSin(_x)) <-- (Deriv(var) x)/Sqrt(1 - (x^2));2 # (Deriv(_var)ArcCos(_x)) <-- -(Deriv(var)x)/Sqrt(1 - (x^2));2 # (Deriv(_var)ArcTan(_x)) <-- (Deriv(var) x)/(1 + x^2);2 # (Deriv(_var)ArcSinh(_x)) <-- (Deriv(var) x)/Sqrt((x^2) + 1);2 # (Deriv(_var)ArcCosh(_x)) <-- (Deriv(var) x)/Sqrt((x^2) - 1);2 # (Deriv(_var)ArcTanh(_x)) <-- (Deriv(var) x)/(1 - x^2);2 # (Deriv(_var)Sqrt(_x)) <-- ((Deriv(var)x)/(2*Sqrt(x)));2 # (Deriv(_var)Complex(_r,_i)) <-- Complex(Deriv(var)r,Deriv(var)i);LocalSymbols(var,var2,a,b,y)[ 2 # (Deriv(_var)Integrate(_var)(_y)) <-- y; 2 # (Deriv(_var)Integrate(_var2,_a,_b)(y_FreeOf?(var))) <-- (Deriv(var)b)*(y Where var2 == b) - (Deriv(var)a)*(y Where var2 == a); 3 # (Deriv(_var)Integrate(_var2,_a,_b)(_y)) <-- (Deriv(var)b)*(y Where var2 == b) - (Deriv(var)a)*(y Where var2 == a) + Integrate(var2,a,b) Deriv(var) y; ];2 # (Deriv(_var)func_List?)_(Not?(List?(var))) <-- Map(\"Deriv\",{FillList(var,Length(func)),func});2 # (Deriv(_var)UniVariate(_var,_first,_coefs)) <--[ Local(result,m,i); result:=FlatCopy(coefs); m:=Length(result); For(i:=1,i<=?m,i++) [ result[i] := result[i] * (first+i-1); ]; UniVariate(var,first-1,result);];";
        scriptMap.put("Deriv",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Differentiate\",{aVar,aFunc});RulebaseHoldArguments(\"Differentiate\",{aVar,aCount,aFunc});RuleHoldArguments(\"Differentiate\",2,1,List?(aVar) And? Not?(List?(aFunc))) Map(\"Differentiate\",{aVar,FillList(aFunc, Length(aVar))});RuleHoldArguments(\"Differentiate\",2,1,List?(aVar) And? List?(aFunc)) Map(\"Differentiate\",{aVar,aFunc});RuleHoldArguments(\"Differentiate\",2,3,True)[ MacroLocal(aVar); Apply(\"Deriv\",{aVar,1,aFunc});];RuleHoldArguments(\"Differentiate\",3,1,List?(aVar) And? Not?(List?(aFunc))) Map(\"Differentiate\",{aVar, FillList(aCount, Length(aVar)), FillList(aFunc, Length(aVar))});RuleHoldArguments(\"Differentiate\",3,1,List?(aVar) And? List?(aFunc)) Map(\"Differentiate\",{aVar, FillList(aCount, Length(aVar)), aFunc});RuleHoldArguments(\"Differentiate\",3,3,True)[ MacroLocal(aVar); Apply(\"Deriv\",{aVar,aCount,aFunc});];HoldArgument(\"Differentiate\",aVar);HoldArgument(\"Differentiate\",aFunc);";
        scriptMap.put("Differentiate",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Diverge\", {aFunc, aBasis});RuleHoldArguments(\"Diverge\", 2, 1, List?(aBasis) And? List?(aFunc) And? Length(aBasis) =? Length(aFunc)) Add(Map(\"Differentiate\", {aBasis,aFunc}));";
        scriptMap.put("Diverge",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "EquationLeft(_symbolicEquation)_(Type(symbolicEquation) =? \"==\") <--[ Local(listForm);  listForm := FunctionToList(symbolicEquation);  listForm[2];];";
        scriptMap.put("EquationLeft",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "EquationRight(_symbolicEquation)_(Type(symbolicEquation) =? \"==\") <--[ Local(listForm);  listForm := FunctionToList(symbolicEquation);  listForm[3];];";
        scriptMap.put("EquationRight",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(lastcoef,OrdBuild, AddFoundSolutionSingle , AddFoundSolution, Fct, MkfactD,p)[LastCoef(_vector,_p) <--[ Local(n); n:=Length(vector); Add(vector*p^(0 .. (n-1)));];OrdBuild(vector,q):=[ Local(i,result,n); Bind(i,2); Bind(result, 0); Bind(n, Length(vector)); While (i<=?n) [ Bind(result,result+(i-1)*vector[i]*p^(i-2)); Bind(i, i+2); ]; q*result;];Function(AddFoundSolutionSingle,{p})[ Local(calc); Bind(calc, Eval(lastcoef)); If (Equal?(calc, 0), [ Local(newlist,count,root); count:=0; root := p; Local(rem); rem:={-root,1}; {testpoly,rem}:=MkfactD(testpoly,rem); rem:={-root,1}; {newlist,rem}:=MkfactD(poly,rem); While (rem =? {}) [ count++; Bind(poly,newlist); rem:={-root,1}; {newlist,rem}:=MkfactD(poly,rem); ]; Local(lgcd,lc); Bind(lgcd,Gcd({andiv,an,root})); Bind(lc,Quotient(an,lgcd)); Bind(result,{var+ (-(Quotient(root,lgcd)/lc)),count}~result); Bind(andiv,Quotient(andiv,lgcd^count)); Bind(anmul,anmul*lc^count); Local(p,q); Bind(lastcoef, LastCoef(testpoly,p)); Bind(ord, OrdBuild(testpoly,q)); ]);];UnFence(AddFoundSolutionSingle,1);Function(AddFoundSolution,{p})[ AddFoundSolutionSingle(p); AddFoundSolutionSingle(-2*q+p);];UnFence(AddFoundSolution,1);Function(Fct,{poly,var})[ Local(maxNrRoots,result,ord,p,q,accu,calc,twoq,mask); Local(gcd); [ Bind(gcd,Gcd(poly)); If(poly[Length(poly)] <? 0,Bind(gcd, gcd * -1)); Bind(poly,poly/gcd); ]; Local(unrat); Bind(unrat,Lcm(MapSingle(\"Denominator\",poly))); Bind(poly,unrat*poly); Local(origdegree); Bind(origdegree,Length(poly)-1); Local(an,andiv,anmul); Bind(an,poly[Length(poly)]); Bind(poly,poly* (an^((origdegree-1) .. -1))); Bind(andiv,an^(origdegree-1)); Bind(anmul,1); Local(leadingcoef,lowestcoef); Bind(leadingcoef,poly[Length(poly)]); [ Local(i); Bind(i,1); Bind(lowestcoef,Abs(poly[i])); While (lowestcoef =? 0 And? i<=?Length(poly)) [ Bind(i,i+1); Bind(lowestcoef,Abs(poly[i])); ]; ];    Local(testpoly); Local(deriv);  deriv:=Rest(poly); [ Local(i); For (i:=1,i<=?Length(deriv),i++) [ deriv[i] := deriv[i]*i; ]; ]; [ Local(q,r,next); q:=poly; r:=deriv; While(r !=? {}) [ next := MkfactD(q,r)[2]; q:=r; r:=next; ];   q:=q/q[Length(q)]; testpoly:=MkfactD(poly,q)[1]; ]; Bind(maxNrRoots,Length(testpoly)-1); Bind(result, {}); Bind(lastcoef, LastCoef(testpoly,p)); Bind(ord, OrdBuild(testpoly,q)); Bind(accu,{}); Bind(q,1); Bind(twoq,MultiplyN(q,2)); Bind(mask,AddN(twoq,MathNegate(1))); if (Even?(testpoly[1])) [ Bind(accu,0~accu); AddFoundSolutionSingle(0); ]; Bind(p,1); Bind(calc, Eval(lastcoef)); If (Even?(calc), [ Bind(accu,1~accu); AddFoundSolution(1); ]); Bind(q,twoq); Bind(twoq,MultiplyN(q,2)); Bind(mask,AddN(twoq,MathNegate(1))); While(Length(result)<?maxNrRoots And? Length(accu)>?0 And? q<=?Abs(testpoly[1])) [ Local(newaccu); Bind(newaccu,{}); ForEach(p,accu) [ Bind(calc,Eval(lastcoef)); If (LessThan?(calc,0), Bind(calc, AddN(calc,MultiplyN(twoq,QuotientN(AddN(MathNegate(calc),twoq),twoq)))) ); Bind(calc, BitAnd(calc, mask)); If ( Equal?(calc, 0), [ Bind(newaccu, p~newaccu); AddFoundSolutionSingle(-2*q+p); ]); Bind(calc, AddN(calc, Eval(ord))); If (LessThan?(calc,0), Bind(calc, AddN(calc,MultiplyN(twoq,QuotientN(AddN(MathNegate(calc),twoq),twoq)))) ); Bind(calc, BitAnd(calc, mask)); If ( Equal?(calc, 0), [ Bind(newaccu, AddN(p,q)~newaccu); AddFoundSolution(AddN(p,q)); ]); ]; Bind(accu, newaccu); Bind(q,twoq); Bind(twoq,MultiplyN(q,2)); Bind(mask,AddN(twoq,MathNegate(1))); ];   Bind(poly,poly*an^(0 .. (Length(poly)-1))); Bind(poly,gcd*anmul*poly);  If(Not? Zero?(unrat * andiv ),Bind(poly,poly/(unrat * andiv ))); If(poly !=? {1}, [ result:={(Add(poly*var^(0 .. (Length(poly)-1)))),1}~result; ]); result;];BinaryFactors(expr):=[ Local(result,uni,coefs); uni:=MakeUni(expr,VarList(expr)[1]); uni:=FunctionToList(uni); coefs:=uni[4]; coefs:=Concat(ZeroVector(uni[3]),coefs); result:=Fct(coefs,uni[2]); result;];MkfactD(numer,denom):=[ Local(q,r,i,j,ln,ld,nq); DropEndZeroes(numer); DropEndZeroes(denom); Bind(numer,Reverse(numer)); Bind(denom,Reverse(denom)); Bind(ln,Length(numer)); Bind(ld,Length(denom)); Bind(q,FillList(0,ln)); Bind(r,FillList(0,ln)); Bind(i,1); If(ld>?0, [ While(Length(numer)>=?Length(denom)) [ Bind(nq,numer[1]/denom[1]); q[ln-(Length(numer)-ld)] := nq; For(j:=1,j<=?Length(denom),j++) [ numer[j] := (numer[j] - nq*denom[j]); ]; r[i] := r[1] + numer[1]; Bind(numer, Rest(numer)); i++; ]; ]); For(j:=0,j<?Length(numer),j++) [ r[i+j] := r[i+j] + numer[j+1]; ]; Bind(q,Reverse(q)); Bind(r,Reverse(r)); DropEndZeroes(q); DropEndZeroes(r); {q,r};];]; ";
        scriptMap.put("BinaryFactors",scriptString);
        scriptMap.put("LastCoef",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # FW(_list)_(Length(list) =? 0) <-- 1;10 # FW(_list)_(Length(list) =? 1) <-- FWatom(list[1]);20 # FW(_list) <--[ Local(result); result:=FWatom(First(list)); ForEach(item,Rest(list)) [ result := ListToFunction({ ToAtom(\"*\"),result,FWatom(item)}); ]; result;];";
        scriptMap.put("FW",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # FWatom({_a,1}) <-- a;20 # FWatom({_a,_n}) <-- ListToFunction({ToAtom(\"^\"),a, n});";
        scriptMap.put("FWatom",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # FactorCancel( p_Rational? ) <-- Factor(p);15 # FactorCancel( p_RationalFunction? ) <--[ If(InVerboseMode(),Tell(FactorCancel,p)); Local(pp,ff,n,d,fn,fd,f,tnu,newn,newd,s,k); pp := Simplify(p); If(InVerboseMode(),Tell(\" \",pp)); ff := Factors(pp); If(InVerboseMode(),Tell(\" \",ff)); tnu := {ff[1]}; If(ListOfLists?(ff), tnu := RemoveDuplicates(Transpose(ff)[1])); newn := {}; newd := {}; If(InVerboseMode(),Tell(\" \",tnu)); ForEach(f,tnu) [ s := Select(ff,Lambda({X},X[1]=?f)); If(InVerboseMode(),Tell(\" \",{f,s})); If( s !=? {}, [ k := Sum(Transpose(s)[2]); If(InVerboseMode(),Tell(\" \",{s,k})); If( k >? 0, DestructiveAppend(newn,{f,k}) ); If( k <? 0, DestructiveAppend(newd,{f,-k}) ); ], [ k := 1; DestructiveAppend(newn,{f,k}); ] ); ]; If(InVerboseMode(),Tell(\" \",{newn,newd})); FW(newn)/FW(newd);];20 # FactorCancel( _p ) <-- Factor(p);";
        scriptMap.put("FactorCancel",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"FactorQS\",{n})[ Local(x,k,fb,j);   k:=Round(N(Sqrt(Exp(Sqrt(Ln(n)*Ln(Ln(n))))))); fb:=ZeroVector(k); For(j:=1,j<=?k,j++)[ fb[j]:=NextPrime(j); ];];";
        scriptMap.put("FactorQS",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1# FactorizeInt(0) <-- {};1# FactorizeInt(1) <-- {};3# FactorizeInt(n_Integer?) <--[ Local(smallpowers); n := Abs(n);    If( Gcd(ProductPrimesTo257(), n) >? 1,  smallpowers := TrialFactorize(n, 257),  smallpowers := {n}  ); n := smallpowers[1];  If(n=?1, Rest(smallpowers),  [  SortFactorList( PollardCombineLists(Rest(smallpowers), PollardRhoFactorize(n)) ); ] );];";
        scriptMap.put("FactorizeInt",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # FactorsBinomials( _x + y_FreeOf?(x) ) <-- {x+y,1};10 # FactorsBinomials( _x - y_FreeOf?(x) ) <-- {x-y,1};10 # FactorsBinomials( c_Constant? * _x + y_FreeOf?(x) ) <-- {c*x+y,1};10 # FactorsBinomials( c_Constant? * _x - y_FreeOf?(x) ) <-- {c*x-y,1};10 # FactorsBinomials( _x^m_Odd? + _y ) <--[ If(InVerboseMode(),Tell(\"FactorsBinomialssum\",{x,m,y})); Local(nn,qq, r); nn := (m-1)/2; qq := (y^(1/m)); If(InVerboseMode(),Tell(\" FBinsum1\",{nn,qq})); r := {{x+qq,1},{Sum(k,0,m-1,(-1)^k*qq^k*x^(m-1-k)),1}};];12 # FactorsBinomials( c_Constant? * _x^m_Odd? + _y ) <--[ If(InVerboseMode(),Tell(\"FactorsBinomialssum\",{c,x,m,y})); Local(nn,qq, r); nn := (m-1)/2; qq := ((y/c)^(1/m)); If(InVerboseMode(),Tell(\" FBinsum.1b\",{nn,qq})); If( c=?1, r := {{x+qq,1},{Sum(k,0,m-1,(-1)^k*qq^k*x^(m-1-k)),1}}, r := {{c,1},{x+qq,1},{Sum(k,0,m-1,(-1)^k*qq^k*x^(m-1-k)),1}} ); ];10 # FactorsBinomials( _x^m_Integer? - _y ) <--[ If(InVerboseMode(),Tell(\"FactorsBinomialsdif\",{x,m,y})); Local(pp,qq,r,L); pp := m-1; qq := (y^(1/m)); If(Number?(y),qq:=GuessRational(N(qq))); If(InVerboseMode(),Tell(\" FBindif.1\",{pp,qq}));  if (m =? 2) [ L := FunctionToList(y); If(And?(L[1]=?ToAtom(\"^\"),L[3]=?2),qq:=L[2]); r := {{x+qq,1},{x-qq,1}}; ] else if (m =? 4) [r := {{x+qq,1},{x-qq,1},{x^2+qq^2,1}};] else if (m =? 6) [r := {{x+qq,1},{x-qq,1},{x^2+x*qq+qq^2,1},{x^2-x*qq+qq^2,1}};] else [r := {{x-qq,1},{Sum(k,0,pp,qq^k*x^(pp-k)),1}};]; r;];12 # xFactorsBinomials( c_Constant? * _x^m_Integer? - _y ) <--[ If(InVerboseMode(),Tell(\"FactorsBinomialsdif\",{c,x,m,y})); Local(aa,bb,c0,r); aa := c^(1/m); bb := ((y)^(1/m)); If(Number?(y),bb:=GuessRational(N(bb))); If(InVerboseMode(),Tell(\" FBindif.1b\",{aa,bb})); r := FactorsBinomials( (aa*x)^m - bb^m );];";
        scriptMap.put("FactorsBinomials",scriptString);
        scriptMap.put("xFactorsBinomials",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # FactorsMonomial(expr_Monomial?) <--[ If(InVerboseMode(),Tell(\"FactorsMonomial\",expr)); Local(den,num,Ns,flat,prod,quot,result,f,ff); If( RationalFunction?(expr), [ den := Denominator(expr); num := Flatten(Numerator(expr),\"*\"); ], [ den := 1; num := Flatten(expr,\"*\"); ] ); If(InVerboseMode(),Tell(\" \",{num,den})); Ns := Select(num, \"Complex?\"); If(InVerboseMode(),Tell(\" \",Ns)); If( Ns =? {}, If( den !=? 1, DestructiveInsert(num,1,1/den)), DestructiveReplace(num,Find(num,Ns[1]),Ns[1]/den) ); If(InVerboseMode(),Tell(\" \",num)); result := {}; ForEach(f,num) [ If( Complex?(f),  DestructiveAppend(result,{(f),1}), If( Atom?(f), DestructiveAppend(result,{f,1}), DestructiveAppend(result,DestructiveDelete(FunctionToList(f),1)) ) ); ]; result;];";
        scriptMap.put("FactorsMonomial",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # FactorsPolynomialOverIntegers(_expr)_PolynomialOverIntegers?(expr) <--[ Local(x); x := VarList(expr)[1]; FactorsPolynomialOverIntegers(expr,x);];15 # FactorsPolynomialOverIntegers(_expr) <-- expr;10 # FactorsPolynomialOverIntegers(_expr,_var)_(PolynomialOverIntegers?(expr,var)) <--[ Local(factorsList,factListTransp,factrs,multiplicities,factrsUnMonic); Local(polyFactors,normalizations,normDivisor,polyFactors,factList); Local(n,result,newResult,gtotal,r,rr,d,g); factorsList := BinaryFactors(expr);        factListTransp := Transpose(factorsList); factrs := factListTransp[1]; multiplicities := factListTransp[2];          factrsUnMonic := MapSingle(\"Together\",factrs);         {polyFactors,normalizations}:=Transpose(MapSingle(\"GetNumerDenom\",factrsUnMonic));          normDivisor := Product(Map(\"^\",{normalizations,multiplicities}));         polyFactors[1] := Simplify(polyFactors[1]/normDivisor);     factListTransp[1] := polyFactors; factList := Transpose(factListTransp);    result := factList;   Local(newResult,gtotal,d,g,rr); newResult := {}; gtotal := 1; ForEach(r,result) [ d := Degree(r[1],var); g := Gcd(Coef(r[1],var,0 .. d)); If( g >? 1,  [ gtotal:=g*gtotal;  r[1]:=Simplify(r[1]/g); ] ); If(d >? 2, [  rr := TryToReduceSpecialPolynomial(r[1]); If( List?(rr),newResult := Concat(newResult,rr) ); ], If( r !=? {1,1}, newResult := r~newResult ) ); ];  If(gtotal>?1,newResult:={gtotal,1}~newResult); newResult;]; 10 # TryToReduceSpecialPolynomial(_x^4+_x^2+1) <-- {{x^2+x+1,1},{x^2-x+1,1}};10 # TryToReduceSpecialPolynomial(_x^6-1) <-- {{x+1,1},{x-1,1},{x^2+x+1,1},{x^2-x+1,1}};";
        scriptMap.put("FactorsPolynomialOverIntegers",scriptString);
        scriptMap.put("TryToReduceSpecialPolynomial",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # FactorsSmallInteger( N_Integer? ) <--[ Local(n, power, prime, result, limit); n := Abs(N);  limit := Ceil(SqrtN(n));  prime := 2;  result := {}; While( prime <=? limit And? n >? 1 And? prime*prime <=? n ) [  {n, power} := FindPrimeFactor(n, prime); If( power >? 0, DestructiveAppend(result, {prime,power}) ); prime := NextPseudoPrime(prime);  ];  If( n >? 1, DestructiveAppend(result, {n,1}) ); result;];";
        scriptMap.put("FactorsSmallInteger",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "FindPrimeFactor(n, prime) :=[ Local(power, factor, oldfactor, step); power := 1; oldfactor := 1;  factor := prime;  While(Modulo(n, factor)=?0)  [ oldfactor := factor;  factor := factor^2; power := power*2; ]; power := Quotient(power,2); factor := oldfactor; n := Quotient(n, factor);  step := Quotient(power,2); While(step>?0 And? n >? 1) [ factor := prime^step; If( Modulo(n, factor)=?0, [ n := Quotient(n, factor); power := power + step; ] ); step := Quotient(step, 2); ]; {n, power};];";
        scriptMap.put("FindPrimeFactor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "FindPrimeFactorSimple(n, prime) :=[ Local(power, factor); power := 0; factor := prime; While(Modulo(n, factor)=?0) [ factor := factor*prime; power++; ]; {n/(factor/prime), power};];";
        scriptMap.put("FindPrimeFactorSimple",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # CanBeMonomial(_expr)<--Not? (HasFunction?(expr,ToAtom(\"+\")) Or? HasFunction?(expr,ToAtom(\"-\")));10 # Monomial?(expr_CanBeMonomial) <-- [ Local(r); If( RationalFunction?(expr), r := (VarList(Denominator(expr)) =? {}), r := True );];15 # Monomial?(_expr) <-- False;10 # FactorsMonomial(expr_Monomial?) <--[ If(InVerboseMode(),Tell(\"FactorsMonomial\",expr)); Local(den,num,Ns,flat,prod,quot,result,f,ff); If( RationalFunction?(expr), [ den := Denominator(expr); num := Flatten(Numerator(expr),\"*\"); ], [ den := 1; num := Flatten(expr,\"*\"); ] ); If(InVerboseMode(),Tell(\" \",{num,den})); Ns := Select(num, \"Complex?\"); If(InVerboseMode(),Tell(\" \",Ns)); If( Ns =? {}, If( den !=? 1, DestructiveInsert(num,1,1/den)), DestructiveReplace(num,Find(num,Ns[1]),Ns[1]/den) ); If(InVerboseMode(),Tell(\" \",num)); result := {}; ForEach(f,num) [ If( Complex?(f),  DestructiveAppend(result,{(f),1}), If( Atom?(f), DestructiveAppend(result,{f,1}), DestructiveAppend(result,DestructiveDelete(FunctionToList(f),1)) ) ); ]; result;];";
        scriptMap.put("Monomials",scriptString);
        scriptMap.put("CanBeMonomial",scriptString);
        scriptMap.put("Monomial?",scriptString);
        scriptMap.put("FactorsMonomial",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Roots(poly_CanBeUni) <--[ Local(factors,result,uni,root,i,deg); factors:=Factors(poly); result:={}; ForEach(item,factors) [ uni:=MakeUni(item[1]); deg:=Degree(uni); If(deg >? 0 And? deg <? 3, [ root:= PSolve(uni); If(Not? List?(root),root:={root}); For(i:=0,i<?item[2],i++) result:= Concat(root, result); ] ); ]; result;];";
        scriptMap.put("Roots",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RootsWithMultiples(poly_CanBeUni) <--[ Local(factors,result,uni,root,i,deg); factors:=Factors(poly); result:={}; ForEach(item,factors) [ uni:=MakeUni(item[1]); deg:=Degree(uni); If(deg >? 0 And? deg <? 3, [ root:= PSolve(uni); If(Not? List?(root),root:={root}); For(i:=1,i<=?Length(root),i++) result:= Concat({{root[i],item[2]}}, result); ] ); ]; result;];";
        scriptMap.put("RootsWithMultiples",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(a,b, list) [SortFactorList(list) := HeapSort(list, {{a,b}, a[1]<?b[1]});];";
        scriptMap.put("SortFactorList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TrialFactorize(n, limit) :=[ Local(power, prime, result); result := {n};  prime := 2;  While(prime <=? limit And? n>?1 And? prime*prime <=? n) [  {n, power} := FindPrimeFactor(n, prime); If( power>?0, DestructiveAppend(result, {prime,power}) ); prime := NextPseudoPrime(prime);  ];  DestructiveReplace(result, 1, n);];";
        scriptMap.put("TrialFactorize",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Factor(p_CanBeUni) <-- FW(Factors(p));";
        scriptMap.put("Factor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " 10 # Factors(p_PositiveInteger?) <-- FactorizeInt(p);11 # Factors(p_Integer?) <-- FactorizeInt(p);12 # Factors(p_Rational?)_(Denominator(p) !=? 1) <-- {{YacasFactor(Numerator(p)) /YacasFactor(Denominator(p)) , 1}}; 14 # Factors(p_GaussianInteger?) <-- GaussianFactors(p);20 # Factors(p_CanBeUni)_(Length(VarList(p)) =? 1) <--[ Local(x,d,coeffs,nterms,factorsList,result); x := VarList(p)[1]; d := Degree(p,x);   coeffs := Coef(p,x,0 .. Degree(p,x)); nterms := Length(Select(coeffs, \"NotZero?\"));  If( nterms =? 2 And? d >? 2, [ result := FactorsBinomials(p); ],  [  factorsList := BinaryFactors(p);    If( AllSatisfy?(\"Integer?\",coeffs), [  result := FactorsPolynomialOverIntegers(p,x); ], [   Local(notInt,rat,dd,lcm,newCoeffs,newPoly,facs); notInt := Select(coeffs, Lambda({i},Not? Integer?(i))); rat := Rationalize(coeffs);  dd := MapSingle(\"Denominator\",rat); lcm := Lcm(dd); newCoeffs := lcm * rat; newPoly := NormalForm(UniVariate(x,0,newCoeffs)); facs := FactorsPolynomialOverIntegers(newPoly); If( InVerboseMode(), [ Echo(\"coeffs \",coeffs); Echo(\"notInt \",notInt); Echo(\"rat \",rat); Echo(\"dd \",dd); Echo(\"lcm \",lcm); Echo(\"newCoeffs \",newCoeffs); Echo(\"newPoly \",newPoly); Echo(\"facs \",facs); ] ); result := {(1/lcm),1}~facs;  ] ); ] ); CombineNumericalFactors( result );];30 # Factors(p_CanBeUni) <--[  Local(vl,nvars,coeffs,result); vl := VarList(p); nvars := Length(vl); coeffs := Coef(p,x,0 .. 8); If(InVerboseMode(),Tell(\"CBU\",{vl,nvars,coeffs})); If (nvars >? 1,  [ If( InVerboseMode(), Echo(\" special \",p)); result := FactorsMultivariateSpecialCases(p); ],  result := FactorsSomethingElse(p)  ); CombineNumericalFactors( result );];40 # Factors(_p) <--[  If( InVerboseMode(), Echo(\"Possibly trying to factor polynomial with non-integral exponents\") ); Local( result);   result := {{p,1}}; CombineNumericalFactors( result ); ];10 # FactorsMultivariateSpecialCases(-_expr) <-- {-1,1}~FactorsMultivariateSpecialCases(expr);10 # FactorsMultivariateSpecialCases(x_Atom? + y_Atom?) <-- [If(InVerboseMode(),Tell(1));{{x+y,1}};];10 # FactorsMultivariateSpecialCases(x_Atom? - y_Atom?) <-- [If(InVerboseMode(),Tell(2));{{x-y,1}};];10 # FactorsMultivariateSpecialCases(_n*_x^p_Integer? + _n*_y) <-- [If(InVerboseMode(),Tell(3));{n,1}~FactorsMultivariateSpecialCases(x+y);];10 # FactorsMultivariateSpecialCases(_n*_x^p_Integer? - _n*_y) <-- [If(InVerboseMode(),Tell(4));{n,1}~FactorsMultivariateSpecialCases(x-y);];10 # FactorsMultivariateSpecialCases(n_Integer?*_x + m_Integer?*_y)_(Gcd(n,m)>?1) <-- {{Gcd(n,m),1},{(Simplify((n*x+m*y)/Gcd(n,m))),1}};10 # FactorsMultivariateSpecialCases(n_Integer?*_x - m_Integer?*_y)_(Gcd(n,m)>?1) <-- {{Gcd(n,m),1},{(Simplify((n*x-m*y)/Gcd(n,m))),1}};10 # FactorsMultivariateSpecialCases(_n*_x + _n*_y) <-- {n,1}~FactorsMultivariateSpecialCases(x+y);10 # FactorsMultivariateSpecialCases(_n*_x - _n*_y) <-- {n,1}~FactorsMultivariateSpecialCases(x-y);10 # FactorsMultivariateSpecialCases(_x^n_Integer? - _y) <-- FactorsBinomials(x^n - y); 10 # FactorsMultivariateSpecialCases(_x^n_Integer? + _y) <-- FactorsBinomials(x^n + y); 20 # FactorsSomethingElse(_p) <--  [ If( InVerboseMode(), [ ECHO(\" *** FactorsSomethingElse: NOT IMPLEMENTED YET ***\"); ] ); p; ];10 # CombineNumericalFactors( factrs_List? ) <-- [ If( InVerboseMode(), Tell(\"Combine\",factrs) ); Local(q,a,b,t,f,err); err := False; t := 1; f := {}; ForEach(q,factrs) [ If( InVerboseMode(), Tell(1,q) ); If( List?(q) And? Length(q)=?2, [ {a,b} := q; If( InVerboseMode(), Echo(\" \",{a,b}) ); If( NumericList?( {a,b} ), t := t * a^b, f := {a,b}~f ); ], err := True ); ]; If( InVerboseMode(), [ Echo(\" t = \",t); Echo(\" f = \",f); Echo(\" err = \",err); ] ); If(Not? err And? t !=? 1, {t,1}~Reverse(f), factrs); ];";
        scriptMap.put("Factors",scriptString);
        scriptMap.put("FactorsMultivariateSpecialCases",scriptString);
        scriptMap.put("FactorsSomethingElse",scriptString);
        scriptMap.put("CombineNumericalFactors",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "50 # jFactorsPoly( poly_CanBeUni ) <--[ If(InVerboseMode(),Tell(\"jFactorsPoly_100612\",poly)); Local(content,pp,ppFactors,monomialFactors,result,moreDetails); Local(vars,nvars,disassem,degrees,mpoly,nterms,allCoeffs,allPowers); Local(hasRealCoefficients,hasRationalCoefficients,isHomogeneous); Local(extraFactor);  moreDetails := False; disassem := DisassembleExpression(poly); allCoeffs := disassem[3];   hasRealCoefficients := HasRealCoefficients(poly); If( hasRealCoefficients,  [ Local(realPoly); realPoly := poly;  allPowers := Flatten(disassem[2],\"List\"); poly := RealToRationalConvert(poly);  hasRealCoefficients := False; ] );   hasRationalCoefficients := HasRationalCoefficients(poly); If(hasRationalCoefficients,  [ Local(rationalPoly); rationalPoly := poly;  If(InVerboseMode(),Tell(\" RATIONAL\",rationalPoly));  poly := RationalToIntegerConvert(poly);  hasRationalCoefficients := False;  ] );  content := xContent( poly ); pp := Simplify( poly / content ); If(Bound?(extraFactor),content := content / extraFactor); If(InVerboseMode(),Tell(\" \",{content,pp}));  If( Length(VarList(pp))=?0, result := {{pp*content,1}}, [  vars := VarList(pp); nvars := Length(vars); disassem := DisassembleExpression(pp); nterms := Length(disassem[3]); degrees := {}; allCoeffs := disassem[3]; allPowers := Flatten(disassem[2],\"List\"); If(nvars >? 0, [ ForEach(v,vars) [ DestructiveAppend(degrees,Degree(pp,v)); ]; isHomogeneous := [   Local(sd,cmp);  sd := Sum /@ disassem[2]; cmp := FillList(sd[1],Length(sd)); ZeroVector?(sd - cmp); ]; ] );  If(InVerboseMode() And? moreDetails, [ Tell(\" \",vars); Tell(\" \",nvars); Tell(\" \",nterms); Tell(\" \",degrees); Tell(\" \",disassem); Tell(\" \",allCoeffs); Tell(\" \",allPowers); Tell(\" \",isHomogeneous); NewLine(); ] );    monomialFactors := FactorsMonomial(content); If(InVerboseMode(),Tell(\" \",monomialFactors));   ppFactors := jFactorsPrimitivePart( pp ); If(InVerboseMode(),Tell(\" \",ppFactors)); If( Not? ListOfLists?(ppFactors), [ Local(L,op,var,exp); L := If(Atom?(ppFactors[1]), ppFactors, FunctionToList(ppFactors[1]) ); If(InVerboseMode(),Tell(\" \",L)); If( L[1] =? ^, ppFactors := {L[2],L[3]} ); ppFactors := {ppFactors}; ] ); If(InVerboseMode(),Tell(\" \",ppFactors));   If( monomialFactors[1][1] =? 1, result := ppFactors,   result := Concat(monomialFactors,ppFactors)  ); ] );  If(InVerboseMode(), [ NewLine(); Tell(\" \",monomialFactors); Tell(\" \",ppFactors); Tell(\" final \",result); ] ); result;];UnFence(\"jFactorsPoly\",1); 60 # jFactorsPrimitivePart( _pp )_(isHomogeneous And? nterms=?2 And? nvars=?2) <--[ If(InVerboseMode(),Tell(\"Bivariate Binomial\",pp)); Local(ppFactors,isDiagonal); isDiagonal := Diagonal?(disassem[2]);  ppFactors := If(isDiagonal,jFactorsBivariateBinomial(pp),jasFactorsInt(pp) );];UnFence(\"jFactorsPrimitivePart\",1); 65 # jFactorsPrimitivePart( _pp )_(isHomogeneous And? nterms>?1 And? nvars=?2) <--[ If(InVerboseMode(),Tell(\"Homogeneous and Bivariate\")); Local(ppFactors); ppFactors := jFactorsHomogeneousBivariate(disassem);];UnFence(\"jFactorsPrimitivePart\",1); 70 # jFactorsPrimitivePart( _pp )_(nvars=?0) <--[ Local(ppFactors); ppfactors := {};]; 100 # jFactorsPrimitivePart( _pp ) <-- [ If(InVerboseMode(),Tell(\"jFactorsPrimitivePart_usingJAS\",pp)); Local(answer); answer := If(Monomial?(pp),{pp,1},jasFactorsInt(pp)); If(InVerboseMode(),Tell(\" \",answer)); answer;];UnFence(\"jFactorsPrimitivePart\",1);10 # jFactorsHomogeneousBivariate( dis_List? ) <-- [ If(InVerboseMode(),[NewLine();Tell(\"jFactorsHomogeneousBivariate\",dis);]); Local(dis1,f,dis2,poly1,ppFactors,residuals); Local(ii,lst,f,preassem); dis1 := {{xi},{{X},{X[1]}} /@ dis[2],dis[3]}; If(InVerboseMode(),Tell(\" \",dis1)); poly1 := Sum(ReassembleListTerms(dis1)); If(InVerboseMode(),Tell(\" \",poly1)); ppFactors := BinaryFactors(poly1); {ppFactors,residuals} := FixUpMonicFactors(ppFactors); For(ii:=1,ii<=?Length(ppFactors),ii++) [ f := ppFactors[ii]; If(InVerboseMode(),Tell(\" \",f[1])); lst := DisassembleExpression(f[1]); If(InVerboseMode(), [ Tell(\" \",lst); Tell(\" \",dis[1]); ] ); DestructiveReplace(lst,1,dis[1]); DestructiveAppend(lst[2][1],0); DestructiveAppend(lst[2][2],1); If(Length(lst[2])=?3, DestructiveAppend(lst[2][3],2)); If(InVerboseMode(),Tell(\" \",lst)); preassem := Sum(ReassembleListTerms(lst)) ; If(InVerboseMode(),Tell(\" \",preassem)); ppFactors[ii][1] := preassem; ]; If(InVerboseMode(),[Tell(\" \",ppFactors); Tell(\" \",residuals);NewLine();] );  ppFactors;];UnFence(\"jFactorsHomogeneousBivariate\",1);10 # RealToRationalConvert( poly_Polynomial? ) <--[   If(InVerboseMode(),[NewLine();Tell(\" REAL\",poly);]); Local(coeffs,gcd,lcm); coeffs := Rationalize /@ (allCoeffs); If(InVerboseMode(),[Tell(\" to-Q\",coeffs);Tell(\" to-Z\",coeffs);]); Local(gcd,lcm); gcd := Gcd(Numerator /@ coeffs); lcm := Lcm(Denominator /@ coeffs); If(InVerboseMode(),[Tell(\" \",gcd);Tell(\" \",lcm);]); disassem[3] := coeffs; allCoeffs := coeffs; poly := Sum(ReassembleListTerms(disassem)); If(InVerboseMode(),Tell(\" new\",poly)); poly;];UnFence(\"RealToRationalConvert\",1);10 # RationalToIntegerConvert( poly_Polynomial? ) <--[   Local(coeffs,gcd,lcm); coeffs := allCoeffs; If(InVerboseMode(),Tell(\" \",coeffs)); lcm := Lcm(Denominator /@ coeffs); extraFactor := lcm; If(InVerboseMode(),[Tell(\" \",extraFactor);]); poly := Simplify(extraFactor*poly); If(InVerboseMode(),Tell(\" new \",poly)); poly;];UnFence(\"RationalToIntegerConvert\",1);100 # CombineNumericalFactors( factrs_List? ) <--[ If( InVerboseMode(), Tell(\"Combine\",factrs) ); Local(q,a,b,t,f,ff,err); err := False; t := 1; f := {}; ForEach(q,factrs) [ If( InVerboseMode(), Tell(1,q) ); If( List?(q) And? Length(q)=?2, [ {a,b} := q; If( InVerboseMode(), Echo(\" \",{a,b}) ); If( NumericList?( {a,b} ), t := t * a^b, f := {a,b}~f ); ], err := True ); ]; If( InVerboseMode(), [ Echo(\" t = \",t); Echo(\" f = \",f); Echo(\" err = \",err); ] ); ff := If(Not? err And? t !=? 1, {t,1}~Reverse(f), factrs); ff := Select(Lambda({x},x!=?{1,1}),ff); If(ff[1]<?0,ff[1]:=-ff[1]);];150 # jFactors( expr_RationalFunction? )_ (Polynomial?(Numerator(expr)) And? Polynomial?(Denominator(expr))) <--[ If(InVerboseMode(),[NewLine();Tell(\"jFactors_Rational_Function\",expr);]); Local(Numer,Denom,fNumer,fDenom); Numer := Numerator(expr); Denom := Denominator(expr); fNumer := jFactors(Numer); fDenom := jFactors(Denom); If(InVerboseMode(),[Tell(\" \",fNumer); Tell(\" \",fDenom);]); fNumer/fDenom;];152 # jFactors( expr_RationalFunction? )_ (Constant?(Numerator(expr)) And? Polynomial?(Denominator(expr))) <--[ If(InVerboseMode(),[NewLine();Tell(\"jFactors_Rational_Denom\",expr);]); Local(Numer,Denom,fNumer,fDenom); Numer := Numerator(expr); Denom := Denominator(expr); fNumer := jFactors(Numer); fDenom := jFactors(Denom); If(InVerboseMode(),[Tell(\" \",fNumer); Tell(\" \",fDenom);]); fNumer/fDenom;];200 # jFactors( _expr )_(Length(VarList(expr)) =? 1) <--[ If(InVerboseMode(),[NewLine();Tell(\"Some other kind of expression\",expr);]); Local(dis,X,pows); dis := DisassembleExpression(expr); X := VarList(expr)[1]; pows := matchPower /@ dis[1]; rats := NearRational /@ pows; dis[1] := x^rats; p := Sum(ReassembleListTerms(dis)); If(InVerboseMode(),Tell(\" new \",p)); jFactors(p);];10 # jFactorsBivariateBinomial( poly_Polynomial? )_(Length(VarList(poly))=?2) <--[ If(InVerboseMode(),Tell(jFactorsBivariateBinomial,poly)); Local(dis,n,X,Y,vars,A,B,s,Ar,Br,Arr,Brr,DAr,DBr,result); dis := DisassembleExpression(poly); If(InVerboseMode(),Tell(\" \",dis)); n := Maximum(dis[2])[1]; X := dis[1][1]; Y := dis[1][2]; vars := dis[1]; A := Abs(dis[3][1]); B := Abs(dis[3][2]); s := Sign(dis[3][1]*dis[3][2]);   Ar := N(A^(1/n)); Arr := Round(Ar); DAr := Abs(Ar-Arr); Br := N(B^(1/n)); Brr := Round(Br); DBr := Abs(Br-Brr); If(InVerboseMode(), [ Tell(\" \",{n,X,Y});  Tell(\" \",{vars,A,B}); Tell(\" \",{Ar,Br,s}); Tell(\" \",{Arr,Brr}); Tell(\" \",{DAr,DBr}); Tell(\" \",dis); ] ); result := If( DAr <? 10^(-9) And? DBr <? 10^(-9), jFB(dis), {{poly,1}} ); result;];UnFence(\"jFactorsBivariateBinomial\",1);50 # jFB( dis_List? )_(Length(dis)=?3 And? Length(dis[3])=?2) <--[ If(InVerboseMode(),[NewLine();Tell(\"jFB\",dis);]); Local(ns,ii,fn,mx,my,fac); If(InVerboseMode(), [ Tell(\" \",n); Tell(\" \",{X,Y}); Tell(\" \",{A,B,s}); Tell(\" \",{Ar,Br}); ] ); X := Arr*X; Y := Brr*Y; If(InVerboseMode(),Tell(\" \",{X,Y}));  fac := jFac( X/Y,n,s);  If(InVerboseMode(), [ NewLine(); Tell(\" \",X/Y); Tell(\" \",fac); ] );   If( Y !=? 1, [ Local(f,d,fs); For(ii:=1,ii<=?Length(fac),ii++) [ f := fac[ii][1]; d := Degree(f,x); If(InVerboseMode(),Tell(\" \",{ii,f,d})); fs := Subst(x,X/Y) f; If(InVerboseMode(),Tell(\" \",{fs,d})); fac[ii][1] := Simplify(Simplify(Y^d*fs)); ]; ] ); fac;];UnFence(\"jFB\",1);60 # jFac( _var, n_PositiveInteger?, s_Integer? ) <--[  If(InVerboseMode(),[NewLine();Tell(\"jFac\",{var,n,s});]); Local(x,poly,result); poly := x^n+s; If(InVerboseMode(),Tell(\" \",poly)); result := jasFactorsInt(poly); ];UnFence(\"jFac\",3);10 # IsPureRational( N_Rational? )_(Not? Integer?(N)) <-- True;12 # IsPureRational( _N ) <-- False;10 # HasRealCoefficients( poly_Polynomial? ) <--[ Local(disassem); disassem := DisassembleExpression(poly); (Length(Select(disassem[3],\"Decimal?\")) >? 0);];10 # HasRealCoefficients( poly_Monomial? ) <--[ Local(disassem); disassem := DisassembleExpression(poly); (Length(Select(disassem[3],\"Decimal?\")) >? 0);];10 # HasRationalCoefficients( poly_Polynomial? ) <--[ Local(disassem,answer); If(InVerboseMode(),Tell(\" HasRationalCoefficients\",poly)); disassem := DisassembleExpression(poly);  answer := (Length(Select(disassem[3],\"IsPureRational\")) >? 0); If(InVerboseMode(),Tell(\" \",answer)); answer;];10 # HasRationalCoefficients( poly_Monomial?) <--[ Local(disassem); disassem := DisassembleExpression(poly); (Length(Select(disassem[3],\"IsPureRational\")) >? 0);];10 # FixUpMonicFactors( factrs_List? ) <--[ If(InVerboseMode(),[ NewLine(); Tell(\" doing monic fixup\"); ] ); Local(factrsnew,residuals,f,uni,); factrsnew := {}; residuals := {}; ForEach(f,factrs) [ If(InVerboseMode(),Tell(\" \",f)); uni := MakeUni(f[1]); If(InVerboseMode(),Tell(\" \",uni)); If( Degree(f[1])=?1, [ Local(cc,lcm,fnew); If(InVerboseMode(),Tell(\" \",Degree(f[1]))); cc := Coef(f[1],uni[1],0 .. 1);  lcm := Lcm( Denominator /@ cc ); uni[3] := lcm * cc; fnew := NormalForm(uni); If( hasRationalCoefficients, [ DestructiveAppend(factrsnew,f); ], [  DestructiveAppend(factrsnew,{fnew,f[2]}); ] ); ] ); If( Degree(f[1])=?2, [ If(InVerboseMode(),Tell(\" \",Degree(f[1]))); Local(pq); pq := PrimitivePart(f[1]); DestructiveAppend(factrsnew,{pq,f[2]}); ] );   If( Degree(f[1]) >? 2, [ If(InVerboseMode(),Tell(\" \",Degree(f[1]))); Local(pq); pq := PrimitivePart(f[1]); DestructiveAppend(residuals,{pq,f[2]}); If(InVerboseMode(),Tell(\" appending to residuals\",pq)); ] );  ]; {factrsnew,residuals};];UnFence(\"FixUpMonicFactors\",1);";
        scriptMap.put("jFactorsPoly",scriptString);
        scriptMap.put("jFactorsPrimitivePart",scriptString);
        scriptMap.put("jFactorsHomogeneousBivariate",scriptString);
        scriptMap.put("RealToRationalConvert",scriptString);
        scriptMap.put("RationalToIntegerConvert",scriptString);
        scriptMap.put("CombineNumericalFactors",scriptString);
        scriptMap.put("jFactorsBivariateBinomial",scriptString);
        scriptMap.put("jFB",scriptString);
        scriptMap.put("IsPureRational",scriptString);
        scriptMap.put("IsPureRational",scriptString);
        scriptMap.put("HasRealCoefficients",scriptString);
        scriptMap.put("HasRealCoefficients",scriptString);
        scriptMap.put("HasRationalCoefficients",scriptString);
        scriptMap.put("HasRationalCoefficients",scriptString);
        scriptMap.put("FixUpMonicFactors",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "PollardCombineLists(_left,_right) <--[ ForEach(item,right) [ PollardMerge(left,item); ]; left;];";
        scriptMap.put("PollardCombineLists",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # PollardMerge(_list,{1,_n}) <-- True;10 # PollardMerge(_list,_item)_(Assoc(item[1],list) =? Empty) <-- DestructiveInsert(list,1,item);20 # PollardMerge(_list,_item) <--[ Local(assoc); assoc := Assoc(item[1],list); assoc[2]:=assoc[2]+item[2];];";
        scriptMap.put("PollardMerge",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "PollardRhoPolynomial(_x) <-- x^2+1;2# PollardRhoFactorize(n_PrimePower?) <-- {GetPrimePower(n)};3# PollardRhoFactorize(_n) <--[ Local(x,y,restarts,gcd,repeat); gcd:=1; restarts := 100;  While(gcd =? 1 And? restarts>=?0)  [ restarts--;  x:= RandomInteger(n-1);  gcd:=1; y:=x; repeat := 4;   While(gcd =? 1 And? repeat>=?0) [ x:= Modulo( PollardRhoPolynomial(x), n); y:= Modulo( PollardRhoPolynomial( Modulo( PollardRhoPolynomial(y), n)  ), n); If(x-y =? 0, [ gcd := 1; repeat--;  ], gcd:=Gcd(x-y,n) ); ]; If(InVerboseMode() And? repeat<=?0, Echo({\"PollardRhoFactorize: Warning: stalled while factorizing \", n, \"; counters \", x, y})); ]; Check(restarts>?0, \"Math\", \"PollardRhoFactorize: Error: failed to factorize \" ~ ToString(n)); If(InVerboseMode() And? gcd >? 1, Echo({\"PollardRhoFactorize: Info: while factorizing \", n, \" found factor \", gcd}));  PollardCombineLists(PollardRhoFactorize(gcd), PollardRhoFactorize(Quotient(n,gcd)));];";
        scriptMap.put("PollardRhoFactorize",scriptString);
        scriptMap.put("PollardRhoPolynomial",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # xContent( poly_CanBeUni ) <--[ Local(disassem,gcdCoefs,lc,minExpts); disassem := DisassembleExpression(poly); gcdCoefs := Gcd(disassem[3]); lc := LeadingCoef(poly); If(NegativeNumber?(lc) And? gcdCoefs >? 0, gcdCoefs:=-gcdCoefs);   gcdCoefs;];";
        scriptMap.put("xContent",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # xFactor( p_CanBeUni ) <-- FW(xFactors(p));10 # xFactor( p_RationalFunction? ) <--[ Local(fs,n,d); fs := xFactors( p ); n := FW(Numerator(fs)); d := FW(Denominator(fs)); n/d;];10 # xFactor( L_List? ) <-- [ Local (result,x,f); result := {}; ForEach(x,L) [ f := xFactors(x); If( f =? {}, f := 0, f := FW(f) ); DestructiveAppend(result,f); ]; result;];20 # xFactor( _expr ) <-- expr;";
        scriptMap.put("xFactor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # xFactors( L_List? ) <-- xFactors /@ L;10 # xFactors(p_PositiveInteger?) <-- [ If( p <? 1600, FactorsSmallInteger(p), FactorizeInt(p) );];12 # xFactors(p_NegativeInteger?) <-- xFactors(-p);14 # xFactors(p_Rational?)_(Denominator(p) !=? 1) <--  { {xFactor(Numerator(p)) / xFactor(Denominator(p) ) , 1} };  16 # xFactors(p_GaussianInteger?) <-- GaussianFactors(p);18 # xFactors(_p)_(Length(VarList(p))=?0) <-- {{p,1}};21 # xFactors( poly_CanBeUni ) <--[ If(InVerboseMode(),Tell(\"xFactors_can_be_uni_100122\",poly)); Local(content,pp,ppFactors,monomialFactors,result); Local(vars,nvars,disassem,degrees,mpoly,nterms,allCoeffs,allPowers); Local(hasRealCoefficients,hasRationalCoefficients,isHomogeneous);   hasRealCoefficients := HasRealCoefficients(poly); If( hasRealCoefficients,  [ Local(realPoly); realPoly := poly;  disassem := DisassembleExpression(poly); allCoeffs := disassem[3]; allPowers := Flatten(disassem[2],\"List\"); poly := ApproximateRealCoefficients(poly); ] );   hasRationalCoefficients := HasRationalCoefficients(poly);   content := xContent( poly ); pp := xPrimitivePart( poly, content ); If(InVerboseMode(),Tell(\" \",{content,pp})); vars := VarList(pp); nvars := Length(vars); disassem := DisassembleExpression(pp); nterms := Length(disassem[3]); degrees := {}; allCoeffs := disassem[3]; allPowers := Flatten(disassem[2],\"List\"); If(nvars >? 0, [ ForEach(v,vars) [ DestructiveAppend(degrees,Degree(pp,v)); ]; isHomogeneous := [   Local(sd,cmp);  sd := Sum /@ disassem[2]; cmp := FillList(sd[1],Length(sd)); ZeroVector?(sd - cmp); ]; ] );     pp := MetaSet(pp,\"nvars\",nvars); pp := MetaSet(pp,\"nterms\",nterms); pp := MetaSet(pp,\"degrees\",degrees); pp := MetaSet(pp,\"isHomogeneous\",isHomogeneous);  If(InVerboseMode(), [ Tell(\" \",vars); Tell(\" \",nvars); Tell(\" \",nterms); Tell(\" \",degrees); Tell(\" \",disassem); Tell(\" \",allCoeffs); Tell(\" \",allPowers); Tell(\" \",isHomogeneous); NewLine(); ] );   ppFactors := xFactorsPrimitivePart( pp ); If(InVerboseMode(),[NewLine();Tell(\" \",ppFactors);]);   If(InVerboseMode(),NewLine()); monomialFactors := FactorsMonomial(content); If(InVerboseMode(),[Tell(\" \",monomialFactors);]); If( monomialFactors[1][1] =? 1, result := ppFactors,  result := Concat(monomialFactors,ppFactors) );  If(InVerboseMode(),[NewLine();Tell(\" final \",result);]); result;]; 10 # xFactorsPrimitivePart( _pp )_(nterms=?2) <--[ If(InVerboseMode(),Tell(\"Binomial\")); Local(ppFactors); ppFactors := xFactorsBinomial(pp);];UnFence(\"xFactorsPrimitivePart\",1); 12 # xFactorsPrimitivePart( _pp )_(isHomogeneous And? nvars=?2) <--[ If(InVerboseMode(),Tell(\"Homogeneous and Bivariate\")); Local(ppFactors); ppFactors := xFactorsHomogeneousBivariate(disassem);];UnFence(\"xFactorsPrimitivePart\",1); 14 # xFactorsPrimitivePart( _pp )_(nvars=?0) <--[ Local(ppFactors); ppfactors := {};]; 16 # xFactorsPrimitivePart( _pp )_(nvars=?1) <-- xFactorsUnivariate(pp);UnFence(\"xFactorsPrimitivePart\",1); 18 # xFactorsPrimitivePart( _pp )_(nvars>?1) <-- xFactorsMultivariate(pp);UnFence(\"xFactorsPrimitivePart\",1); 20 # xFactorsPrimitivePart( _pp ) <-- Tell(\"Should never get here!\");UnFence(\"xFactorsPrimitivePart\",1);30 # xFactorsUnivariate( poly_CanBeUni )_(Length(VarList(poly))=?1) <--[ Local(factrs,coeffs,deg,X,residuals,factrsnew); If(InVerboseMode(), [ NewLine(); Tell(\"xFactorsUnivariate\",poly); Tell(\" \",allCoeffs); ] );     factrs := BinaryFactors(poly); If(InVerboseMode(),Tell(\" output of BinaryFactors\",factrs));      {factrsnew,residuals} := FixUpMonicFactors(factrs);   Local(residOut); residOut := {}; If(Length(residuals) >? 0, residOut := xFactorsResiduals( residuals ) );  If(InVerboseMode(), [ NewLine(); Tell(\" just before end of univariate factoring\"); Tell(\" \",factrs); Tell(\" \",factrsnew); Tell(\" \",residOut); ] );   Local(final); If(Length(Union(factrsnew,residOut)) >? 0, final := Concat(factrsnew,residOut), final := factrs ); CheckForSpecialForms( final );]; UnFence(\"xFactorsUnivariate\",1);40 # xFactorsMultivariate( poly_CanBeUni )_(Length(VarList(poly))>?1) <--[ Local(factrs);  If(InVerboseMode(),[NewLine();Tell(\"xFactorsMultivariate\",poly);]); If( nterms =? 2, [ If(InVerboseMode(),Tell(\" Is Binomial\")); factrs := xFactorsBinomial(poly);  ], [ If(InVerboseMode(),Tell(\" Has more than 2 terms\")); ] ); factrs;];UnFence(\"xFactorsMultivariate\",1);10 # xFactorsHomogeneousBivariate( dis_List? ) <-- [ If(InVerboseMode(),[NewLine();Tell(\"xFactorsHomogeneousBivariate\",dis);]); Local(dis1,f,lst,dis2,poly1,ppFactors,residuals,ii,preassem); dis1 := {{xi},{{X},{X[1]}} /@ dis[2],dis[3]}; If(InVerboseMode(),Tell(\" \",dis1)); poly1 := Sum(ReassembleListTerms(dis1)); If(InVerboseMode(),Tell(\" \",poly1)); ppFactors := BinaryFactors(poly1); {ppFactors,residuals} := FixUpMonicFactors(ppFactors); For(ii:=1,ii<=?Length(ppFactors),ii++) [ f := ppFactors[ii]; If(InVerboseMode(),Tell(\" \",f[1])); lst := DisassembleExpression(f[1]); If(InVerboseMode(), [ Tell(\" \",lst); Tell(\" \",dis[1]); ] ); DestructiveReplace(lst,1,dis[1]); DestructiveAppend(lst[2][1],0); DestructiveAppend(lst[2][2],1); If(InVerboseMode(),Tell(\" \",lst)); preassem := Sum(ReassembleListTerms(lst)) ; If(InVerboseMode(),Tell(\" \",preassem)); ppFactors[ii][1] := preassem; ]; If(InVerboseMode(),[Tell(\" \",ppFactors); Tell(\" \",residuals);NewLine();] );  ppFactors;];UnFence(\"xFactorsHomogeneousBivariate\",1);10 # CheckForSpecialForms( final_List? ) <-- [ If(InVerboseMode(),[NewLine();Tell(\"CheckForSpecialForms\",final);]); Local(LL,ii,fact,mult,dis,new); new := {}; LL := Length(final); For(ii:=1,ii<=?LL,ii++) [ fact := final[ii][1]; mult := final[ii][2]; If(InVerboseMode(),Tell(\" \",{fact,mult})); dis := DisassembleExpression( fact ); If(InVerboseMode(),Tell(\" \",dis)); Local(var); var := dis[1][1]; if ( dis[2]=?{{4},{2},{0}} And? dis[3]=?{1,1,1} ) [ Local(new1,new2); new1 := {var^2-var+1,mult}; new2 := {var^2+var+1,mult}; DestructiveAppend(new,new1); DestructiveAppend(new,new2); If(InVerboseMode(),Tell(\" \",new)); ] else [ If(InVerboseMode(),Tell(\" no special form\")); DestructiveAppend(new,{fact,mult}); ]; ); ]; new;];10 # ApproximateRealCoefficients( poly_Polynomial? ) <--[   If(InVerboseMode(),[NewLine();Tell(\" REAL\",poly);]); Local(coeffs,gcd,lcm); coeffs := Rationalize /@ (allCoeffs); If(InVerboseMode(),[Tell(\" to-Q\",coeffs);Tell(\" to-Z\",coeffs);]); Local(gcd,lcm); gcd := Gcd(Numerator /@ coeffs); lcm := Lcm(Denominator /@ coeffs); If(InVerboseMode(),[Tell(\" \",gcd);Tell(\" \",lcm);]); disassem[3] := coeffs; allCoeffs := coeffs; poly := Sum(ReassembleListTerms(disassem)); If(InVerboseMode(),Tell(\" new\",poly)); poly;];UnFence(\"ApproximateRealCoefficients\",1);100 # CombineNumericalFactors( factrs_List? ) <--[ If( InVerboseMode(), Tell(\"Combine\",factrs) ); Local(q,a,b,t,f,ff,err); err := False; t := 1; f := {}; ForEach(q,factrs) [ If( InVerboseMode(), Tell(1,q) ); If( List?(q) And? Length(q)=?2, [ {a,b} := q; If( InVerboseMode(), Echo(\" \",{a,b}) ); If( NumericList?( {a,b} ), t := t * a^b, f := {a,b}~f ); ], err := True ); ]; If( InVerboseMode(), [ Echo(\" t = \",t); Echo(\" f = \",f); Echo(\" err = \",err); ] ); ff := If(Not? err And? t !=? 1, {t,1}~Reverse(f), factrs); ff := Select(Lambda({x},x!=?{1,1}),ff); If(ff[1]<?0,ff[1]:=-ff[1]);];150 # xFactors( expr_RationalFunction? )_ (Polynomial?(Numerator(expr)) And? Polynomial?(Denominator(expr))) <--[ If(InVerboseMode(),[NewLine();Tell(\"xFactors_Rational_Function\",expr);]); Local(Numer,Denom,fNumer,fDenom); Numer := Numerator(expr); Denom := Denominator(expr); fNumer := xFactors(Numer); fDenom := xFactors(Denom); If(InVerboseMode(),[Tell(\" \",fNumer); Tell(\" \",fDenom);]); fNumer/fDenom;];152 # xFactors( expr_RationalFunction? )_ (Constant?(Numerator(expr)) And? Polynomial?(Denominator(expr))) <--[ If(InVerboseMode(),[NewLine();Tell(\"xFactors_Rational_Denom\",expr);]); Local(Numer,Denom,fNumer,fDenom); Numer := Numerator(expr); Denom := Denominator(expr); fNumer := xFactors(Numer); fDenom := xFactors(Denom); If(InVerboseMode(),[Tell(\" \",fNumer); Tell(\" \",fDenom);]); fNumer/fDenom;];200 # xFactors( _expr )_(Length(VarList(expr)) =? 1) <--[ If(InVerboseMode(),[NewLine();Tell(\"Some other kind of expression\",expr);]); Local(dis,X,pows); dis := DisassembleExpression(expr); X := VarList(expr)[1]; pows := matchPower /@ dis[1]; rats := NearRational /@ pows; dis[1] := x^rats; p := Sum(ReassembleListTerms(dis)); If(InVerboseMode(),Tell(\" new \",p)); xFactors(p);];10 # IsPureRational( N_Rational? )_(Not? Integer?(N)) <-- True;12 # IsPureRational( _N ) <-- False;10 # HasRealCoefficients( poly_Polynomial? ) <--[ Local(disassem); disassem := DisassembleExpression(poly); (Length(Select(disassem[3],\"Decimal?\")) >? 0);];10 # HasRealCoefficients( poly_Monomial? ) <--[ Local(disassem); disassem := DisassembleExpression(poly); (Length(Select(disassem[3],\"Decimal?\")) >? 0);];10 # HasRationalCoefficients( poly_Polynomial? ) <--[ Local(disassem); disassem := DisassembleExpression(poly);  (Length(Select(disassem[3],\"IsPureRational\")) >? 0);];10 # HasRationalCoefficients( poly_Monomial?) <--[ Local(disassem); disassem := DisassembleExpression(poly); (Length(Select(disassem[3],\"IsPureRational\")) >? 0);];10 # FixUpMonicFactors( factrs_List? ) <--[ If(InVerboseMode(),[ NewLine(); Tell(\" doing monic fixup\"); ] ); Local(factrsnew,residuals,uni); factrsnew := {}; residuals := {}; ForEach(f,factrs) [ If(InVerboseMode(),Tell(\" \",f)); uni := MakeUni(f[1]); If(InVerboseMode(),Tell(\" \",uni)); If( Degree(f[1])=?1, [ Local(cc,lcm,fnew); If(InVerboseMode(),Tell(\" \",Degree(f[1]))); cc := Coef(f[1],uni[1],0 .. 1);  lcm := Lcm( Denominator /@ cc ); uni[3] := lcm * cc; fnew := NormalForm(uni); If( hasRationalCoefficients, [ DestructiveAppend(factrsnew,f); ], [  DestructiveAppend(factrsnew,{fnew,f[2]}); ] ); ] ); If( Degree(f[1])=?2, [ If(InVerboseMode(),Tell(\" \",Degree(f[1]))); Local(pq); pq := PrimitivePart(f[1]); DestructiveAppend(factrsnew,{pq,f[2]}); ] );   If( Degree(f[1]) >? 2, [ If(InVerboseMode(),Tell(\" \",Degree(f[1]))); Local(pq); pq := PrimitivePart(f[1]); DestructiveAppend(residuals,{pq,f[2]}); If(InVerboseMode(),Tell(\" appending to residuals\",pq)); ] );  ]; {factrsnew,residuals};];UnFence(\"FixUpMonicFactors\",1);10 # IsIrreducible( poly_Polynomial? )_(Length(VarList(poly))=?1) <--[      If(InVerboseMode(),Tell(\"IsIrreducible\",poly)); Local(var,deg,coeffs,num1); var := VarList(poly)[1]; deg := Degree(poly); coeffs := Coef(poly,var,deg .. 0); If(InVerboseMode(),Tell(\" \",deg)); Local(ii,res,nprimes); nprimes := 0; For(ii:=-3*deg,ii<=?3*deg,ii:=ii+3) [ res := N(Subst(x,ii) poly);  If(Abs(res)=?1 Or? Prime?(res), nprimes := nprimes + 1, ); ]; Tell(\" \",nprimes); If(nprimes >? 2*deg, True, False );];10 # matchPower(_Z^n_Number?) <-- n;15 # matchPower(_Z) <-- 1;";
        scriptMap.put("xFactors",scriptString);
        scriptMap.put("xFactorsPrimitivePart",scriptString);
        scriptMap.put("xFactorsUnivariate",scriptString);
        scriptMap.put("xFactorsMultivariate",scriptString);
        scriptMap.put("xFactorsHomogeneousBivariate",scriptString);
        scriptMap.put("CheckForSpecialForms",scriptString);
        scriptMap.put("ApproximateRealCoefficients",scriptString);
        scriptMap.put("CombineNumericalFactors",scriptString);
        scriptMap.put("IsPureRational",scriptString);
        scriptMap.put("HasRealCoefficients",scriptString);
        scriptMap.put("HasRationalCoefficients",scriptString);
        scriptMap.put("FixUpMonicFactors",scriptString);
        scriptMap.put("IsIrreducible",scriptString);
        scriptMap.put("matchPower",scriptString);
        scriptMap.put("matchPower",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Retract(\"xFactorsBinomial\",*);Retract(\"xFB1\",*);Retract(\"xFB2\",*);Retract(\"IsPowerOf2\",*);10 # xFactorsBinomial( poly_Polynomial? )_(Length(VarList(poly))=?1) <-- [ If(InVerboseMode(),Tell(xFactorsBinomial,poly)); Local(dis,n,X,var,A,B,s,Ar,Br); dis := DisassembleExpression(poly); If(InVerboseMode(),Tell(\" \",dis)); n := Maximum(dis[2])[1]; X := dis[1][1]; var := dis[1][1]; A := Abs(dis[3][1]); B := Abs(dis[3][2]); s := Sign(dis[3][1]*dis[3][2]); Ar := NearRational(N(A^(1/n),20)); Br := NearRational(N(B^(1/n),20)); If(InVerboseMode(),[Tell(\" \",{n,X,var,A,B}); Tell(\" \",{Ar,Br,s});]); If( Integer?(Ar) And? Integer?(Br), xFB1(dis), {{poly,1}} );];10 # xFactorsBinomial( poly_Polynomial? )_(Length(VarList(poly))=?2) <-- [ If(InVerboseMode(),Tell(xFactorsBinomial,poly)); Local(dis,n,X,Y,vars,A,B,s,Ar,Br); dis := DisassembleExpression(poly); If(InVerboseMode(),Tell(\" \",dis)); n := Maximum(dis[2])[1]; X := dis[1][1]; Y := dis[1][2]; vars := dis[1]; A := Abs(dis[3][1]); B := Abs(dis[3][2]); s := Sign(dis[3][1]*dis[3][2]); Ar := NearRational(N(A^(1/n))); Br := NearRational(N(B^(1/n))); If(InVerboseMode(), [ Tell(\" \",{n,X,Y});  Tell(\" \",{vars,A,B}); Tell(\" \",{Ar,Br,s}); ] ); If( Integer?(Ar) And? Integer?(Br), xFB2(dis), {{poly,1}} );];12 # xFB1( dis_List? )_(Length(dis)=?3 And? Length(dis[3])=?2) <--[ If(InVerboseMode(),[NewLine();Tell(\"xFB1\",dis);]); Local(Y,y,ii,fac1); X := Ar*X; Y := Br; Unbind(y); y := 1; If(InVerboseMode(), Tell(\" \",{X,Y})); fac1 := xFB1( X/Y,n,s);   If( InVerboseMode(),Tell(\" \",fac1));   If( Y !=? 1, [ Local(f,d); For(ii:=1,ii<=?Length(fac1),ii++) [ f := fac1[ii][1]; d := Degree(f,var); If(InVerboseMode(),Tell(\" \",{ii,f,d})); fac1[ii][1] := Simplify(Y^d*f); ]; ] ); fac1;];UnFence(\"xFB1\",1);15 # xFB1(_X,n_SmallPrime?,s_NotZero?)_(Odd?(n)) <--[ Local(ans,k); If(InVerboseMode(),[NewLine();Tell(\" xFB1prime\",{X,n,s});]); ans := {{X+s,1}}; If( n >? 1, ans := Concat(ans,{{Sum(k,0,n-1,(-s)^k*X^(n-1-k)),1}}) ); If(InVerboseMode(),Tell(\" \",ans)); ans;];UnFence(\"xFB1\",3);20 # xFB1(_X, n_Odd?, s_PositiveInteger?) <--[ Local(ans,ans1); If(InVerboseMode(),[NewLine(); Tell(\" xFB1oddsum\",{X,Y,n});]); if ( n =? 9 ) [ ans := {{X+1,1},{X^2-X+1,1},{X^6-X^3+1,1}}; ] else if ( n =? 15 ) [ ans := {{X+1,1},{X^2-X+1,1},{X^4-X^3+X^2-X+1,1},{X^8+X^7-X^5-X^4-X^3+X+1,1}}; ] else if ( n =? 21 ) [ ans := {{X+1,1},{X^2-X+1,1},{X^6-X^5+X^4-X^3+X^2-X+1,1},{X^12+X^11-X^9-X^8+X^6-X^4-X^3+X+1,1}}; ] else if ( n =? 25 ) [ ans := {{X+1,1},{X^4-X^3+X^2-X+1,1},{X^20-X^15+X^10-X^5+1,1}}; ] else if ( n =? 35 ) [ ans := {{X+1,1},{X^4-X^3+X^2-X+1,1},{X^6-X^5+X^4-X^3+X^2-X+1,1},{X^24+X^23-X^19-X^18-X^17-X^16+X^14+X^13+X^12+X^11+X^10-X^8-X^7-X^6-X^5+X+1,1}}; ] else if ( n =? 45 ) [ ans := {{X+1,1},{X^2-X+1,1},{X^4-X^3+X^2-X+1,1},{X^6-X^3+1,1},{X^8+X^7-X^5-X^4-X^3+X+1,1},{X^24+X^21-X^15-X^12-X^9+X^3+1,1}}; ] else [ ans := BinaryFactors(X^n+1); ];  ans;];25 # xFB1(_X,n_Odd?, s_NegativeInteger?) <--[ Local(ans); If(InVerboseMode(),[NewLine(); Tell(\" xFB1odddif\",{X,n});]); if ( n =? 9 ) [ ans := {{X-1,1},{X^2+X+1,1},{X^6+X^3+1,1}}; ] else if ( n =? 15 ) [ ans := {{X-1,1},{X^2+X+1,1},{X^4+X^3+X^2+X+1,1},{X^8-X^7+X^5-X^4+X^3-X+1,1}}; ] else if ( n =? 21 ) [ ans := {{X-1,1},{X^2+X+1,1},{X^6+X^5+X^4+X^3+X^2+X+1,1},{X^12-X^11+X^9-X^8+X^6-X^4+X^3-X+1,1}}; ] else if ( n =? 25 ) [ ans := {{X-1,1},{X^4+X^3+X^2+X+1,1},{X^20+X^15+X^10+X^5+1,1}}; ] else if ( n =? 35 ) [ ans := {{X-1,1},{X^4+X^3+X^2+X+1,1},{X^6+X^5+X^4+X^3+X^2+X+1,1},{X^24-X^23+X^19-X^18+X^17-X^16+X^14-X^13+X^12-X^11+X^10-X^8+X^7-X^6+X^5-X+1,1}}; ] else if ( n =? 45 ) [ ans := {{X-1,1},{X^2+X+1,1},{X^4+X^3+X^2+X+1,1},{X^6+X^3+1,1},{X^8-X^7+X^5-X^4+X^3-X+1,1},{X^24-X^21+X^15-X^12+X^9-X^3+1,1}}; ] else [ ans := BinaryFactors(X^n-1); ];  ans; If(InVerboseMode(),Tell(\" \",ans)); ans;];30 # xFB1(_X, n_Even?, s_PositiveInteger?) <--[ Local(ans,fn,mx,my); If(InVerboseMode(),[NewLine(); Tell(\" xFB1evensum\",{X,n});]); fn := {{1,1}}; If( n >? 1, fn := FactorsSmallInteger(n) ); If(Length(fn)=?1 And? Odd?(fn[1][1]), mx:= fn[1][1]^(fn[1][2]-1)); If(Length(fn)>?1, ForEach(f,fn) [ If( Odd?(f[1]), mx := f[1]^f[2] ); ]); my := n/mx; If(InVerboseMode(),Tell(\" \",{mx,my})); If( IsPowerOf2(n), [  ans := {{X^n+1,1}}; ], [  if ( n =? 6 ) [ ans := {{X^2+1,1},{X^4-X^2+1,1}}; ] else if ( n =? 10 ) [ ans := {{X^2+1,1},{X^8-X^6+X^4-X^2+1,1}}; ] else if ( n =? 20 ) [ ans := {{X^4+1,1},{X^16-X^12+X^8-X^4+1,1}}; ] else if ( n =? 30 ) [ ans := {{X^2+1,1},{X^4-x^2+1,1},{X^8-X^6+X^4-X^2+1,1},{X^16+X^14-X^10-X^8-X^6+X^2+1,1}}; ] else if ( n =? 40 ) [ ans := {{X^8+1,1},{X^32-X^24+X^16-X^8+1,1}}; ] else if ( n =? 50 ) [ ans := {{X^2+1,1},{X^8-X^6+X^4-X^2+1,1},{X^40-X^30+X^20-X^10+1,1}}; ] else if ( n =? 100 ) [ ans := {{X^4+1,1},{X^16-X^12+X^8-X^4+1,1},{X^80-X^60+X^40-X^20+1,1}}; ] else [ ans := {{X^my+1,1},{Sum(k,0,mx-1,X^(n-my-k*my)*(-1)^k),1}}; ]; ] ); If(InVerboseMode(),Tell(\" \",ans)); ans;];35 # xFB1(_X, n_Even?, s_NegativeInteger?) <--[ Local(ans); If(InVerboseMode(),[NewLine(); Tell(\" xFB1evendif\",{X,n});]); if ( n =? 2 ) [ ans := {{X-1,1},{X+1,1}}; ] else if ( n =? 10 ) [ ans := {{X-1,1},{X+1,1},{X^4+X^3+X^2+X+1,1},{X^4-X^3+X^2-X+1,1}}; ] else if ( n =? 20 ) [ ans := {{X-1,1},{X+1,1},{X^2+1,1},{X^4+X^3+X^2+X+1,1},{X^4-X^3+X^2-X+1,1},{X^8-X^6+X^4-X^2+1,1}}; ] else if ( n =? 30 ) [ ans := {{X-1,1},{X+1,1},{X^2+X+1,1},{X^2-X+1,1},{X^4+X^3+x^2+X+1,1},{X^4-X^3+x^2-X+1,1},{X^8-X^7+X^5-X^4+X^3-X+1,1},{X^8+X^7-X^5-X^4-X^3+X+1,1}}; ] else if ( n =? 40 ) [ ans := {{X-1,1},{X+1,1},{X^2+1,1},{X^4+1,1},{X^4+X^3+X^2+X+1,1},{X^4-X^3+X^2-X+1,1},{X^8-X^6+X^4-X^2+1,1},{X^16-X^12+X^8-X^4+1,1}}; ] else if ( n =? 50 ) [ ans := {{X-1,1},{X+1,1},{X^4+X^3+X^2+X+1,1},{X^4-X^3+X^2-X+1,1},{X^20+X^15+X^10+X^5+1,1},{X^20-X^15+X^10-X^5+1,1}}; ] else if ( n =? 100 ) [ ans := {{X-1,1},{X+1,1},{X^2+1,1},{X^4+X^3+X^2+X+1,1},{X^4-X^3+X^2-X+1,1},{X^8-X^6+X^4-X^2+1,1},{X^20+X^15+X^10+X^5+1,1},{X^20-X^15+X^10-X^5+1,1},{X^40-X^30+X^20-X^10+1,1}};] else [ ans := Concat( xFB1(X,n/2,1), xFB1(X,n/2,-1) ); ];   If(InVerboseMode(),Tell(\" \",ans)); ans;];50 # xFB2( dis_List? )_(Length(dis)=?3 And? Length(dis[3])=?2) <--[ If(InVerboseMode(),[NewLine();Tell(\"xFB2\",dis);]); Local(ns,ii,fn,mx,my,fac2); If(InVerboseMode(), [ Tell(\" \",n); Tell(\" \",{X,Y}); Tell(\" \",{A,B,s}); Tell(\" \",{Ar,Br}); ] ); X := Ar*X; Y := Br*Y; If(InVerboseMode(),Tell(\" \",{X,Y}));  fac2 := xFB1( X/Y,n,s);  If(InVerboseMode(),Tell(\" \",fac2));   If( Y !=? 1, [ Local(f,d); For(ii:=1,ii<=?Length(fac2),ii++) [ f := fac2[ii][1]; d := Degree(f,vars[1]); If(InVerboseMode(),Tell(\" \",{ii,f,d})); fac2[ii][1] := Simplify(Simplify(Y^d*f)); ]; ] ); fac2;];UnFence(\"xFB2\",1);IsPowerOf2( n_PositiveInteger? ) <-- [ Count(StringToList(ToBase(2,n)),\"1\") =? 1; ];";
        scriptMap.put("xFactorsBinomial",scriptString);
        scriptMap.put("xFB1",scriptString);
        scriptMap.put("xFB2",scriptString);
        scriptMap.put("IsPowerOf",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # xFactorsResiduals( residualList_List? ) <--[ If(InVerboseMode(),[NewLine(); Tell(\"Residuals\",residualList);]); If(InVerboseMode(),Tell(\" --\",content)); If(InVerboseMode(),Tell(\" --\",factrs)); If(InVerboseMode(),Tell(\" --\",factrsnew)); If(InVerboseMode(),Tell(\" --\",residuals)); If(InVerboseMode(),Tell(\" -- original \",degrees)); Local(resid,sqf,sqfGood,rrGood);   residualList := trySQF(residualList);   If(InVerboseMode(), [ Tell(\" after trying SQF on all residuals\"); Tell(\" \",sqfGood); Tell(\" \",factrsnew); Tell(\" \",residualList); NewLine(); ] );   tryRealRoots(residualList);  If(InVerboseMode(), [ Tell(\" after trying for REAL roots on all residuals\"); Tell(\" \",rrGood); Tell(\" \",factrsnew); Tell(\" \",residuals); Tell(\" \",residualList); NewLine(); ] ); residOut;];UnFence(\"xFactorsResiduals\",1);10 # trySQF( residualList_List? ) <--[  Local(resid,sqf); If(InVerboseMode(),[NewLine(); Tell(\"trySQF\",residualList);]); ForEach(resid,residualList) [ If(InVerboseMode(),Tell(\" \",resid));  sqf := SquareFree(resid[1]); If(InVerboseMode(), [ Tell(\" trying SQF\"); Tell(\" \",resid[1]); Tell(\" \",sqf); ] ); If(Degree(sqf) <? Degree(resid[1]), [ If(InVerboseMode(),Tell(\" sqf helps factor resid\")); sqfGood := True; Local(f1,f2); f1 := sqf; f2 := Simplify(resid[1]/sqf); If( f2 =? f1, factrsnew := Concat({{f1,2*resid[2]}},factrsnew), factrsnew := Concat({{f1,resid[2]},{f2,resid[2]}},factrsnew) );  residuals := Difference(residuals,{resid}); If(InVerboseMode(),Tell(\" new\",residuals)); residualList := residuals; ], [ If(InVerboseMode(), [ Tell(\" sqf DOES NOT HELP factor resid\"); sqfGood := False; ] ); ] ); If(InVerboseMode(),Tell(\" after sqf \",factrsnew)); If(InVerboseMode(),Tell(\" \",residuals)); If(InVerboseMode(),Tell(\" \",residualList));  ]; residualList; ];UnFence(\"trySQF\",1);10 # tryRealRoots(residualList_List?)_(Length(residualList)>?0) <--[  If(InVerboseMode(),[NewLine(); Tell(\"tryRealRoots\",residualList);]); ForEach(resid,residualList) [ Local(nrr,rr,ptry,uptry); nrr := RealRootsCount(resid[1]); If(InVerboseMode(), [ Tell(\" this \",resid[1]); Tell(\" \",nrr); ] ); If( nrr >? 0, rr := FindRealRoots(resid[1]), rr := {} ); processRealRoots(rr);  If( nrr =? 2, [ If( nrr =? 0, [  If(InVerboseMode(), [ NewLine(); Tell(\" NO real solutions\"); Tell(\" try something else\"); ] );  Local(u,X); u := MakeUni(resid[1]); X := u[1]; If( u[2]=?0 And? u[3]=?{1,0,1,0,1}, [  DestructiveAppend(residOut,{X^2-X+1,1}); DestructiveAppend(residOut,{X^2+X+1,1}); If(InVerboseMode(), [ Tell(\" found \",factrsnew); Tell(\" \",resid); Tell(\" \",factrs); Tell(\" \",residOut); ] ); ] ); ], [  rr := FindRealRoots(resid[1]); If(InVerboseMode(),Tell(\" \",rr));  goodptry := {}; For(ii:=1,ii<?nrr,ii++) For(jj:=ii+1,jj<=?nrr,jj++) [ If(InVerboseMode(),Tell(\" \",{ii,jj})); ptry := Expand((x-rr[ii])*(x-rr[jj])); uptry := MakeUni(ptry); uptry[3] := \"NearRational\" /@ uptry[3]; If(InVerboseMode(),Tell(\" rat\",uptry[3])); If(InVerboseMode(),Tell(\" \",Maximum(Denominator /@ uptry[3]))); If( Maximum(Denominator /@ uptry[3]) <? 100, [ DestructiveAppend(goodptry,NormalForm(uptry)); ] ); ]; If(InVerboseMode(),Tell(\" \",goodptry)); If(Length(goodptry) >? 0, [ ForEach(pt,goodptry) [ DestructiveAppend(residOut,{pt,1}); ]; ] ); ] );  ] );  ]; ];UnFence(\"tryRealRoots\",1);10 # processRealRoots( rr_NumericList? )_(Length(rr) =? 1) <--[   If(InVerboseMode(),Tell(\" Only 1 real root\",rr)); Local(root); root := rr[1]; rrGood := False; If(Integer?(root),  [ If(InVerboseMode(),Tell(\" integer \",root)); rrGood := True; ], [ Local(rroot); rroot := NearRational(root); If(InVerboseMode(),Tell(\" rational \",rroot)); If(Denominator(rroot) <? 100, [root := rroot; rrGood:=True;] ); ] ); ];UnFence(\"processRealRoots\",1);10 # processRealRoots( rr_NumericList? )_(Length(rr) =? 2) <--[  ptry := Expand((x-rr[1])*(x-rr[2])); If(InVerboseMode(),[Tell(\" \",rr);Tell(\" \",ptry);]); uptry := MakeUni(ptry); uptry[3] := \"NearRational\" /@ uptry[3]; ptry := NormalForm(uptry); If(InVerboseMode(),Tell(\" \",ptry)); If( Abs(Lcm(uptry[3])) <? 100, [  Local(f1,f2,new); f1 := ptry; f2 := Simplify(resid[1]/f1); new := {{f1,resid[2]},{f2,resid[2]}}; If(InVerboseMode(),Tell(\" \",new)); resid := new; residOut := new; If(InVerboseMode(),Tell(\" \",residOut));  ] );];UnFence(\"processRealRoots\",1);10 # processRealRoots( rr_NumericList? )_(Length(rr) >=? 4) <--[  If(InVerboseMode(),Tell(\" \",rr));  goodptry := {}; For(ii:=1,ii<?nrr,ii++) For(jj:=ii+1,jj<=?nrr,jj++) [ If(InVerboseMode(),Tell(\" \",{ii,jj})); ptry := Expand((x-rr[ii])*(x-rr[jj])); uptry := MakeUni(ptry); uptry[3] := \"NearRational\" /@ uptry[3]; If(InVerboseMode(),Tell(\" rat\",uptry[3])); If(InVerboseMode(),Tell(\" \",Maximum(Denominator /@ uptry[3]))); If( Maximum(Denominator /@ uptry[3]) <? 100, [ DestructiveAppend(goodptry,NormalForm(uptry)); ] ); ]; If(InVerboseMode(),Tell(\" \",goodptry)); If(Length(goodptry) >? 0, [ ForEach(pt,goodptry) [ DestructiveAppend(residOut,{pt,1}); ]; ] ); ];";
        scriptMap.put("xFactorsResiduals",scriptString);
        scriptMap.put("trySQF",scriptString);
        scriptMap.put("tryRealRoots",scriptString);
        scriptMap.put("processRealRoots",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " 10 # xPrimitivePart(poly_CanBeUni) <-- [ Local(cont,pp); If(InVerboseMode(),Tell(\" xPrimitivePart1\",poly)); cont := xContent(poly); pp := poly / cont; ];Macro(\"xPrimitivePart\",{poly,xcont})[ Local(pp); If(InVerboseMode(),Tell(\" xPrimitivePart2\",{poly,xcont}));  If( Bound?(@xcont),  [ pp := Eval(@poly) / Eval(@xcont); ], [ Local(xCont); xCont := xContent(Eval(@poly)); @xcont := xCont; pp := Eval(@poly) / xCont; ] ); pp; ];";
        scriptMap.put("xPrimitivePart",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Apply(_applyoper,_applyargs) _ (Or?(String?(applyoper), List?(applyoper))) <-- ApplyFast(applyoper,applyargs);20 # Apply(applyoper_Atom?,_applyargs) <-- ApplyFast(ToString(applyoper),applyargs);30 # Apply(Lambda(_args,_body),_applyargs) <-- `ApplyFast(Hold({@args,@body}),applyargs);UnFence(\"Apply\",2);";
        scriptMap.put("Apply",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(NFunctionNumberize)[NFunction(newname_String?, oldname_String?, arglist_List?) <-- [ RulebaseEvaluateArguments(newname, arglist); RuleEvaluateArguments(newname, Length(arglist), 0,  ListToFunction({NumericList?, arglist}) )  NFunctionNumberize(ListToFunction({ToAtom(\"@\"), oldname, arglist})); ];10 # NFunctionNumberize(x_Number?) <-- x;20 # NFunctionNumberize(x_Atom?) <-- Undefined;]; ";
        scriptMap.put("NFunction",scriptString);
        scriptMap.put("NFunctionNumberize",scriptString);
        scriptMap.put("NFunctionNumberize",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"@\",{func,arg});RuleHoldArguments(\"@\",2,1,List?(arg)) Apply(func,arg);RuleHoldArguments(\"@\",2,2,True ) Apply(func,{arg});";
        scriptMap.put("@",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # (countfrom_Integer? .. countto_Integer?)_(countfrom <=? countto) <-- Table(i,i,countfrom,countto,1);20 # (countfrom_Integer? .. countto_Integer?) <-- Table(i,i,countfrom,countto,-1);";
        scriptMap.put("..",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"/@\",{func,lst}) Apply(\"MapSingle\",{func,lst});";
        scriptMap.put("/@",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"~\",{head,tail});RuleHoldArguments(\"~\",2,20,List?(head) And? Not? List?(tail) ) Concat(head,{tail});RuleHoldArguments(\"~\",2,30,List?(tail) ) Concat({head},tail);RuleHoldArguments(\"~\",2,10,String?(tail) And? String?(head)) ConcatStrings(head,tail);UnFence(\"~\",2);";
        scriptMap.put("~",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "anchor:={};anchor[\"0\"]:=\"a\";anchor[\"name\"]:=\"\";link:={};link[\"0\"]:=\"a\";link[\"href\"]:=\"\";frameset:={};frameset[\"0\"]:=\"frameset\";frameset[\"border\"]:=\"0\";frame:={};frame[\"0\"]:=\"frame\";caption:={};caption[\"0\"]:=\"caption\";table:={};table[\"0\"]:=\"table\";form:={};form[\"0\"]:=\"form\";textarea:={};textarea[\"0\"]:=\"textarea\";textfield:={};textfield[\"0\"]:=\"input\";textfield[\"TYPE\"]:=\"text\";button:={};button[\"0\"]:=\"input\";button[\"TYPE\"]:=\"submit\";bullets:={};bullets[\"0\"]:=\"ul\";bullet:={};bullet[\"0\"]:=\"li\";newline:=\"\";Gt():=\"&gt;\";Lt():=\"&lt;\";HtmlNewParagraph():= (newline ~ \"<p>\" ~ newline);HtmlTitle(title):=[\"<head> <title>\" ~ title ~ \"</title> <link rel=\\\"stylesheet\\\" href=\\\"piper.css\\\" TYPE=\\\"text/css\\\" MEDIA=\\\"screen\\\"></head>\";];HtmlAnchor(name):=[ anchor[\"name\"]:=name; HtmlTag(anchor,\"\");];HtmlTable(cellpadding,width,body):=[ table[\"cellpadding\"]:=ToString(cellpadding); table[\"width\"]:=width; HtmlTag(table,body);];Bullets(list):=HtmlTag(bullets,list);Bullet (list):=HtmlTag(bullet ,list);HtmlCaption(title):=[ HtmlTag(caption,title);];HtmlForm(action,body):=[ form[\"method\"]:=\"get\"; form[\"action\"]:=action; HtmlTag(form,body);];HtmlTextArea(name,width,height,body) :=[ textarea[\"name\"]:=name; textarea[\"cols\"]:=ToString(width); textarea[\"rows\"]:=ToString(height); HtmlTag(textarea,body);];HtmlTextField(name,size,value):=[ textfield[\"name\"]:=name; textfield[\"size\"]:=ToString(size); textfield[\"value\"]:=value; HtmlTag(textfield,\"\");];HtmlSubmitButton(name,value):=[ button[\"name\"]:=name; button[\"value\"]:=value; HtmlTag(button,\"\");];HtmlLink(description,file,tag,target):=[ If(tag !=? \"\", link[\"href\"]:= file ~ \"#\" ~ tag, link[\"href\"]:= file); If(target !=? \"\",link[\"target\"] :=target); HtmlTag(link,description);];HtmlFrameSetRows(columns,body):=[ frameset[\"cols\"]:=\"\"; frameset[\"rows\"]:=columns; HtmlTag(frameset,body);];HtmlFrameSetCols(columns,body):=[ frameset[\"cols\"]:=columns; frameset[\"rows\"]:=\"\"; HtmlTag(frameset,body);];HtmlFrame(source,name):=[ frame[\"src\"]:=source; frame[\"name\"]:=name; HtmlTag(frame,\"\");];HtmlTag(tags,content):=[ Local(result,tag,analytics); result:=\"<\" ~ tags[\"0\"]; ForEach(tag,AssocIndices(tags)) [ If (tag !=? \"0\" And? tags[tag] !=? \"\", result:= result ~ \" \" ~ tag ~ \"=?\" ~ \"\\\"\" ~ tags[tag] ~ \"\\\"\" ); ]; analytics:=\"\"; If(tags[\"0\"] =? \"body\", analytics:=\"<script src=\\\"http:</script><script type=\\\"text/javascript\\\">_uacct = \\\"UA-2425144-1\\\";urchinTracker();</script>\"); result:= result ~ \">\" ~ newline ~ content ~ newline ~ analytics ~ \"</\" ~ tags[\"0\"] ~ \">\" ~ newline; result;];htmldir:=\"\";SetHtmlDirectory(dir):= [htmldir:=dir;];HtmlFile(file) := [htmldir ~ file;];site:={};ClearSite() := [site:={};];LoadSite():=[ PipeFromFile(\"siteall\") [ site:=Read(); ];];SaveSite():=[ PipeToFile(\"siteall\") [ Write(site); WriteString(\";\"); ];];MySQLQuery(pidstr,string):=[ Local(result); PipeToFile(\"sqlin\"~pidstr) WriteString(string); SystemCall(\"mysql mysql < \"~\"sqlin\"~pidstr~\" > sqlout\"~pidstr); SystemCall(FindFile(\"tools/mysqlstubs\")~\" sqlout\"~pidstr~\" sqlout_\"~pidstr); result:= PipeFromFile(\"sqlout_\"~pidstr)Read(); SystemCall(\"rm -rf sqlin\"~pidstr); SystemCall(\"rm -rf sqlout\"~pidstr); SystemCall(\"rm -rf sqlout_\"~pidstr); result;];";
        scriptMap.put("HtmlNewParagraph",scriptString);
        scriptMap.put("HtmlAnchor",scriptString);
        scriptMap.put("HtmlLink",scriptString);
        scriptMap.put("HtmlTable",scriptString);
        scriptMap.put("HtmlCaption",scriptString);
        scriptMap.put("HtmlTitle",scriptString);
        scriptMap.put("HtmlFrameSetRows",scriptString);
        scriptMap.put("HtmlFrameSetCols",scriptString);
        scriptMap.put("HtmlFrame",scriptString);
        scriptMap.put("HtmlTag",scriptString);
        scriptMap.put("HtmlForm",scriptString);
        scriptMap.put("Bullets",scriptString);
        scriptMap.put("Bullet",scriptString);
        scriptMap.put("HtmlTextArea",scriptString);
        scriptMap.put("HtmlTextField",scriptString);
        scriptMap.put("HtmlSubmitButton",scriptString);
        scriptMap.put("SetHtmlDirectory",scriptString);
        scriptMap.put("HtmlFile",scriptString);
        scriptMap.put("ClearSite",scriptString);
        scriptMap.put("LoadSite",scriptString);
        scriptMap.put("SaveSite",scriptString);
        scriptMap.put("MySQLQuery",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # AntiDeriv(_var, poly_CanBeUni(var) ) <-- NormalForm(AntiDeriv(var,`MakeUni(@poly,@var)));5 # AntiDeriv(_var,UniVariate(_var,_first,_coefs)) <--[ Local(result,i); result:=FlatCopy(coefs); For(i:=1,i<=?Length(result),i++) [ result[i]:= result[i]/(first+i); ]; UniVariate(var,first+1,result);];10 # AntiDeriv(_var,_x + _y) <-- AntiDeriv(var,x) + AntiDeriv(var,y);10 # AntiDeriv(_var,_x - _y) <-- AntiDeriv(var,x) - AntiDeriv(var,y);10 # AntiDeriv(_var, - _y) <-- - AntiDeriv(var,y);10 # AntiDeriv(_var,_x/c_FreeOf?(var) )_(HasExpression?(x,var)) <-- AntiDeriv(var,x)/c;10 # AntiDeriv(_var,c_FreeOf?(var)/_x )_(HasExpression?(x,var) And? c!=? 1) <-- c*AntiDeriv(var,1/x);1570 # IntegrateMultiplicative(_var,(exy_CanBeUni(var)) * _exx,_dummy1,_dummy2) <-- IntByParts(var,exy*exx,AntiDeriv(var,exx));1570 # IntegrateMultiplicative(_var,_exx * (exy_CanBeUni(var)),_dummy1,_dummy2) <-- IntByParts(var,exy*exx,AntiDeriv(var,exx));10 # IntByParts(_var,_exy * _exx,Integrate(_var)(_something)) <-- `Hold(AntiDeriv(@var,((@exy)*(@exx))));20 # IntByParts(_var,_exy * _exx,_anti)_(Not? FreeOf?(anti,exx)) <-- `Hold(AntiDeriv(@var,((@exy)*(@exx))));30 # IntByParts(_var,_exy * _exx,_anti) <-- [ Local(cf); cf:=anti*Deriv(var)exy; exy*anti - `(AntiDeriv(@var,@cf)); ];1570 # IntegrateMultiplicative(_var,(exy_CanBeUni(var)) / (exx_CanBeUni(var)),_dummy1,_dummy2) <-- IntRat(var,exy/exx,MakeUni(exy,var),MakeUni(exx,var));10 # IntRat(_var,_exy / _exx,_exyu,_exxu)_ (Degree(exyu) >? Degree(exxu) Or? Degree(Gcd(exyu,exxu)) >? 0) <-- [ Local(gcd); gcd:=Gcd(exxu,exyu); exyu:=Quotient(exyu,gcd); exxu:=Quotient(exxu,gcd); AntiDeriv(var,NormalForm(Quotient(exyu,exxu))) + AntiDeriv(var,NormalForm(Modulo(exyu,exxu))/NormalForm(exxu)); ];11 # IntRat(_var,_exy / _exx,_exyu,_exxu)_ (Degree(exxu,var) >? 1 And? LeadingCoef(exxu)=?1 And? NumericList?(Coef(exxu,var,0 .. Degree(exxu)))) <--[ Local(ee); ee:=Apart(exy/exx,var); `AntiDeriv(@var,@ee);];20 # IntRat(_var,_exy / _exx,_exyu,_exxu) <-- `Hold(AntiDeriv(@var,((@exy)/(@exx))));30 # AntiDeriv(_var,Deriv(_var)(_expr)) <-- expr;100 # AntiDeriv(_var,_exp) <--[ IntegrateMultiplicative(var,exp,a,b);];10 # IntegrateMultiplicative(_var,if(_cond)(_body),_a,_b) <-- [ body := AntiDeriv(var,body); `Hold(if(@cond)(@body)); ];10 # IntegrateMultiplicative(_var,(_left) else (_right),_a,_b) <-- [ left := AntiDeriv(var,left); right := AntiDeriv(var,right); `Hold( (@left) else (@right) ); ];1600 # IntegrateMultiplicative(_var,_exp,_a,_b) <-- `Hold(Integrate(@var)(@exp));LocalSymbols(intpred)[ intpred := 50; IntFunc(_vr,_from,_to) <-- [ `((@intpred) # IntegrateMultiplicative(_var,@from,_dummy1,_dummy2)_MatchLinear(var,@vr) <-- (@to)/Matcheda()); intpred++; ];];IntPureSquare(_vr,_from,_sign2,_sign0,_to) <--[ `(50 # IntegrateMultiplicative(_var,@from,_dummy1,_dummy2)_MatchPureSquared(var,@sign2,@sign0,@vr) <-- (@to));];IntFunc(x,Sqrt(_x),(2*Sqrt(x)^(3))/3);IntFunc(x,1/Sqrt(_x),2*Sqrt(x));IntFunc(x,1/_x^(_n),x^(1-n)/(1-n) );IntFunc(x,Sin(_x),-Cos(x));IntFunc(x,1/Sin(_x), Ln( 1/Sin(x) - Cos(x)/Sin(x) ) );IntFunc(x,Cos(_x),Sin(x));IntFunc(x,1/Cos(_x),Ln(1/Cos(x)+Tan(x)));IntFunc(x,Tan(_x),-Ln(Cos(x)));IntFunc(x,1/Tan(_x),Ln(Sin(x)) );IntFunc(x,Cos(_x)/Sin(_x),Ln(Sin(x)));IntFunc(x,Exp(_x),Exp(x));IntFunc(x,(C_FreeOf?(var))^(_x),C^x/Ln(C));IntFunc(x,num_FreeOf?(var) / (_x),num*Ln(x));IntFunc(x,Ln(_x),x*Ln(x)-x);IntFunc(x,(_x)*Ln(_x),(1/(1+1))*x^(1+1)*Ln(x) - (1/(1+1)^2)*x^(1+1) );IntFunc(x,Ln(_x)*(_x),(1/(1+1))*x^(1+1)*Ln(x) - (1/(1+1)^2)*x^(1+1) );IntFunc(x,1/Sin(_x)^2,-Cos(x)/Sin(x) );IntFunc(x,1/Cos(_x)^2,Tan(x) );IntFunc(x,1/(Sin(_x)*Tan(_x)),-1/Sin(x));IntFunc(x,Tan(_x)/Cos(_x),1/Cos(x));IntFunc(x,1/Sinh(_x)^2,-1/Tanh(x));IntFunc(x,1/Cosh(_x)^2,Tanh(x));IntFunc(x,1/(Sinh(_x)*Tan(_x)),-1/Sinh(x));IntFunc(x,Tanh(_x)/Cosh(_x),-1/Cosh(x));IntFunc(x,1/Sqrt(m_FreeOf?(x)-_x^2),ArcSin(x/Sqrt(m)) );IntFunc(x,Exp(n_Number?*_x)*Sin(m_Number?*_x),Exp(n*x)*(n*Sin(m*x)- m*Cos(m*x))/(m^2+n^2) );IntFunc(x,Ln(_x)*(_x)^n_Number?,(1/(n+1))*x^(n+1)*Ln(x) - (1/(n+1)^2)*x^(n+1) );IntFunc(x,Ln(A_Number?*_x)*(_x)^n_Number?,(1/(n+1))*x^(n+1)*Ln(A*x) - (1/(n+1)^2)*x^(n+1) );IntFunc(x,Sin(Ln(_x)),x*Sin(Ln(x))/2 - x*Cos(Ln(x))/2 );IntFunc(x,Cos(Ln(_x)),x*Sin(Ln(x))/2 + x*Cos(Ln(x))/2 );IntFunc(x,1/((_x)*Ln(_x)),Ln(Ln(x)));IntFunc(x,(_x)^(-1),Ln(x));IntFunc(x,(_x)^(n_FreeOf?(x)),x^(n+1)/(n+1));IntFunc(x,C_FreeOf?(x)*(_x)^(n_FreeOf?(x)),C*x^(n+1)/(n+1));IntFunc(x,C_FreeOf?(x)/(D_FreeOf?(x)*(_x)^(n_FreeOf?(x))),(C/D)*x^(1-n)/(1-n));IntFunc(x,Sinh(_x),Cosh(x));IntFunc(x,Sinh(_x)^2,Sinh(2*x)/4 - x/2);IntFunc(x,1/Sinh(_x),Ln(Tanh(x/2)));IntFunc(x,Cosh(_x),Sinh(x));IntFunc(x,Cosh(_x)^2,Sinh(2*x)/4 + x/2);IntFunc(x,1/Cosh(_x),ArcTan(Sinh(x)));IntFunc(x,Tanh(_x),Ln(Cosh(x)));IntFunc(x,Tanh(_x)/Cosh(_x),-1/Cosh(x));IntFunc(x,1/Cosh(_x)^2,Tanh(x));IntFunc(x,1/Tanh(_x),Ln(Sinh(x)));IntFunc(x,Abs(_x),Abs(x)*x/2); IntFunc(x,ArcTan(_x),x*ArcTan(x) - Ln(x^2 + 1)/2);IntFunc(x,ArcCos(_x),x*ArcCos(x) - Sqrt(1-x^2) );IntFunc(x,ArcTanh(_x),x*ArcTanh(x) + Ln(1-x^2)/2 );IntFunc(x,ArcSinh(_x),x*ArcSinh(x) - Sqrt(x^2 + 1) );IntFunc(x,ArcCosh(_x),x*ArcCosh(x) - Sqrt(x-1)*Sqrt(x+1) );IntFunc(x,num_FreeOf?(var)/(A_Number? + B_Number?*(_x))^2,-num/(A*b + B^2*x));IntFunc(x,num_FreeOf?(var)/(n_Number? + m_Number?*Exp(p_Number?*(_x))),num*x/n - num*Ln(n + m*Exp(p*x))/(n*p));IntFunc(x,num_FreeOf?(var)/(m_Number?*Exp(p_Number?*(_x)) + n_Number?),num*x/n - num*Ln(n + m*Exp(p*x))/(n*p));IntPureSquare(x,num_FreeOf?(var)/(_x),1,1,(num/(Sqrt(Matchedb()*Matcheda())))*ArcTan(var/Sqrt(Matchedb()/Matcheda())));IntFunc(x,Erf(_x), x*Erf(x)+ 1/(Exp(x^2)*Sqrt(Pi)) );UnFence(\"IntegrateMultiplicative\",4);";
        scriptMap.put("AntiDeriv",scriptString);
        scriptMap.put("IntegrateMultiplicative",scriptString);
        scriptMap.put("IntByParts",scriptString);
        scriptMap.put("IntRat",scriptString);
        scriptMap.put("IntPureSquare",scriptString);
        scriptMap.put("IntFunc",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LoadLibraryFunction(\"AntiDeriv\");10 # (Integrate(_var)(expr_List?)) <-- Map(\"Integrate\",{FillList(var,Length(expr)),expr});20 # (Integrate(_var)(_expr)) <-- IntSub(var,expr,AntiDeriv(var,IntClean(var,expr)));20 # (Integrate(_var, optionsList_List?)(_expr)) <--[ Local(result); optionsList := OptionsToAssociativeList(optionsList); result := Integrate(var) expr; If( optionsList[\"logAbs\"] =? \"True\", result := ( result /: {Ln(_x) <- Ln(Abs(x))}) ); result;];10 # IntSub(_var,_expr,Integrate(_var)(_expr2)) <-- `Hold(Integrate(@var)(@expr));20 # IntSub(_var,_expr,_result) <-- result; 10# (Integrate(_var,_from,_to)(expr_List?)) <-- Map(\"Integrate\",{FillList(var,Length(expr)), FillList(from,Length(expr)), FillList(to,Length(expr)), expr});20 # (Integrate(_var,_from,_to)(_expr)) <-- defIntegrate(var,from,to,expr,a,b);20 # (Integrate(_var,_from,_to,optionsList_List?)(_expr)) <--[ Local(result); optionsList := OptionsToAssociativeList(optionsList); result := Integrate(var,from,to) expr; If( optionsList[\"logAbs\"] =? \"True\", result := ( result /: {Ln(_x) <- Ln(Abs(x))}) ); result;];10 # defIntegrate(_var,_from,_to,_expr,_a,_b)_(from =? -to And? OddFunction?(expr,var)) <-- 0;10 # defIntegrate(_var,_from,_from,_expr,_a,_b) <-- 0;12 # defIntegrate(_var,_from,_to,_expr,_a,_b)_(from =? -to And? EvenFunction?(expr,var)) <-- 2*defIntegrate(var,0,to,expr,a,b);100 # defIntegrate(_var,_from,_to,_expr,_a,_b)_(Type(AntiDeriv(var,IntClean(var,expr))) !=? \"AntiDeriv\") <-- IntegrateRange(var,expr,from,to,AntiDeriv(var,IntClean(var,expr)));101 # defIntegrate(_var,_from,_to,_expr,_a,_b) <-- `Hold(Integrate(@var,@from,@to)(@expr));10 # IntegrateRange(_var,_expr,_from,_to,Integrate(_var)_expr2) <-- `Hold(Integrate(@var,@from,@to)@expr);20 # IntegrateRange(_var,_expr,_from,_to,_antideriv) <-- `(@antideriv Where @var == @to) - `(@antideriv Where @var == @from);10 # IntClean(_var,_expr) <--[ if( FreeOf?(var,expr) Or? SumOfTerms?(var,expr) )[ expr; ] else if ( HasFunction?(expr,Sin) Or? HasFunction?(expr,Cos) )[ Simplify(TrigSimpCombine(expr)); ] else [ Simplify(expr); ];];";
        scriptMap.put("Integrate",scriptString);
        scriptMap.put("IntSub",scriptString);
        scriptMap.put("defIntegrate",scriptString);
        scriptMap.put("IntegrateRange",scriptString);
        scriptMap.put("IntClean",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(a,b)[10 # MatchLinear(var_Atom?,expr_CanBeUni(var)) <--[ Bind(expr,MakeUni(expr,var)); MatchLinear(expr);];20 # MatchLinear(_var,_expr) <-- False;10 # MatchLinear(_expr)_(Degree(expr,var)<?2) <--[ Check(UniVar?(expr), \"Argument\", PipeToString()Echo({\"Incorrect argument \",expr,\" passed to MatchLinear\"})); a := Coef(expr,1); b := Coef(expr,0); True;];20 # MatchLinear(_expr) <-- False;UnFence(\"MatchLinear\",1);UnFence(\"MatchLinear\",2);10 # MatchPureSquared(var_Atom?,_sign2,_sign0,expr_CanBeUni(var)) <--[ Bind(expr,MakeUni(expr,var)); MatchPureSquared(expr,sign2,sign0);];20 # MatchPureSquared(_var,_sign2,_sign0,_expr) <-- False;10 # MatchPureSquared(_expr,_sign2,_sign0)_(Degree(expr,var)=?2 And? Coef(expr,1) =? 0 And? Number?(Coef(expr,0)) And? Number?(Coef(expr,2)) And? Coef(expr,0)*sign0 >? 0 And? Coef(expr,2)*sign2 >? 0 ) <--[ Check(UniVar?(expr), \"Argument\", PipeToString()Echo({\"Incorrect argument \",expr,\" passed to MatchLinear\"})); a := Coef(expr,2); b := Coef(expr,0); True;];20 # MatchPureSquared(_expr,_sign2,_sign0) <-- False;UnFence(\"MatchPureSquared\",3);UnFence(\"MatchPureSquared\",4);Matcheda() := a;Matchedb() := b;]; ";
        scriptMap.put("MatchLinear",scriptString);
        scriptMap.put("MatchPureSquared",scriptString);
        scriptMap.put("Matcheda",scriptString);
        scriptMap.put("Matchedb",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "(Assert(_errorclass, _errorobject) _predicate) <--[ CheckErrorTableau(); If(Equal?(predicate, True),  True, [  DestructiveAppend(GetErrorTableau(), {errorclass, errorobject}); False; ] );];(Assert(_errorclass) _predicate) <-- Assert(errorclass, True) predicate;(Assert() _predicate) <-- Assert(\"generic\", True) predicate;";
        scriptMap.put("Assert",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"DefaultPrint\", {x})[ DumpErrors(); WriteString(\"Result: \"); Write(x); WriteString(\";\");];HoldArgument(\"DefaultPrint\", x);";
        scriptMap.put("DefaultPrint",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "DumpErrors() <--[ Local(errorobject, errorword); CheckErrorTableau(); ForEach(errorobject, GetErrorTableau()) [  If( List?(errorobject), [ If(  Length(errorobject) >? 0 And? errorobject[1] =? \"warning\", [ errorword := \"Warning\"; errorobject[1] := \"\";  ], errorword := \"Error: \"  ); If(  Length(errorobject)=?2 And? errorobject[2]=?True, Echo(errorword, errorobject[1]), [ Echo(errorword, errorobject[1], \": \", PrintList(Rest(errorobject))); ] ); ],  Echo(\"Error: \", errorobject) ); ]; ClearErrors();];";
        scriptMap.put("DumpErrors",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # EchoInternal(string_String?) <--[ WriteString(string);];20 # EchoInternal(_item) <--[ Write(item);Space();];RulebaseListedHoldArguments(\"Echo\",{firstParameter, parametersList});5 # Echo(_firstParameter) <-- Echo(firstParameter, {});10 # Echo(_firstParameter, parametersList_List?) <--[ EchoInternal(firstParameter); ForEach(item,parametersList) EchoInternal(item); NewLine(); ];20 # Echo(_firstParameter, _secondParameter) <-- Echo(firstParameter, {secondParameter});Echo() := NewLine();";
        scriptMap.put("Echo",scriptString);
        scriptMap.put("EchoInternal",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Error?() <--[ CheckErrorTableau(); Length(GetErrorTableau())>?0;];Error?(errorclass_String?) <--[ CheckErrorTableau(); GetErrorTableau()[errorclass] !=? Empty;];";
        scriptMap.put("Error?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(ErrorTableau) [  Bind(ErrorTableau, {}); GetErrorTableau() := ErrorTableau; ClearErrors() <-- Bind(ErrorTableau, {});  CheckErrorTableau() <-- If( Not? List?(ErrorTableau), Bind(ErrorTableau, {{\"general\", \"corrupted ErrorTableau\"}}) );]; GetError(errorclass_String?) <--[ Local(error); error := GetErrorTableau()[errorclass]; If( error !=? Empty, error, False );];ClearError(errorclass_String?) <-- AssocDelete(GetErrorTableau(), errorclass);";
        scriptMap.put("GetErrorTableau",scriptString);
        scriptMap.put("ClearError",scriptString);
        scriptMap.put("ClearErrors",scriptString);
        scriptMap.put("GetError",scriptString);
        scriptMap.put("CheckErrorTableau",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Print(_x) <--[ Print(x,60000); NewLine(); DumpErrors();];10 # Print(x_Atom?,_n) <-- Write(x);10 # Print(_x,_n)_(Infix?(Type(x))And? ArgumentsCount(x) =? 2) <--[ Local(bracket); bracket:= (PrecedenceGet(Type(x)) >? n); If(bracket,WriteString(\"(\")); Print(x[1],LeftPrecedenceGet(Type(x))); Write(x[0]); Print(x[2],RightPrecedenceGet(Type(x))); If(bracket,WriteString(\")\"));];10 # Print(_x,_n)_(Prefix?(Type(x)) And? ArgumentsCount(x) =? 1) <--[ Local(bracket); bracket:= (PrecedenceGet(Type(x)) >? n); Write(x[0]); If(bracket,WriteString(\"(\")); Print(x[1],RightPrecedenceGet(Type(x))); If(bracket,WriteString(\")\"));];10 # Print(_x,_n)_(Postfix?(Type(x))And? ArgumentsCount(x) =? 1) <--[ Local(bracket); bracket:= (PrecedenceGet(Type(x)) >? n); If(bracket,WriteString(\"(\")); Print(x[1],LeftPrecedenceGet(Type(x))); Write(x[0]); If(bracket,WriteString(\")\"));];20 # Print(_x,_n)_(Type(x) =? \"List\") <--[ WriteString(\"{\"); PrintArg(x); WriteString(\"}\");];20 # Print(_x,_n)_(Type(x) =? \"Prog\") <--[ WriteString(\"[\"); PrintArgProg(Rest(FunctionToList(x))); WriteString(\"]\");];20 # Print(_x,_n)_(Type(x) =? \"Nth\") <--[ Print(x[1],0); WriteString(\"[\"); Print(x[2],60000); WriteString(\"]\");];100 # Print(x_Function?,_n) <-- [ Write(x[0]); WriteString(\"(\"); PrintArg(Rest(FunctionToList(x))); WriteString(\")\"); ];10 # PrintArg({}) <-- True;20 # PrintArg(_list) <--[ Print(First(list),60000); PrintArgComma(Rest(list));];10 # PrintArgComma({}) <-- True;20 # PrintArgComma(_list) <--[ WriteString(\",\"); Print(First(list),60000); PrintArgComma(Rest(list));];18 # Print(Complex(0,1),_n) <-- [WriteString(\"I\");];19 # Print(Complex(0,_y),_n) <-- [WriteString(\"I*\");Print(y,4);];19 # Print(Complex(_x,1),_n) <-- [Print(x,7);WriteString(\"+I\");];20 # Print(Complex(_x,_y),_n) <-- [Print(x,7);WriteString(\"+I*\");Print(y,4);];10 # PrintArgProg({}) <-- True;20 # PrintArgProg(_list) <--[ Print(First(list),60000); WriteString(\";\"); PrintArgProg(Rest(list));];";
        scriptMap.put("Print",scriptString);
        scriptMap.put("PrintArg",scriptString);
        scriptMap.put("PrintArgComma",scriptString);
        scriptMap.put("PrintArgProg",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Macro(\"Show\",{id}) [SysOut(\"<< \",@id,\" >>\");];Macro(\"Show\",{id,x}) [SysOut(\"<< \",@id,\" >> \",Hold(@x),\": \",Eval(@x));];";
        scriptMap.put("Show",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"TableForm\",{list})[ Local(i); ForEach(i,list) [ Write(i); NewLine(); ]; True;];";
        scriptMap.put("TableForm",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Macro(\"Tell\",{id}) [Echo(<<,@id,>>);];Macro(\"Tell\",{id,x}) [Echo(<<,@id,>>,Hold(@x),\": \",Eval(@x));];";
        scriptMap.put("Tell",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "100 # IsIrrationalFunction(Sqrt(_expr), _var)_(Polynomial?(expr, var) And? Degree(expr, var) >? 0 Or? IsIrrationalFunction(expr,var)) <-- True;100 # IsIrrationalFunction(_expr^_p, _var)_((Polynomial?(expr, var) Or? IsIrrationalFunction(expr,var)) And? RationalOrNumber?(p) And? Not? Zero?(p) And? Not? PositiveInteger?(p)) <-- True;100 # IsIrrationalFunction(_e1 + _e2, _var)_(IsIrrationalFunction(e1, var) And? IsIrrationalFunction(e2, var) Or? Polynomial?(e1, var) And? IsIrrationalFunction(e2, var) Or? IsIrrationalFunction(e1, var) And? Polynomial?(e2, var)) <-- True;100 # IsIrrationalFunction(_e1 - _e2, _var)_(IsIrrationalFunction(e1, var) And? IsIrrationalFunction(e2, var) Or? Polynomial?(e1, var) And? IsIrrationalFunction(e2, var) Or? IsIrrationalFunction(e1, var) And? Polynomial?(e2, var)) <-- True;100 # IsIrrationalFunction(_e1 * _e2, _var)_(IsIrrationalFunction(e1, var) And? IsIrrationalFunction(e2, var) Or? Polynomial?(e1, var) And? IsIrrationalFunction(e2, var) Or? IsIrrationalFunction(e1, var) And? Polynomial?(e2, var)) <-- True;100 # IsIrrationalFunction(_e1 / _e2, _var)_(IsIrrationalFunction(e1, var) And? IsIrrationalFunction(e2, var) Or? Polynomial?(e1, var) And? IsIrrationalFunction(e2, var) Or? IsIrrationalFunction(e1, var) And? Polynomial?(e2, var)) <-- True;500 # IsIrrationalFunction(_expr, _var) <-- False;100 # IrrationalFunctionDegree(Sqrt(_expr), _var)_(Polynomial?(expr, var)) <-- Degree(expr, var) / 2;105 # IrrationalFunctionDegree(Sqrt(_expr), _var)_(IsIrrationalFunction(expr, var)) <-- IrrationalFunctionDegree(expr, var) / 2;110 # IrrationalFunctionDegree(_expr^_p, _var)_(Polynomial?(expr, var) And? RationalOrNumber?(p) And? Not? Zero?(p) And? Not? PositiveInteger?(p)) <-- Degree(expr, var) * p;110 # IrrationalFunctionDegree(_e1 - _e2, _var)_(IsIrrationalFunction(e1,var) And? IsIrrationalFunction(e2,var)) <-- Maximum(IrrationalFunctionDegree(e1,var), IrrationalFunctionDegree(e2,var));110 # IrrationalFunctionDegree(_e1 - _e2, _var)_(IsIrrationalFunction(e1,var) And? Polynomial?(e2,var)) <-- Maximum(IrrationalFunctionDegree(e1,var), Degree(e2, var));110 # IrrationalFunctionDegree(_e1 - _e2, _var)_(Polynomial?(e1,var) And? IsIrrationalFunction(e2,var)) <-- Maximum(Degree(e1, var), IrrationalFunctionDegree(e2,var));110 # IrrationalFunctionDegree(_e1 + _e2, _var)_(IsIrrationalFunction(e1,var) And? IsIrrationalFunction(e2,var)) <-- Maximum(IrrationalFunctionDegree(e1,var), IrrationalFunctionDegree(e2,var));110 # IrrationalFunctionDegree(_e1 + _e2, _var)_(IsIrrationalFunction(e1,var) And? Polynomial?(e2,var)) <-- Maximum(IrrationalFunctionDegree(e1,var), Degree(e2, var));110 # IrrationalFunctionDegree(_e1 + _e2, _var)_(Polynomial?(e1,var) And? IsIrrationalFunction(e2,var)) <-- Maximum(Degree(e1, var), IrrationalFunctionDegree(e2,var));110 # IrrationalFunctionDegree(_e1 * _e2, _var)_(IsIrrationalFunction(e1,var) And? IsIrrationalFunction(e2,var)) <-- IrrationalFunctionDegree(e1,var) + IrrationalFunctionDegree(e2,var);110 # IrrationalFunctionDegree(_e1 * _e2, _var)_(IsIrrationalFunction(e1,var) And? Polynomial?(e2,var)) <-- IrrationalFunctionDegree(e1,var) + Degree(e2, var);110 # IrrationalFunctionDegree(_e1 * _e2, _var)_(Polynomial?(e1,var) And? IsIrrationalFunction(e2,var)) <-- Degree(e1, var) + IrrationalFunctionDegree(e2,var);110 # IrrationalFunctionDegree(_e1 / _e2, _var)_(IsIrrationalFunction(e1,var) And? IsIrrationalFunction(e2,var)) <-- IrrationalFunctionDegree(e1,var) - IrrationalFunctionDegree(e2,var);110 # IrrationalFunctionDegree(_e1 / _e2, _var)_(IsIrrationalFunction(e1,var) And? Polynomial?(e2,var)) <-- IrrationalFunctionDegree(e1,var) - Degree(e2, var);110 # IrrationalFunctionDegree(_e1 / _e2, _var)_(Polynomial?(e1,var) And? IsIrrationalFunction(e2,var)) <-- Degree(e1, var) - IrrationalFunctionDegree(e2,var);100 # IrrationalFunctionLeadingCoef(Sqrt(_expr), _var)_(Polynomial?(expr, var)) <-- Sqrt(LeadingCoef(expr, var));105 # IrrationalFunctionLeadingCoef(Sqrt(_expr), _var)_(IsIrrationalFunction(expr, var)) <-- Sqrt(IrrationalFunctionLeadingCoef(expr, var));110 # IrrationalFunctionLeadingCoef(_expr^_p, _var)_(Polynomial?(expr, var) And? RationalOrNumber?(p) And? Not? Zero?(p) And? Not? PositiveInteger?(p)) <-- LeadingCoef(expr, var)^p;110 # IrrationalFunctionLeadingCoef(_e1 + _e2, _var)_(IsIrrationalFunction(e1,var) And? IsIrrationalFunction(e2,var) And? IrrationalFunctionDegree(e1,var) >? IrrationalFunctionDegree(e2,var)) <-- IrrationalFunctionLeadingCoef(e1,var);110 # IrrationalFunctionLeadingCoef(_e1 + _e2, _var)_(IsIrrationalFunction(e1,var) And? IsIrrationalFunction(e2,var) And? IrrationalFunctionDegree(e1,var) <? IrrationalFunctionDegree(e2,var)) <-- IrrationalFunctionLeadingCoef(e2,var);110 # IrrationalFunctionLeadingCoef(_e1 + _e2, _var)_(IsIrrationalFunction(e1,var) And? IsIrrationalFunction(e2,var) And? IrrationalFunctionDegree(e1,var) =? IrrationalFunctionDegree(e2,var)) <-- IrrationalFunctionLeadingCoef(e1,var) + IrrationalFunctionLeadingCoef(e2,var);110 # IrrationalFunctionLeadingCoef(_e1 + _e2, _var)_(IsIrrationalFunction(e1,var) And? Polynomial?(e2,var) And? IrrationalFunctionDegree(e1,var) >? Degree(e2,var)) <-- IrrationalFunctionLeadingCoef(e1,var);110 # IrrationalFunctionLeadingCoef(_e1 + _e2, _var)_(IsIrrationalFunction(e1,var) And? Polynomial?(e2,var) And? IrrationalFunctionDegree(e1,var) <? Degree(e2,var)) <-- LeadingCoef(e2,var);110 # IrrationalFunctionLeadingCoef(_e1 + _e2, _var)_(IsIrrationalFunction(e1,var) And? Polynomial?(e2,var) And? IrrationalFunctionDegree(e1,var) =? Degree(e2,var)) <-- IrrationalFunctionLeadingCoef(e1,var) + LeadingCoef(e2,var);110 # IrrationalFunctionLeadingCoef(_e1 + _e2, _var)_(Polynomial?(e1,var) And? IsIrrationalFunction(e2,var) And? Degree(e1,var) >? IrrationalFunctionDegree(e2,var)) <-- LeadingCoef(e1,var);110 # IrrationalFunctionLeadingCoef(_e1 + _e2, _var)_(Polynomial?(e1,var) And? IsIrrationalFunction(e2,var) And? Degree(e1,var) <? IrrationalFunctionDegree(e2,var)) <-- IrrationalFunctionLeadingCoef(e2,var);110 # IrrationalFunctionLeadingCoef(_e1 + _e2, _var)_(IsPolynomiaml(e1,var) And? IsIrrationalFunction(e2,var) And? Degree(e1,var) =? IrrationalFunctionDegree(e2,var)) <-- LeadingCoef(e1,var) + IrrationalFunctionLeadingCoef(e2,var);110 # IrrationalFunctionLeadingCoef(_e1 - _e2, _var)_(IsIrrationalFunction(e1,var) And? IsIrrationalFunction(e2,var) And? IrrationalFunctionDegree(e1,var) >? IrrationalFunctionDegree(e2,var)) <-- IrrationalFunctionLeadingCoef(e1,var);110 # IrrationalFunctionLeadingCoef(_e1 - _e2, _var)_(IsIrrationalFunction(e1,var) And? IsIrrationalFunction(e2,var) And? IrrationalFunctionDegree(e1,var) <? IrrationalFunctionDegree(e2,var)) <-- -IrrationalFunctionLeadingCoef(e2,var);110 # IrrationalFunctionLeadingCoef(_e1 - _e2, _var)_(IsIrrationalFunction(e1,var) And? IsIrrationalFunction(e2,var) And? IrrationalFunctionDegree(e1,var) =? IrrationalFunctionDegree(e2,var)) <-- IrrationalFunctionLeadingCoef(e1,var) - IrrationalFunctionLeadingCoef(e2,var);110 # IrrationalFunctionLeadingCoef(_e1 - _e2, _var)_(IsIrrationalFunction(e1,var) And? Polynomial?(e2,var) And? IrrationalFunctionDegree(e1,var) >? Degree(e2,var)) <-- IrrationalFunctionLeadingCoef(e1,var);110 # IrrationalFunctionLeadingCoef(_e1 - _e2, _var)_(IsIrrationalFunction(e1,var) And? Polynomial?(e2,var) And? IrrationalFunctionDegree(e1,var) <? Degree(e2,var)) <-- -LeadingCoef(e2,var);110 # IrrationalFunctionLeadingCoef(_e1 - _e2, _var)_(IsIrrationalFunction(e1,var) And? Polynomial?(e2,var) And? IrrationalFunctionDegree(e1,var) =? Degree(e2,var)) <-- IrrationalFunctionLeadingCoef(e1,var) - LeadingCoef(e2,var);110 # IrrationalFunctionLeadingCoef(_e1 - _e2, _var)_(Polynomial?(e1,var) And? IsIrrationalFunction(e2,var) And? Degree(e1,var) >? IrrationalFunctionDegree(e2,var)) <-- LeadingCoef(e1,var);110 # IrrationalFunctionLeadingCoef(_e1 - _e2, _var)_(Polynomial?(e1,var) And? IsIrrationalFunction(e2,var) And? Degree(e1,var) <? IrrationalFunctionDegree(e2,var)) <-- -IrrationalFunctionLeadingCoef(e2,var);110 # IrrationalFunctionLeadingCoef(_e1 - _e2, _var)_(IsPolynomiaml(e1,var) And? IsIrrationalFunction(e2,var) And? Degree(e1,var) =? IrrationalFunctionDegree(e2,var)) <-- LeadingCoef(e1,var) - IrrationalFunctionLeadingCoef(e2,var);110 # IrrationalFunctionLeadingCoef(_e1 * _e2, _var)_(IsIrrationalFunction(e1,var) And? IsIrrationalFunction(e2,var)) <-- IrrationalFunctionLeadingCoef(e1,var) * IrrationalFunctionLeadingCoef(e2,var);110 # IrrationalFunctionLeadingCoef(_e1 * _e2, _var)_(IsIrrationalFunction(e1,var) And? Polynomial?(e2,var)) <-- IrrationalFunctionLeadingCoef(e1,var) * LeadingCoef(e2, var);110 # IrrationalFunctionLeadingCoef(_e1 * _e2, _var)_(Polynomial?(e1,var) And? IsIrrationalFunction(e2,var)) <-- LeadingCoef(e1, var) * IrrationalFunctionLeadingCoef(e2,var);110 # IrrationalFunctionLeadingCoef(_e1 / _e2, _var)_(IsIrrationalFunction(e1,var) And? IsIrrationalFunction(e2,var)) <-- IrrationalFunctionLeadingCoef(e1,var) / IrrationalFunctionLeadingCoef(e2,var);110 # IrrationalFunctionLeadingCoef(_e1 / _e2, _var)_(IsIrrationalFunction(e1,var) And? Polynomial?(e2,var)) <-- IrrationalFunctionLeadingCoef(e1,var) / LeadingCoef(e2, var);110 # IrrationalFunctionLeadingCoef(_e1 / _e2, _var)_(Polynomial?(e1,var) And? IsIrrationalFunction(e2,var)) <-- LeadingCoef(e1, var) / IrrationalFunctionLeadingCoef(e2,var);100 # Lim(_var, _tar, _dir, _p)_(Polynomial?(p, var) And? Degree(p, var) >? 0 And? Infinity?(tar)) <-- LeadingCoef(p,var) * Sign(tar)^Degree(p,var) * Infinity;110 # Lim(_var, _tar, _dir, _r)_(RationalFunction?(r, var) And? Infinity?(tar)) <-- [ Local(p,q,pd,qd,pc,qc); p:=Numerator(r); q:=Denominator(r); pd:=Degree(p,var); qd:=Degree(q,var); pc:=LeadingCoef(p,var); qc:=LeadingCoef(q,var); If(pd>?qd, pc/qc*tar, If(pd=?qd,pc/qc,0) );];110 # Lim(_var, _tar, _dir, _expr)_(IsIrrationalFunction(expr, var) And? Infinity?(tar)) <-- [ Local(lc,dg); lc:=IrrationalFunctionLeadingCoef(expr, var); dg:=IrrationalFunctionDegree(expr, var); If(lc =? 0, 0, If(dg >? 0, Sign(tar)^dg * Infinity, If(dg =? 0, lc, 0) ) );];120 # Lim(_var, _tar, _dir, Ln(_a) + Ln(_b)) <-- Lim(var, tar, dir, Ln(a*b));120 # Lim(_var, _tar, _dir, Ln(_a) - Ln(_b)) <-- Lim(var, tar, dir, Ln(a/b));200 # Lim(_var, _tar, _dir, _x ^ _y)_( [ Local(lx,ly); lx := Lim(var, tar, dir, x); ly := Lim(var, tar, dir, y); ((Zero?(lx) And? Zero?(ly)) Or? ((lx =? 1) And? Infinity?(ly)) Or? (Infinity?(lx) And? Zero?(ly)));] )<-- Exp(Lim(var, tar, dir, y * Ln(x)));210 # Lim(_var, _tar, _dir, _x ^ _y)<-- Lim(var, tar, dir, x)^Lim(var, tar, dir, y);300 # Lim(_var, _tar, _dir, _x / _y)_( [ Local(lx,ly,infx,infy); lx := Lim(var, tar, dir, x); ly := Lim(var, tar, dir, y); infx := (Infinity?(lx) Or? (Zero?(Re(lx)) And? Infinity?(Im(lx)))); infy := (Infinity?(ly) Or? (Zero?(Re(ly)) And? Infinity?(Im(ly)))); ((Zero?(lx) And? Zero?(ly)) Or? (infx And? infy) );] )<-- Lim(var, tar, dir, ApplyFast(\"Differentiate\", {var, x})/ApplyFast(\"Differentiate\", {var, y}));Dir(Right) <-- 1;Dir(Left) <-- -1;Sign(_var, _tar, _dir, _exp, _n)<-- [ Local(der, coef); der := ApplyFast(\"Differentiate\", {var, exp}); coef := Eval(ApplyFast(\"Subst\", {var, tar, der})); If ( coef =? 0, Sign(var, tar, dir, der, n+1), (Sign(coef)*Dir(dir)) ^ n );];310 # Lim(_var, _tar, _dir, _x / _y)_(Infinity?(tar) And? Zero?(Lim(var, tar, dir, y)))<-- Sign(Lim(var, tar, dir, x))*Sign(Lim(var, tar, dir, ApplyFast(\"Differentiate\", {var, y})))*tar;320 # Lim(_var, _tar, _dir, _x / _y)_Zero?(Lim(var, tar, dir, y))<-- Sign(Lim(var, tar, dir, x))*Sign(var, tar, dir, y, 1)*Infinity;330 # Lim(_var, _tar, _dir, _x / _y) <-- [ Local(u,v,r); u := Lim(var, tar, dir, x); v := Lim(var, tar, dir, y); r := u / v; If (u =? Undefined And? Infinity?(v), [ Local(li, ls); li := LimInf(var,tar,dir,x);  ls := LimSup(var,tar,dir,x); r := (li * ls) / v; ]); r;];400 # Lim(_var, _tar, _dir, _x * Exp(_y))_(Infinity?(Lim(var, tar, dir, x)) And? (Lim(var, tar, dir, y) =? -Infinity))<-- Lim(var, tar, dir, x/Exp(-y));400 # Lim(_var, _tar, _dir, Exp(_x) * _y)_((Lim(var, tar, dir, x) =? -Infinity) And? Infinity?(Lim(var, tar, dir, y)))<-- Lim(var, tar, dir, y/Exp(-x));400 # Lim(_var, _tar, _dir, Ln(_x) * _y)_(Zero?(Lim(var, tar, dir, x)) And? Zero?(Lim(var, tar, dir, y)))<-- Lim(var, tar, dir, y*Ln(x));410 # Lim(_var, _tar, _dir, _x * _y)_((Zero?(Lim(var, tar, dir, x)) And? Infinity?(Lim(var, tar, dir, y))) Or? (Infinity?(Lim(var, tar, dir, x)) And? Zero?(Lim(var, tar, dir, y))))<-- Lim(var, tar, dir, Simplify(ApplyFast(\"Differentiate\", {var, y})/ApplyFast(\"Differentiate\",{var, 1/x})));420 # Lim(_var, _tar, _dir, _x * _y) <-- [ Local(u,v,r); u := Lim(var, tar, dir, x); v := Lim(var, tar, dir, y); r := u * v; If (u =? 0 And? v =? Undefined, [ li := LimInf(var,tar,dir,y);  ls := LimSup(var,tar,dir,y); r := u * li * ls; ], If (u =? Undefined And? v =? 0, [ li := LimInf(var,tar,dir,x);  ls := LimSup(var,tar,dir,x); r := v * li * ls; ])); r;];500 # Lim(_var, _tar, _dir, _x - _y)_( [ Local(lx,ly); lx := Lim(var, tar, dir, x); ly := Lim(var, tar, dir, y); ((lx =? Infinity) And? (ly =? Infinity)) Or? ((lx =? -Infinity) And? (ly =? -Infinity));] )<-- Lim(var, tar, dir, x*(1-y/x));510 # Lim(_var, _tar, _dir, _x - _y)<-- Lim(var, tar, dir, x)-Lim(var, tar, dir, y);520 # Lim(_var, _tar, _dir, - _x)<-- - Lim(var, tar, dir, x);600 # Lim(_var, _tar, _dir, _x + _y)_( [ Local(lx,ly); lx := Lim(var, tar, dir, x); ly := Lim(var, tar, dir, y); ((lx =? Infinity) And? (ly =? -Infinity)) Or? ((lx =? -Infinity) And? (ly =? Infinity));] )<-- Lim(var, tar, dir, x*(1+y/x));603 # Lim(_var, _tar, _dir, _x + _y)_( Lim(var, tar, dir, x) =? Infinity And? Lim(var, tar, dir, y) =? Undefined And? LimInf(var, tar, dir, y) !=? -Infinity Or? Lim(var, tar, dir, x) =? Undefined And? LimInf(var, tar, dir, x) !=? -Infinity And? Lim(var, tar, dir, y) =? Infinity) <-- Infinity;610 # Lim(_var, _tar, _dir, _x + _y)<-- Lim(var, tar, dir, x)+Lim(var, tar, dir, y);700 # Lim(_var, _tar, _dir, exp_Function?)<-- Eval(MapArgs(exp,\"LimitArgs\"));LimitArgs(_arg) <-- Lim(var,tar,dir,arg);UnFence(\"LimitArgs\",1); 701 # Lim(_var, _tar, _dir, _exp)<-- Eval(ApplyFast(\"Subst\", {var, tar, exp}));10 # Lim(_var, tar_Infinity?, _exp) <-- Lim(var, tar, None, exp);20 # Lim(_var, _tar, _exp)<-- [ Local(l); l := Lim(var, tar, Left, exp); If ( l =? Lim(var, tar, Right, exp), l, Undefined );];100 # LimInf(_var, _tar, _dir, Cos( _exp ))_Infinity?(Lim(var,tar,dir,exp)) <-- -1;100 # LimInf(_var, _tar, _dir, Sin( _exp ))_Infinity?(Lim(var,tar,dir,exp)) <-- -1;500 # LimInf(_var, _tar, _dir, _exp) <-- Lim(var,tar,dir,exp);100 # LimSup(_var, _tar, _dir, Cos( _exp ))_Infinity?(Lim(var,tar,dir,exp)) <-- 1;100 # LimSup(_var, _tar, _dir, Sin( _exp ))_Infinity?(Lim(var,tar,dir,exp)) <-- 1;500 # LimSup(_var, _tar, _dir, _exp) <-- Lim(var,tar,dir,exp);(Limit(_var,_lim)(_fie)) <-- Lim(var,lim,fie);(Limit(_var,_lim,_direction)(_fie)) <-- Lim(var,lim,direction,fie);UnFence(\"Limit\",3);";
        scriptMap.put("Limit",scriptString);
        scriptMap.put("IsIrrationalFunction",scriptString);
        scriptMap.put("IrrationalFunctionDegree",scriptString);
        scriptMap.put("IrrationalFunctionLeadingCoef",scriptString);
        scriptMap.put("Dir",scriptString);
        scriptMap.put("LimitArgs",scriptString);
        scriptMap.put("LimInf",scriptString);
        scriptMap.put("LimSup",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"BaseVector\",{row,n})[ Local(i,result); result:=ZeroVector(n); result[row] := 1; result;];";
        scriptMap.put("BaseVector",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CartesianProduct(xList_List?, yList_List?) <--[ Local(cartesianProduct);  cartesianProduct := {};  ForEach(x, xList) [ ForEach(y, yList) [ cartesianProduct := DestructiveAppend(cartesianProduct, {x,y});  ]; ]; cartesianProduct;];";
        scriptMap.put("CartesianProduct",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Cholesky(A_Matrix?) <--[ Local(matrix,n,k,j); n:=Length(A); matrix:=ZeroMatrix(n);  ForEach(i,1 .. n) ForEach(j,1 .. n) matrix[i][j] := A[i][j];  ForEach(i,1 .. n)[ For(k:=1,k<=?(i-1),k++) matrix[i][i] := matrix[i][i] - matrix[k][i]^2; Check( matrix[i][i] >? 0, \"Math\", \"Cholesky: Matrix is not positive definite\"); matrix[i][i] := Sqrt(matrix[i][i]);  For(j:=i+1,j<=?n,j++)[ For(k:=1,k<=?(i-1),k++) matrix[i][j]:= matrix[i][j] - matrix[k][i]*matrix[k][j]; matrix[i][j] := matrix[i][j]/matrix[i][i];  ]; ];  ForEach(i,1 .. n) ForEach(j,1 .. n) If(i>?j,matrix[i][j] := 0); matrix;];";
        scriptMap.put("Cholesky",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"CoFactor\",{matrix,ii,jj})[ Local(perms,indices,result); indices:=Table(i,i,1,Length(matrix),1); perms:=PermutationsList(indices); result:=0; ForEach(item,perms) If(item[ii] =? jj, result:=result+ Product(i,1,Length(matrix), If(ii=?i,1,matrix[i][item[i] ]) )*LeviCivita(item)); result;];";
        scriptMap.put("CoFactor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"CrossProduct\",{aLeft,aRight})[ Local(length); length:=Length(aLeft); Check(length =? 3, \"Argument\", \"OutProduct: error, vectors not of dimension 3\"); Check(length =? Length(aRight), \"Argument\", \"OutProduct: error, vectors not of the same dimension\"); Local(perms); perms := PermutationsList({1,2,3}); Local(result); result:=ZeroVector(3); Local(term); ForEach(term,perms) [ result[ term[1] ] := result[ term[1] ] + LeviCivita(term) * aLeft[ term[2] ] * aRight[ term[3] ] ; ]; result;];";
        scriptMap.put("CrossProduct",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Determinant(_matrix)_(UpperTriangular?(matrix) Or? LowerTriangular?(matrix)) <--[ Local(result); result:=1; ForEach(i, Diagonal(matrix) ) result:=result*i; result;];15 # Determinant(_matrix)_(Length(Select(Diagonal(matrix), \"Zero?\")) >? 0) <-- SymbolicDeterminant(matrix);16 # Determinant(_matrix)_(VarList(matrix) !=? {}) <-- SymbolicDeterminant(matrix);20 # Determinant(_matrix) <-- GaussianDeterminant(matrix);";
        scriptMap.put("Determinant",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Diagonal\",{A})[ Local(result,i,n); n:=Length(A); result:=ZeroVector(n); For(i:=1,i<=?n,i++) [ result[i] := A[i][i]; ]; result;];";
        scriptMap.put("Diagonal",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"DiagonalMatrix\",{list})[ Local(result,i,n); n:=Length(list); result:=Identity(n); For(i:=1,i<=?n,i++) [ result[i][i] := list[i]; ]; result;];";
        scriptMap.put("DiagonalMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(x,i,n,m,aux,dim,result)[ 1 # Dimensions(x_List?) <-- [ Local(i,n,m,aux,dim,result); result:=List(Length(x)); If(Length(x)>?0 And? Length(Select(x, List?))=?Length(x), [ n:=Length(x); dim:=MapSingle(Dimensions,x); m:=Minimum(MapSingle(Length,dim)); For(i:=1,i<=?m,i++) [ aux:=Table(dim[j][i],j,1,n,1); If(Minimum(aux)=?Maximum(aux), result:=DestructiveAppend(result,dim[1][i]), i:=m+1 ); ]; ] ); result; ]; 2 # Dimensions(_x) <-- List();]; ";
        scriptMap.put("Dimensions",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(Dot0,Dot1)[Dot(t1_Vector?,t2_Vector?)_(Length(t1)=?Length(t2)) <-- Dot0(t1,t2,Length(t1));Dot(t1_Matrix?,t2_Vector?)_(Length(t1[1])=?Length(t2)) <--[ Local(i,n,m,result); n:=Length(t1); m:=Length(t2); result:=List(); For(i:=1,i<=?n,i++) DestructiveInsert(result,1,Dot0(t1[i],t2,m)); DestructiveReverse(result);];Dot(t1_Vector?,t2_Matrix?)_(Length(t1)=?Length(t2) And? Length(t2[1])>?0) <-- Dot1(t1,t2,Length(t1),Length(t2[1]));Dot(t1_Matrix?,t2_Matrix?)_(Length(t1[1])=?Length(t2) And? Length(t2[1])>?0) <--[ Local(i,n,k,l,result); n:=Length(t1); k:=Length(t2); l:=Length(t2[1]); result:=List(); For(i:=1,i<=?n,i++) DestructiveInsert(result,1,Dot1(t1[i],t2,k,l)); DestructiveReverse(result);];Dot0(_t1,_t2,_n) <--[ Local(i,result); result:=0; For(i:=1,i<=?n,i++) result:=result+t1[i]*t2[i]; result;];Dot1(_t1,_t2,_m,_n) <--[ Local(i,j,result); result:=ZeroVector(n); For(i:=1,i<=?n,i++) For(j:=1,j<=?m,j++) result[i]:=result[i]+t1[j]*t2[j][i]; result;];]; ";
        scriptMap.put("Dot",scriptString);
        scriptMap.put("Dot0",scriptString);
        scriptMap.put("Dot1",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ExtractSubMatrix( mat_Matrix?, _row1, _col1, _row2, _col2 )_ (And?(PositiveInteger?(row1),PositiveInteger?(col1), PositiveInteger?(row2),PositiveInteger?(col2))) <--[ Local(nrows,ncols,r,row,result); {nrows,ncols} := Dimensions( mat ); Check(And?(row1>?0,col1>?0,row1<?nrows,col1<?ncols), \"Math\", \"ERROR: UL out of range\"); Check(And?(row2>?row1,col2>?col1,row2<=?nrows,col2<=?ncols), \"Math\", \"ERROR: LR out of range\"); result := {}; For(r:=row1,r<=?row2,r++) [ row := Take( MatrixRow(mat,r), {col1,col2} ); result := DestructiveAppend( result, row ); ]; result;];10 # ExtractSubMatrix( mat_Matrix?, _row1, _col1 )_ (And?(PositiveInteger?(row1),PositiveInteger?(col1))) <--[ Local(nrows,ncols); {nrows,ncols} := Dimensions( mat ); Check(And?(row1>?0,col1>?0,row1<?nrows,col1<?ncols), \"Math\", \"ERROR: UL out of range\"); ExtractSubMatrix( mat, row1, col1, nrows, ncols );];";
        scriptMap.put("ExtractSubMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "FrobeniusNorm(matrix_Matrix?) <--[ Local(i,j,result); result:=0; For(i:=1,i<=?Length(matrix),i++) For(j:=1,j<=?Length(matrix[1]),j++) result:=result+Abs(matrix[i][j])^2; Sqrt(result);];";
        scriptMap.put("FrobeniusNorm",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "GaussianDeterminant(matrix):=[ Local(n,s,result); n:=Length(matrix); result:=1; [ matrix:=FlatCopy(matrix); Local(i); For(i:=1,i<=?n,i++) [ matrix[i]:=FlatCopy(matrix[i]); ]; ];  ForEach(i, 1 .. (n-1) ) [ ForEach(k, (i+1) .. n ) [ s:=matrix[k][i]; ForEach(j, i .. n ) [ matrix[k][j] := matrix[k][j] - (s/matrix[i][i])*matrix[i][j];   ]; ]; ];  ForEach(i, Diagonal(matrix) ) result:=result*i; result;];";
        scriptMap.put("GaussianDeterminant",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"GenMatrix\",{func,m,n})[ Local(i,j,result); result:=ZeroMatrix(m,n); For(i:=1,i<=?m,i++) For(j:=1,j<=?n,j++) result[i][j]:=ApplyFast(func,{i,j}); result;];HoldArgument(\"GenMatrix\",func);UnFence(\"GenMatrix\",3);";
        scriptMap.put("GenMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "HankelMatrix(n):=GenMatrix({{i,j}, If(i+j-1>?n,0,i+j-1) }, n,n );HankelMatrix(m,n):=GenMatrix({{i,j}, If(i+j-1>?n,0,i+j-1)}, m,n );";
        scriptMap.put("HankelMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "HessianMatrix(f,v):=GenMatrix({{i,j}, Deriv(v[i]) Deriv(v[j]) f},Length(v),Length(v));";
        scriptMap.put("HessianMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "HilbertInverseMatrix(n):=GenMatrix({{i,j}, (-1)^(i+j)*(i+j-1)*BinomialCoefficient(n+i-1,n-j)*BinomialCoefficient(n+j-1,n-i)*BinomialCoefficient(i+j-2,i-1)^2},n,n);";
        scriptMap.put("HilbertInverseMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "HilbertMatrix(n):=GenMatrix({{i,j}, 1/(i+j-1)}, n,n );HilbertMatrix(m,n):=GenMatrix({{i,j}, 1/(i+j-1)}, m,n );";
        scriptMap.put("HilbertMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Identity(n_NonNegativeInteger?) <--[ Local(i,result); result:={}; For(i:=1,i<=?n,i++) [ DestructiveAppend(result,BaseVector(i,n)); ]; result;];";
        scriptMap.put("Identity",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"InProduct\",{aLeft,aRight})[ Local(length); length:=Length(aLeft); Check(length =? Length(aRight), \"Argument\", \"InProduct: error, vectors not of the same dimension\"); Local(result); result:=0; Local(i); For(i:=1,i<=?length,i++) [ result := result + aLeft[i] * aRight[i]; ]; result;];";
        scriptMap.put("InProduct",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # InfinityNorm( M_Matrix? ) <--[ Local(sumlist,row); sumlist := {}; ForEach(row,M) Push(sumlist,Sum(Abs(row))); Maximum(sumlist);];10 # InfinityNorm( M_Vector? ) <-- Maximum(Abs(M));";
        scriptMap.put("xnfinityNorm",scriptString);
        scriptMap.put("InfinityNorm",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Inverse\",{matrix})[ Local(perms,indices,inv,det,n); n:=Length(matrix); indices:=Table(i,i,1,n,1); perms:=PermutationsList(indices); inv:=ZeroMatrix(n,n); det:=0; ForEach(item,perms) [ Local(i,lc); lc := LeviCivita(item); det:=det+Product(i,1,n,matrix[i][item[i] ])* lc; For(i:=1,i<=?n,i++) [ inv[item[i] ][i] := inv[item[i] ][i]+ Product(j,1,n, If(j=?i,1,matrix[j][item[j] ]))*lc; ]; ]; Check(det !=? 0, \"Math\", \"Zero determinant\"); (1/det)*inv;];";
        scriptMap.put("Inverse",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "JacobianMatrix(f,v):=GenMatrix({{i,j},Deriv(v[j])f[i]},Length(f),Length(f));";
        scriptMap.put("JacobianMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # LU(A_SquareMatrix?) <--[ Local(n,matrix,L,U); n:=Length(A); L:=ZeroMatrix(n,n); U:=ZeroMatrix(n,n); matrix:=ZeroMatrix(n,n); ForEach(i,1 .. n) ForEach(j,1 .. n) matrix[i][j] := A[i][j];  ForEach(i,1 ..(n-1))[  ForEach(k,i+1 .. n)[  matrix[k][i] := matrix[k][i] / matrix[i][i];  ForEach(j,i+1 .. n)[ matrix[k][j] := matrix[k][j] - matrix[k][i]*matrix[i][j]; ]; ]; ]; ForEach(i,1 .. n)[ ForEach(j,1 .. n)[ If(i<=?j,U[i][j]:=matrix[i][j],L[i][j]:=matrix[i][j]); ];  L[i][i]:=1; ]; {L,U};];";
        scriptMap.put("LU",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"LeviCivita\",{indices})[ Local(i,j,length,left,right,factor); length:=Length(indices); factor:=1; For (j:=length,j>?1,j--) [ For(i:=1,i<?j,i++) [ left:=indices[i]; right:=indices[i+1]; If (Equal?(left,right), [ factor := 0 ; ], [ If(Not?(Apply(\"<?\",{left,right})), [ indices:=Insert(Delete(indices,i),i+1,left); factor:= -factor; ]); ]); ]; ]; factor;];";
        scriptMap.put("LeviCivita",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MatrixPower(x_SquareMatrix?, n_NonNegativeInteger?) <--[ Local(result); result:=Identity(Length(x)); While(n !=? 0) [ If(Odd?(n), result:=Dot(result,x)); x:=Dot(x,x); n:=n>>1; ]; result;];MatrixPower(x_SquareMatrix?, n_NegativeInteger?) <-- MatrixPower(Inverse(x),-n);";
        scriptMap.put("MatrixPower",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # MatrixSolve(matrix_Diagonal?,b_Vector?) <--[ If(InVerboseMode(),Tell(\" MatrixSolve_diag\",{matrix,b})); Local(rowsm,rowsb,x); rowsm:=Length(matrix); rowsb:=Length(b); Check(rowsm=?rowsb, \"Argument\", \"MatrixSolve: Matrix and vector must have same number of rows\"); x:=ZeroVector(rowsb); ForEach(i,1 .. rowsb) x[i]:=b[i]/matrix[i][i]; x;];15 # MatrixSolve(matrix_UpperTriangular?,b_Vector?) <--[ If(InVerboseMode(),Tell(\" MatrixSolve_ut\",{matrix,b})); Local(rowsm,rowsb,x,s); rowsm:=Length(matrix); rowsb:=Length(b); Check(rowsm=?rowsb, \"Argument\", \"MatrixSolve: Matrix and vector must have same number of rows\"); x:=ZeroVector(rowsb); x[rowsb]:=b[rowsb]/matrix[rowsb][rowsb]; If(InVerboseMode(),Echo({\"set x[\",rowsb,\"] =? \",b[rowsb]/matrix[rowsb][rowsb]})); ForEach(i,(rowsb-1) .. 1 )[ s:=b[i]; ForEach(j,i+1 .. rowsb )[ s:= s - matrix[i][j]*x[j]; ]; x[i]:= s/matrix[i][i]; If(InVerboseMode(),Echo({\"set x[\",i,\"] =? \",s/matrix[i][i]})); ]; x;];15 # MatrixSolve(matrix_LowerTriangular?,b_Vector?) <--[ If(InVerboseMode(),Tell(\" MatrixSolve_lt\",{matrix,b})); Local(rowsm,rowsb,x,s); rowsm:=Length(matrix); rowsb:=Length(b); Check(rowsm=?rowsb, \"Argument\", \"MatrixSolve: Matrix and vector must have same number of rows\"); x:=ZeroVector(rowsb); x[1]:=b[1]/matrix[1][1]; If(InVerboseMode(),Echo({\"set x[1] =? \",b[1]/matrix[1][1]})); ForEach(i,2 .. rowsb )[ s:=b[i]; ForEach(j,1 .. (i-1) )[ s:= s - matrix[i][j]*x[j]; ]; x[i]:= s/matrix[i][i]; If(InVerboseMode(),Echo({\"set x[\",i,\"] =? \",s/matrix[i][i]})); ]; x;];20 # MatrixSolve(matrix_Matrix?,b_Vector?) <--[ If(InVerboseMode(),Tell(\" MatrixSolve\",{matrix,b})); Local(aug,rowsm,rowsb,x,s); rowsm:=Length(matrix); rowsb:=Length(b); Check(rowsm=?rowsb, \"Argument\", \"MatrixSolve: Matrix and vector must have same number of rows\"); aug:=ZeroMatrix(rowsb,rowsb+1); x:=ZeroVector(rowsb);  ForEach(i, 1 .. rowsb ) ForEach(j, 1 .. rowsb ) aug[i][j] := matrix[i][j]; ForEach(i, 1 .. rowsb ) aug[i][rowsb+1] := b[i];  ForEach(i, 1 .. (rowsb-1) )[   If(aug[i][i] =? 0, [ Local(p,tmp); p:=i+1; While( aug[p][p] =? 0 )[ p++; ]; If(InVerboseMode(), Echo({\"switching row \",i,\"with \",p}) ); tmp:=aug[i]; aug[i]:=aug[p]; aug[p]:=tmp; ]); ForEach(k, (i+1) .. rowsb )[ s:=aug[k][i]; ForEach(j, i .. (rowsb+1) )[ aug[k][j] := aug[k][j] - (s/aug[i][i])*aug[i][j];   ]; ]; ];  x[rowsb]:=aug[rowsb][rowsb+1]/aug[rowsb][rowsb]; If(InVerboseMode(),Echo({\"set x[\",rowsb,\"] =? \",x[rowsb] })); ForEach(i,(rowsb-1) .. 1 )[ s:=aug[i][rowsb+1]; ForEach(j,i+1 .. rowsb)[ s := s - aug[i][j]*x[j]; ]; x[i]:=Simplify(s/aug[i][i]); If(InVerboseMode(),Echo({\"set x[\",i,\"] = \",x[i] })); ]; x;];";
        scriptMap.put("MatrixSolve",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Minor(matrix,i,j) := CoFactor(matrix,i,j)*(-1)^(i+j);";
        scriptMap.put("Minor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Norm(_v) <-- PNorm(v,2);";
        scriptMap.put("Norm",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Normalize\",{vector})[ Local(norm); norm:=0; ForEach(item,vector) [ norm:=norm+item*item; ]; (1/(norm^(1/2)))*vector;];";
        scriptMap.put("Normalize",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"OrthogonalBasis\",{W})[ Local(V,j,k); V:=ZeroMatrix(Length(W),Length(W[1]) ); V[1]:=W[1]; For(k:=2,k<=?Length(W),k++)[ Check(Not? Zero?(Norm(W[k])), \"Argument\", \"OrthogonalBasis: Input vectors must be linearly independent\"); V[k]:=W[k]-Sum(j,1,k-1,InProduct(W[k],V[j])*V[j]/Norm(V[j])^2); ]; V;];";
        scriptMap.put("OrthogonalBasis",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"OrthonormalBasis\",{W})[ Local(i); W:=OrthogonalBasis(W); For(i:=1,i<=?Length(W),i++)[ W[i]:=W[i]/Norm(W[i]); ]; W;];";
        scriptMap.put("OrthonormalBasis",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Outer(t1_Vector?, t2_Vector?) <--[ Local(i,j,n,m,result); n:=Length(t1); m:=Length(t2); result:=ZeroMatrix(n,m); For(i:=1,i<=?n,i++) For(j:=1,j<=?m,j++) result[i][j]:=t1[i]*t2[j]; result;];";
        scriptMap.put("Outer",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"PNorm\",{v,p})[ Local(result,i); Check(p>=?1, \"Argument\", \"PNorm: p must be >=? 1\"); result:=0; For(i:=1,i<=?Length(v),i++)[ result:=result+Abs(v[i])^p; ];  If(p=?2,Sqrt(result),(result)^(1/p) );];";
        scriptMap.put("PNorm",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RREF( AM_Matrix? ) <--[ If( Not? Bound?(iDebug), iDebug := False ); If(iDebug,Tell(\"RREF\",AM)); Local(mRows,nCols,nVars,varVec,ir,jc,col,am,e,ii,pivot); {mRows,nCols} := Dimensions(AM); am := FlatCopy(AM);   Local(cc,cr,col,cnz);  cc := 1;  cr := 1;  While( cr <=? mRows )  [ Local(ic);  If(iDebug, Tell(\" STEP 1\")); cnz := 0;  For(ic:=1,ic<=?nCols,ic++)  [  col := MatrixColumn(am,ic); If( cr >? 1, col := Drop( col, {1,cr-1} ) );  If(Not? ZeroVector?(col), [cnz := ic; Break();]);  ]; cc := cnz; If( iDebug, Tell(\" first non-zero column is \",cc));    If(iDebug, Tell(\" STEP 2\")); For( ir:=cr, ir<=?mRows,ir++ ) [  If( am[ir][cc] !=? 0 And? ir !=? 1,  [ {am[ir],am[cr]} := {am[cr],am[ir]}; If(iDebug, Tell(\" swapping rows \",{cr,ir})); Break(); ] ); ]; If(iDebug,[Tell(\"working matrix\");TableForm(am);]);    If(iDebug, Tell(\" STEP 3\")); am[cr] := am[cr]/am[cr][cc]; If(iDebug,TableForm(am));    If(iDebug, Tell(\" STEP 4\")); For(ir:=cr+1,ir<=?mRows,ir++) [ If( am[ir][cc] !=? 0,  [ am[ir] := am[ir] - am[ir][cc]*am[cr]; ] ); ]; If(iDebug,TableForm(am));     If(iDebug, Tell(\" STEP 5\")); cr := cr + 1; If(iDebug And? cr <=? mRows, [NewLine();Tell(\" \",cr);]);   If( cr=?mRows And? ZeroVector?(am[cr]), Break() ); ];       If(iDebug, Tell(\" STEP 6\")); Local(pc,jr); For(ir:=mRows,ir>?1,ir--) [   If(iDebug,Tell(\"\",{ir,am[ir]})); If(ZeroVector?(am[ir]), [If(iDebug,Tell(\" trailing row of zeros: row \",ir)); ir:=ir-1;Continue();], [ pc := Find(am[ir],1);  If(pc >? 0,  [ For(jr:=ir-1,jr>=?1,jr--) [ If(am[jr][pc]!=?0, am[jr]:=am[jr]-am[jr][pc]*am[ir]); If(iDebug,[NewLine();TableForm(am);]); ]; ] ); ] ); ]; am;]; ";
        scriptMap.put("RREF",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "20 # RecursiveDeterminant(_matrix) <--[  Local(result); If(Equal?(Length(matrix),1),matrix[1][1],[ result:=0; ForEach(i,1 .. Length(matrix))  If(Not?(Equal?(matrix[1][i],0)),  result:=result+matrix[1][i]*(-1)^(i+1)* RecursiveDeterminant(Transpose(Drop(Transpose(Drop(matrix,{1,1})),{i,i})))); result; ]);];";
        scriptMap.put("RecursiveDeterminant",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Sparsity\",{matrix})[ Local(rows,cols,nonzero); nonzero:=0; rows:=Length(matrix); cols:=Length(matrix[1]); ForEach(i, 1 .. rows ) ForEach(j, 1 .. cols ) If(matrix[i][j] !=? 0, nonzero:=nonzero+1 ); N(1 - nonzero/(rows*cols));];";
        scriptMap.put("Sparsity",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"SylvesterMatrix\",{poly1, poly2, var})[ Local(i,m,p,q,y,z,result); y:=Degree(poly1,var); z:=Degree(poly2,var); m:=y+z; p:={}; q:={}; result:=ZeroMatrix(m,m); For(i:=y,i>=?0,i--) DestructiveAppend(p,Coef(poly1,var,i)); For(i:=z,i>=?0,i--) DestructiveAppend(q,Coef(poly2,var,i)); For(i:=1,i<=?z,i++) [ Local(j,k); k:=1; For(j:=i,k<=?Length(p),j++) [ result[i][j]:=p[k]; k++; ]; ]; For(i:=1,i<=?y,i++) [ Local(j,k); k:=1; For(j:=i,k<=?Length(q),j++) [ result[i+z][j]:=q[k]; k++; ]; ]; result;];";
        scriptMap.put("SylvesterMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "20 # SymbolicDeterminant(_matrix) <--[ Local(perms,indices,result); Check((Matrix?(matrix)), \"Argument\", \"Determinant: Argument must be a matrix\"); indices:=Table(i,i,1,Length(matrix),1); perms:=PermutationsList(indices); result:=0; ForEach(item,perms) result:=result+Product(i,1,Length(matrix),matrix[i][item[i] ])* LeviCivita(item); result;];";
        scriptMap.put("SymbolicDeterminant",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ToeplitzMatrix(N):=GenMatrix({{i,j},N[Abs(i-j)+1]}, Length(N), Length(N) );";
        scriptMap.put("ToeplitzMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Trace(matrix_List?) <--[ Local(i,j,n,d,r,aux,result);  d:=Dimensions(matrix);  r:=Length(d);   n:=Minimum(d);   result:=0;  For(i:=1,i<=?n,i++) [ aux:=matrix[i]; For(j:=2,j<=?r,j++) aux:=aux[i]; result:=result+aux; ]; result;];";
        scriptMap.put("Trace",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Transpose(matrix_List?)_(Length(Dimensions(matrix))>?1) <--[ Local(i,j,result); result:=ZeroMatrix(Length(matrix[1]),Length(matrix)); For(i:=1,i<=?Length(matrix),i++) For(j:=1,j<=?Length(matrix[1]),j++) result[j][i]:=matrix[i][j]; result;];";
        scriptMap.put("Transpose",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"VandermondeMatrix\",{vector})[ Local(len,i,j,item,matrix); len:=Length(vector); matrix:=ZeroMatrix(len,len); For(i:=1,i<=?Length(matrix),i++)[ For(j:=1,j<=?Length(matrix[1]),j++)[ matrix[j][i]:=vector[i]^(j-1); ]; ]; matrix;];";
        scriptMap.put("VandermondeMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "WilkinsonMatrix(N):=GenMatrix({{i,j}, If( Abs(i-j)=?1,1, [ If(i=?j,Abs( (N-1)/2 - i+1 ),0 ); ] )}, N,N );";
        scriptMap.put("WilkinsonMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "WronskianMatrix(f,v):=GenMatrix({{i,j}, Deriv(v,i-1) f[j]}, Length(f), Length(f) );";
        scriptMap.put("WronskianMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "x X y := CrossProduct(x,y);";
        scriptMap.put("X",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # ZeroMatrix(n_NonNegativeInteger?) <-- ZeroMatrix(n,n);10 # ZeroMatrix(n_NonNegativeInteger?,m_NonNegativeInteger?) <--[ Local(i,result); result:={}; For(i:=1,i<=?n,i++) DestructiveInsert(result,i,ZeroVector(m)); result;];";
        scriptMap.put("ZeroMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"ZeroVector\",{n})[ Local(i,result); result:={}; For(i:=1,i<=?n,i++) [ DestructiveInsert(result,1,0); ]; result;];";
        scriptMap.put("ZeroVector",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "_x o _y <-- Outer(x,y);";
        scriptMap.put("o",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"MatrixColumn\",{matrix,col})[ Local(m); m:=matrix[1]; Check(col >? 0, \"Argument\", \"MatrixColumn: column index out of range\"); Check(col <=? Length(m), \"Argument\", \"MatrixColumn: column index out of range\"); Local(i,result); result:={}; For(i:=1,i<=?Length(matrix),i++) DestructiveAppend(result,matrix[i][col]); result;];";
        scriptMap.put("MatrixColumn",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MatrixColumnAugment( M_Matrix?, v_Vector? )_(Length(v)=?Dimensions(M)[2]) <--[ If( Not? Bound?(iDebug), iDebug := False ); If( iDebug, Tell(MatrixColumnAugment,{M,v}) ); Local(mRows,nCols,newMat,ir); Local(MT,MA); MT := Transpose(M); MT := MatrixRowStack(MT,v); MA := Transpose(MT);];";
        scriptMap.put("MatrixColumnSwap",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MatrixColumnReplace( M_Matrix?, jCol_PositiveInteger?, v_Vector? )_(Length(v)=?Dimensions(M)[2]) <--[ If( Not? Bound?(iDebug), iDebug := False ); If( iDebug, Tell(MatrixColumnReplace,{M,jCol,v}) ); Local(mRows,nCols,MT); {mRows,nCols} := Dimensions(M); If( jCol <=? nCols, [ MT:=Transpose(M); DestructiveReplace(MT,jCol,v); M:=Transpose(MT); ] ); M;];";
        scriptMap.put("MatrixColumnReplace",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MatrixColumnSwap( M_Matrix?, jCol1_PositiveInteger?, jCol2_PositiveInteger? )_ (And?(jCol1<=?Dimensions(M)[2],jCol2<=?Dimensions(M)[2])) <--[ If( Not? Bound?(iDebug), iDebug := False ); If( iDebug, Tell(MatrixColumnSwap,{M,jCol1,jCol2}) ); Local(MT); MT := Transpose(M); MT := MatrixRowSwap(MT,jCol1,jCol2); M := Transpose(MT);];";
        scriptMap.put("MatrixColumnSwap",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"MatrixRow\",{matrix,row})[ Check(row >? 0, \"Argument\", \"MatrixRow: row index out of range\"); Check(row <=? Length(matrix), \"Argument\", \"MatrixRow: row index out of range\"); Local(result); result:=matrix[row]; result;];";
        scriptMap.put("MatrixRow",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MatrixRowReplace( M_Matrix?, iRow_PositiveInteger?, v_Vector? )_(Length(v)=?Length(M[1])) <--[ If( Not? Bound?(iDebug), iDebug := False ); If( iDebug, Tell(MatrixRowReplace,{M,iRow,v}) ); Local(mRows,nCols); {mRows,nCols} := Dimensions(M); If( iRow <=? mRows, DestructiveReplace(M,iRow,v) ); M;];";
        scriptMap.put("MatrixRowReplace",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MatrixRowStack( M_Matrix?, v_Vector? )_(Length(v)=?Dimensions(M)[1]) <--[ If( Not? Bound?(iDebug), iDebug := False ); If( iDebug, Tell(MatrixRowStack,{M,v}) ); Local(mRows,nCols,newMat,ir); {mRows,nCols} := Dimensions(M); newMat := ZeroMatrix(mRows+1,nCols); For(ir:=1,ir<?mRows+1,ir++) [ MatrixRowReplace(newMat,ir,MatrixRow(M,ir)); ]; MatrixRowReplace(newMat,mRows+1,v); newMat;];";
        scriptMap.put("MatrixRowStack",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MatrixRowSwap( M_Matrix?, iRow1_PositiveInteger?, iRow2_PositiveInteger? )_ (And?(iRow1<=?Dimensions(M)[1],iRow2<=?Dimensions(M)[1])) <--[ If( Not? Bound?(iDebug), iDebug := False ); If( iDebug, Tell(MatrixRowSwap,{M,iRow1,iRow2}) ); Local(row1,row2); If( iRow1 !=? iRow2, [ row1 := MatrixRow(M,iRow1); row2 := MatrixRow(M,iRow2); DestructiveReplace(M,iRow1,row2); DestructiveReplace(M,iRow2,row1); ] ); M;];";
        scriptMap.put("MatrixRowSwap",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Append\",{list,element})[ Check(List?(list), \"Argument\", \"The first argument must be a list.\"); Insert(list,Length(list)+1,element);];";
        scriptMap.put("Append",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(max,f,result)[ BSearch(max,f) := [ Local(result); Bind(result, FindIsq(max,f)); If(Apply(f,{result})!=?0,Bind(result,-1)); result; ];];UnFence(\"BSearch\",2);";
        scriptMap.put("BSearch",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Contains?\",{list,element})[ Local(result); Bind(result,False); While(And?(Not?(result), Not?(Equal?(list, {})))) [ If(Equal?(First(list),element), Bind(result, True), Bind(list, Rest(list)) ); ]; result;];";
        scriptMap.put("Contains?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Count\",{list,element})[ Local(result); Bind(result,0); ForEach(item,list) If(Equal?(item, element), Bind(result,AddN(result,1))); result;];";
        scriptMap.put("Count",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"DestructiveAppend\",{list,element})[ DestructiveInsert(list,Length(list)+1,element);];";
        scriptMap.put("DestructiveAppend",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"DestructiveAppendList\",{list,toadd})[ Local(i,nr); nr:=Length(toadd); For(i:=1,i<=?nr,i++) [ DestructiveAppend(list,toadd[i]); ]; True;];";
        scriptMap.put("DestructiveAppendList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Difference\",{list1,list2})[ Local(l2,index,result); l2:=FlatCopy(list2); result:=FlatCopy(list1); ForEach(item,list1) [ Bind(index,Find(l2,item)); If(index>?0, [ DestructiveDelete(l2,index); DestructiveDelete(result,Find(result,item)); ] ); ]; result;];";
        scriptMap.put("Difference",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Drop\", {lst, range});RuleHoldArguments(\"Drop\", 2, 1, List?(range)) Concat(Take(lst,range[1]-1), Drop(lst, range[2]));RuleHoldArguments(\"Drop\", 2, 2, range >=? 0) If( range =? 0 Or? lst =? {}, lst, Drop( Rest(lst), range-1 ));RuleHoldArguments(\"Drop\", 2, 2, range <? 0) Take( lst, If(AbsN(range) <? Length(lst), Length(lst) + range, 0 ) );";
        scriptMap.put("Drop",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"FillList\", {aItem, aLength})[ Local(i, aResult); aResult:={}; For(i:=0, i<?aLength, i++) DestructiveInsert(aResult,1,aItem); aResult;];";
        scriptMap.put("FillList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Find( list_List?, _element ) <-- [ Local(result,count); result := -1; count := 1; While( And?( result <? 0, Not? ( Equal? (list, {}) ))) [ If(Equal?(First(list), element), result := count ); list := Rest(list); count++; ]; result; ];";
        scriptMap.put("Find",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " 10 # FindAll( list_List?, _element ) <-- [ Local(results,count); results := {}; count := 1; While( Not? list =? {} ) [ If(Equal?( First(list), element), DestructiveAppend(results,count) ); list := Rest(list); count++; ]; results; ];";
        scriptMap.put("FindAll",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # FindFirst( list_List?, _element ) <-- [ Local(result,count); result := -1; count := 1; While( And?( result <? 0, Not? ( Equal? (list, {}) ))) [ If(Equal?(First(list), element), result := count ); list := Rest(list); count++; ]; result; ];";
        scriptMap.put("FindFirst",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(max,f,low,high,mid,current)[FindIsq(max,f) :=[ Local(low,high,mid,current); low:=1; high:=max+1; Bind(mid,((high+low)>>1)); While(high>?low And? mid>?1) [ Bind(mid,((high+low)>>1)); Bind(current,Apply(f,{mid})); If(current =? 0, high:=low-1, If(current >? 0, Bind(high,mid), Bind(low,mid+1) ) ); ]; mid;];];UnFence(\"FindIsq\",2);";
        scriptMap.put("FindIsq",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"FindPredicate\",{list,predicate})[ Local(result,count); Bind(result, -1); Bind(count, 1); While(And?(result<?0, Not?(Equal?(list, {})))) [ If(Apply(predicate,{First(list)}), Bind(result, count) ); Bind(list,Rest(list)); Bind(count,AddN(count,1)); ]; result;]; ";
        scriptMap.put("FindPredicate",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # FuncList(expr_Atom?) <-- {};20 # FuncList(expr_Function?) <--RemoveDuplicates( Concat( {First(FunctionToList(expr))}, Apply(\"Concat\", MapSingle(\"FuncList\", Rest(FunctionToList(expr))) ) ));10 # FuncList(expr_Atom?, looklist_List?) <-- {};20 # FuncList(expr_Function?, looklist_List?)_(Not? Contains?(looklist, ToAtom(Type(expr)))) <-- {ToAtom(Type(expr))};30 # FuncList(expr_Function?, looklist_List?) <--RemoveDuplicates( Concat( {First(FunctionToList(expr))}, [   Local(item, result); result := {}; ForEach(item, expr) result := Concat(result, FuncList(item, looklist)); result; ] ));HoldArgumentNumber(\"FuncList\", 1, 1);HoldArgumentNumber(\"FuncList\", 2, 1);";
        scriptMap.put("FuncList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "FuncListArith(expr) := FuncList(expr, {ToAtom(\"+\"), ToAtom(\"-\"), *, /});HoldArgumentNumber(\"FuncListArith\", 1, 1);";
        scriptMap.put("FuncListArith",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Intersection( LoL_List? )_(AllSatisfy?(\"List?\",LoL)) <--[  Local(nLists,L0,L1,ii,result,LI); nLists := Length(LoL);  If( nLists =? 1, [ result := LoL[1]; ], [ L0 := FlatCopy(LoL[1]); For( ii:=2,ii<=?nLists,ii++) [ L1 := FlatCopy(LoL[ii]);  LI := Intersection(L0,L1);  L0 := FlatCopy(LI); ];  result := L0; ] ); result;]; 11 # Intersection(list1_List?,list2_List?) <--[  Local(l2,index,result); l2:=FlatCopy(list2); result:={}; ForEach(item,list1) [ Bind(index, Find(l2,item)); If(index>?0, [ DestructiveDelete(l2,index); DestructiveInsert(result,1,item); ] ); ]; DestructiveReverse(result);];";
        scriptMap.put("Intersection",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Last(LL_List?)_(Length(LL)>?0) <-- First(Reverse(LL));";
        scriptMap.put("Last",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Macro(\"MacroMapArgs\",{expr,oper})[ Local(ex,tl,op); Bind(op,@oper); Bind(ex,FunctionToList(@expr)); Bind(tl,Rest(ex)); ListToFunction(Concat({ex[1]}, `MacroMapSingle(@op,Hold(@tl))) );];UnFence(\"MacroMapArgs\",2);HoldArgument(\"MacroMapArgs\",oper);";
        scriptMap.put("MacroMapArgs",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TemplateFunction(\"MacroMapSingle\",{func,list})[ Local(mapsingleresult); mapsingleresult:={}; ForEach(mapsingleitem,list) [ DestructiveInsert(mapsingleresult,1, `ApplyFast(func,{Hold(Hold(@mapsingleitem))})); ]; DestructiveReverse(mapsingleresult);];UnFence(\"MacroMapSingle\",2);HoldArgument(\"MacroMapSingle\",func);HoldArgument(\"MacroMapSingle\",list);";
        scriptMap.put("MacroMapSingle",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(func,lists,mapsingleresult,mapsingleitem)[ TemplateFunction(\"Map\",{func,lists}) [ Local(mapsingleresult,mapsingleitem); mapsingleresult:={}; lists:=Transpose(lists); ForEach(mapsingleitem,lists) [ DestructiveInsert(mapsingleresult,1,Apply(func,mapsingleitem)); ]; DestructiveReverse(mapsingleresult); ]; UnFence(\"Map\",2); HoldArgument(\"Map\",func);];";
        scriptMap.put("Map",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TemplateFunction(\"MapArgs\",{expr,oper})[ Bind(expr,FunctionToList(expr)); ListToFunction(Concat({expr[1]}, Apply(\"MapSingle\",{oper,Rest(expr)}) ) );];UnFence(\"MapArgs\",2);HoldArgument(\"MapArgs\",oper);";
        scriptMap.put("MapArgs",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TemplateFunction(\"MapSingle\",{func,list})[ Local(mapsingleresult); mapsingleresult:={}; ForEach(mapsingleitem,list) [ DestructiveInsert(mapsingleresult,1, Apply(func,{mapsingleitem})); ]; DestructiveReverse(mapsingleresult);];UnFence(\"MapSingle\",2);HoldArgument(\"MapSingle\",func);";
        scriptMap.put("MapSingle",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Partition(lst, len):= If( Length(lst) <? len Or? len =? 0, {}, Concat( {Take(lst,len)}, Partition(Drop(lst,len), len) ));";
        scriptMap.put("Partition",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Pop\",{stack,index})[ Local(result); result:=stack[index]; DestructiveDelete(stack,index); result;];";
        scriptMap.put("Pop",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"PopBack\",{stack}) Pop(stack,Length(stack));";
        scriptMap.put("PopBack",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"PopFront\",{stack}) Pop(stack,1);";
        scriptMap.put("PopFront",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # PrintList(list_List?) <-- PrintList(list, \", \");10 # PrintList({}, padding_String?) <-- \"\";20 # PrintList(list_List?, padding_String?) <-- PipeToString() [ Local(i); ForEach(i, list) [ If(Not?(Equal?(i, First(list))), WriteString(padding)); If (String?(i), WriteString(i), If(List?(i), WriteString(\"{\" ~ PrintList(i, padding) ~ \"}\"), Write(i))); ];];";
        scriptMap.put("PrintList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Push\",{stack,element})[ DestructiveInsert(stack,1,element);];";
        scriptMap.put("Push",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Remove(list, expression) :=[ Local(result); Bind(result,{}); ForEach(item,list) If(item !=? expression, DestructiveAppend(result,item)); result;];";
        scriptMap.put("Remove",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"RemoveDuplicates\",{list})[ Local(result); Bind(result,{}); ForEach(item,list) If(Not?(Contains?(result,item)),DestructiveAppend(result,item)); result;];";
        scriptMap.put("RemoveDuplicates",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Reverse(list):=DestructiveReverse(FlatCopy(list));";
        scriptMap.put("Reverse",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(predicate,list,result,item)[ Function(\"Select\",{list,predicate}) [ Local(result); result:={}; ForEach(item,list) [ If(Apply(predicate,{item}),DestructiveAppend(result,item)); ]; result; ]; HoldArgument(\"Select\",predicate); UnFence(\"Select\",2);];";
        scriptMap.put("Select",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Swap\",{list,index1,index2})[ Local(item1,item2); item1:=list[index1]; item2:=list[index2]; list[index1] := item2; list[index2] := item1;];";
        scriptMap.put("Swap",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TemplateFunction(\"Table\",{body,var,countfrom,countto,step}) [ MacroLocal(var); Local(result,nr,ii); result:={}; nr := (countto - countfrom) / step; ii := 0; While( ii <=? nr ) [ MacroBind( var, countfrom + ii * step ); DestructiveInsert( result,1,Eval(body) ); Bind(ii,AddN(ii,1)); ]; DestructiveReverse(result); ];HoldArgumentNumber(\"Table\",5,1); HoldArgumentNumber(\"Table\",5,2); UnFence(\"Table\",5);";
        scriptMap.put("Table",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Take\", {lst, range});RuleHoldArguments(\"Take\", 2, 1, List?(range)) Take( Drop(lst, range[1] -1), range[2] - range[1] + 1);RuleHoldArguments(\"Take\", 2, 2, range >=? 0) If( Length(lst)=?0 Or? range=?0, {}, Concat({First(lst)}, Take(Rest(lst), range-1)));RuleHoldArguments(\"Take\", 2, 2, range <? 0) Drop( lst, If(AbsN(range) <? Length(lst), Length(lst) + range, 0 ));";
        scriptMap.put("Take",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Union\",{list1,list2})[ RemoveDuplicates(Concat(list1,list2));];";
        scriptMap.put("Union",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "VarList(_expr) <-- VarList(expr,\"Variable?\");Function(\"VarList\",{expr,filter})[ RemoveDuplicates(VarListAll(expr,filter));];";
        scriptMap.put("VarList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "VarListAll(_expr) <-- VarListAll(expr,\"Variable?\");10 # VarListAll(_expr,_filter)_(Apply(filter,{expr}) =? True) <-- {expr};20 # VarListAll(expr_Function?,_filter) <--[ Local(item,result, flatlist); Bind(flatlist,Rest(FunctionToList(expr))); Bind(result,{}); ForEach(item,flatlist) Bind(result,Concat(result,VarListAll(item,filter))); result;];30 # VarListAll(_expr,_filter) <-- {};";
        scriptMap.put("VarListAll",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "VarListArith(expr) := VarListSome(expr, {ToAtom(\"+\"), ToAtom(\"-\"), *, /});";
        scriptMap.put("VarListArith",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # VarListSome({}, _looklist) <-- {};10 # VarListSome(expr_Variable?, _looklist) <-- {expr};15 # VarListSome(expr_Atom?, _looklist) <-- {};20 # VarListSome(expr_Function?, looklist_List?)_(Not? Contains?(looklist, ToAtom(Type(expr)))) <-- {expr};30 # VarListSome(expr_Function?, looklist_List?) <--RemoveDuplicates( [  Local(item, result); result := {}; ForEach(item, expr) result := Concat(result, VarListSome(item, looklist)); result; ]);";
        scriptMap.put("VarListSome",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(GlobalStack, x)[ GlobalStack := {}; GlobalPop(x_Atom?) <-- [ Check(Length(GlobalStack)>?0, \"Invariant\", \"GlobalPop: Error: empty GlobalStack\"); MacroBind(x, PopFront(GlobalStack)); Eval(x); ]; HoldArgumentNumber(\"GlobalPop\", 1, 1); GlobalPop() <-- [ Check(Length(GlobalStack)>?0, \"Invariant\", \"GlobalPop: Error: empty GlobalStack\"); PopFront(GlobalStack); ]; GlobalPush(_x) <-- [ Push(GlobalStack, x); x; ];];";
        scriptMap.put("GlobalPush",scriptString);
        scriptMap.put("GlobalPop",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "NewStack() := {{},{}};10 # PushStackFrame(_stack,unfenced) <-- [ DestructiveInsert(stack[1],1,{}); DestructiveInsert(stack[2],1,True); ];10 # PushStackFrame(_stack,fenced) <-- [ DestructiveInsert(stack[1],1,{}); DestructiveInsert(stack[2],1,False); ];PopStackFrame(stack):=[ DestructiveDelete(stack[1],1); DestructiveDelete(stack[2],1);];StackDepth(_stack) <-- Length(stack[1]);AddToStack(stack,element) :=[ DestructiveInsert(stack[1][1],1,{element,{}});];DropOneFrame(_stack) <-- {Rest(stack[1]),Rest(stack[2])};10 # IsOnStack({{},{}},_element) <-- False;11 # IsOnStack(_stack,_element)_(stack[1][1][element] !=? Empty) <-- True;20 # IsOnStack(_stack,_element)_(StackDepth(stack)>?0 And? stack[2][1] =? True) <-- IsOnStack(DropOneFrame(stack),element);30 # IsOnStack(_stack,_element) <--[False;];10 # FindOnStack(_stack,_element)_(stack[1][1][element] !=? Empty) <-- stack[1][1][element];20 # FindOnStack(_stack,_element)_(StackDepth(stack)>?0 And? stack[2][1] =? True) <-- FindOnStack(DropOneFrame(stack),element);30 # FindOnStack(_stack,_element) <-- Check(False, \"Argument\", \"Illegal stack access! Use IsOnStack.\");";
        scriptMap.put("NewStack",scriptString);
        scriptMap.put("PushStackFrame",scriptString);
        scriptMap.put("PopStackFrame",scriptString);
        scriptMap.put("StackDepth",scriptString);
        scriptMap.put("AddToStack",scriptString);
        scriptMap.put("IsOnStack",scriptString);
        scriptMap.put("FindOnStack",scriptString);
        scriptMap.put("DropOneFrame",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "20 # (list_List? AddTo _rest) <--[ Local(res); res:={}; ForEach(item,list) [ res := Concat(res,item AddTo rest); ]; res;];30 # (_aitem AddTo list_List?) <--[ MapSingle({{orig},aitem And? orig},list);];40 # (_aitem AddTo _b) <-- aitem And? b;";
        scriptMap.put("AddTo",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(localResult) [ localResult := True;   10 # LocPredicate(exp_Atom?) <-- [ Local(tr, result);  tr := patterns;  result := False;  While (tr !=? {}) [ If (First(First(tr)) =? exp, [ Echo(\"RULE_Atom: \", Last(First(tr)));  Bind(localResult,Eval(First(Rest(First(tr)))));  result := True;  tr:={}; ], [ tr := Rest(tr); ]); ];  result; ];   10 # LocPredicate(exp_Function?) <-- [ Local(tr, result, head);  tr := patterns;  result := False;  While (tr !=? {}) [ Bind(head, First(First(tr)));  If (Not?(Atom?(head)) And? exp[0] =? head[1] And? PatternMatches(head[2], exp), [ Echo(\"RULE_Function: \", Last(First(tr)));  Bind(localResult, Eval(First(Rest(First(tr)))));  Bind(result, True);  Bind(tr, {}); ], [ Bind(tr, Rest(tr)); ]); ];  result; ];    20 # LocPredicate(_exp) <-- False;   LocChange(_exp) <-- localResult;  ]; UnFence(\"LocPredicate\",1);UnFence(\"LocChange\",1);10 # LocProcessSingle({_pat, _post, _exp}) <-- { {pat[0], PatternCreate(pat, post)}, exp, \"UNNAMED\" };20 # LocProcessSingle({pat_Function?, _exp}) <-- { {pat[0], PatternCreate(pat,True)}, exp, \"UNNAMED\" };30 # LocProcessSingle({pat_Atom?, _exp}) <-- { pat, exp, \"UNNAMED\" };40 # LocProcessSingle(pat_Function? <- _exp) <-- [ Local(justPattern, postPredicate, ruleName);  justPattern := pat;  If(Type(justPattern) =? \"_\", [  justPattern := pat[1]; postPredicate := pat[2]; ], [  postPredicate := True; ] );    If(Type(justPattern) =? \"#\", [  ruleName := pat[1]; justPattern := pat[2];  If(Function?(justPattern), [ { {justPattern[0], PatternCreate(justPattern, postPredicate)}, exp , ruleName}; ], [  { justPattern, exp, ruleName }; ]  ); ], [ ruleName := \"UNNAMED\"; justPattern := pat; { {justPattern[0], PatternCreate(justPattern, postPredicate)}, exp , ruleName}; ] ); ];50 # LocProcessSingle(pat_Atom? <- _exp) <-- { pat, exp, \"UNNAMED\" };LocProcess(patterns) :=[ MapSingle(\"LocProcessSingle\", patterns);];CompilePatterns(patterns) := LocPatterns(LocProcess(patterns));";
        scriptMap.put("CompilePatterns",scriptString);
        scriptMap.put("LocProcess",scriptString);
        scriptMap.put("LocProcessSingle",scriptString);
        scriptMap.put("LocPredicate",scriptString);
        scriptMap.put("LocChange",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Where\",{left,right});UnFence(\"Where\",2);10 # (_body Where var_Atom? == _value) <-- `[Local(@var);@var := @value;@body;];20 # (_body Where (_a And? _b)) <--[ Bind(body,`(@body Where @a)); `(@body Where @b);];30 # (_body Where {}) <-- {};40 # (_body Where list_List?)_List?(list[1]) <-- [ Local(head,rest); head:=First(list); rest:=Rest(list); rest:= `(@body Where @rest); `(@body Where @head) ~ rest; ];50 # (_body Where list_List?) <-- [ Local(head,rest); While (list !=? {}) [ head:=First(list); body := `(@body Where @head); list:=Rest(list); ]; body; ];60 # (_body Where _var == _value) <-- Subst(var,value)body;";
        scriptMap.put("Where",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"<-\",{left,right});HoldArgument(\"<-\",left);HoldArgument(\"<-\",right);";
        scriptMap.put("<-",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # (_expression /:: LocPatterns(_patterns)) <--[ MacroSubstitute(expression,\"LocPredicate\",\"LocChange\");];10 # (_expression /:: _patterns) <--[ Local(old);  Bind(patterns, LocProcess(patterns));  Bind(old, expression);  Bind(expression, MacroSubstitute(expression,\"LocPredicate\",\"LocChange\"));  While (expression !=? old) [ Bind(old, expression);  Bind(expression, MacroSubstitute(expression,\"LocPredicate\",\"LocChange\")); ];  expression;];";
        scriptMap.put("/::",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # (_expression /: LocPatterns(_patterns)) <--[ MacroSubstitute(expression,\"LocPredicate\",\"LocChange\");];10 # (_expression /: _patterns) <--[ Bind(patterns, LocProcess(patterns));  MacroSubstitute(expression,\"LocPredicate\",\"LocChange\");];";
        scriptMap.put("/:",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " 10 # CNF( Not? True) <-- False;11 # CNF( Not? False) <-- True;12 # CNF(True And? (_x)) <-- CNF(x);13 # CNF(False And? (_x)) <-- False;14 # CNF(_x And? True) <-- CNF(x);15 # CNF(_x And? False) <-- False;16 # CNF(True Or? (_x)) <-- True;17 # CNF(False Or? (_x)) <-- CNF(x);18 # CNF((_x) Or? True ) <-- True;19 # CNF((_x) Or? False) <-- CNF(x);21 # CNF(_x Or? _x) <-- CNF(x);22 # CNF(_x And? _x) <-- CNF(x);23 # CNF(_x Or? Not? (_x)) <-- True;14 # CNF(Not? (_x) Or? _x) <-- True;25 # CNF(_x And? Not? (_x)) <-- False;26 # CNF(Not? (_x) And? _x) <-- False;25 # CNF(((_x) == (_y)) Or? ((_x) !== (_y))) <-- True;25 # CNF(((_x) !== (_y)) Or? ((_x) == (_y))) <-- True;26 # CNF(((_x) == (_y)) And? ((_x) !== (_y))) <-- False;26 # CNF(((_x) !== (_y)) And? ((_x) == (_y))) <-- False;27 # CNF(((_x) >=? (_y)) And? ((_x) <? (_y))) <-- False;27 # CNF(((_x) <? (_y)) And? ((_x) >=? (_y))) <-- False;28 # CNF(((_x) >=? (_y)) Or? ((_x) <? (_y))) <-- True;28 # CNF(((_x) <? (_y)) Or? ((_x) >=? (_y))) <-- True;120 # CNF((_x) Or? (_y)) <-- LogOr(x, y, CNF(x), CNF(y));10 # LogOr(_x,_y,_x,_y) <-- x Or? y;20 # LogOr(_x,_y,_u,_v) <-- CNF(u Or? v);130 # CNF( Not? (_x)) <-- LogNot(x, CNF(x));10 # LogNot(_x, _x) <-- Not? (x);20 # LogNot(_x, _y) <-- CNF(Not? (y));40 # CNF( Not? ( Not? (_x))) <-- CNF(x); 45 # CNF((_x) Implies? (_y)) <-- CNF((Not? (x)) Or? (y)); 50 # CNF( Not? ((_x) And? (_y))) <-- CNF((Not? x) Or? (Not? y)); 60 # CNF( Not? ((_x) Or? (_y))) <-- CNF(Not? (x)) And? CNF(Not? (y)); 70 # CNF(((_x) And? (_y)) Or? (_z)) <-- CNF(x Or? z) And? CNF(y Or? z); 80 # CNF((_x) Or? ((_y) And? (_z))) <-- CNF(x Or? y) And? CNF(x Or? z);90 # CNF((_x) And? (_y)) <-- CNF(x) And? CNF(y); 101 # CNF( (_x) <? (_y) ) <-- Not? CNFInEq(x >=? y);102 # CNF( (_x) >? (_y) ) <-- CNFInEq(x >? y);103 # CNF( (_x) >=? (_y) ) <-- CNFInEq(x >=? y);104 # CNF( (_x) <=? (_y) ) <-- Not? CNFInEq(x >? y);105 # CNF( (_x) == (_y) ) <-- CNFInEq(x == y);106 # CNF( (_x) !== (_y) ) <-- Not? CNFInEq(x == y);111 # CNF( Not?((_x) <? (_y)) ) <-- CNFInEq( x >=? y );113 # CNF( Not?((_x) <=? (_y)) ) <-- CNFInEq( x >? y );116 # CNF( Not?((_x) !== (_y)) ) <-- CNFInEq( x == y );200 # CNF(_x) <-- x;20 # CNFInEq((_xex) == (_yex)) <-- (CNFInEqSimplify(xex-yex) == 0);20 # CNFInEq((_xex) >? (_yex)) <-- (CNFInEqSimplify(xex-yex) >? 0);20 # CNFInEq((_xex) >=? (_yex)) <-- (CNFInEqSimplify(xex-yex) >=? 0);30 # CNFInEq(_exp) <-- (CNFInEqSimplify(exp));10 # CNFInEqSimplify((_x) - (_x)) <-- 0; 100# CNFInEqSimplify(_x) <-- [ x;]; ";
        scriptMap.put("CNF",scriptString);
        scriptMap.put("LogOr",scriptString);
        scriptMap.put("LogNot",scriptString);
        scriptMap.put("CNFInEq",scriptString);
        scriptMap.put("CNFInEqSimplify",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " CanProveAux(_proposition) <-- LogicSimplify(proposition, 3);CanProve(_proposition) <-- CanProveAux( proposition );";
        scriptMap.put("CanProve",scriptString);
        scriptMap.put("CanProveAux",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Contradict((_x) - (_y) == 0, (_x) - (_z) == 0)_(y !=? z) <-- True;12 # Contradict((_x) == (_y), (_x) == (_z))_(y !=? z) <-- True;13 # Contradict((_x) - (_y) == 0, (_x) - (_z) >=? 0)_(z >? y) <-- True;14 # Contradict((_x) - (_y) == 0, (_x) - (_z) >? 0)_(z >? y) <-- True;14 # Contradict(Not? (_x) - (_y) >=? 0, (_x) - (_z) >? 0)_(z >? y) <-- True;15 # Contradict(_a, _b) <-- Equal?(SimpleNegate(a), b);";
        scriptMap.put("Contradict",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "DoUnitSubsumptionAndResolution(_list) <--[ Local(i, j, k, isFalse, isTrue, changed); Bind(isFalse, False); Bind(isTrue, False); Bind(changed, True);  While(changed) [ Bind(changed, False); For(i:=1, (Not? isFalse And? Not? isTrue) And? i <=? Length(list), i++) [ If(Length(list[i]) =? 1, [ Local(x); Bind(x, list[i][1]);    For(j:=1, (Not? isFalse And? Not? isTrue) And? j <=? Length(list), j++) [ If(i !=?j, [ Local(deletedClause); Bind(deletedClause, False); For(k:=1, (Not? isFalse And? Not? isTrue And? Not? deletedClause) And? k <=? Length(list[j]), k++) [    If(Subsumes(x, list[j][k]), [  DestructiveDelete(list, j); j--; If(i>?j, i--);  Bind(deletedClause, True); Bind(changed, True); If(Length(list) =? 0, [Bind(isTrue, True);]); ],  If(Contradict(x, list[j][k]), [  DestructiveDelete(list[j], k); k--; Bind(changed, True); If(Length(list[j]) =? 0, [Bind(isFalse, True);]); ]) ); ]; ]); ]; ]); ]; ]; list;];";
        scriptMap.put("DoUnitSubsumptionAndResolution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LogicCombine(_list) <--[ Local(i, j); For(Bind(i,1), i<=?Length(list), Bind(i,AddN(i,1))) [  For(j := 1, (j<=?Length(list[i])), j++) [ Local(tocombine, n, k); Bind(n, list[i][j]); {tocombine, k} := LogicFindWith(list, i, n);  If(tocombine !=? -1, [ Local(combination); Check(k !=? -1, \"Math\", \"k is -1\"); Bind(combination, LogicRemoveTautologies(Concat(list[i], list[tocombine]))); If(combination =? {},  [Bind(list, {{}}); Bind(i, Length(list)+1);], []); ]); ]; ]; list;];";
        scriptMap.put("LogicCombine",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LogicFindWith(_list, _i, _n) <--[ Local(result, index, j); Bind(result, -1); Bind(index, -1); For(j := i+1, (result<?0) And? (j <=? Length(list)), j++) [ Local(k, len); Bind(len, Length(list[j])); For(k := 1, (result<?0) And? (k<=?len), k++) [ Local(el); Bind(el, list[j][k]); If(Contradict(n, el), [Bind(result, j); Bind(index, k);]); ]; ]; {result, index};];";
        scriptMap.put("LogicFindWith",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " 1 # SimpleNegate(Not? (_x)) <-- x;2 # SimpleNegate(_x) <-- Not?(x);LocalCmp(_e1, _e2) <-- LessThan?(PipeToString() Write(e1), PipeToString() Write(e2));100 # SimplifyExpression(_x) <-- x;LogicRemoveTautologies(_e) <--[ Local(i, len, negationfound); Bind(len, Length(e)); Bind(negationfound, False);  e := BubbleSort(e, \"LocalCmp\"); For(Bind(i, 1), (i <=? len) And? (Not? negationfound), i++) [ Local(x, n, j);   Bind(x, MathNth(e,i)); Bind(n, SimpleNegate(x));  For(Bind(j, i+1), (j <=? len) And? (Not? negationfound), j++) [ Local(y); Bind(y, MathNth(e,j)); If(Equal?(y, n), [  Bind(negationfound, True);  ], If(Equal?(y, x), [  DestructiveDelete(e, j); Bind(len,SubtractN(len,1)); ]) ); ]; Check(len =? Length(e), \"Math\", \"The length computation is incorrect\"); ]; If(negationfound, {True}, e); ];";
        scriptMap.put("LogicRemoveTautologies",scriptString);
        scriptMap.put("SimpleNegate",scriptString);
        scriptMap.put("LocalCmp",scriptString);
        scriptMap.put("SimplifyExpression",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " 10 # LogicSimplify(_proposition, _level)_(level<?2) <-- CNF(proposition);20 # LogicSimplify(_proposition, _level) <--[ Local(cnf, list, clauses); Check(level >? 1, \"Argument\", \"Wrong level\");  Bind(cnf, CNF(proposition)); If(level <=? 1, cnf, [ Bind(list, Flatten(cnf, \"And?\")); Bind(clauses, {}); ForEach(clause, list) [ Local(newclause);  Bind(newclause, LogicRemoveTautologies(Flatten(clause, \"Or?\"))); If(newclause !=? {True}, DestructiveAppend(clauses, newclause)); ];  Bind(clauses, RemoveDuplicates(clauses)); If(Equal?(level, 3) And? (Length(clauses) !=? 0), [ Bind(clauses, DoUnitSubsumptionAndResolution(clauses)); Bind(clauses, LogicCombine(clauses)); ]); Bind(clauses, RemoveDuplicates(clauses)); If(Equal?(Length(clauses), 0), True, [  Local(result); Bind(result, True); ForEach(item,clauses) [ Bind(result, result And? UnFlatten(item, \"Or?\", False)); ]; result; ]); ]);];";
        scriptMap.put("LogicSimplify",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Subsumes((_x) - (_y) == 0, Not? ((_x) - (_z)==0))_(y!=?z) <-- True;20 # Subsumes((_x) - (_y) == 0, (_z) - (_x) >=? 0)_(z>=?y) <-- True;20 # Subsumes((_x) - (_y) == 0, (_z) - (_x) >? 0)_(z>?y) <-- True;30 # Subsumes((_x) - (_y) == 0, (_x) - (_z) >=? 0)_(y>=?z) <-- True;30 # Subsumes((_x) - (_y) == 0, (_x) - (_z) >? 0)_(y>?z) <-- True;90 # Subsumes((_x), (_x)) <-- True;100# Subsumes((_x), (_y)) <-- False;";
        scriptMap.put("Subsumes",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # DisassembleExpression( _expr ) <--[ Local(vars); vars := MultiExpressionList( expr ); DisassembleExpression( expr, vars );];10 # DisassembleExpression( _expr, vars_List? ) <--[ Local(mexpr,func,termList,result,powers,coeffs); mexpr := MakeMultiNomial(expr,vars); func := Lambda({x,y},If(y!=?0,DestructiveAppend(termList,{x,y}))); termList := {}; ScanMultiNomial(func,mexpr); result := Concat({vars},Transpose(termList));];";
        scriptMap.put("DisassembleExpression",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Groebner(f_List?) <--[ Local(vars,i,j,S,nr,r); nr:=Length(f); vars:=VarList(f); For(i:=1,i<=?nr,i++) [ f[i] := MakeMultiNomial(f[i],vars); ]; S:={}; For(i:=1,i<?nr,i++) For(j:=i+1,j<=?nr,j++) [ r := (MultiDivide(MultiS(f[i],f[j],f[i]),f)[2]); If(NormalForm(r) !=? 0, S:= r~S); f:=Concat(f,S); S:={}; nr:=Length(f); ]; MapSingle(\"NormalForm\",Concat(f));];";
        scriptMap.put("Groebner",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ListTerms(_expr) <--[ If(InVerboseMode(),Tell(\"ListTerms\",expr)); Local(termList); Local(op,x2,x3); termList := {}; If(Function?(expr), [ {op,x2,x3} := FunctionToList(expr); If(InVerboseMode(),Tell(\" \",{op,x2,x3}));  terms(op,x2,x3); ], [ Push(termList,expr); ] ); termList;];10 # terms(_op,_x2,_x3)_(op=?ToAtom(\"+\") Or? op=?ToAtom(\"-\")) <--[ If(InVerboseMode(),[Tell(\" terms10\",op);Tell(\" \",{x2,x3});]); Local(sgn); If(op=?ToAtom(\"+\"),sgn:=1,sgn:=-1); Push(termList,sgn*x3); If(InVerboseMode(),Tell(\" \",termList)); If(Function?(x2), [ Local(L); L := FunctionToList(x2); If(InVerboseMode(),Tell(\" \",L)); If(Length(L)=?3,terms(L[1],L[2],L[3]),Push(termList,x2)); ], [ Push(termList,x2); ] );];UnFence(\"terms\",3);20 # terms(_op,_x2,_x3) <--[ If(InVerboseMode(),[Tell(\" terms20\",op);Tell(\" \",{x2,x3});]); Local(F); F := ListToFunction({op,x2,x3}); Push(termList,F); If(InVerboseMode(),Tell(\" \",termList)); termList;];UnFence(\"terms\",3);";
        scriptMap.put("ListTerms",scriptString);
        scriptMap.put("terms",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MM(_expr) <-- MM(expr,MultiExpressionList(expr));MM(_expr,_vars) <-- MakeMultiNomial(expr,vars);";
        scriptMap.put("MM",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MultiDivTerm(MultiNomial(_vars,_term1),MultiNomial(_vars,_term2)) <--[ Local(lm1,lm2); Bind(lm1,MultiLeadingTerm(MultiNomial(vars,term1)) ); Bind(lm2,MultiLeadingTerm(MultiNomial(vars,term2)) ); CreateTerm(vars,{lm1[1]-lm2[1],lm1[2] / lm2[2]});];MultiS(_g,_h,MultiNomial(_vars,_terms)) <--[ Local(gamma); gamma :=Maximum(MultiDegree(g),MultiDegree(h)); Local(result,topterm); topterm := MM(Product(vars^gamma)); result := MultiDivTerm(topterm,MultiLT(g))*g - MultiDivTerm(topterm,MultiLT(h))*h; result;];";
        scriptMap.put("MultiDivTerm",scriptString);
        scriptMap.put("MultiS",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "20 # MultiDivide(_f,g_List?) <--[ If(InVerboseMode(),Tell(\"MultiDivide_1\",{f,g})); Local(i,v,q,r,nr); v := MultiExpressionList(f+Sum(g)); f := MakeMultiNomial(f,v); nr := Length(g); For(i:=1,i<=?nr,i++) [ g[i] := MakeMultiNomial(g[i],v); ]; If(Not? Multi?(f),Break()); {q,r} := MultiDivide(f,g); q := MapSingle(\"NormalForm\",q); r := NormalForm(r); {q,r};];10 # MultiDivide(f_Multi?,g_List?) <--[ If(InVerboseMode(),Tell(\"MultiDivide_2\",{f,g})); Local(i,nr,q,r,p,v,finished); nr := Length(g); v := MultiVars(f); q := FillList(0,nr); r := 0; p := f; finished := MultiZero(p); Local(plt,glt); While (Not? finished) [ plt := MultiLT(p);  For(i:=1,i<=?nr,i++) [ glt := MultiLT(g[i]); if (MultiLM(glt) =? MultiLM(plt) Or? MultiTermLess({MultiLM(glt),1}, {MultiLM(plt),1})) [  if (Select(MultiLM(plt)-MultiLM(glt),{{n},n<?0}) =? {} ) [ Local(ff,ltbefore,ltafter); ff := CreateTerm(v,{MultiLM(plt)-MultiLM(glt),MultiLC(plt)/MultiLC(glt)}); If(InVerboseMode(),Tell(\" \",NormalForm(ff))); q[i] := q[i] + ff; ltbefore := MultiLeadingTerm(p); p := p - ff*g[i]; ltafter := MultiLeadingTerm(p); if (ltbefore[1] =? ltafter[1]) [ ltafter := MultiLT(p); p := p-ltafter; ]; i := nr + 2; ]; ]; ]; If (i =? nr + 1, [ r := r + LocalSymbols(a,b)(Subst(a,b)plt); p := p - LocalSymbols(a,b)(Subst(a,b)plt); ] ); finished := MultiZero(p); ]; {q,r};];";
        scriptMap.put("MultiDivide",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # MultiGcd( 0,_g) <-- g;10 # MultiGcd(_f, 0) <-- f;20 # MultiGcd(_f,_g) <--[ Local(v); v:=MultiExpressionList(f+g);  NormalForm(MultiGcd(MakeMultiNomial(f,v),MakeMultiNomial(g,v)));];5 # MultiGcd(f_Multi?,g_Multi?)_(MultiTermLess({MultiLM(f),1},{MultiLM(g),1})) <--[ MultiGcd(g,f);];5 # MultiGcd(MultiNomial(_vars,_terms),g_Multi?)_(MultiLM(MultiNomial(vars,terms)) =? MultiLM(g)) <-- CreateTerm(vars,{FillList(0,Length(vars)),1});5 # MultiGcd(MultiNomial(_vars,_terms),g_Multi?)_( Select(MultiLM(MultiNomial(vars,terms))-MultiLM(g), {{n},n<?0} ) !=? {}) <-- CreateTerm(vars,{FillList(0,Length(vars)),1});5 # MultiGcd(MultiNomial(_vars,_terms),g_Multi?)_(NormalForm(g) =? 0) <-- CreateTerm(vars,{FillList(0,Length(vars)),1});10 # MultiGcd(f_Multi?,g_Multi?) <--[ LocalSymbols(a) [ Bind(f,Subst(a,a)f); Bind(g,Subst(a,a)g); ]; Local(new); While(g !=? 0) [ Bind(new, MultiDivide(f,{g}));If(new[1][1]=?0,[ g:=MakeMultiNomial(1,MultiVars(f)); new[2]:=0;]); Bind(new, new[2]); Bind(f,g); Bind(g,new); ]; MultiPrimitivePart(f);];";
        scriptMap.put("MultiGcd",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"MultiNomial\",{vars,terms});";
        scriptMap.put("MultiNomial",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MultiSimp(_expr) <--[ Local(vars); vars:=MultiExpressionList(expr); MultiSimp2(MM(expr,vars));];10 # MultiSimp2(_a / _b) <--[ Local(c1,c2,gcd,cmn,vars); c1 := MultiContentTerm(a); c2 := MultiContentTerm(b); gcd:=Gcd(c1[2],c2[2]); c1[2] := c1[2]/gcd; c2[2] := c2[2]/gcd; cmn:=Minimum(c1[1],c2[1]); c1[1] := c1[1] - cmn; c2[1] := c2[1] - cmn; vars:=MultiVars(a); Check(vars =? MultiVars(b), \"Argument\", \"incompatible Multivars to simplify\"); (NormalForm(CreateTerm(vars,c1))/NormalForm(CreateTerm(vars,c2))) *(NormalForm(MultiPrimitivePart(a))/NormalForm(MultiPrimitivePart(b)));];20 # MultiSimp2(expr_Multi?) <--[ NormalForm(MultiContent(expr))*NormalForm(MultiPrimitivePart(expr));];30 # MultiSimp2(_expr) <-- expr;MultiContent(multi_Multi?)<--[ Local(least,gcd); Bind(least, MultiDegree(multi)); Bind(gcd,MultiLeadingCoef(multi)); ScanMultiNomial(\"MultiContentScan\",multi); CreateTerm(MultiVars(multi),MultiContentTerm(multi));];MultiContentTerm(multi_Multi?)<--[ Local(least,gcd); Bind(least, MultiDegree(multi)); Bind(gcd,MultiLeadingCoef(multi)); ScanMultiNomial(\"MultiContentScan\",multi); {least,gcd};];MultiContentScan(_coefs,_fact) <--[ Bind(least,Minimum({least,coefs})); Bind(gcd,Gcd(gcd,fact));];UnFence(\"MultiContentScan\",2);MultiPrimitivePart(MultiNomial(vars_List?,_terms))<--[ Local(cont); Bind(cont,MultiContentTerm(MultiNomial(vars,terms))); Bind(cont,CreateTerm(vars,{-cont[1],1/Rationalize(cont[2])})); MultiNomialMultiply(MultiNomial(vars,terms), cont);];10 # MultiRemoveGcd(x_Multi?/y_Multi?) <--[ Local(gcd); Bind(gcd,MultiGcd(x,y)); Bind(x,MultiDivide(x,{gcd})[1][1]); Bind(y,MultiDivide(y,{gcd})[1][1]); x/y;];20 # MultiRemoveGcd(_x) <-- x;5 # MultiDegree(MultiNomial(_vars,_term))_(Not?(List?(term))) <-- {};10 # MultiDegree(MultiNomial(_vars,{})) <-- FillList(-Infinity,Length(vars));20 # MultiDegree(MultiNomial(_vars,_terms)) <-- (MultiLeadingTerm(MultiNomial(vars,terms))[1]);10 # MultiLeadingCoef(MultiNomial(_vars,_terms)) <-- (MultiLeadingTerm(MultiNomial(vars,terms))[2]);10 # MultiLeadingMono(MultiNomial(_vars,{})) <-- 0;20 # MultiLeadingMono(MultiNomial(_vars,_terms)) <-- Product(vars^(MultiDegree(MultiNomial(vars,terms))));20 # MultiLeadingTerm(_m) <-- MultiLeadingCoef(m) * MultiLeadingMono(m);MultiVars(MultiNomial(_vars,_terms)) <-- vars;20 # MultiLT(multi_Multi?) <-- CreateTerm(MultiVars(multi),MultiLeadingTerm(multi));10 # MultiLM(multi_Multi?) <-- MultiDegree(multi);10 # MultiLC(MultiNomial(_vars,{})) <-- 0;20 # MultiLC(multi_Multi?) <-- MultiLeadingCoef(multi);DropZeroLC(multi_Multi?) <-- MultiDropLeadingZeroes(multi);";
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

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ReassembleListTerms( disassembly_List? ) <--[ Local(vars,lst,powers,coeffs,ii,pows,coef,term); vars := disassembly[1]; powers := disassembly[2]; coeffs := disassembly[3]; lst := {}; For(ii:=1,ii<=?Length(powers),ii++) [ pows := powers[ii]; coef := coeffs[ii];  term := coef*Product(vars^pows);  DestructiveAppend(lst,term); ]; lst;];";
        scriptMap.put("ReassembleListTerms",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MultiExpressionList(_expr) <-- VarList(expr,\"MultiExpression?\");10 # MultiExpression?(_x + _y) <-- False;10 # MultiExpression?(_x - _y) <-- False;10 # MultiExpression?( - _y) <-- False;10 # MultiExpression?(_x * _y) <-- False;10 # MultiExpression?(_x / _y) <-- False;10 # MultiExpression?(_x ^ y_PositiveInteger?) <-- False;11 # MultiExpression?(_x ^ _y)_(PositiveInteger?(Simplify(y))) <-- False;10 # MultiExpression?(x_MultiConstant?) <-- False;MultiConstant?(_n) <-- (VarList(n,\"IsVr\")=?{});10 # IsVr(n_Number?) <-- False;10 # IsVr(n_Function?) <-- False;10 # IsVr(n_String?) <-- False;20 # IsVr(_n) <-- True;100 # MultiExpression?(_x) <-- True;10 # Multi?(MultiNomial(vars_List?,_terms)) <-- True;20 # Multi?(_anything) <-- False;LocalSymbols(a,vars,pow) [ 20 # MultiSingleFactor(_vars,_a,_pow) <-- [ Local(term); term:={FillList(0,Length(vars)),1}; term[1][Find(vars,a)] := pow; CreateTerm(vars,term); ];];LocalSymbols(x,y,vars) [10 # MakeMultiNomial(x_MultiConstant?,vars_List?) <-- CreateTerm(vars,{FillList(0,Length(vars)),x});20 # MakeMultiNomial(_x,vars_List?)_(Contains?(vars,x)) <-- MultiSingleFactor(vars,x,1);30 # MakeMultiNomial(_x + _y,vars_List?) <-- MakeMultiNomial(x,vars) + MakeMultiNomial(y,vars);30 # MakeMultiNomial(_x * _y,vars_List?) <-- MakeMultiNomial(x,vars) * MakeMultiNomial(y,vars);30 # MakeMultiNomial(- _x,vars_List?) <-- -MakeMultiNomial(x,vars);30 # MakeMultiNomial(_x - _y,vars_List?) <-- MakeMultiNomial(x,vars) - MakeMultiNomial(y,vars);30 # MakeMultiNomial(MultiNomial(_vars,_terms),vars_List?) <-- MultiNomial(vars,terms);100 # MakeMultiNomial(_x,vars_List?) <-- [ CreateTerm(vars,{FillList(0,Length(vars)),x}); ];];LocalSymbols(x,y,z,vars,gcd,a,a) [ 20 # MakeMultiNomial(_x / (_y / _z),vars_List?) <-- MakeMultiNomial((x*z) / y,vars_List?); 20 # MakeMultiNomial((_x / _y) / _z,vars_List?) <-- MakeMultiNomial((x*z) / y,vars_List?); 25 # MakeMultiNomial(_x / y_Constant?,vars_List?) <-- MakeMultiNomial(1/y,vars)*MakeMultiNomial(x,vars); 30 # MakeMultiNomial(_x / _y,vars_List?) <-- [ Local(result); Bind(result,MultiRemoveGcd(MakeMultiNomial(x,vars)/MakeMultiNomial(y,vars))); result; ]; ];MultiNomial(_vars,_x) + MultiNomial(_vars,_y) <-- MultiNomialAdd(MultiNomial(vars,x), MultiNomial(vars,y));MultiNomial(_vars,_x) * MultiNomial(_vars,_y) <-- MultiNomialMultiply(MultiNomial(vars,x), MultiNomial(vars,y));MultiNomial(_vars,_x) - MultiNomial(_vars,_y) <-- MultiNomialAdd(MultiNomial(vars,x), MultiNomialNegate(MultiNomial(vars,y))); - MultiNomial(_vars,_y) <-- MultiNomialNegate(MultiNomial(vars,y));MultiNomial(_vars,_x) / MultiNomial(_vars,_x) <-- MakeMultiNomial(1, vars);LocalSymbols(x,n,vars) [30 # MakeMultiNomial(_x ^ n_Integer?,vars_List?)_(Contains?(vars,x)) <-- MultiSingleFactor(vars,x,n);40 # MakeMultiNomial(_x ^ n_PositiveInteger?,vars_List?) <-- [ Local(mult,result); Bind(mult,MakeMultiNomial(x,vars)); Bind(result,MakeMultiNomial(1,vars)); While(n>?0) [ If(n&1 !=? 0, Bind(result, MultiNomialMultiply(result,mult))); Bind(n,n>>1); If(n!=?0,Bind(mult,MultiNomialMultiply(mult,mult))); ]; result; ]; 15 # MakeMultiNomial(_x ^ _n,vars_List?)_(Not?(Integer?(n)) And? Integer?(Simplify(n))) <-- MakeMultiNomial( x ^ Simplify(n),vars); 50 # MakeMultiNomial(_x ^ (_n),vars_List?)_(Contains?(vars,x)) <-- [ Bind(n,Simplify(n)); If(Integer?(n), MultiSingleFactor(vars,x,n), MultiSingleFactor(vars,x^n,1) ); ];];x_Multi? + (y_Multi?/z_Multi?) <-- ((x*z+y)/z);(y_Multi?/z_Multi?) + x_Multi? <-- ((x*z+y)/z);(y_Multi?/z_Multi?) + (x_Multi?/w_Multi?) <-- ((y*w+x*z)/(z*w));(y_Multi?/z_Multi?) - (x_Multi?/w_Multi?) <-- ((y*w-x*z)/(z*w));(y_Multi?/z_Multi?) * (x_Multi?/w_Multi?) <-- ((y*x)/(z*w));(y_Multi?/z_Multi?) / (x_Multi?/w_Multi?) <-- ((y*w)/(z*x));x_Multi? - (y_Multi?/z_Multi?) <-- ((x*z-y)/z);(y_Multi?/z_Multi?) - x_Multi? <-- ((y-x*z)/z);(a_Multi?/(c_Multi?/b_Multi?)) <-- ((a*b)/c);((a_Multi?/c_Multi?)/b_Multi?) <-- (a/(b*c));((a_Multi?/b_Multi?) * c_Multi?) <-- ((a*c)/b);(a_Multi? * (c_Multi?/b_Multi?)) <-- ((a*c)/b);- ((a_Multi?)/(b_Multi?)) <-- (-a)/b;MultiNomialMultiply( MultiNomial(_vars,_terms1)/MultiNomial(_vars,_terms2), MultiNomial(_vars,_terms3)/MultiNomial(_vars,_terms4)) <--[ MultiNomialMultiply(MultiNomial(vars,terms1),MultiNomial(vars,terms3))/ MultiNomialMultiply(MultiNomial(vars,terms2),MultiNomial(vars,terms4));];MultiNomialMultiply( MultiNomial(_vars,_terms1)/MultiNomial(_vars,_terms2), MultiNomial(_vars,_terms3)) <--[ MultiNomialMultiply(MultiNomial(vars,terms1),MultiNomial(vars,terms3))/ MultiNomial(vars,terms2);];MultiNomialMultiply( MultiNomial(_vars,_terms3), MultiNomial(_vars,_terms1)/MultiNomial(_vars,_terms2)) <--[ MultiNomialMultiply(MultiNomial(vars,terms1),MultiNomial(vars,terms3))/ MultiNomial(vars,terms2);];10 # MultiNomialMultiply(_a,_b) <--[ Echo({\"ERROR!\",a,b}); Echo({\"ERROR!\",Type(a),Type(b)});];";
        scriptMap.put("MakeMultiNomial",scriptString);
        scriptMap.put("MultiExpressionList",scriptString);
        scriptMap.put("MultiExpression?",scriptString);
        scriptMap.put("MultiConstant?",scriptString);
        scriptMap.put("IsVr",scriptString);
        scriptMap.put("MultiSingleFactor",scriptString);
        scriptMap.put("Multi?",scriptString);
        scriptMap.put("MultiNomialMultiply",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(NormalMultiNomial) [CreateTerm(_vars,{_coefs,_fact}) <-- MultiNomial(vars,CreateSparseTree(coefs,fact));MultiNomialAdd(MultiNomial(_vars,_x), MultiNomial(_vars,_y)) <-- MultiNomial(vars,AddSparseTrees(Length(vars),x,y));MultiNomialMultiplyAdd(MultiNomial(_vars,_x), MultiNomial(_vars,_y),_coefs,_fact) <-- MultiNomial(vars,MultiplyAddSparseTrees(Length(vars),x,y,coefs,fact));MultiNomialNegate(MultiNomial(_vars,_terms)) <-- [ SparseTreeMap(Hold({{coefs,list},-list}),Length(vars),terms); MultiNomial(vars,terms); ];MultiNomialMultiply(MultiNomial(_vars,_x),_multi2) <-- [ Local(result); Bind(result,MakeMultiNomial(0,vars)); SparseTreeScan(\"muadm\",Length(vars),x); result; ];muadm(_coefs,_fact) <--[ Bind(result,MultiNomialMultiplyAdd(result, multi2,coefs,fact));];UnFence(\"muadm\",2);RulebaseHoldArguments(\"NormalForm\",{expression});RuleHoldArguments(\"NormalForm\",1,1000,True) expression;0 # NormalForm(UniVariate(_var,_first,_coefs)) <-- ExpandUniVariate(var,first,coefs);10 # NormalForm(x_Multi?/y_Multi?) <-- NormalForm(x)/NormalForm(y);20 # NormalForm(MultiNomial(_vars,_list) ) <-- NormalMultiNomial(vars,list,1);10 # NormalMultiNomial({},_term,_prefact) <-- prefact*term;20 # NormalMultiNomial(_vars,_list,_prefact) <-- [ Local(first,rest,result); Bind(first,First(vars)); Bind(rest,Rest(vars)); Bind(result,0); ForEach(item,list) [ Bind(result,result+NormalMultiNomial(rest,item[2],prefact*first^(item[1]))); ]; result; ];]; MultiLeadingTerm(MultiNomial(_vars,_terms)) <-- [ Local(coefs,fact); Bind(coefs,MultiDegreeScanHead(terms,Length(vars))); {coefs,fact}; ];10 # MultiDegreeScanHead(_tree,0) <-- [ Bind(fact,tree); {}; ];10 # MultiDegreeScanHead(_tree,1) <-- [ Bind(fact,tree[1][2]); {tree[1][1]}; ];20 # MultiDegreeScanHead(_tree,_depth) <-- [ (tree[1][1])~MultiDegreeScanHead(tree[1][2],depth-1); ];UnFence(\"MultiDegreeScanHead\",2);ScanMultiNomial(_op,MultiNomial(vars_List?,_terms)) <-- SparseTreeScan(op,Length(vars),terms);UnFence(\"ScanMultiNomial\",2);MultiDropLeadingZeroes(MultiNomial(_vars,_terms)) <-- [ MultiDropScan(terms,Length(vars)); MultiNomial(vars,terms); ];10 # MultiDropScan(0,0) <-- True;10 # MultiDropScan({_n,0},0) <-- True;20 # MultiDropScan(_n,0) <-- [ False; ];30 # MultiDropScan(_tree,_depth) <-- [ Local(i); For(i:=1,i<=?Length(tree),i++) [ if (MultiDropScan(tree[i][2],depth-1)) [ DestructiveDelete(tree,i); i--; ] else [ i:=Length(tree); ]; ]; (tree =? {}); ];UnFence(\"MultiDropScan\",2);MultiTermLess({_deg1,_fact1},{_deg2,_fact2}) <-- [ Local(deg); Bind(deg, deg1-deg2); While(deg !=? {} And? First(deg) =? 0) [ Bind(deg, Rest(deg));]; ((deg =? {}) And? (fact1-fact2 <? 0)) Or? ((deg !=? {}) And? (deg[1] <? 0)); ];20 # MultiZero(multi_Multi?) <--[ CheckMultiZero(DropZeroLC(multi));];10 # CheckMultiZero(MultiNomial(_vars,{})) <-- True;20 # CheckMultiZero(MultiNomial(_vars,_terms)) <-- False;";
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

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # SparseTreeGet({},_tree) <-- tree;20 # SparseTreeGet(_key,_tree) <--[ SparseTreeGet2(Rest(key),Assoc(First(key),tree));];10 # SparseTreeGet2(_key,Empty) <-- 0;20 # SparseTreeGet2(_key,_item) <-- SparseTreeGet(key,First(Rest(item)));10 # SparseTreeSet({_i},_tree,_newvalue) <--[ Local(Current,assoc,result); Bind(assoc,Assoc(i,tree)); if(assoc=?Empty) [ Bind(Current,0); Bind(result,Eval(newvalue)); AddSparseTrees(1,tree,CreateSparseTree({i},result)); ] else [ Bind(Current,assoc[2]); Bind(result,Eval(newvalue)); assoc[2] := result; ]; result;];20 # SparseTreeSet(_key,_tree,_newvalue) <--[ SparseTreeSet2(Rest(key),Assoc(First(key),tree));];10 # SparseTreeSet2(_key,Empty) <-- 0;20 # SparseTreeSet2(_key,_item) <-- SparseTreeSet(key,First(Rest(item)),newvalue);UnFence(\"SparseTreeSet\",3);UnFence(\"SparseTreeSet2\",2);LocalSymbols(SparseTreeMap2,SparseTreeScan2,Muaddterm,MuMuaddterm, meradd,meraddmap) [10 # CreateSparseTree({},_fact) <-- fact;20 # CreateSparseTree(_coefs,_fact) <-- CreateSparseTree(First(coefs),Rest(coefs),fact);10 # CreateSparseTree(_first,{},_fact) <-- {{first,fact}};20 # CreateSparseTree(_first,_coefs,_fact) <-- {{first,CreateSparseTree(First(coefs),Rest(coefs),fact)}};10 # SparseTreeMap(_op,_depth,_list) <-- SparseTreeMap2(list,depth,{});10 # SparseTreeMap2(_list,1,_coefs) <-- ForEach(item,list) [ item[2] := ApplyFast(op,{Concat(coefs,{item[1]}),item[2]}); ];20 # SparseTreeMap2(_list,_depth,_coefs) <-- ForEach(item,list) [ SparseTreeMap2(item[2],AddN(depth,-1),Concat(coefs,{item[1]})); ];UnFence(\"SparseTreeMap\", 3);[Local(fn);fn:=ToString(SparseTreeMap2);`UnFence(@fn,3);];10 # SparseTreeScan(_op,_depth,_list) <-- SparseTreeScan2(list,depth,{});10 # SparseTreeScan2(_list,0,_coefs) <-- ApplyFast(op,{coefs,list});20 # SparseTreeScan2(_list,_depth,_coefs) <-- ForEach(item,list) [ SparseTreeScan2(item[2],AddN(depth,-1),Concat(coefs,{item[1]})); ];UnFence(\"SparseTreeScan\", 3);[Local(fn);fn:=ToString(SparseTreeScan2);`UnFence(@fn,3);];5 # AddSparseTrees(0,_x,_y) <-- x+y;10 # AddSparseTrees(_depth,_x,_y) <--[ Local(i,t1,t2,inspt); Bind(t1,x); Bind(i,1); Bind(t2,y); Bind(inspt,{}); While(t1 !=? {} And? t2 !=? {}) [ Muaddterm(First(t1),First(t2)); ]; While(t2 !=? {}) [ Bind(x,DestructiveAppend(x,First(t2))); Bind(t2,Rest(t2)); ]; While(inspt !=? {}) [ Bind(i,First(inspt)); Bind(x,DestructiveInsert(x,i[2],i[1])); Bind(inspt,Rest(inspt)); ]; x;];10 # Muaddterm({_pow,_list1},{_pow,_list2}) <--[ if(depth=?1) [ t1[1][2] := list1+list2; ] else [ t1[1][2] := AddSparseTrees(AddN(depth,-1),list1,list2);]; Bind(t2,Rest(t2));];20 # Muaddterm(_h1,_h2)_(h1[1]<?h2[1]) <--[ Bind(inspt,{h2,i}~inspt); Bind(t2,Rest(t2));];30 # Muaddterm(_h1,_h2)<--[ Bind(t1,Rest(t1)); Bind(i,AddN(i,1));];[Local(fn);fn:=ToString(Muaddterm);`UnFence(@fn,2);];5 # MultiplyAddSparseTrees(0,_x,_y,{},_fact) <-- x+fact*y;10 # MultiplyAddSparseTrees(_depth,_x,_y,_coefs,_fact) <--[ Local(i,t1,t2,inspt,term); Bind(t1,x); Bind(i,1); Bind(t2,y); Bind(inspt,{}); While(t1 !=? {} And? t2 !=? {}) [ MuMuaddterm(First(t1),First(t2),coefs); ]; While(t2 !=? {}) [ Bind(term,First(t2)); Bind(x,DestructiveAppend(x,meradd(First(t2),coefs))); Bind(t2,Rest(t2)); ]; While(inspt !=? {}) [ Bind(i,First(inspt)); Bind(x,DestructiveInsert(x,i[2],i[1])); Bind(inspt,Rest(inspt)); ]; x;];10 # meradd({_ord,rest_List?},_coefs) <--[ Local(head); Bind(head,First(coefs)); Bind(coefs,Rest(coefs)); {ord+head,meraddmap(rest,coefs)};];20 # meradd({_ord,_rest},_coefs) <--[ {ord+First(coefs),rest*fact};];10 # meraddmap(list_List?,_coefs) <--[ Local(result); Bind(result,{}); ForEach(item,list) [ DestructiveAppend(result,meradd(item,coefs)); ]; result;];[Local(fn);fn:=ToString(meradd);`UnFence(@fn,2);];[Local(fn);fn:=ToString(meraddmap);`UnFence(@fn,2);];10 # MuMuaddterm({_pow1,_list1},{_pow2,_list2},_coefs)_(pow1=?pow2+coefs[1]) <--[ if(depth=?1) [ t1[1][2] := list1+fact*list2; ] else [ t1[1] := {pow1,MultiplyAddSparseTrees(AddN(depth,-1),list1,list2,Rest(coefs),fact)}; ]; Bind(t2,Rest(t2));];20 # MuMuaddterm(_h1,_h2,_coefs)_(h1[1]<?h2[1]+coefs[1]) <--[ Bind(inspt,{meradd(First(t2),coefs),i}~inspt); Bind(t2,Rest(t2));];30 # MuMuaddterm(_h1,_h2,_coefs)<--[ Bind(t1,Rest(t1)); Bind(i,AddN(i,1));];[Local(fn);fn:=ToString(MuMuaddterm);`UnFence(@fn,3);];]; ";
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

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BracketRational(r,eps):=[ Local(n,cflist, r1, r2); cflist := ContFracList(r); n:=2; r1 := ContFracEval(Take(cflist,n)); r2 := -r1;  While (n<?Length(cflist) And? ( Abs(N(Eval(r2-r1))) >? Abs(N(Eval(eps*r)) ) ) ) [ r2 := r1; n++; r1 := ContFracEval(Take(cflist,n)); ];   If( n=?Length(cflist), {},  If(N(Eval(r-r1))>?0, {r1, r2},  {r2, r1} ) );];";
        scriptMap.put("BracketRational",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"CharacteristicEquation\",{matrix,var}) SymbolicDeterminant(matrix-var*Identity(Length(matrix)));HoldArgument(\"CharacteristicEquation\",var);";
        scriptMap.put("CharacteristicEquation",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ContFrac(_n) <-- ContFrac(n, 6);50 # ContFrac(_n,_depth) <-- ContFracEval(ContFracList(n, depth), rest);40 # ContFrac(n_CanBeUni,_depth)_(Length(VarList(n)) =? 1) <--[ ContFracDoPoly(n,depth,VarList(n)[1]);];5 # ContFracDoPoly(_exp,0,_var) <-- rest;5 # ContFracDoPoly(0,0,_var) <-- rest;10 # ContFracDoPoly(_exp,_depth,_var) <--[ Local(content,exp2,first,second); first:=Coef(exp,var,0); exp:=exp-first; content:=Content(exp); exp2:=DivPoly(1,PrimitivePart(exp),var,5+3*depth)-1; second:=Coef(exp2,0); exp2 := exp2 - second; first+content/((1+second)+ContFracDoPoly(exp2,depth-1,var));];";
        scriptMap.put("ContFrac",scriptString);
        scriptMap.put("ContFracDoPoly",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ContFracEval({}, _rest) <-- rest;10 # ContFracEval({{_n, _m}}, _rest) <-- n+m+rest;15 # ContFracEval({_n}, _rest) <-- n+rest;20 # ContFracEval(list_List?, _rest)_(List?(First(list))) <-- First(First(list)) + Rest(First(list)) / ContFracEval(Rest(list), rest);30 # ContFracEval(list_List?, _rest) <-- First(list) + 1 / ContFracEval(Rest(list), rest);ContFracEval(list_List?) <-- ContFracEval(list, 0);";
        scriptMap.put("ContFracEval",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ContFracList(_n) <-- ContFracList(n, Infinity);10 # ContFracList(_n, _depth)_(depth <=? 0) <-- {};20 # ContFracList(n_Integer?, _depth) <-- {n};30 # ContFracList(n_Number?, _depth) _InNumericMode() <-- NonN(ContFracList(Rationalize(n), depth));40 # ContFracList(n_Number?, _depth) <-- ContFracList(Rationalize(n), depth);35 # ContFracList((n_NegativeInteger?) / (m_Integer?), _depth) <-- Push( ContFracList(m/Modulo(n,m), depth-1) , Quotient(n,m)-1);40 # ContFracList((n_Integer?) / (m_Integer?), _depth) <-- Push( ContFracList(m/Modulo(n,m), depth-1) , Quotient(n,m));";
        scriptMap.put("ContFracList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Decimal( n_Integer? ) <-- {n,{0}};10 # Decimal( (n_PositiveInteger?) / (d_PositiveInteger?) ) <--[ Local(result,rev,first,period,repeat,static); result:={Quotient(n,d)}; Decimal(result,Modulo(n,d),d,350); rev:=DecimalFindPeriod(result); first:=rev[1]; period:=rev[2]; repeat:=result[first .. (first+period-1)]; static:=result[1 .. (first-1)]; DestructiveAppend(static,repeat);];20 # Decimal(_n/_m)_((n/m)<?0) <-- \"-\"~Decimal(-n/m);10 # Decimal(_result , _n , _d,_count ) <--[ While(count>?0) [ DestructiveAppend(result,Quotient(10*n,d)); n:=Modulo(10*n,d); count--; ];];DecimalFindPeriod(_list) <--[ Local(period,nr,reversed,first,i); reversed:=Rest(DestructiveReverse(FlatCopy(Rest(list)))); nr:=Length(reversed)>>1; period:=1; first:=reversed[1]; For(i:=1,i<?nr,i++) [ If(reversed[i+1] =? first And? DecimalMatches(reversed,i), [ period:=i; i:=nr; ] ); ]; first:=Length(list)-period; While(first>?1 And? list[first] =? list[first+period]) first--; first++; {first,period};];DecimalMatches(_reversed,_period) <--[ Local(nr,matches,first); nr:=0; matches:=True; first:=1; While((nr<?100) And? matches) [ matches := (matches And? (reversed[first .. (first+period-1)] =? reversed[(first+period) .. (first+2*period-1)])); first:=first+period; nr:=nr+period; ]; matches;];";
        scriptMap.put("Decimal",scriptString);
        scriptMap.put("DecimalFindPeriod",scriptString);
        scriptMap.put("DecimalMatches",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # EigenValues(matrix_UpperTriangular?) <-- Diagonal(matrix);10 # EigenValues(matrix_LowerTriangular?) <-- Diagonal(matrix);20 # EigenValues(matrix_Matrix?) <-- Roots(CharacteristicEquation(matrix,xx));";
        scriptMap.put("EigenValues",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "EigenVectors(_matrix,_eigenvalues) <--[ Local(result,n); n:=Length(eigenvalues); result:={}; ForEach(e,eigenvalues) [ Local(possible); possible:=OldSolve(matrix*MakeVector(k,n)==e*MakeVector(k,n),MakeVector(k,n))[1]; If(Not?(ZeroVector?(possible)), DestructiveAppend(result,possible) ); ]; result;];";
        scriptMap.put("EigenVectors",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1 # FreeOf?({},_expr) <-- True;2 # FreeOf?(var_List?, _expr) <-- And?(FreeOf?(First(var),expr), FreeOf?(Rest(var),expr));4 # FreeOf?(_var,{}) <-- True;5 # FreeOf?(_var,expr_List?) <-- And?(FreeOf?(var,First(expr)), FreeOf?(var,Rest(expr)));10 # FreeOf?(_expr,_expr) <-- False;11 # FreeOf?(_var,expr_Function?) <-- FreeOf?(var,Rest(FunctionToList(expr)));12 # FreeOf?(_var,_expr) <-- True;";
        scriptMap.put("FreeOf?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "GuessRational(_x) <-- GuessRational(x, Floor(1/2*BuiltinPrecisionGet()));GuessRational(x_RationalOrNumber?, prec_Integer?) <-- [ Local(denomestimate, cf, i); denomestimate := 1; cf := ContFracList(x); For(i:=2, i<=?Length(cf) And? denomestimate <? 10^prec, i++) [  denomestimate := denomestimate * If( cf[i] =? 1, If( i+2<=?Length(cf),  RoundTo(N(Eval(cf[i]+1/(cf[i+1]+1/cf[i+2]))), 3),  RoundTo(N(Eval(cf[i]+1/cf[i+1])), 3) ),  cf[i] ); ]; If (denomestimate <? 10^prec,  i--  ); i--;   ContFracEval(Take(cf, i));];";
        scriptMap.put("GuessRational",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"InverseTaylor\",{var,val,degree,func})[ Local(l1); l1:=UniTaylor(func,var,val,degree); val+ReversePoly(l1,var,var,var,degree+1);];";
        scriptMap.put("InverseTaylor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LagrangeInt(_var,_list) <--[ Local(nr); nr:=Length(list); Product(FillList(var,nr)-list);];LagrangeInterpolant(list_List?,_values,_var) <--[ Local(i,nr,sublist); nr:=Length(list); result:=0; For(i:=1,i<=?nr,i++) [ sublist:=FlatCopy(list); DestructiveDelete(sublist,i); result:=result + values[i]*LagrangeInt(var,sublist)/LagrangeInt(list[i],sublist); ]; result;];";
        scriptMap.put("LagrangeInterpolant",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # NearRational(_x) <-- NearRational(x, Floor(1/2*BuiltinPrecisionGet()));15 # NearRational(x_RationalOrNumber?, prec_Integer?) <-- [ Local(x1, x2, i, oldprec); oldprec := BuiltinPrecisionGet(); BuiltinPrecisionSet(prec + 8);  x1 := ContFracList(N(Eval(x+10^(-prec)))); x2 := ContFracList(N(Eval(x-10^(-prec))));     For (i:=1, i<=?Length(x1) And? i<=?Length(x2) And? x1[i]=?x2[i], i++ ) True; If( i>?Length(x1),  x1:=x2, If( i>?Length(x2),  True,   x1[i]:=Minimum(x1[i],x2[i]) ) );    If(i>?Length(x1),i:=Length(x1)); x1[i] := x1[i] + If(i=?Length(x1), 0, 1); BuiltinPrecisionSet(oldprec); ContFracEval(Take(x1, i));];20 # NearRational(_z, prec_Integer?)_ (And?(Im(z)!=?0,RationalOrNumber?(Im(z)),RationalOrNumber?(Re(z)))) <--[ Local(rr,ii); rr := Re(z); ii := Im(z); Complex( NearRational(rr,prec), NearRational(ii,prec) );];";
        scriptMap.put("NearRational",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # NewLine() <-- WriteN(Nl(),1);20 # NewLine(n_PositiveInteger?) <-- WriteN(Nl(),n);30 # NewLine(_n) <-- Check(False, \"Argument\", \"The argument must be a positive integer \");";
        scriptMap.put("NewLine",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Nl():= UnicodeToString(10);";
        scriptMap.put("Nl",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ReversePoly(_f,_g,_var,_newvar,_degree) <--[ Local(orig,origg,G,V,W,U,n,initval,firstder,j,k,newsum); orig:=MakeUni(f,var); origg:=MakeUni(g,var); initval:=Coef(orig,0); firstder:=Coef(orig,1); V:=Coef(orig,1 .. Degree(orig)); V:=Concat(V,FillList(0,degree)); G:=Coef(origg,1 .. Degree(origg)); G:=Concat(G,FillList(0,degree)); W:=FillList(0,Length(V)+2); W[1]:=G[1]/firstder; U:=FillList(0,Length(V)+2); U[1]:=1/firstder; n:=1; While(n<?degree-1) [ n++; For(k:=0,k<?n-1,k++) [ newsum:=U[k+1]; For(j:=2,j<=?k+1,j++) [ newsum:=newsum-U[k+2-j]*V[j]; ]; U[k+1]:=newsum/firstder; ]; newsum:=0; For(k:=2,k<=?n,k++) [ newsum:=newsum - k*U[n+1-k]*V[k]; ]; U[n]:=newsum/firstder; newsum:=0; For(k:=1,k<=?n,k++) [ newsum:=newsum + k*U[n+1-k]*G[k]/n; ]; W[n]:=newsum; ]; DestructiveInsert(W,1,Coef(origg,0)); Subst(newvar,newvar-initval) NormalForm(UniVariate(newvar,0,W));];";
        scriptMap.put("ReversePoly",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Space() := WriteN(\" \",1);Space(n):= WriteN(\" \",n);";
        scriptMap.put("Space",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "UniqueConstant() <--[ Local(result); result := ToString(LocalSymbols(C)(C)); ToAtom(StringMidGet(2,Length(result)-1,result));];";
        scriptMap.put("UniqueConstant",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TemplateFunction(\"WithValue\",{var,val,expr})[ If(List?(var), ApplyFast(\"MacroLocal\",var), MacroLocal(var) ); ApplyFast(\":=\",{var,val}); Eval(expr);];";
        scriptMap.put("WithValue",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "WriteN(string,n) :=[ Local(i); For(i:=1,i<=?n,i++) WriteString(string); True;];";
        scriptMap.put("WriteN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "AmicablePair?(m_PositiveInteger?,n_PositiveInteger?) <-- ( ProperDivisorsSum(m)=?n And? ProperDivisorsSum(n)=?m );";
        scriptMap.put("AmicablePair?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # BellNumber(n_Integer?) <-- Sum(k,1,n,StirlingNumber2(n,k));";
        scriptMap.put("BellNumber",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # CarmichaelNumber?(n_Even?) <-- False;5 # CarmichaelNumber?(_n)_(n<?561) <-- False;10 # CarmichaelNumber?(n_PositiveInteger?) <--[ Local(i,factors,length,carmichael); factors:=Factors(n); carmichael:=True; length:=Length(factors); if( length <? 3)[ carmichael:=False; ] else [ For(i:=1,i<=?length And? carmichael,i++)[  If( Modulo(n-1,factors[i][1]-1) !=? 0, carmichael:=False ); If(factors[i][2]>?1,carmichael:=False);  ]; ]; carmichael;];CarmichaelNumber?(n_List?) <-- MapSingle(\"CarmichaelNumber?\",n);";
        scriptMap.put("CarmichaelNumber?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CatalanNumber(_n) <--[ Check( PositiveInteger?(n), \"Argument\", \"CatalanNumber: Error: argument must be positive\" ); BinomialCoefficient(2*n,n)/(n+1);];";
        scriptMap.put("CatalanNumber",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CheckIntPower(n, limit) :=[ Local(s0, s, root); If(limit<=?1, limit:=2);   s0 := IntLog(n, limit);  root := 0; s := 0; While(root =? 0 And? NextPseudoPrime(s)<=?s0)  [ s := NextPseudoPrime(s); root := IntNthRoot(n, s); If( root^s =? n,  True, root := 0 ); ];  If( root=?0, {n, 1}, {root, s} );];";
        scriptMap.put("CheckIntPower",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # Composite?(1) <-- False;10 # Composite?(n_PositiveInteger?) <-- (Not? Prime?(n));";
        scriptMap.put("Composite?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # Coprime?(list_List?) <-- (Lcm(list) =? Product(list));10 # Coprime?(n_Integer?,m_Integer?) <-- (Gcd(n,m) =? 1);";
        scriptMap.put("Coprime?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # DigitalRoot(n_PositiveInteger?) <-- If(n%9=?0,9,n%9);";
        scriptMap.put("DigitalRoot",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # Divisors(0) <-- 0;5 # Divisors(1) <-- 1;10 # Divisors(_n) <--[ Check(PositiveInteger?(n), \"Argument\", \"Divisors: argument must be positive integer\"); Local(len,sum,factors,i); sum:=1; factors:=Factors(n); len:=Length(factors); For(i:=1,i<=?len,i++)[ sum:=sum*(factors[i][2]+1); ]; sum;];";
        scriptMap.put("Divisors",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # DivisorsSum(0) <-- 0;5 # DivisorsSum(1) <-- 1;10 # DivisorsSum(_n) <--[ Check(PositiveInteger?(n), \"Argument\", \"DivisorsSum: argument must be positive integer\"); Local(factors,i,sum,len,p,k); p:=0;k:=0; factors:={}; factors:=Factors(n); len:=Length(factors); sum:=1; For(i:=1,i<=?len,i++)[ p:=factors[i][1]; k:=factors[i][2]; sum:=sum*(p^(k+1)-1)/(p-1); ]; sum;];";
        scriptMap.put("DivisorsSum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # Euler(0) <-- 1;10 # Euler(n_Odd?) <-- 0;10 # Euler(n_Even?) <-- - Sum(r,0,n/2-1,BinomialCoefficient(n,2*r)*Euler(2*r));10 # Euler(n_NonNegativeInteger?,_x) <-- Sum(i,0,Round(n/2),BinomialCoefficient(n,2*i)*Euler(2*i)*(x-1/2)^(n-2*i)/2^(2*i));";
        scriptMap.put("Euler",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # EulerArray(n_Integer?) <--[ Local(E,i,sum,r); E:=ZeroVector(n+1); E[1]:=1; For(i:=1,2*i<=?n,i++)[ sum:=0; For(r:=0,r<=?i-1,r++)[ sum:=sum+BinomialCoefficient(2*i,2*r)*E[2*r+1]; ]; E[2*i+1] := -sum; ]; E;];";
        scriptMap.put("EulerArray",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Eulerian(n_Integer?,k_Integer?) <-- Sum(j,0,k+1,(-1)^j*BinomialCoefficient(n+1,j)*(k-j+1)^n);";
        scriptMap.put("Eulerian",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"FermatNumber\",{n})[ Check(PositiveInteger?(n), \"Argument\", \"FermatNumber: argument must be a positive integer\"); 2^(2^n)+1;];";
        scriptMap.put("FermatNumber",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "20 # GetPrimePower(n_PositiveInteger?) <--[ Local(s, factors, newfactors);  factors := TrialFactorize(n, 257);  If( Length(factors) >? 1,   If( factors[1] =? 1 And? Length(factors) =? 2,  factors[2], {n, 1} ),  [ factors := CheckIntPower(n, 257);  If( factors[2] >? 1,   If( Prime?(factors[1]), factors,  [  newfactors := GetPrimePower(factors[1]);  If( newfactors[2] >? 1, {newfactors[1], newfactors[2]*factors[2]},  {n, 1}  ); ] ),  {n, 1} ); ] );];";
        scriptMap.put("GetPrimePower",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # HarmonicNumber(n_Integer?) <-- HarmonicNumber(n,1);HarmonicNumber(n_Integer?,r_PositiveInteger?) <--[  if( r=?1 )[ Sum(k,1,n,1/k); ] else [ Sum(k,1,n,1/k^r); ];];";
        scriptMap.put("HarmonicNumber",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # IntLog(_x, _base) _ (base<=?1) <-- Undefined;20 # IntLog(_x, _base) <--[ Local(result, step, oldstep, factor, oldfactor); result := 0; oldstep := step := 1; oldfactor := factor := base;  While (x >=? factor) [ oldfactor := factor; factor := factor*factor; oldstep := step; step := step*2; ]; If(x >=? base, [ step := oldstep; result := step; x := Quotient(x, oldfactor); ], step := 0 );  While (step >? 0 And? x !=? 1) [ step := Quotient(step,2);  factor := base^step; If( x >=? factor, [ x:=Quotient(x, factor); result := result + step; ] ); ]; result;];";
        scriptMap.put("IntLog",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # IntNthRoot(_n, 2) <-- Floor(SqrtN(n));20 # IntNthRoot(_n, s_Integer?) <--[ Local(result, k); GlobalPush(BuiltinPrecisionGet());  k := Quotient(IntLog(n, 2), s);  BuiltinPrecisionSet(2+Quotient(k*3361, 11165));  result := Round(ExpN(DivideN(InternalLnNum(DivideN(n, 2^(k*s))), s))*2^k); BuiltinPrecisionSet(GlobalPop());  If(result^s>?n, result-1, result);];";
        scriptMap.put("IntNthRoot",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # IrregularPrime?(p_Composite?) <-- False;5 # IrregularPrime?(_p)_(p<?37) <-- False;10 # IrregularPrime?(p_PositiveInteger?) <--[ Local(i,irregular); i:=1; irregular:=False; While( 2*i + 1 <? p And? (irregular =? False) )[ If( Abs(Numerator(Bernoulli(2*i))) % p =? 0, irregular:=True ); i++; ]; irregular;];";
        scriptMap.put("IrregularPrime?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # LegendreSymbol(_a,_p) <--[ Check( Integer?(a) And? Integer?(p) And? p>?2 And? Coprime?(a,p) And? Prime?(p), \"Argument\", \"LegendreSymbol: Invalid arguments\"); If(QuadraticResidue?(a,p), 1, -1 );];";
        scriptMap.put("LegendreSymbol",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # Moebius(1) <-- 1;10 # Moebius(_n) <--[ Check(PositiveInteger?(n), \"Argument\", \"Moebius: argument must be positive integer\"); Local(factors,i,repeat); repeat:=0; factors:=Factors(n); len:=Length(factors); For(i:=1,i<=?len,i++)[ If(factors[i][2]>?1,repeat:=1); ]; If(repeat=?0,(-1)^len,0);];";
        scriptMap.put("Moebius",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1# NextPrime(_i) <--[ Until(Prime?(i)) i := NextPseudoPrime(i); i;];";
        scriptMap.put("NextPrime",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1# NextPseudoPrime(i_Integer?)_(i<=?1) <-- 2;2# NextPseudoPrime(2) <-- 3;3# NextPseudoPrime(i_Odd?) <--[  i := i+2; If(Modulo(i,3)=?0, i:=i+2, i);];4# NextPseudoPrime(i_Even?) <-- NextPseudoPrime(i-1);";
        scriptMap.put("NextPseudoPrime",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(m,n,r, NthRootTable)[NthRoot(m_NonNegativeInteger?,n_Integer?)_(n>?1) <--[ Local(r); r:=NthRootRestore(m,n); If(Length(r)=?0, [ r:=NthRootCalc(m,n); NthRootSave(m,n,r); ]); r;];Function(\"NthRootCalc\",{m,n})[ Local(i,j,f,r,in); Bind(i,2); Bind(j,Ceil(FastPower(m,N(1.0/n))+1)); Bind(f,1); Bind(r,m);     While(LessThan?(i,j)) [ Bind(in,PowerN(i,n)); While(Equal?(ModuloN(r,in),0)) [ Bind(f,MultiplyN(f,i)); Bind(r,QuotientN(r,in)); ]; While(Equal?(ModuloN(r,i),0))  Bind(r,QuotientN(r,i));   Bind(i,NextPseudoPrime(i)); Bind(j,Ceil(FastPower(r,N(1.0/n))+1)); ];  List(f,QuotientN(m,PowerN(f,n))); ];Function(\"NthRootList\",{n})[ If(Length(NthRootTable)>?0, [ Local(p,xx); p:=Select(NthRootTable, {{xx},First(xx)=?n}); If(Length(p)=?1,Rest(p[1]),List()); ], List());];Function(\"NthRootRestore\",{m,n})[ Local(p); p:=NthRootList(n); If(Length(p)>?0, [ Local(r,xx); r:=Select(p, {{xx},First(xx)=?m}); If(Length(r)=?1,First(Rest(r[1])),List()); ], List());];Function(\"NthRootSave\",{m,n,r})[ Local(p); p:=NthRootList(n); If(Length(p)=?0,  DestructiveInsert(NthRootTable,1,List(n,List(m,r))), [ Local(rr,xx); rr:=Select(p, {{xx},First(xx)=?m}); If(Length(rr)=?0, [  DestructiveAppend(p,List(m,r)); ],  False); ]);];Function(\"NthRootClear\",{}) SetGlobalLazyVariable(NthRootTable,List());NthRootClear();]; ";
        scriptMap.put("NthRoot",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # NumberToRep( N_Number? ) <--[  Local(oldPrec,sgn,assoc,typ,val,prec,rep); oldPrec := BuiltinPrecisionGet(); BuiltinPrecisionSet(300);   sgn := Sign(N);  assoc := DumpNumber(Abs(N));  typ := Assoc(\"type\",assoc)[2];  If( typ =? \"BigDecimal\", [ rep := { sgn*Assoc(\"unscaledValue\",assoc)[2], Assoc(\"precision\", assoc)[2], Assoc(\"scale\", assoc)[2]  }; ], [ Local(val,prec); val := Assoc(\"value\",assoc)[2]; prec := Length(ExpressionToString(val)); rep := { sgn*val, prec }; ] );  BuiltinPrecisionSet(oldPrec); rep;];12 # NumberToRep( N_Complex? ) <-- [ If(Zero?(Re(N)), {NumberToRep(0.0),NumberToRep(Im(N))}, {NumberToRep(Re(N)),NumberToRep(Im(N))} );];";
        scriptMap.put("NumberToRep",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # PartitionsP(n_Integer?,0) <-- 0;5 # PartitionsP(n_Integer?,n_Integer?) <-- 1;5 # PartitionsP(n_Integer?,1) <-- 1;5 # PartitionsP(n_Integer?,2) <-- Floor(n/2);5 # PartitionsP(n_Integer?,3) <-- Round(n^2/12);6 # PartitionsP(n_Integer?,k_Integer?)_(k>?n) <-- 0;10 # PartitionsP(n_Integer?,k_Integer?) <-- PartitionsP(n-1,k-1)+PartitionsP(n-k,k);5 # PartitionsP(0) <-- 1;5 # PartitionsP(1) <-- 1;10 # PartitionsP(n_Integer?)_(n<?250) <-- PartitionsPrecur(n);20 # PartitionsP(n_Integer?) <-- PartitionsPHR(n);10 # PartitionsPHR(n_PositiveInteger?) <--[ Local(P0, A, lambda, mu, muk, result, term, j, k, l, prec, epsilon); result:=0; term:=1;  GlobalPush(BuiltinPrecisionGet());   prec := 2+Quotient(IntNthRoot(Quotient(2*n+2,3),2)*161+117,118); BuiltinPrecisionSet(prec);  epsilon := PowerN(10,-prec)*n*10;   lambda := N(Sqrt(n - 1/24)); mu := N(Pi*lambda*Sqrt(2/3));   P0 := N(1-1/mu)*DivideN(ExpN(mu),(n-DivideN(1,24))*4*SqrtN(3));  A := 0;     For(k:=1, k<=?5+Quotient(IntNthRoot(n,2),2) And? (A=?0 Or? Abs(term)>?epsilon), k++) [  A:=0; For(l:=1,l<=?k,l++) [ If( Gcd(l,k)=?1, A := A + Cos(Pi* (  Sum(j,1,k-1, j*(Modulo(l*j,k)/k-1/2)) - 2*l*n  )/k) ); A:=N(A);  ]; term := If( A=?0,  0, N( A*Sqrt(k)*( [ muk := mu/k;  Exp(muk-mu)*(muk-1) + Exp(-muk-mu)*(muk+1); ] )/(mu-1) ) ); result := result + term; ]; result := result * P0; BuiltinPrecisionSet(GlobalPop()); Round(result);];10 # PartitionsP1(n_PositiveInteger?) <-- [ Local(C,A,lambda,m,pa,k,h,term); GlobalPush(BuiltinPrecisionGet());  BuiltinPrecisionSet(10 + Floor(N(Sqrt(n))) ); pa:=0; C:=Pi*Sqrt(2/3)/k; lambda:=Sqrt(m - 1/24); term:=1;   For(k:=1,k<=?5+Floor(SqrtN(n)*0.5) And? ( term=?0 Or? Abs(term)>?0.1) ,k++)[ A:=0; For(h:=1,h<=?k,h++)[ if( Gcd(h,k)=?1 )[ A:=A+Exp(I*Pi*Sum(j,1,k-1,(j/k)*((h*j)/k - Floor((h*j)/k) -1/2))- 2*Pi*I*h*n/k ); ]; ]; If(A!=?0, term:= N(A*Sqrt(k)*(Deriv(m) Sinh(C*lambda)/lambda) Where m==n ),term:=0 ); pa:=pa+term; ]; pa:=N(pa/(Pi*Sqrt(2))); BuiltinPrecisionSet(GlobalPop()); Round(pa); ];PartitionsPrecur(number_PositiveInteger?) <--[  Local(sign, cache, n, k, pentagonal, P); cache:=ArrayCreate(number+1,1);  n := 1; While(n<?number)  [ n++;  P := 0; k := 1; pentagonal := 1;  sign := 1; While(pentagonal<=?n) [ P := P + (cache[n-pentagonal+1]+If(pentagonal+k<=?n, cache[n-pentagonal-k+1], 0))*sign; pentagonal := pentagonal + 3*k+1; k++; sign := -sign; ]; cache[n+1] := P;  ]; cache[number+1];];PartitionsPrecur(0) <-- 1;";
        scriptMap.put("PartitionsP",scriptString);
        scriptMap.put("PartitionsPHR",scriptString);
        scriptMap.put("PartitionsP1",scriptString);
        scriptMap.put("PartitionsPrecur",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Perfect?(n_PositiveInteger?) <-- ProperDivisorsSum(n)=?n;";
        scriptMap.put("Perfect?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # PrimePower?(n_Prime?) <-- True;10 # PrimePower?(0) <-- False;10 # PrimePower?(1) <-- False;20 # PrimePower?(n_PositiveInteger?) <-- (GetPrimePower(n)[2] >? 1);";
        scriptMap.put("PrimePower?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "2 # Prime?(_n)_(Not? Integer?(n) Or? n<=?1) <-- False;3 # Prime?(n_Integer?)_(n<=?FastIsPrime(0)) <-- SmallPrime?(n);5 # Prime?(n_PositiveInteger?)_(n >? 4 And? Modulo(n^2-1,24)!=?0) <-- False;10 # Prime?(n_PositiveInteger?) <-- RabinMiller(n);";
        scriptMap.put("Prime?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(p, q)[  ProductPrimesTo257() := 2*3*[ If( Integer?(p), p, p := Product(Select( 5 .. 257, {{q}, Modulo(q^2,24)=?1 And? SmallPrime?(q)})) ); ];];";
        scriptMap.put("ProductPrimesTo257",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ProperDivisors(_n) <--[ Check(PositiveInteger?(n), \"Argument\", \"ProperDivisors: argument must be positive integer\"); Divisors(n)-1;];";
        scriptMap.put("ProperDivisors",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ProperDivisorsSum(_n) <--[ Check(PositiveInteger?(n), \"Argument\", \"ProperDivisorsSum: argument must be positive integer\"); DivisorsSum(n)-n;];";
        scriptMap.put("ProperDivisorsSum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # QuadraticResidue?(_a,_p) <--[ Check( Integer?(a) And? Integer?(p) And? p>?2 And? Coprime?(a,p) And? Prime?(p), \"Argument\", \"QuadraticResidue?: Invalid arguments\"); If(a^((p-1)/2) % p =? 1, True, False);];";
        scriptMap.put("QuadraticResidue?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Rationalize(aNumber_List?) <-- Rationalize /@ aNumber;20 # Rationalize( _aNumber ) <--[ Local(result,n,d); result:=Substitute(aNumber,{{x},Number?(x) And? Not?(Integer?(x))},\"RationalizeNumber\"); If(InVerboseMode(),Tell(\"\",result)); If(Length(VarList(aNumber))=?0, [ n:=Numerator(result); If(Type(n)=?\"Numerator\",n:=result); d:=Denominator(result); If(Type(d)=?\"Denominator\",d:=1); result := n*(1/d); ] ); result;];";
        scriptMap.put("Rationalize",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"RationalizeNumber\",{x})[ Check(Number?(x), \"Argument\", \"RationalizeNumber: Error: \" ~ (PipeToString()Write(x)) ~\" is not a number\"); Local(n,i,bip,m); n := 1; i := 0; bip := BuiltinPrecisionGet();  While(i<=?bip And? Not?(FloatIsInt?(x))) [ n := n*10;  x := x*10; i := i+1; ]; m := Floor(x+0.5); (m/n);];";
        scriptMap.put("RationalizeNumber",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RepToNumber( rep_ListOfLists? ) <--[  RepToNumber(rep[1])+I*RepToNumber(rep[2]);];12 # RepToNumber( rep_List? ) <--[  Local(bigInt,precision,scale,strBI,sgn,index,ans); Local(first,secnd,third,LS,numStr); precision := rep[2]; scale := 0; bigInt := rep[1]; precision := rep[2]; sgn := Sign(bigInt); If( Length(rep) >? 2, scale := rep[3] ); strBI := ExpressionToString(Abs(bigInt)); LS := Length(strBI);  If( Length(rep)=?2, [ numStr := strBI; ], [ index := precision-scale; first := strBI[1]; secnd := StringMidGet(2,LS-1,strBI);  third := ExpressionToString(index-1);  if ( index >? 0 ) [ if ( index <? precision ) [  numStr := ConcatStrings(first,\".\",secnd,\"E\",third); ] else if ( index >=? precision ) [  numStr := ConcatStrings(first,\".\",secnd,\"E+\",third); ]; ] else if ( index <? 0 ) [  numStr := ConcatStrings(first,\".\",secnd,\"E\",third); ] else [  first := \"0.\" ;  secnd := strBI; numStr := ConcatStrings(first,secnd); ]; ] ); ans := sgn * ToAtom(numStr);  ans;];";
        scriptMap.put("RepToNumber",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Repunit(0) <-- 0;Repunit(n_PositiveInteger?) <--[ (10^n-1)/9;];";
        scriptMap.put("Repunit",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RoundToPlace( N_Decimal?, place_Integer? ) <--[  Local(rep,sgn,oldInt,oldPrec,oldScale,oldPlaces,strOInt,LS); Local(newInt,newScale,newRep,ans); sgn := Sign(N); rep := NumberToRep( Abs(N) ); oldInt := rep[1]; oldPrec := rep[2]; oldScale := rep[3]; oldPlaces:= oldPrec - oldScale; strOInt := ExpressionToString(oldInt); LS := Length(strOInt);           If(oldPlaces+place>?0, ans := RoundToPrecision(N,oldPlaces+place), ans := 0. ); ans;];15 # RoundToPlace( N_Integer?, place_Integer? )_(place <=? 0) <--[  Local(oldRep,oldPrec,decN,newDecN,ans); oldRep := NumberToRep(N); oldPrec := oldRep[2]; decN := N*1.0; newDecN := RoundToPlace( decN, place );     If( place <=? oldPrec,  ans := Round(newDecN), ans := Round(newDecN*10^(place-oldPrec)) ); ans;];20 # RoundToPlace( N_Complex?, place_Integer? )_(Not? Integer?(N)) <--[  Local(rr,ii); rr := Re(N); ii := Im(N); Complex(RoundToPlace(rr,place),RoundToPlace(ii,place));];";
        scriptMap.put("RoundToPlace",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RoundToPrecision( N_Decimal?, newPrec_PositiveInteger? ) <--[  Local(rep,sgn,oldInt,oldPrec,oldScale,strOInt,LS,BIP0); Local(newInt,newScale,newRep,ans); BIP0 := BuiltinPrecisionGet(); sgn := Sign(N); rep := NumberToRep( If(sgn<?0,-N,N) ); oldInt := rep[1]; oldPrec := rep[2]; oldScale := rep[3]; If( newPrec >? oldPrec, BuiltinPrecisionSet(newPrec) ); strOInt := ExpressionToString(oldInt); LS := Length(strOInt);           Local(first,secnd,rem,ad); if ( newPrec =? oldPrec ) [ ans := N; ] else if ( newPrec <? oldPrec ) [ first := StringMidGet(1, newPrec, strOInt);  secnd := StringMidGet(newPrec+1, LS-newPrec, strOInt); rem := ToAtom(ConcatStrings(\".\",secnd)); ad := If(rem>=?0.5, 1, 0 ); newInt := sgn * ( ToAtom(first) + ad ); newScale := oldScale - ( oldPrec - newPrec );  newRep := {newInt,newPrec,newScale}; ans := RepToNumber(newRep);         ] else [  Local(diffPrec); diffPrec := oldPrec - newPrec; newInt := sgn * ToAtom(strOInt) * 10^(-diffPrec) ; newScale := oldScale - diffPrec; newRep := {newInt,newPrec,newScale};  ans := RepToNumber(newRep); ]; BuiltinPrecisionSet(BIP0); ans;];15 # RoundToPrecision( N_Integer?, newPrec_PositiveInteger? ) <--[  Local(oldRep,oldPrec,decN,newDecN,ans); oldRep := NumberToRep(N); oldPrec := oldRep[2]; decN := N*1.0; newDecN := RoundToPrecision( decN, newPrec );     If( newPrec <=? oldPrec,  ans := Round(newDecN), ans := Round(newDecN*10^(newPrec-oldPrec)) ); ans;];20 # RoundToPrecision( N_Complex?, newPrec_PositiveInteger? ) <--[  Local(rr,ii); rr := Re(N); ii := Im(N); Complex(RoundToPrecision(rr,newPrec),RoundToPrecision(ii,newPrec));];";
        scriptMap.put("RoundToPrecision",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "2 # SmallPrime?(0) <-- False;3 # SmallPrime?(n_Integer?) <-- (FastIsPrime(n)>?0);";
        scriptMap.put("SmallPrime?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SquareFree?(n_Integer?) <-- ( Moebius(n) !=? 0 );";
        scriptMap.put("SquareFree?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # StirlingNumber1(n_Integer?,0) <-- If(n=?0,1,0);10 # StirlingNumber1(n_Integer?,1) <-- (-1)^(n-1)*(n-1)!;10 # StirlingNumber1(n_Integer?,2) <-- (-1)^n*(n-1)! * HarmonicNumber(n-1);10 # StirlingNumber1(n_Integer?,n-1) <-- -BinomialCoefficient(n,2);10 # StirlingNumber1(n_Integer?,3) <-- (-1)^(n-1)*(n-1)! * (HarmonicNumber(n-1)^2 - HarmonicNumber(n-1,2))/2;20 # StirlingNumber1(n_Integer?,m_Integer?) <-- Sum(k,0,n-m,(-1)^k*BinomialCoefficient(k+n-1,k+n-m)*BinomialCoefficient(2*n-m,n-k-m)*StirlingNumber2(k-m+n,k));";
        scriptMap.put("StirlingNumber1",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # StirlingNumber2(n_Integer?,0) <-- If(n=?0,1,0);20 # StirlingNumber2(n_Integer?,k_Integer?) <-- Sum(i,0,k-1,(-1)^i*BinomialCoefficient(k,i)*(k-i)^n)/ k! ;";
        scriptMap.put("StirlingNumber2",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Totient(_n) <--[ Check(PositiveInteger?(n), \"Argument\", \"Totient: argument must be positive integer\"); Local(i,sum,factors,len); sum:=n; factors:=Factors(n); len:=Length(factors); For(i:=1,i<=?len,i++)[ sum:=sum*(1-1/factors[i][1]); ]; sum;];";
        scriptMap.put("Totient",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TwinPrime?(n_PositiveInteger?) <-- (Prime?(n) And? Prime?(n+2));";
        scriptMap.put("TwinPrime?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"FactorGaussianInteger\",{x}) [ Check( GaussianInteger?(x), \"Argument\", \"FactorGaussianInteger: argument must be a Gaussian integer\"); Local(re,im,norm,a,b,d,i,j); re:=Re(x);im:=Im(x); If(re<?0, re:=(-re) ); If(im<?0, im:=(-im) ); norm:=re^2+im^2; if( Composite?(norm) )[ For(i:=0, i^2 <=? norm, i++ )[  For(j:=0, i^2 + j^2 <=? norm, j++)[  if( Not?( (i =? re And? j =? im) Or? (i =? im And? j =? re) ) )[  d:=i^2+j^2; if( d >? 1 )[ a := re * i + im * j; b := im * i - re * j; While( (Modulo(a,d) =? 0) And? (Modulo(b,d) =? 0) ) [ FactorGaussianInteger(Complex(i,j)); re:= a/d; im:= b/d; a := re * i + im * j; b := im * i - re * j; norm := re^2 + im^2; ]; ]; ]; ]; ]; If( re !=? 1 Or? im !=? 0, Echo(Complex(re,im)) ); ] else [ Echo(Complex(re,im)); ];];";
        scriptMap.put("FactorGaussianInteger",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "GaussianFactorPrime(p_Integer?) <-- [ Local(a,i); a := 1; For (i:=2,i<=?(p-1)/2,i++) a := Modulo(a*i,p); GaussianGcd(a+I,p);];";
        scriptMap.put("GaussianFactorPrime",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "AddGaussianFactor(L_List?,z_GaussianInteger?,p_GaussianInteger?) <--[ Local(e); e :=0; While (GaussianInteger?(z:= z/p)) e++; If (e !=? 0, DestructiveAppend(L,{p,e}));];10 # GaussianFactors(n_Integer?) <--[  Local(ifactors,gfactors,p,alpha); ifactors := FactorizeInt(n);  gfactors := {}; ForEach(p,ifactors) [ If (p[1]=?2, [ DestructiveAppend(gfactors,{1+I,p[2]}); DestructiveAppend(gfactors,{1-I,p[2]}); ]); If (Modulo(p[1],4)=?3, DestructiveAppend(gfactors,p)); If (Modulo(p[1],4)=?1, [ alpha := GaussianFactorPrime(p[1]); DestructiveAppend(gfactors,{alpha,p[2]}); DestructiveAppend(gfactors,{Conjugate(alpha),p[2]}); ]); ];gfactors;];20 # GaussianFactors(z_GaussianInteger?) <--[ Local(n,nfactors,gfactors,p); gfactors :={}; n := GaussianNorm(z); nfactors := Factors(n); ForEach(p,nfactors) [ If (p[1]=?2, [ AddGaussianFactor(gfactors,z,1+I);]); If (Modulo(p[1],4)=?3, AddGaussianFactor(gfactors,z,p[1])); If (Modulo(p[1],4)=?1, [ Local(alpha); alpha := GaussianFactorPrime(p[1]); AddGaussianFactor(gfactors,z,alpha); AddGaussianFactor(gfactors,z,Conjugate(alpha)); ]); ]; gfactors;];";
        scriptMap.put("GaussianFactors",scriptString);
        scriptMap.put("AddGaussianFactor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # GaussianGcd(n_GaussianInteger?,m_GaussianInteger?) <--[ If(N(Abs(m))=?0,n, GaussianGcd(m,n - m*Round(n/m) ) );];";
        scriptMap.put("GaussianGcd",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # GaussianInteger?(x_List?) <-- False;10 # GaussianInteger?(x_Complex?) <-- (Integer?(Re(x)) And? Integer?(Im(x)));15 # GaussianInteger?(_x) <-- False;";
        scriptMap.put("GaussianInteger?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "GaussianMod(z_GaussianInteger?,w_GaussianInteger?) <-- z - w * Round(z/w);";
        scriptMap.put("GaussianMod",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "GaussianNorm(z_GaussianInteger?) <-- Re(z)^2+Im(z)^2;";
        scriptMap.put("GaussianNorm",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"GaussianPrime?\",{x})[ if( GaussianInteger?(x) )[ if( Zero?(Re(x)) )[ ( Abs(Im(x)) % 4 =? 3 And? Prime?(Abs(Im(x))) ); ] else if ( Zero?(Im(x)) ) [ ( Abs(Re(x)) % 4 =? 3 And? Prime?(Abs(Re(x))) ); ] else [ Prime?(Re(x)^2 + Im(x)^2); ]; ] else [ False; ];];";
        scriptMap.put("GaussianPrime?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "GaussianUnit?(z_GaussianInteger?) <-- GaussianNorm(z)=?1;";
        scriptMap.put("GaussianUnit?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "DivisorsList(n_PositiveInteger?) <--[ Local(nFactors,f,result,oldresult,x); nFactors:= Factors(n); result := {1}; ForEach (f,nFactors) [ oldresult := result; For (k:=1,k<=?f[2],k++) ForEach (x,oldresult) result:=Append(result,x*f[1]^k); ]; result;];";
        scriptMap.put("DivisorsList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # JacobiSymbol(_a, 1) <-- 1;15 # JacobiSymbol(0, _b) <-- 0;18 # JacobiSymbol(_a, _b) _ (Gcd(a,b)>?1) <-- 0;20 # JacobiSymbol(_a, b_Odd?)_(a>=?Abs(b) Or? a<?0) <-- JacobiSymbol(Modulo(a,Abs(b)),Abs(b));30 # JacobiSymbol(a_Even?, b_Odd?) <--[ Local(c, s);  {c,s}:=FindPrimeFactorSimple(a, 2);  If(Modulo(s,2)=?1 And? Abs(Modulo(b,8)-4)=?1, -1, 1) * JacobiSymbol(c,b);];40 # JacobiSymbol(a_Odd?, b_Odd?) <-- If(Modulo(a,4)=?3 And? Modulo(b,4)=?3, -1, 1) * JacobiSymbol(b,a);";
        scriptMap.put("JacobiSymbol",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MoebiusDivisorsList(n_PositiveInteger?) <--[ Local(nFactors,f,result,oldresult,x); nFactors:= Factors(n); result := {{1,1}}; ForEach (f,nFactors) [ oldresult := result; ForEach (x,oldresult) result:=Append(result,{x[1]*f[1],-x[2]}); ]; result;];";
        scriptMap.put("MoebiusDivisorsList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RamanujanSum(k_PositiveInteger?,0) <-- Totient(k);20 # RamanujanSum(k_PositiveInteger?,n_PositiveInteger?) <--[ Local(s,gcd,d); s:= 0; gcd := Gcd(n,k); ForEach (d,DivisorsList(gcd)) s:=s+d*Moebius(k/d); s;];";
        scriptMap.put("RamanujanSum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SquareFreeDivisorsList(n_PositiveInteger?) <--[ Local(nFactors,f,result,oldresult,x); nFactors:= Factors(n); result := {1}; ForEach (f,nFactors) [ oldresult := result; ForEach (x,oldresult) result:=Append(result,x*f[1]); ]; result;];";
        scriptMap.put("SquareFreeDivisorsList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function (\"SumForDivisors\",{sumvar,n,sumbody}) LocalSymbols(s,d)[ Local(s,d); s:=0; ForEach (d,DivisorsList(n)) [ MacroLocal(sumvar); MacroBind(sumvar,d); s:=s+Eval(sumbody); ]; s;];UnFence(\"SumForDivisors\",3);HoldArgument(\"SumForDivisors\",sumvar);HoldArgument(\"SumForDivisors\",sumbody); ";
        scriptMap.put("SumForDivisors",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"yyy\",{x});10 # OdeLeftHandSideEq(_l == _r) <-- (l-r);20 # OdeLeftHandSideEq(_e) <-- e;10 # OdeNormChange(y(n_Integer?)) <-- ListToFunction({yyy,n});20 # OdeNormChange(y) <-- yyy(0);25 # OdeNormChange(yPRIME) <-- yyy(1);25 # OdeNormChange(yPRIMEPRIME) <-- yyy(2);30 # OdeNormChange(_e) <-- e;OdeNormPred(_e) <-- (e !=? OdeNormChange(e));OdeNormalForm(_e) <--[ e := Substitute(OdeLeftHandSideEq(e),\"OdeNormPred\",\"OdeNormChange\");];10 # OdeChange(yyy(n_Integer?)) <-- Apply(yn,{n});30 # OdeChange(_e) <-- e;OdePred(_e) <-- (e !=? OdeChange(e));UnFence(\"OdeChange\",1);UnFence(\"OdePred\",1);OdeSubstitute(_e,_yn) <--[ Substitute(e,\"OdePred\",\"OdeChange\");];UnFence(\"OdeSubstitute\",2);OdeConstantList(n_Integer?) <--[ Local(result,i); result:=ZeroVector(n); For (i:=1,i<=?n,i++) result[i]:=UniqueConstant(); result;];RulebaseHoldArguments(\"OdeTerm\",{px,list});10# OdeFlatTerm(OdeTerm(_a0,_b0)+OdeTerm(_a1,_b1)) <-- OdeTerm(a0+a1,b0+b1);10# OdeFlatTerm(OdeTerm(_a0,_b0)-OdeTerm(_a1,_b1)) <-- OdeTerm(a0-a1,b0-b1);10# OdeFlatTerm(-OdeTerm(_a1,_b1)) <-- OdeTerm(-a1,-b1);10# OdeFlatTerm(OdeTerm(_a0,_b0)*OdeTerm(_a1,_b1))_ (ZeroVector?(b0) Or? ZeroVector?(b1)) <--[ OdeTerm(a0*a1,a1*b0+a0*b1);];10# OdeFlatTerm(OdeTerm(_a0,_b0)/OdeTerm(_a1,_b1))_ (ZeroVector?(b1)) <-- OdeTerm(a0/a1,b0/a1);10# OdeFlatTerm(OdeTerm(_a0,b0_ZeroVector?)^OdeTerm(_a1,b1_ZeroVector?)) <-- OdeTerm(a0^a1,b0);15 # OdeFlatTerm(OdeTerm(_a,_b)) <-- OdeTerm(a,b);15# OdeFlatTerm(OdeTerm(_a0,_b0)*OdeTerm(_a1,_b1)) <-- OdeTermFail();15# OdeFlatTerm(OdeTerm(_a0,b0)^OdeTerm(_a1,b1)) <-- OdeTermFail();15# OdeFlatTerm(OdeTerm(_a0,b0)/OdeTerm(_a1,b1)) <-- OdeTermFail();20 # OdeFlatTerm(a_Atom?) <-- OdeTermFail();20 # OdeFlatTerm(_a+_b) <-- OdeFlatTerm(OdeFlatTerm(a) + OdeFlatTerm(b));20 # OdeFlatTerm(_a-_b) <-- OdeFlatTerm(OdeFlatTerm(a) - OdeFlatTerm(b));20 # OdeFlatTerm(_a*_b) <-- OdeFlatTerm(OdeFlatTerm(a) * OdeFlatTerm(b));20 # OdeFlatTerm(_a^_b) <-- OdeFlatTerm(OdeFlatTerm(a) ^ OdeFlatTerm(b));20 # OdeFlatTerm(_a/_b) <-- OdeFlatTerm(OdeFlatTerm(a) / OdeFlatTerm(b));OdeMakeTerm(xx_Atom?) <-- OdeTerm(xx,FillList(0,10));OdeMakeTerm(yyy(_n)) <-- OdeTerm(0,BaseVector(n+1,10));20 # OdeMakeTerm(_xx) <-- OdeTerm(xx,FillList(0,10));10 # OdeMakeTermPred(_x+_y) <-- False;10 # OdeMakeTermPred(_x-_y) <-- False;10 # OdeMakeTermPred( -_y) <-- False;10 # OdeMakeTermPred(_x*_y) <-- False;10 # OdeMakeTermPred(_x/_y) <-- False;10 # OdeMakeTermPred(_x^_y) <-- False;20 # OdeMakeTermPred(_rest) <-- True;OdeCoefList(_e) <--[ Substitute(e,\"OdeMakeTermPred\",\"OdeMakeTerm\");];OdeTermFail() <-- OdeTerm(Error,FillList(Error,10));OdeAuxiliaryEquation(_e) <--[  e:=OdeNormalForm(e); e:=OdeSubstitute(e,{{n},aaa^n*Exp(aaa*x)}); e:=Subst(Exp(aaa*x),1)e; Simplify(Subst(aaa,x)e);];OdeSolveLinearHomogeneousConstantCoefficients(_e) <--[ Local(roots,consts,auxeqn);  e:=OdeAuxiliaryEquation(e); auxeqn:=e; If(InVerboseMode(), Echo(\"OdeSolve: Auxiliary Eqn \",auxeqn) );  e := Apply(\"RootsWithMultiples\",{e}); e := RemoveDuplicates(e);  if( Length(e) >? 0 )[ roots:=Transpose(e); consts:= MapSingle(Hold({{nn},Add(OdeConstantList(nn)*(x^(0 .. (nn-1))))}),roots[2]); roots:=roots[1];   Add( consts * Exp(roots*x) ); ] else if ( Degree(auxeqn,x) =? 2 ) [  Local(a,b,c,roots); roots:=ZeroVector(2);  {c,b,a} := Coef(auxeqn,x,0 .. 2); roots := PSolve(a*x^2+b*x+c,x); If(InVerboseMode(),Echo(\"OdeSolve: Roots of quadratic:\",roots) );      if( b=?0 And? c=?0 )[ Add(OdeConstantList(2)*{1,x}); ] else if( Number?(N(roots[1])) )[ If(InVerboseMode(),Echo(\"OdeSolve: Real roots\")); Add(OdeConstantList(2)*{Exp(roots[1]*x),Exp(roots[2]*x)}); ] else [ If(InVerboseMode(),Echo(\"OdeSolve: Complex conjugate pair roots\")); Local(alpha,beta); alpha:=Re(roots[1]); beta:=Im(roots[1]); Exp(alpha*x)*Add( OdeConstantList(2)*{Sin(beta*x),Cos(beta*x)} ); ]; ] else [ Echo(\"OdeSolve: Could not find roots of auxilliary equation\"); ];];10 # OdeOrder(_e) <-- [ Local(h,i,coefs); coefs:=ZeroVector(10);  e:=OdeNormalForm(e); If(InVerboseMode(),Echo(\"OdeSolve: Normal form is\",e)); h:=OdeFlatTerm(OdeCoefList(e)); If(InVerboseMode(),Echo(\"OdeSolve: Flatterm is\",h));   coefs:=Reverse(FunctionToList(h)[3]); While( First(coefs) =? 0 )[ coefs:=Rest(coefs); ]; Length(coefs)-1;];10 # OdeSolve(_expr)_(OdeOrder(expr)=?0) <-- Echo(\"OdeSolve: Not a differential equation\");10 # OdeSolve(yPRIME+_a==_expr)_(FreeOf?(y,a)) <-- OdeSolve(yPRIME==expr-a);10 # OdeSolve(yPRIME-_a==_expr)_(FreeOf?(y,a)) <-- OdeSolve(yPRIME==expr+a);10 # OdeSolve(yPRIME/_a==_expr)_(FreeOf?(y,a)) <-- OdeSolve(yPRIME==expr*a);10 # OdeSolve(_a*yPRIME==_expr)_(FreeOf?(y,a)) <-- OdeSolve(yPRIME==expr/a);10 # OdeSolve(yPRIME*_a==_expr)_(FreeOf?(y,a)) <-- OdeSolve(yPRIME==expr/a);10 # OdeSolve(_a/yPRIME==_expr)_(FreeOf?(y,a)) <-- OdeSolve(yPRIME==a/expr);10 # OdeSolve(yPRIME==_expr)_(FreeOf?({y,yPRIME,yPRIMEPRIME},expr)) <--[ If(InVerboseMode(),Echo(\"OdeSolve: Integral in disguise!\")); If(InVerboseMode(),Echo(\"OdeSolve: Attempting to integrate \",expr)); (Integrate(x) expr)+UniqueConstant();];50 # OdeSolve(_e) <--[ Local(h); e:=OdeNormalForm(e); If(InVerboseMode(),Echo(\"OdeSolve: Normal form is\",e)); h:=OdeFlatTerm(OdeCoefList(e)); If(InVerboseMode(),Echo(\"OdeSolve: Flatterm is\",h)); if (FreeOf?(Error,h)) [ OdeSolveLinear(e,h); ] else OdeUnsolved(e);];10 # OdeSolveLinear(_e,OdeTerm(0,_list))_(Length(VarList(list)) =? 0) <--[ OdeSolveLinearHomogeneousConstantCoefficients(OdeNormalForm(e));];100 # OdeSolveLinear(_e,_ode) <-- OdeUnsolved(e);OdeUnsolved(_e) <-- Subst(yyy,y)e;OdeTest(_e,_solution) <--[ Local(s); s:= `Lambda({n},if (n>?0)(Differentiate(x,n)(@solution)) else (@solution)); e:=OdeNormalForm(e); e:=Apply(\"OdeSubstitute\",{e,s}); e:=Simplify(e); e;];";
        scriptMap.put("OdeSolve",scriptString);
        scriptMap.put("OdeTest",scriptString);
        scriptMap.put("OdeOrder",scriptString);
        scriptMap.put("OdeLeftHandSideEq",scriptString);
        scriptMap.put("OdeNormChange",scriptString);
        scriptMap.put("OdeNormPred",scriptString);
        scriptMap.put("OdeNormalForm",scriptString);
        scriptMap.put("OdeChange",scriptString);
        scriptMap.put("OdePred",scriptString);
        scriptMap.put("OdeSubstitute",scriptString);
        scriptMap.put("OdeConstantList",scriptString);
        scriptMap.put("OdeFlatTerm",scriptString);
        scriptMap.put("OdeMakeTerm",scriptString);
        scriptMap.put("OdeMakeTermPred",scriptString);
        scriptMap.put("OdeCoefList",scriptString);
        scriptMap.put("OdeTermFail",scriptString);
        scriptMap.put("OdeAuxiliaryEquation",scriptString);
        scriptMap.put("OdeSolveLinearHomogeneousConstantCoefficients",scriptString);
        scriptMap.put("OdeSolveLinear",scriptString);
        scriptMap.put("OdeUnsolved",scriptString);
        scriptMap.put("TanMap",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # EvaluateHornerScheme({}, _x) <-- 0;10 # EvaluateHornerScheme({_coeffs}, _x) <-- coeffs;20 # EvaluateHornerScheme(coeffs_List?, _x) <-- First(coeffs)+x*EvaluateHornerScheme(Rest(coeffs), x);OrthoP(n_Integer?, _x)_(n>=?0) <-- OrthoP(n, 0, 0, x);OrthoP(n_Integer?, a_RationalOrNumber?, b_RationalOrNumber?, _x)_(n>=?0 And? a>? -1 And? b>? -1) <-- OrthoPoly(\"Jacobi\", n, {a, b}, x);OrthoG(n_Integer?, a_RationalOrNumber?, _x)_(n>=?0 And? a>? -1/2) <-- OrthoPoly(\"Gegenbauer\", n, {a}, x);OrthoH(n_Integer?, _x)_(n>=?0) <-- OrthoPoly(\"Hermite\", n, {}, x);OrthoL(n_Integer?, a_RationalOrNumber?, _x)_(n>=?0 And? a>? -1) <-- OrthoPoly(\"Laguerre\", n, {a}, x);OrthoT(n_Integer?, _x)_(n>=?0) <-- OrthoPoly(\"Tscheb1\", n, {}, x);OrthoU(n_Integer?, _x)_(n>=?0) <-- OrthoPoly(\"Tscheb2\", n, {}, x);OrthoPSum(c_List?, _x) <-- OrthoP(c, 0, 0, x);OrthoPSum(c_List?, a_RationalOrNumber?, b_RationalOrNumber?, _x)_(a>? -1 And? b>? -1) <-- OrthoPolySum(\"Jacobi\", c, {a, b}, x);OrthoGSum(c_List?, a_RationalOrNumber?, _x)_(a>? -1/2) <-- OrthoPolySum(\"Gegenbauer\", c, {a}, x);OrthoHSum(c_List?, _x) <-- OrthoPolySum(\"Hermite\", c, {}, x);OrthoLSum(c_List?, a_RationalOrNumber?, _x)_(a>? -1) <-- OrthoPolySum(\"Laguerre\", c, {a}, x);OrthoTSum(c_List?, _x) <-- OrthoPolySum(\"Tscheb1\", c, {}, x);OrthoUSum(c_List?, _x) <-- OrthoPolySum(\"Tscheb2\", c, {}, x);LocalSymbols(knownOrthoPoly) [ knownOrthoPoly := Hold({ {\"Jacobi\", {{n, p}, {{(p[1]-p[2])/2, 1+(p[1]+p[2])/2}, {(2*n+p[1]+p[2]-1)*((p[1])^2-(p[2])^2)/(2*n*(n+p[1]+p[2])*(2*n+p[1]+p[2]-2)), (2*n+p[1]+p[2]-1)*(2*n+p[1]+p[2])/(2*n*(n+p[1]+p[2])), -(n+p[1]-1)*(n+p[2]-1)*(2*n+p[1]+p[2])/(n*(n+p[1]+p[2])*(2*n+p[1]+p[2]-2))}}}}, {\"Gegenbauer\", {{n, p}, {{0, 2*p[1]}, {0, 2+2*(p[1]-1)/n, -1-2*(p[1]-1)/n}}}}, {\"Laguerre\", {{n, p}, {{p[1]+1, -1}, {2+(p[1]-1)/n, -1/n, -1-(p[1]-1)/n}}}}, {\"Hermite\", {{n, p}, {{0,2}, {0, 2, -2*(n-1)}}}}, {\"Tscheb1\", {{n, p}, {{0,1}, {0,2,-1}}}}, {\"Tscheb2\", {{n, p}, {{0,2}, {0,2,-1}}}} }); KnownOrthoPoly() := knownOrthoPoly;]; 10 # OrthoPoly(name_String?, _n, p_List?, x_RationalOrNumber?) _ (KnownOrthoPoly()[name] !=? Empty) <-- OrthoPolyNumeric(name, n, p, x);20 # OrthoPoly(name_String?, _n, p_List?, _x) _ (KnownOrthoPoly()[name] !=? Empty) <-- EvaluateHornerScheme(OrthoPolyCoeffs(name, n, p), x);10 # OrthoPolySum(name_String?, c_List?, p_List?, x_RationalOrNumber?) _ (KnownOrthoPoly()[name] !=? Empty) <-- OrthoPolySumNumeric(name, c, p, x);20 # OrthoPolySum(name_String?, c_List?, p_List?, _x) _ (KnownOrthoPoly()[name] !=? Empty) <-- EvaluateHornerScheme(OrthoPolySumCoeffs(name, c, p), x);OrthoPolyNumeric(name_String?, n_Integer?, p_List?, _x) <-- [ Local(value1, value2, value3, ruleCoeffs, index); value1 := 1; ruleCoeffs := Apply(KnownOrthoPoly()[name], {n, p})[1]; value2 := ruleCoeffs[1] + x*ruleCoeffs[2]; index := 1;  While(index<?n) [ index := index + 1; ruleCoeffs := Apply(KnownOrthoPoly()[name], {index, p})[2]; value3 := (ruleCoeffs[1] + x*ruleCoeffs[2])*value2 + ruleCoeffs[3]*value1; value1 := value2; value2 := value3; ]; value2;];OrthoPolySumNumeric(name_String?, c_List?, p_List?, _x) <-- [ Local(value1, value2, value3, ruleCoeffs, ruleCoeffs1, index); value1 := 0; value2 := 0; index := Length(c) - 1;  While(index>=?1) [ ruleCoeffs := Apply(KnownOrthoPoly()[name], {index+1, p})[2]; ruleCoeffs1 := Apply(KnownOrthoPoly()[name], {index+2, p})[2]; value3 := (ruleCoeffs[1] + x*ruleCoeffs[2])*value2 + ruleCoeffs1[3]*value1 + c[index+1]; value1 := value2; value2 := value3; index := index - 1; ];  ruleCoeffs := Apply(KnownOrthoPoly()[name], {1, p})[1]; ruleCoeffs1 := Apply(KnownOrthoPoly()[name], {2, p})[2]; value2 := (ruleCoeffs[1] + x*ruleCoeffs[2])*value2 + ruleCoeffs1[3]*value1 + c[1]; value2;];10 # OrthoPolyCoeffs(name_String?, 0, p_List?) <-- {1};10 # OrthoPolyCoeffs(name_String?, 1, p_List?) <-- Apply(KnownOrthoPoly()[name], {1, p})[1];20 # OrthoPolyCoeffs(name_String?, n_Integer?, p_List?)_(n>?1) <-- [ Local(ruleCoeffs, tmpCoeffs, newCoeffs, prevCoeffs, index, jndex, tmptmpCoeffs, prevCoeffsA, newCoeffsA, tmpCoeffsA);  prevCoeffsA := ZeroVector(n+1); newCoeffsA := ZeroVector(n+1); tmpCoeffsA := ZeroVector(n+1);  prevCoeffs := prevCoeffsA; newCoeffs := newCoeffsA; tmpCoeffs := tmpCoeffsA;  prevCoeffs[1] := 1; ruleCoeffs := Apply(KnownOrthoPoly()[name], {n, p})[1]; newCoeffs[1] := ruleCoeffs[1]; newCoeffs[2] := ruleCoeffs[2];  index := 1;  While(index <? n) [ index := index + 1;  ruleCoeffs := Apply(KnownOrthoPoly()[name], {index, p})[2]; tmpCoeffs[1] := ruleCoeffs[1]*newCoeffs[1] + ruleCoeffs[3]*prevCoeffs[1];  For(jndex:=2, jndex <=? index, jndex:=jndex+1) [ tmpCoeffs[jndex] := ruleCoeffs[1]*newCoeffs[jndex] + ruleCoeffs[3]*prevCoeffs[jndex] + ruleCoeffs[2]*newCoeffs[jndex-1]; ]; tmpCoeffs[index+1] := ruleCoeffs[2]*newCoeffs[index]; tmptmpCoeffs := prevCoeffs; prevCoeffs := newCoeffs; newCoeffs := tmpCoeffs; tmpCoeffs := tmptmpCoeffs; ]; newCoeffs;];OrthoPolySumCoeffs(name_String?, c_List?, p_List?) <-- [ Local(n, ruleCoeffs, ruleCoeffs1, tmpCoeffs, newCoeffs, prevCoeffs, index, jndex, tmptmpCoeffs, prevCoeffsA, newCoeffsA, tmpCoeffsA);  n := Length(c) - 1;  prevCoeffsA := ZeroVector(n+1); newCoeffsA := ZeroVector(n+1); tmpCoeffsA := ZeroVector(n+1);  prevCoeffs := prevCoeffsA; newCoeffs := newCoeffsA; tmpCoeffs := tmpCoeffsA;   For(index:=n, index >=? 1, index:=index-1) [  ruleCoeffs := Apply(KnownOrthoPoly()[name], {index+1, p})[2]; ruleCoeffs1 := Apply(KnownOrthoPoly()[name], {index+2, p})[2]; tmpCoeffs[1] := c[index+1] + ruleCoeffs[1]*newCoeffs[1] + ruleCoeffs1[3]*prevCoeffs[1];  For(jndex:=2, jndex <=? n-index, jndex:=jndex+1) [ tmpCoeffs[jndex] := ruleCoeffs[1]*newCoeffs[jndex] + ruleCoeffs1[3]*prevCoeffs[jndex] + ruleCoeffs[2]*newCoeffs[jndex-1]; ]; If(n-index>?0, tmpCoeffs[n-index+1] := ruleCoeffs[2]*newCoeffs[n-index]); tmptmpCoeffs := prevCoeffs; prevCoeffs := newCoeffs; newCoeffs := tmpCoeffs; tmpCoeffs := tmptmpCoeffs; ];  index:=0; ruleCoeffs := Apply(KnownOrthoPoly()[name], {index+1, p})[1]; ruleCoeffs1 := Apply(KnownOrthoPoly()[name], {index+2, p})[2]; tmpCoeffs[1] := c[index+1] + ruleCoeffs[1]*newCoeffs[1] + ruleCoeffs1[3]*prevCoeffs[1];  For(jndex:=2, jndex <=? n-index, jndex:=jndex+1) [ tmpCoeffs[jndex] := ruleCoeffs[1]*newCoeffs[jndex] + ruleCoeffs1[3]*prevCoeffs[jndex] + ruleCoeffs[2]*newCoeffs[jndex-1]; ]; tmpCoeffs[n-index+1] := ruleCoeffs[2]*newCoeffs[n-index]; tmpCoeffs;];8# OrthoPolyCoeffs(\"Jacobi\", n_Integer?, {0,0}) <--[ Local(i, result); result := ZeroVector(n+1); result[n+1] := (2*n-1)!! /n!;  i := 1; While(2*i<=?n) [  result[n+1-2*i] := -(result[n+3-2*i]*(n-2*i+1)*(n-2*i+2)) / ((2*n-2*i+1)*2*i); i++; ]; result;];OrthoPolyCoeffs(\"Hermite\", n_Integer?, {}) <-- HermiteCoeffs(n);HermiteCoeffs(n_Even?)_(n>?0) <--[ Local(i, k, result); k := Quotient(n,2); result := ZeroVector(n+1); result[1] := (-2)^k*(n-1)!!;  For(i:=1,i<=?k,i++)  result[2*i+1] := Quotient(-2*result[2*i-1] * (k-i+1), (2*i-1)*i);  result;];HermiteCoeffs(n_Odd?)_(n>?0) <--[ Local(i, k, result); k := Quotient(n,2); result := ZeroVector(n+1); result[2] := 2*(-2)^k*(n!!);  For(i:=1,i<=?k,i++)  result[2*i+2] := Quotient(-2*result[2*i] * (k-i+1), i*(2*i+1));  result;];OrthoPolyCoeffs(\"Laguerre\", n_Integer?, {_k}) <--[ Local(i, result); result := ZeroVector(n+1); result[n+1] := (-1)^n/n!;  For(i:=n,i>=?1,i--)  result[i] := -(result[i+1]*i*(k+i))/(n-i+1); result;];OrthoPolyCoeffs(\"Tscheb1\", n_Integer?, {}) <-- ChebTCoeffs(n);OrthoPolyCoeffs(\"Tscheb2\", n_Integer?, {}) <-- ChebUCoeffs(n);1 # ChebTCoeffs(0) <-- {1};2 # ChebTCoeffs(n_Integer?) <--[ Local(i, result); result := ZeroVector(n+1); result[n+1] := 2^(n-1);  i := 1; While(2*i<=?n) [  result[n+1-2*i] := -(result[n+3-2*i]*(n-2*i+2)*(n-2*i+1)) / ((n-i)*4*i); i++; ]; result;];1 # ChebUCoeffs(0) <-- {1};2 # ChebUCoeffs(n_Integer?) <--[ Local(i, result); result := ZeroVector(n+1); result[n+1] := 2^n;  i := 1; While(2*i<=?n) [  result[n+1-2*i] := -(result[n+3-2*i]*(n-2*i+2)*(n-2*i+1)) / ((n-i+1)*4*i); i++; ]; result;];";
        scriptMap.put("OrthoP",scriptString);
        scriptMap.put("OrthoG",scriptString);
        scriptMap.put("OrthoH",scriptString);
        scriptMap.put("OrthoL",scriptString);
        scriptMap.put("OrthoT",scriptString);
        scriptMap.put("OrthoU",scriptString);
        scriptMap.put("OrthoPSum",scriptString);
        scriptMap.put("OrthoGSum",scriptString);
        scriptMap.put("OrthoHSum",scriptString);
        scriptMap.put("OrthoLSum",scriptString);
        scriptMap.put("OrthoTSum",scriptString);
        scriptMap.put("OrthoUSum",scriptString);
        scriptMap.put("EvaluateHornerScheme",scriptString);
        scriptMap.put("OrthoPoly",scriptString);
        scriptMap.put("OrthoPolySum",scriptString);
        scriptMap.put("OrthoPolyNumeric",scriptString);
        scriptMap.put("OrthoPolySumNumeric",scriptString);
        scriptMap.put("OrthoPolyCoeffs",scriptString);
        scriptMap.put("OrthoPolySumCoeffs",scriptString);
        scriptMap.put("HermiteCoeffs",scriptString);
        scriptMap.put("ChebTCoeffs",scriptString);
        scriptMap.put("ChebUCoeffs",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"CForm\",{expression});RulebaseHoldArguments(\"CForm\",{expression, precedence});Function (\"CFormBracketIf\", {predicate, string})[ Check(Boolean?(predicate) And? String?(string), \"Argument\", \"CForm internal error: non-boolean and/or non-string argument of CFormBracketIf\"); If(predicate, ConcatStrings(\"( \", string, \") \"), string);];CFormDoublePrecisionNumber(x_Number?) <--[ Local(i,n,s,f); s := ToString(x); n := Length(s); f := False; For(i := 1, i <=? n, i++) [ If(s[i] =? \"e\" Or? s[i] =? \".\", f := True); ]; If(f, s, s ~ \".\");];CFormMaxPrec() := 60000; 100 # CForm(_x) <-- CForm(x, CFormMaxPrec());110 # CForm(x_Integer?, _p) <-- ToString(x);111 # CForm(x_Zero?, _p) <-- \"0.\";112 # CForm(x_Number?, _p) <-- CFormDoublePrecisionNumber(x);190 # CForm(False, _p) <-- \"false\";190 # CForm(True, _p) <-- \"true\";200 # CForm(x_Atom?, _p) <-- ToString(x);100 # CForm(x_String?, _p) <-- ConcatStrings(\"\\\"\", x, \"\\\"\");LocalSymbols(cformRegularOps) [ cformRegularOps := { {\"+\",\" + \"}, {\"-\",\" - \"}, {\"*\",\" * \"}, {\"/\",\" / \"}, {\":=\",\" = \"}, {\"==\",\" == \"}, {\"=?\",\" == \"}, {\"!=?\",\" != \"}, {\"<=?\",\" <=? \"}, {\">=?\",\" >= \"}, {\"<?\",\" < \"}, {\">?\",\" > \"}, {\"And?\",\" && \"}, {\"Or?\",\" || \"}, {\">>\", \" >> \"}, { \"<<\", \" << \" }, { \"&\", \" & \" }, { \"|\", \" | \" }, { \"%\", \" % \" }, { \"^\", \" ^ \" }, }; CFormRegularOps() := cformRegularOps;];   100 # CForm(+ _y, _p) <-- CFormBracketIf(p<?PrecedenceGet(\"+\"), ConcatStrings(\" + \", CForm(y, RightPrecedenceGet(\"+\")) ) ); 100 # CForm(- _y, _p) <-- CFormBracketIf(p<?PrecedenceGet(\"-\"), ConcatStrings(\" - \", CForm(y, RightPrecedenceGet(\"-\")) ) ); 100 # CForm(_x ^ _y, _p) <-- CFormBracketIf(p<=?PrecedenceGet(\"^\"), ConcatStrings(\"pow(\", CForm(x, CFormMaxPrec()), \", \", CForm(y, CFormMaxPrec()), \")\" ) );100 # CForm(if(_pred)_body, _p) <-- \"if (\"~CForm(pred,60000)~\") \"~CForm(body);100 # CForm(_left else _right, _p) <-- CForm(left)~\" else \"~CForm(right);LocalSymbols(cformMathFunctions) [ cformMathFunctions := { {\"Sqrt\",\"sqrt\"}, {\"Cos\",\"cos\"}, {\"Sin\",\"sin\"}, {\"Tan\",\"tan\"}, {\"Cosh\",\"cosh\"}, {\"Sinh\",\"sinh\"}, {\"Tanh\",\"tanh\"}, {\"Exp\",\"exp\"}, {\"Ln\",\"log\"}, {\"ArcCos\",\"acos\"}, {\"ArcSin\",\"asin\"}, {\"ArcTan\",\"atan\"}, {\"ArcCosh\",\"acosh\"}, {\"ArcSinh\",\"asinh\"}, {\"ArcTanh\",\"atanh\"}, {\"Maximum\",\"max\"}, {\"Minimum\",\"min\"}, {\"Abs\",\"fabs\"}, {\"Floor\",\"floor\"}, {\"Ceil\",\"ceil\"}, {\"!\",\"factorial\"} }; CFormMathFunctions() := cformMathFunctions;]; 120 # CForm(expr_Function?, _p)_(ArgumentsCount(expr)=?2 And? Contains?(AssocIndices(CFormRegularOps()), Type(expr)) ) <-- CFormBracketIf(p<?PrecedenceGet(Type(expr)), ConcatStrings(CForm(FunctionToList(expr)[2], LeftPrecedenceGet(Type(expr))), CFormRegularOps()[Type(expr)], CForm(FunctionToList(expr)[3], RightPrecedenceGet(Type(expr))) ) );120 # CForm(expr_Function?, _p) _ (ArgumentsCount(expr)=?1 And? Contains?(AssocIndices(CFormMathFunctions()), Type(expr)) ) <-- ConcatStrings(CFormMathFunctions()[Type(expr)], \"(\", CForm( FunctionToList(expr)[2], CFormMaxPrec()),\")\" );CFormArgs(list_List?) <--[ Local(i,nr,result); result:=\"\"; nr:=Length(list); For (i:=1,i<=?nr,i++) [ result:=result~CForm(list[i]); If (i<?nr, result:=result~\", \"); ]; result;];200 # CForm(_x, _p)_(Function?(x)) <--[ ConcatStrings(Type(x), \"(\", CFormArgs(Rest(FunctionToList(x))),\")\" );];100 # CForm(Complex(0, 1), _p) <-- \"I\";100 # CForm(Complex(_x, 0), _p) <-- CForm(x, p);110 # CForm(Complex(_x, 1), _p) <-- CForm(x+Hold(I), p);110 # CForm(Complex(0, _y), _p) <-- CForm(Hold(I)*y, p);120 # CForm(Complex(_x, _y), _p) <-- CForm(x+Hold(I)*y, p);100 # CForm(Modulo(_x, _y), _p) <-- CFormBracketIf(p<?PrecedenceGet(\"/\"), ConcatStrings(CForm(x, PrecedenceGet(\"/\")), \" % \", CForm(y, PrecedenceGet(\"/\")) ) );100 # CForm(Nth(_x, _i), _p) <-- ConcatStrings(CForm(x, CFormMaxPrec()), \"[\", CForm(i, CFormMaxPrec()), \"]\");LocalSymbols(cindent) [ cindent:=1; NlIndented():= [ Local(result); result:=\"\"; Local(i); For(i:=1,i<?cindent,i++) [ result:=result~\" \"; ]; result; ]; CIndent() := [ (cindent++); \"\"; ]; CUndent() := [ (cindent--); \"\"; ];]; CFormStatement(_x) <-- CForm(x) ~ \";\" ~ NlIndented();120 # CForm(_x,_p)_(Type(x) =? \"Prog\") <--[ Local(result); result:=CIndent()~\"{\"~NlIndented(); ForEach(item,Rest(FunctionToList(x))) [ result:=result~CFormStatement(item); ]; result:=result~\"}\"~CUndent()~NlIndented(); result;];120 # CForm(For(_from,_to,_step)_body,_p) <-- \"for(\" ~ CForm(from,CFormMaxPrec()) ~ \";\" ~ CForm(to,CFormMaxPrec()) ~ \";\" ~ CForm(step,CFormMaxPrec()) ~ \")\" ~ CIndent() ~ NlIndented() ~ CFormStatement(body) ~ CUndent();120 # CForm(While(_pred)_body, _p) <-- \"while(\" ~ CForm(pred,CFormMaxPrec()) ~ \")\" ~ CIndent() ~ NlIndented() ~ CFormStatement(body) ~ CUndent();";
        scriptMap.put("CForm",scriptString);
        scriptMap.put("CFormDoublePrecisionNumber",scriptString);
        scriptMap.put("CFormArgs",scriptString);
        scriptMap.put("CFormStatement",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(CFormAllFunctions) [    CFormable?(_expr) <-- `CFormable?(@expr, {});  CFormable?(_expr, funclist_List?) <-- [ Local(badfunctions); badfunctions := Difference(`FuncList(@expr), Concat(CFormAllFunctions, funclist)); If(Length(badfunctions)=?0, True, [ If(InVerboseMode(), Echo(Concat({\"CFormable?: Info: unexportable function(s): \"}, badfunctions)) ); False; ] ); ]; HoldArgumentNumber(\"CFormable?\", 1, 1); HoldArgumentNumber(\"CFormable?\", 2, 1);  CFormAllFunctions := MapSingle(ToAtom, Concat(AssocIndices(CFormMathFunctions()), AssocIndices(CFormRegularOps()),  { \"For\", \"While\", \"Prog\", \"Nth\", \"Modulo\", \"Complex\", \"if\", \"else\", \"++\", \"--\", } ));]; ";
        scriptMap.put("CFormable?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CharList(length,item):=[ Local(line,i); line:=\"\"; For(Bind(i,0),LessThan?(i,length),Bind(i,AddN(i,1))) Bind(line, line~item); line;];CharField(width,height) := ArrayCreate(height,CharList(width,\" \"));WriteCharField(charfield):=[ Local(i,len); len:=Length(charfield); For(Bind(i,1),i<=?len,Bind(i,AddN(i,1))) [ WriteString(charfield[i]); NewLine(); ]; True;];ColumnFilled(charfield,column):=[ Local(i,result,len); result:=False; len:=Length(charfield); For(Bind(i, 1),(result =? False) And? (i<=?len),Bind(i,AddN(i,1))) [ If(StringMidGet(column,1,charfield[i]) !=? \" \",result:=True); ]; result;];WriteCharField(charfield,width):=[ Local(pos,length,len); Bind(length, Length(charfield[1])); Bind(pos, 1); While(pos<=?length) [ Local(i,thiswidth); Bind(thiswidth, width); If(thiswidth>?(length-pos)+1, [ Bind(thiswidth, AddN(SubtractN(length,pos),1)); ], [ While (thiswidth>?1 And? ColumnFilled(charfield,pos+thiswidth-1)) [ Bind(thiswidth,SubtractN(thiswidth,1)); ]; If(thiswidth =? 1, Bind(thiswidth, width)); ] ); len:=Length(charfield); For(Bind(i, 1),i<=?len,Bind(i,AddN(i,1))) [ WriteString(StringMidGet(pos,thiswidth,charfield[i])); NewLine(); ]; Bind(pos, AddN(pos, thiswidth)); NewLine(); ]; True;];PutString(charfield,x,y,string):=[ cf[y] := StringMidSet(x,string,cf[y]); True;];MakeOper(x,y,width,height,oper,args,base):=[ Local(result); Bind(result,ArrayCreate(7,0)); ArraySet(result,1,x); ArraySet(result,2,y); ArraySet(result,3,width); ArraySet(result,4,height); ArraySet(result,5,oper); ArraySet(result,6,args); ArraySet(result,7,base); result;];MoveOper(f,x,y):=[ f[1]:=AddN(f[1], x);  f[2]:=AddN(f[2], y);  f[7]:=AddN(f[7], y); ];AlignBase(i1,i2):=[ Local(base); Bind(base, Maximum(i1[7],i2[7])); MoveOper(i1,0,SubtractN(base,(i1[7]))); MoveOper(i2,0,SubtractN(base,(i2[7])));];10 # BuildArgs({}) <-- Formula(ToAtom(\" \"));20 # BuildArgs({_head}) <-- head;30 # BuildArgs(_any) <-- [ Local(item1,item2,comma,base,newitem); Bind(item1, any[1]); Bind(item2, any[2]); Bind(comma, Formula(ToAtom(\",\"))); Bind(base, Maximum(item1[7],item2[7])); MoveOper(item1,0,SubtractN(base,(item1[7]))); MoveOper(comma,AddN(item1[3],1),base); MoveOper(item2,comma[1]+comma[3]+1,SubtractN(base,(item2[7]))); Bind(newitem, MakeOper(0,0,AddN(item2[1],item2[3]),Maximum(item1[4],item2[4]),\"Func\",{item1,comma,item2},base)); BuildArgs(newitem~Rest(Rest(any))); ];FormulaBracket(f):=[ Local(left,right); Bind(left, Formula(ToAtom(\"(\"))); Bind(right, Formula(ToAtom(\")\"))); left[4]:=f[4]; right[4]:=f[4]; MoveOper(left,f[1],f[2]); MoveOper(f,2,0); MoveOper(right,f[1]+f[3]+1,f[2]); MakeOper(0,0,right[1]+right[3],f[4],\"Func\",{left,f,right},f[7]);];1 # Formula(f_Atom?) <-- MakeOper(0,0,Length(ToString(f)),1,\"ToAtom\",ToString(f),0);2 # Formula(_xx ^ _yy) <--[ Local(l,r); Bind(l, BracketOn(Formula(xx),xx,LeftPrecedenceGet(\"^\"))); Bind(r, BracketOn(Formula(yy),yy,RightPrecedenceGet(\"^\"))); MoveOper(l,0,r[4]); MoveOper(r,l[3],0); MakeOper(0,0,AddN(l[3],r[3]),AddN(l[4],r[4]),\"Func\",{l,r},l[2]+l[4]-1);];10 # FormulaArrayItem(xx_List?) <--[ Local(sub,height); sub := {}; height := 0; ForEach(item,xx) [ Local(made); made := FormulaBracket(Formula(item)); If(made[4] >? height,Bind(height,made[4])); DestructiveAppend(sub,made); ]; MakeOper(0,0,0,height,\"List\",sub,height>>1);];20 # FormulaArrayItem(_item) <-- Formula(item);2 # Formula(xx_List?) <--[ Local(sub,width,height); sub:={}; width := 0; height := 1; ForEach(item,xx) [ Local(made); made := FormulaArrayItem(item); If(made[3] >? width,Bind(width,made[3])); MoveOper(made,0,height); Bind(height,AddN(height,AddN(made[4],1))); DestructiveAppend(sub,made); ]; Local(thislength,maxlength); maxlength:=0; ForEach(item,xx) [ thislength:=0; if(List?(item)) [thislength:=Length(item);]; if (maxlength<?thislength) [maxlength:=thislength;]; ]; If(maxlength>?0, [ Local(i,j); width:=0; For(j:=1,j<=?maxlength,j++) [ Local(w); w := 0; For(i:=1,i<=?Length(sub),i++) [ if (List?(xx[i]) And? j<=?Length(xx[i])) If(sub[i][6][j][3] >? w,w := sub[i][6][j][3]); ]; For(i:=1,i<=?Length(sub),i++) [ if (List?(xx[i]) And? j<=?Length(xx[i])) MoveOper(sub[i][6][j],width,0); ]; width := width+w+1; ]; For(i:=1,i<=?Length(sub),i++) [ sub[i][3] := width; ]; ] ); sub := MakeOper(0,0,width,height,\"List\",sub,height>>1); FormulaBracket(sub);];2 # Formula(_xx / _yy) <--[ Local(l,r,dash,width); Bind(l, Formula(xx)); Bind(r, Formula(yy)); Bind(width, Maximum(l[3],r[3])); Bind(dash, Formula(ToAtom(CharList(width,\"-\")))); MoveOper(dash,0,l[4]); MoveOper(l,(SubtractN(width,l[3])>>1),0); MoveOper(r,(SubtractN(width,r[3])>>1),AddN(dash[2], dash[4])); MakeOper(0,0,width,AddN(r[2], r[4]),\"Func\",{l,r,dash},dash[2]);];RulebaseHoldArguments(\"BracketOn\",{op,f,prec});RuleHoldArguments(\"BracketOn\",3,1,Function?(f) And? ArgumentsCount(f) =? 2 And? Infix?(Type(f)) And? PrecedenceGet(Type(f)) >? prec)[ FormulaBracket(op);];RuleHoldArguments(\"BracketOn\",3,2,True)[ op;];10 # Formula(f_Function?)_(ArgumentsCount(f) =? 2 And? Infix?(Type(f))) <--[ Local(l,r,oper,width,height,base); Bind(l, Formula(f[1])); Bind(r, Formula(f[2])); Bind(l, BracketOn(l,f[1],LeftPrecedenceGet(Type(f)))); Bind(r, BracketOn(r,f[2],RightPrecedenceGet(Type(f)))); Bind(oper, Formula(f[0])); Bind(base, Maximum(l[7],r[7])); MoveOper(oper,AddN(l[3],1),SubtractN(base,(oper[7]))); MoveOper(r,oper[1] + oper[3]+1,SubtractN(base,(r[7]))); MoveOper(l,0,SubtractN(base,(l[7]))); Bind(height, Maximum(AddN(l[2], l[4]),AddN(r[2], r[4]))); MakeOper(0,0,AddN(r[1], r[3]),height,\"Func\",{l,r,oper},base);];11 # Formula(f_Function?) <--[ Local(head,args,all); Bind(head, Formula(f[0])); Bind(all, Rest(FunctionToList(f))); Bind(args, FormulaBracket(BuildArgs(MapSingle(\"Formula\",Apply(\"Hold\",{all}))))); AlignBase(head,args); MoveOper(args,head[3],0); MakeOper(0,0,args[1]+args[3],Maximum(head[4],args[4]),\"Func\",{head,args},head[7]);];RulebaseHoldArguments(\"RenderFormula\",{cf,f,x,y});RuleHoldArguments(\"RenderFormula\",4,1,f[5] =? \"ToAtom\" And? f[6] =? \"(\" And? f[4] >? 1)[ Local(height,i); Bind(x, AddN(x,f[1])); Bind(y, AddN(y,f[2])); Bind(height, SubtractN(f[4],1)); cf[y] := StringMidSet(x, \"/\", cf[y]); cf[AddN(y,height)] := StringMidSet(x, \"\\\\\", cf[AddN(y,height)]); For (Bind(i,1),LessThan?(i,height),Bind(i,AddN(i,1))) cf[AddN(y,i)] := StringMidSet(x, \"|\", cf[AddN(y,i)]);];RuleHoldArguments(\"RenderFormula\",4,1,f[5] =? \"ToAtom\" And? f[6] =? \")\" And? f[4] >? 1)[ Local(height,i); Bind(x, AddN(x,f[1])); Bind(y, AddN(y,f[2])); Bind(height, SubtractN(f[4],1)); cf[y] := StringMidSet(x, \"\\\\\", cf[y]); cf[y+height] := StringMidSet(x, \"/\", cf[y+height]); For (Bind(i,1),LessThan?(i,height),Bind(i,AddN(i,1))) cf[AddN(y,i)] := StringMidSet(x, \"|\", cf[AddN(y,i)]);];RuleHoldArguments(\"RenderFormula\",4,5,f[5] =? \"ToAtom\")[ cf[AddN(y, f[2]) ]:= StringMidSet(AddN(x,f[1]),f[6],cf[AddN(y, f[2]) ]);];RuleHoldArguments(\"RenderFormula\",4,6,True)[ ForEach(item,f[6]) [ RenderFormula(cf,item,AddN(x, f[1]),AddN(y, f[2])); ];];LocalSymbols(formulaMaxWidth) [ SetFormulaMaxWidth(width):= [ formulaMaxWidth := width; ]; FormulaMaxWidth() := formulaMaxWidth; SetFormulaMaxWidth(60);]; Function(\"PrettyForm\",{ff})[ Local(cf,f); f:=Formula(ff); cf:=CharField(f[3],f[4]); RenderFormula(cf,f,1,1); NewLine(); WriteCharField(cf,FormulaMaxWidth()); DumpErrors(); True;];EvalFormula(f):=[ Local(result); result:= ListToFunction({ToAtom(\"=?\"),f,Eval(f)}); PrettyForm(result); True;];HoldArgument(\"EvalFormula\",f);";
        scriptMap.put("PrettyForm",scriptString);
        scriptMap.put("EvalFormula",scriptString);
        scriptMap.put("BuildArgs",scriptString);
        scriptMap.put("FormulaArrayItem",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"OMError\", {x,y});RulebaseHoldArguments(\"OMError\", {x,y,z});RulebaseHoldArguments(\"OMREP\",{});RuleHoldArguments(\"OMREP\",0,1,True)[ OMREP(0);];RulebaseHoldArguments(\"OMREP\",{count});LocalSymbols(input,stringOut,result)RuleHoldArguments(\"OMREP\",1,1,True)[ Local(input,stringOut,result); While(Not?(IsExitRequested())) [ Bind(errorObject, False); ExceptionCatch(Bind(input, PipeFromString(ConcatStrings(ReadCmdLineString(\"\"),\" \"))OMRead()), Bind(errorObject,OMGetCoreError())); If(Not?(errorObject =? False), errorObject);  If (Not?(IsExitRequested()) And? errorObject =? False, [ Bind(stringOut,\"\"); Bind(result,False); ExceptionCatch(Bind(stringOut,PipeToString()[Secure(Bind(result,Eval(input)));]), Bind(errorObject,OMGetCoreError())); If(Not?(errorObject =? False), errorObject);  If(Not?(stringOut =? \"\"), WriteString(stringOut)); SetGlobalLazyVariable(%,result); If(PrettyPrinterGet()=?\"\", [ Apply(\"OMForm\",{result}); ], Apply(PrettyPrinterGet(),{result})); If(count >? 0 And? (count:=count-1) =? 0, Exit()); ]); ];];LocalSymbols(omindent) [  OMIndent() := [omindent := omindent + 2;]; OMUndent() := [omindent := omindent - 2;]; OMClearIndent() := [omindent := 0;]; OMIndentSpace() := Space(omindent);  OMClearIndent();]; 10 # OMForm(_expression) <-- [ OMClearIndent(); OMEcho(\"<OMOBJ>\"); OMIndent(); If(Atom?(expression), If(expression =? ToAtom(\"%\"), Secure(expression := Eval(expression)) ) ); OMFormExpression(expression); OMUndent(); OMEcho(\"</OMOBJ>\"); ];10 # OMFormExpression(i_String?) <-- OMEcho(\"<OMSTR>\"~i~\"</OMSTR>\");11 # OMFormExpression(i_Integer?) <-- OMEcho(\"<OMI>\"~ToString(i)~\"</OMI>\");12 # OMFormExpression(i_Number?) <-- OMEcho(\"<OMF dec=\\\"\"~ToString(i)~\"\\\"/>\");13 # OMFormExpression(i_Constant?)_(OMSymbol()[ ToString(i) ] !=? Empty) <-- OMEcho(\"<OMS cd=\\\"\"~OMSymbol()[ ToString(i) ][1] ~\"\\\" name=\\\"\"~OMSymbol()[ ToString(i) ][2]~\"\\\"/>\" );14 # OMFormExpression(i_Constant?) <-- OMEcho(\"<OMV name=\\\"\"~ToString(i)~\"\\\"/>\");15 # OMFormExpression(i_Variable?)_(OMSymbol()[ ToString(i) ] !=? Empty) <-- OMEcho(\"<OMS cd=\\\"\"~OMSymbol()[ ToString(i) ][1] ~\"\\\" name=\\\"\"~OMSymbol()[ ToString(i) ][2]~\"\\\"/>\" );16 # OMFormExpression(i_Variable?) <-- OMEcho(\"<OMV name=\\\"\"~ToString(i)~\"\\\"/>\");16 # OMFormExpression(i_Variable?)_(i =? Empty) <-- False; 10 # OMFormExpression(function_Function?)_(Type(function) =? \"OMError\") <-- [ Local(cd, name); If(List?(function[1]), [ cd := function[1][1]; name := function[1][2]; ], [ cd := \"error\"; name := function[1]; ]); OMEcho(\"<OME>\"); OMIndent(); OMEcho(\"<OMS cd=\\\"\"~cd~\"\\\" name=\\\"\"~name~\"\\\"/>\"); ForEach(i, Rest(function)) OMFormExpression(i); OMUndent(); OMEcho(\"</OME>\"); ];10 # OMFormExpression(function_Function?)_(Type(function) =? \"OME\") <-- [ OMEcho(\"<OME>\"); OMIndent(); ForEach(i, function) OMFormExpression(i); OMUndent(); OMEcho(\"</OME>\"); ];10 # OMFormExpression(function_Function?)_(Type(function) =? \"OMS\") <-- OMEcho(\"<OMS cd=\\\"\"~function[1]~\"\\\" name=\\\"\"~function[2]~\"\\\"/>\");10 # OMFormExpression(function_Function?)_(Type(function) =? \"OMBIND\") <-- [ OMEcho(\"<OMBIND>\"); OMIndent(); ForEach(i, function) OMFormExpression(i); OMUndent(); OMEcho(\"</OMBIND>\"); ];10 # OMFormExpression(function_Function?)_(Type(function) =? \"OMBVAR\") <-- [ OMEcho(\"<OMBVAR>\"); OMIndent(); ForEach(i, function) OMFormExpression(i); OMUndent(); OMEcho(\"</OMBVAR>\"); ];10 # OMFormExpression(function_Function?)_(Type(function) =? \"OMA\") <-- [   OMEcho(\"<OMA>\"); OMIndent(); ForEach(i, function) OMFormExpression(i); OMUndent(); OMEcho(\"</OMA>\"); ];11 # OMFormExpression(function_Function?) <-- [ OMEcho(\"<OMA>\"); OMIndent(); OMFormFunction(function); OMUndent(); OMEcho(\"</OMA>\"); ];11 # OMFormFunction(function_Function?) <-- [ Local(arity); arity := Length(function); OMEcho(\"<OMS cd=\\\"piper\\\" name=\\\"\"~Type(function)~\"\\\"/>\"); If(arity >? 0, ForEach(arg, function) OMFormExpression(arg)); ];10 # OMFormFunction(function_Function?)_(OMSymbol()[ Type(function) ] !=? Empty) <-- [ Local(symbolDef);    symbolDef := OMSymbol()[ OMSignature(function) ]; If(symbolDef =? Empty, symbolDef := OMSymbol()[ Type(function) ] ); If(symbolDef =? Empty Or? Length(symbolDef) <? 3 Or? symbolDef[3] =? {}, [ OMEcho(\"<OMS cd=\\\"\"~symbolDef[1]~\"\\\" name=\\\"\"~symbolDef[2]~\"\\\"/>\"); ForEach(arg, function) OMFormExpression(arg); ], [ Local(result); result := OMApplyMapping(function, symbolDef[3]);  If(List?(result), [ result := ListToFunction(Subst($, function[0]) result); OMFormExpression(result[0]); ForEach(i, result) OMFormExpression(i); ], If(result =? Empty, Echo(\"No rule matched \", function, symbolDef[3]), Echo(\"Unexpected result value from OMApplyMapping(): \", result) ) ); ] ); ];OMWrite(_expression) <--[ Write(expression);];OMEcho(_expression) <--[ OMIndentSpace(); Write(expression); NewLine();];OMEcho(expression_String?) <--[ OMIndentSpace(); WriteString(expression); NewLine();];OMEcho(expression_List?) <--[ ForEach(arg, expression) [ If (String?(arg), WriteString(arg), Write(arg)); ]; NewLine();];OMEscape(_expression) <--[ \"<![CDATA[\"~ToString(expression)~\"]]>\";];OMEscapeString(_expression_String?) <--[ \"<![CDATA[\"~expression~\"]]>\";];OMWriteEscape(_expression) <--[ WriteString(OMEscape(expression));];OMWriteStringEscape(expression_String?) <--[ WriteString(OMEscapeString(expression));];OMEchoEscape(_expression) <--[ OMWriteEscape(expression); NewLine();];OMEchoEscape(expression_String?) <--[ OMWriteStringEscape(expression); NewLine();];OMEchoEscape(expression_List?) <--[ WriteString(\"<![CDATA[\"); ForEach(arg, expression) [ If (String?(arg), WriteString(arg), Write(arg)); ]; WriteString(\"]]>\"); NewLine();];HoldArgumentNumber(\"OMForm\",1,1);OMSignature(_function) <-- \"\";OMSignature(function_Function?) <--[ Local(makeSig); makeSig := {ConcatStrings, Type(function), \"_\"}; Local(type); type := \"\"; ForEach(arg, function) [ If(Type(arg) =? \"List\", type := \"L\", If(Function?(arg), type := \"F\", If(Integer?(arg), type := \"I\", type := \"V\" ) ) ); DestructiveAppend(makeSig, type); ]; Secure(Eval(ListToFunction(makeSig)));];HoldArgumentNumber(\"OMSignature\", 1, 1);LocalSymbols(omtoken) [ OMNextToken() := [ omtoken := XmlExplodeTag(ToString(ReadToken())); ]; OMToken() := omtoken;]; OMRead():=[ Local(result); ExceptionCatch( [ XmlTokenizer(); OMNextToken(); result := MatchOMOBJ(OMToken()); DefaultTokenizer(); ], [ result := OMGetCoreError(); DefaultTokenizer(); ]); result;];OMDump(str):=PipeFromString(str~\" EndOfFile\")[ Local(result); XmlTokenizer(); OMNextToken(); While(OMToken() !=? \"EndOfFile\") [ Echo(\"Exploded \",OMToken()); OMNextToken(); ]; DefaultTokenizer(); True;];10 # MatchClose(_x)_(x =? OMToken()) <-- [OMNextToken();True;];20 # MatchClose(_x) <-- Check(False, \"Syntax\", PipeToString()Echo(\"encodingError:unexpected closing brace\")); 10 # MatchOMOBJ(XmlTag(\"OMOBJ\",_attributes,\"Open\")) <--[  Local(result); OMNextToken(); result := ReadOMOBJ(OMToken()); MatchClose(XmlTag(\"OMOBJ\",{},\"Close\")); result;];10 # MatchOMOBJ(XmlTag(\"OMOBJ\",_attributes,\"OpenClose\")) <--[ OMNextToken();   Empty;];20 # MatchOMOBJ(_rest) <-- Check(False, \"Type\", PipeToString()Echo(\"encodingError:not an OMOBJ :\",rest));10 # ReadOMOBJ(XmlTag(\"OMOBJ\",_attributes,\"Close\")) <--[  Empty;];10 # ReadOMOBJ(XmlTag(\"OMI\",{},\"Open\")) <--[ Local(result); OMNextToken(); result := ToAtom(OMToken()); OMNextToken(); MatchClose(XmlTag(\"OMI\",{},\"Close\")); result;];10 # ReadOMOBJ(XmlTag(\"OMV\",{{\"NAME\",_name}},\"OpenClose\")) <--[ OMNextToken(); ToAtom(name);];10 # ReadOMOBJ(XmlTag(\"OMF\",{{\"DEC\",_dec}},\"OpenClose\")) <--[ OMNextToken(); ToAtom(dec);];10 # ReadOMOBJ(XmlTag(\"OMSTR\",{},\"Open\")) <--[ Local(result); OMNextToken(); If(String?(OMToken()), [result := OMToken(); OMNextToken();], result := \"\"); MatchClose(XmlTag(\"OMSTR\",{},\"Close\")); result;];10 # ReadOMOBJ(XmlTag(\"OMSTR\",{},\"OpenClose\")) <--[ OMNextToken(); \"\";];10 # ReadOMOBJ(XmlTag(\"OMA\",{},\"Open\")) <--[ Local(result, new); result:={}; OMNextToken(); While (OMToken() !=? XmlTag(\"OMA\",{},\"Close\")) [ new:=ReadOMOBJ(OMToken()); DestructiveAppend(result,new); ]; MatchClose(XmlTag(\"OMA\",{},\"Close\")); OMApplyReverseMapping(ListToFunction(result));];10 # ReadOMOBJ(XmlTag(\"OMBIND\",{},\"Open\")) <--[ Local(result, new); result:={}; OMNextToken(); While (OMToken() !=? XmlTag(\"OMBIND\",{},\"Close\")) [ new:=ReadOMOBJ(OMToken()); DestructiveAppend(result,new); ]; MatchClose(XmlTag(\"OMBIND\",{},\"Close\")); result;];10 # ReadOMOBJ(XmlTag(\"OMBVAR\",{},\"Open\")) <--[ Local(result, new); result:={}; OMNextToken(); While (OMToken() !=? XmlTag(\"OMBVAR\",{},\"Close\")) [ new:=ReadOMOBJ(OMToken()); DestructiveAppend(result,new); ]; MatchClose(XmlTag(\"OMBVAR\",{},\"Close\")); result;];10 # OMApplyReverseMapping(piperExp_Function?) <-- piperExp;10 # OMApplyReverseMapping(piperExp_Function?)_(OMSymbol()[ Type(piperExp) ] !=? Empty) <-- [ Local(symbolDef, result); symbolDef := OMSymbol()[ Type(piperExp) ]; If(symbolDef[4] =? {}, result := piperExp, [ result := OMApplyMapping(piperExp, symbolDef[4]); result := Subst($, piperExp[0]) result; If(List?(result), result := ListToFunction(result)); ] ); result; ];10 # OMApplyMapping(_function, _mapping) <--[ Local(expandRules, result); expandRules := { _(_path) <- OMPathSelect(path, function) }; expandRules[1][2][2] := function; mapping := (mapping /: expandRules); Local(ruleMatched); ruleMatched := False; If(Type(mapping) =? \"|\", [ mapping := Flatten(mapping, \"|\"); ForEach(rule, mapping) If(Not? ruleMatched, [ If(Type(rule) =? \"_\", If( Eval(rule[2]), [ result := rule[1]; ruleMatched := True; ] ), [ result := rule; ruleMatched := True; ] ); ] ); ], [ If(Type(mapping) =? \"_\", If(Eval(mapping[2]), result := mapping[1], result := FunctionToList(function) ), result := mapping ); ruleMatched := True; ] ); If(ruleMatched, If(Type(result) =? \"~\", If(Length(result) =? 2, result[1]~result[2], result), result), Empty);];11 # OMPathSelect(path_Number?, _expression) <--[ If(path >=? 0 And? path <=? Length(expression), expression[path], Undefined);];11 # OMPathSelect(path_List?, _expression) <--[ ForEach(i, path) If(Function?(expression) And? i >=? 0 And? i <=? Length(expression), expression := expression[i], Undefined); expression;];HoldArgumentNumber(\"OMPathSelect\", 2, 2);100 # ReadOMOBJ(XmlTag(\"OMS\", _attributes, \"OpenClose\")) <--[ OMNextToken(); Local(omcd, omname); omcd := attributes[\"CD\"]; omname := attributes[\"NAME\"]; If(omcd =? Empty Or? omname =? Empty, OMCheck(False, \"Argument\", OMError({\"moreerrors\", \"encodingError\"}, PipeToString()Echo(\"missing \\\"cd\\\" or \\\"name\\\" attribute: \",attributes))), [ Local(cdTable, piperform); cdTable := OMSymbolReverse()[ omcd ]; If(cdTable !=? Empty, piperform := cdTable[ omname ]);    If(piperform =? Empty, If(cd =? mathpiper, ToAtom(omname), OMS(omcd, omname)), If(String?(piperform), ToAtom(piperform), piperform)); ] );];101 # ReadOMOBJ(_rest) <-- OMCheck(False, \"Unimplemented\", OMError({\"moreerrors\", \"encodingError\"}, PipeToString()Echo(\"unhandled tag: \",rest)));Macro(OMCheck,{predicate,error})[ If(Not?(@predicate), [ Assert(\"omErrorObject\", @error) False; Check(False, \"Undefined\", \"omErrorObject\"); ] , True);];OMGetCoreError():=[ Local(result); result := ExceptionGet();  If(result !=? False, If( Error?(\"omErrorObject\"), [result := GetError(\"omErrorObject\"); ], [result := OMError({\"moreerrors\", \"unexpected\"}, result); ]) ); result;];LocalSymbols(omsymbol, omsymbolreverse) [  omsymbol := {}; omsymbolreverse := {};  OMSymbol() := omsymbol; OMSymbolReverse() := omsymbolreverse;]; OMDef(_piperform, omcd_String?, omname_String?, _directMapping, _reverseMapping) <--[ Local(cdTable); If(String?(piperform), OMSymbol()[ piperform ] := {omcd, omname, directMapping, reverseMapping} ); cdTable := OMSymbolReverse()[ omcd ]; If(cdTable =? Empty, OMSymbolReverse()[ omcd ] := {{omname, piperform}}, [ Local(oldMathPiperform); oldMathPiperform := cdTable[ omname ]; If(oldMathPiperform =? Empty, cdTable[ omname ] := piperform, [ If(oldMathPiperform !=? piperform, [ cdTable[ omname ] := piperform; Echo(\"Warning: the mapping for \", omcd, \":\", omname, \" was already defined as \", oldMathPiperform, \", but is redefined now as \", piperform ); ] ); ] ); ] ); True;];OMDef(_piperform, omcd_String?, omname_String?)<-- OMDef(piperform, omcd, omname, {}, {});OMDef(piperalias_String?, pipername_String?) <--[ OMSymbol()[ piperalias ] := OMSymbol()[ pipername ];];HoldArgumentNumber(\"OMDef\", 5, 4);HoldArgumentNumber(\"OMDef\", 5, 5);OMDef( {}, \"set1\",\"emptyset\" );OMDef( \"List\", \"set1\",\"set\" );OMDef( \"List\", \"linalg2\",\"matrix\" );OMDef( \"List\", \"linalg2\",\"matrixrow\" );OMDef( \"List\", \"linalg2\",\"vector\" );OMDef( \"List\", \"list1\",\"list\" );OMDef( \"Infinity\" , \"nums1\", \"infinity\" );OMDef( \"Undefined\", \"nums1\", \"NaN\" );OMDef( \"And?\" , \"logic1\", \"and\" );OMDef( \"False\", \"logic1\", \"false\" );OMDef( \"Or?\" , \"logic1\", \"or\" );OMDef( \"True\" , \"logic1\", \"true\" );OMDef( \"&\" , mathpiper, \"bitwise_and\" );OMDef( \"|\" , mathpiper, \"bitwise_or\" );OMDef( \"%\" , mathpiper, \"bitwise_xor\" );OMDef( \"/\" , \"arith1\", \"divide\");OMDef( \"/\" , \"nums1\", \"rational\", {$, _1, _2}_(Rational?(_1/_2)) | {OMS(\"arith1\", \"divide\"), _1, _2}, {/, _1, _2});OMDef( \"-\" , \"arith1\", \"unary_minus\");OMDef( \"-\" , \"arith1\", \"minus\" );OMDef( \"+\" , \"arith1\", \"plus\" );OMDef( \"^\" , \"arith1\", \"power\" );OMDef( \"*\" , \"arith1\", \"times\" );OMDef( \"I\", \"nums1\", \"i\" );OMDef( \"CachedConstant\", mathpiper, \"CachedConstant\" );OMDef( \"AssignCachedConstants\", mathpiper, \"AssignCachedConstants\" );OMDef( \"ClearCachedConstants\", mathpiper, \"ClearCachedConstants\" );OMDef( \"Pi\", \"nums1\", \"pi\" );OMDef( \"ArcSin\" , \"transc1\",\"arcsin\" );OMDef( \"ArcCos\" , \"transc1\",\"arccos\" );OMDef( \"ArcTan\" , \"transc1\",\"arctan\" );OMDef( \"ArcSec\" , \"transc1\",\"arcsec\" );OMDef( \"ArcCsc\" , \"transc1\",\"arccsc\" );OMDef( \"ArcCot\" , \"transc1\",\"arccot\" );OMDef( \"ArcSinh\", \"transc1\",\"arcsinh\" );OMDef( \"ArcCosh\", \"transc1\",\"arccosh\" );OMDef( \"ArcTanh\", \"transc1\",\"arctanh\" );OMDef( \"ArcSech\", \"transc1\",\"arcsech\" );OMDef( \"ArcCsch\", \"transc1\",\"arccsch\" );OMDef( \"ArcCoth\", \"transc1\",\"arccoth\" );OMDef( \"Sin\" , \"transc1\",\"sin\" );OMDef( \"Cos\" , \"transc1\",\"cos\" );OMDef( \"Tan\" , \"transc1\",\"tan\" );OMDef( \"Sec\" , \"transc1\",\"sec\" );OMDef( \"Csc\" , \"transc1\",\"csc\" );OMDef( \"Cot\" , \"transc1\",\"cot\" );OMDef( \"Sinh\" , \"transc1\",\"sinh\" );OMDef( \"Cosh\" , \"transc1\",\"cosh\" );OMDef( \"Tanh\" , \"transc1\",\"tanh\" );OMDef( \"Sech\" , \"transc1\",\"sech\" );OMDef( \"Csch\" , \"transc1\",\"csch\" );OMDef( \"Coth\" , \"transc1\",\"coth\" );OMDef( \"Exp\" , \"transc1\",\"exp\" );OMDef( \"Ln\" , \"transc1\",\"ln\" );OMDef( \"Not?\", \"logic1\",\"not\" );OMDef( \"=?\" , \"relation1\",\"eq\" );OMDef( \">=?\", \"relation1\",\"geq\" );OMDef( \">\" , \"relation1\",\"gt\" );OMDef( \"<=?\", \"relation1\",\"leq\" );OMDef( \"<?\" , \"relation1\",\"lt\" );OMDef( \"!=?\", \"relation1\",\"neq\" );OMDef( \"Gcd\", \"arith1\",\"gcd\" );OMDef( \"Sqrt\", \"arith1\",\"root\", { $, _1, 2 }, $(_1)_(_2=?2) | (_1^(1/_2)) );OMDef( \"Abs\", \"arith1\",\"abs\" );OMDef( \"Lcm\", \"arith1\",\"lcm\" );OMDef( \"Floor\", \"rounding1\",\"floor\" );OMDef( \"Ceil\" , \"rounding1\",\"ceiling\" );OMDef( \"Round\", \"rounding1\",\"round\" );OMDef( \"Quotient\" , mathpiper,\"div\" );OMDef( \"Modulo\" , mathpiper,\"mod\" );OMDef( \"Expand\", mathpiper,\"expand\" );OMDef( \"Object\", mathpiper,\"object\" );OMDef( \"Sign\" , mathpiper,\"sign\" );OMDef( \"Implies?\" , \"logic1\",\"implies\" );OMDef( \"Equivales?\" , \"logic1\",\"equivalent\" );OMDef( \"CNF\" , mathpiper,\"cnf\" );OMDef( \"LogicSimplify\", mathpiper,\"logic_simplify\" );OMDef( \"CanProve\" , mathpiper,\"can_prove\" );OMDef( \"LogicRemoveTautologies\", mathpiper,\"logic_remove_tautologies\" );OMDef( \"Subsumes\" , mathpiper,\"subsumes\" );OMDef( \"Complex\" , \"complex1\",\"complex_cartesian\" );OMDef( \"Re\" , \"complex1\",\"real\" );OMDef( \"Im\" , \"complex1\",\"imaginary\" );OMDef( \"Conjugate\", \"complex1\",\"conjugate\" );OMDef( \"Arg\" , \"complex1\",\"argument\" );OMDef( \"Complex?\", mathpiper,\"is_complex\" );OMDef( \"Integrate\", \"calculus1\",\"defint\",  { $, _2 .. _3, OMBIND(OMS(\"fns1\", \"lambda\"), OMBVAR(_1), _4) }, { $, _{2,2,1}, _{1,1}, _{1,2}, _{2,3} } );OMDef( \"AntiDeriv\", mathpiper,\"AntiDeriv\" );OMDef( \"Minimum\", \"minmax1\",\"min\", { \"<OMA>\", \"<OMS cd=\\\"set1\\\" name=\\\"set\\\"/>\", 1,2,3,4,5,6,7,8,9,10,11,12,13,14, \"</OMS>\", \"</OMA>\" }, ($)~_1 );OMDef( \"Maximum\", \"minmax1\",\"max\", { \"<OMA>\", \"<OMS cd=\\\"set1\\\" name=\\\"set\\\"/>\", 1,2,3,4,5,6,7,8,9,10,11,12,13,14, \"</OMS>\", \"</OMA>\" }, ($)~_1 );OMDef( \"!\", \"integer1\",\"factorial\" );OMDef( \"BinomialCoefficient\", \"combinat1\",\"binomial\" );OMDef( \"!!\", mathpiper,\"double_factorial\" );OMDef( \"***\", mathpiper,\"partial_factorial\" );OMDef( \"Add\", mathpiper,\"Add\" );OMDef( \"Sum\", \"arith1\",\"sum\",  { $, _2 .. _3, OMBIND(OMS(\"fns1\", \"lambda\"), OMBVAR(_1), _4) }, { $, _{2,2,1}, _{1,1}, _{1,2}, _{2,3} } );OMDef( \"Product\", mathpiper,\"Product\" );OMDef( \"Taylor\", mathpiper,\"Taylor\" );OMDef( \"Subfactorial\", mathpiper,\"Subfactorial\" );OMDef(\"Limit\", \"limit1\",\"limit\", { _0, _2, OMS(\"limit1\", \"under\"), OMBIND(OMS(\"fns1\", \"lambda\"), OMBVAR(_1), _4) }_(_3=?Left) |{ _0, _2, OMS(\"limit1\", \"above\"), OMBIND(OMS(\"fns1\", \"lambda\"), OMBVAR(_1), _4) }_(_3=?Right) |{ _0, _2, OMS(\"limit1\", \"both_sides\"), OMBIND(OMS(\"fns1\", \"lambda\"), OMBVAR(_1), _3) }, { _0, _{3,2,1}, _1, Left, _{3,3}}_(_2=?OMS(\"limit1\", \"below\")) |{_0, _{3,2,1}, _1, Right, _{3,3}}_(_2=?OMS(\"limit1\", \"above\")) |{_0, _{3,2,1}, _1, _{3,3}} );OMDef( \"BellNumber\", mathpiper,\"BellNumber\" );OMDef( \"CatalanNumber\", mathpiper,\"CatalanNumber\" );OMDef( \"DigitalRoot\", mathpiper,\"DigitalRoot\" );OMDef( \"Divisors\", mathpiper,\"Divisors\" );OMDef( \"DivisorsSum\", mathpiper,\"DivisorsSum\" );OMDef( \"Euler\", mathpiper,\"Euler\" );OMDef( \"EulerArray\", mathpiper,\"EulerArray\" );OMDef( \"Eulerian\", mathpiper,\"Eulerian\" );OMDef( \"FermatNumber\", mathpiper,\"FermatNumber\" );OMDef( \"GetPrimePower\", mathpiper,\"GetPrimePower\" );OMDef( \"HarmonicNumber\", mathpiper,\"HarmonicNumber\" );OMDef( \"IntLog\", mathpiper,\"IntLog\" );OMDef( \"IntNthRoot\", mathpiper,\"IntNthRoot\" );OMDef( \"AmicablePair?\", mathpiper,\"AmicablePair?\" );OMDef( \"CarmichaelNumber?\", mathpiper,\"CarmichaelNumber?\" );OMDef( \"Composite?\", mathpiper,\"Composite?\" );OMDef( \"Coprime?\", mathpiper,\"Coprime?\" );OMDef( \"IrregularPrime?\", mathpiper,\"IrregularPrime?\" );OMDef( \"Perfect?\", mathpiper,\"Perfect?\" );OMDef( \"Prime?\", mathpiper,\"Prime?\" );OMDef( \"PrimePower?\", mathpiper,\"PrimePower?\" );OMDef( \"QuadraticResidue?\", mathpiper,\"QuadraticResidue?\" );OMDef( \"SmallPrime?\", mathpiper,\"SmallPrime?\" );OMDef( \"SquareFree?\", mathpiper,\"SquareFree?\" );OMDef( \"TwinPrime?\", mathpiper,\"TwinPrime?\" );OMDef( \"LegendreSymbol\", mathpiper,\"LegendreSymbol\" );OMDef( \"Moebius\", mathpiper,\"Moebius\" );OMDef( \"NextPrime\", mathpiper,\"NextPrime\" );OMDef( \"NextPseudoPrime\", mathpiper,\"NextPseudoPrime\" );OMDef( \"PartitionsP\", mathpiper,\"PartitionsP\" );OMDef( \"ProductPrimesTo257\", mathpiper,\"ProductPrimesTo257\" );OMDef( \"ProperDivisors\", mathpiper,\"ProperDivisors\" );OMDef( \"ProperDivisorsSum\", mathpiper,\"ProperDivisorsSum\" );OMDef( \"Repunit\", mathpiper,\"Repunit\" );OMDef( \"StirlingNumber1\", mathpiper,\"StirlingNumber1\" );OMDef( \"StirlingNumber2\", mathpiper,\"StirlingNumber2\" );OMDef( \"Totient\", mathpiper,\"Totient\" );OMDef( \"GaussianUnit?\", mathpiper,\"GaussianUnit?\" );OMDef( \"GaussianInteger?\", mathpiper,\"GaussianInteger?\" );OMDef( \"GaussianPrime?\", mathpiper,\"GaussianPrime?\" );OMDef( \"GaussianFactorPrime\", mathpiper,\"GaussianFactorPrime\" );OMDef( \"GaussianNorm\", mathpiper,\"GaussianNorm\" );OMDef( \"GaussianMod\", mathpiper,\"GaussianMod\" );OMDef( \"GaussianFactors\", mathpiper,\"GaussianFactors\" );OMDef( \"AddGaussianFactor\", mathpiper,\"AddGaussianFactor\" );OMDef( \"FactorGaussianInteger\", mathpiper,\"FactorGaussianInteger\" );OMDef( \"GaussianGcd\", mathpiper,\"GaussianGcd\" );OMDef( \"NthRoot\", mathpiper,\"NthRoot\" );OMDef( \"NthRootCalc\", mathpiper,\"NthRootCalc\" );OMDef( \"NthRootList\", mathpiper,\"NthRootList\" );OMDef( \"NthRootSave\", mathpiper,\"NthRootSave\" );OMDef( \"NthRootRestore\", mathpiper,\"NthRootRestore\" );OMDef( \"NthRootClear\", mathpiper,\"NthRootClear\" );OMDef( \"DivisorsList\", mathpiper,\"DivisorsList\" );OMDef( \"SquareFreeDivisorsList\", mathpiper,\"SquareFreeDivisorsList\" );OMDef( \"MoebiusDivisorsList\", mathpiper,\"MoebiusDivisorsList\" );OMDef( \"SumForDivisors\", mathpiper,\"SumForDivisors\" );OMDef( \"RamanujanSum\", mathpiper,\"RamanujanSum\" );OMDef( \"JacobiSymbol\", mathpiper,\"JacobiSymbol\" );OMDef( \"~\" , \"mathpiper\",\"prepend\" );OMDef( \"@\" , \"mathpiper\",\"apply\" );OMDef( \"/@\" , \"mathpiper\",\"list_apply\" );OMDef( \"..\" , \"interval1\",\"integer_interval\" );OMDef( \"NFunction\", \"mathpiper\",\"NFunction\" );OMDef( \"Gamma\", \"nums1\", \"gamma\" );OMDef( \"LnGamma\" , mathpiper, \"LnGamma\" );OMDef( \"Zeta\" , mathpiper, \"Zeta\" );OMDef( \"Bernoulli\" , mathpiper, \"Bernoulli\" );OMDef( \"ApproxInfSum\" , mathpiper, \"ApproxInfSum\" );OMDef( \"BesselJ\" , mathpiper, \"BesselJ\" );OMDef( \"BesselI\" , mathpiper, \"BesselI\" );OMDef( \"BesselY\" , mathpiper, \"BesselY\" );OMDef( \"Erf\" , mathpiper, \"Erf\" );OMDef( \"Erfc\" , mathpiper, \"Erfc\" );OMDef( \"Erfi\" , mathpiper, \"Erfi\" );OMDef( \"FresnelSin\" , mathpiper, \"FresnelSin\" );OMDef( \"FresnelCos\" , mathpiper, \"FresnelCos\" );OMDef( \"LambertW\" , mathpiper, \"LambertW\" );OMDef( \"Beta\" , mathpiper, \"Beta\" );OMDef( \"DirichletEta\" , mathpiper, \"DirichletEta\" );OMDef( \"DirichletLambda\", mathpiper, \"DirichletLambda\" );OMDef( \"DirichletBeta\" , mathpiper, \"DirichletBeta\" );OMDef( \"Sinc\" , mathpiper, \"Sinc\" );OMDef( \"PolyLog\" , mathpiper, \"PolyLog\" );OMDef( \"CatalanConstNum\", mathpiper, \"CatalanConstNum\" );OMDef( \"Digamma\" , mathpiper, \"Digamma\" );OMDef( \"DawsonIntegral\" , mathpiper, \"DawsonIntegral\" );";
        scriptMap.put("OMREP",scriptString);
        scriptMap.put("OMDef",scriptString);
        scriptMap.put("OMForm",scriptString);
        scriptMap.put("OMRead",scriptString);
        scriptMap.put("OMParse",scriptString);
        scriptMap.put("OMEcho",scriptString);
        scriptMap.put("OMEchoEscape",scriptString);
        scriptMap.put("OMFormExpression",scriptString);
        scriptMap.put("OMFormFunction",scriptString);
        scriptMap.put("OMWriteOMEscape",scriptString);
        scriptMap.put("OMEscapeString",scriptString);
        scriptMap.put("OMWriteStringEscape",scriptString);
        scriptMap.put("OMSignature",scriptString);
        scriptMap.put("MatchClose",scriptString);
        scriptMap.put("MatchOMOBJ",scriptString);
        scriptMap.put("ReadOMOBJ",scriptString);
        scriptMap.put("OMApplyReverseMapping",scriptString);
        scriptMap.put("OMApplyMapping",scriptString);
        scriptMap.put("OMPathSelect",scriptString);
        scriptMap.put("OMPathSelect",scriptString);
        scriptMap.put("ReadOMOBJ",scriptString);
        scriptMap.put("OMWriteEscape",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TexForm(_expr) <-- [DumpErrors();WriteString(TeXForm(expr));NewLine();];RulebaseHoldArguments(\"TeXForm\",{expression});RulebaseHoldArguments(\"TeXForm\",{expression, precedence});Function (\"TeXFormBracketIf\", {predicate, string})[ Check(Boolean?(predicate) And? String?(string), \"Argument\", \"TeXForm internal error: non-boolean and/or non-string argument of TeXFormBracketIf\"); If(predicate, ConcatStrings(\"( \", string, \") \"), string);];Function (\"TeXFormMatrixBracketIf\", {predicate, string})[ Check(Boolean?(predicate) And? String?(string), \"Argument\", \"TeXForm internal error: non-boolean and/or non-string argument of TeXFormMatrixBracketIf\"); If(predicate, ConcatStrings(\"\\\\left[ \", string, \"\\\\right]\"), string);];TeXFormMaxPrec() := 60000; 100 # TeXForm(_x) <-- ConcatStrings(\"$\", TeXForm(x, TeXFormMaxPrec()), \"$\");110 # TeXForm(x_Number?, _p) <-- ToString(x);200 # TeXForm(x_Atom?, _p) <-- TeXFormTeXify(ToString(x));100 # TeXForm(x_String?, _p) <--[ Local(characterList); characterList := {}; ForEach(character, x) [ If(character !=? \" \", DestructiveAppend(characterList, character), DestructiveAppend(characterList, \"\\\\hspace{2 mm}\")); ]; ConcatStrings(\"\\\\mathrm{''\", ListToString(characterList), \"''}\");];100 # TeXForm(x_Atom?, _p)_(Infix?(ToString(x))) <-- ConcatStrings(\"\\\\mathrm{\", ToString(x), \"}\");100 # TeXForm(x_List?, _p)_(Length(x)=?0) <-- TeXFormBracketIf(True, \"\");110 # TeXForm(x_List?, _p) <-- TeXFormBracketIf(True, ConcatStrings(TeXForm(First(x), TeXFormMaxPrec()), TeXFormFinishList(Rest(x)) ) );100 # TeXFormFinishList(x_List?)_(Length(x)=?0) <-- \"\";110 # TeXFormFinishList(x_List?) <-- ConcatStrings(\", \", TeXForm(First(x), TeXFormMaxPrec()), TeXFormFinishList(Rest(x))); 115 # TeXForm(_expr * n_Number?, _p) <-- TeXFormBracketIf(p<?PrecedenceGet(\"*\"), ConcatStrings(TeXForm(expr, LeftPrecedenceGet(\"*\")), \"\\\\cdot \", TeXForm(n, RightPrecedenceGet(\"*\")) ) );116 # TeXForm(_n * _expr, _p) _ (Function?(expr) And? Contains?({\"^\", \"!\", \"!!\"}, Type(expr)) And? Number?(FunctionToList(expr)[2])) <-- TeXFormBracketIf(p<?PrecedenceGet(\"*\"), ConcatStrings(TeXForm(n, LeftPrecedenceGet(\"*\")), \"\\\\cdot \", TeXForm(expr, RightPrecedenceGet(\"*\")) ) ); 120 # TeXForm(expr_Function?, _p)_(ArgumentsCount(expr)=?2 And? Infix?(Type(expr)) ) <-- TeXFormBracketIf(p<?PrecedenceGet(Type(expr)), ConcatStrings(TeXForm(FunctionToList(expr)[2], LeftPrecedenceGet(Type(expr))), TeXFormTeXify(Type(expr)), TeXForm(FunctionToList(expr)[3], RightPrecedenceGet(Type(expr))) ) );   120 # TeXForm(expr_Function?, _p)_(ArgumentsCount(expr)=?1 And? Prefix?(Type(expr))) <-- TeXFormBracketIf(p<?PrecedenceGet(Type(expr)), ConcatStrings( TeXFormTeXify(Type(expr)), TeXForm(FunctionToList(expr)[2], RightPrecedenceGet(Type(expr)))) ); 120 # TeXForm(expr_Function?, _p)_(ArgumentsCount(expr)=?1 And? Postfix?(Type(expr))) <-- TeXFormBracketIf(p<?LeftPrecedenceGet(Type(expr)), ConcatStrings( TeXForm(FunctionToList(expr)[2], LeftPrecedenceGet(Type(expr))), TeXFormTeXify(Type(expr))) ); 100 # TeXForm(_x / _y, _p) <-- TeXFormBracketIf(p<?PrecedenceGet(\"/\"), ConcatStrings(\"\\\\frac{\", TeXForm(x, TeXFormMaxPrec()), \"}{\", TeXForm(y, TeXFormMaxPrec()), \"} \") ); 100 # TeXForm(_x ^ (1/2), _p) <-- ConcatStrings(\"\\\\sqrt{\", TeXForm(x, TeXFormMaxPrec()), \"}\");101 # TeXForm(_x ^ (1/_y), _p) <-- ConcatStrings(\"\\\\sqrt[\", TeXForm(y, TeXFormMaxPrec()), \"]{\", TeXForm(x, TeXFormMaxPrec()), \"}\");120 # TeXForm(_x ^ _y, _p) <-- TeXFormBracketIf(p<=?PrecedenceGet(\"^\"), ConcatStrings(TeXForm(x, PrecedenceGet(\"^\")), \" ^{\", TeXForm(y, TeXFormMaxPrec()), \"}\" ) );100 # TeXForm(if(_pred)_body, _p) <-- \"\\\\textrm{if }(\"~TeXForm(pred,60000)~\") \"~TeXForm(body,60000);100 # TeXForm(_left else _right, _p) <-- TeXForm(left,60000)~\"\\\\textrm{ else }\"~TeXForm(right,60000);LocalSymbols(TeXFormRegularOps, TeXFormRegularPrefixOps, TeXFormGreekLetters, TeXFormSpecialNames) [  TeXFormRegularOps := { {\"+\",\" + \"}, {\"-\",\" - \"}, {\"*\",\" \\\\cdot \"}, {\":=\",\" := \"},  {\"==\",\" = \"}, {\"=?\",\" = \"}, {\"!=?\",\"\\\\neq \"}, {\"<=?\",\"\\\\leq \"}, {\">=?\",\"\\\\geq \"}, {\"<?\",\" < \"}, {\">?\",\" > \"}, {\"And?\",\"\\\\wedge \"}, {\"Or?\", \"\\\\vee \"}, {\"<>\", \"\\\\sim \"}, {\"<=>\", \"\\\\approx \"}, {\"Implies?\", \"\\\\Rightarrow \"}, {\"Equivales?\", \"\\\\equiv \"}, {\"%\", \"\\\\bmod \"}, }; TeXFormRegularPrefixOps := { {\"+\",\" + \"}, {\"-\",\" - \"}, {\"Not?\",\" \\\\neg \"} };  TeXFormGreekLetters := {\"Gamma\", \"Delta\", \"Theta\", \"Lambda\", \"Xi\", \"Pi\", \"Sigma\", \"Upsilon\", \"Phi\", \"Psi\", \"Omega\", \"alpha\", \"beta\", \"gamma\", \"delta\", \"epsilon\", \"zeta\", \"eta\", \"theta\", \"iota\", \"kappa\", \"lambda\", \"mu\", \"nu\", \"xi\", \"pi\", \"rho\", \"sigma\", \"tau\", \"upsilon\", \"phi\", \"chi\", \"psi\", \"omega\", \"varpi\", \"varrho\", \"varsigma\", \"varphi\", \"varepsilon\"}; TeXFormSpecialNames := { {\"I\", \"\\\\imath \"},  {\"Pi\", \"\\\\pi \"},  {\"Infinity\", \"\\\\infty \"}, {\"TeX\", \"\\\\textrm{\\\\TeX\\\\/}\"}, {\"LaTeX\", \"\\\\textrm{\\\\LaTeX\\\\/}\"}, {\"Maximum\", \"\\\\max \"},  {\"Minimum\", \"\\\\min \"}, {\"Prog\", \" \"}, {\"Zeta\", \"\\\\zeta \"}, };  Function (\"TeXFormTeXify\", {string}) [ Check(String?(string), \"Argument\", \"TeXForm internal error: non-string argument of TeXFormTeXify\");  If (Contains?(AssocIndices(TeXFormSpecialNames), string), TeXFormSpecialNames[string], If (Contains?(TeXFormGreekLetters, string), ConcatStrings(\"\\\\\", string, \" \"), If (Contains?(AssocIndices(TeXFormRegularOps), string), TeXFormRegularOps[string], If (Contains?(AssocIndices(TeXFormRegularPrefixOps), string), TeXFormRegularPrefixOps[string], If (Length(string) >=? 2 And? Number?(ToAtom(StringMidGet(2, Length(string)-1, string))), ConcatStrings(StringMidGet(1,1,string), \"_{\", StringMidGet(2, Length(string)-1, string), \"}\"), If (Length(string) >? 2, ConcatStrings(\"\\\\mathrm{ \", string, \" }\"), string )))))); ];];200 # TeXForm(x_Function?, _p) _ (Bodied?(Type(x))) <-- [ Local(func, args, lastarg); func := Type(x); args := Rest(FunctionToList(x)); lastarg := PopBack(args); TeXFormBracketIf(p<?PrecedenceGet(func), ConcatStrings( TeXFormTeXify(func), TeXForm(args, TeXFormMaxPrec()), TeXForm(lastarg, PrecedenceGet(func)) ));];220 # TeXForm(x_Function?, _p) <-- ConcatStrings(TeXFormTeXify(Type(x)), TeXForm(Rest(FunctionToList(x)), TeXFormMaxPrec()) ); 100 # TeXForm(Sqrt(_x), _p) <-- ConcatStrings(\"\\\\sqrt{\", TeXForm(x, TeXFormMaxPrec()), \"}\"); 100 # TeXForm(Exp(_x), _p) <-- TeXFormBracketIf(p<?PrecedenceGet(\"/\"), ConcatStrings(\"\\\\exp \", TeXFormBracketIf(True, TeXForm(x, TeXFormMaxPrec())) ) );LocalSymbols(TeXFormMathFunctions, TeXFormMathFunctions2) [   TeXFormMathFunctions := { {\"Cos\",\"\\\\cos \"}, {\"Sin\",\"\\\\sin \"}, {\"Tan\",\"\\\\tan \"}, {\"Cosh\",\"\\\\cosh \"}, {\"Sinh\",\"\\\\sinh \"}, {\"Tanh\",\"\\\\tanh \"}, {\"Ln\",\"\\\\ln \"}, {\"ArcCos\",\"\\\\arccos \"}, {\"ArcSin\",\"\\\\arcsin \"}, {\"ArcTan\",\"\\\\arctan \"}, {\"ArcCosh\",\"\\\\mathrm{arccosh}\\\\, \"}, {\"ArcSinh\",\"\\\\mathrm{arcsinh}\\\\, \"}, {\"ArcTanh\",\"\\\\mathrm{arctanh}\\\\, \"}, {\"Erf\", \"\\\\mathrm{erf}\\\\, \"}, {\"Erfc\", \"\\\\mathrm{erfc}\\\\, \"}, };  TeXFormMathFunctions2 := { {\"BesselI\", \"I \"}, {\"BesselJ\", \"J \"}, {\"BesselK\", \"K \"}, {\"BesselY\", \"Y \"}, {\"OrthoH\", \"H \"}, {\"OrthoP\", \"P \"}, {\"OrthoT\", \"T \"}, {\"OrthoU\", \"U \"}, };    120 # TeXForm(expr_Function?, _p) _ (ArgumentsCount(expr)=?1 And? Contains?(AssocIndices(TeXFormMathFunctions), Type(expr)) ) <-- TeXFormBracketIf(p<?PrecedenceGet(\"*\"), ConcatStrings(TeXFormMathFunctions[Type(expr)], TeXForm( FunctionToList(expr)[2], PrecedenceGet(\"*\")) ) );  120 # TeXForm(expr_Function?, _p) _ (ArgumentsCount(expr)=?2 And? Contains?(AssocIndices(TeXFormMathFunctions2), Type(expr)) ) <-- TeXFormBracketIf(p<?PrecedenceGet(\"*\"), ConcatStrings( TeXFormMathFunctions2[Type(expr)], \"_{\", TeXForm( FunctionToList(expr)[2], TeXFormMaxPrec()),  \"}\", TeXFormBracketIf(True, TeXForm(FunctionToList(expr)[3], TeXFormMaxPrec()) )  ) );]; 100 # TeXForm(Complex(0, 1), _p) <-- TeXForm(Hold(I), p);100 # TeXForm(Complex(_x, 0), _p) <-- TeXForm(x, p);110 # TeXForm(Complex(_x, 1), _p) <-- TeXForm(x+Hold(I), p);110 # TeXForm(Complex(0, _y), _p) <-- TeXForm(Hold(I)*y, p);120 # TeXForm(Complex(_x, _y), _p) <-- TeXForm(x+Hold(I)*y, p);100 # TeXForm(Abs(_x), _p) <-- ConcatStrings(\"\\\\left| \", TeXForm(x, TeXFormMaxPrec()), \"\\\\right| \");100 # TeXForm(Floor(_x), _p) <-- ConcatStrings(\"\\\\left\\\\lfloor \", TeXForm(x, TeXFormMaxPrec()), \"\\\\right\\\\rfloor \");100 # TeXForm(Ceil(_x), _p) <-- ConcatStrings(\"\\\\left\\\\lceil \", TeXForm(x, TeXFormMaxPrec()), \"\\\\right\\\\rceil \");100 # TeXForm(Modulo(_x, _y), _p) <-- TeXFormBracketIf(p<?PrecedenceGet(\"/\"), ConcatStrings(TeXForm(x, PrecedenceGet(\"/\")), \"\\\\bmod \", TeXForm(y, PrecedenceGet(\"/\")) ) );100 # TeXForm(Union(_x, _y), _p) <-- TeXFormBracketIf(p<?PrecedenceGet(\"/\"), ConcatStrings(TeXForm(x, PrecedenceGet(\"/\")), \"\\\\cup \", TeXForm(y, PrecedenceGet(\"/\")) ) );100 # TeXForm(Intersection(_x, _y), _p) <-- TeXFormBracketIf(p<?PrecedenceGet(\"/\"), ConcatStrings(TeXForm(x, PrecedenceGet(\"/\")), \"\\\\cap \", TeXForm(y, PrecedenceGet(\"/\")) ) );100 # TeXForm(Difference(_x, _y), _p) <-- TeXFormBracketIf(p<?PrecedenceGet(\"/\"), ConcatStrings(TeXForm(x, PrecedenceGet(\"/\")), \"\\\\setminus \", TeXForm(y, PrecedenceGet(\"/\")) ) );100 # TeXForm(Contains?(_x, _y), _p) <-- TeXFormBracketIf(p<?PrecedenceGet(\"/\"), ConcatStrings(TeXForm(y, PrecedenceGet(\"/\")), \"\\\\in \", TeXForm(x, PrecedenceGet(\"/\")) ) );100 # TeXForm(BinomialCoefficient(_n, _m), _p) <-- TeXFormBracketIf(False, ConcatStrings(\"{\", TeXForm(n, TeXFormMaxPrec()), \" \\\\choose \", TeXForm(m, TeXFormMaxPrec()), \"}\" ));100 # TeXForm(Sum(_x, _x1, _x2, _expr), _p) <-- TeXFormBracketIf(p<?PrecedenceGet(\"/\"), ConcatStrings(\"\\\\sum _{\", TeXForm(x, TeXFormMaxPrec()), \" = \", TeXForm(x1, TeXFormMaxPrec()), \"} ^{\", TeXForm(x2, TeXFormMaxPrec()), \"} \", TeXForm(expr, PrecedenceGet(\"*\")) ) );100 # TeXForm(Sum(_expr), _p) <-- TeXFormBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(\"\\\\sum \", TeXForm(expr, PrecedenceGet(\"*\")) ) );100 # TeXForm(Product(_x, _x1, _x2, _expr), _p) <-- TeXFormBracketIf(p<?PrecedenceGet(\"/\"), ConcatStrings(\"\\\\prod _{\", TeXForm(x, TeXFormMaxPrec()), \" = \", TeXForm(x1, TeXFormMaxPrec()), \"} ^{\", TeXForm(x2, TeXFormMaxPrec()), \"} \", TeXForm(expr, PrecedenceGet(\"*\")) ) );100 # TeXForm(Integrate(_x, _x1, _x2) _expr, _p) <-- TeXFormBracketIf(p<?PrecedenceGet(\"/\"), ConcatStrings(\"\\\\int _{\", TeXForm(x1, TeXFormMaxPrec()), \"} ^{\", TeXForm(x2, TeXFormMaxPrec()), \" } \", TeXForm(expr, PrecedenceGet(\"*\")), \" d\", TeXForm(x, TeXFormMaxPrec())) );100 # TeXForm(Integrate(_x) _expr, _p) <-- TeXFormBracketIf(p<?PrecedenceGet(\"/\"), ConcatStrings(\"\\\\int \", TeXForm(expr, PrecedenceGet(\"*\")), \" d\", TeXForm(x, TeXFormMaxPrec())) );100 # TeXForm(Limit(_x, _x1) _expr, _p) <-- TeXFormBracketIf(p<?PrecedenceGet(\"/\"), ConcatStrings(\"\\\\lim _{\", TeXForm(x, TeXFormMaxPrec()), \"\\\\rightarrow \", TeXForm(x1, TeXFormMaxPrec()), \"} \", TeXForm(expr, PrecedenceGet(\"/\")) ) );100 # TeXForm(Deriv(_x)_y, _p) <-- TeXFormBracketIf(p<?PrecedenceGet(\"-\"), ConcatStrings( If(Length(VarList(y))>?1, \"\\\\frac{\\\\partial}{\\\\partial \", \"\\\\frac{d}{d \" ), TeXForm(x, PrecedenceGet(\"^\")), \"}\", TeXForm(y, PrecedenceGet(\"/\")) ) );100 # TeXForm(Deriv(_x, _n)_y, _p) <-- TeXFormBracketIf(p<?PrecedenceGet(\"-\"), ConcatStrings( If( Length(VarList(y))>?1, \"\\\\frac{\\\\partial^\" ~ TeXForm(n, TeXFormMaxPrec()) ~ \"}{\\\\partial \", \"\\\\frac{d^\" ~ TeXForm(n, TeXFormMaxPrec()) ~ \"}{d \" ), TeXForm(x, PrecedenceGet(\"^\")), \" ^\", TeXForm(n, TeXFormMaxPrec()), \"}\", TeXForm(y, PrecedenceGet(\"/\")) ) );100 # TeXForm(Differentiate(_x)_y, _p) <-- TeXForm(Deriv(x) y, p);100 # TeXForm(Differentiate(_x, _n)_y, _p) <-- TeXForm(Deriv(x, n) y, p);RulebaseHoldArguments(\"TeXFormNth\",{x,y});100 # TeXForm(Nth(Nth(_x, i_List?), _j), _p) <-- TeXForm(TeXFormNth(x, Append(i,j)), p);100 # TeXForm(TeXFormNth(Nth(_x, i_List?), _j), _p) <-- TeXForm(TeXFormNth(x, Append(i,j)), p);110 # TeXForm(Nth(Nth(_x, _i), _j), _p) <-- TeXForm(TeXFormNth(x, List(i,j)), p);120 # TeXForm(Nth(_x, _i), _p) <-- ConcatStrings(TeXForm(x, TeXFormMaxPrec()), \" _{\", TeXForm(i, TeXFormMaxPrec()), \"}\");120 # TeXForm(TeXFormNth(_x, _i), _p) <-- ConcatStrings(TeXForm(x, TeXFormMaxPrec()), \" _{\", TeXForm(i, TeXFormMaxPrec()), \"}\");80 # TeXForm(M_Matrix?, _p) <-- TeXFormMatrixBracketIf(True, TeXFormPrintMatrix(M));Function (\"TeXFormPrintMatrix\", {M})[ Local(row, col, result, ncol); result := \"\\\\begin{array}{\"; ForEach(col, M[1]) result:=ConcatStrings(result, \"c\"); result := ConcatStrings(result, \"}\"); ForEach(row, 1 .. Length(M)) [ ForEach(col, 1 .. Length(M[row])) [ result := ConcatStrings( result, \" \", TeXForm(M[row][col], TeXFormMaxPrec()), If(col =? Length(M[row]), If(row =? Length(M), \"\", \" \\\\\\\\\"), \" &\")); ]; ]; ConcatStrings(result, \" \\\\end{array} \");];";
        scriptMap.put("TeXForm",scriptString);
        scriptMap.put("TexForm",scriptString);
        scriptMap.put("TeXFormFinishList",scriptString);
        scriptMap.put("TeXFormFinishList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Apart(_f) <-- Apart(f,x);Apart(_f,_var) <--[ Local(rat);  rat := {Numerator(f),Denominator(f)}; If(Degree(rat[1],var) =? 0 And? Degree(rat[2],var) =? 0, [ rat:={Coef(rat[1],var,0),Coef(rat[2],var,0)}; Local(summed,add); summed := Eval(PartFracExpand(Rem(rat[1],rat[2]),rat[2])); add:=(rat[1]/rat[2] - summed); add + summed; ] , [  Expand(Quotient(rat[1],rat[2])) + PartFracExpand(Rem(rat[1],rat[2]),rat[2]); ] );];";
        scriptMap.put("Apart",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ChineseRemainderInteger(mlist_List?,vlist_List?) <--[ Local(m,i,nr,result,msub,euclid,clist); clist:={}; m:=Product(mlist); result:=0; nr:=Length(mlist); For(i:=1,i<=?nr,i++) [ msub:=Quotient(m,mlist[i]); euclid := ExtendedEuclidean(msub,mlist[i]); Local(c); c:=vlist[i] * euclid[2]; c:=Rem(c, mlist[i]); DestructiveAppend(clist,c); result:=result + msub * c; ]; {result,clist};];";
        scriptMap.put("ChineseRemainderInteger",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ChineseRemainderPoly(mlist_List?,vlist_List?) <--[ Local(m,i,nr,result,msub,euclid,clist); clist:={}; m:=Product(mlist); result:=0; nr:=Length(mlist); For(i:=1,i<=?nr,i++) [ msub:=Quotient(m,mlist[i]); euclid := ExtendedEuclideanMonic(msub,mlist[i]); Local(c); c:=vlist[i] * euclid[2]; c:=Modulo(c, mlist[i]); DestructiveAppend(clist,c); result:=result + msub * c; ]; {Expand(result),clist};];";
        scriptMap.put("ChineseRemainderPoly",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ExtendedEuclidean(_f,_g) <--[ Local(r,s,t,i);  r:={f,g}; s:={1,0}; t:={0,1}; i:=1; Local(q,newr,news,newt); newr:=1; While(newr !=? 0) [ newr:=Rem(r[i],r[i+1]); q :=Quotient(r[i],r[i+1]); news :=(s[i]-q*s[i+1]); newt :=(t[i]-q*t[i+1]); DestructiveAppend(r ,newr); DestructiveAppend(s,news); DestructiveAppend(t,newt); i++; ]; {r[i],s[i],t[i]};];";
        scriptMap.put("ExtendedEuclidean",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ExtendedEuclideanMonic(_f,_g) <--[ Local(rho,r,s,t,i);  rho:={LeadingCoef(f),LeadingCoef(g)}; r:={Monic(f),Monic(g)}; s:={1/(rho[1]),0}; t:={0,1/(rho[2])}; i:=1; Local(q,newr,news,newt,newrho); newr:=r[2]; While(newr !=? 0) [ q :=Quotient(r[i],r[i+1]); newr:=Modulo(r[i],r[i+1]); newrho:=LeadingCoef(newr); If (newr !=? 0, newr:=Monic(newr)); news :=(s[i]-q*s[i+1]); newt :=(t[i]-q*t[i+1]); If(newrho !=? 0, [ news:=news/newrho; newt:=newt/newrho; ]); DestructiveAppend(rho,newrho); DestructiveAppend(r ,newr); DestructiveAppend(s,news); DestructiveAppend(t,newt); i++; ]; {r[i],s[i],t[i]};];";
        scriptMap.put("ExtendedEuclideanMonic",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "GcdReduce(_f,_var)<--[ Local(rat,gcd); rat:=RationalForm(f,var); gcd:=Gcd(rat[1],rat[2]); Local(numer,denom,lc); numer:=Quotient(rat[1],gcd); denom:=Quotient(rat[2],gcd); lc:=LeadingCoef(numer,var); numer:=numer/lc; denom:=denom/lc; Expand(numer)/Expand(denom);];";
        scriptMap.put("GcdReduce",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Expand(x); 10 # PAdicExpand(_x,_y) <--[ Local(coefs); coefs:=PAdicExpandInternal(x,y); Subst(p,y)Add(coefs*(p^(0 .. Length(coefs))));];";
        scriptMap.put("PAdicExpand",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # PAdicExpandInternal(0,_y) <-- {};20 # PAdicExpandInternal(_x,_y) <--[ Modulo(x,y) ~ PAdicExpandInternal(Quotient(x,y),y);];";
        scriptMap.put("PAdicExpandInternal",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "PartFracExpand(_g,_f) <--[ Local(mlist,vlist,res); mlist:=Map(\"^\",Transpose(Factors(f))); vlist:=Map(\"Rem\",{FillList(g,Length(mlist)),mlist}); If(Integer?(f), res:=ChineseRemainderInteger(mlist,vlist)[2], res:=ChineseRemainderPoly(mlist,vlist)[2] ); Local(result,divlist); divlist:=Map(\"/\",{res,mlist}); If(Length(divlist)<?2, Add(divlist), [ result:=divlist[1]; ForEach(item,Rest(divlist)) [ result:=ListToFunction({ToAtom(\"+\"),result,item}); ]; result; ]);];";
        scriptMap.put("PartFracExpand",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RationalForm((g_CanBeUni(var))/(f_CanBeUni(var)),_var) <-- { MakeUni(g,var),MakeUni(f,var)};20 # RationalForm(f_CanBeUni(var),_var) <-- { MakeUni(f,var),MakeUni(1,var)};";
        scriptMap.put("RationalForm",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Together((_f/_g) + (_h/_i)) <-- Simplify(Expand(f*i+h*g)/Expand(g*i));10 # Together((_f/_g) - (_h/_i)) <-- Simplify(Expand(f*i-h*g)/Expand(g*i));20 # Together(_f + (_g / _h)) <-- Simplify(Expand(f*h+g)/h);20 # Together((_f/_g) + _h) <-- Simplify(Expand(f+h*g)/g);20 # Together(_f - (_g / _h)) <-- Simplify(Expand(f*h-g)/h);20 # Together((_f/_g) - _h) <-- Simplify(Expand(f-h*g)/g);20 # Together(- (_g / _h)) <-- Simplify((-g)/h);20 # Together((_f/_g) * _h) <-- Simplify(Expand(f*h)/g);20 # Together(_h * (_f/_g)) <-- Simplify(Expand(f*h)/g);20 # Together((_f/_g) / _h) <-- Simplify((f)/Expand(g*h));20 # Together(_h / (_f/_g)) <-- Simplify(Expand(g*h)/f);20 # Together(- _f) <-- - Together(f);30 # Together(_f) <-- f;";
        scriptMap.put("Together",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"_\",{a});RulebaseHoldArguments(\"_\",{a,b});RulebaseHoldArguments(\"DefinePattern\",{leftOperand, rightOperand, rulePrecedence, postPredicate});RuleHoldArguments(\"DefinePattern\",4,9,Equal?(Type(leftOperand),\"_\"))[ DefinePattern(leftOperand[1], rightOperand, rulePrecedence, leftOperand[2]);];RuleHoldArguments(\"DefinePattern\",4,10,True)[ Local(patternFlat,patternVariables, pattern, patternOperator, arg, arity);  Bind(patternFlat, FunctionToList(leftOperand));   Bind(patternVariables, Rest(patternFlat));   Bind(patternOperator,ToString(First(patternFlat)));   Bind(arity,Length(patternVariables));      If(Not?(RulebaseDefined(patternOperator,arity)), [ RulebaseEvaluateArguments(patternOperator,MakeVector(arg,arity)); ] );  Bind(pattern,PatternCreate(patternVariables,postPredicate));  RulePatternEvaluateArguments(patternOperator,arity,rulePrecedence, pattern)rightOperand;  True;];";
        scriptMap.put("DefinePattern",scriptString);
        scriptMap.put("_",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"MakeVector\",{vec,dimension});RuleHoldArguments(\"MakeVector\",2,1,True)[ Local(res,i); res:={}; i:=1; Bind(dimension,AddN(dimension,1)); While(LessThan?(i,dimension)) [ DestructiveInsert(res,1,ToAtom(ConcatStrings(ToString(vec),ToString(i)))); Bind(i,AddN(i,1)); ]; DestructiveReverse(res);];";
        scriptMap.put("MakeVector",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"<--\",{leftOperand,rightOperand});RuleHoldArguments(\"<--\",2,1,Equal?(Type(leftOperand),\"#\"))[ DefinePattern(leftOperand[2],rightOperand,leftOperand[1],True);];RuleHoldArguments(\"<--\",2,2,Function?(leftOperand))[ DefinePattern(leftOperand,rightOperand,0,True);];HoldArgument(\"<--\",leftOperand);HoldArgument(\"<--\",rightOperand);";
        scriptMap.put("<--",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "OptionsListToHash(list) :=[ Local(item, result); result := {}; ForEach(item, list) If( Function?(item) And? (Type(item) =? \"->\" ) And? Atom?(item[1]), result[ToString(item[1])] := If( Atom?(item[2]) And? Not? Number?(item[2]) And? Not? String?(item[2]), ToString(item[2]), item[2] ), Echo({\"OptionsListToHash: Error: item \", item, \" is not of the format a -> b.\"}) );  result;];HoldArgumentNumber(\"OptionsListToHash\", 1, 1);";
        scriptMap.put("OptionsListToHash",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RemoveRepeated({}) <-- {};10 # RemoveRepeated({_x}) <-- {x};20 # RemoveRepeated(list_List?) <-- [ Local(i, done); done := False; For(i:=0, Not? done, i++) [ While(i<?Length(list) And? list[i]=?list[i+1]) DestructiveDelete(list, i); If(i=?Length(list), done := True); ]; list;];";
        scriptMap.put("RemoveRepeated",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "WriteDataItem(tuple_List?, _optionshash) <--[ Local(item); If(  NumericList?(tuple), ForEach(item,tuple) [ Write(item); Space(); ] ); NewLine();];";
        scriptMap.put("WriteDataItem",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(options)[ options := { {\"default\", \"data\"}, {\"data\", \"Plot2DData\"}, {\"java\", \"Plot2DJava\"}, {\"geogebra\", \"Plot2DGeoGebra\"}, {\"jfreechart\", \"Plot2DJFreeChart\"},}; Plot2DOutputs() := options;];Plot2DData(values_List?, _optionsHash) <-- values;Plot2DJava(values_List?, _optionsHash) <--[ Local(result,count); count := 0; result:=\"$plot2d:\"; result := result~\" pensize 2.0 \"; ForEach(function,values) [ result := result~ColorForGraphNr(count); count++; result:=result~\" lines2d \"~ToString(Length(function)); function:=Select(function, Lambda({item},item[2] !=? Undefined)); ForEach(item,function) [ result := result~\" \"~ToString(item[1])~\" \"~ToString(item[2])~\" \"; ]; ]; WriteString(result~\"$\"); True;];10 # ColorForGraphNr(0) <-- \" pencolor 64 64 128 \";10 # ColorForGraphNr(1) <-- \" pencolor 128 64 64 \";10 # ColorForGraphNr(2) <-- \" pencolor 64 128 64 \";20 # ColorForGraphNr(_count) <-- ColorForGraphNr(Modulo(count,3));Plot2DGeogebra(values_List?, _optionsHash) <--[ Local(result,count); count := 0; result:=\"\"; ForEach(function,values) [ function:=Select(function, Lambda({item},item[2] !=? Undefined)); ForEach(item,function) [ result := result~\"(\"~ToString(item[1])~\",\"~ToString(item[2])~\")\"~Nl(); ]; ]; WriteString(result); True;];Plot2DJFreeChart(values_List?, _optionsHash) <--[ Local(rangeList, domainList, function, allProcessedFunctionData, lineChartCallListForm);     ForEach(name, {\"xrange\", \"xname\", \"yname\", \"output\", \"precision\", \"points\", \"depth\"}) [ AssocDelete(optionsHash, name); ];     allProcessedFunctionData := {};  ForEach(function,values) [ rangeList := {};  domainList := {};  function := Select(function, Lambda({item},item[2] !=? Undefined));  ForEach(item,function) [ rangeList := Append(rangeList, item[1]);  domainList := Append(domainList, item[2]); ];  allProcessedFunctionData := Append(allProcessedFunctionData, rangeList); allProcessedFunctionData := Append(allProcessedFunctionData, domainList);  ];   lineChartCallListForm := {LineChart, allProcessedFunctionData };     ForEach(key, AssocIndices(optionsHash)) [ lineChartCallListForm := Append(lineChartCallListForm, Apply(\"->\", {key, optionsHash[key]})); ];     Eval(ListToFunction(lineChartCallListForm)); ];";
        scriptMap.put("Plot2DOutputs",scriptString);
        scriptMap.put("Plot2DData",scriptString);
        scriptMap.put("Plot2DJava",scriptString);
        scriptMap.put("ColorForGraphNr",scriptString);
        scriptMap.put("Plot2DGeogebra",scriptString);
        scriptMap.put("Plot2DJFreeChart",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(var, func, range, option, optionslist, delta, optionshash, c, fc, allvalues, dummy)[Function() Plot2D(func);Function() Plot2D(func, range);Function() Plot2D(func, range, options, ...);1 # Plot2D(_func) <-- (\"Plot2D\" @ {func, -5:5});2 # Plot2D(_func, _range) <-- (\"Plot2D\" @ {func, range, {}});3 # Plot2D(_func, _range, option_Function?) _ (Type(option) =? \"->\" ) <-- (\"Plot2D\" @ {func, range, {option}});5 # Plot2D(_func, _range, optionslist_List?)_(Not? List?(func)) <-- (\"Plot2D\" @ {{func}, range, optionslist});4 # Plot2D(funclist_List?, _range, optionslist_List?) <--[ Local(var, func, delta, optionshash, c, fc, allvalues, dummy); allvalues := {}; optionshash := \"OptionsListToHash\" @ {optionslist};    optionshash[\"xname\"] := \"\";  optionshash[\"yname\"] := {};  If ( Type(range) =? \"->\",  [  optionshash[\"xname\"] := ToString(range[1]); range := range[2]; ] ); If( Type(range) =? \":\",  range := N(Eval({range[1], range[2]})) );  If( optionshash[\"points\"] =? Empty, optionshash[\"points\"] := 23 ); If( optionshash[\"depth\"] =? Empty, optionshash[\"depth\"] := 5 ); If( optionshash[\"precision\"] =? Empty, optionshash[\"precision\"] := 0.0001 ); If( optionshash[\"output\"] =? Empty Or? String?(optionshash[\"output\"]) And? Plot2DOutputs()[optionshash[\"output\"]] =? Empty, optionshash[\"output\"] := Plot2DOutputs()[\"default\"] );  If( optionshash[\"output\"] =? \"datafile\" And? optionshash[\"filename\"] =? Empty, optionshash[\"filename\"] := \"output.data\" );   optionshash[\"points\"] := N(Eval(Quotient(optionshash[\"points\"]+3, 4)));   optionshash[\"precision\"] := N(Eval(optionshash[\"precision\"]));   optionshash[\"xrange\"] := {range[1], range[2]};   delta := N(Eval( (range[2] - range[1]) / (optionshash[\"points\"]) ));   Check(Number?(range[1]) And? Number?(range[2]) And? Number?(optionshash[\"points\"]) And? Number?(optionshash[\"precision\"]), \"Argument\", \"Plot2D: Error: plotting range \" ~(PipeToString()Write(range)) ~\" and/or the number of points \" ~(PipeToString()Write(optionshash[\"points\"])) ~\" and/or precision \" ~(PipeToString()Write(optionshash[\"precision\"])) ~\" is not numeric\" );  ForEach(func, funclist) [  var := VarList(func);  Check(Length(var)<=?1, \"Argument\", \"Plot2D: Error: expression is not a function of one variable: \" ~(PipeToString()Write(func)) );  If(Length(var)=?0, var:={dummy});  If( optionshash[\"xname\"] =? \"\", optionshash[\"xname\"] := ToString(VarList(var)[1]) );  DestructiveAppend(optionshash[\"yname\"], PipeToString()Write(func));  c := range[1]; fc := N(Eval(Apply({var, func}, {c}))); Check(Number?(fc) Or? fc=?Infinity Or? fc=? -Infinity Or? fc=?Undefined, \"Argument\",  \"Plot2D: Error: cannot evaluate function \" ~(PipeToString()Write(func)) ~\" at point \" ~(PipeToString()Write(c)) ~\" to a number, instead got \" ~(PipeToString()Write(fc)) ~\"\" );  DestructiveAppend(allvalues, Plot2Dgetdata(func, var, c, fc, delta, optionshash) );  If(InVerboseMode(), Echo({\"Plot2D: using \", Length(allvalues[Length(allvalues)]), \" points for function \", func}), True); ];   Plot2DOutputs()[optionshash[\"output\"]] @ {allvalues, optionshash};];HoldArgumentNumber(\"Plot2D\", 2, 2);HoldArgumentNumber(\"Plot2D\", 3, 2);HoldArgumentNumber(\"Plot2D\", 3, 3);Plot2Dgetdata(_func, _var, _xinit, _yinit, _deltax, _optionshash) <--[ Local(i, a, fa, b, fb, c, fc, result);  result := { {c,fc} := {xinit, yinit} }; For(i:=0, i<?optionshash[\"points\"], i++) [ {a,fa} := {c, fc};   {b, c} := N(Eval({xinit + (i+1/2)*deltax, xinit + (i+1)*deltax}));  {fb, fc} := N(Eval(MapSingle({var, func}, {b, c}))); result := Concat(result, Rest(Plot2Dadaptive(func, var, {a,b,c}, {fa, fb, fc}, optionshash[\"depth\"],  optionshash[\"precision\"]*optionshash[\"points\"] ))); ];  result;];Plot2Dadaptive(_func, _var, {_a,_b,_c}, {_fa, _fb, _fc}, _depth, _epsilon) <--[ Local(a1, b1, fa1, fb1); a1 := N(Eval((a+b)/2)); b1 := N(Eval((b+c)/2)); {fa1, fb1} := N(Eval(MapSingle({var, func}, {a1, b1}))); If( depth<=?0 Or? (  signchange(fa, fa1, fb) + signchange(fa1, fb, fb1) + signchange(fb, fb1, fc) <=? 2 And?  N(Eval(Abs( (fa-5*fa1+9*fb-7*fb1+2*fc)/24 ) ))  <=? N(Eval( epsilon*(   (5*fb+8*fb1-fc)/12  - Minimum({fa,fa1,fb,fb1,fc}) ) ) ) ),  {{a,fa}, {a1,fa1}, {b,fb}, {b1,fb1}, {c,fc}},  Concat(  Plot2Dadaptive(func, var, {a, a1, b}, {fa, fa1, fb}, depth-1, epsilon*2),  Rest(Plot2Dadaptive(func, var, {b, b1, c}, {fb, fb1, fc}, depth-1, epsilon*2)) ) );];]; ";
        scriptMap.put("Plot2D",scriptString);
        scriptMap.put("Plot2Dgetdata",scriptString);
        scriptMap.put("Plot2Dadaptive",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Plot3DSoutputs() := { {\"default\", \"data\"}, {\"data\", \"Plot3DSdata\"},};Plot3DSdata(values_List?, _optionshash) <-- values;";
        scriptMap.put("Plot3DSoutputs",scriptString);
        scriptMap.put("Plot3DSdata",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " LocalSymbols(var, func, xrange, yrange, option, optionslist, xdelta, ydelta, optionshash, cx, cy, fc, allvalues, dummy)[Function() Plot3DS(func);Function() Plot3DS(func, xrange, yrange);Function() Plot3DS(func, xrange, yrange, options, ...);1 # Plot3DS(_func) <-- (\"Plot3DS\" @ {func, -5:5, -5:5});2 # Plot3DS(_func, _xrange, _yrange) <-- (\"Plot3DS\" @ {func, xrange, yrange, {}});3 # Plot3DS(_func, _xrange, _yrange, option_Function?) _ (Type(option) =? \"->\" ) <-- (\"Plot3DS\" @ {func, xrange, yrange, {option}});5 # Plot3DS(_func, _xrange, _yrange, optionslist_List?)_(Not? List?(func)) <-- (\"Plot3DS\" @ {{func}, xrange, yrange, optionslist});4 # Plot3DS(funclist_List?, _xrange, _yrange, optionslist_List?) <--[ Local(var, func, xdelta, ydelta, optionshash, cx, cy, fc, allvalues, dummy);  allvalues := {}; optionshash := \"OptionsListToHash\" @ {optionslist};  optionshash[\"xname\"] := \"\"; optionshash[\"yname\"] := \"\";  optionshash[\"zname\"] := {};  If ( Type(xrange) =? \"->\",  [  optionshash[\"xname\"] := ToString(xrange[1]); xrange := xrange[2]; ] ); If ( Type(yrange) =? \"->\" ,  [  optionshash[\"yname\"] := ToString(yrange[1]); yrange := yrange[2]; ] ); If( Type(xrange) =? \"~\",  xrange := N(Eval({xrange[1], xrange[2]})) ); If( Type(yrange) =? \"~\",  yrange := N(Eval({yrange[1], yrange[2]})) );  If( optionshash[\"points\"] =? Empty, optionshash[\"points\"] := 10  ); If( optionshash[\"xpoints\"] =? Empty, optionshash[\"xpoints\"] := optionshash[\"points\"] ); If( optionshash[\"ypoints\"] =? Empty, optionshash[\"ypoints\"] := optionshash[\"points\"] ); If( optionshash[\"depth\"] =? Empty, optionshash[\"depth\"] := 2 ); If( optionshash[\"precision\"] =? Empty, optionshash[\"precision\"] := 0.0001 ); If( optionshash[\"hidden\"] =? Empty Or? Not? Boolean?(optionshash[\"hidden\"]), optionshash[\"hidden\"] := True ); If( optionshash[\"output\"] =? Empty Or? String?(optionshash[\"output\"]) And? Plot3DSoutputs()[optionshash[\"output\"]] =? Empty, optionshash[\"output\"] := Plot3DSoutputs()[\"default\"] );  If( optionshash[\"output\"] =? \"datafile\" And? optionshash[\"filename\"] =? Empty, optionshash[\"filename\"] := \"output.data\" ); optionshash[\"used depth\"] := optionshash[\"depth\"];  optionshash[\"xpoints\"] := N(Eval(Quotient(optionshash[\"xpoints\"]+1, 2))); optionshash[\"ypoints\"] := N(Eval(Quotient(optionshash[\"ypoints\"]+1, 2)));  optionshash[\"precision\"] := N(Eval(optionshash[\"precision\"]));  optionshash[\"xrange\"] := {xrange[1], xrange[2]}; optionshash[\"yrange\"] := {yrange[1], yrange[2]};  xdelta := N(Eval( (xrange[2] - xrange[1]) / (optionshash[\"xpoints\"]) ) ); ydelta := N(Eval( (yrange[2] - yrange[1]) / (optionshash[\"ypoints\"]) ) );  Check(NumericList?({xrange[1], xrange[2], optionshash[\"xpoints\"], optionshash[\"ypoints\"], optionshash[\"precision\"]}), \"Argument\",  \"Plot3DS: Error: plotting ranges \" ~(PipeToString()Write(xrange, yrange)) ~\" and/or the number of points \" ~(PipeToString()Write(optionshash[\"xpoints\"], optionshash[\"ypoints\"])) ~\" and/or precision \" ~(PipeToString()Write(optionshash[\"precision\"])) ~\" is not numeric\" );  ForEach(func, funclist) [  var := VarList(func);  Check(Length(var)<=?2, \"Argument\", \"Plot3DS: Error: expression is not a function of at most two variables: \" ~(PipeToString()Write(func)) );  If(Length(var)=?0, var:={dummy, dummy}); If(Length(var)=?1, var:={var[1], dummy});  If( optionshash[\"xname\"] =? \"\", optionshash[\"xname\"] := ToString(var[1]) ); If( optionshash[\"yname\"] =? \"\", optionshash[\"yname\"] := ToString(var[2]) );  DestructiveAppend(optionshash[\"zname\"], PipeToString()Write(func));  cx := xrange[1]; cy := yrange[1]; fc := N(Eval(Apply({var, func}, {cx, cy}))); Check(Number?(fc) Or? fc=?Infinity Or? fc=? -Infinity Or? fc=?Undefined, \"Argument\",  \"Plot3DS: Error: cannot evaluate function \" ~(PipeToString()Write(func)) ~\" at point \" ~(PipeToString()Write(cx, cy)) ~\" to a number, instead got \" ~(PipeToString()Write(fc)) ~\"\" );  DestructiveAppend(allvalues, RemoveRepeated(HeapSort( Plot3DSgetdata(func, var, {cx, cy, fc}, {xdelta, ydelta}, optionshash), Hold({{x,y},x[1]<?y[1] Or? x[1] =? y[1] And? x[2] <=? y[2] } ) )) ); If(InVerboseMode(), Echo({\"Plot3DS: using \", Length(allvalues[Length(allvalues)]), \" points for function \", func}), True); If(InVerboseMode(), Echo({\"Plot3DS: max. used \", 2^(optionshash[\"depth\"] - optionshash[\"used depth\"]), \"subdivisions for \", func}), True); ];  Plot3DSoutputs()[optionshash[\"output\"]] @ {allvalues, optionshash};];HoldArgumentNumber(\"Plot3DS\", 3, 2);HoldArgumentNumber(\"Plot3DS\", 3, 3);HoldArgumentNumber(\"Plot3DS\", 4, 2);HoldArgumentNumber(\"Plot3DS\", 4, 3);HoldArgumentNumber(\"Plot3DS\", 4, 4);Plot3DSgetdata(_func, _var, _initvalues, _deltas, _optionshash) <--[ Local(i, j, xa, ya, fa, xb, yb, fb, result, rowcache);      rowcache := {initvalues}; For(i:=1, i<=?optionshash[\"ypoints\"], i++) [ ya := N(Eval(initvalues[2]+i*deltas[2])); DestructiveAppend(rowcache, {initvalues[1], ya, N(Eval(Apply({var, func}, {initvalues[1], ya})))}); ]; result := rowcache;  For(i:=1, i<=?optionshash[\"xpoints\"], i++) [   xa := N(Eval(initvalues[1]+i*deltas[1])); ya := initvalues[2]; fa := N(Eval(Apply({var, func}, {xa, ya}))); DestructiveAppend(result, {xa, ya, fa});  For(j:=1, j<=?optionshash[\"ypoints\"], j++) [     yb := N(Eval(initvalues[2] + j*deltas[2])); fb := N(Eval(Apply({var, func}, {xa, yb}))); result := Concat(result, Plot3DSadaptive(func, var, {rowcache[j][1], ya, xa, yb, rowcache[j][3], rowcache[j+1][3], fa, fb}, optionshash[\"depth\"],  optionshash[\"precision\"] * optionshash[\"xpoints\"] * optionshash[\"ypoints\"], optionshash ));  rowcache[j] := {xa, ya, fa}; ya := yb; fa := fb; DestructiveAppend(result, {xa, ya, fa}); ]; ]; result;];10 # Plot3DSadaptive(_func, _var, _square, 0, _epsilon, _optionshash) <-- {};20 # Plot3DSadaptive(_func, _var, {_x1, _y1, _x2, _y2, _f11, _f12, _f21, _f22}, _depth, _epsilon, _optionshash) <--[ Local(x3, y3, f13, f31, f33, f32, f23, result);  optionshash[\"used depth\"] := depth-1;  x3 := N(Eval((x1+x2)/2)); y3 := N(Eval((y1+y2)/2));   f13 := N(Eval(Apply({var, func}, {x1, y3}))); f31 := N(Eval(Apply({var, func}, {x3, y1}))); f33 := N(Eval(Apply({var, func}, {x3, y3}))); f32 := N(Eval(Apply({var, func}, {x3, y2}))); f23 := N(Eval(Apply({var, func}, {x2, y3}))); result := {{x1,y3,f13}, {x3, y1, f31}, {x3, y3, f33}, {x3, y2, f32}, {x2, y3, f23}}; If(  signchange(f11,f13,f12) + signchange(f13,f12,f32) + signchange(f12,f32,f22) <=? 2 And? signchange(f22,f23,f21) + signchange(f23,f21,f31) + signchange(f21,f31,f11) <=? 2 And?    N(Eval(Abs( (f11-f23)/2-(f12-f21)/3+(f22-f13)/6+2*(f32-f33)/3 ))) <=? N(Eval( epsilon*(   (f11 + f12 + f21 + f22)/12 + 2*f33/3 - Minimum({f11, f12, f21, f22, f13, f31, f33, f32, f23}) ) ) ) ,  result,  Concat(  result,  Plot3DSadaptive(func, var, {x1, y1, x3, y3, f11, f13, f31, f33}, depth-1, epsilon*4, optionshash), Plot3DSadaptive(func, var, {x1, y3, x3, y2, f13, f12, f33, f32}, depth-1, epsilon*4, optionshash), Plot3DSadaptive(func, var, {x3, y1, x2, y3, f31, f33, f21, f23}, depth-1, epsilon*4, optionshash), Plot3DSadaptive(func, var, {x3, y3, x2, y2, f33, f32, f23, f22}, depth-1, epsilon*4, optionshash) ) );];]; ";
        scriptMap.put("Plot3DS",scriptString);
        scriptMap.put("Plot3DSgetdata",scriptString);
        scriptMap.put("Plot3DSadaptive",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "signchange(x,y,z) :=If( Number?(x) And? Number?(y) And? Number?(z) And? Not? ( x>?y And? y<?z Or?   x<?y And? y>?z ), 0, 1); ";
        scriptMap.put("signchange",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # AllSatisfy?(pred_String?,lst_List?) <-- Apply(\"And?\",(MapSingle(pred,lst)));20 # AllSatisfy?(_pred,_lst) <-- False;";
        scriptMap.put("AllSatisfy?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "0 # BooleanType?(True) <-- True;0 # BooleanType?(False) <-- True;1 # BooleanType?(_anythingelse) <-- False;";
        scriptMap.put("BooleanType?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function (\"Boolean?\", {x}) (x=?True) Or? (x=?False) Or? Function?(x) And? Contains?({\"=?\", \">?\", \"<?\", \">=?\", \"<=?\", \"!=?\", \"And?\", \"Not?\", \"Or?\", \"Implies?\", \"Equivales?\"}, Type(x));";
        scriptMap.put("Boolean?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Constant?(_n) <-- (VarList(n) =? {});";
        scriptMap.put("Constant?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Diagonal?(A_Matrix?) <--[ Local(i,j,m,n,result); m := Length(A); n := Length(A[1]); result := (m=?n);   i:=2; While(i<=?m And? result) [ j:=1; While(j<=?n And? result) [ result:= (i=?j Or? A[i][j] =? 0); j++; ]; i++; ]; If(m=?2, [ result := result And? (A=?Transpose(A)); ] ); result;];";
        scriptMap.put("Diagonal?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Equation?(expr_Atom?) <-- False;12 # Equation?(_expr) <-- FunctionToList(expr)[1] =? == ;";
        scriptMap.put("Equation?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "EvenFunction?(f,x):= (f =? Eval(Subst(x,-x)f));";
        scriptMap.put("EvenFunction?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Even?(n) := Integer?(n) And? ( BitAnd(n,1) =? 0 );";
        scriptMap.put("Even?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "FloatIsInt?(_x) <-- [ x:=N(Eval(x)); Local(prec,result,n); Bind(prec,BuiltinPrecisionGet()); If(Zero?(x),Bind(n,2), If(x>?0, Bind(n,2+FloorN(N(FastLog(x)/FastLog(10)))), Bind(n,2+FloorN(N(FastLog(-x)/FastLog(10)))) )); BuiltinPrecisionSet(n+prec); Bind(result,Zero?(RoundTo(x-Floor(x),prec)) Or? Zero?(RoundTo(x-Ceil(x),prec))); BuiltinPrecisionSet(prec); result; ];";
        scriptMap.put("FloatIsInt?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "HasExpressionArithmetic?(expr, atom) := HasExpressionSome?(expr, atom, {ToAtom(\"+\"), ToAtom(\"-\"), *, /});";
        scriptMap.put("HasExpressionArithmetic?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "HasExpressionSome?(_expr, _atom, _looklist) _ Equal?(expr, atom) <-- True;15 # HasExpressionSome?(expr_Atom?, _atom, _looklist) <-- Equal?(expr, atom);19 # HasExpressionSome?({}, _atom, _looklist) <-- False;20 # HasExpressionSome?(expr_List?, _atom, _looklist) <-- HasExpressionSome?(First(expr), atom, looklist) Or? HasExpressionSome?(Rest(expr), atom, looklist);25 # HasExpressionSome?(expr_Function?, _atom, _looklist)_(Not? Contains?(looklist, ToAtom(Type(expr)))) <-- False;30 # HasExpressionSome?(expr_Function?, _atom, _looklist) <-- HasExpressionSome?(Rest(FunctionToList(expr)), atom, looklist);";
        scriptMap.put("HasExpressionSome?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # HasExpression?(_expr, _atom) _ Equal?(expr, atom) <-- True;15 # HasExpression?(expr_Atom?, _atom) <-- Equal?(expr, atom);19 # HasExpression?({}, _atom) <-- False;20 # HasExpression?(expr_List?, _atom) <-- HasExpression?(First(expr), atom) Or? HasExpression?(Rest(expr), atom);30 # HasExpression?(expr_Function?, _atom) <-- HasExpression?(Rest(FunctionToList(expr)), atom);";
        scriptMap.put("HasExpression?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "HasFunctionArithmetic?(expr, atom) := HasFunctionSome?(expr, atom, {ToAtom(\"+\"), ToAtom(\"-\"), *, /});";
        scriptMap.put("HasFunctionArithmetic?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # HasFunctionSome?(_expr, string_String?, _looklist) <-- HasFunctionSome?(expr, ToAtom(string), looklist);10 # HasFunctionSome?(expr_Atom?, atom_Atom?, _looklist) <-- False;15 # HasFunctionSome?(expr_Function?, atom_Atom?, _looklist)_(Not? Contains?(looklist, ToAtom(Type(expr)))) <-- Equal?(First(FunctionToList(expr)), atom);20 # HasFunctionSome?(expr_Function?, atom_Atom?, _looklist) <-- Equal?(First(FunctionToList(expr)), atom) Or? ListHasFunctionSome?(Rest(FunctionToList(expr)), atom, looklist);";
        scriptMap.put("HasFunctionSome?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # HasFunction?(_expr, string_String?) <-- HasFunction?(expr, ToAtom(string));10 # HasFunction?(expr_Atom?, atom_Atom?) <-- False;20 # HasFunction?(expr_Function?, atom_Atom?) <-- Equal?(First(FunctionToList(expr)), atom) Or? ListHasFunction?(Rest(FunctionToList(expr)), atom);";
        scriptMap.put("HasFunction?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Hermitian?(A_Matrix?) <-- (Conjugate(Transpose(A))=?A);";
        scriptMap.put("Hermitian?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Idempotent?(A_Matrix?) <-- (A^2 =? A);";
        scriptMap.put("Idempotent?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Infinity?(Infinity) <-- True;10 # Infinity?(-(_x)) <-- Infinity?(x);11 # Infinity?(Sign(_x)*y_Infinity?) <-- True;60000 # Infinity?(_x) <-- False;";
        scriptMap.put("Infinity?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "19 # ListHasFunctionSome?({}, _atom, _looklist) <-- False;20 # ListHasFunctionSome?(expr_List?, atom_Atom?, _looklist) <-- HasFunctionSome?(First(expr), atom, looklist) Or? ListHasFunctionSome?(Rest(expr), atom, looklist);";
        scriptMap.put("ListHasFunctionSome?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "19 # ListHasFunction?({}, _atom) <-- False;20 # ListHasFunction?(expr_List?, atom_Atom?) <-- HasFunction?(First(expr), atom) Or? ListHasFunction?(Rest(expr), atom);";
        scriptMap.put("ListHasFunction?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LowerTriangular?(A_Matrix?) <-- (UpperTriangular?(Transpose(A)));";
        scriptMap.put("LowerTriangular?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # CanBeMonomial(_expr)_(Type(expr)=?\"UniVariate\") <-- False;10 # CanBeMonomial(_expr)<--Not? (HasFunction?(expr,ToAtom(\"+\")) Or? HasFunction?(expr,ToAtom(\"-\")));10 # Monomial?(expr_CanBeMonomial) <-- [ Local(r); If( RationalFunction?(expr), r := (VarList(Denominator(expr)) =? {}), r := True );];15 # Monomial?(_expr) <-- False;";
        scriptMap.put("Monomial?",scriptString);
        scriptMap.put("CanBeMonomial",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "NegativeInteger?(x):= Integer?(x) And? x <? 0;";
        scriptMap.put("NegativeInteger?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "NegativeNumber?(x):= Number?(x) And? x <? 0;";
        scriptMap.put("NegativeNumber?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "NegativeReal?(_r) <--[ r:=N(Eval(r)); (Number?(r) And? r <=? 0);];";
        scriptMap.put("NegativeReal?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "NonNegativeInteger?(x):= Integer?(x) And? x >=? 0;";
        scriptMap.put("NonNegativeInteger?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "NonNegativeNumber?(x):= Number?(x) And? x >=? 0;";
        scriptMap.put("NonNegativeNumber?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "NonZeroInteger?(x) := (Integer?(x) And? x !=? 0);";
        scriptMap.put("NonZeroInteger?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # NoneSatisfy?(pred_String?,lst_List?) <-- Not? Apply(\"Or?\",(MapSingle(pred,lst)));20 # NoneSatisfy?(_pred,_lst) <-- True;";
        scriptMap.put("NoneSatisfy?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # NotZero?(x_Number?) <-- ( AbsN(x) >=? PowerN(10, -BuiltinPrecisionGet()));10 # NotZero?(x_Infinity?) <-- True;60000 # NotZero?(_x) <-- False;";
        scriptMap.put("NotZero?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "NumericList?(_arglist) <-- List?(arglist) And? (\"And?\" @ (MapSingle(Hold({{x},Number?(N(Eval(x)))}), arglist)));";
        scriptMap.put("NumericList?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "OddFunction?(f,x):= (f =? Eval(-Subst(x,-x)f));";
        scriptMap.put("OddFunction?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Odd?(n) := Integer?(n) And? ( BitAnd(n,1) =? 1 );";
        scriptMap.put("Odd?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # One?(x_Number?) <-- Zero?(SubtractN(x,1));60000 # One?(_x) <-- False;";
        scriptMap.put("One?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Orthogonal?(A_Matrix?) <-- (Transpose(A)*A=?Identity(Length(A)));";
        scriptMap.put("Orthogonal?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # PolynomialOverIntegers?(expr_Function?) <-- [ Local(x,vars); vars := VarList(expr); If(Length(vars)>?1,vars:=HeapSort(vars,\"GreaterThan?\"));  x := vars[1]; PolynomialOverIntegers?(expr,x);];15 # PolynomialOverIntegers?(_expr) <-- False;10 # PolynomialOverIntegers?(_expr,_var)_(CanBeUni(var,expr)) <--[ If( AllSatisfy?(\"Integer?\",Coef(expr,var,0 .. Degree(expr,var))), True, False );];15 # PolynomialOverIntegers?(_expr,_var) <-- False;";
        scriptMap.put("PolynomialOverIntegers?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Polynomial?( expr_String? ) <-- False;15 # Polynomial?( expr_Constant? ) <-- True; 20 # Polynomial?(_expr) <-- [ Local(x,vars); vars := VarList(expr); If(Length(vars)>?1,vars:=HeapSort(vars,\"GreaterThan?\"));  x := vars[1]; Polynomial?(expr,x);];25 # Polynomial?(_expr) <-- False;10 # Polynomial?(_expr,_var)_(CanBeUni(var,expr)) <-- True;15 # Polynomial?(_expr,_var) <-- False;";
        scriptMap.put("Polynomial?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "PositiveInteger?(x):= Integer?(x) And? x >? 0;";
        scriptMap.put("PositiveInteger?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "PositiveNumber?(x):= Number?(x) And? x >? 0;";
        scriptMap.put("PositiveNumber?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "PositiveReal?(_r) <--[ r:=N(Eval(r)); (Number?(r) And? r >=? 0);];";
        scriptMap.put("PositiveReal?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RationalFunction?(_expr)_(Length(VarList(expr))=?0) <-- False;15 # RationalFunction?(_expr) <-- RationalFunction?(expr,VarList(expr));10 # RationalFunction?(expr_RationalOrNumber?,_var) <-- False;15 # RationalFunction?(_expr,var_Atom?)_(Type(expr)=?\"/\" Or? Type(-expr)=?\"/\") <--[ If (Polynomial?(Numerator(expr),var) And? Polynomial?(Denominator(expr),var), Contains?(VarList(Denominator(expr)),var), False );];20 # RationalFunction?(_expr,vars_List?)_(Type(expr)=?\"/\" Or? Type(-expr)=?\"/\") <--[ If (Polynomial?(Numerator(expr),vars) And? Polynomial?(Denominator(expr),vars), Intersection(vars, VarList(expr)) !=? {}, False );];60000 # RationalFunction?(_expr,_var) <-- False;";
        scriptMap.put("RationalFunction?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RationalOrNumber?(x_Number?) <-- True;10 # RationalOrNumber?(x_Number? / y_Number?) <-- True;10 # RationalOrNumber?(-(x_Number? / y_Number?)) <-- True;60000 # RationalOrNumber?(_x) <-- False;";
        scriptMap.put("RationalOrNumber?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Rational?(x_Integer?) <-- True;10 # Rational?(x_Integer? / y_Integer?) <-- True;10 # Rational?(-(x_Integer? / y_Integer?)) <-- True;60000 # Rational?(_x) <-- False;";
        scriptMap.put("Rational?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SkewSymmetric?(A_Matrix?) <-- (Transpose(A)=?(-1*A));";
        scriptMap.put("SkewSymmetric?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # SumOfTerms?(_var,expr_FreeOf?(var)) <-- False;12 # SumOfTerms?(_var,expr_Atom?()) <-- False;14 # SumOfTerms?(_var,expr_List?())_(expr[1]=?ToAtom(\"+\") Or? expr[1]=?ToAtom(\"-\")) <-- True;16 # SumOfTerms?(_var,expr_List?())_(expr[1]=?ToAtom(\"*\")) <-- Or?(FreeOf?(var,expr[2]),FreeOf?(var,expr[3]));18 # SumOfTerms?(_var,expr_List?())_(expr[1]=?ToAtom(\"/\")) <-- FreeOf?(var,expr[3]);20 # SumOfTerms?(_var,expr_List?()) <-- False;22 # SumOfTerms?(_var,_expr) <-- SumOfTerms?(var,FunctionToList(expr));";
        scriptMap.put("SumOfTerms?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Symmetric?(A_Matrix?) <-- (Transpose(A)=?A);";
        scriptMap.put("Symmetric?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Unitary?(A_Matrix?) <-- (Transpose(Conjugate(A))*A =? Identity(Length(A)));";
        scriptMap.put("Unitary?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "UpperTriangular?(A_Matrix?) <--[ Local(i,j,m,n,result); m:=Length(A); n:=Length(A[1]); i:=2; result:=(m=?n); While(i<=?m And? result) [ j:=1; While(j<=?n And? result) [ result:= (i<=?j Or? A[i][j] =? 0); j++; ]; i++; ]; result;];";
        scriptMap.put("UpperTriangular?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Variable?(_expr) <-- (Atom?(expr) And? Not?(expr =? True) And? Not?(expr =? False) And? Not?(expr =? Infinity) And? Not?(expr =? -Infinity) And? Not?(expr =? Undefined) And? Not?(Number?(N(Eval(expr)))));";
        scriptMap.put("Variable?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"ZeroVector?\",{aList}) aList =? ZeroVector(Length(aList));";
        scriptMap.put("ZeroVector?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Zero?(x_Number?) <-- ( MathSign(x) =? 0 Or? AbsN(x) <? PowerN(10, -BuiltinPrecisionGet()));60000 # Zero?(_x) <-- False;20 # Zero?(UniVariate(_var,_first,_coefs)) <-- ZeroVector?(coefs);";
        scriptMap.put("Zero?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(p,x)[Function(\"Scalar?\",{x}) Not?(List?(x));Function(\"Vector?\",{x}) If(List?(x), Length(Select(x, List?))=?0, False);Function(\"Vector?\",{p,x})[ If(List?(x), [ Local(i,n,result); n:=Length(x); i:=1; result:=True; While(i<=?n And? result) [ result:=Apply(p,{x[i]}); i++; ]; result; ], False);];Function(\"Matrix?\",{x})If(List?(x) And? Length(x)>?0,[ Local(n); n:=Length(x); If(Length(Select(x, Vector?))=?n, MapSingle(Length,x)=?Length(x[1])+ZeroVector(n), False);],False);Function(\"Matrix?\",{p,x})If(Matrix?(x),[ Local(i,j,m,n,result); m:=Length(x); n:=Length(x[1]); i:=1; result:=True; While(i<=?m And? result) [ j:=1; While(j<=?n And? result) [ result:=Apply(p,{x[i][j]}); j++; ]; i++; ]; result;],False);Function(\"SquareMatrix?\",{x}) Matrix?(x) And? Length(x)=?Length(x[1]);Function(\"SquareMatrix?\",{p,x}) Matrix?(p,x) And? Length(x)=?Length(x[1]);]; ";
        scriptMap.put("Scalar?",scriptString);
        scriptMap.put("Matrix?",scriptString);
        scriptMap.put("Vector?",scriptString);
        scriptMap.put("SquareMatrix?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # CDF(BernoulliDistribution(_p), x_Number?) <-- If(x<=?0,0,If(x>?0 And? x<=?1, p,1));11 # CDF(BernoulliDistribution(_p), _x) <-- Hold(If(x<=?0,0,If(x>?0 And? x<=?1, p,1)));10 # CDF(BinomialDistribution(_p,_n),m_Number?)_(m<?0) <-- 0;10 # CDF(BinomialDistribution(_p,n_Integer?),m_Number?)_(m>?n) <-- 1;10 # CDF(BinomialDistribution(_p,_n),_m) <-- Sum @ { i, 0, Floor(m), PMF(BinomialDistribution(p,n),i)};10 # CDF(DiscreteUniformDistribution( a_Number?, b_Number?), x_Number?)_(x<=?a) <-- 0;10 # CDF(DiscreteUniformDistribution( a_Number?, b_Number?), x_Number?)_(x>?b) <-- 1;10 # CDF(DiscreteUniformDistribution( a_Number?, b_Number?), x_Number?)_(a<?x And? x<=?b) <-- (x-a)/(b-a+1);11 # CDF(DiscreteUniformDistribution( _a, _b), _x) <--Hold(If(x<=?a,0,If(x<=?b,(x-a)/(b-a),1)));10 # CDF(PoissonDistribution(_l), x_Number?)_(x<=?0) <-- 0;10 # CDF(PoissonDistribution(_l), _x) <-- Sum @ {i,0,x,PMF(PoissonDistribution(l),i)};10 # CDF(ChiSquareDistribution(_m), _x) <-- IncompleteGamma(m/2,x/2)/Gamma(x/2);10 # CDF(DiscreteDistribution( dom_List?, prob_List?), _x) <-- [ Local(i,cdf,y); i := 1; cdf:=0; y:=dom[i]; While(y<?x) [cdf:=cdf+prob[i];i++;]; cdf; ];10 # CDF(HypergeometricDistribution( N_Number?, M_Number?, n_Number?), x_Number?)_(M <=? N And? n <=? N) <-- [ Sum @ {i,0,x,PMF(HypergeometricDistribution(N, M, n),i)};];10 # CDF(NormalDistribution(_m,_s), _x) <-- 1/2 + 1/2 * ErrorFunction((x - m)/(s*Sqrt(2))); ";
        scriptMap.put("CDF",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Expectation(BernoulliDistribution(_p)) <-- 1-p;10 # Expectation(BinomialDistribution(_p,_n)) <-- n*p;10 # Expectation( DiscreteDistribution( dom_List?, prob_List?))_( Length(dom)=?Length(prob) And? Simplify(Sum(prob))=?1) <-- Sum @ {i,1,Length(dom),dom[i]*prob[i]};";
        scriptMap.put("Expectation",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # PDF(ExponentialDistribution(_l), _x) <-- If(x<?0,0,l*Exp(-l*x));10 # PDF(NormalDistribution(_m,_s),_x) <-- Exp(-(x-m)^2/(2*s^2))/Sqrt(2*Pi*s^2); 10 # PDF(ContinuousUniformDistribution(_a,_b),x)_(a<?b) <-- If(x<?a Or? x>?b,0,1/(b-a));10 # PDF(DiscreteDistribution( dom_List?, prob_List?), _x)_( Length(dom)=?Length(prob) And? Simplify(Add(prob))=?1) <-- [ Local(i); i:=Find(dom,x); If(i =? -1,0,prob[i]); ];10 # PDF( ChiSquareDistribution( _m),x_RationalOrNumber?)_(x<=?0) <-- 0;20 # PDF( ChiSquareDistribution( _m),_x) <-- x^(m/2-1)*Exp(-x/2)/2^(m/2)/Gamma(m/2);10 # PDF(tDistribution(_m),x) <-- Gamma((m+1)/2)*(1+x^2/m)^(-(m+1)/2)/Gamma(m/2)/Sqrt(Pi*m);";
        scriptMap.put("PDF",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # PMF(BernoulliDistribution(_p),0) <-- p;10 # PMF(BernoulliDistribution(_p),1) <-- 1-p;10 # PMF(BernoulliDistribution(_p),x_Number?)_(x !=? 0 And? x !=? 1) <-- 0;10 # PMF(BernoulliDistribution(_p),_x) <-- Hold(If(x=?0,p,If(x=?1,1-p,0)));10 # PMF(BinomialDistribution(_p,_n),_k) <-- BinomialCoefficient(n,k)*p^k*(1-p)^(n-k);10 # PMF(DiscreteUniformDistribution(_a,_b), x_Number?) <-- If(x<?a Or? x>?b, 0 ,1/(b-a+1));11 # PMF(DiscreteUniformDistribution(_a,_b), _x) <-- Hold(If(x<?a Or? x>?b, 0 ,1/(b-a+1)));10 # PMF(PoissonDistribution(_l), n_Number?) <-- If(n<?0,0,Exp(-l)*l^n/n!);11 # PMF(PoissonDistribution(_l),_n) <-- Exp(-l)*l^n/n!;10 # PMF(GeometricDistribution(_p),_n) <--If(n<?0,0,p*(1-p)^n);10 # PMF(DiscreteDistribution( dom_List?, prob_List?), _x)_( Length(dom)=?Length(prob) And? Simplify(Add(prob))=?1) <-- [ Local(i); i:=Find(dom,x); If(i =? -1,0,prob[i]); ]; 10 # PMF(HypergeometricDistribution( N_Number?, M_Number?, n_Number?), x_Number?)_(M <=? N And? n <=? N) <-- (BinomialCoefficient(M,x) * BinomialCoefficient(N-M, n-x))/BinomialCoefficient(N,n);";
        scriptMap.put("PMF",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Manipulate\",{symbolicEquation});HoldArgument(\"Manipulate\",symbolicEquation);10 # Manipulate(_symbolicEquation)_HasFunction?(Eval(symbolicEquation), \"==\") <--[ Local(listForm, operator, operand, left, right, leftManipulated, rightManipulated, operandIndex, equationIndex, leftOrder, rightOrder); listForm := FunctionToList(symbolicEquation);  operator := listForm[1];  If(HasFunction?(Eval(listForm[2]),\"==\" ), [operandIndex := 3; equationIndex := 2; ], [ operandIndex := 2; equationIndex := 3;]);  operand := listForm[operandIndex]; equation := Eval(listForm[equationIndex]); left := EquationLeft(equation); right := EquationRight(equation);  If(operandIndex =? 3, [ leftOrder := `({left,operand});rightOrder := `({right,operand});], [leftOrder := `({operand,left}); rightOrder := `({operand,right});]);   leftManipulated := ExpandBrackets(Simplify(Apply(ToString(operator), leftOrder))); rightManipulated := ExpandBrackets(Simplify(Apply(ToString(operator), rightOrder)));   leftManipulated == rightManipulated;];";
        scriptMap.put("Manipulate",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ControlChart(data) :=[ A2 := .577; D3 := 0; D4 := 2.144; means := {}; meansPoints := {};  ranges := {}; rangesPoints := {};  index := 1; ForEach(group, data) [ groupMean := Mean(group); means := N(Append(means, groupMean)); meansPoints := N(Append(meansPoints,{index, groupMean} ));  groupRange := Range(group); ranges := N(Append(ranges, groupRange)); rangesPoints := N(Append(rangesPoints,{index, groupRange} ));  index++; ];  xBarBar := N(Mean(means));  rBar := N(Mean(ranges));  xBarUCL := N(xBarBar + A2*rBar);  xBarLCL := N(xBarBar - A2*rBar);  rUCL := N(D4*rBar);  rLCL := N(D3*rBar);];";
        scriptMap.put("ControlChart",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(options)[ options := {};  Local(updateObjects);  updateObjects := \"\";  options[\"updateObjects\"] := updateObjects;  GeoGebra() := options;GeoGebra(list) := (options := list);];";
        scriptMap.put("GeoGebra",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "GeoGebraHistogram(classBoundaries, data) := [ Local(command);    command := PatchString(\"Histogram[<?Write(classBoundaries);?>,<?Write(data);?>]\"); JavaCall(geogebra, \"evalCommand\", command);];GeoGebraHistogram(data) := [ Local(command, classBoundaries, noDuplicatesSorted, largestValue, smallestValue, x, numberOfUniqueValues);  noDuplicatesSorted := HeapSort(RemoveDuplicates(data), \"<?\" );  smallestValue := Floor(noDuplicatesSorted[1]);  numberOfUniqueValues := Length(noDuplicatesSorted);  largestValue := Ceil(noDuplicatesSorted[Length(noDuplicatesSorted)]);  classBoundaries := N(Table(x,x,smallestValue-.5,largestValue+.5,1));  command := PatchString(\"Histogram[<?Write(classBoundaries);?>,<?Write(data);?>]\"); JavaCall(geogebra, \"evalCommand\", command);];";
        scriptMap.put("GeoGebraHistogram",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseListedHoldArguments(\"GeoGebraPlot\",{arg1,arg2});5 # GeoGebraPlot(_arg1) <-- GeoGebraPlot(arg1,{}); 20 # GeoGebraPlot(function_Function?, options_List?)_(Not? List?(function)) <--[ Local(command);  function := (Subst(==,=) function);  command := ConcatStrings(PipeToString()Write(function));  JavaCall(geogebra,\"evalCommand\",command);];10 # GeoGebraPlot(list_List?, _options)_(NumericList?(list) ) <--[ If(List?(options), options := OptionsToAssociativeList(options), options := OptionsToAssociativeList({options})); Local(length, index, labelIndex, pointTemplate, segmentCommandTemplate, segmentElementTemplate, command, code, x, y, pointSize);  length := Length(list);  If(Odd?(length), list := Append(list,list[length]));   If(options[\"pointSize\"] !=? Empty, pointSize := options[\"pointSize\"], pointSize := \"1\");  index := 1;  labelIndex := 1;  pointTemplate := \"<element type=\\\"point\\\" label=\\\"A<?Write(labelIndex);?>\\\"> <show object=\\\"true\\\" label=\\\"false\\\"/>?<objColor r=\\\"0\\\" g=\\\"0\\\" b=\\\"255\\\" alpha=\\\"0.0\\\"/> <layer val=\\\"0\\\"/> <animation step=\\\"0.1\\\" speed=\\\"1\\\" type=\\\"0\\\" playing=\\\"false\\\"/> <coords x=\\\"<?Write(x);?>\\\" y=\\\"<?Write(y);?>\\\" z=\\\"1.0\\\"/> <pointSize val=\\\"<?Write(ToAtom(pointSize));?>\\\"/></element>\"; segmentCommandTemplate := \"<command name=\\\"Segment\\\"><input a0=\\\"A1\\\" a1=\\\"A2\\\"/><output a0=\\\"a\\\"/>\"; segmentElementTemplate := \"<element type=\\\"segment\\\" label=\\\"a<?Write(labelIndex-1);?>\\\"><lineStyle thickness=\\\"2\\\" type=\\\"0\\\"/><show object=\\\"true\\\" label=\\\"false\\\"/><layer val=\\\"0\\\"/><coords x=\\\"-1.0\\\" y=\\\"1.0\\\" z=\\\"0.0\\\"/><lineStyle thickness=\\\"2\\\" type=\\\"0\\\"/><eqnStyle style=\\\"implicit\\\"/><outlyingIntersections val=\\\"false\\\"/><keepTypeOnTransform val=\\\"true\\\"/></element>\";      While(index <? length+1) [ x := list[index]; index++; y := list[index]; index++;   code := PatchString(pointTemplate);  JavaCall(geogebra,\"evalXML\",code);  If(options[\"lines\"] =? \"True\" And? labelIndex >? 1, [  command := PatchString(\"a<?Write(labelIndex-1);?> = Segment[A<?Write(labelIndex-1);?>,A<?Write(labelIndex);?>]\"); JavaCall(geogebra, \"evalCommand\", command);  code := PatchString(segmentElementTemplate); JavaCall(geogebra,\"evalXML\",code); ] );  labelIndex++; ];    ];5 # GeoGebraPlot(list_List?, _options)_(Matrix?(list)) <--[ Local(flatList);  flatList := {};  ForEach(subList,list) [ DestructiveAppend(flatList,subList[1]); DestructiveAppend(flatList, subList[2]); ];  GeoGebraPlot(flatList, options);];";
        scriptMap.put("GeoGebraPlot",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # GeoGebraPoint(name_String?, x_Number?, y_Number?) <--[ Local(command);  command := PatchString(\"<?Write(ToAtom(name));?>=(<?Write(x);?>,<?Write(y);?>)\");  JavaCall(geogebra,\"evalCommand\",command);];";
        scriptMap.put("GeoGebraPoint",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ggbLine(point1Label, point2Label) :=[ Local(command);  command := PatchString(\"Line[<?Write(ToAtom(point1Label));?>,<?Write(ToAtom(point2Label));?>]\");   JavaCall(geogebra,\"evalCommand\",command);];";
        scriptMap.put("GgbLine",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Distance(PointA_Point?,PointB_Point?) <--[ Local(x1,x2,y1,y2,distance); x1 := PointA[1]; x2 := PointB[1]; y1 := PointA[2]; y2 := PointB[2];  distance := Sqrt((x2 - x1)^2 + (y2 - y1)^2);];";
        scriptMap.put("Distance",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Midpoint(PointA_Point?,PointB_Point?) <-- [ Local(x1,x2,y1,y2,midpointX,midpointY);  x1 := PointA[1]; x2 := PointB[1]; y1 := PointA[2]; y2 := PointB[2]; midpointX := (x1 + x2)/2; midpointY := (y1 + y2)/2;  {midpointX,midpointY};];Midpoint(segment_Segment?) <-- [ Local(x1,x2,y1,y2,midpointX,midpointY);  x1 := segment[1][1]; x2 := segment[2][1]; y1 := segment[1][2]; y2 := segment[2][2]; midpointX := (x1 + x2)/2; midpointY := (y1 + y2)/2;  {midpointX,midpointY};];";
        scriptMap.put("Midpoint",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Point(x,y) := List(x,y);Point(x,y,z) := List(x,y,z);";
        scriptMap.put("Point",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Point?(p) := If(List?(p) And? (Length(p) =? 2 Or? Length(p) =? 3),True,False);";
        scriptMap.put("Point?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Segment(PointA_Point?,PointB_Point?) <--[ Local(x1,x2,y1,y2);  x1 := PointA[1]; x2 := PointB[1]; y1 := PointA[2]; y2 := PointB[2]; {{x1,y1},{x2,y2}};];";
        scriptMap.put("Segment",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Segment?(list_List?) <--[ If(List?(list[1]) And? Length(list[1])=?2 And? List?(list[2]) And? Length(list[2])=?2,True,False);];";
        scriptMap.put("Segment?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Slope(PointA_Point?,PointB_Point?) <--[ Local(x1,x2,y1,y2,slope); x1 := PointA[1]; x2 := PointB[1]; y1 := PointA[2]; y2 := PointB[2];  slope := (y2 - y1)/(x2 - x1);];10 # Slope(segment_List?)_(Length(segment) =? 2 And? Length(segment[1]) =? 2 And? Length(segment[2]) =? 2) <--[ Local(x1,x2,y1,y2,slope);  x1 := segment[1][1];  x2 := segment[2][1];    y1 := segment[1][2];  y2 := segment[2][2];  slope := (y2 - y1)/(x2 - x1);];";
        scriptMap.put("Slope",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BooleanList(elements, integerBitPattern) :=[ Local(stringBitPattern, leadingDigitsCount, atomBitPattern);  Check(Integer?(elements) And? elements >? 0, \"Argument\", \"The first argument must be an integer that is > 0.\");  Check(Integer?(integerBitPattern) And? integerBitPattern >=? 0, \"Argument\", \"The second argument must be an integer that is >= 0.\");  stringBitPattern := ToBase(2,integerBitPattern);  leadingDigitsCount := elements - Length(stringBitPattern);   If(leadingDigitsCount >? 0, stringBitPattern := Concat(FillList(\"0\",leadingDigitsCount), StringToList(stringBitPattern)));  atomBitPattern := MapSingle(\"ToAtom\", stringBitPattern);  atomBitPattern /: {0 <- False, 1 <- True}; ];";
        scriptMap.put("BooleanList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BooleanLists(elements) :=[ Local(numberOfPatterns);  Check(elements >? 0, \"Argument\", \"The argument must be > 0.\");  numberOfPatterns := 2^elements - 1;  Table(BooleanList(elements, pattern), pattern,0, numberOfPatterns, 1);];";
        scriptMap.put("BooleanLists",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Subexpressions(expression) :=[ Local(subexpressions, uniqueSubexpressions, variables, functions, sortedFunctions);  subexpressions := SubexpressionsHelper(expression,{});  uniqueSubexpressions := RemoveDuplicates(subexpressions);  variables := VarList(uniqueSubexpressions);  functions := Select(uniqueSubexpressions, \"Function?\"); sortedFunctions := Sort(functions,Lambda({x,y},Length(FunctionListAllHelper(x)) + Length(VarList(x)) <? Length(FunctionListAllHelper(y)) + Length(VarList(y))));  Concat(variables, sortedFunctions);];SubexpressionsHelper(expression, list) :=[ Local(first, rest);   if((Not? Atom?(expression)) And? (Length(expression) !=? 0)) [  first := First(expression);  list := SubexpressionsHelper(first, list);  rest := Rest(expression);  If(Length(rest) !=? 0, rest := First(rest));  If(Length(rest) !=? 0, list := SubexpressionsHelper(rest, list)); ];  Append(list, expression);];FunctionListAllHelper(expression) :=[ if(Atom?(expression)) [ {};  ] else if (Function?(expression)) [  Concat( {First(FunctionToList(expression))}, Apply(\"Concat\", MapSingle(\"FunctionListAllHelper\", Rest(FunctionToList(expression))))); ] else [ Check(False, \"Argument\", \"The argument must be an atom or a function.\");  ];];";
        scriptMap.put("Subexpressions",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TruthTable(booleanExpression) :=[ Local(resultList, variables, booleanPatterns, subexpressions, substitutionList, evaluation);  resultList := {};  variables := VarList(booleanExpression);  booleanPatterns := Reverse(BooleanLists(Length(variables)));  subexpressions := Subexpressions(booleanExpression);  DestructiveAppend(resultList, subexpressions);  ForEach(booleanPattern, booleanPatterns) [ substitutionList := Map(\"==\", {variables, booleanPattern});  evaluation := `(subexpressions Where @substitutionList);  DestructiveAppend(resultList, evaluation);  ];  resultList;];";
        scriptMap.put("TruthTable",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CombinationsList(inputList, r) :=[ Local(n,manipulatedIndexes,totalCombinations,combinationsList,combinationsLeft,combination,i,j,currentIndexes);  Check(List?(inputList) And? Length(inputList) >=? 1, \"Argument\", \"The first argument must be a list with 1 or more elements.\");  n := Length(inputList);  Check(r <=? n , \"Argument\", \"The second argument must be <=? the length of the list.\");  manipulatedIndexes := 1 .. r;   totalCombinations := Combinations(n,r);  combinationsLeft := totalCombinations;  combinationsList := {};  While(combinationsLeft >? 0) [ combination := {};  if(combinationsLeft =? totalCombinations) [  combinationsLeft := combinationsLeft - 1;  currentIndexes := manipulatedIndexes; ] else [ i := r;  While(manipulatedIndexes[i] =? n - r + i) [ i--; ];  manipulatedIndexes[i] := manipulatedIndexes[i] + 1;  For(j := i + 1, j <=? r, j++)  [ manipulatedIndexes[j] := manipulatedIndexes[i] + j - i; ];  combinationsLeft := combinationsLeft - 1;  currentIndexes := manipulatedIndexes; ]; For(i := 1, i <=? Length(currentIndexes), i++)  [ combination := Append(combination,(inputList[currentIndexes[i]])); ];  combinationsList := Append(combinationsList,combination); ];  combinationsList;];";
        scriptMap.put("CombinationsList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ElementCount(list) :=[  if(Length(list) =? 0) [ 0; ] else if(Atom?(list)) [ 1; ] else [ ElementCount(First(list)) + ElementCount(Rest(list)); ];];";
        scriptMap.put("ElementCount",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ListOfLists?(listOfLists) :=[ Local(result);  result := True;  if(Not? List?(listOfLists)) [ result := False; ] else  [ ForEach(list, listOfLists) [ If(Not? List?(list), result := False); ]; ];  result;];";
        scriptMap.put("ListOfLists?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ListToString(list_List?)_(Length(list) =? 0) <-- \"\";20 # ListToString(list_List?) <--[ Local(resultString, character);  resultString := \"\";  ForEach(element, list) [ If(String?(element), character := element, character := ToString(element));  resultString := resultString ~ character; ];  resultString;];";
        scriptMap.put("ListToString",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(ZoomInOnce)[ 10 # NumberLinePrintZoom(_lowValue, _highValue, divisions_PositiveInteger?, depth_PositiveInteger?)_(lowValue <? highValue) <--  [  Local(numbers, stepAmount, zoomIndexes, nextZoomIndex, outputWidth, numbersString, output, randomStep, randomZoomNumber, iteration);  iteration := 1;  While(iteration <=? depth) [ {numbers, stepAmount} := ZoomInOnce(lowValue, highValue, divisions);  zoomIndexes := {};  outputWidth := 0;  numbersString := \"\";  ForEach(number, numbers) [ output := PipeToString() Write(number);  zoomIndexes := Append(zoomIndexes, Length(output));  numbersString := numbersString ~ output ~ PipeToString() Space(3);  outputWidth := outputWidth + Length(output) + 3;  ];  randomStep := RandomInteger(divisions);  randomZoomNumber := Sum(Take(zoomIndexes, randomStep));  If(randomStep =? 1, nextZoomIndex := randomZoomNumber + 1, nextZoomIndex := 3*(randomStep-1) + randomZoomNumber + 1);  If(iteration >? 1, Echo(ListToString(FillList(\"-\", outputWidth-3))));   Echo(numbersString);  If(iteration !=? depth,[Space(nextZoomIndex);Echo(\"|\");]);  lowValue := numbers[randomStep];  highValue := numbers[randomStep+1];  iteration++;  ];  ];     ZoomInOnce(_lowValue, _highValue, divisions_PositiveInteger?)_(lowValue <? highValue) <-- [ Local(stepAmount, x, numbers);  stepAmount := If(Decimal?(lowValue) Or? Decimal?(highValue), N((highValue-lowValue)/divisions), (highValue-lowValue)/divisions);  x := lowValue;  numbers := {};  While(x <=? highValue) [  numbers := Append(numbers, x);  x := x + stepAmount;  ];  {numbers, stepAmount};  ];];";
        scriptMap.put("NumberLinePrintZoom",scriptString);
        scriptMap.put("ZoomInOnce",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "OptionsToAssociativeList(optionList) :=[ Local(associativeList, key, value);  associativeList := {};  ForEach(option, optionList) [ If(option[0] =? ->, [ If(String?(option[1]), key := option[1], key := ToString(option[1])); If(String?(option[2]), value := option[2], value := ToString(option[2]));  associativeList := {key, value} ~ associativeList;  ]);  ]; associativeList;];";
        scriptMap.put("OptionsToAssociativeList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # PadLeft(number_Number?, totalDigits_Integer?) <--[ Local(integerString, padAmount, resultString);  integerString := ToString(number);  padAmount := totalDigits - Length(integerString);  If(padAmount >? 0, resultString := ListToString(FillList(0, padAmount)) ~ integerString, resultString := integerString );];";
        scriptMap.put("PadLeft",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"RForm\",{expression});RulebaseHoldArguments(\"RForm\",{expression, precedence});Function (\"RFormBracketIf\", {predicate, string})[ Check(Boolean?(predicate) And? String?(string), \"Argument\", \"RForm internal error: non-boolean and/or non-string argument of RFormBracketIf\"); If(predicate, ConcatStrings(\"( \", string, \") \"), string);];RFormMaxPrec() := 60000; 100 # RForm(_x) <-- RForm(x, RFormMaxPrec());110 # RForm(x_Integer?, _p) <-- ToString(x);111 # RForm(x_Zero?, _p) <-- \"0.\";112 # RForm(x_Number?, _p) <-- ToString(x);190 # RForm(False, _p) <-- \"false\";190 # RForm(True, _p) <-- \"true\";190 # RForm(Pi, _p) <-- \"pi\";200 # RForm(x_Atom?, _p) <-- ToString(x);100 # RForm(x_String?, _p) <-- ConcatStrings(\"\\\"\", x, \"\\\"\");LocalSymbols(rFormRegularOps) [ rFormRegularOps :=  {  {\"+\",\"+\"}, {\"-\",\"-\"}, {\"*\",\"*\"}, {\"/\",\"/\"}, {\"/\",\"/\"}, {\"^\",\"^\"}, {\"=?\",\"==\"}, {\">=?\",\">=\"}, {\">?\",\">\"}, {\"<=?\",\"<=\"}, {\"<?\",\"<\"}, {\"!=\",\"!=\"}, {\"..\",\":\"}, {\"Not?\",\"!\"}, {\":=\",\"<-\"}, {\"sequence\",\":\"}, {\"True\",\"TRUE\"}, {\"Modulo\",\"%%\"}, {\"Quotient\",\"%/%\"}, }; RFormRegularOps() := rFormRegularOps;]; LocalSymbols(rFormMathFunctions) [ rFormMathFunctions := { {\"NthRoot\",\"root\"}, {\"Infinite\",\"Inf\"}, {\"Undefined\",\"NaN\"}, {\"Sin\",\"sin\"}, {\"Cos\",\"cos\"}, {\"Tan\",\"tan\"}, {\"ArcSin\",\"asin\"}, {\"ArcCos\",\"acos\"}, {\"ArcTan\",\"atan\"}, {\"ArcSinh\",\"asinh\"}, {\"ArcCosh\",\"acosh\"}, {\"ArcTanh\",\"atanh\"}, {\"ArcCsc\",\"acsc\"}, {\"ArcCsch\",\"acsch\"}, {\"ArcSec\",\"asec\"}, {\"ArcSech\",\"asech\"}, {\"ArcCot\",\"acot\"}, {\"ArcCoth\",\"acoth\"}, {\"Exp\",\"exp\"}, {\"Ln\",\"log\"}, {\"Sqrt\",\"sqrt\"}, {\"Bin\",\"choose\"}, {\"Gamma\",\"gamma\"}, {\"!\",\"factorial\"}, {\"Limit\",\"limit\"}, {\"Deriv\",\"deriv\"}, {\"Integrate\",\"integrate\"}, {\"Taylor\",\"?\"}, {\"List\",\"list\"}, }; RFormMathFunctions() := rFormMathFunctions;];  100 # RForm(+ _y, _p) <-- RFormBracketIf(p<?PrecedenceGet(\"+\"), ConcatStrings(\" + \", RForm(y, RightPrecedenceGet(\"+\")) ) ); 100 # RForm(- _y, _p) <-- RFormBracketIf(p<?PrecedenceGet(\"-\"), ConcatStrings(\" - \", RForm(y, RightPrecedenceGet(\"-\")) ) ); 100 # RForm(_x ^ _y, _p) <-- RFormBracketIf(p <=? PrecedenceGet(\"^\"), ConcatStrings(RForm(x, RFormMaxPrec()), \"^\", RForm(y, RFormMaxPrec())) );100 # RForm(if(_pred)_body, _p) <-- \"if (\"~RForm(pred,60000)~\") \"~RForm(body);100 # RForm(_left else _right, _p) <-- RForm(left)~\" else \"~RForm(right);120 # RForm(expr_Function?, _p)_(ArgumentsCount(expr)=?2 And? Contains?(AssocIndices(RFormRegularOps()), Type(expr)) ) <-- RFormBracketIf(p<?PrecedenceGet(Type(expr)), ConcatStrings(RForm(FunctionToList(expr)[2], LeftPrecedenceGet(Type(expr))), RFormRegularOps()[Type(expr)], RForm(FunctionToList(expr)[3], RightPrecedenceGet(Type(expr))) ) );120 # RForm(expr_Function?, _p) _ (ArgumentsCount(expr)=?1 And? Contains?(AssocIndices(RFormMathFunctions()), Type(expr)) ) <-- ConcatStrings(RFormMathFunctions()[Type(expr)], \"(\", RForm( FunctionToList(expr)[2], RFormMaxPrec()),\")\" );RFormArgs(list_List?) <--[ Local(i,nr,result); result:=\"\"; nr:=Length(list); For (i:=1,i<=?nr,i++) [ result:=result~RForm(list[i]); If (i<?nr, result:=result~\", \"); ]; result;];200 # RForm(_x, _p)_(Function?(x)) <--[ ConcatStrings(Type(x), \"(\", RFormArgs(Rest(FunctionToList(x))),\")\" );];100 # RForm(Complex(0, 1), _p) <-- \"I\";100 # RForm(Complex(_x, 0), _p) <-- RForm(x, p);110 # RForm(Complex(_x, 1), _p) <-- RForm(x+Hold(I), p);110 # RForm(Complex(0, _y), _p) <-- RForm(Hold(I)*y, p);120 # RForm(Complex(_x, _y), _p) <-- RForm(x+Hold(I)*y, p);100 # RForm(Modulo(_x, _y), _p) <-- RFormBracketIf(p<?PrecedenceGet(\"/\"), ConcatStrings(RForm(x, PrecedenceGet(\"/\")), \" % \", RForm(y, PrecedenceGet(\"/\")) ) );100 # RForm(Nth(_x, _i), _p) <-- ConcatStrings(RForm(x, RFormMaxPrec()), \"[\", RForm(i, RFormMaxPrec()), \"]\");LocalSymbols(RIndent) [ RIndent:=1; RNlIndented():= [ Local(result); result:=\"\"; Local(i); For(i:=1,i<?RIndent,i++) [ result:=result~\" \"; ]; result; ]; RIndent() := [ (RIndent++); \"\"; ]; RUndent() := [ (RIndent--); \"\"; ];]; RFormStatement(_x) <-- RForm(x) ~ \";\" ~ RNlIndented();120 # RForm(_x,_p)_(Type(x) =? \"Prog\") <--[ Local(result); result:=RIndent()~\"{\"~RNlIndented(); ForEach(item,Rest(FunctionToList(x))) [ result:=result~RFormStatement(item); ]; result:=result~\"}\"~RUndent()~RNlIndented(); result;];120 # RForm(For(_from,_to,_step)_body,_p) <-- \"for(\" ~ RForm(from,RFormMaxPrec()) ~ \";\" ~ RForm(to,RFormMaxPrec()) ~ \";\" ~ RForm(step,RFormMaxPrec()) ~ \")\" ~ RIndent() ~ RNlIndented() ~ RFormStatement(body) ~ RUndent();120 # RForm(While(_pred)_body, _p) <-- \"while(\" ~ RForm(pred,RFormMaxPrec()) ~ \")\" ~ RIndent() ~ RNlIndented() ~ RFormStatement(body) ~ RUndent();";
        scriptMap.put("RForm",scriptString);
        scriptMap.put("RFormArgs",scriptString);
        scriptMap.put("RFormStatement",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # StringToList(string_String?)_(Length(string) =? 0) <-- {};20 # StringToList(string_String?) <--[ Local(resultList);  resultList := {};  ForEach(character, string) [ resultList := Append(resultList, character); ];  resultList;];";
        scriptMap.put("StringToList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "StringToNumber( str_String? ) <-- FromBase(10,str);";
        scriptMap.put("StringToNumber",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "VerifyNumeric(expression1, expression2, optionsList) :=[ Local(variablesList1, variablesList2, numericValue1, numericValue2, numericDifference, optionsVariableNamesList, optionsValuesList, associativeList);  variablesList1 := VarList(expression1);  variablesList2 := VarList(expression2);  if(Length(variablesList1) =? 0 And? Length(variablesList2) =? 0) [ numericValue1 := N(expression1);  numericValue2 := N(expression2); ] else [ optionsList := HeapSort(optionsList, Lambda({x,y},LessThan?(x[1],y[1])));  associativeList := OptionsToAssociativeList(optionsList);  optionsVariableNamesList := MapSingle(\"ToAtom\", AssocIndices(associativeList));  optionsValuesList := MapSingle(\"ToAtom\", AssocValues(associativeList));  variablesList1 := HeapSort(variablesList1,\"LessThan?\");  variablesList2 := HeapSort(variablesList2,\"LessThan?\");  Check(variablesList1 =? variablesList2 And? variablesList1 =? optionsVariableNamesList, \"Argument\", \"Both expressions and the options list must have the same variable names and the same number of variables.\");  numericValue1 := N(WithValue(variablesList1, optionsValuesList, expression1));  numericValue2 := N(WithValue(variablesList2, optionsValuesList, expression2 ));  Echo(Map(\"->\",{variablesList1, optionsValuesList}));  NewLine(); ];  Echo(expression1, \"-> \", numericValue1);  NewLine();  Echo(expression2, \"-> \", numericValue2);  numericDifference := N(numericValue1 - numericValue2);  NewLine();  Echo(\"Difference between the numeric values: \", numericDifference);  numericDifference;];VerifyNumeric(expression1, expression2) :=[ VerifyNumeric(expression1, expression2, {});];";
        scriptMap.put("VerifyNumeric",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " 10 # MakeNewPoly( _poly ) <-- MakeNewPoly(poly,{x,y,z});  20 # MakeNewPoly( _poly, polyVars_List? ) <-- [      If(InVerboseMode(),Tell(\" MakeNewPoly\",poly)); Local(p,nTerms,ii,dexp,npVars,pVars,ind,polyExp,pwrProd,pwrProdT,newDexp); npVars := polyVars; nVars := Length(npVars); dexp := DisassembleExpression(poly,npVars); nTerms := Length(dexp[3]); pVars := dexp[1]; If(InVerboseMode(), [ Tell(\" \",nTerms);  Tell(\" \",ReassembleListTerms(dexp));  Tell(\" \",{pVars,npVars}); ] ); newDexp := FlatCopy(dexp); newDexp[1] := npVars; pwrProd := dexp[2]; If(InVerboseMode(), [Tell(\" \",newDexp); Tell(\" \",pwrProd);]); pwrProdT := Transpose(pwrProd); polyExp := Concat({dexp[3]},pwrProdT); polyExp := Push(Transpose(polyExp),npVars); polyExp := Push(polyExp,NP); If(InVerboseMode(),Tell(\" \",polyExp)); polyExp; ]; UnFence(\"MakeNewPoly\",2);";
        scriptMap.put("MakeNewPoly",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Retract(\"npOrderTerms\",*);Retract(\"LEXgt\",*);Retract(\"LEXlt\",*);Retract(\"GRLEXgt\",*);Retract(\"GRLEXlt\",*);Retract(\"GREVLEXgt\",*);Retract(\"GREVLEXlt\",*); 10 # npOrderTerms( expr_NP?, _termOrder ) <-- [      If(InVerboseMode(),[Tell(\"npOrderTerms\",expr); Tell(\" \",termOrder);]); Local(np,v,out); v := expr[2]; np := npTerms(expr); out := NP~v~npOrderTerms(np,termOrder); ];]; 10 # npOrderTerms( expr_List?, _termOrder ) <-- [      If(InVerboseMode(),[Tell(\"npOrderTerms\",expr); Tell(\" \",termOrder);]); Local(np,out); np := FlatCopy(expr);  if ( termOrder =? LEX ) [ If(InVerboseMode(),Tell(\" ordering terms by LEX\")); out := Sort( np, LEXgt ); ] else if ( termOrder =? GRLEX ) [ If(InVerboseMode(),Tell(\" ordering terms by GRLEX\"));  out := Sort( np, GRLEXgt ); ] else if ( termOrder =? GREVLEX ) [ If(InVerboseMode(),Tell(\" ordering terms by GREVLEX\")); out := Sort( np, GREVLEXgt ); ] else  [ Check(False,\"TermOrder\",\"npOrderTerms should never reach this point\"); ]; out; ];]; 20 # npOrderTerms( expr_NP? ) <-- npOrderTerms(expr,LEX); 25 # npOrderTerms( expr_List? ) <-- npOrderTerms(expr,LEX);           10 # LEXgt(term1_List?,term2_List?)_(Length(term1)=?Length(term2)) <-- [ Local(nonzero); nonzero := Select(Rest(term1-term2),\"NotZero?\"); Length(nonzero) !=? 0 And? First(nonzero) >? 0; ]; 10 # LEXlt(term1_List?,term2_List?)_(Length(term1)=?Length(term2)) <-- [ Local(nonzero); nonzero := Select(Rest(term1-term2),\"NotZero?\"); Length(nonzero) !=? 0 And? First(nonzero) <? 0; ]; 10 # GRLEXgt(term1_List?,term2_List?)_(Length(term1)=?Length(term2)) <-- [ Local(sum1,sum2,nonzero); sum1 := Sum(Rest(term1)); sum2 := Sum(Rest(term2)); nonzero := Select(Rest(term1-term2),\"NotZero?\"); If(sum1 >? sum2, True, If(sum1=?sum2 And? (Length(nonzero)!=?0 And? First(nonzero)>?0),True,False) ); ]; 10 # GRLEXlt(term1_List?,term2_List?)_(Length(term1)=?Length(term2)) <-- [ Local(sum1,sum2,nonzero); sum1 := Sum(Rest(term1)); sum2 := Sum(Rest(term2)); nonzero := Select(Rest(term1-term2),\"NotZero?\"); If(sum1 <? sum2, True, If(sum1=?sum2 And? (Length(nonzero)!=?0 And? First(nonzero)<?0),True,False) ); ]; 10 # GREVLEXgt(term1_List?,term2_List?)_(Length(term1)=?Length(term2)) <-- [ Local(sum1,sum2,revnonzero); sum1 := Sum(Rest(term1)); sum2 := Sum(Rest(term2)); revnonzero := Reverse(Select(Rest(term1-term2),\"NotZero?\")); If(sum1 >? sum2, True, If(sum1=?sum2 And? (Length(revnonzero)!=?0 And? First(revnonzero)<?0),True,False) ); ]; 10 # GREVLEXlt(term1_List?,term2_List?)_(Length(term1)=?Length(term2)) <-- [ Local(sum1,sum2,revnonzero); sum1 := Sum(Rest(term1)); sum2 := Sum(Rest(term2)); revnonzero := Reverse(Select(Rest(term1-term2),\"NotZero?\")); If(sum1 <? sum2, True, If(sum1=?sum2 And? (Length(revnonzero)!=?0 And? First(revnonzero)>?0),True,False) ); ]; ";
        scriptMap.put("npOrderTerms",scriptString);
        scriptMap.put("LEXgt",scriptString);
        scriptMap.put("LEXlt",scriptString);
        scriptMap.put("GRLEXgt",scriptString);
        scriptMap.put("GRLEXlt",scriptString);
        scriptMap.put("GREVLEXgt",scriptString);
        scriptMap.put("GREVLEXlt",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " 10 # npTerms( expr_NP? ) <-- Rest(Rest(expr));  10 # npNumTerms( expr_NP? ) <-- Length(npTerms(expr)); 10 # NP?( expr_List? ) <-- expr[1] =? NP; 15 # NP?( _expr ) <-- False;";
        scriptMap.put("npTerms",scriptString);
        scriptMap.put("npNumTerms",scriptString);
        scriptMap.put("NP_",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "AlphaToChiSquareScore(p, df) :=[ Local(ChiEpsilon, ChiMax, minchisq, maxchisq, chisqval, result);  ChiEpsilon := 0.000001;   ChiMax := 99999.0;   minchisq := 0.0;  maxchisq := ChiMax;  p := N(p);  if( p <=? 0.0 Or? p >=? 1.0) [  if (p <=? 0.0)  [ result := maxchisq; ]  else  [ if (p >=? 1.0)  [ result := 0.0; ]; ];  ] else [ chisqval := N(df / SqrtN(p));   While ((maxchisq - minchisq) >? ChiEpsilon)  [ if (ChiSquareScoreToAlpha(chisqval, df) <? p)  [ maxchisq := chisqval; ]  else  [ minchisq := chisqval; ]; chisqval := (maxchisq + minchisq) * 0.5; ];  result := chisqval;  ];  N(result);];";
        scriptMap.put("AlphaToChiSquareScore",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "AnovaCompletelyRandomizedBlock(levelsList, alpha) :=[ Check(Matrix?(levelsList), \"Argument\", \"The first argument must be a list of equal-length lists.\");  Check(alpha >=? 0 And? alpha <=? 1, \"Argument\", \"The second argument must be a number between 0 and 1.\");  Local( topOfSummary, anovaBlockTableRow1, criticalFScore, anovaBlockTableRow3, anovaBlockTableRow2, lengthsList, summaryTableRow, sumsList, meanSquareWithin, topOfPage, htmlJavaString, index, variancesList, grandMean, row, topOfAnovaBlock, result, fScoreBlock, criticalFScoreBlock, blockMeansList, sumOfSquaresWithin, meanSquareBetween, sumOfSquaresBetween, fScore, summaryTableRows, meansList, sumOfSquaresBlock, b, blockSummaryTableRow, bottomOfAnovaBlock, sumOfSquaresWithin, bottomOfPage, k, sumOfSquaresTotal, meanSquareBlock, bottomOfSummary );  meansList := {};  variancesList := {};  sumsList := {};  lengthsList := {};    ForEach(levelList, levelsList) [ meansList := meansList ~ N(Mean(levelList));  variancesList := variancesList ~ N(UnbiasedVariance(levelList));  sumsList := sumsList ~ N(Sum(levelList)); lengthsList := lengthsList ~ Length(levelList); ];  sumOfSquaresWithin := Sum((lengthsList - 1) * variancesList); grandMean := N(Mean(meansList));  sumOfSquaresBetween := Sum(lengthsList*(meansList - grandMean)^2);     blockMeansList := {};  index := 1;  While(index <=? Length(First(levelsList)) ) [ row := MatrixColumn(levelsList, index);  blockMeansList := Append(blockMeansList,N(Mean(row)));  index++; ];  b := Length(blockMeansList);  k := Length(levelsList);  sumOfSquaresBlock := Sum(j,1,b, k*(blockMeansList[j] - grandMean)^2);  sumOfSquaresTotal := N(sumOfSquaresWithin + sumOfSquaresBetween);  sumOfSquaresWithin := N(sumOfSquaresTotal - sumOfSquaresBetween - sumOfSquaresBlock);  meanSquareBetween := N(sumOfSquaresBetween/(k - 1));  meanSquareWithin := N(sumOfSquaresWithin/((k - 1)*(b - 1)));  fScore := N(meanSquareBetween/meanSquareWithin);  meanSquareBlock := N(sumOfSquaresBlock/(b - 1));  fScoreBlock := N(meanSquareBlock/meanSquareWithin);  criticalFScore := ProbabilityToFScore(k - 1, (k - 1)*(b - 1), 1-alpha);  criticalFScoreBlock := ProbabilityToFScore(b - 1, (k - 1)*(b - 1), 1-alpha);    topOfPage :=\" <html> <title> Anova: Completely Randomized Block </title>  <body>\";  topOfSummary :=\" <h2>Anova: Completely Randomized Block</h2>  <TABLE BORDER> <CAPTION align=\\\"left\\\"> <h3>Summary</h3> </CAPTION>  <TR> <TH> Level </TH> <TH> Count</TH> <TH> Sum </TH> <TH> Mean </TH> <TH> Variance </TH> </TR>\";  summaryTableRows := \"\";  summaryTableRow := \"<TR> <TD> <?Write(ToAtom(ToString(Level)~ToString(index)));?> </TD> <TD align=\\\"right\\\"> <?Write(lengthsList[index]);?> </TD> <TD> <?Write(sumsList[index]);?> </TD> <TD> <?Write(meansList[index]);?> </TD> <TD> <?Write(variancesList[index]);?> </TD> </TR>\"~Nl();    index := 1;  While(index <=? Length(levelsList)) [ summaryTableRows := summaryTableRows ~ PatchString(summaryTableRow);  index++; ];    blockSummaryTableRow := \"<TR> <TD> <?Write(ToAtom(\\\"Block\\\"~ToString(index)));?> </TD> <TD align=\\\"right\\\"> <?Write(Length(row));?> </TD> <TD> <?Write(N(Sum(row)));?> </TD> <TD> <?Write(N(Mean(row)));?> </TD> <TD> <?Write(N(UnbiasedVariance(row)));?> </TD> </TR>\"~Nl(); index := 1;  While(index <=? Length(First(levelsList)) ) [ row := MatrixColumn(levelsList, index);  summaryTableRows := summaryTableRows ~ PatchString(blockSummaryTableRow);  index++; ];      bottomOfSummary :=\" </TABLE>\";  topOfAnovaBlock :=\" <br \\> <br \\>  <TABLE BORDER> <CAPTION align=\\\"left\\\"> <h3>ANOVA: Completely Randomized Block</h3> </CAPTION>  <TR> <TH> Source of Variation </TH> <TH> Sum of Squares </TH> <TH> Degrees of Freedom </TH> <TH> Mean Square </TH> <TH> F </TH> <TH> F Critical </TH> </TR>\";   anovaBlockTableRow1 := PatchString(\"<TR> <TD> <?Write(ToAtom(\\\"Between Levels\\\"));?> </TD> <TD > <?Write(sumOfSquaresBetween);?> </TD> <TD> <?Write(k - 1);?> </TD> <TD > <?Write(meanSquareBetween);?> </TD><TD> <?Write(fScore);?> </TD> <TD> <?Write(criticalFScore);?> </TD> </TR>\"~Nl());  anovaBlockTableRow2 := PatchString(\"<TR> <TD> <?Write(ToAtom(\\\"Between Blocks\\\"));?> </TD> <TD > <?Write(sumOfSquaresBlock);?> </TD> <TD> <?Write(b - 1);?> </TD> <TD > <?Write(meanSquareBlock);?> </TD> <TD> <?Write(fScoreBlock);?> </TD> <TD> <?Write(criticalFScoreBlock);?> </TD> </TR>\"~Nl()); anovaBlockTableRow3 := PatchString(\"<TR> <TD> <?Write(ToAtom(\\\"Within Levels\\\"));?> </TD> <TD > <?Write(sumOfSquaresWithin);?> </TD> <TD> <?Write(b - 1);?> </TD> <TD > <?Write(meanSquareWithin);?> </TD></TR>\"~Nl()); bottomOfAnovaBlock :=\" </TABLE>\";  bottomOfPage :=\" </body>  </html>\";  htmlJavaString := JavaNew(\"java.lang.String\", topOfPage ~ topOfSummary ~ summaryTableRows ~ bottomOfSummary ~ topOfAnovaBlock ~ anovaBlockTableRow1 ~ anovaBlockTableRow2 ~ anovaBlockTableRow3 ~ bottomOfAnovaBlock ~ bottomOfPage);    result := {};  result[\"html\"] := htmlJavaString;  result[\"sumOfSquaresWithin\"] := sumOfSquaresWithin;  result[\"sumOfSquaresBetween\"] := sumOfSquaresBetween;  result[\"sumOfSquaresBlock\"] := sumOfSquaresBlock;  result[\"sumOfSquaresTotal\"] := sumOfSquaresTotal;  result[\"meanSquareBetween\"] := meanSquareBetween;  result[\"meanSquareWithin\"] := meanSquareWithin;  result[\"meanSquareBlock\"] := meanSquareBlock;  result[\"fScore\"] := fScore;  result[\"criticalFScore\"] := criticalFScore;  result[\"fScoreBlock\"] := fScoreBlock;  result[\"criticalFScoreBlock\"] := criticalFScoreBlock;  result;];";
        scriptMap.put("AnovaCompletelyRandomizedBlock",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "AnovaSingleFactor(levelsList, alpha) :=[ Check(ListOfLists?(levelsList), \"Argument\", \"The first argument must be a list of lists.\");  Check(alpha >=? 0 And? alpha <=? 1, \"Argument\", \"The second argument must be a number between 0 and 1.\");  Local( anovaTableRow1, anovaTableRow2, anovaTableRow3, anovaTableTotal, bottomOfAnova, bottomOfPage, bottomOfSummary, criticalFScore, degreesOfFreedomBetween, degreesOfFreedomWithin, fScore, grandMean, htmlJavaString, index, lengthsList, meansList, meanSquareBetween, meanSquareWithin, result, summaryTableRow, summaryTableRows, sumOfSquaresBetween, sumOfSquaresTotal, sumOfSquaresWithin, sumsList, topOfAnova, topOfPage, topOfSummary, variancesList); meansList := {};  variancesList := {};  sumsList := {};  lengthsList := {};  ForEach(levelList, levelsList) [ meansList := meansList ~ N(Mean(levelList));  variancesList := variancesList ~ N(UnbiasedVariance(levelList));  sumsList := sumsList ~ N(Sum(levelList)); lengthsList := lengthsList ~ Length(levelList); ];  sumOfSquaresWithin := Sum((lengthsList - 1) * variancesList); grandMean := N(Mean(Flatten(levelsList, \"List\")));  sumOfSquaresBetween := Sum(lengthsList*(meansList - grandMean)^2);  sumOfSquaresTotal := N(sumOfSquaresWithin + sumOfSquaresBetween);  degreesOfFreedomBetween := (Length(levelsList)-1);  degreesOfFreedomWithin := (ElementCount(levelsList) - Length(levelsList));  meanSquareBetween := N(sumOfSquaresBetween/degreesOfFreedomBetween);  meanSquareWithin := N(sumOfSquaresWithin/degreesOfFreedomWithin);  fScore := N(meanSquareBetween/meanSquareWithin);  criticalFScore := ProbabilityToFScore(degreesOfFreedomBetween, degreesOfFreedomWithin, 1-alpha);  topOfPage :=\" <html> <title> Anova: Single Factor </title>  <body>\";  topOfSummary :=\" <h2>Anova: Single Factor</h2>  <TABLE BORDER> <CAPTION align=\\\"left\\\"> <h3>Summary</h3> </CAPTION>  <TR> <TH> Level </TH> <TH> Count</TH> <TH> Sum </TH> <TH> Mean </TH> <TH> Variance </TH> </TR>\";  summaryTableRows := \"\";  summaryTableRow := \"<TR> <TD> <?Write(ToAtom(\\\"Level\\\"~ToString(index)));?> </TD> <TD align=\\\"right\\\"> <?Write(lengthsList[index]);?> </TD> <TD> <?Write(sumsList[index]);?> </TD> <TD> <?Write(meansList[index]);?> </TD> <TD> <?Write(variancesList[index]);?> </TD> </TR>\"~Nl();  index := 1; While(index <=? Length(levelsList)) [ summaryTableRows := summaryTableRows ~ PatchString(summaryTableRow);  index++; ];   bottomOfSummary :=\" </TABLE>\";  topOfAnova :=\" <br \\> <br \\>  <TABLE BORDER> <CAPTION align=\\\"left\\\"> <h3>ANOVA</h3> </CAPTION>  <TR> <TH> Source of Variation </TH> <TH> Sum of Squares </TH> <TH> Degrees of Freedom </TH> <TH> Mean Square Between </TH> <TH> F </TH> <TH> F Critical </TH> </TR>\";   anovaTableRow1 := PatchString(\"<TR> <TD> <?Write(ToAtom(\\\"Between Levels\\\"));?> </TD> <TD > <?Write(sumOfSquaresBetween);?> </TD> <TD> <?Write(degreesOfFreedomBetween);?> </TD> <TD > <?Write(meanSquareBetween);?> </TD><TD> <?Write(fScore);?> </TD> <TD> <?Write(criticalFScore);?> </TD> </TR>\"~Nl());  anovaTableRow2 := PatchString(\"<TR> <TD> <?Write(ToAtom(\\\"Within Levels\\\"));?> </TD> <TD > <?Write(sumOfSquaresWithin);?> </TD> <TD> <?Write(degreesOfFreedomWithin);?> </TD> <TD > <?Write(meanSquareWithin);?> </TD></TR>\"~Nl());  anovaTableTotal := PatchString(\"<TR> <TD> Total </TD> <TD> <?Write(sumOfSquaresTotal);?> </TD> <TD> <?Write(degreesOfFreedomBetween + degreesOfFreedomWithin);?> </TD> </TR>\");  bottomOfAnova :=\" </TABLE>\";  bottomOfPage :=\" </body>  </html>\";  htmlJavaString := JavaNew(\"java.lang.String\", topOfPage ~  topOfSummary ~ summaryTableRows ~ bottomOfSummary ~ topOfAnova ~ anovaTableRow1 ~  anovaTableRow2 ~ anovaTableTotal ~  bottomOfAnova ~ bottomOfPage);    result := {};  result[\"html\"] := htmlJavaString;  result[\"sumOfSquaresWithin\"] := sumOfSquaresWithin;  result[\"sumOfSquaresBetween\"] := sumOfSquaresBetween;  result[\"sumOfSquaresTotal\"] := sumOfSquaresTotal;  result[\"degreesOfFreedomBetween\"] := degreesOfFreedomBetween;  result[\"degreesOfFreedomWithin\"] := degreesOfFreedomWithin;  result[\"meanSquareBetween\"] := meanSquareBetween;  result[\"meanSquareWithin\"] := meanSquareWithin;  result[\"fScore\"] := fScore;  result[\"criticalFScore\"] := criticalFScore;  result;];";
        scriptMap.put("AnovaSingleFactor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BinomialDistributionMean(probability,numberOfTrials) :=[ Check(RationalOrNumber?(probability) And? p >=? 0 And? p <=? 1, \"Argument\", \"The first argument must be a number between 0 and 1.\");  Check(Integer?(numberOfTrials) And? numberOfTrials >=? 0, \"Argument\", \"The second argument must be an integer which is greater than 0.\");  numberOfTrials * probability;]; ";
        scriptMap.put("BinomialDistributionMean",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BinomialDistributionStandardDeviation(probability,numberOfTrials) :=[ Check(RationalOrNumber?(probability) And? p >=? 0 And? p <=? 1, \"Argument\", \"The first argument must be a number between 0 and 1.\");  Check(Integer?(numberOfTrials) And? numberOfTrials >=? 0, \"Argument\", \"The second argument must be an integer which is greater than 0.\");  SqrtN(numberOfTrials * probability * (1 - probability));]; ";
        scriptMap.put("BinomialDistributionStandardDeviation",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ChiSquareScoreToAlpha(score, degreesOfFreedom) :=[ Local(a, y, s, e, c, z, LogSqrtPi, ISqrtPi,result);   y := 0;  LogSqrtPi := 0.5723649429247000870717135;   ISqrtPi := 0.5641895835477562869480795;   if(score <=? 0.0 Or? degreesOfFreedom <? 1)  [ result := 1.0; ] else [ a := N(0.5 * score);  if (degreesOfFreedom >? 1)  [ y := If(-a <? -20, 0, ExpN(-a)); ];  s := If(Even?(degreesOfFreedom), y , (2.0 * ZScoreToProbability(-SqrtN(score))));  if (degreesOfFreedom >? 2)  [ score := 0.5 * (degreesOfFreedom - 1.0);  z := If(Even?(degreesOfFreedom), 1.0, 0.5);  if (a >? 20)  [ e := If(Even?(degreesOfFreedom), 0.0, LogSqrtPi);  c := LogN(a);  While(z <=? score)  [ e := LogN(z) + e; s := s + If(c * z - a - e <? -20, 0, ExpN(c * z - a - e)); z := z + 1.0; ]; result := s; ]  else  [ e := If(Even?(degreesOfFreedom) , 1.0, (ISqrtPi / SqrtN(a)));  c := 0.0;  While(z <=? score)  [ e := e * (a / z); c := c + e; z := z + 1.0; ];  result := c * y + s; ]; ] else [ result := s; ];  ];  N(result);];";
        scriptMap.put("ChiSquareScoreToAlpha",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CoefficientOfDetermination(x,y) :=[  Check(List?(x), \"Argument\", \"The first argument must be a list.\");  Check(List?(y), \"Argument\", \"The second argument must be a list.\");  Check(Length(x) =? Length(y), \"Argument\", \"The lists for argument 1 and argument 2 must have the same length.\");  N(CorrelationCoefficient(x,y)^2);];";
        scriptMap.put("CoefficientOfDetermination",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ConfidenceIntervalOfTheMean(sampleMean,standardDeviation,standardDeviationIsKnown,sampleSize,confidenceLevel) :=[ Check(Boolean?(standardDeviationIsKnown), \"Argument\", \"The third argument must be True or False.\");  Local(criticalZScore,criticalTScore,standardErrorOfTheMean,upperLimitValue,lowerLimitValue,resultList);  resultList := {};  If(sampleSize >=? 30 Or? standardDeviationIsKnown =? True, [ criticalZScore := N(ConfidenceLevelToZScore(confidenceLevel));  resultList[\"criticalZScore\"] := criticalZScore; standardErrorOfTheMean := N(StandardErrorOfTheMean(standardDeviation,sampleSize));  lowerLimitValue := N(sampleMean - criticalZScore * standardErrorOfTheMean);  upperLimitValue := N(sampleMean + criticalZScore * standardErrorOfTheMean);  If(InVerboseMode(), [ Echo(\"Using the normal distribution.\");  Echo(\"Critical z-score: \", criticalZScore);  Echo(\"Standard error of the mean: \", standardErrorOfTheMean); ]); ], [  criticalTScore := OneTailAlphaToTScore(sampleSize - 1, N((1 - confidenceLevel)/2));  resultList[\"criticalTScore\"] := criticalTScore;  standardErrorOfTheMean := N(StandardErrorOfTheMean(standardDeviation,sampleSize));  lowerLimitValue := N(sampleMean - criticalTScore * standardErrorOfTheMean);  upperLimitValue := N(sampleMean + criticalTScore * standardErrorOfTheMean);   If(InVerboseMode(), [ Echo(\"Using the t-distribution.\");  Echo(\"Critical t-score: \", criticalTScore);  Echo(\"Standard error of the mean: \", standardErrorOfTheMean); ]);  ]);  resultList[\"upperLimit\"] := upperLimitValue;  resultList[\"lowerLimit\"] := lowerLimitValue; resultList;];";
        scriptMap.put("ConfidenceIntervalOfTheMean",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ConfidenceIntervalOfTheProportion(numberOfSuccesses,sampleSize,confidenceLevel) :=[ Check(Integer?(numberOfSuccesses) And? numberOfSuccesses >=? 0, \"Argument\", \"The first argument must be an integer which is >=?0\");  Check(Integer?(sampleSize) And? sampleSize >=? 0, \"Argument\", \"The second argument must be an integer which is >=?0\");  Local(criticalZScore,approximateStandardErrorOfTheProportion,upperLimit,lowerLimit,resultList,proportion);  resultList := {};  criticalZScore := ConfidenceLevelToZScore(confidenceLevel);  resultList[\"criticalZScore\"] := criticalZScore;  proportion := N(numberOfSuccesses/sampleSize);  approximateStandardErrorOfTheProportion := Sqrt((proportion*(1 - proportion))/sampleSize);  upperLimit := N(proportion + criticalZScore * approximateStandardErrorOfTheProportion);  lowerLimit := N(proportion - criticalZScore * approximateStandardErrorOfTheProportion);  If(InVerboseMode(), [ Echo(\"Critical z-score: \", criticalZScore);  Echo(\"Proportion: \", proportion);  Echo(\"Standard error of the proportion: \", N(approximateStandardErrorOfTheProportion)); ]);  resultList[\"upperLimit\"] := upperLimit;  resultList[\"lowerLimit\"] := lowerLimit;  resultList;];";
        scriptMap.put("ConfidenceIntervalOfTheProportion",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ConfidenceLevelToZScore(probability) :=[  probability := probability + (1 - probability)/2;  ProbabilityToZScore(probability);];";
        scriptMap.put("ConfidenceLevelToZScore",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ControlChartConstants(n) :=[  Check(n >=? 2 And? n <=? 15, \"Argument\", \"The argument n must be 2 <=? n <=? 20.\");  Local(result, table);  result := {};  n--;  table := { {1.880, 1.128, 0.000, 3.267}, {1.023, 1.693, 0.000, 2.574}, {0.729, 2.059, 0.000, 2.282}, {0.577, 2.326, 0.000, 2.114}, {0.483, 2.534, 0.000, 2.004}, {0.419, 2.704, 0.076, 1.924}, {0.373, 2.847, 0.136, 1.864}, {0.337, 2.970, 0.184, 1.816}, {0.308, 3.078, 0.223, 1.777}, {0.285, 3.173, 0.256, 1.744}, {0.266, 3.258, 0.283, 1.717}, {0.249, 3.336, 0.307, 1.693}, {0.235, 3.407, 0.328, 1.672}, {0.223, 3.472, 0.347, 1.653}, {0.212, 3.532, 0.363, 1.637}, {0.203, 3.588, 0.378, 1.622}, {0.194, 3.640, 0.391, 1.608}, {0.187, 3.689, 0.403, 1.597}, {0.180, 3.735, 0.415, 1.585}, }; result[\"D4\"] := table[n][4];  result[\"D3\"] := table[n][3];  result[\"d2\"] := table[n][2];  result[\"A2\"] := table[n][1];  result;];";
        scriptMap.put("ControlChartConstants",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CorrelationCoefficient(x,y) :=[  Check(List?(x), \"Argument\", \"The first argument must be a list.\");  Check(List?(y), \"Argument\", \"The second argument must be a list.\");  Check(Length(x) =? Length(y), \"Argument\", \"The lists for argument 1 and argument 2 must have the same length.\");  Local(n);  n := Length(x);  N((n*Sum(x*y)-Sum(x)*Sum(y))/Sqrt((n*Sum(x^2)-(Sum(x))^2)*(n*Sum(y^2)-(Sum(y)^2))) );];";
        scriptMap.put("CorrelationCoefficient",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CorrelationMatrix(dataLists) := [ Local(namesList, correlationMatrix);  ForEach(dataList, dataLists) [ Check(Matrix?(dataLists), \"Argument\", \"All lists must have the same number of elements.\"); ]; namesList := MatrixColumn(dataLists,1);  namesList := \"\" ~ namesList;  ForEach(dataList, dataLists) [ PopFront(dataList); ];  correlationMatrix := ZeroMatrix(Length(dataLists)+1);  ForEach(rowIndex, 1 .. Length(dataLists) + 1) [ ForEach(columnIndex, 1 .. Length(dataLists) + 1) [ if(rowIndex >=? 2 And? columnIndex >=? 2) [ correlationMatrix[rowIndex][columnIndex] := N(CorrelationCoefficient(dataLists[rowIndex - 1],dataLists[columnIndex - 1]),2); ] else if(rowIndex =? 1) [ correlationMatrix[rowIndex][columnIndex] := namesList[columnIndex]; ] else [ correlationMatrix[rowIndex][columnIndex] := namesList[rowIndex]; ]; ]; ];  correlationMatrix;];";
        scriptMap.put("CorrelationMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "D2Value(k,n) :=[ Check(k >=? 0 And? k <=? 15, \"Argument\", \"The first argument k must be 0 <=? k <=? 15.\");  Check(n >=? 2 And? n <=? 15, \"Argument\", \"The second argument n must be 2 <=? n <=? 15.\");  n--;  if(k =? 0) [ {1.128,1.693,2.059,2.326,2.534,2.704,2.847,2.970,3.078,3.173,3.259,3.336,3.407,3.472}[n]; ] else [ { {1.414, 1.912, 2.239, 2.481, 2.673, 2.830, 2.963, 3.078, 3.179, 3.269, 3.350, 3.424, 3.491, 3.553}, {1.279, 1.805, 2.151, 2.405, 2.604, 2.768, 2.906, 3.025, 3.129, 3.221, 3.305, 3.380, 3.449, 3.513}, {1.231, 1.769, 2.120, 2.379, 2.581, 2.747, 2.886, 3.006, 3.112, 3.205, 3.289, 3.366, 3.435, 3.499}, {1.206, 1.750, 2.105, 2.366, 2.570, 2.736, 2.877, 2.997, 3.103, 3.197, 3.282, 3.358, 3.428, 3.492}, {1.191, 1.739, 2.096, 2.358, 2.563, 2.730, 2.871, 2.992, 3.098, 3.192, 3.277, 3.354, 3.424, 3.488}, {1.181, 1.731, 2.090, 2.353, 2.558, 2.726, 2.867, 2.988, 3.095, 3.189, 3.274, 3.351, 3.421, 3.486}, {1.173, 1.726, 2.085, 2.349, 2.555, 2.723, 2.864, 2.986, 3.092, 3.187, 3.272, 3.349, 3.419, 3.484}, {1.168, 1.721, 2.082, 2.346, 2.552, 2.720, 2.862, 2.984, 3.090, 3.185, 3.270, 3.347, 3.417, 3.482}, {1.164, 1.718, 2.080, 2.344, 2.550, 2.719, 2.860, 2.982, 3.089, 3.184, 3.269, 3.346, 3.416, 3.481}, {1.160, 1.716, 2.077, 2.342, 2.549, 2.717, 2.859, 2.981, 3.088, 3.183, 3.268, 3.345, 3.415, 3.480}, {1.157, 1.714, 2.076, 2.340, 2.547, 2.716, 2.858, 2.980, 3.087, 3.182, 3.267, 3.344, 3.415, 3.479}, {1.155, 1.712, 2.074, 2.344, 2.546, 2.715, 2.857, 2.979, 3.086, 3.181, 3.266, 3.343, 3.414, 3.479}, {1.153, 1.710, 2.073, 2.338, 2.545, 2.714, 2.856, 2.978, 3.085, 3.180, 3.266, 3.343, 3.413, 3.478}, {1.151, 1.709, 2.072, 2.337, 2.545, 2.714, 2.856, 2.978, 3.085, 3.180, 3.265, 3.342, 3.413, 3.478}, {1.150, 1.708, 2.071, 2.337, 2.544, 2.713, 2.855, 2.977, 3.084, 3.179, 3.265, 3.342, 3.412, 3.477} }[k][n]; ]; ];";
        scriptMap.put("D2Value",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ErrorFunction(x) :=[ Local(a1,a2,a3,a4,a5,p,sign,t,y);  a1 := 0.254829592; a2 := -0.284496736; a3 := 1.421413741; a4 := -1.453152027; a5 := 1.061405429; p := 0.3275911;  sign := 1;  If(x <? 0, sign := -1);  x := AbsN(x);  t := 1.0/(1.0 + p*x); y := N(1.0 - (((((a5*t + a4)*t) + a3)*t + a2)*t + a1)*t*Exp(-x*x)); sign*y;];";
        scriptMap.put("ErrorFunction",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Mode(list) :=[ Check(Length(list) >? 0 And? NumericList?(list), \"Argument\", \"Argument must be a nonempty numeric list.\");  Local(noDuplicatesList, countsList, sortedList, highestCountsList, resultList);  noDuplicatesList := RemoveDuplicates(list);  countsList := {};  ForEach(element, noDuplicatesList) [ countsList := Append(countsList, {Count(list, element), element} ); ];  sortedList := HeapSort(countsList,Lambda({x,y},x[1] >? y[1]));  highestCountsList := Select(sortedList, Lambda({x},x[1] =? sortedList[1][1]));  resultList := MapSingle(Lambda({x},x[2]), highestCountsList);];";
        scriptMap.put("Mode",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Permutations(n) :=[ Check(Integer?(n), \"Argument\", \"Argument must be an integer\"); n!;];Permutations(n, r) :=[ Check(Integer?(n), \"Argument\", \"Argument 1 must be an integer\");  Check(Integer?(r), \"Argument\", \"Argument 2 must be an integer\");  n! /(n-r)!;];";
        scriptMap.put("Permutations",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ProbabilityToZScore(probability) :=[ Local(ZMAX,ZEPSILON,minimumZ,maximumZ,zValue,probabilityValue);  probability := N(probability);  Check(probability >=? 0.0 And? probability <=? 1.0, \"Argument\", \"The argument must be between 0 and 1.\");  ZMAX := 6;   ZEPSILON := 0.000001;   minimumZ := -ZMAX;  maximumZ := ZMAX;  zValue := 0.0; While ((maximumZ - minimumZ) >? ZEPSILON)  [ probabilityValue := ZScoreToProbability(zValue);  if (probabilityValue >? probability)  [ maximumZ := zValue; ]  else  [ minimumZ := zValue; ];  zValue := (maximumZ + minimumZ) * 0.5; ];  zValue;];";
        scriptMap.put("ProbabilityToZScore",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Quartile(list) :=[ sortedList := HeapSort(list,\"<?\");  secondQuartile := Median(sortedList);  If(Odd?(Length(sortedList)), [  secondQuartileIndex := Find(sortedList, secondQuartile);  leftList := Take(sortedList, secondQuartileIndex-1); rightList := Take(sortedList, -(Length(sortedList) - (secondQuartileIndex) ) ); ], [ leftList := Take(sortedList, Length(sortedList)/2); rightList := Take(sortedList, -Length(sortedList)/2); ] );  firstQuartile := Median(leftList);  thirdQuartile := Median(rightList);  interquartileRange := thirdQuartile - firstQuartile;  {firstQuartile, secondQuartile, thirdQuartile, interquartileRange};];";
        scriptMap.put("Quartile",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RandomPick(list) :=[ Check(List?(list), \"Argument\", \"Argument must be a list.\");  Check(Length(list) >? 0, \"Argument\", \"The number of elements in the list must be greater than 0.\");  Local(pickPosition);  pickPosition := RandomInteger(Length(list)); list[pickPosition];];";
        scriptMap.put("RandomPick",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RandomPickVector(list, count) :=[ Check(List?(list), \"Argument\", \"Argument 1 must be a list.\");  Check(Integer?(count), \"Argument\", \"Argument 2 must be an integer.\");  Table(RandomPick(list),x,1,count,1);];";
        scriptMap.put("RandomPickVector",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RandomPickWeighted(list) :=[ Check(List?(list), \"Argument\", \"Argument must be a list.\");  Local(element, probabilities, items, lastWeight, randomNumber, result);  probabilities := 0;  items := {};  lastWeight := 0;     ForEach(element,list) [  probability := element[2]; probabilities := probabilities + probability; ];  Check(probabilities =? 1, \"Argument\", \"The probabilities must sum to 1.\");     ForEach(element,list) [ probability := element[2];  item := element[1];  items := Append(items, {item, {lastWeight, lastWeight := lastWeight + N(probability)}} ); ];     randomNumber := Random();  ForEach(itemData,items) [ If(randomNumber >=? itemData[2][1] And? randomNumber <=? itemData[2][2], result := itemData[1] ); ];    result;];";
        scriptMap.put("RandomPickWeighted",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Range(list) :=[ Check(Length(list) >? 0 And? NumericList?(list), \"Argument\", \"Argument must be a nonempty numeric list.\");  Maximum(list) - Minimum(list);];";
        scriptMap.put("Range",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RegressionLine(x,y) :=[  Check(List?(x), \"Argument\", \"The first argument must be a list.\");  Check(List?(y), \"Argument\", \"The second argument must be a list.\");  Check(Length(x) =? Length(y), \"Argument\", \"The lists for argument 1 and argument 2 must have the same length.\");  Local(n,a,b,xMean,yMean,line,result);  n := Length(x);  b := N((n*Sum(x*y) - Sum(x)*Sum(y))/(n*Sum(x^2)-(Sum(x))^2));  xMean := N(Mean(x));  yMean := N(Mean(y));  a := N(yMean - b*xMean);  line := a + b*Hold(x);  result := {};  result[\"xMean\"] := xMean;  result[\"yMean\"] := yMean;  result[\"line\"] := line;  result[\"yIntercept\"] := a;  result[\"slope\"] := b;  result[\"count\"] := n;  result;];";
        scriptMap.put("RegressionLine",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RegressionLineConfidenceInterval(x,y,xValue,confidenceLevel) :=[  Check(List?(x), \"Argument\", \"The first argument must be a list.\");  Check(List?(y), \"Argument\", \"The second argument must be a list.\");  Check(Length(x) =? Length(y), \"Argument\", \"The lists for argument 1 and argument 2 must have the same length.\");  Check(confidenceLevel >=?0 And? confidenceLevel <=?1, \"Argument\", \"The confidence level must be >=? 0 and <=? 1.\");  Local(n,a,b,xMean,part,result,criticalTScore,standardErrorOfTheEstimate);  regressionLine := RegressionLine(x,y);  n := regressionLine[\"count\"];  f(x) := [Eval(regressionLine[\"line\"]);];  criticalTScore := OneTailAlphaToTScore(n-2, N((1 - confidenceLevel)/2));  standardErrorOfTheEstimate := StandardErrorOfTheEstimate(x,y); xMean := regressionLine[\"xMean\"]; part := N(criticalTScore * standardErrorOfTheEstimate * Sqrt(1/n + ((xValue - xMean)^2)/(Sum(x^2) - Sum(x)^2/n)));  result := {};  result[\"upper\"] := f(xValue) + part;  result[\"lower\"] := f(xValue) - part;  result;];";
        scriptMap.put("RegressionLineConfidenceInterval",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(count, iterations, body)[RulebaseHoldArguments(\"Repeat\",{iterations,body});RuleHoldArguments(\"Repeat\",2,10,Integer?(iterations) And? iterations >? 0)[ Local(count); count := 0; While (iterations >? 0) [ Eval(body); iterations--; count++; ]; count;];RulebaseHoldArguments(\"Repeat\",{body});RuleHoldArguments(\"Repeat\",1,20,True)[ Local(count); count := 0; While (True) [ Eval(body); count++; ]; count;];];UnFence(\"Repeat\",2);HoldArgumentNumber(\"Repeat\",2,2);UnFence(\"Repeat\",1);HoldArgumentNumber(\"Repeat\",1,1);";
        scriptMap.put("Repeat",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Sample(list, sampleSize) :=[ Check(List?(list), \"Argument\", \"The first argument must be a list.\");  Check(Integer?(sampleSize) And? sampleSize >? 0, \"Argument\", \"The second argument must be an integer which is greater than 0.\");  list := Shuffle(list); Take(list, sampleSize);];";
        scriptMap.put("Sample",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SampleSizeForTheMean(standardDeviation,confidenceLevel,marginOfError) :=[ Local(minimumSampleSize);  zScore := ConfidenceLevelToZScore(confidenceLevel);  minimumSampleSize := N(((zScore*standardDeviation)/marginOfError)^2);];";
        scriptMap.put("SampleSizeForTheMean",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SampleSizeForTheProportion(probabilityOfSuccess,confidenceLevel,marginOfError) :=[ Check(probabilityOfSuccess >=?0 And? probabilityOfSuccess <=? 1, \"Argument\", \"The first argument must be between 0 and 1.\"); Local(minimumSampleSize,zScore);  zScore := ConfidenceLevelToZScore(confidenceLevel);  minimumSampleSize := N(probabilityOfSuccess*(1 - probabilityOfSuccess)*(zScore/marginOfError)^2);];";
        scriptMap.put("SampleSizeForTheProportion",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ScheffeTest(levelsList, alpha) :=[ Check(ListOfLists?(levelsList), \"Argument\", \"The first argument must be a list of lists.\");  Check(alpha >=? 0 And? alpha <=? 1, \"Argument\", \"The second argument must be a number between 0 and 1.\");  Local( result, topOfSummary, pairsList, xBarB, xBarA, summaryTableRow, ssw, nA, scheffeStatisticCalculated, nB, summaryList, topOfPage, htmlJavaString, summaryTableRows, meansList, index,b, pairList, a, bottomOfPage, k, countsList, oneComparisonList, scheffeStatistic, bottomOfSummary, resultList);  anova := AnovaSingleFactor(levelsList, alpha);  k := Length(levelsList);  scheffeStatisticCalculated := (k-1)*anova[\"criticalFScore\"];  resultList := {};  resultList[\"scheffeStatisticCalculated\"] := scheffeStatisticCalculated;  meansList := {};  countsList := {};  ForEach(levelList,levelsList) [ meansList := meansList ~ N(Mean(levelList));  countsList := countsList ~ Length(levelList); ];  pairsList := CombinationsList(1 .. Length(levelsList),2);  summaryList := {};  index := 1;  ForEach(pairList, pairsList) [  a := pairList[1];  b := pairList[2];  xBarA := meansList[a];  nA := countsList[a];  xBarB := meansList[b];  nB := countsList[b];  ssw := anova[\"sumOfSquaresWithin\"];  scheffeStatistic := ScheffeStatistic(xBarA,nA,xBarB,nB,ssw,k,countsList);  oneComparisonList := {};  oneComparisonList[\"conclusion\"] := If(scheffeStatistic <=? scheffeStatisticCalculated, \"No Difference\", \"Difference\");  oneComparisonList[\"scheffeStatistic\"] := scheffeStatistic;  oneComparisonList[\"pair\"] := pairList;  summaryList[\"pair\" ~ ToString(index)] := oneComparisonList;  index++; ];  resultList[\"summary\"] := summaryList;     topOfPage :=\" <html> <title> Scheffe Test Summary </title>  <body>\";  topOfSummary :=\" <h2>Scheffe Test Summary</h2>  <TABLE BORDER> <CAPTION align=\\\"left\\\"> <h3>Summary</h3> </CAPTION>  <TR> <TH> Sample Pair</TH> <TH> Measured Scheffe Statistic </TH> <TH> Calculated Scheffe Statistic </TH> <TH> Conclusion </TH> </TR>\";  summaryTableRows := \"\";  summaryTableRow := \"<TR> <TD align=\\\"center\\\"> <?Write(ToAtom(ToString(pairList[1]) ~ \\\" and \\\" ` ToString(pairList[2])));?> </TD> <TD align=\\\"right\\\"> <?Write(summary[\\\"scheffeStatistic\\\"]);?> </TD> <TD align=\\\"right\\\"> <?Write(resultList[\\\"scheffeStatisticCalculated\\\"]);?> </TD> <TD> <?Write(ToAtom(summary[\\\"conclusion\\\"]));?> </TD> </TR>\"~Nl();   ForEach(summary, Reverse(resultList[\"summary\"])) [ summary := summary[2]; pairList := summary[\"pair\"];  summaryTableRows := summaryTableRows ~ PatchString(summaryTableRow);  index++; ];   bottomOfSummary :=\" </TABLE>\";   bottomOfPage :=\" </body>  </html>\";  htmlJavaString := JavaNew(\"java.lang.String\", topOfPage ~ topOfSummary ~ summaryTableRows ~ bottomOfSummary ~ bottomOfPage);      resultList[\"html\"] := htmlJavaString;   DestructiveReverse(resultList);];ScheffeStatistic(xBarA,nA,xBarB,nB,ssw,k,countsList) :=[ N(((xBarA-xBarB)^2)/((ssw/Sum(i,1,k,(countsList[i] - 1))*(1/nA + 1/nB)))); ];";
        scriptMap.put("ScheffeTest",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Shuffle(list) :=[ Check(List?(list), \"Argument\", \"Argument must be a list.\");  Local(index, randomIndex, temporary);  list := FlatCopy(list);  index := Length(list);  While(index >? 1) [ randomIndex := RandomInteger(1,index);  temporary := list[randomIndex];  list[randomIndex] := list[index];  list[index] := temporary;  index--; ];   list;];";
        scriptMap.put("Shuffle",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ShuffledDeckNoSuits() := [ Shuffle(Concat(1 .. 13, 1 .. 13, 1 .. 13, 1 .. 13));];";
        scriptMap.put("ShuffledDeckNoSuits",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "StandardErrorOfTheEstimate(xList,yList) :=[  Check(List?(xList), \"Argument\", \"The first argument must be a list.\");  Check(List?(yList), \"Argument\", \"The second argument must be a list.\");  Check(Length(xList) =? Length(yList), \"Argument\", \"The lists for argument 1 and argument 2 must have the same length.\");  Local(n,a,b,regressionLine);  regressionLine := RegressionLine(xList,yList);  n := regressionLine[\"count\"];  a := regressionLine[\"yIntercept\"];  b := regressionLine[\"slope\"];  N(Sqrt((Sum(yList^2) - a*Sum(yList) - b*Sum(xList*yList))/(n-2)));];";
        scriptMap.put("StandardErrorOfTheEstimate",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "StandardErrorOfTheMean(sigma, sampleSize) :=[ Check(sigma >? 0, \"Argument\", \"The first argument must be a number which is greater than 0.\");  Check(Integer?(sampleSize) And? sampleSize >? 0, \"Argument\", \"The second argument must be an integer which is greater than 0.\");  sigma/Sqrt(sampleSize);];";
        scriptMap.put("StandardErrorOfTheMean",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "StandardErrorOfTheProportion(meanOfSampleProportions, sampleSize) :=[ Check(RationalOrNumber?(meanOfSampleProportions), \"Argument\", \"The first argument must be a number.\");  Check(Integer?(sampleSize) And? sampleSize >? 0, \"Argument\", \"The second argument must be an integer which is greater than 0.\");  Sqrt((meanOfSampleProportions*(1 - meanOfSampleProportions))/sampleSize);];";
        scriptMap.put("StandardErrorOfTheProportion",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "StandardErrorOfTheSlope(xList,yList) :=[  Check(List?(xList), \"Argument\", \"The first argument must be a list.\");  Check(List?(yList), \"Argument\", \"The second argument must be a list.\");  Check(Length(xList) =? Length(yList), \"Argument\", \"The lists for argument 1 and argument 2 must have the same length.\");  Local(standardErrorOfTheEstimate,n,xMean);  standardErrorOfTheEstimate := StandardErrorOfTheEstimate(xList,yList);  n := Length(xList);  xMean := Mean(xList);  N(standardErrorOfTheEstimate/Sqrt(Sum(xList^2) - n*xMean^2));];";
        scriptMap.put("StandardErrorOfTheSlope",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Subset?(bigList, littleList) :=[ Local(result); result := True;  ForEach(element, littleList) [ If(Not? Contains?(bigList,element), result := False); ]; result;];";
        scriptMap.put("Subset?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ValueToZScore(value,mean,standardDeviation) :=[ (value - mean)/standardDeviation;];";
        scriptMap.put("ValueToZScore",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "WeightedMean(list) :=[ Check(List?(list), \"Argument\", \"Argument must be a list.\");  Local( values, lastWeight, weights );  values := {};  weights := {};   ForEach(element,list) [  Check(List?(element), \"Argument\", \"Values and their associated weights must be in a list.\");  Check(Length(element) =? 2, \"Argument\", \"Each value and its associated weight must be in a two element list.\");  values := values ~ element[1]; weights := weights ~ element[2]; ];  Sum(values * weights)/Sum(weights);];";
        scriptMap.put("WeightedMean",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ZScoreToProbability(zScore) :=[ zScore := N(zScore);  Local( y, x, w, ZMAX, result);  ZMAX := 6;   if(zScore =? 0.0) [ x := 0.0; ] else [ y := 0.5 * AbsN(zScore);  if(y >? ZMAX * 0.5) [ x := 1.0; ] else if(y <? 1.0) [ w := y * y; x := ((((((((0.000124818987 * w - 0.001075204047) * w + 0.005198775019) * w - 0.019198292004) * w + 0.059054035642) * w - 0.151968751364) * w + 0.319152932694) * w - 0.531923007300) * w + 0.797884560593) * y * 2.0; ] else [ y := y - 2.0;  x := (((((((((((((-0.000045255659 * y + 0.000152529290) * y - 0.000019538132) * y - 0.000676904986) * y + 0.001390604284) * y - 0.000794620820) * y - 0.002034254874) * y + 0.006549791214) * y - 0.010557625006) * y + 0.011630447319) * y - 0.009279453341) * y + 0.005353579108) * y - 0.002141268741) * y + 0.000535310849) * y + 0.999936657524; ]; ];   If(zScore >? 0.0 , result := (x + 1.0) * 0.5 , result := (1.0 - x) * 0.5);  result;];";
        scriptMap.put("ZScoreToProbability",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ZScoreToValue(zScore) :=[ -((-mean)/standardDeviation - zScore)*standardDeviation;];";
        scriptMap.put("ZScoreToValue",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Pslq(x, precision) :=[ Local (ndigits, gam, A, B, H, n, i, j, k, s, y, tmp, t, m, maxi, gami, t0, t1, t2, t3, t4, mini, Confidence, norme,result); n:=Length(x); ndigits:=BuiltinPrecisionGet(); BuiltinPrecisionSet(precision+10);  Confidence:=10^(-FloorN(N(Eval(precision/3)))); gam:=N(Sqrt(4/3)); For (i:=1, i<=?n,i++) x[i]:=N(Eval(x[i])); A:=Identity(n);  B:=Identity(n);  s:=ZeroVector(n); y:=ZeroVector(n); For(k:=1,k<=?n,k++) [ tmp:=0; For (j:=k,j<=?n,j++) tmp:=tmp + N(x[j]^2); s[k]:=SqrtN(tmp); ]; tmp:=N(Eval(s[1])); For (k:= 1,k<=? n,k++) [ y[k]:=N(Eval(x[k]/tmp)); s[k]:=N(Eval(s[k]/tmp)); ]; H:=ZeroMatrix(n, n-1); For (i:=1,i<=? n,i++) [ if (i <=? n-1) [ H[i][i]:=N(s[i + 1]/s[i]); ]; For (j:= 1,j<=?i-1,j++) [ H[i][j]:= N(-(y[i]*y[j])/(s[j]*s[j + 1])); ]; ]; For (i:=2,i<=?n,i++) [ For (j:=i-1,j>=? 1,j--) [ t:=Round(H[i][j]/H[j][j]); y[j]:=y[j] + t*y[i]; For (k:=1,k<=?j,k++) [ H[i][k]:=H[i][k]-t*H[j][k]; ]; For (k:=1,k<=?n,k++) [ A[i][k]:=A[i][k]-t*A[j][k]; B[k][j]:=B[k][j] + t*B[k][i]; ]; ]; ]; Local(found); found:=False; While (Not?(found)) [ m:=1; maxi:=N(gam*Abs(H[1][1])); gami:=gam; For (i:= 2,i<=? n-1,i++) [ gami:=gami*gam; tmp:=N(gami*Abs(H[i][i])); if (maxi <? tmp) [ maxi:=tmp; m:=i; ]; ]; tmp:=y[m + 1]; y[m + 1]:=y[m]; y[m]:=tmp; For (i:= 1,i<=?n,i++) [ tmp:=A[m + 1][ i]; A[m + 1][ i]:=A[m][ i]; A[m][ i]:=tmp; tmp:=B[i][ m + 1]; B[i][ m + 1]:=B[i][ m]; B[i][ m]:=tmp; ]; For (i:=1,i<=?n-1,i++) [ tmp:=H[m + 1][ i]; H[m + 1][ i]:=H[m][ i]; H[m][ i]:=tmp; ]; if (m <? n-1) [ t0:=N(Eval(Sqrt(H[m][ m]^2 + H[m][ m + 1]^2))); t1:=H[m][ m]/t0; t2:=H[m][ m + 1]/t0; For (i:=m,i<=?n,i++) [ t3:=H[i][ m]; t4:=H[i][ m + 1]; H[i][ m]:=t1*t3 + t2*t4; H[i][ m + 1]:= -t2*t3 + t1*t4; ]; ]; For (i:= 1,i<=? n,i++) [ For (j := Minimum(i-1, m + 1),j>=? 1,j--) [ t:=Round(H[i][ j]/H[j][ j]); y[j]:=y[j] + t*y[i]; For (k:=1,k<=?j,k++) H[i][ k]:=H[i][ k]-t*H[j][ k]; For (k:= 1,k<=?n,k++) [ A[i][ k]:=A[i][ k]-t*A[j][ k]; B[k][ j]:=B[k][ j] + t*B[k][ i]; ]; ]; ];  maxi := N(Dot(H[1], H[1])); For (j:=2,j<=?n,j++) [ tmp:=N(Dot(H[j], H[j]),10); if (maxi <? tmp) [ maxi:=tmp; ]; ]; norme:=N(Eval(1/Sqrt(maxi))); m:=1; mini:=N(Eval(Abs(y[1]))); maxi:=mini; For (j:=2,j<=?n,j++) [ tmp:=N(Eval(Abs(y[j]))); if (tmp <? mini) [ mini:=tmp; m:=j; ]; if (tmp >? maxi) [ maxi:=tmp; ]; ];  if ((mini/maxi) <? Confidence)  [  BuiltinPrecisionSet(ndigits); result:=Transpose(B)[m]; found:=True; ] else [ maxi:=Abs(A[1][ 1]); For (i:=1,i<=?n,i++) [ For (j:=1,j<=?n,j++) [ tmp:=Abs(A[i][ j]); if (maxi <? tmp) [ maxi:=tmp;]; ]; ]; if (maxi >? 10^(precision)) [ BuiltinPrecisionSet(ndigits); result:=Fail; found:=True; ]; BuiltinPrecisionSet(precision+2); ]; ]; result;];";
        scriptMap.put("Pslq",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "FastModularPower(a_PositiveInteger?, b_PositiveInteger?, n_PositiveInteger?) <--[ Local(p, j, r); p := a; j := b; r := 1; While (j >? 0) [ If (Odd?(j), r := ModuloN(r*p, n)); p := ModuloN(p*p, n); j := ShiftRight(j, 1); ]; r;];IsStronglyProbablyPrime(b_PositiveInteger?, n_PositiveInteger?) <--[ Local(m, q, r, a, flag, i, root); m := n-1; q := m; r := 0; root := 0;  While (Even?(q)) [ q := ShiftRight(q, 1); r++; ]; a := FastModularPower(b, q, n); flag := (a =? 1 Or? a =? m); i := 1; While (Not?(flag) And? (i <? r)) [ root := a;  a := ModuloN(a*a, n); flag := (a =? m); i++; ]; {root, flag}; ];10 # RabinMillerSmall(1) <-- False;10 # RabinMillerSmall(2) <-- True;20 # RabinMillerSmall(n_Even?) <-- False;20 # RabinMillerSmall(3) <-- True;30 # RabinMillerSmall(n_PositiveInteger?) <--[ Local(continue, prime, i, primetable, pseudotable, root); continue := True; prime := True; i := 1; primetable := {2, 3, 5, 7, 11, 13, 17}; pseudotable := {2047, 1373653, 25326001, 3215031751, 2152302898747, 3474749660383, 34155071728321};  While (continue And? prime And? (i <? 8)) [  {root, prime} := IsStronglyProbablyPrime(primetable[i], n);  continue := (n >=? pseudotable[i]); i++; ];  If (continue And? (i =? 8), Overflow, prime);];10 # RabinMillerProbabilistic(1, _p) <-- False;10 # RabinMillerProbabilistic(2, _p) <-- True;20 # RabinMillerProbabilistic(n_Even?, _p) <-- False;20 # RabinMillerProbabilistic(3, _p) <-- True;30 # RabinMillerProbabilistic(n_PositiveInteger?, p_PositiveInteger?) <--[ Local(k, prime, b, rootsofminus1, root); k := 1+IntLog(IntLog(n,2),4)+p;  b := 1; prime := True; rootsofminus1 := {0};  While (prime And? k>?0) [ b := NextPseudoPrime(b);  {root, prime} := IsStronglyProbablyPrime(b, n); If(prime, rootsofminus1 := Union(rootsofminus1, {root})); If(Length(rootsofminus1)>?3, prime := False);  If(  InVerboseMode() And? Length(rootsofminus1)>?3, [  Local(factor); rootsofminus1 := Difference(rootsofminus1,{0});  factor := Gcd(n, If( rootsofminus1[1]+rootsofminus1[2]=?n, rootsofminus1[1]+rootsofminus1[3], rootsofminus1[1]+rootsofminus1[2] )); Echo(n, \" = \", factor, \" * \", n/factor); ] ); k--; ]; prime;];RabinMiller(n_PositiveInteger?) <--[  If( n <? 34155071728321, RabinMillerSmall(n), RabinMillerProbabilistic(n, 40)  );];";
        scriptMap.put("RabinMiller",scriptString);
        scriptMap.put("FastModularPower",scriptString);
        scriptMap.put("IsStronglyProbablyPrime",scriptString);
        scriptMap.put("RabinMillerSmall",scriptString);
        scriptMap.put("RabinMillerProbabilistic",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RadSimp(_n)_(Length(VarList(n))<?1) <--[ Local(max, result); Bind(max, CeilN(N(Eval(n^2)))); Bind(result,0); Bind(result,RadSimpTry(n,0,1,max)); if (CheckRadicals(n,result)) result else n;];20 # RadSimp(_n) <-- n;CheckRadicals(_n,_test) <-- Abs(N(Eval(n-test),20)) <? 0.000001;10 # ClampRadicals(_r)_(N(Eval(Abs(r)), 20)<?0.000001) <-- 0;20 # ClampRadicals(_r) <-- r;RadSimpTry(_n,_result,_current,_max)<--[ if (LessThan?(N(Eval(result-n)), 0)) [ Local(i);  i:=BSearch(max,Hold({{try},ClampRadicals(N(Eval((result+Sqrt(try))-n),20))})); If(i>?0, [ Bind(result,result+Sqrt(i)); Bind(i,AddN(max,1)); Bind(current,AddN(max,1)); ]);  if (LessThan?(N(Eval(result-n)), 0)) [ For (Bind(i,current),i<=?max,Bind(i,AddN(i,1))) [ Local(new, test); Bind(test,result+Sqrt(i)); Bind(new,RadSimpTry(n,test,i,max)); if (CheckRadicals(n,new)) [ Bind(result,new); Bind(i,AddN(max,1)); ]; ]; ]; ]; result;];";
        scriptMap.put("RadSimp",scriptString);
        scriptMap.put("CheckRadicals",scriptString);
        scriptMap.put("ClampRadicals",scriptString);
        scriptMap.put("RadSimpTry",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RandomInteger(_n) <--[ Check(PositiveInteger?(n), \"Argument\", \"The argument must be a positive integer.\"); CeilN(Random() * n);];10 # RandomInteger(_lowerBoundInclusive, _upperBoundInclusive) <--[ Check(Integer?(lowerBoundInclusive) And? Integer?(upperBoundInclusive), \"Argument\", \"Both arguments must be integers.\"); Check(lowerBoundInclusive <? upperBoundInclusive, \"Argument\", \"The first argument must be less than the second argument.\"); FloorN(lowerBoundInclusive + Random() * (upperBoundInclusive + 1 - lowerBoundInclusive) );];";
        scriptMap.put("RandomInteger",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RandomIntegerList(_count,_coefmin,_coefmax) <-- Table(FloorN(coefmin+Random()*(coefmax+1-coefmin)),i,1,count,1);";
        scriptMap.put("RandomIntegerList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RandomIntegerMatrix(_rows,_cols,_coefmin,_coefmax) <-- GenMatrix({{i,j}, FloorN(coefmin+Random()*(coefmax+1-coefmin))}, rows, cols );";
        scriptMap.put("RandomIntegerMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RandomInterestingPolynomial( deg_PositiveInteger?, _var ) <--[ RandomSeed( SystemTimer() );  NewRandomPoly(deg,var); ];10 # NewRandomPoly( _deg, _var )_(Equal?(deg,1)) <--[ Local(p,i1,i2); i1 := RandomInteger(1,10); i2 := RandomInteger(-10,10); p := NormalForm(UniVariate(var,0,{i2,i1}));];10 # NewRandomPoly( _deg, _var )_(Equal?(deg,2)) <--[ Local(ii,i1,i2,p,quadPoly); p := FillList(0,2); For(ii:=1,ii<=?2,ii++) [ i1 := RandomInteger(10); i2 := RandomInteger(-10,10); If( i1 >? 1, i2 := i1*i2 ); p[ii] := NormalForm(UniVariate(var,0,{i2,i1})); ]; quadPoly := ExpandBrackets(p[1]*p[2]); quadPoly := Simplify(Quotient(quadPoly,LeadingCoef(quadPoly)));];10 # RandomIrreducibleQuadratic( _var ) <--[ Local(ii,coeffs,discrim,u,p,f);     If(RandomInteger(2)=?1, RandomIrreducibleQuadraticWithComplexRoots(var), RandomIrreducibleQuadraticWithRealRoots(var) );];10 # RandomIrreducibleQuadraticWithRealRoots(_var) <--[ Local(coeffs,ijk); coeffs := FillList(1,3); coeffs[2] := RandomInteger(-10,10); coeffs[3] := RandomInteger(1,10); ijk := Floor(coeffs[2]^2 / (4*coeffs[3])); coeffs[1] := RandomInteger(-10,ijk); discrim := coeffs[2]^2-4*coeffs[1]*coeffs[3]; NormalForm(UniVariate(var,0,coeffs));];10 # RandomIrreducibleQuadraticWithComplexRoots(_var) <--[ Local(coeffs,ijk); coeffs := {1,RandomInteger(-10,10),RandomInteger(1,10)}; coeffs[1] := Ceil(N(coeffs[2]^2/(4*coeffs[3]))) + RandomInteger(1,5); NormalForm(UniVariate(var,0,coeffs));];10 # NewRandomPoly( _deg, _var )_(Equal?(deg,3)) <--[ Local(ii,i1,i2,i3,p,CubicPoly); p := FillList(1,3); If( RandomInteger(3) =? 1, [ For(ii:=1,ii<=?3,ii++) [ i1 := RandomInteger(2); i2 := RandomInteger(-10,10); If( i1 >? 1, i2 := i1*i2 ); p[ii] := NormalForm(UniVariate(var,0,{i2,i1})); ]; ], [ i1 := RandomInteger(2); i2 := RandomInteger(-10,10); If( i1 >? 1, i2 := i1*i2 ); p[1] := NormalForm(UniVariate(var,0,{i2,i1})); p[2] := RandomIrreducibleQuadratic(var); ] ); CubicPoly := ExpandBrackets(Product(p));];10 # NewRandomPoly( _deg, _var )_(Equal?(deg,4)) <--[ Local(ii,i1,i2,i3,i4,p,QuarticPoly); p := FillList(1,4); If( RandomInteger(2) =? 1, [ p[1] := NewRandomPoly(3,x); i1 := RandomInteger(2); i2 := RandomInteger(-10,10); If( i1 >? 1, i2 := i1*i2 ); p[2] := NormalForm(UniVariate(var,0,{i2,i1})); ], [ p[1] := NewRandomPoly(2,x); p[2] := NewRandomPoly(2,x); ] ); QuarticPoly := ExpandBrackets(Product(p));];10 # NewRandomPoly( _deg, _var )_(Equal?(deg,5)) <--[ Local(ii,i1,i2,i3,i4,p,QuinticPoly); p := FillList(1,4); p[1] := NewRandomPoly(1,x); p[2] := RandomIrreducibleQuadraticWithRealRoots(x); p[3] := RandomIrreducibleQuadraticWithComplexRoots(x); QuinticPoly := ExpandBrackets(Product(p));];11 # NewRandomPoly( deg_PositiveInteger?, _var )_(deg >? 5) <--[ Local(p,n,m); p := {}; m := deg; Until( m <? 3 ) [ n := RandomInteger(2,Floor(N(deg/2))); Tell(\" \",{m,n}); Push(p,NewRandomPoly(n,var)); m := m - n; ]; Tell(\" \",m); If( m >? 0, Push(p,NewRandomPoly(m,x))); Expand(Product(p));];";
        scriptMap.put("RandomInterestingPolynomial",scriptString);
        scriptMap.put("NewRandomPoly",scriptString);
        scriptMap.put("RandomIrreducibleQuadratic",scriptString);
        scriptMap.put("RandomIrreducibleQuadraticWithRealRoots",scriptString);
        scriptMap.put("RandomIrreducibleQuadraticWithComplexRoots",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RandomPoly(_var,_degree,_coefmin,_coefmax) <-- NormalForm(UniVariate(var,0,RandomIntegerList(degree+1,coefmin,coefmax)));";
        scriptMap.put("RandomPoly",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(knownRNGEngines, knownRNGDists) [ knownRNGEngines := { { \"default\", \"RNGEngineLCG2\"}, { \"advanced\", \"RNGEngineLEcuyer\"}, }; knownRNGDists := { {\"default\", \"FlatRNGDist\"}, {\"flat\", \"FlatRNGDist\"},  {\"gauss\", \"GaussianRNGDist\"}, }; KnownRNGDists() := knownRNGDists; KnownRNGEngines() := knownRNGEngines;];Function() RngCreate();Function() RngCreate(seed, ...);Function() RngSeed(r, seed);Function() Rng(r);RngCreate() <-- RngCreate(0);10 # RngCreate(aseed_Integer?) <-- (RngCreate @ {seed -> aseed});20 # RngCreate(_key -> _value) <-- `(RngCreate({@key -> value}));30 # RngCreate(options_List?) <--[ options := OptionsListToHash @ {options};  If( options[\"seed\"] =? Empty Or? options[\"seed\"] <=? 0, options[\"seed\"] := 76544321  ); If( options[\"engine\"] =? Empty Or? Not? (Assert(\"warning\", {\"RngCreate: invalid engine\", options[\"engine\"]}) KnownRNGEngines()[options[\"engine\"] ] !=? Empty), options[\"engine\"] := \"default\" ); If( options[\"dist\"] =? Empty Or? Not? (Assert(\"warning\", {\"RngCreate: invalid distribution\", options[\"dist\"]}) KnownRNGDists()[options[\"dist\"] ] !=? Empty), options[\"dist\"] := \"default\" );   { KnownRNGDists()[options[\"dist\"] ], KnownRNGEngines()[options[\"engine\"] ],  KnownRNGEngines()[options[\"engine\"] ] @ { options[\"seed\"] } };];Rng(_r) <--[ Local(state, result); {state, result} := (r[1] @ {r});  DestructiveReplace(r, 3, state);  result; ];RngSeed(_r, seed_Integer?) <--[ Local(state); (Assert(\"warning\", {\"RngSeed: seed must be positive\", seed}) seed >? 0 ) Or? (seed:=76544321); state := (r[2] @ {seed});  DestructiveReplace(r, 3, state);  True;];FlatRNGDist(_r) <-- (r[2] @ {r[3]}); GaussianRNGDist(_rng) <--[    Local(a,b,m, newstate, rnumber); newstate := rng[3];  m:=0; While(m=?0 Or? m>=?1)  [ {newstate, rnumber} := (rng[2] @ {newstate}); a:=2*rnumber-1; {newstate, rnumber} := (rng[2] @ {newstate}); b:=2*rnumber-1; m:=a*a+b*b; ]; {newstate, (a+I*b)*SqrtN(-2*DivideN(InternalLnNum(m),m))};];RNGEngineLCG1(seed_Integer?) <-- {seed};RNGEngineLCG1(state_List?) <-- LCG1(state);RNGEngineLCG2(seed_Integer?) <-- {seed};RNGEngineLCG2(state_List?) <-- LCG2(state);RNGEngineLCG3(seed_Integer?) <-- {seed};RNGEngineLCG3(state_List?) <-- LCG3(state);RNGEngineLCG4(seed_Integer?) <-- {seed};RNGEngineLCG4(state_List?) <-- LCG4(state);LCG1(state) := RandomLCG(state, 2147483647,950706376,0);LCG2(state) := RandomLCG(state, 4294967296,1099087573,0);LCG3(state) := RandomLCG(state, 281474976710656,68909602460261,0);LCG4(state) := RandomLCG(state, 18014398509481984,2783377640906189,0);RandomLCG(_state, _im, _ia, _ic) <--{ DestructiveReplace(state,1, ModuloN(state[1]*ia+ic,im)), DivideN(state[1], im) };RNGEngineLEcuyer(aseed_Integer?) <--[  Local(rngaux, result); rngaux := (RngCreate @ {aseed});  result:=ZeroVector(6);  Local(i); For(i:=1, i<=?6, i++) [ Rng(rngaux); result[i] := rngaux[3][1];  ];  result;];RNGEngineLEcuyer(state_List?) <--[ Local(newstate, result); newstate := { Modulo(1403580*state[2]-810728*state[3], 4294967087), state[1], state[2], Modulo(527612*state[4]-1370589*state[6], 4294944433), state[4], state[5] }; result:=Modulo(state[1]-state[4], 4294967087); { newstate, DivideN(If(result=?0, 4294967087, result), 4294967088) };];LocalSymbols(RandSeed) [  RandSeed := SystemTimer();   Function(\"RandomSeed\", {seed}) Bind(RandSeed, seed);  RandomLCG(_im, _ia, _ic) <-- [ RandSeed:=ModuloN(RandSeed*ia+ic,im); DivideN(RandSeed,im);  ];]; Function(\"Random1\",{}) RandomLCG(4294967296,1103515245,12345);Function(\"Random6\",{}) RandomLCG(1771875,2416,374441);Function(\"Random2\",{}) RandomLCG(2147483647,950706376,0);Function(\"Random3\",{}) RandomLCG(4294967296,1099087573,0);Function(\"Random4\",{}) RandomLCG(281474976710656,68909602460261,0);Function(\"Random5\",{}) RandomLCG(18014398509481984,2783377640906189,0);Function(\"Random\",{}) Random3();";
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

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Combine(expr_Zero?) <-- 0;20 # Combine(_expr) <-- [ Local(L); L := ReassembleListTerms(DisassembleExpression(expr)); UnFlatten(L,\"+\",0);];";
        scriptMap.put("Combine",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Eliminate(_var,_replace,_function) <-- Simplify(Subst(var,replace)function);";
        scriptMap.put("Eliminate",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(AssembleTerms, AssembleTermsRecursive)[ AssembleTerms(list) := [ Check(List?(list), \"Argument\", \"The argument must be a list.\"); If(Length(list) =? 1, First(list), AssembleTermsRecursive(Reverse(list))  ); ]; AssembleTermsRecursive(list) := [ If(Type(list[1]) =? \"-\" Or? NegativeNumber?(list[1]) Or? Type(list[1]) =? \"/\" And? (Type(Numerator(list[1])) =? \"-\" Or? NegativeNumber?(Numerator(list[1]))), If(Length(list) =? 2, ListToFunction({ToAtom(\"-\"), list[2], -list[1]} ), ListToFunction({ToAtom(\"-\"), AssembleTermsRecursive(Rest(list)), -First(list)} ) ), If(Length(list) =? 2, ListToFunction({ToAtom(\"+\"), list[2], list[1]} ), ListToFunction({ToAtom(\"+\"), AssembleTermsRecursive(Rest(list)), First(list)} ) ) ); ];10 # ExpandBrackets(xx_Zero?) <-- 0;20 # ExpandBrackets(_xx)_(Type(xx)=?\"/\" Or? Type(-xx)=?\"/\") <--[ Local(N,D,t); N := ReassembleListTerms(DisassembleExpression(Numerator(xx))); D := ExpandBrackets(Denominator(xx)); AssembleTerms(MapSingle({{t}, t / D}, N));];30 # ExpandBrackets(_xx) <-- AssembleTerms(ReassembleListTerms(DisassembleExpression(xx)));];";
        scriptMap.put("ExpandBrackets",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # ExpandFrac(expr_List?) <-- MapSingle(\"ExpandFrac\", expr);10 # ExpandFrac(_expr)_Not?(HasFunctionSome?(expr, \"/\", {ToAtom(\"+\"), ToAtom(\"-\"), *, /, ^})) <-- expr;15 # ExpandFrac(a_RationalOrNumber?) <-- a;20 # ExpandFrac(_expr) <-- ExpandFraccombine(GetNumerDenom(expr));ExpandFraccombine({_a, _b}) <-- a/b;";
        scriptMap.put("ExpandFrac",scriptString);
        scriptMap.put("ExpandFraccombine",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"DoFlatten\",{doflattenx});UnFence(\"DoFlatten\",1);10 # DoFlatten(_doflattenx)_(Type(doflattenx)=?flattenoper) <-- Apply(\"Concat\",MapSingle(\"DoFlatten\",Rest(FunctionToList(doflattenx))));20 # DoFlatten(_doflattenx) <-- { doflattenx };Function(\"Flatten\",{body,flattenoper})[ DoFlatten(body);];";
        scriptMap.put("Flatten",scriptString);
        scriptMap.put("DoFlatten",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "GetNumerDenom(_expr, _a) <-- GetNumerDenom(expr)*{a,1};10 # GetNumerDenom(_expr)_Not?(HasFunctionSome?(expr, \"/\", {ToAtom(\"+\"), ToAtom(\"-\"), *, /, ^})) <-- {expr, 1};15 # GetNumerDenom(a_RationalOrNumber?) <-- {a, 1};20 # GetNumerDenom(_a + _b) <-- ExpandFracadd(GetNumerDenom(a), GetNumerDenom(b));20 # GetNumerDenom(_a - _b) <-- ExpandFracadd(GetNumerDenom(a), GetNumerDenom(b, -1));20 # GetNumerDenom(- _a) <-- GetNumerDenom(a, -1);20 # GetNumerDenom(+ _a) <-- GetNumerDenom(a);20 # GetNumerDenom(_a * _b) <-- ExpandFracmultiply(GetNumerDenom(a), GetNumerDenom(b));20 # GetNumerDenom(_a / _b) <-- ExpandFracdivide(GetNumerDenom(a), GetNumerDenom(b));20 # GetNumerDenom(_a ^ b_Integer?)_(b >? 1) <-- ExpandFracmultiply(GetNumerDenom(a), GetNumerDenom(a^(b-1)));20 # GetNumerDenom(_a ^ b_Integer?)_(b <? -1) <-- ExpandFracdivide(GetNumerDenom(1), GetNumerDenom(a^(-b)));20 # GetNumerDenom(_a ^ b_Integer?)_(b =? -1) <-- ExpandFracdivide(GetNumerDenom(1), GetNumerDenom(a));25 # GetNumerDenom(_a ^ _b) <-- {a^b, 1};ExpandFracadd({_a, _b}, {_c, _d}) <-- {a*d+b*c, b*d};ExpandFracmultiply({_a, _b}, {_c, _d}) <-- {a*c, b*d};ExpandFracdivide({_a, _b}, {_c, _d}) <-- {a*d, b*c};";
        scriptMap.put("GetNumerDenom",scriptString);
        scriptMap.put("ExpandFracadd",scriptString);
        scriptMap.put("ExpandFracmultiply",scriptString);
        scriptMap.put("ExpandFracdivide",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"SimpAdd\",{x,y});";
        scriptMap.put("SimpAdd",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"SimpDiv\",{x,y});";
        scriptMap.put("SimpDiv",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # SimpExpand(SimpAdd(_x,_y)) <-- SimpExpand(x) + SimpExpand(y);10 # SimpExpand(SimpMul(_x,_y)) <-- SimpExpand(x) * SimpExpand(y);10 # SimpExpand(SimpDiv(_x,_y)) <-- SimpExpand(x) / SimpExpand(y);20 # SimpExpand(_x) <-- x;";
        scriptMap.put("SimpExpand",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # SimpFlatten((_x)+(_y)) <-- SimpAdd(SimpFlatten(x),SimpFlatten(y));10 # SimpFlatten((_x)-(_y)) <-- SimpAdd(SimpFlatten(x),SimpMul(-1,SimpFlatten(y)));10 # SimpFlatten( -(_y)) <-- SimpMul(-1,SimpFlatten(y));10 # SimpFlatten((_x)*(_y)) <-- SimpMul(SimpFlatten(x),SimpFlatten(y));10 # SimpFlatten((_x)/(_y)) <-- SimpDiv(SimpFlatten(x),SimpFlatten(y));10 # SimpFlatten((_x)^(n_PositiveInteger?)) <-- SimpMul(SimpFlatten(x),SimpFlatten(x^(n-1)));100 # SimpFlatten(_x) <--[ x;];";
        scriptMap.put("SimpFlatten",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # SimpImplode(SimpMul(SimpAdd(_x,_y),_z)) <-- SimpImplode(SimpAdd(SimpImplode(SimpMul(x,z)), SimpImplode(SimpMul(y,z))));10 # SimpImplode(SimpMul(_z,SimpAdd(_x,_y))) <-- SimpImplode(SimpAdd(SimpImplode(SimpMul(z,x)), SimpImplode(SimpMul(z,y))));10 # SimpImplode(SimpDiv(SimpAdd(_x,_y),_z)) <-- SimpImplode(SimpAdd(SimpImplode(SimpDiv(x,z)), SimpImplode(SimpDiv(y,z))));20 # SimpImplode(SimpAdd(_x,_y)) <-- SimpAdd(SimpImplode(x),SimpImplode(y));20 # SimpImplode(SimpMul(_x,_y)) <-- SimpMul(SimpImplode(x),SimpImplode(y));20 # SimpImplode(SimpDiv(_x,_y)) <-- SimpDiv(SimpImplode(x),SimpImplode(y));30 # SimpImplode(_x) <-- x;";
        scriptMap.put("SimpImplode",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"SimpMul\",{x,y});";
        scriptMap.put("SimpMul",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Simplify(expr_List?) <-- MapSingle(\"Simplify\",expr);15 # Simplify(Complex(_r,_i)) <-- Complex(Simplify(r),Simplify(i));20 # Simplify((_xex) == (_yex)) <-- (Simplify(xex-yex) == 0);20 # Simplify((_xex) >? (_yex)) <-- (Simplify(xex-yex) >? 0);20 # Simplify((_xex) <? (_yex)) <-- (Simplify(xex-yex) <? 0);20 # Simplify((_xex) >=? (_yex)) <-- (Simplify(xex-yex) >=? 0);20 # Simplify((_xex) <=? (_yex)) <-- (Simplify(xex-yex) <=? 0);20 # Simplify((_xex) !== (_yex)) <-- (Simplify(xex-yex) !== 0);25 # Simplify(if (_a) _b) <-- \"if\" @ {Simplify(a), Simplify(b)};25 # Simplify(_a else _b) <-- \"else\" @ {Simplify(a), Simplify(b)};40 # Simplify(_expr)_(Type(expr)=?\"Ln\") <--[  LnCombine(expr);];40 # Simplify(_expr)_(Type(expr)=?\"Exp\") <--[  expr;];50 # Simplify(_expr) <-- [  MultiSimp(Eval(expr));];";
        scriptMap.put("Simplify",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # UnFlatten({},_op,_identity) <-- identity;20 # UnFlatten(list_List?,_op,_identity) <-- Apply(op,{First(list),UnFlatten(Rest(list),op,identity)});";
        scriptMap.put("UnFlatten",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "FactorialNormalForm(x):=[  x:=(x/:{BinomialCoefficient(_n,_m)<- (n!)/((m!)*(n-m)!)});   x:=( x/::Hold({ (_a/_b)/_c <- (a)/(b*c), (-(_a/_b))/_c <- (-a)/(b*c), (_a/_b)*_c <- (a*c)/b, (_a*_b)^_m <- a^m*b^m, (_a/_b)^_m*_c <- (a^m*c)/b^m, _a*(_b+_c) <- a*b+a*c, (_b+_c)*_a <- a*b+a*c, (_b+_c)/_a <- b/a+c/a, _a*(_b-_c) <- a*b-a*c, (_b-_c)*_a <- a*b-a*c, (_b-_c)/_a <- b/a-c/a })); x;];FactorialSimplify(x):=[ x := FactorialNormalForm(x); FactorialSimplifyWorker(x);];10 # CommonDivisors(_x^(_n),_x^(_m)) <-- {x^Simplify(n-m),1,x^m};10 # CommonDivisors(_x^(_n),_x) <-- {x^Simplify(n-1),1,x};10 # CommonDivisors(_x,_x^(_m)) <-- {x^Simplify(1-m),1,x^m};10 # CommonDivisors((_x) !,_x) <-- {(x-1)!,1,x};10 # CommonDivisors(_x,_x) <-- {1,1,x};10 # CommonDivisors(- _x,_x) <-- {-1,1,x};10 # CommonDivisors(_x,- _x) <-- {1,-1,x};10 # CommonDivisors((_x),(_x)!) <-- {1,(x-1)!,x};10 # CommonDivisors((_x)!, (_y)!)_Integer?(Simplify(x-y)) <-- CommonFact(Simplify(x-y),y);10 # CommonDivisors((_x)! ^ _m, (_y)! ^ _m)_Integer?(Simplify(x-y)) <-- CommonFact(Simplify(x-y),y)^m;10 # CommonFact(dist_NegativeInteger?,_y) <-- {1,Product(i,1,-dist,Simplify(y+i+dist)),Simplify(y+dist)!};11 # CommonFact(_dist,_y) <-- {Product(i,1,dist,Simplify(y+i)),1,Simplify(y)!};60000 # CommonDivisors(_x,_y) <-- {x,y,1};10 # CommonFactors((_x)!,_y)_(Simplify(y-x) =? 1) <-- {y!,1};10 # CommonFactors((_x)!,_y)_(Simplify((-y)-x) =? 1) <-- {(-y)!,-1};10 # CommonFactors(_x^_n,_x^_m) <-- {x^Simplify(n+m),1};10 # CommonFactors(_x^_n,_x) <-- {x^Simplify(n+1),1};60000 # CommonFactors(_x,_y) <-- {x,y};10 # FactorialSimplifyWorker(_x+_y) <-- FactorialSimplifyWorker(x)+FactorialSimplifyWorker(y);10 # FactorialSimplifyWorker(_x-_y) <-- FactorialSimplifyWorker(x)-FactorialSimplifyWorker(y);10 # FactorialSimplifyWorker( -_y) <-- -FactorialSimplifyWorker(y);LocalSymbols(x,y,i,j,n,d)[20 # FactorialSimplifyWorker(_x/_y) <--[  Local(numerCommon,numerTerms); {numerCommon,numerTerms}:=FactorialGroupCommonDivisors(x); Local(denomCommon,denomTerms); {denomCommon,denomTerms}:=FactorialGroupCommonDivisors(y); Local(n,d,c); {n,d,c} := FactorialDivideTerms(numerCommon,denomCommon); (n/d)*Simplify((numerTerms)/(denomTerms));];20 # FactorialGcd(_x,_y) <--[  Local(numerCommon,numerTerms); {numerCommon,numerTerms}:=FactorialGroupCommonDivisors(x); Local(denomCommon,denomTerms); {denomCommon,denomTerms}:=FactorialGroupCommonDivisors(y); Local(n,d,c); {n,d,c} := FactorialDivideTerms(numerCommon,denomCommon); c;];10 # FactorialDivideTerms(- _x,- _y) <-- FactorialDivideTermsAux(x,y);LocalSymbols(n,d,c)[ 20 # FactorialDivideTerms(- _x, _y) <-- [ Local(n,d,c); {n,d,c} := FactorialDivideTermsAux(x,y); {-n,d,c}; ]; 30 # FactorialDivideTerms( _x,- _y) <-- [ Local(n,d,c); {n,d,c} := FactorialDivideTermsAux(x,y); {n,-d,c}; ];];40 # FactorialDivideTerms( _x, _y) <-- [ FactorialDivideTermsAux(x,y); ];LocalSymbols(n,d,c)[ 10 # FactorialDivideTermsAux(_x,_y) <-- [ x:=Flatten(x,\"*\"); y:=Flatten(y,\"*\"); Local(i,j,common); common:=1; For(i:=1,i<=?Length(x),i++) For(j:=1,j<=?Length(y),j++) [ Local(n,d,c); {n,d,c} := CommonDivisors(x[i],y[j]); x[i] := n; y[j] := d; common:=common*c; ]; {Product(x),Product(y),common}; ];];];60000 # FactorialSimplifyWorker(_x) <-- [  Local(numerCommon,numerTerms); {numerCommon,numerTerms}:=FactorialGroupCommonDivisors(x); numerCommon*numerTerms; ];10 # FactorialFlattenAddition(_x+_y) <-- Concat(FactorialFlattenAddition(x), FactorialFlattenAddition(y));10 # FactorialFlattenAddition(_x-_y) <-- Concat(FactorialFlattenAddition(x),-FactorialFlattenAddition(y));10 # FactorialFlattenAddition( -_y) <-- -FactorialFlattenAddition(y);20 # FactorialFlattenAddition(_x ) <-- {x};LocalSymbols(n,d,c)[ 10 # FactorialGroupCommonDivisors(_x) <-- [ Local(terms,common,tail); terms:=FactorialFlattenAddition(x); common := First(terms); tail:=Rest(terms); While (tail !=? {}) [ Local(n,d,c); {n,d,c} := FactorialDivideTerms(common,First(tail)); common := c; tail:=Rest(tail); ]; Local(i,j); For(j:=1,j<=?Length(terms),j++) [ Local(n,d,c); {n,d,c} := FactorialDivideTerms(terms[j],common); Check(d =? 1, \"Math\", PipeToString()[ Echo(\"FactorialGroupCommonDivisors failure 1 : \",d); ]); terms[j] := n; ]; terms:=Add(terms); common:=Flatten(common,\"*\"); For(j:=1,j<=?Length(common),j++) [ Local(f1,f2); {f1,f2}:=CommonFactors(common[j],terms); common[j]:=f1; terms:=f2; For(i:=1,i<=?Length(common),i++) If(i !=? j, [ {f1,f2}:=CommonFactors(common[j],common[i]); common[j]:=f1; common[i]:=f2; ]); ]; common := Product(common); {common,terms}; ];];";
        scriptMap.put("FactorialSimplify",scriptString);
        scriptMap.put("CommonDivisors",scriptString);
        scriptMap.put("CommonFact",scriptString);
        scriptMap.put("CommonFactors",scriptString);
        scriptMap.put("FactorialSimplifyWorker",scriptString);
        scriptMap.put("FactorialFlattenAddition",scriptString);
        scriptMap.put("FactorialGcd",scriptString);
        scriptMap.put("FactorialDivideTerms",scriptString);
        scriptMap.put("FactorialDivideTermsAux",scriptString);
        scriptMap.put("FactorialGroupCommonDivisors",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(assumptions) [assumptions := {};10 # Assume( L_List? ) <--[ If(InVerboseMode(),Tell(\"AssumeLst\",L)); Local(len,s); len := Length(L); If( len >? 0, ForEach(s,L) [ Assume(s); ] ); assumptions;];10 # Assume( _x -> _y ) <--[  If(InVerboseMode(),Tell(\"AssumeItem\",{x,y})); Local(key,value); key := Hold(x);  value := Hold(y); If(InVerboseMode(),Tell(\" \",{key,value}));  DestructiveAppend(assumptions,{Eval(key),Eval(value)}); assumptions;];10 # AssumptionsGet() <-- assumptions;10 # AssumptionsAbout(_key) <--[ Local(props); props := Select(assumptions,Lambda({X},X[1]=?key)); If( Length(props) >? 0, Transpose(props)[2], {} );];10 # Assumed?( _key, _valueExpected ) <-- Contains?(AssumptionsAbout(key),valueExpected);10 # UnAssume( _x )_(Contains?(AssocIndices(assumptions),x)) <--[ Local(lst,len,jj); lst := Lambda({X},If(List?(X),X[1])) /@ assumptions; jj := Find(lst,x); If( jj >? 0, DestructiveDelete(assumptions,jj) ); lst := Lambda({X},If(List?(X),X[1])) /@ assumptions; jj := Find(lst,x); If( jj >? 0, UnAssume(x), True );];]; ";
        scriptMap.put("Assume",scriptString);
        scriptMap.put("AssumptionsGet",scriptString);
        scriptMap.put("AssumptionsAbout",scriptString);
        scriptMap.put("Assumed?",scriptString);
        scriptMap.put("UnAssume",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # CheckSolution( _expr, _var, solution_List? )_(Not? FreeOf?(var,expr)) <-- [ Local(expr0,result,s,r); If( Equation?(expr), Bind(expr0,EquationLeft(expr)-EquationRight(expr)), Bind(expr0,expr) ); result := {}; ForEach(s,solution)  [ r := ( expr0 Where s ); If(r=?0,Push(result,s)); ]; Reverse(result); ];  20 # CheckSolution( _expr, _var, _solution ) <-- False;";
        scriptMap.put("CheckSolution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Newton\",{function,variable,initial,accuracy})[  `Newton(@function,@variable,initial,accuracy,-Infinity,Infinity);];Function(\"Newton\",{function,variable,initial,accuracy,min,max})[ Local(result,adjust,delta,requiredPrec); MacroLocal(variable); requiredPrec := BuiltinPrecisionGet(); accuracy:=N((accuracy/10)*10);  BuiltinPrecisionSet(requiredPrec+2); function:=N(function); adjust:= -function/Apply(\"Differentiate\",{variable,function}); delta:=10000; result:=initial; While (result >? min And? result <? max  And? N(Eval( Maximum(Re(delta), -Re(delta), Im(delta), -Im(delta)) ) ) >? accuracy) [ MacroBind(variable,result); delta:=N(Eval(adjust)); result:=result+delta; ]; BuiltinPrecisionSet(requiredPrec); result:=N(Eval((result/10)*10));  if (result <=? min Or? result >=? max) [result := Fail;]; result;];";
        scriptMap.put("Newton",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SolveSystem(_eqns, _vars) <-- SolveSimpleBackSubstitution(eqns,vars);10 # SolveSimpleBackSubstitutionFindAlternativeForms((_lx) == (_rx)) <--[ Local(newEq); newEq := (Simplify(lx) == Simplify(rx)); If (newEq !=? (lx == rx) And? newEq !=? (0==0),DestructiveAppend(eq,newEq)); newEq := (Simplify(lx - rx) == 0); If (newEq !=? (lx == rx) And? newEq !=? (0==0),DestructiveAppend(eq,newEq));];20 # SolveSimpleBackSubstitutionFindAlternativeForms(_equation) <--[];UnFence(\"SolveSimpleBackSubstitutionFindAlternativeForms\",1);10 # SolveSimpleBackSubstitution(eq_List?,var_List?) <--[ If(InVerboseMode(), Echo({\"Entering SolveSimpleBackSubstitution\"})); Local(result,i,j,nrvar,nreq,sub,nrSet,origEq); eq:=FlatCopy(eq); origEq:=FlatCopy(eq); nrvar:=Length(var); result:={FlatCopy(var)}; nrSet := 0; ForEach(equation,origEq) [ SolveSimpleBackSubstitutionFindAlternativeForms(equation); ]; nreq:=Length(eq);  For(j:=1,j<=?nreq And? nrSet <? nrvar,j++) [ Local(vlist); vlist:=VarListAll(eq[j],`Lambda({pt},Contains?(@var,pt))); For(i:=1,i<=?nrvar And? nrSet <? nrvar,i++) [ If(Count(vlist,var[i]) =? 1, [ sub := FunctionToList(eq[j]); sub := sub[2]-sub[3]; sub:=SuchThat(sub,var[i]); If(InVerboseMode(), Echo({\"From \",eq[j],\" it follows that \",var[i],\" = \",sub})); If(SolveFullSimplify=?True, result:=Simplify(Subst(var[i],sub)result), result[1][i]:=sub ); nrSet++; Local(k,reset); reset:=False; For(k:=1,k<=?nreq And? nrSet <? nrvar,k++) If(Contains?(VarListAll(eq[k],`Lambda({pt},Contains?(@var,pt))),var[i]), [ Local(original); original:=eq[k]; eq[k]:=Subst(var[i],sub)eq[k]; If(Simplify(Simplify(eq[k])) =? (0 == 0), eq[k] := (0 == 0), SolveSimpleBackSubstitutionFindAlternativeForms(eq[k]) ); If(original!=?(0==0) And? eq[k] =? (0 == 0),reset:=True); If(InVerboseMode(), Echo({\" \",original,\" simplifies to \",eq[k]})); ]); nreq:=Length(eq); vlist:=VarListAll(eq[j],`Lambda({pt},Contains?(@var,pt))); i:=nrvar+1;  If(reset,j:=1); ]); ]; ]; Local(zeroeq,tested); tested:={}; ForEach(item,result) [ DestructiveAppend(tested,Map(\"==\",{var,item})); ]; If(InVerboseMode(), Echo({\"Leaving SolveSimpleBackSubstitution\"})); tested;];10 # OldSolve(eq_List?,var_List?) <-- SolveSimpleBackSubstitution(eq,var);90 # OldSolve((left_List?) == right_List?,_var) <-- OldSolve(Map(\"==\",{left,right}),var);100 # OldSolve(_left == _right,_var) <-- SuchThat(left - right , 0 , var);";
        scriptMap.put("OldSolve",scriptString);
        scriptMap.put("SolveSystem",scriptString);
        scriptMap.put("SolveSimpleBackSubstitutionFindAlternativeForms",scriptString);
        scriptMap.put("SolveSimpleBackSubstitution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "PSolve( _uni ) <-- YacasPSolve( uni );PSolve( _uni, _var ) <-- YacasPSolve( uni, var );";
        scriptMap.put("PSolve",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"SolveMatrix\",{matrix,vector})[ If(InVerboseMode(),Tell(\" SolveMatrix\",{matrix,vector})); Local(perms,indices,inv,det,n); n:=Length(matrix); indices:=Table(i,i,1,n,1); perms:=PermutationsList(indices); inv:=ZeroVector(n); det:=0; ForEach(item,perms) [ Local(i,lc); lc := LeviCivita(item); det:=det+Product(i,1,n,matrix[i][item[i] ])* lc; For(i:=1,i<=?n,i++) [ inv[i] := inv[i]+ Product(j,1,n, If(item[j] =? i,vector[j ],matrix[j][item[j] ]))*lc; ]; ]; Check(det !=? 0, \"Math\", \"Zero determinant\"); (1/det)*inv;];";
        scriptMap.put("SolveMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # SolveSystem( eqns_List?, vars_List? )_(Length(eqns)=?1 And? Length(vars)=?1) <--[ {Solve(eqns[1],vars[1])};];12 # SolveSystem( eqns_List?, vars_List? ) <--[  If(InVerboseMode(),Tell(\"SolveSystem\",{eqns,vars}));  Local(eq,expr,exprns,VaD,isLinearSet,ans);  exprns := {}; ForEach(eq,eqns) [ expr := If( Equation?(eq), EquationLeft(eq)-EquationRight(eq), eq ); DestructiveAppend(exprns,expr); ]; If(InVerboseMode(),Tell(\" \",exprns));   VaD := VarsAndDegs(exprns,vars); If(InVerboseMode(),Tell(\" \",VaD)); isLinearSet := Maximum(Flatten(VaD,\"List\"))=?1; If(InVerboseMode(),Tell(\" \",isLinearSet));  If( isLinearSet, ans:=SolveLinearSystem( exprns, vars ), ans:=SolveNonlinearSystem( exprns, vars ) ); If(ans !=? {}, ans := Map(\"==\",{vars,ans})); If(InVerboseMode(),Tell(\"\",ans)); ans;];10 # VarsAndDegs(exs_List?,vars_List?) <--[ Local(ex,v,result); result := {}; ForEach(ex,exs) [ Local(res); res := {}; ForEach(v,vars) [ DestructiveAppend(res,Apply(\"Degree\",{ex,v})); ]; DestructiveAppend(result,res); ]; result;];10 # SolveLinearSystem( polys_List?, vars_List? ) <--[ Local(A, E); If(InVerboseMode(),Tell(\" SolveLinearSystem\",{polys,vars}));  Local(lhs,rhs,zeros); lhs := MakeCoefMatrix(polys,vars); If(InVerboseMode(),Tell(\" \",lhs)); zeros := ZeroVector(Length(vars)); rhs := -WithValue(vars,zeros,polys); If(InVerboseMode(),Tell(\" \",rhs));  A := Transpose(Concat(Transpose(lhs),{rhs})); E := RREF(A); If(Contains?(E,BaseVector(Dimensions(E)[2],Dimensions(E)[2])), {}, MatrixColumn(E,Dimensions(E)[2]) - (ExtractSubMatrix(E, 1, 1, Length(E), Length(E)) - Identity(Length(E))) * vars);];10 # MakeCoefMatrix(polys_List?,vars_List?) <--[ If(InVerboseMode(),Tell(\" MakeCoefMatrix\",{polys,vars})); Local(p,v,result); result := {}; ForEach(p,polys) [ Local(res); res := {}; ForEach(v,vars) [ DestructiveAppend(res,Apply(\"Coef\",{p,v,1})); ]; DestructiveAppend(result,res); ]; result;];";
        scriptMap.put("SolveSystem",scriptString);
        scriptMap.put("VarsAndDegs",scriptString);
        scriptMap.put("SolveLinearSystem",scriptString);
        scriptMap.put("MakeCoefMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ContainsExpression(_body,_body) <-- True;15 # ContainsExpression(body_Atom?,_expr) <-- False;20 # ContainsExpression(body_Function?,_expr) <--[ Local(result,args); result:=False; args:=Rest(FunctionToList(body)); While(args !=? {}) [ result:=ContainsExpression(First(args),expr); args:=Rest(args); if (result =? True) (args:={}); ]; result;];SuchThat(_function,_var) <-- SuchThat(function,0,var);10 # SuchThat(_left,_right,_var)_(left =? var) <-- right;20 # SuchThat(left_Atom?,_right,_var) <-- var;30 # SuchThat((_x) + (_y),_right,_var)_ContainsExpression(x,var) <-- SuchThat(x , right-y , var);30 # SuchThat((_y) + (_x),_right,_var)_ContainsExpression(x,var) <-- SuchThat(x , right-y , var);30 # SuchThat(Complex(_r,_i),_right,_var)_ContainsExpression(r,var) <-- SuchThat(r , right-I*i , var);30 # SuchThat(Complex(_r,_i),_right,_var)_ContainsExpression(i,var) <-- SuchThat(i , right+I*r , var);30 # SuchThat(_x * _y,_right,_var)_ContainsExpression(x,var) <-- SuchThat(x , right/y , var);30 # SuchThat(_y * _x,_right,_var)_ContainsExpression(x,var) <-- SuchThat(x , right/y , var);30 # SuchThat(_x ^ _y,_right,_var)_ContainsExpression(x,var) <-- SuchThat(x , right^(1/y) , var);30 # SuchThat(_x ^ _y,_right,_var)_ContainsExpression(y,var) <-- SuchThat(y , Ln(right)/Ln(x) , var);30 # SuchThat(Sin(_x),_right,_var) <-- SuchThat(x , ArcSin(right) , var);30 # SuchThat(ArcSin(_x),_right,_var) <-- SuchThat(x , Sin(right) , var);30 # SuchThat(Cos(_x),_right,_var) <-- SuchThat(x , ArcCos(right) , var);30 # SuchThat(ArcCos(_x),_right,_var) <-- SuchThat(x , Cos(right) , var);30 # SuchThat(Tan(_x),_right,_var) <-- SuchThat(x , ArcTan(right) , var);30 # SuchThat(ArcTan(_x),_right,_var) <-- SuchThat(x , Tan(right) , var);30 # SuchThat(Exp(_x),_right,_var) <-- SuchThat(x , Ln(right) , var);30 # SuchThat(Ln(_x),_right,_var) <-- SuchThat(x , Exp(right) , var);30 # SuchThat(_x / _y,_right,_var)_ContainsExpression(x,var) <-- SuchThat(x , right*y , var);30 # SuchThat(_y / _x,_right,_var)_ContainsExpression(x,var) <-- SuchThat(x , y/right , var);30 # SuchThat(- (_x),_right,_var) <-- SuchThat(x , -right , var);30 # SuchThat((_x) - (_y),_right,_var)_ContainsExpression(x,var) <-- SuchThat(x , right+y , var);30 # SuchThat((_y) - (_x),_right,_var)_ContainsExpression(x,var) <-- SuchThat(x , y-right , var);30 # SuchThat(Sqrt(_x),_right,_var) <-- SuchThat(x , right^2 , var);";
        scriptMap.put("SuchThat",scriptString);
        scriptMap.put("ContainsExpression",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"YacasPSolve\",{uni});RuleHoldArguments(\"YacasPSolve\",1,1,UniVar?(uni) And? Degree(uni) =? 0) {};RuleHoldArguments(\"YacasPSolve\",1,1,UniVar?(uni) And? Degree(uni) =? 1) -Coef(uni,0)/Coef(uni,1);RuleHoldArguments(\"YacasPSolve\",1,1,UniVar?(uni) And? Degree(uni) =? 2) [ Local(a,b,c,d,q,r); c:=Coef(uni,0); b:=Coef(uni,1); a:=Coef(uni,2); d:=b*b-4*a*c; q:=Sqrt(d)/(2*a);     r:=-b/(2*a); If(InVerboseMode(),[ Tell(\" \",{c,b,a,d}); Tell(\" \",{q,r}); ]); {r+q,r-q}; ];RuleHoldArguments(\"YacasPSolve\",1,1,UniVar?(uni) And? Degree(uni) =? 3 ) [ Local(p,q,r,w,ww,a,b); Local(coef0,coef1,coef3,adjust); adjust := (-Coef(uni,2))/(3*Coef(uni,3)); coef3 := Coef(uni,3); coef1 := 2*Coef(uni,2)*adjust+3*Coef(uni,3)*adjust^2+Coef(uni,1); coef0 := Coef(uni,2)*adjust^2+Coef(uni,3)*adjust^3+ adjust*Coef(uni,1)+Coef(uni,0); p:=coef3; q:=coef1/p; r:=coef0/p; w:=Complex(-1/2,Sqrt(3/4)); ww:=Complex(-1/2,-Sqrt(3/4)); a:=(-r/2 + Sqrt(q^3/27+ r*r/4))^(1/3); b:=(-r/2 - Sqrt(q^3/27+ r*r/4))^(1/3); {a+b+adjust,w*a+ww*b+adjust,ww*a+w*b+adjust};];RuleHoldArguments(\"YacasPSolve\",1,1,UniVar?(uni) And? Degree(uni) =? 4 )[ Local(coef4,a1,a2,a3,a4,y,y1,z,t,s); coef4:=Coef(uni,4); a1:=Coef(uni,3)/coef4; a2:=Coef(uni,2)/coef4; a3:=Coef(uni,1)/coef4; a4:=Coef(uni,0)/coef4;  y1:=First(YacasPSolve(y^3-a2*y^2+(a1*a3-4*a4)*y+(4*a2*a4-a3^2-a1^2*a4),y)); t := Sqrt(y1^2/4-a4); If(t=?0, s:=Sqrt(y1+a1^2/4-a2), s:=(a1*y1-2*a3)/(4*t)); Concat(YacasPSolve(z^2+(a1/2+s)*z+y1/2+t,z), YacasPSolve(z^2+(a1/2-s)*z+y1/2-t,z));];Function(\"YacasPSolve\",{uni,var}) [ Local(u, factors, f, r, s); u := MakeUni(uni, var); If(Type(u) =? \"UniVariate\" And? (And? @ (Lambda({x}, Number?(x) Or? Rational?(x)) /@ u[3])), [ Local(coeffs); coeffs := Rationalize(u[3]); coeffs := If(Length(coeffs) >? 1,  Lcm(Denominator /@ coeffs) * coeffs,  (Denominator /@ coeffs) * coeffs); DestructiveReplace(u, 3, coeffs); factors := If(Degree(u)>?0,  Factors(NormalForm(u)),  {NormalForm(u), 1}); ], [ factors := {{uni, 1}}; ]); r := {}; ForEach(f, factors) [ s := YacasPSolve(MakeUni(f[1],var)); r := Union(r, If(List?(s), s, {s})); ]; If(Length(r) =? 1, r[1], r);];";
        scriptMap.put("YacasPSolve",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # jSolveUniPoly( _lhs==_rhs, var_Atom? ) <--[ jSolveUniPoly(lhs-rhs,var);];15 # jSolveUniPoly( poly_Polynomial?, var_Atom? )_(Length(VarList(poly))=?1) <--[ If(InVerboseMode(),Tell(jSolveUniPoly,{poly,var})); Local(factorList,f,fac,mult,ii,answer); factorList := Factors(poly); If(InVerboseMode(),Tell(\" \",factorList)); answer := {}; ForEach(f,factorList) [ {fac,mult} := f; soln := Solve(fac,var); If(InVerboseMode(),[Tell(\" \",{fac,mult});Tell(\" \",soln);]); ForEach(ii,1 .. mult) [ DestructiveAppend(answer,soln); ]; ]; answer;];20 # jSolveUniPoly( poly_Polynomial?, var_Atom? ) <-- Failed;";
        scriptMap.put("jSolveUniPoly",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(res)[ 10 # Solve(expr_List?, var_List?) <-- SolveSystem(expr, var); 12 # Solve(_expr, var_List?)_(Length(var)=?1) <-- [ {Solve(expr,var[1])}; ];  20 # Solve(_expr, _var)_(Number?(var) Or? String?(var)) <-- [ Assert(\"SolveTypeError\", \"Second argument, \"~(PipeToString() Write(var))~\", is not the name of a variable\") False; {}; ]; 22 # Solve(_expr, _var)_(Not? Atom?(var) And? Not? HasExpression?(expr,var)) <-- [ Assert(\"SolveTypeError\", \"Second argument, \"~(PipeToString() Write(var))~\", is not the name of a variable\") False; {}; ]; 24 # Solve(False,_var) <-- Check(False, \"Argument\", \"Bad input: possibly '=' instead of '==' \"); 30 # Solve(_lhs == _rhs, _var) <-- Solve(lhs - rhs, var); 40 # Solve(0, _var) <-- {var == var}; 41 # Solve(a_Constant?, _var) <-- {}; 42 # Solve(_expr, _var)_(Not? HasExpression?(expr,var)) <-- [ Assert(\"Solve\", \"expression \"~(PipeToString() Write(expr))~\" does not depend on \"~PipeToString() Write(var)) False; {}; ]; 50 # Solve(_expr, _var)_((res := SolvePoly(expr, var)) !=? Failed) <-- res; 60 # Solve(_e1 * _e2, _var) <-- [ Local(t,u,s); t := Union(Solve(e1,var), Solve(e2,var)); u := {}; ForEach(s, t) [ Local(v1,v2); v1 := WithValue(var, s[2], e1); v2 := WithValue(var, s[2], e2); If(Not? (Infinity?(v1) Or? (v1 =? Undefined) Or? Infinity?(v2) Or? (v2 =? Undefined)), DestructiveAppend(u, s)); ]; u; ]; 70 # Solve(_e1 / _e2, _var) <-- [ Local(tn, t, s); tn := Solve(e1, var); t := {}; ForEach(s, tn) If(Not?(Zero?(WithValue(var, s[2], e2))),  DestructiveAppend(t, s) ); t; ]; 80 # Solve(_e1 + _e2, _var)_(Not? HasExpression?(e2,var) And? (res := SolveSimple(e1,-e2,var)) !=? Failed) <-- res; 80 # Solve(_e1 + _e2, _var)_(Not? HasExpression?(e1,var) And? (res := SolveSimple(e2,-e1,var)) !=? Failed) <-- res; 80 # Solve(_e1 - _e2, _var)_(Not? HasExpression?(e2,var) And? (res := SolveSimple(e1,e2,var)) !=? Failed) <-- res; 80 # Solve(_e1 - _e2, _var)_(Not? HasExpression?(e1,var) And? (res := SolveSimple(e2,e1,var)) !=? Failed) <-- res; 85 # Solve(_expr, _var)_((res := SolveSimple(expr, 0, var)) !=? Failed) <-- res; 90 # Solve(_expr, _var)_((res := SolveReduce(expr, var)) !=? Failed) <-- res; 95 # Solve(_expr, _var)_((res := SolveDivide(expr, var)) !=? Failed) <-- res; 100 # Solve(_expr, _var)_((res := Simplify(expr)) !=? expr) <-- Solve(res, var); 110 # Solve(_expr, _var) <-- [ Assert(\"SolveFails\", \"cannot solve equation \"~(PipeToString() Write(expr))~\" for \"~PipeToString() Write(var)) False; {}; ];];10 # SolvePoly(_expr, _var)_(Not? CanBeUni(var, expr)) <-- Failed;20 # SolvePoly(_expr, _var) <--LocalSymbols(x)[ Local(roots); roots := PSolve(expr, var); If(Type(roots) =? \"YacasPSolve\", If(roots =? YacasPSolve(0), {var == var}, Failed),  If(Type(roots) =? \"List\", MapSingle({{x},var==x}, roots),  {var == roots})); ];10 # SolveReduce(_expr, _var) <--[ ClearError(\"SolveFails\");  Local(context, expr2, var2, res, sol, sol2, i); context := SolveContext(expr, var); If(context =? False, res := Failed, [ expr2 := Eval(Subst(context, var2) expr); If(CanBeUni(var2, expr2) And? (Degree(expr2, var2) =? 0 Or? (Degree(expr2, var2) =? 1 And? Coef(expr2, var2, 1) =? 1)), res := Failed,  [ sol2 := Solve(expr2, var2); If(Error?(\"SolveFails\"), [ ClearError(\"SolveFails\"); res := Failed; ], [ res := {}; i := 1; While(i <=? Length(sol2) And? res !=? Failed) [ sol := Solve(context == (var2 Where sol2[i]), var); If(Error?(\"SolveFails\"), [ ClearError(\"SolveFails\"); res := Failed; ], res := Union(res, sol)); i++; ]; ]); ]); ]); res;];10 # SolveContext(expr_Atom?, _var) <-- If(expr=?var, var, {});20 # SolveContext(_expr, _var) <--[ Local(lst, foundVarP, context, i, res); lst := FunctionToList(expr); foundVarP := False; i := 2; While(i <=? Length(lst) And? Not? foundVarP) [ foundVarP := (lst[i] =? var); i++; ]; If(foundVarP, context := expr, [ context := {}; i := 2; While(i <=? Length(lst) And? context !=? False) [ res := SolveContext(lst[i], var); If(res !=? {} And? context !=? {} And? res !=? context, context := False); If(res !=? {} And? context =? {}, context := res); i++; ]; ]); context;];20 # SolveSimple(_e1 + _e2, _rhs, _var)_(e1 =? var And? Not? HasExpression?(e2,var)) <-- { var == rhs-e2 };20 # SolveSimple(_e1 + _e2, _rhs, _var)_(e2 =? var And? Not? HasExpression?(e1,var)) <-- { var == rhs-e1 };20 # SolveSimple(_e1 - _e2, _rhs, _var)_(e1 =? var And? Not? HasExpression?(e2,var)) <-- { var == rhs+e2 };20 # SolveSimple(_e1 - _e2, _rhs, _var)_(e2 =? var And? Not? HasExpression?(e1,var)) <-- { var == e1-rhs };20 # SolveSimple(-(_e1), _rhs, _var)_(e1 =? var) <-- { var == -rhs };20 # SolveSimple(_e1 * _e2, _rhs, _var)_(e1 =? var And? Not? HasExpression?(e2,var)) <-- { var == rhs/e2 };20 # SolveSimple(_e1 * _e2, _rhs, _var)_(e2 =? var And? Not? HasExpression?(e1,var)) <-- { var == rhs/e1 };20 # SolveSimple(_e1 / _e2, _rhs, _var)_(e1 =? var And? Not? HasExpression?(e2,var)) <-- { var == rhs*e2 };10 # SolveSimple(_e1 / _e2, 0, _var)_(e2 =? var And? Not? HasExpression?(e1,var)) <-- { };20 # SolveSimple(_e1 / _e2, _rhs, _var)_(e2 =? var And? Not? HasExpression?(e1,var)) <-- { var == e1/rhs };LocalSymbols(x)[ 20 # SolveSimple(_e1 ^ _n, _rhs, _var)_(e1 =? var And? PositiveInteger?(n)) <-- MapSingle({{x}, var == rhs^(1/n)*x}, Exp(2*Pi*I*(1 .. n)/n)); 20 # SolveSimple(_e1 ^ _n, _rhs, _var)_(e1 =? var And? NegativeInteger?(n)) <-- MapSingle({{x}, var == rhs^(1/n)*x}, Exp(2*Pi*I*(1 .. (-n))/(-n)));];20 # SolveSimple(_e1 ^ _e2, _rhs, _var) _ (PositiveReal?(e1) And? e1 !=? 0 And? e2 =? var And? PositiveReal?(rhs) And? rhs !=? 0) <-- { var == Ln(rhs)/Ln(e1) };10 # SolveSimple(Sin(_e1), 1, _var)_(e1 =? var) <-- { var == 1/2*Pi };10 # SolveSimple(Sin(_e1), _rhs, _var)_(e1 =? var And? rhs =? -1) <-- { var == 3/2*Pi };20 # SolveSimple(Sin(_e1), _rhs, _var)_(e1 =? var) <-- { var == ArcSin(rhs), var == Pi-ArcSin(rhs) };10 # SolveSimple(Cos(_e1), 1, _var)_(e1 =? var) <-- { var == 0 };10 # SolveSimple(Cos(_e1), _rhs, _var)_(e1 =? var And? rhs =? -1) <-- { var == Pi };20 # SolveSimple(Cos(_e1), _rhs, _var)_(e1 =? var) <-- { var == ArcCos(rhs), var == -ArcCos(rhs) };20 # SolveSimple(Tan(_e1), _rhs, _var)_(e1 =? var) <-- { var == ArcTan(rhs) };20 # SolveSimple(ArcSin(_e1), _rhs, _var)_(e1 =? var) <-- { var == Sin(rhs) };20 # SolveSimple(ArcCos(_e1), _rhs, _var)_(e1 =? var) <-- { var == Cos(rhs) };20 # SolveSimple(ArcTan(_e1), _rhs, _var)_(e1 =? var) <-- { var == Tan(rhs) };10 # SolveSimple(Exp(_e1), 0, _var)_(e1 =? var) <-- { };20 # SolveSimple(Exp(_e1), _rhs, _var)_(e1 =? var) <-- { var == Ln(rhs) };20 # SolveSimple(_b^_e1, _rhs, _var)_(e1 =? var And? FreeOf?(var,b) And? Not? Zero?(b)) <-- { var == Ln(rhs) / Ln(b) };20 # SolveSimple(Ln(_e1), _rhs, _var)_(e1 =? var) <-- { var == Exp(rhs) };20 # SolveSimple(Sqrt(_e1), _rhs, _var)_(e1 =? var And? PositiveReal?(Re(rhs)) And? Re(rhs) !=? 0) <-- { var == rhs^2 };20 # SolveSimple(Sqrt(_e1), _rhs, _var)_(e1 =? var And? Re(rhs)=?0 And? PositiveReal?(Im(rhs))) <-- { var == rhs^2 };20 # SolveSimple(Sqrt(_e1), _rhs, _var)_(e1 =? var And? Re(rhs)=?0 And? NegativeReal?(Im(rhs)) And? Im(rhs) !=? 0) <-- { };20 # SolveSimple(Sqrt(_e1), _rhs, _var)_(e1 =? var And? NegativeReal?(Re(rhs)) And? Re(rhs) !=? 0) <-- { };30 # SolveSimple(_lhs, _rhs, _var) <-- Failed;10 # SolveDivide(_e1 + _e2, _var)_(HasExpression?(e1, var) And? HasExpression?(e2, var) And? Not? (HasExpression?(Simplify(1 + (e2/e1)), e1) Or? HasExpression?(Simplify(1 + (e2/e1)), e2))) <-- Solve(1 + (e2/e1), var);10 # SolveDivide(_e1 - _e2, _var)_(HasExpression?(e1, var) And? HasExpression?(e2, var) And? Not? (HasExpression?(Simplify(1 - (e2/e1)), e1) Or? HasExpression?(Simplify(1 - (e2/e1)), e2))) <-- Solve(1 - (e2/e1), var);20 # SolveDivide(_e, _v) <-- Failed;";
        scriptMap.put("Solve",scriptString);
        scriptMap.put("SolvePoly",scriptString);
        scriptMap.put("SolveReduce",scriptString);
        scriptMap.put("SolveContext",scriptString);
        scriptMap.put("SolveSimple",scriptString);
        scriptMap.put("SolveDivide",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " RulebaseHoldArguments(\"xPSolve\",{uni});RuleHoldArguments(\"xPSolve\",1,1,UniVar?(uni) And? Degree(uni) =? 1)[ If(iDebug,Tell(\" xPSolve_1\",uni)); {-Coef(uni,0)/Coef(uni,1)};];RuleHoldArguments(\"xPSolve\",1,1,UniVar?(uni) And? Degree(uni) =? 2)[ If(iDebug,Tell(\" xPSolve_2\",uni)); Local(a,b,c,d,q,r); c:=Coef(uni,0); b:=Coef(uni,1); a:=Coef(uni,2); If(iDebug,Tell(\" \",{a,b,c})); d:=b*b-4*a*c; If(iDebug,Tell(\" \",d));  q:=Sqrt(d)/(2*a); If(iDebug,Tell(\" \",q)); r:=Simplify(-b/(2*a)); If(iDebug,Tell(\" \",r)); {r+q,r-q};];RuleHoldArguments(\"xPSolve\",1,1,UniVar?(uni) And? Degree(uni) =? 3 )[ If(iDebug,Tell(\" xPSolve_3\",uni)); Local(p,q,r,s,t,w1,w2,a,b); Local(c0,c1,c3,adjust);  w1 := (1/2)*Complex(-1, Sqrt(3)); w2 := Conjugate(w1); If( iDebug, Tell(\" \",{w1,w2}) );   adjust := (-uni[3][3])/(3*uni[3][4]); If( iDebug, Tell(\" \",adjust)); c3 := uni[3][4]; c1 := (3*uni[3][4]*adjust+2*uni[3][3])*adjust+uni[3][2]; c0 :=((uni[3][4]*adjust+uni[3][3])*adjust+uni[3][2])*adjust+uni[3][1]; If( iDebug, Tell(\" \",{c0,c1,c3}));   Assert(\"Invariant\", \"Coefficients Must be Real\") And?(Im(c0)=?0,Im(c1)=?0,Im(c2)=?0); If( Error?(\"Invariant\"), DumpErrors() ); p :=c3; q :=c1/p; r :=c0/p; If( iDebug, Tell(\" \",{p,q,r})); Local(a3,b3,qq,r1,r2,r3); qq := Sqrt(q^3/27 + r^2/4); a3 := -r/2 + qq; b3 := -r/2 - qq;  If( iDebug, [Tell(\" \",{qq,a3,b3}); Tell(\" \",N(a3+b3+r)); Tell(\" \",N(a3-b3-2*qq));]); a := (a3)^(1/3); b := (b3)^(1/3); If( iDebug, Tell(\" \",{a,b})); r1 := a+b+adjust; r2 := w1*a+w2*b+adjust; r3 := w2*a+w1*b+adjust;  If( iDebug, [ Tell(\" \",r1); Tell(\" \",r2); Tell(\" \",r3); ] ); {r1,r2,r3};];RuleHoldArguments(\"xPSolve\",1,1,UniVar?(uni) And? Degree(uni) =? 4 )[ If(iDebug,Tell(\" xPSolve_4\",uni)); Local(coef4,a1,a2,a3,a4,y,y1,z,t,s); coef4:=Coef(uni,4); a1:=Coef(uni,3)/coef4; a2:=Coef(uni,2)/coef4; a3:=Coef(uni,1)/coef4; a4:=Coef(uni,0)/coef4; If( iDebug, Tell(\" \",{a1,a2,a3,a4}));  Local(ys); ys := xPSolveCubic(y^3-a2*y^2+(a1*a3-4*a4)*y+(4*a2*a4-a3^2-a1^2*a4)); If( iDebug, [NewLine(); Tell(\" \",ys[1]);] ); y1:=First(ys); If( iDebug, Tell(\" \",y1)); t := Sqrt(y1^2/4-a4); If( iDebug, Tell(\" \",t)); If(t=?0, s:=Sqrt(y1+a1^2/4-a2), s:=(a1*y1-2*a3)/(4*t)); If( iDebug, Tell(\" \",s));  Local(q11,q12,q21,q2,quad1,quad2); q11 := a1/2+s; q12 := y1/2+t; q21 := a1/2-s; q22 := y1/2-t; If( iDebug, Tell(\" \",{q11,q12})); If( iDebug, Tell(\" \",{q21,q22})); quad1 := z^2 + q11*z + q12; quad2 := z^2 + q21*z + q22; If( iDebug, Tell(\" \",{quad1,quad2}));  Local(r1,r2,r3,r4); {r1,r2} := xPSolve( quad1, z ); {r3,r4} := xPSolve( quad2, z ); r1 := NearRational(N(r1,10),8); r2 := NearRational(N(r2,10),8); r3 := NearRational(N(r3,10),8); r4 := NearRational(N(r4,10),8);  {r1,r2,r3,r4};];Function(\"xPSolve\",{expr,var})[ If( Not? Bound?(iDebug), iDebug := False ); If(iDebug,Tell(\"xPSolve_notUni\",{expr,var}));  Local(lhs,rhs,cc,pp,uni,solnpp,solncc,soln); If( Equation?(expr),  [ If(iDebug,Tell(\" is Equation\")); lhs := EquationLeft(expr); rhs := EquationRight(expr); expr := lhs - rhs; ] ); If(iDebug,Tell(\" \",expr)); cc := xContent(expr); pp := xPrimitivePart(expr,cc); If(iDebug,Tell(\" \",{cc,pp})); solnpp := xPSolve(MakeUni(pp,var)); If(iDebug,Tell(\" \",solnpp)); If( Length(VarList(cc)) >? 0 And? Contains?(VarList(cc),var ), [ solncc := xPSolve(MakeUni(cc,var)); If(iDebug,Tell(\" \",solncc)); soln := Concat(solncc,solnpp); ], [ soln := solnpp; ] ); soln;];10 # xPSolveCubic( poly_Polynomial? )_ (Length(VarList(poly))=?1 And? Degree(poly)=?3) <--[ If( iDebug, Tell(\" xPSolveCubic\",poly) ); Local(var,coeffs,ans); var := VarList(poly)[1]; coeffs := Coef(poly,var,3 .. 0); If( iDebug, Tell(\" \",{var,coeffs})); ans := xPSC1(coeffs); ];UnFence(\"xPSolveCubic\",1);10 # xPSC1( coeffs_List? ) <--[ If( iDebug, Tell(\" xPSC1\",coeffs) );  Local(f,g,h,j,iType,ans); f := coeffs[2]/coeffs[1]/3; g := coeffs[3]/coeffs[1]/3 - f^2; h := coeffs[4]/coeffs[1]/2 + f^3 - f * coeffs[3]/coeffs[1]/2; j := g^3 + h^2; If( iDebug, Tell(\" \",{f,g,h,j}) ); ans := xPSC2( {f,g,h,j} );];10 # xPSC2( xs_List? )_(xs[4]=?0) <--[ If( iDebug, Tell(\" Type 1\",xs) );  Local(f,g,h,j,m,r1,r2,r3,ans); {f,g,h,j} := FlatCopy(xs); m := 2*(-h)^(1/3); r1 := NearRational(N(m - f,10),8); r2 := NearRational(N(-m/2 - f,10),8); r3 := NearRational(N(-m/2 - f,10),8); ans := {r1,r2,r3};];10 # xPSC2( xs_List? )_(xs[4]>?0) <--[ If( iDebug, Tell(\" Type 2\",xs) );  Local(f,g,h,j,k,l1,l2,m,n,r1,r2,r3,ans);  {f,g,h,j} := FlatCopy(xs); k := Sqrt(j); l1 := (-h + k)^(1/3); l2 := (-h - k)^(1/3); m := l1 + l2; n := (l1 - l2)*Sqrt(3)/2; r1 := NearRational(N(m - f,10),8); r2 := NearRational(N(-m/2 - f + I*n,10),8); r3 := NearRational(N(Conjugate(r2),10),8); ans := {r1,r2,r3}; ];10 # xPSC2( xs_List? )_(xs[4]<?0 And? xs[3]=?0) <--[ If( iDebug, Tell(\" Type 3a\",xs) ); Local(f,g,h,j,p,r1,r2,r3,ans); {f,g,h,j} := FlatCopy(xs); p := 2*Sqrt(-g); r1 := NearRational(N(-f,10),8); r2 := NearRational(N( p*Sqrt(3)/2 - f,10),8); r3 := NearRational(N(-p*Sqrt(3)/2 - f,10),8); ans := {r1,r2,r3};];10 # xPSC2( xs_List? )_(xs[4]<?0 And? xs[3]>?0) <--[ If( iDebug, Tell(\" Type 3b\",xs) ); Local(p,x,alpha,beta,gama,r1,r2,r3,ans); {f,g,h,j} := FlatCopy(xs); p := 2*Sqrt(-g); k := Sqrt(-j); alpha := ArcTan(k/(-h));  beta := Pi + alpha; gama := beta / 3; If( iDebug, [ Tell(\" \",{p,k}); Tell(\" \",{alpha,beta,gama}); Tell(\" \",57.2957795*N({alpha,beta,gama})); Tell(\" \",N(Cos(gama))); ] ); r1 := NearRational(N(p * Cos(gama) - f,10),8); r2 := NearRational(N(p * Cos(gama+2*Pi/3) - f,10),8); r3 := NearRational(N(p * Cos(gama+4*Pi/3) - f,10),8); ans := {r1,r2,r3};];10 # xPSC2( xs_List? )_(xs[4]<?0 And? xs[3]<?0) <--[ If( iDebug, Tell(\" Type 3c\",xs) ); Local(f,g,h,j,p,k,alpha,beta,gama,r1,r2,r3,ans); {f,g,h,j} := FlatCopy(xs); p := 2*Sqrt(-g); k := Sqrt(-j); alpha := ArcTan(k/(-h));  beta := alpha; gama := beta / 3; If(iDebug,[Tell(\" \",{p,k}); Tell(\" \",{alpha,beta,gama});]); r1 := NearRational(N(p * Cos(gama) - f,10),8); r2 := NearRational(N(p * Cos(gama+2*Pi/3) - f,10),8); r3 := NearRational(N(p * Cos(gama+4*Pi/3) - f,10),8); ans := {r1,r2,r3};];";
        scriptMap.put("xPSolve",scriptString);
        scriptMap.put("xPSolveCubic",scriptString);
        scriptMap.put("xPSC1",scriptString);
        scriptMap.put("xPSC2",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"BubbleSort\",{list,compare})[ Local(i,j,length,left,right);     list:=FlatCopy(list); length:=Length(list);  For (j:=length,j>?1,j--) [ For(i:=1,i<?j,i++) [ left:=list[i]; right:=list[i+1];   If(Not?(Apply(compare,{left,right})), [ DestructiveInsert(DestructiveDelete(list,i),i+1,left); ] );  ]; ]; list;];";
        scriptMap.put("BubbleSort",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BubbleSortIndexed( L_List?, _compare ) <--[ Local(list,indices,pairs,sortedPairs); list := FlatCopy(L); indices := Table(i,i,1,Length(list),1); pairs := Transpose(list~{indices}); sortedPairs := If( Apply(compare,{3,5}),  BubbleSort( pairs, Lambda({X,Y},X[1] <? Y[1]) ), BubbleSort( pairs, Lambda({X,Y},X[1] >? Y[1]) ) ); Transpose(sortedPairs);];";
        scriptMap.put("BubbleSortIndexed",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "HeapSort(L_List?, _compare) <-- [ Local(list); list := FlatCopy(L); HeapSort1(list, ArrayCreate(Length(list), 0), 1, Length(list), compare);];1 # HeapSort1(_L, _tmplist, _first, _last, _compare) _ (last - first <=? 2) <-- SmallSort(L, first, last, compare);2 # HeapSort1(_L, _tmplist, _first, _last, _compare) <--[    Local(mid, ileft, iright, pleft);  mid := first+((last-first)>>1); HeapSort1(L, tmplist, first, mid, compare); HeapSort1(L, tmplist, mid+1, last, compare);  For(ileft := first, ileft <=? mid, ileft++) tmplist[ileft] := L[ileft]; For( [ileft := first; pleft := first; iright := mid+1;], ileft <=? mid,  pleft++  )     If(  iright>?last Or? Apply(compare,{tmplist[ileft],L[iright]}), [  L[pleft] := tmplist[ileft]; ileft++; ], [  L[pleft] := L[iright]; iright++; ] ); L;];";
        scriptMap.put("HeapSort",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "HeapSortIndexed( L_List?, _compare ) <-- HeapSortIndexed1( FlatCopy(L), compare );HeapSortIndexed1( L_List?, _compare ) <--[ Local(list,indices,pairs,sortedPairs); list := FlatCopy(L); indices := Table(i,i,1,Length(list),1); pairs := Transpose(list~{indices}); SortedPairs := {}; sortedPairs := If( Apply(compare,{3,5}),  HeapSort( pairs, Lambda({X,Y},X[1] <? Y[1]) ), HeapSort( pairs, Lambda({X,Y},X[1] >? Y[1]) ) ); Transpose(sortedPairs);];";
        scriptMap.put("HeapSortIndexed",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SmallSort(_list, _first, _last, _compare) _ (last=?first) <-- list;SmallSort(_list, _first, _last, _compare) _ (last=?first+1) <--[ Local(temp); temp := list[first]; If( Apply(compare,{temp,list[last]}), list, [ list[first] := list[last]; list[last] := temp; ]  ); list;];SmallSort(_list, _first, _last, _compare) _ (last=?first+2) <--[ Local(temp); temp := list[first]; If( Apply(compare,{list[first+1],temp}), [ list[first] := list[first+1]; list[first+1] := temp; ]  );  temp := list[last]; If( Apply(compare,{list[first],temp}), If(  Apply(compare,{list[first+1],temp}), list, [ list[last] := list[first+1]; list[first+1] := temp; ]  ), [  list[last] := list[first+1]; list[first+1] := list[first]; list[first] := temp; ] ); list;];";
        scriptMap.put("SmallSort",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SmallSortIndexed(_list, _first, _last, _compare)_(last=?first) <-- list;SmallSortIndexed(_list, _first, _last, _compare)_(last=?first+1) <--[ Local(temp); temp := list[first]; If( Apply(compare,{temp[1],list[last][1]}), list, [ list[first] := list[last]; list[last] := temp; ]  ); list;];SmallSortIndexed(_list, _first, _last, _compare)_(last=?first+2) <--[ Local(temp); temp := list[first]; If( Apply(compare,{list[first+1][1],temp[1]}), [ list[first] := list[first+1]; list[first+1] := temp; ]  );  temp := list[last]; If( Apply(compare,{list[first][1],temp[1]}), If(  Apply(compare,{list[first+1][1],temp[1]}), list, [ list[last] := list[first+1]; list[first+1] := temp; ]  ), [  list[last] := list[first+1]; list[first+1] := list[first]; list[first] := temp; ] ); list;];";
        scriptMap.put("SmallSortIndexed",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " 10 # Sort( L_List? )_(Length(L) <? 20) <-- BubbleSort( L, \"<?\" );15 # Sort( L_List?, _compare )_(Length(L) <? 20) <--  BubbleSort( L, compare );20 # Sort( L_List? ) <-- HeapSort( L, \"<?\" );25 # Sort( L_List?, _compare ) <-- HeapSort( L, compare ); 10 # SortIndexed( L_List? )_(Length(L) <? 20) <-- BubbleSortIndexed( L, \"<?\" );15 # SortIndexed( L_List?, _compare )_(Length(L) <? 20) <-- BubbleSortIndexed( L, compare );20 # SortIndexed( L_List? ) <-- HeapSortIndexed( L, \"<?\" );25 # SortIndexed( L_List?, _compare ) <-- HeapSortIndexed( L, compare );";
        scriptMap.put("Sort",scriptString);
        scriptMap.put("SortIndexed",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"ApproxInfSum\",{expr,start,x})[ ApproxInfSum(expr,start,x,{0});];Function(\"ApproxInfSum\",{expr,start,x,c})[ Local(term,result,k); Local(prec,eps,tmp); prec:=BuiltinPrecisionGet(); BuiltinPrecisionSet(prec+2);  eps:=10^(-prec); term:=1; k:=start; result:=0; While( N(Abs(term) >=? eps) )[ term:=N(Eval(expr));  k:=k+1; result:=result+term; ]; If(InVerboseMode(), Echo(\"ApproxInfSum: Info: using \", k, \" terms of the series\")); BuiltinPrecisionSet(prec);   result;];";
        scriptMap.put("ApproxInfSum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Bernoulli(0) <-- 1;10 # Bernoulli(1) <-- -1/2;15 # Bernoulli(n_Integer?)_(n<?0) <-- Undefined;30 # Bernoulli(n_Odd?) <-- 0;20 # Bernoulli(n_Even?)_(n<=?Bernoulli1Threshold()) <-- InternalBernoulliArray(n)[n+1];20 # Bernoulli(n_Even?)_(n>?Bernoulli1Threshold()) <-- Bernoulli1(n);LocalSymbols(bernoulli1Threshold) [  If(Not? Bound?(bernoulli1Threshold), bernoulli1Threshold := 20); Bernoulli1Threshold() := bernoulli1Threshold; SetBernoulli1Threshold(threshold) := [ bernoulli1Threshold := threshold;];] ; Bernoulli(n_Integer?, _x) <-- [ Local(B, i, result); B := InternalBernoulliArray(n); result := B[1]; For(i:=n-1, i>=?0, i--) [ result := result * x + B[n-i+1]*BinomialCoefficient(n,i); ]; result;];";
        scriptMap.put("Bernoulli",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # BesselI(0,0) <-- 1;10 # BesselI(_n,0)_(n>?0) <-- 0;10 # BesselI(_n,0)_(n<?0 And? Integer?(n)) <-- 0;10 # BesselI(_n,0)_(n<?0 And? Not? Integer?(n)) <-- Infinity;20 # BesselI(1/2,_x) <-- Sqrt(2/(x*Pi))*Sinh(x);20 # BesselI(3/2,_x) <-- Sqrt(2/(x*Pi))*(Cosh(x) - Sinh(x)/x);20 # BesselI(5/2,_x) <-- Sqrt(2/(x*Pi))*((3/x^2 + 1)*Sinh(x) - 3*Cosh(x)/x );30 # BesselI(_n,_z)_(n<?0 And? Integer?(n) ) <-- BesselI(-n,z);40 # BesselI(_n,x_Complex?)_(Constant?(x) And? Abs(x)<=? 2*Gamma(n) ) <--[ApproxInfSum((x/2)^(2*k+c[1])/(k! * Gamma(k+c[1]+1) ),0,x,{n} );];";
        scriptMap.put("BesselI",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # BesselJ(0,0) <-- 1;10 # BesselJ(_n,0)_(n>?0) <-- 0;10 # BesselJ(_n,0)_(n<?0 And? Integer?(n)) <-- 0;10 # BesselJ(_n,0)_(n<?0 And? Not? Integer?(n)) <-- Infinity;10 # BesselJ(0,Infinity)<-- 0;20 # BesselJ(1/2,_x) <-- Sqrt(2/(x*Pi))*Sin(x);20 # BesselJ(-1/2,_x) <-- Sqrt(2/(x*Pi))*Cos(x);20 # BesselJ(3/2,_x) <-- Sqrt(2/(x*Pi))*(Sin(x)/x - Cos(x));20 # BesselJ(-3/2,_x) <-- Sqrt(2/(x*Pi))*(Cos(x)/x + Sin(x));20 # BesselJ(5/2,_x) <-- Sqrt(2/(x*Pi))*((3/x^2 - 1)*Sin(x) - 3*Cos(x)/x );20 # BesselJ(-5/2,_x) <-- Sqrt(2/(x*Pi))*( (3/x^2 -1)*Cos(x) + 3*Sin(x)/x );30 # BesselJ(_n,_x)_(Constant?(x) And? Integer?(n) And? N(Abs(x) >? 2*Gamma(n))) <-- N((2*(n+1)/x)*BesselJ(n+1,x) - BesselJ(n+2,x));30 # BesselJ(_n,_z)_(n<?0 And? Integer?(n) ) <-- (-1)^n*BesselJ(-n,z);40 # BesselJ(_n,x_Complex?)_(N(Abs(x)<=? 2*Gamma(n)) ) <--[ApproxInfSum((-1)^k*(x/2)^(2*k+c[1])/(k! * Gamma(k+c[1]+1) ),0,x,{n} );];50 # BesselJ(0,x_Complex?)_(InNumericMode()) <-- N(BesselJN0(x));";
        scriptMap.put("BesselJ",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "40 # BesselY(_n,x_Complex?)_(Abs(x)<=? 2*Gamma(n) ) <-- N((Cos(n*Pi)*BesselJ(n,x) - BesselJ(-n,x))/Sin(Pi*n));";
        scriptMap.put("BesselY",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Beta(_n,_m) <-- Gamma(m)*Gamma(n)/Gamma(m+n);";
        scriptMap.put("Beta",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CatalanConstNum1() :=[ Local(prec,Aterm,Bterm,nterms,result,n); prec:=BuiltinPrecisionGet(); BuiltinPrecisionSet(10);  nterms := 1+Floor(N((prec*Ln(10)+Ln(prec*Ln(10)/Ln(2)))/Ln(2))); BuiltinPrecisionSet(prec+5); Aterm:=N(1/2); result:= Aterm; Bterm:=Aterm; For(n:=1, n<=?nterms, n++ ) [ Bterm:=DivideN(MultiplyN(Bterm,n), 2*n+1);  Aterm:=DivideN(MultiplyN(Aterm,n)+Bterm, 2*n+1);  result := result + Aterm; ]; BuiltinPrecisionSet(prec); RoundTo(result,prec);];CatalanConstNum() :=[ Local(prec, n, result); prec:=BuiltinPrecisionGet();  n := 1+Quotient(prec*1068+642,643);  BuiltinPrecisionSet(prec+2);  result := N(1/(2*n+1)); While(n>?0) [ result := DivideN(MultiplyN(result, n), 4*n+2)+DivideN(1,2*n-1); n := n-1; ]; result := MultiplyNum(result, 3/8) + N(Pi/8*Ln(2+Sqrt(3))); BuiltinPrecisionSet(prec); RoundTo(result,prec);];CatalanConstNum2() :=[ Local(prec, n, result1, result2); prec:=BuiltinPrecisionGet();   n := 1+Quotient(prec*534+642,643);  BuiltinPrecisionSet(prec+2);  result1 := 0; While(n>=?0) [ result1 := DivideN(result1, 16)+N( +1/(8*n+1)^2 -1/(8*n+2)^2 +1/2/(8*n+3)^2 -1/4/(8*n+5)^2 +1/4/(8*n+6)^2 -1/8/(8*n+7)^2 ); n := n-1; ];   n := 1+Quotient(prec*178+642,643);  BuiltinPrecisionSet(prec+2);  result2 := 0; While(n>=?0) [ result2 := DivideN(result2, 4096)+N( +1/(8*n+1)^2 +1/2/(8*n+2)^2 +1/8/(8*n+3)^2 -1/64/(8*n+5)^2 -1/128/(8*n+6)^2 -1/512/(8*n+7)^2 ); n := n-1; ]; result1 := MultiplyNum(result1, 3/2) - MultiplyNum(result2, 1/4); BuiltinPrecisionSet(prec); RoundTo(result1,prec);];";
        scriptMap.put("CatalanConstNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "";
        scriptMap.put("DawsonIntegral",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Digamma(_n)_(PositiveInteger?(n)) <-- Sum(m,1,n-1,1/m) - gamma;";
        scriptMap.put("Digamma",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # DirichletBeta(1) <-- Pi/4;5 # DirichletBeta(2) <-- Catalan;5 # DirichletBeta(3) <-- Pi^3/32;6 # DirichletBeta(n_Odd?) <-- [ Local(k); k:=(n-1)/2; (-1)^k*Euler(2*k)*(Pi/2)^(2*k+1)/(2*(2*k)!);];10 # DirichletBeta(x_RationalOrNumber?)_(InNumericMode() And? x>=?1 ) <-- [ Local(prec,eps,term,result,k); prec:=BuiltinPrecisionGet(); BuiltinPrecisionSet(prec+3); eps:=10^(-prec); result:=0; term:=1; For(k:=0, Abs(term) >? eps, k++ )[ term:=(-1)^k/(2*k+1)^x; Echo(\"term is \",term); result:=result+term; ]; BuiltinPrecisionSet(prec); RoundTo(result,prec);];";
        scriptMap.put("DirichletBeta",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # DirichletEta(_z) <-- (1-2/2^z)*Zeta(z);";
        scriptMap.put("DirichletEta",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # DirichletLambda(_z)<-- (1-1/2^z)*Zeta(z);";
        scriptMap.put("DirichletLambda",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Erf(0) <-- 0;10 # Erf(Infinity) <-- 1;10 # Erf(Undefined) <-- Undefined;10 # Erf(x_Number?)_(x<?0) <-- -Erf(-x);LocalSymbols(k)[ 40 # Erf(_x)_(InNumericMode() And? (Number?(x) Or? Complex?(x)) And? Abs(x) <=? 1) <--[ Local(prec); prec := BuiltinPrecisionGet();  2 / SqrtN(InternalPi()) * x * SumTaylorNum(x^2, 1, {{k}, -(2*k-1)/(2*k+1)/k},  N(1+87/32*Exp(LambertW(prec*421/497)), 20) );];]; LocalSymbols(nmax, k)[ 50 # Erf(_x)_(InNumericMode() And? (Number?(x) Or? Complex?(x)) And? ( [  nmax := 0; Re(x^2) >? BuiltinPrecisionGet()*3295/1431+0.121; ] Or? [  nmax := N(Minimum((BuiltinPrecisionGet()*3295/1431+0.121)/InternalLnNum(Abs(x)), 2*InternalLnNum(Abs(x))), 10); 2*Abs(x)+Re(x^2) >? BuiltinPrecisionGet()*3295/1431+0.121; ] Or? [   nmax := N(({{k}, k+InternalLnNum(k)} @ BuiltinPrecisionGet()*3295/1431)/2 - 3/2, 10); Abs(x) >? nmax+3/2; ] ) ) <-- If(Re(x)!=?0, Sign(Re(x)), 0) - Exp(-x^2)/x/SqrtN(InternalPi())  * SumTaylorNum(1/x^2, 1, {{k}, -(2*k-1)/2 }, Maximum(0, Floor(nmax)));]; ";
        scriptMap.put("Erf",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Erfc(_x) <-- 1 - Erf(x);";
        scriptMap.put("Erfc",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Erfi(_x) <-- -I*Erf(x*I);";
        scriptMap.put("Erfi",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # FresnelCos(0) <-- 0;10 # FresnelCos(Infinity) <-- 1/2;10 # FresnelCos(x_Number?)_(x<?0) <-- -FresnelCos(x);40 # FresnelCos(x_Number?)_(Abs(x) <=? 1) <-- N(Sqrt(2/Pi)*ApproxInfSum((-1)^(k+1)*x^(4*k-3)/((4*k-3) * (2*k-2)! ),1,x));";
        scriptMap.put("FresnelCos",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # FresnelSin(0) <-- 0;10 # FresnelSin(Infinity) <-- 1/2;10 # FresnelSin(x_Number?)_(x<?0) <-- -FresnelSin(x);40 # FresnelSin(x_Number?)_(Abs(x) <=? 1) <-- N(Sqrt(2/Pi)*ApproxInfSum((-1)^(k+1)*x^(2*k+1)/(k! * (2*k+1)),1,x));";
        scriptMap.put("FresnelSin",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # Gamma(Infinity) <-- Infinity;10 # Gamma(_n)_(Integer?(n) And? n<=?0) <-- Infinity;20 # Gamma(n_RationalOrNumber?)_(PositiveInteger?(n) Or? FloatIsInt?(2*n)) <-- (Round(2*n)/2-1)!;30 # Gamma(x_Constant?)_(InNumericMode()) <-- InternalGammaNum(N(Eval(x)));";
        scriptMap.put("Gamma",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # LambertW(0) <-- 0;10 # LambertW(Infinity) <-- Infinity;10 # LambertW(Undefined) <-- Undefined;10 # LambertW(-Infinity) <-- Infinity + I*Pi;10 # LambertW(-Exp(-1)) <-- -1;20 # LambertW(_x * Ln(_x)) <-- Ln(x);20 # LambertW(Ln(_x) * _x) <-- Ln(x);30 # LambertW(x_Constant?) _ InNumericMode() <-- InternalLambertWNum(Eval(x));10 # InternalLambertWNum(x_Number?)_(x <? -ExpN(-1)) <-- Undefined;20 # InternalLambertWNum(x_Number?) <--[ Local(W); NewtonNum( `Hold( { {W}, [ Local(a); a:=W- @x*ExpN(-W); W-a/(W+1-(W+2)/(W+1)*a/2.); ]}),  If( x<?0, x*ExpN(1) / (1+1 / (1 / SqrtN(2*(x*ExpN(1)+1)) - 1 / SqrtN(2) + 1/(ExpN(1)-1))), InternalLnNum(1+x)*(1-InternalLnNum(1+InternalLnNum(1+x))/(2+InternalLnNum(1+x))) ), 10,  3  );];";
        scriptMap.put("LambertW",scriptString);
        scriptMap.put("InternalLambertWNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # LnGamma(_n)_(Integer?(n) And? n<=?0) <-- Infinity;20 # LnGamma(n_RationalOrNumber?)_(PositiveInteger?(n) Or? FloatIsInt?(2*n)) <-- Ln((Round(2*n)/2-1)!);30 # LnGamma(x_Constant?)_(InNumericMode()) <-- InternalLnGammaNum(N(Eval(x)));";
        scriptMap.put("LnGamma",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # PolyLog(_n,0) <-- 0;10 # PolyLog(1,1/2) <-- Ln(2);10 # PolyLog(_n,1) <-- Zeta(n);10 # PolyLog(_n,_m)_(m=? -1) <-- DirichletEta(n);10 # PolyLog(_n,_x)_(n<? 0) <-- (1/((1-x)^(-n+1)))*Sum(i,0,-n,Eulerian(-n,i)*x^(-n-i) );10 # PolyLog(0,_x) <-- x/(1-x);10 # PolyLog(1,_x) <-- -Ln(1-x);10 # PolyLog(2,1/2) <-- (Pi^2 - 6*Ln(2)^2)/12;10 # PolyLog(3,1/2) <-- (4*Ln(2)^3 - 2*Pi^2*Ln(2)+21*Zeta(3))/24;10 # PolyLog(2,2) <-- Pi^2/4 - Pi*I*Ln(2);20 # PolyLog(_n,_x)_(InNumericMode() And? x <? -1 ) <-- [ Local(prec,result); prec:=BuiltinPrecisionGet(); BuiltinPrecisionSet(prec+5); Echo(\"Warning: PolyLog is only currently accurate for x in [-1,1]\"); result:= (-1)^(n-1)*PolyLog(n,1/x) - ((Ln(-x))^n)/n! - Sum(r,1,Round(n/2), 2^(2*r-2)*Pi^(2*r)*Abs(Bernoulli(2*r))*Ln(-x)^(n-2*r)/( (2*r)! * (n - 2*r)! ) ); BuiltinPrecisionSet(prec); RoundTo(N(result),prec);];20 # PolyLog(_n,_x)_(InNumericMode() And? x>=? -1 And? x <? 0 ) <-- [   Local(prec,result); prec:=BuiltinPrecisionGet(); BuiltinPrecisionSet(prec+5); result:=PolyLog(n,x^2)/2^(n-1) - PolyLog(n,-x) ; BuiltinPrecisionSet(prec); RoundTo(N(result),prec);];20 # PolyLog(_n,_x)_(InNumericMode() And? x >? 0 And? x <? 1) <--[  Local(prec, result, terms); prec:=BuiltinPrecisionGet(); BuiltinPrecisionSet(15);  terms := Floor(-prec*Ln(10)/Ln(x)); terms := Floor(-(prec*Ln(10)-(n-1)*Ln(terms))/Ln(x)); If(terms <? 4, terms := 4); BuiltinPrecisionSet(prec+2*IntLog(prec,10)+5); result := x*SumTaylorNum(x, {{k}, 1/(k+1)^n}, terms); BuiltinPrecisionSet(prec); RoundTo(result, prec);];";
        scriptMap.put("PolyLog",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Sinc(_x) <-- If(x=?0,1,Sin(x)/x);";
        scriptMap.put("Sinc",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Zeta(1) <-- Infinity;10 # Zeta(0) <-- -1/2; 10 # Zeta(3)_InNumericMode() <-- Zeta3(); 10 # Zeta(n_Even?)_(n>?0) <-- Pi^n*(2^(n-1)/n! *Abs(Bernoulli(n)));10 # Zeta(n_Integer?)_(n<?0) <-- -Bernoulli(-n+1)/(-n+1);11 # Zeta(n_Infinity?) <-- 1;20 # Zeta(s_Constant?)_(InNumericMode()) <-- InternalZetaNum(N(Eval(s)));";
        scriptMap.put("Zeta",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Bernoulli1(n_Even?)_(n>=?2) <-- [ Local(B, prec); prec := BuiltinPrecisionGet();   BuiltinPrecisionSet(10); BuiltinPrecisionSet( Ceil(N((1/2*Ln(8*Pi*n)-n+n*Ln(n/2/Pi))/Ln(10)))+3  ); If (InVerboseMode(), Echo({\"Bernoulli: using zeta funcion, precision \", BuiltinPrecisionSet(), \", n = \", n})); B := Floor(N(  If(  n>?250,  InternalZetaNum2(n, n/17+1),    InternalZetaNum1(n, n/17+1)  ) *N(2*n! /(2*Pi)^n)))  * (2*Modulo(n/2,2)-1)  + BernoulliFracPart(n);  BuiltinPrecisionSet(prec);  B;];";
        scriptMap.put("Bernoulli1",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BernoulliFracPart(n_Even?)_(n>=?2) <-- [ Local(p, sum);  sum := 1/2+1/3;  If(Prime?(n+1), sum := sum+1/(n+1)); If(Prime?(n/2+1), sum := sum+1/(n/2+1));   For(p:=5, p<=?n/3+1, p:=NextPrime(p)) If(Modulo(n, p-1)=?0, sum := sum + 1/p);   Quotient(Numerator(sum), Denominator(sum)) - sum + Modulo(n/2,2);  ];";
        scriptMap.put("BernoulliFracPart",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # InternalBernoulliArray(n_Integer?)_(n=?0 Or? n=?1) <-- [ Local(B); B:=ArrayCreate(n+1,0); B[1] := 1; If(n=?1, B[2] := -1/2); B;];20 # InternalBernoulliArray(n_Integer?) <-- [ Local(B, i, k, k2, bin); If (InVerboseMode(), Echo({\"InternalBernoulliArray: using direct recursion, n = \", n})); B:=ArrayCreate(n+1, 0);     B[1] := 1; B[2] := -1/2; B[3] := 1/6; For(i:=4, i<=?n, i := i+2)  [  bin := 1;   B[i+1] := 1/2-1/(i+1)*(1 + Sum(k, 1, i/2-1, [ bin := bin * (i+3-2*k) * (i+2-2*k)/ (2*k-1) / (2*k); B[2*k+1]*bin;  ] ) ); ]; B;];";
        scriptMap.put("InternalBernoulliArray",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "InternalBernoulliArray1(n_Even?) _ (n>=?2) <--[ Local(C, f, k, j, denom, sum); C := ArrayCreate(n+1, 0); f := ArrayCreate(n/2, 0); C[1] := 1; C[2] := -1/2; C[3] := 1/12;  f[1] := 2;  For(k:=2, k<=?n/2, k++)  [  f[k] := f[k-1] * (2*k)*(2*k-1);  C[2*k+1] := 1/(1-4^(-k))/2*( [ denom := 4;  sum := 0; For(j:=1, j<=?k-1, j++) [ sum := sum + C[2*(k-j)+1]/denom/f[j];  denom := denom * 4; ]; (2*k-1)/denom/f[k] - sum; ] ); ];  For(k:=1, k<=?n/2, k++) C[2*k+1] := C[2*k+1] * f[k];  C;];";
        scriptMap.put("InternalBernoulliArray1",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"BesselJN0\",{x})[ Local(ax,z,xx,y,result,res1,res2); Local(c1,c2,c3,c4);   c1:={57568490574.0,-13362590354.0,651619640.7, -11214424.18,77392.33017,-184.9052456}; c2:={57568490411.0,1029532985.0,9494680.718, 59272.64853,267.8532712};   c3:={-0.001098628627,0.00002734510407,-0.000002073370639, 0.0000002093887211}; c4:={-0.01562499995,0.0001430488765,-0.000006911147651, 0.0000007621095161,0.0000000934935152}; ax:=Abs(x); If( ax <? 8.0,[ y:=x^2; res1:=c1[1]+y*(c1[2]+y*c1[3]+y*(c1[4]+y*(c1[5]+y*(c1[6])))); res2:=c1[1]+y*(c2[2]+y*c2[3]+y*(c2[4]+y*(c2[5]+y*1.0))); result:=res1/res2; ],[ z:=8/ax; y:=z^2; xx:=ax-0.785398164; res1:=1.0+y*(c3[1]+y*(c3[2]+y*(c3[3]+y*c4[4]))); res2:=c4[1]+y*(c4[2]+y*(c4[3]+y*(c4[4]-y*c4[5]))); result:=Sqrt(2/(Pi*x))*(Cos(xx)*res1-z*Sin(xx)*res2); ] );];";
        scriptMap.put("BesselJN0",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"BesselNsmall\",{n,x,modified})[ Local(term,result,k); Local(prec,eps,tmp); prec:=BuiltinPrecisionGet(); BuiltinPrecisionSet(Ceil(1.2*prec));  eps:=5*10^(-prec); term:=1; k:=0; result:=0; While( Abs(term) >=? eps )[ term:=x^(2*k+n);   If( k%2=?1 And? modified=?0 , term:=term*-1 ); term:=N(term/(2^(2*k+n)* k! * Gamma(k+n+1) ));  result:=result+term; k:=k+1; ]; BuiltinPrecisionSet(prec);   RoundTo(result,prec);];";
        scriptMap.put("BesselNsmall",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "InternalGammaNum(z) := N(Exp(InternalLnGammaNum(z)));InternalGammaNum(z,a) := N(Exp(InternalLnGammaNum(z,a)));";
        scriptMap.put("InternalGammaNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # InternalLnGammaNum(_z, _a)_(N(Re(z))<?0) <-- [ If (InVerboseMode(), Echo({\"InternalLnGammaNum: using 1-z identity\"})); N(Ln(Pi/Sin(Pi*z)) - InternalLnGammaNum(1-z, a));];20 # InternalLnGammaNum(_z, _a) <-- [ Local(e, k, tmpcoeff, coeff, result); a := Maximum(a, 4);  If (InVerboseMode(), Echo({\"InternalLnGammaNum: precision parameter = \", a})); e := N(Exp(1)); k:=Ceil(a);  result := 0;   While(k>?1) [  k:=k-1; result := N( PowerN(a-k,k)/((z+k)*Sqrt(a-k))-result/(e*k) ); ]; N(Ln(1+Exp(a-1)/Sqrt(2*Pi)*result) + Ln(2*Pi)/2 -a-z+(z+1/2)*Ln(z+a) - Ln(z));];InternalLnGammaNum(z) := [ Local(a, prec, result); prec := BuiltinPrecisionGet(); a:= Quotient((prec-IntLog(prec,10))*659, 526) + 0.4;     BuiltinPrecisionSet(Ceil(prec*1.4));  result := InternalLnGammaNum(z,a); BuiltinPrecisionSet(prec); result;];";
        scriptMap.put("InternalLnGammaNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "GammaConstNum() :=[ Local(k, n, A, B, Uold, U, Vold, V, prec, result); prec:=BuiltinPrecisionGet(); NonN([ BuiltinPrecisionSet(prec+IntLog(prec,10)+3);  n:= 1+Ceil(prec*0.5757+0.2862);  A:= -InternalLnNum(n); B:=1; U:=A; V:=1; k:=0; Uold := 0;  Vold := 0; While(Uold-U !=? 0 Or? Vold-V !=? 0) [ k++; Uold:=U; Vold:=V;  B:=MultiplyNum(B,n^2/k^2);   A:=MultiplyNum(MultiplyNum(A,n^2/k)+B, 1/k);  U:=U+A; V:=V+B; ]; If(InVerboseMode(), Echo(\"GammaConstNum: Info: used\", k, \"iterations at working precision\", BuiltinPrecisionGet())); result:=DivideN(U,V);  ]); BuiltinPrecisionSet(prec);  RoundTo(result, prec); ];";
        scriptMap.put("GammaConstNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "InternalZetaNum(_s, n_Integer?) <-- [ Local(result, j, sign); If (InVerboseMode(), Echo({\"InternalZetaNum: Borwein's method, precision \", BuiltinPrecisionGet(), \", n = \", n})); result := 0; sign := 1;  For(j:=0, j<=?2*n-1, j++) [  result := N(result + sign*InternalZetaNumCoeffEj(j,n)/(1+j)^s ); sign := -sign; ]; N(result/(2^n)/(1-2^(1-s)));];InternalZetaNum1(s, limit) := [ Local(i, sum); If (InVerboseMode(), Echo({\"InternalZetaNum: direct method (sum), precision \", BuiltinPrecisionGet(), \", N = \", limit})); sum := 0; limit := Ceil(N(limit)); For(i:=2, i<=?limit, i++) sum := sum+N(1/PowerN(i, s)); sum+1; ];InternalZetaNum2(s, limit) :=[ Local(i, prod); If (InVerboseMode(), Echo({\"InternalZetaNum: direct method (product), precision \", BuiltinPrecisionGet(), \", N = \", limit})); prod := N( (1-1/PowerN(2, s))*(1-1/PowerN(3,s)) ); limit := Ceil(N(limit)); For(i:=5, i<=?limit, i:= NextPrime(i)) prod := prod*N(1-1/PowerN(i, s)); 1/prod;];InternalZetaNumCoeffEj(j,n) := [ Local(k); 2^n-If(j<?n, 0, Sum(k,0,j-n,BinomialCoefficient(n,k))  );];Zeta3() :=[ Local(result, oldresult, k, term); N([ For( [ k:=1; result := 1; oldresult := -1; term := 1; ], oldresult!=?result, k++ ) [ oldresult := result; term := -term * k^2 / ((2*k+1)*(2*k)); result := result + term/(k+1)^2; ]; result := 5/4*result; ], BuiltinPrecisionGet()+IntLog(BuiltinPrecisionGet(),10)+1); result;];10 # InternalZetaNum(_s) _ (N(s)=?0) <-- -0.5;10 # InternalZetaNum(_s) _ (N(s)=?1) <-- Infinity;20 # InternalZetaNum(_s) <-- [ Local(n, prec, result); prec := BuiltinPrecisionGet(); If(  N(Re(s)) <? 0.5,  [ If(InVerboseMode(), Echo({\"InternalZetaNum: using s->1-s identity, s=\", s, \", precision \", prec})); result := 2*Exp(InternalLnGammaNum(1-s)-(1-s)*Ln(2*InternalPi()))*Sin(InternalPi()*s/2) * InternalZetaNum(1-s); ],  If (N(Re(s)) >? N(1+(prec*Ln(10))/(Ln(prec)+0.1), 6), [  n:= N(10^(prec/(s-1)), 6)+2;  BuiltinPrecisionSet(prec+2);  result := InternalZetaNum1(s, n); ], [  n := Ceil( N( prec*Ln(10)/Ln(8) + 2, 6 ) );  BuiltinPrecisionSet(prec+2);  result := InternalZetaNum(s, n); ] ) ); BuiltinPrecisionSet(prec); result;];";
        scriptMap.put("InternalZetaNum",scriptString);
        scriptMap.put("InternalZetaNum1",scriptString);
        scriptMap.put("InternalZetaNum2",scriptString);
        scriptMap.put("Zeta3",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "GeometricMean(list) := [ Check(List?(list), \"Argument\", \"Argument must be a list.\");  Product(list)^(1/Length(list));];";
        scriptMap.put("GeometricMean",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Mean(list) := [ Check(List?(list), \"Argument\", \"Argument must be a list.\");  Sum(list)/Length(list);];";
        scriptMap.put("Mean",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Median(list) :=[ Check(List?(list), \"Argument\", \"Argument must be a list.\");  Local(sx,n,n2);   sx := HeapSort(list,\"<?\");  n := Length(list);  n2 := (n>>1);  If(Modulo(n,2) =? 1, sx[n2+1], (sx[n2]+sx[n2+1])/2);];";
        scriptMap.put("Median",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "StandardDeviation(list) := [ Check(List?(list), \"Argument\", \"Argument must be a list.\"); Sqrt(UnbiasedVariance(list));];";
        scriptMap.put("StandardDeviation",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "UnbiasedVariance(list) := [ Check(List?(list), \"Argument\", \"Argument must be a list.\");  Sum((list - Mean(list))^2)/(Length(list)-1);];";
        scriptMap.put("UnbiasedVariance",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Variance(list) := [ Check(List?(list), \"Argument\", \"Argument must be a list.\"); Sum((list - Mean(list))^2)/Length(list);];";
        scriptMap.put("Variance",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BernoulliDistribution(p_RationalOrNumber?)_(p<?0 Or? p>?1) <-- Undefined;";
        scriptMap.put("BernoulliDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BinomialDistribution(_p, _n)_ (If(RationalOrNumber?(p),p<?0 Or? p>?1, False) Or? (Constant?(n) And? Not? PositiveInteger?(n)) ) <-- Undefined;";
        scriptMap.put("BinomialDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ChiSquareDistribution(m_RationalOrNumber?)_(m<=?0) <-- Undefined;";
        scriptMap.put("ChiSquareDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ContinuousUniformDistribution(a_RationalOrNumber?, b_RationalOrNumber?)_(a>=?b) <-- Undefined;";
        scriptMap.put("ContinuousUniformDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "DiscreteDistribution( dom_RationalOrNumber? , prob_RationalOrNumber?) <-- Undefined;";
        scriptMap.put("DiscreteDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "DiscreteUniformDistribution(a_RationalOrNumber?, b_RationalOrNumber?)_(a>=?b) <-- Undefined;";
        scriptMap.put("DiscreteUniformDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ExponentialDistribution(l_RationalOrNumber?)_(l<?0) <-- Undefined;";
        scriptMap.put("ExponentialDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "FDistribution(_n1, _n2) <-- Undefined;";
        scriptMap.put("FDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "GeometricDistribution(p_RationalOrNumber?)_(p<?0 Or? p>?1) <-- Undefined;";
        scriptMap.put("GeometricDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "HypergeometricDistribution(N_RationalOrNumber?, M_RationalOrNumber?, n_RationalOrNumber?)_(M >? N Or? n >? N) <-- Undefined;";
        scriptMap.put("HypergeometricDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "NormalDistribution( _m , s2_RationalOrNumber?)_(s2<=?0) <-- Undefined;";
        scriptMap.put("NormalDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "PoissonDistribution(l_RationalOrNumber?)_(l<=?0) <-- Undefined;";
        scriptMap.put("PoissonDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "tDistribution(m_RationalOrNumber?)_(Not? PositiveInteger?(m)) <-- Undefined;";
        scriptMap.put("tDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Retract(ChiSquareTest,*);10 # ChiSquareTest( observedFrequenciesMatrix_Matrix?, expectedFrequenciesMatrix_Matrix?) <--[ Local(observedFrequenciesList, expectedFrequenciesList);  observedFrequenciesList := Flatten(observedFrequenciesMatrix,\"List\");  expectedFrequenciesList := Flatten(expectedFrequenciesMatrix,\"List\");  Check(Length(observedFrequenciesList) >? 0, \"Argument\", \"The first argument must be a nonempty matrix.\");  Check(Length(expectedFrequenciesList) >? 0, \"Argument\", \"The second argument must be a nonempty matrix.\");  Check(Length(expectedFrequenciesList) =? Length(expectedFrequenciesList), \"Argument\", \"The matrices must be of equal length.\"); Local( numerator, chi2, pValue, categoriesCount, degreesOfFreedom, resultList);  resultList := {}; categoriesCount := Length(observedFrequenciesList); numerator := (observedFrequenciesList - expectedFrequenciesList)^2;  chi2 := Sum(i,1,categoriesCount,numerator[i]/(expectedFrequenciesList[i])); degreesOfFreedom := (Dimensions(observedFrequenciesMatrix)[1] - 1)*(Dimensions(observedFrequenciesMatrix)[2] - 1); pValue := 1-N(IncompleteGamma(degreesOfFreedom/2,chi2/2)/Gamma(degreesOfFreedom/2)); resultList[\"degreesOfFreedom\"] := degreesOfFreedom;  resultList[\"pValue\"] := pValue;  resultList[\"chiSquareScore\"] := chi2;  N(resultList);];20 # ChiSquareTest( observedFrequenciesList_List?, expectedFrequenciesList_List?) <--[ Check(Length(observedFrequenciesList) >? 0, \"Argument\", \"The first argument must be a nonempty list.\");  Check(Length(expectedFrequenciesList) >? 0, \"Argument\", \"The second argument must be a nonempty list.\");  Check(Length(expectedFrequenciesList) =? Length(expectedFrequenciesList), \"Argument\", \"The lists must be of equal length.\"); Local( numerator, chi2, pValue, categoriesCount, degreesOfFreedom, resultList);  resultList := {}; categoriesCount := Length(observedFrequenciesList); numerator := (observedFrequenciesList - expectedFrequenciesList)^2;  chi2 := Sum(i,1,categoriesCount,numerator[i]/(expectedFrequenciesList[i])); degreesOfFreedom := categoriesCount - 1; pValue := 1-N(IncompleteGamma(degreesOfFreedom/2,chi2/2)/Gamma(degreesOfFreedom/2)); resultList[\"degreesOfFreedom\"] := degreesOfFreedom;  resultList[\"pValue\"] := pValue;  resultList[\"chiSquareScore\"] := chi2;  N(resultList);];";
        scriptMap.put("ChiSquareTest",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Regress(x,y) :=[ Local(xy,x2,i,mx,my); mx := Mean(x); my := Mean(y); xy := Add((x-mx)*(y-my)); x2 := Add((x-mx)^2); {alpha <- (my-xy*mx/x2) , beta <- xy/x2};];";
        scriptMap.put("Regress",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ExpressionDepth(expression_Function?) <--[ Local(result); result:=0; ForEach(item,Rest(FunctionToList(expression))) [ Local(newresult); newresult:=ExpressionDepth(item); result:=Maximum(result,newresult); ]; result+1;];20 # ExpressionDepth(_expression) <-- 1;UnFence(\"ExpressionDepth\",1);";
        scriptMap.put("ExpressionDepth",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "2 # ArcCos(x_Number?)_InNumericMode() <-- InternalPi()/2-ArcSin(x); 200 # ArcCos(0) <-- Pi/2;200 # ArcCos(1/2) <-- Pi/3;200 # ArcCos(Sqrt(1/2)) <-- Pi/4;200 # ArcCos(Sqrt(3/4)) <-- Pi/6;200 # ArcCos(1) <-- 0;200 # ArcCos(_n)_(n =? -1) <-- Pi;200 # ArcCos(_n)_(-n =? Sqrt(3/4)) <-- 5/6*Pi;200 # ArcCos(_n)_(-n =? Sqrt(1/2)) <-- 3/4*Pi;200 # ArcCos(_n)_(-n =? 1/2) <-- 2/3*Pi;200 # ArcCos(Undefined) <-- Undefined;ArcCos(xlist_List?) <-- MapSingle(\"ArcCos\",xlist);110 # ArcCos(Complex(_r,_i)) <-- (- I)*Ln(Complex(r,i) + (Complex(r,i)^2 - 1)^(1/2));";
        scriptMap.put("ArcCos",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ArcCosh(_x)_(InNumericMode() And? (Number?(x) Or? Type(x) =? \"Complex\")) <-- N(Eval( Ln(x+Sqrt(x^2-1)) ));200 # ArcCosh(Infinity) <-- Infinity;200 # ArcCosh(-Infinity) <-- Infinity+I*Pi/2;200 # ArcCosh(Undefined) <-- Undefined;ArcCosh(xlist_List?) <-- MapSingle(\"ArcCosh\",xlist);";
        scriptMap.put("ArcCosh",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "2 # ArcSin(x_Number?)_(InNumericMode() And? Abs(x)<=?1) <-- ArcSinNum(x);3 # ArcSin(x_Number?)_InNumericMode() <-- Sign(x)*(Pi/2+I*ArcCosh(x));110 # ArcSin(Complex(_r,_i)) <-- (- I) * Ln((I*Complex(r,i)) + ((1-(Complex(r,i)^2))^(1/2)));150 # ArcSin(- _x)_(Not? Constant?(x)) <-- -ArcSin(x);160 # (ArcSin(x_Constant?))_(NegativeNumber?(N(Eval(x)))) <-- -ArcSin(-x);200 # ArcSin(0) <-- 0;200 # ArcSin(1/2) <-- Pi/6;200 # ArcSin(Sqrt(1/2)) <-- Pi/4;200 # ArcSin(Sqrt(3/4)) <-- Pi/3;200 # ArcSin(1) <-- Pi/2;200 # ArcSin(_n)_(n =? -1) <-- -Pi/2;200 # ArcSin(_n)_(-n =? Sqrt(3/4)) <-- -Pi/3;200 # ArcSin(_n)_(-n =? Sqrt(1/2)) <-- -Pi/4;200 # ArcSin(_n)_(-n =? 1/2) <-- -Pi/6;ArcSin(xlist_List?) <-- MapSingle(\"ArcSin\",xlist);200 # ArcSin(Undefined) <-- Undefined;";
        scriptMap.put("ArcSin",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ArcSinh(_x)_(InNumericMode() And? (Number?(x) Or? Type(x) =? \"Complex\")) <-- N(Eval( Ln(x+Sqrt(x^2+1)) ));200 # ArcSinh(Infinity) <-- Infinity;200 # ArcSinh(-Infinity) <-- -Infinity;200 # ArcSinh(Undefined) <-- Undefined;ArcSinh(xlist_List?) <-- MapSingle(\"ArcSinh\",xlist);";
        scriptMap.put("ArcSinh",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # (ArcTan(x_Constant?))_(NegativeNumber?(N(Eval(x)))) <-- -ArcTan(-x);4 # ArcTan(-Tan(_x)) <-- -ArcTan(Tan(x));110 # ArcTan(Complex(_r,_i)) <-- (- I*0.5)*Ln(Complex(1,Complex(r,i))/ Complex(1, - Complex(r,i)));150 # ArcTan(- _x)_(Not? Constant?(x)) <-- -ArcTan(x);160 # (ArcTan(x_Constant?))_(NegativeNumber?(N(Eval(x)))) <-- -ArcTan(-x);200 # ArcTan(Sqrt(3)) <-- Pi/3;200 # ArcTan(-Sqrt(3)) <-- -Pi/3;200 # ArcTan(1) <-- Pi/4;200 # ArcTan(0) <-- 0;200 # ArcTan(_n)_(n =? -1) <-- -Pi/4;200 # ArcTan(Infinity) <-- Pi/2;200 # ArcTan(-Infinity) <-- -Pi/2;200 # ArcTan(Undefined) <-- Undefined;ArcTan(xlist_List?) <-- MapSingle(\"ArcTan\",xlist);2 # ArcTan(x_Number?)_InNumericMode() <-- ArcTanNum(x);";
        scriptMap.put("ArcTan",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ArcTanh(_x)_(InNumericMode() And? (Number?(x) Or? Type(x) =? \"Complex\")) <-- N(Eval( Ln((1+x)/(1-x))/2 ));200 # ArcTanh(Infinity) <-- Infinity+I*Pi/2;200 # ArcTanh(-Infinity) <-- -Infinity-I*Pi/2; 200 # ArcTanh(Undefined) <-- Undefined;ArcTanh(xlist_List?) <-- MapSingle(\"ArcTanh\",xlist);";
        scriptMap.put("ArcTanh",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1 # CosMap( _n )_(Not?(RationalOrNumber?(n))) <-- ListToFunction({ToAtom(\"Cos\"),n*Pi});2 # CosMap( _n )_(n<?0) <-- CosMap(-n);2 # CosMap( _n )_(n>?2) <-- CosMap(Modulo(n,2));3 # CosMap( _n )_(n>?1) <-- CosMap(2-n);4 # CosMap( _n )_(n>?1/2) <-- -CosMap(1-n);5 # CosMap( 0 ) <-- 1;5 # CosMap( 1/6 ) <-- Sqrt(3)/2;5 # CosMap( 1/4 ) <-- Sqrt(2)/2;5 # CosMap( 1/3 ) <-- 1/2;5 # CosMap( 1/2 ) <-- 0;5 # CosMap( 2/5 ) <-- (Sqrt(5)-1)/4;10 # CosMap(_n) <-- ListToFunction({ToAtom(\"Cos\"),n*Pi});2 # Cos(x_Number?)_InNumericMode() <-- CosNum(x);4 # Cos(ArcCos(_x)) <-- x;4 # Cos(ArcSin(_x)) <-- Sqrt(1-x^2);4 # Cos(ArcTan(_x)) <-- 1/Sqrt(1+x^2);5 # Cos(- _x)_(Not? Constant?(x)) <-- Cos(x);6 # (Cos(x_Constant?))_(NegativeNumber?(N(Eval(x)))) <-- Cos(-x);110 # Cos(Complex(_r,_i)) <-- (Exp(I*Complex(r,i)) + Exp(- I*Complex(r,i))) / (2) ;6 # Cos(x_Infinity?) <-- Undefined;6 # Cos(Undefined) <-- Undefined;200 # Cos(v_CanBeUni(Pi))_(Not?(InNumericMode()) And? Degree(v,Pi) <? 2 And? Coef(v,Pi,0) =? 0) <-- CosMap(Coef(v,Pi,1));400 # Cos(x_RationalOrNumber?) <-- [ Local(ll); ll:= FloorN(N(Eval(x/Pi))); If(Even?(ll),x:=(x - Pi*ll),x:=(-x + Pi*(ll+1))); ListToFunction({Cos,x}); ];400 # Cos(x_RationalOrNumber?) <-- [ Local(ll); ll:= FloorN(N(Eval(Abs(x)/Pi))); If(Even?(ll),x:=(Abs(x) - Pi*ll),x:=(-Abs(x) + Pi*(ll+1))); ListToFunction({Cos,x}); ];Cos(xlist_List?) <-- MapSingle(\"Cos\",xlist);";
        scriptMap.put("Cos",scriptString);
        scriptMap.put("CosMap",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # Cosh(- _x) <-- Cosh(x);200 # Cosh(0) <-- 1;200 # Cosh(Infinity) <-- Infinity;200 # Cosh(-Infinity) <-- Infinity;200 # Cosh(ArcCosh(_x)) <-- x;200 # Cosh(ArcSinh(_x)) <-- Sqrt(1+x^2);200 # Cosh(ArcTanh(_x)) <-- 1/Sqrt(1-x^2);200 # Cosh(Undefined) <-- Undefined;Cosh(xlist_List?) <-- MapSingle(\"Cosh\",xlist);2 # Cosh(_x)_(InNumericMode() And? (Number?(x) Or? Type(x) =? \"Complex\")) <-- N(Eval( (Exp(x)+Exp(-x))/2 ));";
        scriptMap.put("Cosh",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "100 # Cot(_x) <-- 1/Tan(x);";
        scriptMap.put("Cot",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "100 # Coth(_x) <-- 1/Tanh(x);";
        scriptMap.put("Coth",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "100 # Csc(_x) <-- 1/Sin(x);";
        scriptMap.put("Csc",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "100 # Csch(_x) <-- 1/Sinh(x);";
        scriptMap.put("Csch",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "2 # Exp(x_Number?)_InNumericMode() <-- ExpNum(x);4 # Exp(Ln(_x)) <-- x;110 # Exp(Complex(_r,_i)) <-- Exp(r)*(Cos(i) + I*Sin(i));200 # Exp(0) <-- 1;200 # Exp(-Infinity) <-- 0;200 # Exp(Infinity) <-- Infinity;200 # Exp(Undefined) <-- Undefined;Exp(xlist_List?) <-- MapSingle(\"Exp\",xlist);";
        scriptMap.put("Exp",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "2 # Ln(0) <-- -Infinity;2 # Ln(1) <-- 0;2 # Ln(Infinity) <-- Infinity;2 # Ln(Undefined) <-- Undefined;2 # Ln(x_NegativeNumber?)_InNumericMode() <-- Complex(Ln(-x), Pi);3 # Ln(x_Number?)_(InNumericMode() And? x>=?1) <-- InternalLnNum(x);4 # Ln(Exp(_x)) <-- x;3 # Ln(Complex(_r,_i)) <-- Complex(Ln(Abs(Complex(r,i))), Arg(Complex(r,i)));4 # Ln(x_NegativeNumber?) <-- Complex(Ln(-x), Pi);5 # Ln(x_Number?)_(InNumericMode() And? x<?1) <-- - InternalLnNum(DivideN(1, x));Ln(xlist_List?) <-- MapSingle(\"Ln\",xlist);";
        scriptMap.put("Ln",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "100 # Sec(_x) <-- 1/Cos(x);";
        scriptMap.put("Sec",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "100 # Sech(_x) <-- 1/Cosh(x);";
        scriptMap.put("Sech",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1 # SinMap( _n )_(Not?(RationalOrNumber?(n))) <-- ListToFunction({ToAtom(\"Sin\"),n*Pi});2 # SinMap( _n )_(n<?0) <-- -SinMap(-n);2 # SinMap( _n )_(n>?2) <-- SinMap(Modulo(n,2));3 # SinMap( _n )_(n>?1) <-- SinMap(n-2);4 # SinMap( _n )_(n>?1/2) <-- SinMap(1-n);5 # SinMap( n_Integer? ) <-- 0;5 # SinMap( 1/6 ) <-- 1/2;5 # SinMap( 1/4 ) <-- Sqrt(2)/2;5 # SinMap( 1/3 ) <-- Sqrt(3)/2;5 # SinMap( 1/2 ) <-- 1;5 # SinMap( 1/10) <-- (Sqrt(5)-1)/4;10 # SinMap(_n) <-- ListToFunction({ToAtom(\"Sin\"),n*Pi});2 # Sin(x_Number?)_InNumericMode() <-- SinNum(x);4 # Sin(ArcSin(_x)) <-- x;4 # Sin(ArcCos(_x)) <-- Sqrt(1-x^2);4 # Sin(ArcTan(_x)) <-- x/Sqrt(1+x^2);5 # Sin(- _x)_(Not? Constant?(x)) <-- -Sin(x);6 # (Sin(x_Constant?))_(NegativeNumber?(N(Eval(x)))) <-- -Sin(-x);6 # Sin(x_Infinity?) <-- Undefined;6 # Sin(Undefined) <-- Undefined;110 # Sin(Complex(_r,_i)) <-- (Exp(I*Complex(r,i)) - Exp(- I*Complex(r,i))) / (I*2) ;200 # Sin(v_CanBeUni(Pi))_(Not?(InNumericMode()) And? Degree(v,Pi) <? 2 And? Coef(v,Pi,0) =? 0) <--[ SinMap(Coef(v,Pi,1));];Sin(xlist_List?) <-- MapSingle(\"Sin\",xlist);";
        scriptMap.put("Sin",scriptString);
        scriptMap.put("SinMap",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "2 # Sinh(_x)_(InNumericMode() And? (Number?(x) Or? Type(x) =? \"Complex\")) <-- N(Eval( (Exp(x)-Exp(-x))/2 ));5 # Sinh(- _x) <-- -Sinh(x);5 # Sinh(- _x) <-- -Sinh(x);200 # Sinh(0) <-- 0;200 # Sinh(Infinity) <-- Infinity;200 # Sinh(-Infinity) <-- -Infinity;200 # Sinh(ArcSinh(_x)) <-- x;200 # Sinh(ArcCosh(_x)) <-- Sqrt((x-1)/(x+1))*(x+1);200 # Sinh(ArcTanh(_x)) <-- x/Sqrt(1-x^2);200 # Sinh(Undefined) <-- Undefined;Sinh(xlist_List?) <-- MapSingle(\"Sinh\",xlist);";
        scriptMap.put("Sinh",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1 # TanMap( _n )_(Not?(RationalOrNumber?(n))) <-- ListToFunction({ToAtom(\"Tan\"),n*Pi});2 # TanMap( _n )_(n<?0) <-- -TanMap(-n);2 # TanMap( _n )_(n>?1) <-- TanMap(Modulo(n,1));4 # TanMap( _n )_(n>?1/2) <-- -TanMap(1-n);5 # TanMap( 0 ) <-- 0;5 # TanMap( 1/6 ) <-- 1/3*Sqrt(3);5 # TanMap( 1/4 ) <-- 1;5 # TanMap( 1/3 ) <-- Sqrt(3);5 # TanMap( 1/2 ) <-- Infinity;10 # TanMap(_n) <-- ListToFunction({ToAtom(\"Tan\"),n*Pi});2 # Tan(x_Number?)_InNumericMode() <-- TanNum(x);4 # Tan(ArcTan(_x)) <-- x;4 # Tan(ArcSin(_x)) <-- x/Sqrt(1-x^2);4 # Tan(ArcCos(_x)) <-- Sqrt(1-x^2)/x;5 # Tan(- _x)_(Not? Constant?(x)) <-- -Tan(x);6 # (Tan(x_Constant?))_(NegativeNumber?(N(Eval(x)))) <-- -Tan(-x);6 # Tan(Infinity) <-- Undefined;6 # Tan(Undefined) <-- Undefined;110 # Tan(Complex(_r,_i)) <-- Sin(Complex(r,i))/Cos(Complex(r,i));200 # Tan(v_CanBeUni(Pi))_(Not?(InNumericMode()) And? Degree(v,Pi) <? 2 And? Coef(v,Pi,0) =? 0) <-- TanMap(Coef(v,Pi,1));Tan(xlist_List?) <-- MapSingle(\"Tan\",xlist);";
        scriptMap.put("Tan",scriptString);
        scriptMap.put("TanMap",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "2 # Tanh(_x)_(InNumericMode() And? (Number?(x) Or? Type(x) =? \"Complex\")) <-- N(Eval( Sinh(x)/Cosh(x) ));200 # Tanh(0) <-- 0;200 # Tanh(Infinity) <-- 1;200 # Tanh(-Infinity) <-- -1;200 # Tanh(ArcTanh(_x)) <-- x;200 # Tanh(ArcSinh(_x)) <-- x/Sqrt(1+x^2);200 # Tanh(ArcCosh(_x)) <-- Sqrt((x-1)/(x+1))*(x+1)/x;200 # Tanh(Undefined) <-- Undefined;Tanh(xlist_List?) <-- MapSingle(\"Tanh\",xlist);";
        scriptMap.put("Tanh",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ArcCosN(x) :=[ FastArcCos(x);];";
        scriptMap.put("ArcCosN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"ArcSinN\",{int1})[ Local(result,eps); Bind(result,FastArcSin(int1)); Local(x,q,s,c); Bind(q,SubtractN(SinN(result),int1)); Bind(eps,MathIntPower(10,MathNegate(BuiltinPrecisionGet()))); While(GreaterThan?(AbsN(q),eps)) [ Bind(s,SubtractN(int1,SinN(result))); Bind(c,CosN(result)); Bind(q,DivideN(s,c)); Bind(result,AddN(result,q)); ]; result;];";
        scriptMap.put("ArcSinN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ArcTanN(x) :=[ FastArcTan(x);];";
        scriptMap.put("ArcTanN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ArcTanNTaylor(x) :=[ Local(numterms); numterms := QuotientN(BuiltinPrecisionGet()*1068, -643*MathBitCount(x))+1;   x*SumTaylorNum(-MultiplyN(x,x), {{k}, 1/(2*k+1)}, numterms);];";
        scriptMap.put("ArcTanNTaylor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " 10 # BisectSqrt(0) <-- 0;10 # BisectSqrt(1) <-- 1;20 # BisectSqrt(N_PositiveInteger?) <--[ Local(l2,u,v,u2,v2,uv2,n);  u := N; l2 := MathBitCount(u)-1;     l2 := l2>>1;  u := 1 << l2; u2 := u << l2;  While( l2 !=? 0 ) [ l2--;  v := 1<<l2; v2 := v<<l2;    uv2 := u<<(l2 + 1);   n := u2 + uv2 + v2;     if( n <=? N ) [ u := u+v;  u2 := n;  ]; ]; u; ];";
        scriptMap.put("BisectSqrt",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BitsToDigits(n, base) := FloorN(0.51+n*N(Ln(2)/Ln(base),10));";
        scriptMap.put("BitsToDigits",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CosNDoubling(value, n) :=[ Local(shift, result); shift := n; result := value; While (shift>?0)  [ result := MultiplyN(MathMul2Exp(result, 1), 2 - result); shift--; ]; result;];";
        scriptMap.put("CosNDoubling",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CosNTaylor(x) :=[ Local(numterms, prec, Bx); prec := QuotientN(BuiltinPrecisionGet()*3919, 1702);  Bx := -QuotientN(MathBitCount(x)*1143, 1649)-2;  numterms := QuotientN( QuotientN( prec-1, QuotientN( MathBitCount( prec-1)*1588, 2291)+Bx), 2)+1;  SumTaylorNum(MultiplyN(x,x), 1, {{k}, -1/(2*k*(2*k-1))}, numterms);];";
        scriptMap.put("CosNTaylor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "DigitsToBits(n, base) := FloorN(0.51+n*N(Ln(base)/Ln(2),10));";
        scriptMap.put("DigitsToBits",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ExpNDoubling1(value, n) :=[ Local(shift, result); shift := n; result := value; While (shift>?0)  [ result := MathMul2Exp(result, 1) + MultiplyN(result, result); shift--; ]; result;];";
        scriptMap.put("ExpNDoubling",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ExpNTaylor(x) :=[ Local(numterms, prec, Bx); prec := QuotientN(BuiltinPrecisionGet()*3919, 1702);  Bx := -QuotientN(MathBitCount(x)*1143, 1649)-2;  numterms := QuotientN( prec-1, QuotientN( MathBitCount( prec-1)*1588, 2291)+Bx)+1;  x*SumTaylorNum(x, 1, {{k}, 1/(k+1)}, numterms);];";
        scriptMap.put("ExpNTaylor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # MathBitCount(0) <-- 1;20 # MathBitCount(_x) _ (x<?0) <-- MathBitCount(-x);30 # MathBitCount(_value) <--[ Local(nbits); nbits:=0; If(value<?1, [  nbits := 1; While(value<?1) [ nbits--; value := MathMul2Exp(value,1); ]; ], [  While(value>=?1) [ nbits++; value := MathMul2Exp(value, -1); ]; ]); nbits;];";
        scriptMap.put("MathBitCount",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MathLnDoubling(value, n) :=[ Local(shift, result); shift := n; result := value; While (shift>?0)  [ result := MultiplyN(result, result); shift--; ]; result;];";
        scriptMap.put("MathLnDoubling",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MathLnTaylor(x) :=[ Local(numterms, y); y := x-1; numterms := QuotientN(BuiltinPrecisionGet()*2136, -643*MathBitCount(y))+1;   y*SumTaylorNum(-y, {{k}, 1/(k+1)}, numterms);];";
        scriptMap.put("MathLnTaylor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MathSqrtFloat(_A) <--[ Local(bitshift, a0, x0, x0sq, targetbits, subtargetbits, gotbits, targetprec); bitshift := ShiftRight(MathBitCount(A)-1,1);  targetprec := BuiltinPrecisionGet();  a0 := MathMul2Exp(A, -bitshift*2);  BuiltinPrecisionSet(10);    targetbits := Minimum(DigitsToBits(targetprec, 10), 1+GetExactBitsN(A));  x0 := DivideN(14+22*a0, 31+5*a0);  gotbits := 7;  subtargetbits := QuotientN(targetbits+8, 9); If(gotbits >=? subtargetbits, subtargetbits := QuotientN(targetbits+2, 3)); If(gotbits >=? subtargetbits, subtargetbits := targetbits*4);  While(gotbits <? targetbits) [ gotbits := 3*gotbits+1;   If(gotbits >=? subtargetbits, [  gotbits := subtargetbits; subtargetbits := targetbits*4;  ]); BuiltinPrecisionSet(BitsToDigits(gotbits, 10)+2);  x0 := SetExactBitsN(x0, gotbits+6);  x0sq := MultiplyN(x0, x0); x0 := AddN(x0, MultiplyN(x0*2, DivideN(a0-x0sq, a0+3*x0sq))); ];  x0 := SetExactBitsN(MathMul2Exp(x0, bitshift), gotbits); BuiltinPrecisionSet(targetprec); x0;];";
        scriptMap.put("MathSqrtFloat",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SinNTaylor(x) :=[ Local(numterms, prec, Bx); prec := QuotientN(BuiltinPrecisionGet()*3919, 1702);  Bx := -QuotientN(MathBitCount(x)*1143, 1649)-2;  numterms := QuotientN( QuotientN( prec+Bx, QuotientN( MathBitCount( prec+Bx)*1588, 2291)+Bx)+1, 2)+1;  x*SumTaylorNum(MultiplyN(x,x), 1, {{k}, -1/(2*k*(2*k+1))}, numterms);];";
        scriptMap.put("SinNTaylor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SinNTripling(value, n) :=[ Local(shift, result); shift := n; result := value; While (shift>?0)  [  result := MultiplyN(result, 3 - MathMul2Exp(MultiplyN(result,result), 2) ); shift--; ]; result;];";
        scriptMap.put("SinNTripling",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SqrtN(x) := MathSqrt1(x); 10 # MathSqrt1(0) <-- 0;100 # MathSqrt1(_x) <-- [ Echo(\"SqrtN: invalid argument: \", x); False;];30 # MathSqrt1(x_PositiveNumber?) <-- x*NewtonNum({{r}, r+r*(1-x*r^2)/2}, FastPower(x,-0.5), 4, 2);30 # MathSqrt1(x_PositiveNumber?) <-- MathSqrtFloat(x);20 # MathSqrt1(x_Integer?) _ (GreaterThan?(x,0)) <--[ Local(result); If(ModuloN(x,4)<?2 And? ModuloN(x,3)<?2 And? ModuloN(x+1,5)<?3,  [  GlobalPush(BuiltinPrecisionGet()); If(MathBitCount(x)+3>?DigitsToBits(BuiltinPrecisionGet(), 10), BuiltinPrecisionSet(BitsToDigits(MathBitCount(x), 10)+1));    result := MathSqrtFloat(x+0.);  If(FloatIsInt?(SetExactBitsN(result, GetExactBitsN(result)-3)), result:= Floor(result+0.5)); BuiltinPrecisionSet(GlobalPop()); ],  result := MathSqrtFloat(x+0.) );  SetExactBitsN(result, DigitsToBits(BuiltinPrecisionGet(),10));];";
        scriptMap.put("SqrtN",scriptString);
        scriptMap.put("MathSqrt1",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ArcSinNum(x) :=[  If( 239*Abs(x) >=? 169,   Sign(x)*(InternalPi()/2-ArcSinN(Sqrt(1-x^2))), ArcSinN(x) );];";
        scriptMap.put("ArcSinNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ArcTanNum(x) :=[  If( Abs(x)>?1, Sign(x)*(InternalPi()/2-ArcSin(1/Sqrt(x^2+1))), ArcSin(x/Sqrt(x^2+1)) );];";
        scriptMap.put("ArcTanNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(BrentCacheOfConstantsN)[ CachedConstant(BrentCacheOfConstantsN, Ln2, InternalLnNum(2)); ];10 # BrentLn(x_Integer?)_(BuiltinPrecisionGet()>?40) <--[ Local(y, n, k, eps); n := BuiltinPrecisionGet();   k := 1 + Quotient(IntLog(4*10^n, x), 2);  BuiltinPrecisionSet(n+5);  eps := DivideN(1, 10^n);  y := PowerN(x, k);   y := DivideN(InternalPi()*y, (2*k)*ArithmeticGeometricMean(4, y, eps)); BuiltinPrecisionSet(n); RoundTo(y, n); ];15 # BrentLn(x_Integer?) <-- LogN(x);20 # BrentLn(_x)_(x<?1) <-- -BrentLn(1/x);30 # BrentLn(_x)_(BuiltinPrecisionGet()>?85) <--[ Local(y, n, n1, k, eps); N([ n := BuiltinPrecisionGet();   n1 := n + IntLog(n,10);   k := 2 + Quotient(n1*28738, 2*8651)   - IntLog(Floor(x), 2);  BuiltinPrecisionSet(n1+2+Quotient(k*3361, 11165));  eps := DivideN(1, 10^(n1+1));  y := x*2^(k-2);    y:=InternalPi()*y/(2*ArithmeticGeometricMean(1,y,eps)) - k*Ln2(); BuiltinPrecisionSet(n); ]); y; ];40 # BrentLn(x_Number?) <-- LogN(x);";
        scriptMap.put("BrentLn",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CosNum(x) :=[ If(x<?0 Or? 113*x>?710, x:=TruncRadian(x)); CosN(x);];";
        scriptMap.put("CosNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ExpNum(x_Number?) _ (x >? MathExpThreshold()) <-- [ Local(i, y); i:=0; For(i:=0, x >? MathExpThreshold(), i++) x := DivideN(x, 2.); For(y:= ExpN(x), i>?0, i--) y := MultiplyN(y, y); y;];20 # ExpNum(x_Number?) _ (2*x <? -MathExpThreshold()) <-- DivideN(1, ExpNum(-x));30 # ExpNum(x_Number?) <-- ExpN(x);";
        scriptMap.put("ExpNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "InternalLnNum(x_Number?)_(x>=?1) <-- NewtonLn(x);InternalLnNum(x_Number?)_(0<?x And? x<?1) <-- - InternalLnNum(DivideN(1,x));";
        scriptMap.put("InternalLnNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LogN(x) := InternalLnNum(x);";
        scriptMap.put("LogN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "NewtonLn(x) := LocalSymbols(y)[ NewtonNum({{y}, 4*x/(ExpNum(y)+x)-2+y},  DivideN(794*IntLog(Floor(x*x), 2), 2291), 10, 3);];";
        scriptMap.put("NewtonLn",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SinNum(x) :=[ If(x<?0 Or? 113*x>?710, x:=TruncRadian(x));  SinN(x);];";
        scriptMap.put("SinNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TanNum(x) :=[ If(x<?0 Or? 113*x>?710, x:=TruncRadian(x)); TanN(x);];";
        scriptMap.put("TanNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TruncRadian(_r) <--[ Local(twopi);  N([ r:=Eval(r); twopi:=2*InternalPi(); r:=r-FloorN(r/twopi)*twopi; ], BuiltinPrecisionGet() + IntLog(Ceil(Abs(N(Eval(r), 1))), 10)); r;];HoldArgument(\"TruncRadian\",r);";
        scriptMap.put("TruncRadian",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(mathExpThreshold) [  mathExpThreshold := If(Not? Bound?(mathExpThreshold), 500); MathExpThreshold() := mathExpThreshold; SetMathExpThreshold(threshold) := [mathExpThreshold:= threshold; ];];";
        scriptMap.put("MathExpThreshold",scriptString);
        scriptMap.put("SetMathExpThreshold",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ArithmeticGeometricMean(a, b, eps) :=[ Check(PositiveReal?(a) And? PositiveReal?(b), \"Argument\", \"The first two arguments must be positive real numbers.\");  Check(PositiveInteger?(eps), \"Argument\", \"The precision argument must be a positive integer.\");  a := N(a, eps);  b := N(b, eps);  Local(a1, b1);  If(InVerboseMode(), Echo(\"ArithmeticGeometricMean: Info: at prec. \", BuiltinPrecisionGet()));   While(Abs(a-b)>=?eps) [ a1 := DivideN(a+b, 2); b1 := SqrtN(MultiplyN(a, b));  a := a1; b := b1; ];  DivideN(a+b, 2);];";
        scriptMap.put("ArithmeticGeometricMean",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # IntPowerNum(_x, 0, _func, _unity) <-- unity;10 # IntPowerNum(_x, n_Integer?, _func, _unity) <--[  Local(result);  While(n >? 0) [ If( (n&1) =? 1, If( Bound?(result),  result := Apply(func, {result,x}), result := x,  ) ); x := Apply(func, {x,x}); n := n>>1; ]; result;];";
        scriptMap.put("IntPowerNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function() MultiplyNum(x, y, ...);Function() MultiplyNum(x);10 # MultiplyNum(x_List?)_(Length(x)>?1) <-- MultiplyNum(First(x), Rest(x));10 # MultiplyNum(x_Rational?, y_RationalOrNumber?) <--[ If( Type(y) =? \"/\",  DivideN(Numerator(x)*Numerator(y), Denominator(x)*Denominator(y)),   If( Numerator(x)=?1, DivideN(y, Denominator(x)), If( Denominator(x)=?1, MultiplyN(y, Numerator(x)), DivideN(MultiplyN(y, Numerator(x)), Denominator(x)) ) ) );];20 # MultiplyNum(x_Number?, y_Rational?) <-- MultiplyNum(y, x);25 # MultiplyNum(x_Number?, y_Number?) <-- MultiplyN(x,y);30 # MultiplyNum(Complex(r_Number?, i_Number?), y_RationalOrNumber?) <-- Complex(MultiplyNum(r, y), MultiplyNum(i, y));35 # MultiplyNum(y_Number?, Complex(r_Number?, i_RationalOrNumber?)) <-- MultiplyNum(Complex(r, i), y);40 # MultiplyNum(Complex(r1_Number?, i1_Number?), Complex(r2_Number?, i2_Number?)) <-- Complex(MultiplyNum(r1,r2)-MultiplyNum(i1,i2), MultiplyNum(r1,i2)+MultiplyNum(i1,r2));30 # MultiplyNum(x_RationalOrNumber?, y_NumericList?)_(Length(y)>?1) <-- MultiplyNum(MultiplyNum(x, First(y)), Rest(y));40 # MultiplyNum(x_RationalOrNumber?, y_NumericList?)_(Length(y)=?1) <-- MultiplyNum(x, First(y));";
        scriptMap.put("MultiplyNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Commondigits(x,y) :=[ Local(diff); diff := Abs(x-y); If( diff=?0, Infinity,  Quotient(IntLog(FloorN(DivideN(Maximum(Abs(x), Abs(y)), diff)), 2)*351, 1166) ); ];NewtonNum(_func, _x0) <-- NewtonNum(func, x0, 5); NewtonNum(_func, _x0, _prec0) <-- NewtonNum(func, x0, prec0, 2);NewtonNum(_func, _xinit, _prec0, _order) <--[ Check(prec0>=?4, \"Argument\", \"NewtonNum: Error: initial precision must be at least 4\"); Check(Integer?(order) And? order>?1, \"Argument\", \"NewtonNum: Error: convergence order must be an integer and at least 2\"); Local(x0, x1, prec, exactdigits, intpart, initialtries); N([ x0 := xinit; prec := BuiltinPrecisionGet(); intpart := IntLog(Ceil(Abs(x0)), 10);   BuiltinPrecisionSet(2+prec0-intpart);  x1 := (func @ x0);   exactdigits := 0; initialtries := 5;  While(exactdigits*order <? prec0 And? initialtries>?0) [ initialtries--; x0 := x1; x1 := (func @ x0); exactdigits := Commondigits(x0, x1);  ];  If( Assert(\"value\", {\"NewtonNum: Error: need a more accurate initial value than\", xinit}) exactdigits >=? 1, [ exactdigits :=Minimum(exactdigits, prec0+2);  intpart := IntLog(Ceil(Abs(x1)), 10);  While(exactdigits*order <=? prec) [ exactdigits := exactdigits*order; BuiltinPrecisionSet(2+Minimum(exactdigits, Quotient(prec,order)+1)-intpart); x0 := x1; x1 := (func @ x0);  ];  BuiltinPrecisionSet(2+prec); x1 := RoundTo( (func @ x1), prec); ],  x1 := xinit ); BuiltinPrecisionSet(prec); ]); x1;];";
        scriptMap.put("NewtonNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SumTaylorNum0(_x, _nthtermfunc, _nterms) <-- SumTaylorNum0(x, nthtermfunc, {}, nterms);SumTaylorNum1(_x, _nthtermfunc, _nterms) <-- SumTaylorNum1(x, nthtermfunc, {}, nterms);SumTaylorNum(_x, _nthtermfunc, _nterms) <--If( nterms >=? 30,   SumTaylorNum1(x, nthtermfunc, nterms), SumTaylorNum0(x, nthtermfunc, nterms));SumTaylorNum(_x, _nthtermfunc, _nexttermfactor, _nterms) <--If( nterms >=? 5,  SumTaylorNum1(x, nthtermfunc, nexttermfactor, nterms), SumTaylorNum0(x, nthtermfunc, nexttermfactor, nterms));1# SumTaylorNum0(_x, _nthtermfunc, {}, _nterms) <--[ Local(sum, k); N([  x:=Eval(x); sum := 0; For(k:=nterms, k>=?0, k--) sum := AddN(sum*x, nthtermfunc @ k); ]); sum;];2# SumTaylorNum0(_x, _nthtermfunc, _nexttermfactor, _nterms) <--[ Local(sum, k, term, delta); N([ x:=Eval(x);  If (Constant?(nthtermfunc), term := nthtermfunc, term := (nthtermfunc @ {0}), ); sum := term;  ]); NonN([ delta := 1; For(k:=1, k<=?nterms And? delta !=? 0, k++) [ term := MultiplyNum(term, nexttermfactor @ {k}, x);  delta := sum; sum := sum + term;  delta := Abs(sum-delta);  ]; ]); sum;];SumTaylorNum0(_x, _nthtermfunc, _nterms) <-- SumTaylorNum0(x, nthtermfunc, {}, nterms);SumTaylorNum1(x, nthtermfunc, nexttermfactor, nterms) :=[  Local(sum, rows, cols, rowstmp, lastpower, i, j, xpower, termtmp); N([  x:=Eval(x);  rows := IntNthRoot(nterms+1, 2); cols := Quotient(nterms+rows, rows);  Check(rows>?1 And? cols>?1, \"Argument\", \"SumTaylorNum1: Internal error: number of Taylor sum terms must be at least 4\"); rowstmp := ArrayCreate(rows, 0); xpower := x ^ rows;      If( nexttermfactor =? {}, termtmp := 1,  If (Constant?(nthtermfunc), termtmp := nthtermfunc, termtmp := (nthtermfunc @ {0}), ) ); ]); NonN([   For(i:=0, i<?cols, i++) [  For(j:=0, j<?rows And? (i<?cols-1 Or? i*rows+j<=?nterms), j++)  [   If( nexttermfactor =? {},  [ rowstmp[j+1] := rowstmp[j+1] + MultiplyNum(termtmp, nthtermfunc @ {i*rows+j}); ], [ rowstmp[j+1] := rowstmp[j+1] + termtmp;  termtmp := MultiplyNum(termtmp, nexttermfactor @ {i*rows+j+1});  ] ); ];  termtmp := termtmp*xpower;  ];   For([j:=rows; sum:=0;], j>?0, j--) sum := sum*x + rowstmp[j]; ]); sum;];";
        scriptMap.put("SumTaylorNum",scriptString);
        scriptMap.put("SumTaylorNum0",scriptString);
        scriptMap.put("SumTaylorNum1",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BinSplitNum(m,n,a,b,p,q) := BinSplitFinal(BinSplitData(m,n,a,b,p,q));BinSplitFinal({_P,_Q,_B,_T}) <-- DivideN(T, MultiplyN(B, Q));BinSplitCombine({_P1, _Q1, _B1, _T1}, {_P2, _Q2, _B2, _T2}) <-- {P1*P2, Q1*Q2, B1*B2, B1*P1*T2+B2*Q2*T1};10 # BinSplitData(_m, _n, _a, _b, _p, _q)_(m>?n) <-- {1,1,1,0};10 # BinSplitData(_m, _n, _a, _b, _p, _q)_(m=?n) <-- {p@m, q@m, b@m, (a@m)*(p@m)};10 # BinSplitData(_m, _n, _a, _b, _p, _q)_(m+1=?n) <-- {(p@m)*(p@n), (q@m)*(q@n), (b@m)*(b@n), (p@m)*((a@m)*(b@n)*(q@n)+(a@n)*(b@m)*(p@n))};20 # BinSplitData(_m, _n, _a, _b, _p, _q) <--[ BinSplitCombine(BinSplitData(m,(m+n)>>1, a,b,p,q), BinSplitData(1+((m+n)>>1),n, a,b,p,q));];";
        scriptMap.put("BinSplitNum",scriptString);
        scriptMap.put("BinSplitData",scriptString);
        scriptMap.put("BinSplitFinal",scriptString);
        scriptMap.put("BinSplitCombine",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Abs(Infinity) <-- Infinity; 10 # Abs(n_Number?) <-- AbsN(n);10 # Abs(n_PositiveNumber?/m_PositiveNumber?) <-- n/m;10 # Abs(n_NegativeNumber?/m_PositiveNumber?) <-- (-n)/m;10 # Abs(n_PositiveNumber?/m_NegativeNumber?) <-- n/(-m);10 # Abs( Sqrt(_x)) <-- Sqrt(x);10 # Abs(-Sqrt(_x)) <-- Sqrt(x);10 # Abs(Complex(_r,_i)) <-- Sqrt(r^2 + i^2);10 # Abs(n_Infinity?) <-- Infinity;10 # Abs(Undefined) <-- Undefined;20 # Abs(n_List?) <-- MapSingle(\"Abs\",n);100 # Abs(_a^_n) <-- Abs(a)^n;";
        scriptMap.put("Abs",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1 # CanonicalAdd((_a+_b)+_c) <-- CanonicalAdd(CanonicalAdd(a)+ CanonicalAdd(CanonicalAdd(b)+ CanonicalAdd(c)));1 # CanonicalAdd((_a-_b)+_c) <-- CanonicalAdd(CanonicalAdd(a)+ CanonicalAdd(CanonicalAdd(c)- CanonicalAdd(b)));1 # CanonicalAdd((_a+_b)-_c) <-- CanonicalAdd(CanonicalAdd(a)+ CanonicalAdd(CanonicalAdd(b)- CanonicalAdd(c)));1 # CanonicalAdd((_a-_b)-_c) <-- CanonicalAdd(CanonicalAdd(a)- CanonicalAdd(CanonicalAdd(b)+ CanonicalAdd(c)));2 # CanonicalAdd(_a) <-- a;";
        scriptMap.put("CanonicalAdd",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # Ceil(Infinity) <-- Infinity;5 # Ceil(-Infinity) <-- -Infinity;5 # Ceil(Undefined) <-- Undefined;10 # Ceil(x_RationalOrNumber?) <-- [ x:=N(x); Local(prec,result,n); Bind(prec,BuiltinPrecisionGet()); If(Zero?(x),Bind(n,2), If(x>?0, Bind(n,2+FloorN(N(FastLog(x)/FastLog(10)))), Bind(n,2+FloorN(N(FastLog(-x)/FastLog(10)))) )); If(n>?prec,BuiltinPrecisionSet(n)); Bind(result,CeilN(x)); BuiltinPrecisionSet(prec); result; ];";
        scriptMap.put("Ceil",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Expand(expr_CanBeUni) <-- NormalForm(MakeUni(expr));20 # Expand(_expr) <-- expr;10 # Expand(expr_CanBeUni(var),_var) <-- NormalForm(MakeUni(expr,var));20 # Expand(_expr,_var) <-- expr;";
        scriptMap.put("Expand",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # Floor(Infinity) <-- Infinity;5 # Floor(-Infinity) <-- -Infinity;5 # Floor(Undefined) <-- Undefined;10 # Floor(x_RationalOrNumber?) <-- [ x:=N(Eval(x)); Local(prec,result,n); Bind(prec,BuiltinPrecisionGet()); If(Zero?(x), Bind(n,2), If(x>?0, Bind(n,2+FloorN(N(FastLog(x)/FastLog(10)))), Bind(n,2+FloorN(N(FastLog(-x)/FastLog(10)))) )); If(n>?prec,BuiltinPrecisionSet(n)); Bind(result,FloorN(x)); BuiltinPrecisionSet(prec); result; ];";
        scriptMap.put("Floor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "0 # Gcd(0,0) <-- 1;1 # Gcd(0,_m) <-- Abs(m);1 # Gcd(_n,0) <-- Abs(n);1 # Gcd(_m,_m) <-- Abs(m);2 # Gcd(_n,1) <-- 1;2 # Gcd(1,_m) <-- 1;2 # Gcd(n_Integer?,m_Integer?) <-- GcdN(n,m);3 # Gcd(_n,_m)_(GaussianInteger?(m) And? GaussianInteger?(n) )<-- GaussianGcd(n,m);4 # Gcd(-(_n), (_m)) <-- Gcd(n,m);4 # Gcd( (_n),-(_m)) <-- Gcd(n,m);4 # Gcd(Sqrt(n_Integer?),Sqrt(m_Integer?)) <-- Sqrt(Gcd(n,m));4 # Gcd(Sqrt(n_Integer?),m_Integer?) <-- Sqrt(Gcd(n,m^2));4 # Gcd(n_Integer?,Sqrt(m_Integer?)) <-- Sqrt(Gcd(n^2,m));5 # Gcd(n_Rational?,m_Rational?) <--[ Gcd(Numerator(n),Numerator(m))/Lcm(Denominator(n),Denominator(m));];10 # Gcd(list_List?)_(Length(list)>?2) <-- [ Local(first); first:=Gcd(list[1],list[2]); Gcd(first~Rest(Rest(list))); ];14 # Gcd({0}) <-- 1;15 # Gcd({_head}) <-- head;20 # Gcd(list_List?)_(Length(list)=?2) <-- Gcd(list[1],list[2]);30 # Gcd(n_CanBeUni,m_CanBeUni)_(Length(VarList(n*m))=?1) <--[ Local(vars); vars:=VarList(n+m); NormalForm(Gcd(MakeUni(n,vars),MakeUni(m,vars)));];100 # Gcd(n_Constant?,m_Constant?) <-- 1;110 # Gcd(_m,_n) <--[ Echo(\"Not simplified\");];0 # Gcd(n_UniVar?,m_UniVar?)_ (n[1] =? m[1] And? Degree(n) <? Degree(m)) <-- Gcd(m,n);1 # Gcd(nn_UniVar?,mm_UniVar?)_ (nn[1] =? mm[1] And? Degree(nn) >=? Degree(mm)) <--[ UniVariate(nn[1],0, UniGcd(Concat(ZeroVector(nn[2]),nn[3]), Concat(ZeroVector(mm[2]),mm[3])));];";
        scriptMap.put("Gcd",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # Lcm(a_Integer?,b_Integer?) <-- Quotient(a*b,Gcd(a,b));10 # Lcm(list_List?)_(Length(list)>?2) <--[ Local(first); first:=Lcm(list[1],list[2]); Lcm(first~Rest(Rest(list)));];10 # Lcm(list_List?)_(Length(list)=?2) <-- Lcm(list[1],list[2]);10 # Lcm(list_List?)_(Length(list)=?1) <-- Lcm(list[1],1);";
        scriptMap.put("Lcm",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LnCombine(_a) <-- DoLnCombine(CanonicalAdd(a));1 # DoLnCombine(Ln(_a)) <-- Ln(a);1 # DoLnCombine(Ln(_a)*_b) <-- Ln(a^b);1 # DoLnCombine(_b*Ln(_a)) <-- Ln(a^b);2 # DoLnCombine(Ln(_a)*_b+_c) <-- DoLnCombine(Ln(a^b)+c);2 # DoLnCombine(Ln(_a)*_b-_c) <-- DoLnCombine(Ln(a^b)-c);2 # DoLnCombine(_b*Ln(_a)+_c) <-- DoLnCombine(Ln(a^b)+c);2 # DoLnCombine(_b*Ln(_a)-_c) <-- DoLnCombine(Ln(a^b)-c);2 # DoLnCombine(_a+(_c*Ln(_b))) <-- DoLnCombine(a+Ln(b^c));2 # DoLnCombine(_a-(_c*Ln(_b))) <-- DoLnCombine(a-Ln(b^c));2 # DoLnCombine(_a+(Ln(_b)*_c)) <-- DoLnCombine(a+Ln(b^c));2 # DoLnCombine(_a-(Ln(_b)*_c)) <-- DoLnCombine(a-Ln(b^c));2 # DoLnCombine(_a+((Ln(_b)*_c)+_d)) <-- DoLnCombine(a+(Ln(b^c)+d));2 # DoLnCombine(_a+((Ln(_b)*_c)-_d)) <-- DoLnCombine(a+(Ln(b^c)-d));2 # DoLnCombine(_a-((Ln(_b)*_c)+_d)) <-- DoLnCombine(a-(Ln(b^c)+d));2 # DoLnCombine(_a-((Ln(_b)*_c)-_d)) <-- DoLnCombine(a-(Ln(b^c)-d));2 # DoLnCombine(_a+((_c*Ln(_b))+_d)) <-- DoLnCombine(a+(Ln(b^c)+d));2 # DoLnCombine(_a+((_c*Ln(_b))-_d)) <-- DoLnCombine(a+(Ln(b^c)-d));2 # DoLnCombine(_a-((_c*Ln(_b))+_d)) <-- DoLnCombine(a-(Ln(b^c)+d));2 # DoLnCombine(_a-((_c*Ln(_b))-_d)) <-- DoLnCombine(a-(Ln(b^c)-d));3 # DoLnCombine(Ln(_a)+Ln(_b)) <-- Ln(a*b);3 # DoLnCombine(Ln(_a)-Ln(_b)) <-- Ln(a/b);3 # DoLnCombine(Ln(_a)+(Ln(_b)+_c)) <-- DoLnCombine(Ln(a*b)+c);3 # DoLnCombine(Ln(_a)+(Ln(_b)-_c)) <-- DoLnCombine(Ln(a*b)-c);3 # DoLnCombine(Ln(_a)-(Ln(_b)+_c)) <-- DoLnCombine(Ln(a/b)-c);3 # DoLnCombine(Ln(_a)-(Ln(_b)-_c)) <-- DoLnCombine(Ln(a/b)+c);4 # DoLnCombine(Ln(_a)+(_b+_c)) <-- b+DoLnCombine(Ln(a)+c);4 # DoLnCombine(Ln(_a)+(_b-_c)) <-- b+DoLnCombine(Ln(a)-c);4 # DoLnCombine(Ln(_a)-(_b+_c)) <-- DoLnCombine(Ln(a)-c)-b;4 # DoLnCombine(Ln(_a)-(_b-_c)) <-- DoLnCombine(Ln(a)+c)-b;4 # DoLnCombine(_a+(Ln(_b)+_c)) <-- a+DoLnCombine(Ln(b)+c);4 # DoLnCombine(_a+(Ln(_b)-_c)) <-- a+DoLnCombine(Ln(b)-c);4 # DoLnCombine(_a-(Ln(_b)+_c)) <-- a-DoLnCombine(Ln(b)+c);4 # DoLnCombine(_a-(Ln(_b)-_c)) <-- a-DoLnCombine(Ln(b)-c);5 # DoLnCombine(_a+(_b+_c)) <-- a+(b+DoLnCombine(c));6 # DoLnCombine(_a) <-- a;";
        scriptMap.put("LnCombine",scriptString);
        scriptMap.put("DoLnCombine",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1 # LnExpand(Ln(x_Integer?)) <-- Add(Map({{n,m},m*Ln(n)},Transpose(Factors(x))));1 # LnExpand(Ln(_a*_b)) <-- LnExpand(Ln(a))+LnExpand(Ln(b));1 # LnExpand(Ln(_a/_b)) <-- LnExpand(Ln(a))-LnExpand(Ln(b));1 # LnExpand(Ln(_a^_n)) <-- LnExpand(Ln(a))*n;2 # LnExpand(_a) <-- a;";
        scriptMap.put("LnExpand",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "0 # Modulo(_n,m_RationalOrNumber?)_(m<?0) <-- `Hold(Modulo(@n,@m));1 # Modulo(n_NegativeInteger?,m_PositiveInteger?) <--[ Local(result); result := ModuloN(n,m); If (result <? 0,result := result + m); result;];1 # Modulo(n_PositiveInteger?,m_PositiveInteger?) <-- ModuloN(n,m);2 # Modulo(0,_m) <-- 0;2 # Modulo(n_PositiveInteger?,Infinity) <-- n;3 # Modulo(n_Integer?,m_Integer?) <-- ModuloN(n,m);4 # Modulo(n_Number?,m_Number?) <-- NonN(Modulo(Rationalize(n),Rationalize(m)));5 # Modulo(n_RationalOrNumber?,m_RationalOrNumber?) <--[ Local(n1,n2,m1,m2); n1:=Numerator(n); n2:=Denominator(n); m1:=Numerator(m); m2:=Denominator(m); Modulo(n1*m2,m1*n2)/(n2*m2);];6 # Modulo(n_List?,m_List?) <-- Map(\"Modulo\",{n,m});7 # Modulo(n_List?,_m) <-- Map(\"Modulo\",{n,FillList(m,Length(n))});30 # Modulo(n_CanBeUni,m_CanBeUni) <--[ Local(vars); vars:=VarList(n+m); NormalForm(Modulo(MakeUni(n,vars),MakeUni(m,vars)));];0 # Modulo(n_UniVar?,m_UniVar?)_(Degree(n) <? Degree(m)) <-- n;1 # Modulo(n_UniVar?,m_UniVar?)_ (n[1] =? m[1] And? Degree(n) >=? Degree(m)) <--[ UniVariate(n[1],0, UniDivide(Concat(ZeroVector(n[2]),n[3]), Concat(ZeroVector(m[2]),m[3]))[2]);];10 # Modulo(n_CanBeUni, m_CanBeUni, vars_List?)_(Length(vars)=?1) <--[ NormalForm(Modulo(MakeUni(n,vars),MakeUni(m,vars)));];";
        scriptMap.put("Modulo",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Object\",{pred,x});RuleHoldArguments(\"Object\",2,0,Apply(pred,{x})=?True) x;";
        scriptMap.put("Object",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "0 # Quotient(n_Integer?,m_Integer?) <-- QuotientN(n,m);1 # Quotient(0 ,_m) <-- 0;2 # Quotient(n_RationalOrNumber?,m_RationalOrNumber?) <--[ Local(n1,n2,m1,m2,sgn1,sgn2); n1:=Numerator(n); n2:=Denominator(n); m1:=Numerator(m); m2:=Denominator(m); sgn1 := Sign(n1*m2); sgn2 := Sign(m1*n2); sgn1*sgn2*Floor(DivideN(sgn1*n1*m2,sgn2*m1*n2));];30 # Quotient(n_CanBeUni,m_CanBeUni)_(Length(VarList(n*m))=?1) <--[ Local(vars,nl,ml); vars:=VarList(n*m); nl := MakeUni(n,vars); ml := MakeUni(m,vars); NormalForm(Quotient(nl,ml));];0 # Quotient(n_UniVar?,m_UniVar?)_(Degree(n) <? Degree(m)) <-- 0;1 # Quotient(n_UniVar?,m_UniVar?)_ (n[1] =? m[1] And? Degree(n) >=? Degree(m)) <--[ UniVariate(n[1],0, UniDivide(Concat(ZeroVector(n[2]),n[3]), Concat(ZeroVector(m[2]),m[3]))[1]);];";
        scriptMap.put("Quotient",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "0 # Rem(n_Number?,m_Number?) <-- n-m*Quotient(n,m);30 # Rem(n_CanBeUni,m_CanBeUni) <-- Modulo(n,m);";
        scriptMap.put("Rem",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # Round(Infinity) <-- Infinity;5 # Round(-Infinity) <-- -Infinity;5 # Round(Undefined) <-- Undefined;10 # Round(x_RationalOrNumber?) <-- FloorN(N(x+0.5));10 # Round(x_List?) <-- MapSingle(\"Round\",x);20 # Round(x_Complex?) _ (RationalOrNumber?(Re(x)) And? RationalOrNumber?(Im(x)) ) <-- FloorN(N(Re(x)+0.5)) + FloorN(N(Im(x)+0.5))*I;";
        scriptMap.put("Round",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Sign(n_PositiveNumber?) <-- 1;10 # Sign(n_Zero?) <-- 0;20 # Sign(n_Number?) <-- -1;15 # Sign(n_Infinity?)_(n <? 0) <-- -1;15 # Sign(n_Infinity?)_(n >? 0) <-- 1;15 # Sign(n_Number?/m_Number?) <-- Sign(n)*Sign(m);20 # Sign(n_List?) <-- MapSingle(\"Sign\",n);100 # Sign(_a)^n_Even? <-- 1;100 # Sign(_a)^n_Odd? <-- Sign(a);";
        scriptMap.put("Sign",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "0 # Sqrt(0) <-- 0;0 # Sqrt(Infinity) <-- Infinity;0 # Sqrt(-Infinity) <-- Complex(0,Infinity);0 # Sqrt(Undefined) <-- Undefined;1 # Sqrt(x_PositiveInteger?)_(Integer?(SqrtN(x))) <-- SqrtN(x);2 # Sqrt(x_PositiveNumber?)_InNumericMode() <-- SqrtN(x);2 # Sqrt(x_NegativeNumber?) <-- Complex(0,Sqrt(-x));3 # Sqrt(x_Complex?)_InNumericMode() <-- x^(1/2);Sqrt(xlist_List?) <-- MapSingle(\"Sqrt\",xlist);90 # (Sqrt(x_Constant?))_(NegativeNumber?(N(x))) <-- Complex(0,Sqrt(-x));110 # Sqrt(Complex(_r,_i)) <-- Exp(Ln(Complex(r,i))/2);400 # Sqrt(x_Integer?)_Integer?(SqrtN(x)) <-- SqrtN(x);400 # Sqrt(x_Integer?/y_Integer?)_(Integer?(SqrtN(x)) And? Integer?(SqrtN(y))) <-- SqrtN(x)/SqrtN(y);";
        scriptMap.put("Sqrt",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1 # Undefined <? _x <-- False;1 # Undefined <=? _x <-- False;1 # Undefined >? _x <-- False;1 # Undefined >=? _x <-- False;1 # _x <? Undefined <-- False;1 # _x <=? Undefined <-- False;1 # _x >? Undefined <-- False;1 # _x >=? Undefined <-- False;5 # (n_Number? <? m_Number?) <-- LessThan?(n-m,0);LocalSymbols(nNum,mNum)[ 10 # (_n <? _m)_[nNum:=N(Eval(n)); mNum:=N(Eval(m));Number?(nNum) And? Number?(mNum);] <-- LessThan?(nNum-mNum,0);];20 # (Infinity <? _n)_(Not?(Infinity?(n))) <-- False;20 # (-Infinity <? _n)_(Not?(Infinity?(n))) <-- True;20 # (_n <? Infinity)_(Not?(Infinity?(n))) <-- True;20 # (_n <? -Infinity)_(Not?(Infinity?(n))) <-- False;30 # (_n1/_n2) <? 0 <-- (n1 <? 0) !=? (n2 <? 0);30 # (_n1*_n2) <? 0 <-- (n1 <? 0) !=? (n2 <? 0);30 # ((_n1+_n2) <? 0)_((n1 <? 0) And? (n2 <? 0)) <-- True;30 # ((_n1+_n2) <? 0)_((n1 >? 0) And? (n2 >? 0)) <-- False;30 # _x^a_Odd? <? 0 <-- x <? 0;30 # _x^a_Even? <? 0 <-- False; 40 # (Sqrt(_x))_(x >? 0) <? 0 <-- False;40 # (Sin(_x) <? 0)_(Not?(Even?(N(x/Pi))) And? Even?(N(Floor(x/Pi)))) <-- False;40 # (Sin(_x) <? 0)_(Not?(Odd? (N(x/Pi))) And? Odd? (N(Floor(x/Pi)))) <-- True;40 # Cos(_x) <? 0 <-- Sin(Pi/2-x) <? 0;40 # (Tan(_x) <? 0)_(Not?(Even?(N(2*x/Pi))) And? Even?(N(Floor(2*x/Pi)))) <-- False;40 # (Tan(_x) <? 0)_(Not?(Odd? (N(2*x/Pi))) And? Odd? (N(Floor(2*x/Pi)))) <-- True;40 # (Complex(_a,_b) <? 0)_(b!=?0) <-- False;40 # (Complex(_a,_b) >=? 0)_(b!=?0) <-- False;40 # (Sqrt(_x))_(x <? 0) <? 0 <-- False;40 # (Sqrt(_x))_(x <? 0) >=? 0 <-- False;50 # -(_x) <? 0 <-- Not?((x<?0) Or? (x=?0));50 # _n >? _m <-- m <? n;50 # _n <=? _m <-- m >=? n;50 # _n >=? _m <-- Not?(n<?m);Function(\"!=?\",{aLeft,aRight}) Not?(aLeft=?aRight);";
        scriptMap.put("<?",scriptString);
        scriptMap.put(">?",scriptString);
        scriptMap.put("<=?",scriptString);
        scriptMap.put(">=?",scriptString);
        scriptMap.put("!=?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"->\",{left,right});HoldArgument(\"->\",left);";
        scriptMap.put("->",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "n_Integer? << m_Integer? <-- ShiftLeft(n,m);n_Integer? >> m_Integer? <-- ShiftRight(n,m);";
        scriptMap.put("<<",scriptString);
        scriptMap.put(">>",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "   Function(\"MacroSubstitute\", {body, predicate, change})[ `MacroSubstitute((Hold(@body)));];HoldArgument(\"MacroSubstitute\", predicate);HoldArgument(\"MacroSubstitute\", change);UnFence(\"MacroSubstitute\", 3);RulebaseHoldArguments(\"MacroSubstitute\", {body});UnFence(\"MacroSubstitute\", 1);RuleHoldArguments(\"MacroSubstitute\", 1, 1, `ApplyFast(predicate, {Hold(Hold(@body))}) =? True)[ `ApplyFast(change, {Hold(Hold(@body))});];RuleHoldArguments(\"MacroSubstitute\", 1, 2, `Function?(Hold(@body)))[ `ApplyFast(\"MacroMapArgs\", {Hold(Hold(@body)), \"MacroSubstitute\"});];RuleHoldArguments(\"MacroSubstitute\", 1, 3, True)[ `Hold(@body);];";
        scriptMap.put("MacroSubstitute",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Substitute\", {body, predicate, change})[ Substitute(body);];HoldArgument(\"Substitute\", predicate);HoldArgument(\"Substitute\", change);UnFence(\"Substitute\", 3);RulebaseHoldArguments(\"Substitute\", {body});UnFence(\"Substitute\", 1);RuleHoldArguments(\"Substitute\", 1, 1, Apply(predicate, {body}) =? True)[ Apply(change,{body});];RuleHoldArguments(\"Substitute\", 1, 2, Function?(body))[ Apply(\"MapArgs\",{body,\"Substitute\"});];RuleHoldArguments(\"Substitute\", 1, 3, True) body;";
        scriptMap.put("Substitute",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function() Add(val, ...);10 # Add({}) <-- 0;20 # Add(values_List?) <--[ Local(i, sum); sum:=0; ForEach(i, values) [ sum := sum + i; ]; sum;];30 # Add(_value) <-- value;";
        scriptMap.put("Add",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function() Maximum(l1, l2, l3, ...);10 # Maximum(_l1, _l2, l3_List?) <-- Maximum(Concat({l1, l2}, l3));20 # Maximum(_l1, _l2, _l3) <-- Maximum({l1, l2, l3});10 # Maximum(l1_List?,l2_List?) <-- Map(\"Maximum\",{l1,l2});20 # Maximum(l1_RationalOrNumber?,l2_RationalOrNumber?) <-- If(l1>?l2,l1,l2);30 # Maximum(l1_Constant?,l2_Constant?) <-- If(N(Eval(l1-l2))>?0,l1,l2);10 # Maximum({}) <-- Undefined;20 # Maximum(list_List?) <--[ Local(result); result:= list[1]; ForEach(item,Rest(list)) result:=Maximum(result,item); result;];30 # Maximum(_x) <-- x;";
        scriptMap.put("Maximum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function() Minimum(l1, l2, l3, ...);10 # Minimum(_l1, _l2, l3_List?) <-- Minimum(Concat({l1, l2}, l3));20 # Minimum(_l1, _l2, _l3) <-- Minimum({l1, l2, l3});10 # Minimum(l1_List?,l2_List?) <-- Map(\"Minimum\",{l1,l2});20 # Minimum(l1_RationalOrNumber?,l2_RationalOrNumber?) <-- If(l1<?l2,l1,l2);30 # Minimum(l1_Constant?,l2_Constant?) <-- If(N(Eval(l1-l2))<?0,l1,l2);10 # Minimum({}) <-- Undefined;20 # Minimum(list_List?) <--[ Local(result); result:= list[1]; ForEach(item,Rest(list)) result:=Minimum(result,item); result;];30 # Minimum(_x) <-- x;";
        scriptMap.put("Minimum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Product\",{sumvar,sumfrom,sumto,sumbody})[ Local(sumi,sumsum); sumsum:=1; For(sumi:=sumfrom,sumi<=?sumto And? sumsum!=?0,sumi++) [ MacroLocal(sumvar); MacroBind(sumvar,sumi); sumsum:=sumsum*Eval(sumbody); ]; sumsum;];UnFence(\"Product\",4);HoldArgument(\"Product\",sumvar);HoldArgument(\"Product\",sumbody);Product(sumlist_List?) <--[ Local(sumi,sumsum); sumsum:=1; ForEach(sumi,sumlist) [ sumsum:=sumsum*sumi; ]; sumsum;];";
        scriptMap.put("Product",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Subfactorial\",{n})[ n! * Sum(k,0,n,(-1)^(k)/k!);];30 # Subfactorial(n_List?) <-- MapSingle(\"Subfactorial\",n);";
        scriptMap.put("Subfactorial",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Sum\",{sumvararg,sumfromarg,sumtoarg,sumbodyarg});10 # Sum(_sumvar,sumfrom_Number?,sumto_Number?,_sumbody)_(sumfrom>?sumto) <-- 0;20 # Sum(_sumvar,sumfrom_Number?,sumto_Number?,_sumbody)_(sumto<?sumfrom) <-- ApplyFast(\"Sum\",{sumvar,sumto,sumfrom,sumbody});30 # Sum(_sumvar,sumfrom_Number?,sumto_Number?,_sumbody) <--LocalSymbols(sumi,sumsum)[ Local(sumi,sumsum); sumsum:=0; For(sumi:=sumfrom,sumi<=?sumto,sumi++) [ MacroLocal(sumvar); MacroBind(sumvar,sumi); sumsum:=sumsum+Eval(sumbody); ]; sumsum;];UnFence(\"Sum\",4);HoldArgument(\"Sum\",sumvararg);HoldArgument(\"Sum\",sumbodyarg);40 # Sum({}) <-- 0;50 # Sum(values_List?) <--[ Local(i, sum); sum:=0; ForEach(i, values) [ sum := sum + i; ]; sum;];LocalSymbols(c,d,expr,from,to,summand,sum,predicate,n,r,var,x) [Function() SumFunc(k,from,to,summand, sum, predicate );Function() SumFunc(k,from,to,summand, sum);HoldArgument(SumFunc,predicate);HoldArgument(SumFunc,sum);HoldArgument(SumFunc,summand);SumFunc(_sumvar,sumfrom_Integer?,_sumto,_sumbody,_sum) <--[    `(40 # Sum(@sumvar,@sumfrom,@sumto,@sumbody ) <-- Eval(@sum) ); `(41 # Sum(@sumvar,p_Integer?,@sumto,@sumbody)_(p >? @sumfrom) <-- [ Local(sub); (sub := Eval(ListToFunction({Sum,sumvararg,@sumfrom,p-1,sumbodyarg}))); Simplify(Eval(@sum) - sub ); ]);];SumFunc(_sumvar,sumfrom_Integer?,_sumto,_sumbody,_sum,_condition) <--[ `(40 # Sum(@sumvar,@sumfrom,@sumto,@sumbody)_(@condition) <-- Eval(@sum) ); `(41 # Sum(@sumvar,p_Integer?,@sumto,@sumbody )_(@condition And? p >? @sumfrom) <-- [ Local(sub); `(sub := Eval(ListToFunction({Sum,sumvararg,@sumfrom,p-1,sumbodyarg}))); Simplify(Eval(@sum) - sub ); ]);];SumFunc(_k,1,_n,_c + _d, Eval(ListToFunction({Sum,sumvararg,1,n,c})) + Eval(ListToFunction({Sum,sumvararg,1,n,d})));SumFunc(_k,1,_n,_c*_expr,Eval(c*ListToFunction({Sum,sumvararg,1,n,expr})), FreeOf?(k,c) );SumFunc(_k,1,_n,_expr/_c,Eval(ListToFunction({Sum,sumvararg,1,n,expr})/c), FreeOf?(k,c) );SumFunc(_k,1,Infinity,1/_k,Infinity);SumFunc(_k,1,_n,_c,c*n,FreeOf?(k,c) );SumFunc(_k,1,_n,_k, n*(n+1)/2 );SumFunc(_k,1,_n,_k^_p,(Bernoulli(p+1,n+1) - Bernoulli(p+1))/(p+1), Integer?(p) );SumFunc(_k,1,_n,2*_k-1, n^2 );SumFunc(_k,1,_n,HarmonicNumber(_k),(n+1)*HarmonicNumber(n) - n );SumFunc(_k,0,_n,(r_FreeOf?(k))^(_k), (1-r^(n+1))/(1-r) );SumFunc(_k,1,Infinity,1/(_k^_d), Zeta(d), FreeOf?(k,d) );SumFunc(_k,1,Infinity,_k^(-_d), Zeta(d), FreeOf?(k,d) );SumFunc(_k,0,Infinity,_x^(2*_k+1)/(2*_k+1)!,Sinh(x) );SumFunc(_k,0,Infinity,(-1)^k*_x^(2*_k+1)/(2*_k+1)!,Sin(x) );SumFunc(_k,0,Infinity,_x^(2*_k)/(2*_k)!,Cosh(x) );SumFunc(_k,0,Infinity,(-1)^k*_x^(2*_k)/(2*_k)!,Cos(x) );SumFunc(_k,0,Infinity,_x^(2*_k+1)/(2*_k+1),ArcTanh(x) );SumFunc(_k,0,Infinity,1/(_k)!,Exp(1) );SumFunc(_k,0,Infinity,_x^_k/(_k)!,Exp(x) );40 # Sum(_var,_from,Infinity,_expr)_( `(Limit(@var,Infinity)(@expr)) =? Infinity) <-- Infinity;SumFunc(_k,1,Infinity,1/BinomialCoefficient(2*_k,_k), (2*Pi*Sqrt(3)+9)/27 );SumFunc(_k,1,Infinity,1/(_k*BinomialCoefficient(2*_k,_k)), (Pi*Sqrt(3))/9 );SumFunc(_k,1,Infinity,1/(_k^2*BinomialCoefficient(2*_k,_k)), Zeta(2)/3 );SumFunc(_k,1,Infinity,1/(_k^3*BinomialCoefficient(2*_k,_k)), 17*Zeta(4)/36 );SumFunc(_k,1,Infinity,(-1)^(_k-1)/_k, Ln(2) );];";
        scriptMap.put("Sum",scriptString);
        scriptMap.put("SumFunc",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Taylor\",{taylorvariable,taylorat,taylororder,taylorfunction}) Taylor1(taylorvariable,taylorat,taylororder)(taylorfunction);Function(\"Taylor1\",{taylorvariable,taylorat,taylororder,taylorfunction})[ Local(n,result,dif,polf); [ MacroLocal(taylorvariable); [ MacroLocal(taylorvariable); MacroBind(taylorvariable, taylorat); result:=Eval(taylorfunction); ]; If(result=?Undefined, [ result:=Apply(\"Limit\",{taylorvariable,taylorat,taylorfunction}); ]); ]; dif:=taylorfunction; polf:=(taylorvariable-taylorat); For(n:=1,result !=? Undefined And? n<=?taylororder,n++) [ dif:= Deriv(taylorvariable) dif; Local(term); MacroLocal(taylorvariable); [ MacroLocal(taylorvariable); MacroBind(taylorvariable, taylorat); term:=Eval(dif); ]; If(term=?Undefined, [ term:=Apply(\"Limit\",{taylorvariable,taylorat,dif}); ]); result:=result+(term/(n!))*(polf^n); ]; result;];";
        scriptMap.put("Taylor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"TaylorLPS\",{order, coeffs, var, expr});RulebaseHoldArguments(\"TaylorLPSInverse\",{lps});RulebaseHoldArguments(\"TaylorLPSAdd\",{lps1,lps2});RulebaseHoldArguments(\"TaylorLPSMultiply\",{lps1,lps2});RulebaseHoldArguments(\"TaylorLPSScalarMult\",{a,lps});RulebaseHoldArguments(\"TaylorLPSCompose\",{lps1,lps2});10 # (Taylor2(_x, _a, _n) _y) _ (Not?(PositiveInteger?(n) Or? Zero?(n))) <-- Check(False, \"Argument\", \"Third argument to Taylor should be a nonnegative integer\");20 # (Taylor2(_x, 0, _n) _y) <--[ Local(res); res := TaylorLPSPowerSeries(TaylorLPSConstruct(x, y), n, x); If (ClearError(\"singularity\"), Echo(y, \"has a singularity at\", x, \"= 0.\")); If (ClearError(\"dunno\"), Echo(\"Cannot determine power series of\", y)); res;];30 # (Taylor2(_x, _a, _n) _y) <-- Subst(x,x-a) Taylor2(x,0,n) Subst(x,x+a) y;TaylorLPSParam1() := 15;10 # TaylorLPSConstruct(_var, _expr) <-- TaylorLPS(Undefined, {}, var, TaylorLPSNormalizeExpr(var, expr));TaylorLPSCoeffs(_lps, _n1, _n2) <--[ Local(res, finished, order, j, k, n, tmp, c1, c2); finished := False;  If (lps[1] =? Infinity, [ res := FillList(0, n2-n1+1); finished := True; ]);  If (Not? finished And? lps[1] !=? Undefined And? n2 <? lps[1]+Length(lps[2]), [ If (n1 >=? lps[1], res := Take(lps[2], {n1-lps[1]+1, n2-lps[1]+1}), If (n2 >=? lps[1], res := Concat(FillList(0, lps[1]-n1), Take(lps[2], n2-lps[1]+1)), res := FillList(0, n2-n1+1))); finished := True; ]);  If (Not? finished, [  order := TaylorLPSCompOrder(lps[3], lps[4]); If (Not? ClearError(\"dunno\"), [ If (lps[1] =? Undefined, [ lps[1] := order; If (order <=? n2, [ lps[2] := Table(TaylorLPSCompCoeff(lps[3], lps[4], n), n, order, n2, 1); ]); ],[ tmp := Table(TaylorLPSCompCoeff(lps[3], lps[4], n), n, lps[1]+Length(lps[2]), n2, 1); lps[2] := Concat(lps[2], tmp); ]); finished := True; ]);  If (Not? finished And? lps[4][0] =? TaylorLPSAdd, [ lps[1] := Minimum(TaylorLPSGetOrder(lps[4][1])[1], TaylorLPSGetOrder(lps[4][2])[1], n2); If (Error?(\"dunno\"), [ ClearError(\"dunno\"); ClearError(\"dunno\"); ],[ If (lps[1] <=? n2, [ c1 := TaylorLPSCoeffs(lps[4][1], lps[1] + Length(lps[2]), n2); c2 := TaylorLPSCoeffs(lps[4][2], lps[1] + Length(lps[2]), n2); lps[2] := Concat(lps[2], c1 + c2); ]); finished := True; ]); ]);  If (Not? finished And? lps[4][0] =? TaylorLPSScalarMult, [ lps[1] := Minimum(TaylorLPSGetOrder(lps[4][2])[1], n2); If (Not? ClearError(\"dunno\"), [ If (lps[1] <=? n2, [ tmp := TaylorLPSCoeffs(lps[4][2], lps[1] + Length(lps[2]), n2); tmp := lps[4][1] * tmp; lps[2] := Concat(lps[2], tmp); ]); finished := True; ]); ]);  If (Not? finished And? lps[4][0] =? TaylorLPSMultiply, [ lps[1] := TaylorLPSGetOrder(lps[4][1])[1] + TaylorLPSGetOrder(lps[4][2])[1]; If (Error?(\"dunno\"), [ ClearError(\"dunno\"); ClearError(\"dunno\"); ],[ If (lps[1] <=? n2, [ c1 := TaylorLPSCoeffs(lps[4][1], lps[4][1][1], n2 - lps[4][2][1]); c2 := TaylorLPSCoeffs(lps[4][2], lps[4][2][1], n2 - lps[4][1][1]); tmp := lps[2]; ForEach(k, (Length(lps[2])+1) .. Length(c1)) tmp := Append(tmp, Sum(j, 1, k, c1[j]*c2[k+1-j])); lps[2] := tmp; ]); finished := True; ]); ]);  If (Not? finished And? lps[4][0] =? TaylorLPSInverse, [ If (lps[4][1][1] =? Infinity, [ Assert(\"div-by-zero\") False; finished := True; ]); If (Not? finished And? lps[2] =? {}, [ order := TaylorLPSGetOrder(lps[4][1])[1]; n := order; c1 := TaylorLPSCoeffs(lps[4][1], n, n)[1]; While (c1 =? 0 And? n <? order + TaylorLPSParam1()) [ n := n + 1; c1 := TaylorLPSCoeffs(lps[4][1], n, n)[1]; ]; If (c1 =? 0, [ Assert(\"maybe-div-by-zero\") False; finished := True; ]); ]); If (Not? finished, [ lps[1] := -lps[4][1][1]; c1 := TaylorLPSCoeffs(lps[4][1], lps[4][1][1], lps[4][1][1]+n2-lps[1]); tmp := lps[2]; If (tmp =? {}, tmp := {1/c1[1]}); If (Length(c1)>?1, [ ForEach(k, (Length(tmp)+1) .. Length(c1)) [ n := -Sum(j, 1, k-1, c1[k+1-j]*tmp[j]) / c1[1]; tmp := Append(tmp, n); ]; ]); lps[2] := tmp; finished := True; ]); ]);  If (Not? finished And? lps[4][0] =? TaylorLPSCompose, [ j := TaylorLPSGetOrder(lps[4][1])[1]; Check(j >=? 0, \"Math\", \"Expansion of f(g(x)) where f has a\" ~ \"singularity is not implemented\"); k := TaylorLPSGetOrder(lps[4][2])[1]; c1 := {j, TaylorLPSCoeffs(lps[4][1], j, n2)}; c2 := {k, TaylorLPSCoeffs(lps[4][2], k, n2)}; c1 := TaylorTPSCompose(c1, c2); lps[1] := c1[1]; lps[2] := c1[2]; finished := True; ]);  If (finished, [  While (lps[2] !=? {} And? lps[2][1] =? 0) [ lps[1] := lps[1] + 1; lps[2] := Rest(lps[2]); ];  If (Not? Error?(\"dunno\") And? Not? Error?(\"div-by-zero\") And? Not? Error?(\"maybe-div-by-zero\"), [ If (lps[1] <=? n1, res := Take(lps[2], {n1-lps[1]+1, n2-lps[1]+1}), If (lps[1] <=? n2, res := Concat(FillList(0, lps[1]-n1), lps[2]), res := FillList(0, n2-n1+1))); ]); ],[ Assert(\"dunno\") False; res := False; ]); ]);  res;];10 # TaylorTPSGetCoeff({_n,_c}, _k) _ (k <? n) <-- 0;10 # TaylorTPSGetCoeff({_n,_c}, _k) _ (k >=? n+Length(c)) <-- Undefined;20 # TaylorTPSGetCoeff({_n,_c}, _k) <-- c[k-n+1];10 # TaylorTPSAdd({_n1,_c1}, {_n2,_c2}) <--[ Local(n, len, c1b, c2b); n := Minimum(n1,n2); len := Minimum(n1+Length(c1), n2+Length(c2)) - n; c1b := Take(Concat(FillList(0, n1-n), c1), len); c2b := Take(Concat(FillList(0, n2-n), c2), len); {n, c1b+c2b};];10 # TaylorTPSScalarMult(_a, {_n2,_c2}) <-- {n2, a*c2};10 # TaylorTPSMultiply({_n1,_c1}, {_n2,_c2}) <--[ Local(j,k,c); c := {}; For (k:=1, k<=?Minimum(Length(c1), Length(c2)), k++) [ c := c ~ Sum(j, 1, k, c1[j]*c2[k+1-j]); ]; {n1+n2, c};];10 # TaylorTPSCompose({_n1,_c1}, {_n2,_c2}) <--[ Local(res, tps, tps2, k, n); n := Minimum(n1+Length(c1)-1, n2+Length(c2)-1); tps := {0, 1 ~ FillList(0, n)};  res := TaylorTPSScalarMult(TaylorTPSGetCoeff({n1,c1}, 0), tps); For (k:=1, k<=?n, k++) [ tps := TaylorTPSMultiply(tps, {n2,c2}); tps2 := TaylorTPSScalarMult(TaylorTPSGetCoeff({n1,c1}, k), tps); res := TaylorTPSAdd(res, tps2); ]; res;];5 # TaylorLPSNormalizeExpr(_var, _e1) _ [TaylorLPSCompOrder(var,e1); Not? ClearError(\"dunno\");] <-- e1;10 # TaylorLPSNormalizeExpr(_var, _e1 + _e2) <-- TaylorLPSAdd(TaylorLPSConstruct(var, e1), TaylorLPSConstruct(var, e2));10 # TaylorLPSNormalizeExpr(_var, - _e1) <-- TaylorLPSScalarMult(-1, TaylorLPSConstruct(var, e1));10 # TaylorLPSNormalizeExpr(_var, _e1 - _e2) <-- (TaylorLPSAdd(TaylorLPSConstruct(var, e1), TaylorLPSConstruct(var, e3)) Where e3 == TaylorLPSScalarMult(-1, TaylorLPSConstruct(var, e2)));10 # TaylorLPSNormalizeExpr(_var, e1_FreeOf?(var) * _e2) <-- TaylorLPSScalarMult(e1, TaylorLPSConstruct(var, e2));10 # TaylorLPSNormalizeExpr(_var, _e1 * e2_FreeOf?(var)) <-- TaylorLPSScalarMult(e2, TaylorLPSConstruct(var, e1));20 # TaylorLPSNormalizeExpr(_var, _e1 * _e2) <-- TaylorLPSMultiply(TaylorLPSConstruct(var, e1), TaylorLPSConstruct(var, e2));10 # TaylorLPSNormalizeExpr(_var, _e1 / e2_FreeOf?(var)) <-- TaylorLPSScalarMult(1/e2, TaylorLPSConstruct(var, e1));20 # TaylorLPSNormalizeExpr(_var, 1 / _e1) <-- TaylorLPSInverse(TaylorLPSConstruct(var, e1));30 # TaylorLPSNormalizeExpr(_var, _e1 / _e2) <-- (TaylorLPSMultiply(TaylorLPSConstruct(var, e1), TaylorLPSConstruct(var, e3)) Where e3 == TaylorLPSInverse(TaylorLPSConstruct(var, e2)));10 # TaylorLPSNormalizeExpr(_var, _e1 ^ (n_PositiveInteger?)) _ (e1 !=? var) <-- TaylorLPSMultiply(TaylorLPSConstruct(var, e1), TaylorLPSConstruct(var, e1^(n-1)));10 # TaylorLPSNormalizeExpr(_var, Tan(_x)) <-- (TaylorLPSMultiply(TaylorLPSConstruct(var, Sin(x)), TaylorLPSConstruct(var, e3)) Where e3 == TaylorLPSInverse(TaylorLPSConstruct(var, Cos(x))));LocalSymbols(res)[50 # TaylorLPSNormalizeExpr(_var, _e1)_[ Local(c, lps1, lps2, lps3, success); success := True; If (Atom?(e1), success := False); If (success And? Length(e1) !=? 1, success := False); If (success And? Atom?(e1[1]), success := False); If (success And? CanBeUni(var, e1[1]) And? Degree(e1[1], var) =? 1, [ success := False; ]); If (success, [ lps2 := TaylorLPSConstruct(var, e1[1]); c := TaylorLPSCoeffs(lps2, 0, 0)[1]; If (Error?(), [ ClearErrors(); success := False; ]); If (success And? TaylorLPSGetOrder(lps2)[1] <? 0, [ success := False; ],[ If (c =? 0, [ lps1 := TaylorLPSConstruct(var, Apply(e1[0], {var})); res := TaylorLPSCompose(lps1, lps2); ],[ lps1 := TaylorLPSConstruct(var, Apply(e1[0], {var+c})); lps3 := TaylorLPSConstruct(var, -c); lps2 := TaylorLPSConstruct(var, TaylorLPSAdd(lps2, lps3)); res := TaylorLPSCompose(lps1, lps2); ]); ]); ]); success; ] <-- res;];60000 # TaylorLPSNormalizeExpr(_var, _e1) <-- e1;5 # TaylorLPSCompCoeff(_var, _expr, _n) _ (n <? TaylorLPSCompOrder(var, expr)) <-- 0;10 # TaylorLPSCompOrder(_x, 0) <-- Infinity;20 # TaylorLPSCompOrder(_x, e_FreeOf?(x)) <-- 0;20 # TaylorLPSCompCoeff(_x, e_FreeOf?(x), 0) <-- e;21 # TaylorLPSCompCoeff(_x, e_FreeOf?(x), _n) <-- 0;30 # TaylorLPSCompOrder(_x, _x) <-- 1;30 # TaylorLPSCompCoeff(_x, _x, 1) <-- 1;31 # TaylorLPSCompCoeff(_x, _x, _n) <-- 0;40 # TaylorLPSCompOrder(_x, _x^(k_PositiveInteger?)) <-- k;40 # TaylorLPSCompCoeff(_x, _x^(k_PositiveInteger?), _k) <-- 1;41 # TaylorLPSCompCoeff(_x, _x^(k_PositiveInteger?), _n) <-- 0;50 # TaylorLPSCompOrder(_x, Sqrt(_y)) _ (CanBeUni(x,y) And? Degree(y,x) =? 1 And? Coef(y,x,0) !=? 0) <-- 0;50 # TaylorLPSCompCoeff(_x, Sqrt(_y), 0) _ (CanBeUni(x,y) And? Degree(y,x) =? 1 And? Coef(y,x,0) !=? 0) <-- Sqrt(Coef(y,x,0));51 # TaylorLPSCompCoeff(_x, Sqrt(_y), _n) _ (CanBeUni(x,y) And? Degree(y,x) =? 1 And? Coef(y,x,0) !=? 0) <--[ Local(j); Coef(y,x,0)^(1/2-n) * Product(j,0,n-1,1/2-j) * Coef(y,x,1)^n/n!;];60 # TaylorLPSCompOrder(_x, Exp(_x)) <-- 0;60 # TaylorLPSCompCoeff(_x, Exp(_x), _n) <-- 1/n!;70 # TaylorLPSCompOrder(_x, Exp(_y))_(CanBeUni(x,y) And? Degree(y,x) =? 1) <-- 0;70 # TaylorLPSCompCoeff(_x, Exp(_y), _n)_(CanBeUni(x,y) And? Degree(y,x) =? 1) <-- Exp(Coef(y,x,0)) * Coef(y,x,1)^n / n!;80 # TaylorLPSCompOrder(_x, Ln(_x+1)) <-- 1;80 # TaylorLPSCompCoeff(_x, Ln(_x+1), _n) <-- (-1)^(n+1)/n;90 # TaylorLPSCompOrder(_x, Sin(_x)) <-- 1;90 # TaylorLPSCompCoeff(_x, Sin(_x), n_Odd?) <-- (-1)^((n-1)/2) / n!;90 # TaylorLPSCompCoeff(_x, Sin(_x), n_Even?) <-- 0;100 # TaylorLPSCompOrder(_x, Cos(_x)) <-- 0;100 # TaylorLPSCompCoeff(_x, Cos(_x), n_Odd?) <-- 0;100 # TaylorLPSCompCoeff(_x, Cos(_x), n_Even?) <-- (-1)^(n/2) / n!;110 # TaylorLPSCompOrder(_x, 1/_x) <-- -1;110 # TaylorLPSCompCoeff(_x, 1/_x, -1) <-- 1;111 # TaylorLPSCompCoeff(_x, 1/_x, _n) <-- 0;TaylorLPSAcceptDeriv(_expr) <-- (Contains?({\"ArcTan\"},Type(expr)));200 # TaylorLPSCompOrder(_x, (_expr))_(TaylorLPSAcceptDeriv(expr)) <-- [ Local(n); n:=0; While ((Limit(x,0)expr) =? 0 And? n<?TaylorLPSParam1()) [ expr := Deriv(x)expr; n++; ]; n; ];200 # TaylorLPSCompCoeff(_x, (_expr), _n)_ (TaylorLPSAcceptDeriv(expr) And? n>=?0 ) <-- [  Local(result); result:=(Limit(x,0)(Deriv(x,n)expr))/(n!);Echo(expr,\" \",n,\" \",result); result; ];60000 # TaylorLPSCompOrder(_var, _expr) <-- Assert(\"dunno\") False;60000 # TaylorLPSCompCoeff(_var, _expr, _n) <-- Check(False, \"Argument\", \"TaylorLPSCompCoeffFallThrough\" ~ PipeToString() Write({var,expr,n}));20 # TaylorLPSGetOrder(TaylorLPS(_order, _coeffs, _var, _expr)) _ (order !=? Undefined) <-- {order, coeffs !=? {}};40 # TaylorLPSGetOrder(_lps) <--[ Local(res, computed, exact, res1, res2); computed := False; res := TaylorLPSCompOrder(lps[3], lps[4]); If (Not? ClearError(\"dunno\"), [ res := {res, True}; computed := True; ]); If (Not? computed And? lps[4][0] =? TaylorLPSAdd, [ res1 := TaylorLPSGetOrder(lps[4][1]); If (Not? ClearError(\"dunno\"), [ res2 := TaylorLPSGetOrder(lps[4][2]); If (Not? ClearError(\"dunno\"), [ res := {Minimum(res1[1],res2[1]), False};  computed := True; ]); ]); ]); If (Not? computed And? lps[4][0] =? TaylorLPSScalarMult, [ res := TaylorLPSGetOrder(lps[4][2]); If (Not? ClearError(\"dunno\"), computed := True); ]); If (Not? computed And? lps[4][0] =? TaylorLPSMultiply, [ res1 := TaylorLPSGetOrder(lps[4][1]); If (Not? ClearError(\"dunno\"), [ res2 := TaylorLPSGetOrder(lps[4][2]); If (Not? ClearError(\"dunno\"), [ res := {res1[1]+res2[1], res1[1] And? res2[1]}; computed := True; ]); ]); ]); If (Not? computed And? lps[4][0] =? TaylorLPSInverse, [ res := TaylorLPSGetOrder(lps[4][1]); If (Not? ClearError(\"dunno\"), [ If (res[1] =? Infinity, [ res[1] =? Undefined; Assert(\"div-by-zero\") False; computed := True; ]); If (Not? computed And? res[2] =? False, [ Local(c, n); n := res[1]; c := TaylorLPSCoeffs(lps[4][1], res[1], res[1])[1]; While (c =? 0 And? res[1] <? n + TaylorLPSParam1()) [ res[1] := res[1] + 1; c := TaylorLPSCoeffs(lps[4][1], res[1], res[1])[1]; ]; If (c =? 0, [ res[1] := Undefined; Assert(\"maybe-div-by-zero\") False; computed := True; ]); ]); If (Not? computed, [ res := {-res[1], True}; computed := True; ]); ]); ]); If (Not? computed And? lps[4][0] =? TaylorLPSCompose, [ res1 := TaylorLPSGetOrder(lps[4][1]); If (Not? ClearError(\"dunno\"), [ res2 := TaylorLPSGetOrder(lps[4][2]); If (Not? ClearError(\"dunno\"), [ res := {res1[1]*res2[1], res1[1] And? res2[1]}; computed := True; ]); ]); ]); If (computed, lps[1] := res[1]); Assert(\"dunno\") computed; res;];10 # TaylorLPSPowerSeries(_lps, _n, _var) <--[ Local(ord, k, coeffs); coeffs := TaylorLPSCoeffs(lps, 0, n); If (Error?(\"dunno\"), [ False; ],[ If (lps[1] <? 0, [ Assert(\"singularity\") False; Undefined; ],[ Sum(k, 0, n, coeffs[k+1]*var^k); ]); ]);];";
        scriptMap.put("Taylor2",scriptString);
        scriptMap.put("TaylorLPS",scriptString);
        scriptMap.put("TaylorLPSNormalizeExpr",scriptString);
        scriptMap.put("TaylorLPSConstruct",scriptString);
        scriptMap.put("TaylorLPSPowerSeries",scriptString);
        scriptMap.put("TaylorLPSCoeffs",scriptString);
        scriptMap.put("TaylorLPSInverse",scriptString);
        scriptMap.put("TaylorLPSAdd",scriptString);
        scriptMap.put("TaylorLPSMultiply",scriptString);
        scriptMap.put("TaylorLPSScalarMult",scriptString);
        scriptMap.put("TaylorLPSCompose",scriptString);
        scriptMap.put("TaylorTPSGetCoeff",scriptString);
        scriptMap.put("TaylorTPSAdd",scriptString);
        scriptMap.put("TaylorTPSMultiply",scriptString);
        scriptMap.put("TaylorTPSCompose",scriptString);
        scriptMap.put("TaylorLPSCompCoeff",scriptString);
        scriptMap.put("TaylorLPSCompOrder",scriptString);
        scriptMap.put("TaylorTPSScalarMult",scriptString);
        scriptMap.put("TaylorLPSAcceptDeriv",scriptString);
        scriptMap.put("TaylorLPSGetOrder",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"Taylor3MultiplyCoefs\",{coefs1,coefs2,degree})[ Local(result,i,j,jset,ilimit,jlimit); Bind(result, ArrayCreate(AddN(degree,1),0)); Bind(i,1); Bind(ilimit,AddN(degree,2)); While (Not? Equal?(i,ilimit)) [ Bind(j,1); Bind(jlimit,AddN(degree,SubtractN(3,i))); While (Not? Equal?(j,jlimit)) [ Bind(jset,AddN(i,SubtractN(j,1))); ArraySet(result,jset,ArrayGet(result,jset) + ArrayGet(coefs1,i)*ArrayGet(coefs2,j)); Bind(j,AddN(j,1)); ]; Bind(i,AddN(i,1)); ]; result;];10 # (Taylor3TaylorCoefs(_var,_degree)(_var)) <--[ Local(result); Bind(result,ArrayCreate(degree+1,0)); ArraySet(result,2, 1); result;];20 # (Taylor3TaylorCoefs(_var,_degree)(_atom))_(FreeOf?(var,atom)) <-- [ Local(result); Bind(result,ArrayCreate(degree+1,0)); ArraySet(result,1, atom); result; ];30 # (Taylor3TaylorCoefs(_var,_degree)(_X + _Y)) <-- [ Local(result,add,i); Bind(result,Taylor3TaylorCoefs(var,degree)(X)); Bind(add, Taylor3TaylorCoefs(var,degree)(Y)); For(i:=1,i<=?degree+1,i++) [ ArraySet(result,i,ArrayGet(result,i)+ArrayGet(add,i)); ]; result; ];30 # (Taylor3TaylorCoefs(_var,_degree)(_X - _Y)) <-- [ Local(result,add,i); Bind(result,Taylor3TaylorCoefs(var,degree)(X)); Bind(add, Taylor3TaylorCoefs(var,degree)(Y)); For(i:=1,i<=?degree+1,i++) [ ArraySet(result,i,ArrayGet(result,i)-ArrayGet(add,i)); ]; result; ];30 # (Taylor3TaylorCoefs(_var,_degree)( - _Y)) <-- [ Local(result,add,i); Bind(result,Taylor3TaylorCoefs(var,degree)(Y)); For(i:=1,i<=?degree+1,i++) [ ArraySet(result,i,-ArrayGet(result,i)); ]; result; ];30 # (Taylor3TaylorCoefs(_var,_degree)(_X * _Y)) <-- Taylor3MultiplyCoefs( Taylor3TaylorCoefs(var,degree)(X), Taylor3TaylorCoefs(var,degree)(Y), degree);30 # (Taylor3TaylorCoefs(_var,_degree)((_X) ^ N_PositiveInteger?)) <--[ Local(result,factor); factor:=Taylor3TaylorCoefs(var,degree)(X); result:=ArrayCreate(degree+1,0); result[1] := 1;  While(N>?0) [ result:=Taylor3MultiplyCoefs(result,factor,degree); N--; ]; result;];60 # Taylor3UniFunction(\"Exp\") <-- True;60 # Taylor3CompCoeff(\"Exp\", _n) <-- 1/n!;80 # Taylor3UniFunction(\"Ln\") <-- False; 80 # Taylor3CompCoeff(\"Ln\", 0) <-- 0;81 # Taylor3CompCoeff(\"Ln\", _n) <-- (-1)^(n+1)/n;90 # Taylor3UniFunction(\"Sin\") <-- True;90 # Taylor3CompCoeff(\"Sin\", n_Odd?) <-- (-1)^((n-1)/2) / n!;90 # Taylor3CompCoeff(\"Sin\", n_Even?) <-- 0;100 # Taylor3UniFunction(\"Cos\") <-- True;100 # Taylor3CompCoeff(\"Cos\", n_Odd?) <-- 0;100 # Taylor3CompCoeff(\"Cos\", n_Even?) <-- (-1)^(n/2) / n!;210 # Taylor3UniFunction(_any)_ ( [ Local(result); result:= Deriv(var)ListToFunction({ToAtom(any),var}); Type(result) !=? \"Deriv\"; ] ) <-- [ True; ];210 # Taylor3CompCoeff(_any, n_Integer?) <-- [ Limit(var,0)(Deriv(var,n)(ListToFunction({ToAtom(any),var}))/n!); ];60000 # Taylor3UniFunction(_any) <-- False;Taylor3FuncCoefs(_fname,_degree) <--[ Local(sins,i); Bind(sins, ArrayCreate(degree+1,0)); For (i:=0,i<=?degree,Bind(i,i+1)) [ ArraySet(sins,i+1, Taylor3CompCoeff(fname,i)); ]; sins;];100 # (Taylor3TaylorCoefs(_var,_degree)(Ln(_f)))_(Simplify(f-1) =? var) <-- Taylor3FuncCoefs(\"Ln\",degree);110 # (Taylor3TaylorCoefs(_var,_degree)(f_Function?))_(ArgumentsCount(f) =? 1 And? (Taylor3UniFunction(Type(f)))) <--[ Local(sins,i,j,result,xx,expr,sinfact); expr := f[1]; sins:=Taylor3FuncCoefs(Type(f),degree); expr:=Taylor3TaylorCoefs(var,degree)expr; result:=ArrayCreate(degree+1,0); ArraySet(result,1, ArrayGet(sins,1)); xx:=expr; For (i:=2,i<=?degree+1,i++) [ Bind(sinfact,sins[i]); For (j:=1,j<=?degree+1,j++) [ ArraySet(result,j,ArrayGet(result,j) + (ArrayGet(xx,j) * sinfact)); ]; Bind(xx,Taylor3MultiplyCoefs(xx,expr,degree)); ]; result;];(Taylor3(_var,_degree)(_expr)) <-- Add((Taylor3TaylorCoefs(var,degree)(expr))[1 .. degree+1]*var^(0 .. degree));10 # (Taylor3(_x, 0, _n) _y) <-- Taylor3(x,n) y;20 # (Taylor3(_x, _a, _n) _y) <-- Subst(x,x-a) Taylor3(x,n) Subst(x,x+a) y;";
        scriptMap.put("Taylor3",scriptString);
        scriptMap.put("Taylor3TaylorCoefs",scriptString);
        scriptMap.put("Taylor3UniFunction",scriptString);
        scriptMap.put("Taylor3CompCoeff",scriptString);
        scriptMap.put("Taylor3FuncCoefs",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "n1_RationalOrNumber? *** n2_RationalOrNumber? <--[ Check(n2-n1 <=? 65535, \"Argument\", \"Partial factorial: Error: the range \" ~ ( PipeToString() Write(n2-n1) ) ~ \" is too large, you may want to avoid exact calculation\"); If(n2-n1<?0, 1, Factorialpartial(n1, n2) );];2# Factorialpartial(_a, _b) _ (b-a>=?4) <-- Factorialpartial(a, a+((b-a)>>1)) * Factorialpartial(a+((b-a)>>1)+1, b);3# Factorialpartial(_a, _b) _ (b-a>=?3) <-- a*(a+1)*(a+2)*(a+3);4# Factorialpartial(_a, _b) _ (b-a>=?2) <-- a*(a+1)*(a+2);5# Factorialpartial(_a, _b) _ (b-a>=?1) <-- a*(a+1);6# Factorialpartial(_a, _b) _ (b-a>=?0) <-- a;";
        scriptMap.put("***",scriptString);
        scriptMap.put("Factorialpartial",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1# (n_PositiveInteger?)!! _ (n<=?3) <-- n;2# (n_PositiveInteger?)!! <--[ Check(n<=?65535, \"Argument\", \"Double factorial: Error: the argument \" ~ ( PipeToString() Write(n) ) ~ \" is too large, you may want to avoid exact calculation\"); Factorialdouble(2+Modulo(n, 2), n);];3# (_n)!! _ (n=? -1 Or? n=?0)<-- 1;2# Factorialdouble(_a, _b) _ (b-a>=?6) <-- Factorialdouble(a, Quotient(a+b,2)) * Factorialdouble(Quotient(a+b,2)+1+Modulo(Quotient(a+b,2)+1-a, 2), b);3# Factorialdouble(_a, _b) _ (b-a>=?4) <-- a*(a+2)*(a+4);4# Factorialdouble(_a, _b) _ (b-a>=?2) <-- a*(a+2);5# Factorialdouble(_a, _b) <-- a;30 # (n_List?)!! <-- MapSingle(\"!!\",n);";
        scriptMap.put("!!",scriptString);
        scriptMap.put("Factorialdouble",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # 0! <-- 1;10 # (Infinity)! <-- Infinity;20 # ((n_PositiveInteger?)!) <-- [ Check(n <=? 65535, \"Argument\", \"Factorial: Error: the argument \" ~ ( PipeToString() Write(n) ) ~ \" is too large, you may want to avoid exact calculation\"); MathFac(n);];25 # ((x_Constant?)!)_(FloatIsInt?(x) And? x>?0) <-- (Round(x)!);30 # ((x_Number?)!)_InNumericMode() <-- InternalGammaNum(x+1);40 # (n_List?)! <-- MapSingle(\"!\",n);HalfIntegerFactorial(n_Odd?) _ (n>?0) <-- Sqrt(Pi) * ( n!! / 2^((n+1)/2) );HalfIntegerFactorial(n_Odd?) _ (n<?0) <-- Sqrt(Pi) * ( (-1)^((-n-1)/2)*2^((-n-1)/2) / (-n-2)!! );40 # (n_RationalOrNumber?)! _(Denominator(Rationalize(n))=?2) <-- HalfIntegerFactorial(Numerator(Rationalize(n)));";
        scriptMap.put("!",scriptString);
        scriptMap.put("HalfIntegerFactorial",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ApplyDelta(_result,Delta(_i,_j)) <-- DestructiveInsert(result,1,Delta(i,j));20 # ApplyDelta(_result,(_x) ^ (n_Integer?))_(n>?0) <-- [ Local(i); For(i:=1,i<=?n,i++) [ ApplyDelta(result,x); ]; ];100 # ApplyDelta(_result,_term) <-- DestructiveAppend(result,term);";
        scriptMap.put("ApplyDelta",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Delta\",{ind1,ind2});";
        scriptMap.put("Delta",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MoveDeltas(_list) <--[ Local(result,i,nr); result:={}; nr:=Length(list); For(i:=1,i<=?nr,i++) [ ApplyDelta(result,list[i]); ]; result;];";
        scriptMap.put("MoveDeltas",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"TD\",{ind});10 # (TD(_i)X(_j)) <-- Delta(i,j);10 # (TD(_i) ( (_f) + (_g) ) ) <-- (TD(i)f) + (TD(i)g);10 # (TD(_i) ( (_f) - (_g) ) ) <-- (TD(i)f) - (TD(i)g);10 # (TD(_i) ( - (_g) ) ) <-- - TD(i)g;10 # (TD(_i) ( (_f) * (_g) ) ) <-- (TD(i)f)*g + f*(TD(i)g);10 # (TD(_i) ( (_f) ^ (n_PositiveInteger?) ) ) <-- n*(TD(i)f)*f^(n-1);10 # (TD(_i)Delta(_j,_k)) <-- 0;10 # (TD(_i)f_Number?) <-- 0;";
        scriptMap.put("TD",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "(TExplicitSum(Ndim_Integer?)(_body)) <-- Eval(body);";
        scriptMap.put("TExplicitSum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"TList\",{head,tail});";
        scriptMap.put("TList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TSimplify(TSum(_indices)(_f)) <--[ TSimplifyAux(TSum(indices)ExpandBrackets(f));];10 # TSimplifyAux(TSum(_indices)((_f) + (_g))) <-- TSimplifyAux(TSum(FlatCopy(indices))(f)) + TSimplifyAux(TSum(FlatCopy(indices))(g));10 # TSimplifyAux(TSum(_indices)((_f) - (_g))) <-- TSimplifyAux(TSum(FlatCopy(indices))(f)) - TSimplifyAux(TSum(FlatCopy(indices))(g));10 # TSimplifyAux(TSum(_indices)( - (_g))) <-- - TSimplifyAux(TSum(indices)(g));40 # TSimplifyAux(TSum(_indices)_body) <--[ Local(flat);  flat:=Flatten(body,\"*\");  flat:=MoveDeltas(flat);  flat:=TSumRest(flat);  Local(varlist,independ,nrdims); varlist:=VarList(flat); independ:=Intersection(indices,varlist); nrdims:=Length(indices)-Length(independ);  Ndim^nrdims*TSum(independ)flat;];";
        scriptMap.put("TSimplify",scriptString);
        scriptMap.put("TSimplifyAux",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"TSum\",{indices,body});10 # (TSum({})(_body)) <-- body;20 # (TSum(_indices)(_body))_(Integer?(Ndim)) <-- LocalSymbols(index,i,sum) [ Local(index,i,sum); index:=indices[1]; sum:=0; MacroLocal(index); For(i:=1,i<=?Ndim,i++) [ MacroBind(index,i); sum:=sum+Eval(TSum(Rest(indices))body); ]; sum; ];UnFence(\"TSum\",2);";
        scriptMap.put("TSum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # TSumRest({}) <-- 1;20 # TSumRest(_list) <--[ TSumSimplify(TList(First(list),Rest(list)));];UnFence(\"TSumRest\",1); ";
        scriptMap.put("TSumRest",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # TSumSimplify(TList(Delta(_ind,_ind),_list))_Contains?(indices,ind) <--[  DestructiveDelete(indices,Find(indices,ind)); Ndim*TSumRest(list);];11 # TSumSimplify(TList(Delta(_ind1,_ind2),_list))_ Contains?(indices,ind2) <--[  DestructiveDelete(indices,Find(indices,ind2));  TSumRest( Subst(ind2,ind1)list );];11 # TSumSimplify(TList(Delta(_ind1,_ind2),_list))_ Contains?(indices,ind1) <--[  DestructiveDelete(indices,Find(indices,ind1));  TSumRest( Subst(ind1,ind2)list );];1010 # TSumSimplify(TList(_term,_list)) <--[ term*TSumRest(list);];UnFence(\"TSumSimplify\",1); ";
        scriptMap.put("TSumSimplify",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"BenchCall\",{expr})[ Echo(\"In&gt \",expr); WriteString(\"<font color=ff0000>\"); Eval(expr); WriteString(\"</font>\"); True;];HoldArgument(\"BenchCall\",expr);";
        scriptMap.put("BenchCall",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"BenchShow\",{expr})[ Echo(\"In&gt \",expr); WriteString(\"<font color=ff0000> \"); Echo(\"Out&gt \",Eval(expr),\"</font>\"); True;];HoldArgument(\"BenchShow\",expr);";
        scriptMap.put("BenchShow",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # EqualAsSets( A_List?, B_List? )_(Length(A)=?Length(B)) <--[ Local(Acopy,b,nba,result); Acopy := FlatCopy(A); result := True; ForEach(b,B) [ nba := Find(Acopy,b); If( nba <? 0, [ result := False; Break(); ] ); DestructiveDelete(Acopy,nba); ]; If( Not? result, result := Length(Acopy)=?0 ); result;];20 # EqualAsSets( _A, _B ) <-- False;";
        scriptMap.put("EqualAsSets",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"KnownFailure\",{expr})[ Local(rfail); Echo(\"Known failure: \", expr); Bind(rfail,Eval(expr)); If(rfail,Echo({\"Failure resolved!\"}));];HoldArgument(\"KnownFailure\",expr);";
        scriptMap.put("KnownFailure",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(TrueFalse)[ RulebaseEvaluateArguments(TrueFalse,{var,expr}); 10 # TrueFalse(var_Atom?,_expr) <-- `{(@expr) Where (@var)==False,(@expr) Where (@var)==True}; 20 # TrueFalse({},_expr) <-- `(@expr); 30 # TrueFalse(var_List?,_expr) <-- `[ Local(t,h); Bind(h,First(@var)); Bind(t,Rest(@var)); TrueFalse(h,TrueFalse(t,@expr)); ]; Macro(LogicTest,{vars,expr1,expr2}) Verify(TrueFalse((@vars),(@expr1)), TrueFalse((@vars),(@expr2)));];";
        scriptMap.put("LogicTest",scriptString);
        scriptMap.put("TrueFalse",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"LogicVerify\",{aLeft,aRight})[ If(aLeft !=? aRight, Verify(CanProve(aLeft Implies? aRight),True) );];";
        scriptMap.put("LogicVerify",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"NextTest\",{aLeft})[WriteString(\"Test suite for \"~aLeft~\" : \" ); NewLine();];";
        scriptMap.put("NextTest",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # NumericEqual(left_Decimal?, right_Decimal?, precision_PositiveInteger?) <--[ If(InVerboseMode(),Tell(\"NumericEqual\",{left,right})); Local(repL,repR,precL,precR,newL,newR,plo,phi,replo,rephi); Local(newhi,newrepL,newlo,newrepR,ans); repL := NumberToRep(left); repR := NumberToRep(right); precL := repL[2]; precR := repR[2]; If(InVerboseMode(),Tell(\" \",{precL,precR,precision})); newL := RoundToPrecision(left, precision ); newR := RoundToPrecision(right, precision ); If(InVerboseMode(),Tell(\" \",{newL,newR})); newrepL := NumberToRep( newL ); newrepR := NumberToRep( newR ); If(InVerboseMode(),Tell(\" \",{newrepL,newrepR})); ans := Verify( newrepL[1] - newrepR[1], 0 ); If(InVerboseMode(),Tell(\" \",ans)); ans;];15 # NumericEqual(left_Integer?, right_Integer?, precision_PositiveInteger?) <--[ If(InVerboseMode(),Tell(\"NumericEqualInt\",{left,right})); left =? right;];20 # NumericEqual(left_Number?, right_Number?, precision_PositiveInteger?) <--[ If(InVerboseMode(),Tell(\"NumericEqualNum\",{left,right})); Local(nI,nD,repI,repD,precI,precD,intAsDec,newDec,newrepI,newrepD,ans); If( Integer?(left), [nI:=left; nD:=right;], [nI:=right; nD:=left;]);  repI := NumberToRep(nI); repD := NumberToRep(nD); precI := repI[2]; precD := repD[2]; intAsDec := RoundToPrecision(1.0*nI,precision); newDec := RoundToPrecision( nD, precision ); newrepI := NumberToRep( intAsDec ); newrepD := NumberToRep( newDec ); If(InVerboseMode(), [ Tell(\" \",{nI,nD}); Tell(\" \",{repI,repD}); Tell(\" \",{precI,precD}); Tell(\" \",{intAsDec,newDec}); Tell(\" \",{newrepI,newrepD}); ] ); ans := Verify( newrepI[1] - newrepD[1], 0 ); If(InVerboseMode(),Tell(\" \",ans)); ans;];25 # NumericEqual(left_Complex?, right_Complex?, precision_PositiveInteger?) <--[ If(InVerboseMode(),Tell(\"NumericEqualC\",{left,right})); Local(rrL,iiL,rrR,iiR,ans); rrL := Re(left); iiL := Im(left); rrR := Re(right); iiR := Im(right); If(InVerboseMode(), [ Tell(\" \",{left,right}); Tell(\" \",{rrL,rrR}); Tell(\" \",{iiL,iiR}); ] ); ans := (NumericEqual(rrL,rrR,precision) And? NumericEqual(iiL,iiR,precision));];";
        scriptMap.put("NumericEqual",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RandVerifyArithmetic(_n)<--[ While(n>?0) [ n--; VerifyArithmetic(FloorN(300*Random()),FloorN(80*Random()),FloorN(90*Random())); ];];";
        scriptMap.put("RandVerifyArithmetic",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RoundTo(x_Number?, precision_PositiveInteger?) <--[ Local(oldPrec,result); oldPrec:=BuiltinPrecisionGet(); BuiltinPrecisionSet(precision); Bind(result,DivideN( Round( MultiplyN(x, 10^precision) ), 10^precision )); BuiltinPrecisionSet(oldPrec); result;];10 # RoundTo(Complex(r_Number?, i_Number?), precision_PositiveInteger?) <-- Complex(RoundTo(r, precision), RoundTo(i, precision));20 # RoundTo( Infinity,precision_PositiveInteger?) <-- Infinity;20 # RoundTo(-Infinity,precision_PositiveInteger?) <-- -Infinity;";
        scriptMap.put("RoundTo",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ShowLine() :=[ Echo(\"File: \", CurrentFile(),\", Line: \", CurrentLine());];";
        scriptMap.put("ShowLine",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Macro(\"TestEquivalent\",{left,right})[ Local(leftEval,rightEval,diff,vars,isEquiv); If(InVerboseMode(),[Tell(TestEquivalent,{left,right});]); leftEval := @left; rightEval := @right; If(InVerboseMode(), [ NewLine(); Tell(\" \",leftEval); Tell(\" \",rightEval); ]); If( List?(leftEval), [ If( List?(rightEval), [  If(InVerboseMode(),Tell(\" both are lists \")); isEquiv := TestTwoLists(leftEval,rightEval); ], isEquiv := False ); ], [ If( List?(rightEval),  isEquiv := False, [  If(InVerboseMode(),Tell(\" neither is list \")); If(Equation?(leftEval), [ If(Equation?(rightEval), [ If(InVerboseMode(),Tell(\" both are equations\")); Local(dLHs,dRHS); dLHS := Simplify(EquationLeft(leftEval) - EquationLeft(rightEval)); dRHS := Simplify(EquationRight(leftEval) - EquationRight(rightEval)); If(InVerboseMode(),Tell(\" \",{dLHS,dRHS})); isEquiv := dLHS=?0 And? dRHS=?0; ], isEquiv := False ); ], [ If(Equation?(rightEval), isEquiv := False, [ If(InVerboseMode(),Tell(\" neither is equation\")); diff := Simplify(leftEval - rightEval); vars := VarList(diff); If(InVerboseMode(), [ Tell(\" \",{leftEval,rightEval}); Tell(\" \",vars); Tell(\" \",diff); ] ); isEquiv := ( Zero?(diff) Or? ZeroVector?(diff) ); ] ); ] ); ] ); ] ); If(InVerboseMode(),Tell(\" Equivalence = \",isEquiv)); If ( Not? isEquiv, [ WriteString(\"******************\"); NewLine(); WriteString(\"L.H.S. evaluates to: \"); Write(leftEval); NewLine(); WriteString(\"which differs from \"); Write(rightEval); NewLine(); WriteString(\" by \");  Write(diff); NewLine(); WriteString(\"******************\"); NewLine(); ] ); isEquiv;];10 # TestTwoLists( L1_List?, L2_List? ) <--[ If(InVerboseMode(),[Tell(\" TestTwoLists\");Tell(\" \",L1);Tell(\" \",L2);]); If(Length(L1)=?1 And? Length(L2)=?1, [ TestEquivalent(L1[1],L2[1]); ], [ EqualAsSets(L1,L2); ] );];";
        scriptMap.put("TestEquivalent",scriptString);
        scriptMap.put("TestTwoLists",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function (\"TestMathPiper\", {expr, ans})[ Local(diff); diff := Simplify(Eval(expr)-Eval(ans)); If (Simplify(diff)=?0, True, [ WriteString(\"******************\"); NewLine(); ShowLine(); Write(expr); WriteString(\" evaluates to \"); NewLine(); Write(Eval(expr)); NewLine(); WriteString(\" which differs from \"); NewLine(); Write(Eval(ans)); NewLine(); WriteString(\" by \"); NewLine(); Write(diff); NewLine(); WriteString(\"******************\"); NewLine(); False; ] );];HoldArgument(\"TestMathPiper\", expr);HoldArgument(\"TestMathPiper\", ans);";
        scriptMap.put("TestMathPiper",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Testing\",{aLeft})[ WriteString(\"--\"); WriteString(aLeft); NewLine();];";
        scriptMap.put("Testing",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Verify\",{aLeft,aRight})[ Local(result); result := Eval(aLeft);  If (Not?(Equal?(result,aRight)), [ WriteString(\"******************\"); NewLine(); ShowLine(); NewLine(); Write(aLeft); NewLine(); WriteString(\" evaluates to \"); NewLine(); Write(result); NewLine(); WriteString(\" which differs from \"); NewLine(); Write(aRight); NewLine(); WriteString(\"******************\"); NewLine(); False; ], True );];HoldArgument(\"Verify\",aLeft);UnFence(\"Verify\",2);Macro(\"Verify\", {a,b,message})[ Echo(\"test \", @message); Verify(@a, @b);];";
        scriptMap.put("Verify",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(f1,f2)[  f1(x,n,m):=(x^n-1)*(x^m-1); f2(x,n,m):=x^(n+m)-(x^n)-(x^m)+1; VerifyArithmetic(x,n,m):= [ Verify(f1(x,n,m),f2(x,n,m)); ];];";
        scriptMap.put("VerifyArithmetic",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "VerifyDiv(_u,_v) <--[ Local(q,r); q:=Quotient(u,v); r:=Rem(u,v); Verify(Expand(u),Expand(q*v+r));];";
        scriptMap.put("VerifyDiv",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "VerifySolve(_e1, _e2) <--If (VerifySolveEqual(Eval(e1), Eval(e2)),  True, [  WriteString(\"******************\"); NewLine(); ShowLine(); NewLine(); Write(e1); NewLine(); WriteString(\" evaluates to \"); NewLine(); Write(Eval(e1)); NewLine(); WriteString(\" which differs from \"); NewLine(); Write(e2); NewLine(); WriteString(\"******************\"); NewLine(); False;  ]); HoldArgumentNumber(\"VerifySolve\", 2, 1);10 # VerifySolveEqual({}, {}) <-- True;20 # VerifySolveEqual({}, e2_List?) <-- False;30 # VerifySolveEqual(e1_List?, e2_List?) <--[ Local(i, found); found := False; i := 0; While(i <? Length(e2) And? Not? found) [ i++; found := VerifySolveEqual(First(e1), e2[i]); ]; If (found, VerifySolveEqual(Rest(e1), Delete(e2, i)), False);];40 # VerifySolveEqual(_l1 == _r1, _l2 == _r2) <-- Equal?(l1,l2) And? Simplify(r1-r2)=?0;50 # VerifySolveEqual(_e1, _e2) <-- Simplify(e1-e2) =? 0;";
        scriptMap.put("VerifySolve",scriptString);
        scriptMap.put("VerifySolveEqual",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # LaplaceTransform(_var1,_var2, _expr ) <-- LapTran(var1,var2,expr);10 # LapTran(_var1,_var2,_x + _y) <-- LapTran(var1,var2,x) + LapTran(var1,var2,y);10 # LapTran(_var1,_var2,_x - _y) <-- LapTran(var1,var2,x) - LapTran(var1,var2,y);10 # LapTran(_var1,_var2, - _y) <-- LapTran(var1,var2,y);10 # LapTran(_var1,_var2, c_Constant?*_y) <-- c*LapTran(var1,var2,y);10 # LapTran(_var1,_var2, _y*c_Constant?) <-- c*LapTran(var1,var2,y);10 # LapTran(_var1,_var2, _y/c_Constant?) <-- LapTran(var1,var2,y)/c;10 # LapTran(_var1,_var2, Exp(c_Constant?*_var1)*_expr ) <-- LapTran(var1,var2-c,expr);10 # LapTran(_var1,_var2, Exp(-c_Constant?*_var1)*_expr ) <-- LapTran(var1,var2+c,expr);10 # LapTran(_var1,_var2, _expr*Exp(c_Constant?*_var1) ) <-- LapTran(var1,var2-c,expr);10 # LapTran(_var1,_var2, _expr*Exp(-c_Constant?*_var1) ) <-- LapTran(var1,var2+c,expr);10 # LapTran(_var1,_var2, _expr/_var1 ) <-- Integrate(var2,var2,Infinity) LapTran(var1,var2,expr) ;10 # LapTran(_var1,_var2, _var1*_expr ) <-- - Deriv(var2) LapTran(var1,var2,expr);10 # LapTran(_var1,_var2, _var1^(n_Integer?)*_expr ) <-- (-1)^n * Deriv(var2,n) LapTran(var1,var2,expr);10 # LapTran(_var1,_var2, _expr*_var1 ) <-- - Deriv(var2) LapTran(var1,var2,expr);10 # LapTran(_var1,_var2, _expr*_var1^(n_Integer?) ) <-- (-1)^n * Deriv(var2,n) LapTran(var1,var2,expr);100 # LapTran(_var1,_var2, _expr ) <-- `Hold(LaplaceTransform(@var1,@var2,@expr));LapTranDef(_in,_out) <--[ Local(i,o);  `(50 # LapTran(_t,_s,@in) <-- @out ); i:=Subst(_t,c_PositiveInteger?*_t) in; o:=Subst(s,s/c) out;  `(50 # LapTran(_t,_s,@i ) <-- @o/c ); i:=Subst(_t,_t/c_PositiveInteger?) in; o:=Subst(s,s*c) out;  `(50 # LapTran(_t,_s,@i ) <-- @o*c );];LapTranDef( (_t)^(n_Constant?), Gamma(n+1)/s^(n+1) );LapTranDef( _t, 1/s^2 );LapTranDef( Sqrt(_t), Sqrt(Pi)/(2*s^(3/2)) );LapTranDef( c_FreeOf?({t,s}), c/s );LapTranDef( Sin(_t), 1/(s^2+1) );LapTranDef( Cos(_t), s/(s^2+1) );LapTranDef( Sinh(_t), c/(s^2-1) );LapTranDef( Cosh(_t), s/(s^2-1) );LapTranDef( Exp(_t), 1/(s-1) );LapTranDef( BesselJ(n_Constant?,_t), (Sqrt(s^2+1)-s)^n /Sqrt(s^2+1) );LapTranDef( BesselI(n_Constant?,_t), (s-Sqrt(s^2+1))^n /Sqrt(s^2-1) );LapTranDef( Ln(_t), -(gamma+Ln(s))/s);LapTranDef( Ln(_t)^2, Pi^2/(6*s)+(gamma+Ln(s))/s );LapTranDef( Erf(_t), Exp(s^2/4)*Erfc(s/2)/s );LapTranDef( Erf(Sqrt(_t)), 1/(Sqrt(s+1)*s) );";
        scriptMap.put("LaplaceTransform",scriptString);
        scriptMap.put("LapTran",scriptString);
        scriptMap.put("LapTranDef",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"FSin\",{f,x});RulebaseHoldArguments(\"FCos\",{f,x});RulebaseHoldArguments(\":*:\",{x,y});IsTrig(f) := (Type(f) =? \"Sin\" Or? Type(f) =? \"Cos\");IsFTrig(f) := (Type(f) =? \"FSin\" Or? Type(f) =? \"FCos\");IsMul(f) := (Type(f) =? \"*\");IsMulF(f) := (Type(f) =? \":*:\");IsPow(f):= (Type(f) =? \"^\" And? Integer?(f[2]) And? f[2] >? 1 );RulebaseHoldArguments(\"TrigChange\",{f});RuleHoldArguments(\"TrigChange\",1,1,Type(f)=?\"Cos\") FCos(1,f[1]);RuleHoldArguments(\"TrigChange\",1,1,Type(f)=?\"Sin\") FSin(1,f[1]);RulebaseHoldArguments(\"TrigUnChange\",{f});RuleHoldArguments(\"TrigUnChange\",1,1,Type(f)=?\"FCos\") Cos(f[2]);RuleHoldArguments(\"TrigUnChange\",1,1,Type(f)=?\"FSin\") Sin(f[2]);RulebaseHoldArguments(\"FReplace\",{f});UnFence(\"FReplace\",1);RuleHoldArguments(\"FReplace\",1,1,IsMul(f)) Substitute(f[1]) :*: Substitute(f[2]);RuleHoldArguments(\"FReplace\",1,2,IsPow(f)) (Substitute(f[1]) :*: Substitute(f[1])) :*: Substitute(f[1]^(f[2]-2));RuleHoldArguments(\"FReplace\",1,3,IsTrig(f)) TrigChange(f);FTest(f):=(IsMul(f) Or? IsPow(f) Or? IsTrig(f));FToInternal(f):=Substitute(f,\"FTest\",\"FReplace\");FReplaceBack(f):=(Substitute(f[1])*Substitute(f[2]));UnFence(\"FReplaceBack\",1);FFromInternal(f):=Substitute(f,\"IsMulF\",\"FReplaceBack\"); FLog(s,f):=[];RulebaseHoldArguments(\"FSimpTerm\",{f,rlist});UnFence(\"FSimpTerm\",2);RuleHoldArguments(\"FSimpTerm\",2,1,Type(f) =? \"+\")[ Local(result,lst); lst:=Flatten(f,\"+\"); result:={{},{}};FLog(\"simpadd\",lst); ForEach(tt,lst) [ Local(new); new:=FSimpTerm(tt,{{},{}}); result:={Concat(result[1],new[1]),Concat(result[2],new[2])}; ]; result;];TrigNegate(f):=[ ListToFunction({f[0],-(f[1]),f[2]});];FUnTrig(result) := Substitute(result,\"IsFTrig\",\"TrigUnChange\");RuleHoldArguments(\"FSimpTerm\",2,1,Type(f) =? \"-\" And? ArgumentsCount(f)=?1)[ Local(result); result:=FSimpTerm(f[1],{{},{}}); Substitute(result,\"IsFTrig\",\"TrigNegate\");];RuleHoldArguments(\"FSimpTerm\",2,1,Type(f) =? \"-\" And? ArgumentsCount(f)=?2)[ Local(result1,result2); result1:=FSimpTerm(f[1],{{},{}}); result2:=FSimpTerm(-(f[2]),{{},{}}); {Concat(result1[1],result2[1]),Concat(result1[2],result2[2])};];RuleHoldArguments(\"FSimpTerm\",2,2,Type(f) =? \":*:\")[ FSimpFactor({Flatten(f,\":*:\")});];RuleHoldArguments(\"FSimpTerm\",2,3,Type(f) =? \"FSin\")[ {rlist[1],f~(rlist[2])};];RuleHoldArguments(\"FSimpTerm\",2,3,Type(f) =? \"FCos\")[ {f~(rlist[1]),rlist[2]};];RuleHoldArguments(\"FSimpTerm\",2,4,True)[ {(FCos(f,0))~(rlist[1]),rlist[2]};];FSimpFactor(flist):=[ Local(rlist); rlist:={{},{}};  While(flist !=? {}) [ Local(term);FLog(\"simpfact\",flist); term:=First(flist); flist:=Rest(flist); FProcessTerm(term); ];FLog(\"simpfact\",flist);FLog(\"rlist\",rlist); rlist;];UnFence(\"FSimpFactor\",1);RulebaseHoldArguments(\"FProcessTerm\",{t});UnFence(\"FProcessTerm\",1);RuleHoldArguments(\"FProcessTerm\",1,1,Type(t[1]) =? \"+\")[ Local(split,term1,term2); split:=t[1]; term1:=FlatCopy(t); term2:=FlatCopy(t); term1[1]:=split[1]; term2[1]:=split[2]; DestructiveInsert(flist,1,term1); DestructiveInsert(flist,1,term2);];RuleHoldArguments(\"FProcessTerm\",1,1,Type(t[1]) =? \"-\" And? ArgumentsCount(t[1]) =? 2)[ Local(split,term1,term2); split:=t[1]; term1:=FlatCopy(t); term2:=FlatCopy(t); term1[1]:=split[1]; term2[1]:=split[2]; DestructiveInsert(term2,1,FCos(-1,0)); DestructiveInsert(flist,1,term1); DestructiveInsert(flist,1,term2);];RuleHoldArguments(\"FProcessTerm\",1,1,Length(t)>?1 And? Type(t[2]) =? \"-\" And? ArgumentsCount(t[2]) =? 2)[ Local(split,term1,term2); split:=t[2]; term1:=FlatCopy(t); term2:=FlatCopy(t); term1[2]:=split[1]; term2[2]:=split[2]; DestructiveInsert(term2,1,FCos(-1,0)); DestructiveInsert(flist,1,term1); DestructiveInsert(flist,1,term2);];RuleHoldArguments(\"FProcessTerm\",1,1,Type(t[1]) =? \":*:\")[ Local(split,term); split:=t[1]; term:=FlatCopy(t); term[1]:=split[1]; DestructiveInsert(term,1,split[2]); DestructiveInsert(flist,1,term);];RuleHoldArguments(\"FProcessTerm\",1,1,Length(t)>?1 And? Type(t[2]) =? \":*:\")[ Local(split,term); split:=t[2]; term:=FlatCopy(t); term[2]:=split[1]; DestructiveInsert(term,1,split[2]); DestructiveInsert(flist,1,term);];RuleHoldArguments(\"FProcessTerm\",1,1,Type(t[1]) =? \"-\" And? ArgumentsCount(t[1]) =? 1)[ Local(split,term); split:=t[1]; term:=FlatCopy(t); term[1]:=split[1]; DestructiveInsert(term,1,FCos(-1,0)); DestructiveInsert(flist,1,term);];RuleHoldArguments(\"FProcessTerm\",1,1,Length(t)>?1 And? Type(t[2]) =? \"-\" And? ArgumentsCount(t[2]) =? 1)[ Local(split,term); split:=t[2]; term:=FlatCopy(t); term[2]:=split[1]; DestructiveInsert(term,1,FCos(-1,0)); DestructiveInsert(flist,1,term);];RuleHoldArguments(\"FProcessTerm\",1,1,Length(t)>?1 And? Type(t[2]) =? \"+\")[ Local(split,term1,term2); split:=t[2]; term1:=FlatCopy(t); term2:=FlatCopy(t); term1[2]:=split[1]; term2[2]:=split[2]; DestructiveInsert(flist,1,term1); DestructiveInsert(flist,1,term2);];RuleHoldArguments(\"FProcessTerm\",1,2,Not?(IsFTrig(t[1])) )[ t[1]:=FCos(t[1],0); DestructiveInsert(flist,1,t);];RuleHoldArguments(\"FProcessTerm\",1,2,Length(t)>?1 And? Not?(IsFTrig(t[2])) )[ t[2]:=FCos(t[2],0); DestructiveInsert(flist,1,t);];RuleHoldArguments(\"FProcessTerm\",1,4,Length(t)=?1 And? Type(t[1]) =? \"FCos\")[ DestructiveInsert(rlist[1],1,t[1]);];RuleHoldArguments(\"FProcessTerm\",1,4,Length(t)=?1 And? Type(t[1]) =? \"FSin\")[ DestructiveInsert(rlist[2],1,t[1]);];RuleHoldArguments(\"FProcessTerm\",1,5,Length(t)>?1)[ Local(x,y,term1,term2,news); x:=t[1]; y:=t[2]; news:=TrigSimpCombineB(x,y);  t:=Rest(t); term1:=FlatCopy(t); term2:=FlatCopy(t); term1[1]:=news[1]; term2[1]:=news[2]; DestructiveInsert(flist,1,term1); DestructiveInsert(flist,1,term2);];RulebaseHoldArguments(\"TrigSimpCombineB\",{x,y});RuleHoldArguments(\"TrigSimpCombineB\",2,1,Type(x) =? \"FCos\" And? Type(y) =? \"FCos\") { FCos((x[1]*y[1])/2,x[2]+y[2]) , FCos((x[1]*y[1])/2,x[2]-y[2]) };RuleHoldArguments(\"TrigSimpCombineB\",2,1,Type(x) =? \"FSin\" And? Type(y) =? \"FSin\") { FCos(-(x[1]*y[1])/2,x[2]+y[2]) , FCos((x[1]*y[1])/2,x[2]-y[2]) };RuleHoldArguments(\"TrigSimpCombineB\",2,1,Type(x) =? \"FSin\" And? Type(y) =? \"FCos\") { FSin((x[1]*y[1])/2,x[2]+y[2]) , FSin( (x[1]*y[1])/2,x[2]-y[2]) };RuleHoldArguments(\"TrigSimpCombineB\",2,1,Type(x) =? \"FCos\" And? Type(y) =? \"FSin\") { FSin((x[1]*y[1])/2,x[2]+y[2]) , FSin(-(x[1]*y[1])/2,x[2]-y[2]) };RulebaseHoldArguments(\"TrigSimpCombine\",{f});RuleHoldArguments(\"TrigSimpCombine\",1,1,List?(f)) Map(\"TrigSimpCombine\",{f});RuleHoldArguments(\"TrigSimpCombine\",1,10,True)[ Local(new,varlist); new:=f;  varlist:=VarList(f); new:=FToInternal(new);FLog(\"Internal\",new);   Local(terms); terms:=FSimpTerm(new,{{},{}}); FLog(\"terms\",terms);  Local(cassoc,sassoc); cassoc:={}; sassoc:={}; ForEach(item,terms[1]) [ CosAdd(item); ]; ForEach(item,terms[2]) [ SinAdd(item); ];FLog(\"cassoc\",cassoc);FLog(\"sassoc\",sassoc);  Local(result); result:=0; ForEach(item,cassoc) [FLog(\"item\",item); result:=result+Expand(FUnTrig(FFromInternal(item[2])))*Cos(item[1]); ]; ForEach(item,sassoc) [FLog(\"item\",item); result:=result+Expand(FUnTrig(FFromInternal(item[2])))*Sin(item[1]); ]; result;];CosAdd(t):=[ Local(look,arg); arg:=Expand(t[2],varlist); look:=Assoc(arg,cassoc); If(look =? Empty, [ arg:=Expand(-arg,varlist); look:=Assoc(arg,cassoc); If(look =? Empty, DestructiveInsert(cassoc,1,{arg,t[1]}), look[2]:=look[2]+t[1] ); ] , look[2]:=look[2]+t[1] );];UnFence(\"CosAdd\",1);SinAdd(t):=[ Local(look,arg); arg:=Expand(t[2],varlist); look:=Assoc(arg,sassoc); If(look =? Empty, [ arg:=Expand(-arg,varlist); look:=Assoc(arg,sassoc); If(look =? Empty, DestructiveInsert(sassoc,1,{arg,-(t[1])}), look[2]:=look[2]-(t[1]) ); ] , look[2]:=look[2]+t[1] );];UnFence(\"SinAdd\",1);";
        scriptMap.put("TrigSimpCombine",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # BigOh(UniVariate(_var,_first,_coefs),_var,_degree) <-- [ While(first+Length(coefs)>=?(degree+1) And? Length(coefs)>?0) DestructiveDelete(coefs,Length(coefs)); UniVariate(var,first,coefs); ];20 # BigOh(_uv,_var,_degree)_CanBeUni(uv,var) <-- NormalForm(BigOh(MakeUni(uv,var),var,degree));";
        scriptMap.put("BigOh",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"CanBeUni\",{expression}) CanBeUni(UniVarList(expression),expression);10 # CanBeUni({},_expression) <-- True;20 # CanBeUni(var_List?,_expression) <-- CanBeUni(First(var),expression) And? CanBeUni(Rest(var),expression);30 # CanBeUni(_var,expression_Atom?) <-- True;35 # CanBeUni(_var,expression_FreeOf?(var)) <-- True;40 # CanBeUni(_var,_x + _y) <-- CanBeUni(var,x) And? CanBeUni(var,y);40 # CanBeUni(_var,_x - _y) <-- CanBeUni(var,x) And? CanBeUni(var,y);40 # CanBeUni(_var, + _y) <-- CanBeUni(var,y);40 # CanBeUni(_var, - _y) <-- CanBeUni(var,y);40 # CanBeUni(_var,_x * _y) <-- CanBeUni(var,x) And? CanBeUni(var,y);40 # CanBeUni(_var,_x / _y) <-- CanBeUni(var,x) And? FreeOf?(var,y);40 # CanBeUni(_var,_x ^ y_Integer?)_(y >=? 0 And? CanBeUni(var,x)) <-- True;41 # CanBeUni(_var,(x_FreeOf?(var)) ^ (y_FreeOf?(var))) <-- True;50 # CanBeUni(_var,UniVariate(_var,_first,_coefs)) <-- True;1000 # CanBeUni(_var,_f)_(Not?(FreeOf?(var,f))) <-- False;1001 # CanBeUni(_var,_f) <-- True;";
        scriptMap.put("CanBeUni",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # Coef(uv_UniVar?,order_List?) <--[ Local(result); result:={}; ForEach(item,order) [ DestructiveAppend(result,Coef(uv,item)); ]; result;];10 # Coef(uv_UniVar?,order_Integer?)_(order<?uv[2]) <-- 0;10 # Coef(uv_UniVar?,order_Integer?)_(order>=?uv[2]+Length(uv[3])) <-- 0;20 # Coef(uv_UniVar?,order_Integer?) <-- uv[3][(order-uv[2])+1];30 # Coef(uv_CanBeUni,_order)_(Integer?(order) Or? List?(order)) <-- Coef(MakeUni(uv),order);Function(\"Coef\",{expression,var,order}) NormalForm(Coef(MakeUni(expression,var),order));";
        scriptMap.put("Coef",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # CollectOn(_var,_expr)_(CanBeUni(var,expr)) <--[ If(InVerboseMode(),Echo(\"<< Collect on: \",var,\" in expression \",expr));  Local(u,a); u := MakeUni(expr,var); If( u[2] >? 0,  [ a := FillList(0,u[2]); u[3] := Concat(a,u[3]); u[2] := 0; ] ); u[3];];";
        scriptMap.put("CollectOn",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Content(UniVariate(_var,_first,_coefs)) <-- Gcd(coefs)*var^first;20 # Content(poly_CanBeUni) <-- NormalForm(Content(MakeUni(poly)));";
        scriptMap.put("Content",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Degree\",{expr});RuleHoldArguments(\"Degree\",1,0, UniVar?(expr))[ Local(i,min,max); min:=expr[2]; max:=min+Length(expr[3]); i:=max; While(i >=? min And? Zero?(Coef(expr,i))) i--; i;];10 # Degree(poly_CanBeUni) <-- Degree(MakeUni(poly));10 # Degree(_poly,_var)_(CanBeUni(var,poly)) <-- Degree(MakeUni(poly,var));20 # Degree(_poly,_var)_(Type(poly)=?\"Sqrt\") <-- Degree(poly^2,var)/2;20 # Degree(_poly,_var)_(FunctionToList(poly)[1]=? ^) <--[ Local(ex,pwr,deg); ex := FunctionToList(poly)[3]; pwr := 1/ex;  deg := Degree(poly^pwr,var);  deg*ex;];";
        scriptMap.put("Degree",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "DivPoly(_A,_B,_var,_deg) <--[ Local(a,b,c,i,j,denom); b:=MakeUni(B,var); denom:=Coef(b,0); if (denom =? 0) [ Local(f); f:=Content(b); b:=PrimitivePart(b); A:=Simplify(A/f); denom:=Coef(b,0); ]; a:=MakeUni(A,var); c:=FillList(0,deg+1); For(i:=0,i<=?deg,i++) [ Local(sum,j); sum:=0; For(j:=0,j<?i,j++) [ sum := sum + c[j+1]*Coef(b,i-j); ]; c[i+1] := (Coef(a,i)-sum) / denom; ]; NormalForm(UniVariate(var,0,c));];";
        scriptMap.put("DivPoly",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "DropEndZeroes(list):=[ Local(end); end:=Length(list); While(list[end] =? 0) [ DestructiveDelete(list,end); end:=end-1; ];];";
        scriptMap.put("DropEndZeroes",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"ExpandUniVariate\",{var,first,coefs})[ Local(result,i); result:=0; For(i:=Length(coefs),i>?0,i--) [ Local(term); term:=NormalForm(coefs[i])*var^(first+i-1); result:=result+term; ]; result;];";
        scriptMap.put("ExpandUniVariate",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Horner(_e,_v) <--[ Local(uni,coefs,result); uni := MakeUni(e,v); coefs:=DestructiveReverse(uni[3]); result:=0; While(coefs !=? {}) [ result := result*v; result := result+First(coefs); coefs := Rest(coefs); ]; result:=result*v^uni[2]; result;];";
        scriptMap.put("Horner",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # LeadingCoef(uv_UniVar?) <-- Coef(uv,Degree(uv));20 # LeadingCoef(uv_CanBeUni) <--[ Local(uvi); uvi:=MakeUni(uv); Coef(uvi,Degree(uvi));];10 # LeadingCoef(uv_CanBeUni(var),_var) <--[ Local(uvi); uvi:=MakeUni(uv,var); Coef(uvi,var,Degree(uvi));];";
        scriptMap.put("LeadingCoef",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"MakeUni\",{expression}) MakeUni(expression,UniVarList(expression));RulebaseHoldArguments(\"MakeUni\",{expression,var});5 # MakeUni(_expr,{}) <-- UniVariate(dummyvar,0,{expression});6 # MakeUni(_expr,var_List?) <--[ Local(result,item); result:=expression; ForEach(item,var) [ result:=MakeUni(result,item); ]; result;];10 # MakeUni(UniVariate(_var,_first,_coefs),_var) <-- UniVariate(var,first,coefs);20 # MakeUni(UniVariate(_v,_first,_coefs),_var) <--[ Local(reslist,item); reslist:={}; ForEach(item,expression[3]) [ If(FreeOf?(var,item), DestructiveAppend(reslist,item), DestructiveAppend(reslist,MakeUni(item,var)) ); ]; UniVariate(expression[1],expression[2],reslist);];LocalSymbols(a,b,var,expression)[ 20 # MakeUni(expression_FreeOf?(var),_var) <-- UniVariate(var,0,{expression}); 30 # MakeUni(_var,_var) <-- UniVariate(var,1,{1}); 30 # MakeUni(_a + _b,_var) <-- MakeUni(a,var) + MakeUni(b,var); 30 # MakeUni(_a - _b,_var) <-- MakeUni(a,var) - MakeUni(b,var); 30 # MakeUni( - _b,_var) <-- - MakeUni(b,var); 30 # MakeUni(_a * _b,_var) <-- MakeUni(a,var) * MakeUni(b,var); 1 # MakeUni(_a ^ n_Integer?,_var) <-- MakeUni(a,var) ^ n; 30 # MakeUni(_a / (b_FreeOf?(var)),_var) <-- MakeUni(a,var) * (1/b);];";
        scriptMap.put("MakeUni",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Monic(UniVariate(_var,_first,_coefs)) <--[ DropEndZeroes(coefs); UniVariate(var,first,coefs/coefs[Length(coefs)]);];20 # Monic(poly_CanBeUni) <-- NormalForm(Monic(MakeUni(poly)));30 # Monic(_poly,_var)_CanBeUni(poly,var) <-- NormalForm(Monic(MakeUni(poly,var)));";
        scriptMap.put("Monic",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # PrimitivePart(UniVariate(_var,_first,_coefs)) <-- UniVariate(var,0,coefs/Gcd(coefs));20 # PrimitivePart(poly_CanBeUni) <-- NormalForm(PrimitivePart(MakeUni(poly)));";
        scriptMap.put("PrimitivePart",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RepeatedSquaresMultiply(_a,- (n_Integer?)) <-- 1/RepeatedSquaresMultiply(a,n);15 # RepeatedSquaresMultiply(UniVariate(_var,_first,{_coef}),(n_Integer?)) <-- UniVariate(var,first*n,{coef^n});20 # RepeatedSquaresMultiply(_a,n_Integer?) <--[ Local(m,b); Bind(m,1); Bind(b,1); While(m<=?n) Bind(m,(ShiftLeft(m,1))); Bind(m, ShiftRight(m,1)); While(m>?0) [ Bind(b,b*b); If (Not?(Equal?(BitAnd(m,n), 0)),Bind(b,b*a)); Bind(m, ShiftRight(m,1)); ]; b;];";
        scriptMap.put("RepeatedSquaresMultiply",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"UniDivide\",{u,v})[ Local(m,n,q,r,k,j); m := Length(u)-1; n := Length(v)-1; While (m>?0 And? Zero?(u[m+1])) m--; While (n>?0 And? Zero?(v[n+1])) n--; q := ZeroVector(m-n+1); r := FlatCopy(u);  For(k:=m-n,k>=?0,k--) [ q[k+1] := r[n+k+1]/v[n+1]; For (j:=n+k-1,j>=?k,j--) [ r[j+1] := r[j+1] - q[k+1]*v[j-k+1]; ]; ]; Local(end); end:=Length(r); While (end>?n) [ DestructiveDelete(r,end); end:=end-1; ]; {q,r};];";
        scriptMap.put("UniDivide",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"UniGcd\",{u,v})[ Local(l,div,mod,m); DropEndZeroes(u); DropEndZeroes(v); l:=UniDivide(u,v); div:=l[1]; mod:=l[2]; DropEndZeroes(mod); m := Length(mod); If(m =? 0, v, UniGcd(v,mod));];";
        scriptMap.put("UniGcd",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"UniTaylor\",{taylorfunction,taylorvariable,taylorat,taylororder})[ Local(n,result,dif,polf); result:={}; [ MacroLocal(taylorvariable); MacroBind(taylorvariable,taylorat); DestructiveAppend(result,Eval(taylorfunction)); ]; dif:=taylorfunction; polf:=(taylorvariable-taylorat); For(n:=1,n<=?taylororder,n++) [ dif:= Deriv(taylorvariable) dif; MacroLocal(taylorvariable); MacroBind(taylorvariable,taylorat); DestructiveAppend(result,(Eval(dif)/n!)); ]; UniVariate(taylorvariable,0,result);];";
        scriptMap.put("UniTaylor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "UniVarList(expr) := VarList(expr);";
        scriptMap.put("UniVarList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # UniVar?(UniVariate(_var,_first,_coefs)) <-- True;20 # UniVar?(_anything) <-- False;200 # aLeft_UniVar? ^ aRight_PositiveInteger? <-- RepeatedSquaresMultiply(aLeft,aRight);200 # aLeft_UniVar? - aRight_UniVar? <--[ Local(from,result); Local(curl,curr,left,right); curl:=aLeft[2]; curr:=aRight[2]; left:=aLeft[3]; right:=aRight[3]; result:={}; from:=Minimum(curl,curr); While(curl<?curr And? left !=? {}) [ DestructiveAppend(result,First(left)); left:=Rest(left); curl++; ]; While(curl<?curr) [ DestructiveAppend(result,0); curl++; ]; While(curr<?curl And? right !=? {}) [ DestructiveAppend(result,-First(right)); right:=Rest(right); curr++; ]; While(curr<?curl) [ DestructiveAppend(result,0); curr++; ]; While(left !=? {} And? right !=? {}) [ DestructiveAppend(result,First(left)-First(right)); left := Rest(left); right := Rest(right); ]; While(left !=? {}) [ DestructiveAppend(result,First(left)); left := Rest(left); ]; While(right !=? {}) [ DestructiveAppend(result,-First(right)); right := Rest(right); ]; UniVariate(aLeft[1],from,result);];201 # (aLeft_UniVar? * _aRight)_((FreeOf?(aLeft[1],aRight))) <--[ aRight*aLeft;];";
        scriptMap.put("UniVar?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ShiftUniVar(UniVariate(_var,_first,_coefs),_fact,_shift) <-- [ UniVariate(var,first+shift,fact*coefs); ];RulebaseHoldArguments(\"UniVariate\",{var,first,coefs});RuleHoldArguments(\"UniVariate\",3,10,Length(coefs)>?0 And? coefs[1]=?0) UniVariate(var,first+1,Rest(coefs));RuleHoldArguments(\"UniVariate\",3,1000,Complex?(var) Or? List?(var)) ExpandUniVariate(var,first,coefs);500 # UniVariate(_var,_f1,_c1) + UniVariate(_var,_f2,_c2) <--[ Local(from,result); Local(curl,curr,left,right); Bind(curl, f1); Bind(curr, f2); Bind(left, c1); Bind(right, c2); Bind(result, {}); Bind(from, Minimum(curl,curr)); While(And?(LessThan?(curl,curr),left !=? {})) [ DestructiveAppend(result,First(left)); Bind(left,Rest(left)); Bind(curl,AddN(curl,1)); ]; While(LessThan?(curl,curr)) [ DestructiveAppend(result,0); Bind(curl,AddN(curl,1)); ]; While(And?(LessThan?(curr,curl), right !=? {})) [ DestructiveAppend(result,First(right)); Bind(right,Rest(right)); Bind(curr,AddN(curr,1)); ]; While(LessThan?(curr,curl)) [ DestructiveAppend(result,0); Bind(curr,AddN(curr,1)); ]; While(And?(left !=? {}, right !=? {})) [ DestructiveAppend(result,First(left)+First(right)); Bind(left, Rest(left)); Bind(right, Rest(right)); ]; While(left !=? {}) [ DestructiveAppend(result,First(left)); Bind(left, Rest(left)); ]; While(right !=? {}) [ DestructiveAppend(result,First(right)); Bind(right, Rest(right)); ]; UniVariate(var,from,result);];200 # UniVariate(_var,_first,_coefs) + a_Number? <-- UniVariate(var,first,coefs) + UniVariate(var,0,{a});200 # a_Number? + UniVariate(_var,_first,_coefs) <-- UniVariate(var,first,coefs) + UniVariate(var,0,{a});200 # - UniVariate(_var,_first,_coefs) <-- UniVariate(var,first,-coefs);200 # (_factor * UniVariate(_var,_first,_coefs))_((FreeOf?(var,factor))) <-- UniVariate(var,first,coefs*factor);200 # (UniVariate(_var,_first,_coefs)/_factor)_((FreeOf?(var,factor))) <-- UniVariate(var,first,coefs/factor);200 # UniVariate(_var,_f1,_c1) * UniVariate(_var,_f2,_c2) <--[ Local(i,j,n,shifted,result); Bind(result,MakeUni(0,var)); Bind(n,Length(c1)); For(i:=1,i<=?n,i++) [ Bind(result,result+ShiftUniVar(UniVariate(var,f2,c2),MathNth(c1,i),f1+i-1)); ]; result;];";
        scriptMap.put("UniVariate",scriptString);
        scriptMap.put("ShiftUniVar",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function (\"UniVariateBinomial\",{x,q,a})[Local(L,i);L := {a};For (i:=1,i<?q,i++) DestructiveAppend(L,0);DestructiveAppend(L,1);UniVariate(x,0,L);];Function(\"SubstituteInUniVar\",{p,k})[ Local(c,i,d,j,NL); L := p[3];  NL := {};  d := Degree(p); i :=d; ForEach(c,L) [   If (i<?d, For (j:=1,j<?k,j++) DestructiveAppend(NL,0));  DestructiveAppend(NL,If(Even?(i),c,-c)); i--; ]; UniVariate(First(p),0,NL);];Function(\"SubstituteAndExpandInUniVar\",{p,k})[ Local(result,i,var,first,coefs,c,nc,exponent); result:=0; var := p[1]; first:= p[2]; coefs:= p[3]; For(i:=Length(coefs),i>?0,i--) [ Local(term); exponent := first+i-1; c:= coefs[i]; nc := If(Even?(exponent),c,-c); term:=NormalForm(nc*var^(exponent*k)); result:=result+term; ]; result;];CyclotomicDivisorsList(n_PositiveInteger?) <--[ Local(nFactors,f,result,oldresult,x); nFactors:= Factors(n); result := {{1,nFactors[1][1],1}}; nFactors := Rest(nFactors); ForEach (f,nFactors) [ oldresult := result; ForEach (x,oldresult) result:=Append(result,{x[1]*f[1],x[2]*f[1],-x[3]}); ]; result;];CyclotomicFactor(_a,_b) <--[ Local(coef,p,i,j,result); p := a/b; result:= {{b*(p-1),1}}; For (i:= p-2,i>=?0,i--) DestructiveAppend(result,{b*i,1}); result;];10 # InternalCyclotomic(n_Even?,_x) <-- [ Local(k,m,result,p,t); k := 1; m := n; While(Even?(m)) [ k := k*2; m := m/2; ]; k := k/2 ; If(m>?1, [ p:= InternalCyclotomic(m,x)[2];  result:={}; ForEach(t,p) DestructiveAppend(result, {t[1]*k,If(Even?(t[1]),t[2],-t[2])}); ], result := {{k,1},{0,1}}  ); SparseUniVar(x,result); ];20 # InternalCyclotomic(n_Odd?,_x)_(n>?1) <--[ Local(divisors,poly1,poly2,q,d,f,coef,i,j,result); divisors := CyclotomicDivisorsList(n); poly1 := {{0,1}}; poly2 := {{0,1}}; ForEach (d,divisors) [ If(InVerboseMode(),Echo(\"d=\",d)); f:= CyclotomicFactor(n/d[1],n/d[2]); If (d[3]=?1,poly1:=MultiplyTerms(poly1,f),poly2:=MultiplyTerms(poly2,f)); If(InVerboseMode(), [ Echo(\"poly1=\",poly1); Echo(\"poly2=\",poly2); ]); ]; If(InVerboseMode(),Echo(\"End ForEach\")); result := If(poly2=?{{0,1}},poly1,DivTermList(poly1,poly2)); SparseUniVar(x,result);];10 # Cyclotomic(1,_x) <-- x-1;20 # Cyclotomic(n_Integer?,_x) <-- ExpandSparseUniVar(InternalCyclotomic(n,x));";
        scriptMap.put("Cyclotomic",scriptString);
        scriptMap.put("CyclotomicDivisorsList",scriptString);
        scriptMap.put("CyclotomicFactor",scriptString);
        scriptMap.put("InternalCyclotomic",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # OldInternalCyclotomic(n_Even?,_x,WantNormalForm_Boolean?) <-- [ Local(k,m,p); k := 1; m := n; While(Even?(m)) [ k := k*2; m := m/2; ]; k := k/2 ; If(m>?1, [ p := OldInternalCyclotomic(m,x,False); If (WantNormalForm, SubstituteAndExpandInUniVar(p,k),SubstituteInUniVar(p,k)); ], If (WantNormalForm, x^k+1, UniVariateBinomial(x,k,1)) ); ];20 # OldInternalCyclotomic(n_Odd?,_x,WantNormalForm_Boolean?)_(n>?1) <--[ Local(divisors,poly1,poly2,q,d,f,result); divisors := MoebiusDivisorsList(n); poly1 :=1 ; poly2 := 1; ForEach (d,divisors) [ q:=n/d[1]; f:=UniVariateBinomial(x,q,-1); If (d[2]=?1,poly1:=poly1*f,poly2:=poly2*f); ]; result := Quotient(poly1,poly2); If(WantNormalForm,NormalForm(result),result);];10 # OldCyclotomic(1,_x) <-- _x-1;20 # OldCyclotomic(n_Integer?,_x) <-- OldInternalCyclotomic(n,x,True);";
        scriptMap.put("OldCyclotomic",scriptString);
        scriptMap.put("OldInternalCyclotomic",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # UniVariateCyclotomic(1,_x) <-- UniVariate(x,0,{-1,1});20 # UniVariateCyclotomic(n_Integer?,_x) <-- OldInternalCyclotomic(n,x,False);";
        scriptMap.put("UniVariateCyclotomic",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"AddTerm\",{termlist,term,begining})[ Local(l,i); l := Length(termlist); If(term[2]!=?0, [ i:=begining; If (l>=?1, While ((i<=?l) And? (term[1]<?termlist[i][1])) i++); If (i>?l, [DestructiveAppend(termlist,term);i++;], If (term[1]=?termlist[i][1], [ Local(nc); nc:=termlist[i][2]+term[2]; If(nc!=?0,DestructiveReplace(termlist,i,{term[1],nc}), [DestructiveDelete(termlist,i);i--;]); ], DestructiveInsert(termlist,i,term)) ); ] ); i+1;];";
        scriptMap.put("AddTerm",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"AddTerms\",{terms1,terms2})[ Local(result,begining,t); begining :=1; ForEach (t,terms2) begining :=AddTerm(terms1,t,begining); terms1;];";
        scriptMap.put("AddTerms",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"DivTermList\",{a,b})[ Local(q,nq,t,c,begining); q := {};  While ((a!=?{}) And? a[1][1]>=?b[1][1]) [ begining := 1; If(InVerboseMode(),Echo(\"degree=\",a[1][1])); nq := {a[1][1]-b[1][1],a[1][2]/b[1][2]};  DestructiveAppend(q,nq);  ForEach (t,b) begining := AddTerm(a,{t[1]+nq[1],-t[2]*nq[2]},begining); ];  q;];";
        scriptMap.put("DivTermList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"ExpandSparseUniVar\",{s})[ Local(result,t,var,termlist); result :=0; var := s[1]; termlist := s[2]; ForEach (t,termlist) [ Local(term); term := NormalForm(t[2]*var^t[1]); result := result + term; ]; result;];";
        scriptMap.put("ExpandSparseUniVar",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # MakeSparseUniVar(poly_CanBeUni,var_Atom?) <--[ If(InVerboseMode(),Tell(\"MakeSparseUniVar\",{var,poly})); Local(uni,first,coeffs,n,c,lc,termlist,term); uni := MakeUni(poly,var); If(InVerboseMode(),Tell(\" \",uni)); first := uni[2]; coeffs := (uni[3]); If(InVerboseMode(),[Tell(\" \",first); Tell(\" \",coeffs);]); termlist := {}; lc := Length(coeffs); For(n:=0,n<?lc,n++) [ c := coeffs[n+1]; term := {n+first,c}; If(InVerboseMode(),Tell(\" \",term)); If(c !=? 0, Push(termlist,term)); ]; If(InVerboseMode(),Tell(\" \",{var,termlist})); {var,termlist};];";
        scriptMap.put("MakeSparseUniVar",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"MultiplySingleTerm\",{termlist,term})[ Local(result,t); result:={}; If(term[2]!=?0, ForEach (t,termlist) DestructiveAppend(result,{t[1]+term[1],t[2]*term[2]}) ); result;];";
        scriptMap.put("MultiplySingleTerm",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"MultiplyTerms\",{terms1,terms2})[ Local(result,t1,t2,begining); result:={}; ForEach (t1,terms1) [ begining :=1; ForEach (t2,terms2) begining := AddTerm(result,{t1[1]+t2[1],t1[2]*t2[2]},1); ]; result;];";
        scriptMap.put("MultiplyTerms",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"SparseUniVar\",{var,termlist});300 # SparseUniVar(_var,_terms1) * SparseUniVar(_var,_terms2) <--SparseUniVar(var, MultiplyTerms(terms1,terms2));300 # SparseUniVar(_var,_terms1) + SparseUniVar(_var,_terms2) <--SparseUniVar(var, AddTerms(terms1,terms2));300 # SparseUniVar(_var,_terms1) - SparseUniVar(_var,_terms2) <--SparseUniVar(var, SubstractTerms(terms1,terms2));";
        scriptMap.put("SparseUniVar",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"SubtractTerms\",{terms1,terms2})[ Local(result,t); begining :=1 ; ForEach (t,terms2) begining := AddTerm(terms1,{t[1],-t[2]},1); terms1;];";
        scriptMap.put("SubtractTerms",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BoundRealRoots(_p) <--[ BoundRealRoots(p,MinimumBound(p),MaximumBound(p));];BoundRealRoots(_p,_Mmin,_Mmax) <--[ Local(S,N,work,result,Vmin,Vmax,a,b,Va,Vb,c,Vc,x); result:={}; if (Zero?(p Where x==0)) [ p:=Simplify(p/x); result:={{0,0}}; ]; S:=SturmSequence(p); Vmin := SturmVariations(S,-Infinity); Vmax := SturmVariations(S,Infinity); N:=Vmin - Vmax; work:={}; if (N=?1) [ result:={{-Mmax,Mmax}}; ]; if (N>?1) [ work := { {-Mmax,-Mmin,Vmin,SturmVariations(S,-Mmin)}, { Mmin, Mmax,SturmVariations(S, Mmin),Vmax} }; ]; While(work !=? {}) [ {a,b,Va,Vb} := First(work); work := Rest(work); c:=(a+b)/2; Vc := SturmVariations(S,c); if (Zero?(p Where x == c)) [ Local(M,Vcmin,Vcplus,pnew); pnew := Simplify((p Where x == x+c)/x); M:=MinimumBound(pnew); Vcmin := SturmVariations(S, c-M); Vcplus := SturmVariations(S, c+M); result:=Concat(result,{{c,c}}); if (Va =? Vcmin+1) [ result:=Concat(result,{{a,c-M}}); ]; if (Va >? Vcmin+1) [ work:=Concat(work,{{a,c-M,Va,Vcmin}}); ]; if (Vb =? Vcplus-1) [ result:=Concat(result,{{c+M,b}}); ]; if (Vb <? Vcplus-1) [ work:=Concat(work,{{c+M,b,Vcplus,Vb}}); ]; ] else [ if (Va =? Vc+1) [ result:=Concat(result,{{a,c}}); ]; if (Va >? Vc+1) [ work:=Concat(work,{{a,c,Va,Vc}}); ]; if (Vb =? Vc-1) [ result:=Concat(result,{{c,b}}); ]; if (Vb <? Vc-1) [ work:=Concat(work,{{c,b,Vc,Vb}}); ]; ]; ]; result;];";
        scriptMap.put("BoundRealRoots",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "iDebug:=False;FindRealRoots(_p) <--[ If(iDebug,Tell(\"FindRealRoots\",p)); Local(vars,var,cc,pp,vlcc,zeroRoot,minb,maxb,rr); vars := VarList(p); var := vars[1]; cc := Content(p); pp := PrimitivePart(p); If(iDebug,Tell(\" \",{cc,pp})); vlcc := VarList(cc); If(Length(vlcc)>?0 And? Contains?(vlcc,var), zeroRoot:=True,zeroRoot:=False); p:=SquareFree(Rationalize(pp)); If(iDebug,Tell(\" after sqf\",p)); minb := MinimumBound(p); maxb := MaximumBound(p); If(iDebug,Tell(\" \",{minb,maxb})); rr := FindRealRoots(p,minb,maxb); If(zeroRoot,DestructiveAppend(rr,0)); rr;];FindRealRoots(_p,_Mmin,_Mmax) <--[ If(iDebug,Tell(\" FindRealRoots3\",{p,Mmin,Mmax})); Local(bounds,result,i,prec,requiredPrec); bounds := BoundRealRoots(p,Mmin,Mmax); If(iDebug,Tell(\" \",{bounds,Length(bounds)})); result:=FillList(0,Length(bounds)); requiredPrec := BuiltinPrecisionGet(); BuiltinPrecisionSet(BuiltinPrecisionGet()+4); prec:=10^-(requiredPrec+1); For(i:=1,i<=?Length(bounds),i++) [ If(iDebug,Tell(i)); Local(a,b,c,r); {a,b} := bounds[i]; c:=N(Eval((a+b)/2)); If(iDebug,Tell(\" \",{a,b,c})); r := Fail; If(iDebug,Tell(\" newt1\",`Hold(Newton(@p,x,@c,@prec,@a,@b)))); if (a !=? b) [r := `Newton(@p,x,@c,prec,a,b);]; If(iDebug,Tell(\" newt2\",r)); if (r =? Fail) [ Local(c,cold,pa,pb,pc); pa:=(p Where x==a); pb:=(p Where x==b); c:=((a+b)/2); cold := a; While (Abs(cold-c)>?prec) [ pc:=(p Where x==c); If(iDebug,Tell(\" \",{a,b,c})); if (Abs(pc) <? prec) [ a:=c; b:=c; ] else if (pa*pc <? 0) [ b:=c; pb:=pc; ] else [ a:=c; pa:=pc; ]; cold:=c; c:=((a+b)/2); ]; r:=c; ]; result[i] := N(Eval((r/10)*(10)),requiredPrec); ]; BuiltinPrecisionSet(requiredPrec); result;];";
        scriptMap.put("FindRealRoots",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " 5 # MaximumBound(_p)_(Zero?(p Where x==0)) <-- MaximumBound(Simplify(p/x));10 # MaximumBound(_p)_(Degree(p)>?0) <--[ Local(an); an:=Coef(p,(Degree(p)-1) .. 0)/Coef(p,Degree(p)); an := N(Eval(Abs(an)^(1/(1 .. Degree(p))))); Rationalize(2*Maximum(an));];20 # MaximumBound(_p) <-- Infinity;";
        scriptMap.put("MaximumBound",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # MinimumBound(_p)_(Zero?(p Where x==0)) <-- 0;20 # MinimumBound(_p)_(Degree(p)>?0) <--[ Local(an,result); an:=Coef(p,1 .. (Degree(p)))/Coef(p,0); an := N(Eval(Abs(an)^(1/(1 .. Degree(p))))); result:=0; an:=2*Maximum(an); if(Not? Zero?(an)) [result := 1/an;]; Simplify(Rationalize(result));];30 # MinimumBound(_p) <-- -Infinity;";
        scriptMap.put("MinimumBound",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RealRootsCount(_p) <--[ Local(S); p:=SquareFree(Rationalize(p)); S:=SturmSequence(p); SturmVariations(S,-Infinity)-SturmVariations(S,Infinity);];";
        scriptMap.put("RealRootsCount",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # SquareFree(_p)_(Length(VarList(p))!=?1) <-- Check(False,\"Argument\",\"Input must be Univariate\");12 # SquareFree(_p) <-- SquareFree(p,VarList(p)[1]);14 # SquareFree(_p,_var)_(Not? Polynomial?(p,var)) <-- Check(False,\"Argument\",\"Input must be Univariate Polynomial\");16 # SquareFree(_p,_var) <--[  Quotient(p,Gcd(p,(`(Differentiate(@var)(@p)))));];";
        scriptMap.put("SquareFree",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # SturmSequence(_p,_var) <--[ Local(result,i,deg,nt); If(InVerboseMode(),Tell(10)); result := {p,`Differentiate(@var)(@p)}; deg := Degree(p,var); For(i:=3,i<=?deg+1,i++) [ nt := -NormalForm(MultiDivide(MM(result[i-2],{var}),{MM(result[i-1],{var})})[2]); DestructiveAppend(result,nt); ]; result;];20 # SturmSequence(_p)_(Length(VarList(p))=?1) <-- SturmSequence(p,VarList(p)[1]);30 # SturmSequence(_p) <-- Check(Length(VarList(p))=?1,\"Argument\",\"Input must be Univariate Polynomial. \");";
        scriptMap.put("SturmSequence",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # SturmVariations(_S,Infinity) <--[ Local(i,s); s:=FillList(0,Length(S)); For(i:=1,i<=?Length(S),i++) [ s[i] := LeadingCoef(S[i]); ]; SturmVariations(s);];10 # SturmVariations(_S,-Infinity) <--[ Local(i,s); s:=FillList(0,Length(S)); For(i:=1,i<=?Length(S),i++) [ s[i] := ((-1)^Degree(S[i]))*LeadingCoef(S[i]); ]; SturmVariations(s);];20 # SturmVariations(_S,_x) <-- SturmVariations(Eval(S));SturmVariations(_S) <--[ Local(result,prev); result:=0; While(Length(S)>?0 And? Zero?(S[1])) S:=Rest(S); if (Length(S)>?0) [ prev:=S[1]; ForEach(item,Rest(S)) [ if(Not? Zero?(item)) [ if (prev*item <? 0) [result++;]; prev:=item; ]; ]; ]; result;];";
        scriptMap.put("SturmVariations",scriptString);
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
