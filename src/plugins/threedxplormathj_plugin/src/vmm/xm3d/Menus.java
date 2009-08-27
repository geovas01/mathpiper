/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.xm3d;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import vmm.actions.ActionList;
import vmm.actions.AbstractActionVMM;
import vmm.core.Animateable;
import vmm.core.Animation;
import vmm.core.BasicAnimator;
import vmm.core.Display;
import vmm.core.Exhibit;
import vmm.core.Filmstrip;
import vmm.core.I18n;
import vmm.core.Parameter;
import vmm.core.Prefs;
import vmm.core.SaveAndRestore;
import vmm.core.TimerAnimation;
import vmm.core.UserExhibit;
import vmm.core.Util;
import vmm.core.View;

public class Menus extends JMenuBar {
	
	/**
	 * For package private constants for use in the second paramter of
	 * the constructor {@link #Menus(WindowXM, int)}
	 */
	static final int NO_FILE_SYSTEM = 1,  // no commands that access file system or System.exit
	                 NO_ACCELERATORS = 2, // don't include accelerator keys for menu items.
	                 SINGLE_EXHIBIT = 4,  // do not include Gallery or Exhibit menu
	                 SINGLE_GALLERY = 8,  // do not include Gallery menu (not needed if SINGLE_EXHIBIT is present)
	                 NO_EXIT = 16;        // do not include the Quit command since System.exit is not allowed
	private int optionFlags;  // options set when this object was created
		
	private WindowXM window;
	private DisplayXM display;
	
	//private boolean newExhibit = true;
	
	private JMenu fileMenu, galleryMenu, exhibitMenu, actionMenu, documentationMenu, viewMenu, settingsMenu, animateMenu;
	
	private AbstractAction morphAction, cyclicMorphAction;
	private AbstractAction playFilmstripAction;
	private AbstractAction saveAnimationAsPngAction, saveAnimationAsJpegAction, playSavedAnimationAction;
	private JMenu speedSubmenu;
	private JRadioButtonMenuItem speedSelect1, speedSelect2, speedSelect3, speedSelect4;
	
	private AbstractAction saveAction;
	private AbstractAction newViewAction, newSynchronizedViewAction;
	
	private JMenuItem saveAsPngCommand, saveAsJpegCommand;
	
	private AbstractAction showATE, showATG;
	
	private JFileChooser fileDialog;
	
	private Galleries galleries;
	
	private View[] alternativeViews;  // if the Exhibit has more views than just the default; these are added to the end of the Action menu
	private JRadioButtonMenuItem[] viewSelect;
	
	/**
	 * The file extension that is used for all 3D-XPlorMath-J settings files that are created  by the
	 * "Save Settings File" command.
	 */
	public static final String FILE_EXTENSION = "3dxmj";
	

	/**
	 * Creates a menu bar for the specified window.
	 * @param window
	 */
	public Menus(WindowXM window) {
		this(window,null,0);
	}

