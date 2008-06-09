//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathrider.yacasdocsplugin;

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

public class YacasDocsToolPanel extends JPanel {
	private YacasDocs pad;

	private JLabel label;
	
	private javax.swing.JButton sourceButton;

	public YacasDocsToolPanel(YacasDocs qnpad) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		pad = qnpad;

		Box labelBox = new Box(BoxLayout.Y_AXIS);
		labelBox.add(Box.createGlue());

		label = new JLabel("test");
		label.setVisible(jEdit.getProperty(
				YacasDocsPlugin.OPTION_PREFIX + "show-filepath").equals(
				"true"));

		labelBox.add(label);
		labelBox.add(Box.createGlue());

		add(labelBox);

		add(Box.createGlue());

		
		//View source button.
		String toolTip = jEdit.getProperty("yacasdocs.source.label");
		sourceButton = new javax.swing.JButton(jEdit.getProperty("yacasdocs.source.button-text"));
		sourceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				YacasDocsToolPanel.this.pad.source();
			}
		});
		
		sourceButton.setEnabled(false);

		sourceButton.setToolTipText(toolTip);
		add(sourceButton);
		
		
		
		add(Box.createGlue());

		add(makeCustomButton("yacasdocs.home", new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				YacasDocsToolPanel.this.pad.home();
			}
		}));
		
		/*
		add(makeCustomButton("jyacas.save-file", new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				YacasDocsToolPanel.this.pad.saveFile();
			}
		}));
		add(makeCustomButton("jyacas.copy-to-buffer",
				new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						YacasDocsToolPanel.this.pad.copyToBuffer();
					}
				}));
		*/
		
	}//end constructor.
	
	public void sourceButtonEnabled(Boolean state)
	{
		sourceButton.setEnabled(state);
	}//end method.

	void propertiesChanged() {
		label.setText(pad.getFilename());
		label.setVisible(jEdit.getProperty(
				YacasDocsPlugin.OPTION_PREFIX + "show-filepath").equals(
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
