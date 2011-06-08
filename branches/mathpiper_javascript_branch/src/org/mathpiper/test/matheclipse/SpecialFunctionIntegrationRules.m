(* ::Package:: *)

(* ::Title:: *)
(*Special Function Integration Rules*)


(* ::Subsection::Closed:: *)
(*Gamma Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*Gamma[n,a+b x]*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Gamma[n_,a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*Gamma[n,a+b*x]/b -
  Gamma[n+1,a+b*x]/b /;
FreeQ[{a,b},x]


(* ::Subsubsection::Closed:: *)
(*x^m Gamma[n,a+b x]*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Gamma[n_,a_.*x_],x_Symbol] :=
  x^(m+1)*Gamma[n,a*x]/(m+1) -
  Gamma[m+n+1,a*x]/((m+1)*a^(m+1)) /;
FreeQ[{a,n},x] && (IntegerQ[m] || PositiveQ[a])


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Gamma[n_,a_*x_],x_Symbol] :=
  x^(m+1)*Gamma[n,a*x]/(m+1) -
  x^(m+1)*Gamma[m+n+1,a*x]/((m+1)*(a*x)^(m+1)) /;
FreeQ[{a,m,n},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Gamma[n_,a_+b_.*x_],x_Symbol] :=
  x^m*(a+b*x)*Gamma[n,a+b*x]/(b*(m+1)) -
  x^m*Gamma[n+1,a+b*x]/(b*(m+1)) -
  Dist[a*m/(b*(m+1)),Int[x^(m-1)*Gamma[n,a+b*x],x]] +
  Dist[m/(b*(m+1)),Int[x^(m-1)*Gamma[n+1,a+b*x],x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m>0


(* ::Subsection::Closed:: *)
(*LogGamma Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*LogGamma[a+b x]*)


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: D[PolyGamma[-2,z],z] == LogGamma[z]*)


Int[LogGamma[a_.+b_.*x_],x_Symbol] :=
  PolyGamma[-2,a+b*x]/b /;
FreeQ[{a,b},x]


(* ::Subsubsection::Closed:: *)
(*x^m LogGamma[a+b x]*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*LogGamma[a_.+b_.*x_],x_Symbol] :=
  x^m*PolyGamma[-2,a+b*x]/b -
  Dist[m/b,Int[x^(m-1)*PolyGamma[-2,a+b*x],x]] /;
FreeQ[{a,b},x] && RationalQ[m] && m>0


(* ::Subsection::Closed:: *)
(*PolyGamma Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*PolyGamma[n, a+b x]*)


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: D[PolyGamma[n,z],z] == PolyGamma[n+1,z]*)


Int[PolyGamma[n_,a_.+b_.*x_],x_Symbol] :=
  PolyGamma[n-1,a+b*x]/b /;
FreeQ[{a,b,n},x]


(* ::Subsubsection::Closed:: *)
(*x^m PolyGamma[n, a+b x]*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*PolyGamma[n_,a_.+b_.*x_],x_Symbol] :=
  x^m*PolyGamma[n-1,a+b*x]/b -
  Dist[m/b,Int[x^(m-1)*PolyGamma[n-1,a+b*x],x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m>0


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_.*PolyGamma[n_,a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*PolyGamma[n,a+b*x]/(m+1) -
  Dist[b/(m+1),Int[x^(m+1)*PolyGamma[n+1,a+b*x],x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m<-1


(* ::Subsubsection::Closed:: *)
(*Gamma[a+b x]^n PolyGamma[0, a+b x]*)


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: D[Gamma[z]^n, z] == n Gamma[z]^n PolyGamma[0, z]*)


Int[Gamma[a_.+b_.*x_]^n_.*PolyGamma[0,a_.+b_.*x_],x_Symbol] :=
  Gamma[a+b*x]^n/(b*n) /;
FreeQ[{a,b,n},x]


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: D[(z!)^n, z] == n (z!)^n PolyGamma[0, 1+z]*)


Int[((a_.+b_.*x_)!)^n_.*PolyGamma[0,c_.+b_.*x_],x_Symbol] :=
  ((a+b*x)!)^n/(b*n) /;
FreeQ[{a,b,c,n},x] && ZeroQ[a-c+1]


(* ::Subsection::Closed:: *)
(*Zeta Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*Zeta[s, a+b x]*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Zeta[2,z] == PolyGamma[1,z]*)


Int[Zeta[2,a_.+b_.*x_],x_Symbol] :=
  Int[PolyGamma[1,a+b*x],x] /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: D[Zeta[s,z],z] == -s*Zeta[s+1,z]*)


Int[Zeta[s_,a_.+b_.*x_],x_Symbol] :=
  -Zeta[s-1,a+b*x]/(b*(s-1)) /;
FreeQ[{a,b,s},x] && NonzeroQ[s-1] && NonzeroQ[s-2]


(* ::Subsubsection::Closed:: *)
(*x^m Zeta[s, a+b x]*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Zeta[2,z] == PolyGamma[1,z]*)


Int[x_^m_.*Zeta[2,a_.+b_.*x_],x_Symbol] :=
  Int[x^m*PolyGamma[1,a+b*x],x] /;
FreeQ[{a,b},x] && RationalQ[m]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Zeta[s_,a_.+b_.*x_],x_Symbol] :=
  -x^m*Zeta[s-1,a+b*x]/(b*(s-1)) +
  Dist[m/(b*(s-1)),Int[x^(m-1)*Zeta[s-1,a+b*x],x]] /;
FreeQ[{a,b,s},x] && RationalQ[m] && m>0 && NonzeroQ[s-1] && NonzeroQ[s-2]


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_.*Zeta[s_,a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*Zeta[s,a+b*x]/(m+1) +
  Dist[b*s/(m+1),Int[x^(m+1)*Zeta[s+1,a+b*x],x]] /;
FreeQ[{a,b,s},x] && RationalQ[m] && m<-1 && NonzeroQ[s-1] && NonzeroQ[s-2]


(* ::Subsection::Closed:: *)
(*Polylogarithm Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*PolyLog[n, a (b x^p)^q]*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[PolyLog[n_,a_.*(b_.*x_^p_.)^q_.],x_Symbol] :=
  x*PolyLog[n,a*(b*x^p)^q] -
  Dist[p*q,Int[PolyLog[n-1,a*(b*x^p)^q],x]] /;
FreeQ[{a,b,p,q},x] && RationalQ[n] && n>0


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[PolyLog[n_,a_.*(b_.*x_^p_.)^q_.],x_Symbol] :=
  x*PolyLog[n+1,a*(b*x^p)^q]/(p*q) -
  Dist[1/(p*q),Int[PolyLog[n+1,a*(b*x^p)^q],x]] /;
FreeQ[{a,b,p,q},x] && RationalQ[n] && n<-1


(* ::Subsubsection::Closed:: *)
(*x^m PolyLog[n, a (b x^p)^q]*)


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: D[PolyLog[n,z],z] == PolyLog[n-1,z]/z*)


Int[PolyLog[n_,a_.*(b_.*x_^p_.)^q_.]/x_,x_Symbol] :=
  PolyLog[n+1,a*(b*x^p)^q]/(p*q) /;
FreeQ[{a,b,n,p,q},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*PolyLog[n_,a_.*(b_.*x_^p_.)^q_.],x_Symbol] :=
  x^(m+1)*PolyLog[n,a*(b*x^p)^q]/(m+1) -
  Dist[p*q/(m+1),Int[x^m*PolyLog[n-1,a*(b*x^p)^q],x]] /;
FreeQ[{a,b,m,p,q},x] && RationalQ[n] && n>0 && NonzeroQ[m+1]


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_.*PolyLog[n_,a_.*(b_.*x_^p_.)^q_.],x_Symbol] :=
  x^(m+1)*PolyLog[n+1,a*(b*x^p)^q]/(p*q) -
  Dist[(m+1)/(p*q),Int[x^m*PolyLog[n+1,a*(b*x^p)^q],x]] /;
FreeQ[{a,b,m,p,q},x] && RationalQ[n] && n<-1 && NonzeroQ[m+1]


(* ::Item:: *)
(*Derivation: Integration by substitution*)


Int[PolyLog[n_,u_]/(a_+b_.*x_),x_Symbol] :=
  Dist[1/b,Subst[Int[PolyLog[n,Regularize[Subst[u,x,-a/b+x/b],x]]/x,x],x,a+b*x]] /;
FreeQ[{a,b,n},x]


(* ::Subsubsection::Closed:: *)
(*PolyLog[n, c (a+b x)^p]*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[PolyLog[n_,c_.*(a_.+b_.*x_)^p_.],x_Symbol] :=
  x*PolyLog[n,c*(a+b*x)^p] -
  Dist[p,Int[PolyLog[n-1,c*(a+b*x)^p],x]] +
  Dist[a*p,Int[PolyLog[n-1,c*(a+b*x)^p]/(a+b*x),x]] /;
FreeQ[{a,b,c,p},x] && RationalQ[n] && n>0


(* ::Subsubsection::Closed:: *)
(*x^m PolyLog[n, c (a+b x)^p]*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*PolyLog[n_,c_.*(a_.+b_.*x_)^p_.],x_Symbol] :=
  x^(m+1)*PolyLog[n,c*(a+b*x)^p]/(m+1) -
  Dist[b*p/(m+1),Int[x^(m+1)*PolyLog[n-1,c*(a+b*x)^p]/(a+b*x),x]] /;
FreeQ[{a,b,c,m,p},x] && RationalQ[n] && n>0 && IntegerQ[m] && m>0


(* ::Subsubsection::Closed:: *)
(*Log[a x^n]^p PolyLog[q, b x^m]/x*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Log[a_.*x_^n_.]^p_.*PolyLog[q_,b_.*x_^m_.]/x_,x_Symbol] :=
  Log[a*x^n]^p*PolyLog[q+1,b*x^m]/m -
  Dist[n*p/m,Int[Log[a*x^n]^(p-1)*PolyLog[q+1,b*x^m]/x,x]] /;
FreeQ[{a,b,m,n,q},x] && RationalQ[p] && p>0


(* ::Subsection::Closed:: *)
(*LambertW (ProductLog) Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*ProductLog[a+b x]^p*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ProductLog[a_.+b_.*x_]^p_.,x_Symbol] :=
  (a+b*x)*ProductLog[a+b*x]^p/b -
  Dist[p,Int[ProductLog[a+b*x]^p/(1+ProductLog[a+b*x]),x]] /;
FreeQ[{a,b},x] && RationalQ[p] && p>=-1


Int[ProductLog[a_.+b_.*x_]^p_,x_Symbol] :=
  (a+b*x)*ProductLog[a+b*x]^p/(b*(p+1)) +
  Dist[p/(p+1),Int[ProductLog[a+b*x]^(p+1)/(1+ProductLog[a+b*x]),x]] /;
FreeQ[{a,b},x] && RationalQ[p] && p<-1


Int[ProductLog[a_.+b_.*x_]^p_,x_Symbol] :=
  ProductLog[a+b*x]^p/(-ProductLog[a+b*x])^p*Int[(-ProductLog[a+b*x])^p,x] /;
FreeQ[{a,b,p},x] && Not[RationalQ[p]]


(* ::Subsubsection::Closed:: *)
(*ProductLog[a+b x]^p / (1+ProductLog[a+b x])*)


Int[1/(1+ProductLog[a_.+b_.*x_]),x_Symbol] :=
  (a+b*x)/(b*ProductLog[a+b*x]) /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: z/(1+z) == 1-1/(1+z)*)


Int[ProductLog[a_.+b_.*x_]/(1+ProductLog[a_.+b_.*x_]),x_Symbol] :=
  x - 
  Int[1/(1+ProductLog[a+b*x]),x] /;
FreeQ[{a,b},x]


Int[1/(ProductLog[a_.+b_.*x_]*(1+ProductLog[a_.+b_.*x_])),x_Symbol] :=
  ExpIntegralEi[ProductLog[a+b*x]]/b /;
FreeQ[{a,b},x]


Int[1/(Sqrt[ProductLog[a_.+b_.*x_]]*(1+ProductLog[a_.+b_.*x_])),x_Symbol] :=
  Sqrt[Pi]*Erfi[Sqrt[ProductLog[a+b*x]]]/b /;
FreeQ[{a,b},x]


Int[ProductLog[a_.+b_.*x_]^p_/(1+ProductLog[a_.+b_.*x_]),x_Symbol] :=
  (a+b*x)*ProductLog[a+b*x]^(p-1)/b -
  Dist[p,Int[ProductLog[a+b*x]^(p-1)/(1+ProductLog[a+b*x]),x]] /;
FreeQ[{a,b},x] && RationalQ[p] && p>0


Int[ProductLog[a_.+b_.*x_]^p_./(1+ProductLog[a_.+b_.*x_]),x_Symbol] :=
  (a+b*x)*ProductLog[a+b*x]^p/(b*(p+1)) -
  Dist[1/(p+1),Int[ProductLog[a+b*x]^(p+1)/(1+ProductLog[a+b*x]),x]] /;
FreeQ[{a,b},x] && RationalQ[p] && p<-1


(* ::Subsubsection::Closed:: *)
(*x^m ProductLog[a+b x]^p*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ProductLog[a_.*x_]^p_.,x_Symbol] :=
  x^(m+1)*ProductLog[a*x]^p/(m+1) -
  Dist[p/(m+1),Int[x^m*ProductLog[a*x]^p/(1+ProductLog[a*x]),x]] /;
FreeQ[a,x] && RationalQ[{m,p}] && NonzeroQ[m+1] && m+p+1>=0


Int[x_^m_.*ProductLog[a_.*x_]^p_.,x_Symbol] :=
  x^(m+1)*ProductLog[a*x]^p/(m+p+1) +
  Dist[p/(m+p+1),Int[x^m*ProductLog[a*x]^(p+1)/(1+ProductLog[a*x]),x]] /;
FreeQ[a,x] && RationalQ[{m,p}] && NonzeroQ[m+1] && m+p+1<0


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1 == 1/(1+z) + z/(1+z)*)


Int[x_^m_.*ProductLog[a_.*x_]^p_.,x_Symbol] :=
  Int[x^m*ProductLog[a*x]^p/(1+ProductLog[a*x]),x] +
  Int[x^m*ProductLog[a*x]^(p+1)/(1+ProductLog[a*x]),x] /;
FreeQ[a,x] && NonzeroQ[m+1]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Int[f[a+b*x,x], x] == Subst[Int[f[x,-a/b+x/b], x], x, a+b*x]/b*)


