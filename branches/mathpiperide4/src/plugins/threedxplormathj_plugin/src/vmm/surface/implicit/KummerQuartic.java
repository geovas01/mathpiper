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

public class KummerQuartic extends SurfaceImplicit {

	public static double sqr(double x){return x*x;}
	
	private RealParamAnimateable a = new RealParamAnimateable("vmm.surface.implicit.KummerQuartic.a", 1.3, 1, 1.5);


	public double heightFunction(double x, double y, double z) {
		double height;
		double aa = a.getValue();
		double xsqr =sqr(x); double ysqr = sqr(y); double zsqr = sqr(z);
        double lambda  = (3.0*sqr(aa) - 1.0)/(3.0 - sqr(aa));
        double  w2     = Math.sqrt(2);
        double pp     = 1.0 - z - w2*x;
        double qq     = 1.0 - z + w2*x;
        double rr     = 1.0 + z + w2*y;
        double ss     = 1.0 + z - w2*y;
         height =  sqr(xsqr + ysqr + zsqr - sqr(aa)) - lambda*pp*qq*rr*ss;  
		return height;
	}
	
	public KummerQuartic(){
		addParameter(a);
		setDefaultWindow(-3.25, 3.25, -3.25, 3.25);
		setDefaultViewpoint(new Vector3D(-9.92,9.1,-11.65));
		setDefaultViewUp(new Vector3D(-0.52,0.39,0.75));
		searchRadius.reset(3);
		randomLineCount.reset(60000);
		pointCloudCount.reset(24000);
		level.reset(0.0,0.05,0.1);
		setFramesForMorphing(12);
		heightFunctionType = equationType.QUARTIC;
	}

}