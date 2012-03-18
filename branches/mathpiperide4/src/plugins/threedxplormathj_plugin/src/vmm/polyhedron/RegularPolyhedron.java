/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.polyhedron;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import vmm.actions.AbstractActionVMM;
import vmm.actions.ActionList;
import vmm.actions.ActionRadioGroup;
import vmm.core.TimerAnimation;
import vmm.core.I18n;
import vmm.core.Parameter;
import vmm.core.RealParamAnimateable;
import vmm.core.VMMSave;
import vmm.core.View;
import vmm.core3D.Transform3D;
import vmm.core3D.Vector3D;
import vmm.core3D.View3D;

/**
 * Represents a regular polyhedron, with a parameter that allows the
 * corners to be "truncated" by various amounts.
 * In fact, this class can also be used for many
 * non-regular polyhedra.  The main requirement is that the algorithm that
 * is used for truncating the corners of the polyhedron makes the following
 * assumption:  For each vertex, the opposite endpoints of the set of edges
 * that are incident on that vertex must be co-planar (otherwise, the
 * facets that are produced when the corners are truncated will not be
 * planar polygons).  Also, the faces should not have any colinear vertices;
 * that is, all the vertices of each face should be real corners of that face.
 * <p>This class is abstract, even though it has no abstract methods, because
 * this class does not define any data for any polyhedron.  The data must be
 * provided by any concrete subclass, probably in the constructor of the 
 * subclass, but in any case before the computeDrawData3D() method in this class
 * is called.
 */
abstract public class RegularPolyhedron extends IFS {
	
	/**
	 * A list of the vertices of the polyhedron.  The value of this variable in this
	 * class is null.  A valid vertex list must be provided by any concrete subclass.
	 * This list contains the vertices of the basic, untruncated polyhedron.  The value
	 * of this variable should not be modified after the object is constructed.
	 */
	protected Vector3D[] polyhedronVertices;
	
	/**
	 * Data for the faces of the polyhedron. The value of this variable in this
	 * class is null.  Valid face must be provided by any concrete subclass.
	 * This list describes the faces of the basic, untruncated polyhedron.
	 * polyhedronFaces[i] is a list of the vertices of face number i, where 
	 * each vertex is specified as an index into the vertex array.  The vertices
	 * must be listed in counterclockwise order, when viewed from the front of
	 * the face.  The value of this variable should not be modified after the 
	 * object is constructed.
	 */
	protected int[][] polyhedronFaces;
	
	private int[][] edgeList;  // This is computed in makeEdgeList the first time computeDrawData3D() is called.
	   // edgeList[i] contains info about edges that have vertex i as an endpoint; it
	   // is an array consisting of integers j such that there is an edge between vertex
	   // i and vertex j.  Furthermore, the j's are ordered so that when vertex i is
	   // truncated, the j's are in the order of the edges arounded the new face that
       // is produced by the truncation.
	
	private Vector3D[] mangledPolyhedronVertices;  // Vertex for the truncated or stellated polhedron, 
	                                                 // computed in makeTruncatedPolyhedron()/makeStellatedPolyhedron()
	private int[][] mangledPolyhedronFaces;  // Face data for the truncated or stellated polhedron
	
	private boolean mangledPolyhedronIsStellated = false;
	@VMMSave private boolean stellationWanted = false;

	private double currentTruncation = -1;  // Truncation amount for which the data in truncatedPolyhedronVertices and
	                                        // truncatedPolyhedronVertices was computed.  If the value is zero, then
	                                        // the vertex and face data are null, and the basic data in polyhedronVertices and
	                                        // polyhedronFaces is used instead.  The value of -1 is there to force the
	                                        // polyhedron data to be set in the superclass the first time
	                                        // computeDrawData3D() is called.
	
	protected double stellationScale = 0.5;   // To create the stellated polyhedron, a copy of the original
	                                          // a copy of the original polyhedron, scaled by this amount is used.
	                                          // This can be changed in the constructor of a subclass.
	
