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
package org.mathpiper.interpreters;

import java.util.ArrayList;
import org.mathpiper.interpreters.ResponseListener;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import org.mathpiper.lisp.Environment;

/**
 *
 */
class AsynchronousInterpreter implements Interpreter
{

    private ArrayList<ResponseListener> removeListeners;
    private ArrayList<ResponseListener> responseListeners;
    private static AsynchronousInterpreter singletonInstance;
    private SynchronousInterpreter interpreter;
    private String expression;

    private AsynchronousInterpreter(SynchronousInterpreter interpreter)
    {
        this.interpreter = interpreter;
        responseListeners = new ArrayList<ResponseListener>();
        removeListeners = new ArrayList<ResponseListener>();
    }//end constructor.

    public static AsynchronousInterpreter newInstance()
    {
        SynchronousInterpreter interpreter = SynchronousInterpreter.newInstance();
        return new AsynchronousInterpreter(interpreter);
    }

    public static AsynchronousInterpreter getInstance()
    {
        if (singletonInstance == null)
        {
            SynchronousInterpreter interpreter = SynchronousInterpreter.getInstance();
            singletonInstance = new AsynchronousInterpreter(interpreter);
        }
        return singletonInstance;
    }

    public EvaluationResponse evaluate(String expression)
    {

        /*
        http://saloon.javaranch.com/cgi-bin/ubb/ultimatebb.cgi?ubb=get_topic&f=27&t=002774
        If you are using java 5 then you can use callable
        If not, then write an abstract class that exposes a new 
        abstract method that implementations must override
        to do the *required* job. (This will contain code that 
        otherwise would have been in run())This class implements 
        Runnable and implements the run() method. In the run method
        call the abstract method. If it throws an exception then store it 
        and give a getter for this exception. You also have to provide a 
        method to enquire whether the task has finished or not!
         */

        //this.input = input;
        //new Thread(this,"MathPiper").start();

        FutureTask task = new EvaluationTask(new MyCallable(expression));
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(task);


        return EvaluationResponse.newInstance();


    }//end method.

    public String call() throws Exception
    {
        //try
        //{
        EvaluationResponse evaluationResponse = interpreter.evaluate(expression);
        notifyListeners(evaluationResponse);
        /*}
        catch(Exception e)
        {
        //Object[] error = new Object[5];
        HashMap errorMap = new HashMap();
        errorMap.put( "error_message",e.getMessage() );
        
        if(e instanceof MathPiperException)
        {
        MathPiperException mpe  = (MathPiperException) e;
        int errorLineNumber = mpe.getLineNumber();
        errorMap.put("line_number", errorLineNumber);
        }
        
        notifyListeners(errorMap);
        }*/
        return "";

    }

    public void addResponseListener(ResponseListener listener)
    {
        responseListeners.add(listener);
    }//end method.

    public void removeResponseListener(ResponseListener listener)
    {
        responseListeners.remove(listener);
    }//end method.

    protected void notifyListeners(EvaluationResponse response)
    {
        //notify listeners.
        for (ResponseListener listener : responseListeners)
        {
            listener.response(response);

            if (listener.remove())
            {
                removeListeners.add(listener);
            }//end if.
        }//end for.


        //Remove certain listeners.
        for (ResponseListener listener : removeListeners)
        {

            if (listener.remove())
            {
                responseListeners.remove(listener);
            }//end if.
        }//end for.

        removeListeners.clear();

    }//end method.

    public void addScriptsDirectory(String dir)
    {
        interpreter.addScriptsDirectory(dir);
    }

    public void haltEvaluation()
    {
        interpreter.haltEvaluation();
    }

    public Environment getEnvironment()
    {
        return interpreter.getEnvironment();
    }

    private class MyCallable implements Callable
    {

        private String expression;

        public MyCallable(String expression)
        {
            this.expression = expression;
        }

        public EvaluationResponse call() throws Exception
        {
            EvaluationResponse evaluationResponse = interpreter.evaluate(expression);
            return evaluationResponse;
        }
    } // MyCallable

    private class EvaluationTask extends FutureTask
    {

        public EvaluationTask(Callable arg0)
        {
            super(arg0);
        }

        public void done()
        {
            EvaluationResponse evaluationResponse = null;
            try
            {
                evaluationResponse = (EvaluationResponse) get();
            } catch (ExecutionException e)
            {
                evaluationResponse = EvaluationResponse.newInstance();
                evaluationResponse.setExceptionMessage(e.getMessage());
            } catch (InterruptedException e)
            {
                evaluationResponse = EvaluationResponse.newInstance();
                evaluationResponse.setExceptionMessage(e.getMessage());
            }

            notifyListeners(evaluationResponse);
        }//done.
    }//EvaluationTask.

    public java.util.zip.ZipFile getScriptsZip()
    {
        return interpreter.getScriptsZip();
    }//end method.
}//end class.
