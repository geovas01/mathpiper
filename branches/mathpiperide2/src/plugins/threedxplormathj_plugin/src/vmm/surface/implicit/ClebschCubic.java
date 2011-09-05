/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.implicit;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import vmm.actions.ActionList;
import vmm.actions.ToggleAction;
import vmm.core.Decoration;
import vmm.core.I18n;
import vmm.core.Transform;
import vmm.core.VMMSave;
import vmm.core.View;
import vmm.core3D.Transform3D;
import vmm.core3D.Vector3D;
import vmm.core3D.View3D;

public class ClebschCubic extends SurfaceImplicit {

	double sqr(double x){return x*x;}
	static double sqrt(double x) { return Math.sqrt(x); }
	static double cube(double x){return x*x*x;}
	

	public double heightFunction(double x, double y, double z) {
		double height;
		double xsqr =sqr(x); double ysqr = sqr(y); double zsqr = sqr(z);  
		double xcube = x*xsqr; double ycube = y*ysqr;  double zcube = z*zsqr;
		height = -(81*(xcube + ycube + zcube) - 
                189*(xsqr*y + xsqr*z + ysqr*x + ysqr*z + zsqr*x + zsqr*y) + 
                54 * x * y * z + 
                126*(x * y + x * z + y * z) -
                9 * (xsqr + ysqr + zsqr) -
                9*(x + y + z) + 1);
		return height;
	}
	
	public ClebschCubic(){
		setDefaultWindow(-3.5, 3.5, -3.5, 3.5);
		setDefaultViewpoint(new Vector3D(26.5,-7.25,-7.25));
		setDefaultViewUp(new Vector3D(0.3608,0.6595,0.6595));
		searchRadius.reset(3);
		randomLineCount.reset(60000);
		pointCloudCount.reset(12000);
		level.reset(0);
		setFramesForMorphing(11);
		InitializeTwentySevenLineArray();
		heightFunctionType = equationType.CUBIC;
	}
	
	public View getDefaultView() {
		return new ClebschView();
	}
	
	// The remainder of the class is concerned with drawing the 27 lines on the Clebsch Cubic.
	
	private class LinesOnCubic extends Decoration {

		Vector3D[][] lines = new Vector3D[27][2];
		ArrayList<Vector3D[]> lineSegmentsToDraw = new ArrayList<Vector3D[]>();  // also for lines for left stereo image
		ArrayList<Vector3D[]> lineSegmentsToDrawForRightStereo = new ArrayList<Vector3D[]>();
		double computedForSearchRadius;
		boolean computedForRayTrace;
		boolean computedForMonocularRayTrace;
		
		public void computeDrawData(View view, boolean exhibitNeedsRedraw, Transform previousTransform, Transform newTransform) {
			synchronized(view) { // synchrnization needed when view style changes during ray-traced rendering.
				ClebschView cv = (ClebschView)view; // This decoration can only occur in a ClebschView !
				boolean needCompute = lineSegmentsToDraw.size() == 0;
				if (computedForSearchRadius != searchRadius.getValue()) {
					computedForSearchRadius = searchRadius.getValue();
					needCompute = true;
					for (int i = 0; i < 27; i++)
						lines[i] = findVisibleSegmentOfClebschLine(i+1, (View3D)view);
				}
				boolean rayTraced = cv.getUseRaytraceRendering();
				if (computedForRayTrace != rayTraced) {
					computedForRayTrace = rayTraced;
					needCompute = true;
				}
				boolean monocular = rayTraced && (cv.getViewStyle() == View3D.MONOCULAR_VIEW);
				if (monocular != computedForMonocularRayTrace) {
					computedForMonocularRayTrace = monocular;
					needCompute = true;
				}
				if (rayTraced && ! newTransform.equals(previousTransform) )
					needCompute = true;
				if (!needCompute)
					return;
				//System.out.println("Compute");
				lineSegmentsToDraw.clear(); 
				lineSegmentsToDrawForRightStereo.clear();
				if (rayTraced) {
					if (computedForMonocularRayTrace) {
						for (Vector3D[] line : lines)
							if (line != null)
								findUnHiddenLineSegments(line[0],line[1],cv,(Transform3D)newTransform, lineSegmentsToDraw);
					}
					else {
						Transform3D[] stereoTransform3D = cv.getStereoViewingTransforms();
						for (Vector3D[] line : lines)
							if (line != null) {
								findUnHiddenLineSegments(line[0],line[1],cv,stereoTransform3D[0], lineSegmentsToDraw);
								findUnHiddenLineSegments(line[0],line[1],cv,stereoTransform3D[1], lineSegmentsToDrawForRightStereo);
							}
					}
				}
				else {
					for (Vector3D[] line : lines)
						if (line != null)
							lineSegmentsToDraw.add(line);
				}
			}
		}

