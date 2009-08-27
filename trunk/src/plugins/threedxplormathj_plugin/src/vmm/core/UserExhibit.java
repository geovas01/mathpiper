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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import vmm.actions.AbstractActionVMM;
import vmm.conformalmap.UserConformalMap;
import vmm.functions.ComplexFunction;
import vmm.functions.Function;
import vmm.functions.Function1;
import vmm.functions.Function2;
import vmm.functions.Function3;
import vmm.functions.ParseError;
import vmm.functions.Parser;
import vmm.planecurve.parametric.UserPlaneCurveParametric;


/**
 * A UserExhbit is meant to be an Exhibit whose data is computed by one
 * or more functions entered by the user.  A UserExhibit class should be
 * declared as a subclass of some subclass of Exhibit and it should implement
 * the UserExhbit interface.  All the methods specified by this interface,
 * except for {@link #getUserExhibitSupport()}, are already defined in the
 * Exhibit class and do not have to be reimplmented in the user exhibit
 * class.  The user exhibit class should define an instance variable of
 * type {@link UserExhibit.Support}.  It should create and customize
 * an object of that type in its contstructor, and should return that
 * object as the value of {@link #getUserExhibitSupport()}.
 * <p>User exhibit classes can be very simple.  For examples, see
 * {@link UserPlaneCurveParametric} and {@link UserConformalMap}.
 * <p>Note that a user exhibits that is a subclass of {@link vmm.core3D.Exhibit3D}
 * should implement {@link vmm.core3D.UserExhibit3D} instead of UserExhibit.
 */
public interface UserExhibit extends Parameterizable {
	
	/**
	 * This method should return a <b>non-null</b> object that is created
	 * and customized in the constructor of the class that implements UserExhbit.
	 */
	public UserExhibit.Support getUserExhibitSupport();
	
	/**
	 * This method is already defined in class {@link Exhibit} and will not
	 * ordinarily be redefined in a user exhibit class.
	 * @see Exhibit#getParameters()
	 */
	public Parameter[] getParameters();
	
	/**
	 * This method is already defined in class {@link Exhibit} and will not
	 * ordinarily be redefined in a user exhibit class.
	 * @see Exhibit#getDefaultWindow()
	 */
	public double[] getDefaultWindow();
	
	/**
	 * This method is already defined in class {@link Exhibit} and will not
	 * ordinarily be redefined in a user exhibit class.
	 * @see Exhibit#setDefaultWindow(double[])
	 */
	public void setDefaultWindow(double[] window);
	
	/**
	 * This method is already defined in class {@link Exhibit} and will not
	 * ordinarily be redefined in a user exhibit class.
	 * @see Exhibit#getDefaultView()
	 */
	public View getDefaultView();
	
