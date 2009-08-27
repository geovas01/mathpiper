/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.awt.geom.Point2D;

/**
 * The class LinearAlgebra provides more methods to the class Point2D
 * @author karcher
 */
public class LinearAlgebra{
  
/**
 * public Point2D minus2D(Point2D P2, Point2D P1) returns a new object with value (P2 - P1).
 */	
	public static Point2D minus2D(Point2D P2, Point2D P1){
		Point2D D = new Point2D.Double();
		D.setLocation(P2.getX() - P1.getX(), P2.getY() - P1.getY());
		return D;
	}
	
	/**
	 * public void assignMinus2D(Point2D P2, Point2D P1) assigns to P2 the value (P2 - P1).
	 */	
		public static void assignMinus2D(Point2D P2, Point2D P1){
			P2.setLocation(P2.getX() - P1.getX(), P2.getY() - P1.getY());
		}
	
	/**
	 * public Point2D convexCombination(double t, Point2D P1, Point2D P2) returns a new object
	 * with value P1 + t*(P2-P1).
	 */
	public static Point2D convexCombination(double t, Point2D P1, Point2D P2){
		Point2D D = new Point2D.Double();
		D.setLocation( (1-t)*P1.getX() + t*P2.getX(), (1-t)*P1.getY() + t*P2.getY());
		return D;
	}
	
	/**
	 * public Point2D convexCombination(double t1, double t2, Point2D P1, Point2D P2) returns a
	 * new object with value (t1*P1 + t2*P2).
	 */
	public static Point2D linearCombination(double t1, double t2, Point2D P1, Point2D P2){
		Point2D D = new Point2D.Double();
		D.setLocation( t1*P1.getX() + t2*P2.getX(), t1*P1.getY() + t2*P2.getY());
		return D;
	}
	
	/**
	 * public Point2D normalTo(Point2D P) returns new object N = (P.y, -P.x).
	 */
	public static Point2D normalTo(Point2D P){
		Point2D N = new Point2D.Double();
		N.setLocation(P.getY(),-P.getX());
		return N;
	}
	
	/**
	 * public Point2D normalTo(Point2D P2, Point2D P1) = normalTo(minus2D(P2,P1)).
	 */
	public static Point2D normalTo(Point2D P2, Point2D P1){
		Point2D N = new Point2D.Double();
		N.setLocation(P2.getY() - P1.getY(), -P2.getX() + P1.getX());
		return N;
	}
	
	/**
	 * public double dotProduct(Point2D P, Point2D Q) returns (P.x*Q.x + P.y*Q.y).
	 */
	public static double dotProduct(Point2D P, Point2D Q){
		return P.getX()*Q.getX() + P.getY()*Q.getY();
	}
	
	/**
	 * public double determinant(Point2D P, Point2D Q) returns (P.x*Q.y - P.y*Q.x).
	 */
	public double determinant(Point2D P, Point2D Q){
		return P.getX()*Q.getY() - P.getY()*Q.getX();
	}
	
	/**
	 * public Point2D intersectTwoLines(Point2D P1, Point2D P2, Point2D Q1, Point2D Q2)
	 * returns the intersection point of P1 + s(P2 - P1) and Q1 + t(Q2 - Q1).
	 * Creates the return Point2D.
	 */
	public static Point2D intersectTwoLines(Point2D P1, Point2D P2, Point2D Q1, Point2D Q2)	{
		double s,nn;
		Point2D A,N;
		
		assignMinus2D(P2, P1);  A = P2;
		assignMinus2D(Q2, Q1);  N = Q2;
		nn = dotProduct(A,N);
		s = ( N.getX()*(Q1.getX() - P1.getX()) + N.getY()*(Q1.getY() - P1.getY()) )/nn;
		
		return convexCombination(s, P1, P2);
	}
	
	/**
	 * public Point2D intersectTwoLines(double P1x, double P1y, double P2x, double P2y, 
	 *		                        double Q1x, double Q1y, double Q2x, double Q2y)
	 * returns the intersection point of P1 + s(P2 - P1) and Q1 + t(Q2 - Q1), where
	 * these four points are given by their double coordinates.
	 * Creates the return Point2D.
	 */
	public static Point2D intersectTwoLines(double P1x, double P1y, double P2x, double P2y, 
			                        double Q1x, double Q1y, double Q2x, double Q2y)	{
		double s,nn;
		Point2D A = new Point2D.Double( P2x-P1x, P2y-P1y);
		Point2D N = new Point2D.Double(-Q2y+Q1y, Q2x-Q1x);
		nn = dotProduct(A,N);
		s = ( N.getX()*(Q1x - P1x) + N.getY()*(Q1y - P1y) )/nn;
		A.setLocation(P1x +s*(P2x-P1x), P1y +s*(P2y-P1y));
		return A;
	}
	


}
