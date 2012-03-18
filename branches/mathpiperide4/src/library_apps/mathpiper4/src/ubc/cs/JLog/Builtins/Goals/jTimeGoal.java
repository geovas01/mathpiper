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
//	TimeGoal
//#########################################################################

package ubc.cs.JLog.Builtins.Goals;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Builtins.*;

public class jTimeGoal extends jCallGoal {
    protected class jReportTimeGoal extends jGoal {
	public long start_time;
	public long last_time;
	public long total_time;

	protected jTimeGoal parent;

	public jReportTimeGoal(jTimeGoal p) {
	    parent = p;
	}

	public boolean prove(iGoalStack goals, iGoalStack proved) {
	    long current_time = System.currentTimeMillis();
	    long elapsed_time = current_time - last_time;

	    total_time += elapsed_time;

	    {
		Thread t = Thread.currentThread();

		if (t instanceof jPrologServiceThread) {
		    jPrologServiceThread pst = (jPrologServiceThread) t;
		    jPrologServices ps = pst.getPrologServices();

		    pst.printOutput("Time (ms): " + Long.toString(elapsed_time)
			    + " ");
		    pst.printOutput("Total time (ms): "
			    + Long.toString(total_time));
		    pst.printOutput("\n");
		}
	    }

	    proved.push(this);
	    return true;
	}

	public boolean retry(iGoalStack goals, iGoalStack proved) {
	    last_time = System.currentTimeMillis();

	    goals.push(this);
	    return false;
	}

	public String getName() {
	    return "REPORT_TIME";
	}

	public int getArity() {
	    return 0;
	}

	public String toString() {
	    StringBuffer sb = new StringBuffer();

	    sb.append(getName() + "/" + String.valueOf(getArity()));

	    return sb.toString();
	}
    };

    protected jReportTimeGoal report_time_goal;

    public jTimeGoal(jTerm c) {
	super(c);

	report_time_goal = new jReportTimeGoal(this);
    };

    public boolean prove(iGoalStack goals, iGoalStack proved) {
	jGoal eg = (goals.empty() ? null : goals.peek());

	report_time_goal.start_time = report_time_goal.last_time = System
		.currentTimeMillis();
	report_time_goal.total_time = 0L;

	goals.push(report_time_goal);
	super.prove(goals, proved); // assume always true
	setEndGoal(eg);

	return true;
    };

    public String getName() {
	return "time";
    };

    public int getArity() {
	return 1;
    };

    public String toString() {
	StringBuffer sb = new StringBuffer();

	sb.append(getName() + "/" + String.valueOf(getArity()) + " goal: ");
	sb.append(getName() + "(" + callee.toString() + ")");

	return sb.toString();
    };
};
