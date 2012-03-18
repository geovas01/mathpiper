/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;


/**
 * A Grid3D represents a surface in three-space as an array of vectors.  The first index of the array represents
 * the "u" direction on the surface, while the second index represents the "v" directions.  Methods are
 * provided for drawing the surface a View3D or View3DLit.  The surface is considered to be made up of
 * "patches" which can be further divided into "subpatches".  The surface can be drawn in wireframe form, by
 * drawing each sub-patch, or just by drawing the large patches.  When drawn in patch form, the lighting of the surface
 * is derived from the View in which it is drawn.  The intrinsic color of each individual patch can be set as
 * part of the surface data.  Drawing of the Grid3D will normally be done by calling methods in View3D and View3DLit,
 * which will make sure that the data needed to complete the drawing correctly is done correctly.
 * <p>It is possible to construct a surface from several copies of a basic grid, where each copy is
 * obtained by applying a TransformMatrix to the basic grid. These transforms are specified by
 * the method {@link #addGridTransform(GridTransformMatrix)}.  See also {@link #setUseIdentityTransform(boolean)}.
 */
public class Grid3D { 
	
	private int uPatchCount, vPatchCount;  // The U and V "resolution"
	private final int subPatchesPerPatch;

	private int uCurveIncrement, vCurveIncrement;
	
	private Color defaultPatchColor = Color.white;
	private Color defaultBackColor;
	
	private int uCount, vCount;

	private Vector3D[][] pointsOnSurface;

	private Color[][] patchColor;  // used only if individual colors are specied for patches; the (i,j) entry is the color for the (i,j) subpatch.

	private Vector3D[][] normal;  // contains normal vectors set from outside this object
	
	private Vector3D[][][] transformedData;     // Transformed pointsOnSurface; first index specifies which copy of the basic grid.
	private Vector3D[][][] transformedVCurves;  // Same data as the above array, but with indices reversed
	
	private boolean subPatchesValid;
	private ArrayList<Patch> subpatches;
	private boolean patchesValid;
	private ArrayList<Patch> patches;
	
	private ArrayList<GridTransformMatrix> transforms;  // If non-null, then addtional transformed copies of the basic grid are added to the data;

	private boolean useIdentityTransform = true;  // If transforms is non-null, this variable tells whether the identity
	                                              // is also used, in addition to the transforms in that list.
	
	/**
	 * Creates a surface grid with the specified numbers of patches in the U and V directions
	 * and with 6 subpatches per patch (in each direction).  Initially, all vertices of the surface are
	 * undefined.  They can be set by calling {@link #setVertex(int, int, Vector3D)}.
	 * @param uPatchCount the number of major patches in the U direction.  The number of subpatches is 6*uPatchCount.  Must be positive.
	 * @param vPatchCount the number of major patches in the V direction.  The number of subpatches is 6*vPatchCount.  Must be positive.
	 */
	public Grid3D(int uPatchCount, int vPatchCount) {
		subPatchesPerPatch = 6;
		setPatchCount(uPatchCount, vPatchCount);
		uCurveIncrement = vCurveIncrement = 6;
	}
	
	/**
	 * Creates a surface grid with the specified numbers of patches in the U and V directions
	 * and with a specified number of subpatches per patch (in each direction).  Initially, all vertices of the surface are
	 * undefined.  They can be set by calling {@link #setVertex(int, int, Vector3D)}.
	 * @param uPatchCount the number of major patches in the U direction.  The number of subpatches is subPatchesPerPatch*uPatchCount.  Must be positive.
	 * @param vPatchCount the number of major patches in the V direction.  The number of subpatches is subPatchesPerPatch*vPatchCount.  Must be positive.
	 * @param subPatchesPerPatch the number of sub-patches that lie along each side of a patch.  Note that the actual number of
	 * subpatches in a patch is the square of this number. 
	 */
	public Grid3D(int uPatchCount, int vPatchCount, int subPatchesPerPatch) {
		this.subPatchesPerPatch = subPatchesPerPatch;
		setPatchCount(uPatchCount, vPatchCount);
		uCurveIncrement = vCurveIncrement = subPatchesPerPatch;
	}
	
	/**
	 * Sets the number of patches in each direction.  This is called only by the contructor -- it is not
	 * possible to change any of this data after the object is constructed.
	 */
	private void setPatchCount(int uPatchCount, int vPatchCount) {
		this.uPatchCount = uPatchCount;
		this.vPatchCount = vPatchCount;
		uCount = 1 + uPatchCount * subPatchesPerPatch;
		vCount = 1 + vPatchCount * subPatchesPerPatch;
		pointsOnSurface = new Vector3D[uCount][vCount];
	}
	
