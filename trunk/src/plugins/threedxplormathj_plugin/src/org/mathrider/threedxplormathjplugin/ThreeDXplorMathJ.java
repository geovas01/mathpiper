//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathrider.threedxplormathjplugin;

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

import vmm.core.Exhibit;
import vmm.core.I18n;
import vmm.core.Util;
import vmm.xm3d.Menus;
import vmm.xm3d.WindowXM;
import vmm.xm3d.EmbeddedApplet;
//import vmm.core.View;



// }}}

// {{{ Piper class
/**
 * 
 * Piper - a dockable JPanel, a demonstration of a jEdit plugin.
 *
 */
public class ThreeDXplorMathJ extends JPanel
	implements EBComponent, ThreeDXplorMathJActions, DefaultFocusComponent{

	// {{{ Instance Variables
	//private static final long serialVersionUID = 6412255692894321789L;

	private String filename;

	private String defaultFilename;

	private View view;

	private boolean floating;

	private ThreeDXplorMathJToolPanel toolPanel;
	
	//private static ThreeDXplorMathJ jFreeChart;
	// }}}

	// {{{ Constructor
	/**
	 * 
	 * @param view the current jedit window
	 * @param position a variable passed in from the script in actions.xml,
	 * 	which can be DockableWindowManager.FLOATING, TOP, BOTTOM, LEFT, RIGHT, etc.
	 * 	see @ref DockableWindowManager for possible values.
	 */
	public ThreeDXplorMathJ(View view, String position) {
		super(new BorderLayout());
		this.view = view;
		this.floating = position.equals(DockableWindowManager.FLOATING);
		
		
		
		//I18n.addFile("vmm.xm3d.stringsXM");
		
		
		//int options = 0;
		//menus = new Menus(this, null, options);


		
		
		//vmm.xm3d.DisplayXM display3d = new vmm.xm3d.DisplayXM();
		
		//this.add(display3d);
		
		
		vmm.xm3d.ThreeDXplorMathJApplet geoGebraApplet = new vmm.xm3d.ThreeDXplorMathJApplet();
		
		//vmm.xm3d.EmbeddedApplet geoGebraApplet = new vmm.xm3d.EmbeddedApplet();


		// Now try to get an applet stub for this class.


		String homeDir = org.gjt.sp.jedit.jEdit.getJEditHome();
		if(homeDir.indexOf(":") != -1){
			homeDir = homeDir.split(":")[1];
		}
		ThreeDXplorMathJAppletStub stub = new ThreeDXplorMathJAppletStub(this, geoGebraApplet, "geogebra.applet.", homeDir);// + java.io.File.separator +"jars" + java.io.File.separator + "scripts" + java.io.File.separator + "init.ggb");

		geoGebraApplet.setStub(stub);

		add(BorderLayout.CENTER, geoGebraApplet);
		
		geoGebraApplet.init();



		//this.toolPanel = new ThreeDXplorMathJToolPanel(this);
		//add(BorderLayout.NORTH, this.toolPanel);

		//JLabel testLabel = new JLabel("TEST");
		//this.add(testLabel);

		//JPanel panel  = HistogramExample1.createDemoPanel();
		
		
		//this.add(ThreeDXplorMathJPlugin.getChartPanel());

		if (floating)
			this.setPreferredSize(new Dimension(500, 250));


		//org.mathpiper.interpreters.Interpreter synchronousInterpreter = org.mathpiper.interpreters.Interpreters.getSynchronousInterpreter();
		//org.mathpiper.interpreters.EvaluationResponse response = synchronousInterpreter.evaluate("Import(\"org/mathpiper/builtin/functions/plugins/threedxplormathj/\");");
		
		//synchronousInterpreter.addResponseListener(this); 
		
		
		//jFreeChart = this;

	}//end constructor
	// }}}


	
	
	
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
