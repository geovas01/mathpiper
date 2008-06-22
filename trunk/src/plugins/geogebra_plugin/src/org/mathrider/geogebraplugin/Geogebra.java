//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathrider.geogebraplugin;

/*
 * Geogebra.java
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
 * $Id: Geogebra.java 9481 2007-05-02 00:34:44Z k_satoda $
 */

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

import geogebra.GeoGebraApplet;
// }}}

// {{{ Geogebra class
/**
 * 
 * Geogebra - a dockable JPanel, a demonstration of a jEdit plugin.
 *
 */
public class Geogebra extends JPanel
    implements EBComponent, GeogebraActions, DefaultFocusComponent {

    // {{{ Instance Variables
	//private static final long serialVersionUID = 6412255692894321789L;

	private String filename;

	private String defaultFilename;

	private View view;

	private boolean floating;

	//private GeogebraTextArea textArea;

	private GeogebraToolPanel toolPanel;
	
	private static GeoGebraApplet geoGebraApplet;
    // }}}

    // {{{ Constructor
	/**
	 * 
	 * @param view the current jedit window
	 * @param position a variable passed in from the script in actions.xml,
	 * 	which can be DockableWindowManager.FLOATING, TOP, BOTTOM, LEFT, RIGHT, etc.
	 * 	see @ref DockableWindowManager for possible values.
	 */
	public Geogebra(View view, String position) {
		super(new BorderLayout());
		this.view = view;
		this.floating = position.equals(DockableWindowManager.FLOATING);



		this.toolPanel = new GeogebraToolPanel(this);
		add(BorderLayout.NORTH, this.toolPanel);

		if (floating)
			this.setPreferredSize(new Dimension(500, 250));

		geoGebraApplet = new GeoGebraApplet();
		

// Now try to get an applet stub for this class.


String homeDir = org.gjt.sp.jedit.jEdit.getJEditHome();
if(homeDir.indexOf(":") != -1){
		homeDir = homeDir.split(":")[1];
}
        MathriderAppletStub stub = new MathriderAppletStub(this,
               geoGebraApplet, "geogebra.applet.", homeDir + java.io.File.separator +"jars" + java.io.File.separator + "scripts" + java.io.File.separator + "init.ggb");
        
		geoGebraApplet.setStub(stub);

		add(BorderLayout.CENTER, geoGebraApplet);
// Initialize and start the applet
        geoGebraApplet.init();
		
		
		//Set look and feel to jEdit look and feel.
		try
		{
			String lf = org.gjt.sp.jedit.jEdit.getProperty("lookAndFeel");
			if(lf != null && lf.length() != 0)
				javax.swing.UIManager.setLookAndFeel(lf);
			else if(org.gjt.sp.jedit.OperatingSystem.isMacOS())
			{
				javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
					.getSystemLookAndFeelClassName());
			}
			else
			{
				javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
					.getCrossPlatformLookAndFeelClassName());
			}
		}
		catch(Exception e)
		{
			Log.log(Log.ERROR,jEdit.class,e);
		}

		
		
        geoGebraApplet.start();

		


	}//end constructor.
    // }}}

    // {{{ Member Functions
	
	
	// {{{ getGeoGebraApplet
	public static geogebra.GeoGebraApplet getGeoGebraApplet()
	{
		return geoGebraApplet;
	}//end method
	// }}}
    
    // {{{ focusOnDefaultComponent
	public void focusOnDefaultComponent() {
		//textArea.requestFocus();
	}
    // }}}

    // {{{ getFileName
	public String getFilename() {
		return filename;
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
		/*String propertyFilename = jEdit
				.getProperty(GeogebraPlugin.OPTION_PREFIX + "filepath");
		if (!StandardUtilities.objectsEqual(defaultFilename, propertyFilename)) {
			saveFile();
			toolPanel.propertiesChanged();
			defaultFilename = propertyFilename;
			filename = defaultFilename;
			readFile();
		}
		Font newFont = GeogebraOptionPane.makeFont();
		if (!newFont.equals(textArea.getFont())) {
			textArea.setFont(newFont);
		}*/
	}//end method.
	
	
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
    
	// GeogebraActions implementation

    // {{{
	public void reset() {
		geoGebraApplet.setXML("<?xml version=\"1.0\" encoding=\"utf-8\"?> <geogebra format=\"2.5\"> </geogebra>");
	}
    // }}}
    
    // {{{ chooseFile
	public void chooseFile() {
		/*
		String[] paths = GUIUtilities.showVFSFileDialog(view, null,
				JFileChooser.OPEN_DIALOG, false);
		if (paths != null && !paths[0].equals(filename)) {
			saveFile();
			filename = paths[0];
			toolPanel.propertiesChanged();
			readFile();
		}
		*/
	}
    // }}}

    // {{{ copyToBuffer
	public void copyToBuffer() {
		/*
		jEdit.newFile(view);
		view.getEditPane().getTextArea().setText(textArea.getText());
		*/
	}//end method.
    // }}}

    // }}}
}//end class.
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

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
