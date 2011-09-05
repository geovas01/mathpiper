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
import java.io.*;
import java.nio.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

/**
 * Cell editor that uses a dialog window.
 * Allows multiline content.
 *
 * @author Alexey Zinger (inline_four@yahoo.com)
 */
public class PopUpCellEditor implements ActionListener, Runnable
{
	/**
	 * Instance of the table the editor is used for.
	 */
	protected JTable table;

	private JTextComponent textComponent;
	private JDialog dialog;
	private boolean changesAccepted;

	/**
	 * Constructs an instance using specified table instance.
	 */
	public PopUpCellEditor(JTable table)
	{
		this.table = table;
	}

	/**
	 * The main method used for launching the dialog.
	 * The method implements <code>Runnable</code> interface and is used for calling
	 * <code>EventQueue.invokeLater(Runnable)</code> method.  This implementation is
	 * synchronized, so only one thread can launch the cell editor at a time.
	 */
	public synchronized void run()
	{
		int row = this.table.getSelectedRow();
		int column = this.table.getSelectedColumn();
		if(row < 0 || column < 0)
		{
			return;
		}
		int modelColumn = this.table.convertColumnIndexToModel(column);
		Object value = this.table.getValueAt(row, column);
		if(this.table.getModel() instanceof BSHTableModel)
		{
			value = ((BSHTableModel)this.table.getModel()).formatValueAt(value, modelColumn, row);
		}

		if(this.dialog == null)
		{
			this.createDialog();
		}
		this.textComponent.setText(String.valueOf(value));
		this.dialog.pack();
		this.dialog.setLocationRelativeTo(this.table.getTopLevelAncestor());
		this.changesAccepted = false;
		this.dialog.setVisible(true);
		if(this.changesAccepted)
		{
            this.table.setValueAt(this.textComponent.getText(), row, column);
		}
	}

	/**
	 * Loads contents of a text file into the text component.
	 *
	 * @since 1.0.1
	 */
	protected void load(File file)
	{
		FileReader reader = null;
		try
		{
			reader = new FileReader(file);
			StringBuffer sb = new StringBuffer();
			char[] cb = new char[1024];
			int l;
			while((l = reader.read(cb)) > 0)
			{
				sb.append(cb, 0, l);
			}
			this.textComponent.setText(sb.toString());
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				if(reader != null)
				{
					reader.close();
				}
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Handles menu item and button events.
	 */
	public void actionPerformed(ActionEvent ev)
	{
		String actionCommand = ev.getActionCommand();
		if(actionCommand.equals(Main.getConstant("action.celleditor.okay")))
		{
			this.changesAccepted = true;
			this.dialog.setVisible(false);
		}
		else if(actionCommand.equals(Main.getConstant("action.celleditor.cancel")))
		{
			this.changesAccepted = false;
			this.dialog.setVisible(false);
		}
		else if(actionCommand.equals(Main.getConstant("action.open")))
		{
			JFileChooser fileChooser = new JFileChooser();
			if(fileChooser.showOpenDialog(this.dialog) == JFileChooser.APPROVE_OPTION)
			{
				this.load(fileChooser.getSelectedFile());
			}
		}
		else
		{
			EventQueue.invokeLater(this);
		}
	}

	/**
	 * Not thread safe.
	 */
	protected void createDialog()
	{
		Container tla = this.table.getTopLevelAncestor();
		if(tla instanceof Frame)
		{
			this.dialog = new JDialog((Frame)tla, Main.getConstant("dialog.celleditor.title"), true);
		}
		else if(tla instanceof Dialog)
		{
			this.dialog = new JDialog((Dialog)tla, Main.getConstant("dialog.celleditor.title"), true);
		}
        this.dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        JPanel contentPane = new JPanel(new BorderLayout());

        this.textComponent = new JTextArea();
        this.textComponent.setFont(Main.getFixedWidthFont());
        JScrollPane textScrollPane = new JScrollPane();
        textScrollPane.getViewport().setView(this.textComponent);
        contentPane.add(textScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton acceptButton = Main.createButton("dialog.celleditor.okay");
        acceptButton.addActionListener(this);
        buttonPanel.add(acceptButton);
        JButton cancelButton = Main.createButton("dialog.celleditor.cancel");
        cancelButton.addActionListener(this);
        buttonPanel.add(cancelButton);
        JButton browseButton = Main.createButton("dialog.celleditor.browse");
        browseButton.addActionListener(this);
        buttonPanel.add(browseButton);

        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        this.dialog.setContentPane(contentPane);
	}
}
