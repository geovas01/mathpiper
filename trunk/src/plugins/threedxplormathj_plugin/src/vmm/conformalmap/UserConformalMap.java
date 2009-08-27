/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.conformalmap;

import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import vmm.core.Complex;
import vmm.core.ComplexVariableParamAnimateable;
import vmm.core.Display;
import vmm.core.I18n;
import vmm.core.UserExhibit;
import vmm.core.View;
import vmm.core3D.UserExhibit3D;

public class UserConformalMap extends ConformalMap implements UserExhibit3D {
	
	private UserExhibit3D.Support userExhibitSupport;
	private UserExhibit.FunctionInfo func;
	
	public UserConformalMap() {
		umin.reset(-2);
		umax.reset("-0.0001");
		vmin.reset("0.0001");
		vmax.reset("2 * pi");
		ures.reset(7);
		vres.reset(20);
		setDefaultWindow2D(-1.5, 1.5, -1.5, 1.5);
		userExhibitSupport = new UserExhibit3D.Support(this) {
			GridTypeInput gridTypeInput = new GridTypeInput();
			class GridTypeInput extends ExtraPanel {
				JRadioButton cartesianButton, polarButton, polarconformalButton;
				GridTypeInput() {
					super(I18n.tr("vmm.conformalmap.ConformalMap.gridChoice"));
					setLayout(new FlowLayout(FlowLayout.CENTER));
					ButtonGroup group = new ButtonGroup();
					cartesianButton = new JRadioButton(I18n.tr("vmm.conformalmap.ConformalMap.cartesian"),true);
					group.add(cartesianButton);
					add(cartesianButton);
					polarButton = new JRadioButton(I18n.tr("vmm.conformalmap.ConformalMap.polar"),true);
					group.add(polarButton);
					add(polarButton);
					polarconformalButton = new JRadioButton(I18n.tr("vmm.conformalmap.ConformalMap.polarconformal"),true);
					group.add(polarconformalButton);
					add(polarconformalButton);
				}
				public void checkData() throws IllegalArgumentException {
				}
			}
			protected void finish(Dialog dialog, View view, boolean creating) {
				    // I want to use the window from the user dialog for the 2D window only.
				double[] w = dialog.getWindow();
				setDefaultWindow2D(w);
				if (view instanceof ConformalMapView)
					((ConformalMapView)view).setWindowForUseWhileThreeDDisabled(w[0],w[1],w[2],w[3]);
				if (gridTypeInput.cartesianButton.isSelected())
					resetGridType(CARTESIAN);
				else if (gridTypeInput.polarButton.isSelected())
					resetGridType(POLAR);
				else
					resetGridType(POLARCONFORMAL);
			}
			protected Dialog createDialog(Display display, View view, boolean creating) {
				Dialog dialog = super.createDialog(display, view, creating);
				if (!creating) {
					if (getGridType() == CARTESIAN)
						gridTypeInput.cartesianButton.setSelected(true);
					else if (getGridType() == POLAR)
						gridTypeInput.polarButton.setSelected(true);
					else
						gridTypeInput.polarconformalButton.setSelected(true);
				}
				dialog.addExtraPanel(gridTypeInput);
				return dialog;
			}
			
		};
		userExhibitSupport.addFunctionParameter(new ComplexVariableParamAnimateable("a",new Complex(0.66667)));
		userExhibitSupport.addFunctionParameter(new ComplexVariableParamAnimateable("c",new Complex(), new Complex(), new Complex(0.5)));
		func = userExhibitSupport.addComplexFunction("f", "((i*tan(i*z/2)+c)/(1+conj(c)*i*tan(i*z/2)))^a", "z");
		userExhibitSupport.setShowViewpoint(false);
	}

	protected Complex function(Complex argument) {
		return func.complexFunctionValue(argument);
	}

	public UserExhibit.Support getUserExhibitSupport() {
		return userExhibitSupport;
	}

}
