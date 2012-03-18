/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Decorations are extra visual components that can be added to an Exhibit, such as
 * a set of axes or a solution curve of an ODE.   Decorations can be added either to
 * Views or directly to Exhibits -- a decoration that is added to a View is visible 
 * only in that View.  A decoration that is added to an Exhibit is visible in all
 * Views of that Exhibit.
 * <p>When a Decoration is added to an Exhibit or View, that object will listen for
 * ChangeEvents from the Decoration.  These events will produce the necessary redrawing.
 * The listening is set up automatically when the Decoration is added.
 * <p>This is an abstract class, even though it has no abstract methods.  Subclasses
 * will probably override the <code>doDraw</code> method  and possibly the <code>computeDrawData</code> method.
 * However, subclasses might also provide some other method for drawing the decoration.
 * <p>Note that to work correctly with the XML save/restore facility defined in {@link vmm.core.SaveAndRestore},
 * a subclass of Decoration must have a parameterless constructor, and it must be an independent class or
 * a public nested class.
 * However, decorations are not saved by default, so this is not a consideration for all subclasses.
 */
abstract public class Decoration {

	private ArrayList<ChangeListener> changeListeners;
	private ChangeEvent changeEvent;
	
	@VMMSave
	private int layer = 1;
	
	/**
	 * This variable is set to true when the Decoration's <code>fireDecorationChangeEvent</code>
	 * method is called.  It can be used in the <code>computeDrawData</code> method by Decoration 
	 * subclasses that cache data, to decide whether or not to recompute that data.
	 */
	protected boolean decorationNeedsRedraw = true;

	/**
	 * Return the layer number for this decoration.  The layer number can be a
	 * positive or negative integer or zero.  Decorations with smaller (or more negative)
	 * layer numbers are drawn first.  The Exhibit itself is in layer zero, so decorations
	 * with negaitve layer numbers are drawn behind the exhibit and decorations with 
	 * positive layer numbers will overlay the exhibit.  For decorations in the same
	 * layer, the default behavior for 2D exhibits, as defined in the Exhibit class, is to draw
	 * the decorations in the same layer in the order in which they were added,
	 * except that decorations added directly to the Exhibit are drawn before those that 
	 * were added to the View.  For 3D exhibits, objects in the same layer should be treated
	 * as one object for the purpose of hidden surface removal. (Note:  Currently, this is not done.
	 * Decorations are simply drawn in order by layer, even for 3D exhibits.)
	 * <p>The default value for the layer is 1, which puts it
	 * on top of the Exhibit.
	 */
	public int getLayer() {
		return layer;
	}
	
	/**
	 * Set the layer in which this decoration is drawn.  The default layer is 1.
	 * @see #getLayer()
	 */
	public void setLayer(int layer) {
		if (this.layer != layer) {
			this.layer = layer;
			fireDecorationChangeEvent();
		}
	}
	
	/**
	 * Causes this decoration to be recomputed and redrawn.
	 */
	public void forceRedraw() {
		decorationNeedsRedraw = true;
		fireDecorationChangeEvent();
	}
	
	
    /**
     * This method is called by the <code>render</code> method in the Exhibit class before it calls 
     * this Decoration's <code>doDraw</code> method, in order to give the Decoration 
     * a chance to recalculate any cached data that it needs in order to draw itself.  
     * (Note that subclasses of the View and Exhibit class are not necessarily required to
     * follow this pattern.)  Not all Decorations will need to "cache" 
     * computed data in this way.  Decorations that do not cache data do not have to override this method.
     * The method in this top-level exhibit class does nothing.
     * <p>In general, a Decoration should check its <code>decorationNeedsRedraw</code> variable and
     * also check the <code>exhibitNeedsRedraw</code> parameter.  It should recompute cached
     * data if either of these is true.   A decoration might also need to compute data if
     * the Transform has changed, if it has cached any data that depends on the Transform.
     * @see Exhibit#render(Graphics2D, View, Transform, ArrayList)
     * @see #doDraw(Graphics2D, View, Transform)
 	 * @param view The View that is drawing the Exhibit; this object contains other
	 * information that might be of use, such as the Display where the Exhibit is drawn
	 * and the the Exhibit that is being drawn.
     * @param exhibitNeedsRedraw This parameter is set to "true" when the Exhibit changes.  
     * If it is true, then presumably the appearance of the Exhibit has
     * changed, so any cached data for this decoration should probably be recomputed.
     * @param previousTransform The Transform object that was used the last time the Exhibit was
     * drawn.  This can be null if the Exhibit is being drawn for the first time.
     * @param newTransform The Transform object that is being used for this drawing of the Exhibit.
     * Transform objects contain data about the window in the xy-plane where the Exhibit is
     * being drawn and about the pixel coordinates on the drawing area.
     * The two Transform objects are provided just in case any cached data depends on the xy-window
     * or on the pixel coordinates.
     */
	public void computeDrawData(View view, boolean exhibitNeedsRedraw, Transform previousTransform, Transform newTransform) {
	}
	