	protected double stellationHeight = 1;    // The height of the pyramids on the stellated polyhedron, BEFORE scaling.
	                                            // This can be changed in the consrtuctor of a subclass.
	
	private double stellationDegree = 1;  //used only during the stellation morph to morph the height of the pyramids.
	private double stellationDegreeUsed = -1;
	
	protected RealParamAnimateable truncation;  // A parameter for setting the truncation amount.  The value of this
	                                          // parameter varies from 1 to 0.5, with 1 indicating no truncation, but
	                                          // the trunctaion amount used for computation in this class is actually
	                                          // 1 - truncation.getValue(), which varies from 0 to 0.5.
	
	/**
	 * A radio group that appears in the Action menu that allows the user to select one of the
	 * standard truncations.
	 */
	protected ActionRadioGroup truncationSelect = new ActionRadioGroup(
			new String[] { I18n.tr("vmm.polyhedron.RegularPolyhedron.NoTruncation"),
					I18n.tr("vmm.polyhedron.RegularPolyhedron.StandardTruncation"),
					I18n.tr("vmm.polyhedron.RegularPolyhedron.MidpointTruncation"),
					I18n.tr("vmm.polyhedron.RegularPolyhedron.Stellated")},
			0 ) {
			public void optionSelected(int selectedIndex) {
				if (selectedIndex == 0)
					truncation.setValue(1);
				else if (selectedIndex == 1)
					truncation.setValueFromString("2/3");
				else if (selectedIndex == 2)
					truncation.setValueFromString("1/2");
				setStellationWanted(selectedIndex == 3);
			} 
	};
	
	/**
	 * Sets up a RegularPolyhedron with default viewpoint (10,-10,10) and default xy-window
	 * (-2,2,-2,2), and adds the {@link #truncation} parameter.
	 */
	protected RegularPolyhedron() {
		setDefaultViewpoint(new Vector3D(10,-10,10));
		setDefaultWindow(-2,2,-2,2);
		truncation = new RealParamAnimateable("vmm.polyhedron.RegularPolyhedron.truncation","1","1","1/2");
		truncation.setMinimumValueForInput(0.5);
		truncation.setMaximumValueForInput(1);
		addParameter(truncation);
	}
	
	public boolean getStellationWanted() {
		return stellationWanted;
	}

	public void setStellationWanted(boolean stellationWanted) {
		if (this.stellationWanted == stellationWanted)
			return;
		this.stellationWanted = stellationWanted;
		if (stellationWanted) {
			truncationSelect.setSelectedIndex(3);
		}
		else {
			double val = truncation.getValue();
			if (val > 0.995)
				truncationSelect.setSelectedIndex(0);
			else if (Math.abs(val - 2.0/3.0) < 0.005)
				truncationSelect.setSelectedIndex(1);
			else if (val < 0.505)
				truncationSelect.setSelectedIndex(2);
			else
				truncationSelect.setSelectedIndex(-1);
		}
		forceRedraw();
	}


	protected void computeDrawData3D(View3D view, boolean exhibitNeedsRedraw, Transform3D previousTransform3D, Transform3D newTransform3D) {
		double trunc = 1 - truncation.getValue();
		if (trunc <= 0.005)
			trunc = 0;
		else if (trunc >= 0.495)
			trunc = 0.5;
		if (stellationWanted) {
			if (!mangledPolyhedronIsStellated || stellationDegree != stellationDegreeUsed) {
				makeStellatedPolyhedron();
				mangledPolyhedronIsStellated = true;
				setIFSData(mangledPolyhedronVertices, mangledPolyhedronFaces);
			}
		}
		else if (mangledPolyhedronIsStellated || trunc != currentTruncation) {  
			     // Note that the condition is always true the first time this method is called.
			if (trunc == 0) {  // Use the data for the untruncated polyhedron.
				mangledPolyhedronFaces = null;
				mangledPolyhedronVertices = null;
				setIFSData(polyhedronVertices, polyhedronFaces);
			}
			else {  // Compute the data for truncated polyhedron and install it so that it will be used for drawing.
				if (edgeList == null)
					makeEdgeList();
				makeTruncatedPolyhedron(trunc);
				setIFSData(mangledPolyhedronVertices, mangledPolyhedronFaces);
			}
			currentTruncation = trunc;
			mangledPolyhedronIsStellated = false;
		}
		super.computeDrawData3D(view, exhibitNeedsRedraw, previousTransform3D,
				newTransform3D);
	}
		
