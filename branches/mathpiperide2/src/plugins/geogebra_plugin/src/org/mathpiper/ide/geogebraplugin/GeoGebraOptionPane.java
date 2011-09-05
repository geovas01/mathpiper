//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathpiper.ide.geogebraplugin;

/*
 * GeogebraOptionPane.java
 * part of the Geogebra plugin for the jEdit text editor
 * Copyright (C) 2008 Ted Kosan

 *
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
 *
 * $Id: GeogebraOptionPane.java 5275 2005-09-10 19:40:17Z ezust $
 */

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.gjt.sp.jedit.AbstractOptionPane;
import org.gjt.sp.jedit.GUIUtilities;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.gui.FontSelector;

public class GeoGebraOptionPane extends AbstractOptionPane implements
		ActionListener {
	private JCheckBox showPath;

	private JTextField pathName;

	private FontSelector font;

	public GeoGebraOptionPane() {
		super(GeoGebraPlugin.NAME);
	}

	public void _init() {
		showPath = new JCheckBox(jEdit
				.getProperty(GeoGebraPlugin.OPTION_PREFIX
						+ "show-filepath.title"), jEdit.getProperty(
				GeoGebraPlugin.OPTION_PREFIX + "show-filepath").equals(
				"true"));
		addComponent(showPath);

		pathName = new JTextField(jEdit
				.getProperty(GeoGebraPlugin.OPTION_PREFIX + "filepath"));
		JButton pickPath = new JButton(jEdit
				.getProperty(GeoGebraPlugin.OPTION_PREFIX + "choose-file"));
		pickPath.addActionListener(this);

		JPanel pathPanel = new JPanel(new BorderLayout(0, 0));
		pathPanel.add(pathName, BorderLayout.CENTER);
		pathPanel.add(pickPath, BorderLayout.EAST);

		addComponent(jEdit.getProperty(GeoGebraPlugin.OPTION_PREFIX
				+ "file"), pathPanel);

		font = new FontSelector(makeFont());
		addComponent(jEdit.getProperty(GeoGebraPlugin.OPTION_PREFIX
				+ "choose-font"), font);
	}

	public void _save() {
		jEdit.setProperty(GeoGebraPlugin.OPTION_PREFIX + "filepath",
				pathName.getText());
		Font _font = font.getFont();
		jEdit.setProperty(GeoGebraPlugin.OPTION_PREFIX + "font", _font
				.getFamily());
		jEdit.setProperty(GeoGebraPlugin.OPTION_PREFIX + "fontsize", String
				.valueOf(_font.getSize()));
		jEdit.setProperty(GeoGebraPlugin.OPTION_PREFIX + "fontstyle",
				String.valueOf(_font.getStyle()));
		jEdit.setProperty(GeoGebraPlugin.OPTION_PREFIX + "show-filepath",
				String.valueOf(showPath.isSelected()));
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

	// helper method to get Font from plugin properties
	static public Font makeFont() {
		int style, size;
		String family = jEdit.getProperty(GeoGebraPlugin.OPTION_PREFIX
				+ "font");
		try {
			size = Integer
					.parseInt(jEdit
							.getProperty(GeoGebraPlugin.OPTION_PREFIX
									+ "fontsize"));
		} catch (NumberFormatException nf) {
			size = 14;
		}
		try {
			style = Integer
					.parseInt(jEdit
							.getProperty(GeoGebraPlugin.OPTION_PREFIX
									+ "fontstyle"));
		} catch (NumberFormatException nf) {
			style = Font.PLAIN;
		}
		return new Font(family, style, size);
	}

}//end class.

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
