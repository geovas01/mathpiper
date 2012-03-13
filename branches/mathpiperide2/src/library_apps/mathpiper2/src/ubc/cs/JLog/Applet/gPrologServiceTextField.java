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
//	PrologServiceTextField
//#########################################################################

package ubc.cs.JLog.Applet;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.iPrologServiceText;
import ubc.cs.JLog.Foundation.jPrologServiceBroadcaster;
import ubc.cs.JLog.Foundation.jPrologServiceListener;
import ubc.cs.JLog.Foundation.jPrologServiceEvent;
import java.awt.TextField;

/**
 * This class represents a TextField based text repository.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public class gPrologServiceTextField implements iPrologServiceText {
    protected TextField text;
    protected jPrologServiceBroadcaster addedText = null;
    protected jPrologServiceBroadcaster removedText = null;

    public gPrologServiceTextField(TextField t) {
	text = t;
    }

    public String getText() {
	return text.getText();
    };

    public synchronized void setText(String t) {
	text.setText(t);

	if (removedText != null)
	    removedText.broadcastEvent(new jPrologServiceEvent());
	if (addedText != null)
	    addedText.broadcastEvent(new jPrologServiceEvent());
    };

    public synchronized void append(String a) {
	text.setText(text.getText() + a);

	if (addedText != null)
	    addedText.broadcastEvent(new jPrologServiceEvent());
    };

    public synchronized void insert(String i, int p) {
	StringBuffer sbuf = new StringBuffer(text.getText());

	sbuf.insert(p, i);

	text.setText(sbuf.toString());

	if (addedText != null)
	    addedText.broadcastEvent(new jPrologServiceEvent());
    };

    public synchronized void remove(int s, int e) {
	String text_str = text.getText();

	text.setText(text_str.substring(0, s) + text_str.substring(e));

	if (removedText != null)
	    removedText.broadcastEvent(new jPrologServiceEvent());
    };

    public synchronized void setCaretPosition(int i) {
	text.setCaretPosition(i);
    };

    public synchronized void select(int s, int e) {
	text.select(s, e);
    };

    public synchronized void selectAll() {
	text.selectAll();
    };

    public void requestFocus() {
	text.requestFocus();
    };

    public synchronized void addTextAddedListener(jPrologServiceListener l) {
	if (addedText == null)
	    addedText = new jPrologServiceBroadcaster();

	addedText.addListener(l);
    };

    public synchronized void removeTextAddedListener(jPrologServiceListener l) {
	if (addedText != null)
	    addedText.removeListener(l);
    };

    public synchronized void addTextRemovedListener(jPrologServiceListener l) {
	if (removedText == null)
	    removedText = new jPrologServiceBroadcaster();

	removedText.addListener(l);
    };

    public synchronized void removeTextRemovedListener(jPrologServiceListener l) {
	if (removedText != null)
	    removedText.removeListener(l);
    };
};
