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
//	gFindDialog
//##################################################################################

package ubc.cs.JLog.Applet;

import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;

public class gFindDialog extends Dialog {
    protected TextField find_field;
    protected String find_string = null;

    public gFindDialog(gWindowBase w, String initial_find) {
	super(w, "Find", true);

	setLayout(new BorderLayout());

	{
	    Panel fields = new Panel();
	    Font font = new Font("Dialog", Font.BOLD, 12);
	    Label info;

	    fields.setLayout(new BorderLayout());

	    info = new Label("Find: ", Label.LEFT);
	    info.setFont(font);

	    fields.add(info, BorderLayout.WEST);

	    find_field = new TextField((initial_find == null ? ""
		    : initial_find));
	    find_field.setFont(font);

	    find_field.addKeyListener(new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
		    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			find_string = find_field.getText();
			close();
		    }
		}
	    });

	    fields.add(find_field, BorderLayout.CENTER);
	    fields.add(new Panel(), BorderLayout.SOUTH);
	    add(fields, BorderLayout.CENTER);
	}

	add(new Panel(), BorderLayout.WEST);
	add(new Panel(), BorderLayout.EAST);
	add(new Panel(), BorderLayout.NORTH);

	{
	    Panel buttons = new Panel();
	    Panel find = new Panel();
	    Button b;

	    buttons.setLayout(new BorderLayout());
	    find.setLayout(new BorderLayout());

	    b = new Button("Find");
	    b.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    find_string = find_field.getText();
		    close();
		}
	    });
	    b.setBackground(Color.white);
	    b.setForeground(Color.black);

	    find.add(b, BorderLayout.EAST);
	    find.add(new Panel(), BorderLayout.CENTER);
	    find.add(new Panel(), BorderLayout.WEST);
	    buttons.add(find, BorderLayout.CENTER);
	    buttons.add(new Panel(), BorderLayout.EAST);
	    buttons.add(new Panel(), BorderLayout.WEST);
	    buttons.add(new Panel(), BorderLayout.SOUTH);
	    add(buttons, BorderLayout.SOUTH);
	}

	pack();

	addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent evt) {
		close();
	    }
	});

	setSize(400, 100);
	{
	    Point ploc = w.getLocation();

	    setLocation(ploc.x + 16, ploc.y + 32);
	}
	setVisible(true);
    };

    public String getFindString() {
	return find_string;
    };

    protected void close() {
	dispose();
    };
};
