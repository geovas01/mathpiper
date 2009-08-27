/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SetNumberOfFramesDialog extends SettingsDialog {
	
	private Exhibit exhibit;
	
	private IntegerParam numberOfFramesForMorphing;
	private JCheckBox useFilmstripForMorphing;
	
	protected ParameterInput numberOfFramesForMorphingInput;
	
	public static void showDialog(View view) {
		(new SetNumberOfFramesDialog(view)).setVisible(true);
	}
	
	/**
	 * Construct a SetViewpointDialog for setting the viewpoint and up direction
	 * of a specified View3D.
	 * @param view The non-null view whose data will be changed.
	 */
	private SetNumberOfFramesDialog(View view) {
		super(view.getDisplay(),I18n.tr("vmm.core.dialogtitle.SetNumberOfFrames"),false,false);
		this.exhibit = view.getExhibit();
		numberOfFramesForMorphing = new IntegerParam(I18n.tr("vmm.core.SetNumberOfFramesDialog.FramesForMorphing", "x"), exhibit.getFramesForMorphing());
		numberOfFramesForMorphingInput = new ParameterInput(numberOfFramesForMorphing);
		numberOfFramesForMorphingInput.setColumns(4);
		numberOfFramesForMorphing.setMinimumValueForInput(2);
		numberOfFramesForMorphing.setMaximumValueForInput(1000);
		useFilmstripForMorphing = new JCheckBox(I18n.tr("vmm.core.SetNumberOfFramesDialog.UseFilmstrip"));
		useFilmstripForMorphing.setSelected(exhibit.getUseFilmstripForMorphing());
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,1,8,8));
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout(8,8));
		top.add(numberOfFramesForMorphingInput,BorderLayout.CENTER);
		top.add(new JLabel(numberOfFramesForMorphing.getName() + ": "), BorderLayout.WEST);
		panel.add(top);
		panel.add(useFilmstripForMorphing);
		int frames = Filmstrip.maxFrames(view.getDisplay().getWidth(), view.getDisplay().getHeight(),true);
		if (frames > 0)
			frames--;
		panel.add(new JLabel(I18n.tr("vmm.core.SetNumberOfFramesDialog.FramesAvailable",""+frames),JLabel.RIGHT));
		addInputPanel(panel);
	}
	
	protected boolean doOK() {
		String error = numberOfFramesForMorphingInput.checkContents();
		if (error != null) {
			JOptionPane.showMessageDialog(this,error,I18n.tr("vmm.core.SettingsDialog.errorTitle"),JOptionPane.ERROR_MESSAGE);
			return false;
		}
		numberOfFramesForMorphingInput.setValueFromContents();
		exhibit.setFramesForMorphing(numberOfFramesForMorphing.getValue());
		exhibit.setUseFilmstripForMorphing(useFilmstripForMorphing.isSelected());
		return true;
	}
	
	
	
}
