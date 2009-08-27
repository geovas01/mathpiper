/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.xm3d;

import java.awt.Color;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JComponent;

import vmm.core.Exhibit;
import vmm.core.I18n;
import vmm.core.SaveAndRestore;
import vmm.core.UserExhibit;
import vmm.core.View;

/**
 * Shows 3D-XPlorMath as an applet embedded on a Web page.
 * With no applet parameters, the applet initially shows the
 * usual 3DXM splash image.  Applet parameters can be used to
 * specify the Exhibit that is initially displayed; if this
 * is done, then the Gallery and Exhibit menus can be omitted
 * by using other applet params. Applet params are shown
 * in the following table:
 *   <table align=center border=2 cellpadding=6>
 *   <tr><td><b>Param Name</b></td><td><b>Meaning</b></td></tr>
 *   <tr><td>Exhibit</td><td>Either the full name of an Exhibit class<br>
 *              or the file name for a settings file;<br>
 *              a settings file name must end in .xml.<br>
 *              The settings file can be either a resource or<br>
 *              an actual file in the same directory as the<br>
 *              HTML document that contains the applet tag.</td></tr>
 *   <tr><td>SingleExhibit</td><td>This is ignored unless an Exhibit param<br>
 *              is present and the Exhibit loads without error.<br>
 *              If present with value equal to &quot;yes&quot;,<br>
 *              then the Exhibit and Gallery menus are omitted./td></tr>
 *   <tr><td>SingleGallery</td><td>This is ignored unless an Exhibit param<br>
 *              is present and the Exhibit loads without error.<br>
 *              It is also ignored if SingleExhibit has value &quot;yes&quot;.<br>
 *              If present with value equal to &quot;yes&quot;,<br>
 *              then the Gallery menus are omitted.</td></tr>
 *   <tr><td>Locale</td><td>The value must be a two-letter language code.<br>
 *              The specified langauge is used for localization in
 *              {@link I18n}</td></tr>
 *   <tr><td>StatusBar</td> If present with value equal to &quot;no&quot;,<br>
 *              then the status bar at the bottom of the display is omitted.</td></tr>
 *   </table>
 */
public class EmbeddedApplet extends JApplet {
	
	private DisplayXM display;
	
	public void init() {
		String locale = getParameter("Locale");
		if (locale != null && locale.length() == 2)
			I18n.setLocale(new Locale(locale));
		I18n.addFile("vmm.xm3d.stringsXM");
		DisplayXM.noSplash();
		int menuOptions = Menus.NO_FILE_SYSTEM | Menus.NO_ACCELERATORS | Menus.NO_EXIT;
		display = new DisplayXM();
		JComponent holder = (JComponent)display.getHolder();
		holder.setBorder(BorderFactory.createLineBorder(Color.GRAY,2));
		if ("no".equalsIgnoreCase(getParameter("StatusBar")))
			display.setShowStatusBar(false);
		String exhibitName = getParameter("Exhibit");
		Exhibit exhibit = null;
		View view = null;
		if (exhibitName != null) {
			try {
				if (exhibitName.endsWith(".xml")) {
					ClassLoader cl = MainForWebStart.class.getClassLoader();
					URL url = cl.getResource(exhibitName);  // try to load as resource
					if (url == null)
						url = new URL(getDocumentBase(),exhibitName);  // try to load file
					InputStream settings =  url.openStream();
					exhibit = SaveAndRestore.readExhibitFromXML(settings,"Settings File");
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
				if ( "yes".equalsIgnoreCase(getParameter("SingleExhibit")) )
					menuOptions |= Menus.SINGLE_EXHIBIT;
				else if ("yes".equalsIgnoreCase(getParameter("SingleGallery")) )
					menuOptions |= Menus.SINGLE_GALLERY;
			}
			catch ( Exception e ) {
				System.out.println("Can't load Exhibit specified by applet param \"" + exhibitName + "\"");
				e.printStackTrace();
				exhibit = null;
			}
		}
		Menus menuBar = new Menus(null,display,menuOptions);
		if (exhibit != null)
			menuBar.install(view, exhibit);
		display.setStopAnimationsOnResize(false);
		setJMenuBar(menuBar);
		setContentPane(holder);
	}
	
	public void stop() {
		display.stopAnimation();
	}
	

}
