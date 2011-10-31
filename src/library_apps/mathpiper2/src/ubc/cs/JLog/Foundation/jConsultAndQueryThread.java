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
//	jConsultAndQueryThread
//#########################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.util.*;
import java.io.PrintWriter;
import ubc.cs.JLog.Parser.*;

/**
* This class implements a prolog consult, followed by a query.  
* The source is a <code>TextArea</code>, as is the error output.
*  
* @author       Glendon Holst
* @version      %I%, %G%
*/
public class jConsultAndQueryThread extends jRetryQueryThread
{
 protected boolean 	in_consult_phase = true;

 jConsultSourceThread 	consult;
 jUserQueryThread 		query;
 
 public 	jConsultAndQueryThread(jPrologServices ps,
                                    iPrologServiceText s,PrintWriter e,
                                    iPrologServiceText qin,PrintWriter o)
 {
  super(ps);

  setName("ConsultAndQueryThread");
 // setPriority(NORM_PRIORITY + 1); //MRJ 2.1 doesn't permit UI interaction with this setting
 
  consult = new jConsultSourceThread(ps,s,e);
  consult.setAllowRelease(false);
  query = new jUserQueryThread(ps,qin,o);
 };

 public void 	setListeners(jPrologServiceBroadcaster c_b,
                                jPrologServiceBroadcaster c_e,
                                jPrologServiceBroadcaster q_b,
                                jPrologServiceBroadcaster q_r,
								jPrologServiceBroadcaster q_e,
                                jPrologServiceBroadcaster q_d,
								jPrologServiceBroadcaster s)
 {
  setStoppedListeners(s);

  consult.setListeners(c_b,c_e,s);
  query.setListeners(q_b,q_r,q_e,s,q_d);
 };

/**
* Performs and controls the entire consultation phase.
* 
*/
 public void 	run()
 {
  in_consult_phase = true;
  consult.run();
  
  in_consult_phase = false;
  query.run();
 };
 
 public boolean 	isCurrentlyConsulting()
 {
  return in_consult_phase;
 };
 
 public synchronized void 	retry()
 {
  if (!in_consult_phase)
   query.retry();
 };
 
/**
* Displays errors and other output that results from consulting the source.
* 
* @param s 		the input string to append to errors. 
*/
 public void 		printOutput(String s)
 {
  if (in_consult_phase)
   consult.printOutput(s);
  else
   query.printOutput(s); 
 };
};

