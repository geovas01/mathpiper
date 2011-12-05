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
//	OpGoal
//#########################################################################
 
package ubc.cs.JLog.Builtins.Goals;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Parser.*;
import ubc.cs.JLog.Builtins.*;

public class jOpGoal extends jGoal
{
 protected jTerm 			priority,specifier,operation;
 
 public 	jOpGoal(jTerm t1,jTerm t2,jTerm t3)
 {
  priority = t1;
  specifier = t2;
  operation = t3;
 };

 public boolean 	prove(iGoalStack goals,iGoalStack proved)
 {int 		pri;
  int 		stype;
  
  pri = getPriority(priority.getValue());
  stype = getSpecifier(specifier.getTerm());
  
  {Thread 		t;
  
   t = Thread.currentThread();
  
   if (t instanceof jPrologServiceThread)
   {jPrologServiceThread		pst = (jPrologServiceThread) t;
    jPrologServices				prolog = pst.getPrologServices();
    pOperatorRegistry 			registry = prolog.getOperatorRegistry();
    
    registerOperations(registry,pri,stype);
    
    proved.push(this);
    return true;
   }
   else
   {
    goals.push(this); // a retry that follows may need a node to remove or retry
    return false;
   } 
  }
 };

 public boolean 	retry(iGoalStack goals,iGoalStack proved)
 {
  goals.push(this); // a retry that follows may need a node to remove or retry
  return false;
 }; 
 
 protected int 			getPriority(jTerm t)
 {
  if (t.type == jType.TYPE_INTEGER)
  {int 	value;
   
   value = ((jInteger) t).getIntegerValue();
   
   if (value < 0 || value > 1200)
    throw new InvalidOpArgumentException("Priority number in 0..1200 range required.");
   
   return value; 
  }
  else
   throw new InvalidOpArgumentException("Priority number required.");
 };

 protected int 			getSpecifier(jTerm t)
 {
  if (t.type == jType.TYPE_ATOM)
  {String 	value;
   
   value = t.getName();
   
   if (value.equalsIgnoreCase("FX"))
    return pOperatorEntry.FX;
   if (value.equalsIgnoreCase("FY"))
    return pOperatorEntry.FY;
   if (value.equalsIgnoreCase("XFX"))
    return pOperatorEntry.XFX;
   if (value.equalsIgnoreCase("XFY"))
    return pOperatorEntry.XFY;
   if (value.equalsIgnoreCase("YFX"))
    return pOperatorEntry.YFX;
   if (value.equalsIgnoreCase("XF"))
    return pOperatorEntry.XF;
   if (value.equalsIgnoreCase("YF"))
    return pOperatorEntry.YF;
   
   throw new InvalidOpArgumentException("'fx','fy','xfx','xfy','yfx','xf' or 'yf' specifier required.");
  }
  else
   throw new InvalidOpArgumentException("Specifier atom required.");
 };

 protected void 		registerOperations(pOperatorRegistry registry,int pri,int stype)
 {jTerm 	t = operation.getTerm();

  do
  {
   if (t.type == jType.TYPE_ATOM)
   {
    registerOperation(t,registry,pri,stype);
    break;
   }
   else if (t.type == jType.TYPE_LIST)
   {jListPair 	tl = (jListPair) t;

    registerOperation(tl.getHead(),registry,pri,stype);
   
    t = tl.getTail();
   }
   else
    throw new InvalidOpArgumentException("Operator name atom required.");
  } while (!(t instanceof jNullList));
 };
 
 protected void 		registerOperation(jTerm t,pOperatorRegistry registry,int pri,int stype)
 {
  if (t.type == jType.TYPE_ATOM)
   registry.addOperator(new pPredicateOperatorEntry(t.getName(),stype,pri));
  else
   throw new InvalidOpArgumentException("Operator name atom required.");
 };

 public String 		getName() 
 {
  return "op";
 };
 
 public int 		getArity() 
 {
  return 3;
 };
 
 public String 		toString()
 {StringBuffer 	sb = new StringBuffer();
   
  sb.append(getName()+"/"+String.valueOf(getArity())+" goal: ");
  sb.append(getName()+"("+priority.toString()+","+specifier.toString()+","+operation.toString()+")");

  return sb.toString();
 };
};

 