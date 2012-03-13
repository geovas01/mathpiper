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
//	aAnimationShape Text
//##################################################################################

package ubc.cs.JLog.Animation;

import java.lang.*;
import java.util.*;
import java.awt.*;

import ubc.cs.JLog.Terms.*;

public class aAnimationShape_text extends aAnimationShape {
    public static final String TEXT_ATTR_KEY = "text";
    public static final String FONT_ATTR_KEY = "font";
    public static final String COLOUR_ATTR_KEY = "colour";

    private static jTermTranslation translations;

    static {
	translations = new jTermTranslation();
	setDefaultsForTranslation(translations);
    }

    protected String text;
    protected float font_size;
    protected Color colour;
    protected Font original_font;

    protected Font cached_font;

    public aAnimationShape_text(aAnimationObject ao, String n, String t,
	    FloatFont f, Color c, float x, float y) {
	super(ao, n);
	text = t;
	original_font = f.font;
	cached_font = null;
	colour = c;
	x_offset = x;
	y_offset = y;
	font_size = f.size;
    };

    public void updateMagnification() {
	cached_font = null;
    };

    public void draw(Graphics g) {
	Point p = object.getDisplayPointFromLocalPosition(x_offset, y_offset,
		rotation_offset);

	if (cached_font == null)
	    cached_font = generateSizedFont(original_font);

	g.setColor(colour);
	g.setFont(cached_font);

	g.drawString(text, p.x, p.y);
    };

    public Rectangle getBounds() {
	Point p = object.getDisplayPointFromLocalPosition(x_offset, y_offset,
		rotation_offset);
	FontMetrics fm;

	if (cached_font == null)
	    cached_font = generateSizedFont(original_font);

	fm = getEnclosingObject().getEnvironment().getFontMetrics(cached_font);

	return new Rectangle(p, new Dimension(fm.stringWidth(text),
		fm.getHeight()));
    };

    public String getType() {
	return "text";
    };

    public static void setDefaultsForTranslation(jTermTranslation t) {
	aAnimationShape.setDefaultsForTranslation(t);

	setStringKeysForTranslation(t, TEXT_ATTR_KEY, jAtom.class, String.class);
	setStringKeysForTranslation(t, FONT_ATTR_KEY, FloatFont.class,
		FloatFont.class);
	setStringKeysForTranslation(t, COLOUR_ATTR_KEY, Color.class,
		Color.class);
    };

    public jTermTranslation getTermTranslation() {
	return translations;
    };

    public Hashtable getAttributes() {
	Hashtable ht;

	ht = super.getAttributes();

	ht.put(TEXT_ATTR_KEY, text);
	ht.put(FONT_ATTR_KEY, new FloatFont(original_font, font_size));
	ht.put(COLOUR_ATTR_KEY, colour);

	return ht;
    };

    public void setAttributes(Hashtable attributes) {
	super.setAttributes(attributes);

	{
	    String t;

	    if ((t = (String) attributes.get(TEXT_ATTR_KEY)) != null)
		text = t;
	}
	{
	    FloatFont f;

	    if ((f = (FloatFont) attributes.get(FONT_ATTR_KEY)) != null) {
		original_font = f.font;
		font_size = f.size;
	    }
	}
	{
	    Color c;

	    if ((c = (Color) attributes.get(COLOUR_ATTR_KEY)) != null)
		colour = c;
	}
    };

    protected Font generateSizedFont(Font of) {
	float mag = object.getEnvironment().getMagnification();

	return new Font(of.getName(), of.getStyle(),
		Math.round(font_size * mag));
    };
};