	/**
	 * Adds a transformed copy of the basic grid to the surface.
	 * @param transform A transform matrix that is applied to the basic grid to produce a transformed copy.
	 * This should be non-null (but null values are simply ignored).
	 */
	public void addGridTransform(GridTransformMatrix transform) {
		if (transform == null)
			return;
		if (transforms == null)
			transforms = new ArrayList<GridTransformMatrix>();
		transforms.add(transform);
		transformedData = transformedVCurves = null;
		patches = subpatches = null;
		patchesValid = subPatchesValid = false;
	}
	
	/**
	 * Discards any grid transforms that have been added by {@link #addGridTransform(GridTransformMatrix)}.
	 */
	public void discardGridTransforms() {
		if (transforms != null) {
			transforms = null;
			transformedData = transformedVCurves = null;
			patches = subpatches = null;
			patchesValid = subPatchesValid = false;
		}
	}
	
	/**
	 * Gets the value of the useIdentityTransform property.
	 * @see #setUseIdentityTransform(boolean)
	 */
	public boolean getUseIdentityTransform() {
		return useIdentityTransform;
	}

	/**
	 * Sets the value of the useIdentityTransform property.  The default value is true.  This
	 * property has an effect only if one or more extra transformed copied of the basic grid have been added 
	 * with the {@link #addGridTransform(GridTransformMatrix)} method.  If the value of this property is set to
	 * false, then the basic grid is NOT part of the data -- that is, the surface represented by this
	 * Grid3D object consists ONLY of the transformed copies of the basic grid.
	 */
	public void setUseIdentityTransform(boolean useIdentityTransform) {
		if (useIdentityTransform != this.useIdentityTransform) {
			this.useIdentityTransform = useIdentityTransform;
			patches = subpatches = null;
			patchesValid = subPatchesValid = false;
		}
	}

	/**
	 * Assign one of the points in the basic grid.
	 * @param uIndex The first index of the array position that is being set.  
	 * This index ranges from 0 to the value of {@link #getUCount()} minus 1.
	 * @param vIndex The second index of the array position that is being set.  
	 * This index ranges from 0 to the value of {@link #getVCount()} minus 1.
	 * @param v The vector the gives the coordinates of the point on the surface.  A pointer to this vector, not a copy 
	 * of the vector, is stored.  This parameter can be null, indicating a missing point on the surface.  In that case,
	 * any grid line or patch that has the point as a vertex is not drawn.
	 */
	public final void setVertex(int uIndex, int vIndex, Vector3D v) {
		pointsOnSurface[uIndex][vIndex] = v;
	}
	
	/**
	 * Get one of the points in the basic grid.
	 * @see #setVertex(int, int, Vector3D)
	 */
	public final Vector3D getVertex(int uIndex, int vIndex) {
		return pointsOnSurface[uIndex][vIndex];
	}
	
	/**
	 * Gets the number of vertices along the U direction of the basic grid.  This number is one plus the total number of
	 * subpatches in that direction, and is computed as 1 + uPatchCount*subPatchesPerPatch.  Note that vertices
	 * are numbered from 0 to <code>getUCount()-1</code> while subpatches are numbered from 0 to <code>getUCount()-2</code>
	 * in the U direction.
	 */
	public int getUCount() {
		return uCount;
	}

	/**
	 * Gets the number of (major) patches in the U direction, as specfied in the contructor or in {@link #setPatchCount(int, int)}.
	 * The number of vertices in the U direction is this value multiplied by subPatchesPerPatch.
	 */
	public int getUPatchCount() {
		return uPatchCount;
	}

	/**
	 * Gets the number of vertices along the V direction of the basic grid.  This number is one plus the total number of
	 * subpatches in that direction, and is computed as 1 + vPatchCount*subPatchesPerPatch.  Note that vertices
	 * are numbered from 0 to <code>getVCount()-1</code> while subpatches are numbered from 0 to <code>getVCount()-2</code>
	 * in the U direction.
	 */
	public int getVCount() {
		return vCount;
	}

	/**
	 * Gets the number of (major) patches in the V direction, as specfied in the contructor or in {@link #setPatchCount(int, int)}.
	 * The number of subpatches in the V direction is this value multiplied by subPatchesPerPatch.
	 */
	public int getVPatchCount() {
		return vPatchCount;
	}

	/**
	 * Gets the total number of subpatches in the basic grid.  This is equal to <code>(getUCount()-1)*(getVCount()-1)</code>.
	 * It is also the same as <code>getPatchCount()*subPatchesPerPatch*subPatchesPerPatch</code>
	 */
	public int getSubPatchCountInGrid() {
		return (uCount-1) * (vCount-1);
	}
	
