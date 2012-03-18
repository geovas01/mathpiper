//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathpiper.ide.fricasplugin;

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

public class FriCASToolPanel extends JPanel {
	private FriCAS pad;

	private JLabel label;

	public FriCASToolPanel(FriCAS qnpad) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		pad = qnpad;

		Box labelBox = new Box(BoxLayout.Y_AXIS);
		labelBox.add(Box.createGlue());

	}

	void propertiesChanged() {

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
