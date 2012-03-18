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
//	aAttributeTranslation
//##################################################################################

package ubc.cs.JLog.Animation;

import java.lang.*;
import java.util.*;
import java.awt.*;

import ubc.cs.JLog.Foundation.iAttributes;
import ubc.cs.JLog.Terms.*;

/**
 * The base class for animation objects and shapes. It provides a standard
 * foundation for attributes and translation between terms and objects.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public abstract class aAttributeTranslation implements iAttributes {
    private static jTermTranslation translations;

    static {
	translations = new jTermTranslation();
	setDefaultsForTranslation(translations);
    }

    /**
     * Get the current translation unit for this object. Usually, it will get a
     * translation unit for the class. The returned unit should not be modified
     * (since it belongs to the class).
     * 
     * @return The <code>jTermTranslation</code> translation unit.
     */
    public jTermTranslation getTermTranslation() {
	return translations;
    };

    public static void setDefaultsForTranslation(jTermTranslation t) {
	t.setDefaults();

	setObjectDefaults(t);
	setTermDefaults(t);
    };

    /**
     * Registers existing converters with a new name. The existing converters
     * are referenced via their keys.
     * 
     * @param t
     *            The <code>jTermTranslation</code> unit to register to.
     * @param skey
     *            The string key to use (is used for both object->term and
     *            term->object conversions.
     * @param okey
     *            An existing TermToObject key.
     * @param tkey
     *            An existing ObjectToTerm key.
     */
    public static void setStringKeysForTranslation(jTermTranslation t,
	    String skey, Object okey, Object tkey) {
	t.RegisterTermToObjectConverter(skey, t.getTermToObjectConverter(okey));
	t.RegisterObjectToTermConverter(skey, t.getObjectToTermConverter(tkey));
    };

    protected static void setObjectDefaults(jTermTranslation t) {
	// this converter creates a Color.
	{
	    iTermToObject c;

	    c = new iTermToObject() {
		public Object createObjectFromTerm(jTerm term) {
		    return convertToColor(term);
		}
	    };

	    t.RegisterTermToObjectConverter(Color.class, c);
	}
	// this converter creates a Font.
	{
	    iTermToObject c;

	    c = new iTermToObject() {
		public Object createObjectFromTerm(jTerm term) {
		    return convertToFont(term);
		}
	    };

	    t.RegisterTermToObjectConverter(FloatFont.class, c);
	}
	// this converter creates a FloatPolygon.
	{
	    iTermToObject c;

	    c = new iTermToObject() {
		public Object createObjectFromTerm(jTerm term) {
		    return convertToPolygon(term);
		}
	    };

	    t.RegisterTermToObjectConverter(FloatPolygon.class, c);
	}
	// this converter creates a FloatRectangle.
	{
	    iTermToObject c;

	    c = new iTermToObject() {
		public Object createObjectFromTerm(jTerm term) {
		    return convertToRectangle(term);
		}
	    };

	    t.RegisterTermToObjectConverter(FloatRectangle.class, c);
	}
    };

    protected static void setTermDefaults(jTermTranslation t) {
	// this converter creates an rgb() predicate, or name color atom from a
	// Color.
	{
	    iObjectToTerm c;

	    c = new iObjectToTerm() {
		public jTerm createTermFromObject(Object obj) {
		    if (obj instanceof Color)
			return convertFromColor((Color) obj);

		    throw new TranslationFailureException(
			    "Expected Color object.");
		}
	    };

	    t.RegisterObjectToTermConverter(Color.class, c);
	}
	// this converter creates a list with font information from a Font.
	{
	    iObjectToTerm c;

	    c = new iObjectToTerm() {
		public jTerm createTermFromObject(Object obj) {
		    if (obj instanceof FloatFont)
			return convertFromFont((FloatFont) obj);

		    throw new TranslationFailureException(
			    "Expected Font object.");
		}
	    };

	    t.RegisterObjectToTermConverter(FloatFont.class, c);
	}
	// this converter creates a list of real pairs from a FloatPolygon.
	{
	    iObjectToTerm c;

	    c = new iObjectToTerm() {
		public jTerm createTermFromObject(Object obj) {
		    if (obj instanceof FloatPolygon)
			return convertFromPolygon((FloatPolygon) obj);

		    throw new TranslationFailureException(
			    "Expected Polygon object.");
		}
	    };

	    t.RegisterObjectToTermConverter(FloatPolygon.class, c);
	}
	// this converter creates a list of real pairs from a FloatRectangle.
	{
	    iObjectToTerm c;

	    c = new iObjectToTerm() {
		public jTerm createTermFromObject(Object obj) {
		    if (obj instanceof FloatRectangle)
			return convertFromRectangle((FloatRectangle) obj);

		    throw new TranslationFailureException(
			    "Expected Rectangle object.");
		}
	    };

	    t.RegisterObjectToTermConverter(FloatRectangle.class, c);
	}
    };

    public static jTerm[] convertToTerms(jTerm t) {
	int i;
	Vector arguments = new Vector();

	t = t.getTerm();

	while (t instanceof jListPair) {
	    jListPair lst = (jListPair) t;

	    arguments.addElement(lst.getHead().getTerm());

	    t = lst.getTail().getTerm();
	}
	{
	    jTerm[] terms = new jTerm[arguments.size()];

	    arguments.copyInto(terms);
	    return terms;
	    // FIX: Preferred version for Java 1.2 and up
	    // return (jTerm[]) arguments.toArray(new jTerm[arguments.size()]);
	}
    };

    public static jTerm[] convertToTerms(jTerm t, int args) {
	int i;
	jTerm[] arguments = new jTerm[args];

	t = t.getTerm();

	for (i = 0; i < args; i++) {
	    jListPair lst;

	    if (t instanceof jListPair) {
		lst = (jListPair) t;
		arguments[i] = lst.getHead().getTerm();

		t = lst.getTail().getTerm();
	    } else
		throw new InvalidAnimationAPIException(
			"Arguments missing for animation predicate.");
	}
	return arguments;
    };

    public static jList convertToList(jTerm t) {
	if (t instanceof jList)
	    return (jList) t;

	throw new InvalidAnimationAPIException(
		"Excpected list in animation argument.");
    };

    public static float convertToFloat(jTerm t, boolean promote) {
	jTerm tx = t.getValue();

	if (tx instanceof jReal)
	    return ((jReal) tx).getRealValue();

	if ((tx instanceof jInteger) && promote)
	    return ((jInteger) tx).getIntegerValue();

	throw new InvalidAnimationAPIException("Expected "
		+ (promote ? "number" : "real")
		+ " in animation predicate argument.");
    };

    public static int convertToInt(jTerm t, boolean demote) {
	jTerm tx = t.getValue();

	if (tx instanceof jInteger)
	    return ((jInteger) tx).getIntegerValue();

	if ((tx instanceof jReal) && demote)
	    return (int) ((jReal) tx).getRealValue();

	throw new InvalidAnimationAPIException("Expected "
		+ (demote ? "number" : "integer")
		+ " in animation predicate argument.");
    };

    public static String convertToString(jTerm t) {
	return t.getName();
    };

    public static boolean convertToBoolean(jTerm t) {
	if (t.getName().equals("true"))
	    return true;
	if (t.getName().equals("false"))
	    return false;

	throw new InvalidAnimationAPIException(
		"Expected boolean value in animation predicate.");
    };

    public static Color convertToColor(jTerm t) {
	if (t instanceof jPredicate) {
	    jPredicate p = (jPredicate) t;
	    jCompoundTerm ct = p.getArguments();

	    if (p.getArity() == 3 && p.getName().equals("rgb")) {
		int r = convertToInt(ct.elementAt(0), true);
		int g = convertToInt(ct.elementAt(1), true);
		int b = convertToInt(ct.elementAt(2), true);

		return new Color(r, g, b);
	    } else
		throw new InvalidAnimationAPIException(
			"Expected rgb/3 color value predicate.");
	}

	if (t.getName().equals("black"))
	    return Color.black;
	if (t.getName().equals("blue"))
	    return Color.blue;
	if (t.getName().equals("cyan"))
	    return Color.cyan;
	if (t.getName().equals("darkgray"))
	    return Color.darkGray;
	if (t.getName().equals("gray"))
	    return Color.gray;
	if (t.getName().equals("green"))
	    return Color.green;
	if (t.getName().equals("lightgray"))
	    return Color.lightGray;
	if (t.getName().equals("magenta"))
	    return Color.magenta;
	if (t.getName().equals("orange"))
	    return Color.orange;
	if (t.getName().equals("pink"))
	    return Color.pink;
	if (t.getName().equals("red"))
	    return Color.red;
	if (t.getName().equals("white"))
	    return Color.white;
	if (t.getName().equals("yellow"))
	    return Color.yellow;

	throw new InvalidAnimationAPIException(
		"Expected color value in animation predicate.");
    };

    public static jTerm convertFromColor(Color c) {
	if (c == Color.black)
	    return new jAtom("black");
	if (c == Color.blue)
	    return new jAtom("blue");
	if (c == Color.cyan)
	    return new jAtom("cyan");
	if (c == Color.darkGray)
	    return new jAtom("darkgray");
	if (c == Color.gray)
	    return new jAtom("gray");
	if (c == Color.green)
	    return new jAtom("green");
	if (c == Color.lightGray)
	    return new jAtom("lightgray");
	if (c == Color.magenta)
	    return new jAtom("magenta");
	if (c == Color.orange)
	    return new jAtom("orange");
	if (c == Color.pink)
	    return new jAtom("pink");
	if (c == Color.red)
	    return new jAtom("red");
	if (c == Color.white)
	    return new jAtom("white");
	if (c == Color.yellow)
	    return new jAtom("yellow");

	// create rgb/3 predicate
	{
	    jCompoundTerm ct = new jCompoundTerm(3);

	    ct.addTerm(new jInteger(c.getRed()));
	    ct.addTerm(new jInteger(c.getGreen()));
	    ct.addTerm(new jInteger(c.getBlue()));

	    return new jPredicate("rgb", ct);
	}
    };

    public static FloatFont convertToFont(jTerm t) {
	jTerm[] terms = convertToTerms(t, 3);
	String name = convertToString(terms[0]);
	String style = convertToString(terms[1]);
	float size = convertToFloat(terms[2], true);
	int stylenum;

	if (style.equals("bold"))
	    stylenum = Font.BOLD;
	else if (style.equals("italic"))
	    stylenum = Font.ITALIC;
	else if (style.equals("plain"))
	    stylenum = Font.PLAIN;
	else
	    throw new InvalidAnimationAPIException(
		    "Expected font style value in animation predicate.");

	return new FloatFont(new Font(name, stylenum, Math.round(size)), size);
    };

    public static jTerm convertFromFont(FloatFont f) {
	String name = f.font.getName();
	String style = null;
	float size = f.size;

	if (f.font.getStyle() == Font.BOLD)
	    style = "bold";
	else if (f.font.getStyle() == Font.ITALIC)
	    style = "italic";
	else if (f.font.getStyle() == Font.PLAIN)
	    style = "plain";
	else
	    throw new InvalidAnimationAPIException(
		    "Expected font style value in animation predicate.");

	return new jListPair(new jAtom(name), new jListPair(new jAtom(style),
		new jListPair(new jReal(size), jNullList.NULL_LIST)));
    };

    public static aAnimationObject convertToAnimationObject(jTerm t,
	    aAnimationEnvironment ae) {
	aAnimationObject obj = null;

	if (t.type == jTerm.TYPE_OBJECT) {
	    Object aobj = ((jObject) t).getObjectReference(); // cast depends
							      // upon correct
							      // type

	    if (aobj instanceof aAnimationObject)
		return (aAnimationObject) aobj;
	}

	{
	    String name = convertToString(t);

	    if ((obj = ae.getObject(name)) == null)
		throw new InvalidAnimationAPIException("Animation object <"
			+ name + "> does not exist.");
	}

	return obj;
    };

    public static aAnimationShape convertToAnimationShape(jTerm t) {
	if (t.type == jTerm.TYPE_OBJECT) {
	    Object aobj = ((jObject) t).getObjectReference(); // cast depends
							      // upon correct
							      // type

	    if (aobj instanceof aAnimationShape)
		return (aAnimationShape) aobj;
	}

	throw new InvalidAnimationAPIException(
		"Shape reference missing in animation predicate.");
    };

    public static aAttributeTranslation convertToAttributesObject(jTerm t) {
	if (t.type == jTerm.TYPE_OBJECT) {
	    Object aobj = ((jObject) t).getObjectReference(); // cast depends
							      // upon correct
							      // type

	    if (aobj instanceof aAttributeTranslation)
		return (aAttributeTranslation) aobj;
	}

	throw new InvalidAnimationAPIException(
		"object reference missing in animation predicate.");
    };

    public static FloatPolygon convertToPolygon(jTerm t) {
	FloatPolygon fp = new FloatPolygon();

	while (t instanceof jListPair) {
	    jListPair lst = (jListPair) t;
	    jTerm arg = lst.getHead().getTerm();

	    if (arg instanceof jCons) {
		jCons carg = (jCons) arg;

		fp.addPoint(convertToFloat(carg.getLHS(), true),
			convertToFloat(carg.getRHS(), true));
	    } else
		throw new InvalidAnimationAPIException(
			"Arguments missing for animation predicate.");

	    t = lst.getTail().getTerm();
	}

	return fp;
    };

    public static jTerm convertFromPolygon(FloatPolygon p) {
	jListPair head = null;
	jListPair next = head;
	int i;

	for (i = 0; i < p.npoints; i++) {
	    jTerm t = new jCons(new jReal(p.xpoints[i]),
		    new jReal(p.ypoints[i]));
	    jListPair prev = next;

	    next = new jListPair(t, jNullList.NULL_LIST);

	    if (prev != null)
		prev.setTail(next);
	    else
		head = next;
	}

	if (head != null)
	    return head;
	else
	    return jNullList.NULL_LIST;
    };

    public static FloatRectangle convertToRectangle(jTerm t) {
	FloatPolygon fp = convertToPolygon(t);

	if (fp.npoints < 2)
	    throw new InvalidAnimationAPIException(
		    "Arguments missing for animation predicate.");
	if (fp.npoints > 2)
	    throw new InvalidAnimationAPIException(
		    "Too many arguments for animation predicate.");

	return new FloatRectangle(fp.xpoints[0], fp.ypoints[0], fp.xpoints[1],
		fp.ypoints[1]);
    };

    public static jTerm convertFromRectangle(FloatRectangle r) {
	return new jListPair(new jCons(new jReal(r.x), new jReal(r.y)),
		new jListPair(
			new jCons(new jReal(r.width), new jReal(r.height)),
			jNullList.NULL_LIST));
    };
};