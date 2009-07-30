//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)
package org.mathrider.jfreechartplugin;

import org.gjt.sp.jedit.*;

/**
 * 
 * @author Ted Kosan
 */
public class JFreeChartPlugin extends EditPlugin implements EBComponent{
	public static final String NAME = "jfreechart";
	public static final String OPTION_PREFIX = "options.jfreechart.";
	
	
	public void start()
	{
		
		//jEdit.getActiveView().getDockableWindowManager().addDockableWindow(org.mathrider.mathpiperplugin.MathPiperPlugin.NAME);
		//jEdit.getActiveView().getDockableWindowManager().showDockableWindow( "mathpiper" );
		//System.out.println("************************************************MathPiper plugin started...");
		EditBus.addToBus(this);
		
	}//end method.
	
	
	public void handleMessage(EBMessage msg)
	{
		//System.out.println("************************************************MathPiper plugin received editor message... "+ msg);
		if (msg instanceof org.gjt.sp.jedit.msg.EditorStarted) {
			//System.out.println("************************************************MathPiper plugin received editor started message...");
			org.mathpiper.interpreters.Interpreter synchronousInterpreter = org.mathpiper.interpreters.Interpreters.getSynchronousInterpreter();
			org.mathpiper.interpreters.EvaluationResponse response = synchronousInterpreter.evaluate("[Import(\"org/mathpiper/builtin/functions/plugins/jfreechart/\"); Plot2DOutputs()[\"default\"] := \"jfreechart\";];");
			if(response.isExceptionThrown())
			{
				System.out.println(response.getExceptionMessage());
			}//end if.
		}//*/

	}//end method.
	
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
