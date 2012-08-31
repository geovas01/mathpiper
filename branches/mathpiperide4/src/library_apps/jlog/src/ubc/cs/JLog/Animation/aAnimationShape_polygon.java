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
//	aAnimationShape Polygon
//##################################################################################

package ubc.cs.JLog.Animation;

import java.lang.*;
import java.util.*;
import java.awt.*;

import ubc.cs.JLog.Terms.*;

public class aAnimationShape_polygon extends aAnimationShape {
    public static final String POLYGON_ATTR_KEY = "polygon";
    public static final String COLOUR_ATTR_KEY = "colour";

    private static jTermTranslation translations;

    static {
	translations = new jTermTranslation();
	setDefaultsForTranslation(translations);
    }

    protected boolean fill;
    protected Color colour;
    protected FloatPolygon original_shape;

    protected FloatPolygon cached_rotshape;
    protected Polygon cached_transhape;

    public aAnimationShape_polygon(aAnimationObject ao, String n,
	    FloatPolygon p, boolean f, Color c) {
	super(ao, n);
	original_shape = p;
	cached_rotshape = null;
	cached_transhape = null;
	fill = f;
	colour = c;
    };

    public void draw(Graphics g) {
	if (cached_rotshape == null) {
	    cached_rotshape = generateRotatedPolygon(original_shape);
	    cached_transhape = generateTranslatedPolygon(cached_rotshape);
	} else if (cached_transhape == null)
	    cached_transhape = generateTranslatedPolygon(cached_rotshape);

	g.setColor(colour);

	if (fill)
	    g.fillPolygon(cached_transhape);

	g.drawPolygon(cached_transhape);
    };

    public Rectangle getBounds() {
	if (cached_rotshape == null) {
	    cached_rotshape = generateRotatedPolygon(original_shape);
	    cached_transhape = generateTranslatedPolygon(cached_rotshape);
	} else if (cached_transhape == null)
	    cached_transhape = generateTranslatedPolygon(cached_rotshape);

	return cached_transhape.getBounds();
    };

    public String getType() {
	return "polygon";
    };

    public void setPolygon(FloatPolygon p) {
	original_shape = p;

	cached_rotshape = null;
	cached_transhape = null;
    };

    public void setLocalPosition(float x, float y) {
	super.setLocalPosition(x, y);
	cached_transhape = null;
    };

    public void setLocalRotation(float r) {
	super.setLocalRotation(r);
	cached_rotshape = null;
	cached_transhape = null;
    };

    public static void setDefaultsForTranslation(jTermTranslation t) {
	aAnimationShape.setDefaultsForTranslation(t);

	setStringKeysForTranslation(t, POLYGON_ATTR_KEY, FloatPolygon.class,
		FloatPolygon.class);
	setStringKeysForTranslation(t, COLOUR_ATTR_KEY, Color.class,
		Color.class);
    };

    public jTermTranslation getTermTranslation() {
	return translations;
    };

    public Hashtable getAttributes() {
	Hashtable ht;

	ht = super.getAttributes();

	ht.put(POLYGON_ATTR_KEY, original_shape);
	ht.put(COLOUR_ATTR_KEY, colour);

	return ht;
    };

    public void setAttributes(Hashtable attributes) {
	super.setAttributes(attributes);

	{
	    FloatPolygon fp;

	    if ((fp = (FloatPolygon) attributes.get(POLYGON_ATTR_KEY)) != null)
		setPolygon(fp);
	}
	{
	    Color c;

	    if ((c = (Color) attributes.get(COLOUR_ATTR_KEY)) != null)
		colour = c;
	}
    };

    public void updatePosition() {
	cached_transhape = null;
    };

    public void updateRotation() {
	cached_rotshape = null;
	cached_transhape = null;
    };

    public void updateMagnification() {
	cached_rotshape = null;
	cached_transhape = null;
    };

    protected FloatPolygon generateRotatedPolygon(FloatPolygon original) {
	FloatPolygon fp = new FloatPolygon(original.npoints);
	float rot = object.getGlobalRotationFromLocal(rotation_offset);
	int i;

	for (i = 0; i < original.npoints; i++) {
	    float xpos = original.xpoints[i];
	    float ypos = original.ypoints[i];
	    float a = (float) (Math.atan2(xpos, ypos) + rot);
	    float d = (float) Math.sqrt(xpos * xpos + ypos * ypos);

	    fp.addPoint((float) Math.sin(a) * d, (float) Math.cos(a) * d);
	}

	return fp;
    };

    protected Polygon generateTranslatedPolygon(FloatPolygon fp) {
	int[] xpts = new int[fp.npoints];
	int[] ypts = new int[fp.npoints];
	int i;

	for (i = 0; i < fp.npoints; i++) {
	    Point p;

	    p = object.getDisplayPointFromLocalPoint(fp.xpoints[i] + x_offset,
		    fp.ypoints[i] + y_offset);

	    xpts[i] = p.x;
	    ypts[i] = p.y;
	}

	return new Polygon(xpts, ypts, fp.npoints);
    };
};
