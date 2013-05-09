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
//	gConsultWindow
//##################################################################################

package ubc.cs.JLog.Applet;

import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.io.*;
import ubc.cs.JLog.Foundation.*;

public class gConsultWindow extends gWindowBase {
    protected gConsultPanel consult;

    protected String file_path = null;
    protected boolean src_changed;
    protected int events_pending; // number of non-user text changed events to
				  // ignore

    gConsultWindow(gJLogApplicationBase b, String default_source_text) {
	super(b);

	src_changed = false;
	events_pending = 1;

	{
	    jPrologServices prolog = parent.getPrologServices();

	    prolog.addBeginQueryListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {
		    updateToolMenuConsult(false);
		}
	    });
	    prolog.addEndQueryListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {
		    updateToolMenuConsult(true);
		}
	    });
	    prolog.addBeginConsultListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {
		    updateToolMenuConsult(false);
		}
	    });
	    prolog.addEndConsultListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {
		    updateToolMenuConsult(true);
		}
	    });
	    prolog.addThreadStoppedListener(new jPrologServiceListener() {
		public void handleEvent(jPrologServiceEvent e) {
		    updateToolMenuConsult(true);
		}
	    });
	}

	setLayout(new GridLayout());
	consult = new gConsultPanel(parent.getPrologServices(),
		default_source_text, true);
	add(consult);
	pack();

	setSize(650, 500);

	{
	    Point loc = getLocation();
	    int numw = parent.getKBWindowCount();
	    int offs = 24;

	    loc.move(30 + (offs * numw), 30 + (offs * numw));
	    setLocation(loc);
	}

	setTitle("JLog - Untitled");
	parent.addKBWindow(this);

	consult.getSourceTextArea().addTextListener(new TextListener() {
	    public void textValueChanged(TextEvent e) {
		if (events_pending > 0)
		    events_pending--;
		else
		    src_changed = true;
	    }
	});

	setVisible(true);
    };

    public boolean close() {
	if (canClose()) {
	    parent.removeKBWindow(this);
	    dispose();
	    return true;
	} else
	    return false;
    };

    public boolean canClose() {
	if (src_changed) {
	    switch (shouldSave()) {
	    case gClosingDialog.CANCEL:
		return false;
	    case gClosingDialog.SAVE:
		return save(file_path);
	    case gClosingDialog.SAVEAS:
		return save(null);
	    case gClosingDialog.DONTSAVE:
		return true;
	    }
	    return true;
	} else
	    return true;
    };

    protected int shouldSave() {
	gClosingDialog d = new gClosingDialog(this);

	return d.getChoiceValue();
    };

    public boolean save(String fileName) {
	File output = null;

	if (fileName == null) {
	    FileDialog fd;

	    fd = new FileDialog(this, "Save As", FileDialog.SAVE);
	    if (parent.getCurrentDirectory() == null)
		fd.setDirectory(System.getProperty("user.dir"));
	    else
		fd.setDirectory(parent.getCurrentDirectory());

	    setVisible(true);
	    fd.setVisible(true);

	    if (fd.getFile() == null)
		return false;

	    parent.setCurrentDirectory(fd.getDirectory());
	    output = new File(fd.getDirectory(), fd.getFile());
	} else {
	    output = new File(fileName);
	}

	file_path = output.getAbsolutePath();

	setTitle("JLog - saving: " + file_path);

	try {
	    BufferedWriter bw = new BufferedWriter(new FileWriter(output));
	    String source;

	    source = consult.getSource().getText();

	    bw.write(source);

	    bw.close();
	    setTitle("JLog - " + file_path);
	    src_changed = false;
	} catch (Exception ex) {
	    displayErrorDialog("Could not save " + file_path);
	    setTitle("JLog - Untitled");
	    file_path = null;
	    src_changed = true;
	    parent.updateWindowMenu();
	    return false;
	}

	parent.updateWindowMenu();
	return true;
    };

    public boolean open(File input) {
	String line_separator = System.getProperty("line.separator", "\n");

	file_path = input.getAbsolutePath();

	setTitle("JLog - opening: " + file_path);

	try {
	    StringBuffer sb = new StringBuffer((int) input.length());
	    BufferedReader br = new BufferedReader(new FileReader(input));

	    while (br.ready()) {
		String line = br.readLine();

		if (line != null)
		    sb.append(line + line_separator);
	    }

	    br.close();

	    events_pending++;
	    src_changed = false;

	    consult.getSource().setText(sb.toString());

	    setTitle("JLog - " + file_path);
	} catch (Exception ex) {
	    displayErrorDialog("Could not load " + file_path);
	    setTitle("JLog - Untitled");
	    file_path = null;
	    parent.updateWindowMenu();
	    return false;
	}

	parent.updateWindowMenu();
	return true;
    };

    public String getFilePath() {
	return file_path;
    };

    public boolean useMenuItem(MenuItem mi) {
	if (mi.getActionCommand() == parent.MENU_CLOSE) {
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    close();
		}
	    });
	    return true;
	} else if (mi.getActionCommand() == parent.MENU_SAVE) {
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    save(file_path);
		}
	    });

	    return true;
	} else if (mi.getActionCommand() == parent.MENU_SAVEAS) {
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    save(null);
		}
	    });

	    return true;
	} else if (mi.getActionCommand() == parent.MENU_CUT) {
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    Clipboard c = Toolkit.getDefaultToolkit()
			    .getSystemClipboard();
		    TextArea ta = consult.getSourceTextArea();
		    int st, en;
		    StringSelection ss;

		    st = ta.getSelectionStart();
		    en = ta.getSelectionEnd();

		    ss = new StringSelection(ta.getSelectedText());

		    consult.getSource().remove(st, en);

		    c.setContents(ss, ss);
		}
	    });

	    return true;
	} else if (mi.getActionCommand() == parent.MENU_COPY) {
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    Clipboard c = Toolkit.getDefaultToolkit()
			    .getSystemClipboard();
		    TextArea ta = consult.getSourceTextArea();
		    StringSelection ss;

		    ss = new StringSelection(ta.getSelectedText());

		    c.setContents(ss, ss);
		}
	    });

	    return true;
	} else if (mi.getActionCommand() == parent.MENU_PASTE) {
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    Clipboard c = Toolkit.getDefaultToolkit()
			    .getSystemClipboard();
		    Transferable t = c.getContents(this);

		    if (t != null) {
			try {
			    String s = (String) t
				    .getTransferData(DataFlavor.stringFlavor);
			    TextArea ta = consult.getSourceTextArea();
			    int cp = ta.getCaretPosition();

			    consult.getSource().insert(s, cp);
			} catch (UnsupportedFlavorException e1) {
			} catch (IOException e2) {
			}
		    }
		}
	    });

	    return true;
	} else if (mi.getActionCommand() == parent.MENU_SELECTALL) {
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    consult.getSource().selectAll();
		}
	    });

	    return true;
	} else if (mi.getActionCommand() == parent.MENU_FIND) {
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    gFindDialog fd;
		    String f_str;

		    fd = new gFindDialog(gConsultWindow.this, consult
			    .getFindString());
		    f_str = fd.getFindString();

		    if (f_str != null)
			consult.findSource(f_str);
		}
	    });

	    return true;
	} else if (mi.getActionCommand() == parent.MENU_FINDNEXT) {
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    consult.findNextSource();
		}
	    });

	    return true;
	} else if (mi.getActionCommand() == parent.MENU_GOTOLINE) {
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    gGotoLineDialog gld;

		    gld = new gGotoLineDialog(gConsultWindow.this, consult
			    .getCurrentSourceLine());

		    if (gld.wasAccepted())
			consult.setCurrentSourceLine(gld.getGotoLine());
		}
	    });

	    return true;
	} else if (mi.getActionCommand() == parent.MENU_CONSULTKB) {
	    mi.setEnabled(parent.getPrologServices().isAvailable());
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    consult();
		}
	    });

	    return true;
	} else if (mi.getActionCommand() == parent.MENU_RESETKB) {
	    mi.setEnabled(parent.getPrologServices().isAvailable());
	    mi.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    resetdb();
		}
	    });

	    return true;
	}

	return false;
    };

    public void consult() {
	consult.consult();
    };

    public void resetdb() {
	consult.resetdb();
    };

    protected void updateToolMenuConsult(boolean state) {
	Menu tool_menu = getMenuBar().getMenu(parent.getToolMenuID());
	int i, max = tool_menu.getItemCount();

	for (i = 0; i < max; i++) {
	    MenuItem mi = tool_menu.getItem(i);

	    if (mi.getActionCommand() == parent.MENU_CONSULTKB) {
		mi.setEnabled(state);
	    } else if (mi.getActionCommand() == parent.MENU_RESETKB) {
		mi.setEnabled(state);
	    }
	}
    };
};
