//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathrider.mathpiperplugin;

//import org.mathrider.piper.CPiper;
//import org.mathrider.piper.Piperexception;
import org.mathpiper.Interpreter;
import org.mathpiper.exceptions.MathPiperException;
import console.Output;

import java.io.*;


import errorlist.*;

/**
 *
 * @author tk
 */


 public class MathPiperInterpreter { 

	private static MathPiperInterpreter instance = null;
    private Interpreter mathPiper;
	private StringOutput stringOutput;
	//java.util.zip.ZipFile scriptsZip;
	
	private static DefaultErrorSource errorSource;
 
    /** Creates a new instance of MathPiperInterpreter */
    protected MathPiperInterpreter() throws MathPiperException {
		
		stringOutput = new StringOutput();
	    
		mathPiper = new Interpreter(stringOutput);
        
 
    }//end constructor.
	
   public static MathPiperInterpreter getInstance() throws MathPiperException{
      if(instance == null) {
         instance = new MathPiperInterpreter();
      }
      return instance;
   }//end method.
   

   
   public java.util.zip.ZipFile getScriptsZip()
   {
	   return mathPiper.getScriptsZip();
   }//end method.

 
    /** Use this method to pass an expression to the MathPiper interpreter.
     *  Returns the output of the interpreter.
     */
    public String[] evaluate(String input) throws MathPiperException {
		

			String result = mathPiper.evaluate(input);

			result = result.trim();
			
			String loadResult = mathPiper.evaluate("LoadResult;");
			loadResult = loadResult.trim();
			
			mathPiper.evaluate(result + ";");//Note:tk:eventually reengineer previous result mechanism in MathPiper.
			
			String sideEffect = stringOutput.toString();
			
			String[] resultsAndSideEffect = new String[3];
			
			if(sideEffect != null && sideEffect.length() != 0){
				resultsAndSideEffect[1] = sideEffect; 
			}
			else
			{
				resultsAndSideEffect[1] = null;
			}
			
			
			if(loadResult != null && loadResult.length() != 0){
				resultsAndSideEffect[2] = loadResult; 
			}
			else
			{
				resultsAndSideEffect[2] = null;
			}
			
			resultsAndSideEffect[0] = result;
			
			return resultsAndSideEffect;

    }//end method.
	
	
	public static DefaultErrorSource getErrorSource()
	{
		if(errorSource == null)
		{
			errorSource = new DefaultErrorSource("MathRider");
			ErrorSource.registerErrorSource(errorSource);
		}//end if.

		return errorSource;
	}//end method.
    

    
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
