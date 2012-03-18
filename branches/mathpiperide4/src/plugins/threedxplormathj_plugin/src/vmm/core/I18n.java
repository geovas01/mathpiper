/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This class implements internationalization by providing facilites for getting strings from
 * properties files.  A single default property file is used, but other files can be added.
 * All files that have been added are searched in the reverse of the order in which they 
 * were added.  The default file is searched last.  This class does not generate errors;
 * if a property file cannot be found for a specified resource name, it is ignored.  If no
 * value can be found for a specified key, then either null or the key itself is returned rather than 
 * generating an error, depending on which method was called.
 * <p>The default properties file name is "strings.properties" in the directory vmm/resources.
 * Property files for other locales should be given names such as, for example, strings_fr.properties, '
 * and stored in the same location.
 */
public class I18n {
	
	private static ArrayList<ResourceBundle> bundles = new ArrayList<ResourceBundle>();  // resource bundles that have been loaded
	private static ArrayList<String> bundleNames = new ArrayList<String>();  // The file names for the resource bundles
	private static Locale locale;
	private static String defaultPropertiesFileName = "vmm.resources.strings";
	
	/**
	 * Sets a locale to use when searching for property files.  This does not affect
	 * any property files that have already been loaded.  If the locale is null (which is the
	 * default), then the default locale will be used -- which is almost always what you want to do!
	 * Property files that have already been loaded are reloaded.  (This method just calls
	 * "setLocale(locale,true)".)  It is not usually necessary to call this method. It could
	 * be used, for example, to give the user a choice of languages.
	 */
	public static void setLocale(Locale locale) {
		setLocale(locale,true);
	}
	
	/**
	 * Sets a locale to use when searching for property files.  If the <code>reload</code> argument is
	 * false, then this will not affect property files that have already been loaded; if <code>reload</code>
	 * is true, then all property files that have already been loaded are discarded and new ones
	 * are loaded using the new locale.
	 * If the locale is null (which is the default), then the default locale will be used -- 
	 * which is almost always what you want to do!  It is not usually necessary to call this method. It could
	 * be used, for example, to give the user a choice of languages.
	 */
	public synchronized static void setLocale(Locale locale, boolean reload) {
		I18n.locale = locale;
		if (reload) {
			bundles.clear();
			ArrayList<String> names = bundleNames;
			bundleNames = new ArrayList<String>();
			for (int i = 0; i < names.size(); i++)
				load(names.get(i));
		}
	}
	
	/**
	 * Returns the locale that is currently being used by this class.
	 * @return the current locale for internationalization.  The return value is non-null.
	 * If no non-null locale has been set by {@link #setLocale(Locale)}, then the default
	 * locale for the Java Virtual Machine is returned.
	 */
	public static Locale getLocale() {
		if (locale == null)
			return Locale.getDefault();
		else
			return locale;
	}
	
	/**
	 * Adds a file to the list of property files that are searched when looking
	 * for a string.  Property files are searched in the reverse of the order
	 * in which they are added.  That is, if a file contains a key that also
	 * occurred in a previously added file, the value in the new file will hide
	 * the value in the old file.
	 * @param fileName The resource name for the property file to be added.  The bundle is
	 * obtained using the <code>getBundle</code> method in the <code>ResourceBundle</code> class.
	 * If the file name is null, nothing is done.  If the file name is a duplicate of
	 * a name that was used previously, the bundle is NOT added to the search path for
	 * a second time.  Note that the file name should be given as a resource name, such
	 * as "vmm.resources.strings", that can be used to locate the resource, rather than as an actual file name.
	 * @return The return value indicates whether a resource bundle was found.  If the return
	 * value is false, it means that no property file with the specified resource name was
	 * found.
	 */
	public synchronized static boolean addFile(String fileName) {
		if (fileName == null)
			return false;
		for (int i = 0; i < bundleNames.size(); i++)
			if (bundleNames.get(i).equals(fileName))
				return bundles.get(i) != null;
		if (bundles.size() == 0 && !fileName.equals(defaultPropertiesFileName))
			load(defaultPropertiesFileName);
		ResourceBundle b = load(fileName);
		return b != null;
	}
	
	/**
	 * Loads a propery file with a specified resource name and returns the resulting
	 * resource bundle.  Returns null if no property file can be found.
	 */
	private synchronized static ResourceBundle load(String name) {
		if (name == null)
			return null;
		ResourceBundle bundle;
		try {
			if (locale == null)
				bundle = ResourceBundle.getBundle(name);
			else
				bundle = ResourceBundle.getBundle(name,locale);
		}
		catch (MissingResourceException e) {
			bundle = null;
		}
		bundles.add(bundle);
		bundleNames.add(name);
		return bundle;
	}
	
