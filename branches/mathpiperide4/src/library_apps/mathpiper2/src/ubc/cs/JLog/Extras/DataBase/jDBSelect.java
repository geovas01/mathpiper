/*
    This file is part of JLog.

    Created by Glendon Holst for Alan Mackworth and the 
    "Computational Intelligence: A Logical Approach" text.
    
    Copyright 1998, 2000, 2002, 2008 by University of British Columbia, 
    Alan Mackworth, Glendon Holst, et. al.
    
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
//	DBSelect
//#########################################################################

package ubc.cs.JLog.Extras.DataBase;

import java.io.*;
import java.util.*;
import java.sql.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Builtins.*;
import ubc.cs.JLog.Terms.Goals.*;

public class jDBSelect extends jBinaryBuiltinPredicate {
    public jDBSelect(jTerm l, jTerm r) {
	super(l, r, TYPE_BUILTINPREDICATE);
    };

    public String getName() {
	return "db_select";
    };

    public boolean prove(jBinaryBuiltinPredicateGoal bg) {
	jTerm sql = bg.term1.getTerm();
	jTerm result = bg.term2.getTerm();
	String query = null;

	if (sql.type == iType.TYPE_VARIABLE)
	    throw new RuntimeException("Query is unbound");

	query = sql.toString();

	try {
	    Statement stmt = jDBConnect.getConnection().createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int columns = rsmd.getColumnCount();
	    jTerm tmp = null;
	    jListPair prev = null;

	    while (rs.next()) {
		jTerm tmpR = null, tR;
		jListPair prevR = null;

		for (int i = 1; i <= columns; i++) {
		    switch (rsmd.getColumnType(i)) {
		    case Types.BIGINT:
		    case Types.SMALLINT:
		    case Types.TINYINT:
		    case Types.INTEGER:
			tR = new jInteger(rs.getInt(i));
			break;
		    case Types.REAL:
		    case Types.FLOAT:
		    case Types.DOUBLE:
		    case Types.NUMERIC:
		    case Types.DECIMAL:
			tR = new jReal(rs.getFloat(i));
			break;
		    default:
			tR = new jAtom(rs.getString(i));
		    }

		    if (prevR == null)
			tmpR = prevR = new jListPair(tR, null);
		    else {
			jListPair nlst = new jListPair(tR, null);

			prevR.setTail(nlst);
			prevR = nlst;
		    }
		}

		if (prevR != null)
		    prevR.setTail(jNullList.NULL_LIST);
		else
		    tmpR = jNullList.NULL_LIST;

		if (prev == null)
		    tmp = prev = new jListPair(tmpR, null);
		else {
		    jListPair nlst = new jListPair(tmpR, null);

		    prev.setTail(nlst);
		    prev = nlst;
		}
	    }

	    if (prev != null)
		prev.setTail(jNullList.NULL_LIST);
	    else
		tmp = jNullList.NULL_LIST;

	    return (result.unify(tmp, bg.unified));
	} catch (Exception ex) {
	    return false;
	}
    };

    public jBinaryBuiltinPredicate duplicate(jTerm l, jTerm r) {
	return new jDBSelect(l, r);
    };
}
