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
//	Atom
//#########################################################################

package ubc.cs.JLog.Terms;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Builtins.*;
import ubc.cs.JLog.Terms.Goals.*;

public class jAtom extends iPredicate {
    // arguments is a dummy variable to provide predicate properties. It should
    // not be used
    // for arguments because arity is 0.
    protected final static jCompoundTerm arguments = new jCompoundTerm(0);

    protected String name;
    protected jRuleDefinitions cached_ruledefs = null;

    public jAtom(String n) {
	name = n;

	// prevent invalid names
	if (name == null)
	    throw new InvalidPredicateNameException();

	type = TYPE_ATOM;
    };

    public String getName() {
	return name;
    };

    public int getArity() {
	return 0;
    };

    protected int compareArguments(iPredicate ipred, boolean first_call,
	    boolean var_equal) {
	if (ipred instanceof jAtom)
	    return EQUAL;

	return (first_call ? -ipred.compareArguments(this, false, var_equal)
		: EQUAL);
    };

    public jRuleDefinitions getCachedRuleDefinitions() {
	return cached_ruledefs;
    };

    public void consult(jKnowledgeBase kb) {
	cached_ruledefs = kb.getRuleDefinitionsMatch(this);
    };

    public void consultReset() {
	cached_ruledefs = null;
    };

    public boolean isConsultNeeded() {
	return cached_ruledefs == null;
    };

    public void addGoals(jGoal g, jVariable[] vars, iGoalStack goals) {
	if (cached_ruledefs == null)
	    if (!lookup_predicate()) {
		goals.push(new jFailGoal());
		return;
	    }

	goals.push(cached_ruledefs.createGoal(arguments));
    };

    public void addGoals(jGoal g, iGoalStack goals) {
	if (cached_ruledefs == null)
	    if (!lookup_predicate()) {
		goals.push(new jFailGoal());
		return;
	    }

	goals.push(cached_ruledefs.createGoal(arguments));
    };

    public boolean equivalence(jTerm term, jEquivalenceMapping v) {
	jTerm t = term.getTerm();

	// many atoms may be same instance
	if (this == term)
	    return true;

	if (t.type != TYPE_ATOM)
	    return false;

	// altough we cannot be certain that term is a jAtom, if it is not then
	// type was wrong
	// so this warrents a failing exception.
	if (type == t.type) {
	    jAtom aterm = (jAtom) t;

	    if (name == aterm.name)
		return true;

	    return name.equals(aterm.name);
	} else if (term instanceof iPredicate) {
	    iPredicate iterm = (iPredicate) t;

	    return name.equals(iterm.getName()) && (iterm.getArity() == 0);
	} else
	    return false;
    };

    public boolean unify(jTerm term, jUnifiedVector v) {
	// if term is variable we let it handle the unification
	if (term.type == TYPE_VARIABLE)
	    return term.unify(this, v);

	// many atoms may be same instance
	if (this == term)
	    return true;

	// altough we cannot be certain that term is a jAtom, if it is not then
	// type was wrong
	// so this warrents a failing exception.
	if (type == term.type) {
	    jAtom aterm = (jAtom) term;

	    if (name == aterm.name)
		return true;

	    return name.equals(aterm.name);
	} else if (term instanceof iPredicate) {
	    iPredicate iterm = (iPredicate) term;

	    return name.equals(iterm.getName()) && (iterm.getArity() == 0);
	} else
	    return false;
    };

    public void registerVariables(jVariableVector v) {
    };

    public void enumerateVariables(jVariableVector v, boolean all) {
    };

    public jTerm duplicate(jVariable[] vars) {
	return this; // since atoms are constants, don't duplicate for memory
		     // and gc considerations
    };

    public jTerm copy(jVariableRegistry vars) {
	return this; // since atoms are constants, don't duplicate for memory
		     // and gc considerations
    };

    public jCompoundTerm getArguments() {
	return arguments;
    };

    public String toString(boolean usename) {
	return name;
    };

    protected boolean lookup_predicate() {
	Thread t;

	t = Thread.currentThread();
	if (t instanceof jPrologServiceThread) {
	    jPrologServiceThread pst = (jPrologServiceThread) t;
	    jPrologServices prolog = pst.getPrologServices();

	    consultReset();
	    consult(prolog.getKnowledgeBase());

	    if (cached_ruledefs == null) {
		if (prolog.getFailUnknownPredicate())
		    return false;
		else
		    throw new UnknownPredicateException(this);
	    } else
		return true;
	} else
	    throw new UnknownPredicateException(this);
    };
};
