	/**
	 * Returns current construction as a ggb file in form of a byte array.
	 * @return null if something went wrong 
	 */
	public synchronized byte [] getGGBfile()

	
	/**
	 * Returns current construction in XML format. May be used for saving.
	 */
	public synchronized String getXML() {
		return app.getXML();
	}
	
	/**
	 * Opens construction given in XML format. May be used for loading constructions.
	 */
	public synchronized void setXML(String xml)
	
	/**
	 * Evaluates the given XML string and changes the current construction. 
	 * Note: the construction is NOT cleared before evaluating the XML string. 	 
	 */
	public synchronized void evalXML(String xmlString)

	
	/**
	 * Evaluates the given string as if it was entered into GeoGebra's 
	 * input text field. 	 
	 */
	public synchronized boolean evalCommand(String cmdString) 

	/**
	 * Turns showing of error dialogs on (true) or (off). 
	 * Note: this is especially useful together with evalCommand().
	 */
	public synchronized void setErrorDialogsActive(boolean flag)
	
	/**
	 * Resets the initial construction (given in filename parameter) of this applet.	 
	 */
	public synchronized void reset()
	
	/**
	 * Refreshs all views. Note: clears traces in
	 * geometry window.
	 */
	public synchronized void refreshViews() 
			
	/**
	 * Loads a construction from a  file (given URL).	 
	 */
	public synchronized void openFile(String strURL) 
	

	
	/**
	 * Shows or hides the object with the given name in the geometry window.
	 */
	public synchronized void setVisible(String objName, boolean visible)
	
	/**
	 * Sets the layer of the object with the given name in the geometry window.
	 * Michael Borcherds 2008-02-27
	 */
	public synchronized void setLayer(String objName, int layer)
	
	/**
	 * Returns the layer of the object with the given name in the geometry window.
	 * returns layer, or -1 if object doesn't exist
	 * Michael Borcherds 2008-02-27
	 */
	public synchronized int getLayer(String objName) 
	
	/**
	 * Shows or hides a complete layer
	 * Michael Borcherds 2008-02-27
	 */
	public synchronized void setLayerVisible(int layer, boolean visible) 

	/**
	 * Sets the fixed state of the object with the given name.
	 */
	public synchronized void setFixed(String objName, boolean flag)
	
	/**
	 * Turns the trace of the object with the given name on or off.
	 */
	public synchronized void setTrace(String objName, boolean flag) 
	
	/**
	 * Shows or hides the label of the object with the given name in the geometry window.
	 */
	public synchronized void setLabelVisible(String objName, boolean visible) 
	
	/**
	 * Sets the label style of the object with the given name in the geometry window.
	 * Possible label styles are NAME = 0, NAME_VALUE = 1 and VALUE = 2.
	 */
	public synchronized void setLabelStyle(String objName, int style) 
	
	/**
	 * Shows or hides the label of the object with the given name in the geometry window.
	 */
	public synchronized void setLabelMode(String objName, boolean visible) 
	
	/**
	 * Sets the color of the object with the given name.
	 */
	public synchronized void setColor(String objName, int red, int green, int blue) 	
	
	/**
	 * Returns the color of the object as an hex string. Note that the hex-string 
	 * starts with # and uses upper case letters, e.g. "#FF0000" for red.
	 */
	public synchronized String getColor(String objName) 	
	
	/**
	 * Deletes the object with the given name.
	 */
	public synchronized void deleteObject(String objName) 
	
	/**
	 * Returns true if the object with the given name exists.
	 */
	public synchronized boolean exists(String objName) 
	
	/**
	 * Returns true if the object with the given name has a vaild
	 * value at the moment.
	 */
	public synchronized boolean isDefined(String objName) 	
	
	/**
	 * Returns the value of the object with the given name as a string.
	 */
	public synchronized String getValueString(String objName) 
	
	/**
	 * Returns the definition of the object with the given name as a string.
	 */
	public synchronized String getDefinitionString(String objName) 
	
	/**
	 * Returns the command of the object with the given name as a string.
	 */
	public synchronized String getCommandString(String objName) 
	
	/**
	 * Returns the x-coord of the object with the given name. Note: returns 0 if
	 * the object is not a point or a vector.
	 */
	public synchronized double getXcoord(String objName) 
	
	/**
	 * Returns the y-coord of the object with the given name. Note: returns 0 if
	 * the object is not a point or a vector.
	 */
	public synchronized double getYcoord(String objName) 
	
	/**
	 * Sets the coordinates of the object with the given name. Note: if the
	 * specified object is not a point or a vector, nothing happens.
	 */
	public synchronized void setCoords(String objName, double x, double y) 
	
	/**
	 * Returns the double value of the object with the given name. Note: returns 0 if
	 * the object does not have a value.
	 */
	public synchronized double getValue(String objName) 
	
	/**
	 * Sets the double value of the object with the given name. Note: if the
	 * specified object is not a number, nothing happens.
	 */
	public synchronized void setValue(String objName, double x) 
	
	/**
	 * Turns the repainting of all views on or off.
	 */
	public synchronized void setRepaintingActive(boolean flag) 	
	

	/*
	 * Methods to change the geometry window's properties	 
	 */
	
	/**
	 * Sets the Cartesian coordinate system in the graphics window.
	 */
	public synchronized void setCoordSystem(double xmin, double xmax, double ymin, double ymax) {
		app.getEuclidianView().setRealWorldCoordSystem(xmin, xmax, ymin, ymax);
	}
	
	/**
	 * Shows or hides the x- and y-axis of the coordinate system in the graphics window.
	 */
	public synchronized void setAxesVisible(boolean xVisible, boolean yVisible) {		
		app.getEuclidianView().showAxes(xVisible, yVisible);
	}	
	
	/**
	 * Shows or hides the coordinate grid in the graphics window.
	 */
	public synchronized void setGridVisible(boolean flag) {		
		app.getEuclidianView().showGrid(flag);
	}
	
	/*
	 * Methods to get all object names of the construction 
	 */
	
	private String [] objNames;
	private int lastGeoElementsIteratorSize = 0;
	
	/**
	 * 
	 * @return
	 */
	private String [] getObjNames() 
	
	/**
	 * Returns an array with all object names.
	 */
	public synchronized String [] getAllObjectNames() {			
		return getObjNames();
	}	
	
	/**
	 * Returns the number of objects in the construction.
	 */
	public synchronized int getObjectNumber() {					
		return getObjNames().length;			
	}	
	
	/**
	 * Returns the name of the n-th object of this construction.
	 */
	public synchronized String getObjectName(int i) {					
		String [] names = getObjNames();
					
		try {
			return names[i];
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * Returns the type of the object with the given name as a string (e.g. point, line, circle, ...)
	 */
	public synchronized String getObjectType(String objName) {
		GeoElement geo = kernel.lookupLabel(objName);
		return (geo == null) ? "" : geo.getObjectType().toLowerCase();
	}
	
	/**
	 * Sets the mode of the geometry window (EuclidianView). 
	 */
	public synchronized void setMode(int mode) {
		app.setMode(mode);
	}	
	
	
	/*
	 * Change listener implementation
	 * Java to JavaScript
	 */
	
	// maps between GeoElement and JavaScript function names
	private HashMap updateListenerMap;
	private ArrayList addListeners, removeListeners, renameListeners, updateListeners, clearListeners;
	private JavaToJavaScriptView javaToJavaScriptView;
	
	/**
	 * Registers a JavaScript function as an add listener for the applet's construction.
	 *  Whenever a new object is created in the GeoGebraApplet's construction, the JavaScript 
	 *  function JSFunctionName is called using the name of the newly created object as a single argument. 
	 */
	public synchronized void registerAddListener(String JSFunctionName) {
		if (JSFunctionName == null || JSFunctionName.length() == 0)
			return;				
						
		// init view
		initJavaScriptView();
		
		// init list
		if (addListeners == null) {
			addListeners = new ArrayList();			
		}		
		addListeners.add(JSFunctionName);				
		System.out.println("registerAddListener: " + JSFunctionName);
	}
	
	/**
	 * Removes a previously registered add listener 
	 * @see registerAddListener() 
	 */
	public synchronized void unregisterAddListener(String JSFunctionName) {
		if (addListeners != null) {
			addListeners.remove(JSFunctionName);
			System.out.println("unregisterAddListener: " + JSFunctionName);
		}	
	}	
	
	/**
	 * Registers a JavaScript function as a remove listener for the applet's construction.
	 * Whenever an object is deleted in the GeoGebraApplet's construction, the JavaScript 
	 * function JSFunctionName is called using the name of the deleted object as a single argument. 	
	 */
	public synchronized void registerRemoveListener(String JSFunctionName) {
		if (JSFunctionName == null || JSFunctionName.length() == 0)
			return;				
						
		// init view
		initJavaScriptView();
		
		// init list
		if (removeListeners == null) {
			removeListeners = new ArrayList();			
		}		
		removeListeners.add(JSFunctionName);				
		System.out.println("registerRemoveListener: " + JSFunctionName);
	}
	
	/**
	 * Removes a previously registered remove listener 
	 * @see registerRemoveListener() 
	 */
	public synchronized void unregisterRemoveListener(String JSFunctionName) {
		if (removeListeners != null) {
			removeListeners.remove(JSFunctionName);
			System.out.println("unregisterRemoveListener: " + JSFunctionName);
		}	
	}	
	
	/**
	 * Registers a JavaScript function as a clear listener for the applet's construction.
	 * Whenever the construction in the GeoGebraApplet's is cleared (i.e. all objects are removed), the JavaScript 
	 * function JSFunctionName is called using no arguments. 	
	 */
	public synchronized void registerClearListener(String JSFunctionName) {
		if (JSFunctionName == null || JSFunctionName.length() == 0)
			return;				
						
		// init view
		initJavaScriptView();
		
		// init list
		if (clearListeners == null) {
			clearListeners = new ArrayList();			
		}		
		clearListeners.add(JSFunctionName);				
		System.out.println("registerClearListener: " + JSFunctionName);
	}
	
	/**
	 * Removes a previously registered clear listener 
	 * @see registerClearListener() 
	 */
	public synchronized void unregisterClearListener(String JSFunctionName) {
		if (clearListeners != null) {
			clearListeners.remove(JSFunctionName);
			System.out.println("unregisterClearListener: " + JSFunctionName);
		}	
	}	
	
	/**
	 * Registers a JavaScript function as a rename listener for the applet's construction.
	 * Whenever an object is renamed in the GeoGebraApplet's construction, the JavaScript 
	 * function JSFunctionName is called using the name of the deleted object as a single argument. 	
	 */
	public synchronized void registerRenameListener(String JSFunctionName) {
		if (JSFunctionName == null || JSFunctionName.length() == 0)
			return;				
						
		// init view
		initJavaScriptView();
		
		// init list
		if (renameListeners == null) {
			renameListeners = new ArrayList();			
		}		
		renameListeners.add(JSFunctionName);				
		System.out.println("registerRenameListener: " + JSFunctionName);
	}
	
	/**
	 * Removes a previously registered rename listener.
	 * @see registerRenameListener() 
	 */
	public synchronized void unregisterRenameListener(String JSFunctionName) {
		if (renameListeners != null) {
			renameListeners.remove(JSFunctionName);
			System.out.println("unregisterRenameListener: " + JSFunctionName);
		}	
	}	
	
	/**
	 * Registers a JavaScript function as an update listener for the applet's construction.
	 * Whenever any object is updated in the GeoGebraApplet's construction, the JavaScript 
	 * function JSFunctionName is called using the name of the updated object as a single argument. 	
	 */
	public synchronized void registerUpdateListener(String JSFunctionName) {
		if (JSFunctionName == null || JSFunctionName.length() == 0)
			return;				
						
		// init view
		initJavaScriptView();
		
		// init list
		if (updateListeners == null) {
			updateListeners = new ArrayList();			
		}		
		updateListeners.add(JSFunctionName);				
		System.out.println("registerUpdateListener: " + JSFunctionName);
	}
	
	/**
	 * Removes a previously registered update listener.
	 * @see registerRemoveListener() 
	 */
	public synchronized void unregisterUpdateListener(String JSFunctionName) {
		if (updateListeners != null) {
			updateListeners.remove(JSFunctionName);
			System.out.println("unregisterUpdateListener: " + JSFunctionName);
		}	
	}	
	
	/**
	 * Registers a JavaScript update listener for an object. Whenever the object with 
	 * the given name changes, a JavaScript function named JSFunctionName 
	 * is called using the name of the changed object as the single argument. 
	 * If objName previously had a mapping JavaScript function, the old value 
	 * is replaced.
	 * 
	 * Example: First, set a change listening JavaScript function:
	 * ggbApplet.setChangeListener("A", "myJavaScriptFunction");
	 * Then the GeoGebra Applet will call the Javascript function
	 * myJavaScriptFunction("A");
	 * whenever object A changes.	
	 */
	public synchronized void registerObjectUpdateListener(String objName, String JSFunctionName) {
		if (JSFunctionName == null || JSFunctionName.length() == 0)
			return;		
		GeoElement geo = kernel.lookupLabel(objName);
		if (geo == null) return;
				
		// init view
		initJavaScriptView();
		
		// init map and view
		if (updateListenerMap == null) {
			updateListenerMap = new HashMap();			
		}
		
		// add map entry
		updateListenerMap.put(geo, JSFunctionName);		
		System.out.println("registerUpdateListener: object: " + objName + ", function: " + JSFunctionName);
	}
	
	/**
	 * Removes a previously set change listener for the given object.
	 * @see setChangeListener
	 */
	public synchronized void unregisterObjectUpdateListener(String objName) {
		if (updateListenerMap != null) {
			GeoElement geo = kernel.lookupLabel(objName);
			if (geo != null) {
				updateListenerMap.remove(geo);
				System.out.println("unregisterUpdateListener for object: " + objName);
			}
		}
	}				

