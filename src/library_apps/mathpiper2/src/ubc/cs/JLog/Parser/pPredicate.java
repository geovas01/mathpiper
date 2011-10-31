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
//	pPredicate
//#########################################################################

package ubc.cs.JLog.Parser;

import java.util.*;
import java.lang.*;
import ubc.cs.JLog.Terms.*;

class pPredicate extends pPacket
{
 protected pParens 					params = null;
 protected String 					name = null;
 protected pPredicateRegistry 		predicates;
 protected boolean 					generic_predicate = true;
 
 public 	pPredicate(pPredicateRegistry p,pToken pt)
 {
  super(pt);
  predicates = p;
 };
 
 public 	pPredicate(pPredicateRegistry p,String n,pToken pt)
 {
  super(pt);
  name = n;
  predicates = p;
 };
 
 public 	pPredicate(pPredicateRegistry p,pToken pt,pParens pp)
 {
  super(pt);
  params = pp;
  predicates = p;
 };
 
 public 	pPredicate(pPredicateRegistry p,String n,pToken pt,pParens pp)
 {
  super(pt);
  params = pp;
  name = n;
  predicates = p;
 };
 
 public String 		getName()
 {
  if (name == null)
   return token.getToken();
  
  return name;
 };

 public void 		setParameters(pParens pp)
 {
  params = pp;
 };

 // if generic_predicate is true, then only create jPredicate for use as rule head.
 // the arguments should be non-generic so that they can be called if unified with a variable. 
 public void 		setGeneric(boolean genericpred)
 {
  generic_predicate = genericpred;
  
  if (params != null)
   params.setGeneric(false);
 };

 public jTerm 			getTerm(pVariableRegistry vars,pTermToPacketHashtable phash)
 {jCompoundTerm 	cterm;
 
  if (params == null)
   cterm = new jCompoundTerm(1);
  else
   cterm = makeCompoundTerm(new jCompoundTerm(),params.getInside(),vars,phash);
  
  {jTerm 	term;
  
   try
   {
    if ((term = createSpecialPredicate(cterm)) != null)
     ;
    else if (!generic_predicate && cterm.size() == 0)
     term = new jAtom(getName());
    else
     term = new jPredicate(getName(),cterm);
     
    phash.putPacket(term,this);
    return term;
   }
   catch (InvalidPredicateNameException e)
   {
    throw new SyntaxErrorException("Valid name required for predicate at ",
                                    token.getPosition(),token.getLine(),token.getCharPos()); 
   }
  }
 };

 protected iPredicate			createSpecialPredicate(jCompoundTerm cterm)
 {pPredicateEntry 		pe;

  if (!generic_predicate && (pe = predicates.getPredicate(getName(),cterm.size())) != null)
   return pe.createPredicate(token,cterm); 
  else 
   return null;
 };

 protected jCompoundTerm 		makeCompoundTerm(jCompoundTerm cterm,pPacket pp,
 							 pVariableRegistry vars,pTermToPacketHashtable phash)
 {
  if (pp instanceof pCons)
  {pCons 	ppc = (pCons) pp;
   
   cterm.addTerm(ppc.getLHS().getTerm(vars,phash)); 
   return makeCompoundTerm(cterm,ppc.getRHS(),vars,phash);
  }

  cterm.addTerm(pp.getTerm(vars,phash));
  return cterm;
 };
};

