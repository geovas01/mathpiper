(* ::Package:: *)

(* ::Title:: *)
(*Hyperbolic Function Integration Rules*)


(* ::Subsection::Closed:: *)
(*Hyperbolic Sine Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*Sinh[a+b x]^n				Powers of sines of linears*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.01.20, CRC 554, A&S 4.5.77*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: Cosh'[z] == -Sinh[z]*)


Int[Sinh[a_.+b_.*x_],x_Symbol] :=
  Cosh[a+b*x]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Reference: G&R 2.414.2, CRC 566*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[Sinh[a_.+b_.*x_]^2,x_Symbol] :=
  -x/2 + Cosh[a+b*x]*Sinh[a+b*x]/(2*b) /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is odd, Sinh[z]^n == (-1+Cosh[z]^2)^((n-1)/2)*Cosh'[z]*)


Int[Sinh[a_.+b_.*x_]^n_,x_Symbol] :=
  Dist[1/b,Subst[Int[Regularize[(-1+x^2)^((n-1)/2),x],x],x,Cosh[a+b*x]]] /;
FreeQ[{a,b},x] && OddQ[n] && n>1


(* ::Subsubsection::Closed:: *)
(*(a+b Sinh[c+d x])^n			Powers of linear binomials of sines of linears*)


Int[1/(a_+b_.*Sinh[c_.+d_.*x_]),x_Symbol] :=
  -Cosh[c+d*x]/(d*(b-a*Sinh[c+d*x])) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2+b^2]


(* ::Item:: *)
(*Reference: G&R 2.441.3b*)


Int[1/(a_+b_.*Sinh[c_.+d_.*x_]),x_Symbol] :=
  -2*ArcTanh[(b-a*Tanh[(c+d*x)/2])/Rt[a^2+b^2,2]]/(d*Rt[a^2+b^2,2]) /;
FreeQ[{a,b,c,d},x] && PosQ[a^2+b^2]


Int[1/(a_+b_.*Sinh[c_.+d_.*x_]),x_Symbol] :=
  2*ArcTan[(b-a*Tanh[(c+d*x)/2])/Rt[-a^2-b^2,2]]/(d*Rt[-a^2-b^2,2]) /;
FreeQ[{a,b,c,d},x] && NegQ[a^2+b^2]


(* ::ItemParagraph:: *)
(**)


Int[Sqrt[a_+b_.*Sinh[c_.+d_.*x_]],x_Symbol] :=
  2*b*Cosh[c+d*x]/(d*Sqrt[a+b*Sinh[c+d*x]]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2+b^2]


(* ::Item:: *)
(*Basis: D[EllipticE[x,n],x] == Sqrt[1-n*Sin[x]^2]*)


Int[Sqrt[a_.+b_.*Sinh[c_.+d_.*x_]],x_Symbol] :=
  2*I*Sqrt[a-I*b]*EllipticE[(Pi/2-I*(c+d*x))/2,2*b/(a*I+b)]/d /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2+b^2] && PositiveQ[a-I*b]


(* ::Item::Closed:: *)
(*Derivation: Extract constant factor*)


(* ::Item:: *)
(*Basis: D[Sqrt[a+b*f[c+d*x]]/Sqrt[(a+b*f[c+d*x])/(a+b)],x] == 0*)


Int[Sqrt[a_.+b_.*Sinh[c_.+d_.*x_]],x_Symbol] :=
  Sqrt[a+b*Sinh[c+d*x]]/Sqrt[(a+b*Sinh[c+d*x])/(a-I*b)]*Int[Sqrt[a/(a-I*b)+b/(a-I*b)*Sinh[c+d*x]],x] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2+b^2] && Not[PositiveQ[a-I*b]]


(* ::ItemParagraph:: *)
(**)


Int[1/Sqrt[a_+b_.*Sinh[c_.+d_.*x_]],x_Symbol] :=
  -2*ArcTanh[Cosh[(c+Pi*I/2+d*x)/2]]*Sinh[(c+Pi*I/2+d*x)/2]/(d*Sqrt[a+b*Sinh[c+d*x]]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a-b*I]


Int[1/Sqrt[a_+b_.*Sinh[c_.+d_.*x_]],x_Symbol] :=
  2*ArcTan[Sinh[(c+Pi*I/2+d*x)/2]]*Cosh[(c+Pi*I/2+d*x)/2]/(d*Sqrt[a+b*Sinh[c+d*x]]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a+b*I]


(* ::Item:: *)
(*Basis: D[EllipticF[x,n],x] == 1/Sqrt[1-n*Sin[x]^2]*)


Int[1/Sqrt[a_.+b_.*Sinh[c_.+d_.*x_]],x_Symbol] :=
  2*I*EllipticF[(Pi/2-I*(c+d*x))/2,2*b/(a*I+b)]/(d*Sqrt[a-I*b]) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2+b^2] && PositiveQ[a-I*b]


(* ::Item::Closed:: *)
(*Derivation: Extract constant factor*)


(* ::Item:: *)
(*Basis: D[Sqrt[(a+b*f[c+d*x])/(a+b)]/Sqrt[a+b*f[c+d*x]],x] == 0*)


Int[1/Sqrt[a_.+b_.*Sinh[c_.+d_.*x_]],x_Symbol] :=
  Sqrt[(a+b*Sinh[c+d*x])/(a-I*b)]/Sqrt[a+b*Sinh[c+d*x]]*Int[1/Sqrt[a/(a-I*b)+b/(a-I*b)*Sinh[c+d*x]],x] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2+b^2] && Not[PositiveQ[a-I*b]]


(* ::ItemParagraph:: *)
(**)


