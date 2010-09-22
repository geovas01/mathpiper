(* ::Package:: *)

(* ::Title:: *)
(*Algebraic Function Integration Rules*)


(* ::Subsection::Closed:: *)
(*(c (a + b x)^n)^m		Powers of powers of linear binomials*)


(* ::Item::Closed:: *)
(*Derivation: Reciprocal rule for integration*)


(* ::Item:: *)
(*Basis: D[(a*f[x]^n)^m/f[x]^(m*n),x] == 0*)


Int[(c_.*(a_.+b_.*x_)^n_.)^m_,x_Symbol] :=
  (a+b*x)*(c*(a+b*x)^n)^m*Log[a+b*x]/b /;
FreeQ[{a,b,c,m,n},x] && ZeroQ[m*n+1]


(* ::Item::Closed:: *)
(*Derivation: Power rule for integration*)


(* ::Item:: *)
(*Basis: D[(a*f[x]^n)^m/f[x]^(m*n),x] == 0*)


Int[(c_.*(a_.+b_.*x_)^n_)^m_,x_Symbol] :=
  (a+b*x)*(c*(a+b*x)^n)^m/(b*(m*n+1)) /;
FreeQ[{a,b,c,m,n},x] && NonzeroQ[m*n+1]


(* ::Subsection::Closed:: *)
(*u (a v^m w^n ...)^p		Distribute powers over powers and products*)


(* Note: The generalization of these rules when m*p is rational is in GeneralIntegrationRules.m *)


(* ::Item:: *)
(*Basis: D[(a*f[x]^n)^m/f[x]^(m*n),x] == 0*)


Int[u_.*(a_.*v_^m_)^p_, x_Symbol] :=
  Module[{q=FractionalPart[p]},
  a^(p-q)*(a*v^m)^q/v^(m*q)*Int[u*v^(m*p),x]] /;
FreeQ[{a,m,p},x] && IntegerQ[m*p]


(* ::Item:: *)
(*Basis: D[(a*f[x]^m*g[x]^n)^p/(f[x]^(m*p)*g[x]^(n*p)),x] == 0*)


Int[u_.*(a_.*v_^m_.*w_^n_.)^p_, x_Symbol] :=
  Module[{q=FractionalPart[p]},
  a^(p-q)*(a*v^m*w^n)^q/(v^(m*q)*w^(n*q))*Int[u*v^(m*p)*w^(n*p),x]] /;
FreeQ[{a,m,n,p},x] && IntegerQ[m*p] && IntegerQ[n*p]


(* ::Subsection::Closed:: *)
(*a x^m + b x^n + \[CenterEllipsis]		Integrands involving sums of monomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: a*z^m+b*z^n == z^m*(a+b*z^(n-m))*)


(* ::Item:: *)
(*Note : Way kool rule!  If m*(p+1)-n+1=0, then rule for x^(n-1)*(a+b*x^n)^p will fire.*)


Int[(a_.*x_^m_.+b_.*x_^n_.)^p_,x_Symbol] :=
  Int[x^(m*p)*(a+b*x^(n-m))^p,x] /;
FreeQ[{a,b,m,n},x] && IntegerQ[p] && ZeroQ[m*(p+1)-n+1] && Not[IntegerQ[{m,n}]]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: a*z^m+b*z^n == z^m*(a+b*z^(n-m))*)


(* ::Item:: *)
(*Note : Way kool rule!  If m*(p+1)-n+1=0, then rule for x^(n-1)*(a+b*x^n)^p will fire.*)


Int[(a_.*x_^m_.+b_.*x_^n_.)^p_,x_Symbol] :=
  Int[(x^m*(a+b*x^(n-m)))^p,x] /;
FreeQ[{a,b,m,n},x] && FractionQ[p] && ZeroQ[m*(p+1)-n+1]


(* Int[(a_.*x_^m_.+b_.*x_^n_.)^p_,x_Symbol] :=
  Int[(x^m*(a+b*x^(n-m)))^p,x] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && m<=n && HalfIntegerQ[p] && ZeroQ[m*(p+1)-n+1] *)


(* Int[(a_.*x_^m_.+b_.*x_^n_.)^p_,x_Symbol] :=
  Int[(x^m*(a+b*x^(n-m)))^p,x] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && m<=n && HalfIntegerQ[p] && Not[2*m==n==2] *)


Int[(a_.*x_^m_.+b_.*x_^n_.+c_.*x_^q_.)^p_,x_Symbol] :=
  Int[(x^m*(a+b*x^(n-m)+c*x^(q-m)))^p,x] /;
FreeQ[{a,b,c},x] && IntegerQ[{m,n,q}] && FractionQ[p] && m<=n<=q


Int[u_.*x_^m_./(a_.*x_^n_.+b_.*x_^p_),x_Symbol] :=
  Int[u*x^(m-n)/(a+b*x^(p-n)),x] /;
FreeQ[{a,b},x] && FractionQ[{m,n,p}] && 0<n<=p


Int[(u_.*x_^m_.+v_)/(a_.*x_^n_.+b_.*x_^p_),x_Symbol] :=
  Int[u*x^(m-n)/(a+b*x^(p-n)),x] + Int[v/(a*x^n+b*x^p),x] /;
FreeQ[{a,b},x] && FractionQ[{m,n,p}] && 0<n<=p && Not[PolynomialQ[v,x]]
(* Int[x^m*(u+v+\[CenterEllipsis]),x] --> Int[x^m*u+x^m*v+\[CenterEllipsis],x] *)


