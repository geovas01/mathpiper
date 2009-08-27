/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.parametric;

import java.awt.event.ActionEvent;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vmm.actions.AbstractActionVMM;
import vmm.actions.ActionList;
import vmm.core.BasicAnimator;
import vmm.core.I18n;
import vmm.core.IntegerParam;
import vmm.core.Parameter;
import vmm.core.RealParamAnimateable;
import vmm.core.View;
import vmm.core3D.Grid3D;
import vmm.core3D.Vector3D;
import vmm.core3D.View3DLit;

/** 
 * Defines a 2-parameter surface family of constant Gaussian curvature K=1 with screw motion symmetry
*/
public class ConstCurvOneHelicoids extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.ConstCurvOneHelicoids.aa","1.2","1.2","1.2");
	private RealParamAnimateable hh = new RealParamAnimateable("vmm.surface.parametric.ConstCurvOneHelicoids.hh","0.2","0","0.7");

	double AA = aa.getValue();
	double HH = hh.getValue()/Math.max(1, Math.abs(hh.getValue()/AA) );
	boolean needsNewArray = true;
	boolean in2ndMorph    = false;
	double r0 = helRad(0);
	double DU;
	int tr;
	Vector3D startPoint = new Vector3D(r0,0,0);
	Vector3D[] TransversCurve;  // This transverse curve is solution of an ODE and is precomputed
	
	
	public ConstCurvOneHelicoids() {
		addParameter(hh);
		addParameter(aa);
		uPatchCount.setValueAndDefault(10);
		vPatchCount.setValueAndDefault(30);
		tr = 1 + uPatchCount.getValue()*6; // 
		umax.reset(findUmax());
		umin.reset(-umax.getValue());
		DU = (umax.getValue()-umin.getValue())/(tr-1);
		vmin.reset("0");
		vmax.reset(2*Math.PI);
		setDefaultViewpoint(new Vector3D(10,-20,10)); 
		setDefaultWindow(-0.8,4.0,-1.8,1.5);
		setDefaultOrientation(View3DLit.NORMAL_ORIENTATION);
	}
	
	/**
	 * Overrides to set needsNewArray
	 */
    public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
	    super.parameterChanged(param, oldValue, newValue);
	   // if ((param == aa)||(param == hh)||(param == uPatchCount)||(param == mp)) // this is not enough
	    needsNewArray = true;
	}
    
	/**
	 * Before createData() executes, constants and array are redone
	 */
    protected void createData() {
    	if (needsNewArray) redoConstantsAndArray();
    	    super.createData();
    }
    
    public void redoConstantsAndArray(){
	    AA = aa.getValue();
	    HH = hh.getValue()/Math.max(1, Math.abs(hh.getValue()/AA) ); // enforce |HH| =< AA
	    umax.reset(findUmax());
	    umin.reset(-umax.getValue());
	    r0 = helRad(0);
	    if (in2ndMorph) vmax.reset(2*Math.PI/r0); else vmax.reset(2*Math.PI);
		tr = 1 + uPatchCount.getValue()*6; // Do not change: Grid3D.subPatchesPerPatch = 6;
		DU = (umax.getValue()-umin.getValue())/(tr-1);
		startPoint = new Vector3D(r0,0,0);
	    TransversCurve = new Vector3D[tr];
	    createCurveArray();
    }
	
    /**
     * We compute the surface transversal to the helices until it becomes singular
     */
	private double findUmax(){
		double a2  = AA*AA;
		double aux = (a2 - 1)/(2*a2);
		double w  = Math.sqrt( Math.abs( Math.sqrt(HH*HH/a2/a2 + aux*aux) + aux ) );
		double result;
		if (in2ndMorph) result = Math.min(0.5, Math.acos(w));
		else  result = Math.acos(w);
		return result;
	}
	
	/**
	 * The functions helRad, dHelRad, nRadial, nZylinder are needed for the ODE of
	 * the transversal curve
	 */
	private double helRad(double u){
		double result = 0;
		double aux = AA*AA*Math.cos(u)*Math.cos(u) - HH*HH;
		if (aux > 0) result = Math.sqrt(aux);
		return result;
	}
	private double dHelRad(double u){
		double result = 0;
		double aux = 2*helRad(u);
		if (aux > 0) result = -AA*AA*Math.sin(2*u)/aux;
		return result;
	}
	
	private Vector3D nRadial(double x, double y){
		double r = Math.sqrt(x*x + y*y);
		Vector3D result = new Vector3D(0,0,0);
		if ( r>0 ) result = new Vector3D(x/r,y/r,0);
		return result;
	}
	private Vector3D nZylinder(double x, double y){
		double r  = (x*x + y*y);
		double RR = Math.sqrt(r + HH*HH);
		Vector3D result = new Vector3D(0,0,0);
		if ( RR>0 ) {
		r = Math.sqrt(r);
		double f  = HH/(r*RR);
		result = new Vector3D(y*f, -x*f, r/RR);
		}
		return result;
	}
	
	public ActionList getAdditionalAnimationsForView(final View view) {
		ActionList actions = super.getAdditionalAnimationsForView(view);
		actions.add(new AbstractActionVMM(I18n.tr("vmm.surface.parametric.ConstCurvOneHelicoids.IsometricMorph")) {
			public void actionPerformed(ActionEvent evt) {
				BasicAnimator animation = new BasicAnimator(); // has to be final for use in the actionPerformed method below.
				animation.setLooping(BasicAnimator.OSCILLATE);  // if not set, the animation will only play once through
				animation.setUseFilmstrip(getUseFilmstripForMorphing());  // respect the setting of useFilmstripForMorphing
				animation.setFrames(getFramesForMorphing());  // Use number of frames as set for morphing this exhibit.
				animation.setMillisecondsPerFrame(200);  // The value used for the standard Morph animation is 100.
				double ra = 0.4;
				double re = 1.5;
				HH = 0.2; AA = re;
				double rr = findUmax();
				animation.addWithCustomValue(hh, HH);
				animation.addWithCustomLimits(aa, ra, re);
				animation.addWithCustomValue(uPatchCount, 6);
				animation.addWithCustomValue(vPatchCount, 36);
				animation.addWithCustomValue(umin, -rr);
				animation.addWithCustomValue(umax, rr);
				animation.addWithCustomValue(vmin, 0);
				animation.addWithCustomValue(vmax, Math.PI);
				animation.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent evt) {
						if (((BasicAnimator)evt.getSource()).isRunning())
							in2ndMorph = true;  // This happens when the animation starts.
						else
							in2ndMorph = false; // This will happen when the animation ends.
					}
				}); 
				view.getDisplay().installAnimation(animation);
			}
		});
		return actions;
	}

	public Vector3D surfacePoint(double u, double v) {
		Vector3D Q = new Vector3D();
		int iu = (int)Math.round((u-umin.getValue())/DU);
		double uATi = umin.getValue()+DU*iu;
		if (u == uATi) 
			Q = TransversCurve[iu];         // array, precomputed in createData    
		else 
			Q = midpointODEstep(uATi,u,TransversCurve[iu],8);
	    double x = Q.x*Math.cos(v) - Q.y*Math.sin(v);
		double y = Q.x*Math.sin(v) + Q.y*Math.cos(v);
		double z = Q.z + HH*v;  
		return new Vector3D(z,-y,x);
	}
	
	/**
	 * This solves the ODE of the transversal curve from tInitial to tFinal
	 */
	protected Vector3D midpointODEstep(double tInitial, double tFinal, Vector3D initialVal, int numSubdivision){
		Vector3D P,T,midP,midT;
		double t = tInitial;
		double aux;
		int newSub = (int)Math.round(1.0+Math.abs(tFinal-tInitial))*numSubdivision;
		double dt = (tFinal-tInitial)/newSub;
		P = new Vector3D(initialVal);
		for (int j = 0; j < newSub ; j++) 
		  {
			aux = dHelRad(t);
			T = nRadial(P.x,P.y);
			T = T.linComb(aux, Math.sqrt(1-aux*aux), nZylinder(P.x,P.y));
			
			midP = P.plus( T.times(dt/2.0) );
			t = t+dt/2.0;
			aux = dHelRad(t);
			midT = nRadial(midP.x,midP.y);
			midT = midT.linComb(aux, Math.sqrt(1-aux*aux), nZylinder(midP.x,midP.y));
			
			P = P.plus(midT.times(dt));
			t=t+dt/2;
		  }
		return new Vector3D(P);
	}
	
	/**
	 * The transversal curve is precomputed and stored in the array  TransversCurve[]
	 */
	private void createCurveArray(){
		double t0 = umin.getValue();
		double t;
		TransversCurve[0] = midpointODEstep(0, t0, startPoint, 32);
		//double dt = (umax.getValue() - t0)/(tr-1);
		for (int i = 1; i < tr ; i++) 
		  {
			t = t0  + (i-1)*DU;
			TransversCurve[i] = midpointODEstep(t, t+DU, TransversCurve[i-1], 8);
		  }
		needsNewArray = false;
	}

}