	/**
	 * Gets the total number of (major) patches in the basic grid. This is equal to <code>getUPatchCount()*getVPatchCount()</code>
	 */
	public int getPatchCountInGrid() {
		return uPatchCount*vPatchCount;
	}
	
	/**
	 * Gets the total number of subpatches in the surface, which might consist of several copies of the baic grid.  
	 * This is equal to the number of copies of the gird times <code>(getUCount()-1)*(getVCount()-1)</code>.
	 */
	public int getSubPatchCountInSurface() {
		int copies = 1;
		if (transforms != null) {
			copies = transforms.size();
			if (useIdentityTransform)
				copies++;
		}
		return copies*(uCount-1) * (vCount-1);
	}
	
	/**
	 * Gets the total number of (major) patches in the surface, which might consist of several copies of the baic grid.  
	 * This is equal to the number of copies of the grid  times <code>getUPatchCount()*getVPatchCount()</code>
	 */
	public int getPatchCountInSurface() {
		int copies = 1;
		if (transforms != null) {
			copies = transforms.size();
			if (useIdentityTransform)
				copies++;
		}
		return copies*uPatchCount*vPatchCount;
	}
	
	/**
	 * Gets the number of subpatches that lie along each edge of a (major) patch, as specified in the constructor.
	 */
	public int getSubpatchesPerPatch() {
		return subPatchesPerPatch;
	}

	/**
	 * Gets the U increment value, which is used to decide which grid lines to draw.
	 * @see #setUCurveIncrement(int)
	 */
	public int getUCurveIncrement() {
		return uCurveIncrement;
	}

	/**
	 * Sets the U increment value, which is used to decide which grid lines to draw.  When drawing u grid lines, the
	 * n-th line is drawn only if n is a multiple of uIncrement (except that the final grid line is always drawn).
	 * A value of zero indicates that no u grid lines are to be drawn, except that when both uIncrement and vIncrement
	 * are zero and a wireframe rendering is being drawn, uIncrement and vIncrement are both forced equal to
	 * subPatchesPerPatch, since otherwise nothing would be drawn.
	 * @param curveIncrement the U increment value.  Any value less than zero is changed to zero.
	 */
	public void setUCurveIncrement(int curveIncrement) {
		if (curveIncrement < 0)
			curveIncrement = 0;
		uCurveIncrement = curveIncrement;
	}

	/**
	 * Gets the V increment value, which is used to decide which grid lines to draw.
	 * @see #setUCurveIncrement(int)
	 */
	public int getVCurveIncrement() {
		return vCurveIncrement;
	}

	/**
	 * Sets the V increment value, which is used to decide which grid lines to draw.  When drawing v grid lines, the
	 * n-th line is drawn only if n is a multiple of uIncrement (except that the final grid line is always drawn).
	 * A value of zero indicates that no u grid lines are to be drawn, except that when both uIncrement and vIncrement
	 * are zero and a wireframe rendering is being drawn, uIncrement and vIncrement are both forced equal to
	 * subPatchesPerPatch, since otherwise nothing would be drawn.
	 * @param curveIncrement the V increment value.  Any value less than zero is changed to zero.
	 */
	public void setVCurveIncrement(int curveIncrement) {
		if (curveIncrement < 0)
			curveIncrement = 0;
		vCurveIncrement = curveIncrement;
	}
	
	/**
	 * Sets the intrinsic color of a subpatch.  If the specified color is null, then the default color is used.
	 * If no color is ever set for a patch, then the default color is used.  The color set by this
	 * method is used for both the front and back color of a patch.
	 * @param uIndex The u index of the subpatch, in the range 0 to <code>getUCount()-2</code>
	 * @param vIndex The v index of the subpatch, in the range 0 to <code>getVCount()-2</code>
	 * @param c The new color of the patch, or zero to use the default color.
	 * @see #setDefaultPatchColor(Color)
	 */
	public void setPatchColor(int uIndex, int vIndex, Color c) {
		if (patchColor == null) {
			if (c == null)
				return;
			patchColor = new Color[uCount][vCount];
		}
		patchColor[uIndex][vIndex] = c;
	}
	
