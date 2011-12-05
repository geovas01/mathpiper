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
//	DefiniteClauseGrammar
//#########################################################################
 
package ubc.cs.JLog.Terms;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.Goals.*;

public class jDCG extends jBinaryBuiltinPredicate
{
 protected class dcgFork
 {
  public Vector 	lhs,rhs;
  
  // both l and r must be predicate registry vectors of non-zero size.
  public 	dcgFork(Vector l,Vector r)
  {
   lhs = l;
   rhs = r;
  };
 };
 
 protected class dcgNotFork
 {
  public Vector 					lhs;
  public dcgUnifyPredicateUpdate 	unify;
  
  public 	dcgNotFork(Vector l,dcgUnifyPredicateUpdate u)
  {
   lhs = l;
   unify = u;
  };
 };
 
 protected interface iPredicateUpdate
 {
  public void 		updatePredicate(jTerm S,jTerm E);
 };
 
 protected class dcgPredicateUpdate implements iPredicateUpdate
 {
  public jPredicate 	pred;
  
  public 	dcgPredicateUpdate(jPredicate p)
  {
   pred = p;
  };
  
  public void 		updatePredicate(jTerm S,jTerm E)
  {
   pred.addTerm(S);
   pred.addTerm(E);
  }; 
 };
 
 protected class dcgTerminalPredicateUpdate extends dcgPredicateUpdate
 {
  public jTerm 	term;
  
  public 	dcgTerminalPredicateUpdate(jPredicate p,jTerm t)
  {
   super(p);
   term = t;
  };
  
  public void 		updatePredicate(jTerm S,jTerm E)
  {
   pred.addTerm(S);
   pred.addTerm(term);
   pred.addTerm(E);
  }; 
 };
 
 protected class dcgUnifyPredicateUpdate implements iPredicateUpdate
 {
  public jUnify 	pred;
  
  public 	dcgUnifyPredicateUpdate(jUnify p)
  {
   pred = p;
  };
  
  public void 		updatePredicate(jTerm S,jTerm E)
  {
   pred.setLHS(S);
   pred.setRHS(E);
  }; 
 };
 
 protected class dcgVariableGenerator
 {
  protected int 	num;
  
  public 	dcgVariableGenerator()
  {
   num = 1;
  };
  
  public jVariable 		createVariable()
  {
   return new jVariable("S"+String.valueOf(num++));
  };
 };
 
 public jDCG(jTerm l,jTerm r)
 {
  super(l,r,TYPE_BUILTINPREDICATE);
 };
  
 // create a DCG for use with makeDCGTerm only. makeDCGRule requires a lhs for the rule head.
 public jDCG(jTerm r)
 {
  super(null,r,TYPE_BUILTINPREDICATE);
 };
  
 public String 		getName()
 {
  return "-->";
 };

 // this is called during parse operations to create the DCG version of the prolog rules 
 public jRule 		makeDCGRule()
 {jPredicate 				head;
  jPredicateTerms 			base = new jPredicateTerms();
  jVariable 				firstv,lastv;  
  
  head = makeDCGHead(firstv = new jVariable("S0"),lastv = new jVariable("S"));
  if (rhs != null)  
   base.makePredicateTerms(makeDCGBase(firstv,lastv));
  
  return new jRule(head,base);
 };
 
 // used by expand_term. creates jIf if head is not null, otherwise returns the DCG body.
 public jTerm 		makeDCGTerm(jTerm S,jTerm E)
 {jTerm 	t;
 
  t = makeDCGBase(S,E);
 
  if (lhs != null)
   return new jIf(makeDCGHead(S,E),t);
  else
   return t;
 };
 
 // like makeDCGTerm above, except creates named variables.
 public jTerm 		makeDCGTerm()
 {
  return makeDCGTerm(new jVariable("S0"),new jVariable("S"));
 };
 
 // S0 and SN should be the first and last variables/terms used, respectivly.
 protected jTerm 		makeDCGBase(jTerm S0,jTerm SN)
 {Vector 	predicate_registry = new Vector();
  jTerm 	t;
  
  t = makeDCGBaseTerm(rhs,predicate_registry);
  
  updateDCGPredicateRegistry(predicate_registry,S0,SN,new dcgVariableGenerator());
  return t;
 };

 protected jTerm 		makeDCGBaseTerm(jTerm r,Vector pr)
 {jCompoundTerm 	terms = new jCompoundTerm();

  makeDCGBaseCompoundTerm(r,terms,pr);
  
  {jTerm 	dcg_term = terms.unmakeCompoundTerm();
  
   if (dcg_term == null)
    throw new InvalidDCGFormException("Expected term on RHS.",this);
    
   return dcg_term;  
  }
 };
 
