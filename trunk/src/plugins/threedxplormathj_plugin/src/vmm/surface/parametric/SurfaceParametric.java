/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.parametric;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import vmm.actions.ActionList;
import vmm.actions.ActionRadioGroup;
import vmm.core.I18n;
import vmm.core.ParameterInput;
import vmm.core.RealParam;
import vmm.core.RealParamAnimateable;
import vmm.core.SaveAndRestore;
import vmm.core.SettingsDialog;
import vmm.core.TaskManager;
import vmm.core.View;
import vmm.core3D.Grid3D;
import vmm.core3D.LightSettings;
import vmm.core3D.Vector3D;
import vmm.core3D.View3DLit;
import vmm.functions.Function2;
import vmm.functions.ParseError;
import vmm.functions.Parser;
import vmm.functions.Variable;
import vmm.surface.Surface;

/**
 * Defines a suface by a set of parametric equations defined on a rectangular domain
 * in the uv-plane.
 */
abstract public class SurfaceParametric extends Surface {
	
	/**
	 * One of the types of surface coloration, for use in the setColoration method.
	 */
	public static final int COLOR_WHITE = 0;
	public static final int COLOR_USER = 1;
	public static final int COLOR_TWO_SIDED_DEFAULT = 2;
	public static final int COLOR_TWO_SIDED_USER = 3;
	public static final int COLOR_GAUSS_CURVATURE = 5;
	public static final int COLOR_MEAN_CURVATURE = 6;
	public static final int COLOR_USER_FUNCTION_HSB = 7;
	public static final int COLOR_USER_FUNCTION_RGB = 8;
	
	private static final Color DEFAULT_FRONT_FACE_COLOR = new Color(229,229,178);  // for two-sided color default
	private static final Color DEFAULT_BACK_FACE_COLOR = new Color(173,166,255);
	
	private int colorationType = COLOR_WHITE;
	private Color twoSidedColor;
	private Color frontColor = DEFAULT_FRONT_FACE_COLOR;
	private Color backColor = DEFAULT_BACK_FACE_COLOR;
	private Variable uminVar = new Variable("umin");
	private Variable umaxVar = new Variable("umax");
	private Variable vminVar = new Variable("vmin");
	private Variable vmaxVar = new Variable("vmax");
	private Function2[] userColorExpressionHSB;
	private Function2[] userColorExpressionRGB;
	private String[] userColorExpressionHSBDefinitions;
	private String[] userColorExpressionRGBDefinitions;
	private TaskManager taskManager; // use for computing surface data in parallel
	
	
	private ActionRadioGroup surfaceColorationSelect = new ActionRadioGroup(new String[] {
			I18n.tr("vmm.surface.parametric.SurfaceParametric.color.WhitePaint"),
			I18n.tr("vmm.surface.parametric.SurfaceParametric.color.UserPaint"),
			I18n.tr("vmm.surface.parametric.SurfaceParametric.color.TwoSidedDefault"),
			I18n.tr("vmm.surface.parametric.SurfaceParametric.color.TwoSidedUser"),
			null,
			I18n.tr("vmm.surface.parametric.SurfaceParametric.color.GaussCurvature"),
			I18n.tr("vmm.surface.parametric.SurfaceParametric.color.MeanCurvature"),
			I18n.tr("vmm.surface.parametric.SurfaceParametric.color.UserHSB"),
			I18n.tr("vmm.surface.parametric.SurfaceParametric.color.UserRGB")
	}, 0) {
		public void optionSelected(int selectedIndex) {
			doSurfaceColorationCommand(selectedIndex);
		}
	};
	
	
	/**
	 * The lower limit of u values for the domain of the parametric functions that define this surface.
	 * Default value is -5.
	 */
	protected RealParamAnimateable umin = new RealParamAnimateable("vmm.surface.parametric.SurfaceParametric.Umin",-5);

	/**
	 * The upper limit of u values for the domain of the parametric functions that define this surface.
	 * Default value is 5.
	 */
	protected RealParamAnimateable umax = new RealParamAnimateable("vmm.surface.parametric.SurfaceParametric.Umax",5);

	/**
	 * The lower limit of v values for the domain of the parametric functions that define this surface.
	 * Default value is -5.
	 */
	protected RealParamAnimateable vmin = new RealParamAnimateable("vmm.surface.parametric.SurfaceParametric.Vmin",-5);
	