	/**
	 * Gets the intrinsic color of a subpatch.
	 * @param uIndex The u index of the subpatch, in the range 0 to <code>getUCount()-2</code>
	 * @param vIndex The v index of the subpatch, in the range 0 to <code>getVCount()-2</code>
	 * @param frontFace Tells whether this is a front or back face
	 * @see #setPatchColor(int, int, Color)
	 */
	public Color getPatchColor(int uIndex, int vIndex, boolean frontFace) {
		if (patchColor == null) {
			if (frontFace || defaultBackColor == null)
				return defaultPatchColor;
			else 
				return defaultBackColor;
		}
		Color c = patchColor[uIndex][vIndex];
		return (c == null)? defaultPatchColor : c;
	}
	
	/**
	 * Resets all individual patch colors, as set by {@link #setPatchColor(int, int, Color)}, to
	 * the default value.
	 */
	public void clearPatchColors() {
		patchColor = null;
	}
	

	/**
	 * Set a normal vector at one of the vertices in the basic grid.  The vector is not meant to be 
	 * a unit vector.
	 * @see #getNormal(int, int)
	 * @see #getUnitNormal(int, int)
	 * @param uIndex the u index of the vertex, in the range 0 to {@link #getUCount()}, inclusive
	 * @param vIndex the v index of the vertex, in the range 0 to {@link #getVCount()}, inclusive
	 * @param normalVector the vector at vertex at position (uIndex,vIndex).  This can be null; in that
	 * case, an approximate normal vector is computed using vectors to neighboring points on the surface.
	 */
	public void setNormal(int uIndex, int vIndex, Vector3D normalVector) {
		if (normal == null)
			normal = new Vector3D[uCount+1][vCount+1];
		normal[uIndex][vIndex] = normalVector;
	}
	
	/**
	 * Returns a normal vector (not assumed to be a unit vector) at a specified vertex of the basic grid.
	 * If a non-null normal vector for this vertex was specified by {@link #setNormal(int, int, Vector3D)},
	 * then that vector is returned.  Otherwise, an approximate normal vector is computed vectors from
	 * the specified vertex to two neighboring vertices on the surface.
	 * @param uIndex the u index of the vertex, in the range 0 to {@link #getUCount()}, inclusive
	 * @param vIndex the v index of the vertex, in the range 0 to {@link #getVCount()}, inclusive
	 */
	public Vector3D getNormal(int uIndex, int vIndex){
		if (normal != null && normal[uIndex][vIndex] != null)
			return new Vector3D(normal[uIndex][vIndex]);
		Vector3D v1 = pointsOnSurface[uIndex][vIndex];
		Vector3D v2, v4;
		if (uIndex < uCount && vIndex < vCount) {
			v2 = pointsOnSurface[uIndex+1][vIndex];
			v4 = pointsOnSurface[uIndex][vIndex+1];
		}
		else if (uIndex == uCount && vIndex < vCount) {
			v2 = pointsOnSurface[uIndex][vIndex+1];
			v4 = pointsOnSurface[uIndex-1][vIndex];
		}
		else if (uIndex < uCount && vIndex == vCount) {
			v2 = pointsOnSurface[uIndex][vIndex-1];
			v4 = pointsOnSurface[uIndex+1][vIndex];
		}
		else {
			v2 = pointsOnSurface[uIndex-1][vIndex];
			v4 = pointsOnSurface[uIndex][vIndex-1];
		}
		Vector3D a = v2.minus(v1);
		Vector3D b = v4.minus(v1);
		Vector3D normal = a.cross(b);  
		return normal;
	}
	
	/**
	 * Returns a unit normal vector at a specified vertex in the basic grid.  The return value is
	 * obtained by normalizing the return value of {@link #getNormal(int, int)}.
	 * However, if this yields an undefined result, a default value of (1,0,0)
	 * is returned.
	 * @param uIndex the u index of the vertex, in the range 0 to {@link #getUCount()}, inclusive
	 * @param vIndex the v index of the vertex, in the range 0 to {@link #getVCount()}, inclusive
	 */
	public Vector3D getUnitNormal(int uIndex, int vIndex) {
		Vector3D N = getNormal(uIndex,vIndex);
		N.normalize();
		if (Double.isInfinite(N.x) || Double.isNaN(N.x) ||Double.isInfinite(N.y) || Double.isNaN(N.y) ||
				Double.isInfinite(N.z) || Double.isNaN(N.z))
			N = new Vector3D(1,0,0);
		return N;
	}
	
	/**
	 * Removes all normal vectors that were set using {@link #setNormal(int, int, Vector3D)}.
	 */
	public void clearNormals() {
		normal = null;
	}
	
	/**
	 * Set the default color that is used as the intrinsic color of a patch for which no other color has been
	 * set.  The default default patch color is white.  The default color is used for any patch for which
	 * no color has been set with {@link #setPatchColor(int, int, Color)}.
	 * @param c the default patch color; if null, white is used as the default color
	 */
	public void setDefaultPatchColor(Color c) {
		defaultPatchColor = (c == null)? Color.white : c;
	}
	
