package org.mathrider.hoteqnplugin;

/*
 * HotEqn.java
 * part of the HotEqn plugin for the jEdit text editor
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
 * $Id: HotEqn.java 9481 2007-05-02 00:34:44Z k_satoda $
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

import hoteqn.HotEqnApplet;
// }}}

// {{{ HotEqn class
/**
 * 
 * HotEqn - a dockable JPanel, a demonstration of a jEdit plugin.
 *
 */
public class HotEqn extends JPanel
    implements EBComponent, HotEqnActions, DefaultFocusComponent {

    // {{{ Instance Variables
	//private static final long serialVersionUID = 6412255692894321789L;

	private String filename;

	private String defaultFilename;

	private View view;

	private boolean floating;

	//private HotEqnTextArea textArea;

	private HotEqnToolPanel toolPanel;
	
	private static HotEqnApplet hotEqnApplet;
    // }}}

    // {{{ Constructor
	/**
	 * 
	 * @param view the current jedit window
	 * @param position a variable passed in from the script in actions.xml,
	 * 	which can be DockableWindowManager.FLOATING, TOP, BOTTOM, LEFT, RIGHT, etc.
	 * 	see @ref DockableWindowManager for possible values.
	 */
	public HotEqn(View view, String position) {
		super(new BorderLayout());
		this.view = view;
		this.floating = position.equals(DockableWindowManager.FLOATING);



		this.toolPanel = new HotEqnToolPanel(this);
		add(BorderLayout.NORTH, this.toolPanel);

		if (floating)
			this.setPreferredSize(new Dimension(500, 250));

		hotEqnApplet = new HotEqnApplet();
		

// Now try to get an applet stub for this class.

        MathriderAppletStub stub = new MathriderAppletStub(this,
               hotEqnApplet, "hoteqn.applet.", "http://localhost/");
        
		hotEqnApplet.setStub(stub);

		add(BorderLayout.CENTER, hotEqnApplet);
// Initialize and start the applet
        hotEqnApplet.init();
        hotEqnApplet.start();

		


	}//end constructor.
    // }}}

    // {{{ Member Functions
	
	
	// {{{ getHotEqnApplet
	public static hoteqn.HotEqnApplet getHotEqnApplet()
	{
		return hotEqnApplet;
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
				.getProperty(HotEqnPlugin.OPTION_PREFIX + "filepath");
		if (!StandardUtilities.objectsEqual(defaultFilename, propertyFilename)) {
			saveFile();
			toolPanel.propertiesChanged();
			defaultFilename = propertyFilename;
			filename = defaultFilename;
			readFile();
		}
		Font newFont = HotEqnOptionPane.makeFont();
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
    
	// HotEqnActions implementation

    // {{{
	public void reset() {
		//hotEqnApplet.setXML("<?xml version=\"1.0\" encoding=\"utf-8\"?> <hoteqn format=\"2.5\"> </hoteqn>");
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

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
