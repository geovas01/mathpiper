/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.implicit;

import vmm.core.RealParamAnimateable;
import vmm.core3D.Vector3D;

public class Join2Tori extends SurfaceImplicit {

	public static double sqr(double x){return x*x;}
	
	private RealParamAnimateable a = new RealParamAnimateable("vmm.surface.implicit.Join2Tori.a", 1.5, 1.5, 1.5);
	private RealParamAnimateable b = new RealParamAnimateable("vmm.surface.implicit.Join2Tori.b", 0.5, 0.5, 0.5);
	private RealParamAnimateable c = new RealParamAnimateable("vmm.surface.implicit.Join2Tori.c", 2.2, 2.2, 2.2);


	public double heightFunction(double x, double y, double z) {
		double height;
		double cc = c.getValue();
		double bb = b.getValue();
		double aa = a.getValue();
		
		double aasqr = sqr(aa);  double bbsqr = sqr(bb);
		double aux = 4*bbsqr*aasqr;
		double xlsqr = sqr(x-cc);   double xrsqr = sqr(x+cc); 
		double ysqr = sqr(y);       double zsqr = sqr(z); 
		double partA = sqr( (xlsqr + ysqr + zsqr) - bbsqr - aasqr ) + 4*aasqr*zsqr - aux; // first torus
		double partB = sqr( (xrsqr + ysqr + zsqr) - bbsqr - aasqr ) + 4*aasqr*zsqr - aux; // second torus
	    height = (partA*partB)/sqr(1 + xlsqr + xrsqr + ysqr + zsqr/2);   
		return height;
	}
	
	public Join2Tori(){
		addParameter(c);
		addParameter(b);
		addParameter(a);
		setDefaultWindow(-3.75, 3.75, -3.75, 3.75);
		setDefaultViewpoint(new Vector3D(2,16,10));
		searchRadius.reset(5);
		randomLineCount.reset(60000);
		pointCloudCount.reset(12000);
		level.reset(0.5,1.0,1.5);
		setFramesForMorphing(11);
	}

}