	/**
	 * Get the default patch color.
	 * @see #setDefaultPatchColor(Color)
	 */
	public Color getDefaultPatchColor() {
		return defaultPatchColor;
	}

	/**
	 * Get the default color for the back face of a patch.
	 * @see #setDefaultBackColor(Color)
	 */
	public Color getDefaultBackColor() {
		return defaultBackColor;
	}

	/**
	 * Set the default color for the back face of a patch.  This color is used
	 * only if no specific color has been set for the patch using the
	 * {@link #setPatchColor(int, int, Color)} method.
	 * @param defaultBackColor The default backface color; if null, the back color is the same as the front color.
	 */
	public void setDefaultBackColor(Color defaultBackColor) {
		this.defaultBackColor = defaultBackColor;
	}


	// ------------------------- methods for rendering the surface -------------------------------
	
	/**
	 * Applies a transform the all vertices of the surface, to produce a set of transformed vertices.
	 * It must be called before the drawing routines in this class are called.
	 * This method, like the drawing routines in this class, is called by the surface drawing routines in 
	 * the View3D and View3DLit classes -- most programmers will use those routines to draw the surface
	 * and will not have to call this routine directly.
	 * @param view3D used for clipping
	 */
	public void applyTransform(Transform3D transform, View3D view3D) {
		int copies = 1;
		if (transforms != null) {
			copies = transforms.size();
			if (useIdentityTransform)
				copies++;
		}
		if (transformedData == null || transformedData.length != copies) {
			transformedData = new Vector3D[copies][uCount][vCount];
			transformedVCurves = new Vector3D[copies][vCount][uCount];
		}
		int offset = 0;
		if (transforms == null || useIdentityTransform) {
			for (int i = 0; i < uCount; i += 1) {
				for (int j = 0; j < vCount; j += 1) {
					if (pointsOnSurface[i][j] == null || view3D.clip(pointsOnSurface[i][j]))
						transformedData[0][i][j] = null;
					else {
						if (transformedData[0][i][j] == null)
							transformedData[0][i][j] = new Vector3D();
						transform.objectToViewCoords(pointsOnSurface[i][j],transformedData[0][i][j]);
					}
					transformedVCurves[0][j][i] = transformedData[0][i][j];
				}
				offset = 1;
			}
		}
		if (transforms != null) {
			for (int copyNum = offset; copyNum < copies; copyNum++) {
				GridTransformMatrix gridTransform = transforms.get(copyNum-offset);
				for (int i = 0; i < uCount; i += 1) {
					for (int j = 0; j < vCount; j += 1) {
						if (pointsOnSurface[i][j] == null)
							transformedData[copyNum][i][j] = null;
						else {
							Vector3D gridPt = gridTransform.apply(pointsOnSurface[i][j]);
							if (view3D.clip(gridPt))
								transformedData[copyNum][i][j] = null;
							else {
								if (transformedData[copyNum][i][j] == null)
									transformedData[copyNum][i][j] = new Vector3D();
								transform.objectToViewCoords(gridPt,transformedData[copyNum][i][j]);
							}
						}
						transformedVCurves[copyNum][j][i] = transformedData[copyNum][i][j];
					}
				}
			}
		}
		patchesValid = subPatchesValid = false;
	}
	
	/**
	 * Draws a wireframe rendering of the surface by drawing some or all of the grid lines in the U and V
	 * directions.  The choice of which lines to draw is determined by the uCurveIncrement and vCurveIncrement
	 * properties.  This method is called by the drawing routines in the View3D and View3DLit classes, and
	 * will not generally be called directly.  This must be called after {@link #applyTransform(Transform3D, View3D)}
	 * has been called.
	 * @see #setUCurveIncrement(int)
	 * @see #setVCurveIncrement(int)
	 */
	public void drawCurves(View3D view, Graphics2D g) {
		int uInc = uCurveIncrement;
		int vInc = vCurveIncrement;
		if (uInc == 0 && vInc == 0)
			uInc = vInc = subPatchesPerPatch;
		if (uInc > 0) {
			for (int copyNum = 0; copyNum < transformedData.length; copyNum++) {
				for (int i = 0; i < uCount-1; i += uInc)
					drawCurve(transformedData[copyNum][i], view, g);
				drawCurve(transformedData[copyNum][uCount-1], view, g);
			}
		}
		if (vInc > 0) {
			for (int copyNum = 0; copyNum < transformedVCurves.length; copyNum++) {
				for (int i = 0; i < vCount-1; i += vInc)
					drawCurve(transformedVCurves[copyNum][i], view, g);
				drawCurve(transformedVCurves[copyNum][vCount-1], view, g);
			}
		}
	}

