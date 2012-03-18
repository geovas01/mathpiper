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
//	gStackPanel
//##################################################################################

package ubc.cs.JLog.Applet;

import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Foundation.*;

public class gStackPanel extends Panel {
    protected final static String STACKLABEL = " Stack";

    protected final static String DISPLAY = "Display";

    protected java.awt.List stacklist;
    protected Label stacklabel;
    protected Button display;

    protected TextArea displayarea;

    // required for debug functionality
    protected jPrologServices prolog;
    protected iDebugGoalStack debugstack = null;
    protected Vector debugstack_vector;
    protected boolean use_proved_stack;

    public gStackPanel(String name, TextArea darea, jPrologServices ps,
	    boolean ups) {
	displayarea = darea;
	debugstack_vector = new Vector(0);
	use_proved_stack = ups;
	{
	    prolog = ps;

	    prolog.addDebugMessagesListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {
		    if (e instanceof jDebugProverGoalStackEvent) {
			if (((jDebugProverGoalStackEvent) e).isProvedStack() == use_proved_stack)
			    debugProverStarted((jDebugProverGoalStackEvent) e);
		    }
		}
	    });
	    prolog.addEndQueryListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {
		    queryTerminated();
		}
	    });
	    prolog.addThreadStoppedListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {
		    queryTerminated();
		}
	    });
	}
	{// create list for stack
	    stacklist = new java.awt.List();
	    stacklist.setBackground(Color.white);
	    stacklist.setForeground(Color.black);
	    stacklist.setMultipleMode(false);
	    stacklist.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
		    display.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
		}
	    });
	    stacklist.setFont(new Font("Monospaced", Font.PLAIN, 10));

	    stacklabel = new Label(name + STACKLABEL, Label.LEFT);

	    stacklist.setEnabled(false);
	}
	{// create button for display
	    display = new Button(DISPLAY);
	    display.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    int sel = stacklist.getSelectedIndex();

		    if (sel >= 0)
			displayGoal(sel);
		}
	    });
	    display.setBackground(Color.white);
	    display.setForeground(Color.black);

	    display.setEnabled(false);
	}

	{
	    Panel button_panel, top_panel, inner_panel;

	    setLayout(new BorderLayout());
	    setFont(new Font("SansSerif", Font.PLAIN, 12));
	    setBackground(Color.lightGray);
	    setForeground(Color.black);

	    add(new Panel(), BorderLayout.NORTH);

	    button_panel = new Panel(new BorderLayout());
	    top_panel = new Panel(new BorderLayout());
	    inner_panel = new Panel(new BorderLayout());

	    button_panel.add(display, BorderLayout.EAST);
	    button_panel.add(new Panel(), BorderLayout.NORTH);

	    top_panel.add(stacklist, BorderLayout.CENTER);
	    top_panel.add(stacklabel, BorderLayout.NORTH);

	    inner_panel.add(top_panel, BorderLayout.CENTER);
	    inner_panel.add(button_panel, BorderLayout.SOUTH);

	    add(inner_panel, BorderLayout.CENTER);
	}
    };

    public synchronized void debugProverStarted(jDebugProverGoalStackEvent de) {
	debugstack = de.getDebugGoalStack();
	debugstack_vector = debugstack.getStackCopy();
	internal_updateStackList(-1);
    };

    public synchronized void queryTerminated() {
	debugstack = null;
	debugstack_vector = new Vector(0);
	stacklist.removeAll();
    };

    public void enablePanel(boolean state) {
	stacklist.setEnabled(state);
	display.setEnabled(stacklist.getSelectedIndex() < 0 ? false : state);
    };

    public synchronized void update() {
	if (debugstack != null)
	    updateStackVector();
    };

    public void displayGoal(int n) {
	if (debugstack != null)
	    internal_displayGoal(debugstack_vector.elementAt(n));
    };

    public synchronized void updateStackVector() {
	Vector newv = debugstack.getStackCopy();
	int top = -1, i, max = Math.min(debugstack_vector.size(), newv.size());
	jGoal g1, g2;

	for (i = 0; i < max; i++) {
	    g1 = internal_getGoal(debugstack_vector.elementAt(i));
	    g2 = internal_getGoal(newv.elementAt(i));

	    if (g1 != g2)
		break;
	    else
		top = i;
	}

	debugstack_vector = newv;
	internal_updateStackList(top);
    };

    protected void internal_updateStackList(int top) {
	int i, max;

	for (i = stacklist.getItemCount() - 1; i > top; i--)
	    stacklist.remove(i);

	for (i = top + 1, max = debugstack_vector.size(); i < max; i++)
	    stacklist.add(
		    internal_getNameArityString(i,
			    internal_getGoal(debugstack_vector.elementAt(i))),
		    i);

	display.setEnabled(stacklist.getSelectedIndex() >= 0);
    };

    protected String internal_getNameArityString(int index, iNameArity na) {
	return new String("[" + String.valueOf(index) + "] " + na.getName()
		+ "/" + String.valueOf(na.getArity()));
    };

    protected jGoal internal_getGoal(Object o) {
	if (o instanceof jDebugProvedGoalStack.jDebugGoalItem)
	    return ((jDebugProvedGoalStack.jDebugGoalItem) o).getGoal();
	else
	    return (jGoal) o;
    };

    protected void internal_displayGoal(Object o) {
	displayarea.append("\n"
		+ internal_getDisplayString(debugstack_vector.indexOf(o),
			internal_getGoal(o)) + "\n");
    };

    protected String internal_getDisplayString(int i, jGoal g) {
	return "[g" + String.valueOf(i) + "] " + g.toString();
    };

    public synchronized int getGoalIndex(jGoal g) {
	if (debugstack != null) {
	    int i, max = debugstack_vector.size();

	    for (i = 0; i < max; i++)
		if (g == internal_getGoal(debugstack_vector.elementAt(i)))
		    return i;
	}

	return -1;
    };
};