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
//	aAnimationObject
//##################################################################################

package ubc.cs.JLog.Animation;

import java.lang.*;
import java.util.*;
import java.awt.*;

import ubc.cs.JLog.Terms.*;

/**
* This represents a graphical object which is composed from possibly several
* collection of <code>aAnimationShape</code>s.  Each collection is a particular
* view of that object (multiple views permit animation). An object also has a
* level attribute that determines its order when displayed in the environment
* (to determine overlap of the object's image).  Animation objects can also
* keep track of the path they have traversed.  Objects are not themselves visible
* (the enclosed shapes give the visible manifestation), but they do have coordinates
* and rotation attributes.  Coordinates are given the canonical global coordinates (i.e.,
* with magnification of 1.0) and rotation of an object is given in radians.  Enclosed 
* shapes have local canonical coordinates and rotations which are offsets from the object
* values.  A display point (non-canonical) is the global point with magnifcation applied. 
*
* @author       Glendon Holst
* @version      %I%, %G%
*/
public class aAnimationObject extends aAttributeTranslation
{
 public static final String			X_ATTR_KEY = "x";
 public static final String			Y_ATTR_KEY = "y";
 public static final String			ROTATION_ATTR_KEY = "rotation";
 public static final String			NAME_ATTR_KEY = "name";
 public static final String			LEVEL_ATTR_KEY = "level";
 public static final String			VIEW_ATTR_KEY = "view";
 public static final String			PATHSIZE_ATTR_KEY = "path_size";
 public static final String			PATHSKIP_ATTR_KEY = "path_skip";
 public static final String			PATHCOLOUR_ATTR_KEY = "path_colour";

 private static jTermTranslation		translations;
 
 static {
  translations = new jTermTranslation();
  setDefaultsForTranslation(translations);
 }

 protected aAnimationEnvironment	environment;
 protected int 						level,view;
 protected String 					name;

 protected Vector					path;
 protected int 						path_size = 0;
 protected int 						path_skip = 0,path_skipcount = 0;
 protected Color 					path_colour;
 
 protected Vector[] 				shapes;
 protected Hashtable[] 				named_shapes;

 protected float					x,y;
 protected float 					rotation;

 /**
  * The constructor.  Requires that the <code>aAnimationEnvironment</code>, 
  * name, level, and the maximum views.
  *
  * @param ae 		The graphical environment that encloses this object.
  * 			Animation object can only belong to one environment.
  * @param n 		The name of the object.
  * @param l 		The level at which this object floats in the environment.
  * 			Lower numbers are deeper in environment (they are drawn
  * 			first), while larger numbers appear towards the top of the
  * 			environment (drawn last).
  * @param vs 		The maximum number of views the object will have. Each view
  * 			must be populated with <code>aAnimationShape</code>s.
  */
 public 	aAnimationObject(aAnimationEnvironment ae,String n,int l,int vs)
 {
  environment = ae;
  name = n;
  level = l;
  view = 0;
 
  path = new Vector();
  path_size = 0;
  path_skip = 0;
  path_skipcount = 0;
  path_colour = Color.gray;
  
  vs = (vs <= 1 ? 1 : vs);
  {int 			i;
  
   shapes = new Vector[vs];
   named_shapes = new Hashtable[vs];

   for (i = 0; i < vs; i++)
   {
    shapes[i] = new Vector();
    named_shapes[i] = new Hashtable();
   }
  }
  
  x = (float) 0.0;
  y = (float) 0.0;
  rotation = (float) 0.0;
 };

 public aAnimationEnvironment 		getEnvironment()
 {
  return environment;
 };

 /**
  * Get a shape in the current view with the given name.
  *
  * @param name 	The name of the shape.
  * @return 		An <code>aAnimationShape</code> from the current view,
  * 			with the given name.
  */
 public aAnimationShape 	getShape(String name)
 {
  return (aAnimationShape) named_shapes[view].get(name);
 };

 /**
  * Get an Enumeration of the shapes in the current view.
  *
  * @return 		An enumeration of the enclosed <code>aAnimationShape</code> shape 
  *					object from the current view.
  */
 public Enumeration 	enumShapes()
 {
  return named_shapes[view].elements();
 };
 
