/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import vmm.actions.AbstractActionVMM;
import vmm.actions.ActionItem;
import vmm.actions.ActionList;

/**
 * An Exhibit represents a mathematical object that can be drawn on the screen.  An Exhibit
 * is rendered by a {@link vmm.core.View}, which drawns the Exhibit in a {@link vmm.core.Display}.
 * An Exhibit emits ChangeEvents when it changes in a way that requires redrawing.  View
 * objects listen for these events to decide when to do a redraw.  The infrastructure for
 * generating these events and for setting up event listening is built into the Exhibit,
 * View, Display, {@link vmm.core.Decoration} and {@link vmm.core.Parameter} classes so
 * that ordinary programmers will not have to worry about it.
 * <p>Exhibit s declared as an abstract class, since it doesn't make sense to use it directly.
 * However, it does not contain any abstract methods.  Concrete subclasses should provide
 * some way of drawing an actual Exhibit, most likely by overriding the <code>doDraw</code> method
 * (but possibly by creating a specialized View class to do the drawing or by some other technique).
 * <p>Concrete subclasses can use the <code>addParameter</code>, <code>setDefaultWindow</code>,
 * <code>setDefaultForeground</code>, and <code>setDefaultBackground</code> to customize the
 * Exhibit.  The subclass can override the <code>getActionsForView</code>, <code>getViewCommandsForView</code>,
 * <code>getSettingsCommandsForView</code>, <code>getCreateAnimation</code>, <code>getMorphingAnimation</code>,
 * and <code>createAxes</code> methods, in addtion to <code>doDraw</code> and possibly <code>computeDrawData</code>.
 * <p>Note that to work correctly with the XML save/restore facility defined in {@link vmm.core.SaveAndRestore},
 * a subclass of Exhibit must have a parameterless constructor, and it must be an independent (not nested) class.
 */
abstract public class Exhibit implements Parameterizable, Decorateable, ChangeListener {
	
	private ArrayList<ChangeListener> changeListeners;
	private ArrayList<View> views;  // Views that are displaying this exhibit (most likely same contents as changeListeners)
	private ChangeEvent changeEvent;
	private String name;
	private Color defaultForeground = Color.black;
	private Color defaultBackground = Color.white;
	
	@VMMSave
	private double[] defaultWindow;
	
	@VMMSave
	private int framesForMorphing = 50;
	
	@VMMSave
	private boolean useFilmstripForMorphing;
	
	protected boolean isMorphing;
	protected View morphingView;
	
	/**
	 * Parameters that have been associated with this Exhibit (presumably by the
	 * {@link #addParameter(Parameter)} method).  This variable will ordinarly be
	 * manipulated through the <code>addParameter</code> method and other parameter-related
	 * methods defined in this class rather than directly.
	 */
	protected ArrayList<Parameter> parameters;
	
	/**
	 * Decorations that have been associated with this Exhibit (presumably through the
	 * {@link #addDecoration(Decoration)} method).  This variable will ordinarily be
	 * manipualted through methods defined in this class rather than directly.
	 */
	protected ArrayList<Decoration> decorations;
	
	/**
	 * This variable is set to true when the Exhibit changes in a way that needs redrawing.
	 * This is done when the {@link #forceRedraw()} method is called, and it should only
	 * be set to true by calling that method, which also arranges for the actual redrawing
	 * to be done.  The value of this variable is passed into the 
	 * {@link #computeDrawData(View, boolean, Transform, Transform)}
	 * method, where it should be checked to determine whether the Exhibit has changed.
	 */
	protected boolean exhibitNeedsRedraw = true;
	
	/**
	 * Used to record the Transform that is used when the Exhibit is drawn, so that it can be
	 * compared with the Transform used the next time the Exhibit is drawn.  This is passed into
	 * the {@link #computeDrawData(View, boolean, Transform, Transform)} method, and it should not
	 * be necessary for ordinary subclasses to use this variable directly.
	 */
	protected Transform previousTransform;




	/**
	 * Returns a name for this Exhibit.  By default, the name that is returned
	 * will be the same as the name of the class to which the Exhibit object belongs.
	 * However, a different name can be set using the setName() method.  The name
	 * is intended to be something that can be used internally to distinguish one
	 * Exhibit from other Exhibits.  For a human-readable name, the getTitle() method 
	 * should be used instead of getName().
	 */
	public String getName() {
		if (name == null)
			return this.getClass().getName();
		else
			return name;	
	}

	/**
	 * Sets the name for this view. In general, the default name, consisting of the full class
	 * name of the Exhibit object, should be sufficient.
	 * @see #getName()
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns a human-readable title for this Exhibit that could, for example, be used in
	 * a menu that is presented to the user.  The title is always obtained by using the name
	 * returned by <code>getName</code> as a key into the default resource bundle.  This allows
	 * for easy internationalization.  Note that if no resource bundle is found or if
	 * the resource bundle does not contain the specified key, then the value returned
	 * by getTitle() will be the same as the value returned by <code>getName</code>.
	 * @see #getName()
	 * @see I18n#tr(String)
	 */
	public String getTitle() {
		return I18n.tr(getName());
	}
	
	/**
	 * Tells how many frames are to be used by a morphing animation, as returned by
	 * {@link #getMorphingAnimation(View, int)}.
	 */
	public int getFramesForMorphing() {
		return framesForMorphing;
	}

	/**
	 * Set the number of frames to be used in a moprphing animation, as returned by
	 * {@link #getMorphingAnimation(View, int)}.  The value must be greater than 1.
	 * Values less than or equal to 1 are ignored.  The default value is 50.
	 */
	public void setFramesForMorphing(int framesForMorphing) {
		if (framesForMorphing > 1)
			this.framesForMorphing = framesForMorphing;
	}
	
