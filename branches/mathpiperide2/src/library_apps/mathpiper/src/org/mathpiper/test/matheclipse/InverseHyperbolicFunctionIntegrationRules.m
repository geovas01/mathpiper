(* ::Package:: *)

(* ::Title:: *)
(*Inverse Hyperbolic Function Integration Rules*)


(* ::Subsection::Closed:: *)
(*Hyperbolic Arcsine Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*ArcSinh[a+b x]^n			Powers of arcsines of linear binomials*)


(* ::Item::Closed:: *)
(*Reference: CRC 579, A&S 4.6.43*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcSinh[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*ArcSinh[a+b*x]/b - Sqrt[1+(a+b*x)^2]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: 1/ArcSinh[z] == Cosh[ArcSinh[z]]/ArcSinh[z]*ArcSinh'[z]*)


Int[1/ArcSinh[a_.+b_.*x_],x_Symbol] :=
  CoshIntegral[ArcSinh[a+b*x]]/b /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[1/ArcSinh[a_.+b_.*x_]^2,x_Symbol] :=
  -Sqrt[1+(a+b*x)^2]/(b*ArcSinh[a+b*x]) + SinhIntegral[ArcSinh[a+b*x]]/b /;
FreeQ[{a,b},x]


(* Replace the above with following when able to integrate result! *)
(* Int[1/ArcSinh[x_]^2,x_Symbol] :=
  -Sqrt[1+x^2]/(ArcSinh[x]) +
   Int[x/(Sqrt[1+x^2]*ArcSinh[x]),x] *)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: 1/Sqrt[ArcSinh[z]] == Cosh[ArcSinh[z]]/Sqrt[ArcSinh[z]]*ArcSinh'[z]*)


Int[1/Sqrt[ArcSinh[a_.+b_.*x_]],x_Symbol] :=
  Sqrt[Pi]/2*(Erf[Sqrt[ArcSinh[a+b*x]]]+Erfi[Sqrt[ArcSinh[a+b*x]]])/b /;
FreeQ[{a,b},x]


(* Replace the above with following when able to integrate result! *)
(* Int[1/Sqrt[ArcSinh[x_]],x_Symbol] :=
  Subst[Int[Cosh[x]/Sqrt[x],x],x,ArcSinh[x]] *)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Sqrt[ArcSinh[a_.+b_.*x_]],x_Symbol] :=
  (a+b*x)*Sqrt[ArcSinh[a+b*x]]/b -
  Sqrt[Pi]/4*(-Erf[Sqrt[ArcSinh[a+b*x]]]+Erfi[Sqrt[ArcSinh[a+b*x]]])/b /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Iterated integration by parts*)


