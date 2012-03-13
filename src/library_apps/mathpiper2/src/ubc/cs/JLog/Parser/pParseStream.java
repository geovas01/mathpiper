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
//	pParseStream
//#########################################################################

package ubc.cs.JLog.Parser;

import java.io.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Builtins.*;

/**
 * The main parsing class that converts packets into their correspoding prolog
 * terms. Used to process user queries, and consult a database. Directly or
 * indirectly uses the <code>pPreTokenizeStream</code>,
 * <code>pTokenizeStream</code>, and <code>pPreParseStream</code>.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public class pParseStream {
    protected pPreParseStream parser;
    protected jKnowledgeBase database;

    public pParseStream(String s, jKnowledgeBase kb, pPredicateRegistry pr,
	    pOperatorRegistry or) {
	parser = new pPreParseStream(s, pr, or);
	database = kb;
    };

    public pParseStream(Reader r, jKnowledgeBase kb, pPredicateRegistry pr,
	    pOperatorRegistry or) {
	parser = new pPreParseStream(r, pr, or);
	database = kb;
    };

    /**
     * Parse the source stream as a query, producing a term representation.
     * 
     * @return The <code>jPredicateTerms</code> instance representing the parsed
     *         query string.
     */
    public jPredicateTerms parseQuery() {
	return parseQuery(null);
    };

    /**
     * Parse the source stream as a query, producing a term representation.
     * 
     * @param assignments
     *            A <code>Hashtable</code> representing variable names as keys,
     *            and their corresponding <code>jTerm</code> value. The keys
     *            must String types, and be valid variable names. Values must be
     *            <code>jTerm</code> objects (or sub-classes)
     * @return The <code>jPredicateTerms</code> instance representing the parsed
     *         query string.
     */
    public jPredicateTerms parseQuery(Hashtable assignments) {
	pPacket pp;

	if ((pp = parser.parse()) != null) {
	    pVariableRegistry vars = new pVariableRegistry();
	    pTermToPacketHashtable phash = new pTermToPacketHashtable();
	    jTerm term;
	    jPredicateTerms base;

	    // add predefined variables to variable registry
	    if (assignments != null) {
		Enumeration e = assignments.keys();

		while (e.hasMoreElements()) {
		    String s = (String) e.nextElement();
		    jTerm t = (jTerm) assignments.get(s);
		    jVariable v = vars.getVariable(s);

		    v.setBinding(t);
		}
	    }

	    // ensure that query terms use non-generic predicates
	    pp.setGeneric(false);

	    term = pp.getTerm(vars, phash);

	    if (term instanceof jCommand)
		term = ((jCommand) term).getRHS();

	    try {
		base = new jPredicateTerms();
		base.makePredicateTerms(term);
	    } catch (PredicateExpectedException e) {
		pToken pt = phash.getToken(e.getTerm(), pp);

		throw new SyntaxErrorException("Expected predicate at ",
			pt.getPosition(), pt.getLine(), pt.getCharPos());
	    }

	    return base;
	}
	return null;
    };

    public jTerm parseTerm() {
	pPacket pp;

	if ((pp = parser.parse()) != null) {
	    pVariableRegistry vars = new pVariableRegistry();
	    pTermToPacketHashtable phash = new pTermToPacketHashtable();
	    jTerm term;

	    // ensure that terms use non-generic predicates. commands and if
	    // operators
	    // ensure that predicates to their left are generic and to the right
	    // are non-generic.
	    pp.setGeneric(false);

	    term = pp.getTerm(vars, phash);

	    if (term instanceof jCommand)
		term = ((jCommand) term).getRHS();

	    return term;
	}
	return null;
    };

    public void parseSource() {
	pPacket pp;
	pUpdateDatabaseRules dhash = new pUpdateDatabaseRules(database);

	while ((pp = parser.parse()) != null) {
	    pVariableRegistry vars = new pVariableRegistry();
	    pTermToPacketHashtable phash = new pTermToPacketHashtable();
	    jTerm term;
	    jPredicate head;
	    jPredicateTerms base;

	    // ensure that head predicates use generic predicates. commands and
	    // if operators
	    // ensure that predicates to their right use non-generic predicates.
	    pp.setGeneric(true);

	    term = pp.getTerm(vars, phash);

	    if (term instanceof jIf) {
		jIf jif = (jIf) term;
		jTerm lhs = jif.getLHS();

		if (lhs instanceof jPredicate)
		    head = (jPredicate) lhs;
		else {
		    pToken pt = phash.getToken(term, pp);

		    throw new SyntaxErrorException(
			    "Expected single predicate before if operator at ",
			    pt.getPosition(), pt.getLine(), pt.getCharPos());
		}

		try {
		    base = new jPredicateTerms();
		    base.makePredicateTerms(jif.getRHS());
		} catch (PredicateExpectedException e) {
		    pToken pt = phash.getToken(e.getTerm(), pp);

		    throw new SyntaxErrorException("Expected predicate at ",
			    pt.getPosition(), pt.getLine(), pt.getCharPos());
		}

		dhash.addRule(new jRule(head, base));
	    } else if (term instanceof jCommand) {
		jCommand command = (jCommand) term;

		try {
		    base = new jPredicateTerms();
		    base.makePredicateTerms(command.getRHS());
		} catch (PredicateExpectedException e) {
		    pToken pt = phash.getToken(e.getTerm(), pp);

		    throw new SyntaxErrorException("Expected predicate at ",
			    pt.getPosition(), pt.getLine(), pt.getCharPos());
		}

		query(base);
	    } else if (term instanceof jDCG) {
		jDCG dcg = (jDCG) term;

		try {
		    dhash.addRule(dcg.makeDCGRule());
		} catch (PredicateExpectedException e) {
		    pToken pt = phash.getToken(e.getTerm(), pp);

		    throw new SyntaxErrorException("Expected predicate at ",
			    pt.getPosition(), pt.getLine(), pt.getCharPos());
		}
	    } else if (term instanceof jPredicate) {
		head = (jPredicate) term;
		base = new jPredicateTerms();

		dhash.addRule(new jRule(head, base));
	    } else {
		pToken pt = phash.getToken(term, pp);

		throw new SyntaxErrorException(
			"Expected predicate or rule at ", pt.getPosition(),
			pt.getLine(), pt.getCharPos());
	    }
	}
    };

    protected void query(jPredicateTerms q) {
	jProver prover;

	prover = new jProver(database);
	prover.prove(q);
    };
};