	/**
	 * Finds the value associated with a specified key.  All loaded reseource bundles are
	 * checked, in the reverse of the order in which they were added.  (The resource bundle
	 * with the default name is automatically added first, so is the last one checked.)
	 * The name of this method, "tr", is short for "translate".
	 * @return The value associated with the key.  If the key does not occur in any of the
	 * loaded resource bundles (or if key is null), then the key itself is returned.  
	 */
	public static String tr(String key) {
		String s = trIfFound(key);
		return (s == null)? key : s;
	}
	
	
	/**
	 * A convenience method that calls MessageFormat.format(tr(key), arg...).
	 * That is, the translation of key can contain substrings of the form {0}, {1}, {2}, ..., and the
	 * arg parameters is substituted for those substrings wherever thye occur.
	 * @param key String to be translated, by calling tr(key).  The translation can contain
	 * substrings of the form {0}, {1}, {2}...
	 * @param arg The strings (or other objects) that are to be substituted for {0}, {1}, {2}, ...
	 * @return The translated string, with any occurance of {0}, {1}, {2}... replaced by the arg's.   
	 * If no translated string is found, then the substitution is done on the key string.
	 */
	public static String tr(String key, Object... arg) {
		return MessageFormat.format(tr(key), arg);
	}
	
	
//	/**
//	 * A convenience method that calls MessageFormat.format(tr(key), new Object[] { arg0, arg1 }).
//	 * That is, the translation of key can contain a substrings of the form {0} or {1}, and the
//	 * arg0 and arg1 parameters are substituted for {0} and {1} wherever they occur.
//	 * @param key String to be translated, by calling tr(key).  The translation can contain
//	 * substrings of the form {0} and {1}.
//	 * @param arg0 The string (or other object) that is to be substituted for {0}.
//	 * @param arg1 The string (or other object) that is to be substituted for {1}.
//	 * @return The translated string, after substitution of arg0 and arg1.  If no translated string is found,
//	 * then the substitution is done on the key string.
//	 */
//	public static String tr(String key, Object arg0, Object arg1) {
//		return MessageFormat.format(tr(key), new Object[] { arg0, arg1 });
//	}
//	
//	/**
//	 * A convenience method that calls MessageFormat.format(tr(key), new Object[] { arg0, arg1, arg2 }).
//	 * That is, the translation of "key" can contain a substrings of the form {0}, {1}, or {2}, and the
//	 * arg0, arg1, and arg2 parameters are substituted for {0}, {1}, and {2} wherever they occur.
//	 * @param key String to be translated, by calling tr(key).  The translation can contain
//	 * substrings of the form {0}, {1}, and {2}.
//	 * @param arg0 The string (or other object) that is to be substituted for {0}.
//	 * @param arg1 The string (or other object) that is to be substituted for {1}.
//	 * @param arg2 The string (or other object) that is to be substituted for {2}.
//	 * @return The translated string, after substitution of arg0 and arg1.  If no translated string is found,
//	 * then the substitution is done on the key string.
//	 */
//	public static String tr(String key, Object arg0, Object arg1, Object arg2) {
//		return MessageFormat.format(tr(key), new Object[] { arg0, arg1, arg2 });
//	}
//	
	/**
	 * Does the same thing as I18n.tr(key), except that if no value is found for the key,
	 * the return value is null.
	 * @return The value associated with the key.  If the key does not occur in any of the
	 * loaded resource bundles (or if key is null), then null  is returned.
	 * @see #tr(String)  
	 */
	public static String trIfFound(String key) {
		if (key == null)
			return null;
		if (bundles.size() == 0)
			load(defaultPropertiesFileName);
		for (int i = bundles.size() - 1; i >= 0; i--) {
			ResourceBundle b = bundles.get(i);
			if (b != null) {
				try {
					return b.getString(key);
				}
				catch (MissingResourceException e) {
				}
			}
		}
		return null;
	}
	
	/**
	 * Does the same thing as I18n.tr(key,arg...), except that if no value is found for the key,
	 * the return value is null.
	 * @see #tr(String, Object[])
	 */
	public static String trIfFound(String key, Object... arg) {
		String s = trIfFound(key);
		if (s == null)
			return null;
		return MessageFormat.format(s, arg);
	}
	
//	/**
//	 * Does the same thing as I18n.tr(key,arg0,arg1), except that if no value is found for the key,
//	 * the return value is null.
//	 * @see #tr(String, Object, Object)
//	 */
//	public static String trIfFound(String key, Object arg0, Object arg1) {
//		String s = trIfFound(key);
//		if (s == null)
//			return null;
//		return MessageFormat.format(s, new Object[] { arg0, arg1 });
//	}
//	
//	/**
//	 * Does the same thing as I18n.tr(key,arg0,arg1,arg2), except that if no value is found for the key,
//	 * the return value is null.
//	 * @see #tr(String, Object, Object, Object)
//	 */
//	public static String trIfFound(String key, Object arg0, Object arg1, Object arg2) {
//		String s = trIfFound(key);
//		if (s == null)
//			return null;
//		return MessageFormat.format(s, new Object[] { arg0, arg1, arg2 });
//	}
	
}