Int[ArcSinh[a_.+b_.*x_]^n_,x_Symbol] :=
  (a+b*x)*ArcSinh[a+b*x]^n/b -
  n*Sqrt[1+(a+b*x)^2]*ArcSinh[a+b*x]^(n-1)/b +
  Dist[n*(n-1),Int[ArcSinh[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n>1


(* ::Item:: *)
(*Derivation: Inverted integration by parts twice*)


Int[ArcSinh[a_.+b_.*x_]^n_,x_Symbol] :=
  -(a+b*x)*ArcSinh[a+b*x]^(n+2)/(b*(n+1)*(n+2)) +
  Sqrt[1+(a+b*x)^2]*ArcSinh[a+b*x]^(n+1)/(b*(n+1)) +
  Dist[1/((n+1)*(n+2)),Int[ArcSinh[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n<-1 && n!=-2


Int[ArcSinh[a_.+b_.*x_]^n_,x_Symbol] :=
  ArcSinh[a+b*x]^n*Gamma[n+1,-ArcSinh[a+b*x]]/(2*b*(-ArcSinh[a+b*x])^n) -
  Gamma[n+1,ArcSinh[a+b*x]]/(2*b) /;
FreeQ[{a,b,n},x] && (Not[RationalQ[n]] || -1<n<1)


(* ::Subsubsection::Closed:: *)
(*x^m ArcSinh[a+ b x]			Products of monomials and arcsines of binomials*)


Int[ArcSinh[a_.*x_^n_.]/x_,x_Symbol] :=
  ArcSinh[a*x^n]^2/(2*n) + 
  ArcSinh[a*x^n]*Log[1-E^(-2*ArcSinh[a*x^n])]/n - 
  PolyLog[2,E^(-2*ArcSinh[a*x^n])]/(2*n) /;
(* ArcSinh[a*x^n]^2/(2*n) + 
  ArcSinh[a*x^n]*Log[1-1/(a*x^n+Sqrt[1+a^2*x^(2*n)])^2]/n - 
  PolyLog[2,1/(a*x^n+Sqrt[1+a^2*x^(2*n)])^2]/(2*n) /; *)
FreeQ[{a,n},x]


(* ::Item::Closed:: *)
(*Reference: CRC 581, A&S 4.6.50*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ArcSinh[a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*ArcSinh[a+b*x]/(m+1) - 
  Dist[b/(m+1),Int[x^(m+1)/Sqrt[1+a^2+2*a*b*x+b^2*x^2],x]] /;
FreeQ[{a,b,m},x] && NonzeroQ[m+1]


(* ::Subsubsection::Closed:: *)
(*(a+b ArcSinh[c+d x])^n		Powers of linear binomials of arcsines of linear binomials*)


Int[1/Sqrt[a_+b_.*ArcSinh[c_.+d_.*x_]],x_Symbol] :=
  Sqrt[Pi]*E^(a/b)*Erf[Sqrt[a+b*ArcSinh[c+d*x]]/Rt[b,2]]/(2*Rt[b,2]*d) + 
  Sqrt[Pi]*E^(-a/b)*Erfi[Sqrt[a+b*ArcSinh[c+d*x]]/Rt[b,2]]/(2*Rt[b,2]*d) /;
FreeQ[{a,b,c,d},x] && PosQ[b]


Int[1/Sqrt[a_+b_.*ArcSinh[c_.+d_.*x_]],x_Symbol] :=
 -Sqrt[Pi]*E^(-a/b)*Erf[Sqrt[a+b*ArcSinh[c+d*x]]/Rt[-b,2]]/(2*Rt[-b,2]*d) - 
  Sqrt[Pi]*E^(a/b)*Erfi[Sqrt[a+b*ArcSinh[c+d*x]]/Rt[-b,2]]/(2*Rt[-b,2]*d) /;
FreeQ[{a,b,c,d},x] && NegQ[b]


(* ::Subsubsection::Closed:: *)
(*(1+x^2)^(m/2) ArcSinh[x]^n		Products of half powers of binomials and powers of arcsines*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[(1+x_^2)^m_.*ArcSinh[x_]^n_.,x_Symbol] :=
  Module[{u=Block[{ShowSteps=False,StepCounter=Null}, Int[(1+x^2)^m,x]]},
  u*ArcSinh[x]^n - 
  Dist[n,Int[Expand[u*ArcSinh[x]^(n-1)/Sqrt[1+x^2]],x]]] /;
HalfIntegerQ[m] && m!=-1/2 && IntegerQ[n] && n>0


(* ::Subsubsection::Closed:: *)
(*x ArcSinh[a+b x]^n/Sqrt[1+(a+b x)^2]  Products of x and powers of arcsines of linears divided by sqrt of linear*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_*ArcSinh[a_.+b_.*x_]^n_/Sqrt[u_],x_Symbol] :=
  Sqrt[u]*ArcSinh[a+b*x]^n/b^2 -
  Dist[n/b,Int[ArcSinh[a+b*x]^(n-1),x]] -
  Dist[a/b,Int[ArcSinh[a+b*x]^n/Sqrt[u],x]] /;
FreeQ[{a,b},x] && ZeroQ[u-1-(a+b*x)^2] && RationalQ[n] && n>1


(* ::Subsubsection::Closed:: *)
(*u ArcSinh[c / (a+b x^n)]^m		Powers of arcsines of reciprocals of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcSinh[z] == ArcCsch[1/z]*)


Int[u_.*ArcSinh[c_./(a_.+b_.*x_^n_.)]^m_.,x_Symbol] :=
  Int[u*ArcCsch[a/c+b*x^n/c]^m,x] /;
FreeQ[{a,b,c,n,m},x]


(* ::Subsubsection::Closed:: *)
(*f[ArcSinh[x]] / Sqrt[1+x^2]		Products of functions of arcsines and its derivative*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: 1/Sqrt[1+z^2] == ArcSinh'[z]*)


(* Int[u_/Sqrt[1+x_^2],x_Symbol] :=
  Subst[Int[Regularize[SubstFor[ArcSinh[x],u,x],x],x],x,ArcSinh[x]] /;
FunctionOfQ[ArcSinh[x],u,x] *)


(* ::Subsubsection::Closed:: *)
(*u ArcSinh[v]				Products of expressions and arcsines of inverse free functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcSinh[u_],x_Symbol] :=
  x*ArcSinh[u] -
  Int[Regularize[x*D[u,x]/Sqrt[1+u^2],x],x] /;
InverseFunctionFreeQ[u,x] && Not[MatchQ[u,c_.+d_.*f_^(a_.+b_.*x) /; FreeQ[{a,b,c,d,f},x]]]


(* ::Subsubsection::Closed:: *)
(*f^(c ArcSinh[a+b x])			Exponentials of arcsines of linears*)


Int[f_^(c_.*ArcSinh[a_.+b_.*x_]),x_Symbol] :=
  f^(c*ArcSinh[a+b*x])*(a+b*x-c*Sqrt[1+(a+b*x)^2]*Log[f])/(b*(1-c^2*Log[f]^2)) /;
FreeQ[{a,b,c,f},x] && NonzeroQ[1-c^2*Log[f]^2]


(* ::Subsubsection::Closed:: *)
(*u E^(n ArcSinh[v])			Products of expressions and exponentials of arcsines  *)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcSinh[z]) == (z+Sqrt[1+z^2])^n*)


Int[E^(n_.*ArcSinh[v_]), x_Symbol] :=
  Int[(v+Sqrt[1+v^2])^n,x] /;
IntegerQ[n] && PolynomialQ[v,x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcSinh[z]) == (z+Sqrt[1+z^2])^n*)


Int[x_^m_.*E^(n_.*ArcSinh[v_]), x_Symbol] :=
  Int[x^m*(v+Sqrt[1+v^2])^n,x] /;
RationalQ[m] && IntegerQ[n] && PolynomialQ[v,x]


(* ::Subsection::Closed:: *)
(*Hyperbolic Arccosine Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*ArcCosh[a+b x]^n			Powers of arccosines of linear binomials*)


(* ::Item::Closed:: *)
(*Reference: CRC 582', A&S 4.6.44*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* Note: Should be simpler, analagous to that for ArcSinh. *)
Int[ArcCosh[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*ArcCosh[a+b*x]/b - Sqrt[-1+a+b*x]*Sqrt[1+a+b*x]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: 1/ArcCosh[z] == Sinh[ArcCosh[z]]/ArcCosh[z]*ArcCosh'[z]*)


Int[1/ArcCosh[a_.+b_.*x_],x_Symbol] :=
  SinhIntegral[ArcCosh[a+b*x]]/b /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[1/ArcCosh[a_.+b_.*x_]^2,x_Symbol] :=
  -Sqrt[-1+a+b*x]*Sqrt[1+a+b*x]/(b*ArcCosh[a+b*x]) + CoshIntegral[ArcCosh[a+b*x]]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: 1/Sqrt[ArcCosh[x]] == Sinh[ArcCosh[x]]/Sqrt[ArcCosh[x]]*ArcCosh'[x]*)


Int[1/Sqrt[ArcCosh[a_.+b_.*x_]],x_Symbol] :=
  Sqrt[Pi]/2*(-Erf[Sqrt[ArcCosh[a+b*x]]] + Erfi[Sqrt[ArcCosh[a+b*x]]])/b /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Sqrt[ArcCosh[a_.+b_.*x_]],x_Symbol] :=
  (a+b*x)*Sqrt[ArcCosh[a+b*x]]/b -
  Sqrt[Pi]/4*(Erf[Sqrt[ArcCosh[a+b*x]]]+Erfi[Sqrt[ArcCosh[a+b*x]]])/b /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Iterated integration by parts*)


Int[ArcCosh[a_.+b_.*x_]^n_,x_Symbol] :=
  (a+b*x)*ArcCosh[a+b*x]^n/b -
  n*Sqrt[-1+a+b*x]*Sqrt[1+a+b*x]*ArcCosh[a+b*x]^(n-1)/b +
  Dist[n*(n-1),Int[ArcCosh[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n>1


(* ::Item:: *)
(*Derivation: Inverted integration by parts twice*)


Int[ArcCosh[a_.+b_.*x_]^n_,x_Symbol] :=
  -(a+b*x)*ArcCosh[a+b*x]^(n+2)/(b*(n+1)*(n+2)) +
  Sqrt[-1+a+b*x]*Sqrt[1+a+b*x]*ArcCosh[a+b*x]^(n+1)/(b*(n+1)) +
  Dist[1/((n+1)*(n+2)),Int[ArcCosh[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n<-1 && n!=-2


Int[ArcCosh[a_.+b_.*x_]^n_,x_Symbol] :=
  ArcCosh[a+b*x]^n*Gamma[n+1,-ArcCosh[a+b*x]]/(2*b*(-ArcCosh[a+b*x])^n) +
  Gamma[n+1,ArcCosh[a+b*x]]/(2*b) /;
FreeQ[{a,b,n},x] && (Not[RationalQ[n]] || -1<n<1)


(* ::Subsubsection::Closed:: *)
(*x^m ArcCosh[a+b x]			Products of monomials and arccosines of monomials*)


Int[ArcCosh[a_.*x_^n_.]/x_,x_Symbol] :=
  ArcCosh[a*x^n]^2/(2*n) + 
  ArcCosh[a*x^n]*Log[1+E^(-2*ArcCosh[a*x^n])]/n - 
  PolyLog[2,-E^(-2*ArcCosh[a*x^n])]/(2*n) /;
(* ArcCosh[a*x^n]^2/(2*n) + 
  ArcCosh[a*x^n]*Log[1+1/(a*x^n+Sqrt[-1+a*x^n]*Sqrt[1+a*x^n])^2]/n - 
  PolyLog[2,-1/(a*x^n+Sqrt[-1+a*x^n]*Sqrt[1+a*x^n])^2]/(2*n) /; *)
FreeQ[{a,n},x]


(* ::Item::Closed:: *)
(*Reference: CRC 584, A&S 4.6.52*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ArcCosh[a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*ArcCosh[a+b*x]/(m+1) - 
  Dist[b/(m+1),Int[x^(m+1)/(Sqrt[-1+a+b*x]*Sqrt[1+a+b*x]),x]] /;
FreeQ[{a,b,m},x] && NonzeroQ[m+1]


(* ::Subsubsection::Closed:: *)
(*(a+b ArcCosh[c+d x])^n		Powers of linear binomials of arccosines of linear binomials*)


Int[1/Sqrt[a_+b_.*ArcCosh[c_.+d_.*x_]],x_Symbol] :=
  Sqrt[Pi]*E^(-a/b)*Erfi[Sqrt[a+b*ArcCosh[c+d*x]]/Rt[b,2]]/(2*Rt[b,2]*d) - 
  Sqrt[Pi]*E^(a/b)*Erf[Sqrt[a+b*ArcCosh[c+d*x]]/Rt[b,2]]/(2*Rt[b,2]*d) /;
FreeQ[{a,b,c,d},x] && PosQ[b]


Int[1/Sqrt[a_+b_.*ArcCosh[c_.+d_.*x_]],x_Symbol] :=
 -Sqrt[Pi]*E^(-a/b)*Erf[Sqrt[a+b*ArcCosh[c+d*x]]/Rt[-b,2]]/(2*Rt[-b,2]*d) + 
  Sqrt[Pi]*E^(a/b)*Erfi[Sqrt[a+b*ArcCosh[c+d*x]]/Rt[-b,2]]/(2*Rt[-b,2]*d) /;
FreeQ[{a,b,c,d},x] && NegQ[b]


(* ::Subsubsection::Closed:: *)
(*(1+x)^(m/2) (-1+x)^(m/2) ArcCosh[x]^n  Products of half-integer powers of linears and powers of arccosines*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[(1+x_)^m_.*(-1+x_)^m_.*ArcCosh[x_]^n_.,x_Symbol] :=
  Module[{u=Block[{ShowSteps=False,StepCounter=Null}, Int[(1+x)^m*(-1+x)^m,x]]},
  u*ArcCosh[x]^n -
  Dist[n,Int[Expand[u*ArcCosh[x]^(n-1)/(Sqrt[1+x]*Sqrt[-1+x])],x]]] /;
HalfIntegerQ[m] && m!=-1/2 && IntegerQ[n] && n>0


(* ::Subsubsection::Closed:: *)
(*x ArcCosh[a+b x]^n/Sqrt[1+(a+b x)^2]    Products of x and powers of arccosines of linears divided by sqrt of linear*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* Int[x_*ArcCosh[a_.+b_.*x_]^n_/Sqrt[u_],x_Symbol] :=
  ??? /;
FreeQ[{a,b},x] && ZeroQ[u-1+(a+b*x)^2] && RationalQ[n] && n>1 *)


(* ::Subsubsection::Closed:: *)
(*u ArcCosh[c / (a+b x^n)]^m		Powers of arccosines of reciprocals of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcCosh[z] == ArcSech[1/z]*)


Int[u_.*ArcCosh[c_./(a_.+b_.*x_^n_.)]^m_.,x_Symbol] :=
  Int[u*ArcSech[a/c+b*x^n/c]^m,x] /;
FreeQ[{a,b,c,n,m},x]


(* ::Subsubsection::Closed:: *)
(*f[ArcCosh[x]] / Sqrt[1+x^2]		Products of functions of arccosines and its derivative*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: 1/(Sqrt[1+z]*Sqrt[-1+z]) == ArcCosh'[z]*)


(* Int[u_/(Sqrt[1+x_]*Sqrt[-1+x_]),x_Symbol] :=
  Subst[Int[Regularize[SubstFor[ArcCosh[x],u,x],x],x],x,ArcCosh[x]] /;
FunctionOfQ[ArcCosh[x],u,x] *)


(* ::Subsubsection::Closed:: *)
(*u ArcCosh[v]				Products of expressions and arccosines of inverse free functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcCosh[u_],x_Symbol] :=
  x*ArcCosh[u] - 
  Int[Regularize[x*D[u,x]/(Sqrt[-1+u]*Sqrt[1+u]),x],x] /;
InverseFunctionFreeQ[u,x] && Not[MatchQ[u,c_.+d_.*f_^(a_.+b_.*x) /; FreeQ[{a,b,c,d,f},x]]]


(* ::Subsubsection::Closed:: *)
(*f^(c ArcCosh[a+b x])			Exponentials of arccosines of linears*)


Int[f_^(c_.*ArcCosh[a_.+b_.*x_]),x_Symbol] :=
  f^(c*ArcCosh[a+b*x])*(a+b*x-c*Sqrt[(-1+a+b*x)/(1+a+b*x)]*(1+a+b*x)*Log[f])/
    (b*(1-c^2*Log[f]^2)) /;
FreeQ[{a,b,c,f},x] && NonzeroQ[1-c^2*Log[f]^2]


(* ::Subsubsection::Closed:: *)
(*u E^(n ArcCosh[v])			Products of expressions and exponentials of arccosines  *)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcCosh[z]) == (z+Sqrt[-1+z]*Sqrt[1+z])^n*)


(* ::Item:: *)
(*Basis: If n is an integer, E^(n*ArcCosh[z]) == (z + Sqrt[(-1+z)/(1+z)] + z*Sqrt[(-1+z)/(1+z)])^n*)


Int[E^(n_.*ArcCosh[v_]), x_Symbol] :=
  Int[(v+Sqrt[-1+v]*Sqrt[1+v])^n,x] /;
IntegerQ[n] && PolynomialQ[v,x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcCosh[z]) == (z+Sqrt[-1+z]*Sqrt[1+z])^n*)


(* ::Item:: *)
(*Basis: If n is an integer, E^(n*ArcCosh[z]) == (z + Sqrt[(-1+z)/(1+z)] + z*Sqrt[(-1+z)/(1+z)])^n*)


Int[x_^m_.*E^(n_.*ArcCosh[v_]), x_Symbol] :=
  Int[x^m*(v+Sqrt[-1+v]*Sqrt[1+v])^n,x] /;
RationalQ[m] && IntegerQ[n] && PolynomialQ[v,x]


(* ::Subsection::Closed:: *)
(*Hyperbolic Arctangent Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*ArcTanh[a+b x^n]			Arctangents of binomials*)


(* ::Item::Closed:: *)
(*Reference: CRC 585, A&S 4.6.45*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcTanh[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*ArcTanh[a+b*x]/b + Log[1-(a+b*x)^2]/(2*b) /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcTanh[a_.+b_.*x_^n_],x_Symbol] :=
  x*ArcTanh[a+b*x^n] -
  Dist[b*n,Int[x^n/(1-a^2-2*a*b*x^n-b^2*x^(2*n)),x]] /;
FreeQ[{a,b},x] && IntegerQ[n]


(* ::Subsubsection::Closed:: *)
(*x^m ArcTanh[a+b x^n]		Products of monomials and arctangents of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: ArcTanh[z] == 1/2*Log[1+z] - 1/2*Log[1-z]*)


Int[ArcTanh[a_.+b_.*x_^n_.]/x_,x_Symbol] :=
  Dist[1/2,Int[Log[1+a+b*x^n]/x,x]] -
  Dist[1/2,Int[Log[1-a-b*x^n]/x,x]] /;
FreeQ[{a,b,n},x]


(* ::Item::Closed:: *)
(*Reference: CRC 588, A&S 4.6.54*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ArcTanh[a_.+b_.*x_^n_.],x_Symbol] :=
  x^(m+1)*ArcTanh[a+b*x^n]/(m+1) -
  Dist[b*n/(m+1),Int[x^(m+n)/(1-a^2-2*a*b*x^n-b^2*x^(2*n)),x]] /;
FreeQ[{a,b,m},x] && IntegerQ[n] && NonzeroQ[m+1] && NonzeroQ[m-n+1]


(* ::Subsubsection::Closed:: *)
(*(1-x^2)^m ArcTanh[x]^n		Products of integer powers of binomials and powers of arctangents*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[(1-x_^2)^m_*ArcTanh[x_]^n_.,x_Symbol] :=
  Module[{u=Block[{ShowSteps=False,StepCounter=Null}, Int[(1-x^2)^m,x]]},
  u*ArcTanh[x]^n -
  Dist[n,Int[Expand[u*ArcTanh[x]^(n-1)/(1-x^2)],x]]] /;
IntegerQ[{m,n}] && m<-1 && n>0


(* Ug.  (-1+x^2)^m should automatically evaluate to (-1)^m*(1-x^2)^m for integer m! *)
Int[(-1+x_^2)^m_*ArcTanh[x_]^n_.,x_Symbol] :=
  Dist[(-1)^m,Int[(1-x^2)^m*ArcTanh[x]^n,x]] /;
IntegerQ[{m,n}] && m<-1 && n>0


(* ::Subsubsection::Closed:: *)
(*(1-x^2)^m ArcCoth[x]^n ArcTanh[x]^p	Products of powers of binomials, arccotangents and arctangents*)


Int[1/((1-x_^2)*ArcCoth[x_]*ArcTanh[x_]),x_Symbol] :=
  (-Log[ArcCoth[x]]+Log[ArcTanh[x]])/(ArcCoth[x]-ArcTanh[x])


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcCoth[x_]^n_.*ArcTanh[x_]^p_./(1-x_^2),x_Symbol] :=
  ArcCoth[x]^(n+1)*ArcTanh[x]^p/(n+1) -
  Dist[p/(n+1),Int[ArcCoth[x]^(n+1)*ArcTanh[x]^(p-1)/(1-x^2),x]] /;
IntegerQ[{n,p}] && 0<p<=n


(* ::Subsubsection::Closed:: *)
(*x ArcTanh[a+b x]^n			Products of x and powers of arctangents of linears*)


Int[x_*ArcTanh[a_.*x_]^n_,x_Symbol] :=
  (-1+a^2*x^2)*ArcTanh[a*x]^n/(2*a^2) +
  Dist[n/(2*a),Int[ArcTanh[a*x]^(n-1),x]] /;
FreeQ[a,x] && RationalQ[n] && n>1


Int[x_*ArcTanh[a_.+b_.*x_]^n_,x_Symbol] :=
  -(1-(a+b*x)^2)*ArcTanh[a+b*x]^n/(2*b^2) +
  Dist[n/(2*b),Int[ArcTanh[a+b*x]^(n-1),x]] -
  Dist[a/b,Int[ArcTanh[a+b*x]^n,x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n>1


(* ::Subsubsection::Closed:: *)
(*u ArcTanh[c / (a+b x^n)]		Arctangent of reciprocals of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcTanh[z] == ArcCoth[1/z]*)


Int[u_.*ArcTanh[c_./(a_.+b_.*x_^n_.)]^m_.,x_Symbol] :=
  Int[u*ArcCoth[a/c+b*x^n/c]^m,x] /;
FreeQ[{a,b,c,n,m},x]


(* ::Subsubsection::Closed:: *)
(*f[x, ArcTanh[a+b x]] / (1-(a+b x)^2)	Products of functions involving arctangents of linears and its derivative*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[z]/(1-z^2) == f[Tanh[ArcTanh[z]]]*ArcTanh'[z]*)


(* ::Item:: *)
(*Basis: r + s*x + t*x^2 == -(s^2-4*r*t)/(4*t)*(1 - (s+2*t*x)^2/(s^2-4*r*t))*)


(* ::Item:: *)
(*Basis: 1-Tanh[z]^2 == Sech[z]^2*)


If[ShowSteps,

Int[u_*v_^n_.,x_Symbol] :=
  Module[{tmp=InverseFunctionOfLinear[u,x]},
  ShowStep["","Int[f[x,ArcTanh[a+b*x]]/(1-(a+b*x)^2),x]",
		   "Subst[Int[f[-a/b+Tanh[x]/b,x],x],x,ArcTanh[a+b*x]]/b",Hold[
  Dist[(-Discriminant[v,x]/(4*Coefficient[v,x,2]))^n/Coefficient[tmp[[1]],x,1],
	Subst[Int[Regularize[SubstForInverseFunction[u,tmp,x]*Sech[x]^(2*(n+1)),x],x], x, tmp]]]] /;
 NotFalseQ[tmp] && Head[tmp]===ArcTanh && ZeroQ[Discriminant[v,x]*tmp[[1]]^2-D[v,x]^2]] /;
SimplifyFlag && QuadraticQ[v,x] && IntegerQ[n] && n<0 && PosQ[Discriminant[v,x]],

Int[u_*v_^n_.,x_Symbol] :=
  Module[{tmp=InverseFunctionOfLinear[u,x]},
  Dist[(-Discriminant[v,x]/(4*Coefficient[v,x,2]))^n/Coefficient[tmp[[1]],x,1],
	Subst[Int[Regularize[SubstForInverseFunction[u,tmp,x]*Sech[x]^(2*(n+1)),x],x], x, tmp]] /;
 NotFalseQ[tmp] && Head[tmp]===ArcTanh && ZeroQ[Discriminant[v,x]*tmp[[1]]^2-D[v,x]^2]] /;
QuadraticQ[v,x] && IntegerQ[n] && n<0 && PosQ[Discriminant[v,x]]]


(* ::Subsubsection::Closed:: *)
(*u E^(n ArcTanh[v])			Products of expressions and exponentials of arctangents*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcTanh[z]) == (1+z)^(n/2)/(1-z)^(n/2)*)


Int[u_.*E^(n_.*ArcTanh[v_]),x_Symbol] :=
  Int[u*(1+v)^(n/2)/(1-v)^(n/2),x] /;
EvenQ[n]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcTanh[z]) == (1+z)^(n/2)/(1-z)^(n/2)*)


Int[E^(n_.*ArcTanh[v_]),x_Symbol] :=
  Int[(1+v)^(n/2)/(1-v)^(n/2),x] /;
RationalQ[n]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcTanh[z]) == ((1+z)/Sqrt[1-z^2])^n*)


Int[x_^m_.*E^(n_.*ArcTanh[v_]), x_Symbol] :=
  Int[x^m*(1+v)^n/(1-v^2)^(n/2),x] /;
RationalQ[m] && OddQ[n] && PolynomialQ[v,x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcTanh[z])*(1-z^2)^m == (1-z)^(m-n/2)*(1+z)^(m+n/2)*)


Int[u_.*E^(n_.*ArcTanh[v_])*(1-v_^2)^m_.,x_Symbol] :=
  Int[u*(1-v)^(m-n/2)*(1+v)^(m+n/2),x] /;
RationalQ[{m,n}] && IntegerQ[m-n/2] && IntegerQ[m+n/2]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcTanh[z])*(1-z^2)^m == (1-z)^(m-n/2)*(1+z)^(m+n/2)*)


Int[u_.*E^(n_.*ArcTanh[v_])*(a_+b_.*v_^2)^m_.,x_Symbol] :=
  (a+b*v^2)^m/(1-v^2)^m*Int[u*(1-v)^(m-n/2)*(1+v)^(m+n/2),x] /;
FreeQ[{a,b},x] && ZeroQ[a+b] && RationalQ[{m,n}] && IntegerQ[m-n/2] && IntegerQ[m+n/2]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcTanh[z]) == (1+z)^n/(1-z^2)^(n/2)*)


Int[u_.*E^(n_.*ArcTanh[v_])*(1-v_^2)^m_.,x_Symbol] :=
  Int[u*(1+v)^n*(1-v^2)^(m-n/2),x] /;
RationalQ[n] && IntegerQ[m] && m>0


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcTanh[z]) == (1+z)^n/(1-z^2)^(n/2)*)


Int[u_.*E^(n_.*ArcTanh[v_])*(1+v_)^m_.,x_Symbol] :=
  Int[u*(1+v)^(m+n)/(1-v^2)^(n/2),x] /;
RationalQ[{m,n}] && IntegerQ[m+n]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcTanh[z]) == (1+z)^(n/2)/(1-z)^(n/2)*)


Int[u_.*E^(n_.*ArcTanh[v_])*(1+v_)^m_.,x_Symbol] :=
  Int[u*(1+v)^(m+n/2)/(1-v)^(n/2),x] /;
RationalQ[{m,n}]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcTanh[z]) == (1+z)^(n/2)/(1-z)^(n/2)*)


Int[u_.*E^(n_.*ArcTanh[v_])*(1-v_)^m_.,x_Symbol] :=
  Int[u*(1+v)^(n/2)*(1-v)^(m-n/2),x] /;
RationalQ[{m,n}]


(* ::Item:: *)
(*Derivation: Algebraic simplification*)


Int[u_.*E^(n_.*ArcTanh[v_])*(a_+b_.*v_)^m_.,x_Symbol] :=
  Dist[a^m,Int[u*E^(n*ArcTanh[v])*(1+b/a*v)^m,x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && RationalQ[n] && NonzeroQ[a-1] && ZeroQ[a^2-b^2]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If m is an integer, E^ArcTanh[z]*(a-a/z^2)^m == (-a)^m*(1+z)*(1-z^2)^(m-1/2)/z^(2*m)*)


Int[u_.*E^ArcTanh[v_]*(a_+b_./v_^2)^m_.,x_Symbol] :=
  b^m*Int[u*(1-v^2)^(m-1/2)/v^(2*m),x] + 
  b^m*Int[u*(1-v^2)^(m-1/2)/v^(2*m-1),x] /;
FreeQ[{a,b},x] && ZeroQ[a+b] && IntegerQ[m]


(* ::Subsubsection::Closed:: *)
(*f[ArcTanh[x]] (1-x^2)^n		Products of functions of arctangents and its derivative*)


(* ::Item:: *)
(*Derivation: Integration by substitution*)


Int[u_*(a_+b_.*x_^2)^n_,x_Symbol] :=
  Dist[a^n,Subst[Int[Regularize[Cosh[x]^(-2*(n+1))*SubstFor[ArcTanh[x],u,x],x],x],x,ArcTanh[x]]] /;
FreeQ[{a,b},x] && FunctionOfQ[ArcTanh[x],u,x] && ZeroQ[a+b] && HalfIntegerQ[n] && n<-1 && 
	PositiveQ[a]


(* ::Item:: *)
(*Derivation: Integration by substitution*)


Int[u_*(a_+b_.*x_^2)^n_,x_Symbol] :=
  Dist[1/a,Subst[Int[Regularize[(a*Sech[x]^2)^(n+1)*SubstFor[ArcTanh[x],u,x],x],x],x,ArcTanh[x]]] /;
FreeQ[{a,b},x] && FunctionOfQ[ArcTanh[x],u,x] && ZeroQ[a+b] && HalfIntegerQ[n] && n<-1


(* ::Item:: *)
(*Derivation: Integration by substitution*)


(* Int[u_*(a_+b_./x_^2)^n_,x_Symbol] :=
  Subst[Int[Regularize[(b*Csch[x]^2)^n*Sech[x]^2*SubstFor[ArcTanh[x],u,x],x],x],x,ArcTanh[x]] /;
FreeQ[{a,b},x] && FunctionOfQ[ArcTanh[x],u,x] && ZeroQ[a+b] && HalfIntegerQ[n] *)


(* ::Subsubsection::Closed:: *)
(*x^m f[ArcTanh[x]] (1-x^2)^n		Products of monomials, functions of arctangents and its derivative*)


(* ::Item:: *)
(*Derivation: Integration by substitution*)


Int[x_^m_.*u_*(a_+b_.*x_^2)^n_,x_Symbol] :=
  Dist[a^n,Subst[Int[Regularize[Tanh[x]^m*Cosh[x]^(-2*(n+1))*SubstFor[ArcTanh[x],u,x],x],x],x,ArcTanh[x]]] /;
FreeQ[{a,b},x] && FunctionOfQ[ArcTanh[x],u,x] && ZeroQ[a+b] && HalfIntegerQ[n] && n<-1 && 
	PositiveQ[a] && IntegerQ[m]


(* ::Subsubsection::Closed:: *)
(*u ArcTanh[v]				Products of expressions and arctangents of inverse free functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcTanh[u_],x_Symbol] :=
  x*ArcTanh[u] -
  Int[Regularize[x*D[u,x]/(1-u^2),x],x] /;
InverseFunctionFreeQ[u,x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ArcTanh[u_],x_Symbol] :=
  x^(m+1)*ArcTanh[u]/(m+1) -
  Dist[1/(m+1),Int[Regularize[x^(m+1)*D[u,x]/(1-u^2),x],x]] /;
FreeQ[m,x] && NonzeroQ[m+1] && InverseFunctionFreeQ[u,x] && 
	Not[FunctionOfQ[x^(m+1),u,x]] && 
	FalseQ[PowerVariableExpn[u,m+1,x]]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[v_*ArcTanh[u_],x_Symbol] :=
  Module[{w=Block[{ShowSteps=False,StepCounter=Null}, Int[v,x]]},  
  w*ArcTanh[u] -
  Int[Regularize[w*D[u,x]/(1-u^2),x],x] /;
 InverseFunctionFreeQ[w,x]] /;
InverseFunctionFreeQ[u,x] && 
	Not[MatchQ[v, x^m_. /; FreeQ[m,x]]] &&
	FalseQ[FunctionOfLinear[v*ArcTanh[u],x]]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcTanh[z] ==  Log[1+z]/2 - Log[1-z]/2*)


Int[ArcTanh[b_.*x_]/(c_+d_.*x_^n_.),x_Symbol] :=
  Dist[1/2,Int[Log[1+b*x]/(c+d*x^n),x]] -
  Dist[1/2,Int[Log[1-b*x]/(c+d*x^n),x]] /;
FreeQ[{b,c,d},x] && IntegerQ[n] && Not[n==2 && ZeroQ[b^2*c+d]]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcTanh[z] ==  Log[1+z]/2 - Log[1-z]/2*)


Int[ArcTanh[a_+b_.*x_]/(c_+d_.*x_^n_.),x_Symbol] :=
  Dist[1/2,Int[Log[1+a+b*x]/(c+d*x^n),x]] -
  Dist[1/2,Int[Log[1-a-b*x]/(c+d*x^n),x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[n] && Not[n==1 && ZeroQ[a*d-b*c]]


(* ::Subsection::Closed:: *)
(*Hyperbolic Arccotangent Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*x^m ArcCoth[a+b x^n]		Arccotangents of binomials*)


(* ::Item::Closed:: *)
(*Reference: CRC 586, A&S 4.6.48*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcCoth[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*ArcCoth[a+b*x]/b + Log[1-(a+b*x)^2]/(2*b) /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcCoth[a_.+b_.*x_^n_],x_Symbol] :=
  x*ArcCoth[a+b*x^n] -
  Dist[b*n,Int[x^n/(1-a^2-2*a*b*x^n-b^2*x^(2*n)),x]] /;
FreeQ[{a,b},x] && IntegerQ[n]


(* ::Subsubsection::Closed:: *)
(*x^m ArcCoth[a+b x^n]		Products of monomials and arccotangents of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: ArcCoth[z] == 1/2*Log[1+1/z] - 1/2*Log[1-1/z]*)


Int[ArcCoth[a_.+b_.*x_^n_.]/x_,x_Symbol] :=
  Dist[1/2,Int[Log[1+1/(a+b*x^n)]/x,x]] -
  Dist[1/2,Int[Log[1-1/(a+b*x^n)]/x,x]] /;
FreeQ[{a,b,n},x]


(* ::Item::Closed:: *)
(*Reference: CRC 590*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ArcCoth[a_.+b_.*x_^n_.],x_Symbol] :=
  x^(m+1)*ArcCoth[a+b*x^n]/(m+1) -
  Dist[b*n/(m+1),Int[x^(m+n)/(1-a^2-2*a*b*x^n-b^2*x^(2*n)),x]] /;
FreeQ[{a,b,m},x] && IntegerQ[n] && NonzeroQ[m+1] && NonzeroQ[m-n+1]


(* ::Subsubsection::Closed:: *)
(*(1-x^2)^m ArcCoth[x]^n		Products of powers of binomials and powers of arccotangents*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[(1-x_^2)^m_*ArcCoth[x_]^n_.,x_Symbol] :=
  Module[{u=Block[{ShowSteps=False,StepCounter=Null}, Int[(1-x^2)^m,x]]},
  u*ArcCoth[x]^n-
  Dist[n,Int[Expand[u*ArcCoth[x]^(n-1)/(1-x^2)],x]]] /;
IntegerQ[{m,n}] && m<-1 && n>0


(* Ug.  (-1+x^2)^m should automatically evaluate to (-1)^m*(1-x^2)^m for integer m! *)
Int[(-1+x_^2)^m_*ArcCoth[x_]^n_.,x_Symbol] :=
  Dist[(-1)^m,Int[(1-x^2)^m*ArcCoth[x]^n,x]] /;
IntegerQ[{m,n}] && m<-1 && n>0


(* ::Subsubsection::Closed:: *)
(*(1-x^2)^m ArcCoth[x]^n ArcTanh[x]^p  Products of powers of binomials, arccotangents and arctangents*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcCoth[x_]^n_.*ArcTanh[x_]^p_/(1-x_^2),x_Symbol] :=
  ArcCoth[x]^n*ArcTanh[x]^(p+1)/(p+1) -
  Dist[n/(p+1),Int[ArcCoth[x]^(n-1)*ArcTanh[x]^(p+1)/(1-x^2),x]] /;
IntegerQ[{n,p}] && 0<n<p


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[(1-x_^2)^m_.*ArcCoth[x_]^n_.*ArcTanh[x_]^p_.,x_Symbol] :=
  Module[{u=Block[{ShowSteps=False,StepCounter=Null}, Int[(1-x^2)^m,x]]},
  u*ArcCoth[x]^n*ArcTanh[x]^p -
  Dist[p,Int[u*ArcCoth[x]^n*ArcTanh[x]^(p-1)/(1-x^2),x]] -
  Dist[n,Int[u*ArcCoth[x]^(n-1)*ArcTanh[x]^p/(1-x^2),x]]] /;
IntegerQ[{m,p,n}] && m<-1 && p>1 && n>1


(* Ug.  (-1+x^2)^m should automatically evaluate to (-1)^m*(1-x^2)^m for integer m! *)
Int[(-1+x_^2)^m_.*ArcCoth[x_]^n_.*ArcTanh[x_]^p_.,x_Symbol] :=
  Dist[(-1)^m,Int[(1-x^2)^m*ArcCoth[x]^n*ArcTanh[x]^p,x]] /;
IntegerQ[{m,n,p}] && m<-1 && n>0


(* ::Subsubsection::Closed:: *)
(*x ArcCoth[a+b x]^n			Products of x and powers of arccotangents of linears*)


Int[x_*ArcCoth[a_.*x_]^n_,x_Symbol] :=
  (-1+a^2*x^2)*ArcCoth[a*x]^n/(2*a^2)+
  Dist[n/(2*a),Int[ArcCoth[a*x]^(n-1),x]] /;
FreeQ[a,x] && RationalQ[n] && n>1


Int[x_*ArcCoth[a_.+b_.*x_]^n_,x_Symbol] :=
  (-1+(a+b*x)^2)*ArcCoth[a+b*x]^n/(2*b^2) +
  Dist[n/(2*b),Int[ArcCoth[a+b*x]^(n-1),x]] -
  Dist[a/b,Int[ArcCoth[a+b*x]^n,x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n>1


(* ::Subsubsection::Closed:: *)
(*u ArcCoth[c / (a+b x^n)]		Arccotangent of reciprocals of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcCoth[z] == ArcTanh[1/z]*)


Int[u_.*ArcCoth[c_./(a_.+b_.*x_^n_.)]^m_.,x_Symbol] :=
  Int[u*ArcTanh[a/c+b*x^n/c]^m,x] /;
FreeQ[{a,b,c,n,m},x]


(* ::Subsubsection::Closed:: *)
(*f[x, ArcCoth[a+b x]] / (1-(a+b x)^2)	Products of functions involving arccotangents of linears and its derivative*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[z]/(1-z^2) == f[Coth[ArcCoth[z]]]*ArcCoth'[z]*)


(* ::Item:: *)
(*Basis: r + s*x + t*x^2 == -(s^2-4*r*t)/(4*t)*(1 - (s+2*t*x)^2/(s^2-4*r*t))*)


(* ::Item:: *)
(*Basis: 1-Coth[z]^2 == -Csch[z]^2*)


If[ShowSteps,

Int[u_*v_^n_.,x_Symbol] :=
  Module[{tmp=InverseFunctionOfLinear[u,x]},
  ShowStep["","Int[f[x,ArcCoth[a+b*x]]/(1-(a+b*x)^2),x]",
		   "Subst[Int[f[-a/b+Coth[x]/b,x],x],x,ArcCoth[a+b*x]]/b",Hold[
  Dist[(-Discriminant[v,x]/(4*Coefficient[v,x,2]))^n/Coefficient[tmp[[1]],x,1],
	Subst[Int[Regularize[SubstForInverseFunction[u,tmp,x]*(-Csch[x]^2)^(n+1),x],x], x, tmp]]]] /;
 NotFalseQ[tmp] && Head[tmp]===ArcCoth && ZeroQ[Discriminant[v,x]*tmp[[1]]^2-D[v,x]^2]] /;
SimplifyFlag && QuadraticQ[v,x] && IntegerQ[n] && n<0 && PosQ[Discriminant[v,x]],

Int[u_*v_^n_.,x_Symbol] :=
  Module[{tmp=InverseFunctionOfLinear[u,x]},
  Dist[(-Discriminant[v,x]/(4*Coefficient[v,x,2]))^n/Coefficient[tmp[[1]],x,1],
	Subst[Int[Regularize[SubstForInverseFunction[u,tmp,x]*(-Csch[x]^2)^(n+1),x],x], x, tmp]] /;
 NotFalseQ[tmp] && Head[tmp]===ArcCoth && ZeroQ[Discriminant[v,x]*tmp[[1]]^2-D[v,x]^2]] /;
QuadraticQ[v,x] && IntegerQ[n] && n<0 && PosQ[Discriminant[v,x]]]


(* ::Subsubsection::Closed:: *)
(*u E^(n ArcCoth[v])			Products of expressions and exponentials of arccotangents*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If n is even, E^(n*ArcCoth[z]) == (1+z)^(n/2)/(-1+z)^(n/2)*)


Int[u_.*E^(n_.*ArcCoth[v_]),x_Symbol] :=
  Int[u*(1+v)^(n/2)/(-1+v)^(n/2),x] /;
EvenQ[n]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcCoth[z]) == (z*(1+z)/(Sqrt[z^2]*Sqrt[-1+z^2]))^n*)


(* ::Item:: *)
(*Basis: E^(n*ArcCoth[z]) == (1/Sqrt[1-1/z^2] + 1/(z*Sqrt[1-1/z^2]))^n*)


Int[E^(n_.*ArcCoth[v_]),x_Symbol] :=
  Int[Expand[(1/Sqrt[1-1/v^2] + 1/(x*Sqrt[1-1/v^2]))^n],x] /;
OddQ[n]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcCoth[z]) == (1+1/z)^(n/2)/(1-1/z)^(n/2)*)


(* Int[E^(n_.*ArcCoth[v_]),x_Symbol] :=
  Int[(1+1/v)^(n/2)/(1-1/v)^(n/2),x] /;
RationalQ[n] *)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[E^(ArcCoth[a_.+b_.*x_]/2), x_Symbol] :=
  x*E^(ArcCoth[a+b*x]/2) -
  Dist[b/2,Int[x*E^(ArcCoth[a+b*x]/2)/(1-(a+b*x)^2),x]] /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*E^(ArcCoth[a_.+b_.*x_]/2), x_Symbol] :=
  x^(m+1)*E^(ArcCoth[a+b*x]/2)/(m+1) -
  Dist[b/(2*(m+1)),Int[x^(m+1)*E^(ArcCoth[a+b*x]/2)/(1-(a+b*x)^2),x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcCoth[z]) == (z*(1+z)/(Sqrt[z^2]*Sqrt[-1+z^2]))^n*)


(* ::Item:: *)
(*Basis: E^(n*ArcCoth[z]) == (1/Sqrt[1-1/z^2] + 1/(z*Sqrt[1-1/z^2]))^n*)


Int[x_^m_.*E^(n_.*ArcCoth[v_]),x_Symbol] :=
  Int[Expand[x^m*(1/Sqrt[1-1/v^2] + 1/(x*Sqrt[1-1/v^2]))^n],x] /;
RationalQ[m] && OddQ[n]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcCoth[z]) == ((1+1/z)/Sqrt[1-1/z^2])^n*)


(* ::Item:: *)
(*Basis: If n is an integer, E^(n*ArcCoth[z]) == (1+z)^n/(z^n*(1-1/z^2)^(n/2))*)


(* Int[x_^m_.*E^(n_.*ArcCoth[v_]), x_Symbol] :=
  Int[x^m*(1+v)^n/(v^n*(1-1/v^2)^(n/2)),x] /;
RationalQ[m] && OddQ[n] && PolynomialQ[v,x] *)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If n is odd, E^(n*ArcCoth[z])*(1-z^2)^m == -(-1)^((n-1)/2)*z*Sqrt[1-1/z^2]/Sqrt[1-z^2]*(1-z)^(m-n/2)*(1+z)^(m+n/2)*)


(* ::Item:: *)
(*Basis: D[f[x]*Sqrt[a-a/f[x]^2]/Sqrt[1-f[x]^2],x] == 0*)


Int[u_.*E^(n_.*ArcCoth[v_])*(1-v_^2)^m_.,x_Symbol] :=
  -(-1)^((n-1)/2)*v*Sqrt[1-1/v^2]/Sqrt[1-v^2]*Int[u*(1-v)^(m-n/2)*(1+v)^(m+n/2),x] /;
OddQ[n] && HalfIntegerQ[m]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^ArcCoth[z]*(1-z^2) == -z*(1+z)*Sqrt[1-1/z^2]*)


