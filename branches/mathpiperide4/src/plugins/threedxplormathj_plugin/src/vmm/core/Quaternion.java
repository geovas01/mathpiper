/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import vmm.core3D.Vector3D;

/**
 * Represents an element of the set of quaternions.
 */
public class Quaternion {
	
	public final static Quaternion ZERO = new Quaternion();
	public final static Quaternion q1 = new Quaternion(1,0,0,0);
	public final static Quaternion qI = new Quaternion(0,1,0,0);
	public final static Quaternion qJ = new Quaternion(0,0,1,0);
	public final static Quaternion qK = new Quaternion(0,0,0,1);
	
	public double a,b,c,d;
	
	/**
	 * Create a quaternion with all coordinates intially equal to zero.
	 */
	public Quaternion() {
	}
	
	/**
	 * Create a quaternion from initial values for each coordinate.
	 */
	public Quaternion(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	/**
	 * Create a copy of an exiting non-null Quaternion object
	 * @param source the object to be copied.  A NullPointerException is thrown if
	 * this is null.
	 */
	public Quaternion(Quaternion source) {
		a = source.a;
		b = source.b;
		c = source.c;
		d = source.d;
	}
	
	public boolean equals(Object obj) {
		try {
			Quaternion q = (Quaternion)obj;
			return a == q.a && b == q.b && c == q.c && d == q.d;
		}
		catch (Exception e) {
			return false; // handles the case where obj == null or is not an instance of Quaternion
		}
	}
	
	public String toString() {
		return "(" + a + "," + b + "," + c + "," + d + ")";
	}
	
	/**
	 * p.dot(q) returns the scalar product 
	 */
	public double dot(Quaternion q) {
		return (a*q.a + b*q.b + c*q.c +d*q.d);
	}
	
	/**
	 * Returns the norm of this quaternion.
	 */
	public double norm() {
		return Math.sqrt(a*a + b*b + c*c + d*d);
	}
	
	/**
	 * p.conj() returns the conjugate to p 
	 */
	public Quaternion conj() {
		return new Quaternion(a,-b,-c,-d);
	}
	
	/**
	 * Compute the sum of this quaternaion and another quaternion
	 * @param q the non-null quaternion to be added to this one.  If this is null, a NullPointerException is thrown.
	 */
	public Quaternion plus(Quaternion q) {
		return new Quaternion(a + q.a, b + q.b, c + q.c, d + q.d);
	}

	/**
	 * Compute the difference of this quaternaion and another quaternion
	 * @param q the non-null quaternion to be subtracted from this one.  If this is null, a NullPointerException is thrown.
	 */
	public Quaternion minus(Quaternion q) {
		return new Quaternion(a - q.a, b - q.b, c - q.c, d - q.d);
	}

	/**
	 * Compute the product of this quaternaion and another quaternion
	 * @param q the non-null quaternion to be ultiplied by this one.  If this is null, a NullPointerException is thrown.
	 */
	public Quaternion times(Quaternion q) {
		return new Quaternion( 
				a*q.a - b*q.b - c*q.c - d*q.d,
				a*q.b + b*q.a + c*q.d - d*q.c,
				a*q.c - b*q.d + c*q.a + d*q.b,
				a*q.d + b*q.c - c*q.b + d*q.a
			);
	}

	/**
	 * Compute the product of this quaternaion and a real number
	 */
	public Quaternion times(double x) {
		return new Quaternion(a*x, b*x, c*x, d*x);
	}

	/**
	 * Compute the multiplicative inverser of this quaternion.  Note that the inverse of
	 * zero will not generate an error; instead, the inverse of zero is a quaternion object
	 * in which every coordinate is Double.NaN.
	 */
	public Quaternion inverse() {
		double denom = a*a + b*b + c*c + d*d;
		return new Quaternion(a/denom, -b/denom, -c/denom, -d/denom);
	}
	
	/**
	 * Compute the quotient of this quaternaion by another quaternion.  Note that division by zero
	 * does not generate an error.  Instead, the the result is a vector in which each coordinate
	 * is Double.NaN.
	 * @param q the non-null quaternion that this quaternion is to be multiplied by.  If this is null, a NullPointerException is thrown.
	 */
	public Quaternion dividedBy(Quaternion q) {
		return this.times(q.inverse());
	}
	
	
	/**
	 * Returns rotation by phi around standard Hopf fiber through (1,0,0,0)
	 */
	public Quaternion rotateAroundHopfFibre(double phi){
		return new Quaternion(a,b,c*Math.cos(phi)-d*Math.sin(phi), c*Math.sin(phi)+d*Math.cos(phi));
	}
	
	/**
	 * Returns rotation by phi around standard Hopf fiber through q0
	 */
	public Quaternion rotateAroundHopfFibre(double phi, Quaternion q0){
		Quaternion p = new Quaternion(this.times(q0.inverse()));
		p = p.rotateAroundHopfFibre(phi);
		return p.times(q0);
	}
	
	/** 
	 * Returns stereographic projection from (-1,0,0,0)
	 */
	public Vector3D StereographicProjection() {
		return new Vector3D(b/(1+a),c/(1+a),d/(1+a));
	}
}
