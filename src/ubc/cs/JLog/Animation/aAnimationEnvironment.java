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
//	aAnimationEnvironment
//##################################################################################

package ubc.cs.JLog.Animation;

import java.lang.*;
import java.util.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import ubc.cs.JLog.Applet.iJLogApplBaseServices;

/**
* This represents a graphical environment containing any number of named 
* <code>aAnimationObject</code>s.  These objects are drawn in this graphical
* environment.
*
* @author       Glendon Holst
* @version      %I%, %G%
*/
public class aAnimationEnvironment extends Panel
{
 protected iJLogApplBaseServices 	base;
 protected Vector					objects;
 protected Hashtable				named_objects;
 protected Image					offscreen_buffer = null; 
  
 protected float 			magnification;
 
 protected FloatRectangle	cached_active_bounds_rect = null;

 public 	aAnimationEnvironment(iJLogApplBaseServices b)
 {
  base = b; 

  init();
 };

 public void 		init()
 {
  setBackground(Color.white);
  setForeground(Color.black);
  objects = new Vector();
  named_objects = new Hashtable();
  
  magnification = (float) 1.0;
 };
 
 /**
  * Sets the maginfication of the environment.  The 
  * <code>aAnimationObject</code>s use the value of this attribute to determine
  * their appearance. Changing the magnification sends update notification to the 
  * objects of the environment, which update their display coordinates accordingly.
  * Note: Not all Animation objects may scale as expected (e.g., font size and display).
  *
  * @param m	The magnification level (1.0 is 100% - no magnification, 
  * 			2.0 is 200%, 0.5 is 50%).
  */
 public void 		setMagnification(float m)
 {Enumeration 	e;
 
  magnification = m;

  e = objects.elements();
  
  while (e.hasMoreElements())
   ((aAnimationObject) e.nextElement()).updateMagnification();
 };
 
 public final float 		getMagnification()
 {
  return magnification;
 };
 
 /**
  * Draws the entire environment, including all <code>aAnimationObject</code>s.
  * Drawing is done in the order of the animation object's level (smaller levels
  * are drawn first, larger levels are drawn later).
  *
  * @param g 		The <code>Graphics</code> environment for 
  * 			<code>Panel</code>s' <code>paint</code> function.
  */
 public void 		paint(Graphics g)
 {Enumeration 	e;
 
  e = objects.elements();
  
  while (e.hasMoreElements())
   ((aAnimationObject) e.nextElement()).draw(g);
 };

 /**
  * Enumerate all <code>aAnimationObject</code>s belonging to the environment.
  *
  * @return  		An <code>Enumeration</code> of<code>aAnimationObject</code>s.
  */
 public Enumeration 	enumObjects()
 {
  return objects.elements();
 };
 
 /**
  * Find an <code>aAnimationObject</code> by name.
  *
  * @param name 	The string name for an object in the environment.
  *
  * @return  		An <code>aAnimationObject</code> matching the given name.
  */
 public aAnimationObject 	getObject(String name)
 {
  return (aAnimationObject) named_objects.get(name);
 };
 
 /**
  * Add an <code>aAnimationObject</code>. The object must already be created
  * with this environment being its environment.
  *
  * @param ao 	The <code>aAnimationObject</code> to add.
  */
 public void 			addObject(aAnimationObject ao)
 {int 	pos,max = objects.size();
  
  for (pos = 0; pos < max; pos++)
   if (((aAnimationObject) objects.elementAt(pos)).getLevel() > ao.getLevel())
    break;
    
  objects.insertElementAt(ao,pos);

  named_objects.put(ao.getName(),ao);
 };
 
 /**
  * Remove an <code>aAnimationObject</code>. 
  *
  * @param ao 		The <code>aAnimationObject</code> to remove.
  */
 public void 			removeObject(aAnimationObject ao)
 {
  objects.removeElement(ao);
  named_objects.remove(ao.getName());
  
  // permits retrieval of overlaid objects
  {Enumeration 			e = objects.elements();
   aAnimationObject 	obj;

   while (e.hasMoreElements())
   {
    obj = (aAnimationObject) e.nextElement();
    if (obj.getName().equals(ao.getName()))
    {
     named_objects.put(obj.getName(),obj);
     break;
    }   
   }
  }
 };
 
 public Dimension	getPreferredSize()
 {int				pad = Math.round(2f*magnification) + 10;
  Enumeration		e = objects.elements();
  Rectangle			r = null;
 
  while (e.hasMoreElements())
  {aAnimationObject	aobj = (aAnimationObject) e.nextElement();
   
   if (r == null)
    r = new Rectangle(aobj.getBounds());
   else
    r.add(aobj.getBounds());
  }
 
  if (r == null)
   return new Dimension(pad,pad);

  return new Dimension(r.x+r.width+pad,r.y+r.height+pad);
 };
 
 public Image 		getImage(String name)
 {
  return base.getImage(name);
 };
 
 public void 		update()
 {Graphics	g = getGraphics();
 
  if (g != null)
   update(g);
 };

 public void 		update(Graphics g)
 {Graphics		offscreen_gr;
  Dimension		dim = getSize();
  
  if (offscreen_buffer == null || offscreen_buffer.getWidth(this) != dim.width || 
									offscreen_buffer.getHeight(this) != dim.height)
   offscreen_buffer = createImage(dim.width, dim.height);

  offscreen_gr = offscreen_buffer.getGraphics();
  offscreen_gr.setPaintMode();
  offscreen_gr.setColor(Color.white);
  offscreen_gr.fillRect(0,0,dim.width,dim.width);  

  paint(offscreen_gr);
  
  g.setColor(Color.white);
  g.drawImage(offscreen_buffer,0,0,this);
 };
};