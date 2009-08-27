/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.functions;

/**
 * A real-valued variable.  A Variable can be added to a {@link Parser}
 * so that it can be used in the defintion of functions and expressions
 * that are parsed by that parser.  It is possible to change the value
 * of a variable with the {@link #setVal(double)} method, which will
 * affect the value of any expressions and functions in which it is used.
 */
public class Variable {
	
	private String name;
	private double x;
	
	/**
	 * Create a variable with a specified name and initial value.
	 * @param name the name of the variable.  This can be null.  If the variable is
	 * to  be added to a Parser, then the name must be non-null and should be 
	 * a legal identifier (starting with a letter and containing only letters,
	 * digits and the underscore character).
	 * @param x the initial value of the variable.
	 */
	public Variable(String name, double x) {
		this.name = name;
		this.x = x;
	}
	
	/**
	 * Create a variable with initial value zero.
	 */
	public Variable(String name) {
		this.name = name;
	}
	
	/**
	 * Create a Variable with a specified initial value and with name
	 * initially equal to null.
	 */
	public Variable(double x) {
		this.x = x;
	}
	
	/**
	 * Returns the current value of this variable.
	 */
	public double getVal() {
		return x;
	}
	
	/**
	 * Sets the value of this variable.
	 */
	public void setVal(double x) {
		this.x = x;
	}
	
	/**
	 * Gets the name of this variable.  This can be null.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of this variable.  The name should not be changed after
	 * the variable has been added to a Parser; even if the name is changed, the
	 * Parser will continue to use it under the name it had at the time it was
	 * added to the parser.
	 */
	public void setName(String name) {
		this.name = name;
	}
	

}
