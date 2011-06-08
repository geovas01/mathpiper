(* ::Package:: *)

(* ::Title:: *)
(*Trig Function Integration Rules*)


(* ::Subsection::Closed:: *)
(*Sine Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*Sin[a+b x]^n				Powers of sines of linears*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.01.5, CRC 290, A&S 4.3.113*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: Cos'[z] == -Sin[z]*)


Int[Sin[a_.+b_.*x_],x_Symbol] :=
  -Cos[a+b*x]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Reference: G&R 2.513.5, CRC 296*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[Sin[a_.+b_.*x_]^2,x_Symbol] :=
  x/2 - Cos[a+b*x]*Sin[a+b*x]/(2*b) /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is odd, Sin[z]^n == -(1-Cos[z]^2)^((n-1)/2)*Cos'[z]*)


Int[Sin[a_.+b_.*x_]^n_,x_Symbol] :=
  Dist[-1/b,Subst[Int[Regularize[(1-x^2)^((n-1)/2),x],x],x,Cos[a+b*x]]] /;
FreeQ[{a,b},x] && OddQ[n] && n>1


(* ::ItemParagraph::Closed:: *)
(**)


(* ::Item:: *)
(*Reference: G&R 2.510.2, CRC 299*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[(c_.*Sin[a_.+b_.*x_])^n_,x_Symbol] :=
  -c*Cos[a+b*x]*(c*Sin[a+b*x])^(n-1)/(b*n) + 
  Dist[(n-1)*c^2/n,Int[(c*Sin[a+b*x])^(n-2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n>1 && Not[OddQ[n]]


(* ::Item::Closed:: *)
(*Reference: G&R 2.510.3, CRC 309*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[(c_.*Sin[a_.+b_.*x_])^n_,x_Symbol] :=
  Cos[a+b*x]*(c*Sin[a+b*x])^(n+1)/(c*b*(n+1)) + 
  Dist[(n+2)/((n+1)*c^2),Int[(c*Sin[a+b*x])^(n+2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n<-1


(* ::Item::Closed:: *)
(*Derivation: Extract constant factor*)


(* ::Item:: *)
(*Basis: D[(c*Sin[x])^n/Sin[x]^n,x] == 0*)


Int[(c_*Sin[a_.+b_.*x_])^n_,x_Symbol] :=
  (c*Sin[a+b*x])^n/Sin[a+b*x]^n*Int[Sin[a+b*x]^n,x] /;
FreeQ[{a,b,c},x] && RationalQ[n] && -1<n<1


(* ::Subsubsection::Closed:: *)
(*(a+b Sin[c+d x])^n			Powers of linear binomials of sines of linears*)


(* ::Item:: *)
(*Reference: G&R 2.555.3', A&S 4.3.132'*)


Int[1/(a_+b_.*Sin[c_.+d_.*x_]),x_Symbol] :=
  -Cos[c+d*x]/(d*(b+a*Sin[c+d*x])) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2-b^2]


(* ::Item:: *)
(*Reference: G&R 2.551.3a, A&S 4.3.131a*)


Int[1/(a_+b_.*Sin[c_.+d_.*x_]),x_Symbol] :=
  2*ArcTan[(b+a*Tan[(c+d*x)/2])/Rt[a^2-b^2,2]]/(d*Rt[a^2-b^2,2]) /;
FreeQ[{a,b,c,d},x] && PosQ[a^2-b^2]


(* ::Item:: *)
(*Reference: G&R 2.551.3b', A&S 4.3.131b'*)


Int[1/(a_+b_.*Sin[c_.+d_.*x_]),x_Symbol] :=
  -2*ArcTanh[(b+a*Tan[(c+d*x)/2])/Rt[b^2-a^2,2]]/(d*Rt[b^2-a^2,2]) /;
FreeQ[{a,b,c,d},x] && NegQ[a^2-b^2]


(* ::ItemParagraph:: *)
(**)


Int[Sqrt[a_+b_.*Sin[c_.+d_.*x_]],x_Symbol] :=
  -2*b*Cos[c+d*x]/(d*Sqrt[a+b*Sin[c+d*x]]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2-b^2]


(* ::Item:: *)
(*Basis: D[EllipticE[x,n],x] == Sqrt[1-n*Sin[x]^2]*)


Int[Sqrt[a_.+b_.*Sin[c_.+d_.*x_]],x_Symbol] :=
  2*Sqrt[a+b]*EllipticE[(-Pi/2+c+d*x)/2,2*b/(a+b)]/d /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2] && PositiveQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Extract constant factor*)


(* ::Item:: *)
(*Basis: D[Sqrt[a+b*f[c+d*x]]/Sqrt[(a+b*f[c+d*x])/(a+b)],x] == 0*)


Int[Sqrt[a_.+b_.*Sin[c_.+d_.*x_]],x_Symbol] :=
  Sqrt[a+b*Sin[c+d*x]]/Sqrt[(a+b*Sin[c+d*x])/(a+b)]*Int[Sqrt[a/(a+b)+b/(a+b)*Sin[c+d*x]],x] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2] && Not[PositiveQ[a+b]]


(* ::ItemParagraph:: *)
(**)


Int[1/Sqrt[a_+b_.*Sin[c_.+d_.*x_]],x_Symbol] :=
  -2*ArcTanh[Cos[(c-Pi/2+d*x)/2]]*Sin[(c-Pi/2+d*x)/2]/(d*Sqrt[a+b*Sin[c+d*x]]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a+b]


Int[1/Sqrt[a_+b_.*Sin[c_.+d_.*x_]],x_Symbol] :=
  2*ArcTanh[Sin[(c-Pi/2+d*x)/2]]*Cos[(c-Pi/2+d*x)/2]/(d*Sqrt[a+b*Sin[c+d*x]]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a-b]


(* ::Item:: *)
(*Basis: D[EllipticF[x,n],x] == 1/Sqrt[1-n*Sin[x]^2]*)


Int[1/Sqrt[a_.+b_.*Sin[c_.+d_.*x_]],x_Symbol] :=
  2*EllipticF[(-Pi/2+c+d*x)/2,2*b/(a+b)]/(d*Sqrt[a+b]) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2] && PositiveQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Extract constant factor*)


(* ::Item:: *)
(*Basis: D[Sqrt[(a+b*f[c+d*x])/(a+b)]/Sqrt[a+b*f[c+d*x]],x] == 0*)


Int[1/Sqrt[a_+b_.*Sin[c_.+d_.*x_]],x_Symbol] :=
  Sqrt[(a+b*Sin[c+d*x])/(a+b)]/Sqrt[a+b*Sin[c+d*x]]*Int[1/Sqrt[a/(a+b)+b/(a+b)*Sin[c+d*x]],x] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2] && Not[PositiveQ[a+b]]


(* ::ItemParagraph:: *)
(**)


Int[(a_+b_.*Sin[c_.+d_.*x_])^n_,x_Symbol] :=
  -b*Cos[c+d*x]*(a+b*Sin[c+d*x])^(n-1)/(d*n) +
  Dist[a*(2*n-1)/n,Int[(a+b*Sin[c+d*x])^(n-1),x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n>1 && ZeroQ[a^2-b^2]


(* ::Item:: *)
(*Reference: G&R 2.555.1'*)


Int[(a_+b_.*Sin[c_.+d_.*x_])^n_,x_Symbol] :=
  b*Cos[c+d*x]*(a+b*Sin[c+d*x])^n/(a*d*(2*n+1)) +
  Dist[(n+1)/(a*(2*n+1)),Int[(a+b*Sin[c+d*x])^(n+1),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n<-1 && ZeroQ[a^2-b^2]


(* ::Item:: *)
(*Reference: G&R 2.552.3 inverted*)


(* Note: This would result in an infinite loop!!! *)
(* Int[(a_+b_.*Sin[c_.+d_.*x_])^n_,x_Symbol] :=
  b*Cos[c+d*x]*(a+b*Sin[c+d*x])^n/(a*d*n) +
  Dist[(a^2-b^2)/a,Int[(a+b*Sin[c+d*x])^(n-1),x]] +
  Dist[b*(n+1)/(a*n),Int[Sin[c+d*x]*(a+b*Sin[c+d*x])^n,x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n>0 && NonzeroQ[a^2-b^2] *)


(* ::Item:: *)
(*Reference: G&R 2.552.3*)


Int[1/(a_+b_.*Sin[c_.+d_.*x_])^2,x_Symbol] :=
  b*Cos[c+d*x]/(d*(a^2-b^2)*(a+b*Sin[c+d*x])) + 
  Dist[a/(a^2-b^2),Int[1/(a+b*Sin[c+d*x]),x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2]


(* ::Item:: *)
(*Reference: G&R 2.552.3*)


Int[(a_+b_.*Sin[c_.+d_.*x_])^n_,x_Symbol] :=
  -b*Cos[c+d*x]*(a+b*Sin[c+d*x])^(n+1)/(d*(n+1)*(a^2-b^2)) +
  Dist[1/((n+1)*(a^2-b^2)),Int[(a*(n+1)-b*(n+2)*Sin[c+d*x])*(a+b*Sin[c+d*x])^(n+1),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n<-1 && NonzeroQ[a^2-b^2]


(* ::Subsubsection::Closed:: *)
(*x^m (a+b Sin[c+d x])^n		Products of monomials and powers of linear binomials of sines of linears*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1+Sin[z]==2*Sin[z/2+Pi/4]^2==2*Cos[z/2-Pi/4]^2*)


Int[x_^m_.*(a_+b_.*Sin[c_.+d_.*x_])^n_,x_Symbol] :=
(*Dist[(2*a)^n,Int[x^m*Sin[Pi/4+c/2+d*x/2]^(2*n),x]] /; *)
  Dist[(2*a)^n,Int[x^m*Cos[-Pi/4+c/2+d*x/2]^(2*n),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[m] && IntegerQ[n] && n<0 && ZeroQ[a-b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1+Sin[z]==2*Sin[z/2+Pi/4]^2==2*Cos[z/2-Pi/4]^2*)


Int[x_^m_.*(a_+b_.*Sin[c_.+d_.*x_])^n_,x_Symbol] :=
(*Dist[2^n,Int[x^m*(a*Sin[Pi/4+c/2+d*x/2]^2)^n,x]] /;*)
  Dist[2^n,Int[x^m*(a*Cos[-Pi/4+c/2+d*x/2]^2)^n,x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[{m,n}] && ZeroQ[a-b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1-Sin[z]==2*Sin[z/2-Pi/4]^2==2*Cos[z/2+Pi/4]^2*)


Int[x_^m_.*(a_+b_.*Sin[c_.+d_.*x_])^n_,x_Symbol] :=
(*Dist[(2*a)^n,Int[x^m*Sin[-Pi/4+c/2+d*x/2]^(2*n),x]] /; *)
  Dist[(2*a)^n,Int[x^m*Cos[Pi/4+c/2+d*x/2]^(2*n),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[m] && IntegerQ[n] && n<0 && ZeroQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1-Sin[z]==2*Sin[z/2-Pi/4]^2==2*Cos[z/2+Pi/4]^2*)


Int[x_^m_.*(a_+b_.*Sin[c_.+d_.*x_])^n_,x_Symbol] :=
(*Dist[2^n,Int[x^m*(a*Sin[-Pi/4+c/2+d*x/2]^2)^n,x]] /; *)
  Dist[2^n,Int[x^m*(a*Cos[Pi/4+c/2+d*x/2]^2)^n,x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[{m,n}] && ZeroQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: 1/(a+b z)^2 == a/((a^2-b^2) (a+b z)) - b (b+a z)/((a^2-b^2) (a+b z)^2)*)


Int[x_/(a_+b_.*Sin[c_.+d_.*x_])^2,x_Symbol] :=
  Dist[a/(a^2-b^2),Int[x/(a+b*Sin[c+d*x]),x]] -
  Dist[b/(a^2-b^2),Int[x*(b+a*Sin[c+d*x])/(a+b*Sin[c+d*x])^2,x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: a+b*Sin[z] == (I*b+2*a*E^(I*z)-I*b*E^(2*I*z))/(2*E^(I*z))*)


Int[x_^m_.*(a_+b_.*Sin[c_.+d_.*x_])^n_,x_Symbol] :=
  Dist[1/2^n,Int[x^m*(I*b+2*a*E^(I*c+I*d*x)-I*b*E^(2*(I*c+I*d*x)))^n/E^(n*(I*c+I*d*x)),x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2] && IntegerQ[n] && n<0 && RationalQ[m] && m>0


(* ::Subsubsection::Closed:: *)
(*(A+B Sin[c+d x]) (a+b Sin[c+d x])^n	Products of powers of linear binomials of sines*)


(* ::Item:: *)
(*Basis: (A+B*z)/Sqrt[a+b*z] == (b*A-a*B)/(b*Sqrt[a+b*z]) + B/b*Sqrt[a+b*z]*)


Int[(A_.+B_.*Sin[c_.+d_.*x_])/Sqrt[a_+b_.*Sin[c_.+d_.*x_]],x_Symbol] :=
  Dist[(b*A-a*B)/b,Int[1/Sqrt[a+b*Sin[c+d*x]],x]] +
  Dist[B/b,Int[Sqrt[a+b*Sin[c+d*x]],x]] /;
FreeQ[{a,b,c,d,A,B},x] && NonzeroQ[b*A-a*B]


(* ::Item:: *)
(*Reference: G&R 2.551.1 inverted*)


Int[(A_.+B_.*Sin[c_.+d_.*x_])*(a_+b_.*Sin[c_.+d_.*x_])^n_,x_Symbol] :=
  -B*Cos[c+d*x]*(a+b*Sin[c+d*x])^n/(d*(n+1)) + 
  Dist[1/(n+1),Int[(b*B*n+a*A*(n+1) + (a*B*n+b*A*(n+1))*Sin[c+d*x])*(a+b*Sin[c+d*x])^(n-1), x]] /;
FreeQ[{a,b,c,d,A,B},x] && RationalQ[n] && n>1 && NonzeroQ[a^2-b^2]


(* ::Item:: *)
(*Reference: G&R 2.551.1 special case*)


Int[(A_+B_.*Sin[c_.+d_.*x_])/(a_+b_.*Sin[c_.+d_.*x_])^2,x_Symbol] :=
  -B*Cos[c+d*x]/(a*d*(a+b*Sin[c+d*x])) /;
FreeQ[{a,b,c,d,A,B},x] && ZeroQ[a*A-b*B]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_*(A_+B_.*Sin[c_.+d_.*x_])/(a_+b_.*Sin[c_.+d_.*x_])^2,x_Symbol] :=
  -B*x*Cos[c+d*x]/(a*d*(a+b*Sin[c+d*x])) +
  Dist[B/(a*d),Int[Cos[c+d*x]/(a+b*Sin[c+d*x]),x]] /;
FreeQ[{a,b,c,d,A,B},x] && ZeroQ[a*A-b*B]


(* ::Item:: *)
(*Reference: G&R 2.551.1*)


Int[(A_.+B_.*Sin[c_.+d_.*x_])*(a_.+b_.*Sin[c_.+d_.*x_])^n_,x_Symbol] :=
  (a*B-b*A)*Cos[c+d*x]*(a+b*Sin[c+d*x])^(n+1)/(d*(n+1)*(a^2-b^2)) +
  Dist[1/((n+1)*(a^2-b^2)),Int[((n+1)*(a*A-b*B)+(n+2)*(a*B-b*A)*Sin[c+d*x])*(a+b*Sin[c+d*x])^(n+1),x]] /;
FreeQ[{a,b,c,d,A,B},x] && RationalQ[n] && n<-1 && NonzeroQ[a^2-b^2]


(* ::Subsubsection::Closed:: *)
(*x^m (a+b Sin[c+d x]^2)^n		Products of monomials and powers of quadratic binomials of sines of linears*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Sin[z]^2 == (1 - Cos[2*z])/2*)


Int[x_^m_./(a_+b_.*Sin[c_.+d_.*x_]^2),x_Symbol] :=
  Dist[2,Int[x^m/(2*a+b-b*Cos[2*c+2*d*x]),x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m>0 && NonzeroQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: a+b*Cos[z]^2+c*Sin[z]^2 == (2*a+b+c + (b-c)*Cos[2*z])/2*)


Int[x_^m_./(a_.+b_.*Cos[d_.+e_.*x_]^2+c_.*Sin[d_.+e_.*x_]^2),x_Symbol] :=
  Dist[2,Int[x^m/(2*a+b+c+(b-c)*Cos[2*d+2*e*x]),x]] /;
FreeQ[{a,b,c,d,e},x] && IntegerQ[m] && m>0 && NonzeroQ[a+b] && NonzeroQ[a+c]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Sin[z]^2 == (1 - Cos[2*z])/2*)


Int[(a_+b_.*Sin[c_.+d_.*x_]^2)^n_,x_Symbol] :=
  Dist[1/2^n,Int[(2*a+b-b*Cos[2*c+2*d*x])^n,x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a+b] && HalfIntegerQ[n]


(* ::Subsubsection::Closed:: *)
(*x^m (a+b Sin[c+d x] Cos[c+d x])^n	Products of monomials and powers involving products of sines and cosines*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Sin[z]*Cos[z] == Sin[2*z]/2*)


Int[x_^m_./(a_+b_.*Sin[c_.+d_.*x_]*Cos[c_.+d_.*x_]),x_Symbol] :=
  Int[x^m/(a+b*Sin[2*c+2*d*x]/2),x] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m>0


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Sin[z]*Cos[z] == Sin[2*z]/2*)


Int[(a_+b_.*Sin[c_.+d_.*x_]*Cos[c_.+d_.*x_])^n_,x_Symbol] :=
  Int[(a+b*Sin[2*c+2*d*x]/2)^n,x] /;
FreeQ[{a,b,c,d},x] && HalfIntegerQ[n]


(* ::Subsubsection::Closed:: *)
(*Sin[a+b x]^m Cos[a+b x]^n		Products of powers of sines and cosines*)


Int[Sin[a_.+b_.*x_]^m_.*Cos[a_.+b_.*x_]^n_,x_Symbol] :=
  Sin[a+b*x]^(m+1)*Cos[a+b*x]^(n+1)/(b*(m+1)) /;
FreeQ[{a,b,m,n},x] && ZeroQ[m+n+2] && NonzeroQ[m+1] && PosQ[m]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is odd, Cos[z]^n == (1-Sin[z]^2)^((n-1)/2)*Sin'[z]*)


Int[Sin[a_.+b_.*x_]^m_*Cos[a_.+b_.*x_]^n_,x_Symbol] :=
  Dist[1/b,Subst[Int[Regularize[x^m*(1-x^2)^((n-1)/2),x],x],x,Sin[a+b*x]]] /;
FreeQ[{a,b,m},x] && OddQ[n] && Not[OddQ[m] && 0<m<n]


(* ::Item:: *)
(*Reference: G&R 2.510.1*)


Int[Sin[a_.+b_.*x_]^m_*Cos[a_.+b_.*x_]^n_,x_Symbol] :=
  -Sin[a+b*x]^(m-1)*Cos[a+b*x]^(n+1)/(b*(n+1)) +
  Dist[(m-1)/(n+1),Int[Sin[a+b*x]^(m-2)*Cos[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m>1 && n<-1


(* ::Item:: *)
(*Reference: G&R 2.510.2, CRC 323b, A&S 4.3.127b*)


Int[Sin[a_.+b_.*x_]^m_*Cos[a_.+b_.*x_]^n_,x_Symbol] :=
  -Sin[a+b*x]^(m-1)*Cos[a+b*x]^(n+1)/(b*(m+n)) +
  Dist[(m-1)/(m+n),Int[Sin[a+b*x]^(m-2)*Cos[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m>1 && Not[OddQ[m]] && NonzeroQ[m+n] &&
Not[OddQ[n] && n>1]


(* ::Item:: *)
(*Reference: G&R 2.510.3, CRC 334a, A&S 4.3.128b*)


Int[Sin[a_.+b_.*x_]^m_*Cos[a_.+b_.*x_]^n_,x_Symbol] :=
  Sin[a+b*x]^(m+1)*Cos[a+b*x]^(n+1)/(b*(m+1)) +
  Dist[(m+n+2)/(m+1),Int[Sin[a+b*x]^(m+2)*Cos[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m<-1 && NonzeroQ[m+n+2]


(* Note: Kool rule, but replace with a more general collect fractional power rule?! *)
Int[Sin[a_.+b_.*x_]^m_*Cos[a_.+b_.*x_]^n_,x_Symbol] :=
  Dist[1/(b*m),Subst[Int[x^(1/m)/(1+x^(2/m)),x],x,Sin[a+b*x]^m*Cos[a+b*x]^n]] /;
FreeQ[{a,b},x] && FractionQ[{m,n}] && ZeroQ[m+n] && IntegerQ[1/m] && m>0


(* ::Subsubsection::Closed:: *)
(*Sin[a+b x]^m Tan[a+b x]^n		Products of powers of sines and tangents*)
(**)


(* ::Item::Closed:: *)
(*Reference: G&R 2.526.18', CRC 327'*)


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sin[z]*Tan[z] == -Cos[z]+Sec[z]*)


Int[Sin[a_.+b_.*x_]*Tan[a_.+b_.*x_],x_Symbol] :=
  -Sin[a+b*x]/b + 
  Int[Sec[a+b*x],x] /;
FreeQ[{a,b},x]


Int[Sin[a_.+b_.*x_]^m_*Tan[a_.+b_.*x_]^n_,x_Symbol] :=
  -Sin[a+b*x]^m*Tan[a+b*x]^(n-1)/(b*m) /;
FreeQ[{a,b,m,n},x] && ZeroQ[m+n-1]


(* ::Item:: *)
(*Derivation: Integration by substitution*)


Int[Sin[a_.+b_.*x_]^m_.*Tan[a_.+b_.*x_]^n_.,x_Symbol] :=
  Dist[-1/b,Subst[Int[Regularize[(1-x^2)^((m+n-1)/2)/x^n,x],x],x,Cos[a+b*x]]] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && OddQ[m+n]


(* ::Item:: *)
(*Reference: G&R 2.510.5, CRC 323a*)


Int[Sin[a_.+b_.*x_]^m_*Tan[a_.+b_.*x_]^n_,x_Symbol] :=
  Sin[a+b*x]^m*Tan[a+b*x]^(n+1)/(b*m) -
  Dist[(n+1)/m,Int[Sin[a+b*x]^(m-2)*Tan[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m>1 && n<-1 && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.510.6, CRC 334b*)


Int[Sin[a_.+b_.*x_]^m_*Tan[a_.+b_.*x_]^n_,x_Symbol] :=
  Sin[a+b*x]^(m+2)*Tan[a+b*x]^(n-1)/(b*(n-1)) -
  Dist[(m+2)/(n-1),Int[Sin[a+b*x]^(m+2)*Tan[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m<-1 && n>1 && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.510.2, CRC 323b*)


Int[Sin[a_.+b_.*x_]^m_*Tan[a_.+b_.*x_]^n_.,x_Symbol]:=
  -Sin[a+b*x]^m*Tan[a+b*x]^(n-1)/(b*m) +
  Dist[(m+n-1)/m,Int[Sin[a+b*x]^(m-2)*Tan[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m>1 && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.510.1*)


Int[Sin[a_.+b_.*x_]^m_.*Tan[a_.+b_.*x_]^n_,x_Symbol] :=
  Sin[a+b*x]^m*Tan[a+b*x]^(n-1)/(b*(n-1)) -
  Dist[(m+n-1)/(n-1),Int[Sin[a+b*x]^m*Tan[a+b*x]^(n-2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n>1 && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.510.3, CRC 334a*)


Int[Sin[a_.+b_.*x_]^m_*Tan[a_.+b_.*x_]^n_.,x_Symbol]:=
  Sin[a+b*x]^(m+2)*Tan[a+b*x]^(n-1)/(b*(m+n+1)) +
  Dist[(m+2)/(m+n+1),Int[Sin[a+b*x]^(m+2)*Tan[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m<-1 && NonzeroQ[m+n+1] && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.510.4*)


Int[Sin[a_.+b_.*x_]^m_.*Tan[a_.+b_.*x_]^n_,x_Symbol]:=
  Sin[a+b*x]^m*Tan[a+b*x]^(n+1)/(b*(m+n+1)) -
  Dist[(n+1)/(m+n+1),Int[Sin[a+b*x]^m*Tan[a+b*x]^(n+2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n<-1 && NonzeroQ[m+n+1] && Not[OddQ[m] && EvenQ[n]]


(* ::Subsubsection::Closed:: *)
(*Sin[a+b x^n]				Sines of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: FresnelS'[z] == Sin[Pi*z^2/2]*)


Int[Sin[b_.*x_^2],x_Symbol] :=
  Sqrt[Pi/2]*FresnelS[Rt[b,2]*x/Sqrt[Pi/2]]/Rt[b,2] /;
FreeQ[b,x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sin[w+z] == Sin[w]*Cos[z] + Cos[w]*Sin[z]*)


Int[Sin[a_+b_.*x_^2],x_Symbol] :=
  Dist[Sin[a],Int[Cos[b*x^2],x]] + 
  Dist[Cos[a],Int[Sin[b*x^2],x]] /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sin[z] == I/2*E^(-I*z) - I/2*E^(I*z)*)


Int[Sin[a_.+b_.*x_^n_],x_Symbol] :=
  Dist[I/2,Int[E^(-a*I-b*I*x^n),x]] - 
  Dist[I/2,Int[E^(a*I+b*I*x^n),x]] /;
FreeQ[{a,b,n},x] && Not[FractionOrNegativeQ[n]]


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* Note: Although resulting integrand looks more complicated than original one, rules for 
	improper binomials rectify it. *)
Int[Sin[a_.+b_.*x_^n_],x_Symbol] :=
  x*Sin[a+b*x^n] -
  Dist[b*n,Int[x^n*Cos[a+b*x^n],x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && n<0


(* ::Subsubsection::Closed:: *)
(*x^m Sin[a+b x^n]			Products of monomials and sines of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: SinIntegral'[z] == Sin[z]/z*)


Int[Sin[a_.*x_^n_.]/x_,x_Symbol] :=
  SinIntegral[a*x^n]/n /;
FreeQ[{a,n},x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sin[w+z] == Sin[w]*Cos[z] + Cos[w]*Sin[z]*)


Int[Sin[a_+b_.*x_^n_.]/x_,x_Symbol] :=
  Dist[Sin[a],Int[Cos[b*x^n]/x,x]] + 
  Dist[Cos[a],Int[Sin[b*x^n]/x,x]] /;
FreeQ[{a,b,n},x]


(* ::Item::Closed:: *)
(*Reference: CRC 392, A&S 4.3.119*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Basis: x^m*Sin[a+b*x^n] == x^(m-n+1)*(Sin[a+b*x^n]*x^(n-1))*)


Int[x_^m_.*Sin[a_.+b_.*x_^n_.],x_Symbol] :=
  -x^(m-n+1)*Cos[a+b*x^n]/(b*n) +
  Dist[(m-n+1)/(b*n),Int[x^(m-n)*Cos[a+b*x^n],x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && RationalQ[m] && 0<n<=m


(* ::Item::Closed:: *)
(*Reference: CRC 405, A&S 4.3.120*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Sin[a_.+b_.*x_^n_.],x_Symbol] :=
  x^(m+1)*Sin[a+b*x^n]/(m+1) -
  Dist[b*n/(m+1),Int[x^(m+n)*Cos[a+b*x^n],x]] /;
FreeQ[{a,b,m,n},x] && (ZeroQ[m+n+1] || IntegerQ[n] && RationalQ[m] && (n>0 && m<-1 || 0<-n<m+1))


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sin[z] == I*E^(-I*z)/2 - I*E^(I*z)/2*)


Int[x_^m_.*Sin[a_.+b_.*x_^n_.],x_Symbol] :=
  Dist[I/2,Int[x^m*E^(-a*I-b*I*x^n),x]] - 
  Dist[I/2,Int[x^m*E^(a*I+b*I*x^n),x]] /;
FreeQ[{a,b,m,n},x] && NonzeroQ[m+1] && NonzeroQ[m-n+1] && Not[FractionQ[m] || FractionOrNegativeQ[n]]


(* ::Subsubsection::Closed:: *)
(*x^m Sin[a+b x^n]^p			Products of monomials and powers of sines of binomials*)


(* ::Item:: *)
(*Reference: G&R 2.631.2' w/ m=1*)


Int[x_^m_.*Sin[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  n*Sin[a+b*x^n]^p/(b^2*n^2*p^2) -
  x^n*Cos[a+b*x^n]*Sin[a+b*x^n]^(p-1)/(b*n*p) +
  Dist[(p-1)/p,Int[x^m*Sin[a+b*x^n]^(p-2),x]] /;
FreeQ[{a,b,m,n},x] && RationalQ[p] && p>1 && ZeroQ[m-2*n+1]


(* ::Item:: *)
(*Reference: G&R 2.631.2'*)


Int[x_^m_.*Sin[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  (m-n+1)*x^(m-2*n+1)*Sin[a+b*x^n]^p/(b^2*n^2*p^2) -
  x^(m-n+1)*Cos[a+b*x^n]*Sin[a+b*x^n]^(p-1)/(b*n*p) +
  Dist[(p-1)/p,Int[x^m*Sin[a+b*x^n]^(p-2),x]] -
  Dist[(m-n+1)*(m-2*n+1)/(b^2*n^2*p^2),Int[x^(m-2*n)*Sin[a+b*x^n]^p,x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && RationalQ[{m,p}] && p>1 && 0<2*n<m+1


(* ::Item:: *)
(*Reference: G&R 2.643.1, CRC 429', A&S 4.3.122*)


Int[x_^m_.*Sin[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  x^n*Cos[a+b*x^n]*Sin[a+b*x^n]^(p+1)/(b*n*(p+1)) - 
  n*Sin[a+b*x^n]^(p+2)/(b^2*n^2*(p+1)*(p+2)) + 
  Dist[(p+2)/(p+1),Int[x^m*Sin[a+b*x^n]^(p+2),x]] /;
FreeQ[{a,b,m,n},x] && RationalQ[p] && p<-1 && p!=-2 && ZeroQ[m-2*n+1]


(* ::Item:: *)
(*Reference: G&R 2.643.1*)


Int[x_^m_.*Sin[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  x^(m-n+1)*Cos[a+b*x^n]*Sin[a+b*x^n]^(p+1)/(b*n*(p+1)) -
  (m-n+1)*x^(m-2*n+1)*Sin[a+b*x^n]^(p+2)/(b^2*n^2*(p+1)*(p+2)) +
  Dist[(p+2)/(p+1),Int[x^m*Sin[a+b*x^n]^(p+2),x]] +
  Dist[(m-n+1)*(m-2*n+1)/(b^2*n^2*(p+1)*(p+2)),Int[x^(m-2*n)*Sin[a+b*x^n]^(p+2),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && RationalQ[{m,p}] && p<-1 && p!=-2 && 0<2*n<m+1 


(* ::Item:: *)
(*Reference: G&R 2.638.1'*)


Int[x_^m_.*Sin[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  x^(m+1)*Sin[a+b*x^n]^p/(m+1) - 
  b*n*p*x^(m+n+1)*Cos[a+b*x^n]*Sin[a+b*x^n]^(p-1)/((m+1)*(m+n+1)) - 
  Dist[b^2*n^2*p^2/((m+1)*(m+n+1)),Int[x^(m+2*n)*Sin[a+b*x^n]^p,x]] + 
  Dist[b^2*n^2*p*(p-1)/((m+1)*(m+n+1)),Int[x^(m+2*n)*Sin[a+b*x^n]^(p-2),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && RationalQ[{m,p}] && p>1 && m<-1 && n>0 && NonzeroQ[m+n+1]


(* ::Subsubsection::Closed:: *)
(*x^m Sin[a+b (c+d x)^n]^p		Products of monomials and powers of sines of binomials of linears*)
(**)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Int[f[a+b x,x],x] == Subst[Int[f[x,-a/b+x/b],x],x,a+b x]/b*)


Int[x_^m_.*Sin[a_.+b_.*(c_+d_.*x_)^n_]^p_.,x_Symbol] :=
  Dist[1/d,Subst[Int[(-c/d+x/d)^m*Sin[a+b*x^n]^p,x],x,c+d*x]] /;
FreeQ[{a,b,c,d,n},x] && IntegerQ[m] && m>0 && RationalQ[p]


(* ::Subsubsection::Closed:: *)
(*Sin[a+b x+c x^2]			Sines of quadratic trinomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If b^2-4*a*c=0, a+b*x+c*x^2 == (b+2*c*x)^2/(4*c)*)


Int[Sin[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  Int[Sin[(b+2*c*x)^2/(4*c)],x] /;
FreeQ[{a,b,c},x] && ZeroQ[b^2-4*a*c]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: a+b*x+c*x^2 == (b+2*c*x)^2/(4*c) - (b^2-4*a*c)/(4*c)*)


(* ::Item:: *)
(*Basis: Sin[z-w] == Cos[w]*Sin[z] - Sin[w]*Cos[z]*)


Int[Sin[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  Cos[(b^2-4*a*c)/(4*c)]*Int[Sin[(b+2*c*x)^2/(4*c)],x] - 
  Sin[(b^2-4*a*c)/(4*c)]*Int[Cos[(b+2*c*x)^2/(4*c)],x] /;
FreeQ[{a,b,c},x] && NonzeroQ[b^2-4*a*c]


(* ::Subsubsection::Closed:: *)
(*(d+e x)^m Sin[a+b x+c x^2]		Products of linears and sines of quadratic trinomials*)
(**)


Int[(d_.+e_.*x_)*Sin[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  -e*Cos[a+b*x+c*x^2]/(2*c) /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)*Sin[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  -e*Cos[a+b*x+c*x^2]/(2*c) -
  Dist[(b*e-2*c*d)/(2*c),Int[Sin[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && NonzeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)^m_*Sin[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  -e*(d+e*x)^(m-1)*Cos[a+b*x+c*x^2]/(2*c) + 
  Dist[e^2*(m-1)/(2*c),Int[(d+e*x)^(m-2)*Cos[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[m] && m>1 && ZeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)^m_*Sin[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  -e*(d+e*x)^(m-1)*Cos[a+b*x+c*x^2]/(2*c) - 
  Dist[(b*e-2*c*d)/(2*c),Int[(d+e*x)^(m-1)*Sin[a+b*x+c*x^2],x]] + 
  Dist[e^2*(m-1)/(2*c),Int[(d+e*x)^(m-2)*Cos[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[m] && m>1 && NonzeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)^m_*Sin[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  (d+e*x)^(m+1)*Sin[a+b*x+c*x^2]/(e*(m+1)) -
  Dist[2*c/(e^2*(m+1)),Int[(d+e*x)^(m+2)*Cos[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[m] && m<-1 && ZeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)^m_*Sin[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  (d+e*x)^(m+1)*Sin[a+b*x+c*x^2]/(e*(m+1)) -
  Dist[(b*e-2*c*d)/(e^2*(m+1)),Int[(d+e*x)^(m+1)*Cos[a+b*x+c*x^2],x]] -
  Dist[2*c/(e^2*(m+1)),Int[(d+e*x)^(m+2)*Cos[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[m] && m<-1 && NonzeroQ[b*e-2*c*d]


(* ::Subsubsection::Closed:: *)
(*Sin[a+b Log[c x^n]]^p			Powers of sines of logarithms*)


Int[Sin[a_.+b_.*Log[c_.*x_^n_.]],x_Symbol] :=
  x*Sin[a+b*Log[c*x^n]]/(1+b^2*n^2) -
  b*n*x*Cos[a+b*Log[c*x^n]]/(1+b^2*n^2) /;
FreeQ[{a,b,c,n},x] && NonzeroQ[1+b^2*n^2]


Int[Sin[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  x*Sin[a+b*Log[c*x^n]]^p/(1+b^2*n^2*p^2) -
  b*n*p*x*Cos[a+b*Log[c*x^n]]*Sin[a+b*Log[c*x^n]]^(p-1)/(1+b^2*n^2*p^2) +
  Dist[b^2*n^2*p*(p-1)/(1+b^2*n^2*p^2),Int[Sin[a+b*Log[c*x^n]]^(p-2),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p>1 && NonzeroQ[1+b^2*n^2*p^2]


Int[Sin[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  x*Cot[a+b*Log[c*x^n]]*Sin[a+b*Log[c*x^n]]^(p+2)/(b*n*(p+1)) -
  x*Sin[a+b*Log[c*x^n]]^(p+2)/(b^2*n^2*(p+1)*(p+2)) +
  Dist[(1+b^2*n^2*(p+2)^2)/(b^2*n^2*(p+1)*(p+2)),Int[Sin[a+b*Log[c*x^n]]^(p+2),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p<-1 && p!=-2


(* ::Subsubsection::Closed:: *)
(*x^m Sin[a+b Log[c x^n]]^p		Products of monomials and powers of sines of logarithms*)


Int[x_^m_.*Sin[a_.+b_.*Log[c_.*x_^n_.]],x_Symbol] :=
  (m+1)*x^(m+1)*Sin[a+b*Log[c*x^n]]/(b^2*n^2+(m+1)^2) -
  b*n*x^(m+1)*Cos[a+b*Log[c*x^n]]/(b^2*n^2+(m+1)^2) /;
FreeQ[{a,b,c,m,n},x] && NonzeroQ[b^2*n^2+(m+1)^2] && NonzeroQ[m+1]


Int[x_^m_.*Sin[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  (m+1)*x^(m+1)*Sin[a+b*Log[c*x^n]]^p/(b^2*n^2*p^2+(m+1)^2) -
  b*n*p*x^(m+1)*Cos[a+b*Log[c*x^n]]*Sin[a+b*Log[c*x^n]]^(p-1)/(b^2*n^2*p^2+(m+1)^2) +
  Dist[b^2*n^2*p*(p-1)/(b^2*n^2*p^2+(m+1)^2),Int[x^m*Sin[a+b*Log[c*x^n]]^(p-2),x]] /;
FreeQ[{a,b,c,m,n},x] && RationalQ[p] && p>1 && NonzeroQ[b^2*n^2*p^2+(m+1)^2] && NonzeroQ[m+1]


Int[x_^m_.*Sin[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  x^(m+1)*Cot[a+b*Log[c*x^n]]*Sin[a+b*Log[c*x^n]]^(p+2)/(b*n*(p+1)) -
  (m+1)*x^(m+1)*Sin[a+b*Log[c*x^n]]^(p+2)/(b^2*n^2*(p+1)*(p+2)) +
  Dist[(b^2*n^2*(p+2)^2+(m+1)^2)/(b^2*n^2*(p+1)*(p+2)),Int[x^m*Sin[a+b*Log[c*x^n]]^(p+2),x]] /;
FreeQ[{a,b,c,m,n},x] && RationalQ[p] && p<-1 && p!=-2 && NonzeroQ[m+1]


(* ::Subsubsection::Closed:: *)
(*x^m Sin[a x^n Log[b x]^p Log[b x]^p  Products of sines and powers of logarithms*)
(**)


Int[Sin[a_.*x_*Log[b_.*x_]^p_.]*Log[b_.*x_]^p_.,x_Symbol] :=
  -Cos[a*x*Log[b*x]^p]/a -
  Dist[p,Int[Sin[a*x*Log[b*x]^p]*Log[b*x]^(p-1),x]] /;
FreeQ[{a,b},x] && RationalQ[p] && p>0


Int[Sin[a_.*x_^n_*Log[b_.*x_]^p_.]*Log[b_.*x_]^p_.,x_Symbol] :=
  -Cos[a*x^n*Log[b*x]^p]/(a*n*x^(n-1)) -
  Dist[p/n,Int[Sin[a*x^n*Log[b*x]^p]*Log[b*x]^(p-1),x]] -
  Dist[(n-1)/(a*n),Int[Cos[a*x^n*Log[b*x]^p]/x^n,x]] /;
FreeQ[{a,b},x] && RationalQ[{n,p}] && p>0


Int[x_^m_*Sin[a_.*x_^n_.*Log[b_.*x_]^p_.]*Log[b_.*x_]^p_.,x_Symbol] :=
  -x^(m-n+1)*Cos[a*x^n*Log[b*x]^p]/(a*n) -
  Dist[p/n,Int[x^m*Sin[a*x^n*Log[b*x]^p]*Log[b*x]^(p-1),x]] +
  Dist[(m-n+1)/(a*n),Int[x^(m-n)*Cos[a*x^n*Log[b*x]^p],x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n,p}] && p>0


(* ::Subsubsection::Closed:: *)
(*u Sin[v]^2				Products involving squares of sines*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sin[z]^2 == 1/2 - 1/2*Cos[2*z]*)


Int[u_*Sin[v_]^2,x_Symbol] :=
  Dist[1/2,Int[u,x]] - 
  Dist[1/2,Int[u*Cos[2*v],x]] /;
FunctionOfTrigQ[u,2*v,x]


(* ::Subsubsection::Closed:: *)
(*u Sin[v] Trig[w]			Products of circular trig functions*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sin[v]*Cos[w] == Sin[v+w]/2 + Sin[v-w]/2*)


Int[u_.*Sin[v_]*Cos[w_],x_Symbol] :=
  Dist[1/2,Int[u*Regularize[Sin[v+w],x],x]] + 
  Dist[1/2,Int[u*Regularize[Sin[v-w],x],x]] /;
(PolynomialQ[v,x] && PolynomialQ[w,x] || IndependentQ[Cancel[v/w],x]) && PosQ[v-w]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sin[v]*Sin[w] == Cos[v-w]/2 - Cos[v+w]/2*)


Int[u_.*Sin[v_]*Sin[w_],x_Symbol] :=
  Dist[1/2,Int[u*Regularize[Cos[v-w],x],x]] - 
  Dist[1/2,Int[u*Regularize[Cos[v+w],x],x]] /;
(PolynomialQ[v,x] && PolynomialQ[w,x] || IndependentQ[Cancel[v/w],x]) && NonzeroQ[v-w]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sin[v]*Tan[w] == -Cos[v] + Cos[v-w]*Sec[w]*)


Int[u_.*Sin[v_]*Tan[w_]^n_.,x_Symbol] :=
  -Int[u*Cos[v]*Tan[w]^(n-1),x] + Cos[v-w]*Int[u*Sec[w]*Tan[w]^(n-1),x] /;
RationalQ[n] && n>0 && FreeQ[v-w,x] && NonzeroQ[v-w]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sin[v]*Cot[w] == Cos[v] + Sin[v-w]*Csc[w]*)


Int[u_.*Sin[v_]*Cot[w_]^n_.,x_Symbol] :=
  Int[u*Cos[v]*Cot[w]^(n-1),x] + Sin[v-w]*Int[u*Csc[w]*Cot[w]^(n-1),x] /;
RationalQ[n] && n>0 && FreeQ[v-w,x] && NonzeroQ[v-w]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sin[v]*Sec[w] == Cos[v-w]*Tan[w] + Sin[v-w]*)


Int[u_.*Sin[v_]*Sec[w_]^n_.,x_Symbol] :=
  Cos[v-w]*Int[u*Tan[w]*Sec[w]^(n-1),x] + Sin[v-w]*Int[u*Sec[w]^(n-1),x] /;
RationalQ[n] && n>0 && FreeQ[v-w,x] && NonzeroQ[v-w]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sin[v]*Csc[w] == Sin[v-w]*Cot[w] + Cos[v-w]*)


Int[u_.*Sin[v_]*Csc[w_]^n_.,x_Symbol] :=
  Sin[v-w]*Int[u*Cot[w]*Csc[w]^(n-1),x] + Cos[v-w]*Int[u*Csc[w]^(n-1),x] /;
RationalQ[n] && n>0 && FreeQ[v-w,x] && NonzeroQ[v-w]


(* ::Subsection::Closed:: *)
(*Cosine Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*Cos[a+b x]^n				Positive integer powers of cosines of linears*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.01.6, CRC 291, A&S 4.3.114*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: Sin'[z] == Cos[z]*)


Int[Cos[a_.+b_.*x_],x_Symbol] :=
  Sin[a+b*x]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Reference: G&R 2.513.11, CRC 302*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[Cos[a_.+b_.*x_]^2,x_Symbol] :=
  x/2 + Cos[a+b*x]*Sin[a+b*x]/(2*b) /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is odd, Cos[z]^n == (1-Sin[z]^2)^((n-1)/2)*Sin'[z]*)


Int[Cos[a_.+b_.*x_]^n_,x_Symbol] :=
  Dist[1/b,Subst[Int[Regularize[(1-x^2)^((n-1)/2),x],x],x,Sin[a+b*x]]] /;
FreeQ[{a,b},x] && OddQ[n] && n>1


(* ::ItemParagraph::Closed:: *)
(**)


(* ::Item:: *)
(*Reference: G&R 2.510.5, CRC 305*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[(c_.*Cos[a_.+b_.*x_])^n_,x_Symbol] :=
  c*Sin[a+b*x]*(c*Cos[a+b*x])^(n-1)/(b*n) + 
  Dist[(n-1)*c^2/n,Int[(c*Cos[a+b*x])^(n-2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n>1 && Not[OddQ[n]]


(* ::Item::Closed:: *)
(*Reference: G&R 2.510.6, CRC 313*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[(c_.*Cos[a_.+b_.*x_])^n_,x_Symbol] :=
  -Sin[a+b*x]*(c*Cos[a+b*x])^(n+1)/(c*b*(n+1)) + 
  Dist[(n+2)/((n+1)*c^2),Int[(c*Cos[a+b*x])^(n+2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n<-1


(* ::Item::Closed:: *)
(*Derivation: Extract constant factor*)


(* ::Item:: *)
(*Basis: D[(c*Cos[x])^n/Cos[x]^n,x] == 0*)


Int[(c_*Cos[a_.+b_.*x_])^n_,x_Symbol] :=
  (c*Cos[a+b*x])^n/Cos[a+b*x]^n*Int[Cos[a+b*x]^n,x] /;
FreeQ[{a,b,c},x] && RationalQ[n] && -1<n<1


(* ::Subsubsection::Closed:: *)
(*(a+b Cos[c+d x])^n			Powers of linear binomials of cosines of linears*)


(* ::Item:: *)
(*Reference: G&R 2.555.4', A&S 4.3.134'/5'*)


Int[1/(a_+b_.*Cos[c_.+d_.*x_]),x_Symbol] :=
  Sin[c+d*x]/(d*(b+a*Cos[c+d*x])) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2-b^2]


(* ::Item:: *)
(*Reference: G&R 2.553.3a, A&S 4.3.133a*)


Int[1/(a_+b_.*Cos[c_.+d_.*x_]),x_Symbol] :=
  2*ArcTan[((a-b)*Tan[(c+d*x)/2])/Rt[a^2-b^2,2]]/(d*Rt[a^2-b^2,2]) /;
FreeQ[{a,b,c,d},x] && PosQ[a^2-b^2]


(* ::Item:: *)
(*Reference: G&R 2.553.3b', A&S 4.3.133b'*)


Int[1/(a_+b_.*Cos[c_.+d_.*x_]),x_Symbol] :=
  -2*ArcTanh[((a-b)*Tan[(c+d*x)/2])/Rt[b^2-a^2,2]]/(d*Rt[b^2-a^2,2]) /;
FreeQ[{a,b,c,d},x] && NegQ[a^2-b^2]


(* ::ItemParagraph:: *)
(**)


Int[Sqrt[a_+b_.*Cos[c_.+d_.*x_]],x_Symbol] :=
  2*b*Sin[c+d*x]/(d*Sqrt[a+b*Cos[c+d*x]]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2-b^2]


(* ::Item:: *)
(*Basis: D[EllipticE[x,n],x] == Sqrt[1-n*Sin[x]^2]*)


Int[Sqrt[a_.+b_.*Cos[c_.+d_.*x_]],x_Symbol] :=
  2*Sqrt[a+b]*EllipticE[(c+d*x)/2,2*b/(a+b)]/d /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2] && PositiveQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Extract constant factor*)


(* ::Item:: *)
(*Basis: D[Sqrt[a+b*f[c+d*x]]/Sqrt[(a+b*f[c+d*x])/(a+b)],x] == 0*)


Int[Sqrt[a_.+b_.*Cos[c_.+d_.*x_]],x_Symbol] :=
  Sqrt[a+b*Cos[c+d*x]]/Sqrt[(a+b*Cos[c+d*x])/(a+b)]*Int[Sqrt[a/(a+b)+b/(a+b)*Cos[c+d*x]],x] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2] && Not[PositiveQ[a+b]]


(* ::ItemParagraph:: *)
(**)


Int[1/Sqrt[a_+b_.*Cos[c_.+d_.*x_]],x_Symbol] :=
  -2*ArcTanh[Cos[(c+d*x)/2]]*Sin[(c+d*x)/2]/(d*Sqrt[a+b*Cos[c+d*x]]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a+b]


Int[1/Sqrt[a_+b_.*Cos[c_.+d_.*x_]],x_Symbol] :=
  2*ArcTanh[Sin[(c+d*x)/2]]*Cos[(c+d*x)/2]/(d*Sqrt[a+b*Cos[c+d*x]]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a-b]


(* ::Item:: *)
(*Basis: D[EllipticF[x,n],x] == 1/Sqrt[1-n*Sin[x]^2]*)


Int[1/Sqrt[a_.+b_.*Cos[c_.+d_.*x_]],x_Symbol] :=
  2*EllipticF[(c+d*x)/2,2*b/(a+b)]/(d*Sqrt[a+b]) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2] && PositiveQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Extract constant factor*)


(* ::Item:: *)
(*Basis: D[Sqrt[(a+b*f[c+d*x])/(a+b)]/Sqrt[a+b*f[c+d*x]],x] == 0*)


Int[1/Sqrt[a_+b_.*Cos[c_.+d_.*x_]],x_Symbol] :=
  Sqrt[(a+b*Cos[c+d*x])/(a+b)]/Sqrt[a+b*Cos[c+d*x]]*Int[1/Sqrt[a/(a+b)+b/(a+b)*Cos[c+d*x]],x] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2] && Not[PositiveQ[a+b]]


(* ::ItemParagraph:: *)
(**)


Int[(a_+b_.*Cos[c_.+d_.*x_])^n_,x_Symbol] :=
  b*Sin[c+d*x]*(a+b*Cos[c+d*x])^(n-1)/(d*n) +
  Dist[a*(2*n-1)/n,Int[(a+b*Cos[c+d*x])^(n-1),x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n>1 && ZeroQ[a^2-b^2]


(* ::Item:: *)
(*Reference: G&R 2.555.2'*)


Int[(a_+b_.*Cos[c_.+d_.*x_])^n_,x_Symbol] :=
  -b*Sin[c+d*x]*(a+b*Cos[c+d*x])^n/(a*d*(2*n+1)) +
  Dist[(n+1)/(a*(2*n+1)),Int[(a+b*Cos[c+d*x])^(n+1),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n<-1 && ZeroQ[a^2-b^2]


(* ::Item:: *)
(*Reference: G&R 2.554.3 inverted*)


(* Note: This would result in an infinite loop!!! *)
(* Int[(a_+b_.*Cos[c_.+d_.*x_])^n_,x_Symbol] :=
  b*Sin[c+d*x]*(a+b*Cos[c+d*x])^n/(a*d*n) +
  Dist[(a^2-b^2)/a,Int[(a+b*Cos[c+d*x])^(n-1),x]] +
  Dist[b*(n+1)/(a*n),Int[Cos[c+d*x]*(a+b*Cos[c+d*x])^n,x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n>0 && NonzeroQ[a^2-b^2] *)


(* ::Item:: *)
(*Reference: G&R 2.554.3*)


Int[1/(a_+b_.*Cos[c_.+d_.*x_])^2,x_Symbol] :=
  -b*Sin[c+d*x]/(d*(a^2-b^2)*(a+b*Cos[c+d*x])) + 
  Dist[a/(a^2-b^2),Int[1/(a+b*Cos[c+d*x]),x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2]


(* ::Item:: *)
(*Reference: G&R 2.554.3*)


Int[(a_+b_.*Cos[c_.+d_.*x_])^n_,x_Symbol] :=
  b*Sin[c+d*x]*(a+b*Cos[c+d*x])^(n+1)/(d*(n+1)*(a^2-b^2)) +
  Dist[1/((n+1)*(a^2-b^2)),Int[(a*(n+1)-b*(n+2)*Cos[c+d*x])*(a+b*Cos[c+d*x])^(n+1),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n<-1 && NonzeroQ[a^2-b^2]


(* ::Subsubsection::Closed:: *)
(*x^m (a+b Cos[c+d x])^n		Products of monomials and powers of linear binomials of cosines of linears*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1+Cos[z] == 2*Cos[z/2]^2*)


Int[x_^m_.*(a_+b_.*Cos[c_.+d_.*x_])^n_,x_Symbol] :=
  Dist[(2*a)^n,Int[x^m*Cos[c/2+d*x/2]^(2*n),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[m] && IntegerQ[n] && n<0 && ZeroQ[a-b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1+Cos[z] == 2*Cos[z/2]^2*)


Int[x_^m_.*(a_+b_.*Cos[c_.+d_.*x_])^n_,x_Symbol] :=
  Dist[2^n,Int[x^m*(a*Cos[c/2+d*x/2]^2)^n,x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[{m,n}] && ZeroQ[a-b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1-Cos[z] == 2*Sin[z/2]^2*)


Int[x_^m_.*(a_+b_.*Cos[c_.+d_.*x_])^n_,x_Symbol] :=
  Dist[(2*a)^n,Int[x^m*Sin[c/2+d*x/2]^(2*n),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[m] && IntegerQ[n] && n<0 && ZeroQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1-Cos[z] == 2*Sin[z/2]^2*)


Int[x_^m_.*(a_+b_.*Cos[c_.+d_.*x_])^n_,x_Symbol] :=
  Dist[2^n,Int[x^m*(a*Sin[c/2+d*x/2]^2)^n,x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[{m,n}] && ZeroQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: 1/(a+b z)^2 == a/((a^2-b^2) (a+b z)) - b (b+a z)/((a^2-b^2) (a+b z)^2)*)


Int[x_/(a_+b_.*Cos[c_.+d_.*x_])^2,x_Symbol] :=
  Dist[a/(a^2-b^2),Int[x/(a+b*Cos[c+d*x]),x]] -
  Dist[b/(a^2-b^2),Int[x*(b+a*Cos[c+d*x])/(a+b*Cos[c+d*x])^2,x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: a+b*Cos[z] == (b+2*a*E^(I*z)+b*E^(2*I*z))/(2*E^(I*z))*)


Int[x_^m_.*(a_+b_.*Cos[c_.+d_.*x_])^n_,x_Symbol] :=
  Dist[1/2^n,Int[x^m*(b+2*a*E^(I*c+I*d*x)+b*E^(2*(I*c+I*d*x)))^n/E^(n*(I*c+I*d*x)),x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2] && IntegerQ[n] && n<0 && RationalQ[m] && m>0


(* ::Subsubsection::Closed:: *)
(*(A+B Cos[c+d x]) (a+b Cos[c+d x])^n	Products of powers of linear binomials of cosines*)


(* ::Item:: *)
(*Basis: (A+B*z)/Sqrt[a+b*z] == (b*A-a*B)/(b*Sqrt[a+b*z]) + B/b*Sqrt[a+b*z]*)


Int[(A_.+B_.*Cos[c_.+d_.*x_])/Sqrt[a_+b_.*Cos[c_.+d_.*x_]],x_Symbol] :=
  Dist[(b*A-a*B)/b,Int[1/Sqrt[a+b*Cos[c+d*x]],x]] +
  Dist[B/b,Int[Sqrt[a+b*Cos[c+d*x]],x]] /;
FreeQ[{a,b,c,d,A,B},x] && NonzeroQ[b*A-a*B]


(* ::Item:: *)
(*Reference: G&R 2.554.1 inverted*)


Int[(A_.+B_.*Cos[c_.+d_.*x_])*(a_+b_.*Cos[c_.+d_.*x_])^n_,x_Symbol] :=
  B*Sin[c+d*x]*(a+b*Cos[c+d*x])^n/(d*(n+1)) + 
  Dist[1/(n+1),Int[(b*B*n+a*A*(n+1) + (a*B*n+b*A*(n+1))*Cos[c+d*x])*(a+b*Cos[c+d*x])^(n-1),x]] /;
FreeQ[{a,b,c,d,A,B},x] && RationalQ[n] && n>1 && NonzeroQ[a^2-b^2]


(* ::Item:: *)
(*Reference: G&R 2.554.1 special case*)


Int[(A_+B_.*Cos[c_.+d_.*x_])/(a_+b_.*Cos[c_.+d_.*x_])^2,x_Symbol] :=
  B*Sin[c+d*x]/(a*d*(a+b*Cos[c+d*x])) /;
FreeQ[{a,b,c,d,A,B},x] && ZeroQ[a*A-b*B]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_*(A_+B_.*Cos[c_.+d_.*x_])/(a_+b_.*Cos[c_.+d_.*x_])^2,x_Symbol] :=
  B*x*Sin[c+d*x]/(a*d*(a+b*Cos[c+d*x])) -
  Dist[B/(a*d),Int[Sin[c+d*x]/(a+b*Cos[c+d*x]),x]] /;
FreeQ[{a,b,c,d,A,B},x] && ZeroQ[a*A-b*B]


(* ::Item:: *)
(*Reference: G&R 2.554.1*)


Int[(A_.+B_.*Cos[c_.+d_.*x_])*(a_.+b_.*Cos[c_.+d_.*x_])^n_,x_Symbol] :=
  -(a*B-b*A)*Sin[c+d*x]*(a+b*Cos[c+d*x])^(n+1)/(d*(n+1)*(a^2-b^2)) +
  Dist[1/((n+1)*(a^2-b^2)),Int[((n+1)*(a*A-b*B)+(n+2)*(a*B-b*A)*Cos[c+d*x])*(a+b*Cos[c+d*x])^(n+1),x]] /;
FreeQ[{a,b,c,d,A,B},x] && RationalQ[n] && n<-1 && NonzeroQ[a^2-b^2]


(* ::Subsubsection::Closed:: *)
(*x^m (a+b Cos[c+d x]^2)^n		Products of monomials and powers of quadratic binomials of sines of linears*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Cos[z]^2 == (1 + Cos[2*z])/2*)


Int[x_^m_./(a_+b_.*Cos[c_.+d_.*x_]^2),x_Symbol] :=
  Dist[2,Int[x^m/(2*a+b+b*Cos[2*c+2*d*x]),x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a+b] && IntegerQ[m] && m>0


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Cos[z]^2 == (1 + Cos[2*z])/2*)


Int[(a_+b_.*Cos[c_.+d_.*x_]^2)^n_,x_Symbol] :=
  Dist[1/2^n,Int[(2*a+b+b*Cos[2*c+2*d*x])^n,x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a+b] && HalfIntegerQ[n]


(* ::Subsubsection::Closed:: *)
(*Cos[a+b x]^n Sin[a+b x]^m 		Products of powers of cosines and sines*)


Int[Sin[a_.+b_.*x_]^m_*Cos[a_.+b_.*x_]^n_.,x_Symbol] :=
  -Sin[a+b*x]^(m+1)*Cos[a+b*x]^(n+1)/(b*(n+1)) /;
FreeQ[{a,b,m,n},x] && ZeroQ[m+n+2] && NonzeroQ[n+1] && PosQ[n]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If m is odd, Sin[z]^m == -(1-Cos[z]^2)^((m-1)/2)*Cos'[z]*)


Int[Sin[a_.+b_.*x_]^m_*Cos[a_.+b_.*x_]^n_,x_Symbol] :=
  Dist[-1/b,Subst[Int[Regularize[x^n*(1-x^2)^((m-1)/2),x],x],x,Cos[a+b*x]]] /;
FreeQ[{a,b,n},x] && OddQ[m] && Not[OddQ[n] && 0<n<=m]


(* ::Item:: *)
(*Reference: G&R 2.510.4*)


Int[Sin[a_.+b_.*x_]^m_*Cos[a_.+b_.*x_]^n_,x_Symbol] :=
  Sin[a+b*x]^(m+1)*Cos[a+b*x]^(n-1)/(b*(m+1)) +
  Dist[(n-1)/(m+1),Int[Sin[a+b*x]^(m+2)*Cos[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m<-1 && n>1


(* ::Item:: *)
(*Reference: G&R 2.510.5, CRC 323a, A&S 4.3.127a*)


Int[Sin[a_.+b_.*x_]^m_*Cos[a_.+b_.*x_]^n_,x_Symbol] :=
  Sin[a+b*x]^(m+1)*Cos[a+b*x]^(n-1)/(b*(m+n)) +
  Dist[(n-1)/(m+n),Int[Sin[a+b*x]^m*Cos[a+b*x]^(n-2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n>1 && Not[OddQ[n]] && NonzeroQ[m+n] &&
Not[OddQ[m] && m>1]


(* ::Item:: *)
(*Reference: G&R 2.510.6, CRC 334b, A&S 4.3.128a*)


Int[Sin[a_.+b_.*x_]^m_*Cos[a_.+b_.*x_]^n_,x_Symbol] :=
  -Sin[a+b*x]^(m+1)*Cos[a+b*x]^(n+1)/(b*(n+1)) +
  Dist[(m+n+2)/(n+1),Int[Sin[a+b*x]^m*Cos[a+b*x]^(n+2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n<-1 && NonzeroQ[m+n+2]


(* Kool rule, but replace with a more general collect fractional power rule?! *)
Int[Sin[a_.+b_.*x_]^m_*Cos[a_.+b_.*x_]^n_,x_Symbol] :=
  Dist[-1/(b*n),Subst[Int[x^(1/n)/(1+x^(2/n)),x],x,Sin[a+b*x]^m*Cos[a+b*x]^n]] /;
FreeQ[{a,b},x] && FractionQ[{m,n}] && ZeroQ[m+n] && IntegerQ[1/n] && n>0


(* ::Subsubsection::Closed:: *)
(*Cos[a+b x]^m Cot[a+b x]^n		Products of powers of cosines and cotangents*)
(**)


(* ::Item::Closed:: *)
(*Reference: G&R 2.526.34'*)


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cos[z]*Cot[z] == -Sin[z]+Csc[z]*)


Int[Cos[a_.+b_.*x_]*Cot[a_.+b_.*x_],x_Symbol] :=
  Cos[a+b*x]/b + 
  Int[Csc[a+b*x],x] /;
FreeQ[{a,b},x]


Int[Cos[a_.+b_.*x_]^m_*Cot[a_.+b_.*x_]^n_,x_Symbol] :=
  Cos[a+b*x]^m*Cot[a+b*x]^(n-1)/(b*m) /;
FreeQ[{a,b,m,n},x] && ZeroQ[m+n-1]


(* ::Item:: *)
(*Derivation: Integration by substitution*)


Int[Cos[a_.+b_.*x_]^m_.*Cot[a_.+b_.*x_]^n_.,x_Symbol] :=
  Dist[1/b,Subst[Int[Regularize[(1-x^2)^((m+n-1)/2)/x^n,x],x],x,Sin[a+b*x]]] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && OddQ[m+n]


(* ::Item:: *)
(*Reference: G&R 2.510.2, CRC 323b*)


Int[Cos[a_.+b_.*x_]^m_*Cot[a_.+b_.*x_]^n_,x_Symbol] :=
  -Cos[a+b*x]^m*Cot[a+b*x]^(n+1)/(b*m) -
  Dist[(n+1)/m,Int[Cos[a+b*x]^(m-2)*Cot[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m>1 && n<-1 && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.510.3, CRC 334a*)


Int[Cos[a_.+b_.*x_]^m_*Cot[a_.+b_.*x_]^n_,x_Symbol] :=
  -Cos[a+b*x]^(m+2)*Cot[a+b*x]^(n-1)/(b*(n-1)) -
  Dist[(m+2)/(n-1),Int[Cos[a+b*x]^(m+2)*Cot[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m<-1 && n>1 && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.510.5, CRC 323a*)


Int[Cos[a_.+b_.*x_]^m_*Cot[a_.+b_.*x_]^n_.,x_Symbol] :=
  Cos[a+b*x]^m*Cot[a+b*x]^(n-1)/(b*m) +
  Dist[(m+n-1)/m,Int[Cos[a+b*x]^(m-2)*Cot[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m>1 && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.510.4*)


Int[Cos[a_.+b_.*x_]^m_.*Cot[a_.+b_.*x_]^n_,x_Symbol] :=
  -Cos[a+b*x]^m*Cot[a+b*x]^(n-1)/(b*(n-1)) -
  Dist[(m+n-1)/(n-1),Int[Cos[a+b*x]^m*Cot[a+b*x]^(n-2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n>1 && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.510.6, CRC 334b*)


Int[Cos[a_.+b_.*x_]^m_*Cot[a_.+b_.*x_]^n_.,x_Symbol] :=
  -Cos[a+b*x]^(m+2)*Cot[a+b*x]^(n-1)/(b*(m+n+1)) +
  Dist[(m+2)/(m+n+1),Int[Cos[a+b*x]^(m+2)*Cot[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m<-1 && NonzeroQ[m+n+1] && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.510.1*)


Int[Cos[a_.+b_.*x_]^m_.*Cot[a_.+b_.*x_]^n_,x_Symbol] :=
  -Cos[a+b*x]^m*Cot[a+b*x]^(n+1)/(b*(m+n+1)) -
  Dist[(n+1)/(m+n+1),Int[Cos[a+b*x]^m*Cot[a+b*x]^(n+2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n<-1 && NonzeroQ[m+n+1] && Not[OddQ[m] && EvenQ[n]]


(* ::Subsubsection::Closed:: *)
(*Cos[a+b x^n]				Cosines of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: FresnelC'[z] == Cos[Pi*z^2/2]*)


Int[Cos[b_.*x_^2],x_Symbol] :=
  Sqrt[Pi/2]*FresnelC[Rt[b,2]*x/Sqrt[Pi/2]]/Rt[b,2] /;
FreeQ[b,x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cos[w+z] == Cos[w]*Cos[z] - Sin[w]*Sin[z]*)


Int[Cos[a_+b_.*x_^2],x_Symbol] :=
  Dist[Cos[a],Int[Cos[b*x^2],x]] - 
  Dist[Sin[a],Int[Sin[b*x^2],x]] /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cos[z] == E^(-I*z)/2 + E^(I*z)/2*)


Int[Cos[a_.+b_.*x_^n_],x_Symbol] :=
  Dist[1/2,Int[E^(-a*I-b*I*x^n),x]] + 
  Dist[1/2,Int[E^(a*I+b*I*x^n),x]] /;
FreeQ[{a,b,n},x] && Not[FractionOrNegativeQ[n]]


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* Note: Although resulting integrand looks more complicated than original one, rules for 
	improper binomials rectify it. *)
Int[Cos[a_.+b_.*x_^n_],x_Symbol] :=
  x*Cos[a+b*x^n] + 
  Dist[b*n,Int[x^n*Sin[a+b*x^n],x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && n<0


(* ::Subsubsection::Closed:: *)
(*x^m Cos[a+b x^n]			Products of monomials and cosines of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: CosIntegral'[z] == Cos[z]/z*)


Int[Cos[a_.*x_^n_.]/x_,x_Symbol] :=
  CosIntegral[a*x^n]/n /;
FreeQ[{a,n},x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cos[w+z] == Cos[w]*Cos[z] - Sin[w]*Sin[z]*)


Int[Cos[a_+b_.*x_^n_.]/x_,x_Symbol] :=
  Dist[Cos[a],Int[Cos[b*x^n]/x,x]] - 
  Dist[Sin[a],Int[Sin[b*x^n]/x,x]] /;
FreeQ[{a,b,n},x]


(* ::Item::Closed:: *)
(*Reference: CRC 396, A&S 4.3.123*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Basis: x^m*Cos[a+b*x^n] == x^(m-n+1)*(Cos[a+b*x^n]*x^(n-1))*)


Int[x_^m_.*Cos[a_.+b_.*x_^n_.],x_Symbol] :=
  x^(m-n+1)*Sin[a+b*x^n]/(b*n) -
  Dist[(m-n+1)/(b*n),Int[x^(m-n)*Sin[a+b*x^n],x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && RationalQ[m] && 0<n<=m


(* ::Item::Closed:: *)
(*Reference: CRC 406, A&S 4.3.124*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Cos[a_.+b_.*x_^n_.],x_Symbol] :=
  x^(m+1)*Cos[a+b*x^n]/(m+1) +
  Dist[b*n/(m+1),Int[x^(m+n)*Sin[a+b*x^n],x]] /;
FreeQ[{a,b,m,n},x] && (ZeroQ[m+n+1] || IntegerQ[n] && RationalQ[m] && (n>0 && m<-1 || 0<-n<m+1))


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cos[z] == E^(-I*z)/2 + E^(I*z)/2*)


Int[x_^m_.*Cos[a_.+b_.*x_^n_.],x_Symbol] :=
  Dist[1/2,Int[x^m*E^(-a*I-b*I*x^n),x]] + 
  Dist[1/2,Int[x^m*E^(a*I+b*I*x^n),x]] /;
FreeQ[{a,b,m,n},x] && NonzeroQ[m+1] && NonzeroQ[m-n+1] && Not[FractionQ[m] || FractionOrNegativeQ[n]]


(* ::Subsubsection::Closed:: *)
(*x^m Cos[a+b x^n]^p			Products of monomials and powers of cosines of binomials*)


(* ::Item:: *)
(*Reference: G&R 2.631.3' w/ m=1*)


Int[x_^m_.*Cos[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  n*Cos[a+b*x^n]^p/(b^2*n^2*p^2) +
  x^n*Sin[a+b*x^n]*Cos[a+b*x^n]^(p-1)/(b*n*p) +
  Dist[(p-1)/p,Int[x^m*Cos[a+b*x^n]^(p-2),x]] /;
FreeQ[{a,b,m,n},x] && RationalQ[p] && p>1 && ZeroQ[m-2*n+1]


(* ::Item:: *)
(*Reference: G&R 2.631.3'*)


Int[x_^m_.*Cos[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  (m-n+1)*x^(m-2*n+1)*Cos[a+b*x^n]^p/(b^2*n^2*p^2) +
  x^(m-n+1)*Sin[a+b*x^n]*Cos[a+b*x^n]^(p-1)/(b*n*p) +
  Dist[(p-1)/p,Int[x^m*Cos[a+b*x^n]^(p-2),x]] -
  Dist[(m-n+1)*(m-2*n+1)/(b^2*n^2*p^2),Int[x^(m-2*n)*Cos[a+b*x^n]^p,x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && RationalQ[{m,p}] && p>1 && 0<2*n<m+1


(* ::Item:: *)
(*Reference: G&R 2.643.2, CRC 431', A&S 4.3.126*)


Int[x_^m_.*Cos[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  -x^n*Sin[a+b*x^n]*Cos[a+b*x^n]^(p+1)/(b*n*(p+1)) - 
  n*Cos[a+b*x^n]^(p+2)/(b^2*n^2*(p+1)*(p+2)) + 
  Dist[(p+2)/(p+1),Int[x^m*Cos[a+b*x^n]^(p+2),x]] /;
FreeQ[{a,b,m,n},x] && RationalQ[p] && p<-1 && p!=-2 && ZeroQ[m-2*n+1]


(* ::Item:: *)
(*Reference: G&R 2.643.2*)


Int[x_^m_.*Cos[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  -x^(m-n+1)*Sin[a+b*x^n]*Cos[a+b*x^n]^(p+1)/(b*n*(p+1)) -
  (m-n+1)*x^(m-2*n+1)*Cos[a+b*x^n]^(p+2)/(b^2*n^2*(p+1)*(p+2)) +
  Dist[(p+2)/(p+1),Int[x^m*Cos[a+b*x^n]^(p+2),x]] +
  Dist[(m-n+1)*(m-2*n+1)/(b^2*n^2*(p+1)*(p+2)),Int[x^(m-2*n)*Cos[a+b*x^n]^(p+2),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && RationalQ[{m,p}] && p<-1 && p!=-2 && 0<2*n<m+1 


(* ::Item:: *)
(*Reference: G&R 2.638.2'*)


Int[x_^m_.*Cos[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  x^(m+1)*Cos[a+b*x^n]^p/(m+1) + 
  b*n*p*x^(m+n+1)*Sin[a+b*x^n]*Cos[a+b*x^n]^(p-1)/((m+1)*(m+n+1)) - 
  Dist[b^2*n^2*p^2/((m+1)*(m+n+1)),Int[x^(m+2*n)*Cos[a+b*x^n]^p,x]] + 
  Dist[b^2*n^2*p*(p-1)/((m+1)*(m+n+1)),Int[x^(m+2*n)*Cos[a+b*x^n]^(p-2),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && RationalQ[{m,p}] && p>1 && m<-1 && n>0 && NonzeroQ[m+n+1]


(* ::Subsubsection::Closed:: *)
(*x^m Cos[a+b (c+d x)^n]^p		Products of monomials and powers of cosines of binomials of linears*)
(**)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Int[f[a+b x,x],x] == Subst[Int[f[x,-a/b+x/b],x],x,a+b x]/b*)


Int[x_^m_.*Cos[a_.+b_.*(c_+d_.*x_)^n_]^p_.,x_Symbol] :=
  Dist[1/d,Subst[Int[(-c/d+x/d)^m*Cos[a+b*x^n]^p,x],x,c+d*x]] /;
FreeQ[{a,b,c,d,n},x] && IntegerQ[m] && m>0 && RationalQ[p]


(* ::Subsubsection::Closed:: *)
(*Cos[a+b x+c x^2]			Cosines of quadratic trinomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If b^2-4*a*c=0, a+b*x+c*x^2 == (b+2*c*x)^2/(4*c)*)


Int[Cos[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  Int[Cos[(b+2*c*x)^2/(4*c)],x] /;
FreeQ[{a,b,c},x] && ZeroQ[b^2-4*a*c]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: a+b*x+c*x^2 == (b+2*c*x)^2/(4*c) - (b^2-4*a*c)/(4*c)*)


(* ::Item:: *)
(*Basis: Cos[z-w] == Cos[w]*Cos[z] + Sin[w]*Sin[z]*)


Int[Cos[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  Cos[(b^2-4*a*c)/(4*c)]*Int[Cos[(b+2*c*x)^2/(4*c)],x] + 
  Sin[(b^2-4*a*c)/(4*c)]*Int[Sin[(b+2*c*x)^2/(4*c)],x] /;
FreeQ[{a,b,c},x] && NonzeroQ[b^2-4*a*c]


(* ::Subsubsection::Closed:: *)
(*(d+e x)^m Cos[a+b x+c x^2]		Products of linears and cosines of quadratic trinomials*)
(**)


Int[(d_.+e_.*x_)*Cos[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  e*Sin[a+b*x+c*x^2]/(2*c) /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)*Cos[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  e*Sin[a+b*x+c*x^2]/(2*c) -
  Dist[(b*e-2*c*d)/(2*c),Int[Cos[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && NonzeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)^m_*Cos[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  e*(d+e*x)^(m-1)*Sin[a+b*x+c*x^2]/(2*c) - 
  Dist[e^2*(m-1)/(2*c),Int[(d+e*x)^(m-2)*Sin[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[m] && m>1 && ZeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)^m_*Cos[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  e*(d+e*x)^(m-1)*Sin[a+b*x+c*x^2]/(2*c) - 
  Dist[(b*e-2*c*d)/(2*c),Int[(d+e*x)^(m-1)*Cos[a+b*x+c*x^2],x]] - 
  Dist[e^2*(m-1)/(2*c),Int[(d+e*x)^(m-2)*Sin[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[m] && m>1 && NonzeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)^m_*Cos[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  (d+e*x)^(m+1)*Cos[a+b*x+c*x^2]/(e*(m+1)) + 
  Dist[2*c/(e^2*(m+1)),Int[(d+e*x)^(m+2)*Sin[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[m] && m<-1 && ZeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)^m_*Cos[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  (d+e*x)^(m+1)*Cos[a+b*x+c*x^2]/(e*(m+1)) + 
  Dist[(b*e-2*c*d)/(e^2*(m+1)),Int[(d+e*x)^(m+1)*Sin[a+b*x+c*x^2],x]] +
  Dist[2*c/(e^2*(m+1)),Int[(d+e*x)^(m+2)*Sin[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[m] && m<-1 && NonzeroQ[b*e-2*c*d]


(* ::Subsubsection::Closed:: *)
(*Cos[a+b Log[c x^n]]^p			Powers of cosines of logarithms*)


Int[Cos[a_.+b_.*Log[c_.*x_^n_.]],x_Symbol] :=
  x*Cos[a+b*Log[c*x^n]]/(1+b^2*n^2) +
  b*n*x*Sin[a+b*Log[c*x^n]]/(1+b^2*n^2) /;
FreeQ[{a,b,c,n},x] && NonzeroQ[1+b^2*n^2]


Int[Cos[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  x*Cos[a+b*Log[c*x^n]]^p/(1+b^2*n^2*p^2) +
  b*n*p*x*Cos[a+b*Log[c*x^n]]^(p-1)*Sin[a+b*Log[c*x^n]]/(1+b^2*n^2*p^2) +
  Dist[b^2*n^2*p*(p-1)/(1+b^2*n^2*p^2),Int[Cos[a+b*Log[c*x^n]]^(p-2),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p>1 && NonzeroQ[1+b^2*n^2*p^2]


Int[Cos[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  -x*Tan[a+b*Log[c*x^n]]*Cos[a+b*Log[c*x^n]]^(p+2)/(b*n*(p+1)) -
  x*Cos[a+b*Log[c*x^n]]^(p+2)/(b^2*n^2*(p+1)*(p+2)) +
  Dist[(1+b^2*n^2*(p+2)^2)/(b^2*n^2*(p+1)*(p+2)),Int[Cos[a+b*Log[c*x^n]]^(p+2),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p<-1 && p!=-2


(* ::Subsubsection::Closed:: *)
(*x^m Cos[a+b Log[c x^n]]^p		Products of monomials and powers of cosines of logarithms*)


Int[x_^m_.*Cos[a_.+b_.*Log[c_.*x_^n_.]],x_Symbol] :=
  (m+1)*x^(m+1)*Cos[a+b*Log[c*x^n]]/(b^2*n^2+(m+1)^2) +
  b*n*x^(m+1)*Sin[a+b*Log[c*x^n]]/(b^2*n^2+(m+1)^2) /;
FreeQ[{a,b,c,m,n},x] && NonzeroQ[b^2*n^2+(m+1)^2] && NonzeroQ[m+1]


Int[x_^m_.*Cos[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  (m+1)*x^(m+1)*Cos[a+b*Log[c*x^n]]^p/(b^2*n^2*p^2+(m+1)^2) +
  b*n*p*x^(m+1)*Cos[a+b*Log[c*x^n]]^(p-1)*Sin[a+b*Log[c*x^n]]/(b^2*n^2*p^2+(m+1)^2) +
  Dist[b^2*n^2*p*(p-1)/(b^2*n^2*p^2+(m+1)^2),Int[x^m*Cos[a+b*Log[c*x^n]]^(p-2),x]] /;
FreeQ[{a,b,c,m,n},x] && RationalQ[p] && p>1 && NonzeroQ[b^2*n^2*p^2+(m+1)^2] && NonzeroQ[m+1]


Int[x_^m_.*Cos[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  -x^(m+1)*Tan[a+b*Log[c*x^n]]*Cos[a+b*Log[c*x^n]]^(p+2)/(b*n*(p+1)) -
  (m+1)*x^(m+1)*Cos[a+b*Log[c*x^n]]^(p+2)/(b^2*n^2*(p+1)*(p+2)) +
  Dist[(b^2*n^2*(p+2)^2+(m+1)^2)/(b^2*n^2*(p+1)*(p+2)),Int[x^m*Cos[a+b*Log[c*x^n]]^(p+2),x]] /;
FreeQ[{a,b,c,m,n},x] && RationalQ[p] && p<-1 && p!=-2 && NonzeroQ[m+1]


(* ::Subsubsection::Closed:: *)
(*x^m Cos[a x^n Log[b x]^p Log[b x]^p	Products of cosines and powers of logarithms*)
(**)


Int[Cos[a_.*x_*Log[b_.*x_]^p_.]*Log[b_.*x_]^p_.,x_Symbol] :=
  Sin[a*x*Log[b*x]^p]/a -
  Dist[p,Int[Cos[a*x*Log[b*x]^p]*Log[b*x]^(p-1),x]] /;
FreeQ[{a,b},x] && RationalQ[p] && p>0


Int[Cos[a_.*x_^n_*Log[b_.*x_]^p_.]*Log[b_.*x_]^p_.,x_Symbol] :=
  Sin[a*x^n*Log[b*x]^p]/(a*n*x^(n-1)) -
  Dist[p/n,Int[Cos[a*x^n*Log[b*x]^p]*Log[b*x]^(p-1),x]] +
  Dist[(n-1)/(a*n),Int[Sin[a*x^n*Log[b*x]^p]/x^n,x]] /;
FreeQ[{a,b},x] && RationalQ[{n,p}] && p>0


Int[x_^m_*Cos[a_.*x_^n_.*Log[b_.*x_]^p_.]*Log[b_.*x_]^p_.,x_Symbol] :=
  x^(m-n+1)*Sin[a*x^n*Log[b*x]^p]/(a*n) -
  Dist[p/n,Int[x^m*Cos[a*x^n*Log[b*x]^p]*Log[b*x]^(p-1),x]] -
  Dist[(m-n+1)/(a*n),Int[x^(m-n)*Sin[a*x^n*Log[b*x]^p],x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n,p}] && p>0


(* ::Subsubsection::Closed:: *)
(*u Cos[v]^2				Products involving squares of cosines*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cos[z]^2 == 1/2 + 1/2*Cos[2*z]*)


Int[u_*Cos[v_]^2,x_Symbol] :=
  Dist[1/2,Int[u,x]] + 
  Dist[1/2,Int[u*Cos[2*v],x]] /;
FunctionOfTrigQ[u,2*v,x]


(* ::Subsubsection::Closed:: *)
(*u Cos[v] Trig[w]			Products of circular trig functions*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sin[v]*Cos[w] == Sin[w+v]/2 - Sin[w-v]/2*)


Int[u_.*Sin[v_]*Cos[w_],x_Symbol] :=
  Dist[1/2,Int[u*Regularize[Sin[w+v],x],x]] - 
  Dist[1/2,Int[u*Regularize[Sin[w-v],x],x]] /;
(PolynomialQ[v,x] && PolynomialQ[w,x] || IndependentQ[Cancel[v/w],x]) && PosQ[w-v]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cos[v]*Cos[w] == Cos[v-w]/2 + Cos[v+w]/2*)


Int[u_.*Cos[v_]*Cos[w_],x_Symbol] :=
  Dist[1/2,Int[u*Regularize[Cos[v-w],x],x]] + 
  Dist[1/2,Int[u*Regularize[Cos[v+w],x],x]] /;
(PolynomialQ[v,x] && PolynomialQ[w,x] || IndependentQ[Cancel[v/w],x]) && NonzeroQ[v-w]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cos[v]*Tan[w] == Sin[v] - Sin[v-w]*Sec[w]*)


Int[u_.*Cos[v_]*Tan[w_]^n_.,x_Symbol] :=
  Int[u*Sin[v]*Tan[w]^(n-1),x] - Sin[v-w]*Int[u*Sec[w]*Tan[w]^(n-1),x] /;
RationalQ[n] && n>0 && FreeQ[v-w,x] && NonzeroQ[v-w]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cos[v]*Cot[w] == -Sin[v] + Cos[v-w]*Csc[w]*)


Int[u_.*Cos[v_]*Cot[w_]^n_.,x_Symbol] :=
  -Int[u*Sin[v]*Cot[w]^(n-1),x] + Cos[v-w]*Int[u*Csc[w]*Cot[w]^(n-1),x] /;
RationalQ[n] && n>0 && FreeQ[v-w,x] && NonzeroQ[v-w]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cos[v]*Sec[w] == -Sin[v-w]*Tan[w] + Cos[v-w]*)


Int[u_.*Cos[v_]*Sec[w_]^n_.,x_Symbol] :=
  -Sin[v-w]*Int[u*Tan[w]*Sec[w]^(n-1),x] + Cos[v-w]*Int[u*Sec[w]^(n-1),x] /;
RationalQ[n] && n>0 && FreeQ[v-w,x] && NonzeroQ[v-w]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cos[v]*Csc[w] == Cos[v-w]*Cot[w] - Sin[v-w]*)


Int[u_.*Cos[v_]*Csc[w_]^n_.,x_Symbol] :=
  Cos[v-w]*Int[u*Cot[w]*Csc[w]^(n-1),x] - Sin[v-w]*Int[u*Csc[w]^(n-1),x] /;
RationalQ[n] && n>0 && FreeQ[v-w,x] && NonzeroQ[v-w]


(* ::Subsection::Closed:: *)
(*Tangent Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*(c Tan[a+b x])^n			Powers of tangents of linears*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.526.17, CRC 292, A&S 4.3.115*)


(* ::Item:: *)
(*Derivation: Reciprocal rule*)


(* ::Item:: *)
(*Basis: Tan[z] == Sin[z]/Cos[z]*)


Int[Tan[a_.+b_.*x_],x_Symbol] :=
  -Log[Cos[a+b*x]]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Reference: G&R 2.526.22, CRC 420*)


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Tan[z]^2 == -1+Sec[z]^2*)


Int[Tan[a_.+b_.*x_]^2,x_Symbol] :=
  -x + Tan[a+b*x]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Reference: G&R 2.510.1, CRC 423, A&S 4.3.129*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


(* ::Item:: *)
(*Basis: Tan[z]^n == Tan[z]^(n-1)/Cos[z]*Sin[z]*)


Int[(c_.*Tan[a_.+b_.*x_])^n_,x_Symbol] :=
  c*(c*Tan[a+b*x])^(n-1)/(b*(n-1)) - 
  Dist[c^2,Int[(c*Tan[a+b*x])^(n-2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n>1


(* ::Item::Closed:: *)
(*Reference: G&R 2.510.4, CRC 427'*)


(* ::Item:: *)
(*Derivation: Inverted integration by parts with a double-back flip*)


Int[(c_.*Tan[a_.+b_.*x_])^n_,x_Symbol] :=
  (c*Tan[a+b*x])^(n+1)/(b*c*(n+1)) - 
  Dist[1/c^2,Int[(c*Tan[a+b*x])^(n+2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n<-1


(* ::Subsubsection::Closed:: *)
(*(a+b Tan[c+d x])^n			Powers of binomials of tangents where a^2+b^2 is zero*)


Int[Sqrt[a_+b_.*Tan[c_.+d_.*x_]],x_Symbol] :=
  -(Sqrt[2]*b*ArcTanh[Sqrt[a+b*Tan[c+d*x]]/(Sqrt[2]*Rt[a,2])])/(d*Rt[a,2]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2+b^2] && PosQ[a]


Int[Sqrt[a_+b_.*Tan[c_.+d_.*x_]],x_Symbol] :=
  (Sqrt[2]*b*ArcTan[Sqrt[a+b*Tan[c+d*x]]/(Sqrt[2]*Rt[-a,2])])/(d*Rt[-a,2]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2+b^2] && NegQ[a]


Int[(a_+b_.*Tan[c_.+d_.*x_])^n_,x_Symbol] :=
  -a^2*(a+b*Tan[c+d*x])^(n-1)/(b*d*(n-1)) + 
  Dist[2*a,Int[(a+b*Tan[c+d*x])^(n-1),x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n>1 && ZeroQ[a^2+b^2]


Int[1/(a_+b_.*Tan[c_.+d_.*x_]),x_Symbol] :=
  x/(2*a) - a/(2*b*d*(a+b*Tan[c+d*x])) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2+b^2]


Int[(a_+b_.*Tan[c_.+d_.*x_])^n_,x_Symbol] :=
  a*(a+b*Tan[c+d*x])^n/(2*b*d*n) + 
  Dist[1/(2*a),Int[(a+b*Tan[c+d*x])^(n+1),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n<0 && ZeroQ[a^2+b^2]


(* ::Subsubsection::Closed:: *)
(*1 / (a+b Tan[c+d x]^n)			Reciprocals of binomials of tangents*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: 1/(a+b*Tan[z]) == Cos[z]/(a*Cos[z]+b*Sin[z])*)


Int[1/(a_+b_.*Tan[c_.+d_.*x_]),x_Symbol] :=
  a*x/(a^2+b^2) + b*Log[a*Cos[c+d*x]+b*Sin[c+d*x]]/(d*(a^2+b^2)) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2+b^2]


Int[1/(a_+b_.*Tan[c_.+d_.*x_]^2),x_Symbol] :=
  x/(a-b) - Sqrt[b]*ArcTan[(Sqrt[b]*Tan[c+d*x])/Sqrt[a]]/(Sqrt[a]*d*(a-b)) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a-b]


(* ::Subsubsection::Closed:: *)
(*x^m Tan[a+b x^n]^p			Products of monomials and powers of tangents of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Tan[z] == -I + 2*I/(1+E^(2*I*z))*)


Int[x_^m_.*Tan[a_.+b_.*x_^n_.],x_Symbol] :=
  -I*x^(m+1)/(m+1) + 
  Dist[2*I,Int[x^m/(1+E^(2*I*a+2*I*b*x^n)),x]] /;
FreeQ[{a,b,m,n},x] && NonzeroQ[m-n+1] && IntegerQ[m] && m>0


(* Note: Rule not in literature ??? *)
Int[x_^m_.*Tan[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  x^(m-n+1)*Tan[a+b*x^n]^(p-1)/(b*n*(p-1)) - 
  Dist[(m-n+1)/(b*n*(p-1)),Int[x^(m-n)*Tan[a+b*x^n]^(p-1),x]] - 
  Int[x^m*Tan[a+b*x^n]^(p-2),x] /;
FreeQ[{a,b},x] && RationalQ[{m,n,p}] && p>1 && NonzeroQ[m-n+1] && 0<n<=m


(* ::Subsubsection::Closed:: *)
(*x^m Tan[a+b x+c x^2]			Products of monomials and tangents of quadratic trinomials*)


Int[x_*Tan[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  -Log[Cos[a+b*x+c*x^2]]/(2*c) -
  Dist[b/(2*c),Int[Tan[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c},x]


(* Valid, but need a rule for x^m*Log[Cos[a+b*x+c*x^2]] to be useful. *)
(* Int[x_^m_*Tan[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  -x^(m-1)*Log[Cos[a+b*x+c*x^2]]/(2*c) - 
  Dist[b/(2*c),Int[x^(m-1)*Tan[a+b*x+c*x^2],x]] + 
  Dist[(m-1)/(2*c),Int[x^(m-2)*Log[Cos[a+b*x+c*x^2]],x]] /;
FreeQ[{a,b,c},x] && RationalQ[m] && m>1 *)


(* ::Subsection::Closed:: *)
(*Cotangent Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*(c Cot[a+b x])^n			Powers of cotangents of linears*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.526.33, CRC 293, A&S 4.3.118*)


(* ::Item:: *)
(*Derivation: Reciprocal rule*)


(* ::Item:: *)
(*Basis: Cot[z] == Cos[z]/Sin[z]*)


Int[Cot[a_.+b_.*x_],x_Symbol] :=
  Log[Sin[a+b*x]]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Reference: G&R 2.526.38, CRC 424*)


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cot[z]^2 == -1+Csc[z]^2*)


Int[Cot[a_.+b_.*x_]^2,x_Symbol] :=
  -x - Cot[a+b*x]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Reference: G&R 2.510.4, CRC 427, A&S 4.3.130*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


(* ::Item:: *)
(*Basis: Cot[z]^n == Cot[z]^(n-1)/Sin[z]*Cos[z]*)


Int[(c_.*Cot[a_.+b_.*x_])^n_,x_Symbol] :=
  -c*(c*Cot[a+b*x])^(n-1)/(b*(n-1)) - 
  Dist[c^2,Int[(c*Cot[a+b*x])^(n-2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n>1


(* ::Item::Closed:: *)
(*Reference: G&R 2.510.1, CRC 423'*)


(* ::Item:: *)
(*Derivation: Inverted integration by parts with a double-back flip*)


Int[(c_.*Cot[a_.+b_.*x_])^n_,x_Symbol] :=
  -(c*Cot[a+b*x])^(n+1)/(b*c*(n+1)) - 
  Dist[1/c^2,Int[(c*Cot[a+b*x])^(n+2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n<-1


(* ::Subsubsection::Closed:: *)
(*(a+b Cot[c+d x])^n			Powers of binomials of cotangents where a^2+b^2 is zero*)


Int[Sqrt[a_+b_.*Cot[c_.+d_.*x_]],x_Symbol] :=
  (Sqrt[2]*b*ArcCoth[Sqrt[a+b*Cot[c+d*x]]/(Sqrt[2]*Rt[a,2])])/(d*Rt[a,2]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2+b^2] && PosQ[a]


Int[Sqrt[a_+b_.*Cot[c_.+d_.*x_]],x_Symbol] :=
  (Sqrt[2]*b*ArcCot[Sqrt[a+b*Cot[c+d*x]]/(Sqrt[2]*Rt[-a,2])])/(d*Rt[-a,2]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2+b^2] && NegQ[a]


Int[(a_+b_.*Cot[c_.+d_.*x_])^n_,x_Symbol] :=
  a^2*(a+b*Cot[c+d*x])^(n-1)/(b*d*(n-1)) + 
  Dist[2*a,Int[(a+b*Cot[c+d*x])^(n-1),x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n>1 && ZeroQ[a^2+b^2]


Int[1/(a_+b_.*Cot[c_.+d_.*x_]),x_Symbol] :=
  x/(2*a) + a/(2*b*d*(a+b*Cot[c+d*x])) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2+b^2]


Int[(a_+b_.*Cot[c_.+d_.*x_])^n_,x_Symbol] :=
  -a*(a+b*Cot[c+d*x])^n/(2*b*d*n) + 
  Dist[1/(2*a),Int[(a+b*Cot[c+d*x])^(n+1),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n<0 && ZeroQ[a^2+b^2]


(* ::Subsubsection::Closed:: *)
(*1 / (a+b Cot[c+d x]^n)			Reciprocals of binomials of cotangents*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: 1/(a+b*Cot[z]) == Sin[z]/(a*Sin[z]+b*Cos[z])*)


Int[1/(a_+b_.*Cot[c_.+d_.*x_]),x_Symbol] :=
  a*x/(a^2+b^2) - b*Log[b*Cos[c+d*x]+a*Sin[c+d*x]]/(d*(a^2+b^2)) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2+b^2]


Int[1/(a_+b_.*Cot[c_.+d_.*x_]^2),x_Symbol] :=
  x/(a-b) + Sqrt[b]*ArcTan[(Sqrt[b]*Cot[c+d*x])/Sqrt[a]]/(Sqrt[a]*d*(a-b)) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a-b]


(* ::Subsubsection::Closed:: *)
(*x^m Cot[a+b x^n]^p			Products of monomials and powers of cotangents of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cot[z] == I - 2*I/(1-E^(2*I*z))*)


Int[x_^m_.*Cot[a_.+b_.*x_^n_.],x_Symbol] :=
  I*x^(m+1)/(m+1) - 
  Dist[2*I,Int[x^m/(1-E^(2*I*a+2*I*b*x^n)),x]] /;
FreeQ[{a,b,m,n},x] && NonzeroQ[m-n+1] && IntegerQ[m] && m>0


(* Note: Rule not in literature ??? *)
Int[x_^m_.*Cot[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  -x^(m-n+1)*Cot[a+b*x^n]^(p-1)/(b*n*(p-1)) + 
  Dist[(m-n+1)/(b*n*(p-1)),Int[x^(m-n)*Cot[a+b*x^n]^(p-1),x]] - 
  Int[x^m*Cot[a+b*x^n]^(p-2),x] /;
FreeQ[{a,b},x] && RationalQ[{m,n,p}] && p>1 && NonzeroQ[m-n+1] && 0<n<=m


(* ::Subsubsection::Closed:: *)
(*x^m Cot[a+b x+c x^2]			Products of monomials and cotangents of quadratic trinomials*)


Int[x_*Cot[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  Log[Sin[a+b*x+c*x^2]]/(2*c) -
  Dist[b/(2*c),Int[Cot[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c},x]


(* Int[x_^m_*Cot[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  x^(m-1)*Log[Sin[a+b*x+c*x^2]]/(2*c) -
  Dist[b/(2*c),Int[x^(m-1)*Cot[a+b*x+c*x^2],x]] -
  Dist[(m-1)/(2*c),Int[x^(m-2)*Log[Sin[a+b*x+c*x^2]],x]] /;
FreeQ[{a,b,c},x] && RationalQ[m] && m>1*)


(* ::Subsection::Closed:: *)
(*Secant Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*Sec[a+b x]^n				Powers of secants of linears*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.526.9', CRC 294', A&S 4.3.117'*)


(* ::Item:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Sec[z] == 1/(1-Sin[z]^2)*Sin'[z]*)


Int[Sec[a_.+b_.*x_],x_Symbol] :=
  ArcTanh[Sin[a+b*x]]/b /;
FreeQ[{a,b},x]


(* Note: This entirely redundant is required due to idem potent problem in Mathematica 6 & 7. *)
Int[1/Sqrt[Sec[a_.+b_.*x_]],x_Symbol] :=
  Sqrt[Cos[a+b*x]]*Sqrt[Sec[a+b*x]]*Int[Sqrt[Cos[a+b*x]],x] /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Extract constant factor*)


(* ::Item:: *)
(*Basis: D[(c*Sec[x])^n*Cos[x]^n,x] == 0*)


Int[(c_.*Sec[a_.+b_.*x_])^n_,x_Symbol] :=
  (c*Sec[a+b*x])^n*Cos[a+b*x]^n*Int[1/Cos[a+b*x]^n,x] /;
FreeQ[{a,b,c},x] && RationalQ[n] && -1<n<1


(* ::Item::Closed:: *)
(*Reference: G&R 2.526.10, CRC 312*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: Tan'[z] == Sec[z]^2*)


Int[Sec[a_.+b_.*x_]^2,x_Symbol] :=
  Tan[a+b*x]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is even, Sec[z]^n == (1+Tan[z]^2)^((n-2)/2)*Tan'[z]*)


Int[Sec[a_.+b_.*x_]^n_,x_Symbol] :=
  Dist[1/b,Subst[Int[Regularize[(1+x^2)^((n-2)/2),x],x],x,Tan[a+b*x]]] /;
FreeQ[{a,b},x] && EvenQ[n] && n>1


(* ::Item::Closed:: *)
(*Reference: G&R 2.510.6, CRC 313*)


(* ::Item:: *)
(*Derivation: Inverted integration by parts with a double-back flip*)


Int[(c_.*Sec[a_.+b_.*x_])^n_,x_Symbol] :=
  c*Sin[a+b*x]*(c*Sec[a+b*x])^(n-1)/(b*(n-1)) + 
  Dist[(n-2)*c^2/(n-1),Int[(c*Sec[a+b*x])^(n-2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n>1 && Not[EvenQ[n]]


(* ::Item::Closed:: *)
(*Reference: G&R 2.510.5, CRC 305*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[(c_.*Sec[a_.+b_.*x_])^n_,x_Symbol] :=
  -Sin[a+b*x]*(c*Sec[a+b*x])^(n+1)/(b*c*n) + 
  Dist[(n+1)/(c^2*n),Int[(c*Sec[a+b*x])^(n+2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n<-1


(* ::Subsubsection::Closed:: *)
(*x^m Sec[a+b x]^n			Products of monomials and powers of secants of linears*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Sec[a_.+b_.*x_],x_Symbol] :=
  -2*I*x^m*ArcTan[E^(I*a+I*b*x)]/b +
  Dist[2*I*m/b,Int[x^(m-1)*ArcTan[E^(I*a+I*b*x)],x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Reference: CRC 430, A&S 4.3.125*)


Int[x_^m_.*Sec[a_.+b_.*x_]^2,x_Symbol] :=
  x^m*Tan[a+b*x]/b -
  Dist[m/b,Int[x^(m-1)*Tan[a+b*x],x]] /;
FreeQ[{a,b},x] && RationalQ[m] && m>0


(* ::Item:: *)
(*Reference: G&R 2.643.2, CRC 431, A&S 4.3.126*)


Int[x_*Sec[a_.+b_.*x_]^n_,x_Symbol] :=
  x*Tan[a+b*x]*Sec[a+b*x]^(n-2)/(b*(n-1)) -
  Sec[a+b*x]^(n-2)/(b^2*(n-1)*(n-2)) +
  Dist[(n-2)/(n-1),Int[x*Sec[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n>1 && n!=2


(* ::Item:: *)
(*Reference: G&R 2.643.2*)


Int[x_^m_*Sec[a_.+b_.*x_]^n_,x_Symbol] :=
  x^m*Tan[a+b*x]*Sec[a+b*x]^(n-2)/(b*(n-1)) -
  m*x^(m-1)*Sec[a+b*x]^(n-2)/(b^2*(n-1)*(n-2)) +
  Dist[(n-2)/(n-1),Int[x^m*Sec[a+b*x]^(n-2),x]] +
  Dist[m*(m-1)/(b^2*(n-1)*(n-2)),Int[x^(m-2)*Sec[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && n>1 && n!=2 && m>1


(* ::Item:: *)
(*Reference: G&R 2.631.3*)


Int[x_*Sec[a_.+b_.*x_]^n_,x_Symbol] :=
  Sec[a+b*x]^n/(b^2*n^2) -
  x*Sin[a+b*x]*Sec[a+b*x]^(n+1)/(b*n) +
  Dist[(n+1)/n,Int[x*Sec[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n<-1


(* ::Item:: *)
(*Reference: G&R 2.631.3*)


Int[x_^m_*Sec[a_.+b_.*x_]^n_,x_Symbol] :=
  m*x^(m-1)*Sec[a+b*x]^n/(b^2*n^2) -
  x^m*Sin[a+b*x]*Sec[a+b*x]^(n+1)/(b*n) +
  Dist[(n+1)/n,Int[x^m*Sec[a+b*x]^(n+2),x]] -
  Dist[m*(m-1)/(b^2*n^2),Int[x^(m-2)*Sec[a+b*x]^n,x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && n<-1 && m>1


(* ::Subsubsection::Closed:: *)
(*(a+b Sec[c+d x])^n			Powers of constant plus secants of linears where a^2-b^2 is zero*)


Int[Sqrt[a_+b_.*Sec[c_.+d_.*x_]],x_Symbol] :=
  2*a*ArcTan[Sqrt[-1+a/b*Sec[c+d*x]]]*Tan[c+d*x]/
		(d*Sqrt[-1+a/b*Sec[c+d*x]]*Sqrt[a+b*Sec[c+d*x]]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2-b^2]


(* Note: There should be a simpler antiderivative! *)
Int[1/Sqrt[a_+b_.*Sec[c_.+d_.*x_]],x_Symbol] :=
  (Sqrt[2]*ArcTan[(Sqrt[2]*Sqrt[a])/Sqrt[-a+b*Sec[x]]]+2*ArcTan[Sqrt[-a+b*Sec[x]]/Sqrt[a]])*
  Sqrt[-a+b*Sec[x]]*Sqrt[a+b*Sec[x]]*Cot[x]/a^(3/2) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2-b^2]


(* ::Subsubsection::Closed:: *)
(*(a+b Sec[c+d x]^n)^m			Powers of constant plus powers of secants of linears*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If n is an integer, a+b*Sec[z]^n == (b+a*Cos[z]^n)/Cos[z]^n*)


Int[(a_+b_.*Sec[v_]^n_.)^m_,x_Symbol] :=
  Int[(b+a*Cos[v]^n)^m/Cos[v]^(m*n),x] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && m<0 && n>0


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If n is an integer, a+b*Sec[z]^n == (b+a*Cos[z]^n)/Cos[z]^n*)


Int[Cos[v_]^p_.*(a_+b_.*Sec[v_]^n_.)^m_,x_Symbol] :=
  Int[Cos[v]^(p-m*n)*(b+a*Cos[v]^n)^m,x] /;
FreeQ[{a,b},x] && IntegerQ[{m,n,p}] && m<0 && n>0


(* ::Subsubsection::Closed:: *)
(*Sec[a+b x]^n Csc[a+b x]^m		Products of powers of secants and cosecants*)


(* ::Item:: *)
(*Reference: G&R 2.526.49, CRC 329*)


Int[Csc[a_.+b_.*x_]*Sec[a_.+b_.*x_],x_Symbol] :=
  Log[Tan[a+b*x]]/b /;
FreeQ[{a,b},x] && PosQ[b]


Int[Csc[a_.+b_.*x_]^m_*Sec[a_.+b_.*x_]^n_,x_Symbol] :=
  Csc[a+b*x]^(m-1)*Sec[a+b*x]^(n-1)/(b*(n-1)) /;
FreeQ[{a,b,m,n},x] && ZeroQ[m+n-2] && NonzeroQ[n-1] && PosQ[n]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If m and n are integers and m+n is even, Csc[z]^m*Sec[z]^n == (1+Tan[z]^2)^((m+n)/2-1)/Tan[z]^m*Tan'[z]*)


Int[Csc[a_.+b_.*x_]^m_.*Sec[a_.+b_.*x_]^n_,x_Symbol] :=
  Dist[1/b,Subst[Int[Regularize[(1+x^2)^((m+n)/2-1)/x^m,x],x],x,Tan[a+b*x]]] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && EvenQ[m+n] && 0<m<=n
(* FreeQ[{a,b},x] && OddQ[m] && OddQ[n] *)


(* ::Item:: *)
(*Reference: G&R 2.510.4*)


Int[Csc[a_.+b_.*x_]^m_*Sec[a_.+b_.*x_]^n_,x_Symbol] :=
  Csc[a+b*x]^(m+1)*Sec[a+b*x]^(n-1)/(b*(n-1)) +
  Dist[(m+1)/(n-1),Int[Csc[a+b*x]^(m+2)*Sec[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m<-1 && n>1


(* ::Item:: *)
(*Reference: G&R 2.510.6, CRC 334b, A&S 4.3.128a*)


Int[Csc[a_.+b_.*x_]^m_.*Sec[a_.+b_.*x_]^n_,x_Symbol] :=
  Csc[a+b*x]^(m-1)*Sec[a+b*x]^(n-1)/(b*(n-1)) +
  Dist[(m+n-2)/(n-1),Int[Csc[a+b*x]^m*Sec[a+b*x]^(n-2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n>1 && Not[EvenQ[m+n]] && Not[EvenQ[n] && OddQ[m] && m>1]


(* ::Item:: *)
(*Reference: G&R 2.510.5, CRC 323a, A&S 4.3.127a*)


Int[Csc[a_.+b_.*x_]^m_.*Sec[a_.+b_.*x_]^n_,x_Symbol] :=
  -Csc[a+b*x]^(m-1)*Sec[a+b*x]^(n+1)/(b*(m+n)) +
  Dist[(n+1)/(m+n),Int[Csc[a+b*x]^m*Sec[a+b*x]^(n+2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n<-1 && NonzeroQ[m+n]


(* ::Subsubsection::Closed:: *)
(*Sec[a+b x]^m Tan[a+b x]^n		Products of powers of secants and tangents*)
(**)


(* ::Item:: *)
(*Derivation: Power rule for integration*)


Int[Sec[a_.+b_.*x_]^m_.*Tan[a_.+b_.*x_]^n_.,x_Symbol] :=
  Sec[a+b*x]^m/(b*m) /;
FreeQ[{a,b,m},x] && n===1


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If m is even, Sec[z]^m == (1+Tan[z]^2)^((m-2)/2)*Tan'[z]*)


Int[Sec[a_.+b_.*x_]^m_*Tan[a_.+b_.*x_]^n_.,x_Symbol] :=
  Dist[1/b,Subst[Int[Regularize[x^n*(1+x^2)^((m-2)/2),x],x],x,Tan[a+b*x]]] /;
FreeQ[{a,b,n},x] && EvenQ[m] && m>2 && Not[OddQ[n] && 0<n<m-1]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is odd, Sec[z]^m*Tan[z]^n == Sec[z]^(m-1)*(-1+Sec[z]^2)^((n-1)/2)*Sec'[z]*)


Int[Sec[a_.+b_.*x_]^m_.*Tan[a_.+b_.*x_]^n_.,x_Symbol] :=
  Dist[1/b,Subst[Int[Regularize[x^(m-1)*(-1+x^2)^((n-1)/2),x],x],x,Sec[a+b*x]]] /;
FreeQ[{a,b,m},x] && OddQ[n] && Not[EvenQ[m] && 0<m<=n+1]


(* ::Item:: *)
(*Reference: G&R 2.510.3, CRC 334a*)


Int[Sec[a_.+b_.*x_]^m_*Tan[a_.+b_.*x_]^n_,x_Symbol] :=
  Sec[a+b*x]^(m-2)*Tan[a+b*x]^(n+1)/(b*(n+1)) -
  Dist[(m-2)/(n+1),Int[Sec[a+b*x]^(m-2)*Tan[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m>1 && n<-1 && Not[EvenQ[m]]


(* ::Item:: *)
(*Reference: G&R 2.510.2, CRC 323b*)


Int[Sec[a_.+b_.*x_]^m_*Tan[a_.+b_.*x_]^n_,x_Symbol] :=
  Sec[a+b*x]^m*Tan[a+b*x]^(n-1)/(b*m) -
  Dist[(n-1)/m,Int[Sec[a+b*x]^(m+2)*Tan[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m<-1 && n>1 && Not[EvenQ[m]]


(* ::Item:: *)
(*Reference: G&R 2.510.5, CRC 323a*)


Int[Sec[a_.+b_.*x_]^m_*Tan[a_.+b_.*x_]^n_,x_Symbol] :=
  -Sec[a+b*x]^m*Tan[a+b*x]^(n+1)/(b*m) /;
FreeQ[{a,b,m,n},x] && ZeroQ[m+n+1]


Int[Sec[a_.+b_.*x_]^m_*Tan[a_.+b_.*x_]^n_,x_Symbol] :=
  -Sec[a+b*x]^m*Tan[a+b*x]^(n+1)/(b*m) +
  Dist[(m+n+1)/m,Int[Sec[a+b*x]^(m+2)*Tan[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m<-1 && Not[EvenQ[m]]


(* ::Item:: *)
(*Reference: G&R 2.510.6, CRC 334b*)


Int[Sec[a_.+b_.*x_]^m_*Tan[a_.+b_.*x_]^n_,x_Symbol] :=
  Sec[a+b*x]^(m-2)*Tan[a+b*x]^(n+1)/(b*(m+n-1)) +
  Dist[(m-2)/(m+n-1),Int[Sec[a+b*x]^(m-2)*Tan[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m>1 && NonzeroQ[m+n-1] && Not[EvenQ[m]] && Not[OddQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.510.1*)


Int[Sec[a_.+b_.*x_]^m_.*Tan[a_.+b_.*x_]^n_,x_Symbol] :=
  Sec[a+b*x]^m*Tan[a+b*x]^(n-1)/(b*(m+n-1)) -
  Dist[(n-1)/(m+n-1),Int[Sec[a+b*x]^m*Tan[a+b*x]^(n-2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n>1 && NonzeroQ[m+n-1] && Not[EvenQ[m]] && Not[OddQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.510.4*)


Int[Sec[a_.+b_.*x_]^m_*Tan[a_.+b_.*x_]^n_,x_Symbol] :=
  Sec[a+b*x]^m*Tan[a+b*x]^(n+1)/(b*(n+1)) -
  Dist[(m+n+1)/(n+1),Int[Sec[a+b*x]^m*Tan[a+b*x]^(n+2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n<-1 && Not[EvenQ[m]]


(* ::Subsubsection::Closed:: *)
(*x^m Sec[a+b x^n]^p Sin[a+b x^n]	Products of monomials, sines and powers of secants of binomials*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Sec[a_.+b_.*x_^n_.]^p_*Sin[a_.+b_.*x_^n_.],x_Symbol] :=
  x^(m-n+1)*Sec[a+b*x^n]^(p-1)/(b*n*(p-1)) -
  Dist[(m-n+1)/(b*n*(p-1)),Int[x^(m-n)*Sec[a+b*x^n]^(p-1),x]] /;
FreeQ[{a,b,p},x] && RationalQ[m] && IntegerQ[n] && m-n>=0 && NonzeroQ[p-1]


(* ::Subsubsection::Closed:: *)
(*x^m Sec[a+b x^n]^p Tan[a+b x^n]	Products of monomials, tangents and powers of secants of binomials*)
(**)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Sec[a_.+b_.*x_^n_.]^p_.*Tan[a_.+b_.*x_^n_.]^q_.,x_Symbol] :=
  x^(m-n+1)*Sec[a+b*x^n]^p/(b*n*p) -
  Dist[(m-n+1)/(b*n*p),Int[x^(m-n)*Sec[a+b*x^n]^p,x]] /;
FreeQ[{a,b,p},x] && RationalQ[m] && IntegerQ[n] && m-n>=0 && q===1 (* Required so InputForm is matchable *)


(* ::Subsubsection::Closed:: *)
(*Sec[a+b Log[c x^n]]^p			Powers of secants of logarithms*)


Int[Sec[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  x*Tan[a+b*Log[c*x^n]]*Sec[a+b*Log[c*x^n]]^(p-2)/(b*n*(p-1)) -
  x*Sec[a+b*Log[c*x^n]]^(p-2)/(b^2*n^2*(p-1)*(p-2)) +
  Dist[(1+b^2*n^2*(p-2)^2)/(b^2*n^2*(p-1)*(p-2)),Int[Sec[a+b*Log[c*x^n]]^(p-2),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p>1 && p!=2


Int[Sec[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  x*Sec[a+b*Log[c*x^n]]^p/(1+b^2*n^2*p^2) -
  b*n*p*x*Sec[a+b*Log[c*x^n]]^(p+1)*Sin[a+b*Log[c*x^n]]/(1+b^2*n^2*p^2) +
  Dist[b^2*n^2*p*(p+1)/(1+b^2*n^2*p^2),Int[Sec[a+b*Log[c*x^n]]^(p+2),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p<-1 && NonzeroQ[1+b^2*n^2*p^2]


(* ::Subsubsection::Closed:: *)
(*x^m Sec[a+b Log[c x^n]]^p		Products of monomials and powers of secants of logarithms*)


Int[Sec[a_.+b_.*Log[c_.*x_^n_.]]^2/x_,x_Symbol] :=
  Tan[a+b*Log[c*x^n]]/(b*n) /;
FreeQ[{a,b,c,n},x]


Int[Sec[a_.+b_.*Log[c_.*x_^n_.]]^p_/x_,x_Symbol] :=
  Tan[a+b*Log[c*x^n]]*Sec[a+b*Log[c*x^n]]^(p-2)/(b*n*(p-1)) +
  Dist[(p-2)/(p-1),Int[Sec[a+b*Log[c*x^n]]^(p-2)/x,x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p>1


Int[Sec[a_.+b_.*Log[c_.*x_^n_.]]^p_/x_,x_Symbol] :=
  -Sec[a+b*Log[c*x^n]]^(p+1)*Sin[a+b*Log[c*x^n]]/(b*n*p) +
  Dist[(p+1)/p,Int[Sec[a+b*Log[c*x^n]]^(p+2)/x,x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p<-1


Int[x_^m_.*Sec[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  x^(m+1)*Tan[a+b*Log[c*x^n]]*Sec[a+b*Log[c*x^n]]^(p-2)/(b*n*(p-1)) -
  (m+1)*x^(m+1)*Sec[a+b*Log[c*x^n]]^(p-2)/(b^2*n^2*(p-1)*(p-2)) +
  Dist[(b^2*n^2*(p-2)^2+(m+1)^2)/(b^2*n^2*(p-1)*(p-2)),Int[x^m*Sec[a+b*Log[c*x^n]]^(p-2),x]] /;
FreeQ[{a,b,c,m,n},x] && RationalQ[p] && p>1 && p!=2


Int[x_^m_.*Sec[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  (m+1)*x^(m+1)*Sec[a+b*Log[c*x^n]]^p/(b^2*n^2*p^2+(m+1)^2) -
  b*n*p*x^(m+1)*Sec[a+b*Log[c*x^n]]^(p+1)*Sin[a+b*Log[c*x^n]]/(b^2*n^2*p^2+(m+1)^2) +
  Dist[b^2*n^2*p*(p+1)/(b^2*n^2*p^2+(m+1)^2),Int[x^m*Sec[a+b*Log[c*x^n]]^(p+2),x]] /;
FreeQ[{a,b,c,m,n},x] && RationalQ[p] && p<-1 && NonzeroQ[b^2*n^2*p^2+(m+1)^2]


(* ::Subsection::Closed:: *)
(*Cosecant Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*Csc[a+b x]^n				Powers of cosecants of linears*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.526.1, CRC 295, A&S 4.3.116'*)


(* ::Item:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Csc[z] == -1/(1-Cos[z]^2)*Cos'[z]*)


Int[Csc[a_.+b_.*x_],x_Symbol] :=
  -ArcTanh[Cos[a+b*x]]/b /;
FreeQ[{a,b},x]


(* Note: This entirely redundant is required due to idem potent problem in Mathematica 6 & 7. *)
Int[1/Sqrt[Csc[a_.+b_.*x_]],x_Symbol] :=
  Sqrt[Csc[a+b*x]]*Sqrt[Sin[a+b*x]]*Int[Sqrt[Sin[a+b*x]],x] /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Extract constant factor*)


(* ::Item:: *)
(*Basis: D[(c*Csc[x])^n*Sin[x]^n,x] == 0*)


Int[(c_.*Csc[a_.+b_.*x_])^n_,x_Symbol] :=
  (c*Csc[a+b*x])^n*Sin[a+b*x]^n*Int[1/Sin[a+b*x]^n,x] /;
FreeQ[{a,b,c},x] && RationalQ[n] && -1<n<1


(* ::Item::Closed:: *)
(*Reference: G&R 2.526.2, CRC 308*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: Cot'[z] == -Csc[z]^2*)


Int[Csc[a_.+b_.*x_]^2,x_Symbol] :=
  -Cot[a+b*x]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is even, Csc[z]^n == -(1+Cot[z]^2)^((n-2)/2)*Cot'[z]*)


Int[Csc[a_.+b_.*x_]^n_,x_Symbol] :=
  Dist[-1/b,Subst[Int[Regularize[(1+x^2)^((n-2)/2),x],x],x,Cot[a+b*x]]] /;
FreeQ[{a,b},x] && EvenQ[n] && n>1


(* ::Item::Closed:: *)
(*Reference: G&R 2.510.3, CRC 309*)


(* ::Item:: *)
(*Derivation: Inverted integration by parts with a double-back flip*)


Int[(c_.*Csc[a_.+b_.*x_])^n_,x_Symbol] :=
  -c*Cos[a+b*x]*(c*Csc[a+b*x])^(n-1)/(b*(n-1)) + 
  Dist[(n-2)*c^2/(n-1),Int[(c*Csc[a+b*x])^(n-2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n>1 && Not[EvenQ[n]]


(* ::Item::Closed:: *)
(*Reference: G&R 2.510.2, CRC 299*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[(c_.*Csc[a_.+b_.*x_])^n_,x_Symbol] :=
  Cos[a+b*x]*(c*Csc[a+b*x])^(n+1)/(b*c*n) + 
  Dist[(n+1)/(c^2*n),Int[(c*Csc[a+b*x])^(n+2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n<-1


(* ::Subsubsection::Closed:: *)
(*x^m Csc[a+b x]^n			Products of monomials and powers of cosecants of linears*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Csc[a_.+b_.*x_],x_Symbol] :=
  -2*x^m*ArcTanh[E^(I*a+I*b*x)]/b +
  Dist[2*m/b,Int[x^(m-1)*ArcTanh[E^(I*a+I*b*x)],x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Reference: CRC 428, A&S 4.3.121*)


Int[x_^m_.*Csc[a_.+b_.*x_]^2,x_Symbol] :=
  -x^m*Cot[a+b*x]/b +
  Dist[m/b,Int[x^(m-1)*Cot[a+b*x],x]] /;
FreeQ[{a,b},x] && RationalQ[m] && m>0


(* ::Item:: *)
(*Reference: G&R 2.643.1, CRC 429', A&S 4.3.122*)


Int[x_*Csc[a_.+b_.*x_]^n_,x_Symbol] :=
  -x*Cot[a+b*x]*Csc[a+b*x]^(n-2)/(b*(n-1)) -
  Csc[a+b*x]^(n-2)/(b^2*(n-1)*(n-2)) +
  Dist[(n-2)/(n-1),Int[x*Csc[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n>1 && n!=2


(* ::Item:: *)
(*Reference: G&R 2.643.1*)


Int[x_^m_*Csc[a_.+b_.*x_]^n_,x_Symbol] :=
  -x^m*Cot[a+b*x]*Csc[a+b*x]^(n-2)/(b*(n-1)) -
  m*x^(m-1)*Csc[a+b*x]^(n-2)/(b^2*(n-1)*(n-2)) +
  Dist[(n-2)/(n-1),Int[x^m*Csc[a+b*x]^(n-2),x]] +
  Dist[m*(m-1)/(b^2*(n-1)*(n-2)),Int[x^(m-2)*Csc[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && n>1 && n!=2 && m>1


(* ::Item:: *)
(*Reference: G&R 2.631.2'*)


Int[x_*Csc[a_.+b_.*x_]^n_,x_Symbol] :=
  Csc[a+b*x]^n/(b^2*n^2) +
  x*Cos[a+b*x]*Csc[a+b*x]^(n+1)/(b*n) +
  Dist[(n+1)/n,Int[x*Csc[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n<-1


(* ::Item:: *)
(*Reference: G&R 2.631.2*)


Int[x_^m_*Csc[a_.+b_.*x_]^n_,x_Symbol] :=
  m*x^(m-1)*Csc[a+b*x]^n/(b^2*n^2) +
  x^m*Cos[a+b*x]*Csc[a+b*x]^(n+1)/(b*n) +
  Dist[(n+1)/n,Int[x^m*Csc[a+b*x]^(n+2),x]] -
  Dist[m*(m-1)/(b^2*n^2),Int[x^(m-2)*Csc[a+b*x]^n,x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && n<-1 && m>1


(* ::Subsubsection::Closed:: *)
(*(a+b Csc[c+d x])^n			Powers of constant plus cosecants of linears where a^2-b^2 is zero*)


Int[Sqrt[a_+b_.*Csc[c_.+d_.*x_]],x_Symbol] :=
  -2*a*ArcTan[Sqrt[-1+a/b*Csc[c+d*x]]]*Cot[c+d*x]/
		(d*Sqrt[-1+a/b*Csc[c+d*x]]*Sqrt[a+b*Csc[c+d*x]]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2-b^2]


(* Note: There should be a simpler antiderivative! *)
Int[1/Sqrt[a_+b_.*Csc[c_.+d_.*x_]],x_Symbol] :=
  -(Sqrt[2]*ArcTan[(Sqrt[2]*Sqrt[a])/Sqrt[-a+b*Csc[x]]]+2*ArcTan[Sqrt[-a+b*Csc[x]]/Sqrt[a]])*
  Sqrt[-a+b*Csc[x]]*Sqrt[a+b*Csc[x]]*Tan[x]/a^(3/2) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2-b^2]


(* ::Subsubsection::Closed:: *)
(*(a+b Csc[c+d x]^n)^m			Powers of constant plus powers of cosecants of linears*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If n is an integer, a+b*Csc[z]^n == (b+a*Sin[z]^n)/Sin[z]^n*)


Int[(a_+b_.*Csc[v_]^n_.)^m_,x_Symbol] :=
  Int[(b+a*Sin[v]^n)^m/Sin[v]^(m*n),x] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && m<0 && n>0


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If n is an integer, a+b*Csc[z]^n == (b+a*Sin[z]^n)/Sin[z]^n*)


Int[Sin[v_]^p_.*(a_+b_.*Csc[v_]^n_.)^m_,x_Symbol] :=
  Int[Sin[v]^(p-m*n)*(b+a*Sin[v]^n)^m,x] /;
FreeQ[{a,b},x] && IntegerQ[{m,n,p}] && m<0 && n>0


(* ::Subsubsection::Closed:: *)
(*Csc[a+b x]^m Sec[a+b x]^n		Products of powers of cosecants and secants*)


(* ::Item:: *)
(*Reference: G&R 2.526.49', CRC 329'*)


Int[Csc[a_.+b_.*x_]*Sec[a_.+b_.*x_],x_Symbol] :=
  -Log[Cot[a+b*x]]/b /;
FreeQ[{a,b},x] && NegQ[b]


Int[Csc[a_.+b_.*x_]^m_*Sec[a_.+b_.*x_]^n_,x_Symbol] :=
  -Csc[a+b*x]^(m-1)*Sec[a+b*x]^(n-1)/(b*(m-1)) /;
FreeQ[{a,b,m,n},x] && ZeroQ[m+n-2] && NonzeroQ[m-1] && PosQ[m]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If m and n are integers and m+n is even, Csc[z]^m*Sec[z]^n == -(1+Cot[z]^2)^((m+n)/2-1)/Cot[z]^n*Cot'[z]*)


Int[Csc[a_.+b_.*x_]^m_*Sec[a_.+b_.*x_]^n_.,x_Symbol] :=
  Dist[-1/b,Subst[Int[Regularize[(1+x^2)^((m+n)/2-1)/x^n,x],x],x,Cot[a+b*x]]] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && EvenQ[m+n] && 0<n<m
(* FreeQ[{a,b},x] && OddQ[m] && OddQ[n] *)


(* ::Item:: *)
(*Reference: G&R 2.510.1*)


Int[Csc[a_.+b_.*x_]^m_*Sec[a_.+b_.*x_]^n_,x_Symbol] :=
  -Csc[a+b*x]^(m-1)*Sec[a+b*x]^(n+1)/(b*(m-1)) +
  Dist[(n+1)/(m-1),Int[Csc[a+b*x]^(m-2)*Sec[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m>1 && n<-1


(* ::Item:: *)
(*Reference: G&R 2.510.3, CRC 334a, A&S 4.3.128b*)


Int[Csc[a_.+b_.*x_]^m_*Sec[a_.+b_.*x_]^n_.,x_Symbol] :=
  -Csc[a+b*x]^(m-1)*Sec[a+b*x]^(n-1)/(b*(m-1)) +
  Dist[(m+n-2)/(m-1),Int[Csc[a+b*x]^(m-2)*Sec[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m>1 && Not[EvenQ[m+n]] && Not[EvenQ[m] && OddQ[n] && n>1]


(* ::Item:: *)
(*Reference: G&R 2.510.2, CRC 323b, A&S 4.3.127b*)


Int[Csc[a_.+b_.*x_]^m_*Sec[a_.+b_.*x_]^n_.,x_Symbol] :=
  Csc[a+b*x]^(m+1)*Sec[a+b*x]^(n-1)/(b*(m+n)) +
  Dist[(m+1)/(m+n),Int[Csc[a+b*x]^(m+2)*Sec[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m<-1 && NonzeroQ[m+n]


(* ::Subsubsection::Closed:: *)
(*Csc[a+b x]^m Cot[a+b x]^n		Products of powers of cosecants and cotangents*)
(**)


(* ::Item:: *)
(*Derivation: Power rule for integration*)


Int[Csc[a_.+b_.*x_]^m_.*Cot[a_.+b_.*x_]^n_.,x_Symbol] :=
  -Csc[a+b*x]^m/(b*m) /;
FreeQ[{a,b,m},x] && n===1


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If m is even, Csc[z]^m == -(1+Cot[z]^2)^((m-2)/2)*Cot'[z]*)


Int[Csc[a_.+b_.*x_]^m_*Cot[a_.+b_.*x_]^n_.,x_Symbol] :=
  Dist[-1/b,Subst[Int[Regularize[x^n*(1+x^2)^((m-2)/2),x],x],x,Cot[a+b*x]]] /;
FreeQ[{a,b,n},x] && EvenQ[m] && m>2 && Not[OddQ[n] && 0<n<m-1]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is odd, Csc[z]^m*Cot[z]^n == -Csc[z]^(m-1)*(-1+Csc[z]^2)^((n-1)/2)*Csc'[z]*)


Int[Csc[a_.+b_.*x_]^m_.*Cot[a_.+b_.*x_]^n_.,x_Symbol] :=
  Dist[-1/b,Subst[Int[Regularize[x^(m-1)*(-1+x^2)^((n-1)/2),x],x],x,Csc[a+b*x]]] /;
FreeQ[{a,b,m},x] && OddQ[n] && Not[EvenQ[m] && 0<m<=n+1]


(* ::Item:: *)
(*Reference: G&R 2.510.6, CRC 334b*)


Int[Csc[a_.+b_.*x_]^m_*Cot[a_.+b_.*x_]^n_,x_Symbol] :=
  -Csc[a+b*x]^(m-2)*Cot[a+b*x]^(n+1)/(b*(n+1)) -
  Dist[(m-2)/(n+1),Int[Csc[a+b*x]^(m-2)*Cot[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m>1 && n<-1 && Not[EvenQ[m]]


(* ::Item:: *)
(*Reference: G&R 2.510.5, CRC 323a*)


Int[Csc[a_.+b_.*x_]^m_*Cot[a_.+b_.*x_]^n_,x_Symbol] :=
  -Csc[a+b*x]^m*Cot[a+b*x]^(n-1)/(b*m) -
  Dist[(n-1)/m,Int[Csc[a+b*x]^(m+2)*Cot[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m<-1 && n>1 && Not[EvenQ[m]]


(* ::Item:: *)
(*Reference: G&R 2.510.2, CRC 323b*)


Int[Csc[a_.+b_.*x_]^m_.*Cot[a_.+b_.*x_]^n_,x_Symbol] :=
  Csc[a+b*x]^m*Cot[a+b*x]^(n+1)/(b*m) /;
FreeQ[{a,b,m,n},x] && ZeroQ[m+n+1]


Int[Csc[a_.+b_.*x_]^m_*Cot[a_.+b_.*x_]^n_,x_Symbol] :=
  Csc[a+b*x]^m*Cot[a+b*x]^(n+1)/(b*m) +
  Dist[(m+n+1)/m,Int[Csc[a+b*x]^(m+2)*Cot[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m<-1 && Not[EvenQ[m]]


(* ::Item:: *)
(*Reference: G&R 2.510.3, CRC 334a*)


Int[Csc[a_.+b_.*x_]^m_*Cot[a_.+b_.*x_]^n_,x_Symbol] :=
  -Csc[a+b*x]^(m-2)*Cot[a+b*x]^(n+1)/(b*(m+n-1)) +
  Dist[(m-2)/(m+n-1),Int[Csc[a+b*x]^(m-2)*Cot[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m>1 && NonzeroQ[m+n-1] && Not[EvenQ[m]] && Not[OddQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.510.4*)


Int[Csc[a_.+b_.*x_]^m_.*Cot[a_.+b_.*x_]^n_,x_Symbol] :=
  -Csc[a+b*x]^m*Cot[a+b*x]^(n-1)/(b*(m+n-1)) -
  Dist[(n-1)/(m+n-1),Int[Csc[a+b*x]^m*Cot[a+b*x]^(n-2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n>1 && NonzeroQ[m+n-1] && Not[EvenQ[m]] && Not[OddQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.510.1*)


Int[Csc[a_.+b_.*x_]^m_.*Cot[a_.+b_.*x_]^n_,x_Symbol] :=
  -Csc[a+b*x]^m*Cot[a+b*x]^(n+1)/(b*(n+1)) -
  Dist[(m+n+1)/(n+1),Int[Csc[a+b*x]^m*Cot[a+b*x]^(n+2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n<-1 && Not[EvenQ[m]]


(* ::Subsubsection::Closed:: *)
(*x^m Csc[a+b x^n]^p Cos[a+b x^n]	Products of monomials, cosines and powers of cosecants of binomials*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Csc[a_.+b_.*x_^n_.]^p_*Cos[a_.+b_.*x_^n_.],x_Symbol] :=
  -x^(m-n+1)*Csc[a+b*x^n]^(p-1)/(b*n*(p-1)) +
  Dist[(m-n+1)/(b*n*(p-1)),Int[x^(m-n)*Csc[a+b*x^n]^(p-1),x]] /;
FreeQ[{a,b,p},x] && RationalQ[m] && IntegerQ[n] && m-n>=0 && NonzeroQ[p-1]


(* ::Subsubsection::Closed:: *)
(*x^m Csc[a+b x^n]^p Cot[a+b x^n]	Products of monomials, cotangents and powers of cosecants of binomials*)
(**)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Csc[a_.+b_.*x_^n_.]^p_.*Cot[a_.+b_.*x_^n_.]^q_.,x_Symbol] :=
  -x^(m-n+1)*Csc[a+b*x^n]^p/(b*n*p) +
  Dist[(m-n+1)/(b*n*p),Int[x^(m-n)*Csc[a+b*x^n]^p,x]] /;
FreeQ[{a,b,p},x] && RationalQ[m] && IntegerQ[n] && m-n>=0 && q===1 (* Required so InputForm is matchable *)


(* ::Subsubsection::Closed:: *)
(*Csc[a+b Log[c x^n]]^p			Powers of cosecants of logarithms*)


Int[Csc[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  -x*Cot[a+b*Log[c*x^n]]*Csc[a+b*Log[c*x^n]]^(p-2)/(b*n*(p-1)) -
  x*Csc[a+b*Log[c*x^n]]^(p-2)/(b^2*n^2*(p-1)*(p-2)) +
  Dist[(1+b^2*n^2*(p-2)^2)/(b^2*n^2*(p-1)*(p-2)),Int[Csc[a+b*Log[c*x^n]]^(p-2),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p>1 && p!=2


Int[Csc[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  x*Csc[a+b*Log[c*x^n]]^p/(1+b^2*n^2*p^2) +
  b*n*p*x*Cos[a+b*Log[c*x^n]]*Csc[a+b*Log[c*x^n]]^(p+1)/(1+b^2*n^2*p^2) +
  Dist[b^2*n^2*p*(p+1)/(1+b^2*n^2*p^2),Int[Csc[a+b*Log[c*x^n]]^(p+2),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p<-1 && NonzeroQ[1+b^2*n^2*p^2]


(* ::Subsubsection::Closed:: *)
(*x^m Csc[a+b Log[c x^n]]^p		Products of monomials and powers of cosecants of logarithms*)


Int[Csc[a_.+b_.*Log[c_.*x_^n_.]]^2/x_,x_Symbol] :=
  -Cot[a+b*Log[c*x^n]]/(b*n) /;
FreeQ[{a,b,c,n},x]


Int[Csc[a_.+b_.*Log[c_.*x_^n_.]]^p_/x_,x_Symbol] :=
  -Cot[a+b*Log[c*x^n]]*Csc[a+b*Log[c*x^n]]^(p-2)/(b*n*(p-1)) +
  Dist[(p-2)/(p-1),Int[Csc[a+b*Log[c*x^n]]^(p-2)/x,x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p>1


Int[Csc[a_.+b_.*Log[c_.*x_^n_.]]^p_/x_,x_Symbol] :=
  Cos[a+b*Log[c*x^n]]*Csc[a+b*Log[c*x^n]]^(p+1)/(b*n*p) +
  Dist[(p+1)/p,Int[Csc[a+b*Log[c*x^n]]^(p+2)/x,x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p<-1


Int[x_^m_.*Csc[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  -x^(m+1)*Cot[a+b*Log[c*x^n]]*Csc[a+b*Log[c*x^n]]^(p-2)/(b*n*(p-1)) -
  (m+1)*x^(m+1)*Csc[a+b*Log[c*x^n]]^(p-2)/(b^2*n^2*(p-1)*(p-2)) +
  Dist[(b^2*n^2*(p-2)^2+(m+1)^2)/(b^2*n^2*(p-1)*(p-2)),Int[x^m*Csc[a+b*Log[c*x^n]]^(p-2),x]] /;
FreeQ[{a,b,c,m,n},x] && RationalQ[p] && p>1 && p!=2


Int[x_^m_.*Csc[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  (m+1)*x^(m+1)*Csc[a+b*Log[c*x^n]]^p/(b^2*n^2*p^2+(m+1)^2) +
  b*n*p*x^(m+1)*Cos[a+b*Log[c*x^n]]*Csc[a+b*Log[c*x^n]]^(p+1)/(b^2*n^2*p^2+(m+1)^2) +
  Dist[b^2*n^2*p*(p+1)/(b^2*n^2*p^2+(m+1)^2),Int[x^m*Csc[a+b*Log[c*x^n]]^(p+2),x]] /;
FreeQ[{a,b,c,m,n},x] && RationalQ[p] && p<-1 && NonzeroQ[b^2*n^2*p^2+(m+1)^2]


(* ::Subsection::Closed:: *)
(*Powers of sums of Trig Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*(a Cos[c+d x] + b Sin[c+d x])^n	Powers of sums of sines and cosines*)


Int[(a_.*Cos[c_.+d_.*x_]+b_.*Sin[c_.+d_.*x_])^n_,x_Symbol] :=
  a*(a*Cos[c+d*x]+b*Sin[c+d*x])^n/(b*d*n) /;
FreeQ[{a,b,c,d,n},x] && ZeroQ[a^2+b^2]


(* ::Item:: *)
(*Reference: G&R 2.557.5b'*)


Int[1/(a_.*Cos[c_.+d_.*x_]+b_.*Sin[c_.+d_.*x_])^2,x_Symbol] :=
  Sin[c+d*x]/(a*d*(a*Cos[c+d*x]+b*Sin[c+d*x])) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2+b^2]


(* ::Item:: *)
(*Basis: a*Cos[z]+b*Sin[z] == Sqrt[a^2+b^2]*Cos[z-ArcTan[a,b]]*)


Int[Sqrt[a_.*Cos[c_.+d_.*x_]+b_.*Sin[c_.+d_.*x_]],x_Symbol] :=
  2*EllipticE[(c+d*x-ArcTan[a,b])/2,2]*Sqrt[a*Cos[c+d*x]+b*Sin[c+d*x]]/
  (d*Sqrt[(a*Cos[c+d*x]+b*Sin[c+d*x])/Sqrt[a^2+b^2]]) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2+b^2]


(* ::Item:: *)
(*Basis: a*Cos[z]+b*Sin[z] == Sqrt[a^2+b^2]*Cos[z-ArcTan[a,b]]*)


Int[1/Sqrt[a_.*Cos[c_.+d_.*x_]+b_.*Sin[c_.+d_.*x_]],x_Symbol] :=
  2*EllipticF[(c+d*x-ArcTan[a,b])/2,2]*Sqrt[(a*Cos[c+d*x]+b*Sin[c+d*x])/Sqrt[a^2+b^2]]/
  (d*Sqrt[a*Cos[c+d*x]+b*Sin[c+d*x]]) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2+b^2]


(* ::Item::Closed:: *)
(*Reference: G&R 2.557'*)


(* ::Item:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is odd, (a*Cos[z]+b*Sin[z])^n == (a^2+b^2-u^2)^((n-1)/2)*D[u,z] where u = -b*Cos[z]+a*Sin[z]*)


(* Note: For odd n<-1, better to stay in the trig world using 2nd rule below ??? *)
Int[(a_.*Cos[c_.+d_.*x_]+b_.*Sin[c_.+d_.*x_])^n_,x_Symbol] :=
  Dist[1/d,Subst[Int[Regularize[(a^2+b^2-x^2)^((n-1)/2),x],x],x,-b*Cos[c+d*x]+a*Sin[c+d*x]]] /;
FreeQ[{a,b},x] && OddQ[n] && n>=-1 && NonzeroQ[a^2+b^2]


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[(a_.*Cos[c_.+d_.*x_]+b_.*Sin[c_.+d_.*x_])^n_,x_Symbol] :=
  -(b*Cos[c+d*x]-a*Sin[c+d*x])*(a*Cos[c+d*x]+b*Sin[c+d*x])^(n-1)/(d*n) +
  Dist[(n-1)*(a^2+b^2)/n,Int[(a*Cos[c+d*x]+b*Sin[c+d*x])^(n-2),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n>1 && NonzeroQ[a^2+b^2] && Not[OddQ[n]]


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[(a_.*Cos[c_.+d_.*x_]+b_.*Sin[c_.+d_.*x_])^n_,x_Symbol] :=
  (b*Cos[c+d*x]-a*Sin[c+d*x])*(a*Cos[c+d*x]+b*Sin[c+d*x])^(n+1)/(d*(n+1)*(a^2+b^2)) + 
  Dist[(n+2)/((n+1)*(a^2+b^2)),Int[(a*Cos[c+d*x]+b*Sin[c+d*x])^(n+2),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n<-1 && NonzeroQ[a^2+b^2]


(* ::Subsubsection::Closed:: *)
(*(a Csc[c+d x] - a Sin[c+d x])^n		where a+b is zero*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Csc[z]-Sin[z] == Cos[z]*Cot[z]*)


Int[(a_.*Csc[c_.+d_.*x_]+b_.*Sin[c_.+d_.*x_])^n_,x_Symbol] :=
  Int[(a*Cos[c+d*x]*Cot[c+d*x])^n,x] /;
FreeQ[{a,b,c,d,n},x] && ZeroQ[a+b]


(* ::Subsubsection::Closed:: *)
(*(a Sec[c+d x] - a Cos[c+d x])^n		where a+b is zero*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Sec[z]-Cos[z] == Sin[z]*Tan[z]*)


Int[(a_.*Sec[c_.+d_.*x_]+b_.*Cos[c_.+d_.*x_])^n_,x_Symbol] :=
  Int[(a*Sin[c+d*x]*Tan[c+d*x])^n,x] /;
FreeQ[{a,b,c,d,n},x] && ZeroQ[a+b]


(* ::Subsection::Closed:: *)
(*Rational functions of Trig Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*u Trig[c+d x]^m / (a Cos[c+d x]+b Sin[c+d x])	where a^2+b^2 is nonzero*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sin[z]^2/(a*Cos[z]+b*Sin[z]) == b/(a^2+b^2)*Sin[z] - a/(a^2+b^2)*Cos[z] + a^2/((a^2+b^2)*(a*Cos[z]+b*Sin[z]))*)


Int[u_.*Sin[c_.+d_.*x_]^n_./(a_.*Cos[c_.+d_.*x_]+b_.*Sin[c_.+d_.*x_]),x_Symbol] :=
  Dist[b/(a^2+b^2),Int[u*Sin[c+d*x]^(n-1),x]] -
  Dist[a/(a^2+b^2),Int[u*Sin[c+d*x]^(n-2)*Cos[c+d*x],x]] +
  Dist[a^2/(a^2+b^2),Int[u*Sin[c+d*x]^(n-2)/(a*Cos[c+d*x]+b*Sin[c+d*x]),x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2+b^2] && IntegerQ[n] && n>0 &&
(n>1 || MatchQ[u,v_.*Tan[c+d*x]^m_. /; IntegerQ[m] && m>0])


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cos[z]^2/(a*Cos[z]+b*Sin[z]) == a/(a^2+b^2)*Cos[z] - b/(a^2+b^2)*Sin[z] + b^2/(a^2+b^2)/(a*Cos[z]+b*Sin[z])*)


Int[u_.*Cos[c_.+d_.*x_]^n_./(a_.*Cos[c_.+d_.*x_]+b_.*Sin[c_.+d_.*x_]),x_Symbol] :=
  Dist[a/(a^2+b^2),Int[u*Cos[c+d*x]^(n-1),x]] -
  Dist[b/(a^2+b^2),Int[u*Cos[c+d*x]^(n-2)*Sin[c+d*x],x]] +
  Dist[b^2/(a^2+b^2),Int[u*Cos[c+d*x]^(n-2)/(a*Cos[c+d*x]+b*Sin[c+d*x]),x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2+b^2] && IntegerQ[n] && n>0 &&
(n>1 || MatchQ[u,v_.*Cot[c+d*x]^m_. /; IntegerQ[m] && m>0])


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cos[z]*Sin[z]/(a*Cos[z]+b*Sin[z]) == b/(a^2+b^2)*Cos[z] + a/(a^2+b^2)*Sin[z] - a*b/((a^2+b^2)*(a*Cos[z]+b*Sin[z]))*)


(* Int[u_.*Cos[c_.+d_.*x_]^m_.*Sin[c_.+d_.*x_]^n_./(a_.*Cos[c_.+d_.*x_]+b_.*Sin[c_.+d_.*x_]),x_Symbol] :=
  Dist[b/(a^2+b^2),Int[u*Cos[c+d*x]^m*Sin[c+d*x]^(n-1),x]] +
  Dist[a/(a^2+b^2),Int[u*Cos[c+d*x]^(m-1)*Sin[c+d*x]^n,x]] -
  Dist[a*b/(a^2+b^2),Int[u*Cos[c+d*x]^(m-1)*Sin[c+d*x]^(n-1)/(a*Cos[c+d*x]+b*Sin[c+d*x]),x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2+b^2] && IntegerQ[{m,n}] && m>0 && n>0 *)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sec[z]/(a*Cos[z]+b*Sin[z]) == Tan[z]/b + (b*Cos[z]-a*Sin[z])/(b*(a*Cos[z]+b*Sin[z]))*)


(* Int[u_.*Sec[c_.+d_.*x_]/(a_.*Cos[c_.+d_.*x_]+b_.*Sin[c_.+d_.*x_]),x_Symbol] :=
  Dist[1/b,Int[u*Tan[c+d*x],x]] + 
  Dist[1/b,Int[u*(b*Cos[c+d*x]-a*Sin[c+d*x])/(a*Cos[c+d*x]+b*Sin[c+d*x]),x]] /;
FreeQ[{a,b,c,d},x] *)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Csc[z]/(a*Cos[z]+b*Sin[z]) == Cot[z]/a - (b*Cos[z]-a*Sin[z])/(a*(a*Cos[z]+b*Sin[z]))*)


(* Int[u_.*Csc[c_.+d_.*x_]/(a_.*Cos[c_.+d_.*x_]+b_.*Sin[c_.+d_.*x_]),x_Symbol] :=
  Dist[1/a,Int[u*Cot[c+d*x],x]] - 
  Dist[1/a,Int[u*(b*Cos[c+d*x]-a*Sin[c+d*x])/(a*Cos[c+d*x]+b*Sin[c+d*x]),x]] /;
FreeQ[{a,b,c,d},x] *)


(* ::Subsubsection::Closed:: *)
(*(a+b Cos[d+e x]+c Sin[d+e x])^n			where a^2-b^2-c^2 is zero*)


(* ::Item:: *)
(*Reference: G&R 2.558.4d*)


Int[1/(a_+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_]),x_Symbol] :=
(* (-c*Cos[d+e*x]+b*Sin[d+e*x])/(a*e*(a+b*Cos[d+e*x]+c*Sin[d+e*x])) *)
  -2/(e*(c+(a-b)*Tan[(d+e*x)/2])) /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[a^2-b^2-c^2]


Int[Sqrt[a_+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_]],x_Symbol] :=
  -2*(c*Cos[d+e*x]-b*Sin[d+e*x])/(e*Sqrt[a+b*Cos[d+e*x]+c*Sin[d+e*x]]) /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[a^2-b^2-c^2]


(* Int[1/Sqrt[a_+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_]],x_Symbol] :=
  -2*ArcTanh[Sin[z/2]]*Cos[z/2]/(e*Sqrt[a+b*Cos[d+e*x]+c*Sin[d+e*x]]) /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[a^2-b^2-c^2] *)


(* Int[1/Sqrt[a_+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_]],x_Symbol] :=
  2*ArcTanh[Sin[z/2]]*Cos[z/2]/(e*Sqrt[a+b*Cos[d+e*x]+c*Sin[d+e*x]]) /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[a^2-b^2-c^2] *)


Int[(a_+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_])^n_,x_Symbol] :=
  (-c*Cos[d+e*x]+b*Sin[d+e*x])*(a+b*Cos[d+e*x]+c*Sin[d+e*x])^(n-1)/(e*n) +
  Dist[a*(2*n-1)/n,Int[(a+b*Cos[d+e*x]+c*Sin[d+e*x])^(n-1),x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[n] && n>1 && ZeroQ[a^2-b^2-c^2]


Int[(a_+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_])^n_,x_Symbol] :=
  (c*Cos[d+e*x]-b*Sin[d+e*x])*(a+b*Cos[d+e*x]+c*Sin[d+e*x])^n/(a*e*(2*n+1)) +
  Dist[(n+1)/(a*(2*n+1)),Int[(a+b*Cos[d+e*x]+c*Sin[d+e*x])^(n+1),x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[n] && n<-1 && ZeroQ[a^2-b^2-c^2]


(* ::Subsubsection::Closed:: *)
(*(a+b Cos[d+e x]+c Sin[d+e x])^n			where a^2-b^2-c^2 is nonzero*)


(* ::Item:: *)
(*Reference: G&R 2.558.4c*)


(* The following two rules should be unified?! *)
Int[1/(a_+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_]),x_Symbol] :=
  Log[a+c*Tan[(d+e*x)/2]]/(c*e) /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[a-b]


Int[1/(a_+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_]),x_Symbol] :=
  -Log[a+c*Cot[(d+e*x)/2]]/(c*e) /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[a+b]


(* ::Item:: *)
(*Reference: G&R 2.558.4a, CRC 342b*)


Int[1/(a_+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_]),x_Symbol] :=
  2*ArcTan[(c+(a-b)*Tan[(d+e*x)/2])/Rt[a^2-b^2-c^2,2]]/(e*Rt[a^2-b^2-c^2,2]) /;
FreeQ[{a,b,c,d,e},x] && NonzeroQ[a^2-b^2] && PosQ[a^2-b^2-c^2]


(* ::Item:: *)
(*Reference: G&R 2.558.4b', CRC 342b'*)


Int[1/(a_+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_]),x_Symbol] :=
  -2*ArcTanh[(c+(a-b)*Tan[(d+e*x)/2])/Rt[-a^2+b^2+c^2,2]]/(e*Rt[-a^2+b^2+c^2,2]) /;
FreeQ[{a,b,c,d,e},x] && NonzeroQ[a^2-b^2] && NegQ[a^2-b^2-c^2]


(* ::Item:: *)
(*Basis: a+b*Cos[z]+c*Sin[z] == a+Sqrt[b^2+c^2]*Cos[z-ArcTan[b,c]]*)


Int[Sqrt[a_.+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_]],x_Symbol] :=
  2*EllipticE[(d+e*x-ArcTan[b,c])/2,2/(1+a/Sqrt[b^2+c^2])]*Sqrt[a+b*Cos[d+e*x]+c*Sin[d+e*x]]/
  (e*Sqrt[(a+b*Cos[d+e*x]+c*Sin[d+e*x])/(a+Sqrt[b^2+c^2])]) /;
FreeQ[{a,b,c,d,e},x] && NonzeroQ[a^2-b^2-c^2]


(* ::Item:: *)
(*Basis: a+b*Cos[z]+c*Sin[z] == a+Sqrt[b^2+c^2]*Cos[z-ArcTan[b,c]]*)


Int[1/Sqrt[a_.+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_]],x_Symbol] :=
  2*EllipticF[(d+e*x-ArcTan[b,c])/2,2/(1+a/Sqrt[b^2+c^2])]*
    Sqrt[(a+b*Cos[d+e*x]+c*Sin[d+e*x])/(a+Sqrt[b^2+c^2])]/
  (e*Sqrt[a+b*Cos[d+e*x]+c*Sin[d+e*x]]) /;
FreeQ[{a,b,c,d,e},x] && NonzeroQ[a^2-b^2-c^2]


(* ::Item:: *)
(*Reference: G&R 2.558.1*)


Int[(a_+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_])^n_,x_Symbol] :=
  (-c*Cos[d+e*x]+b*Sin[d+e*x])*(a+b*Cos[d+e*x]+c*Sin[d+e*x])^(n+1)/(e*(n+1)*(a^2-b^2-c^2)) +
  Dist[1/((n+1)*(a^2-b^2-c^2)),
    Int[((n+1)*a-(n+2)*b*Cos[d+e*x]-(n+2)*c*Sin[d+e*x])*(a+b*Cos[d+e*x]+c*Sin[d+e*x])^(n+1),x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[n] && n<-1 && NonzeroQ[a^2-b^2-c^2]


(* ::Subsubsection::Closed:: *)
(*(A+B Cos[d+e x]+C Sin[d+e x]) (a+b Cos[d+e x]+c Sin[d+e x])^n		where a^2-b^2-c^2 is nonzero*)
(**)


(* ::Item:: *)
(*Reference: G&R 2.558.2*)


Int[(A_.+C_.*Sin[d_.+e_.*x_])/(a_.+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_]),x_Symbol] :=
  -b*C*Log[a+b*Cos[d+e*x]+c*Sin[d+e*x]]/(e*(b^2+c^2)) +
  c*C*(d+e*x)/(e*(b^2+c^2)) +
  Dist[(A-a*c*C/(b^2+c^2)),Int[1/(a+b*Cos[d+e*x]+c*Sin[d+e*x]),x]] /;
FreeQ[{a,b,c,d,e,A,C},x] && NonzeroQ[b^2+c^2] && NonzeroQ[A-a*c*C/(b^2+c^2)]


(* ::Item:: *)
(*Reference: G&R 2.558.2*)


Int[(A_.+B_.*Cos[d_.+e_.*x_])/(a_.+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_]),x_Symbol] :=
  c*B*Log[a+b*Cos[d+e*x]+c*Sin[d+e*x]]/(e*(b^2+c^2)) +
  b*B*(d+e*x)/(e*(b^2+c^2)) +
  Dist[(A-a*b*B/(b^2+c^2)),Int[1/(a+b*Cos[d+e*x]+c*Sin[d+e*x]),x]] /;
FreeQ[{a,b,c,d,e,A,B},x] && NonzeroQ[b^2+c^2] && NonzeroQ[A-a*b*B/(b^2+c^2)]


(* ::Item:: *)
(*Reference: G&R 2.558.2*)


Int[(A_.+B_.*Cos[d_.+e_.*x_]+C_.*Sin[d_.+e_.*x_])/(a_.+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_]),x_Symbol] :=
  (c*B-b*C)*Log[a+b*Cos[d+e*x]+c*Sin[d+e*x]]/(e*(b^2+c^2)) +
  (b*B+c*C)*(d+e*x)/(e*(b^2+c^2)) +
  Dist[(A-a*(b*B+c*C)/(b^2+c^2)),Int[1/(a+b*Cos[d+e*x]+c*Sin[d+e*x]),x]] /;
FreeQ[{a,b,c,d,e,A,B,C},x] && NonzeroQ[b^2+c^2] && NonzeroQ[A-a*(b*B+c*C)/(b^2+c^2)]


(* ::Item:: *)
(*Reference: G&R 2.558.1 inverted*)


(* Int[(A_.+B_.*Cos[d_.+e_.*x_]+C_.*Sin[d_.+e_.*x_])*(a_+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_])^n_,x_Symbol] :=
  (B*c-b*C-a*C*Cos[d+e*x]+a*B*Sin[d+e*x])*(a+b*Cos[d+e*x]+c*Sin[d+e*x])^n/(a*e*(n+1)) + 
  Dist[1/(a*(n+1)),Int[(a*(b*B+c*C)*n + a^2*A*(n+1) + 
    (a^2*B*n + c*(b*C-c*B)*n + a*b*A*(n+1))*Cos[d+e*x] + 
    (a^2*C*n - b*(b*C-c*B)*n + a*c*A*(n+1))*Sin[d+e*x])*
    (a+b*Cos[d+e*x]+c*Sin[d+e*x])^(n-1), x]] /;
FreeQ[{a,b,c,d,e,A,B,C},x] && RationalQ[n] && n>1 && NonzeroQ[a^2-b^2-c^2] *)


(* ::Item:: *)
(*Reference: G&R 2.558.1*)


Int[(A_.+C_.*Sin[d_.+e_.*x_])*(a_.+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_])^n_,x_Symbol] :=
  (b*C+(a*C-c*A)*Cos[d+e*x]+b*A*Sin[d+e*x])*(a+b*Cos[d+e*x]+c*Sin[d+e*x])^(n+1)/
    (e*(n+1)*(a^2-b^2-c^2)) +
  Dist[1/((n+1)*(a^2-b^2-c^2)),
    Int[((n+1)*(a*A-c*C)-(n+2)*b*A*Cos[d+e*x]+(n+2)*(a*C-c*A)*Sin[d+e*x])*
      (a+b*Cos[d+e*x]+c*Sin[d+e*x])^(n+1),x]] /;
FreeQ[{a,b,c,d,e,A,C},x] && RationalQ[n] && n<-1 && NonzeroQ[a^2-b^2-c^2]


(* ::Item:: *)
(*Reference: G&R 2.558.1*)


Int[(A_.+B_.*Cos[d_.+e_.*x_])*(a_.+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_])^n_,x_Symbol] :=
  -(c*B+c*A*Cos[d+e*x]+(a*B-b*A)*Sin[d+e*x])*(a+b*Cos[d+e*x]+c*Sin[d+e*x])^(n+1)/
    (e*(n+1)*(a^2-b^2-c^2)) +
  Dist[1/((n+1)*(a^2-b^2-c^2)),
    Int[((n+1)*(a*A-b*B)+(n+2)*(a*B-b*A)*Cos[d+e*x]-(n+2)*c*A*Sin[d+e*x])*
      (a+b*Cos[d+e*x]+c*Sin[d+e*x])^(n+1),x]] /;
FreeQ[{a,b,c,d,e,A,B},x] && RationalQ[n] && n<-1 && NonzeroQ[a^2-b^2-c^2]


(* ::Item:: *)
(*Reference: G&R 2.558.1*)


Int[(A_.+B_.*Cos[d_.+e_.*x_]+C_.*Sin[d_.+e_.*x_])*(a_.+b_.*Cos[d_.+e_.*x_]+c_.*Sin[d_.+e_.*x_])^n_,x_Symbol] :=
  -(c*B-b*C-(a*C-c*A)*Cos[d+e*x]+(a*B-b*A)*Sin[d+e*x])*(a+b*Cos[d+e*x]+c*Sin[d+e*x])^(n+1)/
    (e*(n+1)*(a^2-b^2-c^2)) +
  Dist[1/((n+1)*(a^2-b^2-c^2)),
    Int[((n+1)*(a*A-b*B-c*C)+(n+2)*(a*B-b*A)*Cos[d+e*x]+(n+2)*(a*C-c*A)*Sin[d+e*x])*
      (a+b*Cos[d+e*x]+c*Sin[d+e*x])^(n+1),x]] /;
FreeQ[{a,b,c,d,e,A,B,C},x] && RationalQ[n] && n<-1 && NonzeroQ[a^2-b^2-c^2]


(* ::Subsubsection::Closed:: *)
(*Trig[v] (a+b Tan[v])^n*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: (a+b*Tan[z])/Sec[z] == a*Cos[z] + b*Sin[z]*)


Int[Sec[v_]^m_.*(a_+b_.*Tan[v_])^n_., x_Symbol] :=
  Int[(a*Cos[v]+b*Sin[v])^n,x] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && m+n==0 


(* ::Subsubsection::Closed:: *)
(*Trig[v] (a+b Cot[v])^n*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: (a+b*Cot[z])/Csc[z] == b*Cos[z] + a*Sin[z]*)


Int[Csc[v_]^m_.*(a_+b_.*Cot[v_])^n_., x_Symbol] :=
  Int[(b*Cos[v]+a*Sin[v])^n,x] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && m+n==0 


(* ::Subsection::Closed:: *)
(*Exponential and Trig Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*Exp[a + b x] Sin[c + d x]^n		Products of exponentials and powers of sines of linears*)


(* ::Item:: *)
(*Reference: CRC 533, A&S 4.3.136*)


Int[E^(a_.+b_.*x_)*Sin[c_.+d_.*x_],x_Symbol] :=
  -d*E^(a+b*x)*Cos[c+d*x]/(b^2+d^2) + b*E^(a+b*x)*Sin[c+d*x]/(b^2+d^2) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[b^2+d^2]


(* ::Item:: *)
(*Reference: CRC 542, A&S 4.3.138*)


Int[E^(a_.+b_.*x_)*Sin[c_.+d_.*x_]^n_,x_Symbol] :=
  b*E^(a+b*x)*Sin[c+d*x]^n/(b^2+d^2*n^2) - 
  d*n*E^(a+b*x)*Cos[c+d*x]*Sin[c+d*x]^(n-1)/(b^2+d^2*n^2) + 
  Dist[n*(n-1)*d^2/(b^2+d^2*n^2),Int[E^(a+b*x)*Sin[c+d*x]^(n-2),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n>1


(* ::Subsubsection::Closed:: *)
(*Exp[a + b x] Cos[c + d x]^n		Products of exponentials and powers of cosines of linears*)


(* ::Item:: *)
(*Reference: CRC 538, A&S 4.3.137*)


Int[E^(a_.+b_.*x_)*Cos[c_.+d_.*x_],x_Symbol] :=
  b*E^(a+b*x)*Cos[c+d*x]/(b^2+d^2) + d*E^(a+b*x)*Sin[c+d*x]/(b^2+d^2) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[b^2+d^2]


(* ::Item:: *)
(*Reference: CRC 543, A&S 4.3.139*)


Int[E^(a_.+b_.*x_)*Cos[c_.+d_.*x_]^n_,x_Symbol] :=
  b*E^(a+b*x)*Cos[c+d*x]^n/(b^2+d^2*n^2) + 
  d*n*E^(a+b*x)*Cos[c+d*x]^(n-1)*Sin[c+d*x]/(b^2+d^2*n^2) +
  Dist[n*(n-1)*d^2/(b^2+d^2*n^2),Int[E^(a+b*x)*Cos[c+d*x]^(n-2),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n>1


(* ::Subsubsection::Closed:: *)
(*Exp[a + b x] Sec[c + d x]^n		Products of exponentials and powers of secants of linears*)


(* ::Item:: *)
(*Reference: CRC 552*)


Int[E^(a_.+b_.*x_)*Sec[c_.+d_.*x_]^n_,x_Symbol] :=
  -b*E^(a+b*x)*Sec[c+d*x]^(n-2)/(d^2*(n-1)*(n-2)) + 
  E^(a+b*x)*Sec[c+d*x]^(n-1)*Sin[c+d*x]/(d*(n-1)) +
  Dist[(b^2+d^2*(n-2)^2)/(d^2*(n-1)*(n-2)),Int[E^(a+b*x)*Sec[c+d*x]^(n-2),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n>1 && n!=2


(* ::Subsubsection::Closed:: *)
(*Exp[a + b x] Csc[c + d x]^n		Products of exponentials and powers of cosecants of linears*)
(**)


(* ::Item:: *)
(*Reference: CRC 551*)


Int[E^(a_.+b_.*x_)*Csc[c_.+d_.*x_]^n_,x_Symbol] :=
  -b*E^(a+b*x)*Csc[c+d*x]^(n-2)/(d^2*(n-1)*(n-2)) - 
  E^(a+b*x)*Cos[c+d*x]*Csc[c+d*x]^(n-1)/(d*(n-1)) +
  Dist[(b^2+d^2*(n-2)^2)/(d^2*(n-1)*(n-2)),Int[E^(a+b*x)*Csc[c+d*x]^(n-2),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n>1 && n!=2


(* ::Subsubsection::Closed:: *)
(*x^m Exp[a + b x] Sin[c + d x]^n	Products of monomials, exponentials and powers of sines of linears*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*E^(a_.+b_.*x_)*Sin[c_.+d_.*x_]^n_.,x_Symbol] :=
  Module[{u=Block[{ShowSteps=False,StepCounter=Null}, Int[E^(a+b*x)*Sin[c+d*x]^n,x]]},
  x^m*u - Dist[m,Int[x^(m-1)*u,x]]] /;
FreeQ[{a,b,c,d},x] && RationalQ[m] && IntegerQ[n] && m>0 && n>0


(* ::Subsubsection::Closed:: *)
(*x^m Exp[a + b x] Cos[c + d x]^n	Products of exponentials and powers of cosines of linears*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*E^(a_.+b_.*x_)*Cos[c_.+d_.*x_]^n_.,x_Symbol] :=
  Module[{u=Block[{ShowSteps=False,StepCounter=Null}, Int[E^(a+b*x)*Cos[c+d*x]^n,x]]},
  x^m*u - Dist[m,Int[x^(m-1)*u,x]]] /;
FreeQ[{a,b,c,d},x] && RationalQ[m] && IntegerQ[n] && m>0 && n>0


(* ::Subsubsection::Closed:: *)
(*u f^v Trig[w]				Products of exponentials and trig functions of polynomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sin[z] == I/2*(1/E^(I*z) - E^(I*z)) *)


Int[f_^v_*Sin[w_],x_Symbol] :=
  Dist[I/2,Int[f^v/E^(I*w),x]] - 
  Dist[I/2,Int[f^v*E^(I*w),x]] /;
FreeQ[f,x] && PolynomialQ[v,x] && Exponent[v,x]<=2 && PolynomialQ[w,x] && Exponent[w,x]<=2


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sin[z] == I/2*(1/E^(I*z) - E^(I*z)) *)


Int[f_^v_*Sin[w_]^n_,x_Symbol] :=
  Dist[(I/2)^n,Int[f^v*(1/E^(I*w)-E^(I*w))^n,x]] /;
FreeQ[f,x] && IntegerQ[n] && n>0 && PolynomialQ[v,x] && Exponent[v,x]<=2 && PolynomialQ[w,x] && Exponent[w,x]<=2


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cos[z] == 1/2*(E^(I*z) + 1/E^(I*z))*)


Int[f_^v_*Cos[w_],x_Symbol] :=
  Dist[1/2,Int[f^v*E^(I*w),x]] + 
  Dist[1/2,Int[f^v/E^(I*w),x]] /;
FreeQ[f,x] && PolynomialQ[v,x] && Exponent[v,x]<=2 && PolynomialQ[w,x] && Exponent[w,x]<=2


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cos[z] == 1/2*(E^(I*z) + 1/E^(I*z))*)


Int[f_^v_*Cos[w_]^n_,x_Symbol] :=
  Dist[1/2^n,Int[f^v*(E^(I*w)+1/E^(I*w))^n,x]] /;
FreeQ[f,x] && IntegerQ[n] && n>0 && PolynomialQ[v,x] && Exponent[v,x]<=2 && PolynomialQ[w,x] && Exponent[w,x]<=2


(* ::Subsection::Closed:: *)
(*Trig Function Simplification Rules*)


(* ::Subsubsection::Closed:: *)
(*u (a-a Trig[v]^2)^n*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1-Sin[z]^2 == Cos[z]^2*)


Int[u_.*(a_+b_.*Sin[v_]^2)^n_.,x_Symbol] :=
  Dist[a^n,Int[u*Cos[v]^(2*n),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && ZeroQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1 - Cos[z]^2 == Sin[z]^2*)


Int[u_.*(a_+b_.*Cos[v_]^2)^n_.,x_Symbol] :=
  Dist[a^n,Int[u*Sin[v]^(2*n),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && ZeroQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1 + Tan[z]^2 == Sec[z]^2*)


Int[u_.*(a_+b_.*Tan[v_]^2)^n_.,x_Symbol] :=
  Dist[a^n,Int[u*Sec[v]^(2*n),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && ZeroQ[a-b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1 + Cot[z]^2 == Csc[z]^2*)


Int[u_.*(a_+b_.*Cot[v_]^2)^n_.,x_Symbol] :=
  Dist[a^n,Int[u*Csc[v]^(2*n),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && ZeroQ[a-b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: -1 + Sec[z]^2 == Tan[z]^2*)


Int[u_.*(a_+b_.*Sec[v_]^2)^n_.,x_Symbol] :=
  Dist[b^n,Int[u*Tan[v]^(2*n),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && ZeroQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: -1 + Csc[z]^2 == Cot[z]^2*)


Int[u_.*(a_+b_.*Csc[v_]^2)^n_.,x_Symbol] :=
  Dist[b^n,Int[u*Cot[v]^(2*n),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && ZeroQ[a+b]


(* ::Subsubsection::Closed:: *)
(*u (a Tan[v]^m+b Sec[v]^m)^n		Simplify sum of powers of trig functions*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If a^2-b^2=0, then a*Tan[z]+b*Sec[z] == a*Tan[z/2+a/b*Pi/4]*)


Int[(a_.*Tan[v_]+b_.*Sec[v_])^n_,x_Symbol] :=
  Dist[a^n,Int[Tan[v/2+a/b*Pi/4]^n,x]] /;
FreeQ[{a,b},x] && ZeroQ[a^2-b^2] && EvenQ[n]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: a*Sec[z]+b*Tan[z] == (a+b*Sin[z])/Cos[z]*)


Int[u_.*(a_.*Sec[v_]^m_.+b_.*Tan[v_]^m_.)^n_.,x_Symbol] :=
  Int[u*(a+b*Sin[v]^m)^n/Cos[v]^(m*n),x] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && (OddQ[m*n] || m*n<0) && Not[m==2 && ZeroQ[a+b]]


(* ::Subsubsection::Closed:: *)
(*u (a Cot[v]^m+b Csc[v]^m)^n		Simplify sum of powers of trig functions*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If a^2-b^2=0, then a*Cot[z]+b*Csc[z] == a*Cot[z/2+(a/b-1)*Pi/4]*)


Int[(a_.*Cot[v_]+b_.*Csc[v_])^n_,x_Symbol] :=
  Dist[a^n,Int[Cot[v/2+(a/b-1)*Pi/4]^n,x]] /;
FreeQ[{a,b},x] && ZeroQ[a^2-b^2] && EvenQ[n]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: a*Csc[z]+b*Cot[z] == (a+b*Cos[z])/Sin[z]*)


Int[u_.*(a_.*Csc[v_]^m_.+b_.*Cot[v_]^m_.)^n_.,x_Symbol] :=
  Int[u*(a+b*Cos[v]^m)^n/Sin[v]^(m*n),x] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && (OddQ[m*n] || m*n<0) && Not[m==2 && ZeroQ[a+b]]


(* ::Subsubsection::Closed:: *)
(*x^m Trig[u]^n Trig[v]^p*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Sin[z]*Cos[z] == Sin[2*z]/2*)


(* Int[x_^m_.*Sin[v_]^n_.*Cos[v_]^n_.,x_Symbol] :=
  Dist[1/2^n,Int[x^m*Sin[Dist[2,v]]^n,x]] /;
IntegerQ[n] *)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Sec[z]*Csc[z] == 2*Csc[2*z]*)


Int[x_^m_.*Sec[v_]^n_.*Csc[v_]^n_.,x_Symbol] :=
  Dist[2^n,Int[x^m*Csc[Dist[2,v]]^n,x]] /;
IntegerQ[{m,n}] && m>0


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Convert trig function to complex exponentials*)


(* Got to improve x^m*f[e^x] integration before doing this! *)
(* Int[x_^m_.*f_[u_]^n_.*g_[v_]^p_.,x_Symbol] :=
  Int[x^m*TrigToExp[f[u]]^n*TrigToExp[g[v]]^p,x] /;
IntegerQ[{m,n,p}] && TrigQ[f] && TrigQ[g] *)


(* ::Subsection::Closed:: *)
(*Trig Function Substitution Rules*)


(* ::Subsubsection::Closed:: *)
(*Pure sine function substitution rules*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[Sin[z]]*Cos[z] == f[Sin[z]] * Sin'[z]*)


Int[u_*Cos[c_.*(a_.+b_.*x_)],x_Symbol] :=
  Dist[1/(b*c),Subst[Int[Regularize[SubstFor[Sin[c*(a+b*x)],u,x],x],x],x,Sin[c*(a+b*x)]]] /;
FreeQ[{a,b,c},x] && FunctionOfQ[Sin[c*(a+b*x)],u,x,True]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[Sin[z]]*Cot[z] == f[Sin[z]]/Sin[z] * Sin'[z]*)


Int[u_*Cot[c_.*(a_.+b_.*x_)],x_Symbol] :=
  Dist[1/(b*c),Subst[Int[Regularize[SubstFor[Sin[c*(a+b*x)],u,x]/x,x],x],x,Sin[c*(a+b*x)]]] /;
FreeQ[{a,b,c},x] && FunctionOfQ[Sin[c*(a+b*x)],u,x,True]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[Sin[z]]*Sin[2*z] == 2*f[Sin[z]]*Sin[z] * Sin'[z]*)


Int[u_*Sin[c_.*(a_.+b_.*x_)],x_Symbol] :=
  Dist[4/(b*c),Subst[Int[Regularize[x*SubstFor[Sin[c*(a+b*x)/2],u,x],x],x],x,Sin[c*(a+b*x)/2]]] /;
FreeQ[{a,b,c},x] && FunctionOfQ[Sin[c*(a+b*x)/2],u,x,True]


(* ::Subsubsection::Closed:: *)
(*Pure cosine function substitution rules*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[Cos[z]]*Sin[z] == -f[Cos[z]] * Cos'[z]*)


Int[u_*Sin[c_.*(a_.+b_.*x_)],x_Symbol] :=
  -Dist[1/(b*c),Subst[Int[Regularize[SubstFor[Cos[c*(a+b*x)],u,x],x],x],x,Cos[c*(a+b*x)]]] /;
FreeQ[{a,b,c},x] && FunctionOfQ[Cos[c*(a+b*x)],u,x,True]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[Cos[z]]*Tan[z] == -f[Cos[z]]/Cos[z] * Cos'[z]*)


Int[u_*Tan[c_.*(a_.+b_.*x_)],x_Symbol] :=
  -Dist[1/(b*c),Subst[Int[Regularize[SubstFor[Cos[c*(a+b*x)],u,x]/x,x],x],x,Cos[c*(a+b*x)]]] /;
FreeQ[{a,b,c},x] && FunctionOfQ[Cos[c*(a+b*x)],u,x,True]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[Cos[z]]*Sin[2*z] == -2*f[Cos[z]]*Cos[z] * Cos'[z]*)


Int[u_*Sin[c_.*(a_.+b_.*x_)],x_Symbol] :=
  -Dist[4/(b*c),Subst[Int[Regularize[x*SubstFor[Cos[c*(a+b*x)/2],u,x],x],x],x,Cos[c*(a+b*x)/2]]] /;
FreeQ[{a,b,c},x] && FunctionOfQ[Cos[c*(a+b*x)/2],u,x,True]


(* ::Subsubsection::Closed:: *)
(*Pure cotangent function substitution rules*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is an integer, f[Cot[z]]*Tan[z]^n == -f[Cot[z]]/(Cot[z]^n*(1+Cot[z]^2)) * Cot'[z]*)


Int[u_*Tan[c_.*(a_.+b_.*x_)]^n_.,x_Symbol] :=
  -Dist[1/(b*c),Subst[Int[Regularize[SubstFor[Cot[c*(a+b*x)],u,x]/(x^n*(1+x^2)),x],x],x,Cot[c*(a+b*x)]]] /;
FreeQ[{a,b,c},x] && IntegerQ[n] && FunctionOfQ[Cot[c*(a+b*x)],u,x,True] && TryPureTanSubst[u*Tan[c*(a+b*x)]^n,x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[Cot[z]] == -f[Cot[z]]/(1+Cot[z]^2) * Cot'[z]*)


If[ShowSteps,

Int[u_,x_Symbol] :=
  Module[{v=FunctionOfTrig[u,x]},
  ShowStep["","Int[f[Cot[a+b*x]],x]","Subst[Int[f[x]/(1+x^2),x],x,Cot[a+b*x]]/b",Hold[
  Dist[-1/Coefficient[v,x,1],Subst[Int[Regularize[SubstFor[Cot[v],u,x]/(1+x^2),x],x],x,Cot[v]]]]] /;
 NotFalseQ[v] && FunctionOfQ[Cot[v],u,x,True] && TryPureTanSubst[u,x]] /;
SimplifyFlag,

Int[u_,x_Symbol] :=
  Module[{v=FunctionOfTrig[u,x]},
  Dist[-1/Coefficient[v,x,1],Subst[Int[Regularize[SubstFor[Cot[v],u,x]/(1+x^2),x],x],x,Cot[v]]] /;
 NotFalseQ[v] && FunctionOfQ[Cot[v],u,x,True] && TryPureTanSubst[u,x]]]


(* ::Subsubsection::Closed:: *)
(*Pure tangent function substitution rules*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is an integer, f[Tan[z]]*Cot[z]^n == f[Tan[z]]/(Tan[z]^n*(1+Tan[z]^2)) * Tan'[z]*)


Int[u_*Cot[c_.*(a_.+b_.*x_)]^n_.,x_Symbol] :=
  Dist[1/(b*c),Subst[Int[Regularize[SubstFor[Tan[c*(a+b*x)],u,x]/(x^n*(1+x^2)),x],x],x,Tan[c*(a+b*x)]]] /;
FreeQ[{a,b,c},x] && IntegerQ[n] && FunctionOfQ[Tan[c*(a+b*x)],u,x,True] && TryPureTanSubst[u*Cot[c*(a+b*x)]^n,x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[Tan[z]] == f[Tan[z]]/(1+Tan[z]^2) * Tan'[z]*)


If[ShowSteps,

Int[u_,x_Symbol] :=
  Module[{v=FunctionOfTrig[u,x]},
  ShowStep["","Int[f[Tan[a+b*x]],x]","Subst[Int[f[x]/(1+x^2),x],x,Tan[a+b*x]]/b",Hold[
  Dist[1/Coefficient[v,x,1],Subst[Int[Regularize[SubstFor[Tan[v],u,x]/(1+x^2),x],x],x,Tan[v]]]]] /;
 NotFalseQ[v] && FunctionOfQ[Tan[v],u,x,True] && TryPureTanSubst[u,x]] /;
SimplifyFlag,

Int[u_,x_Symbol] :=
  Module[{v=FunctionOfTrig[u,x]},
  Dist[1/Coefficient[v,x,1],Subst[Int[Regularize[SubstFor[Tan[v],u,x]/(1+x^2),x],x],x,Tan[v]]] /;
 NotFalseQ[v] && FunctionOfQ[Tan[v],u,x,True] && TryPureTanSubst[u,x]]]


TryPureTanSubst[u_,x_Symbol] :=
  Not[MatchQ[u,ArcTan[a_.*Tan[v_]] /; FreeQ[a,x]]] &&
  Not[MatchQ[u,ArcTan[a_.*Cot[v_]] /; FreeQ[a,x]]] &&
  Not[MatchQ[u,ArcCot[a_.*Tan[v_]] /; FreeQ[a,x]]] &&
  Not[MatchQ[u,ArcCot[a_.*Cot[v_]] /; FreeQ[a,x]]] &&
  u===ExpnExpand[u,x]
