(* ::Package:: *)

(* ::Title:: *)
(*Exponential Function Integration Rules*)


(* ::Subsection::Closed:: *)
(*f^(a+b x^n)				Products of monomials and exponentials of binomials*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.311, CRC 519, A&S 4.2.54*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: D[E^x,x] == E^x*)


Int[E^(a_.+b_.*x_),x_Symbol] :=
  E^(a+b*x)/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Reference: G&R 2.311, CRC 519, A&S 4.2.54*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: D[E^x,x] == E^x*)


Int[f_^(a_.+b_.*x_),x_Symbol] :=
  f^(a+b*x)/(b*Log[f]) /;
FreeQ[{a,b,f},x]


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: Erfi'[z] == 2*E^(z^2)/Sqrt[Pi]*)


Int[E^(a_.+b_.*x_^2),x_Symbol] :=
  E^a*Sqrt[Pi]*Erfi[x*Rt[b,2]]/(2*Rt[b,2]) /;
FreeQ[{a,b},x] && PosQ[b]


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: Erfi'[z] == 2*E^(z^2)/Sqrt[Pi]*)


Int[f_^(a_.+b_.*x_^2),x_Symbol] :=
  f^a*Sqrt[Pi]*Erfi[x*Rt[b*Log[f],2]]/(2*Rt[b*Log[f],2]) /;
FreeQ[{a,b,f},x] && PosQ[b*Log[f]]


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: Erf'[z] == 2*E^(-z^2)/Sqrt[Pi]*)


Int[E^(a_.+b_.*x_^2),x_Symbol] :=
  E^a*Sqrt[Pi]*Erf[x*Rt[-b,2]]/(2*Rt[-b,2]) /;
FreeQ[{a,b},x] && NegQ[b]


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: Erf'[z] == 2*E^(-z^2)/Sqrt[Pi]*)


Int[f_^(a_.+b_.*x_^2),x_Symbol] :=
  f^a*Sqrt[Pi]*Erf[x*Rt[-b*Log[f],2]]/(2*Rt[-b*Log[f],2]) /;
FreeQ[{a,b,f},x] && NegQ[b*Log[f]]


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: D[Gamma[n,x],x] == -E^(-x)*x^(n-1)*)


Int[E^(a_.+b_.*x_^n_),x_Symbol] :=
  -E^a*x*Gamma[1/n,-b*x^n]/(n*(-b*x^n)^(1/n)) /;
FreeQ[{a,b,n},x] && Not[FractionOrNegativeQ[n]]


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: D[Gamma[n,x],x] == -E^(-x)*x^(n-1)*)


Int[f_^(a_.+b_.*x_^n_),x_Symbol] :=
  -f^a*x*Gamma[1/n,-b*x^n*Log[f]]/(n*(-b*x^n*Log[f])^(1/n)) /;
FreeQ[{a,b,f,n},x] && Not[FractionOrNegativeQ[n]]


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* Note: Although resulting integrand looks more complicated than original one,rules for 
	improper binomials rectify it. *)
