/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import vmm.functions.ComplexExpression;
import vmm.functions.Expression;
import vmm.functions.ParseError;
import vmm.functions.Parser;
import vmm.functions.Variable;

/**
 * Provides several static utility funtions that can be used in various places.
 */
public class Util {
	
	private static Parser constantParser;
	private static Parser constantParserWithNanAndInf;
	
	/**
	 * Parses a string to produce a constant Expression.  A default {@link vmm.functions.Parser} is
	 * used to do the parsing.
	 * @param expressionAsString The constant expression, represented as a string.  A ParseError
	 * is thrown if this is not a legal real-valued expression.
	 * @return The expression represented by the string
	 */
	public static Expression parseConstantExpression(String expressionAsString) throws ParseError { 
		if (constantParser == null)
			constantParser = new Parser();
		return constantParser.parseExpression(expressionAsString);
	}

	/**
	 * Parses a string to produce a constant ComplexExpression.  A default {@link vmm.functions.Parser} is
	 * used to do the parsing.
	 * @param expressionAsString The constant expression, represented as a string.  A ParseError
	 * is thrown if this is not a legal complex expression.
	 * @return The expression represented by the string
	 */
	public static ComplexExpression parseComplexConstantExpression(String expressionAsString) throws ParseError { 
		if (constantParser == null)
			constantParser = new Parser();
		return constantParser.parseComplexExpression(expressionAsString);
	}

	/**
	 *  A method that creates a cursor from an image file.  The image
	 *  file is obtained as a resource using the <code>getResource</code> method
	 *  of the Toolkit class.  If the resource is not found or if any
	 *  other error occurs, then the return value of the method will
	 *  be null.  The second and third parameters specify the 
	 *  active point of the cursor -- the point that determines the
	 *  (x,y) position of the cursor in mouse events.
	 */	
	public static Cursor createCursorFromResource(String imageResourceFileName, 
			int activeX, int activeY) {
		try {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			ClassLoader cl = MouseTask.class.getClassLoader();
			java.net.URL imageURL = cl.getResource(imageResourceFileName);
			if (imageURL == null) // resource not found
				return null;
			else {
				Image image = toolkit.createImage(imageURL);
				return toolkit.createCustomCursor(image,
						new Point(activeX, activeY), imageResourceFileName);
				// the last parameter is just a name for the cursor
			}
		}
		catch (Exception e) {
			return null;
		}
	}
	
	
	private static char isMac = '?';
	private static String commandKey = null;
	
	/**
	 * Test whether the program is running on Mac OS.
	 */
	public static boolean isMacOS() {
		if (isMac == '?') {
			try {
				String macTest = System.getProperty("mrj.version");
				if (macTest != null)
					isMac = 'Y';
				else
					isMac = 'N';
			}
			catch (Exception e) { // System.getProperty can throw a security exception
				isMac = 'N';
			}
		}
		return isMac == 'Y';
	}
	
	/**
	 * Returns a KeyStroke, typically for use as the accelerator for a menu command.
	 * Depending on the OS, either "control" or "meta" will be added to the description
	 * that is passed to this method. The descriptions might be just a key name, such as
	 * "A", or it might include modifier keys, such as "shift A" or "alt A".
	 */
	public static KeyStroke getAccelerator(String description) {
		if (commandKey == null) {
			if (isMacOS())
				commandKey = "meta ";
			else
				commandKey = "control ";
		}
		return KeyStroke.getKeyStroke(commandKey + description);
	}
	
