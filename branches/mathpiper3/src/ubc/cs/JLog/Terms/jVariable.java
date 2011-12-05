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
//	Variable
//#########################################################################

package ubc.cs.JLog.Terms;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;

/**
* This is the class for Prolog variables.
*  
* @author       Glendon Holst
* @version      %I%, %G%
*/
public class jVariable extends jTerm
{
 protected String 	name = null;
 protected jTerm 	bound = null;
 protected int 		proxy = -1;

 /**
  * Constructor to create unnamed variables, such as "_".
  *
  */ 
 public 	jVariable()
 {
  type = TYPE_VARIABLE;
 };
 
 public 	jVariable(String n)
 {
  name = n;
  type = TYPE_VARIABLE;
 };
 
 public jTerm 		getValue()
 {
  if (bound != null)
   return bound.getValue();
   
  return this;
 };

 public jTerm 		getTerm()
 {jTerm			b = bound;
  jVariable		p = this;

  while (b != null)
  {
   if (b.type == TYPE_VARIABLE)
   {
    p = (jVariable) b; // assume type member is correct
	b = p.bound;
   }
   else
    return b.getTerm();
  }

  return p;
 };
 
 public String 		getName()
 {
  if (isNamed())
   return name;
   
  return getIdentity();
 };

 /**
  * Get an unique indentifying name. Relies on the <code>Object toString</code> 
  * to tack on a number at the end of the string. If expectations are not met, 
  * this is still handled fairly gracefully.
  *
  * @return  		An identity string (based on the name and and id number 
  * 			from <code>Object toString</code>.
  */ 
 public String 		getIdentity()
 {String 	id =  objectToString();
  int 		pos = id.lastIndexOf('@') + 1;
  
  if (pos < 0 || pos >= id.length())
   pos = id.length() - 6;
  if (pos < 0)
   pos = 0;
  
  return "_" + id.substring(pos);
 };
 
 public boolean 	isNamed()
 {
  return (name != null && !name.equals("_"));
 };

 public boolean 	isNamedForDisplay()
 {
  return (name != null && !name.startsWith("_"));
 };

 public void 		setBinding(jTerm b)
 {
  bound = b;
 };

 public boolean 	isBound()
 {
  return bound != null;
 };

 public void 		registerUnboundVariables(jUnifiedVector v)
 {
  if (bound == null)
   v.addVariable(this);
  else
   bound.registerUnboundVariables(v);
 };

 protected int 		compare(jTerm term,boolean first_call,boolean var_equal)
 {jTerm 	    vt = getTerm();
  jTerm 		t = term.getTerm();
 
  if (vt instanceof jVariable)
  {
   if (t instanceof jVariable)
   {int 			compare_val;
   
    if (var_equal)
     return EQUAL;
     
    compare_val = ((jVariable) vt).getIdentity().compareTo(((jVariable) t).getIdentity());
   
    if (compare_val < 0)
     return LESS_THAN;
    if (compare_val > 0)
     return GREATER_THAN;
 
    return EQUAL;
   }
   else
    return LESS_THAN;
  }
  else
   return vt.compare(t,true,var_equal);
 };

 public boolean 	equivalence(jTerm term,jEquivalenceMapping v)
 {jTerm 		t = term.getTerm();
  
  {jTerm		b = bound;
   jVariable	p = this;
   
   while (b != null)
   {
    if (b.type == TYPE_VARIABLE)
    {
	 p = (jVariable) b;  // assume type member is correct
	 b = p.bound; 
	}
	else // there is no occurs check
	 return b.equivalence(t,v);	
   }

   if (t.type != TYPE_VARIABLE)
    return false;
   
   // past this point, both terms are variables 
   if (p == t)
    return true;

   // assume p and t are variables (if not, a failing exceptin is appropriate).
   return v.mapVariablePair((jVariable) p,(jVariable) t);
  }  
 };

 public boolean 	unify(jTerm term,jUnifiedVector v)
 {jTerm 		t = term.getTerm();
  
  {jTerm		b = bound;
   jVariable	p = this;
   
   while (b != null)
   {
    if (b.type == TYPE_VARIABLE)
    {
	 p = (jVariable) b;  // assume type member is correct
	 b = p.bound; 
	}
	else // there is no occurs check
	 return b.unify(t,v);	
   }

   if (p == t)
    return true;

   p.bound = t;
   v.addVariable(p);
   return true;
  }  
 };

 public void 		registerVariables(jVariableVector v)
 {
  proxy = v.addVariable(this);
 };
  
 public void 		enumerateVariables(jVariableVector v,boolean all)
 {
  if (bound != null)
   bound.enumerateVariables(v,all);
  else
   v.addVariable(this);
 };

 public jTerm 		duplicate(jVariable[] vars)
 {
  return vars[proxy];
 };

 public jTerm 		copy(jVariableRegistry vars)
 {jTerm			b = bound;
  jVariable		p = this;

  while (b != null)
  {
   if (b.type == TYPE_VARIABLE)
   {
    p = (jVariable) b; // assume type member is correct
	b = p.bound;
   }
   else
    return b.copy(vars);
  }

  return vars.copyVariable(p);
 };

 public void 		consult(jKnowledgeBase kb)
 {
  if (bound != null)
   bound.consult(kb);
 };
 
 public void 		consultReset()
 {
  if (bound != null)
   bound.consultReset();
 };
 
 public boolean 	isConsultNeeded()
 {
  return bound != null;
 };

 public String 		toString(boolean usename)
 {jTerm 	t = getTerm();
 
  return (t instanceof jVariable ? 
           (usename ? getName() : ((jVariable) t).getIdentity()): t.toString(usename));
 };
};
