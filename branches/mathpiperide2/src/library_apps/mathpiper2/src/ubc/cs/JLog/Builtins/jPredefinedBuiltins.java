/*
    This file is part of JLog.

    Created by Glendon Holst for Alan Mackworth and the 
    "Computational Intelligence: A Logical Approach" text.
    
    Copyright 1998, 2000, 2002, 2008 by University of British Columbia, 
    Alan Mackworth and Glendon Holst.
    
    This notice must remain in all files which belong to, or are derived 
    from JLog.
    
    Check <http://jlogic.sourceforge.net/> or 
    <http://sourceforge.net/projects/jlogic> for further information
    about JLog, or to contact the authors.

    JLog is free software, dual-licensed under both the GPL and MPL 
    as follows:

    You can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Or, you can redistribute it and/or modify
    it under the terms of the Mozilla Public License as published by
    the Mozilla Foundation; version 1.1 of the License.

    JLog is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License, or the Mozilla Public License for 
    more details.

    You should have received a copy of the GNU General Public License
    along with JLog, in the file GPL.txt; if not, write to the 
    Free Software Foundation, Inc., 
    59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
    URLs: <http://www.fsf.org> or <http://www.gnu.org>

    You should have received a copy of the Mozilla Public License
    along with JLog, in the file MPL.txt; if not, contact:
    http://http://www.mozilla.org/MPL/MPL-1.1.html
    URLs: <http://www.mozilla.org/MPL/>
*/
//#########################################################################
//	PredefinedBuiltins
//#########################################################################
 
package ubc.cs.JLog.Builtins;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Parser.*;

/**
* Creates all the non-builtin predefined predicates needed by the 
* <code>jKnowledgeBase</code> and registers all the operator and predicate entries with
* their corresponding registries for use by the parser. 
*  
* @author       Glendon Holst
* @version      %I%, %G%
*/
public class jPredefinedBuiltins extends jPredefined
{
 public 	jPredefinedBuiltins(jPrologServices ps,String lib)
 {
  super(ps,lib);
 };

 public void		register()
 {
  registerPredefined();
 };
 
 protected void 	registerPredefined()
 {
  addRules(new jRule[] {get_not1(),get_not2()});
  
  addRules(new jRule[] {get_if_then()});
  addRules(new jRule[] {get_if_then_else()});

  addRules(new jRule[] {get_if()});
  addRules(new jRule[] {get_IF1(),get_IF2()});

  addRules(new jRule[] {get_or()});

  addRules(new jRule[] {get_cons()});

  addRules(new jRule[] {get_retractall1(),get_retractall2()});

  addRules(new jRule[] {get_abolish()}); 

  addRules(new jRule[] {get_findall()}); 
  
  addRules(new jRule[] {get_findall4()});

  addRules(new jRule[] {get_FINDALL1(),get_FINDALL2()});
  
  addRules(new jRule[] {get_bagof()}); 

  addRules(new jRule[] {get_BAGOF()}); 

  addRules(new jRule[] {get_BAGOFPOSTSORT1(),get_BAGOFPOSTSORT2()});

  addRules(new jRule[] {get_BAGOFPOSTSORTHELPER1(),get_BAGOFPOSTSORTHELPER2()});

  addRules(new jRule[] {get_BAGOFENUMERATE1(),get_BAGOFENUMERATE2()});
  
  addRules(new jRule[] {get_setof()}); 

  addRules(new jRule[] {get_DCGTERMINAL()}); 

  addRules(new jRule[] {get_expand_term()}); 

  addRules(new jRule[] {get_phrase2()});
  addRules(new jRule[] {get_phrase3()}); 

  addRules(new jRule[] {get_length1(),get_length2()}); 

  addRules(new jRule[] {get_writeln()}); 
  addRules(new jRule[] {get_nl()}); 

  addRules(new jRule[] {get_undo1(),get_undo2()}); 

  addRules(new jRule[] {get_current_op()}); 

  addRules(new jRule[] {get_CURRENTOP1(),get_CURRENTOP2()}); 

  addRules(new jRule[] {get_atom_chars()}); 
  addRules(new jRule[] {get_number_chars()}); 

  addRules(new jRule[] {get_once()}); 
  addRules(new jRule[] {get_ignore1(),get_ignore2()}); 

  consultDatabase();
 };
  
 protected jBuiltinRule 	get_not1()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X;
  
// not(X) :- call(X),!,fail.

