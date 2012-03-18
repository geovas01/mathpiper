//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathpiper.ide.mathpiperdocsplugin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

import org.gjt.sp.jedit.GUIUtilities;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.gui.RolloverButton;

public class MathPiperDocsToolPanel extends JPanel {
	private MathPiperDocs pad;

	private JLabel label;
	
	private javax.swing.JButton sourceButton;
	private javax.swing.AbstractButton backButton;
	private javax.swing.AbstractButton forwardButton;

	public MathPiperDocsToolPanel(MathPiperDocs qnpad) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		pad = qnpad;

		//Box labelBox = new Box(BoxLayout.Y_AXIS);
		//labelBox.add(Box.createGlue());

		//label = new JLabel("test");
		//label.setVisible(jEdit.getProperty(MathPiperDocsPlugin.OPTION_PREFIX + "show-filepath").equals("true"));

		//labelBox.add(label);
		//labelBox.add(Box.createGlue());

		//add(labelBox);

		//add(Box.createGlue());

		
		//View source button.
		String toolTip = jEdit.getProperty("mathpiperdocs.source.label");
		sourceButton = new javax.swing.JButton(jEdit.getProperty("mathpiperdocs.source.button-text"));
		sourceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				MathPiperDocsToolPanel.this.pad.source();
			}
		});
		sourceButton.setEnabled(false);
		sourceButton.setToolTipText(toolTip);
		add(sourceButton);
		
		//Collapse tree button.
		toolTip = jEdit.getProperty("mathpiperdocs.collapse.label");
		JButton collapseButton = new javax.swing.JButton(jEdit.getProperty("mathpiperdocs.collapse.button-text"));
		collapseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				MathPiperDocsToolPanel.this.pad.collapse();
			}
		});
		collapseButton.setEnabled(true);
		collapseButton.setToolTipText(toolTip);
		add(collapseButton);
		
		
		
		add(Box.createGlue());

		//back button.
		backButton = makeCustomButton("mathpiperdocs.back", new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				MathPiperDocsToolPanel.this.pad.back();
			}
		});
		backButton.setEnabled(false);
		add(backButton);
		
		
		//forward button.
		forwardButton = makeCustomButton("mathpiperdocs.forward", new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				MathPiperDocsToolPanel.this.pad.forward();
			}
		});
		forwardButton.setEnabled(false);
		add(forwardButton);
		
		add(makeCustomButton("mathpiperdocs.home", new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				MathPiperDocsToolPanel.this.pad.home();
			}
		}));

		
		/*
		add(makeCustomButton("piper.save-file", new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				MathPiperDocsToolPanel.this.pad.saveFile();
			}
		}));
		add(makeCustomButton("piper.copy-to-buffer",
				new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						MathPiperDocsToolPanel.this.pad.copyToBuffer();
					}
				}));
		*/
		
	}//end constructor.
	
	public void sourceButtonEnabled(Boolean state)
	{
		sourceButton.setEnabled(state);
	}//end method.
	
	
	public void backButtonEnabled(Boolean state)
	{
		backButton.setEnabled(state);
	}//end method.
	
	
	public void forwardButtonEnabled(Boolean state)
	{
		forwardButton.setEnabled(state);
	}//end method.

	void propertiesChanged() {
		label.setText(pad.getFilename());
		label.setVisible(jEdit.getProperty(
				MathPiperDocsPlugin.OPTION_PREFIX + "show-filepath").equals(
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
