//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathrider.geogebraplugin;

import java.applet.*;
import java.net.*;
import java.awt.*;
import javax.swing.JPanel;
import org.gjt.sp.jedit.jEdit;

// This class provides an applet stub for applets running
// as standalone applications. You can set the document base
// by setting the "docbase" system property. Likewise, you can set
// the code base through the "codebase" property.
// You can provide applet parameters by setting system properties
// with the applet's name followed by the parameter. For example:
// <PARAM name="stooge" value="moe">
// for an applet named MyApplet, could be set in this stub
// with by setting the system property "MyAppletstooge" to "moe".
// You can also just set the "stooge" property, but it will try
// using the appletname in front first. This allows you to run
// multiple applets at once that have the same parameter names.


public class MathriderAppletStub extends Object implements AppletStub
{
     JPanel appletJPanel;
     Applet applet;
     String appletName;
     String startDir;

     public MathriderAppletStub()
     {
     }

// startDir is the local directory where this applet is started, or
// another directory if you prefer. If you don't specify a code base
// or a document base, the startDir is used for those. The directory
// separators must be '/' and not '\' or the URL class gets confused.

     public MathriderAppletStub(JPanel appletJPanel, Applet applet, String name,
          String startDir)
     {
          this.appletJPanel = appletJPanel;
          this.applet = applet;
          this.appletName = name;
          this.startDir = startDir;

          RunAppletContext.instance().addApplet(applet, name);
     }

     public void setParams(JPanel appletJPanel, Applet applet, String name,
          String startDir)
     {
          this.appletJPanel = appletJPanel;
          this.applet = applet;
          this.appletName = name;
          this.startDir = startDir;

          RunAppletContext.instance().addApplet(applet, name);
     }

     public boolean isActive() { return true; }

// Return the document base URL. Try getting the docbase system parameter.
// If that isn't available, use the startDir directory.

     public URL getDocumentBase()
     {
          String docbase = System.getProperty("docbase");

          try {
               if (docbase == null) {
                    return new URL("file://"+startDir);
               } else {
                    return new URL(docbase);
               }
          } catch (MalformedURLException e) {
               return null;
          }
     }

// Return the code base URL. Try getting the codebase system parameter.
// If that isn't available, use the startDir directory.

     public URL getCodeBase()
     {
          String codebase = System.getProperty("codebase");

          try {
               if (codebase == null) {
                    return new URL("file://"+startDir);
               } else {
                    return new URL(codebase);
               }
          } catch (MalformedURLException e) {
               return null;
          }
     }

// fetch a parameter for the applet from the system properties. First
// try the applet name followed by the param name. If that's null,
// try just the param name.

     public String getParameter(String paramName)
     {
          

	   String prop = jEdit.getProperty(GeogebraPlugin.NAME + ".applet."+paramName);
	  //String prop = System.getProperty(appletName+paramName);
	  	System.out.println(prop);
          if (prop != null) return prop;
          return System.getProperty(paramName);
     }

     public AppletContext getAppletContext()
     {
          return RunAppletContext.instance();
     }

// appletResize is the only reason we need a reference to the applet's
// frame. If the applet wants to resize, we resize the frame, then
// the applet.

     public void appletResize(int width, int height)
     {
          //appletFrame.resize(width+10, height+20);
          //applet.resize(width, height);
     }
}//end class.

/* {{{ License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */ //}}}