  X = new jVariable("X");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  
  pt = new jPredicateTerms();
  pt.addPredicate(new jCall(X));
  pt.addPredicate(new jCut());
  pt.addPredicate(jFail.FAIL);
     
  return new jBuiltinRule(new jPredicate("not",ct1),pt);
 };
 
 protected jBuiltinRule 	get_not2()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X;
  
// not(X).

  X = new jVariable("X");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  
  pt = new jPredicateTerms();
     
  return new jBuiltinRule(new jPredicate("not",ct1),pt);
 };
 
 protected jBuiltinRule 	get_if_then()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		P,Q;
  
// ->(P,Q) :- call(P),!,call(Q).

  P = new jVariable("P");
  Q = new jVariable("Q");

  ct1 = new jCompoundTerm();
  ct1.addTerm(P);
  ct1.addTerm(Q);
  
  pt = new jPredicateTerms();
  pt.addPredicate(new jCall(P));
  pt.addPredicate(new jCut());
  pt.addPredicate(new jCall(Q));
     
  return new jBuiltinRule(new jPredicate("->",ct1),pt);
 };
 
 protected jBuiltinRule 	get_if_then_else()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt,pt1,pt2;
  jOrPredicate		orp;
  jVariable 		P,Q,R;
  
// ->(P,Q,R) :- call(P),!,call(Q) ; call(R).

  P = new jVariable("P");
  Q = new jVariable("Q");
  R = new jVariable("R");

  ct1 = new jCompoundTerm();
  ct1.addTerm(P);
  ct1.addTerm(Q);
  ct1.addTerm(R);
  
  pt1 = new jPredicateTerms();
  pt1.addPredicate(new jCall(P));
  pt1.addPredicate(new jCut());
  pt1.addPredicate(new jCall(Q));
     
  pt2 = new jPredicateTerms();
  pt2.addPredicate(new jCall(R));

  orp = new jOrPredicate();
  orp.addPredicateTerms(pt1);
  orp.addPredicateTerms(pt2);
  
  pt = new jPredicateTerms();
  pt.addPredicate(orp);
       
  return new jBuiltinRule(new jPredicate("->",ct1),pt);
 };

 protected jBuiltinRule 	get_if()
 {jCompoundTerm 	ct1,ct2;
  jPredicateTerms 	pt;
  jVariable 		P,Q,R,A;
  
// if(P,Q,R) :- 'CREATEARRAY'(1,A),'SETARRAYELEMENT'(A,0,fail), 'IF'(P,Q,R,A).

  P = new jVariable("P");
  Q = new jVariable("Q");
  R = new jVariable("R");
  A = new jVariable("A");

  ct1 = new jCompoundTerm();
  ct1.addTerm(P);
  ct1.addTerm(Q);
  ct1.addTerm(R);

  ct2 = new jCompoundTerm();
  ct2.addTerm(P);
  ct2.addTerm(Q);
  ct2.addTerm(R);
  ct2.addTerm(A);
  
  pt = new jPredicateTerms();
  pt.addPredicate(new jCreateArray(new jInteger(1),A));
  pt.addPredicate(new jSetArrayElement(A,new jInteger(0),new jAtom("fail")));
  pt.addPredicate(new jPredicate("IF",ct2));
       
  return new jBuiltinRule(new jPredicate("if",ct1),pt);
 };
 
 protected jBuiltinRule 	get_IF1()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		P,Q,R,A;
  
// 'IF'(P,Q,_,A) :- call(P), 'SETARRAYELEMENT'(A,0,true), call(Q).

  P = new jVariable("P");
  Q = new jVariable("Q");
  R = new jVariable();
  A = new jVariable("A");

  ct1 = new jCompoundTerm();
  ct1.addTerm(P);
  ct1.addTerm(Q);
  ct1.addTerm(R);
  ct1.addTerm(A);

  pt = new jPredicateTerms();
  pt.addPredicate(new jCall(P));
  pt.addPredicate(new jSetArrayElement(A,new jInteger(0),new jAtom("true")));
  pt.addPredicate(new jCall(Q));
     
  return new jBuiltinRule(new jPredicate("IF",ct1),pt);
 };
 
 protected jBuiltinRule 	get_IF2()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		P,Q,R,A;
  
