/* XLeftRightArrowFactory.java
 * =========================================================================
 * This file is part of the JLaTeXMath Library - http://jlatexmath.sourceforge.net
 *
 * Copyright (C) 2009 DENIZET Calixte
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * A copy of the GNU General Public License can be found in the file
 * LICENSE.txt provided with the source distribution of this program (see
 * the META-INF directory in the source jar). This license can also be
 * found on the GNU website at http://www.gnu.org/licenses/gpl.html.
 *
 * If you did not receive a copy of the GNU General Public License along
 * with this program, contact the lead developer, or write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 *
 */

package org.scilab.forge.jlatexmath;

import java.awt.Color;

/**
 * Responsible for creating a box containing a delimiter symbol that exists
 * in different sizes.
 */
public class XLeftRightArrowFactory {
    
    private static final Atom MINUS = new TeXFormula("-").root;
    private static final Atom LEFT = new TeXFormula("\\leftarrow").root;
    private static final Atom RIGHT = new TeXFormula("\\rightarrow").root;
    
    public static Box create(boolean left, TeXEnvironment env, float width) {
        TeXFont tf = env.getTeXFont();
        int style = env.getStyle();
	Box arr = left ? LEFT.createBox(env) : RIGHT.createBox(env);
	
	float swidth = arr.getWidth();
	if (width <= swidth) 
	    return arr;

	Box minus = new SmashedAtom(MINUS, "").createBox(env);
	Box kern = new SpaceAtom(TeXConstants.UNIT_MU, -3.4f, 0, 0).createBox(env);
	float mwidth = minus.getWidth() + kern.getWidth();
	swidth += kern.getWidth();
	HorizontalBox hb = new HorizontalBox();
	float w;
	for (w = 0; w < width - swidth - mwidth; w += mwidth) {
	    hb.add(minus);
	    hb.add(kern);
	}

	hb.add(new ScaleAtom(MINUS, (width - swidth - w) / minus.getWidth(), 1).createBox(env));
	
	if (left) {
	    hb.add(0, new SpaceAtom(TeXConstants.UNIT_MU, -2.8f, 0, 0).createBox(env));
	    hb.add(0, arr);
	} else {
	    hb.add(new SpaceAtom(TeXConstants.UNIT_MU, -1.8f, 0, 0).createBox(env));
	    hb.add(arr);
	}

	return hb;
    }
    
    public static Box create(TeXEnvironment env, float width) {
        TeXFont tf = env.getTeXFont();
        int style = env.getStyle();
	Box left = LEFT.createBox(env);
	Box right = RIGHT.createBox(env);
	float swidth = left.getWidth() + right.getWidth();

	if (width < swidth) {
	    HorizontalBox hb = new HorizontalBox(left);
	    hb.add(new StrutBox(-Math.min(swidth - width, left.getWidth()), 0, 0, 0));
	    hb.add(right);
	    return hb;
	}

	Box minus = new SmashedAtom(MINUS, "").createBox(env);
	Box kern = new SpaceAtom(TeXConstants.UNIT_MU, -3.4f, 0, 0).createBox(env);
	float mwidth = minus.getWidth() + kern.getWidth();
	swidth += 2 * kern.getWidth();
	
	HorizontalBox hb = new HorizontalBox();
	float w;
	for (w = 0; w < width - swidth - mwidth; w += mwidth) {
	    hb.add(minus);
	    hb.add(kern);
	}
	
	hb.add(new ScaleBox(minus, (width - swidth - w) / minus.getWidth(), 1));
	
	hb.add(0, kern);
	hb.add(0, left);
	hb.add(kern);
	hb.add(right);

	return hb;
    }
}
