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
//	pTokenizerTable
//#########################################################################

package ubc.cs.JLog.Parser;

import java.io.*;
import java.util.*;

/**
 * Table for mapping input values to categories. Used to process the next
 * sequence of input characters that map to the same value.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
class pTokenizerTable {
    protected int[] table;

    public final static int TOKEN_UNKNOWN = 0;
    public final static int TOKEN_EOL = 1;

    // token numbers equal or higher are treated like single characters
    public final static int TOKEN_SINGLE = 0xFF00;

    public pTokenizerTable() {
	table = new int[256];

	resetSyntax();
    };

    public void resetSyntax() {
	int i, max = table.length;

	for (i = 0; i < max; i++)
	    table[i] = TOKEN_UNKNOWN;

	table[10] = TOKEN_EOL;
	table[13] = TOKEN_EOL;
    };

    public void setTokenType(int start, int end, int type) {
	int i;

	for (i = start; i <= end; i++)
	    table[i] = type;
    };

    public void setTokenType(int pos, int type) {
	table[pos] = type;
    };

    // for use by Tokenize classes to read array only.
    public int[] getTokenTable() {
	return table;
    };
};
