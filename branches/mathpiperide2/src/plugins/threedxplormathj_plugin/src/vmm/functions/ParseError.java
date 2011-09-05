/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.functions;

/**
 * An exception of type ParseError is thrown by a 
 * {@link Parser} when it encounters an error in the
 * definition of a function that it is parsing.
 */
public class ParseError extends RuntimeException {
	
	private String sourceString;
	private int errorPosition;
	
	public ParseError(String errorMessage, int pos, String sourceString) {
		super(errorMessage);
		this.sourceString = sourceString;
		this.errorPosition = pos;
	}

	/**
	 * Returns the string in which the parser encountered the error.
	 */
	public String getSourceString() {
		return sourceString;
	}
	
	/**
	 * Returns the position in the string where the parser encountered
	 * the error.  The position is the usually index of the character
	 * that follows the token that was read most recently at the time
	 * the error occurred. 
	 */
	public int getErrorPosition() {
		return errorPosition;
	}
	
}
