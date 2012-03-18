/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

import java.awt.Color;


public class PhongLighting {
		
    final static float removalRatio = 0.6f;

  /* All of the non-directional "ambient light" falling on a surface is assumed to be reflected. 
   * However, for the light from the four sources, only a fraction, theReflectance, of the light 
   * from these sources is reflected, partially diffusely and  partially specularly.
   * For the positive side of the surface theReflectance is 1.0, while for the negative 
   * side it is 0.675. Whether a side is positive or negative depends on the orientation
   * of the surface.
 */
    static float  theReflectance;
    
	private static void getTheReflectance(Vector3D normal, Vector3D theDirection, View3DLit view){
		 final int thePositiveSide = 0;
	     final int theNegativeSide = 1;
		 int theOrientation = view.getOrientation();
	    	 //Vector3D viewDirection = view.getTransform3D().getViewDirection();
	    	 
	    	 float c = (float)normal.dot(theDirection);  
	    	 int theSide =  thePositiveSide; 
	    	 
	     switch (theOrientation) {
	          case View3DLit.NO_ORIENTATION:
	        	           // theSide =  thePositiveSide;
	        	     break;
	          case View3DLit.NORMAL_ORIENTATION:
	             if (c > 0)
				   theSide =  thePositiveSide;
			     else
				    theSide =  theNegativeSide;  
	              break;
	          case View3DLit.REVERSE_ORIENTATION:
	              if (c > 0)
	 			   theSide =  theNegativeSide;
	 		     else
	 			    theSide =  thePositiveSide;  
	               break;
	         }
	       if (theSide == theNegativeSide)
		     theReflectance =  0.675f;
	       else
		     theReflectance = 1.0f; 
	}
	
	/**
	 * This is the Phong-Blinn algorithm for computing the specular component
	 * of the intensity from a source. It should eventually get a better
	 * documentation.
	 */
	private static float specularIntensityOfSource(Vector3D normal, Vector3D sourceHalfwayVector, 
			                                         View3DWithLightSettings view){
	    LightSettings currentLightSettings = view.getLightSettings();
	    int specularExponent = currentLightSettings.getSpecularExponent();
		float g =(float)  -normal.dot(sourceHalfwayVector); 
         g =(float)  Math.max(g,0.1);
         g =(float)  Math.pow(g,specularExponent);  
		return g;
	}
	
	/**
	 *   This is a static method that transforms between camera and space coordinates.
	 * @param theVector  The vector in current camera coordinates to be transformed to space coordinates.
	 * @param theTransform    The Transform3D that contains the camera w.r.t. which theVector is given.
	 * @return           theVector with respect to space coordinates.
	 */
	private static Vector3D getDirectionInSpace(Vector3D theVector, Transform3D theTransform){
 	     Vector3D v1,v2,v3;
 	     Vector3D viewDirection = theTransform.getViewDirection();
	     Vector3D imagePlaneXDirection = theTransform.getImagePlaneXDirection();
	     Vector3D imagePlaneYDirection = theTransform.getImagePlaneYDirection();
 	     v1 = viewDirection.times(-theVector.z);
 	     v2 = imagePlaneXDirection.times(theVector.x);
 	     v3 = imagePlaneYDirection.times(theVector.y);
 	     return ((v1.plus(v2)).plus(v3)).normalized(); // (v1+v2+v3)/||v1+v2+v3||
       }
	
	/*
	 * This mixes together the light from the various sources to give the total light 
	 * intensity.
	 */
	
	private static float addValues(float r0,float r1,float r2,float r3,        // total light from light0 and 3 directional lights
			                       float c0, float c1,float c2, float c3, float c4  //an R, G, or B value of light0, the 3 directional lights and the ambient light.
			                       ){
		  if ( (Math.abs(r0) + Math.abs(r1) + Math.abs(r2) + Math.abs(r3)) == 0)
			  r0 = 1;
		  float sum = r0*c0 + r1*c1 + r2*c2 + r3*c3 + c4;
		  if (sum < 0) 
			   return 0.0f;
		  else
		      if (sum > 1) 
			    return 1.0f;
		      else
			    return sum;
	}
	
