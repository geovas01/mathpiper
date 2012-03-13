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
//	BuiltinPredicate
//#########################################################################

package ubc.cs.JLog.Terms;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;

/**
 * This is the abstract base for builtin predicates. The parser must create the
 * appropriate object for builtin predicates. Builtins do not appear in the
 * database and are designed to provide a way to optimize a predicate or provide
 * additional functionallity.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
abstract public class jBuiltinPredicate extends iPredicate {
    public jBuiltinPredicate() {
	type = TYPE_BUILTINPREDICATE;
    };

    public int getArity() {
	return 0;
    };

    /**
     * Compares predicate aguments. Override in classes with different arities.
     */
    protected int compareArguments(iPredicate ipred, boolean first_call,
	    boolean var_equal) {
	if (ipred instanceof jBuiltinPredicate && getArity() == 0) {
	    jBuiltinPredicate bip = (jBuiltinPredicate) ipred;
	    int compare_val;
	    int arity_b;

	    arity_b = bip.getArity();

	    if (0 < arity_b)
		return LESS_THAN;
	    else if (0 > arity_b)
		return GREATER_THAN;

	    compare_val = getName().compareTo(bip.getName());

	    if (compare_val < 0)
		return LESS_THAN;
	    if (compare_val > 0)
		return GREATER_THAN;
	    return EQUAL;
	}

	return (first_call ? -ipred.compareArguments(this, false, var_equal)
		: EQUAL);
    };

    public boolean isConsultNeeded() {
	return false; // builtins without arguments don't need consulting
    };

    public boolean equivalence(jTerm term, jEquivalenceMapping v) {
	jTerm t = term.getTerm();

	// only unify with other predicate terms
	if (type != t.type)
	    return false;

	// altough we cannot be certain that term is a jBuiltinPredicate, if it
	// is not then
	// type was wrong so this warrents a failing exception.
	{
	    jBuiltinPredicate pterm;

	    pterm = (jBuiltinPredicate) t;

	    // predicates must have same name
	    if (!getName().equals(pterm.getName()))
		return false;

	    // only test if terms have same arity
	    if (getArity() != pterm.getArity())
		return false;

	    // arguments must be equivalent. subclasses defining new arguments
	    // should handle.
	    return equivalenceArguments(pterm, v);
	}
    };

    /**
     * Unification. For builtin predicates, unification may happen when they are
     * arguments to predicates.
     */
    public boolean unify(jTerm term, jUnifiedVector v) {
	// if term is variable we let it handle the unification
	if (term.type == TYPE_VARIABLE)
	    return term.unify(this, v);

	// only unify with other predicate terms
	if (type != term.type)
	    return false;

	// altough we cannot be certain that term is a jBuiltinPredicate, if it
	// is not then
	// type was wrong so this warrents a failing exception.
	{
	    jBuiltinPredicate pterm;

	    pterm = (jBuiltinPredicate) term;

	    // predicates must have same name
	    if (!getName().equals(pterm.getName()))
		return false;

	    // only unify if terms have same arity
	    if (getArity() != pterm.getArity())
		return false;

	    // arguments must unify. subclasses defining new arguments should
	    // handle.
	    return unifyArguments(pterm, v);
	}
    };

    /**
     * Equivalence test arguments. Similar behavior to that of
     * <code>equivalence</code>.
     * 
     * @param pterm
     *            We are assured that pterm has the same type, name, and arity.
     * @param v
     *            same as <code>jEquivalenceMapping</code> parameter in
     *            <code>equivalence</code>.
     * 
     * @return Should test for instanceof as required and return false if there
     *         is no match, true otherwise.
     */
    protected boolean equivalenceArguments(jBuiltinPredicate pterm,
	    jEquivalenceMapping v) {
	return true;
    };

    /**
     * Unification of arguments. Similar behavior to that of <code>unify</code>.
     * 
     * @param pterm
     *            We are assured that pterm has the same type, name, and arity.
     * @param v
     *            same as <code>jUnifiedVector</code> parameter in
     *            <code>unify</code>.
     * 
     * @return Should test for instanceof as required and return false if there
     *         is no match, true otherwise.
     */
    protected boolean unifyArguments(jBuiltinPredicate pterm, jUnifiedVector v) {
	return true;
    };

    public void registerVariables(jVariableVector v) {
    };

    public void enumerateVariables(jVariableVector v, boolean all) {
    };

    public jTerm duplicate(jVariable[] vars) {
	return this; // in many cases builtins are constant, so don't duplicate
    };

    public jTerm copy(jVariableRegistry vars) {
	return this; // in many cases builtins are constant, so don't duplicate
    };

    public String toString(boolean usename) {
	return getName();
    };
};
