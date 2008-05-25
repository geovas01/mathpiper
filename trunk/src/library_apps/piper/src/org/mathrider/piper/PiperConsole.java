package org.mathrider.piper;


import java.io.*;
import java.util.*;

public class PiperConsole extends Thread
{
    static String readLine(InputStream aStream)
  {
    StringBuffer line = new StringBuffer();
    try
    {
      int c = aStream.read();
      while (c != '\n')
      {
      line.append((char)c);
      c = aStream.read();
      }
    }
    catch (Exception e)
    {
      System.out.println(e.toString());
    }
    return line.toString();
  }
  static boolean quitting = false;

  public static void main(String[] argv)
  {
    String defaultDirectory = null;
    String archive = "";

    {
      java.net.URL detectURL = java.lang.ClassLoader.getSystemResource("piperinit.pi");
      if (detectURL != null)
      {
        String detect = detectURL.getPath(); // file:/home/av/src/lib/piper.jar!/piperinit.pi
        archive = detect.substring(0, detect.lastIndexOf('!')); // file:/home/av/src/lib/piper.jar
//System.out.println("Found archive ["+archive+"]");
      }
      else
      {
//System.out.println("Archive not found!!!!");
      }
    }
    int i=0;
    while (i<argv.length)
    {
      if (argv[i].equals("--rootdir"))
      {
        i++;
        defaultDirectory = argv[i];
      }
      if (argv[i].equals("--archive"))
      {
        i++;
        archive = argv[i];
      }
      else
      {
        break;
      }
      i++;
    }
    int scriptsToRun = i;


    StdFileOutput stdoutput = new StdFileOutput(System.out);
    CPiper piper = new CPiper(stdoutput);
    piper.env.iCurrentInput = new CachedStdFileInput(piper.env.iInputStatus);

    try
    {
      String zipFileName = archive;//"file:/Users/ayalpinkus/projects/JavaPiper/piper.jar";
      java.util.zip.ZipFile z = new java.util.zip.ZipFile(new File(new java.net.URI(zipFileName)));
      LispStandard.zipFile = z;
    }
    catch(Exception e)
    {
      System.out.println("Failed to find piper.jar"+e.toString());
    }


    if (defaultDirectory != null)
    {
      String toEvaluate = "DefaultDirectory(\""+defaultDirectory+"\");";
      
      String result ="";
        try{
        result = piper.Evaluate(toEvaluate);
	}
	catch(Piperexception ye)
	{
	    ye.printStackTrace();
	}
      
      if (scriptsToRun == argv.length)
        System.out.println("Out> "+result);
    }
    {
      
	String result = "";
        try{
        result = piper.Evaluate("Load(\"piperinit.pi\");");
	}
	catch(Piperexception ye)
	{
	    ye.printStackTrace();
	}
      
      if (scriptsToRun == argv.length)
        System.out.println("Out> "+result);
    }
    if (scriptsToRun < argv.length)
    {
	    
     try{
      for (;scriptsToRun<argv.length;scriptsToRun++)
      {
        piper.Evaluate("Load(\""+argv[scriptsToRun]+"\");");
      }
	}
	catch(Piperexception ye)
	{
	    ye.printStackTrace();
	}	    

      return;
    }


    System.out.println("This is Piper version '" + CVersion.VERSION + "'.");

    System.out.println("Piper is Free Software--Free as in Freedom--so you can redistribute Piper or");
    System.out.println("modify it under certain conditions. Piper comes with ABSOLUTELY NO WARRANTY.");
    System.out.println("See the GNU General Public License (GPL) for the full conditions.");
//TODO fixme    System.out.println("Type ?license or ?licence to see the GPL; type ?warranty for warranty info.");
    System.out.println("See http://piper.sf.net for more information and documentation on Piper.");

    System.out.println("To exit Piper, enter  Exit(); or quit or Ctrl-c.\n");
/*TODO fixme
    System.out.println("Type ?? for help. Or type ?function for help on a function.\n");
    System.out.println("Type 'restart' to restart Piper.\n");
*/
    System.out.println("To see example commands, keep typing Example();\n");

//piper.Evaluate("BubbleSort(N(PSolve(x^3-3*x^2+2*x,x)), \"<\");");

    System.out.println("Piper in Java");
    while (!quitting)
    {
      System.out.print("In> ");
      String input =  readLine(System.in);
      
      String rs = "";
       try{
       rs = piper.Evaluate(input);
	}
	catch(Piperexception ye)
	{
	    ye.printStackTrace();
	}
      
      
      System.out.println("Out> "+rs);
      if (input.equals("quit")) quitting = true;
    }
  }
}
