/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.polyhedron;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;

import vmm.actions.ActionList;
import vmm.actions.ToggleAction;
import vmm.core.Animation;
import vmm.core.I18n;
import vmm.core.TimerAnimation;
import vmm.core.VMMSave;
import vmm.core.View;
import vmm.core3D.Exhibit3D;
import vmm.core3D.PhongLighting;
import vmm.core3D.Transform3D;
import vmm.core3D.Vector3D;
import vmm.core3D.View3D;
import vmm.core3D.View3DLit;

/**
 * Represents a polyhedron given in the form of a indexed face set.  That is,
 * the data for the polyhedron consists of a list of vertices plus a list of
 * data for the faces of the polyhedron.  A face is specified by a list of
 * integers, one for each vertex of the face, where each integer is an index
 * into the list of vertices.  The vertices for a face must be listed in
 * counterclockwise order as viewed from the front of face.  No public 
 * method is provided for changing the indexed face set data after the
 * object is constructed, but subclasses can do so using the protected
 * method {@link #setIFSData(Vector3D[], int[][])}.
 * <p>An IFS has a create animation that shows the faces of the polyhedron
 * being drawn in back-to-front order using a simple painter's algorithm.
 */
public class IFS extends Exhibit3D {
	
	/**
	 * When drawind the polyhedron in wireframe form, each edge  is divided
	 * into this many sections.  This prevents incorrect drawing order for
	 * the edges, which can occur (at least) in the {@link Rhombohedron}.
	 * The default value is 8; this seems to work in all cases, but the
	 * value can be changed in a subclass if necessary.  Also note that
	 * dividing the edges into multiple segments in this way means that
	 * an edges can be partially clipped, since clipping is applied to
	 * segments rather than to the edge as a whole.
	 */
	protected int edgeDivisor = 8;
	
	/**
	 * When this value is set to true, a modified version of the simple painter's
	 * algorithm is used. In the modified version, all back-facing faces are drawn
	 * before any front-facing face is drawn.  This prevents incorrect draw order
	 * for faces that sometimes occurs (at least) for the {@link Rhombohedron} 
	 * when the unmodified painter's algorithm is used.  The default value is
	 * false, but it is set to true in the Rhombohedron class.
	 */
	protected boolean useBackFaceFudge = false;
	
	private Vector3D[] vertices;  // A list of all the vertices of the polyhedron.
	private int[][] faces;        // faces[i] is a list of vertices of the i-th face, given as indices into the vertex array.
	
	private Vector3D[] unitNormals;   // Unit normals for the faces; computed in setIFSData().
	private ArrayList<int[]> edges;   // A list of edges in the polyhedron.  Each entry is an array of two ints that
	                                  //   give the indices of the endpoints of the edges in the vertex array.
	                                  //   Although an edge can occur in two faces, the edge is only listed once in this list.
	                                  //   This list is computed in setIFSData.

	private ArrayList<Face> clippedFaces;     // A list of just those faces that have not been clipped, in bact-to-front order.
	private ArrayList<Segment> clippedEdges;  // A list of edge segments that have not been clipped, in back-to-front order;
	                                          //      (Each edge in the polyhedron becomes several segments in this list.)
	
	/**
	 * Create a polyhedron, using specifed lists of vertices and faces.  The data is not verified.
	 * @param vertexList an array containing the vertices of the polyhedron
	 * @param faceData describes the faces of the polyhedron.  faceData[i] is a list of vertices
	 *    in the i-th face, listed in counterclockwise order as seen from in front of the face, with
	 *    each vertex specified as an index into the vertex array.
	 */
	public IFS(Vector3D[] vertexList, int[][] faceData) {
		setIFSData(vertexList,faceData);
	}
	
	/**
	 * This protected constructor creates an IFS object with no data for the polyhedron.
	 * The data must be provided by the subclass before the method 
	 * {@link #computeDrawData3D(View3D, boolean, Transform3D, Transform3D)} is called.
	 */
	protected IFS() {
	}
	
