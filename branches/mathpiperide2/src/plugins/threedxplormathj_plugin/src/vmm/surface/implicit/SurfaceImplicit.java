/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.implicit;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import vmm.actions.ActionList;
import vmm.actions.ActionRadioGroup;
import vmm.core.Animateable;
import vmm.core.BasicAnimator;
import vmm.core.Decoration;
import vmm.core.Display;
import vmm.core.Exhibit;
import vmm.core.I18n;
import vmm.core.IntegerParam;
import vmm.core.Parameter;
import vmm.core.Prefs;
import vmm.core.RealParam;
import vmm.core.RealParamAnimateable;
import vmm.core.TaskManager;
import vmm.core.VMMSave;
import vmm.core.View;
import vmm.core3D.Exhibit3D;
import vmm.core3D.LightSettings;
import vmm.core3D.PhongLighting;
import vmm.core3D.Transform3D;
import vmm.core3D.Vector3D;
import vmm.core3D.View3D;
import vmm.core3D.View3DWithLightSettings;

/**
 * Represents a surface in three-space defined as the level set of a real-valued function of x,y,z.
 */
abstract public class SurfaceImplicit extends Exhibit3D {
	
	/**
	 * The implicit surface is defined as solutions of heightFuncton(x,y,z) = level .
	 */
abstract public double heightFunction(double x, double y, double z);

public double heightFunction(Vector3D thePoint) {
	return heightFunction(thePoint.x, thePoint.y, thePoint.z);
}

enum equationType {OTHER,LINEAR,QUADRATIC,CUBIC, QUARTIC}

equationType heightFunctionType = equationType.OTHER;

	
	/**
	 * The level of the heightFunction that define the surface.
	 * Default value is 0.0.
	 */
protected RealParamAnimateable level = new RealParamAnimateable("vmm.surface.implicit.SurfaceImplicit.level",0.0);

	/**
	 * The desired number of points in the cloud of random points that will show the implicit surface in 
	 * Point Cloud Render Mode.  Should be less than MaxPointsInCloud.  Default is 2000.
	 */
protected IntegerParam pointCloudCount = new IntegerParam("vmm.surface.implicit.SurfaceImplicit.pointCloudCount",2000);
	
	/**
	 * The number of lines in the ListOfRandomLines used to form a point cloud by intersecting the
	 * implicit surface with these lines.  Default is 30000.
	 */
protected IntegerParam randomLineCount = new IntegerParam("vmm.surface.implicit.SurfaceImplicit.randomLineCount",40000);
	
	/**
	 * Radius of the sphere centered at origin inside of which we search for points on the surface.  Default value is 3.0.
	 */
protected RealParamAnimateable searchRadius = new RealParamAnimateable("vmm.surface.implicit.SurfaceImplicit.searchRadius",3.0);

/**
 * Raytracing Resolution. Determines how finely the rays are divided to find points of intersection with surface.  Default value is 0.05.
 */
protected RealParam rayTraceResolution = new RealParam("vmm.surface.implicit.SurfaceImplicit.rayTraceResolution",0.05);
	
	/**
	 * A cloud of random points on the implicit surface. Constructed by intersecting lines from ListOfRandomLines
	 * with the surface
	*/
protected Vector3D[] pointCloud;



/**
 * Current number of points in PointCloud
 */
private int numberOfPointsInCloud = 0;

private TaskManager taskManager;  // for parallelization


public  SurfaceImplicit() {
	addParameter(level);
	addParameter(rayTraceResolution);
	rayTraceResolution.setMinimumValueForInput(0.00001);
	addParameter(pointCloudCount);
	pointCloudCount.setMinimumValueForInput(200);
	pointCloudCount.setMaximumValueForInput(50000);
	addParameter(randomLineCount);
	randomLineCount.setMinimumValueForInput(1000);
	randomLineCount.setMaximumValueForInput(200000);
	addParameter(searchRadius);
	searchRadius.setMinimumValueForInput(Double.MIN_VALUE);
	setFramesForMorphing(12);
	setUseFilmstripForMorphing(true);
	setDefaultBackground(Color.BLACK);
}

    
/**
 * Given a point P in R^3 and letting level = heightFunction(P), this gets the unit normal vector 
 * to the surface heightFunction = level at the point P by normalizing the gradient of the 
 * heightFunction at P.
 */
public Vector3D normalToSurfaceAt(Vector3D P) {
	double delta = 0.000001;
	double TwoOverDelta = 2/delta;
	Vector3D grad = new Vector3D();
	grad.x = heightFunction(P.x + delta, P.y, P.z) - heightFunction(P.x-delta, P.y, P.z);
	grad.y = heightFunction(P.x, P.y  + delta, P.z) - heightFunction(P.x, P.y-delta, P.z);
	grad.z = heightFunction(P.x, P.y, P.z + delta) - heightFunction(P.x, P.y, P.z - delta);
	Vector3D gradient = new Vector3D(grad.times(TwoOverDelta));
	return gradient.normalized();
}


	/**
     * A line in R^3: decribed by its direction (Vector3D direction;), a unit vector, and its intercept, (Vector3D intercept;),
     * which is where the line intersects the plane through the origin normal to direction (this is also the point on the line 
     * that is nearest to the origin. The line has the parametric equation: point(t) = intercept + t * direction  .
     */
 public class Line3D extends Object{
    	Vector3D intercept;
    	Vector3D direction;
    	public Vector3D parametricEquation(double t) {
    		Vector3D point = new Vector3D();
    		point.assignLinComb(1,intercept,t,direction);
    		return point;
    	}
    	public Line3D(Vector3D intrcpt, Vector3D drctn) {
    		this.intercept = intrcpt;
    		this.direction = drctn;
    	}
      }   
 

 	/**
 	 * Overridden to shut down the task manager that is used for parallelization,
 	 * when there are no more views of the exhibit.
 	 */
	public void removeView(View view) {
		super.removeView(view);
		if (taskManager != null && (getViews() == null  || getViews().size() == 0)) {
			taskManager.shutDown();
			taskManager = null;
		}			
	}


