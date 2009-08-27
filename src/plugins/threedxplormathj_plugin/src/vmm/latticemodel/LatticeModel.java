/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.latticemodel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import vmm.actions.AbstractActionVMM;
import vmm.actions.ActionList;
import vmm.actions.ActionRadioGroup;
import vmm.actions.ToggleAction;
import vmm.core.Animation;
import vmm.core.Display;
import vmm.core.I18n;
import vmm.core.IntegerParam;
import vmm.core.MouseTask;
import vmm.core.Parameter;
import vmm.core.ParameterDialog;
import vmm.core.ParameterInput;
import vmm.core.RealParam;
import vmm.core.TimerAnimation;
import vmm.core.Transform;
import vmm.core.Util;
import vmm.core.VMMSave;
import vmm.core.View;
import vmm.core3D.Exhibit3D;
import vmm.core3D.Transform3D;
import vmm.core3D.Vector3D;
import vmm.core3D.View3D;

import vmm.fourier.*;
import vmm.functions.ParseError;

abstract public class LatticeModel extends Exhibit3D {
	
	
	public final static int MAX_NUMBER_OF_NODES = 256;   // was originally 512---this caused problems
    //	 values for view.getDisplayStyle()
	public final static int DISPLAY_TRANSVERSE = 0;  
	public final static int DISPLAY_LONGITUDINAL = 1;
	public final static int DISPLAY_CIRCILAR = 2;
	public final static int DISPLAY_PENDULUM = 3;
	public final static int DISPLAY_BRIDGE = 4;
	public final static int DISPLAY_FPU_GRAPH = 5;  // (only used in the FermiPastaUlam exhibit)
	
    //	 values for view.getBoundaryCondition()
	public final static int BOUNDARY_CONDITION_ZERO = 0;  
	public final static int BOUNDARY_CONDITION_PERIODIC = 1;
	
   //	 values for view.getInitialShape();
	public final static int INITIAL_SHAPE_SINUSOIDAL = 0;  
	public final static int INITIAL_SHAPE_GAUSSIAN =   1;
	public final static int INITIAL_SHAPE_THERMAL =    2;
	public final static int INITIAL_SHAPE_KINK =       3;
	public final static int INITIAL_SHAPE_BREATHER =   4;
	
	public final static int INITIAL_MODE_FIRST =     0;  // values for view.getInitialMode()
	public final static int INITIAL_MODE_SECOND =    1;
	public final static int INITIAL_MODE_FOURTH =    2;
	public final static int INITIAL_MODE_EIGHTH =    3;
	public final static int INITIAL_MODE_SIXTEENTH = 4;
	
    //	customization variables that are meant to be set in the constructors of subclasses
	protected int numIterations;  // the number of iterations of Runge-Kutta per step.
	protected double defaultStepSize = 0.1;     
	protected int defaultBoundaryCondition = BOUNDARY_CONDITION_ZERO;
	protected int defaultInitialShape = INITIAL_SHAPE_SINUSOIDAL;
	protected int defaultInitialMode = INITIAL_MODE_FIRST;
	protected int defaultDisplayStyle = DISPLAY_TRANSVERSE;
	protected boolean canShowNormalModeDisplay = true;   //false;
	protected double defaultLatticeLength = 6.2832;
	protected double defaultLatticeDensity = 1.0;
	protected double defaultAmplitude = 1.0;
	protected int defaultNumberOfNodes = 32;
	protected double defaultScaleFactor = 1;
	protected boolean MaxPermittedDisplacementExceeded;  // some node has moved too far from its equilibrium position. Should abort lattice evolution;
	
	abstract protected double  potentialEnergy(double x, double theLatticeSpacing);  //gives the potential energy of a spring that joins the nodes. x is displacement from equilibrium
	abstract protected double internalForceLaw(double x, double theLatticeSpacing); // gives the restoring force of a spring when it is stretched x units from equilibrium 
	abstract protected double externalForceLaw(double x, double theLatticeSpacing);
	
 protected class LatticeData {  // stores the state of the lattice model at a given time.
		public double currentTime = 0;
		public double stepNumber = 0;
		int nodeCount;
		public double stepSize;  // This will be set equal to the step size from the control panel at each step.
		public double density;
		public double maxDisplacement; // The gg ofPascal version; maximum initial dosplacement.
		public double youngsModulus;
		public double latticeLength;
		public int latticeBoundaryCondition;
		public int latticeInitialShape;
		public int latticeInitialMode;
		public double TotalModalEnergy;
		public double[] currentDisplacement = new double[2*MAX_NUMBER_OF_NODES+1];  // gives the current displacements of the lattice nodes from equilibrium
		public double[] currentVelocity = new double[2*MAX_NUMBER_OF_NODES+1];    //gives the current velocities of the lattice nodes
		public double[]  RealNormalModeAngularFrequencies = new double[2*MAX_NUMBER_OF_NODES+1];
		public double[]  MyWaveArray = new double[2*MAX_NUMBER_OF_NODES+1];      // holds FT of currentDisplacement
		public double[]  MyVelocityArray = new double[2*MAX_NUMBER_OF_NODES+1];  // holds FT of currentVelocity

		
		private  void CopyCurrentDisplacementsAndCurrentVelocitiesToMyWaveArrayAndMyVelocityArray() {
		    for (int i = 1; i <= nodeCount; i++) {
		       MyWaveArray[i] = currentDisplacement[i];
		       MyVelocityArray[i] = currentVelocity[i];
		    }
		  }
		
