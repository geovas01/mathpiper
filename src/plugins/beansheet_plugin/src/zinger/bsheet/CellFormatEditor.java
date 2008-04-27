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

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.text.*;

import zinger.bsheet.text.*;

/**
 * Format picker used for cell and column formatting.
 * A <code>Format</code> object is created using its class and a format pattern string.
 * Therefore <code>Format</code> implementations used must
 * <ul>
 * <li>have a public parameterless constructor,
 * <li>have public <code>applyPattern(String)</code> and <code>toPattern()</code> methods,
 * <li>be Java Beans-compatible for persistance.
 * </ul>
 * <p>
 * The area of format editing is going to be readdressed in the near future to be more
 * compatible with the Java Beans-based persistance logic and to allow easy custom formatting
 * definitions.
 *
 * @author Alexey Zinger (inline_four@yahoo.com)
 */
public class CellFormatEditor extends DialogEditor
{
    protected static final Class[] PATTERN_FORMAT_CONSTRUCTOR_SIGNATURE = new Class[] { String.class };
    protected static final Class[] NO_PARAMS_SIGNATURE = new Class[] {};

    protected static final Class[] DEFAULT_FORMAT_CLASSES = new Class[]
    {
        SimpleDateFormatBean.class,
        DecimalFormatBean.class,
        MessageFormatBean.class
    };

    protected JComboBox formatType;
    protected JComboBox formatPattern;

    protected final Class[] formatClasses;

	/**
	 * Uses <code>DEFAULT_FORMAT_CLASSES</code> to call <code>CellFormatEditor(JTable, Class[])</code>.
	 *
	 * @see #DEFAULT_FORMAT_CLASSES
	 */
    public CellFormatEditor(JTable table)
    {
        this(table, CellFormatEditor.DEFAULT_FORMAT_CLASSES);
    }

    public CellFormatEditor(JTable table, Class[] formatClasses)
    {
        super(table,
              Main.getConstant("dialog.format.title"),
              Main.getConstant("dialog.format.okay.action"),
              Main.getConstant("dialog.format.okay.label"),
              Main.getConstant("dialog.format.cancel.action"),
              Main.getConstant("dialog.format.cancel.label"),
              Main.getConstant("action.col_format"),
              Main.getConstant("action.cell_format"));
        this.formatClasses = formatClasses;
    }

	/**
	 * Uses <code>askFormat(Format)</code> to pick a format and apply it to selected columns.
	 *
	 * @see #askFormat(java.text.Format)
	 */
    protected void editColumns()
    {
        int[] selectedColumns = this.table.getSelectedColumns();
        BSHTableModel model = (BSHTableModel)this.table.getModel();
        Format format = (selectedColumns.length == 1) ?
            model.getFormat(this.table.convertColumnIndexToModel(selectedColumns[0]), -1) :
            null;
        format = this.askFormat(format);
        if(this.changesAccepted)
        {
            for(int i = 0; i < selectedColumns.length; ++i)
            {
                model.setFormat(format, this.table.convertColumnIndexToModel(selectedColumns[i]), -1);
            }
        }
    }

	/**
	 * Uses <code>askFormat(Format)</code> to pick a format and apply it to selected cells.
	 *
	 * @see #askFormat(java.text.Format)
	 */
    protected void editCells()
    {
		int[] selectedColumns = this.table.getSelectedColumns();
		int[] selectedRows = this.table.getSelectedRows();
		BSHTableModel model = (BSHTableModel)this.table.getModel();
		Format format = (selectedColumns.length == 1 && selectedRows.length == 1) ?
		                model.getFormat(this.table.convertColumnIndexToModel(selectedColumns[0]), selectedRows[0]) :
		                null;
		format = this.askFormat(format);
		if(this.changesAccepted)
		{
			int modelColumnIndex;
			for(int ci = 0; ci < selectedColumns.length; ++ci)
			{
				modelColumnIndex = this.table.convertColumnIndexToModel(selectedColumns[ci]);
				for(int ri = 0; ri < selectedRows.length; ++ri)
				{
					model.setFormat(format, modelColumnIndex, selectedRows[ri]);
				}
			}
		}
    }

	/**
	 * Pops up a format editor dialog window.
	 * Upon closing of the dialog window, a <code>Format</code> object is created if the dialog was
	 * accepted.
	 *
	 * @see #createFormat(int, java.lang.String)
	 */
    public Format askFormat(Format format)
    {
        if(this.dialog == null)
        {
            this.createDialog();
        }
        this.populateFromFormat(format);
        this.changesAccepted = false;
        this.dialog.setLocationRelativeTo(this.table.getTopLevelAncestor());
        this.dialog.setVisible(true);
        if(this.changesAccepted)
        {
			int formatIndex = this.formatType.getSelectedIndex() - 1;
            format = formatIndex < 0 ?
                     null :
                     this.createFormat(formatIndex, (String)this.formatPattern.getSelectedItem());
        }
        return format;
    }