// 'IF'(_,_,R,A) :- 'GETARRAYELEMENT'(A,0,fail), call(R).

  P = new jVariable();
  Q = new jVariable();
  R = new jVariable("R");
  A = new jVariable("A");

  ct1 = new jCompoundTerm();
  ct1.addTerm(P);
  ct1.addTerm(Q);
  ct1.addTerm(R);
  ct1.addTerm(A);

  pt = new jPredicateTerms();
  pt.addPredicate(new jGetArrayElement(A,new jInteger(0),new jAtom("fail")));
  pt.addPredicate(new jCall(R));
     
  return new jBuiltinRule(new jPredicate("IF",ct1),pt);
 };

 protected jBuiltinRule 	get_or()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt,pt1,pt2;
  jOrPredicate		orp;
  jVariable 		X,Y;
  
// ;(X,Y) :- call(X) ; call(Y).

  X = new jVariable("X");
  Y = new jVariable("Y");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  ct1.addTerm(Y);
  
  pt1 = new jPredicateTerms();
  pt1.addPredicate(new jCall(X));
     
  pt2 = new jPredicateTerms();
  pt2.addPredicate(new jCall(Y));

  orp = new jOrPredicate();
  orp.addPredicateTerms(pt1);
  orp.addPredicateTerms(pt2);
  
  pt = new jPredicateTerms();
  pt.addPredicate(orp);
       
  return new jBuiltinRule(new jPredicate(";",ct1),pt);
 };
 
 protected jBuiltinRule 	get_cons()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X,Y;
  
// ','(X,Y) :- call(X),call(Y).

  X = new jVariable("X");
  Y = new jVariable("Y");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  ct1.addTerm(Y);
  
  pt = new jPredicateTerms();
  pt.addPredicate(new jCall(X));
  pt.addPredicate(new jCall(Y));
     
  return new jBuiltinRule(new jPredicate(",",ct1),pt);
 };
  
 protected jBuiltinRule 	get_retractall1()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X;
  
// retractall(X) :- retract(X),fail.

  X = new jVariable("X");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  
  pt = new jPredicateTerms();
  pt.addPredicate(new jRetract(X));
  pt.addPredicate(jFail.FAIL);
     
  return new jBuiltinRule(new jPredicate("retractall",ct1),pt);
 };

 protected jBuiltinRule 	get_retractall2()
 {jCompoundTerm 	ct1;
  jVariable 		X;
  
// retractall(_).

  X = new jVariable();

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);

  return new jBuiltinRule(new jPredicate("retractall",ct1),new jPredicateTerms());
 };

 protected jBuiltinRule 	get_abolish()
 {jCompoundTerm 	ct1, ct2;
  jPredicateTerms 	pt;
  jVariable 		X, Y, Z;
  
// abolish(X,Y) :- functor(Z,X,Y), retractall(Z).

  X = new jVariable("X");
  Y = new jVariable("Y");
  Z = new jVariable("Z");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  ct1.addTerm(Y);

  ct2 = new jCompoundTerm();
  ct2.addTerm(Z);
    
  pt = new jPredicateTerms();
  pt.addPredicate(new jFunctor(Z,X,Y));
  pt.addPredicate(new jPredicate("retractall",ct2));
     
  return new jBuiltinRule(new jPredicate("abolish",ct1),pt);
 };

 protected jBuiltinRule 	get_findall()
 {jCompoundTerm 	ct1,ct2;
  jPredicateTerms 	pt;
  jVariable 		X,Y,Z,A;
  
// findall(X,Y,Z) :- A = *compoundterm*,'FINDALL'(X,Y,A),'CONVERTARRAY'(A,Z,[]).

  X = new jVariable("X");
  Y = new jVariable("Y");
  Z = new jVariable("Z");
  A = new jVariable("A");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  ct1.addTerm(Y);
  ct1.addTerm(Z);
  
  ct2 = new jCompoundTerm();
  ct2.addTerm(X);
  ct2.addTerm(Y);
  ct2.addTerm(A);
  
  pt = new jPredicateTerms();
  pt.addPredicate(new jUnify(A,new jCompoundTerm()));
  pt.addPredicate(new jPredicate("FINDALL",ct2));
  pt.addPredicate(new jConvertArray(A,Z,jNullList.NULL_LIST));
     
  return new jBuiltinRule(new jPredicate("findall",ct1),pt);
 };

 protected jBuiltinRule 	get_findall4()
 {jCompoundTerm 	ct1,ct2;
  jPredicateTerms 	pt;
  jVariable 		X,Y,Z,R,A;
  
// findall(X,Y,Z,R) :- A = *compoundterm*,'FINDALL'(X,Y,A),'CONVERTARRAY'(A,Z,R).

  X = new jVariable("X");
  Y = new jVariable("Y");
  Z = new jVariable("Z");
  R = new jVariable("R");
  A = new jVariable("A");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  ct1.addTerm(Y);
  ct1.addTerm(Z);
  ct1.addTerm(R);
  
  ct2 = new jCompoundTerm();
  ct2.addTerm(X);
  ct2.addTerm(Y);
  ct2.addTerm(A);
  
  pt = new jPredicateTerms();
  pt.addPredicate(new jUnify(A,new jCompoundTerm()));
  pt.addPredicate(new jPredicate("FINDALL",ct2));
  pt.addPredicate(new jConvertArray(A,Z,R));
     
  return new jBuiltinRule(new jPredicate("findall",ct1),pt);
 };

 protected jBuiltinRule 	get_FINDALL1()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X,Y,A;
  
