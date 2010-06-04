/* CharBox.java
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

import java.awt.Font;
import java.awt.Graphics2D;

/**
 * A box representing a single character.
 */
class CharBox extends Box {

   private final CharFont cf;

   private final Font font;

   /**
    * Create a new CharBox that will represent the character defined by the given
    * Char-object.
    * 
    * @param c a Char-object containing the character's font information.
    */
   public CharBox(Char c) {
      cf = c.getCharFont();
      font = c.getFont();
      width = c.getWidth();
      height = c.getHeight();
      depth = c.getDepth();
   }

   public void draw(Graphics2D g2, float x, float y) {
      // copy
      Font f = g2.getFont();

      g2.setFont(font);
      g2.drawString(Character.toString(cf.c), x, y);

      // restore
      g2.setFont(f);
   }

   public int getLastFontId() {
      return cf.fontId;
   }
}
