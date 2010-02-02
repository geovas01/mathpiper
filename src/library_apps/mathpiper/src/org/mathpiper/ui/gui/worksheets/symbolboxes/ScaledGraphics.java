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
package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.*;

public class ScaledGraphics {

    private double viewScale = 1.0;
    private Graphics iG = null;
    private Graphics2D iG2D = null;
    int prevGray = -1;
    int prevSetFontSize = -1;
    FontMetrics metrics = null;

    public ScaledGraphics(Graphics g) {
        iG = g;
        if (g instanceof Graphics2D) {
            iG2D = (Graphics2D) g;
        }
    }

    public void setLineThickness(double aThickness) {
        if (iG2D != null) {
            iG2D.setStroke(new BasicStroke((float) (aThickness * viewScale), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        }
    }

    public void drawLine(double x0, double y0, double x1, double y1) {
        iG.drawLine((int) (x0 * viewScale), (int) (y0 * viewScale), (int) (x1 * viewScale), (int) (y1 * viewScale));
    }


    public void drawArc(double x,double y,double width,double height,int startAngle,int arcAngle) {
        //iG.drawLine((int) (x0 * viewScale), (int) (y0 * viewScale), (int) (x1 * viewScale), (int) (y1 * viewScale));
        iG.drawArc((int) (x * viewScale), (int) (y * viewScale), (int) (width * viewScale), (int) (height * viewScale), startAngle, arcAngle);
    }

    void setGray(int aGray) {
        if (prevGray != aGray) {
            prevGray = aGray;
            iG.setColor(new Color(aGray, aGray, aGray));
        }
    }

    public void drawText(String text, double x, double y) {
        iG.drawString(text, (int) (x * viewScale), (int) (y * viewScale));
    }

    public void drawscaledText(String text, double x, double y, double scale) {
        int normalFontSize = getFontSize();

        double scaledFontSize = normalFontSize * scale;

        setFontSize((int) Math.round(scaledFontSize));

        iG.drawString(text, (int) (x * viewScale), (int) (y * viewScale));

        setFontSize(normalFontSize);

    }


    void setFontSize(int aSize) {
        int newFontSize = (int) (viewScale * aSize);
        if (prevSetFontSize != newFontSize) {
            prevSetFontSize = newFontSize;
            Font f = new Font("Verdana", Font.PLAIN, newFontSize);
            if (f != null) {
                iG.setFont(f);
                metrics = iG.getFontMetrics();
            }
        }
    }

    int getFontSize() {
        return (int) (prevSetFontSize / viewScale);
    }

    int getTextWidth(String text) {
        java.awt.geom.Rectangle2D textBoundingRectangle = metrics.getStringBounds(text, iG);
        return (int) (textBoundingRectangle.getWidth() / viewScale);
    }

    double getAscent() {
        return (metrics.getAscent() / viewScale);
    }

    double getDescent() {
        return (int) (metrics.getDescent() / viewScale);
    }

    public void setViewScale(double aViewScale) {
        viewScale = aViewScale;
    }

    public void setColor(Color color) {
        if (iG2D != null) {
            iG2D.setColor(color);
        } else if (iG != null) {
            iG.setColor(color);
        }

    }
}