		public void doDraw(Graphics2D g, View view, Transform transform) {
			ClebschView cv = (ClebschView)view;
			synchronized(cv) {
				//System.out.println("Draw " + Math.random());
				boolean rayTraced = cv.getUseRaytraceRendering();
				if (rayTraced)
					cv.setColor(Color.BLACK);
				else if (cv.getViewStyle() == View3D.RED_GREEN_STEREO_VIEW)
					cv.setColor(null);
				else  // non-anaglyph dot-cloud
					cv.setColor(Color.RED);
				if (rayTraced && !computedForMonocularRayTrace) {
					cv.drawLinesOnCubic(lineSegmentsToDraw, lineSegmentsToDrawForRightStereo);
				}
				else {
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					for (Vector3D[] line : lineSegmentsToDraw)
						cv.drawLine(line[0],line[1]);
				}
			}
		}
	}
	
	
	public class ClebschView extends ImplicitSurfaceView { // need this to provide access to stereo viewing transforms
		@VMMSave private boolean drawLines = true;
		private LinesOnCubic linesDecoration;
		ToggleAction drawLinesToggle = new ToggleAction(I18n.tr("vmm.surface.implicit.ClebschCubic.DrawLines"),true) {
			public void actionPerformed(ActionEvent evt) {
				setDrawLines(getState());
			}
		};
		public ClebschView() {
			linesDecoration = new LinesOnCubic();
			addDecoration(linesDecoration);
		}
		public boolean getDrawLines() {
			return drawLines;
		}
		public void setDrawLines(boolean drawLines) {
			if (this.drawLines == drawLines)
				return;
			this.drawLines = drawLines;
			drawLinesToggle.setState(drawLines);
			if (drawLines) {
				linesDecoration = new LinesOnCubic();
				addDecoration(linesDecoration);
			}
			else {
				removeDecoration(linesDecoration);
				linesDecoration = null;
			}
		}
		public ActionList getActions() {
			ActionList actions = super.getActions();
			actions.add(null);
			actions.add(drawLinesToggle);
			return actions;
		}
		Transform3D[] getStereoViewingTransforms() {
			Transform3D leftTransform, rightTransform;
			setUpForLeftEye();
			leftTransform = (Transform3D)getTransform3D().clone();
			setUpForRightEye();
			rightTransform = (Transform3D)getTransform3D().clone();
			finishStereoView();
			return new Transform3D[] { leftTransform, rightTransform };
		}
		void drawLinesOnCubic(ArrayList<Vector3D[]> leftLines, ArrayList<Vector3D[]> rightLines) {
			Point2D p1 = new Point2D.Double();
			Point2D p2 = new Point2D.Double();
			setUpForLeftEye();
			for (Vector3D[] line : leftLines) {
				currentGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				transform3D.objectToDrawingCoords(line[0],p1);
				transform3D.objectToDrawingCoords(line[1],p2);
				currentGraphics.draw(new Line2D.Float(p1,p2));
			}
			setUpForRightEye();
			for (Vector3D[] line : rightLines) {
				currentGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				transform3D.objectToDrawingCoords(line[0],p1);
				transform3D.objectToDrawingCoords(line[1],p2);
				currentGraphics.draw(new Line2D.Float(p1,p2));
			}
			finishStereoView();
		}
	}
	
	
	private double implicitFunctionOfSearchSphereAlongRay(int linenum, double t) {
		Vector3D thePoint = new Vector3D(ClebschLine(linenum,t));
		return sqr(thePoint.norm()) - sqr(searchRadius.getValue());
	}
		

