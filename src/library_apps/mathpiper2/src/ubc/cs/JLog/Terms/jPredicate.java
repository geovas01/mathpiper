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
//	Predicate
//#########################################################################

package ubc.cs.JLog.Terms;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.Goals.*;

/**
 * This is the base class for Prolog predicates.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public class jPredicate extends iPredicate {
    protected String name;
    protected int arity;
    protected jCompoundTerm arguments;
    protected jRuleDefinitions cached_ruledefs = null;

    /**
     * <code>jPredicate</code> constructor.
     * 
     * @param n
     *            The predicates name.
     * @param args
     *            The arguments for the predicate.
     */
    public jPredicate(String n, jCompoundTerm args) {
	name = n;

	// prevent invalid names
	if (name == null || name.length() <= 0)
	    throw new InvalidPredicateNameException();

	arguments = args;
	arity = args.size();

	type = TYPE_PREDICATE;
    };

    /**
     * Copy constructor to convert <code>jAtom</code> to predicate/0
     * 
     * @param a
     *            The <code>jAtom</code>.
     */
    public jPredicate(jAtom a) {
	name = a.getName();
	arguments = a.getArguments();
	arity = 0;
	cached_ruledefs = a.getCachedRuleDefinitions();

	type = TYPE_PREDICATE;
    };

    /**
     * Constructor for subclasses which must set arguments and arity themselves.
     * 
     * @param n
     *            The predicates name.
     */
    protected jPredicate(String n) {
	name = n;

	type = TYPE_PREDICATE;
    };

    protected jPredicate(String n, jCompoundTerm args, int ar,
	    jRuleDefinitions crds) {
	name = n;
	arguments = args;
	arity = ar;
	cached_ruledefs = crds;

	type = TYPE_PREDICATE;
    };

    public final String getName() {
	return name;
    };

    public final int getArity() {
	return arity;
    };

    protected int compareArguments(iPredicate ipred, boolean first_call,
	    boolean var_equal) {
	if (ipred instanceof jPredicate) {
	    jPredicate jp = (jPredicate) ipred;

	    return arguments.compare(jp.arguments, true, var_equal);
	}

	return (first_call ? -ipred.compareArguments(this, false, var_equal)
		: EQUAL);
    };

    public jRuleDefinitions getCachedRuleDefinitions() {
	return cached_ruledefs;
    };

    public void consult(jKnowledgeBase kb) {
	cached_ruledefs = kb.getRuleDefinitionsMatch(this);
	arguments.consult(kb);
	arity = arguments.size();
    };

    public void consultReset() {
	cached_ruledefs = null;
	arity = arguments.size();
	arguments.consultReset();
    };

    public boolean isConsultNeeded() {
	// we assume that if we dont need to consult then neither do the
	// arguments
	return cached_ruledefs == null;
    };

    public void addGoals(jGoal g, jVariable[] vars, iGoalStack goals) {
	if (cached_ruledefs == null)
	    if (!lookup_predicate()) {
		goals.push(new jFailGoal());
		return;
	    }

	goals.push(cached_ruledefs.createGoal((jCompoundTerm) arguments
		.duplicate(vars)));
    };

    public void addGoals(jGoal g, iGoalStack goals) {
	if (cached_ruledefs == null)
	    if (!lookup_predicate()) {
		goals.push(new jFailGoal());
		return;
	    }

	goals.push(cached_ruledefs.createGoal(arguments));
    };

    public void registerUnboundVariables(jUnifiedVector v) {
	arguments.registerUnboundVariables(v);
    };

    public boolean equivalence(jTerm term, jEquivalenceMapping v) {
	jTerm t = term.getTerm();

	// altough we cannot be certain that term is a jPredicate, if it is not
	// then type was
	// wrong, so this warrents a failing exception.
	if (type == t.type) {
	    jPredicate pterm;

	    pterm = (jPredicate) t;

	    // predicates must have same name
	    if (!name.equals(pterm.name))
		return false;

	    // only unify if terms have same arity
	    if (arity != pterm.arity)
		return false;

	    // arguments must be equivalent
	    return arguments.equivalence(pterm.arguments, v);
	} else if (t instanceof iPredicate) {
	    iPredicate iterm = (iPredicate) t;

	    // predicates must have same name
	    if (!(name.equals(iterm.getName())))
		return false;

	    // only unify if terms have same arity
	    if (!(getArity() == iterm.getArity()))
		return false;

	    // arguments must be equivalent
	    return arguments.equivalence(iterm.getArguments(), v);
	} else
	    return false;
    };

    public boolean unify(jTerm term, jUnifiedVector v) {
	// if term is variable we let it handle the unification
	if (term.type == TYPE_VARIABLE)
	    return term.unify(this, v);

	// altough we cannot be certain that term is a jPredicate, if it is not
	// then type was
	// wrong, so this warrents a failing exception.
	if (type == term.type) {
	    jPredicate pterm;

	    pterm = (jPredicate) term;

	    // predicates must have same name
	    if (!name.equals(pterm.name))
		return false;

	    // only unify if terms have same arity
	    if (arity != pterm.arity)
		return false;

	    // arguments must unify
	    return arguments.unify(pterm.arguments, v);
	} else if (term instanceof iPredicate) {
	    iPredicate iterm = (iPredicate) term;

	    // predicates must have same name
	    if (!(name.equals(iterm.getName())))
		return false;

	    // only unify if terms have same arity
	    if (!(getArity() == iterm.getArity()))
		return false;

	    // arguments must unify
	    return arguments.unify(iterm.getArguments(), v);
	} else
	    return false;
    };

    public final boolean equivalenceArguments(jCompoundTerm term,
	    jEquivalenceMapping v) {
	return arguments.equivalence(term, v);
    };

    public final boolean unifyArguments(jCompoundTerm term, jUnifiedVector v) {
	return arguments.unify(term, v);
    };

    public void registerVariables(jVariableVector v) {
	arguments.registerVariables(v);
    };

    public void enumerateVariables(jVariableVector v, boolean all) {
	arguments.enumerateVariables(v, all);
    };

    public jTerm duplicate(jVariable[] vars) {
	return new jPredicate(name, (jCompoundTerm) arguments.duplicate(vars),
		arity, cached_ruledefs);
    };

    public jTerm copy(jVariableRegistry vars) {
	return new jPredicate(name, (jCompoundTerm) arguments.copy(vars),
		arity, cached_ruledefs);
    };

    public final jCompoundTerm getArguments() {
	return arguments;
    };

    public void addTerm(jTerm t) {
	arguments.addTerm(t);
	arity = arguments.size();
    };

    public void removeTerm(jTerm t) {
	arguments.removeTerm(t);
	arity = arguments.size();
    };

    public jCompoundTerm duplicateArguments(jVariable[] vars) {
	return (jCompoundTerm) arguments.duplicate(vars);
    };

    public String toString(boolean usename) {
	return (arguments.size() > 0 ? name + arguments.toString(usename)
		: name);
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