		private void GetRealNormalModeAngularFrequencies(LMView view) {
		    double gamma = Math.sqrt(youngsModulus/(massPerNode(view) * 16 *latticeSpacing(view)));
		    for (int mode = 1; mode <= nodeCount; mode++) 
		      RealNormalModeAngularFrequencies[mode] = 2 * gamma * Math.sin(  (mode * Math.PI)/(2 * nodeCount )  );
		 }
		
		private void DSTMyWaveArrayAndMyVelocityArray() {
		     FourierSeries.DSTinPlace(MyWaveArray, nodeCount);
		     FourierSeries.DSTinPlace(MyVelocityArray,nodeCount);
		   }
		   
		   
		private void IDSTMyWaveArrayAndMyVelocityArray() {
			FourierSeries.IDSTinPlace(MyWaveArray, nodeCount);
			FourierSeries.IDSTinPlace(MyVelocityArray,nodeCount);
		  }
		
		 double sqr(double x) {return x*x;}

		 private double PotentialEnergyOfMode (int k)  {
		     return 17.77*Math.sqrt(2) * sqr( RealNormalModeAngularFrequencies[k]) * sqr( MyWaveArray[ k ] );
		  }

		 private double KineticEnergyOfMode (int k)  {
		     return  17.77*Math.sqrt(2) * sqr(MyVelocityArray  [ k ] );
		  }

		 private double TotalEnergyOfMode (int k)  {
		     return KineticEnergyOfMode(k) + PotentialEnergyOfMode(k);
		   }
		  
		 private void GetLatticeModeEnergies(LMView view) {
			     CopyCurrentDisplacementsAndCurrentVelocitiesToMyWaveArrayAndMyVelocityArray();
			     DSTMyWaveArrayAndMyVelocityArray();
			     GetRealNormalModeAngularFrequencies(view);
			    // for (int i = 1; i <= 2 * nodeCount; i++) {
			    //	 System.out.println(i);
			    //	 System.out.println(MyWaveArray[i]);  }
			     double TotalEnergyOfMode_i;
			     TotalModalEnergy = 0.0;
			     for (int i = 1; i <= nodeCount; i++) {
			       TotalEnergyOfMode_i = KineticEnergyOfMode(i) + PotentialEnergyOfMode(i);
			       TotalModalEnergy = TotalModalEnergy + TotalEnergyOfMode_i;
        //		   if ( (stepNumber % 100) == 1) {
		//	           if ( ( ( (i % latticeInitialMode) == 0) & ( (i / latticeInitialMode) < 6)) & ((stepNumber / 100) < 10000) )
		//	                MyFPUGraphData[(i/latticeInitialMode),(stepNumber/100)] = TotalEnergyOfMode_i;   }
			     }
			  }
		
		// These next methods define some auxilliary lattice data, 
		// latticeSpacing (the equilibrium distance between nodes)  
		// latticeAbscissa(i) the equilibrium position of the i-th node, and
		// massPerNode = total mass (= latticeLength times density) divided by numberOfNodes 
		// as functions of the primary lattice variables, the 
		// latticeLength, the density, and the numberOfNodes. 
		
 	public double latticeSpacing(LMView view){
			return latticeLength /view.getNumberOfNodes();
		}
		
		private double LatticeAbscissa (int NodeIndex,LMView view) {  
			   return NodeIndex * latticeSpacing(view);
		}
		
		public double massPerNode(LMView view) {
			return density * latticeSpacing(view);
		}
		
		 // These next routines give the initial "shapes" of the Lattice string, in terms of 
		 // the displacement x of a nodes from its equilibrium position.  
		 // The methods pulseWidth and half its reciprocal, waveNumber are convenience routines.
		
	    public double pulseWidth() {
			return 1.0/Math.pow(2.0,(double)latticeInitialMode);
		}

			
		public double waveNumber() {
			return  1.0/(2*pulseWidth());
		}
		
		private double SineWave (double x, double WaveNumber, double FundamentalLength) {
		    return maxDisplacement * Math.sin((2 * Math.PI * waveNumber() / FundamentalLength) * x);
		}
		
		 private double Breather(double x,double u){  // u is speed 
		  	  return 4 * Math.atan((Math.sqrt(1 - u * u) / u) * (1 / Math.cosh(Math.sqrt(1 - u * u) * x)));
		}
		 
		 private double Kink (double s,double u) {  // u is speed 
			    return 4 * Math.atan(Math.exp((s / Math.sqrt(1 - u * u))));
		}
		 
		 private double Gaussian (double x, double sigma) {  //sigma is the standard deviation
			   return (1 / (sigma * Math.sqrt(2 * Math.PI))) * Math.exp(-((x*x) / (sigma*sigma)));
		}
		 
		 private double shapeFunction(double x,double sigma,double u) {  //x is the LatticeAbscissa of a node  
			  switch (latticeInitialShape) {
			  case INITIAL_SHAPE_SINUSOIDAL: return Math.sqrt(2) * SineWave(x, waveNumber(), latticeLength);
			  case INITIAL_SHAPE_GAUSSIAN: return Gaussian(x,sigma);
			  case INITIAL_SHAPE_THERMAL: return 0;
			  case INITIAL_SHAPE_KINK: return Kink(x,u);
			  case INITIAL_SHAPE_BREATHER: return  Breather(x,u);
			  default: return 0;
			  }
		  }
		 
