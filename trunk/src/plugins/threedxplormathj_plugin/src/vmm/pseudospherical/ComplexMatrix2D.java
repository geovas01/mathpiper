/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.pseudospherical;

import vmm.core.*;

/**
 *	This class implements a complex two-by-two matrix. 
 *  if A is an object of this class, then the (i,j)-th entry of the matrix is  A.entries[i,j]
 *  Here i and j can be 1 or 2 
 *	
 *	@author Nam Trang
 *
 */

public class ComplexMatrix2D  {       
    Complex entries[][] = new Complex[3][3];   // entries[i][j] with i =0  or j = 0 are ignored
    
    /**
     * default constructor, initializes all entries to 0
     */
    public ComplexMatrix2D() {
		initialize();
	}
	
	/**
     * constructor that copies the content of inp
     */
    public ComplexMatrix2D(ComplexMatrix2D inp){
		if(inp == null)
			initialize();
		else
		{
          for(int i=0; i < 3; i++) {
             for(int j=0; j < 3; j++) {
				entries[i][j] = inp.entries[i][j];
			}
         }
       }
	}
	
        
	public void initialize()  {
		 for(int i=0; i < 3; i++) {
           for(int j=0; j < 3; j++) {
			  entries[i][j] = new Complex(0,0);	
              }
         }
	}
	
	/**
     * return the entry at location (i,j) in the matrix where 1 <= i,j <= 2
     */
	public Complex getMatrixEntry(int i, int j) {
		if(i <= 0 || i >= 3 || j <= 0 || j >= 3)
			return null;
		return entries[i][j];
	}
	
	/**
     * set the entry (i,j) in the matrix to the value of val
     */    
	public void setMatrixEntry(Complex val, int i, int j) {
		if(!(i <= 0 || i >= 3 || j <= 0 || j >= 3))
			entries[i][j] = val;
	}
	
	/**
     * compute the inverse of the matrix
     * if the matrix is not invertible, return null
     */
	public ComplexMatrix2D inverse()  {
		ComplexMatrix2D  theInverse = new ComplexMatrix2D();
		Complex determinant = (entries[1][1].times(entries[2][2])).minus(entries[2][1].times(entries[1][2]));
		Complex zero = new Complex(0,0);
		if(determinant.equals(zero)){
			return null;
		}
		else
		{
            theInverse.entries[1][1] = entries[2][2].dividedBy(determinant);
            theInverse.entries[2][2] = entries[1][1].dividedBy(determinant);
            theInverse.entries[1][2] = (zero.minus(entries[1][2])).dividedBy(determinant);
            theInverse.entries[2][1] = (zero.minus(entries[2][1])).dividedBy(determinant);
            return theInverse;
		}
	}
	
	/**
     * multiply to the right by matrix inp
     * return the product
     */
	public ComplexMatrix2D  multiply(ComplexMatrix2D inp) {
		ComplexMatrix2D theProduct = new ComplexMatrix2D();
                for(int i=1; i < 3; i++) {
                        for(int j=1; j < 3; j++) {			
                        theProduct.entries[i][j] = new Complex();
                         for(int k=1; k < 3; k++){
                          theProduct.entries[i][j] =  theProduct.entries[i][j].plus (entries[i][k].times(inp.entries[k][j]));
                    }
                }
            }
		
		return theProduct;
	}
        
	/**
     * compute and return the transpose of the matrix
     */
	public ComplexMatrix2D transpose()  {
		ComplexMatrix2D theTranspose = new ComplexMatrix2D();
		theTranspose.entries[1][1] = entries[1][1];
		theTranspose.entries[1][2] = entries[2][1];
		theTranspose.entries[2][1] = entries[1][2];
		theTranspose.entries[2][2] = entries[2][2];
		return theTranspose;
	}
	
	/**
     * compute and return the conjugate of the matrix
     */
	public ComplexMatrix2D conjugate()  {
		ComplexMatrix2D theConjugate = new ComplexMatrix2D();
		theConjugate.entries[1][1] = entries[1][1].conj();
		theConjugate.entries[1][2] = entries[1][2].conj();
		theConjugate.entries[2][1] = entries[2][1].conj();
		theConjugate.entries[2][2] = entries[2][2].conj();
		return theConjugate;
	}
	
