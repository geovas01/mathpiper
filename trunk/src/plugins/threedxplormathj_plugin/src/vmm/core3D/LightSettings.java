/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

import java.awt.Color;

/**
 *This class encapsulates the data associated with a lighting
 *environment that will be used to implement Phong Lighting to 
 *illuminate a surface, using either Flat Shading (each patch
 *has a constant color) or Phong Shading (each pixel gets its
 *own color). There is a point source light0 located at ViewPoint
 * and three directional lights. Also provided is an ambient light
 *color, a Specular Ratio, and a Specular Exponent.
 */
public class LightSettings {
	
	/**
	 * To be used as a parameter to the one-parameter constructor, representing default light settings.
	 */
	public final static int DEFAULTS = 0;
	
	/**
	 * To be used as a parameter to the one-parameter constructor, representing settings 
	 * appropriate for "distinctly colored sides".
	 */
	public final static int DISTICTLY_COLORED_SIDES_DEFAULT = 1;  // DISTINCTLY ?? HK
	
	/**
	 * To be used as a parameter to the one-parameter constructor, representing a light setting in 
	 * which all lights are white.
	 */
	public final static int WHITE_LIGHT = 2;

	/**
	 * To be used as a parameter to the one-parameter constructor, representing light settings 
	 * which are the same as the default settings, except for the value of specular exponent 
	 * and specular ratio and a small increase in ambient light.
	 */
	public final static int HIGH_SPECULARITY_DEFAULT = 3;
	
	
	private Color light0;        //this is the color (usually white) of a point source at ViewPoint
	private DirectionalLight directionalLight1, directionalLight2, directionalLight3;
    private Color ambientLight;   //color of non-directional light illuminating surface
    private float specularRatio;  //fraction of light that is reflected specularly from the surface.
    private int specularExponent; //used in Phong formula for fall off of specular light with angle.
 /**
* This constructor creates a new LightSettings from a list of RGB   
*components of the light colors and direction cosines for the
*directions the lights are coming from, and finally the
*specular exponent and ratio
*/   
	public LightSettings(
		    float R0, float G0, float B0,       //RGB of light0
		    float R1, float G1, float B1,       //RGB of directionalLight1
		    float R2, float G2, float B2,       //RGB of directionalLight2
		    float R3, float G3, float B3,       //RGB of directionalLight3
		    float RA, float GA, float BA,       //RGB of ambientLight
		    double x1, double y1, double z1,    //direction cosines of directionalLight1 
		    double x2, double y2, double z2,    //direction cosines of directionalLight2 
		    double x3, double y3, double z3,    //direction cosines of directionalLight3  
		    float specRatio,
		    int specExponent ) {
		light0 = new Color(R0,G0,B0);
		directionalLight1 = new DirectionalLight(R1,G1,B1,x1,y1,z1);
		directionalLight2 = new DirectionalLight(R2,G2,B2,x2,y2,z2);
		directionalLight3 = new DirectionalLight(R3,G3,B3,x3,y3,z3);
		ambientLight = new Color(RA,GA,BA);
		specularRatio = specRatio;
		specularExponent = specExponent;
	}
	
	/**
	 * This constructor creates a default LightSettings object that agrees with the
	 * one used by 3D-XplorMath.
	 */
	public LightSettings(){
	    this(0.25f,0.25f,0.25f,        //RGB of light0
		     1.0f,0.0f,0.0f,          //RGB of directionalLight1
		     0.0f,1.0f,0.0f,          //RGB of directionalLight2
			 0.0f,0.0f,1.0f,          //RGB of directionalLight3
			 0.1f,0.1f,0.1f,          //RGB of ambientLight
			 -0.7071, 0.0, -0.7071,   //direction cosines of directionalLight1 
		     -0.5774,-0.5774,-0.5774, //direction cosines of directionalLight2 
		      0.0, -0.7071, -0.7071,  //direction cosines of directionalLight3  
		      0.3f,                   // fraction of light that is specular
			  25                      //specular exponent
				 );
	}
	
	/**
	 * Create one of the default light settings.  The parameter must be one of the
	 * codes defined in this class, such as LightSettings.DEFAULTS and
	 * LightSettings.VIEWPORT_LIGHT_ONLY.  (Other values are accepted, but
	 * are treated the same as LightSettings.DEFAULT.  Note the LightSettings.DEFAULTS
	 * produces the same effect as the parameterless constructor.
	 */
	public LightSettings(int code) {
		this();
		switch (code) {
		case HIGH_SPECULARITY_DEFAULT:
			specularExponent = 150;
			specularRatio = 0.85F;
			ambientLight = new Color(0.22F,0.22F,0.22F);
			break;
		case DISTICTLY_COLORED_SIDES_DEFAULT:
			light0 = new Color(0.3F, 0.3F, 0.3F);
			directionalLight1.setItsColor(new Color(1, 1, 0.25F));
			directionalLight2.setItsColor(new Color(0.5F, 0.5F, 0.5F));
			directionalLight3.setItsColor(new Color(0.25F, 0.25F, 1));
			directionalLight3.setItsDirection(new Vector3D(-1,0,-1).normalized());
			break;
		case WHITE_LIGHT:
			light0 = Color.WHITE;
			directionalLight1.setItsColor(Color.BLACK);
			directionalLight2.setItsColor(Color.BLACK);
			directionalLight3.setItsColor(Color.BLACK);
			ambientLight = new Color( 0.3F, 0.3F, 0.3F);
			specularRatio = 0;
			break;
		}
	}

	public Color getAmbientLight() {
		return ambientLight;
	}

	public void setAmbientLight(Color ambientLight) {
		this.ambientLight = ambientLight;
	}

	public DirectionalLight getDirectionalLight1() {
		return directionalLight1;
	}

	public void setDirectionalLight1(DirectionalLight directionalLight1) {
		this.directionalLight1 = directionalLight1;
	}

	public DirectionalLight getDirectionalLight2() {
		return directionalLight2;
	}

	public void setDirectionalLight2(DirectionalLight directionalLight2) {
		this.directionalLight2 = directionalLight2;
	}

	public DirectionalLight getDirectionalLight3() {
		return directionalLight3;
	}

	public void setDirectionalLight3(DirectionalLight directionalLight3) {
		this.directionalLight3 = directionalLight3;
	}

	public Color getLight0() {
		return light0;
	}

	public void setLight0(Color light0) {
		this.light0 = light0;
	}

	public int getSpecularExponent() {
		return specularExponent;
	}

	public void setSpecularExponent(int specularExponent) {
		this.specularExponent = specularExponent;
	}

	public float getSpecularRatio() {
		return specularRatio;
	}

	public void setSpecularRatio(float specularRatio) {
		this.specularRatio = specularRatio;
	}

	
}