If[ShowSteps,
Int[x_^m_.*u_,x_Symbol] :=
  ShowStep["","Int[x^m*(u+v+\[CenterEllipsis]),x]","Int[x^m*u+x^m*v+\[CenterEllipsis],x]",Hold[
  Int[Map[Function[x^m*#],u],x]]] /;
SimplifyFlag && FreeQ[m,x] && SumQ[u],

Int[x_^m_.*u_,x_Symbol] :=
  Int[Map[Function[x^m*#],u],x] /;
FreeQ[m,x] && SumQ[u]]


(* ::Subsection::Closed:: *)
(*(a+b x^n)^p / x		Quotients of powers of binomials by integation variable*)


(* ::Item:: *)
(*Reference: CRC 276b*)


Int[1/(x_*Sqrt[a_+b_.*x_^n_.]),x_Symbol] :=
  -2*ArcTanh[Sqrt[a+b*x^n]/Rt[a,2]]/(n*Rt[a,2]) /;
FreeQ[{a,b,n},x] && PosQ[a]


(* ::Item:: *)
(*Reference: CRC 277*)


Int[1/(x_*Sqrt[a_+b_.*x_^n_.]),x_Symbol] :=
  2*ArcTan[Sqrt[a+b*x^n]/Rt[-a,2]]/(n*Rt[-a,2]) /;
FreeQ[{a,b,n},x] && NegQ[a]


(* ::Item:: *)
(*Reference: G&R 2.110.1, CRC 88b*)


Int[(a_+b_.*x_^n_.)^p_/x_,x_Symbol] :=
  (a+b*x^n)^p/(n*p) +
  Dist[a,Int[(a+b*x^n)^(p-1)/x,x]] /;
FreeQ[{a,b,n},x] && FractionQ[p] && p>0


(* ::Item:: *)
(*Reference: G&R 2.110.2, CRC 88d*)


Int[(a_+b_.*x_^n_.)^p_/x_,x_Symbol] :=
  -(a+b*x^n)^(p+1)/(a*n*(p+1)) +
  Dist[1/a,Int[(a+b*x^n)^(p+1)/x,x]] /;
FreeQ[{a,b,n},x] && FractionQ[p] && p<-1


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[x^n]/x == (f[x^n]/x^n)*D[x^n,x]/n*)


(* Int[(a_+b_.*x_^n_)^p_/x_,x_Symbol] :=
  Subst[Int[(a+b*x)^p/x,x],x,x^n]/n /;
FreeQ[{a,b,n},x] && FractionQ[p] && -1<p<0 && p!=-1/2 *)


(* ::Subsection::Closed:: *)
(*(a + b x)^m (c + d x)^n	Products of powers of linear binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If a+c>=0, (a+z)^m*(c-z)^m == (a*c-(a-c)*z-z^2)^m*)


Int[u_.*(a_+b_.*x_)^m_*(c_+d_.*x_)^m_,x_Symbol] :=
  Int[u*(a*c+(a*d+b*c)*x+b*d*x^2)^m,x] /;
FreeQ[{a,b,c,d},x] && FractionQ[m] && ZeroQ[b+d] && PositiveQ[a+c]


(* ::Subsubsection::Closed:: *)
(*1 / ((a+b x) Sqrt[c+d x])	Reciprocals of products of linears and square-roots of linears*)


(* ::Item:: *)
(*Reference: G&R 2.246.1', CRC 147a', A&S 3.3.30'*)


Int[1/((a_.+b_.*x_)*Sqrt[c_.+d_.*x_]),x_Symbol] :=
  -2*ArcTanh[Sqrt[c+d*x]/Rt[(b*c-a*d)/b,2]]/(b*Rt[(b*c-a*d)/b,2]) /;
FreeQ[{a,b,c,d},x] && PosQ[(b*c-a*d)/b]


(* ::Item:: *)
(*Reference: G&R 2.246.2, CRC 148, A&S 3.3.29*)


Int[1/((a_.+b_.*x_)*Sqrt[c_.+d_.*x_]),x_Symbol] :=
  2*ArcTan[Sqrt[c+d*x]/Rt[(a*d-b*c)/b,2]]/(b*Rt[(a*d-b*c)/b,2]) /;
FreeQ[{a,b,c,d},x] && NegQ[(b*c-a*d)/b]


(* ::Subsubsection::Closed:: *)
(*1 / (Sqrt[a+b x] Sqrt[c+d x])	Reciprocal of products of square-roots of linears*)


Int[1/(Sqrt[a_+b_.*x_]*Sqrt[c_+b_.*x_]),x_Symbol] :=
  ArcCosh[b*x/a]/b /;
FreeQ[{a,b,c},x] && ZeroQ[a+c] && PositiveQ[a]


Int[1/(Sqrt[a_.+b_.*x_]*Sqrt[c_.+d_.*x_]),x_Symbol] :=
  2*Rt[b/d,2]*ArcSinh[Sqrt[a+b*x]/Rt[b/d,2]]/b /;
FreeQ[{a,b,c,d},x] && PosQ[b/d] && ZeroQ[a*d-b*c+b]


Int[1/(Sqrt[a_.+b_.*x_]*Sqrt[c_.+d_.*x_]),x_Symbol] :=
  2*Rt[-b/d,2]*ArcSin[Sqrt[a+b*x]/Rt[-b/d,2]]/b /;
FreeQ[{a,b,c,d},x] && NegQ[b/d] && ZeroQ[a*d-b*c+b]


Int[1/(Sqrt[a_.+b_.*x_]*Sqrt[c_.+d_.*x_]),x_Symbol] :=
  2*Rt[b/d,2]*ArcSinh[Rt[b/(a*d-b*c),2]*Sqrt[c+d*x]]/b /;
FreeQ[{a,b,c,d},x] && PosQ[b/d] && PositiveQ[(a*d-b*c)/d]


Int[1/(Sqrt[a_.+b_.*x_]*Sqrt[c_.+d_.*x_]),x_Symbol] :=
  -2*Rt[-b/d,2]*ArcSin[Rt[b/(b*c-a*d),2]*Sqrt[c+d*x]]/b /;
FreeQ[{a,b,c,d},x] && NegQ[b/d] && PositiveQ[(a*d-b*c)/d]


Int[1/(Sqrt[a_.+b_.*x_]*Sqrt[c_.+d_.*x_]),x_Symbol] :=
  2*Rt[d/b,2]*ArcTanh[Rt[d/b,2]*Sqrt[a+b*x]/Sqrt[c+d*x]]/d /;
FreeQ[{a,b,c,d},x] && PosQ[d/b] && NonzeroQ[a*d-b*c]


Int[1/(Sqrt[a_.+b_.*x_]*Sqrt[c_.+d_.*x_]),x_Symbol] :=
  -2*Rt[-d/b,2]*ArcTan[Rt[-d/b,2]*Sqrt[a+b*x]/Sqrt[c+d*x]]/d /;
FreeQ[{a,b,c,d},x] && NegQ[d/b] && NonzeroQ[a*d-b*c]


(* ::Subsubsection::Closed:: *)
(*(a+b x)^m (c+d x)^n		Products of powers of linears*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If b*c-a*d=0 and n is an integer, (a+b*x)^m*(c+d*x)^n == (d/b)^n*(a+b*x)^(m+n)*)


Int[(a_.+b_.*x_)^m_.*(c_.+d_.*x_)^n_.,x_Symbol] :=
  Dist[(d/b)^n,Int[(a+b*x)^(m+n),x]] /;
FreeQ[{a,b,c,d,m},x] && IntegerQ[n] && ZeroQ[b*c-a*d] && Not[IntegerQ[m]]


Int[(a_.+b_.*x_)^m_*(c_.+d_.*x_)^n_,x_Symbol] :=
  (a+b*x)^(m+1)*(c+d*x)^n*Log[a+b*x]/b /;
FreeQ[{a,b,c,d,m,n},x] && Not[IntegerQ[m]] && Not[IntegerQ[n]] && ZeroQ[b*c-a*d] && ZeroQ[m+n+1] && 
(LeafCount[a+b*x]<LeafCount[c+d*x] || LeafCount[a+b*x]==LeafCount[c+d*x] && PosQ[a])


Int[(a_.+b_.*x_)^m_*(c_.+d_.*x_)^n_,x_Symbol] :=
  (a+b*x)^(m+1)*(c+d*x)^n/(b*(m+n+1)) /;
FreeQ[{a,b,c,d,m,n},x] && Not[IntegerQ[m]] && Not[IntegerQ[n]] && ZeroQ[b*c-a*d] && 
NonzeroQ[m+n+1]


(* ::Item:: *)
(*Reference: G&R 2.155, CRC 59a*)


Int[(a_.+b_.*x_)^m_.*(c_.+d_.*x_.)^n_,x_Symbol] :=
  -(a+b*x)^(m+1)*(c+d*x)^(n+1)/((n+1)*(b*c-a*d)) /;
FreeQ[{a,b,c,d,m,n},x] && ZeroQ[m+n+2] && NonzeroQ[b*c-a*d] && NonzeroQ[n+1]


(* ::Item:: *)
(*Reference: G&R 2.151, CRC 59b*)


Int[(c_.+d_.*x_)^n_./(a_.+b_.*x_),x_Symbol] :=
  (c+d*x)^n/(b*n) +
  Dist[(b*c-a*d)/b,Int[(c+d*x)^(n-1)/(a+b*x),x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[b*c-a*d] && FractionQ[n] && n>0


(* ::Item:: *)
(*Reference: G&R 2.151, CRC 59b*)


(* Note: Experimental!!! *)
Int[(a_.+b_.*x_)^m_*(c_.+d_.*x_),x_Symbol] :=
  (a+b*x)^(m+1)*(c+d*x)/(b*(m+2)) +
  Dist[(b*c-a*d)/(b*(m+2)),Int[(a+b*x)^m,x]] /;
FreeQ[{a,b,c,d,m},x] && Not[IntegerQ[m]] && NonzeroQ[b*c-a*d]


(* ::Item:: *)
(* Reference: G&R 2.151, CRC 59b*)


Int[(a_.+b_.*x_)^m_*(c_.+d_.*x_)^n_,x_Symbol] :=
  (a+b*x)^(m+1)*(c+d*x)^n/(b*(m+n+1)) +
  Dist[n/(m+n+1)*(b*c-a*d)/b,Int[(a+b*x)^m*(c+d*x)^(n-1),x]] /;
FreeQ[{a,b,c,d,m},x] && NonzeroQ[b*c-a*d] && NonzeroQ[m+n+1] && RationalQ[n] && n>0 &&
Not[IntegerQ[m]] && (IntegerQ[n] || FractionQ[m] && (n<=m || -1<=m<0))


(* ::Item:: *)
(*Reference: G&R 2.155, CRC 59a*)


Int[(a_.+b_.*x_)^m_.*(c_.+d_.*x_)^n_,x_Symbol] :=
  -(a+b*x)^(m+1)*(c+d*x)^(n+1)/((n+1)*(b*c-a*d)) +
  Dist[(m+n+2)/(n+1)*b/(b*c-a*d),Int[(a+b*x)^m*(c+d*x)^(n+1),x]] /;
FreeQ[{a,b,c,d,m},x] && NonzeroQ[b*c-a*d] && RationalQ[n] && n<-1 && Not[IntegerQ[{m,n}]] &&
(Not[RationalQ[m]] || n>=m || -1<=m<0 (* || n+2<=2*(m+n+2)<0 *))


(* ::Item:: *)
(*Reference: G&R 2.155, CRC 59a*)


Int[(a_.+b_.*x_)^m_.*(c_.+d_.*x_)^n_,x_Symbol] :=
  -(a+b*x)^(m+1)*(c+d*x)^(n+1)/((n+1)*(b*c-a*d)) +
  Dist[(m+n+2)/(n+1)*b/(b*c-a*d),Int[(a+b*x)^m*(c+d*x)^(n+1),x]] /;
FreeQ[{a,b,c,d,m,n},x] && NonzeroQ[b*c-a*d] && NonzeroQ[n+1] && Not[RationalQ[n]] && 
RationalQ[m+n] && Simplify[m+n]<-1


(* ::Item:: *)
(*Reference: G&R 2.153.3, CRC 59c*)


Int[(a_.+b_.*x_)*(c_.+d_.*x_)^n_,x_Symbol] :=
  (a+b*x)*(c+d*x)^(n+1)/(d*(n+1)) -
  Dist[b/(d*(n+1)),Int[(c+d*x)^(n+1),x]] /;
FreeQ[{a,b,c,d,n},x] && Not[IntegerQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.153.3, CRC 59c*)


Int[(a_.+b_.*x_)^m_*(c_.+d_.*x_)^n_,x_Symbol] :=
  (a+b*x)^m*(c+d*x)^(n+1)/(d*(n+1)) -
  Dist[m/(n+1)*b/d,Int[(a+b*x)^(m-1)*(c+d*x)^(n+1),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[{m,n}] && Not[IntegerQ[{m,n}]] && m>0 && n<-1


Int[(a_.+b_.*x_)^m_/(c_+d_.*x_),x_Symbol] :=
  Module[{p=Denominator[m]},
  Dist[p,Subst[Int[x^(m*p+p-1)/(b*c-a*d+d*x^p),x],x,(a+b*x)^(1/p)]]] /;
FreeQ[{a,b,c,d},x] && FractionQ[m] && -1<m<0


Int[(a_.+b_.*x_)^m_*(c_.+d_.*x_)^n_,x_Symbol] :=
  Module[{p=Denominator[m]},
  Dist[p,Subst[Int[x^(m*p+p-1)/(b-d*x^p),x],x,(a+b*x)^(1/p)/(c+d*x)^(1/p)]]] /;
FreeQ[{a,b,c,d},x] && FractionQ[{m,n}] && -1<m<0 && m+n==-1


(* ::Subsubsection::Closed:: *)
(*x^m (a+b x)^n (c+d x)^n	Products of monomials and the same powers of two linears*)


Int[1/(x_*Sqrt[a_+b_.*x_]*Sqrt[c_+d_.*x_]),x_Symbol] :=
  -2*ArcTanh[Rt[c/a,2]*Sqrt[a+b*x]/Sqrt[c+d*x]]/(a*Rt[c/a,2]) /;
FreeQ[{a,b,c,d},x] && PosQ[c/a]


Int[1/(x_*Sqrt[a_+b_.*x_]*Sqrt[c_+d_.*x_]),x_Symbol] :=
  -2*ArcTan[Rt[-c/a,2]*Sqrt[a+b*x]/Sqrt[c+d*x]]/(a*Rt[-c/a,2]) /;
FreeQ[{a,b,c,d},x] && NegQ[c/a]


(* ::Item:: *)
(*Reference: G&R 2.265b*)


Int[(a_+b_.*x_)^n_*(c_+d_.*x_)^n_/x_,x_Symbol] :=
  (a+b*x)^n*(c+d*x)^n/(2*n) +
  Dist[a*c,Int[(a+b*x)^(n-1)*(c+d*x)^(n-1)/x,x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n>0 && ZeroQ[a*d+b*c]


Int[(a_+b_.*x_)^n_*(c_+d_.*x_)^n_/x_,x_Symbol] :=
  (a+b*x)^n*(c+d*x)^n/(2*n) +
  Dist[(a*d+b*c)/2,Int[(a+b*x)^(n-1)*(c+d*x)^(n-1),x]] +
  Dist[a*c,Int[(a+b*x)^(n-1)*(c+d*x)^(n-1)/x,x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n>0 && NonzeroQ[a*d+b*c]


(* ::Item:: *)
(*Reference: G&R 2.268b, CRC 122*)


Int[(a_+b_.*x_)^n_*(c_+d_.*x_)^n_/x_,x_Symbol] :=
  -(a+b*x)^(n+1)*(c+d*x)^(n+1)/(2*a*c*(n+1)) +
  Dist[1/(a*c),Int[(a+b*x)^(n+1)*(c+d*x)^(n+1)/x,x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n<-1 && ZeroQ[a*d+b*c]


Int[(a_+b_.*x_)^n_*(c_+d_.*x_)^n_/x_,x_Symbol] :=
  -(a+b*x)^(n+1)*(c+d*x)^(n+1)/(2*a*c*(n+1)) -
  Dist[(a*d+b*c)/(2*a*c),Int[(a+b*x)^n*(c+d*x)^n,x]] +
  Dist[1/(a*c),Int[(a+b*x)^(n+1)*(c+d*x)^(n+1)/x,x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n<-1 && NonzeroQ[a*d+b*c]


(* ::Item:: *)
(*Reference: G&R 2.174.2*)


Int[x_^m_*(a_.+b_.*x_)^n_*(c_.+d_.*x_)^n_,x_Symbol] :=
  x^(m-1)*(a+b*x)^(n+1)*(c+d*x)^(n+1)/(2*b*d*(n+1)) +
  Dist[1/(b*d),Int[x^(m-2)*(a+b*x)^(n+1)*(c+d*x)^(n+1),x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && RationalQ[m] && m+2*n+1==0 && m>1 && ZeroQ[a*d+b*c]


Int[x_^m_*(a_.+b_.*x_)^n_*(c_.+d_.*x_)^n_,x_Symbol] :=
  x^(m-1)*(a+b*x)^(n+1)*(c+d*x)^(n+1)/(2*b*d*(n+1)) -
  Dist[(a*d+b*c)/(2*b*d),Int[x^(m-1)*(a+b*x)^n*(c+d*x)^n,x]] +
  Dist[1/(b*d),Int[x^(m-2)*(a+b*x)^(n+1)*(c+d*x)^(n+1),x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && RationalQ[m] && m+2*n+1==0 && m>1 && NonzeroQ[a*d+b*c]


(* ::Item:: *)
(*Reference: G&R 2.174.1, CRC 119*)


Int[x_*(a_.+b_.*x_)^n_*(c_.+d_.*x_)^n_,x_Symbol] :=
  (a+b*x)^(n+1)*(c+d*x)^(n+1)/(2*b*d*(n+1)) /;
FreeQ[{a,b,c,d,n},x] && NonzeroQ[n+1] && ZeroQ[a*d+b*c]


Int[x_*(a_.+b_.*x_)^n_*(c_.+d_.*x_)^n_,x_Symbol] :=
  (a+b*x)^(n+1)*(c+d*x)^(n+1)/(2*b*d*(n+1)) -
  Dist[(a*d+b*c)/(2*b*d),Int[(a+b*x)^n*(c+d*x)^n,x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n]


Int[x_^m_*(a_.+b_.*x_)^n_*(c_.+d_.*x_)^n_,x_Symbol] :=
  x^(m-1)*(a+b*x)^(n+1)*(c+d*x)^(n+1)/(b*d*(m+2*n+1)) -
  Dist[a*c*(m-1)/(b*d*(m+2*n+1)),Int[x^(m-2)*(a+b*x)^n*(c+d*x)^n,x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && RationalQ[m] && NonzeroQ[m+2*n+1] && m>1 && (ZeroQ[m+n] || ZeroQ[a*d+b*c])


Int[x_^m_*(a_.+b_.*x_)^n_*(c_.+d_.*x_)^n_,x_Symbol] :=
  x^(m-1)*(a+b*x)^(n+1)*(c+d*x)^(n+1)/(b*d*(m+2*n+1)) -
  Dist[(m+n)*(a*d+b*c)/(b*d*(m+2*n+1)),Int[x^(m-1)*(a+b*x)^n*(c+d*x)^n,x]] -
  Dist[a*c*(m-1)/(b*d*(m+2*n+1)),Int[x^(m-2)*(a+b*x)^n*(c+d*x)^n,x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && RationalQ[m] && NonzeroQ[m+2*n+1] && m>1


(* ::Item:: *)
(*Reference: G&R 2.176, CRC 123*)


Int[x_^m_*(a_+b_.*x_)^n_*(c_+d_.*x_)^n_,x_Symbol] :=
  x^(m+1)*(a+b*x)^(n+1)*(c+d*x)^(n+1)/(a*c*(m+1)) /;
FreeQ[{a,b,c,d,n},x] && NonzeroQ[m+1] && ZeroQ[m+2*n+3] && ZeroQ[a*d+b*c]


Int[x_^m_*(a_+b_.*x_)^n_*(c_+d_.*x_)^n_,x_Symbol] :=
  x^(m+1)*(a+b*x)^(n+1)*(c+d*x)^(n+1)/(a*c*(m+1)) -
  Dist[(m+n+2)/(m+1)*((a*d+b*c)/(a*c)),Int[x^(m+1)*(a+b*x)^n*(c+d*x)^n,x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && RationalQ[m] && m<-1 && ZeroQ[m+2*n+3]


Int[x_^m_*(a_+b_.*x_)^n_*(c_+d_.*x_)^n_,x_Symbol] :=
  x^(m+1)*(a+b*x)^(n+1)*(c+d*x)^(n+1)/(a*c*(m+1)) -
  Dist[(m+2*n+3)/(m+1)*(b*d/(a*c)),Int[x^(m+2)*(a+b*x)^n*(c+d*x)^n,x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && RationalQ[m] && m<-1 && (ZeroQ[m+n+2] || ZeroQ[a*d+b*c])


Int[x_^m_*(a_+b_.*x_)^n_*(c_+d_.*x_)^n_,x_Symbol] :=
  x^(m+1)*(a+b*x)^(n+1)*(c+d*x)^(n+1)/(a*c*(m+1)) -
  Dist[(m+n+2)/(m+1)*((a*d+b*c)/(a*c)),Int[x^(m+1)*(a+b*x)^n*(c+d*x)^n,x]] -
  Dist[(m+2*n+3)/(m+1)*(b*d/(a*c)),Int[x^(m+2)*(a+b*x)^n*(c+d*x)^n,x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && RationalQ[m] && m<-1


(* ::Subsubsection::Closed:: *)
(*x^m (a+b x)^n (c+d x)^p	Products of monomials and different powers of two linears*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: (a+b*x)^n/x == b*(a+b*x)^(n-1) + a*(a+b*x)^(n-1)/x*)


Int[(a_+b_.*x_)^n_*(c_+d_.*x_)^p_/x_,x_Symbol] :=
  Dist[b,Int[(a+b*x)^(n-1)*(c+d*x)^p,x]] +
  Dist[a,Int[(a+b*x)^(n-1)*(c+d*x)^p/x,x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[{n,p}] && n>0 && IntegerQ[n-p]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: (a+b*x)^n/x == (a+b*x)^(n+1)/(a*x) - b/a*(a+b*x)^n*)


Int[(a_+b_.*x_)^n_*(c_+d_.*x_)^p_/x_,x_Symbol] :=
  Dist[1/a,Int[(a+b*x)^(n+1)*(c+d*x)^p/x,x]] -
  Dist[b/a,Int[(a+b*x)^n*(c+d*x)^p,x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[{n,p}] && n<-1 && IntegerQ[n-p]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: x^m*(a+b*x)^n == x^(m-1)*(a+b*x)^(n+1)/b - a*x^(m-1)*(a+b*x)^n/b*)


(* ::Item:: *)
(*Basis: If m>=0 is an integer, x^m == 1/b^m*Sum[(-a)^(m-k)*Binomial[m,m-k]*(a+b*x)^k, {k,0,m}]*)


Int[x_^m_.*(a_+b_.*x_)^n_*(c_.+d_.*x_)^p_.,x_Symbol] :=
  Sum[Dist[(-a)^(m-k)/b^m*Binomial[m,m-k],Int[(a+b*x)^(n+k)*(c+d*x)^p,x]],{k,0,m}] /;
FreeQ[{a,b,c,d,n,p},x] && IntegerQ[{m,p-n}] && m>0 && Not[IntegerQ[n]] && p-n<0 &&
(m>3 || n=!=-1/2)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: x^m*(a+b*x)^n == x^(m-1)*(a+b*x)^(n+1)/b - a*x^(m-1)*(a+b*x)^n/b*)


(* ::Item:: *)
(*Basis: If m and p-n are integers and 0<p-n<=m, x^m*(a+b*x)^n*(c+d*x)^p == *)
(*	1/b^m*Sum[(-a)^(m-k)*Binomial[m,m-k]*(a+b*x)^(n+k)*(c+d*x)^p, {k,0,p-n-1}] + *)
(*	1/(-a)^(p-n)*Sum[(-a/b)^(m-k)*Binomial[m-k-1,p-n-1]*x^k*(a+b*x)^p*(c+d*x)^p, {k,0,m-p+n}]*)


Int[x_^m_.*(a_+b_.*x_)^n_*(c_.+d_.*x_)^p_,x_Symbol] :=
  Sum[Dist[(-a)^(m-k)/b^m*Binomial[m,m-k],Int[(a+b*x)^(n+k)*(c+d*x)^p,x]], {k,0,p-n-1}] + 
  Sum[Dist[(-a/b)^(m-k)/(-a)^(p-n)*Binomial[m-k-1,p-n-1],Int[x^k*(a+b*x)^p*(c+d*x)^p,x]], {k,0,m-p+n}] /;
FreeQ[{a,b,c,d,n,p},x] && IntegerQ[{m,p-n}] && 0<p-n<=m && Not[IntegerQ[n]]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: x^m*(a+b*x)^n == x^m*(a+b*x)^(n+1)/a - b*x^(m+1)*(a+b*x)^n/a*)


(* ::Item:: *)
(*Basis: If m>0 and p-n>0 are integers, (a+b*x)^n*(c+d*x)^p/x^m == *)
(*	(-b)^m*Sum[1/a^(m+k)*Binomial[k+m-1,m-1]*(a+b*x)^(n+k)*(c+d*x)^p, {k,0,p-n-1}] + *)
(*	1/a^(p-n)*Sum[(-b/a)^k*Binomial[p-n+k-1,p-n-1]*(a+b*x)^p*(c+d*x)^p/x^(m-k), {k,0,m-1}]*)


Int[x_^m_.*(a_+b_.*x_)^n_*(c_.+d_.*x_)^p_,x_Symbol] :=
  Sum[Dist[a^(m-k)/(-b)^m*Binomial[k-m-1,-m-1],Int[(a+b*x)^(n+k)*(c+d*x)^p,x]], {k,0,p-n-1}] + 
  Sum[Dist[(-b/a)^k/a^(p-n)*Binomial[p-n+k-1,p-n-1],Int[x^(m+k)*(a+b*x)^p*(c+d*x)^p,x]], {k,0,-m-1}] /;
FreeQ[{a,b,c,d,n,p},x] && IntegerQ[{m,p-n}] && m<0 && p-n>0 && Not[IntegerQ[n]]


(* ::Subsubsection::Closed:: *)
(*x^m (e (a+b x)^p+f (c+d x)^q)^n	Products of expn and powers of sums of square-roots of linears*)


(* ::Item:: *)
(*Basis: r^p*(v*r^q + w) == v*r^(p+q) + w*r^p*)


(* Int[u_.*r_^p_*(v_.*r_^q_+w_),x_Symbol] :=
  Int[u*v*r^(p+q),x] + Int[u*w*r^p,x] /;
HalfIntegerQ[{p,q}] *)


(* Int[u_.*(a_*r_^p_+s_.)*(b_*r_^q_+t_.),x_Symbol] :=
  Dist[a*b,Int[u*r^(p+q),x]] + Int[u*(a*t*r^p+b*s*r^q+s*t),x] /;
FreeQ[{a,b},x] && HalfIntegerQ[{p,q}] *)


(* Int[(e_.*(a_.+b_.*x_)^p_+f_.*(c_.+d_.*x_)^q_)^n_,x_Symbol] :=
  Int[Expand[(e*(a+b*x)^p+f*(c+d*x)^q)^n],x] /;
FreeQ[{a,b,c,d,e,f},x] && IntegerQ[n] && n>0 && HalfIntegerQ[{p,q}] *)


(* Int[x_^m_.*(e_.*(a_.+b_.*x_)^p_+f_.*(c_.+d_.*x_)^q_)^n_,x_Symbol] :=
  Int[Expand[x^m*(e*(a+b*x)^p+f*(c+d*x)^q)^n],x] /;
FreeQ[{a,b,c,d,e,f,m},x] && IntegerQ[n] && n>0 && HalfIntegerQ[{p,q}] *)


(* ::Subsection::Closed:: *)
(*(a + b x^n)^p			Powers of binomials*)


(* ::Subsubsection::Closed:: *)
(*1 / (a+b x^n)				Reciprocals of binomials*)


Int[1/(a_+b_.*(x_^m_)^n_),x_Symbol] :=
  Rt[b/a,2]*x*ArcTan[Rt[b/a,2]*(x^m)^(1/m)]/(b*(x^m)^(1/m)) /;
FreeQ[{a,b,m,n},x] && m*n===2 && PosQ[a/b]


Int[1/(a_+b_.*(x_^m_)^n_),x_Symbol] :=
  -Rt[-b/a,2]*x*ArcTanh[Rt[-b/a,2]*(x^m)^(1/m)]/(b*(x^m)^(1/m)) /;
FreeQ[{a,b,m,n},x] && m*n===2 && NegQ[a/b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: 1/(a+b*z^n) == 1/a - b/(a*(b+a/z^n))*)


Int[1/(a_+b_.*x_^n_),x_Symbol] :=
  x/a - Dist[b/a,Int[1/(b+a*x^(-n)),x]] /;
FreeQ[{a,b},x] && FractionQ[n] && n<0


(* ::Subsubsection::Closed:: *)
(*1 / Sqrt[a+b x^n]			Reciprocals of square-root of binomials*)


(* ::Item::Closed:: *)
(*Reference: CRC 278*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: ArcSinh'[z] == 1/Sqrt[1+z^2]*)


Int[1/Sqrt[a_+b_.*x_^2],x_Symbol] :=
  ArcSinh[Rt[b,2]*x/Sqrt[a]]/Rt[b,2] /;
FreeQ[{a,b},x] && PositiveQ[a] && PosQ[b]


(* ::Item::Closed:: *)
(*Reference: CRC 279, A&S 3.3.44*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: ArcSin'[z] == 1/Sqrt[1-z^2]*)


Int[1/Sqrt[a_+b_.*x_^2],x_Symbol] :=
  ArcSin[Rt[-b,2]*x/Sqrt[a]]/Rt[-b,2] /;
FreeQ[{a,b},x] && PositiveQ[a] && NegQ[b]


(* ::Item:: *)
(*Reference: CRC 278'*)


Int[1/Sqrt[a_+b_.*x_^2],x_Symbol] :=
  ArcTanh[Sqrt[a+b*x^2]/(Rt[b,2]*x)]/Rt[b,2] /;
FreeQ[{a,b},x] && Not[PositiveQ[a]] && PosQ[b]


(* ::Item:: *)
(*Reference: CRC 279'*)


Int[1/Sqrt[a_+b_.*x_^2],x_Symbol] :=
  -ArcTan[Sqrt[a+b*x^2]/(Rt[-b,2]*x)]/Rt[-b,2] /;
FreeQ[{a,b},x] && Not[PositiveQ[a]] && NegQ[b]


Int[1/Sqrt[a_+b_.*x_^4],x_Symbol] :=
  EllipticF[ArcSin[Rt[-b/a,4]*x],-1]/(Rt[-b/a,4]*Sqrt[a]) /;
FreeQ[{a,b},x] && PositiveQ[a]


Int[1/Sqrt[a_+b_.*x_^4],x_Symbol] :=
  Sqrt[(a+b*x^4)/a]*EllipticF[ArcSin[Rt[-b/a,4]*x],-1]/(Rt[-b/a,4]*Sqrt[a+b*x^4]) /;
FreeQ[{a,b},x]


(* ::Subsubsection::Closed:: *)
(*(a+b x^n)^p				Powers of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Sqrt[a+b/z^2] == -Sqrt[a+b*(1/z)^2]/(1/z)^2*D[1/z,z]*)


Int[Sqrt[a_.+b_./x_^2],x_Symbol] :=
  -Subst[Int[Sqrt[a+b*x^2]/x^2,x],x,1/x] /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Reference: G&R 2.110.2', CRC 88d'*)


Int[(a_+b_.*x_^n_)^p_,x_Symbol] :=
  x*(a+b*x^n)^(p+1)/a /;
FreeQ[{a,b,n,p},x] && ZeroQ[n*(p+1)+1]


(* ::Item:: *)
(*Reference: G&R 2.110.1, CRC 88b*)


Int[(a_+b_.*x_^n_)^p_,x_Symbol] :=
  x*(a+b*x^n)^p/(n*p+1) +
  Dist[n*p*a/(n*p+1),Int[(a+b*x^n)^(p-1),x]] /;
FreeQ[{a,b,n},x] && FractionQ[p] && p>0 && NonzeroQ[n*p+1]


(* ::Item:: *)
(*Reference: G&R 2.110.2, CRC 88d*)


Int[(a_+b_.*x_^n_)^p_,x_Symbol] :=
  -x*(a+b*x^n)^(p+1)/(n*(p+1)*a) +
  Dist[(n*(p+1)+1)/(n*(p+1)*a),Int[(a+b*x^n)^(p+1),x]] /;
FreeQ[{a,b,n},x] && FractionQ[p] && p<-1


(* ::Item:: *)
(*Reference: G&R 2.110.6, CRC 88c*)


Int[(a_+b_./x_)^p_,x_Symbol] :=
  x*(a+b/x)^(p+1)/a +
  Dist[b*p/a,Int[(a+b/x)^p/x,x]] /;
FreeQ[{a,b,p},x] && Not[IntegerQ[p]]
(* Transforms fractional power p of binomial into an integer *)


Int[(a_+b_.*x_^n_)^p_,x_Symbol] :=
  Module[{q=Denominator[p]},
  Dist[q*a^(p+1/n)/n,
	Subst[Int[x^(q/n-1)/(1-b*x^q)^(p+1/n+1),x],x,x^(n/q)/(a+b*x^n)^(1/q)]]] /;
FreeQ[{a,b},x] && RationalQ[{p,n}] && -1<p<0 && IntegerQ[p+1/n]


(* ::Subsection::Closed:: *)
(*x^m (a + b x^n)^p		Products of monomials and powers of binomials*)


(* ::Subsubsection::Closed:: *)
(*x^m (a+b x^n)^p			Products of monomials and powers of binomials where n/(m+1)>1 is integer*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n/(m+1) is an integer, x^n == (x^(m+1))^(n/(m+1))*)


Int[x_^m_.*(a_.+b_.*x_^n_)^p_.,x_Symbol] :=
  Dist[1/(m+1),Subst[Int[(a+b*x^(n/(m+1)))^p,x],x,x^(m+1)]] /;
FreeQ[{a,b,m,n,p},x] && NonzeroQ[m+1] && IntegerQ[n/(m+1)] && n/(m+1)>1 && Not[IntegerQ[{m,n,p}]]


(* ::Subsubsection::Closed:: *)
(*x^m (a+b x^n)^p			Products of monomials and powers of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If p is an integer, (a+b*x^n)^p == x^(n*p)*(b+a/x^n)^p*)


Int[x_^m_.*(a_+b_.*x_^n_)^p_,x_Symbol] :=
  Int[x^(m+n*p)*(b+a/x^n)^p,x] /;
FreeQ[{a,b,m},x] && FractionQ[n] && n<0 && IntegerQ[p] && p<0


(* ::Item::Closed:: *)
(*Reference: G&R 2.110.3*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*(a_+b_.*x_^n_)^p_,x_Symbol] :=
  x^(m+1)*(a+b*x^n)^p/(m+1) -
  Dist[b*n*p/(m+1),Int[x^(m+n)*(a+b*x^n)^(p-1),x]] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && FractionQ[p] && p>0 && (n>0 && m<-1 || 0<-n<=m+1)


(* ::Item::Closed:: *)
(*Reference: G&R 2.110.4*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Basis: x^m*(a+b*x^n)^p == x^(m-n+1)*((a+b*x^n)^p*x^(n-1))*)


Int[x_^m_.*(a_+b_.*x_^n_)^p_,x_Symbol] :=
  x^(m-n+1)*(a+b*x^n)^(p+1)/(b*n*(p+1)) -
  Dist[(m-n+1)/(b*n*(p+1)),Int[x^(m-n)*(a+b*x^n)^(p+1),x]] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && FractionQ[p] && (0<n<=m || m<=n<0) && p<-1 && NonzeroQ[m-n+1]


(* ::Item:: *)
(*Reference: G&R 2.110.1, CRC 88b*)


Int[x_^m_.*(a_+b_.*x_^n_)^p_,x_Symbol] :=
  x^(m+1)*(a+b*x^n)^p/(m+n*p+1) +
  Dist[n*p*a/(m+n*p+1),Int[x^m*(a+b*x^n)^(p-1),x]] /;
FreeQ[{a,b,m,n,p},x] && NonzeroQ[m+n*p+1] && FractionQ[p] && p>0 &&
Not[IntegerQ[(m+1)/n] && (m+1)/n>0]


(* ::Item:: *)
(*Reference: G&R 2.110.2, CRC 88d*)


Int[x_^m_.*(a_+b_.*x_^n_)^p_,x_Symbol] :=
  -x^(m+1)*(a+b*x^n)^(p+1)/(a*n*(p+1)) +
  Dist[(m+n*(p+1)+1)/(a*n*(p+1)),Int[x^m*(a+b*x^n)^(p+1),x]] /;
FreeQ[{a,b,m,n},x] && FractionQ[p] && p<-1 && NonzeroQ[m+n*(p+1)+1] && NonzeroQ[m-n+1]


(* ::Item:: *)
(*Reference: G&R 2.110.5, CRC 88a*)


Int[x_^m_.*(a_+b_.*x_^n_.)^p_,x_Symbol] :=
  x^(m-n+1)*(a+b*x^n)^(p+1)/(b*(m+n*p+1)) -
  Dist[a*(m-n+1)/(b*(m+n*p+1)),Int[x^(m-n)*(a+b*x^n)^p,x]] /;
FreeQ[{a,b,m,n,p},x] && NonzeroQ[m+n*p+1] && NonzeroQ[m-n+1] && NonzeroQ[m+1] &&
Not[IntegerQ[{m,n,p}]] && 
	(IntegerQ[{m,n}] && (0<n<=m || m<=n<0) && (Not[RationalQ[p]] || -1<p<0) || 
 	IntegerQ[(m+1)/n] && 0<(m+1)/n && Not[FractionQ[n]] || 
     Not[RationalQ[m]] && RationalQ[m-n] || 
     RationalQ[n] && MatchQ[m,u_+q_ /; RationalQ[q] && (0<n<=q || n<0 && q<0)] || 
     MatchQ[m,u_+q_.*n /; RationalQ[q] && q>=1])


(* ::Item:: *)
(*Reference: G&R 2.110.6, CRC 88c*)


Int[x_^m_.*(a_+b_.*x_^n_.)^p_,x_Symbol] :=
  x^(m+1)*(a+b*x^n)^(p+1)/(a*(m+1)) -
  Dist[b*(m+n*(p+1)+1)/(a*(m+1)),Int[x^(m+n)*(a+b*x^n)^p,x]] /;
FreeQ[{a,b,m,n,p},x] && NonzeroQ[m+1] && (* NonzeroQ[m+n*(p+1)+1] && *) Not[IntegerQ[{m,n,p}]] && 
	(IntegerQ[{m,n}] && (n>0 && m<-1 || 0<-n<=m+1) ||
     Not[RationalQ[m]] && RationalQ[m+n] ||
     RationalQ[n] && MatchQ[m,u_+q_ /; RationalQ[q] && (n>0 && q<0 || 0<-n<=q)] || 
     MatchQ[m,u_+q_*n /; RationalQ[q] && q<0]) 
(* Transforms fractional power p of binomial into an integer *)


Int[x_^m_.*(a_+b_.*x_^n_)^p_,x_Symbol] :=
  Module[{q=Denominator[p]},
  q*a^(p+(m+1)/n)/n*
	Subst[Int[x^(q*(m+1)/n-1)/(1-b*x^q)^(p+(m+1)/n+1),x],x,x^(n/q)/(a+b*x^n)^(1/q)]] /;
FreeQ[{a,b},x] && RationalQ[{m,n,p}] && -1<p<0 && IntegerQ[p+(m+1)/n] && GCD[m+1,n]==1


(* Necessary ??? *)
Int[u_.*x_^m_.*(v_.*x_^n_.+w_),x_Symbol] :=
  Int[u*v*x^(m+n),x] +
  Int[u*w*x^m,x] /;
FreeQ[{m,n},x] && Not[PolynomialQ[w,x]]


(* ::Subsubsection::Closed:: *)
(*1 / Sqrt[x^m (a+b x^n)]		Reciprocals of squareroots of products of monomials and binomials*)


Int[1/Sqrt[x_^m_.*(a_.+b_.*x_^n_.)],x_Symbol] :=
  2*ArcTanh[x*Rt[b,2]/Sqrt[x^m*(a+b*x^n)]]/(n*Rt[b,2]) /;
FreeQ[{a,b,n,m},x] && ZeroQ[m+n-2] && PosQ[b]


Int[1/Sqrt[x_^m_*(a_.+b_.*x_^n_.)],x_Symbol] :=
  2*ArcTan[x*Rt[-b,2]/Sqrt[x^m*(a+b*x^n)]]/(n*Rt[-b,2]) /;
FreeQ[{a,b,n,m},x] && ZeroQ[m+n-2] && NegQ[b]


(* ::Subsection::Closed:: *)
(*x^p (x^m (a + b x^n))^q	Products of monomials and powers of products*)


(* Note: Kool rules! *)


Int[(x_^m_.*(a_+b_.*x_^n_.))^p_,x_Symbol] :=
  (x^m*(a+b*x^n))^(p+1)/(b*n*(p+1)*x^(m*(p+1))) /;
FreeQ[{a,b,m,n,p},x] && ZeroQ[m*p-n+1] && NonzeroQ[p+1]


Int[x_^q_.*(x_^m_.*(a_+b_.*x_^n_.))^p_,x_Symbol] :=
  (x^m*(a+b*x^n))^(p+1)/(b*n*(p+1)*x^(m*(p+1))) /;
FreeQ[{a,b,m,n,p},x] && ZeroQ[q+m*p-n+1] && NonzeroQ[p+1]


Int[(x_^m_.*(a_+b_.*x_^n_.))^p_.,x_Symbol] :=
  -(x^m*(a+b*x^n))^(p+1)/(a*n*(p+1)*x^(m-1)) /;
FreeQ[{a,b,m,n,p},x] && ZeroQ[m*p+n*(p+1)+1] && NonzeroQ[p+1]


Int[x_^q_.*(x_^m_.*(a_+b_.*x_^n_.))^p_.,x_Symbol] :=
  -(x^m*(a+b*x^n))^(p+1)/(a*n*(p+1)*x^(m-1-q)) /;
FreeQ[{a,b,m,n,p,q},x] && ZeroQ[1+q+m*p+n*(p+1)] && NonzeroQ[p+1]


(* ::Subsection::Closed:: *)
(*(a + b x^n)^m (c + d x^p)^q	Products of powers of binomials*)


(* ::Subsubsection::Closed:: *)
(*(a+b x^n)^p (c+d x^n)^q		Products of powers of binomials*)


Int[1/((a_.+b_.*x_^2)*Sqrt[c_+d_.*x_^2]),x_Symbol] :=
  x/Sqrt[c+d*x^2] /;
FreeQ[{a,b,c,d},x] && ZeroQ[a*d-b*c]


(* ::Item:: *)
(*Reference: CRC 232, A&S 3.3.50'*)


Int[1/((a_+b_.*x_^2)*Sqrt[c_+d_.*x_^2]),x_Symbol] :=
  ArcTanh[x*Rt[(a*d-b*c)/a,2]/Sqrt[c+d*x^2]]/(a*Rt[(a*d-b*c)/a,2]) /;
FreeQ[{a,b,c,d},x] && PosQ[(a*d-b*c)/a]


(* ::Item:: *)
(*Reference: CRC 233, A&S 3.3.49*)


Int[1/((a_+b_.*x_^2)*Sqrt[c_+d_.*x_^2]),x_Symbol] :=
  ArcTan[x*Rt[(b*c-a*d)/a,2]/Sqrt[c+d*x^2]]/(a*Rt[(b*c-a*d)/a,2]) /;
FreeQ[{a,b,c,d},x] && NegQ[(a*d-b*c)/a]


Int[1/(Sqrt[a_+b_.*x_^2]*Sqrt[c_+d_.*x_^2]),x_Symbol] :=
  EllipticF[ArcSin[Rt[-d/c,2]*x], b*c/(a*d)]/(Rt[-d/c,2]*Sqrt[a*c]) /;
FreeQ[{a,b,c,d},x] && PositiveQ[a] && PositiveQ[c] && 
(PositiveQ[-d/c] && NonzeroQ[a+b] || Not[PositiveQ[-b/a] && NonzeroQ[c+d]])


Int[1/(Sqrt[a_+b_.*x_^2]*Sqrt[c_+d_.*x_^2]),x_Symbol] :=
  Sqrt[(a+b*x^2)/a]*Sqrt[(c+d*x^2)/c]*EllipticF[ArcSin[Rt[-d/c,2]*x], b*c/(a*d)]/
    (Rt[-d/c,2]*Sqrt[a+b*x^2]*Sqrt[c+d*x^2]) /;
FreeQ[{a,b,c,d},x] && 
(PositiveQ[-d/c] && NonzeroQ[a+b] || Not[PositiveQ[-b/a] && NonzeroQ[c+d]])


Int[Sqrt[a_+b_.*x_^2]/Sqrt[c_+d_.*x_^2],x_Symbol] :=
  Sqrt[a/c]*EllipticE[ArcSin[Rt[-d/c,2]*x], b*c/(a*d)]/Rt[-d/c,2] /;
FreeQ[{a,b,c,d},x] && PositiveQ[a] && PositiveQ[c]


Int[Sqrt[a_+b_.*x_^2]/Sqrt[c_+d_.*x_^2],x_Symbol] :=
  Sqrt[a+b*x^2]*Sqrt[(c+d*x^2)/c]*EllipticE[ArcSin[Rt[-d/c,2]*x], b*c/(a*d)]/
    (Rt[-d/c,2]*Sqrt[(a+b*x^2)/a]*Sqrt[c+d*x^2]) /;
FreeQ[{a,b,c,d},x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: (a+b*z^n)/(c+d*z^n) == b/d + (a*d-b*c)/(d*(c+d*z^n))*)


Int[(a_.+b_.*x_^n_)^p_*(c_.+d_.*x_^n_)^q_.,x_Symbol] :=
  Dist[b/d,Int[(a+b*x^n)^(p-1)*(c+d*x^n)^(q+1),x]] /;
FreeQ[{a,b,c,d,n},x] && RationalQ[{p,q}] && p>0 && q<=-1 && ZeroQ[a*d-b*c]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: (a+b*z^n)/(c+d*z^n) == b/d + (a*d-b*c)/(d*(c+d*z^n))*)


Int[(a_.+b_.*x_^n_)^p_*(c_.+d_.*x_^n_)^q_.,x_Symbol] :=
  Dist[(a*d-b*c)/d,Int[(a+b*x^n)^(p-1)*(c+d*x^n)^q,x]] +
  Dist[b/d,Int[(a+b*x^n)^(p-1)*(c+d*x^n)^(q+1),x]] /;
FreeQ[{a,b,c,d,n},x] && RationalQ[{p,q}] && p>0 && q<=-1 && NonzeroQ[a*d-b*c] && 
IntegerQ[n] && n>0


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: 1/((a+b*z^n)*(c+d*z^n)) == b/((b*c-a*d)*(a+b*z^n)) - d/((b*c-a*d)*(c+d*z^n))*)


Int[(a_.+b_.*x_^n_)^p_.*(c_.+d_.*x_^n_)^q_.,x_Symbol] :=
  Dist[b/(b*c-a*d),Int[(a+b*x^n)^p*(c+d*x^n)^(q+1),x]] -
  Dist[d/(b*c-a*d),Int[(a+b*x^n)^(p+1)*(c+d*x^n)^q,x]] /;
FreeQ[{a,b,c,d,n},x] && RationalQ[{p,q}] && p<-1 && q<=-1 && NonzeroQ[b*c-a*d] && 
IntegerQ[n] && n>0


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If b*c-a*d==0, (a+b*z^n)*(c+d*z^n) == d/b*(a+b*z^n)^2*)


(* Int[u_.*(a_+b_.*x_^n_.)^p_*(c_+d_.*x_^n_.)^q_,x_Symbol] :=
  Dist[(d/b)^q,Int[u*(a+b*x^n)^(p+q),x]] /;
FreeQ[{a,b,c,d,n,p},x] && IntegerQ[q] && ZeroQ[b*c-a*d] *)


(* ::Subsubsection::Closed:: *)
(*x^m (a+b x^2)^n (c+d x^2)^p		Products of monomials and powers of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: z/Sqrt[a+b*z] == Sqrt[a+b*z]/b - a/(b*Sqrt[a+b*z])*)


Int[x_^2/(Sqrt[a_+b_.*x_^2]*Sqrt[c_+d_.*x_^2]),x_Symbol] :=
  Dist[1/b,Int[Sqrt[a+b*x^2]/Sqrt[c+d*x^2],x]] -
  Dist[a/b,Int[1/(Sqrt[a+b*x^2]*Sqrt[c+d*x^2]),x]] /;
FreeQ[{a,b,c,d},x]


Int[x_^2*Sqrt[a_+b_.*x_^2]/Sqrt[c_+d_.*x_^2],x_Symbol] :=
  x*Sqrt[a+b*x^2]*Sqrt[c+d*x^2]/(3*d) -
  Dist[1/(3*d),Int[(a*c+(2*b*c-a*d)*x^2)/(Sqrt[a+b*x^2]*Sqrt[c+d*x^2]),x]] /;
FreeQ[{a,b,c,d},x]


(* ::Subsubsection::Closed:: *)
(*x^m (a+b x^n) / (c+d x^p)		Products of monomials and quotients of binomials*)


Int[(a_.+b_.*x_^n_.)/(x_*(c_+d_.*x_^p_.)),x_Symbol] :=
  a*Log[x]/c+
  Dist[1/c,Int[x^(n-1)*(b*c-a*d*x^(p-n))/(c+d*x^p),x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[{n,p}] && 0<n<=p


Int[(a_.+b_.*x_^n_.)/(x_*(c_+d_.*x_^p_.)),x_Symbol] :=
  a*Log[x]/c+
  Dist[1/c,Int[x^(p-1)*(-a*d+b*c*x^(n-p))/(c+d*x^p),x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[{n,p}] && 0<p<n


Int[x_^m_*(a_.+b_.*x_^n_.)/(c_+d_.*x_^p_.),x_Symbol] :=
  a*x^(m+1)/(c*(m+1))+
  Dist[1/c,Int[x^(m+n)*(b*c-a*d*x^(p-n))/(c+d*x^p),x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[{m,n,p}] && m<-1 && 0<n<=p


Int[x_^m_*(a_.+b_.*x_^n_.)/(c_+d_.*x_^p_.),x_Symbol] :=
  a*x^(m+1)/(c*(m+1))+
  Dist[1/c,Int[x^(m+p)*(-a*d+b*c*x^(n-p))/(c+d*x^p),x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[{m,n,p}] && m<-1 && 0<p<=n


(* ::Subsection::Closed:: *)
(*a + b x + c x^2		Integrands involving quadratic trinomials*)


(* ::Subsubsection::Closed:: *)
(*u (a + b x + c x^2)^n			Powers of quadratic trinomials where b^2-4ac is zero*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: b^2/(4*c)+b*x+c*x^2 == (b/2+c*x)^2/c*)


Int[(a_+b_.*x_+c_.*x_^2)^n_,x_Symbol] :=
  Int[Regularize[((b/2+c*x)^2/c)^n,x],x] /;
FreeQ[{a,b,c,n},x] && Not[IntegerQ[n]] && ZeroQ[b^2-4*a*c]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: b^2/(4*c)+b*x+c*x^2 == (b/2+c*x)^2/c*)


Int[u_*(a_+b_.*x_+c_.*x_^2)^n_,x_Symbol] :=
  Int[Regularize[u*((b/2+c*x)^2/c)^n,x],x] /;
FreeQ[{a,b,c,n},x] && Not[IntegerQ[n]] && ZeroQ[b^2-4*a*c] && (MonomialQ[u,x] || LinearQ[u,x])


(* ::Subsubsection::Closed:: *)
(*1 / Sqrt[a+b x+c x^2]			Reciprocals of square-roots of quadratic trinomials*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.261.2', CRC 237b', A&S 3.3.34'*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: D[ArcSinh[x],x] == 1/Sqrt[1+x^2]*)
(* Note: Unlike in the references, this formulation of the rule is valid even if not c>0. *)


Int[1/Sqrt[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  ArcSinh[(b+2*c*x)/(Sqrt[4*a-b^2/c]*Rt[c,2])]/Rt[c,2] /;
FreeQ[{a,b,c},x] && PositiveQ[4*a-b^2/c] && PosQ[c]


(* ::Item::Closed:: *)
(*Reference: G&R 2.261.3', CRC 238', A&S 3.3.36'*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: D[ArcSin[x],x] == 1/Sqrt[1-x^2] *)


(* Note: Unlike in the references,this formulation of the rule is valid even if not c>0. *)
Int[1/Sqrt[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  -ArcSin[(b+2*c*x)/(Sqrt[4*a-b^2/c]*Rt[-c,2])]/Rt[-c,2] /;
FreeQ[{a,b,c},x] && PositiveQ[4*a-b^2/c] && NegQ[c]


(* ::Item::Closed:: *)
(*Reference: G&R 2.261.1, CRC 237a, A&S 3.3.33*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


Int[1/Sqrt[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
(* ArcTanh[(b+2*c*x)/(2*Rt[c,2]*Sqrt[a+b*x+c*x^2])]/Rt[c,2] /; *)
  ArcTanh[(2*Rt[c,2]*Sqrt[a+b*x+c*x^2])/(b+2*c*x)]/Rt[c,2] /;
FreeQ[{a,b,c},x] && PosQ[c] && NonzeroQ[b^2-4*a*c]


(* ::Item::Closed:: *)
(*Reference: CRC 238'*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


Int[1/Sqrt[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
(* -ArcTan[(b+2*c*x)/(2*Rt[-c,2]*Sqrt[a+b*x+c*x^2])]/Rt[-c,2] /; *)
  ArcTan[(2*Rt[-c,2]*Sqrt[a+b*x+c*x^2])/(b+2*c*x)]/Rt[-c,2] /;
FreeQ[{a,b,c},x] && NegQ[c] && NonzeroQ[b^2-4*a*c]


(* ::Subsubsection::Closed:: *)
(*1 / ((d+e x) Sqrt[a+b x+c x^2])		Reciprocals of products of linears and square-roots of quadratic trinomials*)


(* ::Item:: *)
(*Reference: G&R 2.266.7, CRC 260*)
(* Note: Unnecessary because this is a special case of the rule for when m+2*(n+1) is zero! *)


(* Int[1/((d_.+e_.*x_)*Sqrt[a_.+c_.*x_^2]),x_Symbol] :=
  Module[{q=c*d^2+a*e^2},
(*  e*Sqrt[a+c*x^2]/(c*d*(d+e*x)) /; *)
  (-d+e*x)/(d*e*Sqrt[a+c*x^2]) /;
 ZeroQ[q]] /;
FreeQ[{a,c,d,e},x] *)


(* ::Item:: *)
(*Reference: G&R 2.266.1', CRC 258'*)


Int[1/((d_.+e_.*x_)*Sqrt[a_.+c_.*x_^2]),x_Symbol] :=
  Module[{q=c*d^2+a*e^2},
(* -ArcTanh[(a*e-c*d*x)/(Rt[q,2]*Sqrt[a+c*x^2])]/Rt[q,2] /; *)
  -ArcTanh[(Rt[q,2]*Sqrt[a+c*x^2])/(a*e-c*d*x)]/Rt[q,2] /;
 PosQ[q]] /;
FreeQ[{a,c,d,e},x]


(* ::Item:: *)
(*Reference: G&R 2.266.3, CRC 259*)


Int[1/((d_.+e_.*x_)*Sqrt[a_.+c_.*x_^2]),x_Symbol] :=
  Module[{q=c*d^2+a*e^2},
(* ArcTan[(a*e-c*d*x)/(Rt[-q,2]*Sqrt[a+c*x^2])]/Rt[-q,2] /; *)
  -ArcTan[(Rt[-q,2]*Sqrt[a+c*x^2])/(a*e-c*d*x)]/Rt[-q,2] /;
 NegQ[q]] /;
FreeQ[{a,c,d,e},x]


(* ::Item:: *)
(*Reference: G&R 2.266.7, CRC 260*)


Int[1/((d_.+e_.*x_)*Sqrt[a_.+b_.*x_+c_.*x_^2]),x_Symbol] :=
  -2*e*Sqrt[a+b*x+c*x^2]/((b*e-2*c*d)*(d+e*x)) /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[c*d^2-b*d*e+a*e^2]


(* ::Item:: *)
(*Reference: G&R 2.266.1', CRC 258'*)


Int[1/((d_.+e_.*x_)*Sqrt[a_.+b_.*x_+c_.*x_^2]),x_Symbol] :=
  Module[{q=c*d^2-b*d*e+a*e^2},
  -ArcTanh[(2*Rt[q,2]*Sqrt[a+b*x+c*x^2])/(2*a*e-b*d+(b*e-2*c*d)*x)]/Rt[q,2] /;
 PosQ[q]] /;
FreeQ[{a,b,c,d,e},x]


(* ::Item:: *)
(*Reference: G&R 2.266.3, CRC 259*)


Int[1/((d_.+e_.*x_)*Sqrt[a_.+b_.*x_+c_.*x_^2]),x_Symbol] :=
  Module[{q=c*d^2-b*d*e+a*e^2},
  -ArcTan[(2*Rt[-q,2]*Sqrt[a+b*x+c*x^2])/(2*a*e-b*d+(b*e-2*c*d)*x)]/Rt[-q,2] /;
 NegQ[q]] /;
FreeQ[{a,b,c,d,e},x]


(* ::Subsubsection::Closed:: *)
(*(a+b x+c x^2)^n			Powers of quadratic trinomials*)


(* ::Item:: *)
(*Reference: G&R 2.260.2, CRC 245, A&S 3.3.37*)


Int[(a_.+b_.*x_+c_.*x_^2)^n_,x_Symbol] :=
  (b+2*c*x)*(a+b*x+c*x^2)^n/(2*c*(2*n+1)) -
  Dist[n*(b^2-4*a*c)/(2*c*(2*n+1)),Int[(a+b*x+c*x^2)^(n-1),x]] /;
FreeQ[{a,b,c},x] && FractionQ[n] && n>0


(* ::Subsubsection::Closed:: *)
(*x^m / (a+b x+c x^2)			Quotients of monomials by quadratic trinomials*)


(* ::Item:: *)
(*Reference: G&R 2.174.1, CRC 119*)


Int[x_^m_/(a_.+b_.*x_+c_.*x_^2),x_Symbol] :=
  x^(m-1)/(c*(m-1))-
  Dist[1/c,Int[x^(m-2)*(a+b*x)/(a+b*x+c*x^2),x]] /;
FreeQ[{a,b,c},x] && FractionQ[m] && m>1


(* ::Item:: *)
(*Reference: G&R 2.265c*)


Int[x_^m_/(b_.*x_+c_.*x_^2),x_Symbol] :=
  x^m/(b*m)-
  Dist[c/b,Int[x^(m+1)/(b*x+c*x^2),x]] /;
FreeQ[{b,c},x] && FractionQ[m] && m<-1


(* ::Item:: *)
(*Reference: G&R 2.176, CRC 123*)


Int[x_^m_/(a_+b_.*x_+c_.*x_^2),x_Symbol] :=
  x^(m+1)/(a*(m+1))-
  Dist[1/a,Int[x^(m+1)*(b+c*x)/(a+b*x+c*x^2),x]] /;
FreeQ[{a,b,c},x] && FractionQ[m] && m<-1


(* ::Subsubsection::Closed:: *)
(*x^m (d+e x) / (a+b x+c x^2)		Products of monomials and quotients of linears by quadratic trinomials*)


Int[x_^m_.*(d_+e_.*x_)/(a_+b_.*x_+c_.*x_^2),x_Symbol] :=
  e*x^m/(c*m)-
  Dist[1/c,Int[x^(m-1)*(a*e+(b*e-c*d)*x)/(a+b*x+c*x^2),x]] /;
FreeQ[{a,b,c,d,e},x] && FractionQ[m] && m>0


Int[x_^m_.*(d_+e_.*x_)/(a_+b_.*x_+c_.*x_^2),x_Symbol] :=
  d*x^(m+1)/(a*(m+1))-
  Dist[1/a,Int[x^(m+1)*(b*d-a*e+c*d*x)/(a+b*x+c*x^2),x]] /;
FreeQ[{a,b,c,d,e},x] && FractionQ[m] && m<-1


(* ::Subsubsection::Closed:: *)
(*(a+b x+c x^2)^n / (d+e x)		Quotients of powers of quadratic trinomials by linears*)


(* ::Item:: *)
(*Reference: G&R 2.265b*)


Int[(a_.+b_.*x_+c_.*x_^2)^n_/(d_.+e_.*x_),x_Symbol] :=
  (a+b*x+c*x^2)^n/(2*e*n) + 
  Dist[(b*e-2*c*d)/(2*e^2), Int[(a+b*x+c*x^2)^(n-1),x]] /;
FreeQ[{a,b,c,d,e},x] && FractionQ[n] && n>0 && ZeroQ[c*d^2-b*d*e+a*e^2]


(* ::Item:: *)
(*Reference: G&R 2.265b*)


Int[(a_.+b_.*x_+c_.*x_^2)^n_/(d_.+e_.*x_),x_Symbol] :=
  (a+b*x+c*x^2)^n/(2*e*n) + 
  Dist[(c*d^2-b*d*e+a*e^2)/e^2, Int[(a+b*x+c*x^2)^(n-1)/(d+e*x),x]] /;
FreeQ[{a,b,c,d,e},x] && FractionQ[n] && n>0 && ZeroQ[b*e-2*c*d]


(* ::Item:: *)
(*Reference: G&R 2.265b*)


Int[(a_.+b_.*x_+c_.*x_^2)^n_/(d_.+e_.*x_),x_Symbol] :=
  (a+b*x+c*x^2)^n/(2*e*n) + 
  Dist[(b*e-2*c*d)/(2*e^2), Int[(a+b*x+c*x^2)^(n-1),x]] + 
  Dist[(c*d^2-b*d*e+a*e^2)/e^2, Int[(a+b*x+c*x^2)^(n-1)/(d+e*x),x]] /;
FreeQ[{a,b,c,d,e},x] && FractionQ[n] && n>0


(* ::Item:: *)
(*Reference: G&R 2.268b, CRC 122*)


Int[(a_.+b_.*x_+c_.*x_^2)^n_/(d_.+e_.*x_),x_Symbol] :=
  -e*(a+b*x+c*x^2)^(n+1)/(2*(n+1)*(c*d^2-b*d*e+a*e^2)) + 
  Dist[e^2/(c*d^2-b*d*e+a*e^2), Int[(a+b*x+c*x^2)^(n+1)/(d+e*x),x]] /;
FreeQ[{a,b,c,d,e},x] && FractionQ[n] && n<-1 && NonzeroQ[c*d^2-b*d*e+a*e^2] && ZeroQ[2*c*d-b*e]


(* ::Item:: *)
(*Reference: G&R 2.268b, CRC 122*)


Int[(a_.+b_.*x_+c_.*x_^2)^n_/(d_.+e_.*x_),x_Symbol] :=
  -e*(a+b*x+c*x^2)^(n+1)/(2*(n+1)*(c*d^2-b*d*e+a*e^2)) + 
  Dist[(2*c*d-b*e)/(2*(c*d^2-b*d*e+a*e^2)), Int[(a+b*x+c*x^2)^n,x]] + 
  Dist[e^2/(c*d^2-b*d*e+a*e^2), Int[(a+b*x+c*x^2)^(n+1)/(d+e*x),x]] /;
FreeQ[{a,b,c,d,e},x] && FractionQ[n] && n<-1 && NonzeroQ[c*d^2-b*d*e+a*e^2]


(* ::Subsection::Closed:: *)
(*a + b x^2 + c x^4		Integrands involving symmetric quartic trinomials*)


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If q=Sqrt[b^2-4*a*c], then a+b*x^2+c*x^4 == a*(1+2*c*x^2/(b-q))*(1+2*c*x^2/(b+q))*)


(* ::Item:: *)
(*Basis: If q=Sqrt[b^2-4*a*c], then D[Sqrt[1+2*c*x^2/(b-q)]*Sqrt[1+2*c*x^2/(b+q)]/Sqrt[a+b*x^2+c*x^4], x] == 0*)


Int[1/Sqrt[a_+b_.*x_^2+c_.*x_^4],x_Symbol] :=
  Module[{q=Rt[b^2-4*a*c,2]},
  Sqrt[1+2*c*x^2/(b-q)]*Sqrt[1+2*c*x^2/(b+q)]/Sqrt[a+b*x^2+c*x^4]*Int[1/(Sqrt[1+2*c*x^2/(b-q)]*Sqrt[1+2*c*x^2/(b+q)]),x]] /;
FreeQ[{a,b,c},x] && NonzeroQ[b^2-4*a*c]


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If q=Sqrt[b^2-4*a*c], then a+b*x^2+c*x^4 == a*(1+2*c*x^2/(b-q))*(1+2*c*x^2/(b+q))*)


(* ::Item:: *)
(*Basis: If q=Sqrt[b^2-4*a*c], then D[Sqrt[1+2*c*x^2/(b-q)]*Sqrt[1+2*c*x^2/(b+q)]/Sqrt[a+b*x^2+c*x^4], x] == 0*)


Int[x_^2/Sqrt[a_+b_.*x_^2+c_.*x_^4],x_Symbol] :=
  Module[{q=Rt[b^2-4*a*c,2]},
  Sqrt[1+2*c*x^2/(b-q)]*Sqrt[1+2*c*x^2/(b+q)]/Sqrt[a+b*x^2+c*x^4]*Int[x^2/(Sqrt[1+2*c*x^2/(b-q)]*Sqrt[1+2*c*x^2/(b+q)]),x]] /;
FreeQ[{a,b,c},x] && NonzeroQ[b^2-4*a*c]


(* ::Subsection::Closed:: *)
(*a + b x^k + c x^(2k)		Integrands involving symmetric trinomials*)


(* ::Subsubsection::Closed:: *)
(*(a + b x^2)/(c + d x^2 + e x^4)		Quotients of binomials by quartic trinomials*)


(* ::ItemParagraph:: *)
(*Previously undiscovered rules ??? *)


Int[(a_+b_.*x_^k_)/(c_+d_.*x_^2+e_.*x_^k_+f_.*x_^j_), x_Symbol] :=
  a/Rt[c*d,2]*ArcTan[(k-1)*a*Rt[c*d,2]*x/(c*((k-1)*a-b*x^k))] /;
FreeQ[{a,b,c,d,e,f,j,k},x] && Not[IntegerQ[k]] && j===2*k && ZeroQ[(k-1)^2*a^2*f-b^2*c] && ZeroQ[b*e+2*(k-1)*a*f] &&
PosQ[c*d]


Int[(a_+b_.*x_^k_)/(c_+d_.*x_^2+e_.*x_^k_+f_.*x_^j_), x_Symbol] :=
  a/Rt[-c*d,2]*ArcTanh[(k-1)*a*Rt[-c*d,2]*x/(c*((k-1)*a-b*x^k))] /;
FreeQ[{a,b,c,d,e,f,j,k},x] && Not[IntegerQ[k]] && j===2*k && ZeroQ[(k-1)^2*a^2*f-b^2*c] && ZeroQ[b*e+2*(k-1)*a*f] &&
NegQ[c*d]


Int[x_^m_.*(a_+b_.*x_^n_.)/(c_+d_.*x_^k_.+e_.*x_^n_.+f_.*x_^j_), x_Symbol] :=
  a*ArcTan[(m-n+1)*a*Rt[c*d,2]*x^(m+1)/(c*((m-n+1)*a+(m+1)*b*x^n))]/((m+1)*Rt[c*d,2]) /;
FreeQ[{a,b,c,d,e,f,j,k,m,n},x] && ZeroQ[k-2*(m+1)] && ZeroQ[j-2*n] &&
ZeroQ[(m-n+1)^2*a^2*f-(m+1)^2*b^2*c] && ZeroQ[(m+1)*b*e-2*(m-n+1)*a*f] && PosQ[c*d]


Int[x_^m_.*(a_+b_.*x_^n_.)/(c_+d_.*x_^k_.+e_.*x_^n_.+f_.*x_^j_), x_Symbol] :=
  a*ArcTanh[(m-n+1)*a*Rt[-c*d,2]*x^(m+1)/(c*((m-n+1)*a+(m+1)*b*x^n))]/((m+1)*Rt[-c*d,2]) /;
FreeQ[{a,b,c,d,e,f,j,k,m,n},x] && ZeroQ[k-2*(m+1)] && ZeroQ[j-2*n] &&
ZeroQ[(m-n+1)^2*a^2*f-(m+1)^2*b^2*c] && ZeroQ[(m+1)*b*e-2*(m-n+1)*a*f] && NegQ[c*d]


(* ::Subsubsection::Closed:: *)
(*(a+b x^k+c x^(2k))^n			Powers of symmetric trinomials*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.161.1a'*)


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: 1/(a+b*z+c*z^2) == 2*c/(q*(b-q+2*c*z)) - 2*c/(q*(b+q+2*c*z)) where q=Sqrt[b^2-4*a*c]*)


Int[1/(a_+b_.*x_^k_+c_.*x_^j_.),x_Symbol] :=
  Module[{q=Rt[b^2-4*a*c,2]},
  Dist[2*c/q,Int[1/(b-q+2*c*x^k),x]] -
  Dist[2*c/q,Int[1/(b+q+2*c*x^k),x]]] /;
FreeQ[{a,b,c,j,k},x] && Not[IntegerQ[k]] && j===2*k && NonzeroQ[b^2-4*a*c] && Not[NegativeQ[b^2-4*a*c]]


(* ::Item:: *)
(*Reference: G&R 2.161.1b?*)


Int[1/(a_+b_.*x_^k_+c_.*x_^j_),x_Symbol] :=
  Module[{q=2*Rt[a/c,2]-b/c},
  Dist[1/(2*c*Rt[a/c,2]*Rt[q,2]),Int[(Rt[q,2]+x^(k/2))/(Rt[a/c,2]+Rt[q,2]*x^(k/2)+x^k),x]] +
  Dist[1/(2*c*Rt[a/c,2]*Rt[q,2]),Int[(Rt[q,2]-x^(k/2))/(Rt[a/c,2]-Rt[q,2]*x^(k/2)+x^k),x]] /;
 PosQ[q]] /;  
FreeQ[{a,b,c},x] && EvenQ[k] && k>0 && j===2*k && PosQ[a/c] && NegativeQ[b^2-4*a*c]


(* ::Item:: *)
(*Reference: G&R 2.161.5' (GR5 2.161.4 is a special case.)*)
(* Previously undiscovered rule ??? *)


Int[(a_+b_.*x_^k_+c_.*x_^j_.)^n_,x_Symbol] :=
  -x*(b^2-2*a*c+b*c*x^k)*(a+b*x^k+c*x^j)^(n+1)/(k*a*(n+1)*(b^2-4*a*c)) +
  Dist[(k*(n+1)*(b^2-4*a*c)+b^2-2*a*c)/(k*a*(n+1)*(b^2-4*a*c)),Int[(a+b*x^k+c*x^j)^(n+1),x]] +
  Dist[(k*(2*n+3)+1)*b*c/(k*a*(n+1)*(b^2-4*a*c)),Int[x^k*(a+b*x^k+c*x^j)^(n+1),x]] /;
FreeQ[{a,b,c,j,k},x] && RationalQ[n] && Not[IntegerQ[k]] && j===2*k && n<-1 && NonzeroQ[b^2-4*a*c]


(* ::Subsubsection::Closed:: *)
(*1 / (x Sqrt[a+b x^k+c x^(2k)])		Reciprocals of products of x and square-roots of symmetric trinomials*)


(* ::Item:: *)
(*Reference: G&R 2.266.7, CRC 260*)


Int[1/(x_*Sqrt[b_.*x_^k_.+c_.*x_^j_.]),x_Symbol] :=
  -2*Sqrt[b*x^k+c*x^j]/(b*k*x^k) /;
FreeQ[{b,c,j,k},x] && j===2*k


(* ::Item:: *)
(*Reference: G&R 2.266.1', CRC 258'*)


Int[1/(x_*Sqrt[a_+b_.*x_^k_.+c_.*x_^j_.]),x_Symbol] :=
  -ArcTanh[(2*a+b*x^k)/(2*Rt[a,2]*Sqrt[a+b*x^k+c*x^j])]/(k*Rt[a,2]) /;
FreeQ[{a,b,c,j,k},x] && j===2*k && PosQ[a]


(* ::Item:: *)
(*Reference: G&R 2.266.3, CRC 259*)


Int[1/(x_*Sqrt[a_+b_.*x_^k_.+c_.*x_^j_.]),x_Symbol] :=
  ArcTan[(2*a+b*x^k)/(2*Rt[-a,2]*Sqrt[a+b*x^k+c*x^j])]/(k*Rt[-a,2]) /;
FreeQ[{a,b,c,j,k},x] && j===2*k && NegQ[a]


(* ::Subsubsection::Closed:: *)
(*x^m / (a+b x^k+c x^(2k))		Quotients of monomials by symmetric trinomials*)


(* ::Item:: *)
(*Reference: G&R 2.177.1', CRC 120'*)


(* Note: This rule does not use the obvious substitution u=x^k on the whole integrand reducing 
	it to 1/(x*(a+b*x+c*x^2)) so that Log[x] instead of Log[x^k] appears in the result *)
Int[1/(x_*(a_+b_.*x_^k_+c_.*x_^j_.)),x_Symbol] :=
(* Dist[1/a,Int[x^(k-1)*(b+c*x^k)/(a+b*x^k+c*x^j),x]] /; *)
  Log[x]/a -
  Dist[1/(a*k),Subst[Int[(b+c*x)/(a+b*x+c*x^2),x],x,x^k]] /;
FreeQ[{a,b,c,j,k},x] && Not[IntegerQ[k]] && j===2*k


(* ::Item::Closed:: *)
(*Reference: G&R 2.161.3'*)


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: z/(a+b*z+c*z^2) == (1-b/q)/(b-q+2*c*z) + (1+b/q)/(b+q+2*c*z) where q=Sqrt[b^2-4*a*c]*)


Int[x_^k_/(a_+b_.*x_^k_+c_.*x_^j_.),x_Symbol] :=
  Module[{q=Rt[b^2-4*a*c,2]},
  Dist[(1-b/q),Int[1/(b-q+2*c*x^k),x]] +
  Dist[(1+b/q),Int[1/(b+q+2*c*x^k),x]]] /;
FreeQ[{a,b,c,j,k},x] && Not[IntegerQ[k]] && j===2*k && NonzeroQ[b^2-4*a*c] && Not[NegativeQ[b^2-4*a*c]]


(* ::Item:: *)
(*Reference: G&R 2.174.1', CRC 119'*)


Int[x_^m_./(a_+b_.*x_^k_+c_.*x_^j_),x_Symbol] :=
  x^(m-j+1)/(c*(m-j+1)) - 
  Dist[1/c,Int[x^(m-j)*(a+b*x^k)/(a+b*x^k+c*x^j),x]] /;
FreeQ[{a,b,c,j,k},x] && Not[IntegerQ[k]] && j===2*k && RationalQ[{m,k}] && 0<j<m+1


(* ::Item:: *)
(*Reference: G&R 2.176', CRC 123'*)


Int[x_^m_/(a_+b_.*x_^k_+c_.*x_^j_),x_Symbol] :=
  x^(m+1)/(a*(m+1))-
  Dist[1/a,Int[x^(m+k)*(b+c*x^k)/(a+b*x^k+c*x^j),x]] /;
FreeQ[{a,b,c,j,k},x] && Not[IntegerQ[k]] && j===2*k && RationalQ[{m,k}] && m<-1 && k>0


(* ::Subsubsection::Closed:: *)
(*x^m (a+b x^k+c x^(2k))^n		Products of monomials and powers of symmetric trinomials*)


(* ::Item:: *)
(*Reference: G&R 2.174.2'*)


(* Note: This should be generalized from quadratic to all symmetric trinomials! *)
Int[x_^m_*(a_.+b_.*x_+c_.*x_^2)^n_,x_Symbol] :=
  -x^(m-1)*(a+b*x+c*x^2)^(n+1)/(c*(m-1)) - 
  Dist[b/(2*c),Int[x^(m-1)*(a+b*x+c*x^2)^n,x]] + 
  Dist[1/c,Int[x^(m-2)*(a+b*x+c*x^2)^(n+1),x]] /;
FreeQ[{a,b,c},x] && RationalQ[{m,n}] && n<-1 && ZeroQ[m+2*n+1] && Not[IntegerQ[{m,n}]]


(* ::Item:: *)
(*Reference: G&R 2.160.4*)


Int[x_^m_*(a_+b_.*x_^k_.+c_.*x_^j_.)^n_,x_Symbol] :=
  x^(m+1)*(a+b*x^k+c*x^j)^n/(m+j*n+1) +
  Dist[a*j*n/(m+j*n+1),Int[x^m*(a+b*x^k+c*x^j)^(n-1),x]] +
  Dist[b*k*n/(m+j*n+1),Int[x^(m+k)*(a+b*x^k+c*x^j)^(n-1),x]] /;
FreeQ[{a,b,c},x] && RationalQ[{j,k,m,n}] && j==2*k && j>0 && m<-1 && n>1 && NonzeroQ[m+j*n+1] && 
Not[IntegerQ[{j,k,m,n}]]


(* ::Item:: *)
(*Reference: G&R 2.160.3'*)


Int[x_^m_*(a_+b_.*x_^k_.+c_.*x_^j_.)^n_,x_Symbol] :=
  x^(m-j+1)*(a+b*x^k+c*x^j)^(n+1)/(c*k*(n+1)) +
  Dist[a/c,Int[x^(m-j)*(a+b*x^k+c*x^j)^n,x]] /;
FreeQ[{a,b,c,n},x] && FractionQ[{j,k,m}] && j===2*k && 0<j<=m && NonzeroQ[n+1] &&
ZeroQ[m+k*(n-1)+1] && Not[IntegerQ[n] && n>=-1] && Not[IntegerQ[{j,k,m,n}]]


(* ::Item:: *)
(*Reference: G&R 2.160.3 (GR5 2.174.1 is a special case.)*)


Int[x_^m_*(a_+b_.*x_^k_.+c_.*x_^j_.)^n_,x_Symbol] :=
  x^(m-j+1)*(a+b*x^k+c*x^j)^(n+1)/(c*(m+j*n+1)) -
  Dist[b*(m+k*(n-1)+1)/(c*(m+j*n+1)),Int[x^(m-k)*(a+b*x^k+c*x^j)^n,x]] -
  Dist[a*(m-j+1)/(c*(m+j*n+1)),Int[x^(m-j)*(a+b*x^k+c*x^j)^n,x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[{j,k,m}] && j==2*k && 0<j<=m && NonzeroQ[m+j*n+1] &&
NonzeroQ[m+k*(n-1)+1] && Not[IntegerQ[n] && n>=-1] && Not[IntegerQ[{j,k,m,n}]]


(* ::Item:: *)
(*Reference: G&R 2.160.1 special case*)


Int[x_^m_*(a_+b_.*x_^k_.+c_.*x_^j_.)^n_,x_Symbol] :=
  x^(m+1)*(a+b*x^k+c*x^j)^(n+1)/(a*(m+1)) +
  Dist[c/a,Int[x^(m+j)*(a+b*x^k+c*x^j)^n,x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[{j,k,m}] && j==2*k && j>0 && m<-1 && ZeroQ[m+1+k*(n+1)] &&
Not[IntegerQ[{j,k,m,n}]]


(* ::Item:: *)
(*Reference: G&R 2.160.1 special case*)


Int[x_^m_*(a_+b_.*x_^k_.+c_.*x_^j_.)^n_,x_Symbol] :=
  x^(m+1)*(a+b*x^k+c*x^j)^(n+1)/(a*(m+1)) -
  Dist[b/(2*a),Int[x^(m+k)*(a+b*x^k+c*x^j)^n,x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[{j,k,m}] && j==2*k && j>0 && m<-1 && ZeroQ[m+1+j*(n+1)] &&
Not[IntegerQ[{j,k,m,n}]]


(* ::Item:: *)
(*Reference: G&R 2.160.2*)


Int[x_^m_*(a_+b_.*x_^k_.+c_.*x_^j_.)^n_,x_Symbol] :=
  x^(m+1)*(a+b*x^k+c*x^j)^n/(m+1) -
  Dist[b*k*n/(m+1),Int[x^(m+k)*(a+b*x^k+c*x^j)^(n-1),x]] -
  Dist[c*j*n/(m+1),Int[x^(m+j)*(a+b*x^k+c*x^j)^(n-1),x]] /;
FreeQ[{a,b,c},x] && RationalQ[{j,k,m,n}] && j==2*k && j>0 && m<-1 && n>1 &&
Not[IntegerQ[{j,k,m,n}]]


(* ::Item:: *)
(*Reference: G&R 2.160.1*)


(* ::Item:: *)
(*Note: G&R 2.161.6 is a special case*)


Int[x_^m_*(a_+b_.*x_^k_.+c_.*x_^j_.)^n_,x_Symbol] :=
  x^(m+1)*(a+b*x^k+c*x^j)^(n+1)/(a*(m+1)) -
  Dist[b*(m+1+k*(n+1))/(a*(m+1)),Int[x^(m+k)*(a+b*x^k+c*x^j)^n,x]] -
  Dist[c*(m+1+j*(n+1))/(a*(m+1)),Int[x^(m+j)*(a+b*x^k+c*x^j)^n,x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[{j,k,m}] && j==2*k && j>0 && m<-1 && NonzeroQ[m+1+k*(n+1)] &&
NonzeroQ[m+1+j*(n+1)] && Not[RationalQ[n] && n>1] && Not[IntegerQ[{j,k,m,n}]]


(* Previously undiscovered rules ??? *)
Int[x_^k_.*(a_+b_.*x_^k_.+c_.*x_^j_.)^n_,x_Symbol] :=
  x*(b+2*c*x^k)*(a+b*x^k+c*x^j)^n/(2*c*(k*(2*n+1)+1)) - 
  Dist[b/(2*c*(k*(2*n+1)+1)),Int[(a+b*x^k+c*x^j)^n, x]] - 
  Dist[k*n*(b^2-4*a*c)/(2*c*(k*(2*n+1)+1)),Int[x^k*(a+b*x^k+c*x^j)^(n-1), x]] /;
FreeQ[{a,b,c,j,k},x] && RationalQ[n] && Not[IntegerQ[k]] && j===2*k && n>0 && NonzeroQ[b^2-4*a*c] &&
NonzeroQ[k*(2*n+1)+1]


Int[x_^k_.*(a_+b_.*x_^k_.+c_.*x_^j_.)^n_,x_Symbol] :=
  x*(b+2*c*x^k)*(a+b*x^k+c*x^j)^(n+1)/(k*(n+1)*(b^2-4*a*c)) - 
  Dist[b/(k*(n+1)*(b^2-4*a*c)),Int[(a+b*x^k+c*x^j)^(n+1),x]] -
  Dist[2*c*(k*(2*n+3)+1)/(k*(n+1)*(b^2-4*a*c)),Int[x^k*(a+b*x^k+c*x^j)^(n+1),x]] /;
FreeQ[{a,b,c,j,k},x] && RationalQ[n] && Not[IntegerQ[k]] && j===2*k && n<-1 && NonzeroQ[b^2-4*a*c]


(* ::Item:: *)
(*Derivation: Integration by substitution*)


(* Note: Transforms quadratic trinomial into a quadratic binomial. *)
(* Int[x_^m_.*(a_.+b_.*x_+c_.*x_^2)^n_,x_Symbol] :=
  Subst[Int[Expand[(-b/(2*c)+x)^m*(a-b^2/(4*c)+c*x^2)^n],x],x,b/(2*c)+x] /;
FreeQ[{a,b,c},x] && IntegerQ[m] && m>0 && FractionQ[n] *)


(* ::Subsubsection::Closed:: *)
(*x^m (d+e x^k) / (a+b x^k+c x^(2k))	Products of monomials and quotients of binomials by symmetric trinomials*)


(* These way kool, and to my knowledge original, rules reduce the degree of monomial without
	increasing the complexity of the integrands. *)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: (d+e*z)/(a+b*z+c*z^2) == (e+(2*c*d-b*e)/q)/(b-q+2*c*z) + (e-(2*c*d-b*e)/q)/(b+q+2*c*z) where q=Sqrt[b^2-4*a*c]*)


(* Basis: (d+e*z)/(a+b*z+c*z^2) == (e+(2*c*d-b*e)/q)/(b-q+2*c*z) + (e-(2*c*d-b*e)/q)/(b+q+2*c*z)
	where q=Sqrt[b^2-4*a*c] *)


Int[(d_+e_.*x_^k_)/(a_+b_.*x_^k_+c_.*x_^j_.),x_Symbol] :=
  Module[{q=Rt[b^2-4*a*c,2]},
  Dist[(e+(2*c*d-b*e)/q),Int[1/(b-q+2*c*x^k),x]] +
  Dist[(e-(2*c*d-b*e)/q),Int[1/(b+q+2*c*x^k),x]]] /;
FreeQ[{a,b,c,d,e,j,k},x] && Not[IntegerQ[k]] && j===2*k && NonzeroQ[b^2-4*a*c] && Not[NegativeQ[b^2-4*a*c]]


(* Note: This rule does not use the obvious substitution u=x^k on the whole integrand reducing 
	it to (d+e*x)/(x*(a+b*x+c*x^2)) so that Log[x] instead of Log[x^k] appears in the result *)
Int[(d_.+e_.*x_^k_)/(x_*(a_+b_.*x_^k_+c_.*x_^j_.)),x_Symbol] :=
(* Dist[1/a,Int[x^(k-1)*(b*d-a*e+c*d*x^k)/(a+b*x^k+c*x^j),x]] /; *)
  d*Log[x]/a -
    1/(a*k)*Subst[Int[(b*d-a*e+c*d*x)/(a+b*x+c*x^2),x],x,x^k] /;
FreeQ[{a,b,c,d,e,j,k},x] && Not[IntegerQ[k]] && j===2*k


Int[x_^m_.*(d_.+e_.*x_^k_)/(a_+b_.*x_^k_+c_.*x_^j_.),x_Symbol] :=
  e*x^(m-k+1)/(c*(m-k+1)) -
  Dist[1/c,Int[x^(m-k)*(a*e+(b*e-c*d)*x^k)/(a+b*x^k+c*x^j),x]] /;
FreeQ[{a,b,c,d,e,j,k},x] && Not[IntegerQ[k]] && j===2*k && RationalQ[{m,k}] && 0<k<m+1


Int[x_^m_*(d_.+e_.*x_^k_)/(a_+b_.*x_^k_+c_.*x_^j_.),x_Symbol] :=
  d*x^(m+1)/(a*(m+1)) -
  Dist[1/a,Int[x^(m+k)*(b*d-a*e+c*d*x^k)/(a+b*x^k+c*x^j),x]] /;
FreeQ[{a,b,c,d,e,j,k},x] && Not[IntegerQ[k]] && j===2*k && RationalQ[{m,k}] && m<-1 && k>0


(* ::Subsection::Closed:: *)
(*x^m ((a+b x)/(c+d x))^n	Products of monomials and powers of quotients of linears*)


(* Int[(e_.*(a_.+b_.*x_)/(c_.+d_.*x_))^n_,x_Symbol] :=
  Dist[e*(b*c-a*d),Subst[Int[x^n/(b*e-d*x)^2,x],x,e*(a+b*x)/(c+d*x)]] /;
FreeQ[{a,b,c,d,e},x] && FractionQ[n] && NonzeroQ[b*c-a*d] *)


(* Int[x_^m_.*(e_.*(a_.+b_.*x_)/(c_.+d_.*x_))^n_,x_Symbol] :=
  Dist[e*(b*c-a*d),Subst[Int[x^n*(-a*e+c*x)^m/(b*e-d*x)^(m+2),x],x,e*(a+b*x)/(c+d*x)]] /;
FreeQ[{a,b,c,d,e},x] && IntegerQ[m] && FractionQ[n] && NonzeroQ[b*c-a*d] *)


(* Int[(f_+g_.*x_)^m_*(e_.*(a_.+b_.*x_)/(c_.+d_.*x_))^n_,x_Symbol] :=
  Dist[1/g,Subst[Int[x^m*(e*(a-b*f/g+b/g*x)/(c-d*f/g+d/g*x))^n,x],x,f+g*x]] /;
FreeQ[{a,b,c,d,e,f,g},x] && IntegerQ[m] && m<0 && FractionQ[n] && NonzeroQ[b*c-a*d] *)


(* ::Subsection::Closed:: *)
(*Sqrt[a x+Sqrt[b+a^2 x^2]]	Nested square roots*)


Int[Sqrt[a_.*x_+Sqrt[b_+c_.*x_^2]], x_Symbol] :=
  2*(2*a*x-Sqrt[b+c*x^2])*Sqrt[a*x+Sqrt[b+c*x^2]]/(3*a) /;
FreeQ[{a,b,c},x] && c===a^2


Int[Sqrt[a_.*x_-Sqrt[b_+c_.*x_^2]], x_Symbol] :=
  2*(2*a*x+Sqrt[b+c*x^2])*Sqrt[a*x-Sqrt[b+c*x^2]]/(3*a) /;
FreeQ[{a,b,c},x] && c===a^2


(* ::Subsection::Closed:: *)
(*Sqrt[a+Sqrt[a^2+b x^2]]	Nested square roots*)


Int[Sqrt[a_+Sqrt[c_+b_.*x_^2]], x_Symbol] :=
  2*Sqrt[a+Sqrt[a^2+b*x^2]]*(-a^2+b*x^2+a*Sqrt[a^2+b*x^2])/(3*b*x) /;
FreeQ[{a,b,c},x] && c===a^2


Int[Sqrt[a_-Sqrt[c_+b_.*x_^2]], x_Symbol] :=
  2*Sqrt[a-Sqrt[a^2+b*x^2]]*(-a^2+b*x^2-a*Sqrt[a^2+b*x^2])/(3*b*x) /;
FreeQ[{a,b,c},x] && c===a^2


(* ::Subsection::Closed:: *)
(*u / (v+Sqrt[w])		Rationalization of denominators*)


Int[u_./(a_.*x_^m_.+b_.*Sqrt[c_.*x_^n_]),x_Symbol] :=
  Int[u*(a*x^m-b*Sqrt[c*x^n])/(a^2*x^(2*m)-b^2*c*x^n),x] /;
FreeQ[{a,b,c,m,n},x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If b*e^2=d*f^2, 1/(e*Sqrt[a+b*x^n]+f*Sqrt[c+d*x^n]) == (e*Sqrt[a+b*x^n]-f*Sqrt[c+d*x^n])/(a*e^2-c*f^2)*)


Int[u_.*(e_.*Sqrt[a_.+b_.*x_^n_.]+f_.*Sqrt[c_.+d_.*x_^n_.])^m_,x_Symbol] :=
  Dist[(a*e^2-c*f^2)^m,Int[u*(e*Sqrt[a+b*x^n]-f*Sqrt[c+d*x^n])^(-m),x]] /;
FreeQ[{a,b,c,d,e,f,n},x] && IntegerQ[m] && m<0 && ZeroQ[b*e^2-d*f^2]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If a*e^2=c*f^2, 1/(e*Sqrt[a+b*x^n]+f*Sqrt[c+d*x^n]) == (e*Sqrt[a+b*x^n]-f*Sqrt[c+d*x^n])/((b*e^2-d*f^2)*x^n)*)


Int[u_.*(e_.*Sqrt[a_.+b_.*x_^n_.]+f_.*Sqrt[c_.+d_.*x_^n_.])^m_,x_Symbol] :=
  Dist[(b*e^2-d*f^2)^m,Int[u*(e*Sqrt[a+b*x^n]-f*Sqrt[c+d*x^n])^(-m)*x^(m*n),x]] /;
FreeQ[{a,b,c,d,e,f,n},x] && IntegerQ[m] && m<0 && ZeroQ[a*e^2-c*f^2]


Int[u_./(a_+b_.*Sqrt[c_+d_.*x_^n_]),x_Symbol] :=
  Dist[-a/(b^2*d),Int[u/x^n,x]] +
  Dist[1/(b*d),Int[u*Sqrt[c+d*x^n]/x^n,x]] /;
FreeQ[{a,b,c,d,n},x] && a^2===b^2*c


Int[u_./(a_.*x_^m_.+b_.*Sqrt[c_+d_.*x_^n_]),x_Symbol] :=
  Dist[-a/(b^2*c),Int[u*x^m,x]] +
  Dist[1/(b*c),Int[u*Sqrt[c+d*x^n],x]] /;
FreeQ[{a,b,c,d,m,n},x] && n===2*m && a^2===b^2*d


Int[u_./(a_+b_.*x_^m_.+c_.*Sqrt[d_+e_.*x_^n_]),x_Symbol] :=
  Dist[1/(2*b),Int[u/x^m,x]] +
  Dist[1/(2*a),Int[u,x]] -
  Dist[c/(2*a*b),Int[u*Sqrt[d+e*x^n]/x^m,x]] /;
FreeQ[{a,b,c,d,m,n},x] && n===2*m && a^2===c^2*d && b^2===c^2*e


Int[u_./(a_+b_.*x_^m_.+c_.*Sqrt[d_+e_.*x_^n_]),x_Symbol] :=
  Dist[a/b^2,Int[u/x^(2*m),x]] +
  Dist[1/b,Int[u/x^m,x]] -
  Dist[c/b^2,Int[u*Sqrt[d+e*x^n]/x^(2*m),x]] /;
FreeQ[{a,b,c,d,m,n},x] && n===m && a^2===c^2*d && 2*a*b===c^2*e


(* Int[u_./(e_.*Sqrt[a_.+b_.*x_]+f_.*Sqrt[c_.+d_.*x_]),x_Symbol] :=
  Int[u*(e*Sqrt[a+b*x]-f*Sqrt[c+d*x])/(a*e^2-c*f^2+(b*e^2-d*f^2)*x),x] /;
FreeQ[{a,b,c,d,e,f},x] *)


Int[u_./(a_.*x_+b_.*Sqrt[c_.+d_.*x_^2]),x_Symbol] :=
  Dist[a,Int[x*u/(-b^2*c+(a^2-b^2*d)*x^2),x]] -
  Dist[b,Int[u*Sqrt[c+d*x^2]/(-b^2*c+(a^2-b^2*d)*x^2),x]] /;
FreeQ[{a,b,c,d},x]


Int[u_./(e_.*Sqrt[(a_.+b_.*x_^n_.)^p_.]+f_.*Sqrt[(a_.+b_.*x_^n_.)^q_.]),x_Symbol] :=
  Int[u*(e*Sqrt[(a+b*x^n)^p]-f*Sqrt[(a+b*x^n)^q])/(e^2*(a+b*x^n)^p-f^2*(a+b*x^n)^q),x] /;
FreeQ[{a,b,e,f},x] && IntegerQ[{n,p,q}]


(* Int[u_./(v_+a_.*Sqrt[w_]),x_Symbol] :=
  Int[u*v/(v^2-a^2*w),x] -
  Dist[a,Int[u*Sqrt[w]/(v^2-a^2*w),x]] /;
FreeQ[a,x] && PolynomialQ[v,x] *)


(* Int[u_./(a_.*x_+b_.*Sqrt[c_+d_.*x_]),x_Symbol] :=
  Int[(a*x*u-b*u*Sqrt[c+d*x])/(-b^2*c-b^2*d*x+a^2*x^2),x] /;
FreeQ[{a,b,c,d},x] *)


(* ::Subsection::Closed:: *)
(*u Sqrt[c+d x]/(a+b x)		Rationalization of numerator*)


Int[u_.*Sqrt[c_+d_.*x_^2]/(a_+b_.*x_),x_Symbol] :=
  a*Int[u/Sqrt[c+d*x^2],x] - 
  b*Int[x*u/Sqrt[c+d*x^2],x] /;
FreeQ[{a,b,c,d},x] && c===a^2 && d===-b^2


(* ::Subsection::Closed:: *)
(*Sqrt[a+b x^4]			Integrands involving square roots of quartic binomials*)


Int[Sqrt[b_.*x_^2+Sqrt[a_+c_.*x_^4]]/Sqrt[a_+c_.*x_^4],x_Symbol] :=
  ArcTanh[Rt[2*b,2]*x/Sqrt[b*x^2+Sqrt[a+c*x^4]]]/Rt[2*b,2] /;
FreeQ[{a,b,c},x] && ZeroQ[b^2-c] && PosQ[b]


Int[Sqrt[b_.*x_^2+Sqrt[a_+c_.*x_^4]]/Sqrt[a_+c_.*x_^4],x_Symbol] :=
  ArcTan[Rt[-2*b,2]*x/Sqrt[b*x^2+Sqrt[a+c*x^4]]]/Rt[-2*b,2] /;
FreeQ[{a,b,c},x] && ZeroQ[b^2-c] && NegQ[b]


(* ::Item::Closed:: *)
(*Author: Martin*)


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If a>0, Sqrt[a+z^2] == Sqrt[Sqrt[a]+I*z]*Sqrt[Sqrt[a]-I*z]*)


(* ::Item:: *)
(*Basis: If a>0, Sqrt[z+Sqrt[a+z^2]]/Sqrt[a+z^2] == (1-I)/(2*Sqrt[Sqrt[a]-I*z]) + (1+I)/(2*Sqrt[Sqrt[a]+I*z])*)


Int[u_.*Sqrt[v_+Sqrt[a_+w_]]/Sqrt[a_+w_],x_Symbol] :=
  Dist[(1-I)/2, Int[u/Sqrt[Sqrt[a]-I*v],x]] +
  Dist[(1+I)/2, Int[u/Sqrt[Sqrt[a]+I*v],x]] /;
FreeQ[a,x] && ZeroQ[w-v^2] && PositiveQ[a] && Not[LinearQ[v,x]]


(* ::Subsection::Closed:: *)
(*1 / (a+b f[c+d x])		Aggressively factor out constants to prevent them occurring in logarithms*)


(* Note: Constant factors in denominator are aggressively factored out to prevent them occurring
	unnecessarily in logarithm of antiderivative! *)
If[ShowSteps,

Int[1/(a_+b_.*u_),x_Symbol] :=
  Module[{lst=ConstantFactor[a+b*u,x]},
  ShowStep["","Int[1/(a*c+b*c*u),x]","c*Int[1/(a+b*u),x]",Hold[
  Dist[1/lst[[1]],Int[1/lst[[2]],x]]]] /;
 lst[[1]]=!=1] /;
SimplifyFlag && FreeQ[{a,b},x] && (
	MatchQ[u,f_^(c_.+d_.*x) /; FreeQ[{c,d,f},x]] ||
	MatchQ[u,f_[c_.+d_.*x] /; FreeQ[{c,d},x] && MemberQ[{Tan,Cot,Tanh,Coth},f]]),

Int[1/(a_+b_.*u_),x_Symbol] :=
  Module[{lst=ConstantFactor[a+b*u,x]},
  Dist[1/lst[[1]],Int[1/lst[[2]],x]] /;
 lst[[1]]=!=1] /;
FreeQ[{a,b},x] && (
	MatchQ[u,f_^(c_.+d_.*x) /; FreeQ[{c,d,f},x]] ||
	MatchQ[u,f_[c_.+d_.*x] /; FreeQ[{c,d},x] && MemberQ[{Tan,Cot,Tanh,Coth},f]])]
