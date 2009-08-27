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

public class Dodecahedron extends RegularPolyhedron {

	public Dodecahedron() {
		double t = (Math.sqrt(5) - 1)/2;
		double t2 = 2-1/t;  //t*t; // Only for the golden ration is t2=t*t
		polyhedronVertices = new Vector3D[] {
				new Vector3D(-1,0,-t2),
				new Vector3D(t,t,t),
				new Vector3D(1,0,t2),
				new Vector3D(t,-t,t),
				new Vector3D(0,-t2,1),
				new Vector3D(0,t2,1),
				new Vector3D(1,0,-t2),
				new Vector3D(t,t,-t),
				new Vector3D(t2,1,0),
				new Vector3D(-t2,1,0),
				new Vector3D(-t,t,-t),
				new Vector3D(0,t2,-1),
				new Vector3D(t,-t,-t),
				new Vector3D(t2,-1,0),
				new Vector3D(-t2,-1,0),
				new Vector3D(-1,0,t2),
				new Vector3D(-t,t,t),
				new Vector3D(-t,-t,-t),
				new Vector3D(0,-t2,-1),
				new Vector3D(-t,-t,t)
		};
		polyhedronFaces = new int[][] {
				{ 16, 9, 8, 1, 5 },
				{ 9, 10, 11, 7, 8 },
				{ 8, 7, 6, 2, 1 },
				{ 6, 12, 13, 3, 2 },
				{ 18, 17, 14, 13, 12 },
				{ 14, 19, 4, 3, 13 },
				{ 4, 5, 1, 2, 3 },
				{ 15, 16, 5, 4, 19 },
				{ 7, 11, 18, 12, 6 },
				{ 10, 0, 17, 18, 11 },
				{ 0, 10, 9, 16, 15 },
				{ 17, 0, 15, 19, 14 }
		};
		setDefaultWindow(-1.3,1.3,-1.3,1.3);
		stellationScale = 0.65;
		stellationHeight = 1.0514622242382667;  // computed as follows:
//		Vector3D c = polyhedronVertices[1].plus(polyhedronVertices[2]).plus(polyhedronVertices[3]).plus(polyhedronVertices[4]).plus(polyhedronVertices[5]).times(0.2);  // center of face
//		Vector3D y = polyhedronVertices[1].minus(polyhedronVertices[8]);  // edge adjacent to top face
//		Vector3D x = c.minus(polyhedronVertices[1]);  // center of top face - vertex #1
//		double h = x.norm()*x.norm()*y.norm()/x.dot(y);
//		stellationHeight = Math.sqrt(h*h-x.norm()*x.norm());
//		System.out.println(stellationHeight);
	}
	
	/**
	 * Overridden to add the showEdgeLengtMorph action to the list of commands for the Animation menu.
	 */
	public ActionList getAdditionalAnimationsForView(final View view) {
		ActionList actions = super.getAdditionalAnimationsForView(view);
		AbstractActionVMM showEdgeLengthMorph = new AbstractActionVMM(I18n.tr("vmm.polyhedron.Dodecahedron.EdgeLengthMorphCommand")) {
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
		    double g = (Math.sqrt(5) - 1)/2;
	    	setEdgeLength( g + getFrameNumber() * ((1-g) / getFrames()) );
		}
		void setEdgeLength(double t) {
			double g = (Math.sqrt(5) - 1)/2;
			double t2 = (2-1/t)*g/t;  // this rescaled version morphs better
			double s = g/t;
			t = g;
			polyhedronVertices[0] = new Vector3D(-s,0,-t2);
			polyhedronVertices[1] = new Vector3D(t,t,t);
			polyhedronVertices[2] = new Vector3D(s,0,t2);
			polyhedronVertices[3] = new Vector3D(t,-t,t);
			polyhedronVertices[4] = new Vector3D(0,-t2,s);
			polyhedronVertices[5] = new Vector3D(0,t2,s);
			polyhedronVertices[6] = new Vector3D(s,0,-t2);
			polyhedronVertices[7] = new Vector3D(t,t,-t);
			polyhedronVertices[8] = new Vector3D(t2,s,0);
			polyhedronVertices[9] = new Vector3D(-t2,s,0);
			polyhedronVertices[10] = new Vector3D(-t,t,-t);
			polyhedronVertices[11] = new Vector3D(0,t2,-s);
			polyhedronVertices[12] = new Vector3D(t,-t,-t);
			polyhedronVertices[13] = new Vector3D(t2,-s,0);
			polyhedronVertices[14] = new Vector3D(-t2,-s,0);
			polyhedronVertices[15] = new Vector3D(-s,0,t2);
			polyhedronVertices[16] = new Vector3D(-t,t,t);
			polyhedronVertices[17] = new Vector3D(-t,-t,-t);
			polyhedronVertices[18] = new Vector3D(0,-t2,-s);
			polyhedronVertices[19] = new Vector3D(-t,-t,t);
		    setIFSData(polyhedronVertices,polyhedronFaces);  // Without this line, colors of faces remain the same during morph
		    forceRedraw();  // make sure exhibit is redrawn with new values.
		}
	}

	
}
