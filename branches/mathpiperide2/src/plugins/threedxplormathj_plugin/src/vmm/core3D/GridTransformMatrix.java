/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

/**
 * Represents an affine transform on real 3-space as a matrix;
 * meant for use with {@link vmm.core3D.Grid3D} to represent transformations
 * applied to a basic grid to produce additional segments of a complete surface.
 */
public class GridTransformMatrix {
	
	private double[][] matrix;
	private boolean reverseSurfaceOrientation;

	/**
	 * Constructs an identity matrix.
	 */
	public GridTransformMatrix() {
		matrix = new double[3][4];
		matrix[0][0] = matrix[1][1] = matrix[2][2] = 1;
		reverseSurfaceOrientation = false;
	}
		
	/**
	 * Copy constructor.
	 * @param m The TransformMatrix object to be copied.  If m is null, an identity matrix is constructed.
	 */
	public GridTransformMatrix(GridTransformMatrix m) {
		if (m == null) {
			matrix = new double[3][4];
			matrix[0][0] = matrix[1][1] = matrix[2][2] = 1;
			reverseSurfaceOrientation = false;
		}
		else {
			matrix = new double[3][4];//matrix = m.matrix.clone();
			for (int i = 0; i < 3; i++){
				for (int j = 0; j < 4; j++){
					matrix[i][j] = m.matrix[i][j];
				}
			}
		   this.reverseSurfaceOrientation = m.reverseSurfaceOrientation; // is this a good way to start? HK
		}
	}
	
	/**
	 * Explicit constructor, giving the entries in the transformation matrix.
	 * @param or tells whether the transformation should be orientation-reversing.
	 */
	
	public static GridTransformMatrix SetGridTransformMatrix(double a0, double a1, double a2, double a3,
		double b0, double b1, double b2, double b3, double c0, double c1, double c2, double c3, boolean or){
		GridTransformMatrix m = new GridTransformMatrix();
		m.matrix[0][0] = a0; m.matrix[0][1] = a1; m.matrix[0][2] = a2; m.matrix[0][3] = a3;
		m.matrix[1][0] = b0; m.matrix[1][1] = b1; m.matrix[1][2] = b2; m.matrix[1][3] = b3;
		m.matrix[2][0] = c0; m.matrix[2][1] = c1; m.matrix[2][2] = c2; m.matrix[2][3] = c3;
		m.reverseSurfaceOrientation = or;
		return m;
	}
	
