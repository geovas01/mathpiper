/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vmm.core.Util;

/**
 * A 3D vector with components of type double named x, y, and z.
 */
public class Vector3D  {

	/**
	 * The first component of the vector.
	 */
	public double x;
	
	/**
	 * The second component of the vector.
	 */
	public double y;
	
	/**
	 * The third component of the vector.
	 */
	public double z;
	
	/**
	 * The origin, (0,0,0).
	 */
	public static final Vector3D ORIGIN = new Vector3D(0,0,0);
	
	/**
	 * The unit vector in the x direction, (1,0,0).
	 */
	public static final Vector3D UNIT_X = new Vector3D(1,0,0);
	
	/**
	 * The unit vector in the y direction, (0,1,0).
	 */
	public static final Vector3D UNIT_Y = new Vector3D(0,1,0);
	
	/**
	 * The uint vector in the z direction, (0,0,1).
	 */
	public static final Vector3D UNIT_Z = new Vector3D(0,0,1);
	
	/**
	 * Construct a vector with all three components initially equal to zero.
	 */
	public Vector3D() {
	}
	
	/**
	 * Construct a vector that initially has coordinates (x,y,z).
	 */
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Construct a vector that is initially a copy of a specified vector.
	 * @param v the constructed vector is (v.x, v.y, v.z), or is (0,0,0) if v is null.
	 */
	public Vector3D(Vector3D v) {
		if (v == null)
			x = y = z = 0;
		else {
			x = v.x;
			y = v.y;
			z = v.z;
		}
	}
	
	/**
	 * Returns true <code>obj</code> is a non-null object whose class in Vector3D and such that
	 * the three corrdinates of the vector <code>obj</code> are the same as the coordinates of this
	 * vector.
	 */
	public boolean equals(Object obj) {
		if (obj == null || ! (obj.getClass().equals(Vector3D.class)))
			return false;
		Vector3D v = (Vector3D)obj;
		return v.x == x && v.y == y && v.z == z;
	}
	
	/**
	 * Returns the length of this vector, computed as <code>Math.sqrt(x*x+y*y+z*z)</code>
	 */
	public double norm() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	/**
	 * Returns the length^2 of this vector, computed as <code>(x*x+y*y+z*z)</code>
	 */
	public double norm2() {
		return (x*x + y*y + z*z);
	}
	
	/**
	 * Divides each component of the vector by the norm of the vector, giving a vector that has length 1.
	 * However, if the vector is zero, or if any of its components are infinite or undefined, then the
	 * result of calling this method is to set all the components of the vector to Double.NaN.
	 * This method modifies the vector.
	 * @see #normalized()
	 */
	public void normalize() {
		double lengthRecip = 1.0 / Math.sqrt(x*x + y*y + z*z);
		if (	Double.isNaN(lengthRecip) || Double.isInfinite(lengthRecip) )
			x = y = z = Double.NaN;
		else {
			this.x *= lengthRecip;
			this.y *= lengthRecip;
			this.z *= lengthRecip;
		}
	}
	
	/**
	 * Multiplies each component of this vector by -1.  This method modifies the vector.
	 * @see #negated()
	 */
	public void negate() {
		x = -x;
		y = -y;
		z = -z;
	}
	
	/**
	 * Returns a new vector that is equal to this vector normalized.  This vector is not modified.
	 * @see #normalize()
	 */
	public Vector3D normalized() {
		Vector3D v = new Vector3D(x,y,z);
		v.normalize();
		return v;
	}
	
	/**
	 * Returns a new vector that is equal to -1 times this vector.  This vector is not modified.
	 * @see #negate()
	 */
	public Vector3D negated() {
		return new Vector3D(-x,-y,-z);
	}
	
	/**
	 * Returns the vector sum of this vector and v.  A new vector object is constructed to contain
	 * the result; neither input vector is modified.
	 * @param v a non-null vector
	 */
	public Vector3D plus(Vector3D v) {
		return new Vector3D(x + v.x, y + v.y, z + v.z);
	}
	
	/**
	 * Returns the linear combination c*this + d*v. A new vector object contains the result.
	 */
	public Vector3D linComb(double c, double d,Vector3D v) {
		return new Vector3D(c*x + d*v.x, c*y + d*v.y, c*z + d*v.z);
	}
	
	/**
	 * this.assign(v) assigns to this the value v.
	 */
	public void assign(Vector3D v) {
		x = v.x; y = v.y; z = v.z;
	}
	
	/**
	 * this.assignPlus(v) assigns to this the value this + v.
	 */
	public void assignPlus(Vector3D v) {
		x += v.x;  y += v.y; z += v.z;
	}
	
	/**
	 * this.assignSum(v,w) assigns to this the value v + w.
	 */
	public void assignSum(Vector3D v, Vector3D w) {
		x = v.x + w.x;  y = v.y + w.y; z = v.z + w.z;
	}
	
	/**
	 * this.assignLinComb(c,d,v) assigns to this the value c*this + d*v.
	 */
	public void assignLinComb(double c, double d, Vector3D v) {
		x = c*x + d*v.x; y = c*y + d*v.y; z =  c*z + d*v.z;
	}
	
	/**
	 * this.assignLinComb(c,v,d,w) assigns to this the value c*v + d*w.
	 */
	public void assignLinComb(double c, Vector3D v, double d, Vector3D w) {
		x = c*v.x + d*w.x; y = c*v.y + d*w.y; z =  c*v.z + d*w.z;
	}

	/**
	 * this.assignPlus_SumTimes(u, v, w, ww, d) assigns to this the value: this + (u + v + w + ww)*d.
	 */
	public void assignPlus_SumTimes(Vector3D u, Vector3D v, Vector3D w, Vector3D ww, double d) {
		x += d*(u.x + v.x + w.x + ww.x); y += d*(u.y + v.y + w.y + ww.y); 
		z +=  d*(u.z + v.z + w.z + ww.z);
	}
	