// 'FINDALL'(X,Y,A) :- call(Y),'APPENDARRAY'(A,X),fail.

  X = new jVariable("X");
  Y = new jVariable("Y");
  A = new jVariable("A");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  ct1.addTerm(Y);
  ct1.addTerm(A);
  
  pt = new jPredicateTerms();
  pt.addPredicate(new jCall(Y));
  pt.addPredicate(new jAppendArray(A,X));
  pt.addPredicate(jFail.FAIL);
     
  return new jBuiltinRule(new jPredicate("FINDALL",ct1),pt);
 };

 protected jBuiltinRule 	get_FINDALL2()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X,Y,A;
  
// FINDALL(_,_,_).

  X = new jVariable();
  Y = new jVariable();
  A = new jVariable();

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  ct1.addTerm(Y);
  ct1.addTerm(A);

  pt = new jPredicateTerms();
  
  return new jBuiltinRule(new jPredicate("FINDALL",ct1),pt);
 };
 
 protected jBuiltinRule 	get_bagof()
 {jCompoundTerm 	ct1,ct2;
  jPredicateTerms 	pt;
  jVariable 		A,B,C,X,Y,Z,L;
  
// bagof(X,Y,Z) :- A = *compoundterm*,B = *compoundterm*,C = *compoundterm*,
//				   'ENUMERATEVARIABLESARRAY'(A,X,true),'ENUMERATEVARIABLESARRAY'(B,Y,false),
// 				   'SUBTRACTARRAY'(C,B,A),'CONVERTARRAY'(C,L,[]),!,BAGOF(X,L,Y,Z).

  A = new jVariable("A");
  B = new jVariable("B");
  C = new jVariable("C");
  X = new jVariable("X");
  Y = new jVariable("Y");
  Z = new jVariable("Z");
  L = new jVariable("L");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  ct1.addTerm(Y);
  ct1.addTerm(Z);
  
  ct2 = new jCompoundTerm();
  ct2.addTerm(X);
  ct2.addTerm(C);
  ct2.addTerm(Y);
  ct2.addTerm(Z);
  
  pt = new jPredicateTerms();
  pt.addPredicate(new jUnify(A,new jCompoundTerm()));
  pt.addPredicate(new jUnify(B,new jCompoundTerm()));
  pt.addPredicate(new jUnify(C,new jCompoundTerm()));
  pt.addPredicate(new jEnumerateVariablesArray(A,X,true));
  pt.addPredicate(new jEnumerateVariablesArray(B,Y,false));
  pt.addPredicate(new jSubtractArray(C,B,A));
  pt.addPredicate(new jConvertArray(C,L,jNullList.NULL_LIST));
  pt.addPredicate(new jCut());
  pt.addPredicate(new jPredicate("BAGOF",ct2));
     
  return new jBuiltinRule(new jPredicate("bagof",ct1),pt);
 };

 protected jBuiltinRule 	get_BAGOF()
 {jCompoundTerm 	ct1,ct2,ct3,ct4;
  jPredicateTerms 	pt;
  jVariable 		X,Y,C,B,Z,A,Y1,A1;
  
// 'BAGOF'(X,Y,C,B) :- findall(Y-X,C,Z),keysort(Z,A,true),'BAGOFPOSTSORT'(_,A,A1),!,
// 						'BAGOFENUMERATE'(A1,[Y,B]).

  X = new jVariable("X");
  Y = new jVariable("Y");
  C = new jVariable("C");
  B = new jVariable("B");
  Z = new jVariable("Z");
  A = new jVariable("A");
  A1 = new jVariable("A1");
  Y1 = new jVariable();

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  ct1.addTerm(Y);
  ct1.addTerm(C);
  ct1.addTerm(B);
  
  ct2 = new jCompoundTerm();
  ct2.addTerm(new jSubtract(Y,X));
  ct2.addTerm(C);
  ct2.addTerm(Z);

  ct3 = new jCompoundTerm();
  ct3.addTerm(Y1);
  ct3.addTerm(A);
  ct3.addTerm(A1);

  ct4 = new jCompoundTerm();
  ct4.addTerm(A1);
  ct4.addTerm(new jListPair(Y,new jListPair(B,jNullList.NULL_LIST)));

  pt = new jPredicateTerms();
  pt.addPredicate(new jPredicate("findall",ct2));
  pt.addPredicate(new jKeysort(Z,A,true));
  pt.addPredicate(new jPredicate("BAGOFPOSTSORT",ct3));
  pt.addPredicate(new jCut());
  pt.addPredicate(new jPredicate("BAGOFENUMERATE",ct4));
     
  return new jBuiltinRule(new jPredicate("BAGOF",ct1),pt);
 };

 protected jBuiltinRule 	get_BAGOFPOSTSORT1()
 {jCompoundTerm 	ct1,ct2,ct3;
  jPredicateTerms 	pt;
  jVariable 		Y,X,XYs,A,B,B1,Y1;
  
// 'BAGOFPOSTSORT'(Y,[Y-X|XYs],[[Y,[X|A]]|B]) :- 'BAGOFPOSTSORTHELPER'(Y,XYs,A,B1),!,
// 												 'BAGOFPOSTSORT'(_,B1,B).

  X = new jVariable("X");
  Y = new jVariable("Y");
  XYs = new jVariable("XYs");
  A = new jVariable("A");
  B = new jVariable("B");
  B1 = new jVariable("B1");
  Y1 = new jVariable();

  ct1 = new jCompoundTerm();
  ct1.addTerm(Y);
  ct1.addTerm(new jListPair(new jSubtract(Y,X),XYs));
  ct1.addTerm(new jListPair(new jListPair(Y,
									new jListPair(new jListPair(X,A),jNullList.NULL_LIST)),B));
  
  ct2 = new jCompoundTerm();
  ct2.addTerm(Y);
  ct2.addTerm(XYs);
  ct2.addTerm(A);
  ct2.addTerm(B1);

  ct3 = new jCompoundTerm();
  ct3.addTerm(Y1);
  ct3.addTerm(B1);
  ct3.addTerm(B);

  pt = new jPredicateTerms();
  pt.addPredicate(new jPredicate("BAGOFPOSTSORTHELPER",ct2));
  pt.addPredicate(new jCut());
  pt.addPredicate(new jPredicate("BAGOFPOSTSORT",ct3));
     
  return new jBuiltinRule(new jPredicate("BAGOFPOSTSORT",ct1),pt);
 };

 protected jBuiltinRule 	get_BAGOFPOSTSORT2()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X;
  