	/**
	 * Draws some or all of the subpatches that make up the surface as shaded, lighted color patches.
	 * This method is called by {@link View3DLit#drawSurface(Grid3D, double, double)} and will probably not
	 * be used directly.  If it is, it is essential that {@link #applyTransform(Transform3D, View3D)} be called
	 * before this method is called.  The startPercent and endPercent are in the range 0 to 1, and they
	 * specify the subset of patches that are to be drawn.
	 * Lighting settings from the View3DLit are applied to the patches that are drawn.
	 * This method must be called after {@link #applyTransform(Transform3D, View3D)}.
	 */
	public void drawSubPatches(View3DLit view, Graphics2D g, double startPercent, double endPercent) {
		computeSubPatches();
		drawPatches(subpatches, 1, view, view.getPhongShading(), startPercent, endPercent);
	}
		
	/**
	 * Draws a "rough" version of the surface by drawing the (major) patches rather than the subpatches.
	 * The drawing uses flat shading; other than that, lighting settings are taken from the View3DLit.
	 * This method is called by {@link View3DLit#drawSurface(Grid3D, double, double)} and will probably not
	 * be used directly.  If it is, it is essential that {@link #applyTransform(Transform3D, View3D)} be called
	 * before this method is called.
	 */
	public void drawMajorPatches(View3DLit view, Graphics2D g) {
		computeMajorPatches();
		drawPatches(patches, subPatchesPerPatch, view, false, 0, 1);
	}
		
	
	private void computeSubPatches() { 
		if (subpatches == null || !subPatchesValid) {
			if (subpatches == null) {
				subpatches = new ArrayList<Patch>((uCount-1)*(vCount-1));
				for (int copyNum = 0; copyNum < transformedData.length; copyNum++) {
					for  (int i = 0; i < uCount-1; i++)
						for (int j = 0; j < vCount-1; j++)
							subpatches.add(new Patch(copyNum,i,j,1));
				}
			}
			for (int i = 0; i < subpatches.size(); i++)
				subpatches.get(i).compute();
			Collections.sort(subpatches);
			subPatchesValid = true;
			if (subPatchesPerPatch == 1) {
				patches = subpatches;
				patchesValid = true;
			}
		}
	}
	
	private void computeMajorPatches() {
		if (patches == null || !patchesValid) {
			if (patches == null) {
				patches = new ArrayList<Patch>(transformedData.length*(uPatchCount-1)*(vPatchCount-1));
				for (int copyNum = 0; copyNum < transformedData.length; copyNum++)
					for  (int i = 0; i < uCount-1; i += subPatchesPerPatch)
						for (int j = 0; j < vCount-1; j += subPatchesPerPatch)
							patches.add(new Patch(copyNum,i,j,subPatchesPerPatch));
			}
			for (int i = 0; i < patches.size(); i++)
				patches.get(i).compute();
			Collections.sort(patches);
			patchesValid = true;
			if (subPatchesPerPatch == 1) {
				subpatches = patches;
				subPatchesValid = true;
			}
		}
	}

	// ------------------------  private implementation section---------------------------------------
	
	private void drawCurve(Vector3D[] transformedCurve, View3D view, Graphics2D g) {
		GeneralPath curve = new GeneralPath();
		boolean previousPoint = false;
		boolean broken = false;
		for (int i = 0; i < transformedCurve.length-1; i += 1) {
			if (transformedCurve[i] != null) { 
				if ( ! previousPoint ) {
					curve.moveTo((float)transformedCurve[i].x, (float)transformedCurve[i].y);
				}
				else {
					curve.lineTo((float)transformedCurve[i].x, (float)transformedCurve[i].y);
				}
			}
			previousPoint = transformedCurve[i] != null;
			if (!previousPoint)
				broken = true;
		}
		if (!broken && transformedCurve[transformedCurve.length-1] != null
				&& Math.abs(transformedCurve[0].x - transformedCurve[transformedCurve.length-1].x) < 1e-5
				&& Math.abs(transformedCurve[0].y - transformedCurve[transformedCurve.length-1].y) < 1e-5)
			curve.closePath();
		else if (previousPoint && transformedCurve[transformedCurve.length-1] != null)
			curve.lineTo((float)transformedCurve[transformedCurve.length-1].x, (float)transformedCurve[transformedCurve.length-1].y);
		g.draw(curve);

	}
	
	
	private class Patch implements Comparable<Patch> {  // This is really a referenece to a patch or subpatch -- data is in the Grid3D object

