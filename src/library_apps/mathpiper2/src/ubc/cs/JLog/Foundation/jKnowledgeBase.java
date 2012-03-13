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
//	Knowlege Base
//#########################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;

/**
 * The Prolog rule definitions database.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public class jKnowledgeBase {
    protected Hashtable definitions;

    public jKnowledgeBase() {
	definitions = new Hashtable();
    };

    /**
     * Use this method to set all the rules for a particular predicate
     * definition at once. Unlike the separate <code>addRule</code> methods,
     * this method only updates the rule definition if it does not already
     * exist. It is an error if the definition already exists and belongs to a
     * different library.
     * 
     * @param rds
     *            The rule definition to add.
     */
    public void addRuleDefinitions(jRuleDefinitions rds) {
	jRuleDefinitions rdsmatch;

	if ((rdsmatch = getRuleDefinitionsMatch(rds)) == null)
	    definitions.put(getKeyString(rds), rds);
	else if (!rdsmatch.sameLibrary(rds.getLibrary())) {
	    String err;

	    err = "Rules "
		    + rdsmatch.getName()
		    + "/"
		    + rdsmatch.getArity()
		    + " already exists"
		    + (rdsmatch.getLibrary() != null ? " in library "
			    + rdsmatch.getLibrary() : "");

	    throw new UnmatchedRuleException(err);
	}
    };

    public void addRule(jRule r) {
	jRuleDefinitions rds;

	if ((rds = getRuleDefinitionsMatch(r)) == null)
	    definitions.put(getKeyString(r),
		    rds = new jRuleDefinitions(r.getName(), r.getArity()));

	rds.addRule(r);
    };

    public void addRuleFirst(jRule r) {
	jRuleDefinitions rds;

	if ((rds = getRuleDefinitionsMatch(r)) == null)
	    definitions.put(getKeyString(r),
		    rds = new jRuleDefinitions(r.getName(), r.getArity()));

	rds.addRuleFirst(r);
    };

    public void addRuleLast(jRule r) {
	jRuleDefinitions rds;

	if ((rds = getRuleDefinitionsMatch(r)) == null)
	    definitions.put(getKeyString(r),
		    rds = new jRuleDefinitions(r.getName(), r.getArity()));

	rds.addRuleLast(r);
    };

    public void removeRule(jRule r) {
	jRuleDefinitions rds;

	if ((rds = getRuleDefinitionsMatch(r)) != null)
	    rds.removeRule(r);
    };

    public void clearRules() {
	definitions = new Hashtable();
    };

    public Enumeration enumDefinitions() {
	return definitions.elements();
    };

    /**
     * Convert a set of rules matching the given name and arity to dynamic
     * rules.
     * 
     * @param r
     *            The name and arity of the rule set to convert.
     */
    public synchronized void makeRuleDefinitionDynamic(iNameArity r) {
	jRuleDefinitions rds;

	rds = getRuleDefinitionsMatch(r);

	if (rds == null)
	    definitions.put(getKeyString(r),
		    new jDynamicRuleDefinitions(r.getName(), r.getArity()));
	else if (!(rds instanceof jDynamicRuleDefinitions))
	    definitions.put(getKeyString(r), new jDynamicRuleDefinitions(rds));
    };

    /**
     * Access the set of rules matching the given name and arity.
     * 
     * @param r
     *            The name and arity of the rule set to retrieve.
     * 
     * @return The <code>jRuleDefinitions</code> rule set, or <code>null</code>
     *         if it doesn't exist.
     */
    public jRuleDefinitions getRuleDefinitionsMatch(iNameArity r) {
	return (jRuleDefinitions) definitions.get(getKeyString(r));
    };

    public void consult() {
	Enumeration e;
	jRuleDefinitions rds;

	e = enumDefinitions();
	while (e.hasMoreElements()) {
	    rds = (jRuleDefinitions) e.nextElement();
	    rds.consult(this);
	}
    };

    protected String getKeyString(iNameArity r) {
	return r.getName() + "/" + Integer.toString(r.getArity());
    };
};
