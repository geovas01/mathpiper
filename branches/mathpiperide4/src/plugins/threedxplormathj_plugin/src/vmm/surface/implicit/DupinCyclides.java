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

public class DupinCyclides extends SurfaceImplicit {

	public static double sqr(double x){return x*x;}
	
	private RealParamAnimateable a = new RealParamAnimateable("vmm.surface.implicit.DupinCyclides.a", 2, 2, 2);
	private RealParamAnimateable b = new RealParamAnimateable("vmm.surface.implicit.DupinCyclides.b", 1.8, 1.8, 1.8);
//	private RealParamAnimateable c = new RealParamAnimateable("vmm.surface.implicit.DupinCyclides.c", 1, 1, 1);
	private RealParamAnimateable d = new RealParamAnimateable("vmm.surface.implicit.DupinCyclides.d", 1, 0.7, 2.5);


	public double heightFunction(double x, double y, double z) {
		double height;
		double dd = d.getValue();
//		double cc = c.getValue();
		double bb = b.getValue();
		double aa = a.getValue();
		
		double xsqr=sqr(x); double ysqr = sqr(y); double zsqr = sqr(z);  
        
        if (Math.abs(bb) > Math.abs(aa)) {bb = Math.signum(bb) * Math.abs(aa);}
        
        height = sqr(xsqr + ysqr + zsqr - dd * dd + bb * bb) - 
                                 4 * sqr(aa * x + Math.sqrt(aa * aa - bb * bb) * dd) - 
                                 4 * bb * bb * ysqr;
		return height;
	}
	
	public DupinCyclides(){
		addParameter(b);
		addParameter(a);
		setDefaultWindow(-4.0, 4.0, -4.0, 4.0);
		setDefaultViewpoint(new Vector3D(0,10,10));
		searchRadius.reset(4);
		randomLineCount.reset(60000);
		pointCloudCount.reset(12000);
		level.reset(0,0,0);
		setFramesForMorphing(11);
	}

}