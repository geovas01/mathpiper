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
//##################################################################################
//	gProverStackPanel
//##################################################################################

package ubc.cs.JLog.Applet;

import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import ubc.cs.JLog.Foundation.*;

public class gProverStackPanel extends gStackPanel {
    // required for debug functionality
    protected gStackPanel otherstackpanel;

    public gProverStackPanel(String name, TextArea darea, jPrologServices ps,
	    gStackPanel gps) {
	super(name, darea, ps, true);
	otherstackpanel = gps;
    };

    protected void internal_displayGoal(Object o) {
	displayarea.append("\n"
		+ internal_getDisplayString(debugstack_vector.indexOf(o),
			internal_getGoal(o)) + "\n");
	if (o instanceof jDebugProvedGoalStack.jDebugGoalItem) {
	    jDebugProvedGoalStack.jDebugGoalItem gi = (jDebugProvedGoalStack.jDebugGoalItem) o;
	    Vector sg = gi.getSubGoals();

	    if (sg.size() > 0) {
		Enumeration e = sg.elements();

		displayarea.append("sub goals:\n");
		while (e.hasMoreElements()) {
		    jGoal g = (jGoal) e.nextElement();
		    int index;

		    if ((index = getGoalIndex(g)) >= 0)
			displayarea.append(internal_getDisplayString(index, g));
		    else if ((index = otherstackpanel.getGoalIndex(g)) >= 0)
			displayarea.append(otherstackpanel
				.internal_getDisplayString(index, g));
		    else
			displayarea.append("[trying] " + g.toString());

		    displayarea.append("\n");
		}
	    }
	    displayarea.append("\n");
	}
    };

    protected String internal_getDisplayString(int i, jGoal g) {
	return "[p" + String.valueOf(i) + "] " + g.toString();
    };
};