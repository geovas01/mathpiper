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
//	Rule Definitions
//   set of similar knowledge base rules organized for quick access
//#########################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Terms.Goals.*;

/**
 * This class represents an ordered collection of <code>jRule</code>s of the
 * same name and arity. This is designed for statically defined rules that
 * cannot change during a proof.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public class jRuleDefinitions implements iNameArity {
    protected Vector rules = null;
    protected String rule_name = null;
    protected int rule_arity = -1;
    protected String library = null;
    protected boolean builtin = false;

    /**
     * Constructor for use by sub-classes which set up the members themeselves.
     */
    protected jRuleDefinitions() {
    };

    public jRuleDefinitions(String name, int arity) {
	rules = new Vector();
	rule_name = name;
	rule_arity = arity;
    };

    public String getName() {
	return rule_name;
    };

    public int getArity() {
	return rule_arity;
    };

    /**
     * Determine if rules are builtin predicates.
     * 
     * @return <code>true</code> if these rules are builtin predicates,
     *         <code>false</code> otherwise.
     */
    public boolean isBuiltin() {
	return builtin;
    };

    /**
     * Set the name of the library associated with this rule definition. Once an
     * association is made it is an error to change it (throws
     * <code>InvalidLibraryEntryException</code> in this case). The default is a
     * <code>null</code> valued library.
     * 
     * @param lib
     *            The name of the library.
     */
    public void setLibrary(String lib) {
	if (library == null)
	    library = lib;
	else
	    throw new InvalidLibraryEntryException();
    };

    /**
     * Get the name of the library associated with this operator.
     * 
     * @return The name of the library. May be <code>null</code>.
     */
    public String getLibrary() {
	return library;
    };

    public boolean sameLibrary(String lib) {
	if (library == null && lib == null)
	    return true;
	if (library == null || lib == null)
	    return false;

	return library.equals(lib);
    };

    public int size() {
	return rules.size();
    };

    public jRule getRuleAt(int n) {
	return (jRule) rules.elementAt(n);
    };

    /**
     * Determine if rules match the given name and arity.
     * 
     * @param fn
     *            The given name and arity.
     * 
     * @return <code>true</code> if these rules have the same name and arity as
     *         the given argument, <code>false</code> otherwise.
     */
    public final boolean match(iNameArity fn) {
	return (fn.getName().equals(rule_name) && fn.getArity() == rule_arity);
    };

    /**
     * Add a rule to the definitions. No ordering is specified, and duplicate
     * rules are permitted.
     * 
     * @param fn
     *            The <code>jRule</code> to add.
     */
    public void addRule(jRule fn) {
	if (!match(fn))
	    throw new UnmatchedRuleException();

	rules.addElement(fn);
    };

    /**
     * Add a rule to the begining of the rule definitions. Duplicate rules are
     * permitted. Rules at the begining are evaluated first during the proof
     * phase.
     * 
     * @param fn
     *            The <code>jRule</code> to add.
     */
    public void addRuleFirst(jRule fn) {
	if (!match(fn))
	    throw new UnmatchedRuleException();

	rules.insertElementAt(fn, 0);
    };

    /**
     * Add a rule to the end of the rule definitions. Duplicate rules are
     * permitted. Rules at the end are evaluated last during the proof phase.
     * 
     * @param fn
     *            The <code>jRule</code> to add.
     */
    public void addRuleLast(jRule fn) {
	if (!match(fn))
	    throw new UnmatchedRuleException();

	rules.insertElementAt(fn, rules.size());
    };

    /**
     * Remove a rule from the rule definitions. Removes on one instance of the
     * rule, if duplicate rules exist.
     * 
     * @param fn
     *            The <code>jRule</code> to remove.
     */
    public void removeRule(jRule fn) {
	rules.removeElement(fn);
    };

    public void clearRules() {
	rules = new Vector();
    };

    public Enumeration enumRules() {
	return rules.elements();
    };

    public jPredicateGoal createGoal(jCompoundTerm t) {
	return new jPredicateGoal(this, t);
    };

    public void consult(jKnowledgeBase kb) {
	Enumeration e;
	jRule r;

	e = enumRules();
	while (e.hasMoreElements()) {
	    r = (jRule) e.nextElement();

	    // builtin is true if any rule is a builtin.
	    if (r instanceof jBuiltinRule)
		builtin = true;

	    r.consult(kb);
	}
    };

    public final boolean prove(jPredicateGoal pg, iGoalStack goals) {
	int next_rule;

	while ((next_rule = pg.getNextRuleNumber()) < rules.size())
	    if (((jRule) rules.elementAt(next_rule)).unify(pg, goals))
		return true;

	return false;
    };

    public boolean retry(jPredicateGoal pg) {
	return (pg.getRuleNumber() < rules.size());
    };

    public String toString() {
	StringBuffer sb = new StringBuffer();

	sb.append(getName() + "/" + Integer.toString(getArity()));

	{
	    String lib = getLibrary();

	    if (lib != null)
		sb.append(" library:" + lib);
	}

	return sb.toString();
    };
};