Int[u_.*E^ArcCoth[v_]*(1-v_^2)^m_.,x_Symbol] :=
  -Int[Expand[u*v*(1+v)*Sqrt[1-1/v^2]*(1-v^2)^(m-1),x],x] /;
IntegerQ[m] && m>0


(* ::Item:: *)
(*Basis: D[(a-a*f[x]^2)^m/(1-f[x]^2)^m,x] == 0*)


Int[u_.*E^ArcCoth[v_]*(a_+b_.*v_^2)^m_.,x_Symbol] :=
  (a+b*v^2)^m/(1-v^2)^m*Int[u*E^ArcCoth[v]*(1-v^2)^m,x] /;
FreeQ[{a,b},x] && ZeroQ[a+b] && NonzeroQ[a-1]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcCoth[z]) == (1+1/z)^n/(1-1/z^2)^(n/2)*)


Int[u_.*E^(n_.*ArcCoth[v_])*(1-1/v_^2)^m_.,x_Symbol] :=
  Int[u*(1+v)^(m+n/2)*(-1+v)^(m-n/2)/v^(2*m),x] /;
IntegerQ[n] && IntegerQ[m-n/2]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcCoth[z]) == (1+1/z)^n/(1-1/z^2)^(n/2)*)


Int[u_.*E^(n_.*ArcCoth[v_])*(1-1/v_^2)^m_.,x_Symbol] :=
  Int[Expand[u*(1+1/v)^n*(1-1/v^2)^(m-n/2),x],x] /;
