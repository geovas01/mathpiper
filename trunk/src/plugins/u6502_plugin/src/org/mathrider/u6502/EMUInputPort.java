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

public class EMUInputPort extends javax.swing.JPanel implements IOChip, ActionListener
{
	private JToggleButton[] bits = new JToggleButton[8];
	
	private JButton button1, button2;
	
	//private ImageIcon Switch0, Switch1, Switch2, Switch3, Switch4, Switch5, Switch6, Switch7;
	
	private Icon onIcon, offIcon;
	private int register = 0;
	
	private String label = "";


	public EMUInputPort(String label)
	{
		super();
		
		this.label = label;

		this.setLayout(new BorderLayout());

		
		Box switches = new Box(BoxLayout.X_AXIS);
		
		
		//Load on image.
		java.io.InputStream inputStream = org.gjt.sp.jedit.jEdit.getPlugin("org.mathrider.u6502plugin.U6502Plugin").getPluginJAR().getClassLoader().getResourceAsStream( "resources/images/button_on.png" );
		java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream( inputStream );
		byte[] buffer = new byte[4096];
		try 
		{
			bufferedInputStream.read( buffer, 0, 4096 );
		} catch( Exception e )
		{
			System.err.println( "FaiSwitch to read image." );
		}
		onIcon = new javax.swing.ImageIcon(java.awt.Toolkit.getDefaultToolkit().createImage( buffer ));
		
		//Load off image.
		inputStream = org.gjt.sp.jedit.jEdit.getPlugin("org.mathrider.u6502plugin.U6502Plugin").getPluginJAR().getClassLoader().getResourceAsStream( "resources/images/button_off.png" );
		bufferedInputStream = new java.io.BufferedInputStream( inputStream );
		buffer = new byte[4096];
		try 
		{
			bufferedInputStream.read( buffer, 0, 4096 );
		} catch( Exception e )
		{
			System.err.println( "FaiSwitch to read image." );
		}
		offIcon = new javax.swing.ImageIcon(java.awt.Toolkit.getDefaultToolkit().createImage( buffer ));
		
		
		//Initialize Switches.
		for(int x = 7; x > -1; x--)
		{
			bits[x] = new JToggleButton(offIcon);
			bits[x].setSelectedIcon(onIcon);
			bits[x].addActionListener(this);
			bits[x].setPreferredSize(new Dimension(22,22));
			switches.add(bits[x]); 
			switches.add(Box.createHorizontalStrut(3));
		}
		                            

		
		
		button1 = new JButton("Reset");
		//button1.setBackground(Color.green);
		button1.addActionListener(this);
		//buttons.add(button1);
		//button2 = new JButton("Close GeoGebra");
		//button2.setBackground(Color.red);
		//button2.addActionListener(this);
		//buttons.add(button2);
		//this.add(buttons,BorderLayout.NORTH);
		JPanel panel = new JPanel();
		//panel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel.add(new JLabel(label));
		panel.add(switches);
		this.add(panel,BorderLayout.CENTER);
		

	}//end constructor.
	
	public EMUInputPort()
	{
		this("");
		
	}//end constructor.
	


	public void actionPerformed(ActionEvent event)
	{
		Object src = event.getSource();
		System.out.println("Src: " + src);

			for(int x = 0, mask = 1; x < 8; mask = mask << 1, x++)
			{

				if(src == bits[x])
				{
					if(bits[x].isSelected())
					{
						register |= mask;
					}
					else
					{
						register &= ((~mask)&0xff);
					}
				}

			}//end for.

	}





	public int read(int location)
	{
		//System.out.println("XXXXX " + location);
		if(location > 0)
		{
			return(65);
		}
		else
		{
			return register;
		}
	}//end method.



	public void write(int location, int value)
	{
		//System.out.println(location + " " + value);


	}//end method.
	
	
	public void reset()
	{
	}//end method.



}//end class.

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
