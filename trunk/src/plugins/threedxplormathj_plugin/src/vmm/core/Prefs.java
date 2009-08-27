/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

/**
 * Provides a simple mechanism for saving and retrieving user preferences,
 * with a default implementation based on the standard package java.util.prefs.
 * (There is a way to use a different implementation, which is provided to
 * allow, for example, using the WebStart Persistance service for storing the
 * preferences. See {@link #setPrefs(Prefs)}.)
 * <p>Preferences are stored as aset of key/value pairs, where both the
 * key and value are Strings.  The key string must be non-null and cannot
 * be the empty string.  (Also, it should probably not contain the "/" character.  
 * Some convenience methods
 * are provided for working with values of type int, double, and boolean, but
 * these just automate the converions of the values to and from Strings.
 * <p>Note that the design philosopy of this class is that it should always
 * be harmless to call methods from this class, even in applets where security
 * restrictions might make it impossible to access the Preferences database.
 */
public class Prefs {
	
	private static Prefs prefs; // The actual preferences implementation;
                                // might be replaced by an object belonging
                                // to a subclass.

	/**
	 * This string (in the default Prefs implementation) is prepended to the key
	 * parameter in all the methods defined in this class to give the key that
	 * is actually used in cals to the Java Preferences API.  It represents
	 * a unique path name that should not be used by any other program.
	 */
	protected final static String DEFAULT_PREFIX = "/org/virtualmathmuseum/vmm/";

	
	/**
	 * All preference values that have been associated with keys by calling
	 * {@link #put(String, String)}, {@link #putInt(String, int)}, etc., are
	 * not acutally sent to persistent storage until this method is called.
	 * They are, however, remembered by this class so that subsequent calles
	 * to {@link #get(String)}, {@link #getInt(String, int)}, etc., will
	 * return the correct values while the program is running.
	 * This method does not throw any exception (in the default implementation), but is not guaranteed to
	 * save the data.  For example, it will probably fail to save the data if called
	 * from an applet.
	 * @see #save(String) 
	 * @return true if the data is successfully saved, false if it cannot be saved.
	 *   The return value will probably be disregarded in most cases.
	 */
	synchronized public static boolean save() {
		if (prefs == null)
			return true;  // No preferences have been set, so there is nothing to save.
		else
			return prefs.doSave();
	}
	
	/**
	 * The preference value, if any, that has been associated with the
	 * given key is saved to persitent on-disk storage.
	 * @see #save()
	 * @param key the key whose value is to be saved to persistent storage.
	 * @return true if the data is successfully saved, false if it cannot be saved.
	 *   The return value will probably be disregarded in most cases.
	 */
	synchronized public static boolean save(String key) {
		if (prefs == null)
			return true;  // No preferences have been set, so there is nothing to save.
		else
			return prefs.doSave(key);
	}
	
	/**
	 * Associate a value with a key in the preferences database.  This is not
	 * automatically saved to persistent storage.  
	 * @see #save()
	 * @param key a non-null, non-empty string that identifies the preference value.
	 *    A null or empty string will generate an IllegalArgumentException (in the
	 *    default implementation).
	 * @param value The value that is to be associated to the key.  The value
	 *    can be null.  However, note that no way is provided to differentiate
	 *    between a key that has a null value and a key that has no value at
	 *    all.
	 */
	synchronized public static void put(String key, String value) {
		if (prefs == null)
			prefs = new Prefs();
		prefs.doPut(key,value);
	}
	
	/**
	 * Associate a value with a key in the preferences database, and then save that
	 * preference to persistent storage if possible.  The return value tells whether
	 * the value was successfully saved to persistent storage.  This methos simply
	 * calls {@link #put(String, String)} and {@link #save(String)}.
	 */
	synchronized public static boolean putAndSave(String key, String value) {
		prefs.doPut(key,value);
		return prefs.doSave(key);
	}
	
	/**
	 * Get the preferences value associated with a given key.  This method never
	 * throws an exception (in the default implemtation). 
	 * @see #put(String, String)
	 * @param key a non-null, non-empty string that identifies the preference value.
	 *   If the key is a null or empty string, no error occurs, but the return value
	 *   is null.
	 * @return the preference value associated with the key.  This value
	 *   can be null.  If a value has
	 *   been set for the key with a <code>put</code> method, that value is
	 *   returned.  Otherwise, an attempt is made to read the value from
	 *   persistant storage; if attempt fails, the return value is null.
	 */
	synchronized public static String get(String key) {
		if (prefs == null)
			prefs = new Prefs();
		return prefs.doGet(key);
	}
	
	/**
	 * A convenience method that calls <code>put(key,""+value)</code>.
	 * @see #put(String, String)
	 */
	public static void putInt(String key, int value) {
		put(key,""+value);
	}
	
