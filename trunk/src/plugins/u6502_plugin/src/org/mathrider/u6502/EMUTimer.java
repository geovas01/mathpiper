/* {{{ License.

Copyright (C) 2008 Ted Kosan
                                                                                      
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
 
package org.mathrider.u6502;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class EMUTimer extends JPanel implements IOChip
{
	private java.util.Timer timer;
	private int register;
	private JTextField display;
	private String label;
	private java.util.Formatter sprintf;
	private StringBuilder stringBuilder;
	private Font bitstreamVera;
	private float fontSize = 12;

	public EMUTimer(String label)
	{
		super();
		
		java.io.InputStream inputStream = org.gjt.sp.jedit.jEdit.getPlugin("org.mathrider.u6502plugin.U6502Plugin").getPluginJAR().getClassLoader().getResourceAsStream( "resources/ttf-bitstream-vera-1.10/VeraMono.ttf" );
		try
		{
			bitstreamVera = Font.createFont (Font.TRUETYPE_FONT, inputStream);
			bitstreamVera = bitstreamVera.deriveFont(fontSize);
			
		
			stringBuilder = new StringBuilder();
			sprintf = new java.util.Formatter(stringBuilder, java.util.Locale.US);
			
			this.label = label;
			
			timer = new java.util.Timer();
			timer.scheduleAtFixedRate(new Task() ,0,100);
			
			JPanel panel = new JPanel();
			panel.add(new JLabel(label));
			
			display = new JTextField(2);
			display.setFont(bitstreamVera);
			display.setHorizontalAlignment(JTextField.RIGHT);
			sprintf.format("%02X",register);
			display.setText(stringBuilder.toString());
			stringBuilder.delete(0,stringBuilder.length());
			panel.add(display);
			this.add(panel,BorderLayout.CENTER);
		}
		catch(FontFormatException e)
		{
			e.printStackTrace();
		}
		catch(java.io.IOException e)
		{
			e.printStackTrace();
		}
		
	}//Constructor.
	
	private class Task extends java.util.TimerTask
	{
		public synchronized void run()
		{
			if(register == 0)
			{
				return;
			}
			else
			{
				register--;
				sprintf.format("%02X",register);
				display.setText(stringBuilder.toString());
				stringBuilder.delete(0,stringBuilder.length());
			}
		}//
	}//end method.
	

	
	public int read(int location)
	{	

		if(location == 0)
		{
			return register;
		}
		else
		{
			return 0xff;
		}

	}//end method.
	
	
	
	public synchronized void write(int location, int value)
	{
		//System.out.println("XXX " + location + " " + value);

		if(location == 0)
		{
			register = value;
		}
		
	}//end method.
	
	
	public synchronized void reset()
	{
		register = 0;
		display.setText("00");
	}//end methodl.


	
}//end class.


