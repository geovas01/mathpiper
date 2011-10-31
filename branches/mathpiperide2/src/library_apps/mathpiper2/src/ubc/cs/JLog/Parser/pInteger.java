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
//	pInteger
//#########################################################################

package ubc.cs.JLog.Parser;

import java.util.*;
import java.lang.*;

class pInteger extends pToken
{
 protected int 		value;
 
 public 	pInteger(String s,int pos,int line,int cpos)
 {
  super(s,pos,line,cpos);
  try
  {
   value = Integer.valueOf(s).intValue();
  }
  catch (NumberFormatException e)
  {
   throw new SyntaxErrorException("Invalid integer number format at ",pos,line,cpos);
  }
 };

 public 	pInteger(String sb,String sn,int pos,int line,int cpos)
 {
  super(sb+"'"+sn,pos,line,cpos);
  try
  {int 		base;
  
   base = Integer.valueOf(sb).intValue();
   
   if (base == 0)
   {
    if (sn.length() != 1)
     throw new SyntaxErrorException("Expected single character at ",
                                     pos + sb.length() + 1,line,cpos + sb.length() + 1);
     
    value = sn.charAt(0);
   }
   else if (base >= Character.MIN_RADIX && base <= Character.MAX_RADIX)
   {int 	multi,cnt,result;
   
    value = 0;
    for (cnt = sn.length(),multi = 1; cnt >= 0; cnt--,multi *= base)
    {
     if ((result = Character.digit(sn.charAt(cnt),base)) < 0)
      throw new SyntaxErrorException("Expected valid number character within radix at ",
                        pos + sb.length() - cnt + 1,line,cpos + sb.length() - cnt + 1);
      
     value += result*multi;
    }
   }
   else
    throw new SyntaxErrorException("Invalid radix range at ",pos,line,cpos);
  }
  catch (NumberFormatException e)
  {
   throw new SyntaxErrorException("Invalid integer number format at ",pos,line,cpos);
  }
 };

 public int 		getValue()
 {
  return value;
 };
};

