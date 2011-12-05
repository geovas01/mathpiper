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
//	PredicateRegistry
//#########################################################################

package ubc.cs.JLog.Parser;

import java.lang.*;
import java.util.*;

/**
* The registery for <code>pPredicateEntry</code>s.  This is like a translation table
* for predicates, where each entry is a mapping object that takes parsing packets,
* and produces prolog terms.   
*  
* @author       Glendon Holst
* @version      %I%, %G%
*/
public class pPredicateRegistry
{
 protected Hashtable 					builtins;
 
 public pPredicateRegistry()
 {
  builtins = new Hashtable();
 };

 public void 			addPredicate(pPredicateEntry pe)
 {
  // optimize for 1, 2, 3, and 4-ary predicates.
  if (pe.getArity() == pPredicateEntry.NARY_ARITY)
  {int		i;
  
   for(i=1; i <= 4; i++)
    if (pe.isArity(i))
     builtins.put(getKeyString(pe.getName(),i),pe);
  }
  
  builtins.put(getKeyString(pe.getName(),pe.getArity()),pe);
 };
 
 public pPredicateEntry 	getPredicate(String name,int arity)
 {pPredicateEntry 	pe;
  
  // search for exact match
  if ((pe = (pPredicateEntry) builtins.get(getKeyString(name,arity))) != null)
   return pe;

  // search for possible n-ary match
  pe = (pPredicateEntry) builtins.get(getKeyString(name,pPredicateEntry.NARY_ARITY));
  
  if (pe != null && pe.isArity(arity))
   return pe;

  return null;
 }; 			
 
 public void 			clearPredicates()
 {
  builtins = new Hashtable();
 };
 
 public Enumeration 	enumPredicates()
 {
  return builtins.elements();
 };
 
 protected String		getKeyString(String name, int arity)
 {
  return name + "/" + Integer.toString(arity);
 };
};
