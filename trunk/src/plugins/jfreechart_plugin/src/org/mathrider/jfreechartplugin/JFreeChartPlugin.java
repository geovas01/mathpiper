//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)
package org.mathrider.jfreechartplugin;

import org.gjt.sp.jedit.*;
import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * 
 * @author Ted Kosan
 */
public class JFreeChartPlugin extends EditPlugin implements EBComponent{
	public static final String NAME = "jfreechart";
	public static final String OPTION_PREFIX = "options.jfreechart.";
	
	private static JPanel chartPanel;
	
	
	public void start()
	{
		chartPanel = new ChartPanel();
		
		//jEdit.getActiveView().getDockableWindowManager().addDockableWindow(org.mathrider.mathpiperplugin.MathPiperPlugin.NAME);
		//jEdit.getActiveView().getDockableWindowManager().showDockableWindow( "mathpiper" );
		//System.out.println("************************************************MathPiper plugin started...");
		
		
		//EditBus.addToBus(this);
		
	}//end method.
	
	
	public void handleMessage(EBMessage msg)
	{
		//System.out.println("************************************************MathPiper plugin received editor message... "+ msg);
		if (msg instanceof org.gjt.sp.jedit.msg.EditorStarted) {
			//System.out.println("************************************************MathPiper plugin received editor started message...");
			
			/*org.mathpiper.interpreters.Interpreter synchronousInterpreter = org.mathpiper.interpreters.Interpreters.getSynchronousInterpreter();
			org.mathpiper.interpreters.EvaluationResponse response = synchronousInterpreter.evaluate("[Import(\"org/mathpiper/builtin/functions/plugins/jfreechart/\"); Plot2DOutputs()[\"default\"] := \"jfreechart\";];");
			if(response.isExceptionThrown())
			{
				System.out.println(response.getExceptionMessage());
			}//end if.*/
			
			
		}//*/

	}//end method.
	
	
	private class ChartPanel extends JPanel implements org.mathpiper.interpreters.ResponseListener {
		
		public ChartPanel()
		{
			super(new BorderLayout());
			
			this.add(HistogramExample1.createDemoPanel());
			
			org.mathpiper.interpreters.Interpreter synchronousInterpreter = org.mathpiper.interpreters.Interpreters.getSynchronousInterpreter();
			org.mathpiper.interpreters.EvaluationResponse response = synchronousInterpreter.evaluate("[Import(\"org/mathpiper/builtin/functions/plugins/jfreechart/\"); Plot2DOutputs()[\"default\"] := \"jfreechart\";];");
			if(response.isExceptionThrown())
			{
				System.out.println(response.getExceptionMessage());
			}//end if.
		
			synchronousInterpreter.addResponseListener(this); 
		}//end consstructor
			
		public void response(org.mathpiper.interpreters.EvaluationResponse response)
		{
			//JFreeChart handler.
			if(response.getObject() != null)
			{
				Object object = response.getObject();
				if(object instanceof org.jfree.chart.ChartPanel)
				{	
					org.gjt.sp.jedit.jEdit.getActiveView().getDockableWindowManager().showDockableWindow( "jfreechart" );
					JPanel newChart = (JPanel) object;
					this.removeAll();
					this.add(newChart);
					this.revalidate();
				}//end if.//
			}//end if.*/
		    
		}//end method.
		
		
		public boolean remove()
		{
			return false;
		};
	
	
	}//end class.
	
	
	public static JPanel getChartPanel()
	{
		return chartPanel;
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
