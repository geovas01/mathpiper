//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathpiper.ide.geogebraplugin;

/*
 * GeoGebraToolPanel.java
 * part of the GeoGebra plugin for the jEdit text editor
 * Copyright (C) 2008 Ted Kosan.
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
 * $Id: GeoGebraToolPanel.java 5275 2005-09-10 19:40:17Z ezust $
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.gjt.sp.jedit.GUIUtilities;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.gui.RolloverButton;

public class GeoGebraToolPanel extends JPanel {
	private GeoGebra pad;

	private JLabel label;

	public GeoGebraToolPanel(GeoGebra qnpad) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		pad = qnpad;

		Box labelBox = new Box(BoxLayout.Y_AXIS);
		labelBox.add(Box.createGlue());

		label = new JLabel("This is an experimental version of GeoGebra. Please do not distribute it outside of MathPiperIDE.");
		//JLabel title = new JLabel("Want a Raise?", JLabel.CENTER);
		//title.setFont(new Font("Serif", Font.BOLD, 48));

		label.setForeground(java.awt.Color.BLUE);
		
		label.setVisible(jEdit.getProperty(
				GeoGebraPlugin.OPTION_PREFIX + "show-filepath").equals(
				"true"));

		labelBox.add(label);
		labelBox.add(Box.createGlue());

		add(labelBox);

		add(Box.createGlue());

		add(makeCustomButton("geogebra.reset", new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				GeoGebraToolPanel.this.pad.reset();
			}
		}));
		/*
		add(makeCustomButton("piper.save-file", new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				GeoGebraToolPanel.this.pad.saveFile();
			}
		}));
		add(makeCustomButton("piper.copy-to-buffer",
				new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						GeoGebraToolPanel.this.pad.copyToBuffer();
					}
				}));
		*/
		
	}//end constructor.

	void propertiesChanged() {
		label.setText(pad.getFilename());
		label.setVisible(jEdit.getProperty(
				GeoGebraPlugin.OPTION_PREFIX + "show-filepath").equals(
				"true"));
	}

	private AbstractButton makeCustomButton(String name, ActionListener listener) {
		String toolTip = jEdit.getProperty(name.concat(".label"));
		AbstractButton b = new RolloverButton(GUIUtilities.loadIcon(jEdit
				.getProperty(name + ".icon")));
		if (listener != null) {
			b.addActionListener(listener);
			b.setEnabled(true);
		} else {
			b.setEnabled(false);
		}
		b.setToolTipText(toolTip);
		return b;
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
