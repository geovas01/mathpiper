/** @file
 * Copyright (C) 2004&ndash;5 John D Lamb (J.D.Lamb@btinternet.com)
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
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package jscicalc.pobject;
import jscicalc.AngleType;

/**
 * This is the superclass for trigonometric funtions and their inverses.
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.6 $
 */
public abstract class Trig extends RFunction {
    /**
     * Trig functions are unusual because they require an AngleType. We set the
     * default here.
     * @param angleType AngleType.DEGREES (or possibly AngleType.RADIANS)
     */
    public Trig( AngleType angleType ){
	setScale( angleType );
    }

    /**
     * This function tells us whether we are to work in degrees or radians.
     * @param angleType AngleType.DEGREES or AngleType.RADIANS
     */
    public void setScale( AngleType angleType ){
	switch( angleType ){
    	case RADIANS:
	    scale = 1; 
	    iscale = 1; 
	    break;
	case DEGREES:
	    scale = StrictMath.PI / 180;
	    iscale = 180 / StrictMath.PI;
	    break;
	}
	this.angleType = angleType;
    }
    
    /**
     * Find AngleType
     * @return AngleType
     */
    public final AngleType getAngleType(){
	return angleType;
    }

    /**
     * Used internally to convert to radians before calculating result.
     */
    protected double scale;
    /**
     * Used internally to convert from radians after calculating result.
     */
    protected double iscale;
    /**
     * Use internally to record angle type.
     */
    protected AngleType angleType;
}
