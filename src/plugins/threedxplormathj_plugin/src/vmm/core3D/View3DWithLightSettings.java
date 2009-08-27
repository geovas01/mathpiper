/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import vmm.actions.AbstractActionVMM;
import vmm.actions.ActionList;
import vmm.actions.ToggleAction;
import vmm.core.I18n;
import vmm.core.SaveAndRestore;
import vmm.core.Util;
import vmm.core.VMMSave;
import vmm.core.View;

/**
 * A 3D view, with associated light settings.  This class is the base class for {@link View3DLit}, which adds
 * a varitey of other lighting controls.  A View3DWithLightSettings was introduced for use with
 * implicit surfaces, where many of the lighting controls in View3DLit are not appropriate.
 */
public class View3DWithLightSettings extends View3D {

	@VMMSave
	private boolean lightingEnabled = true;
	
	/**
	 * The lightSettings that are used in analglyph mode.  By default, the same
	 * light settings are used for both analyglyph and non-anaglyph modes.  However,
	 * this can be changed by calling {@link #setAnaglyphLightSettings(LightSettings)} or
	 * {@link #setNonAnaglyphLightSettings(LightSettings)}.
	 */
	private LightSettings anaglyphLightSettings;
	
	/**
	 * The light settings that are used in non-analyglyph mode.
	 */
	private LightSettings nonAnaglyphLightSettings;
	
	/**
	 * Command for displaying a light settings dialog.
	 */
	protected AbstractActionVMM lightSettingsCommand = new AbstractActionVMM(I18n.tr("vmm.core3D.commands.LightSettings")) {
		public void actionPerformed(ActionEvent evt) {
			LightSettingsDialog.showDialog(View3DWithLightSettings.this);
		}
	};
	
	/**
	 * ToggleAction for enabling/disabling lighting.  This ToggleAction is not
	 * added to the settings commands in this class, but is added in {@link View3DLit#getSettingsCommands()}.
	 */
	protected ToggleAction lightingEnabledToggle = new ToggleAction(I18n.tr("vmm.core3D.commands.EnableLighting"), true) {
		public void actionPerformed(ActionEvent evt) {
			setLightingEnabled(getState());
		}
	};


	/**
	 * Create the view, with a default LightSettings object.
	 */
	public View3DWithLightSettings() {
		anaglyphLightSettings = nonAnaglyphLightSettings = new LightSettings();
	}
	
	/**
	 * Retrieve the current {@link LightSettings} for this view.  The return value can
	 * depend on whether the current view style is anaglyph.  By default, the
	 * same light settings object is used for both; this can only change if either
	 * {@link #setAnaglyphLightSettings(LightSettings)} or {@link #setNonAnaglyphLightSettings(LightSettings)}
	 * is called in a subclass.
	 */
	public LightSettings getLightSettings() {
		return getViewStyle() == RED_GREEN_STEREO_VIEW ? anaglyphLightSettings : nonAnaglyphLightSettings;
	}

	/**
	 * Sets the current light settings for this view.  Note that calling this method does <b>not</b>
	 * automatically force a redraw; it simply replaces the current light settings in the view.
	 * If {@link #setAnaglyphLightSettings(LightSettings)} or {@link #setNonAnaglyphLightSettings(LightSettings)}
	 * has been called, then this only affects the light settings for the current view style;
	 * otherwise, the new light settings replaces the light settings that are used for all view styles.
	 * @param lightSettings the new light settings; if this is null, the current light settings are not changed.
	 */
	public void setLightSettings(LightSettings lightSettings) {
		if (lightSettings != null) {
			if (anaglyphLightSettings == nonAnaglyphLightSettings)
				anaglyphLightSettings = nonAnaglyphLightSettings = lightSettings;
			else if (getViewStyle() == RED_GREEN_STEREO_VIEW)
				anaglyphLightSettings = lightSettings;
			else
				nonAnaglyphLightSettings = lightSettings;
		}
	}
	
