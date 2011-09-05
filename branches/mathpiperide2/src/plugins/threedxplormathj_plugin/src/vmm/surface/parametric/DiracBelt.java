/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.parametric;

import vmm.core.Animation;
import vmm.core.BasicAnimator;
import vmm.core.RealParamAnimateable;
import vmm.core.TimerAnimation;
import vmm.core.View;
import vmm.core3D.View3DLit;
import vmm.core3D.Vector3D;


/**
* Defines a Dirac Belt  (for the Belt-Trick) with parametric equations
* ((1/2)*(v - 8*u*cos(t)*(1 + 2*cos(t)^2*cos(v))*sin(t)*sin(v/2)^2 + cos(4*t)*(v - sin(v)) + sin(v)) -2.5,
* -cos(v)*sin(2*t) + u*cos(t)^2*sin(2*v);, 
* u*(-cos(2*t)*sin(t)^2 + cos(t)^2*(cos(2*t)*cos(2*v) + 4*cos(v)*sin(t)^2)) + (1/2)*sin(4*t)*(-v + sin(v)))) 
*/
public class DiracBelt extends SurfaceParametric {
	
	private RealParamAnimateable t = new RealParamAnimateable("vmm.surface.parametric.DiracBelt.t", 0, 0, 3.14159);
	
	public View getDefaultView() {
        View3DLit view = new View3DLit();
        view.setRenderingStyle(View3DLit.WIREFRAME_RENDERING);
        view.setViewStyle(View3DLit.RED_GREEN_STEREO_VIEW);
        return view;
    }
	

	public Animation getCreateAnimation(View view) {
	     TimerAnimation theAnimation = getMorphingAnimation(view,BasicAnimator.LOOP);
	     theAnimation.setMillisecondsPerFrame(200);
	     return theAnimation;
	  }
	
	public DiracBelt() {
		uPatchCount.setValueAndDefault(6);
		vPatchCount.setValueAndDefault(36);
		umin.reset(-0.5);
		umax.reset(0.5);
		vmin.reset(0);
		vmax.reset(6.0);
		addParameter(t);
		setDefaultWindow(-4.25, 4.25, -4.25, 4.25);
		setDefaultViewpoint(new Vector3D(-1, -7.8, 1.6));
		setFramesForMorphing(36);
	}
	
	public Vector3D surfacePoint(double u, double v) {
		double tt = t.getValue();
		double sinhalfv = Math.sin(v/2);
		double sinv = Math.sin(v);
		double sin2v = Math.sin(2*v);
		double cosv = Math.cos(v);
		double cos2v = Math.cos(2*v);
		double cost = Math.cos(tt);
		double sint = Math.sin(tt);
		double sin2t = Math.sin(2*tt);
		double sin4t = Math.sin(4*tt);
		double cos2t = Math.cos(2*tt);
		double cos4t = Math.cos(4*tt);
		double x = 0.5*(v - 8*u*cost*(1 + 2*cost*cost*cosv)*sint*sinhalfv*sinhalfv + cos4t*(v - sinv) + sinv) - 2.0;
		double y =  -cosv*sin2t + u*cost*cost*sin2v ;
		double z = u*(-cos2t*sint*sint + cost*cost*(cos2t*cos2v + 4*cosv*sint*sint)) + 0.5*sin4t*(-v + sinv) ;
		return new Vector3D(x,y,z);

	}

}
