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
//	jPrologServiceThread
//#########################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.util.*;

/**
 * This class provides an independant thread of execution for services using a
 * <code>jPrologService</code> Prolog proof engine.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public class jPrologServiceThread extends Thread {
    protected jPrologServices prolog;
    protected jPrologServiceBroadcaster stopped = null;

    protected boolean allow_release;

    public jPrologServiceThread(jPrologServices ps) {
	prolog = ps;
	allow_release = true;
    };

    public jPrologServices getPrologServices() {
	return prolog;
    };

    public void setStoppedListeners(jPrologServiceBroadcaster s) {
	stopped = s;
    };

    // this is a bit of a hack (but stop() is final method). there is no
    // guarentee that
    // other broadcasted messages (such as end messages) from the run method
    // have already
    // been sent.
    public void broadcasted_stop() {
	if (stopped != null)
	    stopped.broadcastEvent(new jThreadStoppedEvent(this));

	stop();
    };

    public boolean isCurrentlyConsulting() {
	return false;
    };

    /**
     * Set the thread release state upon completion. By default the thread may
     * release itself from <code>jPrologServices</code> when it no longer needs
     * to use its services. If this thread is being used in sequence (i.e., is
     * being invoked by another worker thread) then it should not initiate
     * release, because the next thread may need Prolog Services.
     * 
     * @param a
     *            true to allow thread release, false to disallow it.
     */
    public void setAllowRelease(boolean a) {
	allow_release = a;
    };

    public void printOutput(String s) {
	prolog.printOutput(s);
    };
};
