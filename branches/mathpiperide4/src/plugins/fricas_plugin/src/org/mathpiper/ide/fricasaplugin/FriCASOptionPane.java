//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathpiper.ide.fricasplugin;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.gjt.sp.jedit.AbstractOptionPane;
import org.gjt.sp.jedit.GUIUtilities;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.gui.FontSelector;

public class FriCASOptionPane extends AbstractOptionPane implements
	ActionListener {


	private JTextField pathName;



	public FriCASOptionPane() {
		super(FriCASPlugin.NAME);
	}

	public void _init() {

		pathName = new JTextField(jEdit.getProperty(FriCASPlugin.OPTION_PREFIX + "path"));
		JButton pickPath = new JButton(jEdit.getProperty(FriCASPlugin.OPTION_PREFIX + "choose-file"));
		pickPath.addActionListener(this);

		JPanel pathPanel = new JPanel(new BorderLayout(0, 0));
		JLabel instructions = new JLabel("Path to the CAS executable.");
		pathPanel.add(instructions,BorderLayout.NORTH);
		pathPanel.add(pathName, BorderLayout.CENTER);
		pathPanel.add(pickPath, BorderLayout.EAST);

		addComponent(jEdit.getProperty(FriCASPlugin.OPTION_PREFIX + "file"), pathPanel);


	}

	public void _save() {
		jEdit.setProperty(FriCASPlugin.OPTION_PREFIX + "path", pathName.getText());

	}

	// end AbstractOptionPane implementation

	// begin ActionListener implementation
	public void actionPerformed(ActionEvent evt) {
		String[] paths = GUIUtilities.showVFSFileDialog(null, null,
		                 JFileChooser.OPEN_DIALOG, false);
		if (paths != null) {
			pathName.setText(paths[0]);
		}
	}


}//


/* {{{ License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */ //}}}