	/**
	 * Creates a menu bar, possibly based on some options.
	 * @param window the WindowXM on which the menu bar will be used (possibly null, if it is for an applet).
	 *    If the value is null, then the menu bar is for a display that is embedded in a applet on a 
	 *    web page, and there will be no file/window menu.
	 * @param display the DisplayXM to which the menu bar applies.  If window is non-null, then display
	 *    is obtained as window.getDisplay().  It is an error for both window and display to be null.
	 * @param optionFlags options for the menu bar. A value of zero gives the default menubar for
	 *    a window in a stand-alone application.  Otherwise, it can be a combination of flags given
	 *    by the constants FOR_APPLET, SINGLE_EXHIBIT, SINGLE_GALLERY.
	 */
	Menus(WindowXM window, DisplayXM display, int optionFlags) {
		this.window = window;
		if (display == null)
			display = window.getDisplay();
		this.display = display;
		this.optionFlags = optionFlags;
		display.addPropertyChangeListener( new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					if (evt.getPropertyName().equals(Display.STATUS_PROPERTY))
						setUpForDisplayStatus((String)evt.getNewValue());
					else if (evt.getPropertyName().equals(Display.FILMSTRIP_AVAILABLE_PROPERTY))
						setUpForDisplayFilmstrip(((Boolean)evt.getNewValue()).booleanValue());
				}
			});
		createMenus();
		if (window != null)
			add(fileMenu);
		if ( (optionFlags & SINGLE_EXHIBIT) == 0 && (optionFlags & SINGLE_GALLERY) == 0)
			add(galleryMenu);
		if ( (optionFlags & SINGLE_EXHIBIT) == 0)
			add(exhibitMenu);
		if ( (optionFlags & NO_FILE_SYSTEM) == 0)
			add(documentationMenu);
		add(actionMenu);
		add(settingsMenu);
		add(viewMenu);		
		add(animateMenu);
		setUpMenusForNoExhibit();
	}
	
	int getOptionFlags() { // added for use in WindowXM.
		return optionFlags;
	}
	
	void install(View view, Exhibit exhibit) { // added for use by applets
		alternativeViews = null;
		viewSelect = null;
		if (window != null)
			window.setTitle(exhibit.getTitle());
		getAlternativeViews(exhibit);
		if (view == null)
			view = (alternativeViews == null)? exhibit.getDefaultView() : alternativeViews[0];
		display.install(view,exhibit);
		setUpMenusForNewExhibit(view,exhibit);
		String galleryName = galleries.findGalleryForExhibit(exhibit);
		if (galleryName != null)
			galleries.selectGalleryByName(galleryName, this, exhibitMenu);
		Animation create = exhibit.getCreateAnimation(view);
		if (create != null)
			display.installAnimation(create);
	}
	
	void installExhibit(Galleries.ExhibitItem exhibitInfo) {
		Exhibit exhibit = null;
		View view = null;  
		alternativeViews = null;
		viewSelect = null;
		if (exhibitInfo != null && exhibitInfo.isXML) {
			try {
				ClassLoader cl = this.getClass().getClassLoader();
				java.net.URL imageURL = cl.getResource(exhibitInfo.classname);
				java.io.InputStream in = imageURL.openStream();
				exhibit = SaveAndRestore.readExhibitFromXML(in, exhibitInfo.classname);
				if (exhibitInfo.name != exhibitInfo.classname)
					exhibit.setName(exhibitInfo.name);
				ArrayList<View> views = exhibit.getViews();
				if (views != null && views.size() > 0)
					view = views.get(0);
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(window, I18n.tr("3dxm.menus.error.BadXMLFile", exhibitInfo.classname, e.getMessage()));
				e.printStackTrace();
			}
		}
		else if (exhibitInfo != null) {
			try {
				exhibit = (Exhibit)Class.forName(exhibitInfo.classname).newInstance();
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(window,I18n.tr("3dxm.menus.error.BadClassName",exhibitInfo.classname));
				e.printStackTrace();
			}
			if (exhibit instanceof UserExhibit) {
				view = ((UserExhibit)exhibit).getUserExhibitSupport().showCreateDialog(display);
				if (view == null)
					return;
			}
		}
		if (exhibit == null) {
			if (window != null)
				window.setTitle(I18n.tr("3dxm.window.defaultname"));
			display.install(null,null);
			setUpMenusForNoExhibit();
			return;
		}
		if (window != null)
			window.setTitle(exhibit.getTitle());
		getAlternativeViews(exhibit);
		if (view == null)
			view = (alternativeViews == null)? exhibit.getDefaultView() : alternativeViews[0];
		display.install(view,exhibit);
		setUpMenusForNewExhibit(view,exhibit);
		Animation create = exhibit.getCreateAnimation(view);
		if (create != null)
			display.installAnimation(create);
	}
	
	private void getAlternativeViews(Exhibit exhibit) {
		View[] extraViews = exhibit.getAlternativeViews();
		if (extraViews != null) {
			alternativeViews = new View[extraViews.length + 1];
			alternativeViews[0] = exhibit.getDefaultView();
			for (int i = 0; i < extraViews.length; i++)
				alternativeViews[i+1] = extraViews[i];
		}
	}
	
	private void setUpForDisplayStatus(String status) {
		if (saveAction != null)
			saveAction.setEnabled(status.equals(Display.STATUS_IDLE));
		newViewAction.setEnabled(status.equals(Display.STATUS_IDLE));
		newSynchronizedViewAction.setEnabled(status.equals(Display.STATUS_IDLE));
		if (saveAsJpegCommand != null)
			saveAsJpegCommand.setEnabled( ! (status.equals(Display.STATUS_EMPTY) || status.equals(Display.STATUS_ANIMATION_RUNNING)) );
		if (saveAsPngCommand != null)
			saveAsPngCommand.setEnabled( ! (status.equals(Display.STATUS_EMPTY) || status.equals(Display.STATUS_ANIMATION_RUNNING)) );
	}
	
	private void setUpForDisplayFilmstrip(boolean filmstripAvailable) {
		playFilmstripAction.setEnabled(filmstripAvailable);
		if (saveAnimationAsJpegAction != null)
			saveAnimationAsJpegAction.setEnabled(filmstripAvailable);
		if (saveAnimationAsPngAction != null)	
			saveAnimationAsPngAction.setEnabled(filmstripAvailable);
	}
	
	private void setUpMenusForNoExhibit() {
		actionMenu.removeAll();
		viewMenu.removeAll();
		settingsMenu.removeAll();
		actionMenu.setEnabled(false);
		viewMenu.setEnabled(false);
		animateMenu.setEnabled(false);
		settingsMenu.setEnabled(false);
		checkATEandATG();
	}
	
	private void setUpMenusForNewExhibit(View view, Exhibit exhibit) {
		actionMenu.removeAll();
		actionMenu.setEnabled(false);
		ActionList actions = view.getActionsForViewAndExhibit();
		if (actions != null && actions.getItemCount() > 0) {
			actionMenu.setEnabled(true);
			addMenuItems(actions,actionMenu);
		}
		if (alternativeViews != null) {
			if (viewSelect == null) {  // first time for this exhibit, so viewSelect commands have to be created.
				ButtonGroup group = new ButtonGroup();
				ActionListener listener = new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						Object src = evt.getSource(); 
						for (int i = 0; i < alternativeViews.length; i++)
							if (src == viewSelect[i]) {
								swapInAlternativeView(alternativeViews[i]);
								return;
							}
					}
				};
				viewSelect = new JRadioButtonMenuItem[alternativeViews.length];
				for (int i = 0; i < alternativeViews.length; i++) {
					viewSelect[i] = new JRadioButtonMenuItem(alternativeViews[i].getTitle());
					group.add(viewSelect[i]);
					viewSelect[i].addActionListener(listener);
					if (alternativeViews[i] == view)
						viewSelect[i].setSelected(true);
				}
			}
			if (actionMenu.getMenuComponentCount() > 0)
				actionMenu.addSeparator();
			for (int i = 0; i < alternativeViews.length; i++)
				actionMenu.add(viewSelect[i]);
			actionMenu.setEnabled(true);
		}
		ActionList viewCommands = view.getViewCommands();
		viewMenu.removeAll();
		if (viewCommands != null && viewCommands.getItemCount() > 0) {
			viewMenu.setEnabled(true);
			addMenuItems(viewCommands,viewMenu);
		}
		else
			viewMenu.setEnabled(false);
		ActionList settingsCommands = view.getSettingsCommandsForViewAndExhibit();
		settingsMenu.removeAll();
		if (settingsCommands != null && settingsCommands.getItemCount() > 0) {
			settingsMenu.setEnabled(true);
			addMenuItems(settingsCommands,settingsMenu);
		}
		else
			settingsMenu.setEnabled(false);
		ActionList animateCommands = view.getAdditionalAnimationsForViewAndExhibit();
		animateMenu.removeAll();
		animateMenu.add(speedSubmenu);
		animateMenu.addSeparator();
		animateMenu.add(morphAction);
		animateMenu.add(cyclicMorphAction);
		animateMenu.addSeparator();
		if (animateCommands != null && animateCommands.getItemCount() > 0) {
			addMenuItems(animateCommands,animateMenu);
			animateMenu.addSeparator();
		}
		animateMenu.add(playFilmstripAction);
		animateMenu.setEnabled(true);
		Parameter[] params = view.getViewAndExhibitParameters();
		boolean animated = false;
		for (int i = 0; i < params.length; i++)
			if (params[i] instanceof Animateable) {
				animated = true;
				break;
			}
		galleries.setUpExhibitMenu(this, exhibitMenu);
		if (exhibit instanceof UserExhibit) {
			if (((UserExhibit)exhibit).getUserExhibitSupport().getAllowNewParameters())
				animated = true;  // because user might add new animated parameters
			if (((UserExhibit)exhibit).getUserExhibitSupport().getAllowChangeUserDataCommand()) {
				AbstractActionVMM changeUserDataAction = ((UserExhibit)exhibit).getUserExhibitSupport().makeChangeUserDataAction(view);
				exhibitMenu.addSeparator();
				exhibitMenu.add(changeUserDataAction);
			}
		}
		speedSelect1.setSelected(true);
		display.setTimeDilationForAnimations(1);
		morphAction.setEnabled(animated);
		cyclicMorphAction.setEnabled(animated);
		checkATEandATG();
	}
	
	private void swapInAlternativeView(View view) {
		if (display.getView() == view || display.getExhibit() == null)
			return;
		Exhibit exhibit = display.getExhibit();
		view.takeExhibit(display.getView(),true);
		display.install(view,exhibit);
		Animation create = exhibit.getCreateAnimation(view);
		if (create != null)
			display.installAnimation(create);
		setUpMenusForNewExhibit(view,exhibit);
	}
	
	private void addMenuItems(ActionList items, JMenu menu) {
		if (items != null) {
			for (int i = 0; i < items.getItemCount(); i++) {
				if (items.getItem(i) == null)
					menu.addSeparator();
				else {
					JMenuItem[] menuItems = items.getItem(i).getMenuItems();
					for (JMenuItem m : menuItems) {
						if (m == null)
							menu.addSeparator();
						else {
							if ( (optionFlags & NO_ACCELERATORS) != 0) {
								if (m instanceof JMenu) {
									int ct = ((JMenu)m).getMenuComponentCount();
									for (int j = 0; j < ct; j++) {
										Component c = ((JMenu)m).getMenuComponent(j);
										if (c != null && c instanceof JMenuItem && !(c instanceof JMenu))
											((JMenuItem)c).setAccelerator(null);
									}
								}
								else
									m.setAccelerator(null);
							}
							menu.add(m);
						}
					}
				}
			}
		}
	}
	
	private void createMenus() {
		
		String accel; // For adding accelerators to commands.
		
		boolean noFileSystem = (optionFlags & NO_FILE_SYSTEM) != 0;
		boolean noAccelerators = (optionFlags & NO_ACCELERATORS) != 0;

		fileMenu = new JMenu(noFileSystem? I18n.tr("3dxm.menus.LauncherApplet.WindowsMenuName") : 
			                                          I18n.tr("3dxm.menus.fileMenuName"));
		galleryMenu = new JMenu(I18n.tr("3dxm.menus.galleryMenuName"));
		actionMenu  = new JMenu(I18n.tr("3dxm.menus.actionMenuName"));
		exhibitMenu = new JMenu(I18n.tr("3dxm.gallery.PlaneCurves"));
		documentationMenu = new JMenu(I18n.tr("3dxm.menus.documentationMenuName"));
		viewMenu = new JMenu(I18n.tr("3dxm.menus.viewMenuName"));
		settingsMenu = new JMenu(I18n.tr("3dxm.menus.settingsMenuName"));
		animateMenu = new JMenu(I18n.tr("3dxm.menus.animateMenuName"));
		
		documentationMenu.add(new AbstractAction(I18n.tr("3dxm.command.documentation.GettingStarted")) {
			public void actionPerformed(ActionEvent evt) {
				String resourceName = null;
				String language = I18n.getLocale().getLanguage().toLowerCase();
				if (!language.equals("en")) {
					resourceName = "vmm/xm3d/getting_started_" + language + ".html";
					if (getClass().getClassLoader().getResource(resourceName) == null)
						resourceName = null;
				}
				if (resourceName  == null)
					resourceName = "vmm/xm3d/getting_started.html";
				WindowXM.showHTMLDocWindow(resourceName);
			}
		});
		documentationMenu.addSeparator();
		showATE = new AbstractAction(I18n.tr("3dxm.command.documentation.ATE")) {
			public void actionPerformed(ActionEvent evt) {
				if (display == null || display.getExhibit() == null)
					return;
				String exhibitName = display.getExhibit().getName();
				showDocumentation(exhibitName + ".ATE.html");
			}
		};
		documentationMenu.add(showATE);
		showATG = new AbstractAction(I18n.tr("3dxm.command.documentation.ATG")) {
			public void actionPerformed(ActionEvent evt) {
				String galleryName = galleries.currentGalleryName();
				if (galleryName == null)
					return;
				showDocumentation(galleryName + ".ATG.html");
			}
		};
		documentationMenu.add(showATG);
		documentationMenu.addSeparator();
		documentationMenu.add(new AbstractAction(I18n.tr("3dxm.command.documentation.DocPrefs")) {
			public void actionPerformed(ActionEvent e) {
				new DocPrefsDialog(display).setVisible(true);
				Galleries.setDocumentationLocation();
				checkATEandATG();
			}
		});
		documentationMenu.add(new AbstractAction(I18n.tr("About the Documentation")) {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(display, I18n.tr("3dxm.documentation.about.text"));
			}
		});
		
				
		try {
			galleries = new Galleries();
		}
		catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(window,I18n.tr("3dxm.menus.error.BadGalleryInfo",e.toString()));
			System.exit(0);
		}
		galleries.setUpGalleryMenu(this,galleryMenu,exhibitMenu);
		
		if ( (optionFlags & SINGLE_EXHIBIT) == 0) {
			accel = noAccelerators? null : "N";
			fileMenu.add(new AbstractActionVMM(I18n.tr("3dxm.command.New"), accel) {
				public void actionPerformed(ActionEvent evt) {
					WindowXM newWin = new WindowXM(window);
					if ( (optionFlags & SINGLE_GALLERY) != 0 ) {
						String gallery = galleries.currentGalleryName();
						Menus newWinMenus = newWin.getMenus();
						newWinMenus.galleries.selectGalleryByName(gallery, newWinMenus, newWinMenus.exhibitMenu);
					}
					newWin.setVisible(true);
				}
			});
		}
		newViewAction = new AbstractAction(I18n.tr("3dxm.command.NewView")) {
			public void actionPerformed(ActionEvent evt) {
				Exhibit exhibit = display.getExhibit();
				if (exhibit == null)
					return;
				WindowXM w = new WindowXM(window);
				w.getMenus().getAlternativeViews(exhibit);
				View view = w.getMenus().alternativeViews == null? exhibit.getDefaultView() : w.getMenus().alternativeViews[0];
				w.getDisplay().install(view, exhibit);
				w.getMenus().setUpMenusForNewExhibit(view,exhibit);
				w.setTitle(window.getTitle());
				w.getMenus().galleries.selectGalleryByName(galleries.currentGalleryName(),w.getMenus(),w.getMenus().exhibitMenu);
				w.setVisible(true);
			}
		};
		newViewAction.setEnabled(false);
		fileMenu.add(newViewAction);
		newSynchronizedViewAction = new AbstractAction(I18n.tr("3dxm.command.NewViewShareTransform")) {
			public void actionPerformed(ActionEvent evt) {
				Exhibit exhibit = display.getExhibit();
				if (exhibit == null)
					return;
				WindowXM w = new WindowXM(window);
				w.getMenus().getAlternativeViews(exhibit);
				View view = w.getMenus().alternativeViews == null? exhibit.getDefaultView() : w.getMenus().alternativeViews[0];
				view.takeExhibit(display.getView(),true);
				w.getDisplay().install(view, null);
				w.getMenus().setUpMenusForNewExhibit(view,exhibit);
				w.setTitle(window.getTitle());
				w.getMenus().galleries.selectGalleryByName(galleries.currentGalleryName(),w.getMenus(),w.getMenus().exhibitMenu);
				w.setVisible(true);
			}
		};
		newSynchronizedViewAction.setEnabled(false);
		fileMenu.add(newSynchronizedViewAction);
		if (!noFileSystem) {
			accel = noAccelerators? null : "O";
			fileMenu.add(new AbstractActionVMM(I18n.tr("3dxm.command.Open"), accel) {
				public void actionPerformed(ActionEvent evt) {
					doOpen();
				}
			});
			fileMenu.addSeparator();
			accel = noAccelerators? null : "S";
			saveAction = new AbstractActionVMM(I18n.tr("3dxm.command.Save"), accel) {
				public void actionPerformed(ActionEvent evt) {
					doSave();
				}
			};
			saveAsJpegCommand = new JMenuItem(I18n.tr("3dxm.command.SaveAsJpeg"));
			saveAsPngCommand = new JMenuItem(I18n.tr("3dxm.command.SaveAsPng"));
			ActionListener saveImageListener = new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (evt.getSource() == saveAsJpegCommand)
						doSaveImage("JPEG");
					else if (evt.getSource() == saveAsPngCommand)
						doSaveImage("PNG");
				}
			};
			saveAnimationAsPngAction = new AbstractActionVMM(I18n.tr("3dxm.command.saveAnimationPngFormat")) {
				public void actionPerformed(ActionEvent evt) {
					doSaveAnimation("PNG");
				}
			};
			saveAnimationAsPngAction.setEnabled(false);
			saveAnimationAsJpegAction = new AbstractActionVMM(I18n.tr("3dxm.command.saveAnimationJpegFormat")) {
				public void actionPerformed(ActionEvent evt) {
					doSaveAnimation("JPEG");
				}
			};
			saveAnimationAsJpegAction.setEnabled(false);
			playSavedAnimationAction = new AbstractActionVMM(I18n.tr("3dxm.command.playSavedAnimation")) {
				public void actionPerformed(ActionEvent evt) {
					doPlaySavedAnimation();
				}
			};
			saveAsJpegCommand.addActionListener(saveImageListener);
			saveAsPngCommand.addActionListener(saveImageListener);
			saveAction.setEnabled(false);
			saveAsJpegCommand.setEnabled(false);
			saveAsPngCommand.setEnabled(false);
			fileMenu.add(saveAction);
			fileMenu.addSeparator();
			fileMenu.add(saveAsPngCommand);
			fileMenu.add(saveAsJpegCommand);
			fileMenu.addSeparator();
			fileMenu.add(saveAnimationAsPngAction);
			fileMenu.add(saveAnimationAsJpegAction);
			fileMenu.add(playSavedAnimationAction);
		}
		fileMenu.addSeparator();
		accel = noAccelerators? null : "W";
		fileMenu.add(new AbstractActionVMM(I18n.tr("3dxm.command.Close"), accel) {
			public void actionPerformed(ActionEvent evt) {
				window.dispose();
			}
		});
		if ( (optionFlags & NO_EXIT ) == 0) {
			accel = noAccelerators? null : "Q";
			fileMenu.add(new AbstractActionVMM(I18n.tr("3dxm.command.Quit"), accel) {
				public void actionPerformed(ActionEvent evt) {
					System.exit(0);
				}
			});
		}
		
		accel = noAccelerators? null : "P";
		playFilmstripAction = new AbstractActionVMM(I18n.tr("3dxm.command.playback"), accel) {
			public void actionPerformed(ActionEvent evt) {
				display.playFilmstrip();
			}
		};
		playFilmstripAction.setEnabled(false);
		
		morphAction = new AbstractAction(I18n.tr("3dxm.command.Morph")) { 
			public void actionPerformed(ActionEvent evt) {
				Exhibit currentExhibit = display.getExhibit();
				if (currentExhibit == null)
					return;
				BasicAnimator anim = currentExhibit.getMorphingAnimation(display.getView(), BasicAnimator.OSCILLATE);
				if (anim != null)
					display.installAnimation(anim);
				else
					JOptionPane.showMessageDialog(display,I18n.tr("3dxm.morphing.noAnimation"));
			}
		};
		
		cyclicMorphAction = new AbstractAction(I18n.tr("3dxm.command.CyclicMorph")) { 
			public void actionPerformed(ActionEvent evt) {
				Exhibit currentExhibit = display.getExhibit();
				if (currentExhibit == null)
					return;
				BasicAnimator anim = currentExhibit.getMorphingAnimation(display.getView(), BasicAnimator.LOOP);
				if (anim != null)
					display.installAnimation(anim);
				else
					JOptionPane.showMessageDialog(display,I18n.tr("3dxm.morphing.noAnimation"));
			}
		};
		
		speedSelect1 = new JRadioButtonMenuItem(I18n.tr("3dxm.command.NormalSpeed"),true);
		speedSelect2 = new JRadioButtonMenuItem(I18n.tr("3dxm.command.HalfSpeed"),true);
		speedSelect3 = new JRadioButtonMenuItem(I18n.tr("3dxm.command.QuarterSpeed"),true);
		speedSelect4 = new JRadioButtonMenuItem(I18n.tr("3dxm.command.EighthSpeed"),true);
		if ( (optionFlags & NO_ACCELERATORS) == 0) {
			speedSelect1.setAccelerator(Util.getAccelerator("1"));
			speedSelect2.setAccelerator(Util.getAccelerator("2"));
			speedSelect3.setAccelerator(Util.getAccelerator("3"));
			speedSelect4.setAccelerator(Util.getAccelerator("4"));
		}
		ButtonGroup speedGroup = new ButtonGroup();
		speedGroup.add(speedSelect1);
		speedGroup.add(speedSelect2);
		speedGroup.add(speedSelect3);
		speedGroup.add(speedSelect4);
		SetSpeedAction speedAction = new SetSpeedAction();
		speedSelect1.addActionListener(speedAction);
		speedSelect2.addActionListener(speedAction);
		speedSelect3.addActionListener(speedAction);
		speedSelect4.addActionListener(speedAction);
		speedSubmenu = new JMenu(I18n.tr("3dxm.command.AnimationSpeed"));
		speedSubmenu.add(speedSelect1);
		speedSubmenu.add(speedSelect2);
		speedSubmenu.add(speedSelect3);
		speedSubmenu.add(speedSelect4);
		
	}
	
	
	private void showDocumentation(String document) {
		boolean useExternalBrowser = Prefs.getBoolean("3dxm.docprefs.useExternalBrowser", false);
		String externalBrowserCommand = null;
		if (useExternalBrowser) {
			externalBrowserCommand = Prefs.get("3dxm.docprefs.externalBrowserCommand", null);
			if (externalBrowserCommand == null || externalBrowserCommand.indexOf("%f") < 0)
				useExternalBrowser = false;
		}
		String 	documentationBaseURL = Prefs.get("3dxm.docprefs.documentationBaseURL", 
				I18n.tr("3dxm.DocPrefsDialog.DefaultDocumentationLocation"));
		URL docURL;
		if (documentationBaseURL.startsWith("http://")) {
			String doc;
			if (documentationBaseURL.endsWith("/"))
				doc = documentationBaseURL + document;
			else
				doc = documentationBaseURL + "/" + document;
			try {
				docURL = new URL(doc);
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(window,I18n.tr("3dxm.documentation.error.BadDocumentationLocation",doc));
				return;
			}
		}
		else {
			File docFile = new File(documentationBaseURL, document);
			try {
				docURL = docFile.getAbsoluteFile().toURI().toURL();
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(window,I18n.tr("3dxm.documentation.error.BadDocumentationLocation",docFile.getAbsolutePath()));
				return;
			}
		}
		if (!useExternalBrowser) {
			WindowXM.showHTMLDocWindow(docURL);
		}
		else {
			String command = externalBrowserCommand.replace("%f", docURL.toString() );
			String errorMessage = runCommand(command);
			if (errorMessage != null) {
				JOptionPane.showMessageDialog(window,I18n.tr(errorMessage));
			}
		}
	}
	
	private String runCommand(final String command) {
		class Runner extends Thread { 
			volatile String errorMessage = null;
			public void run() {
				System.out.println("Running external command: " + command);
				Process process;
				try {
					process = Runtime.getRuntime().exec(command);
				}
				catch (Exception e) {
					errorMessage = I18n.tr("3dxm.documentation.error.cantLaunchBrowser",command);
					return;
				}
				BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				try {
					String e = err.readLine();
					while (e != null) {
						if (errorMessage == null)  // non-null error output from process...
							errorMessage = I18n.tr("3dxm.documentation.error.errorFromBrowserProcess");
						e = err.readLine();
					}
				}
				catch (Exception e) {
				}
			}
		}
		Runner runner = new Runner();
		runner.start();
		try {
			runner.join(2000);
		}
		catch (Exception e) {
		}
		if (runner.errorMessage != null && !runner.isAlive()) {
			   // note: if the process has run for two seconds, then, even if there was output to standard err, 
			   // return null to indicate no error.  This is because after two seconds, it's likely that the
			   // process is running in spite of the error.  (On Linux, when using konqueror, there was some
			   // output to standard error, but that did not stop konqueror from running and loading the web page.)
			return runner.errorMessage;
		}
		else
			return null;
	}
	
	void gotDocumnetationFileList(TreeSet<String> documentationFiles) {
		if (galleries.currentGalleryName() != null)
			showATG.setEnabled(documentationFiles.contains(galleries.currentGalleryName() + ".ATG.html"));
		if (display.getExhibit() != null)
			showATE.setEnabled(documentationFiles.contains(display.getExhibit().getName() + ".ATE.html"));
	}
	
	private void checkATEandATG() {
		if (galleries.currentGalleryName() == null)
			showATG.setEnabled(false);
		else 
			showATG.setEnabled( Galleries.documentationFileExists(this,galleries.currentGalleryName() + ".ATG.html") );
		if (display.getExhibit() == null)
			showATE.setEnabled(false);
		else
			showATE.setEnabled( Galleries.documentationFileExists(this, display.getExhibit().getName() + ".ATE.html") );
	}
	
	private void setupFileDialog() {
		if (fileDialog == null)
			fileDialog = new JFileChooser();
		String directory = Prefs.get("filechooser.directory");
		if (directory != null) {
			File dir = new File(directory);
			if (dir.isDirectory())
				fileDialog.setCurrentDirectory(dir);
		}
	}
	
	private void saveFileDialogDirectory() {
		File dir = fileDialog.getCurrentDirectory();
		if (dir != null) {
			String dirName = dir.getAbsolutePath();
			Prefs.put("filechooser.directory", dirName);
			Prefs.save("filechooser.directory");
		}
	}
	
	private void doOpen() {
		setupFileDialog();
		fileDialog.setDialogTitle(I18n.tr("3dxm.dialog.openDialog.title"));
		int option = fileDialog.showOpenDialog(display);
		if (option != JFileChooser.APPROVE_OPTION)
			return;
		File selectedFile = fileDialog.getSelectedFile();
		Exhibit exhibit;
		String galleryName = null;
		try {
			Document exhibitDoc = SaveAndRestore.readXMLDocument(selectedFile);
			NodeList galleryElements = exhibitDoc.getDocumentElement().getElementsByTagName("gallery_3dxm");
			if (galleryElements.getLength() > 0) {
				Element galleryElement = (Element)galleryElements.item(0);
				galleryName = galleryElement.getAttribute("name").trim();
			}
			exhibit = SaveAndRestore.readExhibitFromXML(selectedFile);
		}
		catch (Exception e) {
			errorMessage(I18n.tr("3dxm.dialog.openDialog.error",e.getMessage()));
			return;
		}
		window.setTitle(exhibit.getTitle());
		galleries.selectGalleryByName(galleryName,this,exhibitMenu);
		ArrayList<View> views = exhibit.getViews();
		View view;
		if (views == null || views.size() == 0)
			view = exhibit.getDefaultView();
		else
			view = views.get(0);
		display.install(view,exhibit);
		Animation create = exhibit.getCreateAnimation(view);
		if (create != null)
			display.installAnimation(create);
		View[] extraViews = exhibit.getAlternativeViews();
		if (extraViews != null) {
			alternativeViews = new View[extraViews.length + 1];
			alternativeViews[0] = exhibit.getDefaultView();
			for (int i = 0; i < extraViews.length; i++)
				alternativeViews[i+1] = extraViews[i];  
		}
		setUpMenusForNewExhibit(view,exhibit);
		saveFileDialogDirectory();
	}
	
	private void doSave() {
		if (display.getExhibit() == null)
			return;
		display.stopAnimation();
		setupFileDialog();
		String defaultName = I18n.tr("3dxm.dialog.saveDialog.SettingsFileNameForExhibit",display.getExhibit().getTitle()) + '.' + FILE_EXTENSION;
		File selectedFile = new File(defaultName);
		fileDialog.setSelectedFile(selectedFile);  // Go to directory from last save image operation
		fileDialog.setDialogTitle(I18n.tr("3dxm.dialog.saveDialog.title"));
		int option = fileDialog.showSaveDialog(display);
		if (option != JFileChooser.APPROVE_OPTION)
			return;  // user canceled
		selectedFile = fileDialog.getSelectedFile();
		if (! selectedFile.exists() && ! selectedFile.getName().toLowerCase().endsWith("." + FILE_EXTENSION)) {
			selectedFile = new File(selectedFile.getParent(), selectedFile.getName() + '.' + FILE_EXTENSION);
		}
		if (selectedFile.exists()) {
			int response = JOptionPane.showConfirmDialog(display,
					I18n.tr("3dxm.dialog.saveDialog.fileExists", selectedFile.getName()),
					I18n.tr("3dxm.dialog.saveDialog.confirmSaveTitle"),
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (response == JOptionPane.NO_OPTION)
				return;
		}
		PrintWriter out;
		try {
			out = new PrintWriter(new FileWriter(selectedFile));
		}
		catch (IOException e) {
			errorMessage(I18n.tr("3dxm.dialog.error.cantOpenOutputFile", selectedFile.getName()));
			return;
		}
		Document doc = SaveAndRestore.exhibitToXML(display.getExhibit(),display.getView());
		String galleryName = galleries.currentGalleryName();
		if (galleryName != null) {
			Element galleryElement = doc.createElement("gallery_3dxm");
			galleryElement.setAttribute("name",galleryName);
			doc.getDocumentElement().appendChild(galleryElement);
		}
		try {
			SaveAndRestore.writeXMLDocument(out,doc);
		}
		catch (Exception e) {
			errorMessage(I18n.tr("3dxm.dialog.error.cantWriteFile", selectedFile.getName()));
			return;
		}
		out.close();
		if (out.checkError()) {
			errorMessage(I18n.tr("3dxm.dialog.error.cantWriteFile", selectedFile.getName()));
			return;
		}
		saveFileDialogDirectory();
	}
	
	private void doSaveImage(String format) {
		if (display.getView() == null || display.getExhibit() == null)
			return;
		BufferedImage image;  // Need a BufferedImage to write the image.
		try {
			image = display.getView().getImage(false);
		}
		catch (OutOfMemoryError e) {
			errorMessage(I18n.tr("3dxm.dialog.saveImageDialog.error.OutOfMemory"));
			return;
		}
		if (image == null) {
			errorMessage(I18n.tr("3dxm.dialog.saveImageDialog.error.NoImage"));
			return;
		}
		setupFileDialog();
		String defaultName = display.getExhibit().getTitle() + "." + format.toLowerCase();
		File selectedFile = new File(defaultName);
		fileDialog.setSelectedFile(selectedFile);  // Go to directory from last save image operation
		fileDialog.setDialogTitle(I18n.tr("3dxm.dialog.saveImageDialog.title",format));
		int option = fileDialog.showSaveDialog(display);
		if (option != JFileChooser.APPROVE_OPTION)
			return;  // user canceled
		selectedFile = fileDialog.getSelectedFile();
		if (! selectedFile.exists()) {
			if (format.equalsIgnoreCase("PNG") && ! selectedFile.getName().toLowerCase().endsWith(".png"))
				selectedFile = new File(selectedFile.getParent(), selectedFile.getName() + ".png");
			else if (format.equalsIgnoreCase("JPEG") && ! selectedFile.getName().toLowerCase().endsWith(".jpeg")
					&& ! selectedFile.getName().toLowerCase().endsWith(".jpg"))
				selectedFile = new File(selectedFile.getParent(), selectedFile.getName() + ".jpeg");
		}
		if (selectedFile.exists()) {
			int response = JOptionPane.showConfirmDialog(display,
					I18n.tr("3dxm.dialog.saveDialog.fileExists", selectedFile.getName()),
					I18n.tr("3dxm.dialog.saveDialog.confirmSaveTitle"),
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (response == JOptionPane.NO_OPTION)
				return;
		}
		try {
			if ( ! ImageIO.write(image,format,selectedFile) ) {
				errorMessage(I18n.tr("3dxm.dialog.saveImageDialog.error.BadFormat",format));
				return;
			}
		}
		catch (Exception e) {
			errorMessage(I18n.tr("3dxm.dialog.saveImageDialog.error.WriteError"));
			return;
		}
		saveFileDialogDirectory();
	}
	
	private void doSaveAnimation(String format) {
		Filmstrip filmstrip = display.getSavedFilmstrip();
		if (filmstrip == null)
			return;
		display.stopAnimation();
		int looping = display.getSavedFilmstripLooping();
		boolean cyclic = looping == TimerAnimation.LOOP;
		setupFileDialog();
		String defaultName;
		if (display.getExhibit() == null)
			defaultName = I18n.tr("3dxm.dialog.saveAnimationDialog.AnimationFileNameForExhibit",format.toLowerCase()) + ".3dxmja";
		else
			defaultName = I18n.tr("3dxm.dialog.saveAnimationDialog.AnimationFileNameForExhibit",display.getExhibit().getTitle()) +".3dxmja";
		File selectedFile = new File(defaultName);
		fileDialog.setSelectedFile(selectedFile);  // Go to directory from last save image operation
		fileDialog.setDialogTitle(I18n.tr("3dxm.dialog.saveAnimationDialog.title",format));
		int option = fileDialog.showSaveDialog(display);
		if (option != JFileChooser.APPROVE_OPTION)
			return;  // user canceled
		selectedFile = fileDialog.getSelectedFile();
		if (! selectedFile.exists()) {
			if ( ! selectedFile.getName().toLowerCase().endsWith(".3dxmja"))
				selectedFile = new File(selectedFile.getParent(), selectedFile.getName() + ".3dxmja");
		}
		if (selectedFile.exists()) {
			int response = JOptionPane.showConfirmDialog(display,
					I18n.tr("3dxm.dialog.saveDialog.fileExists", selectedFile.getName()),
					I18n.tr("3dxm.dialog.saveDialog.confirmSaveTitle"),
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (response == JOptionPane.NO_OPTION)
				return;
		}
		OutputStream out;
		try {
			out = new BufferedOutputStream(new FileOutputStream(selectedFile));
		}
		catch (IOException e) {
			errorMessage(I18n.tr("3dxm.dialog.error.cantWriteFile", selectedFile.getName()));
			return;
		}
		display.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try {
			boolean saved = SavedAnimationSupport.saveAnimation(display, out, filmstrip, cyclic, format);
			if (!saved)
				selectedFile.delete();
		}
		catch (IOException e) {
			errorMessage(I18n.tr("3dxm.dialog.error.errorSavingAnimation", selectedFile.getName(), e.getMessage()));
			e.printStackTrace();
		}
		display.setCursor(Cursor.getDefaultCursor());
	}
	
	private void doPlaySavedAnimation() {
		display.stopAnimation();
		setupFileDialog();
		fileDialog.setDialogTitle(I18n.tr("3dxm.dialog.playSavedAnimation.title"));
		int option = fileDialog.showOpenDialog(display);
		if (option != JFileChooser.APPROVE_OPTION)
			return;
		File selectedFile = fileDialog.getSelectedFile();
		ZipFile zipFile;
		try {
			zipFile = new ZipFile(selectedFile);
		}
		catch (IOException e) {
			errorMessage(I18n.tr("3dxm.dialog.playSavedAnimation.CantRead", selectedFile.getName()));
			return;
		}
		display.discardFilmstrip();  // frees up memory for the filmstrip about to be loaded
		try {
			SavedAnimationSupport.readAndPlay(display,zipFile);
		}
		catch (Exception e) {
			errorMessage(I18n.tr("3dxm.dialog.playSavedAnimation.error", selectedFile.getName(), e.getMessage()));
			return;
		}
		finally {
			try {
				zipFile.close();
			}
			catch (Exception e) {
			}
		}
	}
	
	private void errorMessage(String message) {
		JOptionPane.showMessageDialog(display,message,I18n.tr("3dxm.dialog.errormessage.title"),JOptionPane.ERROR_MESSAGE);
	}
	
	
	private class SetSpeedAction implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			Object src = evt.getSource();
			if (src == speedSelect1)
				display.setTimeDilationForAnimations(1);
			else if (src == speedSelect2)
				display.setTimeDilationForAnimations(2);
			else if (src == speedSelect3)
				display.setTimeDilationForAnimations(4);
			else if (src == speedSelect4)
				display.setTimeDilationForAnimations(8);
		}
	}
	
	

}
