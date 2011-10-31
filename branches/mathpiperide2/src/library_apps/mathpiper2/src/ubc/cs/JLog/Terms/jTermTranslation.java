/*
    This file is part of JLog.

    Created by Glendon Holst for Alan Mackworth and the 
    "Computational Intelligence: A Logical Approach" text.
    
    Copyright 1998, 2000, 2002, 2008 by University of British Columbia, 
    Alan Mackworth and Glendon Holst.
    
    This notice must remain in all files which belong to, or are derived 
    from JLog.
    
    Check <http://jlogic.sourceforge.net/> or 
    <http://sourceforge.net/projects/jlogic> for further information
    about JLog, or to contact the authors.

    JLog is free software, dual-licensed under both the GPL and MPL 
    as follows:

    You can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Or, you can redistribute it and/or modify
    it under the terms of the Mozilla Public License as published by
    the Mozilla Foundation; version 1.1 of the License.

    JLog is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License, or the Mozilla Public License for 
    more details.

    You should have received a copy of the GNU General Public License
    along with JLog, in the file GPL.txt; if not, write to the 
    Free Software Foundation, Inc., 
    59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
    URLs: <http://www.fsf.org> or <http://www.gnu.org>

    You should have received a copy of the Mozilla Public License
    along with JLog, in the file MPL.txt; if not, contact:
    http://http://www.mozilla.org/MPL/MPL-1.1.html
    URLs: <http://www.mozilla.org/MPL/>
*/
//#########################################################################
//	Term Translation
//#########################################################################

package ubc.cs.JLog.Terms;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.jPrologServices;
import ubc.cs.JLog.Parser.pParseStream;

/**
* This class aggregates iObjectToTerm and iTermToObject converters into a single
* conversion class which chooses the correct converter object for the 
* for desired conversion.  The fundamental idea behind the converter mappings is that typically
* each prolog term object has a single corresponding Java object, and each Java object has
* a single corresponding prolog term.  To peform an object->term conversion we look up the
* object class in the term converter look-up table and use the associated converter. To peform  
* a term->object conversion we look up the term class in the object converter table and use 
* the associated converter.  Because the source object class is just a key for a converter 
* lookup, the idea is extended to arbitrary keys for converters.  It is possible to specify
* which converter to use via these keys (see the two parameter versions of 
* <code>createTermFromObject</code> and <code>createObjectFromTerm</code>).  For each conversion
* direction (i.e., object->term and term->object) there is a default converter, used if
* the not matching converters are found in the look-up table. 
* 
* @author       Glendon Holst
* @version      %I%, %G%
*/
public class jTermTranslation implements iObjectToTerm, iTermToObject
{
 protected Hashtable		createTermKeys;
 protected Hashtable		createObjectKeys;
 protected iObjectToTerm	defaultCreateTerm;
 protected iTermToObject	defaultCreateObject;
 protected jPrologServices	prolog = null;
 
 public jTermTranslation()
 {
  createTermKeys = new Hashtable();
  createObjectKeys = new Hashtable();
  defaultCreateTerm = null;
  defaultCreateObject = null;
  prolog = null;
 };

 public jTermTranslation(jPrologServices ps)
 {
  createTermKeys = new Hashtable();
  createObjectKeys = new Hashtable();
  defaultCreateTerm = null;
  defaultCreateObject = null;
  prolog = ps;
 };
 
 /**
  * Sets default translators in the create*Keys hashtables.  Existing registered translators
  * remain, provided they were not registered for the default keys.  This method establishes
  * the default translation behaviour.
  */
 public void		setDefaults()
 {
  setObjectDefaults();
  setTermDefaults();
 };