		 private void InitializeDisplacementsAndVelocities(LMView view){
			 int i;
			 for( i = 1; i <= view.getNumberOfNodes(); i++) {
				 currentVelocity[i] = 0.0;                                      // the string is "plucked" not struck
				 currentDisplacement[i]  = shapeFunction(LatticeAbscissa(i,view),1,1);
				 currentVelocity[0] = 0.0;
				 currentDisplacement[0] = currentDisplacement[view.getNumberOfNodes()];
			  } 
		  }
	
		
		public LatticeData(LMView view) {
            //This construtor should initialize the LatticeData, using the values of (at least) view.getNumberOfNodes(),
			//   view.getInitialShape(), view.getInitialMode(), view.getBoundaryCondition(), view.latticeLength.getValue(),
			//   and view.latticeDensity.getValue()
			nodeCount = view.getNumberOfNodes();
			density = view.latticeDensity.getValue();
			maxDisplacement = view.amplitude.getValue();
			youngsModulus = 1; 
			latticeLength = view.latticeLength.getValue();
			latticeBoundaryCondition = view.getBoundaryCondition();
			latticeInitialShape = view.getInitialShape();
			latticeInitialMode =  view.getInitialMode();
			InitializeDisplacementsAndVelocities(view);
		}
		

			// The next few methods step the time forward by time step using Runge-Kutta to integrate Newton's Equations 
			// of Motion Force = mass * acceleration to get the acceleration from the force law that defines the particular 
			// lattice model. NextNodeIndex(i,view) is a utility routine returning i + 1) mod numberOfNodes
			
		private int NextNodeIndex(int i, int numNodes){     //  NextNodeIndex := (i + 1) mod NumberOfNodes 
		     if ((i >= numNodes )  || (i < 0) ) return  0;
		      return (i + 1);
		}
		
		private double PotentialEnergyOfSpring(LMView view,int node)  {  //Node is the node to left of spring}
		      return potentialEnergy(currentDisplacement[NextNodeIndex(node,nodeCount)] - currentDisplacement[node],
		    		  latticeSpacing(view));
		   }
		
		private double KineticEnergyOfNode(LMView view, int node) {
		    return  0.5 * massPerNode(view) * currentVelocity[node] * currentVelocity[node];
		   }
		   
		 private double LatticeTotalKineticEnergy(LMView view)  {
		     double KE= 0;
		     for (int node=1; node <= nodeCount; node++) 
		        KE = KE + KineticEnergyOfNode(view, node);
		        return KE;
		  }
		
		private double LatticeTotalPotentialEnergy(LMView view) {
		    double PE = 0;
		     for (int spring=0; spring <= nodeCount; spring++)   //there are nodeCount + 1 springs!!
		        PE = PE + PotentialEnergyOfSpring(view,spring); 
		       return PE;
		  }
		
		protected double LatticeTotalEnergy(LMView view) {
			return LatticeTotalPotentialEnergy(view) + LatticeTotalKineticEnergy(view);
		}
			
		 // This is the Acceleration of theNode from the forces due to adjacent modes and any external force.
		private double Acceleration (LMView view,int theNode, double PreviousNodeDisplacement, 
                                     double theNodeDisplacement, double NextNodeDisplacement) { 
               double externalForce, internalForce, totalForce;
               
               if  (  (latticeBoundaryCondition == BOUNDARY_CONDITION_ZERO) & 
            		   (( theNode == 0 ) || ( theNode == nodeCount ))) return 0;
               
                 internalForce = internalForceLaw(theNodeDisplacement - PreviousNodeDisplacement, latticeSpacing(view)) - 
                                  internalForceLaw(NextNodeDisplacement - theNodeDisplacement,latticeSpacing(view));
                 externalForce = externalForceLaw(theNodeDisplacement,latticeSpacing(view));
                 totalForce = internalForce + externalForce;
                 return totalForce/massPerNode(view);   //   Newton's Law: acceleration = Force/mass
          }

		
		protected void RungeKuttaLattice( double[] x0, double[] u0,LMView view){ 
		    double  h = stepSize;
		    double[] h1 = new double[nodeCount + 1];
		    double[] k1 = new double[nodeCount + 1];
		    double[] h2 = new double[nodeCount + 1];
		    double[] k2 = new double[nodeCount + 1];
		    double[] h3 = new double[nodeCount + 1];
		    double[] k3 = new double[nodeCount + 1];
		    double[] h4 = new double[nodeCount + 1];
		    double[] k4 = new double[nodeCount + 1];
	        int i;
		     for( i = 1; i <= nodeCount; i++) {
		      h1[i] = h * u0[i];
		      k1[i] = h * Acceleration(view,i, x0[i - 1], x0[i], x0[NextNodeIndex(i,nodeCount)]);
		      }
		      h1[0] = h1[nodeCount];
		      k1[0] = k1[nodeCount];
		      for( i = 1; i <= nodeCount; i++) {
		       h2[i] = h * (u0[i] + k1[i] / 2);
		       k2[i] = h * Acceleration(view,i, x0[i - 1] + h1[i - 1] / 2, x0[i] + h1[i] / 2, 
		    		                    x0[NextNodeIndex(i,nodeCount)] + h1[NextNodeIndex(i,nodeCount)] / 2);
		     }
		     h2[0] = h2[nodeCount];
		     k2[0] = k2[nodeCount];
		      for( i = 1; i <= nodeCount; i++) {
		       h3[i] = h * (u0[i] + k2[i] / 2);
		       k3[i] = h * Acceleration(view,i, x0[i - 1] + h2[i - 1] / 2, x0[i] + h2[i] / 2, 
		    		                    x0[NextNodeIndex(i,nodeCount)] + h2[NextNodeIndex(i,nodeCount)] / 2);
		     }
		      h3[0] = h3[nodeCount];
		      k3[0] = k3[nodeCount];
		      for( i = 1; i <= nodeCount; i++) {
		       h4[i] = h * (u0[i] + k3[i]);
		       k4[i] = h * Acceleration(view,i, x0[i - 1] + h3[i - 1], x0[i] + h3[i], 
		    		                     x0[NextNodeIndex(i,nodeCount)] + h3[NextNodeIndex(i,nodeCount)]);
		     }
		     h4[0] = h4[nodeCount];
		     k4[0] = k4[nodeCount];
		     for ( i = 0; i <= nodeCount; i++) {
		        x0[i] = x0[i] + h1[i] / 6 + h2[i] / 3 + h3[i] / 3 + h4[i] / 6;
		        u0[i] = u0[i] + k1[i] / 6 + k2[i] / 3 + k3[i] / 3 + k4[i] / 6;
		     }
		}
		
