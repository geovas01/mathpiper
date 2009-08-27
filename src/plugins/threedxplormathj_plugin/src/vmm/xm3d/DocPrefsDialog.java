/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.xm3d;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import vmm.core.I18n;
import vmm.core.Prefs;
import vmm.core.SettingsDialog;
import vmm.core.Util;

public class DocPrefsDialog extends SettingsDialog {
	
	private boolean useExternalBrowser;
	private String externalBrowserCommand;
	private String documentationBaseURL;
	
	private JComboBox browserSelect;
	private JTextField docsLocationInput;
	private JButton browseDocLocationButton;
	
	public DocPrefsDialog(Component parent) {
		super(parent,I18n.tr("3dxm.DocPrefsDialog.title"),true,false);
		Box inputPanel = Box.createVerticalBox();
		Box browserPanel = Box.createVerticalBox();
		browserPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(I18n.tr("3dxm.DocPrefsDialog.SelectBrowser")),
				BorderFactory.createEmptyBorder(5,5,5,5)));
		Box locationPanel = Box.createVerticalBox();
		locationPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(I18n.tr("3dxm.DocPrefsDialog.SelectDocsLocation")),
				BorderFactory.createEmptyBorder(5,5,5,5)));
		inputPanel.add(locationPanel);
		inputPanel.add(Box.createVerticalStrut(7));
		inputPanel.add(browserPanel);
		inputPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		browserSelect = new JComboBox();
		useExternalBrowser = Prefs.getBoolean("3dxm.docprefs.useExternalBrowser", false);
		externalBrowserCommand = Prefs.get("3dxm.docprefs.externalBrowserCommand", null);
		if (externalBrowserCommand == null || externalBrowserCommand.indexOf("%f") < 0)
			useExternalBrowser = false;
		documentationBaseURL = Prefs.get("3dxm.docprefs.documentationBaseURL", 
				I18n.tr("3dxm.DocPrefsDialog.DefaultDocumentationLocation"));
		if (useExternalBrowser)
			browserSelect.addItem(externalBrowserCommand);
		browserSelect.addItem(I18n.tr("3dxm.DocPrefsDialog.BuiltInBrowser"));
		String os;
		try {
			os = System.getProperty("os.name").toLowerCase();
		}
		catch (Exception e) {
			os = "";
		}
		if (Util.isMacOS()) {
			browserSelect.addItem("open %f");
			browserSelect.addItem("open -a safari %f");
			browserSelect.addItem("open -a firefox %f");
		}
		else if (os.indexOf("linux") >= 0) {
			browserSelect.addItem("gnome-open %f");
			browserSelect.addItem("kfmclient exec %f");
			browserSelect.addItem("firefox %f");
			browserSelect.addItem("konqueror %f");
		}
		else if (os.indexOf("wind") >= 0) {
			browserSelect.addItem("cmd.exe /c start %f");
			browserSelect.addItem("\"c:\\Program Files\\Internet Explorer\\iexplore\" %f");
			browserSelect.addItem("firefox %f");
		}
		else {
			browserSelect.addItem("firefox %f");
		}
		browserSelect.setSelectedItem(0);
		browserSelect.setEditable(true);
		JPanel temp = new JPanel();
		temp.add(new JLabel(I18n.tr("3dxm.DocPrefsDialog.selectBrowserInfo"),JLabel.LEFT));
		browserPanel.add(temp);
		browserPanel.add(Box.createVerticalStrut(5));
		browserPanel.add(browserSelect);
		docsLocationInput = new JTextField(documentationBaseURL);
		browseDocLocationButton = new JButton(I18n.tr("3dxm.DocPrefsDialog.Browse"));
		browseDocLocationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				browseToSelectDocDirectory();
			}
		});
		temp = new JPanel();
		temp.add(new JLabel(I18n.tr("3dxm.DocPrefsDialog.selectDocsLocationInfo"),JLabel.LEFT));
		locationPanel.add(temp);
		locationPanel.add(Box.createVerticalStrut(5));
		temp = new JPanel();
		temp.setLayout(new BorderLayout(5,5));
		temp.add(docsLocationInput,BorderLayout.CENTER);
		temp.add(browseDocLocationButton,BorderLayout.EAST);
		locationPanel.add(temp);
		addInputPanel(inputPanel);
		pack();
	}


	private void browseToSelectDocDirectory() {
		JFileChooser dialog = new JFileChooser();
		dialog.setDialogTitle(I18n.tr("3dxm.DocPrefsDialog.SelectDocsDirectoryTitle"));
		dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (JFileChooser.APPROVE_OPTION == dialog.showOpenDialog(this)) {
			File docsDir = dialog.getSelectedFile();
			docsLocationInput.setText(docsDir.getAbsolutePath());
		}
	}

	protected void doDefaults() {
		browserSelect.setSelectedItem(I18n.tr("3dxm.DocPrefsDialog.BuiltInBrowser"));
		docsLocationInput.setText(I18n.tr("3dxm.DocPrefsDialog.DefaultDocumentationLocation"));
	}

	protected boolean doOK() {
		String docsLoc = docsLocationInput.getText();
		if (!docsLoc.startsWith("http://")) {
			File file = new File(docsLoc);
			if (!file.exists()) {
				JOptionPane.showMessageDialog(this, I18n.tr("3dxm.DocPrefsDialog.error.DocsDirDoesNotExist"));
				return false;
			}
			if (!file.isDirectory()) {
				JOptionPane.showMessageDialog(this, I18n.tr("3dxm.DocPrefsDialog.error.DocsDirIsNotDirectory"));
				return false;
			}
		}
		externalBrowserCommand = (String)browserSelect.getSelectedItem();
		useExternalBrowser = !externalBrowserCommand.equals(I18n.tr("3dxm.DocPrefsDialog.BuiltInBrowser"));
		if (useExternalBrowser) {
			if (!externalBrowserCommand.contains("%f")) {
				JOptionPane.showMessageDialog(this, I18n.tr("3dxm.DocPrefsDialog.error.BadBrowserCommand"));
				return false;
			}
		}
		Prefs.putBoolean("3dxm.docprefs.useExternalBrowser", useExternalBrowser);
		Prefs.save("3dxm.docprefs.useExternalBrowser");
		if (useExternalBrowser)
			Prefs.putAndSave("3dxm.docprefs.externalBrowserCommand", externalBrowserCommand);
		else
			Prefs.putAndSave("3dxm.docprefs.externalBrowserCommand", "");
		documentationBaseURL = docsLoc;
		Prefs.putAndSave("3dxm.docprefs.documentationBaseURL", documentationBaseURL);
		return true;
	}

	
}