Int[x_^m_.*ProductLog[a_+b_.*x_]^p_.,x_Symbol] :=
  Dist[1/b,Subst[Int[(-a/b+x/b)^m*ProductLog[x]^p,x],x,a+b*x]] /;
FreeQ[{a,b,p},x] && IntegerQ[m] && m>0


(* ::Subsubsection::Closed:: *)
(*x^m / (1+ProductLog[a+b x])*)


Int[x_^m_./(1+ProductLog[a_.*x_]),x_Symbol] :=
  x^(m+1)/((m+1)*ProductLog[a*x]) -
  Dist[m/(m+1),Int[x^m/(ProductLog[a*x]*(1+ProductLog[a*x])),x]] /;
FreeQ[a,x] && RationalQ[m] && m>0


Int[x_^m_./(1+ProductLog[a_.*x_]),x_Symbol] :=
  x^(m+1)/(m+1) -
  Int[x^m*ProductLog[a*x]/(1+ProductLog[a*x]),x] /;
FreeQ[a,x] && RationalQ[m] && m<-1


Int[x_^m_./(1+ProductLog[a_.*x_]),x_Symbol] :=
  x^m*Gamma[m+1,-(m+1)*ProductLog[a*x]]/
	(a*(m+1)*E^(m*ProductLog[a*x])*(-(m+1)*ProductLog[a*x])^m) /;
