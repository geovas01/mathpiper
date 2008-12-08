/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mathpiper.scripts;

/**
 *
 * @author tkosan
 */
public class ScriptsToClasses {


	private java.io.File testDirectory;
	private java.io.FileWriter logFile;


	public void classify(String directory)
	{
		try{

			logFile = new java.io.FileWriter("mathpiper_scripts.log");


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

					//evaluationResponse = mathPiper.evaluate("Load(\"" + files[x].getName() + "\");");
					//output = "Result: " + evaluationResponse.getResult() + "\nSide Effects:\n" + evaluationResponse.getSideEffects() + "\n";
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


		ScriptsToClasses scripts = new ScriptsToClasses();
		scripts.classify(directory);

	}//end main

}