	/**
	 * An object of type FunctionInfo holds information about one the functions, to be entered
	 * by the user, that compute the data for the user exhibit.  An object of type
	 * FunctionInfo is created by the methods {@link UserExhibit.Support#addRealFunction(String, String, String[])}
	 * and {@link UserExhibit.Support#addComplexFunction(String, String, String[])}; it is not possible to
	 * create an object of this type directly.
	 * A FunctionInfo object contains either a real-valued function, of type
	 * {@link Function}, or a complex-valued function, of type {@link ComplexFunction},
	 * depending on which method created it.
	 */
	public static class FunctionInfo {
		private Object function;  // Will be either Function or ComplexFunction
		final private boolean isComplex;
		final private String name;
		final private String[] argumentNames;
		private String prompt;
		private String definition;
		private FunctionInfo(String name, String definition, String[] argumentNames, boolean isComplex) {
		   this.name = name;
		   this.definition = definition;
		   this.argumentNames = argumentNames;
		   this.isComplex = isComplex;
		   setPrompt(null);
		}
		/**
		 * Returns the value of the real-valued function contained in this FunctionInfo object,
		 * at a specified list of input arguments for the functions.  This method should
		 * only be called for a FucntionInfo that was created by the
		 * method {@link UserExhibit.Support#addRealFunction(String, String, String[])}.
		 * @throws ClassCastException if the function is complex-valued, not real-valued
		 * @throws IllegalArgumentException if the number of arguments does not match the arity of the function
		 */
		public double realFunctionValue(double... arg) {
			return ((Function)function).value(arg);
		}
		/**
		 * Returns the value of the complex-valued function contained in this FunctionInfo object,
		 * at a specified list of input arguments for the functions.  This method should
		 * only be called for a FucntionInfo that was created by the
		 * method {@link UserExhibit.Support#addComplexFunction(String, String, String[])}.
		 * @throws ClassCastException if the function is real-valued, not complex-valued
		 * @throws IllegalArgumentException if the number of arguments does not match the arity of the function
		 */
		public Complex complexFunctionValue(Complex... arg) {
			return ((ComplexFunction)function).value(arg);
		}
		/**
		 * Returns the value of the complex-valued function contained in this FunctionInfo object,
		 * at a specified list of input arguments for the functions.  The arguments are specified
		 * by values of type double that give the real and imaginary parts of the complex arguments
		 * that are to be passed to the function.  This method should
		 * only be called for a FucntionInfo that was created by the
		 * method {@link UserExhibit.Support#addComplexFunction(String, String, String[])}.
		 * That is, <code>complexFunctionValue(re1,im1,re2,im2...)</code> is equivalent to
		 * <code>complexFunction(new Complex(re1,im1), new Complex(re2,im2)...)</code.
		 * @throws ClassCastException if the function is real-valued, not complex-valued
		 * @throws IllegalArgumentException if the number of arguments is not equal to the
		 *    arity of the function multiplied by two.
		 */
		public Complex complexFunctionValue(double... arg) {
			return ((ComplexFunction)function).value(arg);
		}
		/**
		 * Returns the real-valued function contained in this FunctionInfo object, or returns
		 * null if no function has been created yet.  <b>Warning: Just after a FunctionInfo
		 * object has been created, the return value is null; it will only be non-null after
		 * the user has had a chance to enter the definition of the function.  Furthermove,
		 * the function can change later, if {@link UserExhibit.Support#showChangeDialog(Display, View)}
		 * is called.}  This means that you should ordinarily not keep a copy of the value returned by
		 * this method, and that there is generally little reason to use it.  Note that
		 * if the arity of the fucntion is 1, 2, or 3, then the return value will actuall be
		 * of type {@link Function1}, {@link Function2}, or {@link Function3}.
		 * @throws ClassCastException if the function is complex-valued, not real-valued.
		 */
		public Function getRealFunction() {
			return (Function)function;
		}
		/**
		 * Returns the complex-valued function contained in this FunctionInfo object, or returns
		 * null if no function has been created yet.  <b>Warning: Just after a FunctionInfo
		 * object has been created, the return value is null; it will only be non-null after
		 * the user has had a chance to enter the definition of the function.  Furthermove,
		 * the function can change later, if {@link UserExhibit.Support#showChangeDialog(Display, View)}
		 * is called.}  This means that you should ordinarily not keep a copy of the value returned by
		 * this method, and that there is generally little reason to use it.  Note that
		 * if the arity of the fucntion is 1, 2, or 3, then the return value will actuall be
		 * of type {@link vmm.functions.ComplexFunction1}, {@link vmm.functions.ComplexFunction2}, or {@link vmm.functions.ComplexFunction3}.
		 * @throws ClassCastException if the function is real-valued, not complex-valued.
		 */
		public ComplexFunction getComplexFunction() {
			return (ComplexFunction)function;
		}
		/**
		 * Returns the names of the arguments to the function, as specified in the parameter list
		 * of {@link UserExhibit.Support#addRealFunction(String, String, String[])} or
		 * {@link UserExhibit.Support#addComplexFunction(String, String, String[])} when this
		 * FunctionInfo object was created.
		 */
		public String[] getArgumentNames() {
			return argumentNames;
		}
		/**
		 * Returns the names of the arguments to the function, as entered by the user.
		 */
		public String getDefinition() {
			return definition;
		}
		/**
		 * Tells whether the function is complex-valued or real-valued.
		 */
		public boolean getIsComplex() {
			return isComplex;
		}
		/**
		 * Returns the name of the function, as specified in the parameter list
		 * of {@link UserExhibit.Support#addRealFunction(String, String, String[])} or
		 * {@link UserExhibit.Support#addComplexFunction(String, String, String[])} when this
		 * FunctionInfo object was created.
		 */
		public String getName() {
			return name;
		}
		/**
		 * Set the label to be used next to the text fiels in the dialog box where the user
		 * enters the definition of the fuction.  By default, the prompt is of a form such
		 * as "f(x,y)" where "f" is the name of the function and "x" and "y" are the arguments
		 * of the function.  You only need to use this method if you would like a different
		 * prompt.
		 */
		public void setPrompt(String prompt) {
			if (prompt != null)
				this.prompt = prompt;
			else {
				this.prompt = name;
				this.prompt += "(";
				if (argumentNames != null && argumentNames.length > 0) {
					for (int i  = 0; i < argumentNames.length; i++) {
						this.prompt += argumentNames[i];
						this.prompt += (i == argumentNames.length-1)? ") = " : ",";
					}
				}
			}
		}
	}
	
	/**
	 * An object of type UserExhibit.Support holds the information need for a user
	 * exhibit and provides some methods for manipulating that information.  Most
	 * users of this class will only have to declare an instance variable of
	 * type UserExhibit.Support and create and customize the object in the constructor
	 * of the user exhibit class (saving references to the {@link UserExhibit.FunctionInfo}
	 * objects created by the support object).  This Support object should be returned
	 * as the value of {@link UserExhibit#getUserExhibitSupport()}.
	 * <p>Note that the once the support object has been created, the system handles most
	 * of the details, such as showing the dialog box where the user will enter the
	 * definitions of the functions and saving the values of those functions when the
	 * user exhibit is saved to a settings file.
	 */
	public static class Support {
		
		final private UserExhibit exhibit;
		private ArrayList<FunctionInfo> functions = new ArrayList<FunctionInfo>(); 
		private ArrayList<Parameter> functionParameters = new ArrayList<Parameter>();
		private boolean showWindow = true;
		private boolean allowNewParameters = true;
		private boolean allowChangeUserDataCommand = true;
		
		/**
		 * Create a UserExhibit.Support object for use by a specified UserExhibit
		 * @param exhibit the UserExhibit that uses this support object.  The value of this
		 * parameter will ordinarily be specified as "this".
		 */
		public Support(UserExhibit exhibit) {
			this.exhibit = exhibit;
		}
			
		/**
		 * In addition to the arguments of the function, the definition of a function
		 * can contain refereneces to "parameters".  (The values of these parameters can
		 * be set by the user in the "SetParameters" dialog.)  This method adds a
		 * real-valued parameter to the user info, so that it can be used in the
		 * function definitions.  The name of the parameter should be something that
		 * will be appropriate for use in a function definition.
		 * @param p the non-null parameter to be made available for use in the function
		 * definitions.  Note that the parameter can belong to the subclass
		 * {@link VariableParamAnimateable}; if that is the case, then the user will
		 * be able to specify upper and lower limits for morphing the parameter.
		 */
		public void addFunctionParameter(VariableParam p) {
			functionParameters.add(p);
			p.setOwner(exhibit);
		}
		