RationalQ[n] && IntegerQ[2*m]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^ArcCoth[z] == (1+1/z)/Sqrt[1-1/z^2]*)


(* ::Item:: *)
(*Basis: D[(a-a/f[x]^2)^m/(1-1/f[x]^2)^m,x] == 0*)


Int[u_.*E^ArcCoth[v_]*(a_+b_./v_^2)^m_.,x_Symbol] :=
  Int[u*(1+1/v)*(1-v^(-2))^(m-1/2),x]*(a+b*v^(-2))^m/(1-v^(-2))^m /;
FreeQ[{a,b},x] && ZeroQ[a+b] && IntegerQ[2*m]


(* ::Subsubsection::Closed:: *)
(*f[ArcCoth[x]] (1-x^2)^n		Products of functions of arccotangents and its derivative*)


(* ::Item:: *)
(*Derivation: Integration by substitution*)


(* Int[u_*(a_+b_.*x_^2)^n_,x_Symbol] :=
  Dist[1/a,Subst[Int[Regularize[(b*Csch[x]^2)^(n+1)*SubstFor[ArcCoth[x],u,x],x],x],x,ArcCoth[x]]] /;
FreeQ[{a,b},x] && FunctionOfQ[ArcCoth[x],u,x] && ZeroQ[a+b] && HalfIntegerQ[n] *)