         // This method advances the LatticeData object by one step.
		
		public void step(LMView view) { // The view is a parameter in case any info from the view is needed.
			numIterations = 3;
			for (int i = 0; i < numIterations; i++) {
	          RungeKuttaLattice(currentDisplacement,currentVelocity,view);
	          currentTime = currentTime + stepSize;
			}
			 stepNumber++;
		}
   }   // end of class LatticeData

	
	 
	 protected void drawTransverse(View3D view){
		 int i;
		 Color saveColor = view.getColor();
		 LMView lmView = (LMView)view;
		 LatticeData data = lmView.getLatticeData();
		 double scale = lmView.controlPanel.getScaleFactor();
		 if (((LMView)view).getShowNormalModeDisplay())
				data.GetLatticeModeEnergies((LMView)view);
		 view.drawLine(0,0,data.latticeLength,0);
		 view.drawLine(0,-1.6/scale,0,1.6/scale);
		 view.drawLine(data.latticeLength,-1.6/scale,data.latticeLength,1.6/scale);
		 for (i=0;i < data.nodeCount; i++)
			 view.drawLine(data.LatticeAbscissa(i,lmView),data.currentDisplacement[i],data.LatticeAbscissa(i+1,lmView),data.currentDisplacement[i+1]);
		 view.setColor(Color.BLUE);
		 for (i=0;i < data.nodeCount; i++)
			 view.drawLine(data.LatticeAbscissa(i,lmView),data.currentVelocity[i],data.LatticeAbscissa(i+1,lmView),data.currentVelocity[i+1]);
		 view.setColor(saveColor);
	 }
	
	
	protected void doDraw3D(Graphics2D g, View3D view, Transform3D transform) {
		LMView lmView = (LMView)view;
		int displayStyle = lmView.getDisplayStyle();  // the view must be of type LMView (defined below)
		LatticeData data = lmView.getLatticeData();
		double pixelWidth = (data.latticeLength) / (transform.getWidth() - 30);  // allows for 15-pixel border on left and right
		double xmin = -15*pixelWidth;  // TODO:  check computation of xmin, xmax, ymin, ymax -- especially for 3D views  -- note that 3D views will have to be centered at 0
		double xmax = data.latticeLength + 15*pixelWidth;
		double scale = 1;
		if (displayStyle == DISPLAY_TRANSVERSE){
			scale = lmView.controlPanel.getScaleFactor();
		    double ymax = 2.0 / scale;  // TODO: ???    //   was 2/scale
		    double ymin = -ymax;
		    ((LMTransform)transform).resetXYLimits(xmin, xmax, ymin, ymax);
			  drawTransverse(view);
		}
		// TODO:  What about viewpoints in 3D ???
		if (displayStyle == DISPLAY_PENDULUM)
			view.setViewPoint(new Vector3D(0,-5*data.latticeLength,0));  // TODO: check viewpoints!
		else if (displayStyle == DISPLAY_BRIDGE)
			view.setViewPoint(new Vector3D(data.latticeLength,-5*data.latticeLength,data.latticeLength));
		
		// TODO:  insert the code to draw the data in the given display style
		if (view.getEnableThreeD())  // placeholder code
			view.drawString("(3D) Step number = " + data.stepNumber, new Vector3D(0,0,1));
		else  
			view.drawString("  TNE = " + Math.round(100000.0*data.LatticeTotalEnergy(lmView))/100000.0,0,-1.25/scale);
		//    view.drawString("  TME = " + Math.round(100000.0*data.TotalModalEnergy)/100000.0,0,-1.40/scale);
			view.drawString("  Time = " + Math.round(4.0*data.currentTime)/4.0,0,-1.55/scale);
	
		if (((LMView)view).getShowNormalModeDisplay()) {
			// TODO: draw the normal mode display ( maybe after additional checks? )
		}
	}

	public View getDefaultView() {
		return new LMView();
	}
	
	public Animation getCreateAnimation(View view) {
		LMView lmView = (LMView)view;  // view must be of type LMview
		return lmView.new EvolveLattice(true);
	}
	