   public static Color phongLightingColor(Vector3D normal,     // normal to surface at position (location) where color is desired.
		                                  View3DWithLightSettings view,      // the View3DWithLightSettings containing the LightSettings.
		                                  Transform3D transform,   // The transform containing the viewpoint, etc.
		                                  Vector3D location,   // point on surface where normal is located and where color is desired.
		                                  Color intrinsicColor // color surface is painted at patch location
		                                  ){
	   int viewStyle = view.getViewStyle();
	   Vector3D viewPoint = transform.getViewPoint();
    	   Vector3D viewDirection = transform.getViewDirection();
	   Vector3D locationDirection = (location.minus(viewPoint)).normalized();
	   float[] intrinsicColorValues = intrinsicColor.getColorComponents(null); // array holding the R, G, and B values of intrinsicColor 
       LightSettings currentLightSettings = view.getLightSettings();
       Color source0Color = currentLightSettings.getLight0();
       DirectionalLight source1 = currentLightSettings.getDirectionalLight1();
       Color source1Color = source1.getItsColor();
       DirectionalLight source2 = currentLightSettings.getDirectionalLight2();
       Color source2Color = source2.getItsColor();
       DirectionalLight source3 = currentLightSettings.getDirectionalLight3();
       Color source3Color = source3.getItsColor();
       Color ambientColor = currentLightSettings.getAmbientLight();
       float scat0,scat1,scat2,scat3;   //intensities of scattered light from sources
       float spec0,spec1,spec2,spec3;   //intensities of specular light from sources
       float tot0,tot1,tot2,tot3;       //total intensities from sources
       //  int theEye,theLeftEye,theRightEye;
       Vector3D colorNormal;     // either normal or - normal depending on orientation
       Vector3D source1DirectionInSpace = getDirectionInSpace(source1.getItsDirection(),transform);
       Vector3D source2DirectionInSpace = getDirectionInSpace(source2.getItsDirection(),transform);
       Vector3D source3DirectionInSpace = getDirectionInSpace(source3.getItsDirection(),transform);
       Vector3D source0HalfwayVector = (locationDirection.plus(viewDirection)).normalized();
       Vector3D source1HalfwayVector = (locationDirection.plus(source1DirectionInSpace)).normalized().times(-1);
       Vector3D source2HalfwayVector = (locationDirection.plus(source2DirectionInSpace)).normalized().times(-1);
       Vector3D source3HalfwayVector = (locationDirection.plus(source3DirectionInSpace)).normalized().times(-1);
       Vector3D theDirection;
        
       if (view.getOrthographicProjection())    
    	        theDirection = viewDirection;         // we're using orthographic projection
    	   else
    		    theDirection = locationDirection;     // we're using perpective projection
      
       
       if (normal.dot(theDirection) <= 0)
	       colorNormal = normal;
       else
	       colorNormal = normal.negated();
       
    //  boolean source1InViewpointHalfspace = (colorNormal.dot(source1DirectionInSpace)<=0);
    //  boolean source2InViewpointHalfspace = (colorNormal.dot(source2DirectionInSpace)<=0);
    //  boolean source3InViewpointHalfspace = (colorNormal.dot(source3DirectionInSpace)<=0);
       
       
       if (view instanceof View3DLit)
    	   getTheReflectance(normal,theDirection,(View3DLit)view);
       else
    	   theReflectance = 1.0F;
       
       // Next, get the intensity of the diffusely scattered light from each source.
       
    	   scat0 = theReflectance * (float) -colorNormal.dot(locationDirection);
//       if (!source1InViewpointHalfspace) scat1 = 0;   else 
       scat1 = theReflectance * (float) -colorNormal.dot(source1DirectionInSpace);
//        if (!source2InViewpointHalfspace)/ scat2 = 0;   else 
       scat2 = theReflectance * (float)  -colorNormal.dot(source2DirectionInSpace);
//        if (!source3InViewpointHalfspace)/ spec3 = 0;   else 
       scat3 = theReflectance * (float)  -colorNormal.dot(source3DirectionInSpace);
        	  
        	/*
        	 * Now, get the intensity of the specularly reflected light for each source.
        	*/
    	   
    	   spec0 = theReflectance * specularIntensityOfSource(normal, source0HalfwayVector, view);
    	   
//       if (!source1InViewpointHalfspace) spec1 = 0;    else 
//   	   if (!source1InViewpointHalfspace) {System.out.print("negative");  }  
    	   
	   spec1 = theReflectance * specularIntensityOfSource(normal, source1HalfwayVector, view);
//       if (!source2InViewpointHalfspace)  spec2 = 0;  else 
	   spec2 = theReflectance * specularIntensityOfSource(normal, source2HalfwayVector, view);
//       if (!source3InViewpointHalfspace)  spec3 = 0;  else 
	   spec3 = theReflectance * specularIntensityOfSource(normal, source3HalfwayVector, view);
	       
	    /*
	     * And now compute the total brightness of the diffuse and specular light for each source.
	     */
	       
	    tot0 = (1 - removalRatio * currentLightSettings.getSpecularRatio()) *scat0
                + currentLightSettings.getSpecularRatio() *spec0;
	    tot1 = (1 - removalRatio * currentLightSettings.getSpecularRatio()) *scat1
                + currentLightSettings.getSpecularRatio() *spec1; 
	    tot2 = (1 - removalRatio * currentLightSettings.getSpecularRatio()) *scat2
                + currentLightSettings.getSpecularRatio() *spec2; 
	    tot3 = (1 - removalRatio * currentLightSettings.getSpecularRatio()) *scat3
                + currentLightSettings.getSpecularRatio() *spec3; 
	    
  // Finally, mix the total colors from the various sources and the ambient light.
	    
	    float[] source0ColorValues = source0Color.getColorComponents(null);    // These are arrays holding  R, G, and B values
	    float[] source1ColorValues = source1Color.getColorComponents(null);    // in 0, 1, and 2 spots respectively
	    float[] source2ColorValues = source2Color.getColorComponents(null);
	    float[] source3ColorValues = source3Color.getColorComponents(null);
	    float[] ambientColorValues = ambientColor.getColorComponents(null);
	    
	    float redValue,greenValue,blueValue;
	    
	 
	    if (viewStyle == View3D.RED_GREEN_STEREO_VIEW) {
	    	
	       	    redValue = greenValue = blueValue = Math.min(Math.max(tot0,0.0f),1.0f);
	    	    
	   /*    	
	      switch(theEye) {
	          	case theLeftEye:
	          	    redValue = tot0;
	          	    greenValue = 0.0f;
	          	    blueValue =  0.0f;
	          	    break;
	          	case theRightEye:
	          		redValue = 0.0f;
	          	    greenValue = tot0;
	          	    blueValue =  0.0f;
	          	   break;
	        	} 
	   */
	       	    
	    }
	    else { 
	    
	    redValue = addValues(tot0,tot1,tot2,tot3,
	    		             source0ColorValues[0],source1ColorValues[0],
	    		             source2ColorValues[0],source3ColorValues[0],ambientColorValues[0] );
	    
	    greenValue = addValues(tot0,tot1,tot2,tot3,
                         source0ColorValues[1],source1ColorValues[1],
                         source2ColorValues[1],source3ColorValues[1],ambientColorValues[1] );
	    
	    blueValue = addValues(tot0,tot1,tot2,tot3,
                         source0ColorValues[2],source1ColorValues[2],
                         source2ColorValues[2],source3ColorValues[2],ambientColorValues[2] );
	   }
	    
	    return new Color(redValue*intrinsicColorValues[0],
	    		            greenValue*intrinsicColorValues[1],
	    		            blueValue*intrinsicColorValues[2]);
   }
}
