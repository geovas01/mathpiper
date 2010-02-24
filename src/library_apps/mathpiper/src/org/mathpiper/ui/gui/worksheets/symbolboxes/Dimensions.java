
package org.mathpiper.ui.gui.worksheets.symbolboxes;


public class Dimensions {


    public double width;


    public double height;




    public Dimensions() {
	this(0, 0);
    }


    public Dimensions(Dimensions d) {
	this(d.width, d.height);
    }


    public Dimensions(double width, double height) {
	this.width = width;
	this.height = height;
    }


    public double getWidth() {
	return width;
    }


    public double getHeight() {
	return height;
    }


    public void setSize(double width, double height) {
	this.width = width;
	this.height = height;
    }


    public Dimensions getSize() {
	return new Dimensions(width, height);
    }


    public void setSize(Dimensions d) {
	setSize(d.width, d.height);
    }


    public void setSize(int width, int height) {
    	this.width = width;
    	this.height = height;
    }


    public boolean equals(Object obj) {
	if (obj instanceof Dimensions) {
	    Dimensions d = (Dimensions)obj;
	    return (width == d.width) && (height == d.height);
	}
	return false;
    }


    public int hashCode() {
        double sum = width + height;
        return (int) (sum * (sum + 1)/2 + width);
    }


    public String toString() {
	return getClass().getName() + "[width=" + width + ",height=" + height + "]";
    }
}