FreeQ[a,x] && NonzeroQ[m+1]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Int[f[a+b*x,x], x] == Subst[Int[f[x,-a/b+x/b], x], x, a+b*x]/b*)


Int[x_^m_./(1+ProductLog[a_+b_.*x_]),x_Symbol] :=
  Dist[1/b,Subst[Int[(-a/b+x/b)^m/(1+ProductLog[x]),x],x,a+b*x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0


(* ::Subsubsection::Closed:: *)
(*x^m ProductLog[a x]^p / (1+ProductLog[a x])*)
(**)


Int[x_^m_.*ProductLog[a_.*x_]^p_./(1+ProductLog[a_.*x_]),x_Symbol] :=
  x^(m+1)*ProductLog[a*x]^(p-1)/(m+1) /;
FreeQ[{a,m,p},x] && ZeroQ[m+(p-1)+1] && NonzeroQ[m+1]


Int[x_^m_.*ProductLog[a_.*x_]^p_./(1+ProductLog[a_.*x_]),x_Symbol] :=
  a^p*ExpIntegralEi[-p*ProductLog[a*x]] /;
FreeQ[{a,m},x] && IntegerQ[p] && ZeroQ[m+p+1]


Int[x_^m_.*ProductLog[a_.*x_]^p_/(1+ProductLog[a_.*x_]),x_Symbol] :=
  a^(p-1/2)*Sqrt[Pi/(p-1/2)]*Erf[Sqrt[(p-1/2)*ProductLog[a*x]]] /;
FreeQ[{a,m},x] && IntegerQ[p-1/2] && p>1 && ZeroQ[m+(p-1/2)+1]


Int[x_^m_.*ProductLog[a_.*x_]^p_/(1+ProductLog[a_.*x_]),x_Symbol] :=
  a^(p-1/2)*Sqrt[-Pi/(p-1/2)]*Erfi[Sqrt[-(p-1/2)*ProductLog[a*x]]] /;
FreeQ[{a,m},x] && IntegerQ[p-1/2] && p<0 && ZeroQ[m+(p-1/2)+1]


Int[x_^m_.*ProductLog[a_.*x_]^p_./(1+ProductLog[a_.*x_]),x_Symbol] :=
  x^(m+1)*ProductLog[a*x]^(p-1)/(m+1) -
  Dist[(m+(p-1)+1)/(m+1),Int[x^m*ProductLog[a*x]^(p-1)/(1+ProductLog[a*x]),x]] /;
FreeQ[a,x] && RationalQ[{m,p}] && NonzeroQ[m+1] && m+p>0


Int[x_^m_.*ProductLog[a_.*x_]^p_./(1+ProductLog[a_.*x_]),x_Symbol] :=
  x^(m+1)*ProductLog[a*x]^p/(m+p+1) -
  Dist[(m+1)/(m+p+1),Int[x^m*ProductLog[a*x]^(p+1)/(1+ProductLog[a*x]),x]] /;
FreeQ[a,x] && RationalQ[{m,p}] && NonzeroQ[m+1] && m+p<-1


Int[x_^m_.*ProductLog[a_.*x_]^p_./(1+ProductLog[a_.*x_]),x_Symbol] :=
  x^m*Gamma[m+p+1,-(m+1)*ProductLog[a*x]]*ProductLog[a*x]^p/
	(a*(m+1)*E^(m*ProductLog[a*x])*(-(m+1)*ProductLog[a*x])^(m+p)) /;
FreeQ[a,x] && NonzeroQ[m+1]


(* ::Subsubsection::Closed:: *)
(*ProductLog[a x^n]^p*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ProductLog[a_.*x_^n_]^p_.,x_Symbol] :=
  x*ProductLog[a*x^n]^p -
  Dist[n*p,Int[ProductLog[a*x^n]^p/(1+ProductLog[a*x^n]),x]] /;
FreeQ[{a,n,p},x] && (ZeroQ[n*(p-1)+1] || IntegerQ[p-1/2] && ZeroQ[n*(p-1/2)+1])


Int[ProductLog[a_.*x_^n_]^p_.,x_Symbol] :=
  x*ProductLog[a*x^n]^p/(n*p+1) +
  Dist[n*p/(n*p+1),Int[ProductLog[a*x^n]^(p+1)/(1+ProductLog[a*x^n]),x]] /;
FreeQ[{a,n},x] && (IntegerQ[p] && ZeroQ[n*(p+1)+1] || IntegerQ[p-1/2] && ZeroQ[n*(p+1/2)+1])


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Int[f[x], x] == -Subst[Int[f[1/x]/x^2, x], x, 1/x]*)


Int[ProductLog[a_.*x_^n_]^p_.,x_Symbol] :=
  -Subst[Int[ProductLog[a*x^(-n)]^p/x^2,x],x,1/x] /;
FreeQ[{a,p},x] && IntegerQ[n] && n<0


(* ::Subsubsection::Closed:: *)
(*ProductLog[a x^n]^p / (1+ProductLog[a x^n])*)


Int[ProductLog[a_.*x_^n_]^p_./(1+ProductLog[a_.*x_^n_]),x_Symbol] :=
  x*ProductLog[a*x^n]^(p-1) /;
FreeQ[{a,n,p},x] && ZeroQ[n*(p-1)+1]


Int[ProductLog[a_.*x_^n_]^p_./(1+ProductLog[a_.*x_^n_]),x_Symbol] :=
  a^p*ExpIntegralEi[-p*ProductLog[a*x^n]]/n /;
FreeQ[{a,n},x] && IntegerQ[p] && ZeroQ[n*p+1]


Int[ProductLog[a_.*x_^n_]^p_/(1+ProductLog[a_.*x_^n_]),x_Symbol] :=
  a^(p-1/2)*Sqrt[Pi/(p-1/2)]*Erf[Sqrt[(p-1/2)*ProductLog[a*x^n]]]/n /;
FreeQ[{a,n},x] && IntegerQ[p-1/2] && p>1 && ZeroQ[n*(p-1/2)+1]


Int[ProductLog[a_.*x_^n_]^p_/(1+ProductLog[a_.*x_^n_]),x_Symbol] :=
  a^(p-1/2)*Sqrt[-Pi/(p-1/2)]*Erfi[Sqrt[-(p-1/2)*ProductLog[a*x^n]]]/n /;
FreeQ[{a,n},x] && IntegerQ[p-1/2] && p<0 && ZeroQ[n*(p-1/2)+1]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Int[f[x], x] == -Subst[Int[f[1/x]/x^2, x], x, 1/x]*)


