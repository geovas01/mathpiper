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

public abstract class SBox
{
	java.awt.Dimension iDimension;
	java.awt.Point iPosition;
	int iSize;
	int iAscent;
	
	abstract public void calculatePositions(GraphicsPrimitives g, int aSize, java.awt.Point aPosition);
	abstract public void render(GraphicsPrimitives g);

	public java.awt.Dimension getDimension()
	{
		return iDimension;
	}
	
	public java.awt.Point getCalculatedPosition()
	{
		return iPosition;
	}
	
	public int getSetSize()
	{
		return iSize;
	}
	
	public int getCalculatedAscent()
	{
		return iAscent;
	}

	public void drawBoundingBox(GraphicsPrimitives g)
	{
		g.SetLineThickness(0);
		int x0 = iPosition.x;
		int y0 = iPosition.y-getCalculatedAscent();
		int x1 = x0+iDimension.width;
		int y1 = y0+iDimension.height;
		g.DrawLine(x0,y0,x1,y0);
		g.DrawLine(x1,y0,x1,y1);
		g.DrawLine(x1,y1,x0,y1);
		g.DrawLine(x0,y1,x0,y0);
	}

}