Int[E^(a_.+b_.*x_^n_.),x_Symbol] :=
  x*E^(a+b*x^n) - 
  Dist[b*n,Int[x^n*E^(a+b*x^n),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && n<0


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* Note: Although resulting integrand looks more complicated than original one,rules for 
	improper binomials rectify it. *)
Int[f_^(a_.+b_.*x_^n_.),x_Symbol] :=
  x*f^(a+b*x^n) - 
  Dist[b*n*Log[f],Int[x^n*f^(a+b*x^n),x]] /;
FreeQ[{a,b,f},x] && IntegerQ[n] && n<0


(* ::Subsection::Closed:: *)
(*x^m f^(a+b x^n)			Products of monomials and exponentials of binomials*)
(**)


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: ExpIntegralEi'[z] == E^z/z*)


Int[E^(a_.+b_.*x_^n_.)/x_,x_Symbol] :=
  E^a*ExpIntegralEi[b*x^n]/n /;
FreeQ[{a,b,n},x]


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: ExpIntegralEi'[z] == E^z/z*)


Int[f_^(a_.+b_.*x_^n_.)/x_,x_Symbol] :=
  f^a*ExpIntegralEi[b*x^n*Log[f]]/n /;
FreeQ[{a,b,f,n},x]


(* ::Item::Closed:: *)
(*Reference: G&R 2.321.1, CRC 521, A&S 4.2.55*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Basis: x^m*f^(a+b*x^n) == x^(m-n+1)*(f^(a+b*x^n)*x^(n-1))*)


Int[x_^m_.*f_^(a_.+b_.*x_^n_.),x_Symbol] :=
  x^(m-n+1)*f^(a+b*x^n)/(b*n*Log[f]) -
  Dist[(m-n+1)/(b*n*Log[f]),Int[x^(m-n)*f^(a+b*x^n),x]] /;
FreeQ[{a,b,f},x] && IntegerQ[n] && RationalQ[m] && 0<n<=m


(* ::Item::Closed:: *)
(*Reference: G&R 2.324.1, CRC 523, A&S 4.2.56*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*f_^(a_.+b_.*x_^n_.),x_Symbol] :=
  x^(m+1)*f^(a+b*x^n)/(m+1) -
  Dist[b*n*Log[f]/(m+1),Int[x^(m+n)*f^(a+b*x^n),x]] /;
FreeQ[{a,b,f},x] && IntegerQ[n] && RationalQ[m] && (n>0 && m<-1 || 0<-n<=m+1)


Int[x_^m_.*f_^(a_.+b_.*x_^n_.),x_Symbol] :=
  -f^a*x^(m+1)*Gamma[(m+1)/n,-b*x^n*Log[f]]/(n*(-b*x^n*Log[f])^((m+1)/n)) /;
FreeQ[{a,b,f,m,n},x] &&
  NonzeroQ[m+1] &&
  NonzeroQ[m-n+1] &&
  Not[m===-1/2 && ZeroQ[n-1]] &&
  Not[IntegerQ[{m,n}] && n>0 && (m<-1 || m>=n)] &&
  Not[RationalQ[{m,n}] && (FractionQ[m] || FractionOrNegativeQ[n])]


(* ::Subsection::Closed:: *)
(*f^(a+b x+c x^2)			Exponentials of quadratic trinomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If b^2-4*a*c=0, a+b*x+c*x^2 == (b+2*c*x)^2/(4*c)*)


Int[f_^(a_.+b_.*x_+c_.*x_^2),x_Symbol] :=
  Int[f^((b+2*c*x)^2/(4*c)),x] /;
FreeQ[{a,b,c,f},x] && ZeroQ[b^2-4*a*c]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: a+b*x+c*x^2 == (b+2*c*x)^2/(4*c) - (b^2-4*a*c)/(4*c)*)


(* ::Item:: *)
(*Basis: f^(z-w) == f^z/f^w*)


Int[f_^(a_.+b_.*x_+c_.*x_^2),x_Symbol] :=
  f^(a-b^2/(4*c))*Int[f^((b+2*c*x)^2/(4*c)),x] /;
FreeQ[{a,b,c,f},x]


(* ::Subsection::Closed:: *)
(*(d+e x)^m f^(a+b x+c x^2)		Products of linears and exponentials of quadratic trinomials*)
(**)


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


(* Note: This rule unnecessary because derivative of quadratic divides linear factor. *)
(* Int[(d_.+e_.*x_)*f_^(a_.+b_.*x_+c_.*x_^2),x_Symbol] :=
  e*f^(a+b*x+c*x^2)/(2*c*Log[f]) /;
FreeQ[{a,b,c,d,e,f},x] && ZeroQ[b*e-2*c*d] *)


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[(d_.+e_.*x_)*f_^(a_.+b_.*x_+c_.*x_^2),x_Symbol] :=
  e*f^(a+b*x+c*x^2)/(2*c*Log[f]) -
  Dist[(b*e-2*c*d)/(2*c),Int[f^(a+b*x+c*x^2),x]] /;
