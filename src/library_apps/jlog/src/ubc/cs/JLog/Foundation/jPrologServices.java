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
//	jPrologServices
//#########################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.zip.*;
import java.net.*;
import java.io.*;
import ubc.cs.JLog.Parser.*;
import ubc.cs.JLog.Terms.*;

/**
 * This class is the near complete environment for a Prolog environment. It
 * contains the rules database, a registery of predicates and operators needed
 * for parsing Prolog code, a connection to a graphical display environment,
 * connections to text input and output consoles, several broadcasters to notify
 * observers of several possible events, and some debugging attributes. It does
 * not, however, contain the prover itself.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public class jPrologServices {
    static final String LIB_INIT_TOC_PREFIX = "INIT_";
    static final String LIB_INIT_TOC_POSTFIX = "LIB.TOC";
    static final String LIB_POSTFIX_STR = "Lib.jar";
    static final String BUILTINS_LIB = "builtins";

    protected jPrologServiceThread thread = null;
    protected jKnowledgeBase database;
    protected pPredicateRegistry predicates;
    protected pOperatorRegistry operators;
    protected Random rand;

    protected jPrologServiceBroadcaster beginQuery, retryQuery, endQuery;
    protected jPrologServiceBroadcaster beginConsult, endConsult;
    protected jPrologServiceBroadcaster threadStopped;
    protected jPrologServiceBroadcaster debugMessages;
    protected jPrologServiceBroadcaster stateChanged;

    protected PrintWriter default_output = null;
    protected PrintWriter current_output = null;
    protected BufferedReader default_input = null;
    protected BufferedReader current_input = null;

    // external services
    protected iPrologFileServices fileservices = null;
    protected Object animation = null;

    protected boolean debugging = false;
    protected boolean fail_unknown_predicate = false;

    /**
     * This function returns the credit assignment and copyright informaiton
     * string. It must be preserved by authors of derivative works.
     * 
     * @return The credit and information string.
     */
    public static String getRequiredCreditInfo() {
	return "JLog v1.3.8  Prolog in Java.\n\n"
		+ "Created by Glendon Holst\n"
		+ " for Alan Mackworth and \"Computational Intelligence: A Logical Approach\" text.\n\n"
		+ "Copyright 1998, 2000, 2002, 2004, 2005, 2007, 2008 by:\n"
		+ " University of British Columbia, Alan Mackworth and Glendon Holst\n\n"
		+ "Released under the GNU GPL (General Public License) and/or MPL 1.1 (Mozilla Public License):\n"
		+ " JLog is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;\n"
		+ " without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.\n"
		+ " See the GNU General Public License for more details: <http://www.fsf.org> or <http://www.gnu.org>.\n"
		+ " See the Mozilla Public License for more details: <http://http://http://www.mozilla.org/MPL/>.\n\n"
		+ " Source code available from: <http://jlogic.sourceforge.net/> or <http://sourceforge.net/projects/jlogic>\n\n"
		+ "This information string must remain, in its entirety, in all distributions of this work (derivative or otherwise).\n"
		+ " Authors of derivative works may note their involvement by appending their information below:\n\n"
		+ "---------------------------------------------\n\n";
    };

    public jPrologServices(jKnowledgeBase kb, pPredicateRegistry pr,
	    pOperatorRegistry or) {
	database = kb;
	predicates = pr;
	operators = or;

	beginQuery = new jPrologServiceBroadcaster();
	retryQuery = new jPrologServiceBroadcaster();
	endQuery = new jPrologServiceBroadcaster();
	beginConsult = new jPrologServiceBroadcaster();
	endConsult = new jPrologServiceBroadcaster();
	threadStopped = new jPrologServiceBroadcaster();
	debugMessages = new jPrologServiceBroadcaster();
	stateChanged = new jPrologServiceBroadcaster();

	rand = new Random();

	// register Terms
	{
	    jPredefined pd = new jPredefinedTerms(this);

	    pd.register();
	}
    };

    public synchronized void addBeginQueryListener(jPrologServiceListener l) {
	beginQuery.addListener(l);
    };

    public synchronized void addRetryQueryListener(jPrologServiceListener l) {
	retryQuery.addListener(l);
    };

    public synchronized void addEndQueryListener(jPrologServiceListener l) {
	endQuery.addListener(l);
    };

    public synchronized void addBeginConsultListener(jPrologServiceListener l) {
	beginConsult.addListener(l);
    };

    public synchronized void addEndConsultListener(jPrologServiceListener l) {
	endConsult.addListener(l);
    };

    public synchronized void removeBeginQueryListener(jPrologServiceListener l) {
	beginQuery.removeListener(l);
    };

    public synchronized void removeRetryQueryListener(jPrologServiceListener l) {
	retryQuery.removeListener(l);
    };

    public synchronized void removeEndQueryListener(jPrologServiceListener l) {
	endQuery.removeListener(l);
    };

    public synchronized void removeBeginConsultListener(jPrologServiceListener l) {
	beginConsult.removeListener(l);
    };

    public synchronized void removeEndConsultListener(jPrologServiceListener l) {
	endConsult.removeListener(l);
    };

    public synchronized void addThreadStoppedListener(jPrologServiceListener l) {
	threadStopped.addListener(l);
    };

    public synchronized void removeThreadStoppedListener(
	    jPrologServiceListener l) {
	threadStopped.removeListener(l);
    };

    public synchronized void addDebugMessagesListener(jPrologServiceListener l) {
	debugMessages.addListener(l);
    };

    public synchronized void removeDebugMessagesListener(
	    jPrologServiceListener l) {
	debugMessages.removeListener(l);
    };

    public synchronized void addStateChangedListener(jPrologServiceListener l) {
	stateChanged.addListener(l);
    };

    public synchronized void removeStateChangedListener(jPrologServiceListener l) {
	stateChanged.removeListener(l);
    };

    /**
     * Returns whether the prolog services are busy or not.
     * 
     * @return true if there is not already a busy thread, false otherwise.
     */
    public synchronized boolean isAvailable() {
	return (thread == null || !thread.isAlive());
    };

    /**
     * Starts a prolog services of a given type.
     * 
     * @param t
     *            The thread which will use the prolog services.
     * @return true if the thread is accepted.
     */
    public synchronized boolean start(jPrologServiceThread t) {
	if (isAvailable()) {
	    resetOutput();
	    resetInput();

	    thread = t;

	    if (thread instanceof jUserQueryThread)
		((jUserQueryThread) thread).setListeners(beginQuery,
			retryQuery, endQuery, threadStopped, debugMessages);
	    else if (thread instanceof jAPIQueryThread)
		((jAPIQueryThread) thread).setListeners(beginQuery, retryQuery,
			endQuery, threadStopped, debugMessages);
	    else if (thread instanceof jAPIConsultThread)
		((jAPIConsultThread) thread).setListeners(beginConsult,
			endConsult, threadStopped);
	    else if (thread instanceof jConsultSourceThread)
		((jConsultSourceThread) thread).setListeners(beginConsult,
			endConsult, threadStopped);
	    else if (thread instanceof jResetDatabaseThread)
		((jResetDatabaseThread) thread).setListeners(beginConsult,
			endConsult, threadStopped);
	    else if (thread instanceof jConsultAndQueryThread)
		((jConsultAndQueryThread) thread).setListeners(beginConsult,
			endConsult, beginQuery, retryQuery, endQuery,
			debugMessages, threadStopped);

	    thread.start();
	    return true;
	}
	return false;
    };

    public synchronized void start() {
	resetKnowledgeBase();
	resetOutput();
	resetInput();
    };

    /**
     * Forcefully terminates the current user thread. The thread may broadcast a
     * 'stopped' message.
     */
    public synchronized void stop() {
	if (thread != null && thread.isAlive())
	    thread.broadcasted_stop();
	else
	    thread = null;
    };

    /**
     * Releases the current thread as a user of <code>jPrologServices</code>.
     * This should be done only by the threads themselves, after they have
     * finished using the services provided by this instance.
     */
    public synchronized void release() {
	thread = null;
    };

    public synchronized void suspend() {
	if (thread != null && thread.isAlive())
	    thread.suspend();
	else
	    thread = null;
    };

    public synchronized void resume() {
	if (thread != null && thread.isAlive())
	    thread.resume();
	else
	    thread = null;
    };

    public void resetKnowledgeBase() {
	database.clearRules();
	predicates.clearPredicates();
	operators.clearOperators();

	// register Terms
	{
	    jPredefined pd = new jPredefinedTerms(this);

	    pd.register();
	}

	// register Builtins
	{
	    try {
		loadLibrary(BUILTINS_LIB);
	    } catch (IOException e) {
		throw new LoadLibraryException(
			"IO Error loading builtins library");
	    }
	}
    };

    public jKnowledgeBase getKnowledgeBase() {
	return database;
    };

    public pPredicateRegistry getPredicateRegistry() {
	return predicates;
    };

    public pOperatorRegistry getOperatorRegistry() {
	return operators;
    };

    public void setAnimationEnvironment(Object ae) {
	animation = ae;
    };

    public Object getAnimationEnvironment() {
	return animation;
    };

    public void loadLibrary(String lib) throws IOException {
	String tocname;
	iPrologFileServices pfs = getFileServices();

	if (pfs == null)
	    throw new LoadLibraryException("Missing FileServices");

	tocname = LIB_INIT_TOC_PREFIX + lib.toUpperCase()
		+ LIB_INIT_TOC_POSTFIX;

	// loads the INIT_libnameLIB.TOC file as a resource
	try {
	    InputStream toc_is;

	    toc_is = pfs.getResourceInputStreamFromFilename(tocname);

	    loadLibraryFromTOC(lib, toc_is);

	    return; // if load suceeds, we are done.
	} catch (IOException e) {
	    e.printStackTrace();// load resource method failed, catch exception
				// to try again
	}

	// this version loads the TOC from the given library file
	// The library file must exist in the current directory
	{
	    String libname = lib + LIB_POSTFIX_STR;
	    ZipInputStream lib_is;
	    ZipEntry zentry;

	    lib_is = new ZipInputStream(pfs.getInputStreamFromFilename(libname));

	    while ((zentry = lib_is.getNextEntry()) != null) {
		if (zentry.getName().equals(tocname)) {
		    loadLibraryFromTOC(lib, lib_is);
		    break;
		}
	    }
	}
    };

    protected void loadLibraryFromTOC(String lib, InputStream toc_is)
	    throws IOException {
	BufferedReader toc_read = new BufferedReader(new InputStreamReader(
		toc_is));
	StreamTokenizer tokenizer = new StreamTokenizer(toc_read);
	int token;

	tokenizer.commentChar('#');
	tokenizer.quoteChar('\"');
	tokenizer.ordinaryChar(':');
	tokenizer.wordChars('_', '_');
	tokenizer.lowerCaseMode(false);
	tokenizer.eolIsSignificant(true);

	while ((token = tokenizer.nextToken()) != StreamTokenizer.TT_EOF) {
	    if (token == StreamTokenizer.TT_WORD
		    && tokenizer.sval.equalsIgnoreCase("LoadClass")) {
		loadLibraryTOCParseLoadClass(lib, tokenizer);
	    } else if (token == StreamTokenizer.TT_WORD
		    && tokenizer.sval
			    .equalsIgnoreCase("RegisterGenericPredicateEntry")) {
		loadLibraryTOCParseGenericPredicate(lib, tokenizer);
	    } else if (token == StreamTokenizer.TT_WORD
		    && tokenizer.sval
			    .equalsIgnoreCase("RegisterGenericOperatorEntry")) {
		loadLibraryTOCParseGenericOperator(lib, tokenizer);
	    } else if (token == StreamTokenizer.TT_EOL)
		tokenizer.pushBack(); // empty time

	    // ignore remaining tokens until EOL or EOF
	    do {
		if ((token = tokenizer.nextToken()) == StreamTokenizer.TT_EOF)
		    tokenizer.pushBack();
	    } while (token != StreamTokenizer.TT_EOF
		    && token != StreamTokenizer.TT_EOL);
	}
    };

    protected void loadLibraryTOCParseLoadClass(String lib,
	    StreamTokenizer tokenizer) throws IOException {
	int token;

	if ((token = tokenizer.nextToken()) != ':')
	    throw new LoadLibraryException(
		    "Expected ':' separator in TOC at line: "
			    + Integer.toString(tokenizer.lineno()));

	if ((token = tokenizer.nextToken()) == StreamTokenizer.TT_WORD) {
	    loadClass(lib, tokenizer.sval);
	} else
	    throw new LoadLibraryException(
		    "Expected Class Name in TOC at line: "
			    + Integer.toString(tokenizer.lineno()));
    };

    protected void loadLibraryTOCParseGenericPredicate(String lib,
	    StreamTokenizer tokenizer) throws IOException {
	int token;
	String name, classname;
	int arity;

	if ((token = tokenizer.nextToken()) != ':')
	    throw new LoadLibraryException(
		    "Expected ':' separator in TOC at line: "
			    + Integer.toString(tokenizer.lineno()));

	if ((token = tokenizer.nextToken()) == '\"') {
	    name = tokenizer.sval;
	} else if (token == StreamTokenizer.TT_WORD) {
	    name = tokenizer.sval;
	} else
	    throw new LoadLibraryException(
		    "Expected Name string in TOC at line: "
			    + Integer.toString(tokenizer.lineno()));

	if ((token = tokenizer.nextToken()) == StreamTokenizer.TT_NUMBER) {
	    arity = (int) Math.round(tokenizer.nval);
	} else
	    throw new LoadLibraryException(
		    "Expected Arity integer in TOC at line: "
			    + Integer.toString(tokenizer.lineno()));

	if ((token = tokenizer.nextToken()) == StreamTokenizer.TT_WORD) {
	    pGenericPredicateEntry gpe;

	    classname = tokenizer.sval;

	    gpe = new pGenericPredicateEntry(name, arity, loadClass(lib,
		    classname));
	    registerPredicateOperatorEntryInstance(lib, gpe);
	} else
	    throw new LoadLibraryException(
		    "Expected Class name in TOC at line: "
			    + Integer.toString(tokenizer.lineno()));
    };

    protected void loadLibraryTOCParseGenericOperator(String lib,
	    StreamTokenizer tokenizer) throws IOException {
	int token;
	String name, classname;
	int type, priority;
	boolean atoms;

	if ((token = tokenizer.nextToken()) != ':')
	    throw new LoadLibraryException(
		    "Expected ':' separator in TOC at line: "
			    + Integer.toString(tokenizer.lineno()));

	if ((token = tokenizer.nextToken()) == '\"') {
	    name = tokenizer.sval;
	} else if (token == StreamTokenizer.TT_WORD) {
	    name = tokenizer.sval;
	} else
	    throw new LoadLibraryException(
		    "Expected Name string in TOC at line: "
			    + Integer.toString(tokenizer.lineno()));

	if ((token = tokenizer.nextToken()) == StreamTokenizer.TT_WORD) {
	    if (tokenizer.sval.equalsIgnoreCase("FX"))
		type = pOperatorEntry.FX;
	    else if (tokenizer.sval.equalsIgnoreCase("FY"))
		type = pOperatorEntry.FY;
	    else if (tokenizer.sval.equalsIgnoreCase("XFX"))
		type = pOperatorEntry.XFX;
	    else if (tokenizer.sval.equalsIgnoreCase("XFY"))
		type = pOperatorEntry.XFY;
	    else if (tokenizer.sval.equalsIgnoreCase("YFX"))
		type = pOperatorEntry.YFX;
	    else if (tokenizer.sval.equalsIgnoreCase("XF"))
		type = pOperatorEntry.XF;
	    else if (tokenizer.sval.equalsIgnoreCase("YF"))
		type = pOperatorEntry.YF;
	    else
		throw new LoadLibraryException(
			"Expected Type (FX,FY,XFX,XFY,YFX,XF,YF) in TOC at line: "
				+ Integer.toString(tokenizer.lineno()));
	} else
	    throw new LoadLibraryException(
		    "Expected Type (FX,FY,XFX,XFY,YFX,XF,YF) in TOC at line: "
			    + Integer.toString(tokenizer.lineno()));

	if ((token = tokenizer.nextToken()) == StreamTokenizer.TT_NUMBER) {
	    priority = (int) Math.round(tokenizer.nval);
	} else
	    throw new LoadLibraryException(
		    "Expected Priority integer in TOC at line: "
			    + Integer.toString(tokenizer.lineno()));

	if ((token = tokenizer.nextToken()) == StreamTokenizer.TT_WORD) {
	    if (tokenizer.sval.equalsIgnoreCase("TRUE"))
		atoms = true;
	    else if (tokenizer.sval.equalsIgnoreCase("FALSE"))
		atoms = false;
	    else
		throw new LoadLibraryException(
			"Expected Allow Atoms boolean (TRUE,FALSE) in TOC at line: "
				+ Integer.toString(tokenizer.lineno()));
	} else
	    throw new LoadLibraryException(
		    "Expected Allow Atoms boolean (TRUE,FALSE) in TOC at line: "
			    + Integer.toString(tokenizer.lineno()));

	if ((token = tokenizer.nextToken()) == StreamTokenizer.TT_WORD) {
	    pGenericOperatorEntry goe;

	    classname = tokenizer.sval;

	    goe = new pGenericOperatorEntry(name, type, priority, atoms,
		    loadClass(lib, classname));
	    registerPredicateOperatorEntryInstance(lib, goe);
	} else
	    throw new LoadLibraryException(
		    "Expected Class name in TOC at line: "
			    + Integer.toString(tokenizer.lineno()));
    };

    public Class loadClass(String lib, String classname) {
	iPrologFileServices pfs = getFileServices();
	Class obj_class = null;
	Object obj_inst = null;

	// try default class loader, suitable for applets and applications
	try {
	    obj_class = Class.forName(classname);
	} catch (Exception e) {
	}

	// try again using another mechanism, suitable for applications and JVM
	// version >= 1.2
	/*
	 * if (obj_class == null) try {URL curl =
	 * pfs.getURLFromFilename(lib+LIB_POSTFIX_STR); URLClassLoader cloader =
	 * new URLClassLoader(new URL[] {curl});
	 * 
	 * obj_class = cloader.loadClass(classname); } catch (Exception e) { }
	 */

	// try again using another mechanism, suitable for applications and JVM
	// version >= 1.1
	// this version dynamically loads the URLClassLoader.
	// Replace with version above when JLog requires JVM version >= 1.2
	if (obj_class == null)
	    try {
		URL curl = pfs.getURLFromFilename(lib + LIB_POSTFIX_STR);
		Class cload_class = Class.forName("java.net.URLClassLoader");
		Constructor cload_cons = null;
		ClassLoader cloader = null;

		cload_cons = cload_class
			.getConstructor(new Class[] { URL[].class });
		cloader = (ClassLoader) cload_cons
			.newInstance(new Object[] { new URL[] { curl } });

		obj_class = cloader.loadClass(classname);
	    } catch (Exception e) {
	    }

	// all attempts to load class failed... giving up.
	if (obj_class == null)
	    throw new LoadLibraryException("Failed to load class: " + classname);

	// some classes require an instance registered
	try {
	    if (pPredicateEntry.class.isAssignableFrom(obj_class)
		    || pOperatorEntry.class.isAssignableFrom(obj_class)) {
		obj_inst = obj_class.newInstance();

		registerPredicateOperatorEntryInstance(lib, obj_inst);
	    } else if (jPredefined.class.isAssignableFrom(obj_class)) {
		Constructor obj_cons;

		obj_cons = obj_class.getConstructor(new Class[] {
			jPrologServices.class, String.class });
		obj_inst = obj_cons.newInstance(new Object[] { this, lib });

		registerPredefinedInstance(lib, obj_inst);
	    }
	} catch (Exception e) {
	    throw new LoadLibraryException(
		    "Failed to instantiate or register class instance");
	}

	return obj_class;
    };

    protected void registerPredicateOperatorEntryInstance(String lib,
	    Object obj_inst) {
	// register instances as needed
	if (obj_inst instanceof pPredicateEntry) {
	    pPredicateEntry pentry = (pPredicateEntry) obj_inst;
	    pPredicateRegistry preg = getPredicateRegistry();
	    pPredicateEntry pmatch;

	    pmatch = preg.getPredicate(pentry.getName(), pentry.getArity());

	    if (pmatch == null) {
		pentry.setLibrary(lib);
		preg.addPredicate(pentry);
	    } else if (pmatch.getLibrary() != null
		    && !pmatch.getLibrary().equals(lib)) {
		String err;

		err = "Predicate " + pmatch.getName() + "/" + pmatch.getArity()
			+ " already exists in library " + pmatch.getLibrary();

		throw new LoadLibraryException(err);
	    }
	} else if (obj_inst instanceof pOperatorEntry) {
	    pOperatorEntry oentry = (pOperatorEntry) obj_inst;
	    pOperatorRegistry oreg = getOperatorRegistry();
	    pOperatorEntry omatch;

	    omatch = oreg.getOperator(oentry.getName(), oentry.hasLHS());

	    if (omatch == null) {
		oentry.setLibrary(lib);
		oreg.addOperator(oentry);
	    } else if (omatch.getLibrary() != null
		    && !omatch.getLibrary().equals(lib)) {
		String err;

		err = "Operator " + omatch.getName()
			+ " already exists in library " + omatch.getLibrary();

		throw new LoadLibraryException(err);
	    }
	}
    };

    protected void registerPredefinedInstance(String lib, Object obj_inst) {
	if (obj_inst instanceof jPredefined) {
	    jPredefined predef = (jPredefined) obj_inst;

	    predef.register();
	}
    };

    public void setDebugging(boolean dp) {
	boolean old_debug = debugging;

	debugging = dp;
	if (stateChanged != null && old_debug != debugging)
	    stateChanged.broadcastEvent(new jPrologServiceEvent());
    };

    public boolean getDebugging() {
	return debugging;
    };

    /**
     * Set the behaviour for unknown predicates. By default missing predicates
     * throw an exception.
     * 
     * @param fp
     *            If <code>false</code> (default), then missing predicates
     *            generate a failing exception (i.e., exception thrown), if
     *            <code>true</code> then the query for that predicate fails
     *            (i.e., no exception is thrown).
     */
    public void setFailUnknownPredicate(boolean fp) {
	boolean old_fup = fail_unknown_predicate;

	fail_unknown_predicate = fp;
	if (stateChanged != null && old_fup != fail_unknown_predicate)
	    stateChanged.broadcastEvent(new jPrologServiceEvent());
    };

    /**
     * Get the behaviour for unknown predicates.
     * 
     * @return If false, then exceptions are thrown for missing predicates,
     *         otherwise the query for that predicate fails (no exception
     *         thrown).
     */
    public boolean getFailUnknownPredicate() {
	return fail_unknown_predicate;
    };

    public Random getRandomGenerator() {
	return rand;
    };

    public void setDefaultOutput(PrintWriter o) {
	default_output = o;
	current_output = o;
    };

    public void setOutput(PrintWriter o) {
	current_output = o;
    };

    public PrintWriter getOutput() {
	return current_output;
    };

    public void resetOutput() {
	current_output = default_output;
    };

    public void printOutput(String s) {
	if (current_output != null) {
	    current_output.print(s);
	    current_output.flush();
	}
    };

    public void setDefaultInput(BufferedReader i) {
	default_input = i;
	current_input = i;
    };

    public void setInput(BufferedReader i) {
	current_input = i;
    };

    public BufferedReader getInput() {
	return current_input;
    };

    public void resetInput() {
	current_input = default_input;
    };

    /**
     * Sets the file services object. This object is used to access files
     * (remotely or locally depending upon the type of object).
     * 
     * @param fs
     *            The <code>iPrologFileServices</code> object to use.
     */
    public void setFileServices(iPrologFileServices fs) {
	fileservices = fs;
    };

    /**
     * Gets the file services object. This object is used to access files
     * (remotely or locally depending upon the type of object). Since this
     * object may change, any users of the file services object should acquire
     * it once, and hold on to it until they are finished (i.e., separate calls
     * to this method may return different file services).
     * 
     * @return The <code>iPrologFileServices</code> object.
     */
    public iPrologFileServices getFileServices() {
	return fileservices;
    };

};