 /**
  * Add an <code>aAnimationShape</code>. The shape must already be created
  * with this object being its parent object.  Shape is added to the current view.
  *
  * @param as 	The <code>aAnimationShape</code> to add.
  */
 public void 			addShape(aAnimationShape as)
 {
  shapes[view].addElement(as);
  named_shapes[view].put(as.getName(),as);
 };
 
 /**
  * Remove an <code>aAnimationShape</code> from the current view. 
  *
  * @param as 		The <code>aAnimationShape</code> to remove.
  */
 public void 			removeShape(aAnimationShape as)
 {
  shapes[view].removeElement(as);
  named_shapes[view].remove(as.getName());
  
  // permits retrieval of overlaid shapes, adds any remaining shape with the same name
  // to the named_shapes hashtable
  {Enumeration 			e = shapes[view].elements();
   aAnimationShape 		shp;

   while (e.hasMoreElements())
   {
    shp = (aAnimationShape) e.nextElement();
    if (shp.getName().equals(as.getName()))
    {
     named_shapes[view].put(shp.getName(),shp);
     break;
    }   
   }
  }
 };
 
 /**
  * Draw the current view of the object in the <code>Graphics</code> environment
  * provided. Includes any path that the object has recorded.
  *
  * @param g 		The <code>Graphics</code> environment to draw into.
  */
 public void 		draw(Graphics g)
 {Enumeration 	e;
  
  // draw path
  {float			mag = environment.getMagnification();
   
   g.setColor(path_colour);
   e = path.elements();
   
   while (e.hasMoreElements())
   {Point 			p;
    int				px,py;
	
    p = (Point) e.nextElement();
    
	px = Math.round(p.x*mag);
	py = Math.round(p.y*mag);
	
	g.drawLine(px,py,px,py);
   }
  }

  {
   e = shapes[view].elements();
  
   while (e.hasMoreElements())
    ((aAnimationShape) e.nextElement()).draw(g);
  }
 };
 
 /**
  * Returns the bounding rectangle for this object in the current view, 
  * given in global coordinates. 
  *
  * @return 	The <code>Rectangle</code> bounding this object.
  */
 public Rectangle	getBounds()
 {Enumeration	e = shapes[view].elements();
  Rectangle		r = null;
  
  while (e.hasMoreElements())
  {aAnimationShape		as = (aAnimationShape) e.nextElement();

   if (r == null)
    r = new Rectangle(as.getBounds());
   else
    r.add(as.getBounds());
  }
  
  if (r == null)
   r = new Rectangle();
   
  return r; 
 };
 
 public String 		getName()
 {
  return name;
 };
 
 public void 		setName(String n)
 {
  environment.removeObject(this);
  name = n;
  environment.addObject(this);
 };
 
 public int 		getLevel()
 {
  return level;
 };

 /**
  * Set the object level in the display heirarchy.
  *
  * @param l 		The level at which this object floats in the environment.
  * 			Lower numbers are deeper in environment (they are drawn
  * 			first), while larger numbers appear towards the top of the
  * 			environment (drawn last).
  */
 public void 		setLevel(int l)
 {
  environment.removeObject(this);
  level = l;
  environment.addObject(this);
 };


 /**
  * Get the current display view.
  *
  * @return 		The view number (zero based).
  */
 public int 		getView()
 {
  return view;
 };
 
 /**
  * Get the view containing the given shape.
  *
  * @param s		The shape, which this object should contain.
  * @return 		The view number (zero based) for the given shape, or the current
  *					view if shape not found.
  */
 public int 		getShapeView(aAnimationShape s)
 {int	i;
 
  for (i=0; i < shapes.length; i++)
  {
   if (shapes[i].contains(s))
    return i;
  }
  
  return view;
 };
 
 /**
  * Set the current view to display.  Does not cause the environment to re-paint.
  *
  * @param v 		The view number (zero based).
  */
 public void 		setView(int v)
 {
  view = (v < 0 ? 0 : (v >= shapes.length ? shapes.length - 1 : v));
 };
 
