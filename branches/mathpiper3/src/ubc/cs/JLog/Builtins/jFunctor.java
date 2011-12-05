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
//	Functor
//#########################################################################
 
package ubc.cs.JLog.Builtins;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Parser.*;
import ubc.cs.JLog.Builtins.Goals.*;

public class jFunctor extends jTrinaryBuiltinPredicate
{
 public jFunctor(jTerm t,jTerm n,jTerm a)
 {
  super(t,n,a,TYPE_BUILTINPREDICATE);
 };
  
 public String 		getName()
 {
  return "functor";
 };

 public final boolean 	prove(jFunctorGoal fg,pPredicateRegistry pr)
 {jTerm 	t1,t2,t3;
  
  t1 = fg.term1.getTerm();
  t2 = fg.term2.getTerm();
  t3 = fg.term3.getTerm();
  
  if (t1.type == TYPE_VARIABLE && !(t2.type == TYPE_VARIABLE) && t3.type == TYPE_INTEGER)
  {int  				arity = ((jInteger) t3).getIntegerValue();
  
   if (arity > 0)
   {pPredicateEntry 		pe = pr.getPredicate(t2.getName(),arity);
    iPredicate 				p;
   
    if (pe != null)
     p = pe.createPredicate();
    else
    {jCompoundTerm 		ct = new jCompoundTerm();
     int 				i;
     
     for (i = 0; i < arity; i++)
      ct.addTerm(new jVariable("_"));
    
     p = new jPredicate(t2.getName(),ct);
    }
    
    return t1.unify(p,fg.unified);
   }
   else
    throw new InvalidFunctorArgumentException();
  }
  else if (t1 instanceof iPredicate)
  {iPredicate 		ip = (iPredicate) t1;
   
   if (!(t2.unify(new jAtom(ip.getName()),fg.unified)))
    return false;
     
   return t3.unify(new jInteger(ip.getArity()),fg.unified);
  }
  else
   throw new InvalidFunctorArgumentException(); 
 };

 public void 		addGoals(jGoal g,jVariable[] vars,iGoalStack goals)
 {
  goals.push(new jFunctorGoal(this,term1.duplicate(vars),term2.duplicate(vars),term3.duplicate(vars)));
 };

 public void 		addGoals(jGoal g,iGoalStack goals)
 {
  goals.push(new jFunctorGoal(this,term1,term2,term3));
 };

 protected jTrinaryBuiltinPredicate 		duplicate(jTerm t1,jTerm t2,jTerm t3)
 {
  return new jFunctor(t1,t2,t3); 
 };
};

 