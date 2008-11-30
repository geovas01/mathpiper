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
        this.result = result;
    }

    public String getSideEffects()
    {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects)
    {
        this.sideEffects = sideEffects;
    }

    public String getExceptionMessage()
    {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage)
    {
        this.exceptionMessage = exceptionMessage;
    }

    
    public Exception getException()
    {
        return exception;
    }

    public void setException(Exception exception)
    {
        this.exception = exception;
    }

    public String getLoadResult()
    {
        return loadResult;
    }

    public void setLoadResult(String loadResult)
    {
        this.loadResult = loadResult;
    }
    
    

}