 /**
  This creates a fixed list of lines in R^3 that is random with respect to the kinematic 
  measure on the subset of the space of all lines that meet the ball of radius searchRadius
  centered at the origin.
  */
	protected Line3D[] ListOfRandomLines;

private class MakeRandomLines implements Runnable {
	int start, end;
	MakeRandomLines(int start, int end) {
		this.start = start;
		this.end = end;
	}
	public void run() {
    	Vector3D Z_Axis = Vector3D.UNIT_Z;
		Vector3D unrotatedIntercept = new Vector3D();  // can be reused in the loop
		Random random = new Random(start); 
		for (int i = start; i <= end; i++) {
			double longitude = 2* Math.PI* random.nextDouble();
			double colatitude = Math.acos(random.nextDouble());
			
			Vector3D theDirection = new Vector3D();
			theDirection.x = Math.sin(colatitude) * Math.cos(longitude); 
			theDirection.y = Math.sin(colatitude) * Math.sin(longitude);          
			theDirection.z = Math.cos(colatitude);
			
			double theAngle =  2*Math.PI*random.nextDouble();
			double theRadius = Math.sqrt(random.nextDouble());
			
			unrotatedIntercept.x = theRadius*Math.cos(theAngle);
			unrotatedIntercept.y = theRadius*Math.sin(theAngle);
			unrotatedIntercept.z = 0;
			unrotatedIntercept = unrotatedIntercept.times(searchRadius.getValue());  // scale it !
			
			Vector3D theIntercept = Maps.Transvection(Z_Axis,theDirection,unrotatedIntercept);	
			ListOfRandomLines[i] = new Line3D(theIntercept,theDirection);
		}
	}
}

private double searchRadiusUsedForRandomLines;

synchronized public void MakeListOfRandomLines() {
	double desiredSearchRadius = searchRadius.getValue();
	int desiredNumberOfLines = randomLineCount.getValue();
	if ( searchRadiusUsedForRandomLines == desiredSearchRadius && ListOfRandomLines != null &&
			ListOfRandomLines.length == desiredNumberOfLines+1)
		return;   // the desired lines are already computed
	searchRadiusUsedForRandomLines = desiredSearchRadius;
	ListOfRandomLines = new Line3D[randomLineCount.getValue() + 1]; 
	ArrayList<Runnable> jobs = new ArrayList<Runnable>();
	for (int start = 1; start < ListOfRandomLines.length; start += 500) {
		int end = start + 500 - 1;
		if (end >= ListOfRandomLines.length)
			end = ListOfRandomLines.length - 1;
		jobs.add( new MakeRandomLines(start,end) );
	}
	if (taskManager == null) 
		taskManager = new TaskManager();
	taskManager.executeAndWait(jobs);
}
    
 
 /**
  * This is the minimum spacing for testing along a line the sign of the height function prior to using regula falsi
  * Default is 0.05, but certain surfaces (notably the Steiner Roman Surface) need a smaller value so as not to miss roots.
  */
  protected double resolution = rayTraceResolution.getValue();
 

 /**
  * The heightFunction as a function of the signed distance t along line.
  */
protected double heightAlongLine(double t, Line3D line) {
 	Vector3D point = new Vector3D(line.parametricEquation(t));
 	double theHeight = heightFunction(point.x,point.y,point.z);
 	return theHeight;
 }
 
/**
 * This is the function whose vanishing gives a parameter value t such that a point 
 * of the line with that parameter value lies on the implicit surface. 
 * That is, if theFunction(t, theLine) = 0, then theLine.parametricEquation(t)
 * is a point of the surface
 */
 public  double theFunction(double t, Line3D theLine){
	// System.out.println(heightAlongLine(t,theLine) - level.getValue());
		return   heightAlongLine(t,theLine) - level.getValue();
	}
 
 /**
  * This calculates the real roots (if any) of theEquation when it is a quadratic polynomial 
  * and stores them in theRoots
  */
 public void  quadraticSolve(Line3D theLine, double[] theRoots) {
 // Want real solutions of  f(x) = a x^2 + b x + c  = 0 
 //	where f(t) = theFunction(t,theLine)
	double a,b,c;  // the coefficients
 // first find the coefficients from values of theFunction at -1, 0, 1 
     double f_1 = theFunction(-1,theLine);
	 double f0 = theFunction(0,theLine);
	 double f1 = theFunction(1,theLine);
	 // f(0) = c
	 c = f0;
	 // f(1)  = a + b + c
	 // f(-1) = a - b + c
	 // f(1) - f(-1) = 2 b
	 b = 0.5*(f1 - f_1);
	 a = f1 - b - c;
	 double Disc = b*b - 4*a*c;   //the discriminant of the quadratic equation
     double numRoots = ((Disc >=0) ? 2.0 : 0.0); 
     theRoots[0] = numRoots;
	 if (numRoots == 2.0) {
		 theRoots[1] = 0.5*(-b - Math.sqrt(Disc))/a;
	     theRoots[2] = 0.5*(-b + Math.sqrt(Disc))/a;
	     
	     if (theRoots[2] < theRoots[1]) {
	    	 double t = theRoots[2];
	    	 theRoots[2] = theRoots[1];
	    	 theRoots[1] = t;  
	     } 
	 }
 }
 

 public void cubicSolve(Line3D theLine, double[] theRoots)  {
	 final double EPS = 1e-9;    // if abs(t) < 0 we consider t to be zero.
	// Want real solutions of  f(x) = a x^3 + b x^2 + c x + d = 0
	// where f(t) = theFunction(t,theLine)
	double a,b,c,d;  // the coefficients
	//first find the coefficients from values of theFunction at -1, 0, 1, 2
	double f_1 = theFunction(-1,theLine);
	double f0 = theFunction(0,theLine);
	double f1 = theFunction(1,theLine);
	double f2 = theFunction(2,theLine);
	// f(0) = d
	d = f0;
	// f(1)  =  a + b + c + d
	// f(-1) = -a + b - c + d  so  
    // 2(a + c) = f(1) - f(-1)
	// b + d = 0.5 * ((f(1) + f(-1))
	// b = 0.5 * ((f(1) + f(-1)) - d
	b = 0.5*(f1 + f_1) - d;
	// f(2) = 8a + 4b + 2c + d = 6a + 4b + 2(a + c) + d
	a = (1.0/6.0) * (f2 - 4.0*b -(f1 - f_1) - d);
    if (Math.abs(a) < EPS) quadraticSolve(theLine,theRoots); 
    else  //begin !(a==0)
    {
	c = 0.5*(f1 - f_1) - a;
    // reduce to monic form: x^3 + Ax^2 + Bx + C = 0  
    double A = b/a;
	double B = c/a;
	double C = d/a;
	// substitution x = t - A/3 reduces equation to  t^3 +pt + q = 0  where  p = b - a^3/3, q = c + (2a^3 - 9 a b)/27
	double p = 1.0/3.0 * (- 1.0/3.0 * A*A + B);
	//System.out.println(p);
	double q = (1.0/27.0 * A*A*A - 1.0/6.0 * A * B + (1.0/2.0)*C);
	// use Cardano's formula 
	double cube_p = p * p * p;
	double disc   = q * q + cube_p;
	
	if (Math.abs(disc) < EPS)   // i.e., disc is zero
	{
		if (Math.abs(q) < EPS) // one triple solution 
		{
            theRoots[ 0 ] = 1;
	        theRoots[ 1 ] = 0;
		}
		else // one single and one double real solution 
		{
             theRoots[ 0 ] = 2;
             double u = Math.cbrt(-q);
             theRoots[ 1 ] = 2 * u;
             theRoots[ 2 ] = - u;
		}
	}  // end disc = 0   
       
       else
    	   if (disc < 0) // "Casus irreducibilis": 3 real solutions
	{
		    double phi = 1.0/3.0 * Math.acos(-q / Math.sqrt(-cube_p));
		    double s = 2.0 * Math.sqrt(-p);
            theRoots[ 0 ] = 3.0;
		    theRoots[ 1 ] =   s * Math.cos(phi);
		    theRoots[ 2 ] = - s * Math.cos(phi + Math.PI / 3.0);
		    theRoots[ 3 ] = - s * Math.cos(phi - Math.PI / 3.0);
	}
	else // disc > 0  so one real solution 
	{
		    double sqrt_disc = Math.sqrt(disc);
		    double u =   Math.cbrt(sqrt_disc - q);
		    double v = - Math.cbrt(sqrt_disc + q);    
            theRoots[0] = 1.0;
		    theRoots[ 1 ] = u + v;
	} 
       // any roots so far are in terms of the substitution variable t = x + A/3, 
       // substitute back  x = t - A/3, i.e., subtract A/3 from each root.
	for (int i = 1; i <= theRoots[ 0 ] ; ++i){
		theRoots[ i ] = theRoots[i] - (1.0/3.0) * A;}
   }  //end !(a==0)
    
//  tiny insertion sort
   for (int i = 2; i <= theRoots[0]; i++) 
    { for (int j = i; j > 1 && theRoots[j - 1] > theRoots[j]; j--) 
        { double t = theRoots[j];
            theRoots[j] = theRoots[j - 1];
            theRoots[j - 1] = t;
        } 
    } // end of sort  
    
 }
 
