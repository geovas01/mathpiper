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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 */
class AsynchronousInterpreter implements Interpreter
{
    private static AsynchronousInterpreter singletonInstance;
    private SynchronousInterpreter interpreter;
    private ExecutorService es = Executors.newSingleThreadExecutor();

    private AsynchronousInterpreter(SynchronousInterpreter interpreter)
    {
        this.interpreter = interpreter;
    }//end constructor.

    static AsynchronousInterpreter newInstance()
    {
        SynchronousInterpreter interpreter = SynchronousInterpreter.newInstance();
        return new AsynchronousInterpreter(interpreter);
    }

    static AsynchronousInterpreter getInstance()
    {
        if (singletonInstance == null)
        {
            SynchronousInterpreter interpreter = SynchronousInterpreter.getInstance();
            singletonInstance = new AsynchronousInterpreter(interpreter);
        }
        return singletonInstance;
    }

     static AsynchronousInterpreter newInstance(String docBase)
    {
        SynchronousInterpreter interpreter = SynchronousInterpreter.newInstance(docBase);
        return new AsynchronousInterpreter(interpreter);
    }

    static AsynchronousInterpreter getInstance(String docBase)
    {
        if (singletonInstance == null)
        {
            SynchronousInterpreter interpreter = SynchronousInterpreter.getInstance(docBase);
            singletonInstance = new AsynchronousInterpreter(interpreter);
        }
        return singletonInstance;
    }


    public boolean initialize() {
        return interpreter.initialize();
    }
    
    public synchronized EvaluationResponse evaluate(String inputExpression) {
	    return this.evaluate(inputExpression, false);
    }//end method.

    public EvaluationResponse evaluate(String inputExpression, boolean notifyEvaluationListeners)
    {
        return evaluate(inputExpression, notifyEvaluationListeners, "NONE");
    }//end method.
    
    public EvaluationResponse evaluate(String inputExpression, boolean notifyEvaluationListeners, String inputSource) 
    {
        FutureTask task = new EvaluationTask(new Evaluator(inputExpression, notifyEvaluationListeners, inputSource));
  
        es.submit(task);

        return EvaluationResponse.newInstance();  
    }

    public synchronized EvaluationResponse evaluate(Cons inputExpression) {
        return interpreter.evaluate(inputExpression);
    }


    public void addResponseListener(ResponseListener listener)
    {
	    interpreter.addResponseListener(listener);
    }//end method.

    public void removeResponseListener(ResponseListener listener)
    {
	    interpreter.removeResponseListener(listener);
    }//end method.


    public void haltEvaluation()
    {
        interpreter.haltEvaluation();
    }
    
    public void haltEvaluation(String message)
    {
        interpreter.haltEvaluation(message);
    }

    public Environment getEnvironment()
    {
        return interpreter.getEnvironment();
    }

    private class Evaluator implements Callable
    {

        private String expression;
	private boolean notifyEvaluationListeners;
        private String sourceName;

        public Evaluator(String expression, boolean notifyEvaluationListeners, String sourceName)
        {
            this.expression = expression;
	    this.notifyEvaluationListeners = notifyEvaluationListeners;
            this.sourceName = sourceName;
        }

        public EvaluationResponse call() throws Exception
        {
            EvaluationResponse evaluationResponse = interpreter.evaluate(expression, notifyEvaluationListeners, sourceName);
            return evaluationResponse;
        }
    } // MyCallable

    private class EvaluationTask extends FutureTask
    {

        public EvaluationTask(Callable arg0)
        {
            super(arg0);
        }

        @Override public void done()
        {
            EvaluationResponse evaluationResponse = null;
            try
            {
                evaluationResponse = (EvaluationResponse) get();
            } catch (ExecutionException e)
            {
                evaluationResponse = EvaluationResponse.newInstance();
            } catch (InterruptedException e)
            {
                evaluationResponse = EvaluationResponse.newInstance();
            }

        }//done.
    }//EvaluationTask.

    /*public java.util.zip.ZipFile getScriptsZip()
    {
        return interpreter.getScriptsZip();
    }//end method.*/
}//end class.
