//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathpiper.ide.mathpiperdocsplugin;


// {{{ imports
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.*;

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

import org.mathpiper.ui.gui.help.FunctionTreePanel;
import org.mathpiper.ui.gui.help.HelpListener;
import org.mathpiper.ui.gui.help.HelpEvent;


//import bsh.Interpreter;


// }}}

// {{{  MathPiperDocs class
/**
 * 
 * MathPiperDocs - a dockable JPanel..
 *
 */
public class MathPiperDocs extends JPanel
			implements EBComponent, MathPiperDocsActions, DefaultFocusComponent, HelpListener
{

	// {{{ Instance Variables
	//private static final long serialVersionUID = 6412255692894321789L;

	private String filename;

	private String defaultFilename;

	private View view;

	private boolean floating;
	private JScrollPane docsScrollPane;

	//private MathPiperDocsTextArea textArea;

	private MathPiperDocsToolPanel toolPanel;

	private static MathPiperDocs mathPiperDocs;

	private JEditorPane editorPane;

	private FunctionTreePanel helpPanel;

	Interpreter bshInterpreter;

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
	public MathPiperDocs(View view, String position)
	{
		super(new BorderLayout());
		mathPiperDocs = this;

		//bshInterpreter = new Interpreter();

		this.view = view;
		this.floating = position.equals(DockableWindowManager.FLOATING);





		if (floating)
			this.setPreferredSize(new Dimension(500, 250));

		//hotEqn = new sHotEqn();
		//hotEqn.setFontsizes(18,18,18,18);
		//hotEqn.setEquation("");


		// Now try to get an applet stub for this class.

		//MathriderAppletStub stub = new MathriderAppletStub(this,
		//hotEqn, "hoteqn.applet.", "http://localhost/");

		//hotEqn.setStub(stub);



		//editorPane = new JEditorPane();
		//editorPane.setEditorKit(new javax.swing.text.html.HTMLEditorKit());


		//JdocsScrollPane editorScrollPane = new JScrollPane(editorPane);

		//ClassLoader classLoader = jEdit.getPlugin("org.mathpiper.ide.mathpiperdocsplugin.MathPiperDocsPlugin").getPluginJAR().getClassLoader();

		try {
			helpPanel = new org.mathpiper.ui.gui.help.FunctionTreePanel();

			helpPanel.addHelpListener(this);

			add(BorderLayout.CENTER,helpPanel);



			add(BorderLayout.NORTH, helpPanel.getToolPanel());
		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}


		//docsScrollPane = new JScrollPane(editorPane,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		//JPanel spacerPanel = new JPanel();
		//spacerPanel.setBackground(java.awt.Color.WHITE);
		//spacerPanel.add(new JLabel(" "));
		//scrollPane.setRowHeaderView(spacerPanel);


		//add(BorderLayout.CENTER,docsScrollPane);

		//initDocViewer();

		// Initialize and start the applet
		// hotEqn.init();
		// hotEqn.start();


		//  System.out.println("YYYYY1: " + this.getClass().getResource("piper_manual/Algolchapter1.html"));
		//  System.out.println("YYYYY2: " + this.getClass().getResource("/piper_manual/Algolchapter1.html"));

		//java.net.URL imageURL = JBasicBuilderToolPanel.class.getResource( jEdit.getProperty(name + ".icon") );

	}//end constructor.
	// }}}

	// {{{ Member Functions


	public boolean viewFunction(String functionName)
	{
		return helpPanel.viewFunction(functionName, true);
	}//end method.


	// {{{ getMathPiperDocs
	public static MathPiperDocs getMathPiperDocs()
	{
		return MathPiperDocs.mathPiperDocs;
	}//end method
	// }}}

	// {{{ focusOnDefaultComponent
	public void focusOnDefaultComponent()
	{
		//textArea.requestFocus();
	}
	// }}}

	// {{{ getFileName
	public String getFilename()
	{
		return filename;
	}
	// }}}

	// EBComponent implementation

	// {{{ handleMessage
	public void handleMessage(EBMessage message)
	{
		if (message instanceof PropertiesChanged) {
			propertiesChanged();
		}
	}
	// }}}

	// {{{ propertiesChanged
	private void propertiesChanged()
	{
		/*String propertyFilename = jEdit
				.getProperty(MathPiperDocsPlugin.OPTION_PREFIX + "filepath");
		if (!StandardUtilities.objectsEqual(defaultFilename, propertyFilename)) {
			saveFile();
			toolPanel.propertiesChanged();
			defaultFilename = propertyFilename;
			filename = defaultFilename;
			readFile();
	}
		Font newFont = MathPiperDocsOptionPane.makeFont();
		if (!newFont.equals(textArea.getFont())) {
			textArea.setFont(newFont);
	}*/
	}//end method.


	// }}}

	// These JComponent methods provide the appropriate points
	// to subscribe and unsubscribe this object to the EditBus.

	// {{{ addNotify
	public void addNotify()
	{
		super.addNotify();
		EditBus.addToBus(this);
	}
	// }}}

	// {{{ removeNotify
	public void removeNotify()
	{
		//saveFile();
		super.removeNotify();
		EditBus.removeFromBus(this);
	}
	// }}}


	/* // {{{ initDocViewer()
	public void initDocViewer() {

	try
{
	//Note: this is in development mode.  Switch comment to other line for distribution.
	java.net.URL docsURL = jEdit.getPlugin("org.mathpiper.ide.mathpiperdocsplugin.MathPiperDocsPlugin").getPluginJAR().getClassLoader().getResource("scripts/MathPiper_Docs.bsh");
	//java.net.URL docsURL =new java.net.URL( "file:///C:/ted/checkouts/mathpiperide/src/plugins/piper_docs_plugin/src/scripts/MathPiper_Docs.bsh");


	//System.out.println("YYYYY2: " + helpURL.toString());

	java.io.Reader sourceIn = new java.io.BufferedReader( new java.io.InputStreamReader(docsURL.openStream() ));
	try 
{
		bshInterpreter.set("mathPiperDocPanel",this);
		bshInterpreter.set("docsScrollPane",docsScrollPane);
		bshInterpreter.set("editorPane",editorPane);
		bshInterpreter.set("view",view);
		bshInterpreter.set("toolPanel",this.toolPanel);

		java.net.URL homePage = jEdit.getPlugin("org.mathpiper.ide.mathpiperdocsplugin.MathPiperDocsPlugin").getPluginJAR().getClassLoader().getResource("mathpiper_manual/books2.html");
		java.util.ArrayList pageList = new java.util.ArrayList();
		//pageList.add(homePage);
		bshInterpreter.set("homePage",homePage);
		bshInterpreter.set("pageList",pageList);
		bshInterpreter.set("pageIndex",-1);
		bshInterpreter.eval( "classFunctionInfo = org.gjt.sp.jedit.jEdit.getPlugin(\"org.mathpiper.ide.mathpiperdocsplugin.MathPiperDocsPlugin\").getPluginJAR().getClassLoader().loadClass(\"org.mathpiper.ide.mathpiperdocsplugin.FunctionInfo\",true);");
		bshInterpreter.eval( "classFunctionInfoTree = org.gjt.sp.jedit.jEdit.getPlugin(\"org.mathpiper.ide.mathpiperdocsplugin.MathPiperDocsPlugin\").getPluginJAR().getClassLoader().loadClass(\"org.mathpiper.ide.mathpiperdocsplugin.FunctionInfoTree\",true);");

		//new org.mathpiper.ide.piperdocsplugin.FunctionInfo(null,null);
		//bshInterpreter.eval("import org.mathpiper.ide.piperdocsplugin.FunctionInfo;");
		//bshInterpreter.eval("new org.mathpiper.ide.piperdocsplugin.FunctionInfo(null,null);");
		
		bshInterpreter.eval( sourceIn );
} 
	catch (Exception e)
{
		e.printStackTrace();
}
	finally
{
		sourceIn.close();
}

	//bshInterpreter.source(jeditresource:/piper_docs_plugin.jar!/scripts/MathPiper_Docs.bsh);
}
	catch(Exception e) //Note: add proper exception handling here and everywhere Exception is caught.
{
	e.printStackTrace();
}

}//end method.
	 // }}}*/



	// MathPiperDocsActions implementation


	//{{{ source()
	public void source()
	{
		try {
			//bshInterpreter.eval( "source();" );

		} catch(Exception e) //Note: add proper exception handling here and everywhere Exception is caught.
		{
			e.printStackTrace();
		}
	}//end method.
	//}}}


	//{{{ collapse()
	public void collapse()
	{
		try {
			//bshInterpreter.eval( "collapse();" );

		} catch(Exception e) //Note: add proper exception handling here and everywhere Exception is caught.
		{
			e.printStackTrace();
		}
	}//end method.
	//}}}

	//{{{ reset()
	public void reset()
	{}//end method.
	//}}}


	// {{{ back button()
	public void back()
	{
		try {
			//bshInterpreter.eval( "back();" );

		} catch(Exception e) //Note: add proper exception handling here and everywhere Exception is caught.
		{
			e.printStackTrace();
		}
	}//end method.
	// }}}



	// {{{ forward button()
	public void forward()
	{
		try {
			//bshInterpreter.eval( "forward();" );

		} catch(Exception e) //Note: add proper exception handling here and everywhere Exception is caught.
		{
			e.printStackTrace();
		}
	}//end method.
	// }}}

	// {{{ home button()
	public void home()
	{
		try {
			//bshInterpreter.eval( "home();" );

		} catch(Exception e) //Note: add proper exception handling here and everywhere Exception is caught.
		{
			e.printStackTrace();
		}
	}//end method.
	// }}}


	// {{{ chooseFile
	public void chooseFile()
	{
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
	public void copyToBuffer()
	{
		/*
		jEdit.newFile(view);
		view.getEditPane().getTextArea().setText(textArea.getText());
		*/
	}//end method.
	// }}}


	// }}}




	public void helpEvent(HelpEvent helpEvent)
	{
		String mpwFilePath = helpEvent.getFilePath();

		String mpwSourceCode = helpEvent.getSourceCode();


		viewSource(mpwFilePath, mpwSourceCode);
	}



	private void viewSource(String mpwFilePath, String mpwSourceCode)
	{
		//System.out.println("XXXXXXXX " + mpwFilePath);
		if(mpwFilePath.endsWith(".mpw")) 
		{
			try {

				String mpwFilename = mpwFilePath.substring(mpwFilePath.lastIndexOf("/",mpwFilePath.lastIndexOf("/") -1));
				
				mpwFilename = mpwFilename.substring(1, mpwFilename.length());
				
				mpwFilename = mpwFilename.replace("/", "_");
				
				mpwFilename = mpwFilename.replace(".mpw", ".");
				

				final File tempFile = File.createTempFile(mpwFilename, ".mpw");

				tempFile.deleteOnExit();

				BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));

				out.write(mpwSourceCode);

				out.close();

				final View activeView = org.gjt.sp.jedit.jEdit.getActiveView();

				org.gjt.sp.jedit.io.VFSManager.runInAWTThread(

				    new Runnable() {

					    public void run() {
						    org.gjt.sp.jedit.jEdit.openFile(activeView, tempFile.getAbsolutePath());
					    }

				    }
				);


			} catch (IOException e) {

				e.printStackTrace();

			}
		} else {
			//org.gjt.sp.jedit.Macros.message(org.gjt.sp.jedit.jEdit.getActiveView(), "XXX");
		}




	}//end source.







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