	 private Vector3D[] findVisibleSegmentOfClebschLine(int linenum, View3D view) {
		 boolean lineIntersectsSearchSphere;
		 double A,B,C,D,leftEnd,rightEnd;
		 B = 0.5*( implicitFunctionOfSearchSphereAlongRay(linenum,1) - implicitFunctionOfSearchSphereAlongRay(linenum,-1) );
		 C = implicitFunctionOfSearchSphereAlongRay(linenum,0);
		 A = implicitFunctionOfSearchSphereAlongRay(linenum,1) - B - C;
		 D = B*B-4*A*C;
		 lineIntersectsSearchSphere = (D>0);
		 if (lineIntersectsSearchSphere){
			 leftEnd =  (- B - sqrt(D))/(2*A);
			 rightEnd = (- B + sqrt(D))/(2*A);
			 Vector3D initialPoint = new Vector3D(ClebschLine(linenum, leftEnd));
		     Vector3D finalPoint =   new Vector3D(ClebschLine(linenum, rightEnd));
		     return new Vector3D[] { initialPoint, finalPoint };
        }
		 else 
			 return null;
	 }


	 private void findUnHiddenLineSegments(Vector3D pt1, Vector3D pt2, 
			 ImplicitSurfaceView view, Transform3D transform, ArrayList<Vector3D[]> linesToDraw) {
		 Vector3D viewPoint = transform.getViewPoint();
		 double[] theRoots = new double[5];
		 double pixelSize = transform.getPixelWidth();
		 Point2D xyPt1 = transform.objectToXYWindowCoords(pt1);
		 Point2D xyPt2 = transform.objectToXYWindowCoords(pt2);
		 double distanceOnScreen = sqrt(sqr(xyPt1.getX() - xyPt2.getX()) + sqr(xyPt1.getY() - xyPt2.getY()));
		 int steps = (int)(distanceOnScreen / pixelSize);
		 if (steps > 100)
			 steps = 100;
		 if (steps == 0) {
			 if (! isHidden(pt1,viewPoint,view,transform,theRoots))
				 linesToDraw.add( new Vector3D[] { pt1, pt1 });
			 return;
		 }
		 double pixelsPerStep = distanceOnScreen/steps/pixelSize; 
		 Vector3D dv = pt2.minus(pt1).times(1.0/steps);
		 Vector3D A,B,unHiddenSegmentStart;
		 boolean hiddenA, hiddenB;
		 A = pt1;
		 hiddenA = isHidden(A,viewPoint,view,transform,theRoots);
		 unHiddenSegmentStart = hiddenA ? null : A;
		 for (int i = 1; i <= steps; i++) {
			 B = pt1.plus(dv.times(i));
			 hiddenB = isHidden(B,viewPoint,view,transform,theRoots);
			 if (hiddenA != hiddenB) {
				 Vector3D A1 = A;
				 Vector3D B1 = B;
				 boolean hiddenA1 = hiddenA;
				 double size = pixelsPerStep;
				 Vector3D divisionPt = A1.plus(B1).times(0.5);
				 while (size > 1) {  // get distance between endpoints down to 1 pixel or less
					 boolean hiddenMiddle = isHidden(divisionPt,viewPoint,view,transform,theRoots);
					 if (hiddenMiddle == hiddenA1)
						 A1 = divisionPt;
					 else   // hiddenMiddle == hiddenB1
						 B1 = divisionPt;
					 size /= 2;
					 divisionPt = A1.plus(B1).times(0.5);
				 }
				 if (hiddenB) {// and therefore !hiddenA
					 linesToDraw.add(new Vector3D[] { unHiddenSegmentStart, divisionPt });
					 unHiddenSegmentStart = null;
				 }
				 else // !hiddenB && hiddenA
					 unHiddenSegmentStart = divisionPt;
			 }
			 if (i == steps && !hiddenB)
				 linesToDraw.add(new Vector3D[] {unHiddenSegmentStart, B} );
			 A = B;
			 hiddenA = hiddenB;
		 }
	 }
	 
