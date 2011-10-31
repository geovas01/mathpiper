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
//	pTermToPacketHashtable
//#########################################################################

package ubc.cs.JLog.Parser;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;

/**
* Keeps track of <code>jTerms</code> and their associated <code>pPacket</code>s.
* The packets contain parsing relevant information, which my be needed generate
* error messages if the term proves to be invalid. 
*  
* @author       Glendon Holst
* @version      %I%, %G%
*/
public class pTermToPacketHashtable extends Hashtable
{
 public pTermToPacketHashtable()
 {
  super();
 };
 
 /**
  * Finds the packet associated with the given term key.
  *
  * @param t 		The term key.
  * 
  * @return 		The associated packet if there was a match.
  */
 public pPacket 		getPacket(jTerm t)
 {
  return (pPacket) get(t);
 };
 
 /**
  * Associates a term with a packet.
  *
  * @param t 		The term key.
  * @param pp 		The packet to associate with term t.
  * 
  * @return 		Either null if the term can't have a packet, or the packet
  * 			returned by the <code>Hashtable</code>.
  */
 public pPacket 		putPacket(jTerm t,pPacket pp)
 {
  if (t == null)
   return null;
   
  return (pPacket) put(t,pp);
 };
 
 /**
  * Looks up packet in the hash given a term key, and returns the packets token. 
  * This just encapsulates a common use of this hashtable.
  *
  * @param t 		The term key.
  * @param pp 		The packet associated with term t. Used if there is no packet
  * 			associated in this hashtable.
  * 
  * @return 		The resulting packet's token if there was a match, otherwise 
  * 			returning the packet <code>pp</code>'s token.
  */
 public pToken 			getToken(jTerm t,pPacket pp)
 {pPacket 		badpp = null;
  pToken 		pt;
    
  if (t != null)
   badpp = getPacket(t);
  if (badpp == null)
   badpp = pp;

  if (badpp != null)
   return badpp.getToken(); 

  return null;
 };
};
