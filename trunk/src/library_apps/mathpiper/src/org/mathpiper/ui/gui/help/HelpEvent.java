package org.mathpiper.ui.gui.help;


public class HelpEvent
{
    private String filePath = null;

    private String sourceCode = null;


    public HelpEvent()
    {
        super();
    }


    public HelpEvent(String filePath, String sourceCode)
    {
    	this.filePath = filePath;
    	
        this.sourceCode = sourceCode;
    }


    
    public String getFilePath() {
        return filePath;
    }


    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    

    public String getSourceCode() {
        return sourceCode;
    }


    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }



}//end class.