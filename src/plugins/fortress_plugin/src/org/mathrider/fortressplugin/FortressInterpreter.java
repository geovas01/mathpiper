//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathrider.fortressplugin;

//import org.mathrider.piper.CPiper;
//import org.mathrider.piper.Piperexception;

import console.Output;
import org.mathrider.ResponseListener;
import java.io.*;
import errorlist.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author tk
 */


public class FortressInterpreter {
	private static DefaultErrorSource errorSource;
/*
	private static FortressInterpreter instance;


	//java.util.zip.ZipFile scriptsZip;

	

	//Creates a new instance of FortressInterpreter 
	protected FortressInterpreter() throws FortressException {





	}//end constructor.

	public static FortressInterpreter getInstance() throws FortressException{
		if(instance == null) {
			instance = new FortressInterpreter();
		}
		return instance;
	}//end method.



	public java.util.zip.ZipFile getScriptsZip()
	{
		return mathPiper.getScriptsZip();
	}//end method.





*/

	public static DefaultErrorSource getErrorSource()
	{
		if(errorSource == null)
		{
			errorSource = new DefaultErrorSource("MathRider");
			ErrorSource.registerErrorSource(errorSource);
		}//end if.

		return errorSource;
	}//end method.

	/*

	public void addResponseListener(ResponseListener listener)
	{
		responseListeners.add(listener);
	}//end method.

	public void removeResponseListener(ResponseListener listener)
	{
		responseListeners.remove(listener);
	}//end method.

	protected void notifyListeners(HashMap response)
	{
		//notify listeners.
		for(ResponseListener listener : responseListeners)
		{
			listener.response(response);

			if(listener.remove())
			{
				removeListeners.add(listener);
			}//end if.
		}//end for.


		//Remove certain listeners.
		for(ResponseListener listener : removeListeners)
		{

			if(listener.remove())
			{
				responseListeners.remove(listener);
			}//end if.
		}//end for.

		removeListeners.clear();

	}//end method.



	public void stopCurrentCalculation()
	{
		mathPiper.stopCurrentCalculation();
		stringOutput.clear();
	}


*/

}//end class.


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
 */ //}}}


// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
