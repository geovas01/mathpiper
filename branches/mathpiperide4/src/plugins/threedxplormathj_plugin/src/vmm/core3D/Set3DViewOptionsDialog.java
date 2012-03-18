/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import vmm.core.I18n;
import vmm.core.ParameterInput;
import vmm.core.Prefs;
import vmm.core.RealParam;
import vmm.core.SettingsDialog;

/**
 * A dialog for setting 3D view options (an eye separation multiplier that multiplies
 * the inter-eye distance in all stereo views, and a choice of when anaglyph view
 * mode should be used for an exhibit). Note that the values
 * of these options are saved, using {@link Prefs}, in
 * a way that makes them apply until the user exits the program.
 */
public class Set3DViewOptionsDialog extends SettingsDialog {
	
	private View3D view;
	
	private RealParam multiplier;
	
	private ParameterInput input;
	
	private double originalVal;
	
	private JRadioButton alwaysAnaglyphButton, neverAnaglyphButton, defaultAnaglyphButton;
	private String originalAnaglyph;
	
	private JCheckBox moveObjectsForwardInAnaglyphToggle;
	private boolean originalMoveObjectsForwardInAnaglyph;
	
	public static void showDialog(View3D view) {
		(new Set3DViewOptionsDialog(view)).setVisible(true);
	}
	