	/**
	 * Change the data that describes the polyhedron.  Note that this is used by
	 * {@link RegularPolyhedron} to show the various truncated forms of the polyhedron.
	 * Each truncation has its own truncation.
	 */
	protected void setIFSData(Vector3D[] vertices, int[][] faces) {
		this.vertices = vertices;
		this.faces = faces;
		edges = new ArrayList<int[]>();
		for (int[] face : faces) {
			makeseg: for (int i = 0; i < face.length; i++) {
				int v1 = face[i];
				int v2 = i == face.length-1 ? face[0] : face[i+1];
				for (int[] edge : edges)
					if (edge[0] == v1 && edge[1] == v2 || edge[0] == v2 && edge[1] == v1)
						continue makeseg;
				edges.add(new int[] {v1,v2});
			}
		}
		unitNormals = new Vector3D[faces.length];
		for (int i = 0; i < faces.length; i++) {
			int[] vertexIndices = faces[i];
			Vector3D normal = null;
			for (int j = 0; j < vertexIndices.length; j++) {
				Vector3D v1 = vertices[vertexIndices[j]];
				Vector3D v2 = vertices[ vertexIndices[(j+1) % (vertexIndices.length)] ];
				Vector3D v3 = vertices[ vertexIndices[(j+2) % (vertexIndices.length)] ];
				normal = v3.minus(v2).cross(v1.minus(v2));
				normal.normalize();
				if (!Double.isNaN(normal.x))
					break;  // in case v1,v2,v3 are colinear vertices, go on to try the next set of vertices
			}
			if (normal == null)
				normal = new Vector3D(0,0,1);
			unitNormals[i] = normal;
		}
		clippedFaces = null;
		clippedEdges = null;
		forceRedraw();
	}
	
	protected void computeDrawData3D(View3D view, boolean exhibitNeedsRedraw, Transform3D previousTransform3D, Transform3D newTransform3D) {
		if (clippedFaces == null || exhibitNeedsRedraw || ! newTransform3D.hasSameProjection(previousTransform3D))
			clip(view);
	}

