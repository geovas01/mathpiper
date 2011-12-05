/*
    This file is part of JLog.

    Created by Glendon Holst for Alan Mackworth and the 
    "Computational Intelligence: A Logical Approach" text.
    
    Copyright 1998, 2000, 2002, 2008 by University of British Columbia, 
    Alan Mackworth and Glendon Holst.
    
    This notice must remain in all files which belong to, or are derived 
    from JLog.
    
    Check <http://jlogic.sourceforge.net/> or 
    <http://sourceforge.net/projects/jlogic> for further information
    about JLog, or to contact the authors.

    JLog is free software, dual-licensed under both the GPL and MPL 
    as follows:

    You can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Or, you can redistribute it and/or modify
    it under the terms of the Mozilla Public License as published by
    the Mozilla Foundation; version 1.1 of the License.

    JLog is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License, or the Mozilla Public License for 
    more details.

    You should have received a copy of the GNU General Public License
    along with JLog, in the file GPL.txt; if not, write to the 
    Free Software Foundation, Inc., 
    59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
    URLs: <http://www.fsf.org> or <http://www.gnu.org>

    You should have received a copy of the Mozilla Public License
    along with JLog, in the file MPL.txt; if not, contact:
    http://http://www.mozilla.org/MPL/MPL-1.1.html
    URLs: <http://www.mozilla.org/MPL/>
*/
//##################################################################################
//	aAnimationShape
//##################################################################################

package ubc.cs.JLog.Animation;

import java.lang.*;
import java.util.*;
import java.awt.*;

import ubc.cs.JLog.Terms.*;

/**
* This abstract class represents a simple graphical shape.  A shape belongs to an 
* AnimationObject, and the position and rotation of this shape is given as offset from
* position and rotation of the enclosing object.   
*
* @author       Glendon Holst
* @version      %I%, %G%
*/
abstract public class aAnimationShape extends aAttributeTranslation
{
 public static final String			X_ATTR_KEY = "x";
 public static final String			Y_ATTR_KEY = "y";
 public static final String			ROTATION_ATTR_KEY = "rotation";
 public static final String			TYPE_ATTR_KEY = "type";
 public static final String			NAME_ATTR_KEY = "name";
 public static final String			OBJECT_ATTR_KEY = "object";

 private static jTermTranslation		translations;
 
 static {
  translations = new jTermTranslation();
  setDefaultsForTranslation(translations);
 }
 
 protected aAnimationObject			object;
 protected String 					name;
 protected float					x_offset,y_offset;
 protected float 					rotation_offset;
 
 /**
  * The constructor.  Requires the owning <code>aAnimationObject</code>, and a name.
  *
  * @param ao 		The graphical object composed from this shape.
  * 			Animation shapes can only belong to one object.
  * @param n 		The name of the shape.
  */
 public 	aAnimationShape(aAnimationObject ao,String n)
 {
  object = ao;
  name = n;
  x_offset = (float) 0.0;
  y_offset = (float) 0.0;
  rotation_offset = (float) 0.0;
 };

 /**
  * Draw the shape in the <code>Graphics</code> environment
  * provided. 
  *
  * @param g 		The <code>Graphics</code> environment to draw into.
  */
 abstract public void 		draw(Graphics g);
 
 public String 		getName()
 {
  return name;
 };

 public void 		setName(String n)
 {int	curr_view = object.getView();
 
  object.setView(object.getShapeView(this));
  object.removeShape(this);
  name = n;
  object.addShape(this);
  object.setView(curr_view);
 };

 /**
  * Sub-classes should return their type value (i.e., the value used to specify the shape type
  * to create in the animation&lt;addshape&gt; predicate command).
  *
  * @return		The type string for the class.
  */
 abstract public String 		getType();

 /**
  * Returns the parent, enclosing object. 
  *
  * @return 	The <code>aAnimationObject</code> containing this shape.
  */
 public aAnimationObject 		getEnclosingObject()
 {
  return object;
 };

 /**
  * Returns the bounding rectangle for this shape, given in global coordinates. 
  *
  * @return 	The <code>Rectangle</code> bounding this shape.
  */
 abstract public Rectangle 		getBounds();
 
 /**
  * Set the new location of the shape to the given offset values.
  *
  * @param x 	The new x position component, relative to enclosing object.
  * @param y 	The new y position component, relative to enclosing object.
  */
 public void 		setLocalPosition(float x,float y)
 {
  x_offset = x;
  y_offset = y;
 };
 
 /**
  * Set the object relative rotation of the shape to the given angle in radians.
  *
  * @param r 	The new angle of the shape in radians.
  */
 public void 		setLocalRotation(float r)
 {
  rotation_offset = r;
 };
 
 public static void		setDefaultsForTranslation(jTermTranslation t)
 {
  aAttributeTranslation.setDefaultsForTranslation(t);
  
  setStringKeysForTranslation(t,X_ATTR_KEY,jReal.class,Float.class);
  setStringKeysForTranslation(t,Y_ATTR_KEY,jReal.class,Float.class);
  setStringKeysForTranslation(t,ROTATION_ATTR_KEY,jReal.class,Float.class);
  setStringKeysForTranslation(t,TYPE_ATTR_KEY,jAtom.class,String.class);
  setStringKeysForTranslation(t,NAME_ATTR_KEY,jAtom.class,String.class);
  setStringKeysForTranslation(t,OBJECT_ATTR_KEY,jObject.class,jObject.class);
 };

 public jTermTranslation	getTermTranslation()
 {
  return translations;
 };

 public Hashtable   getAttributes()
 {Hashtable		ht = new Hashtable(10);
 
  ht.put(X_ATTR_KEY,new Float(x_offset));
  ht.put(Y_ATTR_KEY,new Float(y_offset));
  ht.put(ROTATION_ATTR_KEY,new Float(rotation_offset));
  ht.put(TYPE_ATTR_KEY,getType());
  ht.put(NAME_ATTR_KEY,name);
  ht.put(OBJECT_ATTR_KEY,object);

  return ht;  
 };

 public void		setAttributes(Hashtable attributes)
 {
  // set name
  {String		n;
  
   if ((n = (String) attributes.get(NAME_ATTR_KEY)) != null)
    setName(n);
  }
  // set location
  {Float		fx = null, fy = null;
  
   fx = (Float) attributes.get(X_ATTR_KEY);
   fy = (Float) attributes.get(Y_ATTR_KEY);
  
   if (fx != null || fy != null)
    setLocalPosition((fx != null ? fx.floatValue() : x_offset),
					 (fy != null ? fy.floatValue() : y_offset));
  }
  // set rotation
  {Float		r;
  
   if ((r = (Float) attributes.get(ROTATION_ATTR_KEY)) != null)
    setLocalRotation(r.floatValue());
  }
 };
  
 /**
  * Update notification.  The enclosing object position has changed.
  */
 public void 		updatePosition()
 {
 };
 
 /**
  * Update notification.  The enclosing object rotation has changed.
  */
 public void 		updateRotation()
 {
 };

 /**
  * Update notification.  The environment magnification value changed.
  */
 public void 		updateMagnification()
 {
 };
};