	/**
	 * A convenience method that calls <code>put(key,""+value)</code>,
	 * except that the values Double.NaN, Double.POSITIVE_INFINITY,
	 * and Double.NEGATIVE_INFINITY are encoded as "NaN", "+INF",
	 * and "-INF", respectively.
	 * @see #put(String, String)
	 */
	public static void putDouble(String key, double value) {
		if (Double.isNaN(value))
			put (key,"NaN");
		else if (value == Double.POSITIVE_INFINITY)
			put(key,"+INF");
		else if (value == Double.NEGATIVE_INFINITY)
			put(key,"-INF");
		else
			put(key,""+value);
	}
	
	/**
	 * A convenience method that calls <code>put(key,"true")</code>
	 * or <code>put(key,"false")</code>, depending on the value.
	 * @see #put(String, String)
	 */
	public static void putBoolean(String key, boolean value) {
		put(key, value? "true" : "false");
	}
	
	/**
	 * Returns the preference string associated with a given key, or deflt if
	 * no value is associated with the key.  This method never
	 * throws an exception.
	 * @param key the key whose associated preference value is to be returned
	 * @param deflt the value that will be returned if the key has no
	 *    associated preference value or if an error occurs when trying
	 *    to read the preferenece value from persistent storage.
	 */
	public static String get(String key, String deflt) {
		String val = get(key);
		return (val == null? deflt : val);
	}
	
	/**
	 * Returns <code>Integer.parseInt(get(key))</code>, or deflt if
	 * no value is associated with the given key or if the string
	 * associated with that key is not an integer.  This method never
	 * throws an exception.
	 * @see #get(String)
	 * @param key the key whose associated preference value is to be returned
	 * @param deflt the value that will be returned if the key has no
	 *    associated preference value or if the value does not represent
	 *    an integer, or if an error occurs while trying to read the
	 *    value from persistent storage.
	 */
	public static int getInt(String key, int deflt) {
		String val = get(key);
		if (val == null)
			return deflt;
		else {
			try {
				return Integer.parseInt(val);
			}
			catch (Exception e) {
				return deflt;
			}
		}
	}
	
	/**
	 * Returns <code>Double.parseDouble(get(key))</code>, or deflt if
	 * no value is associated with the given key or if the string
	 * associated with that key is not an integer.  The strings
	 * "NaN", "+INF", and "-INF" are traslated into the special
	 * values Double.NaN, Double.POSITIVE_INFINTY, and Double.NEGATIVE_INFINITY.
	 * This method never throws an exception.
	 * @see #get(String)
	 * @param key the key whose associated preference value is to be returned
	 * @param deflt the value that will be returned if the key has no
	 *    associated preference value or if the value does not represent
	 *    a real number, or if an error occurs while trying to read the
	 *    value from persistent storage.
	 */
	public static double getDouble(String key, double deflt) {
		String val = get(key);
		if (val == null)
			return deflt;
		else if (val.equals("NaN"))
			return Double.NaN;
		else if (val.equals("+INF"))
			return Double.POSITIVE_INFINITY;
		else if (val.equals("-INF"))
			return Double.NEGATIVE_INFINITY;
		else {
			try {
				return Double.parseDouble(val);
			}
			catch (Exception e) {
				return deflt;
			}
		}
	}
	
	/**
	 * Returns <code>true</code> if <code>get(key)</code> equals "true",
	 * or <code>false</code> if <code>get(key)</code> equals "false", or deflt
	 * if the value of <code>get(key)</code> is some other value.
	 * This method never throws an exception.
	 * @see #get(String)
	 * @param key the key whose associated preference value is to be returned
	 * @param deflt the value that will be returned if the key has no
	 *    associated preference value or if the value does not represent
	 *    a boolean value, or if an error occurs while trying to read the
	 *    value from persistent storage.
	 */
	public static boolean getBoolean(String key, boolean deflt) {
		String val = get(key);
		if (val == null)
			return deflt;
		else if (val.equalsIgnoreCase("true"))
			return true;
		else if (val.equalsIgnoreCase("false"))
			return false;
		else
			return deflt;
	}
	
	/**
	 * Set the object of type Prefs that actually handles the storage and retrival
	 * of prefernce values.  By default, the object is just an object of type
	 * Prefs, but it could be changed to an object belonging to a sub-class of
	 * Prefs by calling this method.  This method should ordinarily be called
	 * at the start of a program, before any calls to other static methods in
	 * this class.  If <code>prefs</code> is null, then a default object will
	 * be created of type Prefs.
	 */
	public static void setPrefs(Prefs prefs) {
		Prefs.prefs = prefs;
	}
	