	/**
	 * This method is called by the <code>render</code> method of the Exhbit class to draw the Decoration.  
	 * The <code>computeDrawData</code> method is called by <code>render</code> before it calls this method, so any cached 
	 * data should be valid by the time this method is called.  (Note that subclasses of View and
	 * Exhibit are not necessarily required to follow this pattern.)  
	 * The method in this class does nothing.  Subclasses will ordinarily override this method to draw the Decoration.
     * @see Exhibit#render(Graphics2D, View, Transform, ArrayList)
     * @see #computeDrawData(View, boolean, Transform, Transform)
	 * @param g The graphics context where the Exhibit and its decorations are being drawn.
	 * @param view The View that is drawing the Exhibit; this object contains other
	 * information that might be of use, such as the Display, if any, associated with
	 * the View, and the the actual Exhibit that is being drawn.
	 * @param transform Contains information about the rectangular area in the
	 * xy-plane that is being drawn and about the rectangle of pixels in the graphics
	 * context where it is drawn.  Note that at least for the top-level View class,
	 * transform.getX() and transform.getY() can be assumed to be zero.
	 */
	public void doDraw(Graphics2D g, View view, Transform transform) {
	}

			
	//----------------- support for ChangeEvents --------------------------------------
	
	/**
	 * Change events are sent when the decoration needs to be redrawn.  This is set up
	 * automatically in the VMM core.  It should not
	 * be necessary for ordinary programmers to call this method.
	 */
	synchronized public void addChangeListener(ChangeListener listener) {
		if (listener == null)
			return;
		if (changeListeners == null)
			changeListeners = new ArrayList<ChangeListener>();
		changeListeners.add(listener);
	}
	
	/**
	 * Change events are sent when the decoration needs to be redrawn.  This is set up
	 * automatically in the VMM core.  It should not
	 * be necessary for ordinary programmers to call this method.
	 */
	synchronized public void removeChangeListener(ChangeListener listener) {
		if (listener != null && changeListeners != null) {
			changeListeners.remove(listener);
			if (changeListeners.isEmpty())
				changeListeners = null;
		}
	}
	
	/**
	 * This method should be called whenever the decoration changes and needs to be redrawn.
	 * Note that it is called by {@link #forceRedraw()}, and will not generally be
	 * called directly.
	 * The change event will be propagagted to any Views where the Decoration is
	 * displayed, causing them to be redrawn.
	 */
	synchronized protected void fireDecorationChangeEvent() { 
		if (changeListeners == null)
			return;
		if (changeEvent == null)
			changeEvent = new ChangeEvent(this);
		for (int i = 0; i < changeListeners.size(); i++)
			changeListeners.get(i).stateChanged(changeEvent);
	}
	
	//----------------- XML IO -------------------------------------------------------
	
	/**
	 * This method is called when an XML representation of this decoration is being created by the 
	 * {@link SaveAndRestore} class to give the decoration a chance to add any 
	 * extra inforamtion that is necesary to restore the state of the decoration.
	 * Note that variables that are marked with {@link vmm.core.VMMSave} are saved
	 * and restored automatically.
	 * <p>The method in this top-level Decoration class does nothing.
	 * <p>When a subclass overrides this method, it should ordinarily start by calling
	 * super.addExtraXML(containingDocument,decorationElement) to make sure that information from the superclass
	 * is saved.
	 * @param containingDocument The overall XML document that contains the decoration Element that is being created.
	 * This parameter is necessary because it is needed to create any nested sub-elements that are to be added
	 * to the decoration element.
	 * @param decorationElement The XML element that is being constructed.  This element already exists; the
	 * purpose of this method to add any extra information that would be needed to reconstruct this decoration
	 * object from the XML represenation.
	 * @see #readExtraXML(Element)
	 */
	public void addExtraXML(Document containingDocument, Element decorationElement) {
	}
	
	/**
	 * This method is called when this decoration is being reconstructed from an XML representation by the
	 * {@link SaveAndRestore} class.   This method is responsible
	 * for retrieving any data that was written by {@link #addExtraXML(Document, Element)},
	 * except that properties written with {@link SaveAndRestore#addProperty(Object, String, Document, Element)}
	 * are retrieved automatically.
	 * The method in this top-level Decoration class does nothing.
	 * <p>In general, when a subclass overrides this method, it should be sure to call
	 * super.readExtraXML(decorationInfo).
	 * @param decoratinInfo The &lt;decoration&gt; element from the XML file that contains the information about this
	 * decoration.  Some methods from the {@link SaveAndRestore} class might be useful for getting the data.
	 * @throws IOException If an error is found, an exception of type IOException should be thrown.
	 * This will abort the whole processing of the XML file.
	 * @see #addExtraXML(Document, Element)
	 */
	public void readExtraXML(Element decoratinInfo) throws IOException {
	}
	
			
}
