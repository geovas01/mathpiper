/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

import java.awt.Color;

/**
 * @author dick
 * A DirectionalLight represents a source of light that shines on the Exhibit and 
 * that is located on the "Sphere at infinity". It has a Color and a direction 
 * (a unit vector). All light rays from the source travel parallel to this direction.
 */

public class DirectionalLight {
	
private Color itsColor;        // Color of the light.
private Vector3D itsDirection; // direction from which light is shining. Should be a unit vector.

public DirectionalLight(Color theColor, Vector3D theDirection) {
	setItsColor(theColor);
	setItsDirection(theDirection);
   }

public DirectionalLight(
		   float R,float G,float B,      //RGB values of  itsColor
		   double x, double y, double z  //components of itsDirection
		               ) {
	    Color theColor  = new Color(R,G,B);
	    Vector3D theDirection = new Vector3D(x,y,z);
	    setItsColor(theColor);
	    setItsDirection(theDirection);
   }

public Color getItsColor() {
	return itsColor;
}

public void setItsColor(Color itsColor) {
	this.itsColor = itsColor;
}

public Vector3D getItsDirection() {
	return itsDirection;
}

public void setItsDirection(Vector3D itsDirection) {
	if (itsDirection.norm() == 0) 
		  this.itsDirection = new Vector3D(-1.0,0.0,0.0);
	else 
		  this.itsDirection = itsDirection.normalized();
}
	
}