 protected void 		updateDCGPredicateRegistry(Vector pr,jTerm S,jTerm E,
 													dcgVariableGenerator g)
 {int 			i, max = pr.size();
  jTerm 		v = S;
 
  for (i = 0; i < max; i++)
  {Object 		o;
   jTerm 		next;
   
   if (i < max - 1) // not last
    next = g.createVariable();
   else
    next = E;
   
   o = pr.elementAt(i);
   if (o instanceof dcgNotFork)
   {dcgNotFork 		nf = (dcgNotFork) o;
   
    updateDCGPredicateRegistry(nf.lhs,v,g.createVariable(),g);
    nf.unify.updatePredicate(v,next);
   }
   else if (o instanceof dcgFork)
   {dcgFork 		f = (dcgFork) o;
   
    updateDCGPredicateRegistry(f.lhs,v,next,g);
    updateDCGPredicateRegistry(f.rhs,v,next,g);  
   }
   else // o should be a iPredicateUpdate
   {iPredicateUpdate 		up = (iPredicateUpdate) o;
   
    up.updatePredicate(v,next);
   }
   v = next;
  }
 };
 
 protected void 		makeDCGBaseCompoundTerm(jTerm r,jCompoundTerm terms,Vector pr)
 {jTerm 			rt = r.getTerm();
  
  if (rt instanceof jNullList)
  {jUnify 		un;
  
   terms.addTerm(un = new jUnify());
     
   pr.addElement(new dcgUnifyPredicateUpdate(un));
  }
  else if (rt instanceof jListPair)
  {
   makeDCGConsList((jListPair) rt,terms,pr);
  }
  else if (rt instanceof jVariable)
  {
   terms.addTerm(makeDCGPhrase((jVariable) rt,pr));
  }
  else if (rt instanceof jCons)
  {jCons 			cons_rt = (jCons) rt;
   Enumeration 		e;
   jCompoundTerm 	ct;
   
   ct = new jCompoundTerm();
   makeDCGBaseCompoundTerm(cons_rt.getLHS(),ct,pr);
   e = ct.enumTerms();
   while (e.hasMoreElements())
    terms.addTerm((jTerm) e.nextElement());
    
   ct = new jCompoundTerm();
   makeDCGBaseCompoundTerm(cons_rt.getRHS(),ct,pr);
   e = ct.enumTerms();
   while (e.hasMoreElements())
    terms.addTerm((jTerm) e.nextElement());
  }
  else if (rt instanceof jOr)
  {jOr 		or_rt = (jOr) rt;
   Vector 	lv = new Vector();
   Vector 	rv = new Vector();
   
   terms.addTerm(new jOr(makeDCGBaseTerm(or_rt.getLHS(),lv),
   							makeDCGBaseTerm(or_rt.getRHS(),rv)));

   addDCGFork(pr,lv,rv);
  }
  else if (rt instanceof jPredicate)
  {jPredicate 		pt = (jPredicate) rt;
   
   if (pt.getName().equals("not"))
   {jCompoundTerm 		ct = new jCompoundTerm();
    Vector 				lv = new Vector();
    Enumeration			e = pt.getArguments().enumTerms();
    
    while (e.hasMoreElements())
    {
     ct.addTerm(makeDCGBaseTerm((jTerm) e.nextElement(),lv));
    }
    
    {jUnify 	un;
    
     terms.addTerm(new jPredicate(pt.getName(),ct));
     terms.addTerm(un = new jUnify());
     
     pr.addElement(new dcgNotFork(lv,new dcgUnifyPredicateUpdate(un)));
    }
   }
   else if (pt.getName().equals("{}"))
   {Enumeration 	e = pt.getArguments().enumTerms();
   
    while (e.hasMoreElements())
    {
     terms.addTerm((jTerm) e.nextElement());
    }
   }
   else if (pt.getName().equals("->") && pt.getArity() == 2)
   {jCompoundTerm 		ct = new jCompoundTerm();
        
    ct.addTerm(makeDCGBaseTerm(pt.getArguments().elementAt(0),pr));
    ct.addTerm(makeDCGBaseTerm(pt.getArguments().elementAt(1),pr));
    
    terms.addTerm(new jPredicate(pt.getName(),ct));
   }
   else if (pt.getName().equals("->") && pt.getArity() == 3)
   {jCompoundTerm 		ct = new jCompoundTerm();
    Vector 				lv = new Vector();
    Vector 				rv = new Vector();
        
    ct.addTerm(makeDCGBaseTerm(pt.getArguments().elementAt(0),lv));
    ct.addTerm(makeDCGBaseTerm(pt.getArguments().elementAt(1),lv));
    ct.addTerm(makeDCGBaseTerm(pt.getArguments().elementAt(2),rv));
    
    terms.addTerm(new jPredicate(pt.getName(),ct));
    addDCGFork(pr,lv,rv);
   }
   else 
   {jPredicate 		p;
   
    terms.addTerm(p = new jPredicate(pt.getName(),makeCompoundTerm(pt.getArguments())));
    
    pr.addElement(new dcgPredicateUpdate(p));
   }
  }
  else if (rt instanceof jAtom)
  {jPredicate 	pa;
  
   terms.addTerm(pa = new jPredicate(rt.getName(),new jCompoundTerm()));
   
   pr.addElement(new dcgPredicateUpdate(pa));
  }
  else if (rt instanceof iPredicate)
  {iPredicate 		ip = (iPredicate) rt;
   jPredicate 		p;
   
   terms.addTerm(p = new jPredicate(ip.getName(),makeCompoundTerm(ip.getArguments())));
    
   pr.addElement(new dcgPredicateUpdate(p));
  }
  else
   terms.addTerm(rt);
 };
 