 public void quarticSolve(Line3D theLine, double[] theRoots)  {
     // Want real solutions of f(x) = ax^4+bx^3+cx^2+dx+e=0
     // where f(t) = theFunction(t,theLine)
      double a, b, c,  d,  e;  // the coefficients
    //  f(t) = ax^4+bx^3+cx^2+dx+e
    //   f_2 = f(-2)
    //   f_1 = f(-1)
    //   f0  = f(0)
    //   f1  = f(1)
    //   f2 =  f(2)
//    first find the coefficients from values of theFunction at -2, -1, 0, 1, 2
      double f_2 = theFunction(-2,theLine);
  	  double f_1 = theFunction(-1,theLine);
  	  double f0  = theFunction(0,theLine);
  	  double f1  = theFunction(1,theLine);
  	  double f2  = theFunction(2,theLine);
    //   f0 = e
         e = f0;
    //  f1  = a + b + c + d + e
    //  f_1 = a - b + c - d + e
    //  f1 + f_1 = 2*(a + c + e)
    //  f1 - f_1 = 2*(b + d)
    //  f2  = 16 a + 8 b + 4 c + 2 d + e = 12a  + 8b + 4*(a+c) + 2d + e = 12a  + 8b + 2*(f1 + f_1) - 4e + 2d + e
    //  f_2 = 16 a - 8 b + 4 c - 2 d + e 
    //  f2 + f_2 = 32 a + 8 c + 2 e
    //  f2 - f_2 = 16 b + 4 d = 12 b + 4*(b + d) = 12 b + 2*(f1 - f_1)
         b = (1.0/12.0)*(f2 - f_2) - (1.0/6.0)*(f1 - f_1);
         d = 0.5*(f1 - f_1) - b;
     //  (a + c) = 0.5*(f1 + f_1) - e   
        a = (1.0/12.0)*(f2 - 8*b -2*(f1 + f_1) + 3*e - 2*d);
        c = 0.5*(f1 + f_1) - e - a;
      //  System.out.println(a);
      double inva = 1 / a;
      double c1 = b * inva;
      double c2 = c * inva;
      double c3 = d * inva;
      double c4 = e * inva;
      // cubic resolvant
      double c12 = c1 * c1;
      double p = -0.375 * c12 + c2;
      double q = 0.125 * c12 * c1 - 0.5 * c1 * c2 + c3;
      double r = -0.01171875 * c12 * c12 + 0.0625 * c12 * c2 - 0.25 * c1 * c3 + c4;
      double z = solveCubicForQuartic(-0.5 * p, -r, 0.5 * r * p - 0.125 * q * q);
      double d1 = 2.0 * z - p;
      if (d1 < 0) {
          if (d1 > 1.0e-10)
              d1 = 0;
          theRoots[0] = 0.0;
      }
      double d2;
      if (d1 < 1.0e-10) {
          d2 = z * z - r;
          if (d2 < 0)
        	  theRoots[0] = 0.0;
          d2 = Math.sqrt(d2);
      } else {
          d1 = Math.sqrt(d1);
          d2 = 0.5 * q / d1;
      }
      // setup useful values for the quadratic factors
      double q1 = d1 * d1;
      double q2 = -0.25 * c1;
      double pm = q1 - 4 * (z - d2);
      double pp = q1 - 4 * (z + d2);
      if (pm >= 0 && pp >= 0) {
          // 4 roots (!)
          pm = Math.sqrt(pm);
          pp = Math.sqrt(pp);
          theRoots[0] = 4.0;
          theRoots[1] = -0.5 * (d1 + pm) + q2;
          theRoots[2] = -0.5 * (d1 - pm) + q2;
          theRoots[3] = 0.5 * (d1 + pp) + q2;
          theRoots[4] = 0.5 * (d1 - pp) + q2;
          
      }
      
     else if (pm >= 0) {
          pm = Math.sqrt(pm);
          theRoots[0] = 2;
          theRoots[1] = -0.5 * (d1 + pm) + q2;
          theRoots[2] = -0.5 * (d1 - pm) + q2;
      } else if (pp >= 0) {
          pp = Math.sqrt(pp);
          theRoots[0] = 2;
          theRoots[1] = 0.5 * (d1 - pp) + q2;
          theRoots[2] = 0.5 * (d1 + pp) + q2;
      }
      
//    tiny insertion sort
      for (int i = 2; i <= theRoots[0]; i++) 
      { for (int j = i; j > 1 && theRoots[j - 1] > theRoots[j]; j--) 
          { double t = theRoots[j];
              theRoots[j] = theRoots[j - 1];
              theRoots[j - 1] = t;
          } 
      } // end of sort
      
  }

  /**
   * Return only one root for the specified cubic equation. This routine is
   * only meant to be called by the quartic solver. It assumes the cubic is of
   * the form: x^3+px^2+qx+r.
   */
  private static final double solveCubicForQuartic(double p, double q, double r) {
      double A2 = p * p;
      double Q = (A2 - 3.0 * q) / 9.0;
      double R = (p * (A2 - 4.5 * q) + 13.5 * r) / 27.0;
      double Q3 = Q * Q * Q;
      double R2 = R * R;
      double d = Q3 - R2;
      double an = p / 3.0;
      if (d >= 0) {
          d = R / Math.sqrt(Q3);
          double theta = Math.acos(d) / 3.0;
          double sQ = -2.0 * Math.sqrt(Q);
          return sQ * Math.cos(theta) - an;
      } else {
          double sQ = Math.pow(Math.sqrt(R2 - Q3) + Math.abs(R), 1.0 / 3.0);
          if (R < 0)
              return (sQ + Q / sQ) - an;
          else
              return -(sQ + Q / sQ) - an;
      }
  }

	 
 
 /**
  * This finds an approximate root of theFunction, 
  * on the interval [leftEnd,rightEnd] assuming that the sign of 
  * theFunction is not the same at both ends. It uses an optimized 
  * verison of RegulaFalsi due to Hermann Karcher.
  */
 public double FindNextRoot(Line3D theLine, double leftEnd, double rightEnd, double tolerance){
    double u;
//    double v;
//    double weight;
    double[] args =   new double[3];
    double[] values = new double[3];
//    int index = 1;
//    int count = 1;
    args[1] = leftEnd;
    args[2] = rightEnd;
    values[1] = theFunction(args[1],theLine);
    values[2] = theFunction(args[2],theLine);

    u  = ( args[1]*values[2] - args[2]*values[1] ) / (values[2] - values[1]); //Secant root
   /* v  = theFunction(u,theLine); 
    while ( Math.abs(v) > tolerance) {
      if (Math.signum(v) == Math.signum(values[index])) {count = count + 1;}
      else { index = 3 - index;  count = 1; } 
     weight = count*Math.min(0.25,(v/values[index])*(v/values[index])) ;        
     args[index] =   u; 
     values[index] = v;                           
     u = ( args[1]*values[2] - args[2]*values[1] )/(values[2] - values[1]);  // Secant root
     u = ( u + weight * args[3-index] ) / (1 + weight);  
   } */
  return u;
} 
 
