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
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Shows a dialog where the user can change the values of some Parameters.
 * In general, only the static <code>showDialog</code> method will be used.
 * Note that in a ParameterDialog, pressing the ESCAPE key is the same as clicking the Cancel button,
 * and pressing return key is the same as clicking the OK button.
 */
public class ParameterDialog extends SettingsDialog {
	
	private Parameter[] parameters;
	private ParameterInput[] inputBoxes;
	
    /**
     * Create and show a modal dialog for setting parameter values.  This is the method that
     * will most commonly be used to create ParameterDialogs.  This method does not return
     * until the user has either canceled the dialog or clicked OK.  When the user clicks OK,
     * only legal parameter values are accepted.  That is,
     * any changes made by the user are checked to see whether they represent legal values for
     * the parameters; if not, the dialog remains on the screen.  The input boxes in this
     * dialog are of type {@link ParameterInput}.
     * @param parentComponent If parentComponent is non-null and is contained in a JFrame,
     * then that JFrame becomes the parent of the modal dialog (so user interaction with that frome
     * is blocked until the user dismisses the dialog).  If parentComponet is null or is not contained
     * in a JFrame, then an invisible JFrame is created to be the parent of the dialog.
     * @param title The title to appear in the dialog box's title bar.
     * @param params An array of Parameters. The array should be
     * non-null and non-empty. The values of the parameters can change if the user clicks the
     * dialog's OK button.  No changes are made if the user cancels the dialog.
     */
	public static boolean showDialog(Component parentComponent, String title, Parameter[] params) {
		ParameterDialog dialog = new ParameterDialog(parentComponent,title,params);
		dialog.setVisible(true);
		return ! dialog.canceled;
	}
	
	/**
	 * Create a dialog box for setting the values of some Parameters.  This is probably not
	 * going to be used directly.  The static <code>showDialog</code> method is more likely to be used.
	 * @see #showDialog(Component, String, Parameter[])
	 */
	public ParameterDialog(Component parentComponent, String title, Parameter[] params) {
		super(parentComponent,title,true,true);
		this.parameters = params;
		JPanel topPanel = new JPanel();
		topPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		topPanel.setLayout(new GridLayout(1,0,5,5));
		topPanel.add(new JLabel(I18n.tr("vmm.core.ParameterDialog.ParameterName"),JLabel.CENTER));
		topPanel.add(new JLabel(I18n.tr("vmm.core.ParameterDialog.ParameterValue"),JLabel.CENTER));
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
        		BorderFactory.createLineBorder(Color.BLACK),
				BorderFactory.createEmptyBorder(4,4,4,4)));
        inputBoxes = new ParameterInput[params.length];
        inputPanel.setLayout(new GridLayout(0,2,5,5));
		for (int i = 0; i < params.length; i++) {
			JLabel label = new JLabel(params[i].getTitle() + " = ",JLabel.RIGHT);
			final String tip = params[i].getHint();
			if (tip == null)
				inputPanel.add(label);
			else {
				JPanel labelPanel = new JPanel();
				inputPanel.add(labelPanel);
				labelPanel.setLayout(new BorderLayout());
				labelPanel.add(label,BorderLayout.CENTER);
				JButton showHint = new JButton("?");
				showHint.setPreferredSize(new Dimension(40,showHint.getPreferredSize().height));  // Button was too wide
				labelPanel.add(showHint,BorderLayout.WEST);
				final String dialogtitle = params[i].getTitle();
				showHint.addActionListener( new ActionListener() { 
					public void actionPerformed(ActionEvent evt) {
						JOptionPane.showMessageDialog(ParameterDialog.this, new JLabel(tip), dialogtitle, JOptionPane.INFORMATION_MESSAGE);
					}
				});
			}
			inputBoxes[i] = params[i].createParameterInput(ParameterInput.VALUE);
			inputPanel.add(inputBoxes[i]);
			inputBoxes[i].setMargin(new Insets(3,3,3,3));
		}
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout(6,6));
		p.add(topPanel,BorderLayout.NORTH);
		p.add(inputPanel,BorderLayout.CENTER);
		addInputPanel(p);
	}
	

	/**
	 * This is called when the user clicks OK to check the data in the input boxes.  If all the data
	 * is legal, then the values of the parameters are changed and the return value is true.
	 */
	protected boolean doOK() {
		for (int i = 0; i < parameters.length; i++) {
			String error = inputBoxes[i].checkContents();
			if (error != null) {
				JOptionPane.showMessageDialog(this,error,I18n.tr("vmm.core.ParameterDialog.errorTitle"),JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		for (int i = 0; i < parameters.length; i++)
			inputBoxes[i].setValueFromContents();
		return true;
	}
	
	
	protected void doDefaults() {
		for (int i = 0; i < parameters.length; i++) {
			inputBoxes[i].defaultVal();
		}
	}

}
