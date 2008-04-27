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
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import zinger.util.recycling.*;

public class TableModifier implements ActionListener, ClipboardOwner
{
    protected final JTable table;

    public TableModifier(JTable table)
    {
        this.table = table;
    }

    public void actionPerformed(ActionEvent ev)
    {
        String actionCommand = ev.getActionCommand();
        if(actionCommand.equals(Main.getConstant("action.del_cols")))
        {
            TableModel tableModel = this.table.getModel();
            if(tableModel instanceof BSHTableModel)
            {
                TableColumnModel columnModel = this.table.getColumnModel();
                int[] selectedColumns = columnModel.getSelectedColumns();
                Arrays.sort(selectedColumns);
                Vector columnIdentifiers = ((BSHTableModel)tableModel).getColumnIdentifiers();
                Vector dataVector = ((BSHTableModel)tableModel).getDataVector();
                for(int i = selectedColumns.length - 1; i >= 0; --i)
                {
                    if(i == 0 && selectedColumns[i] == 0)
                    {
                        break;
                    }
                    columnIdentifiers.remove(i);
                    for(Iterator iterator = dataVector.iterator(); iterator.hasNext();)
                    {
                        ((Vector)iterator.next()).remove(selectedColumns[i]);
                    }
                }
                ((BSHTableModel)tableModel).setDataVector(dataVector, columnIdentifiers);
            }
        }
        else if(actionCommand.equals(Main.getConstant("action.add_col")))
        {
            TableModel tableModel = this.table.getModel();
            if(tableModel instanceof DefaultTableModel)
            {
				((DefaultTableModel)tableModel).addColumn(null);
			}
        }
        else if(actionCommand.equals(Main.getConstant("action.del_rows")))
        {
            TableModel tableModel = this.table.getModel();
            if(tableModel instanceof BSHTableModel)
            {
                int[] selectedRows = this.table.getSelectedRows();
                Arrays.sort(selectedRows);
                Vector dataVector = ((BSHTableModel)tableModel).getDataVector();
                for(int i = selectedRows.length - 1; i >= 0; --i)
                {
                    dataVector.remove(selectedRows[i]);
                }
                ((BSHTableModel)tableModel).setDataVector(dataVector, ((BSHTableModel)tableModel).getColumnIdentifiers());
            }
        }
        else if(actionCommand.equals(Main.getConstant("action.add_row")))
        {
            TableModel tableModel = this.table.getModel();
            if(tableModel instanceof DefaultTableModel)
            {
				((DefaultTableModel)tableModel).addRow((Vector)null);
            }
        }
        else if(actionCommand.equals(Main.getConstant("action.copy")))
        {
            Point[] selectionCorners = this.getSelectionCorners();
            this.copyRegion(selectionCorners[0].x, selectionCorners[0].y, selectionCorners[1].x, selectionCorners[1].y, false);
        }
        else if(actionCommand.equals(Main.getConstant("action.copy_objects")))
        {
            Point[] selectionCorners = this.getSelectionCorners();
            this.copyRegion(selectionCorners[0].x, selectionCorners[0].y, selectionCorners[1].x, selectionCorners[1].y, true);
        }
        else if(actionCommand.equals(Main.getConstant("action.paste")))
        {
            this.paste(this.table.getSelectedRow(), this.table.getSelectedColumn());
        }
        else if(actionCommand.equals(Main.getConstant("action.paste_fill")))
        {
			Point[] selectionCorners = this.getSelectionCorners();
			this.paste(selectionCorners[0].x, selectionCorners[0].y, selectionCorners[1].x, selectionCorners[1].y);
		}
        else if(actionCommand.equals(Main.getConstant("action.delete")))
        {
            Point[] selectionCorners = this.getSelectionCorners();
			this.deleteRegion(selectionCorners[0].x, selectionCorners[0].y, selectionCorners[1].x, selectionCorners[1].y);
		}
		else if(actionCommand.equals(Main.getConstant("action.cut")))
		{
            Point[] selectionCorners = this.getSelectionCorners();
            this.copyRegion(selectionCorners[0].x, selectionCorners[0].y, selectionCorners[1].x, selectionCorners[1].y, false);
			this.deleteRegion(selectionCorners[0].x, selectionCorners[0].y, selectionCorners[1].x, selectionCorners[1].y);
		}
		else if(actionCommand.equals(Main.getConstant("action.cut_objects")))
		{
            Point[] selectionCorners = this.getSelectionCorners();
            this.copyRegion(selectionCorners[0].x, selectionCorners[0].y, selectionCorners[1].x, selectionCorners[1].y, true);
			this.deleteRegion(selectionCorners[0].x, selectionCorners[0].y, selectionCorners[1].x, selectionCorners[1].y);
		}
		else if(actionCommand.equals(Main.getConstant("action.show_dependencies")))
		{
			if(this.table instanceof ZTable)
			{
				((ZTable)this.table).setExtraRendererOn(Dependencies.class.getName(), ((JCheckBoxMenuItem)ev.getSource()).getState());
			}
		}
		else if(actionCommand.equals(Main.getConstant("action.flip")))
		{
			Point[] selectionCorners = this.getSelectionCorners();
			this.flipRegion(selectionCorners[0].x, selectionCorners[0].y, selectionCorners[1].x, selectionCorners[1].y);
		}
    }

