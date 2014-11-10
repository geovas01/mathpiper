//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathpiper.ide.wolframplugin;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.Box;

import org.gjt.sp.jedit.AbstractOptionPane;
import org.gjt.sp.jedit.GUIUtilities;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.gui.FontSelector;

public class WolframOptionPane extends AbstractOptionPane implements
	ActionListener {

	private JTextField hostnameTextField;
	private JTextField usernameTextField;
	private JTextField passwordTextField;



	public WolframOptionPane() {
		super(WolframPlugin.NAME);
	}

	public void _init() {


		JPanel optionsPanel = new JPanel();
		
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.PAGE_AXIS));
		JButton saveButton = new JButton(jEdit.getProperty(WolframPlugin.OPTION_PREFIX + "save-options-button-label"));
		saveButton.addActionListener(this);
		
		optionsPanel.add(new JLabel("hostname"));
		hostnameTextField = new JTextField(jEdit.getProperty(WolframPlugin.OPTION_PREFIX + "hostname"));
		optionsPanel.add(hostnameTextField);
		optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		
		optionsPanel.add(new JLabel("username"));
		usernameTextField = new JTextField(jEdit.getProperty(WolframPlugin.OPTION_PREFIX + "username"));
		optionsPanel.add(usernameTextField);
		optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		
		optionsPanel.add(new JLabel("password"));
		passwordTextField = new JTextField(jEdit.getProperty(WolframPlugin.OPTION_PREFIX + "password"));
		optionsPanel.add(passwordTextField);
		optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		
		optionsPanel.add(Box.createRigidArea(new Dimension(0,5)));
		optionsPanel.add(saveButton);

		addComponent("WOLFRAM", optionsPanel);


	}

	public void _save() {
		jEdit.setProperty(WolframPlugin.OPTION_PREFIX + "hostname", hostnameTextField.getText());
		jEdit.setProperty(WolframPlugin.OPTION_PREFIX + "username", usernameTextField.getText());
		jEdit.setProperty(WolframPlugin.OPTION_PREFIX + "password", passwordTextField.getText());

	}

	// end AbstractOptionPane implementation

	// begin ActionListener implementation
	public void actionPerformed(ActionEvent evt) {

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
