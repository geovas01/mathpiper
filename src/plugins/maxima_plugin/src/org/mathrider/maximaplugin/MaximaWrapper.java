package org.mathrider.maximaplugin;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.*;


import errorlist.*;

/**
 *
 * @author tk
 */


 public class MaximaWrapper { 

//	private static org.gjt.sp.jedit.bsh.Interpreter bshInstance;
	private static MaximaWrapper maximaInstance = null;
	
	private StringBuffer resultBuffer;
	private Pattern inputPromptPattern;
	private InputStream inputStream;
	private OutputStream outputStream;
	private String result;
	private String startMessage;

    /** Creates a new instance of MaximaWrapper */
    protected MaximaWrapper() throws IOException
	{

	
		ArrayList command = new ArrayList();
		command.add("C:\\Program Files\\Maxima-5.15.0\\bin\\maxima.bat");
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		Process maximaProcess = processBuilder.start();
		inputStream = maximaProcess.getInputStream();
		outputStream = maximaProcess.getOutputStream();
		resultBuffer = new StringBuffer();
		inputPromptPattern = Pattern.compile("\\n\\(%i[0-9]+\\)");
		startMessage = getResponse();
 
    }//end constructor.
	
	public String getStartMessage()
	{
		return startMessage;
	}//end method.
	
   public static MaximaWrapper getInstance() throws IOException
   {
      if(maximaInstance == null) {
         maximaInstance = new MaximaWrapper();
      }
      return maximaInstance;
   }//end method.
   
   
   
    String evaluate(String send) throws IOException
    {
        outputStream.write(send.getBytes());
        outputStream.flush();
        //new Thread( max ).start();
        return getResponse();
    }//end send.
   
   
	public String getResponse() throws IOException
    {
	    boolean keepRunning = true;
		
		mainLoop: while(keepRunning)
        {
             int serialAvailable = inputStream.available();
             if (serialAvailable == 0)
             {
				 try
				 {
					 Thread.sleep(100);
				 }
				 catch(InterruptedException ie)
				 {
					 System.out.println("Maxima session interrupted.");
				 }
                continue mainLoop;
             }//end while

               byte[] bytes = new byte[serialAvailable];
                
				inputStream.read( bytes, 0, serialAvailable );
               resultBuffer.append(new String(bytes));
               result = resultBuffer.toString();
               //System.out.println("SSSSS " + str);
               Matcher matcher = inputPromptPattern.matcher(result);
               if(matcher.find())
               {
                    //System.out.println("PPPPPP found end");
                    resultBuffer.delete(0,resultBuffer.length());
                    keepRunning = false;
                    
               }//end if.

                System.out.println("NNNNNNN " + result);

				//self.terminalOutputStream.write( bytes )
				
				//event = utility.DataEvent( self, bytes, self.packetMode )
				
				//self.notifyListeners( event )

        }//end while.
		
		return result;
        
    }//end method



    
}//end class.

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