    public Point[] getSelectionCorners()
    {
        Point[] corners =
        new Point[]
        {
            new Point(-1, -1),
            new Point(-1, -1)
        };

        int[] selectedRows = this.table.getSelectedRows();
        for(int i = 0; i < selectedRows.length; ++i)
        {
            corners[0].x = corners[0].x < 0 ? selectedRows[i] : Math.min(corners[0].x, selectedRows[i]);
            corners[1].x = corners[1].x < 0 ? selectedRows[i] : Math.max(corners[1].x, selectedRows[i]);
        }

        int[] selectedColumns = this.table.getSelectedColumns();
        for(int i = 0; i < selectedColumns.length; ++i)
        {
            corners[0].y = corners[0].y < 0 ? selectedColumns[i] : Math.min(corners[0].y, selectedColumns[i]);
            corners[1].y = corners[1].y < 0 ? selectedColumns[i] : Math.max(corners[1].y, selectedColumns[i]);
        }


        return corners;
    }

    public void copyRegion(int startRow, int startColumn, int endRow, int endColumn, boolean asObjects)
    {
        Transferable contents = this.getContents(startRow, startColumn, endRow, endColumn, asObjects);
        this.table.getToolkit().getSystemClipboard().setContents(contents, this);
    }

	/**
	 * Creates a <code>Transferable</code> object representing contents and appearance of a cell region.
	 *
	 * @param startRow table row index of top left region corner
	 * @param startColumn table column index of top left region corner
	 * @param endRow table row index of bottom right region corner
	 * @param endColumn table column index of bottom right region corner
	 * @param asObjects if <code>true</code>, result contains model value, otherwise formatted text value
	 *
	 * @see #setContents(java.awt.datatransfer.Transferable, int, int)
	 * @see #getContents(int, int, boolean)
	 *
	 * @since 0.9.1
	 */
    public Transferable getContents(int startRow, int startColumn, int endRow, int endColumn, boolean asObjects)
    {
		int selectionHeight = endRow - startRow + 1;
		int selectionWidth = endColumn - startColumn + 1;
        Object[][] selection = new Object[selectionHeight][selectionWidth];
        Appearance[][] appearances = asObjects ? new Appearance[selectionHeight][selectionWidth] : null;

        int modelColumnIndex;
        TableModel model = this.table.getModel();
        for(int i = startRow; i <= endRow; ++i)
        {
            for(int j = startColumn; j <= endColumn; ++j)
            {
				modelColumnIndex = this.table.convertColumnIndexToModel(j);
                selection[i - startRow][j - startColumn] = asObjects ?
                                                           model.getValueAt(i, modelColumnIndex) :
                                                           ((BSHTableModel)model).formatValueAt(((BSHTableModel)model).evalValueAt(modelColumnIndex, i), modelColumnIndex, i);
				if(asObjects)
				{
					appearances[i - startRow][j - startColumn] = ((BSHTableModel)model).getAppearance(modelColumnIndex, i, false);
				}
            }
        }

        return new MulticellTransferable(selection, appearances);
	}

	/**
	 * Creates a <code>Transferable</code> object representing contents and appearance of a cell.
	 * Calls <code>getContents(row, column, row, column, asObjects)</code>.
	 *
	 * @see #setContents(java.awt.datatransfer.Transferable, int, int)
	 * @see #getContents(int, int, int, int, boolean)
	 *
	 * @since 0.9.1
	 */
	public Transferable getContents(int row, int column, boolean asObjects)
	{
		return this.getContents(row, column, row, column, asObjects);
	}

    public void deleteRegion(int startRow, int startColumn, int endRow, int endColumn)
    {
		TableModel model = this.table.getModel();
        int column;
		for(int i = startRow; i <= endRow; ++i)
		{
			for(int j = startColumn; j <= endColumn; ++j)
			{
				column = this.table.convertColumnIndexToModel(j);
				try
				{
                	model.setValueAt(null, i, column);
				}
				catch(IndexOutOfBoundsException ex)
				{
					ex.printStackTrace();
				}
			}
		}

		if(!(model instanceof BSHTableModel) && (model instanceof AbstractTableModel))
		{
			((AbstractTableModel)model).fireTableDataChanged();
		}
	}

