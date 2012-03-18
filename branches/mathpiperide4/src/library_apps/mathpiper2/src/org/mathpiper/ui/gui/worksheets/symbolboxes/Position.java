

package org.mathpiper.ui.gui.worksheets.symbolboxes;




public class Position {

    public double x;


    public double y;


    public Position() {
	this(0, 0);
    }


    public Position(Position p) {
	this(p.x, p.y);
    }


    public Position(double x, double y) {
	this.x = x;
	this.y = y;
    }


    public double getX() {
	return x;
    }


    public double getY() {
	return y;
    }


    public Position getLocation() {
	return new Position(x, y);
    }


    public void setLocation(Position p) {
	setLocation(p.x, p.y);
    }


    public void setLocation(int x, int y) {
	move(x, y);
    }


    public void setLocation(double x, double y) {
	this.x = x;
	this.y = y;
    }


    public void move(double x, double y) {
	this.x = x;
	this.y = y;
    }


    public void translate(double dx, double dy) {
	this.x += dx;
	this.y += dy;
    }


    public boolean equals(Object obj) {
	if (obj instanceof Position) {
	    Position pt = (Position)obj;
	    return (x == pt.x) && (y == pt.y);
	}
	return super.equals(obj);
    }


    public String toString() {
	return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }
}