	/**
	 * Set the light setting object that is used for anaglyph view style.  After this method is
	 * called, separate objects will be used for anaglyph and non-anaglyph view styles.
	 * @param lightSettings the new lidht settings object; if this is null, nothing is done.
	 */
	protected void setAnaglyphLightSettings(LightSettings lightSettings) {
		if (lightSettings != null)
			this.anaglyphLightSettings = lightSettings;
	}
		
	/**
	 * Set the light setting object that is used for non-anaglyph view styles.  After this method is
	 * called, separate objects will be used for anaglyph and non-anaglyph view styles.
	 * @param lightSettings the new lidht settings object; if this is null, nothing is done.
	 */
	protected void setNonAnaglyphLightSettings(LightSettings lightSettings) {
		if (lightSettings != null)
			this.nonAnaglyphLightSettings = lightSettings;
	}
		
	/**
	 * Check whether lighting is currently enabled.
	 * @see #setLightingEnabled(boolean)
	 */
	public boolean getLightingEnabled() {
		return lightingEnabled;
	}

	/**
	 * Set whether lighting is currently enabled.  When lighting is enabled, surface colors are
	 * computed based on intrinsic color plus illumination.  When it is disabled, surface patches are
	 * drawn using intrinsic color only (leading to a completely flat appearance).  The default value
	 * is true.
	 * @param lightingEnabled
	 */
	public void setLightingEnabled(boolean lightingEnabled) {
	    if (this.lightingEnabled == lightingEnabled)
	    		return;
		this.lightingEnabled = lightingEnabled;
		lightingEnabledToggle.setState(lightingEnabled);
		forceRedraw();
	}
	
	/**
	 * Overridden in this subclass to swap in the anaglyph or non-anaglph light settings, as appropriate.
	 * See {@link #setAnaglyphLightSettings(LightSettings)} and {@link #setNonAnaglyphLightSettings(LightSettings)}.
	 */
	public void setViewStyle(int viewStyle) {
		super.setViewStyle(viewStyle);
		setLightSettings( viewStyle == View3D.RED_GREEN_STEREO_VIEW ? anaglyphLightSettings : nonAnaglyphLightSettings);
	}

	/**
	 * Adds a checkbox for setting the lighting and dragAsSurface commands to any settings contributed by the superclass.
	 * @see View#getSettingsCommands()
	 */
	public ActionList getSettingsCommands() {
		ActionList commands = super.getSettingsCommands();
		lightSettingsCommand.setEnabled(getEnableThreeD());
		commands.add(null);
		commands.add(lightingEnabledToggle);
		commands.add(lightSettingsCommand);
		return commands;
	}

	//----------------------- XML routines -------------------------------------------
	
	/**
	 * Overridden to add light settings info.
	 */
	public void addExtraXML(Document containingDocument, Element viewElement) {
		super.addExtraXML(containingDocument, viewElement);
		Element lightSettingsElement = containingDocument.createElement("light-settings");
		addLightToXML(containingDocument,lightSettingsElement,nonAnaglyphLightSettings);
		viewElement.appendChild(lightSettingsElement);
		if (anaglyphLightSettings != nonAnaglyphLightSettings) {
			lightSettingsElement = containingDocument.createElement("light-settings-anaglyph");
			addLightToXML(containingDocument,lightSettingsElement,anaglyphLightSettings);
			viewElement.appendChild(lightSettingsElement);
		}
	}
	
