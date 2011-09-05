/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 
package vmm.fractals;


import vmm.actions.ActionList;
import vmm.actions.ActionRadioGroup;
import vmm.core.Complex;
import vmm.core.I18n;
import vmm.core.VMMSave;
import vmm.core.View;


/**
 * The square-filling Hilbert curve.
 */
public class Hilbert extends RepeatedSegmentFractal {
	
	@VMMSave
	private int segmentChoice = 2;  // A property with values 1-4 that determines the shape
	                                // of the repeated segments from which the curve is built.
	
	private ActionRadioGroup segmentChoiceSelect;  // ActionGroup for controlling the value
	                                               // of the segmentChoice property.

	public Hilbert() {
		fractality.reset(0.5,0.25,0.5);
		recursionLevel.reset(8);
		setDefaultWindow(0,1,-0.1,1.1);
		colorRepeatFactor = 4;
		fastDrawRecursionLevel = 5;
		segmentChoiceSelect = new ActionRadioGroup() {
			public void optionSelected(int selectedIndex) {
				setSegmentChoice(selectedIndex + 1);
			}
		};
		segmentChoiceSelect.addItem("1");
		segmentChoiceSelect.addItem("2");
		segmentChoiceSelect.addItem("3");
		segmentChoiceSelect.addItem("4");
		segmentChoiceSelect.setSelectedIndex(segmentChoice-1);
	}

	
	/**
	 * Returns the value of the segmentChoice property.
	 * @see #setSegmentChoice(int)
	 */
	public int getSegmentChoice() {
		return segmentChoice;
	}


	/**
	 * Sets the value of the segmentChoice property.  Legal values are 1 to 4.  This property
	 * determines the shape of the segment that is repeated to make an approximation of the
	 * fractal curve.
	 * @param segmentChoice The new value of the segmentChoice property.  Should be in the
	 *    range 1 to 4; otehr values will be silently ignored.
	 */
	public void setSegmentChoice(int segmentChoice) {
		if (this.segmentChoice != segmentChoice && segmentChoice >= 1 && segmentChoice <= 4) {
			this.segmentChoice = segmentChoice;
			segmentChoiceSelect.setSelectedIndex(segmentChoice-1);
			forceRedraw();
		}
	}


	/**
	 * Adds a submenu that contains commands for selecting the value of the
	 * segmentChoice property.
	 */
	public ActionList getActionsForView(View view) {
		ActionList actions = super.getActionsForView(view);
		ActionList submenu = new ActionList(I18n.tr("vmm.fractals.Hilbert.segmentchoice"));
		submenu.add(segmentChoiceSelect);
		actions.add(null);
		actions.add(submenu);
		return actions;
	}



	protected Complex[] computeNextLevel(Complex[] hilbertCurve, int computedLevel) {
		Complex[] newHilbertCurve;
		double a = Math.min(0.5, Math.max(fractality.getValue(), 0.0));
		double r = 0.5 - a;
		double s = Math.sqrt(a-0.25);
		Complex c = new Complex(r,s);
		Complex ca = new Complex(r+2*a,s);
		if (computedLevel == 0) {
			newHilbertCurve = computeHilbertDecoration(); // initialization
		}
		else {       // Hilbert iteration: 
			newHilbertCurve = new Complex[ 4*hilbertCurve.length]; 
			int hl = hilbertCurve.length;
			for (int i = 0; i < hl ; i++) {
				Complex p1 = hilbertCurve[i];
				newHilbertCurve[i] = p1.conj().times(c);
				newHilbertCurve[i+hl] = p1.times(a).plus(c);
				newHilbertCurve[i+2*hl] = newHilbertCurve[i+hl].plus(a);
				newHilbertCurve[i+3*hl] = p1.conj().times(c.conj()).plus(ca);
			}
		}
		return newHilbertCurve;
	}

	/**
	 * Computes the base level segment that is used at recursion level 1.
	 */
	private Complex[] computeHilbertDecoration() {
		Complex[] hilbertDecoration;
		if (segmentChoice == 1){
			hilbertDecoration = new Complex[6];
			hilbertDecoration[0] = new Complex(0.02, 0.02);
			hilbertDecoration[1] = new Complex(0.25, 0.25);
			hilbertDecoration[2] = new Complex(0.25, 0.75);
			hilbertDecoration[3] = new Complex(0.75, 0.75);
			hilbertDecoration[4] = new Complex(0.75, 0.25);
			hilbertDecoration[5] = new Complex(0.98, 0.02);
		}
		else if (segmentChoice == 2){ 
			hilbertDecoration = new Complex[4];
			hilbertDecoration[0] = new Complex(0.25, 0.25);
			hilbertDecoration[1] = new Complex(0.25, 0.75);
			hilbertDecoration[2] = new Complex(0.75, 0.75);
			hilbertDecoration[3] = new Complex(0.75, 0.25);
		}
		else if (segmentChoice == 3){
			hilbertDecoration = new Complex[2];
			hilbertDecoration[0] = new Complex(0.02, 0.02);
			hilbertDecoration[1] = new Complex(0.98, 0.02);
		}
		else {
			hilbertDecoration = new Complex[6];
			hilbertDecoration[0] = new Complex(0.02, 0.02);
			hilbertDecoration[1] = new Complex(0.10, 0.05);
			hilbertDecoration[2] = new Complex(0.25, 0.5);
			hilbertDecoration[3] = new Complex(0.50, 0.15);
			hilbertDecoration[4] = new Complex(0.75, 0.25);
			hilbertDecoration[5] = new Complex(0.98, 0.02);
		}
		return hilbertDecoration;
	}



}