		/**
		 * In addition to the arguments of the function, the definition of a function
		 * can contain refereneces to "parameters".  (The values of these parameters can
		 * be set by the user in the "SetParameters" dialog.)  This method adds a
		 * complex-valued parameter to the user info, so that it can be used in the
		 * function definitions.  The name of the parameter should be something that
		 * will be appropriate for use in a function definition.
		 * <p>Note that, by default, the user can change the list of parameters.
		 * See {@link #setAllowNewParameters(boolean)}.
		 * @param p the non-null parameter to be made available for use in the function
		 * definitions.  Note that the parameter can belong to the subclass
		 * {@link ComplexVariableParamAnimateable}; if that is the case, then the user will
		 * be able to specify upper and lower limits for morphing the parameter.
		 */
		public void addFunctionParameter(ComplexVariableParam p) {
			functionParameters.add(p);
			p.setOwner(exhibit);
		}
		
		/**
		 * Add a real-valued function to the user info.  The user will be able to modify the definition
		 * of the function in a dialog box.  A reference to the return value of this function should
		 * be saved; that reference is needed to compute the value of the function.
		 * @param name The name of the function, which will be used only in the label for the text field
		 *   where the user enters the definition.
		 * @param definition The initial value for the defintion of the function.  This can be modified
		 *    by the user.  The defintion will be processed by a {@link Parser} to produce the
		 *    actual function.
		 * @param argumentName The names of the arguments of the function, such as the "x" and
		 *    "y" in "f(x,y)".  These arguments can be used in the definition of the function.
		 * @return  A FunctionInfo object that will contain the function and that can be used to
		 *    compute values of the function.  (Note that the function is null just after this 
		 *    function is called.)
		 */
		public FunctionInfo addRealFunction(String name, String definition, String... argumentName) {
			FunctionInfo f = new FunctionInfo(name, definition, argumentName, false);
			functions.add(f);
			return f;
		}
		
		/**
		 * Add a complex-valued function to the user info.  The user will be able to modify the definition
		 * of the function in a dialog box.  A reference to the return value of this function should
		 * be saved; that reference is needed to compute the value of the function.
		 * @param name The name of the function, which will be used only in the label for the text field
		 *   where the user enters the definition.
		 * @param definition The initial value for the defintion of the function.  This can be modified
		 *    by the user.  The defintion will be processed by a {@link Parser} to produce the
		 *    actual complex-valued function.
		 * @param argumentName The names of the arguments of the function, such as the "z" and
		 *    "w" in "f(z,w)".  These arguments can be used in the definition of the function.
		 * @return  A FunctionInfo object that will contain the function and that can be used to
		 *    compute values of the function.  (Note that the function is null just after this 
		 *    function is called.)
		 */
		public FunctionInfo addComplexFunction(String name, String definition, String... argumentName) {
			FunctionInfo f = new FunctionInfo(name, definition, argumentName, true);
			functions.add(f);
			return f;
		}
		
		/**
		 * Returns the number of functions, real and complex, that have been added to this
		 * Support object.  (This method is used by the system, but is not usually needed
		 * otherwise.)
		 */
		public int getFunctionCount() {
			return functions.size();
		}
		
		/**
		 * Returns the FunctionInfo object for the i-th function that was added to
		 * this object.  (This method is used by the system, but is not usually needed
		 * otherwise.)
		 */
		public FunctionInfo getFunctionInfo(int index) {
			return functions.get(index);
		}
		
		/**
		 * Gets the list of parameters that can be used in the definitions of the functions.
		 * (These are not function arguments, but are other parameters.)  The parameters 
		 * were added either by the user or by {@link #addFunctionParameter(ComplexVariableParam)}
		 * or {@link #addFunctionParameter(VariableParam)}.  (This method is used by the system,
		 * but is not usually needed otherwise.)
		 */
		public Parameter[] getFunctionParameters() {
			Parameter[] array = new Parameter[functionParameters.size()];
			functionParameters.toArray(array);
			return array;
		}
		
		/**
		 * @see #setAllowChangeUserDataCommand(boolean)
		 */
		public boolean getAllowChangeUserDataCommand() {
			return allowChangeUserDataCommand;
		}

		/**
		 * Sets the value of the allowChangeUserData property.  The default value is true.
		 * When this property is true, a "Change User Data" command appears in the 3DXM Settings
		 * menu.  This command calls up the dialog box where the user enters the data for the
		 * user exhibit.  If this property is false, then that command is omitted and the
		 * user will not be able to change the user data once it has been created.
		 */
		public void setAllowChangeUserDataCommand(boolean allowChangeUserDataCommand) {
			this.allowChangeUserDataCommand = allowChangeUserDataCommand;
		}

		/**
		 * @see #setAllowNewParameters(boolean)
		 */
		public boolean getAllowNewParameters() {
			return allowNewParameters;
		}

		/**
		 * Sets the value of the allowNewParameters property.  The default value is true.
		 * If this property is true,  then the user will be able to add new parameters
		 * for use in function definitions and will also be able to remove existing parameters.
		 * "Add Parameter" and "Remove Parameter" buttons are added to the dialog box to
		 * implement this.  If the value of the property if false, then the buttons are
		 * omitted from the dialog box, and the only parameters that are available for
		 * use in the functions are those added by {@link #addFunctionParameter(VariableParam)}
		 * and {@link #addFunctionParameter(ComplexVariableParam)}.
		 */
		public void setAllowNewParameters(boolean allowNewParameters) {
			this.allowNewParameters = allowNewParameters;
		}

		/**
		 * @see #setShowWindow(boolean)
		 */
		public boolean getShowWindow() {
			return showWindow;
		}