	 private boolean isHidden(Vector3D point,Vector3D viewPoint, ImplicitSurfaceView view, Transform3D transform, double[] theRoots) {
         Vector3D directionToPixel = point.minus(viewPoint).normalized();
         double projOfViewPtOnDirection = viewPoint.dot(directionToPixel);
         Vector3D intercpt = viewPoint.minus(directionToPixel.times(projOfViewPtOnDirection));
         Line3D lineFromViewptToPixel = new Line3D(intercpt,directionToPixel);
         Vector3D firstIntersection = view.GetFirstIntersectionsOfLineWithCubicSurface(lineFromViewptToPixel, theRoots);
         if (firstIntersection.x == -12345.0)
        	 return false;  // Theoretically, this shouldn't  happen since the point being tested in on the surface.
         Vector3D directionFromIntersectionToPoint = point.minus(firstIntersection);
         if (directionFromIntersectionToPoint.norm() < 0.05) {
        	   // The first intersection point is actually the point that we are testing, so it is not hidden.
        	 return false; 
         }
  	         // In theory, the answer should now be true, since the point being tested is
  	         // supposed to be a point on the surface.  However, to allow for numerical errors,
             // check whehter the first intersection is actually *behind* the point.
		 return ! (directionFromIntersectionToPoint.dot(directionToPixel) < 0);
	 }
	 
