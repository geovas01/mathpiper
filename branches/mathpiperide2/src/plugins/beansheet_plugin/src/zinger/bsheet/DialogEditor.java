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

import javax.swing.*;

/**
 * Base class for various dialogs applicable to cell or column ranges of a <code>JTable</code>.
 * This abstract class provides basic logic for consuming events from accept and cancel buttons (OK, Cancel),
 * and menu items indicating column or cell set action.
 *
 * @author Alexey Zinger (inline_four@yahoo.com)
 */
public abstract class DialogEditor implements ActionListener
{
    protected final JTable table;

    protected JDialog dialog;
    protected JButton acceptButton;
    protected JButton cancelButton;

    protected final Object lock = new Object();

    protected boolean changesAccepted = false;

    protected final String acceptCommand;
    protected final String cancelCommand;
    protected final String colEditCommand;
    protected final String cellEditCommand;

    protected final String acceptLabel;
    protected final String cancelLabel;

    protected final String dialogTitle;

    public DialogEditor(JTable table,
                        String dialogTitle,
                        String acceptCommand,
                        String acceptLabel,
                        String cancelCommand,
                        String cancelLabel,
                        String colEditCommand,
                        String cellEditCommand)
    {
		this.table = table;
		this.dialogTitle = dialogTitle;
		this.acceptCommand = acceptCommand;
		this.acceptLabel = acceptLabel;
		this.cancelCommand = cancelCommand;
		this.cancelLabel = cancelLabel;
		this.colEditCommand = colEditCommand;
		this.cellEditCommand = cellEditCommand;
	}

    public void actionPerformed(ActionEvent ev)
    {
        String actionCommand = ev.getActionCommand();
        if(actionCommand.equals(this.acceptCommand))
        {
            this.changesAccepted = true;
            this.dialog.setVisible(false);
        }
        else if(actionCommand.equals(this.cancelCommand))
        {
            this.dialog.setVisible(false);
        }
        else if(actionCommand.equals(this.colEditCommand))
        {
            this.editColumns();
        }
        else if(actionCommand.equals(this.cellEditCommand))
        {
            this.editCells();
        }
    }

    protected JPanel buildButtonPanel()
    {
        JPanel buttonPanel = new JPanel(new FlowLayout());

        this.acceptButton = new JButton(this.acceptLabel);
        this.acceptButton.setActionCommand(this.acceptCommand);
        this.acceptButton.addActionListener(this);
        buttonPanel.add(this.acceptButton);

        this.cancelButton = new JButton(this.cancelLabel);
        this.cancelButton.setActionCommand(this.cancelCommand);
        this.cancelButton.addActionListener(this);
        buttonPanel.add(this.cancelButton);

        return buttonPanel;
	}

    protected void createDialog()
    {
		Frame frame = (Frame)this.table.getTopLevelAncestor();
        this.dialog = new JDialog(frame, this.dialogTitle, true);
        this.dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        JPanel contentPane = new JPanel(new BorderLayout());
        JPanel buttonPanel = this.buildButtonPanel();
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        this.dialog.setContentPane(contentPane);
        this.dialog.pack();
        this.dialog.setLocationRelativeTo(frame);
    }

    protected abstract void editColumns();
    protected abstract void editCells();
}
