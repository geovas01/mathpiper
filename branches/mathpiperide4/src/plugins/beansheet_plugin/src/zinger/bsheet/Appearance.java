// Bean Sheet
// Copyright (C) 2004 Alexey Zinger
// 
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

package zinger.bsheet;

import java.awt.*;

/**
 * Bean class representing appearance of a GUI element.
 *
 * @author Alexey Zinger (inline_four@yahoo.com)
 */
public class Appearance
{
	private Font font;
	private Number alignmentX;
	private Number alignmentY;
	private Color bgColor;
	private Color fgColor;

	public final Font getFont()
	{
		return this.font;
	}

	public final void setFont(Font font)
	{
		this.font = font;
	}

	public final Number getAlignmentX()
	{
		return this.alignmentX;
	}

	public final void setAlignmentX(Number alignmentX)
	{
		this.alignmentX = alignmentX;
	}

	public final Number getAlignmentY()
	{
		return this.alignmentY;
	}

	public final void setAlignmentY(Number alignmentY)
	{
		this.alignmentY = alignmentY;
	}

	public final Color getBgColor()
	{
		return this.bgColor;
	}

	public final void setBgColor(Color bgColor)
	{
		this.bgColor = bgColor;
	}

	public final Color getFgColor()
	{
		return this.fgColor;
	}

	public final void setFgColor(Color fgColor)
	{
		this.fgColor = fgColor;
	}

	public String toString()
	{
		return (new StringBuffer(super.toString()))
		                 .append(" [font: ").append(this.getFont()).append(']')
		                 .append(" [alignmentX: ").append(this.getAlignmentX()).append(']')
		                 .append(" [alignmentY: ").append(this.getAlignmentY()).append(']')
		                 .append(" [bgColor: ").append(this.getBgColor()).append(']')
		                 .append(" [fgColor: ").append(this.getFgColor()).append(']')
		                 .toString();
	}

	public void applyTo(Appearance appearance)
	{
		if(this != appearance)
		{
			appearance.setFont(this.getFont());
			appearance.setAlignmentX(this.getAlignmentX());
			appearance.setAlignmentY(this.getAlignmentY());
			appearance.setBgColor(this.getBgColor());
			appearance.setFgColor(this.getFgColor());
		}
	}
}