 /**
  * Sets the default <code>iTermToObject</code> conversion objects for converting
  * Prolog jTerms into their corresponding Java objects.  Also sets the default 
  * <code>iTermToObject</code> conversion object.  The following conversion mappings
  * are registered (Keys : ObjectClass):<br>
  * ([Float,jReal] : Float)<br>
  * ([Integer,jInteger] : Integer)<br>
  * ([Boolean,jTrue,jFail] : Boolean)<br>
  * ([jObject] : jObject)<br>
  * ([jListPair,jNullList] : Vector)<br>
  * ([jVariable] : void)<br>
  * ([jTerm] : jTerm)<br>
  * ([String,jAtom] : String) default<br>
  */
 protected void		setObjectDefaults()
 {
  // this converter creates a Float, possibly upgrading an integer.
  {iTermToObject	c;
   
   c = new iTermToObject()
		{
		 public Object 	createObjectFromTerm(jTerm term)
                        {
						 if (term instanceof jInteger)
						  return new Float((float) ((jInteger) term).getIntegerValue());
						 else if (term instanceof jReal)
						  return new Float(((jReal) term).getRealValue());
						 
						 throw new TranslationFailureException("Expected numerical term."); 
                        }
		};
				
   RegisterTermToObjectConverter(Float.class,c);
   RegisterTermToObjectConverter(jReal.class,c);
  }
  // this converter creates an Integer, possibly downgrading a real.
  {iTermToObject	c;
   
   c = new iTermToObject()
		{
		 public Object 	createObjectFromTerm(jTerm term)
                        {
						 if (term instanceof jInteger)
						  return new Integer(((jInteger) term).getIntegerValue());
						 else if (term instanceof jReal)
						  return new Integer((int) ((jReal) term).getRealValue());
						 
						 throw new TranslationFailureException("Expected numerical term."); 
                        }
		};
				
   RegisterTermToObjectConverter(Integer.class,c);
   RegisterTermToObjectConverter(jInteger.class,c);
  }
  // this converter creates a Boolean, based on jTrue, jFail, or a jAtom name "true" or "fail".
  {iTermToObject	c;
   
   c = new iTermToObject()
		{
		 public Object 	createObjectFromTerm(jTerm term)
                        {
						 if (term instanceof jTrue || 
							 (term instanceof jAtom && term.getName().equals("true")))
						  return Boolean.TRUE;
						 else if (term instanceof jFail || 
								  (term instanceof jAtom && term.getName().equals("fail")))
						  return Boolean.FALSE;
						 
						 throw new TranslationFailureException("Expected boolean term."); 
                        }
		};
				
   RegisterTermToObjectConverter(Boolean.class,c);
   RegisterTermToObjectConverter(jTrue.class,c);
   RegisterTermToObjectConverter(jFail.class,c);
  }
  // this converter returns the object associated with a jObject.
  {iTermToObject	c;
   
   c = new iTermToObject()
		{
		 public Object 	createObjectFromTerm(jTerm term)
                        {
						 if (term instanceof jObject)
						  return ((jObject) term).getObjectReference();
						 
						 throw new TranslationFailureException("Expected jObject term."); 
                        }
		};
				
   RegisterTermToObjectConverter(jObject.class,c);
  }
  // this converter returns a vector for a list.
  {iTermToObject	c;
   
   c = new iTermToObject()
		{
		 public Object 	createObjectFromTerm(jTerm term)
                        {
						 if (term instanceof jList)
						 {Enumeration   e = ((jList) term).elements(jTermTranslation.this);
						  Vector		v = new Vector();
						  
						  while (e.hasMoreElements())
						   v.addElement(e.nextElement());
  
						  return v;		
						 }
						 
						 throw new TranslationFailureException("Expected jList term."); 
                        }
		};
				
   RegisterTermToObjectConverter(jListPair.class,c);
   RegisterTermToObjectConverter(jNullList.class,c);
  }
  // this converter returns a Void for a variable.
  {iTermToObject	c;
   
   c = new iTermToObject()
		{
		 public Object 	createObjectFromTerm(jTerm term)
                        {
						 if (term instanceof jVariable)
						 {  
						  return void.class;		
						 }
						 
						 throw new TranslationFailureException("Expected jVariable term."); 
                        }
		};
				
   RegisterTermToObjectConverter(jVariable.class,c);
  }
  // no translation, returns the original term
  {iTermToObject	c;
   
   c = new iTermToObject()
		{
		 public Object 	createObjectFromTerm(jTerm term)
                        {
						 return term;		
                        }
		};
				
   RegisterTermToObjectConverter(jTerm.class,c);
  }
  // default translation, create string representation of term
  {iTermToObject	c;
   
   c = new iTermToObject()
		{
		 public Object 	createObjectFromTerm(jTerm term)
                        {
						 if (term instanceof jAtom || 
							(term instanceof jPredicate && 
								((jPredicate) term).getArity() == 0))
						  return "'" + term.toString() + "'";
						 
						 return term.toString();		
                        }
		};
				
   RegisterTermToObjectConverter(jAtom.class,c);
   RegisterTermToObjectConverter(String.class,c);
   RegisterDefaultTermToObjectConverter(c);
  }
 };
 
