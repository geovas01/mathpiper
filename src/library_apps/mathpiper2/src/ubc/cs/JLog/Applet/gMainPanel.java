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
//	MainPanel
//#########################################################################

package ubc.cs.JLog.Applet;

import java.awt.*;
import java.io.*;
import java.applet.Applet;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Parser.*;

/**
 * This is the Applet for the full Prolog in Java environment, complete with
 * user interface.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public class gMainPanel extends Panel {
    protected final static String QUERY = "query";
    protected final static String ANIMATION = "animation";
    protected final static String DEBUG = "debug";
    protected final static String CONSULT = "consult";
    protected final static String HELP = "help";

    protected gCardPanel workarea;
    protected gButtonTabMenu menu;
    protected gConsultPanel consult;
    protected gQueryPanel query;
    protected gDebugPanel debug;
    protected gAnimationPanel animation;
    protected gHelpPanel help;

    protected jConsultAndQueryThread thread = null;

    protected iJLogApplBaseServices base = null;

    public gMainPanel(iJLogApplBaseServices b) {
	gButtonTab consult_tab, query_tab, debug_tab, animation_tab, help_tab;
	gButtonTab active_tab = null;
	boolean tab_specified = false;

	base = b;

	{// create consult area for prolog database
	    String source_string = null;
	    String source_errors = null;

	    try {
		source_string = base.getSource();
	    } catch (IOException e) {
		source_errors = "Error Reading Prolog File: " + e.toString();
	    }

	    consult = new gConsultPanel(base.getPrologServices(),
		    source_string, true);

	    if (source_errors != null) {
		PrintWriter errors = consult.getErrorsStream();

		errors.print(source_errors);
		errors.flush();
	    }
	}
	{// create query area for prolog input and output
	    query = new gQueryPanel(base.getPrologServices(),
		    base.getParameter("query"), true);
	}
	{// create area for prolog debugging services
	    debug = new gDebugPanel(base.getPrologServices(), true);
	}
	{// create area for animation animations
	    animation = new gAnimationPanel(base, base.getPrologServices());
	}
	{// create area for help
	    help = new gHelpPanel(base);
	}
	{// create working area for prolog
	    workarea = new gCardPanel();

	    workarea.add(consult, CONSULT);
	    workarea.add(query, QUERY);
	    workarea.add(debug, DEBUG);
	    workarea.add(animation, ANIMATION);
	    workarea.add(help, HELP);
	}
	// create menu and items for workingarea
	{
	    menu = new gButtonTabMenu(gButtonTabMenu.HORIZONTAL);

	    menu.add(consult_tab = new gButtonTab("Consult", menu, workarea,
		    CONSULT));
	    menu.add(query_tab = new gButtonTab("Query", menu, workarea, QUERY));
	    menu.add(debug_tab = new gButtonTab("Debug", menu, workarea, DEBUG));
	    menu.add(animation_tab = new gButtonTab("Animation", menu,
		    workarea, ANIMATION));
	    menu.add(help_tab = new gButtonTab("Help", menu, workarea, HELP));

	    query.setSelectDebugPanel(new gButtonTabMenuAction(debug_tab) {
		public void switchToCard() {
		    menu.setActiveTab(button);
		}
	    });
	}

	setLayout(new BorderLayout());
	add(menu, BorderLayout.NORTH);
	add(workarea, BorderLayout.CENTER);

	// set active pane and button
	{
	    String pane = base.getParameter("pane");

	    if (pane != null && pane.equalsIgnoreCase("consult")) {
		active_tab = consult_tab;
		tab_specified = true;
	    } else if (pane != null && pane.equalsIgnoreCase("query")) {
		active_tab = query_tab;
		tab_specified = true;
	    } else if (pane != null && pane.equalsIgnoreCase("debug")) {
		active_tab = debug_tab;
		tab_specified = true;
	    } else if (pane != null && pane.equalsIgnoreCase("animation")) {
		active_tab = animation_tab;
		tab_specified = true;
	    } else if (pane != null && pane.equalsIgnoreCase("help")) {
		active_tab = help_tab;
		tab_specified = true;
	    } else {
		active_tab = consult_tab;
	    }
	}

	// perform actions
	{
	    String action = base.getParameter("action");

	    if (action != null && action.equalsIgnoreCase("consult")) {
		consult.consult();
	    } else if (action != null && action.equalsIgnoreCase("query")) {
		consult.pre_consult();

		base.getPrologServices().addEndConsultListener(
			new jPrologServiceListener() {
			    public void handleEvent(jPrologServiceEvent e) {
				query.pre_query();
				query.set_thread(gMainPanel.this.thread);
				gMainPanel.this.thread = null;
				base.getPrologServices()
					.removeEndConsultListener(this);
			    }

			    public int getPriority() {
				return 100;
			    }
			});

		if (!base.getPrologServices().start(
			thread = new jConsultAndQueryThread(base
				.getPrologServices(), consult.getSource(),
				consult.getErrorsStream(), query.getQinput(),
				query.getOutputStream()))) {
		    PrintWriter errors = consult.getErrorsStream();
		    PrintWriter output = query.getOutputStream();

		    errors.println("consult failed. other events pending.");
		    errors.flush();
		    output.println("query failed. other events pending.");
		    output.flush();
		} else if (!tab_specified
			&& base.getPrologServices().getDebugging())
		    active_tab = debug_tab;
	    }
	}

	menu.setActiveTab(active_tab);
    };
};