	private void addLightToXML(Document containingDocument, Element lightSettingsElement, LightSettings lightSettings) {
		SaveAndRestore.addProperty(lightSettings,"specularRatio",containingDocument,lightSettingsElement);
		SaveAndRestore.addProperty(lightSettings,"specularExponent",containingDocument,lightSettingsElement);
		SaveAndRestore.addProperty(lightSettings,"light0",containingDocument,lightSettingsElement);
		SaveAndRestore.addProperty(lightSettings,"ambientLight",containingDocument,lightSettingsElement);
		Element light;
		if (lightSettings.getDirectionalLight1() != null) {
			light = containingDocument.createElement("light1");
			light.setAttribute("color", Util.toExternalString(lightSettings.getDirectionalLight1().getItsColor()));
			light.setAttribute("direction", Util.toExternalString(lightSettings.getDirectionalLight1().getItsDirection()));
			lightSettingsElement.appendChild(light);
		}
		if (lightSettings.getDirectionalLight2() != null) {
			light = containingDocument.createElement("light2");
			light.setAttribute("color", Util.toExternalString(lightSettings.getDirectionalLight2().getItsColor()));
			light.setAttribute("direction", Util.toExternalString(lightSettings.getDirectionalLight2().getItsDirection()));
			lightSettingsElement.appendChild(light);
		}
		if (lightSettings.getDirectionalLight3() != null) {
			light = containingDocument.createElement("light3");
			light.setAttribute("color", Util.toExternalString(lightSettings.getDirectionalLight3().getItsColor()));
			light.setAttribute("direction", Util.toExternalString(lightSettings.getDirectionalLight3().getItsDirection()));
			lightSettingsElement.appendChild(light);
		}
	}

	/**
	 * Overridden to read back the light settings info.
	 */
	public void readExtraXML(Element viewInfo) throws IOException {
		super.readExtraXML(viewInfo);
		Element lightSettingsElement = SaveAndRestore.getChildElement(viewInfo,"light-settings");
		if (lightSettingsElement == null)
			return;
		LightSettings lightSettings = readLightFromXML(lightSettingsElement);
		nonAnaglyphLightSettings = anaglyphLightSettings = lightSettings;
		lightSettingsElement = SaveAndRestore.getChildElement(viewInfo,"light-settings-anaglyph");
		if (lightSettingsElement != null) {
			lightSettings = readLightFromXML(lightSettingsElement);
			anaglyphLightSettings = lightSettings;
		}
	}
	
	private LightSettings readLightFromXML(Element lightSettingsElement) throws IOException {
		LightSettings lightSettings = new LightSettings();
		SaveAndRestore.readProperties(lightSettings,lightSettingsElement);
		Element lightElement;
		Color color;
		Vector3D direction;
		lightElement = SaveAndRestore.getChildElement(lightSettingsElement, "light1");
		if (lightElement != null) {
			try {
				color = (Color)Util.externalStringToValue(lightElement.getAttribute("color"), Color.class);
			}
			catch (Exception e) {
				throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", lightElement.getAttribute("color"), "color"));
			}
			try {
				direction = (Vector3D)Util.externalStringToValue(lightElement.getAttribute("direction"), Vector3D.class);
				direction.normalize();
			}
			catch (Exception e) {
				throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", lightElement.getAttribute("direction"), "direction"));
			}
			lightSettings.setDirectionalLight1(new DirectionalLight(color,direction));
		}
		lightElement = SaveAndRestore.getChildElement(lightSettingsElement, "light2");
		if (lightElement != null) {
			try {
				color = (Color)Util.externalStringToValue(lightElement.getAttribute("color"), Color.class);
			}
			catch (Exception e) {
				throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", lightElement.getAttribute("color"), "color"));
			}
			try {
				direction = (Vector3D)Util.externalStringToValue(lightElement.getAttribute("direction"), Vector3D.class);
				direction.normalize();
			}
			catch (Exception e) {
				throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", lightElement.getAttribute("direction"), "direction"));
			}
			lightSettings.setDirectionalLight2(new DirectionalLight(color,direction));
		}
		lightElement = SaveAndRestore.getChildElement(lightSettingsElement, "light3");
		if (lightElement != null) {
			try {
				color = (Color)Util.externalStringToValue(lightElement.getAttribute("color"), Color.class);
			}
			catch (Exception e) {
				throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", lightElement.getAttribute("color"), "color"));
			}
			try {
				direction = (Vector3D)Util.externalStringToValue(lightElement.getAttribute("direction"), Vector3D.class);
				direction.normalize();
			}
			catch (Exception e) {
				throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", lightElement.getAttribute("direction"), "direction"));
			}
			lightSettings.setDirectionalLight3(new DirectionalLight(color,direction));
		}
		return lightSettings;
	}
	

}