		/**
		 * Sets the value of the showWindow property.  The default value is true.  If
		 * the value is true, then a panel is added to the dialog box where the user can
		 * specify the xy-window (that is, the range of values that are visible in the
		 * view) for the exhibit.  Setting the property to false removes this panel
		 * from the dialog box.
		 */
		public void setShowWindow(boolean showWindow) {
			this.showWindow = showWindow;
		}
		
		/**
		 * This method initializes the user exhibit to the default values from its
		 * create dialog.  It has the same effect as if {@link #showCreateDialog(Display)}
		 * were called and the user clicked "OK" without making any changes to the data
		 * in the dialog box, except that the dialog is not actually shown.  This is
		 * used to initially load a user exhibit in the main program for 3D-XplorMath-J
		 * and in the 3DXM LauncherApplet; it will probably not be used otherwise.
		 * @return A view that is configured according to the default data, or null
		 * if an error occurs.  There should not be an error, unless there is a bug
		 * in the user exhibit.
		 */
		public View defaults() {
			View view = exhibit.getDefaultView();
			Dialog dialog = createDialog(null,view,true);
			boolean ok = dialog.getData();
			if (!ok)
				return null;
			finish(dialog,view,true);
			return view;
		}
		
		/**
		 * Shows the dialog box where the user enters the data for the exhibit.
		 * This method is called by the system just after an object of type UserExhibit
		 * is constructed.  It will not ordinarily be used directly.
		 * @param display The display that serves as the parent component of the dialog box; can be null.
		 */
		public View showCreateDialog(Display display) {
			View view = exhibit.getDefaultView();
			Dialog dialog = createDialog(display,view,true);
			boolean ok = dialog.showDialog();
			if (!ok)
				return null;
			finish(dialog,view,true);
			return view;
		}
		
		/**
		 * Shows the dialog box where the user enters the data for the exhibit.
		 * This method is called by the system in response to a "Change User
		 * Data" command.  It will not ordinarily be used directly.
		 */
		public boolean showChangeDialog(Display display, View view) {
			Dialog dialog = createDialog(display,view,false);
			boolean ok = dialog.showDialog();
			if (!ok)
				return false;
			finish(dialog,view,false);
			return true;
		}
		
		/**
		 * Creates the "Change User Data" command for adding to the 3DXM Settings menu.
		 * This is called by the system and will not ordinarily be used directly.
		 */
		public AbstractActionVMM makeChangeUserDataAction(final View view) {
			return new AbstractActionVMM(I18n.tr("vmm.core.UserExhibitDialog.SetUserData")) {
				public void actionPerformed(ActionEvent evt) {
					if ( showChangeDialog(view.getDisplay(), view) ) {
						if (view.getExhibit() != null)
							view.getExhibit().forceRedraw();  // ( probalby done automatically, but to be safe... )
					}
				}
			};
		}
		
		/**
		 * This is called by the system when the user exhibit is being saved to a settings
		 * file to write the user data to the settings file.  This method will not oridinarly
		 * be used directly.
		 */
		public void addToXML(Document containingDocument, Element userDataElement) {
			for (Parameter param : functionParameters) {
				Element paramElement = containingDocument.createElement("functionParam");
				SaveAndRestore.buildParameterElement(containingDocument, paramElement, param);
				paramElement.setAttribute("isComplex", param instanceof ComplexVariableParam? "yes" : "no");
				paramElement.setAttribute("isAnimateable", param instanceof Animateable? "yes" : "no");
				userDataElement.appendChild(paramElement);
			}
			for (FunctionInfo func : functions) {
				Element functionElement = containingDocument.createElement("function");
				functionElement.setAttribute("name", func.name);
				functionElement.setAttribute("definition",func.definition);
				userDataElement.appendChild(functionElement);
			}
			SaveAndRestore.addProperties(this, 
					new String[] { "allowChangeUserDataCommand", "showWindow", "allowNewParameters" }, 
					containingDocument, userDataElement);
		}
		
		/**
		 * This method is called by the system when the user exhibit is being read
		 * from a settings file.  This method will not ordinarily be called directly.
		 */
		public void readFromXML(Element userDataElement) throws IOException {
			ArrayList<Parameter> functionParameters = new ArrayList<Parameter>();
			Parser parser = new Parser();
			NodeList paramCandidates = userDataElement.getChildNodes();
			for (int i = 0; i < paramCandidates.getLength(); i++) {
				Node child = paramCandidates.item(i);
				if (child instanceof Element && ((Element)child).getTagName().equals("functionParam")) {
					Element paramInfo = (Element)child;
					Parameter param;
					String name = paramInfo.getAttribute("name").trim();
					boolean isComplex = "yes".equalsIgnoreCase(paramInfo.getAttribute("isComplex"));
					boolean isAnimateable = "yes".equalsIgnoreCase(paramInfo.getAttribute("isAnimateable"));
					if (isComplex) {
						if (isAnimateable)
							param = new ComplexVariableParamAnimateable(name,new Complex());
						else
							param = new ComplexVariableParam(name,new Complex());
					}
					else {
						if (isAnimateable)
							param = new VariableParamAnimateable(name,0);
						else
							param = new VariableParam(name,0);
					}
					SaveAndRestore.setParamInfoFromElement(param,paramInfo);
					functionParameters.add(param);
					if (param instanceof ComplexVariableParam)
						parser.add(((ComplexVariableParam)param).getVariable());
					else
						parser.add(((VariableParam)param).getVariable());
				}
			}
			for (int i = 0; i < paramCandidates.getLength(); i++) {
				Node child = paramCandidates.item(i);
				if (child instanceof Element && ((Element)child).getTagName().equals("function")) {
					Element functionElement = ((Element)child);
					String name = functionElement.getAttribute("name").trim();
					String definition = functionElement.getAttribute("definition").trim();
					FunctionInfo info = null;
					for (FunctionInfo f : functions)
						if (f.getName().equalsIgnoreCase(name)) {
							info = f;
							break;
						}
					if (info == null)
						throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.UnknownFunctionInUserExhibitData",name));
					info.definition = definition;
					try {
						if (info.isComplex)
							info.function = parser.parseComplexFunction(info.name, definition, info.argumentNames);
						else
							info.function = parser.parseFunction(info.name, definition, info.argumentNames);							
					}
					catch (ParseError e) {
						throw new IOException(I18n.tr("vmm.core.SaveAndRestore.error.BadExpressionDefinition"));
					}
				}
			}
			for (Parameter p : functionParameters)
				p.setOwner(null);
			this.functionParameters = functionParameters;
			for (Parameter p : functionParameters)
				p.setOwner(exhibit);
			SaveAndRestore.readProperties(this, userDataElement);
		}
		
