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
//##################################################################################
//	jPrologFileServices
//##################################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class jPrologFileServices implements iPrologFileServices
{
 public static String   getTextFromInputStream_S(InputStream in_strm) throws IOException
 {BufferedReader 	b_read = new BufferedReader(new InputStreamReader(in_strm));
  StringBuffer		result = new StringBuffer();
  String 		read_in;
     
  while ((read_in = b_read.readLine()) != null)
  {
   result.append(read_in + "\n");
  }
  
  b_read.close();
   
  return convertStringLinebreaks_S(result.toString());
 };

 public static String 		convertStringLinebreaks_S(String param)
 {StringBuffer	result = new StringBuffer();
  String 	lfcr = new String("\n\r");
  String 	crlf = new String("\r\n");
  String 	lf = new String("\n");
  String 	cr = new String("\r");
  String 	line_separator = System.getProperty("line.separator","\n");
  int 		start = 0,curr, next;
  boolean 	eos = false, eol;
  
  if (param == null)
   return param;
  
  while (!eos)
  {
   if ((curr = param.indexOf(lfcr,start)) >= 0)
   {
    next = curr + 2;
    eol = true;
   }
   else if ((curr = param.indexOf(crlf,start)) >= 0)
   {
    next = curr + 2;
    eol = true;
   }
   else if ((curr = param.indexOf(cr,start)) >= 0)
   {
    next = curr + 1;
    eol = true;
   }
   else if ((curr = param.indexOf(lf,start)) >= 0)
   {
    next = curr + 1;
    eol = true;
   }
   else 
   {
    next = curr = param.length();
    eos = true;
    eol = false;
   } 

   if (start <= curr)
   {
    result.append(param.substring(start,curr));
    if (eol)
     result.append(line_separator);
     
    start = next;
   }
   else
    eos = true;
  }
  
  return result.toString();  
 }; 

 public static URL				getURLFromFilename_S(String name) throws MalformedURLException, IOException
 {File		file = new File(name);
  URL 		ufile = new URL("file:///" + file.getCanonicalPath());

  return ufile;
 };
 
 public static InputStream 	getInputStreamFromFilename_S(String name) throws MalformedURLException, IOException
 {File 		file = new File(name);

  return new FileInputStream(file);
 };
 
 public static URL				getResourceURLFromFilename_S(String name) throws MalformedURLException, IOException
 {//URL   rsrc_url = ClassLoader.getSystemResource(name); // works as App, not Applet
  //URL   rsrc_url = Thread.currentThread().getContextClassLoader().getResource(name); // works for both, but uses v1.2 feature
  URL   rsrc_url = jPrologServices.class.getClassLoader().getResource(name); // works for both -- in J#, getClassLoader() returns null...
 
  if (rsrc_url == null)
   throw new IOException();
   
  return rsrc_url;
 };
 
 public static InputStream 	getResourceInputStreamFromFilename_S(String name) throws MalformedURLException, IOException
 {//InputStream   in = ClassLoader.getSystemResourceAsStream(name);
  //InputStream   in = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
  InputStream   in = jPrologServices.class.getClassLoader().getResourceAsStream(name);
 
  if (in == null)
   throw new IOException();

  return in;
 };
 
 public					jPrologFileServices()
 {
 };
 
 public URL				getURLFromFilename(String name) throws MalformedURLException, IOException
 {
  return jPrologFileServices.getURLFromFilename_S(name);
 };
 
 public InputStream 	getInputStreamFromFilename(String name) throws MalformedURLException, IOException
 {
  return jPrologFileServices.getInputStreamFromFilename_S(name);
 };
 
 public URL				getResourceURLFromFilename(String name) throws MalformedURLException, IOException
 {
  return jPrologFileServices.getResourceURLFromFilename_S(name);
 };
 
 public InputStream 	getResourceInputStreamFromFilename(String name) throws MalformedURLException, IOException
 {
  return jPrologFileServices.getResourceInputStreamFromFilename_S(name);
 };
 
 public String			getTextFromInputStream(InputStream in_strm) throws IOException
 {
  return jPrologFileServices.getTextFromInputStream_S(in_strm);
 };
};

