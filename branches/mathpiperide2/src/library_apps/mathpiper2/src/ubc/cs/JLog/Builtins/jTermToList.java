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
//	TermToList
//#########################################################################

package ubc.cs.JLog.Builtins;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Parser.*;
import ubc.cs.JLog.Builtins.Goals.*;

public class jTermToList extends jBinaryBuiltinPredicate {
    public jTermToList(jTerm l, jTerm r) {
	super(l, r, TYPE_BUILTINPREDICATE);
    };

    public String getName() {
	return "=..";
    };

    public boolean prove(jTermToListGoal tg, pPredicateRegistry pr,
	    pOperatorRegistry or) {
	jTerm l, r;

	l = tg.lhs.getTerm();
	r = tg.rhs.getTerm();

	if (l instanceof iPredicate) {
	    jCompoundTerm ct = ((iPredicate) l).getArguments();
	    Enumeration e = ct.enumTerms();
	    jListPair head, prev;

	    head = prev = new jListPair(new jAtom(l.getName()), null);

	    while (e.hasMoreElements()) {
		jListPair next;

		prev.setTail(next = new jListPair((jTerm) e.nextElement(), null));
		prev = next;
	    }

	    prev.setTail(jNullList.NULL_LIST);

	    return r.unify(head, tg.unified);
	} else if (l instanceof jConjunctTerm) {
	    jListPair head;

	    head = new jListPair(new jAtom(l.getName()), new jListPair(
		    ((jConjunctTerm) l).getLHS(), new jListPair(
			    ((jConjunctTerm) l).getRHS(), jNullList.NULL_LIST)));

	    return r.unify(head, tg.unified);
	} else if (r instanceof jListPair) {
	    jListPair lst = (jListPair) r;
	    jTerm name = lst.getHead().getTerm();
	    jCompoundTerm ct;
	    jTerm next;

	    if (!(name instanceof iPredicate && ((iPredicate) name).getArity() == 0))
		throw new InvalidTermToListArgumentException();

	    ct = new jCompoundTerm();
	    next = lst.getTail().getTerm();

	    while (next instanceof jListPair) {
		lst = (jListPair) next;

		ct.addTerm(lst.getHead().getTerm());
		next = lst.getTail().getTerm();
	    }

	    if (!(next instanceof jNullList))
		throw new InvalidTermToListArgumentException();

	    {
		pPredicateEntry pe = pr.getPredicate(name.getName(), ct.size());
		jTerm p;

		if (pe != null) {
		    p = pe.createPredicate(ct);
		} else {
		    pOperatorEntry oe = or.getOperator(name.getName(), true);

		    if (oe != null) {
			jTerm lhs, rhs;

			if (ct.size() != 2)
			    throw new InvalidTermToListArgumentException();

			lhs = ct.elementAt(0);
			rhs = ct.elementAt(1);

			p = oe.createOperator(lhs, rhs);
		    } else
			p = new jPredicate(name.getName(), ct);
		}

		return l.unify(p, tg.unified);
	    }
	} else
	    throw new InvalidTermToListArgumentException();
    };

    public void addGoals(jGoal g, jVariable[] vars, iGoalStack goals) {
	goals.push(new jTermToListGoal(this, lhs.duplicate(vars), rhs
		.duplicate(vars)));
    };

    public void addGoals(jGoal g, iGoalStack goals) {
	goals.push(new jTermToListGoal(this, lhs, rhs));
    };

    protected jBinaryBuiltinPredicate duplicate(jTerm l, jTerm r) {
	return new jTermToList(l, r);
    };
};
