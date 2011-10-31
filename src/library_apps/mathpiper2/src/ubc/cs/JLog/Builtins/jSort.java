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
//	Sort
//#########################################################################
 
package ubc.cs.JLog.Builtins;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Builtins.Goals.*;

public class jSort extends jBinaryBuiltinPredicate
{
 protected boolean 		var_equal = false;

 public jSort(jTerm l,jTerm r)
 {
  super(l,r,TYPE_BUILTINPREDICATE);
 };
  
 public jSort(jTerm l,jTerm r,boolean ve)
 {
  super(l,r,TYPE_BUILTINPREDICATE);
  var_equal = ve;
 };
  
 public String 		getName()
 {
  return "sort";
 };
 
 public boolean 	prove(jSortGoal sg)
 {jTerm 	l,r;
  jList 	sorted;
  
  l = sg.lhs.getTerm();
  r = sg.rhs.getTerm();
   
   
  if (l instanceof jList)
   sorted = sort(l,sg.var_equal);
  else
   throw new InvalidSortSourceListException();
   
  return r.unify(sorted,sg.unified);
 };

 protected jList 	sort(jTerm src,boolean ve)
 {Vector 	dest = new Vector();
  
  src = src.getTerm();
  while (src.type == TYPE_LIST)
  {
   sort_insert(((jListPair) src).getHead(),dest,ve);
   src = ((jListPair) src).getTail().getTerm();
  }

  if (src != null && src.type != TYPE_NULLLIST)
   sort_insert(src,dest,ve);

  return sort_makelist(dest);   
 };

 protected jList 	sort_makelist(Vector dest)
 {Enumeration 		e = dest.elements();
  jListPair				prev = null; 
  jList 			dlst = null;
  
  while (e.hasMoreElements())
  {jTerm 	t = (jTerm) e.nextElement();
   
   if (prev == null)
    dlst = prev = new jListPair(t,null);
   else
   {jListPair 	nlst;
    
    prev.setTail(nlst = new jListPair(t,null));
    prev = nlst;
   }
  }
   
  if (prev != null)
   prev.setTail(jNullList.NULL_LIST);
  else
   dlst = jNullList.NULL_LIST;
    
  return dlst;
 };
 
 // return values same as compare, capares l(vector element) to r(inserted element)
 protected int 		sort_compare(jTerm l,jTerm r,boolean ve)
 {
  return l.compare(r,ve);
 };
 
 protected void 	sort_insert(jTerm t,Vector v,boolean ve)
 {int 	i,max,result;
 
  for (i = 0, max = v.size(); i < max; i++)
  {
   result = sort_compare((jTerm) v.elementAt(i),t,ve);
  
   switch (result)
   {
    case GREATER_THAN:
      v.insertElementAt(t,i);    
    case EQUAL:
      return;
   }  
  }
  
  v.insertElementAt(t,v.size());
 };

 public void 		addGoals(jGoal g,jVariable[] vars,iGoalStack goals)
 {
  goals.push(new jSortGoal(this,lhs.duplicate(vars),rhs.duplicate(vars),var_equal));
 }; 

 public void 		addGoals(jGoal g,iGoalStack goals)
 {
  goals.push(new jSortGoal(this,lhs,rhs,var_equal));
 }; 

 public jBinaryBuiltinPredicate 		duplicate(jTerm l,jTerm r)
 {
  return new jSort(l,r,var_equal); 
 };
};