	/**
	 * Returns the value of the useFilmstripForMorping property.
	 * @see #setUseFilmstripForMorphing(boolean)
	 */
	public boolean getUseFilmstripForMorphing() {
		return useFilmstripForMorphing;
	}

	/**
	 * Sets the value of the useFilmstripForMorphing property.  If this property is true,
	 * then a morphing animation for this exhibit will be set to create a filmstrip, so
	 * that each frame of the animation only needs to be computed once.
	 * @see TimerAnimation#setUseFilmstrip(boolean)
	 */
	public void setUseFilmstripForMorphing(boolean useFilmstripForMorphing) {
		this.useFilmstripForMorphing = useFilmstripForMorphing;
	}	
	
	//----------------- XML IO -------------------------------------------------------
	
	/**
	 * This method is called when an XML representation of this exhibit is being constructed by the 
	 * {@link SaveAndRestore} class to give the exhibit a chance to write any extra infomation that
	 * is not saved by default.  Any Parameters associated with the exhibit are
	 * saved automatically.   Note that Decorations associated with the exhibit
	 * are NOT saved automatically UNLESS the decoration class is marked with a {@link vmm.core.VMMSave} 
	 * annotation.  Property variables will also be saved automatically IF they are marked
	 * with {@link vmm.core.VMMSave} annotations.
	 * <p>The method in this top level class does nothing. 
	 * <p>When a subclass overrides this method, it should ordinarily start by calling
	 * super.addExtraXML(containingDocument,exhibitElement) to make sure that information from the superclass
	 * is saved.
	 * @param containingDocument The overall XML document that contains the exhibit Element that is being created.
	 * This parameter is necessary because it is needed to create any nested subelements that are to be added
	 * to the exhibit element.
	 * @param exhibitElement The XML element that is being constructed.  This element already exists; the
	 * purpose of this method to add any extra information that would be needed to reconstruct this exhibit
	 * object from the XML represenation.
	 * @see #readExtraXML(Element)
	 */
	public void addExtraXML(Document containingDocument, Element exhibitElement) {
	}
	
	/**
	 * This method is called when this exhibit is being reconstructed from an XML representation by the
	 * {@link SaveAndRestore} class.  The Exhibit object has already been created, and default information
	 * (parameters and decorations) have been retrieved. This method is responsible
	 * for retrieving any data that was written by {@link #addExtraXML(Document, Element)},
	 * except that properties written with {@link SaveAndRestore#addProperty(Object, String, Document, Element)}
	 * are retrieved automatically, and decorations saved with {@link SaveAndRestore#addDecorationElement(Document, Element, Decoration)}
	 * are retrieved automatically.
	 * The method in this top-level Exhibit class does nothing.
	 * <p>In general, when a subclass overrides this method, it should be sure to call
	 * super.readExtraXML(exhibitInfo).
	 * @param exhibitInfo The &lt;exhibit&gt; element from the XML file that contains the information about this
	 * exhibit.  Some methods from the {@link SaveAndRestore} class might be useful for getting the data.
	 * @throws IOException If an error is found, an exception of type IOException should be thrown.
	 * This will abort the whole processing of the XML file.
	 * @see #addExtraXML(Document, Element)
	 */
	public void readExtraXML(Element exhibitInfo) throws IOException {
	}
	
			
	//----------------- support for ChangeEvents --------------------------------------
	
	/**
	 * Add a ChangeListener to this Exhibit.
	 * Change events are sent when the exhibit needs to be redrawn.  This is set up
	 * automatically in the VMM core.  It should not
	 * be necessary for ordinary programmers to call or to override this method.
	 * @param listener The listener to be added to the exhibit.  If the value is null, nothing is done.
	 */
	synchronized public void addChangeListener(ChangeListener listener) {
		if (listener == null)
			return;
		if (changeListeners == null)
			changeListeners = new ArrayList<ChangeListener>();
		changeListeners.add(listener);
	}
	
	/**
	 * Remove a ChangeListener from this Exhibit.
	 * Change events are sent when the exhibit needs to be redrawn.  This is set up
	 * automatically in the VMM core.  It should not
	 * be necessary for ordinary programmers to call or to override this method.
	 * @param listener The listener to be removed.  If it is not currently registered with the
	 * Exhibit, then nothing is done.
	 */
	synchronized public void removeChangeListener(ChangeListener listener) {
		if (listener != null && changeListeners != null) {
			changeListeners.remove(listener);
			if (changeListeners.isEmpty())
				changeListeners = null;
		}
	}
	
	/**
	 * Sends a ChangeEvent to registered ChangeListeners.
	 * A sub-class should call either this method or the <code>forceRedraw</code> method when
	 * the Exhbit is changed and needs to be redrawn.  The change event is
	 * propagagted to the Views that are displaying this Exhibit, which will respond by redrawing.  The difference
	 * between <code>fireExhibitChangeEvent</code> and <code>forceRedraw</code>  is that <code>forceRedraw</code>
	 * will force recomputation of cached data in the exhibit by setting the value of the variable
	 * {@link #exhibitNeedsRedraw} to true.  The <code>fireExhibitChangeEvent</code> method does not do this.
	 * <p>If the exhibit is being "morphed", the change event might be propagated only to the view in which the
	 * morphing is being displayed and to other views, if any, in the same display.  
	 * This will be at least true if the <code>getMorphingAnimation</code> method 
	 * in this class is called with a non-null View parameter.  This is to prevent other views of the
	 * exhibit from redrawing themselves when parameters change during a morph.
	 * @see #forceRedraw()
	 * @see #getMorphingAnimation(View, int)
	 */
	synchronized protected void fireExhibitChangeEvent() { 
		if (changeListeners == null)
			return;
		if (changeEvent == null)
			changeEvent = new ChangeEvent(this);
		if (isMorphing && morphingView != null) {
			for (int i = 0; i < changeListeners.size(); i++) {
				if (changeListeners.get(i) instanceof View) {
					View v = (View)changeListeners.get(i);
					if (v.getDisplay() == morphingView.getDisplay())
						v.stateChanged(changeEvent);
				}
			}
		}
		else {
			for (int i = 0; i < changeListeners.size(); i++)
				changeListeners.get(i).stateChanged(changeEvent);
		}
	}
	
