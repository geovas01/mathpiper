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
 */ //}}}
// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
package org.mathpiper.io;

public class InputStatus {

    private String iSourceName;
    private int iLineNumber;
    private int iLineIndex;
    
    private InputStatus() {
    }

    public InputStatus(String aSourceName) {
        iSourceName = aSourceName;
        iLineNumber = 1;
        iLineIndex = 0;
    }


    public InputStatus(InputStatus aPreviousStatus) {
        iSourceName = aPreviousStatus.iSourceName;
        iLineNumber = aPreviousStatus.iLineNumber;
        iLineIndex = aPreviousStatus.iLineIndex;
        //System.out.println("InputStatus construct to "+iSourceName);
    }


    public void setTo(String aSourceName) {
        //System.out.println("InputStatus set to "+aFileName);

        //functionLoadSequence.add(new InputStatus(this));
        
        iSourceName = aSourceName;
        iLineNumber = 1;
        iLineIndex = 0;
    }


    public void restoreFrom(InputStatus aPreviousStatus) {
        iSourceName = aPreviousStatus.iSourceName;
        iLineNumber = aPreviousStatus.iLineNumber;
        iLineIndex = aPreviousStatus.iLineIndex;
        //System.out.println("InputStatus restore to "+iSourceName);

    }


    public int getLineNumber() {
        return iLineNumber;
    }


    public String getSourceName() {
        return iSourceName;
    }


    public int getLineIndex() {
        return iLineIndex;
    }


    public void nextLine() {
        iLineNumber++;
        iLineIndex = 0;
    }


    public void nextCharacter() {
        iLineIndex++;
    }


    /*public String toString()
    {
        int index = 1;
        StringBuilder stringBuilder = new StringBuilder();

        Iterator iterator = functionLoadSequence.iterator();

        while(iterator.hasNext())
        {
            InputStatus inputStatus = (InputStatus) iterator.next();
            stringBuilder.append("Index: " + index++ + ", Filename: " + inputStatus.getFileName() + ", Line Number: " + inputStatus.getLineNumber() + ", Line Index: " + inputStatus.getLineIndex() + "\n");
        }

        stringBuilder.append("Index: " + index++ + ", Filename: " + iSourceName + ", Line Number: " + iLineNumber + ", Line Index: " + iLineIndex + "\n");

        return stringBuilder.toString();

    }
     */

};
