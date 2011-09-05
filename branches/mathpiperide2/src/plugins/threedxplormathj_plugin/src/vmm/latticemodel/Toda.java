/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.latticemodel;

import vmm.core.I18n;
import vmm.core.RealParam;
import vmm.core.View;
import vmm.latticemodel.FermiPastaUlam.FPULatticeData;
import vmm.latticemodel.FermiPastaUlam.FPUView;
import vmm.latticemodel.LatticeModel.LMView;
import vmm.latticemodel.LatticeModel.LatticeData;

public class Toda extends LatticeModel {
	protected RealParam a = new RealParam("vmm.latticeModel.Toda.a",1);
	protected RealParam b = new RealParam("vmm.latticeModel.Toda.b",1);
	
	protected double potentialEnergy(double x,double theLatticeSpacing){  //gives the potential energy of a spring that joins the nodes. d is displacement from equilibrium
		return ( (a.getValue()/b.getValue()) * (Math.exp(-b.getValue() * x) - 1)  + a.getValue() * x )/ (16*theLatticeSpacing);  // ( (a/b) (exp(-b x) -1) + a x)/(16*latticeSpacing)
	}
	protected double internalForceLaw(double x,double theLatticeSpacing)  { // gives the restoring force of a spring when it is stretched d units from equilibrium 
		return a.getValue() * (Math.exp(-b.getValue() * x) - 1)/(16*theLatticeSpacing);
	}
	protected double externalForceLaw(double x,double theLatticeSpacing) {
		return 0;
	}
	
	protected class TodaLatticeData extends LatticeData {
		// TODO:  This class should add any extra data/functionality required for this exhibit.
		
		
		
		
		TodaLatticeData(TodaView view) {
			super(view);
			youngsModulus = a.getValue() * b.getValue();
			// TODO: Initialize extra data elements.
		}
		
		public void step(LMView view) {
			numIterations = 1;
			super.step(view);
			TodaView todaView = (TodaView)view;
			// TODO: Do extra Toda stuff??
		}
	}
	


	public Toda() {
		canShowNormalModeDisplay = true;
		defaultStepSize = 0.03;
		defaultAmplitude = 0.5;
	    defaultScaleFactor = 1.5;
	    defaultNumberOfNodes = 128;
		defaultBoundaryCondition = BOUNDARY_CONDITION_PERIODIC;
		defaultInitialShape = INITIAL_SHAPE_SINUSOIDAL;
		defaultInitialMode =  INITIAL_MODE_EIGHTH;
		addParameter(b);
		addParameter(a);
	}
	
	public View getDefaultView() {
		return new TodaView();
	}
	
	public class TodaView extends LMView {
		
		public TodaView() {
		}
		
		public LatticeData getLatticeData() {
			if (latticeData == null)
				latticeData = new TodaLatticeData(this);  // use the right type of lattice data!
			return latticeData;
		}
	}
}
