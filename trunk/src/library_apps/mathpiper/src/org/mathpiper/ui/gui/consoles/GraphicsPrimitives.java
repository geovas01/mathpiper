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

import java.awt.*;

public class GraphicsPrimitives
{
	static double viewScale = 1.0;
	private Graphics iG = null;
	private Graphics2D iG2D = null;

	int prevGray = -1;

	int prevSetFontSize = -1;
	FontMetrics metrics = null;

	public GraphicsPrimitives(Graphics g)
	{
		iG = g;
		if ( g instanceof Graphics2D )
		{
			iG2D = (Graphics2D)g;
		}
	}
	public void setLineThickness(float aThickness)
	{
		if (iG2D != null)
		{
			iG2D.setStroke(new BasicStroke((float)(aThickness*viewScale),BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		}
	}

	public void drawLine(int x0, int y0, int x1, int y1)
	{
		iG.drawLine((int)(x0*viewScale),(int)(y0*viewScale),(int)(x1*viewScale),(int)(y1*viewScale));
	}


	void setGray(int aGray)
	{
		if (prevGray != aGray)
		{
			prevGray = aGray;
			iG.setColor(new Color(aGray, aGray, aGray));
		}
	}

	public void drawText(String text, int x, int y)
	{
		iG.drawString(text, (int)(x*viewScale), (int)(y*viewScale));
	}


	void setFontSize(int aSize)
	{
		int newFontSize = (int)(viewScale*aSize);
		if (prevSetFontSize != newFontSize)
		{
			prevSetFontSize = newFontSize;
			Font f = new Font ("Verdana", Font.PLAIN, newFontSize);
			if (f != null)
			{
				iG.setFont(f);
				metrics = iG.getFontMetrics();
			}
		}
	}
	int getFontSize()
	{
		return (int)(prevSetFontSize/viewScale);
	}
	int textWidth(String s)
	{
		java.awt.geom.Rectangle2D m = metrics.getStringBounds(s,iG);
		return (int)(m.getWidth()/viewScale);
	}
	int getAscent()
	{
		return (int)(metrics.getAscent()/viewScale);
	}
	double getDescent()
	{
		return (int)(metrics.getDescent()/viewScale);
	}

}
