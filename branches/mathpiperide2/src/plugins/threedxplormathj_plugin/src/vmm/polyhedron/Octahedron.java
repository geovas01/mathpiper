/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.polyhedron;

import vmm.core3D.Vector3D;

public class Octahedron extends RegularPolyhedron {

	public Octahedron() {
		polyhedronVertices = new Vector3D[] {
				new Vector3D(-1,0,0),
				new Vector3D(0,0,-1),
				new Vector3D(0,0,1),
				new Vector3D(0,-1,0),
				new Vector3D(0,1,0),
				new Vector3D(1,0,0),
		};
		polyhedronFaces = new int[][] {
				{ 4, 5, 2 },
				{ 0, 4, 2 },
				{ 0, 2, 3 },
				{ 5, 3, 2 },
				{ 5, 4, 1 },
				{ 4, 0, 1 },
				{ 0, 3, 1 },
				{ 3, 5, 1 },
		};
		setDefaultWindow(-1.25,1.25,-1.25,1.25);
		stellationScale = 0.6;
		stellationHeight = 1.1547005383792517;  // computed as follows
//		Vector3D c = polyhedronVertices[0].plus(polyhedronVertices[2]).plus(polyhedronVertices[3]).times(1.0/3.0);  // center of face
//		Vector3D y = (polyhedronVertices[3].plus(polyhedronVertices[0]).times(0.5)).minus(polyhedronVertices[1]);
//		Vector3D x = c.minus(polyhedronVertices[3].plus(polyhedronVertices[0]).times(0.5)); 
//		double h = x.norm()*x.norm()*y.norm()/x.dot(y);
//		stellationHeight = Math.sqrt(h*h-x.norm()*x.norm());
//		System.out.println(stellationHeight);
	}
}
