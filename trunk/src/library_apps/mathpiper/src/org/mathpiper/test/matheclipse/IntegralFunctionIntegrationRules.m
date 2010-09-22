(* ::Package:: *)

(* ::Title:: *)
(*Integral Function Integration Rules*)


(* ::Subsection::Closed:: *)
(*Exponential Integral En Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*ExpIntegralE[n,a+b x]			Exponential integral E function of linears*)


(* ::Item:: *)
(*Basis: D[ExpIntegralE[n,z],z] == -ExpIntegralE[n-1,z]*)


Int[ExpIntegralE[n_,a_.+b_.*x_],x_Symbol] :=
  -ExpIntegralE[n+1,a+b*x]/b /;
FreeQ[{a,b,n},x]


(* ::Subsubsection::Closed:: *)
(*x^m ExpIntegralE[n,a+b x]		Products of monomials and exponential integral E function of linears*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ExpIntegralE[n_,a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*ExpIntegralE[n,a+b*x]/(m+1) +
  Dist[b/(m+1),Int[x^(m+1)*ExpIntegralE[n-1,a+b*x],x]] /;
FreeQ[{a,b,m},x] && IntegerQ[n] && n>0


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_.*ExpIntegralE[n_,a_.+b_.*x_],x_Symbol] :=
  -x^m*ExpIntegralE[n+1,a+b*x]/b +
  Dist[m/b,Int[x^(m-1)*ExpIntegralE[n+1,a+b*x],x]] /;
FreeQ[{a,b,m},x] && IntegerQ[n] && n<0


(* ::Subsection::Closed:: *)
(*Exponential Integral Ei Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*ExpIntegralEi[a+b x]^n		Powers of exponential integral function of linears*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ExpIntegralEi[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*ExpIntegralEi[a+b*x]/b - E^(a+b*x)/b /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ExpIntegralEi[a_.+b_.*x_]^2,x_Symbol] :=
  (a+b*x)*ExpIntegralEi[a+b*x]^2/b -
  Dist[2,Int[E^(a+b*x)*ExpIntegralEi[a+b*x],x]] /;
FreeQ[{a,b},x]


(* ::Subsubsection::Closed:: *)
(*x^m ExpIntegralEi[a+b x]^n		Products of monomials and powers of exponential integral functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ExpIntegralEi[a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*ExpIntegralEi[a+b*x]/(m+1) -
  Dist[b/(m+1),Int[x^(m+1)*E^(a+b*x)/(a+b*x),x]] /;
FreeQ[{a,b,m},x] && NonzeroQ[m+1]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ExpIntegralEi[b_.*x_]^2,x_Symbol] :=
  x^(m+1)*ExpIntegralEi[b*x]^2/(m+1) -
  Dist[2/(m+1),Int[x^m*E^(b*x)*ExpIntegralEi[b*x],x]] /;
FreeQ[b,x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Derivation: Iterated integration by parts*)


Int[x_^m_.*ExpIntegralEi[a_+b_.*x_]^2,x_Symbol] :=
  x^(m+1)*ExpIntegralEi[a+b*x]^2/(m+1) +
  a*x^m*ExpIntegralEi[a+b*x]^2/(b*(m+1)) -
  Dist[2/(m+1),Int[x^m*E^(a+b*x)*ExpIntegralEi[a+b*x],x]] -
  Dist[a*m/(b*(m+1)),Int[x^(m-1)*ExpIntegralEi[a+b*x]^2,x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


(* Int[x_^m_.*ExpIntegralEi[a_+b_.*x_]^2,x_Symbol] :=
  b*x^(m+2)*ExpIntegralEi[a+b*x]^2/(a*(m+1)) +
  x^(m+1)*ExpIntegralEi[a+b*x]^2/(m+1) -
  Dist[2*b/(a*(m+1)),Int[x^(m+1)*E^(a+b*x)*ExpIntegralEi[a+b*x],x]] -
  Dist[b*(m+2)/(a*(m+1)),Int[x^(m+1)*ExpIntegralEi[a+b*x]^2,x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m<-2 *)


(* ::Subsubsection::Closed:: *)
(*E^(a+b x) ExpIntegralEi[c+d x]	Products of exponential and exponential integral functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[E^(a_.+b_.*x_)*ExpIntegralEi[c_.+d_.*x_],x_Symbol] :=
  E^(a+b*x)*ExpIntegralEi[c+d*x]/b -
  Dist[d/b,Int[E^(a+b*x)*E^(c+d*x)/(c+d*x),x]] /;
FreeQ[{a,b,c,d},x]


(* ::Subsubsection::Closed:: *)
(*x^m E^(a+b x) ExpIntegralEi[c+d x]	Products of monomials, exponential and exponential integral functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*E^(a_.+b_.*x_)*ExpIntegralEi[c_.+d_.*x_],x_Symbol] :=
  x^m*E^(a+b*x)*ExpIntegralEi[c+d*x]/b -
  Dist[d/b,Int[x^m*E^(a+b*x)*E^(c+d*x)/(c+d*x),x]] -
  Dist[m/b,Int[x^(m-1)*E^(a+b*x)*ExpIntegralEi[c+d*x],x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_*E^(a_.+b_.*x_)*ExpIntegralEi[c_.+d_.*x_],x_Symbol] :=
  x^(m+1)*E^(a+b*x)*ExpIntegralEi[c+d*x]/(m+1) -
  Dist[d/(m+1),Int[x^(m+1)*E^(a+b*x)*E^(c+d*x)/(c+d*x),x]] -
  Dist[b/(m+1),Int[x^(m+1)*E^(a+b*x)*ExpIntegralEi[c+d*x],x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m<-1


(* ::Subsection::Closed:: *)
(*Sine Integral Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*SinIntegral[a+b x]^n			Powers of sine integral function of linears*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[SinIntegral[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*SinIntegral[a+b*x]/b + Cos[a+b*x]/b/;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[SinIntegral[a_.+b_.*x_]^2,x_Symbol] :=
  (a+b*x)*SinIntegral[a+b*x]^2/b -
  Dist[2,Int[Sin[a+b*x]*SinIntegral[a+b*x],x]] /;
FreeQ[{a,b},x]


(* ::Subsubsection::Closed:: *)
(*x^m SinIntegral[a+b x]^n		Products of monomials and powers of sine integral functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*SinIntegral[a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*SinIntegral[a+b*x]/(m+1) -
  Dist[b/(m+1),Int[x^(m+1)*Sin[a+b*x]/(a+b*x),x]] /;
FreeQ[{a,b,m},x] && NonzeroQ[m+1]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*SinIntegral[b_.*x_]^2,x_Symbol] :=
  x^(m+1)*SinIntegral[b*x]^2/(m+1) -
  Dist[2/(m+1),Int[x^m*Sin[b*x]*SinIntegral[b*x],x]] /;
FreeQ[b,x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Derivation: Iterated integration by parts*)


Int[x_^m_.*SinIntegral[a_+b_.*x_]^2,x_Symbol] :=
  x^(m+1)*SinIntegral[a+b*x]^2/(m+1) +
  a*x^m*SinIntegral[a+b*x]^2/(b*(m+1)) -
  Dist[2/(m+1),Int[x^m*Sin[a+b*x]*SinIntegral[a+b*x],x]] -
  Dist[a*m/(b*(m+1)),Int[x^(m-1)*SinIntegral[a+b*x]^2,x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


(* Int[x_^m_.*SinIntegral[a_+b_.*x_]^2,x_Symbol] :=
  b*x^(m+2)*SinIntegral[a+b*x]^2/(a*(m+1)) +
  x^(m+1)*SinIntegral[a+b*x]^2/(m+1) -
  Dist[2*b/(a*(m+1)),Int[x^(m+1)*Sin[a+b*x]*SinIntegral[a+b*x],x]] -
  Dist[b*(m+2)/(a*(m+1)),Int[x^(m+1)*SinIntegral[a+b*x]^2,x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m<-2 *)


(* ::Subsubsection::Closed:: *)
(*Sin[a+b x] SinIntegral[c+d x]		Products of sine and sine integral functions*)


(* ::Item::Closed:: *)
(*Reference: G&R 5.32.2*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Sin[a_.+b_.*x_]*SinIntegral[c_.+d_.*x_],x_Symbol] :=
  -Cos[a+b*x]*SinIntegral[c+d*x]/b +
  Dist[d/b,Int[Cos[a+b*x]*Sin[c+d*x]/(c+d*x),x]] /;
FreeQ[{a,b,c,d},x]


(* ::Subsubsection::Closed:: *)
(*x^m Sin[a+b x] SinIntegral[c+d x]	Products of monomials, sine and sine integral functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Sin[a_.+b_.*x_]*SinIntegral[c_.+d_.*x_],x_Symbol] :=
  -x^m*Cos[a+b*x]*SinIntegral[c+d*x]/b +
  Dist[d/b,Int[x^m*Cos[a+b*x]*Sin[c+d*x]/(c+d*x),x]] +
  Dist[m/b,Int[x^(m-1)*Cos[a+b*x]*SinIntegral[c+d*x],x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_*Sin[a_.+b_.*x_]*SinIntegral[c_.+d_.*x_],x_Symbol] :=
  x^(m+1)*Sin[a+b*x]*SinIntegral[c+d*x]/(m+1) -
  Dist[d/(m+1),Int[x^(m+1)*Sin[a+b*x]*Sin[c+d*x]/(c+d*x),x]] -
  Dist[b/(m+1),Int[x^(m+1)*Cos[a+b*x]*SinIntegral[c+d*x],x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m<-1


(* ::Subsubsection::Closed:: *)
(*Cos[a+b x] SinIntegral[c+d x]		Products of cosine and sine integral functions*)


(* ::Item::Closed:: *)
(*Reference: G&R 5.32.1*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Cos[a_.+b_.*x_]*SinIntegral[c_.+d_.*x_],x_Symbol] :=
  Sin[a+b*x]*SinIntegral[c+d*x]/b -
  Dist[d/b,Int[Sin[a+b*x]*Sin[c+d*x]/(c+d*x),x]] /;
FreeQ[{a,b,c,d},x]


(* ::Subsubsection::Closed:: *)
(*x^m Cos[a+b x] SinIntegral[c+d x]	Products of monomials, cosine and sine integral functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Cos[a_.+b_.*x_]*SinIntegral[c_.+d_.*x_],x_Symbol] :=
  x^m*Sin[a+b*x]*SinIntegral[c+d*x]/b -
  Dist[d/b,Int[x^m*Sin[a+b*x]*Sin[c+d*x]/(c+d*x),x]] -
  Dist[m/b,Int[x^(m-1)*Sin[a+b*x]*SinIntegral[c+d*x],x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_.*Cos[a_.+b_.*x_]*SinIntegral[c_.+d_.*x_],x_Symbol] :=
  x^(m+1)*Cos[a+b*x]*SinIntegral[c+d*x]/(m+1) -
  Dist[d/(m+1),Int[x^(m+1)*Cos[a+b*x]*Sin[c+d*x]/(c+d*x),x]] +
  Dist[b/(m+1),Int[x^(m+1)*Sin[a+b*x]*SinIntegral[c+d*x],x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m<-1


(* ::Subsection::Closed:: *)
(*Cosine Integral Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*CosIntegral[a+b x]^n			Powers of cosine integral function of linears*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[CosIntegral[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*CosIntegral[a+b*x]/b - Sin[a+b*x]/b /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[CosIntegral[a_.+b_.*x_]^2,x_Symbol] :=
  (a+b*x)*CosIntegral[a+b*x]^2/b -
  Dist[2,Int[Cos[a+b*x]*CosIntegral[a+b*x],x]] /;
FreeQ[{a,b},x]


(* ::Subsubsection::Closed:: *)
(*x^m CosIntegral[a+b x]^n		Products of monomials and powers of cosine integral functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*CosIntegral[a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*CosIntegral[a+b*x]/(m+1) -
  Dist[b/(m+1),Int[x^(m+1)*Cos[a+b*x]/(a+b*x),x]] /;
FreeQ[{a,b,m},x] && NonzeroQ[m+1]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*CosIntegral[b_.*x_]^2,x_Symbol] :=
  x^(m+1)*CosIntegral[b*x]^2/(m+1) -
  Dist[2/(m+1),Int[x^m*Cos[b*x]*CosIntegral[b*x],x]] /;
FreeQ[b,x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Derivation: Iterated integration by parts*)


Int[x_^m_.*CosIntegral[a_+b_.*x_]^2,x_Symbol] :=
  x^(m+1)*CosIntegral[a+b*x]^2/(m+1) +
  a*x^m*CosIntegral[a+b*x]^2/(b*(m+1)) -
  Dist[2/(m+1),Int[x^m*Cos[a+b*x]*CosIntegral[a+b*x],x]] -
  Dist[a*m/(b*(m+1)),Int[x^(m-1)*CosIntegral[a+b*x]^2,x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


(* Int[x_^m_.*CosIntegral[a_+b_.*x_]^2,x_Symbol] :=
  b*x^(m+2)*CosIntegral[a+b*x]^2/(a*(m+1)) +
  x^(m+1)*CosIntegral[a+b*x]^2/(m+1) -
  Dist[2*b/(a*(m+1)),Int[x^(m+1)*Cos[a+b*x]*CosIntegral[a+b*x],x]] -
  Dist[b*(m+2)/(a*(m+1)),Int[x^(m+1)*CosIntegral[a+b*x]^2,x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m<-2 *)


(* ::Subsubsection::Closed:: *)
(*Sin[a+b x] CosIntegral[c+d x]		Products of sine and cosine integral functions*)


(* ::Item::Closed:: *)
(*Reference: G&R 5.31.2*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Sin[a_.+b_.*x_]*CosIntegral[c_.+d_.*x_],x_Symbol] :=
  -Cos[a+b*x]*CosIntegral[c+d*x]/b +
  Dist[d/b,Int[Cos[a+b*x]*Cos[c+d*x]/(c+d*x),x]] /;
FreeQ[{a,b,c,d},x]


(* ::Subsubsection::Closed:: *)
(*x^m Sin[a+b x] CosIntegral[c+d x]	Products of monomials, sine and cosine integral functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Sin[a_.+b_.*x_]*CosIntegral[c_.+d_.*x_],x_Symbol] :=
  -x^m*Cos[a+b*x]*CosIntegral[c+d*x]/b +
  Dist[d/b,Int[x^m*Cos[a+b*x]*Cos[c+d*x]/(c+d*x),x]] +
  Dist[m/b,Int[x^(m-1)*Cos[a+b*x]*CosIntegral[c+d*x],x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_*Sin[a_.+b_.*x_]*CosIntegral[c_.+d_.*x_],x_Symbol] :=
  x^(m+1)*Sin[a+b*x]*CosIntegral[c+d*x]/(m+1) -
  Dist[d/(m+1),Int[x^(m+1)*Sin[a+b*x]*Cos[c+d*x]/(c+d*x),x]] -
  Dist[b/(m+1),Int[x^(m+1)*Cos[a+b*x]*CosIntegral[c+d*x],x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m<-1


(* ::Subsubsection::Closed:: *)
(*Cos[a+b x] CosIntegral[c+d x]	Products of cosine and cosine integral functions*)


(* ::Item::Closed:: *)
(*Reference: G&R 5.31.1*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Cos[a_.+b_.*x_]*CosIntegral[c_.+d_.*x_],x_Symbol] :=
  Sin[a+b*x]*CosIntegral[c+d*x]/b -
  Dist[d/b,Int[Sin[a+b*x]*Cos[c+d*x]/(c+d*x),x]] /;
FreeQ[{a,b,c,d},x]


(* ::Subsubsection::Closed:: *)
(*x^m Cos[a+b x] CosIntegral[c+d x]	Products of monomials, cosine and cosine integral functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Cos[a_.+b_.*x_]*CosIntegral[c_.+d_.*x_],x_Symbol] :=
  x^m*Sin[a+b*x]*CosIntegral[c+d*x]/b -
  Dist[d/b,Int[x^m*Sin[a+b*x]*Cos[c+d*x]/(c+d*x),x]] -
  Dist[m/b,Int[x^(m-1)*Sin[a+b*x]*CosIntegral[c+d*x],x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_.*Cos[a_.+b_.*x_]*CosIntegral[c_.+d_.*x_],x_Symbol] :=
  x^(m+1)*Cos[a+b*x]*CosIntegral[c+d*x]/(m+1) -
  Dist[d/(m+1),Int[x^(m+1)*Cos[a+b*x]*Cos[c+d*x]/(c+d*x),x]] +
  Dist[b/(m+1),Int[x^(m+1)*Sin[a+b*x]*CosIntegral[c+d*x],x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m<-1


(* ::Subsection::Closed:: *)
(*Hyperbolic Sine Integral Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*SinhIntegral[a+b x]^n			Powers of hyperbolic sine integral function of linears*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[SinhIntegral[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*SinhIntegral[a+b*x]/b - Cosh[a+b*x]/b/;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[SinhIntegral[a_.+b_.*x_]^2,x_Symbol] :=
  (a+b*x)*SinhIntegral[a+b*x]^2/b -
  Dist[2,Int[Sinh[a+b*x]*SinhIntegral[a+b*x],x]] /;
FreeQ[{a,b},x]


(* ::Subsubsection::Closed:: *)
(*x^m SinhIntegral[a+b x]^n		Products of monomials and powers of hyperbolic sine integral functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*SinhIntegral[a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*SinhIntegral[a+b*x]/(m+1) -
  Dist[b/(m+1),Int[x^(m+1)*Sinh[a+b*x]/(a+b*x),x]] /;
FreeQ[{a,b,m},x] && NonzeroQ[m+1]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*SinhIntegral[b_.*x_]^2,x_Symbol] :=
  x^(m+1)*SinhIntegral[b*x]^2/(m+1) -
  Dist[2/(m+1),Int[x^m*Sinh[b*x]*SinhIntegral[b*x],x]] /;
FreeQ[b,x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Derivation: Iterated integration by parts*)


Int[x_^m_.*SinhIntegral[a_+b_.*x_]^2,x_Symbol] :=
  x^(m+1)*SinhIntegral[a+b*x]^2/(m+1) +
  a*x^m*SinhIntegral[a+b*x]^2/(b*(m+1)) -
  Dist[2/(m+1),Int[x^m*Sinh[a+b*x]*SinhIntegral[a+b*x],x]] -
  Dist[a*m/(b*(m+1)),Int[x^(m-1)*SinhIntegral[a+b*x]^2,x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


(* Int[x_^m_.*SinhIntegral[a_+b_.*x_]^2,x_Symbol] :=
  b*x^(m+2)*SinhIntegral[a+b*x]^2/(a*(m+1)) +
  x^(m+1)*SinhIntegral[a+b*x]^2/(m+1) -
  Dist[2*b/(a*(m+1)),Int[x^(m+1)*Sinh[a+b*x]*SinhIntegral[a+b*x],x]] -
  Dist[b*(m+2)/(a*(m+1)),Int[x^(m+1)*SinhIntegral[a+b*x]^2,x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m<-2 *)


(* ::Subsubsection::Closed:: *)
(*Sinh[a+b x] SinhIntegral[c+d x]	Products of hyperbolic sine and hyperbolic sine integral functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Sinh[a_.+b_.*x_]*SinhIntegral[c_.+d_.*x_],x_Symbol] :=
  Cosh[a+b*x]*SinhIntegral[c+d*x]/b -
  Dist[d/b,Int[Cosh[a+b*x]*Sinh[c+d*x]/(c+d*x),x]] /;
FreeQ[{a,b,c,d},x]


(* ::Subsubsection::Closed:: *)
(*x^m Sinh[a+b x] SinhIntegral[c+d x]	Products of monomials, hyperbolic sine and hyperbolic sine integral functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Sinh[a_.+b_.*x_]*SinhIntegral[c_.+d_.*x_],x_Symbol] :=
  x^m*Cosh[a+b*x]*SinhIntegral[c+d*x]/b -
  Dist[d/b,Int[x^m*Cosh[a+b*x]*Sinh[c+d*x]/(c+d*x),x]] -
  Dist[m/b,Int[x^(m-1)*Cosh[a+b*x]*SinhIntegral[c+d*x],x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_*Sinh[a_.+b_.*x_]*SinhIntegral[c_.+d_.*x_],x_Symbol] :=
  x^(m+1)*Sinh[a+b*x]*SinhIntegral[c+d*x]/(m+1) -
  Dist[d/(m+1),Int[x^(m+1)*Sinh[a+b*x]*Sinh[c+d*x]/(c+d*x),x]] -
  Dist[b/(m+1),Int[x^(m+1)*Cosh[a+b*x]*SinhIntegral[c+d*x],x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m<-1


(* ::Subsubsection::Closed:: *)
(*Cosh[a+b x] SinhIntegral[c+d x]	Products of hyperbolic cosine and hyperbolic sine integral functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Cosh[a_.+b_.*x_]*SinhIntegral[c_.+d_.*x_],x_Symbol] :=
  Sinh[a+b*x]*SinhIntegral[c+d*x]/b -
  Dist[d/b,Int[Sinh[a+b*x]*Sinh[c+d*x]/(c+d*x),x]] /;
FreeQ[{a,b,c,d},x]


(* ::Subsubsection::Closed:: *)
(*x^m Cosh[a+b x] SinhIntegral[c+d x]	Products of monomials, hyperbolic cosine and hyperbolic sine integrals*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Cosh[a_.+b_.*x_]*SinhIntegral[c_.+d_.*x_],x_Symbol] :=
  x^m*Sinh[a+b*x]*SinhIntegral[c+d*x]/b -
  Dist[d/b,Int[x^m*Sinh[a+b*x]*Sinh[c+d*x]/(c+d*x),x]] -
  Dist[m/b,Int[x^(m-1)*Sinh[a+b*x]*SinhIntegral[c+d*x],x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_.*Cosh[a_.+b_.*x_]*SinhIntegral[c_.+d_.*x_],x_Symbol] :=
  x^(m+1)*Cosh[a+b*x]*SinhIntegral[c+d*x]/(m+1) -
  Dist[d/(m+1),Int[x^(m+1)*Cosh[a+b*x]*Sinh[c+d*x]/(c+d*x),x]] -
  Dist[b/(m+1),Int[x^(m+1)*Sinh[a+b*x]*SinhIntegral[c+d*x],x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m<-1


(* ::Subsection::Closed:: *)
(*Hyperbolic Cosine Integral Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*CoshIntegral[a+b x]^n		Powers of hyperbolic cosine integral function of linears*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[CoshIntegral[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*CoshIntegral[a+b*x]/b - Sinh[a+b*x]/b /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[CoshIntegral[a_.+b_.*x_]^2,x_Symbol] :=
  (a+b*x)*CoshIntegral[a+b*x]^2/b -
  Dist[2,Int[Cosh[a+b*x]*CoshIntegral[a+b*x],x]] /;
FreeQ[{a,b},x]


(* ::Subsubsection::Closed:: *)
(*x^m CoshIntegral[a+b x]^n		Products of monomials and powers of hyperbolic cosine integral functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*CoshIntegral[a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*CoshIntegral[a+b*x]/(m+1) -
  Dist[b/(m+1),Int[x^(m+1)*Cosh[a+b*x]/(a+b*x),x]] /;
FreeQ[{a,b,m},x] && NonzeroQ[m+1]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*CoshIntegral[b_.*x_]^2,x_Symbol] :=
  x^(m+1)*CoshIntegral[b*x]^2/(m+1) -
  Dist[2/(m+1),Int[x^m*Cosh[b*x]*CoshIntegral[b*x],x]] /;
FreeQ[b,x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Derivation: Iterated integration by parts*)


Int[x_^m_.*CoshIntegral[a_+b_.*x_]^2,x_Symbol] :=
  x^(m+1)*CoshIntegral[a+b*x]^2/(m+1) +
  a*x^m*CoshIntegral[a+b*x]^2/(b*(m+1)) -
  Dist[2/(m+1),Int[x^m*Cosh[a+b*x]*CoshIntegral[a+b*x],x]] -
  Dist[a*m/(b*(m+1)),Int[x^(m-1)*CoshIntegral[a+b*x]^2,x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


(* Int[x_^m_.*CoshIntegral[a_+b_.*x_]^2,x_Symbol] :=
  b*x^(m+2)*CoshIntegral[a+b*x]^2/(a*(m+1)) +
  x^(m+1)*CoshIntegral[a+b*x]^2/(m+1) -
  Dist[2*b/(a*(m+1)),Int[x^(m+1)*Cosh[a+b*x]*CoshIntegral[a+b*x],x]] -
  Dist[b*(m+2)/(a*(m+1)),Int[x^(m+1)*CoshIntegral[a+b*x]^2,x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m<-2 *)


(* ::Subsubsection::Closed:: *)
(*Sinh[a+b x] CoshIntegral[c+d x]	Products of hyperbolic sine and hyperbolic cosine integral functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Sinh[a_.+b_.*x_]*CoshIntegral[c_.+d_.*x_],x_Symbol] :=
  Cosh[a+b*x]*CoshIntegral[c+d*x]/b -
  Dist[d/b,Int[Cosh[a+b*x]*Cosh[c+d*x]/(c+d*x),x]] /;
FreeQ[{a,b,c,d},x]


(* ::Subsubsection::Closed:: *)
(*x^m Sinh[a+b x] CoshIntegral[c+d x]	Products of monomials, hyperbolic sine and hyperbolic cosine integrals*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Sinh[a_.+b_.*x_]*CoshIntegral[c_.+d_.*x_],x_Symbol] :=
  x^m*Cosh[a+b*x]*CoshIntegral[c+d*x]/b -
  Dist[d/b,Int[x^m*Cosh[a+b*x]*Cosh[c+d*x]/(c+d*x),x]] -
  Dist[m/b,Int[x^(m-1)*Cosh[a+b*x]*CoshIntegral[c+d*x],x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_*Sinh[a_.+b_.*x_]*CoshIntegral[c_.+d_.*x_],x_Symbol] :=
  x^(m+1)*Sinh[a+b*x]*CoshIntegral[c+d*x]/(m+1) -
  Dist[d/(m+1),Int[x^(m+1)*Sinh[a+b*x]*Cosh[c+d*x]/(c+d*x),x]] -
  Dist[b/(m+1),Int[x^(m+1)*Cosh[a+b*x]*CoshIntegral[c+d*x],x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m<-1


(* ::Subsubsection::Closed:: *)
(*Cosh[a+b x] CoshIntegral[c+d x]	Products of hyperbolic sine and hyperbolic cosine integral functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Cosh[a_.+b_.*x_]*CoshIntegral[c_.+d_.*x_],x_Symbol] :=
  Sinh[a+b*x]*CoshIntegral[c+d*x]/b -
  Dist[d/b,Int[Sinh[a+b*x]*Cosh[c+d*x]/(c+d*x),x]] /;
FreeQ[{a,b,c,d},x]


(* ::Subsubsection::Closed:: *)
(*x^m Cosh[a+b x] CoshIntegral[c+d x]Products of monomials, hyperbolic cosine and hyperbolic cosine integrals*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Cosh[a_.+b_.*x_]*CoshIntegral[c_.+d_.*x_],x_Symbol] :=
  x^m*Sinh[a+b*x]*CoshIntegral[c+d*x]/b -
  Dist[d/b,Int[x^m*Sinh[a+b*x]*Cosh[c+d*x]/(c+d*x),x]] -
  Dist[m/b,Int[x^(m-1)*Sinh[a+b*x]*CoshIntegral[c+d*x],x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_.*Cosh[a_.+b_.*x_]*CoshIntegral[c_.+d_.*x_],x_Symbol] :=
  x^(m+1)*Cosh[a+b*x]*CoshIntegral[c+d*x]/(m+1) -
  Dist[d/(m+1),Int[x^(m+1)*Cosh[a+b*x]*Cosh[c+d*x]/(c+d*x),x]] -
  Dist[b/(m+1),Int[x^(m+1)*Sinh[a+b*x]*CoshIntegral[c+d*x],x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m<-1


(* ::Subsection::Closed:: *)
(*Logarithmic Integral Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*LogIntegral[a+b x]^n			Powers of log integral of linears*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[LogIntegral[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*LogIntegral[a+b*x]/b - ExpIntegralEi[2*Log[a+b*x]]/b /;
FreeQ[{a,b},x]


(* ::Subsubsection::Closed:: *)
(*x^m LogIntegral[a+b x]		Products of monomials and log integral of linears*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*LogIntegral[a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*LogIntegral[a+b*x]/(m+1) -
  Dist[b/(m+1),Int[x^(m+1)/Log[a+b*x],x]] /;
FreeQ[{a,b,m},x] && NonzeroQ[m+1]
