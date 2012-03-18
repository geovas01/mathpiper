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
//	gAnimationPanel
//##################################################################################

package ubc.cs.JLog.Applet;

import java.lang.*;
import java.lang.reflect.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import ubc.cs.JLog.Foundation.*;

/**
 * This is the panel for displaying a graphical environment.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public class gAnimationPanel extends Panel {
    protected static final String ANIMATION_ENV = "ubc.cs.JLog.Animation.aAnimationEnvironment";

    public gAnimationPanel(iJLogApplBaseServices b, jPrologServices prolog) {
	Panel animation = null;

	{// create area for animation to appear in

	    try {
		Class a_class = null;
		Constructor a_cons = null;

		a_class = Class.forName(ANIMATION_ENV);
		a_cons = a_class
			.getConstructor(new Class[] { iJLogApplBaseServices.class });
		animation = (Panel) a_cons.newInstance(new Object[] { b });

		prolog.setAnimationEnvironment(animation);
	    } catch (Exception e) {
		animation = makeErrorPanel();
	    }
	}

	{
	    Panel inner_panel;
	    ScrollPane scroll_animation;

	    setLayout(new BorderLayout());
	    setFont(new Font("SansSerif", Font.PLAIN, 12));
	    setBackground(Color.lightGray);
	    setForeground(Color.black);

	    add(new Panel(), BorderLayout.NORTH);
	    add(new Panel(), BorderLayout.WEST);
	    add(new Panel(), BorderLayout.EAST);
	    add(new Panel(), BorderLayout.SOUTH);

	    scroll_animation = new ScrollPane();
	    scroll_animation.add(animation, BorderLayout.CENTER);

	    inner_panel = new Panel(new BorderLayout());

	    inner_panel.add(scroll_animation, BorderLayout.CENTER);
	    inner_panel.add(new Panel(), BorderLayout.SOUTH);

	    add(inner_panel, BorderLayout.CENTER);
	}
    };

    private Panel makeErrorPanel() {
	String err_str = "ERROR: Animation Library not available - Missing class: "
		+ ANIMATION_ENV;
	Panel p = new Panel(new BorderLayout());

	p.setFont(new Font("Dialog", Font.PLAIN, 12));
	p.setBackground(Color.lightGray);
	p.setForeground(Color.black);
	p.add(new Label(err_str), BorderLayout.CENTER);

	return p;
    };
};