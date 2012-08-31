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
//	PredicateEntry
//#########################################################################

package ubc.cs.JLog.Parser;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.*;

/**
 * Abstract base class for entries in the <code>pPredicateRegistry</code>. Each
 * predicate entry subclass represents a type of prolog predicate at the level
 * needed for the parser (they are parse time representations). Predicate
 * entries contain information about the corresponding predicate, such as name
 * and arity. Predicate entries generate the corresponding real Prolog predicate
 * objects.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
abstract public class pPredicateEntry implements iNameArity {
    public static final int NARY_ARITY = -1;

    protected String name;
    protected int arity;
    protected String library = null;

    public pPredicateEntry(String name, int arity) {
	this.name = name;
	this.arity = arity;

	// prevent invalid entries
	if (name == null || name.length() <= 0)
	    throw new InvalidPredicateNameException();
    };

    public String getName() {
	return name;
    };

    public int getArity() {
	return arity;
    };

    /**
     * Tests the arity of this entry. Most predicates entries will only return
     * true if there is an exact match, however, for n-ary predicates, arity
     * should be set to NARY_ARITY, and isArity should always return true for
     * supported arities.
     * 
     * @param a
     *            The arity to test this entry for.
     * 
     * @return <code>boolean</code> which is true if entry can be of this arity.
     */
    public boolean isArity(int a) {
	return arity == a;
    };

    /**
     * Set the name of the library associated with this predicate. Once an
     * association is made it is an error to change it (throws
     * <code>InvalidLibraryEntryException</code> in this case). Predicates
     * default to a <code>null</code> valued library.
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
     * Get the name of the library associated with this predicate.
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

    /**
     * Public interface for generating the real prolog term objects. May
     * generate syntax errors. Invokes internal <code>createPredicate</code> to
     * perform actual construction of term.
     * 
     * @param pt
     *            The parsing token representing the operator. Used for
     *            generating information about the location of the operator in
     *            the input stream.
     * @param cterm
     *            A terms belonging to this predicate.
     * 
     * @return <code>iPredicat</code> representing this predicate and its terms.
     */
    public iPredicate createPredicate(pToken pt, jCompoundTerm cterm) {
	if (!isArity(cterm.size()))
	    throw new SyntaxErrorException("Term with arity "
		    + String.valueOf(arity) + " expected for predicate at ",
		    pt.getPosition(), pt.getLine(), pt.getCharPos());

	return createPredicate(cterm);
    };

    /**
     * Public interface for generating the real prolog term objects. May
     * generate syntax errors. Invokes internal <code>createPredicate</code> to
     * perform actual construction of term.
     * 
     * @return <code>iPredicat</code> representing this predicate with given
     *         arity, with unamed variables for terms.
     */
    public iPredicate createPredicate() {
	jCompoundTerm cterm = new jCompoundTerm();
	int i;

	for (i = 0; i < arity; i++)
	    cterm.addTerm(new jVariable("_"));

	return createPredicate(cterm);
    };

    /**
     * The internal method for creating the <code>iPredicate</code>
     * representation of this predicate representation. Subclasses must
     * override. The terms are provided, already created. The caller is
     * responsible to ensure that arity and cterm.size match.
     * 
     * @param cterm
     *            The collection of <code>jTerm</code>s.
     * 
     * @return <code>iPredicate</code> representing this predicate and its
     *         terms.
     */
    abstract public iPredicate createPredicate(jCompoundTerm cterm);

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
