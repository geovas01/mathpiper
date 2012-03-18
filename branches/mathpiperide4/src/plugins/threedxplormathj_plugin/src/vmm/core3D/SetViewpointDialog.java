/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import vmm.core.I18n;
import vmm.core.ParameterInput;
import vmm.core.RealParam;
import vmm.core.SettingsDialog;

/**
 * A dialog for setting the viewpoint, up direction, and clip distance.
 */
public class SetViewpointDialog extends SettingsDialog {
	
	private View3D view;
	
	private RealParam viewX, viewY, viewZ;
	private RealParam upX, upY, upZ;
	private RealParam clipdist;
	
	private ParameterInput[] inputs;
	
	private JRadioButton useDefaultClip, useCustomClip;
	
	public static void showDialog(View3D view) {
		(new SetViewpointDialog(view)).setVisible(true);
	}
	
	/**
	 * Construct a SetViewpointDialog for setting the viewpoint and up direction
	 * of a specified View3D.
	 * @param view The non-null view whose data will be changed.
	 */
	private SetViewpointDialog(View3D view) {
		super(view.getDisplay(),I18n.tr("vmm.core3D.commands.SetViewpoint"));
		this.view = view;
		Vector3D viewpoint = view.getViewPoint();
		Vector3D viewup = view.getViewUp();
		Vector3D defaultViewpoint = viewpoint;
		Vector3D defaultViewup = viewup;
		if (view.getExhibit() != null && view.getExhibit() instanceof Exhibit3D) {
			defaultViewpoint = ((Exhibit3D)view.getExhibit()).getDefaultViewpoint();
			defaultViewup = Vector3D.UNIT_Z;
		}
		viewX = new RealParam(I18n.tr("vmm.core3D.SetViewpointDialog.ViewpointComponent", "x"), viewpoint.x);
		viewX.setDefaultValue(defaultViewpoint.x);
		viewY = new RealParam(I18n.tr("vmm.core3D.SetViewpointDialog.ViewpointComponent", "y"), viewpoint.y);
		viewY.setDefaultValue(defaultViewpoint.y);
		viewZ = new RealParam(I18n.tr("vmm.core3D.SetViewpointDialog.ViewpointComponent", "z"), viewpoint.z);
		viewZ.setDefaultValue(defaultViewpoint.z);
		upX = new RealParam(I18n.tr("vmm.core3D.SetViewpointDialog.UpDirectionComponent", "x"), viewup.x);
		upX.setDefaultValue(defaultViewup.x);
		upY = new RealParam(I18n.tr("vmm.core3D.SetViewpointDialog.UpDirectionComponent", "y"), viewup.y);
		upY.setDefaultValue(defaultViewup.y);
		upZ = new RealParam(I18n.tr("vmm.core3D.SetViewpointDialog.UpDirectionComponent", "z"), viewup.z);
		upZ.setDefaultValue(defaultViewup.z);
		clipdist = new RealParam(I18n.tr("vmm.core3D.SetViewpointDialog.ClipDistance"),0);
		clipdist.setMinimumValueForInput(Double.MIN_VALUE);
		inputs = new ParameterInput[7];
		inputs[0] = new ParameterInput(viewX);
		inputs[1] = new ParameterInput(viewY);
		inputs[2] = new ParameterInput(viewZ);
		inputs[3] = new ParameterInput(upX);
		inputs[4] = new ParameterInput(upY);
		inputs[5] = new ParameterInput(upZ);
		inputs[6] = new ParameterInput(clipdist);
		inputs[6].setEditable(false);
		inputs[6].setEnabled(false);
		for (int i = 0; i < 7; i++)
			inputs[i].setColumns(7);
		Box panel = Box.createVerticalBox();
		panel.add(Box.createVerticalStrut(8));
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JPanel bottom = new JPanel();
		bottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panel.add(top);
		panel.add(Box.createVerticalStrut(10));
		panel.add(bottom);
		top.add(new JLabel(I18n.tr("vmm.core3D.SetViewpointDialog.ViewFrom")));
		top.add(new JLabel(" ( "));
		top.add(inputs[0]);
		top.add(new JLabel(" , "));
		top.add(inputs[1]);
		top.add(new JLabel(" , "));
		top.add(inputs[2]);
		top.add(new JLabel(" ) "));
		bottom.add(new JLabel(I18n.tr("vmm.core3D.SetViewpointDialog.UpDirection")));
		bottom.add(new JLabel(" ( "));
		bottom.add(inputs[3]);
		bottom.add(new JLabel(" , "));
		bottom.add(inputs[4]);
		bottom.add(new JLabel(" , "));
		bottom.add(inputs[5]);
		bottom.add(new JLabel(" ) "));
		panel.add(Box.createVerticalStrut(20));
		JLabel clipLabel = new JLabel(I18n.tr("vmm.core3D.SetViewpointDialog.ClipDistance") + ":");
		panel.add(clipLabel);
		ButtonGroup group = new ButtonGroup();
		useDefaultClip = new JRadioButton(I18n.tr("vmm.core3D.SetViewpointDialog.UseDefaultClip"));
		useCustomClip = new JRadioButton(I18n.tr("vmm.core3D.SetViewpointDialog.UseCustomClip") + ":");
		group.add(useDefaultClip);
		group.add(useCustomClip);
		useDefaultClip.setSelected(true);
		useCustomClip.addItemListener( new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				boolean custom = useCustomClip.isSelected();
				inputs[6].setEditable(custom);
				inputs[6].setEnabled(custom);
				if (custom) {
					inputs[6].selectAll();
					inputs[6].requestFocus();
				}
			}
		});
		JPanel def = new JPanel();
		def.setLayout(new FlowLayout(FlowLayout.LEFT));
		def.add(Box.createHorizontalStrut(30));
		def.add(useDefaultClip);
		panel.add(def);
		JPanel cus = new JPanel();
		cus.setLayout(new FlowLayout(FlowLayout.LEFT));
		cus.add(Box.createHorizontalStrut(30));
		cus.add(useCustomClip);
		cus.add(Box.createHorizontalStrut(15));
		cus.add(inputs[6]);
		panel.add(cus);
		top.setAlignmentX(0);
		bottom.setAlignmentX(0);
		clipLabel.setAlignmentX(0);
		def.setAlignmentX(0);
		cus.setAlignmentX(0);
		addInputPanel(panel);
	}
	
	protected boolean doOK() {
		for (int i = 0; i < 6; i++) {
			String error = inputs[i].checkContents();
			if (error != null) {
				JOptionPane.showMessageDialog(this,error,I18n.tr("vmm.core.SettingsDialog.errorTitle"),JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		if (useCustomClip.isSelected()) {
			String error = inputs[6].checkContents();
			if (error != null) {
				JOptionPane.showMessageDialog(this,error,I18n.tr("vmm.core.SettingsDialog.errorTitle"),JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		for (int i = 0; i < 6; i++)
			inputs[i].setValueFromContents();
		Vector3D newView = new Vector3D(viewX.getValue(),viewY.getValue(),viewZ.getValue());
		if (newView.norm() < 1e-10) {
			JOptionPane.showMessageDialog(this,I18n.tr("vmm.core3D.SetViewpointDialog.BadViewpointError"),I18n.tr("vmm.core.SettingsDialog.errorTitle"),JOptionPane.ERROR_MESSAGE);
			return false;
		}
		view.setViewPoint(newView);
		view.setViewUp(new Vector3D(upX.getValue(),upY.getValue(),upZ.getValue()));
		if (useCustomClip.isSelected() && view.getTransform3D() != null) {
			inputs[6].setValueFromContents();
			view.getTransform3D().setClipDistance(clipdist.getValue());
		}
		return true;
	}
	
	protected void doDefaults() {
		for (int i = 0; i < 6; i++)
			inputs[i].defaultVal();
		useDefaultClip.setSelected(true);
	}
	
	
}