	/**
	 * @since 0.9.1
	 */
	public void flipRegion(int startRow, int startColumn, int endRow, int endColumn)
	{
		if(endRow - startRow > endColumn - startColumn)
		{
			this.ensureColumnCount(startColumn + endRow - startRow);

			int temp = endRow;
			endRow = startRow + endColumn - startColumn;
			endColumn = startColumn + temp - startRow;
		}
		else
		{
			this.ensureRowCount(startRow + endColumn - startRow);
		}

		for(int row = startRow; row <= endRow; ++row)
		{
			for(int column = startColumn; column <= endColumn; ++column)
			{
				if(column - startColumn <= row - startRow)
				{
					continue;
				}
				this.swapCells(row, column, startRow + column - startColumn, startColumn + row - startRow, true);
			}
		}
	}

	/**
	 * Swaps contents of two cells.
	 *
	 * @since 0.9.1
	 */
	public void swapCells(int row1, int column1, int row2, int column2, boolean asObjects)
	{
		Transferable tempContents = this.getContents(row1, column1, asObjects);
		this.setContents(this.getContents(row2, column2, asObjects), row1, column1);
		this.setContents(tempContents, row2, column2);
	}

    public void lostOwnership(Clipboard clipboard, Transferable contents)
    {
        //System.out.println("Lost ownership: " + clipboard + ", " + contents);
    }

	public void paste(int startRow, int startColumn)
	{
        Transferable contents = this.table.getToolkit().getSystemClipboard().getContents(this);
        this.setContents(contents, startRow, startColumn);
    }

    /**
     * @since 1.0.3
     */
    public void paste(int row1, int column1, int row2, int column2)
    {
		Transferable contents = this.table.getToolkit().getSystemClipboard().getContents(this);
		this.setContents(contents, row1, column1, row2, column2);
	}

	/**
	 * @since 1.0.3
	 */
	protected Map getContentsMap(Transferable contents)
	{
        DataFlavor[] supportedFlavors = MulticellTransferable.getSupportedFlavors();
        int flavorIndex;
        for(flavorIndex = 0; flavorIndex < supportedFlavors.length; ++flavorIndex)
        {
			if(contents.isDataFlavorSupported(supportedFlavors[flavorIndex]))
			{
				break;
			}
		}
		Object[][] selection;
		Appearance[][] appearances = null;
		Map contentsMap = null;
		try
		{
			switch(flavorIndex)
			{
				case MulticellTransferable.ENHANCED_JAVA_2D_ARRAY_FLAVOR_INDEX:
				contentsMap = (Map)contents.getTransferData(supportedFlavors[flavorIndex]);
				break;

				case MulticellTransferable.JAVA_2D_ARRAY_FLAVOR_INDEX:
				contentsMap = new HashMap();
				contentsMap.put(
					MulticellTransferable.ENHANCED_SELECTION_KEY,
					(Object[][])contents.getTransferData(supportedFlavors[flavorIndex])
				);
				break;

				case MulticellTransferable.STRING_FLAVOR_INDEX:
				contentsMap = new HashMap();
				contentsMap.put(
					MulticellTransferable.ENHANCED_SELECTION_KEY,
					MulticellTransferable.parse2DArrayString((String)contents.getTransferData(supportedFlavors[flavorIndex]))
				);
				break;

				case MulticellTransferable.PLAIN_TEXT_FLAVOR_INDEX:
				contentsMap = new HashMap();
				contentsMap.put(
					MulticellTransferable.ENHANCED_SELECTION_KEY,
					MulticellTransferable.parseReader(new InputStreamReader((InputStream)contents.getTransferData(supportedFlavors[flavorIndex])))
				);
				break;

				default:
			}
		}
		catch(UnsupportedFlavorException ex)
		{
			ex.printStackTrace();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}

		return contentsMap;
	}

	/**
	 * Writes the contents of a <code>Transferable</code> object starting at the top left corner specified.
	 *
	 * @param contents table (model) data to be written
	 * @param startRow table row index of top left corner of the region
	 * @param startColumn table column index of top left corner of the region
	 *
	 * @see #setContents(java.awt.datatransfer.Transferable, int, int, int, int)
	 * @see #getContents(int, int, int, int, boolean)
	 * @see #getContents(int, int, boolean)
	 *
	 * @since 0.9.1
	 */
	public void setContents(Transferable contents, int startRow, int startColumn)
	{
		this.setContents(contents, startRow, startColumn, -1, -1);
	}

