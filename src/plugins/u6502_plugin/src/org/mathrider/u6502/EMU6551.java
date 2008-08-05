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


public class EMU6551 implements IOChip
{
	private int[] registers;
	
	public EMU6551()
	{
		registers = new int[2];
		registers[1] = 0x10;
		
	}//Constructor.
	
	public int read(int location)
	{
		return registers[location & 0x3];
	}//end method.
	
	public void write(int location)
	{
		System.out.println(registers[location & 0x3]);
	}//end method.
	
}//end class.

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
