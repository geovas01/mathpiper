/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.xm3d;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;

import javax.swing.JFrame;

import vmm.core.Exhibit;
import vmm.core.I18n;
import vmm.core.Util;
import vmm.core.View;


/**
 * Windows in the 3D-ExploreMath application belong to this class. 
 * Note that when the last window of this type is closed, the program ends.
 */
public class WindowXM extends JFrame {
	
	static {  // Static initializer, for adding string resource file.
		I18n.addFile("vmm.xm3d.stringsXM");
	}
	
	private static int openWindowCount;
	private static WindowXM activeWindow;
	private static HTMLDocWindow htmlDocWindow;
	private Menus menus;
	private DisplayXM display;
	
	private LauncherApplet launcherApplet;  // non-null, if this Window belongs to a LauncherApplet

	/**
	 * Returns that Display that is shown in this window.  Every WindowXM has
	 * one associated display.
	 */
	public DisplayXM getDisplay() {
		return display;
	}
	
	/**
	 * Returns the View currently associated with this window's display, or null if there
	 * is no current view.  This is a convenience method that does the same thing as
	 * getDisplay().getView();
	 */
	public View getView() {
		return display.getView();
	}
	
	/**
	 * Returns the Exhibit currently associated with this window's display, or null if there
	 * is no current exhibit.  This is a convenience method that does the same thing as
	 * getDisplay().getExhibit();
	 */
	public Exhibit getExhibit() {
		return display.getExhibit();
	}
	
	/**
	 * This package-visible method returns the menu bar for this window.  This method is used
	 * in {@link Menus}.
	 */
	Menus getMenus() {
		return menus;
	}
	
	/**
	 * Creates a WindowXM with no "parent" window.  This method is used in 3D-XPlorMath to make
	 * the first WindowXM of the program.
	 */
	public WindowXM() {
		this(null);
	}
	
	/**
	 * There is single {@link HTMLDocWindow} associated with all WindowXM windows.  (It is 
	 * a private static variable in the WinodwXM class.)  This method will show show the
	 * HTMLDocWindow, if it is not already visible, with its contents set to a specified
	 * file.
	 * @param resourceFileName the file to be displayed, or null to show the window without
	 * changing its contents.  This is a resouces name, so the file should be somewhere in the
	 * path that is searched by the class loader.
	 */
	public static void showHTMLDocWindow(String resourceFileName) {
		if (htmlDocWindow == null)
			htmlDocWindow = new HTMLDocWindow();
		htmlDocWindow.showFile(resourceFileName);
	}
	
	/**
	 * There is single {@link HTMLDocWindow} associated with all WindowXM windows.  (It is 
	 * a private static variable in the WinodwXM class.)  This method will show show the
	 * HTMLDocWindow, if it is not already visible, with its contents set to the Web page
	 * at a specified URL.
	 * @param resourceURL the non-null URL of the page that is to be displayed to be displayed,
	 */
	public static void showHTMLDocWindow(URL resourceURL) {
		if (htmlDocWindow == null)
			htmlDocWindow = new HTMLDocWindow();
		htmlDocWindow.showURL(resourceURL);
	}
	
	/**
	 * Creates a WindowXM.  
	 * @param parent if non-null, then the location of the new window is based on the
	 * location of its "parent."  (Also, any options for the menu bar are
	 * copied from the parent.) This parameter is not otherwise used.
	 */
	public WindowXM(WindowXM parent) {
		this(parent,null,0);
	}
			
	/**
	 * Creates a window whose menu bar has the specified set of options.
	 * Introduced for use with {@link MainForWebStart}.
	 * @param menuOptionFlags option flags to be used in construction of the menu bar for this window
	 */
	WindowXM(int menuOptionFlags) {
		this(null,null,menuOptionFlags);
	}
	
	/**
	 * Used by {@link LauncherApplet} to create a window when the user
	 * clicks the button on the applet.
	 * @param launcher The LauncherApplet that is creating this window
	 * @param menuOptionFlags option flags to be used in construction of the menu bar for this window
	 */
	WindowXM(LauncherApplet launcher, int menuOptionFlags) {
		this(null, launcher, menuOptionFlags);
	}
	
	/**
	 * This private constructor is called by the other constructors in this class
	 * to do all the work.
	 */
	private WindowXM(WindowXM parent, LauncherApplet launcher, int menuOptionFlags) {
		super(I18n.tr("3dxm.window.defaultname"));
		if (launcher != null)
			launcherApplet = launcher;
		else if (parent != null)
			launcherApplet = parent.launcherApplet;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int preferredWidth,preferredHeight;
		preferredWidth = (screenSize.width*3) / 4;
		if (preferredWidth > 800)
			preferredWidth = 800;
		preferredHeight = (int)( preferredWidth / 1.628 );
		if (preferredWidth > screenSize.width - 40)
			preferredWidth = screenSize.width - 40;
		if (preferredHeight > screenSize.height - 60)
			preferredHeight = screenSize.height - 60;
		if (activeWindow != null)
			activeWindow.display.discardFilmstrip();
		display = new DisplayXM();
		display.setPreferredSize(new Dimension(preferredWidth,preferredHeight));
		setContentPane(display.getHolder());
		int options = 0;
		if (menuOptionFlags != 0)
			options = menuOptionFlags;
		else if (parent != null)
			options = parent.getMenus().getOptionFlags();
		menus = new Menus(this, null, options);
		setJMenuBar(menus);
		pack();
		if (parent == null) {
			if (Util.isMacOS())
				setLocation( 30, 35 );
			else
				setLocation( (screenSize.width - preferredWidth) / 2, 35 );
		}
		else {
			Point p = parent.getLocation();
			int x = p.x + 40;
			int y = p.y + 30;
			if (x + getWidth() > screenSize.width)
				x = 20;
			if (y + getHeight() > screenSize.height)
				y = 40;
			setLocation(x,y);
		}
		openWindowCount++;
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent evt) {
				if (launcherApplet != null)
					launcherApplet.windowOpened(WindowXM.this);
			}
			public void windowClosed(WindowEvent evt) {
				display.stopAnimation();
				display.install(null,null);
				openWindowCount--;
				if (launcherApplet != null)
					launcherApplet.windowClosed(WindowXM.this);
				if (openWindowCount == 0) {
					if (launcherApplet == null)
						System.exit(0);
				}
			}
			public void windowActivated(WindowEvent evt) {
				if (activeWindow != WindowXM.this) {
					if (activeWindow != null) {
						activeWindow.display.stopAnimation();
						activeWindow.display.discardFilmstrip();
					}
					activeWindow = WindowXM.this;
				}
			}
		});
		display.addPropertyChangeListener("preferredSize", new PropertyChangeListener() { 
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("preferredSize")) {
					display.invalidate();
					validate();
					pack();
				}
			}
		});
	}      
	
}