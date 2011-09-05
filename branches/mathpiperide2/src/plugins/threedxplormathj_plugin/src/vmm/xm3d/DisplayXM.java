/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.xm3d;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import vmm.core.Display;
import vmm.core.Exhibit;
import vmm.core.I18n;
import vmm.core.View;

/**
 * A Display class for use in the 3DXM application, with a few extra features.
 */
public class DisplayXM extends Display {
	
	private static String backgroundURL = "vmm/resources/splash_background.jpeg";
	   // The background image is loaded from this URL (which is
	   // meant to reference a resource file in the application's
	   // jar file.
	
	private static boolean firstWindow = true;
	private volatile Image splashBackground;
	
	/**
	 * This constructor sets up handlers for several keystrokes, which will
	 * be processed by a DisplayXM whenever it is in a focussed window.
	 * Control-period, Meta-period, and Escape will cause any ongoing
	 * animation to be stopped.  The space bar will pause/unpause any
	 * ongoing animation.  Also, the first DisplayXM that is created
	 * will show a background image and greeting message until the first
	 * exhibit is installed.
	 */
	public DisplayXM() {
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("control PERIOD"), "reset");
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("meta PERIOD"), "reset");
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("ESCAPE"), "reset");
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("SPACE"), "pause");
		getActionMap().put("reset",new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				stopAnimation();
			}
		});
		getActionMap().put("pause", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				toggleAnimationPaused();
			}
		});
		if (firstWindow) {
			try {
				java.net.URL imageURL = getClass().getClassLoader().getResource(backgroundURL);
				if (imageURL != null)
					splashBackground = Toolkit.getDefaultToolkit().createImage(imageURL);
			}
			catch (Exception e) {
			}
			firstWindow = false;
		}
	}
	
	static void noSplash() {  // called from MainForWebStart.java, LauncherApplet.java, EmbeddedApplet.java to surpress the spassh screen in the first window.
		firstWindow = false;
	}
	
	public void install(View view, Exhibit exhibit) {
		super.install(view, exhibit);
		if (getExhibit() != null)
			splashBackground = null;
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (getStatus() == STATUS_EMPTY) {
			if (splashBackground != null) {
				g.drawImage(splashBackground,0,0,getWidth(),getHeight(),this);
				showWelcomeMessage(g);
			}
			else {
				g.setColor(Color.red);
				g.drawString(I18n.tr("3dxm.DisplayXM.SelectExhibitMessage"), 15, 30);
			}
		}
	}
	
	private void showWelcomeMessage(Graphics g) {
		g.setFont(new Font("Serif",Font.BOLD,18));
		String message = I18n.tr("3dxm.DisplayXM.WelcomeMessage");
		String version = I18n.tr("application.nameAndVersion");
		String[] msg = message.split("\n");
		String[] lines = new String[msg.length + 1];
		lines[0] = version;
		for (int i = 0; i < msg.length; i++)
			lines[i+1] = msg[i];
		FontMetrics fm = g.getFontMetrics();
		int lineheight = fm.getAscent() + fm.getDescent() + fm.getLeading() + 12;
		int y = fm.getAscent() + (getHeight() - lineheight*lines.length)/2;
		int width = 0;
		for (int i = 0; i < lines.length; i++) {
			int linewidth = fm.stringWidth(lines[i]);
			if (linewidth > width)
				width = linewidth;
		}
		int x = (getWidth() - width)/2;
		g.setColor(Color.RED);
		for (int i = 0; i < lines.length; i++) {
			g.drawString(lines[i], x + (width-fm.stringWidth(lines[i]))/2, y);
			y += lineheight;
		}
	}
	
	
	
	
}
