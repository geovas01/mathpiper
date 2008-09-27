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

import org.mathrider.piper.io.InputStatus;
import org.mathrider.piper.printers.InfixPrinter;
import org.mathrider.piper.lisp.parsers.InfixParser;
import org.mathrider.piper.io.StringOutput;
import org.mathrider.piper.io.StringInput;
import org.mathrider.piper.lisp.Output;
import org.mathrider.piper.lisp.Standard;
import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.parsers.Tokenizer;
import org.mathrider.piper.lisp.parsers.Parser;
import org.mathrider.piper.lisp.Input;
import org.mathrider.piper.lisp.Printer;


/**
 * 
 * @author
 */
public class Interpreter
{
	
	public Environment env = null;
	Tokenizer tokenizer = null;
	Printer printer = null;
	public String iError = null;
	
	
        String defaultDirectory = null;
        String archive = "";
        String detect = "";
        String pathParent = "";
        boolean inZipFile = false;
        
	public Interpreter(Output stdoutput)
	{
		env = new Environment(stdoutput);
		tokenizer = new Tokenizer();
		printer = new InfixPrinter(env.iPrefixOperators, env.iInfixOperators, env.iPostfixOperators, env.iBodiedOperators);
        

		env.iCurrentInput = new CachedStdFileInput(piper.env.iInputStatus);
        

            java.net.URL detectURL = java.lang.ClassLoader.getSystemResource("piperinit.pi");
            pathParent = new File(detectURL.getPath()).getParent();
            addDirectory(piper, pathParent);
            //StdFileInput.setPath(pathParent + File.separator);


            if (detectURL != null)
            {
                detect = detectURL.getPath(); // file:/home/av/src/lib/piper.jar!/piperinit.pi

                if (detect.indexOf('!') != -1)
                {
                    archive = detect.substring(0, detect.lastIndexOf('!')); // file:/home/av/src/lib/piper.jar

                    try
                    {
                        String zipFileName = archive;//"file:/Users/ayalpinkus/projects/JavaPiper/piper.jar";

                        java.util.zip.ZipFile z = new java.util.zip.ZipFile(new File(new java.net.URI(zipFileName)));
                        Standard.zipFile = z;
                        inZipFile = true;
                    } catch (Exception e)
                    {
                        System.out.println("Failed to find piper.jar" + e.toString());
                    }
                }



            //System.out.println("Found archive ["+archive+"]");
            } else
            {
                
                System.out.println("Code is not in an archive.");
            }
	    
	    
	

		try
		{
			




            String result = "";
            try
            {
                result = piper.evaluate("Load(\"piperinit.pi\");");

            } catch (PiperException pe)
            {
                pe.printStackTrace();
            }


		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
	
	
    public void addDirectory( String directory)
    {
        String toEvaluate = "DefaultDirectory(\"" + directory + File.separator + "\");";

        String result = "";
        try
        {
            result = piper.evaluate(toEvaluate);
        } catch (PiperException pe)
        {
            pe.printStackTrace();
        }

    }
        
        
        
	public String evaluate(String input) throws PiperException
	{
		if (input.length() == 0)
			return "";
		String rs = "";
		try
		{
			env.iEvalDepth=0;
			env.iEvaluator.resetStack();


			iError = null;

			Pointer in_expr = new Pointer();
			if (env.iPrettyReader != null)
			{
				InputStatus someStatus = new InputStatus();
				StringBuffer inp = new StringBuffer();
				inp.append(input);
				InputStatus oldstatus = env.iInputStatus;
				env.iInputStatus.setTo("String");
				StringInput newInput = new StringInput(new StringBuffer(input),env.iInputStatus);

				Input previous = env.iCurrentInput;
				env.iCurrentInput = newInput;
				try
				{
					Pointer args = new Pointer();
					Standard.internalApplyString(env, in_expr,
					                                 env.iPrettyReader,
					                                 args);
				}
				catch (Exception e)
				{
					throw new PiperException(e.getMessage());//Note:tk. Throw PiperException instead of just exception.
				}
				finally
				{
					env.iCurrentInput = previous;
					env.iInputStatus.restoreFrom(oldstatus);
				}
			}
			else
			{
				InputStatus someStatus = new InputStatus();
				StringBuffer inp = new StringBuffer();
				inp.append(input);
				inp.append(";");
				StringInput input_str = new StringInput(inp,someStatus);
				Parser parser = new InfixParser(tokenizer, input_str, env, env.iPrefixOperators, env.iInfixOperators, env.iPostfixOperators, env.iBodiedOperators);
				parser.parse( in_expr );
			}

			Pointer result = new Pointer();
			env.iEvaluator.eval(env, result, in_expr);

			String percent = env.hashTable().lookUp("%");
			env.setVariable(percent,result,true);

			StringBuffer string_out = new StringBuffer();
			Output output = new StringOutput(string_out);

			if (env.iPrettyPrinter != null)
			{
				Pointer nonresult = new Pointer();
				Standard.internalApplyString(env, nonresult,
				                                 env.iPrettyPrinter,
				                                 result);
				rs = string_out.toString();
			}
			else
			{
				printer.RememberLastChar(' ');
				printer.Print(result, output, env);
				rs = string_out.toString();
			}
		}
		catch (Exception e)
		{
			//      e.printStackTrace();
			//System.out.println(e.toString());

			//Note:tk throw PiperException instead of simply printing the exception message.
			iError = e.getMessage();
			throw new PiperException(iError);
		}
		return rs;
	}

}