Int[(a_+b_.*Sinh[c_.+d_.*x_])^n_,x_Symbol] :=
  b*Cosh[c+d*x]*(a+b*Sinh[c+d*x])^(n-1)/(d*n) +
  Dist[a*(2*n-1)/n,Int[(a+b*Sinh[c+d*x])^(n-1),x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n>1 && ZeroQ[a^2+b^2]


Int[(a_+b_.*Sinh[c_.+d_.*x_])^n_,x_Symbol] :=
  -b*Cosh[c+d*x]*(a+b*Sinh[c+d*x])^n/(a*d*(2*n+1)) +
  Dist[(n+1)/(a*(2*n+1)),Int[(a+b*Sinh[c+d*x])^(n+1),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n<-1 && ZeroQ[a^2+b^2]


(* ::Item:: *)
(*Reference: G&R 2.441.1 inverted*)


(* This results in an infinite loop!!! *)
(* Int[(a_+b_.*Sinh[c_.+d_.*x_])^n_,x_Symbol] :=
  -b*Cosh[c+d*x]*(a+b*Sinh[c+d*x])^n/(a*d*n) +
  Dist[(a^2+b^2)/a,Int[(a+b*Sinh[c+d*x])^(n-1),x]] +
  Dist[b*(n+1)/(a*n),Int[Sinh[c+d*x]*(a+b*Sinh[c+d*x])^n,x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n>0 && NonzeroQ[a^2+b^2] *)


Int[1/(a_+b_.*Sinh[c_.+d_.*x_])^2,x_Symbol] :=
  -b*Cosh[c+d*x]/(d*(a^2+b^2)*(a+b*Sinh[c+d*x])) + 
  Dist[a/(a^2+b^2),Int[1/(a+b*Sinh[c+d*x]),x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2+b^2]


Int[(a_+b_.*Sinh[c_.+d_.*x_])^n_,x_Symbol] :=
  b*Cosh[c+d*x]*(a+b*Sinh[c+d*x])^(n+1)/(d*(n+1)*(a^2+b^2)) +
  Dist[1/((n+1)*(a^2+b^2)),Int[(a*(n+1)-b*(n+2)*Sinh[c+d*x])*(a+b*Sinh[c+d*x])^(n+1),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n<-1 && NonzeroQ[a^2+b^2]


(* ::ItemParagraph::Closed:: *)
(**)


(* ::Item:: *)
(*Reference: G&R 2.411.2, CRC 567b*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[(c_.*Sinh[a_.+b_.*x_])^n_,x_Symbol] :=
  c*Cosh[a+b*x]*(c*Sinh[a+b*x])^(n-1)/(b*n) -
  Dist[(n-1)*c^2/n,Int[(c*Sinh[a+b*x])^(n-2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n>1 && Not[OddQ[n]]


(* ::Item::Closed:: *)
(*Reference: G&R 2.411.5, CRC 568a*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[(c_.*Sinh[a_.+b_.*x_])^n_,x_Symbol] :=
  Cosh[a+b*x]*(c*Sinh[a+b*x])^(n+1)/(c*b*(n+1)) - 
  Dist[(n+2)/((n+1)*c^2),Int[(c*Sinh[a+b*x])^(n+2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n<-1


(* ::Item::Closed:: *)
(*Derivation: Extract constant factor*)


(* ::Item:: *)
(*Basis: D[(c*Sinh[x])^n/Sinh[x]^n,x] == 0*)


Int[(c_*Sinh[a_.+b_.*x_])^n_,x_Symbol] :=
  (c*Sinh[a+b*x])^n/Sinh[a+b*x]^n*Int[Sinh[a+b*x]^n,x] /;
FreeQ[{a,b,c},x] && RationalQ[n] && -1<n<1


(* ::Subsubsection::Closed:: *)
(*x^m (a+b Sinh[c+d x])^n		Products of monomials and powers of constants plus sines of linears*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1-I*Sinh[z]==-2*Sinh[z/2+Pi*I/4]^2==2*Cosh[z/2-Pi*I/4]^2*)


Int[x_^m_.*(a_+b_.*Sinh[c_.+d_.*x_])^n_,x_Symbol] :=
(*Dist[(-2*a)^n,Int[x^m*Sinh[Pi*I/4+c/2+d*x/2]^(2*n),x]] /; *)
  Dist[(2*a)^n,Int[x^m*Cosh[-Pi*I/4+c/2+d*x/2]^(2*n),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[m] && IntegerQ[n] && n<0 && ZeroQ[a-b*I]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1-I*Sinh[z]==-2*Sinh[z/2+Pi*I/4]^2==2*Cosh[z/2-Pi*I/4]^2*)


Int[x_^m_.*(a_+b_.*Sinh[c_.+d_.*x_])^n_,x_Symbol] :=
(*Dist[2^n,Int[x^m*(-a*Sinh[Pi*I/4+c/2+d*x/2]^2)^n,x]] /; *)
  Dist[2^n,Int[x^m*(a*Cosh[-Pi*I/4+c/2+d*x/2]^2)^n,x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[{m,n}] && ZeroQ[a-b*I]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1+I*Sinh[z]==-2*Sinh[z/2-Pi*I/4]^2==2*Cosh[z/2+Pi*I/4]^2*)


Int[x_^m_.*(a_+b_.*Sinh[c_.+d_.*x_])^n_,x_Symbol] :=
(*Dist[(-2*a)^n,Int[x^m*Sinh[-Pi*I/4+c/2+d*x/2]^(2*n),x]] /; *)
  Dist[(2*a)^n,Int[x^m*Cosh[Pi*I/4+c/2+d*x/2]^(2*n),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[m] && IntegerQ[n] && n<0 && ZeroQ[a+b*I]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1+I*Sinh[z]==-2*Sinh[z/2-Pi*I/4]^2==2*Cosh[z/2+Pi*I/4]^2*)


Int[x_^m_.*(a_+b_.*Sinh[c_.+d_.*x_])^n_,x_Symbol] :=
(*Dist[2^n,Int[x^m*(-a*Sinh[-Pi*I/4+c/2+d*x/2]^2)^n,x]] /; *)
  Dist[2^n,Int[x^m*(a*Cosh[Pi*I/4+c/2+d*x/2]^2)^n,x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[{m,n}] && ZeroQ[a+b*I]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: 1/(a+b z)^2 == a/((a^2+b^2) (a+b z)) + b (b-a z)/((a^2+b^2) (a+b z)^2)*)


Int[x_/(a_+b_.*Sinh[c_.+d_.*x_])^2,x_Symbol] :=
  Dist[a/(a^2+b^2),Int[x/(a+b*Sinh[c+d*x]),x]] +
  Dist[b/(a^2+b^2),Int[x*(b-a*Sinh[c+d*x])/(a+b*Sinh[c+d*x])^2,x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2+b^2]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: a+b*Sinh[z] == (-b+2*a*E^z+b*E^(2*z))/(2*E^z)*)


Int[x_^m_.*(a_+b_.*Sinh[c_.+d_.*x_])^n_,x_Symbol] :=
  Dist[1/2^n,Int[x^m*(-b+2*a*E^(c+d*x)+b*E^(2*(c+d*x)))^n/E^(n*(c+d*x)),x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2+b^2] && IntegerQ[n] && n<0 && RationalQ[m] && m>0


(* ::Subsubsection::Closed:: *)
(*(A+B Sinh[c+d x]) (a+b Sinh[c+d x])^n  Products of powers of linear binomials of sines*)


(* ::Item:: *)
(*Basis: (A+B*z)/Sqrt[a+b*z] == (b*A-a*B)/(b*Sqrt[a+b*z]) + B/b*Sqrt[a+b*z]*)


Int[(A_.+B_.*Sinh[c_.+d_.*x_])/Sqrt[a_+b_.*Sinh[c_.+d_.*x_]],x_Symbol] :=
  Dist[(b*A-a*B)/b,Int[1/Sqrt[a+b*Sinh[c+d*x]],x]] +
  Dist[B/b,Int[Sqrt[a+b*Sinh[c+d*x]],x]] /;
FreeQ[{a,b,c,d,A,B},x] && NonzeroQ[b*A-a*B]


(* ::Item:: *)
(*Reference: G&R 2.441.1 inverted*)


Int[(A_.+B_.*Sinh[c_.+d_.*x_])*(a_+b_.*Sinh[c_.+d_.*x_])^n_,x_Symbol] :=
  B*Cosh[c+d*x]*(a+b*Sinh[c+d*x])^n/(d*(n+1)) + 
  Dist[1/(n+1),Int[(-b*B*n+a*A*(n+1) + (a*B*n+b*A*(n+1))*Sinh[c+d*x])*(a+b*Sinh[c+d*x])^(n-1),x]] /;
FreeQ[{a,b,c,d,A,B},x] && RationalQ[n] && n>1 && NonzeroQ[a^2+b^2]


(* ::Item:: *)
(*Reference: G&R 2.441.1 special case*)


Int[(A_+B_.*Sinh[c_.+d_.*x_])/(a_+b_.*Sinh[c_.+d_.*x_])^2,x_Symbol] :=
  B*Cosh[c+d*x]/(a*d*(a+b*Sinh[c+d*x])) /;
FreeQ[{a,b,c,d,A,B},x] && ZeroQ[a*A+b*B]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_*(A_+B_.*Sinh[c_.+d_.*x_])/(a_+b_.*Sinh[c_.+d_.*x_])^2,x_Symbol] :=
  B*x*Cosh[c+d*x]/(a*d*(a+b*Sinh[c+d*x])) -
  Dist[B/(a*d),Int[Cosh[c+d*x]/(a+b*Sinh[c+d*x]),x]] /;
FreeQ[{a,b,c,d,A,B},x] && ZeroQ[a*A+b*B]


(* ::Item:: *)
(*Reference: G&R 2.441.1*)


Int[(A_.+B_.*Sinh[c_.+d_.*x_])*(a_.+b_.*Sinh[c_.+d_.*x_])^n_,x_Symbol] :=
  -(a*B-b*A)*Cosh[c+d*x]*(a+b*Sinh[c+d*x])^(n+1)/(d*(n+1)*(a^2+b^2)) +
  Dist[1/((n+1)*(a^2+b^2)),Int[((n+1)*(a*A+b*B)+(n+2)*(a*B-b*A)*Sinh[c+d*x])*(a+b*Sinh[c+d*x])^(n+1),x]] /;
FreeQ[{a,b,c,d,A,B},x] && RationalQ[n] && n<-1 && NonzeroQ[a^2+b^2]


(* ::Subsubsection::Closed:: *)
(*x^m (a+b Sinh[c+d x]^2)^n		Products of monomials and powers of quadratic binomials of sines of linears*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Sinh[z]^2 == (-1 + Cosh[2*z])/2*)


Int[x_^m_./(a_+b_.*Sinh[c_.+d_.*x_]^2),x_Symbol] :=
  Dist[2,Int[x^m/(2*a-b+b*Cosh[2*c+2*d*x]),x]] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m>0 && NonzeroQ[a-b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: a+b*Cosh[z]^2+c*Sinh[z]^2 == (2*a+b-c + (b+c)*Cosh[2*z])/2*)


Int[x_^m_./(a_.+b_.*Cosh[d_.+e_.*x_]^2+c_.*Sinh[d_.+e_.*x_]^2),x_Symbol] :=
  Dist[2,Int[x^m/(2*a+b-c+(b+c)*Cosh[2*d+2*e*x]),x]] /;
FreeQ[{a,b,c,d,e},x] && IntegerQ[m] && m>0 && NonzeroQ[a+b] && NonzeroQ[a-c]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Sinh[z]^2 == (-1 + Cosh[2*z])/2*)


Int[(a_+b_.*Sinh[c_.+d_.*x_]^2)^n_,x_Symbol] :=
  Dist[1/2^n,Int[(2*a-b+b*Cosh[2*c+2*d*x])^n,x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a-b] && HalfIntegerQ[n]


(* ::Subsubsection::Closed:: *)
(*x^m (a+b Sinh[c+d x] Cosh[c+d x])^n	Products of monomials and powers involving products of sines and cosines*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Sinh[z]*Cosh[z] == Sinh[2*z]/2*)


Int[x_^m_./(a_+b_.*Sinh[c_.+d_.*x_]*Cosh[c_.+d_.*x_]),x_Symbol] :=
  Int[x^m/(a+b*Sinh[2*c+2*d*x]/2),x] /;
FreeQ[{a,b,c,d},x] && IntegerQ[m] && m>0


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Sinh[z]*Cosh[z] == Sinh[2*z]/2*)


Int[(a_+b_.*Sinh[c_.+d_.*x_]*Cosh[c_.+d_.*x_])^n_,x_Symbol] :=
  Int[(a+b*Sinh[2*c+2*d*x]/2)^n,x] /;
FreeQ[{a,b,c,d},x] && HalfIntegerQ[n]


(* ::Subsubsection::Closed:: *)
(*Sinh[a+b x]^m Cosh[a+b x]^n		Products of powers of sines and cosines*)


Int[Sinh[a_.+b_.*x_]^m_.*Cosh[a_.+b_.*x_]^n_,x_Symbol] :=
  Sinh[a+b*x]^(m+1)*Cosh[a+b*x]^(n+1)/(b*(m+1)) /;
FreeQ[{a,b,m,n},x] && ZeroQ[m+n+2] && NonzeroQ[m+1] && PosQ[m]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is odd, Cosh[z]^n == (1+Sinh[z]^2)^((n-1)/2)*Sinh'[z]*)


Int[Sinh[a_.+b_.*x_]^m_*Cosh[a_.+b_.*x_]^n_,x_Symbol] :=
  Dist[1/b,Subst[Int[Regularize[x^m*(1+x^2)^((n-1)/2),x],x],x,Sinh[a+b*x]]] /;
FreeQ[{a,b,m},x] && OddQ[n] && Not[OddQ[m] && 0<m<n]


(* ::Item:: *)
(*Reference: G&R 2.411.3*)


Int[Sinh[a_.+b_.*x_]^m_*Cosh[a_.+b_.*x_]^n_,x_Symbol] :=
  Sinh[a+b*x]^(m-1)*Cosh[a+b*x]^(n+1)/(b*(n+1)) -
  Dist[(m-1)/(n+1),Int[Sinh[a+b*x]^(m-2)*Cosh[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m>1 && n<-1


(* ::Item:: *)
(*Reference: G&R 2.411.2, CRC 567b, A&S 4.5.85b*)


Int[Sinh[a_.+b_.*x_]^m_*Cosh[a_.+b_.*x_]^n_,x_Symbol] :=
  Sinh[a+b*x]^(m-1)*Cosh[a+b*x]^(n+1)/(b*(m+n)) -
  Dist[(m-1)/(m+n),Int[Sinh[a+b*x]^(m-2)*Cosh[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m>1 && Not[OddQ[m]] && NonzeroQ[m+n] &&
Not[OddQ[n] && n>1]


(* ::Item:: *)
(*Reference: G&R 2.411.5, CRC 568a, A&S 4.5.86a*)


Int[Sinh[a_.+b_.*x_]^m_*Cosh[a_.+b_.*x_]^n_,x_Symbol] :=
  Sinh[a+b*x]^(m+1)*Cosh[a+b*x]^(n+1)/(b*(m+1)) -
  Dist[(m+n+2)/(m+1),Int[Sinh[a+b*x]^(m+2)*Cosh[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m<-1 && NonzeroQ[m+n+2]


(* Kool rule *)
Int[Sinh[a_.+b_.*x_]^m_*Cosh[a_.+b_.*x_]^n_,x_Symbol] :=
  Dist[1/(b*m),Subst[Int[x^(1/m)/(1-x^(2/m)),x],x,Sinh[a+b*x]^m*Cosh[a+b*x]^n]] /;
FreeQ[{a,b},x] && FractionQ[{m,n}] && ZeroQ[m+n] && IntegerQ[1/m] && m>0


(* ::Subsubsection::Closed:: *)
(*Sinh[a+b x]^m Tanh[a+b x]^n	Products of powers of sines and tangents*)
(**)


(* ::Item::Closed:: *)
(*Reference: G&R 2.423.18'*)


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sinh[z]*Tanh[z] == Cosh[z]-Sech[z]*)


Int[Sinh[a_.+b_.*x_]*Tanh[a_.+b_.*x_],x_Symbol] :=
  Sinh[a+b*x]/b - Int[Sech[a+b*x],x] /;
FreeQ[{a,b},x]


Int[Sinh[a_.+b_.*x_]^m_*Tanh[a_.+b_.*x_]^n_,x_Symbol]:=
  Sinh[a+b*x]^m*Tanh[a+b*x]^(n-1)/(b*m) /;
FreeQ[{a,b,m,n},x] && ZeroQ[m+n-1]


(* ::Item:: *)
(*Derivation: Integration by substitution*)


Int[Sinh[a_.+b_.*x_]^m_.*Tanh[a_.+b_.*x_]^n_.,x_Symbol] :=
  Dist[1/b,Subst[Int[Regularize[(-1+x^2)^((m+n-1)/2)/x^n,x],x],x,Cosh[a+b*x]]] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && OddQ[m+n]


(* ::Item:: *)
(*Reference: G&R 2.411.1, CRC 567a*)


Int[Sinh[a_.+b_.*x_]^m_*Tanh[a_.+b_.*x_]^n_,x_Symbol] :=
  Sinh[a+b*x]^m*Tanh[a+b*x]^(n+1)/(b*m) -
  Dist[(n+1)/m,Int[Sinh[a+b*x]^(m-2)*Tanh[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m>1 && n<-1 && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.411.6, CRC 568b*)


Int[Sinh[a_.+b_.*x_]^m_*Tanh[a_.+b_.*x_]^n_,x_Symbol] :=
  Sinh[a+b*x]^(m+2)*Tanh[a+b*x]^(n-1)/(b*(n-1)) -
  Dist[(m+2)/(n-1),Int[Sinh[a+b*x]^(m+2)*Tanh[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m<-1 && n>1 && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.411.2, CRC 567b*)


Int[Sinh[a_.+b_.*x_]^m_*Tanh[a_.+b_.*x_]^n_.,x_Symbol]:=
  Sinh[a+b*x]^m*Tanh[a+b*x]^(n-1)/(b*m) -
  Dist[(m+n-1)/m,Int[Sinh[a+b*x]^(m-2)*Tanh[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m>1 && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.411.3*)


Int[Sinh[a_.+b_.*x_]^m_.*Tanh[a_.+b_.*x_]^n_,x_Symbol] :=
  -Sinh[a+b*x]^m*Tanh[a+b*x]^(n-1)/(b*(n-1)) +
  Dist[(m+n-1)/(n-1),Int[Sinh[a+b*x]^m*Tanh[a+b*x]^(n-2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n>1 && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.411.5, CRC 568a*)


Int[Sinh[a_.+b_.*x_]^m_*Tanh[a_.+b_.*x_]^n_.,x_Symbol]:=
  Sinh[a+b*x]^(m+2)*Tanh[a+b*x]^(n-1)/(b*(m+n+1)) -
  Dist[(m+2)/(m+n+1),Int[Sinh[a+b*x]^(m+2)*Tanh[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m<-1 && NonzeroQ[m+n+1] && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.411.4*)


Int[Sinh[a_.+b_.*x_]^m_.*Tanh[a_.+b_.*x_]^n_,x_Symbol]:=
  Sinh[a+b*x]^m*Tanh[a+b*x]^(n+1)/(b*(m+n+1)) +
  Dist[(n+1)/(m+n+1),Int[Sinh[a+b*x]^m*Tanh[a+b*x]^(n+2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n<-1 && NonzeroQ[m+n+1] && Not[OddQ[m] && EvenQ[n]]


(* ::Subsubsection::Closed:: *)
(*Sinh[a+b x^n]				Sines of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: FresnelS'[z] == Sin[Pi*z^2/2]*)


(* Note: This rule introduces I;whereas,converting to exponentials does not. *)
(* Int[Sinh[b_.*x_^2],x_Symbol] :=
  -I*Sqrt[Pi/2]*FresnelS[Rt[I*b,2]*x/Sqrt[Pi/2]]/Rt[I*b,2] /;
FreeQ[b,x] *)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sinh[z] == E^z/2 - E^(-z)/2*)


Int[Sinh[a_.+b_.*x_^n_],x_Symbol] :=
  Dist[1/2,Int[E^(a+b*x^n),x]] - 
  Dist[1/2,Int[E^(-a-b*x^n),x]] /;
FreeQ[{a,b,n},x] && Not[FractionOrNegativeQ[n]]


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* Note: Although resulting integrand looks more complicated than original one, rules for 
	improper binomials rectify it. *)
Int[Sinh[a_.+b_.*x_^n_],x_Symbol] :=
  x*Sinh[a+b*x^n] - 
  Dist[b*n,Int[x^n*Cosh[a+b*x^n],x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && n<0


(* ::Subsubsection::Closed:: *)
(*x^m Sinh[a+b x^n]			Products of monomials and sines of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: SinhIntegral'[z] == Sinh[z]/z*)


Int[Sinh[a_.*x_^n_.]/x_,x_Symbol] :=
  SinhIntegral[a*x^n]/n /;
FreeQ[{a,n},x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sinh[w+z] == Sinh[w]*Cosh[z] + Cosh[w]*Sinh[z]*)


Int[Sinh[a_+b_.*x_^n_.]/x_,x_Symbol] :=
  Dist[Sinh[a],Int[Cosh[b*x^n]/x,x]] + 
  Dist[Cosh[a],Int[Sinh[b*x^n]/x,x]] /;
FreeQ[{a,b,n},x]


(* ::Item::Closed:: *)
(*Reference: CRC 392h, A&S 4.5.83*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Basis: x^m*Sinh[a+b*x^n] == x^(m-n+1)*(Sinh[a+b*x^n]*x^(n-1))*)


Int[x_^m_.*Sinh[a_.+b_.*x_^n_.],x_Symbol] :=
  x^(m-n+1)*Cosh[a+b*x^n]/(b*n) -
  Dist[(m-n+1)/(b*n),Int[x^(m-n)*Cosh[a+b*x^n],x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && RationalQ[m] && 0<n<=m


(* ::Item::Closed:: *)
(*Reference: CRC 405h*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Sinh[a_.+b_.*x_^n_.],x_Symbol] :=
  x^(m+1)*Sinh[a+b*x^n]/(m+1) -
  Dist[b*n/(m+1),Int[x^(m+n)*Cosh[a+b*x^n],x]] /;
FreeQ[{a,b,m,n},x] && (ZeroQ[m+n+1] || IntegerQ[n] && RationalQ[m] && (n>0 && m<-1 || 0<-n<m+1))


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sinh[z] == E^z/2 - E^(-z)/2*)


Int[x_^m_.*Sinh[a_.+b_.*x_^n_.],x_Symbol] :=
  Dist[1/2,Int[x^m*E^(a+b*x^n),x]] - 
  Dist[1/2,Int[x^m*E^(-a-b*x^n),x]] /;
FreeQ[{a,b,m,n},x] && NonzeroQ[m+1] && NonzeroQ[m-n+1] && Not[FractionQ[m] || FractionOrNegativeQ[n]]


(* ::Subsubsection::Closed:: *)
(*x^m Sinh[a+b x^n]^p			Products of monomials and powers of sines of binomials*)


(* ::Item:: *)
(*Reference: G&R 2.471.1b' w/ q=0 and r=1*)


Int[x_^m_.*Sinh[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  -n*Sinh[a+b*x^n]^p/(b^2*n^2*p^2) +
  x^n*Cosh[a+b*x^n]*Sinh[a+b*x^n]^(p-1)/(b*n*p) -
  Dist[(p-1)/p,Int[x^m*Sinh[a+b*x^n]^(p-2),x]] /;
FreeQ[{a,b,m,n},x] && RationalQ[p] && p>1 && ZeroQ[m-2*n+1]


(* ::Item:: *)
(*Reference: G&R 2.471.1b' w/ q=0*)


Int[x_^m_.*Sinh[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  -(m-n+1)*x^(m-2*n+1)*Sinh[a+b*x^n]^p/(b^2*n^2*p^2) +
  x^(m-n+1)*Cosh[a+b*x^n]*Sinh[a+b*x^n]^(p-1)/(b*n*p) -
  Dist[(p-1)/p,Int[x^m*Sinh[a+b*x^n]^(p-2),x]] +
  Dist[(m-n+1)*(m-2*n+1)/(b^2*n^2*p^2),Int[x^(m-2*n)*Sinh[a+b*x^n]^p,x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && RationalQ[{m,p}] && p>1 && 0<2*n<m+1


(* ::Item:: *)
(*Reference: G&R 2.477.1*)


Int[x_^m_.*Sinh[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  x^n*Cosh[a+b*x^n]*Sinh[a+b*x^n]^(p+1)/(b*n*(p+1)) - 
  n*Sinh[a+b*x^n]^(p+2)/(b^2*n^2*(p+1)*(p+2)) - 
  Dist[(p+2)/(p+1),Int[x^m*Sinh[a+b*x^n]^(p+2),x]] /;
FreeQ[{a,b,m,n},x] && RationalQ[p] && p<-1 && p!=-2 && ZeroQ[m-2*n+1]


(* ::Item:: *)
(*Reference: G&R 2.477.1*)


Int[x_^m_.*Sinh[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  x^(m-n+1)*Cosh[a+b*x^n]*Sinh[a+b*x^n]^(p+1)/(b*n*(p+1)) -
  (m-n+1)*x^(m-2*n+1)*Sinh[a+b*x^n]^(p+2)/(b^2*n^2*(p+1)*(p+2)) -
  Dist[(p+2)/(p+1),Int[x^m*Sinh[a+b*x^n]^(p+2),x]] +
  Dist[(m-n+1)*(m-2*n+1)/(b^2*n^2*(p+1)*(p+2)),Int[x^(m-2*n)*Sinh[a+b*x^n]^(p+2),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && RationalQ[{m,p}] && p<-1 && p!=-2 && 0<2*n<m+1


(* ::Item:: *)
(*Reference: G&R 2.475.1'*)


Int[x_^m_.*Sinh[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  x^(m+1)*Sinh[a+b*x^n]^p/(m+1) - 
  b*n*p*x^(m+n+1)*Cosh[a+b*x^n]*Sinh[a+b*x^n]^(p-1)/((m+1)*(m+n+1)) + 
  Dist[b^2*n^2*p^2/((m+1)*(m+n+1)),Int[x^(m+2*n)*Sinh[a+b*x^n]^p,x]] + 
  Dist[b^2*n^2*p*(p-1)/((m+1)*(m+n+1)),Int[x^(m+2*n)*Sinh[a+b*x^n]^(p-2),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && RationalQ[{m,p}] && p>1 && m<-1 && n>0 && NonzeroQ[m+n+1]


(* ::Subsubsection::Closed:: *)
(*x^m Sinh[a+b (c+d x)^n]^p		Products of monomials and powers of sines of binomials of linears*)
(**)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Int[f[a+b x,x],x] == Subst[Int[f[x,-a/b+x/b],x],x,a+b x]/b*)


Int[x_^m_.*Sinh[a_.+b_.*(c_+d_.*x_)^n_]^p_.,x_Symbol] :=
  Dist[1/d,Subst[Int[(-c/d+x/d)^m*Sinh[a+b*x^n]^p,x],x,c+d*x]] /;
FreeQ[{a,b,c,d,n},x] && IntegerQ[m] && m>0 && RationalQ[p]


(* ::Subsubsection::Closed:: *)
(*Sinh[a+b x+c x^2]			Sines of quadratic trinomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If b^2-4*a*c=0, a+b*x+c*x^2 == (b+2*c*x)^2/(4*c)*)


Int[Sinh[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  Int[Sinh[(b+2*c*x)^2/(4*c)],x] /;
FreeQ[{a,b,c},x] && ZeroQ[b^2-4*a*c]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sinh[z] == E^z/2 - E^(-z)/2*)


Int[Sinh[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  Dist[1/2,Int[E^(a+b*x+c*x^2),x]] - 
  Dist[1/2,Int[E^(-a-b*x-c*x^2),x]] /;
FreeQ[{a,b,c},x] && NonzeroQ[b^2-4*a*c]


(* ::Subsubsection::Closed:: *)
(*(d+e x)^m Sinh[a+b x+c x^2]		Products of monomials and sines of quadratic trinomials*)
(**)


Int[(d_.+e_.*x_)*Sinh[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  e*Cosh[a+b*x+c*x^2]/(2*c) /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)*Sinh[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  e*Cosh[a+b*x+c*x^2]/(2*c) -
  Dist[(b*e-2*c*d)/(2*c),Int[Sinh[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && NonzeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)^m_*Sinh[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  e*(d+e*x)^(m-1)*Cosh[a+b*x+c*x^2]/(2*c) -
  Dist[e^2*(m-1)/(2*c),Int[(d+e*x)^(m-2)*Cosh[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[m] && m>1 && ZeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)^m_*Sinh[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  e*(d+e*x)^(m-1)*Cosh[a+b*x+c*x^2]/(2*c) -
  Dist[(b*e-2*c*d)/(2*c),Int[(d+e*x)^(m-1)*Sinh[a+b*x+c*x^2],x]] -
  Dist[e^2*(m-1)/(2*c),Int[(d+e*x)^(m-2)*Cosh[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[m] && m>1 && NonzeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)^m_*Sinh[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  (d+e*x)^(m+1)*Sinh[a+b*x+c*x^2]/(e*(m+1)) -
  Dist[2*c/(e^2*(m+1)),Int[(d+e*x)^(m+2)*Cosh[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[m] && m<-1 && ZeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)^m_*Sinh[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  (d+e*x)^(m+1)*Sinh[a+b*x+c*x^2]/(e*(m+1)) -
  Dist[(b*e-2*c*d)/(e^2*(m+1)),Int[(d+e*x)^(m+1)*Cosh[a+b*x+c*x^2],x]] -
  Dist[2*c/(e^2*(m+1)),Int[(d+e*x)^(m+2)*Cosh[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[m] && m<-1 && NonzeroQ[b*e-2*c*d]


(* ::Subsubsection::Closed:: *)
(*Sinh[a+b Log[c x^n]]^p		Powers of sines of logarithms*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Sinh[b*Log[c*x^n]] == (c*x^n)^b/2 - 1/(2*(c*x^n)^b)*)


Int[Sinh[b_.*Log[c_.*x_^n_.]]^p_.,x_Symbol] :=
  Int[((c*x^n)^b/2 - 1/(2*(c*x^n)^b))^p,x] /;
FreeQ[c,x] && RationalQ[{b,n,p}]


Int[Sinh[a_.+b_.*Log[c_.*x_^n_.]],x_Symbol] :=
  x*Sinh[a+b*Log[c*x^n]]/(1-b^2*n^2) -
  b*n*x*Cosh[a+b*Log[c*x^n]]/(1-b^2*n^2) /;
FreeQ[{a,b,c,n},x] && NonzeroQ[1-b^2*n^2]


Int[Sqrt[Sinh[a_.+b_.*Log[c_.*x_^n_.]]],x_Symbol] :=
  x*Sqrt[Sinh[a+b*Log[c*x^n]]]/Sqrt[-1+E^(2*a)*(c*x^n)^(4/n)]*
    Int[Sqrt[-1+E^(2*a)*(c*x^n)^(4/n)]/x,x] /;
FreeQ[{a,b,c,n},x] && ZeroQ[b*n-2]


(* Int[1/Sqrt[Sinh[a_.+b_.*Log[c_.*x_^n_.]]],x_Symbol] :=
  ??? /;
FreeQ[{a,b,c,n},x] && ZeroQ[b*n-2] *)


Int[Sinh[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  x*Coth[a+b*Log[c*x^n]]*Sinh[a+b*Log[c*x^n]]^(p+2)/(b*n*(p+1)) -
  x*Sinh[a+b*Log[c*x^n]]^(p+2)/(b^2*n^2*(p+1)*(p+2)) /;
FreeQ[{a,b,c,n,p},x] && NonzeroQ[p+1] && NonzeroQ[p+2] && ZeroQ[b^2*n^2*(p+2)^2-1]


Int[Sinh[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  x*Sinh[a+b*Log[c*x^n]]^p/(1-b^2*n^2*p^2) -
  b*n*p*x*Cosh[a+b*Log[c*x^n]]*Sinh[a+b*Log[c*x^n]]^(p-1)/(1-b^2*n^2*p^2) +
  Dist[b^2*n^2*p*(p-1)/(1-b^2*n^2*p^2),Int[Sinh[a+b*Log[c*x^n]]^(p-2),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p>1 && NonzeroQ[1-b^2*n^2*p^2]


Int[Sinh[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  x*Coth[a+b*Log[c*x^n]]*Sinh[a+b*Log[c*x^n]]^(p+2)/(b*n*(p+1)) -
  x*Sinh[a+b*Log[c*x^n]]^(p+2)/(b^2*n^2*(p+1)*(p+2)) -
  Dist[(b^2*n^2*(p+2)^2-1)/(b^2*n^2*(p+1)*(p+2)),Int[Sinh[a+b*Log[c*x^n]]^(p+2),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p<-1 && p!=-2 && NonzeroQ[b^2*n^2*(p+2)^2-1]


(* ::Subsubsection::Closed:: *)
(*x^m Sinh[a+b Log[c x^n]]^p		Products of monomials and powers of sines of logarithms*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Sinh[b*Log[c*x^n]] == (c*x^n)^b/2 - 1/(2*(c*x^n)^b)*)


Int[x_^m_.*Sinh[b_.*Log[c_.*x_^n_.]]^p_.,x_Symbol] :=
  Int[x^m*((c*x^n)^b/2 - 1/(2*(c*x^n)^b))^p,x] /;
FreeQ[c,x] && RationalQ[{b,m,n,p}]


Int[x_^m_.*Sinh[a_.+b_.*Log[c_.*x_^n_.]],x_Symbol] :=
  (m+1)*x^(m+1)*Sinh[a+b*Log[c*x^n]]/((m+1)^2-b^2*n^2) -
  b*n*x^(m+1)*Cosh[a+b*Log[c*x^n]]/((m+1)^2-b^2*n^2) /;
FreeQ[{a,b,c,m,n},x] && NonzeroQ[(m+1)^2-b^2*n^2] && NonzeroQ[m+1]


Int[x_^m_.*Sinh[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  x^(m+1)*Coth[a+b*Log[c*x^n]]*Sinh[a+b*Log[c*x^n]]^(p+2)/(b*n*(p+1)) -
  (m+1)*x^(m+1)*Sinh[a+b*Log[c*x^n]]^(p+2)/(b^2*n^2*(p+1)*(p+2)) /;
FreeQ[{a,b,c,m,n,p},x] && NonzeroQ[p+1] && NonzeroQ[p+2] && ZeroQ[(m+1)^2-b^2*n^2*(p+2)^2]


Int[x_^m_.*Sinh[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  (m+1)*x^(m+1)*Sinh[a+b*Log[c*x^n]]^p/((m+1)^2-b^2*n^2*p^2) -
  b*n*p*x^(m+1)*Cosh[a+b*Log[c*x^n]]*Sinh[a+b*Log[c*x^n]]^(p-1)/((m+1)^2-b^2*n^2*p^2) +
  Dist[b^2*n^2*p*(p-1)/((m+1)^2-b^2*n^2*p^2),Int[x^m*Sinh[a+b*Log[c*x^n]]^(p-2),x]] /;
FreeQ[{a,b,c,m,n},x] && RationalQ[p] && p>1 && NonzeroQ[(m+1)^2-b^2*n^2*p^2] && NonzeroQ[m+1]


Int[x_^m_.*Sinh[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  x^(m+1)*Coth[a+b*Log[c*x^n]]*Sinh[a+b*Log[c*x^n]]^(p+2)/(b*n*(p+1)) -
  (m+1)*x^(m+1)*Sinh[a+b*Log[c*x^n]]^(p+2)/(b^2*n^2*(p+1)*(p+2)) +
  Dist[((m+1)^2-b^2*n^2*(p+2)^2)/(b^2*n^2*(p+1)*(p+2)),Int[x^m*Sinh[a+b*Log[c*x^n]]^(p+2),x]] /;
FreeQ[{a,b,c,m,n},x] && RationalQ[p] && p<-1 && p!=-2 && NonzeroQ[m+1] && NonzeroQ[(m+1)^2-b^2*n^2*(p+2)^2]


(* ::Subsubsection::Closed:: *)
(*x^m Sinh[a x^n Log[b x]^p Log[b x]^p  Products of sines and powers of logarithms*)
(**)


Int[Sinh[a_.*x_*Log[b_.*x_]^p_.]*Log[b_.*x_]^p_.,x_Symbol] :=
  Cosh[a*x*Log[b*x]^p]/a -
  Dist[p,Int[Sinh[a*x*Log[b*x]^p]*Log[b*x]^(p-1),x]] /;
FreeQ[{a,b},x] && RationalQ[p] && p>0


Int[Sinh[a_.*x_^n_*Log[b_.*x_]^p_.]*Log[b_.*x_]^p_.,x_Symbol] :=
  Cosh[a*x^n*Log[b*x]^p]/(a*n*x^(n-1)) -
  Dist[p/n,Int[Sinh[a*x^n*Log[b*x]^p]*Log[b*x]^(p-1),x]] +
  Dist[(n-1)/(a*n),Int[Cosh[a*x^n*Log[b*x]^p]/x^n,x]] /;
FreeQ[{a,b},x] && RationalQ[{n,p}] && p>0


Int[x_^m_*Sinh[a_.*x_^n_.*Log[b_.*x_]^p_.]*Log[b_.*x_]^p_.,x_Symbol] :=
  x^(m-n+1)*Cosh[a*x^n*Log[b*x]^p]/(a*n) -
  Dist[p/n,Int[x^m*Sinh[a*x^n*Log[b*x]^p]*Log[b*x]^(p-1),x]] -
  Dist[(m-n+1)/(a*n),Int[x^(m-n)*Cosh[a*x^n*Log[b*x]^p],x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n,p}] && p>0


(* ::Subsubsection::Closed:: *)
(*u Sinh[v]^2				Products involving squares of sines*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sinh[z]^2 == -1/2 + 1/2*Cosh[2*z]*)


Int[u_*Sinh[v_]^2,x_Symbol] :=
  Dist[-1/2,Int[u,x]] + 
  Dist[1/2,Int[u*Cosh[2*v],x]] /;
FunctionOfHyperbolicQ[u,2*v,x]


(* ::Subsubsection::Closed:: *)
(*u Sinh[v] Hyper[w]			Products of hyperbolic trig functions*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sinh[v]*Cosh[w] == Sinh[v+w]/2 + Sinh[v-w]/2*)


Int[u_.*Sinh[v_]*Cosh[w_],x_Symbol] :=
  Dist[1/2,Int[u*Regularize[Sinh[v+w],x],x]] + 
  Dist[1/2,Int[u*Regularize[Sinh[v-w],x],x]] /;
(PolynomialQ[v,x] && PolynomialQ[w,x] || IndependentQ[Cancel[v/w],x]) && PosQ[v-w]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sinh[v]*Sinh[w] == Cosh[v+w]/2 - Cosh[v-w]/2*)


Int[u_.*Sinh[v_]*Sinh[w_],x_Symbol] :=
  Dist[1/2,Int[u*Regularize[Cosh[v+w],x],x]] - 
  Dist[1/2,Int[u*Regularize[Cosh[v-w],x],x]] /;
(PolynomialQ[v,x] && PolynomialQ[w,x] || IndependentQ[Cancel[v/w],x]) && NonzeroQ[v-w]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sinh[v]*Tanh[w] == Cosh[v] - Cosh[v-w]*Sech[w]*)


Int[u_.*Sinh[v_]*Tanh[w_]^n_.,x_Symbol] :=
  Int[u*Cosh[v]*Tanh[w]^(n-1),x] - Cosh[v-w]*Int[u*Sech[w]*Tanh[w]^(n-1),x] /;
RationalQ[n] && n>0 && FreeQ[v-w,x] && NonzeroQ[v-w]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sinh[v]*Coth[w] == Cosh[v] + Sinh[v-w]*Csch[w]*)


Int[u_.*Sinh[v_]*Coth[w_]^n_.,x_Symbol] :=
  Int[u*Cosh[v]*Coth[w]^(n-1),x] + Sinh[v-w]*Int[u*Csch[w]*Coth[w]^(n-1),x] /;
RationalQ[n] && n>0 && FreeQ[v-w,x] && NonzeroQ[v-w]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sinh[v]*Sech[w] == Cosh[v-w]*Tanh[w] + Sinh[v-w]*)


Int[u_.*Sinh[v_]*Sech[w_]^n_.,x_Symbol] :=
  Cosh[v-w]*Int[u*Tanh[w]*Sech[w]^(n-1),x] + Sinh[v-w]*Int[u*Sech[w]^(n-1),x] /;
RationalQ[n] && n>0 && FreeQ[v-w,x] && NonzeroQ[v-w]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sinh[v]*Csch[w] == Sinh[v-w]*Coth[w] + Cosh[v-w]*)


Int[u_.*Sinh[v_]*Csch[w_]^n_.,x_Symbol] :=
  Sinh[v-w]*Int[u*Coth[w]*Csch[w]^(n-1),x] + Cosh[v-w]*Int[u*Csch[w]^(n-1),x] /;
RationalQ[n] && n>0 && FreeQ[v-w,x] && NonzeroQ[v-w]


(* ::Subsection::Closed:: *)
(*Hyperbolic Cosine Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*Cosh[a+b x]^n				Powers of cosines of linears*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.01.21, CRC 555, A&S 4.5.78*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: Sinh'[z] == Cosh[z]*)


Int[Cosh[a_.+b_.*x_],x_Symbol] :=
  Sinh[a+b*x]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Reference: G&R 2.414.9, CRC 572*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[Cosh[a_.+b_.*x_]^2,x_Symbol] :=
  x/2 + Cosh[a+b*x]*Sinh[a+b*x]/(2*b) /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is odd, Cosh[z]^n == (1+Sinh[z]^2)^((n-1)/2)*Sinh'[z]*)


Int[Cosh[a_.+b_.*x_]^n_,x_Symbol] :=
  Dist[1/b,Subst[Int[Regularize[(1+x^2)^((n-1)/2),x],x],x,Sinh[a+b*x]]] /;
FreeQ[{a,b},x] && OddQ[n] && n>1


(* ::Subsubsection::Closed:: *)
(*(a+b Cosh[c+d x])^n			Powers of linear binomials of cosines of linears*)


(* ::Item:: *)
(*Reference: G&R 2.446.2'*)


Int[1/(a_+b_.*Cosh[c_.+d_.*x_]),x_Symbol] :=
  Sinh[c+d*x]/(d*(b+a*Cosh[c+d*x])) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2-b^2]


(* ::Item:: *)
(*Reference: G&R 2.443.3c'*)


Int[1/(a_+b_.*Cosh[c_.+d_.*x_]),x_Symbol] :=
  2*ArcTanh[((a-b)*Tanh[(c+d*x)/2])/Rt[a^2-b^2,2]]/(d*Rt[a^2-b^2,2]) /;
FreeQ[{a,b,c,d},x] && PosQ[a^2-b^2]


(* ::Item:: *)
(*Reference: G&R 2.443.3a'*)


Int[1/(a_+b_.*Cosh[c_.+d_.*x_]),x_Symbol] :=
  -2*ArcTan[((a-b)*Tanh[(c+d*x)/2])/Rt[b^2-a^2,2]]/(d*Rt[b^2-a^2,2]) /;
FreeQ[{a,b,c,d},x] && NegQ[a^2-b^2]


(* ::ItemParagraph:: *)
(**)


Int[Sqrt[a_+b_.*Cosh[c_.+d_.*x_]],x_Symbol] :=
  2*b*Sinh[c+d*x]/(d*Sqrt[a+b*Cosh[c+d*x]]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2-b^2]


(* ::Item:: *)
(*Basis: D[EllipticE[x,n],x] == Sqrt[1-n*Sin[x]^2]*)


Int[Sqrt[a_.+b_.*Cosh[c_.+d_.*x_]],x_Symbol] :=
  -2*I*Sqrt[a+b]*EllipticE[I*(c+d*x)/2,2*b/(a+b)]/d /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2] && PositiveQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Extract constant factor*)


(* ::Item:: *)
(*Basis: D[Sqrt[a+b*f[c+d*x]]/Sqrt[(a+b*f[c+d*x])/(a+b)],x] == 0*)


Int[Sqrt[a_.+b_.*Cosh[c_.+d_.*x_]],x_Symbol] :=
  Sqrt[a+b*Cosh[c+d*x]]/Sqrt[(a+b*Cosh[c+d*x])/(a+b)]*Int[Sqrt[a/(a+b)+b/(a+b)*Cosh[c+d*x]],x] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2] && Not[PositiveQ[a+b]]


(* ::ItemParagraph:: *)
(**)


Int[1/Sqrt[a_+b_.*Cosh[c_.+d_.*x_]],x_Symbol] :=
  2*ArcTan[Sinh[(c+d*x)/2]]*Cosh[(c+d*x)/2]/(d*Sqrt[a+b*Cosh[c+d*x]]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a-b]


Int[1/Sqrt[a_+b_.*Cosh[c_.+d_.*x_]],x_Symbol] :=
  -2*ArcTanh[Cosh[(c+d*x)/2]]*Sinh[(c+d*x)/2]/(d*Sqrt[a+b*Cosh[c+d*x]]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a+b]


(* ::Item:: *)
(*Basis: D[EllipticF[x,n],x] == 1/Sqrt[1-n*Sin[x]^2]*)


Int[1/Sqrt[a_.+b_.*Cosh[c_.+d_.*x_]],x_Symbol] :=
  -2*I*EllipticF[I*(c+d*x)/2,2*b/(a+b)]/(d*Sqrt[a+b]) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2] && PositiveQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Extract constant factor*)


(* ::Item:: *)
(*Basis: D[Sqrt[(a+b*f[c+d*x])/(a+b)]/Sqrt[a+b*f[c+d*x]],x] == 0*)


Int[1/Sqrt[a_+b_.*Cosh[c_.+d_.*x_]],x_Symbol] :=
  Sqrt[(a+b*Cosh[c+d*x])/(a+b)]/Sqrt[a+b*Cosh[c+d*x]]*Int[1/Sqrt[a/(a+b)+b/(a+b)*Cosh[c+d*x]],x] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2] && Not[PositiveQ[a+b]]


(* ::ItemParagraph:: *)
(**)


Int[(a_+b_.*Cosh[c_.+d_.*x_])^n_,x_Symbol] :=
  b*Sinh[c+d*x]*(a+b*Cosh[c+d*x])^(n-1)/(d*n) +
  Dist[a*(2*n-1)/n,Int[(a+b*Cosh[c+d*x])^(n-1),x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n>1 && ZeroQ[a^2-b^2]


(* ::Item:: *)
(*Reference: G&R 2.446.1'*)


Int[(a_+b_.*Cosh[c_.+d_.*x_])^n_,x_Symbol] :=
  -b*Sinh[c+d*x]*(a+b*Cosh[c+d*x])^n/(a*d*(2*n+1)) +
  Dist[(n+1)/(a*(2*n+1)),Int[(a+b*Cosh[c+d*x])^(n+1),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n<-1 && ZeroQ[a^2-b^2]


(* ::Item:: *)
(*Reference: G&R 2.443.1 inverted*)


(* Note: This results in an infinite loop!!! *)
(* Int[(a_+b_.*Cosh[c_.+d_.*x_])^n_,x_Symbol] :=
  -b*Sinh[c+d*x]*(a+b*Cosh[c+d*x])^n/(a*d*n) +
  Dist[(a^2-b^2)/a,Int[(a+b*Cosh[c+d*x])^(n-1),x]] +
  Dist[b*(n+1)/(a*n),Int[Cosh[c+d*x]*(a+b*Cosh[c+d*x])^n,x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n>0 && NonzeroQ[a^2-b^2] *)


(* ::Item:: *)
(*Reference: G&R 2.443.1*)


Int[1/(a_+b_.*Cosh[c_.+d_.*x_])^2,x_Symbol] :=
  -b*Sinh[c+d*x]/(d*(a^2-b^2)*(a+b*Cosh[c+d*x])) + 
  Dist[a/(a^2-b^2),Int[1/(a+b*Cosh[c+d*x]),x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2]


(* ::Item:: *)
(*Reference: G&R 2.443.1*)


Int[(a_+b_.*Cosh[c_.+d_.*x_])^n_,x_Symbol] :=
  b*Sinh[c+d*x]*(a+b*Cosh[c+d*x])^(n+1)/(d*(n+1)*(a^2-b^2)) + 
  Dist[1/((n+1)*(a^2-b^2)),Int[(a*(n+1)-b*(n+2)*Cosh[c+d*x])*(a+b*Cosh[c+d*x])^(n+1),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n<-1 && NonzeroQ[a^2-b^2]


(* ::ItemParagraph::Closed:: *)
(**)


(* ::Item:: *)
(*Reference: G&R 2.411.1, CRC 305h*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[(c_.*Cosh[a_.+b_.*x_])^n_,x_Symbol] :=
  c*Sinh[a+b*x]*(c*Cosh[a+b*x])^(n-1)/(b*n) + 
  Dist[(n-1)*c^2/n,Int[(c*Cosh[a+b*x])^(n-2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n>1 && Not[OddQ[n]]


(* ::Item::Closed:: *)
(*Reference: G&R 2.411.6, CRC 568b*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[(c_.*Cosh[a_.+b_.*x_])^n_,x_Symbol] :=
  -Sinh[a+b*x]*(c*Cosh[a+b*x])^(n+1)/(b*c*(n+1)) + 
  Dist[(n+2)/((n+1)*c^2),Int[(c*Cosh[a+b*x])^(n+2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n<-1


(* ::Item::Closed:: *)
(*Derivation: Extract constant factor*)


(* ::Item:: *)
(*Basis: D[(c*Cosh[x])^n/Cosh[x]^n,x] == 0*)


Int[(c_*Cosh[a_.+b_.*x_])^n_,x_Symbol] :=
  (c*Cosh[a+b*x])^n/Cosh[a+b*x]^n*Int[Cosh[a+b*x]^n,x] /;
FreeQ[{a,b,c},x] && RationalQ[n] && -1<n<1


(* ::Subsubsection::Closed:: *)
(*x^m (a+b Cosh[c+d x])^n		Products of monomials and powers of linear binomials of cosines of linears*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1+Cosh[z] == 2*Cosh[z/2]^2*)


Int[x_^m_.*(a_+b_.*Cosh[c_.+d_.*x_])^n_,x_Symbol] :=
  Dist[(2*a)^n,Int[x^m*Cosh[c/2+d*x/2]^(2*n),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[m] && IntegerQ[n] && n<0 && ZeroQ[a-b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1+Cosh[z] == 2*Cosh[z/2]^2*)


Int[x_^m_.*(a_+b_.*Cosh[c_.+d_.*x_])^n_,x_Symbol] :=
  Dist[2^n,Int[x^m*(a*Cosh[c/2+d*x/2]^2)^n,x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[{m,n}] && ZeroQ[a-b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1-Cosh[z] == -2*Sinh[z/2]^2*)


Int[x_^m_.*(a_+b_.*Cosh[c_.+d_.*x_])^n_,x_Symbol] :=
  Dist[(-2*a)^n,Int[x^m*Sinh[c/2+d*x/2]^(2*n),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[m] && IntegerQ[n] && n<0 && ZeroQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1-Cosh[z] == -2*Sinh[z/2]^2*)


Int[x_^m_.*(a_+b_.*Cosh[c_.+d_.*x_])^n_,x_Symbol] :=
  Dist[2^n,Int[x^m*(-a*Sinh[c/2+d*x/2]^2)^n,x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[{m,n}] && ZeroQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: 1/(a+b z)^2 == a/((a^2-b^2) (a+b z)) - b (b+a z)/((a^2-b^2) (a+b z)^2)*)


Int[x_/(a_+b_.*Cosh[c_.+d_.*x_])^2,x_Symbol] :=
  Dist[a/(a^2-b^2),Int[x/(a+b*Cosh[c+d*x]),x]] -
  Dist[b/(a^2-b^2),Int[x*(b+a*Cosh[c+d*x])/(a+b*Cosh[c+d*x])^2,x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: a+b*Cosh[z] == (b+2*a*E^z+b*E^(2*z))/(2*E^z)*)


Int[x_^m_.*(a_+b_.*Cosh[c_.+d_.*x_])^n_,x_Symbol] :=
  Dist[1/2^n,Int[x^m*(b+2*a*E^(c+d*x)+b*E^(2*(c+d*x)))^n/E^(n*(c+d*x)),x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2] && IntegerQ[n] && n<0 && RationalQ[m] && m>0


(* ::Subsubsection::Closed:: *)
(*(A+B Cosh[c+d x]) (a+b Cosh[c+d x])^n  Products of powers of linear binomials of cosines*)


(* ::Item:: *)
(*Basis: (A+B*z)/Sqrt[a+b*z] == (b*A-a*B)/(b*Sqrt[a+b*z]) + B/b*Sqrt[a+b*z]*)


Int[(A_.+B_.*Cosh[c_.+d_.*x_])/Sqrt[a_+b_.*Cosh[c_.+d_.*x_]],x_Symbol] :=
  Dist[(b*A-a*B)/b,Int[1/Sqrt[a+b*Cosh[c+d*x]],x]] +
  Dist[B/b,Int[Sqrt[a+b*Cosh[c+d*x]],x]] /;
FreeQ[{a,b,c,d,A,B},x] && NonzeroQ[b*A-a*B]


(* ::Item:: *)
(*Reference: G&R 2.443.1 inverted*)


Int[(A_.+B_.*Cosh[c_.+d_.*x_])*(a_+b_.*Cosh[c_.+d_.*x_])^n_,x_Symbol] :=
  B*Sinh[c+d*x]*(a+b*Cosh[c+d*x])^n/(d*(n+1)) + 
  Dist[1/(n+1),Int[(b*B*n+a*A*(n+1) + (a*B*n+b*A*(n+1))*Cosh[c+d*x])*(a+b*Cosh[c+d*x])^(n-1),x]] /;
FreeQ[{a,b,c,d,A,B},x] && RationalQ[n] && n>1 && NonzeroQ[a^2-b^2]


(* ::Item:: *)
(*Reference: G&R 2.443.1 special case*)


Int[(A_+B_.*Cosh[c_.+d_.*x_])/(a_+b_.*Cosh[c_.+d_.*x_])^2,x_Symbol] :=
  B*Sinh[c+d*x]/(a*d*(a+b*Cosh[c+d*x])) /;
FreeQ[{a,b,c,d,A,B},x] && ZeroQ[a*A-b*B]


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_*(A_+B_.*Cosh[c_.+d_.*x_])/(a_+b_.*Cosh[c_.+d_.*x_])^2,x_Symbol] :=
  B*x*Sinh[c+d*x]/(a*d*(a+b*Cosh[c+d*x])) -
  Dist[B/(a*d),Int[Sinh[c+d*x]/(a+b*Cosh[c+d*x]),x]] /;
FreeQ[{a,b,c,d,A,B},x] && ZeroQ[a*A-b*B]


(* ::Item:: *)
(*Reference: G&R 2.443.1*)


Int[(A_.+B_.*Cosh[c_.+d_.*x_])*(a_.+b_.*Cosh[c_.+d_.*x_])^n_,x_Symbol] :=
  -(a*B-b*A)*Sinh[c+d*x]*(a+b*Cosh[c+d*x])^(n+1)/(d*(n+1)*(a^2-b^2)) +
  Dist[1/((n+1)*(a^2-b^2)),Int[((n+1)*(a*A-b*B)+(n+2)*(a*B-b*A)*Cosh[c+d*x])*(a+b*Cosh[c+d*x])^(n+1),x]] /;
FreeQ[{a,b,c,d,A,B},x] && RationalQ[n] && n<-1 && NonzeroQ[a^2-b^2]


(* ::Subsubsection::Closed:: *)
(*x^m (a+b Cosh[c+d x]^2)^n		Products of monomials and powers of quadratic binomials of sines of linears*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Cosh[z]^2 == (1 + Cosh[2*z])/2*)


Int[x_^m_./(a_+b_.*Cosh[c_.+d_.*x_]^2),x_Symbol] :=
  Dist[2,Int[x^m/(2*a+b+b*Cosh[2*c+2*d*x]),x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a+b] && IntegerQ[m] && m>0


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Cosh[z]^2 == (1 + Cosh[2*z])/2*)


Int[(a_+b_.*Cosh[c_.+d_.*x_]^2)^n_,x_Symbol] :=
  Dist[1/2^n,Int[(2*a+b+b*Cosh[2*c+2*d*x])^n,x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a+b] && HalfIntegerQ[n]


(* ::Subsubsection::Closed:: *)
(*Cosh[a+b x]^n Sinh[a+b x]^m 	Products of powers of cosines and sines*)


Int[Sinh[a_.+b_.*x_]^m_*Cosh[a_.+b_.*x_]^n_.,x_Symbol] :=
  -Sinh[a+b*x]^(m+1)*Cosh[a+b*x]^(n+1)/(b*(n+1)) /;
FreeQ[{a,b,m,n},x] && ZeroQ[m+n+2] && NonzeroQ[n+1] && PosQ[n]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If m is odd, Sinh[z]^m == (-1+Cosh[z]^2)^((m-1)/2)*Cosh'[z]*)


Int[Sinh[a_.+b_.*x_]^m_*Cosh[a_.+b_.*x_]^n_,x_Symbol] :=
  Dist[1/b,Subst[Int[Regularize[x^n*(-1+x^2)^((m-1)/2),x],x],x,Cosh[a+b*x]]] /;
FreeQ[{a,b,n},x] && OddQ[m] && Not[OddQ[n] && 0<n<=m]


(* ::Item:: *)
(*Reference: G&R 2.411.4*)


Int[Sinh[a_.+b_.*x_]^m_*Cosh[a_.+b_.*x_]^n_,x_Symbol] :=
  Sinh[a+b*x]^(m+1)*Cosh[a+b*x]^(n-1)/(b*(m+1)) -
  Dist[(n-1)/(m+1),Int[Sinh[a+b*x]^(m+2)*Cosh[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m<-1 && n>1


(* ::Item:: *)
(*Reference: G&R 2.411.1, CRC 567a, A&S 4.5.85a*)


Int[Sinh[a_.+b_.*x_]^m_*Cosh[a_.+b_.*x_]^n_,x_Symbol] :=
  Sinh[a+b*x]^(m+1)*Cosh[a+b*x]^(n-1)/(b*(m+n)) +
  Dist[(n-1)/(m+n),Int[Sinh[a+b*x]^m*Cosh[a+b*x]^(n-2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n>1 && Not[OddQ[n]] && NonzeroQ[m+n] &&
Not[OddQ[m] && m>1]


(* ::Item:: *)
(*Reference: G&R 2.411.6, CRC 568b, A&S 4.5.86b*)


Int[Sinh[a_.+b_.*x_]^m_*Cosh[a_.+b_.*x_]^n_,x_Symbol] :=
  -Sinh[a+b*x]^(m+1)*Cosh[a+b*x]^(n+1)/(b*(n+1)) +
  Dist[(m+n+2)/(n+1),Int[Sinh[a+b*x]^m*Cosh[a+b*x]^(n+2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n<-1 && NonzeroQ[m+n+2]


(* Kool rule *)
Int[Sinh[a_.+b_.*x_]^m_*Cosh[a_.+b_.*x_]^n_,x_Symbol] :=
  Dist[1/(b*n),Subst[Int[x^(1/n)/(1-x^(2/n)),x],x,Sinh[a+b*x]^m*Cosh[a+b*x]^n]] /;
FreeQ[{a,b},x] && FractionQ[{m,n}] && ZeroQ[m+n] && IntegerQ[1/n] && n>0


(* ::Subsubsection::Closed:: *)
(*Cosh[a+b x]^m Coth[a+b x]^n		Products of powers of cosines and cotangents*)
(**)


(* ::Item::Closed:: *)
(*Reference: G&R 2.423.34'*)


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cosh[z]*Coth[z] == Sinh[z]+Csch[z]*)


Int[Cosh[a_.+b_.*x_]*Coth[a_.+b_.*x_],x_Symbol] :=
  Cosh[a+b*x]/b + Int[Csch[a+b*x],x] /;
FreeQ[{a,b},x]


Int[Cosh[a_.+b_.*x_]^m_*Coth[a_.+b_.*x_]^n_,x_Symbol] :=
  Cosh[a+b*x]^m*Coth[a+b*x]^(n-1)/(b*m) /;
FreeQ[{a,b,m,n},x] && ZeroQ[m+n-1]


(* ::Item:: *)
(*Derivation: Integration by substitution*)


Int[Cosh[a_.+b_.*x_]^m_.*Coth[a_.+b_.*x_]^n_.,x_Symbol] :=
  Dist[1/b,Subst[Int[Regularize[(1+x^2)^((m+n-1)/2)/x^n,x],x],x,Sinh[a+b*x]]] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && OddQ[m+n]


(* ::Item:: *)
(*Reference: G&R 2.411.2, CRC 567b*)


Int[Cosh[a_.+b_.*x_]^m_.*Coth[a_.+b_.*x_]^n_.,x_Symbol] :=
  Cosh[a+b*x]^m*Coth[a+b*x]^(n+1)/(b*m) +
  Dist[(n+1)/m,Int[Cosh[a+b*x]^(m-2)*Coth[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m>1 && n<-1 && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.411.5, CRC 568a*)


Int[Cosh[a_.+b_.*x_]^m_.*Coth[a_.+b_.*x_]^n_.,x_Symbol] :=
  -Cosh[a+b*x]^(m+2)*Coth[a+b*x]^(n-1)/(b*(n-1)) +
  Dist[(m+2)/(n-1),Int[Cosh[a+b*x]^(m+2)*Coth[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m<-1 && n>1 && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.411.1, CRC 567a*)


Int[Cosh[a_.+b_.*x_]^m_*Coth[a_.+b_.*x_]^n_.,x_Symbol] :=
  Cosh[a+b*x]^m*Coth[a+b*x]^(n-1)/(b*m) +
  Dist[(m+n-1)/m,Int[Cosh[a+b*x]^(m-2)*Coth[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m>1 && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.411.4*)


Int[Cosh[a_.+b_.*x_]^m_.*Coth[a_.+b_.*x_]^n_,x_Symbol] :=
  -Cosh[a+b*x]^m*Coth[a+b*x]^(n-1)/(b*(n-1)) +
  Dist[(m+n-1)/(n-1),Int[Cosh[a+b*x]^m*Coth[a+b*x]^(n-2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n>1 && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.411.6, CRC 568b*)


Int[Cosh[a_.+b_.*x_]^m_.*Coth[a_.+b_.*x_]^n_.,x_Symbol] :=
  -Cosh[a+b*x]^(m+2)*Coth[a+b*x]^(n-1)/(b*(m+n+1)) +
  Dist[(m+2)/(m+n+1),Int[Cosh[a+b*x]^(m+2)*Coth[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m<-1 && NonzeroQ[m+n+1] && Not[OddQ[m] && EvenQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.411.3*)


Int[Cosh[a_.+b_.*x_]^m_.*Coth[a_.+b_.*x_]^n_.,x_Symbol] :=
  Cosh[a+b*x]^m*Coth[a+b*x]^(n+1)/(b*(m+n+1)) +
  Dist[(n+1)/(m+n+1),Int[Cosh[a+b*x]^m*Coth[a+b*x]^(n+2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n<-1 && NonzeroQ[m+n+1] && Not[OddQ[m] && EvenQ[n]]


(* ::Subsubsection::Closed:: *)
(*Cosh[a+b x^n]				Cosines of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: FresnelC'[z] == Cos[Pi*z^2/2]*)


(* Note: This rule introduces I;whereas,converting to exponentials does not. *)
(* Int[Cosh[b_.*x_^2],x_Symbol] :=
  Sqrt[Pi/2]*FresnelC[Rt[I*b,2]*x/Sqrt[Pi/2]]/Rt[I*b,2] /;
FreeQ[b,x] *)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cosh[z] == E^(-z)/2 + E^z/2*)


Int[Cosh[a_.+b_.*x_^n_],x_Symbol] :=
  Dist[1/2,Int[E^(-a-b*x^n),x]] + 
  Dist[1/2,Int[E^(a+b*x^n),x]] /;
FreeQ[{a,b,n},x] && Not[FractionOrNegativeQ[n]]


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* Note: Although resulting integrand looks more complicated than original one, rules for 
	improper binomials rectify it. *)
Int[Cosh[a_.+b_.*x_^n_],x_Symbol] :=
  x*Cosh[a+b*x^n] - 
  Dist[b*n,Int[x^n*Sinh[a+b*x^n],x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && n<0


(* ::Subsubsection::Closed:: *)
(*x^m Cosh[a+b x^n]			Products of monomials and cosines of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: CoshIntegral'[z] == Cosh[z]/z*)


Int[Cosh[a_.*x_^n_.]/x_,x_Symbol] :=
  CoshIntegral[a*x^n]/n /;
FreeQ[{a,n},x]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cosh[w+z] == Cosh[w]*Cosh[z] + Sinh[w]*Sinh[z]*)


Int[Cosh[a_+b_.*x_^n_.]/x_,x_Symbol] :=
  Dist[Cosh[a],Int[Cosh[b*x^n]/x,x]] + 
  Dist[Sinh[a],Int[Sinh[b*x^n]/x,x]] /;
FreeQ[{a,b,n},x]


(* ::Item::Closed:: *)
(*Reference: CRC 396h, A&S 4.5.84*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


(* ::Item:: *)
(*Basis: x^m*Cosh[a+b*x^n] == x^(m-n+1)*(Cosh[a+b*x^n]*x^(n-1))*)


Int[x_^m_.*Cosh[a_.+b_.*x_^n_.],x_Symbol] :=
  x^(m-n+1)*Sinh[a+b*x^n]/(b*n) -
  Dist[(m-n+1)/(b*n),Int[x^(m-n)*Sinh[a+b*x^n],x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && RationalQ[m] && 0<n<=m


(* ::Item::Closed:: *)
(*Reference: CRC 406h*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Cosh[a_.+b_.*x_^n_.],x_Symbol] :=
  x^(m+1)*Cosh[a+b*x^n]/(m+1) -
  Dist[b*n/(m+1),Int[x^(m+n)*Sinh[a+b*x^n],x]] /;
FreeQ[{a,b,m,n},x] && (ZeroQ[m+n+1] || IntegerQ[n] && RationalQ[m] && (n>0 && m<-1 || 0<-n<m+1))


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cosh[z] == E^(-z)/2 + E^z/2*)


Int[x_^m_.*Cosh[a_.+b_.*x_^n_.],x_Symbol] :=
  Dist[1/2,Int[x^m*E^(-a-b*x^n),x]] + 
  Dist[1/2,Int[x^m*E^(a+b*x^n),x]] /;
FreeQ[{a,b,m,n},x] && NonzeroQ[m+1] && NonzeroQ[m-n+1] && Not[FractionQ[m] || FractionOrNegativeQ[n]]


(* ::Subsubsection::Closed:: *)
(*x^m Cosh[a+b x^n]^p			Products of monomials and powers of cosines of binomials*)


(* ::Item:: *)
(*Reference: G&R 2.471.1a' w/ p=0 and r=1*)


Int[x_^m_.*Cosh[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  -n*Cosh[a+b*x^n]^p/(b^2*n^2*p^2) +
  x^n*Sinh[a+b*x^n]*Cosh[a+b*x^n]^(p-1)/(b*n*p) +
  Dist[(p-1)/p,Int[x^m*Cosh[a+b*x^n]^(p-2),x]] /;
FreeQ[{a,b,m,n},x] && RationalQ[p] && p>1 && ZeroQ[m-2*n+1]


(* ::Item:: *)
(*Reference: G&R 2.471.1a' w/ p=0*)


Int[x_^m_.*Cosh[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  -(m-n+1)*x^(m-2*n+1)*Cosh[a+b*x^n]^p/(b^2*n^2*p^2) +
  x^(m-n+1)*Sinh[a+b*x^n]*Cosh[a+b*x^n]^(p-1)/(b*n*p) +
  Dist[(p-1)/p,Int[x^m*Cosh[a+b*x^n]^(p-2),x]] +
  Dist[(m-n+1)*(m-2*n+1)/(b^2*n^2*p^2),Int[x^(m-2*n)*Cosh[a+b*x^n]^p,x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && RationalQ[{m,p}] && p>1 && 0<2*n<m+1


(* ::Item:: *)
(*Reference: G&R 2.477.2*)


Int[x_^m_.*Cosh[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  -x^n*Sinh[a+b*x^n]*Cosh[a+b*x^n]^(p+1)/(b*n*(p+1)) + 
  n*Cosh[a+b*x^n]^(p+2)/(b^2*n^2*(p+1)*(p+2)) + 
  Dist[(p+2)/(p+1),Int[x^m*Cosh[a+b*x^n]^(p+2),x]] /;
FreeQ[{a,b,m,n},x] && RationalQ[p] && p<-1 && p!=-2 && ZeroQ[m-2*n+1]


(* ::Item:: *)
(*Reference: G&R 2.477.2*)


Int[x_^m_.*Cosh[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  -x^(m-n+1)*Sinh[a+b*x^n]*Cosh[a+b*x^n]^(p+1)/(b*n*(p+1)) +
  (m-n+1)*x^(m-2*n+1)*Cosh[a+b*x^n]^(p+2)/(b^2*n^2*(p+1)*(p+2)) +
  Dist[(p+2)/(p+1),Int[x^m*Cosh[a+b*x^n]^(p+2),x]] -
  Dist[(m-n+1)*(m-2*n+1)/(b^2*n^2*(p+1)*(p+2)),Int[x^(m-2*n)*Cosh[a+b*x^n]^(p+2),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && RationalQ[{m,p}] && p<-1 && p!=-2 && 0<2*n<m+1


(* ::Item:: *)
(*Reference: G&R 2.475.2'*)


Int[x_^m_.*Cosh[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  x^(m+1)*Cosh[a+b*x^n]^p/(m+1) - 
  b*n*p*x^(m+n+1)*Sinh[a+b*x^n]*Cosh[a+b*x^n]^(p-1)/((m+1)*(m+n+1)) + 
  Dist[b^2*n^2*p^2/((m+1)*(m+n+1)),Int[x^(m+2*n)*Cosh[a+b*x^n]^p,x]] - 
  Dist[b^2*n^2*p*(p-1)/((m+1)*(m+n+1)),Int[x^(m+2*n)*Cosh[a+b*x^n]^(p-2),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && RationalQ[{m,p}] && p>1 && m<-1 && n>0 && NonzeroQ[m+n+1]


(* ::Subsubsection::Closed:: *)
(*x^m Cosh[a+b (c+d x)^n]^p		Products of monomials and powers of cosines of binomials of linears*)
(**)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Int[f[a+b x,x],x] == Subst[Int[f[x,-a/b+x/b],x],x,a+b x]/b*)


Int[x_^m_.*Cosh[a_.+b_.*(c_+d_.*x_)^n_]^p_.,x_Symbol] :=
  Dist[1/d,Subst[Int[(-c/d+x/d)^m*Cosh[a+b*x^n]^p,x],x,c+d*x]] /;
FreeQ[{a,b,c,d,n},x] && IntegerQ[m] && m>0 && RationalQ[p]


(* ::Subsubsection::Closed:: *)
(*Cosh[a+b x+c x^2]			Cosines of quadratic trinomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: If b^2-4*a*c=0, a+b*x+c*x^2 == (b+2*c*x)^2/(4*c)*)


Int[Cosh[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  Int[Cosh[(b+2*c*x)^2/(4*c)],x] /;
FreeQ[{a,b,c},x] && ZeroQ[b^2-4*a*c]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cosh[z] == E^(-z)/2 + E^z/2*)


Int[Cosh[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  Dist[1/2,Int[E^(-a-b*x-c*x^2),x]] + 
  Dist[1/2,Int[E^(a+b*x+c*x^2),x]] /;
FreeQ[{a,b,c},x] && NonzeroQ[b^2-4*a*c]


(* ::Subsubsection::Closed:: *)
(*(d+e x)^m Cosh[a+b x+c x^2]		Products of monomials and cosines of quadratic trinomials*)
(**)


Int[(d_.+e_.*x_)*Cosh[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  e*Sinh[a+b*x+c*x^2]/(2*c) /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)*Cosh[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  e*Sinh[a+b*x+c*x^2]/(2*c) - 
  Dist[(b*e-2*c*d)/(2*c),Int[Cosh[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && NonzeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)^m_*Cosh[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  e*(d+e*x)^(m-1)*Sinh[a+b*x+c*x^2]/(2*c) - 
  Dist[e^2*(m-1)/(2*c),Int[(d+e*x)^(m-2)*Sinh[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[m] && m>1 && ZeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)^m_*Cosh[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  e*(d+e*x)^(m-1)*Sinh[a+b*x+c*x^2]/(2*c) - 
  Dist[(b*e-2*c*d)/(2*c),Int[(d+e*x)^(m-1)*Cosh[a+b*x+c*x^2],x]] - 
  Dist[e^2*(m-1)/(2*c),Int[(d+e*x)^(m-2)*Sinh[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[m] && m>1 && NonzeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)^m_*Cosh[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  (d+e*x)^(m+1)*Cosh[a+b*x+c*x^2]/(e*(m+1)) - 
  Dist[2*c/(e^2*(m+1)),Int[(d+e*x)^(m+2)*Sinh[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[m] && m<-1 && ZeroQ[b*e-2*c*d]


Int[(d_.+e_.*x_)^m_*Cosh[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  (d+e*x)^(m+1)*Cosh[a+b*x+c*x^2]/(e*(m+1)) - 
  Dist[(b*e-2*c*d)/(e^2*(m+1)),Int[(d+e*x)^(m+1)*Sinh[a+b*x+c*x^2],x]] -
  Dist[2*c/(e^2*(m+1)),Int[(d+e*x)^(m+2)*Sinh[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[m] && m<-1 && NonzeroQ[b*e-2*c*d]


(* ::Subsubsection::Closed:: *)
(*Cosh[a+b Log[c x^n]]^p		Powers of cosines of logarithms*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Cosh[b*Log[c*x^n]] == (c*x^n)^b/2 + 1/(2*(c*x^n)^b)*)


Int[Cosh[b_.*Log[c_.*x_^n_.]]^p_.,x_Symbol] :=
  Int[((c*x^n)^b/2 + 1/(2*(c*x^n)^b))^p,x] /;
FreeQ[c,x] && RationalQ[{b,n,p}]


Int[Cosh[a_.+b_.*Log[c_.*x_^n_.]],x_Symbol] :=
  x*Cosh[a+b*Log[c*x^n]]/(1-b^2*n^2) -
  b*n*x*Sinh[a+b*Log[c*x^n]]/(1-b^2*n^2) /;
FreeQ[{a,b,c,n},x] && NonzeroQ[1-b^2*n^2]


Int[Sqrt[Cosh[a_.+b_.*Log[c_.*x_^n_.]]],x_Symbol] :=
  x*Sqrt[Cosh[a+b*Log[c*x^n]]]/Sqrt[1+E^(2*a)*(c*x^n)^(4/n)]*
    Int[Sqrt[1+E^(2*a)*(c*x^n)^(4/n)]/x,x] /;
FreeQ[{a,b,c,n},x] && ZeroQ[b*n-2]


Int[Cosh[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  -x*Tanh[a+b*Log[c*x^n]]*Cosh[a+b*Log[c*x^n]]^(p+2)/(b*n*(p+1)) +
  x*Cosh[a+b*Log[c*x^n]]^(p+2)/(b^2*n^2*(p+1)*(p+2)) /;
FreeQ[{a,b,c,n,p},x] && NonzeroQ[p+1] && NonzeroQ[p+2] && ZeroQ[b^2*n^2*(p+2)^2-1]


Int[Cosh[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  x*Cosh[a+b*Log[c*x^n]]^p/(1-b^2*n^2*p^2) -
  b*n*p*x*Cosh[a+b*Log[c*x^n]]^(p-1)*Sinh[a+b*Log[c*x^n]]/(1-b^2*n^2*p^2) -
  Dist[b^2*n^2*p*(p-1)/(1-b^2*n^2*p^2),Int[Cosh[a+b*Log[c*x^n]]^(p-2),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p>1 && NonzeroQ[1-b^2*n^2*p^2]


Int[Cosh[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  -x*Tanh[a+b*Log[c*x^n]]*Cosh[a+b*Log[c*x^n]]^(p+2)/(b*n*(p+1)) +
  x*Cosh[a+b*Log[c*x^n]]^(p+2)/(b^2*n^2*(p+1)*(p+2)) +
  Dist[(b^2*n^2*(p+2)^2-1)/(b^2*n^2*(p+1)*(p+2)),Int[Cosh[a+b*Log[c*x^n]]^(p+2),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p<-1 && p!=-2 && NonzeroQ[b^2*n^2*(p+2)^2-1]


(* ::Subsubsection::Closed:: *)
(*x^m Cosh[a+b Log[c x^n]]^p		Products of monomials and powers of cosines of logarithms*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Cosh[b*Log[c*x^n]] == (c*x^n)^b/2 + 1/(2*(c*x^n)^b)*)


Int[x_^m_.*Cosh[b_.*Log[c_.*x_^n_.]]^p_.,x_Symbol] :=
  Int[x^m*((c*x^n)^b/2 + 1/(2*(c*x^n)^b))^p,x] /;
FreeQ[c,x] && RationalQ[{b,m,n,p}]


Int[x_^m_.*Cosh[a_.+b_.*Log[c_.*x_^n_.]],x_Symbol] :=
  (m+1)*x^(m+1)*Cosh[a+b*Log[c*x^n]]/((m+1)^2-b^2*n^2) -
  b*n*x^(m+1)*Sinh[a+b*Log[c*x^n]]/((m+1)^2-b^2*n^2) /;
FreeQ[{a,b,c,m,n},x] && NonzeroQ[(m+1)^2-b^2*n^2] && NonzeroQ[m+1]


Int[x_^m_.*Cosh[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  -x^(m+1)*Tanh[a+b*Log[c*x^n]]*Cosh[a+b*Log[c*x^n]]^(p+2)/(b*n*(p+1)) +
  (m+1)*x^(m+1)*Cosh[a+b*Log[c*x^n]]^(p+2)/(b^2*n^2*(p+1)*(p+2)) /;
FreeQ[{a,b,c,m,n,p},x] && NonzeroQ[p+1] && NonzeroQ[p+2] && ZeroQ[(m+1)^2-b^2*n^2*(p+2)^2]


Int[x_^m_.*Cosh[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  (m+1)*x^(m+1)*Cosh[a+b*Log[c*x^n]]^p/((m+1)^2-b^2*n^2*p^2) -
  b*n*p*x^(m+1)*Cosh[a+b*Log[c*x^n]]^(p-1)*Sinh[a+b*Log[c*x^n]]/((m+1)^2-b^2*n^2*p^2) -
  Dist[b^2*n^2*p*(p-1)/((m+1)^2-b^2*n^2*p^2),Int[x^m*Cosh[a+b*Log[c*x^n]]^(p-2),x]] /;
FreeQ[{a,b,c,m,n},x] && RationalQ[p] && p>1 && NonzeroQ[(m+1)^2-b^2*n^2*p^2] && NonzeroQ[m+1]


Int[x_^m_.*Cosh[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  -x^(m+1)*Tanh[a+b*Log[c*x^n]]*Cosh[a+b*Log[c*x^n]]^(p+2)/(b*n*(p+1)) +
  (m+1)*x^(m+1)*Cosh[a+b*Log[c*x^n]]^(p+2)/(b^2*n^2*(p+1)*(p+2)) -
  Dist[((m+1)^2-b^2*n^2*(p+2)^2)/(b^2*n^2*(p+1)*(p+2)),Int[x^m*Cosh[a+b*Log[c*x^n]]^(p+2),x]] /;
FreeQ[{a,b,c,m,n},x] && RationalQ[p] && p<-1 && p!=-2 && NonzeroQ[m+1] && NonzeroQ[(m+1)^2-b^2*n^2*(p+2)^2]


(* ::Subsubsection::Closed:: *)
(*x^m Cosh[a x^n Log[b x]^p Log[b x]^p  Products of cosines and powers of logarithms*)
(**)


Int[Cosh[a_.*x_*Log[b_.*x_]^p_.]*Log[b_.*x_]^p_.,x_Symbol] :=
  Sinh[a*x*Log[b*x]^p]/a -
  Dist[p,Int[Cosh[a*x*Log[b*x]^p]*Log[b*x]^(p-1),x]] /;
FreeQ[{a,b},x] && RationalQ[p] && p>0


Int[Cosh[a_.*x_^n_*Log[b_.*x_]^p_.]*Log[b_.*x_]^p_.,x_Symbol] :=
  Sinh[a*x^n*Log[b*x]^p]/(a*n*x^(n-1)) -
  Dist[p/n,Int[Cosh[a*x^n*Log[b*x]^p]*Log[b*x]^(p-1),x]] +
  Dist[(n-1)/(a*n),Int[Sinh[a*x^n*Log[b*x]^p]/x^n,x]] /;
FreeQ[{a,b},x] && RationalQ[{n,p}] && p>0


Int[x_^m_*Cosh[a_.*x_^n_.*Log[b_.*x_]^p_.]*Log[b_.*x_]^p_.,x_Symbol] :=
  x^(m-n+1)*Sinh[a*x^n*Log[b*x]^p]/(a*n) -
  Dist[p/n,Int[x^m*Cosh[a*x^n*Log[b*x]^p]*Log[b*x]^(p-1),x]] -
  Dist[(m-n+1)/(a*n),Int[x^(m-n)*Sinh[a*x^n*Log[b*x]^p],x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n,p}] && p>0


(* ::Subsubsection::Closed:: *)
(*u Cosh[v]^2				Products involving squares of cosines*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cosh[z]^2 == 1/2 + 1/2*Cosh[2*z]*)


Int[u_*Cosh[v_]^2,x_Symbol] :=
  Dist[1/2,Int[u,x]] + 
  Dist[1/2,Int[u*Cosh[2*v],x]] /;
FunctionOfHyperbolicQ[u,2*v,x]


(* ::Subsubsection::Closed:: *)
(*u Cosh[v] Hyper[w]			Products of hyperbolic trig functions*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sinh[v]*Cosh[w] == Sinh[w+v]/2 - Sinh[w-v]/2*)


Int[u_.*Sinh[v_]*Cosh[w_],x_Symbol] :=
  Dist[1/2,Int[u*Regularize[Sinh[w+v],x],x]] - 
  Dist[1/2,Int[u*Regularize[Sinh[w-v],x],x]] /;
(PolynomialQ[v,x] && PolynomialQ[w,x] || IndependentQ[Cancel[v/w],x]) && PosQ[w-v]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cosh[v]*Cosh[w] == Cosh[v+w]/2 + Cosh[v-w]/2*)


Int[u_.*Cosh[v_]*Cosh[w_],x_Symbol] :=
  Dist[1/2,Int[u*Regularize[Cosh[v+w],x],x]] + 
  Dist[1/2,Int[u*Regularize[Cosh[v-w],x],x]] /;
(PolynomialQ[v,x] && PolynomialQ[w,x] || IndependentQ[Cancel[v/w],x]) && NonzeroQ[v-w]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cosh[v]*Tanh[w] == Sinh[v] - Sinh[v-w]*Sech[w]*)


Int[u_.*Cosh[v_]*Tanh[w_]^n_.,x_Symbol] :=
  Int[u*Sinh[v]*Tanh[w]^(n-1),x] - Sinh[v-w]*Int[u*Sech[w]*Tanh[w]^(n-1),x] /;
RationalQ[n] && n>0 && FreeQ[v-w,x] && NonzeroQ[v-w]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cosh[v]*Coth[w] == Sinh[v] + Cosh[v-w]*Csch[w]*)


Int[u_.*Cosh[v_]*Coth[w_]^n_.,x_Symbol] :=
  Int[u*Sinh[v]*Coth[w]^(n-1),x] + Cosh[v-w]*Int[u*Csch[w]*Coth[w]^(n-1),x] /;
RationalQ[n] && n>0 && FreeQ[v-w,x] && NonzeroQ[v-w]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cosh[v]*Sech[w] == Sinh[v-w]*Tanh[w] + Cosh[v-w]*)


Int[u_.*Cosh[v_]*Sech[w_]^n_.,x_Symbol] :=
  Sinh[v-w]*Int[u*Tanh[w]*Sech[w]^(n-1),x] + Cosh[v-w]*Int[u*Sech[w]^(n-1),x] /;
RationalQ[n] && n>0 && FreeQ[v-w,x] && NonzeroQ[v-w]


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cosh[v]*Csch[w] == Cosh[v-w]*Coth[w] + Sinh[v-w]*)


Int[u_.*Cosh[v_]*Csch[w_]^n_.,x_Symbol] :=
  Cosh[v-w]*Int[u*Coth[w]*Csch[w]^(n-1),x] + Sinh[v-w]*Int[u*Csch[w]^(n-1),x] /;
RationalQ[n] && n>0 && FreeQ[v-w,x] && NonzeroQ[v-w]


(* ::Subsection::Closed:: *)
(*Hyperbolic Tangent Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*(c Tanh[a+b x])^n			Powers of tangents of linears*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.243.17, CRC 556, A&S 4.5.79*)


(* ::Item:: *)
(*Derivation: Reciprocal rule*)


(* ::Item:: *)
(*Basis: Tanh[z] == Sinh[z]/Cosh[z]*)


Int[Tanh[a_.+b_.*x_],x_Symbol] :=
  Log[Cosh[a+b*x]]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Reference: G&R 2.423.22, CRC 569*)


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Tanh[z]^2 == 1-Sech[z]^2*)


Int[Tanh[a_.+b_.*x_]^2,x_Symbol] :=
  x - Tanh[a+b*x]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Reference: G&R 2.411.3, CRC 570, A&S 4.5.87*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


(* ::Item:: *)
(*Basis: Tanh[z]^n == Tanh[z]^(n-1)/Cosh[z]*Sinh[z]*)


Int[(c_.*Tanh[a_.+b_.*x_])^n_,x_Symbol] :=
  -c*(c*Tanh[a+b*x])^(n-1)/(b*(n-1)) + 
  Dist[c^2,Int[(c*Tanh[a+b*x])^(n-2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n>1


(* ::Item::Closed:: *)
(*Reference: G&R 2.411.4, CRC 574'*)


(* ::Item:: *)
(*Derivation: Inverted integration by parts with a double-back flip*)


Int[(c_.*Tanh[a_.+b_.*x_])^n_,x_Symbol] :=
  (c*Tanh[a+b*x])^(n+1)/(b*c*(n+1)) + 
  Dist[1/c^2,Int[(c*Tanh[a+b*x])^(n+2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n<-1


(* ::Subsubsection::Closed:: *)
(*(a+b Tanh[c+d x])^n			Powers of binomials of tangents where a^2-b^2 is zero*)


Int[Sqrt[a_+b_.*Tanh[c_.+d_.*x_]],x_Symbol] :=
  (Sqrt[2]*b*ArcTanh[Sqrt[a+b*Tanh[c+d*x]]/(Sqrt[2]*Rt[a,2])])/(d*Rt[a,2]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2-b^2] && PosQ[a]


Int[Sqrt[a_+b_.*Tanh[c_.+d_.*x_]],x_Symbol] :=
  -(Sqrt[2]*b*ArcTan[Sqrt[a+b*Tanh[c+d*x]]/(Sqrt[2]*Rt[-a,2])])/(d*Rt[-a,2]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2-b^2] && NegQ[a]


Int[(a_+b_.*Tanh[c_.+d_.*x_])^n_,x_Symbol] :=
  -a^2*(a+b*Tanh[c+d*x])^(n-1)/(b*d*(n-1)) + 
  Dist[2*a,Int[(a+b*Tanh[c+d*x])^(n-1),x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n>1 && ZeroQ[a^2-b^2]


Int[1/(a_+b_.*Tanh[c_.+d_.*x_]),x_Symbol] :=
  x/(2*a) - a/(2*b*d*(a+b*Tanh[c+d*x])) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2-b^2]


Int[(a_+b_.*Tanh[c_.+d_.*x_])^n_,x_Symbol] :=
  a*(a+b*Tanh[c+d*x])^n/(2*b*d*n) + 
  Dist[1/(2*a),Int[(a+b*Tanh[c+d*x])^(n+1),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n<0 && ZeroQ[a^2-b^2]


(* ::Subsubsection::Closed:: *)
(*1 / (a+b Tanh[c+d x]^2)		Reciprocals of binomials of tangents*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: 1/(a+b*Tanh[z]) == Cosh[z]/(a*Cosh[z]+b*Sinh[z])*)


Int[1/(a_+b_.*Tanh[c_.+d_.*x_]),x_Symbol] :=
  a*x/(a^2-b^2) - b*Log[a*Cosh[c+d*x]+b*Sinh[c+d*x]]/(d*(a^2-b^2)) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2]


Int[1/(a_+b_.*Tanh[c_.+d_.*x_]^2),x_Symbol] :=
  x/(a+b) + Sqrt[b]*ArcTan[(Sqrt[b]*Tanh[c+d*x])/Sqrt[a]]/(Sqrt[a]*d*(a+b)) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a+b]


(* ::Subsubsection::Closed:: *)
(*x^m Tanh[a+b x^n]^p			Products of monomials and powers of tangents of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Tanh[z] == 1 - 2/(1+E^(2*z))*)


Int[x_^m_.*Tanh[a_.+b_.*x_^n_.],x_Symbol] :=
  x^(m+1)/(m+1) - 
  Dist[2,Int[x^m/(1+E^(2*a+2*b*x^n)),x]] /;
FreeQ[{a,b,m,n},x] && NonzeroQ[m-n+1] && IntegerQ[m] && m>0


(* Note: Rule not in literature ??? *)
Int[x_^m_.*Tanh[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  -x^(m-n+1)*Tanh[a+b*x^n]^(p-1)/(b*n*(p-1)) + 
  Dist[(m-n+1)/(b*n*(p-1)),Int[x^(m-n)*Tanh[a+b*x^n]^(p-1),x]] + 
  Int[x^m*Tanh[a+b*x^n]^(p-2),x] /;
FreeQ[{a,b},x] && RationalQ[{m,n,p}] && p>1 && NonzeroQ[m-n+1] && 0<n<=m


(* ::Subsubsection::Closed:: *)
(*x^m Tanh[a+b x+c x^2]		Products of monomials and tangents of quadratic trinomials*)


Int[x_*Tanh[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  Log[Cosh[a+b*x+c*x^2]]/(2*c) -
  Dist[b/(2*c),Int[Tanh[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c},x]


(* Valid, but need a rule for x^m*Log[Cosh[a+b*x+c*x^2]] to be useful. *)
(* Int[x_^m_*Tanh[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  x^(m-1)*Log[Cosh[a+b*x+c*x^2]]/(2*c) -
  Dist[b/(2*c),Int[x^(m-1)*Tanh[a+b*x+c*x^2],x]] -
  Dist[(m-1)/(2*c),Int[x^(m-2)*Log[Cosh[a+b*x+c*x^2]],x]] /;
FreeQ[{a,b,c},x] && RationalQ[m] && m>1 *)


(* ::Subsection::Closed:: *)
(*Hyperbolic Cotangent Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*(c Coth[a+b x])^n			Powers of cotangents of linears*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.423.33, CRC 557, A&S 4.5.82*)


(* ::Item:: *)
(*Derivation: Reciprocal rule*)


(* ::Item:: *)
(*Basis: Coth[z] == Cosh[z]/Sinh[z]*)


Int[Coth[a_.+b_.*x_],x_Symbol] :=
  Log[Sinh[a+b*x]]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Reference: G&R 2.423.38, CRC 573*)


(* ::Item:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Coth[z]^2 == 1+Csch[z]^2*)


Int[Coth[a_.+b_.*x_]^2,x_Symbol] :=
  x - Coth[a+b*x]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Reference: G&R 2.411.4, CRC 574, A&S 4.5.88*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


(* ::Item:: *)
(*Basis: Coth[z]^n == Coth[z]^(n-1)/Sinh[z]*Cosh[z]*)


Int[(c_.*Coth[a_.+b_.*x_])^n_,x_Symbol] :=
  -c*(c*Coth[a+b*x])^(n-1)/(b*(n-1)) + 
  Dist[c^2,Int[(c*Coth[a+b*x])^(n-2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n>1


(* ::Item::Closed:: *)
(*Reference: G&R 2.411.3, CRC 570'*)


(* ::Item:: *)
(*Derivation: Inverted integration by parts with a double-back flip*)


Int[(c_.*Coth[a_.+b_.*x_])^n_,x_Symbol] :=
  (c*Coth[a+b*x])^(n+1)/(b*c*(n+1)) + 
  Dist[1/c^2,Int[(c*Coth[a+b*x])^(n+2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n<-1


(* ::Subsubsection::Closed:: *)
(*(a+b Coth[c+d x])^n			Powers of binomials of cotangents where a^2-b^2 is zero*)


Int[Sqrt[a_+b_.*Coth[c_.+d_.*x_]],x_Symbol] :=
  (Sqrt[2]*b*ArcCoth[Sqrt[a+b*Coth[c+d*x]]/(Sqrt[2]*Rt[a,2])])/(d*Rt[a,2]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2-b^2] && PosQ[a]


Int[Sqrt[a_+b_.*Coth[c_.+d_.*x_]],x_Symbol] :=
  (Sqrt[2]*b*ArcCot[Sqrt[a+b*Coth[c+d*x]]/(Sqrt[2]*Rt[-a,2])])/(d*Rt[-a,2]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2-b^2] && NegQ[a]


Int[(a_+b_.*Coth[c_.+d_.*x_])^n_,x_Symbol] :=
  -a^2*(a+b*Coth[c+d*x])^(n-1)/(b*d*(n-1)) + 
  Dist[2*a,Int[(a+b*Coth[c+d*x])^(n-1),x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n>1 && ZeroQ[a^2-b^2]


Int[1/(a_+b_.*Coth[c_.+d_.*x_]),x_Symbol] :=
  x/(2*a) - a/(2*b*d*(a+b*Coth[c+d*x])) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2-b^2]


Int[(a_+b_.*Coth[c_.+d_.*x_])^n_,x_Symbol] :=
  a*(a+b*Coth[c+d*x])^n/(2*b*d*n) + 
  Dist[1/(2*a),Int[(a+b*Coth[c+d*x])^(n+1),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n<0 && ZeroQ[a^2-b^2]


(* ::Subsubsection::Closed:: *)
(*1 / (a+b Coth[c+d x]^n)		Reciprocals of binomials of cotangents*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: 1/(a+b*Coth[z]) == Sinh[z]/(a*Sinh[z]+b*Cosh[z])*)


Int[1/(a_+b_.*Coth[c_.+d_.*x_]),x_Symbol] :=
  a*x/(a^2-b^2) - b*Log[b*Cosh[c+d*x]+a*Sinh[c+d*x]]/(d*(a^2-b^2)) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2]


Int[1/(a_+b_.*Coth[c_.+d_.*x_]^2),x_Symbol] :=
  x/(a+b) + Sqrt[b]*ArcTan[(Sqrt[b]*Coth[c+d*x])/Sqrt[a]]/(Sqrt[a]*d*(a+b)) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a+b]


(* ::Subsubsection::Closed:: *)
(*x^m Coth[a+b x^n]^p			Products of monomials and powers of cotangents of binomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Coth[z] == 1 - 2/(1-E^(2*z))*)


Int[x_^m_.*Coth[a_.+b_.*x_^n_.],x_Symbol] :=
  x^(m+1)/(m+1) - 
  Dist[2,Int[x^m/(1-E^(2*a+2*b*x^n)),x]] /;
FreeQ[{a,b,m,n},x] && NonzeroQ[m-n+1] && IntegerQ[m] && m>0


(* Note: Rule not in literature ??? *)
Int[x_^m_.*Coth[a_.+b_.*x_^n_.]^p_,x_Symbol] :=
  -x^(m-n+1)*Coth[a+b*x^n]^(p-1)/(b*n*(p-1)) + 
  Dist[(m-n+1)/(b*n*(p-1)),Int[x^(m-n)*Coth[a+b*x^n]^(p-1),x]] + 
  Int[x^m*Coth[a+b*x^n]^(p-2),x] /;
FreeQ[{a,b},x] && RationalQ[{m,n,p}] && p>1 && NonzeroQ[m-n+1] && 0<n<=m


(* ::Subsubsection::Closed:: *)
(*x^m Coth[a+b x+c x^2]		Products of monomials and cotangents of quadratic trinomials*)


Int[x_*Coth[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  Log[Sinh[a+b*x+c*x^2]]/(2*c) -
  Dist[b/(2*c),Int[Coth[a+b*x+c*x^2],x]] /;
FreeQ[{a,b,c},x]


(* Int[x_^m_*Coth[a_.+b_.*x_+c_.*x_^2],x_Symbol] :=
  x^(m-1)*Log[Sinh[a+b*x+c*x^2]]/(2*c) -
  Dist[b/(2*c),Int[x^(m-1)*Coth[a+b*x+c*x^2],x]] -
  Dist[(m-1)/(2*c),Int[x^(m-2)*Log[Sinh[a+b*x+c*x^2]],x]] /;
FreeQ[{a,b,c},x] && RationalQ[m] && m>1 *)


(* ::Subsection::Closed:: *)
(*Hyperbolic Secant Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*Sech[a+b x]^n				Powers of secants of linears*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.423.9, CRC 558, A&S 4.5.81*)


(* ::Item:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Sech[z] == 1/(1+Sinh[z]^2)*Sinh'[z]*)


Int[Sech[a_.+b_.*x_],x_Symbol] :=
(* -ArcCot[Sinh[a+b*x]]/b *)
  ArcTan[Sinh[a+b*x]]/b /;
FreeQ[{a,b},x]


(* Note: This entirely redundant is required due to idem potent problem in Mathematica 6 & 7. *)
Int[1/Sqrt[Sech[a_.+b_.*x_]],x_Symbol] :=
  Sqrt[Cosh[a+b*x]]*Sqrt[Sech[a+b*x]]*Int[Sqrt[Cosh[a+b*x]],x] /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Extract constant factor*)


(* ::Item:: *)
(*Basis: D[(c*Sech[x])^n*Cosh[x]^n,x] == 0*)


Int[(c_.*Sech[a_.+b_.*x_])^n_,x_Symbol] :=
  (c*Sech[a+b*x])^n*Cosh[a+b*x]^n*Int[1/Cosh[a+b*x]^n,x] /;
FreeQ[{a,b,c},x] && RationalQ[n] && -1<n<1


(* ::Item::Closed:: *)
(*Reference: G&R 2.423.10, CRC 571*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: Tanh'[z] == Sech[z]^2*)


Int[Sech[a_.+b_.*x_]^2,x_Symbol] :=
  Tanh[a+b*x]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is even, Sech[z]^n == (1-Tanh[z]^2)^((n-2)/2)*Tanh'[z]*)


Int[Sech[a_.+b_.*x_]^n_,x_Symbol] :=
  Dist[1/b,Subst[Int[Regularize[(1-x^2)^((n-2)/2),x],x],x,Tanh[a+b*x]]] /;
FreeQ[{a,b},x] && EvenQ[n] && n>1


(* ::Item::Closed:: *)
(*Reference: G&R 2.411.6, CRC 568b*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[(c_.*Sech[a_.+b_.*x_])^n_,x_Symbol] :=
  c*Sinh[a+b*x]*(c*Sech[a+b*x])^(n-1)/(b*(n-1)) + 
  Dist[(n-2)*c^2/(n-1),Int[(c*Sech[a+b*x])^(n-2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n>1 && Not[EvenQ[n]]


(* ::Item::Closed:: *)
(*Reference: G&R 2.411.1, CRC 567a*)


(* ::Item:: *)
(*Derivation: Inverted integration by parts with a double-back flip*)


Int[(c_.*Sech[a_.+b_.*x_])^n_,x_Symbol] :=
  -Sinh[a+b*x]*(c*Sech[a+b*x])^(n+1)/(b*c*n) + 
  Dist[(n+1)/(c^2*n),Int[(c*Sech[a+b*x])^(n+2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n<-1


(* ::Subsubsection::Closed:: *)
(*x^m Sech[a+b x]^n			Products of monomials and powers of secants of linears*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Sech[a_.+b_.*x_],x_Symbol] :=
  2*x^m*ArcTan[E^(a+b*x)]/b -
  Dist[2*m/b,Int[x^(m-1)*ArcTan[E^(a+b*x)],x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Reference: CRC 430h*)


Int[x_^m_.*Sech[a_.+b_.*x_]^2,x_Symbol] :=
  x^m*Tanh[a+b*x]/b -
  Dist[m/b,Int[x^(m-1)*Tanh[a+b*x],x]] /;
FreeQ[{a,b},x] && RationalQ[m] && m>0


(* ::Item:: *)
(*Reference: G&R 2.643.2h, CRC 431h*)


Int[x_*Sech[a_.+b_.*x_]^n_,x_Symbol] :=
  x*Tanh[a+b*x]*Sech[a+b*x]^(n-2)/(b*(n-1)) +
  Sech[a+b*x]^(n-2)/(b^2*(n-1)*(n-2)) +
  Dist[(n-2)/(n-1),Int[x*Sech[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n>1 && n!=2


(* ::Item:: *)
(*Reference: G&R 2.643.2h*)


Int[x_^m_*Sech[a_.+b_.*x_]^n_,x_Symbol] :=
  x^m*Tanh[a+b*x]*Sech[a+b*x]^(n-2)/(b*(n-1)) +
  m*x^(m-1)*Sech[a+b*x]^(n-2)/(b^2*(n-1)*(n-2)) +
  Dist[(n-2)/(n-1),Int[x^m*Sech[a+b*x]^(n-2),x]] -
  Dist[m*(m-1)/(b^2*(n-1)*(n-2)),Int[x^(m-2)*Sech[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && n>1 && n!=2 && m>1


(* ::Item:: *)
(*Reference: G&R 2.631.3h*)


Int[x_*Sech[a_.+b_.*x_]^n_,x_Symbol] :=
  -Sech[a+b*x]^n/(b^2*n^2) -
  x*Sinh[a+b*x]*Sech[a+b*x]^(n+1)/(b*n) +
  Dist[(n+1)/n,Int[x*Sech[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n<-1


(* ::Item:: *)
(*Reference: G&R 2.631.3h*)


Int[x_^m_*Sech[a_.+b_.*x_]^n_,x_Symbol] :=
  -m*x^(m-1)*Sech[a+b*x]^n/(b^2*n^2) -
  x^m*Sinh[a+b*x]*Sech[a+b*x]^(n+1)/(b*n) +
  Dist[(n+1)/n,Int[x^m*Sech[a+b*x]^(n+2),x]] +
  Dist[m*(m-1)/(b^2*n^2),Int[x^(m-2)*Sech[a+b*x]^n,x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && n<-1 && m>1


(* ::Subsubsection::Closed:: *)
(*(a+b Sech[c+d x])^n			Powers of constant plus secants of linears where a^2-b^2 is zero*)


Int[Sqrt[a_+b_.*Sech[c_.+d_.*x_]],x_Symbol] :=
  2*a*ArcTan[Sqrt[-1+a/b*Sech[c+d*x]]]*Tanh[c+d*x]/
		(d*Sqrt[-1+a/b*Sech[c+d*x]]*Sqrt[a+b*Sech[c+d*x]]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2-b^2]


(* Note: There should be a simpler antiderivative! *)
Int[1/Sqrt[a_+b_.*Sech[c_.+d_.*x_]],x_Symbol] :=
  -(Sqrt[2]*ArcTan[(Sqrt[2]*Sqrt[a])/Sqrt[-a+b*Sech[x]]]+2*ArcTan[Sqrt[-a+b*Sech[x]]/Sqrt[a]])*
  Sqrt[-a+b*Sech[x]]*Sqrt[a+b*Sech[x]]*Coth[x]/a^(3/2) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2-b^2]


(* ::Subsubsection::Closed:: *)
(*(a+b Sech[c+d x]^n)^m		Powers of constant plus powers of secants of linears*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If n is an integer, a+b*Sech[z]^n == (b+a*Cosh[z]^n)/Cosh[z]^n*)


Int[(a_+b_.*Sech[v_]^n_.)^m_,x_Symbol] :=
  Int[(b+a*Cosh[v]^n)^m/Cosh[v]^(m*n),x] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && m<0 && n>0


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If n is an integer, a+b*Sech[z]^n == (b+a*Cosh[z]^n)/Cosh[z]^n*)


Int[Cosh[v_]^p_.*(a_+b_.*Sech[v_]^n_.)^m_,x_Symbol] :=
  Int[Cosh[v]^(p-m*n)*(b+a*Cosh[v]^n)^m,x] /;
FreeQ[{a,b},x] && IntegerQ[{m,n,p}] && m<0 && n>0


(* ::Subsubsection::Closed:: *)
(*Sech[a+b x]^n Csch[a+b x]^m		Products of powers of secants and cosecants*)


(* ::Item:: *)
(*Reference: G&R 2.423.49*)


Int[Csch[a_.+b_.*x_]*Sech[a_.+b_.*x_],x_Symbol] :=
  Log[Tanh[a+b*x]]/b /;
FreeQ[{a,b},x] && PosQ[b]


Int[Csch[a_.+b_.*x_]^m_*Sech[a_.+b_.*x_]^n_,x_Symbol] :=
  Csch[a+b*x]^(m-1)*Sech[a+b*x]^(n-1)/(b*(n-1)) /;
FreeQ[{a,b,m,n},x] && ZeroQ[m+n-2] && NonzeroQ[n-1] && PosQ[n]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If m and n are integers and m + n is even, Csch[z]^m*Sech[z]^n == (1-Tanh[z]^2)^((m+n)/2-1)/Tanh[z]^m*Tanh'[z]*)


Int[Csch[a_.+b_.*x_]^m_.*Sech[a_.+b_.*x_]^n_,x_Symbol] :=
  Dist[1/b,Subst[Int[Regularize[(1-x^2)^((m+n)/2-1)/x^m,x],x],x,Tanh[a+b*x]]] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && EvenQ[m+n] && 0<m<=n
(* FreeQ[{a,b},x] && OddQ[m] && OddQ[n] *)


(* ::Item:: *)
(*Reference: G&R 2.411.4*)


Int[Csch[a_.+b_.*x_]^m_*Sech[a_.+b_.*x_]^n_,x_Symbol] :=
  -Csch[a+b*x]^(m+1)*Sech[a+b*x]^(n-1)/(b*(n-1)) -
  Dist[(m+1)/(n-1),Int[Csch[a+b*x]^(m+2)*Sech[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m<-1 && n>1


(* ::Item:: *)
(*Reference: G&R 2.411.6, CRC 568b, A&S 4.5.86b*)


Int[Csch[a_.+b_.*x_]^m_.*Sech[a_.+b_.*x_]^n_,x_Symbol] :=
  Csch[a+b*x]^(m-1)*Sech[a+b*x]^(n-1)/(b*(n-1)) +
  Dist[(m+n-2)/(n-1),Int[Csch[a+b*x]^m*Sech[a+b*x]^(n-2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n>1 && Not[EvenQ[m+n]] && Not[EvenQ[n] && OddQ[m] && m>1]


(* ::Item:: *)
(*Reference: G&R 2.411.1, CRC 567a, A&S 4.5.85a*)


Int[Csch[a_.+b_.*x_]^m_.*Sech[a_.+b_.*x_]^n_,x_Symbol] :=
  -Csch[a+b*x]^(m-1)*Sech[a+b*x]^(n+1)/(b*(m+n)) +
  Dist[(n+1)/(m+n),Int[Csch[a+b*x]^m*Sech[a+b*x]^(n+2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n<-1 && NonzeroQ[m+n]


(* ::Subsubsection::Closed:: *)
(*Sech[a+b x]^m Tanh[a+b x]^n	Products of powers of secants and tangents*)
(**)


(* ::Item:: *)
(*Derivation: Power rule for integration*)


Int[Sech[a_.+b_.*x_]^m_.*Tanh[a_.+b_.*x_]^n_.,x_Symbol] :=
  -Sech[a+b*x]^m/(b*m) /;
FreeQ[{a,b,m},x] && n===1


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If m is even, Sech[z]^m == (1-Tanh[z]^2)^((m-2)/2)*Tanh'[z]*)


Int[Sech[a_.+b_.*x_]^m_*Tanh[a_.+b_.*x_]^n_.,x_Symbol] :=
  Dist[1/b,Subst[Int[Regularize[x^n*(1-x^2)^((m-2)/2),x],x],x,Tanh[a+b*x]]] /;
FreeQ[{a,b,n},x] && EvenQ[m] && m>2 && Not[OddQ[n] && 0<n<m-1]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is odd, Sech[z]^m*Tanh[z]^n == -Sech[z]^(m-1)*(1-Sech[z]^2)^((n-1)/2)*Sech'[z]*)


Int[Sech[a_.+b_.*x_]^m_.*Tanh[a_.+b_.*x_]^n_.,x_Symbol] :=
  Dist[-1/b,Subst[Int[Regularize[x^(m-1)*(1-x^2)^((n-1)/2),x],x],x,Sech[a+b*x]]] /;
FreeQ[{a,b,m},x] && OddQ[n] && Not[EvenQ[m] && 0<m<=n+1]


(* ::Item:: *)
(*Reference: G&R 2.411.5, CRC 568a*)


Int[Sech[a_.+b_.*x_]^m_*Tanh[a_.+b_.*x_]^n_,x_Symbol] :=
  Sech[a+b*x]^(m-2)*Tanh[a+b*x]^(n+1)/(b*(n+1)) +
  Dist[(m-2)/(n+1),Int[Sech[a+b*x]^(m-2)*Tanh[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m>1 && n<-1 && Not[EvenQ[m]]


(* ::Item:: *)
(*Reference: G&R 2.411.2, CRC 567b*)


Int[Sech[a_.+b_.*x_]^m_*Tanh[a_.+b_.*x_]^n_,x_Symbol] :=
  -Sech[a+b*x]^m*Tanh[a+b*x]^(n-1)/(b*m) +
  Dist[(n-1)/m,Int[Sech[a+b*x]^(m+2)*Tanh[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m<-1 && n>1 && Not[EvenQ[m]]


(* ::Item:: *)
(*Reference: G&R 2.411.1, CRC 567a*)


Int[Sech[a_.+b_.*x_]^m_*Tanh[a_.+b_.*x_]^n_,x_Symbol] :=
  -Sech[a+b*x]^m*Tanh[a+b*x]^(n+1)/(b*m) /;
FreeQ[{a,b,m,n},x] && ZeroQ[m+n+1]


Int[Sech[a_.+b_.*x_]^m_*Tanh[a_.+b_.*x_]^n_,x_Symbol] :=
  -Sech[a+b*x]^m*Tanh[a+b*x]^(n+1)/(b*m) +
  Dist[(m+n+1)/m,Int[Sech[a+b*x]^(m+2)*Tanh[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m<-1 && Not[EvenQ[m]]


(* ::Item:: *)
(*Reference: G&R 2.411.6, CRC 568b*)


Int[Sech[a_.+b_.*x_]^m_*Tanh[a_.+b_.*x_]^n_,x_Symbol] :=
  Sech[a+b*x]^(m-2)*Tanh[a+b*x]^(n+1)/(b*(m+n-1)) +
  Dist[(m-2)/(m+n-1),Int[Sech[a+b*x]^(m-2)*Tanh[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m>1 && NonzeroQ[m+n-1] && Not[EvenQ[m]] && Not[OddQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.411.3*)


Int[Sech[a_.+b_.*x_]^m_.*Tanh[a_.+b_.*x_]^n_,x_Symbol] :=
  -Sech[a+b*x]^m*Tanh[a+b*x]^(n-1)/(b*(m+n-1)) +
  Dist[(n-1)/(m+n-1),Int[Sech[a+b*x]^m*Tanh[a+b*x]^(n-2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n>1 && NonzeroQ[m+n-1] && Not[EvenQ[m]] && Not[OddQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.411.4*)


Int[Sech[a_.+b_.*x_]^m_*Tanh[a_.+b_.*x_]^n_,x_Symbol] :=
  Sech[a+b*x]^m*Tanh[a+b*x]^(n+1)/(b*(n+1)) +
  Dist[(m+n+1)/(n+1),Int[Sech[a+b*x]^m*Tanh[a+b*x]^(n+2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n<-1 && Not[EvenQ[m]]


(* ::Subsubsection::Closed:: *)
(*x^m Sech[a+b x^n]^p Sinh[a+b x^n]  Products of monomials, sines and powers of secants of binomials*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Sech[a_.+b_.*x_^n_.]^p_*Sinh[a_.+b_.*x_^n_.],x_Symbol] :=
  -x^(m-n+1)*Sech[a+b*x^n]^(p-1)/(b*n*(p-1)) +
  Dist[(m-n+1)/(b*n*(p-1)),Int[x^(m-n)*Sech[a+b*x^n]^(p-1),x]] /;
FreeQ[{a,b,p},x] && RationalQ[m] && IntegerQ[n] && m-n>=0 && NonzeroQ[p-1]


(* ::Subsubsection::Closed:: *)
(*x^m Sech[a+b x^n]^p Tanh[a+b x^n]  Products of monomials, tangents and powers of secants of binomials*)
(**)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Sech[a_.+b_.*x_^n_.]^p_.*Tanh[a_.+b_.*x_^n_.]^q_.,x_Symbol] :=
  -x^(m-n+1)*Sech[a+b*x^n]^p/(b*n*p) +
  Dist[(m-n+1)/(b*n*p),Int[x^(m-n)*Sech[a+b*x^n]^p,x]] /;
FreeQ[{a,b,p},x] && RationalQ[m] && IntegerQ[n] && m-n>=0 && q===1 (* Required so InputForm is matchable *)


(* ::Subsubsection::Closed:: *)
(*Sech[a+b Log[c x^n]]^p		Powers of secants of logarithms*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Sech[b*Log[c*x^n]] == 2 / ((c*x^n)^b + 1/(c*x^n)^b)*)


Int[Sech[b_.*Log[c_.*x_^n_.]]^p_.,x_Symbol] :=
  Int[(2/((c*x^n)^b+1/(c*x^n)^b))^p,x] /;
FreeQ[c,x] && RationalQ[{b,n,p}]


Int[Sech[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  x*Tanh[a+b*Log[c*x^n]]*Sech[a+b*Log[c*x^n]]^(p-2)/(b*n*(p-1)) +
  x*Sech[a+b*Log[c*x^n]]^(p-2)/(b^2*n^2*(p-1)*(p-2)) -
  Dist[(1-b^2*n^2*(p-2)^2)/(b^2*n^2*(p-1)*(p-2)),Int[Sech[a+b*Log[c*x^n]]^(p-2),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p>1 && p!=2


Int[Sech[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  x*Sech[a+b*Log[c*x^n]]^p/(1-b^2*n^2*p^2) +
  b*n*p*x*Sech[a+b*Log[c*x^n]]^(p+1)*Sinh[a+b*Log[c*x^n]]/(1-b^2*n^2*p^2) -
  Dist[b^2*n^2*p*(p+1)/(1-b^2*n^2*p^2),Int[Sech[a+b*Log[c*x^n]]^(p+2),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p<-1 && NonzeroQ[1-b^2*n^2*p^2]


(* ::Subsubsection::Closed:: *)
(*x^m Sech[a+b Log[c x^n]]^p		Products of monomials and powers of secants of logarithms*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Sech[b*Log[c*x^n]] == 2 / ((c*x^n)^b + 1/(c*x^n)^b)*)


Int[x_^m_.Sech[b_.*Log[c_.*x_^n_.]]^p_.,x_Symbol] :=
  Int[x^m*(2/((c*x^n)^b+1/(c*x^n)^b))^p,x] /;
FreeQ[c,x] && RationalQ[{b,m,n,p}]


Int[Sech[a_.+b_.*Log[c_.*x_^n_.]]^2/x_,x_Symbol] :=
  Tanh[a+b*Log[c*x^n]]/(b*n) /;
FreeQ[{a,b,c,n},x]


Int[Sech[a_.+b_.*Log[c_.*x_^n_.]]^p_/x_,x_Symbol] :=
  Tanh[a+b*Log[c*x^n]]*Sech[a+b*Log[c*x^n]]^(p-2)/(b*n*(p-1)) +
  Dist[(p-2)/(p-1),Int[Sech[a+b*Log[c*x^n]]^(p-2)/x,x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p>1


Int[Sech[a_.+b_.*Log[c_.*x_^n_.]]^p_/x_,x_Symbol] :=
  -Sech[a+b*Log[c*x^n]]^(p+1)*Sinh[a+b*Log[c*x^n]]/(b*n*p) +
  Dist[(p+1)/p,Int[Sech[a+b*Log[c*x^n]]^(p+2)/x,x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p<-1


Int[x_^m_.*Sech[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  x^(m+1)*Tanh[a+b*Log[c*x^n]]*Sech[a+b*Log[c*x^n]]^(p-2)/(b*n*(p-1)) +
  (m+1)*x^(m+1)*Sech[a+b*Log[c*x^n]]^(p-2)/(b^2*n^2*(p-1)*(p-2)) -
  Dist[(b^2*n^2*(p-2)^2-(m+1)^2)/(b^2*n^2*(p-1)*(p-2)),Int[x^m*Sech[a+b*Log[c*x^n]]^(p-2),x]] /;
FreeQ[{a,b,c,m,n},x] && RationalQ[p] && p>1 && p!=2


Int[x_^m_.*Sech[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  -(m+1)*x^(m+1)*Sech[a+b*Log[c*x^n]]^p/(b^2*n^2*p^2-(m+1)^2) -
  b*n*p*x^(m+1)*Sech[a+b*Log[c*x^n]]^(p+1)*Sinh[a+b*Log[c*x^n]]/(b^2*n^2*p^2-(m+1)^2) +
  Dist[b^2*n^2*p*(p+1)/(b^2*n^2*p^2-(m+1)^2),Int[x^m*Sech[a+b*Log[c*x^n]]^(p+2),x]] /;
FreeQ[{a,b,c,m,n},x] && RationalQ[p] && p<-1 && NonzeroQ[b^2*n^2*p^2-(m+1)^2]


(* ::Subsection::Closed:: *)
(*Hyperbolic Cosecant Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*Csch[a+b x]^n				Powers of cosecants of linears*)


(* ::Item::Closed:: *)
(*Reference: G&R 2.423.1', CRC 559', A&S 4.5.80'*)


(* ::Item:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: Csch[z] == -1/(1-Cosh[z]^2)*Cosh'[z]*)


Int[Csch[a_.+b_.*x_],x_Symbol] :=
(* -ArcTanh[Cosh[a+b*x]]/b *)
  -ArcCoth[Cosh[a+b*x]]/b /;
FreeQ[{a,b},x]


(* Note: This entirely redundant is required due to idem potent problem in Mathematica 6 & 7. *)
Int[1/Sqrt[Csch[a_.+b_.*x_]],x_Symbol] :=
  Sqrt[Csch[a+b*x]]*Sqrt[Sinh[a+b*x]]*Int[Sqrt[Sinh[a+b*x]],x] /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Extract constant factor*)


(* ::Item:: *)
(*Basis: D[(c*Csch[x])^n*Sinh[x]^n,x] == 0*)


Int[(c_.*Csch[a_.+b_.*x_])^n_,x_Symbol] :=
  (c*Csch[a+b*x])^n*Sinh[a+b*x]^n*Int[1/Sinh[a+b*x]^n,x] /;
FreeQ[{a,b,c},x] && RationalQ[n] && -1<n<1


(* ::Item::Closed:: *)
(*Reference: G&R 2.423.2, CRC 575*)


(* ::Item:: *)
(*Derivation: Primitive rule*)


(* ::Item:: *)
(*Basis: Coth'[z] == -Csch[z]^2*)


Int[Csch[a_.+b_.*x_]^2,x_Symbol] :=
  -Coth[a+b*x]/b /;
FreeQ[{a,b},x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is even, Csch[z]^n == -(-1+Coth[z]^2)^((n-2)/2)*Coth'[z]*)


Int[Csch[a_.+b_.*x_]^n_,x_Symbol] :=
  Dist[-1/b,Subst[Int[Regularize[(-1+x^2)^((n-2)/2),x],x],x,Coth[a+b*x]]] /;
FreeQ[{a,b},x] && EvenQ[n] && n>1


(* ::Item::Closed:: *)
(*Reference: G&R 2.411.5, CRC 568a*)


(* ::Item:: *)
(*Derivation: Inverted integration by parts with a double-back flip*)


Int[(c_.*Csch[a_.+b_.*x_])^n_,x_Symbol] :=
  -c*Cosh[a+b*x]*(c*Csch[a+b*x])^(n-1)/(b*(n-1)) - 
  Dist[(n-2)*c^2/(n-1),Int[(c*Csch[a+b*x])^(n-2),x]] /;
FreeQ[{a,b,c},x] && RationalQ[n] && n>1 && Not[EvenQ[n]]


(* ::Item::Closed:: *)
(*Reference: G&R 2.411.2, CRC 567b*)


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[(c_.*Csch[a_.+b_.*x_])^n_,x_Symbol] :=
  -Cosh[a+b*x]*(c*Csch[a+b*x])^(n+1)/(b*c*n) - 
  Dist[(n+1)/(c^2*n),Int[(c*Csch[a+b*x])^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n<-1


(* ::Subsubsection::Closed:: *)
(*x^m Csch[a+b x]^n			Products of monomials and powers of cosecants of linears*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Csch[a_.+b_.*x_],x_Symbol] :=
  -2*x^m*ArcTanh[E^(a+b x)]/b +
  Dist[2*m/b,Int[x^(m-1)*ArcTanh[E^(a+b x)],x]] /;
FreeQ[{a,b},x] && IntegerQ[m] && m>0


(* ::Item:: *)
(*Reference: CRC 428h*)


Int[x_^m_.*Csch[a_.+b_.*x_]^2,x_Symbol] :=
  -x^m*Coth[a+b*x]/b +
  Dist[m/b,Int[x^(m-1)*Coth[a+b*x],x]] /;
FreeQ[{a,b},x] && RationalQ[m] && m>0


(* ::Item:: *)
(*Reference: G&R 2.643.1h, CRC 429h*)


Int[x_*Csch[a_.+b_.*x_]^n_,x_Symbol] :=
  -x*Coth[a+b*x]*Csch[a+b*x]^(n-2)/(b*(n-1)) -
  Csch[a+b*x]^(n-2)/(b^2*(n-1)*(n-2)) -
  Dist[(n-2)/(n-1),Int[x*Csch[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n>1 && n!=2


(* ::Item:: *)
(*Reference: G&R 2.643.1h*)


Int[x_^m_*Csch[a_.+b_.*x_]^n_,x_Symbol] :=
  -x^m*Coth[a+b*x]*Csch[a+b*x]^(n-2)/(b*(n-1)) -
  m*x^(m-1)*Csch[a+b*x]^(n-2)/(b^2*(n-1)*(n-2)) -
  Dist[(n-2)/(n-1),Int[x^m*Csch[a+b*x]^(n-2),x]] +
  Dist[m*(m-1)/(b^2*(n-1)*(n-2)),Int[x^(m-2)*Csch[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && n>1 && n!=2 && m>1


(* ::Item:: *)
(*Reference: G&R 2.631.2h*)


Int[x_*Csch[a_.+b_.*x_]^n_,x_Symbol] :=
  -Csch[a+b*x]^n/(b^2*n^2) -
  x*Cosh[a+b*x]*Csch[a+b*x]^(n+1)/(b*n) -
  Dist[(n+1)/n,Int[x*Csch[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[n] && n<-1


(* ::Item:: *)
(*Reference: G&R 2.631.2h*)


Int[x_^m_*Csch[a_.+b_.*x_]^n_,x_Symbol] :=
  -m*x^(m-1)*Csch[a+b*x]^n/(b^2*n^2) -
  x^m*Cosh[a+b*x]*Csch[a+b*x]^(n+1)/(b*n) -
  Dist[(n+1)/n,Int[x^m*Csch[a+b*x]^(n+2),x]] +
  Dist[m*(m-1)/(b^2*n^2),Int[x^(m-2)*Csch[a+b*x]^n,x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && n<-1 && m>1


(* ::Subsubsection::Closed:: *)
(*(a+b Csch[c+d x])^n			Powers of constant plus cosecants of linears where a^2+b^2 is zero*)


Int[Sqrt[a_+b_.*Csch[c_.+d_.*x_]],x_Symbol] :=
  2*a*ArcTan[Sqrt[-1-a/b*Csch[c+d*x]]]*Coth[c+d*x]/
		(d*Sqrt[-1-a/b*Csch[c+d*x]]*Sqrt[a+b*Csch[c+d*x]]) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2+b^2]


(* Note: There should be a simpler antiderivative! *)
Int[1/Sqrt[a_+b_.*Csch[c_.+d_.*x_]],x_Symbol] :=
  -(Sqrt[2]*ArcTan[(Sqrt[2]*Sqrt[a])/Sqrt[-a+b*Csch[x]]]+2*ArcTan[Sqrt[-a+b*Csch[x]]/Sqrt[a]])*
		Sqrt[-a+b*Csch[x]]*Sqrt[a+b*Csch[x]]*Tanh[x]/a^(3/2) /;
FreeQ[{a,b,c,d},x] && ZeroQ[a^2+b^2]


(* ::Subsubsection::Closed:: *)
(*(a+b Csch[c+d x]^n)^m		Powers of constant plus powers of cosecants of linears*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If n is an integer, a+b*Csch[z]^n == (b+a*Sinh[z]^n)/Sinh[z]^n*)


Int[(a_+b_.*Csch[v_]^n_.)^m_,x_Symbol] :=
  Int[(b+a*Sinh[v]^n)^m/Sinh[v]^(m*n),x] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && m<0 && n>0


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If n is an integer, a+b*Csch[z]^n == (b+a*Sinh[z]^n)/Sinh[z]^n*)


Int[Sinh[v_]^p_.*(a_+b_.*Csch[v_]^n_.)^m_,x_Symbol] :=
  Int[Sinh[v]^(p-m*n)*(b+a*Sinh[v]^n)^m,x] /;
FreeQ[{a,b},x] && IntegerQ[{m,n,p}] && m<0 && n>0


(* ::Subsubsection::Closed:: *)
(*Csch[a+b x]^m Sech[a+b x]^n		Products of powers of cosecants and secants*)


(* ::Item:: *)
(*Reference: G&R 2.423.49'*)


Int[Csch[a_.+b_.*x_]*Sech[a_.+b_.*x_],x_Symbol] :=
  -Log[Coth[a+b*x]]/b /;
FreeQ[{a,b},x] && NegQ[b]


Int[Csch[a_.+b_.*x_]^m_*Sech[a_.+b_.*x_]^n_,x_Symbol] :=
  -Csch[a+b*x]^(m-1)*Sech[a+b*x]^(n-1)/(b*(m-1)) /;
FreeQ[{a,b,m,n},x] && ZeroQ[m+n-2] && NonzeroQ[m-1] && PosQ[m]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If m and n are integers and m+n is even, Csch[z]^m*Sech[z]^n == -(-1+Coth[z]^2)^((m+n)/2-1)/Coth[z]^n*Coth'[z]*)


Int[Csch[a_.+b_.*x_]^m_*Sech[a_.+b_.*x_]^n_.,x_Symbol] :=
  Dist[-1/b,Subst[Int[Regularize[(-1+x^2)^((m+n)/2-1)/x^n,x],x],x,Coth[a+b*x]]] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && EvenQ[m+n] && 0<n<m
(* FreeQ[{a,b},x] && OddQ[m] && OddQ[n] *)


(* ::Item:: *)
(*Reference: G&R 2.411.3*)


Int[Csch[a_.+b_.*x_]^m_*Sech[a_.+b_.*x_]^n_,x_Symbol] :=
  -Csch[a+b*x]^(m-1)*Sech[a+b*x]^(n+1)/(b*(m-1)) -
  Dist[(n+1)/(m-1),Int[Csch[a+b*x]^(m-2)*Sech[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m>1 && n<-1


(* ::Item:: *)
(*Reference: G&R 2.411.5, CRC 568a, A&S 4.5.86a*)


Int[Csch[a_.+b_.*x_]^m_*Sech[a_.+b_.*x_]^n_.,x_Symbol] :=
  -Csch[a+b*x]^(m-1)*Sech[a+b*x]^(n-1)/(b*(m-1)) -
  Dist[(m+n-2)/(m-1),Int[Csch[a+b*x]^(m-2)*Sech[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m>1 && Not[EvenQ[m+n]] && Not[EvenQ[m] && OddQ[n] && n>1]


(* ::Item:: *)
(*Reference: G&R 2.411.2, CRC 567b, A&S 4.5.85b*)


Int[Csch[a_.+b_.*x_]^m_*Sech[a_.+b_.*x_]^n_.,x_Symbol] :=
  -Csch[a+b*x]^(m+1)*Sech[a+b*x]^(n-1)/(b*(m+n)) -
  Dist[(m+1)/(m+n),Int[Csch[a+b*x]^(m+2)*Sech[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m<-1 && NonzeroQ[m+n]


(* ::Subsubsection::Closed:: *)
(*Csch[a+b x]^m Coth[a+b x]^n		Products of powers of cosecants and cotangents*)
(**)


(* ::Item:: *)
(*Derivation: Power rule for integration*)


Int[Csch[a_.+b_.*x_]^m_.*Coth[a_.+b_.*x_]^n_.,x_Symbol] :=
  -Csch[a+b*x]^m/(b*m) /;
FreeQ[{a,b,m},x] && n===1


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If m is even, Csch[z]^m == -(-1+Coth[z]^2)^((m-2)/2)*Coth'[z]*)


Int[Csch[a_.+b_.*x_]^m_*Coth[a_.+b_.*x_]^n_.,x_Symbol] :=
  Dist[-1/b,Subst[Int[Regularize[x^n*(-1+x^2)^((m-2)/2),x],x],x,Coth[a+b*x]]] /;
FreeQ[{a,b,n},x] && EvenQ[m] && m>2 && Not[OddQ[n] && 0<n<m-1]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is odd, Csch[z]^m*Coth[z]^n == -Csch[z]^(m-1)*(1+Csch[z]^2)^((n-1)/2)*Csch'[z]*)


Int[Csch[a_.+b_.*x_]^m_.*Coth[a_.+b_.*x_]^n_.,x_Symbol] :=
  Dist[-1/b,Subst[Int[Regularize[x^(m-1)*(1+x^2)^((n-1)/2),x],x],x,Csch[a+b*x]]] /;
FreeQ[{a,b,m},x] && OddQ[n] && Not[EvenQ[m] && 0<m<=n+1]


(* ::Item:: *)
(*Reference: G&R 2.411.6, CRC 568b*)


Int[Csch[a_.+b_.*x_]^m_.*Coth[a_.+b_.*x_]^n_,x_Symbol] :=
  -Csch[a+b*x]^(m-2)*Coth[a+b*x]^(n+1)/(b*(n+1)) -
  Dist[(m-2)/(n+1),Int[Csch[a+b*x]^(m-2)*Coth[a+b*x]^(n+2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m>1 && n<-1 && Not[EvenQ[m]]


(* ::Item:: *)
(*Reference: G&R 2.411.1, CRC 567a*)


Int[Csch[a_.+b_.*x_]^m_*Coth[a_.+b_.*x_]^n_,x_Symbol] :=
  -Csch[a+b*x]^m*Coth[a+b*x]^(n-1)/(b*m) -
  Dist[(n-1)/m,Int[Csch[a+b*x]^(m+2)*Coth[a+b*x]^(n-2),x]] /;
FreeQ[{a,b},x] && RationalQ[{m,n}] && m<-1 && n>1 && Not[EvenQ[m]]


(* ::Item:: *)
(*Reference: G&R 2.411.2, CRC 567b*)


Int[Csch[a_.+b_.*x_]^m_.*Coth[a_.+b_.*x_]^n_,x_Symbol] :=
  -Csch[a+b*x]^m*Coth[a+b*x]^(n+1)/(b*m) /;
FreeQ[{a,b,m,n},x] && ZeroQ[m+n+1]


Int[Csch[a_.+b_.*x_]^m_*Coth[a_.+b_.*x_]^n_,x_Symbol] :=
  -Csch[a+b*x]^m*Coth[a+b*x]^(n+1)/(b*m) -
  Dist[(m+n+1)/m,Int[Csch[a+b*x]^(m+2)*Coth[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m<-1 && Not[EvenQ[m]]


(* ::Item:: *)
(*Reference: G&R 2.411.5, CRC 568a*)


Int[Csch[a_.+b_.*x_]^m_*Coth[a_.+b_.*x_]^n_,x_Symbol] :=
  -Csch[a+b*x]^(m-2)*Coth[a+b*x]^(n+1)/(b*(m+n-1)) -
  Dist[(m-2)/(m+n-1),Int[Csch[a+b*x]^(m-2)*Coth[a+b*x]^n,x]] /;
FreeQ[{a,b,n},x] && RationalQ[m] && m>1 && NonzeroQ[m+n-1] && Not[EvenQ[m]] && Not[OddQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.411.4*)


Int[Csch[a_.+b_.*x_]^m_.*Coth[a_.+b_.*x_]^n_,x_Symbol] :=
  -Csch[a+b*x]^m*Coth[a+b*x]^(n-1)/(b*(m+n-1)) +
  Dist[(n-1)/(m+n-1),Int[Csch[a+b*x]^m*Coth[a+b*x]^(n-2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n>1 && NonzeroQ[m+n-1] && Not[EvenQ[m]] && Not[OddQ[n]]


(* ::Item:: *)
(*Reference: G&R 2.411.3*)


Int[Csch[a_.+b_.*x_]^m_.*Coth[a_.+b_.*x_]^n_,x_Symbol] :=
  Csch[a+b*x]^m*Coth[a+b*x]^(n+1)/(b*(n+1)) +
  Dist[(m+n+1)/(n+1),Int[Csch[a+b*x]^m*Coth[a+b*x]^(n+2),x]] /;
FreeQ[{a,b,m},x] && RationalQ[n] && n<-1 && Not[EvenQ[m]]


(* ::Subsubsection::Closed:: *)
(*x^m Csch[a+b x^n]^p Cosh[a+b x^n]	Products of monomials, cosines and powers of cosecants of binomials*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Csch[a_.+b_.*x_^n_.]^p_*Cosh[a_.+b_.*x_^n_.],x_Symbol] :=
  -x^(m-n+1)*Csch[a+b*x^n]^(p-1)/(b*n*(p-1)) +
  Dist[(m-n+1)/(b*n*(p-1)),Int[x^(m-n)*Csch[a+b*x^n]^(p-1),x]] /;
FreeQ[{a,b,p},x] && RationalQ[m] && IntegerQ[n] && m-n>=0 && NonzeroQ[p-1]


(* ::Subsubsection::Closed:: *)
(*x^m Csch[a+b x^n]^p Coth[a+b x^n]	Products of monomials, cotangents and powers of cosecants of binomials*)
(**)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*Csch[a_.+b_.*x_^n_.]^p_.*Coth[a_.+b_.*x_^n_.]^q_.,x_Symbol] :=
  -x^(m-n+1)*Csch[a+b*x^n]^p/(b*n*p) +
  Dist[(m-n+1)/(b*n*p),Int[x^(m-n)*Csch[a+b*x^n]^p,x]] /;
FreeQ[{a,b,p},x] && RationalQ[m] && IntegerQ[n] && m-n>=0 && q===1 (* Required so InputForm is matchable *)


(* ::Subsubsection::Closed:: *)
(*Csch[a+b Log[c x^n]]^p		Powers of cosecants of logarithms*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Csch[b*Log[c*x^n]] == 2 / ((c*x^n)^b - 1/(c*x^n)^b)*)


Int[Csch[b_.*Log[c_.*x_^n_.]]^p_.,x_Symbol] :=
  Int[(2/((c*x^n)^b - 1/(c*x^n)^b))^p,x] /;
FreeQ[c,x] && RationalQ[{b,n,p}]


Int[Csch[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  -x*Coth[a+b*Log[c*x^n]]*Csch[a+b*Log[c*x^n]]^(p-2)/(b*n*(p-1)) -
  x*Csch[a+b*Log[c*x^n]]^(p-2)/(b^2*n^2*(p-1)*(p-2)) +
  Dist[(1-b^2*n^2*(p-2)^2)/(b^2*n^2*(p-1)*(p-2)),Int[Csch[a+b*Log[c*x^n]]^(p-2),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p>1 && p!=2


Int[Csch[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  x*Csch[a+b*Log[c*x^n]]^p/(1-b^2*n^2*p^2) +
  b*n*p*x*Cosh[a+b*Log[c*x^n]]*Csch[a+b*Log[c*x^n]]^(p+1)/(1-b^2*n^2*p^2) +
  Dist[b^2*n^2*p*(p+1)/(1-b^2*n^2*p^2),Int[Csch[a+b*Log[c*x^n]]^(p+2),x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p<-1 && NonzeroQ[1-b^2*n^2*p^2]


(* ::Subsubsection::Closed:: *)
(*x^m Csch[a+b Log[c x^n]]^p		Products of monomials and powers of cosecants of logarithms*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Csch[b*Log[c*x^n]] == 2 / ((c*x^n)^b - 1/(c*x^n)^b)*)


Int[x_^m_.*Csch[b_.*Log[c_.*x_^n_.]]^p_.,x_Symbol] :=
  Int[x^m*(2/((c*x^n)^b - 1/(c*x^n)^b))^p,x] /;
FreeQ[c,x] && RationalQ[{b,m,n,p}]


Int[Csch[a_.+b_.*Log[c_.*x_^n_.]]^2/x_,x_Symbol] :=
  -Coth[a+b*Log[c*x^n]]/(b*n) /;
FreeQ[{a,b,c,n},x]


Int[Csch[a_.+b_.*Log[c_.*x_^n_.]]^p_/x_,x_Symbol] :=
  -Coth[a+b*Log[c*x^n]]*Csch[a+b*Log[c*x^n]]^(p-2)/(b*n*(p-1)) -
  Dist[(p-2)/(p-1),Int[Csch[a+b*Log[c*x^n]]^(p-2)/x,x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p>1


Int[Csch[a_.+b_.*Log[c_.*x_^n_.]]^p_/x_,x_Symbol] :=
  -Cosh[a+b*Log[c*x^n]]*Csch[a+b*Log[c*x^n]]^(p+1)/(b*n*p) -
  Dist[(p+1)/p,Int[Csch[a+b*Log[c*x^n]]^(p+2)/x,x]] /;
FreeQ[{a,b,c,n},x] && RationalQ[p] && p<-1


Int[x_^m_.*Csch[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  -x^(m+1)*Coth[a+b*Log[c*x^n]]*Csch[a+b*Log[c*x^n]]^(p-2)/(b*n*(p-1)) -
  (m+1)*x^(m+1)*Csch[a+b*Log[c*x^n]]^(p-2)/(b^2*n^2*(p-1)*(p-2)) -
  Dist[(b^2*n^2*(p-2)^2-(m+1)^2)/(b^2*n^2*(p-1)*(p-2)),Int[x^m*Csch[a+b*Log[c*x^n]]^(p-2),x]] /;
FreeQ[{a,b,c,m,n},x] && RationalQ[p] && p>1 && p!=2


Int[x_^m_.*Csch[a_.+b_.*Log[c_.*x_^n_.]]^p_,x_Symbol] :=
  -(m+1)*x^(m+1)*Csch[a+b*Log[c*x^n]]^p/(b^2*n^2*p^2-(m+1)^2) -
  b*n*p*x^(m+1)*Cosh[a+b*Log[c*x^n]]*Csch[a+b*Log[c*x^n]]^(p+1)/(b^2*n^2*p^2-(m+1)^2) -
  Dist[b^2*n^2*p*(p+1)/(b^2*n^2*p^2-(m+1)^2),Int[x^m*Csch[a+b*Log[c*x^n]]^(p+2),x]] /;
FreeQ[{a,b,c,m,n},x] && RationalQ[p] && p<-1 && NonzeroQ[b^2*n^2*p^2-(m+1)^2]


(* ::Subsection::Closed:: *)
(*Powers of sums of Hyperbolic Trig Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*(a Cosh[c+d x] + b Sinh[c+d x])^n	Powers of sums of sines and cosines*)


Int[(a_.*Cosh[c_.+d_.*x_]+b_.*Sinh[c_.+d_.*x_])^n_,x_Symbol] :=
  a*(a*Cosh[c+d*x]+b*Sinh[c+d*x])^n/(b*d*n) /;
FreeQ[{a,b,c,d,n},x] && ZeroQ[a^2-b^2]


Int[1/(a_.*Cosh[c_.+d_.*x_]+b_.*Sinh[c_.+d_.*x_])^2,x_Symbol] :=
  Sinh[c+d*x]/(a*d*(a*Cosh[c+d*x]+b*Sinh[c+d*x])) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2]


(* ::Item:: *)
(*Basis: a*Cosh[z]+b*Sinh[z] == -I*Sqrt[a^2-b^2]*Sinh[z+I*ArcTan[I*b,a]]*)


Int[Sqrt[a_.*Cosh[c_.+d_.*x_]+b_.*Sinh[c_.+d_.*x_]],x_Symbol] :=
  2*I*EllipticE[(Pi/2-I*(c+d*x+I*ArcTan[I*b,a]))/2,2]*
  Sqrt[a*Cosh[c+d*x]+b*Sinh[c+d*x]]/
  (d*Sqrt[-(a*Cosh[c+d*x]+b*Sinh[c+d*x])/Sqrt[a^2-b^2]]) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2]


(* ::Item:: *)
(*Basis: a*Cosh[z]+b*Sinh[z] == -I*Sqrt[a^2-b^2]*Sinh[z+I*ArcTan[I*b,a]]*)


Int[1/Sqrt[a_.*Cosh[c_.+d_.*x_]+b_.*Sinh[c_.+d_.*x_]],x_Symbol] :=
  2*I*EllipticF[(Pi/2-I*(c+d*x+I*ArcTan[I*b,a]))/2,2]*
  Sqrt[-(a*Cosh[c+d*x]+b*Sinh[c+d*x])/Sqrt[a^2-b^2]]/
  (d*Sqrt[a*Cosh[c+d*x]+b*Sinh[c+d*x]]) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2]


(* ::Item::Closed:: *)
(*Reference: G&R 2.449'*)


(* ::Item:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is odd, (a*Cosh[z]+b*Sinh[z])^n == (a^2-b^2+u^2)^((n-1)/2)*D[u,z] where u = b*Cosh[z]+a*Sinh[z]*)


(* Note: For odd n<-1, might as well stay in the trig world using 2nd rule below ??? *)
Int[(a_.*Cosh[c_.+d_.*x_]+b_.*Sinh[c_.+d_.*x_])^n_,x_Symbol] :=
  Dist[1/d,Subst[Int[Regularize[(a^2-b^2+x^2)^((n-1)/2),x],x],x,b*Cosh[c+d*x]+a*Sinh[c+d*x]]] /;
FreeQ[{a,b,c,d},x] && OddQ[n] && n>=-1 && NonzeroQ[a^2-b^2]


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[(a_.*Cosh[c_.+d_.*x_]+b_.*Sinh[c_.+d_.*x_])^n_,x_Symbol] :=
  (b*Cosh[c+d*x]+a*Sinh[c+d*x])*(a*Cosh[c+d*x]+b*Sinh[c+d*x])^(n-1)/(d*n) +
  Dist[(n-1)*(a^2-b^2)/n,Int[(a*Cosh[c+d*x]+b*Sinh[c+d*x])^(n-2),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n>1 && NonzeroQ[a^2-b^2] && Not[OddQ[n]]


(* ::Item:: *)
(*Derivation: Integration by parts with a double-back flip*)


Int[(a_.*Cosh[c_.+d_.*x_]+b_.*Sinh[c_.+d_.*x_])^n_,x_Symbol] :=
  -(b*Cosh[c+d*x]+a*Sinh[c+d*x])*(a*Cosh[c+d*x]+b*Sinh[c+d*x])^(n+1)/(d*(n+1)*(a^2-b^2)) +
  Dist[(n+2)/((n+1)*(a^2-b^2)),Int[(a*Cosh[c+d*x]+b*Sinh[c+d*x])^(n+2),x]] /;
FreeQ[{a,b,c,d},x] && RationalQ[n] && n<-1 && NonzeroQ[a^2-b^2]


(* ::Subsubsection::Closed:: *)
(*(a Csch[c+d x] + a Sinh[c+d x])^n	where a-b is zero*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Csch[z]+Sinh[z] == Cosh[z]*Coth[z]*)


Int[(a_.*Csch[c_.+d_.*x_]+b_.*Sinh[c_.+d_.*x_])^n_,x_Symbol] :=
  Int[(a*Cosh[c+d*x]*Coth[c+d*x])^n,x] /;
FreeQ[{a,b,c,d,n},x] && ZeroQ[a-b]


(* ::Subsubsection::Closed:: *)
(*(a Sech[c+d x] + a Cosh[c+d x])^n	where a+b is zero*)
(**)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Sech[z]-Cosh[z] == -Sinh[z]*Tanh[z]*)


Int[(a_.*Sech[c_.+d_.*x_]+b_.*Cosh[c_.+d_.*x_])^n_,x_Symbol] :=
  Int[(-a*Sinh[c+d*x]*Tanh[c+d*x])^n,x] /;
FreeQ[{a,b,c,d,n},x] && ZeroQ[a+b]


(* ::Subsection::Closed:: *)
(*Rational functions of Hyperbolic Trig Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*u Hyper[c+d x]^n / (a Cosh[c+d x]+b Sinh[c+d x])	where a^2-b^2 is nonzero*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sinh[z]^2/(a*Cosh[z]+b*Sinh[z]) == -b/(a^2-b^2)*Sinh[z] + a/(a^2-b^2)*Cosh[z] - a^2/(a^2-b^2)/(a*Cosh[z]+b*Sinh[z])*)


Int[u_.*Sinh[c_.+d_.*x_]^n_/(a_.*Cosh[c_.+d_.*x_]+b_.*Sinh[c_.+d_.*x_]),x_Symbol] :=
  Dist[-b/(a^2-b^2),Int[u*Sinh[c+d*x]^(n-1),x]] +
  Dist[a/(a^2-b^2),Int[u*Sinh[c+d*x]^(n-2)*Cosh[c+d*x],x]] -
  Dist[a^2/(a^2-b^2),Int[u*Sinh[c+d*x]^(n-2)/(a*Cosh[c+d*x]+b*Sinh[c+d*x]),x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2] && IntegerQ[n] && n>1


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cosh[z]^2/(a*Cosh[z]+b*Sinh[z]) == a/(a^2-b^2)*Cosh[z] - b/(a^2-b^2)*Sinh[z] - b^2/(a^2-b^2)/(a*Cosh[z]+b*Sinh[z])*)


Int[u_.*Cosh[c_.+d_.*x_]^n_/(a_.*Cosh[c_.+d_.*x_]+b_.*Sinh[c_.+d_.*x_]),x_Symbol] :=
  Dist[a/(a^2-b^2),Int[u*Cosh[c+d*x]^(n-1),x]] -
  Dist[b/(a^2-b^2),Int[u*Cosh[c+d*x]^(n-2)*Sinh[c+d*x],x]] -
  Dist[b^2/(a^2-b^2),Int[u*Cosh[c+d*x]^(n-2)/(a*Cosh[c+d*x]+b*Sinh[c+d*x]),x]] /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2] && IntegerQ[n] && n>1


(* ::Subsubsection::Closed:: *)
(*(a+b Cosh[d+e x]+c Sinh[d+e x])^n			where a^2-b^2+c^2 is zero*)


(* ::Item:: *)
(*Reference: G&R 2.451.4d*)


Int[1/(a_+b_.*Cosh[d_.+e_.*x_]+c_.*Sinh[d_.+e_.*x_]),x_Symbol] :=
  -2/(e*(c-(a-b)*Tanh[(d+e*x)/2])) /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[a^2-b^2+c^2]


Int[Sqrt[a_+b_.*Cosh[d_.+e_.*x_]+c_.*Sinh[d_.+e_.*x_]],x_Symbol] :=
  2*(c*Cosh[d+e*x]+b*Sinh[d+e*x])/(e*Sqrt[a+b*Cosh[d+e*x]+c*Sinh[d+e*x]]) /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[a^2-b^2+c^2]


Int[(a_+b_.*Cosh[d_.+e_.*x_]+c_.*Sinh[d_.+e_.*x_])^n_,x_Symbol] :=
  (c*Cosh[d+e*x]+b*Sinh[d+e*x])*(a+b*Cosh[d+e*x]+c*Sinh[d+e*x])^(n-1)/(e*n) +
  Dist[a*(2*n-1)/n,Int[(a+b*Cosh[d+e*x]+c*Sinh[d+e*x])^(n-1),x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[n] && n>1 && ZeroQ[a^2-b^2+c^2]


Int[(a_+b_.*Cosh[d_.+e_.*x_]+c_.*Sinh[d_.+e_.*x_])^n_,x_Symbol] :=
  -(c*Cosh[d+e*x]+b*Sinh[d+e*x])*(a+b*Cosh[d+e*x]+c*Sinh[d+e*x])^n/(a*e*(2*n+1)) +
  Dist[(n+1)/(a*(2*n+1)),Int[(a+b*Cosh[d+e*x]+c*Sinh[d+e*x])^(n+1),x]] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[n] && n<-1 && ZeroQ[a^2-b^2+c^2]


(* ::Subsubsection::Closed:: *)
(*(a+b Cosh[d+e x]+c Sinh[d+e x])^n			where a^2-b^2+c^2 is nonzero*)


(* ::Item:: *)
(*Reference: G&R 2.451.4c*)


(* Note: The following two rules should be unified! *)
Int[1/(a_+b_.*Cosh[d_.+e_.*x_]+c_.*Sinh[d_.+e_.*x_]),x_Symbol] :=
  Log[a+c*Tanh[(d+e*x)/2]]/(c*e) /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[a-b]


Int[1/(a_+b_.*Cosh[d_.+e_.*x_]+c_.*Sinh[d_.+e_.*x_]),x_Symbol] :=
  -Log[a-c*Coth[(d+e*x)/2]]/(c*e) /;
FreeQ[{a,b,c,d,e},x] && ZeroQ[a+b]


(* ::Item:: *)
(*Reference: G&R 2.451.4a*)


Int[1/(a_+b_.*Cosh[d_.+e_.*x_]+c_.*Sinh[d_.+e_.*x_]),x_Symbol] :=
  2*ArcTan[(c-(a-b)*Tanh[(d+e*x)/2])/Rt[-a^2+b^2-c^2,2]]/(e*Rt[-a^2+b^2-c^2,2]) /;
FreeQ[{a,b,c,d,e},x] && NonzeroQ[a^2-b^2] && NegQ[a^2-b^2+c^2]


(* ::Item:: *)
(*Reference: G&R 2.451.4b'*)


Int[1/(a_+b_.*Cosh[d_.+e_.*x_]+c_.*Sinh[d_.+e_.*x_]),x_Symbol] :=
  -2*ArcTanh[(c-(a-b)*Tanh[(d+e*x)/2])/Rt[a^2-b^2+c^2,2]]/(e*Rt[a^2-b^2+c^2,2]) /;
FreeQ[{a,b,c,d,e},x] && NonzeroQ[a^2-b^2] && PosQ[a^2-b^2+c^2]


(* ::Item:: *)
(*Basis: a+b*Cosh[z]+c*Sinh[z] == a-I*Sqrt[b^2-c^2]*Sinh[z+I*ArcTan[I*c,b]]*)


Int[Sqrt[a_.+b_.*Cosh[d_.+e_.*x_]+c_.*Sinh[d_.+e_.*x_]],x_Symbol] :=
  2*I*EllipticE[(Pi/2-I*(d+e*x+I*ArcTan[I*c,b]))/2,2/(1-a/Sqrt[b^2-c^2])]*
  Sqrt[a+b*Cosh[d+e*x]+c*Sinh[d+e*x]]/
  (e*Sqrt[(a+b*Cosh[d+e*x]+c*Sinh[d+e*x])/(a-Sqrt[b^2-c^2])]) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[a^2-b^2+c^2]


(* ::Item:: *)
(*Basis: a+b*Cosh[z]+c*Sinh[z] == a-I*Sqrt[b^2-c^2]*Sinh[z+I*ArcTan[I*c,b]]*)


Int[1/Sqrt[a_.+b_.*Cosh[d_.+e_.*x_]+c_.*Sinh[d_.+e_.*x_]],x_Symbol] :=
  2*I*EllipticF[(Pi/2-I*(d+e*x+I*ArcTan[I*c,b]))/2,2/(1-a/Sqrt[b^2-c^2])]*
  Sqrt[(a+b*Cosh[d+e*x]+c*Sinh[d+e*x])/(a-Sqrt[b^2-c^2])]/
  (e*Sqrt[a+b*Cosh[d+e*x]+c*Sinh[d+e*x]]) /;
FreeQ[{a,b,c,d,e},x] && NonzeroQ[a^2-b^2+c^2]


(* ::Item:: *)
(*Reference: G&R 2.451.1*)


Int[(a_+b_.*Cosh[d_.+e_.*x_]+c_.*Sinh[d_.+e_.*x_])^n_,x_Symbol] :=
  (c*Cosh[d+e*x]+b*Sinh[d+e*x])*(a+b*Cosh[d+e*x]+c*Sinh[d+e*x])^(n+1)/(e*(n+1)*(a^2-b^2+c^2)) +
  1/((n+1)*(a^2-b^2+c^2))*
    Int[((n+1)*a-(n+2)*b*Cosh[d+e*x]-(n+2)*c*Sinh[d+e*x])*(a+b*Cosh[d+e*x]+c*Sinh[d+e*x])^(n+1),x] /;
FreeQ[{a,b,c,d,e},x] && RationalQ[n] && n<-1 && NonzeroQ[a^2-b^2+c^2]


(* ::Subsubsection::Closed:: *)
(*(A+B Cosh[d+e x]+C Sinh[d+e x]) (a+b Cosh[d+e x]+c Sinh[d+e x])^n	where a^2-b^2+c^2 is nonzero*)
(**)


(* ::Item:: *)
(*Reference: G&R 2.451.2*)


Int[(A_.+C_.*Sinh[d_.+e_.*x_])/(a_.+b_.*Cosh[d_.+e_.*x_]+c_.*Sinh[d_.+e_.*x_]),x_Symbol] :=
  b*C*Log[a+b*Cosh[d+e*x]+c*Sinh[d+e*x]]/(e*(b^2-c^2)) -
  c*C*(d+e*x)/(e*(b^2-c^2)) +
  Dist[(A+a*c*C/(b^2-c^2)),Int[1/(a+b*Cosh[d+e*x]+c*Sinh[d+e*x]),x]] /;
FreeQ[{a,b,c,d,e,A,C},x] && NonzeroQ[b^2-c^2] && NonzeroQ[A+a*c*C/(b^2-c^2)]


(* ::Item:: *)
(*Reference: G&R 2.451.2*)


Int[(A_.+B_.*Cosh[d_.+e_.*x_])/(a_.+b_.*Cosh[d_.+e_.*x_]+c_.*Sinh[d_.+e_.*x_]),x_Symbol] :=
  -c*B*Log[a+b*Cosh[d+e*x]+c*Sinh[d+e*x]]/(e*(b^2-c^2)) +
  b*B*(d+e*x)/(e*(b^2-c^2)) +
  Dist[(A-a*b*B/(b^2-c^2)),Int[1/(a+b*Cosh[d+e*x]+c*Sinh[d+e*x]),x]] /;
FreeQ[{a,b,c,d,e,A,B},x] && NonzeroQ[b^2-c^2] && NonzeroQ[A-a*b*B/(b^2-c^2)]


(* ::Item:: *)
(*Reference: G&R 2.451.2*)


Int[(A_.+B_.*Cosh[d_.+e_.*x_]+C_.*Sinh[d_.+e_.*x_])/(a_.+b_.*Cosh[d_.+e_.*x_]+c_.*Sinh[d_.+e_.*x_]),x_Symbol] :=
  -(c*B-b*C)*Log[a+b*Cosh[d+e*x]+c*Sinh[d+e*x]]/(e*(b^2-c^2)) +
  (b*B-c*C)*(d+e*x)/(e*(b^2-c^2)) +
  Dist[(A-a*(b*B-c*C)/(b^2-c^2)),Int[1/(a+b*Cosh[d+e*x]+c*Sinh[d+e*x]),x]] /;
FreeQ[{a,b,c,d,e,A,B,C},x] && NonzeroQ[b^2-c^2] && NonzeroQ[A-a*(b*B-c*C)/(b^2-c^2)]


(* ::Item:: *)
(*Reference: G&R 2.451.1*)


Int[(A_.+C_.*Sinh[d_.+e_.*x_])*(a_.+b_.*Cosh[d_.+e_.*x_]+c_.*Sinh[d_.+e_.*x_])^n_,x_Symbol] :=
  (-b*C+(c*A-a*C)*Cosh[d+e*x]+b*A*Sinh[d+e*x])*(a+b*Cosh[d+e*x]+c*Sinh[d+e*x])^(n+1)/
    (e*(n+1)*(a^2-b^2+c^2)) +
  Dist[1/((n+1)*(a^2-b^2+c^2)),
    Int[((n+1)*(a*A+c*C)-(n+2)*b*A*Cosh[d+e*x]-(n+2)*(c*A-a*C)*Sinh[d+e*x])*
      (a+b*Cosh[d+e*x]+c*Sinh[d+e*x])^(n+1),x]] /;
FreeQ[{a,b,c,d,e,A,C},x] && RationalQ[n] && n<-1 && NonzeroQ[a^2-b^2+c^2]


(* ::Item:: *)
(*Reference: G&R 2.451.1*)


Int[(A_.+B_.*Cosh[d_.+e_.*x_])*(a_.+b_.*Cosh[d_.+e_.*x_]+c_.*Sinh[d_.+e_.*x_])^n_,x_Symbol] :=
  (c*B+c*A*Cosh[d+e*x]+(b*A-a*B)*Sinh[d+e*x])*(a+b*Cosh[d+e*x]+c*Sinh[d+e*x])^(n+1)/
    (e*(n+1)*(a^2-b^2+c^2)) +
  Dist[1/((n+1)*(a^2-b^2+c^2)),
    Int[((n+1)*(a*A-b*B)-(n+2)*(b*A-a*B)*Cosh[d+e*x]-(n+2)*c*A*Sinh[d+e*x])*
      (a+b*Cosh[d+e*x]+c*Sinh[d+e*x])^(n+1),x]] /;
FreeQ[{a,b,c,d,e,A,B},x] && RationalQ[n] && n<-1 && NonzeroQ[a^2-b^2+c^2]


(* ::Item:: *)
(*Reference: G&R 2.451.1*)


Int[(A_.+B_.*Cosh[d_.+e_.*x_]+C_.*Sinh[d_.+e_.*x_])*(a_.+b_.*Cosh[d_.+e_.*x_]+c_.*Sinh[d_.+e_.*x_])^n_,x_Symbol] :=
  (c*B-b*C+(c*A-a*C)*Cosh[d+e*x]+(b*A-a*B)*Sinh[d+e*x])*(a+b*Cosh[d+e*x]+c*Sinh[d+e*x])^(n+1)/
    (e*(n+1)*(a^2-b^2+c^2)) +
  Dist[1/((n+1)*(a^2-b^2+c^2)),
    Int[((n+1)*(a*A-b*B+c*C)-(n+2)*(b*A-a*B)*Cosh[d+e*x]-(n+2)*(c*A-a*C)*Sinh[d+e*x])*
      (a+b*Cosh[d+e*x]+c*Sinh[d+e*x])^(n+1),x]] /;
FreeQ[{a,b,c,d,e,A,B,C},x] && RationalQ[n] && n<-1 && NonzeroQ[a^2-b^2+c^2]


(* ::Subsubsection::Closed:: *)
(*u (a+b Tanh[c+d x])^n*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: (a+b*Tanh[z])/Sech[z] == a*Cosh[z] + b*Sinh[z]*)


Int[Sech[v_]^m_.*(a_+b_.*Tanh[v_])^n_., x_Symbol] :=
  Int[(a*Cosh[v]+b*Sinh[v])^n,x] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && m+n==0 


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: (a+b*Coth[z])/Csch[z] == b*Cosh[z] + a*Sinh[z]*)


Int[Csch[v_]^m_.*(a_+b_.*Coth[v_])^n_., x_Symbol] :=
  Int[(b*Cosh[v]+a*Sinh[v])^n,x] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && m+n==0 


(* ::Subsection::Closed:: *)
(*Exponential and Hyperbolic Trig Function Integration Rules*)


(* ::Subsubsection::Closed:: *)
(*Exp[a + b x] Sinh[c + d x]^n		Products of exponentials and powers of sines of linears*)


(* ::Item:: *)
(*Reference: CRC 533, A&S 4.3.136*)


Int[E^(a_.+b_.*x_)*Sinh[c_.+d_.*x_],x_Symbol] :=
  -d*E^(a+b*x)*Cosh[c+d*x]/(b^2-d^2) + b*E^(a+b*x)*Sinh[c+d*x]/(b^2-d^2) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[b^2-d^2]


(* ::Item:: *)
(*Reference: CRC 542, A&S 4.3.138*)


Int[E^(a_.+b_.*x_)*Sinh[c_.+d_.*x_]^n_,x_Symbol] :=
  -d*n*E^(a+b*x)*Cosh[c+d*x]*Sinh[c+d*x]^(n-1)/(b^2-d^2*n^2) + 
  b*E^(a+b*x)*Sinh[c+d*x]^n/(b^2-d^2*n^2) + 
  Dist[n*(n-1)*d^2/(b^2-d^2*n^2),Int[E^(a+b*x)*Sinh[c+d*x]^(n-2),x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n>1


(* ::Subsubsection::Closed:: *)
(*Exp[a + b x] Cosh[c + d x]^n		Products of exponentials and powers of cosines of linears*)


(* ::Item:: *)
(*Reference: CRC 538, A&S 4.3.137*)


Int[E^(a_.+b_.*x_)*Cosh[c_.+d_.*x_],x_Symbol] :=
  b*E^(a+b*x)*Cosh[c+d*x]/(b^2-d^2) - d*E^(a+b*x)*Sinh[c+d*x]/(b^2-d^2) /;
FreeQ[{a,b,c,d},x] && NonzeroQ[b^2-d^2]


(* ::Item:: *)
(*Reference: CRC 543, A&S 4.3.139*)


Int[E^(a_.+b_.*x_)*Cosh[c_.+d_.*x_]^n_,x_Symbol] :=
  b*E^(a+b*x)*Cosh[c+d*x]^n/(b^2-d^2*n^2) - 
  d*n*E^(a+b*x)*Cosh[c+d*x]^(n-1)*Sinh[c+d*x]/(b^2-d^2*n^2) - 
  Dist[n*(n-1)*d^2/(b^2-d^2*n^2),Int[E^(a+b*x)*Cosh[c+d*x]^(n-2),x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n>1


(* ::Subsubsection::Closed:: *)
(*Exp[a + b x] Sech[c + d x]^n		Products of exponentials and powers of secants of linears*)


(* ::Item:: *)
(*Reference: CRC 552*)


Int[E^(a_.+b_.*x_)*Sech[c_.+d_.*x_]^n_,x_Symbol] :=
  b*E^(a+b*x)*Sech[c+d*x]^(n-2)/(d^2*(n-2)*(n-1)) + 
  E^(a+b*x)*Sech[c+d*x]^(n-1)*Sinh[c+d*x]/(d*(n-1)) -
  Dist[(b^2-d^2*(n-2)^2)/(d^2*(n-1)*(n-2)),Int[E^(a+b*x)*Sech[c+d*x]^(n-2),x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n>1 && n!=2


(* ::Subsubsection::Closed:: *)
(*Exp[a + b x] Csch[c + d x]^n		Products of exponentials and powers of cosecants of linears*)
(**)


(* ::Item:: *)
(*Reference: CRC 551*)


Int[E^(a_.+b_.*x_)*Csch[c_.+d_.*x_]^n_,x_Symbol] :=
  -b*E^(a+b*x)*Csch[c+d*x]^(n-2)/(d^2*(n-1)*(n-2)) - 
  E^(a+b*x)*Cosh[c+d*x]*Csch[c+d*x]^(n-1)/(d*(n-1)) +
  Dist[(b^2-d^2*(n-2)^2)/(d^2*(n-1)*(n-2)),Int[E^(a+b*x)*Csch[c+d*x]^(n-2),x]] /;
FreeQ[{a,b,c,d},x] && FractionQ[n] && n>1 && n!=2


(* ::Subsubsection::Closed:: *)
(*x^m Exp[a + b x] Sinh[c + d x]^n	Products of monomials, exponentials and powers of sines of linears*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*E^(a_.+b_.*x_)*Sinh[c_.+d_.*x_]^n_.,x_Symbol] :=
  Module[{u=Block[{ShowSteps=False,StepCounter=Null}, Int[E^(a+b*x)*Sinh[c+d*x]^n,x]]},
  x^m*u - 
  Dist[m,Int[x^(m-1)*u,x]]] /;
FreeQ[{a,b,c,d},x] && RationalQ[m] && IntegerQ[n] && m>0 && n>0


(* ::Subsubsection::Closed:: *)
(*x^m Exp[a + b x] Cosh[c + d x]^n	Products of exponentials and powers of cosines of linears*)


(* ::Item:: *)
(*Derivation: Integration by parts*)


Int[x_^m_.*E^(a_.+b_.*x_)*Cosh[c_.+d_.*x_]^n_.,x_Symbol] :=
  Module[{u=Block[{ShowSteps=False,StepCounter=Null}, Int[E^(a+b*x)*Cosh[c+d*x]^n,x]]},
  x^m*u - 
  Dist[m,Int[x^(m-1)*u,x]]] /;
FreeQ[{a,b,c,d},x] && RationalQ[m] && IntegerQ[n] && m>0 && n>0


(* ::Subsubsection::Closed:: *)
(*u f^v Hyper[w]				Products of exponentials and hyperbolic functions of polynomials*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sinh[z] == 1/2*(E^z - 1/E^z) *)


Int[f_^v_*Sinh[w_],x_Symbol] :=
  Dist[1/2,Int[f^v*E^w,x]] - 
  Dist[1/2,Int[f^v/E^w,x]] /;
FreeQ[f,x] && PolynomialQ[v,x] && Exponent[v,x]<=2 && PolynomialQ[w,x] && Exponent[w,x]<=2


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Sinh[z] == 1/2*(E^z - 1/E^z) *)


Int[f_^v_*Sinh[w_]^n_,x_Symbol] :=
  Dist[1/2^n,Int[f^v*(E^w-1/E^w)^n,x]] /;
FreeQ[f,x] && IntegerQ[n] && n>0 && PolynomialQ[v,x] && Exponent[v,x]<=2 && PolynomialQ[w,x] && Exponent[w,x]<=2


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cosh[z] == 1/2*(E^z + 1/E^z)*)


Int[f_^v_*Cosh[w_],x_Symbol] :=
  Dist[1/2,Int[f^v*E^w,x]] + 
  Dist[1/2,Int[f^v/E^w,x]] /;
FreeQ[f,x] && PolynomialQ[v,x] && Exponent[v,x]<=2 && PolynomialQ[w,x] && Exponent[w,x]<=2


(* ::Item::Closed:: *)
(*Derivation: Algebraic expansion*)


(* ::Item:: *)
(*Basis: Cosh[z] == 1/2*(E^z + 1/E^z)*)


Int[f_^v_*Cosh[w_]^n_,x_Symbol] :=
  Dist[1/2^n,Int[f^v*(E^w+1/E^w)^n,x]] /;
FreeQ[f,x] && IntegerQ[n] && n>0 && PolynomialQ[v,x] && Exponent[v,x]<=2 && PolynomialQ[w,x] && Exponent[w,x]<=2


(* ::Subsection::Closed:: *)
(*Hyperbolic Function Simplification Rules*)


(* ::Subsubsection::Closed:: *)
(*u (a-a Hyper[v]^2)^n*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1+Sinh[z]^2 == Cosh[z]^2*)


Int[u_.*(a_+b_.*Sinh[v_]^2)^n_.,x_Symbol] :=
  Dist[a^n,Int[u*Cosh[v]^(2*n),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && ZeroQ[a-b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: -1 + Cosh[z]^2 == Sinh[z]^2*)


Int[u_.*(a_+b_.*Cosh[v_]^2)^n_.,x_Symbol] :=
  Dist[b^n,Int[u*Sinh[v]^(2*n),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && ZeroQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1 - Tanh[z]^2 == Sech[z]^2*)


Int[u_.*(a_+b_.*Tanh[v_]^2)^n_.,x_Symbol] :=
  Dist[a^n,Int[u*Sech[v]^(2*n),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && ZeroQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: -1 + Coth[z]^2 == Csch[z]^2*)


Int[u_.*(a_+b_.*Coth[v_]^2)^n_.,x_Symbol] :=
  Dist[b^n,Int[u*Csch[v]^(2*n),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && ZeroQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1 - Sech[z]^2 == Tanh[z]^2*)


Int[u_.*(a_+b_.*Sech[v_]^2)^n_.,x_Symbol] :=
  Dist[a^n,Int[u*Tanh[v]^(2*n),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && ZeroQ[a+b]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: 1 + Csch[z]^2 == Coth[z]^2*)


Int[u_.*(a_+b_.*Csch[v_]^2)^n_.,x_Symbol] :=
  Dist[a^n,Int[u*Coth[v]^(2*n),x]] /;
FreeQ[{a,b},x] && IntegerQ[n] && ZeroQ[a-b]


(* ::Subsubsection::Closed:: *)
(*u (a Tanh[v]^m+b Sech[v]^m)^n  	Simplify sum of powers of hyperbolic functions*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If a^2+b^2=0, then a*Tanh[z]+b*Sech[z] == a*Tanh[z/2-a/b*Pi/4]*)


(* Int[(a_.*Tanh[v_]+b_.*Sech[v_])^n_,x_Symbol] :=
  Dist[a^n,Int[Tanh[v/2-a/b*Pi/4]^n,x]] /;
FreeQ[{a,b},x] && ZeroQ[a^2+b^2] && EvenQ[n] *)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: a*Sech[z]+b*Tanh[z] == (a+b*Sinh[z])/Cosh[z]*)


Int[u_.*(a_.*Sech[v_]^m_.+b_.*Tanh[v_]^m_.)^n_.,x_Symbol] :=
  Int[u*(a+b*Sinh[v]^m)^n/Cosh[v]^(m*n),x] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && (OddQ[m*n] || m*n<0) && Not[m==2 && ZeroQ[a-b]]


(* ::Subsubsection::Closed:: *)
(*u (a Coth[v]^m+b Csch[v]^m)^n 	Simplify sum of powers of hyperbolic functions*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: If a^2-b^2=0, then a*Coth[z]+b*Csch[z] == a*Coth[z/2+(a/b-1)*Pi*I/4]*)


Int[(a_.*Coth[v_]+b_.*Csch[v_])^n_,x_Symbol] :=
  Dist[a^n,Int[Coth[v/2+(a/b-1)*Pi*I/4]^n,x]] /;
FreeQ[{a,b},x] && ZeroQ[a^2-b^2] && EvenQ[n]


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: a*Csch[z]+b*Coth[z] == (a+b*Cosh[z])/Sinh[z]*)


Int[u_.*(a_.*Csch[v_]^m_.+b_.*Coth[v_]^m_.)^n_.,x_Symbol] :=
  Int[u*(a+b*Cosh[v]^m)^n/Sinh[v]^(m*n),x] /;
FreeQ[{a,b},x] && IntegerQ[{m,n}] && (OddQ[m*n] || m*n<0) && Not[m==2 && ZeroQ[a+b]]


(* ::Subsubsection::Closed:: *)
(*x^m Hyper[u]^n Hyper[v]^p*)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Sinh[z]*Cosh[z] == Sinh[2*z]/2*)


(* Int[x_^m_.*Sinh[v_]^n_.*Cosh[v_]^n_.,x_Symbol] :=
  Dist[1/2^n,Int[x^m*Sinh[Dist[2,v]]^n,x]] /;
IntegerQ[n] *)


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Sech[z]*Csch[z] == 2*Csch[2*z]*)


Int[x_^m_.*Sech[v_]^n_.*Csch[v_]^n_.,x_Symbol] :=
  Dist[2^n,Int[x^m*Csch[Dist[2,v]]^n,x]] /;
IntegerQ[{m,n}] && m>0


(* ::Item::Closed:: *)
(*Derivation: Algebraic simplification*)


(* ::Item:: *)
(*Basis: Convert hyperbolic function to complex exponentials*)


(* Got to improve x^m*f[e^x] integration before doing this! *)
(* Int[x_^m_.*f_[u_]^n_.*g_[v_]^p_.,x_Symbol] :=
  Int[x^m*TrigToExp[f[u]]^n*TrigToExp[g[v]]^p,x] /;
IntegerQ[{m,n,p}] && HyperbolicQ[f] && HyperbolicQ[g] *)


(* ::Subsection::Closed:: *)
(*Hyperbolic Function Substitution Rules*)


(* ::Subsubsection::Closed:: *)
(*Pure hyperbolic sine function substitution rules*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[Sinh[z]]*Cosh[z] == f[Sinh[z]] * Sinh'[z]*)


Int[u_*Cosh[c_.*(a_.+b_.*x_)],x_Symbol] :=
  Dist[1/(b*c),Subst[Int[Regularize[SubstFor[Sinh[c*(a+b*x)],u,x],x],x],x,Sinh[c*(a+b*x)]]] /;
FreeQ[{a,b,c},x] && FunctionOfQ[Sinh[c*(a+b*x)],u,x,True]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[Sinh[z]]*Coth[z] == f[Sinh[z]]/Sinh[z] * Sinh'[z]*)


Int[u_*Coth[c_.*(a_.+b_.*x_)],x_Symbol] :=
  Dist[1/(b*c),Subst[Int[Regularize[SubstFor[Sinh[c*(a+b*x)],u,x]/x,x],x],x,Sinh[c*(a+b*x)]]] /;
FreeQ[{a,b,c},x] && FunctionOfQ[Sinh[c*(a+b*x)],u,x,True]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[Sinh[z]]*Sinh[2*z] == 2*f[Sinh[z]]*Sinh[z] * Sinh'[z]*)


Int[u_*Sinh[c_.*(a_.+b_.*x_)],x_Symbol] :=
  Dist[4/(b*c),Subst[Int[Regularize[x*SubstFor[Sinh[c*(a+b*x)/2],u,x],x],x],x,Sinh[c*(a+b*x)/2]]] /;
FreeQ[{a,b,c},x] && FunctionOfQ[Sinh[c*(a+b*x)/2],u,x,True]


(* ::Subsubsection::Closed:: *)
(*Pure hyperbolic cosine function substitution rules*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[Cosh[z]]*Sinh[z] == f[Cosh[z]] * Cosh'[z]*)


Int[u_*Sinh[c_.*(a_.+b_.*x_)],x_Symbol] :=
  Dist[1/(b*c),Subst[Int[Regularize[SubstFor[Cosh[c*(a+b*x)],u,x],x],x],x,Cosh[c*(a+b*x)]]] /;
FreeQ[{a,b,c},x] && FunctionOfQ[Cosh[c*(a+b*x)],u,x,True]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[Cosh[z]]*Tanh[z] == f[Cosh[z]]/Cosh[z] * Cosh'[z]*)


Int[u_*Tanh[c_.*(a_.+b_.*x_)],x_Symbol] :=
  Dist[1/(b*c),Subst[Int[Regularize[SubstFor[Cosh[c*(a+b*x)],u,x]/x,x],x],x,Cosh[c*(a+b*x)]]] /;
FreeQ[{a,b,c},x] && FunctionOfQ[Cosh[c*(a+b*x)],u,x,True]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[Cosh[z]]*Sinh[2*z] == 2*f[Cosh[z]]*Cosh[z] * Cosh'[z]*)


Int[u_*Sinh[c_.*(a_.+b_.*x_)],x_Symbol] :=
  Dist[4/(b*c),Subst[Int[Regularize[x*SubstFor[Cosh[c*(a+b*x)/2],u,x],x],x],x,Cosh[c*(a+b*x)/2]]] /;
FreeQ[{a,b,c},x] && FunctionOfQ[Cosh[c*(a+b*x)/2],u,x,True]


(* ::Subsubsection::Closed:: *)
(*Pure hyperbolic cotangent function substitution rules*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is an integer, f[Coth[z]]*Tanh[z]^n == f[Coth[z]]/(Coth[z]^n*(1-Coth[z]^2)) * Coth'[z]*)


Int[u_*Tanh[c_.*(a_.+b_.*x_)]^n_.,x_Symbol] :=
  Dist[1/(b*c),Subst[Int[Regularize[SubstFor[Coth[c*(a+b*x)],u,x]/(x^n*(1-x^2)),x],x],x,Coth[c*(a+b*x)]]] /;
FreeQ[{a,b,c},x] && IntegerQ[n] && FunctionOfQ[Coth[c*(a+b*x)],u,x,True] && TryPureTanhSubst[u*Tanh[c*(a+b*x)]^n,x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[Coth[z]] == f[Coth[z]]/(1-Coth[z]^2) * Coth'[z]*)


If[ShowSteps,

Int[u_,x_Symbol] :=
  Module[{v=FunctionOfHyperbolic[u,x]},
  ShowStep["","Int[f[Coth[a+b*x]],x]","Subst[Int[f[x]/(1-x^2),x],x,Coth[a+b*x]]/b",Hold[
  Dist[1/Coefficient[v,x,1],Subst[Int[Regularize[SubstFor[Coth[v],u,x]/(1-x^2),x],x],x,Coth[v]]]]] /;
 NotFalseQ[v] && FunctionOfQ[Coth[v],u,x,True] && TryPureTanhSubst[u,x]] /;
SimplifyFlag,

Int[u_,x_Symbol] :=
  Module[{v=FunctionOfHyperbolic[u,x]},
  Dist[1/Coefficient[v,x,1],Subst[Int[Regularize[SubstFor[Coth[v],u,x]/(1-x^2),x],x],x,Coth[v]]] /;
 NotFalseQ[v] && FunctionOfQ[Coth[v],u,x,True] && TryPureTanhSubst[u,x]]]


(* ::Subsubsection::Closed:: *)
(*Pure hyperbolic tangent function substitution rules*)


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: If n is an integer, f[Tanh[z]]*Coth[z]^n == f[Tanh[z]]/(Tanh[z]^n*(1-Tanh[z]^2)) * Tanh'[z]*)


Int[u_*Coth[c_.*(a_.+b_.*x_)]^n_.,x_Symbol] :=
  Dist[1/(b*c),Subst[Int[Regularize[SubstFor[Tanh[c*(a+b*x)],u,x]/(x^n*(1-x^2)),x],x],x,Tanh[c*(a+b*x)]]] /;
FreeQ[{a,b,c},x] && IntegerQ[n] && FunctionOfQ[Tanh[c*(a+b*x)],u,x,True] && TryPureTanhSubst[u*Coth[c*(a+b*x)]^n,x]


(* ::Item::Closed:: *)
(*Derivation: Integration by substitution*)


(* ::Item:: *)
(*Basis: f[Tanh[z]] == f[Tanh[z]]/(1-Tanh[z]^2) * Tanh'[z]*)


If[ShowSteps,

Int[u_,x_Symbol] :=
  Module[{v=FunctionOfHyperbolic[u,x]},
  ShowStep["","Int[f[Tanh[a+b*x]],x]","Subst[Int[f[x]/(1-x^2),x],x,Tanh[a+b*x]]/b",Hold[
  Dist[1/Coefficient[v,x,1],Subst[Int[Regularize[SubstFor[Tanh[v],u,x]/(1-x^2),x],x],x,Tanh[v]]]]] /;
 NotFalseQ[v] && FunctionOfQ[Tanh[v],u,x,True] && TryPureTanhSubst[u,x]] /;
SimplifyFlag,

Int[u_,x_Symbol] :=
  Module[{v=FunctionOfHyperbolic[u,x]},
  Dist[1/Coefficient[v,x,1],Subst[Int[Regularize[SubstFor[Tanh[v],u,x]/(1-x^2),x],x],x,Tanh[v]]] /;
 NotFalseQ[v] && FunctionOfQ[Tanh[v],u,x,True] && TryPureTanhSubst[u,x]]]


TryPureTanhSubst[u_,x_Symbol] :=
  Not[MatchQ[u,ArcTanh[a_.*Tanh[v_]] /; FreeQ[a,x]]] &&
  Not[MatchQ[u,ArcTanh[a_.*Coth[v_]] /; FreeQ[a,x]]] &&
  Not[MatchQ[u,ArcCoth[a_.*Tanh[v_]] /; FreeQ[a,x]]] &&
  Not[MatchQ[u,ArcCoth[a_.*Coth[v_]] /; FreeQ[a,x]]] &&
  u===ExpnExpand[u,x]
