//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathrider.yacasdocsplugin;


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
import javax.swing.JLabel;
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
import org.gjt.sp.jedit.bsh.Interpreter;

//import bsh.Interpreter;


// }}}

// {{{  YacasDocs class
/**
 * 
 * YacasDocs - a dockable JPanel..
 *
 */
public class YacasDocs extends JPanel
    implements EBComponent, YacasDocsActions, DefaultFocusComponent {

    // {{{ Instance Variables
	//private static final long serialVersionUID = 6412255692894321789L;

	private String filename;

	private String defaultFilename;

	private View view;

	private boolean floating;

	//private YacasDocsTextArea textArea;

	private YacasDocsToolPanel toolPanel;
	
	private static YacasDocs yacasDocs;
	
	//private static sHotEqn hotEqn;
    // }}}

    // {{{ Constructor
	/**
	 * 
	 * @param view the current jedit window
	 * @param position a variable passed in from the script in actions.xml,
	 * 	which can be DockableWindowManager.FLOATING, TOP, BOTTOM, LEFT, RIGHT, etc.
	 * 	see @ref DockableWindowManager for possible values.
	 */
	public YacasDocs(View view, String position) {
		super(new BorderLayout());
		yacasDocs = this;
		this.view = view;
		this.floating = position.equals(DockableWindowManager.FLOATING);



		this.toolPanel = new YacasDocsToolPanel(this);
		add(BorderLayout.NORTH, this.toolPanel);

		if (floating)
			this.setPreferredSize(new Dimension(500, 250));

		//hotEqn = new sHotEqn();
		//hotEqn.setFontsizes(18,18,18,18);
		//hotEqn.setEquation("");
		

// Now try to get an applet stub for this class.

        //MathriderAppletStub stub = new MathriderAppletStub(this,
               //hotEqn, "hoteqn.applet.", "http://localhost/");
        
		//hotEqn.setStub(stub);
		JScrollPane scrollPane = new JScrollPane(null,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		JPanel spacerPanel = new JPanel();
		spacerPanel.setBackground(java.awt.Color.WHITE);
		spacerPanel.add(new JLabel(" "));
		scrollPane.setRowHeaderView(spacerPanel);
		add(BorderLayout.CENTER,scrollPane);
		
	
// Initialize and start the applet
       // hotEqn.init();
       // hotEqn.start();

		
	 //  System.out.println("YYYYY1: " + this.getClass().getResource("yacas_manual/Algolchapter1.html"));
	 //  System.out.println("YYYYY2: " + this.getClass().getResource("/yacas_manual/Algolchapter1.html"));
	  
	   //java.net.URL imageURL = JBasicBuilderToolPanel.class.getResource( jEdit.getProperty(name + ".icon") );
	   
	}//end constructor.
    // }}}

    // {{{ Member Functions
	
	
	// {{{ getYacasDocs
	public static YacasDocs getYacasDocs()
	{	
		return YacasDocs.yacasDocs;  
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
				.getProperty(YacasDocsPlugin.OPTION_PREFIX + "filepath");
		if (!StandardUtilities.objectsEqual(defaultFilename, propertyFilename)) {
			saveFile();
			toolPanel.propertiesChanged();
			defaultFilename = propertyFilename;
			filename = defaultFilename;
			readFile();
		}
		Font newFont = YacasDocsOptionPane.makeFont();
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
    
	// YacasDocsActions implementation

    // {{{
	public void reset() {
		//hotEqn.setEquation("");
		//System.out.println("YYYYY2: " + YacasDocs.class.getResource("yacas_manual/Algochapter1.html"));
		
		Interpreter interpreter = new Interpreter();
		
		try
		{
			java.net.URL helpURL = jEdit.getPlugin("org.mathrider.yacasdocsplugin.YacasDocsPlugin").getPluginJAR().getClassLoader().getResource("scripts/Yacas_Docs.bsh");
		//System.out.println("YYYYY2: " + helpURL.toString());
		
		java.io.Reader sourceIn = new java.io.BufferedReader( new java.io.InputStreamReader(helpURL.openStream() ));
		try {
			interpreter.eval( sourceIn );
		} finally {
			sourceIn.close();
		}
		
			//interpreter.source(jeditresource:/yacas_docs_plugin.jar!/scripts/Yacas_Docs.bsh);
		}
		catch(Exception e) //Note: add proper exception handling here and everywhere Exception is caught.
		{
			e.printStackTrace();
		}
//			interpreter.set("helpURL",helpURL);
//			interpreter.set("jEdit",jEdit);
//			YacasDocs.class.getResource("/scripts/Yacas_Docs.bsh")
//			
//			
//						try 
//			{   
//				tempFilePath = tempFile.getAbsolutePath();
//
//				//System.out.println(tempFilePath);
//
//				interpreter.source(tempFilePath);
//				result = "";
//	//System.out.println("tempFilePath: " + tempFilePath);
//				status = "%output ";
//	//Sy	stem.out.println("Out> " + result);
//				
//					
//			}catch(Exception e) 
//			{
//				result = e.getMessage();
//				status = "%error ";
//			}
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

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
