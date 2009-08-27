/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

import java.awt.BorderLayout;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import vmm.core.I18n;
import vmm.core.SettingsDialog;

/**
 * A dialog for setting a transparency level, which is
 * constrained to be in the range 0 and 1.  A JSlider is used for input.
 */
public class SetTransparencyDialog extends SettingsDialog {
	
	private View3DLit view;
	
	private JSlider slider;
	
	private int originalPosition;
	
	public static void showDialog(View3DLit view) {
		(new SetTransparencyDialog(view)).setVisible(true);
	}
	
	/**
	 * Construct a dialog for setting the transparency level for a View3DLit.
	 * @param view A view whose display will be the parent of the dialog.  This view is also
	 *    redrawn (if it is showing a stereo view) to show the new transparency.
	 */
	private SetTransparencyDialog(View3DLit view) {
		super(view.getDisplay(),I18n.tr("vmm.core3D.commands.setTransparency"));
		this.view = view;
		double originalVal = view.getTransparency();
		originalPosition = (int)(255*originalVal);
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout());
		inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		slider = new JSlider(0,255,originalPosition);
		Hashtable<Integer, JComponent> labels = new Hashtable<Integer, JComponent>();
		labels.put(0, new JLabel(I18n.tr("vmm.core3D.SetTransparencyDialog.Opaque")));
		labels.put(255, new JLabel(I18n.tr("vmm.core3D.SetTransparencyDialog.Transparent")));
		slider.setLabelTable(labels);
		slider.setPaintLabels(true);
		inputPanel.add(slider,BorderLayout.CENTER);
		addInfoLabel(I18n.tr("vmm.core3D.SetTransparencyDialog.info"));
		addInputPanel(inputPanel);
	}
	
	protected boolean doOK() {
		int position = slider.getValue();
		if (position != originalPosition) {
			double value = position <= 10 ? 0 : position/255.0;
			view.setTransparency(value);
			originalPosition = position;
		}
		return true;
	}
	
	protected void doDefaults() {
		slider.setValue(0);
	}
	
	
}
