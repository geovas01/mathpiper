/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.latticemodel;

import java.awt.Graphics2D;

import vmm.core.I18n;
import vmm.core.RealParam;
import vmm.core.View;
import vmm.core3D.Transform3D;
import vmm.core3D.View3D;
import vmm.latticemodel.LatticeModel.LatticeData;

public class FermiPastaUlam extends LatticeModel {
	
	protected RealParam a = new RealParam("vmm.latticeModel.FermiPastaUlam.a",1);
	protected RealParam b = new RealParam("vmm.latticeModel.FermiPastaUlam.b",0.25);
	protected RealParam c = new RealParam("vmm.latticeModel.FermiPastaUlam.c",0);
	protected RealParam d = new RealParam("vmm.latticeModel.FermiPastaUlam.d",0);
	
	protected double potentialEnergy(double x,double theLatticeSpacing){ //gives the potential energy of a spring that joins the nodes. x is displacement from equilibrium
		return x * x * (a.getValue() / 2 + x * (b.getValue() / 3 + x * (c.getValue() / 4 + x * d.getValue() / 5)))/(16*theLatticeSpacing);
	}
	protected double internalForceLaw(double x,double theLatticeSpacing)  { // gives the restoring force of a spring when it is stretched x units from equilibrium 
		return  -x * (a.getValue() + x * (b.getValue() + x * (c.getValue() + x * d.getValue())))/(16*theLatticeSpacing);   //-(a x + b x^2 + c x^3 + d x^4)/(16 LatticeSpacing)
	}
	protected double externalForceLaw(double x,double theLatticeSpacing) {
		return 0;
	}
	
	protected class FPULatticeData extends LatticeData {
		// TODO:  This class should add any extra data/functionality required for this exhibit.
		
		FPULatticeData(FPUView view) {
			super(view);
			// TODO: Initialize extra data elements.
			youngsModulus = a.getValue();
		}
		
		public void step(LMView view) {
			super.step(view);
			FPUView fpuView = (FPUView)view;
			// TODO: Do extra FPU stuff??
		}
	}
		
	public FermiPastaUlam() {
		canShowNormalModeDisplay = true;
		defaultAmplitude = 1;
		addParameter(d);
		addParameter(c);
		addParameter(b);
		addParameter(a);
	}
	
	protected void doDraw3D(Graphics2D g, View3D view, Transform3D transform) {
		int displayStyle = ((LMView)view).getDisplayStyle();
		if (displayStyle != DISPLAY_FPU_GRAPH) {
			super.doDraw3D(g, view, transform);
			return;
		}
		// TODO:  draw the data using FPU Graph  Display
		view.drawString("FPU Graph Display",0,1);  // placeholder code
		if (((LMView)view).getShowNormalModeDisplay()) {
			// TODO: draw the normal mode display ( maybe after additional checks? )
		}
	}

	public View getDefaultView() {
		return new FPUView();
	}
	
	public class FPUView extends LMView {
		
		public FPUView() {
			displayStyleSelect.addItem(I18n.tr("vmm.latticemodel.FermiPastaUlam.FPUGraphDisplay"));
		}
		public LatticeData getLatticeData() {
			if (latticeData == null)
				latticeData = new FPULatticeData(this);  // use the right type of lattice data!
			return latticeData;
		}
	}

}
