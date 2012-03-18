/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.xm3d;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLEditorKit;

import vmm.core.I18n;

/**
 * A window that holds a very simple HTML viewer.
 */
public class HTMLDocWindow extends JFrame {
	
	private JEditorPane editPane;
	private ArrayList<Object> history = new ArrayList<Object>();
	private int currentHistoryIndex = -1;
	private JButton backButton, forwardButton, closeButton;
	
	/**
	 * Creates an initially invisible window. 
	 */
	public HTMLDocWindow() {
		super(I18n.tr("3dxm.HTMLDocWindow.title"));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		editPane = new JEditorPane();
		editPane.setEditable(false);
		editPane.setMargin(new Insets(15,15,10,5));
		editPane.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent evt) {
				if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					try {
						editPane.setPage(evt.getURL());
					}
					catch (Exception e) {
						editPane.setContentType("text/plain");
						editPane.setText(I18n.tr("3dxm.HTMLDocWindow.loadError") + "\n");
					}
				}
			}
		});
		backButton = new JButton(I18n.tr("3dxm.HTMLDocWindow.Back"));
		forwardButton = new JButton(I18n.tr("3dxm.HTMLDocWindow.Forward"));
		closeButton = new JButton(I18n.tr("3dxm.HTMLDocWindow.Close"));
		JPanel buttonBar = new JPanel();
		buttonBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonBar.add(backButton);
		buttonBar.add(forwardButton);
		buttonBar.add(closeButton);
		backButton.setEnabled(false);
		forwardButton.setEnabled(false);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
				history.clear();
				currentHistoryIndex = -1;
			}
		});
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				historyBack();
			}
		});
		forwardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				historyForward();
			}
		});
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout(3,3));
		content.setBackground(Color.GRAY);
		content.add(new JScrollPane(editPane),BorderLayout.CENTER);
		content.add(buttonBar,BorderLayout.SOUTH);
		setContentPane(content);
		HTMLEditorKit kit = (HTMLEditorKit)editPane.getEditorKitForContentType("text/html");
		kit.getStyleSheet().addRule("body { font-size: 16pt }");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width-620)/2,100,650,500);
	}
	
	/**
	 * Creates a window showing the specified initial file, and
	 * shows the window.  Note that the file name is treated as
	 * a resource name that is located using the getResource()
	 * method of the class loader.
	 */
	public HTMLDocWindow(String initialFile) {
		this();
		if (initialFile != null)
			showFile(initialFile);
		else
			setVisible(true);
	}
	
	/**
	 * Creates a window showing the specified intiial URL, and shows
	 * the window.
	 */
	public HTMLDocWindow(URL initialURL) {
		this();
		if (initialURL != null)
			showURL(initialURL);
		else 
			setVisible(true);
	}
	
	/**
	 * Set the contents of the window to a specified URL.  If an error occurs
	 * while trying to load the url, an error message is shown in the window.
	 */
	public void showURL(URL url) {
		historyAdd(url);
		try {
			editPane.setPage(url);
		}
		catch (Exception e) {
			editPane.setContentType("text/plain");
			editPane.setText(I18n.tr("3dxm.HTMLDocWindow.loadError") + "\n");
		}
		setVisible(true);
	}

	/**
	 * Set the content of the window to the specified file.   Note that the file name is treated as
	 * a resource name that is located using the getResource() method of the class loader.
	 * @param file the name of the resource file whose contenxt are to appear in the window.
	 */
	public void showFile(String file) {
		historyAdd(file);
		try {
			URL fileURL = getClass().getClassLoader().getResource(file);
			if (fileURL == null)
				throw new Exception();
			editPane.setPage(fileURL);
		}
		catch (Exception e) {
			editPane.setContentType("text/plain");
			editPane.setText(I18n.tr("3dxm.HTMLDocWindow.loadError") + "\n");
		}
		setVisible(true);
	}
	
	private void historyBack() {
		if (currentHistoryIndex > 0) {
			currentHistoryIndex--;
			showFromHistory();
		}
	}
	
	private void historyForward() {
		if (currentHistoryIndex >= 0 && currentHistoryIndex < history.size() - 1) {
			currentHistoryIndex++;
			showFromHistory();
		}
	}
	
	private void showFromHistory() {
		Object location = history.get(currentHistoryIndex);
		try {
			URL newLocation;
			if (location instanceof String)
				newLocation = getClass().getClassLoader().getResource((String)location);
			else
				newLocation = (URL)location;
			editPane.setPage(newLocation);
		}
		catch (Exception e) {
			editPane.setContentType("text/plain");
			editPane.setText(I18n.tr("3dxm.HTMLDocWindow.loadError") + "\n");
		}
		backButton.setEnabled(currentHistoryIndex > 0);
		forwardButton.setEnabled(currentHistoryIndex < history.size() - 1);
	}
	
	private void historyAdd(Object location) {
		if (currentHistoryIndex == -1) { // first item is being added
			history.add(location);
			currentHistoryIndex = 0;
		}
		else if (location.equals(history.get(currentHistoryIndex)))
			return;
		else {
			while (history.size() > currentHistoryIndex+1)
				history.remove(history.size()-1);
			history.add(location);
			currentHistoryIndex = history.size() - 1;
		}
		backButton.setEnabled(currentHistoryIndex > 0);
		forwardButton.setEnabled(currentHistoryIndex < history.size() - 1);
	}
	
}
