//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathrider.piper_me.tests;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.mathrider.piper_me.PiperInterpreter;

public class PiperTest implements org.mathrider.piper_me.FileLocator 
{
	private java.io.File testDirectory;
	private String result;
	private java.io.FileWriter logFile;
	
	public PiperTest()
	{
		super();
		
	}//end constructor.
	
	
	public void test(String directory)
	{
    org.mathrider.piper_me.StdFileInput.locator = this;
		try{
			
			logFile = new java.io.FileWriter("piper_tests.log");
			
			testDirectory = new java.io.File(directory);
			if(testDirectory.exists() )
			{
				java.io.File[] files = testDirectory.listFiles(new java.io.FilenameFilter () 
				{
					public boolean accept(java.io.File file, String name)
					{
						if(name.endsWith(".yts") || name.endsWith(".mpt"))
						{
							return(true);
						}
						else
						{
							return(false);
						}
					}
				});
				
				
				String output;
				//Execute each .pit file in the specified directory.
				for(int x = 0; x < files.length; x++)
				{	
          PiperInterpreter piper = new PiperInterpreter();
					output = "\n===========================\n" + files[x].getName() + ": "; 
					System.out.print(output);
					logFile.write(output);
					
					result = piper.evaluate("Load(\"" + files[x].getName() + "\");");
					output = result + "\n";
					System.out.println(output);
					logFile.write(output);
				}

			}
			else
			{
				System.out.println("\nDirectory " + directory + "does not exist.\n");
			}
			
			logFile.close();
			
		}
		catch(java.io.IOException e)
		{
			e.printStackTrace();
		}
		catch(org.mathrider.piper_me.Piperexception e)
		{
			e.printStackTrace();
		}//end try/catch.
		
	}//end method.
	
	public static void main(String[] args)
	{
		/*Note: This program currently only works if it is executed in the current directory
		  because Piper cannot handle paths properly yet.
		  
		  Execute with a command line similar to the following:
		  
		    java -cp .;../dist/piper.jar org.mathrider.piper.tests.PiperTest
		*/
		
		String directory;
		
		if(args.length > 0)
		{	
			directory = args[0];
		}
		else
		{
			directory = ".";
		}
		PiperTest pt = new PiperTest();
		pt.test(directory);
		
	}//end main
  
   public InputStream getStream(String name) {
      InputStream is = getClass().getResourceAsStream(name);
      if (is == null)
        is = getClass().getResourceAsStream("/"+name);
      if (is == null) {
        try {
          is = new BufferedInputStream(new FileInputStream(name));
        } catch (FileNotFoundException e) {
          is = null;
        }
      }
      return is;
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
 
 // :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