(* ::Subsubsection::Closed:: *)
(*x^m f[ArcCoth[x]] (1-x^2)^n		Products of monomials, functions of arctangents and its derivative*)


(* ::Item:: *)
(*Derivation: Integration by substitution*)


Int[x_^m_.*u_*(a_+b_.*x_^2)^n_,x_Symbol] :=
  Dist[-b^n,Subst[Int[Regularize[Coth[x]^m*Sinh[x]^(-2*(n+1))*SubstFor[ArcCoth[x],u,x],x],x],x,ArcCoth[x]]] /;
FreeQ[{a,b},x] && FunctionOfQ[ArcCoth[x],u,x] && ZeroQ[a+b] && HalfIntegerQ[n] && n<-1 && 
	PositiveQ[a] && IntegerQ[m]


(* ::Subsubsection::Closed:: *)
(*u ArcCoth[v]				Products of expressions and arccotangents of inverse free functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcCoth[u_],x_Symbol] :=
  x*ArcCoth[u] - 
  Int[Regularize[x*D[u,x]/(1-u^2),x],x] /;
InverseFunctionFreeQ[u,x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ArcCoth[u_],x_Symbol] :=
  x^(m+1)*ArcCoth[u]/(m+1) - 
  Dist[1/(m+1),Int[Regularize[x^(m+1)*D[u,x]/(1-u^2),x],x]] /;
