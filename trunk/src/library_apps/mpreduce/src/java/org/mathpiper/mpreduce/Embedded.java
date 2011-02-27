package org.mathpiper.mpreduce;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

/**
 *
 *
 */
public class Embedded {

    Jlisp jlisp;
    private static Embedded JlispCASInstance = null;
    private StringBuffer responseBuffer;
    private Pattern inputPromptPattern;
    private PipedInputStream myInputStream;
    private PipedOutputStream myOutputStream;
    private String response;
    private String startMessage;
    private String prompt;
    private Thread reduceThread;


    public Embedded() {

        System.out.println("MPReduce version .03");

        jlisp = new Jlisp();

        try {

            myOutputStream = new PipedOutputStream();
            myInputStream = new PipedInputStream();

            final PipedOutputStream jLispOutputStream = new PipedOutputStream(myInputStream);
            final PipedInputStream jLispInputStream = new PipedInputStream(myOutputStream);

            //myOutputStream.connect(jLispInputStream);
            //myInputStream.connect(jLispOutputStream);



            final String[] args = new String[0];

            reduceThread = new Thread(new Runnable() {

                public void run() {
                    try {
                        jlisp.startup(args,
                                new InputStreamReader(new BufferedInputStream(jLispInputStream)),
                                new PrintWriter(new BufferedOutputStream(jLispOutputStream)),
                                true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            });

            reduceThread.setName("MPReduce");

            reduceThread.start();


            responseBuffer = new StringBuffer();
            inputPromptPattern = Pattern.compile("\\n[0-9]+\\:");
            
          
            startMessage = getResponse();
            

            //Initialize MPReduce.
            send("off int; on errcont; off nat;");

        } catch (Throwable t) {
            t.printStackTrace();

        }



    }//end constructor.


    public String getStartMessage() {
        return startMessage;
    }//end method.


    public String getPrompt() {
        return prompt;
    }//end method.


    public static Embedded getInstance() throws Throwable {
        if (JlispCASInstance == null) {
            JlispCASInstance = new Embedded();
        }
        return JlispCASInstance;
    }//end method.


    public synchronized void send(String send) throws Throwable {
    	send = send + ";\n";
        myOutputStream.write(send.getBytes());
        myOutputStream.flush();
        
    }//end send.


    public void haltEvaluation()
    {
        jlisp.interruptEvaluation = true;
    }


    public String getResponse() throws Throwable {
        boolean keepChecking = true;

        mainLoop:
        while (keepChecking) {
            int serialAvailable = myInputStream.available();
            if (serialAvailable == 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ie) {
                    System.out.println("Jlisp session interrupted.");
                }
                continue mainLoop;
            }//end while

            byte[] bytes = new byte[serialAvailable];

            myInputStream.read(bytes, 0, serialAvailable);
            responseBuffer.append(new String(bytes));
            response = responseBuffer.toString();
            //System.out.println("SSSSS " + response);
            Matcher matcher = inputPromptPattern.matcher(response);
            if (matcher.find()) {
                //System.out.println("PPPPPP found end");

                responseBuffer.delete(0, responseBuffer.length());

                int promptIndex = matcher.start();

                prompt = response.substring(promptIndex, response.length());
                response = response.substring(0, promptIndex);

                response = response.trim();
                 

                keepChecking = false;

            }//end if.

        }//end while.

        return response;

    }//end method


    public static void main(String[] args) {
        Embedded mpreduce = new Embedded();

        String result = "";

        try
        {


            mpreduce.send("(X-Y)^100;");
            //mpreduce.send("while 1 < 2 do 1;");

            Thread.sleep(1000);
            System.out.println("Interrupting reduce thread.");
            mpreduce.haltEvaluation();
            result = mpreduce.getResponse();
            System.out.println(result);


            mpreduce.send("2+2;");
            result = mpreduce.getResponse();
            System.out.println(result);

        }
        catch(Throwable t)
        {
            t.printStackTrace();
        }

        

    }

}//end class.

