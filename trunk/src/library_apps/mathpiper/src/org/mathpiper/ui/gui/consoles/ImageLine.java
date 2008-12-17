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

package org.mathpiper.ui.gui.consoles;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

    class ImageLine extends MathOutputLine
    {
        Color bkColor = new Color(255, 255, 255); //TODO:tk:This variable was originally in ConsoleApplet.
        ImageLine(Image aImage, Applet aApplet)
        {
            iImage = aImage;
            iApplet = aApplet;
        }

        public void draw(Graphics g, int x, int y)
        {
            if (iImage != null)
            {
                Dimension d = iApplet.getSize();
                g.drawImage(iImage, (d.width - iImage.getWidth(iApplet)) / 2, y, bkColor, iApplet);
            }
        }

        public int height(Graphics g)
        {
            return iImage.getHeight(iApplet);
        }
        Image iImage;
        Applet iApplet;
    }