	/**
	 * The upper limit of v values for the domain of the parametric functions that define this surface.
	 * Default value is 5.
	 */
	protected RealParamAnimateable vmax = new RealParamAnimateable("vmm.surface.parametric.SurfaceParametric.Vmax",5);
	
	/**
	 * The parametric equations that define the surface, given as a function from the uv-plane into R3.
	 * The return value of this function can be null, indicating that the function is undefined for
	 * the given u and v. 
	 */
	abstract public Vector3D surfacePoint(double u, double v);
	
	/**
	 * Adds umin, umax, vmin, and vmax as parameters of the exhibit.
	 */
	public SurfaceParametric() {
		addParameter(vmax);
		addParameter(vmin);
		addParameter(umax);
		addParameter(umin);
		setFramesForMorphing(20);
	}
	

	private final static double epsilon = 0.00005; // for use in derivative functions only
	
	/**
	 * Returns an approximation for the partial derivative in the direction u at a specified (u,v) point.
	 */
	public  Vector3D deriv_u (double u, double v) {
		return surfacePoint(u + epsilon, v).minus(surfacePoint(u - epsilon, v)).times(1/(2*epsilon));
	}
	
	/**
	 * Returns an approximation for the partial derivative in the direction v at a specified (u,v) point.
	 */
	public Vector3D deriv_v (double u, double v) {
		return surfacePoint(u, v + epsilon).minus(surfacePoint(u, v - epsilon)).times(1/(2*epsilon));
	}

	/**
	 * Returns the Surface Normal, by default from numerical differentiation.
	 * For Weierstrass surfaces the known Gauss map allows to override the numerical differentiation.
	 */
	public Vector3D surfaceNormal (double u, double v) {
		Vector3D d1 = deriv_u(u,v);
		Vector3D d2 = deriv_v(u,v);
		return d1.cross(d2);
	}
	
 	/**
 	 * Overridden to shut down the task manager that is used for parallelization,
 	 * when there are no more views of the exhibit.
 	 */
	public void removeView(View view) {
		super.removeView(view);
		if (taskManager != null && (getViews() == null  || getViews().size() == 0)) {
			taskManager.shutDown();
			taskManager = null;
		}			
	}


	private class ComputeOneRowOfGrid implements Runnable {
		Grid3D data;
		int row;
		double u;
		double v1, dv;
		int vCount;
		ComputeOneRowOfGrid(Grid3D data, int row, double u, double v1, double dv, int vCount) {
			this.row = row;
			this.u = u;
			this.data = data;
			this.v1 = v1;
			this.dv = dv;
			this.vCount = vCount;
		}
		public void run() {
			for (int j = 0; j < vCount; j++) {
				double v = v1 + j*dv;
				data.setVertex(row,j,surfacePoint(u,v));
				if (colorationType > COLOR_TWO_SIDED_USER)
					data.setPatchColor(row,j,computePatchColor(u,v));
				//Vector3D d1 = deriv_u(u,v);
				//Vector3D d2 = deriv_v(u,v);
				Vector3D normal = surfaceNormal(u,v);
				data.setNormal(row,j,normal);
			}
		}
	}
	