// 'BAGOFPOSTSORT'(_,[],[]).

  X = new jVariable();

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  ct1.addTerm(jNullList.NULL_LIST);
  ct1.addTerm(jNullList.NULL_LIST);

  pt = new jPredicateTerms();
  
  return new jBuiltinRule(new jPredicate("BAGOFPOSTSORT",ct1),pt);
 };

 protected jBuiltinRule 	get_BAGOFPOSTSORTHELPER1()
 {jCompoundTerm 	ct1,ct2;
  jPredicateTerms 	pt;
  jVariable 		Y,X,XYs,A,B,B1,Y1;
  
// 'BAGOFPOSTSORTHELPER'(Y,[Y1-X|XYs],[X|A],B) :- =@=(Y,Y1),!,
// 												 'BAGOFPOSTSORTHELPER'(Y,XYs,A,B).

  X = new jVariable("X");
  Y = new jVariable("Y");
  XYs = new jVariable("XYs");
  A = new jVariable("A");
  B = new jVariable("B");
  B1 = new jVariable("B1");
  Y1 = new jVariable("Y1");

  ct1 = new jCompoundTerm();
  ct1.addTerm(Y);
  ct1.addTerm(new jListPair(new jSubtract(Y1,X),XYs));
  ct1.addTerm(new jListPair(X,A));
  ct1.addTerm(B);
  
  ct2 = new jCompoundTerm();
  ct2.addTerm(Y);
  ct2.addTerm(XYs);
  ct2.addTerm(A);
  ct2.addTerm(B);

  pt = new jPredicateTerms();
  pt.addPredicate(new jEquivalence(Y,Y1));
  pt.addPredicate(new jCut());
  pt.addPredicate(new jPredicate("BAGOFPOSTSORTHELPER",ct2));
     
  return new jBuiltinRule(new jPredicate("BAGOFPOSTSORTHELPER",ct1),pt);
 };

 protected jBuiltinRule 	get_BAGOFPOSTSORTHELPER2()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X,XYs;
  
