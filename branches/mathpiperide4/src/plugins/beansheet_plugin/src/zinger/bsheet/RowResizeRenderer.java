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
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

public class RowResizeRenderer extends MouseAdapter implements MouseMotionListener, ZTable.ExtraRenderer
{
	public static final int NOT_IN_HEADER_COLUMN_MODE = 0;
	public static final int IN_HEADER_COLUMN_MODE = 1;
	public static final int CAN_RESIZE_MODE = 2;
	public static final int RESIZING_MODE = 3;

	protected final JTable table;

	private int mode = RowResizeRenderer.NOT_IN_HEADER_COLUMN_MODE;

	private Point lastPoint;
	private int row;

	public RowResizeRenderer(JTable table)
	{
		this.table = table;
		this.table.addMouseMotionListener(this);
		this.table.addMouseListener(this);
	}

	public void paint(Graphics g)
	{
		if(this.getMode() == RowResizeRenderer.RESIZING_MODE)
		{
			Rectangle clipRect = g.getClipBounds();
			g.drawLine(clipRect.x,
					   this.lastPoint.y,
					   clipRect.x + clipRect.width,
					   this.lastPoint.y);
		}
	}

	public void mouseReleased(MouseEvent ev)
	{
		if(this.getMode() == RowResizeRenderer.RESIZING_MODE)
		{
			Rectangle cellRect = this.table.getCellRect(this.row, 0, false);
			int rowHeight = this.lastPoint.y - cellRect.y;
			if(rowHeight > 1)
			{
				this.table.setRowHeight(this.row, rowHeight);
			}
			this.setMode(RowResizeRenderer.NOT_IN_HEADER_COLUMN_MODE);
			this.table.repaint();
		}
	}

	public void mousePressed(MouseEvent ev)
	{
		int mode = this.getMode();
		Point p = ev.getPoint();
		if(mode == RowResizeRenderer.CAN_RESIZE_MODE)
		{
			this.row = this.table.rowAtPoint(p);
			this.setMode(RowResizeRenderer.RESIZING_MODE);
		}
	}

	public void mouseDragged(MouseEvent ev)
	{
		int mode = this.getMode();
		Point p = ev.getPoint();
		if(mode == RowResizeRenderer.RESIZING_MODE)
		{
			this.lastPoint = p;
			this.table.repaint();
		}
	}

	public void mouseMoved(MouseEvent ev)
	{
		int mode = this.getMode();
		if(mode == RowResizeRenderer.RESIZING_MODE)
		{
			this.table.repaint();
			return;
		}
		Point p = ev.getPoint();
		this.lastPoint = p;
		if(this.table.convertColumnIndexToModel(this.table.columnAtPoint(p)) == 0)
		{
			if(mode == RowResizeRenderer.NOT_IN_HEADER_COLUMN_MODE)
			{
				this.setMode(RowResizeRenderer.IN_HEADER_COLUMN_MODE);
			}
			else
			{
				Rectangle cellRect = this.table.getCellRect(this.table.rowAtPoint(p), this.table.columnAtPoint(p), true);

				boolean closeToEdge = p.y > cellRect.y + cellRect.height - 6;
				switch(mode)
				{
					case RowResizeRenderer.CAN_RESIZE_MODE:
					if(!closeToEdge)
					{
						this.setMode(RowResizeRenderer.IN_HEADER_COLUMN_MODE);
					}
					break;

					case RowResizeRenderer.IN_HEADER_COLUMN_MODE:
					if(closeToEdge)
					{
						this.setMode(RowResizeRenderer.CAN_RESIZE_MODE);
					}
					break;
				}
			}
		}
		else
		{
			if(mode == RowResizeRenderer.IN_HEADER_COLUMN_MODE || mode == RowResizeRenderer.CAN_RESIZE_MODE)
			{
				this.setMode(RowResizeRenderer.NOT_IN_HEADER_COLUMN_MODE);
			}
		}
	}

	protected synchronized void setMode(int mode)
	{
		this.mode = mode;
		switch(this.mode)
		{
			case RowResizeRenderer.CAN_RESIZE_MODE:
			case RowResizeRenderer.RESIZING_MODE:
			this.table.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			break;

			default:
			this.table.setCursor(Cursor.getDefaultCursor());
			break;
		}
	}

	public int getMode()
	{
		return this.mode;
	}
}

