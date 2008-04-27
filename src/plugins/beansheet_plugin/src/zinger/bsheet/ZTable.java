// Bean Sheet
// Copyright (C) 2004-2006 Alexey Zinger
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
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

/**
 * Extensions to <code>JTable</code>.
 * This class provides hooks for rendering on top of the standard <code>JTable</code> grid.
 *
 * @see zinger.bsheet.ZTable.ExtraRenderer
 *
 * @author Alexey Zinger (inline_four@yahoo.com)
 */
public class ZTable extends JTable implements KeyListener, MouseListener
{
	/**
	 * Interface for rendering extensions to <code>JTable</code>.
	 *
	 * @author Alexey Zinger (inline_four@yahoo.com)
	 */
	public static interface ExtraRenderer
	{
		/**
		 * Gets called after standard painting of the grid is complete.
		 *
		 * @param g
		 * graphics object that was used by <code>paintComponent(Graphics)</code> method.
		 *
		 * @see zinger.bsheet.ZTable#paintComponent(java.awt.Graphics)
		 */
		public void paint(Graphics g);
	}

	protected final Map extraRenderers = new TreeMap();

	/**
	 * @since 1.0.3
	 */
	protected boolean notEditable = false;

	private final Runnable repainter =
	new Runnable()
	{
		public void run()
		{
			ZTable.this.repaint();
		}
	};

	public ZTable()
	{
		this.init();
	}

	public ZTable(TableModel dm)
	{
		super(dm);
		this.init();
	}

	public ZTable(TableModel dm,
	              TableColumnModel cm)
	{
		super(dm, cm);
		this.init();
	}

	public ZTable(TableModel dm,
				  TableColumnModel cm,
				  ListSelectionModel sm)
	{
		super(dm, cm, sm);
		this.init();
	}

	public ZTable(int numRows,
				  int numColumns)
	{
		super(numRows, numColumns);
		this.init();
	}

	public ZTable(Vector rowData,
				  Vector columnNames)
	{
		super(rowData, columnNames);
		this.init();
	}

	public ZTable(Object[][] rowData,
				  Object[] columnNames)
	{
		super(rowData, columnNames);
		this.init();
	}

	protected void init()
	{
		this.addKeyListener(this);
		this.addMouseListener(this);
	}

