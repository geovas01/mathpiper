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
//	pList
//#########################################################################

package ubc.cs.JLog.Parser;

import java.util.*;
import java.lang.*;
import ubc.cs.JLog.Terms.*;

class pList extends pPacket
{
 protected pPacket 		head,tail;
 
 public 	pList(pToken pt,pPacket h,pPacket t)
 {
  super(pt);
  head = h;
  tail = t;
 };
 
 public jTerm 		getTerm(pVariableRegistry vars,pTermToPacketHashtable phash)
 {jTerm 	term;
 
  term = makeList(head,tail,vars,phash);
  phash.putPacket(term,this);
 
  return term;
 };
 
 protected jList 		makeList(pPacket pak1,pPacket pak2,
 									pVariableRegistry vars,pTermToPacketHashtable phash)
 {jList 			start = null;
  jListPair 			last = null;
  
  if (pak1 == null)
   return jNullList.NULL_LIST;
  
  start = last = new jListPair(null,null);
  
  do
  {
   if (pak1 instanceof pCons)
   {
    last.setHead(((pCons) pak1).getLHS().getTerm(vars,phash));
    last.setTail(last = new jListPair(null,null));
    pak1 = ((pCons) pak1).getRHS();
   }
   else
   { 
    last.setHead(pak1.getTerm(vars,phash));
    pak1 = null;
   }
  } while (pak1 != null);
 
  if (pak2 == null)
   last.setTail(jNullList.NULL_LIST);
  else
   last.setTail(pak2.getTerm(vars,phash));
   
  return start;
 };
 
 public void 		setGeneric(boolean genericpred)
 {
  if (head != null)
   head.setGeneric(genericpred);
  if (tail != null)
   tail.setGeneric(genericpred);
 };
};