Int[ProductLog[a_.*x_^n_]^p_./(1+ProductLog[a_.*x_^n_]),x_Symbol] :=
  -Subst[Int[ProductLog[a*x^(-n)]^p/(x^2*(1+ProductLog[a*x^(-n)])),x],x,1/x] /;
FreeQ[{a,p},x] && IntegerQ[n] && n<0


(* ::Subsubsection::Closed:: *)
(*x^m ProductLog[a x^n]^p*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ProductLog[a_.*x_^n_]^p_.,x_Symbol] :=
  x^(m+1)*ProductLog[a*x^n]^p/(m+1) -
  Dist[n*p/(m+1),Int[x^m*ProductLog[a*x^n]^p/(1+ProductLog[a*x^n]),x]] /;
FreeQ[{a,m,n,p},x] && NonzeroQ[m+1] &&
	(ZeroQ[m+n*(p-1)+1] || 
	 IntegerQ[p] && ZeroQ[m+n*p+1] ||
	 IntegerQ[p-1/2] && ZeroQ[m+n*(p-1/2)+1])


Int[x_^m_.*ProductLog[a_.*x_^n_]^p_.,x_Symbol] :=
  x^(m+1)*ProductLog[a*x^n]^p/(m+n*p+1) +
  Dist[n*p/(m+n*p+1),Int[x^m*ProductLog[a*x^n]^(p+1)/(1+ProductLog[a*x^n]),x]] /;
