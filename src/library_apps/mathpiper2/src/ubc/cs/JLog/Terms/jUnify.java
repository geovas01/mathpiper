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
//	Unify
//#########################################################################

package ubc.cs.JLog.Terms;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.Goals.*;

public class jUnify extends jBinaryBuiltinPredicate {
    // this constructor permits the delayed setting of lhs and rhs terms. setLHS
    // and setRHS
    // must be called before any other member.
    public jUnify() {
	super(null, null, TYPE_BUILTINPREDICATE);
    };

    public jUnify(jTerm l, jTerm r) {
	super(l, r, TYPE_BUILTINPREDICATE);
    };

    public String getName() {
	return "=";
    };

    // setLHS and setRHS complement default constructor
    public void setLHS(jTerm l) {
	lhs = l;
    };

    public void setRHS(jTerm r) {
	rhs = r;
    };

    public boolean prove(jBinaryBuiltinPredicateGoal ig) {
	jTerm l, r;

	l = ig.term1.getTerm();
	r = ig.term2.getTerm();

	return l.unify(r, ig.unified);
    };

    public jBinaryBuiltinPredicate duplicate(jTerm l, jTerm r) {
	return new jUnify(l, r);
    };

    public String toString(boolean usename) {
	return lhs.toString(usename) + " " + getName() + " "
		+ rhs.toString(usename);
    };
};
