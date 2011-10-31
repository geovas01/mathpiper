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
//	Random
//#########################################################################
 
package ubc.cs.JLog.Builtins;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Foundation.*;

public class jRandom extends jUnaryArithmetic
{
 public 	jRandom(jTerm r)
 {
  super(r);
 };
 
 public String 		getName()
 {
  return "random";
 };  

 public  jTerm 		getValue()
 {jTerm 		r;
  
  r = rhs.getValue();

  if (r.type == TYPE_INTEGER)
   return new jInteger(operatorInt(((jInteger) r).getIntegerValue()));
  else if (r.type == TYPE_REAL)
   return new jReal(operatorReal(((jReal) r).getRealValue()));
   
  throw new InvalidArithmeticOperationException();
 };

 protected jUnaryBuiltinPredicate 	duplicate(jTerm r)
 {
  return new jRandom(r);
 };
 
 protected int 		operatorInt(int r)
 {Thread 		t = Thread.currentThread();
  
  if (r < 1)
   throw new InvalidArithmeticOperationException();
    
  if (t instanceof jPrologServiceThread)
  {jPrologServiceThread		pst = (jPrologServiceThread) t;
   Random			gen;
   double 			val;
   int 				result;
   
   gen = pst.getPrologServices().getRandomGenerator();
   val = gen.nextDouble();
   
   result = (int) Math.floor((double) r * val);
   result = (result < r ? result : 0); // ensure 0 <= result < r
     
   return result;
  }
  else
   throw new InvalidArithmeticOperationException();
 };
 
 protected float 	operatorReal(float r)
 {Thread 		t = Thread.currentThread();
  
  if (t instanceof jPrologServiceThread)
  {jPrologServiceThread		pst = (jPrologServiceThread) t;
   Random			gen;
   
   gen = pst.getPrologServices().getRandomGenerator();
   
   return r * gen.nextFloat();
  }
  else
   throw new InvalidArithmeticOperationException();
 };
};
