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
//	pPreTokenizeStream
//#########################################################################

package ubc.cs.JLog.Parser;

import java.io.*;
import java.util.*;

/**
 * Takes an input stream and produces a stream of pre-tokens, ready for a fully
 * tokenizing parse.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
class pPreTokenizeStream {
    protected Reader reader;
    protected Stack pushback, previous_tables;
    protected pTokenizerTable current_table;

    protected int currentPosition = 0; // the number of characters into the
				       // stream

    protected boolean read_pushedback = false;
    protected int read_pushback;

    public final static int TOKEN_EOF = -1;

    public pPreTokenizeStream(Reader r) {
	reader = r;

	current_table = null;
	previous_tables = new Stack();

	pushback = new Stack();
    };

    public void useTokenizerTable(pTokenizerTable t) {
	if (current_table != null)
	    previous_tables.push(current_table);

	current_table = t;
    };

    public pTokenizerTable popTokenizerTable() {
	pTokenizerTable t = current_table;

	if (previous_tables.empty())
	    return null;

	current_table = (pTokenizerTable) previous_tables.pop();
	return t;
    };

    public void pushBackToken(pPreToken p) {
	pushback.push(p);
    };

    public pPreToken getNextToken() {
	if (!pushback.empty())
	    return (pPreToken) pushback.pop();
	else {
	    pPreToken pt;
	    int c, startpos = currentPosition;
	    StringBuffer sb;
	    boolean collectall;
	    int[] table;

	    if (current_table == null)
		throw new TokenizeStreamException(
			"There is no Tokenizing Table");

	    table = current_table.getTokenTable();

	    c = read();

	    if (c < 0)
		return new pPreToken(TOKEN_EOF, startpos);

	    if (c >= table.length || table[c] == pTokenizerTable.TOKEN_UNKNOWN)
		throw new SyntaxErrorException(
			"Unknown character at position ", startpos);

	    sb = new StringBuffer();
	    sb.append((char) c);
	    pt = new pPreToken(table[c], startpos);

	    collectall = (pt.getType() < pTokenizerTable.TOKEN_SINGLE);

	    while (collectall) {
		c = read();

		if (c < 0 || c >= table.length || table[c] != pt.getType()) {
		    pushbackRead(c);
		    break;
		}

		sb.append((char) c);
	    }

	    pt.setToken(sb.toString());
	    return pt;
	}
    };

    protected int read() {
	currentPosition++;

	if (read_pushedback) {
	    read_pushedback = false;
	    return read_pushback;
	} else {
	    try {
		return reader.read();
	    } catch (IOException e) {
		throw new TokenizeStreamException(
			"IO error occurred while reading at position ",
			currentPosition - 1, e);
	    }
	}
    };

    // this simple tokenizer only supports a single pushback
    protected void pushbackRead(int c) {
	read_pushedback = true;
	read_pushback = c;
	currentPosition--;
    };
};
