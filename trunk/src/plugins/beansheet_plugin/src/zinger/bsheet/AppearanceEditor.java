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

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class AppearanceEditor extends DialogEditor implements ChangeListener
{
	protected JColorChooser fgColorChooser;
	protected JColorChooser bgColorChooser;

	protected JComboBox fontFamilyPicker;
	protected JSpinner fontSizeSpinner;
	protected JCheckBox boldCheckBox;
	protected JCheckBox italicCheckBox;

	protected JComboBox hAlignmentSelection;
	protected JComboBox vAlignmentSelection;

	protected JComponent sample;

	protected JButton clearButton;
	protected boolean clearAccepted;

	private Appearance appearance;

	public AppearanceEditor(JTable table)
	{
        super(table,
              Main.getConstant("dialog.appearance.title"),
              Main.getConstant("dialog.appearance.okay.action"),
              Main.getConstant("dialog.appearance.okay.label"),
              Main.getConstant("dialog.appearance.cancel.action"),
              Main.getConstant("dialog.appearance.cancel.label"),
              Main.getConstant("action.col_appearance"),
              Main.getConstant("action.cell_appearance"));
	}

	protected void editColumns()
	{
		int[] selectedColumns = this.table.getSelectedColumns();
		BSHTableModel model = (BSHTableModel)this.table.getModel();
		this.appearance = (selectedColumns.length == 1) ?
		                  model.getAppearance(this.table.convertColumnIndexToModel(selectedColumns[0]), -1, false) :
		                  null;
		if(this.appearance == null)
		{
			this.appearance = new Appearance();
		}
		this.prompt();
		if(this.changesAccepted)
		{
			Appearance appearance;
			for(int ci = 0; ci < selectedColumns.length; ++ci)
			{
				appearance = model.getAppearance(this.table.convertColumnIndexToModel(selectedColumns[ci]), -1, true);
				this.appearance.applyTo(appearance);
			}
			this.table.repaint();
		}
		else if(this.clearAccepted)
		{
			for(int ci = 0; ci < selectedColumns.length; ++ci)
			{
				model.removeAppearance(this.table.convertColumnIndexToModel(selectedColumns[ci]), -1);
			}
			this.table.repaint();
		}
	}

	protected void editCells()
	{
		int[] selectedColumns = this.table.getSelectedColumns();
		int[] selectedRows = this.table.getSelectedRows();
		BSHTableModel model = (BSHTableModel)this.table.getModel();
		this.appearance = (selectedColumns.length == 1 && selectedRows.length == 1) ?
		                  model.getAppearance(this.table.convertColumnIndexToModel(selectedColumns[0]), selectedRows[0], false) :
		                  null;
		if(this.appearance == null)
		{
			this.appearance = new Appearance();
		}
		this.prompt();
		if(this.changesAccepted)
		{
			Appearance appearance;
			int modelColumnIndex;
			for(int ci = 0; ci < selectedColumns.length; ++ci)
			{
				modelColumnIndex = this.table.convertColumnIndexToModel(selectedColumns[ci]);
				for(int ri = 0; ri < selectedRows.length; ++ri)
				{
					appearance = model.getAppearance(modelColumnIndex, selectedRows[ri], true);
					this.appearance.applyTo(appearance);
				}
			}
			this.table.repaint();
		}
		else if(this.clearAccepted)
		{
			int modelColumnIndex;
			for(int ci = 0; ci < selectedColumns.length; ++ci)
			{
				modelColumnIndex = this.table.convertColumnIndexToModel(selectedColumns[ci]);
				for(int ri = 0; ri < selectedRows.length; ++ri)
				{
					model.removeAppearance(modelColumnIndex, selectedRows[ri]);
				}
			}
		}
	}

	protected synchronized void prompt()
	{
		if(this.dialog == null)
		{
			this.createDialog();
		}
		this.clearAccepted = false;
		this.changesAccepted = false;

		this.fgColorChooser.setColor(this.appearance.getFgColor());
		this.bgColorChooser.setColor(this.appearance.getBgColor());

		Font f = this.appearance.getFont();
		if(f != null)
		{
			this.fontFamilyPicker.setSelectedItem(f.getName());
			this.fontSizeSpinner.setValue(new Integer(f.getSize()));
			this.boldCheckBox.setSelected(f.isBold());
			this.italicCheckBox.setSelected(f.isItalic());
		}

		Number hAlignment = this.appearance.getAlignmentX();
		if(hAlignment != null)
		{
			this.hAlignmentSelection.setSelectedIndex(this.getSelectionFromHAlignment(hAlignment.intValue()));
		}

		Number vAlignment = this.appearance.getAlignmentY();
		if(vAlignment != null)
		{
			this.vAlignmentSelection.setSelectedIndex(this.getSelectionFromVAlignment(vAlignment.intValue()));
		}

		this.updateSampleUI();
		this.dialog.setLocationRelativeTo(this.table.getTopLevelAncestor());
		this.dialog.setVisible(true);
		if(this.changesAccepted)
		{
			this.appearance.setFgColor(this.fgColorChooser.getColor());
			this.appearance.setBgColor(this.bgColorChooser.getColor());
			this.appearance.setFont(this.getCurrentFont());
			this.appearance.setAlignmentX(new Integer(this.getHAlignmentFromSelection(this.hAlignmentSelection.getSelectedIndex())));
			this.appearance.setAlignmentY(new Integer(this.getVAlignmentFromSelection(this.vAlignmentSelection.getSelectedIndex())));
		}
	}

	protected int getHAlignmentFromSelection(int hAlignmentSelection)
	{
		switch(hAlignmentSelection)
		{
			case 1: return SwingConstants.CENTER;
			case 2: return SwingConstants.RIGHT;
			default: return SwingConstants.LEFT;
		}
	}

	protected int getVAlignmentFromSelection(int vAlignmentSelection)
	{
		switch(vAlignmentSelection)
		{
			case 0: return SwingConstants.TOP;
			case 2: return SwingConstants.BOTTOM;
			default: return SwingConstants.CENTER;
		}
	}

	protected int getSelectionFromHAlignment(int hAlignment)
	{
		switch(hAlignment)
		{
			case SwingConstants.CENTER: return 1;
			case SwingConstants.RIGHT: return 2;
			default: return 0;
		}
	}

	protected int getSelectionFromVAlignment(int vAlignment)
	{
		switch(vAlignment)
		{
			case SwingConstants.TOP: return 0;
			case SwingConstants.BOTTOM: return 2;
			default: return 1;
		}
	}

	protected void createDialog()
	{
		super.createDialog();

		this.fgColorChooser = new JColorChooser(Color.black);
		this.fgColorChooser.setPreviewPanel(new JPanel());
		this.fgColorChooser.getSelectionModel().addChangeListener(this);

		this.bgColorChooser = new JColorChooser(Color.white);
		this.bgColorChooser.setPreviewPanel(new JPanel());
		this.bgColorChooser.getSelectionModel().addChangeListener(this);

		this.fontFamilyPicker = new JComboBox(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
		this.fontFamilyPicker.addActionListener(this);
		this.fontSizeSpinner = new JSpinner();
		this.fontSizeSpinner.setValue(new Integer(12));
		this.fontSizeSpinner.addChangeListener(this);
		this.boldCheckBox = new JCheckBox("Bold");
		this.boldCheckBox.addActionListener(this);
		this.italicCheckBox = new JCheckBox("Italic");
		this.italicCheckBox.addActionListener(this);
		JPanel fontPane = new JPanel();
		fontPane.add(this.fontFamilyPicker);
		fontPane.add(this.fontSizeSpinner);
		fontPane.add(this.boldCheckBox);
		fontPane.add(this.italicCheckBox);

		this.hAlignmentSelection = new JComboBox(new Object[]
		{
			Main.getConstant("dialog.appearance.alignment.left.label"),
			Main.getConstant("dialog.appearance.alignment.center.label"),
			Main.getConstant("dialog.appearance.alignment.right.label")
		});
		this.hAlignmentSelection.setEditable(false);
		this.hAlignmentSelection.setActionCommand(Main.getConstant("dialog.appearance.alignment.horizontal.action"));
		this.hAlignmentSelection.addActionListener(this);

		this.vAlignmentSelection = new JComboBox(new Object[]
		{
			Main.getConstant("dialog.appearance.alignment.top.label"),
			Main.getConstant("dialog.appearance.alignment.middle.label"),
			Main.getConstant("dialog.appearance.alignment.bottom.label")
		});
		this.vAlignmentSelection.setEditable(false);
		this.vAlignmentSelection.setActionCommand(Main.getConstant("dialog.appearance.alignment.vertical.action"));
		this.vAlignmentSelection.addActionListener(this);

		JPanel alignmentPane = new JPanel();
		alignmentPane.add(hAlignmentSelection);
		alignmentPane.add(vAlignmentSelection);


		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add(Main.getConstant("dialog.appearance.tab.foreground"), this.fgColorChooser);
		tabbedPane.add(Main.getConstant("dialog.appearance.tab.background"), this.bgColorChooser);
		tabbedPane.add(Main.getConstant("dialog.appearance.tab.font"), fontPane);
		tabbedPane.add(Main.getConstant("dialog.appearance.tab.alignment"), alignmentPane);

		this.sample = new JLabel(Main.getConstant("dialog.appearance.sample.text"));
		Dimension samplePreferredSize = this.sample.getPreferredSize();
		if(samplePreferredSize != null)
		{
			samplePreferredSize.height <<= 1;
			this.sample.setPreferredSize(samplePreferredSize);
		}

		Container contentPane = this.dialog.getContentPane();
		contentPane.add(this.sample, BorderLayout.NORTH);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

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

	public void stateChanged(ChangeEvent ev)
	{
		Object src = ev.getSource();
		if(src == this.bgColorChooser.getSelectionModel() ||
		   src == this.fgColorChooser.getSelectionModel() ||
		   src == this.fontSizeSpinner)
		{
			this.updateSampleUI();
		}
	}

	public void actionPerformed(ActionEvent ev)
	{
		Object src = ev.getSource();
		if(src == this.fontFamilyPicker ||
		   src == this.boldCheckBox ||
		   src == this.italicCheckBox ||
		   src == this.hAlignmentSelection ||
		   src == this.vAlignmentSelection)
		{
			this.updateSampleUI();
		}
		else if(src == this.clearButton)
		{
			this.clearAccepted = true;
			this.dialog.setVisible(false);
		}
		else
		{
			super.actionPerformed(ev);
		}
	}

	/**
	 * @since 1.0.5
	 */
	protected void updateSampleUI()
	{
		this.sample.setBackground(this.bgColorChooser.getColor());
		this.sample.setForeground(this.fgColorChooser.getColor());
		this.sample.setFont(this.getCurrentFont());
		if(this.sample instanceof JLabel)
		{
			((JLabel)this.sample).setHorizontalAlignment(this.getHAlignmentFromSelection(this.hAlignmentSelection.getSelectedIndex()));
			((JLabel)this.sample).setVerticalAlignment(this.getVAlignmentFromSelection(this.vAlignmentSelection.getSelectedIndex()));
		}
	}

	protected Font getCurrentFont()
	{
		int style = Font.PLAIN;
		if(this.boldCheckBox.isSelected())
		{
			style |= Font.BOLD;
		}
		if(this.italicCheckBox.isSelected())
		{
			style |= Font.ITALIC;
		}
		return new Font((String)this.fontFamilyPicker.getSelectedItem(), style, ((Number)this.fontSizeSpinner.getValue()).intValue());
	}

	/**
	 * @deprecated Use <code>updateSampleUI()</code>.
	 *
	 * @see #updateSampleUI()
	 */
	protected void updateSampleFont()
	{
		this.sample.setFont(this.getCurrentFont());
	}
}
