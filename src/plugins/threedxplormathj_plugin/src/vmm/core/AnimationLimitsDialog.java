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
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class supports making a dialog where the user can set upper and lower limits on
 * animated parameters.  In general, the only method that you will use is the static <code>showDialog</code> method
 * which creates the dialog and makes appear on the screen.  Note that the input boxes in this
 * dialog are objects of type {@link vmm.core.ParameterInput}.
 */
public class AnimationLimitsDialog extends SettingsDialog {
	
	private Parameter[] parameters;
	private ParameterInput[][] inputBoxes;
	
	/**
	 * Create and show a modal dialog for setting animation limits.   This is the method that
     * will most commonly be used to create AnimationLimitsDialogs.  This method does not return
     * until the user has either canceled the dialog or clicked OK.  When the user clicks OK,
     * only legal values are accepted.  That is,
     * any changes made by the user are checked to see whether they represent legal values for
     * the parameters; if not, the dialog remains on the screen.  The input boxes in this
     * dialog are of type {@link ParameterInput}.
     * <p>If there are no animateable parameters in the list of parameters, then a message
     * to that effect is displayed to the user, do AnimationLimits dialog is shown,
     * and the return value of this method is false.
	 * @param parentComponent If parentComponent is non-null and is contained in a JFrame,
	 * then that JFrame becomes the parent of the modal dialog (so user interaction with that frome
	 * is blocked until the user dismisses the dialog).  If parentComponet is null or is not contained
	 * in JFrame, then an invisible JFrame is created to be the parent of the dialog.
	 * @param title The title to appear in the dialog box's title bar.
	 * @param params An array of Parameters.  Parameters that are not of type Animateable 
	 * are filtered out and the remaining parameters are shown in the dialog.  There should be
	 * at least one Animateable parameter in this array.
	 * User modifications are aplied directly to the parameter objects passed in this array.
	 */
	public static boolean showDialog(Component parentComponent, String title, Parameter[] params) {
		boolean animated = false;
		if (params != null) {
			for (int i = 0; i < params.length; i++)
				if (params[i] instanceof Animateable) {
					animated = true;
					break;
				}
		}
		if (!animated) {
			JOptionPane.showMessageDialog(parentComponent,I18n.tr("vmm.core.AnimationLimitsDialog.noMorphing"));
			return false;
		}
		AnimationLimitsDialog dialog = new AnimationLimitsDialog(parentComponent,title,params);
		dialog.setVisible(true);
		return ! dialog.canceled;
	}
	
	/**
	 * Create a dialog box for setting the limits on a specified set of animated parameters.
	 * This constrcutor will probably not generally be used directly; the showDialog() method will
	 * usually be used instead.  Note that the dialog will be created even if there are no
	 * Aniamteable parameters in the list (but showDialog() checks this before creating a dialog).
	 */
	public AnimationLimitsDialog(Component parentComponent, String title, Parameter[] params) {
		super(parentComponent,title,true,false);
		if (parentComponent != null && parentComponent instanceof Display) {
			final Display display = (Display)parentComponent;
			AbstractAction morph = new AbstractAction(I18n.tr("vmm.core.AnimatinonLimitsDialog.MorphButtonName")) {
				public void actionPerformed(ActionEvent evt) {
					if (!doOK())
						return;
					View view = display.getView();
					if (view == null)
						return;
					Exhibit exhibit = view.getExhibit();
					if (exhibit == null)
						return;
					Animation morphAnim = exhibit.getMorphingAnimation(view, BasicAnimator.OSCILLATE);
					if (morphAnim != null) {
						canceled = false;
						dispose();
						display.installAnimation(morphAnim);
					}
				}
			};
			buttonBar.add(new JButton(morph), 2);
		}
		int animatedCount = 0;
		if (params != null) {
			for (int i = 0; i < params.length; i++)
				if (params[i] instanceof Animateable) 
					animatedCount++;
		}
		if (animatedCount == params.length)
			parameters = params;
		else {
			parameters = new Parameter[animatedCount];
			int pos = 0;
			for (int i = 0; i < params.length; i++)
				if (params[i] instanceof Animateable) 
					parameters[pos++] = params[i];
		}
		JPanel topPanel = new JPanel();
		topPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		topPanel.setLayout(new GridLayout(1,0,5,5));
		topPanel.add(new JLabel(I18n.tr("vmm.core.ParameterDialog.ParameterName"),JLabel.CENTER));
		topPanel.add(new JLabel(I18n.tr("vmm.core.ParameterDialog.AnimationStartValue"),JLabel.CENTER));
		topPanel.add(new JLabel(I18n.tr("vmm.core.ParameterDialog.AnimationEndValue"),JLabel.CENTER));
		JPanel inputPanel = new JPanel();
		inputPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				BorderFactory.createEmptyBorder(4,4,4,4)));
		inputBoxes = new ParameterInput[parameters.length][2];
		inputPanel.setLayout(new GridLayout(0,3,5,5));
		for (int i = 0; i < parameters.length; i++) {
			JLabel label = new JLabel(parameters[i].getTitle(),JLabel.CENTER);
			String tip = parameters[i].getHint();
			if (tip != null)
				label.setToolTipText(tip);
			inputPanel.add(label);
			inputBoxes[i][0] = parameters[i].createParameterInput(ParameterInput.ANIMATION_START);
			inputPanel.add(inputBoxes[i][0]);
			inputBoxes[i][0].setMargin(new Insets(3,3,3,3));
			inputBoxes[i][1] = parameters[i].createParameterInput(ParameterInput.ANIMATION_END);
			inputPanel.add(inputBoxes[i][1]);
			inputBoxes[i][1].setMargin(new Insets(3,3,3,3));
		}
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(inputPanel,BorderLayout.CENTER);
		p.add(topPanel,BorderLayout.NORTH);
		addInputPanel(p);
		setResizable(false);
	}
	
	
	
	protected boolean doOK() {
		for (int i = 0; i < parameters.length; i++) {
			String error = inputBoxes[i][0].checkContents();
			if (error != null) {
				errorMessage(error);
				return false;
			}
			error = inputBoxes[i][1].checkContents();
			if (error != null) {
				errorMessage(error);
				return false;
			}
		}
		for (int i = 0; i < parameters.length; i++) {
			inputBoxes[i][0].setValueFromContents();
			inputBoxes[i][1].setValueFromContents();
		}	
		return true;
	}
	
	private void errorMessage(String message) {
		JOptionPane.showMessageDialog(this,message,I18n.tr("vmm.core.ParameterDialog.errorTitle"),JOptionPane.ERROR_MESSAGE);
	}
		
	
	protected void doDefaults() {
		for (int i = 0; i < parameters.length; i++) {
			inputBoxes[i][0].defaultVal();
			inputBoxes[i][1].defaultVal();
		}
	}
	
}
