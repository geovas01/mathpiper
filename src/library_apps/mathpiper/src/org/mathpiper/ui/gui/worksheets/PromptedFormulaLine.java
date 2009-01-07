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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

    class PromptedFormulaLine extends MathOutputLine
    {

        PromptedFormulaLine(int aIndent, String aPrompt, Font aPromptFont, Color aPromptColor, String aLine)
        {
            iIndent = aIndent;
            iPrompt = aPrompt;
            iPromptFont = aPromptFont;
            iPromptColor = aPromptColor;

            TexParser parser = new TexParser();
            expression = parser.parse(aLine);
        }
        SBox expression;

        public void draw(Graphics g, int x, int y)
        {
            int hgt = height(g);
            {
                g.setColor(iPromptColor);
                g.setFont(iPromptFont);
                FontMetrics fontMetrics = g.getFontMetrics();
                g.drawString(iPrompt, x, y + fontMetrics.getAscent() + (hgt - fontMetrics.getHeight()) / 2);
            }

            g.setColor(Color.black);
            GraphicsPrimitives gp = new GraphicsPrimitives(g);
            gp.setLineThickness(0);
            expression.calculatePositions(gp, 3, new java.awt.Point(x + iIndent, y + expression.getCalculatedAscent() + 10));
            expression.render(gp);
        }

        public int height(Graphics g)
        {
            if (height == -1)
            {
                GraphicsPrimitives gp = new GraphicsPrimitives(g);
                expression.calculatePositions(gp, 3, new java.awt.Point(0, 0));
                height = expression.getDimension().height + 20;
            }
            return height;
        }
        int height = -1;
        int iIndent;
        private String iPrompt;
        private Font iPromptFont;
        private Color iPromptColor;
    }