	/**
	 * Tests whether a given object is equal to this TransformMatrix.  If the object is null
	 * or is not a TransformMatrix, the return value is false.  Otherwise, the return value
	 * is true if and only if the two transforms have the same matrices.
	 *  ===> How about the orientation?                                   <== HK
	 */
	public boolean equals(Object o) {
		try {
			double[][] m = ((GridTransformMatrix)o).matrix;
			for (int r = 0; r < 3; r++)
				for (int c = 0; c < 4; c++)
					if (m[r][c] != matrix[r][c])
						return false;
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Applies the transform to a vector.  To apply the transform to (x,y,z), the column vector (x,y,z,1) is
	 * mulitplied on the left by a 4-by-4 matrix, producing a vector (a,b,c,1).  The return value is
	 * the vector (a,b,c).
	 * @param vec The non-null vector that is to be transformed.  This vector is not modified.
	 * @return A new Vector3D object containing the result of applying the transform to vec.
	 */
	public Vector3D apply(Vector3D vec) {
		return new Vector3D(
			matrix[0][0]*vec.x + matrix[0][1]*vec.y + matrix[0][2]*vec.z + matrix[0][3],
			matrix[1][0]*vec.x + matrix[1][1]*vec.y + matrix[1][2]*vec.z + matrix[1][3],
			matrix[2][0]*vec.x + matrix[2][1]*vec.y + matrix[2][2]*vec.z + matrix[2][3]
		);
	}

	/**
	 * Apply the transformation to a surface normal.  If the reverseSurfaceOrientation property
	 * is true, the resulting normal is multiplied by -1.
	 * @param N The non-null normal vector that is to be transformed.  This vector is not modified.
	 * @return A new Vector3D object containing the result of applying the transform to N.
	 */
	public Vector3D applyToNormal(Vector3D N) {
		Vector3D aux = new Vector3D(
		matrix[0][0]*N.x + matrix[0][1]*N.y + matrix[0][2]*N.z,
		matrix[1][0]*N.x + matrix[1][1]*N.y + matrix[1][2]*N.z,
		matrix[2][0]*N.x + matrix[2][1]*N.y + matrix[2][2]*N.z) ; // Don't apply tranlation to N
		N.assign(aux);
		//N = apply(N);  // TODO: Fix this -- it's not correct (except maybe for orthogonal transforms).
		if (reverseSurfaceOrientation)
			N.negate();
		return N;
	}

	/**
	 * Gets the value of the reverseSurfaceOrientation property.
	 * @see #setReverseSurfaceOrientation(boolean)
	 */
	public boolean getReverseSurfaceOrientation() {
		return reverseSurfaceOrientation;
	}

	/**
	 * Sets the value of the reverseSurfaceOrientation property.  The default value is false.
	 * This property affects the result of applying the transformation to a surface normal vector
	 * using the {@link #applyToNormal(Vector3D)} method.  If the value of this property is
	 * true, then the transformed vector is negated.
	 */
	public GridTransformMatrix setReverseSurfaceOrientation(boolean reverseSurfaceOrientation) {
		this.reverseSurfaceOrientation = reverseSurfaceOrientation;
		return this;
	}
	
	/**
	* Reverses the value of the reverseSurfaceOrientation property.
	* Calling this method is equivalent to calling
	* setReverseSurfaceOrientation( ! getReverseSurfaceOrientation )
	* @return A reference to this TransformMatrix -- returned for convenience to allow chaining of transformations.
	*/
	public GridTransformMatrix reverse() {
	reverseSurfaceOrientation = ! reverseSurfaceOrientation;
	return this;
	}

	/**
	 * Applies a scaling operation to this TransformMatrix, by multiplying it on the LEFT by
	 * a scaling matrix.  This TransformMatrix is modified in place.
	 * @param s  The (uniform) scale factor.
	 * @return A reference to this TransformMatrix -- returned for convenience to allow chaining of transformations.
	 */
	public GridTransformMatrix scale(double s) {
		return scale(s,s,s);
	}

	/**
	 * Applies a scaling operation to this TransformMatrix, by multiplying it on the LEFT by
	 * a scaling matrix.  This TransformMatrix is modified in place.
	 * @param sx The scaling in the x direction.
	 * @param sy The scaling in the y direction.
	 * @param sz The scaling in the z direction.
	 * @return A reference to this TransformMatrix -- returned for convenience to allow chaining of transformations.
	 */
	public GridTransformMatrix scale(double sx, double sy, double sz) {
		matrix[0][0] *= sx;
		matrix[0][1] *= sx;
		matrix[0][2] *= sx;
		matrix[0][3] *= sx;
		matrix[1][0] *= sy;
		matrix[1][1] *= sy;
		matrix[1][2] *= sy;
		matrix[1][3] *= sy;
		matrix[2][0] *= sz;
		matrix[2][1] *= sz;
		matrix[2][2] *= sz;
		matrix[2][3] *= sz;
		return this;
	}
	
	/**
	 * Applies a translation operation to this TransformMatrix, by multiplying it on the LEFT by
	 * a translation matrix.  This TransformMatrix is modified in place.
	 * @param tx The translation in the x direction.
	 * @param ty The translation in the y direction.
	 * @param tz The translation in the z direction.
	 * @return A reference to this TransformMatrix -- returned for convenience to allow chaining of transformations.
	 */
	public GridTransformMatrix translate(double tx, double ty, double tz) {
		matrix[0][3] += tx;
		matrix[1][3] += ty;
		matrix[2][3] += tz;
		return this;
	}
	/**
	 * Applies a translation operation to this TransformMatrix, by multiplying it on the LEFT by
	 * a translation matrix.  This TransformMatrix is modified in place.
	 * @param v The translation vector. If v == null: return this.
	 * @return A reference to this TransformMatrix -- returned for convenience to allow chaining of transformations.
	 */
	public GridTransformMatrix translate(Vector3D v) {
		if (v!=null) {
		matrix[0][3] += v.x;
		matrix[1][3] += v.y;
		matrix[2][3] += v.z;
		}
		return this;
	}
	
	/**
	* Replaces the translation part of the matrix with a given Vector3D vt.
	* This does not compose the transformation with a translation; it disacards the current
	* translation and applies a new one. Calling this method is equivalent to calling
	* translate(tr.minus(getTranslation())).
	* @param vt The vector that becomes the new translation. A null vector is taken to be zero.
	* @return A reference to this TransformMatrix -- returned for convenience to allow chaining of transformations.
	*/
	public GridTransformMatrix setTranslation(Vector3D vt) {
	if (vt == null) {
	matrix[0][3] = matrix[1][3] = matrix[2][3] = 0;
	}
	else {
	matrix[0][3] = vt.x;
	matrix[1][3] = vt.y;
	matrix[2][3] = vt.z;
	}
	return this;
	}

	/**
	* Replaces the translation part of the matrix with the vector (x,y,z).
	* This does not compose the transformation with a translation; it disacards the current
	* translation and applies a new one.
	* @return A reference to this TransformMatrix -- returned for convenience to allow chaining of transformations.
	*/
	public GridTransformMatrix setTranslation(double x, double y, double z) {
	matrix[0][3] = x;
	matrix[1][3] = y;
	matrix[2][3] = z;
	return this;
	}

	
	/**
	 * Applies a rotation operation to this TransformMatrix, by multiplying it on the LEFT by
	 * a rotation matrix.  The rotation is about the z-axis, through a specified number of degrees.
	 * This TransformMatrix is modified in place.
	 * @param degrees Measure, in degrees, of the angle of rotation about the z-axis.
	 * @return A reference to this TransformMatrix -- returned for convenience to allow chaining of transformations.
	 */
	public GridTransformMatrix rotateZ(double degrees) {
		double radians = degrees*Math.PI/180;
		double c = Math.cos(radians);
		double s = Math.sin(radians);
		double a = c*matrix[0][0] + s*matrix[1][0];
		matrix[1][0] = -s*matrix[0][0] + c*matrix[1][0];
		matrix[0][0] = a;
		a = c*matrix[0][1] + s*matrix[1][1];
		matrix[1][1] = -s*matrix[0][1] + c*matrix[1][1];
		matrix[0][1] = a;
		a = c*matrix[0][2] + s*matrix[1][2];
		matrix[1][2] = -s*matrix[0][2] + c*matrix[1][2];
		matrix[0][2] = a;
		a = c*matrix[0][3] + s*matrix[1][3];
		matrix[1][3] = -s*matrix[0][3] + c*matrix[1][3];
		matrix[0][3] = a;
		return this;
	}
	
	/**
	 * Applies a rotation operation to this TransformMatrix, by multiplying it on the LEFT by
	 * a rotation matrix.  The rotation is about the x-axis, through a specified number of degrees.
	 * This TransformMatrix is modified in place.
	 * @param degrees Measure, in degrees, of the angle of rotation about the x-axis.
	 * @return A reference to this TransformMatrix -- returned for convenience to allow chaining of transformations.
	 */
	public GridTransformMatrix rotateX(double degrees) {
		double radians = degrees*Math.PI/180;
		double c = Math.cos(radians);
		double s = Math.sin(radians);
		double a = c*matrix[1][0] + s*matrix[2][0];
		matrix[2][0] = -s*matrix[1][0] + c*matrix[2][0];
		matrix[1][0] = a;
		a = c*matrix[1][1] + s*matrix[2][1];
		matrix[2][1] = -s*matrix[1][1] + c*matrix[2][1];
		matrix[1][1] = a;
		a = c*matrix[1][2] + s*matrix[2][2];
		matrix[2][2] = -s*matrix[1][2] + c*matrix[2][2];
		matrix[1][2] = a;
		a = c*matrix[1][3] + s*matrix[2][3];
		matrix[2][3] = -s*matrix[1][3] + c*matrix[2][3];
		matrix[1][3] = a;
		return this;
	}
	
	/**
	 * Applies a rotation operation to this TransformMatrix, by multiplying it on the LEFT by
	 * a rotation matrix.  The rotation is about the y-axis, through a specified number of degrees.
	 * This TransformMatrix is modified in place.
	 * @param degrees Measure, in degrees, of the angle of rotation about the y-axis.
	 * @return A reference to this TransformMatrix -- returned for convenience to allow chaining of transformations.
	 */
	public GridTransformMatrix rotateY(double degrees) {
		double radians = degrees*Math.PI/180;
		double c = Math.cos(radians);
		double s = Math.sin(radians);
		double a = c*matrix[2][0] + s*matrix[0][0];
		matrix[0][0] = -s*matrix[2][0] + c*matrix[0][0];
		matrix[2][0] = a;
		a = c*matrix[2][1] + s*matrix[0][1];
		matrix[0][1] = -s*matrix[2][1] + c*matrix[0][1];
		matrix[2][1] = a;
		a = c*matrix[2][2] + s*matrix[0][2];
		matrix[0][2] = -s*matrix[2][2] + c*matrix[0][2];
		matrix[2][2] = a;
		a = c*matrix[2][3] + s*matrix[0][3];
		matrix[0][3] = -s*matrix[2][3] + c*matrix[0][3];
		matrix[2][3] = a;
		return this;
	}

	/**
	 * Multiplying this GridTransformationMatrix on the left by another
	 * matrix.  Note that the transformation is modified in place.
	 * @param m the matrix by which this matrix is to be multiplied on the left.  If m is null, this
	 * matrix is not modified.
	 * @return A reference to this TransformMatrix -- returned for convenience to allow chaining of transformations.
	 */
	public GridTransformMatrix leftMultiplyBy(GridTransformMatrix m) {
		double[][] newMatrix = new double[3][4];
		for (int r = 0; r < 3; r++)
			for (int c = 0; c < 4; c++) {
				double sum = 0;
				for (int i = 0; i < 3; i++)
					sum += m.matrix[r][i] * matrix[i][c];
				if (c == 3)
					sum += m.matrix[r][3];  // matrix[3][3] is taken to be 1, other entries in bottom row are 0 ??HK
				newMatrix[r][c] = sum;
			}
		this.matrix = newMatrix;
		this.reverseSurfaceOrientation = ((this.reverseSurfaceOrientation)!=(m.reverseSurfaceOrientation));
		return this;
	}
	
}
