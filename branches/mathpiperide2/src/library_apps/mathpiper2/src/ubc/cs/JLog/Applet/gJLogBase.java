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
//	JLogBase
//#########################################################################

package ubc.cs.JLog.Applet;

import java.awt.*;
import java.net.*;
import java.io.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Parser.*;

/**
* This is the base class for applets or applications using the 
* Prolog in Java environment.  It includes <code>jPrologServices</code>, 
* but does not specify the user interface.  It is designed to be a delegate
* class for the <code>gJLogAppletBase</code> or 
* <code>gJLogApplicationBase</code> classes which need
* the <code>jPrologServices</code>.  Derivative authors should ensure 
* that the credits from <code>getRequiredCreditInfo</code> function
* are preserved and visible in their own product.
*
* @author       Glendon Holst
* @version      %I%, %G%
*/
public class gJLogBase implements iJLogApplBaseServices
{ 
 protected jPrologServices			prolog;
 protected iJLogApplBaseServices 	parent;
 
 /**
 * Constructor.  Requires a parent delegate of type <code>iJLogApplBaseServices</code>
 * The delegate is invoked first, and can override the behaviours of the 
 * member functions.
 *
 * @param p 	The parent delegate instance for this object.
 */ 
 public gJLogBase(iJLogApplBaseServices p)
 {
  prolog = new jPrologServices(new jKnowledgeBase(),
                               new pPredicateRegistry(),
							   new pOperatorRegistry());
  prolog.setFileServices(p);

  parent = p;
 };

 public void init() 
 {
  prolog.start();  
 };

 public void 	start()
 {
  prolog.resume();
 };
 
 public void 	stop()
 { 
  prolog.suspend();
 };
 
 public void 	destroy()
 { 
  prolog.stop();
  prolog = null;
 };

 /**
 * This function returns the credit assignment and copyright informaiton string.
 * It must be preserved by authors of derivative works. All applets should be 
 * derived from the <code>gJLogAppletBase</code> class, and if they provide their 
 * own <code>getAppletInfo()</code> function it should invoke this function first.
 * For example: <code>return super.getAppletInfo() + "derivative information";</code>
 *
 * @return 	The credit and information string.
 */ 
 public String 			getRequiredCreditInfo()
 {
  return jPrologServices.getRequiredCreditInfo();
 };
 
 public String[][] 	getParameterInfo()
 {String[][] 	info = {{"source","text string","prolog source code"},
                        {"file","text string","prolog source code URL"},
                        {"query","text string","prolog query"},
                        {"action","text string","'consult' =  consult, 'query' = consult + query"},
                        {"pane","text string","'consult', 'query', 'debug', 'animation', 'help'"}};

  return info;
 }; 

 public String 		getSource() throws IOException
 {String 	source_text = parent.getParameter("source");
  String 	source_file = parent.getParameter("url");
  InputStream 	file;
   
  if (source_file == null)
   return parent.convertStringLinebreaks(source_text);
  
  try
  {
   file = parent.getInputStreamFromFilename(source_file); 
  }
  catch (MalformedURLException e)
  {
   return null;
  }
  catch (IOException e)
  {
   return null;
  }
  
  return parent.getTextFromInputStream(file);
 }

 public URL 	getURLFromFilename(String name) throws MalformedURLException, IOException
 {
  return null; // do nothing
 };

 public InputStream 	getInputStreamFromFilename(String name) throws MalformedURLException, IOException
 {
  return null; // do nothing
 };

 public URL 	getResourceURLFromFilename(String name) throws MalformedURLException, IOException
 {
  return null; // do nothing
 };

 public InputStream 	getResourceInputStreamFromFilename(String name) throws MalformedURLException, IOException
 {
  return null; // do nothing
 };

 public String 		getTextFromInputStream(InputStream in_strm) throws IOException
 {
  return jPrologFileServices.getTextFromInputStream_S(in_strm);
 };

 public String 		getParameter(String name)
 {
  return null;
 }; 
 
 public String 		convertStringLinebreaks(String param)
 {
  return jPrologFileServices.convertStringLinebreaks_S(param);
 }; 

 public Image 		getImage(String name)
 {
  return null; // do nothing
 };
 
 public jPrologServices 	getPrologServices()
 {
  return prolog;
 };
};
