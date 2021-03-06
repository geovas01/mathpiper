//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathpiper.ide.mathpipercalcplugin;

// {{{ imports
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import org.gjt.sp.jedit.EBComponent;
import org.gjt.sp.jedit.EBMessage;
import org.gjt.sp.jedit.EditBus;
import org.gjt.sp.jedit.GUIUtilities;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.gui.DefaultFocusComponent;
import org.gjt.sp.jedit.gui.DockableWindowManager;
import org.gjt.sp.jedit.msg.PropertiesChanged;
import org.gjt.sp.util.Log;
import org.gjt.sp.util.StandardUtilities;

// }}}

// {{{ Piper class
/**
 * 
 * Piper - a dockable JPanel, a demonstration of a jEdit plugin.
 *
 */
public class MathPiperCalc extends JPanel
	implements EBComponent, MathPiperCalcActions, DefaultFocusComponent {

	// {{{ Instance Variables
	//private static final long serialVersionUID = 6412255692894321789L;

	private String filename;

	private String defaultFilename;

	private View view;

	private boolean floating;

	private MathPiperCalcToolPanel toolPanel;
	
	private static MathPiperCalc jFreeChart;
	// }}}

	// {{{ Constructor
	/**
	 * 
	 * @param view the current jedit window
	 * @param position a variable passed in from the script in actions.xml,
	 * 	which can be DockableWindowManager.FLOATING, TOP, BOTTOM, LEFT, RIGHT, etc.
	 * 	see @ref DockableWindowManager for possible values.
	 */
	public MathPiperCalc(View view, String position) {
		super(new BorderLayout());
		this.view = view;
		this.floating = position.equals(DockableWindowManager.FLOATING);


		//this.toolPanel = new MathPiperCalcToolPanel(this);
		//add(BorderLayout.NORTH, this.toolPanel);

		//JLabel testLabel = new JLabel("TEST");
		//this.add(testLabel);

		JPanel panel  = new org.mathpiper.ui.gui.calculator.CalculatorPanel();
		this.add(panel);

		
		
		if (floating) this.setPreferredSize(new Dimension(500, 250));


		
		jFreeChart = this;

	}//end constructor
	// }}}

	
	public static JPanel getMathPiperCalc()
	{
		return jFreeChart;
	}//end method.
	
	

	
	public boolean remove()
	{
		return false;
	};
	
	// {{{ Member Functions

	// {{{ focusOnDefaultComponent
	public void focusOnDefaultComponent() {
		//textArea.requestFocus();
	}
	// }}}


	// EBComponent implementation

	// {{{ handleMessage
	public void handleMessage(EBMessage message) {
		if (message instanceof PropertiesChanged) {
			propertiesChanged();
		}
	}
	// }}}

	// {{{ propertiesChanged
	private void propertiesChanged() {

	}
	// }}}

	// These JComponent methods provide the appropriate points
	// to subscribe and unsubscribe this object to the EditBus.

	// {{{ addNotify
	public void addNotify() {
		super.addNotify();
		EditBus.addToBus(this);
	}
	// }}}

	// {{{ removeNotify
	public void removeNotify() {
		//saveFile();
		super.removeNotify();
		EditBus.removeFromBus(this);
	}
	// }}}

	// PiperActions implementation



	// {{{ chooseFile
	public void chooseFile() {

	}
	// }}}

	// {{{ copyToBuffer
	public void copyToBuffer() {

	}
	// }}}
	// {{{ readFile()
	/**
	 * Helper method
	 */
	private void readFile() {

	}
	// }}}

	public void saveFile()
	{
	}

	// {{{ getFileName
	public String getFilename() {
		return filename;
	}
	// }}}

}
// }}}

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
