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
package org.mathpiper.ui.gui.applets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

class PromptedGraph2DLine extends MathOutputLine {

    PromptedGraph2DLine(int aIndent, String aPrompt, Font aPromptFont, Color aPromptColor, String aLine) {
        iIndent = aIndent;
        iPrompt = aPrompt;
        iPromptFont = aPromptFont;
        iPromptColor = aPromptColor;
        iGrapher = new Grapher(aLine);
    }
    Grapher iGrapher;

    public void draw(Graphics g, int x, int y) {
        iGrapher.paint(g, x, y, size);
    }

    public int height(Graphics g) {
        return size.height;
    }
    Dimension size = new Dimension(320, 240);
    int iIndent;
    private String iPrompt;
    private Font iPromptFont;
    private Color iPromptColor;
}

