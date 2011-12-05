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
//	UnifiedVector
//#########################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;

/**
* This class is used to register <code>jVariable</code> mapped pairs during 
* equivalence testing.
*  
* @author       Glendon Holst
* @version      %I%, %G%
*/
public class jEquivalenceMapping
{
 protected Hashtable 		variables;
 
 public jEquivalenceMapping()
 {
  variables = new Hashtable();
 };
 
 /**
  * Adds a mapping between two different variables.  Should be invoked by the 
  * <code>jVariable</code> during equivalence testing.
  *
  * @param v1 		a variable to pair.
  * @param v2 		the other variable in the pair.
  *
  * @return boolean	<code>true</code> if the variables were previously unmapped, or this is the same mapping
  *					<code>false</code> otherwise.
  */
 public final boolean 		mapVariablePair(jVariable v1,jVariable v2)
 {
  Object val1 = variables.get(v1);
  Object val2 = variables.get(v2);

  if (val1 == null && val2 == null)
  {
   variables.put(v1,v2);
   variables.put(v2,v1);

   return true;
  }  
  
  return (val1 == v2 && val2 == v1);
 };

};