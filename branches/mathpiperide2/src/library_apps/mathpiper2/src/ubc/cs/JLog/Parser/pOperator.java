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
//	pOperator
//#########################################################################

package ubc.cs.JLog.Parser;

import java.util.*;
import java.lang.*;
import ubc.cs.JLog.Terms.*;

class pOperator extends pPacket {
    protected pPacket lhs = null, rhs = null;
    protected pOperatorEntry op;

    public pOperator(pOperatorEntry op, pToken pt) {
	super(pt);
	this.op = op;
    };

    public void setLHS(pPacket l) {
	lhs = l;
    };

    public void setRHS(pPacket r) {
	rhs = r;
    };

    // verifyCompletion and get?HS functions should only be called when there is
    // no possiblity
    // of any further packets to attach, such as when the operator to the right
    // requires a lhs.
    public void verifyCompletion() {
	getLHS();
	getRHS();
    };

    public pPacket getLHS() {
	if (isAtom())
	    return lhs;

	if (hasLHS() && lhs == null)
	    throw new SyntaxErrorException(
		    "LHS term required before operator '" + getName() + "' at ",
		    token.getPosition(), token.getLine(), token.getCharPos());

	return lhs;
    };

    public pPacket getRHS() {
	if (isAtom())
	    return rhs;

	if (hasRHS() && rhs == null)
	    throw new SyntaxErrorException("RHS term required after operator '"
		    + getName() + "' at ", token.getPosition(),
		    token.getLine(), token.getCharPos());

	return rhs;
    };

    public boolean hasLHS() {
	return op.hasLHS();
    };

    public boolean hasRHS() {
	return op.hasRHS();
    };

    public boolean isAtom() {
	return op.isAtomPermitted() && lhs == null && rhs == null;
    };

    public int getType() {
	return op.getType();
    };

    public int getPriority() {
	return op.getPriority();
    };

    public int getAssociativity() {
	return op.getAssociativity();
    };

    public boolean isNonAssociativeLeft() {
	return op.isNonAssociativeLeft();
    };

    public boolean isNonAssociativeRight() {
	return op.isNonAssociativeRight();
    };

    public boolean hasLHSPacket() {
	return lhs != null;
    };

    public boolean hasRHSPacket() {
	return rhs != null;
    };

    public jTerm getTerm(pVariableRegistry vars, pTermToPacketHashtable phash) {
	jTerm term;

	term = op.createOperator(token, lhs, rhs, vars, phash);
	phash.putPacket(term, this);

	return term;
    };

    // built-in operators can never be generic since they cannot be overridden.
    // This result
    // happens naturally since non-builtin operators return a jPredicate. rules,
    // which require
    // a jpredicate as the head, will throw an error if a builtin operator
    // appears in the head.
    // the arguments should be non-generic so that they can be called if unified
    // with a variable.
    public void setGeneric(boolean genericpred) {
	pPacket l, r;

	l = getLHS();
	r = getRHS();

	if (l != null)
	    l.setGeneric(false);
	if (r != null)
	    r.setGeneric(false);
    };
};
