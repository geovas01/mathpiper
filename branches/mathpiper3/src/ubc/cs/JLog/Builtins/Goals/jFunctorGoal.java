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
//	FunctorGoal
//#########################################################################
 
package ubc.cs.JLog.Builtins.Goals;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Parser.*;
import ubc.cs.JLog.Builtins.*;

public class jFunctorGoal extends jGoal
{
 protected jFunctor 			functor;
 
 // for use by functor
 public jTerm 				term1,term2,term3;
 public jUnifiedVector 		unified;
 
 public 	jFunctorGoal(jFunctor f,jTerm t1,jTerm t2,jTerm t3)
 {
  functor = f;
  term1 = t1;
  term2 = t2;
  term3 = t3;
  unified = new jUnifiedVector();
 };

 public boolean 	prove(iGoalStack goals,iGoalStack proved)
 {Thread 		t;
  
  t = Thread.currentThread();
  
  if (t instanceof jPrologServiceThread)
  {jPrologServiceThread			pst = (jPrologServiceThread) t;
   jPrologServices				prolog = pst.getPrologServices();
   pPredicateRegistry 			registry = prolog.getPredicateRegistry();
    
   if (functor.prove(this,registry))
   {
    proved.push(this);
    return true;
   }
  }
  {
   { // we need to initialize goal to potentially restart
    unified.restoreVariables();
   }
   goals.push(this); // a retry that follows may need a node to remove or retry
   return false;
  } 
 };

 public boolean 	retry(iGoalStack goals,iGoalStack proved)
 {
  unified.restoreVariables();
  
  goals.push(this); // a retry that follows may need a node to remove or retry
  return false;
 }; 
 
 public final void 	internal_restore(iGoalStack goals)
 {
  unified.restoreVariables();
 };

 public String 		getName() 
 {
  return functor.getName();
 };
 
 public int 		getArity() 
 {
  return functor.getArity();
 };
 
 public String 		toString()
 {StringBuffer 	sb = new StringBuffer();
   
  sb.append(getName()+"/"+String.valueOf(getArity())+" goal: ");
  sb.append(getName()+"("+term1.toString()+","+term2.toString()+","+term3.toString()+")");
  
  return sb.toString();
 };
};

 