/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.spacecurve.parametric;

import vmm.core.RealParamAnimateable;
import vmm.core3D.Vector3D;

public class SphericalEllipse extends SphericalCurve {
		
		
		RealParamAnimateable aa = new RealParamAnimateable("vmm.spacecurve.parametric.SphericalEllipse.aa",0.6,0.6,0.6);
		//RealParamAnimateable bb = new RealParamAnimateable("vmm.spacecurve.parametric.SphericalEllipse.bb",0.89,0.4,1.1);
		//RealParamAnimateable cc = new RealParamAnimateable("vmm.spacecurve.parametric.SphericalEllipse.cc",3,5,2);
		//RealParamAnimateable dd = new RealParamAnimateable("vmm.spacecurve.parametric.SphericalEllipse.dd",5,5,5);
		RealParamAnimateable ee = new RealParamAnimateable("vmm.spacecurve.parametric.SphericalEllipse.ee",0.9,0.4,1.15);

		
		public SphericalEllipse() {
			setDefaultViewpoint(new Vector3D(10,0,30));
			setDefaultWindow(-1.1,1.1,-1.1,1.1);
			tResolution.setValueAndDefault(200);
			tmin.setValueAndDefault(0);
			tmax.setValueAndDefaultFromString("2 * pi");
			addParameter(ee);
			//addParameter(dd);
			//addParameter(cc);
			//addParameter(bb);
			addParameter(aa);
			tubeSize.setValueAndDefault(0.1);
		}
		
		protected Vector3D value(double t) {
			Vector3D E,P,N,F,SecantFtoP,Aux;
			double a = aa.getValue();
			//double b = bb.getValue(); 
			//double c = cc.getValue();
			//double d = dd.getValue();
			double e = ee.getValue();
			N = new Vector3D(0,0,1);
			F = new Vector3D(Math.sin(e), 0, Math.cos(e));
			P = geographicCoordinates(2*a, t);
			SecantFtoP = P.minus(F);
			SecantFtoP.normalize();
			Aux = F.minus(P);
			double coeff1 = Aux.dot(P.minus(N));
			double coeff2 = Aux.dot(P);
			double c = coeff2/coeff1;
			E = N.linComb(c, 1-c,P);
			if (2*a > Math.PI) E.negate();
			E.normalize();
			return new Vector3D(E);
		}
/**
	var
		E, P, N, F, SecantFtoP: vector;
		coeff1,coeff2:longreal;
        lambda:longreal;

   	
    begin        // SphericalEllipseXYZCoord 
	    GetFoci;
		P := LongitudeCircle(t,1);
		SecantFtoP := VectorDiffFcn(P, F);  
        Normalize(SecantFtoP);
        // Now solve the equation to find E
       coeff1 :=DotProd(VectorDiffFcn(P, N), VectorDiffFcn(F, P) );
       coeff2 :=DotProd( P, VectorDiffFcn(F, P) );
       E := ConvexCombination (coeff2/coeff1, N, P);
       // This E is the intersection of the plane of the tangent great 
       // circle through M and E with the segment from N to P
       if (2*aa > pi) then
          E := ScalarTimesVector(-1,E);
       Normalize(E);
	   SphericalEllipseXYZCoord := E;
	end;
 */
		
	}