	/**
	 * Returns the vector difference of this vector and v.  A new vector object is constructed to contain
	 * the result; neither input vector is modified.
	 * @param v a non-null vector
	 */
	public Vector3D minus(Vector3D v) {
		return new Vector3D(x - v.x, y - v.y, z - v.z);
	}
	
	/**
	 * this.assignMinus(v) assigns to this the value this (- v).
	 */
	public void assignMinus(Vector3D v) {
		x -= v.x;  y -= v.y; z -= v.z;
	}
	
	/**
	 * this.assignMinus(v, w) assigns to this the value this (v - w).
	 */
	public void assignMinus(Vector3D v, Vector3D w) {
		x = v.x - w.x;  y = v.y - w.y; z = v.z - w.z;
	}
	
	/**
	 * Returns the scalar product of this vector times d.  A new vector object is constructed to contain
	 * the result; the vector is not modified.
	 * @param d the scalar that is to be multiplied times this vector
	 */

	public Vector3D times(double d) {
		return new Vector3D(d*x, d*y, d*z);
	}
	
	/**
	 * this.assignTimes(d) assigns to this the value d*this.
	 */
	public void assignTimes(double d) {
		x *= d;  y *= d; z *= d;
	}
	
	/**
	 * this.assignTimes(d, v) assigns to this the value d*v.
	 */
	public void assignTimes(double d, Vector3D v) {
		x = d*v.x;  y = d*v.y; z = d*v.z;
	}

	/**
	 * this.assignSumTimes(v, w, d) assigns to this the value (v + w)*d.
	 */
	public void assignSumTimes(Vector3D v, Vector3D w, double d) {
		x = d*(v.x + w.x);  y = d*(v.y + w.y); z = d*(v.z + w.z);
	}
	
	/**
	 * this.assignSumTimes(v, w, vv, ww, d) assigns to this the value (v + w + vv + ww)*d.
	 */
	public void assignSumTimes(Vector3D v, Vector3D w, Vector3D vv, Vector3D ww, double d) {
		x = d*(v.x + w.x + vv.x + ww.x);  y = d*(v.y + w.y + vv.y + ww.y); z = d*(v.z + w.z + vv.z + ww.z);
	}
	
	/**
	 * Returns the dot product of this vector and v.  A new vector object is constructed to contain
	 * the result; neither input vector is modified.
	 * @param v a non-null vector
	 */
	public double dot(Vector3D v) {
		return x*v.x + y*v.y + z*v.z;
	}
	
	/**
	 * Returns the cross product of this vector and v.  A new vector object is constructed to contain
	 * the result; neither input vector is modified.
	 * @param v a non-null vector
	 */
	public Vector3D cross(Vector3D v) {
		double x1 =  (y * v.z) - (v.y * z);
		double y1 = -(x * v.z) + (v.x * z);
		double z1 =  (x * v.y) - (v.x * y);
		return new Vector3D(x1,y1,z1);
	}
	
	/**
	 * Rotates this around the unit vector axis by 180 degrees: this to -this + 2<this,axis>*axis.
	 * Call as this.reflectInAxis(axis).
	 */
	public Vector3D reflectInAxis(Vector3D axis) {
	       double s = 2 * (axis.x * x + axis.y * y + axis.z * z);
	       Vector3D destination = new Vector3D(s*axis.x - x,s*axis.y - y,s*axis.z - z);
	       return destination;
	    }
	
	/**
	 * Why can't the following version not be called by SphericalCycloid? 
	public static Vector3D reflectInAxis(Vector3D axis, Vector3D source) {
		double s = 2 * (axis.x * source.x + axis.y * source.y + axis.z * source.z);
		Vector3D destination = new Vector3D(s*axis.x - source.x,s*axis.y - source.y,s*axis.z - source.z);
		return destination;
	}   
	*/
	
	/**
	 * Returns a string representation of this vector of the form "(x,y,z)" where x, y, and z are
	 * the numerical components of the vector.  The special strings NaN, INF and -INF are used to
	 * represent the values Double.NaN, Double.POSITIVE_INFINITY, and Double.NEGATIVE_INFINITY.
	 */
	public String toString() {
		String xStr = Util.toExternalString(x);
		String yStr = Util.toExternalString(y);
		String zStr = Util.toExternalString(z);
		return "(" + xStr + "," + yStr + "," + zStr + ")";
	}
	
	/**
	 * Attempts to convert a string into a value of type Vector3D.  The string should be
	 * in the format produced by {@link #toString()}, of the form "(x,y,z)".  Will also accept strings
	 * that use a space as a separator and are not enclosed in parentheses, of the form "x y z".
	 * @param str the non-null string that is to be converted
	 * @throws NumberFormatException if the string is not of the correct format (or is null)
	 */
	public static Vector3D fromString(String str) throws NumberFormatException {
		try {
			Matcher matcher = Pattern.compile("\\((.*),(.*),(.*)\\)").matcher(str);
			if (! matcher.matches() ) {
				matcher = Pattern.compile("(.*) (.*) (.*)").matcher(str.trim());
				if (! matcher.matches())
					throw new Exception();
			}
			String xStr = matcher.group(1);
			String yStr = matcher.group(2);
			String zStr = matcher.group(3);
			double x = (Double)Util.externalStringToValue(xStr, Double.TYPE);
			double y = (Double)Util.externalStringToValue(yStr, Double.TYPE);
			double z = (Double)Util.externalStringToValue(zStr, Double.TYPE);
			return new Vector3D(x,y,z);
		}
		catch (Exception e) {
			throw new NumberFormatException("Can't convert string to Vector3D");
		}
	}
	
}