	/**
     * compute and return the result of adding the matrix with inp
     */    
	public ComplexMatrix2D add(ComplexMatrix2D inp)  {
		ComplexMatrix2D thesum = new ComplexMatrix2D();
		thesum.entries[1][1] = entries[1][1].plus(inp.entries[1][1]);
		thesum.entries[1][2] = entries[1][2].plus(inp.entries[1][2]);
		thesum.entries[2][1] = entries[2][1].plus(inp.entries[2][1]);
		thesum.entries[2][2] = entries[2][2].plus(inp.entries[2][2]);
		return  thesum;
	}
	
	/**
     * compute and return the result of subtracting inp from the matrix
     */
	public ComplexMatrix2D subtract(ComplexMatrix2D inp) {
		ComplexMatrix2D thedifference = new ComplexMatrix2D();
		thedifference.entries[1][1] = entries[1][1].minus(inp.entries[1][1]);
		thedifference.entries[1][2] = entries[1][2].minus(inp.entries[1][2]);
		thedifference.entries[2][1] = entries[2][1].minus(inp.entries[2][1]);
		thedifference.entries[2][2] = entries[2][2].minus(inp.entries[2][2]);
		return  thedifference;
	}
	
	/**
     * compute and return the result of multiplying the matrix with the complex number c
     */
	public ComplexMatrix2D scalarMul(Complex c) {
		ComplexMatrix2D theScalarProd = new ComplexMatrix2D();
        theScalarProd.entries[1][1] = entries[1][1].times(c);
		theScalarProd.entries[1][2] = entries[1][2].times(c);
		theScalarProd.entries[2][1] = entries[2][1].times(c);
		theScalarProd.entries[2][2] = entries[2][2].times(c);
		return theScalarProd;
	}
	
	/**
     * test for equality
     * two matrices are equal if and only if all their corresponding entries are equal
     */
	public boolean equals(ComplexMatrix2D inp)
	{
		return entries[1][1].equals(inp.entries[1][1])&&
               entries[1][2].equals(inp.entries[1][2])&&
               entries[2][1].equals(inp.entries[2][1])&&
               entries[2][2].equals(inp.entries[2][2]);
	}
        
	/**
     * print the content of the matrix in the format {{a11,a12},{a21,a22}}
     */    
	public String toString()  {
		String out = new String();
		out += "{{";
		out += entries[1][1].toString();
		out += ",";
		out += entries[1][2].toString();
		out += "},{";
		out += entries[2][1].toString();
		out += ",";
		out += entries[2][2].toString();
		out += "}}";
		return out;
	}
	
	/**
     * static method returning an object of type ComplexMatrix2D that is
     * the projection matrix onto the direction of vector v
     */
	public static ComplexMatrix2D getProj(ComplexVector2D v) {
		ComplexMatrix2D mat = new ComplexMatrix2D();
		double c = v.norm2();
		mat.entries[1][1] = ((v.x).times(v.x.conj() ) ).dividedBy(c);
		mat.entries[1][2] = ((v.x).times(v.y.conj() ) ).dividedBy(c);
		mat.entries[2][1] = ((v.y).times(v.x.conj() ) ).dividedBy(c);
		mat.entries[2][2] = ((v.y).times(v.y.conj() ) ).dividedBy(c);
		return mat;
	}
	
	
	/**
     * return an object of type ComplexVector2D that is the result of multiplying
     * the matrix with a complex vector v
     */
	public ComplexVector2D vectMul(ComplexVector2D v)  {
		Complex v1 = new Complex((entries[1][1].times(v.x)).plus(entries[1][2].times(v.y)));
		Complex v2 = new Complex((entries[2][1].times(v.x)).plus(entries[2][2].times(v.y)));
		return new ComplexVector2D(v1,v2);
	}
	
	/**
     * return the trace of the matrix.
     * the trace of a matrix is the sum of its entries along the diagonal
     */
	public Complex trace()
	{
		return entries[1][1].plus(entries[2][2]);
	}
	
	/**
     * return the determinant of the matrix.
     * the determinant of a 2x2 matrix of the form {{a,b},{c,d}} is ad-bc
     */
	public Complex determinant()
	{
		return entries[1][1].times(entries[2][2]).minus((entries[1][2].times(entries[2][1])));
	}
}