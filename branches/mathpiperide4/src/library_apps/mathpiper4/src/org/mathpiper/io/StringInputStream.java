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
package org.mathpiper.io;

public class StringInputStream
        extends MathPiperInputStream {

    int iCurrentPosition;
    protected String iString;


    public StringInputStream(String aString, InputStatus aStatus) {
        super(aStatus);
        iString = aString;
        iCurrentPosition = 0;
    }


    public char next()
            throws Throwable {

        if (iCurrentPosition == iString.length()) {
            return '\0';
        }

        iCurrentPosition++;
        
        iStatus.nextCharacter();

        char c = iString.charAt(iCurrentPosition - 1);

        if (c == '\n') {
            iStatus.nextLine();
        }

        return c;
    }


    public char peek()
            throws Throwable {

        if (iCurrentPosition == iString.length()) {
            return '\0';
        }

        return iString.charAt(iCurrentPosition);
    }


    public boolean endOfStream() {

        return (iCurrentPosition == iString.length());
    }


    public String startPtr() {

        return iString;
    }


    public int position() {

        return iCurrentPosition;
    }


    public void setPosition(int aPosition) {
        iCurrentPosition = aPosition;
    }

}
