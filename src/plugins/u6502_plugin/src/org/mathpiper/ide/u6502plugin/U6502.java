//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathpiper.ide.u6502plugin;


// {{{ imports
import org.mathrider.u6502.EMU6551;

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
import javax.swing.JEditorPane;

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

// {{{  U6502 class
/**
 * 
 * U6502 - a dockable JPanel..
 *
 */
public class U6502 extends JPanel
    implements EBComponent, U6502Actions, DefaultFocusComponent {

    // {{{ Instance Variables
	//private static final long serialVersionUID = 6412255692894321789L;

	private String filename;

	private String defaultFilename;

	private View view;

	private boolean floating;
	private JScrollPane scrollPane;

	//private U6502TextArea textArea;

	private U6502ToolPanel toolPanel;
	
	private static U6502 u6502;
	
	private EMU6551 uart;
	
    //static u6502.CalculatorApplet jSciCalcApplet;
	
	//private JEditorPane editorPane;
	
	//Interpreter bshInterpreter;
	
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
	public U6502(View view, String position) {
		super(new BorderLayout());
		u6502 = this;
		
//		bshInterpreter = new Interpreter();
		
		this.view = view;
		this.floating = position.equals(DockableWindowManager.FLOATING);
		
		uart = EMU6551.getInstance();
		//uart.setFontSize(18);
		this.add(uart,BorderLayout.CENTER);

//		jSciCalcApplet = new u6502.CalculatorApplet();
//		jSciCalcApplet.setup();
//		JPanel calcPanel = new JPanel();
//		calcPanel.add(jSciCalcApplet);
//		add(calcPanel);   
//
//		this.toolPanel = new U6502ToolPanel(this);
//		add(BorderLayout.NORTH, this.toolPanel);
//
//		if (floating)
//			this.setPreferredSize(new Dimension(500, 250));
//
		//hotEqn = new sHotEqn();
		//hotEqn.setFontsizes(18,18,18,18);
		//hotEqn.setEquation("");
		

// Now try to get an applet stub for this class.

        //MathriderAppletStub stub = new MathriderAppletStub(this,
               //hotEqn, "hoteqn.applet.", "http://localhost/");
        
		//hotEqn.setStub(stub);
		
//		editorPane = new JEditorPane();
//		editorPane.setEditorKit(new javax.swing.text.html.HTMLEditorKit());
//		//JScrollPane editorScrollPane = new JScrollPane(editorPane);
//		scrollPane = new JScrollPane(editorPane,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
//		JPanel spacerPanel = new JPanel();
//		spacerPanel.setBackground(java.awt.Color.WHITE);
//		spacerPanel.add(new JLabel(" "));
//		scrollPane.setRowHeaderView(spacerPanel);
//		add(BorderLayout.CENTER,scrollPane);
		
//		initDocViewer(); 


	
// Initialize and start the applet
       // hotEqn.init();
       // hotEqn.start();

		
	 //  System.out.println("YYYYY1: " + this.getClass().getResource("piper_manual/Algolchapter1.html"));
	 //  System.out.println("YYYYY2: " + this.getClass().getResource("/piper_manual/Algolchapter1.html"));
	  
	   //java.net.URL imageURL = JBasicBuilderToolPanel.class.getResource( jEdit.getProperty(name + ".icon") );
	   
	}//end constructor.
    // }}}

    // {{{ Member Functions
	
	
	// {{{ getU6502
	public static U6502 getU6502()
	{	
		return U6502.u6502;  
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
				.getProperty(U6502Plugin.OPTION_PREFIX + "filepath");
		if (!StandardUtilities.objectsEqual(defaultFilename, propertyFilename)) {
			saveFile();
			toolPanel.propertiesChanged();
			defaultFilename = propertyFilename;
			filename = defaultFilename;
			readFile();
		}
		Font newFont = U6502OptionPane.makeFont();
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
    
	
    // {{{ initDocViewer()
//	public void initDocViewer() {
//	
//		try
//		{
//			//Note: this is in development mode.  Switch comment to other line for distribution.
//			java.net.URL docsURL = jEdit.getPlugin("org.mathrider.u6502plugin.U6502Plugin").getPluginJAR().getClassLoader().getResource("scripts/Piper_Docs.bsh");
//			//java.net.URL docsURL =new java.net.URL( "file:///C:/ted/checkouts/mathrider/src/plugins/bean_sheet_plugin/src/scripts/Piper_Docs.bsh");
//
//			
//			//System.out.println("YYYYY2: " + helpURL.toString());
//		
//		java.io.Reader sourceIn = new java.io.BufferedReader( new java.io.InputStreamReader(docsURL.openStream() ));
//			try {
//				bshInterpreter.set("piperDocPanel",this);
//				bshInterpreter.set("editorScrollPane",scrollPane);
//				bshInterpreter.set("editorPane",editorPane);
//				bshInterpreter.set("view",view);
//				bshInterpreter.set("toolPanel",this.toolPanel);
//				bshInterpreter.eval( sourceIn );
//			} finally {
//				sourceIn.close();
//			}
//		
//			//bshInterpreter.source(jeditresource:/bean_sheet_plugin.jar!/scripts/Piper_Docs.bsh);
//		}
//		catch(Exception e) //Note: add proper exception handling here and everywhere Exception is caught.
//		{
//			e.printStackTrace();
//		}
//
//	}//end method.
    // }}}

	
	
	// U6502Actions implementation

	
	//{{{ source()
//	public void source()
//	{
//		try {
//			bshInterpreter.eval( "source();" );
//		
//		}
//		catch(Exception e) //Note: add proper exception handling here and everywhere Exception is caught.
//		{
//			e.printStackTrace();
//		}
//	}//end method.
	//}}}             
	
	//{{{ reset()
	public void reset()
	{
	}//end method.
	//}}}
	
    
	
	// {{{ home()
//	public void home() 
//	{
//		try {
//			bshInterpreter.eval( "home();" );
//		
//		}
//		catch(Exception e) //Note: add proper exception handling here and everywhere Exception is caught.
//		{
//			e.printStackTrace();
//		}
//	}//end method.
//    // }}}
	
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
