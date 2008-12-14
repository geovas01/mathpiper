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

class StringLine extends MOutputLine {

    StringLine(String aText, Font aFont, Color aColor) {
        iText = aText;
        iFont = aFont;
        iColor = aColor;
    }

    public void draw(Graphics g, int x, int y) {
        g.setColor(iColor);
        g.setFont(iFont);
        FontMetrics fontMetrics = g.getFontMetrics();
        g.drawString(iText, x, y + fontMetrics.getHeight());
    }

    public int height(Graphics g) {
        g.setFont(iFont);
        FontMetrics fontMetrics = g.getFontMetrics();
        return fontMetrics.getHeight();
    }
    private String iText;
    private Font iFont;
    private Color iColor;
}
