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
//	ConvertArray
//#########################################################################
 
package ubc.cs.JLog.Builtins;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.Goals.*;

public class jConvertArray extends jTrinaryBuiltinPredicate
{
 public jConvertArray(jTerm t1,jTerm t2,jTerm t3)
 {
  super(t1,t2,t3,TYPE_BUILTINPREDICATE);
 };
  
 public String 		getName()
 {
  return "CONVERTARRAY";
 };
 
 public boolean 	prove(jTrinaryBuiltinPredicateGoal cag)
 {jTerm 	t1,t2,t3;
  
  t1 = cag.term1.getTerm();
  t2 = cag.term2.getTerm();
  t3 = cag.term3.getTerm();
   
  if (t1 instanceof jCompoundTerm)
  {jCompoundTerm 	ct = (jCompoundTerm) t1;
   jTerm 			list;
   
   list = convert_makelist(ct.enumTerms(),t3);

   return t2.unify(list,cag.unified);
  }
  else
   throw new ExpectedCompoundTermException();
 };

 protected jTerm 	convert_makelist(Enumeration e,jTerm r)
 {jListPair				prev = null; 
  jTerm 			dlst = null;
  
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
   prev.setTail(r);
  else 
   dlst = r;
    
  return dlst;
 };

 public jTrinaryBuiltinPredicate 		duplicate(jTerm t1,jTerm t2,jTerm t3)
 {
  return new jConvertArray(t1,t2,t3); 
 };
};

