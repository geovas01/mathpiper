
%======================================================
%       Name:           tensor - tensor arithmetics
%       Author:         A.Kryukov (kryukov@npi.msu.su)
%       Copyright:      (C), 1993-1996, A.Kryukov
%       Version:        2.02    28/03/96
%------------------------------------------------------
%       Release:        Nov. 13, 1993     th_match, th_match1
%                       Jul. 13, 1994     symmetry generated by multiplication.
%                       Mar. 28, 1996     t_gcd, t_prep, t_times4
%======================================================

module tensor$

% Redistribution and use in source and binary forms, with or without
% modification, are permitted provided that the following conditions are met:
%
%    * Redistributions of source code must retain the relevant copyright
%      notice, this list of conditions and the following disclaimer.
%    * Redistributions in binary form must reproduce the above copyright
%      notice, this list of conditions and the following disclaimer in the
%      documentation and/or other materials provided with the distribution.
%
% THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
% AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
% THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
% PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNERS OR
% CONTRIBUTORS
% BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
% CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
% SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
% INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
% CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
% ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
% POSSIBILITY OF SUCH DAMAGE.
%


fluid '(!*debug)$
switch debug$

%=================================================
%       tensor::=(!:tensor . ((th1 . pv1) (th2 . pv2) ...))
%       th::=(tn . il . dl)
%       tn::=(id1 id2 ...)
%==================================================

global '(!*basis)$

