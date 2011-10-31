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
//	GenericPredicateEntry
//#########################################################################

package ubc.cs.JLog.Parser;

import java.lang.*;
import java.lang.reflect.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;

/**
* Dynamically constructs the <code>iPredicate</code> term from a description of the name,
* arity, and class name for the associated predicate class.  It is suitable for predicates
* constructed directly from the <code>jCompoundTerm</code> passed into 
* <code>createPredicate</code>.
*  
* @author       Glendon Holst
* @version      %I%, %G%
*/
public class pGenericPredicateEntry extends pPredicateEntry
{
 protected final static Class[][]   constructor_params_arrays = new Class[][] {new Class[] {},
				new Class[] {jTerm.class}, new Class[] {jTerm.class,jTerm.class},
				new Class[] {jTerm.class,jTerm.class,jTerm.class},
				new Class[] {jTerm.class,jTerm.class,jTerm.class,jTerm.class}};

 protected Class				predicate_class;

 public 	pGenericPredicateEntry(String name,int arity,String classname )
 {
  super(name,arity);

  if (arity < 0)
   throw new InvalidGenericPredicateEntryException("Arity must be a fixed, positive value");
 
  try
  {
   predicate_class = Class.forName(classname);
  }
  catch (Exception e)
  {
   throw new InvalidGenericPredicateEntryException("Predicate Class not found: "+classname);
  }
 };

 public 	pGenericPredicateEntry(String name,int arity,Class p_class )
 {
  super(name,arity);

  if (arity < 0)
   throw new InvalidGenericPredicateEntryException("Arity must be a fixed, positive value");
 
  predicate_class = p_class;
 };
 
 public iPredicate 		createPredicate(jCompoundTerm cterm)
 {
  try
  {Constructor		pred_cons = null;
   iPredicate		pred = null;
   
   pred_cons = predicate_class.getConstructor(getConstructorParamsArray());
   pred = (iPredicate) pred_cons.newInstance(getConstructorArgsArray(cterm));

   return pred;   
  }
  catch (Exception e)
  {
   throw new InvalidGenericPredicateEntryException("Predicate construction failed");
  }
 };
 
 protected final Class[]	getConstructorParamsArray()
 {
  if (arity >= 0 && arity <= 4)
   return constructor_params_arrays[arity];
  else
  {Class[]		params = new Class[arity];
   int			i;
   
   for (i = 0; i < arity; i++)
    params[i] = jTerm.class;
   
   return params;
  }
 };
 
 protected final Object[]	getConstructorArgsArray(jCompoundTerm cterm)
 {Object[]		args = new Object[arity];
  int			i;
   
  for (i = 0; i < arity; i++)
   args[i] = cterm.elementAt(i);
   
  return args;
 };
};

class InvalidGenericPredicateEntryException extends RuntimeException
{
 public InvalidGenericPredicateEntryException() {};
 public InvalidGenericPredicateEntryException(String s) {super(s);};
};