		/**
		 * Called by {@link #showCreateDialog(Display)} and {@link #showChangeDialog(Display, View)}
		 * to actually create the dialog box.  This can be overridden by a subclass to
		 * customize the dialog box.
		 */
		protected Dialog createDialog(Display display,View view, boolean creating) {
			Component c = display;
			while (c != null && ! (c instanceof JFrame) )
				c = c.getParent();
			Dialog dialog = new Dialog((JFrame)c, "");
			if (showWindow) {
				double[] window = creating? exhibit.getDefaultWindow() : view.getRequestedWindow();
				dialog.showViewWindowLimits(window[0],window[1],window[2],window[3]);
			}
			if (allowNewParameters)
				dialog.allowNewParameters();
			return dialog;
		}
		
		/**
		 * Called by {@link #showCreateDialog(Display)} and {@link #showChangeDialog(Display, View)}
		 * after the user has clicked the OK button to dismiss the dialog box.  It can be
		 * overridden in a subclass to customize the way the data in the dialog box is applied.
		 */
		protected void finish(Dialog dialog, View view, boolean creating) {
			view.setWindow(dialog.getWindow());
			exhibit.setDefaultWindow(dialog.getWindow());
		}
		
		/**
		 * An object of type ExtraPanel can be added to the dialog box to get some
		 * extra data from the user.  It would generally be added in the {@link UserExhibit.Support#createDialog(Display, View, boolean)}
		 * method.  See {@link UserExhibit.Support.Dialog#addExtraPanel(vmm.core.UserExhibit.Support.ExtraPanel)}.
		 */
		protected abstract static class ExtraPanel extends JPanel {
			public ExtraPanel(String title) {
				setBorder(
						BorderFactory.createCompoundBorder(
						BorderFactory.createEmptyBorder(4,0,4,0),
						BorderFactory.createCompoundBorder(
								BorderFactory.createTitledBorder(
										BorderFactory.createLineBorder(Color.black),
										title ),
								BorderFactory.createEmptyBorder(5,5,5,5) )
						));
			}
			public void commitData() {
			}
			abstract public void checkData() throws IllegalArgumentException;
		}
		
		
		/**
		 * Implements the dialog box where the user enters the data for a user exhibit.
		 */
		protected class Dialog extends JDialog {
			
			private JFrame parent;
			private ArrayList<ParameterInput> parameterInputs = new ArrayList<ParameterInput>();
			private ArrayList<JTextField> functionInputs = new ArrayList<JTextField>();
			private ArrayList<ExtraPanel> extraPanels;
			
			private JPanel expressionPromptPanel;
			private JPanel expressionInputPanel;
			private JPanel parameterPanel;
			private boolean parameterPanelAdded;
			private JPanel windowPanel;
			private Box inputPane;
			private JPanel buttons;
			private JButton okButton, cancelButton, addParameterButton, removeParameterButton;
			
			private double[] window;
			private JTextField[] windowInputs;
			
			private boolean showParameters;
			
			private boolean canceled;