	/**
	 * Sends a change event, presumably in response to a change in one of the
	 * Decorations that have been added to this Exhibit.
	 * An Exhibit listens for change events from Decorations that have been added
	 * to the Exhibit.  The Exhibit responds by firing a change event in turn,
	 * which will go to any Views that are displaying the Exhibit.  This will
	 * cause the Views to redraw.  This method is not meant to be called directly.
	 * @see #addDecoration(Decoration)
	 */
	public void stateChanged(ChangeEvent evt) {
		fireExhibitChangeEvent();
	}
	
	//------------------------------- Parameter Handling --------------------------
	
	/**
	 * Returns an array containing all the parameters associated with this Exhibit.
	 * Note that when this is a {@link UserExhibit}, the array returned by this
	 * method does <b>not</b> include the function parameters associated with the
	 * user exhibit; see {@link UserExhibit.Support#getFunctionParameters()}.
	 * No parameters are defined by the Exhibit class itself.  Subclasses should add
	 * parameters using the {@link #addParameter(Parameter)} method.  Note that in the array returned 
	 * by this method, parameters are listed in the REVERSE of the order in which
	 * they were added to the Exhibit.
	 * @return An array containing the parameters associated with this Exhibit; these are
	 * the actual parameters, not copies.  Changing a parameter will ordinarily cause
	 * the Exhibit to be redrawn to reflect the change.  The return value is non-null.  If
	 * there are no parameters, a zero-length array is returned.
	 */
	public Parameter[] getParameters() {
		if (parameters == null)
			return new Parameter[] {};
		else {
			Parameter[] params = new Parameter[parameters.size()];
			for (int i = params.length-1; i >= 0; i--)
				params[params.length-i-1] = parameters.get(i);
			return params;
		}
	}
	
	/**
	 * Returns a paramter that is associated with this Exhibit and that has the specified name, if there is one.
	 * If the name is null, or if there is no parameter with the specified name, then the
	 * return value is null.
	 */
	public Parameter getParameterByName(String name) {
		if (parameters != null && name != null) {
			for (int i = 0; i < parameters.size(); i++)
				if (name.equals(parameters.get(i).getName()))
					return parameters.get(i);
		}
		return null;
	}
	
	/**
	 * Associate a parameter with this Exhibit.  This method is protected, so it can only be
	 * called by a subclass.  This Exhibit is set to be the owner of the Parameter, so that
	 * the Exhibit will be notified when the Parameter changes.  A Parameter change will cause the
	 * Exhibit to be redrawn.
	 * @param param The parameter to be added.  If the parameter is null, it is ignored.  Adding a
	 * parameter for a second time has no effect.
	 * @see #getParameters()
	 */
	protected void addParameter(Parameter param) {
		if (param == null)
			return;
		if (parameters == null)
			parameters = new ArrayList<Parameter>();
		if (parameters.contains(param))
			return;
		parameters.add(param);
		param.setOwner(this);
	}
	
	
	/**
	 * Removes a parameter from this Exhibit.  The method is protected, so it can only be used by
	 * subclasses (and, in fact, it will probably only be used in a few circumstances).
	 * @param param The parameter to be removed.  If the value is null or if the parameter is
	 * not associated with this Exhibit, then nothing is done.
	 */
	protected void removeParameter(Parameter param) {
		if (param == null || parameters == null || (!parameters.contains(param)))
			return;
		parameters.remove(param);
		param.setOwner(null);
	}
	
