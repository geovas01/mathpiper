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
import vmm.xm3d.Menus;
import vmm.xm3d.DisplayXM;
import vmm.xm3d.MainForWebStart;


public class ThreeDXplorMathJApplet extends JApplet {
	
	private DisplayXM display;
	
	public void init() {
		String locale = getParameter("Locale");
		if (locale != null && locale.length() == 2)
			I18n.setLocale(new Locale(locale));
		I18n.addFile("vmm.xm3d.stringsXM");
		DisplayXM.noSplash();
		int menuOptions = Menus.NO_ACCELERATORS | Menus.NO_EXIT;// | Menus.NO_FILE_SYSTEM;
		display = new DisplayXM();
		JComponent holder = (JComponent)display.getHolder();
		holder.setBorder(BorderFactory.createLineBorder(Color.GRAY,2));
		if ("no".equalsIgnoreCase(getParameter("StatusBar")))
			display.setShowStatusBar(false);
		//String exhibitName = getParameter("Exhibit");
		String exhibitName = "vmm.surface.parametric.MonkeySaddle";
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
		if (exhibit != null) menuBar.install(view, exhibit);
		display.setStopAnimationsOnResize(false);
		setJMenuBar(menuBar);
		setContentPane(holder);
	}
	
	public void stop() {
		display.stopAnimation();
	}
	

}

