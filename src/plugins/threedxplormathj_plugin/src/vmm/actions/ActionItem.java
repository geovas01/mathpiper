/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.actions;

import javax.swing.JMenuItem;

/**
 * Represents an item or group of items that can be presented
 * to a user in a menu. The other classes in the vmm.actions
 * package implement this interface.  The menus of the 3DXM
 * application are built from ActionItems.
 */
public interface ActionItem {
	
	/**
	 * Returns an array of one or more menu items representing
	 * this ActionItem.  A value in the returned array can be 
	 * null, which is meant to represent a separator in the men
	 */
	public JMenuItem[] getMenuItems();
	
}
