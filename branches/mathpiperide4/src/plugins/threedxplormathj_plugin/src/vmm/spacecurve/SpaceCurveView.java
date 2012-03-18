/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.spacecurve;

import java.awt.event.ActionEvent;

import vmm.actions.ActionList;
import vmm.actions.ToggleAction;
import vmm.core.I18n;
import vmm.core.VMMSave;
import vmm.core3D.View3D;

/**
 * A View class for SpaceCurves.  Adds a {@link #useReverseCollar} property to a generic View3D.
 */
public class SpaceCurveView extends View3D {

	/**
	 * Used to determine whether the SpaceCurve exhibit in this View should be drawn with "revesed collars", 
	 * which give the curve a thick and more three-dimensional appearance.
	 */
	@VMMSave protected boolean useReverseCollar;
	
	/**
	 * A menu command for controlling the value of {@link #useReverseCollar}.  This is created in the 
	 * {@link #getActions()} method.
	 */
	protected ToggleAction reverseCollarToggle = new ToggleAction(I18n.tr("vmm.spacecurve.SpaceCurve.UseReverseCollar")) {
		public void actionPerformed(ActionEvent evt) { 
			setUseReverseCollar( getState() );
		}
	};

	/**
	 * Get the current setting of the useReverseCollar property.
	 * @see #setUseReverseCollar(boolean)
	 */
	public boolean getUseReverseCollar() {
		return useReverseCollar;
	}

	/**
	 * Sets the value of the useReverseCollar property.  When this property is true, the curve is drawn
	 * as a thick curve (or collar) in the foreground color, with a thin curve in the background color running
	 * down the middle.  This gives a nice 3D appearance.  When useReverseCollar is false, a one-pixel-wide
	 * curve is drawn.  The default value of this property is false.
	 */
	public void setUseReverseCollar(boolean useReverseCollar) {
		if (this.useReverseCollar != useReverseCollar) {
			this.useReverseCollar = useReverseCollar;
			reverseCollarToggle.setState(useReverseCollar);
			forceRedraw();
		}
	}

	/**
	 * Returns a list of actions for this view.  This method is overridden to add a ToggleAction for controlling
	 * the value of the useReverseCollar property.
	 * @see #setUseReverseCollar(boolean)
	 */
	public ActionList getActions() {
		ActionList actions = super.getActions();
		actions.add(reverseCollarToggle);
		return actions;
	}

}