 /**
  * This finds the points of intersection of a line (theLine) and the surface in the case that the surface is quadratic.
 * @param points 
  *
  */
 private void IntersectLineWithImplicitQuadraticSurface(Line3D theLine, double[] theRoots, ArrayList<Vector3D> points) {
	     // modified by Eck to place points into an array list instead of directly into the pointCloud array
	  double searchRadiusSquared = Math.pow(searchRadius.getValue(),2);
	  double interceptNormSquared = Math.pow(theLine.intercept.norm() ,2);
	  double currentIntervalBound = Math.sqrt(searchRadiusSquared - interceptNormSquared);
	  quadraticSolve(theLine, theRoots);
	  if (theRoots[0] == 2.0)
	  {   if ( (Math.abs(theRoots[1]) <= currentIntervalBound) )
		     { points.add(new Vector3D(theLine.parametricEquation(theRoots[1])));} 
		  if ( (Math.abs(theRoots[2]) <= currentIntervalBound))
			{ points.add(new Vector3D(theLine.parametricEquation(theRoots[2])));} 
	  }
   }
 
  /**
   * This finds the points of intersection of a line (theLine) and the surface in the case that the surface is cubic.
   *
   */
  private void IntersectLineWithImplicitCubicSurface(Line3D theLine, double[] theRoots, ArrayList<Vector3D> points) {
	     // modified by Eck to place points into an array list instead of directly into the pointCloud array
	  double searchRadiusSquared = Math.pow(searchRadius.getValue(),2);
	  double interceptNormSquared = Math.pow(theLine.intercept.norm() ,2);
	  double currentIntervalBound = Math.sqrt(searchRadiusSquared - interceptNormSquared);
	  cubicSolve(theLine, theRoots);
	  //System.out.println(theRoots[0]);
	  if (theRoots[0] > 0.0) 
	    { if ( (Math.abs(theRoots[1]) <= currentIntervalBound) )
			{ points.add(new Vector3D(theLine.parametricEquation(theRoots[1])));} }
	  if (theRoots[0] > 1.0) 
	    { if ( (Math.abs(theRoots[2]) <= currentIntervalBound))
			{ points.add(new Vector3D(theLine.parametricEquation(theRoots[2])));} }
	  if (theRoots[0] > 2.0) 
	    {  if ( (Math.abs(theRoots[3]) <= currentIntervalBound))
			{ points.add(new Vector3D(theLine.parametricEquation(theRoots[3])));} } 
   }
  
  
  private void IntersectLineWithImplicitQuarticSurface(Line3D theLine, double[] theRoots, ArrayList<Vector3D> points) {
	     // modified by Eck to place points into an array list instead of directly into the pointCloud array
	  double searchRadiusSquared = Math.pow(searchRadius.getValue(),2);
	  double interceptNormSquared = Math.pow(theLine.intercept.norm() ,2);
	  double currentIntervalBound = Math.sqrt(searchRadiusSquared - interceptNormSquared);
	  quarticSolve(theLine, theRoots);
	 // System.out.println(theRoots[0]);
	  if (theRoots[0] > 0.0)  
         { if ( (Math.abs(theRoots[1]) <= currentIntervalBound) )
			{ points.add(new Vector3D(theLine.parametricEquation(theRoots[1])));} }
	  if (theRoots[0] > 1.0) 
	    { if (  (Math.abs(theRoots[2]) <= currentIntervalBound))
	    	{ points.add(new Vector3D(theLine.parametricEquation(theRoots[2])));} }
	  if (theRoots[0] > 2.0) 
	    {  if ( (Math.abs(theRoots[3]) <= currentIntervalBound))
	    	{ points.add(new Vector3D(theLine.parametricEquation(theRoots[3])));} }
	  if (theRoots[0] > 3.0) 
	    {  if ( (Math.abs(theRoots[4]) <= currentIntervalBound))
	    	{ points.add(new Vector3D(theLine.parametricEquation(theRoots[4])));} }

   }
  
 /**
  * This finds the points of intersection of a line (theLine) and the surface.
  *
  */
  private void IntersectLineWithImplicitSurface(Line3D theLine, ArrayList<Vector3D> points) {
	     // modified by Eck to place points into an array list instead of directly into the pointCloud array
      double leftValue, rightValue;
      double searchRadiusSquared = Math.pow(searchRadius.getValue(),2);
      double interceptNormSquared = Math.pow(theLine.intercept.norm() ,2);
      double currentIntervalBound = Math.sqrt(Math.max(0.001,searchRadiusSquared - interceptNormSquared));
      double searchIntervalLength = 2*currentIntervalBound;
      int numSubIntervals = (int)Math.round(40*(currentIntervalBound));// (int)Math.round(25*(currentIntervalBound));
      double subintervalLength = (searchIntervalLength)/numSubIntervals;
      if (subintervalLength > resolution) {
              numSubIntervals = (int)Math.round((searchIntervalLength)/resolution) + 1;
              subintervalLength = searchIntervalLength/numSubIntervals;   }
      Vector3D startVector, dVec;
      startVector = theLine.parametricEquation(-currentIntervalBound -subintervalLength);
      dVec = theLine.parametricEquation(-currentIntervalBound).minus(startVector);
      double theLevel = level.getValue();
      double x1, y1, z1;
      double x2, y2, z2;
      double dx, dy, dz;
      double t1,t2,dt;
      dx = dVec.x;
      dy = dVec.y;
      dz = dVec.z;
      x1 = startVector.x;
      y1 = startVector.y;
      z1 = startVector.z;
      leftValue = heightFunction(x1, y1, z1) - theLevel;
      t1 = -currentIntervalBound - subintervalLength;
      dt = subintervalLength;

       for (int i = 0; i <= numSubIntervals; i++) {
              t2 = t1 + dt;
              x2 = x1 + dx;
              y2 = y1 + dy;
              z2 = z1 + dz;
              rightValue = heightFunction(x2, y2, z2) - theLevel;
              if (Math.signum(leftValue) != Math.signum(rightValue)) {
                      double u  = ( t1*rightValue - t2*leftValue ) / (rightValue -leftValue); //Secant root
                      points.add(theLine.parametricEquation(u));
              }
              leftValue = rightValue;
              x1 = x2;
              y1 = y2;
              z1 = z2;
              t1 = t2;
       }
}
  