// 'BAGOFPOSTSORTHELPER'(_,XYs,[],XYs).

  X = new jVariable();
  XYs = new jVariable("XYs");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  ct1.addTerm(XYs);
  ct1.addTerm(jNullList.NULL_LIST);
  ct1.addTerm(XYs);

  pt = new jPredicateTerms();
  
  return new jBuiltinRule(new jPredicate("BAGOFPOSTSORTHELPER",ct1),pt);
 };

 protected jBuiltinRule 	get_BAGOFENUMERATE1()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		A,As;
  
// 'BAGOFENUMERATE'([A|_],A).

  A = new jVariable("A");
  As = new jVariable();

  ct1 = new jCompoundTerm();
  ct1.addTerm(new jListPair(A,As));
  ct1.addTerm(A);

  pt = new jPredicateTerms();
     
  return new jBuiltinRule(new jPredicate("BAGOFENUMERATE",ct1),pt);
 };

 protected jBuiltinRule 	get_BAGOFENUMERATE2()
 {jCompoundTerm 	ct1,ct2;
  jPredicateTerms 	pt;
  jVariable 		A,As,B;
  
// 'BAGOFENUMERATE'([A|As],B) :- 'BAGOFENUMERATE'(As,B).

  A = new jVariable("A");
  As = new jVariable("As");
  B = new jVariable("B");

  ct1 = new jCompoundTerm();
  ct1.addTerm(new jListPair(A,As));
  ct1.addTerm(B);

  ct2 = new jCompoundTerm();
  ct2.addTerm(As);
  ct2.addTerm(B);

  pt = new jPredicateTerms();
  pt.addPredicate(new jPredicate("BAGOFENUMERATE",ct2));
  
  return new jBuiltinRule(new jPredicate("BAGOFENUMERATE",ct1),pt);
 };
 
 protected jBuiltinRule 	get_setof()
 {jCompoundTerm 	ct1,ct2;
  jPredicateTerms 	pt;
  jVariable 		T,X,Y,Z;
  
// setof(X,Y,Z) :- bagof(X,Y,T),sort(T,Z).

  T = new jVariable("T");
  X = new jVariable("X");
  Y = new jVariable("Y");
  Z = new jVariable("Z");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  ct1.addTerm(Y);
  ct1.addTerm(Z);
  
  ct2 = new jCompoundTerm();
  ct2.addTerm(X);
  ct2.addTerm(Y);
  ct2.addTerm(T);
  
  pt = new jPredicateTerms();
  pt.addPredicate(new jPredicate("bagof",ct2));
  pt.addPredicate(new jSort(T,Z));
     
  return new jBuiltinRule(new jPredicate("setof",ct1),pt);
 };
 
 protected jBuiltinRule 	get_DCGTERMINAL()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X,S;
  
// 'DCGTERMINAL'([X|S],X,S).

  X = new jVariable("X");
  S = new jVariable("S");

  ct1 = new jCompoundTerm();
  ct1.addTerm(new jListPair(X,S));
  ct1.addTerm(X);
  ct1.addTerm(S);
  
  pt = new jPredicateTerms();
     
  return new jBuiltinRule(new jPredicate("DCGTERMINAL",ct1),pt);
 };
 
 protected jBuiltinRule 	get_expand_term()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X,Y,S0,S;
  
// expand_term(X,Y) :- expand_term(X,Y,S0,S).

  X = new jVariable("X");
  Y = new jVariable("Y");
  S0 = new jVariable("S0");
  S = new jVariable("S");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  ct1.addTerm(Y);

  pt = new jPredicateTerms();
  pt.addPredicate(new jExpandTerm(X,Y,S0,S));
  
  return new jBuiltinRule(new jPredicate("expand_term",ct1),pt);
 };

 protected jBuiltinRule 	get_phrase2()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X,Y,P;
  
// phrase(X,Y) :- expand_term(X,P,Y,[]),call(P).

  X = new jVariable("X");
  Y = new jVariable("Y");
  P = new jVariable("P");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  ct1.addTerm(Y);
 
  pt = new jPredicateTerms();
  pt.addPredicate(new jExpandTerm(X,P,Y,jNullList.NULL_LIST));
  pt.addPredicate(new jCall(P));
  
  return new jBuiltinRule(new jPredicate("phrase",ct1),pt);
 };

 protected jBuiltinRule 	get_phrase3()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X,Y,P,Z;
  