		private int copyNum;  // Which copy of the grid does this patch belong to?
		private GridTransformMatrix gridTransform;  // The transform used for this copy.
		private int uIndex, vIndex;  // The subpatch indices in the u and v direction
		private int size; // this will be either 1 for a subpatch or subPatchesPerPatch for a (major) patch
		
		private double z;  // average z-value of transformed midpoints -- computed when compute() is called
		
		private Patch(int copyNum, int uIndex, int vIndex, int size) {
			this.copyNum = copyNum;
			this.uIndex = uIndex;
			this.vIndex = vIndex;
			this.size = size;
			if (transforms != null) {
				if (copyNum > 0 || (copyNum == 0 && !useIdentityTransform)) {
					if (useIdentityTransform)
						gridTransform = transforms.get(copyNum - 1);
					else
						gridTransform = transforms.get(copyNum);
				}
			}
		}
		
		public Vector3D getVertex(int i) {
			Vector3D pt;  // Untransformed point
			switch (i) {
			case 1: pt = pointsOnSurface[uIndex][vIndex]; break;
			case 2: pt = pointsOnSurface[uIndex+size][vIndex]; break;
			case 3: pt = pointsOnSurface[uIndex+size][vIndex+size]; break;
			case 4: pt = pointsOnSurface[uIndex][vIndex+size]; break;
			default: throw new IllegalArgumentException("Illegal vertex number \"" + i + "\" for patch; must be 1, 2, 3, or 4.");
			}
			if (pt == null || gridTransform == null)
				return pt;
			else
				return gridTransform.apply(pt);
 		}
		
		public Vector3D getTransformedVertex(int i) {
			switch (i) {
			case 1: return transformedData[copyNum][uIndex][vIndex];
			case 2: return transformedData[copyNum][uIndex+size][vIndex];
			case 3: return transformedData[copyNum][uIndex+size][vIndex+size];
			case 4: return transformedData[copyNum][uIndex][vIndex+size];
			default: throw new IllegalArgumentException("Illegal vertex number \"" + i + "\" for patch; must be 1, 2, 3, or 4.");
			}
		}
		
		private void compute() {
			Vector3D v1 = getTransformedVertex(1);
			Vector3D v2 = getTransformedVertex(2);
			Vector3D v3 = getTransformedVertex(3);
			Vector3D v4 = getTransformedVertex(4);
			if (v1 == null || v2 == null || v3 == null || v4 == null)
				z = Double.POSITIVE_INFINITY;
			else {
				z = (v1.z + v2.z + v3.z + v4.z) / 4;
				if (Double.isInfinite(z))
					z = Double.POSITIVE_INFINITY;
			}
		}
		
		public double getMidpointZ() {
			return z;  // Will be Double.NaN if one of the vertices of this patch is not defined.
		}
		
		/**
		 * Returns a unit normal vector for the "upper left" vertex.  It is obtained
		 * by calling {@link Grid3D#getUnitNormal(int, int)}.
		 * @return
		 */
		public Vector3D getUnitNormal() {
			Vector3D N = Grid3D.this.getUnitNormal(uIndex,vIndex);
			if (gridTransform != null) {
				N = gridTransform.applyToNormal(N);
			}
			return N;
		}
				
		public int compareTo(Patch p) {
			if (z > p.z)
				return 1;
			else if (z == p.z)
				return 0;
			else
				return -1;
		}
		
	}
	
	
	
	// ---------------------------------- DRAWING THE PATCHES -------------------------------------------
	
	
	
