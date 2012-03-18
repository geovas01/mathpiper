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
//	gHelpPanel
//##################################################################################

package ubc.cs.JLog.Applet;

import java.lang.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Parser.*;

public class gHelpPanel extends Panel {
    protected final static String HELPFOLDER = "Help/";

    protected abstract class gHelpTopic {
	protected String topic;

	public gHelpTopic(String t) {
	    topic = t;
	};

	public String getTopic() {
	    return topic;
	};

	abstract public String getString() throws MalformedURLException,
		IOException;
    };

    protected class gHelpTopicFile extends gHelpTopic {
	protected iJLogApplBaseServices base;
	protected String file;

	public gHelpTopicFile(String t, iJLogApplBaseServices b, String f) {
	    super(t);
	    base = b;
	    file = f;
	};

	public String getString() throws MalformedURLException, IOException {
	    InputStream doc = base
		    .getInputStreamFromFilename(gHelpPanel.HELPFOLDER + file);

	    return base.getTextFromInputStream(doc);
	};
    };

    protected class gHelpTopicAbout extends gHelpTopic {
	protected iJLogApplBaseServices base;

	public gHelpTopicAbout(String t, iJLogApplBaseServices b) {
	    super(t);
	    base = b;
	};

	public String getString() throws MalformedURLException, IOException {
	    return base.getRequiredCreditInfo();
	};
    };

    protected class gHelpTopicKB extends gHelpTopic {
	protected iJLogApplBaseServices base;

	public gHelpTopicKB(String t, iJLogApplBaseServices b) {
	    super(t);
	    base = b;
	};

	public String getString() throws MalformedURLException, IOException {
	    jPrologServices ps = base.getPrologServices();
	    StringBuffer sb = new StringBuffer();

	    sb.append("KB Predicates:\n==============\n\n");
	    {
		jKnowledgeBase kb = ps.getKnowledgeBase();
		Enumeration e = kb.enumDefinitions();

		while (e.hasMoreElements()) {
		    iNameArity r = (iNameArity) e.nextElement();

		    sb.append(r.toString());
		    sb.append("\n");
		}
		sb.append("\n");
	    }

	    sb.append("Builtin Predicates:\n===================\n\n");
	    {
		pPredicateRegistry pr = ps.getPredicateRegistry();
		Enumeration e = pr.enumPredicates();

		while (e.hasMoreElements()) {
		    pPredicateEntry pe = (pPredicateEntry) e.nextElement();

		    sb.append(pe.toString());
		    sb.append("\n");
		}
		sb.append("\n");
	    }

	    sb.append("Builtin Operators:\n==================\n\n");
	    {
		pOperatorRegistry opr = ps.getOperatorRegistry();
		Enumeration e = opr.enumOperators();

		while (e.hasMoreElements()) {
		    pOperatorEntry ope = (pOperatorEntry) e.nextElement();

		    sb.append(ope.toString());
		    sb.append("\n");
		}
		sb.append("\n");
	    }

	    return sb.toString();
	};
    };

    protected final static String TOPICLABEL = "Topic";
    protected final static String HELPLABEL = "Help";

    protected TextArea help;
    protected java.awt.List topic;
    protected Label topiclabel, helplabel;
    protected Vector topics_info;

    protected iJLogApplBaseServices base;