FreeQ[{a,b,c,d,e,f},x] && NonzeroQ[b*e-2*c*d]


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[(d_.+e_.*x_)^m_*f_^(a_.+b_.*x_+c_.*x_^2),x_Symbol] :=
  e*(d+e*x)^(m-1)*f^(a+b*x+c*x^2)/(2*c*Log[f]) -
  Dist[(m-1)*e^2/(2*c*Log[f]),Int[(d+e*x)^(m-2)*f^(a+b*x+c*x^2),x]] /;
FreeQ[{a,b,c,d,e,f},x] && RationalQ[m] && m>1 && ZeroQ[b*e-2*c*d]


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[(d_.+e_.*x_)^m_*f_^(a_.+b_.*x_+c_.*x_^2),x_Symbol] :=
  e*(d+e*x)^(m-1)*f^(a+b*x+c*x^2)/(2*c*Log[f]) -
  Dist[(b*e-2*c*d)/(2*c),Int[(d+e*x)^(m-1)*f^(a+b*x+c*x^2),x]] -
  Dist[(m-1)*e^2/(2*c*Log[f]),Int[(d+e*x)^(m-2)*f^(a+b*x+c*x^2),x]] /;
FreeQ[{a,b,c,d,e,f},x] && RationalQ[m] && m>1 && NonzeroQ[b*e-2*c*d]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[(d_.+e_.*x_)^m_*f_^(a_.+b_.*x_+c_.*x_^2),x_Symbol] :=
  (d+e*x)^(m+1)*f^(a+b*x+c*x^2)/(e*(m+1)) -
  Dist[2*c*Log[f]/(e^2*(m+1)),Int[(d+e*x)^(m+2)*f^(a+b*x+c*x^2),x]] /;
FreeQ[{a,b,c,d,e,f},x] && RationalQ[m] && m<-1 && ZeroQ[b*e-2*c*d]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[(d_.+e_.*x_)^m_*f_^(a_.+b_.*x_+c_.*x_^2),x_Symbol] :=
  (d+e*x)^(m+1)*f^(a+b*x+c*x^2)/(e*(m+1)) -
  Dist[(b*e-2*c*d)*Log[f]/(e^2*(m+1)),Int[(d+e*x)^(m+1)*f^(a+b*x+c*x^2),x]] -
  Dist[2*c*Log[f]/(e^2*(m+1)),Int[(d+e*x)^(m+2)*f^(a+b*x+c*x^2),x]] /;