	private void drawPatches(ArrayList<Patch> patches, int spacing, View3DLit view, boolean usePhongShading, double startPercent, double endPercent) {
		Polygon quad = new Polygon();
		Point2D pt = new Point2D.Double();
		Transform3D transform = view.getTransform3D();
		Graphics2D g = transform.getUntransformedGraphics(); // so I can use pixel coords for drawing
		Object saveAntialiased = g.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
		boolean viewIsAntialiased = view.getAntialiased();
		int opaqueness = (int)(255*(1-view.getTransparency()));
		if (startPercent < 0)
			startPercent = 0;
		if (endPercent > 1)
			endPercent = 1;
		int validPatchCount = patches.size();
		while (validPatchCount > 0 && patches.get(validPatchCount-1).getMidpointZ() == Double.POSITIVE_INFINITY)
			validPatchCount--;
		if (validPatchCount == 0)
			return;
		int startIndex, endIndex;
		if (startPercent <= 0)
			startIndex = 0;
		else
			startIndex = (int)(validPatchCount*startPercent);
		if (endPercent >= 1)
			endIndex = validPatchCount;
		else
			endIndex = (int)(validPatchCount*endPercent);
		for (int i = startIndex; i < endIndex; i++) {
			Patch patch = patches.get(i);
			if (!Double.isInfinite(patch.z)) {  // patch.z will be infinite only if the patch can't be drawn because one of the vertices is undefined
	
				int x1, y1, x2, y2, x3, y3, x4, y4;  // integer pixel coordinates of the vertices of the patch.
	
				Vector3D v1 = patch.getTransformedVertex(1);
				Vector3D v2 = patch.getTransformedVertex(2);
				Vector3D v3 = patch.getTransformedVertex(3);
				Vector3D v4 = patch.getTransformedVertex(4);
				pt.setLocation(v1.x,v1.y);
				transform.windowToViewport(pt);
				x1 = (int)(pt.getX() + 0.4999);
				y1 = (int)(pt.getY() + 0.4999);
				pt.setLocation(v2.x,v2.y);
				transform.windowToViewport(pt);
				x2 = (int)(pt.getX() + 0.4999);
				y2 = (int)(pt.getY() + 0.4999);
				pt.setLocation(v3.x,v3.y);
				transform.windowToViewport(pt);
				x3 = (int)(pt.getX() + 0.4999);
				y3 = (int)(pt.getY() + 0.4999);
				pt.setLocation(v4.x,v4.y);
				transform.windowToViewport(pt);
				x4 = (int)(pt.getX() + 0.4999);
				y4 = (int)(pt.getY() + 0.4999);
	
				Color saveColor = g.getColor();
	
				Color c = computeColorForPatch(patch,view);
				if (opaqueness < 255)
					c = new Color(c.getRed(),c.getGreen(),c.getBlue(),opaqueness);
				g.setColor(c);
	
				quad.reset();          // draw the inside of the patch
				quad.addPoint(x1,y1);
				quad.addPoint(x2,y2);
				quad.addPoint(x3,y3);
				quad.addPoint(x4,y4);
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF); // force off for patch drawing
				g.fillPolygon(quad);
				//g.drawPolygon(quad);  // I found this was needed to cover all the pixels -- shouldn't be necessary and won't work with transparency
				if (viewIsAntialiased)
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON); // restore if is set on for the view
	
				g.setColor(view.getViewStyle() == View3D.RED_GREEN_STEREO_VIEW ? Color.white : Color.black);  // grid line color
				if (uCurveIncrement > 0) {
					if (patch.uIndex % uCurveIncrement == 0)
						g.drawLine(x1,y1,x4,y4);
					if ((patch.uIndex+spacing) % uCurveIncrement == 0 || patch.uIndex+spacing >= uCount-1)
						g.drawLine(x2,y2,x3,y3);
				}
				if (vCurveIncrement > 0) {
					if (patch.vIndex % vCurveIncrement == 0)
						g.drawLine(x1,y1,x2,y2);
					if ((patch.vIndex+spacing) % vCurveIncrement == 0 || patch.vIndex+spacing >= vCount-1)
						g.drawLine(x4,y4,x3,y3);
				}
				g.setColor(saveColor);
			}
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,saveAntialiased); 
		}
	}

	private Color computeColorForPatch(Patch patch, View3DLit view) {
		Vector3D viewDirection = view.getTransform3D().getViewDirection();
		Vector3D v1 = patch.getVertex(1);  // untransformed vertices
		Vector3D v2 = patch.getVertex(2);
		Vector3D v3 = patch.getVertex(3);
		Vector3D v4 = patch.getVertex(4);
		Vector3D patchCG = (v1.plus(v2.plus(v3.plus(v4)))).times(0.25);
		Vector3D normal = patch.getUnitNormal();
		boolean frontFace;
		if (view.getOrientation() == View3DLit.NO_ORIENTATION)
			frontFace = true;
		else { 
			if (view.getOrthographicProjection())
				frontFace = normal.dot(viewDirection) > 0;
			else
				frontFace = normal.dot(patchCG.minus(view.getTransform3D().getViewPoint())) > 0;
			if (view.getOrientation() == View3DLit.REVERSE_ORIENTATION)
				frontFace = !frontFace;
		}
		Color intrinsicColor = getPatchColor(patch.uIndex,patch.vIndex,frontFace);
		if (view.getLightingEnabled())
			return PhongLighting.phongLightingColor(normal,view,view.getTransform3D(),patchCG,intrinsicColor);
		else
			return intrinsicColor;
	}

	
	
}