	/**
	 * Computes the data for the surface using equally spaced values of u and v in the ranges
	 * defined by umin, umax, vmin, and vmax.
	 */
	protected void createData() {
		if (data == null || data.getUPatchCount() != uPatchCount.getValue() || data.getVPatchCount() != vPatchCount.getValue())
			data = new Grid3D(uPatchCount.getValue(), vPatchCount.getValue());  // create a new grid object only if necessary.
		int uCount = data.getUCount();
		int vCount = data.getVCount();
		double u1 = umin.getValue();
		double u2 = umax.getValue();
		double v1 = vmin.getValue();
		double v2 = vmax.getValue();
		double du = (u2 - u1) / (uCount - 1);
		double dv = (v2 - v1) / (vCount - 1);
		uminVar.setVal(umin.getValue());
		umaxVar.setVal(umax.getValue());
		vminVar.setVal(vmin.getValue());
		vmaxVar.setVal(vmax.getValue());
		if (taskManager == null)
			taskManager = new TaskManager();
		ArrayList<Runnable> tasks = new ArrayList<Runnable>(uCount);
		for (int i = 0; i < uCount; i++)
			tasks.add(new ComputeOneRowOfGrid(data,i,u1+i*du,v1,dv,vCount));
		taskManager.executeAndWait(tasks);
		if (colorationType <= COLOR_TWO_SIDED_USER) {
			data.clearPatchColors();
			switch (colorationType) {
			case COLOR_WHITE:
				data.setDefaultPatchColor(Color.WHITE);
				data.setDefaultBackColor(null);
				break;
			case COLOR_USER:
				data.setDefaultPatchColor(twoSidedColor);
				data.setDefaultBackColor(null);
				break;
			case COLOR_TWO_SIDED_DEFAULT:
			case COLOR_TWO_SIDED_USER:
				data.setDefaultPatchColor(frontColor);
				data.setDefaultBackColor(backColor);
				break;
			}
		}
	}

//	protected void createData() {
//		if (data == null || data.getUPatchCount() != uPatchCount.getValue() || data.getVPatchCount() != vPatchCount.getValue())
//			data = new Grid3D(uPatchCount.getValue(), vPatchCount.getValue());  // create a new grid object only if necessary.
//		int uCount = data.getUCount();
//		int vCount = data.getVCount();
//		double u1 = umin.getValue();
//		double u2 = umax.getValue();
//		double v1 = vmin.getValue();
//		double v2 = vmax.getValue();
//		double du = (u2 - u1) / (uCount - 1);
//		double dv = (v2 - v1) / (vCount - 1);
//		for (int i = 0; i < uCount; i++) {
//			double u = u1 + i*du;
//			for (int j = 0; j < vCount; j++) {
//				double v = v1 + j*dv;
//				data.setVertex(i,j,surfacePoint(u,v));
//				if (colorationType > COLOR_TWO_SIDED_USER)
//					data.setPatchColor(i,j,computePatchColor(u,v));
//			}
//		}
//		for (int i = 0; i <= uCount; i++) {
//			double u = u1 + i*du;
//			for (int j = 0; j <= vCount; j++) {
//				double v = v1 + j*dv;
//				Vector3D d1 = deriv_u(u,v);
//				Vector3D d2 = deriv_v(u,v);
//				Vector3D normal = d1.cross(d2);
//				data.setNormal(i,j,normal);
//			}
//		}
//		if (colorationType <= COLOR_TWO_SIDED_USER) {
//			data.clearPatchColors();
//			switch (colorationType) {
//			case COLOR_WHITE:
//				data.setDefaultPatchColor(Color.WHITE);
//				data.setDefaultBackColor(null);
//				break;
//			case COLOR_USER:
//				data.setDefaultPatchColor(twoSidedColor);
//				data.setDefaultBackColor(null);
//				break;
//			case COLOR_TWO_SIDED_DEFAULT:
//			case COLOR_TWO_SIDED_USER:
//				data.setDefaultPatchColor(frontColor);
//				data.setDefaultBackColor(backColor);
//				break;
//			}
//		}
//	}
	
	private Color computePatchColor(double u, double v) {
		switch (colorationType) {
		case COLOR_GAUSS_CURVATURE:
			double gc = findGaussAndMeanCurvatureAt(u,v)[1];
			double gc_mod1 = gc - (int)Math.floor(gc); 
			return Color.getHSBColor((float)gc_mod1, 1.0F, 1.0F);
		case COLOR_MEAN_CURVATURE:
			double mc = findGaussAndMeanCurvatureAt(u,v)[0];
			double mc_mod1 = mc - (int)Math.floor(mc);
			return Color.getHSBColor((float)mc_mod1, 1.0F, 1.0F);
		default:
			try {
				if (colorationType == COLOR_USER_FUNCTION_HSB) {
					float a = (float) Math.max(0, Math.min(1, userColorExpressionHSB[0].value(u,v)));
					float b = (float) Math.max(0, Math.min(1, userColorExpressionHSB[1].value(u,v)));
					float c = (float) Math.max(0, Math.min(1, userColorExpressionHSB[2].value(u,v)));
					return Color.getHSBColor(a, b, c);
				}
				else {
					float a = (float) Math.max(0, Math.min(1, userColorExpressionRGB[0].value(u,v)));
					float b = (float) Math.max(0, Math.min(1, userColorExpressionRGB[1].value(u,v)));
					float c = (float) Math.max(0, Math.min(1, userColorExpressionRGB[2].value(u,v)));
					return new Color(a, b, c);
				}
			}
			catch (NullPointerException e) { // This can only happen if expressions were not set properly
				return Color.WHITE;
			}
		}
	}
	
