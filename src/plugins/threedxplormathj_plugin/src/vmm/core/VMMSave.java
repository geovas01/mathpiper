/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation "@VMMSave" can be added to a declaration of a property
 * variable in Exhibit, View, Transform, or Declaration class to indicate that
 * the value of the property is to be saved by {@link vmm.core.SaveAndRestore}
 * when it outputs an object that belongs to the class to a file.  Here,
 * a "property" variable means an instance variable for which there exist getter and
 * setter methods with the standard names ("getXxxx" and "setXxxx" the
 * name of the vaiable is "xxxx").  (The alternative to doing this is
 * to call {@link vmm.core.SaveAndRestore#addProperty(Object, String, org.w3c.dom.Document, org.w3c.dom.Element)}
 * in an <code>addExtraXML()</code> method in the class.
 * See {@link vmm.core.Exhibit#addExtraXML(org.w3c.dom.Document, org.w3c.dom.Element)}.)
 * <p>In addition, "@VMMSave" can be added to the declaration of a subclass
 * of Decoration to indicate that all decorations of that type, when they
 * have been added to a View or Exhibit, are to be saved automatically
 * by SaveAndRestore when the View or Exhibit is saved.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface VMMSave {
}
