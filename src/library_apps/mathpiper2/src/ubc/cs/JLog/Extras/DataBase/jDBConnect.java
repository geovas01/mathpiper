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
//	DBConnect
//#########################################################################

package ubc.cs.JLog.Extras.DataBase;

import java.util.*;
import java.sql.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Builtins.*;
import ubc.cs.JLog.Terms.Goals.*;

public class jDBConnect extends jQuadaryBuiltinPredicate {
    private static Connection connection = null;

    public static Connection getConnection() {
	return connection;
    };

    public static void closeConnection() {
	try {
	    if (connection != null)
		connection.close();
	} catch (SQLException sqlex) {
	}
	connection = null;
    };

    public jDBConnect(jTerm driver, jTerm url, jTerm user, jTerm pwd) {
	super(driver, url, user, pwd, TYPE_BUILTINPREDICATE);
    };

    public String getName() {
	return "db_connect";
    };

    public boolean prove(jQuadaryBuiltinPredicateGoal qg) {
	String driver = qg.term1.getTerm().toString();
	String url = qg.term2.getTerm().toString();
	String user = qg.term3.getTerm().toString();
	String pwd = qg.term4.getTerm().toString();

	// any existing connection is forcibly closed
	closeConnection();

	try {
	    Class.forName(driver).newInstance();
	    connection = DriverManager.getConnection(url, user, pwd);
	} catch (Exception ex) {
	    System.err.println(ex.getMessage());
	}

	return (connection != null);
    };

    public jQuadaryBuiltinPredicate duplicate(jTerm t1, jTerm t2, jTerm t3,
	    jTerm t4) {
	return new jDBConnect(t1, t2, t3, t4);
    };
};
