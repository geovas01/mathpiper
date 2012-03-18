/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.xm3d;

import java.io.InputStream;
import java.net.URL;
import java.util.Locale;

import vmm.core.Exhibit;
import vmm.core.I18n;
import vmm.core.SaveAndRestore;
import vmm.core.UserExhibit;
import vmm.core.View;

/**
 * A main class to be used with WebStart.  Runs a version of
 * the program that does not include file system commands.
 * Command line argments can include: the option "--SingleExhibit"
 * or the option "--SingleGallery" (if both are present, only
 * --SingleExhibit is applied);  an option "--Locale=xx" where
 * xx is the two-letter language code to be used in {@link I18n};
 * and an argement specifying an Exhibit
 * to be shown when the program is launched -- this argument
 * should be either the full class name of an Exhibit class or
 * the full resource file name, ending in ".xml", for an xml settings file).
 * Any other command line arguments are ignored.
 * Note that if no Exhibit is specified, or if the Exhibit cannot
 * be loaded, then the "--SingleGallery" or "--SingleExhibit" is
 * ignored.  (It is <b>not</b> possible to show a single gallery
 * with no Exhibit initially selected.)
 */
public class MainForWebStart {

	public static void main(String[] args) {
		boolean singleExhibit = false;
		boolean singleGallery = false;
		String exhibitName = null;
		DisplayXM.noSplash();
		for (int i = 0; i < args.length; i++) {
			if (args[i].equalsIgnoreCase("--SingleExhibit"))
				singleExhibit = true;
			else if (args[i].equalsIgnoreCase("--SingleGallery"))
				singleGallery = true;
			else if (args[i].toLowerCase().startsWith("--locale=")) {
				String locale = args[i].substring(9).toLowerCase();
				if (locale.length() == 2)
					I18n.setLocale(new Locale(locale));
			}
			else if (! args[i].startsWith("-"))
				exhibitName = args[i];
		}
		Exhibit exhibit = null;
		View view = null;
		if (exhibitName != null) {
			try {
				if (exhibitName.endsWith(".xml")) {
					ClassLoader cl = MainForWebStart.class.getClassLoader();
					URL url = cl.getResource(exhibitName);
					if (url == null)
						throw new Exception("Can't find resource file.");
					InputStream in = url.openStream();					
					exhibit = SaveAndRestore.readExhibitFromXML(in,"Settings File");
					view = exhibit.getViews().get(0);
				}
				else {
					exhibit = (Exhibit)Class.forName(exhibitName).newInstance();
					if (exhibit instanceof UserExhibit) {
						view = ((UserExhibit)exhibit).getUserExhibitSupport().defaults();
						if (view == null)
							throw new Exception("Error while creating user exhibits with default settings");
					}
					else
						view = exhibit.getDefaultView();
				}
			}
			catch (Exception e) {
				System.out.println("Could not load specified exhibit, \"" + exhibitName + "\".");
				e.printStackTrace();
				singleExhibit = singleGallery = false;
				exhibit = null;
				view = null;
			}
		}
		int options = Menus.NO_FILE_SYSTEM;
		if (exhibitName != null && singleExhibit)
			options |= Menus.SINGLE_EXHIBIT;
		else if (exhibitName != null && singleGallery)
			options |= Menus.SINGLE_GALLERY;
		WindowXM window = new WindowXM(options);
		if (exhibit != null)
			window.getMenus().install(view, exhibit);
		window.setVisible(true);
	}
	
}
