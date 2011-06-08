(* ::Package:: *)

(* ::Title:: *)
(*Inverse Trig Function Integration Rules*)


(* ::Subsection::Closed:: *)
(*Arcsine Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*ArcSin[a+b x]^n			Powers of arcsines of linear binomials*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.813.1, CRC 441, A&S 4.4.58*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcSin[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*ArcSin[a+b*x]/b + Sqrt[1-(a+b*x)^2]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: 1/ArcSin[z] == Cos[ArcSin[z]]/ArcSin[z]*ArcSin'[z]*)


Int[1/ArcSin[a_.+b_.*x_],x_Symbol] :=
  CosIntegral[ArcSin[a+b*x]]/b /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[1/ArcSin[a_.+b_.*x_]^2,x_Symbol] :=
  -Sqrt[1-(a+b*x)^2]/(b*ArcSin[a+b*x]) - SinIntegral[ArcSin[a+b*x]]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: 1/Sqrt[ArcSin[z]] == Cos[ArcSin[z]]/Sqrt[ArcSin[z]]*ArcSin'[z]*)


Int[1/Sqrt[ArcSin[a_.+b_.*x_]],x_Symbol] :=
  Sqrt[2*Pi]*FresnelC[Sqrt[2/Pi]*Sqrt[ArcSin[a+b*x]]]/b /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Sqrt[ArcSin[a_.+b_.*x_]],x_Symbol] :=
  (a+b*x)*Sqrt[ArcSin[a+b*x]]/b -
  Sqrt[Pi/2]*FresnelS[Sqrt[2/Pi]*Sqrt[ArcSin[a+b*x]]]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Reference: CRC 465*)


(* ::Item:: *)
(*Derivation: Iterated integration by parts*)


