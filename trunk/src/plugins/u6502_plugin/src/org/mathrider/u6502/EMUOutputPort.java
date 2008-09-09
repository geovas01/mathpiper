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
	private JLabel[] bits = new JLabel[8];
	
	private JButton button1, button2;
	
	//private ImageIcon led0, led1, led2, led3, led4, led5, led6, led7;
	
	private Icon onIcon, offIcon;
	private int register = 0;
	
	private String label = "";


	public EMUOutputPort(String label)
	{
		super();
		
		this.label = label;

		this.setLayout(new BorderLayout());

		
		Box leds = new Box(BoxLayout.X_AXIS);
		
		
		//Load on image.
		java.io.InputStream inputStream = org.gjt.sp.jedit.jEdit.getPlugin("org.mathrider.u6502plugin.U6502Plugin").getPluginJAR().getClassLoader().getResourceAsStream( "resources/images/yellow.gif" );
		java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream( inputStream );
		byte[] buffer = new byte[4096];
		try 
		{
			bufferedInputStream.read( buffer, 0, 4096 );
		} catch( Exception e )
		{
			System.err.println( "Failed to read image." );
		}
		onIcon = new javax.swing.ImageIcon(java.awt.Toolkit.getDefaultToolkit().createImage( buffer ));
		
		//Load off image.
		inputStream = org.gjt.sp.jedit.jEdit.getPlugin("org.mathrider.u6502plugin.U6502Plugin").getPluginJAR().getClassLoader().getResourceAsStream( "resources/images/gray.gif" );
		bufferedInputStream = new java.io.BufferedInputStream( inputStream );
		buffer = new byte[4096];
		try 
		{
			bufferedInputStream.read( buffer, 0, 4096 );
		} catch( Exception e )
		{
			System.err.println( "Failed to read image." );
		}
		offIcon = new javax.swing.ImageIcon(java.awt.Toolkit.getDefaultToolkit().createImage( buffer ));
		
		
		//Initialize LED 7.
		bits[7] = new JLabel(offIcon);
		leds.add(bits[7]); 
		leds.add(Box.createHorizontalStrut(3));
		                            
		//Initialize LED 6.         
		bits[6] = new JLabel(offIcon);
		leds.add(bits[6]);
		leds.add(Box.createHorizontalStrut(3));
				                    
		//Initialize LED 5.         
		bits[5] = new JLabel(offIcon);
		leds.add(bits[5]);
		leds.add(Box.createHorizontalStrut(3));
				                    
		//Initialize LED 4.         
		bits[4] = new JLabel(offIcon);
		leds.add(bits[4]);
		leds.add(Box.createHorizontalStrut(3));
				                    
		//Initialize LED 3.         
		bits[3] = new JLabel(offIcon);
		leds.add(bits[3]);
		leds.add(Box.createHorizontalStrut(3));
				                    
		//Initialize LED 2.         
		bits[2] = new JLabel(offIcon);
		leds.add(bits[2]);
		leds.add(Box.createHorizontalStrut(3));
				                    
		//Initialize LED 1.         
		bits[1] = new JLabel(offIcon);
		leds.add(bits[1]);
		leds.add(Box.createHorizontalStrut(3));
		                            
		//Initialize LED 0..         
		bits[0] = new JLabel(offIcon);
		leds.add(bits[0]);
		
		
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
		panel.add(leds);
		this.add(panel,BorderLayout.CENTER);
		

	}//end constructor.
	
	public EMUOutputPort()
	{
		this("");
		
	}//end constructor.
	


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
		//System.out.println("XXXXX " + location);
		if(location > 0)
		{
			return(66);
		}
		else
		{
			return register;
		}
	}//end method.



	public void write(int location, int value)
	{
		//System.out.println(location + " " + value);

		
		if(location == 0)
		{
			register = value;
			
			for(int x = 0, mask = 1; x < 8; mask = mask << 1, x++)
			{
				//System.out.println(mask + " " + x);
				if((value & mask) > 0)
				{
					bits[x].setIcon(onIcon);
				}
				else
				{
					bits[x].setIcon(offIcon);
				}
			}//end for.

		}

	}//end method.
	
	public void reset()
	{
			register = 0;
			
			for(int x = 0; x < 8;  x++)
			{

				bits[x].setIcon(offIcon);
			}//end for.
	}//end method.



}//end class.

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
