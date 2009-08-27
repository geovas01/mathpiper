/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.polyhedron;

import vmm.core3D.Vector3D;

public class Rhombohedron extends RegularPolyhedron {
	
	public Rhombohedron() {
		polyhedronVertices = new Vector3D[] {
				new Vector3D( 3-1.5, 1-1.5, 1-1.5 ),
				new Vector3D( 1-1.5, 1-1.5, 1-1.5 ),
				new Vector3D( 0-1.5, 2-1.5, 2-1.5 ),
				new Vector3D( 1-1.5, 3-1.5, 1-1.5 ),
				new Vector3D( 2-1.5, 2-1.5, 0-1.5 ),
				new Vector3D( 2-1.5, 0-1.5, 2-1.5 ),
				new Vector3D( 1-1.5, 1-1.5, 3-1.5 ),
				new Vector3D( 2-1.5, 2-1.5, 2-1.5 )
		};
		polyhedronFaces = new int[][] {
				{ 6, 7, 0, 5 },
				{ 4, 3, 2, 1 },
				{ 0, 7, 3, 4 },
				{ 1, 2, 6, 5 },
				{ 5, 0, 4, 1 },
				{ 2, 3, 7, 6 }
		};
		setDefaultViewpoint(new Vector3D(0,-14,9));
		useBackFaceFudge = true;
		stellationScale = 0.8;
		stellationHeight = 1.414213562373095;
//		Vector3D c = polyhedronVertices[1].plus(polyhedronVertices[2]).plus(polyhedronVertices[3]).plus(polyhedronVertices[4]).times(1.0/4.0);  // center of face
//		Vector3D y = polyhedronVertices[1].minus(polyhedronVertices[5]);  // edge adjacent to face
//		Vector3D x = c.minus(polyhedronVertices[1]);  // center of face - vertex #1
//		double h = x.norm()*x.norm()*y.norm()/x.dot(y);
//		stellationHeight = Math.sqrt(h*h-x.norm()*x.norm());
//		System.out.println(stellationHeight);
	}
	
}

