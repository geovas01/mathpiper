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
//	jUserQueryThread
//#########################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.util.*;
import java.io.PrintWriter;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Parser.*;

/**
 * This class is the thread that attempts to prove a user query. It can parse an
 * input stream into the predicates of a user query, and it contains the Prolog
 * prover itself. The prover runs as part of this thread.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public class jUserQueryThread extends jRetryQueryThread {
    protected iPrologServiceText qinput;
    protected PrintWriter output;
    protected jPrologServiceBroadcaster beginq = null, retryq = null,
	    endq = null;
    protected jPrologServiceBroadcaster debugm = null;

    protected jProver prover = null;
    protected jPredicateTerms query = null;
    protected boolean result = false;
    protected int retry = 0;
    protected jVariableVector var_vector = null;

    public jUserQueryThread(jPrologServices ps, iPrologServiceText qin,
	    PrintWriter o) {
	super(ps);

	setName("UserQueryThread");
	// setPriority(NORM_PRIORITY + 1); //MRJ 2.1 doesn't permit UI
	// interaction with this setting

	qinput = qin;
	output = o;
    };

    public void setListeners(jPrologServiceBroadcaster b,
	    jPrologServiceBroadcaster r, jPrologServiceBroadcaster e,
	    jPrologServiceBroadcaster s, jPrologServiceBroadcaster d) {
	setStoppedListeners(s);
	beginq = b;
	retryq = r;
	endq = e;
	debugm = d;
    };

    public synchronized void retry() {
	retry++;
	notify();
    };

    protected synchronized boolean waitForRetry() {
	while (retry <= 0) {
	    try {
		wait();
	    } catch (InterruptedException e) {
		return false;
	    }
	}
	retry--;
	return true;
    };

    public void run() {
	if (beginq != null)
	    beginq.broadcastEvent(new jPrologServiceEvent());

	try {
	    query();

	    if (retryq != null)
		retryq.broadcastEvent(new jUserQueryEvent(result));

	    while (result) {
		if (waitForRetry()) {
		    internal_retry();
		    internal_display();

		    if (retryq != null)
			retryq.broadcastEvent(new jUserQueryEvent(result));
		}
	    }
	} catch (ThreadDeath e) {
	    throw e; // thread death is user controlled
	} catch (Error e) {
	    output.println("JAVA ERROR:");
	    output.println(e.toString() + "\n");
	    output.flush();
	    qinput.requestFocus();
	    throw e;
	} finally {
	    if (allow_release)
		prolog.release();

	    var_vector = null;

	    if (endq != null)
		endq.broadcastEvent(new jPrologServiceEvent());
	}
    };

    protected void query() {
	pParseStream parser;

	parser = new pParseStream(qinput.getText(), prolog.getKnowledgeBase(),
		prolog.getPredicateRegistry(), prolog.getOperatorRegistry());

	try {
	    result = false;
	    query = parser.parseQuery();

	    if (query != null) {
		var_vector = new jVariableVector();
		query.registerVariables(var_vector);

		output.println(query.toString(true));
		output.flush();

		internal_prove();
		internal_display();
	    } else {
		output.println("empty query.");
		output.flush();
	    }
	} catch (SyntaxErrorException e) {
	    output.println("SYNTAX ERROR:");
	    output.println(e.toString() + "\n");
	    output.flush();
	    qinput.setCaretPosition(e.getPosition());
	    qinput.select(e.getPosition(), e.getPosition() + 1);
	    qinput.requestFocus();
	} catch (TokenizeStreamException e) {
	    output.println("INTERNAL ERROR:");
	    output.println(e.toString() + "\n");
	    output.flush();
	    qinput.setCaretPosition(e.getPosition());
	    qinput.select(e.getPosition(), e.getPosition() + 1);
	    qinput.requestFocus();
	} catch (RuntimeException e) {
	    output.println("ERROR:");
	    output.println(e.toString() + "\n");
	    output.flush();
	    qinput.requestFocus();
	}
    };

    protected void internal_prove() {
	prover = (prolog.getDebugging() ? new jDebugProver(
		prolog.getKnowledgeBase(), debugm) : new jProver(
		prolog.getKnowledgeBase()));

	result = prover.prove(query);
    };

    protected void internal_retry() {
	if (result) {
	    try {
		result = prover.retry();
	    } catch (InvalidInputException e) {
		if (!e.isHandled()) {
		    output.println("ERROR:");
		    output.println(e.toString() + "\n");
		    output.flush();
		    qinput.requestFocus();
		}
		result = false;
	    } catch (RuntimeException e) {
		output.println("ERROR:");
		output.println(e.toString() + "\n");
		output.flush();
		qinput.requestFocus();
		result = false;
	    }
	}
    };

    protected void internal_display() {
	if (query == null)
	    return;

	if (result) {
	    jVariable[] vars;
	    int i, max;

	    vars = var_vector.getVariables();

	    for (i = 0, max = vars.length; i < max; i++)
		if (vars[i].isNamedForDisplay())
		    output.println(vars[i].getName() + " = "
			    + vars[i].toString(false));

	    output.println("yes");
	} else
	    output.println("no");

	output.flush();
    };
};