	/**
	 * Adds {@link #truncationSelect} to the actions from the superclass.
	 */
	public ActionList getActionsForView(View view) {
		ActionList actions = super.getActionsForView(view);
		actions.add(null);
		actions.add(truncationSelect);
		return actions;
	}
	
	/**
	 * This is overridden to keep the selected item in the action group
	 * {@link #truncationSelect} in sync with the value of the parameter {@link #truncation}.
	 */
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
		super.parameterChanged(param, oldValue, newValue);
		if (param == truncation) {
			stellationWanted = false;
			double val = truncation.getValue();
			if (val > 0.995)
				truncationSelect.setSelectedIndex(0);
			else if (Math.abs(val - 2.0/3.0) < 0.005)
				truncationSelect.setSelectedIndex(1);
			else if (val < 0.505)
				truncationSelect.setSelectedIndex(2);
			else
				truncationSelect.setSelectedIndex(-1);
		}
	}
	
	public ActionList getAdditionalAnimationsForView(final View view) {
		ActionList actions = super.getAdditionalAnimationsForView(view);
		actions.add(new AbstractActionVMM(I18n.tr("vmm.polyhedron.RegularPolyhedron.StellationMorph")) {
			public void actionPerformed(ActionEvent evt) {
				TimerAnimation animation = new TimerAnimation() {
					protected void animationStarting() {
						stellationDegree = 0;
						truncation.setValue(1);
						setStellationWanted(true);
						forceRedraw();
					}
					protected void animationEnding() {
						stellationDegree = 1;
						forceRedraw();
					}
					protected void drawFrame() {
						stellationDegree = (double)getFrameNumber()/getFrames();
						forceRedraw();
					}
				};
				animation.setLooping(TimerAnimation.OSCILLATE);
				animation.setFrames(getFramesForMorphing());
				view.getDisplay().installAnimation(animation);
			}
		}); 
		return actions;
	}

	/**
	 * Computes the essential edge list data that is needed for making the truncated
	 * polyhedra.  The object is to have a list of the edges that come out of each
	 * vertex of the original polyhedron;  the data actually contains a list of
	 * the opposite endpoints of each of those edges.  The hard part is putting
	 * the list of edges into the right order -- counterclockwise as seen when
	 * looking down on the vertex from outside the polyhedron.
	 */
	private void makeEdgeList() {
		edgeList = new int[polyhedronVertices.length][];
		for (int v = 0; v < polyhedronVertices.length; v++) {
			ArrayList<Integer> endpoints = new ArrayList<Integer>();  // a list of opposit endpoints of edges that start at vertex v
			for (int[] face : polyhedronFaces) {
				   // find any occurrence of vertex v in this face.  When one is found, add the two
				   // vertices that are connedted to v by edges of this face (but don't ever add the
				   // same vertex twice).
				for (int i = 0; i < face.length; i++)  {
					if (face[i] == v) {
						int nextv = i == face.length-1 ? face[0] : face[i+1];
						int prevv = i == 0 ? face[face.length-1] : face[i-1];
						if (!endpoints.contains(nextv))
							endpoints.add( nextv );
						if (!endpoints.contains(prevv))
							endpoints.add( prevv );
					}
				}
			}
			int[] e = new int[endpoints.size()];  // The list of endpoints in the correct order.
			e[0] = endpoints.remove(0);  // We could start anywhere, so just take e[0] to be the first vertex in the list.
			Vector3D top = polyhedronVertices[v];  // This is the corner vertex, that all the other vertices are connected to.
			for (int i = 1; i < e.length; i++) {
				   // The object here is to find the next vertex in counterclockwise order from vertex v1 (= vertex number e[i-1]).
				   // The vertex, v2, that we want the following property:  Consider the plane that contains
				   // top, vertex e[i-1], and v2; all the other vertices must lie on the correct side of this
				   // plane; specifically, if for each other vertex v, n.dot(v.minus(v1)) must be less than 0,
				   // where n is the unit normal to the plane.
				Vector3D v1 = polyhedronVertices[e[i-1]];
				for (int j = 0; j < endpoints.size(); j++) {
					Vector3D v2 = polyhedronVertices[endpoints.get(j)];  // the vertex to be tested
					Vector3D n = top.minus(v1).cross(v2.minus(v1));
					boolean ok = true;
					for (int k = 0; k < endpoints.size(); k++) {
						if (k != j && n.dot(polyhedronVertices[endpoints.get(k)].minus(v1)) > 0) {
							ok = false;  // vertex endpoints.get(k) lies on the wrong side of the plane
							break;
						}
					}
					if (ok) {
						e[i] = endpoints.remove(j);
						break;
					}
				}
			}
			edgeList[v] = e;
		}
	}

	/**
	 * Creates the data for a truncated polyhedron, using the untruncated polyhedron data,
	 * the truncation amount, and the edgeList data that was computed by {@link #makeEdgeList()}.
	 * @param truncation  the truncation amount, between 0 and 0.5.
	 */
	private void makeTruncatedPolyhedron(double truncation) {
		int origEdges = 0;  // the number of edges in the original polyhedron data; if an edge belongs to two faces, it is counted twice.
		for (int[] vi : polyhedronFaces)
			origEdges += vi.length;
		int vertexCount = origEdges;  // the vertex count for the truncated polyhedron -- each directed edge in the basic polyhedron gives one vertex.
		mangledPolyhedronVertices = new Vector3D[vertexCount];
		int faceCount = polyhedronFaces.length + polyhedronVertices.length; // one face for each face in the original, plus a new face for each corner of the original.
		mangledPolyhedronFaces = new int[faceCount][];
		int face = 0;
		int vertex = 0;
		int[] vertexOffsets = new int[polyhedronVertices.length]; 
		          // Vertex number i in the original polyhedron gives rise to one vertex in truncatedPolyhedronVertices
		          // for each edge that comes out of vertex number i.  vertexOffsets[i] tells the index in the array
		          // truncatedPolyhedronVertices where these new vertices start.
		for (int i = 0; i < polyhedronVertices.length; i++) {
			   // create the truncated polyhedron vertices corresponding to all the edges that come out of
			   // the i-th vertex in the original polyhedron.  At the same time, create the face that is produced
			   // by cutting off the corner at that vertex.
			vertexOffsets[i] = vertex;
			int[] faceData = new int[edgeList[i].length];  // data for the face
			for (int j = 0; j < edgeList[i].length; j++) {
				   // create the vertex corresponding to this edges.  The vertex is located along the edge, partway from one
				   // endpoint to the other, with the truncation amount giving the distance as a fraction of the length of the edge.
				mangledPolyhedronVertices[vertex] = polyhedronVertices[i].plus(polyhedronVertices[edgeList[i][j]].minus(polyhedronVertices[i]).times(truncation));
				faceData[j] = vertex;  // add this vertex to the face
				vertex++;
			}
			mangledPolyhedronFaces[face++] = faceData;  // add the face
		}
		for (int i = 0; i < polyhedronFaces.length; i++) {
			   // Add the face of the truncated polyhedron that corresponds to the i-th face in the original polyhedron.
			   // This face has two vertices for each edge of the original face, except when the truncation amount
			   // is 0.5 (in which case, the two vertices are in the same location place and only one of the pair of
			   // doubled vertices is used for the face.)  The hard part is finding the index of each vertex in the vertex list.
			int faceSize = polyhedronFaces[i].length;
			if (truncation < 0.5)
				faceSize *= 2;
			int[] faceData = new int[faceSize];
			int v = 0;
			for (int j = 0; j < polyhedronFaces[i].length; j++) {
				int v1 = polyhedronFaces[i][j];  // endpoint of one edge comeing out of vertex j of face i.
				int v2 = polyhedronFaces[i][ j == polyhedronFaces[i].length-1 ? 0 : j+1];  // endpoint for the other edge
				int offset1 = vertexOffsets[v1];  // where do the vertices corming from vertex v1 in the original polyhedron start in truncatedPolyhedronVertices?
				int offset2;
				for (offset2 = 0; offset2 < edgeList[v1].length; offset2++) {
					   // find which edge from v1 in the original polyhedron has v2 has its other endpoint.
					if (edgeList[v1][offset2] == v2)
						break;
				}
				assert offset2 < edgeList[v1].length;
				faceData[v++] = offset1+offset2;  // position in truncatedPolyhedronVertices of the vertex that comes from edge (v1,v2) in the original polyhedron.
				if (truncation < 0.5) {
					offset1 = vertexOffsets[v2];
					for (offset2 = 0; offset2 < edgeList[v2].length; offset2++) {
						if (edgeList[v2][offset2] == v1)
							break;
					}
					assert offset2 < edgeList[v2].length;
					faceData[v++] = offset1+offset2;   // position in truncatedPolyhedronVertices of the vertex that comes from edge (v2,v1) in the original polyhedron.
				}
			}
			mangledPolyhedronFaces[face++] = faceData;  // add this face to the face data
		}
	}
	
	private void makeStellatedPolyhedron() {
		int vertexCount = polyhedronVertices.length + polyhedronFaces.length;  // the vertex count for the stellated polyhedron -- original vertices plus one extra vertex for each face.
		mangledPolyhedronVertices = new Vector3D[vertexCount];
		int faceCount = 0;
		for (int[] vi : polyhedronFaces)  // vi is the list of vertices around the face, and so its length is the number of edges of that face
			faceCount += vi.length;  // Each edge of each face give rise to a face in the stellated polyhedron
		mangledPolyhedronFaces = new int[faceCount][];
		for (int i = 0; i < polyhedronVertices.length; i++)
			mangledPolyhedronVertices[i] = polyhedronVertices[i].times(stellationScale);
		double stellationHeightScaled = stellationHeight * stellationScale;
		int vertex = polyhedronVertices.length;  // Next vertex in this spot
		int face = 0;  // Next face in this spot
		for (int[] faceVertexList : polyhedronFaces) {
			Vector3D edge1 = mangledPolyhedronVertices[faceVertexList[1]].minus(mangledPolyhedronVertices[faceVertexList[0]]);
			Vector3D edge2 = mangledPolyhedronVertices[faceVertexList[2]].minus(mangledPolyhedronVertices[faceVertexList[1]]);
			Vector3D axisDirection = edge2.cross(edge1).normalized();
			Vector3D faceCenter = new Vector3D(0,0,0);
			for (int i : faceVertexList)
				faceCenter.assignPlus(mangledPolyhedronVertices[i]);
			faceCenter = faceCenter.times(1.0/faceVertexList.length);
			Vector3D pyramidTop = faceCenter.plus(axisDirection.times(stellationHeightScaled*stellationDegree));
			mangledPolyhedronVertices[vertex] = pyramidTop;
			for (int i = 1; i < faceVertexList.length; i++)
				mangledPolyhedronFaces[face++] = new int[] { faceVertexList[i-1], faceVertexList[i], vertex };
			mangledPolyhedronFaces[face++] = new int[] { faceVertexList[faceVertexList.length-1], faceVertexList[0], vertex };
			vertex++;
		}
		stellationDegreeUsed = stellationDegree;
		assert vertex == mangledPolyhedronVertices.length;
		assert face == mangledPolyhedronFaces.length;
	}
	
	
}
