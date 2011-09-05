/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Provides static methods for saving Exhibits and their associated Views to an XML file and for
 * restoring an Exhbit and Views from such a file.  (Methods in this class can also be used
 * for encoding/decoding exhibits to XML Docment objects without ever writing them to a file.)
 * There are also a few stating utility routines that can help programmers write Exhbit, View, 
 * and Decoration classes in a way that will work with this Save/Restore facility. 
 * <p>When Views and Exhibits are stored in XML format by this class their parameters
 * are saved automatically. Decorations whose classes are marked with a
 * {@link vmm.core.VMMSave} are saved automatically.  Property variables in Views, Exhibits, and
 * Decorations that are marked with a {@link vmm.core.VMMSave} annotation are saved automatically. 
 * Saving other data of a View, Exhibit, or Decoration class is the responsibility
 * of that class.  Parameters, properties, and decorations are restored automatically when the file is read.
 * The Exhibit, View, and Decoration classes include an <code>addExtraXML</code> method that can be overridden to
 * write any necessary extra data that is not saved automatically and a <code>readExtraXML</code>
 * method for restoring the data when the file is read.
 * <p>Note that for property variables, only properties of certain types are supported by
 * these methods.  For data other than supported properties, the programmer should write an XML representation
 * of the data in <code>writeExtraXML</code> and should read this data in the <code>readExtraXML</code> method
 * in the same class.
 * <p>This class is meant to work with well-formed XML, but does not require valid XML (that is, there
 * is no DTD or XML schema to specify the syntax).  
 */
public class SaveAndRestore {
	
	// -------------------------- read an XML description of an Exhibit from a file --------------------------
	
	
	/**
	 * Read an XML file containing a representation of an Exhibit and possibly some associated Views.
	 * If any Views are found, the Exhibit is installed into each View, and the list of Views can
	 * be obtained by calling the {@link Exhibit#getViews()} method if the exhibit.
	 * @param in the XML file from which the data is to be read
	 * @return An exhibit read from the file.  Any views that were read from the file can be retrieved
	 * by calling <code>exhibit.getViews()</code>.
	 * @throws IOException throws IOException an error occurs while reading the file.  This can include errors in XML syntax.
	 */
	public static Exhibit readExhibitFromXML(File in) throws IOException {
		return readExhibitFromXML(new FileInputStream(in), in.getName());
	}
	
	/**
	 * Constructs an Exhibit from its XML representation.  The representation is in the form of the
	 * Documents produced by the {@link #exhibitToXML(Exhibit)} method and similar mehtods in this class.
	 * If the Document contains any Views, then the Exhibit is installed in each View. The list of
	 * views can be obtained by calling the exhibit's {@link Exhibit#getViews()} method.
	 * @throws IllegalArgumentException if the Document is not a legal XML representation of an Exhibit
	 */
	public static Exhibit convertXMLToExhibit(Document exhibitDocument) {
		try {
			Element exhibitInfo = exhibitDocument.getDocumentElement();
			if (! exhibitInfo.getTagName().equals("exhibit-info"))
				throw new IllegalArgumentException();
			Element exhibitElement = getChildElement(exhibitInfo,"exhibit");
			if (exhibitElement == null)
				throw new IllegalArgumentException();
			if (exhibitInfo.getElementsByTagName("exhibit").getLength() > 1)
				throw new IllegalArgumentException();
			Exhibit exhibit;
			try {
				exhibit =  buildExhibitFromElement(exhibitElement);
			}
			catch (Exception e) {
				throw new IllegalArgumentException();
			}
			NodeList viewCandidates = exhibitInfo.getChildNodes();
			for (int i = 0; i < viewCandidates.getLength(); i++) {
				Node child = viewCandidates.item(i);
				if (child instanceof Element && ((Element)child).getTagName().equals("view")) {
					Element viewInfo = (Element)child;
					buildViewFromElement(viewInfo,exhibit);
				}
			}
			return exhibit;
		}
		catch (Exception e) {
			throw new IllegalArgumentException(I18n.tr("vmm.core.SaveAndRestore.error.NotValidExhibitDocument"));
		}
	}
	
