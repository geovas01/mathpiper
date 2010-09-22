(* ::Package:: *)

(* ::Title:: *)
(*Rational Function Integration Rules*)


(* ::Subsection::Closed:: *)
(*a u				Products having constant factors*)


(* ::Item:: *)
(*Reference: CRC 1*)


Int[a_,x_Symbol] :=
   a*x /;
IndependentQ[a,x]


(* ::Item:: *)
(*Derivation: Power rule for integration*)


Int[c_*(a_+b_.*x_),x_Symbol] :=
  c*(a+b*x)^2/(2*b) /;
FreeQ[{a,b,c},x]


(* ::Item:: *)
(*Reference: G&R 2.02.1, CRC 2*)


If[ShowSteps,

Int[c_*(a_+b_.*x_)^n_,x_Symbol] :=
  ShowStep["","Int[a*u,x]","a*Int[u,x]",Hold[
  Dist[c,Int[(a+b*x)^n,x]]]] /;
SimplifyFlag && FreeQ[{a,b,c,n},x] && NonzeroQ[n+1],

Int[c_*(a_+b_.*x_)^n_,x_Symbol] :=
  Dist[c,Int[(a+b*x)^n,x]] /;
FreeQ[{a,b,c,n},x] && NonzeroQ[n+1]]


(* ::Item:: *)
(*Reference: G&R 2.02.1, CRC 2*)


If[ShowSteps,

Int[a_*u_,x_Symbol] :=
  Module[{lst=ConstantFactor[u,x]},
  ShowStep["","Int[a*u,x]","a*Int[u,x]",Hold[
  Dist[a*lst[[1]],Int[lst[[2]],x]]]]] /;
SimplifyFlag && FreeQ[a,x] && Not[MatchQ[u,b_*v_ /; FreeQ[b,x]]],

Int[a_*u_,x_Symbol] :=
  Module[{lst=ConstantFactor[u,x]},
  Dist[a*lst[[1]],Int[lst[[2]],x]]] /;
FreeQ[a,x] && Not[MatchQ[u,b_*v_ /; FreeQ[b,x]]]]


(* Note: Constant factors in denominators are aggressively factored out to prevent them occurring
	unnecessarily in logarithm terms of antiderivatives! *) 
If[ShowSteps,

Int[u_,x_Symbol] :=
  Module[{lst=ConstantFactor[Simplify[Denominator[u]],x]},
  ShowStep["","Int[a*u,x]","a*Int[u,x]",Hold[
  Dist[1/lst[[1]],Int[Numerator[u]/lst[[2]],x]]]] /;
 lst[[1]]=!=1] /;
SimplifyFlag && (
	MatchQ[u,1/(a_+b_.*x) /; FreeQ[{a,b},x]] ||
	MatchQ[u,x^m_./(a_+b_.*x^n_) /; FreeQ[{a,b,m,n},x] && ZeroQ[m-n+1]] ||
	MatchQ[u,1/((a_.+b_.*x)*(c_+d_.*x)) /; FreeQ[{a,b,c,d},x]] ||
	MatchQ[u,(d_.+e_.*x)/(a_+b_.*x+c_.*x^2) /; FreeQ[{a,b,c,d,e},x]] ||
	MatchQ[u,(c_.*(a_.+b_.*x)^n_)^m_ /; FreeQ[{a,b,c,m,n},x] && ZeroQ[m*n+1]]),

Int[u_,x_Symbol] :=
  Module[{lst=ConstantFactor[Simplify[Denominator[u]],x]},
  Dist[1/lst[[1]],Int[Numerator[u]/lst[[2]],x]] /;
 lst[[1]]=!=1] /;
	MatchQ[u,1/(a_+b_.*x) /; FreeQ[{a,b},x]] ||
	MatchQ[u,x^m_./(a_+b_.*x^n_) /; FreeQ[{a,b,m,n},x] && ZeroQ[m-n+1]] ||
	MatchQ[u,1/((a_.+b_.*x)*(c_+d_.*x)) /; FreeQ[{a,b,c,d},x]] ||
	MatchQ[u,(d_.+e_.*x)/(a_+b_.*x+c_.*x^2) /; FreeQ[{a,b,c,d,e},x]] ||
	MatchQ[u,(c_.*(a_.+b_.*x)^n_)^m_ /; FreeQ[{a,b,c,m,n},x] && ZeroQ[m*n+1]]]


(* Note: Constant factors in denominators are aggressively factored out to prevent them occurring
	unnecessarily in logarithm terms of antiderivatives! *) 
If[ShowSteps,

Int[u_/v_,x_Symbol] :=
  Module[{lst=ConstantFactor[v,x]},
  ShowStep["","Int[a*u,x]","a*Int[u,x]",Hold[
  Dist[1/lst[[1]],Int[u/lst[[2]],x]]]] /;
 lst[[1]]=!=1] /;
SimplifyFlag && Not[FalseQ[DerivativeDivides[v,u,x]]],

Int[u_/v_,x_Symbol] :=
  Module[{lst=ConstantFactor[v,x]},
  Dist[1/lst[[1]],Int[u/lst[[2]],x]] /;
 lst[[1]]=!=1] /;
Not[FalseQ[DerivativeDivides[v,u,x]]]]


(* ::Item:: *)
(*Basis: D[f[x]^p*(a*x^n/f[x])^p/x^(n*p),x] == 0*)


(* ??? *)


(* ::Item:: *)
(*Basis: D[x^(n*p)*f[x]^p/(a*x^n*f[x])^p,x] == 0*)


(* ??? *)


(* ::Item:: *)
(*Basis: D[f[x]^m/(-f[x])^m,x] == 0*)


Int[u_.*v_^m_*w_^n_,x_Symbol] :=
  (v^m*w^n)*Int[u,x] /;
FreeQ[{m,n},x] && ZeroQ[m+n] && ZeroQ[v+w]


(* ::Item:: *)
(*Basis: D[(a+b*x^m)^p/(x^(m*p)*(-b-a/x^m)^p),x] == 0*)


Int[u_.*(a_.+b_.*x_^m_.)^p_.*(c_.+d_.*x_^n_.)^q_., x_Symbol] :=
  (a+b*x^m)^p*(c+d*x^n)^q/x^(m*p)*Int[u*x^(m*p),x] /;
FreeQ[{a,b,c,d,m,n,p,q},x] && ZeroQ[a+d] && ZeroQ[b+c] && ZeroQ[m+n] && ZeroQ[p+q]


(* ::Subsection::Closed:: *)
(*(a + b x)^n			Powers of linear binomials*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.01.2, CRC 27, A&S 3.3.15*)


(* ::Item:: *)
(*Derivation: Reciprocal rule for integration*)


Int[1/(a_+b_.*x_),x_Symbol] :=
  Log[-a-b*x]/b /;
FreeQ[{a,b},x] && NegativeCoefficientQ[a]


(* ::Item::Closed:: *)
(*Reference: G&R 2.01.2, CRC 27, A&S 3.3.15*)


(* ::Item:: *)
(*Derivation: Reciprocal rule for integration*)


Int[1/(a_.+b_.*x_),x_Symbol] :=
  Log[a+b*x]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Reference: G&R 2.01.1, CRC 7*)


(* ::Item:: *)
(*Derivation: Power rule for integration*)


Int[x_^n_.,x_Symbol] :=
  x^(n+1)/(n+1) /;
IndependentQ[n,x] && NonzeroQ[n+1]


(* ::Item::Closed:: *)
(*Reference: G&R 2.01.1, CRC 23, A&S 3.3.14*)


(* ::Item:: *)
(*Derivation: Power rule for integration*)


Int[(a_.+b_.*x_)^n_,x_Symbol] :=
  (a+b*x)^(n+1)/(b*(n+1)) /;
FreeQ[{a,b,n},x] && NonzeroQ[n+1]


(* ::Subsection::Closed:: *)
(*a x^m + b x^n + \[CenterEllipsis]		Integrands involving sums of monomials*)


(* ::Item:: *)
(*Reference: CRC 1,2,4,7,9*)


If[ShowSteps,

Int[u_,x_Symbol] :=
  If[PolynomialQ[u,x],
    ShowStep["","Int[a+b*x+c*x^2+\[CenterEllipsis],x]","a*x+b*x^2/2+c*x^3/3+\[CenterEllipsis]",Hold[
    IntegrateMonomialSum[u,x]]],
  ShowStep["","Int[a+b/x+c*x^m+\[CenterEllipsis],x]","a*x+b*Log[x]+c*x^(m+1)/(m+1)+\[CenterEllipsis]",Hold[
  IntegrateMonomialSum[u,x]]]] /;
SimplifyFlag && MonomialSumQ[u,x],

Int[u_,x_Symbol] :=
  IntegrateMonomialSum[u,x] /;
MonomialSumQ[u,x]]


(* u is a monomial sum in x.  IntegrateMonomialSum[u,x] returns the antiderivative of u wrt x
	with the antiderivative of the constants terms of u collected into a single term times x. *)
