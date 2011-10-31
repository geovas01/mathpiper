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
//	GenericOperatorEntry
//#########################################################################

package ubc.cs.JLog.Parser;

import java.lang.*;
import java.lang.reflect.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;

/**
* Dynamically constructs the operator <code>jTerm</code> term from a description of the
* operators name, type, and priority. It is suitable for operators
* constructed directly from the the arguments passed into <code>createOperator</code>.
*  
* @author       Glendon Holst
* @version      %I%, %G%
*/
public class pGenericOperatorEntry extends pOperatorEntry
{
 protected final static Class[][]   constructor_params_arrays = new Class[][] {
				new Class[] {jTerm.class}, new Class[] {jTerm.class,jTerm.class}};

 protected Class		operator_class;
 protected boolean		allow_atom = true;

 public 	pGenericOperatorEntry(String name,int type,int priority,String classname)
 {
  super(name,type,priority);
  try
  {
   operator_class = Class.forName(classname);
  }
  catch (Exception e)
  {
   throw new InvalidGenericOperatorEntryException("Operator Class not found: "+classname);
  }
 };

 public 	pGenericOperatorEntry(String name,int type,int priority,boolean aatom, String classname)
 {
  super(name,type,priority);

  allow_atom = aatom;
  
  try
  {
   operator_class = Class.forName(classname);
  }
  catch (Exception e)
  {
   throw new InvalidGenericOperatorEntryException("Operator Class not found");   
  }
 };

 public 	pGenericOperatorEntry(String name,int type,int priority,Class op_class)
 {
  super(name,type,priority);
  operator_class = op_class;
 };
 
 public 	pGenericOperatorEntry(String name,int type,int priority,boolean aatom,Class op_class)
 {
  super(name,type,priority);
  allow_atom = aatom;
  operator_class = op_class;
 };
 
 public boolean 		isAtomPermitted()
 {
  return allow_atom;
 };
 
 public jTerm 		createOperator(jTerm l,jTerm r)
 {
  try
  {Constructor		op_cons = null;
   jTerm			op = null;
   
   op_cons = operator_class.getConstructor(getConstructorParamsArray());
   op = (jTerm) op_cons.newInstance(getConstructorArgsArray(l,r));

   return op;   
  }
  catch (Exception e)
  {
   throw new InvalidGenericOperatorEntryException("Operator construction failed");
  }
 };

 protected final Class[]	getConstructorParamsArray()
 {
  switch (type)
  {
   case FX:
   case FY:
   case XF:
   case YF:
	 return constructor_params_arrays[0];
   case XFX:
   case XFY:
   case YFX:
     return constructor_params_arrays[1];
   default:
     return null;
  }
 };
 
 protected final Object[]	getConstructorArgsArray(jTerm l,jTerm r)
 {
  switch (type)
  {
   case FX:
   case FY:
     return new Object[] {r};
   case XF:
   case YF:
	 return new Object[] {l};
   case XFX:
   case XFY:
   case YFX:
     return new Object[] {l,r};
   default:
     return null;
  }
 };
};

class InvalidGenericOperatorEntryException extends RuntimeException
{
 public InvalidGenericOperatorEntryException() {};
 public InvalidGenericOperatorEntryException(String s) {super(s);};
};

