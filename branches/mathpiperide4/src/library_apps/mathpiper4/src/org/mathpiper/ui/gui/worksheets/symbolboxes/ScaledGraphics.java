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
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

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

    public void drawRectangle(double x, double y, double width, double height) {
        iG.drawRect((int) (x * viewScale), (int) (y * viewScale), (int) (width * viewScale), (int) (height * viewScale));
    }

    public void setGray(int aGray) {
        if (prevGray != aGray) {
            prevGray = aGray;
            iG.setColor(new Color(aGray, aGray, aGray));
        }
    }

    public void drawText(String text, double x, double y) {
        iG.drawString(text, (int) (x * viewScale), (int) (y * viewScale));
    }

    public void drawscaledText(String text, double x, double y, double scale) {
        double normalFontSize = getFontSize();

        double scaledFontSize = normalFontSize * scale;

        setFontSize(scaledFontSize);

        iG.drawString(text, (int) (x * viewScale), (int) (y * viewScale));

        setFontSize(normalFontSize);

    }
    
    
    public void drawLatex(TeXFormula texFormula, int x, int y)
    {
	//Obtain image example.
	//http://forge.scilab.org/index.php/p/jlatexmath/source/tree/8b1b0250b95fe80c0e245db2671a817f299ecaf7/examples/Basic/Example1.java

	TeXIcon icon = texFormula.createTeXIcon(TeXConstants.STYLE_DISPLAY, (float)(12 * viewScale));

	JLabel jl = new JLabel();

	jl.setForeground(new Color(0, 0, 0));
	
	icon.paintIcon(jl, iG, (int)(x * viewScale), (int)(y * viewScale));
	
	//iG.drawRect((int)(x * viewScale), (int)(y * viewScale), (int)(icon.getIconWidth()), (int)(icon.getIconHeight()));
        
    }


    public void setFontSize(double aSize) {
        int newFontSize = (int) (viewScale * aSize);
        if (prevSetFontSize != newFontSize) {
            prevSetFontSize = newFontSize;
            Font f = new Font("Monospaced", Font.PLAIN, newFontSize);
            if (f != null) {
                iG.setFont(f);
                metrics = iG.getFontMetrics();
            }
        }
    }

    public double getFontSize() {
        return  (prevSetFontSize / viewScale);
    }

    public double getScaledTextWidth(String text) {
        java.awt.geom.Rectangle2D textBoundingRectangle = metrics.getStringBounds(text, iG);
        return  (textBoundingRectangle.getWidth() / viewScale);
    }

    public double getScaledTextHeight(String text) {
        java.awt.geom.Rectangle2D textBoundingRectangle = metrics.getStringBounds(text, iG);
        return  (textBoundingRectangle.getHeight() / viewScale);
    }

    public double getTextWidth(String text) {
        java.awt.geom.Rectangle2D textBoundingRectangle = metrics.getStringBounds(text, iG);
        return  textBoundingRectangle.getWidth();
    }

    public double getTextHeight(String text) {
        java.awt.geom.Rectangle2D textBoundingRectangle = metrics.getStringBounds(text, iG);
        return  textBoundingRectangle.getHeight();
    }

    public double getAscent() {
        return (metrics.getAscent() / viewScale);
    }

    public double getDescent() {
        return  (metrics.getDescent() / viewScale);
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

    }//end method.
    
    public static int fontForSize(int aSize) {

        if (aSize > 3) {
            aSize = 3;
        }

        if (aSize < 0) {
            aSize = 0;
        }

        switch (aSize) {

            case 0:
                return 6;

            case 1:
                return 8;

            case 2:
                return 12;

            case 3:
                return 16;

            default:
                return 16;
        }//end switch.
        
    }//end method.


}//end class.
