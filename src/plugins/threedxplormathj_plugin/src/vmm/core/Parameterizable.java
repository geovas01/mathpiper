/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

/**
 * This interface is to be implemented by objects that can have associated
 * Parameters.  A Parameterizable object can be the "owner" of a parameter.
 * When a parameter value is changed, it calls the parameterChanged method
 * in its owner.  In the VMM core, Exhibits and Views are Parameterizable.
 */
public interface Parameterizable {

	/**
	 * This method is called by parameters that are "owned" by this Parameteriazable object
	 * when their values are set.
	 * @param param The Parmeter whose value has been set.
	 * @param oldValue The previous value of the parameter.
	 * @param newValue The new, current value of the parameter.  This is not
	 *    necessarily guaranteed to be different from the old value (although it
	 *    is for parameters definedin the VMM core).
	 */
	public void parameterChanged(Parameter param, Object oldValue, Object newValue);

}
