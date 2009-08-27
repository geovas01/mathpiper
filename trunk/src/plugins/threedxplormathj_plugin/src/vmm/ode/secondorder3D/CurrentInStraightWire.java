/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.secondorder3D;

import vmm.core.RealParamAnimateable;
import vmm.core3D.Vector3D;

public class CurrentInStraightWire extends ChargedParticles {
	
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.ode.secondorder3D.CurrentInStraightWire.WireDirX",1,1,0);
	private RealParamAnimateable bb = new RealParamAnimateable("vmm.ode.secondorder3D.CurrentInStraightWire.WireDirY",0,0,1);
	private RealParamAnimateable cc = new RealParamAnimateable("vmm.ode.secondorder3D.CurrentInStraightWire.WireDirZ",0);
	private RealParamAnimateable dd = new RealParamAnimateable("vmm.ode.secondorder3D.CurrentInStraightWire.Current",1);
	
	public CurrentInStraightWire() {
		addParameter(cc);
		addParameter(bb);
		addParameter(aa);
		addParameter(dd);
		initialDataDefault = new double[] {1, 1, 0, 0, 0.3, 0, .05, 45 };
		setDefaultWindow(-2, 2, -2, 2);
		setDefaultViewpoint(new Vector3D(10, -10, 10));
	}

	protected void magneticField(double x, double y, double z, Vector3D answer) {
		double a = aa.getValue();
		double b = bb.getValue();
		double c = cc.getValue();
		double d = dd.getValue();
		Vector3D wire, normProj, force;
		double denom = Math.sqrt(a*a + b*b + c*c);
		if (denom == 0)
			wire = new Vector3D(1,0,0);
		else
			wire = new Vector3D( a/denom, b/denom, c/denom);
		double wireDotPosition = wire.x * x + wire.y * y + wire.z * z;
		normProj = new Vector3D(x - wire.x*wireDotPosition, y - wire.y*wireDotPosition, z - wire.z*wireDotPosition);
		               // previous line gives position - wireDotPosition*wire, i.e. projection of position normal to wire
		double r = normProj.x*normProj.x + normProj.y*normProj.y + normProj.z*normProj.z;
		force = wire.cross(normProj);
		answer.x = force.x * (d/r);
		answer.y = force.y * (d/r);
		answer.z = force.z * (d/r);
	}
	
//SetVector(aa, bb, cc, wire);              {The direction of the wire}
//    {(we assume wire goes thru the origin)}
//dsquare := aa * aa + bb * bb + cc * cc;
//
//if dsquare <> 0 then
//ScalarProd(1 / sqrt(dsquare), wire, wire)   {unit vector along wire}
//else
//SetVector(1, 0, 0, wire);          {if wire is a zero vector make wire the x-unit}
//
//SetVector(x, y, z, Position);
//ProjectNormalTo(wire, Position, NormalProj);    {Projection of position normal to wire}
//rr := NormSquare(NormalProj);
//
//if rr = 0 then
//SetVector(0, 0, 0, force)             {if we're sitting on the wire make the force zero}
//else begin
//CrossProd(wire, NormalProj, force);   {the force is orthogonal to the plane containing the }
//ScalarProd(dd / rr, force, force);    {wire and the position vector and has length 1/rr}
//    {bb is the current in the wire}
//end;
//MagneticField := force;



}
