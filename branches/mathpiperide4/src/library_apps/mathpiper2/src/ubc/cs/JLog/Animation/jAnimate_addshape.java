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
//#########################################################################
//	Animate AddShape
//#########################################################################

package ubc.cs.JLog.Animation;

import java.lang.*;
import java.util.*;
import java.awt.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.*;

public class jAnimate_addshape extends jAnimate {
    public jAnimate_addshape(jTerm t) {
	super(t);
    };

    public String getName() {
	return "animate<addshape>";
    };

    public int getNumberArguments() {
	return 2;
    };

    protected jUnaryBuiltinPredicate duplicate(jTerm r) {
	return new jAnimate_addshape(r);
    };

    public boolean prove(jAnimateGoal ag, aAnimationEnvironment ae) {
	return action(ae, aAttributeTranslation.convertToTerms(ag.term),
		ag.unified);
    };

    protected boolean action(aAnimationEnvironment ae, jTerm[] terms,
	    jUnifiedVector uv) {
	if (terms.length >= getNumberArguments()) {
	    aAnimationObject obj = aAttributeTranslation
		    .convertToAnimationObject(terms[0], ae);
	    jTerm[] shapeinfo = aAttributeTranslation.convertToTerms(terms[1],
		    3);
	    String shpname = aAttributeTranslation
		    .convertToString(shapeinfo[0]);
	    String shptype = aAttributeTranslation
		    .convertToString(shapeinfo[1]);
	    aAnimationShape shaperef;

	    if (shptype.equals("polygon")) {
		jTerm[] shape = aAttributeTranslation.convertToTerms(
			shapeinfo[2], 3);
		FloatPolygon fp = aAttributeTranslation
			.convertToPolygon(shape[0]);
		boolean fill = aAttributeTranslation.convertToBoolean(shape[1]);
		Color fc = aAttributeTranslation.convertToColor(shape[2]);

		obj.addShape(shaperef = new aAnimationShape_polygon(obj,
			shpname, fp, fill, fc));
	    } else if (shptype.equals("line")) {
		jTerm[] shape = aAttributeTranslation.convertToTerms(
			shapeinfo[2], 2);
		FloatRectangle fr = aAttributeTranslation
			.convertToRectangle(shape[0]);
		Color fc = aAttributeTranslation.convertToColor(shape[1]);

		obj.addShape(shaperef = new aAnimationShape_line(obj, shpname,
			fr, fc));
	    } else if (shptype.equals("text")) {
		jTerm[] shape = aAttributeTranslation.convertToTerms(
			shapeinfo[2], 5);
		String text = aAttributeTranslation.convertToString(shape[0]);
		FloatFont f = aAttributeTranslation.convertToFont(shape[1]);
		Color c = aAttributeTranslation.convertToColor(shape[2]);
		float x = aAttributeTranslation.convertToFloat(shape[3], true);
		float y = aAttributeTranslation.convertToInt(shape[4], true);

		obj.addShape(shaperef = new aAnimationShape_text(obj, shpname,
			text, f, c, x, y));
	    } else if (shptype.equals("image")) {
		jTerm[] shape = aAttributeTranslation.convertToTerms(
			shapeinfo[2], 5);
		String img = aAttributeTranslation.convertToString(shape[0]);
		float x = aAttributeTranslation.convertToFloat(shape[1], true);
		float y = aAttributeTranslation.convertToFloat(shape[2], true);
		float w = aAttributeTranslation.convertToFloat(shape[3], true);
		float h = aAttributeTranslation.convertToFloat(shape[4], true);

		obj.addShape(shaperef = new aAnimationShape_image(obj, shpname,
			img, x, y, w, h));
	    } else
		throw new InvalidAnimationAPIException("Unknown shape type.");

	    if (terms.length >= getNumberArguments() + 1) {
		return terms[2].unify(new jObject(shaperef), uv);
	    } else
		return true;
	} else
	    throw new InvalidAnimationAPIException(
		    "Arguments missing for animation predicate.");
    };

    protected void action(aAnimationEnvironment ae, jTerm[] terms) {
	// do nothing
    };
};