	/**
	 * Read the XML representation of an exhibit and possibly some associated views from an InputStream.
	 * If any Views are found, the Exhibit is installed into each View, and the list of Views can
	 * be obtained by calling the {@link Exhibit#getViews()} method if the exhibit.
	 * @param in the input stream from which the XML data is to be read
	 * @param inputName the file (or other) name of the input stream, which is used only in error messages
	 * @return An exhibit read from the file.  Any views that were read from the file can be retrieved
	 * by calling <code>exhibit.getViews()</code>.
	 * @throws IOException throws IOException an error occurs while reading the file.  This can include errors in XML syntax.
	 */
	public static Exhibit readExhibitFromXML(InputStream in, String inputName) throws IOException {
		Document doc = readXMLDocument(in,inputName);
		Element exhibitInfo;
		exhibitInfo = doc.getDocumentElement();
		if (! exhibitInfo.getTagName().equals("exhibit-info"))
			throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.NotAnExhibitInfoFile",inputName,exhibitInfo.getTagName()));
		Element exhibitElement = getChildElement(exhibitInfo,"exhibit");
		if (exhibitElement == null)
			throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.MissingExhibitElement",inputName));
		if (exhibitInfo.getElementsByTagName("exhibit").getLength() > 1)
			throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.TooManyExhibitElements",inputName));
		Exhibit exhibit;
		try {
			exhibit =  buildExhibitFromElement(exhibitElement);
		}
		catch (Exception e) {
			throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.IllegalDataInFile",inputName,e.getMessage()));
		}
		NodeList viewCandidates = exhibitInfo.getChildNodes();
		for (int i = 0; i < viewCandidates.getLength(); i++) {
			Node child = viewCandidates.item(i);
			if (child instanceof Element && ((Element)child).getTagName().equals("view")) {
				Element viewInfo = (Element)child;
				buildViewFromElement(viewInfo,exhibit);
			}
		}
		return exhibit;
	}
	
	/**
	 * Reads an XML file and produces a DOM Document tree from it.  This is meant for reading the
	 * XML form of an exhibit, but in fact it can read any XML document.
	 */
	public static Document readXMLDocument(File file) throws IOException {
		return readXMLDocument(new FileInputStream(file), file.getName());
	}
	
	/**
	 * Reads XML from an input source and produces a DOM Document tree from it.  This is meant for reading the
	 * XML form of an exhibit, but in fact it can read any XML document.
	 */
	public static Document readXMLDocument(InputStream in, String inputName) throws IOException {
		DocumentBuilder reader;
		Document doc;
		try {
			reader = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		}
		catch (Exception e) {
			throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.CantGetDocumentBuilder"));
		}
		try {
			doc = reader.parse(in);
		}
		catch (SAXException e) {
			throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.IllegalXMLFile",inputName,e.getMessage()));
		}
		catch (IOException e) {
			throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.InputError",inputName,e.getMessage()));
		}
		return doc;
	}
	
	// ----------------------------------- building an XML representation of an exhibit's state  ------------------------
	
	/**
	 * Constructs an XML representaion of an Exhibit and a single view of that Exhibit.
	 * This is a convenience method that just calls {@link #exhibitToXML(Exhibit, View[])}.
	 */
	public static Document exhibitToXML(Exhibit exhibit, View view) {
		return exhibitToXML(exhibit, view == null ? (View[])null : new View[] { view });
	}
	
	/**
	 * Constructs an XML representaion of an Exhibit and any views of that exhibit, as returned
	 * by the {@link Exhibit#getViews()} method.
	 * This is a convenience method that just calls {@link #exhibitToXML(Exhibit, View[])}.
	 */
	public static Document exhibitToXML(Exhibit exhibit) {
		ArrayList<View> views = exhibit.getViews();
		if (views == null || views.size() == 0)
			return exhibitToXML(exhibit, (View[])null);
		View[] viewArray = new View[views.size()];
		views.toArray(viewArray);
		return exhibitToXML(exhibit,viewArray);
	}
	
