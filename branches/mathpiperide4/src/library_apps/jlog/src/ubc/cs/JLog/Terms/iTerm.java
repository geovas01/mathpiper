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
//	Term
//#########################################################################

package ubc.cs.JLog.Terms;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;

/**
 * This is the fundamental interface for Prolog terms.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public interface iTerm extends iName, iValue, iUnifiable, iConsultable {
    // used for compare
    public static final int LESS_THAN = -1;
    public static final int EQUAL = 0;
    public static final int GREATER_THAN = 1;

    public jTerm getValue();

    /**
     * Returns the non-evaluated <code>jTerm</code> representation of this term.
     * Variables return the non-evaluated terms they are bound to.
     * 
     * @return <code>jTerm</code> of the non-evaluated term bound to this
     *         instance.
     */
    public jTerm getTerm();

    public String getName();

    /**
     * The public interface for term comparision, it calls the protected
     * <code>compare</code> that sub-classes must override.
     * 
     * @param term
     *            the <code>jTerm</code> to compare with this instance of
     *            <code>jTerm</code>.
     * @param var_equal
     *            if <code>var_equal</code> is <code>true</code>, then unbound
     *            variables are considered equal. <code>var_equal</code> =
     *            <code>false</code> is standard prolog behavior.
     * @return <code>LESS_THAN</code> if this instance is less than
     *         <code>term</code>, <code>EQUAL</code> if the two terms are equal,
     *         and <code>GREATER_THAN</code> if this instance is greater than
     *         <code>term</code>.
     */
    public int compare(jTerm term, boolean var_equal);

    /**
     * Call to determine if enclosing rule should record all variable states. A
     * rules goal only tracks variables which were unified, and relies on stack
     * unwinding to unbind all variables. Terms such as <code>cut</code>, which
     * jump back to the containing rules goal require that all variables are
     * restored.
     * 
     * @return <code>true</code> if all variable bindings must be recorded by
     *         the rule containing this term. <code>false</code> otherwise.
     *         Normally returns <code>false</code>
     */
    public boolean requiresCompleteVariableState();

    /**
     * Adds any unbound variables belonging to this term (or belonging to any
     * sub-part of this term) to the <code>jUnifiedVector</code>
     * 
     * @param v
     *            The <code>jUnifiedVector</code> where unbound variables are
     *            added to. This parameter is used to as output to the caller,
     *            not as input.
     */
    public void registerUnboundVariables(jUnifiedVector v);

    /**
     * Adds all variables belonging to this term (or belonging to any sub-part
     * of this term) to the <code>jUnifiedVector</code> Should be called during
     * the consultation phase by rules for their owned terms both head and base.
     * All variables encountered should be unbound; hence it is NOT recommended
     * to call this method after the consultation phase completes (e.g., after a
     * query proof). After call, this term should not be modified in any way.
     * 
     * @param v
     *            The <code>jUnifiedVector</code> where variables are added to.
     *            This parameter is used to as output to the caller, not as
     *            input.
     */
    public void registerVariables(jVariableVector v);

    /**
     * Adds variables belonging to this term (or belonging to any sub-part of
     * this term) to the <code>jVariableVector</code>. If a variable is bound,
     * it is not included in the enumeration.
     * 
     * @param v
     *            The <code>jVariabeVector</code> where variables are added to.
     *            This parameter is used to as output to the caller, not as
     *            input.
     * @param all
     *            If <code>true</code>, then all variables should register,
     *            otherwise only add non-existentially qualified variables.
     */
    public void enumerateVariables(jVariableVector v, boolean all);

    /**
     * The public interface for evaluating term equivalence (i.e., structural
     * similarity).
     * 
     * @param term
     *            the <code>jTerm</code> to compare with this instance of
     *            <code>jTerm</code>.
     * @param v
     *            <code>v</code> is the collection of equivalent variable pairs
     *            found. It should be empty when first calling this function.
     * @return <code>true</code> if this instance is equivalent to
     *         <code>term</code>, <code>false</code> otherwise.
     */
    public boolean equivalence(jTerm term, jEquivalenceMapping v);

    public boolean unify(jTerm term, jUnifiedVector v);

    /**
     * Creates a complete and entirely independant duplicate of this term. User
     * should call only for terms for which <code>registerVariables</code> has
     * already been invoked. Within <code>duplicate</code>, any other calls to
     * duplicate should pass along the same <code>vars</code> array produced by
     * <code>registerVariables</code> since the same duplication path previously
     * taken by <code>registerVariables</code> should be taken by
     * <code>duplicate</code>. This call is designed only for terms which belong
     * to rules and are templates for instantiation. Any variables in the term
     * should be unbound. As implied by the modification restrictions on
     * <code>registerVariables</code>, terms and their children cannot change
     * (especially during call!)
     * 
     * @param vars
     *            The user passes in a duplicate of the variable vector produced
     *            from the previous call to <code>registerVariables</code>.
     *            <code>vars</code> is produced from the
     *            <code>jVariableVector</code> by creating a single duplicate
     *            variable for each variable. Since this is created in the same
     *            order as the <code>registerVariables</code>, it is now
     *            efficient for <code>jVariables</code> to return their unique
     *            duplicate.
     * @return <code>jTerm</code> which is an instantiated duplicate of this
     *         term.
     */
    public jTerm duplicate(jVariable[] vars);

    /**
     * Public member function which creates a copy of this term. This member
     * function is designed to duplicate a term, but without the restrictions of
     * the <code>duplicate</code> function. Sub-classes need not override, only
     * implement the abstract <code>copy(vars)</code> member function. copy is
     * not as efficient as <code>duplicate</code>.
     * 
     * @return <code>jTerm</code> which is an instantiated copy of this term.
     */
    public jTerm copy();

    /**
     * Internal member function which creates a copy of this term. This member
     * function is designed to duplicate a term, but without the restrictions of
     * the <code>duplicate</code> function. Should only be invoked by
     * <code>copy()</code> or other <code>copy(vars)</code>. Should only invoke
     * other <code>copy(vars)</code> functions. Bound variables should return a
     * copy of the bound term.
     * 
     * @param vars
     *            The registry of variables and their duplicates. Initially this
     *            is empty. As variables generate copies, they add themselves
     *            and their copy to the <code>jVariableRegistry</code>, and this
     *            is output from the function call. Any further calls with the
     *            same <code>vars</code> ensures that the same variable (in a
     *            different term) returns the same copy.
     * @return <code>jTerm</code> which is an instantiated copy of this term.
     */
    public jTerm copy(jVariableRegistry vars);

    public void consult(jKnowledgeBase kb);

    public void consultReset();

    public boolean isConsultNeeded();

    /**
     * Produces a string identifying this term, suitable for display to the
     * console.
     * 
     * param usename determines whether to display variables by name or
     * identity. <code>false</code> is the default for displaying the term,
     * <code>true</code> for displaying this term in a user query.
     * 
     * @return <code>String</code> which is a textual representation of this
     *         term.
     */
    public String toString(boolean usename);
};
