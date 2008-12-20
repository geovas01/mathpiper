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
 */

//}}}
// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:

package org.mathpiper.test;


import org.mathpiper.Interfaces;
import org.mathpiper.interpreters.EvaluationResponse;
import org.mathpiper.interpreters.Interpreter;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class MathPiperTest
{
	private Interpreter mathPiper;
	private java.io.File testDirectory;
	private EvaluationResponse evaluationResponse;
	private java.io.FileWriter logFile;

	public MathPiperTest()
	{
		super();

	}//end constructor.


	public void test()
	{
		try{

			logFile = new java.io.FileWriter("mathpiper_tests.log");

			

			BufferedReader scriptNames = new  BufferedReader(new InputStreamReader( java.lang.ClassLoader.getSystemResource("tests/scripts/test_index.txt").openStream()));
			if (scriptNames != null) //File is on the classpath.
			{
				while(scriptNames .ready())
				{
					mathPiper = Interfaces.newSynchronousInterpreter();

					String scriptName = scriptNames.readLine();
					
					String output;

					output = "\n===========================\n" +scriptName + ": ";
					System.out.print(output);
					logFile.write(output);

					evaluationResponse = mathPiper.evaluate("Load(\"tests\\scripts\\" +scriptName + "\");");
					output = "Result: " + evaluationResponse.getResult() + "\nSide Effects:\n" + evaluationResponse.getSideEffects() + "\n";
					System.out.println(output);
					logFile.write(output);

				}//end while.

			}
			else
			{
				System.out.println("\nProblem finding test scripts.\n");
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

		MathPiperTest pt = new MathPiperTest();
		pt.test();

	}//end main

}//end class.


