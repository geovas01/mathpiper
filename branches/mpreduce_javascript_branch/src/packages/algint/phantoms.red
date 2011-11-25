MODULE PHANTOMS;

% Author: James H. Davenport.

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


FLUID '(!*trint CANCELLATIONLIST INEQUALITYSTACK LHS MAGICLIST NINDEX
        TERMSUSED);

EXPORTS PHANTOMTERMS;

IMPORTS GETV,!*MULTF,ADDF;

% See if an exponent set agrees with a set of linear constraints.


SYMBOLIC PROCEDURE MATCHP(POW,EQUALITIES,INEQUALITIES);
%TRUE IF THE EQUALITIES AND INEQUALITIES GIVEN ARE CONSISTENT WITH;
%THE GIVEN POWER;
 BEGIN SCALAR FG;
TOP:
    IF NULL EQUALITIES THEN GO TO INEQ;
    FG:=MATCHEQNP(POW,CAR EQUALITIES);
    IF NULL FG THEN RETURN NIL; %CLASH WITH AN EQUALITY;
    EQUALITIES:=CDR EQUALITIES;
    GO TO TOP;
INEQ:
    IF NULL INEQUALITIES THEN RETURN T; %OK - CONSISTENT;
    FG:=MATCHEQNP(POW,CAR INEQUALITYSTACK);
    IF FG THEN RETURN NIL; %CLASH WITH AN INEQUALITY;
    INEQUALITIES:=CDR INEQUALITIES;
    GO TO INEQ END;


SYMBOLIC PROCEDURE MATCHEQNP(POW,EQQN);
% SUBSTITUTE INTEGERS GIVEN BY POW INTO LINEAR
% FORM EQQN AND RETURN T IF RESULT IS ZERO;
 BEGIN SCALAR I,RES;
    RES:=GETV(EQQN,1); %CONSTANT TERM FROM THE EQUATION;
    FOR I:=2:NINDEX DO <<
      RES:=ADDF(RES,!*MULTF(!*N2F CAR POW,GETV(EQQN,I)));
      POW:=CDR POW >>;
    IF NULL RES THEN RETURN T
                ELSE RETURN NIL
 END;

%SYMBOLIC PROCEDURE !*N2F N;
%% CONVERT NUMBER INTO STANDARD FORM;
%    IF N=0 THEN NIL
%    ELSE N;

SYMBOLIC PROCEDURE PHANTOMTERMS(LHS);
% PUT EXTRA TERM ON THE FRONT OF LHS TO ALLOW FOR MALICE IN;
% CANCELLING TERMS. GIVE IT A ZERO WEIGHT;
 BEGIN SCALAR R,S;
    IF !*TRINT THEN PRINTC "PHANTOMTERMS CALLED ON";
    IF !*TRINT THEN SUPERPRINT LHS;
    R:=FINDPHANTOMS(LPOW LHS); %THINGS THAT MAY BE NEEDED;
    IF !*TRINT THEN PRINTC "FINDPHANTOMS RETURNED";
    IF !*TRINT THEN PRINTC R;
    WHILE R DO <<
       IF NOT MEMBER(CAR R,TERMSUSED) THEN S:=(CAR R) . S;
       R:=CDR R >>;
    IF !*TRINT THEN PRINTC "UNUSED PHANTOMS=";
    S:=REVERSEWOC S; %BACK IN RIGHT ORDER AGAIN;
    IF !*TRINT THEN PRINTC S;
    IF NOT NULL S THEN <<
    IF !*TRINT THEN   PRINTC "PHANTOM TERM GENERATED IN INTEGRAND";
    IF !*TRINT THEN   PRINTC "EXPONENT SET IS";
    IF !*TRINT THEN   PRINTC CAR S >>;
    IF NULL S THEN R:=LHS
    ELSE R := ((CAR S) . (NIL . 1)) . LHS;
    RETURN R END;


SYMBOLIC PROCEDURE FINDPHANTOMS(POW);
 BEGIN SCALAR L,W;
    L:=CANCELLATIONLIST;
TOP:
    IF NULL L THEN RETURN CDRXX ASSOC(POW,MAGICLIST);
%SEEK USER HELP ON MAGICLIST;
    W:=PHANTOMMATCH(POW,CAR L);
    IF NOT NULL W THEN RETURN W;
    L:=CDR L;
    GO TO TOP END;


SYMBOLIC PROCEDURE CDRXX A;
    IF ATOM A THEN NIL
    ELSE CDR A;


SYMBOLIC PROCEDURE PHANTOMMATCH(POW,PATTERN);
% ITEMS ON CANCELLATIONLIST ARE (CASE . EFFECT) WHERE
% CASE = (EQUALITIES . INEQUALITIES) - A SET OF CONSTRAINTS
% ON THE POWER THAT MUST BE SATISFIED BEFORE IT IS SPECIAL,
% AND EFFECT IS A LIST OF OFFSETS THAT HAVE TO BE SUBTRACTED
% TO POW TO GET THE POWERS REPRESENTING GENERATED PHANTOMS;
 BEGIN SCALAR R;
    R:=MATCHP(POW,CAAR PATTERN,CDAR PATTERN);
    IF NULL R THEN RETURN NIL; %TEMPLATE DOES NOT FIT;
    RETURN OFFSETLIST(POW,CDR PATTERN)
 END;

SYMBOLIC PROCEDURE OFFSETLIST(POW,L);
    IF NULL L THEN NIL
    ELSE BEGIN
        SCALAR W;
        W:=OFFSET1(POW,CAR L);
        L:=OFFSETLIST(POW,CDR L);
        IF W THEN L:=W . L;
        RETURN L END;

SYMBOLIC PROCEDURE OFFSET1(POW,DELTA);
% COMPUTE OFFSET VERSION OF EXPONENT SET - RETURN NIL IF THIS
% WOULD INVOLVE NEGATIVE POWERS;
  BEGIN
    SCALAR W;
TOP:
    IF NULL POW THEN RETURN REVERSEWOC W;
    W:=(CAR POW - CAR DELTA) . W;
    IF MINUSP CAR W THEN RETURN NIL;
    POW:=CDR POW; DELTA:=CDR DELTA;
    GOTO TOP
   END;

ENDMODULE;

END;