	/**
	 * Returns the object that is used to handle storage of preference values.
	 * The return value is non-null.
	 * @see #setPrefs(Prefs)
	 */
	public static Prefs getPrefs() {
		if (prefs == null)
			prefs = new Prefs();
		return prefs;
	}
	
	
	//---------------------------- Default implementation of preferences ----------------------
	
	private HashMap<String, String> map;  // Storage for key/value pairs in memory.
	private Preferences preferencesRoot;  // For accessing user preferences stored on disk.
	
	/**
	 * Prevents the creation of objects of type Prefs, except by a subclass.
	 * This is here only to make it possible to create objects belonging to
	 * a subclass.  This constructor does nothing.
	 */
	protected Prefs() {
	}
	
	/**
	 * Get the preference value associated with a key.
	 * @param key The non-null, non-empty string that identifies the preference value.
	 *    If the value is null or empty, no error occurs; the return value is null.
	 * @return The associated value.  If a value has been set with a call to
	 *   {@link #doPut(String, String)}, then that value is returned.  If not,
	 *   then an attempt is made to read the value from user preferenced
	 *   in persistent storage.  If an error occurs while tyring to read the
	 *   value from persistene storage, then the return value is null.
	 *   Note that the return value is cached so that only one access of
	 *   persistent storage will be made for a given key, even if there
	 *   are multiple calls to this method for this key.
	 */
	protected String doGet(String key) {
		if (key == null || key.length() == 0)
			return null;
		if (map == null)
			map = new HashMap<String,String>();
		key = DEFAULT_PREFIX + key;
		if (map.containsKey(key))
			return map.get(key);
		String value;
		try {
			if (preferencesRoot == null)
				preferencesRoot = Preferences.userRoot();
			value = preferencesRoot.get(key, null);
		}
		catch (Exception e) {
			value = null;
		}
		map.put(key,value);
		return value;
	}
	
	/**
	 * Associates a given preference value with a given key.  The associate is
	 * made only in memory, not to persistent storage.  For the value to
	 * be saved to persistent storage, {@link #doSave()} must be called.
	 * @param key the non-null, non-empty key string.  A null or empty string
	 *    will cause an IllegalArgumentException.  The string should ordinarly not
	 *    contain the "/" character (which would put the preference value into
	 *    a differenet Preferences node on disk).
	 * @param value the value to be associated with the key.  This value can be null.
	 *    Null values are generally to be interpreted as "use the default".
	 */
	protected void doPut(String key, String value) {
		if (key == null || key.length() == 0)
			throw new IllegalArgumentException("key string must be non-null and non-empty.");
		if (map == null)
			map = new HashMap<String,String>();
		map.put(DEFAULT_PREFIX + key, value);
	}
	
	/**
	 * Tries to save any modified preference values to persistent storage, using
	 * Java's Preferences API.  No exception will be thrown if the attempt fails.
	 * (Null values are not saved to persistent storage; if the value is null,
	 * the associated key is removed from the Preferences database.)
	 * @return  true, if the attempt to save the values succeeds, false if it fails.
	 *    If there is nothing to save, the return value is true; a return value of
	 *    false indicates that an error occurred.
	 */
	protected boolean doSave() {
		if (map == null || map.size() == 0)
			return true;
		try {
			if (preferencesRoot == null)
				preferencesRoot = Preferences.userRoot();
			for (Map.Entry<String,String> item : map.entrySet()) {
				String key = item.getKey();
				String value = item.getValue();
				String oldValue = preferencesRoot.get(key,null);
				if (value == null && oldValue != null) {
					preferencesRoot.remove(key);
				}
				else if (value != null && !value.equals(oldValue)) {
					preferencesRoot.put(key,value);
				}
			}
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * Tries to save any modified preference values to persistent storage, using
	 * Java's Preferences API.  No exception will be thrown if the attempt fails.
	 * (Null values are not saved to persistent storage; if the value is null,
	 * the associated key is removed from the Preferences database.)
	 * @return  true, if the attempt to save the values succeeds, false if it fails.
	 *    If there is nothing to save, the return value is true; a return value of
	 *    false indicates that an error occurred.
	 */
	protected boolean doSave(String key) {
		if (map == null || map.size() == 0)
			return true;
		key = DEFAULT_PREFIX + key;
		if (! map.containsKey(key))
			return true;
		String value = map.get(key);
		try {
			if (preferencesRoot == null)
				preferencesRoot = Preferences.userRoot();
			String oldValue = preferencesRoot.get(key,null);
			if (value == null && oldValue != null) {
				preferencesRoot.remove(key);
			}
			else if (value != null && !value.equals(oldValue)) {
				preferencesRoot.put(key,value);
			}
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

}