	/**
	 * Constructs an XML representaion of an Exhibit and possibly some Views of that Exhibit.
	 * The XML document that is constructed is supposed to represent the complete state of
	 * the Exhbit and Views, allowing them to be reconstructed by the
	 * {@link #readExhibitFromXML(File)} or {@link #convertXMLToExhibit(Document)} method in this class.
	 * @param exhibit The exhibit whose state is to be encoded.
	 * @param views View of the exhibit that should be encoded into the XML document.  If this is null, no
	 * views are encoded.  Any view in this array must be capable of displaying the exhibit.
	 */
	public static Document exhibitToXML(Exhibit exhibit, View[] views) {
		Document exhibitDocument;
		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		}
		catch (Exception e) {
			throw new RuntimeException(I18n.tr("vmm.core.SaveAndRestore.error.CantGetDocumentBuilder"));
		}
		exhibitDocument = builder.getDOMImplementation().createDocument(null,"exhibit-info",null);
		Element exhibitInfoElement = exhibitDocument.getDocumentElement();
		Element exhibitElement = exhibitDocument.createElement("exhibit");
		exhibitInfoElement.appendChild(exhibitElement);
		buildExhibitElement(exhibitDocument,exhibitElement,exhibit);
		if (views != null)
			for (int i = 0; i < views.length; i++) {
				Element viewElement = exhibitDocument.createElement("view");
				buildViewElement(exhibitDocument,viewElement,views[i]);
				exhibitInfoElement.appendChild(viewElement);
			}
		return exhibitDocument;
	}
	
	/**
	 * Writes a simple XML document to an output stream. This method is meant for use
	 * with the type of XML document manipulated with this class, with no namespaces,
	 * DTD, etc.
	 * @param out The non-null output stream to which the document is to be written.
	 * @param doc The non-null XML documnent that is to be written.
	 */
	public static void writeXMLDocument(PrintWriter out, Document doc) {
		out.println("<?xml version=\"1.0\"?>");
		writeElement(out,doc.getDocumentElement(),"");
	}
	
	/**
	 * This is a convenience method that just calls {@link #addProperty(Object, String, Document, Element)} for
	 * each name in the propertyNames array.
	 */
	public static void addProperties(Object propertyOwner, String[] propertyNames, Document document, Element element) {
		for (int i = 0; i < propertyNames.length; i++)
			addProperty(propertyOwner,propertyNames[i],document,element);
	}
	
	/**
	 * Adds an XML Element represent a property of an object to an XML element that represents that
	 * object.  Note that this method does <b>not</b> have to be used with properties that
	 * are marked with the {@link vmm.core.VMMSave} annotation, since such properites are 
	 * saved automatically.
	 * <p>The term "property" here refers to a JavaBean-style property with a "getXxxx()" and a
	 * "setXxxx(value)" method, where xxxx is the name of the property.  This method can be called, for
	 * example, by an Exhibit, Decoration, or View to add properties to its XML representation.
	 * (This would be done in {@link Exhibit#addExtraXML(Document, Element)} and similar methods in
	 * decoration and view classes.)  This method can only handle properties of certain types:
	 * any of the primitive types, String, Color, Point2D, Vector3D, double[], and any class that has a one-parameter
	 * constructor with arguemnt of type String that can reconstruct an object from the string returned by
	 * the toString() method of the object. (The conversion of value to string is actually done 
	 * by {@link Util#toExternalString(Object)}.)
	 * @see #readProperties(Object, Element)
	 * @param propertyOwner  The non-null object whose property is to be encoded.
	 * @param propertyName  The name of the property that is to be encoded.
	 * @param document The XML Document that contains the Element that is being created.  This is provided
	 * as a parameter because it is needed to create any child elements that are to be added to the element.
	 * @param element The element that is being constructed to represent the propertyOwner.
	 * @throws IllegalArgumentException if no such property is found in the propertyOwner object, or if any
	 * other Exception occurs.
	 */
	public static void addProperty(Object propertyOwner, String propertyName, Document document, Element element) {
		Element propertyElement = document.createElement("property");
		try {
			Method readMethod = (new PropertyDescriptor(propertyName,propertyOwner.getClass())).getReadMethod();
			Object value = readMethod.invoke(propertyOwner,(Object[])null);
			String valueString = Util.toExternalString(value);
			propertyElement.setAttribute("name", propertyName);
			propertyElement.setAttribute("value", valueString);
		}
		catch (Exception e) {
			throw new IllegalArgumentException("Can't access property \"" + propertyName + "\".");
		}
		element.appendChild(propertyElement);
	}
	

	/**
	 * Reads all the &lt;propety&gt; elements inside a given element and restores the values
	 * of those properties into a specified object.  The properties that are handled by this
	 * method are presumably properites that were added to the XML using the
	 * {@link #addProperty(Object, String, Document, Element)} or
	 * {@link #addProperties(Object, String[], Document, Element)} method.  (In any case,
	 * the format of the property elements must be the same as those produced by these
	 * methods.)
	 * @param owner The object whose properties are being restored.
	 * @param containingElement The element that contains the &lt;property&gt; elements.  The
	 *   childer of this element are examined.  Fore each child element whose tag name is
	 *   "propety", an attempt is made to call setter method in the owner object to set
	 *   the value of the property.  If no setter method for the property exists, it is
	 *   <b>not</b> considered to be an error.  (This allows new properties to be added
	 *   in future releases while still allowing old releases to read the settings files
	 *   that contain the newly added properties.)
	 * @throws IOException if a &lt;property&gt; element does not have a name and a value
	 *    attribute or if the value attribute cannot be converted to a value of the type
	 *    required by the setter method for the property.
	 */
	public static void readProperties(Object owner, Element containingElement) throws IOException {
		NodeList children = containingElement.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child instanceof Element && ((Element)child).getTagName().equals("property")) {
				Element propertyElement = (Element)child;
				String propertyName = propertyElement.getAttribute("name").trim();
				if (propertyName.length() == 0)
					throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.MissingPropertyName"));
				if (!propertyElement.hasAttribute("value"))
					throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.MissingValueAttribute", "property/" + propertyName));
				try {
					String valueString = propertyElement.getAttribute("value");
					Method writeMethod;
					try {
						writeMethod = (new PropertyDescriptor(propertyName,owner.getClass())).getWriteMethod();
					}
					catch (IntrospectionException e) {
						continue;  // property does not exist -- not an error, it could have been added since XML file was created.
					}
					Class[] parameterTypes = writeMethod.getParameterTypes();
					Object value = Util.externalStringToValue(valueString, parameterTypes[0]);
					writeMethod.invoke(owner, new Object[] { value } );
				}
				catch (Exception e) {
					e.printStackTrace();
					throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.ErrorReadingProperty",propertyName,e.getMessage()));
				}
			}
		}
	}
	
	/**
	 * Adds an XML element representing a Decoration to a specified parent element.  
	 * Note that is is not necessary to use this method for Decorations whose classes
	 * are marked with the {@link vmm.core.VMMSave} annotation, since such decorations
	 * are saved automatically, along with the View or Exhibit to which they have been added.
	 * <p>When the parent element represents a View or Exhibit, the decoration will automatically be
	 * recreated and added to the recreated view or exhibit when the XML file is read.  There
	 * is no need for the Exhibit or View class itself to take any action restore the decoration.
	 * This method would ordinarily be used in the <code>addExtraXML</code> method of an Exhibit
	 * or View class.  Note however, that another possibility is to save other information (such
	 * as a boolean property) that indicates the presense of the decoration.
	 * @param doc The non-null overall XML document that is being constructed.
	 * @param parentElement The non-null element to which the decoration element is to be added as a child.
	 * @param dec The non-null decoration that is to be encoded into XML and added to the parent element in 
	 * the XML document.
	 */
	public static void addDecorationElement(Document doc, Element parentElement, Decoration dec) {
		Element decorationElement = doc.createElement("decoration");
		String classname = dec.getClass().getName();
		decorationElement.setAttribute("class",classname);
		addSavedProperties(dec,doc,decorationElement);
		dec.addExtraXML(doc,decorationElement);
		parentElement.appendChild(decorationElement);
	}

	/**
	 * Adds a child element to a given parent containing a "value" attribute with a specified value.
	 * The value is meant to be retrieved using {@link #getChildElementValue(Element, String, Class)}.
	 * @param containingDocument The document containing the parent element.
	 * @param parentElement The element to which the child is to be added.
	 * @param name The name of the child element.
	 * @param value The value of the "value" element. Major types such as Double, Float, Integer, 
	 * String, Color, Vector3D, double[] are supported.  The conversion of value-to-string is done
	 * using {@link Util#toExternalString(Object)}.
	 */
	public static void addElement(Document containingDocument, Element parentElement, String name, Object value) {
		Element child = containingDocument.createElement(name);
		child.setAttribute("value",Util.toExternalString(value));
		parentElement.appendChild(child);
	}
	
	/**
	 * Retrieves a value from a child element that was created with {@link #addElement(Document, Element, String, Object)}
	 * @param parentElement The element to which the child element was added.
	 * @param name The name of the child element.
	 * @param valueType The type of value expected.
	 * @return The value from the element, conveted from the "value" attribute string, or null if the value is not successfully retrieved.
	 */
	public static Object getChildElementValue(Element parentElement, String name, Class valueType) {
		Element child = getChildElement(parentElement,name);
		if (child == null)
			return null;
		String valueString = child.getAttribute("value");
		if (valueString == null || valueString.length() == 0)
			return null;
		try {
			return Util.externalStringToValue(valueString, valueType);
		}
		catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Creates a transform element from a transform.
	 * @param nodeName the name of the element that will be created
	 * @param doc the Document that will contain the transform element.
	 * @param transform the non-null transform
	 * @return A &lt;transform&gt; element that describes the transform.
	 */
	public static Element makeTransformElement(String nodeName, Document doc, Transform transform) {
		Element transformElement = doc.createElement(nodeName);
		transformElement.setAttribute("class", transform.getClass().getName());
		Element windowElement = doc.createElement("window");
		windowElement.setAttribute("limits", transform.getXminRequested() + " " + transform.getXmaxRequested() + " "
				+ transform.getYminRequested() + " " + transform.getYmaxRequested() );
		transformElement.appendChild(windowElement);
		addSavedProperties(transform, doc, transformElement);
		return transformElement;
	}
	
	/**
	 * Create a Transform object form a &lt;transform&gt; element that describes the transform.
	 */
	public static Transform buildTransformFromElement(Element transformElement) throws IOException {
		Transform transform;
		String transformClassName = transformElement.getAttribute("class");
		try {
			transform = (Transform)createObjectFromClassName(transformClassName,null);
		}
		catch (Exception e) {
			throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.CantMakeObject",transformClassName));
		}
		Element windowElement = getChildElement(transformElement,"window");
		if (windowElement != null) {
			String str = windowElement.getAttribute("limits");
			double[] window;
			try {
				window = (double[])Util.externalStringToValue(str,double[].class);
			}
			catch (Exception e) {
				throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", str, "window"));
			}
			if (window.length != 4)
				throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", str, "window"));
			transform.setLimits(window[0], window[1], window[2], window[3]);
		}
		if (transform != null)
			readProperties(transform,transformElement);
		return transform;
	}
	
	/**
	 * Returns a child element of an element, based on the name of the child element.
	 * @param parent the element that might contain the child element
	 * @param tagName the name of the desired child element
	 * @return the child element, or null if no child element with the given name exists.
	 *   (If more than one exists, the first is returned.)
	 */
	public static Element getChildElement(Element parent, String tagName) {
		NodeList children = parent.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child instanceof Element && ((Element)child).getTagName().equals(tagName))
				return (Element)child;
		}
		return null;
	}
	

	//-------------------- output helpers ---------------------------------
	
	/**
	 * Creates an object belonging to a class with a given class name.  The second paramter
	 * is an object that might contain the class as an inner class.   If the object can't be
	 * created using a parameterless constructor, then an attempt is made to create the object
	 * using a constructor that accepts a single paramter of that type of possiblyContainingObject.
	 * This will work for inner classes in that object; it will also work in fact if the
	 * class is not an inner class but has a constructor that satisfies the description.
	 */
	private static Object createObjectFromClassName(String classname, Object possiblyContainingObject) throws Exception {
		Class theClass = Class.forName(classname);
		try {
			return theClass.newInstance();
		}
		catch (Exception e) {
		}
		if (possiblyContainingObject == null)
			throw new Exception();
		// Try making an inner class!
		Object theObject = null;
		Constructor[] clist = theClass.getConstructors();
		for (int i = 0; i < clist.length; i++) {
			Class[] parameterTypes = clist[i].getParameterTypes();
			if (parameterTypes != null && parameterTypes.length == 1 && parameterTypes[0].isInstance(possiblyContainingObject)) {
				theObject = clist[i].newInstance( new Object[] { possiblyContainingObject } );
				break;
			}
		}
		if (theObject == null && possiblyContainingObject instanceof View && ((View)possiblyContainingObject).getExhibit() != null) {
			possiblyContainingObject = ((View)possiblyContainingObject).getExhibit();  // Try again with exhbiit object
			for (int i = 0; i < clist.length; i++) {
				Class[] parameterTypes = clist[i].getParameterTypes();
				if (parameterTypes != null && parameterTypes.length == 1 && parameterTypes[0].isInstance(possiblyContainingObject)) {
					theObject = clist[i].newInstance( new Object[] { possiblyContainingObject } );
					break;
				}
			}
		}
		if (theObject == null)
			throw new Exception();
		return theObject;
	}
		
	private static void writeElement(PrintWriter out, Element element, String indent) {
		String name = element.getTagName();
		out.print(indent + "<" + name);
		NamedNodeMap attributes = element.getAttributes();
		if (attributes != null) {
			for (int i = 0; i < attributes.getLength(); i++) {
				Attr attribute = (Attr)attributes.item(i);
				out.print(" " + attribute.getName() + "=\"" + attribute.getValue() + "\"");
			}
		}
		NodeList children = element.getChildNodes();
		if (children.getLength() == 0)
			out.println("/>");
		else {
			out.println(">");
			for (int i = 0; i < children.getLength(); i++)
				writeElement(out,(Element)children.item(i),indent+"   ");
			out.println(indent + "</" + name + ">");
		}
	}
		
	private static void buildExhibitElement(Document doc, Element exhibitElement, Exhibit exhibit) {
		String classname = exhibit.getClass().getName();
		exhibitElement.setAttribute("class",classname);
		if (! classname.equals(exhibit.getName()))
			exhibitElement.setAttribute("name", exhibit.getName());
		Parameter[] params = exhibit.getParameters();
		for (int i = params.length-1; i >= 0; i--) {
			Element paramElement = doc.createElement("parameter");
			buildParameterElement(doc,paramElement,params[i]);
			exhibitElement.appendChild(paramElement);
		}
		Decoration[] decorations = exhibit.getDecorations();
		for (int i = 0; i < decorations.length; i++) {
			Class<?> cl = decorations[i].getClass();
			if ( cl.getAnnotation(VMMSave.class) != null )
				addDecorationElement(doc,exhibitElement,decorations[i]);
		}
		addSavedProperties(exhibit,doc,exhibitElement);
		exhibit.addExtraXML(doc,exhibitElement);
		if (exhibit instanceof UserExhibit) {
			UserExhibit.Support userData = ((UserExhibit)exhibit).getUserExhibitSupport();
			Element userDataElement = doc.createElement("userdata");
			userData.addToXML(doc, userDataElement);
			exhibitElement.appendChild(userDataElement);
		}
	}
	
	/**
	 * Package private method is used in {@link UserExhibit}.
	 */
	static void buildParameterElement(Document doc, Element parameterElement, Parameter param) {
		String classname = param.getClass().getName();
		parameterElement.setAttribute("class",classname);
		String name = param.getName();
		if (name != null)
			parameterElement.setAttribute("name",name);
		Element valueElement = doc.createElement("value");
		valueElement.setAttribute("value", param.getValueAsString());
		valueElement.setAttribute("default", param.getDefaultValueAsString());
		if (param instanceof IntegerParam) {
			int min = ((IntegerParam)param).getMinimumValueForInput();
			int max = ((IntegerParam)param).getMaximumValueForInput();
			if (min > Integer.MIN_VALUE)
				valueElement.setAttribute("min",""+min);
			if (max < Integer.MAX_VALUE)
				valueElement.setAttribute("max",""+max);
		}
		else if (param instanceof RealParam) {
			double min = ((RealParam)param).getMinimumValueForInput();
			double max = ((RealParam)param).getMaximumValueForInput();
			if (min > Double.NEGATIVE_INFINITY)
				valueElement.setAttribute("min", Util.toExternalString(new Double(min)));
			if (max < Double.POSITIVE_INFINITY)
				valueElement.setAttribute("max", Util.toExternalString(new Double(max)));
		}
		else if (param instanceof ComplexParam) {
			Complex min = ((ComplexParam)param).getMinimumValueForInput();
			Complex max = ((ComplexParam)param).getMaximumValueForInput();
			if (min.re > Double.NEGATIVE_INFINITY)
				valueElement.setAttribute("min_re", Util.toExternalString(new Double(min.re)));
			if (max.re< Double.POSITIVE_INFINITY)
				valueElement.setAttribute("max_re", Util.toExternalString(new Double(max.re)));
			if (min.im > Double.NEGATIVE_INFINITY)
				valueElement.setAttribute("min_im", Util.toExternalString(new Double(min.im)));
			if (max.im< Double.POSITIVE_INFINITY)
				valueElement.setAttribute("max_im", Util.toExternalString(new Double(max.im)));
		}
		parameterElement.appendChild(valueElement);
		if (param instanceof Animateable) {
			Animateable anim = (Animateable)param;
			Element startElement = doc.createElement("start");
			startElement.setAttribute("value", anim.getAnimationStartAsString());
			startElement.setAttribute("default", anim.getDefaultAnimationStartAsString());
			parameterElement.appendChild(startElement);
			Element endElement = doc.createElement("end");
			endElement.setAttribute("value", anim.getAnimationEndAsString());
			endElement.setAttribute("default", anim.getDefaultAnimationEndAsString());
			parameterElement.appendChild(endElement);
		}
	}
	
	private static void buildViewElement(Document doc, Element viewElement, View view) {
		String classname = view.getClass().getName();
		viewElement.setAttribute("class",classname);
		if (! classname.equals(view.getName()))
			viewElement.setAttribute("name", view.getName());
		Parameter[] params = view.getParameters();
		for (int i = params.length-1; i >= 0; i--) {
			Element paramElement = doc.createElement("parameter");
			buildParameterElement(doc,paramElement,params[i]);
			viewElement.appendChild(paramElement);
		}
		Decoration[] decorations = view.getDecorations();
		for (int i = 0; i < decorations.length; i++) {
			Class<?> cl = decorations[i].getClass();
			if ( cl.getAnnotation(VMMSave.class) != null )
				addDecorationElement(doc,viewElement,decorations[i]);
		}
		Transform transform = view.getTransform();
		if (transform != null) {
			Element transformElement = makeTransformElement("transform", doc, transform);
			viewElement.appendChild(transformElement);
		}
		addSavedProperties(view,doc,viewElement);
		view.addExtraXML(doc,viewElement);
	}
	
	
	/**
	 * Adds properties to the element corresponding to fields of obj which are marked with
	 * the {@link vmm.core.VMMSave} annotation.  This is called for objects of type
	 * Decoration, Exhibit, and View.
	 */
	private static void addSavedProperties(Object obj, Document document, Element element) {
		Class cl = obj.getClass();
		while (!cl.equals(Object.class)) {
			Field[] fields = cl.getDeclaredFields();
			for (Field field : fields) {
				if (field.getAnnotation(VMMSave.class) != null) {
					String propName = field.getName();
					addProperty(obj,propName,document,element);
				}
			}
			cl = cl.getSuperclass();
		}
	}

	//--------------------- input helpers ------------------------------
	
	private static Exhibit buildExhibitFromElement(Element exhibitElement) throws IOException{
		String classname = exhibitElement.getAttribute("class").trim();
		Exhibit exhibit;
		String name;
		if (classname.trim().length() == 0)
			throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.MissingClassNameForExhibit"));
		try {
			exhibit = (Exhibit)Class.forName(classname).newInstance();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.CantMakeObject",classname));
		}
		name = exhibitElement.getAttribute("name").trim();
		if (name.length() > 0)
			exhibit.setName(name);
		NodeList paramCandidates = exhibitElement.getChildNodes();
		Element userDataElement = null;
		for (int i = 0; i < paramCandidates.getLength(); i++) {
			Node child = paramCandidates.item(i);
			if (child instanceof Element && ((Element)child).getTagName().equals("parameter")) {
				Element paramInfo = (Element)child;
				classname = paramInfo.getAttribute("class").trim();
				if (classname.length() == 0)
					throw new IOException("vmm.core.SaveAndRestore.error.MissingClassNameForParameter");
				name = paramInfo.getAttribute("name").trim();
				Parameter param = null;
				if (name.length() > 0) {  // parameter with this name might already exist; if so, don't create another
					param = exhibit.getParameterByName(name);
				}
				if (param == null) {
					try {
						param = (Parameter)createObjectFromClassName(classname,exhibit); //Class.forName(classname).newInstance();
					}
					catch (Exception e) {
						throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.CantMakeObject",classname));
					}
					if (name.length() > 0)
						param.setName(name);
					exhibit.addParameter(param);
				}
				setParamInfoFromElement(param,paramInfo);
			}
			else if (child instanceof Element && ((Element)child).getTagName().equals("decoration")) {
				Element decorationInfo = (Element)child;
				classname = decorationInfo.getAttribute("class");
				if (classname.length() == 0)
					throw new IOException("vmm.core.SaveAndRestore.error.MissingClassNameForDecoration");
				Decoration dec;
				try {
					dec = (Decoration)createObjectFromClassName(classname,exhibit);  //Class.forName(classname).newInstance();
				}
				catch (Exception e) {
					throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.CantMakeObject",classname));
				}
				readProperties(dec,decorationInfo);
				dec.readExtraXML(decorationInfo);
				exhibit.addDecoration(dec);
			}
			else if (child instanceof Element && ((Element)child).getTagName().equals("userdata"))
				userDataElement = (Element)child;
		}
		readProperties(exhibit,exhibitElement);
		exhibit.readExtraXML(exhibitElement);
		if (exhibit instanceof UserExhibit) {
			if (userDataElement == null)
				throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.MissingUserData"));
			UserExhibit.Support userInfo = ((UserExhibit)exhibit).getUserExhibitSupport();
			userInfo.readFromXML(userDataElement);
		}
		return exhibit;
	}
	
	/**
	 * This package-private method is used in {@link UserExhibit}
	 */
	static void setParamInfoFromElement(Parameter param, Element paramInfo) throws IOException {
		Element item = getChildElement(paramInfo,"value");
		if (item != null) {
			String str = item.getAttribute("value").trim();
			String dstr = item.getAttribute("default").trim();
			if (str.length() == 0)
				throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.MissingValueAttribute", "value"));
			try {
				param.setValueFromString(str);
			}
			catch (Exception e) {
				throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", str, "value"));
			}
			if (dstr.length() > 0) {
				try {
					param.setDefaultValueFromString(dstr);
				}
				catch (Exception e) {
					throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", str, "default"));
				}
			}
		}
		if (param instanceof IntegerParam) {
			String min = item.getAttribute("min").trim();
			String max = item.getAttribute("max").trim();
			if (min.length() > 0) {
				try {
					((IntegerParam)param).setMinimumValueForInput(Integer.parseInt(min));
				}
				catch (NumberFormatException e) {
					throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", min, "min"));
				}
			}
			if (max.length() > 0) {
				try {
					((IntegerParam)param).setMaximumValueForInput(Integer.parseInt(max));
				}
				catch (NumberFormatException e) {
					throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", max, "max"));
				}
			}
		}
		else if (param instanceof RealParam) {
			String min = item.getAttribute("min").trim();
			String max = item.getAttribute("max").trim();
			if (min.length() > 0) {
				try {
					Double d = (Double)Util.externalStringToValue(min, Double.TYPE);
					((RealParam)param).setMinimumValueForInput(d.doubleValue());
				}
				catch (Exception e) {
					throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", min, "min"));
				}
			}
			if (max.length() > 0) {
				try {
					Double d = (Double)Util.externalStringToValue(max, Double.TYPE);
					((RealParam)param).setMaximumValueForInput(d.doubleValue());
				}
				catch (Exception e) {
					throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", max, "max"));
				}
			}
		}
		else if (param instanceof ComplexParam) {
			String min_re = item.getAttribute("min_re").trim();
			String max_re = item.getAttribute("max_re").trim();
			String min_im = item.getAttribute("min_im").trim();
			String max_im = item.getAttribute("max_im").trim();
			Complex min = new Complex(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
			Complex max = new Complex(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
			if (min_re.length() > 0) {
				try {
					Double d = (Double)Util.externalStringToValue(min_re, Double.TYPE);
					min.re = d.doubleValue();
				}
				catch (Exception e) {
					throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", min_re, "min_re"));
				}
			}
			if (min_im.length() > 0) {
				try {
					Double d = (Double)Util.externalStringToValue(min_im, Double.TYPE);
					min.im = d.doubleValue();
				}
				catch (Exception e) {
					throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", min_im, "min_in"));
				}
			}
			if (max_re.length() > 0) {
				try {
					Double d = (Double)Util.externalStringToValue(max_re, Double.TYPE);
					max.re = d.doubleValue();
				}
				catch (Exception e) {
					throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", max_re, "max_re"));
				}
			}
			if (max_im.length() > 0) {
				try {
					Double d = (Double)Util.externalStringToValue(max_im, Double.TYPE);
					max.im = d.doubleValue();
				}
				catch (Exception e) {
					throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", max_im, "max_in"));
				}
			}
			((ComplexParam)param).setMinimumValueForInput(min);
			((ComplexParam)param).setMaximumValueForInput(max);
		}
		if ( ! (param instanceof Animateable))
			return;
		Animateable anim = (Animateable)param;
		item = getChildElement(paramInfo,"start");
		if (item != null) {
			String str = item.getAttribute("value").trim();
			String dstr = item.getAttribute("default").trim();
			if (str.length() == 0)
				throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.MissingValueAttribute", "value"));
			try {
				anim.setAnimationStartFromString(str);
			}
			catch (Exception e) {
				throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", str, "value"));
			}
			if (dstr.length() > 0) {
				try {
					anim.setDefaultAnimationStartFromString(dstr);
				}
				catch (Exception e) {
					throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", str, "default"));
				}
			}
		}
		item = getChildElement(paramInfo,"end");
		if (item != null) {
			String str = item.getAttribute("value").trim();
			String dstr = item.getAttribute("default").trim();
			if (str.length() == 0)
				throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.MissingValueAttribute", "value"));
			try {
				anim.setAnimationEndFromString(str);
			}
			catch (Exception e) {
				throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", str, "value"));
			}
			if (dstr.length() > 0) {
				try {
					anim.setDefaultAnimationEndFromString(dstr);
				}
				catch (Exception e) {
					throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadValueAttribute", str, "default"));
				}
			}
		}
	}
	
	private static void buildViewFromElement(Element viewElement, Exhibit exhibit) throws IOException {
		String classname = viewElement.getAttribute("class").trim();
		View view;
		String name;
		if (classname.trim().length() == 0)
			throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.MissingClassNameForView"));
		try {
			view = (View)createObjectFromClassName(classname,exhibit);  //Class.forName(classname).newInstance();
		}
		catch (Exception e) {
			throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.CantMakeObject",classname));
		}
		name = viewElement.getAttribute("name").trim();
		if (name.length() > 0)
			view.setName(name);
		view.setExhibit(exhibit);
		NodeList paramCandidates = viewElement.getChildNodes();
		for (int i = 0; i < paramCandidates.getLength(); i++) {
			Node child = paramCandidates.item(i);
			if (child instanceof Element && ((Element)child).getTagName().equals("parameter")) {
				Element paramInfo = (Element)child;
				classname = paramInfo.getAttribute("class").trim();
				if (classname.length() == 0)
					throw new IOException("vmm.core.SaveAndRestore.error.MissingClassNameForParameter");
				name = paramInfo.getAttribute("name").trim();
				Parameter param = null;
				if (name.length() > 0) {  // parameter with this name might already exist; if so, don't create another
					param = view.getParameterByName(name);
				}
				if (param == null) {
					try {
						param = (Parameter)createObjectFromClassName(classname,view);  //Class.forName(classname).newInstance();
					}
					catch (Exception e) {
						throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.CantMakeObject",classname));
					}
					if (name.length() > 0)
						param.setName(name);
					view.addParameter(param);
				}
				setParamInfoFromElement(param,paramInfo);
			}
			else if (child instanceof Element && ((Element)child).getTagName().equals("decoration")) {
				Element decorationInfo = (Element)child;
				classname = decorationInfo.getAttribute("class");
				if (classname.length() == 0)
					throw new IOException("vmm.core.SaveAndRestore.error.MissingClassNameForDecoration");
				Decoration dec;
				try {
					dec = (Decoration)createObjectFromClassName(classname,view);   //Class.forName(classname).newInstance();
				}
				catch (Exception e) {
					throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.CantMakeObject",classname));
				}
				readProperties(dec,decorationInfo);
				dec.readExtraXML(decorationInfo);
				view.addDecoration(dec);
			}
		}
		readProperties(view,viewElement);
		Element transformElement = getChildElement(viewElement,"transform");
		if (transformElement != null) {
			Transform transform = buildTransformFromElement(transformElement);
			view.setTransform(transform);
		}
		view.readExtraXML(viewElement);
	}
			

}