FreeQ[m,x] && NonzeroQ[m+1] && InverseFunctionFreeQ[u,x] && 
	Not[FunctionOfQ[x^(m+1),u,x]] && 
	FalseQ[PowerVariableExpn[u,m+1,x]]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[v_*ArcCoth[u_],x_Symbol] :=
  Module[{w=Block[{ShowSteps=False,StepCounter=Null}, Int[v,x]]},  
  w*ArcCoth[u] - 
  Int[Regularize[w*D[u,x]/(1-u^2),x],x] /;
 InverseFunctionFreeQ[w,x]] /;
InverseFunctionFreeQ[u,x] && 
	Not[MatchQ[v, x^m_. /; FreeQ[m,x]]] &&
	FalseQ[FunctionOfLinear[v*ArcCoth[u],x]]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcCoth[z] ==  Log[1+1/z]/2 - Log[1-1/z]/2*)


Int[ArcCoth[b_.*x_]/(c_+d_.*x_^n_.),x_Symbol] :=
  Dist[1/2,Int[Log[1+1/(b*x)]/(c+d*x^n),x]] -
  Dist[1/2,Int[Log[1-1/(b*x)]/(c+d*x^n),x]] /;
FreeQ[{b,c,d},x] && IntegerQ[n] && Not[n==2 && ZeroQ[b^2*c+d]]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcCoth[z] ==  Log[1+1/z]/2 - Log[1-1/z]/2*)


