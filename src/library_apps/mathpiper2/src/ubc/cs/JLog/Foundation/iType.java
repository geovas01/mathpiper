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
//	Type
//#########################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.util.*;

/**
* This abstract interface defines all the prolog types.   
* Use <code>iType.getType()</code> in place of instanceof for speed, and when only the 
* actual instance type matters, and not any super classes. 
* <P>
* Any new types added to the system must be registered in this interface.  It is unlikely
* that this would be needed, since they are quite general.
* 
* @author       Glendon Holst
* @version      %I%, %G%
*/
public interface iType
{
 // any new term types should be added here
 public final static int 	TYPE_UNDEFINED = -1;
 public final static int 	TYPE_ATOM = 0;
 public final static int 	TYPE_INTEGER = 1;
 public final static int 	TYPE_REAL = 2;
 public final static int 	TYPE_PREDICATE = 3;
 public final static int 	TYPE_BUILTINPREDICATE = 4;
 public final static int 	TYPE_COMPOUND = 5;
 public final static int 	TYPE_PREDICATETERMS = 6;
 public final static int 	TYPE_CONS = 7;
 public final static int 	TYPE_OR = 8;
 public final static int 	TYPE_ORPREDICATE = 9;
 public final static int 	TYPE_IF = 10;
 public final static int 	TYPE_COMMAND = 11;
 public final static int 	TYPE_LIST = 12;
 public final static int 	TYPE_NULLLIST = 13;
 public final static int 	TYPE_VARIABLE = 14;
 public final static int 	TYPE_OPERATOR = 15;
 public final static int 	TYPE_UNARYOPERATOR = 16;
 public final static int 	TYPE_NUMERICCOMPARE = 17;
 public final static int 	TYPE_COMPARE = 18;
 public final static int 	TYPE_TYPE = 19;
 public final static int 	TYPE_ARITHMETIC = 20;
 public final static int 	TYPE_UNARYARITHMETIC = 21;
 public final static int 	TYPE_OBJECT = 22;
 
 public int 	getType(); 
};
