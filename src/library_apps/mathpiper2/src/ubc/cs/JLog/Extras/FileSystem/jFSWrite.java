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
//	FSWrite
//#########################################################################

package ubc.cs.JLog.Extras.FileSystem;

import java.io.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Builtins.*;
import ubc.cs.JLog.Terms.Goals.*;

public class jFSWrite extends jTrinaryBuiltinPredicate {
    public jFSWrite(jTerm t1, jTerm t2, jTerm t3) {
	super(t1, t2, t3, TYPE_BUILTINPREDICATE);
    };

    public String getName() {
	return "fs_write";
    };

    public boolean prove(jTrinaryBuiltinPredicateGoal tg) {
	jTerm t1 = tg.term1.getTerm();
	jTerm t2 = tg.term2.getTerm();
	jTerm t3 = tg.term3.getTerm();
	String fileName;
	jListPair data;
	boolean append = "append".equals(t3.toString().toLowerCase());

	if (t1 instanceof jVariable) {
	    if (((jVariable) t1).isBound()) {
		fileName = ((jVariable) t1).toString();
	    } else {
		throw new RuntimeException("Filename variable is unbound");
	    }
	} else {
	    fileName = t1.toString();
	}

	if (t2 instanceof jVariable) {
	    if (((jVariable) t2).isBound()) {
		jTerm tmp = ((jVariable) t2).getTerm();

		if (tmp instanceof jListPair) {
		    data = (jListPair) tmp;
		} else {
		    throw new RuntimeException(
			    "Write data variable is not a list");
		}
	    } else {
		throw new RuntimeException("Write data variable is unbound");
	    }
	} else if (t2 instanceof jListPair) {
	    data = (jListPair) t2;
	} else {
	    throw new RuntimeException("Write data is not a list");
	}

	{
	    jTerm iter = data;
	    jListPair tmp;
	    String line;

	    try {
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,
			append));

		while (iter instanceof jListPair) {
		    tmp = (jListPair) iter;
		    line = tmp.getHead().toString();
		    if (tmp.getHead() instanceof jNullList)
			line = "";

		    bw.write(line);
		    bw.newLine();
		    iter = tmp.getTail();
		}

		bw.close();
	    } catch (IOException ioex) {
		throw new RuntimeException(ioex.getMessage());
	    }
	}

	return true;
    };

    protected jTrinaryBuiltinPredicate duplicate(jTerm t1, jTerm t2, jTerm t3) {
	return new jFSWrite(t1, t2, t3);
    };
}
