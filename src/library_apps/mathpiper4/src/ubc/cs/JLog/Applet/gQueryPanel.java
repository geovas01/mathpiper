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
//	gQueryPanel
//##################################################################################

package ubc.cs.JLog.Applet;

import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import ubc.cs.JLog.Foundation.*;

public class gQueryPanel extends Panel {
    protected final static String QUERY = "   Query  ";
    protected final static String INPUT = "   Input  ";
    protected final static String RETRY = "   Retry  ";
    protected final static String PAUSE = "   Pause  ";
    protected final static String CONTINUE = " Continue ";
    protected final static String STOP = "   Stop  ";
    protected final static String CLEAR = "   Clear  ";
    protected final static String QLABEL = "Query";
    protected final static String INLABEL = "Input";
    protected final static String OUTLABEL = "Output";

    // required for query appearance
    protected TextArea output;
    protected TextField input;
    protected TextField qinput;
    protected Button query, retry, pause, stop, enter, clear;
    protected Label outlabel, inlabel, qlabel;

    // required for query functionality
    protected jPrologServices prolog;
    protected jRetryQueryThread thread = null;
    protected gButtonTabMenuAction debug_action = null;

    public gQueryPanel(jPrologServices ps, String default_query_text,
	    boolean show_buttons) {
	{// create text area for prolog output
	    output = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
	    output.setBackground(Color.white);
	    output.setForeground(Color.black);
	    output.setFont(new Font("Monospaced", Font.PLAIN, 12));

	    outlabel = new Label(OUTLABEL, Label.LEFT);
	}
	{// create text area for prolog input
	    input = new TextField();
	    input.setBackground(Color.white);
	    input.setForeground(Color.black);
	    input.setEditable(false);

	    inlabel = new Label(INLABEL, Label.LEFT);
	}
	{// create input button
	    enter = new Button(INPUT);
	    enter.setBackground(Color.white);
	    enter.setForeground(Color.black);
	    enter.setEnabled(false);
	}
	{
	    prolog = ps;
	    prolog.setDefaultOutput(getOutputStream());
	    prolog.setDefaultInput(getInputStream());
	    prolog.addRetryQueryListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {
		    if (e instanceof jUserQueryEvent) {
			jUserQueryEvent uqe = (jUserQueryEvent) e;

			if (uqe.getResult()) {
			    gQueryPanel.this.retry.setEnabled(true);
			    gQueryPanel.this.query.setEnabled(true);
			    gQueryPanel.this.qinput.setEditable(true);
			}
		    }
		}
	    });
	    prolog.addEndQueryListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {
		    gQueryPanel.this.reset_buttons();
		}
	    });
	    prolog.addBeginConsultListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {// this should
								// only occur
								// when no query
								// is already
								// running
								// so our
								// responce is
								// very simple
		    query.setEnabled(false);
		}
	    });
	    prolog.addEndConsultListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {// this should
								// only occur
								// when no query
								// is already
								// running
								// so our
								// responce is
								// very simple
		    query.setEnabled(true);
		}
	    });
	}
	{// create text area for prolog query
	    qinput = new TextField();
	    qinput.addKeyListener(new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
		    if (e.getKeyCode() == KeyEvent.VK_ENTER
			    && qinput.isEditable() && query.isEnabled())
			gQueryPanel.this.query();
		}
	    });
	    qinput.setBackground(Color.white);
	    qinput.setForeground(Color.black);

	    qinput.setText(default_query_text);

	    qlabel = new Label(QLABEL, Label.LEFT);
	}
	{// create query button
	    query = new Button(QUERY);
	    query.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    gQueryPanel.this.query();
		}
	    });
	    query.setBackground(Color.white);
	    query.setForeground(Color.black);
	}
	{// create retry button
	    retry = new Button(RETRY);
	    retry.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    gQueryPanel.this.retry();
		}
	    });
	    retry.setBackground(Color.white);
	    retry.setForeground(Color.black);
	    retry.setEnabled(false);
	}
	{// create pause button
	    pause = new Button(PAUSE);
	    pause.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    gQueryPanel.this.pause();
		}
	    });
	    pause.setBackground(Color.white);
	    pause.setForeground(Color.black);
	    pause.setEnabled(false);
	}
	{// create stop button
	    stop = new Button(STOP);
	    stop.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    gQueryPanel.this.stop();
		}
	    });
	    stop.setBackground(Color.white);
	    stop.setForeground(Color.black);
	    stop.setEnabled(false);
	}
	{// create clear button
	    clear = new Button(CLEAR);
	    clear.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    gQueryPanel.this.clear();
		}
	    });
	    clear.setBackground(Color.white);
	    clear.setForeground(Color.black);
	}

	{
	    Panel query_panel, input_panel, output_panel, q_buttons, in_buttons, out_buttons, inner_panel;
	    Panel qbutton_panel, inbutton_panel, outbutton_panel, top_panel;

	    setLayout(new BorderLayout());
	    setFont(new Font("SansSerif", Font.PLAIN, 12));
	    setBackground(Color.lightGray);
	    setForeground(Color.black);

	    add(new Panel(), BorderLayout.NORTH);
	    add(new Panel(), BorderLayout.WEST);
	    add(new Panel(), BorderLayout.EAST);
	    add(new Panel(), BorderLayout.SOUTH);

	    query_panel = new Panel(new BorderLayout());
	    input_panel = new Panel(new BorderLayout());
	    q_buttons = new Panel(new GridLayout(1, 0));
	    in_buttons = new Panel(new GridLayout(1, 0));
	    out_buttons = new Panel(new GridLayout(1, 0));
	    output_panel = new Panel(new BorderLayout());
	    qbutton_panel = new Panel(new BorderLayout());
	    inbutton_panel = new Panel(new BorderLayout());
	    outbutton_panel = new Panel(new BorderLayout());
	    inner_panel = new Panel(new BorderLayout());
	    top_panel = new Panel(new BorderLayout());

	    q_buttons.add(query);
	    q_buttons.add(retry);

	    if (show_buttons) {
		q_buttons.add(pause);
		q_buttons.add(stop);
	    }

	    in_buttons.add(enter);

	    out_buttons.add(clear);

	    qbutton_panel.add(q_buttons, BorderLayout.CENTER);
	    qbutton_panel.add(new Panel(), BorderLayout.WEST);

	    inbutton_panel.add(in_buttons, BorderLayout.CENTER);
	    inbutton_panel.add(new Panel(), BorderLayout.WEST);

	    outbutton_panel.add(out_buttons, BorderLayout.EAST);
	    outbutton_panel.add(new Panel(), BorderLayout.NORTH);
	    outbutton_panel.add(new Panel(), BorderLayout.SOUTH);

	    query_panel.add(qinput, BorderLayout.CENTER);
	    query_panel.add(qbutton_panel, BorderLayout.EAST);
	    query_panel.add(qlabel, BorderLayout.NORTH);

	    input_panel.add(input, BorderLayout.CENTER);
	    input_panel.add(inbutton_panel, BorderLayout.EAST);
	    input_panel.add(inlabel, BorderLayout.NORTH);

	    output_panel.add(output, BorderLayout.CENTER);
	    output_panel.add(outbutton_panel, BorderLayout.SOUTH);
	    output_panel.add(outlabel, BorderLayout.NORTH);

	    top_panel.add(query_panel, BorderLayout.NORTH);
	    top_panel.add(input_panel, BorderLayout.SOUTH);

	    inner_panel.add(top_panel, BorderLayout.NORTH);
	    inner_panel.add(output_panel, BorderLayout.CENTER);

	    add(inner_panel, BorderLayout.CENTER);
	}
    };

    public void setSelectDebugPanel(gButtonTabMenuAction da) {
	debug_action = da;
    };

    public iPrologServiceText getQinput() {
	return new gPrologServiceTextField(qinput);
    };

    public iPrologServiceText getInput() {
	return new gPrologServiceTextField(input);
    };

    public PrintWriter getOutputStream() {
	return new PrintWriter(new gOutputStreamTextArea(output));
    };

    public BufferedReader getInputStream() {
	return new BufferedReader(new gInputStreamTextField(input, enter));
    };

    public void pre_query() {
	input.setText("");

	pause.setLabel(PAUSE);

	qinput.setEditable(false);
	query.setEnabled(false);
	retry.setEnabled(false);
	pause.setEnabled(true);
	stop.setEnabled(true);

	if (thread != null && thread.isAlive()) {
	    thread.broadcasted_stop();

	    try {
		thread.join();
	    } catch (InterruptedException e) {
	    }

	    output.append("done.\n");
	}
    };

    // function is only for use by parent object when initiating a thread that
    // contains a query.
    public void set_thread(jRetryQueryThread t) {
	if (thread != null && thread.isAlive()) {
	    thread.broadcasted_stop();

	    try {
		thread.join();
	    } catch (InterruptedException e) {
	    }

	    output.append("done.\n");
	}

	thread = t;
    };

    public void query() {
	pre_query();

	if (!prolog.start(thread = new jUserQueryThread(prolog, getQinput(),
		getOutputStream())))
	    output.append("query failed. other events pending.\n");
	else if (prolog.getDebugging() && debug_action != null)
	    debug_action.switchToCard();
    };

    public void retry() {
	qinput.setEditable(false);
	query.setEnabled(false);
	retry.setEnabled(false);

	if (thread != null && thread.isAlive()) {
	    thread.retry();

	    if (prolog.getDebugging() && debug_action != null)
		debug_action.switchToCard();
	} else
	    output.append("prolog thread not available.\n");
    };

    public void pause() {
	if (thread == null || !thread.isAlive()) {
	    pause.setEnabled(false);
	    output.append("prolog thread not available.\n");
	} else if (pause.getLabel() == PAUSE) {
	    pause.setLabel(CONTINUE);
	    thread.suspend();
	} else {
	    pause.setLabel(PAUSE);
	    thread.resume();
	}
    };

    public boolean isPaused() {
	return (pause.getLabel() == CONTINUE);
    };

    public void stop() {
	reset_buttons();

	if (thread != null && thread.isAlive())
	    thread.broadcasted_stop();

	thread = null;

	output.append("done.\n");
    };

    protected void reset_buttons() {
	pause.setLabel(PAUSE);

	qinput.setEditable(true);
	input.setEditable(false);
	enter.setEnabled(false);
	query.setEnabled(true);
	retry.setEnabled(false);
	pause.setEnabled(false);
	stop.setEnabled(false);
    };

    public void clear() {
	output.setText("");
    };
};