  private class FindPointsOnPointCloud implements Runnable {  // Tasks used in point cloud parallelization.
	  int startIndex, endIndex;
	  ArrayList<Vector3D> points = new ArrayList<Vector3D>();
	  boolean finished;
	  CollectPointsForPointCloud owner;
	  FindPointsOnPointCloud(CollectPointsForPointCloud owner, int startIndex, int endIndex) {
		  this.owner = owner;
		  this.startIndex = startIndex;
		  this.endIndex = endIndex;
	  }
	  boolean isFinished() {
		  synchronized(owner) {
			  return finished;
		  }
	  }
	  public void run() {
		  double[] theRoots = new double[5];
		  for (int i = startIndex; i <= endIndex; i++) {
		  		 theRoots[ 0 ] = 0;
		  		 theRoots[ 1 ] = 10000;
		  		 theRoots[ 2 ] = 10000;
		  		 theRoots[ 3 ] = 10000;
		  		 theRoots[ 4 ] = 10000;
		  		if (heightFunctionType == equationType.QUADRATIC)
		  		   IntersectLineWithImplicitQuadraticSurface(ListOfRandomLines[i],theRoots,points);
		  		else if (heightFunctionType == equationType.CUBIC)
		  		   IntersectLineWithImplicitCubicSurface(ListOfRandomLines[i],theRoots,points);
		  		else if (heightFunctionType == equationType.QUARTIC)
		  	  	   IntersectLineWithImplicitQuarticSurface(ListOfRandomLines[i],theRoots,points);
		  		else
		            IntersectLineWithImplicitSurface(ListOfRandomLines[i], points);
		  }
		  synchronized(owner) {
			  finished = true;
			  owner.taskCompleted();
		  }
	  }
  }
  
  private class CollectPointsForPointCloud {  // Support for point cloud parallelization. Do I really need a class for this????
	  TaskManager.Job findPointsJob;
	  ArrayList<FindPointsOnPointCloud> tasks;
	  int tasksCompleted;
	  CollectPointsForPointCloud(ArrayList<FindPointsOnPointCloud> tasks, TaskManager.Job job) {
		  this.tasks = tasks;
		  this.findPointsJob = job;
		  numberOfPointsInCloud = 0;
	  }
	  synchronized void taskCompleted() { 
		  if (findPointsJob.isFinished())
			  return;
		  while (tasks.get(tasksCompleted).isFinished()) {
			  ArrayList<Vector3D> points = tasks.get(tasksCompleted).points;
			  for (Vector3D point : points) {
				  pointCloud[numberOfPointsInCloud++] = point;
				  if (numberOfPointsInCloud == pointCloud.length) {
					  findPointsJob.cancel();
					  break;
				  }
			  }
			  tasksCompleted++;
			  if (tasksCompleted >= tasks.size())
				  break;
		  }
	  }
  }
  
  
  private void createPointCloud() {
	  pointCloud = new Vector3D[pointCloudCount.getValue()];
	  TaskManager.Job job = taskManager.createJob();
	  int startIndex = 1;
	  ArrayList<FindPointsOnPointCloud> tasks = new ArrayList<FindPointsOnPointCloud>();
	  CollectPointsForPointCloud taskOwner = new CollectPointsForPointCloud(tasks,job);
	  while (startIndex < ListOfRandomLines.length) {
		  int endIndex = startIndex + 499;
		  if (endIndex > ListOfRandomLines.length)
			  endIndex = ListOfRandomLines.length;
		  FindPointsOnPointCloud task = new FindPointsOnPointCloud(taskOwner, startIndex, endIndex);
		  tasks.add(task);
		  startIndex = endIndex + 1;
	  }
	  synchronized(taskOwner) {
		     // Had to add syncrhonization, or sometimes job completed and was canceled before all tasks were added, giving an error!
		  for (Runnable task : tasks)
			  job.add(task);
	  }
	  job.close();
	  job.await(0);
  }
  
	protected void computeDrawData3D(View3D view, boolean exhibitNeedsRedraw, Transform3D previousTransform3D, Transform3D newTransform3D) {
		if (exhibitNeedsRedraw || pointCloud == null || ListOfRandomLines == null) {
			if (! (view instanceof ImplicitSurfaceView) || ! ((ImplicitSurfaceView)view).getUseRaytraceRendering() ) {
				// make data for point cloud representation -- the data for the ray-traced image is the image itself
				view.getDisplay().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				MakeListOfRandomLines();
				createPointCloud();
				view.getDisplay().setCursor(Cursor.getDefaultCursor());
			}
		}
	}
	
	protected void doDraw3D(Graphics2D g, View3D view, Transform3D transform) {
		if ( ! (view instanceof ImplicitSurfaceView) || ! ((ImplicitSurfaceView)view).getUseRaytraceRendering() )
			view.drawPixels(pointCloud);
		else {// Need to draw a ray-traced rendering.
			((ImplicitSurfaceView)view).killRayTraceJob();  // stop on-going async tracing computation, if any
			((ImplicitSurfaceView)view).drawRayTracedSurface();
		}
	}

	public View getDefaultView() {
		return new ImplicitSurfaceView();
	}
	
	
	public BasicAnimator getMorphingAnimation(final View view, int looping) {
		if (! (view instanceof ImplicitSurfaceView))
			return super.getMorphingAnimation(view, looping);
		Parameter[] parameters = view.getViewAndExhibitParameters();
		if (parameters == null)
			return null;
		boolean animated = false;
		for (int i = 0; i < parameters.length; i++)
			if (parameters[i] instanceof Animateable && ((Animateable)parameters[i]).reallyAnimated()) {
				animated = true;
				break;
			}
		if ( ! animated )
			return null;
		final BasicAnimator anim = new BasicAnimator(getFramesForMorphing()) {
			public void animationStarting() {
				((ImplicitSurfaceView)view).killRayTraceJob();
				morphingView = view;
				isMorphing = true;
			}
			public void animationEnding() {
				morphingView = null;
				isMorphing = false;
			}
			protected void nextFrame(ActionEvent evt) {
				if (((ImplicitSurfaceView)view).isBuildingImage())
					return;  // ignore events that occur while a ray-traced image is being created
				super.nextFrame(evt);
			}
		};
		if (getUseFilmstripForMorphing()) {
			anim.setUseFilmstrip(true);
			anim.setMillisecondsPerFrame(100);
		}
		for (int i = 0; i < parameters.length; i++)
			if (parameters[i] instanceof Animateable)
				anim.addAnimatedItem((Animateable)parameters[i]);
		anim.setLooping(looping);
		return anim;
	}

	
	public class ImplicitSurfaceView extends View3DWithLightSettings{  //this inner class definition continues to the end of the file!
		@VMMSave boolean useRaytraceRendering = false;
		protected ActionRadioGroup renderSelect;
		public ImplicitSurfaceView() {
			setViewStyle(View3D.RED_GREEN_STEREO_VIEW);
			renderSelect = new ActionRadioGroup() {
				public void optionSelected(int selectedIndex) {
					setUseRaytraceRendering(selectedIndex == 1);
				}
			};
			renderSelect.addItem(I18n.tr("vmm.surface.implicit.PointCloud"));
			renderSelect.addItem(I18n.tr("vmm.surface.implicit.RayTrace"));
			renderSelect.setSelectedIndex(0);
			LightSettings ls = new LightSettings();
			ls.setSpecularExponent(80);
			ls.setSpecularRatio(0.25F);
			setAnaglyphLightSettings(ls);
			setNonAnaglyphLightSettings(new LightSettings(LightSettings.HIGH_SPECULARITY_DEFAULT));
		}
		public boolean getUseRaytraceRendering() {
			return useRaytraceRendering;
		}
		public void setUseRaytraceRendering(boolean useRaytraceRendering) {
			if (this.useRaytraceRendering == useRaytraceRendering)
				return;
			if (getDisplay() != null)
				getDisplay().stopAnimation();
			killRayTraceJob();
			this.useRaytraceRendering = useRaytraceRendering;
			renderSelect.setSelectedIndex( useRaytraceRendering ? 1 : 0 );
			String originalAnaglyph = Prefs.get("view3d.initialAnaglyphMode", "default");
			if (useRaytraceRendering) {
				if (getViewStyle() == View3D.RED_GREEN_STEREO_VIEW && ! originalAnaglyph.equalsIgnoreCase("always"))
					setViewStyle(View3D.MONOCULAR_VIEW);
				setOrthographicProjection(false);
				projectionCommands.setEnabled(false);
			}
			else {
				if (getViewStyle() == View3D.MONOCULAR_VIEW && ! originalAnaglyph.equalsIgnoreCase("never"))
					setViewStyle(View3D.RED_GREEN_STEREO_VIEW);
				projectionCommands.setEnabled(true);
			}
			forceRedraw();
		}
		public void setExhibit(Exhibit exhibit) {
			killRayTraceJob();
			super.setExhibit(exhibit);
		}
		public ActionList getActions() {
			ActionList actions = super.getActions();
			actions.add(renderSelect);
			return actions;
		}
		public ActionList getSettingsCommands() {
			ActionList actions = super.getSettingsCommands();
			actions.remove(lightingEnabledToggle);
			return actions;
		}
		boolean isBuildingImage() {
			return buildingImageForFilmstrip;
		}
		protected void drawRayTracedSurface() {
			if (getViewStyle() == View3D.MONOCULAR_VIEW)
				drawRayTracedDirect(7);
			else {
				setUpForLeftEye();
				drawRayTracedDirect(7);
				setUpForRightEye();
				drawRayTracedDirect(7);
				finishStereoView();
			}
			if (!getFastDrawing())
				startRayTraceJob();
		}
		
