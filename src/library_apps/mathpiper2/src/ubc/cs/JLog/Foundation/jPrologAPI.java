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
// jPrologAPI
//#########################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.util.*;
import java.io.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Parser.*;

/**
 * This class is encapsulates the Prolog Engine into a single, simple class,
 * suitable for programmatic access.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public class jPrologAPI {
    protected class KeyPairs {
	public Object termkey;
	public Object objkey;

	public KeyPairs(Object tkey, Object okey) {
	    termkey = tkey;
	    objkey = okey;
	};
    };

    protected static final int QUERY_STATE_WAITING = 0;
    protected static final int QUERY_STATE_SUCCEEDED = 1;
    protected static final int QUERY_STATE_FINISHED = 2;

    protected jPrologServices prolog;
    protected jAPIQueryThread thread = null;

    protected Hashtable var_translation_keys = null;
    protected jTermTranslation translations = null;

    protected int query_state = QUERY_STATE_WAITING;

    /**
     * Basic constructor instantiates a bare-bones Prolog engine, and consults
     * the given source string. All the external facilities, except for file
     * services, that the Prolog engine may require are null. This is fine for
     * basic Prolog KBs which do not use output (e.g., write/1, writeln/1,
     * nl/0), do not use input (e.g., read/1), and do not use the animation
     * environment (e.g., animate/2). A jPrologFileServices instance provides
     * the file services.
     * 
     * @param source
     *            The source string to consult (i.e., the initial KB).
     */
    public jPrologAPI(String source) {
	prolog = new jPrologServices(new jKnowledgeBase(),
		new pPredicateRegistry(), new pOperatorRegistry());

	prolog.setFileServices(new jPrologFileServices());

	initTranslation();
	initPrologListeners();
	initConsultSource(source);
    };

    /**
     * Basic constructor instantiates a bare-bones Prolog engine, and consults
     * the given source input stream. All the external facilities, except for
     * file services, that the Prolog engine may require are null. This is fine
     * for basic Prolog KBs which do not use output (e.g., write/1, writeln/1,
     * nl/0), do not use input (e.g., read/1), and do not use the animation
     * environment (e.g., animate/2). A jPrologFileServices instance provides
     * the file services.
     * 
     * @param source
     *            The source input stream to consult (i.e., the initial KB).
     */
    public jPrologAPI(InputStream source) throws IOException {
	prolog = new jPrologServices(new jKnowledgeBase(),
		new pPredicateRegistry(), new pOperatorRegistry());

	prolog.setFileServices(new jPrologFileServices());

	initTranslation();
	initPrologListeners();
	initConsultSource(prolog.getFileServices().getTextFromInputStream(
		source));
    };

    /**
     * Complete constructor instantiates a bare-bones Prolog engine, sets the
     * external services to those provided, and consults the given source
     * string. If any external facilities are null, then the Prolog source
     * should not use any predicates which require those facilities.
     * 
     * @param source
     *            The source string to consult (i.e., the initial KB).
     * @param fs
     *            External file services facilities (e.g., used for
     *            load_library/1 etc.) If null, an instance of
     *            jPrologFileServices is used by default.
     * @param output
     *            External text ouput facilities (e.g., used for writeln/1 etc.)
     * @param input
     *            External text input facilities (e.g., used for read/1 etc.)
     * @param ae
     *            External animation display facilities (e.g., used for
     *            animation/2 etc.) Must be of type
     *            <code>aAnimationEnvironment</code>, else an exception will be
     *            thrown when proving <code>animation/2</code>.
     */
    public jPrologAPI(String source, iPrologFileServices fs,
	    PrintWriter output, BufferedReader input, Object ae) {
	prolog = new jPrologServices(new jKnowledgeBase(),
		new pPredicateRegistry(), new pOperatorRegistry());

	if (fs != null)
	    prolog.setFileServices(fs);
	else
	    prolog.setFileServices(new jPrologFileServices());

	prolog.setDefaultOutput(output);
	prolog.setDefaultInput(input);
	prolog.setAnimationEnvironment(ae);

	initTranslation();
	initPrologListeners();
	initConsultSource(source);
    };

    /**
     * Complete constructor instantiates a bare-bones Prolog engine, sets the
     * external services to those provided, and consults the given source input
     * stream. If any external facilities are null, then the Prolog source
     * should not use any predicates which require those facilities.
     * 
     * @param source
     *            The source input stream to consult (i.e., the initial KB).
     * @param fs
     *            External file services facilities (e.g., used for
     *            load_library/1 etc.) If null, an instance of
     *            jPrologFileServices is used by default.
     * @param output
     *            External text ouput facilities (e.g., used for writeln/1 etc.)
     * @param input
     *            External text input facilities (e.g., used for read/1 etc.)
     * @param ae
     *            External animation display facilities (e.g., used for
     *            animation/2 etc.) Must be of type
     *            <code>aAnimationEnvironment</code>, else an exception will be
     *            thrown when proving <code>animation/2</code>.
     */
    public jPrologAPI(InputStream source, iPrologFileServices fs,
	    PrintWriter output, BufferedReader input, Object ae)
	    throws IOException {
	prolog = new jPrologServices(new jKnowledgeBase(),
		new pPredicateRegistry(), new pOperatorRegistry());

	if (fs != null)
	    prolog.setFileServices(fs);
	else
	    prolog.setFileServices(new jPrologFileServices());

	prolog.setDefaultOutput(output);
	prolog.setDefaultInput(input);
	prolog.setAnimationEnvironment(ae);

	initTranslation();
	initPrologListeners();
	initConsultSource(prolog.getFileServices().getTextFromInputStream(
		source));
    };

    protected void initTranslation() {
	var_translation_keys = new Hashtable();

	translations = new jTermTranslation(prolog);
	translations.setDefaults();
    };

    protected void initPrologListeners() {
	prolog.addRetryQueryListener(new jPrologServiceListener() {
	    public void handleEvent(jPrologServiceEvent e) {
		if (e instanceof jUserQueryEvent) {
		    jUserQueryEvent uqe = (jUserQueryEvent) e;

		    if (uqe.getResult())
			jPrologAPI.this.setQueryResultState(true);
		}
	    }
	});
	prolog.addEndQueryListener(new jPrologServiceListener() {
	    public void handleEvent(jPrologServiceEvent e) {
		if (jPrologAPI.this.query_state == QUERY_STATE_WAITING)
		    jPrologAPI.this.setQueryResultState(false);
	    }
	});
    };

    protected void initConsultSource(String source) {
	pParseStream parser = new pParseStream(source,
		prolog.getKnowledgeBase(), prolog.getPredicateRegistry(),
		prolog.getOperatorRegistry());
	prolog.start();

	consultSource(source);
    };

    /**
     * This function returns the credit assignment and copyright informaiton
     * string. It must be preserved by authors of derivative works. If the
     * derivative work displays credit information, use this method to get the
     * JLog specific information.
     * 
     * @return The credit and information string.
     */
    public String getRequiredCreditInfo() {
	return jPrologServices.getRequiredCreditInfo();
    };

    /**
     * Set the behaviour for unknown predicates. By default the behaviour is
     * that missing predicates throw an exception.
     * 
     * @param fp
     *            If <code>fail</code> (default) then missing predicates
     *            generate a failing exception (i.e., exception thrown), if
     *            <code>false</code> then the query for that predicate fails
     *            (i.e., no exception is thrown).
     */
    public void setFailUnknownPredicate(boolean fp) {
	prolog.setFailUnknownPredicate(fp);
    };

    /**
     * Get the behaviour for unknown predicates.
     * 
     * @return If false, then exceptions are thrown for missing predicates,
     *         otherwise the query for that predicate fails (no exception
     *         thrown).
     */
    public boolean getFailUnknownPredicate() {
	return prolog.getFailUnknownPredicate();
    };

    /**
     * Associates a variable with translation keys. When the variable binding
     * hashtable is passed to the query method, the default behaviour is to
     * translate the bound values according to their type, but if a special
     * translation is preferred, then the key value is used to determine the
     * mapping. These keys are used to find the preferred iObjectToTerm or
     * iTermToObject conversion in the TermTranslation object, .
     * 
     * @param v
     *            The name of the variable.
     * @param tkey
     *            The translation lookup key to use for translating the value
     *            bound to this variable to a prolog term. Set to null to use
     *            the default translation.
     * @param okey
     *            The translation lookup key to use for translating the value
     *            bound to this variable back to a Java object. Set to null for
     *            the default translation.
     */
    public void setVariableTranslationKeys(String v, Object tkey, Object okey) {
	if (tkey == null && okey == null)
	    var_translation_keys.remove(v);
	else
	    var_translation_keys.put(v, new KeyPairs(tkey, okey));
    };

    /**
     * Get the current translation unit used to convert queries and results
     * between Prolog terms and Java objects. Modifying the conversion units
     * associated with the returned translation object will affect how the API
     * processes query input and results.
     * 
     * @return The <code>jTermTranslation</code> object used for translating
     *         between objects and terms.
     */
    public jTermTranslation getTranslation() {
	return translations;
    };

    /**
     * Sets the translation unit used to convert queries and results between
     * Prolog terms and Java objects.
     * 
     * @param tt
     *            The <code>jTermTranslation</code> object to use for
     *            translating between objects and terms.
     */
    public void setTranslation(jTermTranslation tt) {
	translations = tt;
    };

    /**
     * Consult a source string.
     * 
     * @param source
     *            The KB source string to consult.
     */
    public synchronized void consultSource(String source) {
	pParseStream parser = new pParseStream(source,
		prolog.getKnowledgeBase(), prolog.getPredicateRegistry(),
		prolog.getOperatorRegistry());

	joinForcedQueryCompletion();

	if (thread != null && thread.isAlive()) {
	    thread.broadcasted_stop();

	    try {
		thread.join();
	    } catch (InterruptedException e) {
	    }
	}

	{
	    jAPIConsultThread cthread;

	    if (!prolog.start(cthread = new jAPIConsultThread(prolog, source))) {
		throw new NoThreadAvailableException(
			"consult failed, other events pending.");
	    } else {
		try {
		    cthread.join();
		} catch (InterruptedException e) {
		}
		{
		    RuntimeException ex = cthread.getResultException();

		    if (ex != null)
			throw ex;
		}
	    }
	}
    };

    /**
     * Initiates a query.
     * 
     * @param query
     *            The query string (must be non-empty).
     * @return Returns a hashtable with all the variables in the query, and
     *         their bindings. Each key in the hashtable is a named variable,
     *         and the object type of the associated value is translated using
     *         the jTermTranslation object associated with this API object (to
     *         translate from Object to jTerm). Returns null if the query fails.
     */
    public Hashtable query(String query) {
	return query(query, null);
    };

    /**
     * Initiates a query, and stops the query after the first result.
     * 
     * @param query
     *            The query string (must be non-empty).
     * @return Returns a hashtable with all the variables in the query, just as
     *         the query method does. Returns null if the query fails.
     */
    public synchronized Hashtable queryOnce(String query) {
	Hashtable result;

	result = query(query, null);

	stop();

	return result;
    };

    /**
     * Initiates a query, pre-binding variables based on the key:value pairs in
     * the given hashtable. For each entry (Var:Value) in values, a variable
     * named Var is created and the variable instance is bound to the translated
     * jTerm of Value. See <code>jTermTranslation</code> for more information
     * about how translation between Java objects and Prolog terms is performed.
     * By default, the jPrologAPI class uses the default translation, but the
     * user may set the preferred jTermTranslation object.
     * 
     * @param query
     *            The query string (must be non-empty).
     * @param bindings
     *            A hashtable mapping variable names (key) to their values
     *            (value). If null, then performs the same as
     *            <code>query(String)</code> above.
     * @return Returns a hashtable of variable bindings (just like
     *         <code>query(String)</code> above). The values are translated
     *         using the translation object associated with this API object (to
     *         translate from jTerm to Object). Returns null if the query fails.
     */
    public synchronized Hashtable query(String query, Hashtable bindings) {
	Hashtable bind_terms = null;

	if (bindings != null) {
	    Enumeration e = bindings.keys();

	    bind_terms = new Hashtable();

	    while (e.hasMoreElements()) {
		String key = (String) e.nextElement();
		Object value = bindings.get(key);
		KeyPairs kpairs;
		jTerm term;

		if ((kpairs = (KeyPairs) var_translation_keys.get(key)) != null
			&& kpairs.termkey != null)
		    term = translations.createTermFromObject(value,
			    kpairs.termkey);
		else
		    term = translations.createTermFromObject(value);

		bind_terms.put(key, term);
	    }
	}

	joinForcedQueryCompletion();

	if (!prolog.start(thread = new jAPIQueryThread(prolog, bind_terms,
		query))) {
	    throw new NoThreadAvailableException(
		    "query failed, other events pending.");
	}

	return waitForCompletion();
    };

    /**
     * Initiates a query, pre-binding variables based on the key:value pairs in
     * the given hashtable, but stops the query after the first result.
     * 
     * @param query
     *            The query string (must be non-empty).
     * @param bindings
     *            A hashtable mapping variable names (key) to their values
     *            (value). Just as the corresponding query method.
     * @return Returns a hashtable of variable bindings (just like query method
     *         above. Returns null if the query fails.
     */
    public synchronized Hashtable queryOnce(String query, Hashtable bindings) {
	Hashtable result;

	result = query(query, bindings);

	stop();

	return result;
    };

    /**
     * Retries the previous query.
     * 
     * @return Returns a hashtable of variable bindings (just like
     *         <code>query(String)</code> above). Returns null if the query
     *         retry fails.
     */
    public synchronized Hashtable retry() {
	query_state = QUERY_STATE_WAITING;

	if (thread != null && thread.isAlive())
	    thread.retry();
	else
	    return null;

	return waitForCompletion();
    };

    public synchronized void stop() {
	if (thread != null && thread.isAlive())
	    thread.broadcasted_stop();

	query_state = QUERY_STATE_FINISHED;
    };

    protected synchronized Hashtable waitForCompletion() {
	while (query_state == QUERY_STATE_WAITING) {
	    try {
		wait();
	    } catch (InterruptedException e) {
		return null;
	    }
	}

	{
	    RuntimeException ex = thread.getResultException();

	    if (ex != null)
		throw ex;
	}
	{
	    Hashtable ht = null;

	    if (query_state == QUERY_STATE_SUCCEEDED)
		ht = thread.getResultHashtable();

	    query_state = QUERY_STATE_WAITING;

	    // translation term values to objects
	    if (ht != null) {
		Enumeration e = ht.keys();

		while (e.hasMoreElements()) {
		    String key = (String) e.nextElement();
		    jTerm value = (jTerm) ht.get(key);
		    KeyPairs kpairs;
		    Object obj;

		    if ((kpairs = (KeyPairs) var_translation_keys.get(key)) != null
			    && kpairs.objkey != null)
			obj = translations.createObjectFromTerm(value,
				kpairs.objkey);
		    else
			obj = translations.createObjectFromTerm(value);

		    ht.put(key, obj);
		}
	    }

	    return ht;
	}
    };

    protected synchronized void setQueryResultState(boolean result) {
	if (query_state == QUERY_STATE_WAITING) {
	    query_state = (result ? QUERY_STATE_SUCCEEDED
		    : QUERY_STATE_FINISHED);
	    notify();
	}
    };

    protected synchronized void joinForcedQueryCompletion() {
	if (thread != null && thread.isAlive()) {
	    query_state = QUERY_STATE_FINISHED;

	    thread.broadcasted_stop();

	    try {
		thread.join();
	    } catch (InterruptedException e) {
	    }
	}

	query_state = QUERY_STATE_WAITING;
    }
};
