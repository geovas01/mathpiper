(* ::Package:: *)

(* ::Title:: *)
(*Logarithm Function Integration Rules*)


(* ::Subsection::Closed:: *)
(*Log[c (a+b x)^n]			Logarithms of powers of linears*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.711.1, CRC 499, A&S 4.1.49*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Log[c_.*(a_.+b_.*x_)^n_.],x_Symbol] :=
  (a+b*x)*Log[c*(a+b*x)^n]/b - n*x /;
FreeQ[{a,b,c,n},x]


(* ::Subsection::Closed:: *)
(*(d+e x)^m Log[c (a+b x)^n]		Products of powers of linears and logarithms of powers of linears*)
(**)


(* ::Item::Closed:: *)
(*Reference: G&R 2.728.2*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: D[-PolyLog[2, d+e*x]/e, x] == Log[1-d-e*x]/(d+e*x)*)


Int[Log[c_.*(a_.+b_.*x_)]/(d_+e_.*x_),x_Symbol] :=
  -PolyLog[2,1-a*c-b*c*x]/e /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[a*c*e-b*c*d-e]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Log[c_.*(a_.+b_.*x_)^n_.]/(d_+e_.*x_),x_Symbol] :=
  Log[c*(a+b*x)^n]*Log[b*(d+e*x)/(b*d-a*e)]/e + 
  n*PolyLog[2,-e*(a+b*x)/(b*d-a*e)]/e /;
FreeQ[{a,b,c,d,e,n},x] && NonzeroQ[b*d-a*e]


(* ::Subsection::Closed:: *)
(*Log[c (a+b x^n)^p]			Logarithms of powers of binomials*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.728.1*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Log[c_.*(b_.*x_^n_)^p_.],x_Symbol] :=
  x*Log[c*(b*x^n)^p] - n*p*x /;
FreeQ[{b,c,n,p},x]


(* ::Item::Closed:: *)
(*Reference: G&R 2.728.1*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Log[c_.*(a_+b_.*x_^n_)^p_.],x_Symbol] :=
  x*Log[c*(a+b*x^n)^p] - 
  Dist[b*n*p,Int[1/(b+a*x^(-n)),x]] /;
FreeQ[{a,b,c,p},x] && RationalQ[n] && n<0


(* ::Item::Closed:: *)
(*Reference: G&R 2.728.1*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Log[c_.*(a_+b_.*x_^n_)^p_.],x_Symbol] :=
  x*Log[c*(a+b*x^n)^p] - n*p*x + 
  Dist[a*n*p,Int[1/(a+b*x^n),x]] /;
FreeQ[{a,b,c,n,p},x]


(* ::Subsection::Closed:: *)
(*x^m Log[c (a+b x^n)^p]		Products of monomials and logarithms of powers of binomials*)
(**)


(* ::Item::Closed:: *)
(*Reference: G&R 2.728.2*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: D[PolyLog[2,-x],x] == -Log[1+x]/x*)


Int[Log[1+b_.*x_^n_.]/x_,x_Symbol] :=
  -PolyLog[2,-b*x^n]/n /;
FreeQ[{b,n},x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If a>0, Log[a*z] == Log[a] + Log[z]*)


Int[Log[c_.*(a_+b_.*x_^n_.)]/x_,x_Symbol] :=
  Log[a*c]*Log[x] +
  Int[Log[1+b*x^n/a]/x,x] /;
FreeQ[{a,b,c,n},x] && PositiveQ[a*c]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Log[c_.*(a_+b_.*x_^n_.)^p_.]/x_,x_Symbol] :=
  Log[c*(a+b*x^n)^p]*Log[-b*x^n/a]/n -
  Dist[b*p,Int[x^(n-1)*Log[-b*x^n/a]/(a+b*x^n),x]] /;
(* p*PolyLog[2,1+b*x^n/a]/n /; *)
FreeQ[{a,b,c,n,p},x]


