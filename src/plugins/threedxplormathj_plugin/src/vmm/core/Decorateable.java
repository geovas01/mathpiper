/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

/**
 * A simple interface implemented by objects that to which objects of type {@link vmm.core.Decoration} can be added.
 * This interface is implemented by the {@link vmm.core.Exhibit} class and the {@link vmm.core.View} class.  
 * It is not likely to find other uses.
 */
public interface Decorateable {
	
	/**
	 * Add a decoration to this object.
	 * @param dec The decoration to be added.  If null, nothing is done.
	 */
	public void addDecoration(Decoration dec);
	
	/**
	 * Remove a decoration from this object.
	 * @param dec The decoration to be removed (if present).
	 */
	public void removeDecoration(Decoration dec);

}
