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
//	aAnimationShape Line
//##################################################################################

package ubc.cs.JLog.Animation;

import java.lang.*;
import java.util.*;
import java.awt.*;

import ubc.cs.JLog.Terms.*;

public class aAnimationShape_line extends aAnimationShape
{
 public static final String			LINE_ATTR_KEY = "line";
 public static final String			COLOUR_ATTR_KEY = "colour";

 private static jTermTranslation		translations;
 
 static {
  translations = new jTermTranslation();
  setDefaultsForTranslation(translations);
 }
 
 protected Color 					colour;
 protected FloatRectangle			original_line;

 protected FloatRectangle			cached_rotline;
 protected Rectangle 				cached_tranline;

 /**
  * The constructor.  Requires the owning <code>aAnimationObject</code>, and a name.
  * The rectangle describes the two points that make up the line (the width, height are the
  * x,y of the second point).
  *
  * @param ao 		The graphical object composed from this shape.
  * 			Animation shapes can only belong to one object.
  * @param n 		The name of the shape.
  * @param r 		The rectangle representing the line.
  * @param c 		The colour of the line.
  */
 public 	aAnimationShape_line(aAnimationObject ao,String n,FloatRectangle r,Color c)
 {
  super(ao,n);
  original_line = r;
  cached_rotline = null;
  cached_tranline = null;
  colour = c;
 };

 public void 		draw(Graphics g)
 {
  if (cached_rotline == null)
  {
   cached_rotline = generateRotatedLine(original_line);
   cached_tranline = generateTranslatedLine(cached_rotline);
  }
  else if (cached_tranline == null)
   cached_tranline = generateTranslatedLine(cached_rotline);
   
  g.setColor(colour);

  g.drawLine(cached_tranline.x,cached_tranline.y,cached_tranline.width,cached_tranline.height);
 };

 public Rectangle 		getBounds()
 {
  if (cached_rotline == null)
  {
   cached_rotline = generateRotatedLine(original_line);
   cached_tranline = generateTranslatedLine(cached_rotline);
  }
  else if (cached_tranline == null)
   cached_tranline = generateTranslatedLine(cached_rotline);
  
  {Rectangle	cr = cached_tranline;
   
   return new Rectangle(cr.x,cr.y,cr.width-cr.x,cr.height-cr.y);
  }
 };

 public String 		getType()
 {
  return "line";
 };

 public void 		setLine(FloatRectangle r)
 {
  original_line = r;
  
  cached_rotline = null;
  cached_tranline = null;
 };

 
 public void 		setLocalPosition(float x,float y)
 {
  super.setLocalPosition(x,y);
  cached_tranline = null;
 };
 
 public void 		setLocalRotation(float r)
 {
  super.setLocalRotation(r);
  cached_rotline = null;
  cached_tranline = null;
 };

 public static void		setDefaultsForTranslation(jTermTranslation t)
 {
  aAnimationShape.setDefaultsForTranslation(t);

  setStringKeysForTranslation(t,LINE_ATTR_KEY,FloatRectangle.class,FloatRectangle.class);
  setStringKeysForTranslation(t,COLOUR_ATTR_KEY,Color.class,Color.class);
 };

 public jTermTranslation	getTermTranslation()
 {
  return translations;
 };

 public Hashtable   getAttributes()
 {Hashtable		ht;
 
  ht = super.getAttributes();
 
  ht.put(LINE_ATTR_KEY,original_line);
  ht.put(COLOUR_ATTR_KEY,colour);
  
  return ht;  
 };

 public void		setAttributes(Hashtable attributes)
 {
  super.setAttributes(attributes);
 
  {FloatRectangle	fr;
  
   if ((fr = (FloatRectangle) attributes.get(LINE_ATTR_KEY)) != null)
    setLine(fr);
  }
  {Color	c;
  
   if ((c = (Color) attributes.get(COLOUR_ATTR_KEY)) != null)
    colour = c;
  }
 };
 
 public void 		updatePosition()
 {
  cached_tranline = null;
 };
 
 public void 		updateRotation()
 {
  cached_rotline = null;
  cached_tranline = null;
 };

 public void		updateMagnification()
 {
  cached_rotline = null;
  cached_tranline = null;
 };
 
 protected FloatRectangle 	generateRotatedLine(FloatRectangle fr)
 {float		rot = object.getGlobalRotationFromLocal(rotation_offset);
  float 	a1 = (float) (Math.atan2(fr.x,fr.y) + rot);
  float 	d1 = (float) Math.sqrt(fr.x*fr.x + fr.y*fr.y);
  float 	a2 = (float) (Math.atan2(fr.width,fr.height) + rot);
  float 	d2 = (float) Math.sqrt(fr.width*fr.width + fr.height*fr.height);
  
  return new FloatRectangle((float) Math.sin(a1)*d1,(float) Math.cos(a1)*d1,
							(float) Math.sin(a2)*d2,(float) Math.cos(a2)*d2);
 };

 protected Rectangle 	generateTranslatedLine(FloatRectangle fr)
 {Point	p1;
  Point	p2;
   
  p1 = object.getDisplayPointFromLocalPoint(fr.x+x_offset,fr.y+y_offset);
  p2 = object.getDisplayPointFromLocalPoint(fr.width+x_offset,fr.height+y_offset);

  return new Rectangle(p1.x,p1.y,p2.x,p2.y);
 };
};

