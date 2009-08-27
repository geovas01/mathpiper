/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 
package vmm.fractals;

import java.awt.event.ActionEvent;

import vmm.actions.ActionList;
import vmm.actions.ToggleAction;
import vmm.core.Complex;
import vmm.core.I18n;
import vmm.core.VMMSave;
import vmm.core.View;

/**
 * The fractal "Sierpinski" curve.
 */
public class Sierpinski extends RepeatedSegmentFractal {
    
	Complex[] helpSierpinskiCurve;
/**
 * The most interesting curve to draw is a family of injective curves. They converge to
 * the Sierpinski curve whose range is the Sierpinski triangle. However, the iteration is
 * not defined in terms of this injective curve, but in terms of a non-injective curve
 * with twice as many points. Therefore the iteration cannot be written as for the other
 * fractal curves. We use a third complex array, the above helpSierpinskiCurve.
 */

	@VMMSave
	private boolean useInjectiveCurves = true;  // Determines whether the
	                                // injective Sierpinski approximation is drawn or the
								    // or the non-injective one used for the iteration.
	
	private ToggleAction segmentChoiceToggle;  //Ror controlling the value
	                                               // of the segmentChoice property.

	
	public Sierpinski() {
		fractality.reset(0.5, 1.0/3.0, 0.5);
		fractality.setMaximumValueForInput(0.5);
		fractality.setMinimumValueForInput(1.0/3.0);
		recursionLevel.reset(9);
		recursionLevel.setMaximumValueForInput(11);
		recursionLevel.setMinimumValueForInput(1);
		setDefaultWindow(-1,1,-0.75,1.25);
		colorRepeatFactor = 2;
		fastDrawRecursionLevel = 6;
		
		segmentChoiceToggle = new ToggleAction(I18n.tr("vmm.fractals.Sierpinski.segmentchoice"), true) {
			public void actionPerformed(ActionEvent evt) {
				setUseInjectiveCurves(getState());
			}
		};
	}
	
	/**
	 * Returns the value of the useInjectiveCurves property.
	 */
	public boolean getUseInjectiveCurve() {
		return useInjectiveCurves;
	}


	/**
	 * Sets the value of the setUseInjectiveCurves property.   This property
	 * determines which of two approximations of the Sierpinski curve is drawn.
	 * @param useInjectiveCurves If set to true, then an injective approximation is used for the
	 *   Sierpinski curve.  Otherwise, a non-injective approximation is used.
	 */
	public void setUseInjectiveCurves(boolean useInjectiveCurves) {
		if (this.useInjectiveCurves == useInjectiveCurves)
			return;
		this.useInjectiveCurves = useInjectiveCurves;
		segmentChoiceToggle.setState(useInjectiveCurves);
		forceRedraw();
	}

	/**
	 * Adds a submenu that contains commands for selecting the value of the
	 * segmentChoice property.
	 */
	public ActionList getActionsForView(View view) {
		ActionList actions = super.getActionsForView(view);
		actions.add(null);
		actions.add(segmentChoiceToggle);
		return actions;
	}
	
	
	/**
	 * returns true if u-->v-->w turns to the left
	 */
	private boolean turnsLeft(Complex u, Complex v, Complex w) {
		return (-(v.im-u.im)*(w.re-v.re) + (v.re-u.re)*(w.im-v.im) > 0);
	}
	