// phrase(X,Y,Z) :- expand_term(X,P,Y,Z),call(P).

  X = new jVariable("X");
  Y = new jVariable("Y");
  P = new jVariable("P");
  Z = new jVariable("Z");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  ct1.addTerm(Y);
  ct1.addTerm(Z);
 
  pt = new jPredicateTerms();
  pt.addPredicate(new jExpandTerm(X,P,Y,Z));
  pt.addPredicate(new jCall(P));
  
  return new jBuiltinRule(new jPredicate("phrase",ct1),pt);
 };

 protected jBuiltinRule 	get_length1()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  
// length([],0).

  ct1 = new jCompoundTerm();
  ct1.addTerm(jNullList.NULL_LIST);
  ct1.addTerm(new jInteger(0));
 
  pt = new jPredicateTerms();
  
  return new jBuiltinRule(new jPredicate("length",ct1),pt);
 };

 protected jBuiltinRule 	get_length2()
 {jCompoundTerm 	ct1,ct2;
  jPredicateTerms 	pt,pt1,pt2;
  jOrPredicate		orp;
  jVariable 		X,S,Y,Y1;
  
// length([X|S],Y) :- (var(Y) ; integer(Y)),length(S,Y1),Y is Y1 + 1.

  X = new jVariable("X");
  S = new jVariable("S");
  Y = new jVariable("Y");
  Y1 = new jVariable("Y1");

  ct1 = new jCompoundTerm();
  ct1.addTerm(new jListPair(X,S));
  ct1.addTerm(Y);
 
  ct2 = new jCompoundTerm();
  ct2.addTerm(S);
  ct2.addTerm(Y1);

  pt1 = new jPredicateTerms();
  pt1.addPredicate(new jIsVariable(Y));
     
  pt2 = new jPredicateTerms();
  pt2.addPredicate(new jIsInteger(Y));

  orp = new jOrPredicate();
  orp.addPredicateTerms(pt1);
  orp.addPredicateTerms(pt2);
 
  pt = new jPredicateTerms();
  pt.addPredicate(orp);
  pt.addPredicate(new jPredicate("length",ct2));
  pt.addPredicate(new jIs(Y,new jAdd(Y1,new jInteger(1))));
  
  return new jBuiltinRule(new jPredicate("length",ct1),pt);
 };

 protected jBuiltinRule 	get_writeln()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X;
  
// writeln(X) :- write(X), nl.

  X = new jVariable("X");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
 
  pt = new jPredicateTerms();
  pt.addPredicate(new jWrite(X,true));
  
  return new jBuiltinRule(new jPredicate("writeln",ct1),pt);
 };

 protected jBuiltinRule 	get_nl()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  
//nl.

  ct1 = new jCompoundTerm();
 
  pt = new jPredicateTerms();
  pt.addPredicate(new jWrite(true));
  
  return new jBuiltinRule(new jPredicate("nl",ct1),pt);
 };

 protected jBuiltinRule 	get_undo1()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X;
    
// undo(X).

  X = new jVariable("X");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
 
  pt = new jPredicateTerms();
  
  return new jBuiltinRule(new jPredicate("undo",ct1),pt);
 };

 protected jBuiltinRule 	get_undo2()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X;
  
// undo(X) :- call(X),fail.

  X = new jVariable("X");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
 
  pt = new jPredicateTerms();
  pt.addPredicate(new jCall(X));
  pt.addPredicate(jFail.FAIL);
  
  return new jBuiltinRule(new jPredicate("undo",ct1),pt);
 };

 protected jBuiltinRule 	get_current_op()
 {jCompoundTerm 	ct1,ct2;
  jPredicateTerms 	pt;
  jVariable 		X,Y,Z,B,A;
  
// current_op(X,Y,Z) :- 'CURRENTOPLIST'(A),sort(A,B),!,'CURRENTOP'(B,X,Y,Z).

  X = new jVariable("X");
  Y = new jVariable("Y");
  Z = new jVariable("Z");
  B = new jVariable("B");
  A = new jVariable("A");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  ct1.addTerm(Y);
  ct1.addTerm(Z);
  
  ct2 = new jCompoundTerm();
  ct2.addTerm(B);
  ct2.addTerm(X);
  ct2.addTerm(Y);
  ct2.addTerm(Z);
  
  pt = new jPredicateTerms();
  pt.addPredicate(new jCurrentOpList(A));
  pt.addPredicate(new jSort(A,B));
  pt.addPredicate(new jCut());
  pt.addPredicate(new jPredicate("CURRENTOP",ct2));
     
  return new jBuiltinRule(new jPredicate("current_op",ct1),pt);
 };

 protected jBuiltinRule 	get_CURRENTOP1()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X,Y,Z,Xs;
  
