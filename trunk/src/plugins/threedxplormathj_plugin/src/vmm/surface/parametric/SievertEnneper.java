/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.parametric;

import vmm.core.RealParamAnimateable;
import vmm.core3D.Vector3D;
import vmm.core3D.View3DLit;

/** 
 * Defines the SievertEnneper surface.
*/
public class SievertEnneper extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa","0.0","2.5","-2.5");
	
	
	public SievertEnneper() {
		uPatchCount.setValueAndDefault(12);
		vPatchCount.setValueAndDefault(12);
		umin.reset("-1.5608");
		umax.reset("1.5608");
		vmin.reset("0.02");
		vmax.reset("3.12159");
		setDefaultViewpoint(new Vector3D(10,-10,10)); 
		setDefaultWindow(-2.5,2.5,-2.5,2.5);
		setDefaultOrientation(View3DLit.NO_ORIENTATION);
		addParameter(aa);
	}
	
	
	public Vector3D surfacePoint(double u, double v) {
	    double AA = Math.exp(aa.getValue());
	    double phiu = -u/Math.sqrt(AA + 1) + Math.atan(Math.sqrt(AA + 1)*Math.tan(u));
	    double cu = Math.cos(u);
	    double su = Math.sin(u);
	    double sv = Math.sin(v);
	    double auv = 2/(AA + 1 - AA*sv*sv*cu*cu);
	    double ruv = auv*Math.sqrt((AA + 1)*(1 + AA*su*su))*sv/Math.sqrt(AA);
	    double x = ruv*Math.cos(phiu);
		double z = ruv*Math.sin(phiu);
		double y = (Math.log(Math.tan(v/2)) + (AA + 1)*auv*Math.cos(v))/Math.sqrt(AA);
		return new Vector3D(x,y,z);
	}

}
/**Pascal
ca := exp(aa);
phiu := -u/sqrt(ca + 1) + arctan(sqrt(ca+ 1)*tan(u));
auv := 2/(ca + 1 - ca*sin(v)**2*cos(u)**2);
ruv := auv*sqrt((ca + 1)*(1 + ca*sin(u)**2))*sin(v)/sqrt(ca);
P.x := ruv*cos(phiu);
P.z := ruv*sin(phiu);
P.y := (ln(tan(v/2)) + (ca + 1)*auv*cos(v))/sqrt(ca);

SievertEnneperXYZ := P; */
