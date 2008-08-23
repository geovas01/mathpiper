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

package org.mathrider.u6502;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class EMUOutputPort extends javax.swing.JPanel implements IOChip, ActionListener
{
	private int[] registers;
	
	private JLabel[] bits = new JLabel[8];
	
	private JButton button1, button2;
	
	private ImageIcon led0;
	
	private java.awt.Image onImage, offImage;


	public EMUOutputPort()
	{


		this.setLayout(new BorderLayout());

		
		Box guiBox = new Box(BoxLayout.X_AXIS);
		
		
		java.io.InputStream inputStream = org.gjt.sp.jedit.jEdit.getPlugin("org.mathrider.u6502plugin.U6502Plugin").getPluginJAR().getClassLoader().getResourceAsStream( "resources/images/gray.gif" );
		java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream( inputStream );
		byte[] buffer = new byte[4096];
		try 
		{
			bufferedInputStream.read( buffer, 0, 4096 );
		} catch( Exception e )
		{
			System.err.println( "Failed to read image." );
		}
		onImage = java.awt.Toolkit.getDefaultToolkit().createImage( buffer );
		led0 = new javax.swing.ImageIcon( onImage );
		
		
		bits[0] = new JLabel("LED-0", led0, SwingConstants.CENTER);
		guiBox.add(bits[0]);
		
		button1 = new JButton("Reset");
		//button1.setBackground(Color.green);
		button1.addActionListener(this);
		//buttons.add(button1);
		//button2 = new JButton("Close GeoGebra");
		//button2.setBackground(Color.red);
		//button2.addActionListener(this);
		//buttons.add(button2);
		//this.add(buttons,BorderLayout.NORTH);
		this.add(guiBox,BorderLayout.CENTER);
		

	}//Constructor.



	public void actionPerformed(ActionEvent event)
	{
		Object src = event.getSource();

		if (src == button1)
		{
			
		}
		else if (src == button2)
		{

		}

	}





	public int read(int location)
	{
		return registers[location & 0x3];
	}//end method.



	public void write(int location, int value)
	{
		//System.out.println(value);
		
		registers[location] = value;

	}//end method.



}//end class.

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
