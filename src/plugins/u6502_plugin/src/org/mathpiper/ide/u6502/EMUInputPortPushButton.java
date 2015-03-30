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

package org.mathpiper.ide.u6502;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EMUInputPortPushButton extends javax.swing.JPanel implements IOChip, ActionListener
{
	private JButton[] bits = new JButton[8];
	
	private JButton button1, button2;
	
	//private ImageIcon Switch0, Switch1, Switch2, Switch3, Switch4, Switch5, Switch6, Switch7;
	
	private Icon onIcon, offIcon;
	private int register = 0;
	
	private String label = "";


	public EMUInputPortPushButton(String label)
	{
		super();
		
		this.label = label;

		this.setLayout(new BorderLayout());

		
		Box switches = new Box(BoxLayout.X_AXIS);
		
		
		//Load on image.
		//java.io.InputStream inputStream = org.gjt.sp.jedit.jEdit.getPlugin("org.mathpiper.ide.u6502plugin.U6502Plugin").getPluginJAR().getClassLoader().getResourceAsStream( "resources/images/button_on.png" );
		java.io.InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("resources/images/black_push_button.png");
                
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
		// inputStream = org.gjt.sp.jedit.jEdit.getPlugin("org.mathpiper.ide.u6502plugin.U6502Plugin").getPluginJAR().getClassLoader().getResourceAsStream( "resources/images/button_off.png" );
                inputStream = this.getClass().getClassLoader().getResourceAsStream("resources/images/blue_push_button.png");
                
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
			bits[x] = new JButton(offIcon);
			//bits[x].setSelectedIcon(onIcon);
			//bits[x].addActionListener(this);
			bits[x].addChangeListener(new ChangeListener(){
					@Override
					public void stateChanged(ChangeEvent event){
							Object src = event.getSource();
							System.out.println("Src: " + src);
							//System.out.println("This is called on the click of the button");

							for(int x = 0, mask = 1; x < 8; mask = mask << 1, x++)
							{

								if(src == bits[x])
								{
									if(bits[x].getModel().isPressed())
									{
										bits[x].setIcon(onIcon);
										//System.out.println("The button is Pressed");
										register |= mask;
									}
									else
									{	
										bits[x].setIcon(offIcon);
										register &= ((~mask)&0xff);
									}
								}

							}//end for.
							
						}
					});
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
		SpringLayout layout = new SpringLayout();
		panel.setLayout(layout);
		JLabel deviceLabel = new JLabel(label);
		panel.add(deviceLabel);
		panel.add(switches);
		layout.putConstraint(SpringLayout.WEST, deviceLabel, 5, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, deviceLabel, 5, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, switches, 5, SpringLayout.EAST, deviceLabel);
		layout.putConstraint(SpringLayout.NORTH, switches, 5, SpringLayout.NORTH, panel);
		//layout.putConstraint(SpringLayout.EAST, panel, 5, SpringLayout.EAST, switches);
		layout.putConstraint(SpringLayout.SOUTH, panel, 5, SpringLayout.SOUTH, switches);

		this.add(panel,BorderLayout.CENTER);
		

	}//end constructor.
	
	public EMUInputPortPushButton()
	{
		this("");
		
	}//end constructor.
	

	
	public void actionPerformed(ActionEvent event)
	{
		Object src = event.getSource();
		System.out.println("Src: " + src);
		System.out.println("This is called on the click of the button");

			for(int x = 0, mask = 1; x < 8; mask = mask << 1, x++)
			{

				if(src == bits[x])
				{
					if(bits[x].isEnabled())
					{
						bits[x].setIcon(offIcon);
						System.out.println("The button is Pressed");
						register |= mask;
					}
					else
					{	
						bits[x].setIcon(onIcon);
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
