/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.actions;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;

/**
 * An ActionRadioGroup represents a group of mutually exclusive options.  Only one of the
 * options can be selected at a given time; this rule is enforced by the group.  Methods are
 * provided for getting sets of JRadioButtons or JRadioButtonMenuItems to represent
 * the group of options; the user can manipulate one of the buttons or menu items to
 * change the currently selected option.  When the selected option changes because of
 * a user action, the {@link #optionSelected(int)} method is called; override this method
 * in a subclass to perform some action when the user changes the selection.
 */
public class ActionRadioGroup implements ActionItem {
	
	private ArrayList<AbstractAction> actions;
	private int selectedIndex; // currently selected option, given as an index into actions, or -1 to indicate no selection.
	private boolean enabled = true;
	
	/**
	 * This private class represents the menu items that are returned by the getRadioButtonMenuItems method.
	 */
	private class ActionRadioButtonMenuItem extends JRadioButtonMenuItem implements PropertyChangeListener {
		ActionRadioButtonMenuItem(final int index) { // index is an index into the actions list.
			super(actions.get(index));
			AbstractAction action = actions.get(index);
			setSelected( ((Boolean)action.getValue("RadioState")).booleanValue() );
			action.addPropertyChangeListener(this);
		}
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals("RadioState")) {
				boolean newState = ((Boolean)evt.getNewValue()).booleanValue();
				if (isSelected() != newState)
					setSelected(newState);
			}
		}
	}
	
	/**
	 * This private class represents the radio buttons that are returned by the getRadioButtonMenuItems method.
	 */
	private class ActionRadioButton extends JRadioButton implements PropertyChangeListener {
		ActionRadioButton(final int index) { // index is an index into the actions list.
			super(actions.get(index));
			AbstractAction action = actions.get(index);
			setSelected( ((Boolean)action.getValue("RadioState")).booleanValue() );
			action.addPropertyChangeListener(this);
		}
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals("RadioState")) {
				boolean newState = ((Boolean)evt.getNewValue()).booleanValue();
				if (isSelected() != newState)
					setSelected(newState);
			}
		}
	}
	
	/**
	 * Create an initially empty option group.
	 */
	public ActionRadioGroup() {
		this(null,-1);
	}
	
	/**
	 * Create a group of options in which initially no option is selected.
	 * @param actionNames Names for the options, which become names of menu items or names or radio buttons.
	 * Null values are OK and are meant to represent separators in a menu.  If actionNames is null, the
	 * list of actions is initially empty.
	 */
	public ActionRadioGroup(String[] actionNames) {
		this(actionNames,-1);
	}

	/**
	 * Create a group of options in which a specifed option is initially selected.
	 * @param actionNames Names for the options, which become names of menu items or names or radio buttons.
	 * Null values are OK and are meant to represent separators in a menu.  If actionNames is null, the
	 * list of actions is initially empty.
	 * @param selectedIndex The index in actionNames of the initially selected index, or -1 to indicate
	 * that no item is initially selected.  If this value is outside the range of indicies for actionNames,
	 * or if actionNames[selectedIndex] is null, then the value is silently changed to -1.
	 */
	public ActionRadioGroup(String[] actionNames, int selectedIndex) {
		if (actionNames == null || selectedIndex < 0 || 
				selectedIndex >= actionNames.length || actionNames[selectedIndex] == null)
			selectedIndex = -1;
		actions = new ArrayList<AbstractAction>();
		if (actionNames != null) {
			for (int i = 0; i < actionNames.length; i++)
				addItem(actionNames[i]);
		}
		this.selectedIndex = -1;
		setSelectedIndex(selectedIndex);
	}
	
	/**
	 * This method is called when one of the options is selected because of a user action on one of the
	 * radio buttons or menu items returned by {@link #createRadioButtonMenuItems()} or {@link #createRadioButtons()}.
	 * It is <b>not</b> called when the selected index is changed programmatically by {@link #setSelectedIndex(int)}.
	 * In this class, this method does nothing.  Subclasses can override this method to perform some action when the
	 * user changes the selection.  Note that this method will be called even when the user selects the option
	 * that is already selected.
	 * @param selectedIndex The index of the item that was selected, or -1 to indicate that no option is selected.
	 */
	public void optionSelected(int selectedIndex) {
	}

	/**
	 * Return a list of radio button menu items that are synchronized with the selection state of the options
	 * in this group.  The user can select a new option by selecting the corresponding menu item.  The
	 * list can contain null items, which are meant to represent separators in the menu.
	 */
	public JRadioButtonMenuItem[] createRadioButtonMenuItems() {
		JRadioButtonMenuItem[] items = new JRadioButtonMenuItem[actions.size()];
		for (int i = 0; i < items.length; i++)
			if (actions.get(i) != null)
				items[i] = new ActionRadioButtonMenuItem(i);
		return items;
	}
	
	/**
	 * Return a list of radio buttons that are synchronized with the selection state of the options
	 * in this group.  The user can select a new option by selecting the corresponding button.  The
	 * list can contain null items, which are meant to represent separators.
	 */
	public JRadioButton[] createRadioButtons() {
		JRadioButton[] items = new JRadioButton[actions.size()];
		for (int i = 0; i < items.length; i++)
			if (actions.get(i) != null)	
				items[i] = new ActionRadioButton(i);
		return items;
	}
	
	/**
	 * Adds an option to the group.  Note that using this method after menu items or radio buttons have
	 * been created for the group would be a little strange, since they would not be added to the
	 * exiting menu item and button groups.  There is no way to remove options
	 * once they have been added.
	 * @param itemName The name of the option, or null if this item is meant to represent a separator in a menu.
	 * @return the index (option number) of the newly added option.  This will probably be ignored in most cases.
	 */
	public int addItem(String itemName) {
		if (itemName == null)
			actions.add(null);
		else {
			final int index = actions.size();
			AbstractAction action = new AbstractAction(itemName) {
				public void actionPerformed(ActionEvent evt) {
					setSelectedIndex(index);
					optionSelected(index);
				}
			};
			action.putValue("RadioState", new Boolean(false));
			action.setEnabled(enabled);
			actions.add(action);
		}
		return actions.size() - 1;
	}
	
	/**
	 * Return the index of the currently selected option, or -1 if no option is currently selected.
	 */
	public int getSelectedIndex() {
		return selectedIndex;
	}
	
	/**
	 * Set the currently selected option.
	 * @param index the option to be selected, or -1 to indicate that no option is selected.  Any
	 * value outside the range of legal option numbers is treated as -1.  Also, an index for which
	 * the option is a null value is treated as -1.
	 */
	public void setSelectedIndex(int index) {
		if (index < 0 || index >= actions.size() || actions.get(index) == null)
			index = -1;
		if (selectedIndex >= 0)
			actions.get(selectedIndex).putValue("RadioState", new Boolean(false));
		if (index >= 0)
			actions.get(index).putValue("RadioState", new Boolean(true));
		selectedIndex = index;
	}
	
	/**
	 * Set the enabled status of the Actions (and hence the buttons and menu items) associated with this group. 
	 */
	public void setEnabled(boolean enabled) {
		if (this.enabled != enabled) {
			this.enabled = enabled;
			for (int i = 0; i < actions.size(); i ++)
				actions.get(i).setEnabled(enabled);
		}
	}
	
	/**
	 * Get the current enabled status of the this action group.
	 * @see #setEnabled(boolean)
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Returns the same array that would be returned by {@link #createRadioButtonMenuItems()}.
	 */
	public JMenuItem[] getMenuItems() {
		return createRadioButtonMenuItems();
	}

	/**
	 * Returns the number of items in the group.
	 */
	public int getItemCount() {
		return actions.size();
	}
}