	/**
	 * Tries to compute the amount of memory that is available and not yet used.
	 */
	public static long availableMemory() {
		System.gc();
		Runtime runtime = Runtime.getRuntime();
		long memoryInUse = runtime.totalMemory() - runtime.freeMemory();  // Looks like this can underestimate
		long maxMem = runtime.maxMemory();  // amount of memory the JVM is willing to request
		long mem;
		if (maxMem == Long.MAX_VALUE) // This would mean that the maxMem value is useless (but it seems to work on Mac, Windows, Linux)
			mem = runtime.freeMemory();
		else
			mem =  maxMem - memoryInUse;
//		System.out.println("\nFrom Util.availableMemory():");
//		System.out.println("   Max Memory =   " + runtime.maxMemory());
//		System.out.println("   Free Memory =  " + runtime.freeMemory());
//		System.out.println("   Total Memory = " + runtime.totalMemory());
//		System.out.println("   Avlbl Memory = " + mem);
		return mem;
	}
	
	
	public static Point2D getPoint2DFromUser(Component parent, String prompt) {
		return getPoint2DFromUser(parent, prompt, Double.NaN, Double.NaN);
	}

	public static Point2D getPoint2DFromUser(Component parent, String prompt, double initialX, double initialY) {
		return getPoint2DFromUser(parent, prompt, initialX, initialY, "x", "y");
	}

	public static Point2D getPoint2DFromUser(Component parent, String prompt, double initialX, double initialY, String nameForX, String nameForY) {
		RealParam xVal, yVal;
		ParameterInput xIn, yIn;
		xVal = new RealParam(nameForX, initialX);
		yVal = new RealParam(nameForY, initialY);
		xIn = new ParameterInput(xVal);
		yIn = new ParameterInput(yVal);
		xIn.setColumns(15);
		yIn.setColumns(15);
		if (Double.isNaN(initialX))
			xIn.setText("");
		if (Double.isNaN(initialY))
			yIn.setText("");

		JPanel top = new JPanel();
		top.setLayout(new BorderLayout(3,3));
		top.add( new JLabel(xVal.getTitle() + " = "), BorderLayout.WEST);
		top.add(xIn, BorderLayout.CENTER);

		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout(3,3));
		bottom.add( new JLabel(yVal.getTitle() + " = "), BorderLayout.WEST);
		bottom.add(yIn, BorderLayout.CENTER);

