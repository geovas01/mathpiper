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
//	Predefined
//#########################################################################

package ubc.cs.JLog.Terms;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Parser.*;

/**
 * Abstract class for creating any non-builtin predefined predicates needed by
 * the <code>jKnowledgeBase</code> and registering any operator and predicate
 * entries with their corresponding registries for use by the parser. The
 * <code>register</code> method for subclasses in a JLog library is
 * automatically invoked, as neccessary.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
abstract public class jPredefined {
    private jKnowledgeBase database;
    private pPredicateRegistry predicates;
    private pOperatorRegistry operators;
    private String library;

    public jPredefined(jPrologServices ps, String lib) {
	database = ps.getKnowledgeBase();
	predicates = ps.getPredicateRegistry();
	operators = ps.getOperatorRegistry();
	library = lib;
    };

    abstract public void register();

    public String getLibrary() {
	return library;
    };

    protected void addPredicate(pPredicateEntry pe) {
	pe.setLibrary(library);

	predicates.addPredicate(pe);
    };

    protected void addOperator(pOperatorEntry oe) {
	oe.setLibrary(library);

	operators.addOperator(oe);
    };

    protected void addRuleDefinitions(jRuleDefinitions rds) {
	rds.setLibrary(library);

	database.addRuleDefinitions(rds);
    };

    protected void addRules(jRule[] r) {
	if (r.length > 0) {
	    jRuleDefinitions rds = new jRuleDefinitions(r[0].getName(),
		    r[0].getArity());
	    int i;

	    for (i = 0; i < r.length; i++)
		rds.addRule(r[i]);

	    rds.setLibrary(library);

	    database.addRuleDefinitions(rds);
	}
    };

    protected void consultDatabase() {
	database.consult();
    }
};