    protected Format createFormat(int formatIndex, String formatPattern)
    {
		try
        {
            return (Format)this.formatClasses[formatIndex].getConstructor(CellFormatEditor.PATTERN_FORMAT_CONSTRUCTOR_SIGNATURE).newInstance(new Object[] {formatPattern});
        }
        catch(NoSuchMethodException ex)
        {
            ex.printStackTrace();
        }
        catch(SecurityException ex)
        {
            ex.printStackTrace();
        }
        catch(IllegalAccessException ex)
        {
            ex.printStackTrace();
        }
        catch(IllegalArgumentException ex)
        {
            ex.printStackTrace();
        }
        catch(InstantiationException ex)
        {
            ex.printStackTrace();
        }
        catch(InvocationTargetException ex)
        {
            ex.printStackTrace();
        }
        catch(ExceptionInInitializerError ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    protected void populateFromFormat(Format format)
    {
        if(format == null)
        {
			this.formatType.setSelectedIndex(0);
            this.formatPattern.setSelectedItem("");
            return;
        }
        Class formatClass = format.getClass();
        int formatIndex;
        for(formatIndex = 0; formatIndex < this.formatClasses.length; ++formatIndex)
        {
            if(formatClass.equals(this.formatClasses[formatIndex]))
            {
                this.formatType.setSelectedIndex(formatIndex + 1);
                break;
            }
        }
        try
        {
            this.formatPattern.setSelectedItem((String)formatClass.getMethod("toPattern", CellFormatEditor.NO_PARAMS_SIGNATURE).invoke(format, (Object[])CellFormatEditor.NO_PARAMS_SIGNATURE));
        }
        catch(NoSuchMethodException ex)
        {
            ex.printStackTrace();
        }
        catch(SecurityException ex)
        {
            ex.printStackTrace();
        }
        catch(IllegalAccessException ex)
        {
            ex.printStackTrace();
        }
        catch(IllegalArgumentException ex)
        {
            ex.printStackTrace();
        }
        catch(InvocationTargetException ex)
        {
            ex.printStackTrace();
        }
        catch(ExceptionInInitializerError ex)
        {
            ex.printStackTrace();
        }
    }

    protected String getFormatLabel(int formatIndex)
    {
		String formatLabel = Main.getConstant("format." + this.formatClasses[formatIndex].getName() + ".label");
		if(formatLabel == null)
		{
			formatLabel = this.formatClasses[formatIndex].getName();
		}
		return formatLabel;
	}

    protected void createDialog()
    {
		super.createDialog();

		Container contentPane = this.dialog.getContentPane();
        String[] formatClassLabels = new String[this.formatClasses.length + 1];
        formatClassLabels[0] = "None";
        for(int i = 0; i < formatClasses.length; ++i)
        {
            formatClassLabels[i + 1] = this.getFormatLabel(i);
        }
        this.formatType = new JComboBox(formatClassLabels);
        this.formatType.addActionListener(this);
        contentPane.add(this.formatType, BorderLayout.WEST);
        this.formatPattern = new JComboBox();
        this.formatPattern.setEditable(true);
        this.formatPattern.setFont(Main.getFixedWidthFont());
        Dimension preferred = formatPattern.getPreferredSize();
        preferred.width += 300;
        this.formatPattern.setPreferredSize(preferred);
        contentPane.add(this.formatPattern, BorderLayout.CENTER);

        this.dialog.pack();
        this.dialog.setResizable(false);
    }

    public void actionPerformed(ActionEvent ev)
    {
		if(ev.getSource() == this.formatType)
		{
			this.refreshFormatPattern();
		}
		else
		{
			super.actionPerformed(ev);
		}
	}

	protected void refreshFormatPattern()
	{
		this.formatPattern.removeAllItems();
		int formatIndex = this.formatType.getSelectedIndex() - 1;
		if(formatIndex < 0)
		{
			return;
		}
		String formatPatterns = Main.getConstant("format." + this.formatClasses[formatIndex].getName() + ".patterns");
		if(formatPatterns == null)
		{
			return;
		}
		try
		{
			for(StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(formatPatterns)); tokenizer.nextToken() != StreamTokenizer.TT_EOF; )
			{
				this.formatPattern.addItem(tokenizer.sval);
			}
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
}