FreeQ[{a,b,c,d,e,f},x] && RationalQ[m] && m<-1 && NonzeroQ[b*e-2*c*d]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[(a_.+b_.*x_)^m_*f_^((c_.+d_.*x_)^n_.),x_Symbol] :=
  (a+b*x)^(m+1)*f^((c+d*x)^n)/(b*(m+1)) -
  Dist[d*n*Log[f]/(b*(m+1)),Int[(a+b*x)^(m+1)*f^((c+d*x)^n)*(c+d*x)^(n-1),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[m] && m<-1 && IntegerQ[n] && n>1


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* Int[(a_.+b_.*x_)^m_*f_^u_,x_Symbol] :=
  (a+b*x)^(m+1)*f^u/(b*(m+1)) -
  Dist[Log[f]/(b*(m+1)),Int[(a+b*x)^(m+1)*f^u*D[u,x],x]] /;
FreeQ[{a,b},x] && PolynomialQ[u,x] && Exponent[u,x]>1 && RationalQ[m] && m<-1 *)


(* ::Subsection::Closed:: *)
(*(a+b f^(c+d x))^n			Powers of linear exponentials of linear binomials*)


(* ::Item:: *)
(*Reference: CRC 256*)


Int[1/(a_+b_.*f_^(c_.+d_.*x_)),x_Symbol] :=
  -Log[b+a*f^(-c-d*x)]/(a*d*Log[f]) /;
FreeQ[{a,b,c,d,f},x] && NegativeCoefficientQ[d]


(* ::Item:: *)
(*Reference: CRC 256*)


Int[1/(a_+b_.*f_^(c_.+d_.*x_)),x_Symbol] :=
  x/a-Log[a+b*f^(c+d*x)]/(a*d*Log[f]) /;
FreeQ[{a,b,c,d,f},x]


Int[1/Sqrt[a_+b_.*f_^(c_.+d_.*x_)],x_Symbol] :=
  -2*ArcTanh[Sqrt[a+b*f^(c+d*x)]/Sqrt[a]]/(Sqrt[a]*d*Log[f]) /;
FreeQ[{a,b,c,d,f},x] && PosQ[a]


Int[1/Sqrt[a_+b_.*f_^(c_.+d_.*x_)],x_Symbol] :=
  2*ArcTan[Sqrt[a+b*f^(c+d*x)]/Sqrt[-a]]/(Sqrt[-a]*d*Log[f]) /;
FreeQ[{a,b,c,d,f},x] && NegQ[a]


Int[(a_+b_.*f_^(c_.+d_.*x_))^n_,x_Symbol] :=
  (a+b*f^(c+d*x))^n/(n*d*Log[f]) + 
  Dist[a,Int[(a+b*f^(c+d*x))^(n-1),x]] /;
FreeQ[{a,b,c,d,f},x] && FractionQ[n] && n>0


Int[(a_+b_.*f_^(c_.+d_.*x_))^n_,x_Symbol] :=
  -(a+b*f^(c+d*x))^(n+1)/((n+1)*a*d*Log[f]) + 
  Dist[1/a,Int[(a+b*f^(c+d*x))^(n+1),x]] /;
FreeQ[{a,b,c,d,f},x] && RationalQ[n] && n<-1


(* ::Subsection::Closed:: *)
(*x^m (a+b f^(c+d x))^n		Products of monomials and powers of linear exponentials of linear binomials*)
(**)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: 1/(a+b*z) == 1/a - b/a*z/(a+b*z)*)


Int[x_^m_./(a_+b_.*f_^(c_.+d_.*x_)), x_Symbol] :=
  x^(m+1)/(a*(m+1)) -
  Dist[b/a,Int[x^m*f^(c+d*x)/(a+b*f^(c+d*x)),x]] /;
FreeQ[{a,b,c,d,f},x] && RationalQ[m] && m>0


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*(a_+b_.*f_^(c_.+d_.*x_))^n_, x_Symbol] :=
  Module[{u=Block[{ShowSteps=False,StepCounter=Null}, Int[(a+b*f^(c+d*x))^n,x]]},
  x^m*u - Dist[m,Int[x^(m-1)*u,x]]] /;
FreeQ[{a,b,c,d,f},x] && RationalQ[{m,n}] && m>0 && n<-1


(* ::Subsection::Closed:: *)
(*x^m f^(c (a+b x)^n)			Products of linears and exponentials of powers of linears*)


(* Yikes!!! Need to do something likes this for trig and hyperbolic too.Ug! *)


Int[x_^m_*f_^(c_.*(a_+b_.*x_)^2),x_Symbol] :=
  Int[x^m*f^(a^2*c+2*a*b*c*x+b^2*c*x^2),x] /;
FreeQ[{a,b,c,f},x] && FractionQ[m] && m>1


Int[x_^m_.*f_^(c_.*(a_+b_.*x_)^n_),x_Symbol] :=
  Dist[1/b^m,Int[Expand[b^m*x^m-(a+b*x)^m,x]*f^(c*(a+b*x)^n),x]] +
  Dist[1/b^(m+1),Subst[Int[x^m*f^(c*x^n),x],x,a+b*x]] /;
FreeQ[{a,b,c,f,n},x] && IntegerQ[m] && m>0


(* ::Subsection::Closed:: *)
(*x^m f^(c+d x)/(a+b f^(c+d x))	Products of monomials and linear exponentials of linear binomials*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*f_^(c_.+d_.*x_)/(a_+b_.*f_^(c_.+d_.*x_)), x_Symbol] :=
  x^m*Log[1+b*f^(c+d*x)/a]/(b*d*Log[f]) -
  Dist[m/(b*d*Log[f]),Int[x^(m-1)*Log[1+b/a*f^(c+d*x)],x]] /;