(* ::Item::Closed:: *)
(*Reference: G&R 2.728.1, CRC 501, A&S 4.1.50'*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Log[c_.*(b_.*x_^n_.)^p_.],x_Symbol] :=
  x^(m+1)*Log[c*(b*x^n)^p]/(m+1) - n*p*x^(m+1)/(m+1)^2 /;
FreeQ[{b,c,m,n,p},x] && NonzeroQ[m+1]


(* ::Item::Closed:: *)
(*Reference: G&R 2.728.1, CRC 501, A&S 4.1.50'*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Log[c_.*(a_+b_.*x_^n_.)^p_.],x_Symbol] :=
  x^(m+1)*Log[c*(a+b*x^n)^p]/(m+1) - 
  Dist[b*n*p/(m+1),Int[x^(m+n)/(a+b*x^n),x]] /;
FreeQ[{a,b,c,m,n,p},x] && NonzeroQ[m+1] && NonzeroQ[m-n+1]


(* ::Subsection::Closed:: *)
(*(a+b Log[c (d+e x)^n])^p		Powers of linear binomials of logarithms*)


(* ::Item::Closed:: *)
(*Reference: CRC 492*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: D[LogIntegral[x],x] == 1/Log[x]*)


Int[1/Log[c_.*(d_.+e_.*x_)],x_Symbol] :=
  LogIntegral[c*(d+e*x)]/(c*e) /;
FreeQ[{c,d,e},x]


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: D[ExpIntegralEi[x],x] == E^x/x*)


Int[1/(a_.+b_.*Log[c_.*(d_.+e_.*x_)^n_.]),x_Symbol] :=
  (d+e*x)*ExpIntegralEi[(a+b*Log[c*(d+e*x)^n])/(b*n)]/(b*e*n*E^(a/(b*n))*(c*(d+e*x)^n)^(1/n)) /;
FreeQ[{a,b,c,d,e,n},x]


Int[1/Sqrt[a_.+b_.*Log[c_.*(d_.+e_.*x_)^n_.]],x_Symbol] :=
  Sqrt[Pi]*(d+e*x)*Erfi[Rt[1/(b*n),2]*Sqrt[a+b*Log[c*(d+e*x)^n]]]/
    (b*e*n*Rt[1/(b*n),2]*E^(a/(b*n))*(c*(d+e*x)^n)^(1/n)) /;
FreeQ[{a,b,c,d,e,n},x] && PosQ[1/(b*n)]


Int[1/Sqrt[a_.+b_.*Log[c_.*(d_.+e_.*x_)^n_.]],x_Symbol] :=
  Sqrt[Pi]*(d+e*x)*Erf[Rt[-1/(b*n),2]*Sqrt[a+b*Log[c*(d+e*x)^n]]]/
    (b*e*n*Rt[-1/(b*n),2]*E^(a/(b*n))*(c*(d+e*x)^n)^(1/n)) /;
FreeQ[{a,b,c,d,e,n},x] && NegQ[1/(b*n)]


(* ::Item::Closed:: *)
(*Reference: G&R 2.711.1, CRC 490*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[(a_.+b_.*Log[c_.*x_^n_.])^p_,x_Symbol] :=
  x*(a+b*Log[c*x^n])^p -
  Dist[b*n*p,Int[(a+b*Log[c*x^n])^(p-1),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p>0


(* ::Item::Closed:: *)
(*Reference: G&R 2.711.1, CRC 490*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Log[c_.*(d_.+e_.*x_)^n_.]^p_,x_Symbol] :=
  (d+e*x)*Log[c*(d+e*x)^n]^p/e -
  Dist[n*p,Int[Log[c*(d+e*x)^n]^(p-1),x]] /;
FreeQ[{c,d,e,n},x] && RationalQ[p] && p>0


(* ::Item::Closed:: *)
(*Reference: G&R 2.711.1, CRC 490*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[(a_.+b_.*Log[c_.*(d_.+e_.*x_)^n_.])^p_,x_Symbol] :=
  x*(a+b*Log[c*(d+e*x)^n])^p -
  Dist[b*e*n*p,Int[x*(a+b*Log[c*(d+e*x)^n])^(p-1)/(d+e*x),x]] /;
FreeQ[{a,b,c,d,e,n},x] && RationalQ[p] && p>0


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[(a_.+b_.*Log[c_.*x_^n_.])^p_,x_Symbol] :=
  x*(a+b*Log[c*x^n])^(p+1)/(b*n*(p+1)) -
  Dist[1/(b*n*(p+1)),Int[(a+b*Log[c*x^n])^(p+1),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p<-1


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[Log[c_.*(d_.+e_.*x_)^n_.]^p_,x_Symbol] :=
  (d+e*x)*Log[c*(d+e*x)^n]^(p+1)/(e*n*(p+1)) -
  Dist[1/(n*(p+1)),Int[Log[c*(d+e*x)^n]^(p+1),x]] /;
FreeQ[{c,d,e,n},x] && RationalQ[p] && p<-1


Int[(a_.+b_.*Log[c_.*(d_.+e_.*x_)^n_.])^p_,x_Symbol] :=
  (d+e*x)*Gamma[p+1,-(a+b*Log[c*(d+e*x)^n])/(b*n)]*(a+b*Log[c*(d+e*x)^n])^p/
    (e*(-(a+b*Log[c*(d+e*x)^n])/(b*n))^p*E^(a/(b*n))*(c*(d+e*x)^n)^(1/n)) /;
FreeQ[{a,b,c,d,e,n,p},x] && NonzeroQ[p+1]


(* ::Subsection::Closed:: *)
(*x^m (a+b Log[c x^n])^p		Products of monomials and powers of linear binomials of logarithms*)
(**)


Int[x_^m_./(a_.+b_.*Log[c_.*x_^n_.]),x_Symbol] :=
  x^(m+1)*ExpIntegralEi[(m+1)*(a+b*Log[c*x^n])/(b*n)]/(b*n*E^(a*(m+1)/(b*n))*(c*x^n)^((m+1)/n)) /;
FreeQ[{a,b,c,m,n},x] && NonzeroQ[m+1]


Int[x_^m_./Sqrt[a_.+b_.*Log[c_.*x_^n_.]],x_Symbol] :=
  Sqrt[Pi]*x^(m+1)*Erfi[Rt[(m+1)/(b*n),2]*Sqrt[a+b*Log[c*x^n]]]/
    (b*n*Rt[(m+1)/(b*n),2]*E^(a*(m+1)/(b*n))*(c*x^n)^((m+1)/n)) /;
FreeQ[{a,b,c,m,n},x] && NonzeroQ[m+1] && PosQ[(m+1)/(b*n)]


Int[x_^m_./Sqrt[a_.+b_.*Log[c_.*x_^n_.]],x_Symbol] :=
  Sqrt[Pi]*x^(m+1)*Erf[Rt[-(m+1)/(b*n),2]*Sqrt[a+b*Log[c*x^n]]]/
    (b*n*Rt[-(m+1)/(b*n),2]*E^(a*(m+1)/(b*n))*(c*x^n)^((m+1)/n)) /;
FreeQ[{a,b,c,m,n},x] && NonzeroQ[m+1] && NegQ[(m+1)/(b*n)]


(* ::Item::Closed:: *)
(*Reference: G&R 2.721.1, CRC 496, A&S 4.1.51*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*(a_.+b_.*Log[c_.*x_^n_.])^p_,x_Symbol] :=
  x^(m+1)*(a+b*Log[c*x^n])^p/(m+1) -
  Dist[b*n*p/(m+1),Int[x^m*(a+b*Log[c*x^n])^(p-1),x]] /;
FreeQ[{a,b,c,m,n},x] && RationalQ[p] && p>0 && NonzeroQ[m+1]


(* ::Item::Closed:: *)
(*Reference: G&R 2.724.1, CRC 495*)


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_.*(a_.+b_.*Log[c_.*x_^n_.])^p_,x_Symbol] :=
  x^(m+1)*(a+b*Log[c*x^n])^(p+1)/(b*n*(p+1)) -
  Dist[(m+1)/(b*n*(p+1)),Int[x^m*(a+b*Log[c*x^n])^(p+1),x]] /;
FreeQ[{a,b,c,m,n},x] && RationalQ[p] && p<-1 && NonzeroQ[m+1]


Int[x_^m_.*(a_.+b_.*Log[c_.*x_^n_.])^p_,x_Symbol] :=
  x^(m+1)*Gamma[p+1,-(m+1)*(a+b*Log[c*x^n])/(b*n)]*(a+b*Log[c*x^n])^p/
    ((m+1)*E^(a*(m+1)/(b*n))*(c*x^n)^((m+1)/n)*(-(m+1)*(a+b*Log[c*x^n])/(b*n))^p) /;
FreeQ[{a,b,c,m,n,p},x] && NonzeroQ[m+1]


(* Need a rule for arbitrarily deep nesting of powers! *) 
Int[Log[a_.*(b_.*x_^n_.)^p_]^q_.,x_Symbol] :=
  Subst[Int[Log[x^(n*p)]^q,x],x^(n*p),a*(b*x^n)^p] /;
FreeQ[{a,b,n,p,q},x]


Int[Log[a_.*(b_.*(c_.*x_^n_.)^p_)^q_]^r_.,x_Symbol] :=
  Subst[Int[Log[x^(n*p*q)]^r,x],x^(n*p*q),a*(b*(c*x^n)^p)^q] /;
FreeQ[{a,b,c,n,p,q,r},x]


Int[x_^m_.*Log[a_.*(b_.*x_^n_.)^p_]^q_.,x_Symbol] :=
  Subst[Int[x^m*Log[x^(n*p)]^q,x],x^(n*p),a*(b*x^n)^p] /;
FreeQ[{a,b,m,n,p,q},x] && NonzeroQ[m+1] && Not[x^(n*p)===a*(b*x^n)^p]


Int[x_^m_.*Log[a_.*(b_.*(c_.*x_^n_.)^p_)^q_]^r_.,x_Symbol] :=
  Subst[Int[x^m*Log[x^(n*p*q)]^r,x],x^(n*p*q),a*(b*(c*x^n)^p)^q] /;
FreeQ[{a,b,c,m,n,p,q,r},x] && NonzeroQ[m+1] && Not[x^(n*p*q)===a*(b*(c*x^n)^p)^q]


(* ::Subsection::Closed:: *)
(*Log[c (a+b x)^n]^p / (d+e x)		Quotients of powers of logarithms of powers of binomials by x*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Log[c_.*(a_+b_.*x_)^n_.]^p_./(d_.+e_.*x_),x_Symbol] :=
  Log[c*(a+b*x)^n]^p*Log[b*(d+e*x)/(b*d-a*e)]/e -
  Dist[b*n*p/e,Int[Log[c*(a+b*x)^n]^(p-1)*Log[b*(d+e*x)/(b*d-a*e)]/(a+b*x),x]] /;
FreeQ[{a,b,c,d,e,n},x] && RationalQ[p] && p>0 && NonzeroQ[b*d-a*e]


(* ::Item:: *)
(*Note: Log[z] == -PolyLog[1, 1-z]*)


Int[Log[c_.*(a_+b_.*x_)^n_.]^p_.*Log[h_.*(f_.+g_.*x_)]/(d_+e_.*x_),x_Symbol] :=
  Module[{q=Simplify[1-h*(f+g*x)]},
  -Log[c*(a+b*x)^n]^p*PolyLog[2,q]/e +
  Dist[b*n*p/e,Int[Log[c*(a+b*x)^n]^(p-1)*PolyLog[2,q]/(a+b*x),x]]] /;
FreeQ[{a,b,c,d,e,f,g,h,n},x] && RationalQ[p] && p>0 && ZeroQ[a*e-b*d] && ZeroQ[a*g*h-b*(f*h-1)]


Int[Log[c_.*(a_+b_.*x_)^n_.]^p_.*PolyLog[m_,h_.*(f_.+g_.*x_)]/(d_+e_.*x_),x_Symbol] :=
  Log[c*(a+b*x)^n]^p*PolyLog[m+1,h*(f+g*x)]/e -
  Dist[b*n*p/e,Int[Log[c*(a+b*x)^n]^(p-1)*PolyLog[m+1,h*(f+g*x)]/(a+b*x),x]] /;
FreeQ[{a,b,c,d,e,f,g,h,m,n},x] && RationalQ[p] && p>0 && ZeroQ[a*e-b*d] && ZeroQ[a*g-b*f]


(* ::Item:: *)
(*Note: Reduces binomial to a linear, even for fractional and symbolic m, n and p.*)


(* Int[Log[c_.*(a_+b_.*x_^m_)^n_.]^p_./x_,x_Symbol] :=
  Dist[1/m,Subst[Int[Log[c*(a+b*x)^n]^p/x,x],x,x^m]] /;
FreeQ[{a,b,c,m,n,p},x] *)


(* ::Subsection::Closed:: *)
(*x^m Log[c (a+b x)^n]^p		Products of monomials and powers of logarithms of monomials*)
(**)


Int[x_^m_.*Log[c_.*(a_+b_.*x_)^n_.]^p_,x_Symbol] :=
  x^m*(a+b*x)*Log[c*(a+b*x)^n]^p/(b*(m+1)) -
  Dist[a*m/(b*(m+1)),Int[x^(m-1)*Log[c*(a+b*x)^n]^p,x]] -
  Dist[n*p/(m+1),Int[x^m*Log[c*(a+b*x)^n]^(p-1),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[{m,p}] && m>0 && p>0


Int[Log[c_.*(a_+b_.*x_)^n_.]^p_/x_^2,x_Symbol] :=
  -(a+b*x)*Log[c*(a+b*x)^n]^p/(a*x) +
  Dist[b*n*p/a,Int[Log[c*(a+b*x)^n]^(p-1)/x,x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p>0


Int[x_^m_.*Log[c_.*(a_+b_.*x_)^n_.]^p_,x_Symbol] :=
  x^(m+1)*(a+b*x)*Log[c*(a+b*x)^n]^p/(a*(m+1)) -
  Dist[(b*(m+2))/(a*(m+1)),Int[x^(m+1)*Log[c*(a+b*x)^n]^p,x]] -
  Dist[b*n*p/(a*(m+1)),Int[x^(m+1)*Log[c*(a+b*x)^n]^(p-1),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[{m,p}] && m<-1 && m!=-2 && p>0


Int[x_^m_.*Log[c_.*(a_+b_.*x_)^n_.]^p_,x_Symbol] :=
  Dist[1/b,Subst[Int[(-a/b+x/b)^m*Log[c*x^n]^p,x],x,a+b*x]] /;
FreeQ[{a,b,c,n,p},x] && IntegerQ[m] && m>0 && Not[RationalQ[p] && p>0]


(* ::Subsection::Closed:: *)
(*Log[c (a+b x^m)^n]^p		Powers of logarithms of binomials*)
(**)


(* Way kool rule!  Note that the b/x in the resulting integrand will be transformed to b*x
	by the rule Int[f[x^n]/x,x] -> Subst[Int[f[x]/x,x],x,x^n]/n *)
Int[Log[c_.*(a_+b_./x_)^n_.]^p_, x_Symbol] :=
  (b+a*x)*Log[c*(a+b/x)^n]^p/a + 
  Dist[b/a*n*p,Int[Log[c*(a+b/x)^n]^(p-1)/x,x]] /;
FreeQ[{a,b,c,n},x] && IntegerQ[p] && p>0


Int[Log[c_.*(a_+b_.*x_^2)^n_.]^2, x_Symbol] :=
  x*Log[c*(a+b*x^2)^n]^2 + 
    8*n^2*x -
    4*n*x*Log[c*(a+b*x^2)^n] + 
    (n*Sqrt[a]/Sqrt[-b])*( 
      4*n*Log[(-Sqrt[a]+Sqrt[-b]*x)/(Sqrt[a]+Sqrt[-b]*x)] - 
      4*n*ArcTanh[Sqrt[-b]*x/Sqrt[a]]*(Log[-Sqrt[a]/Sqrt[-b]+x] + Log[Sqrt[a]/Sqrt[-b]+x]) -
      n*Log[-Sqrt[a]/Sqrt[-b]+x]^2 + 
      n*Log[Sqrt[a]/Sqrt[-b]+x]^2 - 
      2*n*Log[Sqrt[a]/Sqrt[-b]+x]*Log[1/2-Sqrt[-b]*x/(2*Sqrt[a])] + 
      2*n*Log[-Sqrt[a]/Sqrt[-b]+x]*Log[(1+Sqrt[-b]*x/Sqrt[a])/2] + 
      4*ArcTanh[Sqrt[-b]*x/Sqrt[a]]*Log[c*(a+b*x^2)^n] + 
      2*n*PolyLog[2,1/2-Sqrt[-b]*x/(2*Sqrt[a])] - 
      2*n*PolyLog[2,(1+Sqrt[-b]*x/Sqrt[a])/2]) /;
FreeQ[{a,b,c,n},x]


(* ::Subsection::Closed:: *)
(*Log[d (a+b x+c x^2)^n]^p		Powers of logarithms of powers of quadratics*)
(**)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Log[d_.*(a_.+b_.*x_+c_.*x_^2)^n_.]^2,x_Symbol] :=
  x*Log[d*(a+b*x+c*x^2)^n]^2 -
  Dist[2*b*n,Int[x*Log[d*(a+b*x+c*x^2)^n]/(a+b*x+c*x^2),x]] - 
  Dist[4*c*n,Int[x^2*Log[d*(a+b*x+c*x^2)^n]/(a+b*x+c*x^2),x]] /;
FreeQ[{a,b,c,d,n},x]


(* ::Subsection::Closed:: *)
(*x^m Log[a Log[b x^n]^m]		Logarithms of logarithms*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Log[a_.*Log[b_.*x_^n_.]^p_.],x_Symbol] :=
  x*Log[a*Log[b*x^n]^p] - 
  Dist[n*p,Int[1/Log[b*x^n],x]] /;
FreeQ[{a,b,n,p},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Log[a_.*Log[b_.*x_^n_.]^p_.]/x_,x_Symbol] :=
  Log[b*x^n]*(-p+Log[a*Log[b*x^n]^p])/n /;
FreeQ[{a,b,n,p},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Log[a_.*Log[b_.*x_^n_.]^p_.],x_Symbol] :=
  x^(m+1)*Log[a*Log[b*x^n]^p]/(m+1) - 
  Dist[n*p/(m+1),Int[x^m/Log[b*x^n],x]] /;
FreeQ[{a,b,m,n,p},x] && NonzeroQ[m+1]


(* ::Subsection::Closed:: *)
(*Log[u] / x				Quotients of logarithms by x*)


(* Int[Log[b_.*x_^n_.+c_.*x_^p_.]/x_,x_Symbol] :=
  -Log[x^n]*(Log[x^n]/2-Log[b*x^n+c*x^p]+Log[1+c*x^n/b])/n - 
  PolyLog[2,-c*x^n/b]/n /;
FreeQ[{b,c,n,p},x] && p===2*n *)


(* Int[Log[a_+b_.*x_^n_.+c_.*x_^p_.]/x_,x_Symbol] :=
  Module[{q=Sqrt[b^2-4*a*c]},
  -Log[x]*(Log[1+2*c*x^n/(b-q)]+Log[1+2*c*x^n/(b+q)]-Log[a+b*x^n+c*x^p]) - 
  PolyLog[2,-2*c*x^n/(b-q)]/n - 
  PolyLog[2,-2*c*x^n/(b+q)]/n] /;
FreeQ[{a,b,c,n,p},x] && p===2*n *)


(* Way kool rule!  More generally valid for any integrand of the form f ((a+b*x)/(c+d*x))/x. *)
Int[Log[(a_.+b_.*x_)/(c_+d_.*x_)]^m_./x_,x_Symbol] :=
  Subst[Int[Log[a/c+x/c]^m/x,x],x,(b*c-a*d)*x/(c+d*x)] - 
  Subst[Int[Log[b/d+x/d]^m/x,x],x,-(b*c-a*d)/(c+d*x)] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m>0 && NonzeroQ[b*c-a*d]


(* ::Subsection::Closed:: *)
(*Log[u]^n / (a+b x+...)			Quotients of powers of logarithms by polynomials*)


(* Int[Log[u_]^n_./(a_+b_.*x_),x_Symbol] :=
  Dist[1/b,Subst[Int[Regularize[Log[Subst[u,x,-a/b+x/b]]^n/x,x],x],x,a+b*x]] /;
FreeQ[{a,b,n},x] && NonzeroQ[u-a-b*x] && InverseFunctionFreeQ[u,x] *)


(* ::Subsection::Closed:: *)
(*(A+B Log[d+e x]) / Sqrt[a+b Log[d+e x]]  Quotients of linear binomials of logarithms*)


(* ::Item:: *)
(*Basis: (A+B*z)/Sqrt[a+b*z] == (b*A-a*B)/(b*Sqrt[a+b*z]) + B/b*Sqrt[a+b*z]*)


Int[(A_.+B_.*Log[c_.+d_.*x_])/Sqrt[a_+b_.*Log[c_.+d_.*x_]],x_Symbol] :=
  Dist[(b*A-a*B)/b,Int[1/Sqrt[a+b*Log[c+d*x]],x]] +
  Dist[B/b,Int[Sqrt[a+b*Log[c+d*x]],x]] /;
FreeQ[{a,b,c,d,A,B},x] && NonzeroQ[b*A-a*B]


(* ::Subsection::Closed:: *)
(*(a+b x)^m Log[c+d x]^n*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[(a_.+b_.*x_)^m_.*Log[c_.+d_.*x_]^n_,x_Symbol] :=
  (a+b*x)^(m+1)*Log[c+d*x]^n/(b*(m+1)) -
  Dist[d*n/(b*(m+1)),Int[Regularize[(a+b*x)^(m+1)*Log[c+d*x]^(n-1)/(c+d*x),x],x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[{m,n}] && m<-1 && n>0


(* ::Subsection::Closed:: *)
(*f^(a Log[u])				Exponentials of logarithms*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: f^(a*Log[g]) == g^(a*Log[f])*)


Int[f_^(a_.*Log[u_]),x_Symbol] :=
  Int[u^(a*Log[f]),x] /;
FreeQ[{a,f},x]


(* ::Subsection::Closed:: *)
(*Integration by substitution*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: 1/z == Log'[z]*)


Int[1/(a_.*x_+b_.*x_*Log[c_.*x_^n_.]^m_.),x_Symbol] :=
  Dist[1/n,Subst[Int[1/(a+b*x^m),x],x,Log[c*x^n]]] /;
FreeQ[{a,b,c,m,n},x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: 1/z == Log'[z]*)


If[ShowSteps,

Int[u_/x_,x_Symbol] :=
  Module[{lst=FunctionOfLog[u,x]},
  ShowStep["","Int[f[Log[a*x^n]]/x,x]","Subst[Int[f[x],x],x,Log[a*x^n]]/n",Hold[
  Dist[1/lst[[3]],Subst[Int[lst[[1]],x],x,Log[lst[[2]]]]]]] /;
 Not[FalseQ[lst]]] /;
SimplifyFlag && NonsumQ[u],

Int[u_/x_,x_Symbol] :=
  Module[{lst=FunctionOfLog[u,x]},
  Dist[1/lst[[3]],Subst[Int[lst[[1]],x],x,Log[lst[[2]]]]] /;
 Not[FalseQ[lst]]] /;
NonsumQ[u]]


(* ::Subsection::Closed:: *)
(*Integration by parts*)


(* ::Item::Closed:: *)
(*Reference: A&S 4.1.53*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Basis: D[Log[f[x]], x] == D[f[x],x] / f[x]*)


Int[Log[u_],x_Symbol] :=
  x*Log[u] -
  Int[Regularize[x*D[u,x]/u,x],x] /;
AlgebraicFunctionQ[u,x]