symbolic procedure t_simp v$
  begin scalar x;
     if !*debug then << terpri()$ print list('t_simp . v) >>$
     if (x:=get(car v,'!:tensor))=99
       then put(car v,'!:tensor,length cdr v)
     else if null(x = length cdr v)
       then rederr list('t_simp,"*** Invalid number of indices:",v)$
%    v:='!:tensor . list((list car v . il_simp cdr v . nil)
         % -AK 28/03/96
     v:='!:tensor . list((list car v . il_simp cdr v . 1)
         % +AK 28/03/96
                        . list(1 . mkunitp length cdr v)
                        )$
     return (((if cdr z then z else nil)
             where z=sieve_t(v,!*basis)) ./ 1
            )$
  end$

global '(domainlist!*)$

switch tensor$

domainlist!*:=union('(!:tensor),domainlist!*)$
put('tensor,'tag,'!:tensor)$
put('!:tensor,'dname,'tensor)$
%flag('(!:tensor),'field)$              % !:tensor is not a field!
put('!:tensor,'minus,'t_minus)$
put('!:tensor,'minusp,'t_minusp)$
put('!:tensor,'plus,'t_plus)$
put('!:tensor,'times,'t_times)$ % v*c
put('!:tensor,'difference,'t_difference)$
put('!:tensor,'zerop,'t_zerop)$
put('!:tensor,'onep,'t_onep)$
put('!:tensor,'prepfn,'t_prep)$
put('!:tensor,'prifn,'t_pri)$
put('!:tensor,'intequivfn,'t_intequiv)$
put('!:tensor,'i2d,'i2tensor)$
put('!:tensor,'expt,'t_expt)$
put('!:tensor,'quotient,'t_quotient)$
put('!:tensor,'divide,'t_divide)$
put('!:tensor,'gcd,'t_gcd)$

flag('(!:tensor),'tmode)$

symbolic procedure t_minus u$
  if atom cdr u then -cdr u
  else sieve_t(car u . t_minus1(cdr u,nil),!*basis)$

symbolic procedure t_minus1(u,v)$
  if null u then reversip v
  else t_minus1(cdr u,(caar u . pv_neg cdar u) . v)$

symbolic procedure t_minusp u$ nil$

symbolic procedure t_plus(u,v)$
  if atom cdr u
    then rederr list('t_plus,"*** Tensor can't be added to:",cdr u)
  else if atom cdr v then t_plus(v,u)
  else sieve_t(car u . t_plus1(cdr u,cdr v),!*basis)$

symbolic procedure t_plus1(u,v)$
  if null u then v
  else if null v then u
  else t_plus1(cdr u,t_plus2(car u,v,nil))$

symbolic procedure t_plus2(x,v,w)$
  if null v then reversip(x . w)
  else if th_match(car x,caar v)
    then append(cdr v, reversip(t_add2(x,car v) . w))
  else t_plus2(x,cdr v, car v . w)$

symbolic procedure t_times(u,v)$
  % u,v - tensor::=(!:tensor . tlist)
  if t_intequiv u then t_times(v,u)
  else if atom cdr v then car u . t_timesc(cdr u,cdr v,nil)
  else (sieve_t(x,!*basis)
        where x=car u . t_times1(cdr u,cdr v,nil)
%             ,y=b_expand(u,v)
       )$

symbolic procedure t_timesc(tt,c,w)$
  % tt,w - tlist::=((th1 . pv1) ...)
  % c - integer
  if null tt then reversip w
  else t_timesc(cdr tt
               ,c
               ,(caar tt . pv_multc(cdar tt,c)) . w
               )$

symbolic procedure t_times1(u,v,w)$
  % u,v,w - tlist::=((th1 . pv1) ...)
  if null u then reversip w
  else t_times1(cdr u,v,t_times2(v,car u,w))$

symbolic procedure t_times2(v,x,w)$
  % u,w - tlist::=((th1 . pv1) ...)
  % x - (th . pv)
  if null v then w
%  else t_times2(cdr v,x,t_plus2(t_times3(car v,x),w,nil))$
  else t_times2(cdr v,x,t_plus2(t_times4(car v,x),w,nil))$

symbolic procedure t_times3(y,x)$
  % x,y - (th . pv)
  if null ordp(caar y,caar x) then t_times3(x,y)
  else (append(caar y,caar x)
       . il_simp append(cadar y,cadar x)
       . append(cddar y,cddar x)
       ) . cdr pv_times('!:pv . cdr y,'!:pv . cdr x)$

symbolic procedure t_times4(x,y)$                % mod. AK 28/03/96
  % x,y - (th . pv)
  % return product of x by y.
  % side effect: the !*basis will be updated by symmetry properties
  %              generate by multiplication.
  begin scalar tf1,tf2,z,den$
    den := cddar x * cddar y$                     % + AK 28/03/96
    tf1 := t_split(x)$
    tf2 := t_split(y)$
    z   := t_fuse(tf1,tf2)$
    rplacd(cdar z,den)$                           % + AK 28/03/96
    return z$
  end$

symbolic procedure t_split(x)$
  % x - (th . pv)
  % r.v. - list of tensor factors: (tf1 ...)
  %        where tf - (th . pv) and th is a simple tname, i.e. (id)
  if null cdaar x then list x
  else (t_split1(caar x,pappl(p,cadar x)
                ,unpkpv pappl_pv(p,cdr x),nil
                )
        where p = prev(cdadr x)
       )$

symbolic procedure t_split1(tn,il,pv,tfl)$
  % tfl (r.v.) - list of tfactors.
  if null tn then reversip tfl
  else if cdr pv then rederr list('t_split1,": too long pvector ",pv)
  else (
        (t_split1(cdr tn,cdr ils,list(caar pv . cdr pvs),
                 ((list car tn . list car ils)
                 . list(1 . p_rescale car pvs)
                 ) . tfl
                )
          where ils = l_split(il,n,nil),
                pvs = l_split(cdar pv,n,nil)
        ) where n = get(car tn,'!:tensor)
       )$

symbolic procedure pv_rescale(pv)$ pv_rescale1(pv,nil)$

symbolic procedure pv_rescale1(pv,pv1)$
  if null pv then reversip pv1
  else pv_rescale1(cdr pv,(caar pv . p_rescale cdar pv). pv1)$

symbolic procedure p_rescale p$
  (for each x in p collect (x-n)) where n = car p - 1$

symbolic procedure l_split(lst,n,lst1)$
  % Split list lst into two lists where first one contain n items.
  % (r.v.) - (lst1 . lst_rest)
  if n<=0 then (reversip lst1) . lst
  else l_split(cdr lst,n-1,car lst . lst1)$

symbolic procedure unpkpv(pv)$
  unpkpv1(pv,nil)$

symbolic procedure unpkpv1(pv,upv)$
  if null pv then reversip upv
  else unpkpv1(cdr pv,(caar pv . unpkp cdar pv) . upv)$

symbolic procedure t_fuse(tf1,tf2)$
  % tf1, tf2 - list of tensor factors
  % r.v. - the result of "multiplication" of them with order.
  t_fuse1(reversip tf1,reversip tf2,nil)$

symbolic procedure t_fuse1(tf1,tf2,tf3)$
  % r.v. - tf3 - total ordered tensor factor list.
  (if null tf1 then t_fuse2(reversip append(reversip tf2,tf3),nil)
   else if null tf2 then t_fuse2(reversip append(reversip tf1,tf3),nil)
   else if null ordp(caaar tf1,caaar tf2)
          then t_fuse1(cdr tf1,tf2,car tf1 . tf3)
   else t_fuse1(tf1,cdr tf2,car tf2 . tf3)
  )
%   where x=if tf1 and tf3 and caaar tf1 = caaar tf3
%              then addmultsym(car tf1,car tf3)
%            else if tf2 and tf3 and caaar tf2 = caaar tf3
%              then addmultsym(car tf2,car tf3)
%            else if tf3
%             then !*basis:=b_expand1(if tf1 then car tf1 else car tf2
%                                          ,car tf3,!*basis,!*basis
%                                          )
%            else nil
  $

symbolic procedure t_fuse2(tf,te)$
  if null tf then te
  else t_fuse2(cdr tf,t_fuse3(car tf,te))$

symbolic procedure t_fuse3(t1,t2)$
  % t1,t2 - tensors
  % r.v.  - it's product.
  % side effect: !*basis will be updated.
  if null t2 then pkt t1
  else if null caar t1 then pkt t2
  else ((( ((caaar t1 . caar t2)
           .il_simp append(cadar t1,cadar t2)
           .nil
           )
         . cdr pv_times('!:pv . cdr t1,'!:pv . cdr t2)
         )
         where x=addmultsym(t1,t2)
        )
        where zz = b_expand(list('!:tensor,t1),list('!:tensor,t2))
                                                  %  Aug. 06, 1994
       )$

symbolic procedure addmultsym(t1,t2)$             % AK, Nov. 20, 1994
  addmsym(t1,t2,caar t1,caar t2)$

symbolic procedure addmsym(t1,t2,k1,k2)$
  % t1,t2 - tensors the product of them generate new symmtries.
  % k1,k2 - current name of t1,t2.
  if null k2 then !*basis:=b_expand1(t1,t2,!*basis,!*basis)
  else if null k1 then addmsym(t1,t2,caar t1,cdr k2)
  else if null(car k1 eq car k2) then addmsym(t1,t2,cdr k1,k2)
  else (addmsym(t1,t2,cdr k1,k2)
          where zz:=addmsym0(t1,t2,msymperm0(car t1,car t2,k1,k2))
       )$

symbolic procedure addmsym0(t1,t2,pz)$
  (addmultsym1(th . cdr pv_difference(z,'!:pv . pappl_pv(car pz,cdr z)))
  )where th = ((caaar t1 . caar t2) . cdr pz . nil),
           z = pv_times('!:pv . cdr t1,'!:pv . cdr t2)$

symbolic procedure msymperm0(th1,th2,k1,k2)$
  begin scalar il1,il2,n0,nam1,nam2,w1,w2,zl$
    nam1:=car th1$
    nam2:=car th2$
    il1:=cadr th1$
    il2:=cadr th2$
    n0:=length il1$
    il2:=il_simp append(il1,il2)$
    zl:=il2$
    il1:=nil$
    for i:=1:n0 do << il1:=car il2 . il1$ il2:=cdr il2 >>$
    il1:=reversip il1$
    w1:=nil$
    while null(nam1 eq k1) do <<
      n0:=get(car nam1,'!:tensor)$
      for i:=1:n0 do << w1:=car il1 . w1$ il1:=cdr il1 >>$
      nam1:=cdr nam1$
    >>$
    w2:=nil$
    while null(nam2 eq k2) do <<
      n0:=get(car nam2,'!:tensor)$
      for i:=1:n0 do << w2:=car il2 . w2$ il2:=cdr il2 >>$
      nam2:=cdr nam2$
    >>$
    n0:=get(car nam1,'!:tensor)$
    for i:=1:n0 do <<
      w1:=car il2 . w1$
      w2:=car il1 . w2$
      il1:=cdr il1$
      il2:=cdr il2$
    >>$
    w1:=append(reversip w1,il1)$
    w2:=append(reversip w2,il2)$
    return pfind(append(w1,w2),zl) . zl$
  end$

symbolic procedure addmultsym_(t1,t2)$ nil$

symbolic procedure addmultsym__(t1,t2)$
  if caaar t1 neq caaar t2
    then !*basis:=b_expand1(t1,t2,!*basis,!*basis)
  else((addmultsym1(th .
                    cdr pv_difference(z,'!:pv . pappl_pv(car pz,cdr z)))
       )where th = ((caaar t1 . caar t2) . cdr pz . nil),
              z = pv_times('!:pv . cdr t1,'!:pv . cdr t2)
      )where pz = msymperm(cadar t1,cadar t2)
%            ,zz = b_expand1(t1,t2,!*basis,!*basis)     %  Aug. 06, 1994
  $

symbolic procedure msymperm(il1,il2)$
  begin scalar zl,w,k;
    k:=length il1;
    zl:=il_simp append(il1,il2)$
    il2:=zl;
    for i:=1:k do << w:=car il2 . w; il2:=cdr il2>>;
    il1:=reversip w;
    w:=nil;
    for i:=1:k do << w:=car il2 . w; il2:=cdr il2>>;
    w:=reversip w;
    return pfind(append(w,append(il1,il2)),zl) . zl;
  end$

symbolic procedure addmultsym2(t1,t2,bs)$
  if null bs then nil
  else if null th_match0(car t2,caar bs)
         then addmultsym2(t1,t2,cdr bs)
  else rederr list "b_xpand?"$

symbolic procedure addmultsym1(te)$
  !*basis:=tsym2(list te,!*basis,nil)$

symbolic procedure pkt(t1)$ car t1 . pkpv(cdr t1,nil)$

symbolic procedure pkpv(pv,ppv)$
  if null pv then reversip ppv
  else pkpv(cdr pv,(caar pv . pkp cdar pv) . ppv)$

symbolic procedure t_difference(u,v)$
  t_plus(u,t_minus v)$

symbolic procedure t_zerop(u)$ null cdr u$

symbolic procedure t_onep u$ cdr u = 1$

symbolic procedure t_prep u$                         % mod. AK 28/03/96
  (if not(cddr caadr u = 1) then 'quotient . x . list cddr caadr u
   else x)
  where x=t_prep1(cdr u,nil)$

symbolic procedure t_prep1(u,v)$
  if null u
    then if null v then nil
         else if cdr v then 'plus . reversip v
         else car v
  else t_prep1(cdr u,th2pe(caar u,cdar u) . v)$

%symbolic procedure t_prep u$ th2pe(cadr u,cddr u)$

symbolic procedure t_pri(u)$ t_pri1(u,nil)$

symbolic procedure t_intequiv u$
  atom cdr u$

symbolic procedure i2tensor n$
  '!:tensor . n$

symbolic procedure t_expt(u,n)$
  if n=1 then u
  else if atom cdr u then cdr u^n
  else rederr list('t_expt,"*** Can't powered tensor")$

symbolic procedure t_quotient(u,c)$
  if t_intequiv c and cdr c = 1 then u
  else rederr list('t_quotient,"*** Tensor can't be divided by: ",c)$

symbolic procedure t_divide(u,v)$
  rederr list('t_divide,"*** Can't divide tensor by tensor")$

symbolic procedure t_gcd(u,v)$                   % AK 28/03/96
  if atom cdr v then 1
  else rederr list('t_gcd,"*** Can't find gcd of two tensors")$

initdmode 'tensor$

endmodule;

end;