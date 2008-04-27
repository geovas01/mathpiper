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
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class TableSort extends DialogEditor
{
	protected static class ComparatorEditor extends AbstractCellEditor implements TableCellEditor
	{
		protected PopUpCellEditor popUpEditor;

		public synchronized Component getTableCellEditorComponent(JTable table,
													 Object value,
													 boolean isSelected,
													 int row,
													 int column)
		{
			return null;
		}

		public Object getCellEditorValue()
		{
			return null;
		}

		public boolean isCellEditable(EventObject ev)
		{
			if (ev instanceof MouseEvent && ((MouseEvent)ev).getClickCount() > 1)
			{
				if(this.popUpEditor == null)
				{
					this.popUpEditor = new PopUpCellEditor((JTable)ev.getSource());
				}
				this.popUpEditor.run();
            }
            return false;
		}

		public boolean shouldSelectCell(EventObject ev)
		{
			if(ev instanceof MouseEvent)
			{
				return ((MouseEvent)ev).getClickCount() < 2;
			}
			return true;
		}
	}

	protected static class ComparatorTableModel extends BSHTableModel
	{
		public ComparatorTableModel(int cols)
		{
			super(1, cols);
			this.rowIndexes = false;
		}

		public Class getColumnClass(int col)
		{
			return Comparator.class;
		}

		public boolean isCellEditable(int row, int col)
		{
			return true;
		}

		public Object getValueAt(int row, int col)
		{
			if(col >= this.getColumnCount())
			{
				return null;
			}
			Object val = super.getValueAt(row, col);
			if(val == null)
			{
				val = "=\n//Natural order\nnull";
			}
			return val;
		}

	}

	protected JTable comparatorTable;
	protected TableColumnModel columnModel;
	protected TableModel model;

	protected int[] sortColumns;
	protected Comparator[] comparators;

	protected final String setNaturalOrderCommand;
	protected final String setReverseOrderCommand;

	public TableSort(JTable table)
	{
		super(table,
		      Main.getConstant("dialog.sort.title"),
		      Main.getConstant("dialog.sort.okay.action"),
		      Main.getConstant("dialog.sort.okay.label"),
		      Main.getConstant("dialog.sort.cancel.action"),
		      Main.getConstant("dialog.sort.cancel.label"),
		      Main.getConstant("action.col_sort"),
		      Main.getConstant("action.cell_sort"));
		this.setNaturalOrderCommand = Main.getConstant("action.natural_order");
		this.setReverseOrderCommand = Main.getConstant("action.reverse_order");
	}

	protected void editColumns()
	{
		this.prompt();

		if(this.changesAccepted)
		{
			this.doSort(0, this.table.getRowCount());
		}
	}

	protected void editCells()
	{
		this.prompt();

		if(this.changesAccepted)
		{
			int[] selectedRows = this.table.getSelectedRows();
			int minRow = selectedRows[0];
			int maxRow = minRow;
			for(int i = 1; i < selectedRows.length; ++i)
			{
				if(selectedRows[i] < minRow)
				{
					minRow = selectedRows[i];
				}
				else if(selectedRows[i] > maxRow)
				{
					maxRow = selectedRows[i];
				}
			}
			this.doSort(minRow, maxRow - minRow + 1);
		}
	}

	protected void doSort(int startRow, int nRows)
	{
		((BSHTableModel)this.table.getModel()).sort(startRow, nRows, this.sortColumns, this.comparators);
	}

	protected void prompt()
	{
		if(this.dialog == null)
		{
			this.createDialog();
		}
		TableSort.ComparatorTableModel model = new TableSort.ComparatorTableModel(this.table.getSelectedColumns().length + 1);
		this.comparatorTable.setModel(model);

		this.comparatorTable.setDefaultRenderer(Comparator.class, model.new BSHCellRenderer()
		{
			protected Object getFormattedValue(int col, int row)
			{
				Object val = this.getEvaluatedValue(col, row);

				if(val == null)
				{
					val = "Ascending";
				}
				else if(val.equals(Collections.reverseOrder()))
				{
					val = "Descending";
				}
				else
				{
					val = "Scripted";
				}

				return val;
			}
		});

		this.changesAccepted = false;

		java.util.List cols = new LinkedList();
		for(Enumeration en = this.columnModel.getColumns(); en.hasMoreElements();)
		{
			cols.add(en.nextElement());
		}
		for(Iterator iter = cols.iterator(); iter.hasNext(); )
		{
			this.columnModel.removeColumn((TableColumn)iter.next());
		}

		int[] selectedCols = this.table.getSelectedColumns();
		TableColumn col;
		int colIndex;
		for(int i = 0; i < selectedCols.length; ++i)
		{
			colIndex = this.table.convertColumnIndexToModel(selectedCols[i]);
			col = new TableColumn(i);
			col.setHeaderValue(this.table.getModel().getColumnName(colIndex));
			this.columnModel.addColumn(col);
		}

		this.dialog.pack();
		this.dialog.setLocationRelativeTo(this.table.getTopLevelAncestor());
		this.dialog.setVisible(true);

		if(this.changesAccepted)
		{
			this.sortColumns = new int[selectedCols.length];
			this.comparators = new Comparator[selectedCols.length];
			for(int i = 0; i < this.sortColumns.length; ++i)
			{
				this.sortColumns[i] = selectedCols[this.columnModel.getColumn(i).getModelIndex()];
				try
				{
					this.comparators[i] = (Comparator)((BSHTableModel)this.comparatorTable.getModel()).evalValueAt(this.columnModel.getColumn(i).getModelIndex(), 0);
				}
				catch(ClassCastException ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}

	protected void createComparatorTable()
	{
		this.comparatorTable = new JTable();
		this.comparatorTable.setCellSelectionEnabled(true);
		this.comparatorTable.setRowSelectionAllowed(false);
		this.comparatorTable.setDefaultEditor(Comparator.class, new TableSort.ComparatorEditor());
	}

	protected void createDialog()
	{
		super.createDialog();

		Container contentPane = this.dialog.getContentPane();

		Container innerPane = new JPanel(new BorderLayout());

		this.createComparatorTable();

		this.columnModel = this.comparatorTable.getColumnModel();
		this.model = this.comparatorTable.getModel();
		innerPane.add(this.comparatorTable.getTableHeader(), BorderLayout.NORTH);

		innerPane.add(this.comparatorTable, BorderLayout.CENTER);

		contentPane.add(innerPane, BorderLayout.CENTER);

		JToolBar toolBar = this.createToolBar();

		contentPane.add(toolBar, BorderLayout.NORTH);
	}

	protected JToolBar createToolBar()
	{
		JToolBar toolBar = new JToolBar();

		JButton naturalOrderButton = Main.createButton("dialog.sort.toolbar.set_natural_order");
		naturalOrderButton.addActionListener(this);
		toolBar.add(naturalOrderButton);

		JButton reverseOrderButton = Main.createButton("dialog.sort.toolbar.set_reverse_order");
		reverseOrderButton.addActionListener(this);
		toolBar.add(reverseOrderButton);

		return toolBar;
	}

	public void actionPerformed(ActionEvent ev)
	{
		String actionCommand = ev.getActionCommand();
		if(actionCommand.equals(this.setNaturalOrderCommand))
		{
			this.setSelectedComparators(null);
		}
		else if(actionCommand.equals(this.setReverseOrderCommand))
		{
			this.setSelectedComparators("= Collections.reverseOrder()");
		}
		else
		{
			super.actionPerformed(ev);
		}
	}

	protected void setSelectedComparators(Object comp)
	{
		int[] selectedRows = this.comparatorTable.getSelectedRows();
		int[] selectedColumns = this.comparatorTable.getSelectedColumns();
		for(int rowIndex = 0; rowIndex < selectedRows.length; ++rowIndex)
		{
			for(int colIndex = 0; colIndex < selectedColumns.length; ++colIndex)
			{
				this.comparatorTable.setValueAt(comp, selectedRows[rowIndex], selectedColumns[colIndex]);
			}
		}
	}
}
