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

package org.mathrider.piper;
//class CVersion { static String VERSION = "1.2.2"; }


class CVersion
{
	static String version = "";

	static
	{

		java.util.zip.ZipFile piperZip = null;
		String propertyFileName = "";
		try
		{
			java.net.URL propertiesURL = java.lang.ClassLoader.getSystemResource("piper.properties");
			if (propertiesURL != null)
			{
				String propertiesPath = propertiesURL.getPath();
				propertyFileName = propertiesPath.substring(0, propertiesPath.lastIndexOf('!'));
				piperZip = new java.util.zip.ZipFile(new java.io.File(new java.net.URI(propertyFileName)));
			}
			else
			{
				System.out.println("piper.properties not found!!!!");
			}

			java.util.Properties properties = new java.util.Properties();

			java.util.zip.ZipEntry e = piperZip.getEntry("piper.properties");

			java.io.InputStream s = piperZip.getInputStream(e);

			properties.load(s);

			version = properties.getProperty("piper.version");

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}//end try/catch.


	}//end constructor.


}//end class.
