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
//	OperatorRegistry
//#########################################################################

package ubc.cs.JLog.Parser;

import java.lang.*;
import java.util.*;

/**
 * The registery for <code>pOperatorEntry</code>s. This is like a translation
 * table for operators, where each entry is a mapping object that takes parsing
 * packets, and produces prolog terms.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public class pOperatorRegistry {
    protected Hashtable operators;

    public pOperatorRegistry() {
	operators = new Hashtable();
    };

    public void addOperator(pOperatorEntry oe) {
	operators.put(getKeyString(oe.getName(), oe.hasLHS()), oe);
    };

    public pOperatorEntry getOperator(String name, boolean lhs_op) {
	return (pOperatorEntry) operators.get(getKeyString(name, lhs_op));
    };

    public void clearOperators() {
	operators = new Hashtable();
    };

    public Enumeration enumOperators() {
	return operators.elements();
    };

    protected final String getKeyString(String name, boolean lhs_op) {
	return name + (lhs_op ? "/LHS" : "/N");
    };
};
