package org.mathpiper.ide.piper_me.tests;

import org.mathpiper.ide.piper_me.DefaultFileLocator;
import org.mathpiper.ide.piper_me.PiperInterpreter;

public class PiperTest  
{
	private java.io.File testDirectory;
	private String result;
	private java.io.FileWriter logFile;
	
	
	
	public void test(String directory)
	{
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
          PiperInterpreter piper = new PiperInterpreter(new DefaultFileLocator());
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
		catch(org.mathpiper.ide.piper_me.Piperexception e)
		{
			e.printStackTrace();
		}//end try/catch.
		
	}//end method.
	
	public static void main(String[] args)
	{
		/*Note: This program currently only works if it is executed in the current directory
		  because Piper cannot handle paths properly yet.
		  
		  Execute with a command line similar to the following:
		  
		    java -cp .;../dist/piper.jar org.mathpiper.ide.piper.tests.PiperTest
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
}//end class.
