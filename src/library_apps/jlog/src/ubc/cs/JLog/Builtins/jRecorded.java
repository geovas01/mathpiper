/*
    This file is part of JLog.

    Created by Glendon Holst for Alan Mackworth and the 
    "Computational Intelligence: A Logical Approach" text.
    
    Copyright 2013 by Ted Kosan.
    
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


package ubc.cs.JLog.Builtins;

import java.util.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Builtins.Goals.*;

/*
 * Todo:tk:this is an incomplete version of recorded. It does not yet support multiple rules for each key.
 * See http://www.franz.com/support/documentation/9.0/doc/prolog.html.
 */

public class jRecorded extends jTrinaryBuiltinPredicate {

	public jRecorded(jTerm t1, jTerm t2, jTerm t3) {
		super(t1, t2, t3, TYPE_BUILTINPREDICATE);
	};

	public String getName() {
		return "recorded";
	};

	public final boolean prove(jRecordedGoal jredg, jPrologServices prolog) {
		jTerm t1, t2, t3;

		t1 = jredg.term1.getTerm();
		t2 = jredg.term2.getTerm();
		t3 = jredg.term3.getTerm();

		Map<String, jTerm> recordMap = prolog.getRecordMap();
		
		String name = t1.getName();
		
		int arity = 0;
		
		if(t1 instanceof jPredicate)
		{
			jPredicate predicate = (jPredicate) t1;
			
			arity = predicate.getArity();
		}
		
		Object object = recordMap.get(name + "/" + arity);
		
		if(object == null || !(object instanceof jTerm))
		{
			return false;
		}
		
		jTerm valueTerm = (jTerm) object;
		
		valueTerm.unify(t2, new jUnifiedVector());
		
		t1.unify(t3,new jUnifiedVector());
		

	    return true;

	};

	public void addGoals(jGoal g, jVariable[] vars, iGoalStack goals) {
		goals.push(new jRecordedGoal(this, term1.duplicate(vars), term2
				.duplicate(vars), term3.duplicate(vars)));
	};

	public void addGoals(jGoal g, iGoalStack goals) {
		goals.push(new jRecordedGoal(this, term1, term2, term3));
	};

	public jTrinaryBuiltinPredicate duplicate(jTerm t1, jTerm t2, jTerm t3) {
		return new jRecorded(t1, t2, t3);
	};
};
