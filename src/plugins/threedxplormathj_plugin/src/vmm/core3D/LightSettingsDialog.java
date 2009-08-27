/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import vmm.core.I18n;
import vmm.core.IntegerParam;
import vmm.core.ParameterInput;
import vmm.core.RealParam;
import vmm.core.SettingsDialog;

public class LightSettingsDialog extends SettingsDialog {
	
	private RealParam[][] colorParams = new RealParam[5][3];
	private RealParam[][] directionParams = new RealParam[3][3];
	private RealParam specularRatioParam;
	private IntegerParam specularExponentParam;
	
	private ParameterInput[][] colorInputs = new ParameterInput[5][3];
	private ParameterInput[][] directionInputs = new ParameterInput[3][3];
	private ParameterInput specularRatioInput;
	private ParameterInput specularExponentInput;
	private JButton[] colorSetButton = new JButton[5];
	
	private JButton[] defaultSettingsButton = new JButton[4];
	
	private LightSettings currentSettings;
	
	private View3DWithLightSettings owner;
	
	private ActionListener colorSetter = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			Object src = evt.getSource();
			for (int i = 0; i < 5; i++)
				if (src == colorSetButton[i]) {
					Color c = Color.white;
					try {
						c = new Color((float)colorParams[i][0].getValue(),(float)colorParams[i][1].getValue(),(float)colorParams[i][2].getValue());
					}
					catch (Exception e) {
					}
					c = JColorChooser.showDialog(LightSettingsDialog.this,I18n.tr("vmm.core3D.LightSettingsDialog.ColorDialogTitle"),c);
					if (c == null)
						return;
					double r = (double)c.getRed() / 255;
					double g = (double)c.getGreen() / 255;
					double b = (double)c.getBlue() / 255;  
					r = ( (int)(r*100000 + 0.499) ) / 100000.0;
					g = ( (int)(g*100000 + 0.499) ) / 100000.0;
					b = ( (int)(b*100000 + 0.499) ) / 100000.0;
					colorInputs[i][0].setText("" + r);
					colorInputs[i][1].setText("" + g);
					colorInputs[i][2].setText("" + b);
					return;
				}
		}
	};
	
	private ActionListener defaultsListener = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			Object src = evt.getSource();
			for (int i = 0; i < 4; i++) {
				if (src == defaultSettingsButton[i]) {
					LightSettings newSettings;
					switch (i) {
					case 1:
						newSettings = new LightSettings(LightSettings.WHITE_LIGHT);
						break;
					case 2:
						newSettings = new LightSettings(LightSettings.DISTICTLY_COLORED_SIDES_DEFAULT);
						break;
					case 3:
						newSettings = new LightSettings(LightSettings.HIGH_SPECULARITY_DEFAULT);
						break;
					default: // case 0
						newSettings = new LightSettings();
						break;
					}
					colorInputs[0][0].setText("" + ((int)(newSettings.getLight0().getRed()/255.0*10000 + 0.5))/10000.0);
					colorInputs[0][1].setText("" + ((int)(newSettings.getLight0().getGreen()/255.0*10000 + 0.5))/10000.0);
					colorInputs[0][2].setText("" + ((int)(newSettings.getLight0().getBlue()/255.0*10000 + 0.5))/10000.0);
					colorInputs[1][0].setText("" + ((int)(newSettings.getDirectionalLight1().getItsColor().getRed()/255.0*10000 + 0.5))/10000.0);
					colorInputs[1][1].setText("" + ((int)(newSettings.getDirectionalLight1().getItsColor().getGreen()/255.0*10000 + 0.5))/10000.0);
					colorInputs[1][2].setText("" + ((int)(newSettings.getDirectionalLight1().getItsColor().getBlue()/255.0*10000 + 0.5))/10000.0);
					colorInputs[2][0].setText("" + ((int)(newSettings.getDirectionalLight2().getItsColor().getRed()/255.0*10000 + 0.5))/10000.0);
					colorInputs[2][1].setText("" + ((int)(newSettings.getDirectionalLight2().getItsColor().getGreen()/255.0*10000 + 0.5))/10000.0);
					colorInputs[2][2].setText("" + ((int)(newSettings.getDirectionalLight2().getItsColor().getBlue()/255.0*10000 + 0.5))/10000.0);
					colorInputs[3][0].setText("" + ((int)(newSettings.getDirectionalLight3().getItsColor().getRed()/255.0*10000 + 0.5))/10000.0);
					colorInputs[3][1].setText("" + ((int)(newSettings.getDirectionalLight3().getItsColor().getGreen()/255.0*10000 + 0.5))/10000.0);
					colorInputs[3][2].setText("" + ((int)(newSettings.getDirectionalLight3().getItsColor().getBlue()/255.0*10000 + 0.5))/10000.0);					
					colorInputs[4][0].setText("" + ((int)(newSettings.getAmbientLight().getRed()/255.0*10000 + 0.5))/10000.0);
					colorInputs[4][1].setText("" + ((int)(newSettings.getAmbientLight().getGreen()/255.0*10000 + 0.5))/10000.0);
					colorInputs[4][2].setText("" + ((int)(newSettings.getAmbientLight().getBlue()/255.0*10000 + 0.5))/10000.0);
					directionInputs[0][0].setText("" + ((int)(newSettings.getDirectionalLight1().getItsDirection().x*10000 + 0.5))/10000.0);
					directionInputs[0][1].setText("" + ((int)(newSettings.getDirectionalLight1().getItsDirection().y*10000 + 0.5))/10000.0);
					directionInputs[0][2].setText("" + ((int)(newSettings.getDirectionalLight1().getItsDirection().z*10000 + 0.5))/10000.0);
					directionInputs[1][0].setText("" + ((int)(newSettings.getDirectionalLight2().getItsDirection().x*10000 + 0.5))/10000.0);
					directionInputs[1][1].setText("" + ((int)(newSettings.getDirectionalLight2().getItsDirection().y*10000 + 0.5))/10000.0);
					directionInputs[1][2].setText("" + ((int)(newSettings.getDirectionalLight2().getItsDirection().z*10000 + 0.5))/10000.0);
					directionInputs[2][0].setText("" + ((int)(newSettings.getDirectionalLight3().getItsDirection().x*10000 + 0.5))/10000.0);
					directionInputs[2][1].setText("" + ((int)(newSettings.getDirectionalLight3().getItsDirection().y*10000 + 0.5))/10000.0);
					directionInputs[2][2].setText("" + ((int)(newSettings.getDirectionalLight3().getItsDirection().z*10000 + 0.5))/10000.0);
					specularRatioInput.setText("" + ((int)(newSettings.getSpecularRatio()*10000 + 0.5))/10000.0);
					specularExponentInput.setText("" + newSettings.getSpecularExponent());
				}
			}
		}
	};
	
	public static void showDialog(View3DWithLightSettings view) {
		(new LightSettingsDialog(view)).setVisible(true);
	}

	
	public LightSettingsDialog(View3DWithLightSettings view) {
		super(view.getDisplay(), I18n.tr("vmm.core3D.LightSettingsDialog.dialogTitle"), false, true);
		owner = view;
		currentSettings = view.getLightSettings();
		LightSettings defaultSettings = new LightSettings();
		Box panel = Box.createVerticalBox();

		JPanel colorPanel = borderedPanel(I18n.tr("vmm.core3D.LightSettingsDialog.LightColors"), 6, 5);
		JPanel directionPanel = borderedPanel(I18n.tr("vmm.core3D.LightSettingsDialog.LightDirections"), 4, 4);
		JPanel specularPanel = borderedPanel(I18n.tr("vmm.core3D.LightSettingsDialog.Specular"), 1, 4);
		JPanel defaultsPanel = borderedPanel(I18n.tr("vmm.core3D.LightSettingsDialog.DefaultsButtons"), 0, 2);
		panel.add(colorPanel);
		panel.add(directionPanel);
		panel.add(specularPanel);
		panel.add(defaultsPanel);
		
		String[] names = new String[] {
				I18n.tr("vmm.core3D.LightSettingsDialog.Source0"),	
				I18n.tr("vmm.core3D.LightSettingsDialog.Source1"),	
				I18n.tr("vmm.core3D.LightSettingsDialog.Source2"),	
				I18n.tr("vmm.core3D.LightSettingsDialog.Source3"),	
				I18n.tr("vmm.core3D.LightSettingsDialog.Ambient")
		};
		String[] colorNames = new String[] {
				I18n.tr("common.Red"),
				I18n.tr("common.Green"),
				I18n.tr("common.Blue")
		};
		Vector3D[] currentDirections = new Vector3D[] {
				currentSettings.getDirectionalLight1().getItsDirection(),
				currentSettings.getDirectionalLight2().getItsDirection(),
				currentSettings.getDirectionalLight3().getItsDirection()
		};
		Vector3D[] defaultDirections = new Vector3D[] {
				defaultSettings.getDirectionalLight1().getItsDirection(),
				defaultSettings.getDirectionalLight2().getItsDirection(),
				defaultSettings.getDirectionalLight3().getItsDirection()
		};

		colorPanel.add(new JLabel());
		colorPanel.add(new JLabel(colorNames[0],JLabel.CENTER));
		colorPanel.add(new JLabel(colorNames[1],JLabel.CENTER));
		colorPanel.add(new JLabel(colorNames[2],JLabel.CENTER));
		colorPanel.add(new JLabel());
		for (int i = 0; i < 5; i++) {
			colorPanel.add(new JLabel(names[i], JLabel.RIGHT));
			for (int j = 0; j < 3; j++) {
				colorParams[i][j] = new RealParam(names[i] + " " + colorNames[j], colorComponent(currentSettings,i,j));
				colorParams[i][j].setDefaultValue((int)(100000*colorComponent(defaultSettings,i,j)+0.4999) / 100000.0);
				colorParams[i][j].setMinimumValueForInput(0);
				colorParams[i][j].setMaximumValueForInput(1);
				colorInputs[i][j] = new ParameterInput(colorParams[i][j]);
				colorInputs[i][j].setColumns(7);
				colorPanel.add(colorInputs[i][j]);
			}
			colorSetButton[i] = new JButton(I18n.tr("vmm.core3D.LightSettingsDialog.SetColorButton"));
			colorSetButton[i].addActionListener(colorSetter);
			colorPanel.add(colorSetButton[i]);
		}
		
		directionPanel.add(new JLabel());
		directionPanel.add(new JLabel("x", JLabel.CENTER));
		directionPanel.add(new JLabel("y", JLabel.CENTER));
		directionPanel.add(new JLabel("z", JLabel.CENTER));
		for (int i = 0; i < 3; i++) {
			directionPanel.add(new JLabel(names[i+1], JLabel.RIGHT));
			directionParams[i][0] = new RealParam(names[i+1] + " x", currentDirections[i].x);
			directionParams[i][0].setDefaultValue((int)(100000*defaultDirections[i].x + 0.4999) / 100000.0);
			directionParams[i][1] = new RealParam(names[i+1] + " y", currentDirections[i].y);
			directionParams[i][1].setDefaultValue((int)(100000*defaultDirections[i].y + 0.4999) / 100000.0);
			directionParams[i][2] = new RealParam(names[i+1] + " z", currentDirections[i].z);
			directionParams[i][2].setDefaultValue((int)(100000*defaultDirections[i].z + 0.4999) / 100000.0);
			for (int j = 0; j < 3; j++) {
				directionInputs[i][j] = new ParameterInput(directionParams[i][j]);
				directionInputs[i][j].setColumns(7);
				directionPanel.add(directionInputs[i][j]);
			}
		}
		
		specularPanel.add(new JLabel(I18n.tr("vmm.core3D.LightSettingsDialog.Ratio"), JLabel.RIGHT));
		specularRatioParam = new RealParam("vmm.core3D.LightSettingsDialog.SpecularRatio", currentSettings.getSpecularRatio());
		specularRatioParam.setDefaultValue((int)(100000*defaultSettings.getSpecularRatio() + 0.4999) / 100000.0);
		specularRatioParam.setMinimumValueForInput(0);
		specularRatioParam.setMaximumValueForInput(1);
		specularRatioInput = new ParameterInput(specularRatioParam);
		specularRatioInput.setColumns(7);
		specularPanel.add(specularRatioInput);
		specularPanel.add(new JLabel(I18n.tr("vmm.core3D.LightSettingsDialog.Exponent"), JLabel.RIGHT));
		specularExponentParam = new IntegerParam("vmm.core3D.LightSettingsDialog.SpecularExponent", currentSettings.getSpecularExponent());
		specularExponentParam.setDefaultValue(defaultSettings.getSpecularExponent());
		specularExponentParam.setMinimumValueForInput(1);
		specularExponentParam.setMaximumValueForInput(250);
		specularExponentInput = new ParameterInput(specularExponentParam);
		specularExponentInput.setColumns(7);
		specularPanel.add(specularExponentInput);
		
		defaultSettingsButton[0] = new JButton(I18n.tr("vmm.core3D.LightSettingsDialog.preset.Defaults"));
		defaultSettingsButton[1] = new JButton(I18n.tr("vmm.core3D.LightSettingsDialog.preset.White"));
		defaultSettingsButton[2] = new JButton(I18n.tr("vmm.core3D.LightSettingsDialog.preset.DistinctlyColoredSidesDefault"));
		defaultSettingsButton[3] = new JButton(I18n.tr("vmm.core3D.LightSettingsDialog.preset.HighSpecularityDefault"));
		for (int i = 0; i < defaultSettingsButton.length; i++) {
			defaultSettingsButton[i].addActionListener(defaultsListener);
			defaultsPanel.add(defaultSettingsButton[i]);
		}
		
		addInputPanel(panel);
	}
	
	private JPanel borderedPanel(String title, int rows, int cols) {
		JPanel panel = borderedPanel(title);
		panel.setLayout(new GridLayout(rows,cols,7,4));
		return panel;
	}

	
	private double colorComponent(LightSettings lights, int colorNum, int componentNum) {
		Color c;
		switch (colorNum) {
			case 0: c = lights.getLight0(); break;
			case 1: c = lights.getDirectionalLight1().getItsColor(); break;
			case 2: c = lights.getDirectionalLight2().getItsColor(); break;
			case 3: c = lights.getDirectionalLight3().getItsColor(); break;
			default: c = lights.getAmbientLight(); break;
		}
		switch (componentNum) {
			case 0: return (double)c.getRed() / 255;
			case 1: return (double)c.getGreen() / 255;
			default: return (double)c.getBlue() / 255;
		}
	}

	protected boolean doOK() {
		Vector3D[] directionVectors = new Vector3D[3];
		Color[] colors = new Color[5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				String error = colorInputs[i][j].checkContents();
				if (error != null) {
					errorMessage(error);
					return false;
				}
				colorInputs[i][j].setValueFromContents();
			}
			colors[i] = new Color((float)colorParams[i][0].getValue(),(float)colorParams[i][1].getValue(),(float)colorParams[i][2].getValue());
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				String error = directionInputs[i][j].checkContents();
				if (error != null) {
					errorMessage(error);
					return false;
				}
				directionInputs[i][j].setValueFromContents();
			}
			directionVectors[i] = new Vector3D(directionParams[i][0].getValue(),directionParams[i][1].getValue(),directionParams[i][2].getValue());
			if (directionVectors[i].norm() < 1e-10) {
				errorMessage(I18n.tr("vmm.core3D.LightSettingsDialog.BadDirectionVector"));
				directionInputs[i][0].requestFocus();
				directionInputs[i][0].selectAll();
				return false;
			}
			directionVectors[i].normalize();
		}
		String error = specularRatioInput.checkContents();
		if (error != null) {
			errorMessage(error);
			return false;
		}
		specularRatioInput.setValueFromContents();
		error = specularExponentInput.checkContents();
		if (error != null) {
			errorMessage(error);
			return false;
		}
		specularExponentInput.setValueFromContents();
		currentSettings.setLight0(colors[0]);
		currentSettings.setDirectionalLight1(new DirectionalLight(colors[1],directionVectors[0]));
		currentSettings.setDirectionalLight2(new DirectionalLight(colors[2],directionVectors[1]));
		currentSettings.setDirectionalLight3(new DirectionalLight(colors[3],directionVectors[2]));
		currentSettings.setAmbientLight(colors[4]);
		currentSettings.setSpecularExponent(specularExponentParam.getValue());
		currentSettings.setSpecularRatio((float)specularRatioParam.getValue());
		owner.setLightingEnabled(true);  // FORCE LIGHTING ON
		owner.forceRedraw();
		return true;
	}
	
	private void errorMessage(String error) {
		JOptionPane.showMessageDialog(this,error,I18n.tr("vmm.core.SettingsDialog.errorTitle"),JOptionPane.ERROR_MESSAGE);
	}
	
}
