/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import vmm.core.Display;
import vmm.core.Exhibit;
import vmm.core.I18n;
import vmm.core.SaveAndRestore;
import vmm.core.UserExhibit;
import vmm.core.View;

/**
 * An interface that should be implemented by a user exhibit that is a 
 * subclass of Exhibit3D.  See {@link UserExhibit}.  For a 3D user
 * exhbiit, the "Support" object will be of type
 * UserExhibit3D.Support rather than UserExhibit.Support
 */
public interface UserExhibit3D extends UserExhibit {
	
	/**
	 * This method is already defined in class {@link Exhibit} and will not
	 * ordinarily be redefined in a user exhibit class.
	 * @see Exhibit3D#getDefaultViewpoint()
	 */
	public Vector3D getDefaultViewpoint();

	/**
	 * This method is already defined in class {@link Exhibit} and will not
	 * ordinarily be redefined in a user exhibit class.
	 * @see Exhibit3D#setDefaultViewpoint(Vector3D)
	 */
	public void setDefaultViewpoint(Vector3D viewpoint);
	
	/**
	 * This subclass of UserExhibit.Support contains user
	 * data for a 3D user exhibit.
	 */
	public static class Support extends UserExhibit.Support {
		
		private boolean showViewpoint = true;
		private ViewpointInput viewpointInput;
		final private UserExhibit3D exhibit3D;
		
		/**
		 * Create a 3D user exhibit support object.  This will ordinarily be
		 * called in the constructor of a class that implements the UserExhbit3D
		 * interface, in the form "new UserExhibit3D.Support(this)".
		 */
		public Support(UserExhibit3D exhibit3D) {
			super(exhibit3D);
			this.exhibit3D = exhibit3D;
		}
		
		/**
		 * @see #setShowViewpoint(boolean)
		 */
		public boolean getShowViewpoint() {
			return showViewpoint;
		}

		/**
		 * Sets the showViewpoint property.  The default value is true.
		 * When this property is true, an input panel is added to the user data
		 * dialog box where the use can enter the viewpoint from which the
		 * exhibit is viewed.  If the property is false, then the viewpoint
		 * input panel is omitted from the dialog box.
		 */
		public void setShowViewpoint(boolean showViewpoint) {
			this.showViewpoint = showViewpoint;
		}
		
		protected Dialog createDialog(Display display, View view, boolean creating) {
			Dialog dialog = super.createDialog(display,view,creating);
			if (showViewpoint && view instanceof View3D) {
				Vector3D v;
				if (creating)
					v = exhibit3D.getDefaultViewpoint();
				else
					v = ((View3D)view).getViewPoint();
				if (v == null)
					v = new Vector3D(20,0,0);
				viewpointInput = new ViewpointInput(v);
				dialog.addExtraPanel(viewpointInput);
			}
			return dialog;
		}
		
		protected void finish(Dialog dialog, View view, boolean creating) {
			super.finish(dialog,view,creating);
			if (showViewpoint && view instanceof View3D) {
				((View3D)view).setViewPoint(viewpointInput.viewpoint);
				exhibit3D.setDefaultViewpoint(viewpointInput.viewpoint);
			}
		}
		
		public void addToXML(Document containingDocument, Element userDataElement) {
			super.addToXML(containingDocument, userDataElement);
			SaveAndRestore.addProperty(this, "showViewpoint", containingDocument, userDataElement);
		}
		
		private class ViewpointInput extends ExtraPanel {
			private JTextField[] viewpointInputs;
			private Vector3D viewpoint;
			private ViewpointInput(Vector3D v) {
				super(I18n.tr("vmm.core.UserExhibitDialog.ViewpointPanelTitle"));
				setLayout(new GridLayout(0,4,5,5));
				viewpointInputs = new JTextField[3];
				viewpointInputs[0] = new JTextField("" + v.x);
				viewpointInputs[1] = new JTextField("" + v.y);
				viewpointInputs[2] = new JTextField("" + v.z);
				add(new JLabel(I18n.tr("vmm.core.UserExhibitDialog.ViewFrom") + ": ", JLabel.RIGHT));
				add(viewpointInputs[0]);
				add(viewpointInputs[1]);
				add(viewpointInputs[2]);
			}
			public void checkData() {
				double[] vp = new double[3];
				for (int i = 0; i < 3; i++) {
					try {
						vp[i] = Double.parseDouble(viewpointInputs[i].getText());
					}
					catch (NumberFormatException e) {
						viewpointInputs[i].requestFocus();
						viewpointInputs[i].selectAll();
						throw new IllegalArgumentException(I18n.tr("vmm.core.UserExhibitDialog.error.badViewpoint"));
					}
				}
				viewpoint = new Vector3D(vp[0],vp[1],vp[2]);
			}
		}
		
	}

}
