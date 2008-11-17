//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathpiper.test;

import org.mathpiper.Interpreter;
import org.mathpiper.io.StandardFileOutputStream;

public class MathPiperTest
{
	private Interpreter mathPiper;
	private java.io.File testDirectory;
	private String result;
	private java.io.FileWriter logFile;
	
	public MathPiperTest()
	{
		super();
		
	}//end constructor.
	
	
	public void test(String directory)
	{
		try{
			
			logFile = new java.io.FileWriter("mathpiper_tests.log");
			
			mathPiper = new Interpreter( new StandardFileOutputStream(System.out));
			
			testDirectory = new java.io.File(directory);
			if(testDirectory.exists() )
			{
				java.io.File[] files = testDirectory.listFiles(new java.io.FilenameFilter () 
				{
					public boolean accept(java.io.File file, String name)
					{
						if(name.endsWith(".mpt"))
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
				//Execute each .mpt file in the specified directory.
				for(int x = 0; x < files.length; x++)
				{	
					output = "\n===========================\n" + files[x].getName() + ": "; 
					System.out.print(output);
					logFile.write(output);
					
					result = mathPiper.evaluate("Load(\"" + files[x].getName() + "\");");
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
		catch(org.mathpiper.exceptions.MathPiperException e)
		{
			e.printStackTrace();
		}//end try/catch.
		
	}//end method.
	
	public static void main(String[] args)
	{
		/*Note: This program currently only works if it is executed in the current directory
		  because MathPiper cannot handle paths properly yet.
		  
		  Execute with a command line similar to the following:
		  
		    java -cp .;../dist/piper.jar org.mathpiper.tests.MathPiperTest
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
                
             
		MathPiperTest pt = new MathPiperTest();
		pt.test(directory);
		
	}//end main
	
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
