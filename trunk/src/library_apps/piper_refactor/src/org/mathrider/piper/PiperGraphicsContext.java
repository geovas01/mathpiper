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

package org.mathrider.piper;


import java.awt.*;

class PiperGraphicsContext
{
	Graphics graphics;
	int xtop;
	int ytop;
	
	public PiperGraphicsContext(Graphics g,int x, int y)
	{
		graphics = g;
		xtop = x;
		ytop = y;
	}

	public void SetColor(int red, int green, int blue)
	{
		graphics.setColor(new Color(red,green,blue));
	}
	
	public void DrawText(int x, int y, String text)
	{
		graphics.drawString(text,x+xtop,y+ytop);
	}
	
	public void DrawLine(int x0, int y0, int x1, int y1)
	{
		graphics.drawLine(xtop+x0,ytop+y0,xtop+x1,ytop+y1);
	}
	
	public void DrawRoundRect(int x,int y, int width, int height, int arc)
	{
		graphics.drawRoundRect(xtop+x,ytop+y,width,height,arc,arc);
	}
	
	public void DrawRect(int x,int y, int width, int height)
	{
		graphics.drawRect(xtop+x,ytop+y,width,height);
	}
	
	public void FillRoundRect(int x,int y, int width, int height,int arc)
	{
		graphics.fillRoundRect(xtop+x,ytop+y,width,height,arc,arc);
	}
	
	public void FillRect(int x,int y, int width, int height)
	{
		graphics.fillRect(xtop+x,ytop+y,width,height);
	}
	
	public int FontHeight()
	{
		FontMetrics fontMetrics = graphics.getFontMetrics();
		return fontMetrics.getHeight();
	}
	
	public int FontDescent()
	{
		FontMetrics fontMetrics = graphics.getFontMetrics();
		return fontMetrics.getDescent();
	}
	
	public int TextWidthInPixels(String text)
	{
		FontMetrics fontMetrics = graphics.getFontMetrics();
		return fontMetrics.stringWidth(text);
	}
	
	public int SetFontSize(int aBold, int aSize)
	{
		if (aBold != 0)
			graphics.setFont(new Font("Helvetica", Font.BOLD, aSize));
		else
			graphics.setFont(new Font("Helvetica", Font.PLAIN, aSize));
		return 1;
	}
};
