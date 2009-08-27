/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;

/**
 * A ToggleAction is an Action that has a boolean-valued property named "ToggleState".
 * The {@link #createCheckBox()} and {@link #createCheckBoxMenuItem()} methods return
 * components whose state is synchronized with each other and with the state of this
 * ToggleAction.  When one of these components generates an ActionEvent, the states of
 * the ToggleAction and of all the synchronized components are changed to match.
 * A {@link #getState()} method is provided as a convenience to make it possible to
 * determine the current state.  (Note that this is a concrete subclass of AbstractAction,
 * but its <code>actionPerformed</code> method is defined to do nothing.  It is still possible
 * to override this method if you want to perform some action when the state changes.)
 */
public class ToggleAction extends AbstractActionVMM {
	
	/**
	 *  This private class represents JCheckBoxMenuItems that are synchronized with the
	 *  ToggleAction.  The JCheckBoxMenu items listen for property change events from
	 *  the ToggleAction that occur when its state changes, and change their states to
	 *  match.  The ToggleAction listens for ActionEvents from the JCheckBoxMenuItems
	 *  that occur when the user changes the menu item's state, and changes its state
	 *  to match.
	 */
	private class ActionCheckBoxMenuItem extends JCheckBoxMenuItem implements PropertyChangeListener {
		ActionCheckBoxMenuItem() {
			super(ToggleAction.this);
			setSelected( ((Boolean)getValue("ToggleState")).booleanValue() );
			addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					Boolean oldVal = (Boolean)getValue("ToggleState");
					if (isSelected() != oldVal.booleanValue()) {
						Boolean newVal = new Boolean(isSelected());
						putValue("ToggleState", newVal);
						firePropertyChange("ToggleState",oldVal,newVal);
					}
				}
			});
			ToggleAction.this.addPropertyChangeListener(this);
		}
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals("ToggleState")) {
				boolean actionState = ((Boolean)getValue("ToggleState")).booleanValue();
				if (isSelected() != actionState)
					setSelected(actionState);
			}
		}
	}

	/**
	 *  This private class represents JCheckBoxes that are synchronized with the
	 *  ToggleAction.  The JCheckBoxes listen for property change events from
	 *  the ToggleAction that occur when its state changes, and change their states to
	 *  match.  The ToggleAction listens for ActionEvents from the JCheckBoxMenuItems
	 *  that occur when the user changes the menu item's state, and changes its state
	 *  to match.
	 */
	private class ActionCheckBox extends JCheckBox implements PropertyChangeListener {
		ActionCheckBox() {
			super(ToggleAction.this);
			setSelected( ((Boolean)getValue("ToggleState")).booleanValue() );
			addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					Boolean oldVal = (Boolean)getValue("ToggleState");
					if (isSelected() != oldVal.booleanValue()) {
						Boolean newVal = new Boolean(isSelected());
						putValue("ToggleState", newVal);
						firePropertyChange("ToggleState",oldVal,newVal);
					}
				}
			});
			ToggleAction.this.addPropertyChangeListener(this);
		}
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals("ToggleState")) {
				boolean actionState = ((Boolean)getValue("ToggleState")).booleanValue();
				if (isSelected() != actionState)
					setSelected(actionState);
			}
		}
	}
	
	/**
	 * Create a ToggleAction with no name or icon and initial state set to false.
	 */
	public ToggleAction() {
		this(null,null);
	}

	/**
	 * Create a ToggleAction with a given name but no icon and initial state set to false.
	 * @param name If non-null, this string will be set as the Name property of the Action.
	 */
	public ToggleAction(String name) {
		this(name,null);
	}

	/**
	 * Create a ToggleAction with a specified name and initial state, and no icon.
	 * @param name If non-null, this string will be set as the Name property of the Action.
	 * @param checked The initial value of the "ToggleState" property.
	 */
	public ToggleAction(String name, boolean checked) {
		super(name);
		putValue("ToggleState", new Boolean(checked));
	}

	/**
	 * Create a ToggleAction with a specified name and icon and with state initially set to false.
	 * @param name If non-null, this string will be set as the Name property of the Action.
	 * @param icon If non-null this icon will be set as the Icon property of the Action.
	 */
	public ToggleAction(String name, Icon icon) {
		super(name, icon);
		putValue("ToggleState", new Boolean(false));
	}
	
	/**
	 * This method implements the abstract method that is inherited from the AbstractAction class,
	 * but it is defined to do nothing.  If you want to perform some action when the state of
	 * the ToggleAction is changed because of a user action, you can override this method.  Note
	 * that this method is NOT called when the state is changed programmatically.
	 */
	public void actionPerformed(ActionEvent evt) {
	}
	
	/**
	 * Gets the current state of the ToggleAction.  This is just the boolean value
	 * associated with the "ToggleState" property of the Action.
	 */
	public boolean getState() {
		return ((Boolean)getValue("ToggleState")).booleanValue();
	}
	
	/**
	 * Sets the current state of the ToggleAction.  The state is just the "ToggleState"
	 * property of the Action; this method is just a convenience method that sets that
	 * property.
	 */
	public void setState(boolean state) {
		putValue("ToggleState", new Boolean(state));
	}

    /**
     * Return a JCheckBoxMenuItem whose selection state is synchronized with the state of this
     * ToggleAction and with other components returned by this method and by {@link #createCheckBox()}.
     */
	public JCheckBoxMenuItem createCheckBoxMenuItem() {
		return new ActionCheckBoxMenuItem();
	}
	
    /**
     * Return a JCheckBox whose selection state is synchronized with the state of this
     * ToggleAction and with other components returned by this method and by {@link #createCheckBoxMenuItem()}.
     */
	public JCheckBox createCheckBox() {
		return new ActionCheckBox();
	}

	/**
	 * Returns an array containing a single JCheckBoxMenuItem.  The menu item
	 * is constructed by calling {@link #createCheckBoxMenuItem()}.
	 */
	public JMenuItem[] getMenuItems() {
		return new JMenuItem[] { createCheckBoxMenuItem() };
	}
	

}