 /**
  * Sets the default <code>iObjectToTerm</code> conversion objects for converting
  * Java objects into their corresponding Prolog jTerm objects. Also sets the default 
  * <code>iObjectToTerm</code> conversion object.  The following conversion mappings
  * are registered (Keys : ObjectClass):<br>
  * ([Float,Double,jReal] : jReal)<br>
  * ([Short,Integer,Long,jInteger] : jInteger)<br>
  * ([Boolean,jTrue,jFail] : jTrue, jFail)<br>
  * ([Object] : jObject)<br>
  * ([Vector,Enumeration] : jList)<br>
  * ([jTerm] : jTerm)<br>
  * (["String",jAtom,jTerm] : jAtom,jTerm) default if no PrologServices specified<br>
  * ([String,jAtom,jTerm] : jTerm) default if PrologServices specified<br>
  */
 protected void		setTermDefaults()
 {
  // this converter creates a real term, possibly upgrading integers.
  {iObjectToTerm	c;
   
   c = new iObjectToTerm()
		{
		 public jTerm 	createTermFromObject(Object obj)
                        {
						 if (obj instanceof Float)
						  return new jReal(((Float) obj).floatValue());
						 else if (obj instanceof Double)
						  return new jReal(((Double) obj).floatValue());
						 else if (obj instanceof Integer)
						  return new jReal(((Integer) obj).floatValue());
						 else if (obj instanceof Long)
						  return new jReal(((Long) obj).floatValue());
						 
						 throw new TranslationFailureException("Expected numerical object."); 
                        }
		};
				
   RegisterObjectToTermConverter(Float.class,c);
   RegisterObjectToTermConverter(Double.class,c);
   RegisterObjectToTermConverter(jReal.class,c);
  }
  // this converter creates an integer, possibly downgrading a float.
  {iObjectToTerm	c;
   
   c = new iObjectToTerm()
		{
		 public jTerm 	createTermFromObject(Object obj)
                        {
						 if (obj instanceof Short)
						  return new jInteger(((Short) obj).intValue());
						 else if (obj instanceof Integer)
						  return new jInteger(((Integer) obj).intValue());
						 else if (obj instanceof Long)
						  return new jInteger(((Long) obj).intValue());
						 else if (obj instanceof Float)
						  return new jInteger(((Float) obj).intValue());
						 else if (obj instanceof Double)
						  return new jInteger(((Double) obj).intValue());
						 
						 throw new TranslationFailureException("Expected numerical term."); 
                        }
		};
				
   RegisterObjectToTermConverter(Short.class,c);
   RegisterObjectToTermConverter(Integer.class,c);
   RegisterObjectToTermConverter(Long.class,c);
   RegisterObjectToTermConverter(jInteger.class,c);
  }
  // this converter creates a true or fail term, from Boolean or String with "true" or "fail".
  {iObjectToTerm	c;
   
   c = new iObjectToTerm()
		{
		 public jTerm 	createTermFromObject(Object obj)
                        {
						 if (obj instanceof Boolean)
						  return (((Boolean) obj).booleanValue() ? 
												(jTerm) jTrue.TRUE : 
												(jTerm) jFail.FAIL);
						 else if (obj instanceof String)
						 {String	s = (String) obj;
						 
						  if (s.equalsIgnoreCase("true"))
						   return jTrue.TRUE;
						  else if (s.equalsIgnoreCase("fail"))
						   return jFail.FAIL;
						 }
						 
						 throw new TranslationFailureException("Expected boolean term."); 
                        }
		};
				
   RegisterObjectToTermConverter(Boolean.class,c);
   RegisterObjectToTermConverter(jTrue.class,c);
   RegisterObjectToTermConverter(jFail.class,c);
  }
  // this converter returns a jObject, encapsulating the given object.
  {iObjectToTerm	c;
   
   c = new iObjectToTerm()
		{
		 public jTerm 	createTermFromObject(Object obj)
                        {
						 return new jObject(obj);
                        }
		};
				
   RegisterObjectToTermConverter(jObject.class,c);
  }
  // this converter returns a list given a vector, or an enumeration.
  {iObjectToTerm	c;
   
   c = new iObjectToTerm()
		{
		 public jTerm 	createTermFromObject(Object obj)
                        {
						 if (obj instanceof Vector)
						  return jListPair.createListFromEnumeration(
										((Vector) obj).elements(),jTermTranslation.this);
						 else if (obj instanceof Enumeration)
						  return jListPair.createListFromEnumeration((Enumeration) obj,
										jTermTranslation.this);

						 throw new TranslationFailureException("Expected enumerable object."); 
                        }
		};
				
   RegisterObjectToTermConverter(Vector.class,c);
   RegisterObjectToTermConverter(Enumeration.class,c);
  }
  // this converter returns the original object if it is a jTerm.
  {iObjectToTerm	c;
   
   c = new iObjectToTerm()
		{
		 public jTerm 	createTermFromObject(Object obj)
                        {
						 if (obj instanceof jTerm)
						 {
						  return (jTerm) obj;
						 }

						 throw new TranslationFailureException("Expected jTerm object."); 
                        }
		};
				
   RegisterObjectToTermConverter(jTerm.class,c);
  }
  // this converter returns an atom (from String representation of object) or original jTerm.
  {iObjectToTerm	c;
   
   c = new iObjectToTerm()
		{
		 public jTerm 	createTermFromObject(Object obj)
                        {
						 if (obj instanceof jTerm)
						  return (jTerm) obj;
						  
						 return new jAtom(obj.toString()); 
                        }
		};
				
   RegisterObjectToTermConverter(jAtom.class,c);
   if (prolog == null)
   {
    RegisterObjectToTermConverter(String.class,c);
    RegisterObjectToTermConverter(StringBuffer.class,c);
    RegisterDefaultObjectToTermConverter(c);
   }
  }
  // this default converter returns parses Strings to their jTerm representation.
  // prolog services must exist for this converter to exist and be the default.
  if (prolog != null)
  {iObjectToTerm	c;
   
   c = new iObjectToTerm()
		{
		 public jTerm 	createTermFromObject(Object obj)
                        {
						 if (obj instanceof jTerm)
						  return (jTerm) obj;
						 
						  {pParseStream 	parser;
						   jTerm			query;
   
						   parser = new pParseStream(obj.toString()+".",
											prolog.getKnowledgeBase(),
											prolog.getPredicateRegistry(),
											prolog.getOperatorRegistry());
   
						   query = parser.parseTerm(); 
    
                           if (query != null)
							return query;
							
						   throw new TranslationFailureException("Invalid representation."); 
                          } 
                        }
		};
				
   RegisterObjectToTermConverter(String.class,c);
   RegisterObjectToTermConverter(StringBuffer.class,c);
   RegisterDefaultObjectToTermConverter(c);
  }
 };
 