FreeQ[{a,m,n,p},x] && NonzeroQ[m+1] &&
	(IntegerQ[p] && ZeroQ[m+n*(p+1)+1] || IntegerQ[p-1/2] && ZeroQ[m+n*(p+1/2)+1])


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Int[f[x], x] == -Subst[Int[f[1/x]/x^2, x], x, 1/x]*)


Int[x_^m_.*ProductLog[a_.*x_^n_]^p_.,x_Symbol] :=
  -Subst[Int[ProductLog[a*x^(-n)]^p/x^(m+2),x],x,1/x] /;
FreeQ[{a,p},x] && IntegerQ[{m,n}] && n<0 && NonzeroQ[m+1]


(* ::Subsubsection::Closed:: *)
(*x^m / (1+ProductLog[a x^n])*)


(* Int[x_^m_./(1+ProductLog[a_.*x_^n_]),x_Symbol] :=
  x^(m+1)/((m+1)*ProductLog[a*x^n]) -
  Dist[(m-n+1)/(m+1),Int[x^m/(ProductLog[a*x^n]*(1+ProductLog[a*x^n])),x]] /;
FreeQ[a,x] && RationalQ[{m,n}] && m>0 && NonzeroQ[m-n+1] *)


(* Int[x_^m_./(1+ProductLog[a_.*x_^n_]),x_Symbol] :=
  x^(m+1)/(m+1) -
  Int[x^m*ProductLog[a*x^n]/(1+ProductLog[a*x^n]),x] /;
FreeQ[a,x] && RationalQ[{m,n}] && m<-1 *)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Int[f[x], x] == -Subst[Int[f[1/x]/x^2, x], x, 1/x]*)


