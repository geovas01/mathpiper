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
//	CurrentOpList
//#########################################################################
 
package ubc.cs.JLog.Builtins;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Parser.*;
import ubc.cs.JLog.Builtins.Goals.*;

public class jCurrentOpList extends jUnaryBuiltinPredicate
{
 public jCurrentOpList(jTerm list)
 {
  super(list,TYPE_BUILTINPREDICATE);
 };
  
 public String 		getName()
 {
  return "CURRENTOPLIST";
 };

 public boolean 	prove(jCurrentOpListGoal olg,Enumeration e)
 {jTerm 	r,l = null;
  jListPair 	prev = null;
  
  r = olg.rhs.getTerm();
  
  while (e.hasMoreElements())
  {pOperatorEntry 	oe = (pOperatorEntry) e.nextElement();
   jListPair 			next;
   
   next = new jListPair(createSingleOperatorList(oe),null);
   if (prev != null)
    prev.setTail(next);
   else
    l = next;
    
   prev = next;
  }
   
  if (prev != null)
   prev.setTail(jNullList.NULL_LIST);
  else
   l = jNullList.NULL_LIST;
   
  return r.unify(l,olg.unified);
 };

 protected String 	createSpecifierString(int type)
 {
  switch (type)
  {
   case pOperatorEntry.FX:
    return "fx";
   case pOperatorEntry.FY:
    return "fy";
   case pOperatorEntry.XFX:
    return "xfx";
   case pOperatorEntry.XFY:
    return "xfy";
   case pOperatorEntry.YFX:
    return "yfx";
   case pOperatorEntry.XF:
    return "xf";
   case pOperatorEntry.YF:
    return "yf";
   default:
    return "?";
  }
 };
 
 protected jListPair 	createSingleOperatorList(pOperatorEntry oe)
 {
  return new jListPair(new jInteger(oe.getPriority()),
            new jListPair(new jAtom(oe.getName()),
            new jAtom(createSpecifierString(oe.getType()))));
 };
 
 public void 		addGoals(jGoal g,jVariable[] vars,iGoalStack goals)
 {
  goals.push(new jCurrentOpListGoal(this,rhs.duplicate(vars)));
 };

 public void 		addGoals(jGoal g,iGoalStack goals)
 {
  goals.push(new jCurrentOpListGoal(this,rhs));
 };

 protected jUnaryBuiltinPredicate 		duplicate(jTerm r)
 {
  return new jCurrentOpList(r); 
 };
};

 