// Bean Sheet
// Copyright (C) 2004-2005 Alexey Zinger
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

import bsh.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.text.*;

import zinger.bsheet.commands.*;

public class TableSearch extends DialogEditor
{
	protected final String clearCommand;

	protected JTextField searchText;
	protected JCheckBox caseSensitiveCheck;
	protected JCheckBox testRawCheck;
	protected JCheckBox testEvalCheck;
	protected JCheckBox testFormattedCheck;
	protected JButton clearButton;

	protected Iterator searchIterator;

	public TableSearch(JTable table)
	{
        super(table,
              Main.getConstant("dialog.search.title"),
              Main.getConstant("dialog.search.okay.action"),
              Main.getConstant("dialog.search.okay.label"),
              Main.getConstant("dialog.search.cancel.action"),
              Main.getConstant("dialog.search.cancel.label"),
              Main.getConstant("action.search"),
              Main.getConstant("action.search"));
		this.clearCommand = Main.getConstant("dialog.search.clear.action");
	}

	protected void createDialog()
	{
		super.createDialog();
		this.dialog.setModal(false);

		this.searchText = new JTextField();
		this.searchText.setActionCommand(this.acceptCommand);
		this.searchText.addActionListener(this);

		Container contentPane = this.dialog.getContentPane();
		contentPane.add(searchText, BorderLayout.NORTH);

		this.caseSensitiveCheck = new JCheckBox(Main.getConstant("dialog.search.case_sensitive.label"));
		this.testRawCheck = new JCheckBox(Main.getConstant("dialog.search.test_raw.label"), null, true);
		this.testEvalCheck = new JCheckBox(Main.getConstant("dialog.search.test_eval.label"), null, true);
		this.testFormattedCheck = new JCheckBox(Main.getConstant("dialog.search.test_formatted.label"), null, true);

		JPanel settingsPane = new JPanel(new FlowLayout());
		settingsPane.add(caseSensitiveCheck);
		settingsPane.add(testRawCheck);
		settingsPane.add(testEvalCheck);
		settingsPane.add(testFormattedCheck);

		contentPane.add(settingsPane, BorderLayout.CENTER);

		this.dialog.pack();
	}

	protected JPanel buildButtonPanel()
	{
		JPanel buttonPanel = super.buildButtonPanel();

		this.clearButton = Main.createButton("dialog.appearance.clear");
		this.clearButton.addActionListener(this);
		buttonPanel.add(this.clearButton);

		return buttonPanel;
	}

	protected void editCells()
	{
		this.search();
	}

	protected void editColumns()
	{
		this.search();
	}

	protected synchronized void search()
	{
		if(this.dialog == null)
		{
			this.createDialog();
		}
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				TableSearch.this.dialog.setVisible(true);
			}
		});
	}

	protected Iterator buildSearchRange()
	{
		BSHTableModel model = (BSHTableModel)this.table.getModel();
		return new RangeIterator(1, 0, model.getLastNonEmptyColumn(), model.getLastNonEmptyRow());
	}

	protected Iterator buildSearchIterator()
	{
		ConditionalIterator.Condition searchCondition = new ConditionalIterator.Condition()
		{
			public boolean isSatisfied(Object contextValue)
			{
				if(contextValue == null || !(contextValue instanceof Point))
				{
					return false;
				}
				try
				{
					return contains.INSTANCE.cellContains(((BSHTableModel)TableSearch.this.table.getModel()).bsh,
					                                       null,
					                                       ((Point)contextValue).x,
					                                       ((Point)contextValue).y,
					                                       TableSearch.this.searchText.getText(),
					                                       !TableSearch.this.caseSensitiveCheck.isSelected(),
					                                       TableSearch.this.testRawCheck.isSelected(),
					                                       TableSearch.this.testEvalCheck.isSelected(),
					                                       TableSearch.this.testFormattedCheck.isSelected());
				}
				catch(EvalError ex) // shouldn't happen
				{
					ex.printStackTrace();
					return false;
				}
			}
		};

		return new ConditionalIterator(this.buildSearchRange(), searchCondition);
	}

	protected void doNext()
	{
		if(this.searchIterator == null)
		{
			this.searchIterator = this.buildSearchIterator();
			this.setSearchUIEnabled(false);
		}

		if(this.searchIterator.hasNext())
		{
			Point coord = (Point)this.searchIterator.next();

			coord.x = this.table.convertColumnIndexToView(coord.x);

			this.table.setRowSelectionInterval(coord.y, coord.y);
			this.table.setColumnSelectionInterval(coord.x, coord.x);

			this.table.scrollRectToVisible(this.table.getCellRect(coord.y, coord.x, true));
		}
		else
		{
			this.searchIterator = null;
			this.setSearchUIEnabled(true);
		}
	}

	protected void setSearchUIEnabled(boolean enabled)
	{
		this.searchText.setEditable(enabled);
		this.caseSensitiveCheck.setEnabled(enabled);
	}

    public void actionPerformed(ActionEvent ev)
    {
        String actionCommand = ev.getActionCommand();
        if(actionCommand.equals(this.acceptCommand))
        {
			this.doNext();
        }
        else if(actionCommand.equals(this.clearCommand))
        {
			this.searchIterator = null;
			this.setSearchUIEnabled(true);
			this.searchText.selectAll();
			this.searchText.grabFocus();
		}
        else
        {
			super.actionPerformed(ev);
		}
    }
}
