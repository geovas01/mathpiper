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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

class PromptedStringLine extends MOutputLine {

    PromptedStringLine(int aIndent, String aPrompt, String aText, Font aPromptFont, Font aFont, Color aPromptColor, Color aColor) {
        iIndent = aIndent;
        iPrompt = aPrompt;
        iText = aText;
        iPromptFont = aPromptFont;
        iFont = aFont;
        iPromptColor = aPromptColor;
        iColor = aColor;
    }

    public void draw(Graphics g, int x, int y) {
        {
            g.setColor(iPromptColor);
            g.setFont(iPromptFont);
            FontMetrics fontMetrics = g.getFontMetrics();
            g.drawString(iPrompt, x, y + fontMetrics.getAscent());
            if (iIndent != 0) {
                x += iIndent;
            } else {
                x += fontMetrics.stringWidth(iPrompt);
            }
        }
        {
            g.setColor(iColor);
            g.setFont(iFont);
            FontMetrics fontMetrics = g.getFontMetrics();
            g.drawString(iText, x, y + fontMetrics.getAscent());
        }
    }

    public int height(Graphics g) {
        g.setFont(iFont);
        FontMetrics fontMetrics = g.getFontMetrics();
        return fontMetrics.getHeight();
    }
    int iIndent;
    private String iPrompt;
    private String iText;
    private Font iPromptFont;
    private Font iFont;
    private Color iPromptColor;
    private Color iColor;
}
