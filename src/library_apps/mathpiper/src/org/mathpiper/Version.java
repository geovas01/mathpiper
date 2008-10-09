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

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
package org.mathpiper;
//class Version { static String VERSION = "1.2.2"; }

public class Version
{

   public  static String version = "";
    

    static
    {

        java.util.zip.ZipFile piperZip = null;
        String propertyFileName = "";
        try
        {
            java.net.URL propertiesURL = java.lang.ClassLoader.getSystemResource("mathpiper.properties");
            
             java.io.InputStream s;

            if (propertiesURL != null)
            {
                String propertiesPath = propertiesURL.getPath();
                if(propertiesPath.indexOf('!') != -1)
                {
                    propertyFileName = propertiesPath.substring(0, propertiesPath.lastIndexOf('!'));
                    piperZip = new java.util.zip.ZipFile(new java.io.File(new java.net.URI(propertyFileName)));

                     java.util.zip.ZipEntry e = piperZip.getEntry("mathpiper.properties");

                    s = piperZip.getInputStream(e);
                }
                else
                {
                    s = propertiesURL.openStream();
                }


                java.util.Properties properties = new java.util.Properties();



                properties.load(s);

                version = properties.getProperty("piper.version");
            } else
            {
                System.out.println("piper.properties not found!!!!");
            }



        } catch (Exception e)
        {
            e.printStackTrace();
        }//end try/catch.


    }//end constructor.
}//end class.
