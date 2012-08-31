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
//	Name
//#########################################################################

package ubc.cs.JLog.Builtins;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Builtins.Goals.*;

public class jName extends jBinaryBuiltinPredicate {
    protected boolean prefer_atom;

    public jName(jTerm l, jTerm r) {
	this(l, r, false);
    };

    public jName(jTerm l, jTerm r, boolean pa) {
	super(l, r, TYPE_BUILTINPREDICATE);
	prefer_atom = pa;
    };

    public String getName() {
	return "name";
    };

    public boolean prove(jNameGoal ng) {
	jTerm l, r;

	l = ng.lhs.getTerm();
	r = ng.rhs.getTerm();

	if ((l instanceof iPredicate && ((iPredicate) l).getArity() == 0)
		|| (l.type == TYPE_INTEGER) || (l.type == TYPE_REAL)) {
	    String s;
	    jList il;

	    if (l.type == TYPE_REAL)
		s = String.valueOf(((jReal) l).getRealValue());
	    if (l.type == TYPE_INTEGER)
		s = String.valueOf(((jInteger) l).getIntegerValue());
	    else
		s = l.getName();

	    il = convertStringToList(s);
	    return r.unify(il, ng.unified);
	} else if (r instanceof jList) {
	    String s;
	    jTerm t;

	    s = convertListToString((jList) r);
	    t = convertStringToTerm(s, ng.prefer_atom);

	    return l.unify(t, ng.unified);
	} else
	    throw new InvalidNameArgumentException();
    };

    protected jTerm convertStringToTerm(String s, boolean pa) {
	if (pa)
	    return new jAtom(s);

	// try to create an integer first
	try {
	    return new jInteger(Integer.parseInt(s));
	} catch (NumberFormatException e) {
	    // do nothing
	}
	// try to create a real next
	try {
	    return new jReal(Float.parseFloat(s));
	} catch (NumberFormatException e) {
	    return new jAtom(s);
	}
    };

    protected String convertListToString(jList i) {
	StringBuffer sb = new StringBuffer();
	jTerm t = i;

	while (t.type == TYPE_LIST) {
	    jListPair l = (jListPair) t;
	    jTerm it;

	    it = l.getHead().getTerm();

	    if (it.type != TYPE_INTEGER)
		throw new InvalidNameArgumentException();

	    sb.append((char) ((jInteger) it).getIntegerValue());

	    t = l.getTail().getTerm();
	}

	return sb.toString();
    };

    protected jList convertStringToList(String s) {
	int i, max = s.length();
	jList head = null;
	jListPair prev = null;

	for (i = 0; i < max; i++) {
	    jListPair next;

	    next = new jListPair(new jInteger((int) s.charAt(i)), null);

	    if (prev == null)
		head = next;
	    else
		prev.setTail(next);

	    prev = next;
	}

	if (prev == null)
	    head = jNullList.NULL_LIST;
	else
	    prev.setTail(jNullList.NULL_LIST);

	return head;
    };

    public void addGoals(jGoal g, jVariable[] vars, iGoalStack goals) {
	goals.push(new jNameGoal(this, lhs.duplicate(vars),
		rhs.duplicate(vars), prefer_atom));
    };

    public void addGoals(jGoal g, iGoalStack goals) {
	goals.push(new jNameGoal(this, lhs, rhs, prefer_atom));
    };

    protected jBinaryBuiltinPredicate duplicate(jTerm lhs, jTerm rhs) {
	return new jName(lhs, rhs, prefer_atom);
    };
};
