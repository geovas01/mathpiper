/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 
package vmm.surface.parametric;

//import vmm.core.Animation;
import vmm.core.RealParamAnimateable;
import vmm.core.View;
import vmm.core3D.Vector3D;
import vmm.surface.SurfaceView;

/**
 * Defines a surface with parametric equations.
 * @author traudel
 *
 */
public class SnailShell  extends SurfaceParametric {

	private RealParamAnimateable a = new RealParamAnimateable("a",1.0,1.0,1.0);
	private RealParamAnimateable b = new RealParamAnimateable("b",1.4,1.0,1.4);
	private RealParamAnimateable c = new RealParamAnimateable("c",0.05,0.1,0.05);
	private RealParamAnimateable d = new RealParamAnimateable("d",6.0,2.0,6.0);


	
	public SnailShell() {
		addParameter(d);
		addParameter(c);
		addParameter(b);
		addParameter(a);
		setDefaultViewpoint(new Vector3D(-40,20,0));
		setDefaultWindow(-3.5,3.5,-3.5,3.5);
		umin.reset("-pi");
		umax.reset("pi");
		vmin.reset(-2);
		vmax.reset(25);
		uPatchCount.setValueAndDefault(30);
		vPatchCount.setValueAndDefault(40);
	}
	

	public Vector3D surfacePoint(double u, double v) {
		double auxa = a.getValue();
		double auxb = b.getValue();
		double auxc = c.getValue();
		double auxd = d.getValue();
		double vv = v+(v-vmin.getValue())*(v-vmin.getValue())/16;
		double s = Math.exp(-auxc*vv);
		double r = s*(auxa+auxb*Math.cos(u));
		return new Vector3D(r * Math.cos(vv),auxd-s*(auxd+auxb*Math.sin(u)),r * Math.sin(vv));
	}


	public View getDefaultView() {
		SurfaceView mySurfaceView = new SurfaceView();
		mySurfaceView.setGridSpacing(0);
		mySurfaceView.setAntialiased(true);
		return mySurfaceView;
	}

}