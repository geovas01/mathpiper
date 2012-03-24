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
//	ReadGoal
//#########################################################################

package ubc.cs.JLog.Builtins.Goals;

import java.lang.*;
import java.util.*;
import java.io.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Parser.*;
import ubc.cs.JLog.Builtins.*;

/**
 * Goal for inputing text from the console.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public class jReadGoal extends jGoal {
    protected jRead read;

    // for use by read
    public jTerm term;
    public jUnifiedVector unified;

    public jReadGoal(jRead r, jTerm t) {
	read = r;
	term = t;
	unified = new jUnifiedVector();
    };

    public boolean prove(iGoalStack goals, iGoalStack proved) {
	jTerm in;

	in = getInput();

	if (read.prove(this, in)) {
	    proved.push(this);
	    return true;
	} else {
	    { // we need to initialize goal to potentially restart
		unified.restoreVariables();
	    }
	    goals.push(this); // a retry that follows may need a node to remove
			      // or retry
	    return false;
	}
    };

    public boolean retry(iGoalStack goals, iGoalStack proved) {
	unified.restoreVariables();

	goals.push(this); // a retry that follows may need a node to remove or
			  // retry
	return false;
    };

    public void internal_restore(iGoalStack goals) {
	unified.restoreVariables();
    };

    protected jTerm getInput() {
	Thread thread = Thread.currentThread();

	if (thread instanceof jPrologServiceThread) {
	    jPrologServiceThread pst = (jPrologServiceThread) thread;
	    jPrologServices prolog = pst.getPrologServices();
	    BufferedReader in = prolog.getInput();
	    PrintWriter out = prolog.getOutput();
	    String input = null;

	    if (in == null)
		throw new MissingInputServicesException(
			"InputStream Services unavailable");

	    while (input == null) {
		try {
		    input = in.readLine();
		} catch (IOException e) {
		    throw new InvalidInputException();
		}

		if (input == null)
		    throw new InvalidInputException();

		{
		    pParseStream parser;

		    parser = new pParseStream(input, prolog.getKnowledgeBase(),
			    prolog.getPredicateRegistry(),
			    prolog.getOperatorRegistry());

		    try {
			jTerm trm;

			trm = parser.parseTerm();

			if (trm != null) {
			    out.println(trm.toString(true));
			    out.flush();
			    return trm;
			} else
			    throw new InvalidInputException();
		    } catch (SyntaxErrorException e) {
			out.println(input);
			out.println("SYNTAX ERROR:");
			out.println(e.toString());
			out.flush();
			input = null;
			out.println("Try Again...\n");
			out.flush();
		    } catch (TokenizeStreamException e) {
			out.println(input);
			out.println("INTERNAL ERROR:");
			out.println(e.toString());
			out.flush();
			input = null;
			out.println("Try Again...\n");
			out.flush();
		    }
		}
	    }
	    throw new InvalidInputException();
	} else
	    throw new MissingInputServicesException();
    };

    public String getName() {
	return read.getName();
    };

    public int getArity() {
	return read.getArity();
    };

    public String toString() {
	StringBuffer sb = new StringBuffer();

	sb.append(getName() + "/" + String.valueOf(getArity()) + " GOAL: ");
	sb.append(getName() + "(" + term.toString() + ")");

	return sb.toString();
    };
};