Int[1/(1+ProductLog[a_.*x_^n_]),x_Symbol] :=
  -Subst[Int[1/(x^2*(1+ProductLog[a*x^(-n)])),x],x,1/x] /;
FreeQ[a,x] && IntegerQ[n] && n<0


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Int[f[x], x] == -Subst[Int[f[1/x]/x^2, x], x, 1/x]*)


Int[x_^m_./(1+ProductLog[a_.*x_^n_]),x_Symbol] :=
  -Subst[Int[1/(x^(m+2)*(1+ProductLog[a*x^(-n)])),x],x,1/x] /;
FreeQ[a,x] && IntegerQ[{m,n}] && n<0 && NonzeroQ[m+1]


(* ::Subsubsection::Closed:: *)
(*x^m ProductLog[a x^n]^p / (1+ProductLog[a x^n])*)
(**)


Int[x_^m_.*ProductLog[a_.*x_^n_]^p_./(1+ProductLog[a_.*x_^n_]),x_Symbol] :=
  x^(m+1)*ProductLog[a*x^n]^(p-1)/(m+1) /;
FreeQ[{a,m,n,p},x] && ZeroQ[m+n*(p-1)+1] && NonzeroQ[m+1]


Int[x_^m_.*ProductLog[a_.*x_^n_]^p_./(1+ProductLog[a_.*x_^n_]),x_Symbol] :=
  a^p*ExpIntegralEi[-p*ProductLog[a*x^n]]/n /;
