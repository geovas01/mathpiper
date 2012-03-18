/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.xm3d;

import javax.swing.*;

import vmm.core.Exhibit;
import vmm.core.I18n;
import vmm.core.SaveAndRestore;
import vmm.core.UserExhibit;
import vmm.core.View;

import java.awt.event.*;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;


/**
 * An applet that appears on a web page as a single button.  Clicking the
 * button will open a 3D-XPlorMath window.  An applet parameter can be
 * used to specify an Exhibit that should be shown in the window when it
 * opens.  There are some other applet parameters as well, as shown in this
 * table.  Note that all params are optional.
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
 *   <tr><td>OpenCommand</td><td>if present, this string is used as the<br>
 *              name of the button; if absent, the name is<br>
 *              obtained by looking up the localized string<br>
 *              with key 3dxm.LauncherApplet.OpenCommand</td></tr>
 *   <tr><td>CloseCommand</td><td>if present, this string is used as the<br>
 *              name of the button after the window is opened; if absent,<br>
 *               the name is obtained by looking up the localized<br>
 *               string with key 3dxm.LauncherApplet.OpenCommand</td></tr>
 *   </table>
 */
public class LauncherApplet extends JApplet {
	
	private JButton launchButton;
	private String openString;
	private String closeString;
	private String exhibitName;

	private ArrayList<WindowXM> openWindows = new ArrayList<WindowXM>();
	
	public void init() {
		String locale = getParameter("Locale");
		if (locale != null && locale.length() == 2)
			I18n.setLocale(new Locale(locale));
		I18n.addFile("vmm.xm3d.stringsXM");
		DisplayXM.noSplash();
		openString = getParameter("OpenCommand");
		if (openString == null)
			openString = I18n.tr("3dxm.LauncherApplet.OpenCommand");
		closeString = getParameter("CloseCommand");
		if (closeString == null)
			closeString = I18n.tr("3dxm.LauncherApplet.CloseCommand");
		exhibitName = getParameter("Exhibit");
		launchButton = new JButton(openString);
		getContentPane().add(launchButton);
		launchButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				doButton();
			}
		});
	}
		
	private void doButton() {
		launchButton.setEnabled(false);
		if (openWindows.size() == 0) {
			Exhibit exhibit = null;
			View view = null;
			int menuOptionFlags = Menus.NO_FILE_SYSTEM | Menus.NO_EXIT;
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
							view = ((UserExhibit)exhibit).getUserExhibitSupport().showCreateDialog(null);
							if (view == null) {
								launchButton.setEnabled(true);
								return;
							}
						}
						else
							view = exhibit.getDefaultView();
					}
				}
				catch ( Exception e ) {
					System.out.println("Can't load Exhibit specified by applet param \"" + exhibitName + "\"");
					e.printStackTrace();
					exhibit = null;
					view = null;
					return;
				}
				if ( "yes".equalsIgnoreCase(getParameter("SingleExhibit")) )
					menuOptionFlags |= Menus.SINGLE_EXHIBIT;
				else if ("yes".equalsIgnoreCase(getParameter("SingleGallery")) )
					menuOptionFlags |= Menus.SINGLE_GALLERY;
			}
			WindowXM window = new WindowXM(this,menuOptionFlags);
			if (exhibit != null)
				window.getMenus().install(view, exhibit);
			window.setVisible(true);
		}
		else {
			for (WindowXM window : openWindows)
				window.dispose();
		}
	}
	
	void windowClosed(WindowXM window) { 
		openWindows.remove(window);
		if (openWindows.size() == 0) {
			launchButton.setText(openString);
			launchButton.setEnabled(true);
		}
	}
	
	void windowOpened(WindowXM window) { 
		launchButton.setText(closeString);
		launchButton.setEnabled(true);
		openWindows.add(window);
	}

}