// 'CURRENTOP'([[X,Y,Z]|Xs],X,Y,Z).

  X = new jVariable("X");
  Y = new jVariable("Y");
  Z = new jVariable("Z");
  Xs = new jVariable("Xs");

  ct1 = new jCompoundTerm();
  ct1.addTerm(new jListPair(new jListPair(X,new jListPair(Y,Z)),Xs));
  ct1.addTerm(X);
  ct1.addTerm(Y);
  ct1.addTerm(Z);

  pt = new jPredicateTerms();
  
  return new jBuiltinRule(new jPredicate("CURRENTOP",ct1),pt);
 };
 
 protected jBuiltinRule 	get_CURRENTOP2()
 {jCompoundTerm 	ct1,ct2;
  jPredicateTerms 	pt;
  jVariable 		X,Y,Z,Xs,X1,Y1,Z1;
  
// 'CURRENTOP'([[_,_,_]|Xs],X1,Y1,Z1) :- 'CURRENTOP'(Xs,X1,Y1,Z1).

  X = new jVariable();
  Y = new jVariable();
  Z = new jVariable();
  Xs = new jVariable("Xs");
  X1 = new jVariable("X1");
  Y1 = new jVariable("Y1");
  Z1 = new jVariable("Z1");

  ct1 = new jCompoundTerm();
  ct1.addTerm(new jListPair(new jListPair(X,new jListPair(Y,Z)),Xs));
  ct1.addTerm(X1);
  ct1.addTerm(Y1);
  ct1.addTerm(Z1);
  
  ct2 = new jCompoundTerm();
  ct2.addTerm(Xs);
  ct2.addTerm(X1);
  ct2.addTerm(Y1);
  ct2.addTerm(Z1);
  
  pt = new jPredicateTerms();
  pt.addPredicate(new jPredicate("CURRENTOP",ct2));
     
  return new jBuiltinRule(new jPredicate("CURRENTOP",ct1),pt);
 };

 protected jBuiltinRule 	get_atom_chars()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X,Y;
  
// atom_chars(X,Y) :- name(X,Y,true),atom(X).

  X = new jVariable("X");
  Y = new jVariable("Y");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  ct1.addTerm(Y);
 
  pt = new jPredicateTerms();
  pt.addPredicate(new jName(X,Y,true));
  pt.addPredicate(new jIsAtom(X));
  
  return new jBuiltinRule(new jPredicate("atom_chars",ct1),pt);
 };

 protected jBuiltinRule 	get_number_chars()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X,Y;
  
// number_chars(X,Y) :- name(X,Y,false),number(X).

  X = new jVariable("X");
  Y = new jVariable("Y");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  ct1.addTerm(Y);
 
  pt = new jPredicateTerms();
  pt.addPredicate(new jName(X,Y,false));
  pt.addPredicate(new jIsNumber(X));
  
  return new jBuiltinRule(new jPredicate("number_chars",ct1),pt);
 };

 protected jBuiltinRule 	get_once()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X;
  
// once(X) :- call(X),!.

  X = new jVariable("X");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  
  pt = new jPredicateTerms();
  pt.addPredicate(new jCall(X));
  pt.addPredicate(new jCut());
     
  return new jBuiltinRule(new jPredicate("once",ct1),pt);
 };

 protected jBuiltinRule 	get_ignore1()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X;
  
// ignore(X) :- call(X),!.

  X = new jVariable("X");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  
  pt = new jPredicateTerms();
  pt.addPredicate(new jCall(X));
  pt.addPredicate(new jCut());
     
  return new jBuiltinRule(new jPredicate("ignore",ct1),pt);
 };

 protected jBuiltinRule 	get_ignore2()
 {jCompoundTerm 	ct1;
  jPredicateTerms 	pt;
  jVariable 		X;
  
// ignore(X).

  X = new jVariable("X");

  ct1 = new jCompoundTerm();
  ct1.addTerm(X);
  
  pt = new jPredicateTerms();
     
  return new jBuiltinRule(new jPredicate("ignore",ct1),pt);
 };
};

