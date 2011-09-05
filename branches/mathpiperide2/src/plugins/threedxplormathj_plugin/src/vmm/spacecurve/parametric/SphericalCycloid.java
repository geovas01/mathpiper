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

	/**
	 * Defines a rolling curve on the unit sphere
	 */
	public class SphericalCycloid extends SphericalCurve {
		
		
		RealParamAnimateable aa = new RealParamAnimateable("vmm.spacecurve.parametric.SphericalCycloid.aa",0.2,0.2,0.2);
		RealParamAnimateable bb = new RealParamAnimateable("vmm.spacecurve.parametric.SphericalCycloid.bb",2,-1,3);
		//RealParamAnimateable cc = new RealParamAnimateable("vmm.spacecurve.parametric.SphericalCycloid.cc",3,5,2);
		//RealParamAnimateable dd = new RealParamAnimateable("vmm.spacecurve.parametric.SphericalCycloid.dd",5,5,5);
		RealParamAnimateable ee = new RealParamAnimateable("vmm.spacecurve.parametric.SphericalCycloid.ee",5,5,5);
		
		public SphericalCycloid() {
			setDefaultViewpoint(new Vector3D(10,0,30));
			setDefaultWindow(-1.1,1.1,-1.1,1.1);
			tResolution.setValueAndDefault(200);
			tmin.setValueAndDefault(0);
			tmax.setValueAndDefaultFromString("2 * pi");
			addParameter(ee);
			//addParameter(dd);
			//addParameter(cc);
			addParameter(bb);
			addParameter(aa);
			tubeSize.setValueAndDefault(0.1);
		}
		
		protected Vector3D value(double t) {
			// The radius of the road circle is a*pi. The radius of the rolling circle is rol,
			// rol is computed from the intended symmetry (integer e) and from a*pi.
			// The circle rolls outside if e > 0, otherwise inside.
			Vector3D E,M,C;
			double a = aa.getValue();
			double b = bb.getValue(); 
			//double c = cc.getValue();
			//double d = dd.getValue();
			double e = ee.getValue();
			e = (int)(e);
			double start = 0.0;
			if (Math.signum(e) < 0)  start = Math.PI;
			double rol = Math.asin(Math.sin(Math.PI*a)/Math.abs(e));
			E = geographicCoordinates(b * rol, (e+1)*t + start);
			M = geographicCoordinates((Math.PI*a + Math.signum(e)*rol)/2, t);
			C = E.reflectInAxis(M);
			return new Vector3D(C);
		}
		
		
	}