		protected Vector3D GetFirstIntersectionsOfLineWithQuadraticSurface(Line3D theLine,double[] theRoots) {
			  if (theLine.intercept.norm() > searchRadius.getValue()) return new Vector3D(-12345,0,0);
			  Vector3D theFirstIntersection = new Vector3D();
			  theFirstIntersection = null;
			  double searchRadiusSquared = Math.pow(searchRadius.getValue(),2);
			  double interceptNormSquared = Math.pow(theLine.intercept.norm() ,2);
			  double currentIntervalBound = Math.sqrt(Math.max(0.00001,searchRadiusSquared - interceptNormSquared));
			  quadraticSolve(theLine,theRoots);
			  if  (theRoots[0] == 0.0) return new Vector3D(-12345,0,0);
			  if ( (Math.abs(theRoots[1]) > currentIntervalBound) && (Math.abs(theRoots[2]) > currentIntervalBound) ) 
					    return new Vector3D(-12345,0,0);
			    
			   if (Math.abs(theRoots[1]) < currentIntervalBound)
			    	theFirstIntersection = theLine.parametricEquation(theRoots[1]);
			    else 
			    	//return new Vector3D(-12345,0,0);
			    		theFirstIntersection = theLine.parametricEquation(theRoots[2]);
			 return theFirstIntersection;
		  }
		
		protected Vector3D GetFirstIntersectionsOfLineWithCubicSurface(Line3D theLine, double[] theRoots) {
			  if (theLine.intercept.norm() > searchRadius.getValue()) return new Vector3D(-12345,0,0);
			  Vector3D theFirstIntersection = new Vector3D();
			  theFirstIntersection = null;
			  double searchRadiusSquared = Math.pow(searchRadius.getValue(),2);
			  double interceptNormSquared = Math.pow(theLine.intercept.norm() ,2);
			  double currentIntervalBound = Math.sqrt(Math.max(0.00001,searchRadiusSquared - interceptNormSquared));
			  cubicSolve(theLine,theRoots);
			  if  (theRoots[0] == 0.0) return new Vector3D(-12345,0,0);
			  if ( (Math.abs(theRoots[1]) > currentIntervalBound) && 
				   (Math.abs(theRoots[2]) > currentIntervalBound) &&
				   (Math.abs(theRoots[3]) > currentIntervalBound)) 
					    return new Vector3D(-12345,0,0);
			    
			   if (Math.abs(theRoots[1]) < currentIntervalBound)
			    	theFirstIntersection = theLine.parametricEquation(theRoots[1]);
			   else 
			   if (Math.abs(theRoots[2]) < currentIntervalBound)   
			    	theFirstIntersection = theLine.parametricEquation(theRoots[2]);
			   else
				    theFirstIntersection = theLine.parametricEquation(theRoots[3]);
			   
			 return theFirstIntersection;
		  }
		
		protected Vector3D GetFirstIntersectionsOfLineWithQuarticSurface(Line3D theLine, double[] theRoots) {
			  if (theLine.intercept.norm() > searchRadius.getValue()) return new Vector3D(-12345,0,0);
			  Vector3D theFirstIntersection = new Vector3D();
			  theFirstIntersection = null;
			  double searchRadiusSquared = Math.pow(searchRadius.getValue(),2);
			  double interceptNormSquared = Math.pow(theLine.intercept.norm() ,2);
			  double currentIntervalBound = Math.sqrt(Math.max(0.00001,searchRadiusSquared - interceptNormSquared));
			  quarticSolve(theLine,theRoots);
			  if  (theRoots[0] == 0.0) return new Vector3D(-12345,0,0);
		/**	  if ( (Math.abs(theRoots[1]) > currentIntervalBound) && 
				   (Math.abs(theRoots[2]) > currentIntervalBound)  &&
				   (Math.abs(theRoots[3]) > currentIntervalBound)  &&
				   (Math.abs(theRoots[4]) > currentIntervalBound))  
					    return new Vector3D(-12345,0,0); */
			    
			  if (Math.abs(theRoots[1]) < currentIntervalBound)
			    	theFirstIntersection = theLine.parametricEquation(theRoots[1]);
			   else 
			   if (Math.abs(theRoots[2]) < currentIntervalBound)   
			    	theFirstIntersection = theLine.parametricEquation(theRoots[2]);
			   else
			   if (Math.abs(theRoots[3]) < currentIntervalBound) 
			        theFirstIntersection = theLine.parametricEquation(theRoots[3]);
			   else
			   if (Math.abs(theRoots[4]) < currentIntervalBound) 
				    theFirstIntersection = theLine.parametricEquation(theRoots[4]);
			   else
				   theFirstIntersection = new Vector3D(-12345,0,0);
			  
			return theFirstIntersection;
		  }
		
