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
//	jConsultSourceThread
//#########################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.util.*;
import java.io.PrintWriter;
import ubc.cs.JLog.Parser.*;

/**
 * This class implements consulting of a prolog source text. The source is a
 * <code>TextArea</code>, as is the error output.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public class jConsultSourceThread extends jPrologServiceThread {
    protected iPrologServiceText source;
    protected PrintWriter errors;
    protected jPrologServiceBroadcaster begin = null, end = null;

    public jConsultSourceThread(jPrologServices ps, iPrologServiceText s,
	    PrintWriter e) {
	super(ps);

	setName("ConsultSourceThread");
	// setPriority(NORM_PRIORITY + 1); //MRJ 2.1 doesn't permit UI
	// interaction with this setting

	source = s;
	errors = e;
    };

    public void setListeners(jPrologServiceBroadcaster b,
	    jPrologServiceBroadcaster e, jPrologServiceBroadcaster s) {
	setStoppedListeners(s);
	begin = b;
	end = e;
    };

    /**
     * Performs and controls the entire consultation phase.
     * 
     */
    public void run() {
	if (begin != null)
	    begin.broadcastEvent(new jPrologServiceEvent());

	errors.println("consulting.");
	errors.flush();

	try {
	    pParseStream parser;

	    parser = new pParseStream(source.getText(),
		    prolog.getKnowledgeBase(), prolog.getPredicateRegistry(),
		    prolog.getOperatorRegistry());

	    try {
		parser.parseSource();
		prolog.getKnowledgeBase().consult();

		errors.print("completed.");
		errors.flush();
	    } catch (SyntaxErrorException e) {
		errors.println("SYNTAX ERROR:");
		errors.println(e.toString() + "\n");
		source.setCaretPosition(e.getPosition());
		source.select(e.getPosition(), e.getPosition() + 1);
		errors.print("terminated.");
		errors.flush();
		source.requestFocus();
	    } catch (TokenizeStreamException e) {
		errors.println("INTERNAL ERROR:");
		errors.println(e.toString() + "\n");
		source.setCaretPosition(e.getPosition());
		source.select(e.getPosition(), e.getPosition() + 1);
		errors.print("terminated.");
		errors.flush();
		source.requestFocus();
	    } catch (RuntimeException e) {
		errors.println("ERROR:");
		errors.println(e.toString() + "\n");
		errors.print("terminated.");
		errors.flush();
		source.requestFocus();
	    }
	} catch (ThreadDeath e) {
	    throw e; // thread death is user controlled
	} catch (Error e) {
	    errors.println("JAVA ERROR:");
	    errors.println(e.toString() + "\n");
	    errors.print("terminated.");
	    errors.flush();
	    source.requestFocus();
	    throw e;
	} finally {
	    if (allow_release)
		prolog.release();

	    if (end != null)
		end.broadcastEvent(new jPrologServiceEvent());
	}
    };

    public boolean isCurrentlyConsulting() {
	return true;
    };

    /**
     * Displays errors and other output that results from consulting the source.
     * 
     * @param s
     *            the input string to append to errors.
     */
    public void printOutput(String s) {
	errors.print(s);
	errors.flush();
    };
};
