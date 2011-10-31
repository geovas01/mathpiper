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
//	pPreToken
//#########################################################################

package ubc.cs.JLog.Parser;

import java.util.*;
import java.lang.*;

/**
* Simple token category used to represent the results from the tokenizer tables.  
*  
* @author       Glendon Holst
* @version      %I%, %G%
*/
class pPreToken
{
 protected String 		token = "";
 protected int 			position = 0;
 protected int 			type = pTokenizerTable.TOKEN_UNKNOWN;

 public pPreToken()
 {
 };
 
 public pPreToken(String s)
 {
  token = s;
 };
 
 public pPreToken(int type,int pos)
 {
  this.type = type;
  this.position = pos;
 };
 
 public pPreToken(String s,int type,int pos)
 {
  token = s;
  this.type = type;
  this.position = pos;
 };
 
 public String 		getToken()
 {
  return token;
 };
 
 public void 		setToken(String s)
 {
  token = s;
 };
 
 public int 		getType()
 {
  return type;
 };
 
 public void 		setType(int t)
 {
  type = t;
 };
 
 public int 		getPosition()
 {
  return position;
 };

 public void 		setPosition(int p)
 {
  position = p;
 };
};