	protected Complex[] computeNextLevel(Complex[] sierpinskiCurve, int computedLevel) {
		boolean sflag;
		int j,ll;
		double bb = fractality.getValue();
		double bi = Math.sqrt(0.5*bb*(1+1.5*bb) - 0.25);
		Complex c1 = new Complex((1+bb)/2, -bi);
		Complex c2 = new Complex((1-bb)/2, +bi);
		Complex[] newSierpinskiCurve;
		if (computedLevel == 0) { 
			double aa = 0.5; // can be a parameter
			Complex u = new Complex(-1, -1/Math.sqrt(3));
			Complex v = new Complex(+1, -1/Math.sqrt(3));
			Complex w = new Complex( 0, 2/Math.sqrt(3));
			
			helpSierpinskiCurve = new Complex[7];
			helpSierpinskiCurve[0] = new Complex(u.realLinComb(aa,aa,v));
			helpSierpinskiCurve[1] = new Complex( v);
			helpSierpinskiCurve[2] = new Complex(v.realLinComb(aa,aa,w));
			helpSierpinskiCurve[3] = new Complex( w);
			helpSierpinskiCurve[4] = new Complex(w.realLinComb(aa,aa,u));
			helpSierpinskiCurve[5] = new Complex( u);
			helpSierpinskiCurve[6] = new Complex(u.realLinComb(aa,aa,v));
			
			newSierpinskiCurve = new Complex[4];
			newSierpinskiCurve[0] = new Complex(helpSierpinskiCurve[0]);
			newSierpinskiCurve[1] = new Complex(helpSierpinskiCurve[2]);
			newSierpinskiCurve[2] = new Complex(helpSierpinskiCurve[4]);
			newSierpinskiCurve[3] = new Complex(helpSierpinskiCurve[6]);
		}
		else {
			ll = helpSierpinskiCurve.length;
			newSierpinskiCurve = new Complex[ll];
			for (int k=0; k<ll; k++) {
				newSierpinskiCurve[k] = helpSierpinskiCurve[k];
			}
			helpSierpinskiCurve = new Complex[3*helpSierpinskiCurve.length-2];
			j= 0;
			helpSierpinskiCurve[j] = newSierpinskiCurve[j];
			for (int i=0; i < ll-1; i = i+2) 
			{
		    sflag = turnsLeft(newSierpinskiCurve[i],newSierpinskiCurve[i+1],newSierpinskiCurve[i+2]);	
		    j= j+1;
	        if (bb == 1.0/3)             // sflag is undefined if bb = 1/3
	        	helpSierpinskiCurve[j] = new Complex(newSierpinskiCurve[i].
	                                  realLinComb((1+bb)/2, (1-bb)/2,newSierpinskiCurve[i+1]));
	        else { if (sflag)
	        	helpSierpinskiCurve[j] = new Complex(newSierpinskiCurve[i].
                                      complexLinComb(c1, c2,newSierpinskiCurve[i+1]));
	        else
	        	helpSierpinskiCurve[j] = new Complex(newSierpinskiCurve[i].
                                      complexLinComb(c1.conj(), c2.conj(),newSierpinskiCurve[i+1]));
	             }
	        j= j+1;
	        helpSierpinskiCurve[j] = new Complex(newSierpinskiCurve[i].
                                     realLinComb(bb, (1-bb),newSierpinskiCurve[i+1]));
	        j= j+1;
	        helpSierpinskiCurve[j] = new Complex(newSierpinskiCurve[i+1]);
	        j= j+1;
	        helpSierpinskiCurve[j] = new Complex(newSierpinskiCurve[i+1].
                                     realLinComb((1-bb), bb,newSierpinskiCurve[i+2]));
            j= j+1;
            if (bb == 1.0/3)             // sflag is undefined if bb = 1/3
	        	helpSierpinskiCurve[j] = new Complex(newSierpinskiCurve[i+1].
	                                  realLinComb((1-bb)/2, (1+bb)/2,newSierpinskiCurve[i+2]));
	        else { if (sflag)
	        	helpSierpinskiCurve[j] = new Complex(newSierpinskiCurve[i+1].
                                      complexLinComb(c2.conj(), c1.conj(),newSierpinskiCurve[i+2]));
	        else
	        	helpSierpinskiCurve[j] = new Complex(newSierpinskiCurve[i+1].
                                      complexLinComb(c2, c1,newSierpinskiCurve[i+2]));
	             }
	        j= j+1;
	        helpSierpinskiCurve[j] = new Complex(newSierpinskiCurve[i+2]);
			}
			
			     newSierpinskiCurve = new Complex[ ((helpSierpinskiCurve.length+1)/2) ];
			     for (int k=0; k < newSierpinskiCurve.length; k++)   {
				   newSierpinskiCurve[k] = helpSierpinskiCurve[2*k]; }
		}
		if (!getUseInjectiveCurve())
			return helpSierpinskiCurve;
		else
			return newSierpinskiCurve;
	}
	

}