package org.mathpiper.ui.gui.help;


public class HelpEvent
{

    private String sourceCode = null;


    public HelpEvent()
    {
        super();
    }


    public HelpEvent(String sourceCode)
    {
        this.sourceCode = sourceCode;
    }


    

    public String getSourceCode() {
        return sourceCode;
    }


    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }



}//end class.