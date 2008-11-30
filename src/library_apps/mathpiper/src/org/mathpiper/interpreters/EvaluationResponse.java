/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mathpiper.interpreters;

/**
 *
 */
public class EvaluationResponse {
    private String result = "";
    private String loadResult = "";
    private String sideEffects = "";
    private String exceptionMessage = "";
    private boolean exceptionThrown = false;
    private Exception exception = null;
    private int lineNumber;
            
    private EvaluationResponse()
    {
    }
    
    public static EvaluationResponse newInstance()
    {
        return new EvaluationResponse();
    }

    public int getLineNumber()
    {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber)
    {
        this.lineNumber = lineNumber;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result.trim();
    }

    public String getSideEffects()
    {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects)
    {
        this.sideEffects = sideEffects.trim();
    }

    public String getExceptionMessage()
    {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage)
    {
        this.exceptionMessage = exceptionMessage.trim();
    }

    
    public Exception getException()
    {
        return exception;
    }

    public void setException(Exception exception)
    {
        this.exceptionThrown = true;
        this.exception = exception;
    }

    public boolean isExceptionThrown()
    {
        return exceptionThrown;
    }
        
    public String getLoadResult()
    {
        return loadResult;
    }

    public void setLoadResult(String loadResult)
    {
        this.loadResult = loadResult.trim();
    }
    
    

}
