package org.mathrider.jyacasplugin;

import java.io.*;
import net.sf.yacas.LispOutput;

class StringOutput extends LispOutput
{
	StringBuffer stringBuffer;
	
	public StringOutput()
	{
		this.stringBuffer = new java.lang.StringBuffer();
	}
	
	public void PutChar(char aChar)
	{
		this.stringBuffer.append(aChar);
	}

	    
	/*public void setStringBuffer(StringBuffer stringBuffer)
	{
		this.stringBuffer = stringBuffer;
	}//end method.*/
	
	
	public String toString()
	{
		if(this.stringBuffer.length() != 0)
		{
			String outputMessage = this.stringBuffer.toString();
			this.stringBuffer.delete(0,this.stringBuffer.length());
			return outputMessage;
		}
		else
		{
			return null;
		}//end else.


	}//end method.
	    
}//end class.
		



              
// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