	public ActionList getActionsForView(View view) {
		ActionList commands = super.getActionsForView(view);
		if (! (view instanceof View3DLit))
			return commands;
		ActionList submenu = new ActionList(I18n.tr("vmm.surface.parametric.SurfaceParametric.SurfaceColoration"));
		submenu.add(surfaceColorationSelect);
		commands.add(null);
		commands.add(submenu);
		return commands;
	}
	
	public void addExtraXML(Document containingDocument, Element viewElement) {
		super.addExtraXML(containingDocument,viewElement);
		if (colorationType != COLOR_WHITE) {
			Element colorElement = containingDocument.createElement("surface_coloration");
			colorElement.setAttribute("type",""+colorationType);
			switch (colorationType) {
			case COLOR_USER:
				SaveAndRestore.addElement(containingDocument,colorElement,"user_color",twoSidedColor);
				break;
			case COLOR_TWO_SIDED_USER:
				SaveAndRestore.addElement(containingDocument,colorElement,"front_color",frontColor);
				SaveAndRestore.addElement(containingDocument,colorElement,"back_color",backColor);
				break;
			case COLOR_USER_FUNCTION_HSB:
				SaveAndRestore.addElement(containingDocument,colorElement,"user_color_1",userColorExpressionHSBDefinitions[0]);
				SaveAndRestore.addElement(containingDocument,colorElement,"user_color_2",userColorExpressionHSBDefinitions[1]);
				SaveAndRestore.addElement(containingDocument,colorElement,"user_color_3",userColorExpressionHSBDefinitions[2]);
				break;
			case COLOR_USER_FUNCTION_RGB:
				SaveAndRestore.addElement(containingDocument,colorElement,"user_color_1",userColorExpressionRGBDefinitions[0]);
				SaveAndRestore.addElement(containingDocument,colorElement,"user_color_2",userColorExpressionRGBDefinitions[1]);
				SaveAndRestore.addElement(containingDocument,colorElement,"user_color_3",userColorExpressionRGBDefinitions[2]);
				break;
			}
			viewElement.appendChild(colorElement);
		}
	}
	
