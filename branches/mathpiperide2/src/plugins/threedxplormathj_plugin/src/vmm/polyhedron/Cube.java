/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.polyhedron;

import vmm.core3D.Vector3D;

public class Cube extends RegularPolyhedron {
	
	public Cube()  {
		polyhedronVertices = new Vector3D[] {
				new Vector3D(1,1,1),
				new Vector3D(1,1,-1),
				new Vector3D(1,-1,-1),
				new Vector3D(1,-1,1),
				new Vector3D(-1,1,1),
				new Vector3D(-1,1,-1),
				new Vector3D(-1,-1,-1),
				new Vector3D(-1,-1,1)
		};
		polyhedronFaces = new int[][] {
				{ 0, 1, 2, 3 },
				{ 0, 3, 7, 4 },
				{ 0, 4, 5, 1 },
				{ 6, 2, 1, 5 },
				{ 6, 5, 4, 7 },
				{ 6, 7, 3, 2 }
		};
		stellationScale = 0.8;
		stellationHeight = 1;
	}
	
}
