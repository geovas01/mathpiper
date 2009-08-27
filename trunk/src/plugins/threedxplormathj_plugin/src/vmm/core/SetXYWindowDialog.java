/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * A dialog box where the user can set the ranges of x and y values that
 * will be visible.  (The range can be expanded vertically or horizontally
 * to preserve the aspect ratio.)  The dialog box shows four input
 * boxes where the user can input the desired xmin, xmax, ymin, and
 * ymax.
 */
public class SetXYWindowDialog extends SettingsDialog {
	
	private RealParam xmin, xmax, ymin, ymax;
	private ParameterInput xminInput, xmaxInput, yminInput, ymaxInput;
	private View view;

	/**
	 * Show the dialog, and set the View whose limits will be controlled by
	 * this dialog.
	 */
	public static void showDialog(View view) {
		(new SetXYWindowDialog(view)).setVisible(true);
	}
	
	protected SetXYWindowDialog(View view) {
		super(view.getDisplay(), I18n.tr("vmm.core.dialogtitle.SetXYWindowDialog"), true, true);
		this.view = view;
		Transform transform = view.getTransform();
		xmin = new RealParam("vmm.core.dialogtitle.SetXYWindowDialog.xmin", transform.getXminRequested());
		xmax = new RealParam("vmm.core.dialogtitle.SetXYWindowDialog.xmax", transform.getXmaxRequested());
		ymin = new RealParam("vmm.core.dialogtitle.SetXYWindowDialog.ymin", transform.getYminRequested());
		ymax = new RealParam("vmm.core.dialogtitle.SetXYWindowDialog.ymax", transform.getYmaxRequested());
		xminInput = new ParameterInput(xmin);
		xmaxInput = new ParameterInput(xmax);
		yminInput = new ParameterInput(ymin);
		ymaxInput = new ParameterInput(ymax);
		xminInput.setColumns(7);
		xmaxInput.setColumns(7);
		yminInput.setColumns(7);
		ymaxInput.setColumns(7);
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(2,4,5,5));
		inputPanel.add(new JLabel(xmin.getTitle() + ": ",JLabel.RIGHT));
		inputPanel.add(xminInput);
		inputPanel.add(new JLabel(xmax.getTitle() + ": ",JLabel.RIGHT));
		inputPanel.add(xmaxInput);
		inputPanel.add(new JLabel(ymin.getTitle() + ": ",JLabel.RIGHT));
		inputPanel.add(yminInput);
		inputPanel.add(new JLabel(ymax.getTitle() + ": ",JLabel.RIGHT));
		inputPanel.add(ymaxInput);
		addInputPanel(inputPanel);
		addInfoLabel(I18n.tr("vmm.core.SetXYWindowDialog.info"));
	}

	protected boolean doOK() {
		String error;
		error= xminInput.checkContents();
		if (error != null) {
			JOptionPane.showMessageDialog(this,error,I18n.tr("vmm.core.SettingsDialog.errorTitle"),JOptionPane.ERROR_MESSAGE);
			xminInput.selectAll();
			xminInput.requestFocus();
			return false;
		}
		error= xmaxInput.checkContents();
		if (error != null) {
			JOptionPane.showMessageDialog(this,error,I18n.tr("vmm.core.SettingsDialog.errorTitle"),JOptionPane.ERROR_MESSAGE);
			xmaxInput.selectAll();
			xmaxInput.requestFocus();
			return false;
		}
		error= yminInput.checkContents();
		if (error != null) {
			JOptionPane.showMessageDialog(this,error,I18n.tr("vmm.core.SettingsDialog.errorTitle"),JOptionPane.ERROR_MESSAGE);
			yminInput.selectAll();
			yminInput.requestFocus();
			return false;
		}
		error= ymaxInput.checkContents();
		if (error != null) {
			JOptionPane.showMessageDialog(this,error,I18n.tr("vmm.core.SettingsDialog.errorTitle"),JOptionPane.ERROR_MESSAGE);
			ymaxInput.selectAll();
			ymaxInput.requestFocus();
			return false;
		}
		xminInput.setValueFromContents();
		xmaxInput.setValueFromContents();
		yminInput.setValueFromContents();
		ymaxInput.setValueFromContents();
		if (xmin.getValue() >= xmax.getValue()) {
			JOptionPane.showMessageDialog(this,I18n.tr("vmm.core.SetXYWindowDialog.XmaxLessThanXminError"),
					I18n.tr("vmm.core.SettingsDialog.errorTitle"),JOptionPane.ERROR_MESSAGE);
			xminInput.selectAll();
			xminInput.requestFocus();
			return false;
		}
		if (ymin.getValue() >= ymax.getValue()) {
			JOptionPane.showMessageDialog(this,I18n.tr("vmm.core.SetXYWindowDialog.YmaxLessThanYminError"),
					I18n.tr("vmm.core.SettingsDialog.errorTitle"),JOptionPane.ERROR_MESSAGE);
			yminInput.selectAll();
			yminInput.requestFocus();
			return false;
		}
		view.getTransform().setLimits(xmin.getValue(), xmax.getValue(), ymin.getValue(), ymax.getValue());
		return true;
	}

	/**
	 * This will be called when the user clicks the Defaults button.  Gets the default window
	 * from the Exhibit, and uses that to set the values in the xmin, xmax, ymin, and ymax
	 * input boxes.
	 */
	protected void doDefaults() {
		double[] defaultWindow = view.getExhibit().getDefaultWindow();
		xminInput.setText("" + defaultWindow[0]);
		xmaxInput.setText("" + defaultWindow[1]);
		yminInput.setText("" + defaultWindow[2]);
		ymaxInput.setText("" + defaultWindow[3]);
	}

}