	/**
	 * Writes the contents of a <code>Transferable</code> object starting at the top left corner specified.
	 * Optionally fills the entire specified rectangle if <code>endRow</code> and <code>endColumn</code>
	 * are greater or equal to <code>startRow</code> and <code>startColumn</code> respectively.  Filling
	 * is achieved by not going out of bounds of the rectangle and repeating the clipboard contents
	 * until the rectangle is filled.
	 *
	 * @param contents table (model) data to be written
	 * @param startRow table row index of top left corner of the region
	 * @param startColumn table column index of top left corner of the region
	 * @param endRow table row index of bottom right corner of the region (values <code>&lt; startRow</code> signify no filling)
	 * @param endColumn table column index of bottom right corner of the region (values <code>&lt; startColumn</code> signify no filling)
	 *
	 * @see #getContents(int, int, int, int, boolean)
	 * @see #getContents(int, int, boolean)
	 *
	 * @since 1.0.3
	 */
    public void setContents(Transferable contents, int startRow, int startColumn, int endRow, int endColumn)
    {
        if(contents == null)
        {
            return;
        }
		TableModel model = this.table.getModel();
		Map contentsMap = this.getContentsMap(contents);
		Object[][] selection = (Object[][])contentsMap.get(MulticellTransferable.ENHANCED_SELECTION_KEY);
		Appearance[][] appearances =
			(model instanceof BSHTableModel) ?
			(Appearance[][])contentsMap.get(MulticellTransferable.ENHANCED_APPEARANCES_KEY) :
			null;

		boolean fill = (startRow <= endRow && startColumn <= endColumn);

		boolean dimensionsChanged = !fill && this.ensureRowCount(startRow + selection.length);

		int columnCount;
		int rowCount;
		if(fill)
		{
			rowCount = endRow - startRow + 1;
			columnCount = endColumn - startColumn + 1;
		}
		else
		{
			rowCount = selection.length;
			columnCount = -1;
		}
		int iIndex = -1;
		int jIndex = -1;
		for(int i = 0; i < rowCount; ++i)
		{
			if(++iIndex >= selection.length)
			{
				iIndex = 0;
			}
			int row = startRow + i;
			if(!fill)
			{
				dimensionsChanged = this.ensureColumnCount(this.table.convertColumnIndexToModel(startColumn) + selection[i].length) || dimensionsChanged;
				columnCount = selection[i].length;
			}
			for(int j = 0; j < columnCount; ++j)
			{
				if(++jIndex >= selection[iIndex].length)
				{
					jIndex = 0;
				}
				int column = this.table.convertColumnIndexToModel(startColumn + j);
				model.setValueAt(selection[iIndex][jIndex], row, column);
				if(appearances != null && appearances[iIndex][jIndex] != null)
				{
					appearances[iIndex][jIndex].applyTo(((BSHTableModel)model).getAppearance(column, row, true));
				}
			}
		}

		if(model instanceof BSHTableModel && !dimensionsChanged)
		{
			for(int i = 0; i < selection.length; ++i)
			{
				int row = startRow + i;
				for(int j = 0; j < selection[i].length; ++j)
				{
					int column = this.table.convertColumnIndexToModel(startColumn + j);
					((BSHTableModel)model).followDependencies(column, row);
				}
			}
		}
		else if(model instanceof AbstractTableModel)
		{
			((AbstractTableModel)model).fireTableDataChanged();
		}
	}

    public boolean ensureColumnCount(int count)
    {
		int columnCount = this.table.getColumnCount();
		if(columnCount < count)
		{
			TableModel model = this.table.getModel();
			if(model instanceof DefaultTableModel)
			{
				for(int i = columnCount; i <= count; ++i)
				{
					((DefaultTableModel)model).addColumn(null);
				}
				return true;
			}
		}
		return false;
	}

	public boolean ensureRowCount(int count)
	{
		int rowCount = this.table.getRowCount();
		if(rowCount < count)
		{
			TableModel model = this.table.getModel();
			if(model instanceof BSHTableModel)
			{
				Vector dataVector = ((BSHTableModel)model).getDataVector();
				for(int i = rowCount; i <= count; ++i)
				{
					dataVector.add(new Vector());
				}
				((BSHTableModel)model).setDataVector(dataVector, ((BSHTableModel)model).getColumnIdentifiers());
				return true;
			}
		}
		return false;
	}

    public void translateValueTo(int sourceRow,
                                 int sourceCol,
                                 int targetRowMin,
                                 int targetColMin,
                                 int targetRowMax,
                                 int targetColMax)
    {
		Object obj = this.table.getValueAt(sourceRow, sourceCol);
		if(obj == null || !(obj instanceof String) || !((String)obj).startsWith("="))
		{
			for(int row = targetRowMin; row <= targetRowMax; ++row)
			{
				for(int col = targetColMin; col <= targetColMax; ++col)
				{
					this.table.setValueAt(obj, row, col);
				}
			}
			return;
		}

		String formula = (String)obj;
	}
}
