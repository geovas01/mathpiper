% ----------------------------------------------------------------------
% $Id: sfto.red 703 2010-08-19 11:39:39Z thomas-sturm $
% ----------------------------------------------------------------------
% Copyright (c) 1995-2009 A. Dolzmann, T. Sturm, 2010 T. Sturm
% ----------------------------------------------------------------------
% Redistribution and use in source and binary forms, with or without
% modification, are permitted provided that the following conditions
% are met:
%
%    * Redistributions of source code must retain the relevant
%      copyright notice, this list of conditions and the following
%      disclaimer.
%    * Redistributions in binary form must reproduce the above
%      copyright notice, this list of conditions and the following
%      disclaimer in the documentation and/or other materials provided
%      with the distribution.
%
% THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
% "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
% LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
% A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
% OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
% SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
% LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
% DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
% THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
% (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
% OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
%

lisp <<
   fluid '(sfto_rcsid!* sfto_copyright!*);
   sfto_rcsid!* := "$Id: sfto.red 703 2010-08-19 11:39:39Z thomas-sturm $";
   sfto_copyright!* := "(c) 1995-2009 A. Dolzmann, T. Sturm, 2010 T. Sturm"
>>;

module sfto;
% Standard form tools.

fluid '(!*ezgcd !*gcd !*rldavgcd !*rational);

switch sfto_yun,sfto_tobey,sfto_musser;
!*sfto_yun := T;

put('sqfpart,'polyfn,'sfto_sqfpartf);
put('tsqsum,'psopfn,'sfto_tsqsum!$);
put('sqfdec,'psopfn,'sfto_sqfdec!$);
put('pdec,'psopfn,'sfto_pdec!$);
put('sfto_yun,'simpfg,
   '((T (setq !*sfto_tobey nil) (setq !*sfto_musser nil))));
put('sfto_tobey,'simpfg,
   '((T (setq !*sfto_yun nil) (setq !*sfto_musser nil))));
put('sfto_musser,'simpfg,
   '((T (setq !*sfto_tobey nil) (setq !*sfto_yun nil))));
operator exteuc;
operator degree;
operator coefficients;

typedef any;
typedef sf checked by sfpx;
typedef sf!* checked by sfpx!*;
typedef domain checked by domainp;
typedef id checked by idp;
typedef pair checked by pairp;
typedef list checked by listp;
typedef alist checked by alistp;
typedef kernel checked by assert_kernelp;
typedef boolean checked by booleanp;
typedef extra!-boolean;
typedef am_list checked by am_listp;
typedef am_poly checked by am_polyp;

procedure sfto_dcontentf(u);
   % Standard form tools domain content standard form. [u] is an SF.
   % Returns a domain element, which is the (non-negatice) content of
   % [u] as a multivariate polynomial over the current domain.
   sfto_dcontentf1(u,nil);

assert sfto_dcontentf: (sf) -> domain;

procedure sfto_dcontentf1(u,g);
   % Standard form tools domain content standard form subroutine. [u]
   % is a term; [g] is a domain element. Returns the gcd of the
   % content of [u] and [g], which is a domain element.
   if g = 1 then
      g
   else if domainp u then
      sfto_gcdf(absf u,g)
   else
      sfto_dcontentf1(red u,sfto_dcontentf1(lc u,g));

assert sfto_dcontentf1: (sf,domain) -> domain;

procedure sfto_dprpartf(u);
   % Standard form tools domain primitive part standard form. [u] is
   % an SF. Returns an SF which is the primitive part of [u] as a
   % multivariate polynomial over the current domain. The sign of the
   % head coefficient in this primitive part is positive.
   sfto_dprpartf1(u,sfto_dcontentf u);

assert sfto_dprpartf: (sf) -> sf;

procedure sfto_dprpartksf(u);
   % Standard form tools domain primitive part standard form keep
   % sign. [u] is an SF. Returns an SF which is the primitive part of
   % [u] as a multivariate polynomial over the current domain. The
   % sign of the head coefficient in this primitive part is that of
   % [u].
   quotf(u,sfto_dcontentf u);

assert sfto_dprpartksf: (sf) -> sf;

procedure sfto_dprpartf1(u,c);
   % Standard form tools domain primitive part standard form
   % subroutine. [u] and [c] are SF's. Returns an SF which is the
   % primitive part of [u] as a multivariate polynomial over the
   % current domain.
   (if minusf w then negf w else w) where w = quotf(u,c);

assert sfto_dprpartf1: (sf,sf) -> sf;

procedure sfto_sqfpartf(u);
   % Standard form tools square-free part. [u] is a non-zero SF.
   % Returns an SF which is the square-free part of [u] as a
   % multivariate polynomial. The (domain) content is dropped.
   begin scalar c,pp;
      if domainp u then return 1;
      c := sfto_ucontentf u;
      pp := quotf(u,c);
      return multf(sfto_sqfpartf(c),quotf(pp,sfto_gcdf!*(pp,diff(pp,mvar u))))
   end;

assert sfto_sqfpartf: (sf!*) -> sf!*;

procedure sfto_ucontentf(u);
   % Standard form tools univariate content standard form. [u] is an
   % SF. Returns the content of [u] as a univariate polynomial in its
   % [mvar] over the polynomial ring in all other contained variables.
   if domainp u then u else sfto_ucontentf1(u,mvar u);

assert sfto_ucontentf: (sf) -> sf;

procedure sfto_ucontentf1(u,v);
   % Standard form tools univariate content standard form subroutine.
   % [v] is a kernel; [u] is an SF with main variable [v]. Returns an
   % SF which is the content of [u] as an univariate polynomial in [v]
   % over the polynomial ring in all other contained variables.
   if domainp u or mvar u neq v then u else
      sfto_gcdf!*(lc u,sfto_ucontentf1(red u,v));

assert sfto_ucontentf1: (sf,kernel) -> sf;

procedure sfto_uprpartf(u);
   % Standard form tools univariate primitive part. [u] is an SF.
   % Returns the primitive part of [u] as a univariate polynomial in
   % its [mvar] over the polynomial ring in all other contained
   % variables.
   quotf(u,sfto_ucontentf u);

assert sfto_uprpartf: (sf) -> sf;

procedure sfto_tsqsumf(u);
   % Standard form tools trivial square sum standard form. [u] is an
   % SF. Returns one of [nil], ['stsq], or ['tsq]. ['stsq] means that
   % in the sparse distributive representation of [u] all exponents
   % are even and all coefficients are positive. ['tsq] means that all
   % exponents are even and all coefficients are positive except for
   % that there is no absolute summand.
   if domainp u then
      (if null u then 'tsq else if not minusf u then 'stsq)
   else
      evenp ldeg u and sfto_tsqsumf lc u and sfto_tsqsumf red u;

assert sfto_tsqsumf: (sf) -> id;

procedure sfto_tsqsum!$(argl);
   sfto_tsqsumf(numr simp car argl);

assert sfto_tsqsum!$: (list) -> id;

procedure sfto_sqfdecf(u);
   % Standard form tools multivariate square-free decomposition
   % standard form. [u] is an SF. Returns a (dense) list $((q_1 .
   % 1),(q_2 . 2),...,(q_n . n))$ such that $\prod q_i^i = u$ with the
   % $q_i$ square-free and pairwise relatively prime. The (integer)
   % content of u is dropped. Decomposition is performed by merging
   % univariate decompositions. The univariate decomposition method is
   % selected by turning on one of the switches [sfto_yun] (default),
   % [sfto_tobey], or [sfto_musser].
   begin scalar c,pp;
      if domainp u then return {1 . 1};
      c := sfto_ucontentf u;
      pp := quotf(u,c);
      return sfto_sqdmerge(sfto_sqfdecf(c),sfto_usqfdecf(pp))
   end;

assert sfto_sqfdecf: (sf) -> alist;

procedure sfto_sqfdec!$(argl);
   % Standard form tools square free decomposition. [argl] is an
   % argument list. Returns an AM list of AM lists of the form
   % $(p_i,m_i)$, where the $p_i$ are polynomials represented as a
   % Lisp-prefix-form and the $m_i$ are integers.
   begin scalar w;
      return 'list . for each x in sfto_sqfdecf numr simp car argl join
      	 if (w := prepf car x) neq 1 then {{'list,w,cdr x}}
   end;

assert sfto_sqfdec!$: (list) -> am_list;

procedure sfto_usqfdecf(u);
   if !*sfto_yun then
      sfto_yun!-usqfdecf u
   else if !*sfto_musser then
      sfto_musser!-usqfdecf u
   else if !*sfto_tobey then
      sfto_tobey!-usqfdecf u
   else
      rederr {"sfto_usqfdecf: select a decomposition method"};

assert sfto_usqfdecf: (sf) -> alist;

procedure sfto_yun!-usqfdecf(p);
   % Standard form tools univariate square-free decomposition after
   % Yun. [p] is a an SF that is viewed a univariate Polynomial in its
   % [mvar] over the polynomial ring in all other variables; in this
   % sense, [p] must be primitive. Returns the square-free
   % decomposition of [p] as a (dense) list $((q_1 . 1),(q_2 .
   % 2),...,(q_n . n))$ such that $\prod q_i^i = u$ with the $q_i$
   % square-free and pairwise relatively prime.
   begin scalar !*gcd,x,g,c,d,w,l; integer n;
      !*gcd := T;
      x := mvar p;
      g := sfto_gcdf(p,w := diff(p,x));
      c := quotf(p,g);
      d := addf(quotf(w,g),negf(diff(c,x)));
      repeat <<
	 p := sfto_gcdf(c,d);
	 l := (p . (n := n+1)) . l;
	 c := quotf(c,p);
	 d := addf(quotf(d,p),negf(diff(c,x)))
      >> until domainp c;
      return reversip l
   end;

assert sfto_yun!-usqfdecf: (sf) -> alist;

procedure sfto_musser!-usqfdecf(u);
   % Standard form tools univariate square-free decomposition after
   % Musser. [p] is a an SF that is viewed a univariate Polynomial in
   % its [mvar] over the polynomial ring in all other variables; in
   % this sense, [p] must be primitive. Returns the square-free
   % decomposition of [p] as a (dense) list $((q_1 . 1),(q_2 .
   % 2),...,(q_n . n))$ such that $\prod q_i^i = u$ with the $q_i$
   % square-free and pairwise relatively prime.
   begin scalar !*gcd,v,u1,sqfp,sqfp1,l; integer n;
      !*gcd := T;
      v := mvar u;
      u1 := sfto_gcdf(u,diff(u,v));
      sqfp := quotf(u,u1);
      while degr(u1,v)>0 do <<
	 sqfp1 := sfto_gcdf(sqfp,u1);
	 l := (quotf(sqfp,sqfp1) . (n := n+1)) . l;
	 u1 := quotf(u1,sqfp1);
	 sqfp := sqfp1
      >>;
      l := (sqfp . (n := n+1)) . l;
      return reversip l
   end;

assert sfto_musser!-usqfdecf: (sf) -> alist;

procedure sfto_tobey!-usqfdecf(u);
   % Standard form tools univariate square-free decomposition after
   % Tobey and Horowitz. [p] is a an SF that is viewed a univariate
   % Polynomial in its [mvar] over the polynomial ring in all other
   % variables; in this sense, [p] must be primitive. Returns the
   % square-free decomposition of [p] as a (dense) list $((q_1 .
   % 1),(q_2 . 2),...,(q_n . n))$ such that $\prod q_i^i = u$ with the
   % $q_i$ square-free and pairwise relatively prime.
   begin scalar !*gcd,v,h,q1,q2,l; integer n;
      !*gcd := T;
      v := mvar u;
      h := sfto_gcdf(u,diff(u,v));
      q2 := quotf(u,h);
      while degr(u,v)>0 do <<
	 u := h;
	 q1 := q2;
	 h := sfto_gcdf(u,diff(u,v));
	 q2 := quotf(u,h);
	 l := (quotf(q1,q2) . (n := n+1)) . l
      >>;
      return reversip l
   end;

assert sfto_tobey!-usqfdecf: (sf) -> alist;

procedure sfto_sqdmerge(l1,l2);
   % Standard form tools square-free decomposition merge
   begin scalar l;
      l := l1;
      while l1 and l2 do <<
	 caar l1 := multf(caar l1,caar l2);
      	 l1 := cdr l1;
	 l2 := cdr l2
      >>;
      if l2 then l := nconc(l,l2);
      return l
   end;

assert sfto_sqdmerge: (alist,alist) -> alist;

procedure sfto_pdecf(u);
   % Standard form tools multivariate parity decomposition. [u] is an
   % SF. Returns a consed pair $a . d$ such that $a$ is the product of
   % all square-free factors with an odd multiplicity in [u] and $d$
   % is that of the even multiplicity square-free factors. The
   % (integer) content of u is dropped. Decomposition is performed by
   % merging univariate decompositions. The univariate decomposition
   % method is selected by turning on one of the switches [sfto_yun]
   % (default), [sfto_musser].
   begin scalar c,dpdc,dpdpp;
      if domainp u then return 1 . 1;
      c := sfto_ucontentf u;
      dpdc := sfto_pdecf c;
      dpdpp := sfto_updecf quotf(u,c);
      return multf(car dpdc,car dpdpp) . multf(cdr dpdc,cdr dpdpp)
   end;

assert sfto_pdecf: (sf) -> pair;

procedure sfto_updecf(u);
   if !*sfto_yun then
      sfto_yun!-updecf u
   else if !*sfto_musser then
      sfto_musser!-updecf u
   else
      rederr {"sfto_updecf: select a decomposition method"};

assert sfto_updecf: (sf) -> pair;

procedure sfto_yun!-updecf(p);
   begin scalar !*gcd,x,g,c,d,w,l,od;
      !*gcd := T;
      l := 1 . 1;
      x := mvar p;
      g := sfto_gcdf(p,w := diff(p,x));
      c := quotf(p,g);
      d := addf(quotf(w,g),negf(diff(c,x)));
      repeat <<
	 od := not od;
	 p := sfto_gcdf(c,d);
	 if od then car l := multf(car l,p) else cdr l := multf(cdr l,p);
	 c := quotf(c,p);
	 d := addf(quotf(d,p),negf(diff(c,x)))
      >> until domainp c;
      return l
   end;

assert sfto_yun!-updecf: (sf) -> pair;

procedure sfto_musser!-updecf(u);
   begin scalar !*gcd,od,v,u1,sqfp,sqfp1,l;
      !*gcd := T;
      od := T;
      l := 1 . 1;
      v := mvar u;
      u1 := sfto_gcdf(u,diff(u,v));
      sqfp := quotf(u,u1);
      while degr(u1,v)>0 do <<
	 sqfp1 := sfto_gcdf(sqfp,u1);
	 if od then
 	    car l := multf(car l,quotf(sqfp,sqfp1))
	 else
	    cdr l := multf(cdr l,quotf(sqfp,sqfp1));
	 u1 := quotf(u1,sqfp1);
	 sqfp := sqfp1;
	 od := not od
      >>;
      if od then
	 car l := multf(car l,sqfp)
      else
	 cdr l := multf(cdr l,sqfp);
      return l
   end;

assert sfto_musser!-updecf: (sf) -> pair;

procedure sfto_pdec!$(argl);
   {'list,prepf car w,prepf cdr w}
      where w=sfto_pdecf numr simp car argl;

assert sfto_pdec!$: (list) -> am_list;

procedure sfto_decdegf(u,k,n);
   % Standard form tools decrement degree standard form. [u] is an SF;
   % [k] is a variable; [n] is an integer. Returns an SF. Replace each
   % occurence of $[k]^d$ by $k^(d/n)$.
   reorder sfto_decdegf1(sfto_reorder(u,k),k,n);

assert sfto_decdegf: (sf,kernel,number) -> sf;

procedure sfto_decdegf1(u,k,n);
   % Standard form tools decrement degree standard form. [u] is an SF
   % with main variable [k]; [k] is a variable; [n] is an integer.
   % Returns an SF. Replace each occurence of $[k]^d$ by $k^(d/n)$.
   if degr(u,k)=0 then
      u
   else
      mvar u .** (ldeg u / n) .* lc u .+ sfto_decdegf1(red u,k,n);

assert sfto_decdegf1: (any,kernel,number) -> any;

procedure sfto_reorder(u,v);
   % Standard form tools reorder. [u] is an SF; [v] is a kernel.
   % Returns the SF [u] reorderd wrt. [{v}].
   begin scalar w;
      w := setkorder {v};
      u := reorder u;
      setkorder w;
      return u
   end;

assert sfto_reorder: (sf,kernel) -> any;

procedure sfto_groebnerf(l);
   % Standard form tools Groebner calculation standard form. [l] is a
   % list of SF's. Returns a list of SF's. The returned list is the
   % reduced Groebner basis of [l] wrt. the current term order.
   begin scalar w;
      if null l then return nil;
      w := groebnereval {'list . for each sf in l collect prepf sf};
      return for each x in cdr w collect
	 numr simp x
   end;

assert sfto_groebnerf: (list) -> list;

procedure sfto_preducef(f,gl);
   % Standard form tools polynomial reduction standard form. [f] is an
   % SF and [gl] a list of SF's. Returns the numerator of [f] reduced
   % wrt. [gl].
   if null gl then
      f
   else if (null cdr gl) and (domainp car gl) then
      nil
   else
      numr simp preduceeval {
	 prepf f,'list . for each sf in gl collect prepf sf};

assert sfto_preducef: (sf,list) -> sf;

procedure sfto_greducef(f,gl);
   % Standard form tools polynomial reduction standard form. [f] is an
   % SF and [gl] a list of SF's. Returns the SF [f] reduced wrt. a
   % Groebner basis of [gl].
   if null gl then
      f
   else if (null cdr gl) and (domainp car gl) then
      nil
   else
      numr simp greduceeval {
	 prepf f,'list . for each sf in gl collect prepf sf};

assert sfto_greducef: (sf,list) -> sf;

procedure sfto_gcdf!*(f,g);
   % Standard form tools greatest common divisor of standard forms.
   % [f] and [g] are SF's. Returns an SF, the GCD of [f] and [g].
   % Compute the GCD of [f] and [g] via [gcdf!*] or [ezgcdf] according
   % to Davenport's criterion: If, in one polynomial, the number of
   % variables of a degree greater than 2 is greater than 1, then use
   % [ezgcd].
   sfto_gcdf(f,g) where !*gcd=T;

assert sfto_gcdf!*: (sf,sf) -> sf;

procedure sfto_gcdf(f,g);
   % Standard form tools greatest common divisor of standard forms.
   % [f] and [g] are SF's. Returns an SF, the GCD of [f] and [g].
   % Compute the GCD of [f] and [g] via [gcdf!*] or [ezgcdf] according
   % to Davenport's criterion: If, in one polynomial, the number of
   % variables of a degree greater than 2 is greater than 1, then use
   % [ezgcd]. For computing the real gcd of [f] ang [g] this
   % procedures require, that [!*gcd] is set to [T].
   if null !*rldavgcd then
      gcdf(f,g)
   else if sfto_davp(f,nil) or sfto_davp(g,nil) then
      gcdf(f,g) where !*ezgcd=nil
   else
      ezgcdf(f,g);

assert sfto_gcdf: (sf,sf) -> sf;

procedure sfto_davp(f,badv);
   % Standard form tools Davenport predicate. [f] is an SF; [v] is a
   % kernel or [nil]. Returns Boolean. [T] means [gcdf] can be used.
   if domainp f then
      T
   else if ldeg f > 2 then
      if badv and mvar f neq badv then
	 nil
      else
	 sfto_davp(lc f,mvar f) and sfto_davp(red f,mvar f)
   else
      sfto_davp(lc f,badv) and sfto_davp(red f,badv);

assert sfto_davp: (sf,kernel) -> boolean;

procedure sfto_sqrtf(f);
   % Standard form tools square root standard form. Returns [nil] or
   % an SF $g$, such that $g**2=[f]$.
   begin scalar a,c,w,sd,result;
      c := sfto_dcontentf(f);
      result := isqrt c;
      if result**2 neq c then
	 return nil;
      sd := sfto_sqfdecf(f);
      w := sd;
      while sd do <<
	 a := car sd;
      	 sd := cdr sd;
	 if not(evenp cdr a) and car a neq 1 then <<
 	    sd := nil;
	    a := 'break
	 >> else
	    result := multf(result,exptf(car a,cdr a / 2 ))
      >>;
      if a neq 'break and exptf(result,2) = f then
	 return result
   end;

assert sfto_sqrtf: (sf) -> extra!-boolean;

procedure sfto_monfp(sf);
   % Standard form tools monomial predicate. [f] is an SF. Returns an
   % SF. Check if [sf] is of the form $a x_1 \dots x_n$ for a domain
   % element $a$ and kernels $x_i$.
   domainp sf or (null red sf and sfto_monfp lc sf);

assert sfto_monfp: (sf) -> sf;

procedure sfto_sqfpartz(z);
   % Standard form tools square free part of an integer. [z] is an
   % integer with prime decomposition $p_1^{e_1}\cdots p_n^{e_n}$.
   % Returns $\prod \{p_i\}$.
   sfto_zdgen(z,0);

assert sfto_sqfpartz: (number) -> number;

procedure sfto_zdeqn(z,n);
   % Standard form tools z decomposition equal n. [z] is an integer
   % with prime decomposition $p_1^{e_1}\cdots p_m^{e_m}$; [n] is a
   % positive integer. Returns $\prod \{p_i:e_i=n\}$.
   for each x in zfactor z product
      if cdr x = n then car x else 1;

assert sfto_zdeqn: (number,number) -> number;

procedure sfto_zdgtn(z,n);
   % Standard form tools z decomposition greater than n. [z] is an
   % integer with prime decomposition $p_1^{e_1}\cdots p_n^{e_n}$; [n]
   % is a positive integer. Returns $\prod \{p_i:e_i>n\}$.
   for each x in zfactor z product
      if cdr x > n then car x else 1;

assert sfto_zdgtn: (number,number) -> number;

procedure sfto_zdgen(z,n);
   % Standard form tools z decomposition greater than or equal to n.
   % [z] is an integer with prime decomposition $p_1^{e_1}\cdots
   % p_n^{e_n}$; [n] is a positive integer. Returns $\prod
   % \{p_i:e_i\geq n\}$.
   for each x in zfactor z product
      if cdr x >= n then car x else 1;

assert sfto_zdgen: (number,number) -> number;

procedure sfto_exteucf(a,b);
   % Standard form tools extended Euclidean Algorithm for polynomials.
   % [a], [b] are SF's describing univariate polynomials both in the
   % same variable. Returns a list $(g,s,t)$ of SQ's such that
   % $\gcd([a],[b])=g=s[a]+t[b]$. If the GCD $g$ is a domain element,
   % then it is normalized to $1$.
   begin scalar w,s,tt,u,v,s1,t1,!*rational;
      on1 'rational;
      s := numr simp 1;
      tt := numr simp 0;
      u := numr simp 0;
      v := numr simp 1;
      while not null b do <<
	 w := qremf(a,b);
	 a := b;
	 b := cdr w;
	 s1 := s;
	 t1 := tt;
	 s := u;
	 tt := v;
	 u := addf(s1,negf multf(car w,u));
	 v := addf(t1,negf multf(car w,v))
      >>;
      if domainp a then <<
	 s := quotf(s,a);
	 tt := quotf(tt,a);
	 a := 1
      >>;
      off1 'rational;
      return {resimp !*f2q a,resimp !*f2q s,resimp !*f2q tt}
   end;

assert sfto_exteucf: (sf,sf) -> list;

procedure exteuc(a,b);
   begin scalar af,bf,ka,kb;
      af := numr simp a;
      bf := numr simp b;
      if domainp af and domainp bf then
	 return 'list . sfto_exteucd(a,b);
      ka := kernels af;
      if (ka and cdr ka) or (kb and cdr kb) then
	 rederr "non-univariate input polynomial";
      return 'list . for each x in sfto_exteucf(af,bf) collect prepsq x
   end;

assert exteuc: (am_poly,am_poly) -> am_list;

procedure sfto_exteucd(a,b);
   % Standard form tools extended Euclidean Algorithm for domain
   % elements (integers). [a], [b] are numbers. Returns a list
   % $(g,s,t)$ of numbers such that $g>=0$ and
   % $\gcd([a],[b])=g=s[a]+t[b]$.
   begin integer s,tt,u,v,s1,t1,q,r;
      s := 1;
      v :=  1;
      while not eqn(b,0) do <<
	 q := a / b;
	 r := remainder(a,b);
	 a := b;
	 b := r;
	 s1 := s;
	 t1 := tt;
	 s := u;
	 tt := v;
	 u := s1 - q * u;
	 v := t1 - q * v
      >>;
      if a < 0 then <<
	 s := -s;
	 tt := -tt;
	 a := -a
      >>;
      return {a,s,tt}
   end;

assert sfto_exteucd: (number,number) -> list;

procedure sfto_linp(f,vl);
   % Standard form tools linear predicate. [f] is a SF; [vl] is a list
   % of variables. Returns Bool. Returns [T] if [f] is linear in [vl].
   sfto_linp1(f,vl,nil);

assert sfto_linp: (sf,list) -> boolean;

procedure sfto_linp1(f,vl,oc);
   domainp f or
      (not(mvar f memq vl) and not(mvar f memq oc) and
	 sfto_linp1(lc f,vl,oc) and sfto_linp1(red f,vl,oc)) or
      (mvar f memq vl and not(mvar f memq oc) and ldeg f = 1 and
	 sfto_linp1(lc f,vl,mvar f . oc) and sfto_linp1(red f,vl,oc));

assert sfto_linp1: (sf,list,list) -> boolean;

procedure sfto_linwpp(f,vl);
   % Standard form tools linear and weakly parametric predicate. [f] is
   % a SF; [vl] is a list of variables. Returns Bool. Returns [T] if [f]
   % is linear and weakly parametric in [vl].
   domainp f or
      (not(mvar f memq vl) and null intersection(kernels lc f,vl) and
	 sfto_linwpp(red f,vl)) or
      (mvar f memq vl and ldeg f = 1 and domainp lc f and
	 sfto_linwpp(red f,vl));

assert sfto_linwpp: (sf,list) -> boolean;

procedure sfto_varf(f);
   % Variable form. [f] is an SF. Returns [nil] or an identifier. If
   % [f] is a variable then return the corresponding kernel else
   % return nil. In contrast to sfto_idvarf, this function rerturn also
   % non-atomic kernels.
   if not domainp f and null red f and eqn(lc f,1) and eqn(ldeg f,1) then
      mvar f;

assert sfto_varf: (sf) -> extra!-boolean;

procedure sfto_idvarf(f);
   % Identifier variable form. [f] is an SF. Returns [nil] or an
   % identifier. If [f] is an atomic variable then return the
   % corresponding identifier else return nil.
   if not domainp f and
      null red f and eqn(lc f,1) and eqn(ldeg f,1) and idp mvar f
   then
      mvar f;

assert sfto_idvarf: (sf) -> extra!-boolean;

procedure sfto_lmultf(fl);
   % Ordered field standard form list multf. [fl] is a list of SFs.
   % Returns an SF. Result is the product of the SFs in [fl].
   if null fl then 1 else multf(car fl,sfto_lmultf cdr fl);

assert sfto_lmultf: (list) -> sf;

procedure degree(u);
   sfto_tdegf numr simp u;

assert degree: (am_poly) -> number;

procedure sfto_tdegf(f);
   % Ordered field standard form total degree standard form. [f] is an
   % SF. Returns a number. The result is the total degree of [f].
   begin scalar w,x; integer td;
      if domainp f then
      	 return 0;
      x := mvar f;
      while not domainp f and mvar f eq x do <<
	 w := sfto_tdegf lc f + ldeg f;
	 if w > td then
	    td := w;
	 f := red f
      >>;
      w := sfto_tdegf f;
      if  w > td then
	 return w;
      return td
   end;

assert sfto_tdegf: (sf) -> number;

procedure coefficients(f,vl);
   'list . for each c in sfto_allcoeffs(numr simp f,cdr vl) collect
      prepf c;

assert coefficients: (am_poly,am_list) -> am_list;

procedure sfto_allcoeffs(f,vl);
   sfto_allcoeffs1({f},vl);

assert sfto_allcoeffs: (sf,list) -> list;

procedure sfto_allcoeffs1(l,vl);
   if null vl then
      l
   else
      sfto_allcoeffs1(for each f in l join
	 sfto_coefs(sfto_reorder(f,car vl),car vl),cdr vl);

assert sfto_allcoeffs1: (list,list) -> list;

procedure sfto_coefs(f,v);
   if not domainp f and mvar f eq v then coeffs f else {f};

assert sfto_coefs: (sf,kernel) -> list;

endmodule;  % [sfto]

end;  % of file
