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
//	JLogAppletBase
//#########################################################################

package ubc.cs.JLog.Applet;

import java.awt.*;
import java.net.*;
import java.io.*;
import java.applet.Applet;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Parser.*;

/**
 * This is the abstract Applet class for the Prolog in Java environment. It
 * includes <code>jPrologServices</code>, but does not specify the user
 * interface. It is designed as a super class for any applets which need the
 * <code>jPrologServices</code>. Derivative authors should inherit from this
 * class to preserve the <code>getAppletInfo()</code> credits.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
abstract public class gJLogAppletBase extends Applet implements
	iJLogApplBaseServices {
    protected gJLogBase base;

    public gJLogAppletBase() {
	base = new gJLogBase(this);
    };

    public void init() {
	super.init();

	base.init();
    };

    public void start() {
	base.start();
    };

    public void stop() {
	base.stop();
    };

    public void destroy() {
	base.destroy();
    };

    /**
     * This function returns the credit assignment and copyright informaiton
     * string. It must be preserved by authors of derivative works. All applets
     * should be derived from the <code>gJLogAppletBase</code> class, and if
     * they provide their own <code>getAppletInfo()</code> function it should
     * invoke this version first. For example:
     * <code>return super.getAppletInfo() + "derivative information";</code>
     * 
     * @return The credit and information string.
     */
    public String getAppletInfo() {
	return base.getRequiredCreditInfo();
    };

    public String[][] getParameterInfo() {
	return base.getParameterInfo();
    };

    public String getRequiredCreditInfo() {
	return base.getRequiredCreditInfo();
    };

    public String getSource() throws IOException {
	return base.getSource();
    }

    public URL getURLFromFilename(String name) throws MalformedURLException,
	    IOException {
	URL dbase = getDocumentBase();
	URL file = new URL(dbase, name);

	return file;
    };

    public InputStream getInputStreamFromFilename(String name)
	    throws MalformedURLException, IOException {
	URL dbase = getDocumentBase();
	URL file = new URL(dbase, name);

	return file.openStream();
    };

    public URL getResourceURLFromFilename(String name)
	    throws MalformedURLException, IOException {
	return jPrologFileServices.getResourceURLFromFilename_S(name);
    };

    public InputStream getResourceInputStreamFromFilename(String name)
	    throws MalformedURLException, IOException {
	return jPrologFileServices.getResourceInputStreamFromFilename_S(name);
    };

    public String getTextFromInputStream(InputStream in_strm)
	    throws IOException {
	return base.getTextFromInputStream(in_strm);
    };

    public String getParameter(String name) {
	return convertStringLinebreaks(super.getParameter(name));
    };

    public String convertStringLinebreaks(String param) {
	return base.convertStringLinebreaks(param);
    };

    public Image getImage(String name) {
	return getImage(getDocumentBase(), name);
    };

    public jPrologServices getPrologServices() {
	return base.getPrologServices();
    };
};
