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
//	Consultable
//#########################################################################

package ubc.cs.JLog.Terms;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;

/**
* <code>iConsultable</code> is the interface for classes that may need to
* access the knowledge base before any proof attempts are made.
* Usually such consulting is done after the knowledge base changes, such as
* when a prolog source is consulted.  The usual purpose of such consulting is to 
* prefetch and cache the knowledge base entries so that a search of the 
* knowledge base during the proof phase is not required.
*
* @author       Glendon Holst
* @version      %I%, %G%
*/
public interface iConsultable
{
 /**
  * Consult the given <code>jKnowledgeBase</code> and cache any invariant values (those 
  * which would not change until the next time the <code>jKnowledgeBase</code> changes). 
  * Where possible, consultable objects should minimize computation and assume that any 
  * cached values are still accurate.
  *
  * @param kb 		the knowledge base which attempted proofs are based upon.
  */
 public void 		consult(jKnowledgeBase kb);
 
 /**
  * Called when the <code>jKnowledgeBase</code> changes. Should set all cached values to 
  * dirty so that a  following call to <code>consult</code> would perform a full lookup 
  * as required.
  *
  */
 public void 		consultReset();
 
 /**
  * Determine if cached values already exist and hence if consulting is required. If 
  * object does not knowimmediatly whether consulting is needed, then assuming it is 
  * required.
  *
  * @return 		<code>false</code> if cached values are valid, 
  * 			<code>true</code> otherwise, if <code>consult</code> should be
  * 			called.
  */
 public boolean 	isConsultNeeded();
};