Int[ArcSin[a_.+b_.*x_]^n_,x_Symbol] :=
  (a+b*x)*ArcSin[a+b*x]^n/b +
  n*Sqrt[1-(a+b*x)^2]*ArcSin[a+b*x]^(n-1)/b -
  Dist[n*(n-1),Int[ArcSin[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n>1


(* ::Item:: *)
(*Derivation: Inverted integration by parts twice*)


Int[ArcSin[a_.+b_.*x_]^n_,x_Symbol] :=
  (a+b*x)*ArcSin[a+b*x]^(n+2)/(b*(n+1)*(n+2)) +
  Sqrt[1-(a+b*x)^2]*ArcSin[a+b*x]^(n+1)/(b*(n+1)) -
  Dist[1/((n+1)*(n+2)),Int[ArcSin[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n<-1 && n!=-2


Int[ArcSin[a_.+b_.*x_]^n_,x_Symbol] :=
  I*ArcSin[a+b*x]^n*(-(I*ArcSin[a+b*x])^n*Gamma[n+1,-I*ArcSin[a+b*x]] + 
  (-I*ArcSin[a+b*x])^n*Gamma[n+1,I*ArcSin[a+b*x]])/(2*b*(ArcSin[a+b*x]^2)^n) /;
FreeQ[{a,b,n},x] && (Not[RationalQ[n]] || -1<n<1)


(* ::Subsubsection::Closed:: *)
(*x^m ArcSin[a+b x]			Products of monomials and arcsines of binomials*)


Int[ArcSin[a_.*x_^n_.]/x_,x_Symbol] :=
  -I*ArcSin[a*x^n]^2/(2*n) + 
  ArcSin[a*x^n]*Log[1-(I*a*x^n+Sqrt[1-a^2*x^(2*n)])^2]/n - 
  I*PolyLog[2,(I*a*x^n+Sqrt[1-a^2*x^(2*n)])^2]/(2*n) /;
(* Sqrt[-a^2]*ArcSin[a*x^n]^2/(2*a*n) + 
  ArcSin[a*x^n]*Log[2*x^n*(a^2*x^n+Sqrt[-a^2]*Sqrt[1-a^2*x^(2*n)])]/n + 
  Sqrt[-a^2]*PolyLog[2,1-2*x^n*(a^2*x^n+Sqrt[-a^2]*Sqrt[1-a^2*x^(2*n)])]/(2*a*n) /; *)
FreeQ[{a,n},x]


(* ::Item::Closed:: *)
(*Reference: G&R 2.831, CRC 453, A&S 4.4.65*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ArcSin[a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*ArcSin[a+b*x]/(m+1) - 
  Dist[b/(m+1),Int[x^(m+1)/Sqrt[1-a^2-2*a*b*x-b^2*x^2],x]] /;
FreeQ[{a,b,m},x] && NonzeroQ[m+1]


(* ::Subsubsection::Closed:: *)
(*(a+b ArcSin[c+d x])^n			Powers of linear binomials of arcsines of linear binomials*)


Int[1/Sqrt[a_+b_.*ArcSin[c_.+d_.*x_]],x_Symbol] :=
  Sqrt[2*Pi]*Cos[a/b]*FresnelC[Sqrt[2/Pi]*Sqrt[a+b*ArcSin[c+d*x]]/Rt[b,2]]/(d*Rt[b,2]) + 
  Sqrt[2*Pi]*Sin[a/b]*FresnelS[Sqrt[2/Pi]*Sqrt[a+b*ArcSin[c+d*x]]/Rt[b,2]]/(d*Rt[b,2]) /;
FreeQ[{a,b,c,d},x] && PosQ[b]


Int[1/Sqrt[a_+b_.*ArcSin[c_.+d_.*x_]],x_Symbol] :=
 -Sqrt[2*Pi]*Cos[a/b]*FresnelC[(Sqrt[2/Pi]*Sqrt[a+b*ArcSin[c+d*x]])/Rt[-b,2]]/(d*Rt[-b,2]) + 
  Sqrt[2*Pi]*Sin[a/b]*FresnelS[(Sqrt[2/Pi]*Sqrt[a+b*ArcSin[c+d*x]])/Rt[-b,2]]/(d*Rt[-b,2]) /;
FreeQ[{a,b,c,d},x] && NegQ[b]


(* ::Subsubsection::Closed:: *)
(*(1-x^2)^(m/2) ArcSin[x]^n		Products of half-integer powers of binomials and powers of arcsines*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[(1-x_^2)^m_.*ArcSin[x_]^n_.,x_Symbol] :=
  Module[{u=Block[{ShowSteps=False,StepCounter=Null}, Int[(1-x^2)^m,x]]},
  u*ArcSin[x]^n -
  Dist[n,Int[Expand[u*ArcSin[x]^(n-1)/Sqrt[1-x^2]],x]]] /;
HalfIntegerQ[m] && m!=-1/2 && IntegerQ[n] && n>0


(* ::Subsubsection::Closed:: *)
(*x ArcSin[a+b x]^n/Sqrt[1-(a+b x)^2]	Products of x and powers of arcsines of linears divided by sqrt of linear*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_*ArcSin[a_.+b_.*x_]^n_/Sqrt[u_],x_Symbol] :=
  -Sqrt[u]*ArcSin[a+b*x]^n/b^2 +
  Dist[n/b,Int[ArcSin[a+b*x]^(n-1),x]] -
  Dist[a/b,Int[ArcSin[a+b*x]^n/Sqrt[u],x]] /;
FreeQ[{a,b},x] && ZeroQ[u-1+(a+b*x)^2] && RationalQ[n] && n>1


(* ::Subsubsection::Closed:: *)
(*u ArcSin[c / (a+b x^n)]^m		Powers of arcsines of reciprocals of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcSin[z] == ArcCsc[1/z]*)


Int[u_.*ArcSin[c_./(a_.+b_.*x_^n_.)]^m_.,x_Symbol] :=
  Int[u*ArcCsc[a/c+b*x^n/c]^m,x] /;
FreeQ[{a,b,c,n,m},x]


(* ::Subsubsection::Closed:: *)
(*f[ArcSin[x]] / Sqrt[1-x^2]		Products of functions of inverse sines and its derivative*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[ArcSin[x]]/Sqrt[1-x^2] == f[ArcSin[x]]*ArcSin'[x]*)


(* Int[u_/Sqrt[1-x_^2],x_Symbol] :=
  Subst[Int[Regularize[SubstFor[ArcSin[x],u,x],x],x],x,ArcSin[x]] /;
FunctionOfQ[ArcSin[x],u,x] *)


(* ::Subsubsection::Closed:: *)
(*u ArcSin[v]				Products of expressions and arcsines of inverse free functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcSin[u_],x_Symbol] :=
  x*ArcSin[u] -
  Int[Regularize[x*D[u,x]/Sqrt[1-u^2],x],x] /;
InverseFunctionFreeQ[u,x] && Not[MatchQ[u,c_.+d_.*f_^(a_.+b_.*x) /; FreeQ[{a,b,c,d,f},x]]]


(* ::Subsubsection::Closed:: *)
(*f^(c ArcSin[a+b x])			Exponentials of arcsines of linears*)


Int[f_^(c_.*ArcSin[a_.+b_.*x_]),x_Symbol] :=
  f^(c*ArcSin[a+b*x])*(a+b*x+c*Sqrt[1-(a+b*x)^2]*Log[f])/(b*(1+c^2*Log[f]^2)) /;
FreeQ[{a,b,c,f},x] && NonzeroQ[1+c^2*Log[f]^2]


(* ::Subsection::Closed:: *)
(*Arccosine Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*ArcCos[a+b x]^n			Powers of arccosines of linear binomials*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.814.1, CRC 442, A&S 4.4.59*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcCos[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*ArcCos[a+b*x]/b - Sqrt[1-(a+b*x)^2]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: 1/ArcCos[z] == -Sin[ArcCos[z]]/ArcCos[z]*ArcCos'[z]*)


Int[1/ArcCos[a_.+b_.*x_],x_Symbol] :=
  -SinIntegral[ArcCos[a+b*x]]/b /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[1/ArcCos[a_.+b_.*x_]^2,x_Symbol] :=
  Sqrt[1-(a+b*x)^2]/(b*ArcCos[a+b*x]) - CosIntegral[ArcCos[a+b*x]]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: 1/Sqrt[ArcCos[z]] == -Sin[ArcCos[z]]/Sqrt[ArcCos[z]]*ArcCos'[z]*)


Int[1/Sqrt[ArcCos[a_.+b_.*x_]],x_Symbol] :=
  -Sqrt[2*Pi]*FresnelS[Sqrt[2/Pi]*Sqrt[ArcCos[a+b*x]]]/b /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[Sqrt[ArcCos[a_.+b_.*x_]],x_Symbol] :=
  (a+b*x)*Sqrt[ArcCos[a+b*x]]/b - Sqrt[Pi/2]*FresnelC[Sqrt[2/Pi]*Sqrt[ArcCos[a+b*x]]]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Reference: CRC 466*)


(* ::Item:: *)
(*Derivation: Iterated integration by parts*)


Int[ArcCos[a_.+b_.*x_]^n_,x_Symbol] :=
  (a+b*x)*ArcCos[a+b*x]^n/b -
  n*Sqrt[1-(a+b*x)^2]*ArcCos[a+b*x]^(n-1)/b -
  Dist[n*(n-1),Int[ArcCos[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n>1


(* ::Item:: *)
(*Derivation: Inverted integration by parts twice*)


Int[ArcCos[a_.+b_.*x_]^n_,x_Symbol] :=
  (a+b*x)*ArcCos[a+b*x]^(n+2)/(b*(n+1)*(n+2)) -
  Sqrt[1-(a+b*x)^2]*ArcCos[a+b*x]^(n+1)/(b*(n+1)) -
  Dist[1/((n+1)*(n+2)),Int[ArcCos[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n<-1 && n!=-2


Int[ArcCos[a_.+b_.*x_]^n_,x_Symbol] :=
  ArcCos[a+b*x]^n*((I*ArcCos[a+b*x])^n*Gamma[n+1,-I*ArcCos[a+b*x]] + 
  (-I*ArcCos[a+b*x])^n*Gamma[n+1,I*ArcCos[a+b*x]])/(2*b*(ArcCos[a+b*x]^2)^n) /;
FreeQ[{a,b,n},x] && (Not[RationalQ[n]] || -1<n<1)


(* ::Subsubsection::Closed:: *)
(*x^m ArcCos[a+b x]			Products of monomials and arccosines of binomials*)


Int[ArcCos[a_.*x_^n_.]/x_,x_Symbol] :=
  -I*ArcCos[a*x^n]^2/(2*n) + 
  ArcCos[a*x^n]*Log[1-1/(I*a*x^n+Sqrt[1-a^2*x^(2*n)])^2]/n -
  I*PolyLog[2,1/(I*a*x^n+Sqrt[1-a^2*x^(2*n)])^2]/(2*n) /;
(* -Sqrt[-a^2]*ArcSin[a*x^n]^2/(2*a*n) + 
  Pi*Log[x]/2 + 
  Sqrt[-a^2]*ArcSinh[Sqrt[-a^2]*x^n]*Log[1-1/(Sqrt[-a^2]*x^n+Sqrt[1-a^2*x^(2*n)])^2]/(a*n) - 
  Sqrt[-a^2]*PolyLog[2,1/(Sqrt[-a^2]*x^n+Sqrt[1-a^2*x^(2*n)])^2]/(2*a*n) /; *)
FreeQ[{a,n},x]


(* ::Item::Closed:: *)
(*Reference: G&R 2.832, CRC 454, A&S 4.4.67*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ArcCos[a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*ArcCos[a+b*x]/(m+1) +
  Dist[b/(m+1),Int[x^(m+1)/Sqrt[1-a^2-2*a*b*x-b^2*x^2],x]] /;
FreeQ[{a,b,m},x] && NonzeroQ[m+1]


(* ::Subsubsection::Closed:: *)
(*(a+b ArcCos[c+d x])^n			Powers of linear binomials of arccosines of linear binomials*)


Int[1/Sqrt[a_+b_.*ArcCos[c_.+d_.*x_]],x_Symbol] :=
 -Sqrt[2*Pi]*Cos[a/b]*FresnelS[Sqrt[2/Pi]*Sqrt[a+b*ArcCos[c+d*x]]/Rt[b,2]]/(d*Rt[b,2]) + 
  Sqrt[2*Pi]*Sin[a/b]*FresnelC[Sqrt[2/Pi]*Sqrt[a+b*ArcCos[c+d*x]]/Rt[b,2]]/(d*Rt[b,2]) /;
FreeQ[{a,b,c,d},x] && PosQ[b]


Int[1/Sqrt[a_+b_.*ArcCos[c_.+d_.*x_]],x_Symbol] :=
 -Sqrt[2*Pi]*Cos[a/b]*FresnelS[(Sqrt[2/Pi]*Sqrt[a+b*ArcCos[c+d*x]])/Rt[-b,2]]/(d*Rt[-b,2]) - 
  Sqrt[2*Pi]*Sin[a/b]*FresnelC[(Sqrt[2/Pi]*Sqrt[a+b*ArcCos[c+d*x]])/Rt[-b,2]]/(d*Rt[-b,2]) /;
FreeQ[{a,b,c,d},x] && NegQ[b]


(* ::Subsubsection::Closed:: *)
(*(1-x^2)^(m/2) ArcCos[x]^n		Products of half-integer powers of binomials and powers of arccosines*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[(1-x_^2)^m_.*ArcCos[x_]^n_.,x_Symbol] :=
  Module[{u=Block[{ShowSteps=False,StepCounter=Null}, Int[(1-x^2)^m,x]]},
  u*ArcCos[x]^n +
  Dist[n,Int[Expand[u*ArcCos[x]^(n-1)/Sqrt[1-x^2]],x]]] /;
HalfIntegerQ[m] && m!=-1/2 && IntegerQ[n] && n>0


(* ::Subsubsection::Closed:: *)
(*x ArcCos[a+b x]^n/Sqrt[1-(a+b x)^2]  	Products of x and powers of arccosines of linears divided by sqrt of linear*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_*ArcCos[a_.+b_.*x_]^n_/Sqrt[u_],x_Symbol] :=
  -Sqrt[u]*ArcCos[a+b*x]^n/b^2 -
  Dist[n/b,Int[ArcCos[a+b*x]^(n-1),x]] -
  Dist[a/b,Int[ArcCos[a+b*x]^n/Sqrt[u],x]] /;
FreeQ[{a,b},x] && ZeroQ[u-1+(a+b*x)^2] && RationalQ[n] && n>1


(* ::Subsubsection::Closed:: *)
(*u ArcCos[c / (a+b x^n)]^m		Powers of arccosines of reciprocals of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcCos[z] == ArcSec[1/z]*)


Int[u_.*ArcCos[c_./(a_.+b_.*x_^n_.)]^m_.,x_Symbol] :=
  Int[u*ArcSec[a/c+b*x^n/c]^m,x] /;
FreeQ[{a,b,c,n,m},x]


(* ::Subsubsection::Closed:: *)
(*f[ArcCos[x]] / Sqrt[1-x^2]		Products of functions of inverse cosines and its derivative*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: 1/Sqrt[1-z^2] == -ArcCos'[z]*)


(* Int[u_/Sqrt[1-x_^2],x_Symbol] :=
  -Subst[Int[Regularize[SubstFor[ArcCos[x],u,x],x],x],x,ArcCos[x]] /;
FunctionOfQ[ArcCos[x],u,x] *)


(* ::Subsubsection::Closed:: *)
(*u ArcCos[v]				Products of expressions and arccosines of inverse free functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcCos[u_],x_Symbol] :=
  x*ArcCos[u] +
  Int[Regularize[x*D[u,x]/Sqrt[1-u^2],x],x] /;
InverseFunctionFreeQ[u,x] && Not[MatchQ[u,c_.+d_.*f_^(a_.+b_.*x) /; FreeQ[{a,b,c,d,f},x]]]


(* ::Subsubsection::Closed:: *)
(*f^(c ArcCos[a+b x])			Exponentials of arccosines of linears*)


Int[f_^(c_.*ArcCos[a_.+b_.*x_]),x_Symbol] :=
  f^(c*ArcCos[a+b*x])*(a+b*x-c*Sqrt[1-(a+b*x)^2]*Log[f])/(b*(1+c^2*Log[f]^2)) /;
FreeQ[{a,b,c,f},x] && NonzeroQ[1+c^2*Log[f]^2]


(* ::Subsection::Closed:: *)
(*Arctangent Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*ArcTan[a+b x^n]			Arctangents of binomials*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.822.1, CRC 443, A&S 4.4.60*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcTan[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*ArcTan[a+b*x]/b - Log[1+(a+b*x)^2]/(2*b) /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcTan[a_.+b_.*x_^n_],x_Symbol] :=
  x*ArcTan[a+b*x^n] -
  Dist[b*n,Int[x^n/(1+a^2+2*a*b*x^n+b^2*x^(2*n)),x]] /;
FreeQ[{a,b},x] && IntegerQ[n]


(* ::Subsubsection::Closed:: *)
(*x^m ArcTan[a+b x^n]			Products of monomials and arctangents of binomials*)
(**)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: ArcTan[z] == I/2*Log[1-I*z] - I/2*Log[1+I*z]*)


Int[ArcTan[a_.+b_.*x_^n_.]/x_,x_Symbol] :=
  Dist[I/2,Int[Log[1-I*a-I*b*x^n]/x,x]] -
  Dist[I/2,Int[Log[1+I*a+I*b*x^n]/x,x]] /;
FreeQ[{a,b,n},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* Int[ArcTan[a_.+b_.*x_]/x_,x_Symbol] :=
  Log[x]*ArcTan[a+b*x] -
  Dist[b,Int[Log[x]/(1+(a+b*x)^2),x]] /;
FreeQ[{a,b},x] *)


(* ::Item::Closed:: *)
(*Reference: G&R 2.851, CRC 456, A&S 4.4.69*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ArcTan[a_.+b_.*x_^n_.],x_Symbol] :=
  x^(m+1)*ArcTan[a+b*x^n]/(m+1) -
  Dist[b*n/(m+1),Int[x^(m+n)/(1+a^2+2*a*b*x^n+b^2*x^(2*n)),x]] /;
FreeQ[{a,b,m},x] && IntegerQ[n] && NonzeroQ[m+1] && NonzeroQ[m-n+1]


(* ::Subsubsection::Closed:: *)
(*(1+x^2)^m ArcTan[x]^n		Products of powers of binomials and powers of arctangents*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[(1+x_^2)^m_*ArcTan[x_]^n_.,x_Symbol] :=
  Module[{u=Block[{ShowSteps=False,StepCounter=Null}, Int[(1+x^2)^m,x]]},
  u*ArcTan[x]^n -
  Dist[n,Int[u*ArcTan[x]^(n-1)/(1+x^2),x]]] /;
IntegerQ[{m,n}] && m<-1 && n>0


(* ::Subsubsection::Closed:: *)
(*(1+x^2)^m ArcCot[x]^n ArcTan[x]^p	Products of powers of binomials, arccotangents and arctangents*)


Int[1/((1+x_^2)*ArcCot[x_]*ArcTan[x_]),x_Symbol] :=
  (-Log[ArcCot[x]]+Log[ArcTan[x]])/(ArcCot[x]+ArcTan[x])


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcCot[x_]^n_.*ArcTan[x_]^p_./(1+x_^2),x_Symbol] :=
  -ArcCot[x]^(n+1)*ArcTan[x]^p/(n+1) +
  Dist[p/(n+1),Int[ArcCot[x]^(n+1)*ArcTan[x]^(p-1)/(1+x^2),x]] /;
IntegerQ[{n,p}] && 0<p<=n


(* ::Subsubsection::Closed:: *)
(*x ArcTan[a+b x]^n			Products of x and powers of arctangents of linears*)


Int[x_*ArcTan[b_.*x_]^n_,x_Symbol] :=
  ((b*x)^2+1)*ArcTan[b*x]^n/(2*b^2) -
  Dist[n/(2*b),Int[ArcTan[b*x]^(n-1),x]] /;
FreeQ[b,x] && RationalQ[n] && n>1


Int[x_*ArcTan[a_+b_.*x_]^n_,x_Symbol] :=
  ((a+b*x)^2+1)*ArcTan[a+b*x]^n/(2*b^2) -
  Dist[n/(2*b),Int[ArcTan[a+b*x]^(n-1),x]] -
  Dist[a/b,Int[ArcTan[a+b*x]^n,x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n>1


(* ::Subsubsection::Closed:: *)
(*u ArcTan[c / (a+b x^n)]		Arctangents of reciprocals of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcTan[z] == ArcCot[1/z]*)


Int[u_.*ArcTan[c_./(a_.+b_.*x_^n_.)]^m_.,x_Symbol] :=
  Int[u*ArcCot[a/c+b*x^n/c]^m,x] /;
FreeQ[{a,b,c,n,m},x]


(* ::Subsubsection::Closed:: *)
(*f[ArcTan[x]] (1+x^2)^n		Products of functions of arctangents and its derivative*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: 1/(1+z^2) == ArcTan'[z]*)


Int[u_*(a_+b_.*x_^2)^n_,x_Symbol] :=
  Dist[a^n,Subst[Int[Regularize[Cos[x]^(-2*(n+1))*SubstFor[ArcTan[x],u,x],x],x],x,ArcTan[x]]] /;
FreeQ[{a,b},x] && FunctionOfQ[ArcTan[x],u,x] && ZeroQ[a-b] && IntegerQ[n] && n<-1


(* ::Item:: *)
(*Derivation: Integration by substitution*)


Int[u_*(a_+b_.*x_^2)^n_,x_Symbol] :=
  Dist[a^n,Subst[Int[Regularize[Cos[x]^(-2*(n+1))*SubstFor[ArcTan[x],u,x],x],x],x,ArcTan[x]]] /;
FreeQ[{a,b},x] && FunctionOfQ[ArcTan[x],u,x] && ZeroQ[a-b] && HalfIntegerQ[n] && n<-1 && 
	PositiveQ[a]


(* ::Item:: *)
(*Derivation: Integration by substitution*)


Int[u_*(a_+b_.*x_^2)^n_,x_Symbol] :=
  Dist[1/a,Subst[Int[Regularize[(a*Sec[x]^2)^(n+1)*SubstFor[ArcTan[x],u,x],x],x],x,ArcTan[x]]] /;
FreeQ[{a,b},x] && FunctionOfQ[ArcTan[x],u,x] && ZeroQ[a-b] && HalfIntegerQ[n] && n<-1


(* ::Subsubsection::Closed:: *)
(*x^m f[ArcTan[x]] (1+x^2)^n		Products of monomials, functions of arctangents and its derivative*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: 1/(1+z^2) == ArcTan'[z]*)


Int[x_^m_.*u_*(a_+b_.*x_^2)^n_,x_Symbol] :=
  Dist[a^n,Subst[Int[Regularize[Tan[x]^m*Cos[x]^(-2*(n+1))*SubstFor[ArcTan[x],u,x],x],x],x,ArcTan[x]]] /;
FreeQ[{a,b},x] && FunctionOfQ[ArcTan[x],u,x] && ZeroQ[a-b] && IntegerQ[{m,n}] && n<0


(* ::Item:: *)
(*Derivation: Integration by substitution*)


Int[x_^m_.*u_*(a_+b_.*x_^2)^n_,x_Symbol] :=
  Dist[a^n,Subst[Int[Regularize[Tan[x]^m*Cos[x]^(-2*(n+1))*SubstFor[ArcTan[x],u,x],x],x],x,ArcTan[x]]] /;
FreeQ[{a,b},x] && FunctionOfQ[ArcTan[x],u,x] && ZeroQ[a-b] && HalfIntegerQ[n] && n<-1 && 
	PositiveQ[a] && IntegerQ[m]
(* Need to generalize for arbitrary functions of ArcTan[Sqrt[b/a]*x] *)


(* ::Item:: *)
(*Derivation: Integration by substitution*)


(* Int[f_[ArcTan[c_.*x_]]/(a_+b_.*x_^2),x_Symbol] :=
  Dist[1/(a*Sqrt[b/a]),Subst[Int[f[x],x],x,ArcTan[c*x]]] /;
FreeQ[{a,b,c,f},x] && c===Sqrt[b/a] *)


(* ::Subsubsection::Closed:: *)
(*v ArcTan[u]				Products of expressions and arctangents of inverse free functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcTan[u_],x_Symbol] :=
  x*ArcTan[u] -
  Int[Regularize[x*D[u,x]/(1+u^2),x],x] /;
InverseFunctionFreeQ[u,x] && 
	Not[MatchQ[u,c_.+d_.*f_^(a_.+b_.*x) /; FreeQ[{a,b,c,d,f},x]]]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ArcTan[u_],x_Symbol] :=
  x^(m+1)*ArcTan[u]/(m+1) -
  Dist[1/(m+1),Int[Regularize[x^(m+1)*D[u,x]/(1+u^2),x],x]] /;
FreeQ[m,x] && NonzeroQ[m+1] && InverseFunctionFreeQ[u,x] && 
	Not[FunctionOfQ[x^(m+1),u,x]] && 
	FalseQ[PowerVariableExpn[u,m+1,x]]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[v_*ArcTan[u_],x_Symbol] :=
  Module[{w=Block[{ShowSteps=False,StepCounter=Null}, Int[v,x]]},  
  w*ArcTan[u] -
  Int[Regularize[w*D[u,x]/(1+u^2),x],x] /;
 InverseFunctionFreeQ[w,x]] /;
InverseFunctionFreeQ[u,x] && 
	Not[MatchQ[v, x^m_. /; FreeQ[m,x]]] &&
	FalseQ[FunctionOfLinear[v*ArcTan[u],x]]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcTan[z] ==  I/2*Log[1-I*z] - I/2*Log[1+I*z]*)


Int[ArcTan[b_.*x_]/(c_+d_.*x_^n_.),x_Symbol] :=
  Dist[I/2,Int[Log[1-I*b*x]/(c+d*x^n),x]] -
  Dist[I/2,Int[Log[1+I*b*x]/(c+d*x^n),x]] /;
FreeQ[{b,c,d},x] && IntegerQ[n] && Not[n==2 && ZeroQ[b^2*c-d]]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcTan[z] ==  I/2*Log[1-I*z] - I/2*Log[1+I*z]*)


Int[ArcTan[a_+b_.*x_]/(c_+d_.*x_^n_.),x_Symbol] :=
  Dist[I/2,Int[Log[1-I*a-I*b*x]/(c+d*x^n),x]] -
  Dist[I/2,Int[Log[1+I*a+I*b*x]/(c+d*x^n),x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[n] && Not[n==1 && ZeroQ[a*d-b*c]]


(* ::Subsection::Closed:: *)
(*Arccotangent Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*ArcCot[a+b x^n]			Arccotangents of binomials*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.822.2, CRC 444, A&S 4.4.63*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcCot[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*ArcCot[a+b*x]/b + Log[1+(a+b*x)^2]/(2*b) /;
FreeQ[{a,b},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcCot[a_.+b_.*x_^n_],x_Symbol] :=
  x*ArcCot[a+b*x^n] +
  Dist[b*n,Int[x^n/(1+a^2+2*a*b*x^n+b^2*x^(2*n)),x]] /;
FreeQ[{a,b},x] && IntegerQ[n]


(* ::Subsubsection::Closed:: *)
(*x^m ArcCot[a+b x^n]			Products of monomials and arccotangents of binomials*)
(**)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: ArcCot[z] == I/2*Log[1-I/z] - I/2*Log[1+I/z]*)


Int[ArcCot[a_.+b_.*x_^n_.]/x_,x_Symbol] :=
  Dist[I/2,Int[Log[1-I/(a+b*x^n)]/x,x]] -
  Dist[I/2,Int[Log[1+I/(a+b*x^n)]/x,x]] /;
FreeQ[{a,b,n},x]


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* Int[ArcCot[a_.+b_.*x_]/x_,x_Symbol] :=
  Log[x]*ArcCot[a+b*x] +
  Dist[b,Int[Log[x]/(1+(a+b*x)^2),x]] /;
FreeQ[{a,b},x] *)


(* ::Item::Closed:: *)
(*Reference: G&R 2.852, CRC 458, A&S 4.4.71*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ArcCot[a_.+b_.*x_^n_.],x_Symbol] :=
  x^(m+1)*ArcCot[a+b*x^n]/(m+1) +
  Dist[b*n/(m+1),Int[x^(m+n)/(1+a^2+2*a*b*x^n+b^2*x^(2*n)),x]] /;
FreeQ[{a,b,m},x] && IntegerQ[n] && NonzeroQ[m+1] && NonzeroQ[m-n+1]


(* ::Subsubsection::Closed:: *)
(*(1+x^2)^m ArcCot[x]^n		Products of powers of binomials and powers of arccotangents*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[(1+x_^2)^m_*ArcCot[x_]^n_.,x_Symbol] :=
  Module[{u=Block[{ShowSteps=False,StepCounter=Null}, Int[(1+x^2)^m,x]]},
  u*ArcCot[x]^n +
  Dist[n,Int[u*ArcCot[x]^(n-1)/(1+x^2),x]]] /;
IntegerQ[{m,n}] && m<-1 && n>0


(* ::Subsubsection::Closed:: *)
(*(1+x^2)^m ArcCot[x]^n ArcTan[x]^p	Products of powers of binomials, arccotangents and arctangents*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcCot[x_]^n_.*ArcTan[x_]^p_/(1+x_^2),x_Symbol] :=
  ArcCot[x]^n*ArcTan[x]^(p+1)/(p+1) +
  Dist[n/(p+1),Int[ArcCot[x]^(n-1)*ArcTan[x]^(p+1)/(1+x^2),x]] /;
IntegerQ[{n,p}] && 0<n<p


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[(1+x_^2)^m_*ArcCot[x_]^n_.*ArcTan[x_]^p_.,x_Symbol] :=
  Module[{u=Block[{ShowSteps=False,StepCounter=Null}, Int[(1+x^2)^m,x]]},
  u*ArcCot[x]^n*ArcTan[x]^p -
  Dist[p,Int[u*ArcCot[x]^n*ArcTan[x]^(p-1)/(1+x^2),x]] +
  Dist[n,Int[u*ArcCot[x]^(n-1)*ArcTan[x]^p/(1+x^2),x]]] /;
IntegerQ[{m,p,n}] && m<-1 && p>0 && n>0


(* ::Subsubsection::Closed:: *)
(*x ArcCot[a+b x]^n			Products of x and powers of arccotangents of linears*)


Int[x_*ArcCot[b_.*x_]^n_,x_Symbol] :=
  ((b*x)^2+1)*ArcCot[b*x]^n/(2*b^2) +
  Dist[n/(2*b),Int[ArcCot[b*x]^(n-1),x]] /;
FreeQ[b,x] && RationalQ[n] && n>1


Int[x_*ArcCot[a_.+b_.*x_]^n_,x_Symbol] :=
  ((a+b*x)^2+1)*ArcCot[a+b*x]^n/(2*b^2) +
  Dist[n/(2*b),Int[ArcCot[a+b*x]^(n-1),x]] -
  Dist[a/b,Int[ArcCot[a+b*x]^n,x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n>1


(* ::Subsubsection::Closed:: *)
(*u ArcCot[c / (a+b x^n)]			Inverse cotangent of reciprocals of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcCot[z] == ArcTan[1/z]*)


Int[u_.*ArcCot[c_./(a_.+b_.*x_^n_.)]^m_.,x_Symbol] :=
  Int[u*ArcTan[a/c+b*x^n/c]^m,x] /;
FreeQ[{a,b,c,n,m},x]


(* ::Subsubsection::Closed:: *)
(*f[ArcCot[x]] (1+x^2)^n		Products of functions of arccotangents and its derivative*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: 1/(1+z^2) == -ArcCot'[z]*)


Int[u_*(a_+b_.*x_^2)^n_,x_Symbol] :=
  Dist[-a^n,Subst[Int[Regularize[Sin[x]^(-2*(n+1))*SubstFor[ArcCot[x],u,x],x],x],x,ArcCot[x]]] /;
FreeQ[{a,b},x] && FunctionOfQ[ArcCot[x],u,x] && ZeroQ[a-b] && IntegerQ[n] && n<-1


(* ::Item:: *)
(*Derivation: Integration by substitution*)


Int[u_*(a_+b_.*x_^2)^n_,x_Symbol] :=
  Dist[-1/a,Subst[Int[Regularize[(a*Csc[x]^2)^(n+1)*SubstFor[ArcCot[x],u,x],x],x],x,ArcCot[x]]] /;
FreeQ[{a,b},x] && FunctionOfQ[ArcCot[x],u,x] && ZeroQ[a-b] && HalfIntegerQ[n] && n<-1


(* ::Subsubsection::Closed:: *)
(*x^m f[ArcCot[x]] (1+x^2)^n		Products of monomials, functions of arccotangents and its derivative*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: 1/(1+z^2) == -ArcCot'[z]*)


Int[x_^m_.*u_*(a_+b_.*x_^2)^n_,x_Symbol] :=
  Dist[-a^n,Subst[Int[Regularize[Cot[x]^m*Sin[x]^(-2*(n+1))*SubstFor[ArcCot[x],u,x],x],x],x,ArcCot[x]]] /;
FreeQ[{a,b},x] && FunctionOfQ[ArcCot[x],u,x] && ZeroQ[a-b] && IntegerQ[{m,n}] && n<0


(* ::Item:: *)
(*Derivation: Integration by substitution*)


(* ??? *)
Int[x_^m_.*u_*(a_+b_.*x_^2)^n_,x_Symbol] :=
  Dist[-a^n,Subst[Int[Regularize[Cot[x]^m*Sin[x]^(-2*(n+1))*SubstFor[ArcCot[x],u,x],x],x],x,ArcCot[x]]] /;
FreeQ[{a,b},x] && FunctionOfQ[ArcCot[x],u,x] && ZeroQ[a-b] && HalfIntegerQ[n] && n<-1 && 
	PositiveQ[a] && IntegerQ[m]


(* ::Subsubsection::Closed:: *)
(*v ArcCot[u]				Products of expressions and arccotangents of inverse free functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcCot[u_],x_Symbol] :=
  x*ArcCot[u] +
  Int[Regularize[x*D[u,x]/(1+u^2),x],x] /;
InverseFunctionFreeQ[u,x] && 
	Not[MatchQ[u,c_.+d_.*f_^(a_.+b_.*x) /; FreeQ[{a,b,c,d,f},x]]]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ArcCot[u_],x_Symbol] :=
  x^(m+1)*ArcCot[u]/(m+1) +
  Dist[1/(m+1),Int[Regularize[x^(m+1)*D[u,x]/(1+u^2),x],x]] /;
FreeQ[m,x] && NonzeroQ[m+1] && InverseFunctionFreeQ[u,x] && 
	Not[FunctionOfQ[x^(m+1),u,x]] && 
	FalseQ[PowerVariableExpn[u,m+1,x]]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[v_*ArcCot[u_],x_Symbol] :=
  Module[{w=Block[{ShowSteps=False,StepCounter=Null}, Int[v,x]]},  
  w*ArcCot[u] +
  Int[Regularize[w*D[u,x]/(1+u^2),x],x] /;
 InverseFunctionFreeQ[w,x]] /;
InverseFunctionFreeQ[u,x] && 
	Not[MatchQ[v, x^m_. /; FreeQ[m,x]]] &&
	FalseQ[FunctionOfLinear[v*ArcCot[u],x]]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcCot[z] ==  I/2*Log[1-I/z] - I/2*Log[1+I/z]*)


Int[ArcCot[b_.*x_]/(c_+d_.*x_^n_.),x_Symbol] :=
  Dist[I/2,Int[Log[1-I/(b*x)]/(c+d*x^n),x]] -
  Dist[I/2,Int[Log[1+I/(b*x)]/(c+d*x^n),x]] /;
FreeQ[{b,c,d},x] && IntegerQ[n] && Not[n==2 && ZeroQ[b^2*c-d]]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcCot[z] ==  I/2*Log[1-I/z] - I/2*Log[1+I/z]*)


Int[ArcCot[a_+b_.*x_]/(c_+d_.*x_^n_.),x_Symbol] :=
  Dist[I/2,Int[Log[1-I/(a+b*x)]/(c+d*x^n),x]] -
  Dist[I/2,Int[Log[1+I/(a+b*x)]/(c+d*x^n),x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[n] && Not[n==1 && ZeroQ[a*d-b*c]]


(* ::Subsection::Closed:: *)
(*Arcsecant Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*ArcSec[a+b x]^n			Powers of arcsecants of linear binomials*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.821.2, CRC 445', A&S 4.4.62'*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcSec[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*ArcSec[a+b*x]/b - 
  Int[1/((a+b*x)*Sqrt[1-1/(a+b*x)^2]),x] /;
FreeQ[{a,b},x]


(* ::Subsubsection::Closed:: *)
(*x^m ArcSec[a+b x]			Products of monomials and arcsecants of binomials*)


Int[ArcSec[a_.*x_^n_.]/x_,x_Symbol] :=
  I*ArcSec[a*x^n]^2/(2*n) - 
  ArcSec[a*x^n]*Log[1-1/(I/(x^n*a)+Sqrt[1-1/(x^(2*n)*a^2)])^2]/n + 
  I*PolyLog[2,1/(I/(x^n*a)+Sqrt[1-1/(x^(2*n)*a^2)])^2]/(2*n) /;
(* Sqrt[-1/a^2]*a*ArcCsc[a*x^n]^2/(2*n) + 
  Pi*Log[x]/2 - 
  Sqrt[-1/a^2]*a*ArcSinh[Sqrt[-1/a^2]/x^n]*Log[1-1/(Sqrt[-(1/a^2)]/x^n+Sqrt[1-1/(x^(2*n)*a^2)])^2]/n + 
  Sqrt[-1/a^2]*a*PolyLog[2, 1/(Sqrt[-1/a^2]/x^n+Sqrt[1-1/(x^(2*n)*a^2)])^2]/(2*n) *)
FreeQ[{a,n},x]


(* ::Item:: *)
(*Derivation: Integration by substitution*)


Int[x_^m_.*ArcSec[a_+b_.*x_],x_Symbol] :=
  Dist[1/b,Subst[Int[(-a/b+x/b)^m*ArcSec[x],x],x,a+b*x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0


(* ::Item::Closed:: *)
(*Reference: CRC 474*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ArcSec[a_.*x_],x_Symbol] :=
  x^(m+1)*ArcSec[a*x]/(m+1) - 
  Dist[1/(a*(m+1)),Int[x^(m-1)/Sqrt[1-1/(a*x)^2],x]] /;
FreeQ[{a,m},x] && NonzeroQ[m+1]


(* ::Item::Closed:: *)
(*Reference: CRC 474*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ArcSec[a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*ArcSec[a+b*x]/(m+1) - 
  Dist[b/(m+1),Int[x^(m+1)/(Sqrt[1-1/(a+b*x)^2]*(a+b*x)^2),x]] /;
FreeQ[{a,b,m},x] && NonzeroQ[m+1]


(* ::Subsubsection::Closed:: *)
(*u ArcSec[c / (a+b x^n)]^m		Powers of arcsecants of reciprocals of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcSec[z] == ArcCos[1/z]*)


Int[u_.*ArcSec[c_./(a_.+b_.*x_^n_.)]^m_.,x_Symbol] :=
  Int[u*ArcCos[a/c+b*x^n/c]^m,x] /;
FreeQ[{a,b,c,n,m},x]


(* ::Subsubsection::Closed:: *)
(*v ArcSec[u]				Products of expressions and arcsecants of inverse free functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcSec[u_],x_Symbol] :=
  x*ArcSec[u] -
  Int[Regularize[x*D[u,x]/(u^2*Sqrt[1-1/u^2]),x],x] /;
InverseFunctionFreeQ[u,x] && Not[MatchQ[u,c_.+d_.*f_^(a_.+b_.*x) /; FreeQ[{a,b,c,d,f},x]]]


(* ::Subsection::Closed:: *)
(*Arccosecant Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*ArcCsc[a+b x]^n			Powers of arcsecants of linear binomials*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.821.1, CRC 446', A&S 4.4.61'*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcCsc[a_.+b_.*x_],x_Symbol] :=
  (a+b*x)*ArcCsc[a+b*x]/b +
  Int[1/((a+b*x)*Sqrt[1-1/(a+b*x)^2]),x] /;
FreeQ[{a,b},x]


(* ::Subsubsection::Closed:: *)
(*x^m ArcCsc[a+b x]			Products of monomials and arccosecants of binomials*)


Int[ArcCsc[a_.*x_^n_.]/x_,x_Symbol] :=
(* Int[ArcSin[1/a*x^(-n)]/x,x] /; *)
  I*ArcCsc[a*x^n]^2/(2*n) - 
  ArcCsc[a*x^n]*Log[1-(I/(x^n*a)+Sqrt[1-1/(x^(2*n)*a^2)])^2]/n + 
  I*PolyLog[2,(I/(x^n*a)+Sqrt[1-1/(x^(2*n)*a^2)])^2]/(2*n) /;
(* -Sqrt[-1/a^2]*a*ArcCsc[a*x^n]^2/(2*n) - 
  ArcCsc[a*x^n]*Log[2*(1/(x^n*a^2) + Sqrt[-1/a^2]*Sqrt[1-1/(x^(2*n)*a^2)])/x^n]/n - 
  Sqrt[-1/a^2]*a*PolyLog[2, 1-2*(1/(x^n*a^2)+Sqrt[-1/a^2]*Sqrt[1-1/(x^(2*n)*a^2)])/x^n]/(2*n) /; *)
FreeQ[{a,n},x]


(* ::Item:: *)
(*Derivation: Integration by substitution*)


Int[x_^m_.*ArcCsc[a_+b_.*x_],x_Symbol] :=
  Dist[1/b,Subst[Int[(-a/b+x/b)^m*ArcCsc[x],x],x,a+b*x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0


(* ::Item::Closed:: *)
(*Reference: CRC 477*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ArcCsc[a_.*x_],x_Symbol] :=
  x^(m+1)*ArcCsc[a*x]/(m+1) + 
  Dist[1/(a*(m+1)),Int[x^(m-1)/Sqrt[1-1/(a*x)^2],x]] /;
FreeQ[{a,m},x] && NonzeroQ[m+1]


(* ::Item::Closed:: *)
(*Reference: CRC 477*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*ArcCsc[a_.+b_.*x_],x_Symbol] :=
  x^(m+1)*ArcCsc[a+b*x]/(m+1) + 
  Dist[b/(m+1),Int[x^(m+1)/(Sqrt[1-1/(a+b*x)^2]*(a+b*x)^2),x]] /;
FreeQ[{a,b,m},x] && NonzeroQ[m+1]


(* ::Subsubsection::Closed:: *)
(*u ArcCsc[c / (a+b x^n)]			Inverse cosecant of reciprocals of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: ArcCsc[z] == ArcSin[1/z]*)


Int[u_.*ArcCsc[c_./(a_.+b_.*x_^n_.)]^m_.,x_Symbol] :=
  Int[u*ArcSin[a/c+b*x^n/c]^m,x] /;
FreeQ[{a,b,c,n,m},x]


(* ::Subsubsection::Closed:: *)
(*v ArcCsc[u]				Products of expressions and arccosecants of inverse free functions*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[ArcCsc[u_],x_Symbol] :=
  x*ArcCsc[u] +
  Int[Regularize[x*D[u,x]/(u^2*Sqrt[1-1/u^2]),x],x] /;
InverseFunctionFreeQ[u,x] && Not[MatchQ[u,c_.+d_.*f_^(a_.+b_.*x) /; FreeQ[{a,b,c,d,f},x]]]