FreeQ[{a,m,n},x] && IntegerQ[p] && ZeroQ[m+n*p+1]


Int[x_^m_.*ProductLog[a_.*x_^n_]^p_/(1+ProductLog[a_.*x_^n_]),x_Symbol] :=
  a^(p-1/2)*Sqrt[Pi/(p-1/2)]*Erf[Sqrt[(p-1/2)*ProductLog[a*x^n]]]/n /;
FreeQ[{a,m,n},x] && IntegerQ[p-1/2] && p>1 && ZeroQ[m+n*(p-1/2)+1]


Int[x_^m_.*ProductLog[a_.*x_^n_]^p_/(1+ProductLog[a_.*x_^n_]),x_Symbol] :=
  a^(p-1/2)*Sqrt[-Pi/(p-1/2)]*Erfi[Sqrt[-(p-1/2)*ProductLog[a*x^n]]]/n /;
FreeQ[{a,m,n},x] && IntegerQ[p-1/2] && p<0 && ZeroQ[m+n*(p-1/2)+1]


Int[x_^m_.*ProductLog[a_.*x_^n_]^p_./(1+ProductLog[a_.*x_^n_]),x_Symbol] :=
  x^(m+1)*ProductLog[a*x^n]^(p-1)/(m+1) -
  Dist[(m+n*(p-1)+1)/(m+1),Int[x^m*ProductLog[a*x^n]^(p-1)/(1+ProductLog[a*x^n]),x]] /;
