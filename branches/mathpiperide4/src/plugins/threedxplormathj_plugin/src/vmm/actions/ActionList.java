/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.actions;

import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * A simple class that is meant to represent a submenu (or a list of items in a menu)
 * when used with methods such as {@link vmm.core.Exhibit#getActionsForView(View)}
 * and {@link vmm.core.View#getViewCommands()}.  The name of an ActionList is
 * meant to be used as a name for a submenu that contains commands corresponding
 * to all the items in the ActionList.
 */
public class ActionList implements ActionItem{

	private String name;
	private ArrayList<ActionItem> items;
	
	/**
	 * Create an unnamed ActionList.
	 */
	public ActionList() {
		this(null);
	}
	
	/**
	 * Create an ActionList with a specfied name.
	 * @param name
	 */
	public ActionList(String name) {
		this.name = name;
		items = new ArrayList<ActionItem>();
	}
	
	/**
	 * Adds an ActionItem to the list of items in this ActionList.  The object added can
	 * be of type {@link AbstractActionVMM}, {@link ToggleAction}, <@link ActionRadioGroup}, ActionList, or any other
	 * object that implements ActionItem.  Null values are allowed and are meant to r
	 * epresent separators in a mwnu.
	 */
	public void add(ActionItem object) {
		items.add(object);
	}
	
	/**
	 * Removes a specified ActionItem from this ActionList.  If the item does not occur
	 * in the list, nothing is done and no error occurs.  (If the item occurs more than
	 * once, only the first occurrence is removed.)
	 * @param object the object to be removed
	 */
	public void remove(ActionItem object) {
		items.remove(object);
	}

	/**
	 * Sets the name of this ActionList which, if non-null, is meant to be used as a name
	 * for the submenu.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the name of this ActionList.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the number of items that have been added to this ActionList.
	 */
	public int getItemCount() {
		return items.size();
	}

	/**
	 * Gets the i-th ActionItem in this ActionList.  Will throw an exception if
	 * i is less than zero or greater than or equal to the number of items in the
	 * list.
	 */
	public ActionItem getItem(int i) {
		return items.get(i);
	}
	
	/**
	 * Returns a list containing all the ActionItems that have been added to this ActionList.
	 * (The returned ArrayList is newly allocated and no reference is kept to it.)
	 */
	public ArrayList<ActionItem> getItems() {
		ArrayList<ActionItem> itemcopy = new ArrayList<ActionItem>();
		itemcopy.addAll(items);
		return itemcopy;
	}
	
	/**
	 * Add all the ActionItems in list to this ActionList.
	 * @param list list of items to be added; if null, nothing is done
	 */
	public void addAll(ActionList list) {
		if (list != null) {
			int ct = list.getItemCount();
			for (int i = 0; i < ct; i++)
				add(list.getItem(i));
		}
	}

	/**
	 * If this ActionList is unnamed then the retunn value is an array containing
	 * JMenuItems created by the individual ActionItems in the ActionList.
	 * If this ActionList has a non-null name with non-zero length, then the return
	 * value is an array containing a single item, which is a JMenu that in turn
	 * contains all the JMenuItems the JMenuItems from the individual ActionItems;
	 * the name of the JMenu is the name of the ActionList.
	 */
	public JMenuItem[] getMenuItems() {
		ArrayList<JMenuItem> menuItems = new ArrayList<JMenuItem>();
		for (ActionItem item : items) {
			if (item == null)
				menuItems.add(null);
			else {
				JMenuItem[] m = item.getMenuItems();
				for (JMenuItem i : m)
					menuItems.add(i);
			}
		}
		if (name == null || name.length() == 0) {
			JMenuItem[] a = new JMenuItem[menuItems.size()];
			return menuItems.toArray(a);
		}
		else {
			JMenu menu = new JMenu(name);
			for (JMenuItem i : menuItems)
				if (i == null)
					menu.addSeparator();
				else
					menu.add(i);
			return new JMenuItem[] { menu };
		}
	}

}
