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
package org.mathpiper.interpreters;

import org.mathpiper.exceptions.MathPiperException;
import org.mathpiper.io.InputStatus;
import org.mathpiper.printers.InfixPrinter;
import org.mathpiper.lisp.parsers.MathPiperParser;
import org.mathpiper.io.StringOutputStream;
import org.mathpiper.io.StringInputStream;
import org.mathpiper.io.OutputStream;
import org.mathpiper.lisp.UtilityFunctions;
import org.mathpiper.lisp.ConsPointer;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;
import org.mathpiper.lisp.parsers.Parser;
import org.mathpiper.io.InputStream;
import org.mathpiper.lisp.printers.Printer;

import org.mathpiper.io.CachedStandardFileInputStream;
import java.io.*;
import org.mathpiper.io.StringOutput;
import org.mathpiper.lisp.GlobalVariable;

/**
 * 
 * 
 */
public class SynchronousInterpreter
{

    private Environment environment = null;
    MathPiperTokenizer tokenizer = null;
    Printer printer = null;
    private String iError = null;
    String defaultDirectory = null;
    String archive = "";
    String detect = "";
    String pathParent = "";
    boolean inZipFile = false;
    OutputStream sideEffectsStream;
    private static SynchronousInterpreter singletonInstance;

    private SynchronousInterpreter()
    {
        sideEffectsStream = new StringOutput();

        try
        {
            environment = new Environment(sideEffectsStream);
            tokenizer = new MathPiperTokenizer();
            printer = new InfixPrinter(environment.iPrefixOperators, environment.iInfixOperators, environment.iPostfixOperators, environment.iBodiedOperators);


            environment.iCurrentInput = new CachedStandardFileInputStream(environment.iInputStatus);


            java.net.URL detectURL = java.lang.ClassLoader.getSystemResource("mathpiperinit.mpi");

            //StdFileInput.setPath(pathParent + File.separator);


            if (detectURL != null)
            {
                detect = detectURL.getPath(); // file:/home/av/src/lib/piper.jar!/piperinit.mpi

                if (detect.indexOf('!') != -1)
                {
                    archive = detect.substring(0, detect.lastIndexOf('!')); // file:/home/av/src/lib/piper.jar

                    try
                    {
                        String zipFileName = archive;//"file:/Users/ayalpinkus/projects/JavaMathPiper/piper.jar";

                        java.util.zip.ZipFile z = new java.util.zip.ZipFile(new File(new java.net.URI(zipFileName)));
                        UtilityFunctions.zipFile = z;
                        inZipFile = true;
                    } catch (Exception e)
                    {
                        System.out.println("Failed to find mathpiper.jar" + e.toString());
                    }
                } else
                {
                    pathParent = new File(detectURL.getPath()).getParent();
                    addDirectory(pathParent);
                }
            } else
            {
                System.out.println("Cannot find mathpiperinit.mpi.");
            }


            //String result = "";
            try
            {
                evaluate("Load(\"mathpiperinit.mpi\");");

            } catch (MathPiperException pe)
            {
                pe.printStackTrace();
            }


        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }

    public static SynchronousInterpreter newInstance()
    {
        return new SynchronousInterpreter();
    }

    public static SynchronousInterpreter getInstance()
    {
        if (singletonInstance == null)
        {
            singletonInstance = new SynchronousInterpreter();
        }
        return singletonInstance;
    }

    public java.util.zip.ZipFile getScriptsZip()
    {
        return UtilityFunctions.zipFile;
    }//end method.

    public void addDirectory(String directory)
    {
        String toEvaluate = "DefaultDirectory(\"" + directory + File.separator + "\");";

        //String result = "";
        try
        {
            evaluate(toEvaluate);
        } catch (MathPiperException pe)
        {
            pe.printStackTrace();
        }

    }

    public EvaluationResponse evaluate(String inputExpression) throws MathPiperException
    {
        EvaluationResponse evaluationResponse = EvaluationResponse.newInstance();
        if (inputExpression.length() == 0)
        {
            //return (String) "";
            return evaluationResponse;
        }
        String resultString = "";
        try
        {
            environment.iEvalDepth = 0;
            environment.iEvaluator.resetStack();


            iError = null;

            ConsPointer inputExpressionPointer = new ConsPointer();
            if (environment.iPrettyReader != null)
            {
                InputStatus someStatus = new InputStatus();
                StringBuffer inp = new StringBuffer();
                inp.append(inputExpression);
                InputStatus oldstatus = environment.iInputStatus;
                environment.iInputStatus.setTo("String");
                StringInputStream newInput = new StringInputStream(new StringBuffer(inputExpression), environment.iInputStatus);

                InputStream previous = environment.iCurrentInput;
                environment.iCurrentInput = newInput;
                try
                {
                    ConsPointer args = new ConsPointer();
                    UtilityFunctions.internalApplyString(environment, inputExpressionPointer,
                            environment.iPrettyReader,
                            args);
                } catch (Exception e)
                {
                    throw new MathPiperException(e.getMessage(), -1);//Note:tk. Throw MathPiperException instead of just exception.

                } finally
                {
                    environment.iCurrentInput = previous;
                    environment.iInputStatus.restoreFrom(oldstatus);
                }
            } else
            {

                InputStatus someStatus = new InputStatus();

                StringBuffer inp = new StringBuffer();
                inp.append(inputExpression);
                inp.append(";");
                StringInputStream inputExpressionBuffer = new StringInputStream(inp, someStatus);

                Parser infixParser = new MathPiperParser(tokenizer, inputExpressionBuffer, environment, environment.iPrefixOperators, environment.iInfixOperators, environment.iPostfixOperators, environment.iBodiedOperators);
                infixParser.parse(inputExpressionPointer);
            }

            ConsPointer result = new ConsPointer();
            environment.iEvaluator.evaluate(environment, result, inputExpressionPointer);

            String percent = environment.getTokenHash().lookUp("%");
            environment.setVariable(percent, result, true);

            StringBuffer string_out = new StringBuffer();
            OutputStream output = new StringOutputStream(string_out);

            if (environment.iPrettyPrinter != null)
            {
                ConsPointer nonresult = new ConsPointer();
                UtilityFunctions.internalApplyString(environment, nonresult,
                        environment.iPrettyPrinter,
                        result);
                resultString = string_out.toString();
            } else
            {
                printer.rememberLastChar(' ');
                printer.print(result, output, environment);
                resultString = string_out.toString();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            //System.out.println(e.toString());
            //System.out.println(e.getMessage() );

            //Note:tk throw MathPiperException instead of simply printing the exception message.
            iError = e.getMessage();
            int errorLineNumber = -1;
            if (e instanceof MathPiperException)
            {
                MathPiperException mpe = (MathPiperException) e;
                errorLineNumber = mpe.getLineNumber();
            }
            evaluationResponse.setErrorMessage(iError);
            evaluationResponse.setLineNumber(errorLineNumber);
        //throw new MathPiperException(iError, errorLineNumber);
        }


        evaluationResponse.setResult(resultString.trim());

        String sideEffects = sideEffectsStream.toString();

        if (sideEffects != null && sideEffects.length() != 0)
        {
            //resultMap.put("side_effects", sideEffects);
            evaluationResponse.setSideEffects(sideEffects.toLowerCase());
        }

        GlobalVariable loadResultVariable = (GlobalVariable) environment.iGlobalState.lookUp("LoadResult");
        if (loadResultVariable != null)
        {
            String loadResultString = loadResultVariable.toString();
            if (loadResultString != null && loadResultString.length() != 0)
            {
                //resultMap.put("load_result", loadResult);
                evaluationResponse.setLoadResult(loadResultString.trim());
            }
        }





        return evaluationResponse;
    }

    public void haltEvaluation()
    {
        environment.iEvalDepth = environment.iMaxEvalDepth + 100;
    }

    public Environment getEnvironment()
    {
        return environment;
    }
}
