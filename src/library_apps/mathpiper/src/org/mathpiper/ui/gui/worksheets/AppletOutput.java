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
package org.mathpiper.ui.gui.worksheets;

import java.io.IOException;

class AppletOutput {

    public AppletOutput(Console aApplet) {
        iApplet = aApplet;
    }
    Console iApplet;

    public void write(int c) throws IOException {
        if (c == '\n') {
            iApplet.addLineStatic(0, buffer.toString());
            buffer = new StringBuffer();
        } else {
            buffer.append((char) c);
        }
    }

    public void print(String s) {
        try {
            int i, nr;
            nr = s.length();
            for (i = 0; i < nr; i++) {
                write(s.charAt(i));
            }
        } catch (IOException e) {
        }
    }

    public void println(Exception e) {
        println(e.getMessage());
    }

    public void println(String s) {
        print(s);
        print("\n");
    }
    StringBuffer buffer = new StringBuffer();
}