FreeQ[{a,b,c,d,f},x] && RationalQ[m] && m>0


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*f_^(c_.+d_.*x_)/(a_.+b_.*f_^v_),x_Symbol] :=
  Module[{u=Block[{ShowSteps=False,StepCounter=Null}, Int[f^(c+d*x)/(a+b*f^v),x]]},
  x^m*u - Dist[m,Int[x^(m-1)*u,x]]] /;
FreeQ[{a,b,c,d,f},x] && ZeroQ[2*(c+d*x)-v] && RationalQ[m] && m>0


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_./(a_.*f_^(c_.+d_.*x_)+b_.*f_^v_),x_Symbol] :=
  Module[{u=Block[{ShowSteps=False,StepCounter=Null}, Int[1/(a*f^(c+d*x)+b*f^v),x]]},
  x^m*u - Dist[m,Int[x^(m-1)*u,x]]] /;
FreeQ[{a,b,c,d,f},x] && ZeroQ[(c+d*x)+v] && RationalQ[m] && m>0


(* ::Item::Closed:: *)
(*Note: The remaining inverse function integration rules in this section are required to integrate the expressions generated by the above rules.*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Log[1+c_.*f_^(a_.+b_.*x_)],x_Symbol] :=
  -PolyLog[2,-c*f^(a+b*x)]/(b*Log[f]) /;
FreeQ[{a,b,c,f},x]


(* ::Item:: *)
(*Basis: D[Log[c+d*g[x]],x] == D[Log[1+d/c*g[x]],x]*)


Int[Log[c_+d_.*f_^(a_.+b_.*x_)],x_Symbol] :=
  x*Log[c+d*f^(a+b*x)] - x*Log[1+d/c*f^(a+b*x)] +
  Int[Log[1+d/c*f^(a+b*x)],x] /;
FreeQ[{a,b,c,d,f},x] && NonzeroQ[c-1]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Log[1+c_.*f_^(a_.+b_.*x_)],x_Symbol] :=
  -x^m*PolyLog[2,-c*f^(a+b*x)]/(b*Log[f]) +
  Dist[m/(b*Log[f]),Int[x^(m-1)*PolyLog[2,-c*f^(a+b*x)],x]] /;
FreeQ[{a,b,c,f},x] && RationalQ[m] && m>0


(* ::Item:: *)
(*Basis: D[Log[c+d*g[x]],x] == D[Log[1+d/c*g[x]],x]*)


Int[x_^m_.*Log[c_+d_.*f_^(a_.+b_.*x_)],x_Symbol] :=
  x^(m+1)*Log[c+d*f^(a+b*x)]/(m+1) - x^(m+1)*Log[1+d/c*f^(a+b*x)]/(m+1) +
  Int[x^m*Log[1+d/c*f^(a+b*x)],x] /;
FreeQ[{a,b,c,d,f},x] && NonzeroQ[c-1] && RationalQ[m] && m>0


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: D[PolyLog[n,z],z] == PolyLog[n-1,z]/z*)


Int[PolyLog[n_,c_.*f_^(a_.+b_.*x_)],x_Symbol] :=
  PolyLog[n+1,c*f^(a+b*x)]/(b*Log[f]) /;
FreeQ[{a,b,c,n},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*PolyLog[n_,c_.*f_^(a_.+b_.*x_)],x_Symbol] :=
  x^m*PolyLog[n+1,c*f^(a+b*x)]/(b*Log[f]) -
  Dist[m/(b*Log[f]),Int[x^(m-1)*PolyLog[n+1,c*f^(a+b*x)],x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[m] && m>0


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcTanh[z] == 1/2*Log[1+z] - 1/2*Log[1-z]*)


Int[ArcTanh[b_.*f_^(c_.+d_.*x_)],x_Symbol] :=
  Dist[1/2,Int[Log[1+b*f^(c+d*x)],x]] -
  Dist[1/2,Int[Log[1-b*f^(c+d*x)],x]] /;
FreeQ[{b,c,d,f},x] 


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcTanh[z] == 1/2*Log[1+z] - 1/2*Log[1-z]*)