 public static void		setDefaultsForTranslation(jTermTranslation t)
 {
  aAttributeTranslation.setDefaultsForTranslation(t);
  
  setStringKeysForTranslation(t,X_ATTR_KEY,jReal.class,Float.class);
  setStringKeysForTranslation(t,Y_ATTR_KEY,jReal.class,Float.class);
  setStringKeysForTranslation(t,ROTATION_ATTR_KEY,jReal.class,Float.class);
  setStringKeysForTranslation(t,NAME_ATTR_KEY,jAtom.class,String.class);
  setStringKeysForTranslation(t,LEVEL_ATTR_KEY,jInteger.class,Integer.class);
  setStringKeysForTranslation(t,VIEW_ATTR_KEY,jInteger.class,Integer.class);
  setStringKeysForTranslation(t,PATHSIZE_ATTR_KEY,jInteger.class,Integer.class);
  setStringKeysForTranslation(t,PATHSKIP_ATTR_KEY,jInteger.class,Integer.class);
  setStringKeysForTranslation(t,PATHCOLOUR_ATTR_KEY,Color.class,Color.class);
 };

 public jTermTranslation	getTermTranslation()
 {
  return translations;
 };

 public Hashtable   getAttributes()
 {Hashtable		ht = new Hashtable(9);
 
  ht.put(X_ATTR_KEY,new Float(x));
  ht.put(Y_ATTR_KEY,new Float(y));
  ht.put(ROTATION_ATTR_KEY,new Float(rotation));
  ht.put(NAME_ATTR_KEY,name);    
  ht.put(LEVEL_ATTR_KEY,new Integer(level));
  ht.put(VIEW_ATTR_KEY,new Integer(view));
  ht.put(PATHSIZE_ATTR_KEY,new Integer(path_size)); 
  ht.put(PATHSKIP_ATTR_KEY,new Integer(path_skip));
  ht.put(PATHCOLOUR_ATTR_KEY,path_colour);

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
    setPosition((fx != null ? fx.floatValue() : x),(fy != null ? fy.floatValue() : y));
  }
  // set rotation  
  {Float		r;
  
   if ((r = (Float) attributes.get(ROTATION_ATTR_KEY)) != null)
    setRotation(r.floatValue());
  }
  // set level
  {Integer		l;
  
   if ((l = (Integer) attributes.get(LEVEL_ATTR_KEY)) != null)
    setLevel(l.intValue());
  }
  // set view
  {Integer		v;
  
   if ((v = (Integer) attributes.get(VIEW_ATTR_KEY)) != null)
    setView(v.intValue());
  }
  // set path attributes
  {Integer		psz, psk;
   Color		c;
  
   psz = (Integer) attributes.get(PATHSIZE_ATTR_KEY);
   psk = (Integer) attributes.get(PATHSKIP_ATTR_KEY);
   c = (Color) attributes.get(PATHCOLOUR_ATTR_KEY);

   if (psz != null || psk != null || c != null)
    setPathSize((psz != null ? psz.intValue() : path_size),
			    (psk != null ? psk.intValue() : path_skip),
			    (c != null ? c : path_colour));
  }
 };
 
 /**
  * Calculates the display coordinates for given local (i.e., shape relative) point and
  * rotation.  The display coordinate is the global coordinate after magnification.
  *
  * @param dx 		Local (shape relative) x position offset from the objects position.
  * @param dy 		Local (shape relative) y position offset from the objects position.
  * @param dr		Local (shape relative) rotation offset.
  */
 public final Point		getDisplayPointFromLocalPosition(float dx,float dy,float dr)
 {float		gx = x + dx;
  float		gy = y + dy;
  float		gr = rotation + dr;
  float 	a = (float) (Math.atan2(gx,gy) + gr);
  float 	d = (float) Math.sqrt(gx*gx + gy*gy);
  float		m = environment.getMagnification();
  
  return new Point(Math.round((float) Math.sin(a)*d*m),Math.round((float) Math.cos(a)*d*m)); 
 };

 /**
  * Calculates the display coordinates for given local (i.e., shape relative) point.
  * The display coordinate is the global coordinate after magnification.
  *
  * @param dx 		Local (shape relative) x position offset from the objects position.
  * @param dy 		Local (shape relative) y position offset from the objects position.
  */
 public final Point		getDisplayPointFromLocalPoint(float dx,float dy)
 {float		gx = x + dx;
  float		gy = y + dy;
  float		m = environment.getMagnification();
  
  return new Point(Math.round(gx*m),Math.round(gy*m)); 
 };
 
 public final float		getGlobalRotationFromLocal(float dr)
 {
  return rotation + dr;
 };

 public final float		getGlobalXPositionFromLocal(float dx)
 {
  return x + dx;
 };

 public final float		getGlobalYPositionFromLocal(float dy)
 {
  return y + dy;
 };
 
 /**
  * Set the path attributes. The path is a history of previous locations for this
  * object. The path is drawn, unless no historical locations are recorded.
  *
  * @param sz 		The number of previous locations to keep track of.
  * @param sk 		The number of previous locations skip over before recording
  * 			a location (used to prevent sampling every location).
  * @param c 		The colour to draw the path in.
  */
 public void 		setPathSize(int sz,int sk,Color c)
 {
  path_size = sz;
  path_colour = c;
  if (path_skip != sk)
  {
   path_skip = sk;
   path_skipcount = 0;
  }
  
  if (path_size == 0)
   path.removeAllElements();
  else if (path_size > 0)
  {
   while (path.size() > path_size)
    path.removeElementAt(0);
  }
 };
 
 /**
  * Get the size of the path.
  *
  * @param allowed 	true if we want the maximum number of previous locations
  * 			allowed, false if we just want to know how many locations
  * 			are already recorded in the path.
  * @return 		If <code>allowed</code> is true, then returns the maximum
  * 			number of previous locations to record, otherwise returns 
  * 			the number of previous locations currently recorded.
  */
 public int 		getPathSize(boolean allowed)
 {
  return (allowed ? path_size : path.size());
 };
 
 /**
  * Add a location to the path. Invoked internally when the object moves.
  *
  * @param x 		The x component of the location.
  * @param y 		The y component of the location.
  */
 protected void 	addPath(float x,float y)
 {
  if (path_size == 0)
   return;
   
  path.addElement(new Point(Math.round(x),Math.round(y)));
  
  if (path_size >= 0 && path.size() > path_size)
   path.removeElementAt(0);
 };
 
 /**
  * Adjust the global location of the object to the given position. Moves the object.
  * Notifies child shapes that the object position has changed.
  *
  * @param x 		The x component of the location.
  * @param y 		The y component of the location.
  */
 public void 		setPosition(float x,float y)
 {int 		v;
  
  this.x = x;
  this.y = y;
  
  for (v = 0; v < shapes.length; v++)
  {Enumeration 		e = shapes[v].elements();
  
   while (e.hasMoreElements())
    ((aAnimationShape) e.nextElement()).updatePosition();
  }
 
  if (--path_skipcount < 0)
  {
   path_skipcount = path_skip;
   addPath(this.x,this.y);
  }
 };
 
 /**
  * Adjust the rotation of the object to the given angle in radians. 
  * Adjusts the real location of the child shapes by the given rotation.
  *
  * @param r 		The angle of the object in radians.
  */
 public void 		setRotation(float r)
 {int 			v;

  rotation = r;
 
  for (v = 0; v < shapes.length; v++)
  {Enumeration 		e = shapes[v].elements();
  
   while (e.hasMoreElements())
    ((aAnimationShape) e.nextElement()).updateRotation();
  }
 };

 /**
 * Update notification.  The environment magnification value changed.
 */
 public void 		updateMagnification()
 {int		v;
 
  for (v = 0; v < shapes.length; v++)
  {Enumeration 			e = shapes[v].elements();

   while (e.hasMoreElements())
    ((aAnimationShape) e.nextElement()).updateMagnification();
  }
 }; 
};