 protected void 		makeDCGConsList(jListPair src,jCompoundTerm terms,Vector pr)
 {jTerm 	l = src;
   	
  while (l instanceof jListPair)
  {
   terms.addTerm(makeDCGTERMINAL(((jListPair) l).getHead(),pr));
   l = ((jListPair) l).getTail().getTerm();
  }
  
  if (l != null && !(l instanceof jNullList))
   terms.addTerm(makeDCGTERMINAL(l,pr)); 
 };		
 
 protected jPredicate 		makeDCGTERMINAL(jTerm t,Vector pr)
 {jPredicate 		p;
  
  p = new jPredicate("DCGTERMINAL",new jCompoundTerm());
  pr.addElement(new dcgTerminalPredicateUpdate(p,t));
  
  return p;
 };
  
 protected jPredicate 		makeDCGPhrase(jVariable v,Vector pr)
 {jCompoundTerm 	ct = new jCompoundTerm();
  jPredicate 		p;
   
  ct.addTerm((jVariable) v);
  
  p = new jPredicate("phrase",ct);
  pr.addElement(new dcgPredicateUpdate(p));
  
  return p;
 };

 protected void 			addDCGFork(Vector pr,Vector l,Vector r)
 {Vector 	merge = null;
 
  if (l.size() > 0 && r.size() > 0)
   pr.addElement(new dcgFork(l,r));
  else if (l.size() > 0)
   merge = l;
  else if (r.size() > 0)
   merge = r;

  if (merge != null)
  {Enumeration 		e = merge.elements();
  
   while (e.hasMoreElements())
    pr.addElement(e.nextElement());
  }
 };
 
 protected jCompoundTerm 	makeCompoundTerm(jCompoundTerm t)
 {jCompoundTerm 	ct = new jCompoundTerm();
  Enumeration 		e = t.enumTerms();
  
  while (e.hasMoreElements())
   ct.addTerm((jTerm) e.nextElement());
   
  return ct;
 };
 
 // S0 and SN should be the first and last variables/terms used for the base, respectivly.
 protected jPredicate 	makeDCGHead(jTerm S0,jTerm SN)
 {jTerm 			lt = lhs.getTerm();
  jPredicate 		h;
  jListPair 			l = null;
  
  if (lt instanceof jPredicate)
  {
   h = (jPredicate) lt;
  }
  else if (lt instanceof jCons)
  {jCons 		c = (jCons) lt;
  
   if ((c.getLHS().getTerm() instanceof jPredicate) && (c.getRHS().getTerm() instanceof iList))
   {
    h = (jPredicate) c.getLHS().getTerm();
    l = (c.getRHS().getTerm() instanceof jListPair ? (jListPair) c.getRHS().getTerm() : null); 
   }
   else
    throw new InvalidDCGFormException("Expected predicate,list on LHS.",this);
  }
  else
   throw new InvalidDCGFormException("Expected predicate or predicate,list on LHS.",this);
 
  {jTerm 			t3;
   
   if (l != null)
    t3 = adjustTerminalList(l,SN);
   else
    t3 = SN; 
   
   return adjustPredicate(h,S0,t3);
  }
 };

 // this should not adjust the existing list src, instead it should create a copy that uses
 // the same head terms, but new jListPair tail terms.
 protected jListPair 		adjustTerminalList(jListPair src,jTerm SN)
 {jListPair 	l,head;
  jTerm 	curr;
  
  l = head =  new jListPair(src.getHead(),null);
  curr = src.getTail();
  
  while (curr instanceof jListPair)
  {jListPair 	oldl = l;
  
   oldl.setTail(l = new jListPair(((jListPair) curr).getHead(),null));
   curr = ((jListPair) curr).getTail();
  }
  
  if (curr instanceof jNullList)
   l.setTail(SN);
  else
   l.setTail(new jListPair(curr,SN));
   
  return head;
 };
 
 // this should not adjust the existing jPredicate src, instead it should create a copy using
 // the same terms, but a new compound term.
 protected jPredicate 	adjustPredicate(jPredicate src,jTerm t2,jTerm t3)
 {jCompoundTerm 	ct = new jCompoundTerm(src.getArguments().size()+2);
  Enumeration 		e = src.getArguments().enumTerms();
  
  while (e.hasMoreElements())
   ct.addTerm((jTerm) e.nextElement());
   
  ct.addTerm(t2);
  ct.addTerm(t3);
  
  return new jPredicate(src.getName(),ct);
 };
 
 public void 		addGoals(jGoal g,jVariable[] vars,iGoalStack goals)
 {
  goals.push(new jFailGoal());
 }; 

 public void 		addGoals(jGoal g,iGoalStack goals)
 {
  goals.push(new jFailGoal());
 }; 

 public jBinaryBuiltinPredicate 		duplicate(jTerm l,jTerm r)
 {
  return new jDCG(l,r); 
 };

 public String 		toString(boolean usename)
 {
  return lhs.toString(usename) + " " + getName() + " " + rhs.toString(usename);
 };
};

