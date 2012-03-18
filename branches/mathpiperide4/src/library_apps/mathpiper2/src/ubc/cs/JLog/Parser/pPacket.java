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
//	pPacket
//#########################################################################

package ubc.cs.JLog.Parser;

import java.util.*;
import java.lang.*;
import ubc.cs.JLog.Terms.*;

/**
 * Abstract base class representing packets in the parse stream. Each packet is
 * the parse stream representation of a prolog term, and is bound to the parse
 * stream via its corresponding token. Packets form the nodes in our implicit
 * parse tree.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
abstract class pPacket {
    protected pToken token;

    public pPacket(pToken pt) {
	token = pt;
    };

    public String getName() {
	return token.getToken();
    };

    public pToken getToken() {
	return token;
    };

    /**
     * Public interface for generating the real prolog term objects. May
     * generate syntax errors.
     * 
     * @param vars
     *            A registery of named variables (ensures that variables of the
     *            same name are the same instance). This should be the variable
     *            registry of the rule or command containing this operator.
     * @param phash
     *            A registery of terms and their corresponding parsing tokens.
     * 
     * @return <code>jTerm</code> representing this packet.
     */
    abstract public jTerm getTerm(pVariableRegistry vars,
	    pTermToPacketHashtable phash);

    /**
     * Determine if this packet represents a generic predicate (not builtin). If
     * generic_predicate is true, then only create <code>jPredicate</code> for
     * use as rule head. This call is for <code>pPredicate</code> class, but
     * others must pass it down to children packets. Some operators, such as if
     * and command, will pass true to the lhs, and false to rhs. The initiating
     * call should be with genericpred false for queries, and false when adding
     * terms to the database.
     * 
     * @param genericpred
     *            The value to set generic_predicate to.
     */
    abstract public void setGeneric(boolean genericpred);

    public int getPosition() {
	return token.getPosition();
    };

    public int getLine() {
	return token.getLine();
    };

    public int getCharPos() {
	return token.getCharPos();
    };

    public String toString() {
	return getClass().toString() + ": " + token.toString();
    };
};
