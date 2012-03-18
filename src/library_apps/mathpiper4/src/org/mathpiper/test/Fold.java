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

package org.mathpiper.test;

import java.util.HashMap;
import java.util.Map;

public class Fold {

    private int startLineNumber;
    private String type;
    private String contents;
    private Map<String, String> attributes = new HashMap();

    public Fold(int startLineNumber, String header, String contents) {

        this.startLineNumber = startLineNumber;

        scanHeader(header);

        this.contents = contents;
    }//end inner class.

    private void scanHeader(String header) {
        String[] headerParts = header.trim().split(",");

        type = headerParts[0];

        for (int x = 1; x < headerParts.length; x++) {
            headerParts[x] = headerParts[x].replaceFirst("=", ",");
            String[] headerPart = headerParts[x].split(",");
            String attributeName = headerPart[0];
            String attributeValue = headerPart[1].replace("\"", "");
            attributes.put(attributeName, attributeValue);
        }

    }//end method.

    public Map getAttributes() {
        return attributes;
    }

    public String getContents() {
        return contents;
    }

    public String getType() {
        return type;
    }

    public int getStartLineNumber()
    {
        return this.startLineNumber;
    }
    
}//end class.