			protected Dialog(JFrame parent, String title) {
				super(parent,title,true);
				this.parent = parent;
				Parameter[] exhibitParameters = exhibit.getParameters();
				JPanel contentPane = new JPanel();
				contentPane.setLayout(new BorderLayout(8,8));
				setContentPane(contentPane);
				inputPane = Box.createVerticalBox();
				JScrollPane scroller = new JScrollPane(inputPane,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				contentPane.add(scroller,BorderLayout.CENTER);

				okButton = new JButton(I18n.tr("buttonNames.OK"));
				cancelButton = new JButton(I18n.tr("buttonNames.Cancel"));
				addParameterButton = new JButton(I18n.tr("buttonNames.AddParameter"));
				removeParameterButton = new JButton(I18n.tr("buttonNames.RemoveParameter"));
				ActionListener buttonHandler = new ButtonHandler();
				okButton.addActionListener(buttonHandler);
				cancelButton.addActionListener(buttonHandler);
				addParameterButton.addActionListener(buttonHandler);
				removeParameterButton.addActionListener(buttonHandler);
				removeParameterButton.setEnabled(functionParameters.size() > 0);
				buttons = new JPanel();
				buttons.setLayout(new FlowLayout(FlowLayout.RIGHT,8,8));
				buttons.add(cancelButton);
				buttons.add(okButton);
				contentPane.add(buttons,BorderLayout.SOUTH);
				
				JPanel expressionPanel = new JPanel();
				expressionPanel.setBorder(
						BorderFactory.createCompoundBorder(
							BorderFactory.createEmptyBorder(4,0,4,0),
							BorderFactory.createCompoundBorder(
									BorderFactory.createTitledBorder(
											BorderFactory.createLineBorder(Color.black),
											I18n.tr("vmm.core.UserExhibitDialog.EnterExpressions") ),
									BorderFactory.createEmptyBorder(5,5,5,5) )
							));
				expressionPanel.setLayout(new BorderLayout(5,5));
				expressionPromptPanel = new JPanel();
				expressionPromptPanel.setLayout(new GridLayout(0,1,5,5));
				expressionPanel.add(expressionPromptPanel,BorderLayout.WEST);
				expressionInputPanel = new JPanel();
				expressionInputPanel.setLayout(new GridLayout(0,1,5,5));
				expressionPanel.add(expressionInputPanel,BorderLayout.CENTER);
				for (FunctionInfo funcInfo : functions) {
					expressionPromptPanel.add(new JLabel(funcInfo.prompt,JLabel.RIGHT));
					JTextField in = new JTextField(funcInfo.definition,40);
					functionInputs.add(in);
					expressionInputPanel.add(in, BorderLayout.CENTER);
				}
				inputPane.add(expressionPanel);
				parameterPanel = new JPanel();
				parameterPanel.setBorder(
						BorderFactory.createCompoundBorder(
							BorderFactory.createEmptyBorder(4,0,4,0),
							BorderFactory.createCompoundBorder(
									BorderFactory.createTitledBorder(
											BorderFactory.createLineBorder(Color.black),
											I18n.tr("vmm.core.UserExhibitDialog.Parameters") ),
									BorderFactory.createEmptyBorder(5,5,5,5) )
							));
				parameterPanel.setLayout(new GridLayout(0,4,5,5));
				Color red = new Color(180,0,0);
				JLabel label = new JLabel(I18n.tr("vmm.core.ParameterDialog.ParameterName"), JLabel.CENTER);
				label.setForeground(red);
				parameterPanel.add(label);
				label = new JLabel(I18n.tr("vmm.core.ParameterDialog.ParameterValue"), JLabel.CENTER);
				label.setForeground(red);
				parameterPanel.add(label);
				label = new JLabel(I18n.tr("vmm.core.ParameterDialog.AnimationStartValue"), JLabel.CENTER);
				label.setForeground(red);
				parameterPanel.add(label);
				label = new JLabel(I18n.tr("vmm.core.ParameterDialog.AnimationEndValue"), JLabel.CENTER);
				label.setForeground(red);
				parameterPanel.add(label);
				if (functionParameters.size() > 0) {
					for (Parameter p : functionParameters)
						addParamInputs(p);
				}
				if (exhibitParameters != null && exhibitParameters.length > 0) {
					JPanel exhibitParamPanel = new JPanel();
					exhibitParamPanel.setBorder(
							BorderFactory.createCompoundBorder(
								BorderFactory.createEmptyBorder(4,0,4,0),
								BorderFactory.createCompoundBorder(
										BorderFactory.createTitledBorder(
												BorderFactory.createLineBorder(Color.black),
												I18n.tr("vmm.core.UserExhibitDialog.ExhibitParameters") ),
										BorderFactory.createEmptyBorder(5,5,5,5) )
								));
					exhibitParamPanel.setLayout(new GridLayout(0,4,5,5));
					for (int i = 0; i < exhibitParameters.length; i++) {
						Parameter p = exhibitParameters[i];
						exhibitParamPanel.add(new JLabel(p.getTitle() + " = ", JLabel.RIGHT));
						ParameterInput in0 = p.createParameterInput(ParameterInput.VALUE);
						parameterInputs.add(in0);
						exhibitParamPanel.add(in0);
						if (p instanceof Animateable) {
							ParameterInput in1 = p.createParameterInput(ParameterInput.ANIMATION_START);
							parameterInputs.add(in1);
							exhibitParamPanel.add(in1);
							ParameterInput in2 = p.createParameterInput(ParameterInput.ANIMATION_END);
							parameterInputs.add(in2);
							exhibitParamPanel.add(in2);
						}
						else {
							exhibitParamPanel.add(new JLabel()); // place holders
							exhibitParamPanel.add(new JLabel());
						}
					}
					inputPane.add(exhibitParamPanel);
				}
				if (extraPanels != null) {
					for (ExtraPanel extraInput : extraPanels)
						inputPane.add(extraInput);
				}
				buttons.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(
						KeyStroke.getKeyStroke("ESCAPE"), "cancel");
				buttons.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(
						KeyStroke.getKeyStroke("ENTER"), "ok");
				buttons.getActionMap().put("cancel",new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						cancelButton.doClick();
					}
				});
				buttons.getActionMap().put("ok",new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						okButton.doClick();
					}
				});
				addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent evt) {
						canceled = true;
						dispose();
					}
				});
			}
			
			/**
			 * Add an extra input panel to the dialog box.
			 */
			public void addExtraPanel(ExtraPanel inputPanel) {
				if (extraPanels == null)
					extraPanels = new ArrayList<ExtraPanel>();
				extraPanels.add(inputPanel);
				inputPane.add(inputPanel);
			}
			
			private boolean showDialog()  {
				if (parent != null)
					setLocation(parent.getX()+20, parent.getY()+40);
				checkBounds();
				canceled = false;
				setVisible(true);
				return !canceled;
			}
			
			private void checkBounds() {
				pack();
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				if (getHeight() > screenSize.height - 30)
					setSize(getWidth() + 30, screenSize.height - 30);
				if (getLocation().y + getHeight() > screenSize.height - 3)
					setLocation(getLocation().x, screenSize.height - getHeight() - 3);
				if (getLocation().x + getWidth() > screenSize.width - 3)
					setLocation(screenSize.width - 3 - getWidth(), getLocation().y);
			}
			
			private class ButtonHandler implements ActionListener {
				public void actionPerformed(ActionEvent evt) {
					Object src = evt.getSource();
					if (src == cancelButton) {
						canceled = true;
						dispose();
					}
					else if (src == addParameterButton)
						doAddParameter();
					else if (src == removeParameterButton)
						doRemoveParameter();
					else if (src == okButton) {
						if (getData()) {
							canceled = false;
							dispose();
						}
					}
				}
			}
			
			private void makeWindowInputPanel(double[] initialWindow) {
				windowPanel = new JPanel();
				windowPanel.setBorder(
						BorderFactory.createCompoundBorder(
							BorderFactory.createEmptyBorder(4,0,4,0),
							BorderFactory.createCompoundBorder(
									BorderFactory.createTitledBorder(
											BorderFactory.createLineBorder(Color.black),
											I18n.tr("vmm.core.UserExhibitDialog.ViewLimits") ),
											BorderFactory.createEmptyBorder(5,5,5,5) )
							));
				windowPanel.setLayout(new GridLayout(0,4,5,5));
				windowInputs = new JTextField[initialWindow.length];
				for (int i = 0; i < initialWindow.length; i++)
					windowInputs[i] = new JTextField("" + initialWindow[i]);
				windowPanel.add(new JLabel(I18n.tr("common.xmin") + " = ", JLabel.RIGHT));
				windowPanel.add(windowInputs[0]);
				windowPanel.add(new JLabel(I18n.tr("common.xmax") + " = ", JLabel.RIGHT));
				windowPanel.add(windowInputs[1]);
				windowPanel.add(new JLabel(I18n.tr("common.ymin") + " = ", JLabel.RIGHT));
				windowPanel.add(windowInputs[2]);
				windowPanel.add(new JLabel(I18n.tr("common.ymax") + " = ", JLabel.RIGHT));
				windowPanel.add(windowInputs[3]);
			}
			
			private void errorMessage(String message) {
				JOptionPane.showMessageDialog(this,message,I18n.tr("vmm.core.UserExhibitDialog.errorTitle"),JOptionPane.ERROR_MESSAGE);
			}
			
			private void allowNewParameters() {
				if (showParameters == false) {
					showParameters = true;
					buttons.add(addParameterButton,0);
					buttons.add(removeParameterButton,0);
					buttons.validate();
				}
			}
			
			private void doAddParameter() {
				JCheckBox isComplex = new JCheckBox(I18n.tr("vmm.core.UserExhibitDialog.ParameterIsComplex"));
				JCheckBox isAnimateable = new JCheckBox(I18n.tr("vmm.core.UserExhibitDialog.ParameterIsAnimateable"),true);
				JPanel top = new JPanel();
				top.add(new JLabel(I18n.tr("vmm.core.UserExhibitDialog.ParameterName")));
				JTextField nameInput = new JTextField(4);
				top.add(nameInput);
				JPanel panel = new JPanel();
				panel.setLayout(new GridLayout(3,1,10,10));
				panel.add(top);
				panel.add(isComplex);
				panel.add(isAnimateable);
				int response = JOptionPane.showConfirmDialog(this, panel, I18n.tr("vmm.core.UserExhibitDialog.AddParameterTitle"), 
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (response == JOptionPane.CANCEL_OPTION)
					return;
				String paramName = nameInput.getText();
				if (paramName == null || paramName.trim().length() == 0)
					return;
				paramName = paramName.trim();
				if (!Character.isLetter(paramName.charAt(0))) {
					errorMessage(I18n.tr("vmm.core.UserExhibitDialog.error.BadParamName"));
					return;
				}
				for (int i = 0; i < paramName.length(); i++)
					if (!Character.isLetterOrDigit(paramName.charAt(i))) {
						errorMessage(I18n.tr("vmm.core.UserExhibitDialog.error.BadParamName"));
						return;
					}
				for (Parameter p : functionParameters)
					if (p.getName().equalsIgnoreCase(paramName)) {
						errorMessage(I18n.tr("vmm.core.UserExhibitDialog.error.DuplicateParam",paramName));
						return;
					}
				Parameter p;
				if (isComplex.isSelected())
					p = isAnimateable.isSelected() ? new ComplexVariableParamAnimateable(paramName,new Complex(1), new Complex(-5), new Complex(5))
									: new ComplexVariableParam(paramName,new Complex(1));
				else
					p = isAnimateable.isSelected() ? new VariableParamAnimateable(paramName,1,-5,5) : new VariableParam(paramName,1);
				functionParameters.add(p);
				p.setOwner(exhibit);
				addParamInputs(p);
				removeParameterButton.setEnabled(true);
				validate();
				checkBounds();
			}
			
			private void doRemoveParameter() {
				final JDialog dlg = new JDialog(this,I18n.tr("vmm.core.UserExhibitDialog.RemoveParameterDialogTitle"),true);
				dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				JPanel deleteButtons = new JPanel();
				deleteButtons.setLayout(new GridLayout(0,1,7,7));
				deleteButtons.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(Color.black),
						BorderFactory.createEmptyBorder(8,8,8,8) ));
				for (int i = 0; i < functionParameters.size(); i++) {
					JButton b = new JButton(functionParameters.get(i).getTitle());
					deleteButtons.add(b);
					final int paramNum = i;
					b.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							dlg.dispose();
							Parameter p = functionParameters.get(paramNum);
							functionParameters.remove(paramNum);
							p.setOwner(null);
							parameterPanel.remove(4*(paramNum+1)+3);
							parameterPanel.remove(4*(paramNum+1)+2);
							parameterPanel.remove(4*(paramNum+1)+1);
							parameterPanel.remove(4*(paramNum+1));
							for (int i = parameterInputs.size()-1; i >= 0; i--)
								if (parameterInputs.get(i).getParameter() == p)
									parameterInputs.remove(i);
							Dialog.this.validate();
							if (functionParameters.size() == 0)
								removeParameterButton.setEnabled(false);
							checkBounds();
						}
					});
				}
				JPanel content = new JPanel();
				dlg.setContentPane(content);
				content.setLayout(new BorderLayout(7,7));
				content.setBorder(BorderFactory.createEmptyBorder(7,7,7,7));
				content.add(new JLabel(I18n.tr("vmm.core.UserExhibitDialog.RemoveParameterDialogPrompt")), BorderLayout.NORTH);
				JPanel middle = new JPanel();
				middle.add(deleteButtons);
				content.add(middle, BorderLayout.CENTER);
				JPanel bottom = new JPanel();
				JButton cb = new JButton(I18n.tr("buttonNames.Cancel"));
				cb.addActionListener( new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						dlg.dispose();
					}
				});
				bottom.add(cb);
				content.add(bottom, BorderLayout.SOUTH);
				dlg.pack();
				dlg.setLocation(getLocation().x + 15, getLocation().y + 25);
				dlg.setVisible(true);
			}
			
			/**
			 * Checks the data entered by the user.  If all the data is legal, then the changes are applied
			 * to any parameters that appear in the dialog box.  The return value indicates whether the data
			 * was legal.
			 */
			private boolean getData() {
				Object[] newFunctions = new Object[functionInputs.size()];
				Parser parser = new Parser();
				for (Parameter p : functionParameters) {
					if (p instanceof ComplexVariableParam)
						parser.add(((ComplexVariableParam)p).getVariable());
					else
						parser.add(((VariableParam)p).getVariable());
				}
				for (int i = 0; i < functionInputs.size(); i++) {
					try {
						FunctionInfo funcInfo = functions.get(i);
						if (funcInfo.isComplex)
						    newFunctions[i] = parser.parseComplexFunction(funcInfo.name,functionInputs.get(i).getText(),funcInfo.argumentNames);
						else
							newFunctions[i] = parser.parseFunction(funcInfo.name,functionInputs.get(i).getText(),funcInfo.argumentNames);
					}
					catch (ParseError e) {
						errorMessage(I18n.tr("vmm.core.UserExhibitDialog.error.parseError", functionInputs.get(i).getText(), e.getMessage()));
						functionInputs.get(i).requestFocus();
						functionInputs.get(i).selectAll();
						return false;
					}
				}
				if (windowInputs != null) {
					window = new double[4];
					for (int i = 0; i < 4; i++) {
						try {
							window[i] = Double.parseDouble(windowInputs[i].getText());
						}
						catch (NumberFormatException e) {
							String which = "common.";
							if (i == 0)
								which += "xmin";
							else if (i == 1)
								which += "xmax";
							else if (i == 2)
								which += "ymin";
							else if (i == 3)
								which += "ymax";
							errorMessage(I18n.tr("vmm.core.UserExhibitDialog.error.badReal", I18n.tr(which)));
							windowInputs[i].requestFocus();
							windowInputs[i].selectAll();
							return false;
						}
					}
					if (window[1] <= window[0]) {
						errorMessage(I18n.tr("vmm.core.UserExhibitDialog.error.badRange", I18n.tr("common.xmax"), I18n.tr("common.xmin")));
						windowInputs[1].requestFocus();
						windowInputs[1].selectAll();
						return false;
					}
					if (window[3] <= window[2]) {
						errorMessage(I18n.tr("vmm.core.UserExhibitDialog.error.badRange", I18n.tr("common.ymax"), I18n.tr("common.ymin")));
						windowInputs[1].requestFocus();
						windowInputs[1].selectAll();
						return false;
					}
				}
				for (int i = 0; i < parameterInputs.size(); i++) {
					String error = parameterInputs.get(i).checkContents();
					if (error != null) {
						errorMessage(error);
						return false;
					}
				}
				if (extraPanels != null) {
					for (ExtraPanel p : extraPanels) {
						try {
							p.checkData();
						}
						catch (IllegalArgumentException e) {
							errorMessage(e.getMessage());
						}
					}
				}
 				for (int i = 0; i < functionInputs.size(); i++) {
					functions.get(i).function = newFunctions[i];
					functions.get(i).definition = functionInputs.get(i).getText();
				}
				for (int i = 0; i < parameterInputs.size(); i++) {
					parameterInputs.get(i).setValueAndDefaultFromContents();
				}
				if (extraPanels != null) {
					for (ExtraPanel p : extraPanels)
						p.commitData();
				}
				return true;
			}
					
			private void addParamInputs(Parameter p) {
				ParameterInput input0 = p.createParameterInput(ParameterInput.VALUE);
				parameterInputs.add(input0);
				parameterPanel.add(new JLabel(p.getTitle() + " = ", JLabel.RIGHT));
				parameterPanel.add(input0);
				if (p instanceof Animateable) {
					ParameterInput input1 = p.createParameterInput(ParameterInput.ANIMATION_START);
					ParameterInput input2 = p.createParameterInput(ParameterInput.ANIMATION_END);
					parameterInputs.add(input1);
					parameterInputs.add(input2);
					parameterPanel.add(input1);
					parameterPanel.add(input2);
				}
				else {
					parameterPanel.add(new JLabel());
					parameterPanel.add(new JLabel());
				}
				if (!parameterPanelAdded) {
					inputPane.add(parameterPanel,1);
					parameterPanelAdded = true;
				}
			}
			
			private void showViewWindowLimits(double xmin, double xmax, double ymin, double ymax) {
				makeWindowInputPanel(new double[] { xmin, xmax, ymin, ymax } );
				inputPane.add(windowPanel);
			}
			
			/**
			 * Returns the xy-window entered by the user.
			 * @return  An array of length 4 containing xmin, xmax, ymin, ymax.
			 */
			public double[] getWindow() {
				if (window == null)
					return null;
				else
					return window.clone();
			}

		}

	}
	
}
