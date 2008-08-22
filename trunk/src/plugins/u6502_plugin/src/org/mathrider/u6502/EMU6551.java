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

/*
;Transmitt Receive Register.
6551TRR	equ A000h ;400ah


;Status Register.  
;		    Status			          cleared by
;	b0	Parity error * (1: error)       self clearing **
;	b1	Framing error * (1: error)      self clearing **
;	b2	Overrun * (1: error)            self clearing **
;	b3	Receive Data Register Full (1: full)  Read Receive Data Register
;	b4	Transmit Data Reg Empty (1: empty) Write Transmit Data Register
;	b5	DCD (0: DCD low, 1: DCD high) Not resettable, reflects DCD state
;	b6	DSR (0: DSR low, 1: DCD high) Not resettable, reflects DSR state
;	b7	IRQ (0: no int., 1: interrupt)  Read Status Register
;                                                                                                            
;	note: * no interrupt generated for these conditions
;	      ** cleared automatically after a read of RDR and the next 
;		error free receipt of data
6551StR	equ A001h ;400bh  


;Comand Register  
;	b0	Data Terminal Ready
;			0 : disable receiver and all interrupts (DTR high)
;			1 : enable receiver and all interrupts (DTR low)
;	b1	Receiver Interrupt Enable
;			0 : IRQ interrupt enabled from bit 3 of status register
;			1 : IRQ interrupt disabled
;	b3,b2	Transmitter Control
;				Transmit Interrupt    RTS level    Transmitter
;			00	   disabled		high		off
;			01	   enabled		low		on
;			10	   disabled		low		on
;			11	   disabled		low	   Transmit BRK
;	b4	Normal/Echo Mode for Receiver
;			0 : normal
;			1 : echo (bits 2 and 3 must be 0)
;	b5	Parity Enable
;			0 : parity disabled, no parity bit generated or received
;			1 : parity enabled
;	b7,b6	Parity
;			00 : odd parity receiver and transmitter
;			01 : even parity receiver and transmitter
;			10 : mark parity bit transmitted, parity check disabled
;			11 : space parity bit transmitted, parity check disabled


;6551CmR	equ 0b202h  

;Control Register
;
;	b3-b0	baud rate generator:
;			0000 : 16x external clock
;			0001 : 50 baud
;			0010 : 75
;			0011 : 110
;			0100 : 134.5
;			0101 : 150
;			0110 : 300
;			0111 : 600
;			1000 : 1200
;			1001 : 1800
;			1010 : 2400
;			1011 : 3600
;			1100 : 4800
;			1101 : 7200
;			1110 : 9600
;			1111 : 19,200
;	b4	receiver clock source
;			0 : external receiver clock
;			1 : baud rate generator
;	b6,b5	word length                                                                  
;			00 : 8 bits
;			01 : 7
;			10 : 6
;			11 : 5
;	b7	stop bits
;			0 : 1 stop bit
;			1 : 2 stop bits
;			    (1 stop bit if parity and word length = 8)
;			    (1 1/2 stop bits if word length = 5 and no parity)
;6551CtR	equ 0b203h  
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class EMU6551 extends javax.swing.JPanel implements IOChip, ActionListener, KeyListener
{
	private int[] registers;
	private java.util.Random rnd = new java.util.Random( 839374 );

	private JButton button1, button2;
	private JTextArea typeArea;
	private JScrollPane typePane;
	private AbstractQueue keySendQueue;
	private char[] typedKey = new char[1];
	private CircularBuffer buffer;
	private EMU6502 emulator;
	
	private JPanel buttons;
	private boolean deleteFlag = false;

	public EMU6551()
	{
		registers = new int[4];
		registers[1] = 0x10;
		buffer = new CircularBuffer();

		this.setLayout(new BorderLayout());

		//keySendQueue = new java.util.concurrent.ArrayBlockingQueue(30);

		buttons = new JPanel();
		
		Box guiBox = new Box(BoxLayout.Y_AXIS);
		typeArea = new JTextArea(30,20);
		typeArea.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
		typeArea.addKeyListener(this);
		typePane = new JScrollPane(typeArea);
		guiBox.add(typePane);
		
		button1 = new JButton("Reset");
		//button1.setBackground(Color.green);
		button1.addActionListener(this);
		buttons.add(button1);
		//button2 = new JButton("Close GeoGebra");
		//button2.setBackground(Color.red);
		//button2.addActionListener(this);
		//buttons.add(button2);
		this.add(buttons,BorderLayout.NORTH);
		this.add(guiBox,BorderLayout.CENTER);
		
		emulator = new EMU6502(this);
		

	}//Constructor.



	public void actionPerformed(ActionEvent event)
	{
		Object src = event.getSource();

		if (src == button1)
		{
			emulator.reset();
		}
		else if (src == button2)
		{

		}

	}



	public void keyPressed(KeyEvent e)
	{
	}

	public void keyReleased(KeyEvent e)
	{
	}

	public void keyTyped(KeyEvent e)
	{

		char key = e.getKeyChar();
		//System.out.println((int)key);

		if((int)key == 22)
		{
			try
			{
				String clipBoard = (String)java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().getData(java.awt.datatransfer.DataFlavor.stringFlavor);
				
				if(clipBoard.length() != 0)
				{
					char[] chars = clipBoard.toCharArray();
					for(int x = 0; x < chars.length; x++)
					{
						buffer.put((int) chars[x]);

					}//end for.
					setReceiveDataRegisterFull(true);
				}//end if.

			}
			catch(NullPointerException ev)
			{
				ev.printStackTrace();
			}
			catch( IllegalStateException ev)
			{
				ev.printStackTrace();
			}
			catch(java.awt.datatransfer.UnsupportedFlavorException ev)
			{
				ev.printStackTrace();
			}
			catch(java.io.IOException ev)
			{
				ev.printStackTrace();
			}
		}
		else
		{
			//System.out.println(key);
			//registers[0] = (int) key;
			buffer.put((int) key);
			setReceiveDataRegisterFull(true);
		}
	}




	public int read(int location)
	{
		if(location > 3)
		{
			return rnd.nextInt(256);
		}
		else if(location == 2 && (registers[1] & 0x8) == 0)
		{
			try
			{
				Thread.sleep(200);
			}
			catch(InterruptedException e)
			{
			}
			
			return 0;
		}
		else
		{
			if(location == 0)
			{
				registers[0] = buffer.get();
			}

			if(buffer.isEmpty())
			{
				setReceiveDataRegisterFull(false);
			}

			return registers[location & 0x3];
		}
	}//end method.



	public void write(int location, int value)
	{
		//System.out.println(value);
		
		location = location & 3;
		
		if(value == 8)
		{
			deleteFlag = true;
		}
		else if(value == 32 && deleteFlag == true)
		{
			deleteFlag = false;
		}
		else if(location == 0 )
		{
			//System.out.print((char)value);
			//System.out.println(value);
			typedKey[0] = (char)value;
			typeArea.append(new String(typedKey));
			typeArea.setCaretPosition( typeArea.getDocument().getLength() );

		}
		registers[location] = value;

	}//end method.


	private synchronized void setReceiveDataRegisterFull(boolean state)
	{
		if(state == true)
		{
			registers[1] |= 0x8;
		}
		else
		{
			registers[1] &= 0xf7;
		}
	}//end method.
	
	
	public static void main(String[] args)
	{
		EMU6551 uart = new EMU6551();
		
		JFrame frame = new javax.swing.JFrame();
		Container contentPane = frame.getContentPane();
		contentPane.add(uart,BorderLayout.CENTER);
		//frame.setAlwaysOnTop(true);
		frame.setSize(new Dimension(600, 600));
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		//frame.setResizable(false);
		frame.setPreferredSize(new Dimension(600, 600));
		frame.setLocationRelativeTo(null); // added
		frame.pack();
		frame.setVisible(true);
	}//end main.




}//end class.

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
