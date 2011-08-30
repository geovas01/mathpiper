
package org.mathpiper.ui.gui.worksheets.symbolboxes;

import java.awt.Dimension;


public class Bounds {


    public double top;

    public double bottom;

    public double left;

    public double right;




    public Bounds() {
	this(0, 0, 0, 0);
    }


    public Bounds(double top, double bottom, double left, double right) {
	this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public double getBottom() {
        return bottom;
    }

    public void setBottom(double bottom) {
        this.bottom = bottom;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getRight() {
        return right;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public double getTop() {
        return top;
    }

    public void setTop(double top) {
        this.top = top;
    }


    public Dimension  getScaledDimension(double scale)
    {
        return new Dimension((int)( (right - left)*scale), (int)( (bottom - top)*scale) );
    }


    public String toString() {
	return "[top=" + top + ",bottom=" + bottom + ",left=" + left + ",right=" + right + "]";
    }
}