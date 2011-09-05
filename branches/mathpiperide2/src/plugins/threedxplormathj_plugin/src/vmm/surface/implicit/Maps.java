/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.implicit;

import vmm.core3D.Vector3D;

/**
* This class defines several important and related linear maps of R^3 to itself as static methods; namely projection 
*  P on  a one-dimemsional subspace, L, reflection R in L, and the transvection T defined by a pair of unit vectors.
*  In what follows axis denotes a unit vector of R^3 and L the subspace it spans. The projection P onto the line
*  L spanned by axis is the linear map (v ---> <v,axis> axis). where < . > denotes the   dot product. 
*  We note that it has L as its +1 eigenspace and the orthogonal complement L' of L is its 0 eigenspace. Since 
*  reflection in L has L as its +1 eigenspace and L' as its -1 eigenspace, it is clear that R = 2P -I where 
* I is the identity map.  The transvection T defined by unit vectors e1 and e2 is defined whenever e2 is 
* not equal to plus or minus e1, so that e1 and e2 are a basis for a two-dimensional subspace V. Then T 
* is defined to be the unique rotation that is the identity on V', the orthogonal complement of V, and 
* on V is the unique rotation of V carrying e1 into e2.  It is easy to see that T is just the product of 
* two reflections. Namely, if m denotes the midpoint of e1 and e2 on the unit sphere S^2 (obtained by 
* normalizing e1 + e2) then T is reflection in m followed by reflection in e2, or equally well it is the
* reflection in e1 followed by reflection in m. For convenience, each of these maps is provided in two forms, 
* as a Vector3D valued function and as void function returning its image as one of the arguments.
 */
public class Maps {
	
	static double dotProd(Vector3D v, Vector3D w){
		//System.out.println(v.x*w.x + v.y*w.y + v.z*w.z);
		return (v.x*w.x + v.y*w.y + v.z*w.z);
	}
	
/**
 * destination is the projection of a Vector3D, source, onto line spanned by the unit vector, axis.
 */	
	static public void ProjectOnAxis(Vector3D axis, Vector3D source, Vector3D destination) {
		  // destination = axis.times(axis.dot(source));
		 destination = axis.times(dotProd(axis,source));
		  //System.out.println(axis.dot(axis));
		  //System.out.println(axis.x);System.out.println(axis.y);System.out.println(axis.z);
		  //System.out.println();
	 }
	
/**
* returns the projection of a Vector3D, source, onto line spanned by the unit vector, axis.
*/	
	static public Vector3D ProjectionOnAxis(Vector3D axis, Vector3D source) {
		  //return axis.times(axis.dot(source));
		//System.out.println(axis.dot(source));
		   return axis.times(dotProd(axis,source));
	    }
	
	/**
	 * destination is the reflection of a Vector3D, source, in the line spanned by the unit vector, axis.
	 */	
	static public void ReflectInAxis(Vector3D axis, Vector3D source, Vector3D destination) {
		  //destination = axis.times(2*axis.dot(source)).minus(source);
		destination = axis.times(2*dotProd(axis,source)).minus(source);
		//  System.out.println(axis.dot(source));
	  }
	
	/**
	 * returns the reflection of a Vector3D, source, in the line spanned by the unit vector, axis.
	 */	
	static public Vector3D ReflectionInAxis(Vector3D axis, Vector3D source) {
		  // return axis.times(2*axis.dot(source)).minus(source);
		//System.out.println(axis.times(2*dotProd(axis,source)).minus(source).norm());
		return axis.times(2*dotProd(axis,source)).minus(source);
	  }
	    
	/**
	*  e1 and e2 should be linearly independent unit vectors. In the plane V spanned by e1 and e2, Transvect 
	*  is the rotation carrying e1 to e2, and it is the identity orthogonal to V.
	*/
	static public void Transvect(Vector3D e1, Vector3D e2, Vector3D source, Vector3D destination) {
			Vector3D midpoint = new Vector3D( (e1.plus(e2)).normalized() );
			ReflectInAxis(midpoint,ReflectionInAxis(e1,source),destination);
			//System.out.println(dotProd(destination,destination));
			  //System.out.println(dotProd(e2,e2));
			// System.out.println(e2.x); System.out.println(e2.y);System.out.println(e2.z);System.out.println();
	  }
	
	/**
	*  e1 and e2 should be linearly independent unit vectors. If V is the plane spanned by e1 and e2, Transvection 
	*  returns the rotation applied to source that carries e1 to e2, and is the identity orthogonal to V.
	*/
	static public Vector3D Transvection(Vector3D e1, Vector3D e2, Vector3D source) {
		    Vector3D midpoint = new Vector3D( (e1.plus(e2)).normalized() );
		    //System.out.println(ReflectionInAxis(midpoint,ReflectionInAxis(e1,source)).norm());
		    return ReflectionInAxis(midpoint,ReflectionInAxis(e1,source));
   }

}