	public ActionList getSettingsCommandsForView(View view) {
		ActionList commands = super.getSettingsCommandsForView(view);
		Object setXYLimitsCommand = commands.getItem(commands.getItemCount() - 1);
		if (setXYLimitsCommand instanceof AbstractActionVMM)
			((AbstractActionVMM)setXYLimitsCommand).setEnabled(false);
		return commands;
	}
	
	public Transform getDefaultTransform(View view) {
		return new LMTransform();
	}
	
	protected class LMTransform extends Transform3D {
		   // I need to define this class because resetLimits() is a protected method in
		   // the Transform class.  The resetXYLimits() is used to reset the x and y limits
		   // in the doDraw() method.  This will not work unless preserveAspect and
		   // applyTransform2D have been turned off, as they are here.  This is becuase by the time
		   // doDraw() is called, these two properties have already been applied to the limits and to
		   // the graphics context respectively.
		public void resetXYLimits(double xmin, double xmax, double ymin, double ymax) {
			resetLimits(xmin,xmax,ymin,ymax);
		}
	}

	public class LMView extends View3D {
		protected AbstractActionVMM restartAction = new AbstractActionVMM(I18n.tr("vmm.latticemodel.command.Start")) {
			public void actionPerformed(ActionEvent evt) {
				doRestart();
			}
		};
		protected AbstractActionVMM stopAction = new AbstractActionVMM(I18n.tr("vmm.latticemodel.command.Stop")) {
			public void actionPerformed(ActionEvent evt) {
				doStop();
			}
		};
		protected AbstractActionVMM continueAction = new AbstractActionVMM(I18n.tr("vmm.latticemodel.command.Continue")) {
			public void actionPerformed(ActionEvent evt) {
				doContinue();
			}
		};
		protected AbstractActionVMM stepAction = new AbstractActionVMM(I18n.tr("vmm.latticemodel.command.Step")) {
			public void actionPerformed(ActionEvent evt) {
				doStep();
			}
		};
		protected ActionRadioGroup displayStyleSelect = new ActionRadioGroup() {
			public void optionSelected(int selectedIndex) {
				setDisplayStyle(getSelectedIndex());
			}
		};
		protected ActionRadioGroup initialModeSelect = new ActionRadioGroup() {
			public void optionSelected(int selectedIndex) {
				setInitialMode(getSelectedIndex());
			}
		};
		protected ActionRadioGroup initialShapeSelect = new ActionRadioGroup() {
			public void optionSelected(int selectedIndex) {
				setInitialShape(getSelectedIndex());
			}
		};
		protected ActionRadioGroup boundaryConditionSelect = new ActionRadioGroup() {
			public void optionSelected(int selectedIndex) {
				setBoundaryCondition(getSelectedIndex());
			}
		};
		protected ToggleAction showNormalModeToggle = new ToggleAction(I18n.tr("vmm.latticemodel.ShowNormalModeDisplay"),canShowNormalModeDisplay) {
			public void actionPerformed(ActionEvent evt) {
				setShowNormalModeDisplay(getState());
			}
		};
		protected ToggleAction showControlPanelToggle = new ToggleAction(I18n.tr("vmm.latticemodel.ShowControlPanel"),true) {
			public void actionPerformed(ActionEvent evt) {
				setShowControlPanel(getState());
			}
		};
		protected RealParam latticeLength = new RealParam("vmm.latticeModel.LatticeLength",defaultLatticeLength);
		protected RealParam latticeDensity = new RealParam("vmm.latticeModel.LatticeDensity",defaultLatticeDensity);
		protected RealParam amplitude = new RealParam("vmm.latticeModel.Amplitude",defaultAmplitude);
		protected IntegerParam numberOfNodes = new IntegerParam("vmm.latticeModel.NumberOfNodes",defaultNumberOfNodes) {
			public ParameterInput createParameterInput(int inputType) {
				return new ParameterInput(this,inputType) {
					public String checkContents() {
						String contents = getText();
						double val = 0;
						try {
							val = Util.parseConstantExpression(contents).value();
						}
						catch (ParseError e) {
							requestFocus();
							selectAll();
							return I18n.tr("vmm.core.ParameterInput.badExpression",  getTitle(), contents, e.getMessage() );
						}
						int intval;
						if (val > 0)
							intval = (int)(val + 1e-8);
						else
							intval = (int)(val - 1e-8);
						if (Math.abs(val - intval) > 5e-9) {
							requestFocus();
							selectAll();
							return I18n.tr("vmm.core.ParameterInput.badint", getTitle());
						}
						if (intval < 3 || (intval > 65 &&
								intval != 128 && intval != 256)) {
							requestFocus();
							selectAll();
							return I18n.tr("vmm.latticemodel.LatticeModel.badNumberOfNodes");
						}
						return null;
					}
				};
			}
		};
		@VMMSave private int displayStyle = DISPLAY_TRANSVERSE;
		@VMMSave private int initialShape;
		@VMMSave private int initialMode = defaultInitialMode;
		@VMMSave private int boundaryCondition;
		@VMMSave private boolean showControlPanel = true;
		@VMMSave private boolean showNormalModeDisplay = canShowNormalModeDisplay;
		protected LatticeData latticeData;
		private ControlPanel controlPanel;
		public LMView() {
			setAntialiased(true);
			addParameter(latticeLength);
			latticeLength.setMinimumValueForInput(Double.MIN_VALUE);
			addParameter(latticeDensity);
			addParameter(amplitude);
			latticeDensity.setMinimumValueForInput(Double.MIN_VALUE);
			addParameter(numberOfNodes);
			numberOfNodes.setMinimumValueForInput(3);
			numberOfNodes.setMaximumValueForInput(512);
			displayStyleSelect.addItem(I18n.tr("vmm.latticemodel.TransverseDisplay"));
			displayStyleSelect.addItem(I18n.tr("vmm.latticemodel.LongitudinalDisplay"));
			displayStyleSelect.addItem(I18n.tr("vmm.latticemodel.CircularDisplay"));
			displayStyleSelect.addItem(I18n.tr("vmm.latticemodel.PendulumDisplay"));
			displayStyleSelect.addItem(I18n.tr("vmm.latticemodel.BridgeDisplay"));
			displayStyleSelect.setSelectedIndex(defaultDisplayStyle);
			initialShapeSelect.addItem(I18n.tr("vmm.latticeModel.SinusoidalInitialShape"));
			initialShapeSelect.addItem(I18n.tr("vmm.latticeModel.GaussianInitialShape"));
			initialShapeSelect.addItem(I18n.tr("vmm.latticeModel.ThermalInitialShape"));
			initialShapeSelect.addItem(I18n.tr("vmm.latticeModel.KinkInitialShape"));
			initialShapeSelect.addItem(I18n.tr("vmm.latticeModel.BreatherInitialShape"));
			initialShapeSelect.setSelectedIndex(defaultInitialShape);
			initialModeSelect.addItem(I18n.tr("vmm.latticeModel.initialMode.First"));
			initialModeSelect.addItem(I18n.tr("vmm.latticeModel.initialMode.Second"));
			initialModeSelect.addItem(I18n.tr("vmm.latticeModel.initialMode.Fourth"));
			initialModeSelect.addItem(I18n.tr("vmm.latticeModel.initialMode.Eighth"));
			initialModeSelect.addItem(I18n.tr("vmm.latticeModel.initialMode.Sixteenth"));
			initialModeSelect.setSelectedIndex(defaultInitialMode);
			boundaryConditionSelect.addItem(I18n.tr("vmm.latticeModel.ZeroBoundaryCondition"));
			boundaryConditionSelect.addItem(I18n.tr("vmm.latticeModel.PeriodicBoundaryCondition"));
			boundaryConditionSelect.setSelectedIndex(defaultBoundaryCondition);
			stopAction.setEnabled(false);
			setEnableThreeD(false);  // to match default display style
			setTransform(new LMTransform()); // this is needed so that the transform used for 2D will be a LMTransform!
			showAxesAction.setEnabled(false);
			setApplyGraphics2DTransform(false);  
			setPreserveAspect(false);
		}
		
