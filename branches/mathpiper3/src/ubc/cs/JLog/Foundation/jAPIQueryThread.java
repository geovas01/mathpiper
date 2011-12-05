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
//	jAPIQueryThread
//#########################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Parser.*;

/**
* This class is the thread that attempts to prove a query. It can parse an
* input string into the predicates of a user query, and it contains the 
* Prolog prover itself.  The prover runs as part of this thread.  It is similar to 
* <code>jUserQueryThread</code>, but is designed for use via <code>jPrologAPI</code> where the 
* caller is responsible to handling exceptions.
*
* @author       Glendon Holst
* @version      %I%, %G%
*/
public class jAPIQueryThread extends jRetryQueryThread //jUserQueryThread
{
 protected String							qinput;

 protected jPrologServiceBroadcaster 		beginq = null,retryq = null,endq = null;
 protected jPrologServiceBroadcaster 		debugm = null;
 

 protected jProver 				prover = null;
 protected jPredicateTerms		query = null;
 protected boolean 				result = false;
 protected int 					retry = 0;
 protected jVariableVector		var_vector = null;
 protected Hashtable			var_prebindings;
 protected RuntimeException		result_exception = null;
 
 public 	jAPIQueryThread(jPrologServices ps,String qin)
 {
  super(ps);
 // setPriority(NORM_PRIORITY + 1); //MRJ 2.1 doesn't permit UI interaction with this setting

  var_prebindings = null;
  qinput = qin;
 };

 public 	jAPIQueryThread(jPrologServices ps,Hashtable bindings,String qin)
 {
  super(ps);

  setName("APIQueryThread");
 // setPriority(NORM_PRIORITY + 1); //MRJ 2.1 doesn't permit UI interaction with this setting

  var_prebindings = bindings;
  qinput = qin;
 };

 public void 	setListeners(jPrologServiceBroadcaster b,jPrologServiceBroadcaster r,
                                jPrologServiceBroadcaster e,jPrologServiceBroadcaster s,
								jPrologServiceBroadcaster d)
 {
  setStoppedListeners(s);
  beginq = b;
  retryq = r;
  endq = e;
  debugm = d;
 };

 public synchronized void 	retry()
 {
  retry++;
  notify();
 };

 protected synchronized boolean 	waitForRetry()
 {
  while (retry <= 0)
  {
   try
   {
    wait();   
   }
   catch (InterruptedException e)
   {
    return false;
   }
  }
  retry--;
  return true;
 };

 public void 	run()
 {
  result_exception = null;

  if (beginq != null)
   beginq.broadcastEvent(new jPrologServiceEvent());
 
  try
  {
   query();
 
   if (retryq != null)
    retryq.broadcastEvent(new jUserQueryEvent(result));
  
   while (result)
   {
    if (waitForRetry())
    {
     internal_retry();
    
     if (retryq != null)
      retryq.broadcastEvent(new jUserQueryEvent(result));
    }
   }
  }
  catch (RuntimeException e)
  {
   result_exception = e;
  }
  finally
  {
   if (allow_release)
    prolog.release();

   var_vector = null;

   if (endq != null)
    endq.broadcastEvent(new jPrologServiceEvent());
  }
 };
 
 protected void 	query()
 {pParseStream 	parser;
   
  parser = new pParseStream(qinput,prolog.getKnowledgeBase(),
                             prolog.getPredicateRegistry(),
                             prolog.getOperatorRegistry());
  result = false;
  query = parser.parseQuery(var_prebindings); 

  if (query != null)
  {
   var_vector = new jVariableVector();
   query.registerVariables(var_vector);

   internal_prove();
  }
 };
 
 protected void 	internal_prove()
 {
  prover = (prolog.getDebugging() ? 
  				new jDebugProver(prolog.getKnowledgeBase(),debugm) :
  				new jProver(prolog.getKnowledgeBase()));
  				
  result = prover.prove(query);
 };
 
 protected void 	internal_retry()
 {
  if (result)
   result = prover.retry();
 };
 
 /**
 * Returns a hashtable of the variable bindings, as a result of a sucessful query / retry.
 * 
 * @return			Returns a hashtable with all the variables in the query, and their bindings.
 *					Each key in the hashtable is a variable name, and the associated value
 *					is the <code>jTerm</code> the variable was bound to. 
 *					Returns null if the query failed.
 */
 public Hashtable 	getResultHashtable()
 {
  if (query == null)
   return null;
   
  if (result)
  {Hashtable		ht = new Hashtable();
   jVariable[] 		vars;
   int				i,max;
      
   vars = var_vector.getVariables(); 
     
   for (i = 0, max = vars.length; i < max; i++)
   {
    if (vars[i].isNamedForDisplay())
	 ht.put(vars[i].getName(),vars[i].getTerm());
   }
   
   return ht;	
  }	
  else
   return null;
 };

 /**
 * Returns the RuntimeException thrown by an unsucessful query / retry 
 * (e.g., UnknownPredicateException).
 * 
 * @return			Returns a RuntimeException if the query failed. 
 *					Returns null if the query did not throw an exception.
 */
 public RuntimeException 	getResultException()
 {
  return result_exception;
 }; 
};