Int[x_^m_.*ArcTanh[b_.*f_^(c_.+d_.*x_)],x_Symbol] :=
  Dist[1/2,Int[x^m*Log[1+b*f^(c+d*x)],x]] -
  Dist[1/2,Int[x^m*Log[1-b*f^(c+d*x)],x]] /;
FreeQ[{b,c,d,f},x] && IntegerQ[m] && m>0


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcCoth[z] == 1/2*Log[1+1/z] - 1/2*Log[1-1/z]*)


Int[ArcCoth[b_.*f_^(c_.+d_.*x_)],x_Symbol] :=
  Dist[1/2,Int[Log[1+1/(b*f^(c+d*x))],x]] -
  Dist[1/2,Int[Log[1-1/(b*f^(c+d*x))],x]] /;
FreeQ[{b,c,d,f},x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcCoth[z] == 1/2*Log[1+1/z] - 1/2*Log[1-1/z]*)


Int[x_^m_.*ArcCoth[b_.*f_^(c_.+d_.*x_)],x_Symbol] :=
  Dist[1/2,Int[x^m*Log[1+1/(b*f^(c+d*x))],x]] -
  Dist[1/2,Int[x^m*Log[1-1/(b*f^(c+d*x))],x]] /;
FreeQ[{b,c,d,f},x] && IntegerQ[m] && m>0


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcTan[z] == I/2*Log[1-I*z] - I/2*Log[1+I*z]*)


Int[ArcTan[b_.*f_^(c_.+d_.*x_)],x_Symbol] :=
  Dist[I/2,Int[Log[1-b*I*f^(c+d*x)],x]] -
  Dist[I/2,Int[Log[1+b*I*f^(c+d*x)],x]] /;
FreeQ[{b,c,d,f},x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcTan[z] == I/2*Log[1-I*z] - I/2*Log[1+I*z]*)


Int[x_^m_.*ArcTan[a_.+b_.*f_^(c_.+d_.*x_)],x_Symbol] :=
  Dist[I/2,Int[x^m*Log[1-a*I-b*I*f^(c+d*x)],x]] -
  Dist[I/2,Int[x^m*Log[1+a*I+b*I*f^(c+d*x)],x]] /;
FreeQ[{a,b,c,d,f},x] && IntegerQ[m] && m>0


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcCot[z] == I/2*Log[1-I/z] - I/2*Log[1+I/z]*)


Int[ArcCot[b_.*f_^(c_.+d_.*x_)],x_Symbol] :=
  Dist[I/2,Int[Log[1-I/(b*f^(c+d*x))],x]] -
  Dist[I/2,Int[Log[1+I/(b*f^(c+d*x))],x]] /;
FreeQ[{b,c,d,f},x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcCot[z] == I/2*Log[1-I/z] - I/2*Log[1+I/z]*)


Int[x_^m_.*ArcCot[a_.+b_.*f_^(c_.+d_.*x_)],x_Symbol] :=
  Dist[I/2,Int[x^m*Log[1-I/(a+b*f^(c+d*x))],x]] -
  Dist[I/2,Int[x^m*Log[1+I/(a+b*f^(c+d*x))],x]] /;
FreeQ[{a,b,c,d,f},x] && IntegerQ[m] && m>0


(* ::Subsection::Closed:: *)
(*(a+b x)^m f^(e (c+d x)^n)		Products of linears and exponentials of powers of linears*)


If[ShowSteps,

Int[(a_.+b_.*x_)^m_.*(f_^(e_.*(c_.+d_.*x_)^n_.))^p_.,x_Symbol] :=
  ShowStep["","Int[(a+b*x)^m*f[x],x]","Subst[Int[x^m*f[-a/b+x/b],x],x,a+b*x]/b",Hold[
  Dist[1/b,Subst[Int[x^m*(f^(e*(c-a*d/b+d*x/b)^n))^p,x],x,a+b*x]]]] /;
SimplifyFlag && FreeQ[{a,b,c,d,e,f,m,n},x] && RationalQ[p] && Not[a===0 && b===1],

Int[(a_.+b_.*x_)^m_.*(f_^(e_.*(c_.+d_.*x_)^n_.))^p_.,x_Symbol] :=
  Dist[1/b,Subst[Int[x^m*(f^(e*(c-a*d/b+d*x/b)^n))^p,x],x,a+b*x]] /;
FreeQ[{a,b,c,d,e,f,m,n},x] && RationalQ[p] && Not[a===0 && b===1]]