	/*  According to Matthias Weber (who calculated them using Mathematica)
       the parametric equations for the 27 lines on the Clebsch Cubic are:

{1     (-1/3, -t, t)          }
{2     (-1/3 + 3*t, 0, t)     }
{3     (-t, -1/3, t)          }
{4     (0, -1/3 + 3*t, t)     }
{5     (0, (1 + 3*t)/9, t)    }
{6     (0, 1/3 - t, t)        }
{7     ((1 + 3*t)/9, 0, t)    }
{8     ( 1/3 - t, 0, t)       }
{9     (1/3, 2/3 - t, t)      }
{10    (2/3 - t, 1/3, t)      }
{11    ((5 - 3*sqrt(5) + 6*sqrt(5)*t)/30, 1/6 - 1/(6*sqrt(5)) + t - (3*t)/sqrt(5), t)        }
{12    ((7 - 3*sqrt(5))/6 + (-3 + sqrt(5))*t,1/2 - sqrt(5)/6 + sqrt(5)*t, t)                 }
{13    ((1 - sqrt(5) + 3*(-5 + 3*sqrt(5))*t)/12, (3 + sqrt(5) + 3*(-3 + sqrt(5))*t)/12, t)   }
{14    ((3 - sqrt(5) - 3*(3 + sqrt(5))*t)/12, (1 + sqrt(5) - 3*(5 + 3*sqrt(5))*t)/12, t)     }
{15    (1/2 - sqrt(5)/6 + sqrt(5)*t, (7 - 3*sqrt(5))/6 + (-3 + sqrt(5))*t, t)                }
{16    (1/6 - 1/(6*sqrt(5)) + t - (3*t)/sqrt(5), (5 - 3*sqrt(5) + 6*sqrt(5)*t)/30, t)        }
{17    ((1 + sqrt(5) - 3*(5 + 3*sqrt(5))*t)/12, (3 - sqrt(5) - 3*(3 + sqrt(5))*t)/12, t)     }
{18    ((3 + sqrt(5) + 3*(-3 + sqrt(5))*t)/12, (1 - sqrt(5) + 3*(-5 + 3*sqrt(5))*t)/12, t)   }
{19    ((3 + sqrt(5) - 6*sqrt(5)*t)/6, (7 + 3*sqrt(5) - 6*(3 + sqrt(5))*t)/6, t)             }
{20    ((5 + sqrt(5))/30 + t + (3*t)/sqrt(5), (5 + 3*sqrt(5) - 6*sqrt(5)*t)/30, t)           }
{21    ((5 + 3*sqrt(5) - 6*sqrt(5)*t)/30, (5 + sqrt(5))/30 + t + (3*t)/sqrt(5), t)           }
{22    ((7 + 3*sqrt(5) - 6*(3 + sqrt(5))*t)/6, (3 + sqrt(5) - 6*sqrt(5)*t)/6, t)             }
{23    (-1/3 + 3*t, t, 0)   }
{24    (-t, t, -1/3)        }
{25    (1/9 + t/3, t, 0)    }
{26    (1/3 - t, t, 0)      }
{27    (2/3 - t, t, 1/3)    }
*/

/*  The i_th Clebsch Line will be given parametrically by:

(TwentySevenLineArray[i][1][1] + TwentySevenLineArray[i[]1][2]t ,
TwentySevenLineArray[i][2][1] + TwentySevenLineArray[i][2][2]t,
TwentySevenLineArray[i][3][1] + TwentySevenLineArray[i]3][2]t)    so   */


double[][][] TwentySevenLineArray = new double[28][4][3];

protected Vector3D ClebschLine(int linenum, double t) {
   Vector3D thePoint = new Vector3D();
   thePoint.x = TwentySevenLineArray[linenum][1][1] + TwentySevenLineArray[linenum][1][2] * t;
   thePoint.y = TwentySevenLineArray[linenum][2][1] + TwentySevenLineArray[linenum][2][2] * t;
   thePoint.z = TwentySevenLineArray[linenum][3][1] + TwentySevenLineArray[linenum][3][2] * t;
   return  thePoint;
 }

protected void InitializeTwentySevenLineArray() {

  TwentySevenLineArray[1][1][1] = -1.0/3.0;           
  TwentySevenLineArray[1][1][2] = 0;
  TwentySevenLineArray[1][2][1] = 0;
  TwentySevenLineArray[1][2][2] = -1;
  TwentySevenLineArray[1][3][1] = 0;
  TwentySevenLineArray[1][3][2] = 1;
 
  TwentySevenLineArray[2][1][1] = -1.0/3.0;            
  TwentySevenLineArray[2][1][2] = 3;
  TwentySevenLineArray[2][2][1] = 0;
  TwentySevenLineArray[2][2][2] = 0;
  TwentySevenLineArray[2][3][1] = 0;
  TwentySevenLineArray[2][3][2] = 1;
 
  TwentySevenLineArray[3][1][1] = 0;                 
  TwentySevenLineArray[3][1][2] = -1;
  TwentySevenLineArray[3][2][1] = -1.0/3.0;
  TwentySevenLineArray[3][2][2] = 0;
  TwentySevenLineArray[3][3][1] = 0;
  TwentySevenLineArray[3][3][2] = 1;
 
  TwentySevenLineArray[4][1][1] = 0;                 
  TwentySevenLineArray[4][1][2] = 0;
  TwentySevenLineArray[4][2][1] = -1.0/3.0;
  TwentySevenLineArray[4][2][2] = 3;
  TwentySevenLineArray[4][3][1] = 0;
  TwentySevenLineArray[4][3][2] = 1;

  TwentySevenLineArray[5][1][1] = 0;                 
  TwentySevenLineArray[5][1][2] = 0;
  TwentySevenLineArray[5][2][1] = 1.0/9.0;
  TwentySevenLineArray[5][2][2] = 1.0/3.0;
  TwentySevenLineArray[5][3][1] = 0;
  TwentySevenLineArray[5][3][2] = 1;
 
  TwentySevenLineArray[6][1][1] = 0;                  
  TwentySevenLineArray[6][1][2] = 0;
  TwentySevenLineArray[6][2][1] = 1.0/3.0;
  TwentySevenLineArray[6][2][2] = -1;
  TwentySevenLineArray[6][3][1] = 0;
  TwentySevenLineArray[6][3][2] = 1;
 
  TwentySevenLineArray[7][1][1] = 1.0/9.0;                 
  TwentySevenLineArray[7][1][2] = 1.0/3.0;
  TwentySevenLineArray[7][2][1] = 0;
  TwentySevenLineArray[7][2][2] = 0;
  TwentySevenLineArray[7][3][1] = 0;
  TwentySevenLineArray[7][3][2] = 1;
 
  TwentySevenLineArray[8][1][1] = 1.0/3.0;                
  TwentySevenLineArray[8][1][2] = -1;
  TwentySevenLineArray[8][2][1] = 0;
  TwentySevenLineArray[8][2][2] = 0;
  TwentySevenLineArray[8][3][1] = 0;
  TwentySevenLineArray[8][3][2] = 1;
 
  TwentySevenLineArray[9][1][1] = 1.0/3.0;               
  TwentySevenLineArray[9][1][2] = 0;
  TwentySevenLineArray[9][2][1] = 2.0/3.0;
  TwentySevenLineArray[9][2][2] = -1;
  TwentySevenLineArray[9][3][1] = 0;
  TwentySevenLineArray[9][3][2] = 1;
 
  TwentySevenLineArray[10][1][1] = 2.0/3.0;               
  TwentySevenLineArray[10][1][2] = -1;
  TwentySevenLineArray[10][2][1] = 1.0/3.0;
  TwentySevenLineArray[10][2][2] = 0;
  TwentySevenLineArray[10][3][1] = 0;
  TwentySevenLineArray[10][3][2] = 1;
 
  TwentySevenLineArray[11][1][1] = 1.0/6.0 - sqrt(5)/10.0 ;  
  TwentySevenLineArray[11][1][2] = sqrt(5)/5.0;
  TwentySevenLineArray[11][2][1] = 1.0/6.0 - 1/(6*sqrt(5));
  TwentySevenLineArray[11][2][2] = 1 - 3.0/sqrt(5);
  TwentySevenLineArray[11][3][1] = 0;
  TwentySevenLineArray[11][3][2] = 1;
 
  TwentySevenLineArray[12][1][1] = (7 - 3*sqrt(5))/6.0;  
  TwentySevenLineArray[12][1][2] = (-3 + sqrt(5));
  TwentySevenLineArray[12][2][1] = 1.0/2.0 - sqrt(5)/6.0 ;
  TwentySevenLineArray[12][2][2] = sqrt(5);
  TwentySevenLineArray[12][3][1] = 0;
  TwentySevenLineArray[12][3][2] = 1;
 
  TwentySevenLineArray[13][1][1] = (1 - sqrt(5))/12.0;   
  TwentySevenLineArray[13][1][2] = (-5 + 3*sqrt(5) )/4.0;
  TwentySevenLineArray[13][2][1] = (3 + sqrt(5))/12.0;
  TwentySevenLineArray[13][2][2] = (-3 + sqrt(5))/4.0;
  TwentySevenLineArray[13][3][1] = 0;                     
  TwentySevenLineArray[13][3][2] = 1;

  TwentySevenLineArray[14][1][1] = (3 - sqrt(5))/12.0 ;   
  TwentySevenLineArray[14][1][2] = -(3 + sqrt(5))/4.0;
  TwentySevenLineArray[14][2][1] = (1 + sqrt(5))/12.0;
  TwentySevenLineArray[14][2][2] = -(5 + 3*sqrt(5))/4.0;
  TwentySevenLineArray[14][3][1] = 0;
  TwentySevenLineArray[14][3][2] = 1;
 
  TwentySevenLineArray[15][1][1] = 1.0/2.0 - sqrt(5)/6.0 ;                
  TwentySevenLineArray[15][1][2] = sqrt(5);
  TwentySevenLineArray[15][2][1] = (7 - 3*sqrt(5))/6.0;
  TwentySevenLineArray[15][2][2] = -3 + sqrt(5);
  TwentySevenLineArray[15][3][1] = 0;
  TwentySevenLineArray[15][3][2] = 1;
 
  TwentySevenLineArray[16][1][1] = 1.0/6.0 - 1/(6*sqrt(5)); 
  TwentySevenLineArray[16][1][2] = 1 - 3.0/sqrt(5);
  TwentySevenLineArray[16][2][1] = 1.0/6.0 - sqrt(5)/10;
  TwentySevenLineArray[16][2][2] = sqrt(5)/5.0;
  TwentySevenLineArray[16][3][1] = 0;
  TwentySevenLineArray[16][3][2] = 1;
 
  TwentySevenLineArray[17][1][1] = (1 + sqrt(5))/12.0;     
  TwentySevenLineArray[17][1][2] = -(5 + 3*sqrt(5))/4.0;
  TwentySevenLineArray[17][2][1] = (3-sqrt(5))/12.0;
  TwentySevenLineArray[17][2][2] = -(3+sqrt(5))/4.0;
  TwentySevenLineArray[17][3][1] = 0;
  TwentySevenLineArray[17][3][2] = 1;
 
  TwentySevenLineArray[18][1][1] = (3.0 + sqrt(5))/12.0;     
  TwentySevenLineArray[18][1][2] = (-3+sqrt(5))/4.0;
  TwentySevenLineArray[18][2][1] = (1-sqrt(5))/12.0;
  TwentySevenLineArray[18][2][2] = (-5+3*sqrt(5))/4.0;
  TwentySevenLineArray[18][3][1] = 0;
  TwentySevenLineArray[18][3][2] = 1;
 
  TwentySevenLineArray[19][1][1] = (3+sqrt(5))/6.0;        
  TwentySevenLineArray[19][1][2] = -sqrt(5);
  TwentySevenLineArray[19][2][1] = (7+3*sqrt(5))/6.0;
  TwentySevenLineArray[19][2][2] = -(3+sqrt(5));
  TwentySevenLineArray[19][3][1] = 0;
  TwentySevenLineArray[19][3][2] = 1;
 
  TwentySevenLineArray[20][1][1] = (5+sqrt(5))/30.0;      
  TwentySevenLineArray[20][1][2] = 1+3/sqrt(5);
  TwentySevenLineArray[20][2][1] = 5+3*sqrt(5) ;
  TwentySevenLineArray[20][2][2] = -sqrt(5)/5.0;
  TwentySevenLineArray[20][3][1] = 0;
  TwentySevenLineArray[20][3][2] = 1;
 
  TwentySevenLineArray[21][1][1] = 1.0/6.0+sqrt(5)/10.0;        
  TwentySevenLineArray[21][1][2] = -sqrt(5)/5;
  TwentySevenLineArray[21][2][1] = (5+sqrt(5))/30.0;
  TwentySevenLineArray[21][2][2] = 1+3.0/sqrt(5);
  TwentySevenLineArray[21][3][1] = 0;
  TwentySevenLineArray[21][3][2] = 1;
 
  TwentySevenLineArray[22][1][1] = (7 + 3*sqrt(5))/6.0;      
  TwentySevenLineArray[22][1][2] = -(3 + sqrt(5));
  TwentySevenLineArray[22][2][1] = (3 + sqrt(5))/6.0;
  TwentySevenLineArray[22][2][2] = -sqrt(5);
  TwentySevenLineArray[22][3][1] = 0;
  TwentySevenLineArray[22][3][2] = 1;

  TwentySevenLineArray[23][1][1] = -1.0/3.0;                    
  TwentySevenLineArray[23][1][2] = 3;   
  TwentySevenLineArray[23][2][1] = 0;
  TwentySevenLineArray[23][2][2] = 1;
  TwentySevenLineArray[23][3][1] = 0;
  TwentySevenLineArray[23][3][2] = 0;
 
  TwentySevenLineArray[24][1][1] = 0;                       
  TwentySevenLineArray[24][1][2] = -1;
  TwentySevenLineArray[24][2][1] = 0;
  TwentySevenLineArray[24][2][2] = 1;
  TwentySevenLineArray[24][3][1] = -1.0/3.0;
  TwentySevenLineArray[24][3][2] = 0;
 
  TwentySevenLineArray[25][1][1] = 1.0/9.0;                      
  TwentySevenLineArray[25][1][2] = 1.0/3.0;
  TwentySevenLineArray[25][2][1] = 0;
  TwentySevenLineArray[25][2][2] = 1;
  TwentySevenLineArray[25][3][1] = 0;
  TwentySevenLineArray[25][3][2] = 0;
 
  TwentySevenLineArray[26][1][1] = 1.0/3.0;                      
  TwentySevenLineArray[26][1][2] = -1;
  TwentySevenLineArray[26][2][1] = 0;
  TwentySevenLineArray[26][2][2] = 1;
  TwentySevenLineArray[26][3][1] = 0;
  TwentySevenLineArray[26][3][2] = 0;
 
  TwentySevenLineArray[27][1][1] = 2.0/3.0;                     
  TwentySevenLineArray[27][1][2] = -1;
  TwentySevenLineArray[27][2][1] = 0;
  TwentySevenLineArray[27][2][2] = 1;
  TwentySevenLineArray[27][3][1] = 1.0/3.0;
  TwentySevenLineArray[27][3][2] = 0;

 }
	

}