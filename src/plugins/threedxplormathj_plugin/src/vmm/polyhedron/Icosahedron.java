/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.polyhedron;

import java.awt.event.ActionEvent;

import vmm.actions.AbstractActionVMM;
import vmm.actions.ActionList;
import vmm.core.I18n;
import vmm.core.TimerAnimation;
import vmm.core.View;
import vmm.core3D.Vector3D;

public class Icosahedron extends RegularPolyhedron {
	
	
	public Icosahedron() {
		double t = (Math.sqrt(5) - 1)/2;
		polyhedronVertices = new Vector3D[] {
				new Vector3D(-1,-t,0),
				new Vector3D(0,1,t),
				new Vector3D(0,1,-t),
				new Vector3D(1,t,0),
				new Vector3D(1,-t,0),
				new Vector3D(0,-1,-t),
				new Vector3D(0,-1,t),
				new Vector3D(t,0,1),
				new Vector3D(-t,0,1),
				new Vector3D(t,0,-1),
				new Vector3D(-t,0,-1),
				new Vector3D(-1,t,0),
		};
		polyhedronFaces = new int[][] {
				{ 3, 7, 1 },
				{ 4, 7, 3 },
				{ 6, 7, 4 },
				{ 8, 7, 6 },
				{ 7, 8, 1 },
				{ 9, 4, 3 },
				{ 2, 9, 3 },
				{ 2, 3, 1 },
				{ 11, 2, 1 },
				{ 10, 2, 11 },
				{ 10, 9, 2 },
				{ 9, 5, 4 },
				{ 6, 4, 5 },
				{ 0, 6, 5 },
				{ 0, 11, 8 },
				{ 11, 1, 8 },
				{ 10, 0, 5 },
				{ 10, 5, 9 },
				{ 0, 8, 6 },
				{ 0, 10, 11 },
		};
		setDefaultWindow(-1.3,1.3,-1.3,1.3);
		stellationScale = 0.45;
		stellationHeight = 1.8683447179254318;  // computed as follows
//		Vector3D c = polyhedronVertices[1].plus(polyhedronVertices[2]).plus(polyhedronVertices[3]).times(1.0/3.0);  // center of face
//		Vector3D y = polyhedronVertices[1].minus(polyhedronVertices[8]);  // edge adjacent to face
//		Vector3D x = c.minus(polyhedronVertices[1]);  // center of face - vertex #1
//		double h = x.norm()*x.norm()*y.norm()/x.dot(y);
//		stellationHeight = Math.sqrt(h*h-x.norm()*x.norm());
//		System.out.println(stellationHeight);

	}
	
	/**
	 * Overridden to add the showEdgeLengtMorph action to the list of commands for the Animation menu.
	 */
	public ActionList getAdditionalAnimationsForView(final View view) {
		ActionList actions = super.getAdditionalAnimationsForView(view);
		AbstractActionVMM showEdgeLengthMorph = new AbstractActionVMM(I18n.tr("vmm.polyhedron.Icosahedron.EdgeLengthMorphCommand")) {
			public void actionPerformed(ActionEvent evt) {
				view.getDisplay().installAnimation( new EdgeLengthMorph() );
			}
		};
		actions.add(showEdgeLengthMorph);
		return actions;
	}
	
	
	private class EdgeLengthMorph extends TimerAnimation {
		double saveTruncation;
		EdgeLengthMorph() {
			   // Treat this like a morph in that the number of frames will be the same 
			   // as specified by getFramesForMorphing(). 50 milliseconds per frame.
			super(getFramesForMorphing(), 50);  // (Calls constructor from TimerAnimation class.)
			setLooping(TimerAnimation.OSCILLATE);  // animation plays back-and-forth forever, until canceled.
		}
		protected void animationStarting() {
			saveTruncation = truncation.getValue();  // save current truncation value.
			truncation.setValue(1);  // Use untruncated polyhedron during morph.
			setStellationWanted(false);
			useBackFaceFudge = true;
		}
		protected void animationEnding() {
			setEdgeLength((Math.sqrt(5) - 1)/2); // restore default value at end of animation
			truncation.setValue(saveTruncation); // restore truncation value from start of animation
			useBackFaceFudge = false;
		} 
		protected void drawFrame() {
	    	setEdgeLength( getFrameNumber() * ((Math.sqrt(5) - 1)/2) / getFrames() );
		}
		void setEdgeLength(double EL) {
		    polyhedronVertices[1].z =  EL;  polyhedronVertices[ 2].z = -EL; 
		    polyhedronVertices[3].y =  EL;  polyhedronVertices[ 4].y = -EL; 
		    polyhedronVertices[5].z = -EL;  polyhedronVertices[ 6].z =  EL;
		    polyhedronVertices[7].x =  EL;  polyhedronVertices[ 8].x = -EL;
		    polyhedronVertices[9].x =  EL;  polyhedronVertices[10].x = -EL;
		    polyhedronVertices[0].y = -EL;  polyhedronVertices[11].y =  EL;
		    setIFSData(polyhedronVertices,polyhedronFaces);  // Without this line, colors of faces remain the same during morph
		    forceRedraw();  // make sure exhibit is redrawn with new values.
		}
	}


	
}
