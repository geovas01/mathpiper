package org.mathrider.piper_me;
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
