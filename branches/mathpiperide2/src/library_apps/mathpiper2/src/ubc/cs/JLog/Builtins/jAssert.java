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
//	Assert
//#########################################################################

package ubc.cs.JLog.Builtins;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Builtins.Goals.*;

public class jAssert extends jUnaryBuiltinPredicate {
    public final static boolean FIRST = false;
    public final static boolean LAST = true;

    public jAssert(jTerm t) {
	super(t, TYPE_BUILTINPREDICATE);
    };

    public String getName() {
	return "assert";
    };

    public boolean prove(jAssertGoal ag, jKnowledgeBase kb) {
	jTerm t;

	t = ag.term.getTerm();

	if (t instanceof jIf) {
	    jIf jif = (jIf) t;
	    jTerm lhs = jif.getLHS().getTerm();
	    jPredicate head = null;
	    jPredicateTerms base = null;
	    jVariableRegistry vars = new jVariableRegistry();

	    if (lhs instanceof jPredicate)
		head = (jPredicate) lhs;
	    else
		throw new InvalidAssertException(
			"Expected single predicate before if operator in assert.");

	    try {
		base = new jPredicateTerms();
		base.makePredicateTerms(jif.getRHS().getTerm());
	    } catch (PredicateExpectedException e) {
		throw new InvalidAssertException(
			"Expected predicates after if operator.");
	    }

	    return assertPredicate((jPredicate) head.copy(vars),
		    (jPredicateTerms) base.copy(vars), kb, ag.addlast);
	} else if (t instanceof jPredicate) {
	    return assertPredicate((jPredicate) t.copy(), null, kb, ag.addlast);
	} else if (t instanceof jAtom) {
	    jPredicate tp = new jPredicate((jAtom) t);

	    return assertPredicate(tp, null, kb, ag.addlast);
	} else
	    throw new InvalidAssertException(
		    "Asserted term cannot be builtin or unbound variable.");
    };

    // h and b should be copied terms. b may be null.
    protected boolean assertPredicate(jPredicate h, jPredicateTerms b,
	    jKnowledgeBase kb, boolean addlast) {
	jRuleDefinitions rds;
	jRule rule;

	rule = new jRule(h, (b == null ? new jPredicateTerms() : b));
	if ((rds = h.getCachedRuleDefinitions()) == null)
	    rds = kb.getRuleDefinitionsMatch(rule);

	if (rds == null) {
	    kb.makeRuleDefinitionDynamic(rule);
	    if (addlast)
		kb.addRuleLast(rule);
	    else
		kb.addRuleFirst(rule);
	} else if (rds instanceof jDynamicRuleDefinitions) {
	    if (addlast)
		rds.addRuleLast(rule);
	    else
		rds.addRuleFirst(rule);
	} else
	    throw new InvalidAssertException("Asserted term '" + h.getName()
		    + "/" + String.valueOf(h.getArity()) + "' must be dynamic.");

	rule.consult(kb);
	return true;
    };

    public void addGoals(jGoal g, jVariable[] vars, iGoalStack goals) {
	goals.push(new jAssertGoal(this, rhs.duplicate(vars), LAST));
    };

    public void addGoals(jGoal g, iGoalStack goals) {
	goals.push(new jAssertGoal(this, rhs, LAST));
    };

    protected jUnaryBuiltinPredicate duplicate(jTerm r) {
	return new jAssert(r);
    };
};