	protected void doDraw3D(Graphics2D g, View3D view, Transform3D transform) {
		boolean wire = false;
		if ( ! (view instanceof View3DLit) )
			wire = true;
		else if ( ((View3DLit)view).getRenderingStyle() == View3DLit.WIREFRAME_RENDERING)
			wire = true;
		else if ( ((View3DLit)view).getFastDrawing() && !((View3DLit)view).getDragAsSurface())
			wire = true;
		if (wire) {
			boolean thickWireframe = true;
			if (view instanceof IFSView)
				thickWireframe = ((IFSView)view).getThickWireframe();
			Color saveColor = g.getColor();
			Stroke saveStroke = g.getStroke();
			Stroke wideStroke = new BasicStroke(transform.getDefaultStrokeSize() * 5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
			Stroke normalStroke = new BasicStroke(transform.getDefaultStrokeSize(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
			for (Segment seg : clippedEdges) {
				if (seg.wd == true) {
					g.setStroke(wideStroke);
					g.setColor(thickWireframe ? saveColor : g.getBackground());
				}
				else {
					g.setStroke(normalStroke);
					g.setColor(thickWireframe ? g.getBackground() : saveColor);
				}
				view.drawLine(seg.v1,seg.v2);
			}
			g.setColor(saveColor);
			g.setStroke(saveStroke);
		}
		else {
			View3DLit vl = (View3DLit)view;
			int sidesToDraw = -1;
			if (view instanceof IFSView)
				sidesToDraw = ((IFSView)view).sidesToDraw;
			if (sidesToDraw < 0)
				sidesToDraw = clippedFaces.size();
			int opaqueness = (int)(255*(1-vl.getTransparency()));
			for (int f = 0; f < sidesToDraw; f++) {
				Face face = clippedFaces.get(f);
				if (vl.getLightingEnabled()) {
					Color c = PhongLighting.phongLightingColor(face.unitNormal, vl, vl.getTransform3D(),
                            face.centerPoint, Color.white);
					if (opaqueness < 255)
						c = new Color(c.getRed(),c.getGreen(),c.getBlue(),opaqueness);
					view.setColor(c);
				}
				else
					view.setColor(Color.WHITE);
				Vector3D[] v = new Vector3D[face.vertexIndices.length];
				for (int i = 0; i < face.vertexIndices.length; i++)
					v[i] = vertices[face.vertexIndices[i]];
				vl.fillPolygon(v, Color.BLACK);
			}
		}
	}

	/**
	 * Returns a View of type {@link IFS.IFSView}.
	 */
	public View getDefaultView() {
		return new IFSView();
	}
	
	/**
	 * Returns an animation in which the faces of the polyhedron are drawn one at a time,
	 * in back-to-front order.
	 */
	public Animation getCreateAnimation(View view) {
		if (! (view instanceof IFSView) )
			return null;
		final IFSView ifsView = (IFSView)view;
		return new TimerAnimation(-1,200) {
			protected void drawFrame() {
				ifsView.sidesToDraw++;
				if (clippedFaces != null && ifsView.sidesToDraw >= clippedFaces.size())
					cancel();
				else {
					if (clippedFaces != null && getMillisecondsPerFrame() * clippedFaces.size() > 3000) {
						setMillisecondsPerFrame(3000/clippedFaces.size());
					}
					fireExhibitChangeEvent();
				}
			}
			protected void animationStarting() {
				ifsView.sidesToDraw = 0;
				fireExhibitChangeEvent();
			}
			protected void animationEnding() {
				ifsView.sidesToDraw = -1;
				fireExhibitChangeEvent();
			}
			
		};
	}

	/**
	 * Adds to the Actions a toggle to control whether thick lines are used for drawing
	 * the wireframe form of the polyhedron.
	 */
	public ActionList getActionsForView(View view) {
		ActionList actions =  super.getActionsForView(view);
		if (view instanceof IFSView) {
			actions.add(null);
			actions.add(((IFSView)view).thickWireframeToggle);
		}
		return actions;
	}

	/**
	 * Creates the lists of faces and of edge segments that are used to draw the
	 * polyhedron.
	 */
	private void clip(View3D view) {
		Transform3D transform = view.getTransform3D();
		clippedFaces = new ArrayList<Face>();  // The list of faces that are not clipped from the given View.
		makeface: for (int f = 0; f < faces.length; f++) {
			int[] faceData = faces[f];
			for (int i : faceData)
				if (view.clip(vertices[i]))
					continue makeface;  // one of the vertices has been clipped; do not add this face to the list
			Face face = new Face(faceData,unitNormals[f]);
			clippedFaces.add(face);
		}
		for (Face face : clippedFaces)
			face.computeZ(transform);    // ( must be called befor sorting )
		Collections.sort(clippedFaces);  // sorts the list of faces into back-to-front order.
		clippedEdges = new ArrayList<Segment>();  // The list of edges that are not clipped from the given View.
		for (int[] edge : edges) {
			Vector3D v1 = vertices[edge[0]];
			Vector3D v2 = vertices[edge[1]];
			Vector3D dv = (v2.minus(v1).times(1.0/edgeDivisor));
			Vector3D v = v1;
			for (int i = 0; i < edgeDivisor; i++) {  // the edge is divided into several segments of equal length
				Vector3D w = (i == edgeDivisor-1)? v2 : v1.plus(dv.times(i));
				if (! (view.clip(v) || view.clip(w)) ) {
					clippedEdges.add(new Segment(v,w,true));  // add this segment only if both its endpoint are not clipped
					clippedEdges.add(new Segment(v,w,false));
				}
				v = w;
			}
		}
		for (Segment seg : clippedEdges)
			seg.computeZ(transform);     // ( must be called before sorting )
		Collections.sort(clippedEdges);  // sort the segments into back-to-front order
	}

	/**
	 * Holds the data for one face of the polyhedron, with the information needed 
	 * to sort the list of faces into back to front order.
	 *
	 */
	private class Face implements Comparable<Face>{
		int[] vertexIndices;    // vertices of this face, as indices into the vertex array
		Vector3D unitNormal;    // used in coputing color of patch
		Vector3D centerPoint;   // used in computing color of patch
		double centerZ; // only valid after computeZ has been called to apply a transform
		boolean isBackFace;  // This is used only if useBackFaceFudge is true; otherwise it is false.
		Face(int[] indices, Vector3D unitNormal) {
			vertexIndices = indices;
			this.unitNormal = unitNormal;
			centerPoint = vertices[vertexIndices[0]];
			for (int i = 1; i < vertexIndices.length; i++)
				centerPoint = centerPoint.plus(vertices[vertexIndices[i]]);
			centerPoint = centerPoint.times(1.0 / vertexIndices.length);
		}
		void computeZ(Transform3D transform) {
			centerZ = 0;
			for (int index : vertexIndices)
				centerZ += transform.objectToViewZ(vertices[index]);
			centerZ /= vertexIndices.length;
			if (useBackFaceFudge) {
				if (transform.getOrthographicProjection())
					isBackFace = unitNormal.dot(transform.getViewDirection()) <= 0;
				else {
					Vector3D patchCG = vertices[vertexIndices[0]];
					for (int i = 1; i < vertexIndices.length; i++)
						patchCG = patchCG.plus(vertices[vertexIndices[i]]);
					patchCG = patchCG.times(1.0 / vertexIndices.length);
					isBackFace = unitNormal.dot(patchCG.minus(transform.getViewPoint())) <= 0;
				}
			}
			else {
				isBackFace = false;
			}
		}
		public int compareTo(Face face) {  // Note that this only works if computeZ() has been called for all the faces in the list!
			if (isBackFace == face.isBackFace) {
				if (centerZ == face.centerZ)
					return 0;
				else if (centerZ < face.centerZ)
					return -1;
				else
					return 1;
			}
			else if (isBackFace)
				return -1;  // when only one face is marked as a backface, it always comes before the front face
			else
				return 1;
		}
	}
	
	
	/**
	 * Contains information about one of the segments that make up the edges of the
	 * polyhedron, with the information needed to sort the segments into back to
	 * front order.
	 */
	private class Segment implements Comparable<Segment> {
		Vector3D v1, v2; // endpoints of this segment
		double centerZ;  // only valid after computeZ has been called to apply a transform
		boolean wd;      // true for wide segments, false for narrow
		Segment(Vector3D v1, Vector3D v2, boolean wd) {
			this.v1 = v1;
			this.v2 = v2;
			this.wd = wd;
		}
		void computeZ(Transform3D transform) {
			if (wd == true)
			//centerZ = (transform.objectToViewZ(v1) + transform.objectToViewZ(v2)) / 2;
			    centerZ = 1024*Math.min(transform.objectToViewZ(v1),transform.objectToViewZ(v2));
			else
				centerZ = 1024*Math.max(transform.objectToViewZ(v1),transform.objectToViewZ(v2))+8;
		}
		public int compareTo(Segment seg) { // Note that this only works if computeZ() has been called for all the segments in the list!
			if (centerZ == seg.centerZ)
				return 0;
			else if (centerZ < seg.centerZ)
				return -1;
			else
				return 1;
		}
	}
	
	public static class IFSView extends View3DLit {
		private int sidesToDraw = -1;
		@VMMSave private boolean thickWireframe = true;
		public IFSView() {
			setAntialiased(true);
			setDragAsSurface(true);
		}
		protected ToggleAction thickWireframeToggle = new ToggleAction(I18n.tr("vmm.polyhedron.IFS.thickWireframe"),true) {
			public void actionPerformed(ActionEvent evt) {
				setThickWireframe(getState());
			}
		};
		public boolean getThickWireframe() {
			return thickWireframe;
		}
		public void setThickWireframe(boolean thickWireframe) {
			if (this.thickWireframe == thickWireframe)
				return;
			this.thickWireframe = thickWireframe;
			thickWireframeToggle.setState(thickWireframe);
			forceRedraw();
		}
	}

}
