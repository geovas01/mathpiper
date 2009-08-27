/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.actions;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

import vmm.core.Util;

/**
 * A convenience class for making an Action with an accelerator key.
 */
public abstract class AbstractActionVMM extends AbstractAction implements ActionItem {
	
	/**
	 * Create an AbstractActionVMM with a specified name, no icon, and no
	 * accelerator.
	 * @param name The name of the Action.  (This should be internationalized 
	 * by the caller.)
	 */
	public AbstractActionVMM(String name) {
		super(name);
	}

	/**
	 * Create an AbstractActionVMM with a specified name and icon, and with no
	 * accelerator.
	 * @param name The name of the Action.  (This should be internationalized 
	 * by the caller.)
	 */
	public AbstractActionVMM(String name, Icon icon) {
		super(name,icon);
	}

	/**
	 * Create an AbstractActionVMM with a specified name, icon, and 
	 * accelerator.
	 * @param name The name of the Action.  (This should be internationalized 
	 * by the caller.)
	 * @param accelerator A String that describes the accelerator, as for
	 * the method KeyStroke.getKeyStroke(String), except that the main
	 * command key (ctrl or meta) should NOT be included in the description
	 * String.  If the value of this parameter is <code>null</code>, no accelerator is added.  
	 * On Macs, "meta" is added to the string; otherwise,
	 * "ctrl: is added; this gives the appropriate modifier for tha
	 * platform.
	 */
	public AbstractActionVMM(String name, String accelerator) {
		super(name);
		if (accelerator != null)
			putValue(Action.ACCELERATOR_KEY, Util.getAccelerator(accelerator));
	}

	/**
	 * Returns an array containing one item, a JMenuItem created from this AbstractAction.
	 */
	public JMenuItem[] getMenuItems() {
		return new JMenuItem[] { new JMenuItem(this) };
	}
	
	

}