	/**
	 * This method will be called automatically when a parameter that has been added to this Exhibit
	 * is changed.  It should not ordinarily be called directly.  Note that in fact, this method
	 * simply calls <code>forceRedraw</code>.  This method is defined in the <@link Parameterizable} interface.
	 * @see #forceRedraw()
	 */
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
		forceRedraw();
	}
	
	//--------------------------- Actions and Settings Commands ----------------------------------------
	
	
	/**
	 * This method returns a list of {@link ActionItem} that can be applied to a View that
	 * contains this Exhibit.  If the view parameter is null, it should return actions
	 * that are appropriate in any View.
	 * View is presumably one that is displaying this Exhibit.
	 * <p>The items in the returned list must implement the ActionItem interface and
	 * generally belong to one of the classes {@link vmm.actions.AbstractActionVMM},
	 * {@link vmm.actions.ToggleAction}, {@link vmm.actions.ActionRadioGroup}, or
	 * {@link vmm.actions.ActionList}.  Null items represent separators.	 
	 * <p>In this top-level Exhibit class, the return value is a list that contains
	 * one object of type {@link AbstractActionVMM}.  The action represents the "Create" command, which is meant to run the
	 * "create animation" of the Exhibit.
	 * <p>In general, when overriding this method, subclasses should call "super.getActionsForView()"
	 * to obtain a list of actions from the superclass. It can then add additional
	 * actions or remove or disable actions that are in the list from the superclass.
	 */
	public ActionList getActionsForView(final View view) {
		ActionList actions = new ActionList();
		AbstractActionVMM createAction = new AbstractActionVMM(I18n.tr("vmm.core.commands.Create"), "K") {
				public void actionPerformed(ActionEvent evt) {
					Animation create = getCreateAnimation(view);
					if (create != null) {
						if (view != null && view.getDisplay() != null)
							view.getDisplay().installAnimation(create);
						else
							create.start();
					}
					else
						forceRedraw();  // will force build animation to run, even if there is no create animation.
				}
			};
		actions.add(createAction);
		return actions;
	}
	
	/**
	 * Returns a list of "settings commands" that can be applied to this Exibit, when displayed 
	 * in the specified View. 
	 * <p>The items in the returned list must implement the ActionItem interface and
	 * generally belong to one of the classes {@link vmm.actions.AbstractActionVMM},
	 * {@link vmm.actions.ToggleAction}, {@link vmm.actions.ActionRadioGroup}, or
	 * {@link vmm.actions.ActionList}.  Null items represent separators.
	 * The idea is that the list items will be added to a menu or otherwise presented to the user
	 * so that the user can invoke the commands (with ActionList items representing submenus).
	 * <p>In this top-level Exhibit class, the list contains several commands 
	 * that call up dialogs where the user can change various settings.  (These include:
	 * "Set Parameters", "Set Morphing", "Set Number of Frames", and "Set Visible Ranges".)
	 * Note that for these commands, the View parameter is used simply to get the View's Display,
	 * which is used as the parent of the dialog.
	 * <p>In general, when overriding this method, subclasses should call "super.getSettingsCommandsForView()"
	 * to obtain a list of commands from the superclass. It can then add additional
	 * commands or remove or disable commands that are in the list from the superclass.
	 */
	public ActionList getSettingsCommandsForView(final View view) {
		ActionList commands = new ActionList();
		AbstractActionVMM setParametersAction = new AbstractActionVMM(I18n.tr("vmm.core.commands.SetParameters")) {
			public void actionPerformed(ActionEvent evt) {
				view.getDisplay().stopAnimation();
				Parameter[] params = view.getViewAndExhibitParameters();
				ParameterDialog.showDialog(view.getDisplay(),I18n.tr("vmm.core.dialogtitle.SetParameters"),params);
			}
		};
		AbstractActionVMM setAnimationParametersAction = new AbstractActionVMM(I18n.tr("vmm.core.commands.SetAnimationParameters")) {
			public void actionPerformed(ActionEvent evt) {
				view.getDisplay().stopAnimation();
				Parameter[] params = view.getViewAndExhibitParameters();
				AnimationLimitsDialog.showDialog(view.getDisplay(),I18n.tr("vmm.core.dialogtitle.SetAnimationParameters"),params);
			}
		};
		AbstractActionVMM setNumberOfFramesAction = new AbstractActionVMM(I18n.tr("vmm.core.commands.SetNumberOfFrames")) {
			public void actionPerformed(ActionEvent evt) {
				view.getDisplay().stopAnimation();
				SetNumberOfFramesDialog.showDialog(view);
			}
		};
		AbstractActionVMM setXYLimitsAction = new AbstractActionVMM(I18n.tr("vmm.core.commands.SetXYWindow")) { 
			public void actionPerformed(ActionEvent evt) {
				view.getDisplay().stopAnimation();
				SetXYWindowDialog.showDialog(view);
			}
		};
		Parameter[] params = view.getViewAndExhibitParameters();
		boolean animated = false;
		for (int i = 0; i < params.length; i++)
			if (params[i] instanceof Animateable) {
				animated = true;
				break;
			}
		setParametersAction.setEnabled(params.length > 0);
		setAnimationParametersAction.setEnabled(animated);
		setNumberOfFramesAction.setEnabled(animated);
		commands.add(setParametersAction);
		commands.add(setAnimationParametersAction);
		commands.add(setNumberOfFramesAction);
		commands.add(setXYLimitsAction);  // NOTE:  LatticeModels.java depends on this being the last item in the list!
		return commands;
	}

	
	/**
	 * This method returns a list of {@link ActionItem} that will be added to
	 * the Animation menu of the 3dxm applicaiton.  The values in the list should
	 * ordinarily be commands (i.e. {@link vmm.actions.AbstractActionVMM})
	 * that run animations (although there is no way to enforce
	 * this restriction).  These animations commands are in addition to the standard "Morph"
	 * and "Cyclic Morph" commands.  Note that these commands are added to those returned
	 * by {@link View#getAdditionalAnimations()}.
	 * <p>In this top-level Exhibit class, the return value is a an empty list  (but not null).  
	 * In general, when overriding this method, subclasses should call "super.getAdditionalAnimationsForView()"
	 * to obtain a list of actions from the superclass. It can then add additional
	 * actions or remove or disable actions that are in the list from the superclass.
	 * @param view The view in which the animations will be run.
	 */
	public ActionList getAdditionalAnimationsForView(View view) {
		return new ActionList();
	}
	
	/**
	 * Adds a view to the list of views that are displaying this Exhibit.  This method is called by
	 * the view when the exhibit is added to the view; it is not meant to be called directly.
	 * @param view The view that that is about to start displaying this Exhibit.
	 * @see View#setExhibit(Exhibit)
	 */
	public void addView(View view) {
		if (view == null)
			return;
		if (views == null)
			views = new ArrayList<View>();
		if (! views.contains(view))
			views.add(view);
	}
	
	/**
	 * Removes a view from the list of views that are displaying this Exhibit.  This method is called
	 * by the view when the exhibit is removed from the view; it is not meant to be called directly.
	 * @param view The view that is no longer displaying this exhibit.
	 */
	public void removeView(View view) {
		if (views != null)
			views.remove(view);
	}
	
	/**
	 * Returns a list of Views that are displaying this Exhibit.  The return value can be null.
	 */
	public ArrayList<View> getViews() {
		return views;
	}

	//--------------------------------------- Decorations ------------------------------------
	
	/**
	 * Add a decoration that will appear in all Views of this Exhibit.  Of course, the
	 * decoration must be one that is compatible with this Exhibit.  Note that adding
	 * and removing decortions will fire ChangeEvents that will cause the Exhibit
	 * to be redrawn.  However, it will not force
	 * recomputation of cached data for the Exhibit.  (This is, it does not call {@link #forceRedraw()}.)
	 * @param d The decoration to be added.  If this is null or has already been added to the Exhibit, 
	 * then nothing is done.  The Exhibit is added
	 * as a ChangeListener to the Decoration.  When the decoration sends a ChangeEvent
	 * to the Exhibit, it will in turn send a change event to any Views that are showing
	 * the Exhibit, which will cause the Views to be redrawn.
	 */
	public void addDecoration(Decoration d) {
		if (d == null)
			return;
		if (decorations == null)
			decorations = new ArrayList<Decoration>();
		if (decorations.contains(d))
			return;
		decorations.add(d);
		d.addChangeListener(this);
		fireExhibitChangeEvent();
	}
	
	/**
	 * Remove a specified Decoration, if present.
	 * @param d The decoration to be removed.  If this is null or or has not been added to this Exhibit then nothing is done.
	 * @see #addDecoration(Decoration)
	 */
	public void removeDecoration(Decoration d) {
		if (decorations != null && d != null && decorations.contains(d)) {
			decorations.remove(d);
			d.removeChangeListener(this);
			if (decorations.size() == 0)
				decorations = null;
			fireExhibitChangeEvent();
		}
	}
	
	/**
	 * Remove all decorations from the Exhbiit.
	 * @see #addDecoration(Decoration)
	 */
	public void clearDecorations() {
		if (decorations != null) {
			for (int i = 0; i < decorations.size(); i++)
				decorations.get(i).removeChangeListener(this);
			decorations = null;
			fireExhibitChangeEvent();
		}
	}
	
	/**
	 * Returns a list of decorations that have been added directly to this exhibit.
	 * @return a non-null array, possibly of length zero, containing the decorations.
	 */
	public Decoration[] getDecorations() {
		if (decorations == null)
			return new Decoration[0];
		else {
			Decoration[] decs = new Decoration[decorations.size()];
			decorations.toArray(decs);
			return decs;
		}
	}
	
	//--------------------------------------- Drawing ----------------------------------------
	
	/**
	 * This method returns a default View object for viewing this Exhibit.  The method in
	 * this top-level Exhibit class returns an object belonging to the generic top-level View class.
	 * This method will often be overidden in subclasses to return a more appropriate View. 
	 * The return value of this method should always be non-null.
	 */
	public View getDefaultView() {
		return new View();
	}
	
	/**
	 * This method returns additionAal View objects that can be used to view this Exhibit, in addition to
	 * the view returned by {@link #getDefaultView()}.  A return value of null indicates that there are no
	 * alternative views of this Exhibit.  (It seems like alternative views will be very rare.)
	 * <p>If there are alternative views of an Exhibit, then it is a good idea that each view be assigned
	 * a name (using {@link View#setName(String)}) that can be used to distinguish among the views.  This is
	 * important for the 3dxm shell program, since it presents the names of the views to the user in a
	 * menu.  Both the default view and the alternative views should have names.
	 * @return An array containing alternative views for this exhibit.  In the top-level Exhibit class,
	 * the return value is null.
	 */
	public View[] getAlternativeViews() {
		return null;
	}
	
	/**
	 * Returns a four-element array rerpresening the default Window in the xy-plane in
	 * which this Exhibit wants to be displayed.  The default value is the
	 * array (-5,5,-5,5).  This can be changed with the <code>setDefaultWindow</code> method.
	 * The four values in the array are the minumin x-value, the maximum x-value, the
	 * minimumn y-value, and the maximum y-value, in that order.  In the View class,
	 * when an Exhibit is installed in a View, the View's window is set to the
	 * Exhibit's default window.
	 */
	public double[] getDefaultWindow() {
		if (defaultWindow != null)
			return defaultWindow;
		else
			return new double[] { -5, 5, -5, 5 };
	}
	
	/**
	 * Set the default window in the xy-plane for viewing this Exhibit.
	 * @see #getDefaultWindow()
	 */
	public void setDefaultWindow(double xmin, double xmax, double ymin, double ymax) {
		defaultWindow = new double[] { xmin, xmax, ymin, ymax };
	}
	
	/**
	 * Sets the default window in the xy-plane for viewing this Exhibit, using
	 * xy-limits from an array.  The array must be non-null and must contain at least 4 elements, 
	 * or an exception will be thrown.
	 * This method just calls <code>setDefaultWindow(window[0],window[1],window[2],window[3])</code>.
	 * @see #getDefaultWindow()
	 */
	public void setDefaultWindow(double[] window) {
		setDefaultWindow(window[0], window[1], window[2], window[3]);
	}
	
	/**
	 * Returns a default Transform object to be used in the specified View.
	 * The View calls this method when the Exhibit is installed in the View.
	 * The Transform object that is returned should be a newly created object,
	 * since the View will use and modify the object that is returned, not a copy.
	 * @param view The View is provided as a parameter since there might be several
	 * types of View appropriate for a given exhibit, and they might require 
	 * different Transforms.  In particular, for example, a 3D View will require a 3D Transform.
	 * @return In this top-level Exhibit class, the Transform that is returned 
	 * is a 2D Transform created by calling "new Transform(xmin,xmax,ymin,ymax)" where the values
	 * of xmin, xmax, ymin, and ymax are taken from the array returned
	 * by <code>getDefaultWindow</code>.
	 * @see #getDefaultWindow()
	 * @see Transform#Transform(double, double, double, double)
	 */
	public Transform getDefaultTransform(View view) {
		double[] window = getDefaultWindow();
		return new Transform(window[0],window[1],window[2],window[3]);
	}
	
	/**
	 * Returns the default background color desired by this Exhibit. The value in this class is Color.white,
	 * but can be changed by calling <code>setDefaultBackground</code>.
	 * <p>This method is called in the Display class to set the background color
	 * when a new exhibit is installed.  It is not likely to be called directly.
	 */
	public Color getDefaultBackground() {
		return defaultBackground;
	}
	
	/**
	 * Set the default background color for this Exhibit.  This would ordinarily be called
	 * in the constructor of a sub-class. <b>Note</b> that setting a default background color
	 * with this method automatically sets the default foreground
	 * color to black or white, depending on the default background color.  If you want
	 * a different default foreground color, call {@link #setDefaultForeground(Color)}
	 * <b>after</b> callind <code>setDefaultBackground</code>.
	 * @param c The default background color.  If this is null, the default (white) is used.
	 */
	public void setDefaultBackground(Color c) {
		if (c == null)
			defaultBackground = Color.white;
		else
			defaultBackground = c;
		if (defaultBackground.getRed() < 150 && defaultBackground.getGreen() < 120 && defaultBackground.getBlue() < 150)
			setDefaultForeground(Color.white);
		else 
			setDefaultForeground(Color.black);
	}
	
	/**
	 * Returns the default foreground color desired by this Exhibit.  The value in this class is Color.black
	 * but can be changed by calling <code>setDefaultForeground</code>.
	 * <p>This method is called in the Display class to set the foregournd color
	 * when a new exhibit is installed.  It is not likely to be called directly.
	 */
	public Color getDefaultForeground() {
		return defaultForeground;
	}
	
	/**
	 * Set the default foreground color for this Exhibit.  This would ordinarily be called
	 * in the constructor of a sub-class.  <b>Note</b> that setting a default background color
	 * with {@link #setDefaultBackground(Color)} automatically sets the default foreground
	 * color to black or white, depending on the default background color.  If you want
	 * a different default foreground color, call <code>setDefaultForeground</code>
	 * <b>after</b> calling <code>setDefaultBackground</code>.
	 * @param c The default foreground color.  If this is null, the default (black) is used.
	 */
	public void setDefaultForeground(Color c) {
		if (c == null)
			defaultForeground = Color.black;
		else
			defaultForeground = c;
	}
	
	/**
	 * This method is called to "create" the Exhibit.  It should return an animation
	 * that will show the process of creating the Exhibit, whatever that means.  The animation
	 * is run when the Exhibit is first shown on the screen (in the 3dxm shell program) and when the "Create" action
	 * command is invoked.  If the
	 * return value is null, then there is no creation animation for this exhibit.  If
	 * the return value is non-null, then it can be expected that the animation will be
	 * installed in the View's Display.
	 * @param view A View that is displaying this Exhibit.  The animation, if any, that
	 * is returned by this method will be installed in the View's display.  If the view
	 * is null, than a creation animation that can run independently of a view could
	 * be returned.
	 * @return A creation animation for the given View of this Exhibit.  The return value
	 * can be null to indicate that no creation animation is to be run.
	 */
	public Animation getCreateAnimation(View view) {
		return null;
	}
	
	
	/**
	 * This method is meant to produce a "morph" animation for this Exhibit, by animating
	 * any applicable animateable parameters.  The parameters can come from the Exhibit,
	 * from the View, and, in the case of a {@link UserExhibit} from the function
	 * parameters of the user exhibit. If
	 * there are no Animateable parameters, the return value is null.  Otherwise,
	 * the return value is a {@link BasicAnimator} to which all of the Exhibit's {@link Animateable}
	 * parameters have been added.   The number
	 * of frames in the animation returned by this method is the value of the "framesForMorphing"
	 * property.  Subclasses might want to override this method to provide
	 * more complex morphing animations, but they should follow the same pattern.  In particular,
	 * any parmeter values that are changed during the animation should be restored to their
	 * original values when the animation ends.
	 * @param view The view that the animation will be shown in, if any.  If the value is
	 * null, then the animation will not be associated with any particular view.  In that
	 * case, the Morphing animation will animate the Exhibit in all Views in which it appears, since
	 * it operates by changing parameters of the exhibit.  If the View is non-null, then only the
	 * specified View will receive changeEvents from the Exhibit while the morph is in progress.
	 * @param looping  This must be one of the values <code>BasicAnimator.ONCE</code>, <code>BasicAnimator.LOOP</code>,
	 * or <code>BasicAnimator.OSCILLATE</code>.  The value is passed, without any error checking, to
	 * the animation's <code>setLooping</code> method.
	 * @see BasicAnimator#setLooping(int)
	 * @see #setFramesForMorphing(int)
	 */
	public BasicAnimator getMorphingAnimation(final View view, int looping) {
		Parameter[] parameters = view.getViewAndExhibitParameters();
		if (parameters == null)
			return null;
		boolean animated = false;
		for (int i = 0; i < parameters.length; i++)
			if (parameters[i] instanceof Animateable && ((Animateable)parameters[i]).reallyAnimated()) {
				animated = true;
				break;
			}
		if ( ! animated )
			return null;
		final BasicAnimator anim = new BasicAnimator(framesForMorphing) {
			public void animationStarting() {
				morphingView = view;
				isMorphing = true;
			}
			public void animationEnding() {
				morphingView = null;
				isMorphing = false;
			}
		};
		if (useFilmstripForMorphing) {
			anim.setUseFilmstrip(true);
			anim.setMillisecondsPerFrame(100);
		}
		for (int i = 0; i < parameters.length; i++)
			if (parameters[i] instanceof Animateable)
				anim.addAnimatedItem((Animateable)parameters[i]);
		anim.setLooping(looping);
		return anim;
	}
	
	/**
	 * Returns a "build animation" for this Exhibit, or null if no build animation is to be used.
	 * A build animation is a very special animation that shows a bit-by-bit drawing of an Exhibit over
	 * a period of time.  It uses the off-screen canvas facility provided by 
	 * {@link View#prepareOSIForDrawing()}.  An example of this is displaying a surface by drawing
	 * patches of the surface in back-to-front order.
	 * <p>This method is called by the {@link #render(Graphics2D, View, Transform, ArrayList)} method
	 * before calling {@link #computeDrawData(View, boolean, Transform, Transform)}.  If the return
	 * value is non-null, then the build animation is installed in the view as an alternative to
	 * drawing the exhibit directly.  However, no build animation is installed if an animation is
	 * already in progress in the view's display, or if the {@link View#getFastDrawing()} method of
	 * the view returns true, or if the View is not using an off=screen image.
	 * <p>An Exhibit that has a build animation does not need a separate creation animation, since the
	 * build animation will be invoked automatically when the object is first drawn and when the Create
	 * command is given.
	 * @param view The view into which the build animation will be installed.
	 * @return The return value in this top-level Exhibit class is null.
	 */
	public Animation getBuildAnimation(View view) {
		return null;
	}
	
	/**
	 * This method forces the Exhibit to be completely redrawn by marking it as needing to be
	 * redrawn and sending a change event to any ChangeListeners that have beeen registered with
	 * the Exhibit, presumably just to any Views that are showing this Exhibit.  Note that Views
	 * will be forced to redraw their off-screen images.   This method is
	 * called automatically when a Parameter that has been added to this Exhibit is changed.
	 * Note that calling this method will cause data cached in the Exhibit to be recomputed.
	 * In fact, this method simply sets {@link #exhibitNeedsRedraw} to true and then calls
	 * {@link #fireExhibitChangeEvent()}.
	 */
	public void forceRedraw() {
		exhibitNeedsRedraw = true;
		fireExhibitChangeEvent();
	}
	
	/**
	 * This method is called by the View class when it needs to redraw the Exhibit.
	 * (Subclasses of the View class might not call this method -- a View class that
	 * is created just for a particular type of Exhibit might draw the Exhibit directly.)
	 * This method will not ordinarily be called directly, now will it be overridden in 
	 * most subclasses.
	 * <p>The method in this class does the following:  First, it calls the
	 * Exhibit's <code>computeDrawData</code> method and then the <code>computeDrawData</code> method of
	 * any decoration that has been associated with the View or with the Exhibit.
	 * Then, it calls the <code>doDraw</code> method of the Exhibit and Decorations; these are
	 * called in the order of the Decorations' layers with the Exhibit being considered
	 * as lying in layer number 0.  Subclasses
	 * will ordinarily override code>computeDrawData</code> and/or <code>doDraw</code>, rather than overriding
	 * this method.
	 * @see #forceRedraw()
	 * @see #computeDrawData(View, boolean, Transform, Transform)
	 * @see #doDraw(Graphics2D, View, Transform)
	 * @see #getBuildAnimation(View)
	 * @see View#render(Graphics2D, int, int)
	 * @param g The graphics context where the exhibit is to be drawn.  The drawing
	 * area has already been cleared to the background color (assuming that this method is called
	 * from the <code>render</code> method in the top-level View class).
	 * @param view The View that is drawing the Exhibit; this object contains other
	 * information that might be of use, such as the Display, if any, associated with
	 * the View.
	 * @param transform Contains information about the rectangular area in the
	 * xy-plane that is being drawn and about the rectangle of pixels in the graphics
	 * context where it is drawn.  Note that at least for the top-level View class,
	 * transform.getX() and transform.getY() can be assumed to be zero.
	 * @param extraDecorations Decorations that were added to the View that is being drawn;
	 * the value can be null.
	 */
	public void render(Graphics2D g, View view, Transform transform, ArrayList<Decoration> extraDecorations) {
		int decorationCount = (decorations == null? 0 : decorations.size()) + (extraDecorations == null? 0 : extraDecorations.size());
		Decoration[] decs = null;
		if (decorationCount > 0) {  // gather all decorations into an array sorted by layer number
			int decsIndex = 0;
			decs = new Decoration[decorationCount];
			if (decorations != null) {
				for (int i = 0; i < decorations.size(); i++) {
					int j = 0;
					Decoration d = decorations.get(i);
					while (j < decsIndex && d.getLayer() >= decs[j].getLayer())
						j++;
					for (int k = decsIndex; k > j; k--)
						decs[k] = decs[k-1];
					decs[j] = d;
					decsIndex++;
				}
			}
			if (extraDecorations != null) {
				for (int i = 0; i < extraDecorations.size(); i++) {
					int j = 0;
					Decoration d = extraDecorations.get(i);
					while (j < decsIndex && d.getLayer() >= decs[j].getLayer())
						j++;
					for (int k = decsIndex; k > j; k--)
						decs[k] = decs[k-1];
					decs[j] = d;
					decsIndex++;
				}
			}
		}
		Animation builder = null;
		if (!view.getFastDrawing() && view.getDisplay() != null && 
				view.getDisplay().getStatus() != Display.STATUS_ANIMATION_RUNNING && 
				view.getDisplay().getStatus() != Display.STATUS_ANIMATION_PAUSED ) {
			builder = getBuildAnimation(view);
		}
		computeDrawDataHook(view,transform);
		if (decs != null) {
			for (int i = 0; i < decs.length; i++) {
				decs[i].computeDrawData(view,exhibitNeedsRedraw,previousTransform,transform);
				decs[i].decorationNeedsRedraw = false;
			}
		}
		exhibitNeedsRedraw = false;
		int i = 0;
		if (decs != null) {
			while (i < decs.length && decs[i].getLayer() < 0) {
				decs[i].doDraw(g,view,transform);
				i++;
			}
		}
		if (builder == null) {
			doDrawHook(g,view,transform);
			if (decs != null) {
				while (i < decs.length) {
					decs[i].doDraw(g,view,transform);
					i++;
				}
			}
		}
		else { 
			final Animation buildAnim = builder;
			final View theView = view;
			Decoration[] remainingDecs = null;
			if (decs != null && i < decs.length) {
				remainingDecs = new Decoration[decs.length - i];
				for (int j = i; j < decs.length; j++)
					remainingDecs[j-i] = decs[j];
			}
			final Decoration[] theDecs = remainingDecs;
			view.installBuildAnimation(builder);
			builder.addChangeListener( new ChangeListener() {
				public void stateChanged(ChangeEvent evt) {
					if (!buildAnim.isRunning() && buildAnim == theView.getBuildAnimation()) {
						theView.installBuildAnimation(null);
						if (theDecs != null && theView.beginDrawToOffscreenImage()) {  
							for (int i = 0; i < theDecs.length; i++)
								theDecs[i].doDraw(theView.getTransform().getGraphics(),theView,theView.getTransform());
							theView.endDrawToOffscreenImage();
							theView.getDisplay().repaint();
						}
					}
				}
			});
		}
	}
	
	/**
	 * Called by render to compute the draw data for the exhibit.  This method just calls 
	 * {@link #computeDrawData(View, boolean, Transform, Transform)} and then records the
	 * prvious transform for use in the next call.   This method is called by
	 * {@link #render(Graphics2D, View, Transform, ArrayList)}.  It exists only as
	 * a hook to be overridded in the Exhibit3D class and probably will not be used
	 * except for that or perhaps other compex subclasses.
	 */
	protected void computeDrawDataHook(View view, Transform transform) {
		computeDrawData(view, exhibitNeedsRedraw, previousTransform, transform);
		previousTransform = (Transform)(transform.clone());
	}
	
	/**
	 * Called by render to this exhibit.  This method just calls 
	 * {@link #doDraw(Graphics2D, View, Transform)}   This method is called by
	 * {@link #render(Graphics2D, View, Transform, ArrayList)}.  It exists only as
	 * a hook to be overridded in the Exhibit3D class and probably will not be used
	 * except for that or perhaps other compex subclasses.
	 */
	protected void doDrawHook(Graphics2D g, View view, Transform transform) {
		doDraw(g,view,transform);
	}
	
    /**
     * Recompute any cached data in the Exhibit that might have changed since it was computed.
     * This method is called (indirectly through <code>computeDrawDataHook</code>)
     * by the <code>render</code> method before it calls <code>doDraw</code>.  Its purpose
     * is to give the Exhibit a chance to recalculate any data that it needs in order to
     * draw itself.  Not all Exhibits will need to "cache" computed data in this way.
     * The method in this top-level exhibit class does nothing. 
     * @see #render(Graphics2D, View, Transform, ArrayList)
     * @see #forceRedraw()
     * @see #doDraw(Graphics2D, View, Transform)
     * @param view The View that is drawing the Exhibit.
     * @param exhibitNeedsRedraw This parameter is set to "true" if the Exhibit's forceRedraw()
     * method has been called since the last redraw.  If it is true, then presumably the appearance of the Exhibit has
     * changed, so any cached data should be recomputed.
     * @param previousTransform The Transform object that was used the last time the Exhibit was
     * drawn.  This can be null if the Exhibit is being drawn for the first time.
     * @param newTransform The Transform object that is being used for this drawing of the Exhibit.
     * Transform objects contain data about the window in the xy-plane where the Exhibit is
     * being drawn and about the pixel coordinates on the drawing area.
     * The two Transform objects are provided in case any cached data depends on the xy-window
     * or on the pixel coordinates.  Note that the Transform might have changed even if exhibitNeedsRedraw is false.
     */
	protected void computeDrawData(View view, boolean exhibitNeedsRedraw, Transform previousTransform, Transform newTransform) {
	}
	
	/**
	 * Do the actual drawing of the exhibit.
	 * This method is called (indirectly through <code>doDrawHook</code>)
	 * by <code>render</code> to draw the Exhibit.  The <code>computeDrawData</code> method is called
	 * by <code>render</code> before it calls this method, so any cached data should be valid by the time this method
	 * is called.  The method in this class does nothing.  Subclasses should override this method, unless they provide
	 * some other technique for drawing the Exhibit.
     * @see #render(Graphics2D, View, Transform, ArrayList)
     * @see #computeDrawData(View, boolean, Transform, Transform)
	 * @param g The graphics context where the Exhibit is being drawn.  It has already been cleared
	 * to the background color (assuming that drawing is being done in the usual way, as defined in the
	 * top-level View class).
	 * @param view The View that is drawing the Exhibit; this object contains other
	 * information that might be of use, such as the Display, if any, associated with
	 * the View.
	 * @param transform Contains information about the rectangular area in the
	 * xy-plane that is being drawn and about the rectangle of pixels in the graphics
	 * context where it is drawn.  Note that at least for the top-level View class,
	 * transform.getX() and transform.getY() can be assumed to be zero.
	 */
	protected void doDraw(Graphics2D g, View view, Transform transform) {
	}

}