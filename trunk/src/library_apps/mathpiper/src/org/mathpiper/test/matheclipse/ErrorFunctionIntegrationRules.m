(* ::Package:: *)

(* ::Title:: *)
(*Error and Fresnel Function Integration Rules*)


(* ::Subsection::Closed:: *)
(*Error Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*Erf[a+b x]^n				Powers of error function of linears*)


(* ::Item::Closed:: *)
(*Reference: G&R 5.41*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Erf[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*Erf[a+b*x]/b + 1/(b*Sqrt[Pi]*Exp[(a+b*x)^2]) /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Erf[a_.+b_.*x_]^2,x_Symbol] :=
  (a+b*x)*Erf[a+b*x]^2/b -
  Dist[4/Sqrt[Pi],Int[(a+b*x)*Erf[a+b*x]/E^(a+b*x)^2,x]] /;
FreeQ[{a,b},x]


(* ::Subsubsection::Closed:: *)
(*x^m Erf[a+b x]^n			Products of monomials and powers of error functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Erf[a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*Erf[a+b*x]/(m+1) -
  Dist[2*b/(Sqrt[Pi]*(m+1)),Int[x^(m+1)/Exp[(a+b*x)^2],x]] /;
FreeQ[{a,b,m},x] && NonzeroQ[m+1]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Erf[b_.*x_]^2,x_Symbol] :=
  x^(m+1)*Erf[b*x]^2/(m+1) -
  Dist[4*b/(Sqrt[Pi]*(m+1)),Int[x^(m+1)*E^(-b^2*x^2)*Erf[b*x],x]] /;
FreeQ[b,x] && IntegerQ[m] && m+1!=0 && (m>0 || OddQ[m])


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Int[f[x, a+b*x], x] == Subst[Int[f[-a/b+x/b, x], x], x, a+b*x]/b*)


Int[x_^m_.*Erf[a_+b_.*x_]^2,x_Symbol] :=
  Dist[1/b,Subst[Int[(-a/b+x/b)^m*Erf[x]^2,x],x,a+b*x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0


(* ::Subsubsection::Closed:: *)
(*x^m E^(-b^2 x^2) Erf[b x]		Products of monomials, exponentials and error functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_*E^(c_.*x_^2)*Erf[b_.*x_],x_Symbol] :=
  -E^(-b^2*x^2)*Erf[b*x]/(2*b^2) +
  Dist[1/(b*Sqrt[Pi]),Int[E^(-2*b^2*x^2),x]] /;
FreeQ[{b,c},x] && c===-b^2


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_*E^(c_.*x_^2)*Erf[b_.*x_],x_Symbol] :=
  -x^(m-1)*E^(-b^2*x^2)*Erf[b*x]/(2*b^2) +
  Dist[1/(b*Sqrt[Pi]),Int[x^(m-1)*E^(-2*b^2*x^2),x]] +
  Dist[(m-1)/(2*b^2),Int[x^(m-2)*E^(-b^2*x^2)*Erf[b*x],x]] /;
FreeQ[{b,c},x] && c===-b^2 && IntegerQ[m] && m>1


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_*E^(c_.*x_^2)*Erf[b_.*x_],x_Symbol] :=
  x^(m+1)*E^(-b^2*x^2)*Erf[b*x]/(m+1) -
  Dist[2*b/(Sqrt[Pi]*(m+1)),Int[x^(m+1)*E^(-2*b^2*x^2),x]] +
  Dist[2*b^2/(m+1),Int[x^(m+2)*E^(-b^2*x^2)*Erf[b*x],x]] /;
FreeQ[{b,c},x] && c===-b^2 && EvenQ[m] && m<-1


(* ::Subsection::Closed:: *)
(*Complementary Error Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*Erfc[a+b x]^n				Powers of complementary error function of linears*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Erfc[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*Erfc[a+b*x]/b - 1/(b*Sqrt[Pi]*Exp[(a+b*x)^2]) /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Erfc[a_.+b_.*x_]^2,x_Symbol] :=
  (a+b*x)*Erfc[a+b*x]^2/b +
  Dist[4/Sqrt[Pi],Int[(a+b*x)*Erfc[a+b*x]/E^(a+b*x)^2,x]] /;
FreeQ[{a,b},x]


(* ::Subsubsection::Closed:: *)
(*x^m Erfc[ a+b x]^n			Products of monomials and powers of complementary error function*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Erfc[a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*Erfc[a+b*x]/(m+1) +
  Dist[2*b/(Sqrt[Pi]*(m+1)),Int[x^(m+1)/Exp[(a+b*x)^2],x]] /;
FreeQ[{a,b,m},x] && NonzeroQ[m+1]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Erfc[b_.*x_]^2,x_Symbol] :=
  x^(m+1)*Erfc[b*x]^2/(m+1) +
  Dist[4*b/(Sqrt[Pi]*(m+1)),Int[x^(m+1)*E^(-b^2*x^2)*Erfc[b*x],x]] /;
FreeQ[b,x] && IntegerQ[m] && m+1!=0 && (m>0 || OddQ[m])


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Int[f[x, a+b*x], x] == Subst[Int[f[-a/b+x/b, x], x], x, a+b*x]/b*)


Int[x_^m_.*Erfc[a_+b_.*x_]^2,x_Symbol] :=
  Dist[1/b,Subst[Int[(-a/b+x/b)^m*Erfc[x]^2,x],x,a+b*x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0


(* ::Subsubsection::Closed:: *)
(*x^m E^(-b^2 x^2) Erfc[b x]		Products of monomials, exponentials and complementary error functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_*E^(c_.*x_^2)*Erfc[b_.*x_],x_Symbol] :=
  -E^(-b^2*x^2)*Erfc[b*x]/(2*b^2) -
  Dist[1/(b*Sqrt[Pi]),Int[E^(-2*b^2*x^2),x]] /;
FreeQ[{b,c},x] && c===-b^2


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_*E^(c_.*x_^2)*Erfc[b_.*x_],x_Symbol] :=
  -x^(m-1)*E^(-b^2*x^2)*Erfc[b*x]/(2*b^2) -
  Dist[1/(b*Sqrt[Pi]),Int[x^(m-1)*E^(-2*b^2*x^2),x]] +
  Dist[(m-1)/(2*b^2),Int[x^(m-2)*E^(-b^2*x^2)*Erfc[b*x],x]] /;
FreeQ[{b,c},x] && c===-b^2 && IntegerQ[m] && m>1


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_*E^(c_.*x_^2)*Erfc[b_.*x_],x_Symbol] :=
  x^(m+1)*E^(-b^2*x^2)*Erfc[b*x]/(m+1) +
  Dist[2*b/(Sqrt[Pi]*(m+1)),Int[x^(m+1)*E^(-2*b^2*x^2),x]] +
  Dist[2*b^2/(m+1),Int[x^(m+2)*E^(-b^2*x^2)*Erfc[b*x],x]] /;
FreeQ[{b,c},x] && c===-b^2 && EvenQ[m] && m<-1


(* ::Subsection::Closed:: *)
(*Imaginary Error Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*Erfi[a+b x]^n				Powers of imaginary error function of linears*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Erfi[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*Erfi[a+b*x]/b - Exp[(a+b*x)^2]/(b*Sqrt[Pi]) /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Erfi[a_.+b_.*x_]^2,x_Symbol] :=
  (a+b*x)*Erfi[a+b*x]^2/b -
  Dist[4/Sqrt[Pi],Int[(a+b*x)*E^(a+b*x)^2*Erfi[a+b*x],x]] /;
FreeQ[{a,b},x]


(* ::Subsubsection::Closed:: *)
(*x^m Erfi[a+b x]^n			Products of monomials and powers of imaginary error functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Erfi[a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*Erfi[a+b*x]/(m+1) -
  Dist[2*b/(Sqrt[Pi]*(m+1)),Int[x^(m+1)*Exp[(a+b*x)^2],x]] /;
FreeQ[{a,b,m},x] && NonzeroQ[m+1]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Erfi[b_.*x_]^2,x_Symbol] :=
  x^(m+1)*Erfi[b*x]^2/(m+1) -
  Dist[4*b/(Sqrt[Pi]*(m+1)),Int[x^(m+1)*E^(b^2*x^2)*Erfi[b*x],x]] /;
FreeQ[b,x] && IntegerQ[m] && m+1!=0 && (m>0 || OddQ[m])


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Int[f[x, a+b*x], x] == Subst[Int[f[-a/b+x/b, x], x], x, a+b*x]/b*)


Int[x_^m_.*Erfi[a_+b_.*x_]^2,x_Symbol] :=
  Dist[1/b,Subst[Int[(-a/b+x/b)^m*Erfi[x]^2,x],x,a+b*x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0


(* ::Subsubsection::Closed:: *)
(*x^m E^(b^2 x^2) Erfi[b x]		Products of monomials, exponentials and imaginary error functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_*E^(c_.*x_^2)*Erfi[b_.*x_],x_Symbol] :=
  E^(b^2*x^2)*Erfi[b*x]/(2*b^2) -
  Dist[1/(b*Sqrt[Pi]),Int[E^(2*b^2*x^2),x]] /;
FreeQ[{b,c},x] && c===b^2


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_*E^(c_.*x_^2)*Erfi[b_.*x_],x_Symbol] :=
  x^(m-1)*E^(b^2*x^2)*Erfi[b*x]/(2*b^2) -
  Dist[1/(b*Sqrt[Pi]),Int[x^(m-1)*E^(2*b^2*x^2),x]] -
  Dist[(m-1)/(2*b^2),Int[x^(m-2)*E^(b^2*x^2)*Erfi[b*x],x]] /;
FreeQ[{b,c},x] && c===b^2 && IntegerQ[m] && m>1


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_*E^(c_.*x_^2)*Erfi[b_.*x_],x_Symbol] :=
  x^(m+1)*E^(b^2*x^2)*Erfi[b*x]/(m+1) -
  Dist[2*b/(Sqrt[Pi]*(m+1)),Int[x^(m+1)*E^(2*b^2*x^2),x]] -
  Dist[2*b^2/(m+1),Int[x^(m+2)*E^(b^2*x^2)*Erfi[b*x],x]] /;
FreeQ[{b,c},x] && c===b^2 && EvenQ[m] && m<-1


(* ::Subsection::Closed:: *)
(*Fresnel Integral S Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*FresnelS[a+b x]^n				Powers of Fresnel integral S functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[FresnelS[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*FresnelS[a+b*x]/b + Cos[Pi/2*(a+b*x)^2]/(b*Pi) /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[FresnelS[a_.+b_.*x_]^2,x_Symbol] :=
  (a+b*x)*FresnelS[a+b*x]^2/b -
  Dist[2,Int[(a+b*x)*Sin[Pi/2*(a+b*x)^2]*FresnelS[a+b*x],x]] /;
FreeQ[{a,b},x]


(* ::Subsubsection::Closed:: *)
(*x^m FresnelS[a+b x]^n			Products of monomials and powers of Fresnel integral S functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*FresnelS[a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*FresnelS[a+b*x]/(m+1) -
  Dist[b/(m+1),Int[x^(m+1)*Sin[Pi/2*(a+b*x)^2],x]] /;
FreeQ[{a,b,m},x] && NonzeroQ[m+1]


(* ::Item::Closed:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Note: Also apply rule when m mod 4 = 1 when a closed-form antiderivative is defined for Cos[Pi/2*x^2]*FresnelS[x].*)


Int[x_^m_*FresnelS[b_.*x_]^2,x_Symbol] :=
  x^(m+1)*FresnelS[b*x]^2/(m+1) -
  Dist[2*b/(m+1),Int[x^(m+1)*Sin[Pi/2*b^2*x^2]*FresnelS[b*x],x]] /;
FreeQ[b,x] && IntegerQ[m] && m+1!=0 && (m>0 && EvenQ[m] || Mod[m,4]==3)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Int[f[x, a+b*x], x] == Subst[Int[f[-a/b+x/b, x], x], x, a+b*x]/b*)


(* ::Item:: *)
(*Note: Rule not necessary until a closed-form antiderivative is defined for Cos[Pi/2*x^2]*FresnelS[x].*)


(* Int[x_^m_.*FresnelS[a_+b_.*x_]^2,x_Symbol] :=
  Dist[1/b,Subst[Int[(-a/b+x/b)^m*FresnelS[x]^2,x],x,a+b*x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0 *)


(* ::Subsubsection::Closed:: *)
(*x^m Sin[Pi/2 b^2 x^2] FresnelS[b x]		Products of monomials, sines and Fresnel integral S functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_*Sin[c_.*x_^2]*FresnelS[b_.*x_],x_Symbol] :=
  -Cos[Pi/2*b^2*x^2]*FresnelS[b*x]/(Pi*b^2) +
  Dist[1/(2*b*Pi),Int[Sin[Pi*b^2*x^2],x]] /;
FreeQ[{b,c},x] && c===Pi/2*b^2


(* ::Item::Closed:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Note: Also apply rule when m mod 4 = 2 when a closed-form antiderivative is defined for Cos[Pi/2*x^2]*FresnelS[x].*)


Int[x_^m_*Sin[c_.*x_^2]*FresnelS[b_.*x_],x_Symbol] :=
  -x^(m-1)*Cos[Pi/2*b^2*x^2]*FresnelS[b*x]/(Pi*b^2) +
  Dist[1/(2*b*Pi),Int[x^(m-1)*Sin[Pi*b^2*x^2],x]] +
  Dist[(m-1)/(Pi*b^2),Int[x^(m-2)*Cos[Pi/2*b^2*x^2]*FresnelS[b*x],x]] /;
FreeQ[{b,c},x] && c===Pi/2*b^2 && IntegerQ[m] && m>1 && Not[Mod[m,4]==2]


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_*Sin[c_.*x_^2]*FresnelS[b_.*x_],x_Symbol] :=
  x^(m+1)*Sin[Pi/2*b^2*x^2]*FresnelS[b*x]/(m+1) - b*x^(m+2)/(2*(m+1)*(m+2)) +
  Dist[b/(2*(m+1)),Int[x^(m+1)*Cos[Pi*b^2*x^2],x]] -
  Dist[Pi*b^2/(m+1),Int[x^(m+2)*Cos[Pi/2*b^2*x^2]*FresnelS[b*x],x]] /;
FreeQ[{b,c},x] && c===Pi/2*b^2 && IntegerQ[m] && m<-2 && Mod[m,4]==0


(* ::Subsubsection::Closed:: *)
(*x^m Cos[Pi/2 b^2 x^2] FresnelS[b x]		Products of monomials, cosines and Fresnel integral S functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_*Cos[c_.*x_^2]*FresnelS[b_.*x_],x_Symbol] :=
  Sin[Pi/2*b^2*x^2]*FresnelS[b*x]/(Pi*b^2) - x/(2*b*Pi) +
  Dist[1/(2*b*Pi),Int[Cos[Pi*b^2*x^2],x]] /;
FreeQ[{b,c},x] && c===Pi/2*b^2


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_*Cos[c_.*x_^2]*FresnelS[b_.*x_],x_Symbol] :=
  x^(m-1)*Sin[Pi/2*b^2*x^2]*FresnelS[b*x]/(Pi*b^2) - x^m/(2*b*m*Pi) +
  Dist[1/(2*b*Pi),Int[x^(m-1)*Cos[Pi*b^2*x^2],x]] -
  Dist[(m-1)/(Pi*b^2),Int[x^(m-2)*Sin[Pi/2*b^2*x^2]*FresnelS[b*x],x]] /;
FreeQ[{b,c},x] && c===Pi/2*b^2 && IntegerQ[m] && m>1 && Not[Mod[m,4]==0]


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_*Cos[c_.*x_^2]*FresnelS[b_.*x_],x_Symbol] :=
  x^(m+1)*Cos[Pi/2*b^2*x^2]*FresnelS[b*x]/(m+1) -
  Dist[b/(2*(m+1)),Int[x^(m+1)*Sin[Pi*b^2*x^2],x]] +
  Dist[Pi*b^2/(m+1),Int[x^(m+2)*Sin[Pi/2*b^2*x^2]*FresnelS[b*x],x]] /;
FreeQ[{b,c},x] && c===Pi/2*b^2 && IntegerQ[m] && m<-1 && Mod[m,4]==2


(* ::Subsection::Closed:: *)
(*Fresnel Integral C Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*FresnelC[a+b x]^n				Powers of Fresnel integral C functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[FresnelC[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*FresnelC[a+b*x]/b - Sin[Pi/2*(a+b*x)^2]/(b*Pi) /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[FresnelC[a_.+b_.*x_]^2,x_Symbol] :=
  (a+b*x)*FresnelC[a+b*x]^2/b -
  Dist[2,Int[(a+b*x)*Cos[Pi/2*(a+b*x)^2]*FresnelC[a+b*x],x]] /;
FreeQ[{a,b},x]


(* ::Subsubsection::Closed:: *)
(*x^m FresnelC[a+b x]^n			Products of monomials and powers of Fresnel integral C functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*FresnelC[a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*FresnelC[a+b*x]/(m+1) -
  Dist[b/(m+1),Int[x^(m+1)*Cos[Pi/2*(a+b*x)^2],x]] /;
FreeQ[{a,b,m},x] && NonzeroQ[m+1]


(* ::Item::Closed:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Note: Also apply rule when m mod 4 = 1 when a closed-form antiderivative is defined for Sin[Pi/2*x^2]*FresnelC[x].*)


Int[x_^m_*FresnelC[b_.*x_]^2,x_Symbol] :=
  x^(m+1)*FresnelC[b*x]^2/(m+1) -
  Dist[2*b/(m+1),Int[x^(m+1)*Cos[Pi/2*b^2*x^2]*FresnelC[b*x],x]] /;
FreeQ[b,x] && IntegerQ[m] && m+1!=0 && (m>0 && EvenQ[m] || Mod[m,4]==3)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Int[f[x, a+b*x], x] == Subst[Int[f[-a/b+x/b, x], x], x, a+b*x]/b*)


(* ::Item:: *)
(*Note: Rule not necessary until a closed-form antiderivative is defined for Sin[Pi/2*x^2]*FresnelC[x].*)


(* Int[x_^m_.*FresnelC[a_+b_.*x_]^2,x_Symbol] :=
  Dist[1/b,Subst[Int[(-a/b+x/b)^m*FresnelC[x]^2,x],x,a+b*x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0 *)


(* ::Subsubsection::Closed:: *)
(*x^m Sin[Pi/2 b^2 x^2] FresnelC[b x]		Products of monomials, sines and Fresnel integral C functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_*Sin[c_.*x_^2]*FresnelC[b_.*x_],x_Symbol] :=
  -Cos[Pi/2*b^2*x^2]*FresnelC[b*x]/(Pi*b^2) + x/(2*b*Pi) +
  Dist[1/(2*b*Pi),Int[Cos[Pi*b^2*x^2],x]] /;
FreeQ[{b,c},x] && c===Pi/2*b^2


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_*Sin[c_.*x_^2]*FresnelC[b_.*x_],x_Symbol] :=
  -x^(m-1)*Cos[Pi/2*b^2*x^2]*FresnelC[b*x]/(Pi*b^2) + x^m/(2*b*m*Pi) +
  Dist[1/(2*b*Pi),Int[x^(m-1)*Cos[Pi*b^2*x^2],x]] +
  Dist[(m-1)/(Pi*b^2),Int[x^(m-2)*Cos[Pi/2*b^2*x^2]*FresnelC[b*x],x]] /;
FreeQ[{b,c},x] && c===Pi/2*b^2 && IntegerQ[m] && m>1 && Not[Mod[m,4]==0]


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_*Sin[c_.*x_^2]*FresnelC[b_.*x_],x_Symbol] :=
  x^(m+1)*Sin[Pi/2*b^2*x^2]*FresnelC[b*x]/(m+1) -
  Dist[b/(2*(m+1)),Int[x^(m+1)*Sin[Pi*b^2*x^2],x]] -
  Dist[Pi*b^2/(m+1),Int[x^(m+2)*Cos[Pi/2*b^2*x^2]*FresnelC[b*x],x]] /;
FreeQ[{b,c},x] && c===Pi/2*b^2 && IntegerQ[m] && m<-1 && Mod[m,4]==2


(* ::Subsubsection::Closed:: *)
(*x^m Cos[Pi/2 b^2 x^2] FresnelC[b x]		Products of monomials, cosines and Fresnel integral C functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_*Cos[c_.*x_^2]*FresnelC[b_.*x_],x_Symbol] :=
  Sin[Pi/2*b^2*x^2]*FresnelC[b*x]/(Pi*b^2) -
  Dist[1/(2*b*Pi),Int[Sin[Pi*b^2*x^2],x]] /;
FreeQ[{b,c},x] && c===Pi/2*b^2


(* ::Item::Closed:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Note: Also apply rule when m mod 4 = 2 when a closed-form antiderivative is defined for Sin[Pi/2*x^2]*FresnelC[x].*)


Int[x_^m_*Cos[c_.*x_^2]*FresnelC[b_.*x_],x_Symbol] :=
  x^(m-1)*Sin[Pi/2*b^2*x^2]*FresnelC[b*x]/(Pi*b^2) -
  Dist[1/(2*b*Pi),Int[x^(m-1)*Sin[Pi*b^2*x^2],x]] -
  Dist[(m-1)/(Pi*b^2),Int[x^(m-2)*Sin[Pi/2*b^2*x^2]*FresnelC[b*x],x]] /;
FreeQ[{b,c},x] && c===Pi/2*b^2 && IntegerQ[m] && m>1 && Not[Mod[m,4]==2]


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_*Cos[c_.*x_^2]*FresnelC[b_.*x_],x_Symbol] :=
  x^(m+1)*Cos[Pi/2*b^2*x^2]*FresnelC[b*x]/(m+1) - b*x^(m+2)/(2*(m+1)*(m+2)) -
  Dist[b/(2*(m+1)),Int[x^(m+1)*Cos[Pi*b^2*x^2],x]] +
  Dist[Pi*b^2/(m+1),Int[x^(m+2)*Sin[Pi/2*b^2*x^2]*FresnelC[b*x],x]] /;
FreeQ[{b,c},x] && c===Pi/2*b^2 && IntegerQ[m] && m<-2 && Mod[m,4]==0