		protected Vector3D GetFirstIntersectionsOfLineWithSurface(Line3D theLine) {
            if (theLine.intercept.norm() > searchRadius.getValue()) return new Vector3D(-12345,0,0);
            double leftValue, rightValue;
            double searchRadiusSquared = Math.pow(searchRadius.getValue(),2);
            double interceptNormSquared = Math.pow(theLine.intercept.norm() ,2);
            double currentIntervalBound = Math.sqrt(Math.max(0.001,searchRadiusSquared - interceptNormSquared));
            double searchIntervalLength = 2*currentIntervalBound;
            int numSubIntervals = (int)Math.round(40*(currentIntervalBound));// (int)Math.round(25*(currentIntervalBound));
            double subintervalLength = (searchIntervalLength)/numSubIntervals;
            resolution = rayTraceResolution.getValue();
            if (subintervalLength > resolution) {
                    numSubIntervals = (int)Math.round((searchIntervalLength)/resolution) + 1;
                    subintervalLength = searchIntervalLength/numSubIntervals;   }
            Vector3D startVector, dVec;
            startVector = theLine.parametricEquation(-currentIntervalBound - subintervalLength);
            dVec = theLine.parametricEquation(-currentIntervalBound).minus(startVector);
            double theLevel = level.getValue();
            double x1, y1, z1;
            double x2, y2, z2;
            double dx, dy, dz;
            double t1,t2,dt;
            dx = dVec.x;
            dy = dVec.y;
            dz = dVec.z;
            x1 = startVector.x;
            y1 = startVector.y;
            z1 = startVector.z;
            leftValue = heightFunction(x1, y1, z1) - theLevel;
            t1 = -currentIntervalBound - subintervalLength;
            dt = subintervalLength;
             for (int i = 0; i <= numSubIntervals; i++) {
                    t2 = t1 + dt;
                    x2 = x1 + dx;
                    y2 = y1 + dy;
                    z2 = z1 + dz;
                    rightValue = heightFunction(x2, y2, z2) - theLevel;
                    if (Math.signum(leftValue) != Math.signum(rightValue)) {
                            double u  = ( t1*rightValue - t2*leftValue ) / (rightValue -leftValue); //Secant root
                           return theLine.parametricEquation(u);
                    }
                    leftValue = rightValue;
                    x1 = x2;
                    y1 = y2;
                    z1 = z2;
                    t1 = t2;
             }
             return new Vector3D(-12345,0,0);
    }

	
	
		private void drawRayTracedDirect(int theResolution) {
            int width = transform3D.getWidth();
            int height = transform3D.getHeight();
            double centerOffset = theResolution * 0.5;  // offset from (x,y) to center of patch
            Point2D pt = new Point2D.Double();
            Vector3D viewPt = getViewPoint();
            Vector3D xDir = transform3D.getImagePlaneXDirection();
            Vector3D yDir = transform3D.getImagePlaneYDirection();
            Color bgColor = getBackground();
            if (getViewStyle() == View3D.RED_GREEN_STEREO_VIEW)
            	bgColor = Color.BLACK;
            double[] theRoots = new double[5];
            for (int y = 0; y < height; y += theResolution)
                for (int x = 0; x < width; x += theResolution) 
                    {
                	 theRoots[ 0 ] = 0;
             		 theRoots[ 1 ] = 10000;
             		 theRoots[ 2 ] = 10000;
             		 theRoots[ 3 ] = 10000;
             		 theRoots[ 4 ] = 10000;
                     pt.setLocation(x+centerOffset,y+centerOffset);
                     transform3D.viewportToWindow(pt);
                     Vector3D Xcomponent = xDir.times(pt.getX());
                     Vector3D Ycomponent = yDir.times(pt.getY());
                     Vector3D worldPoint = Xcomponent.plus(Ycomponent);
                     // At this point, pt.getX() is the x-coord of the point on the screen, and
                     // pt.getY() is the y-coord.  These are in viewing coordinates, not world
                     // coordinates, and need to be transformed back to (x,y,z) in world coords.
                     // worldPoint is a Vector3D whose 3 components give this (x,y,z) .
                     // Then, cast a ray from the viewpoint and find the appropriate color.
                     Vector3D directionToPixel = worldPoint.minus(viewPt).normalized();
                     double ProjOfViewPtOnDirection = viewPt.dot(directionToPixel);
                     Vector3D intercpt = viewPt.minus(directionToPixel.times(ProjOfViewPtOnDirection));
                     Line3D lineFromViewptToPixel = new Line3D(intercpt,directionToPixel);
                     Vector3D FirstIntersection;
                     if (heightFunctionType == equationType.QUADRATIC)
                        FirstIntersection = GetFirstIntersectionsOfLineWithQuadraticSurface(lineFromViewptToPixel, theRoots);
                     else 
                    if (heightFunctionType == equationType.CUBIC)
                       FirstIntersection = GetFirstIntersectionsOfLineWithCubicSurface(lineFromViewptToPixel, theRoots);
                    else 
                    if (heightFunctionType == equationType.QUARTIC)
                           FirstIntersection = GetFirstIntersectionsOfLineWithQuarticSurface(lineFromViewptToPixel, theRoots);
                     else
                        FirstIntersection = GetFirstIntersectionsOfLineWithSurface(lineFromViewptToPixel);
                     // now compute the color c of the pixel
                     Color c = bgColor; // this will give background color unless an intersection exists
                     if  ( !(FirstIntersection.x == -12345.0) )   // intersection exists
                        { 
                    	  Vector3D theNormal = normalToSurfaceAt(FirstIntersection);
                          c = PhongLighting.phongLightingColor
                            (  theNormal,   // normal to surface at FirstIntersection.
                               this,        // the View3DLit containing the LightSettings.
                               transform3D,  // Contains Viewpoint, etc.
                               FirstIntersection,  // point on surface where color is desired.
                               Color.WHITE         // intrinsic color of surface at FirstIntersection    
                            );
                        }  //end of if clause
                            if (theResolution == 1)  drawPixelDirect(c, x, y);
                            else {setColor(c);
                                 fillRectDirect(x, y, theResolution, theResolution); }
                 }  // End of inner and outer for loops
            
		}  // End of drawRayTraceDirect
		
		private int rayTraceJobNum;
		private TaskManager.Job currentRayTraceJob;
		private RayTraceThread rayTraceThread;
		private Graphics leftAnaglyphGraphics, rightAnaglyphGraphics;
		
