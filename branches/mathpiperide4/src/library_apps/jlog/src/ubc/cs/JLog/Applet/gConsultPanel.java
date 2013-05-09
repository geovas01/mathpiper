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
//	gConsultPanel
//##################################################################################

package ubc.cs.JLog.Applet;

import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import ubc.cs.JLog.Foundation.*;

public class gConsultPanel extends Panel {
    protected final static String RESET = "Reset KB";
    protected final static String CONSULT = "Consult";
    protected final static String FIND = "Find";
    protected final static String GOTOLINE = "Goto";
    protected final static String ERRLABEL = "Status";
    protected final static String SRCLABEL = "Source";

    protected final static int ERRROWS = 12;

    // required for consult appearance
    protected TextArea source, errors;
    protected TextField find_field, gotoline_field;
    protected Button resetdb, consult, find, gotoline, loadPress;
    protected Label srclabel, errlabel;

    // required for consult functionality
    protected jPrologServices prolog;

    // required for find
    protected String find_str = null;
    protected int find_pos = 0;

    public gConsultPanel(jPrologServices ps, String default_source_text,
	    boolean show_buttons) {
	{
	    prolog = ps;
	    prolog.addBeginQueryListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {// this should
								// only occur
								// when no
								// consult/reset
								// is already
								// running
								// so our
								// responce is
								// very simple
		    gConsultPanel.this.disable_buttons();
		}
	    });
	    prolog.addEndQueryListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {// this should
								// only occur
								// when no
								// consult/reset
								// is already
								// running
								// so our
								// responce is
								// very simple
		    gConsultPanel.this.reset_buttons();
		}
	    });
	    prolog.addEndConsultListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {
		    gConsultPanel.this.reset_buttons();
		}
	    });
	    prolog.addThreadStoppedListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {
		    gConsultPanel.this.reset_buttons();
		}
	    });
	}
	{// create file area for prolog db input
	    source = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
	    source.setBackground(Color.white);
	    source.setForeground(Color.black);
	    source.setFont(new Font("Monospaced", Font.PLAIN, 12));

	    source.setText(default_source_text);

	    srclabel = new Label(SRCLABEL, Label.LEFT);
	}
	{// create status and errors area for parse output
	    errors = new TextArea("", ERRROWS, 1,
		    TextArea.SCROLLBARS_VERTICAL_ONLY);
	    errors.setBackground(Color.white);
	    errors.setForeground(Color.black);
	    errors.setEditable(false);
	    errors.setFont(new Font("Monospaced", Font.PLAIN, 12));

	    errlabel = new Label(ERRLABEL, Label.LEFT);
	}
	{// create reset button
	    resetdb = new Button(RESET);
	    resetdb.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    gConsultPanel.this.resetdb();
		}
	    });
	    resetdb.setBackground(Color.white);
	    resetdb.setForeground(Color.black);
	}
	{// create consult button
	    consult = new Button(CONSULT);
	    consult.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    gConsultPanel.this.consult();
		}
	    });
	    consult.setBackground(Color.white);
	    consult.setForeground(Color.black);
	}
	{// create load press button
	    loadPress = new Button("Load Press");
	    loadPress.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    gConsultPanel.this.loadPress();
		}
	    });
	    loadPress.setBackground(Color.white);
	    loadPress.setForeground(Color.black);
	}
	{// create find button and field
	    find = new Button(FIND);
	    find.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    gConsultPanel.this.find();
		}
	    });
	    find.setBackground(Color.white);
	    find.setForeground(Color.black);

	    find_field = new TextField("");
	    find_field.setFont(new Font("SansSerif", Font.PLAIN, 12));

	    find_field.addKeyListener(new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
		    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			gConsultPanel.this.find();
		    }
		}
	    });
	}
	{// create goto button
	    gotoline = new Button(GOTOLINE);
	    gotoline.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    gConsultPanel.this.goLine();
		}
	    });
	    gotoline.setBackground(Color.white);
	    gotoline.setForeground(Color.black);

	    gotoline_field = new TextField("");
	    gotoline_field.setFont(new Font("Monospaced", Font.PLAIN, 12));

	    gotoline_field.addFocusListener(new FocusAdapter() {
		public void focusGained(FocusEvent e) {
		    gotoline_field.setText(Integer
			    .toString(getCurrentSourceLine()));
		    gotoline_field.selectAll();
		}
	    });
	    gotoline_field.addKeyListener(new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
		    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			gConsultPanel.this.goLine();
		    }
		}
	    });
	}

	{
	    Panel button_panel, top_buttons, bottom_buttons, inner_panel;
	    Panel src_panel, err_panel, find_panel, goto_panel, middle_panel, right_panel;
	    Panel middle_buttons1, middle_buttons2;

	    setLayout(new BorderLayout());
	    setFont(new Font("SansSerif", Font.PLAIN, 12));
	    setBackground(Color.lightGray);
	    setForeground(Color.black);

	    add(new Panel(), BorderLayout.NORTH);
	    add(new Panel(), BorderLayout.WEST);
	    add(new Panel(), BorderLayout.EAST);
	    add(new Panel(), BorderLayout.SOUTH);

	    src_panel = new Panel(new BorderLayout());
	    err_panel = new Panel(new BorderLayout());
	    top_buttons = new Panel(new GridLayout(0, 1));
	    bottom_buttons = new Panel(new GridLayout(0, 1));
	    middle_buttons1 = new Panel(new BorderLayout());
	    middle_buttons2 = new Panel(new BorderLayout());
	    middle_panel = new Panel(new BorderLayout());
	    button_panel = new Panel(new BorderLayout());
	    right_panel = new Panel(new BorderLayout());
	    inner_panel = new Panel(new BorderLayout());
	    find_panel = new Panel(new BorderLayout());
	    goto_panel = new Panel(new BorderLayout());

	    top_buttons.add(resetdb);
	    bottom_buttons.add(consult);
	    bottom_buttons.add(loadPress);

	    find_panel.add(new Panel(), BorderLayout.NORTH);
	    find_panel.add(find_field, BorderLayout.CENTER);
	    find_panel.add(find, BorderLayout.SOUTH);

	    goto_panel.add(new Panel(), BorderLayout.NORTH);
	    goto_panel.add(gotoline_field, BorderLayout.CENTER);
	    goto_panel.add(gotoline, BorderLayout.SOUTH);

	    middle_buttons1.add(new Panel(), BorderLayout.CENTER);
	    middle_buttons1.add(find_panel, BorderLayout.SOUTH);
	    middle_buttons2.add(middle_buttons1, BorderLayout.CENTER);
	    middle_buttons2.add(goto_panel, BorderLayout.SOUTH);

	    middle_panel.add(middle_buttons2, BorderLayout.CENTER);
	    middle_panel.add(new Panel(), BorderLayout.NORTH);
	    middle_panel.add(new Panel(), BorderLayout.SOUTH);

	    button_panel.add(top_buttons, BorderLayout.NORTH);
	    button_panel.add(middle_panel, BorderLayout.CENTER);
	    button_panel.add(bottom_buttons, BorderLayout.SOUTH);

	    right_panel.add(button_panel, BorderLayout.CENTER);
	    right_panel.add(new Panel(), BorderLayout.WEST);

	    src_panel.add(source, BorderLayout.CENTER);

	    if (show_buttons)
		src_panel.add(right_panel, BorderLayout.EAST);

	    src_panel.add(srclabel, BorderLayout.NORTH);

	    err_panel.add(errors, BorderLayout.CENTER);
	    err_panel.add(errlabel, BorderLayout.NORTH);
	    err_panel.add(new Panel(), BorderLayout.SOUTH);

	    inner_panel.add(src_panel, BorderLayout.CENTER);
	    inner_panel.add(err_panel, BorderLayout.SOUTH);

	    add(inner_panel, BorderLayout.CENTER);
	}
    };

    public iPrologServiceText getSource() {
	return new gPrologServiceTextArea(source);
    };

    public TextArea getSourceTextArea() {
	return source;
    };

    public int getCurrentSourceLine() {
	String source_str = source.getText();
	int source_pos = source.getCaretPosition();
	int pos = 0;
	int line = 1;

	do {
	    pos = source_str.indexOf("\n", pos);

	    if (pos >= source_pos || pos < 0)
		return line;

	    if (pos >= 0)
		pos++;

	    line++;
	} while (pos >= 0);

	return 0;
    };

    public void setCurrentSourceLine(int line) {
	String source_str = source.getText();
	int start_pos = 0;
	int pos = 0;
	int current_line = 1;

	do {
	    pos = source_str.indexOf("\n", start_pos);

	    if (current_line == line) {
		source.select(start_pos, (pos >= 0 ? pos : source_str.length()));
		return;
	    }

	    start_pos = pos + 1;
	    current_line++;
	} while (pos >= 0 && current_line <= line);

	source.setCaretPosition(source_str.length());
    };

    public String getFindString() {
	return find_str;
    };

    public boolean findSource(String substr) {
	find_str = substr;
	find_pos = (source.getSelectionStart() != source.getSelectionEnd() ? source
		.getSelectionEnd() : source.getCaretPosition());

	return findNextSource();
    };

    public boolean findNextSource() {
	String source_str = source.getText();

	if (source.getSelectionEnd() != find_pos)
	    find_pos = source.getCaretPosition();

	if (find_str != null) {
	    if (find_pos >= 0)
		find_pos = source_str.indexOf(find_str, find_pos);

	    if (find_pos < 0) {
		find_pos = 0;
		find_pos = source_str.indexOf(find_str, find_pos);
	    }

	    if (find_pos >= 0) {
		int start_pos = find_pos;

		find_pos += find_str.length();

		source.select(start_pos, find_pos);

		return true;
	    } else
		Toolkit.getDefaultToolkit().beep();
	}
	return false;
    };

    public PrintWriter getErrorsStream() {
	return new PrintWriter(new gOutputStreamTextArea(errors));
    };

    public void resetdb() {
	errors.setText("");
	consult.setEnabled(false);
	resetdb.setEnabled(false);

	if (!prolog.start(new jResetDatabaseThread(prolog, getErrorsStream())))
	    errors.append("reset failed. other events pending.\n");
    };

    public void pre_consult() {
	errors.setText("");
	source.setEditable(false);
	consult.setEnabled(false);
	resetdb.setEnabled(false);
    };

    public void consult() {
	pre_consult();

	if (!prolog.start(new jConsultSourceThread(prolog, getSource(),
		getErrorsStream())))
	    errors.append("consult failed. other events pending.\n");
    };
    
    public void loadPress() {
    	
		errors.setText("");
    	//todo:tk:test method for loading PRESS.
    	BufferedReader file = null;
    	try {
    		file = new BufferedReader(new FileReader("/home/tkosan/git/press/load.txt"));
    		String line;
    		while ((line = file.readLine()) != null && (!line.trim().equals(""))) {
    			
    			if(line.startsWith("%"))
    			{
    				continue;
    			}

    			errors.append(line + "\n");
    			String source = new Scanner(new File(line) ).useDelimiter("\\A").next();
    			

    			if (!prolog.start(new jConsultSourceThread(prolog, new StringBufferText(new StringBuilder(source)), getErrorsStream())))
    			{
    				errors.append("consult failed. other events pending.\n");
    			}
    			
    			while(! prolog.isAvailable())
    			{
    				try
    				{
    					Thread.sleep(100);
    				}catch(InterruptedException ie){
    				}
    			}
    			
    			errors.append("\n\n");
    			
    			errors.setCaretPosition(errors.getText().length());


    		}




    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	finally
    	{
    		if(file != null)
    		{
    			try {
    				file.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}

    };
    
    private class StringBufferText implements iPrologServiceText
    { //todo:tk:hack to allow source code to be read by JLog from a file.
    	private StringBuilder stringBuffer;
    	
    	public StringBufferText(StringBuilder sb)
    	{
    		this.stringBuffer = sb;
    	}
    	
        public String getText(){
        	return stringBuffer.toString();
        };
        

        public void setText(String t){};

        public void append(String a){};

        public void insert(String i, int p){};

        public void remove(int s, int e){};

        public void setCaretPosition(int i){};

        public void select(int s, int e){};

        public void selectAll(){};

        public void requestFocus(){};
    }

    public void find() {
	findSource(find_field.getText());
    };

    public void goLine() {
	try {
	    setCurrentSourceLine(Integer.parseInt(gotoline_field.getText()));
	    gotoline_field.setText(Integer.toString(getCurrentSourceLine()));
	    gotoline_field.selectAll();
	} catch (NumberFormatException ex) {
	    gotoline_field.setText(Integer.toString(getCurrentSourceLine()));
	    gotoline_field.selectAll();
	    Toolkit.getDefaultToolkit().beep();
	}
    };

    protected void reset_buttons() {
	consult.setEnabled(true);
	resetdb.setEnabled(true);
	source.setEditable(true);
    };

    protected void disable_buttons() {
	consult.setEnabled(false);
	resetdb.setEnabled(false);
    };
};