IntegrateMonomialSum[u_,x_Symbol] :=
  Module[{lst=Map[Function[If[FreeQ[#,x],{#,0},{0,#*x*If[Exponent[#,x]===-1,Log[x],1/(Exponent[#,x]+1)]}]],u]},
  lst[[1]]*x + lst[[2]]]


(* ::Item:: *)
(*Reference: G&R 2.02.2, CRC 2,4*)


If[ShowSteps,

Int[u_,x_Symbol] :=
  Module[{lst=SplitMonomialTerms[u,x]},
  ShowStep["","Int[a*u+b*v+\[CenterEllipsis],x]","a*Int[u,x]+b*Int[v,x]+\[CenterEllipsis]",Hold[
  Int[lst[[1]],x] + SplitFreeIntegrate[lst[[2]],x]]] /;
 SumQ[lst[[1]]] && Not[FreeQ[lst[[1]],x]] && lst[[2]]=!=0] /;
SimplifyFlag && SumQ[u],

Int[u_,x_Symbol] :=
  Module[{lst=SplitMonomialTerms[u,x]},
  Int[lst[[1]],x] + SplitFreeIntegrate[lst[[2]],x] /;
 SumQ[lst[[1]]] && Not[FreeQ[lst[[1]],x]] && lst[[2]]=!=0] /;
SumQ[u]]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: z*(u+v) == z*u+z*v*)


If[ShowSteps,

Int[x_^m_.*u_,x_Symbol] :=
  ShowStep["","Int[z*(u+v+\[CenterEllipsis]),x]","Int[z*u+z*v+\[CenterEllipsis],x]",Hold[
  Int[Map[Function[x^m*#],u],x]]] /;
SimplifyFlag && IntegerQ[m] && SumQ[u],

Int[x_^m_.*u_,x_Symbol] :=
  Int[Map[Function[x^m*#],u],x] /;
IntegerQ[m] && SumQ[u]]


(* ::Subsection::Closed:: *)
(*a + b x				Integrands involving linear binomials*)


(* ::Subsubsection::Closed:: *)
(*x^m (a+b x)^n			Products of monomials and powers of linear binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification and integration by substitution*)


(* ::Item:: *)
(*Basis: x*(a+b*x) == -a^2/(4*b)*(1-(1+2*b*x/a)^2)*)


(* ::Item:: *)
(*Substitution: u = 1+2*b*x^n/a*)


Int[1/(x_*(a_+b_.*x_^n_.)),x_Symbol] :=
  -2*ArcTanh[1+2*b*x^n/a]/(a*n) /;
FreeQ[{a,b,n},x] && PosQ[n] && RationalQ[b/a]


(* ::Item::Closed:: *)
(*Reference: G&R 2.118.1, CRC 84*)


(* ::Item:: *)
(*Derivation: Reciprocal rule for integration*)


(* ::Item:: *)
(*Basis: 1/(x*(a+b*x^n)) == 1/(x^(n+1)*(b+a/x^n))*)


Int[1/(x_*(a_+b_.*x_^n_.)),x_Symbol] :=
(* -Log[(a+b*x^n)/x^n]/(a*n) /; *)
  Log[x]/a - Log[a+b*x^n]/(a*n) /;
FreeQ[{a,b,n},x] && PosQ[n] && Not[RationalQ[b/a]]


(* ::Item::Closed:: *)
(*Reference: G&R 2.118.1, CRC 84*)


(* ::Item:: *)
(*Derivation: Reciprocal rule for integration*)


(* ::Item:: *)
(*Basis: 1/(x*(a+b*x^n)) == 1/(x^(n+1)*(b+a/x^n))*)


Int[1/(x_*(a_+b_.*x_^n_.)),x_Symbol] :=
  -Log[b+a*x^(-n)]/(a*n) /;
FreeQ[{a,b,n},x] && NegQ[n]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: a*x+b*x^n == x*(a+b*x^(n-1))*)


Int[1/(a_.*x_+b_.*x_^n_),x_Symbol] :=
  Int[1/(x*(a+b*x^(n-1))),x] /;
FreeQ[{a,b,n},x]


(* ::Item:: *)
(*Reference: G&R 2.110.2, CRC 26b special case*)


Int[x_^m_.*(a_+b_.*x_)^n_,x_Symbol] :=
  -x^(m+1)*(a+b*x)^(n+1)/(a*(n+1)) /;
FreeQ[{a,b,m,n},x] && ZeroQ[m+n+2] && NonzeroQ[n+1]


(* ::Item::Closed:: *)
(*Reference: G&R 2.110.2, CRC 26b*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Basis: x^m (a+b*x)^n == x^(m+n+2) ((a+b*x)^n / x^(n+2))*)


Int[x_^m_.*(a_+b_.*x_)^n_,x_Symbol] :=
  -x^(m+1)*(a+b*x)^(n+1)/(a*(n+1)) +
  Dist[(m+n+2)/(a*(n+1)),Int[x^m*(a+b*x)^(n+1),x]] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && 0<m<-n-2 && 2*m+n+1>0


(* ::Item::Closed:: *)
(*Reference: G&R 2.110.1, CRC 26a*)


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_*(a_.+b_.*x_)^n_.,x_Symbol] :=
  x^(m+1)*(a+b*x)^n/(m+n+1) +
  Dist[a*n/(m+n+1),Int[x^m*(a+b*x)^(n-1),x]] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && 0<n<m/2


(* ::Item::Closed:: *)
(*Reference: G&R 2.110.6, CRC 88c*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Basis: x^m (a+b*x)^n == (x^m / (a+b*x)^(m+2)) (a+b*x)^(m+n+2)*)


Int[x_^m_*(a_.+b_.*x_)^n_.,x_Symbol] :=
  x^(m+1)*(a+b*x)^(n+1)/(a*(m+1)) -
  Dist[b*(m+n+2)/(a*(m+1)),Int[x^(m+1)*(a+b*x)^n,x]] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && 0<n<-m-2 && m+2*n-1>0


(* ::Item::Closed:: *)
(*Reference: G&R 2.110.5, CRC 26c*)


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_.*(a_.+b_.*x_)^n_,x_Symbol] :=
  x^m*(a+b*x)^(n+1)/(b*(m+n+1)) -
  Dist[a*m/(b*(m+n+1)),Int[x^(m-1)*(a+b*x)^n,x]] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && 0<m<n/2


(* ::Subsubsection::Closed:: *)
(*(a + b x)^m (c + d x)^n		Products of powers of linear binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If b c+a d=0, (a+b*x) (c+d x) == a c + b d x^2*)


Int[(a_+b_.*x_)^n_.*(c_+d_.*x_)^n_.,x_Symbol] :=
  Int[(a*c+b*d*x^2)^n,x] /;
FreeQ[{a,b,c,d},x] && IntegerQ[n] && ZeroQ[b*c+a*d]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If b c-a d=0 and n is an integer, (a+b*x)^m (c+d x)^n == (d/b)^n (a+b*x)^(m+n)*)


Int[(a_+b_.*x_)^m_.*(c_+d_.*x_)^n_.,x_Symbol] :=
  Dist[(d/b)^n,Int[(a+b*x)^(m+n),x]] /;
FreeQ[{a,b,c,d,m},x] && ZeroQ[b*c-a*d] && IntegerQ[n] && 
(Not[IntegerQ[m]] || LeafCount[a+b*x]<=LeafCount[c+d*x])


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification and integration by substitution*)


(* ::Item:: *)
(*Basis: (a+b*x)*(c+d*x) == -(b*c-a*d)^2*(1-((b*c+a*d)/(b*c-a*d) + 2*b*d*x/(b*c-a*d))^2)/(4*b*d)*)


(* ::Item:: *)
(*Substitution: u = (b*c+a*d)/(b*c-a*d) + 2*b*d*x/(b*c-a*d)*)


(* ::Item:: *)
(*Note: If b c - a d is not rational, partial fraction expansion is applied and two nicer looking logs are returned. *)


Int[1/((a_+b_.*x_)*(c_+d_.*x_)),x_Symbol] :=
  -2*ArcTanh[(b*c+a*d)/(b*c-a*d) + 2*b*d*x/(b*c-a*d)]/(b*c-a*d) /;
FreeQ[{a,b,c,d},x] && RationalQ[b*c-a*d] && b*c-a*d!=0


(* ::Item:: *)
(*Reference: G&R 2.155, CRC 59a special case*)


Int[(a_+b_.*x_)^m_.*(c_+d_.*x_)^n_,x_Symbol] :=
  -(a+b*x)^(m+1)*(c+d*x)^(n+1)/((n+1)*(b*c-a*d)) /;
FreeQ[{a,b,c,d,m,n},x] && ZeroQ[m+n+2] && NonzeroQ[b*c-a*d] && NonzeroQ[n+1]


(* ::Item::Closed:: *)
(*Reference: G&R 2.155, CRC 59a*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Basis: (a+b*x)^m (c+d x)^n == (a+b*x)^(m+n+2) ((c+d x)^n / (a+b*x)^(n+2))*)


Int[(a_+b_.*x_)^m_.*(c_+d_.*x_)^n_,x_Symbol] :=
  -(a+b*x)^(m+1)*(c+d*x)^(n+1)/((n+1)*(b*c-a*d)) +
  Dist[b*(m+n+2)/((n+1)*(b*c-a*d)),Int[(a+b*x)^m*(c+d*x)^(n+1),x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[{m,n}] && NonzeroQ[b*c-a*d] && 0<m<-n-2 && 2*m+n+1>0


(* ::Item::Closed:: *)
(*Reference: G&R 2.151, CRC 59b*)


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[(a_+b_.*x_)^m_*(c_+d_.*x_)^n_.,x_Symbol] :=
  (a+b*x)^(m+1)*(c+d*x)^n/(b*(m+n+1)) +
  Dist[n*(b*c-a*d)/(b*(m+n+1)),Int[(a+b*x)^m*(c+d*x)^(n-1),x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[{m,n}] && NonzeroQ[b*c-a*d] && 0<n<=m


(* ::Subsubsection::Closed:: *)
(*x^m (a+b x)^n (c+d x)^p		Products of monomials and powers of two linears*)


Int[x_^m_.*(a_+b_.*x_)^n_.*(c_+d_.*x_)^n_.,x_Symbol] :=
  Int[x^m*(a*c+b*d*x^2)^n,x] /;
FreeQ[{a,b,c,d,m},x] && IntegerQ[n] && ZeroQ[b*c+a*d]


(* Int[x_^m_*(a_+b_.*x_)^n_*(c_+d_.*x_)^n_,x_Symbol] :=
  Int[x^m*(a*d+b*c+2*b*d*x)^(2*n),x]/(4*b*d)^n /;
FreeQ[{a,b,c,d,m},x] && IntegerQ[n] && ZeroQ[a*d-b*c] *)


(* ::Item:: *)
(*Basis: D[x*(a+b*x^n)^(p+1),x] == (a+b*x^n)^p*(a+b*(n*(p+1)+1)*x^n)*)


Int[(a_+b_.*x_^n_.)^p_.*(c_+d_.*x_^n_.), x_Symbol] :=
  c*x*(a+b*x^n)^(p+1)/a /;
FreeQ[{a,b,c,d,n,p},x] && ZeroQ[a*d-b*c*(n*(p+1)+1)]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: D[x^(m+1)*(a+b*x^n)^(p+1),x] == x^m*(a+b*x^n)^p*(a*(m+1)+b*(m+n*(p+1)+1)*x^n)*)


Int[x_^m_.*(a_+b_.*x_^n_.)^p_.*(c_+d_.*x_^n_.), x_Symbol] :=
  c*x^(m+1)*(a+b*x^n)^(p+1)/(a*(m+1)) /;
FreeQ[{a,b,c,d,m,n,p},x] && NonzeroQ[m+1] && ZeroQ[a*d*(m+1)-b*c*(m+n*(p+1)+1)]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: D[x^(m+1)*(a+b*x^n)^(p+1),x] == x^m*(a+b*x^n)^p*(a*(m+1)+b*(m+n*(p+1)+1)*x^n)*)


Int[x_^m_.*(a_+b_.*x_^n_.)^p_.*(c_.*x_^q_.+d_.*x_^r_.), x_Symbol] :=
  c*x^(m+q+1)*(a+b*x^n)^(p+1)/(a*(m+q+1)) /;
FreeQ[{a,b,c,d,m,n,p,q,r},x] && NonzeroQ[m+q+1] && ZeroQ[n-r+q] && 
	ZeroQ[a*d*(m+q+1)-b*c*(m+q+n*(p+1)+1)]


Int[(a_+b_.*x_)^m_.*(c_+d_.*x_)^n_.*(e_+f_.*x_), x_Symbol] :=
  f*(a+b*x)^(m+1)*(c+d*x)^(n+1)/(b*d*(m+n+2)) /;
FreeQ[{a,b,c,d,e,f,m,n},x] && NonzeroQ[m+n+2] && ZeroQ[f*(b*c*(m+1)+a*d*(n+1))-b*d*e*(m+n+2)]


Int[x_*(a_+b_.*x_)^n_.*(c_+d_.*x_)^p_.,x_Symbol] :=
  (a+b*x)^(n+1)*(c+d*x)^(p+1)/(b*d*(2+n+p)) - 
  Dist[(b*c*(n+1)+a*d*(p+1))/(b*d*(2+n+p)), Int[(a+b*x)^n*(c+d*x)^p, x]] /;
FreeQ[{a,b,c,d,n,p},x] && IntegerQ[{n,p}] && 0<n<=2 && p>5


Int[x_^m_*(a_+b_.*x_)^n_.*(c_+d_.*x_)^p_.,x_Symbol] :=
  x^(m-1)*(a+b*x)^(n+1)*(c+d*x)^(p+1)/(b*d*(1+m+n+p)) - 
  Dist[a*c*(m-1)/(b*d*(1+m+n+p)), Int[x^(m-2)*(a+b*x)^n*(c+d*x)^p, x]] - 
  Dist[(b*c*(m+n)+a*d*(m+p))/(b*d*(1+m+n+p)), Int[x^(m-1)*(a+b*x)^n*(c+d*x)^p, x]] /;
FreeQ[{a,b,c,d,n,p},x] && IntegerQ[{m,n,p}] && 0<m<=2 && 0<n<=2 && p>5


(* ::Subsection::Closed:: *)
(*a + b x^n			Integrands involving nonlinear binomials*)


(* ::Subsubsection::Closed:: *)
(*1 / (a+b x^n)				Reciprocals of binomials*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.124.1a, CRC 60, A&S 3.3.21*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: ArcTan'[z] == 1/(1+z^2)*)


Int[1/(a_+b_.*x_^2),x_Symbol] :=
  Rt[b/a,2]*ArcTan[Rt[b/a,2]*x]/b /;
FreeQ[{a,b},x] && PosQ[a/b]


(* ::Item::Closed:: *)
(*Reference: G&R 2.124.1b', CRC 61b, A&S 3.3.23*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: ArcTanh'[z] == 1/(1-z^2)*)


Int[1/(a_+b_.*x_^2),x_Symbol] :=
  -Rt[-b/a,2]*ArcTanh[Rt[-b/a,2]*x]/b /;
FreeQ[{a,b},x] && NegQ[a/b]


(* ::Item::Closed:: *)
(*Reference: G&R 2.126.1.2, CRC 74*)


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis:  If q=(a/b)^(1/3), 1/(a+b*z^3) == q/(3*a*(q+z)) + q/(3*a)*(2*q-z)/(q^2-q*z+z^2)*)


Int[1/(a_+b_.*x_^3),x_Symbol] :=
  Module[{r=Numerator[Rt[a/b,3]], s=Denominator[Rt[a/b,3]]},
  Dist[r/(3*a),Int[1/(r+s*x),x]] +
  Dist[r/(3*a),Int[(2*r-s*x)/(r^2-r*s*x+s^2*x^2),x]]] /;
FreeQ[{a,b},x] && PosQ[a/b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis:  If q=(-a/b)^(1/3), 1/(a+b*z^3) == q/(3*a*(q-z)) + q/(3*a)*(2*q+z)/(q^2+q*z+z^2)*)


Int[1/(a_+b_.*x_^3),x_Symbol] :=
  Module[{r=Numerator[Rt[-a/b,3]], s=Denominator[Rt[-a/b,3]]},
  Dist[r/(3*a),Int[1/(r-s*x),x]] +
  Dist[r/(3*a),Int[(2*r+s*x)/(r^2+r*s*x+s^2*x^2),x]]] /;
FreeQ[{a,b},x] && NegQ[a/b]


(* ::Item::Closed:: *)
(*Reference: G&R 2.132.1.1', CRC 77'*)


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis:  If q=(a/b)^(1/4), 1/(a+b*z^4) == *)
(*	q/(2*Sqrt[2]*a)*(Sqrt[2]*q-z)/(q^2-Sqrt[2]*q*z+z^2) + q/(2*Sqrt[2]*a)*(Sqrt[2]*q+z)/(q^2+Sqrt[2]*q*z+z^2)*)


Int[1/(a_+b_.*x_^n_),x_Symbol] :=
  Module[{r=Numerator[Rt[a/b,4]], s=Denominator[Rt[a/b,4]]},
  Dist[r/(2*Sqrt[2]*a),Int[(Sqrt[2]*r-s*x^(n/4))/(r^2-Sqrt[2]*r*s*x^(n/4)+s^2*x^(n/2)),x]] +
  Dist[r/(2*Sqrt[2]*a),Int[(Sqrt[2]*r+s*x^(n/4))/(r^2+Sqrt[2]*r*s*x^(n/4)+s^2*x^(n/2)),x]]] /;
FreeQ[{a,b},x] && EvenQ[n/2] && n>2 && PosQ[a/b]


(* ::Item::Closed:: *)
(*Reference: G&R 2.132.1.2', CRC 78'*)


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis:  If q=Sqrt[-a/b], 1/(a+b*z^2) == q/(2*a*(q-z)) + q/(2*a*(q+z))*)


Int[1/(a_+b_.*x_^n_),x_Symbol] :=
  Module[{r=Numerator[Rt[-a/b,2]], s=Denominator[Rt[-a/b,2]]},
  Dist[r/(2*a),Int[1/(r-s*x^(n/2)),x]] +
  Dist[r/(2*a),Int[1/(r+s*x^(n/2)),x]]] /;
FreeQ[{a,b},x] && EvenQ[n/2] && n>2 && NegQ[a/b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If n>0 is even and q=(a/b)^(1/n), 1/(a+b*x^n) == Sum[2 q (q-Cos[(2 k-1) Pi/n] x)/(a n (q^2-2 q Cos[(2 k-1) Pi/n] x+x^2)), {k,1,n/2}]*)


(* ::Item:: *)
(*Basis: If n/2>0 is odd and q=(a/b)^(2/n), 1/(a+b*x^n) == 2*q/(a*n*(q+x^2)) +*)
(*	4*q/(a*n)*Sum[(q-Cos[2*(2*k-1)*Pi/n]*x^2)/(q^2-2*q*Cos[2*(2*k-1)*Pi/n]*x^2+x^4), {k, 1, (n/2-1)/2}]*)


Int[1/(a_+b_.*x_^n_),x_Symbol] :=
  Module[{r=Numerator[Rt[a/b,n/2]], s=Denominator[Rt[a/b,n/2]]},
  Dist[2*r/(a*n),Int[1/(r+s*x^2),x]] +
  Dist[4*r/(a*n),Int[Sum[(r-s*Cos[2*(2*k-1)*Pi/n]*x^2)/(r^2-2*r*s*Cos[2*(2*k-1)*Pi/n]*x^2+s^2*x^4),{k,1,(n/2-1)/2}],x]]] /;
FreeQ[{a,b},x] && OddQ[n/2] && n>2 && PosQ[a/b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If n>0 is even and q=(-a/b)^(1/n), 1/(a+b*x^n) == 2 q^2/(n a (q^2-x^2)) + Sum[2 q (q-Cos[2 k Pi/n] x)/(a n (q^2-2 q Cos[2 k Pi/n] x+x^2)), {k,1,n/2-1}]*)


(* ::Item:: *)
(*Basis: If n/2>0 is odd and q=(-a/b)^(2/n), 1/(a+b*x^n) == 2*q/(a*n*(q-x^2)) +*)
(*	4*q/(a*n)*Sum[(q-Cos[4*k*Pi/n]*x^2)/(q^2-2*q*Cos[4*k*Pi/n]*x^2+x^4), {k, 1, (n/2-1)/2}]*)


Int[1/(a_+b_.*x_^n_),x_Symbol] :=
  Module[{r=Numerator[Rt[-a/b,n/2]], s=Denominator[Rt[-a/b,n/2]]},
  Dist[2*r/(a*n),Int[1/(r-s*x^2),x]] +
  Dist[4*r/(a*n),Int[Sum[(r-s*Cos[4*k*Pi/n]*x^2)/(r^2-2*r*s*Cos[4*k*Pi/n]*x^2+s^2*x^4),{k,1,(n/2-1)/2}],x]]] /;
FreeQ[{a,b},x] && OddQ[n/2] && n>2 && NegQ[a/b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If n>0 is odd and q=(a/b)^(1/n), 1/(a+b*x^n) == q/(a n (q+x)) + Sum[2 q (q-Cos[(2 k-1) Pi/n] x)/(a n (q^2-2 q Cos[(2 k-1) Pi/n] x+x^2)), {k,1,(n-1)/2}]*)


Int[1/(a_+b_.*x_^n_),x_Symbol] :=
  Module[{r=Numerator[Rt[a/b,n]], s=Denominator[Rt[a/b,n]]},
  Int[r/(a*n*(r+s*x)) + 
	Sum[2*r*(r-s*Cos[(2*k-1)*Pi/n]*x)/(a*n*(r^2-2*r*s*Cos[(2*k-1)*Pi/n]*x+s^2*x^2)), {k,1,(n-1)/2}],x]] /;
FreeQ[{a,b},x] && OddQ[n] && n>1 && PosQ[a/b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If n>0 is odd and q=(-a/b)^(1/n), 1/(a+b*x^n) == q/(a n (q-x)) + Sum[2 q (q+Cos[(2 k-1) Pi/n] x)/(a n (q^2+2 q Cos[(2 k-1) Pi/n] x+x^2)), {k,1,(n-1)/2}]*)


Int[1/(a_+b_.*x_^n_),x_Symbol] :=
  Module[{r=Numerator[Rt[-a/b,n]], s=Denominator[Rt[-a/b,n]]},
  Int[r/(a*n*(r-s*x)) + 
	Sum[2*r*(r+s*Cos[(2*k-1)*Pi/n]*x)/(a*n*(r^2+2*r*s*Cos[(2*k-1)*Pi/n]*x+s^2*x^2)), {k,1,(n-1)/2}],x]] /;
FreeQ[{a,b},x] && OddQ[n] && n>1 && NegQ[a/b]


(* ::Subsubsection::Closed:: *)
(*x^m / (a+b x^n)			Quotients of monomials by binomials*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.126.2, CRC 75*)


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Let q=(a/b)^(1/3), then x/(a+b*x^3) == -q^2/(3*a*(q+x)) + q^2/(3*a)*(q+x)/(q^2-q*x+x^2)*)


Int[x_/(a_+b_.*x_^3),x_Symbol] :=
  Module[{r=Numerator[Rt[a/b,3]], s=Denominator[Rt[a/b,3]]},
  Dist[-r^2/(3*a*s),Int[1/(r+s*x),x]] +
  Dist[r^2/(3*a*s),Int[(r+s*x)/(r^2-r*s*x+s^2*x^2),x]]] /;
FreeQ[{a,b},x] && PosQ[a/b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Let q=(-a/b)^(1/3), then x/(a+b*x^3) == q^2/(3*a*(q-x)) - q^2/(3*a)*(q-x)/(q^2+q*x+x^2)*)


Int[x_/(a_+b_.*x_^3),x_Symbol] :=
  Module[{r=Numerator[Rt[-a/b,3]], s=Denominator[Rt[-a/b,3]]},
  Dist[r^2/(3*a*s),Int[1/(r-s*x),x]] -
  Dist[r^2/(3*a*s),Int[(r-s*x)/(r^2+r*s*x+s^2*x^2),x]]] /;
FreeQ[{a,b},x] && NegQ[a/b]


(* ::Item:: *)
(*Derivation: Integration by substitution*)


Int[x_^m_./(a_+b_.*x_^n_),x_Symbol] :=
  Module[{g=GCD[m+1,n]},
  Dist[1/g,Subst[Int[x^((m+1)/g-1)/(a+b*x^(n/g)),x],x,x^g]] /;
 g>1] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && 0<m+1<n


(* ::Item::Closed:: *)
(*Reference: G&R 2.132.3.1', CRC 81'*)


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Let q=(a/b)^(1/4), then z^2/(a+b*z^4) == 1/(2*Sqrt[2]*b*q)*z/(q^2-Sqrt[2]*q*z+z^2) - 1/(2*Sqrt[2]*b*q)*z/(q^2+Sqrt[2]*q*z+z^2)*)


(* ::Item:: *)
(*Basis: Let q=(a/b)^(1/4), then x^m/(a+b*x^(2*m)) == *)
(*		1/(2*Sqrt[2]*b*q)*x^(m/2)/(q^2-Sqrt[2]*q*x^(m/2)+x^m) - 1/(2*Sqrt[2]*b*q)*x^(m/2)/(q^2+Sqrt[2]*q*x^(m/2)+x^m)*)


Int[x_^m_/(a_+b_.*x_^n_),x_Symbol] :=
  Module[{r=Numerator[Rt[a/b,4]], s=Denominator[Rt[a/b,4]]},
  Dist[s^3/(2*Sqrt[2]*b*r),Int[x^(m/2)/(r^2-Sqrt[2]*r*s*x^(m/2)+s^2*x^m),x]] -
  Dist[s^3/(2*Sqrt[2]*b*r),Int[x^(m/2)/(r^2+Sqrt[2]*r*s*x^(m/2)+s^2*x^m),x]]] /;
FreeQ[{a,b},x] && EvenQ[m] && m>0 && n===2*m && PosQ[a/b]


(* ::Item::Closed:: *)
(*Reference: G&R 2.132.3.2', CRC 82'*)


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Let q=Sqrt[-a/b], then z/(a+b*z^2) == 1/(2*b*(q+z)) - 1/(2*b*(q-z))*)


(* ::Item:: *)
(*Basis: Let q=Sqrt[-a/b], then x^m/(a+b*x^(2*m)) == 1/(2*b*(q+x^m)) - 1/(2*b*(q-x^m))*)


Int[x_^m_/(a_+b_.*x_^n_),x_Symbol] :=
  Module[{r=Numerator[Rt[-a/b,2]], s=Denominator[Rt[-a/b,2]]},
  Dist[s/(2*b),Int[1/(r+s*x^m),x]] -
  Dist[s/(2*b),Int[1/(r-s*x^m),x]]] /;
FreeQ[{a,b},x] && EvenQ[m] && m>0 && n===2*m && NegQ[a/b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If n>0 is even and 0<=m<n, let q=(a/b)^(1/n), then x^m/(a+b*x^n) ==*)
(*  Sum[2 q^(m+1) (q Cos[(2 k-1) m Pi/n]-Cos[(2 k-1) (m+1) Pi/n] x)/(a n (q^2-2 q Cos[(2 k-1) Pi/n] x+x^2)), {k,1,n/2}]*)


(* ::Item:: *)
(*Basis: If n/2>0 is odd, m is even and 0<=m<n, let q=(a/b)^(2/n), then x^m/(a+b*x^n) == -2*(-q)^(m/2+1)/(a*n*(q+x^2)) +*)
(*  4*q^(m/2+1)/(a*n)*Sum[(q*Cos[(2*k-1)*m*Pi/n]-Cos[(2*k-1)*(m+2)*Pi/n]*x^2)/(q^2-2*q*Cos[2*(2*k-1)*Pi/n]*x^2+x^4), {k, 1, (n/2-1)/2}]*)


Int[x_^m_./(a_+b_.*x_^n_),x_Symbol] :=
  Module[{r=Numerator[Rt[a/b,n/2]], s=Denominator[Rt[a/b,n/2]]},
  Dist[-2*(-r)^(m/2+1)/(a*n*s^(m/2)),Int[1/(r+s*x^2),x]] +
  Dist[4*r^(m/2+1)/(a*n*s^(m/2)),
	Int[Sum[(r*Cos[(2*k-1)*m*Pi/n]-s*Cos[(2*k-1)*(m+2)*Pi/n]*x^2)/(r^2-2*r*s*Cos[2*(2*k-1)*Pi/n]*x^2+s^2*x^4),{k,1,(n/2-1)/2}],x]]] /;
 FreeQ[{a,b},x] && OddQ[n/2] && EvenQ[m] && 0<m<n && GCD[m+1,n]==1 && PosQ[a/b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If m and n are even and 0<=m<n, let q=(-a/b)^(1/n), then x^m/(a+b*x^n) ==*)
(*    2 q^(m+2)/(n a (q^2-x^2)) + Sum[2 q^(m+1) (q Cos[2 k m Pi/n]-Cos[2 k (m+1) Pi/n] x)/(a n (q^2-2 q Cos[2 k Pi/n] x+x^2)), {k,1,n/2-1}]*)


(* ::Item:: *)
(*Basis: If n/2>0 is odd, m is even and 0<=m<n, let q=(-a/b)^(2/n), then x^m/(a+b*x^n) == 2*q^(m/2+1)/(a*n*(q-x^2)) +*)
(*    4*q^(m/2+1)/(a*n)*Sum[(q*Cos[2*k*m*Pi/n]-Cos[2*k*(m+2)*Pi/n]*x^2)/(q^2-2*q*Cos[4*k*Pi/n]*x^2+x^4), {k, 1, (n/2-1)/2}]*)


Int[x_^m_./(a_+b_.*x_^n_),x_Symbol] :=
  Module[{r=Numerator[Rt[-a/b,n/2]], s=Denominator[Rt[-a/b,n/2]]},
  Dist[2*r^(m/2+1)/(a*n*s^(m/2)),Int[1/(r-s*x^2),x]] +
  Dist[4*r^(m/2+1)/(a*n*s^(m/2)),
	Int[Sum[(r*Cos[2*k*m*Pi/n]-s*Cos[2*k*(m+2)*Pi/n]*x^2)/(r^2-2*r*s*Cos[4*k*Pi/n]*x^2+s^2*x^4),{k,1,(n/2-1)/2}],x]]] /;
 FreeQ[{a,b},x] && OddQ[n/2] && EvenQ[m] && 0<m<n && GCD[m+1,n]==1 && NegQ[a/b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If n>0 is odd and 0<=m<n, let q=(a/b)^(1/n), then*)
(*x^m/(a+b*x^n) == -(-q)^(m+1)/(a n (q+x)) + Sum[2 q^(m+1) (q Cos[(2 k-1) m Pi/n]-Cos[(2 k-1) (m+1) Pi/n] x)/(a n (q^2-2 q Cos[(2 k-1) Pi/n] x+x^2)), {k,1,(n-1)/2}]*)


Int[x_^m_./(a_+b_.*x_^n_),x_Symbol] :=
  Module[{r=Numerator[Rt[a/b,n]], s=Denominator[Rt[a/b,n]]},
  Int[-(-r)^(m+1)/s^m/(a*n*(r+s*x)) + Sum[2*r^(m+1)/s^m*
		(r*Cos[(2*k-1)*m*Pi/n]-s*Cos[(2*k-1)*(m+1)*Pi/n]*x)/(a*n*(r^2-2*r*s*Cos[(2*k-1)*Pi/n]*x+s^2*x^2)),
			{k,1,(n-1)/2}],x]] /;
FreeQ[{a,b},x] && OddQ[n] && IntegerQ[m] && 0<m<n && GCD[m+1,n]==1 && PosQ[a/b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If n>0 is odd and 0<=m<n, let q=(-a/b)^(1/n), then*)
(*x^m/(a+b*x^n) == q^(m+1)/(a n (q-x)) - Sum[2 (-q)^(m+1) (q Cos[(2 k-1) m Pi/n]+Cos[(2 k-1) (m+1) Pi/n] x)/(a n (q^2+2 q Cos[(2 k-1) Pi/n] x+x^2)),{k,1,(n-1)/2}]*)


Int[x_^m_./(a_+b_.*x_^n_),x_Symbol] :=
  Module[{r=Numerator[Rt[-a/b,n]], s=Denominator[Rt[-a/b,n]]},
  Int[r^(m+1)/(a*n*s^m*(r-s*x)) - Sum[2*(-r)^(m+1)/s^m*
		(r*Cos[(2*k-1)*m*Pi/n]+s*Cos[(2*k-1)*(m+1)*Pi/n]*x)/(a*n*(r^2+2*r*s*Cos[(2*k-1)*Pi/n]*x+s^2*x^2)),
			{k,1,(n-1)/2}],x]] /;
FreeQ[{a,b},x] && OddQ[n] && IntegerQ[m] && 0<m<n && GCD[m+1,n]==1 && NegQ[a/b]


(* ::Item::Closed:: *)
(*Note: The following expansion is valid but not needed, since GCD[m+1,n]>1 when m is odd and n even:*)


(* ::Item:: *)
(*Basis: If m is odd and n is even and 0<=m<n, let q=(-a/b)^(1/n), then*)
(*x^m/(a+b*x^n) == 2 q^(m+1) x/(n a (q^2-x^2)) + Sum[2 q^(m+1) (q Cos[2 k m Pi/n]-Cos[2 k (m+1) Pi/n] x)/(a n (q^2-2 q Cos[2 k Pi/n] x+x^2)), {k,1,n/2-1}]*)


(* ::Subsubsection::Closed:: *)
(*(c+d x^m) / (a+b x^n)			Quotients of monomials by binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Let q=(a/b)^(1/4), then (c+d x^m)/(a+b*x^(2*m)) == *)
(*					q/(2*Sqrt[2]*a)*(Sqrt[2]*c*q+(c-d*q^2)*x^(m/2))/(q^2+Sqrt[2]*q*x^(m/2)+x^m) + *)
(*					q/(2*Sqrt[2]*a)*(Sqrt[2]*c*q-(c-d*q^2)*x^(m/2))/(q^2-Sqrt[2]*q*x^(m/2)+x^m)*)


Int[(c_.+d_.*x_^m_)/(a_+b_.*x_^n_),x_Symbol] :=
  Module[{q=Rt[a/b,4]},
  Dist[q/(2*Sqrt[2]*a),Int[(Sqrt[2]*c*q+(c-d*q^2)*x^(m/2))/(q^2+Sqrt[2]*q*x^(m/2)+x^m),x]] +
  Dist[q/(2*Sqrt[2]*a),Int[(Sqrt[2]*c*q-(c-d*q^2)*x^(m/2))/(q^2-Sqrt[2]*q*x^(m/2)+x^m),x]]] /;
FreeQ[{a,b,c,d},x] && EvenQ[m] && m>0 && n===2*m && PosQ[a/b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Let q=Sqrt[-a/b], then (c+d*x^m)/(a+b*x^(2*m)) == (c+d*q)/(2*(a+b*q*x^m)) + (c-d*q)/(2*(a-b*q*x^m))*)


Int[(c_.+d_.*x_^m_)/(a_+b_.*x_^n_),x_Symbol] :=
  Module[{q=Rt[-a/b,2]},
  Dist[(c+d*q)/2, Int[1/(a+b*q*x^m),x]] +
  Dist[(c-d*q)/2, Int[1/(a-b*q*x^m),x]]] /;
FreeQ[{a,b,c,d},x] && EvenQ[m] && m>0 && n===2*m && NegQ[a/b] && NonzeroQ[b*c^2+a*d^2]


(* ::Subsubsection::Closed:: *)
(*(a+b x^n)^p				Powers of binomials*)


(* ::Item:: *)
(*Reference: G&R 2.110.2, CRC 88d special case*)


Int[(a_+b_.*x_^n_)^p_,x_Symbol] :=
  x*(a+b*x^n)^(p+1)/a /;
FreeQ[{a,b,n,p},x] && ZeroQ[n*(p+1)+1]


(* ::Item::Closed:: *)
(*Reference: G&R 2.110.2, CRC 88d*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Basis: (a+b*x^n)^p == x^(n*(p+1)+1) * ((a+b*x^n)^p/x^(n*(p+1)+1))*)


(* ::Item:: *)
(*Basis: Int[(a+b*x^n)^p/x^(n*(p+1)+1),x] == -((a+b*x^n)^(p+1)/(x^(n*(p+1))*(a*n*(p+1))))*)


(* ::Item:: *)
(*Note: Requirement that n>1 ensures new term is a proper fraction.*)


Int[(a_+b_.*x_^n_)^p_,x_Symbol] :=
  -x*(a+b*x^n)^(p+1)/(a*n*(p+1)) + 
  Dist[(n*(p+1)+1)/(a*n*(p+1)),Int[(a+b*x^n)^(p+1),x]] /;
FreeQ[{a,b},x] && IntegerQ[{n,p}] && n>1 && p<-1


(* ::Subsubsection::Closed:: *)
(*x^m (a+b x^n)^p			Products of monomials and powers of binomials*)


(* ::Item:: *)
(*Reference: G&R 2.110.6, CRC 88c special case*)


Int[x_^m_.*(a_+b_.*x_^n_)^p_,x_Symbol] :=
  x^(m+1)*(a+b*x^n)^(p+1)/(a*(m+1)) /;
FreeQ[{a,b,m,n,p},x] && ZeroQ[m+n*(p+1)+1] && NonzeroQ[m+1] && NonzeroQ[p+2]


(* ::Item::Closed:: *)
(*Reference: G&R 2.110.4*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Basis: x^m (a+b*x^n)^p == x^(m-n+1) * ((a+b*x^n)^p*x^(n-1))*)


(* ::Item:: *)
(*Basis: Int[(a+b*x^n)^p*x^(n-1),x] == (a+b*x^n)^(p+1)/(b*n*(p+1))*)


(* ::Item:: *)
(*Note: Requirement that m<2 n-1 ensures new term is a proper fraction.*)


Int[x_^m_.*(a_+b_.*x_^n_)^p_,x_Symbol] :=
  x^(m-n+1)*(a+b*x^n)^(p+1)/(b*n*(p+1)) - 
  Dist[(m-n+1)/(b*n*(p+1)),Int[x^(m-n)*(a+b*x^n)^(p+1),x]] /;
FreeQ[{a,b},x] && IntegerQ[{m,n,p}] && n>1 && p<-1 && n<=m<2*n-1


(* ::Item::Closed:: *)
(*Reference: G&R 2.110.2, CRC 88d*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Basis: x^m (a+b*x^n)^p == x^(m+n*(p+1)+1) ((a+b*x^n)^p/x^(n*(p+1)+1))*)


(* ::Item:: *)
(*Basis: Int[(a+b*x^n)^p/x^(n*(p+1)+1),x] == -((a+b*x^n)^(p+1)/(x^(n*(p+1))*(a*n*(p+1))))*)


(* ::Item:: *)
(*Note: Requirement that m+1<n ensures new term is a proper fraction.*)


Int[x_^m_.*(a_+b_.*x_^n_)^p_,x_Symbol] :=
  -x^(m+1)*(a+b*x^n)^(p+1)/(a*n*(p+1)) + 
  Dist[(m+n*(p+1)+1)/(a*n*(p+1)),Int[x^m*(a+b*x^n)^(p+1),x]] /;
FreeQ[{a,b},x] && IntegerQ[{m,n,p}] && p<-1 && n>1 && 0<m+1<n && NonzeroQ[m+n*(p+1)+1]


(* ::Item::Closed:: *)
(*Reference: G&R 2.110.6, CRC 88c*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Basis: x^m*(a+b*x^n)^p == (x^m/(a+b*x^n)^((m+n+1)/n)) * (a+b*x^n)^((m+n*(p+1)+1)/n)*)


(* ::Item:: *)
(*Basis: Int[x^m/(a+b*x^n)^((m+n+1)/n),x] == x^(m+1)/((a+b*x^n)^((m+1)/n)*(a*(m+1)))*)


Int[x_^m_.*(a_+b_.*x_^n_)^p_,x_Symbol] :=
  x^(m+1)*(a+b*x^n)^(p+1)/(a*(m+1)) - 
  Dist[b*(m+n*(p+1)+1)/(a*(m+1)),Int[x^(m+n)*(a+b*x^n)^p,x]] /;
FreeQ[{a,b},x] && IntegerQ[{m,n,p}] && m<-1 && n>0 && 
IntegerQ[(m+n*(p+1)+1)/n] && 0<n-2(m+n*(p+1)+1)<n*p


(* ::Item::Closed:: *)
(*Reference: G&R 2.110.5, CRC 88a*)


(* ::Item:: *)
(*Derivation: Inverted integration by parts*)


Int[x_^m_.*(a_+b_.*x_^n_)^p_,x_Symbol] :=
  x^(m-n+1)*(a+b*x^n)^(p+1)/(b*(m+n*p+1)) - 
  Dist[a*(m-n+1)/(b*(m+n*p+1)),Int[x^(m-n)*(a+b*x^n)^p,x]] /;
FreeQ[{a,b},x] && IntegerQ[{m,n,p}] && NonzeroQ[m+n*p+1] && (IntegerQ[(m+1)/n] && 0<(m+1)/n) &&
2*m/n<p+1 && 0<n<=m


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


Int[x_^m_.*(a_+b_.*x_^n_.)^p_.,x_Symbol] :=
  Int[Expand[x^m*(a+b*x^n)^p],x] /;
FreeQ[{a,b,m,n},x] && IntegerQ[p] && p>0 && ExpandIntegrandQ[m,n,p]


(* ::Item::Closed:: *)
(*Reference: G&R 2.110.4*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Basis: x^m (a+b*x^n)^p == x^(m-n+1) ((a+b*x^n)^p x^(n-1))*)


(* ::Item:: *)
(*Note: Requirement that m<2 n-1 ensures new term is a proper fraction.*)


(* ::Item:: *)
(*Note: Unfortunately this rule is necessary to prevent the Ostrogradskiy-Hermite method from being applied instead of substituting for c+d x.*)


Int[(c_+d_.*x_)^m_.*(a_+b_.*(c_+d_.*x_)^n_)^p_,x_Symbol] :=
  (c+d*x)^(m-n+1)*(a+b*(c+d*x)^n)^(p+1)/(b*d*n*(p+1)) - 
  Dist[(m-n+1)/(b*n*(p+1)),Int[(c+d*x)^(m-n)*(a+b*(c+d*x)^n)^(p+1),x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[{m,n,p}] && n>1 && p<-1 && n<=m<2*n-1


(* ::Subsubsection::Closed:: *)
(*(a+b x^n)^m / (b+a/x^n)		Quotients of powers of monomials and monomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: (a+b*x^n)/(b+a/x^n) == x^n*)


Int[(a_+b_.*x_^n_.)^m_/(b_+a_.*x_^p_.),x_Symbol] :=
  Int[x^n*(a+b*x^n)^(m-1), x] /;
FreeQ[{a,b,m,n,p},x] && ZeroQ[n+p]


(* ::Subsection::Closed:: *)
(*a + b x + c x^2		Integrands involving quadratic trinomials*)


(* ::Subsubsection::Closed:: *)
(*(a+b x+c x^2)^n			Powers of quadratic trinomials*)


(* ::Item::Closed:: *)
(*Reference: A&S 3.3.18*)


(* ::Item:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If b^2-4*a*c=0, a+b*z+c*z^2 == (b/2+c*z)^2/c*)


Int[(a_+b_.*x_+c_.*x_^2)^n_,x_Symbol] :=
  Int[(b/2+c*x)^(2*n),x]/c^n /;
FreeQ[{a,b,c},x] && IntegerQ[n] && ZeroQ[b^2-4*a*c]


(* ::Item::Closed:: *)
(*Reference: G&R 2.172.2, CRC 110a, A&S 3.3.17*)


(* ::Item:: *)
(*Derivation: Algebraic simplification and integration by substitution*)


(* ::Item:: *)
(*Basis: If q=Sqrt[b^2-4 a c], a+b x+c x^2 == -q^2/(4 c) (1-((b+2 c x)/q)^2)*)


(* ::Item:: *)
(*Substitution: u = (b+2 c x)/q where q = Sqrt[b^2-4 a c]*)


Int[1/(a_+b_.*x_+c_.*x_^2),x_Symbol] :=
  Module[{q=Rt[b^2-4*a*c,2]},
  -2*ArcTanh[b/q+2*c*x/q]/q /;
 SqrtNumberQ[q] && RationalQ[b/q]] /;
FreeQ[{a,b,c},x] && PosQ[b^2-4*a*c]


(* ::Item::Closed:: *)
(*Reference: G&R 2.172.2, CRC 110a, A&S 3.3.17*)


(* ::Item:: *)
(*Derivation: Algebraic simplification and integration by substitution*)


(* ::Item:: *)
(*Basis: If q=Sqrt[b^2-4 a c], a+b x+c x^2 == -q^2/(4 c) (1-((b+2 c x)/q)^2)*)


(* ::Item:: *)
(*Substitution: u = (b+2 c x)/q where q = Sqrt[b^2-4 a c]*)


Int[1/(a_+b_.*x_+c_.*x_^2),x_Symbol] :=
  -2*ArcTanh[(b+2*c*x)/Rt[b^2-4*a*c,2]]/Rt[b^2-4*a*c,2] /;
FreeQ[{a,b,c},x] && PosQ[b^2-4*a*c]


(* ::Item::Closed:: *)
(*Reference: G&R 2.172.4, CRC 109, A&S 3.3.16*)


(* ::Item:: *)
(*Derivation: Algebraic simplification and integration by substitution*)


(* ::Item:: *)
(*Basis: If q = Sqrt[4 a c-b^2], a+b x+c x^2 == q^2/(4 c) (1+((b+2 c x)/q)^2)*)


(* ::Item:: *)
(*Substitution: u = (b+2 c x)/q where q = Sqrt[4 a c-b^2]*)


Int[1/(a_+b_.*x_+c_.*x_^2),x_Symbol] :=
  Module[{q=Rt[4*a*c-b^2,2]},
  2*ArcTan[b/q+2*c*x/q]/q /;
 SqrtNumberQ[q] && RationalQ[b/q]] /;
FreeQ[{a,b,c},x] && NegQ[b^2-4*a*c]


(* ::Item::Closed:: *)
(*Reference: G&R 2.172.4, CRC 109, A&S 3.3.16*)


(* ::Item:: *)
(*Derivation: Algebraic simplification and integration by substitution*)


(* ::Item:: *)
(*Basis: If q = Sqrt[4 a c-b^2], a+b x+c x^2 == q^2/(4 c) (1+((b+2 c x)/q)^2)*)


(* ::Item:: *)
(*Substitution: u = (b+2 c x)/q where q = Sqrt[4 a c-b^2]*)


Int[1/(a_+b_.*x_+c_.*x_^2),x_Symbol] :=
  2*ArcTan[(b+2*c*x)/Rt[4*a*c-b^2,2]]/Rt[4*a*c-b^2,2] /;
FreeQ[{a,b,c},x] && NegQ[b^2-4*a*c]


(* ::Item:: *)
(*Reference: G&R 2.264.5, CRC 239*)


Int[1/(a_.+b_.*x_+c_.*x_^2)^(3/2),x_Symbol] :=
  -2*(b+2*c*x)/((b^2-4*a*c)*Sqrt[a+b*x+c*x^2]) /;
FreeQ[{a,b,c},x] && NonzeroQ[b^2-4*a*c]


(* ::Item:: *)
(*Reference: G&R 2.171.3, GR5 2.263.3, CRC 113,241*)


Int[(a_.+b_.*x_+c_.*x_^2)^n_,x_Symbol] :=
  (b+2*c*x)*(a+b*x+c*x^2)^(n+1)/((n+1)*(b^2-4*a*c)) -
  Dist[2*c*(2*n+3)/((n+1)*(b^2-4*a*c)),Int[(a+b*x+c*x^2)^(n+1),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n<-1 && NonzeroQ[b^2-4*a*c]


(* ::Subsubsection::Closed:: *)
(*(d+e x) / (a+b x+c x^2)			Quotients of linears by quadratic trinomials*)


(* ::Item:: *)
(*Reference: G&R 2.175.1, CRC 114*)


Int[(d_.+e_.*x_)/(a_+b_.*x_+c_.*x_^2),x_Symbol] :=
  e*Log[-a-b*x-c*x^2]/(2*c) /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[2*c*d-b*e] && NegativeCoefficientQ[a]


(* ::Item:: *)
(*Reference: G&R 2.175.1, CRC 114*)


Int[(d_.+e_.*x_)/(a_+b_.*x_+c_.*x_^2),x_Symbol] :=
  e*Log[a+b*x+c*x^2]/(2*c) /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[2*c*d-b*e]


(* ::Item:: *)
(*Reference: A&S 3.3.19*)


Int[(d_.+e_.*x_)/(a_+b_.*x_+c_.*x_^2),x_Symbol] :=
  e*Log[-a-b*x-c*x^2]/(2*c) +
  Dist[Simplify[(2*c*d-b*e)/(2*c)],Int[1/(a+b*x+c*x^2),x]] /;
FreeQ[{a,b,c,d,e},x] && Not[RationalQ[Rt[b^2-4*a*c,2]]] && NonzeroQ[a*e^2+c*d^2-b*d*e] && 
NegativeCoefficientQ[a]


(* ::Item:: *)
(*Reference: A&S 3.3.19*)


Int[(d_.+e_.*x_)/(a_+b_.*x_+c_.*x_^2),x_Symbol] :=
  e*Log[a+b*x+c*x^2]/(2*c) +
  Dist[Simplify[(2*c*d-b*e)/(2*c)],Int[1/(a+b*x+c*x^2),x]] /;
FreeQ[{a,b,c,d,e},x] && Not[RationalQ[Rt[b^2-4*a*c,2]]] && NonzeroQ[a*e^2+c*d^2-b*d*e]


(* ::Subsubsection::Closed:: *)
(*(d+e x)^m (a+c x^2)^n			Products of powers of linears and powers of quadratic binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If a*e^2+c*d^2==0, a+c*x^2 == (d+e*x)*(a/d+c/e*x)*)


Int[(d_+e_.*x_)^m_.*(a_+c_.*x_^2)^n_.,x_Symbol] :=
  Int[(d+e*x)^(m+n)*(a/d+c/e*x)^n,x] /;
FreeQ[{a,c,d,e,m},x] && IntegerQ[n] && ZeroQ[a*e^2+c*d^2]


(* ::Item:: *)
(*Reference: G&R 2.174.1, CRC 119*)


Int[(d_+e_.*x_)*(a_.+c_.*x_^2)^n_,x_Symbol] :=
  e*(a+c*x^2)^(n+1)/(2*c*(n+1)) +
  Dist[d,Int[(a+c*x^2)^n,x]] /;
FreeQ[{a,c,d,e,n},x] && NonzeroQ[n+1] && Not[IntegerQ[n] && n>0]


(* ::Item:: *)
(*Reference: G&R 2.174.1, CRC 119*)


Int[(d_+e_.*x_)^m_*(a_.+c_.*x_^2)^n_,x_Symbol] :=
  e*(d+e*x)^(m-1)*(a+c*x^2)^(n+1)/(c*(m+2*n+1)) -
  Dist[(a*e^2+c*d^2)*(m-1)/(c*(m+2*n+1)),Int[(d+e*x)^(m-2)*(a+c*x^2)^n,x]] /;
FreeQ[{a,c,d,e},x] && RationalQ[{m,n}] && m>1 && ZeroQ[m+n]


(* ::Item:: *)
(*Reference: G&R 2.174.1, CRC 119*)


Int[(d_+e_.*x_)^m_.*(a_.+c_.*x_^2)^n_,x_Symbol] :=
  e*(d+e*x)^(m-1)*(a+c*x^2)^(n+1)/(c*(m+2*n+1)) +
  Dist[2*c*d*(m+n)/(c*(m+2*n+1)),Int[(d+e*x)^(m-1)*(a+c*x^2)^n,x]] /;
FreeQ[{a,c,d,e,n},x] && RationalQ[m] && m>1 && NonzeroQ[m+2*n+1] && Not[IntegerQ[n] && n>=-1] &&
ZeroQ[a*e^2+c*d^2]


(* ::Item:: *)
(*Reference: G&R 2.174.1, CRC 119*)


(* Int[(d_+e_.*x_)^m_*(a_.+c_.*x_^2)^n_,x_Symbol] :=
  e*(d+e*x)^(m-1)*(a+c*x^2)^(n+1)/(c*(m+2*n+1)) +
  Dist[2*c*d*(m+n)/(c*(m+2*n+1)),Int[(d+e*x)^(m-1)*(a+c*x^2)^n,x]] -
  Dist[(a*e^2+c*d^2)*(m-1)/(c*(m+2*n+1)),Int[(d+e*x)^(m-2)*(a+c*x^2)^n,x]] /;
FreeQ[{a,c,d,e,n},x] && RationalQ[m] && m>1 && NonzeroQ[m+2*n+1] && Not[IntegerQ[n] && n>=-1] *)


Int[(d_+e_.*x_)^m_*(a_+c_.*x_^2)^n_,x_Symbol] :=
  e*(d+e*x)^m*(a+c*x^2)^(n+1)/(2*c*d*(n+1)) /;
FreeQ[{a,c,d,e,n},x] && ZeroQ[c*d^2+a*e^2] && ZeroQ[m+2*(n+1)] && NonzeroQ[n+1]


Int[(d_+e_.*x_)^m_*(a_+c_.*x_^2)^n_,x_Symbol] :=
  -e*(d+e*x)^m*(a+c*x^2)^(n+1)/(2*c*d*(m+n+1)) +
  Dist[(m+2*(n+1))/(2*d*(m+n+1)),Int[(d+e*x)^(m+1)*(a+c*x^2)^n,x]] /;
FreeQ[{a,c,d,e,n},x] && RationalQ[m] && m<-1 && ZeroQ[c*d^2+a*e^2] && NonzeroQ[m+n+1]


(* ::Item:: *)
(*Reference: G&R 2.176, CRC 123*)


Int[(d_+e_.*x_)^m_*(a_+c_.*x_^2)^n_,x_Symbol] :=
  e*(d+e*x)^(m+1)*(a+c*x^2)^(n+1)/((m+1)*(c*d^2+a*e^2)) +
  Dist[2*c*d*(m+n+2)/((m+1)*(c*d^2+a*e^2)),Int[(d+e*x)^(m+1)*(a+c*x^2)^n,x]] /;
FreeQ[{a,c,d,e,n},x] && RationalQ[m] && m<-1 && NonzeroQ[c*d^2+a*e^2] && 
Not[IntegerQ[n] && n>=-1] && ZeroQ[m+2*n+3]


(* ::Item:: *)
(*Reference: G&R 2.176, CRC 123*)


(* Int[(d_+e_.*x_)^m_*(a_+c_.*x_^2)^n_,x_Symbol] :=
  e*(d+e*x)^(m+1)*(a+c*x^2)^(n+1)/((m+1)*(c*d^2+a*e^2)) +
  Dist[2*c*d*(m+n+2)/((m+1)*(c*d^2+a*e^2)),Int[(d+e*x)^(m+1)*(a+c*x^2)^n,x]] -
  Dist[c*(m+2*n+3)/((m+1)*(c*d^2+a*e^2)),Int[(d+e*x)^(m+2)*(a+c*x^2)^n,x]] /;
FreeQ[{a,c,d,e,n},x] && RationalQ[m] && m<-1 && NonzeroQ[c*d^2+a*e^2] && 
Not[IntegerQ[n] && n>=-1] && NonzeroQ[m+2*n+3] *)


(* ::Subsubsection::Closed:: *)
(*(d+e x)^m (a+b x+c x^2)^n		Products of powers of linears and powers of quadratic trinomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If a*e^2+c*d^2==b*d*e, a+b*x+c*x^2 == (d+e*x)*(a/d+c/e*x)*)


Int[(d_+e_.*x_)^m_.*(a_.+b_.*x_+c_.*x_^2)^n_.,x_Symbol] :=
  Int[(d+e*x)^(m+n)*(a/d+c/e*x)^n,x] /;
FreeQ[{a,b,c,d,e,m},x] && IntegerQ[n] && ZeroQ[a*e^2+c*d^2-b*d*e]


(* ::Item:: *)
(*Reference: G&R 2.174.2*)


Int[(d_.+e_.*x_)^m_*(a_.+b_.*x_+c_.*x_^2)^n_,x_Symbol] :=
  -e*(d+e*x)^(m-1)*(a+b*x+c*x^2)^(n+1)/(c*(m-1)) +
  Dist[e^2/c,Int[(d+e*x)^(m-2)*(a+b*x+c*x^2)^(n+1),x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[{m,n}] && n<-1 && ZeroQ[m+2*n+1] && ZeroQ[2*c*d-b*e]


(* ::Item:: *)
(*Reference: G&R 2.174.2*)


(* Int[(d_.+e_.*x_)^m_*(a_.+b_.*x_+c_.*x_^2)^n_,x_Symbol] :=
  -e*(d+e*x)^(m-1)*(a+b*x+c*x^2)^(n+1)/(c*(m-1)) +
  Dist[(2*c*d-b*e)/(2*c),Int[(d+e*x)^(m-1)*(a+b*x+c*x^2)^n,x]] +
  Dist[e^2/c,Int[(d+e*x)^(m-2)*(a+b*x+c*x^2)^(n+1),x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[{m,n}] && n<-1 && ZeroQ[m+2*n+1] *)


(* ::Item:: *)
(*Reference: G&R 2.174.1, CRC 119*)


Int[(d_.+e_.*x_)*(a_.+b_.*x_+c_.*x_^2)^n_,x_Symbol] :=
  e*(a+b*x+c*x^2)^(n+1)/(2*c*(n+1)) /;
FreeQ[{a,b,c,d,e,n},x] && ZeroQ[2*c*d-b*e] && NonzeroQ[n+1]


(* ::Item:: *)
(*Reference: G&R 2.174.1, CRC 119*)


Int[(d_.+e_.*x_)*(a_.+b_.*x_+c_.*x_^2)^n_,x_Symbol] :=
  e*(a+b*x+c*x^2)^(n+1)/(2*c*(n+1)) +
  Dist[(2*c*d-b*e)/(2*c),Int[(a+b*x+c*x^2)^n,x]] /;
FreeQ[{a,b,c,d,e,n},x] && NonzeroQ[n+1] && Not[IntegerQ[n] && n>0]


(* ::Item:: *)
(*Reference: G&R 2.174.1, CRC 119*)


(* Int[(d_.+e_.*x_)^m_.*(a_.+b_.*x_+c_.*x_^2)^n_,x_Symbol] :=
  e*(d+e*x)^(m-1)*(a+b*x+c*x^2)^(n+1)/(c*(2*n+m+1)) /;
FreeQ[{a,b,c,d,e,m,n},x] && ZeroQ[2*c*d-b*e] && NonzeroQ[2*n+m+1] && ZeroQ[b^2-4*a*c] *)


(* ::Item:: *)
(*Reference: G&R 2.174.1, CRC 119*)


Int[(d_.+e_.*x_)^m_*(a_.+b_.*x_+c_.*x_^2)^n_,x_Symbol] :=
  e*(d+e*x)^(m-1)*(a+b*x+c*x^2)^(n+1)/(c*(m+2*n+1)) -
  Dist[(e*(a*e-b*d)+c*d^2)*(m-1)/(c*(m+2*n+1)),Int[(d+e*x)^(m-2)*(a+b*x+c*x^2)^n,x]] /;
FreeQ[{a,b,c,d,e,n},x] && RationalQ[m] && m>1 && NonzeroQ[m+2*n+1] && Not[IntegerQ[n] && n>=-1] && 
(ZeroQ[m+n] || ZeroQ[2*c*d-b*e])


(* ::Item:: *)
(*Reference: G&R 2.174.1, CRC 119*)


Int[(d_.+e_.*x_)^m_.*(a_.+b_.*x_+c_.*x_^2)^n_,x_Symbol] :=
  e*(d+e*x)^(m-1)*(a+b*x+c*x^2)^(n+1)/(c*(m+2*n+1)) +
  Dist[(2*c*d-b*e)*(m+n)/(c*(m+2*n+1)),Int[(d+e*x)^(m-1)*(a+b*x+c*x^2)^n,x]] /;
FreeQ[{a,b,c,d,e,n},x] && RationalQ[m] && m>1 && NonzeroQ[m+2*n+1] && Not[IntegerQ[n] && n>=-1] &&
ZeroQ[e*(a*e-b*d)/c+d^2]


(* ::Item:: *)
(*Reference: G&R 2.174.1, CRC 119*)


(* Int[(d_.+e_.*x_)^m_*(a_.+b_.*x_+c_.*x_^2)^n_,x_Symbol] :=
  e*(d+e*x)^(m-1)*(a+b*x+c*x^2)^(n+1)/(c*(m+2*n+1)) +
  Dist[(2*c*d-b*e)*(m+n)/(c*(m+2*n+1)),Int[(d+e*x)^(m-1)*(a+b*x+c*x^2)^n,x]] -
  Dist[(e*(a*e-b*d)/c+d^2)*(m-1)/(m+2*n+1),Int[(d+e*x)^(m-2)*(a+b*x+c*x^2)^n,x]] /;
FreeQ[{a,b,c,d,e,n},x] && RationalQ[m] && m>1 && NonzeroQ[m+2*n+1] && Not[IntegerQ[n] && n>=-1] *)


(* ::Item:: *)
(*Reference: G&R 2.265c*)


Int[x_^m_*(b_.*x_+c_.*x_^2)^n_,x_Symbol] :=
  x^m*(b*x+c*x^2)^(n+1)/(b*(m+n+1)) /;
FreeQ[{b,c,m,n},x] && NonzeroQ[m+n+1] && ZeroQ[m+2*(n+1)]


(* ::Item:: *)
(*Reference: G&R 2.265c*)


Int[x_^m_*(b_.*x_+c_.*x_^2)^n_,x_Symbol] :=
  x^m*(b*x+c*x^2)^(n+1)/(b*(m+n+1)) - 
  Dist[c*(m+2*(n+1))/(b*(m+n+1)),Int[x^(m+1)*(b*x+c*x^2)^n,x]] /;
FreeQ[{b,c,n},x] && RationalQ[m] && m<-1 && NonzeroQ[m+n+1] && Not[IntegerQ[n] && n>=-1]


(* ::Item:: *)
(*Reference: G&R 2.176, CRC 123*)


Int[(d_.+e_.*x_)^m_*(a_+b_.*x_+c_.*x_^2)^n_,x_Symbol] :=
  e*(d+e*x)^(m+1)*(a+b*x+c*x^2)^(n+1)/((m+1)*(c*d^2+a*e^2-b*d*e)) +
  Dist[(2*c*d-b*e)*(m+n+2)/((m+1)*(c*d^2+a*e^2-b*d*e)),Int[(d+e*x)^(m+1)*(a+b*x+c*x^2)^n,x]] /;
FreeQ[{a,b,c,d,e,n},x] && RationalQ[m] && m<-1 && NonzeroQ[c*d^2+a*e^2-b*d*e] && 
Not[IntegerQ[n] && n>=-1] && ZeroQ[m+2*n+3]


(* ::Item:: *)
(*Reference: G&R 2.176, CRC 123*)


(* Int[(d_.+e_.*x_)^m_*(a_+b_.*x_+c_.*x_^2)^n_,x_Symbol] :=
  e*(d+e*x)^(m+1)*(a+b*x+c*x^2)^(n+1)/((m+1)*(c*d^2+a*e^2-b*d*e)) +
  Dist[(2*c*d-b*e)*(m+n+2)/((m+1)*(c*d^2+a*e^2-b*d*e)),Int[(d+e*x)^(m+1)*(a+b*x+c*x^2)^n,x]] -
  Dist[c*(m+2*n+3)/((m+1)*(c*d^2+a*e^2-b*d*e)),Int[(d+e*x)^(m+2)*(a+b*x+c*x^2)^n,x]] /;
FreeQ[{a,b,c,d,e,n},x] && RationalQ[m] && m<-1 && NonzeroQ[c*d^2+a*e^2-b*d*e] && 
Not[IntegerQ[n] && n>=-1] && NonzeroQ[m+2*n+3] *)


(* ::Subsection::Closed:: *)
(*a + b x^k + c x^(2k)		Integrands involving symmetric trinomials*)


(* ::Subsubsection::Closed:: *)
(*(d+e x^2)/(a+b x^2+c x^4)			Quotients of binomials by quartic trinomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If b^2-4*a*c=0, a+b*z^2+c*z^4 == (b/2+c*z^2)^2/c*)


Int[u_./(a_+b_.*x_^2+c_.*x_^4), x_Symbol] :=
  Dist[c,Int[u/(b/2+c*x^2)^2,x]] /;
FreeQ[{a,b,c},x] && ZeroQ[b^2-4*a*c] && PolynomialQ[u,x]


(* Previously undiscovered rules ??? *)


Int[(d_+e_.*x_^2)/(a_+b_.*x_^2+c_.*x_^4), x_Symbol] :=
  d/(a*Rt[(d*b+2*e*a)/(d*a),2])*ArcTan[d*Rt[(d*b+2*e*a)/(d*a),2]*x/(d-e*x^2)] /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[d^2*c-e^2*a] && PosQ[(d*b+2*e*a)/(d*a)]


Int[(d_+e_.*x_^2)/(a_+b_.*x_^2+c_.*x_^4), x_Symbol] :=
  d/(a*Rt[-(d*b+2*e*a)/(d*a),2])*ArcTanh[d*Rt[-(d*b+2*e*a)/(d*a),2]*x/(d-e*x^2)] /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[d^2*c-e^2*a] && NegQ[(d*b+2*e*a)/(d*a)]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If q=Sqrt[a/c], then 1/(a+b*x^2+c*x^4) == c*q*(q+x^2)/(2*a*(a+b*x^2+c*x^4)) + c*q*(q-x^2)/(2*a*(a+b*x^2+c*x^4))*)


Int[1/(a_+b_.*x_^2+c_.*x_^4), x_Symbol] :=
  Module[{q=Rt[a/c,2]},
  Dist[c*q/(2*a),Int[(q+x^2)/(a+b*x^2+c*x^4),x]] +
  Dist[c*q/(2*a),Int[(q-x^2)/(a+b*x^2+c*x^4),x]]] /;
FreeQ[{a,b,c},x] && PosQ[a/c] &&
(NegativeQ[b^2-4*a*c] || RationalQ[a/c] && Not[PositiveQ[b^2-4*a*c]])


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If q=Sqrt[-a/c], then 1/(a+b*x^2+c*x^4) == -c*q/(2*a)*(q+x^2)/(a+b*x^2+c*x^4) - c*q/(2*a)*(q-x^2)/(a+b*x^2+c*x^4)*)


Int[1/(a_+b_.*x_^2+c_.*x_^4), x_Symbol] :=
  Module[{q=Rt[-a/c,2]},
  -Dist[c*q/(2*a),Int[(q+x^2)/(a+b*x^2+c*x^4),x]] -
  Dist[c*q/(2*a),Int[(q-x^2)/(a+b*x^2+c*x^4),x]]] /;
FreeQ[{a,b,c},x] && NegQ[a/c] &&
(NegativeQ[b^2-4*a*c] || RationalQ[a/c] && Not[PositiveQ[b^2-4*a*c]])


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If q=Sqrt[a/c], then x^2/(a+b*x^2+c*x^4) == (q+x^2)/(2*(a+b*x^2+c*x^4)) - (q-x^2)/(2*(a+b*x^2+c*x^4))*)


Int[x_^2/(a_+b_.*x_^2+c_.*x_^4), x_Symbol] :=
  Module[{q=Rt[a/c,2]},
  Dist[1/2,Int[(q+x^2)/(a+b*x^2+c*x^4),x]] -
  Dist[1/2,Int[(q-x^2)/(a+b*x^2+c*x^4),x]]] /;
FreeQ[{a,b,c},x] && PosQ[a/c] && 
(NegativeQ[b^2-4*a*c] || RationalQ[a/c] && Not[PositiveQ[b^2-4*a*c]])


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If q=Sqrt[-a/c], then x^2/(a+b*x^2+c*x^4) == (q+x^2)/(2*(a+b*x^2+c*x^4)) - (q-x^2)/(2*(a+b*x^2+c*x^4))*)


Int[x_^2/(a_+b_.*x_^2+c_.*x_^4), x_Symbol] :=
  Module[{q=Rt[-a/c,2]},
  Dist[1/2,Int[(q+x^2)/(a+b*x^2+c*x^4),x]] -
  Dist[1/2,Int[(q-x^2)/(a+b*x^2+c*x^4),x]]] /;
FreeQ[{a,b,c},x] && NegQ[a/c] &&
(NegativeQ[b^2-4*a*c] || RationalQ[a/c] && Not[PositiveQ[b^2-4*a*c]])


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If q=Sqrt[a/c], then (d+e*x^2)/(a+b*x^2+c*x^4) == *)
(*	(q*c*d+a*e)/(2*a)*(q+x^2)/(a+b*x^2+c*x^4) + (q*c*d-a*e)/(2*a)*(q-x^2)/(a+b*x^2+c*x^4)*)


Int[(d_+e_.*x_^2)/(a_+b_.*x_^2+c_.*x_^4), x_Symbol] :=
  Module[{q=Rt[a/c,2]},
  Dist[(q*c*d+a*e)/(2*a),Int[(q+x^2)/(a+b*x^2+c*x^4),x]] +
  Dist[(q*c*d-a*e)/(2*a),Int[(q-x^2)/(a+b*x^2+c*x^4),x]]] /;
FreeQ[{a,b,c,d,e},x] && NonzeroQ[d^2*c-e^2*a] && PosQ[a/c] &&
(NegativeQ[b^2-4*a*c] || RationalQ[a/c] && Not[PositiveQ[b^2-4*a*c]])


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If q=Sqrt[-a/c], then (d+e*x^2)/(a+b*x^2+c*x^4) == *)
(*	-(q*c*d-a*e)/(2*a)*(q+x^2)/(a+b*x^2+c*x^4) - (q*c*d+a*e)/(2*a)*(q-x^2)/(a+b*x^2+c*x^4)*)


Int[(d_+e_.*x_^2)/(a_+b_.*x_^2+c_.*x_^4), x_Symbol] :=
  Module[{q=Rt[-a/c,2]},
  Dist[-(q*c*d-a*e)/(2*a),Int[(q+x^2)/(a+b*x^2+c*x^4),x]] -
  Dist[(q*c*d+a*e)/(2*a),Int[(q-x^2)/(a+b*x^2+c*x^4),x]]] /;
FreeQ[{a,b,c,d,e},x] && NonzeroQ[d^2*c-e^2*a] && NegQ[a/c] &&
(NegativeQ[b^2-4*a*c] || RationalQ[a/c] && Not[PositiveQ[b^2-4*a*c]])


(* ::Subsubsection::Closed:: *)
(*x^m (a + b x^2)/(c + d x^n + e x^k + f x^j)		Quotients of binomials by quartic trinomials*)


Int[(a_+b_.*x_^k_)/(c_+d_.*x_^2+e_.*x_^k_+f_.*x_^j_), x_Symbol] :=
  a/Rt[c*d,2]*ArcTan[a*(k-1)*Rt[c*d,2]*x/(c*(a*(k-1)-b*x^k))] /;
FreeQ[{a,b,c,d,e,f},x] && IntegerQ[{j,k}] && k>0 && j==2*k && ZeroQ[(k-1)^2*a^2*f-b^2*c] && 
ZeroQ[b*e+2*a*(k-1)*f] && PosQ[c*d]


Int[(a_+b_.*x_^k_)/(c_+d_.*x_^2+e_.*x_^k_+f_.*x_^j_), x_Symbol] :=
  a/Rt[-c*d,2]*ArcTanh[a*(k-1)*Rt[-c*d,2]*x/(c*(a*(k-1)-b*x^k))] /;
FreeQ[{a,b,c,d,e,f},x] && IntegerQ[{j,k}] && k>0 && j==2*k && ZeroQ[(k-1)^2*a^2*f-b^2*c] && 
ZeroQ[b*e+2*a*(k-1)*f] && NegQ[c*d]


Int[x_^m_.*(a_+b_.*x_^k_.)/(c_+d_.*x_^n_.+e_.*x_^k_.+f_.*x_^j_), x_Symbol] :=
  a*ArcTan[a*(m-k+1)*Rt[c*d,2]*x^(m+1)/(c*(a*(m-k+1)+b*(m+1)*x^k))]/((m+1)*Rt[c*d,2]) /;
FreeQ[{a,b,c,d,e,f,j,k,m,n},x] && ZeroQ[n-2*(m+1)] && ZeroQ[j-2*k] &&
ZeroQ[a^2*f*(m-k+1)^2-b^2*c*(m+1)^2] && ZeroQ[b*e*(m+1)-2*a*f*(m-k+1)] && PosQ[c*d]


Int[x_^m_.*(a_+b_.*x_^k_.)/(c_+d_.*x_^n_.+e_.*x_^k_.+f_.*x_^j_), x_Symbol] :=
  a*ArcTanh[a*(m-k+1)*Rt[-c*d,2]*x^(m+1)/(c*(a*(m-k+1)+b*(m+1)*x^k))]/((m+1)*Rt[-c*d,2]) /;
FreeQ[{a,b,c,d,e,f,j,k,m,n},x] && ZeroQ[n-2*(m+1)] && ZeroQ[j-2*k] &&
ZeroQ[a^2*f*(m-k+1)^2-b^2*c*(m+1)^2] && ZeroQ[b*e*(m+1)-2*a*f*(m-k+1)] && NegQ[c*d]


(* ::Subsubsection::Closed:: *)
(*(a+b x^k+c x^(2k))^n			Powers of symmetric trinomials*)


(* ::Item:: *)
(*Reference: G&R 2.161.1b?*)


Int[1/(a_+b_.*x_^k_+c_.*x_^j_),x_Symbol] :=
  Module[{q=2*Rt[a/c,2]-b/c},
  Dist[1/(2*c*Rt[a/c,2]*Rt[q,2]),Int[(Rt[q,2]+x^(k/2))/(Rt[a/c,2]+Rt[q,2]*x^(k/2)+x^k),x]] + 
  Dist[1/(2*c*Rt[a/c,2]*Rt[q,2]),Int[(Rt[q,2]-x^(k/2))/(Rt[a/c,2]-Rt[q,2]*x^(k/2)+x^k),x]] /;
 Not[NegativeQ[q]]] /;  
FreeQ[{a,b,c},x] && IntegerQ[{j,k}] && k>0 && j==2*k && EvenQ[k] && PosQ[a/c] && 
NegativeQ[b^2-4*a*c]


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
FreeQ[{a,b,c},x] && IntegerQ[{j,k}] && k>0 && j==2*k && NonzeroQ[b^2-4*a*c] && 
(OddQ[k] || Not[NegativeQ[b^2-4*a*c]])


(* ::Item:: *)
(*Reference: G&R 2.161.5' (GR5 2.161.4 is a special case.)*)


(* Previously undiscovered rule ??? *)
Int[(a_+b_.*x_^k_+c_.*x_^j_.)^n_,x_Symbol] :=
  -x*(b^2-2*a*c+b*c*x^k)*(a+b*x^k+c*x^j)^(n+1)/(k*a*(n+1)*(b^2-4*a*c)) +
  Dist[(k*(n+1)*(b^2-4*a*c)+b^2-2*a*c)/(k*a*(n+1)*(b^2-4*a*c)),Int[(a+b*x^k+c*x^j)^(n+1),x]] +
  Dist[(k*(2*n+3)+1)*b*c/(k*a*(n+1)*(b^2-4*a*c)),Int[x^k*(a+b*x^k+c*x^j)^(n+1),x]] /;
FreeQ[{a,b,c},x] && IntegerQ[{j,k,n}] && k>0 && j==2*k && NonzeroQ[b^2-4*a*c] && n<-1


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
FreeQ[{a,b,c},x] && IntegerQ[{j,k}] && k>0 && j==2*k && NonzeroQ[b^2-4*a*c]


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


Int[x_^m_./(a_+b_.*x_^k_+c_.*x_^j_),x_Symbol] :=
  Module[{q=2*Rt[a/c,2]-b/c},
  Dist[1/(2*c*Rt[q,2]),Int[x^(m-k/2)/(Rt[a/c,2]-Rt[q,2]*x^(k/2)+x^k),x]] -
  Dist[1/(2*c*Rt[q,2]),Int[x^(m-k/2)/(Rt[a/c,2]+Rt[q,2]*x^(k/2)+x^k),x]] /;
 PosQ[q]] /;  
FreeQ[{a,b,c},x] && IntegerQ[{j,k,m}] && k>0 && j==2*k && EvenQ[m] && EvenQ[k] && 
0<k/2<=m<j && PosQ[a/c] && NegativeQ[b^2-4*a*c]


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
FreeQ[{a,b,c},x] && IntegerQ[{j,k}] && k>0 && j==2*k && NonzeroQ[b^2-4*a*c] && 
(OddQ[k] || Not[NegativeQ[b^2-4*a*c]])


(* ::Item:: *)
(*Reference: G&R 2.174.1', CRC 119'*)


Int[x_^m_./(a_+b_.*x_^k_+c_.*x_^j_),x_Symbol] :=
  x^(m-j+1)/(c*(m-j+1))-
  Dist[1/c,Int[x^(m-j)*(a+b*x^k)/(a+b*x^k+c*x^(2k)),x]] /;
FreeQ[{a,b,c},x] && IntegerQ[{j,k,m}] && k>0 && j==2*k && NonzeroQ[b^2-4*a*c] && 0<j<m+1


(* ::Item:: *)
(*Reference: G&R 2.176', CRC 123'*)


Int[x_^m_/(a_+b_.*x_^k_+c_.*x_^j_),x_Symbol] :=
  x^(m+1)/(a*(m+1)) - 
  Dist[1/a,Int[x^(m+k)*(b+c*x^k)/(a+b*x^k+c*x^j),x]] /;
FreeQ[{a,b,c},x] && IntegerQ[{j,k,m}] && k>0 && j==2*k && NonzeroQ[b^2-4*a*c] && m<-1 && k>0


(* ::Subsubsection::Closed:: *)
(*x^m (a+b x^k+c x^(2k))^n		Products of monomials and powers of symmetric trinomials*)


(* ::Item:: *)
(*Derivation: Integration by substitution*)


(* Int[x_^m_./(a_+b_.*x_^k_+c_.*x_^j_.),x_Symbol] :=
  Module[{g=GCD[m+1,k]},
  Dist[1/g,Subst[Int[x^((m+1)/g-1)/(a+b*x^(k/g)+c*x^(j/g)),x],x,x^g]] /;
 g>1] /;
FreeQ[{a,b,c},x] && IntegerQ[{m,k,j}] && j==2*k && 0<m<j *)


(* ::Item:: *)
(*Derivation: Integration by substitution*)


Int[x_^m_.*(a_+b_.*x_^k_+c_.*x_^j_)^n_,x_Symbol] :=
  Dist[1/k,Subst[Int[x^((m+1)/k-1)*(a+b*x+c*x^2)^n,x],x,x^k]] /;
FreeQ[{a,b,c},x] && IntegerQ[{j,k,m,n}] && k>0 && j==2*k && m>0 && n<0 && IntegerQ[(m+1)/k] && 
NonzeroQ[b^2-4*a*c]


(* Int[(a_+b_.*x_^k_+c_.*x_^j_.)^n_,x_Symbol] :=
  Int[(b/2+c*x^k)^(2*n),x]/c^n /;
FreeQ[{a,b,c},x] && IntegerQ[{j,k,n}] && k>0 && j==2*k && ZeroQ[b^2-4*a*c] *)


(* ::Item:: *)
(*Reference: G&R 2.160.4*)


Int[x_^m_*(a_+b_.*x_^k_+c_.*x_^j_.)^n_,x_Symbol] :=
  x^(m+1)*(a+b*x^k+c*x^j)^n/(m+j*n+1) +
  Dist[a*j*n/(m+j*n+1),Int[x^m*(a+b*x^k+c*x^j)^(n-1),x]] +
  Dist[b*k*n/(m+j*n+1),Int[x^(m+k)*(a+b*x^k+c*x^j)^(n-1),x]] /;
FreeQ[{a,b,c},x] && IntegerQ[{j,k,m,n}] && k>0 && j==2*k && m<-1 && n>1 && NonzeroQ[m+j*n+1]


(* ::Item:: *)
(*Reference: G&R 2.160.3'*)


Int[x_^m_*(a_+b_.*x_^k_+c_.*x_^j_.)^n_,x_Symbol] :=
  x^(m-j+1)*(a+b*x^k+c*x^j)^(n+1)/(c*k*(n+1)) +
  Dist[a/c,Int[x^(m-j)*(a+b*x^k+c*x^j)^n,x]] /;
FreeQ[{a,b,c},x] && IntegerQ[{j,k,m,n}] && k>0 && j==2*k && m-j>=0 && n<-1 && ZeroQ[m+k*(n-1)+1]


(* ::Item:: *)
(*Reference: G&R 2.160.3 (GR5 2.174.1 is a special case.)*)


Int[x_^m_*(a_+b_.*x_^k_+c_.*x_^j_.)^n_,x_Symbol] :=
  x^(m-j+1)*(a+b*x^k+c*x^j)^(n+1)/(c*(m+j*n+1)) -
  Dist[b*(m+k*(n-1)+1)/(c*(m+j*n+1)),Int[x^(m-k)*(a+b*x^k+c*x^j)^n,x]] -
  Dist[a*(m-j+1)/(c*(m+j*n+1)),Int[x^(m-j)*(a+b*x^k+c*x^j)^n,x]] /;
FreeQ[{a,b,c},x] && IntegerQ[{j,k,m,n}] && k>0 && j==2*k && m-j>=0 && n<-1 && NonzeroQ[m+j*n+1] &&
NonzeroQ[m+k*(n-1)+1]


(* ::Item:: *)
(*Reference: G&R 2.160.2*)


Int[x_^m_*(a_+b_.*x_^k_+c_.*x_^j_.)^n_,x_Symbol] :=
  x^(m+1)*(a+b*x^k+c*x^j)^n/(m+1) -
  Dist[b*k*n/(m+1),Int[x^(m+k)*(a+b*x^k+c*x^j)^(n-1),x]] -
  Dist[c*j*n/(m+1),Int[x^(m+j)*(a+b*x^k+c*x^j)^(n-1),x]] /;
FreeQ[{a,b,c},x] && IntegerQ[{j,k,m,n}] && k>0 && j==2*k && m<-1 && n>1


(* ::Item:: *)
(*Reference: G&R 2.160.1 (GR5 2.161.6 is a special case.)*)


Int[x_^m_*(a_+b_.*x_^k_+c_.*x_^j_.)^n_,x_Symbol] :=
  x^(m+1)*(a+b*x^k+c*x^j)^(n+1)/(a*(m+1)) -
  Dist[b*(m+1+k*(n+1))/(a*(m+1)),Int[x^(m+k)*(a+b*x^k+c*x^j)^n,x]] -
  Dist[c*(m+1+j*(n+1))/(a*(m+1)),Int[x^(m+j)*(a+b*x^k+c*x^j)^n,x]] /;
FreeQ[{a,b,c},x] && IntegerQ[{j,k,m,n}] && k>0 && j==2*k && m<-1 && n<=1


(* Previously undiscovered rules ??? *)
Int[x_^k_*(a_+b_.*x_^k_+c_.*x_^j_.)^n_,x_Symbol] :=
  x*(b+2*c*x^k)*(a+b*x^k+c*x^j)^n/(2*c*(k*(2*n+1)+1)) - 
  Dist[b/(2*c*(k*(2*n+1)+1)),Int[(a+b*x^k+c*x^j)^n, x]] - 
  Dist[k*n*(b^2-4*a*c)/(2*c*(k*(2*n+1)+1)),Int[x^k*(a+b*x^k+c*x^j)^(n-1), x]] /;
FreeQ[{a,b,c},x] && IntegerQ[{j,k,n}] && k>0 && j==2*k && n>0 && NonzeroQ[b^2-4*a*c] &&
NonzeroQ[k*(2*n+1)+1]


Int[x_^k_*(a_+b_.*x_^k_+c_.*x_^j_.)^n_,x_Symbol] :=
  x*(b+2*c*x^k)*(a+b*x^k+c*x^j)^(n+1)/(k*(n+1)*(b^2-4*a*c)) - 
  Dist[b/(k*(n+1)*(b^2-4*a*c)),Int[(a+b*x^k+c*x^j)^(n+1),x]] -
  Dist[2*c*(k*(2*n+3)+1)/(k*(n+1)*(b^2-4*a*c)),Int[x^k*(a+b*x^k+c*x^j)^(n+1),x]] /;
FreeQ[{a,b,c},x] && IntegerQ[{j,k,n}] && k>0 && j==2*k && n<-1 && NonzeroQ[b^2-4*a*c]


(* ::Subsubsection::Closed:: *)
(*x^m (d+e x^k) / (a+b x^k+c x^(2k))	Products of monomials and quotients of binomials by symmetric trinomials*)


(* These way kool, and to my knowledge original, rules reduce the degree of monomial without
	increasing the complexity of the integrands. *)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: (d+e*z)/(a+b*z+c*z^2) == (e+(2*c*d-b*e)/q)/(b-q+2*c*z) + (e-(2*c*d-b*e)/q)/(b+q+2*c*z) where q=Sqrt[b^2-4*a*c]*)


Int[(d_+e_.*x_^k_)/(a_+b_.*x_^k_+c_.*x_^j_.),x_Symbol] :=
  Module[{q=Rt[b^2-4*a*c,2]},
  Dist[(e+(2*c*d-b*e)/q),Int[1/(b-q+2*c*x^k),x]] +
  Dist[(e-(2*c*d-b*e)/q),Int[1/(b+q+2*c*x^k),x]]] /;
FreeQ[{a,b,c,d,e},x] && IntegerQ[{j,k}] && k>0 && j==2*k && NonzeroQ[b^2-4*a*c] && 
Not[NegativeQ[b^2-4*a*c]]


(* Note: This rule does not use the obvious substitution u=x^k on the whole integrand reducing 
	it to (d+e*x)/(x*(a+b*x+c*x^2)) so that Log[x] instead of Log[x^k] appears in the result *)
Int[(d_.+e_.*x_^k_)/(x_*(a_+b_.*x_^k_+c_.*x_^j_.)),x_Symbol] :=
(* Dist[1/a,Int[x^(k-1)*(b*d-a*e+c*d*x^k)/(a+b*x^k+c*x^j),x]] /; *)
  d*Log[x]/a -
  Dist[1/(a*k),Subst[Int[(b*d-a*e+c*d*x)/(a+b*x+c*x^2),x],x,x^k]] /;
FreeQ[{a,b,c,d,e},x] && IntegerQ[{j,k}] && k>0 && j==2*k


Int[x_^m_.*(d_.+e_.*x_^k_)/(a_+b_.*x_^k_+c_.*x_^j_.),x_Symbol] :=
  e*x^(m-k+1)/(c*(m-k+1)) -
  Dist[1/c,Int[x^(m-k)*(a*e+(b*e-c*d)*x^k)/(a+b*x^k+c*x^j),x]] /;
FreeQ[{a,b,c,d,e},x] && IntegerQ[{j,k,m}] && k>0 && j==2*k && k<m+1


Int[x_^m_*(d_.+e_.*x_^k_)/(a_+b_.*x_^k_+c_.*x_^j_.),x_Symbol] :=
  d*x^(m+1)/(a*(m+1)) -
  Dist[1/a,Int[x^(m+k)*(b*d-a*e+c*d*x^k)/(a+b*x^k+c*x^j),x]] /;
FreeQ[{a,b,c,d,e},x] && IntegerQ[{j,k,m}] && k>0 && j==2*k && m<-1


(* ::Subsection::Closed:: *)
(*a + b x + c x^2 + b x^3 + a x^4  Integrands involving symmetric quartic polynomials*)


(* ::Subsubsection::Closed:: *)
(*(d+e x+f x^2+g x^3)/(a+b x+c x^2+b x^3+a x^4)	Quotients of binomials by quartic trinomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If q=Sqrt[8*a^2+b^2-4*a*c], then a+b*x+c*x^2+b*x^3+a*x^4 == a*(1+((b-q)*x)/(2*a)+x^2)*(1+((b+q)*x)/(2*a)+x^2)*)


(* ::Item:: *)
(*Basis: If q=Sqrt[8*a^2+b^2-4*a*c], then (d+e*x+f*x^2+g*x^3)/(a+b*x+c*x^2+b*x^3+a*x^4) == *)
(*	(b*d-2*a*e+2*a*g+d*q+(2*a*d-2*a*f+b*g+g*q)*x)/(q*(2*a+(b+q)*x+2*a*x^2)) - *)
(*	(b*d-2*a*e+2*a*g-d*q+(2*a*d-2*a*f+b*g-g*q)*x)/(q*(2*a+(b-q)*x+2*a*x^2))*)


Int[(d_.+e_.*x_+f_.*x_^2+g_.*x_^3)/(a_+b_.*x_+c_.*x_^2+b_.*x_^3+a_.*x_^4),x_Symbol] :=
  Module[{q=Sqrt[8*a^2+b^2-4*a*c]},
  Dist[1/q,Int[(b*d-2*a*e+2*a*g+d*q+(2*a*d-2*a*f+b*g+g*q)*x)/(2*a+(b+q)*x+2*a*x^2),x]] -
  Dist[1/q,Int[(b*d-2*a*e+2*a*g-d*q+(2*a*d-2*a*f+b*g-g*q)*x)/(2*a+(b-q)*x+2*a*x^2),x]]] /;
FreeQ[{a,b,c,d,e,f,g},x] && PosQ[8*a^2+b^2-4*a*c]


Int[(d_.+e_.*x_+g_.*x_^3)/(a_+b_.*x_+c_.*x_^2+b_.*x_^3+a_.*x_^4),x_Symbol] :=
  Module[{q=Sqrt[8*a^2+b^2-4*a*c]},
  Dist[1/q,Int[(b*d-2*a*e+2*a*g+d*q+(2*a*d+b*g+g*q)*x)/(2*a+(b+q)*x+2*a*x^2),x]] -
  Dist[1/q,Int[(b*d-2*a*e+2*a*g-d*q+(2*a*d+b*g-g*q)*x)/(2*a+(b-q)*x+2*a*x^2),x]]] /;
FreeQ[{a,b,c,d,e,g},x] && PosQ[8*a^2+b^2-4*a*c]


(* ::Subsection::Closed:: *)
(*a x^p + b x^q			Integrands involving nonnormal binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: a z^p+b z^q == z^p (a+b z^(q-p))*)


Int[(a_.*x_^p_.+b_.*x_^q_.)^n_,x_Symbol] :=
  Int[x^(n*p)*(a+b*x^(q-p))^n,x] /;
FreeQ[{a,b,p,q},x] && IntegerQ[n] && Not[FractionQ[p]] && Not[FractionQ[q]] && Not[NegativeQ[q-p]]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: a z^p+b z^q == z^p (a+b z^(q-p))*)


Int[x_^m_.*(a_.*x_^p_.+b_.*x_^q_.)^n_,x_Symbol] :=
  Int[x^(m+n*p)*(a+b*x^(q-p))^n,x] /;
FreeQ[{a,b,m,p,q},x] && IntegerQ[n] && Not[FractionQ[p]] && Not[FractionQ[q]] && Not[FractionQ[m]] && Not[NegativeQ[q-p]]


(* ::Subsection::Closed:: *)
(*P(x)/Q(x)^n			Quotients of polynomials and powers of polynomials*)


(* Note: Finds one term of the rational part of the antiderivative, thereby reducing the degree 
	of the polynomial in the numerator of the integrand.  Equivalent to the Ostrogradskiy-Hermite
	method (GR5 2.104) but without the need to solve a system of linear equations. *)
(* If m+1>=n and m+1-n*p!=0, let c=pm/(qn*(m+1-n*p)), then Int[Pm[x]/Qn[x]^p,x] -->
	c*x^(m-n+1)/Qn[x]^(p-1)+
		Int[(Pm[x]-c*x^(m-n)*((m-n+1)*Qn[x]+(1-p)*x*D[Qn[x],x]))/Qn[x]^p,x] *)
(* Integrate[Sum[ai*(m+i-i*p)*x^(m+i-1),{i,0,n}]/Sum[ai*x^i,{i,0,n}]^p,x] == 
		x^m/Sum[ai*x^i,{i,0,n}]^(p-1) *)
(* Note: Requirement that m<2*n-1 ensures new term is a proper fraction. *)
If[ShowSteps,

Int[u_*v_^p_,x_Symbol] :=
  Module[{m=Exponent[u,x],n=Exponent[v,x]},
  Module[{c=Coefficient[u,x,m]/(Coefficient[v,x,n]*(m+1+n*p)),w},
  w=Apart[u-c*x^(m-n)*((m-n+1)*v+(p+1)*x*D[v,x]),x];
  If[ZeroQ[w],
    ShowStep["If p>1, m+1>=n>1, and m-n*p<-1, let c=pm/(qn*(m+1-n*p)), then if (Pm[x]-c*x^(m-n)*((m-n+1)*Qn[x]+(1-p)*x*D[Qn[x],x]))==0,",
	  "Int[Pm[x]/Qn[x]^p,x]", "c*x^(m-n+1)/Qn[x]^(p-1)",
      Hold[c*x^(m-n+1)*v^(p+1)]],
  ShowStep["If p>1, m+1>=n>1, and m-n*p<-1, let c=pm/(qn*(m+1-n*p)), then",
	"Int[Pm[x]/Qn[x]^p,x]",
	"c*x^(m-n+1)/Qn[x]^(p-1)+Int[(Pm[x]-c*x^(m-n)*((m-n+1)*Qn[x]+(1-p)*x*D[Qn[x],x]))/Qn[x]^p,x]",
	Hold[c*x^(m-n+1)*v^(p+1) + Int[w*v^p,x]]]]] /;
 m+1>=n>1 && m+n*p<-1 && FalseQ[DerivativeDivides[v,u,x]]] /;
SimplifyFlag && RationalQ[p] && p<-1 && PolynomialQ[{u,v},x] && SumQ[v] && 
Not[MonomialQ[u,x] && BinomialQ[v,x]] && 
Not[ZeroQ[Coefficient[u,x,0]] && ZeroQ[Coefficient[v,x,0]]],

Int[u_*v_^p_,x_Symbol] :=
  Module[{m=Exponent[u,x],n=Exponent[v,x]},
  Module[{c=Coefficient[u,x,m]/(Coefficient[v,x,n]*(m+1+n*p)),w},
  c=Coefficient[u,x,m]/(Coefficient[v,x,n]*(m+1+n*p));
  w=Apart[u-c*x^(m-n)*((m-n+1)*v+(p+1)*x*D[v,x]),x];
  If[ZeroQ[w],
    c*x^(m-n+1)*v^(p+1),
  c*x^(m-n+1)*v^(p+1) + Int[w*v^p,x]]] /;
 m+1>=n>1 && m+n*p<-1 && FalseQ[DerivativeDivides[v,u,x]]] /;
RationalQ[p] && p<-1 && PolynomialQ[{u,v},x] && SumQ[v] && 
Not[MonomialQ[u,x] && BinomialQ[v,x]] && 
Not[ZeroQ[Coefficient[u,x,0]] && ZeroQ[Coefficient[v,x,0]]]]


(* ::Subsection::Closed:: *)
(*u + v				Sums*)


(* ::Item:: *)
(*Reference: G&R 2.02.5*)


Int[f_'[u_]*g_[v_]*w_. + f_[u_]*g_'[v_]*t_.,x_Symbol] :=
  f[u]*g[v] /;
FreeQ[{f,g},x] && ZeroQ[D[u,x]-w] && ZeroQ[D[v,x]-t]


(* ::Item::Closed:: *)
(*Reference: G&R 2.02.2, CRC 2,4*)


(* ::Item:: *)
(*Basis: Int[a*u+b*v+...,x] == a*Int[u,x]+b*Int[v,x]+...*)


If[ShowSteps,

Int[u_,x_Symbol] :=
  If[SplitFreeTerms[u,x][[1]]===0,
    ShowStep["","Int[a*u+b*v+\[CenterEllipsis],x]","a*Int[u,x]+b*Int[v,x]+\[CenterEllipsis]",Hold[
    SplitFreeIntegrate[u,x]]],
  ShowStep["","Int[a+b*u+c*v+\[CenterEllipsis],x]","a*x+b*Int[u,x]+c*Int[v,x]+\[CenterEllipsis]",Hold[
  SplitFreeIntegrate[u,x]]]] /;
SimplifyFlag && SumQ[u],

Int[u_,x_Symbol] :=
  SplitFreeIntegrate[u,x] /;
SumQ[u]]


(* ::Item:: *)
(*Basis: Int[a*u,x] == a*Int[u,x]*)


SplitFreeIntegrate[u_,x_Symbol] :=
  If[SumQ[u],
    Map[Function[SplitFreeIntegrate[#,x]],u],
  If[FreeQ[u,x],
    u*x,
  If[MatchQ[u,c_*(a_+b_.*x) /; FreeQ[{a,b,c},x]],
    Int[u,x],
  Module[{lst=SplitFreeFactors[u,x]},
  Dist[lst[[1]], Int[lst[[2]],x]]]]]]