	/**
	 * Construct a dialog for setting the eye 3D view options.
	 * @param view A view whose display will be the parent of the dialog.  This view is
	 *    redrawn (if it is showing a stereo view) to show the new eye separation, and anaglyph
	 *    is turned on or off if the setting of apply anaglyph is never or always.
	 */
	private Set3DViewOptionsDialog(View3D view) {
		super(view.getDisplay(),I18n.tr("vmm.core3D.commands.SetEyeSep"));
		this.view = view;
		originalVal = Prefs.getDouble("eyeSeparationMultiplier", 1);
		multiplier = new RealParam(I18n.tr("vmm.core3D.SetEyeSepDialog.EyeSep"),originalVal);
		multiplier.setDefaultValue(1);
		multiplier.setMinimumValueForInput(0.1);
		multiplier.setMaximumValueForInput(10);
		input = new ParameterInput(multiplier);
		input.setColumns(5);
		Box inputPanel = Box.createVerticalBox();
		inputPanel.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
		Box eyeSepInput = Box.createVerticalBox();
		Box viewModeInput = Box.createVerticalBox();
		Box anaglyphPositioningInput = Box.createVerticalBox();
		eyeSepInput.setAlignmentX(0);
		viewModeInput.setAlignmentX(0);
		inputPanel.add(eyeSepInput);
		inputPanel.add(Box.createVerticalStrut(12));
		inputPanel.add(viewModeInput);
		inputPanel.add(Box.createVerticalStrut(12));
		inputPanel.add(anaglyphPositioningInput);
		eyeSepInput.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(I18n.tr("vmm.core3D.Set3DViewOptionsDialog.SetEyeSepMul")),
				BorderFactory.createEmptyBorder(8,8,8,8)));
		viewModeInput.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(I18n.tr("vmm.core3D.Set3DViewOptionsDialog.SetViewModePref")),
				BorderFactory.createEmptyBorder(8,8,8,8)));
		anaglyphPositioningInput.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(I18n.tr("vmm.core3D.Set3DViewOptionsDialog.SetAnaglyphObjectPosition")),
				BorderFactory.createEmptyBorder(8,8,8,8)));
		JLabel labl = new JLabel(I18n.tr("vmm.core3D.Set3DViewOptionsDialog.EyeSep.info"));
		labl.setAlignmentX(0);
		eyeSepInput.add(labl);
		viewModeInput.add(Box.createVerticalStrut(18));
		JPanel temp = new JPanel();
		labl =  new JLabel(I18n.tr("vmm.core3D.Set3DViewOptionsDialog.EyeSepMul") + " = ");
		labl.setAlignmentX(0);
		temp.add(labl);
		eyeSepInput.add(temp);
		temp.add(input);
		temp.setAlignmentX(0);
		originalAnaglyph = Prefs.get("view3d.initialAnaglyphMode", "default");
		alwaysAnaglyphButton = new JRadioButton(I18n.tr("vmm.core3D.SetAnaglyphDefaultMode.always"));
		neverAnaglyphButton = new JRadioButton(I18n.tr("vmm.core3D.SetAnaglyphDefaultMode.never"));
		defaultAnaglyphButton = new JRadioButton(I18n.tr("vmm.core3D.SetAnaglyphDefaultMode.default"));
		ButtonGroup group = new ButtonGroup();
		group.add(alwaysAnaglyphButton);
		group.add(neverAnaglyphButton);
		group.add(defaultAnaglyphButton);
		if (originalAnaglyph.equalsIgnoreCase("always"))
			alwaysAnaglyphButton.setSelected(true);
		else if (originalAnaglyph.equalsIgnoreCase("never"))
			neverAnaglyphButton.setSelected(true);
		else
			defaultAnaglyphButton.setSelected(true);
		labl = new JLabel(I18n.tr("vmm.core3D.Set3DViewOptionsDialog.AnaglyphPref.info"));
		viewModeInput.add(labl);
		viewModeInput.add(Box.createVerticalStrut(12));
		viewModeInput.add(defaultAnaglyphButton);
		viewModeInput.add(alwaysAnaglyphButton);
		viewModeInput.add(neverAnaglyphButton);
		moveObjectsForwardInAnaglyphToggle = new JCheckBox(I18n.tr("vmm.core3D.Set3DViewOptionsDialog.AnaglyphPositionOption"));
		originalMoveObjectsForwardInAnaglyph = "yes".equals(Prefs.get("view3d.moveObjectsForwardInAnaglyph"));
		moveObjectsForwardInAnaglyphToggle.setSelected(originalMoveObjectsForwardInAnaglyph);
		anaglyphPositioningInput.add(Box.createVerticalStrut(2));
		anaglyphPositioningInput.add(moveObjectsForwardInAnaglyphToggle);
		addInfoLabel(I18n.tr("vmm.core3D.Set3DViewOptionsDialog.info"));
		addInputPanel(inputPanel);
	}
	
	protected boolean doOK() {
		String error = input.checkContents();
		if (error != null) {
			JOptionPane.showMessageDialog(this,error,I18n.tr("vmm.core.SettingsDialog.errorTitle"),JOptionPane.ERROR_MESSAGE);
			return false;
		}
		input.setValueFromContents();
		double sep = multiplier.getValue();
		if (sep != originalVal) {
			Prefs.putDouble("eyeSeparationMultiplier", sep);
			if (view.getViewStyle() == View3D.RED_GREEN_STEREO_VIEW || view.getViewStyle() == View3D.STEREOGRAPH_VIEW
					|| view.getViewStyle() == View3D.CROSS_EYE_STEREO_VIEW)
				view.forceRedraw();
		}
		if (defaultAnaglyphButton.isSelected())
			Prefs.put("view3d.initialAnaglyphMode","default");
		else if (alwaysAnaglyphButton.isSelected()) {
			if (view.getViewStyle() == View3D.MONOCULAR_VIEW)
				view.setViewStyle(View3D.RED_GREEN_STEREO_VIEW);
			Prefs.put("view3d.initialAnaglyphMode","always");
		}
		else if (neverAnaglyphButton.isSelected()) {
			if (view.getViewStyle() == View3D.RED_GREEN_STEREO_VIEW)
				view.setViewStyle(View3D.MONOCULAR_VIEW);
			Prefs.put("view3d.initialAnaglyphMode","never");
		}
		if (moveObjectsForwardInAnaglyphToggle.isSelected()) {
			if (view.getViewStyle() == View3D.RED_GREEN_STEREO_VIEW && view.getTransform3D() != null) {
				double extent = Math.max(view.getTransform3D().getXmaxRequested() - view.getTransform3D().getXminRequested(),
						view.getTransform3D().getYmaxRequested() - view.getTransform3D().getYminRequested());
				view.getTransform3D().setObjectDisplacementNormalToScreen(extent/3);
			}
			Prefs.put("view3d.moveObjectsForwardInAnaglyph","yes");
		}
		else {
			if (view.getViewStyle() == View3D.RED_GREEN_STEREO_VIEW && view.getTransform3D() != null)
				view.getTransform3D().setObjectDisplacementNormalToScreen(0);
			Prefs.put("view3d.moveObjectsForwardInAnaglyph","no");
		}
		return true;
	}
	
	protected void doDefaults() {
		input.defaultVal();
		defaultAnaglyphButton.setSelected(true);
		moveObjectsForwardInAnaglyphToggle.setSelected(false);
	}
	
	
}