		public int getNumberOfNodes() {  // returns a value that is quaranted to be legal
			int nodes = numberOfNodes.getValue();
			if (nodes < 3)
				return 3;
			if (nodes <= 64)
				return nodes;
			if (nodes <= 128)
				return 128;
			else
			return 256;
		}
		
		public LatticeData getLatticeData() {
			if (latticeData == null)
				latticeData = new LatticeData(this);
			return latticeData;
		}
		public int getDisplayStyle() {
			return displayStyle;
		}
		public void setDisplayStyle(int displayStyle) {
			if (displayStyle < 0 || displayStyle >= displayStyleSelect.getItemCount())
				return;  // ignore illegal values
			if (this.displayStyle == displayStyle)
				return;
			this.displayStyle = displayStyle;
			displayStyleSelect.setSelectedIndex(displayStyle);
			forceRedraw();
			boolean is3D = displayStyle == DISPLAY_PENDULUM || displayStyle == DISPLAY_BRIDGE;
			boolean changingDimension = is3D != getEnableThreeD();
			setEnableThreeD(is3D);
			if (is3D && changingDimension)  {
				setViewStyle(View3D.RED_GREEN_STEREO_VIEW);
				setViewpointAction.setEnabled(false); // it's enagled when setEnableThreeD(true) is called
			}
			if (displayStyle == DISPLAY_TRANSVERSE)
				controlPanel.showScaleFactor(I18n.tr("vmm.latticemodel.yScaleFactor"));
			else if (displayStyle == DISPLAY_FPU_GRAPH)
				controlPanel.showScaleFactor(I18n.tr("vmm.latticemodel.xScaleFactor"));
			else
				controlPanel.hideScaleFactor();
			if (displayStyle == DISPLAY_TRANSVERSE)
				controlPanel.setScaleFactor(defaultScaleFactor);
			else if (displayStyle == DISPLAY_FPU_GRAPH)
				controlPanel.setScaleFactor(0.005);
			if (changingDimension && getDisplay() != null)
				getDisplay().installMouseTask(getDefaultMouseTask());  // different for 2D and 3D.
		}
		public int getBoundaryCondition() {
			return boundaryCondition;
		}
		public void setBoundaryCondition(int boundaryCondition) {
			if (boundaryCondition != BOUNDARY_CONDITION_ZERO && boundaryCondition != BOUNDARY_CONDITION_PERIODIC)
				return;  // ignore illegal value
			if (this.boundaryCondition == boundaryCondition)
				return;
			this.boundaryCondition = boundaryCondition;
			boundaryConditionSelect.setSelectedIndex(boundaryCondition);
			initialConditionChanged();
		}
		public int getInitialShape() {
			return initialShape;
		}
		public void setInitialShape(int initialShape) {
			if (initialShape < 0 || initialShape >= initialShapeSelect.getItemCount())
				return;  // ignore illegal values
			if (this.initialShape == initialShape)
				return;
			this.initialShape = initialShape;
			initialShapeSelect.setSelectedIndex(initialShape);
			initialModeSelect.setEnabled(initialShape == INITIAL_SHAPE_SINUSOIDAL);
			initialConditionChanged();
		}
		public int getInitialMode() {
			return initialMode;
		}
		public void setInitialMode(int initialMode) {
			if (initialMode < 0 || initialMode >= initialModeSelect.getItemCount())
				return;  // ignore illegal values
			if (this.initialMode == initialMode)
				return;
			this.initialMode = initialMode;
			initialModeSelect.setSelectedIndex(initialMode);
			initialConditionChanged();
		}
		public MouseTask getDefaultMouseTask() { 
			if (getEnableThreeD())
				return null;
			else
				return new LatticeMouseTask2D();
		}
		public ActionList getActions() {
			ActionList actions = super.getActions();
			actions.add(stopAction);
			actions.add(continueAction);
			actions.add(stepAction);
			actions.add(null);
			ActionList boundary = new ActionList(I18n.tr("vmm.latticemodel.BoundaryCondition"));
			boundary.add(boundaryConditionSelect);
			actions.add(boundary);
			ActionList initialShp = new ActionList(I18n.tr("vmm.latticemodel.InitialShape"));
			initialShp.add(initialShapeSelect);
			actions.add(initialShp);
			ActionList initialMode = new ActionList(I18n.tr("vmm.latticemodel.InitialMode"));
			initialMode.add(initialModeSelect);
			actions.add(initialMode);
			actions.add(null);
			ActionList displayStyle = new ActionList(I18n.tr("vmm.latticemodel.DisplayStyle"));
			displayStyle.add(displayStyleSelect);
			actions.add(displayStyle);
			actions.add(null);
			if (canShowNormalModeDisplay)
				actions.add(showNormalModeToggle);
			actions.add(showControlPanelToggle);
			return actions;
		}
		public boolean getShowControlPanel() {
			return showControlPanel;
		}
		public void setShowControlPanel(boolean showControlPanel) {
			if (this.showControlPanel == showControlPanel)
				return;
			this.showControlPanel = showControlPanel;
			showControlPanelToggle.setState(showControlPanel);
			if (showControlPanel) {
				if (getDisplay() != null) {
					getDisplay().getHolder().add(getControlPanel(),BorderLayout.EAST);
					getDisplay().getHolder().validate();
				}
			}
			else {
				if (getDisplay() != null && controlPanel != null) {
					getDisplay().getHolder().remove(controlPanel);
					getDisplay().getHolder().validate();
				}
			}
		}
		public boolean getShowNormalModeDisplay() {
			return showNormalModeDisplay;
		}
		public void setShowNormalModeDisplay(boolean showNormalModeDisplay) {
			if ( ! canShowNormalModeDisplay)
				showNormalModeDisplay = false;  // don't allow this to be set to true if the exhibit can't show a NormalModeDisplay
			if (this.showNormalModeDisplay == showNormalModeDisplay)
				return;
			this.showNormalModeDisplay = showNormalModeDisplay;
			showNormalModeToggle.setState(showNormalModeDisplay);
			forceRedraw();
		}
		public void parameterChanged(Parameter param, Object oldValue,Object newValue) {
		    super.parameterChanged(param, oldValue, newValue);
		    initialConditionChanged();
		}
		
		
		public void setDisplay(Display display) {
			super.setDisplay(display);
			if (display != null)
				display.setStopAnimationsOnResize(false);
			if (display != null && showControlPanel) {
				display.getHolder().add(getControlPanel(),BorderLayout.EAST);
				display.getHolder().validate();
			}
		}
		ControlPanel getControlPanel() {
				// I don't want to create the control panel when view is first created to give Fermi-Pasta-Ulam
				// subclass a chance to add a new display style to displayStyleSelect.
			if (controlPanel == null)
				controlPanel = new ControlPanel(); 
			return controlPanel;  
		}
		private void initialConditionChanged() {
			if (getDisplay() != null)
				getDisplay().stopAnimation();
			latticeData = null;
			restartAction.putValue(Action.NAME, I18n.tr("vmm.latticemodel.command.Start"));
			forceRedraw();
		}
		private void doRestart() {
			getDisplay().stopAnimation();
			getDisplay().installAnimation(new EvolveLattice(true));
		}
		private void doStop() {
			getDisplay().stopAnimation();
		}
		private void doStep() {  
			LatticeData data = getLatticeData(); // getLatticeData() never returns null
			data.stepSize = getControlPanel().getStepSize();
				// TODO:  should the step size really be obtained from the control panel at each step instead of just at the start?
			data.step(this);  
			forceRedraw();
		}
		private void doContinue() {
			getDisplay().installAnimation(new EvolveLattice(false));
		}
		private class LatticeMouseTask2D extends MouseTask {
			int startY;
			int startX;
			double initialScaleFactor;
			boolean dragging;
			public boolean doMouseDown(MouseEvent evt, Display display, View view, int width, int height) {
				if (!controlPanel.isScaleFactorShown()) {
					dragging = false;
					return false;
				}
				startX = evt.getX();
				startY = evt.getY();
				initialScaleFactor = controlPanel.getScaleFactor();
				dragging = true;
				return true;
			}
			public void doMouseDrag(MouseEvent evt, Display display, View view, int width, int height) {
				if (! dragging)
					return;
				double multiplier = Math.exp((startY - evt.getY() + (evt.getX() - startX))/250.0);
				controlPanel.setScaleFactor(initialScaleFactor * multiplier);
				forceRedraw();
			}
			public void doMouseUp(MouseEvent evt, Display display, View view, int width, int height) {
				dragging = false;
			}
		}
		private class EvolveLattice extends TimerAnimation {
			boolean create;
			EvolveLattice(boolean create) {
				setMillisecondsPerFrame(5);  // TODO:  Check speed!
				this.create = create;
			}
			protected void drawFrame() {
				doStep();
			}
			protected void animationStarting() {
				if (create)
					latticeData = null;  // forces data to be recomputed based on settings for initial conditions
				continueAction.setEnabled(false);
				stepAction.setEnabled(false);
				stopAction.setEnabled(true);
				restartAction.putValue(Action.NAME, I18n.tr("vmm.latticemodel.command.Restart"));
			}
			protected void animationEnding() {
				continueAction.setEnabled(true);
				stepAction.setEnabled(true);
				stopAction.setEnabled(false);
			}
		}
		protected class ControlPanel extends JPanel {
			JTextField stepSizeInput;
			JTextField scaleFactorInput;
			boolean scaleFactorShown = true;
			JLabel scaleFactorLabel;
			JPanel scaleFactorPanel;
			JPanel componentPanel;
			Font font;
			ControlPanel() {
				setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
				componentPanel = new JPanel();
				componentPanel.setLayout(new GridLayout(0,1));
				add(componentPanel);
				font = new Font("SansSerif", Font.BOLD, 10);
				Font inputFont = new Font("SansSerif", Font.PLAIN, 10);
				JButton restartButton = new JButton(restartAction);
				restartButton.setFont(font);
				componentPanel.add(restartButton);
				JButton stopButton = new JButton(stopAction);
				stopButton.setFont(font);
				componentPanel.add(stopButton);
				JButton continueButton = new JButton(continueAction);
				continueButton.setFont(font);
				componentPanel.add(continueButton);
				JButton stepButton = new JButton(stepAction);
				stepButton.setFont(font);
				componentPanel.add(stepButton);
				componentPanel.add(Box.createVerticalStrut(1));
				stepSizeInput = new JTextField("" + defaultStepSize, 6);
				stepSizeInput.setFont(inputFont);
				JLabel lbl = new JLabel(I18n.tr("vmm.latticemodel.StepSize") + "=");
				lbl.setFont(font);
				JPanel p = new JPanel();
				p.add(lbl);
				p.add(stepSizeInput);
				componentPanel.add(p);
				scaleFactorInput = new JTextField("" + defaultScaleFactor, 5);
				scaleFactorInput.setFont(inputFont);
				scaleFactorLabel = new JLabel(I18n.tr("vmm.latticemodel.yScaleFactor") + "=");
				scaleFactorLabel.setFont(font);
				scaleFactorPanel = new JPanel();
				scaleFactorPanel.add(scaleFactorLabel);
				scaleFactorPanel.add(scaleFactorInput);
				componentPanel.add(scaleFactorPanel);
				JRadioButton[] displaySelectButtons = displayStyleSelect.createRadioButtons();
				for (JRadioButton rb : displaySelectButtons) {
					rb.setFont(font);
					componentPanel.add(rb);
				}
				componentPanel.add(Box.createVerticalStrut(1));
				JButton b = new JButton(I18n.tr("vmm.latticemodel.setParams"));
				b.setFont(font);
				componentPanel.add(b);
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						getDisplay().stopAnimation();
						Parameter[] params = getViewAndExhibitParameters();
						ParameterDialog.showDialog(getDisplay(),I18n.tr("vmm.core.dialogtitle.SetParameters"),params);
					}
				});
			}
			double getStepSize() { // get step size from input box, but return default step size if input is bad
				try {
					double d = Double.parseDouble(stepSizeInput.getText().trim());
					if (d <= 0)
						throw new NumberFormatException();
					return d;
				}
				catch (NumberFormatException e) {
					stepSizeInput.setText("" + defaultStepSize);
					return defaultStepSize;
				}
			}
			double getScaleFactor() { // get scaleFactor from input box, but return default scale factor if input is bad
				try {
					double d = Double.parseDouble(scaleFactorInput.getText().trim());
					if (d <= 0)
						throw new NumberFormatException();
					return d;
				}
				catch (NumberFormatException e) {
					scaleFactorInput.setText("" + defaultScaleFactor);
					return defaultScaleFactor;
				}
			}
			void setScaleFactor(double scaleFactor) {
				String str = String.format("%.4f", scaleFactor);
				scaleFactorInput.setText(str);
			}
			boolean isScaleFactorShown() {
				return scaleFactorShown;
			}
			void hideScaleFactor() {
				if (scaleFactorShown) {
					componentPanel.remove(6);
					componentPanel.add(Box.createVerticalStrut(1), 6);
					scaleFactorShown = false;
					validate();
				}
			}
			void showScaleFactor(String label) {
				scaleFactorLabel.setText(label);
				if (! scaleFactorShown) {
					componentPanel.remove(6);
					componentPanel.add(scaleFactorPanel, 6);
					scaleFactorShown = true;
				}
				validate();
			}
		}
	
	}


}