    public gHelpPanel(iJLogApplBaseServices b) {
	base = b;

	{// create help area for instruction
	    help = new TextArea("", 0, 80, TextArea.SCROLLBARS_VERTICAL_ONLY);
	    help.setBackground(Color.white);
	    help.setForeground(Color.black);
	    help.setEditable(false);
	    help.setFont(new Font("Monospaced", Font.PLAIN, 12));

	    helplabel = new Label(HELPLABEL, Label.LEFT);
	}
	{// create list for topics
	    topic = new java.awt.List();
	    topic.setBackground(Color.white);
	    topic.setForeground(Color.black);
	    topic.setMultipleMode(false);
	    topic.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
		    if (e.getStateChange() == ItemEvent.SELECTED)
			gHelpPanel.this.help((Integer) e.getItem());
		    else
			help.setText("");
		}
	    });

	    topics_info = createHelpTopics();
	    updateHelpTopicsList();

	    topiclabel = new Label(TOPICLABEL, Label.LEFT);
	}

	{
	    Panel topic_panel, help_panel, inner_panel;

	    setLayout(new BorderLayout());
	    setFont(new Font("SansSerif", Font.PLAIN, 12));
	    setBackground(Color.lightGray);
	    setForeground(Color.black);

	    add(new Panel(), BorderLayout.NORTH);
	    add(new Panel(), BorderLayout.WEST);
	    add(new Panel(), BorderLayout.EAST);
	    add(new Panel(), BorderLayout.SOUTH);

	    topic_panel = new Panel(new BorderLayout());
	    help_panel = new Panel(new BorderLayout());
	    inner_panel = new Panel(new BorderLayout());

	    topic_panel.add(topic, BorderLayout.CENTER);
	    topic_panel.add(topiclabel, BorderLayout.NORTH);

	    help_panel.add(help, BorderLayout.CENTER);
	    help_panel.add(helplabel, BorderLayout.NORTH);

	    inner_panel.add(topic_panel, BorderLayout.WEST);
	    inner_panel.add(help_panel, BorderLayout.CENTER);
	    inner_panel.add(new Panel(), BorderLayout.SOUTH);

	    add(inner_panel, BorderLayout.CENTER);
	}
    };

    protected Vector createHelpTopics() {
	Vector v = new Vector();

	v.addElement(new gHelpTopicAbout("About JLog", base));
	v.addElement(new gHelpTopicFile("Getting Starting", base,
		"getting_started.txt"));
	v.addElement(new gHelpTopicFile("Applet Tags", base, "applet_tags.txt"));
	v.addElement(new gHelpTopicFile("Consult Facility", base, "consult.txt"));
	v.addElement(new gHelpTopicFile("Query Facility", base, "query.txt"));
	v.addElement(new gHelpTopicFile("Debug Facility", base, "debug.txt"));
	v.addElement(new gHelpTopicFile("Declarative Debug", base,
		"declarative_debug.txt"));
	v.addElement(new gHelpTopicFile("Animate Facility", base, "animate.txt"));
	v.addElement(new gHelpTopicFile("Animate Predicates", base,
		"animate_predicates.txt"));
	v.addElement(new gHelpTopicFile("Filesystem Predicates", base,
		"fs_predicates.txt"));
	v.addElement(new gHelpTopicFile("Database Predicates", base,
		"db_predicates.txt"));
	v.addElement(new gHelpTopicFile("BSF Predicates", base,
		"bsf_predicates.txt"));
	v.addElement(new gHelpTopicKB("Listing of Available Predicates", base));
	v.addElement(new gHelpTopicFile("JLog Compatability", base,
		"jlog_compatability.txt"));
	v.addElement(new gHelpTopicFile("Prolog Information", base,
		"prolog_information.txt"));

	return v;
    };

    protected void updateHelpTopicsList() {
	Enumeration e = topics_info.elements();

	topic.removeAll();

	while (e.hasMoreElements())
	    topic.add(((gHelpTopic) e.nextElement()).getTopic());

	help.setText("");
    };

    public void help(Integer I) {
	int i = I.intValue();
	gHelpTopic ht = (gHelpTopic) topics_info.elementAt(i);

	help.setText("Loading...");

	try {
	    String body_contents = ht.getString();

	    help.setText(ht.getTopic() + "\n\n");
	    help.append(body_contents);

	    help.setCaretPosition(0);
	} catch (MalformedURLException e) {
	    help.setText("Help document was not found.");
	} catch (IOException e) {
	    help.setText("Help document was not read.");
	}
    };
};