(* ::Subsection::Closed:: *)
(*f^((a+b x^4)/x^2)			Exponentials of quotients of binomials and monomials*)


Int[f_^((a_.+b_.*x_^4)/x_^2),x_Symbol] :=
  Sqrt[Pi]*Exp[2*Sqrt[-a*Log[f]]*Sqrt[-b*Log[f]]]*Erf[(Sqrt[-a*Log[f]]+Sqrt[-b*Log[f]]*x^2)/x]/
    (4*Sqrt[-b*Log[f]]) -
  Sqrt[Pi]*Exp[-2*Sqrt[-a*Log[f]]*Sqrt[-b*Log[f]]]*Erf[(Sqrt[-a*Log[f]]-Sqrt[-b*Log[f]]*x^2)/x]/
    (4*Sqrt[-b*Log[f]]) /;
FreeQ[{a,b,f},x]


(* ::Subsection::Closed:: *)
(*u / (a+b f^(d+e x)+c f^(g+h x))	Quotients by quadratic trinomial exponentials of linears*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: 1/(a+b*f^z+c*f^(2*z)) == 1/a - f^z*(b+c*f^z)/(a*(a+b*f^z+c*f^(2*z)))*)


Int[1/(a_+b_.*f_^u_+c_.*f_^v_), x_Symbol] :=
  x/a -
  Dist[1/a,Int[f^u*(b+c*f^u)/(a+b*f^u+c*f^v),x]] /;
FreeQ[{a,b,c,f},x] && LinearQ[u,x] && LinearQ[v,x] && ZeroQ[2*u-v] &&
Not[RationalQ[Rt[b^2-4*a*c,2]]]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: (d+e*f^z)/(a+b*f^z+c*f^(2*z)) == d/a - f^z*(b*d-a*e+c*d*f^z)/(a*(a+b*f^z+c*f^(2*z)))*)


Int[(d_+e_.*f_^u_)/(a_+b_.*f_^u_+c_.*f_^v_), x_Symbol] :=
  d*x/a -
  Dist[1/a,Int[f^u*(b*d-a*e+c*d*f^u)/(a+b*f^u+c*f^v),x]] /;
FreeQ[{a,b,c,d,e,f},x] && LinearQ[u,x] && LinearQ[v,x] && ZeroQ[2*u-v] &&
Not[RationalQ[Rt[b^2-4*a*c,2]]]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1/(a+b*z+c/z) == z/(c+a*z+b*z^2)*)


Int[u_/(a_+b_.*f_^v_+c_.*f_^w_), x_Symbol] :=
  Int[u*f^v/(c+a*f^v+b*f^(2*v)),x] /;
FreeQ[{a,b,c,f},x] && LinearQ[v,x] && LinearQ[w,x] && ZeroQ[v+w] &&
If[RationalQ[Coefficient[v,x,1]], Coefficient[v,x,1]>0, LeafCount[v]<LeafCount[w]]


(* ::Subsection::Closed:: *)
(*x^m (E^x+x^m)^n*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Int[f[x]*(E^x+f[x])^n, x] == -(E^x+f[x])^(n+1)/(n+1) + Int[(E^x+f[x])^(n+1), x] + Int[f'[x]*(E^x+f[x])^n, x]*)


Int[x_^m_.*(E^x_+x_^m_.)^n_,x_Symbol] :=
  -(E^x+x^m)^(n+1)/(n+1) +
  Int[(E^x+x^m)^(n+1),x] +
  Dist[m,Int[x^(m-1)*(E^x+x^m)^n,x]] /;
RationalQ[{m,n}] && m>0 && NonzeroQ[n+1]
