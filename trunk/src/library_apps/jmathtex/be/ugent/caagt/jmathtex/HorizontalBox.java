/* HorizontalBox.java
 * =========================================================================
 * This file is part of the JMathTeX Library - http://jmathtex.sourceforge.net
 *
 * Copyright (C) 2004-2007 Universiteit Gent
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * A copy of the GNU General Public License can be found in the file
 * LICENSE.txt provided with the source distribution of this program (see
 * the META-INF directory in the source jar). This license can also be
 * found on the GNU website at http://www.gnu.org/licenses/gpl.html.
 *
 * If you did not receive a copy of the GNU General Public License along
 * with this program, contact the lead developer, or write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 *
 */

package be.ugent.caagt.jmathtex;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ListIterator;

/**
 * A box composed of a horizontal row of child boxes.
 */
class HorizontalBox extends Box {
    
    private float curPos = 0; // NOPMD
    
    public HorizontalBox(Box b, float w, int alignment) {
        float rest = w - b.getWidth();
        if (alignment == TeXConstants.ALIGN_CENTER) {
            StrutBox s = new StrutBox(rest / 2, 0, 0, 0);
            add(s);
            add(b);
            add(s);
        } else if (alignment == TeXConstants.ALIGN_LEFT) {
            add(b);
            add(new StrutBox(rest, 0, 0, 0));
        } else if (alignment == TeXConstants.ALIGN_RIGHT) {
            add(new StrutBox(rest, 0, 0, 0));
            add(b);
        }
    }
    
    public HorizontalBox(Box b) {
        add(b);
    }
    
    public HorizontalBox() {
        // basic horizontal box
    }
    
    public HorizontalBox(Color fg, Color bg) {
        super(fg, bg);
    }
    
    public void draw(Graphics2D g2, float x, float y) {
        startDraw(g2, x, y);
        float xPos = x;
        for (Box box: children) {
            box.draw(g2, xPos, y + box.shift);
            xPos += box.getWidth();
        }
        endDraw(g2);
    }
    
    public final void add(Box b) {
        recalculate(b);
        super.add(b);
    }
    
    private void recalculate(Box b) {
        curPos += b.getWidth();
        width = Math.max(width, curPos);
        height = Math.max((children.size() == 0 ? Float.NEGATIVE_INFINITY
                : height), b.height - b.shift);
        depth = Math.max(
                (children.size() == 0 ? Float.NEGATIVE_INFINITY : depth), b.depth
                + b.shift);
    }
    
    public int getLastFontId() {
        // iterate from the last child box to the first untill a font id is found
        // that's not equal to NO_FONT
        int fontId = TeXFont.NO_FONT;
        for (ListIterator it = children.listIterator(children.size()); fontId == TeXFont.NO_FONT
                && it.hasPrevious();)
            fontId = ((Box) it.previous()).getLastFontId();
        
        return fontId;
    }
}
