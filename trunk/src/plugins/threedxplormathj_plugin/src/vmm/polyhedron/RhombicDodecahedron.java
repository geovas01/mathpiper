/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.polyhedron;

import vmm.core3D.Vector3D;

public class RhombicDodecahedron extends RegularPolyhedron {

	public RhombicDodecahedron() {
		polyhedronVertices = new Vector3D[] {
				new Vector3D(0,2,0),
				new Vector3D(-1,-1,-1),
				new Vector3D(1,-1,-1),
				new Vector3D(1,1,-1),
				new Vector3D(-1,1,-1),
				new Vector3D(-1,-1,1),
				new Vector3D(1,-1,1),
				new Vector3D(1,1,1),
				new Vector3D(-1,1,1),
				new Vector3D(0,0,2),
				new Vector3D(0,0,-2),
				new Vector3D(-2,0,0),
				new Vector3D(2,0,0),
				new Vector3D(0,-2,0)
		};
		polyhedronFaces = new int[][] {
				{ 1, 13, 2, 10 },
				{ 2, 12, 3, 10 },
				{ 3, 0, 4, 10 },
				{ 4, 11, 1, 10 },
				{ 2, 13, 6, 12 },
				{ 3, 12, 7, 0 },
				{ 4, 0, 8, 11 },
				{ 1, 11, 5, 13 },
				{ 5,9, 6, 13 },
				{ 6, 9, 7, 12 },
				{ 7, 9, 8, 0 },
				{ 8, 9, 5, 11 }
		};
		setDefaultViewpoint(new Vector3D(0,14,9));
		setDefaultWindow(-2.2,2.2,-2.2,2.2);
		stellationScale=0.7;
		stellationHeight = 1.414213562373095;
//		Vector3D c = polyhedronVertices[1].plus(polyhedronVertices[13]).plus(polyhedronVertices[2]).plus(polyhedronVertices[10]).times(1.0/4.0);  // center of face
//		Vector3D y = polyhedronVertices[1].minus(polyhedronVertices[11]);
//		Vector3D x = c.minus(polyhedronVertices[1]); 
//		double h = x.norm()*x.norm()*y.norm()/x.dot(y);
//		stellationHeight = Math.sqrt(h*h-x.norm()*x.norm());
//		System.out.println(stellationHeight);
	}
	
}
