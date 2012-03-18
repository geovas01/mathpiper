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
//	CompoundTerm
//#########################################################################

package ubc.cs.JLog.Terms;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;

/**
 * This class represents a collection of terms.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public class jCompoundTerm extends jTerm implements iMakeUnmake {
    protected Vector terms;

    public jCompoundTerm() {
	terms = new Vector();
	type = TYPE_COMPOUND;
    };

    public jCompoundTerm(int initialCapacity) {
	terms = new Vector(initialCapacity);
	type = TYPE_COMPOUND;
    };

    public jCompoundTerm(Vector t) {
	terms = t;
	type = TYPE_COMPOUND;
    };

    public int compare(jTerm term, boolean first_call, boolean var_equal) {
	jTerm t = term.getTerm();

	if (t instanceof jCompoundTerm) {
	    Vector vt = ((jCompoundTerm) t).terms;
	    int i, max = terms.size();
	    int result;

	    if (max < vt.size())
		return LESS_THAN;
	    else if (max > vt.size())
		return GREATER_THAN;

	    for (i = 0; i < max; i++) {
		result = ((jTerm) terms.elementAt(i)).compare(
			(jTerm) vt.elementAt(i), true, var_equal);
		if (result != EQUAL)
		    return result;
	    }
	    return EQUAL;
	}

	return (first_call ? -t.compare(this, false, var_equal) : EQUAL);
    };

    public final boolean hasTerm(jTerm t) {
	return (terms.indexOf(t) >= 0);
    };

    public void addTerm(jTerm t) {
	terms.addElement(t);
    };

    public void removeTerm(jTerm t) {
	terms.removeElement(t);
    };

    public void removeAllTerms() {
	terms.removeAllElements();
    };

    public final Enumeration enumTerms() {
	return terms.elements();
    };

    public final int size() {
	return terms.size();
    };

    public final jTerm elementAt(int index) {
	return (jTerm) terms.elementAt(index);
    };

    /**
     * Mutates a term element in the compount term. NOTE: Do not use this
     * method, except in an exceptional circumstance, since it violates the
     * logical consistency of standard Prolog.
     * 
     * @param index
     *            The zero-based index of the element to mutate.
     * @param term
     *            The term to make the new element at index.
     * 
     */
    public void mutateElementAt(int index, jTerm term) {
	terms.setElementAt(term, index);
    };

    public final boolean isEmpty() {
	return terms.isEmpty();
    };

    public boolean requiresCompleteVariableState() {
	Enumeration e;

	e = terms.elements();

	while (e.hasMoreElements())
	    if (((jTerm) e.nextElement()).requiresCompleteVariableState())
		return true;

	return false;
    };

    public void registerUnboundVariables(jUnifiedVector v) {
	int i, sz = terms.size();

	for (i = 0; i < sz; i++)
	    ((jTerm) terms.elementAt(i)).registerUnboundVariables(v);
    };

    public boolean equivalence(jTerm term, jEquivalenceMapping v) {
	jTerm t = term.getTerm();

	// only equiv with other compound terms of same type
	if (type != t.type)
	    return false;

	// altough we cannot be certain that term is a jCompoundTerm, if it is
	// not then type
	// was wrong so this warrents a failing exception.
	{
	    jCompoundTerm cterm;
	    int sz;

	    cterm = (jCompoundTerm) t;

	    // only equivalence if terms have same size
	    if ((sz = terms.size()) != cterm.terms.size())
		return false;

	    {
		int i;

		// equiv each element of compound term, exit on first failure.
		for (i = 0; i < sz; i++)
		    if (!((jTerm) terms.elementAt(i)).equivalence(
			    (jTerm) cterm.terms.elementAt(i), v))
			return false;
	    }
	}
	return true;
    };

    public boolean unify(jTerm term, jUnifiedVector v) {
	// if term is variable we let it handle the unification
	if (term.type == TYPE_VARIABLE)
	    return term.unify(this, v);

	// only unify with other compound terms of same type
	if (type != term.type)
	    return false;

	// altough we cannot be certain that term is a jCompoundTerm, if it is
	// not then type
	// was wrong so this warrents a failing exception.
	{
	    jCompoundTerm cterm;
	    int sz;

	    cterm = (jCompoundTerm) term;

	    // only unify if terms have same size
	    if ((sz = terms.size()) != cterm.terms.size())
		return false;

	    {
		int i;

		// unify each element of compound term, exit on first
		// unification failure.
		for (i = 0; i < sz; i++)
		    if (!((jTerm) terms.elementAt(i)).unify(
			    (jTerm) cterm.terms.elementAt(i), v))
			return false;
	    }
	}
	return true;
    };

    public void registerVariables(jVariableVector v) {
	Enumeration e;

	e = terms.elements();

	while (e.hasMoreElements())
	    ((jTerm) e.nextElement()).registerVariables(v);
    };

    public void enumerateVariables(jVariableVector v, boolean all) {
	Enumeration e;

	e = terms.elements();

	while (e.hasMoreElements())
	    ((jTerm) e.nextElement()).enumerateVariables(v, all);
    };

    public jTerm duplicate(jVariable[] vars) {
	return new jCompoundTerm(internal_duplicate(vars));
    };

    protected Vector internal_duplicate(jVariable[] vars) {
	int i, sz = terms.size();
	Vector t = new Vector(sz);

	for (i = 0; i < sz; i++)
	    t.addElement(((jTerm) terms.elementAt(i)).duplicate(vars));

	return t;
    };

    public jTerm copy(jVariableRegistry vars) {
	return new jCompoundTerm(internal_copy(vars));
    };

    public Vector internal_copy(jVariableRegistry vars) {
	int i, sz = terms.size();
	Vector t = new Vector(sz);

	for (i = 0; i < sz; i++)
	    t.addElement(((jTerm) terms.elementAt(i)).copy(vars));

	return t;
    };

    /**
     * Makes this a copy of the provided <code>jCompoundTerm</code>.
     * 
     * @param ct
     *            <code>jCompoundTerm</code> to duplicate.
     */
    public void copyCompoundTerm(jCompoundTerm ct) {
	terms = (Vector) ct.terms.clone();
    };

    /**
     * Removes all <code>jTerms</code> in given <code>jCompoundTerm</code> from
     * this.
     * 
     * @param ct
     *            <code>jCompoundTerm</code> with terms to remove.
     */
    public void subtractCompoundTerm(jCompoundTerm ct) {
	Enumeration e = ct.terms.elements();

	while (e.hasMoreElements())
	    removeTerm((jTerm) e.nextElement());
    };

    /**
     * Removes all <code>jTerms</code> not in given <code>jCompoundTerm</code>
     * from this.
     * 
     * @param ct
     *            <code>jCompoundTerm</code> with terms to keep.
     */
    public void unionCompoundTerm(jCompoundTerm ct) {
	Enumeration e = ct.terms.elements();

	while (e.hasMoreElements()) {
	    jTerm t = (jTerm) e.nextElement();

	    if (!hasTerm(t))
		addTerm(t);
	}
    };

    /**
     * Adds all <code>jTerms</code> in given <code>jCompoundTerm</code> to this.
     * 
     * @param ct
     *            <code>jCompoundTerm</code> with terms to add.
     */
    public void intersectionCompoundTerm(jCompoundTerm ct) {
	Enumeration e = terms.elements();
	Vector vt = new Vector(Math.min(size(), ct.size()));

	while (e.hasMoreElements()) {
	    jTerm t = (jTerm) e.nextElement();

	    if (ct.hasTerm(t))
		vt.addElement(t);
	}

	terms = vt;
    };

    /**
     * Makes this a representation of the provided <code>jTerm</code>. Invokes
     * <code>makeCompoundTerm</code>.
     * 
     * @param t
     *            <code>jTerm</code> using <code>jCons</code> to separate terms.
     */
    public void make(jTerm t) {
	removeAllTerms();
	makeCompoundTerm(t);
    };

    /**
     * Add <code>jCons</code> separated <code>jTerm</code>s to this
     * <code>jCompoundTerm</code>.
     * 
     * @param t
     *            <code>jTerm</code> using <code>jCons</code> to separate terms.
     */
    public void makeCompoundTerm(jTerm t) {
	while (t instanceof jCons) {
	    addTerm(((jCons) t).getLHS());
	    t = ((jCons) t).getRHS().getTerm();
	}
	addTerm(t);
    };

    /**
     * Creates a <code>jCons</code> separated <code>jTerm</code>s to represent
     * this <code>jCompoundTerm</code>. Invokes <code>unmakeCompoundTerm</code>
     * to perform work.
     * 
     * @return <code>jTerm</code> using <code>jCons</code> to separate terms.
     *         Duplicates this <code>jCompoundTerm</code>
     */
    public jTerm unmake() {
	return unmakeCompoundTerm();
    };

    /**
     * Creates a <code>jCons</code> separated <code>jTerm</code>s to represent
     * this <code>jCompoundTerm</code>.
     * 
     * @return <code>jTerm</code> using <code>jCons</code> to separate terms.
     *         Duplicates this <code>jCompoundTerm</code>
     */
    public synchronized jTerm unmakeCompoundTerm() {
	int i;
	jTerm prev = null;

	for (i = terms.size() - 1; i >= 0; i--) {
	    if (prev == null)
		prev = (jTerm) terms.elementAt(i);
	    else
		prev = new jCons((jTerm) terms.elementAt(i), prev);
	}
	return prev;
    };

    public void consult(jKnowledgeBase kb) {
	Enumeration e;

	e = enumTerms();

	while (e.hasMoreElements())
	    ((iConsultable) e.nextElement()).consult(kb);
    };

    public void consultReset() {
	Enumeration e;

	e = enumTerms();

	while (e.hasMoreElements())
	    ((iConsultable) e.nextElement()).consultReset();
    };

    public String toString(boolean usename) {
	StringBuffer sb = new StringBuffer();
	Enumeration e;
	boolean first = true;

	e = terms.elements();

	sb.append(getStartingSymbol());
	while (e.hasMoreElements()) {
	    jTerm t;
	    boolean higher_priority;

	    if (!first)
		sb.append(",");

	    t = (jTerm) e.nextElement();
	    higher_priority = isHigherPriorityOperator(t) && (terms.size() > 1);

	    if (higher_priority)
		sb.append("(");

	    sb.append(t.toString(usename));

	    if (higher_priority)
		sb.append(")");

	    first = false;
	}
	sb.append(getEndingSymbol());

	return sb.toString();
    };

    protected String getStartingSymbol() {
	return "(";
    };

    protected String getEndingSymbol() {
	return ")";
    };

    /**
     * Determines if provided <code>jTerm</code> is of higher priority than ','.
     * 
     * @param t
     *            The <code>jTerm</code> to evaluate.
     * 
     * @return <code>true</code> if <code>jTerm</code> is a builtin operator
     *         with higher priority than the ',' operator, <code>false</code>
     *         otherwise.
     */
    protected boolean isHigherPriorityOperator(jTerm t) {
	return (t instanceof jOr || t instanceof jOrPredicate
		|| t instanceof jCommand || t instanceof jIf);
    };
};