FreeQ[a,x] && RationalQ[{m,n,p}] && n>0 && NonzeroQ[m+1] && m+n*(p-1)+1>0


Int[x_^m_.*ProductLog[a_.*x_^n_]^p_./(1+ProductLog[a_.*x_^n_]),x_Symbol] :=
  x^(m+1)*ProductLog[a*x^n]^p/(m+n*p+1) -
  Dist[(m+1)/(m+n*p+1),Int[x^m*ProductLog[a*x^n]^(p+1)/(1+ProductLog[a*x^n]),x]] /;
FreeQ[a,x] && RationalQ[{m,n,p}] && n>0 && NonzeroQ[m+1] && m+n*p+1<0


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Int[f[x], x] == -Subst[Int[f[1/x]/x^2, x], x, 1/x]*)


Int[x_^m_.*ProductLog[a_.*x_^n_]^p_./(1+ProductLog[a_.*x_^n_]),x_Symbol] :=
  -Subst[Int[ProductLog[a*x^(-n)]^p/(x^(m+2)*(1+ProductLog[a*x^(-n)])),x],x,1/x] /;
FreeQ[{a,p},x] && IntegerQ[{m,n}] && n<0 && NonzeroQ[m+1]


(* ::Subsubsection::Closed:: *)
(*(-ProductLog[a+b x])^p / (1+ProductLog[a+b x])*)


Int[(-ProductLog[a_.+b_.*x_])^p_./(1+ProductLog[a_.+b_.*x_]),x_Symbol] :=
  Gamma[p+1,-ProductLog[a+b*x]]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1 == 1/(1+z) - -z/(1+z)*)


Int[(-ProductLog[a_.+b_.*x_])^p_,x_Symbol] :=
  Int[(-ProductLog[a+b*x])^p/(1+ProductLog[a+b*x]),x] -
  Int[(-ProductLog[a+b*x])^(p+1)/(1+ProductLog[a+b*x]),x] /;
FreeQ[{a,b,p},x]


(* ::Subsubsection::Closed:: *)
(*f[ProductLog[a+b x]]*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: 1/z == (1 + ProductLog[z])/ProductLog[z]*ProductLog'[z]*)


If[ShowSteps,

Int[u_/x_,x_Symbol] :=
  Module[{lst=FunctionOfProductLog[u,x]},
  ShowStep["","Int[f[ProductLog[a*x^n]]/x,x]","Subst[Int[f[x]*(1+x)/x,x],x,ProductLog[a*x^n]]/n",Hold[
  Dist[1/lst[[3]],Subst[Int[Regularize[lst[[1]]*(1+x)/x,x],x],x,ProductLog[lst[[2]]]]]]] /;
 Not[FalseQ[lst]]] /;
SimplifyFlag && NonsumQ[u],

Int[u_/x_,x_Symbol] :=
  Module[{lst=FunctionOfProductLog[u,x]},
  Dist[1/lst[[3]],Subst[Int[Regularize[lst[[1]]*(1+x)/x,x],x],x,ProductLog[lst[[2]]]]] /;
 Not[FalseQ[lst]]] /;
NonsumQ[u]]


(* ::Item:: *)
(*Derivation: Integration by *)


(* Int[x_^m_.*ProductLog[a_.+b_.*x_],x_Symbol] :=
  Dist[1/b^(m+1),Subst[Int[Regularize[(x*E^x-a)^m*x*(x+1)*E^x,x],x],x,ProductLog[a+b*x]]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0 *)


(* ::Item::Closed:: *)
(*Author: Rob Corless 2009-07-10*)


(* ::Item:: *)
(*Derivation: Legendre substitution for inverse functions*)


(* ::Item:: *)
(*Basis: ProductLog[z]*E^ProductLog[z] == z*)


Int[u_,x_Symbol] :=
  Subst[Int[Regularize[(x+1)*E^x*SubstFor[ProductLog[x],u,x],x],x],x,ProductLog[x]] /;
FunctionOfQ[ProductLog[x],u,x]
