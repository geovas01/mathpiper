/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.parametric;

import java.awt.event.ActionEvent;

import vmm.actions.AbstractActionVMM;
import vmm.actions.ActionList;
import vmm.core.BasicAnimator;
import vmm.core.Complex;
import vmm.core.I18n;
import vmm.core.IntegerParam;
import vmm.core.Parameter;
import vmm.core.RealParamAnimateable;
import vmm.core.View;
import vmm.core3D.Vector3D;
import vmm.core3D.View3DLit;
import vmm.surface.SurfaceView;
import vmm.core.Animation;


/**
 * Defines a Boy's surface, following Bryant-Kusner
 */
public class BoyBryantKusner extends SurfaceParametric {
	
//	private IntegerParam mp = new IntegerParam("Choose default morph No",5);
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa","0.5","0.5","0.5");  
//	int MP = mp.getValue();
	
	public BoyBryantKusner() {
		uPatchCount.setValueAndDefault(16);
		vPatchCount.setValueAndDefault(32);
		// the default morph should have 20 frames and run at half speed
		umin.reset(-1.45,-0.2,-1.449);
		umax.reset(0.0);
		vmin.reset(0.0);
		vmax.reset("2*pi");
		setDefaultOrientation(View3DLit.NO_ORIENTATION);
		setDefaultViewpoint(new Vector3D(0,0,17.3));
		setDefaultWindow(-2.1,2.1,-2.1,2.1);
		addParameter(aa);
	//	addParameter(mp);
	}

	public View getDefaultView() {
		SurfaceView view = (SurfaceView)super.getDefaultView();
		    //view.setShowUGridLines(false);
		return view;
	}
	
/*
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
	    super.parameterChanged(param, oldValue, newValue);
	    if (mp.getValue() != MP){
    	    MP = mp.getValue();
	    if (MP==1){
	      	uPatchCount.setValue(16);
			vPatchCount.setValue(32);
			umin.reset(-1.45,-0.2,-1.449);
			umax.reset(0.0);
			vmin.reset(0.0);
			vmax.reset("2*pi");
	    }
	    else{
	      	uPatchCount.setValue(24);
			vPatchCount.setValue(6);
			umin.reset(-1.45);
			umax.reset(1.45);
			vmin.reset("0.0","0.0","2*pi-0.7");
			vmax.reset("0.7","0.7","2*pi");
	    }
	    }
*/
	
	public ActionList getAdditionalAnimationsForView(final View view) {
		ActionList actions = super.getAdditionalAnimationsForView(view);
		actions.add(new AbstractActionVMM(I18n.tr("common.AlternativeMorph")) {
			public void actionPerformed(ActionEvent evt) {
				BasicAnimator animation = new BasicAnimator();
				animation.setLooping(BasicAnimator.LOOP);  // if not set, the animation will only play once through
				//animation.setLooping(BasicAnimator.OSCILLATE);
				animation.setUseFilmstrip(getUseFilmstripForMorphing());  // respect the setting of useFilmstripForMorphing
				animation.setFrames(getFramesForMorphing());  // Use number of frames as set for morphing this exhibit.
				animation.setMillisecondsPerFrame(200);  // The value used for the standard Morph animation is 100
				animation.addWithCustomValue(uPatchCount, 24);
				animation.addWithCustomValue(vPatchCount, 6);
				animation.addWithCustomValue(umin, -1.45);
				animation.addWithCustomValue(umax, 1.45);
				animation.addWithCustomLimits(vmin, 0, 2*Math.PI-0.7);
				//animation.addWithCustomLimits(vmin, 0, 0);
				animation.addWithCustomLimits(vmax, 0.7, 2*Math.PI);
				view.getDisplay().installAnimation(animation);
			}
		});
		return actions;

	}
	
	public Vector3D surfacePoint(double u, double v) {
		Complex One_C = new Complex(1.0,0.0);
		Complex I_C = new Complex(0.0,1.0);
		double uu = Math.exp(Math.tan(u));
		Complex z = new Complex(uu*Math.cos(v), uu*Math.sin(v));
		//Complex z = new Complex(u*Math.cos(v),u*Math.sin(v)); // not good outside the unit disk
		Complex zSquare = new Complex(z.times(z));
		Complex zCube = new Complex(z.times(zSquare));
		Complex OneOverZsquare = new Complex(One_C.dividedBy(zSquare));
		Complex OneOverZCube = new Complex(One_C.dividedBy(zCube));
		Complex zSquarePlusOneOverZsquare = new Complex(zSquare.plus(OneOverZsquare));
		Complex zSquareMinusOneOverZsquare= new Complex(zSquare.minus(OneOverZsquare));
		Complex zCubePlusOneOverZCube = new Complex(zCube.plus(OneOverZCube));
		Complex zCubeMinusOneOverZCube = new Complex(zCube.minus(OneOverZCube));
		Complex multiplier = new Complex(One_C.dividedBy( zCubeMinusOneOverZCube.plus(Math.sqrt(5)) ));
		Complex Wx = new Complex(zSquareMinusOneOverZsquare.times(I_C).times(multiplier)); 
		Complex Wy = new Complex(multiplier.times(zSquarePlusOneOverZsquare)); 
		Complex Wz = new Complex(zCubePlusOneOverZCube.times(I_C).times(2.0/3.0));
		Wz = multiplier.times(Wz);
		double x  = Wx.re;
		double y  = Wy.re;
		double zz = Wz.re + aa.getValue();
		double denom = 1.0/Math.max(0.00001, x*x + y*y + zz*zz);
		return new Vector3D(denom*x,denom*y,denom*zz);
	}

}