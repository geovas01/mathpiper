//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathrider.jungplugin;


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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.functors.ConstantTransformer;

import edu.uci.ics.jung.algorithms.layout.BalloonLayout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.graph.Tree;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalLensGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.layout.LayoutTransition;
import edu.uci.ics.jung.visualization.transform.LensSupport;
import edu.uci.ics.jung.visualization.transform.MutableTransformer;
import edu.uci.ics.jung.visualization.transform.MutableTransformerDecorator;
import edu.uci.ics.jung.visualization.transform.shape.HyperbolicShapeTransformer;
import edu.uci.ics.jung.visualization.transform.shape.ViewLensSupport;
import edu.uci.ics.jung.visualization.util.Animator;


//import bsh.Interpreter;


// }}}

// {{{  Jung class
/**
 * 
 * Jung - a dockable JPanel..
 *
 */
public class Jung extends JPanel
    implements EBComponent, JungActions, DefaultFocusComponent {

    // {{{ Instance Variables
	//private static final long serialVersionUID = 6412255692894321789L;

	
	Forest<String,Integer> graph;
    /**
     * the visual component and renderer for the graph
     */
    VisualizationViewer<String,Integer> vv;
    
    VisualizationServer.Paintable rings;
    
    String root;
    
    TreeLayout<String,Integer> layout;
    
    BalloonLayout<String,Integer> radialLayout;
    /**
     * provides a Hyperbolic lens for the view
     */
    LensSupport hyperbolicViewSupport;	
	

	private String filename;

	private String defaultFilename;

	private View view;

	private boolean floating;
	private JScrollPane docsScrollPane;

	//private JungTextArea textArea;

	private JungToolPanel toolPanel;
	
	private static Jung jung;
	
	private JEditorPane editorPane;
	
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
	public Jung(View view, String position) {
		super(new BorderLayout());

		jung = this;
		this.view = view;
		this.floating = position.equals(DockableWindowManager.FLOATING);

		
		
		
//=======
	graph = new DelegateForest<String,Integer>();
	   createTree();
        layout = new TreeLayout<String,Integer>(graph);
        radialLayout = new BalloonLayout<String,Integer>(graph);
        radialLayout.setSize(new Dimension(900,900));
        vv =  new VisualizationViewer<String,Integer>(layout, new Dimension(600,600));
        vv.setBackground(Color.white);
        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line());
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        // add a listener for ToolTips
        vv.setVertexToolTipTransformer(new ToStringLabeller());
        vv.getRenderContext().setArrowFillPaintTransformer(new ConstantTransformer(Color.lightGray));
        rings = new Rings(radialLayout);
        
        Container content = this;
        final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
        content.add(panel);
        
        final DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();

        vv.setGraphMouse(graphMouse);
        vv.addKeyListener(graphMouse.getModeKeyListener());
        
        hyperbolicViewSupport = 
            new ViewLensSupport<String,Integer>(vv, new HyperbolicShapeTransformer(vv, 
            		vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW)), 
                    new ModalLensGraphMouse());


        graphMouse.addItemListener(hyperbolicViewSupport.getGraphMouse().getModeListener());

        JComboBox modeBox = graphMouse.getModeComboBox();
        modeBox.addItemListener(graphMouse.getModeListener());
        graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);

        final ScalingControl scaler = new CrossoverScalingControl();
        
        vv.scaleToLayout(scaler);

        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1.1f, vv.getCenter());
            }
        });
        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1/1.1f, vv.getCenter());
            }
        });
        
        JToggleButton radial = new JToggleButton("Balloon");
        radial.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {

					LayoutTransition<String,Integer> lt =
						new LayoutTransition<String,Integer>(vv, layout, radialLayout);
					Animator animator = new Animator(lt);
					animator.start();
					vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).setToIdentity();
					vv.addPreRenderPaintable(rings);
				} else {

					LayoutTransition<String,Integer> lt =
						new LayoutTransition<String,Integer>(vv, radialLayout, layout);
					Animator animator = new Animator(lt);
					animator.start();
					vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).setToIdentity();
					vv.removePreRenderPaintable(rings);
				}
				vv.repaint();
			}});
        final JRadioButton hyperView = new JRadioButton("Hyperbolic View");
        hyperView.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                hyperbolicViewSupport.activate(e.getStateChange() == ItemEvent.SELECTED);
            }
        });

        JPanel scaleGrid = new JPanel(new GridLayout(1,0));
        scaleGrid.setBorder(BorderFactory.createTitledBorder("Zoom"));

        JPanel controls = new JPanel();
        scaleGrid.add(plus);
        scaleGrid.add(minus);
        //controls.add(radial);
        controls.add(scaleGrid);
        controls.add(modeBox);
        controls.add(hyperView);
        content.add(controls, BorderLayout.SOUTH);

		
		//=====


		//this.toolPanel = new JungToolPanel(this);
		//add(BorderLayout.NORTH, this.toolPanel);

		if (floating)
			this.setPreferredSize(new Dimension(500, 250));


	   
	}//end constructor.
    // }}}

    // {{{ Member Functions
	
	
	// {{{ getJung
	public static Jung getJung()
	{	
		return Jung.jung;  
	}//end method
	// }}}
	
	public void setGraph(edu.uci.ics.jung.graph.Graph graph)
	{
		//this.graph = graph;
		vv.setGraphLayout(new edu.uci.ics.jung.algorithms.layout.FRLayout(graph));
	} 
    
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
				.getProperty(JungPlugin.OPTION_PREFIX + "filepath");
		if (!StandardUtilities.objectsEqual(defaultFilename, propertyFilename)) {
			saveFile();
			toolPanel.propertiesChanged();
			defaultFilename = propertyFilename;
			filename = defaultFilename;
			readFile();
		}
		Font newFont = JungOptionPane.makeFont();
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
	public void initDocViewer() {
	
		try
		{
			//Note: this is in development mode.  Switch comment to other line for distribution.
			java.net.URL docsURL = jEdit.getPlugin("org.mathrider.jungplugin.JungPlugin").getPluginJAR().getClassLoader().getResource("scripts/MathPiper_Docs.bsh");
			//java.net.URL docsURL =new java.net.URL( "file:///C:/ted/checkouts/mathrider/src/plugins/piper_docs_plugin/src/scripts/MathPiper_Docs.bsh");

			
			//System.out.println("YYYYY2: " + helpURL.toString());
		
		java.io.Reader sourceIn = new java.io.BufferedReader( new java.io.InputStreamReader(docsURL.openStream() ));
			try 
			{
				bshInterpreter.set("mathPiperDocPanel",this);
				bshInterpreter.set("docsScrollPane",docsScrollPane);
				bshInterpreter.set("editorPane",editorPane);
				bshInterpreter.set("view",view);
				bshInterpreter.set("toolPanel",this.toolPanel);
				//bshInterpreter.set("mathPiperInterpreter",MathPiperInterpreter.getInstance());//Note:tk:fixing race condition.
				java.net.URL homePage = jEdit.getPlugin("org.mathrider.jungplugin.JungPlugin").getPluginJAR().getClassLoader().getResource("mathpiper_manual/books2.html");
				java.util.ArrayList pageList = new java.util.ArrayList();
				//pageList.add(homePage);
				bshInterpreter.set("homePage",homePage);
				bshInterpreter.set("pageList",pageList);
				bshInterpreter.set("pageIndex",-1);
				bshInterpreter.eval( "classFunctionInfo = org.gjt.sp.jedit.jEdit.getPlugin(\"org.mathrider.jungplugin.JungPlugin\").getPluginJAR().getClassLoader().loadClass(\"org.mathrider.jungplugin.FunctionInfo\",true);");
				bshInterpreter.eval( "classFunctionInfoTree = org.gjt.sp.jedit.jEdit.getPlugin(\"org.mathrider.jungplugin.JungPlugin\").getPluginJAR().getClassLoader().loadClass(\"org.mathrider.jungplugin.FunctionInfoTree\",true);");

				//new org.mathrider.piperdocsplugin.FunctionInfo(null,null);
				//bshInterpreter.eval("import org.mathrider.piperdocsplugin.FunctionInfo;");
				//bshInterpreter.eval("new org.mathrider.piperdocsplugin.FunctionInfo(null,null);");
				
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
    // }}}

	
	
	// JungActions implementation

	
	//{{{ source()
	public void source()
	{
		try {
			bshInterpreter.eval( "source();" );
		
		}
		catch(Exception e) //Note: add proper exception handling here and everywhere Exception is caught.
		{
			e.printStackTrace();
		}
	}//end method.
	//}}}
	
	
	//{{{ collapse()
	public void collapse()
	{
		try {
			bshInterpreter.eval( "collapse();" );
		
		}
		catch(Exception e) //Note: add proper exception handling here and everywhere Exception is caught.
		{
			e.printStackTrace();
		}
	}//end method.
	//}}}
	
	//{{{ reset()
	public void reset()
	{
	}//end method.
	//}}}

	
	// {{{ back button()
	public void back() 
	{
		try {
			bshInterpreter.eval( "back();" );
		
		}
		catch(Exception e) //Note: add proper exception handling here and everywhere Exception is caught.
		{
			e.printStackTrace();
		}
	}//end method.
    // }}}


	
	// {{{ forward button()
	public void forward() 
	{
		try {
			bshInterpreter.eval( "forward();" );
		
		}
		catch(Exception e) //Note: add proper exception handling here and everywhere Exception is caught.
		{
			e.printStackTrace();
		}
	}//end method.
    // }}}

	// {{{ home button()
	public void home() 
	{
		try {
			bshInterpreter.eval( "home();" );
		
		}
		catch(Exception e) //Note: add proper exception handling here and everywhere Exception is caught.
		{
			e.printStackTrace();
		}
	}//end method.
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
	Factory<DirectedGraph<String,Integer>> graphFactory = 
		new Factory<DirectedGraph<String,Integer>>() {

		public DirectedGraph<String, Integer> create() {
			return new DirectedSparseMultigraph<String,Integer>();
		}
	};

	Factory<Tree<String,Integer>> treeFactory =
		new Factory<Tree<String,Integer>> () {

		public Tree<String, Integer> create() {
			return new DelegateTree<String,Integer>(graphFactory);
		}
	};

	Factory<Integer> edgeFactory = new Factory<Integer>() {
		int i=0;
		public Integer create() {
			return i++;
		}};
    
    Factory<String> vertexFactory = new Factory<String>() {
    	int i=0;
		public String create() {
			return "V"+i++;
		}};
	
	    private void createTree() {
    	
       	graph.addVertex("A0");
       	graph.addEdge(edgeFactory.create(), "A0", "B0");
       	graph.addEdge(edgeFactory.create(), "A0", "B1");
       	graph.addEdge(edgeFactory.create(), "A0", "B2");
       	
       	graph.addEdge(edgeFactory.create(), "B0", "C0");
       	graph.addEdge(edgeFactory.create(), "B0", "C1");
       	graph.addEdge(edgeFactory.create(), "B0", "C2");
       	graph.addEdge(edgeFactory.create(), "B0", "C3");

       	graph.addEdge(edgeFactory.create(), "C2", "H0");
       	graph.addEdge(edgeFactory.create(), "C2", "H1");

       	graph.addEdge(edgeFactory.create(), "B1", "D0");
       	graph.addEdge(edgeFactory.create(), "B1", "D1");
       	graph.addEdge(edgeFactory.create(), "B1", "D2");

       	graph.addEdge(edgeFactory.create(), "B2", "E0");
       	graph.addEdge(edgeFactory.create(), "B2", "E1");
       	graph.addEdge(edgeFactory.create(), "B2", "E2");

       	graph.addEdge(edgeFactory.create(), "D0", "F0");
       	graph.addEdge(edgeFactory.create(), "D0", "F1");
       	graph.addEdge(edgeFactory.create(), "D0", "F2");
       	
       	graph.addEdge(edgeFactory.create(), "D1", "G0");
       	graph.addEdge(edgeFactory.create(), "D1", "G1");
       	graph.addEdge(edgeFactory.create(), "D1", "G2");
       	graph.addEdge(edgeFactory.create(), "D1", "G3");
       	graph.addEdge(edgeFactory.create(), "D1", "G4");
       	graph.addEdge(edgeFactory.create(), "D1", "G5");
       	graph.addEdge(edgeFactory.create(), "D1", "G6");
       	graph.addEdge(edgeFactory.create(), "D1", "G7");
       	
       	// uncomment this to make it a Forest:
//       	graph.addVertex("K0");
//       	graph.addEdge(edgeFactory.create(), "K0", "K1");
//       	graph.addEdge(edgeFactory.create(), "K0", "K2");
//       	graph.addEdge(edgeFactory.create(), "K0", "K3");
//       	
//       	graph.addVertex("J0");
//    	graph.addEdge(edgeFactory.create(), "J0", "J1");
//    	graph.addEdge(edgeFactory.create(), "J0", "J2");
//    	graph.addEdge(edgeFactory.create(), "J1", "J4");
//    	graph.addEdge(edgeFactory.create(), "J2", "J3");
////    	graph.addEdge(edgeFactory.create(), "J2", "J5");
////    	graph.addEdge(edgeFactory.create(), "J4", "J6");
////    	graph.addEdge(edgeFactory.create(), "J4", "J7");
////    	graph.addEdge(edgeFactory.create(), "J3", "J8");
////    	graph.addEdge(edgeFactory.create(), "J6", "B9");

       	
    }
	
	
    class Rings implements VisualizationServer.Paintable {
    	
    	BalloonLayout<String,Integer> layout;
    	
    	public Rings(BalloonLayout<String,Integer> layout) {
    		this.layout = layout;
    	}
    	
		public void paint(Graphics g) {
			g.setColor(Color.gray);
		
			Graphics2D g2d = (Graphics2D)g;

			Ellipse2D ellipse = new Ellipse2D.Double();
			for(String v : layout.getGraph().getVertices()) {
				Double radius = layout.getRadii().get(v);
				if(radius == null) continue;
				Point2D p = layout.transform(v);
				ellipse.setFrame(-radius, -radius, 2*radius, 2*radius);
				AffineTransform at = AffineTransform.getTranslateInstance(p.getX(), p.getY());
				Shape shape = at.createTransformedShape(ellipse);
				
				MutableTransformer viewTransformer =
					vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW);
				
				if(viewTransformer instanceof MutableTransformerDecorator) {
					shape = vv.getRenderContext().getMultiLayerTransformer().transform(shape);
				} else {
					shape = vv.getRenderContext().getMultiLayerTransformer().transform(Layer.LAYOUT,shape);
				}

				g2d.draw(shape);
			}
		}

		public boolean useTransform() {
			return true;
		}
    }


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