 public void RegisterTermToObjectConverter(Object key,iTermToObject conv)
 {
  createObjectKeys.put(key,conv);
 };

 public void RegisterDefaultTermToObjectConverter(iTermToObject conv)
 {
  defaultCreateObject = conv;
 };

 public void RegisterObjectToTermConverter(Object key,iObjectToTerm conv)
 {
  createTermKeys.put(key,conv);
 };
  
 public void RegisterDefaultObjectToTermConverter(iObjectToTerm conv)
 {
  defaultCreateTerm = conv;
 };
 
 public iObjectToTerm	getObjectToTermConverter(Object key)
 {
  return (iObjectToTerm) createTermKeys.get(key);
 };

 public iObjectToTerm	getDefaultObjectToTermConverter()
 {
  return defaultCreateTerm;
 };

 public iTermToObject	getTermToObjectConverter(Object key)
 {
  return (iTermToObject) createObjectKeys.get(key);
 };

 public iTermToObject	getDefaultTermToObjectConverter()
 {
  return defaultCreateObject;
 };
    
 public jTerm 		createTermFromObject(Object obj)
 {
  return createTermFromObject(obj,obj.getClass());
 };
 
 public Object		createObjectFromTerm(jTerm term)
 {
  return createObjectFromTerm(term,term.getClass());
 };

 public jTerm		createTermFromObject(Object obj,Object key)
 {iObjectToTerm		conv = (iObjectToTerm) createTermKeys.get(key);
 
  if (conv == null)
   conv = defaultCreateTerm;
   
  if (conv != null)
   return conv.createTermFromObject(obj);

  throw new TranslationFailureException("No object to term conversion unit found."); 
 };

 public Object		createObjectFromTerm(jTerm term,Object key)
 {iTermToObject		conv = (iTermToObject) createObjectKeys.get(key);
 
  if (conv == null)
   conv = defaultCreateObject;
   
  if (conv != null)
   return conv.createObjectFromTerm(term);

  throw new TranslationFailureException("No term to object conversion unit found."); 
 };
};
