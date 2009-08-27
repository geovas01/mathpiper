/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * An abstract base class that can be used as the basis for various settings dialogs.
 * This class provides a dialog box with OK, cancel, and optional defaults button.
 */
abstract public class SettingsDialog extends JDialog {
	
	/**
	 * One of the buttons that can appear at the bottom of the dialog box.
	 */
	protected JButton okButton, defaultsButton, cancelButton, applyButton;
	
	/**
	 * This variable is set to false when the {@link #doCancel()} method is called.  Nothing else is done 
	 * with it in this class.
	 */
	protected boolean canceled;
	
	/**
	 * The content pane of the dialog box.  This pane has a BorderLayout, with the panel that
	 * contains the buttons in the SOUTH position.  The input panel will be added to the
	 * CENTER position by the {@link #addInputPanel(JComponent)} method.  A subclass might want to put
	 * something in the NORTH, EAST or WEST position.
	 */
	protected JPanel contentPanel;
	
	/**
	 * The panel that holds the row of buttons, at the bottom of the dialog box.
	 * Subclasses could add extra buttons to this panel.
	 */
	protected JPanel buttonBar;
	
	/**
	 * Create a modal SettingsDialog, containing only the three buttons.  An "input panel"
	 * can be added with {@link #addInputPanel(JComponent)}.  The dialog box is not shown automatically.
	 * @param parent if this component is non-null and is contained in a JFrame, that JFrame becomes the parent of the dialog box
	 * @param title the title of the dialog box
	 */
	protected SettingsDialog(Component parent, String title) {
		this(parent,title,true,true);
	}

	/**
	 * Create a modal SettingsDialog, containing only two or threebuttons.  An "input panel"
	 * can be added with {@link #addInputPanel(JComponent)}.  The dialog box is not shown automatically.
	 * @param parent if this component is non-null and is contained in a JFrame, that JFrame becomes the parent of the dialog box
	 * @param title the title of the dialog box
	 * @param showDefaultsButton if true, a Defaults button is added; if false, it is not added
	 * @param showApplyButton if ture, an Apply button is added
	 */
	protected SettingsDialog(Component parent, String title, boolean showDefaultsButton, boolean showApplyButton) {
		super(frameAncestor(parent),title,true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		buttonBar = new JPanel();
		buttonBar.setLayout(new FlowLayout(FlowLayout.RIGHT, 7, 7));
		buttonBar.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		contentPanel.add(buttonBar,BorderLayout.SOUTH);
		setContentPane(contentPanel);
		okButton = new JButton(I18n.tr("buttonNames.OK"));
		defaultsButton = new JButton(I18n.tr("buttonNames.Defaults"));
		cancelButton = new JButton(I18n.tr("buttonNames.Cancel"));
		applyButton = new JButton(I18n.tr("buttonNames.Apply"));
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Object src = evt.getSource();
				if (src == okButton) {
					if (doOK()) {
						canceled = false;
						dispose();
					}
				}
				else if (src == applyButton) 
					doApply();
				else if (src == defaultsButton)
					doDefaults();
				else if (src == cancelButton)
					doCancel();
			}
		};
		okButton.addActionListener(listener);
		defaultsButton.addActionListener(listener);
		cancelButton.addActionListener(listener);
		applyButton.addActionListener(listener);
		buttonBar.add(cancelButton);
		if (showDefaultsButton)
			buttonBar.add(defaultsButton);
		if (showApplyButton)
			buttonBar.add(applyButton);
		buttonBar.add(okButton);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		buttonBar.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("ESCAPE"), "cancel");
		buttonBar.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("ENTER"), "ok");
		buttonBar.getActionMap().put("cancel",new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				cancelButton.doClick();
			}
		});
		buttonBar.getActionMap().put("ok",new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				okButton.doClick();
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				doCancel();
			}
		});
		JFrame parentFrame = frameAncestor(parent);
		if (parentFrame != null) {
			Point pt = parentFrame.getLocation();
			setLocation(pt.x + 25, pt.y + 45);
		}
		setResizable(false);
	}
	
	private static JFrame frameAncestor(Component c) {
		while (c != null && !(c instanceof JFrame))
			c = c.getParent();
		return (JFrame)c;
	}
	
	/**
	 * Adds the specified panel to the CENTER position of the dialog box.  This method should be called
	 * before the panel is shown.  This panel will contain all the input components to get data from the user.
	 */
	protected void addInputPanel(JComponent inputPanel) {
		inputPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		contentPanel.add(inputPanel,BorderLayout.CENTER);
		pack();
	}
	
	/**
	 * Adds a label to the top of the dialog box, above the input panel.  This method should be called befor
	 * the panel is shown.
	 * @param info the text to be shown on the label.  This is passed to the constructor for JLabel and
	 * so can be html-formated text.
	 */
	protected void addInfoLabel(String info) {
		JLabel infoLabel = new JLabel(info);
		infoLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		contentPanel.add(infoLabel,BorderLayout.NORTH);
		pack();
	}
	
	/**
	 * Returns the defaults button.  This is meant to be used in cases where the defaults button is
	 * not added to the dialog box in the constructor, to retrieve the defaults button so it can be used in
	 * the input panel instead.
	 */
	protected JButton defaultsButton() {
		return defaultsButton;
	}
	
	/**
	 * A convenience method for creating a panel that uses a titled border
	 * @param title the title for the titled border
	 */
	protected JPanel borderedPanel(String title) {
		JPanel panel = new JPanel();
		panel.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createEmptyBorder(4,0,4,0),
					BorderFactory.createCompoundBorder(
							BorderFactory.createTitledBorder(
									BorderFactory.createLineBorder(Color.black),
									title ),
							BorderFactory.createEmptyBorder(5,5,5,5) )
					));
		return panel;
	}

	
	/**
	 * This will be called when the user clicks the Defaults button (and will never be called if there is no
	 * Defaults button).  The method in the SettingsDialog class does nothing.
	 */
	protected void doDefaults() {
	}
	
	/**
	 * This is called when the user clicks the Cancel button or closes the dialog box by clicking on its
	 * close box.  It sets the protected variable "canceled" to true and disposed of the window.  It is
	 * probably not necessary to override this method.
	 */
	protected void doCancel() {
		canceled = true;
		dispose();
	}
	
	/**
	 * This is called when the user clicks the Apply button.  The method in the SettingsDialog class
	 * simply calls {@link #doOK()} and ignores the return value.  Presumably, this will get the data
	 * from the dialog and apply it if it is legal, but will leave the dialog box on the screen.
	 */
	protected void doApply() {
		doOK();
	}
	
	/**
	 * This is called when the user clicks the OK button.  The return value indicates whether the dialog should
	 * be disposed of.  If the return value is true, the dialog is disposed.  If the return value is false,
	 * the dialog stays on the screen.  Presumably, the return value will be false when the data entered
	 * by the user is not legal.
	 */
	abstract protected boolean doOK();

}
