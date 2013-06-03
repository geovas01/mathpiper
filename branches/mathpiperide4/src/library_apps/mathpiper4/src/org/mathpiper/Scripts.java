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
        scriptString[1] = "RulebaseHoldArguments(MathPiperInitLoad,[]);RuleHoldArguments(MathPiperInitLoad, 0, 1, True){  StandardOperatorsLoad(); Constant(True);  Constant(False);  Constant(Empty);  Constant(Infinity);  Constant(Undefined);};";
        scriptMap.put("MathPiperInitLoad",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Nth\",[alist,aindex]);RuleHoldArguments(\"Nth\",2,10, And?(Equal?(Function?(alist),True), Equal?(Integer?(aindex),True), Not?(Equal?(First(FunctionToList(alist)),Nth)) )) MathNth(alist,aindex);RuleHoldArguments(\"Nth\",2,14, And?(Equal?(String?(alist),True),List?(aindex)) ){ Local(result); result:=\"\"; ForEach(i,aindex) { result := result ~ StringMidGet(i,1,alist); }; result;};RuleHoldArguments(\"Nth\",2,15,Equal?(String?(alist),True)){ StringMidGet(aindex,1,alist);};RuleHoldArguments(\"Nth\",2,20,Equal?(List?(aindex),True)){ Map([[ii],alist[ii]],[aindex]);};RuleHoldArguments(\"Nth\",2,30, And?( Equal?(Generic?(alist),True), Equal?(GenericTypeName(alist),\"Array\"), Equal?(Integer?(aindex),True) ) ){ ArrayGet(alist,aindex);};RuleHoldArguments(\"Nth\",2,40,Equal?(String?(aindex),True)){ Local(as); as := Assoc(aindex,alist); Decide(Not?(Equal?(as,Empty)),Assign(as,Nth(as,2))); as;};";
        scriptMap.put("Nth",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"ArgumentsCount\",[aLeft]) Length(FunctionToList(aLeft))-1;";
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
        scriptString[1] = "RulebaseHoldArguments(\"==\",[left,right]);";
        scriptMap.put("==",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"!==\",[left,right]);";
        scriptMap.put("!==",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"--\",[aVar]){ MacroAssign(aVar,SubtractN(Eval(aVar),1)); Eval(aVar);};UnFence(\"--\",1);HoldArgument(\"--\",aVar);";
        scriptMap.put("--",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(numericMode){ numericMode := False;  LocalSymbols(previousNumericMode, previousPrecision, numericResult)  Macro(\"NM\",[expression, precision]) {      Local(previousNumericMode, previousPrecision, numericResult, exception); previousPrecision := BuiltinPrecisionGet();  BuiltinPrecisionSet(@precision+5); AssignCachedConstantsN(); previousNumericMode := numericMode; numericMode := True; exception := False;  ExceptionCatch( numericResult:=Eval(@expression), exception := ExceptionGet() );  Decide(Decimal?(numericResult), numericResult := RoundToN(numericResult, @precision));  numericMode := previousNumericMode; Decide(Not? numericMode, { ClearCachedConstantsN(); } ); BuiltinPrecisionSet(previousPrecision); Check(exception =? False, exception[\"type\"], exception[\"message\"]); numericResult; };  LocalSymbols(precision,heldExpression)  Macro(\"NM\",[expression]) { Local(precision, heldExpression); precision := BuiltinPrecisionGet(); heldExpression := Hold(@expression); `NM(@heldExpression, @precision); };   LocalSymbols(result)  Macro(\"NonNM\",[expression]) { Local(result); GlobalPush(numericMode); numericMode := False; result := (@expression); numericMode := GlobalPop(); result; };  Function(\"NumericMode?\",[]) numericMode;}; ";
        scriptMap.put("NM",scriptString);
        scriptMap.put("NonNM",scriptString);
        scriptMap.put("NumericMode?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "a_NonNegativeInteger? % b_PositiveInteger? <-- Modulo(a,b);";
        scriptMap.put("%",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"++\",[aVar]){ MacroAssign(aVar,AddN(Eval(aVar),1)); Eval(aVar);};UnFence(\"++\",1);HoldArgument(\"++\",aVar);";
        scriptMap.put("++",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " RulebaseHoldArguments(\"'\", [expression]); RuleHoldArguments(\"'\", 1, 100, True) `(Hold(@expression)); HoldArgument(\"'\",expression);";
        scriptMap.put("'",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "a_NonNegativeInteger? | b_NonNegativeInteger? <-- BitOr(a,b);";
        scriptMap.put("|",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "50 # x_Number? * y_Number? <-- MultiplyN(x,y);100 # 1 * _x <-- x;100 # _x * 1 <-- x;100 # (_f * _x)::(f=? -1) <-- -x;100 # (_x * _f)::(f=? -1) <-- -x;100 # ((_x)*(_x ^ _m)) <-- x^(m+1);100 # ((_x ^ _m)*(_x)) <-- x^(m+1);100 # ((_x ^ _n)*(_x ^ _m)) <-- x^(m+n);100 # Tan(_x)*Cos(_x) <-- Sin(x);100 # Sinh(_x)*Csch(_x) <-- 1;100 # Cosh(_x)*Sech(_x) <-- 1;100 # Cos(_x)*Tan(_x) <-- Sin(x);100 # Coth(_x)*Sinh(_x) <-- Cosh(x);100 # Tanh(_x)*Cosh(_x) <-- Sinh(x);105 # (f_NegativeNumber? * _x) <-- -(-f)*x;105 # (_x * f_NegativeNumber?) <-- -(-f)*x;95 # x_Matrix? * y_Matrix? <--{ Local(i,j,k,row,result); result:=ZeroMatrix(Length(x),Length(y[1])); For(i:=1,i<=?Length(x),i++) For(j:=1,j<=?Length(y),j++) For(k:=1,k<=?Length(y[1]),k++) { row:=result[i]; row[k]:= row[k]+x[i][j]*y[j][k]; }; result;};96 # x_Matrix? * y_List? <--{ Local(i,result); result:=[]; For(i:=1,i<=?Length(x),i++) { DestructiveInsert(result,i,Dot(x[i], y)); }; result;};97 # (x_List? * y_NonObject?)::Not?(List?(y)) <-- y*x;98 # (x_NonObject? * y_List?)::Not?(List?(x)) <--{ Local(i,result); result:=[]; For(i:=1,i<=?Length(y),i++) { DestructiveInsert(result,i,x * y[i]); }; result;};50 # _x * Undefined <-- Undefined;50 # Undefined * _y <-- Undefined;100 # 0 * y_Infinity? <-- Undefined;100 # x_Infinity? * 0 <-- Undefined;101 # 0 * (_x) <-- 0;101 # (_x) * 0 <-- 0;100 # x_Number? * (y_Number? * _z) <-- (x*y)*z;100 # x_Number? * (_y * z_Number?) <-- (x*z)*y;100 # (_x * _y) * _y <-- x * y^2;100 # (_x * _y) * _x <-- y * x^2;100 # _y * (_x * _y) <-- x * y^2;100 # _x * (_x * _y) <-- y * x^2;100 # _x * (_y / _z) <-- (x*y)/z;100 # (_y / _z) * _x <-- (x*y)/z;100 # (_x * y_Number?)::Not?(Number?(x)) <-- y*x;100 # (_x) * (_x) ^ (n_Constant?) <-- x^(n+1);100 # (_x) ^ (n_Constant?) * (_x) <-- x^(n+1);100 # (_x * _y)* _x ^ n_Constant? <-- y * x^(n+1);100 # (_y * _x)* _x ^ n_Constant? <-- y * x^(n+1);100 # Sqrt(_x) * (_x) ^ (n_Constant?) <-- x^(n+1/2);100 # (_x) ^ (n_Constant?) * Sqrt(_x) <-- x^(n+1/2);100 # Sqrt(_x) * (_x) <-- x^(3/2);100 # (_x) * Sqrt(_x) <-- x^(3/2);105 # x_Number? * -(_y) <-- (-x)*y;105 # (-(_x)) * (y_Number?) <-- (-y)*x;106 # _x * -(_y) <-- -(x*y);106 # (- _x) * _y <-- -(x*y);150 # (_x) ^ (n_Constant?) * (_x) <-- x^(n-1);250 # x_Number? * y_Infinity? <-- Sign(x)*y;250 # x_Infinity? * y_Number? <-- Sign(y)*x;230 # (aLeft_List? * aRight_List?)::(Length(aLeft)=?Length(aRight)) <-- Map(\"*\",[aLeft,aRight]);242 # (x_Integer? / y_Integer?) * (v_Integer? / w_Integer?) <-- (x*v)/(y*w);243 # x_Integer? * (y_Integer? / z_Integer?) <-- (x*y)/z;243 # (y_Integer? / z_Integer?) * x_Integer? <-- (x*y)/z;400 # (_x) * (_x) <-- x^2;400 # x_RationalOrNumber? * Sqrt(y_RationalOrNumber?) <-- Sign(x)*Sqrt(x^2*y);400 # Sqrt(y_RationalOrNumber?) * x_RationalOrNumber? <-- Sign(x)*Sqrt(x^2*y);400 # Sqrt(y_RationalOrNumber?) * Sqrt(x_RationalOrNumber?) <-- Sqrt(y*x);";
        scriptMap.put("*",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "50 # _x ^ (1/2) <-- Sqrt(x);50 # (x_PositiveNumber? ^ (1/2))::Integer?(SqrtN(x)) <-- SqrtN(x);58 # 1 ^ n_Infinity? <-- Undefined;59 # _x ^ 1 <-- x;59 # 1 ^ _n <-- 1;59 # x_Zero? ^ y_Zero? <-- Undefined;60 # (x_Zero? ^ n_RationalOrNumber?)::(n>?0) <-- 0;60 # (x_Zero? ^ n_RationalOrNumber?)::(n<?0) <-- Infinity;59 # _x ^ Undefined <-- Undefined;59 # Undefined ^ _x <-- Undefined;61 # Infinity ^ (y_NegativeNumber?) <-- 0;61 # (-Infinity) ^ (y_NegativeNumber?) <-- 0;61 # x_PositiveNumber? ^ y_PositiveInteger? <-- MathIntPower(x,y);61 # x_PositiveNumber? ^ y_NegativeInteger? <-- 1/MathIntPower(x,-y);65 # (x_PositiveNumber? ^ y_Number?)::NumericMode?() <-- Exp(y*Ln(x));90 # (-_x)^m_Even? <-- x^m;91 # (x_Constant? ^ (m_Odd? / p_Odd?))::(NegativeNumber?(Re(NM(Eval(x))))) <-- -((-x)^(m/p));92 # (x_NegativeNumber? ^ y_Number?)::NumericMode?() <-- Exp(y*Ln(x));70 # (_x ^ m_RationalOrNumber?) ^ n_RationalOrNumber? <-- x^(n*m);80 # (x_Number?/y_Number?) ^ n_PositiveInteger? <-- x^n/y^n;80 # (x_Number?/y_Number?) ^ n_NegativeInteger? <-- y^(-n)/x^(-n);80 # x_NegativeNumber? ^ n_Even? <-- (-x)^n;80 # x_NegativeNumber? ^ n_Odd? <-- -((-x)^n);100 # ((x_Number?)^(n_Integer?/(_m)))::(n>?1) <-- MathIntPower(x,n)^(1/m);100 # Sqrt(_n)^(m_Even?) <-- n^(m/2);100 # Abs(_a)^n_Even? <-- a^n;100 # Abs(_a)^n_Odd? <-- Sign(a)*a^n;200 # x_Matrix? ^ n_PositiveInteger? <-- x*(x^(n-1));204 # (xlist_List? ^ nlist_List?)::(Length(xlist)=?Length(nlist)) <-- Map(\"^\",[xlist,nlist]);205 # (xlist_List? ^ n_Constant?)::(Not?(List?(n))) <-- Map([[xx],xx^n],[xlist]);206 # (_x ^ n_List?)::(Not?(List?(x))) <-- Map([[xx],x^xx],[n]);249 # x_Infinity? ^ 0 <-- Undefined;250 # Infinity ^ (_n) <-- Infinity;250 # Infinity ^ (_x_Complex?) <-- Infinity;250 # ((-Infinity) ^ (n_Number?))::(Even?(n)) <-- Infinity;250 # ((-Infinity) ^ (n_Number?))::(Odd?(n)) <-- -Infinity;250 # (x_Number? ^ Infinity)::(x>? -1 And? x <? 1) <-- 0;250 # (x_Number? ^ Infinity)::(x>? 1) <-- Infinity;250 # (x_Complex? ^ Infinity)::(Magnitude(x) >? 1) <-- Infinity;250 # (x_Complex? ^ Infinity)::(Magnitude(x) <? -1) <-- -Infinity;250 # (x_Complex? ^ Infinity)::(Magnitude(x) >? -1 And? Magnitude(x) <? 1) <-- 0;250 # (x_Number? ^ -Infinity)::(x>? -1 And? x <? 1) <-- Infinity;250 # (x_Number? ^ -Infinity)::(x<? -1) <-- 0;250 # (x_Number? ^ -Infinity)::(x>? 1) <-- 0;255 # (x_Complex? ^ Infinity)::(Abs(x) =? 1) <-- Undefined;255 # (x_Complex? ^ -Infinity)::(Abs(x) =? 1) <-- Undefined;400 # _x ^ 0 <-- 1;";
        scriptMap.put("^",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "51 # -Undefined <-- Undefined;54 # - (- _x) <-- x;55 # (- (x_Number?)) <-- SubtractN(0,x);100 # _x - n_Constant?*(_x) <-- (1-n)*x;100 # n_Constant?*(_x) - _x <-- (n-1)*x;100 # Sinh(_x)^2-Cosh(_x)^2 <-- 1;100 # Sinh(_x)-Cosh(_x) <-- Exp(-x);110 # - (_x - _y) <-- y-x;111 # - (x_Number? / _y) <-- (-x)/y; LocalSymbols(x){ 200 # - (x_List?) <-- MapSingle(\"-\",x);};50 # x_Number? - y_Number? <-- SubtractN(x,y);50 # x_Number? - y_Number? <-- SubtractN(x,y);60 # Infinity - Infinity <-- Undefined;100 # 0 - _x <-- -x;100 # _x - 0 <-- x;100 # _x - _x <-- 0;107 # -( (-(_x))/(_y)) <-- x/y;107 # -( (_x)/(-(_y))) <-- x/y;110 # _x - (- _y) <-- x + y;110 # _x - (y_NegativeNumber?) <-- x + (-y);111 # (_x + _y)- _x <-- y;111 # (_x + _y)- _y <-- x;112 # _x - (_x + _y) <-- - y;112 # _y - (_x + _y) <-- - x;113 # (- _x) - _y <-- -(x+y);113 # (x_NegativeNumber?) - _y <-- -((-x)+y);113 # (x_NegativeNumber?)/_y - _z <-- -((-x)/y+z);LocalSymbols(x,y,xarg,yarg){ 10 # ((x_List?) - (y_List?))::(Length(x)=?Length(y)) <-- { Map([[xarg,yarg],xarg-yarg],[x,y]); };};240 # (x_List? - y_NonObject?)::Not?(List?(y)) <-- -(y-x);241 # (x_NonObject? - y_List?)::Not?(List?(x)) <--{ Local(i,result); result:=[]; For(i:=1,i<=?Length(y),i++) { DestructiveInsert(result,i,x - y[i]); }; result;};250 # z_Infinity? - Complex(_x,_y) <-- Complex(-x+z,-y);250 # Complex(_x,_y) - z_Infinity? <-- Complex(x-z,y);251 # z_Infinity? - _x <-- z;251 # _x - z_Infinity? <-- -z;250 # Undefined - _y <-- Undefined;250 # _x - Undefined <-- Undefined;210 # x_Number? - (y_Number? / z_Number?) <--(x*z-y)/z;210 # (y_Number? / z_Number?) - x_Number? <--(y-x*z)/z;210 # (x_Number? / v_Number?) - (y_Number? / z_Number?) <--(x*z-y*v)/(v*z);";
        scriptMap.put("-",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "100 # + _x <-- x;50 # x_Number? + y_Number? <-- AddN(x,y);100 # 0 + _x <-- x;100 # _x + 0 <-- x;100 # _x + _x <-- 2*x;100 # _x + n_Constant?*(_x) <-- (n+1)*x;100 # n_Constant?*(_x) + _x <-- (n+1)*x;100 # Sinh(_x)+Cosh(_x) <-- Exp(x);101 # _x + - _y <-- x-y;101 # _x + (- _y)/(_z) <-- x-(y/z);101 # (- _y)/(_z) + _x <-- x-(y/z);101 # (- _x) + _y <-- y-x;102 # _x + y_NegativeNumber? <-- x-(-y);102 # _x + y_NegativeNumber? * _z <-- x-((-y)*z);102 # _x + (y_NegativeNumber?)/(_z) <-- x-((-y)/z);102 # (y_NegativeNumber?)/(_z) + _x <-- x-((-y)/z);102 # (x_NegativeNumber?) + _y <-- y-(-x);150 # _n1 / _d + _n2 / _d <-- (n1+n2)/d;200 # (x_Number? + _y)::Not?(Number?(y)) <-- y+x;200 # ((_y + x_Number?) + _z)::Not?(Number?(y) Or? Number?(z)) <-- (y+z)+x;200 # ((x_Number? + _y) + z_Number?)::Not?(Number?(y)) <-- y+(x+z);200 # ((_x + y_Number?) + z_Number?)::Not?(Number?(x)) <-- x+(y+z);210 # x_Number? + (y_Number? / z_Number?) <--(x*z+y)/z;210 # (y_Number? / z_Number?) + x_Number? <--(x*z+y)/z;210 # (x_Number? / v_Number?) + (y_Number? / z_Number?) <--(x*z+y*v)/(v*z);220 # (xlist_List? + ylist_List?)::(Length(xlist)=?Length(ylist)) <-- Map(\"+\",[xlist,ylist]);SumListSide(_x, y_List?) <--{ Local(i,result); result:=[]; For(i:=1,i<=?Length(y),i++) { DestructiveInsert(result,i,x + y[i]); }; result;};240 # (x_List? + _y)::Not?(List?(y)) <-- SumListSide(y,x);241 # (_x + y_List?)::Not?(List?(x)) <-- SumListSide(x,y);250 # z_Infinity? + Complex(_x,_y) <-- Complex(x+z,y);250 # Complex(_x,_y) + z_Infinity? <-- Complex(x+z,y);251 # z_Infinity? + _x <-- z;251 # _x + z_Infinity? <-- z;250 # Undefined + _y <-- Undefined;250 # _x + Undefined <-- Undefined;";
        scriptMap.put("+",scriptString);
        scriptMap.put("SumListSide",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "50 # 0 / 0 <-- Undefined;52 # x_PositiveNumber? / 0 <-- Infinity;52 # x_NegativeNumber? / 0 <-- -Infinity;55 # (_x / y_Number?)::(Zero?(y)) <-- Undefined;55 # 0 / _x <-- 0;56 # (x_NonZeroInteger? / y_NonZeroInteger?)::(GcdN(x,y) >? 1) <-- { Local(gcd); Assign(x,x); Assign(y,y); Assign(gcd,GcdN(x,y)); QuotientN(x,gcd)/QuotientN(y,gcd); };57 # ((x_NonZeroInteger? * _expr) / y_NonZeroInteger?)::(GcdN(x,y) >? 1) <-- { Local(gcd); Assign(x,x); Assign(y,y); Assign(gcd,GcdN(x,y)); (QuotientN(x,gcd)*expr)/QuotientN(y,gcd); };57 # ((x_NonZeroInteger?) / (y_NonZeroInteger? * _expr))::(GcdN(x,y) >? 1) <-- { Local(gcd); Assign(x,x); Assign(y,y); Assign(gcd,GcdN(x,y)); QuotientN(x,gcd)/(QuotientN(y,gcd)*expr); };57 # ((x_NonZeroInteger? * _p) / (y_NonZeroInteger? * _q))::(GcdN(x,y) >? 1) <-- { Local(gcd); Assign(x,x); Assign(y,y); Assign(gcd,GcdN(x,y)); (QuotientN(x,gcd)*p)/(QuotientN(y,gcd)*q); };60 # (x_Decimal? / y_Number?) <-- DivideN(x,y);60 # (x_Number? / y_Decimal?) <-- DivideN(x,y); 60 # (x_Number? / y_Number?)::(NumericMode?()) <-- DivideN(x,y);90 # x_Infinity? / y_Infinity? <-- Undefined;95 # x_Infinity? / y_Number? <-- Sign(y)*x;95 # x_Infinity? / y_Complex? <-- Infinity;90 # Undefined / _y <-- Undefined;90 # _y / Undefined <-- Undefined;100 # _x / _x <-- 1;100 # _x / 1 <-- x;100 # (_x / y_NegativeNumber?) <-- -x/(-y);100 # (_x / - _y) <-- -x/y;100 # Tan(_x)/Sin(_x) <-- (1/Cos(x));100 # 1/Csch(_x) <-- Sinh(x);100 # Sin(_x)/Tan(_x) <-- Cos(x);100 # Sin(_x)/Cos(_x) <-- Tan(x);100 # 1/Sech(_x) <-- Cosh(x);100 # 1/Sec(_x) <-- Cos(x);100 # 1/Csc(_x) <-- Sin(x);100 # Cos(_x)/Sin(_x) <-- (1/Tan(x));100 # 1/Cot(_x) <-- Tan(x);100 # 1/Coth(_x) <-- Tanh(x);100 # Sinh(_x)/Cosh(_x) <-- Tanh(x);150 # (_x) / (_x) ^ (n_Constant?) <-- x^(1-n);150 # Sqrt(_x) / (_x) ^ (n_Constant?) <-- x^(1/2-n);150 # (_x) ^ (n_Constant?) / Sqrt(_x) <-- x^(n-1/2);150 # (_x) / Sqrt(_x) <-- Sqrt(x);200 # (_x / _y)/ _z <-- x/(y*z);230 # _x / (_y / _z) <-- (x*z)/y;240 # (xlist_List? / ylist_List?)::(Length(xlist)=?Length(ylist)) <-- Map(\"/\",[xlist,ylist]);250 # (x_List? / _y)::(Not?(List?(y))) <--{ Local(i,result); result:=[]; For(i:=1,i<=?Length(x),i++) { DestructiveInsert(result,i,x[i] / y); }; result;};250 # (_x / y_List?)::(Not?(List?(x))) <--{ Local(i,result); result:=[]; For(i:=1,i<=?Length(y),i++) { DestructiveInsert(result,i,x/y[i]); }; result;};250 # _x / Infinity <-- 0;250 # _x / (-Infinity) <-- 0;400 # 0 / _x <-- 0;400 # x_RationalOrNumber? / Sqrt(y_RationalOrNumber?) <-- Sign(x)*Sqrt(x^2/y);400 # Sqrt(y_RationalOrNumber?) / x_RationalOrNumber? <-- Sign(x)*Sqrt(y/(x^2));400 # Sqrt(y_RationalOrNumber?) / Sqrt(x_RationalOrNumber?) <-- Sqrt(y/x);";
        scriptMap.put("/",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(StandardOperatorsLoad,[]);RuleHoldArguments(StandardOperatorsLoad, 0, 1, True){  Infix(\"=?\",90); Infix(\"And?\",1000); RightAssociativeSet(\"And?\"); Infix(\"Or?\", 1010); Prefix(\"Not?\", 100); Infix(\"<?\",90); Infix(\">?\",90); Infix(\"<=?\",90); Infix(\">=?\",90); Infix(\"!=?\",90); Infix(\":=\",22000); RightAssociativeSet(\":=\");  RulebaseHoldArguments(\":\",[left,right]); HoldArgument(\":\",left); Infix(\":\",960); RightAssociativeSet(\":\");  RulebaseHoldArguments(\"->\",[left,right]); HoldArgument(\"->\",left); Infix(\"->\",950); Infix(\"+\",70); Infix(\"-\",70); RightPrecedenceSet(\"-\",40); Infix(\"/\",30); Infix(\"*\",40); Infix(\"^\",20); LeftPrecedenceSet(\"^\",19);  RightAssociativeSet(\"^\"); Prefix(\"+\",50); Prefix(\"-\",50); RightPrecedenceSet(\"-\",40); Bodied(\"For\",60000); Bodied(\"Until\",60000); Postfix(\"++\",5); Postfix(\"--\",5); Bodied(\"ForEach\",60000); Infix(\"<<\",10); Infix(\">>\",10); Bodied(\"Differentiate\",60000); Bodied(\"Deriv\",60000); Infix(\"X\",30); Infix(\".\",30); Infix(\"o\",30); Postfix(\"!\", 30); Postfix(\"!!\", 30); Infix(\"***\", 50); Bodied(\"Integrate\",60000); Bodied(\"Limit\",60000); Bodied(\"EchoTime\", 60000); Bodied(\"Repeat\", 60000);  Infix(\"~\",70); RightAssociativeSet(\"~\"); Infix(\"@\",600); Infix(\"/@\",600); Infix(\"..\",600); Bodied(\"Taylor\",60000); Bodied(\"Taylor1\",60000); Bodied(\"Taylor2\",60000); Bodied(\"Taylor3\",60000); Bodied(\"InverseTaylor\",60000); Infix(\"<--\",22000); Infix(\"#\",21900); Bodied(\"TSum\",60000); Bodied(\"TExplicitSum\",60000); Bodied(\"TD\",5);   Infix(\"==\",90); Infix(\"!==\",90);  Infix(\"Implies?\",10000);  Infix(\"Equivales?\",10000);  Bodied(\"If\",5); Infix(\"Else\",60000); RightAssociativeSet(\"Else\");  Infix(\"&\",50); Infix(\"|\",50); Infix(\"%\",50);  Infix(\"/:\",20000); Infix(\"/::\",20000); Infix(\"<-\",10000);  Infix(\"<>\", PrecedenceGet(\"=?\")); Infix(\"<=>\", PrecedenceGet(\"=?\"));  Infix(\"Where\", 11000); Infix(\"AddTo\", 2000); Bodied(\"Function\",60000); Bodied(\"Macro\",60000); Bodied(\"Assert\", 60000);  Bodied(\"Defun\",0); Bodied(\"TemplateFunction\",60000); Bodied(\"Taylor3TaylorCoefs\",0); Infix(\":*:\",3);};";
        scriptMap.put("StandardOperatorsLoad",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Assoc\",[key,list]) BuiltinAssoc(key,list);";
        scriptMap.put("Assoc",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(hash, key, element, hashexpr){10 # AssocDelete(hash_List?, element_List?) <--{ Local(index); index := Find(hash, element); Decide( index >? 0, DestructiveDelete(hash, index) ); index>?0; };20 # AssocDelete(hash_List?, key_String?) <--{ AssocDelete(hash, BuiltinAssoc(key, hash));};30 # AssocDelete(hash_List?, Empty) <-- False;}; ";
        scriptMap.put("AssocDelete",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "AssocIndices(associndiceslist_List?) <-- DestructiveReverse(MapSingle(\"First\",associndiceslist));";
        scriptMap.put("AssocIndices",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "AssocValues(assocvalueslist_List?) <-- DestructiveReverse(MapSingle(Lambda([x],First(Rest(x))),assocvalueslist));";
        scriptMap.put("AssocValues",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"CosN\",[x])Trigonometry(x,0.0,1.0,1.0);";
        scriptMap.put("CosN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"ExpN\", [x]) Decide(Equal?(x,0),1, Decide(LessThan?(x,0),DivideN(1, ExpN(MathNegate(x))), Decide(GreaterThan?(x,1), MathExpDoubling(MathExpTaylor0(MathMul2Exp(x,MathNegate(MathBitCount(x)))), MathBitCount(x)), MathExpTaylor0(x) )));";
        scriptMap.put("ExpN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"MathExpDoubling\", [value, n]){ Local(shift, result); Assign(shift, n); Assign(result, value); While (GreaterThan?(shift,0))  { Assign(result, MultiplyN(result, result)); Assign(shift, AddN(shift,MathNegate(1))); }; result;};";
        scriptMap.put("MathExpDoubling",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"MathExpTaylor0\",[x]){ Check(x<=?1 And? x>=?0,\"\",\" Argument must be between 0 and 1 inclusive.\"); Local(i,aResult,term,eps);     Assign(i,0);  Assign(aResult,1.0);  Assign(term,1.0); Assign(eps,MathIntPower(10,MathNegate(BuiltinPrecisionGet())));   While(GreaterThan?(AbsN(term),eps)) {  Assign(i,AddN(i,1));  Assign(term,DivideN(MultiplyN(term,x),i));  Assign(aResult,AddN(aResult,term)); }; aResult;};";
        scriptMap.put("MathExpTaylor0",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "";
        scriptMap.put("MathFloatPower",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"MathIntPower\", [x,y]) Decide(Equal?(x,0),0,Decide(Equal?(x,1),1, Decide(Integer?(y),Decide(LessThan?(y,0),  DivideN(1, PositiveIntPower(AddN(x,0.),MathNegate(y))),  PositiveIntPower(x,y) ),  False) ));";
        scriptMap.put("MathIntPower",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"MathMul2Exp\", [x,n])  Decide(GreaterThan?(n,0), MultiplyN(x, MathIntPower(2,n)), DivideN(x, MathIntPower(2,MathNegate(n))));";
        scriptMap.put("MathMul2Exp",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"MathPi\",[]){    Local(initialPrec,curPrec,result,aPrecision); Assign(aPrecision,BuiltinPrecisionGet()); Assign(initialPrec, aPrecision);  Assign(curPrec, 40);  Assign(result, 3.141592653589793238462643383279502884197169399);   While (GreaterThan?(initialPrec, MultiplyN(curPrec,3))) { Assign(initialPrec, FloorN(DivideN(AddN(initialPrec,2),3))); }; Assign(curPrec, initialPrec); While (Not?(GreaterThan?(curPrec, aPrecision))) {   BuiltinPrecisionSet(curPrec); Assign(result,AddN(result,SinN(result)));    Decide(Equal?(curPrec, aPrecision),  { Assign(curPrec, AddN(aPrecision,1));  }, { Assign(curPrec, MultiplyN(curPrec,3));   Decide(GreaterThan?(curPrec, aPrecision), { Assign(curPrec, aPrecision);  }); }); }; BuiltinPrecisionSet(aPrecision); result;};";
        scriptMap.put("MathPi",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"PositiveIntPower\", [x,n]){ Local(result,unit); Decide(LessThan?(n,0), False, { Assign(unit,1);  Assign(result, unit); Decide(Equal?(n,0),unit, Decide(Equal?(n,1),x, { While(GreaterThan?(n,0)) { Decide( Equal?(BitAnd(n,1), 1), Assign(result, MultiplyN(result,x)) ); Assign(x, MultiplyN(x,x)); Assign(n,ShiftRight(n,1)); }; result; } ) ); });};";
        scriptMap.put("PositiveIntPower",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"PowerN\", [x,y]) Decide(Equal?(x,0),0, Decide(Equal?(x,1),1, Decide(Integer?(y), MathIntPower(x,y), False) ));";
        scriptMap.put("PowerN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"SinN\",[x])Trigonometry(x,1.0,x,x);";
        scriptMap.put("SinN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"TanN\",[x])DivideN(SinN(x),CosN(x));";
        scriptMap.put("TanN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"Trigonometry\",[x,i,sum,term]){ Local(x2,orig,eps,previousPrec,newPrec); Assign(previousPrec,BuiltinPrecisionGet()); Assign(newPrec,AddN(BuiltinPrecisionGet(),2)); Assign(x2,MultiplyN(x,x)); BuiltinPrecisionSet(newPrec); Assign(eps,MathIntPower(10,MathNegate(previousPrec))); While(GreaterThan?(AbsN(term),eps)) { Assign(term,MultiplyN(term,x2)); Assign(i,AddN(i,1.0)); Assign(term,DivideN(term,i)); Assign(i,AddN(i,1.0)); Assign(term,DivideN(MathNegate(term),i)); BuiltinPrecisionSet(previousPrec); Assign(sum, AddN(sum, term)); BuiltinPrecisionSet(newPrec); }; BuiltinPrecisionSet(previousPrec); sum;};";
        scriptMap.put("Trigonometry",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # BinomialCoefficient(0,0) <-- 1;10 # BinomialCoefficient(n_PositiveInteger?,m_NonNegativeInteger?)::(2*m <=? n) <-- ((n-m+1) *** n) / m!;15 # BinomialCoefficient(n_PositiveInteger?,m_NonNegativeInteger?)::(2*m >? n And? m <=? n) <-- BinomialCoefficient(n, n-m);20 # BinomialCoefficient(n_Integer?,m_Integer?) <-- 0;Combinations(n,m) := BinomialCoefficient(n,m);";
        scriptMap.put("Combinations",scriptString);
        scriptMap.put("BinomialCoefficient",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"PermutationsList\",[result,list]){ Decide(Length(list) =? 0, { result; }, { Local(head); Local(newresult); Local(i); head:=list[1]; newresult:=[]; ForEach(item,result) { For(i:=Length(item)+1,i>?0,i--) { DestructiveInsert(newresult,1,Insert(item,i,head)); }; }; newresult:=DestructiveReverse(newresult); PermutationsList(newresult,Rest(list)); });};Function(\"PermutationsList\",[list]){ PermutationsList([[]],list);};";
        scriptMap.put("PermutationsList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Arg(Complex(Cos(_x),Sin(_x))) <-- x;10 # Arg(x_Zero?) <-- Undefined;15 # Arg(x_PositiveReal?) <-- 0;15 # Arg(x_NegativeReal?) <-- Pi;20 # Arg(Complex(r_Zero?,i_Constant?)) <-- Sign(i)*Pi/2;30 # Arg(Complex(r_PositiveReal?,i_Constant?)) <-- ArcTan(i/r);40 # Arg(Complex(r_NegativeReal?,i_PositiveReal?)) <-- Pi+ArcTan(i/r);50 # Arg(Complex(r_NegativeReal?,i_NegativeReal?)) <-- ArcTan(i/r)-Pi;";
        scriptMap.put("Arg",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "0 # Complex(_r,i_Zero?) <-- r;2 # Complex(Complex(_r1,_i1),_i2) <-- Complex(r1,i1+i2);2 # Complex(_r1,Complex(_r2,_i2)) <-- Complex(r1-i2,r2);6 # Complex(Undefined,_x) <-- Undefined;6 # Complex(_x,Undefined) <-- Undefined;110 # Complex(_r1,_i1) + Complex(_r2,_i2) <-- Complex(r1+r2,i1+i2);300 # Complex(_r,_i) + x_Constant? <-- Complex(r+x,i);300 # x_Constant? + Complex(_r,_i) <-- Complex(r+x,i);110 # - Complex(_r,_i) <-- Complex(-r,-i);300 # Complex(_r,_i) - x_Constant? <-- Complex(r-x,i);300 # x_Constant? - Complex(_r,_i) <-- Complex((-r)+x,-i);111 # Complex(_r1,_i1) - Complex(_r2,_i2) <-- Complex(r1-r2,i1-i2);110 # Complex(_r1,_i1) * Complex(_r2,_i2) <-- Complex(r1*r2-i1*i2,r1*i2+r2*i1);250 # Complex(r_Zero?,_i) * x_Infinity? <-- Complex(0,i*x);250 # Complex(_r,i_Zero?) * x_Infinity? <-- Complex(r*x,0);251 # Complex(_r,_i) * x_Infinity? <-- Complex(r*x,i*x);250 # x_Infinity? * Complex(r_Zero?,_i) <-- Complex(0,i*x);250 # x_Infinity? * Complex(_r,i_Zero?) <-- Complex(r*x,0);251 # x_Infinity? * Complex(_r,_i) <-- Complex(r*x,i*x);300 # Complex(_r,_i) * y_Constant? <-- Complex(r*y,i*y);300 # y_Constant? * Complex(_r,_i) <-- Complex(r*y,i*y);330 # Complex(_r,_i) * (y_Constant? / _z) <-- (Complex(r*y,i*y))/z;330 # (y_Constant? / _z) * Complex(_r,_i) <-- (Complex(r*y,i*y))/z;110 # x_Constant? / Complex(_r,_i) <-- (x*Conjugate(Complex(r,i)))/(r^2+i^2);300 # Complex(_r,_i) / y_Constant? <-- Complex(r/y,i/y);110 # (_x ^ Complex(_r,_i)) <-- Exp(Complex(r,i)*Ln(x));110 # (Complex(_r,_i) ^ x_RationalOrNumber?)::(Not?(Integer?(x))) <-- Exp(x*Ln(Complex(r,i)));123 # Complex(_r, _i) ^ n_NegativeInteger? <-- 1/Complex(r, i)^(-n);124 # Complex(_r, _i) ^ (p_Zero?) <-- 1; 125 # Complex(_r, _i) ^ n_PositiveInteger? <--{  Local(result, x); x:=Complex(r,i); result:=1; While(n >? 0) { If((n&1) =? 1) { result := result*x; }; x := x*x; n := n>>1; }; result;};";
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
        scriptString[1] = "LocalSymbols(a,x){Function(\"Conjugate\",[a]) SubstituteApply(a,[[x],Type(x)=?\"Complex\"],[[x],Complex(x[1],-(x[2]))]);}; ";
        scriptMap.put("Conjugate",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # II^n_NegativeInteger? <-- (-II)^(-n);20 # (II^_n)::(Even?(n) =? True) <-- (-1)^(n>>1);20 # (II^_n)::(Odd?(n) =? True) <-- II*(-1)^(n>>1);";
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
        scriptString[1] = "Function(\"Magnitude\",[x]) { Sqrt(Re(x)^2 + Im(x)^2);};";
        scriptMap.put("Magnitude",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(complexReduce) { Assign(complexReduce, Hold( [ Exp(x_ComplexII?) <- Exp(ReII(x))*(Cos(ImII(x))+II*Sin(ImII(x))) ])); NN(_c) <-- { Local(result); c := (c /:: complexReduce); result := Coef(Expand(c,II),II,[0,1]); result; };}; ";
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
        scriptString[1] = "SetGlobalLazyVariable(I,Complex(0,1));LocalSymbols(CacheOfConstantsN) {RulebaseHoldArguments(\"CachedConstant\", [Ccache, Catom, Cfunc]);UnFence(\"CachedConstant\", 3); HoldArgument(\"CachedConstant\", Cfunc);HoldArgument(\"CachedConstant\", Ccache); RuleHoldArguments(\"CachedConstant\", 3, 10, And?(Atom?(Catom), Function?(Cfunc))){ Local(Cname,CfunctionName); Assign(Cname, ToString(Catom));  Assign(CfunctionName,ConcatStrings(\"Internal\",Cname)); Decide(  Atom?(Eval(Ccache)), MacroAssign(Eval(Ccache), []) );  Decide( Equal?(BuiltinAssoc(Cname, Eval(Ccache)), Empty),  {  MacroUnassign(Catom);  DestructiveInsert(Eval(Ccache), 1, [Cname, 0, 0]);   RulebaseEvaluateArguments(CfunctionName, []); `( RuleHoldArguments(@CfunctionName, 0, 1024, True) { Local(newprec, newC, cachedC); Assign(newprec, BuiltinPrecisionGet());   Assign(cachedC, BuiltinAssoc(@Cname, @Ccache)); Decide( MathNth(cachedC, 2) !=? newprec, {  Decide(Equal?(InVerboseMode(),True), Echo(\"CachedConstant: Info: constant \", @Cname, \" is being recalculated at precision \", newprec)); Assign(newC, RoundTo(Eval(@Cfunc),newprec)); DestructiveReplace(cachedC, 2, newprec); DestructiveReplace(cachedC, 3, newC); newC; },  MathNth(cachedC, 3) ); });   },  Echo(\"CachedConstant: Warning: constant \", Catom, \" already defined\") );};RuleHoldArguments(\"CachedConstant\", 3, 20, True) Echo(\"CachedConstant: Error: \", Catom, \" must be an atom and \", Cfunc, \" must be a function.\");Function(\"AssignCachedConstantsN\", []){ Local(var,fname); ForEach(var, AssocIndices(CacheOfConstantsN)) { MacroUnassign(ToAtom(var)); Assign(fname,ConcatStrings(\"Internal\",var)); Assign(var,ToAtom(var));  `SetGlobalLazyVariable((@var), ListToFunction([ToAtom(fname)])); };};UnFence(\"AssignCachedConstantsN\", 0);Function(\"ClearCachedConstantsN\", []){ Local(centry); ForEach(centry, CacheOfConstantsN) MacroUnassign(ToAtom(centry[1]));};UnFence(\"ClearCachedConstantsN\", 0);CachedConstant(CacheOfConstantsN, Pi,{ Local(result,oldprec); Assign(oldprec,BuiltinPrecisionGet());Decide(Equal?(InVerboseMode(),True), Echo(\"Recalculating Pi at precision \",oldprec+5)); BuiltinPrecisionSet(BuiltinPrecisionGet()+5); result := MathPi();Decide(Equal?(InVerboseMode(),True),Echo(\"Switching back to precision \",oldprec)); BuiltinPrecisionSet(oldprec); result;});CachedConstant(CacheOfConstantsN, gamma, GammaConstNum());CachedConstant(CacheOfConstantsN, GoldenRatio, NM( (1+Sqrt(5))/2 ) );CachedConstant(CacheOfConstantsN, Catalan, CatalanConstNum() );}; ";
        scriptMap.put("I",scriptString);
        scriptMap.put("CachedConstant",scriptString);
        scriptMap.put("AssignCachedConstantsN",scriptString);
        scriptMap.put("ClearCachedConstantsN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Else\",[ifthen,otherwise]);0 # (If(_predicate) _body Else _otherwise)::(Eval(predicate) =? True) <-- Eval(body);0 # (If(_predicate) _body Else _otherwise)::(Eval(predicate) =? False) <-- Eval(otherwise);1 # (If(_predicate) _body Else _otherwise) <-- ListToFunction([ToAtom(\"Else\"), ListToFunction([ToAtom(\"If\"), (Eval(predicate)), body]), otherwise]);HoldArgument(\"Else\",ifthen);HoldArgument(\"Else\",otherwise);UnFence(\"Else\",2);";
        scriptMap.put("Else",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TemplateFunction(\"For\",[start,predicate,increment,body]){ Eval(start); While (Equal?(Eval(predicate),True)) { Eval(body); Eval(increment); };};UnFence(\"For\",4);HoldArgumentNumber(\"For\",4,1);HoldArgumentNumber(\"For\",4,2);HoldArgumentNumber(\"For\",4,3);HoldArgumentNumber(\"For\",4,4);";
        scriptMap.put("For",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TemplateFunction(\"ForEach\",[item,listOrString,body]){ Decide(And?(Equal?(Generic?(listOrString),True), Equal?(GenericTypeName(listOrString),\"Array\") ), `ForEachInArray(@item,listOrString,@body), { MacroLocal(item);  Decide(String?(listOrString), {  Local(index, stringLength);  stringLength := Length(listOrString);  index := 1; While(index <=? stringLength ) { MacroAssign(item,listOrString[index] );  Eval(body);  index++; }; }, { Local(foreachtail); Assign(foreachtail,listOrString); While(Not?(Equal?(foreachtail,[]))) { MacroAssign(item,First(foreachtail)); Eval(body); Assign(foreachtail,Rest(foreachtail)); }; }); });};UnFence(\"ForEach\",3);HoldArgumentNumber(\"ForEach\",3,1);HoldArgumentNumber(\"ForEach\",3,3);";
        scriptMap.put("ForEach",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"If\",[predicate,body]);(If(True) _body) <-- Eval(body);HoldArgument(\"If\",body);UnFence(\"If\",2);";
        scriptMap.put("If",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MacroRulebaseHoldArguments(\"Lambda\",[args,body]);";
        scriptMap.put("Lambda",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TemplateFunction(\"Until\",[predicate,body]){ Eval(body); While (Equal?(Eval(predicate),False)) { Eval(body); }; True;};UnFence(\"Until\",2);HoldArgumentNumber(\"Until\",2,1);HoldArgumentNumber(\"Until\",2,2);";
        scriptMap.put("Until",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(Verbose) { Assign(Verbose,False); Function(\"V\",[aNumberBody]) { Local(prevVerbose,result); Assign(prevVerbose,Verbose); Assign(Verbose,True); Assign(result,Eval(aNumberBody)); Assign(Verbose,prevVerbose); result; }; Function(\"InVerboseMode\",[]) Verbose;}; HoldArgument(\"V\",aNumberBody);UnFence(\"V\",1);";
        scriptMap.put("V",scriptString);
        scriptMap.put("InVerboseMode",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"AssignArray\", [listOrAssociationList, indexOrKey, newValue]);UnFence(\"AssignArray\", 3);RuleHoldArguments(\"AssignArray\", 3, 1, String?(indexOrKey)){ Local(keyValuePair); keyValuePair := Assoc(indexOrKey, listOrAssociationList); Decide(keyValuePair =? Empty, DestructiveInsert(listOrAssociationList, 1 , [indexOrKey, newValue]), DestructiveReplace(keyValuePair, 2 , newValue) ); True;};RuleHoldArguments(\"AssignArray\",3,1, And?( Equal?(Generic?(listOrAssociationList), True), Equal?(GenericTypeName(listOrAssociationList), \"Array\"))){ ArraySet(listOrAssociationList, indexOrKey, newValue);};RuleHoldArguments(\"AssignArray\",3 , 2, True){ DestructiveReplace(listOrAssociationList, indexOrKey, newValue); True;};";
        scriptMap.put("AssignArray",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MacroRulebaseHoldArguments(\"Defun\",[func,args,body]);RuleHoldArguments(\"Defun\",3,0,True){ Local(nrargs); Assign(nrargs,Length(@args)); Retract(@func, `(@nrargs)); RulebaseHoldArguments(@func,@args); Local(fn,bd); Assign(fn,Hold(@func)); Assign(bd,Hold(@body)); `RuleHoldArguments(@fn, @nrargs, 0,True)(@bd);};";
        scriptMap.put("Defun",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Function\",[oper,args,body]);RuleHoldArguments(\"Function\",3,2047, And?(GreaterThan?(Length(args), 1), Equal?( MathNth(args, Length(args)), ToAtom(\"...\") ))){ DestructiveDelete(args,Length(args));  Retract(oper,Length(args)); RulebaseListedEvaluateArguments(oper,args); RuleEvaluateArguments(oper,Length(args),1025,True) body; };RuleHoldArguments(\"Function\",3,2048,True){ Retract(oper,Length(args)); RulebaseEvaluateArguments(oper,args); RuleEvaluateArguments(oper,Length(args),1025,True) body;};RulebaseHoldArguments(\"Function\",[oper]);RuleHoldArguments(\"Function\",1,2047, And?(Function?(oper), GreaterThan?(Length(oper), 1), Equal?( MathNth(oper, Length(oper)), ToAtom(\"...\") ))){ Local(args); Assign(args,Rest(FunctionToList(oper))); DestructiveDelete(args,Length(args));  Decide(RulebaseDefined(Type(oper),Length(args)), False,  RulebaseListedEvaluateArguments(Type(oper),args) );};RuleHoldArguments(\"Function\",1,2048, And?(Function?(oper))){ Local(args); Assign(args,Rest(FunctionToList(oper))); Decide(RulebaseDefined(Type(oper),Length(args)), False,  RulebaseEvaluateArguments(Type(oper),args) );};HoldArgument(\"Function\",oper);HoldArgument(\"Function\",args);HoldArgument(\"Function\",body);";
        scriptMap.put("Function",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"HoldArgumentNumber\",[function,arity,index]){ Local(args); args:=RulebaseArgumentsList(function,arity); ApplyFast(\"HoldArgument\",[function,args[index]]);};";
        scriptMap.put("HoldArgumentNumber",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Macro\",[oper,args,body]);HoldArgument(\"Macro\",oper);HoldArgument(\"Macro\",args);HoldArgument(\"Macro\",body);RuleHoldArguments(\"Macro\",3,2047, And?(GreaterThan?(Length(args), 1), Equal?( MathNth(args, Length(args)), ToAtom(\"...\") ))){ DestructiveDelete(args,Length(args));  Retract(oper,Length(args)); `MacroRulebaseListedHoldArguments(@oper,@args); RuleEvaluateArguments(oper,Length(args),1025,True) body; };RuleHoldArguments(\"Macro\",3,2048,True){ Retract(oper,Length(args)); `MacroRulebaseHoldArguments(@oper,@args); RuleEvaluateArguments(oper,Length(args),1025,True) body;};RulebaseHoldArguments(\"Macro\",[oper]);RuleHoldArguments(\"Macro\",1,2047, And?(Function?(oper), GreaterThan?(Length(oper), 1), Equal?( MathNth(oper, Length(oper)), ToAtom(\"...\") ))){ Local(args,name); Assign(args,Rest(FunctionToList(oper))); DestructiveDelete(args,Length(args));  Assign(name,Type(oper)); Decide(RulebaseDefined(Type(oper),Length(args)), False,  `MacroRulebaseListedHoldArguments(@name,@args) );};RuleHoldArguments(\"Macro\",1,2048, And?(Function?(oper))){ Local(args,name); Assign(args,Rest(FunctionToList(oper))); Assign(name,Type(oper)); Decide(RulebaseDefined(Type(oper),Length(args)), False,  { `MacroRulebaseHoldArguments(@name,@args); } );};";
        scriptMap.put("Macro",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"TemplateFunction\",[oper,args,body]);HoldArgument(\"TemplateFunction\",oper);HoldArgument(\"TemplateFunction\",args);HoldArgument(\"TemplateFunction\",body);RuleHoldArguments(\"TemplateFunction\",3,2047,True){ Retract(oper,Length(args)); Local(arglist); arglist:=FlatCopy(args); DestructiveAppend(arglist,[args,ListToFunction([Hold,body])]); arglist:=ApplyFast(\"LocalSymbols\",arglist); RulebaseEvaluateArguments(oper,arglist[1]); RuleEvaluateArguments(oper,Length(args),1025,True) arglist[2];};";
        scriptMap.put("TemplateFunction",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Unholdable\",[var]);HoldArgument(\"Unholdable\",var);UnFence(\"Unholdable\",1);RuleHoldArguments(\"Unholdable\",1,10,Equal?(Type(Eval(var)),\"Eval\")){ MacroAssign(var,Eval(Eval(var))); };RuleHoldArguments(\"Unholdable\",1,20,True){  True;};";
        scriptMap.put("Unholdable",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\":=\",[aLeftAssign, aRightAssign]);UnFence(\":=\", 2);HoldArgument(\":=\", aLeftAssign);HoldArgument(\":=\", aRightAssign);RuleHoldArguments(\":=\", 2, 0, Atom?(aLeftAssign)){ Check( Not? Number?(aLeftAssign), \"Argument\", \"Only a variable can be placed on the left side of an := operator.\" ); MacroAssign(aLeftAssign,Eval(aRightAssign)); Eval(aLeftAssign);};RuleHoldArguments(\":=\",2 , 0, List?(aLeftAssign)){ Map(\":=\",[aLeftAssign,Eval(aRightAssign)]);};RuleHoldArguments(\":=\", 2, 10, Function?(aLeftAssign) And? (First(FunctionToList(aLeftAssign)) =? Nth)){ Local(listOrAssociationList, indexOrKey, argumentsList); Assign(argumentsList, (FunctionToList(aLeftAssign))); Assign(argumentsList, Rest(argumentsList)); Assign(listOrAssociationList, Eval(First(argumentsList))); Assign(argumentsList, Rest(argumentsList)); Assign(indexOrKey, Eval(First(argumentsList))); AssignArray(listOrAssociationList, indexOrKey, Eval(aRightAssign));};RuleHoldArguments(\":=\", 2, 30, Function?(aLeftAssign) And? Not?(Equal?(aLeftAssign[0], ToAtom(\":=\"))) ){ Check( Not? Equal?(aLeftAssign[0], ToAtom(\"/\")), \"Argument\", \"Only a variable can be placed on the left side of an := operator.\" ); Local(oper,args,arity); Assign(oper,ToString(aLeftAssign[0])); Assign(args,Rest(FunctionToList(aLeftAssign))); Decide( And?(GreaterThan?(Length(args), 1), Equal?( MathNth(args, Length(args)), ToAtom(\"...\") )),  { DestructiveDelete(args, Length(args));  Assign(arity, Length(args)); Retract(oper, arity); RulebaseListedEvaluateArguments(oper, args); },  { Assign(arity, Length(args)); Retract(oper, arity); RulebaseEvaluateArguments(oper, args); } ); Unholdable(aRightAssign); RuleEvaluateArguments(oper, arity, 1025, True) aRightAssign;};";
        scriptMap.put(":=",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "EquationLeft(_symbolicEquation)::(Type(symbolicEquation) =? \"==\") <--{ Local(listForm);  listForm := FunctionToList(symbolicEquation);  listForm[2];};";
        scriptMap.put("EquationLeft",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "EquationRight(_symbolicEquation)::(Type(symbolicEquation) =? \"==\") <--{ Local(listForm);  listForm := FunctionToList(symbolicEquation);  listForm[3];};";
        scriptMap.put("EquationRight",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Apply(_applyoper,_applyargs):: (Or?(String?(applyoper), List?(applyoper))) <-- ApplyFast(applyoper,applyargs);20 # Apply(applyoper_Atom?,_applyargs) <-- ApplyFast(ToString(applyoper),applyargs);30 # Apply(Lambda(_args,_body),_applyargs) <-- `ApplyFast(Hold([@args,@body]),applyargs);UnFence(\"Apply\",2);";
        scriptMap.put("Apply",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(NFunctionNumberize){NFunction(newname_String?, oldname_String?, arglist_List?) <-- { RulebaseEvaluateArguments(newname, arglist); RuleEvaluateArguments(newname, Length(arglist), 0,  ListToFunction([NumericList?, arglist]) )  NFunctionNumberize(ListToFunction([ToAtom(\"@\"), oldname, arglist])); };10 # NFunctionNumberize(x_Number?) <-- x;20 # NFunctionNumberize(x_Atom?) <-- Undefined;}; ";
        scriptMap.put("NFunction",scriptString);
        scriptMap.put("NFunctionNumberize",scriptString);
        scriptMap.put("NFunctionNumberize",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"@\",[func,arg]);RuleHoldArguments(\"@\",2,1,List?(arg)) Apply(func,arg);RuleHoldArguments(\"@\",2,2,True ) Apply(func,[arg]);";
        scriptMap.put("@",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # (countfrom_Integer? .. countto_Integer?)::(countfrom <=? countto) <-- BuildList(i,i,countfrom,countto,1);20 # (countfrom_Integer? .. countto_Integer?) <-- BuildList(i,i,countfrom,countto,-1);";
        scriptMap.put("..",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"/@\",[func,lst]) Apply(\"MapSingle\",[func,lst]);";
        scriptMap.put("/@",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"~\",[head,tail]);RuleHoldArguments(\"~\",2,20,List?(head) And? Not? List?(tail) ) Concat(head,[tail]);RuleHoldArguments(\"~\",2,30,List?(tail) ) Concat([head],tail);RuleHoldArguments(\"~\",2,10,String?(tail) And? String?(head)) ConcatStrings(head,tail);UnFence(\"~\",2);";
        scriptMap.put("~",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "(Assert(_errorclass, _errorobject) _predicate) <--{ CheckErrorTableau(); Decide(Equal?(predicate, True),  True, {  DestructiveAppend(GetErrorTableau(), [errorclass, errorobject]); False; } );};(Assert(_errorclass) _predicate) <-- Assert(errorclass, True) predicate;(Assert() _predicate) <-- Assert(\"generic\", True) predicate;";
        scriptMap.put("Assert",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"DefaultPrint\", [x]){ DumpErrors(); WriteString(\"Result: \"); Write(x); WriteString(\";\");};HoldArgument(\"DefaultPrint\", x);";
        scriptMap.put("DefaultPrint",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "DumpErrors() <--{ Local(errorobject, errorword); CheckErrorTableau(); ForEach(errorobject, GetErrorTableau()) {  Decide( List?(errorobject), { Decide(  Length(errorobject) >? 0 And? errorobject[1] =? \"warning\", { errorword := \"Warning\"; errorobject[1] := \"\";  }, errorword := \"Error: \"  ); Decide(  Length(errorobject)=?2 And? errorobject[2]=?True, Echo(errorword, errorobject[1]), { Echo(errorword, errorobject[1], \": \", PrintList(Rest(errorobject))); } ); },  Echo(\"Error: \", errorobject) ); }; ClearErrors();};";
        scriptMap.put("DumpErrors",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # EchoInternal(string_String?) <--{ WriteString(string);};20 # EchoInternal(_item) <--{ Write(item);Space();};RulebaseListedHoldArguments(\"Echo\",[firstParameter, parametersList]);5 # Echo(_firstParameter) <-- Echo(firstParameter, []);10 # Echo(_firstParameter, parametersList_List?) <--{ EchoInternal(firstParameter); ForEach(item,parametersList) EchoInternal(item); NewLine(); };20 # Echo(_firstParameter, _secondParameter) <-- Echo(firstParameter, [secondParameter]);Echo() := NewLine();";
        scriptMap.put("Echo",scriptString);
        scriptMap.put("EchoInternal",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Error?() <--{ CheckErrorTableau(); Length(GetErrorTableau())>?0;};Error?(errorclass_String?) <--{ CheckErrorTableau(); GetErrorTableau()[errorclass] !=? Empty;};";
        scriptMap.put("Error?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(ErrorTableau) {  Assign(ErrorTableau, []); GetErrorTableau() := ErrorTableau; ClearErrors() <-- Assign(ErrorTableau, []);  CheckErrorTableau() <-- Decide( Not? List?(ErrorTableau), Assign(ErrorTableau, [[\"general\", \"corrupted ErrorTableau\"]]) );}; GetError(errorclass_String?) <--{ Local(error); error := GetErrorTableau()[errorclass]; Decide( error !=? Empty, error, False );};ClearError(errorclass_String?) <-- AssocDelete(GetErrorTableau(), errorclass);";
        scriptMap.put("GetErrorTableau",scriptString);
        scriptMap.put("ClearError",scriptString);
        scriptMap.put("ClearErrors",scriptString);
        scriptMap.put("GetError",scriptString);
        scriptMap.put("CheckErrorTableau",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Print(_x) <--{ Print(x,60000); NewLine(); DumpErrors();};10 # Print(x_Atom?,_n) <-- Write(x);10 # Print(_x,_n)::(Infix?(Type(x))And? ArgumentsCount(x) =? 2) <--{ Local(bracket); bracket:= (PrecedenceGet(Type(x)) >? n); Decide(bracket,WriteString(\"(\")); Print(x[1],LeftPrecedenceGet(Type(x))); Write(x[0]); Print(x[2],RightPrecedenceGet(Type(x))); Decide(bracket,WriteString(\")\"));};10 # Print(_x,_n)::(Prefix?(Type(x)) And? ArgumentsCount(x) =? 1) <--{ Local(bracket); bracket:= (PrecedenceGet(Type(x)) >? n); Write(x[0]); Decide(bracket,WriteString(\"(\")); Print(x[1],RightPrecedenceGet(Type(x))); Decide(bracket,WriteString(\")\"));};10 # Print(_x,_n)::(Postfix?(Type(x))And? ArgumentsCount(x) =? 1) <--{ Local(bracket); bracket:= (PrecedenceGet(Type(x)) >? n); Decide(bracket,WriteString(\"(\")); Print(x[1],LeftPrecedenceGet(Type(x))); Write(x[0]); Decide(bracket,WriteString(\")\"));};20 # Print(_x,_n)::(Type(x) =? \"List\") <--{ WriteString(\"[\"); PrintArg(x); WriteString(\"]\");};20 # Print(_x,_n)::(Type(x) =? \"Block\") <--{ WriteString(\"[\"); PrintArgBlock(Rest(FunctionToList(x))); WriteString(\"]\");};20 # Print(_x,_n)::(Type(x) =? \"Nth\") <--{ Print(x[1],0); WriteString(\"[\"); Print(x[2],60000); WriteString(\"]\");};100 # Print(x_Function?,_n) <-- { Write(x[0]); WriteString(\"(\"); PrintArg(Rest(FunctionToList(x))); WriteString(\")\"); };10 # PrintArg([]) <-- True;20 # PrintArg(_list) <--{ Print(First(list),60000); PrintArgComma(Rest(list));};10 # PrintArgComma([]) <-- True;20 # PrintArgComma(_list) <--{ WriteString(\",\"); Print(First(list),60000); PrintArgComma(Rest(list));};18 # Print(Complex(0,1),_n) <-- {WriteString(\"I\");};19 # Print(Complex(0,_y),_n) <-- {WriteString(\"I*\");Print(y,4);};19 # Print(Complex(_x,1),_n) <-- {Print(x,7);WriteString(\"+I\");};20 # Print(Complex(_x,_y),_n) <-- {Print(x,7);WriteString(\"+I*\");Print(y,4);};10 # PrintArgBlock([]) <-- True;20 # PrintArgBlock(_list) <--{ Print(First(list),60000); WriteString(\";\"); PrintArgBlock(Rest(list));};";
        scriptMap.put("Print",scriptString);
        scriptMap.put("PrintArg",scriptString);
        scriptMap.put("PrintArgComma",scriptString);
        scriptMap.put("PrintArgBlock",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"TableForm\",[list]){ Local(i); ForEach(i,list) { Write(i); NewLine(); }; True;};";
        scriptMap.put("TableForm",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Macro(\"Tell\",[id]) {Echo(<<,@id,>>);};Macro(\"Tell\",[id,x]) {Echo(<<,@id,>>,Hold(@x),\": \",Eval(@x));};";
        scriptMap.put("Tell",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Macro(\"TellNow\",[id]) {SysOut(\"<< \",@id,\" >>\");};Macro(\"TellNow\",[id,x]) {SysOut(\"<< \",@id,\" >> \",Hold(@x),\": \",Eval(@x));};";
        scriptMap.put("TellNow",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CartesianProduct(xList_List?, yList_List?) <--{ Local(cartesianProduct);  cartesianProduct := [];  ForEach(x, xList) { ForEach(y, yList) { cartesianProduct := DestructiveAppend(cartesianProduct, [x,y]);  }; }; cartesianProduct;};";
        scriptMap.put("CartesianProduct",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(x,i,n,m,aux,dim,result){ 1 # Dimensions(x_List?) <-- { Local(i,n,m,aux,dim,result); result:=List(Length(x)); Decide(Length(x)>?0 And? Length(Select(x, List?))=?Length(x), { n:=Length(x); dim:=MapSingle(Dimensions,x); m:=Minimum(MapSingle(Length,dim)); For(i:=1,i<=?m,i++) { aux:=BuildList(dim[j][i],j,1,n,1); Decide(Minimum(aux)=?Maximum(aux), result:=DestructiveAppend(result,dim[1][i]), i:=m+1 ); }; } ); result; }; 2 # Dimensions(_x) <-- List();}; ";
        scriptMap.put("Dimensions",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Transpose(matrix_List?)::(Length(Dimensions(matrix))>?1) <--{ Local(i,j,result); result:=ZeroMatrix(Length(matrix[1]),Length(matrix)); For(i:=1,i<=?Length(matrix),i++) For(j:=1,j<=?Length(matrix[1]),j++) result[j][i]:=matrix[i][j]; result;};";
        scriptMap.put("Transpose",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # ZeroMatrix(n_NonNegativeInteger?) <-- ZeroMatrix(n,n);10 # ZeroMatrix(n_NonNegativeInteger?,m_NonNegativeInteger?) <--{ Local(i,result); result:=[]; For(i:=1,i<=?n,i++) DestructiveInsert(result,i,ZeroVector(m)); result;};";
        scriptMap.put("ZeroMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"ZeroVector\",[n]){ Local(i,result); result:=[]; For(i:=1,i<=?n,i++) { DestructiveInsert(result,1,0); }; result;};";
        scriptMap.put("ZeroVector",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"MatrixColumn\",[matrix,col]){ Local(m); m:=matrix[1]; Check(col >? 0, \"Argument\", \"MatrixColumn: column index out of range\"); Check(col <=? Length(m), \"Argument\", \"MatrixColumn: column index out of range\"); Local(i,result); result:=[]; For(i:=1,i<=?Length(matrix),i++) DestructiveAppend(result,matrix[i][col]); result;};";
        scriptMap.put("MatrixColumn",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MatrixColumnAugment( M_Matrix?, v_Vector? )::(Length(v)=?Dimensions(M)[2]) <--[ Decide( Not? Assigned?(iDebug), iDebug := False ); Decide( iDebug, Tell(MatrixColumnAugment,[M,v]) ); Local(mRows,nCols,newMat,ir); Local(MT,MA); MT := Transpose(M); MT := MatrixRowStack(MT,v); MA := Transpose(MT);];";
        scriptMap.put("MatrixColumnSwap",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MatrixColumnReplace( M_Matrix?, jCol_PositiveInteger?, v_Vector? )::(Length(v)=?Dimensions(M)[2]) <--{ Decide( Not? Assigned?(iDebug), iDebug := False ); Decide( iDebug, Tell(MatrixColumnReplace,[M,jCol,v]) ); Local(mRows,nCols,MT); [mRows,nCols] := Dimensions(M); Decide( jCol <=? nCols, { MT:=Transpose(M); DestructiveReplace(MT,jCol,v); M:=Transpose(MT); } ); M;};";
        scriptMap.put("MatrixColumnReplace",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MatrixColumnSwap( M_Matrix?, jCol1_PositiveInteger?, jCol2_PositiveInteger? ):: (And?(jCol1<=?Dimensions(M)[2],jCol2<=?Dimensions(M)[2])) <--{ Decide( Not? Assigned?(iDebug), iDebug := False ); Decide( iDebug, Tell(MatrixColumnSwap,[M,jCol1,jCol2]) ); Local(MT); MT := Transpose(M); MT := MatrixRowSwap(MT,jCol1,jCol2); M := Transpose(MT);};";
        scriptMap.put("MatrixColumnSwap",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"MatrixRow\",[matrix,row]){ Check(row >? 0, \"Argument\", \"MatrixRow: row index out of range\"); Check(row <=? Length(matrix), \"Argument\", \"MatrixRow: row index out of range\"); Local(result); result:=matrix[row]; result;};";
        scriptMap.put("MatrixRow",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MatrixRowReplace( M_Matrix?, iRow_PositiveInteger?, v_Vector? )::(Length(v)=?Length(M[1])) <--{ Decide( Not? Assigned?(iDebug), iDebug := False ); Decide( iDebug, Tell(MatrixRowReplace,[M,iRow,v]) ); Local(mRows,nCols); [mRows,nCols] := Dimensions(M); Decide( iRow <=? mRows, DestructiveReplace(M,iRow,v) ); M;};";
        scriptMap.put("MatrixRowReplace",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MatrixRowStack( M_Matrix?, v_Vector? )::(Length(v)=?Dimensions(M)[1]) <--{ Decide( Not? Assigned?(iDebug), iDebug := False ); Decide( iDebug, Tell(MatrixRowStack,[M,v]) ); Local(mRows,nCols,newMat,ir); [mRows,nCols] := Dimensions(M); newMat := ZeroMatrix(mRows+1,nCols); For(ir:=1,ir<?mRows+1,ir++) { MatrixRowReplace(newMat,ir,MatrixRow(M,ir)); }; MatrixRowReplace(newMat,mRows+1,v); newMat;};";
        scriptMap.put("MatrixRowStack",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MatrixRowSwap( M_Matrix?, iRow1_PositiveInteger?, iRow2_PositiveInteger? ):: (And?(iRow1<=?Dimensions(M)[1],iRow2<=?Dimensions(M)[1])) <--{ Decide( Not? Assigned?(iDebug), iDebug := False ); Decide( iDebug, Tell(MatrixRowSwap,[M,iRow1,iRow2]) ); Local(row1,row2); Decide( iRow1 !=? iRow2, { row1 := MatrixRow(M,iRow1); row2 := MatrixRow(M,iRow2); DestructiveReplace(M,iRow1,row2); DestructiveReplace(M,iRow2,row1); } ); M;};";
        scriptMap.put("MatrixRowSwap",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Append\",[list,element]){ Check(List?(list), \"Argument\", \"The first argument must be a list.\"); Insert(list,Length(list)+1,element);};";
        scriptMap.put("Append",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TemplateFunction(\"BuildList\",[body,var,countfrom,countto,step]) { MacroLocal(var); Local(result,nr,ii); result:=[]; nr := (countto - countfrom) / step; ii := 0; While( ii <=? nr ) { MacroAssign( var, countfrom + ii * step ); DestructiveInsert( result,1,Eval(body) ); Assign(ii,AddN(ii,1)); }; DestructiveReverse(result); };HoldArgumentNumber(\"BuildList\",5,1); HoldArgumentNumber(\"BuildList\",5,2); UnFence(\"BuildList\",5);";
        scriptMap.put("BuildList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Contains?\",[list,element]){ Local(result); Assign(result,False); While(And?(Not?(result), Not?(Equal?(list, [])))) { Decide(Equal?(First(list),element), Assign(result, True), Assign(list, Rest(list)) ); }; result;};";
        scriptMap.put("Contains?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Count\",[list,element]){ Local(result); Assign(result,0); ForEach(item,list) Decide(Equal?(item, element), Assign(result,AddN(result,1))); result;};";
        scriptMap.put("Count",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"DestructiveAppend\",[list,element]){ DestructiveInsert(list,Length(list)+1,element);};";
        scriptMap.put("DestructiveAppend",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"DestructiveAppendList\",[list,toadd]){ Local(i,nr); nr:=Length(toadd); For(i:=1,i<=?nr,i++) { DestructiveAppend(list,toadd[i]); }; True;};";
        scriptMap.put("DestructiveAppendList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Difference\",[list1,list2]){ Local(l2,index,result); l2:=FlatCopy(list2); result:=FlatCopy(list1); ForEach(item,list1) { Assign(index,Find(l2,item)); Decide(index>?0, { DestructiveDelete(l2,index); DestructiveDelete(result,Find(result,item)); } ); }; result;};";
        scriptMap.put("Difference",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Drop\", [lst, range]);RuleHoldArguments(\"Drop\", 2, 1, List?(range)) Concat(Take(lst,range[1]-1), Drop(lst, range[2]));RuleHoldArguments(\"Drop\", 2, 2, range >=? 0) Decide( range =? 0 Or? lst =? [], lst, Drop( Rest(lst), range-1 ));RuleHoldArguments(\"Drop\", 2, 2, range <? 0) Take( lst, Decide(AbsN(range) <? Length(lst), Length(lst) + range, 0 ) );";
        scriptMap.put("Drop",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"FillList\", [aItem, aLength]){ Local(i, aResult); aResult:=[]; For(i:=0, i<?aLength, i++) DestructiveInsert(aResult,1,aItem); aResult;};";
        scriptMap.put("FillList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Find( list_List?, _element ) <-- { Local(result,count); result := -1; count := 1; While( And?( result <? 0, Not? ( Equal? (list, []) ))) { Decide(Equal?(First(list), element), result := count ); list := Rest(list); count++; }; result; };";
        scriptMap.put("Find",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " 10 # FindAll( list_List?, _element ) <-- { Local(results,count); results := []; count := 1; While( Not? list =? [] ) { Decide(Equal?( First(list), element), DestructiveAppend(results,count) ); list := Rest(list); count++; }; results; };";
        scriptMap.put("FindAll",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # FindFirst( list_List?, _element ) <-- { Local(result,count); result := -1; count := 1; While( And?( result <? 0, Not? ( Equal? (list, []) ))) { Decide(Equal?(First(list), element), result := count ); list := Rest(list); count++; }; result; };";
        scriptMap.put("FindFirst",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # FuncList(expr_Atom?) <-- [];20 # FuncList(expr_Function?) <--RemoveDuplicates( Concat( [First(FunctionToList(expr))], Apply(\"Concat\", MapSingle(\"FuncList\", Rest(FunctionToList(expr))) ) ));10 # FuncList(expr_Atom?, looklist_List?) <-- [];20 # FuncList(expr_Function?, looklist_List?)::(Not? Contains?(looklist, ToAtom(Type(expr)))) <-- [ToAtom(Type(expr))];30 # FuncList(expr_Function?, looklist_List?) <--RemoveDuplicates( Concat( [First(FunctionToList(expr))], {   Local(item, result); result := []; ForEach(item, expr) result := Concat(result, FuncList(item, looklist)); result; } ));HoldArgumentNumber(\"FuncList\", 1, 1);HoldArgumentNumber(\"FuncList\", 2, 1);";
        scriptMap.put("FuncList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "FuncListArith(expr) := FuncList(expr, [ToAtom(\"+\"), ToAtom(\"-\"), *, /]);HoldArgumentNumber(\"FuncListArith\", 1, 1);";
        scriptMap.put("FuncListArith",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Intersection( LoL_List? )::(AllSatisfy?(\"List?\",LoL)) <--{  Local(nLists,L0,L1,ii,result,LI); nLists := Length(LoL);  Decide( nLists =? 1, { result := LoL[1]; }, { L0 := FlatCopy(LoL[1]); For( ii:=2,ii<=?nLists,ii++) { L1 := FlatCopy(LoL[ii]);  LI := Intersection(L0,L1);  L0 := FlatCopy(LI); };  result := L0; } ); result;}; 11 # Intersection(list1_List?,list2_List?) <--{  Local(l2,index,result); l2:=FlatCopy(list2); result:=[]; ForEach(item,list1) { Assign(index, Find(l2,item)); Decide(index>?0, { DestructiveDelete(l2,index); DestructiveInsert(result,1,item); } ); }; DestructiveReverse(result);};";
        scriptMap.put("Intersection",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Last(LL_List?)::(Length(LL)>?0) <-- First(Reverse(LL));";
        scriptMap.put("Last",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Macro(\"MacroMapArgs\",[expr,oper]){ Local(ex,tl,op); Assign(op,@oper); Assign(ex,FunctionToList(@expr)); Assign(tl,Rest(ex)); ListToFunction(Concat([ex[1]], `MacroMapSingle(@op,Hold(@tl))) );};UnFence(\"MacroMapArgs\",2);HoldArgument(\"MacroMapArgs\",oper);";
        scriptMap.put("MacroMapArgs",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TemplateFunction(\"MacroMapSingle\",[func,list]){ Local(mapsingleresult); mapsingleresult:=[]; ForEach(mapsingleitem,list) { DestructiveInsert(mapsingleresult,1, `ApplyFast(func,[Hold(Hold(@mapsingleitem))])); }; DestructiveReverse(mapsingleresult);};UnFence(\"MacroMapSingle\",2);HoldArgument(\"MacroMapSingle\",func);HoldArgument(\"MacroMapSingle\",list);";
        scriptMap.put("MacroMapSingle",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(func,lists,mapsingleresult,mapsingleitem){ TemplateFunction(\"Map\",[func,lists]) { Local(mapsingleresult,mapsingleitem); mapsingleresult:=[]; lists:=Transpose(lists); ForEach(mapsingleitem,lists) { DestructiveInsert(mapsingleresult,1,Apply(func,mapsingleitem)); }; DestructiveReverse(mapsingleresult); }; UnFence(\"Map\",2); HoldArgument(\"Map\",func);};";
        scriptMap.put("Map",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TemplateFunction(\"MapArgs\",[expr,oper]){ Assign(expr,FunctionToList(expr)); ListToFunction(Concat([expr[1]], Apply(\"MapSingle\",[oper,Rest(expr)]) ) );};UnFence(\"MapArgs\",2);HoldArgument(\"MapArgs\",oper);";
        scriptMap.put("MapArgs",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TemplateFunction(\"MapSingle\",[func,list]){ Local(mapsingleresult); mapsingleresult:=[]; ForEach(mapsingleitem,list) { DestructiveInsert(mapsingleresult,1, Apply(func,[mapsingleitem])); }; DestructiveReverse(mapsingleresult);};UnFence(\"MapSingle\",2);HoldArgument(\"MapSingle\",func);";
        scriptMap.put("MapSingle",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Partition(lst, len):= Decide( Length(lst) <? len Or? len =? 0, [], Concat( [Take(lst,len)], Partition(Drop(lst,len), len) ));";
        scriptMap.put("Partition",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Pop\",[stack,index]){ Local(result); result:=stack[index]; DestructiveDelete(stack,index); result;};";
        scriptMap.put("Pop",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"PopBack\",[stack]) Pop(stack,Length(stack));";
        scriptMap.put("PopBack",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"PopFront\",[stack]) Pop(stack,1);";
        scriptMap.put("PopFront",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # PrintList(list_List?) <-- PrintList(list, \", \");10 # PrintList([], padding_String?) <-- \"\";20 # PrintList(list_List?, padding_String?) <-- PipeToString() { Local(i); ForEach(i, list) { Decide(Not?(Equal?(i, First(list))), WriteString(padding)); Decide(String?(i), WriteString(i), Decide(List?(i), WriteString(\"[\" ~ PrintList(i, padding) ~ \"]\"), Write(i))); };};";
        scriptMap.put("PrintList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Push\",[stack,element]){ DestructiveInsert(stack,1,element);};";
        scriptMap.put("Push",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Remove(list, expression) :={ Local(result); Assign(result,[]); ForEach(item,list) Decide(item !=? expression, DestructiveAppend(result,item)); result;};";
        scriptMap.put("Remove",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"RemoveDuplicates\",[list]){ Local(result); Assign(result,[]); ForEach(item,list) Decide(Not?(Contains?(result,item)),DestructiveAppend(result,item)); result;};";
        scriptMap.put("RemoveDuplicates",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Reverse(list):=DestructiveReverse(FlatCopy(list));";
        scriptMap.put("Reverse",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(predicate,list,result,item){ Function(\"Select\",[list,predicate]) { Local(result); result:=[]; ForEach(item,list) { Decide(Apply(predicate,[item]),DestructiveAppend(result,item)); }; result; }; HoldArgument(\"Select\",predicate); UnFence(\"Select\",2);};";
        scriptMap.put("Select",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Swap\",[list,index1,index2]){ Local(item1,item2); item1:=list[index1]; item2:=list[index2]; list[index1] := item2; list[index2] := item1;};";
        scriptMap.put("Swap",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Take\", [lst, range]);RuleHoldArguments(\"Take\", 2, 1, List?(range)) Take( Drop(lst, range[1] -1), range[2] - range[1] + 1);RuleHoldArguments(\"Take\", 2, 2, range >=? 0) Decide( Length(lst)=?0 Or? range=?0, [], Concat([First(lst)], Take(Rest(lst), range-1)));RuleHoldArguments(\"Take\", 2, 2, range <? 0) Drop( lst, Decide(AbsN(range) <? Length(lst), Length(lst) + range, 0 ));";
        scriptMap.put("Take",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Union\",[list1,list2]){ RemoveDuplicates(Concat(list1,list2));};";
        scriptMap.put("Union",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "VarList(_expr) <-- VarList(expr,\"Variable?\");Function(\"VarList\",[expr,filter]){ RemoveDuplicates(VarListAll(expr,filter));};";
        scriptMap.put("VarList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "  VarListAll(_expr) <-- VarListAll(expr,\"Variable?\");10 # VarListAll(_expr,_filter)::(Apply(filter,[expr]) =? True) <-- [expr];20 # VarListAll(expr_Function?,_filter) <--{ Local(item,result, flatlist);  Assign(flatlist,Rest(FunctionToList(expr)));  Assign(result,[]);  ForEach(item,flatlist) Assign(result,Concat(result,VarListAll(item,filter)));  result;};30 # VarListAll(_expr,_filter) <-- [];";
        scriptMap.put("VarListAll",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "VarListArith(expr) := VarListSome(expr, [ToAtom(\"+\"), ToAtom(\"-\"), *, /]);";
        scriptMap.put("VarListArith",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # VarListSome([], _looklist) <-- [];10 # VarListSome(expr_Variable?, _looklist) <-- [expr];15 # VarListSome(expr_Atom?, _looklist) <-- [];20 # VarListSome(expr_Function?, looklist_List?)::(Not? Contains?(looklist, ToAtom(Type(expr)))) <-- [expr];30 # VarListSome(expr_Function?, looklist_List?) <--RemoveDuplicates( {  Local(item, result); result := []; ForEach(item, expr) result := Concat(result, VarListSome(item, looklist)); result; });";
        scriptMap.put("VarListSome",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(GlobalStack, x){ GlobalStack := []; GlobalPop(x_Atom?) <-- { Check(Length(GlobalStack)>?0, \"Invariant\", \"GlobalPop: Error: empty GlobalStack\"); MacroAssign(x, PopFront(GlobalStack)); Eval(x); }; HoldArgumentNumber(\"GlobalPop\", 1, 1); GlobalPop() <-- { Check(Length(GlobalStack)>?0, \"Invariant\", \"GlobalPop: Error: empty GlobalStack\"); PopFront(GlobalStack); }; GlobalPush(_x) <-- { Push(GlobalStack, x); x; };};";
        scriptMap.put("GlobalPush",scriptString);
        scriptMap.put("GlobalPop",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "20 # (list_List? AddTo _rest) <--{ Local(res); res:=[]; ForEach(item,list) { res := Concat(res,item AddTo rest); }; res;};30 # (_aitem AddTo list_List?) <--{ MapSingle([[orig],aitem And? orig],list);};40 # (_aitem AddTo _b) <-- aitem And? b;";
        scriptMap.put("AddTo",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Where\",[left,right]);UnFence(\"Where\",2);10 # (_body Where var_Atom? == _value) <-- `{ Local(@var);@var := @value;@body;};20 # (_body Where (_a And? _b)) <--{ Assign(body,`(@body Where @a)); `(@body Where @b);};30 # (_body Where []) <-- [];40 # (_body Where list_List?)::List?(list[1]) <-- { Local(head,rest); head:=First(list); rest:=Rest(list); rest:= `(@body Where @rest); `(@body Where @head) ~ rest; };50 # (_body Where list_List?) <-- { Local(head,rest); While (list !=? []) { head:=First(list); body := `(@body Where @head); list:=Rest(list); }; body; };60 # (_body Where _var == _value) <-- Substitute(var,value)body;";
        scriptMap.put("Where",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"<-\",[left,right]);HoldArgument(\"<-\",left);HoldArgument(\"<-\",right);";
        scriptMap.put("<-",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(localResult) { localResult := True;   10 # LocPredicate(exp_Atom?) <-- { Local(patternsList, result);  patternsList := patterns;  result := False;  While (patternsList !=? []) { Decide(First(First(patternsList)) =? exp, { Decide(InVerboseMode() =? True, Echo(Last(First(patternsList))));  Assign(localResult,Eval(First(Rest(First(patternsList)))));  result := True;  patternsList:=[]; }, { patternsList := Rest(patternsList); }); };  result; };   10 # LocPredicate(exp_Function?) <-- { Local(patternsList, result, head);  patternsList := patterns;  result := False;  While (patternsList !=? []) { Assign(head, First(First(patternsList)));  Decide(Not?(Atom?(head)) And? exp[0] =? head[1] And? PatternMatch?(head[2], exp), { Decide(InVerboseMode() =? True, Echo(Last(First(patternsList))));  Assign(localResult, Eval(First(Rest(First(patternsList)))));  Assign(result, True);  Assign(patternsList, []); }, { Assign(patternsList, Rest(patternsList)); }); };  result; };    20 # LocPredicate(_exp) <-- False;   LocChange(_exp) <-- localResult;  }; UnFence(\"LocPredicate\",1);UnFence(\"LocChange\",1);10 # PatternCompile([_pat, _post, _exp]) <-- [ [pat[0], PatternCreate(pat, post)], exp, \"UNNAMED\" ];20 # PatternCompile([pat_Function?, _exp]) <-- [ [pat[0], PatternCreate(pat,True)], exp, \"UNNAMED\" ];30 # PatternCompile([pat_Atom?, _exp]) <-- [ pat, exp, \"UNNAMED\" ];40 # PatternCompile(pat_Function? <- _exp) <-- { Local(justPattern, postPredicate, ruleName);  justPattern := pat;  Decide(Type(justPattern) =? \"_\", {  justPattern := pat[1]; postPredicate := pat[2]; }, {  postPredicate := True; } );    Decide(Type(justPattern) =? \"#\", {  ruleName := pat[1]; justPattern := pat[2];  Decide(Function?(justPattern), { [ [justPattern[0], PatternCreate(justPattern, postPredicate)], exp , ruleName]; }, {  [ justPattern, exp, ruleName ]; }  ); }, { ruleName := \"UNNAMED\"; justPattern := pat; [ [justPattern[0], PatternCreate(justPattern, postPredicate)], exp , ruleName]; } ); };50 # PatternCompile(pat_Atom? <- _exp) <-- [ pat, exp, \"UNNAMED\" ];PatternsCompile(patterns) :={ MapSingle(\"PatternCompile\", patterns);};";
        scriptMap.put("PatternsCompile",scriptString);
        scriptMap.put("PatternCompile",scriptString);
        scriptMap.put("LocPredicate",scriptString);
        scriptMap.put("LocChange",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # (_expression /:: _patterns) <--{ Local(old);  Assign(patterns, PatternsCompile(patterns));  Assign(old, expression);  Assign(expression, MacroSubstituteApply(expression,\"LocPredicate\",\"LocChange\"));  While (expression !=? old) { Assign(old, expression);  Assign(expression, MacroSubstituteApply(expression,\"LocPredicate\",\"LocChange\")); };  expression;};";
        scriptMap.put("/::",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # (_expression /: _patterns) <--{ Assign(patterns, PatternsCompile(patterns));  MacroSubstituteApply(expression,\"LocPredicate\",\"LocChange\");};";
        scriptMap.put("/:",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # DisassembleExpression( _expr ) <--{ Local(vars); vars := MultiExpressionList( expr ); DisassembleExpression( expr, vars );};10 # DisassembleExpression( _expr, vars_List? ) <--{ Local(mexpr,func,termList,result,powers,coeffs); mexpr := MakeMultiNomial(expr,vars); func := Lambda([x,y],Decide(y!=?0,DestructiveAppend(termList,[x,y]))); termList := []; ScanMultiNomial(func,mexpr); result := Concat([vars],Transpose(termList));};";
        scriptMap.put("DisassembleExpression",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Groebner(f_List?) <--{ Local(vars,i,j,S,nr,r); nr:=Length(f); vars:=VarList(f); For(i:=1,i<=?nr,i++) { f[i] := MakeMultiNomial(f[i],vars); }; S:=[]; For(i:=1,i<?nr,i++) For(j:=i+1,j<=?nr,j++) { r := (MultiDivide(MultiS(f[i],f[j],f[i]),f)[2]); Decide(NormalForm(r) !=? 0, S:= r~S); f:=Concat(f,S); S:=[]; nr:=Length(f); }; MapSingle(\"NormalForm\",Concat(f));};";
        scriptMap.put("Groebner",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ListTerms(_expr) <--{ Decide(InVerboseMode(),Tell(\"ListTerms\",expr)); Local(termList); Local(op,x2,x3); termList := []; Decide(Function?(expr), { [op,x2,x3] := FunctionToList(expr); Decide(InVerboseMode(),Tell(\" \",[op,x2,x3]));  terms(op,x2,x3); }, { Push(termList,expr); } ); termList;};10 # terms(_op,_x2,_x3)::(op=?ToAtom(\"+\") Or? op=?ToAtom(\"-\")) <--{ Decide(InVerboseMode(),{Tell(\" terms10\",op);Tell(\" \",[x2,x3]);}); Local(sgn); Decide(op=?ToAtom(\"+\"),sgn:=1,sgn:=-1); Push(termList,sgn*x3); Decide(InVerboseMode(),Tell(\" \",termList)); Decide(Function?(x2), { Local(L); L := FunctionToList(x2); Decide(InVerboseMode(),Tell(\" \",L)); Decide(Length(L)=?3,terms(L[1],L[2],L[3]),Push(termList,x2)); }, { Push(termList,x2); } );};UnFence(\"terms\",3);20 # terms(_op,_x2,_x3) <--{ Decide(InVerboseMode(),{Tell(\" terms20\",op);Tell(\" \",[x2,x3]);}); Local(F); F := ListToFunction([op,x2,x3]); Push(termList,F); Decide(InVerboseMode(),Tell(\" \",termList)); termList;};UnFence(\"terms\",3);";
        scriptMap.put("ListTerms",scriptString);
        scriptMap.put("terms",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MM(_expr) <-- MM(expr,MultiExpressionList(expr));MM(_expr,_vars) <-- MakeMultiNomial(expr,vars);";
        scriptMap.put("MM",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MultiDivTerm(MultiNomial(_vars,_term1),MultiNomial(_vars,_term2)) <--{ Local(lm1,lm2); Assign(lm1,MultiLeadingTerm(MultiNomial(vars,term1)) ); Assign(lm2,MultiLeadingTerm(MultiNomial(vars,term2)) ); CreateTerm(vars,[lm1[1]-lm2[1],lm1[2] / lm2[2]]);};MultiS(_g,_h,MultiNomial(_vars,_terms)) <--{ Local(gamma); gamma :=Maximum(MultiDegree(g),MultiDegree(h)); Local(result,topterm); topterm := MM(Product(vars^gamma)); result := MultiDivTerm(topterm,MultiLT(g))*g - MultiDivTerm(topterm,MultiLT(h))*h; result;};";
        scriptMap.put("MultiDivTerm",scriptString);
        scriptMap.put("MultiS",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "20 # MultiDivide(_f,g_List?) <--{ Decide(InVerboseMode(),Tell(\"MultiDivide_1\",[f,g])); Local(i,v,q,r,nr); v := MultiExpressionList(f+Sum(g)); f := MakeMultiNomial(f,v); nr := Length(g); For(i:=1,i<=?nr,i++) { g[i] := MakeMultiNomial(g[i],v); }; Decide(Not? Multi?(f),Break()); [q,r] := MultiDivide(f,g); q := MapSingle(\"NormalForm\",q); r := NormalForm(r); [q,r];};10 # MultiDivide(f_Multi?,g_List?) <--{ Decide(InVerboseMode(),Tell(\"MultiDivide_2\",[f,g])); Local(i,nr,q,r,p,v,finished); nr := Length(g); v := MultiVars(f); q := FillList(0,nr); r := 0; p := f; finished := MultiZero(p); Local(plt,glt); While (Not? finished) { plt := MultiLT(p);  For(i:=1,i<=?nr,i++) { glt := MultiLT(g[i]); If(MultiLM(glt) =? MultiLM(plt) Or? MultiTermLess([MultiLM(glt),1], [MultiLM(plt),1])) {  If(Select(MultiLM(plt)-MultiLM(glt),[[n],n<?0]) =? [] ) { Local(ff,ltbefore,ltafter); ff := CreateTerm(v,[MultiLM(plt)-MultiLM(glt),MultiLC(plt)/MultiLC(glt)]); Decide(InVerboseMode(),Tell(\" \",NormalForm(ff))); q[i] := q[i] + ff; ltbefore := MultiLeadingTerm(p); p := p - ff*g[i]; ltafter := MultiLeadingTerm(p); If(ltbefore[1] =? ltafter[1]) { ltafter := MultiLT(p); p := p-ltafter; }; i := nr + 2; }; }; }; Decide(i =? nr + 1, { r := r + LocalSymbols(a,b)(Substitute(a,b)plt); p := p - LocalSymbols(a,b)(Substitute(a,b)plt); } ); finished := MultiZero(p); }; [q,r];};";
        scriptMap.put("MultiDivide",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # MultiGcd( 0,_g) <-- g;10 # MultiGcd(_f, 0) <-- f;20 # MultiGcd(_f,_g) <--{ Local(v); v:=MultiExpressionList(f+g);  NormalForm(MultiGcd(MakeMultiNomial(f,v),MakeMultiNomial(g,v)));};5 # MultiGcd(f_Multi?,g_Multi?)::(MultiTermLess([MultiLM(f),1],[MultiLM(g),1])) <--{ MultiGcd(g,f);};5 # MultiGcd(MultiNomial(_vars,_terms),g_Multi?)::(MultiLM(MultiNomial(vars,terms)) =? MultiLM(g)) <-- CreateTerm(vars,[FillList(0,Length(vars)),1]);5 # MultiGcd(MultiNomial(_vars,_terms),g_Multi?)::( Select(MultiLM(MultiNomial(vars,terms))-MultiLM(g), [[n],n<?0] ) !=? []) <-- CreateTerm(vars,[FillList(0,Length(vars)),1]);5 # MultiGcd(MultiNomial(_vars,_terms),g_Multi?)::(NormalForm(g) =? 0) <-- CreateTerm(vars,[FillList(0,Length(vars)),1]);10 # MultiGcd(f_Multi?,g_Multi?) <--{ LocalSymbols(a) { Assign(f,Substitute(a,a)f); Assign(g,Substitute(a,a)g); }; Local(new); While(g !=? 0) { Assign(new, MultiDivide(f,[g]));Decide(new[1][1]=?0,{ g:=MakeMultiNomial(1,MultiVars(f)); new[2]:=0;}); Assign(new, new[2]); Assign(f,g); Assign(g,new); }; MultiPrimitivePart(f);};";
        scriptMap.put("MultiGcd",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"MultiNomial\",[vars,terms]);";
        scriptMap.put("MultiNomial",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MultiSimp(_expr) <--{ Local(vars); vars:=MultiExpressionList(expr); MultiSimp2(MM(expr,vars));};10 # MultiSimp2(_a / _b) <--{ Local(c1,c2,gcd,cmn,vars); c1 := MultiContentTerm(a); c2 := MultiContentTerm(b); gcd:=Gcd(c1[2],c2[2]); c1[2] := c1[2]/gcd; c2[2] := c2[2]/gcd; cmn:=Minimum(c1[1],c2[1]); c1[1] := c1[1] - cmn; c2[1] := c2[1] - cmn; vars:=MultiVars(a); Check(vars =? MultiVars(b), \"Argument\", \"incompatible Multivars to simplify\"); (NormalForm(CreateTerm(vars,c1))/NormalForm(CreateTerm(vars,c2))) *(NormalForm(MultiPrimitivePart(a))/NormalForm(MultiPrimitivePart(b)));};20 # MultiSimp2(expr_Multi?) <--{ NormalForm(MultiContent(expr))*NormalForm(MultiPrimitivePart(expr));};30 # MultiSimp2(_expr) <-- expr;MultiContent(multi_Multi?)<--{ Local(least,gcd); Assign(least, MultiDegree(multi)); Assign(gcd,MultiLeadingCoef(multi)); ScanMultiNomial(\"MultiContentScan\",multi); CreateTerm(MultiVars(multi),MultiContentTerm(multi));};MultiContentTerm(multi_Multi?)<--{ Local(least,gcd); Assign(least, MultiDegree(multi)); Assign(gcd,MultiLeadingCoef(multi)); ScanMultiNomial(\"MultiContentScan\",multi); [least,gcd];};MultiContentScan(_coefs,_fact) <--{ Assign(least,Minimum([least,coefs])); Assign(gcd,Gcd(gcd,fact));};UnFence(\"MultiContentScan\",2);MultiPrimitivePart(MultiNomial(vars_List?,_terms))<--{ Local(cont); Assign(cont,MultiContentTerm(MultiNomial(vars,terms))); Assign(cont,CreateTerm(vars,[-cont[1],1/Rationalize(cont[2])])); MultiNomialMultiply(MultiNomial(vars,terms), cont);};10 # MultiRemoveGcd(x_Multi?/y_Multi?) <--{ Local(gcd); Assign(gcd,MultiGcd(x,y)); Assign(x,MultiDivide(x,[gcd])[1][1]); Assign(y,MultiDivide(y,[gcd])[1][1]); x/y;};20 # MultiRemoveGcd(_x) <-- x;5 # MultiDegree(MultiNomial(_vars,_term))::(Not?(List?(term))) <-- [];10 # MultiDegree(MultiNomial(_vars,[])) <-- FillList(-Infinity,Length(vars));20 # MultiDegree(MultiNomial(_vars,_terms)) <-- (MultiLeadingTerm(MultiNomial(vars,terms))[1]);10 # MultiLeadingCoef(MultiNomial(_vars,_terms)) <-- (MultiLeadingTerm(MultiNomial(vars,terms))[2]);10 # MultiLeadingMono(MultiNomial(_vars,[])) <-- 0;20 # MultiLeadingMono(MultiNomial(_vars,_terms)) <-- Product(vars^(MultiDegree(MultiNomial(vars,terms))));20 # MultiLeadingTerm(_m) <-- MultiLeadingCoef(m) * MultiLeadingMono(m);MultiVars(MultiNomial(_vars,_terms)) <-- vars;20 # MultiLT(multi_Multi?) <-- CreateTerm(MultiVars(multi),MultiLeadingTerm(multi));10 # MultiLM(multi_Multi?) <-- MultiDegree(multi);10 # MultiLC(MultiNomial(_vars,[])) <-- 0;20 # MultiLC(multi_Multi?) <-- MultiLeadingCoef(multi);DropZeroLC(multi_Multi?) <-- MultiDropLeadingZeroes(multi);";
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
        scriptString[1] = "10 # ReassembleListTerms( disassembly_List? ) <--{ Local(vars,lst,powers,coeffs,ii,pows,coef,term); vars := disassembly[1]; powers := disassembly[2]; coeffs := disassembly[3]; lst := []; For(ii:=1,ii<=?Length(powers),ii++) { pows := powers[ii]; coef := coeffs[ii];  term := coef*Product(vars^pows);  DestructiveAppend(lst,term); }; lst;};";
        scriptMap.put("ReassembleListTerms",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MultiExpressionList(_expr) <-- VarList(expr,\"MultiExpression?\");10 # MultiExpression?(_x + _y) <-- False;10 # MultiExpression?(_x - _y) <-- False;10 # MultiExpression?( - _y) <-- False;10 # MultiExpression?(_x * _y) <-- False;10 # MultiExpression?(_x / _y) <-- False;10 # MultiExpression?(_x ^ y_PositiveInteger?) <-- False;11 # MultiExpression?(_x ^ _y)::(PositiveInteger?(Simplify(y))) <-- False;10 # MultiExpression?(x_MultiConstant?) <-- False;MultiConstant?(_n) <-- (VarList(n,\"IsVr\")=?[]);10 # IsVr(n_Number?) <-- False;10 # IsVr(n_Function?) <-- False;10 # IsVr(n_String?) <-- False;20 # IsVr(_n) <-- True;100 # MultiExpression?(_x) <-- True;10 # Multi?(MultiNomial(vars_List?,_terms)) <-- True;20 # Multi?(_anything) <-- False;LocalSymbols(a,vars,pow) { 20 # MultiSingleFactor(_vars,_a,_pow) <-- { Local(term); term:=[FillList(0,Length(vars)),1]; term[1][Find(vars,a)] := pow; CreateTerm(vars,term); };};LocalSymbols(x,y,vars) {10 # MakeMultiNomial(x_MultiConstant?,vars_List?) <-- CreateTerm(vars,[FillList(0,Length(vars)),x]);20 # MakeMultiNomial(_x,vars_List?)::(Contains?(vars,x)) <-- MultiSingleFactor(vars,x,1);30 # MakeMultiNomial(_x + _y,vars_List?) <-- MakeMultiNomial(x,vars) + MakeMultiNomial(y,vars);30 # MakeMultiNomial(_x * _y,vars_List?) <-- MakeMultiNomial(x,vars) * MakeMultiNomial(y,vars);30 # MakeMultiNomial(- _x,vars_List?) <-- -MakeMultiNomial(x,vars);30 # MakeMultiNomial(_x - _y,vars_List?) <-- MakeMultiNomial(x,vars) - MakeMultiNomial(y,vars);30 # MakeMultiNomial(MultiNomial(_vars,_terms),vars_List?) <-- MultiNomial(vars,terms);100 # MakeMultiNomial(_x,vars_List?) <-- { CreateTerm(vars,[FillList(0,Length(vars)),x]); };};LocalSymbols(x,y,z,vars,gcd,a,a) { 20 # MakeMultiNomial(_x / (_y / _z),vars_List?) <-- MakeMultiNomial((x*z) / y,vars_List?); 20 # MakeMultiNomial((_x / _y) / _z,vars_List?) <-- MakeMultiNomial((x*z) / y,vars_List?); 25 # MakeMultiNomial(_x / y_Constant?,vars_List?) <-- MakeMultiNomial(1/y,vars)*MakeMultiNomial(x,vars); 30 # MakeMultiNomial(_x / _y,vars_List?) <-- { Local(result); Assign(result,MultiRemoveGcd(MakeMultiNomial(x,vars)/MakeMultiNomial(y,vars))); result; }; };MultiNomial(_vars,_x) + MultiNomial(_vars,_y) <-- MultiNomialAdd(MultiNomial(vars,x), MultiNomial(vars,y));MultiNomial(_vars,_x) * MultiNomial(_vars,_y) <-- MultiNomialMultiply(MultiNomial(vars,x), MultiNomial(vars,y));MultiNomial(_vars,_x) - MultiNomial(_vars,_y) <-- MultiNomialAdd(MultiNomial(vars,x), MultiNomialNegate(MultiNomial(vars,y))); - MultiNomial(_vars,_y) <-- MultiNomialNegate(MultiNomial(vars,y));MultiNomial(_vars,_x) / MultiNomial(_vars,_x) <-- MakeMultiNomial(1, vars);LocalSymbols(x,n,vars) {30 # MakeMultiNomial(_x ^ n_Integer?,vars_List?)::(Contains?(vars,x)) <-- MultiSingleFactor(vars,x,n);40 # MakeMultiNomial(_x ^ n_PositiveInteger?,vars_List?) <-- { Local(mult,result); Assign(mult,MakeMultiNomial(x,vars)); Assign(result,MakeMultiNomial(1,vars)); While(n>?0) { Decide(n&1 !=? 0, Assign(result, MultiNomialMultiply(result,mult))); Assign(n,n>>1); Decide(n!=?0,Assign(mult,MultiNomialMultiply(mult,mult))); }; result; }; 15 # MakeMultiNomial(_x ^ _n,vars_List?)::(Not?(Integer?(n)) And? Integer?(Simplify(n))) <-- MakeMultiNomial( x ^ Simplify(n),vars); 50 # MakeMultiNomial(_x ^ (_n),vars_List?)::(Contains?(vars,x)) <-- { Assign(n,Simplify(n)); Decide(Integer?(n), MultiSingleFactor(vars,x,n), MultiSingleFactor(vars,x^n,1) ); };};x_Multi? + (y_Multi?/z_Multi?) <-- ((x*z+y)/z);(y_Multi?/z_Multi?) + x_Multi? <-- ((x*z+y)/z);(y_Multi?/z_Multi?) + (x_Multi?/w_Multi?) <-- ((y*w+x*z)/(z*w));(y_Multi?/z_Multi?) - (x_Multi?/w_Multi?) <-- ((y*w-x*z)/(z*w));(y_Multi?/z_Multi?) * (x_Multi?/w_Multi?) <-- ((y*x)/(z*w));(y_Multi?/z_Multi?) / (x_Multi?/w_Multi?) <-- ((y*w)/(z*x));x_Multi? - (y_Multi?/z_Multi?) <-- ((x*z-y)/z);(y_Multi?/z_Multi?) - x_Multi? <-- ((y-x*z)/z);(a_Multi?/(c_Multi?/b_Multi?)) <-- ((a*b)/c);((a_Multi?/c_Multi?)/b_Multi?) <-- (a/(b*c));((a_Multi?/b_Multi?) * c_Multi?) <-- ((a*c)/b);(a_Multi? * (c_Multi?/b_Multi?)) <-- ((a*c)/b);- ((a_Multi?)/(b_Multi?)) <-- (-a)/b;MultiNomialMultiply( MultiNomial(_vars,_terms1)/MultiNomial(_vars,_terms2), MultiNomial(_vars,_terms3)/MultiNomial(_vars,_terms4)) <--{ MultiNomialMultiply(MultiNomial(vars,terms1),MultiNomial(vars,terms3))/ MultiNomialMultiply(MultiNomial(vars,terms2),MultiNomial(vars,terms4));};MultiNomialMultiply( MultiNomial(_vars,_terms1)/MultiNomial(_vars,_terms2), MultiNomial(_vars,_terms3)) <--{ MultiNomialMultiply(MultiNomial(vars,terms1),MultiNomial(vars,terms3))/ MultiNomial(vars,terms2);};MultiNomialMultiply( MultiNomial(_vars,_terms3), MultiNomial(_vars,_terms1)/MultiNomial(_vars,_terms2)) <--{ MultiNomialMultiply(MultiNomial(vars,terms1),MultiNomial(vars,terms3))/ MultiNomial(vars,terms2);};10 # MultiNomialMultiply(_a,_b) <--{ Echo([\"ERROR!\",a,b]); Echo([\"ERROR!\",Type(a),Type(b)]);};";
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
        scriptString[1] = "LocalSymbols(NormalMultiNomial) {CreateTerm(_vars,[_coefs,_fact]) <-- MultiNomial(vars,CreateSparseTree(coefs,fact));MultiNomialAdd(MultiNomial(_vars,_x), MultiNomial(_vars,_y)) <-- MultiNomial(vars,AddSparseTrees(Length(vars),x,y));MultiNomialMultiplyAdd(MultiNomial(_vars,_x), MultiNomial(_vars,_y),_coefs,_fact) <-- MultiNomial(vars,MultiplyAddSparseTrees(Length(vars),x,y,coefs,fact));MultiNomialNegate(MultiNomial(_vars,_terms)) <-- { SparseTreeMap(Hold([[coefs,list],-list]),Length(vars),terms); MultiNomial(vars,terms); };MultiNomialMultiply(MultiNomial(_vars,_x),_multi2) <-- { Local(result); Assign(result,MakeMultiNomial(0,vars)); SparseTreeScan(\"muadm\",Length(vars),x); result; };muadm(_coefs,_fact) <--{ Assign(result,MultiNomialMultiplyAdd(result, multi2,coefs,fact));};UnFence(\"muadm\",2);RulebaseHoldArguments(\"NormalForm\",[expression]);RuleHoldArguments(\"NormalForm\",1,1000,True) expression;0 # NormalForm(UniVariate(_var,_first,_coefs)) <-- ExpandUniVariate(var,first,coefs);10 # NormalForm(x_Multi?/y_Multi?) <-- NormalForm(x)/NormalForm(y);20 # NormalForm(MultiNomial(_vars,_list) ) <-- NormalMultiNomial(vars,list,1);10 # NormalMultiNomial([],_term,_prefact) <-- prefact*term;20 # NormalMultiNomial(_vars,_list,_prefact) <-- { Local(first,rest,result); Assign(first,First(vars)); Assign(rest,Rest(vars)); Assign(result,0); ForEach(item,list) { Assign(result,result+NormalMultiNomial(rest,item[2],prefact*first^(item[1]))); }; result; };}; MultiLeadingTerm(MultiNomial(_vars,_terms)) <-- { Local(coefs,fact); Assign(coefs,MultiDegreeScanHead(terms,Length(vars))); [coefs,fact]; };10 # MultiDegreeScanHead(_tree,0) <-- { Assign(fact,tree); []; };10 # MultiDegreeScanHead(_tree,1) <-- { Assign(fact,tree[1][2]); [tree[1][1]]; };20 # MultiDegreeScanHead(_tree,_depth) <-- { (tree[1][1])~MultiDegreeScanHead(tree[1][2],depth-1); };UnFence(\"MultiDegreeScanHead\",2);ScanMultiNomial(_op,MultiNomial(vars_List?,_terms)) <-- SparseTreeScan(op,Length(vars),terms);UnFence(\"ScanMultiNomial\",2);MultiDropLeadingZeroes(MultiNomial(_vars,_terms)) <-- { MultiDropScan(terms,Length(vars)); MultiNomial(vars,terms); };10 # MultiDropScan(0,0) <-- True;10 # MultiDropScan([_n,0],0) <-- True;20 # MultiDropScan(_n,0) <-- { False; };30 # MultiDropScan(_tree,_depth) <-- { Local(i); For(i:=1,i<=?Length(tree),i++) { If(MultiDropScan(tree[i][2],depth-1)) { DestructiveDelete(tree,i); i--; } Else { i:=Length(tree); }; }; (tree =? []); };UnFence(\"MultiDropScan\",2);MultiTermLess([_deg1,_fact1],[_deg2,_fact2]) <-- { Local(deg); Assign(deg, deg1-deg2); While(deg !=? [] And? First(deg) =? 0) { Assign(deg, Rest(deg));}; ((deg =? []) And? (fact1-fact2 <? 0)) Or? ((deg !=? []) And? (deg[1] <? 0)); };20 # MultiZero(multi_Multi?) <--{ CheckMultiZero(DropZeroLC(multi));};10 # CheckMultiZero(MultiNomial(_vars,[])) <-- True;20 # CheckMultiZero(MultiNomial(_vars,_terms)) <-- False;";
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
        scriptString[1] = "10 # SparseTreeGet([],_tree) <-- tree;20 # SparseTreeGet(_key,_tree) <--{ SparseTreeGet2(Rest(key),Assoc(First(key),tree));};10 # SparseTreeGet2(_key,Empty) <-- 0;20 # SparseTreeGet2(_key,_item) <-- SparseTreeGet(key,First(Rest(item)));10 # SparseTreeSet([_i],_tree,_newvalue) <--{ Local(Current,assoc,result); Assign(assoc,Assoc(i,tree)); If(assoc=?Empty) { Assign(Current,0); Assign(result,Eval(newvalue)); AddSparseTrees(1,tree,CreateSparseTree([i],result)); } Else { Assign(Current,assoc[2]); Assign(result,Eval(newvalue)); assoc[2] := result; }; result;};20 # SparseTreeSet(_key,_tree,_newvalue) <--{ SparseTreeSet2(Rest(key),Assoc(First(key),tree));};10 # SparseTreeSet2(_key,Empty) <-- 0;20 # SparseTreeSet2(_key,_item) <-- SparseTreeSet(key,First(Rest(item)),newvalue);UnFence(\"SparseTreeSet\",3);UnFence(\"SparseTreeSet2\",2);LocalSymbols(SparseTreeMap2,SparseTreeScan2,Muaddterm,MuMuaddterm, meradd,meraddmap) {10 # CreateSparseTree([],_fact) <-- fact;20 # CreateSparseTree(_coefs,_fact) <-- CreateSparseTree(First(coefs),Rest(coefs),fact);10 # CreateSparseTree(_first,[],_fact) <-- [[first,fact]];20 # CreateSparseTree(_first,_coefs,_fact) <-- [[first,CreateSparseTree(First(coefs),Rest(coefs),fact)]];10 # SparseTreeMap(_op,_depth,_list) <-- SparseTreeMap2(list,depth,[]);10 # SparseTreeMap2(_list,1,_coefs) <-- ForEach(item,list) { item[2] := ApplyFast(op,[Concat(coefs,[item[1]]),item[2]]); };20 # SparseTreeMap2(_list,_depth,_coefs) <-- ForEach(item,list) { SparseTreeMap2(item[2],AddN(depth,-1),Concat(coefs,[item[1]])); };UnFence(\"SparseTreeMap\", 3);{Local(fn);fn:=ToString(SparseTreeMap2);`UnFence(@fn,3);};10 # SparseTreeScan(_op,_depth,_list) <-- SparseTreeScan2(list,depth,[]);10 # SparseTreeScan2(_list,0,_coefs) <-- ApplyFast(op,[coefs,list]);20 # SparseTreeScan2(_list,_depth,_coefs) <-- ForEach(item,list) { SparseTreeScan2(item[2],AddN(depth,-1),Concat(coefs,[item[1]])); };UnFence(\"SparseTreeScan\", 3);{Local(fn);fn:=ToString(SparseTreeScan2);`UnFence(@fn,3);};5 # AddSparseTrees(0,_x,_y) <-- x+y;10 # AddSparseTrees(_depth,_x,_y) <--{ Local(i,t1,t2,inspt); Assign(t1,x); Assign(i,1); Assign(t2,y); Assign(inspt,[]); While(t1 !=? [] And? t2 !=? []) { Muaddterm(First(t1),First(t2)); }; While(t2 !=? []) { Assign(x,DestructiveAppend(x,First(t2))); Assign(t2,Rest(t2)); }; While(inspt !=? []) { Assign(i,First(inspt)); Assign(x,DestructiveInsert(x,i[2],i[1])); Assign(inspt,Rest(inspt)); }; x;};10 # Muaddterm([_pow,_list1],[_pow,_list2]) <--{ If(depth=?1) { t1[1][2] := list1+list2; } Else { t1[1][2] := AddSparseTrees(AddN(depth,-1),list1,list2);}; Assign(t2,Rest(t2));};20 # Muaddterm(_h1,_h2)::(h1[1]<?h2[1]) <--{ Assign(inspt,[h2,i]~inspt); Assign(t2,Rest(t2));};30 # Muaddterm(_h1,_h2)<--{ Assign(t1,Rest(t1)); Assign(i,AddN(i,1));};{Local(fn);fn:=ToString(Muaddterm);`UnFence(@fn,2);};5 # MultiplyAddSparseTrees(0,_x,_y,[],_fact) <-- x+fact*y;10 # MultiplyAddSparseTrees(_depth,_x,_y,_coefs,_fact) <--{ Local(i,t1,t2,inspt,term); Assign(t1,x); Assign(i,1); Assign(t2,y); Assign(inspt,[]); While(t1 !=? [] And? t2 !=? []) { MuMuaddterm(First(t1),First(t2),coefs); }; While(t2 !=? []) { Assign(term,First(t2)); Assign(x,DestructiveAppend(x,meradd(First(t2),coefs))); Assign(t2,Rest(t2)); }; While(inspt !=? []) { Assign(i,First(inspt)); Assign(x,DestructiveInsert(x,i[2],i[1])); Assign(inspt,Rest(inspt)); }; x;};10 # meradd([_ord,rest_List?],_coefs) <--{ Local(head); Assign(head,First(coefs)); Assign(coefs,Rest(coefs)); [ord+head,meraddmap(rest,coefs)];};20 # meradd([_ord,_rest],_coefs) <--{ [ord+First(coefs),rest*fact];};10 # meraddmap(list_List?,_coefs) <--{ Local(result); Assign(result,[]); ForEach(item,list) { DestructiveAppend(result,meradd(item,coefs)); }; result;};{Local(fn);fn:=ToString(meradd);`UnFence(@fn,2);};{Local(fn);fn:=ToString(meraddmap);`UnFence(@fn,2);};10 # MuMuaddterm([_pow1,_list1],[_pow2,_list2],_coefs)::(pow1=?pow2+coefs[1]) <--{ If(depth=?1) { t1[1][2] := list1+fact*list2; } Else { t1[1] := [pow1,MultiplyAddSparseTrees(AddN(depth,-1),list1,list2,Rest(coefs),fact)]; }; Assign(t2,Rest(t2));};20 # MuMuaddterm(_h1,_h2,_coefs)::(h1[1]<?h2[1]+coefs[1]) <--{ Assign(inspt,[meradd(First(t2),coefs),i]~inspt); Assign(t2,Rest(t2));};30 # MuMuaddterm(_h1,_h2,_coefs)<--{ Assign(t1,Rest(t1)); Assign(i,AddN(i,1));};{Local(fn);fn:=ToString(MuMuaddterm);`UnFence(@fn,3);};}; ";
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
        scriptString[1] = "BracketRational(r,eps):={ Local(n,cflist, r1, r2); cflist := ContFracList(r); n:=2; r1 := ContFracEval(Take(cflist,n)); r2 := -r1;  While (n<?Length(cflist) And? ( Abs(NM(Eval(r2-r1))) >? Abs(NM(Eval(eps*r)) ) ) ) { r2 := r1; n++; r1 := ContFracEval(Take(cflist,n)); };   Decide( n=?Length(cflist), [],  Decide(NM(Eval(r-r1))>?0, [r1, r2],  [r2, r1] ) );};";
        scriptMap.put("BracketRational",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ContFrac(_n) <-- ContFrac(n, 6);50 # ContFrac(_n,_depth) <-- ContFracEval(ContFracList(n, depth), rest);40 # ContFrac(n_CanBeUni,_depth)::(Length(VarList(n)) =? 1) <--{ ContFracDoPoly(n,depth,VarList(n)[1]);};5 # ContFracDoPoly(_exp,0,_var) <-- rest;5 # ContFracDoPoly(0,0,_var) <-- rest;10 # ContFracDoPoly(_exp,_depth,_var) <--{ Local(content,exp2,first,second); first:=Coef(exp,var,0); exp:=exp-first; content:=Content(exp); exp2:=DivPoly(1,PrimitivePart(exp),var,5+3*depth)-1; second:=Coef(exp2,0); exp2 := exp2 - second; first+content/((1+second)+ContFracDoPoly(exp2,depth-1,var));};";
        scriptMap.put("ContFrac",scriptString);
        scriptMap.put("ContFracDoPoly",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ContFracEval([], _rest) <-- rest;10 # ContFracEval([[_n, _m]], _rest) <-- n+m+rest;15 # ContFracEval([_n], _rest) <-- n+rest;20 # ContFracEval(list_List?, _rest)::(List?(First(list))) <-- First(First(list)) + Rest(First(list)) / ContFracEval(Rest(list), rest);30 # ContFracEval(list_List?, _rest) <-- First(list) + 1 / ContFracEval(Rest(list), rest);ContFracEval(list_List?) <-- ContFracEval(list, 0);";
        scriptMap.put("ContFracEval",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ContFracList(_n) <-- ContFracList(n, Infinity);10 # ContFracList(_n, _depth)::(depth <=? 0) <-- [];20 # ContFracList(n_Integer?, _depth) <-- [n];30 # ContFracList(n_Number?, _depth)::NumericMode?() <-- NonNM(ContFracList(Rationalize(n), depth));40 # ContFracList(n_Number?, _depth) <-- ContFracList(Rationalize(n), depth);35 # ContFracList((n_NegativeInteger?) / (m_Integer?), _depth) <-- Push( ContFracList(m/Modulo(n,m), depth-1) , Quotient(n,m)-1);40 # ContFracList((n_Integer?) / (m_Integer?), _depth) <-- Push( ContFracList(m/Modulo(n,m), depth-1) , Quotient(n,m));";
        scriptMap.put("ContFracList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Decimal( n_Integer? ) <-- [n,[0]];10 # Decimal( (n_PositiveInteger?) / (d_PositiveInteger?) ) <--{ Local(result,rev,first,period,repeat,static); result:=[Quotient(n,d)]; Decimal(result,Modulo(n,d),d,350); rev:=DecimalFindPeriod(result); first:=rev[1]; period:=rev[2]; repeat:=result[first .. (first+period-1)]; static:=result[1 .. (first-1)]; DestructiveAppend(static,repeat);};20 # Decimal(_n/_m)::((n/m)<?0) <-- \"-\"~Decimal(-n/m);10 # Decimal(_result , _n , _d,_count ) <--{ While(count>?0) { DestructiveAppend(result,Quotient(10*n,d)); n:=Modulo(10*n,d); count--; };};DecimalFindPeriod(_list) <--{ Local(period,nr,reversed,first,i); reversed:=Rest(DestructiveReverse(FlatCopy(Rest(list)))); nr:=Length(reversed)>>1; period:=1; first:=reversed[1]; For(i:=1,i<?nr,i++) { Decide(reversed[i+1] =? first And? DecimalMatches(reversed,i), { period:=i; i:=nr; } ); }; first:=Length(list)-period; While(first>?1 And? list[first] =? list[first+period]) first--; first++; [first,period];};DecimalMatches(_reversed,_period) <--{ Local(nr,matches,first); nr:=0; matches:=True; first:=1; While((nr<?100) And? matches) { matches := (matches And? (reversed[first .. (first+period-1)] =? reversed[(first+period) .. (first+2*period-1)])); first:=first+period; nr:=nr+period; }; matches;};";
        scriptMap.put("Decimal",scriptString);
        scriptMap.put("DecimalFindPeriod",scriptString);
        scriptMap.put("DecimalMatches",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1 # FreeOf?([],_expr) <-- True;2 # FreeOf?(var_List?, _expr) <-- And?(FreeOf?(First(var),expr), FreeOf?(Rest(var),expr));4 # FreeOf?(_var,[]) <-- True;5 # FreeOf?(_var,expr_List?) <-- And?(FreeOf?(var,First(expr)), FreeOf?(var,Rest(expr)));10 # FreeOf?(_expr,_expr) <-- False;11 # FreeOf?(_var,expr_Function?) <-- FreeOf?(var,Rest(FunctionToList(expr)));12 # FreeOf?(_var,_expr) <-- True;";
        scriptMap.put("FreeOf?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "GuessRational(_x) <-- GuessRational(x, Floor(1/2*BuiltinPrecisionGet()));GuessRational(x_RationalOrNumber?, prec_Integer?) <-- { Local(denomestimate, cf, i); denomestimate := 1; cf := ContFracList(x); For(i:=2, i<=?Length(cf) And? denomestimate <? 10^prec, i++) {  denomestimate := denomestimate * Decide( cf[i] =? 1, Decide( i+2<=?Length(cf),  RoundTo(NM(Eval(cf[i]+1/(cf[i+1]+1/cf[i+2]))), 3),  RoundTo(NM(Eval(cf[i]+1/cf[i+1])), 3) ),  cf[i] ); }; Decide(denomestimate <? 10^prec,  i--  ); i--;   ContFracEval(Take(cf, i));};";
        scriptMap.put("GuessRational",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # NearRational(_x) <-- NearRational(x, Floor(1/2*BuiltinPrecisionGet()));15 # NearRational(x_RationalOrNumber?, prec_Integer?) <-- { Local(x1, x2, i, oldprec); oldprec := BuiltinPrecisionGet(); BuiltinPrecisionSet(prec + 8);  x1 := ContFracList(NM(Eval(x+10^(-prec)))); x2 := ContFracList(NM(Eval(x-10^(-prec))));     For (i:=1, i<=?Length(x1) And? i<=?Length(x2) And? x1[i]=?x2[i], i++ ) True; Decide( i>?Length(x1),  x1:=x2, Decide( i>?Length(x2),  True,   x1[i]:=Minimum(x1[i],x2[i]) ) );    Decide(i>?Length(x1),i:=Length(x1)); x1[i] := x1[i] + Decide(i=?Length(x1), 0, 1); BuiltinPrecisionSet(oldprec); ContFracEval(Take(x1, i));};20 # NearRational(_z, prec_Integer?):: (And?(Im(z)!=?0,RationalOrNumber?(Im(z)),RationalOrNumber?(Re(z)))) <--{ Local(rr,ii); rr := Re(z); ii := Im(z); Complex( NearRational(rr,prec), NearRational(ii,prec) );};";
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
        scriptString[1] = "ReversePoly(_f,_g,_var,_newvar,_degree) <--{ Local(orig,origg,G,V,W,U,n,initval,firstder,j,k,newsum); orig:=MakeUni(f,var); origg:=MakeUni(g,var); initval:=Coef(orig,0); firstder:=Coef(orig,1); V:=Coef(orig,1 .. Degree(orig)); V:=Concat(V,FillList(0,degree)); G:=Coef(origg,1 .. Degree(origg)); G:=Concat(G,FillList(0,degree)); W:=FillList(0,Length(V)+2); W[1]:=G[1]/firstder; U:=FillList(0,Length(V)+2); U[1]:=1/firstder; n:=1; While(n<?degree-1) { n++; For(k:=0,k<?n-1,k++) { newsum:=U[k+1]; For(j:=2,j<=?k+1,j++) { newsum:=newsum-U[k+2-j]*V[j]; }; U[k+1]:=newsum/firstder; }; newsum:=0; For(k:=2,k<=?n,k++) { newsum:=newsum - k*U[n+1-k]*V[k]; }; U[n]:=newsum/firstder; newsum:=0; For(k:=1,k<=?n,k++) { newsum:=newsum + k*U[n+1-k]*G[k]/n; }; W[n]:=newsum; }; DestructiveInsert(W,1,Coef(origg,0)); Substitute(newvar,newvar-initval) NormalForm(UniVariate(newvar,0,W));};";
        scriptMap.put("ReversePoly",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Space() := WriteN(\" \",1);Space(n):= WriteN(\" \",n);";
        scriptMap.put("Space",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "UniqueConstant() <--{ Local(result); result := ToString(LocalSymbols(C)(C)); ToAtom(StringMidGet(2,Length(result)-1,result));};";
        scriptMap.put("UniqueConstant",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TemplateFunction(\"WithValue\",[var,val,expr]){ Decide(List?(var), ApplyFast(\"MacroLocal\",var), MacroLocal(var) ); ApplyFast(\":=\",[var,val]); Eval(expr);};";
        scriptMap.put("WithValue",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "WriteN(string,n) :={ Local(i); For(i:=1,i<=?n,i++) WriteString(string); True;};";
        scriptMap.put("WriteN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # IntLog(_x, _base):: (base<=?1) <-- Undefined;20 # IntLog(_x, _base) <--{ Local(result, step, oldstep, factor, oldfactor); result := 0; oldstep := step := 1; oldfactor := factor := base;  While (x >=? factor) { oldfactor := factor; factor := factor*factor; oldstep := step; step := step*2; }; Decide(x >=? base, { step := oldstep; result := step; x := Quotient(x, oldfactor); }, step := 0 );  While (step >? 0 And? x !=? 1) { step := Quotient(step,2);  factor := base^step; Decide( x >=? factor, { x:=Quotient(x, factor); result := result + step; } ); }; result;};";
        scriptMap.put("IntLog",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1# NextPrime(_i) <--{ Until(Prime?(i)) i := NextPseudoPrime(i); i;};";
        scriptMap.put("NextPrime",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1# NextPseudoPrime(i_Integer?)::(i<=?1) <-- 2;2# NextPseudoPrime(2) <-- 3;3# NextPseudoPrime(i_Odd?) <--{  i := i+2; Decide(Modulo(i,3)=?0, i:=i+2, i);};4# NextPseudoPrime(i_Even?) <-- NextPseudoPrime(i-1);";
        scriptMap.put("NextPseudoPrime",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(m,n,r, NthRootTable){NthRoot(m_NonNegativeInteger?,n_Integer?)::(n>?1) <--{ Local(r); r:=NthRootRestore(m,n); Decide(Length(r)=?0, { r:=NthRootCalc(m,n); NthRootSave(m,n,r); }); r;};Function(\"NthRootCalc\",[m,n]){ Local(i,j,f,r,in); Assign(i,2); Assign(j,Ceil(FastPower(m,NM(1.0/n))+1)); Assign(f,1); Assign(r,m);     While(LessThan?(i,j)) { Assign(in,PowerN(i,n)); While(Equal?(ModuloN(r,in),0)) { Assign(f,MultiplyN(f,i)); Assign(r,QuotientN(r,in)); }; While(Equal?(ModuloN(r,i),0))  Assign(r,QuotientN(r,i));   Assign(i,NextPseudoPrime(i)); Assign(j,Ceil(FastPower(r,NM(1.0/n))+1)); };  List(f,QuotientN(m,PowerN(f,n))); };Function(\"NthRootList\",[n]){ Decide(Length(NthRootTable)>?0, { Local(p,xx); p:=Select(NthRootTable, [[xx],First(xx)=?n]); Decide(Length(p)=?1,Rest(p[1]),List()); }, List());};Function(\"NthRootRestore\",[m,n]){ Local(p); p:=NthRootList(n); Decide(Length(p)>?0, { Local(r,xx); r:=Select(p, [[xx],First(xx)=?m]); Decide(Length(r)=?1,First(Rest(r[1])),List()); }, List());};Function(\"NthRootSave\",[m,n,r]){ Local(p); p:=NthRootList(n); Decide(Length(p)=?0,  DestructiveInsert(NthRootTable,1,List(n,List(m,r))), { Local(rr,xx); rr:=Select(p, [[xx],First(xx)=?m]); Decide(Length(rr)=?0, {  DestructiveAppend(p,List(m,r)); },  False); });};Function(\"NthRootClear\",[]) SetGlobalLazyVariable(NthRootTable,List());NthRootClear();}; ";
        scriptMap.put("NthRoot",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # NumberToRep( N_Number? ) <--{  Local(oldPrec,sgn,assoc,typ,val,prec,rep); oldPrec := BuiltinPrecisionGet(); BuiltinPrecisionSet(300);   sgn := Sign(N);  assoc := DumpNumber(Abs(N));  typ := Assoc(\"type\",assoc)[2];  Decide( typ =? \"BigDecimal\", { rep := [ sgn*Assoc(\"unscaledValue\",assoc)[2], Assoc(\"precision\", assoc)[2], Assoc(\"scale\", assoc)[2]  ]; }, { Local(val,prec); val := Assoc(\"value\",assoc)[2]; prec := Length(ToString(val)); rep := [ sgn*val, prec ]; } );  BuiltinPrecisionSet(oldPrec); rep;};12 # NumberToRep( N_Complex? ) <-- { Decide(Zero?(Re(N)), [NumberToRep(0.0),NumberToRep(Im(N))], [NumberToRep(Re(N)),NumberToRep(Im(N))] );};";
        scriptMap.put("NumberToRep",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # PrimePower?(n_Prime?) <-- True;10 # PrimePower?(0) <-- False;10 # PrimePower?(1) <-- False;20 # PrimePower?(n_PositiveInteger?) <-- (GetPrimePower(n)[2] >? 1);";
        scriptMap.put("PrimePower?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "2 # Prime?(_n)::(Not? Integer?(n) Or? n<=?1) <-- False;3 # Prime?(n_Integer?)::(n<=?FastIsPrime(0)) <-- SmallPrime?(n);5 # Prime?(n_PositiveInteger?)::(n >? 4 And? Modulo(n^2-1,24)!=?0) <-- False;10 # Prime?(n_PositiveInteger?) <-- RabinMiller(n);";
        scriptMap.put("Prime?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(p, q){  ProductPrimesTo257() := 2*3* { Decide( Integer?(p), p, p := Product(Select( 5 .. 257, [[q], Modulo(q^2,24)=?1 And? SmallPrime?(q)])) ); };};";
        scriptMap.put("ProductPrimesTo257",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Rationalize(aNumber_List?) <-- Rationalize /@ aNumber;20 # Rationalize( _aNumber ) <--{ Local(result,n,d); result:=SubstituteApply(aNumber,[[x],Number?(x) And? Not?(Integer?(x))],\"RationalizeNumber\"); Decide(InVerboseMode(),Tell(\"\",result)); Decide(Length(VarList(aNumber))=?0, { n:=Numerator(result); Decide(Type(n)=?\"Numerator\",n:=result); d:=Denominator(result); Decide(Type(d)=?\"Denominator\",d:=1); result := n*(1/d); } ); result;};";
        scriptMap.put("Rationalize",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"RationalizeNumber\",[x]){ Check(Number?(x), \"Argument\", \"RationalizeNumber: Error: \" ~ (PipeToString()Write(x)) ~\" is not a number\"); Local(n,i,bip,m); n := 1; i := 0; bip := BuiltinPrecisionGet();  While(i<=?bip And? Not?(FloatIsInt?(x))) { n := n*10;  x := x*10; i := i+1; }; m := Floor(x+0.5); (m/n);};";
        scriptMap.put("RationalizeNumber",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RepToNumber( rep_ListOfLists? ) <--{  RepToNumber(rep[1])+I*RepToNumber(rep[2]);};12 # RepToNumber( rep_List? ) <--{  Local(bigInt,precision,scale,strBI,sgn,index,ans); Local(first,secnd,third,LS,numStr); precision := rep[2]; scale := 0; bigInt := rep[1]; precision := rep[2]; sgn := Sign(bigInt); Decide( Length(rep) >? 2, scale := rep[3] ); strBI := ToString(Abs(bigInt)); LS := Length(strBI);  Decide( Length(rep)=?2, { numStr := strBI; }, { index := precision-scale; first := strBI[1]; secnd := StringMidGet(2,LS-1,strBI);  third := ToString(index-1);  If( index >? 0 ) { If( index <? precision ) {  numStr := ConcatStrings(first,\".\",secnd,\"E\",third); } Else If( index >=? precision ) {  numStr := ConcatStrings(first,\".\",secnd,\"E+\",third); }; } Else If( index <? 0 ) {  numStr := ConcatStrings(first,\".\",secnd,\"E\",third); } Else {  first := \"0.\" ;  secnd := strBI; numStr := ConcatStrings(first,secnd); }; } ); ans := sgn * ToAtom(numStr);  ans;};";
        scriptMap.put("RepToNumber",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RoundToPlace( N_Decimal?, place_Integer? ) <--{  Local(rep,sgn,oldInt,oldPrec,oldScale,oldPlaces,strOInt,LS); Local(newInt,newScale,newRep,ans); sgn := Sign(N); rep := NumberToRep( Abs(N) ); oldInt := rep[1]; oldPrec := rep[2]; oldScale := rep[3]; oldPlaces:= oldPrec - oldScale; strOInt := ToString(oldInt); LS := Length(strOInt);           Decide(oldPlaces+place>?0, ans := RoundToPrecision(N,oldPlaces+place), ans := 0. ); ans;};15 # RoundToPlace( N_Integer?, place_Integer? )::(place <=? 0) <--{  Local(oldRep,oldPrec,decN,newDecN,ans); oldRep := NumberToRep(N); oldPrec := oldRep[2]; decN := N*1.0; newDecN := RoundToPlace( decN, place );     Decide( place <=? oldPrec,  ans := Round(newDecN), ans := Round(newDecN*10^(place-oldPrec)) ); ans;};20 # RoundToPlace( N_Complex?, place_Integer? )::(Not? Integer?(N)) <--{  Local(rr,ii); rr := Re(N); ii := Im(N); Complex(RoundToPlace(rr,place),RoundToPlace(ii,place));};";
        scriptMap.put("RoundToPlace",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RoundToPrecision( N_Decimal?, newPrec_PositiveInteger? ) <--{  Local(rep,sgn,oldInt,oldPrec,oldScale,strOInt,LS,BIP0); Local(newInt,newScale,newRep,ans); BIP0 := BuiltinPrecisionGet(); sgn := Sign(N); rep := NumberToRep( Decide(sgn<?0,-N,N) ); oldInt := rep[1]; oldPrec := rep[2]; oldScale := rep[3]; Decide( newPrec >? oldPrec, BuiltinPrecisionSet(newPrec) ); strOInt := ToString(oldInt); LS := Length(strOInt);           Local(first,secnd,rem,ad); If( newPrec =? oldPrec ) { ans := N; } Else If( newPrec <? oldPrec ) { first := StringMidGet(1, newPrec, strOInt);  secnd := StringMidGet(newPrec+1, LS-newPrec, strOInt); rem := ToAtom(ConcatStrings(\".\",secnd)); ad := Decide(rem>=?0.5, 1, 0 ); newInt := sgn * ( ToAtom(first) + ad ); newScale := oldScale - ( oldPrec - newPrec );  newRep := [newInt,newPrec,newScale]; ans := RepToNumber(newRep);         } Else {  Local(diffPrec); diffPrec := oldPrec - newPrec; newInt := sgn * ToAtom(strOInt) * 10^(-diffPrec) ; newScale := oldScale - diffPrec; newRep := [newInt,newPrec,newScale];  ans := RepToNumber(newRep); }; BuiltinPrecisionSet(BIP0); ans;};15 # RoundToPrecision( N_Integer?, newPrec_PositiveInteger? ) <--{  Local(oldRep,oldPrec,decN,newDecN,ans); oldRep := NumberToRep(N); oldPrec := oldRep[2]; decN := N*1.0; newDecN := RoundToPrecision( decN, newPrec );     Decide( newPrec <=? oldPrec,  ans := Round(newDecN), ans := Round(newDecN*10^(newPrec-oldPrec)) ); ans;};20 # RoundToPrecision( N_Complex?, newPrec_PositiveInteger? ) <--{  Local(rr,ii); rr := Re(N); ii := Im(N); Complex(RoundToPrecision(rr,newPrec),RoundToPrecision(ii,newPrec));};";
        scriptMap.put("RoundToPrecision",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "2 # SmallPrime?(0) <-- False;3 # SmallPrime?(n_Integer?) <-- (FastIsPrime(n)>?0);";
        scriptMap.put("SmallPrime?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # GaussianGcd(n_GaussianInteger?,m_GaussianInteger?) <--{ Decide(NM(Abs(m))=?0,n, GaussianGcd(m,n - m*Round(n/m) ) );};";
        scriptMap.put("GaussianGcd",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # GaussianInteger?(x_List?) <-- False;10 # GaussianInteger?(x_Complex?) <-- (Integer?(Re(x)) And? Integer?(Im(x)));15 # GaussianInteger?(_x) <-- False;";
        scriptMap.put("GaussianInteger?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"::\",[a,b]);RulebaseHoldArguments(\"DefinePattern\",[leftOperand, rightOperand, rulePrecedence, postPredicate]);RuleHoldArguments(\"DefinePattern\",4,9,Equal?(Type(leftOperand),\"::\")){ DefinePattern(leftOperand[1], rightOperand, rulePrecedence, leftOperand[2]);};RuleHoldArguments(\"DefinePattern\",4,10,True){ Local(patternFlat,patternVariables, pattern, patternOperator, arg, arity);  Assign(patternFlat, FunctionToList(leftOperand));   Assign(patternVariables, Rest(patternFlat));   Assign(patternOperator,ToString(First(patternFlat)));   Assign(arity,Length(patternVariables));      Decide(Not?(RulebaseDefined(patternOperator,arity)), { RulebaseEvaluateArguments(patternOperator,MakeVector(arg,arity)); } );  Assign(pattern,PatternCreate(patternVariables,postPredicate));  RulePatternEvaluateArguments(patternOperator,arity,rulePrecedence, pattern)rightOperand;  True;};";
        scriptMap.put("DefinePattern",scriptString);
        scriptMap.put("_",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"MakeVector\",[vec,dimension]);RuleHoldArguments(\"MakeVector\",2,1,True){ Local(res,i); res:=[]; i:=1; Assign(dimension,AddN(dimension,1)); While(LessThan?(i,dimension)) { DestructiveInsert(res,1,ToAtom(ConcatStrings(ToString(vec),ToString(i)))); Assign(i,AddN(i,1)); }; DestructiveReverse(res);};";
        scriptMap.put("MakeVector",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"<--\",[leftOperand,rightOperand]);RuleHoldArguments(\"<--\",2,1,Equal?(Type(leftOperand),\"#\")){ DefinePattern(leftOperand[2],rightOperand,leftOperand[1],True);};RuleHoldArguments(\"<--\",2,2,Function?(leftOperand)){ DefinePattern(leftOperand,rightOperand,0,True);};HoldArgument(\"<--\",leftOperand);HoldArgument(\"<--\",rightOperand);";
        scriptMap.put("<--",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "OptionsListToHash(list) :={ Local(item, result); result := []; ForEach(item, list) Decide( Function?(item) And? (Type(item) =? \":\" ) And? Atom?(item[1]), result[ToString(item[1])] := Decide( Atom?(item[2]) And? Not? Number?(item[2]) And? Not? String?(item[2]), ToString(item[2]), item[2] ), Echo([\"OptionsListToHash: Error: item \", item, \" is not of the format a: b.\"]) );  result;};HoldArgumentNumber(\"OptionsListToHash\", 1, 1);";
        scriptMap.put("OptionsListToHash",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RemoveRepeated([]) <-- [];10 # RemoveRepeated([_x]) <-- [x];20 # RemoveRepeated(list_List?) <-- { Local(i, done); done := False; For(i:=0, Not? done, i++) { While(i<?Length(list) And? list[i]=?list[i+1]) DestructiveDelete(list, i); Decide(i=?Length(list), done := True); }; list;};";
        scriptMap.put("RemoveRepeated",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "WriteDataItem(tuple_List?, _optionshash) <--{ Local(item); Decide(  NumericList?(tuple), ForEach(item,tuple) { Write(item); Space(); } ); NewLine();};";
        scriptMap.put("WriteDataItem",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(options){ options := [ [\"default\", \"data\"], [\"data\", \"Plot2DData\"], [\"java\", \"Plot2DJava\"], [\"geogebra\", \"Plot2DGeoGebra\"], [\"jfreechart\", \"Plot2DJFreeChart\"],]; Plot2DOutputs() := options;};Plot2DData(values_List?, _optionsHash) <-- values;Plot2DJava(values_List?, _optionsHash) <--{ Local(result,count); count := 0; result:=\"$plot2d:\"; result := result~\" pensize 2.0 \"; ForEach(function,values) { result := result~ColorForGraphNr(count); count++; result:=result~\" lines2d \"~ToString(Length(function)); function:=Select(function, Lambda([item],item[2] !=? Undefined)); ForEach(item,function) { result := result~\" \"~ToString(item[1])~\" \"~ToString(item[2])~\" \"; }; }; WriteString(result~\"$\"); True;};10 # ColorForGraphNr(0) <-- \" pencolor 64 64 128 \";10 # ColorForGraphNr(1) <-- \" pencolor 128 64 64 \";10 # ColorForGraphNr(2) <-- \" pencolor 64 128 64 \";20 # ColorForGraphNr(_count) <-- ColorForGraphNr(Modulo(count,3));Plot2DGeogebra(values_List?, _optionsHash) <--{ Local(result,count); count := 0; result:=\"\"; ForEach(function,values) { function:=Select(function, Lambda([item],item[2] !=? Undefined)); ForEach(item,function) { result := result~\"(\"~ToString(item[1])~\",\"~ToString(item[2])~\")\"~Nl(); }; }; WriteString(result); True;};Plot2DJFreeChart(values_List?, _optionsHash) <--{ Local(rangeList, domainList, function, allProcessedFunctionData, lineChartCallListForm);     ForEach(name, [\"xrange\", \"xname\", \"yname\", \"output\", \"precision\", \"points\", \"depth\"]) { AssocDelete(optionsHash, name); };     allProcessedFunctionData := [];  ForEach(function,values) { rangeList := [];  domainList := [];  function := Select(function, Lambda([item],item[2] !=? Undefined));  ForEach(item,function) { rangeList := Append(rangeList, item[1]);  domainList := Append(domainList, item[2]); };  allProcessedFunctionData := Append(allProcessedFunctionData, rangeList); allProcessedFunctionData := Append(allProcessedFunctionData, domainList);  };   lineChartCallListForm := [LineChart, allProcessedFunctionData ];     ForEach(key, AssocIndices(optionsHash)) { lineChartCallListForm := Append(lineChartCallListForm, Apply(\":\", [key, optionsHash[key]])); };     Eval(ListToFunction(lineChartCallListForm)); };";
        scriptMap.put("Plot2DOutputs",scriptString);
        scriptMap.put("Plot2DData",scriptString);
        scriptMap.put("Plot2DJava",scriptString);
        scriptMap.put("ColorForGraphNr",scriptString);
        scriptMap.put("Plot2DGeogebra",scriptString);
        scriptMap.put("Plot2DJFreeChart",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(var, func, range, option, optionslist, delta, optionshash, c, fc, allvalues, dummy){Function() Plot2D(func);Function() Plot2D(func, range);Function() Plot2D(func, range, options, ...);1 # Plot2D(_func) <-- (\"Plot2D\" @ [func, -5->5]);2 # Plot2D(_func, _range) <-- (\"Plot2D\" @ [func, range, []]);3 # Plot2D(_func, _range, option_Function?):: (Type(option) =? \":\" ) <-- (\"Plot2D\" @ [func, range, [option]]);5 # Plot2D(_func, _range, optionslist_List?)::(Not? List?(func)) <-- (\"Plot2D\" @ [[func], range, optionslist]);4 # Plot2D(funclist_List?, _range, optionslist_List?) <--{ Local(var, func, delta, optionshash, c, fc, allvalues, dummy); allvalues := []; optionshash := \"OptionsListToHash\" @ [optionslist];    optionshash[\"xname\"] := \"\";  optionshash[\"yname\"] := [];    Decide( Type(range) =? \"->\",  range := NM(Eval([range[1], range[2]])) );  Decide( optionshash[\"points\"] =? Empty, optionshash[\"points\"] := 23 ); Decide( optionshash[\"depth\"] =? Empty, optionshash[\"depth\"] := 5 ); Decide( optionshash[\"precision\"] =? Empty, optionshash[\"precision\"] := 0.0001 ); Decide( optionshash[\"output\"] =? Empty Or? String?(optionshash[\"output\"]) And? Plot2DOutputs()[optionshash[\"output\"]] =? Empty, optionshash[\"output\"] := Plot2DOutputs()[\"default\"] );  Decide( optionshash[\"output\"] =? \"datafile\" And? optionshash[\"filename\"] =? Empty, optionshash[\"filename\"] := \"output.data\" );   optionshash[\"points\"] := NM(Eval(Quotient(optionshash[\"points\"]+3, 4)));   optionshash[\"precision\"] := NM(Eval(optionshash[\"precision\"]));   optionshash[\"xrange\"] := [range[1], range[2]];   delta := NM(Eval( (range[2] - range[1]) / (optionshash[\"points\"]) ));  Check(Number?(range[1]) And? Number?(range[2]) And? Number?(optionshash[\"points\"]) And? Number?(optionshash[\"precision\"]), \"Argument\", \"Plot2D: Error: plotting range \" ~(PipeToString()Write(range)) ~\" and/or the number of points \" ~(PipeToString()Write(optionshash[\"points\"])) ~\" and/or precision \" ~(PipeToString()Write(optionshash[\"precision\"])) ~\" is not numeric\" );  ForEach(func, funclist) {  var := VarList(func);  Check(Length(var)<=?1, \"Argument\", \"Plot2D: Error: expression is not a function of one variable: \" ~(PipeToString()Write(func)) );  Decide(Length(var)=?0, var:=[dummy]);  Decide( optionshash[\"xname\"] =? \"\", optionshash[\"xname\"] := ToString(VarList(var)[1]) );  DestructiveAppend(optionshash[\"yname\"], PipeToString()Write(func));  c := range[1]; fc := NM(Eval(Apply([var, func], [c]))); Check(Number?(fc) Or? fc=?Infinity Or? fc=? -Infinity Or? fc=?Undefined, \"Argument\",  \"Plot2D: Error: cannot evaluate function \" ~(PipeToString()Write(func)) ~\" at point \" ~(PipeToString()Write(c)) ~\" to a number, instead got \" ~(PipeToString()Write(fc)) ~\"\" );  DestructiveAppend(allvalues, Plot2Dgetdata(func, var, c, fc, delta, optionshash) );  Decide(InVerboseMode(), Echo([\"Plot2D: using \", Length(allvalues[Length(allvalues)]), \" points for function \", func]), True); };   Plot2DOutputs()[optionshash[\"output\"]] @ [allvalues, optionshash];};HoldArgumentNumber(\"Plot2D\", 2, 2);HoldArgumentNumber(\"Plot2D\", 3, 2);HoldArgumentNumber(\"Plot2D\", 3, 3);Plot2Dgetdata(_func, _var, _xinit, _yinit, _deltax, _optionshash) <--{ Local(i, a, fa, b, fb, c, fc, result);  result := [ [c,fc] := [xinit, yinit] ]; For(i:=0, i<?optionshash[\"points\"], i++) { [a,fa] := [c, fc];   [b, c] := NM(Eval([xinit + (i+1/2)*deltax, xinit + (i+1)*deltax]));  [fb, fc] := NM(Eval(MapSingle([var, func], [b, c]))); result := Concat(result, Rest(Plot2Dadaptive(func, var, [a,b,c], [fa, fb, fc], optionshash[\"depth\"],  optionshash[\"precision\"]*optionshash[\"points\"] ))); };  result;};Plot2Dadaptive(_func, _var, [_a,_b,_c], [_fa, _fb, _fc], _depth, _epsilon) <--{ Local(a1, b1, fa1, fb1); a1 := NM(Eval((a+b)/2)); b1 := NM(Eval((b+c)/2)); [fa1, fb1] := NM(Eval(MapSingle([var, func], [a1, b1]))); Decide( depth<=?0 Or? (  signchange(fa, fa1, fb) + signchange(fa1, fb, fb1) + signchange(fb, fb1, fc) <=? 2 And?  NM(Eval(Abs( (fa-5*fa1+9*fb-7*fb1+2*fc)/24 ) ))  <=? NM(Eval( epsilon*(   (5*fb+8*fb1-fc)/12  - Minimum([fa,fa1,fb,fb1,fc]) ) ) ) ),  [[a,fa], [a1,fa1], [b,fb], [b1,fb1], [c,fc]],  Concat(  Plot2Dadaptive(func, var, [a, a1, b], [fa, fa1, fb], depth-1, epsilon*2),  Rest(Plot2Dadaptive(func, var, [b, b1, c], [fb, fb1, fc], depth-1, epsilon*2)) ) );};}; ";
        scriptMap.put("Plot2D",scriptString);
        scriptMap.put("Plot2Dgetdata",scriptString);
        scriptMap.put("Plot2Dadaptive",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Plot3DSoutputs() := [ [\"default\", \"data\"], [\"data\", \"Plot3DSdata\"],];Plot3DSdata(values_List?, _optionshash) <-- values;";
        scriptMap.put("Plot3DSoutputs",scriptString);
        scriptMap.put("Plot3DSdata",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " LocalSymbols(var, func, xrange, yrange, option, optionslist, xdelta, ydelta, optionshash, cx, cy, fc, allvalues, dummy){Function() Plot3DS(func);Function() Plot3DS(func, xrange, yrange);Function() Plot3DS(func, xrange, yrange, options, ...);1 # Plot3DS(_func) <-- (\"Plot3DS\" @ [func, -5->5, -5->5]);2 # Plot3DS(_func, _xrange, _yrange) <-- (\"Plot3DS\" @ [func, xrange, yrange, []]);3 # Plot3DS(_func, _xrange, _yrange, option_Function?):: (Type(option) =? \":\" ) <-- (\"Plot3DS\" @ [func, xrange, yrange, [option]]);5 # Plot3DS(_func, _xrange, _yrange, optionslist_List?)::(Not? List?(func)) <-- (\"Plot3DS\" @ [[func], xrange, yrange, optionslist]);4 # Plot3DS(funclist_List?, _xrange, _yrange, optionslist_List?) <--{ Local(var, func, xdelta, ydelta, optionshash, cx, cy, fc, allvalues, dummy);  allvalues := []; optionshash := \"OptionsListToHash\" @ [optionslist];  optionshash[\"xname\"] := \"\"; optionshash[\"yname\"] := \"\";  optionshash[\"zname\"] := [];   Decide( Type(xrange) =? \":\",  xrange := NM(Eval([xrange[1], xrange[2]])) ); Decide( Type(yrange) =? \":\",  yrange := NM(Eval([yrange[1], yrange[2]])) );  Decide( optionshash[\"points\"] =? Empty, optionshash[\"points\"] := 10  ); Decide( optionshash[\"xpoints\"] =? Empty, optionshash[\"xpoints\"] := optionshash[\"points\"] ); Decide( optionshash[\"ypoints\"] =? Empty, optionshash[\"ypoints\"] := optionshash[\"points\"] ); Decide( optionshash[\"depth\"] =? Empty, optionshash[\"depth\"] := 2 ); Decide( optionshash[\"precision\"] =? Empty, optionshash[\"precision\"] := 0.0001 ); Decide( optionshash[\"hidden\"] =? Empty Or? Not? Boolean?(optionshash[\"hidden\"]), optionshash[\"hidden\"] := True ); Decide( optionshash[\"output\"] =? Empty Or? String?(optionshash[\"output\"]) And? Plot3DSoutputs()[optionshash[\"output\"]] =? Empty, optionshash[\"output\"] := Plot3DSoutputs()[\"default\"] );  Decide( optionshash[\"output\"] =? \"datafile\" And? optionshash[\"filename\"] =? Empty, optionshash[\"filename\"] := \"output.data\" ); optionshash[\"used depth\"] := optionshash[\"depth\"];  optionshash[\"xpoints\"] := NM(Eval(Quotient(optionshash[\"xpoints\"]+1, 2))); optionshash[\"ypoints\"] := NM(Eval(Quotient(optionshash[\"ypoints\"]+1, 2)));  optionshash[\"precision\"] := NM(Eval(optionshash[\"precision\"]));  optionshash[\"xrange\"] := [xrange[1], xrange[2]]; optionshash[\"yrange\"] := [yrange[1], yrange[2]];  xdelta := NM(Eval( (xrange[2] - xrange[1]) / (optionshash[\"xpoints\"]) ) ); ydelta := NM(Eval( (yrange[2] - yrange[1]) / (optionshash[\"ypoints\"]) ) );  Check(NumericList?([xrange[1], xrange[2], optionshash[\"xpoints\"], optionshash[\"ypoints\"], optionshash[\"precision\"]]), \"Argument\",  \"Plot3DS: Error: plotting ranges \" ~(PipeToString()Write(xrange, yrange)) ~\" and/or the number of points \" ~(PipeToString()Write(optionshash[\"xpoints\"], optionshash[\"ypoints\"])) ~\" and/or precision \" ~(PipeToString()Write(optionshash[\"precision\"])) ~\" is not numeric\" );  ForEach(func, funclist) {  var := VarList(func);  Check(Length(var)<=?2, \"Argument\", \"Plot3DS: Error: expression is not a function of at most two variables: \" ~(PipeToString()Write(func)) );  Decide(Length(var)=?0, var:=[dummy, dummy]); Decide(Length(var)=?1, var:=[var[1], dummy]);  Decide( optionshash[\"xname\"] =? \"\", optionshash[\"xname\"] := ToString(var[1]) ); Decide( optionshash[\"yname\"] =? \"\", optionshash[\"yname\"] := ToString(var[2]) );  DestructiveAppend(optionshash[\"zname\"], PipeToString()Write(func));  cx := xrange[1]; cy := yrange[1]; fc := NM(Eval(Apply([var, func], [cx, cy]))); Check(Number?(fc) Or? fc=?Infinity Or? fc=? -Infinity Or? fc=?Undefined, \"Argument\",  \"Plot3DS: Error: cannot evaluate function \" ~(PipeToString()Write(func)) ~\" at point \" ~(PipeToString()Write(cx, cy)) ~\" to a number, instead got \" ~(PipeToString()Write(fc)) ~\"\" );  DestructiveAppend(allvalues, RemoveRepeated(HeapSort( Plot3DSgetdata(func, var, [cx, cy, fc], [xdelta, ydelta], optionshash), Hold([[x,y],x[1]<?y[1] Or? x[1] =? y[1] And? x[2] <=? y[2] ] ) )) ); Decide(InVerboseMode(), Echo([\"Plot3DS: using \", Length(allvalues[Length(allvalues)]), \" points for function \", func]), True); Decide(InVerboseMode(), Echo([\"Plot3DS: max. used \", 2^(optionshash[\"depth\"] - optionshash[\"used depth\"]), \"subdivisions for \", func]), True); };  Plot3DSoutputs()[optionshash[\"output\"]] @ [allvalues, optionshash];};HoldArgumentNumber(\"Plot3DS\", 3, 2);HoldArgumentNumber(\"Plot3DS\", 3, 3);HoldArgumentNumber(\"Plot3DS\", 4, 2);HoldArgumentNumber(\"Plot3DS\", 4, 3);HoldArgumentNumber(\"Plot3DS\", 4, 4);Plot3DSgetdata(_func, _var, _initvalues, _deltas, _optionshash) <--{ Local(i, j, xa, ya, fa, xb, yb, fb, result, rowcache);      rowcache := [initvalues]; For(i:=1, i<=?optionshash[\"ypoints\"], i++) { ya := NM(Eval(initvalues[2]+i*deltas[2])); DestructiveAppend(rowcache, [initvalues[1], ya, NM(Eval(Apply([var, func], [initvalues[1], ya])))]); }; result := rowcache;  For(i:=1, i<=?optionshash[\"xpoints\"], i++) {   xa := NM(Eval(initvalues[1]+i*deltas[1])); ya := initvalues[2]; fa := NM(Eval(Apply([var, func], [xa, ya]))); DestructiveAppend(result, [xa, ya, fa]);  For(j:=1, j<=?optionshash[\"ypoints\"], j++) {     yb := NM(Eval(initvalues[2] + j*deltas[2])); fb := NM(Eval(Apply([var, func], [xa, yb]))); result := Concat(result, Plot3DSadaptive(func, var, [rowcache[j][1], ya, xa, yb, rowcache[j][3], rowcache[j+1][3], fa, fb], optionshash[\"depth\"],  optionshash[\"precision\"] * optionshash[\"xpoints\"] * optionshash[\"ypoints\"], optionshash ));  rowcache[j] := [xa, ya, fa]; ya := yb; fa := fb; DestructiveAppend(result, [xa, ya, fa]); }; }; result;};10 # Plot3DSadaptive(_func, _var, _square, 0, _epsilon, _optionshash) <-- [];20 # Plot3DSadaptive(_func, _var, [_x1, _y1, _x2, _y2, _f11, _f12, _f21, _f22], _depth, _epsilon, _optionshash) <--{ Local(x3, y3, f13, f31, f33, f32, f23, result);  optionshash[\"used depth\"] := depth-1;  x3 := NM(Eval((x1+x2)/2)); y3 := NM(Eval((y1+y2)/2));   f13 := NM(Eval(Apply([var, func], [x1, y3]))); f31 := NM(Eval(Apply([var, func], [x3, y1]))); f33 := NM(Eval(Apply([var, func], [x3, y3]))); f32 := NM(Eval(Apply([var, func], [x3, y2]))); f23 := NM(Eval(Apply([var, func], [x2, y3]))); result := [[x1,y3,f13], [x3, y1, f31], [x3, y3, f33], [x3, y2, f32], [x2, y3, f23]]; Decide(  signchange(f11,f13,f12) + signchange(f13,f12,f32) + signchange(f12,f32,f22) <=? 2 And? signchange(f22,f23,f21) + signchange(f23,f21,f31) + signchange(f21,f31,f11) <=? 2 And?    NM(Eval(Abs( (f11-f23)/2-(f12-f21)/3+(f22-f13)/6+2*(f32-f33)/3 ))) <=? NM(Eval( epsilon*(   (f11 + f12 + f21 + f22)/12 + 2*f33/3 - Minimum([f11, f12, f21, f22, f13, f31, f33, f32, f23]) ) ) ) ,  result,  Concat(  result,  Plot3DSadaptive(func, var, [x1, y1, x3, y3, f11, f13, f31, f33], depth-1, epsilon*4, optionshash), Plot3DSadaptive(func, var, [x1, y3, x3, y2, f13, f12, f33, f32], depth-1, epsilon*4, optionshash), Plot3DSadaptive(func, var, [x3, y1, x2, y3, f31, f33, f21, f23], depth-1, epsilon*4, optionshash), Plot3DSadaptive(func, var, [x3, y3, x2, y2, f33, f32, f23, f22], depth-1, epsilon*4, optionshash) ) );};}; ";
        scriptMap.put("Plot3DS",scriptString);
        scriptMap.put("Plot3DSgetdata",scriptString);
        scriptMap.put("Plot3DSadaptive",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "signchange(x,y,z) :=Decide( Number?(x) And? Number?(y) And? Number?(z) And? Not? ( x>?y And? y<?z Or?   x<?y And? y>?z ), 0, 1); ";
        scriptMap.put("signchange",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # AllSatisfy?(pred_String?,lst_List?) <-- Apply(\"And?\",(MapSingle(pred,lst)));20 # AllSatisfy?(_pred,_lst) <-- False;";
        scriptMap.put("AllSatisfy?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "0 # BooleanType?(True) <-- True;0 # BooleanType?(False) <-- True;1 # BooleanType?(_anythingElse) <-- False;";
        scriptMap.put("BooleanType?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function (\"Boolean?\", [x]) (x=?True) Or? (x=?False) Or? Function?(x) And? Contains?([\"=?\", \">?\", \"<?\", \">=?\", \"<=?\", \"!=?\", \"And?\", \"Not?\", \"Or?\", \"Implies?\", \"Equivales?\"], Type(x));";
        scriptMap.put("Boolean?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Constant?(_n) <-- (VarList(n) =? []);";
        scriptMap.put("Constant?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Equation?(expr_Atom?) <-- False;12 # Equation?(_expr) <-- FunctionToList(expr)[1] =? == ;";
        scriptMap.put("Equation?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "EvenFunction?(f,x):= (f =? Eval(Substitute(x,-x)f));";
        scriptMap.put("EvenFunction?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Even?(n) := Integer?(n) And? ( BitAnd(n,1) =? 0 );";
        scriptMap.put("Even?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "FloatIsInt?(_x) <-- { x:=NM(Eval(x)); Local(prec,result,n); Assign(prec,BuiltinPrecisionGet()); Decide(Zero?(x),Assign(n,2), Decide(x>?0, Assign(n,2+FloorN(NM(FastLog(x)/FastLog(10)))), Assign(n,2+FloorN(NM(FastLog(-x)/FastLog(10)))) )); BuiltinPrecisionSet(n+prec); Assign(result,Zero?(RoundTo(x-Floor(x),prec)) Or? Zero?(RoundTo(x-Ceil(x),prec))); BuiltinPrecisionSet(prec); result; };";
        scriptMap.put("FloatIsInt?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "HasExpressionArithmetic?(expr, atom) := HasExpressionSome?(expr, atom, [ToAtom(\"+\"), ToAtom(\"-\"), *, /]);";
        scriptMap.put("HasExpressionArithmetic?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "HasExpressionSome?(_expr, _atom, _looklist):: Equal?(expr, atom) <-- True;15 # HasExpressionSome?(expr_Atom?, _atom, _looklist) <-- Equal?(expr, atom);19 # HasExpressionSome?([], _atom, _looklist) <-- False;20 # HasExpressionSome?(expr_List?, _atom, _looklist) <-- HasExpressionSome?(First(expr), atom, looklist) Or? HasExpressionSome?(Rest(expr), atom, looklist);25 # HasExpressionSome?(expr_Function?, _atom, _looklist)::(Not? Contains?(looklist, ToAtom(Type(expr)))) <-- False;30 # HasExpressionSome?(expr_Function?, _atom, _looklist) <-- HasExpressionSome?(Rest(FunctionToList(expr)), atom, looklist);";
        scriptMap.put("HasExpressionSome?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # HasExpression?(_expr, _atom):: Equal?(expr, atom) <-- True;15 # HasExpression?(expr_Atom?, _atom) <-- Equal?(expr, atom);19 # HasExpression?([], _atom) <-- False;20 # HasExpression?(expr_List?, _atom) <-- HasExpression?(First(expr), atom) Or? HasExpression?(Rest(expr), atom);30 # HasExpression?(expr_Function?, _atom) <-- HasExpression?(Rest(FunctionToList(expr)), atom);";
        scriptMap.put("HasExpression?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "HasFunctionArithmetic?(expr, atom) := HasFunctionSome?(expr, atom, [ToAtom(\"+\"), ToAtom(\"-\"), *, /]);";
        scriptMap.put("HasFunctionArithmetic?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # HasFunctionSome?(_expr, string_String?, _looklist) <-- HasFunctionSome?(expr, ToAtom(string), looklist);10 # HasFunctionSome?(expr_Atom?, atom_Atom?, _looklist) <-- False;15 # HasFunctionSome?(expr_Function?, atom_Atom?, _looklist)::(Not? Contains?(looklist, ToAtom(Type(expr)))) <-- Equal?(First(FunctionToList(expr)), atom);20 # HasFunctionSome?(expr_Function?, atom_Atom?, _looklist) <-- Equal?(First(FunctionToList(expr)), atom) Or? ListHasFunctionSome?(Rest(FunctionToList(expr)), atom, looklist);";
        scriptMap.put("HasFunctionSome?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # HasFunction?(_expr, string_String?) <-- HasFunction?(expr, ToAtom(string));10 # HasFunction?(expr_Atom?, atom_Atom?) <-- False;20 # HasFunction?(expr_Function?, atom_Atom?) <-- Equal?(First(FunctionToList(expr)), atom) Or? ListHasFunction?(Rest(FunctionToList(expr)), atom);";
        scriptMap.put("HasFunction?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Infinity?(Infinity) <-- True;10 # Infinity?(-(_x)) <-- Infinity?(x);11 # Infinity?(Sign(_x)*y_Infinity?) <-- True;60000 # Infinity?(_x) <-- False;";
        scriptMap.put("Infinity?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "19 # ListHasFunctionSome?([], _atom, _looklist) <-- False;20 # ListHasFunctionSome?(expr_List?, atom_Atom?, _looklist) <-- HasFunctionSome?(First(expr), atom, looklist) Or? ListHasFunctionSome?(Rest(expr), atom, looklist);";
        scriptMap.put("ListHasFunctionSome?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "19 # ListHasFunction?([], _atom) <-- False;20 # ListHasFunction?(expr_List?, atom_Atom?) <-- HasFunction?(First(expr), atom) Or? ListHasFunction?(Rest(expr), atom);";
        scriptMap.put("ListHasFunction?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # CanBeMonomial(_expr)::(Type(expr)=?\"UniVariate\") <-- False;10 # CanBeMonomial(_expr)<--Not? (HasFunction?(expr,ToAtom(\"+\")) Or? HasFunction?(expr,ToAtom(\"-\")));10 # Monomial?(expr_CanBeMonomial) <-- { Local(r); Decide( RationalFunction?(expr), r := (VarList(Denominator(expr)) =? []), r := True );};15 # Monomial?(_expr) <-- False;";
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
        scriptString[1] = "NegativeReal?(_r) <--{ r:=NM(Eval(r)); (Number?(r) And? r <=? 0);};";
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
        scriptString[1] = "NumericList?(_arglist) <-- List?(arglist) And? (\"And?\" @ (MapSingle(Hold([[x],Number?(NM(Eval(x)))]), arglist)));";
        scriptMap.put("NumericList?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "OddFunction?(f,x):= (f =? Eval(-Substitute(x,-x)f));";
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
        scriptString[1] = "10 # PolynomialOverIntegers?(expr_Function?) <-- { Local(x,vars); vars := VarList(expr); Decide(Length(vars)>?1,vars:=HeapSort(vars,\"GreaterThan?\"));  x := vars[1]; PolynomialOverIntegers?(expr,x);};15 # PolynomialOverIntegers?(_expr) <-- False;10 # PolynomialOverIntegers?(_expr,_var)::(CanBeUni(var,expr)) <--{ Decide( AllSatisfy?(\"Integer?\",Coef(expr,var,0 .. Degree(expr,var))), True, False );};15 # PolynomialOverIntegers?(_expr,_var) <-- False;";
        scriptMap.put("PolynomialOverIntegers?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Polynomial?( expr_String? ) <-- False;15 # Polynomial?( expr_Constant? ) <-- True; 20 # Polynomial?(_expr) <-- { Local(x,vars); vars := VarList(expr); Decide(Length(vars)>?1,vars:=HeapSort(vars,\"GreaterThan?\"));  x := vars[1]; Polynomial?(expr,x);};25 # Polynomial?(_expr) <-- False;10 # Polynomial?(_expr,_var)::(CanBeUni(var,expr)) <-- True;15 # Polynomial?(_expr,_var) <-- False;";
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
        scriptString[1] = "PositiveReal?(_r) <--{ r:=NM(Eval(r)); (Number?(r) And? r >=? 0);};";
        scriptMap.put("PositiveReal?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RationalFunction?(_expr)::(Length(VarList(expr))=?0) <-- False;15 # RationalFunction?(_expr) <-- RationalFunction?(expr,VarList(expr));10 # RationalFunction?(expr_RationalOrNumber?,_var) <-- False;15 # RationalFunction?(_expr,var_Atom?)::(Type(expr)=?\"/\" Or? Type(-expr)=?\"/\") <--{ Decide(Polynomial?(Numerator(expr),var) And? Polynomial?(Denominator(expr),var), Contains?(VarList(Denominator(expr)),var), False );};20 # RationalFunction?(_expr,vars_List?)::(Type(expr)=?\"/\" Or? Type(-expr)=?\"/\") <--{ Decide(Polynomial?(Numerator(expr),vars) And? Polynomial?(Denominator(expr),vars), Intersection(vars, VarList(expr)) !=? [], False );};60000 # RationalFunction?(_expr,_var) <-- False;";
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
        scriptString[1] = "10 # SumOfTerms?(_var,expr_FreeOf?(var)) <-- False;12 # SumOfTerms?(_var,expr_Atom?()) <-- False;14 # SumOfTerms?(_var,expr_List?())::(expr[1]=?ToAtom(\"+\") Or? expr[1]=?ToAtom(\"-\")) <-- True;16 # SumOfTerms?(_var,expr_List?())::(expr[1]=?ToAtom(\"*\")) <-- Or?(FreeOf?(var,expr[2]),FreeOf?(var,expr[3]));18 # SumOfTerms?(_var,expr_List?())::(expr[1]=?ToAtom(\"/\")) <-- FreeOf?(var,expr[3]);20 # SumOfTerms?(_var,expr_List?()) <-- False;22 # SumOfTerms?(_var,_expr) <-- SumOfTerms?(var,FunctionToList(expr));";
        scriptMap.put("SumOfTerms?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Variable?(_expr) <-- (Atom?(expr) And? Not?(expr =? True) And? Not?(expr =? False) And? Not?(expr =? Infinity) And? Not?(expr =? -Infinity) And? Not?(expr =? Undefined) And? Not?(Number?(NM(Eval(expr)))));";
        scriptMap.put("Variable?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"ZeroVector?\",[aList]) aList =? ZeroVector(Length(aList));";
        scriptMap.put("ZeroVector?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Zero?(x_Number?) <-- ( MathSign(x) =? 0 Or? AbsN(x) <? PowerN(10, -BuiltinPrecisionGet()));60000 # Zero?(_x) <-- False;20 # Zero?(UniVariate(_var,_first,_coefs)) <-- ZeroVector?(coefs);";
        scriptMap.put("Zero?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(p,x){Function(\"Scalar?\",[x]) Not?(List?(x));Function(\"Vector?\",[x]) Decide(List?(x), Length(Select(x, List?))=?0, False);Function(\"Vector?\",[p,x]){ Decide(List?(x), { Local(i,n,result); n:=Length(x); i:=1; result:=True; While(i<=?n And? result) { result:=Apply(p,[x[i]]); i++; }; result; }, False);};Function(\"Matrix?\",[x])Decide(List?(x) And? Length(x)>?0,{ Local(n); n:=Length(x); Decide(Length(Select(x, Vector?))=?n, MapSingle(Length,x)=?Length(x[1])+ZeroVector(n), False);},False);Function(\"Matrix?\",[p,x])Decide(Matrix?(x),{ Local(i,j,m,n,result); m:=Length(x); n:=Length(x[1]); i:=1; result:=True; While(i<=?m And? result) { j:=1; While(j<=?n And? result) { result:=Apply(p,[x[i][j]]); j++; }; i++; }; result;},False);Function(\"SquareMatrix?\",[x]) Matrix?(x) And? Length(x)=?Length(x[1]);Function(\"SquareMatrix?\",[p,x]) Matrix?(p,x) And? Length(x)=?Length(x[1]);}; ";
        scriptMap.put("Scalar?",scriptString);
        scriptMap.put("Matrix?",scriptString);
        scriptMap.put("Vector?",scriptString);
        scriptMap.put("SquareMatrix?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # CDF(BernoulliDistribution(_p), x_Number?) <-- Decide(x<=?0,0,Decide(x>?0 And? x<=?1, p,1));11 # CDF(BernoulliDistribution(_p), _x) <-- Hold(Decide(x<=?0,0,Decide(x>?0 And? x<=?1, p,1)));10 # CDF(BinomialDistribution(_p,_n),m_Number?)::(m<?0) <-- 0;10 # CDF(BinomialDistribution(_p,n_Integer?),m_Number?)::(m>?n) <-- 1;10 # CDF(BinomialDistribution(_p,_n),_m) <-- Sum @ [ i, 0, Floor(m), PMF(BinomialDistribution(p,n),i)];10 # CDF(DiscreteUniformDistribution( a_Number?, b_Number?), x_Number?)::(x<=?a) <-- 0;10 # CDF(DiscreteUniformDistribution( a_Number?, b_Number?), x_Number?)::(x>?b) <-- 1;10 # CDF(DiscreteUniformDistribution( a_Number?, b_Number?), x_Number?)::(a<?x And? x<=?b) <-- (x-a)/(b-a+1);11 # CDF(DiscreteUniformDistribution( _a, _b), _x) <--Hold(Decide(x<=?a,0,Decide(x<=?b,(x-a)/(b-a),1)));10 # CDF(PoissonDistribution(_l), x_Number?)::(x<=?0) <-- 0;10 # CDF(PoissonDistribution(_l), _x) <-- Sum @ [i,0,x,PMF(PoissonDistribution(l),i)];10 # CDF(ChiSquareDistribution(_m), _x) <-- IncompleteGamma(m/2,x/2)/Gamma(x/2);10 # CDF(DiscreteDistribution( dom_List?, prob_List?), _x) <-- { Local(i,cdf,y); i := 1; cdf:=0; y:=dom[i]; While(y<?x) {cdf:=cdf+prob[i];i++;}; cdf; };10 # CDF(HypergeometricDistribution( N_Number?, M_Number?, n_Number?), x_Number?)::(M <=? N And? n <=? N) <-- { Sum @ [i,0,x,PMF(HypergeometricDistribution(N, M, n),i)];};10 # CDF(NormalDistribution(_m,_s), _x) <-- 1/2 + 1/2 * ErrorFunction((x - m)/(s*Sqrt(2))); ";
        scriptMap.put("CDF",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Expectation(BernoulliDistribution(_p)) <-- 1-p;10 # Expectation(BinomialDistribution(_p,_n)) <-- n*p;10 # Expectation( DiscreteDistribution( dom_List?, prob_List?))::( Length(dom)=?Length(prob) And? Simplify(Sum(prob))=?1) <-- Sum @ [i,1,Length(dom),dom[i]*prob[i]];";
        scriptMap.put("Expectation",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # PDF(ExponentialDistribution(_l), _x) <-- Decide(x<?0,0,l*Exp(-l*x));10 # PDF(NormalDistribution(_m,_s),_x) <-- Exp(-(x-m)^2/(2*s^2))/Sqrt(2*Pi*s^2); 10 # PDF(ContinuousUniformDistribution(_a,_b),x)::(a<?b) <-- Decide(x<?a Or? x>?b,0,1/(b-a));10 # PDF(DiscreteDistribution( dom_List?, prob_List?), _x)::( Length(dom)=?Length(prob) And? Simplify(Add(prob))=?1) <-- { Local(i); i:=Find(dom,x); Decide(i =? -1,0,prob[i]); };10 # PDF( ChiSquareDistribution( _m),x_RationalOrNumber?)::(x<=?0) <-- 0;20 # PDF( ChiSquareDistribution( _m),_x) <-- x^(m/2-1)*Exp(-x/2)/2^(m/2)/Gamma(m/2);10 # PDF(tDistribution(_m),x) <-- Gamma((m+1)/2)*(1+x^2/m)^(-(m+1)/2)/Gamma(m/2)/Sqrt(Pi*m);";
        scriptMap.put("PDF",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # PMF(BernoulliDistribution(_p),0) <-- p;10 # PMF(BernoulliDistribution(_p),1) <-- 1-p;10 # PMF(BernoulliDistribution(_p),x_Number?)::(x !=? 0 And? x !=? 1) <-- 0;10 # PMF(BernoulliDistribution(_p),_x) <-- Hold(Decide(x=?0,p,Decide(x=?1,1-p,0)));10 # PMF(BinomialDistribution(_p,_n),_k) <-- BinomialCoefficient(n,k)*p^k*(1-p)^(n-k);10 # PMF(DiscreteUniformDistribution(_a,_b), x_Number?) <-- Decide(x<?a Or? x>?b, 0 ,1/(b-a+1));11 # PMF(DiscreteUniformDistribution(_a,_b), _x) <-- Hold(Decide(x<?a Or? x>?b, 0 ,1/(b-a+1)));10 # PMF(PoissonDistribution(_l), n_Number?) <-- Decide(n<?0,0,Exp(-l)*l^n/n!);11 # PMF(PoissonDistribution(_l),_n) <-- Exp(-l)*l^n/n!;10 # PMF(GeometricDistribution(_p),_n) <--Decide(n<?0,0,p*(1-p)^n);10 # PMF(DiscreteDistribution( dom_List?, prob_List?), _x)::( Length(dom)=?Length(prob) And? Simplify(Add(prob))=?1) <-- { Local(i); i:=Find(dom,x); Decide(i =? -1,0,prob[i]); }; 10 # PMF(HypergeometricDistribution( N_Number?, M_Number?, n_Number?), x_Number?)::(M <=? N And? n <=? N) <-- (BinomialCoefficient(M,x) * BinomialCoefficient(N-M, n-x))/BinomialCoefficient(N,n);";
        scriptMap.put("PMF",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Manipulate\",[symbolicEquation]);HoldArgument(\"Manipulate\",symbolicEquation);10 # Manipulate(_symbolicEquation)::HasFunction?(Eval(symbolicEquation), \"==\") <--{ Local(listForm, operator, operand, left, right, leftManipulated, rightManipulated, operandIndex, equationIndex, leftOrder, rightOrder); listForm := FunctionToList(symbolicEquation);  operator := listForm[1];  Decide(HasFunction?(Eval(listForm[2]),\"==\" ), {operandIndex := 3; equationIndex := 2; }, { operandIndex := 2; equationIndex := 3;});  operand := listForm[operandIndex]; equation := Eval(listForm[equationIndex]); left := EquationLeft(equation); right := EquationRight(equation);  Decide(operandIndex =? 3, { leftOrder := `([left,operand]);rightOrder := `([right,operand]);}, {leftOrder := `([operand,left]); rightOrder := `([operand,right]);});   leftManipulated := ExpandBrackets(Simplify(Apply(ToString(operator), leftOrder))); rightManipulated := ExpandBrackets(Simplify(Apply(ToString(operator), rightOrder)));   leftManipulated == rightManipulated;};";
        scriptMap.put("Manipulate",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ControlChart(data) :={ A2 := .577; D3 := 0; D4 := 2.144; means := []; meansPoints := [];  ranges := []; rangesPoints := [];  index := 1; ForEach(group, data) { groupMean := Mean(group); means := NM(Append(means, groupMean)); meansPoints := NM(Append(meansPoints,[index, groupMean] ));  groupRange := Range(group); ranges := NM(Append(ranges, groupRange)); rangesPoints := NM(Append(rangesPoints,[index, groupRange] ));  index++; };  xBarBar := NM(Mean(means));  rBar := NM(Mean(ranges));  xBarUCL := NM(xBarBar + A2*rBar);  xBarLCL := NM(xBarBar - A2*rBar);  rUCL := NM(D4*rBar);  rLCL := NM(D3*rBar);};";
        scriptMap.put("ControlChart",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(options){ options := [];  Local(updateObjects);  updateObjects := \"\";  options[\"updateObjects\"] := updateObjects;  GeoGebra() := options;GeoGebra(list) := (options := list);};";
        scriptMap.put("GeoGebra",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "GeoGebraHistogram(classBoundaries, data) := { Local(command);    command := PatchString(\"Histogram[<?Write(classBoundaries);?>,<?Write(data);?>]\"); JavaCall(geogebra, \"evalCommand\", command);};GeoGebraHistogram(data) := { Local(command, classBoundaries, noDuplicatesSorted, largestValue, smallestValue, x, numberOfUniqueValues);  noDuplicatesSorted := HeapSort(RemoveDuplicates(data), \"<?\" );  smallestValue := Floor(noDuplicatesSorted[1]);  numberOfUniqueValues := Length(noDuplicatesSorted);  largestValue := Ceil(noDuplicatesSorted[Length(noDuplicatesSorted)]);  classBoundaries := NM(BuildList(x,x,smallestValue-.5,largestValue+.5,1));  command := PatchString(\"Histogram[<?Write(classBoundaries);?>,<?Write(data);?>]\"); JavaCall(geogebra, \"evalCommand\", command);};";
        scriptMap.put("GeoGebraHistogram",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseListedHoldArguments(\"GeoGebraPlot\",[arg1,arg2]);5 # GeoGebraPlot(_arg1) <-- GeoGebraPlot(arg1,[]); 20 # GeoGebraPlot(function_Function?, options_List?)::(Not? List?(function)) <--{ Local(command);  function := (Substitute(==,=) function);  command := ConcatStrings(PipeToString()Write(function));  JavaCall(geogebra,\"evalCommand\",command);};10 # GeoGebraPlot(list_List?, _options)::(NumericList?(list) ) <--{ Decide(List?(options), options := OptionsToAssociativeList(options), options := OptionsToAssociativeList([options])); Local(length, index, labelIndex, pointTemplate, segmentCommandTemplate, segmentElementTemplate, command, code, x, y, pointSize);  length := Length(list);  Decide(Odd?(length), list := Append(list,list[length]));   Decide(options[\"pointSize\"] !=? Empty, pointSize := ToString(options[\"pointSize\"]), pointSize := \"1\");  index := 1;  labelIndex := 1;  pointTemplate := \"<element type=\\\"point\\\" label=\\\"A<?Write(labelIndex);?>\\\"> <show object=\\\"true\\\" label=\\\"false\\\"/>?<objColor r=\\\"0\\\" g=\\\"0\\\" b=\\\"255\\\" alpha=\\\"0.0\\\"/> <layer val=\\\"0\\\"/> <animation step=\\\"0.1\\\" speed=\\\"1\\\" type=\\\"0\\\" playing=\\\"false\\\"/> <coords x=\\\"<?Write(x);?>\\\" y=\\\"<?Write(y);?>\\\" z=\\\"1.0\\\"/> <pointSize val=\\\"<?Write(ToAtom(pointSize));?>\\\"/></element>\"; segmentCommandTemplate := \"<command name=\\\"Segment\\\"><input a0=\\\"A1\\\" a1=\\\"A2\\\"/><output a0=\\\"a\\\"/>\"; segmentElementTemplate := \"<element type=\\\"segment\\\" label=\\\"a<?Write(labelIndex-1);?>\\\"><lineStyle thickness=\\\"2\\\" type=\\\"0\\\"/><show object=\\\"true\\\" label=\\\"false\\\"/><layer val=\\\"0\\\"/><coords x=\\\"-1.0\\\" y=\\\"1.0\\\" z=\\\"0.0\\\"/><lineStyle thickness=\\\"2\\\" type=\\\"0\\\"/><eqnStyle style=\\\"implicit\\\"/><outlyingIntersections val=\\\"false\\\"/><keepTypeOnTransform val=\\\"true\\\"/></element>\";      While(index <? length+1) { x := list[index]; index++; y := list[index]; index++;   code := PatchString(pointTemplate);  JavaCall(geogebra,\"evalXML\",code);  Decide(options[\"lines\"] =? True And? labelIndex >? 1, {  command := PatchString(\"a<?Write(labelIndex-1);?> = Segment[A<?Write(labelIndex-1);?>,A<?Write(labelIndex);?>]\"); JavaCall(geogebra, \"evalCommand\", command);  code := PatchString(segmentElementTemplate); JavaCall(geogebra,\"evalXML\",code); } );  labelIndex++; };    };5 # GeoGebraPlot(list_List?, _options)::(Matrix?(list)) <--{ Local(flatList);  flatList := [];  ForEach(subList,list) { DestructiveAppend(flatList,subList[1]); DestructiveAppend(flatList, subList[2]); };  GeoGebraPlot(flatList, options);};";
        scriptMap.put("GeoGebraPlot",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # GeoGebraPoint(name_String?, x_Number?, y_Number?) <--{ Local(command);  command := PatchString(\"<?Write(ToAtom(name));?>=(<?Write(x);?>,<?Write(y);?>)\");  JavaCall(geogebra,\"evalCommand\",command);};";
        scriptMap.put("GeoGebraPoint",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ggbLine(point1Label, point2Label) :={ Local(command);  command := PatchString(\"Line[<?Write(ToAtom(point1Label));?>,<?Write(ToAtom(point2Label));?>]\");   JavaCall(geogebra,\"evalCommand\",command);};";
        scriptMap.put("GgbLine",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Distance(PointA_Point?,PointB_Point?) <--{ Local(x1,x2,y1,y2,distance); x1 := PointA[1]; x2 := PointB[1]; y1 := PointA[2]; y2 := PointB[2];  distance := Sqrt((x2 - x1)^2 + (y2 - y1)^2);};";
        scriptMap.put("Distance",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Midpoint(PointA_Point?,PointB_Point?) <-- { Local(x1,x2,y1,y2,midpointX,midpointY);  x1 := PointA[1]; x2 := PointB[1]; y1 := PointA[2]; y2 := PointB[2]; midpointX := (x1 + x2)/2; midpointY := (y1 + y2)/2;  [midpointX,midpointY];};Midpoint(segment_Segment?) <-- { Local(x1,x2,y1,y2,midpointX,midpointY);  x1 := segment[1][1]; x2 := segment[2][1]; y1 := segment[1][2]; y2 := segment[2][2]; midpointX := (x1 + x2)/2; midpointY := (y1 + y2)/2;  [midpointX,midpointY];};";
        scriptMap.put("Midpoint",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Point(x,y) := List(x,y);Point(x,y,z) := List(x,y,z);";
        scriptMap.put("Point",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Point?(p) := Decide(List?(p) And? (Length(p) =? 2 Or? Length(p) =? 3),True,False);";
        scriptMap.put("Point?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Segment(PointA_Point?,PointB_Point?) <--{ Local(x1,x2,y1,y2);  x1 := PointA[1]; x2 := PointB[1]; y1 := PointA[2]; y2 := PointB[2]; [[x1,y1],[x2,y2]];};";
        scriptMap.put("Segment",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Segment?(list_List?) <--{ Decide(List?(list[1]) And? Length(list[1])=?2 And? List?(list[2]) And? Length(list[2])=?2,True,False);};";
        scriptMap.put("Segment?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Slope(PointA_Point?,PointB_Point?) <--{ Local(x1,x2,y1,y2,slope); x1 := PointA[1]; x2 := PointB[1]; y1 := PointA[2]; y2 := PointB[2];  slope := (y2 - y1)/(x2 - x1);};10 # Slope(segment_List?)::(Length(segment) =? 2 And? Length(segment[1]) =? 2 And? Length(segment[2]) =? 2) <--{ Local(x1,x2,y1,y2,slope);  x1 := segment[1][1];  x2 := segment[2][1];    y1 := segment[1][2];  y2 := segment[2][2];  slope := (y2 - y1)/(x2 - x1);};";
        scriptMap.put("Slope",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BooleanList(elements, integerBitPattern) :={ Local(stringBitPattern, leadingDigitsCount, atomBitPattern);  Check(Integer?(elements) And? elements >? 0, \"Argument\", \"The first argument must be an integer that is > 0.\");  Check(Integer?(integerBitPattern) And? integerBitPattern >=? 0, \"Argument\", \"The second argument must be an integer that is >= 0.\");  stringBitPattern := ToBase(2,integerBitPattern);  leadingDigitsCount := elements - Length(stringBitPattern);   Decide(leadingDigitsCount >? 0, stringBitPattern := Concat(FillList(\"0\",leadingDigitsCount), StringToList(stringBitPattern)));  atomBitPattern := MapSingle(\"ToAtom\", stringBitPattern);  atomBitPattern /: [0 <- False, 1 <- True]; };";
        scriptMap.put("BooleanList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BooleanLists(elements) :={ Local(numberOfPatterns);  Check(elements >? 0, \"Argument\", \"The argument must be > 0.\");  numberOfPatterns := 2^elements - 1;  BuildList(BooleanList(elements, pattern), pattern,0, numberOfPatterns, 1);};";
        scriptMap.put("BooleanLists",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Subexpressions(expression) :={ Local(subexpressions, uniqueSubexpressions, variables, functions, sortedFunctions);  subexpressions := SubexpressionsHelper(expression,[]);  uniqueSubexpressions := RemoveDuplicates(subexpressions);  variables := VarList(uniqueSubexpressions);  functions := Select(uniqueSubexpressions, \"Function?\"); sortedFunctions := Sort(functions,Lambda([x,y],Length(FunctionListAllHelper(x)) + Length(VarList(x)) <? Length(FunctionListAllHelper(y)) + Length(VarList(y))));  Concat(variables, sortedFunctions);};SubexpressionsHelper(expression, list) :={ Local(first, rest);   If((Not? Atom?(expression)) And? (Length(expression) !=? 0)) {  first := First(expression);  list := SubexpressionsHelper(first, list);  rest := Rest(expression);  Decide(Length(rest) !=? 0, rest := First(rest));  Decide(Length(rest) !=? 0, list := SubexpressionsHelper(rest, list)); };  Append(list, expression);};FunctionListAllHelper(expression) :={ If(Atom?(expression)) { [];  } Else If(Function?(expression)) {  Concat( [First(FunctionToList(expression))], Apply(\"Concat\", MapSingle(\"FunctionListAllHelper\", Rest(FunctionToList(expression))))); } Else { Check(False, \"Argument\", \"The argument must be an atom or a function.\");  };};";
        scriptMap.put("Subexpressions",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TruthTable(booleanExpression) :={ Local(resultList, variables, booleanPatterns, subexpressions, substitutionList, evaluation);  resultList := [];  variables := VarList(booleanExpression);  booleanPatterns := Reverse(BooleanLists(Length(variables)));  subexpressions := Subexpressions(booleanExpression);  DestructiveAppend(resultList, subexpressions);  ForEach(booleanPattern, booleanPatterns) { substitutionList := Map(\"==\", [variables, booleanPattern]);  evaluation := `(subexpressions Where @substitutionList);  DestructiveAppend(resultList, evaluation);  };  resultList;};";
        scriptMap.put("TruthTable",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CombinationsList(inputList, r) :={ Local(n,manipulatedIndexes,totalCombinations,combinationsList,combinationsLeft,combination,i,j,currentIndexes);  Check(List?(inputList) And? Length(inputList) >=? 1, \"Argument\", \"The first argument must be a list with 1 or more elements.\");  n := Length(inputList);  Check(r <=? n , \"Argument\", \"The second argument must be <=? the length of the list.\");  manipulatedIndexes := 1 .. r;   totalCombinations := Combinations(n,r);  combinationsLeft := totalCombinations;  combinationsList := [];  While(combinationsLeft >? 0) { combination := [];  If(combinationsLeft =? totalCombinations) {  combinationsLeft := combinationsLeft - 1;  currentIndexes := manipulatedIndexes; } Else { i := r;  While(manipulatedIndexes[i] =? n - r + i) { i--; };  manipulatedIndexes[i] := manipulatedIndexes[i] + 1;  For(j := i + 1, j <=? r, j++)  { manipulatedIndexes[j] := manipulatedIndexes[i] + j - i; };  combinationsLeft := combinationsLeft - 1;  currentIndexes := manipulatedIndexes; }; For(i := 1, i <=? Length(currentIndexes), i++)  { combination := Append(combination,(inputList[currentIndexes[i]])); };  combinationsList := Append(combinationsList,combination); };  combinationsList;};";
        scriptMap.put("CombinationsList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ElementCount(list) :={  If(Length(list) =? 0) { 0; } Else If(Atom?(list)) { 1; } Else { ElementCount(First(list)) + ElementCount(Rest(list)); };};";
        scriptMap.put("ElementCount",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ListOfLists?(listOfLists) :={ Local(result);  result := True;  If(Not? List?(listOfLists)) { result := False; } Else  { ForEach(list, listOfLists) { Decide(Not? List?(list), result := False); }; };  result;};";
        scriptMap.put("ListOfLists?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ListToString(list_List?)::(Length(list) =? 0) <-- \"\";20 # ListToString(list_List?) <--{ Local(resultString, character);  resultString := \"\";  ForEach(element, list) { Decide(String?(element), character := element, character := ToString(element));  resultString := resultString ~ character; };  resultString;};";
        scriptMap.put("ListToString",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(ZoomInOnce){ 10 # NumberLinePrintZoom(_lowValue, _highValue, divisions_PositiveInteger?, depth_PositiveInteger?)::(lowValue <? highValue) <--  {  Local(numbers, stepAmount, zoomIndexes, nextZoomIndex, outputWidth, numbersString, output, randomStep, randomZoomNumber, iteration);  iteration := 1;  While(iteration <=? depth) { [numbers, stepAmount] := ZoomInOnce(lowValue, highValue, divisions);  zoomIndexes := [];  outputWidth := 0;  numbersString := \"\";  ForEach(number, numbers) { output := PipeToString() Write(number);  zoomIndexes := Append(zoomIndexes, Length(output));  numbersString := numbersString ~ output ~ PipeToString() Space(3);  outputWidth := outputWidth + Length(output) + 3;  };  randomStep := RandomInteger(divisions);  randomZoomNumber := Sum(Take(zoomIndexes, randomStep));  Decide(randomStep =? 1, nextZoomIndex := randomZoomNumber + 1, nextZoomIndex := 3*(randomStep-1) + randomZoomNumber + 1);  Decide(iteration >? 1, Echo(ListToString(FillList(\"-\", outputWidth-3))));   Echo(numbersString);  Decide(iteration !=? depth,{Space(nextZoomIndex);Echo(\"|\");});  lowValue := numbers[randomStep];  highValue := numbers[randomStep+1];  iteration++;  };  };     ZoomInOnce(_lowValue, _highValue, divisions_PositiveInteger?)::(lowValue <? highValue) <-- { Local(stepAmount, x, numbers);  stepAmount := Decide(Decimal?(lowValue) Or? Decimal?(highValue), NM((highValue-lowValue)/divisions), (highValue-lowValue)/divisions);  x := lowValue;  numbers := [];  While(x <=? highValue) {  numbers := Append(numbers, x);  x := x + stepAmount;  };  [numbers, stepAmount];  };};";
        scriptMap.put("NumberLinePrintZoom",scriptString);
        scriptMap.put("ZoomInOnce",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "OptionsToAssociativeList(optionList) :={ Local(associativeList, key, value);  associativeList := [];  ForEach(option, optionList) { Decide(option[0] =? : , { Decide(String?(option[1]), key := option[1], key := ToString(option[1])); Decide(String?(option[2]) Or? Number?(option[2]) Or? Constant?(option[2]), value := option[2], value := ToString(option[2]));  associativeList := [key, value] ~ associativeList;  });  }; associativeList;};";
        scriptMap.put("OptionsToAssociativeList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # PadLeft(number_Number?, totalDigits_Integer?) <--{ Local(integerString, padAmount, resultString);  integerString := ToString(number);  padAmount := totalDigits - Length(integerString);  Decide(padAmount >? 0, resultString := ListToString(FillList(0, padAmount)) ~ integerString, resultString := integerString );};";
        scriptMap.put("PadLeft",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # StringToList(string_String?)::(Length(string) =? 0) <-- [];20 # StringToList(string_String?) <--{ Local(resultList);  resultList := [];  ForEach(character, string) { resultList := Append(resultList, character); };  resultList;};";
        scriptMap.put("StringToList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "StringToNumber( str_String? ) <-- FromBase(10,str);";
        scriptMap.put("StringToNumber",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "VerifyNumeric(expression1, expression2, optionsList) :={ Local(variablesList1, variablesList2, numericValue1, numericValue2, numericDifference, optionsVariableNamesList, optionsValuesList, associativeList);  variablesList1 := VarList(expression1);  variablesList2 := VarList(expression2);  If(Length(variablesList1) =? 0 And? Length(variablesList2) =? 0) { numericValue1 := NM(expression1);  numericValue2 := NM(expression2); } Else { optionsList := HeapSort(optionsList, Lambda([x,y],LessThan?(x[1],y[1])));  associativeList := OptionsToAssociativeList(optionsList);  optionsVariableNamesList := MapSingle(\"ToAtom\", AssocIndices(associativeList));  optionsValuesList := MapSingle(\"ToAtom\", AssocValues(associativeList));  variablesList1 := HeapSort(variablesList1,\"LessThan?\");  variablesList2 := HeapSort(variablesList2,\"LessThan?\");  Check(variablesList1 =? variablesList2 And? variablesList1 =? optionsVariableNamesList, \"Argument\", \"Both expressions and the options list must have the same variable names and the same number of variables.\");  numericValue1 := NM(WithValue(variablesList1, optionsValuesList, expression1));  numericValue2 := NM(WithValue(variablesList2, optionsValuesList, expression2 ));  Echo(Map(\":\",[variablesList1, optionsValuesList]));  NewLine(); };  Echo(expression1, \"-> \", numericValue1);  NewLine();  Echo(expression2, \"-> \", numericValue2);  numericDifference := NM(numericValue1 - numericValue2);  NewLine();  Echo(\"Difference between the numeric values: \", numericDifference);  numericDifference;};VerifyNumeric(expression1, expression2) :={ VerifyNumeric(expression1, expression2, []);};";
        scriptMap.put("VerifyNumeric",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " Retract(\"+$\", *); Retract(\"-$\", *); Retract(\"/$\", *); Retract(\"*$\", *); Retract(\"^$\", *);  Infix(\"+$\",70); Infix(\"-$\",70); RightPrecedenceSet(\"-$\",40); Infix(\"/$\",30); Infix(\"*$\",40); Infix(\"^$\",20); LeftPrecedenceSet(\"^$\",19);  RightAssociativeSet(\"^$\"); Prefix(\"+$\",50); Prefix(\"-$\",50); RightPrecedenceSet(\"-$\",40);   RulebaseEvaluateArguments(\"+$\",[lhs, rhs]); RulebaseEvaluateArguments(\"-$\",[lhs, rhs]); RulebaseEvaluateArguments(\"/$\",[lhs, rhs]); RulebaseEvaluateArguments(\"*$\",[lhs, rhs]); RulebaseEvaluateArguments(\"^$\",[lhs, rhs]); RulebaseEvaluateArguments(\"+$\",[operand]); RulebaseEvaluateArguments(\"-$\",[operand]); Position(subTerm, term) :={ Local(positionList);  Check(Function?(term), \"Argument\", \"The argument must be an expression that contains a function.\");  positionList := PositionHelper(subTerm, term,[False]);  positionList := Rest(positionList);  Rest(Reverse(positionList));};PositionHelper(subTerm, term, list) :={  Local(argPosition); If(Atom?(term) And? (subTerm =? term)) { list[1] := True;  list; } Else If(Atom?(term) ) { list; } Else { argPosition := Length(term);  While(argPosition >? 0) {  list := PositionHelper(subTerm, term[argPosition], list);  Decide(list[1] =? True, {list := Append(list, argPosition);argPosition := -1;});  argPosition--; };  list; };};Occurrence(expression, variable) :={ Check(Function?(expression), \"Argument\", \"The argument must be an expression that contains a function.\"); Count(VarListAll(expression), variable);};SingleOccurrence?(expression, variable) :={ Occurrence(expression, variable) =? 1;};Retract(\"SolveEquation\",*);RulebaseListedHoldArguments(\"SolveEquation\",[equation, variable, optionsList]);5 # SolveEquation(_equation, _variable) <-- SolveEquation(equation, variable, []);10 # SolveEquation(_equation, _variable, optionsList_List?) <--{ Local(options, path, steps);  options := OptionsToAssociativeList(optionsList);  Check(Occurrence(equation, variable) >? 0, \"Argument\", \"The variable \" ~ ToString(variable) ~ \" does not occur in the equation.\");  Check(Occurrence(equation, variable) =? 1, \"Argument\", \"Equations with multiple occurrences of the unknown are not currently supported.\");  equation := AddDollarSigns(equation);  steps := Isolate(variable, equation);};20 # SolveEquation(_equation, _variable, _singleOption) <-- SolveEquation(equation, variable, [singleOption]);Isolate(variable, equation) :={ Local(result, rest, currentEquation, path, showTree); result := [];  path := Position(variable, equation);  DestructiveAppend(result, [equation,\"Equation\", \"The original equation.\", variable]);  rest := [equation]; ForEach(pathNumber, path) { currentEquation := First(rest);  rest := Isolax(pathNumber, currentEquation);  DestructiveAppend(result, rest); };  result;};Retract(\"StepsView\",*);RulebaseListedHoldArguments(\"StepsView\",[steps, optionsList]);5 # StepsView(_steps) <-- StepsView(steps, []);10 # StepsView(_steps, optionsList_List?) <--{ Local(result, currentEquation, options, message, treeScale, latexScale);  result := [];  options := OptionsToAssociativeList(optionsList);  If(Number?(options[\"Scale\"])) { treeScale := options[\"Scale\"]; } Else { treeScale := 1.5; };  latexScale := treeScale * 17;  message := \"\\\\footnotesize \\\\mbox{The dominant operator on the left side of an equation is the\\\\\\\\operator that has the lowest precedence. This is always the top \\\\\\\\operator on the left side of the equation's expression tree.\\\\\\\\\\\\vspace{.75in}The expression tree of an equation that has only one occurrence\\\\\\\\of the variable to be isolated can be solved using the following\\\\\\\\procedure:\\\\\\\\\\\\vspace{.75in}1) Identify the operator that is at the top of the left side of the\\\\\\\\\\\\hspace{.5in}tree (it is highlighted in the trees below).\\\\\\\\\\\\vspace{.75in}2) Identify the operand of this operator that does not contain the\\\\\\\\\\\\hspace{.75in}variable to be isolated.\\\\\\\\\\\\vspace{.75in}3) Remove the top operator (along with the operand from step 2)\\\\\\\\\\\\hspace{.5in}from the left side of the tree, and add the inverse of\\\\\\\\\\\\hspace{.5in}this operator (along with the operand from step 2) to\\\\\\\\\\\\hspace{.5in}the top of the right side of the tree.\\\\\\\\\\\\vspace{.75in}}\";  DestructiveAppend(result, LatexView(message, Scale: latexScale));   message := \"\\\\text{Solve}\\\\ \" ~ ToString(ToAtom(ListToString(Remove(StringToList(UnparseLatex(TagLeftSideDominantFunction(RemoveDollarSigns(steps[1][1])))),\"$\"))) ) ~ \"\\\\ \\\\text{for}\\\\ \" ~ ToString(steps[1][4]) ~ \".\"; DestructiveAppend(result, LatexView(message, Scale: latexScale));   Decide(options[\"ShowTree\"] =? True, DestructiveAppend(result, TreeView(TagLeftSideDominantFunction(RemoveDollarSigns(steps[1][1])), Scale: treeScale, Resizable: False, IncludeExpression: False)));   steps := Rest(steps);  ForEach(rest, steps) {  If(Not? List?(rest[1])) { result := Compose(result, rest[1], rest[3], rest[4], Length(rest) =? 4, options[\"ShowTree\"], treeScale, latexScale); } Else { result := Compose(result, rest[1][1], rest[3][1], rest[4][1], Length(rest) =? 4, options[\"ShowTree\"], treeScale, latexScale);  result := Compose(result, rest[1][2], rest[3][2], rest[4][2], Length(rest) =? 4, options[\"ShowTree\"], treeScale, latexScale); };  };  result;};20 # StepsView(_steps, _singleOption) <-- StepsView(steps, [singleOption]);Compose(result, newExpression, explanation, beforeSimplification, lengthFour?, showTree?, treeScale, latexScale) :={ DestructiveAppend(result, LatexView(\" \\\\vspace{.75in}\", Scale: latexScale)); DestructiveAppend(result, LatexView(ToLatex(explanation),Scale: latexScale));  If(lengthFour?) { DestructiveAppend(result, LatexView(\" \\\\vspace{.10in}\", Scale: latexScale)); DestructiveAppend(result, LatexView(TexFormNoDollarSigns(beforeSimplification),Scale: latexScale)); };  DestructiveAppend(result, LatexView(\" \\\\vspace{.10in}\", Scale: latexScale));   DestructiveAppend(result, LatexView(ToAtom(ListToString(Remove(StringToList(UnparseLatex(TagLeftSideDominantFunction(RemoveDollarSigns(newExpression)))),\"$\"))), Scale: latexScale ));   Decide(showTree? =? True, DestructiveAppend(result, TreeView(TagLeftSideDominantFunction(RemoveDollarSigns(newExpression)), Scale: treeScale, Resizable: False, IncludeExpression: False)));  result; };TagLeftSideDominantFunction(expression) :={ If(Function?(expression) And? Function?(Cdr(Car(expression)))) { MetaSet(Car(Cdr(Car(expression))),\"HighlightColor\",\"GREEN\");  MetaSet(Car(Cdr(Car(expression))),\"HighlightNodeShape\",\"RECTANGLE\"); };  expression;};RemoveDollarSigns(equation) :={ equation := Substitute(ToAtom(\"+$\"),ToAtom(\"+\")) equation; equation := Substitute(ToAtom(\"-$\"),ToAtom(\"-\")) equation; equation := Substitute(ToAtom(\"*$\"),ToAtom(\"*\")) equation; equation := Substitute(ToAtom(\"/$\"),ToAtom(\"/\")) equation; equation := Substitute(ToAtom(\"^$\"),ToAtom(\"^\")) equation;};AddDollarSigns(equation) :={ equation := Substitute(ToAtom(\"+\"),ToAtom(\"+$\")) equation; equation := Substitute(ToAtom(\"-\"),ToAtom(\"-$\")) equation; equation := Substitute(ToAtom(\"*\"),ToAtom(\"*$\")) equation; equation := Substitute(ToAtom(\"/\"),ToAtom(\"/$\")) equation; equation := Substitute(ToAtom(\"^\"),ToAtom(\"^$\")) equation;};TexFormNoDollarSigns(equation) := { ToAtom(ListToString(Remove(StringToList(UnparseLatex(RemoveDollarSigns(equation))),\"$\")));};ToLatex(string) :={ Local(oldList, newList);  oldList := StringToList(string);  oldList := Remove(oldList, \"$\");  newList := []; ForEach(character, oldList) { DestructiveAppend(newList, Decide(character =? \" \", \"\\\\ \", character)); };  ListToString(newList);};Isolax(side, equation) :={ side :: equation /: [  1 :: -$ _lhs == _rhs <- [lhs == -$ rhs, \"UnaryMinus\", \"\\\\text{Multiply both sides by }\" ~ ToString(-1) ~ \"\\\\text{:}\", -1 *$ -$ lhs == rhs *$ -1],  1 :: _term1 +$ _term2 == _rhs <- [term1 == rhs -$ term2, \"Addition1\", \"\\\\text{Subtract }\" ~ ToString(term2) ~ \"\\\\text{ from both sides:}\", -$ term2 +$ term1 +$ term2 == rhs -$ term2], 2 :: _term1 +$ _term2 == _rhs <- [term2 == rhs -$ term1, \"Addition2\", \"\\\\text{Subtract }\" ~ ToString(term1) ~ \"\\\\text{ from both sides:}\", -$ term1 +$ term1 +$ term2 == rhs -$ term1],  1 :: _term1 -$ _term2 == _rhs <- [term1 == rhs +$ term2, \"Subtraction1\", \"\\\\text{Add }\" ~ ToString(term2) ~ \"\\\\text{ to both sides:}\", term2 +$ term1 -$ term2 == rhs +$ term2], 2 :: _term1 -$ _term2 == _rhs <- [term2 == term1 -$ rhs, \"Subtraction2\", \"\\\\text{Add }\" ~ ToString(term1) ~ \"\\\\text{ to both sides:}\", term1 +$ term1 -$ term2 == rhs +$ term1],  1 :: _term1 *$ _term2 == _rhs <- [term1 == rhs /$ term2, \"Multiplication1\", \"\\\\text{Divide both sides by }\" ~ ToString(term2) ~ \"\\\\text{:}\", (term1 *$ term2) /$ term2 == rhs /$ term2], 2 :: _term1 *$ _term2 == _rhs <- [term2 == rhs /$ term1, \"Multiplication2\", \"\\\\text{Divide both sides by }\" ~ ToString(term1) ~ \"\\\\text{:}\", (term1 *$ term2) /$ term1 == rhs /$ term1],  1 :: _term1 /$ _term2 == _rhs <- [term1 == rhs *$ term2, \"Division1\", \"\\\\text{Multiply both sides by }\" ~ ToString(term2) ~ \"\\\\text{:}\", term2 *$ (term1 /$ term2) == rhs *$ term2], 2 :: _term1 /$ _term2 == _rhs <- [[rhs *$ term2 == term1, term2 == term1 /$ rhs], \"Division2\", [\"\\\\text{Multiply both sides by }\" ~ ToString(term2) ~ \"\\\\text{ and exchange sides:}\", \"\\\\text{Divide both sides by }\" ~ ToString(TexFormNoDollarSigns(rhs)) ~ \"\\\\text{:}\"], [(term1 /$ term2) *$ term2 == rhs *$ term2, (rhs *$ term2) /$ rhs == term1 /$ rhs]],      1 :: _term1 ^$ _term2 == _rhs <- [term1 == rhs^$(1/$term2), \"Exponentiation1b\", \"\\\\text{Take root }\" ~ ToString(term2) ~ \"\\\\text{ of both sides:}\", (term1 ^$ term2)^$(1/term2) == rhs^$(1/$ term2)],   ]; };";
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

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "AlphaToChiSquareScore(p, df) :={ Local(ChiEpsilon, ChiMax, minchisq, maxchisq, chisqval, result);  ChiEpsilon := 0.000001;   ChiMax := 99999.0;   minchisq := 0.0;  maxchisq := ChiMax;  p := NM(p);  If( p <=? 0.0 Or? p >=? 1.0) {  If(p <=? 0.0)  { result := maxchisq; }  Else  { If(p >=? 1.0)  { result := 0.0; }; };  } Else { chisqval := NM(df / SqrtN(p));   While ((maxchisq - minchisq) >? ChiEpsilon)  { If(ChiSquareScoreToAlpha(chisqval, df) <? p)  { maxchisq := chisqval; }  Else  { minchisq := chisqval; }; chisqval := (maxchisq + minchisq) * 0.5; };  result := chisqval;  };  NM(result);};";
        scriptMap.put("AlphaToChiSquareScore",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "AnovaCompletelyRandomizedBlock(levelsList, alpha) :={ Check(Matrix?(levelsList), \"Argument\", \"The first argument must be a list of equal-length lists.\");  Check(alpha >=? 0 And? alpha <=? 1, \"Argument\", \"The second argument must be a number between 0 and 1.\");  Local( topOfSummary, anovaBlockTableRow1, criticalFScore, anovaBlockTableRow3, anovaBlockTableRow2, lengthsList, summaryTableRow, sumsList, meanSquareWithin, topOfPage, htmlJavaString, index, variancesList, grandMean, row, topOfAnovaBlock, result, fScoreBlock, criticalFScoreBlock, blockMeansList, sumOfSquaresWithin, meanSquareBetween, sumOfSquaresBetween, fScore, summaryTableRows, meansList, sumOfSquaresBlock, b, blockSummaryTableRow, bottomOfAnovaBlock, sumOfSquaresWithin, bottomOfPage, k, sumOfSquaresTotal, meanSquareBlock, bottomOfSummary );  meansList := [];  variancesList := [];  sumsList := [];  lengthsList := [];    ForEach(levelList, levelsList) { meansList := meansList ~ NM(Mean(levelList));  variancesList := variancesList ~ NM(UnbiasedVariance(levelList));  sumsList := sumsList ~ NM(Sum(levelList)); lengthsList := lengthsList ~ Length(levelList); };  sumOfSquaresWithin := Sum((lengthsList - 1) * variancesList); grandMean := NM(Mean(meansList));  sumOfSquaresBetween := Sum(lengthsList*(meansList - grandMean)^2);     blockMeansList := [];  index := 1;  While(index <=? Length(First(levelsList)) ) { row := MatrixColumn(levelsList, index);  blockMeansList := Append(blockMeansList,NM(Mean(row)));  index++; };  b := Length(blockMeansList);  k := Length(levelsList);  sumOfSquaresBlock := Sum(j,1,b, k*(blockMeansList[j] - grandMean)^2);  sumOfSquaresTotal := NM(sumOfSquaresWithin + sumOfSquaresBetween);  sumOfSquaresWithin := NM(sumOfSquaresTotal - sumOfSquaresBetween - sumOfSquaresBlock);  meanSquareBetween := NM(sumOfSquaresBetween/(k - 1));  meanSquareWithin := NM(sumOfSquaresWithin/((k - 1)*(b - 1)));  fScore := NM(meanSquareBetween/meanSquareWithin);  meanSquareBlock := NM(sumOfSquaresBlock/(b - 1));  fScoreBlock := NM(meanSquareBlock/meanSquareWithin);  criticalFScore := ProbabilityToFScore(k - 1, (k - 1)*(b - 1), 1-alpha);  criticalFScoreBlock := ProbabilityToFScore(b - 1, (k - 1)*(b - 1), 1-alpha);    topOfPage :=\" <html> <title> Anova: Completely Randomized Block </title>  <body>\";  topOfSummary :=\" <h2>Anova: Completely Randomized Block</h2>  <TABLE BORDER> <CAPTION align=\\\"left\\\"> <h3>Summary</h3> </CAPTION>  <TR> <TH> Level </TH> <TH> Count</TH> <TH> Sum </TH> <TH> Mean </TH> <TH> Variance </TH> </TR>\";  summaryTableRows := \"\";  summaryTableRow := \"<TR> <TD> <?Write(ToAtom(ToString(Level)~ToString(index)));?> </TD> <TD align=\\\"right\\\"> <?Write(lengthsList[index]);?> </TD> <TD> <?Write(sumsList[index]);?> </TD> <TD> <?Write(meansList[index]);?> </TD> <TD> <?Write(variancesList[index]);?> </TD> </TR>\"~Nl();    index := 1;  While(index <=? Length(levelsList)) { summaryTableRows := summaryTableRows ~ PatchString(summaryTableRow);  index++; };    blockSummaryTableRow := \"<TR> <TD> <?Write(ToAtom(\\\"Block\\\"~ToString(index)));?> </TD> <TD align=\\\"right\\\"> <?Write(Length(row));?> </TD> <TD> <?Write(NM(Sum(row)));?> </TD> <TD> <?Write(NM(Mean(row)));?> </TD> <TD> <?Write(NM(UnbiasedVariance(row)));?> </TD> </TR>\"~Nl(); index := 1;  While(index <=? Length(First(levelsList)) ) { row := MatrixColumn(levelsList, index);  summaryTableRows := summaryTableRows ~ PatchString(blockSummaryTableRow);  index++; };      bottomOfSummary :=\" </TABLE>\";  topOfAnovaBlock :=\" <br \\> <br \\>  <TABLE BORDER> <CAPTION align=\\\"left\\\"> <h3>ANOVA: Completely Randomized Block</h3> </CAPTION>  <TR> <TH> Source of Variation </TH> <TH> Sum of Squares </TH> <TH> Degrees of Freedom </TH> <TH> Mean Square </TH> <TH> F </TH> <TH> F Critical </TH> </TR>\";   anovaBlockTableRow1 := PatchString(\"<TR> <TD> <?Write(ToAtom(\\\"Between Levels\\\"));?> </TD> <TD > <?Write(sumOfSquaresBetween);?> </TD> <TD> <?Write(k - 1);?> </TD> <TD > <?Write(meanSquareBetween);?> </TD><TD> <?Write(fScore);?> </TD> <TD> <?Write(criticalFScore);?> </TD> </TR>\"~Nl());  anovaBlockTableRow2 := PatchString(\"<TR> <TD> <?Write(ToAtom(\\\"Between Blocks\\\"));?> </TD> <TD > <?Write(sumOfSquaresBlock);?> </TD> <TD> <?Write(b - 1);?> </TD> <TD > <?Write(meanSquareBlock);?> </TD> <TD> <?Write(fScoreBlock);?> </TD> <TD> <?Write(criticalFScoreBlock);?> </TD> </TR>\"~Nl()); anovaBlockTableRow3 := PatchString(\"<TR> <TD> <?Write(ToAtom(\\\"Within Levels\\\"));?> </TD> <TD > <?Write(sumOfSquaresWithin);?> </TD> <TD> <?Write(b - 1);?> </TD> <TD > <?Write(meanSquareWithin);?> </TD></TR>\"~Nl()); bottomOfAnovaBlock :=\" </TABLE>\";  bottomOfPage :=\" </body>  </html>\";  htmlJavaString := JavaNew(\"java.lang.String\", topOfPage ~ topOfSummary ~ summaryTableRows ~ bottomOfSummary ~ topOfAnovaBlock ~ anovaBlockTableRow1 ~ anovaBlockTableRow2 ~ anovaBlockTableRow3 ~ bottomOfAnovaBlock ~ bottomOfPage);    result := [];  result[\"html\"] := htmlJavaString;  result[\"sumOfSquaresWithin\"] := sumOfSquaresWithin;  result[\"sumOfSquaresBetween\"] := sumOfSquaresBetween;  result[\"sumOfSquaresBlock\"] := sumOfSquaresBlock;  result[\"sumOfSquaresTotal\"] := sumOfSquaresTotal;  result[\"meanSquareBetween\"] := meanSquareBetween;  result[\"meanSquareWithin\"] := meanSquareWithin;  result[\"meanSquareBlock\"] := meanSquareBlock;  result[\"fScore\"] := fScore;  result[\"criticalFScore\"] := criticalFScore;  result[\"fScoreBlock\"] := fScoreBlock;  result[\"criticalFScoreBlock\"] := criticalFScoreBlock;  result;};";
        scriptMap.put("AnovaCompletelyRandomizedBlock",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "AnovaSingleFactor(levelsList, alpha) :={ Check(ListOfLists?(levelsList), \"Argument\", \"The first argument must be a list of lists.\");  Check(alpha >=? 0 And? alpha <=? 1, \"Argument\", \"The second argument must be a number between 0 and 1.\");  Local( anovaTableRow1, anovaTableRow2, anovaTableRow3, anovaTableTotal, bottomOfAnova, bottomOfPage, bottomOfSummary, criticalFScore, degreesOfFreedomBetween, degreesOfFreedomWithin, fScore, grandMean, htmlJavaString, index, lengthsList, meansList, meanSquareBetween, meanSquareWithin, result, summaryTableRow, summaryTableRows, sumOfSquaresBetween, sumOfSquaresTotal, sumOfSquaresWithin, sumsList, topOfAnova, topOfPage, topOfSummary, variancesList); meansList := [];  variancesList := [];  sumsList := [];  lengthsList := [];  ForEach(levelList, levelsList) { meansList := meansList ~ NM(Mean(levelList));  variancesList := variancesList ~ NM(UnbiasedVariance(levelList));  sumsList := sumsList ~ NM(Sum(levelList)); lengthsList := lengthsList ~ Length(levelList); };  sumOfSquaresWithin := Sum((lengthsList - 1) * variancesList); grandMean := NM(Mean(Flatten(levelsList, \"List\")));  sumOfSquaresBetween := Sum(lengthsList*(meansList - grandMean)^2);  sumOfSquaresTotal := NM(sumOfSquaresWithin + sumOfSquaresBetween);  degreesOfFreedomBetween := (Length(levelsList)-1);  degreesOfFreedomWithin := (ElementCount(levelsList) - Length(levelsList));  meanSquareBetween := NM(sumOfSquaresBetween/degreesOfFreedomBetween);  meanSquareWithin := NM(sumOfSquaresWithin/degreesOfFreedomWithin);  fScore := NM(meanSquareBetween/meanSquareWithin);  criticalFScore := ProbabilityToFScore(degreesOfFreedomBetween, degreesOfFreedomWithin, 1-alpha);  topOfPage :=\" <html> <title> Anova: Single Factor </title>  <body>\";  topOfSummary :=\" <h2>Anova: Single Factor</h2>  <TABLE BORDER> <CAPTION align=\\\"left\\\"> <h3>Summary</h3> </CAPTION>  <TR> <TH> Level </TH> <TH> Count</TH> <TH> Sum </TH> <TH> Mean </TH> <TH> Variance </TH> </TR>\";  summaryTableRows := \"\";  summaryTableRow := \"<TR> <TD> <?Write(ToAtom(\\\"Level\\\"~ToString(index)));?> </TD> <TD align=\\\"right\\\"> <?Write(lengthsList[index]);?> </TD> <TD> <?Write(sumsList[index]);?> </TD> <TD> <?Write(meansList[index]);?> </TD> <TD> <?Write(variancesList[index]);?> </TD> </TR>\"~Nl();  index := 1; While(index <=? Length(levelsList)) { summaryTableRows := summaryTableRows ~ PatchString(summaryTableRow);  index++; };   bottomOfSummary :=\" </TABLE>\";  topOfAnova :=\" <br \\> <br \\>  <TABLE BORDER> <CAPTION align=\\\"left\\\"> <h3>ANOVA</h3> </CAPTION>  <TR> <TH> Source of Variation </TH> <TH> Sum of Squares </TH> <TH> Degrees of Freedom </TH> <TH> Mean Square Between </TH> <TH> F </TH> <TH> F Critical </TH> </TR>\";   anovaTableRow1 := PatchString(\"<TR> <TD> <?Write(ToAtom(\\\"Between Levels\\\"));?> </TD> <TD > <?Write(sumOfSquaresBetween);?> </TD> <TD> <?Write(degreesOfFreedomBetween);?> </TD> <TD > <?Write(meanSquareBetween);?> </TD><TD> <?Write(fScore);?> </TD> <TD> <?Write(criticalFScore);?> </TD> </TR>\"~Nl());  anovaTableRow2 := PatchString(\"<TR> <TD> <?Write(ToAtom(\\\"Within Levels\\\"));?> </TD> <TD > <?Write(sumOfSquaresWithin);?> </TD> <TD> <?Write(degreesOfFreedomWithin);?> </TD> <TD > <?Write(meanSquareWithin);?> </TD></TR>\"~Nl());  anovaTableTotal := PatchString(\"<TR> <TD> Total </TD> <TD> <?Write(sumOfSquaresTotal);?> </TD> <TD> <?Write(degreesOfFreedomBetween + degreesOfFreedomWithin);?> </TD> </TR>\");  bottomOfAnova :=\" </TABLE>\";  bottomOfPage :=\" </body>  </html>\";  htmlJavaString := JavaNew(\"java.lang.String\", topOfPage ~  topOfSummary ~ summaryTableRows ~ bottomOfSummary ~ topOfAnova ~ anovaTableRow1 ~  anovaTableRow2 ~ anovaTableTotal ~  bottomOfAnova ~ bottomOfPage);    result := [];  result[\"html\"] := htmlJavaString;  result[\"sumOfSquaresWithin\"] := sumOfSquaresWithin;  result[\"sumOfSquaresBetween\"] := sumOfSquaresBetween;  result[\"sumOfSquaresTotal\"] := sumOfSquaresTotal;  result[\"degreesOfFreedomBetween\"] := degreesOfFreedomBetween;  result[\"degreesOfFreedomWithin\"] := degreesOfFreedomWithin;  result[\"meanSquareBetween\"] := meanSquareBetween;  result[\"meanSquareWithin\"] := meanSquareWithin;  result[\"fScore\"] := fScore;  result[\"criticalFScore\"] := criticalFScore;  result;};";
        scriptMap.put("AnovaSingleFactor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BinomialDistributionMean(probability,numberOfTrials) :={ Check(RationalOrNumber?(probability) And? p >=? 0 And? p <=? 1, \"Argument\", \"The first argument must be a number between 0 and 1.\");  Check(Integer?(numberOfTrials) And? numberOfTrials >=? 0, \"Argument\", \"The second argument must be an integer which is greater than 0.\");  numberOfTrials * probability;}; ";
        scriptMap.put("BinomialDistributionMean",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BinomialDistributionStandardDeviation(probability,numberOfTrials) :={ Check(RationalOrNumber?(probability) And? p >=? 0 And? p <=? 1, \"Argument\", \"The first argument must be a number between 0 and 1.\");  Check(Integer?(numberOfTrials) And? numberOfTrials >=? 0, \"Argument\", \"The second argument must be an integer which is greater than 0.\");  SqrtN(numberOfTrials * probability * (1 - probability));}; ";
        scriptMap.put("BinomialDistributionStandardDeviation",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ChiSquareScoreToAlpha(score, degreesOfFreedom) :={ Local(a, y, s, e, c, z, LogSqrtPi, ISqrtPi,result);   y := 0;  LogSqrtPi := 0.5723649429247000870717135;   ISqrtPi := 0.5641895835477562869480795;   If(score <=? 0.0 Or? degreesOfFreedom <? 1)  { result := 1.0; } Else { a := NM(0.5 * score);  If(degreesOfFreedom >? 1)  { y := Decide(-a <? -20, 0, ExpN(-a)); };  s := Decide(Even?(degreesOfFreedom), y , (2.0 * ZScoreToProbability(-SqrtN(score))));  If(degreesOfFreedom >? 2)  { score := 0.5 * (degreesOfFreedom - 1.0);  z := Decide(Even?(degreesOfFreedom), 1.0, 0.5);  If(a >? 20)  { e := Decide(Even?(degreesOfFreedom), 0.0, LogSqrtPi);  c := LogN(a);  While(z <=? score)  { e := LogN(z) + e; s := s + Decide(c * z - a - e <? -20, 0, ExpN(c * z - a - e)); z := z + 1.0; }; result := s; }  Else  { e := Decide(Even?(degreesOfFreedom) , 1.0, (ISqrtPi / SqrtN(a)));  c := 0.0;  While(z <=? score)  { e := e * (a / z); c := c + e; z := z + 1.0; };  result := c * y + s; }; } Else { result := s; };  };  NM(result);};";
        scriptMap.put("ChiSquareScoreToAlpha",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CoefficientOfDetermination(x,y) :={  Check(List?(x), \"Argument\", \"The first argument must be a list.\");  Check(List?(y), \"Argument\", \"The second argument must be a list.\");  Check(Length(x) =? Length(y), \"Argument\", \"The lists for argument 1 and argument 2 must have the same length.\");  NM(CorrelationCoefficient(x,y)^2);};";
        scriptMap.put("CoefficientOfDetermination",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ConfidenceIntervalOfTheMean(sampleMean,standardDeviation,standardDeviationIsKnown,sampleSize,confidenceLevel) :={ Check(Boolean?(standardDeviationIsKnown), \"Argument\", \"The third argument must be True or False.\");  Local(criticalZScore,criticalTScore,standardErrorOfTheMean,upperLimitValue,lowerLimitValue,resultList);  resultList := [];  Decide(sampleSize >=? 30 Or? standardDeviationIsKnown =? True, { criticalZScore := NM(ConfidenceLevelToZScore(confidenceLevel));  resultList[\"criticalZScore\"] := criticalZScore; standardErrorOfTheMean := NM(StandardErrorOfTheMean(standardDeviation,sampleSize));  lowerLimitValue := NM(sampleMean - criticalZScore * standardErrorOfTheMean);  upperLimitValue := NM(sampleMean + criticalZScore * standardErrorOfTheMean);  Decide(InVerboseMode(), { Echo(\"Using the normal distribution.\");  Echo(\"Critical z-score: \", criticalZScore);  Echo(\"Standard error of the mean: \", standardErrorOfTheMean); }); }, {  criticalTScore := OneTailAlphaToTScore(sampleSize - 1, NM((1 - confidenceLevel)/2));  resultList[\"criticalTScore\"] := criticalTScore;  standardErrorOfTheMean := NM(StandardErrorOfTheMean(standardDeviation,sampleSize));  lowerLimitValue := NM(sampleMean - criticalTScore * standardErrorOfTheMean);  upperLimitValue := NM(sampleMean + criticalTScore * standardErrorOfTheMean);   Decide(InVerboseMode(), { Echo(\"Using the t-distribution.\");  Echo(\"Critical t-score: \", criticalTScore);  Echo(\"Standard error of the mean: \", standardErrorOfTheMean); });  });  resultList[\"upperLimit\"] := upperLimitValue;  resultList[\"lowerLimit\"] := lowerLimitValue; resultList;};";
        scriptMap.put("ConfidenceIntervalOfTheMean",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ConfidenceIntervalOfTheProportion(numberOfSuccesses,sampleSize,confidenceLevel) :={ Check(Integer?(numberOfSuccesses) And? numberOfSuccesses >=? 0, \"Argument\", \"The first argument must be an integer which is >=?0\");  Check(Integer?(sampleSize) And? sampleSize >=? 0, \"Argument\", \"The second argument must be an integer which is >=?0\");  Local(criticalZScore,approximateStandardErrorOfTheProportion,upperLimit,lowerLimit,resultList,proportion);  resultList := [];  criticalZScore := ConfidenceLevelToZScore(confidenceLevel);  resultList[\"criticalZScore\"] := criticalZScore;  proportion := NM(numberOfSuccesses/sampleSize);  approximateStandardErrorOfTheProportion := Sqrt((proportion*(1 - proportion))/sampleSize);  upperLimit := NM(proportion + criticalZScore * approximateStandardErrorOfTheProportion);  lowerLimit := NM(proportion - criticalZScore * approximateStandardErrorOfTheProportion);  Decide(InVerboseMode(), { Echo(\"Critical z-score: \", criticalZScore);  Echo(\"Proportion: \", proportion);  Echo(\"Standard error of the proportion: \", NM(approximateStandardErrorOfTheProportion)); });  resultList[\"upperLimit\"] := upperLimit;  resultList[\"lowerLimit\"] := lowerLimit;  resultList;};";
        scriptMap.put("ConfidenceIntervalOfTheProportion",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ConfidenceLevelToZScore(probability) :={  probability := probability + (1 - probability)/2;  ProbabilityToZScore(probability);};";
        scriptMap.put("ConfidenceLevelToZScore",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ControlChartConstants(n) :={  Check(n >=? 2 And? n <=? 15, \"Argument\", \"The argument n must be 2 <=? n <=? 20.\");  Local(result, table);  result := [];  n--;  table := [ [1.880, 1.128, 0.000, 3.267], [1.023, 1.693, 0.000, 2.574], [0.729, 2.059, 0.000, 2.282], [0.577, 2.326, 0.000, 2.114], [0.483, 2.534, 0.000, 2.004], [0.419, 2.704, 0.076, 1.924], [0.373, 2.847, 0.136, 1.864], [0.337, 2.970, 0.184, 1.816], [0.308, 3.078, 0.223, 1.777], [0.285, 3.173, 0.256, 1.744], [0.266, 3.258, 0.283, 1.717], [0.249, 3.336, 0.307, 1.693], [0.235, 3.407, 0.328, 1.672], [0.223, 3.472, 0.347, 1.653], [0.212, 3.532, 0.363, 1.637], [0.203, 3.588, 0.378, 1.622], [0.194, 3.640, 0.391, 1.608], [0.187, 3.689, 0.403, 1.597], [0.180, 3.735, 0.415, 1.585], ]; result[\"D4\"] := table[n][4];  result[\"D3\"] := table[n][3];  result[\"d2\"] := table[n][2];  result[\"A2\"] := table[n][1];  result;};";
        scriptMap.put("ControlChartConstants",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CorrelationCoefficient(x,y) :={  Check(List?(x), \"Argument\", \"The first argument must be a list.\");  Check(List?(y), \"Argument\", \"The second argument must be a list.\");  Check(Length(x) =? Length(y), \"Argument\", \"The lists for argument 1 and argument 2 must have the same length.\");  Local(n);  n := Length(x);  NM((n*Sum(x*y)-Sum(x)*Sum(y))/Sqrt((n*Sum(x^2)-(Sum(x))^2)*(n*Sum(y^2)-(Sum(y)^2))) );};";
        scriptMap.put("CorrelationCoefficient",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CorrelationMatrix(dataLists) := { Local(namesList, correlationMatrix);  ForEach(dataList, dataLists) { Check(Matrix?(dataLists), \"Argument\", \"All lists must have the same number of elements.\"); }; namesList := MatrixColumn(dataLists,1);  namesList := \"\" ~ namesList;  ForEach(dataList, dataLists) { PopFront(dataList); };  correlationMatrix := ZeroMatrix(Length(dataLists)+1);  ForEach(rowIndex, 1 .. Length(dataLists) + 1) { ForEach(columnIndex, 1 .. Length(dataLists) + 1) { If(rowIndex >=? 2 And? columnIndex >=? 2) { correlationMatrix[rowIndex][columnIndex] := NM(CorrelationCoefficient(dataLists[rowIndex - 1],dataLists[columnIndex - 1]),2); } Else If(rowIndex =? 1) { correlationMatrix[rowIndex][columnIndex] := namesList[columnIndex]; } Else { correlationMatrix[rowIndex][columnIndex] := namesList[rowIndex]; }; }; };  correlationMatrix;};";
        scriptMap.put("CorrelationMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "D2Value(k,n) :={ Check(k >=? 0 And? k <=? 15, \"Argument\", \"The first argument k must be 0 <=? k <=? 15.\");  Check(n >=? 2 And? n <=? 15, \"Argument\", \"The second argument n must be 2 <=? n <=? 15.\");  n--;  If(k =? 0) { [1.128,1.693,2.059,2.326,2.534,2.704,2.847,2.970,3.078,3.173,3.259,3.336,3.407,3.472][n]; } Else { [ [1.414, 1.912, 2.239, 2.481, 2.673, 2.830, 2.963, 3.078, 3.179, 3.269, 3.350, 3.424, 3.491, 3.553], [1.279, 1.805, 2.151, 2.405, 2.604, 2.768, 2.906, 3.025, 3.129, 3.221, 3.305, 3.380, 3.449, 3.513], [1.231, 1.769, 2.120, 2.379, 2.581, 2.747, 2.886, 3.006, 3.112, 3.205, 3.289, 3.366, 3.435, 3.499], [1.206, 1.750, 2.105, 2.366, 2.570, 2.736, 2.877, 2.997, 3.103, 3.197, 3.282, 3.358, 3.428, 3.492], [1.191, 1.739, 2.096, 2.358, 2.563, 2.730, 2.871, 2.992, 3.098, 3.192, 3.277, 3.354, 3.424, 3.488], [1.181, 1.731, 2.090, 2.353, 2.558, 2.726, 2.867, 2.988, 3.095, 3.189, 3.274, 3.351, 3.421, 3.486], [1.173, 1.726, 2.085, 2.349, 2.555, 2.723, 2.864, 2.986, 3.092, 3.187, 3.272, 3.349, 3.419, 3.484], [1.168, 1.721, 2.082, 2.346, 2.552, 2.720, 2.862, 2.984, 3.090, 3.185, 3.270, 3.347, 3.417, 3.482], [1.164, 1.718, 2.080, 2.344, 2.550, 2.719, 2.860, 2.982, 3.089, 3.184, 3.269, 3.346, 3.416, 3.481], [1.160, 1.716, 2.077, 2.342, 2.549, 2.717, 2.859, 2.981, 3.088, 3.183, 3.268, 3.345, 3.415, 3.480], [1.157, 1.714, 2.076, 2.340, 2.547, 2.716, 2.858, 2.980, 3.087, 3.182, 3.267, 3.344, 3.415, 3.479], [1.155, 1.712, 2.074, 2.344, 2.546, 2.715, 2.857, 2.979, 3.086, 3.181, 3.266, 3.343, 3.414, 3.479], [1.153, 1.710, 2.073, 2.338, 2.545, 2.714, 2.856, 2.978, 3.085, 3.180, 3.266, 3.343, 3.413, 3.478], [1.151, 1.709, 2.072, 2.337, 2.545, 2.714, 2.856, 2.978, 3.085, 3.180, 3.265, 3.342, 3.413, 3.478], [1.150, 1.708, 2.071, 2.337, 2.544, 2.713, 2.855, 2.977, 3.084, 3.179, 3.265, 3.342, 3.412, 3.477] ][k][n]; }; };";
        scriptMap.put("D2Value",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ErrorFunction(x) :={ Local(a1,a2,a3,a4,a5,p,sign,t,y);  a1 := 0.254829592; a2 := -0.284496736; a3 := 1.421413741; a4 := -1.453152027; a5 := 1.061405429; p := 0.3275911;  sign := 1;  Decide(x <? 0, sign := -1);  x := AbsN(x);  t := 1.0/(1.0 + p*x); y := NM(1.0 - (((((a5*t + a4)*t) + a3)*t + a2)*t + a1)*t*Exp(-x*x)); sign*y;};";
        scriptMap.put("ErrorFunction",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Mode(list) :={ Check(Length(list) >? 0 And? NumericList?(list), \"Argument\", \"Argument must be a nonempty numeric list.\");  Local(noDuplicatesList, countsList, sortedList, highestCountsList, resultList);  noDuplicatesList := RemoveDuplicates(list);  countsList := [];  ForEach(element, noDuplicatesList) { countsList := Append(countsList, [Count(list, element), element] ); };  sortedList := HeapSort(countsList,Lambda([x,y],x[1] >? y[1]));  highestCountsList := Select(sortedList, Lambda([x],x[1] =? sortedList[1][1]));  resultList := MapSingle(Lambda([x],x[2]), highestCountsList);};";
        scriptMap.put("Mode",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Permutations(n) :={ Check(Integer?(n), \"Argument\", \"Argument must be an integer\"); n!;};Permutations(n, r) :={ Check(Integer?(n), \"Argument\", \"Argument 1 must be an integer\");  Check(Integer?(r), \"Argument\", \"Argument 2 must be an integer\");  n! /(n-r)!;};";
        scriptMap.put("Permutations",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ProbabilityToZScore(probability) :={ Local(ZMAX,ZEPSILON,minimumZ,maximumZ,zValue,probabilityValue);  probability := NM(probability);  Check(probability >=? 0.0 And? probability <=? 1.0, \"Argument\", \"The argument must be between 0 and 1.\");  ZMAX := 6;   ZEPSILON := 0.000001;   minimumZ := -ZMAX;  maximumZ := ZMAX;  zValue := 0.0; While ((maximumZ - minimumZ) >? ZEPSILON)  { probabilityValue := ZScoreToProbability(zValue);  If(probabilityValue >? probability)  { maximumZ := zValue; }  Else  { minimumZ := zValue; };  zValue := (maximumZ + minimumZ) * 0.5; };  zValue;};";
        scriptMap.put("ProbabilityToZScore",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Quartile(list) :={ sortedList := HeapSort(list,\"<?\");  secondQuartile := Median(sortedList);  Decide(Odd?(Length(sortedList)), {  secondQuartileIndex := Find(sortedList, secondQuartile);  leftList := Take(sortedList, secondQuartileIndex-1); rightList := Take(sortedList, -(Length(sortedList) - (secondQuartileIndex) ) ); }, { leftList := Take(sortedList, Length(sortedList)/2); rightList := Take(sortedList, -Length(sortedList)/2); } );  firstQuartile := Median(leftList);  thirdQuartile := Median(rightList);  interquartileRange := thirdQuartile - firstQuartile;  [firstQuartile, secondQuartile, thirdQuartile, interquartileRange];};";
        scriptMap.put("Quartile",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RandomPick(list) :={ Check(List?(list), \"Argument\", \"Argument must be a list.\");  Check(Length(list) >? 0, \"Argument\", \"The number of elements in the list must be greater than 0.\");  Local(pickPosition);  pickPosition := RandomInteger(Length(list)); list[pickPosition];};";
        scriptMap.put("RandomPick",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RandomPickVector(list, count) :={ Check(List?(list), \"Argument\", \"Argument 1 must be a list.\");  Check(Integer?(count), \"Argument\", \"Argument 2 must be an integer.\");  BuildList(RandomPick(list),x,1,count,1);};";
        scriptMap.put("RandomPickVector",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RandomPickWeighted(list) :={ Check(List?(list), \"Argument\", \"Argument must be a list.\");  Local(element, probabilities, items, lastWeight, randomNumber, result);  probabilities := 0;  items := [];  lastWeight := 0;     ForEach(element,list) {  probability := element[2]; probabilities := probabilities + probability; };  Check(probabilities =? 1, \"Argument\", \"The probabilities must sum to 1.\");     ForEach(element,list) { probability := element[2];  item := element[1];  items := Append(items, [item, [lastWeight, lastWeight := lastWeight + NM(probability)]] ); };     randomNumber := Random();  ForEach(itemData,items) { Decide(randomNumber >=? itemData[2][1] And? randomNumber <=? itemData[2][2], result := itemData[1] ); };    result;};";
        scriptMap.put("RandomPickWeighted",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Range(list) :={ Check(Length(list) >? 0 And? NumericList?(list), \"Argument\", \"Argument must be a nonempty numeric list.\");  Maximum(list) - Minimum(list);};";
        scriptMap.put("Range",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RegressionLine(x,y) :={  Check(List?(x), \"Argument\", \"The first argument must be a list.\");  Check(List?(y), \"Argument\", \"The second argument must be a list.\");  Check(Length(x) =? Length(y), \"Argument\", \"The lists for argument 1 and argument 2 must have the same length.\");  Local(n,a,b,xMean,yMean,line,result);  n := Length(x);  b := NM((n*Sum(x*y) - Sum(x)*Sum(y))/(n*Sum(x^2)-(Sum(x))^2));  xMean := NM(Mean(x));  yMean := NM(Mean(y));  a := NM(yMean - b*xMean);  line := a + b*Hold(x);  result := [];  result[\"xMean\"] := xMean;  result[\"yMean\"] := yMean;  result[\"line\"] := line;  result[\"yIntercept\"] := a;  result[\"slope\"] := b;  result[\"count\"] := n;  result;};";
        scriptMap.put("RegressionLine",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RegressionLineConfidenceInterval(x,y,xValue,confidenceLevel) :={  Check(List?(x), \"Argument\", \"The first argument must be a list.\");  Check(List?(y), \"Argument\", \"The second argument must be a list.\");  Check(Length(x) =? Length(y), \"Argument\", \"The lists for argument 1 and argument 2 must have the same length.\");  Check(confidenceLevel >=?0 And? confidenceLevel <=?1, \"Argument\", \"The confidence level must be >=? 0 and <=? 1.\");  Local(n,a,b,xMean,part,result,criticalTScore,standardErrorOfTheEstimate);  regressionLine := RegressionLine(x,y);  n := regressionLine[\"count\"];  f(x) := {Eval(regressionLine[\"line\"]);};  criticalTScore := OneTailAlphaToTScore(n-2, NM((1 - confidenceLevel)/2));  standardErrorOfTheEstimate := StandardErrorOfTheEstimate(x,y); xMean := regressionLine[\"xMean\"]; part := NM(criticalTScore * standardErrorOfTheEstimate * Sqrt(1/n + ((xValue - xMean)^2)/(Sum(x^2) - Sum(x)^2/n)));  result := [];  result[\"upper\"] := f(xValue) + part;  result[\"lower\"] := f(xValue) - part;  result;};";
        scriptMap.put("RegressionLineConfidenceInterval",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(count, iterations, body){RulebaseHoldArguments(\"Repeat\",[iterations,body]);RuleHoldArguments(\"Repeat\",2,10,Integer?(iterations) And? iterations >? 0){ Local(count); count := 0; While (iterations >? 0) { Eval(body); iterations--; count++; }; count;};RulebaseHoldArguments(\"Repeat\",[body]);RuleHoldArguments(\"Repeat\",1,20,True){ Local(count); count := 0; While (True) { Eval(body); count++; }; count;};};UnFence(\"Repeat\",2);HoldArgumentNumber(\"Repeat\",2,2);UnFence(\"Repeat\",1);HoldArgumentNumber(\"Repeat\",1,1);";
        scriptMap.put("Repeat",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Sample(list, sampleSize) :={ Check(List?(list), \"Argument\", \"The first argument must be a list.\");  Check(Integer?(sampleSize) And? sampleSize >? 0, \"Argument\", \"The second argument must be an integer which is greater than 0.\");  list := Shuffle(list); Take(list, sampleSize);};";
        scriptMap.put("Sample",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SampleSizeForTheMean(standardDeviation,confidenceLevel,marginOfError) :={ Local(minimumSampleSize);  zScore := ConfidenceLevelToZScore(confidenceLevel);  minimumSampleSize := NM(((zScore*standardDeviation)/marginOfError)^2);};";
        scriptMap.put("SampleSizeForTheMean",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SampleSizeForTheProportion(probabilityOfSuccess,confidenceLevel,marginOfError) :={ Check(probabilityOfSuccess >=?0 And? probabilityOfSuccess <=? 1, \"Argument\", \"The first argument must be between 0 and 1.\"); Local(minimumSampleSize,zScore);  zScore := ConfidenceLevelToZScore(confidenceLevel);  minimumSampleSize := NM(probabilityOfSuccess*(1 - probabilityOfSuccess)*(zScore/marginOfError)^2);};";
        scriptMap.put("SampleSizeForTheProportion",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ScheffeTest(levelsList, alpha) :={ Check(ListOfLists?(levelsList), \"Argument\", \"The first argument must be a list of lists.\");  Check(alpha >=? 0 And? alpha <=? 1, \"Argument\", \"The second argument must be a number between 0 and 1.\");  Local( result, topOfSummary, pairsList, xBarB, xBarA, summaryTableRow, ssw, nA, scheffeStatisticCalculated, nB, summaryList, topOfPage, htmlJavaString, summaryTableRows, meansList, index,b, pairList, a, bottomOfPage, k, countsList, oneComparisonList, scheffeStatistic, bottomOfSummary, resultList);  anova := AnovaSingleFactor(levelsList, alpha);  k := Length(levelsList);  scheffeStatisticCalculated := (k-1)*anova[\"criticalFScore\"];  resultList := [];  resultList[\"scheffeStatisticCalculated\"] := scheffeStatisticCalculated;  meansList := [];  countsList := [];  ForEach(levelList,levelsList) { meansList := meansList ~ NM(Mean(levelList));  countsList := countsList ~ Length(levelList); };  pairsList := CombinationsList(1 .. Length(levelsList),2);  summaryList := [];  index := 1;  ForEach(pairList, pairsList) {  a := pairList[1];  b := pairList[2];  xBarA := meansList[a];  nA := countsList[a];  xBarB := meansList[b];  nB := countsList[b];  ssw := anova[\"sumOfSquaresWithin\"];  scheffeStatistic := ScheffeStatistic(xBarA,nA,xBarB,nB,ssw,k,countsList);  oneComparisonList := [];  oneComparisonList[\"conclusion\"] := Decide(scheffeStatistic <=? scheffeStatisticCalculated, \"No Difference\", \"Difference\");  oneComparisonList[\"scheffeStatistic\"] := scheffeStatistic;  oneComparisonList[\"pair\"] := pairList;  summaryList[\"pair\" ~ ToString(index)] := oneComparisonList;  index++; };  resultList[\"summary\"] := summaryList;     topOfPage :=\" <html> <title> Scheffe Test Summary </title>  <body>\";  topOfSummary :=\" <h2>Scheffe Test Summary</h2>  <TABLE BORDER> <CAPTION align=\\\"left\\\"> <h3>Summary</h3> </CAPTION>  <TR> <TH> Sample Pair</TH> <TH> Measured Scheffe Statistic </TH> <TH> Calculated Scheffe Statistic </TH> <TH> Conclusion </TH> </TR>\";  summaryTableRows := \"\";  summaryTableRow := \"<TR> <TD align=\\\"center\\\"> <?Write(ToAtom(ToString(pairList[1]) ~ \\\" and \\\" ` ToString(pairList[2])));?> </TD> <TD align=\\\"right\\\"> <?Write(summary[\\\"scheffeStatistic\\\"]);?> </TD> <TD align=\\\"right\\\"> <?Write(resultList[\\\"scheffeStatisticCalculated\\\"]);?> </TD> <TD> <?Write(ToAtom(summary[\\\"conclusion\\\"]));?> </TD> </TR>\"~Nl();   ForEach(summary, Reverse(resultList[\"summary\"])) { summary := summary[2]; pairList := summary[\"pair\"];  summaryTableRows := summaryTableRows ~ PatchString(summaryTableRow);  index++; };   bottomOfSummary :=\" </TABLE>\";   bottomOfPage :=\" </body>  </html>\";  htmlJavaString := JavaNew(\"java.lang.String\", topOfPage ~ topOfSummary ~ summaryTableRows ~ bottomOfSummary ~ bottomOfPage);      resultList[\"html\"] := htmlJavaString;   DestructiveReverse(resultList);};ScheffeStatistic(xBarA,nA,xBarB,nB,ssw,k,countsList) :={ NM(((xBarA-xBarB)^2)/((ssw/Sum(i,1,k,(countsList[i] - 1))*(1/nA + 1/nB)))); };";
        scriptMap.put("ScheffeTest",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Shuffle(list) :={ Check(List?(list), \"Argument\", \"Argument must be a list.\");  Local(index, randomIndex, temporary);  list := FlatCopy(list);  index := Length(list);  While(index >? 1) { randomIndex := RandomInteger(1,index);  temporary := list[randomIndex];  list[randomIndex] := list[index];  list[index] := temporary;  index--; };   list;};";
        scriptMap.put("Shuffle",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ShuffledDeckNoSuits() := { Shuffle(Concat(1 .. 13, 1 .. 13, 1 .. 13, 1 .. 13));};";
        scriptMap.put("ShuffledDeckNoSuits",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "StandardErrorOfTheEstimate(xList,yList) :={  Check(List?(xList), \"Argument\", \"The first argument must be a list.\");  Check(List?(yList), \"Argument\", \"The second argument must be a list.\");  Check(Length(xList) =? Length(yList), \"Argument\", \"The lists for argument 1 and argument 2 must have the same length.\");  Local(n,a,b,regressionLine);  regressionLine := RegressionLine(xList,yList);  n := regressionLine[\"count\"];  a := regressionLine[\"yIntercept\"];  b := regressionLine[\"slope\"];  NM(Sqrt((Sum(yList^2) - a*Sum(yList) - b*Sum(xList*yList))/(n-2)));};";
        scriptMap.put("StandardErrorOfTheEstimate",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "StandardErrorOfTheMean(sigma, sampleSize) :={ Check(sigma >? 0, \"Argument\", \"The first argument must be a number which is greater than 0.\");  Check(Integer?(sampleSize) And? sampleSize >? 0, \"Argument\", \"The second argument must be an integer which is greater than 0.\");  sigma/Sqrt(sampleSize);};";
        scriptMap.put("StandardErrorOfTheMean",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "StandardErrorOfTheProportion(meanOfSampleProportions, sampleSize) :={ Check(RationalOrNumber?(meanOfSampleProportions), \"Argument\", \"The first argument must be a number.\");  Check(Integer?(sampleSize) And? sampleSize >? 0, \"Argument\", \"The second argument must be an integer which is greater than 0.\");  Sqrt((meanOfSampleProportions*(1 - meanOfSampleProportions))/sampleSize);};";
        scriptMap.put("StandardErrorOfTheProportion",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "StandardErrorOfTheSlope(xList,yList) :={  Check(List?(xList), \"Argument\", \"The first argument must be a list.\");  Check(List?(yList), \"Argument\", \"The second argument must be a list.\");  Check(Length(xList) =? Length(yList), \"Argument\", \"The lists for argument 1 and argument 2 must have the same length.\");  Local(standardErrorOfTheEstimate,n,xMean);  standardErrorOfTheEstimate := StandardErrorOfTheEstimate(xList,yList);  n := Length(xList);  xMean := Mean(xList);  NM(standardErrorOfTheEstimate/Sqrt(Sum(xList^2) - n*xMean^2));};";
        scriptMap.put("StandardErrorOfTheSlope",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Subset?(bigList, littleList) :={ Local(result); result := True;  ForEach(element, littleList) { Decide(Not? Contains?(bigList,element), result := False); }; result;};";
        scriptMap.put("Subset?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ValueToZScore(value,mean,standardDeviation) :={ (value - mean)/standardDeviation;};";
        scriptMap.put("ValueToZScore",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "WeightedMean(list) :={ Check(List?(list), \"Argument\", \"Argument must be a list.\");  Local( values, lastWeight, weights );  values := [];  weights := [];   ForEach(element,list) {  Check(List?(element), \"Argument\", \"Values and their associated weights must be in a list.\");  Check(Length(element) =? 2, \"Argument\", \"Each value and its associated weight must be in a two element list.\");  values := values ~ element[1]; weights := weights ~ element[2]; };  Sum(values * weights)/Sum(weights);};";
        scriptMap.put("WeightedMean",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ZScoreToProbability(zScore) :={ zScore := NM(zScore);  Local( y, x, w, ZMAX, result);  ZMAX := 6;   If(zScore =? 0.0) { x := 0.0; } Else { y := 0.5 * AbsN(zScore);  If(y >? ZMAX * 0.5) { x := 1.0; } Else If(y <? 1.0) { w := y * y; x := ((((((((0.000124818987 * w - 0.001075204047) * w + 0.005198775019) * w - 0.019198292004) * w + 0.059054035642) * w - 0.151968751364) * w + 0.319152932694) * w - 0.531923007300) * w + 0.797884560593) * y * 2.0; } Else { y := y - 2.0;  x := (((((((((((((-0.000045255659 * y + 0.000152529290) * y - 0.000019538132) * y - 0.000676904986) * y + 0.001390604284) * y - 0.000794620820) * y - 0.002034254874) * y + 0.006549791214) * y - 0.010557625006) * y + 0.011630447319) * y - 0.009279453341) * y + 0.005353579108) * y - 0.002141268741) * y + 0.000535310849) * y + 0.999936657524; }; };   Decide(zScore >? 0.0 , result := (x + 1.0) * 0.5 , result := (1.0 - x) * 0.5);  result;};";
        scriptMap.put("ZScoreToProbability",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ZScoreToValue(zScore, mean, standardDeviation) :={ -((-mean)/standardDeviation - zScore)*standardDeviation;};";
        scriptMap.put("ZScoreToValue",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "FastModularPower(a_PositiveInteger?, b_PositiveInteger?, n_PositiveInteger?) <--{ Local(p, j, r); p := a; j := b; r := 1; While (j >? 0) { Decide(Odd?(j), r := ModuloN(r*p, n)); p := ModuloN(p*p, n); j := ShiftRight(j, 1); }; r;};IsStronglyProbablyPrime(b_PositiveInteger?, n_PositiveInteger?) <--{ Local(m, q, r, a, flag, i, root); m := n-1; q := m; r := 0; root := 0;  While (Even?(q)) { q := ShiftRight(q, 1); r++; }; a := FastModularPower(b, q, n); flag := (a =? 1 Or? a =? m); i := 1; While (Not?(flag) And? (i <? r)) { root := a;  a := ModuloN(a*a, n); flag := (a =? m); i++; }; [root, flag]; };10 # RabinMillerSmall(1) <-- False;10 # RabinMillerSmall(2) <-- True;20 # RabinMillerSmall(n_Even?) <-- False;20 # RabinMillerSmall(3) <-- True;30 # RabinMillerSmall(n_PositiveInteger?) <--{ Local(continue, prime, i, primetable, pseudotable, root); continue := True; prime := True; i := 1; primetable := [2, 3, 5, 7, 11, 13, 17]; pseudotable := [2047, 1373653, 25326001, 3215031751, 2152302898747, 3474749660383, 34155071728321];  While (continue And? prime And? (i <? 8)) {  [root, prime] := IsStronglyProbablyPrime(primetable[i], n);  continue := (n >=? pseudotable[i]); i++; };  Decide(continue And? (i =? 8), Overflow, prime);};10 # RabinMillerProbabilistic(1, _p) <-- False;10 # RabinMillerProbabilistic(2, _p) <-- True;20 # RabinMillerProbabilistic(n_Even?, _p) <-- False;20 # RabinMillerProbabilistic(3, _p) <-- True;30 # RabinMillerProbabilistic(n_PositiveInteger?, p_PositiveInteger?) <--{ Local(k, prime, b, rootsofminus1, root); k := 1+IntLog(IntLog(n,2),4)+p;  b := 1; prime := True; rootsofminus1 := [0];  While (prime And? k>?0) { b := NextPseudoPrime(b);  [root, prime] := IsStronglyProbablyPrime(b, n); Decide(prime, rootsofminus1 := Union(rootsofminus1, [root])); Decide(Length(rootsofminus1)>?3, prime := False);  Decide(  InVerboseMode() And? Length(rootsofminus1)>?3, {  Local(factor); rootsofminus1 := Difference(rootsofminus1,[0]);  factor := Gcd(n, Decide( rootsofminus1[1]+rootsofminus1[2]=?n, rootsofminus1[1]+rootsofminus1[3], rootsofminus1[1]+rootsofminus1[2] )); Echo(n, \" = \", factor, \" * \", n/factor); } ); k--; }; prime;};RabinMiller(n_PositiveInteger?) <--{  Decide( n <? 34155071728321, RabinMillerSmall(n), RabinMillerProbabilistic(n, 40)  );};";
        scriptMap.put("RabinMiller",scriptString);
        scriptMap.put("FastModularPower",scriptString);
        scriptMap.put("IsStronglyProbablyPrime",scriptString);
        scriptMap.put("RabinMillerSmall",scriptString);
        scriptMap.put("RabinMillerProbabilistic",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RandomInteger(_n) <--{ Check(PositiveInteger?(n), \"Argument\", \"The argument must be a positive integer.\"); CeilN(Random() * n);};10 # RandomInteger(_lowerBoundInclusive, _upperBoundInclusive) <--{ Check(Integer?(lowerBoundInclusive) And? Integer?(upperBoundInclusive), \"Argument\", \"Both arguments must be integers.\"); Check(lowerBoundInclusive <? upperBoundInclusive, \"Argument\", \"The first argument must be less than the second argument.\"); FloorN(lowerBoundInclusive + Random() * (upperBoundInclusive + 1 - lowerBoundInclusive) );};";
        scriptMap.put("RandomInteger",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RandomIntegerList(_count,_coefmin,_coefmax) <-- BuildList(FloorN(coefmin+Random()*(coefmax+1-coefmin)),i,1,count,1);";
        scriptMap.put("RandomIntegerList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RandomIntegerMatrix(_rows,_cols,_coefmin,_coefmax) <-- BuildMatrix([[i,j], FloorN(coefmin+Random()*(coefmax+1-coefmin))], rows, cols );";
        scriptMap.put("RandomIntegerMatrix",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RandomInterestingPolynomial( deg_PositiveInteger?, _var ) <--{ RandomSeed( SystemTimer() );  NewRandomPoly(deg,var); };10 # NewRandomPoly( _deg, _var )::(Equal?(deg,1)) <--{ Local(p,i1,i2); i1 := RandomInteger(1,10); i2 := RandomInteger(-10,10); p := NormalForm(UniVariate(var,0,[i2,i1]));};10 # NewRandomPoly( _deg, _var )::(Equal?(deg,2)) <--{ Local(ii,i1,i2,p,quadPoly); p := FillList(0,2); For(ii:=1,ii<=?2,ii++) { i1 := RandomInteger(10); i2 := RandomInteger(-10,10); Decide( i1 >? 1, i2 := i1*i2 ); p[ii] := NormalForm(UniVariate(var,0,[i2,i1])); }; quadPoly := ExpandBrackets(p[1]*p[2]); quadPoly := Simplify(Quotient(quadPoly,LeadingCoef(quadPoly)));};10 # RandomIrreducibleQuadratic( _var ) <--{ Local(ii,coeffs,discrim,u,p,f);     Decide(RandomInteger(2)=?1, RandomIrreducibleQuadraticWithComplexRoots(var), RandomIrreducibleQuadraticWithRealRoots(var) );};10 # RandomIrreducibleQuadraticWithRealRoots(_var) <--{ Local(coeffs,ijk); coeffs := FillList(1,3); coeffs[2] := RandomInteger(-10,10); coeffs[3] := RandomInteger(1,10); ijk := Floor(coeffs[2]^2 / (4*coeffs[3])); coeffs[1] := RandomInteger(-10,ijk); discrim := coeffs[2]^2-4*coeffs[1]*coeffs[3]; NormalForm(UniVariate(var,0,coeffs));};10 # RandomIrreducibleQuadraticWithComplexRoots(_var) <--{ Local(coeffs,ijk); coeffs := [1,RandomInteger(-10,10),RandomInteger(1,10)]; coeffs[1] := Ceil(NM(coeffs[2]^2/(4*coeffs[3]))) + RandomInteger(1,5); NormalForm(UniVariate(var,0,coeffs));};10 # NewRandomPoly( _deg, _var )::(Equal?(deg,3)) <--{ Local(ii,i1,i2,i3,p,CubicPoly); p := FillList(1,3); Decide( RandomInteger(3) =? 1, { For(ii:=1,ii<=?3,ii++) { i1 := RandomInteger(2); i2 := RandomInteger(-10,10); Decide( i1 >? 1, i2 := i1*i2 ); p[ii] := NormalForm(UniVariate(var,0,[i2,i1])); }; }, { i1 := RandomInteger(2); i2 := RandomInteger(-10,10); Decide( i1 >? 1, i2 := i1*i2 ); p[1] := NormalForm(UniVariate(var,0,[i2,i1])); p[2] := RandomIrreducibleQuadratic(var); } ); CubicPoly := ExpandBrackets(Product(p));};10 # NewRandomPoly( _deg, _var )::(Equal?(deg,4)) <--{ Local(ii,i1,i2,i3,i4,p,QuarticPoly); p := FillList(1,4); Decide( RandomInteger(2) =? 1, { p[1] := NewRandomPoly(3,x); i1 := RandomInteger(2); i2 := RandomInteger(-10,10); Decide( i1 >? 1, i2 := i1*i2 ); p[2] := NormalForm(UniVariate(var,0,[i2,i1])); }, { p[1] := NewRandomPoly(2,x); p[2] := NewRandomPoly(2,x); } ); QuarticPoly := ExpandBrackets(Product(p));};10 # NewRandomPoly( _deg, _var )::(Equal?(deg,5)) <--{ Local(ii,i1,i2,i3,i4,p,QuinticPoly); p := FillList(1,4); p[1] := NewRandomPoly(1,x); p[2] := RandomIrreducibleQuadraticWithRealRoots(x); p[3] := RandomIrreducibleQuadraticWithComplexRoots(x); QuinticPoly := ExpandBrackets(Product(p));};11 # NewRandomPoly( deg_PositiveInteger?, _var )::(deg >? 5) <--{ Local(p,n,m); p := []; m := deg; Until( m <? 3 ) { n := RandomInteger(2,Floor(NM(deg/2))); Tell(\" \",[m,n]); Push(p,NewRandomPoly(n,var)); m := m - n; }; Tell(\" \",m); Decide( m >? 0, Push(p,NewRandomPoly(m,x))); Expand(Product(p));};";
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
        scriptString[1] = "LocalSymbols(knownRNGEngines, knownRNGDists) { knownRNGEngines := [ [ \"default\", \"RNGEngineLCG2\"], [ \"advanced\", \"RNGEngineLEcuyer\"], ]; knownRNGDists := [ [\"default\", \"FlatRNGDist\"], [\"flat\", \"FlatRNGDist\"],  [\"gauss\", \"GaussianRNGDist\"], ]; KnownRNGDists() := knownRNGDists; KnownRNGEngines() := knownRNGEngines;};Function() RngCreate();Function() RngCreate(seed, ...);Function() RngSeed(r, seed);Function() Rng(r);RngCreate() <-- RngCreate(0);10 # RngCreate(aseed_Integer?) <-- (RngCreate @ [seed: aseed]);20 # RngCreate(_key: _value) <-- `(RngCreate([@key: value]));30 # RngCreate(options_List?) <--{ options := OptionsListToHash @ [options];  Decide( options[\"seed\"] =? Empty Or? options[\"seed\"] <=? 0, options[\"seed\"] := 76544321  ); Decide( options[\"engine\"] =? Empty Or? Not? (Assert(\"warning\", [\"RngCreate: invalid engine\", options[\"engine\"]]) KnownRNGEngines()[options[\"engine\"] ] !=? Empty), options[\"engine\"] := \"default\" ); Decide( options[\"dist\"] =? Empty Or? Not? (Assert(\"warning\", [\"RngCreate: invalid distribution\", options[\"dist\"]]) KnownRNGDists()[options[\"dist\"] ] !=? Empty), options[\"dist\"] := \"default\" );   [ KnownRNGDists()[options[\"dist\"] ], KnownRNGEngines()[options[\"engine\"] ],  KnownRNGEngines()[options[\"engine\"] ] @ [ options[\"seed\"] ] ];};Rng(_r) <--{ Local(state, result); [state, result] := (r[1] @ [r]);  DestructiveReplace(r, 3, state);  result; };RngSeed(_r, seed_Integer?) <--{ Local(state); (Assert(\"warning\", [\"RngSeed: seed must be positive\", seed]) seed >? 0 ) Or? (seed:=76544321); state := (r[2] @ [seed]);  DestructiveReplace(r, 3, state);  True;};FlatRNGDist(_r) <-- (r[2] @ [r[3]]); GaussianRNGDist(_rng) <--{    Local(a,b,m, newstate, rnumber); newstate := rng[3];  m:=0; While(m=?0 Or? m>=?1)  { [newstate, rnumber] := (rng[2] @ [newstate]); a:=2*rnumber-1; [newstate, rnumber] := (rng[2] @ [newstate]); b:=2*rnumber-1; m:=a*a+b*b; }; [newstate, (a+I*b)*SqrtN(-2*DivideN(InternalLnNum(m),m))];};RNGEngineLCG1(seed_Integer?) <-- [seed];RNGEngineLCG1(state_List?) <-- LCG1(state);RNGEngineLCG2(seed_Integer?) <-- [seed];RNGEngineLCG2(state_List?) <-- LCG2(state);RNGEngineLCG3(seed_Integer?) <-- [seed];RNGEngineLCG3(state_List?) <-- LCG3(state);RNGEngineLCG4(seed_Integer?) <-- [seed];RNGEngineLCG4(state_List?) <-- LCG4(state);LCG1(state) := RandomLCG(state, 2147483647,950706376,0);LCG2(state) := RandomLCG(state, 4294967296,1099087573,0);LCG3(state) := RandomLCG(state, 281474976710656,68909602460261,0);LCG4(state) := RandomLCG(state, 18014398509481984,2783377640906189,0);RandomLCG(_state, _im, _ia, _ic) <--[ DestructiveReplace(state,1, ModuloN(state[1]*ia+ic,im)), DivideN(state[1], im) ];RNGEngineLEcuyer(aseed_Integer?) <--{  Local(rngaux, result); rngaux := (RngCreate @ [aseed]);  result:=ZeroVector(6);  Local(i); For(i:=1, i<=?6, i++) { Rng(rngaux); result[i] := rngaux[3][1];  };  result;};RNGEngineLEcuyer(state_List?) <--{ Local(newstate, result); newstate := [ Modulo(1403580*state[2]-810728*state[3], 4294967087), state[1], state[2], Modulo(527612*state[4]-1370589*state[6], 4294944433), state[4], state[5] ]; result:=Modulo(state[1]-state[4], 4294967087); [ newstate, DivideN(Decide(result=?0, 4294967087, result), 4294967088) ];};LocalSymbols(RandSeed) {  RandSeed := SystemTimer();   Function(\"RandomSeed\", [seed]) Assign(RandSeed, seed);  RandomLCG(_im, _ia, _ic) <-- { RandSeed:=ModuloN(RandSeed*ia+ic,im); DivideN(RandSeed,im);  };}; Function(\"Random1\",[]) RandomLCG(4294967296,1103515245,12345);Function(\"Random6\",[]) RandomLCG(1771875,2416,374441);Function(\"Random2\",[]) RandomLCG(2147483647,950706376,0);Function(\"Random3\",[]) RandomLCG(4294967296,1099087573,0);Function(\"Random4\",[]) RandomLCG(281474976710656,68909602460261,0);Function(\"Random5\",[]) RandomLCG(18014398509481984,2783377640906189,0);Function(\"Random\",[]) Random3();";
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
        scriptString[1] = "10 # Combine(expr_Zero?) <-- 0;20 # Combine(_expr) <-- { Local(L); L := ReassembleListTerms(DisassembleExpression(expr)); UnFlatten(L,\"+\",0);};";
        scriptMap.put("Combine",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Eliminate(_var,_replace,_function) <-- Simplify(Substitute(var,replace)function);";
        scriptMap.put("Eliminate",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(AssembleTerms, AssembleTermsRecursive){ AssembleTerms(list) := { Check(List?(list), \"Argument\", \"The argument must be a list.\"); Decide(Length(list) =? 1, First(list), AssembleTermsRecursive(Reverse(list))  ); }; AssembleTermsRecursive(list) := { Decide(Type(list[1]) =? \"-\" Or? NegativeNumber?(list[1]) Or? Type(list[1]) =? \"/\" And? (Type(Numerator(list[1])) =? \"-\" Or? NegativeNumber?(Numerator(list[1]))), Decide(Length(list) =? 2, ListToFunction([ToAtom(\"-\"), list[2], -list[1]] ), ListToFunction([ToAtom(\"-\"), AssembleTermsRecursive(Rest(list)), -First(list)] ) ), Decide(Length(list) =? 2, ListToFunction([ToAtom(\"+\"), list[2], list[1]] ), ListToFunction([ToAtom(\"+\"), AssembleTermsRecursive(Rest(list)), First(list)] ) ) ); };10 # ExpandBrackets(xx_Zero?) <-- 0;20 # ExpandBrackets(_xx)::(Type(xx)=?\"/\" Or? Type(-xx)=?\"/\") <--{ Local(N,D,t); N := ReassembleListTerms(DisassembleExpression(Numerator(xx))); D := ExpandBrackets(Denominator(xx)); AssembleTerms(MapSingle([[t], t / D], N));};30 # ExpandBrackets(_xx) <-- AssembleTerms(ReassembleListTerms(DisassembleExpression(xx)));};";
        scriptMap.put("ExpandBrackets",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # ExpandFrac(expr_List?) <-- MapSingle(\"ExpandFrac\", expr);10 # ExpandFrac(_expr)::Not?(HasFunctionSome?(expr, \"/\", [ToAtom(\"+\"), ToAtom(\"-\"), *, /, ^])) <-- expr;15 # ExpandFrac(a_RationalOrNumber?) <-- a;20 # ExpandFrac(_expr) <-- ExpandFraccombine(GetNumerDenom(expr));ExpandFraccombine([_a, _b]) <-- a/b;";
        scriptMap.put("ExpandFrac",scriptString);
        scriptMap.put("ExpandFraccombine",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"DoFlatten\",[doflattenx]);UnFence(\"DoFlatten\",1);10 # DoFlatten(_doflattenx)::(Type(doflattenx)=?flattenoper) <-- Apply(\"Concat\",MapSingle(\"DoFlatten\",Rest(FunctionToList(doflattenx))));20 # DoFlatten(_doflattenx) <-- [ doflattenx ];Function(\"Flatten\",[body,flattenoper]){ DoFlatten(body);};";
        scriptMap.put("Flatten",scriptString);
        scriptMap.put("DoFlatten",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "GetNumerDenom(_expr, _a) <-- GetNumerDenom(expr)*[a,1];10 # GetNumerDenom(_expr)::Not?(HasFunctionSome?(expr, \"/\", [ToAtom(\"+\"), ToAtom(\"-\"), *, /, ^])) <-- [expr, 1];15 # GetNumerDenom(a_RationalOrNumber?) <-- [a, 1];20 # GetNumerDenom(_a + _b) <-- ExpandFracadd(GetNumerDenom(a), GetNumerDenom(b));20 # GetNumerDenom(_a - _b) <-- ExpandFracadd(GetNumerDenom(a), GetNumerDenom(b, -1));20 # GetNumerDenom(- _a) <-- GetNumerDenom(a, -1);20 # GetNumerDenom(+ _a) <-- GetNumerDenom(a);20 # GetNumerDenom(_a * _b) <-- ExpandFracmultiply(GetNumerDenom(a), GetNumerDenom(b));20 # GetNumerDenom(_a / _b) <-- ExpandFracdivide(GetNumerDenom(a), GetNumerDenom(b));20 # GetNumerDenom(_a ^ b_Integer?)::(b >? 1) <-- ExpandFracmultiply(GetNumerDenom(a), GetNumerDenom(a^(b-1)));20 # GetNumerDenom(_a ^ b_Integer?)::(b <? -1) <-- ExpandFracdivide(GetNumerDenom(1), GetNumerDenom(a^(-b)));20 # GetNumerDenom(_a ^ b_Integer?)::(b =? -1) <-- ExpandFracdivide(GetNumerDenom(1), GetNumerDenom(a));25 # GetNumerDenom(_a ^ _b) <-- [a^b, 1];ExpandFracadd([_a, _b], [_c, _d]) <-- [a*d+b*c, b*d];ExpandFracmultiply([_a, _b], [_c, _d]) <-- [a*c, b*d];ExpandFracdivide([_a, _b], [_c, _d]) <-- [a*d, b*c];";
        scriptMap.put("GetNumerDenom",scriptString);
        scriptMap.put("ExpandFracadd",scriptString);
        scriptMap.put("ExpandFracmultiply",scriptString);
        scriptMap.put("ExpandFracdivide",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"SimpAdd\",[x,y]);";
        scriptMap.put("SimpAdd",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"SimpDiv\",[x,y]);";
        scriptMap.put("SimpDiv",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # SimpExpand(SimpAdd(_x,_y)) <-- SimpExpand(x) + SimpExpand(y);10 # SimpExpand(SimpMul(_x,_y)) <-- SimpExpand(x) * SimpExpand(y);10 # SimpExpand(SimpDiv(_x,_y)) <-- SimpExpand(x) / SimpExpand(y);20 # SimpExpand(_x) <-- x;";
        scriptMap.put("SimpExpand",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # SimpFlatten((_x)+(_y)) <-- SimpAdd(SimpFlatten(x),SimpFlatten(y));10 # SimpFlatten((_x)-(_y)) <-- SimpAdd(SimpFlatten(x),SimpMul(-1,SimpFlatten(y)));10 # SimpFlatten( -(_y)) <-- SimpMul(-1,SimpFlatten(y));10 # SimpFlatten((_x)*(_y)) <-- SimpMul(SimpFlatten(x),SimpFlatten(y));10 # SimpFlatten((_x)/(_y)) <-- SimpDiv(SimpFlatten(x),SimpFlatten(y));10 # SimpFlatten((_x)^(n_PositiveInteger?)) <-- SimpMul(SimpFlatten(x),SimpFlatten(x^(n-1)));100 # SimpFlatten(_x) <--{ x;};";
        scriptMap.put("SimpFlatten",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # SimpImplode(SimpMul(SimpAdd(_x,_y),_z)) <-- SimpImplode(SimpAdd(SimpImplode(SimpMul(x,z)), SimpImplode(SimpMul(y,z))));10 # SimpImplode(SimpMul(_z,SimpAdd(_x,_y))) <-- SimpImplode(SimpAdd(SimpImplode(SimpMul(z,x)), SimpImplode(SimpMul(z,y))));10 # SimpImplode(SimpDiv(SimpAdd(_x,_y),_z)) <-- SimpImplode(SimpAdd(SimpImplode(SimpDiv(x,z)), SimpImplode(SimpDiv(y,z))));20 # SimpImplode(SimpAdd(_x,_y)) <-- SimpAdd(SimpImplode(x),SimpImplode(y));20 # SimpImplode(SimpMul(_x,_y)) <-- SimpMul(SimpImplode(x),SimpImplode(y));20 # SimpImplode(SimpDiv(_x,_y)) <-- SimpDiv(SimpImplode(x),SimpImplode(y));30 # SimpImplode(_x) <-- x;";
        scriptMap.put("SimpImplode",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"SimpMul\",[x,y]);";
        scriptMap.put("SimpMul",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Simplify(expr_List?) <-- MapSingle(\"Simplify\",expr);15 # Simplify(Complex(_r,_i)) <-- Complex(Simplify(r),Simplify(i));20 # Simplify((_xex) =? (_yex)) <-- (Simplify(xex-yex) =? 0);20 # Simplify((_xex) >? (_yex)) <-- (Simplify(xex-yex) >? 0);20 # Simplify((_xex) <? (_yex)) <-- (Simplify(xex-yex) <? 0);20 # Simplify((_xex) >=? (_yex)) <-- (Simplify(xex-yex) >=? 0);20 # Simplify((_xex) <=? (_yex)) <-- (Simplify(xex-yex) <=? 0);20 # Simplify((_xex) !=? (_yex)) <-- (Simplify(xex-yex) !=? 0);25 # Simplify(If(_a) _b) <-- \"If\" @ [Simplify(a), Simplify(b)];25 # Simplify(_a Else _b) <-- \"Else\" @ [Simplify(a), Simplify(b)];40 # Simplify(_expr)::(Type(expr)=?\"Ln\") <--{  LnCombine(expr);};40 # Simplify(_expr)::(Type(expr)=?\"Exp\") <--{  expr;};50 # Simplify(_expr) <-- {  MultiSimp(Eval(expr));};";
        scriptMap.put("Simplify",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # UnFlatten([],_op,_identity) <-- identity;20 # UnFlatten(list_List?,_op,_identity) <-- Apply(op,[First(list),UnFlatten(Rest(list),op,identity)]);";
        scriptMap.put("UnFlatten",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"BubbleSort\",[list,compare]){ Local(i,j,length,left,right);     list:=FlatCopy(list); length:=Length(list);  For (j:=length,j>?1,j--) { For(i:=1,i<?j,i++) { left:=list[i]; right:=list[i+1];   Decide(Not?(Apply(compare,[left,right])), { DestructiveInsert(DestructiveDelete(list,i),i+1,left); } );  }; }; list;};";
        scriptMap.put("BubbleSort",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BubbleSortIndexed( L_List?, _compare ) <--{ Local(list,indices,pairs,sortedPairs); list := FlatCopy(L); indices := BuildList(i,i,1,Length(list),1); pairs := Transpose(list~[indices]); sortedPairs := Decide( Apply(compare,[3,5]),  BubbleSort( pairs, Lambda([X,Y],X[1] <? Y[1]) ), BubbleSort( pairs, Lambda([X,Y],X[1] >? Y[1]) ) ); Transpose(sortedPairs);};";
        scriptMap.put("BubbleSortIndexed",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "HeapSort(L_List?, _compare) <-- { Local(list); list := FlatCopy(L); HeapSort1(list, ArrayCreate(Length(list), 0), 1, Length(list), compare);};1 # HeapSort1(_L, _tmplist, _first, _last, _compare):: (last - first <=? 2) <-- SmallSort(L, first, last, compare);2 # HeapSort1(_L, _tmplist, _first, _last, _compare) <--{    Local(mid, ileft, iright, pleft);  mid := first+((last-first)>>1); HeapSort1(L, tmplist, first, mid, compare); HeapSort1(L, tmplist, mid+1, last, compare);  For(ileft := first, ileft <=? mid, ileft++) tmplist[ileft] := L[ileft]; For( {ileft := first; pleft := first; iright := mid+1;}, ileft <=? mid,  pleft++  )     Decide(  iright>?last Or? Apply(compare,[tmplist[ileft],L[iright]]), {  L[pleft] := tmplist[ileft]; ileft++; }, {  L[pleft] := L[iright]; iright++; } ); L;};";
        scriptMap.put("HeapSort",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "HeapSortIndexed( L_List?, _compare ) <-- HeapSortIndexed1( FlatCopy(L), compare );HeapSortIndexed1( L_List?, _compare ) <--{ Local(list,indices,pairs,sortedPairs); list := FlatCopy(L); indices := BuildList(i,i,1,Length(list),1); pairs := Transpose(list~[indices]); SortedPairs := []; sortedPairs := Decide( Apply(compare,[3,5]),  HeapSort( pairs, Lambda([X,Y],X[1] <? Y[1]) ), HeapSort( pairs, Lambda([X,Y],X[1] >? Y[1]) ) ); Transpose(sortedPairs);};";
        scriptMap.put("HeapSortIndexed",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SmallSort(_list, _first, _last, _compare):: (last=?first) <-- list;SmallSort(_list, _first, _last, _compare):: (last=?first+1) <--{ Local(temp); temp := list[first]; Decide( Apply(compare,[temp,list[last]]), list, { list[first] := list[last]; list[last] := temp; }  ); list;};SmallSort(_list, _first, _last, _compare):: (last=?first+2) <--{ Local(temp); temp := list[first]; Decide( Apply(compare,[list[first+1],temp]), { list[first] := list[first+1]; list[first+1] := temp; }  );  temp := list[last]; Decide( Apply(compare,[list[first],temp]), Decide(  Apply(compare,[list[first+1],temp]), list, { list[last] := list[first+1]; list[first+1] := temp; }  ), {  list[last] := list[first+1]; list[first+1] := list[first]; list[first] := temp; } ); list;};";
        scriptMap.put("SmallSort",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SmallSortIndexed(_list, _first, _last, _compare)::(last=?first) <-- list;SmallSortIndexed(_list, _first, _last, _compare)::(last=?first+1) <--{ Local(temp); temp := list[first]; Decide( Apply(compare,[temp[1],list[last][1]]), list, { list[first] := list[last]; list[last] := temp; }  ); list;};SmallSortIndexed(_list, _first, _last, _compare)::(last=?first+2) <--{ Local(temp); temp := list[first]; Decide( Apply(compare,[list[first+1][1],temp[1]]), { list[first] := list[first+1]; list[first+1] := temp; }  );  temp := list[last]; Decide( Apply(compare,[list[first][1],temp[1]]), Decide(  Apply(compare,[list[first+1][1],temp[1]]), list, { list[last] := list[first+1]; list[first+1] := temp; }  ), {  list[last] := list[first+1]; list[first+1] := list[first]; list[first] := temp; } ); list;};";
        scriptMap.put("SmallSortIndexed",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " 10 # Sort( L_List? )::(Length(L) <? 20) <-- BubbleSort( L, \"<?\" );15 # Sort( L_List?, _compare )::(Length(L) <? 20) <--  BubbleSort( L, compare );20 # Sort( L_List? ) <-- HeapSort( L, \"<?\" );25 # Sort( L_List?, _compare ) <-- HeapSort( L, compare ); 10 # SortIndexed( L_List? )::(Length(L) <? 20) <-- BubbleSortIndexed( L, \"<?\" );15 # SortIndexed( L_List?, _compare )::(Length(L) <? 20) <-- BubbleSortIndexed( L, compare );20 # SortIndexed( L_List? ) <-- HeapSortIndexed( L, \"<?\" );25 # SortIndexed( L_List?, _compare ) <-- HeapSortIndexed( L, compare );";
        scriptMap.put("Sort",scriptString);
        scriptMap.put("SortIndexed",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "GeometricMean(list) := { Check(List?(list), \"Argument\", \"Argument must be a list.\");  Product(list)^(1/Length(list));};";
        scriptMap.put("GeometricMean",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Mean(list) := { Check(List?(list), \"Argument\", \"Argument must be a list.\");  Sum(list)/Length(list);};";
        scriptMap.put("Mean",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Median(list) :={ Check(List?(list), \"Argument\", \"Argument must be a list.\");  Local(sx,n,n2);   sx := HeapSort(list,\"<?\");  n := Length(list);  n2 := (n>>1);  Decide(Modulo(n,2) =? 1, sx[n2+1], (sx[n2]+sx[n2+1])/2);};";
        scriptMap.put("Median",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "StandardDeviation(list) := { Check(List?(list), \"Argument\", \"Argument must be a list.\"); Sqrt(UnbiasedVariance(list));};";
        scriptMap.put("StandardDeviation",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "UnbiasedVariance(list) := { Check(List?(list), \"Argument\", \"Argument must be a list.\");  Sum((list - Mean(list))^2)/(Length(list)-1);};";
        scriptMap.put("UnbiasedVariance",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Variance(list) := { Check(List?(list), \"Argument\", \"Argument must be a list.\"); Sum((list - Mean(list))^2)/Length(list);};";
        scriptMap.put("Variance",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BernoulliDistribution(p_RationalOrNumber?)::(p<?0 Or? p>?1) <-- Undefined;";
        scriptMap.put("BernoulliDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BinomialDistribution(_p, _n):: (Decide(RationalOrNumber?(p),p<?0 Or? p>?1, False) Or? (Constant?(n) And? Not? PositiveInteger?(n)) ) <-- Undefined;";
        scriptMap.put("BinomialDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ChiSquareDistribution(m_RationalOrNumber?)::(m<=?0) <-- Undefined;";
        scriptMap.put("ChiSquareDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ContinuousUniformDistribution(a_RationalOrNumber?, b_RationalOrNumber?)::(a>=?b) <-- Undefined;";
        scriptMap.put("ContinuousUniformDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "DiscreteDistribution( dom_RationalOrNumber? , prob_RationalOrNumber?) <-- Undefined;";
        scriptMap.put("DiscreteDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "DiscreteUniformDistribution(a_RationalOrNumber?, b_RationalOrNumber?)::(a>=?b) <-- Undefined;";
        scriptMap.put("DiscreteUniformDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ExponentialDistribution(l_RationalOrNumber?)::(l<?0) <-- Undefined;";
        scriptMap.put("ExponentialDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "FDistribution(_n1, _n2) <-- Undefined;";
        scriptMap.put("FDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "GeometricDistribution(p_RationalOrNumber?)::(p<?0 Or? p>?1) <-- Undefined;";
        scriptMap.put("GeometricDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "HypergeometricDistribution(N_RationalOrNumber?, M_RationalOrNumber?, n_RationalOrNumber?)::(M >? N Or? n >? N) <-- Undefined;";
        scriptMap.put("HypergeometricDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "NormalDistribution( _m , s2_RationalOrNumber?)::(s2<=?0) <-- Undefined;";
        scriptMap.put("NormalDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "PoissonDistribution(l_RationalOrNumber?)::(l<=?0) <-- Undefined;";
        scriptMap.put("PoissonDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "tDistribution(m_RationalOrNumber?)::(Not? PositiveInteger?(m)) <-- Undefined;";
        scriptMap.put("tDistribution",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Retract(ChiSquareTest,*);10 # ChiSquareTest( observedFrequenciesMatrix_Matrix?, expectedFrequenciesMatrix_Matrix?) <--{ Local(observedFrequenciesList, expectedFrequenciesList);  observedFrequenciesList := Flatten(observedFrequenciesMatrix,\"List\");  expectedFrequenciesList := Flatten(expectedFrequenciesMatrix,\"List\");  Check(Length(observedFrequenciesList) >? 0, \"Argument\", \"The first argument must be a nonempty matrix.\");  Check(Length(expectedFrequenciesList) >? 0, \"Argument\", \"The second argument must be a nonempty matrix.\");  Check(Length(expectedFrequenciesList) =? Length(expectedFrequenciesList), \"Argument\", \"The matrices must be of equal length.\"); Local( numerator, chi2, pValue, categoriesCount, degreesOfFreedom, resultList);  resultList := []; categoriesCount := Length(observedFrequenciesList); numerator := (observedFrequenciesList - expectedFrequenciesList)^2;  chi2 := Sum(i,1,categoriesCount,numerator[i]/(expectedFrequenciesList[i])); degreesOfFreedom := (Dimensions(observedFrequenciesMatrix)[1] - 1)*(Dimensions(observedFrequenciesMatrix)[2] - 1); pValue := 1-NM(IncompleteGamma(degreesOfFreedom/2,chi2/2)/Gamma(degreesOfFreedom/2)); resultList[\"degreesOfFreedom\"] := degreesOfFreedom;  resultList[\"pValue\"] := pValue;  resultList[\"chiSquareScore\"] := chi2;  NM(resultList);};20 # ChiSquareTest( observedFrequenciesList_List?, expectedFrequenciesList_List?) <--{ Check(Length(observedFrequenciesList) >? 0, \"Argument\", \"The first argument must be a nonempty list.\");  Check(Length(expectedFrequenciesList) >? 0, \"Argument\", \"The second argument must be a nonempty list.\");  Check(Length(expectedFrequenciesList) =? Length(expectedFrequenciesList), \"Argument\", \"The lists must be of equal length.\"); Local( numerator, chi2, pValue, categoriesCount, degreesOfFreedom, resultList);  resultList := []; categoriesCount := Length(observedFrequenciesList); numerator := (observedFrequenciesList - expectedFrequenciesList)^2;  chi2 := Sum(i,1,categoriesCount,numerator[i]/(expectedFrequenciesList[i])); degreesOfFreedom := categoriesCount - 1; pValue := 1-NM(IncompleteGamma(degreesOfFreedom/2,chi2/2)/Gamma(degreesOfFreedom/2)); resultList[\"degreesOfFreedom\"] := degreesOfFreedom;  resultList[\"pValue\"] := pValue;  resultList[\"chiSquareScore\"] := chi2;  NM(resultList);};";
        scriptMap.put("ChiSquareTest",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Regress(x,y) :={ Local(xy,x2,i,mx,my); mx := Mean(x); my := Mean(y); xy := Add((x-mx)*(y-my)); x2 := Add((x-mx)^2); [alpha <- (my-xy*mx/x2) , beta <- xy/x2];};";
        scriptMap.put("Regress",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "2 # ArcCos(x_Number?)::NumericMode?() <-- InternalPi()/2-ArcSin(x); 200 # ArcCos(0) <-- Pi/2;200 # ArcCos(1/2) <-- Pi/3;200 # ArcCos(Sqrt(1/2)) <-- Pi/4;200 # ArcCos(Sqrt(3/4)) <-- Pi/6;200 # ArcCos(1) <-- 0;200 # ArcCos(_n)::(n =? -1) <-- Pi;200 # ArcCos(_n)::(-n =? Sqrt(3/4)) <-- 5/6*Pi;200 # ArcCos(_n)::(-n =? Sqrt(1/2)) <-- 3/4*Pi;200 # ArcCos(_n)::(-n =? 1/2) <-- 2/3*Pi;200 # ArcCos(Undefined) <-- Undefined;ArcCos(xlist_List?) <-- MapSingle(\"ArcCos\",xlist);110 # ArcCos(Complex(_r,_i)) <-- (- I)*Ln(Complex(r,i) + (Complex(r,i)^2 - 1)^(1/2));";
        scriptMap.put("ArcCos",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ArcCosh(_x)::(NumericMode?() And? (Number?(x) Or? Type(x) =? \"Complex\")) <-- NM(Eval( Ln(x+Sqrt(x^2-1)) ));200 # ArcCosh(Infinity) <-- Infinity;200 # ArcCosh(-Infinity) <-- Infinity+I*Pi/2;200 # ArcCosh(Undefined) <-- Undefined;ArcCosh(xlist_List?) <-- MapSingle(\"ArcCosh\",xlist);";
        scriptMap.put("ArcCosh",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "2 # ArcSin(x_Number?)::(NumericMode?() And? Abs(x)<=?1) <-- ArcSinNum(x);3 # ArcSin(x_Number?)::NumericMode?() <-- Sign(x)*(Pi/2+I*ArcCosh(x));110 # ArcSin(Complex(_r,_i)) <-- (- I) * Ln((I*Complex(r,i)) + ((1-(Complex(r,i)^2))^(1/2)));150 # ArcSin(- _x)::(Not? Constant?(x)) <-- -ArcSin(x);160 # (ArcSin(x_Constant?))::(NegativeNumber?(NM(Eval(x)))) <-- -ArcSin(-x);200 # ArcSin(0) <-- 0;200 # ArcSin(1/2) <-- Pi/6;200 # ArcSin(Sqrt(1/2)) <-- Pi/4;200 # ArcSin(Sqrt(3/4)) <-- Pi/3;200 # ArcSin(1) <-- Pi/2;200 # ArcSin(_n)::(n =? -1) <-- -Pi/2;200 # ArcSin(_n)::(-n =? Sqrt(3/4)) <-- -Pi/3;200 # ArcSin(_n)::(-n =? Sqrt(1/2)) <-- -Pi/4;200 # ArcSin(_n)::(-n =? 1/2) <-- -Pi/6;ArcSin(xlist_List?) <-- MapSingle(\"ArcSin\",xlist);200 # ArcSin(Undefined) <-- Undefined;";
        scriptMap.put("ArcSin",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ArcSinh(_x)::(NumericMode?() And? (Number?(x) Or? Type(x) =? \"Complex\")) <-- NM(Eval( Ln(x+Sqrt(x^2+1)) ));200 # ArcSinh(Infinity) <-- Infinity;200 # ArcSinh(-Infinity) <-- -Infinity;200 # ArcSinh(Undefined) <-- Undefined;ArcSinh(xlist_List?) <-- MapSingle(\"ArcSinh\",xlist);";
        scriptMap.put("ArcSinh",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # (ArcTan(x_Constant?))::(NegativeNumber?(NM(Eval(x)))) <-- -ArcTan(-x);4 # ArcTan(-Tan(_x)) <-- -ArcTan(Tan(x));110 # ArcTan(Complex(_r,_i)) <-- (- I*0.5)*Ln(Complex(1,Complex(r,i))/ Complex(1, - Complex(r,i)));150 # ArcTan(- _x)::(Not? Constant?(x)) <-- -ArcTan(x);160 # (ArcTan(x_Constant?))::(NegativeNumber?(NM(Eval(x)))) <-- -ArcTan(-x);200 # ArcTan(Sqrt(3)) <-- Pi/3;200 # ArcTan(-Sqrt(3)) <-- -Pi/3;200 # ArcTan(1) <-- Pi/4;200 # ArcTan(0) <-- 0;200 # ArcTan(_n)::(n =? -1) <-- -Pi/4;200 # ArcTan(Infinity) <-- Pi/2;200 # ArcTan(-Infinity) <-- -Pi/2;200 # ArcTan(Undefined) <-- Undefined;ArcTan(xlist_List?) <-- MapSingle(\"ArcTan\",xlist);2 # ArcTan(x_Number?)::NumericMode?() <-- ArcTanNum(x);";
        scriptMap.put("ArcTan",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ArcTanh(_x)::(NumericMode?() And? (Number?(x) Or? Type(x) =? \"Complex\")) <-- NM(Eval( Ln((1+x)/(1-x))/2 ));200 # ArcTanh(Infinity) <-- Infinity+I*Pi/2;200 # ArcTanh(-Infinity) <-- -Infinity-I*Pi/2; 200 # ArcTanh(Undefined) <-- Undefined;ArcTanh(xlist_List?) <-- MapSingle(\"ArcTanh\",xlist);";
        scriptMap.put("ArcTanh",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1 # CosMap( _n )::(Not?(RationalOrNumber?(n))) <-- ListToFunction([ToAtom(\"Cos\"),n*Pi]);2 # CosMap( _n )::(n<?0) <-- CosMap(-n);2 # CosMap( _n )::(n>?2) <-- CosMap(Modulo(n,2));3 # CosMap( _n )::(n>?1) <-- CosMap(2-n);4 # CosMap( _n )::(n>?1/2) <-- -CosMap(1-n);5 # CosMap( 0 ) <-- 1;5 # CosMap( 1/6 ) <-- Sqrt(3)/2;5 # CosMap( 1/4 ) <-- Sqrt(2)/2;5 # CosMap( 1/3 ) <-- 1/2;5 # CosMap( 1/2 ) <-- 0;5 # CosMap( 2/5 ) <-- (Sqrt(5)-1)/4;10 # CosMap(_n) <-- ListToFunction([ToAtom(\"Cos\"),n*Pi]);2 # Cos(x_Number?)::NumericMode?() <-- CosNum(x);4 # Cos(ArcCos(_x)) <-- x;4 # Cos(ArcSin(_x)) <-- Sqrt(1-x^2);4 # Cos(ArcTan(_x)) <-- 1/Sqrt(1+x^2);5 # Cos(- _x)::(Not? Constant?(x)) <-- Cos(x);6 # (Cos(x_Constant?))::(NegativeNumber?(NM(Eval(x)))) <-- Cos(-x);110 # Cos(Complex(_r,_i)) <-- (Exp(I*Complex(r,i)) + Exp(- I*Complex(r,i))) / (2) ;6 # Cos(x_Infinity?) <-- Undefined;6 # Cos(Undefined) <-- Undefined;200 # Cos(v_CanBeUni(Pi))::(Not?(NumericMode?()) And? Degree(v,Pi) <? 2 And? Coef(v,Pi,0) =? 0) <-- CosMap(Coef(v,Pi,1));400 # Cos(x_RationalOrNumber?) <-- { Local(ll); ll:= FloorN(NM(Eval(x/Pi))); Decide(Even?(ll),x:=(x - Pi*ll),x:=(-x + Pi*(ll+1))); ListToFunction([Cos,x]); };400 # Cos(x_RationalOrNumber?) <-- { Local(ll); ll:= FloorN(NM(Eval(Abs(x)/Pi))); Decide(Even?(ll),x:=(Abs(x) - Pi*ll),x:=(-Abs(x) + Pi*(ll+1))); ListToFunction([Cos,x]); };Cos(xlist_List?) <-- MapSingle(\"Cos\",xlist);";
        scriptMap.put("Cos",scriptString);
        scriptMap.put("CosMap",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # Cosh(- _x) <-- Cosh(x);200 # Cosh(0) <-- 1;200 # Cosh(Infinity) <-- Infinity;200 # Cosh(-Infinity) <-- Infinity;200 # Cosh(ArcCosh(_x)) <-- x;200 # Cosh(ArcSinh(_x)) <-- Sqrt(1+x^2);200 # Cosh(ArcTanh(_x)) <-- 1/Sqrt(1-x^2);200 # Cosh(Undefined) <-- Undefined;Cosh(xlist_List?) <-- MapSingle(\"Cosh\",xlist);2 # Cosh(_x)::(NumericMode?() And? (Number?(x) Or? Type(x) =? \"Complex\")) <-- NM(Eval( (Exp(x)+Exp(-x))/2 ));";
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
        scriptString[1] = "2 # Exp(x_Number?)::NumericMode?() <-- ExpNum(x);4 # Exp(Ln(_x)) <-- x;110 # Exp(Complex(_r,_i)) <-- Exp(r)*(Cos(i) + I*Sin(i));200 # Exp(0) <-- 1;200 # Exp(-Infinity) <-- 0;200 # Exp(Infinity) <-- Infinity;200 # Exp(Undefined) <-- Undefined;Exp(xlist_List?) <-- MapSingle(\"Exp\",xlist);";
        scriptMap.put("Exp",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "2 # Ln(0) <-- -Infinity;2 # Ln(1) <-- 0;2 # Ln(Infinity) <-- Infinity;2 # Ln(Undefined) <-- Undefined;2 # Ln(x_NegativeNumber?)::NumericMode?() <-- Complex(Ln(-x), Pi);3 # Ln(x_Number?)::(NumericMode?() And? x>=?1) <-- InternalLnNum(x);4 # Ln(Exp(_x)) <-- x;3 # Ln(Complex(_r,_i)) <-- Complex(Ln(Abs(Complex(r,i))), Arg(Complex(r,i)));4 # Ln(x_NegativeNumber?) <-- Complex(Ln(-x), Pi);5 # Ln(x_Number?)::(NumericMode?() And? x<?1) <-- - InternalLnNum(DivideN(1, x));Ln(xlist_List?) <-- MapSingle(\"Ln\",xlist);";
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
        scriptString[1] = "1 # SinMap( _n )::(Not?(RationalOrNumber?(n))) <-- ListToFunction([ToAtom(\"Sin\"),n*Pi]);2 # SinMap( _n )::(n<?0) <-- -SinMap(-n);2 # SinMap( _n )::(n>?2) <-- SinMap(Modulo(n,2));3 # SinMap( _n )::(n>?1) <-- SinMap(n-2);4 # SinMap( _n )::(n>?1/2) <-- SinMap(1-n);5 # SinMap( n_Integer? ) <-- 0;5 # SinMap( 1/6 ) <-- 1/2;5 # SinMap( 1/4 ) <-- Sqrt(2)/2;5 # SinMap( 1/3 ) <-- Sqrt(3)/2;5 # SinMap( 1/2 ) <-- 1;5 # SinMap( 1/10) <-- (Sqrt(5)-1)/4;10 # SinMap(_n) <-- ListToFunction([ToAtom(\"Sin\"),n*Pi]);2 # Sin(x_Number?)::NumericMode?() <-- SinNum(x);4 # Sin(ArcSin(_x)) <-- x;4 # Sin(ArcCos(_x)) <-- Sqrt(1-x^2);4 # Sin(ArcTan(_x)) <-- x/Sqrt(1+x^2);5 # Sin(- _x)::(Not? Constant?(x)) <-- -Sin(x);6 # (Sin(x_Constant?))::(NegativeNumber?(NM(Eval(x)))) <-- -Sin(-x);6 # Sin(x_Infinity?) <-- Undefined;6 # Sin(Undefined) <-- Undefined;110 # Sin(Complex(_r,_i)) <-- (Exp(I*Complex(r,i)) - Exp(- I*Complex(r,i))) / (I*2) ;200 # Sin(v_CanBeUni(Pi))::(Not?(NumericMode?()) And? Degree(v,Pi) <? 2 And? Coef(v,Pi,0) =? 0) <--{ SinMap(Coef(v,Pi,1));};Sin(xlist_List?) <-- MapSingle(\"Sin\",xlist);";
        scriptMap.put("Sin",scriptString);
        scriptMap.put("SinMap",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "2 # Sinh(_x)::(NumericMode?() And? (Number?(x) Or? Type(x) =? \"Complex\")) <-- NM(Eval( (Exp(x)-Exp(-x))/2 ));5 # Sinh(- _x) <-- -Sinh(x);5 # Sinh(- _x) <-- -Sinh(x);200 # Sinh(0) <-- 0;200 # Sinh(Infinity) <-- Infinity;200 # Sinh(-Infinity) <-- -Infinity;200 # Sinh(ArcSinh(_x)) <-- x;200 # Sinh(ArcCosh(_x)) <-- Sqrt((x-1)/(x+1))*(x+1);200 # Sinh(ArcTanh(_x)) <-- x/Sqrt(1-x^2);200 # Sinh(Undefined) <-- Undefined;Sinh(xlist_List?) <-- MapSingle(\"Sinh\",xlist);";
        scriptMap.put("Sinh",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1 # TanMap( _n )::(Not?(RationalOrNumber?(n))) <-- ListToFunction([ToAtom(\"Tan\"),n*Pi]);2 # TanMap( _n )::(n<?0) <-- -TanMap(-n);2 # TanMap( _n )::(n>?1) <-- TanMap(Modulo(n,1));4 # TanMap( _n )::(n>?1/2) <-- -TanMap(1-n);5 # TanMap( 0 ) <-- 0;5 # TanMap( 1/6 ) <-- 1/3*Sqrt(3);5 # TanMap( 1/4 ) <-- 1;5 # TanMap( 1/3 ) <-- Sqrt(3);5 # TanMap( 1/2 ) <-- Infinity;10 # TanMap(_n) <-- ListToFunction([ToAtom(\"Tan\"),n*Pi]);2 # Tan(x_Number?)::NumericMode?() <-- TanNum(x);4 # Tan(ArcTan(_x)) <-- x;4 # Tan(ArcSin(_x)) <-- x/Sqrt(1-x^2);4 # Tan(ArcCos(_x)) <-- Sqrt(1-x^2)/x;5 # Tan(- _x)::(Not? Constant?(x)) <-- -Tan(x);6 # (Tan(x_Constant?))::(NegativeNumber?(NM(Eval(x)))) <-- -Tan(-x);6 # Tan(Infinity) <-- Undefined;6 # Tan(Undefined) <-- Undefined;110 # Tan(Complex(_r,_i)) <-- Sin(Complex(r,i))/Cos(Complex(r,i));200 # Tan(v_CanBeUni(Pi))::(Not?(NumericMode?()) And? Degree(v,Pi) <? 2 And? Coef(v,Pi,0) =? 0) <-- TanMap(Coef(v,Pi,1));Tan(xlist_List?) <-- MapSingle(\"Tan\",xlist);";
        scriptMap.put("Tan",scriptString);
        scriptMap.put("TanMap",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "2 # Tanh(_x)::(NumericMode?() And? (Number?(x) Or? Type(x) =? \"Complex\")) <-- NM(Eval( Sinh(x)/Cosh(x) ));200 # Tanh(0) <-- 0;200 # Tanh(Infinity) <-- 1;200 # Tanh(-Infinity) <-- -1;200 # Tanh(ArcTanh(_x)) <-- x;200 # Tanh(ArcSinh(_x)) <-- x/Sqrt(1+x^2);200 # Tanh(ArcCosh(_x)) <-- Sqrt((x-1)/(x+1))*(x+1)/x;200 # Tanh(Undefined) <-- Undefined;Tanh(xlist_List?) <-- MapSingle(\"Tanh\",xlist);";
        scriptMap.put("Tanh",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ArcCosN(x) :={ FastArcCos(x);};";
        scriptMap.put("ArcCosN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Defun(\"ArcSinN\",[int1]){ Local(result,eps); Assign(result,FastArcSin(int1)); Local(x,q,s,c); Assign(q,SubtractN(SinN(result),int1)); Assign(eps,MathIntPower(10,MathNegate(BuiltinPrecisionGet()))); While(GreaterThan?(AbsN(q),eps)) { Assign(s,SubtractN(int1,SinN(result))); Assign(c,CosN(result)); Assign(q,DivideN(s,c)); Assign(result,AddN(result,q)); }; result;};";
        scriptMap.put("ArcSinN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ArcTanN(x) :={ FastArcTan(x);};";
        scriptMap.put("ArcTanN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ArcTanNTaylor(x) :={ Local(numterms); numterms := QuotientN(BuiltinPrecisionGet()*1068, -643*MathBitCount(x))+1;   x*SumTaylorNum(-MultiplyN(x,x), [[k], 1/(2*k+1)], numterms);};";
        scriptMap.put("ArcTanNTaylor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = " 10 # BisectSqrt(0) <-- 0;10 # BisectSqrt(1) <-- 1;20 # BisectSqrt(N_PositiveInteger?) <--{ Local(l2,u,v,u2,v2,uv2,n);  u := N; l2 := MathBitCount(u)-1;     l2 := l2>>1;  u := 1 << l2; u2 := u << l2;  While( l2 !=? 0 ) { l2--;  v := 1<<l2; v2 := v<<l2;    uv2 := u<<(l2 + 1);   n := u2 + uv2 + v2;     If( n <=? N ) { u := u+v;  u2 := n;  }; }; u; };";
        scriptMap.put("BisectSqrt",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "BitsToDigits(n, base) := FloorN(0.51+n*NM(Ln(2)/Ln(base),10));";
        scriptMap.put("BitsToDigits",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CosNDoubling(value, n) :={ Local(shift, result); shift := n; result := value; While (shift>?0)  { result := MultiplyN(MathMul2Exp(result, 1), 2 - result); shift--; }; result;};";
        scriptMap.put("CosNDoubling",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CosNTaylor(x) :={ Local(numterms, prec, Bx); prec := QuotientN(BuiltinPrecisionGet()*3919, 1702);  Bx := -QuotientN(MathBitCount(x)*1143, 1649)-2;  numterms := QuotientN( QuotientN( prec-1, QuotientN( MathBitCount( prec-1)*1588, 2291)+Bx), 2)+1;  SumTaylorNum(MultiplyN(x,x), 1, [[k], -1/(2*k*(2*k-1))], numterms);};";
        scriptMap.put("CosNTaylor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "DigitsToBits(n, base) := FloorN(0.51+n*NM(Ln(base)/Ln(2),10));";
        scriptMap.put("DigitsToBits",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ExpNDoubling1(value, n) :={ Local(shift, result); shift := n; result := value; While (shift>?0)  { result := MathMul2Exp(result, 1) + MultiplyN(result, result); shift--; }; result;};";
        scriptMap.put("ExpNDoubling",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ExpNTaylor(x) :={ Local(numterms, prec, Bx); prec := QuotientN(BuiltinPrecisionGet()*3919, 1702);  Bx := -QuotientN(MathBitCount(x)*1143, 1649)-2;  numterms := QuotientN( prec-1, QuotientN( MathBitCount( prec-1)*1588, 2291)+Bx)+1;  x*SumTaylorNum(x, 1, [[k], 1/(k+1)], numterms);};";
        scriptMap.put("ExpNTaylor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # MathBitCount(0) <-- 1;20 # MathBitCount(_x):: (x<?0) <-- MathBitCount(-x);30 # MathBitCount(_value) <--{ Local(nbits); nbits:=0; Decide(value<?1, {  nbits := 1; While(value<?1) { nbits--; value := MathMul2Exp(value,1); }; }, {  While(value>=?1) { nbits++; value := MathMul2Exp(value, -1); }; }); nbits;};";
        scriptMap.put("MathBitCount",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MathLnDoubling(value, n) :={ Local(shift, result); shift := n; result := value; While (shift>?0)  { result := MultiplyN(result, result); shift--; }; result;};";
        scriptMap.put("MathLnDoubling",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MathLnTaylor(x) :={ Local(numterms, y); y := x-1; numterms := QuotientN(BuiltinPrecisionGet()*2136, -643*MathBitCount(y))+1;   y*SumTaylorNum(-y, [[k], 1/(k+1)], numterms);};";
        scriptMap.put("MathLnTaylor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "MathSqrtFloat(_A) <--{ Local(bitshift, a0, x0, x0sq, targetbits, subtargetbits, gotbits, targetprec); bitshift := ShiftRight(MathBitCount(A)-1,1);  targetprec := BuiltinPrecisionGet();  a0 := MathMul2Exp(A, -bitshift*2);  BuiltinPrecisionSet(10);    targetbits := Minimum(DigitsToBits(targetprec, 10), 1+GetExactBitsN(A));  x0 := DivideN(14+22*a0, 31+5*a0);  gotbits := 7;  subtargetbits := QuotientN(targetbits+8, 9); Decide(gotbits >=? subtargetbits, subtargetbits := QuotientN(targetbits+2, 3)); Decide(gotbits >=? subtargetbits, subtargetbits := targetbits*4);  While(gotbits <? targetbits) { gotbits := 3*gotbits+1;   Decide(gotbits >=? subtargetbits, {  gotbits := subtargetbits; subtargetbits := targetbits*4;  }); BuiltinPrecisionSet(BitsToDigits(gotbits, 10)+2);  x0 := SetExactBitsN(x0, gotbits+6);  x0sq := MultiplyN(x0, x0); x0 := AddN(x0, MultiplyN(x0*2, DivideN(a0-x0sq, a0+3*x0sq))); };  x0 := SetExactBitsN(MathMul2Exp(x0, bitshift), gotbits); BuiltinPrecisionSet(targetprec); x0;};";
        scriptMap.put("MathSqrtFloat",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SinNTaylor(x) :={ Local(numterms, prec, Bx); prec := QuotientN(BuiltinPrecisionGet()*3919, 1702);  Bx := -QuotientN(MathBitCount(x)*1143, 1649)-2;  numterms := QuotientN( QuotientN( prec+Bx, QuotientN( MathBitCount( prec+Bx)*1588, 2291)+Bx)+1, 2)+1;  x*SumTaylorNum(MultiplyN(x,x), 1, [[k], -1/(2*k*(2*k+1))], numterms);};";
        scriptMap.put("SinNTaylor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SinNTripling(value, n) :={ Local(shift, result); shift := n; result := value; While (shift>?0)  {  result := MultiplyN(result, 3 - MathMul2Exp(MultiplyN(result,result), 2) ); shift--; }; result;};";
        scriptMap.put("SinNTripling",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SqrtN(x) := MathSqrt1(x); 10 # MathSqrt1(0) <-- 0;100 # MathSqrt1(_x) <-- { Echo(\"SqrtN: invalid argument: \", x); False;};30 # MathSqrt1(x_PositiveNumber?) <-- x*NewtonNum([[r], r+r*(1-x*r^2)/2], FastPower(x,-0.5), 4, 2);30 # MathSqrt1(x_PositiveNumber?) <-- MathSqrtFloat(x);20 # MathSqrt1(x_Integer?):: (GreaterThan?(x,0)) <--{ Local(result); Decide(ModuloN(x,4)<?2 And? ModuloN(x,3)<?2 And? ModuloN(x+1,5)<?3,  {  GlobalPush(BuiltinPrecisionGet()); Decide(MathBitCount(x)+3>?DigitsToBits(BuiltinPrecisionGet(), 10), BuiltinPrecisionSet(BitsToDigits(MathBitCount(x), 10)+1));    result := MathSqrtFloat(x+0.);  Decide(FloatIsInt?(SetExactBitsN(result, GetExactBitsN(result)-3)), result:= Floor(result+0.5)); BuiltinPrecisionSet(GlobalPop()); },  result := MathSqrtFloat(x+0.) );  SetExactBitsN(result, DigitsToBits(BuiltinPrecisionGet(),10));};";
        scriptMap.put("SqrtN",scriptString);
        scriptMap.put("MathSqrt1",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ArcSinNum(x) :={  Decide( 239*Abs(x) >=? 169,   Sign(x)*(InternalPi()/2-ArcSinN(Sqrt(1-x^2))), ArcSinN(x) );};";
        scriptMap.put("ArcSinNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ArcTanNum(x) :={  Decide( Abs(x)>?1, Sign(x)*(InternalPi()/2-ArcSin(1/Sqrt(x^2+1))), ArcSin(x/Sqrt(x^2+1)) );};";
        scriptMap.put("ArcTanNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(BrentCacheOfConstantsN){ CachedConstant(BrentCacheOfConstantsN, Ln2, InternalLnNum(2)); };10 # BrentLn(x_Integer?)::(BuiltinPrecisionGet()>?40) <--{ Local(y, n, k, eps); n := BuiltinPrecisionGet();   k := 1 + Quotient(IntLog(4*10^n, x), 2);  BuiltinPrecisionSet(n+5);  eps := DivideN(1, 10^n);  y := PowerN(x, k);   y := DivideN(InternalPi()*y, (2*k)*ArithmeticGeometricMean(4, y, eps)); BuiltinPrecisionSet(n); RoundTo(y, n); };15 # BrentLn(x_Integer?) <-- LogN(x);20 # BrentLn(_x)::(x<?1) <-- -BrentLn(1/x);30 # BrentLn(_x)::(BuiltinPrecisionGet()>?85) <--{ Local(y, n, n1, k, eps); NM({ n := BuiltinPrecisionGet();   n1 := n + IntLog(n,10);   k := 2 + Quotient(n1*28738, 2*8651)   - IntLog(Floor(x), 2);  BuiltinPrecisionSet(n1+2+Quotient(k*3361, 11165));  eps := DivideN(1, 10^(n1+1));  y := x*2^(k-2);    y:=InternalPi()*y/(2*ArithmeticGeometricMean(1,y,eps)) - k*Ln2(); BuiltinPrecisionSet(n); }); y; };40 # BrentLn(x_Number?) <-- LogN(x);";
        scriptMap.put("BrentLn",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CosNum(x) :={ Decide(x<?0 Or? 113*x>?710, x:=TruncRadian(x)); CosN(x);};";
        scriptMap.put("CosNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # ExpNum(x_Number?):: (x >? MathExpThreshold()) <-- { Local(i, y); i:=0; For(i:=0, x >? MathExpThreshold(), i++) x := DivideN(x, 2.); For(y:= ExpN(x), i>?0, i--) y := MultiplyN(y, y); y;};20 # ExpNum(x_Number?):: (2*x <? -MathExpThreshold()) <-- DivideN(1, ExpNum(-x));30 # ExpNum(x_Number?) <-- ExpN(x);";
        scriptMap.put("ExpNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "InternalLnNum(x_Number?)::(x>=?1) <-- NewtonLn(x);InternalLnNum(x_Number?)::(0<?x And? x<?1) <-- - InternalLnNum(DivideN(1,x));";
        scriptMap.put("InternalLnNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LogN(x) := InternalLnNum(x);";
        scriptMap.put("LogN",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "NewtonLn(x) := LocalSymbols(y){ NewtonNum([[y], 4*x/(ExpNum(y)+x)-2+y],  DivideN(794*IntLog(Floor(x*x), 2), 2291), 10, 3);};";
        scriptMap.put("NewtonLn",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "SinNum(x) :={ Decide(x<?0 Or? 113*x>?710, x:=TruncRadian(x));  SinN(x);};";
        scriptMap.put("SinNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TanNum(x) :={ Decide(x<?0 Or? 113*x>?710, x:=TruncRadian(x)); TanN(x);};";
        scriptMap.put("TanNum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "TruncRadian(_r) <--{ Local(twopi);  NM({ r:=Eval(r); twopi:=2*InternalPi(); r:=r-FloorN(r/twopi)*twopi; }, BuiltinPrecisionGet() + IntLog(Ceil(Abs(NM(Eval(r), 1))), 10)); r;};HoldArgument(\"TruncRadian\",r);";
        scriptMap.put("TruncRadian",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(mathExpThreshold) {  mathExpThreshold := Decide(Not? Assigned?(mathExpThreshold), 500); MathExpThreshold() := mathExpThreshold; SetMathExpThreshold(threshold) := {mathExpThreshold:= threshold; };};";
        scriptMap.put("MathExpThreshold",scriptString);
        scriptMap.put("SetMathExpThreshold",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Commondigits(x,y) :={ Local(diff); diff := Abs(x-y); Decide( diff=?0, Infinity,  Quotient(IntLog(FloorN(DivideN(Maximum(Abs(x), Abs(y)), diff)), 2)*351, 1166) ); };NewtonNum(_func, _x0) <-- NewtonNum(func, x0, 5); NewtonNum(_func, _x0, _prec0) <-- NewtonNum(func, x0, prec0, 2);NewtonNum(_func, _xinit, _prec0, _order) <--{ Check(prec0>=?4, \"Argument\", \"NewtonNum: Error: initial precision must be at least 4\"); Check(Integer?(order) And? order>?1, \"Argument\", \"NewtonNum: Error: convergence order must be an integer and at least 2\"); Local(x0, x1, prec, exactdigits, intpart, initialtries); NM({ x0 := xinit; prec := BuiltinPrecisionGet(); intpart := IntLog(Ceil(Abs(x0)), 10);   BuiltinPrecisionSet(2+prec0-intpart);  x1 := (func @ x0);   exactdigits := 0; initialtries := 5;  While(exactdigits*order <? prec0 And? initialtries>?0) { initialtries--; x0 := x1; x1 := (func @ x0); exactdigits := Commondigits(x0, x1);  };  Decide( Assert(\"value\", [\"NewtonNum: Error: need a more accurate initial value than\", xinit]) exactdigits >=? 1, { exactdigits :=Minimum(exactdigits, prec0+2);  intpart := IntLog(Ceil(Abs(x1)), 10);  While(exactdigits*order <=? prec) { exactdigits := exactdigits*order; BuiltinPrecisionSet(2+Minimum(exactdigits, Quotient(prec,order)+1)-intpart); x0 := x1; x1 := (func @ x0);  };  BuiltinPrecisionSet(2+prec); x1 := RoundTo( (func @ x1), prec); },  x1 := xinit ); BuiltinPrecisionSet(prec); }); x1;};";
        scriptMap.put("NewtonNum",scriptString);

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
        scriptString[1] = "5 # Ceil(Infinity) <-- Infinity;5 # Ceil(-Infinity) <-- -Infinity;5 # Ceil(Undefined) <-- Undefined;10 # Ceil(x_RationalOrNumber?) <-- { x:=NM(x); Local(prec,result,n); Assign(prec,BuiltinPrecisionGet()); Decide(Zero?(x),Assign(n,2), Decide(x>?0, Assign(n,2+FloorN(NM(FastLog(x)/FastLog(10)))), Assign(n,2+FloorN(NM(FastLog(-x)/FastLog(10)))) )); Decide(n>?prec,BuiltinPrecisionSet(n)); Assign(result,CeilN(x)); BuiltinPrecisionSet(prec); result; };";
        scriptMap.put("Ceil",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Expand(expr_CanBeUni) <-- NormalForm(MakeUni(expr));20 # Expand(_expr) <-- expr;10 # Expand(expr_CanBeUni(var),_var) <-- NormalForm(MakeUni(expr,var));20 # Expand(_expr,_var) <-- expr;";
        scriptMap.put("Expand",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # Floor(Infinity) <-- Infinity;5 # Floor(-Infinity) <-- -Infinity;5 # Floor(Undefined) <-- Undefined;10 # Floor(x_RationalOrNumber?) <-- { x:=NM(Eval(x)); Local(prec,result,n); Assign(prec,BuiltinPrecisionGet()); Decide(Zero?(x), Assign(n,2), Decide(x>?0, Assign(n,2+FloorN(NM(FastLog(x)/FastLog(10)))), Assign(n,2+FloorN(NM(FastLog(-x)/FastLog(10)))) )); Decide(n>?prec,BuiltinPrecisionSet(n)); Assign(result,FloorN(x)); BuiltinPrecisionSet(prec); result; };";
        scriptMap.put("Floor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "0 # Gcd(0,0) <-- 1;1 # Gcd(0,_m) <-- Abs(m);1 # Gcd(_n,0) <-- Abs(n);1 # Gcd(_m,_m) <-- Abs(m);2 # Gcd(_n,1) <-- 1;2 # Gcd(1,_m) <-- 1;2 # Gcd(n_Integer?,m_Integer?) <-- GcdN(n,m);3 # Gcd(_n,_m)::(GaussianInteger?(m) And? GaussianInteger?(n) )<-- GaussianGcd(n,m);4 # Gcd(-(_n), (_m)) <-- Gcd(n,m);4 # Gcd( (_n),-(_m)) <-- Gcd(n,m);4 # Gcd(Sqrt(n_Integer?),Sqrt(m_Integer?)) <-- Sqrt(Gcd(n,m));4 # Gcd(Sqrt(n_Integer?),m_Integer?) <-- Sqrt(Gcd(n,m^2));4 # Gcd(n_Integer?,Sqrt(m_Integer?)) <-- Sqrt(Gcd(n^2,m));5 # Gcd(n_Rational?,m_Rational?) <--{ Gcd(Numerator(n),Numerator(m))/Lcm(Denominator(n),Denominator(m));};10 # Gcd(list_List?)::(Length(list)>?2) <-- { Local(first); first:=Gcd(list[1],list[2]); Gcd(first~Rest(Rest(list))); };14 # Gcd([0]) <-- 1;15 # Gcd([_head]) <-- head;20 # Gcd(list_List?)::(Length(list)=?2) <-- Gcd(list[1],list[2]);30 # Gcd(n_CanBeUni,m_CanBeUni)::(Length(VarList(n*m))=?1) <--{ Local(vars); vars:=VarList(n+m); NormalForm(Gcd(MakeUni(n,vars),MakeUni(m,vars)));};100 # Gcd(n_Constant?,m_Constant?) <-- 1;110 # Gcd(_m,_n) <--{ Echo(\"Not simplified\");};0 # Gcd(n_UniVar?,m_UniVar?):: (n[1] =? m[1] And? Degree(n) <? Degree(m)) <-- Gcd(m,n);1 # Gcd(nn_UniVar?,mm_UniVar?):: (nn[1] =? mm[1] And? Degree(nn) >=? Degree(mm)) <--{ UniVariate(nn[1],0, UniGcd(Concat(ZeroVector(nn[2]),nn[3]), Concat(ZeroVector(mm[2]),mm[3])));};";
        scriptMap.put("Gcd",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # Lcm(a_Integer?,b_Integer?) <-- Quotient(a*b,Gcd(a,b));10 # Lcm(list_List?)::(Length(list)>?2) <--{ Local(first); first:=Lcm(list[1],list[2]); Lcm(first~Rest(Rest(list)));};10 # Lcm(list_List?)::(Length(list)=?2) <-- Lcm(list[1],list[2]);10 # Lcm(list_List?)::(Length(list)=?1) <-- Lcm(list[1],1);";
        scriptMap.put("Lcm",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LnCombine(_a) <-- DoLnCombine(CanonicalAdd(a));1 # DoLnCombine(Ln(_a)) <-- Ln(a);1 # DoLnCombine(Ln(_a)*_b) <-- Ln(a^b);1 # DoLnCombine(_b*Ln(_a)) <-- Ln(a^b);2 # DoLnCombine(Ln(_a)*_b+_c) <-- DoLnCombine(Ln(a^b)+c);2 # DoLnCombine(Ln(_a)*_b-_c) <-- DoLnCombine(Ln(a^b)-c);2 # DoLnCombine(_b*Ln(_a)+_c) <-- DoLnCombine(Ln(a^b)+c);2 # DoLnCombine(_b*Ln(_a)-_c) <-- DoLnCombine(Ln(a^b)-c);2 # DoLnCombine(_a+(_c*Ln(_b))) <-- DoLnCombine(a+Ln(b^c));2 # DoLnCombine(_a-(_c*Ln(_b))) <-- DoLnCombine(a-Ln(b^c));2 # DoLnCombine(_a+(Ln(_b)*_c)) <-- DoLnCombine(a+Ln(b^c));2 # DoLnCombine(_a-(Ln(_b)*_c)) <-- DoLnCombine(a-Ln(b^c));2 # DoLnCombine(_a+((Ln(_b)*_c)+_d)) <-- DoLnCombine(a+(Ln(b^c)+d));2 # DoLnCombine(_a+((Ln(_b)*_c)-_d)) <-- DoLnCombine(a+(Ln(b^c)-d));2 # DoLnCombine(_a-((Ln(_b)*_c)+_d)) <-- DoLnCombine(a-(Ln(b^c)+d));2 # DoLnCombine(_a-((Ln(_b)*_c)-_d)) <-- DoLnCombine(a-(Ln(b^c)-d));2 # DoLnCombine(_a+((_c*Ln(_b))+_d)) <-- DoLnCombine(a+(Ln(b^c)+d));2 # DoLnCombine(_a+((_c*Ln(_b))-_d)) <-- DoLnCombine(a+(Ln(b^c)-d));2 # DoLnCombine(_a-((_c*Ln(_b))+_d)) <-- DoLnCombine(a-(Ln(b^c)+d));2 # DoLnCombine(_a-((_c*Ln(_b))-_d)) <-- DoLnCombine(a-(Ln(b^c)-d));3 # DoLnCombine(Ln(_a)+Ln(_b)) <-- Ln(a*b);3 # DoLnCombine(Ln(_a)-Ln(_b)) <-- Ln(a/b);3 # DoLnCombine(Ln(_a)+(Ln(_b)+_c)) <-- DoLnCombine(Ln(a*b)+c);3 # DoLnCombine(Ln(_a)+(Ln(_b)-_c)) <-- DoLnCombine(Ln(a*b)-c);3 # DoLnCombine(Ln(_a)-(Ln(_b)+_c)) <-- DoLnCombine(Ln(a/b)-c);3 # DoLnCombine(Ln(_a)-(Ln(_b)-_c)) <-- DoLnCombine(Ln(a/b)+c);4 # DoLnCombine(Ln(_a)+(_b+_c)) <-- b+DoLnCombine(Ln(a)+c);4 # DoLnCombine(Ln(_a)+(_b-_c)) <-- b+DoLnCombine(Ln(a)-c);4 # DoLnCombine(Ln(_a)-(_b+_c)) <-- DoLnCombine(Ln(a)-c)-b;4 # DoLnCombine(Ln(_a)-(_b-_c)) <-- DoLnCombine(Ln(a)+c)-b;4 # DoLnCombine(_a+(Ln(_b)+_c)) <-- a+DoLnCombine(Ln(b)+c);4 # DoLnCombine(_a+(Ln(_b)-_c)) <-- a+DoLnCombine(Ln(b)-c);4 # DoLnCombine(_a-(Ln(_b)+_c)) <-- a-DoLnCombine(Ln(b)+c);4 # DoLnCombine(_a-(Ln(_b)-_c)) <-- a-DoLnCombine(Ln(b)-c);5 # DoLnCombine(_a+(_b+_c)) <-- a+(b+DoLnCombine(c));6 # DoLnCombine(_a) <-- a;";
        scriptMap.put("LnCombine",scriptString);
        scriptMap.put("DoLnCombine",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1 # LnExpand(Ln(x_Integer?)) <-- Add(Map([[n,m],m*Ln(n)],Transpose(Factors(x))));1 # LnExpand(Ln(_a*_b)) <-- LnExpand(Ln(a))+LnExpand(Ln(b));1 # LnExpand(Ln(_a/_b)) <-- LnExpand(Ln(a))-LnExpand(Ln(b));1 # LnExpand(Ln(_a^_n)) <-- LnExpand(Ln(a))*n;2 # LnExpand(_a) <-- a;";
        scriptMap.put("LnExpand",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "0 # Modulo(_n,m_RationalOrNumber?)::(m<?0) <-- `Hold(Modulo(@n,@m));1 # Modulo(n_NegativeInteger?,m_PositiveInteger?) <--{ Local(result); result := ModuloN(n,m); Decide(result <? 0,result := result + m); result;};1 # Modulo(n_PositiveInteger?,m_PositiveInteger?) <-- ModuloN(n,m);2 # Modulo(0,_m) <-- 0;2 # Modulo(n_PositiveInteger?,Infinity) <-- n;3 # Modulo(n_Integer?,m_Integer?) <-- ModuloN(n,m);4 # Modulo(n_Number?,m_Number?) <-- NonNM(Modulo(Rationalize(n),Rationalize(m)));5 # Modulo(n_RationalOrNumber?,m_RationalOrNumber?) <--{ Local(n1,n2,m1,m2); n1:=Numerator(n); n2:=Denominator(n); m1:=Numerator(m); m2:=Denominator(m); Modulo(n1*m2,m1*n2)/(n2*m2);};6 # Modulo(n_List?,m_List?) <-- Map(\"Modulo\",[n,m]);7 # Modulo(n_List?,_m) <-- Map(\"Modulo\",[n,FillList(m,Length(n))]);30 # Modulo(n_CanBeUni,m_CanBeUni) <--{ Local(vars); vars:=VarList(n+m); NormalForm(Modulo(MakeUni(n,vars),MakeUni(m,vars)));};0 # Modulo(n_UniVar?,m_UniVar?)::(Degree(n) <? Degree(m)) <-- n;1 # Modulo(n_UniVar?,m_UniVar?):: (n[1] =? m[1] And? Degree(n) >=? Degree(m)) <--{ UniVariate(n[1],0, UniDivide(Concat(ZeroVector(n[2]),n[3]), Concat(ZeroVector(m[2]),m[3]))[2]);};10 # Modulo(n_CanBeUni, m_CanBeUni, vars_List?)::(Length(vars)=?1) <--{ NormalForm(Modulo(MakeUni(n,vars),MakeUni(m,vars)));};";
        scriptMap.put("Modulo",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Object\",[pred,x]);RuleHoldArguments(\"Object\",2,0,Apply(pred,[x])=?True) x;";
        scriptMap.put("Object",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "0 # Quotient(n_Integer?,m_Integer?) <-- QuotientN(n,m);1 # Quotient(0 ,_m) <-- 0;2 # Quotient(n_RationalOrNumber?,m_RationalOrNumber?) <--{ Local(n1,n2,m1,m2,sgn1,sgn2); n1:=Numerator(n); n2:=Denominator(n); m1:=Numerator(m); m2:=Denominator(m); sgn1 := Sign(n1*m2); sgn2 := Sign(m1*n2); sgn1*sgn2*Floor(DivideN(sgn1*n1*m2,sgn2*m1*n2));};30 # Quotient(n_CanBeUni,m_CanBeUni)::(Length(VarList(n*m))=?1) <--{ Local(vars,nl,ml); vars:=VarList(n*m); nl := MakeUni(n,vars); ml := MakeUni(m,vars); NormalForm(Quotient(nl,ml));};0 # Quotient(n_UniVar?,m_UniVar?)::(Degree(n) <? Degree(m)) <-- 0;1 # Quotient(n_UniVar?,m_UniVar?):: (n[1] =? m[1] And? Degree(n) >=? Degree(m)) <--{ UniVariate(n[1],0, UniDivide(Concat(ZeroVector(n[2]),n[3]), Concat(ZeroVector(m[2]),m[3]))[1]);};";
        scriptMap.put("Quotient",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "0 # Rem(n_Number?,m_Number?) <-- n-m*Quotient(n,m);30 # Rem(n_CanBeUni,m_CanBeUni) <-- Modulo(n,m);";
        scriptMap.put("Rem",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # Round(Infinity) <-- Infinity;5 # Round(-Infinity) <-- -Infinity;5 # Round(Undefined) <-- Undefined;10 # Round(x_RationalOrNumber?) <-- FloorN(NM(x+0.5));10 # Round(x_List?) <-- MapSingle(\"Round\",x);20 # Round(x_Complex?)::(RationalOrNumber?(Re(x)) And? RationalOrNumber?(Im(x)) ) <-- FloorN(NM(Re(x)+0.5)) + FloorN(NM(Im(x)+0.5))*I;";
        scriptMap.put("Round",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Sign(n_PositiveNumber?) <-- 1;10 # Sign(n_Zero?) <-- 0;20 # Sign(n_Number?) <-- -1;15 # Sign(n_Infinity?)::(n <? 0) <-- -1;15 # Sign(n_Infinity?)::(n >? 0) <-- 1;15 # Sign(n_Number?/m_Number?) <-- Sign(n)*Sign(m);20 # Sign(n_List?) <-- MapSingle(\"Sign\",n);100 # Sign(_a)^n_Even? <-- 1;100 # Sign(_a)^n_Odd? <-- Sign(a);";
        scriptMap.put("Sign",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "0 # Sqrt(0) <-- 0;0 # Sqrt(Infinity) <-- Infinity;0 # Sqrt(-Infinity) <-- Complex(0,Infinity);0 # Sqrt(Undefined) <-- Undefined;1 # Sqrt(x_PositiveInteger?)::(Integer?(SqrtN(x))) <-- SqrtN(x);2 # Sqrt(x_PositiveNumber?)::NumericMode?() <-- SqrtN(x);2 # Sqrt(x_NegativeNumber?) <-- Complex(0,Sqrt(-x));3 # Sqrt(x_Complex?)::NumericMode?() <-- x^(1/2);Sqrt(xlist_List?) <-- MapSingle(\"Sqrt\",xlist);90 # (Sqrt(x_Constant?))::(NegativeNumber?(NM(x))) <-- Complex(0,Sqrt(-x));110 # Sqrt(Complex(_r,_i)) <-- Exp(Ln(Complex(r,i))/2);400 # Sqrt(x_Integer?)::Integer?(SqrtN(x)) <-- SqrtN(x);400 # Sqrt(x_Integer?/y_Integer?)::(Integer?(SqrtN(x)) And? Integer?(SqrtN(y))) <-- SqrtN(x)/SqrtN(y);";
        scriptMap.put("Sqrt",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1 # Undefined <? _x <-- False;1 # Undefined <=? _x <-- False;1 # Undefined >? _x <-- False;1 # Undefined >=? _x <-- False;1 # _x <? Undefined <-- False;1 # _x <=? Undefined <-- False;1 # _x >? Undefined <-- False;1 # _x >=? Undefined <-- False;5 # (n_Number? <? m_Number?) <-- LessThan?(n-m,0);LocalSymbols(nNum,mNum){ 10 # (_n <? _m)::{nNum:=NM(Eval(n)); mNum:=NM(Eval(m));Number?(nNum) And? Number?(mNum);} <-- LessThan?(nNum-mNum,0);};20 # (Infinity <? _n)::(Not?(Infinity?(n))) <-- False;20 # (-Infinity <? _n)::(Not?(Infinity?(n))) <-- True;20 # (_n <? Infinity)::(Not?(Infinity?(n))) <-- True;20 # (_n <? -Infinity)::(Not?(Infinity?(n))) <-- False;30 # (_n1/_n2) <? 0 <-- (n1 <? 0) !=? (n2 <? 0);30 # (_n1*_n2) <? 0 <-- (n1 <? 0) !=? (n2 <? 0);30 # ((_n1+_n2) <? 0)::((n1 <? 0) And? (n2 <? 0)) <-- True;30 # ((_n1+_n2) <? 0)::((n1 >? 0) And? (n2 >? 0)) <-- False;30 # _x^a_Odd? <? 0 <-- x <? 0;30 # _x^a_Even? <? 0 <-- False; 40 # (Sqrt(_x))::(x >? 0) <? 0 <-- False;40 # (Sin(_x) <? 0)::(Not?(Even?(NM(x/Pi))) And? Even?(NM(Floor(x/Pi)))) <-- False;40 # (Sin(_x) <? 0)::(Not?(Odd? (NM(x/Pi))) And? Odd? (NM(Floor(x/Pi)))) <-- True;40 # Cos(_x) <? 0 <-- Sin(Pi/2-x) <? 0;40 # (Tan(_x) <? 0)::(Not?(Even?(NM(2*x/Pi))) And? Even?(NM(Floor(2*x/Pi)))) <-- False;40 # (Tan(_x) <? 0)::(Not?(Odd? (NM(2*x/Pi))) And? Odd? (NM(Floor(2*x/Pi)))) <-- True;40 # (Complex(_a,_b) <? 0)::(b!=?0) <-- False;40 # (Complex(_a,_b) >=? 0)::(b!=?0) <-- False;40 # (Sqrt(_x))::(x <? 0) <? 0 <-- False;40 # (Sqrt(_x))::(x <? 0) >=? 0 <-- False;50 # -(_x) <? 0 <-- Not?((x<?0) Or? (x=?0));50 # _n >? _m <-- m <? n;50 # _n <=? _m <-- m >=? n;50 # _n >=? _m <-- Not?(n<?m);Function(\"!=?\",[aLeft,aRight]) Not?(aLeft=?aRight);";
        scriptMap.put("<?",scriptString);
        scriptMap.put(">?",scriptString);
        scriptMap.put("<=?",scriptString);
        scriptMap.put(">=?",scriptString);
        scriptMap.put("!=?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"->\",[left,right]);HoldArgument(\"->\",left);";
        scriptMap.put("->",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "n_Integer? << m_Integer? <-- ShiftLeft(n,m);n_Integer? >> m_Integer? <-- ShiftRight(n,m);";
        scriptMap.put("<<",scriptString);
        scriptMap.put(">>",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "   Function(\"MacroSubstituteApply\", [body, predicate, change]){ `MacroSubstituteApply((Hold(@body)));};HoldArgument(\"MacroSubstituteApply\", predicate);HoldArgument(\"MacroSubstituteApply\", change);UnFence(\"MacroSubstituteApply\", 3);RulebaseHoldArguments(\"MacroSubstituteApply\", [body]);UnFence(\"MacroSubstituteApply\", 1);RuleHoldArguments(\"MacroSubstituteApply\", 1, 1, `ApplyFast(predicate, [Hold(Hold(@body))]) =? True){ `ApplyFast(change, [Hold(Hold(@body))]);};RuleHoldArguments(\"MacroSubstituteApply\", 1, 2, `Function?(Hold(@body))){ `ApplyFast(\"MacroMapArgs\", [Hold(Hold(@body)), \"MacroSubstituteApply\"]);};RuleHoldArguments(\"MacroSubstituteApply\", 1, 3, True){ `Hold(@body);};";
        scriptMap.put("MacroSubstituteApply",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"SubstituteApply\", [body, predicate, change]){ SubstituteApply(body);};HoldArgument(\"SubstituteApply\", predicate);HoldArgument(\"SubstituteApply\", change);UnFence(\"SubstituteApply\", 3);RulebaseHoldArguments(\"SubstituteApply\", [body]);UnFence(\"SubstituteApply\", 1);RuleHoldArguments(\"SubstituteApply\", 1, 1, Apply(predicate, [body]) =? True){ Apply(change,[body]);};RuleHoldArguments(\"SubstituteApply\", 1, 2, Function?(body)){ Apply(\"MapArgs\",[body,\"SubstituteApply\"]);};RuleHoldArguments(\"SubstituteApply\", 1, 3, True) body;";
        scriptMap.put("SubstituteApply",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function() Add(val, ...);10 # Add([]) <-- 0;20 # Add(values_List?) <--{ Local(i, sum); sum:=0; ForEach(i, values) { sum := sum + i; }; sum;};30 # Add(_value) <-- value;";
        scriptMap.put("Add",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function() Maximum(l1, l2, l3, ...);10 # Maximum(_l1, _l2, l3_List?) <-- Maximum(Concat([l1, l2], l3));20 # Maximum(_l1, _l2, _l3) <-- Maximum([l1, l2, l3]);10 # Maximum(l1_List?,l2_List?) <-- Map(\"Maximum\",[l1,l2]);20 # Maximum(l1_RationalOrNumber?,l2_RationalOrNumber?) <-- Decide(l1>?l2,l1,l2);30 # Maximum(l1_Constant?,l2_Constant?) <-- Decide(NM(Eval(l1-l2))>?0,l1,l2);10 # Maximum([]) <-- Undefined;20 # Maximum(list_List?) <--{ Local(result); result:= list[1]; ForEach(item,Rest(list)) result:=Maximum(result,item); result;};30 # Maximum(_x) <-- x;";
        scriptMap.put("Maximum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function() Minimum(l1, l2, l3, ...);10 # Minimum(_l1, _l2, l3_List?) <-- Minimum(Concat([l1, l2], l3));20 # Minimum(_l1, _l2, _l3) <-- Minimum([l1, l2, l3]);10 # Minimum(l1_List?,l2_List?) <-- Map(\"Minimum\",[l1,l2]);20 # Minimum(l1_RationalOrNumber?,l2_RationalOrNumber?) <-- Decide(l1<?l2,l1,l2);30 # Minimum(l1_Constant?,l2_Constant?) <-- Decide(NM(Eval(l1-l2))<?0,l1,l2);10 # Minimum([]) <-- Undefined;20 # Minimum(list_List?) <--{ Local(result); result:= list[1]; ForEach(item,Rest(list)) result:=Minimum(result,item); result;};30 # Minimum(_x) <-- x;";
        scriptMap.put("Minimum",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Product\",[sumvar,sumfrom,sumto,sumbody]){ Local(sumi,sumsum); sumsum:=1; For(sumi:=sumfrom,sumi<=?sumto And? sumsum!=?0,sumi++) { MacroLocal(sumvar); MacroAssign(sumvar,sumi); sumsum:=sumsum*Eval(sumbody); }; sumsum;};UnFence(\"Product\",4);HoldArgument(\"Product\",sumvar);HoldArgument(\"Product\",sumbody);Product(sumlist_List?) <--{ Local(sumi,sumsum); sumsum:=1; ForEach(sumi,sumlist) { sumsum:=sumsum*sumi; }; sumsum;};";
        scriptMap.put("Product",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Subfactorial\",[n]){ n! * Sum(k,0,n,(-1)^(k)/k!);};30 # Subfactorial(n_List?) <-- MapSingle(\"Subfactorial\",n);";
        scriptMap.put("Subfactorial",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Sum\",[sumvararg,sumfromarg,sumtoarg,sumbodyarg]);10 # Sum(_sumvar,sumfrom_Number?,sumto_Number?,_sumbody)::(sumfrom>?sumto) <-- 0;20 # Sum(_sumvar,sumfrom_Number?,sumto_Number?,_sumbody)::(sumto<?sumfrom) <-- ApplyFast(\"Sum\",[sumvar,sumto,sumfrom,sumbody]);30 # Sum(_sumvar,sumfrom_Number?,sumto_Number?,_sumbody) <--LocalSymbols(sumi,sumsum){ Local(sumi,sumsum); sumsum:=0; For(sumi:=sumfrom,sumi<=?sumto,sumi++) { MacroLocal(sumvar); MacroAssign(sumvar,sumi); sumsum:=sumsum+Eval(sumbody); }; sumsum;};UnFence(\"Sum\",4);HoldArgument(\"Sum\",sumvararg);HoldArgument(\"Sum\",sumbodyarg);40 # Sum([]) <-- 0;50 # Sum(values_List?) <--{ Local(i, sum); sum:=0; ForEach(i, values) { sum := sum + i; }; sum;};";
        scriptMap.put("Sum",scriptString);
        scriptMap.put("SumFunc",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "n1_RationalOrNumber? *** n2_RationalOrNumber? <--{ Check(n2-n1 <=? 65535, \"Argument\", \"Partial factorial: Error: the range \" ~ ( PipeToString() Write(n2-n1) ) ~ \" is too large, you may want to avoid exact calculation\"); Decide(n2-n1<?0, 1, Factorialpartial(n1, n2) );};2# Factorialpartial(_a, _b):: (b-a>=?4) <-- Factorialpartial(a, a+((b-a)>>1)) * Factorialpartial(a+((b-a)>>1)+1, b);3# Factorialpartial(_a, _b):: (b-a>=?3) <-- a*(a+1)*(a+2)*(a+3);4# Factorialpartial(_a, _b):: (b-a>=?2) <-- a*(a+1)*(a+2);5# Factorialpartial(_a, _b):: (b-a>=?1) <-- a*(a+1);6# Factorialpartial(_a, _b):: (b-a>=?0) <-- a;";
        scriptMap.put("***",scriptString);
        scriptMap.put("Factorialpartial",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "1# (n_PositiveInteger?)!! _ (n<=?3) <-- n;2# (n_PositiveInteger?)!! <--{ Check(n<=?65535, \"Argument\", \"Double factorial: Error: the argument \" ~ ( PipeToString() Write(n) ) ~ \" is too large, you may want to avoid exact calculation\"); Factorialdouble(2+Modulo(n, 2), n);};3# (_n)!! _ (n=? -1 Or? n=?0)<-- 1;2# Factorialdouble(_a, _b):: (b-a>=?6) <-- Factorialdouble(a, Quotient(a+b,2)) * Factorialdouble(Quotient(a+b,2)+1+Modulo(Quotient(a+b,2)+1-a, 2), b);3# Factorialdouble(_a, _b):: (b-a>=?4) <-- a*(a+2)*(a+4);4# Factorialdouble(_a, _b):: (b-a>=?2) <-- a*(a+2);5# Factorialdouble(_a, _b) <-- a;30 # (n_List?)!! <-- MapSingle(\"!!\",n);";
        scriptMap.put("!!",scriptString);
        scriptMap.put("Factorialdouble",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # 0! <-- 1;10 # (Infinity)! <-- Infinity;20 # ((n_PositiveInteger?)!) <-- { Check(n <=? 65535, \"Argument\", \"Factorial: Error: the argument \" ~ ( PipeToString() Write(n) ) ~ \" is too large, you may want to avoid exact calculation\"); MathFac(n);};25 # ((x_Constant?)!)::(FloatIsInt?(x) And? x>?0) <-- (Round(x)!);30 # ((x_Number?)!)::NumericMode?() <-- InternalGammaNum(x+1);40 # (n_List?)! <-- MapSingle(\"!\",n);HalfIntegerFactorial(n_Odd?):: (n>?0) <-- Sqrt(Pi) * ( n!! / 2^((n+1)/2) );HalfIntegerFactorial(n_Odd?):: (n<?0) <-- Sqrt(Pi) * ( (-1)^((-n-1)/2)*2^((-n-1)/2) / (-n-2)!! );40 # (n_RationalOrNumber?)! ::(Denominator(Rationalize(n))=?2) <-- HalfIntegerFactorial(Numerator(Rationalize(n)));";
        scriptMap.put("!",scriptString);
        scriptMap.put("HalfIntegerFactorial",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"BenchCall\",[expr]){ Echo(\"In&gt \",expr); WriteString(\"<font color=ff0000>\"); Eval(expr); WriteString(\"</font>\"); True;};HoldArgument(\"BenchCall\",expr);";
        scriptMap.put("BenchCall",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"BenchShow\",[expr]){ Echo(\"In&gt \",expr); WriteString(\"<font color=ff0000> \"); Echo(\"Out&gt \",Eval(expr),\"</font>\"); True;};HoldArgument(\"BenchShow\",expr);";
        scriptMap.put("BenchShow",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # EqualAsSets( A_List?, B_List? )::(Length(A)=?Length(B)) <--{ Local(Acopy,b,nba,result); Acopy := FlatCopy(A); result := True; ForEach(b,B) { nba := Find(Acopy,b); Decide( nba <? 0, { result := False; Break(); } ); DestructiveDelete(Acopy,nba); }; Decide( Not? result, result := Length(Acopy)=?0 ); result;};20 # EqualAsSets( _A, _B ) <-- False;";
        scriptMap.put("EqualAsSets",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"KnownFailure\",[expr]){ Local(rfail); Echo(\"Known failure: \", expr); Assign(rfail,Eval(expr)); Decide(rfail,Echo([\"Failure resolved!\"]));};HoldArgument(\"KnownFailure\",expr);";
        scriptMap.put("KnownFailure",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(TrueFalse){ RulebaseEvaluateArguments(TrueFalse,[var,expr]); 10 # TrueFalse(var_Atom?,_expr) <-- `[(@expr) Where (@var)==False,(@expr) Where (@var)==True]; 20 # TrueFalse([],_expr) <-- `(@expr); 30 # TrueFalse(var_List?,_expr) <-- `{  Local(t,h); Assign(h,First(@var)); Assign(t,Rest(@var)); TrueFalse(h,TrueFalse(t,@expr)); }; Macro(LogicTest,[vars,expr1,expr2]) Verify(TrueFalse((@vars),(@expr1)), TrueFalse((@vars),(@expr2)));};";
        scriptMap.put("LogicTest",scriptString);
        scriptMap.put("TrueFalse",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"LogicVerify\",[aLeft,aRight]){ Decide(aLeft !=? aRight, Verify(CanProve(aLeft Implies? aRight),True) );};";
        scriptMap.put("LogicVerify",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"NextTest\",[aLeft]){ WriteString(\"Test suite for \"~aLeft~\" : \"); NewLine();};";
        scriptMap.put("NextTest",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # NumericEqual(left_Decimal?, right_Decimal?, precision_PositiveInteger?) <--{ Decide(InVerboseMode(),Tell(\"NumericEqual\",[left,right])); Local(repL,repR,precL,precR,newL,newR,plo,phi,replo,rephi); Local(newhi,newrepL,newlo,newrepR,ans); repL := NumberToRep(left); repR := NumberToRep(right); precL := repL[2]; precR := repR[2]; Decide(InVerboseMode(),Tell(\" \",[precL,precR,precision])); newL := RoundToPrecision(left, precision ); newR := RoundToPrecision(right, precision ); Decide(InVerboseMode(),Tell(\" \",[newL,newR])); newrepL := NumberToRep( newL ); newrepR := NumberToRep( newR ); Decide(InVerboseMode(),Tell(\" \",[newrepL,newrepR])); ans := Verify( newrepL[1] - newrepR[1], 0 ); Decide(InVerboseMode(),Tell(\" \",ans)); ans;};15 # NumericEqual(left_Integer?, right_Integer?, precision_PositiveInteger?) <--{ Decide(InVerboseMode(),Tell(\"NumericEqualInt\",[left,right])); left =? right;};20 # NumericEqual(left_Number?, right_Number?, precision_PositiveInteger?) <--{ Decide(InVerboseMode(),Tell(\"NumericEqualNum\",[left,right])); Local(nI,nD,repI,repD,precI,precD,intAsDec,newDec,newrepI,newrepD,ans); Decide( Integer?(left), {nI:=left; nD:=right;}, {nI:=right; nD:=left;});  repI := NumberToRep(nI); repD := NumberToRep(nD); precI := repI[2]; precD := repD[2]; intAsDec := RoundToPrecision(1.0*nI,precision); newDec := RoundToPrecision( nD, precision ); newrepI := NumberToRep( intAsDec ); newrepD := NumberToRep( newDec ); Decide(InVerboseMode(), { Tell(\" \",[nI,nD]); Tell(\" \",[repI,repD]); Tell(\" \",[precI,precD]); Tell(\" \",[intAsDec,newDec]); Tell(\" \",[newrepI,newrepD]); } ); ans := Verify( newrepI[1] - newrepD[1], 0 ); Decide(InVerboseMode(),Tell(\" \",ans)); ans;};25 # NumericEqual(left_Complex?, right_Complex?, precision_PositiveInteger?) <--{ Decide(InVerboseMode(),Tell(\"NumericEqualC\",[left,right])); Local(rrL,iiL,rrR,iiR,ans); rrL := Re(left); iiL := Im(left); rrR := Re(right); iiR := Im(right); Decide(InVerboseMode(), { Tell(\" \",[left,right]); Tell(\" \",[rrL,rrR]); Tell(\" \",[iiL,iiR]); } ); ans := (NumericEqual(rrL,rrR,precision) And? NumericEqual(iiL,iiR,precision));};";
        scriptMap.put("NumericEqual",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RandVerifyArithmetic(_n)<--{ While(n>?0) { n--; VerifyArithmetic(FloorN(300*Random()),FloorN(80*Random()),FloorN(90*Random())); };};";
        scriptMap.put("RandVerifyArithmetic",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RoundTo(x_Number?, precision_PositiveInteger?) <--{ Local(oldPrec,result); oldPrec:=BuiltinPrecisionGet(); BuiltinPrecisionSet(precision); Assign(result,DivideN( Round( MultiplyN(x, 10^precision) ), 10^precision )); BuiltinPrecisionSet(oldPrec); result;};10 # RoundTo(Complex(r_Number?, i_Number?), precision_PositiveInteger?) <-- Complex(RoundTo(r, precision), RoundTo(i, precision));20 # RoundTo( Infinity,precision_PositiveInteger?) <-- Infinity;20 # RoundTo(-Infinity,precision_PositiveInteger?) <-- -Infinity;";
        scriptMap.put("RoundTo",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ShowLine() :={ Echo(\"File: \", CurrentFile(),\", Line: \", CurrentLine());};";
        scriptMap.put("ShowLine",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Macro(\"TestEquivalent\",[left,right]){ Local(leftEval,rightEval,diff,vars,isEquiv); Decide(InVerboseMode(),{Tell(TestEquivalent,[left,right]);}); leftEval := @left; rightEval := @right; Decide(InVerboseMode(), { NewLine(); Tell(\" \",leftEval); Tell(\" \",rightEval); }); Decide( List?(leftEval), { Decide( List?(rightEval), {  Decide(InVerboseMode(),Tell(\" both are lists \")); isEquiv := TestTwoLists(leftEval,rightEval); }, isEquiv := False ); }, { Decide( List?(rightEval),  isEquiv := False, {  Decide(InVerboseMode(),Tell(\" neither is list \")); Decide(Equation?(leftEval), { Decide(Equation?(rightEval), { Decide(InVerboseMode(),Tell(\" both are equations\")); Local(dLHs,dRHS); dLHS := Simplify(EquationLeft(leftEval) - EquationLeft(rightEval)); dRHS := Simplify(EquationRight(leftEval) - EquationRight(rightEval)); Decide(InVerboseMode(),Tell(\" \",[dLHS,dRHS])); isEquiv := dLHS=?0 And? dRHS=?0; }, isEquiv := False ); }, { Decide(Equation?(rightEval), isEquiv := False, { Decide(InVerboseMode(),Tell(\" neither is equation\")); diff := Simplify(leftEval - rightEval); vars := VarList(diff); Decide(InVerboseMode(), { Tell(\" \",[leftEval,rightEval]); Tell(\" \",vars); Tell(\" \",diff); } ); isEquiv := ( Zero?(diff) Or? ZeroVector?(diff) ); } ); } ); } ); } ); Decide(InVerboseMode(),Tell(\" Equivalence = \",isEquiv)); Decide( Not? isEquiv, { WriteString(\"******************\"); NewLine(); WriteString(\"L.H.S. evaluates to: \"); Write(leftEval); NewLine(); WriteString(\"which differs from \"); Write(rightEval); NewLine(); WriteString(\" by \");  Write(diff); NewLine(); WriteString(\"******************\"); NewLine(); } ); isEquiv;};10 # TestTwoLists( L1_List?, L2_List? ) <--{ Decide(InVerboseMode(),{Tell(\" TestTwoLists\");Tell(\" \",L1);Tell(\" \",L2);}); Decide(Length(L1)=?1 And? Length(L2)=?1, { TestEquivalent(L1[1],L2[1]); }, { EqualAsSets(L1,L2); } );};";
        scriptMap.put("TestEquivalent",scriptString);
        scriptMap.put("TestTwoLists",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function (\"TestMathPiper\", [expr, ans]){ Local(diff); diff := Simplify(Eval(expr)-Eval(ans)); Decide(Simplify(diff)=?0, True, { WriteString(\"******************\"); NewLine(); ShowLine(); Write(expr); WriteString(\" evaluates to \"); NewLine(); Write(Eval(expr)); NewLine(); WriteString(\" which differs from \"); NewLine(); Write(Eval(ans)); NewLine(); WriteString(\" by \"); NewLine(); Write(diff); NewLine(); WriteString(\"******************\"); NewLine(); False; } );};HoldArgument(\"TestMathPiper\", expr);HoldArgument(\"TestMathPiper\", ans);";
        scriptMap.put("TestMathPiper",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Testing\",[aLeft]){ WriteString(\"--\"); WriteString(aLeft); NewLine();};";
        scriptMap.put("Testing",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"Verify\",[aLeft,aRight]){ Local(result); result := Eval(aLeft);  Decide(Not?(Equal?(result,aRight)), { WriteString(\"******************\"); NewLine(); ShowLine(); NewLine(); Write(aLeft); NewLine(); WriteString(\" evaluates to \"); NewLine(); Write(result); NewLine(); WriteString(\" which differs from \"); NewLine(); Write(aRight); NewLine(); WriteString(\"******************\"); NewLine(); False; }, True );};HoldArgument(\"Verify\",aLeft);UnFence(\"Verify\",2);Macro(\"Verify\", [a,b,message]){ Echo(\"test \", @message); Verify(@a, @b);};";
        scriptMap.put("Verify",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "LocalSymbols(f1,f2){  f1(x,n,m):=(x^n-1)*(x^m-1); f2(x,n,m):=x^(n+m)-(x^n)-(x^m)+1; VerifyArithmetic(x,n,m):= { Verify(f1(x,n,m),f2(x,n,m)); };};";
        scriptMap.put("VerifyArithmetic",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "VerifyDiv(_u,_v) <--{ Local(q,r); q:=Quotient(u,v); r:=Rem(u,v); Verify(Expand(u),Expand(q*v+r));};";
        scriptMap.put("VerifyDiv",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "VerifySolve(_e1, _e2) <--Decide(VerifySolveEqual(Eval(e1), Eval(e2)),  True, {  WriteString(\"******************\"); NewLine(); ShowLine(); NewLine(); Write(e1); NewLine(); WriteString(\" evaluates to \"); NewLine(); Write(Eval(e1)); NewLine(); WriteString(\" which differs from \"); NewLine(); Write(e2); NewLine(); WriteString(\"******************\"); NewLine(); False;  }); HoldArgumentNumber(\"VerifySolve\", 2, 1);10 # VerifySolveEqual([], []) <-- True;20 # VerifySolveEqual([], e2_List?) <-- False;30 # VerifySolveEqual(e1_List?, e2_List?) <--{ Local(i, found); found := False; i := 0; While(i <? Length(e2) And? Not? found) { i++; found := VerifySolveEqual(First(e1), e2[i]); }; Decide(found, VerifySolveEqual(Rest(e1), Delete(e2, i)), False);};40 # VerifySolveEqual(_l1 == _r1, _l2 == _r2) <-- Equal?(l1,l2) And? Simplify(r1-r2)=?0;50 # VerifySolveEqual(_e1, _e2) <-- Simplify(e1-e2) =? 0;";
        scriptMap.put("VerifySolve",scriptString);
        scriptMap.put("VerifySolveEqual",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # BigOh(UniVariate(_var,_first,_coefs),_var,_degree) <-- { While(first+Length(coefs)>=?(degree+1) And? Length(coefs)>?0) DestructiveDelete(coefs,Length(coefs)); UniVariate(var,first,coefs); };20 # BigOh(_uv,_var,_degree)::CanBeUni(uv,var) <-- NormalForm(BigOh(MakeUni(uv,var),var,degree));";
        scriptMap.put("BigOh",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"CanBeUni\",[expression]) CanBeUni(UniVarList(expression),expression);10 # CanBeUni([],_expression) <-- True;20 # CanBeUni(var_List?,_expression) <-- CanBeUni(First(var),expression) And? CanBeUni(Rest(var),expression);30 # CanBeUni(_var,expression_Atom?) <-- True;35 # CanBeUni(_var,expression_FreeOf?(var)) <-- True;40 # CanBeUni(_var,_x + _y) <-- CanBeUni(var,x) And? CanBeUni(var,y);40 # CanBeUni(_var,_x - _y) <-- CanBeUni(var,x) And? CanBeUni(var,y);40 # CanBeUni(_var, + _y) <-- CanBeUni(var,y);40 # CanBeUni(_var, - _y) <-- CanBeUni(var,y);40 # CanBeUni(_var,_x * _y) <-- CanBeUni(var,x) And? CanBeUni(var,y);40 # CanBeUni(_var,_x / _y) <-- CanBeUni(var,x) And? FreeOf?(var,y);40 # CanBeUni(_var,_x ^ y_Integer?)::(y >=? 0 And? CanBeUni(var,x)) <-- True;41 # CanBeUni(_var,(x_FreeOf?(var)) ^ (y_FreeOf?(var))) <-- True;50 # CanBeUni(_var,UniVariate(_var,_first,_coefs)) <-- True;1000 # CanBeUni(_var,_f)::(Not?(FreeOf?(var,f))) <-- False;1001 # CanBeUni(_var,_f) <-- True;";
        scriptMap.put("CanBeUni",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "5 # Coef(uv_UniVar?,order_List?) <--{ Local(result); result:=[]; ForEach(item,order) { DestructiveAppend(result,Coef(uv,item)); }; result;};10 # Coef(uv_UniVar?,order_Integer?)::(order<?uv[2]) <-- 0;10 # Coef(uv_UniVar?,order_Integer?)::(order>=?uv[2]+Length(uv[3])) <-- 0;20 # Coef(uv_UniVar?,order_Integer?) <-- uv[3][(order-uv[2])+1];30 # Coef(uv_CanBeUni,_order)::(Integer?(order) Or? List?(order)) <-- Coef(MakeUni(uv),order);Function(\"Coef\",[expression,var,order]) NormalForm(Coef(MakeUni(expression,var),order));";
        scriptMap.put("Coef",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # CollectOn(_var,_expr)::(CanBeUni(var,expr)) <--{ Decide(InVerboseMode(),Echo(\"<< Collect on: \",var,\" in expression \",expr));  Local(u,a); u := MakeUni(expr,var); Decide( u[2] >? 0,  { a := FillList(0,u[2]); u[3] := Concat(a,u[3]); u[2] := 0; } ); u[3];};";
        scriptMap.put("CollectOn",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Content(UniVariate(_var,_first,_coefs)) <-- Gcd(coefs)*var^first;20 # Content(poly_CanBeUni) <-- NormalForm(Content(MakeUni(poly)));";
        scriptMap.put("Content",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"Degree\",[expr]);RuleHoldArguments(\"Degree\",1,0, UniVar?(expr)){ Local(i,min,max); min:=expr[2]; max:=min+Length(expr[3]); i:=max; While(i >=? min And? Zero?(Coef(expr,i))) i--; i;};10 # Degree(poly_CanBeUni) <-- Degree(MakeUni(poly));10 # Degree(_poly,_var)::(CanBeUni(var,poly)) <-- Degree(MakeUni(poly,var));20 # Degree(_poly,_var)::(Type(poly)=?\"Sqrt\") <-- Degree(poly^2,var)/2;20 # Degree(_poly,_var)::(FunctionToList(poly)[1]=? ^) <--{ Local(ex,pwr,deg); ex := FunctionToList(poly)[3]; pwr := 1/ex;  deg := Degree(poly^pwr,var);  deg*ex;};";
        scriptMap.put("Degree",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "DivPoly(_A,_B,_var,_deg) <--{ Local(a,b,c,i,j,denom); b:=MakeUni(B,var); denom:=Coef(b,0); If(denom =? 0) { Local(f); f:=Content(b); b:=PrimitivePart(b); A:=Simplify(A/f); denom:=Coef(b,0); }; a:=MakeUni(A,var); c:=FillList(0,deg+1); For(i:=0,i<=?deg,i++) { Local(sum,j); sum:=0; For(j:=0,j<?i,j++) { sum := sum + c[j+1]*Coef(b,i-j); }; c[i+1] := (Coef(a,i)-sum) / denom; }; NormalForm(UniVariate(var,0,c));};";
        scriptMap.put("DivPoly",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "DropEndZeroes(list):={ Local(end); end:=Length(list); While(list[end] =? 0) { DestructiveDelete(list,end); end:=end-1; };};";
        scriptMap.put("DropEndZeroes",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"ExpandUniVariate\",[var,first,coefs]){ Local(result,i); result:=0; For(i:=Length(coefs),i>?0,i--) { Local(term); term:=NormalForm(coefs[i])*var^(first+i-1); result:=result+term; }; result;};";
        scriptMap.put("ExpandUniVariate",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Horner(_e,_v) <--{ Local(uni,coefs,result); uni := MakeUni(e,v); coefs:=DestructiveReverse(uni[3]); result:=0; While(coefs !=? []) { result := result*v; result := result+First(coefs); coefs := Rest(coefs); }; result:=result*v^uni[2]; result;};";
        scriptMap.put("Horner",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # LeadingCoef(uv_UniVar?) <-- Coef(uv,Degree(uv));20 # LeadingCoef(uv_CanBeUni) <--{ Local(uvi); uvi:=MakeUni(uv); Coef(uvi,Degree(uvi));};10 # LeadingCoef(uv_CanBeUni(var),_var) <--{ Local(uvi); uvi:=MakeUni(uv,var); Coef(uvi,var,Degree(uvi));};";
        scriptMap.put("LeadingCoef",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"MakeUni\",[expression]) MakeUni(expression,UniVarList(expression));RulebaseHoldArguments(\"MakeUni\",[expression,var]);5 # MakeUni(_expr,[]) <-- UniVariate(dummyvar,0,[expression]);6 # MakeUni(_expr,var_List?) <--{ Local(result,item); result:=expression; ForEach(item,var) { result:=MakeUni(result,item); }; result;};10 # MakeUni(UniVariate(_var,_first,_coefs),_var) <-- UniVariate(var,first,coefs);20 # MakeUni(UniVariate(_v,_first,_coefs),_var) <--{ Local(reslist,item); reslist:=[]; ForEach(item,expression[3]) { Decide(FreeOf?(var,item), DestructiveAppend(reslist,item), DestructiveAppend(reslist,MakeUni(item,var)) ); }; UniVariate(expression[1],expression[2],reslist);};LocalSymbols(a,b,var,expression){ 20 # MakeUni(expression_FreeOf?(var),_var) <-- UniVariate(var,0,[expression]); 30 # MakeUni(_var,_var) <-- UniVariate(var,1,[1]); 30 # MakeUni(_a + _b,_var) <-- MakeUni(a,var) + MakeUni(b,var); 30 # MakeUni(_a - _b,_var) <-- MakeUni(a,var) - MakeUni(b,var); 30 # MakeUni( - _b,_var) <-- - MakeUni(b,var); 30 # MakeUni(_a * _b,_var) <-- MakeUni(a,var) * MakeUni(b,var); 1 # MakeUni(_a ^ n_Integer?,_var) <-- MakeUni(a,var) ^ n; 30 # MakeUni(_a / (b_FreeOf?(var)),_var) <-- MakeUni(a,var) * (1/b);};";
        scriptMap.put("MakeUni",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # Monic(UniVariate(_var,_first,_coefs)) <--{ DropEndZeroes(coefs); UniVariate(var,first,coefs/coefs[Length(coefs)]);};20 # Monic(poly_CanBeUni) <-- NormalForm(Monic(MakeUni(poly)));30 # Monic(_poly,_var)::CanBeUni(poly,var) <-- NormalForm(Monic(MakeUni(poly,var)));";
        scriptMap.put("Monic",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # PrimitivePart(UniVariate(_var,_first,_coefs)) <-- UniVariate(var,0,coefs/Gcd(coefs));20 # PrimitivePart(poly_CanBeUni) <-- NormalForm(PrimitivePart(MakeUni(poly)));";
        scriptMap.put("PrimitivePart",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # RepeatedSquaresMultiply(_a,- (n_Integer?)) <-- 1/RepeatedSquaresMultiply(a,n);15 # RepeatedSquaresMultiply(UniVariate(_var,_first,[_coef]),(n_Integer?)) <-- UniVariate(var,first*n,[coef^n]);20 # RepeatedSquaresMultiply(_a,n_Integer?) <--{ Local(m,b); Assign(m,1); Assign(b,1); While(m<=?n) Assign(m,(ShiftLeft(m,1))); Assign(m, ShiftRight(m,1)); While(m>?0) { Assign(b,b*b); Decide(Not?(Equal?(BitAnd(m,n), 0)),Assign(b,b*a)); Assign(m, ShiftRight(m,1)); }; b;};";
        scriptMap.put("RepeatedSquaresMultiply",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"UniDivide\",[u,v]){ Local(m,n,q,r,k,j); m := Length(u)-1; n := Length(v)-1; While (m>?0 And? Zero?(u[m+1])) m--; While (n>?0 And? Zero?(v[n+1])) n--; q := ZeroVector(m-n+1); r := FlatCopy(u);  For(k:=m-n,k>=?0,k--) { q[k+1] := r[n+k+1]/v[n+1]; For (j:=n+k-1,j>=?k,j--) { r[j+1] := r[j+1] - q[k+1]*v[j-k+1]; }; }; Local(end); end:=Length(r); While (end>?n) { DestructiveDelete(r,end); end:=end-1; }; [q,r];};";
        scriptMap.put("UniDivide",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"UniGcd\",[u,v]){ Local(l,div,mod,m); DropEndZeroes(u); DropEndZeroes(v); l:=UniDivide(u,v); div:=l[1]; mod:=l[2]; DropEndZeroes(mod); m := Length(mod); Decide(m =? 0, v, UniGcd(v,mod));};";
        scriptMap.put("UniGcd",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"UniTaylor\",[taylorfunction,taylorvariable,taylorat,taylororder]){ Local(n,result,dif,polf); result:=[]; { MacroLocal(taylorvariable); MacroAssign(taylorvariable,taylorat); DestructiveAppend(result,Eval(taylorfunction)); }; dif:=taylorfunction; polf:=(taylorvariable-taylorat); For(n:=1,n<=?taylororder,n++) { dif:= Deriv(taylorvariable) dif; MacroLocal(taylorvariable); MacroAssign(taylorvariable,taylorat); DestructiveAppend(result,(Eval(dif)/n!)); }; UniVariate(taylorvariable,0,result);};";
        scriptMap.put("UniTaylor",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "UniVarList(expr) := VarList(expr);";
        scriptMap.put("UniVarList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # UniVar?(UniVariate(_var,_first,_coefs)) <-- True;20 # UniVar?(_anything) <-- False;200 # aLeft_UniVar? ^ aRight_PositiveInteger? <-- RepeatedSquaresMultiply(aLeft,aRight);200 # aLeft_UniVar? - aRight_UniVar? <--{ Local(from,result); Local(curl,curr,left,right); curl:=aLeft[2]; curr:=aRight[2]; left:=aLeft[3]; right:=aRight[3]; result:=[]; from:=Minimum(curl,curr); While(curl<?curr And? left !=? []) { DestructiveAppend(result,First(left)); left:=Rest(left); curl++; }; While(curl<?curr) { DestructiveAppend(result,0); curl++; }; While(curr<?curl And? right !=? []) { DestructiveAppend(result,-First(right)); right:=Rest(right); curr++; }; While(curr<?curl) { DestructiveAppend(result,0); curr++; }; While(left !=? [] And? right !=? []) { DestructiveAppend(result,First(left)-First(right)); left := Rest(left); right := Rest(right); }; While(left !=? []) { DestructiveAppend(result,First(left)); left := Rest(left); }; While(right !=? []) { DestructiveAppend(result,-First(right)); right := Rest(right); }; UniVariate(aLeft[1],from,result);};201 # (aLeft_UniVar? * _aRight)::((FreeOf?(aLeft[1],aRight))) <--{ aRight*aLeft;};";
        scriptMap.put("UniVar?",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "ShiftUniVar(UniVariate(_var,_first,_coefs),_fact,_shift) <-- { UniVariate(var,first+shift,fact*coefs); };RulebaseHoldArguments(\"UniVariate\",[var,first,coefs]);RuleHoldArguments(\"UniVariate\",3,10,Length(coefs)>?0 And? coefs[1]=?0) UniVariate(var,first+1,Rest(coefs));RuleHoldArguments(\"UniVariate\",3,1000,Complex?(var) Or? List?(var)) ExpandUniVariate(var,first,coefs);500 # UniVariate(_var,_f1,_c1) + UniVariate(_var,_f2,_c2) <--{ Local(from,result); Local(curl,curr,left,right); Assign(curl, f1); Assign(curr, f2); Assign(left, c1); Assign(right, c2); Assign(result, []); Assign(from, Minimum(curl,curr)); While(And?(LessThan?(curl,curr),left !=? [])) { DestructiveAppend(result,First(left)); Assign(left,Rest(left)); Assign(curl,AddN(curl,1)); }; While(LessThan?(curl,curr)) { DestructiveAppend(result,0); Assign(curl,AddN(curl,1)); }; While(And?(LessThan?(curr,curl), right !=? [])) { DestructiveAppend(result,First(right)); Assign(right,Rest(right)); Assign(curr,AddN(curr,1)); }; While(LessThan?(curr,curl)) { DestructiveAppend(result,0); Assign(curr,AddN(curr,1)); }; While(And?(left !=? [], right !=? [])) { DestructiveAppend(result,First(left)+First(right)); Assign(left, Rest(left)); Assign(right, Rest(right)); }; While(left !=? []) { DestructiveAppend(result,First(left)); Assign(left, Rest(left)); }; While(right !=? []) { DestructiveAppend(result,First(right)); Assign(right, Rest(right)); }; UniVariate(var,from,result);};200 # UniVariate(_var,_first,_coefs) + a_Number? <-- UniVariate(var,first,coefs) + UniVariate(var,0,[a]);200 # a_Number? + UniVariate(_var,_first,_coefs) <-- UniVariate(var,first,coefs) + UniVariate(var,0,[a]);200 # - UniVariate(_var,_first,_coefs) <-- UniVariate(var,first,-coefs);200 # (_factor * UniVariate(_var,_first,_coefs))::((FreeOf?(var,factor))) <-- UniVariate(var,first,coefs*factor);200 # (UniVariate(_var,_first,_coefs)/_factor)::((FreeOf?(var,factor))) <-- UniVariate(var,first,coefs/factor);200 # UniVariate(_var,_f1,_c1) * UniVariate(_var,_f2,_c2) <--{ Local(i,j,n,shifted,result); Assign(result,MakeUni(0,var)); Assign(n,Length(c1)); For(i:=1,i<=?n,i++) { Assign(result,result+ShiftUniVar(UniVariate(var,f2,c2),MathNth(c1,i),f1+i-1)); }; result;};";
        scriptMap.put("UniVariate",scriptString);
        scriptMap.put("ShiftUniVar",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"AddTerm\",[termlist,term,begining]){ Local(l,i); l := Length(termlist); Decide(term[2]!=?0, { i:=begining; Decide(l>=?1, While ((i<=?l) And? (term[1]<?termlist[i][1])) i++); Decide(i>?l, {DestructiveAppend(termlist,term);i++;}, Decide(term[1]=?termlist[i][1], { Local(nc); nc:=termlist[i][2]+term[2]; Decide(nc!=?0,DestructiveReplace(termlist,i,[term[1],nc]), {DestructiveDelete(termlist,i);i--;}); }, DestructiveInsert(termlist,i,term)) ); } ); i+1;};";
        scriptMap.put("AddTerm",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"AddTerms\",[terms1,terms2]){ Local(result,begining,t); begining :=1; ForEach (t,terms2) begining :=AddTerm(terms1,t,begining); terms1;};";
        scriptMap.put("AddTerms",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"DivTermList\",[a,b]){ Local(q,nq,t,c,begining); q := [];  While ((a!=?[]) And? a[1][1]>=?b[1][1]) { begining := 1; Decide(InVerboseMode(),Echo(\"degree=\",a[1][1])); nq := [a[1][1]-b[1][1],a[1][2]/b[1][2]];  DestructiveAppend(q,nq);  ForEach (t,b) begining := AddTerm(a,[t[1]+nq[1],-t[2]*nq[2]],begining); };  q;};";
        scriptMap.put("DivTermList",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"ExpandSparseUniVar\",[s]){ Local(result,t,var,termlist); result :=0; var := s[1]; termlist := s[2]; ForEach (t,termlist) { Local(term); term := NormalForm(t[2]*var^t[1]); result := result + term; }; result;};";
        scriptMap.put("ExpandSparseUniVar",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "10 # MakeSparseUniVar(poly_CanBeUni,var_Atom?) <--{ Decide(InVerboseMode(),Tell(\"MakeSparseUniVar\",[var,poly])); Local(uni,first,coeffs,n,c,lc,termlist,term); uni := MakeUni(poly,var); Decide(InVerboseMode(),Tell(\" \",uni)); first := uni[2]; coeffs := (uni[3]); Decide(InVerboseMode(),{Tell(\" \",first); Tell(\" \",coeffs);}); termlist := []; lc := Length(coeffs); For(n:=0,n<?lc,n++) { c := coeffs[n+1]; term := [n+first,c]; Decide(InVerboseMode(),Tell(\" \",term)); Decide(c !=? 0, Push(termlist,term)); }; Decide(InVerboseMode(),Tell(\" \",[var,termlist])); [var,termlist];};";
        scriptMap.put("MakeSparseUniVar",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"MultiplySingleTerm\",[termlist,term]){ Local(result,t); result:=[]; Decide(term[2]!=?0, ForEach (t,termlist) DestructiveAppend(result,[t[1]+term[1],t[2]*term[2]]) ); result;};";
        scriptMap.put("MultiplySingleTerm",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"MultiplyTerms\",[terms1,terms2]){ Local(result,t1,t2,begining); result:=[]; ForEach (t1,terms1) { begining :=1; ForEach (t2,terms2) begining := AddTerm(result,[t1[1]+t2[1],t1[2]*t2[2]],1); }; result;};";
        scriptMap.put("MultiplyTerms",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"SparseUniVar\",[var,termlist]);300 # SparseUniVar(_var,_terms1) * SparseUniVar(_var,_terms2) <--SparseUniVar(var, MultiplyTerms(terms1,terms2));300 # SparseUniVar(_var,_terms1) + SparseUniVar(_var,_terms2) <--SparseUniVar(var, AddTerms(terms1,terms2));300 # SparseUniVar(_var,_terms1) - SparseUniVar(_var,_terms2) <--SparseUniVar(var, SubstractTerms(terms1,terms2));";
        scriptMap.put("SparseUniVar",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "Function(\"SubtractTerms\",[terms1,terms2]){ Local(result,t); begining :=1 ; ForEach (t,terms2) begining := AddTerm(terms1,[t[1],-t[2]],1); terms1;};";
        scriptMap.put("SubtractTerms",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "CharList(length,item):={ Local(line,i); line:=\"\"; For(Assign(i,0),LessThan?(i,length),Assign(i,AddN(i,1))) Assign(line, line~item); line;};CharField(width,height) := ArrayCreate(height,CharList(width,\" \"));WriteCharField(charfield):={ Local(i,len); len:=Length(charfield); For(Assign(i,1),i<=?len,Assign(i,AddN(i,1))) { WriteString(charfield[i]); NewLine(); }; True;};ColumnFilled(charfield,column):={ Local(i,result,len); result:=False; len:=Length(charfield); For(Assign(i, 1),(result =? False) And? (i<=?len),Assign(i,AddN(i,1))) { Decide(StringMidGet(column,1,charfield[i]) !=? \" \",result:=True); }; result;};WriteCharField(charfield,width):={ Local(pos,length,len); Assign(length, Length(charfield[1])); Assign(pos, 1); While(pos<=?length) { Local(i,thiswidth); Assign(thiswidth, width); Decide(thiswidth>?(length-pos)+1, { Assign(thiswidth, AddN(SubtractN(length,pos),1)); }, { While (thiswidth>?1 And? ColumnFilled(charfield,pos+thiswidth-1)) { Assign(thiswidth,SubtractN(thiswidth,1)); }; Decide(thiswidth =? 1, Assign(thiswidth, width)); } ); len:=Length(charfield); For(Assign(i, 1),i<=?len,Assign(i,AddN(i,1))) { WriteString(StringMidGet(pos,thiswidth,charfield[i])); NewLine(); }; Assign(pos, AddN(pos, thiswidth)); NewLine(); }; True;};PutString(charfield,x,y,string):={ cf[y] := StringMidSet(x,string,cf[y]); True;};MakeOper(x,y,width,height,oper,args,base):={ Local(result); Assign(result,ArrayCreate(7,0)); ArraySet(result,1,x); ArraySet(result,2,y); ArraySet(result,3,width); ArraySet(result,4,height); ArraySet(result,5,oper); ArraySet(result,6,args); ArraySet(result,7,base); result;};MoveOper(f,x,y):={ f[1]:=AddN(f[1], x);  f[2]:=AddN(f[2], y);  f[7]:=AddN(f[7], y); };AlignBase(i1,i2):={ Local(base); Assign(base, Maximum(i1[7],i2[7])); MoveOper(i1,0,SubtractN(base,(i1[7]))); MoveOper(i2,0,SubtractN(base,(i2[7])));};10 # BuildArgs([]) <-- Formula(ToAtom(\" \"));20 # BuildArgs([_head]) <-- head;30 # BuildArgs(_any) <-- { Local(item1,item2,comma,base,newitem); Assign(item1, any[1]); Assign(item2, any[2]); Assign(comma, Formula(ToAtom(\",\"))); Assign(base, Maximum(item1[7],item2[7])); MoveOper(item1,0,SubtractN(base,(item1[7]))); MoveOper(comma,AddN(item1[3],1),base); MoveOper(item2,comma[1]+comma[3]+1,SubtractN(base,(item2[7]))); Assign(newitem, MakeOper(0,0,AddN(item2[1],item2[3]),Maximum(item1[4],item2[4]),\"Func\",[item1,comma,item2],base)); BuildArgs(newitem~Rest(Rest(any))); };FormulaBracket(f):={ Local(left,right); Assign(left, Formula(ToAtom(\"(\"))); Assign(right, Formula(ToAtom(\")\"))); left[4]:=f[4]; right[4]:=f[4]; MoveOper(left,f[1],f[2]); MoveOper(f,2,0); MoveOper(right,f[1]+f[3]+1,f[2]); MakeOper(0,0,right[1]+right[3],f[4],\"Func\",[left,f,right],f[7]);};1 # Formula(f_Atom?) <-- MakeOper(0,0,Length(ToString(f)),1,\"ToAtom\",ToString(f),0);2 # Formula(_xx ^ _yy) <--{ Local(l,r); Assign(l, BracketOn(Formula(xx),xx,LeftPrecedenceGet(\"^\"))); Assign(r, BracketOn(Formula(yy),yy,RightPrecedenceGet(\"^\"))); MoveOper(l,0,r[4]); MoveOper(r,l[3],0); MakeOper(0,0,AddN(l[3],r[3]),AddN(l[4],r[4]),\"Func\",[l,r],l[2]+l[4]-1);};10 # FormulaArrayItem(xx_List?) <--{ Local(sub,height); sub := []; height := 0; ForEach(item,xx) { Local(made); made := FormulaBracket(Formula(item)); Decide(made[4] >? height,Assign(height,made[4])); DestructiveAppend(sub,made); }; MakeOper(0,0,0,height,\"List\",sub,height>>1);};20 # FormulaArrayItem(_item) <-- Formula(item);2 # Formula(xx_List?) <--{ Local(sub,width,height); sub:=[]; width := 0; height := 1; ForEach(item,xx) { Local(made); made := FormulaArrayItem(item); Decide(made[3] >? width,Assign(width,made[3])); MoveOper(made,0,height); Assign(height,AddN(height,AddN(made[4],1))); DestructiveAppend(sub,made); }; Local(thislength,maxlength); maxlength:=0; ForEach(item,xx) { thislength:=0; If(List?(item)) {thislength:=Length(item);}; If(maxlength<?thislength) {maxlength:=thislength;}; }; Decide(maxlength>?0, { Local(i,j); width:=0; For(j:=1,j<=?maxlength,j++) { Local(w); w := 0; For(i:=1,i<=?Length(sub),i++) { If(List?(xx[i]) And? j<=?Length(xx[i])) Decide(sub[i][6][j][3] >? w,w := sub[i][6][j][3]); }; For(i:=1,i<=?Length(sub),i++) { If(List?(xx[i]) And? j<=?Length(xx[i])) MoveOper(sub[i][6][j],width,0); }; width := width+w+1; }; For(i:=1,i<=?Length(sub),i++) { sub[i][3] := width; }; } ); sub := MakeOper(0,0,width,height,\"List\",sub,height>>1); FormulaBracket(sub);};2 # Formula(_xx / _yy) <--{ Local(l,r,dash,width); Assign(l, Formula(xx)); Assign(r, Formula(yy)); Assign(width, Maximum(l[3],r[3])); Assign(dash, Formula(ToAtom(CharList(width,\"-\")))); MoveOper(dash,0,l[4]); MoveOper(l,(SubtractN(width,l[3])>>1),0); MoveOper(r,(SubtractN(width,r[3])>>1),AddN(dash[2], dash[4])); MakeOper(0,0,width,AddN(r[2], r[4]),\"Func\",[l,r,dash],dash[2]);};RulebaseHoldArguments(\"BracketOn\",[op,f,prec]);RuleHoldArguments(\"BracketOn\",3,1,Function?(f) And? ArgumentsCount(f) =? 2 And? Infix?(Type(f)) And? PrecedenceGet(Type(f)) >? prec){ FormulaBracket(op);};RuleHoldArguments(\"BracketOn\",3,2,True){ op;};10 # Formula(f_Function?)::(ArgumentsCount(f) =? 2 And? Infix?(Type(f))) <--{ Local(l,r,oper,width,height,base); Assign(l, Formula(f[1])); Assign(r, Formula(f[2])); Assign(l, BracketOn(l,f[1],LeftPrecedenceGet(Type(f)))); Assign(r, BracketOn(r,f[2],RightPrecedenceGet(Type(f)))); Assign(oper, Formula(f[0])); Assign(base, Maximum(l[7],r[7])); MoveOper(oper,AddN(l[3],1),SubtractN(base,(oper[7]))); MoveOper(r,oper[1] + oper[3]+1,SubtractN(base,(r[7]))); MoveOper(l,0,SubtractN(base,(l[7]))); Assign(height, Maximum(AddN(l[2], l[4]),AddN(r[2], r[4]))); MakeOper(0,0,AddN(r[1], r[3]),height,\"Func\",[l,r,oper],base);};11 # Formula(f_Function?) <--{ Local(head,args,all); Assign(head, Formula(f[0])); Assign(all, Rest(FunctionToList(f))); Assign(args, FormulaBracket(BuildArgs(MapSingle(\"Formula\",Apply(\"Hold\",[all]))))); AlignBase(head,args); MoveOper(args,head[3],0); MakeOper(0,0,args[1]+args[3],Maximum(head[4],args[4]),\"Func\",[head,args],head[7]);};RulebaseHoldArguments(\"RenderFormula\",[cf,f,x,y]);RuleHoldArguments(\"RenderFormula\",4,1,f[5] =? \"ToAtom\" And? f[6] =? \"(\" And? f[4] >? 1){ Local(height,i); Assign(x, AddN(x,f[1])); Assign(y, AddN(y,f[2])); Assign(height, SubtractN(f[4],1)); cf[y] := StringMidSet(x, \"/\", cf[y]); cf[AddN(y,height)] := StringMidSet(x, \"\\\\\", cf[AddN(y,height)]); For (Assign(i,1),LessThan?(i,height),Assign(i,AddN(i,1))) cf[AddN(y,i)] := StringMidSet(x, \"|\", cf[AddN(y,i)]);};RuleHoldArguments(\"RenderFormula\",4,1,f[5] =? \"ToAtom\" And? f[6] =? \")\" And? f[4] >? 1){ Local(height,i); Assign(x, AddN(x,f[1])); Assign(y, AddN(y,f[2])); Assign(height, SubtractN(f[4],1)); cf[y] := StringMidSet(x, \"\\\\\", cf[y]); cf[y+height] := StringMidSet(x, \"/\", cf[y+height]); For (Assign(i,1),LessThan?(i,height),Assign(i,AddN(i,1))) cf[AddN(y,i)] := StringMidSet(x, \"|\", cf[AddN(y,i)]);};RuleHoldArguments(\"RenderFormula\",4,5,f[5] =? \"ToAtom\"){ cf[AddN(y, f[2]) ]:= StringMidSet(AddN(x,f[1]),f[6],cf[AddN(y, f[2]) ]);};RuleHoldArguments(\"RenderFormula\",4,6,True){ ForEach(item,f[6]) { RenderFormula(cf,item,AddN(x, f[1]),AddN(y, f[2])); };};LocalSymbols(formulaMaxWidth) { SetFormulaMaxWidth(width):= { formulaMaxWidth := width; }; FormulaMaxWidth() := formulaMaxWidth; SetFormulaMaxWidth(60);}; Function(\"UnparseMath2D\",[ff]){ Local(cf,f); f:=Formula(ff); cf:=CharField(f[3],f[4]); RenderFormula(cf,f,1,1); NewLine(); WriteCharField(cf,FormulaMaxWidth()); DumpErrors(); True;};EvalFormula(f):={ Local(result); result:= ListToFunction([ToAtom(\"=?\"),f,Eval(f)]); UnparseMath2D(result); True;};HoldArgument(\"EvalFormula\",f);";
        scriptMap.put("UnparseMath2D",scriptString);
        scriptMap.put("EvalFormula",scriptString);
        scriptMap.put("BuildArgs",scriptString);
        scriptMap.put("FormulaArrayItem",scriptString);

        scriptString = new String[2];
        scriptString[0] = null;
        scriptString[1] = "RulebaseHoldArguments(\"UnparseLatex\",[expression]);RulebaseHoldArguments(\"UnparseLatex\",[expression, precedence]);Function (\"UnparseLatexBracketIf\", [predicate, string]){ Check(Boolean?(predicate) And? String?(string), \"Argument\", \"UnparseLatex internal error: non-boolean and/or non-string argument of UnparseLatexBracketIf\"); Decide(predicate, ConcatStrings(\"( \", string, \") \"), string);};Function (\"UnparseLatexMatrixBracketIf\", [predicate, string]){ Check(Boolean?(predicate) And? String?(string), \"Argument\", \"UnparseLatex internal error: non-boolean and/or non-string argument of UnparseLatexMatrixBracketIf\"); Decide(predicate, ConcatStrings(\"\\\\left[ \", string, \"\\\\right]\"), string);};UnparseLatexMaxPrec() := 60000; 100 # UnparseLatex(_x) <-- ConcatStrings(\"$\", UnparseLatex(x, UnparseLatexMaxPrec()), \"$\");110 # UnparseLatex(x_Number?, _p) <-- ToString(x);200 # UnparseLatex(x_Atom?, _p) <-- UnparseLatexLatexify(ToString(x));100 # UnparseLatex(x_String?, _p) <--{ Local(characterList); characterList := []; ForEach(character, x) { Decide(character !=? \" \", DestructiveAppend(characterList, character), DestructiveAppend(characterList, \"\\\\hspace{2 mm}\")); }; ConcatStrings(\"\\\\mathrm{''\", ListToString(characterList), \"''}\");};100 # UnparseLatex(x_Atom?, _p)::(Infix?(ToString(x))) <-- ConcatStrings(\"\\\\mathrm{\", ToString(x), \"}\");100 # UnparseLatex(x_List?, _p)::(Length(x) =? 0) <-- UnparseLatexBracketIf(True, \"\");110 # UnparseLatex(x_List?, _p) <-- UnparseLatexBracketIf(True, ConcatStrings(UnparseLatex(First(x), UnparseLatexMaxPrec()), UnparseLatexFinishList(Rest(x)) ) );100 # UnparseLatexFinishList(x_List?)::(Length(x) =? 0) <-- \"\";110 # UnparseLatexFinishList(x_List?) <-- ConcatStrings(\", \", UnparseLatex(First(x), UnparseLatexMaxPrec()), UnparseLatexFinishList(Rest(x)));  115 # UnparseLatex(_expr * n_Number?, _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"*\"), ConcatStrings(UnparseLatex(expr, LeftPrecedenceGet(\"*\")), \"\\\\times \", UnparseLatex(n, RightPrecedenceGet(\"*\")) ) );116 # UnparseLatex(_n * _expr, _p):: (Function?(expr) And? Contains?([\"^\", \"!\", \"!!\"], Type(expr)) And? Number?(FunctionToList(expr)[2])) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"*\"), ConcatStrings(UnparseLatex(n, LeftPrecedenceGet(\"*\")), \"\\\\times \", UnparseLatex(expr, RightPrecedenceGet(\"*\")) ) ); 119 # UnparseLatex(expr_Function?, _p)::(ArgumentsCount(expr) =? 2 And? Infix?(Type(expr)) And? Not? Type(expr) =? \"^\" And? MetaGet(expr[0],\"HighlightColor\") !=? Empty) <-- UnparseLatexBracketIf(p <? PrecedenceGet(Type(expr)), ConcatStrings(UnparseLatex(FunctionToList(expr)[2], LeftPrecedenceGet(Type(expr))), \"\\\\colorbox{\",MetaGet(expr[0],\"HighlightColor\"),\"}{\", UnparseLatexLatexify(Type(expr)), \"}\", UnparseLatex(FunctionToList(expr)[3], RightPrecedenceGet(Type(expr))) ) );120 # UnparseLatex(expr_Function?, _p)::(ArgumentsCount(expr) =? 2 And? Infix?(Type(expr)) ) <-- UnparseLatexBracketIf(p <? PrecedenceGet(Type(expr)), ConcatStrings(UnparseLatex(FunctionToList(expr)[2], LeftPrecedenceGet(Type(expr))), UnparseLatexLatexify(Type(expr)), UnparseLatex(FunctionToList(expr)[3], RightPrecedenceGet(Type(expr))) ) );   119 # UnparseLatex(expr_Function?, _p)::(ArgumentsCount(expr) =? 1 And? Prefix?(Type(expr)) And? MetaGet(expr[0],\"HighlightColor\") !=? Empty) <-- UnparseLatexBracketIf(p <? PrecedenceGet(Type(expr)), ConcatStrings(\"\\\\colorbox{\",MetaGet(expr[0],\"HighlightColor\"),\"}{\", UnparseLatexLatexify(Type(expr)), \"}\", UnparseLatex(FunctionToList(expr)[2], RightPrecedenceGet(Type(expr)))) );120 # UnparseLatex(expr_Function?, _p)::(ArgumentsCount(expr) =? 1 And? Prefix?(Type(expr))) <-- UnparseLatexBracketIf(p <? PrecedenceGet(Type(expr)), ConcatStrings(UnparseLatexLatexify(Type(expr)), UnparseLatex(FunctionToList(expr)[2], RightPrecedenceGet(Type(expr)))) ); 120 # UnparseLatex(expr_Function?, _p)::(ArgumentsCount(expr) =? 1 And? Postfix?(Type(expr))) <-- UnparseLatexBracketIf(p <? LeftPrecedenceGet(Type(expr)), ConcatStrings( UnparseLatex(FunctionToList(expr)[2], LeftPrecedenceGet(Type(expr))), UnparseLatexLatexify(Type(expr))) ); 98 # UnparseLatex(_x / _y, _p)::(Assigned?(operatorMetaMap) And? MetaGet(operatorMetaMap,\"HighlightColor\") !=? Empty) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(\"\\\\colorbox{\",MetaGet(operatorMetaMap,\"HighlightColor\"),\"}{\\\\frac \\\\textcolor{Black}{\", UnparseLatex(x, UnparseLatexMaxPrec()), \"} \\\\textcolor{Black}{\", UnparseLatex(y, UnparseLatexMaxPrec()), \"}} \") );100 # UnparseLatex(_x / _y, _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(\"\\\\frac{\", UnparseLatex(x, UnparseLatexMaxPrec()), \"}{\", UnparseLatex(y, UnparseLatexMaxPrec()), \"} \") ); 100 # UnparseLatex(_x ^ (1/2), _p) <-- ConcatStrings(\"\\\\sqrt{\", UnparseLatex(x, UnparseLatexMaxPrec()), \"}\");101 # UnparseLatex(_x ^ (1/_y), _p) <-- ConcatStrings(\"\\\\sqrt[\", UnparseLatex(y, UnparseLatexMaxPrec()), \"]{\", UnparseLatex(x, UnparseLatexMaxPrec()), \"}\");120 # UnparseLatex(_x ^ _y, _p) <-- UnparseLatexBracketIf(p <=? PrecedenceGet(\"^\"), ConcatStrings(UnparseLatex(x, PrecedenceGet(\"^\")), \" ^{\", UnparseLatex(y, UnparseLatexMaxPrec()), \"}\" ) );100 # UnparseLatex(If(_pred) _body, _p) <-- \"\\\\textrm{if }(\" ~ UnparseLatex(pred,60000) ~ \") \" ~ UnparseLatex(body,60000);100 # UnparseLatex(_left Else _right, _p) <-- UnparseLatex(left,60000) ~ \"\\\\textrm{ Else }\" ~ UnparseLatex(right,60000);LocalSymbols(UnparseLatexRegularOps, UnparseLatexRegularPrefixOps, UnparseLatexGreekLetters, UnparseLatexSpecialNames) {  UnparseLatexRegularOps := [ [\"+\",\" + \"], [\"-\",\" - \"], [\"*\",\" \\\\times \"], [\":=\",\" := \"],  [\"==\",\" = \"], [\"=?\",\" = \"], [\"!=?\",\"\\\\neq \"], [\"<=?\",\"\\\\leq \"], [\">=?\",\"\\\\geq \"], [\"<?\",\" < \"], [\">?\",\" > \"], [\"And?\",\"\\\\wedge \"], [\"Or?\", \"\\\\vee \"], [\"<>\", \"\\\\sim \"], [\"<=>\", \"\\\\approx \"], [\"Implies?\", \"\\\\Rightarrow \"], [\"Equivales?\", \"\\\\equiv \"], [\"%\", \"\\\\bmod \"], ]; UnparseLatexRegularPrefixOps := [ [\"+\",\" + \"], [\"-\",\" - \"], [\"Not?\",\" \\\\neg \"] ];  UnparseLatexGreekLetters := [\"Gamma\", \"Delta\", \"Theta\", \"Lambda\", \"Xi\", \"Pi\", \"Sigma\", \"Upsilon\", \"Phi\", \"Psi\", \"Omega\", \"alpha\", \"beta\", \"gamma\", \"delta\", \"epsilon\", \"zeta\", \"eta\", \"theta\", \"iota\", \"kappa\", \"lambda\", \"mu\", \"nu\", \"xi\", \"pi\", \"rho\", \"sigma\", \"tau\", \"upsilon\", \"phi\", \"chi\", \"psi\", \"omega\", \"varpi\", \"varrho\", \"varsigma\", \"varphi\", \"varepsilon\"]; UnparseLatexSpecialNames := [ [\"I\", \"\\\\imath \"],  [\"Pi\", \"\\\\pi \"],  [\"Infinity\", \"\\\\infty \"], [\"TeX\", \"\\\\textrm{\\\\TeX\\\\/}\"], [\"LaTeX\", \"\\\\textrm{\\\\LaTeX\\\\/}\"], [\"Maximum\", \"\\\\max \"],  [\"Minimum\", \"\\\\min \"], [\"Block\", \" \"], [\"Zeta\", \"\\\\zeta \"], ];  Function (\"UnparseLatexLatexify\", [string]) { Check(String?(string), \"Argument\", \"UnparseLatex internal error: non-string argument of UnparseLatexLatexify\");  Decide(Contains?(AssocIndices(UnparseLatexSpecialNames), string), UnparseLatexSpecialNames[string], Decide(Contains?(UnparseLatexGreekLetters, string), ConcatStrings(\"\\\\\", string, \" \"), Decide(Contains?(AssocIndices(UnparseLatexRegularOps), string), UnparseLatexRegularOps[string], Decide(Contains?(AssocIndices(UnparseLatexRegularPrefixOps), string), UnparseLatexRegularPrefixOps[string], Decide(Length(string) >=? 2 And? Number?(ToAtom(StringMidGet(2, Length(string)-1, string))), ConcatStrings(StringMidGet(1,1,string), \"_{\", StringMidGet(2, Length(string)-1, string), \"}\"), Decide(Length(string) >? 2, ConcatStrings(\"\\\\mathrm{ \", string, \" }\"), string )))))); };};200 # UnparseLatex(x_Function?, _p):: (Bodied?(Type(x))) <-- { Local(func, args, lastarg); func := Type(x); args := Rest(FunctionToList(x)); lastarg := PopBack(args); UnparseLatexBracketIf(p <? PrecedenceGet(func), ConcatStrings( UnparseLatexLatexify(func), UnparseLatex(args, UnparseLatexMaxPrec()), UnparseLatex(lastarg, PrecedenceGet(func)) ));};220 # UnparseLatex(x_Function?, _p) <-- ConcatStrings(UnparseLatexLatexify(Type(x)), UnparseLatex(Rest(FunctionToList(x)), UnparseLatexMaxPrec()) ); 100 # UnparseLatex(Sqrt(_x), _p) <-- ConcatStrings(\"\\\\sqrt{\", UnparseLatex(x, UnparseLatexMaxPrec()), \"}\"); 100 # UnparseLatex(Exp(_x), _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(\"\\\\exp \", UnparseLatexBracketIf(True, UnparseLatex(x, UnparseLatexMaxPrec())) ) );LocalSymbols(UnparseLatexMathFunctions, UnparseLatexMathFunctions2) {   UnparseLatexMathFunctions := [ [\"Cos\",\"\\\\cos \"], [\"Sin\",\"\\\\sin \"], [\"Tan\",\"\\\\tan \"], [\"Cosh\",\"\\\\cosh \"], [\"Sinh\",\"\\\\sinh \"], [\"Tanh\",\"\\\\tanh \"], [\"Ln\",\"\\\\ln \"], [\"ArcCos\",\"\\\\arccos \"], [\"ArcSin\",\"\\\\arcsin \"], [\"ArcTan\",\"\\\\arctan \"], [\"ArcCosh\",\"\\\\mathrm{arccosh}\\\\, \"], [\"ArcSinh\",\"\\\\mathrm{arcsinh}\\\\, \"], [\"ArcTanh\",\"\\\\mathrm{arctanh}\\\\, \"], [\"Erf\", \"\\\\mathrm{erf}\\\\, \"], [\"Erfc\", \"\\\\mathrm{erfc}\\\\, \"], ];  UnparseLatexMathFunctions2 := [ [\"BesselI\", \"I \"], [\"BesselJ\", \"J \"], [\"BesselK\", \"K \"], [\"BesselY\", \"Y \"], [\"OrthoH\", \"H \"], [\"OrthoP\", \"P \"], [\"OrthoT\", \"T \"], [\"OrthoU\", \"U \"], ];    120 # UnparseLatex(expr_Function?, _p):: (ArgumentsCount(expr) =? 1 And? Contains?(AssocIndices(UnparseLatexMathFunctions), Type(expr)) ) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"*\"), ConcatStrings(UnparseLatexMathFunctions[Type(expr)], UnparseLatex( FunctionToList(expr)[2], PrecedenceGet(\"*\")) ) );  120 # UnparseLatex(expr_Function?, _p):: (ArgumentsCount(expr) =? 2 And? Contains?(AssocIndices(UnparseLatexMathFunctions2), Type(expr)) ) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"*\"), ConcatStrings( UnparseLatexMathFunctions2[Type(expr)], \"_{\", UnparseLatex( FunctionToList(expr)[2], UnparseLatexMaxPrec()),  \"}\", UnparseLatexBracketIf(True, UnparseLatex(FunctionToList(expr)[3], UnparseLatexMaxPrec()) )  ) );}; 100 # UnparseLatex(Complex(0, 1), _p) <-- UnparseLatex(Hold(I), p);100 # UnparseLatex(Complex(_x, 0), _p) <-- UnparseLatex(x, p);110 # UnparseLatex(Complex(_x, 1), _p) <-- UnparseLatex(x + Hold(I), p);110 # UnparseLatex(Complex(0, _y), _p) <-- UnparseLatex(Hold(I)*y, p);120 # UnparseLatex(Complex(_x, _y), _p) <-- UnparseLatex(x + Hold(I)*y, p);100 # UnparseLatex(Abs(_x), _p) <-- ConcatStrings(\"\\\\left| \", UnparseLatex(x, UnparseLatexMaxPrec()), \"\\\\right| \");100 # UnparseLatex(Floor(_x), _p) <-- ConcatStrings(\"\\\\left\\\\lfloor \", UnparseLatex(x, UnparseLatexMaxPrec()), \"\\\\right\\\\rfloor \");100 # UnparseLatex(Ceil(_x), _p) <-- ConcatStrings(\"\\\\left\\\\lceil \", UnparseLatex(x, UnparseLatexMaxPrec()), \"\\\\right\\\\rceil \");100 # UnparseLatex(Modulo(_x, _y), _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(UnparseLatex(x, PrecedenceGet(\"/\")), \"\\\\bmod \", UnparseLatex(y, PrecedenceGet(\"/\")) ) );100 # UnparseLatex(Union(_x, _y), _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(UnparseLatex(x, PrecedenceGet(\"/\")), \"\\\\cup \", UnparseLatex(y, PrecedenceGet(\"/\")) ) );100 # UnparseLatex(Intersection(_x, _y), _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(UnparseLatex(x, PrecedenceGet(\"/\")), \"\\\\cap \", UnparseLatex(y, PrecedenceGet(\"/\")) ) );100 # UnparseLatex(Difference(_x, _y), _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(UnparseLatex(x, PrecedenceGet(\"/\")), \"\\\\setminus \", UnparseLatex(y, PrecedenceGet(\"/\")) ) );100 # UnparseLatex(Contains?(_x, _y), _p) <-- UnparseLatexBracketIf(p <? PrecedenceGet(\"/\"), ConcatStrings(UnparseLatex(y, PrecedenceGet(\"/\")), \"\\\\in \", UnparseLatex(x, PrecedenceGet(\"/\")) ) );100 # UnparseLatex(BinomialCoefficient(_n, _m), _p) <-- UnparseLatexBracketIf(False, ConcatStrings(\"{\", UnparseLatex(n, UnparseLatexMaxPrec()), \" \\\\choose \", UnparseLatex(m, UnparseLatexMaxPrec()), \"}\" ));RulebaseHoldArguments(\"UnparseLatexNth\",[x,y]);100 # UnparseLatex(Nth(Nth(_x, i_List?), _j), _p) <-- UnparseLatex(UnparseLatexNth(x, Append(i,j)), p);100 # UnparseLatex(UnparseLatexNth(Nth(_x, i_List?), _j), _p) <-- UnparseLatex(UnparseLatexNth(x, Append(i,j)), p);110 # UnparseLatex(Nth(Nth(_x, _i), _j), _p) <-- UnparseLatex(UnparseLatexNth(x, List(i,j)), p);120 # UnparseLatex(Nth(_x, _i), _p) <-- ConcatStrings(UnparseLatex(x, UnparseLatexMaxPrec()), \" _{\", UnparseLatex(i, UnparseLatexMaxPrec()), \"}\");120 # UnparseLatex(UnparseLatexNth(_x, _i), _p) <-- ConcatStrings(UnparseLatex(x, UnparseLatexMaxPrec()), \" _{\", UnparseLatex(i, UnparseLatexMaxPrec()), \"}\");80 # UnparseLatex(M_Matrix?, _p) <-- UnparseLatexMatrixBracketIf(True, UnparseLatexPrintMatrix(M));Function (\"UnparseLatexPrintMatrix\", [M]){ Local(row, col, result, ncol); result := \"\\\\begin{array}{\"; ForEach(col, M[1]) result:=ConcatStrings(result, \"c\"); result := ConcatStrings(result, \"}\"); ForEach(row, 1 .. Length(M)) { ForEach(col, 1 .. Length(M[row])) { result := ConcatStrings( result, \" \", UnparseLatex(M[row][col], UnparseLatexMaxPrec()), Decide(col =? Length(M[row]), Decide(row =? Length(M), \"\", \" \\\\\\\\\"), \" &\")); }; }; ConcatStrings(result, \" \\\\end{array} \");};";
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
