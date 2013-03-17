/* {{{ License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
//}}}
// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
package org.mathpiper.builtin.functions.optional;

import java.io.InputStreamReader;

import org.mathpiper.io.InputStatus;
import org.mathpiper.io.MathPiperInputStream;
import org.mathpiper.io.StringInputStream;

public class FileInputStream extends MathPiperInputStream {

    private String iFileName;

    private InputStreamReader inputStream;

    private StringBuilder fileStringBuilder = new StringBuilder();

    private StringInputStream stringInputStream;



    public FileInputStream(String aFileName, InputStatus aStatus) throws Throwable {
	
	super(aStatus);

	iFileName = aFileName;

	InputStreamReader inputStream = new InputStreamReader(new java.io.FileInputStream(aFileName));

	InputStreamReader stream = new InputStreamReader(new java.io.FileInputStream(aFileName));
	int c;

	while (true) {
	    c = stream.read();

	    if (c == -1)

		break;

	    fileStringBuilder.append((char) c);
	}

	stringInputStream = new StringInputStream(fileStringBuilder.toString(), aStatus);
    }



    public char next() throws Throwable {
	return stringInputStream.next();
    }



    public char peek() throws Throwable {
	return stringInputStream.peek();
    }



    public boolean endOfStream() {
	return stringInputStream.endOfStream();
    }



    public String startPtr() {
	return stringInputStream.startPtr();
    }



    public int position() {
	return stringInputStream.position();
    }



    public void setPosition(int aPosition) {
	stringInputStream.setPosition(aPosition);
    }



    public String toString() {
	return ("File: " + iStatus.getSourceName());
    }

}