		synchronized private void killRayTraceJob() {
			if (currentRayTraceJob == null)
				return;
			buildingImageForFilmstrip = false;
			if (!currentRayTraceJob.isFinished())
				currentRayTraceJob.cancel();
			rayTraceThread = null;
			currentRayTraceJob = null;
			rayTraceJobNum++;
			if (leftAnaglyphGraphics != null) {
				leftAnaglyphGraphics.dispose();
				rightAnaglyphGraphics.dispose();
				leftAnaglyphGraphics = rightAnaglyphGraphics = null;
			}
		}
		synchronized private void startRayTraceJob() {
			assert currentRayTraceJob == null;
			BufferedImage image1, image2;
			Transform3D transform1, transform2;
			if (getViewStyle() == View3D.MONOCULAR_VIEW) {
				transform1 = (Transform3D)getTransform3D().clone();
				transform2 = null;
				image1 = fullOSI;
				image2 = null;
			}
			else {
				setUpForLeftEye();
				transform1 = (Transform3D)getTransform3D().clone();
				setUpForRightEye();
				transform2 = (Transform3D)getTransform3D().clone();
				finishStereoView();
				if (getViewStyle() == View3D.RED_GREEN_STEREO_VIEW) {
					image1 = stereoComposite.getLeftEyeImage();
					image2 = stereoComposite.getRightEyeImage();
					leftAnaglyphGraphics = image1.getGraphics();
					rightAnaglyphGraphics = image2.getGraphics();
				}
				else {
					image1 = leftStereographOSI;
					image2 = rightStereographOSI;
				}
			}
			if (taskManager == null)
				taskManager = new TaskManager();
			currentRayTraceJob = taskManager.createJob();
			int rows = transform1.getHeight();
			for (int y = 0; y < rows; y++)
				currentRayTraceJob.add(new RayTraceTask(rayTraceJobNum,transform1,transform2,image1,image2,y));
			currentRayTraceJob.close();
			buildingImageForFilmstrip = true;
			rayTraceThread = new RayTraceThread(currentRayTraceJob,rayTraceJobNum);
			rayTraceThread.start();
		}
		synchronized private void finishRayTraceTask(RayTraceTask task) {
			if (task.jobID != rayTraceJobNum)
				return;
			if (leftAnaglyphGraphics != null) {
				for (int i = 0; i < task.width; i++) {
					leftAnaglyphGraphics.setColor(new Color(task.rgb1[i]));
					leftAnaglyphGraphics.fillRect(i,task.y,1,1);
				}
				for (int i = 0; i < task.width; i++) {
					rightAnaglyphGraphics.setColor(new Color(task.rgb2[i]));
					rightAnaglyphGraphics.fillRect(i,task.y,1,1);
				}
			}
			else {
				task.image1.setRGB(0, task.y, task.width, 1, task.rgb1, 0, task.width);
				if (task.image2 != null)
					task.image2.setRGB(0, task.y, task.width, 1, task.rgb2, 0, task.width);
			}
		}
		private class RayTraceThread extends Thread {
			TaskManager.Job job;
			int jobID;
			RayTraceThread(TaskManager.Job job, int jobID) {
				this.job = job;
				this.jobID = jobID;
			}
			public void run() {
				try {
					while (true) {
						boolean done = job.await(500);
						synchronized(ImplicitSurfaceView.this) {
							if (jobID != rayTraceJobNum)
								break;
							if (done)
								buildingImageForFilmstrip = false;
							Display d = getDisplay();
							if (d != null) {
								Decoration[] decs1 = getDecorations();
								Exhibit exhibit = getExhibit();
								Decoration[] decs2 = exhibit == null ? new Decoration[] {} : exhibit.getDecorations();
								if (decs1.length > 0 || decs2.length > 0) {
									Graphics2D g = prepareOSIForDrawing();
									if (g != null) { 
										for (Decoration dec : decs1)
											dec.doDraw(g,ImplicitSurfaceView.this,getTransform());
										if (decs2 != null) {
											for (Decoration dec : decs2)
												dec.doDraw(g,ImplicitSurfaceView.this,getTransform());
										}
										finishOSIDraw();
									}
								}
								if (getViewStyle() == RED_GREEN_STEREO_VIEW) {
									try {
										stereoComposite.compose();
									}
									catch (NullPointerException e) {
										// can occur if the view style has changed
									}
								}
								d.repaint();
							}
						}
						if (done)
							break;
					}
				}
				finally {
					synchronized(ImplicitSurfaceView.this) {
						if (jobID == rayTraceJobNum)
							killRayTraceJob();
					}
				}
			}
		}
		private class RayTraceTask implements Runnable {
			Transform3D transform1;
			Transform3D transform2;
			BufferedImage image1;
			BufferedImage image2;
			int y;
			int width;
			int jobID;
			int[] rgb1;
			int[] rgb2;
			RayTraceTask(int jobID, Transform3D transform1, Transform3D transform2, 
					                  BufferedImage image1, BufferedImage image2, int y) {
				this.transform1 = transform1;
				this.transform2 = transform2;
				this.image1 = image1;
				this.image2 = image2;
				this.y = y;
				this.jobID = jobID;
			}
			void compute(int[] rgb, ImplicitSurfaceView view, Transform3D transform) {
	            Point2D pt = new Point2D.Double();
	            Vector3D viewPt = transform.getViewPoint();
	            Vector3D xDir = transform.getImagePlaneXDirection();
	            Vector3D yDir = transform.getImagePlaneYDirection();
	            Color bgColor = getBackground();
	            if (getViewStyle() == View3D.RED_GREEN_STEREO_VIEW)
	            	bgColor = Color.BLACK;
	            int bgColorRGB = bgColor.getRGB();
	            double[] theRoots = new double[5];
                for (int x = 0; x < width; x++)  {
	               	 theRoots[ 0 ] = 0;
	         		 theRoots[ 1 ] = 10000;
	         		 theRoots[ 2 ] = 10000;
	         		 theRoots[ 3 ] = 10000;
	         		 theRoots[ 4 ] = 10000;
                     pt.setLocation(x+0.5,y+0.5);
                     transform.viewportToWindow(pt);
                     Vector3D Xcomponent = xDir.times(pt.getX());
                     Vector3D Ycomponent = yDir.times(pt.getY());
                     Vector3D worldPoint = Xcomponent.plus(Ycomponent);
                     Vector3D directionToPixel = worldPoint.minus(viewPt).normalized();
                     double ProjOfViewPtOnDirection = viewPt.dot(directionToPixel);
                     Vector3D intercpt = viewPt.minus(directionToPixel.times(ProjOfViewPtOnDirection));
                     Line3D lineFromViewptToPixel = new Line3D(intercpt,directionToPixel);
                     Vector3D FirstIntersection;
                     if (heightFunctionType == equationType.QUADRATIC)
                        FirstIntersection = GetFirstIntersectionsOfLineWithQuadraticSurface(lineFromViewptToPixel,theRoots);
                     else 
                     if (heightFunctionType == equationType.CUBIC)
                        FirstIntersection = GetFirstIntersectionsOfLineWithCubicSurface(lineFromViewptToPixel,theRoots);
                     else 
                     if (heightFunctionType == equationType.QUARTIC)
                            FirstIntersection = GetFirstIntersectionsOfLineWithQuarticSurface(lineFromViewptToPixel,theRoots);
                     else
                        FirstIntersection = GetFirstIntersectionsOfLineWithSurface(lineFromViewptToPixel);
                     int cRGB = bgColorRGB; // this will give background color unless an intersection exists
                     if  ( !(FirstIntersection.x == -12345.0) )   // intersection exists
                        { 
                    	  Vector3D theNormal = normalToSurfaceAt(FirstIntersection);
                          Color c = PhongLighting.phongLightingColor
                            (  theNormal,   // normal to surface at FirstIntersection.
                               view,        // the View3DLit containing the LightSettings.
                               getTransform3D(),   // the transform containing the viewpoint, etc.
                               FirstIntersection,  // point on surface where color is desired.
                               Color.WHITE         // intrinsic color of surface at FirstIntersection    
                            );
                          cRGB = c.getRGB();
                        }  //end of if clause
                     rgb[x] = cRGB;
                 }  // End of for loop
			}
			public void run() {
				width = transform1.getWidth();
				rgb1 = new int[width];
				compute(rgb1,ImplicitSurfaceView.this,transform1);
				if (transform2 != null) {
					rgb2 = new int[width];
					compute(rgb2, ImplicitSurfaceView.this,transform2);
				}
				finishRayTraceTask(this);
			}
		} // end class RayTraceTask
	}  // End of declaration of inner class ImplicitSurfaceView

}   // End of class declaration for SurfaceImplicit


	