		JPanel input = new JPanel();
		input.setLayout(new GridLayout(2,1,10,10));
		input.add(top);
		input.add(bottom);
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout(10,10));
		content.add(input,BorderLayout.CENTER);
		content.add(new JLabel(prompt,JLabel.LEFT), BorderLayout.NORTH);

		int response = JOptionPane.showConfirmDialog(parent,content,"",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (response == JOptionPane.CANCEL_OPTION)
			return null;
		String xStr = xIn.getText();
		String yStr = yIn.getText();
		double newX, newY;
		try {
			newX = Double.parseDouble(xStr);
			newY = Double.parseDouble(yStr);
		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(parent, I18n.tr("vmm.core.Util.getPoint2D.error.illegalnumbers"));
			return null;
		}
		return new Point2D.Double(newX,newY);
	}
	
	/**
	 * A convenience method that breaks up a string into tokens, where
	 * the tokens are substrings seprated by specified delimiters.
	 * For example, explode("ab,cde,f,ghij", ",") produces an array
	 * of the four substrings "ab"  "cde"  "f"  "ghi".
	 * @param str the string that is to be exploded; if null, then the return
	 *   value is null
	 * @param separators characters in this string are separatos between tokens
	 *   in the string that is being exploded.  If this is null, then the
	 *   return value will be an array of length one containing str as its
	 *   only element.
	 */
	public static String[] explode(String str, String separators) {
		if (str == null)
			return null;
		if (separators == null)
			return new String[] { str };
		StringTokenizer tokenizer = new StringTokenizer(str, separators);
		int ct = tokenizer.countTokens();
		String[] tokens = new String[ct];
		for (int i = 0; i < ct; i++)
			tokens[i] = tokenizer.nextToken();
		return tokens;
	}
	
	/**
	 * Converts any value to a string -- hopefull a string that can be parsed
	 * by {@link #externalStringToValue(String, Class)} to recover the original
	 * value.  This method definitely works for:  the wrapper types such as Double, Complex,
	 * Color, Point2D, Vector3D, double[], int[].  For other types, this method
	 * returns value.toString(), which might work.  In no case does it throw an exception.
	 * Note that a null value is converted to the string "##NULL##".  For double and
	 * float numbers, the special strings NaN, +INF, -INF, and EPSILON are used
	 * to represent the special values Not-a-number, positive infinity, negative
	 * infinity, and min_value (the smallest positive value).  This method is
	 * used in {@link SaveAndRestore} and in other places where conversion of
	 * an object to a string is needed.
	 */
	public static String toExternalString(Object value) {
		try {
			if (value == null)
				return "##NULL##";
			else if (value instanceof Double) {
				double x = ((Double)value).doubleValue();
				if (Double.isNaN(x))
					return "NaN";
				else if (x == Double.POSITIVE_INFINITY)
					return "+INF";
				else if (x == Double.NEGATIVE_INFINITY)
					return "-INF";
				else if (x == Double.MIN_VALUE)
					return "EPSILON";
				else
					return "" + x;
			}
			else if (value instanceof Float) {
				float x = ((Float)value).floatValue();
				if (Float.isNaN(x))
					return "NaN";
				else if (x == Float.POSITIVE_INFINITY)
					return "+INF";
				else if (x == Float.NEGATIVE_INFINITY)
					return "-INF";
				else if (x == Float.MIN_VALUE)
					return "EPSILON";
				else
					return "" + x;
			}
			else if (value instanceof Color) {
				Color c = (Color)value;
				return c.getRed() + " " + c.getGreen() + " " + c.getBlue();
			}
			else if (value instanceof Point2D) {
				Point2D v = (Point2D)value;
				return toExternalString(v.getX()) + " " + toExternalString(v.getY());
			}
			else if (value instanceof double[]) {
				String s = "";
				double[] d = (double[])value;
				for (int i = 0; i < d.length; i++) {
					s = s + toExternalString(d[i]);
					if (i < d.length-1)
						s = s + " ";
				}
				return s;
			}
			else if (value instanceof int[]) {
				String s = "";
				int[] d = (int[])value;
				for (int i = 0; i < d.length; i++) {
					s = s + d[i];
					if (i < d.length-1)
						s = s + " ";
				}
				return s;
			}
		}
		catch (Exception e) {
		}
		return value.toString();
	}
	
	/**
	 * Tries to convert a string to a value of a specified type.  This method can handle
	 * the strings produced by {@link #toExternalString(Object)} for at least the following
	 * types:  the wrapper types, Color, Point2D, Complex, Vector3D, double[], and int[].
	 * The special string "##NULL##" is converted to the value null (except in the case
	 * of primitive types.) The method will work for primitive types such as Double.TYPE.  
	 * Note that for the type Double.TYPE, the return value is of type Double (just as it 
	 * would be for Double.class).  Aside from these built-in types, the method will try
	 * to find a static method in the class named "fromString" and with one parameter of
	 * type String.  If such a method is found, it will be called and its return value
	 * will be the return value of this mthod.  If no such method is found, an attempt
	 * is made to find a constructor in the class with one parameter of type String.
     * If such a constructor is found, it is invoked to produce the return value.
     * <p>An exception of type IllegalArgumentException will be thrown if none of this
     * succeeds in producing an object of the specified type.  No other type of 
     * exception will be thrown.
	 */
	public static Object externalStringToValue(String str, Class valueType) throws IllegalArgumentException {
		try {
			if (valueType.isPrimitive()) {
				if (valueType.equals(Boolean.TYPE))
					return new Boolean(str);
				else if (valueType.equals(Integer.TYPE))
					return new Integer(str);
				else if (valueType.equals(Double.TYPE)) {
					if ("NaN".equalsIgnoreCase(str))
						return Double.NaN;
					else if ("+INF".equalsIgnoreCase(str))
						return Double.POSITIVE_INFINITY;
					else if ("-INF".equalsIgnoreCase(str))
						return Double.NEGATIVE_INFINITY;
					else if ("EPSILON".equalsIgnoreCase(str))
						return Double.MIN_VALUE;
					else
						return new Double(str);
				}
				else if (valueType.equals(Float.TYPE)) {
					if ("NaN".equalsIgnoreCase(str))
						return Float.NaN;
					else if ("+INF".equalsIgnoreCase(str))
						return Float.POSITIVE_INFINITY;
					else if ("-INF".equalsIgnoreCase(str))
						return Float.NEGATIVE_INFINITY;
					else if ("EPSILON".equalsIgnoreCase(str))
						return Float.MIN_VALUE;
					else
						return new Float(str);
				}
				else if (valueType.equals(Long.TYPE))
					return new Long(str);
				else if (valueType.equals(Short.TYPE))
					return new Short(str);
				else if (valueType.equals(Byte.TYPE))
					return new Byte(str);
				else
					return new Character(str.charAt(0));
			}
			else if (str.equalsIgnoreCase("##NULL##"))
				return null;
			else if (valueType.equals(Double.class))
				return externalStringToValue(str, Double.TYPE);
			else if (valueType.equals(Float.class))
				return externalStringToValue(str, Float.TYPE);
			else if (valueType.equals(String.class))
				return str;
			else if (valueType.equals(Complex.class)) {
				if (constantParserWithNanAndInf == null) {
					constantParserWithNanAndInf = new Parser();
					constantParserWithNanAndInf.add(new Variable("NaN",Double.NaN));
					constantParserWithNanAndInf.add(new Variable("INF",Double.POSITIVE_INFINITY));
				}
				return constantParserWithNanAndInf.parseComplexExpression(str).value();
			}
			else if (valueType.equals(Color.class)) {
				StringTokenizer tokenizer = new StringTokenizer(str," ,\t");
				String redString = tokenizer.nextToken();
				String greenString = tokenizer.nextToken();
				String blueString = tokenizer.nextToken();
				return new Color( Integer.parseInt(redString), Integer.parseInt(greenString), Integer.parseInt(blueString));
			}
			else if (valueType.equals(double[].class)) {
				StringTokenizer tokenizer = new StringTokenizer(str,", \t");
				int ct = tokenizer.countTokens();
				double[] numbers = new double[ct];
				for (int i = 0; i < ct; i++) {
					String s = tokenizer.nextToken();
					numbers[i] = (Double)externalStringToValue(s, Double.TYPE);
				}
				return numbers;
			}
			else if (valueType.equals(int[].class)) {
				StringTokenizer t = new StringTokenizer(str," ,\t");
				int[] a = new int[t.countTokens()];
				for (int i = 0; i < a.length; i++)
					a[i] = Integer.parseInt(t.nextToken());
				return a;
			}
			else if (valueType.equals(Point2D.class)) {
				double[] nums = (double[])externalStringToValue(str,double[].class);
				if (nums.length != 2)
					throw new Exception();
				return new Point2D.Double(nums[0],nums[1]);
			}
			else {  // try to make the object using either a static fromString(String) or, if there is none,
				    // a constructor with one parameter of type String.  If none of this works, an error occurs.
				Method fromExternalString = null;
				try {
					fromExternalString = valueType.getDeclaredMethod("fromString", new Class[] { String.class });
				}
				catch (Exception e) {
				}
				if (fromExternalString != null) {
					Object obj = fromExternalString.invoke(null, str);
					if (valueType.isInstance(obj))
						return obj;
					else
						throw new Exception();
				}
				else {
					Constructor constructor = valueType.getConstructor(new Class[] { String.class });
					return constructor.newInstance(new Object[] { str });
				}
			}
		}
		catch (Exception e) {
			throw new IllegalArgumentException(I18n.tr("vmm.core.Util.error.BadString",str));
		}
	}
		   
}