Int[ArcCoth[a_+b_.*x_]/(c_+d_.*x_^n_.),x_Symbol] :=
  Dist[1/2,Int[Log[1+1/(a+b*x)]/(c+d*x^n),x]] -
  Dist[1/2,Int[Log[1-1/(a+b*x)]/(c+d*x^n),x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[n] && Not[n==1 && ZeroQ[a*d-b*c]]


(* ::Subsection::Closed:: *)
(*Hyperbolic Arcsecant Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*ArcSech[a+b x]^n			Powers of arcsecants of linear binomials*)


(* ::Item::Closed:: *)
(*Reference: CRC 591', A&S 4.6.47'*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcSech[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*ArcSech[a+b*x]/b - 2*ArcTan[Sqrt[(1-a-b*x)/(1+a+b*x)]]/b /;
FreeQ[{a,b},x]


(* ::Subsubsection::Closed:: *)
(*x^m ArcSech[a+b x]			Products of monomials and arcsecants of monomials*)


Int[ArcSech[a_.*x_^n_.]/x_,x_Symbol] :=
(*   Int[ArcCosh[1/a*x^(-n)]/x,x] /; *)
  -ArcSech[a*x^n]^2/(2*n) - 
  ArcSech[a*x^n]*Log[1+E^(-2*ArcSech[a*x^n])]/n + 
  PolyLog[2,-E^(-2*ArcSech[a*x^n])]/(2*n) /;
(* -ArcSech[a*x^n]^2/(2*n) - 
  ArcSech[a*x^n]*Log[1+1/(1/(a*x^n)+Sqrt[-1+1/(a*x^n)]*Sqrt[1+1/(a*x^n)])^2]/n + 
  PolyLog[2,-1/(1/(a*x^n)+Sqrt[-1+1/(a*x^n)]*Sqrt[1+1/(a*x^n)])^2]/(2*n) /; *)
FreeQ[{a,n},x]


(* ::Item:: *)
(*Derivation: Integration by substitution*)


Int[x_^m_.*ArcSech[a_+b_.*x_],x_Symbol] :=
  Dist[1/b,Subst[Int[(-a/b+x/b)^m*ArcSech[x],x],x,a+b*x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0


(* ::Item::Closed:: *)
(*Reference: CRC 593', A&S 4.6.58'*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Basis: D[ArcSech[x],x] == -Sqrt[1/(1+x)]*Sqrt[1+x]/(x*Sqrt[1+x]*Sqrt[1-x])*)


(* ::Item:: *)
(*Basis: D[Sqrt[1/(1+a+b*x^n)]*Sqrt[1+a+b*x^n],x] == 0*)


Int[x_^m_.*ArcSech[a_.*x_],x_Symbol] :=
  x^(m+1)*ArcSech[a*x]/(m+1) + 
(*  Dist[1/(m+1),Int[x^m*Sqrt[(1-a*x)/(1+a*x)]/(1-a*x),x]] /; *)
  Dist[1/(m+1),Int[x^m/(Sqrt[(1-a*x)/(1+a*x)]*(1+a*x)),x]] /;
FreeQ[{a,m},x] && NonzeroQ[m+1]


(* ::Subsubsection::Closed:: *)
(*u ArcSech[c / (a+b x^n)]		Inverse secant of reciprocals of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcSech[z] == ArcCosh[1/z]*)


Int[u_.*ArcSech[c_./(a_.+b_.*x_^n_.)]^m_.,x_Symbol] :=
  Int[u*ArcCosh[a/c+b*x^n/c]^m,x] /;
FreeQ[{a,b,c,n,m},x]


(* ::Subsubsection::Closed:: *)
(*u ArcSech[v]				Products of expressions and arcsecants of inverse free functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* Int[ArcSech[u_],x_Symbol] :=
  x*ArcSech[u] +
  Int[Regularize[x*D[u,x]/(u^2*Sqrt[-1+1/u]*Sqrt[1+1/u]),x],x] /;
InverseFunctionFreeQ[u,x] && Not[MatchQ[u,c_.+d_.*f_^(a_.+b_.*x) /; FreeQ[{a,b,c,d,f},x]]] *)


(* ::Subsubsection::Closed:: *)
(*u E^(n ArcSech[v])			Products of expressions and exponentials of arccosines  *)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcSech[z]) == (Sqrt[-1+1/z]*Sqrt[1+1/z] + 1/z)^n*)


(* ::Item:: *)
(*Basis: If n is an integer, E^(n*ArcSech[z]) == (1/z + Sqrt[(1-z)/(1+z)] + Sqrt[(1-z)/(1+z)]/z)^n*)


(* ::Item:: *)
(*Basis: If n is an integer, E^(n*ArcSech[z]) == ((1+Sqrt[1-z]/Sqrt[1/(1+z)])/z)^n*)


Int[E^(n_.*ArcSech[v_]), x_Symbol] :=
  Int[(1/v + Sqrt[(1-v)/(1+v)] + Sqrt[(1-v)/(1+v)]/v)^n,x] /;
IntegerQ[n] && PolynomialQ[v,x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcSech[z]) == (Sqrt[-1+1/z]*Sqrt[1+1/z] + 1/z)^n*)


Int[x_^m_.*E^(n_.*ArcSech[v_]), x_Symbol] :=
  Int[x^m*(1/v + Sqrt[(1-v)/(1+v)] + Sqrt[(1-v)/(1+v)]/v)^n,x] /;
RationalQ[m] && IntegerQ[n] && PolynomialQ[v,x]


(* ::Subsection::Closed:: *)
(*Hyperbolic Arccosecant Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*ArcCsch[a+b x]^n			Powers of arcsecants of linear binomials*)


(* ::Item::Closed:: *)
(*Reference: CRC 594', A&S 4.6.46'*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcCsch[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*ArcCsch[a+b*x]/b + ArcTanh[Sqrt[1+1/(a+b*x)^2]]/b /;
FreeQ[{a,b},x]


(* ::Subsubsection::Closed:: *)
(*x^m ArcCsch[a x^n]			Products of monomials and arccosecants of monomials*)


Int[ArcCsch[a_.*x_^n_.]/x_,x_Symbol] :=
(* Int[ArcSinh[1/a*x^(-n)]/x,x] /; *)
  -ArcCsch[a*x^n]^2/(2*n) - 
  ArcCsch[a*x^n]*Log[1-E^(-2*ArcCsch[a*x^n])]/n + 
  PolyLog[2,E^(-2*ArcCsch[a*x^n])]/(2*n) /;
(* -ArcCsch[a*x^n]^2/(2*n) - 
  ArcCsch[a*x^n]*Log[1-1/(1/(a*x^n)+Sqrt[1+1/(a^2*x^(2*n))])^2]/n + 
  PolyLog[2,1/(1/(a*x^n)+Sqrt[1+1/(a^2*x^(2*n))])^2]/(2*n) /; *)
FreeQ[{a,n},x]


(* ::Item:: *)
(*Derivation: Integration by substitution*)


Int[x_^m_.*ArcCsch[a_+b_.*x_],x_Symbol] :=
  Dist[1/b,Subst[Int[(-a/b+x/b)^m*ArcCsch[x],x],x,a+b*x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0


(* ::Item::Closed:: *)
(*Reference: CRC 596, A&S 4.6.56*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ArcCsch[a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*ArcCsch[a+b*x]/(m+1) + 
  Dist[b/(m+1),Int[x^(m+1)/((a+b*x)^2*Sqrt[1+1/(a+b*x)^2]),x]] /;
FreeQ[{a,b,m},x] && NonzeroQ[m+1]


(* ::Subsubsection::Closed:: *)
(*u ArcCsch[c / (a+b x^n)]		Inverse cosecant of reciprocals of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcCsch[z] == ArcSinh[1/z]*)


Int[u_.*ArcCsch[c_./(a_.+b_.*x_^n_.)]^m_.,x_Symbol] :=
  Int[u*ArcSinh[a/c+b*x^n/c]^m,x] /;
FreeQ[{a,b,c,n,m},x]


(* ::Subsubsection::Closed:: *)
(*u ArcCsch[v]				Products of expressions and arccosecants of inverse free functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcCsch[u_],x_Symbol] :=
  x*ArcCsch[u] +
  Int[Regularize[x*D[u,x]/(u^2*Sqrt[1+1/u^2]),x],x] /;
InverseFunctionFreeQ[u,x] && Not[MatchQ[u,c_.+d_.*f_^(a_.+b_.*x) /; FreeQ[{a,b,c,d,f},x]]]


(* ::Subsubsection::Closed:: *)
(*u E^(n ArcCsch[v])			Products of expressions and exponentials of arccosines  *)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcCsch[z]) == (1/z+Sqrt[1+1/z^2])^n*)


Int[E^(n_.*ArcCsch[v_]), x_Symbol] :=
  Int[(1/v+Sqrt[1+1/v^2])^n,x] /;
IntegerQ[n] && PolynomialQ[v,x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: E^(n*ArcCsch[z]) == (1/z+Sqrt[1+1/z^2])^n*)


Int[x_^m_.*E^(n_.*ArcCsch[v_]), x_Symbol] :=
  Int[x^m*(1/v+Sqrt[1+1/v^2])^n,x] /;
RationalQ[m] && IntegerQ[n] && PolynomialQ[v,x]
