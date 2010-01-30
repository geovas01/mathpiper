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
package org.mathpiper.ui.gui.worksheets.mathoutputlines;

import org.mathpiper.ui.gui.worksheets.*;
import org.mathpiper.ui.gui.worksheets.symbolboxes.ScaledGraphics;
import org.mathpiper.ui.gui.worksheets.symbolboxes.SBox;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class PromptedFormulaLine extends MathOutputLine {

    SBox sBoxExpression;

    public PromptedFormulaLine(int aIndent, String aPrompt, Font aPromptFont, Color aPromptColor, String aLine) {
        iIndent = aIndent;
        iPrompt = aPrompt;
        iPromptFont = aPromptFont;
        iPromptColor = aPromptColor;

        TexParser parser = new TexParser();
        sBoxExpression = parser.parse(aLine);
    }

    public void draw(Graphics g, int x, int y) {
        int hgt = height(g);
        {
            g.setColor(iPromptColor);
            g.setFont(iPromptFont);
            FontMetrics fontMetrics = g.getFontMetrics();
            g.drawString(iPrompt, x, y + fontMetrics.getAscent() + (hgt - fontMetrics.getHeight()) / 2);
        }

        g.setColor(Color.black);
        ScaledGraphics sg = new ScaledGraphics(g);
        sg.setLineThickness(0);
        sBoxExpression.calculatePositions(sg, 3, new java.awt.Point(x + iIndent, (int) (y + sBoxExpression.getCalculatedAscent() + 10)));
        sBoxExpression.render(sg);
    }

    public int height(Graphics g) {
        if (height == -1) {
            ScaledGraphics sg = new ScaledGraphics(g);
            sBoxExpression.calculatePositions(sg, 3, new java.awt.Point(0, 0));
            height = sBoxExpression.getDimension().height + 20;
        }
        return height;
    }
    int height = -1;
    int iIndent;
    private String iPrompt;
    private Font iPromptFont;
    private Color iPromptColor;
}