	public void readExtraXML(Element viewInfo) throws IOException {
		super.readExtraXML(viewInfo);
		try {
			NodeList nodes = viewInfo.getElementsByTagName("surface_coloration");
			if (nodes.getLength() == 0)
				return;
			Element colorElement = (Element)nodes.item(0);
			int colorType = Integer.parseInt(colorElement.getAttribute("type"));
			switch (colorType) {
			case COLOR_USER:
				twoSidedColor = (Color)SaveAndRestore.getChildElementValue(colorElement,"user_color",Color.class);
				if (twoSidedColor == null)
					return;
				break;
			case COLOR_TWO_SIDED_USER:
				frontColor = (Color)SaveAndRestore.getChildElementValue(colorElement,"front_color",Color.class);
				backColor = (Color)SaveAndRestore.getChildElementValue(colorElement,"back_color",Color.class);
				if (frontColor == null || backColor == null)
					return;
				break;
			case COLOR_USER_FUNCTION_HSB:
			case COLOR_USER_FUNCTION_RGB:
				Parser parser = new Parser();
				parser.add(uminVar);
				parser.add(umaxVar);
				parser.add(vminVar);
				parser.add(vmaxVar);
				Function2[] exp = new Function2[3];
				String[] defs = new String[3];
				defs[0] = (String)SaveAndRestore.getChildElementValue(colorElement,"user_color_1",String.class);
				defs[1] = (String)SaveAndRestore.getChildElementValue(colorElement,"user_color_2",String.class);
				defs[2] = (String)SaveAndRestore.getChildElementValue(colorElement,"user_color_3",String.class);
				exp[0] = parser.parseFunction2(null,defs[0],"u","v");
				exp[1] = parser.parseFunction2(null,defs[1],"u","v");
				exp[2] = parser.parseFunction2(null,defs[2],"u","v");
				if (colorType == COLOR_USER_FUNCTION_HSB) {
					userColorExpressionHSB = exp;
					userColorExpressionHSBDefinitions = defs;
				}
				else {
					userColorExpressionRGB = exp;
					userColorExpressionRGBDefinitions = defs;
				}
			}
			colorationType = colorType;
			surfaceColorationSelect.setSelectedIndex(colorationType);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Vector3D gaussMap (double u, double v) {
		Vector3D VV = deriv_u(u, v).cross(deriv_v(u, v));
		double norm = VV.norm();
		//System.out.println("Without override in gaussMap");// presently not used
		if (Double.isInfinite(norm) || Double.isNaN(norm) || norm == 0) { 
			VV.x = 1.0;
			VV.y = 0.0;
			VV.z = 0.0;
		}
		else
			VV.normalize();
		return VV;
	}
	
	private Vector3D gaussMap_u (double u, double v) {
		return gaussMap(u + epsilon, v).minus(gaussMap(u - epsilon, v)).times(1/(2*epsilon));
	}
	
	private Vector3D gaussMap_v (double u, double v) {
		return gaussMap(u, v + epsilon).minus(gaussMap(u, v - epsilon)).times(1/(2*epsilon));
	}
	
	
	private double[] findGaussAndMeanCurvatureAt (double u, double v) {
		    //returns an array of two items, containing the mean and gaussian curvature
		Vector3D X_u, X_v;            // un-normalized tangent vectors in the u and v directions:
		Vector3D N_u, N_v;            // directional derivatives of unit normal vector field in u and v directions
		Vector3D e1;               // the unit vector in the u  direction
		Vector3D e2;               // a unit tangent vector orthogonal to the u  direction
		Vector3D e3;               // e3 = e1 + e2
		Vector3D N_e1, N_e2, N_e3;       // directional derivatives of unit normal field in the e1 and e2 directions
		double compX_vAlong_e1;     // the component of X_v along e1
		Vector3D w, w1;               // projections of X_v tangent and normal to e1
		Vector3D N_w, N_w1;            // directional derivatives of unit normal field in the w and w1 directions
		
		double A11, A22, A12;      // matrix elements of 2nd Fundamental Form in the basis e1, e2
		
		X_u = deriv_u(u, v);         // un-normalized tangent vector in u direction
		X_v = deriv_v(u, v);         // un-normalized tangent vector in v direction
		
		N_u = gaussMap_u(u, v);      // directional derivative of unit normal field in u direction
		N_v = gaussMap_v(u, v);      // directional derivative of unit normal field in v direction
		
		
		e1 = X_u.normalized();     // normalize X_u to get unit vector e1 in X_u direction
		
		// Now apply Gram-Schmidt to get e2
		
		compX_vAlong_e1 = X_v.dot(e1);
		w = e1.times(compX_vAlong_e1);      // w is the projection of X_v along e1
		w1 = X_v.minus(w);                  // w1 is the projection of X_v normal to e1
		e2 = w1.normalized();   // now normalize w1 to get unit vector e2 normal to e1
		
		// end of Gram-Schmidt
		
		
		e3 = e1.plus(e2);                  // e3 is the tangent vector (e1 + e2)
		
		
		N_e1 = N_u.times( 1 / X_u.norm() );        // by linearity of directional derivative
		N_w = N_e1.times(compX_vAlong_e1);      // by linearity of directional derivative
		N_w1 = N_v.minus(N_w);                // by linearity of directional derivative
		N_e2 = N_w1.times( 1 / w1.norm() );    // by linearity of directional derivative
		N_e3 = N_e1.plus(N_e2);               // by linearity of directional derivative
		
		
		A11 = -N_e1.dot(e1);                  // by definition of second fundamental form
		A22 = -N_e2.dot(e2);                  // by definition of second fundamental form
		A12 = 0.5 * (-N_e3.dot(e3) - A11 - A22);   // by polarization
		
		double meanCurvature = A11 + A22;                           //  Since H is the trace of the second fundamental form
		double gaussianCurvature = A11 * A22 - A12 * A12;                  //  Since K is the determinant of the second fundamental form
		
		return new double[] { meanCurvature, gaussianCurvature };
	}
	
	  
	
	private void doSurfaceColorationCommand(int command) {
		ArrayList views = getViews();
		switch (command) {
		case COLOR_WHITE:
			twoSidedColor = null;  // forced default
			for (int i = 0; i < views.size(); i++)
				if (views.get(i) instanceof View3DLit)
					((View3DLit)views.get(i)).setLightSettings(new LightSettings());
			break;
		case COLOR_TWO_SIDED_DEFAULT:
			frontColor = DEFAULT_FRONT_FACE_COLOR;
			backColor = DEFAULT_BACK_FACE_COLOR;
			for (int i = 0; i < views.size(); i++)
				if (views.get(i) instanceof View3DLit)
					((View3DLit)views.get(i)).setLightSettings(new LightSettings(LightSettings.DISTICTLY_COLORED_SIDES_DEFAULT));
			break;
		case COLOR_USER:
		case COLOR_TWO_SIDED_USER:
			ColorDialog dialog = new ColorDialog(command == COLOR_TWO_SIDED_USER);
			dialog.setVisible(true);
			if (dialog.canceled()) {
				surfaceColorationSelect.setSelectedIndex(colorationType);
				return;
			}
			break;
		case COLOR_USER_FUNCTION_HSB:
		case COLOR_USER_FUNCTION_RGB:
			UserFunctionDialog dia = new UserFunctionDialog(command == COLOR_USER_FUNCTION_RGB);
			dia.setVisible(true);
			if (dia.canceled()) {
				surfaceColorationSelect.setSelectedIndex(colorationType);
				return;
			}
			break;
		}
		for (int i = 0; i < views.size(); i++)
			if (views.get(i) instanceof View3DLit)
				((View3DLit)views.get(i)).setLightingEnabled(command <= COLOR_TWO_SIDED_USER);
		surfaceColorationSelect.setSelectedIndex(command);
		colorationType = command;
		forceRedraw();
	}
	
	private class UserFunctionDialog extends SettingsDialog {

		private boolean useRGB;
		private JTextField[] inputs = new JTextField[3];
		
	    UserFunctionDialog(boolean useRGB) {
			super(null, I18n.tr("vmm.surface.parametric.SurfaceParametric.dialog.UserColorFunctions"));
			this.useRGB = useRGB;
			JPanel inputPanel = new JPanel();
			inputPanel.setLayout(new BorderLayout(5,5));
			JPanel left = new JPanel();
			left.setLayout(new GridLayout(0,1,5,5));
			left.add(new JLabel(I18n.tr( (useRGB? "common.Red" : "common.Hue")) + "(u,v) = ", JLabel.RIGHT));
			left.add(new JLabel(I18n.tr( (useRGB? "common.Green" : "common.Saturation")) + "(u,v) = ", JLabel.RIGHT));
			left.add(new JLabel(I18n.tr( (useRGB? "common.Blue" : "common.Brightness")) + "(u,v) = ", JLabel.RIGHT));
			inputPanel.add(left,BorderLayout.WEST);
			inputs[0] = new JTextField(30);
			inputs[1] = new JTextField(30);
			inputs[2] = new JTextField(30);
			JPanel right = new JPanel();
			right.setLayout(new GridLayout(0,1,5,5));
			right.add(inputs[0]);
			right.add(inputs[1]);
			right.add(inputs[2]);
			inputPanel.add(right,BorderLayout.CENTER);
			if (!useRGB) {
				if (userColorExpressionHSBDefinitions == null)
					doDefaults();
				else {
					inputs[0].setText(userColorExpressionHSBDefinitions[0]);
					inputs[1].setText(userColorExpressionHSBDefinitions[1]);
					inputs[2].setText(userColorExpressionHSBDefinitions[2]);
				}
			}
			else {
				if (userColorExpressionRGBDefinitions == null)
					doDefaults();
				else {
					inputs[0].setText(userColorExpressionRGBDefinitions[0]);
					inputs[1].setText(userColorExpressionRGBDefinitions[1]);
					inputs[2].setText(userColorExpressionRGBDefinitions[2]);
				}
			}
			addInputPanel(inputPanel);
		}

		protected boolean doOK() {
			Parser parser = new Parser();
			parser.add(uminVar);
			parser.add(umaxVar);
			parser.add(vminVar);
			parser.add(vmaxVar);
			Function2[] exp = new Function2[3];
			String[] defs = new String[3];
			for (int i = 0; i < 3; i++){
				defs[i] = inputs[i].getText();
				try {
					exp[i] = parser.parseFunction2(null,defs[i],"u","v");
				}
				catch (ParseError e) {
					String error = I18n.tr("vmm.surface.parametric.SurfaceParametric.error.BadExpression", e.getMessage());
					JOptionPane.showMessageDialog(this,error,I18n.tr("vmm.core.SettingsDialog.errorTitle"),JOptionPane.ERROR_MESSAGE);
					inputs[i].selectAll();
					inputs[i].requestFocus();
					return false;
				}
			}
			if (useRGB) {
				userColorExpressionRGB = exp;
				userColorExpressionRGBDefinitions = defs;
			}
			else {
				userColorExpressionHSB = exp;
				userColorExpressionHSBDefinitions = defs;
			}
			return true;
		}
		
		protected void doApply() {
			if (doOK()) {
				colorationType = useRGB? COLOR_USER_FUNCTION_RGB : COLOR_USER_FUNCTION_HSB;
				ArrayList views = getViews();
				for (int i = 0; i < views.size(); i++)
					if (views.get(i) instanceof View3DLit)
						((View3DLit)views.get(i)).setLightingEnabled(false);
				forceRedraw();
			}
		}

		protected void doDefaults() {
			if (useRGB) {
				inputs[0].setText("0.5");
				inputs[1].setText("(u - umin) / (umax - umin)");
				inputs[2].setText("(v - vmin) / (vmax - vmin)");
			}
			else {
				inputs[0].setText("(v - vmin) / (vmax - vmin)");
				inputs[1].setText("1");
				inputs[2].setText("1");
			}
		}
		
		boolean canceled() {
			return canceled;
		}
		
	} // end class UserFunctionDialog
	
	
	private class ColorDialog extends SettingsDialog {
		private boolean twoSided;
		private RealParam[] params;
		private ParameterInput[] inputs;
		private JButton setButton, setButton2;
		private ActionListener setListener = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (evt.getSource() == setButton || evt.getSource() == setButton2) {
					Color c = Color.GRAY;
					try {
						if (evt.getSource() == setButton)
							c = new Color( (float)params[0].getValue(), (float)params[1].getValue(), (float)params[2].getValue() );
						else
							c = new Color( (float)params[3].getValue(), (float)params[4].getValue(), (float)params[5].getValue() );
					}
					catch (Exception e){
					}
					c = JColorChooser.showDialog(ColorDialog.this,"",c);
					if (c == null)
						return;
					double r = (double)c.getRed() / 255;
					double g = (double)c.getGreen() / 255;
					double b = (double)c.getBlue() / 255;  
					r = ( (int)(r*100000 + 0.499) ) / 100000.0;
					g = ( (int)(g*100000 + 0.499) ) / 100000.0;
					b = ( (int)(b*100000 + 0.499) ) / 100000.0; 
					if (evt.getSource() == setButton) {
						inputs[0].setText("" + r);
						inputs[1].setText("" + g);
						inputs[2].setText("" + b);
					}
					else {
						inputs[3].setText("" + r);
						inputs[4].setText("" + g);
						inputs[5].setText("" + b);
					}
				}
			}
		};
		
		ColorDialog(boolean twoSided) {
			super(null, I18n.tr("vmm.surface.parametric.SurfaceParametric.dialog.UserColor"));
			this.twoSided = twoSided;
			if (twoSided) {
				params = new RealParam[6];
				inputs = new ParameterInput[6];
				params[0] = new RealParam(I18n.tr("common.Front") + I18n.tr("common.Red"), frontColor.getRed()/255.0);
				params[1] = new RealParam(I18n.tr("common.Front") + I18n.tr("common.Green"), frontColor.getGreen()/255.0);
				params[2] = new RealParam(I18n.tr("common.Front") + I18n.tr("common.Blue"), frontColor.getBlue()/255.0);
				params[3] = new RealParam(I18n.tr("common.Back") + I18n.tr("common.Red"), backColor.getRed()/255.0);
				params[4] = new RealParam(I18n.tr("common.Back") + I18n.tr("common.Green"), backColor.getGreen()/255.0);
				params[5] = new RealParam(I18n.tr("common.Back") + I18n.tr("common.Blue"), backColor.getBlue()/255.0);
				params[0].setDefaultValue((int)(10000.0*229.0/255.0 + 0.499) / 10000.0);
				params[1].setDefaultValue((int)(10000.0*229.0/255.0 + 0.499) / 10000.0);
				params[2].setDefaultValue((int)(10000.0*178.0/255.0 + 0.499) / 10000.0);
				params[3].setDefaultValue((int)(10000.0*173.0/255.0 + 0.499) / 10000.0);
				params[4].setDefaultValue((int)(10000.0*166.0/255.0 + 0.499) / 10000.0);
				params[5].setDefaultValue(1);
			}
			else {
				params = new RealParam[3];
				inputs = new ParameterInput[3];
				Color c = twoSidedColor;
				if (c == null)
					c = Color.WHITE;
				params[0] = new RealParam(I18n.tr("common.Red"), c.getRed()/255.0);
				params[1] = new RealParam(I18n.tr("common.Green"), c.getGreen()/255.0);
				params[2] = new RealParam(I18n.tr("common.Blue"), c.getBlue()/255.0);
				params[0].setDefaultValue(1);
				params[1].setDefaultValue(1);
				params[2].setDefaultValue(1);
			}
			for (int i = 0; i < params.length; i++) {
				params[i].setMinimumValueForInput(0);
				params[i].setMaximumValueForInput(1);
				inputs[i] = new ParameterInput(params[i]);
				inputs[i].setColumns(6);
			}
			JPanel inputPanel = new JPanel();
			inputPanel.setLayout(new GridLayout(0, twoSided? 5 : 4, 5, 5));
			if (twoSided)
				inputPanel.add(new JLabel());
			inputPanel.add(new JLabel(I18n.tr("common.Red"), JLabel.CENTER));
			inputPanel.add(new JLabel(I18n.tr("common.Green"), JLabel.CENTER));
			inputPanel.add(new JLabel(I18n.tr("common.Blue"), JLabel.CENTER));
			inputPanel.add(new JLabel());
			if (twoSided)
				inputPanel.add(new JLabel(I18n.tr("common.Front") + ": ", JLabel.RIGHT));
			inputPanel.add(inputs[0]);
			inputPanel.add(inputs[1]);
			inputPanel.add(inputs[2]);
			setButton = new JButton(I18n.tr("vmm.core3D.LightSettingsDialog.SetColorButton"));
			setButton.addActionListener(setListener);
			inputPanel.add(setButton);
			if (twoSided) {
				inputPanel.add(new JLabel(I18n.tr("common.Back") + ": ", JLabel.RIGHT));
				inputPanel.add(inputs[3]);
				inputPanel.add(inputs[4]);
				inputPanel.add(inputs[5]);
				setButton2 = new JButton(I18n.tr("vmm.core3D.LightSettingsDialog.SetColorButton"));
				setButton2.addActionListener(setListener);
				inputPanel.add(setButton2);
			}
			addInputPanel(inputPanel);
		}
				
		protected void doApply() {
			if (doOK()) {
				ArrayList views = getViews();
				colorationType = twoSided? COLOR_TWO_SIDED_USER : COLOR_USER;
				for (int i = 0; i < views.size(); i++)
					if (views.get(i) instanceof View3DLit)
						((View3DLit)views.get(i)).setLightingEnabled(true);
				if (twoSided) {
					for (int i = 0; i < views.size(); i++)
						if (views.get(i) instanceof View3DLit)
							((View3DLit)views.get(i)).setLightSettings(new LightSettings(LightSettings.DISTICTLY_COLORED_SIDES_DEFAULT));
				}
				else {
					for (int i = 0; i < views.size(); i++)
						if (views.get(i) instanceof View3DLit)
							((View3DLit)views.get(i)).setLightSettings(new LightSettings());
				}
				forceRedraw();
			}
		}
		
		protected boolean doOK() {
			for (int i = 0; i < inputs.length; i++) {
				String error = inputs[i].checkContents();
				if (error != null) {
					JOptionPane.showMessageDialog(this,error,I18n.tr("vmm.core.SettingsDialog.errorTitle"),JOptionPane.ERROR_MESSAGE);
					return false;
				}
				inputs[i].setValueFromContents();
			}
			if (twoSided) {
				frontColor = new Color( (float)params[0].getValue(), (float)params[1].getValue(), (float)params[2].getValue() );
				backColor = new Color( (float)params[3].getValue(), (float)params[4].getValue(), (float)params[5].getValue() );
			}
			else {
				twoSidedColor = new Color( (float)params[0].getValue(), (float)params[1].getValue(), (float)params[2].getValue() );
			}
			return true;
		}
		
		protected void doDefaults() {
			for (int i = 0; i < inputs.length; i++)
				inputs[i].defaultVal();
		}

		boolean canceled() {
			return canceled;
		}
		
	} // end class ColorDialog

}