	/**
	 * Extends standard method.
	 * After calling <code>super.paintComponent(g)</code>, this method iterates through
	 * extra renderer objects in <code>extraRenderers</code> and invokes their <code>paint(Graphics)</code>
	 * methods.  Renderers are invoked in the order of iteration, which is determined by the natural
	 * order of their ID's.  Not all renderers may be invoked depending on their state (on/off).
	 *
	 * @see zinger.bsheet.ZTable.ExtraRenderer#paint(java.awt.Graphics)
	 * @see #extraRenderers
	 * @see #addExtraRenderer(java.lang.Object, zinger.bsheet.ZTable.ExtraRenderer)
	 * @see #isExtraRendererOn(java.lang.Object)
	 */
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Object[] holder;
		for(Iterator iterator = this.extraRenderers.values().iterator(); iterator.hasNext(); )
		{
			holder = (Object[])iterator.next();
			if(((Boolean)holder[1]).booleanValue())
			{
				((ZTable.ExtraRenderer)holder[0]).paint(g);
			}
		}
	}

	/**
	 * Convenience method for scheduling a repaint call on <code>EventQueue</code>.
	 */
	protected void requestRepaint()
	{
		EventQueue.invokeLater(this.repainter);
	}

	/**
	 * Returns an unmodifiable set of renderer ID's (keys by which they are stored).
	 *
	 * @see #addExtraRenderer(java.lang.Object, zinger.bsheet.ZTable.ExtraRenderer)
	 */
	public Set getExtraRendererIDs()
	{
		return Collections.unmodifiableSet(this.extraRenderers.keySet());
	}

	/**
	 * Adds an <code>ExtraRenderer</code> object under the specified ID.
	 * Because of the way renderers are iterated through, the natural order of their ID's
	 * determines the order in which they are invoked.
	 *
	 * @see  #paintComponent(java.awt.Graphics)
	 */
	public void addExtraRenderer(Object id, ZTable.ExtraRenderer exren)
	{
		this.extraRenderers.put(id, new Object[] {exren, Boolean.FALSE});
	}

	/**
	 * Removes a renderer object.
	 * Upon removal, a repaint call is scheduled.
	 *
	 * @see #requestRepaint()
	 *
	 * @return the renderer object that was stored under the specified key, <code>null</code>
	 * if none was found
	 */
	public ZTable.ExtraRenderer removeExtraRenderer(Object id)
	{
		Object[] holder = (Object[])this.extraRenderers.remove(id);
		this.requestRepaint();
		return holder == null ? null : (ZTable.ExtraRenderer)holder[0];
	}

	/**
	 * Gets a renderer object stored under the specified ID.
	 */
	public ZTable.ExtraRenderer getExtraRenderer(Object id)
	{
		Object[] holder = (Object[])this.extraRenderers.get(id);
		return holder == null ? null : (ZTable.ExtraRenderer)holder[0];
	}

	/**
	 * Determines whether the renderer object stored under the specified ID is on or off.
	 * If a renderer object is off, it will not be invoked by <code>paintComponent(Graphics)</code>.
	 *
	 * @see #paintComponent(java.awt.Graphics)
	 * @see #setExtraRendererOn(java.lang.Object, boolean)
	 */
	public boolean isExtraRendererOn(Object id)
	{
		Object[] holder = (Object[])this.extraRenderers.get(id);
		return holder == null ? false : ((Boolean)holder[1]).booleanValue();
	}

	/**
	 * Sets the state of a renderer object stored under the specified ID.
	 * If a renderer object was found and its state changed, a repaint call is scheduled.
	 *
	 * @see #requestRepaint()
	 * @see #setAllExtraRenderersOn(boolean)
	 */
	public void setExtraRendererOn(Object id, boolean on)
	{
		Object[] holder = (Object[])this.extraRenderers.get(id);
		if(holder != null && on != ((Boolean)holder[1]).booleanValue())
		{
			holder[1] = on ? Boolean.TRUE : Boolean.FALSE;
			this.requestRepaint();
		}
	}

	/**
	 * A performance enhancing method for setting state for all available renderers.
	 * This method turns all available renderers on and only schedules a single repaint call.
	 * When dealing with many renderers, it is expected to be more efficient than iterating through available
	 * renderer ID's and calling <code>setExtraRendererOn(boolean)</code>.  Unlike that method,
	 * however, this one schedules a repaint call regardless of whether any renderer states
	 * have been changes.
	 *
	 * @see #requestRepaint()
	 * @see #setExtraRendererOn(java.lang.Object, boolean)
	 */
	public void setAllExtraRenderersOn(boolean on)
	{
		Boolean onVal = on ? Boolean.TRUE : Boolean.FALSE;
		for(Iterator iterator = this.extraRenderers.values().iterator(); iterator.hasNext(); )
		{
			((Object[])iterator.next())[1] = onVal;
		}
		this.requestRepaint();
	}

	public boolean isCellEditable(int row, int column)
	{
		return !this.notEditable && super.isCellEditable(row, column);
	}

	/////////////////////////////////////////////////////////////////////////
	// KeyListener API

	/**
	 * @since 1.0.3
	 */
	public void keyPressed(KeyEvent ev)
	{
		this.notEditable = (ev.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) > 0 ||
		                   (ev.getModifiersEx() & KeyEvent.ALT_DOWN_MASK) > 0;
	}

	/**
	 * @since 1.0.3
	 */
	public void keyReleased(KeyEvent ev)
	{
	}

	/**
	 * @since 1.0.3
	 */
	public void keyTyped(KeyEvent ev)
	{
	}

	// KeyListener API
	/////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////
	// MouseListener API

	/**
	 * @since 1.0.3
	 */
	public void mouseClicked(MouseEvent ev)
	{
		this.notEditable = false;
	}

	/**
	 * @since 1.0.3
	 */
	public void mousePressed(MouseEvent ev)
	{
		this.notEditable = false;
	}

	/**
	 * @since 1.0.3
	 */
	public void mouseReleased(MouseEvent ev)
	{
		this.notEditable = false;
	}

	/**
	 * @since 1.0.3
	 */
	public void mouseEntered(MouseEvent ev)
	{
	}

	/**
	 * @since 1.0.3
	 */
	public void mouseExited(MouseEvent ev)
	{
	}

	// MouseListener API
	/////////////////////////////////////////////////////////////////////////
}
