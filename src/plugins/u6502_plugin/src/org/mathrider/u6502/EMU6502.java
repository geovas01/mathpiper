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

public class EMU6502
{
/*
PC	....	program counter			(16 bit)
AC	....	accumulator			(8 bit)
X	....	X register			(8 bit)
Y	....	Y register			(8 bit)
SR	....	status register [NV-BDIZC]	(8 bit)
SP	....	stack pointer			(8 bit)
IR    ....  instruction register (hidden)  	(8 bit)
cc - instruction category.
bbb - addressing mode.
aaa - instruction.
*/

//cc == 01.
	static final int ORA = 0;
	static final int AND = 1;
	static final int EOR = 2;
	static final int ADC = 3;
	static final int STA = 4;
	static final int LDA = 5;
	static final int CMP = 6;
	static final int SBC = 7;  
	
//Addressing modes 01.
	static final int $INDIRECT_X$_01 = 0;
	static final int INDIRECT_01 = 1;
	static final int IMMEDIATE_01 = 2;
	static final int ABSOLUTE_01 = 3;
	static final int $INDIRECT$_Y_01 = 4;
	static final int INDIRECT_X_01 = 5;
	static final int ABSOLUTE_Y_01 = 6;
	static final int ABSOLUTE_X_01 = 7;
	
//cc == 10.
	static final int ASL = 0;
	static final int ROL = 1;
	static final int LSR = 2;
	static final int ROR = 3;
	static final int STX = 4;
	static final int LDX = 5;
	static final int DEC = 6;
	static final int INC = 7;
	
	static final int BRK = 0X00;
	static final int JSR = 0X20;
	static final int RTI = 0X40;
	static final int RTS = 0X60;
	static final int PHP = 0X08;
	static final int PLP = 0X28;
	static final int PHA = 0X48;
	static final int PLA = 0X68;
	static final int DEY = 0X88;
	static final int TAY = 0XA8;
	static final int INY = 0XC8;
	static final int INX = 0XE8;
	static final int CLC = 0X18;
	static final int SEC = 0X38;
	static final int CLI = 0X58;
	static final int SEI = 0X78;
	static final int TYA = 0X98;
	static final int CLV = 0XB8;
	static final int CLD = 0XD8;
	static final int SED = 0XF8;
	
	static final int TXA = 0X8A;
	static final int TXS = 0X9A;
	static final int TAX = 0XAA;
	static final int TSX = 0XBA;
	static final int DEX = 0XCA;
	static final int NOP = 0XEA;					

	
	
//Addressing modes 10_00.
	static final int IMMEDIATE_10_00 = 0;
	static final int INDIRECT_10_00 = 1;
	static final int ACCUMULATOR_10 = 2;
	static final int ABSOLUTE_10_00 = 3;
	static final int RELATIVE = 4;
	static final int INDIRECT_X_10_00 = 5;
	static final int ABSOLUTE_X_10_00 = 7;

//cc == 00.
	static final int BIT = 1;
	static final int JMP_ABS = 2;
	static final int JMP_IND_ABS = 3;
	static final int STY = 4;
	static final int LDY = 5;
	static final int CPY = 6;
	static final int CPX = 7;
	
//Flags for branches.
	static final int NEGATIVE = 0;
	static final int OVERFLOW = 1;
	static final int CARRY = 2;
	static final int ZERO = 3;


    public int run;
	
	
    public int cc;
    public int bbb;
    public int aaa;
    public int pc;
    public int a;
    public int x;
    public int y;
    //public int sr
    public int sp;
    public int ir;
	
    public int n;	//Negative
    public int v;	//Overflow
    public int b;	//Break
    public int d;	//Decimal (use BCD for arithmetics)
    public int i;	//Interrupt (IRQ disable)
    public int z;	//Zero
    public int c;	//Carry

	public int ck_n;
	public int ck_v;
	public int ck_z;
	public int ck_c; 

	public int operand1;
	public int operand2;
    
    public int[] ram;
	public int[] rom;
	
	public Object[] memory;
	
	public Object mem;
	public int[] chip;
	public int[] chip2;
	
	public int block;
	public int block2;
	public int offset;
	public int offset2;
	public int oldOffset;
	public int access;
	
	public int tmp;
	public int tmp2;
	
	
	public int bothNegativeFlag;
	public int negativeLargerFlag;
	public int accumulatorFlag;
	
	

	
	public EMU6502()
	{
		super();
		
		ram = new int[1000];
		rom = new int[1000];
		memory = new Object[8];
		
		
	}//end constructor.
    
    

    public void run()
    {
		run = 1;
		
		n = 0;
		v = 0;
		b = 1;
		d = 0;
		i = 1;
		z = 0;
		c = 0;
		
		ck_n = 0;
		ck_v = 0;
		ck_z = 0;
		ck_c = 0; 
		
		sp = 0x01ff;
		pc = 0xe000;
		offset = (pc & 0x1fff);
		
		//Place chips into memory.
		memory[0] = ram;
		memory[7] = rom;
		

		
		while(run == 1)
		{
			block = (pc & 0xe000) >> 13;
			oldOffset = offset;
			offset = (pc & 0x1fff);
			
		//System.out.println("block: " + block);
		//System.out.println("offset: " + offset);
			
			chip = memory[block];
		System.out.println("offset:" + offset);
			ir = chip[offset++];
		System.out.printf("ir: %2x\n\n",ir);

			
		//System.out.printf("ir %x\n", ir);
			
	        cc = ir & 0x03;
	        bbb = (ir & 0x1c) >> 2;
	        aaa = (ir & 0xe0) >> 5;
			
	        if (cc == 01)
	        {
				
				switch(bbb)
				{
					case $INDIRECT_X$_01:
					break;
					
					case INDIRECT_01:
					break;
					
					case IMMEDIATE_01:
						chip2 = chip;
						offset2 = offset++;
					break;
					
					case ABSOLUTE_01:
						operand1 = chip[offset++];
						operand2 = chip[offset++];
						access = (operand2 << 8) | operand1;
						block2 = (access & 0xe000) >> 13;
						offset2 = (access & 0x1fff);
						chip2 = memory[block2];
						
					break;
					
					case $INDIRECT$_Y_01:
					break;
					
					case INDIRECT_X_01:
					break;
					
					case ABSOLUTE_Y_01:
						operand1 = chip[offset++];
						operand2 = chip[offset++];
						access = ((operand2 << 8) | operand1) + y;
						block2 = (access & 0xe000) >> 13;
						offset2 = (access & 0x1fff);
						chip2 = memory[block2];
					break;
					
					case ABSOLUTE_X_01:
						operand1 = chip[offset++];
						operand2 = chip[offset++];
						access = ((operand2 << 8) | operand1) + x;
						block2 = (access & 0xe000) >> 13;
						offset2 = (access & 0x1fff);
						chip2 = memory[block2];
					break;			
				
				}//end switch.
		
				switch(aaa)
				{
					case ORA:
						tmp = a = a | chip2[offset2];
						ck_n = ck_z = 1;
					break;
					
					case AND:
						tmp = a = a & chip2[offset2];
						ck_n = ck_z = 1;
					break;
					
					case EOR:
						tmp = a = a ^ chip2[offset2];
						ck_n = ck_z = 1;
					break;
					
					case ADC:
						
						tmp = (chip2[offset2]) & 0x80;
						tmp2 = a & 0x80;
	
						
						
						//Does overflow flag need to be set?
						if ((tmp ^ tmp2) == 0) //Numbers have the same sign.
						{
							ck_v = 1;
							
							//Are both numbers negative?
							if ((tmp & tmp2) > 0)
							{
								bothNegativeFlag = 1;
							}
							else
							{
								bothNegativeFlag = 0;
							}//End else.
						}
						else
						{
							ck_v = 0;
						}
						
						
						//Perform caclulation.
						tmp = a + chip2[offset2] + c;
						tmp = a = tmp & 0xff;
						
						
						//Set state of the overflow flag.
						if(ck_v == 1)
						{
							//Both negative.
							if(bothNegativeFlag > 0)
							{
								
								if((tmp & 0x100) > 0)
								{
									v = 1;
								}
								else
								{
									v = 0;
								}
							}
							else //Both positive.
							{
								if(tmp > 127)
								{
									v = 1;
								}
								else
								{
									v = 0;
								}
							}//end else
						}//end if.
						
						ck_n = ck_c = ck_z = 1;
						
					break;
					
					case STA:
						chip2[offset2] = a;
					break;
					
					case LDA:
						tmp = a = chip2[offset2];
						ck_n = ck_z = 1;
				//System.out.printf("Instruction: LDA,  data: %x\n", a);
					break;
					
					case CMP:
						tmp = a - chip2[offset2];
						ck_n = ck_c = ck_z = 1;
					break;
					
					case SBC:
					
						//Does overflow flag need to be set?
						tmp = (chip2[offset2]) & 0x80;
						tmp2 = a & 0x80;
						if ((tmp ^ tmp2) == 1) //Numbers do not have the same sign.
						{
							ck_v = 1;
							
							if (tmp > 0)
							{
								tmp2 = ( ((chip2[offset2]) * -1)) & 0xff;
								if (tmp2 > a)
								{
									negativeLargerFlag = 1;
								}
								else
								{
									negativeLargerFlag = 0;
								}
							}
							else
							{
								tmp2 = (a * -1) & 0xff;
								if (a > tmp2)
								{
									negativeLargerFlag = 1;
								}
								else
								{
									negativeLargerFlag = 0;
								}
							}//end else.
							
							
						}
						else
						{
							ck_v = 0;
						}//end else.
					
						//Perform calculation.
						tmp = a - chip2[offset2] + ((~c) & 0x01); //Note: check borrow.
						tmp = a = tmp & 0xff;
						
						
						//Set state of the overflow flag.
						if(ck_v == 1)
						{
							//Negative larger.
							if(negativeLargerFlag == 1)
							{
								
								if((tmp & 0x100) > 0)
								{
									v = 1;
								}
								else
								{
									v = 0;
								}
							}
							else //Positive larger.
							{
								if(tmp > 127)
								{
									v = 1;
								}
								else
								{
									v = 0;
								}
							}//end else
						}//end if.					
						
						
						
						
						ck_n = ck_c = ck_z = 1;
					break;				
				}//end switch.
				
	        }
	        else //cc == 10 or 00.
	        {
			
				switch(bbb)
				{
					case IMMEDIATE_10_00:
						chip2 = chip;
						offset2 = offset++;
					break;
					
					case INDIRECT_10_00:
					break;
					
					case ACCUMULATOR_10:
						accumulatorFlag = 1;
					break;
					
					case INDIRECT_X_10_00:
					break;
					
					case RELATIVE:
						operand1 = chip[offset++];
						
						//Indicator.
						tmp = aaa & 0x01;
						
						//Flag indicator..
						tmp2 = (aaa >> 1);
						
						switch(tmp2)
						{
							case NEGATIVE:
								tmp2 = n;
							break;
							
							case OVERFLOW:
								tmp2 = v;
							break;
							
							case CARRY:
								tmp2 = c;
							break;
	
							case ZERO:
								tmp2 = z;
							break;
							
						}//end switch.
						
						//Take branch if flag is same state as indicator.
						if (tmp2 == tmp)
						{
							offset = offset + operand1;
						}
						
					//No need to perform the rest of the main loop if a branch was encountered.
					pc = pc + offset;
					continue;
						
	
					
					case ABSOLUTE_10_00:
						operand1 = chip[offset++];
						operand2 = chip[offset++];
						access = (operand2 << 8) | operand1;
						block2 = (access & 0xe000) >> 13;
						offset2 = (access & 0x1fff);
						chip2 = memory[block2];
						
					break;
					
					case ABSOLUTE_X_10_00:
						operand1 = chip[offset++];
						operand2 = chip[offset++];
						access = ((operand2 << 8) | operand1) + x;
						block2 = (access & 0xe000) >> 13;
						offset2 = (access & 0x1fff);
						chip2 = memory[block2];
					break;			
				
				}//end switch.
				
				
				if (cc == 10)
				{
					switch(aaa)
					{
						case ASL:
						
							if (accumulatorFlag == 1)
							{
								a = a << 1;
								if ((a & 0x100) == 1)
								{
									c = 1;
								}
								else
								{
									c = 0;
								}//end else.
								tmp = a = a & 0xff;
								
							}
							else
							{
								tmp = chip2[offset2];
								tmp = tmp << 1;
								if ((tmp & 0x100) == 1)
								{
									c = 1;
								}
								else
								{
									c = 0;
								}//end else.
								tmp = chip2[offset2] = (tmp & 0xff);
							}//end else.
						
							ck_n = ck_c = ck_z = 1;
						
						break;
						
						case ROL:
							if (accumulatorFlag == 1)
							{
								a = a << 1;
								a = a | c;
								if ((a & 0x100) == 1)
								{
									c = 1;
								}
								else
								{
									c = 0;
								}//end else.
								tmp = a = a & 0xff;
								
							}
							else
							{
								tmp = chip2[offset2];
								tmp = tmp << 1;
								tmp = tmp | c;
								if ((tmp & 0x100) == 1)
								{
									c = 1;
								}
								else
								{
									c = 0;
								}//end else.
								tmp = chip2[offset2] = (tmp & 0xff);
							}//end else.
						
							ck_n = ck_c = ck_z = 1;
						
						break;
						
						case LSR:
						
							if (accumulatorFlag == 1)
							{
								c = a & 0x01;
								a = a >> 1;
								tmp = a = a & 0xff;
								
							}
							else
							{
								tmp = chip2[offset2];
								c = tmp & 0x01;
								tmp = tmp >> 1;
								tmp = chip2[offset2] = (tmp & 0xff);
							}//end else.
						
							ck_c = ck_z = 1;
							
						break;
						
						case ROR:
						
							if (accumulatorFlag == 1)
							{
								tmp2 = c;
								c = a & 0x01;
								a = a >> 1;
								tmp2 = tmp2 << 7;
								a = a | tmp2;
								tmp = a = a & 0xff;
								
							}
							else
							{
								tmp2 = c;
								tmp = chip2[offset2];
								c = tmp & 0x01;
								tmp = tmp >> 1;
								tmp2 = tmp2 << 7;
								tmp = tmp | tmp2;
								tmp = chip2[offset2] = (tmp & 0xff);
							}//end else.
						
							ck_n = ck_c = ck_z = 1;
							
						break;
						
						case STX:
							chip2[offset2] = x;
						break;
						
						case LDX:
							tmp = x = chip2[offset2];
							ck_n = ck_z = 1;
						break;
						
						case DEC:
							tmp = chip2[offset2];
							tmp = tmp - 1;
							tmp = chip2[offset2] = tmp;
							
							ck_n = ck_z = 1;
						break;
						
						case INC:
							tmp = chip2[offset2];
							tmp = tmp + 1;
							tmp = chip2[offset2] = tmp;
							
							ck_n = ck_z = 1;
						break;	
					}//end switch;
					
					
					switch(ir)
					{
						case TXA:
							a = x;
						break;
						
						case TXS:
							sp = x | 0x100;
						break;
						
						case TAX:
							x = a;
						break;
						
						case TSX:
							x = sp & 0xff;
						break;
						
						case DEX:
							x--;
						break;
						
						case NOP:
						
						break;
					}//end switch.
					
				}
				else //cc == 00.
				{
					
					switch(aaa)
					{
						case BIT:
							a = a & chip2[offset2];
							
							tmp = a >> 6;
							v = tmp & 0x01;
							
							tmp = tmp >> 1;
							n = tmp & 0x01;
							
							tmp = a;
							
							ck_z = 1;
						
						break;
						
						case JMP_ABS:
	
							pc = access;
						
						break;
						
						case JMP_IND_ABS:
						
							operand1 = chip2[offset2];
							operand2 = chip2[offset2+1];
							
							pc = ((operand2 << 8) | operand1);
							
							
						break;
						
						case STY:
						
							chip2[offset2] = y;
							
						break;
						
						case LDY:
						
							tmp = y = chip2[offset2];
							ck_n = ck_z = 1;
							
						break;
						
						case CPY:
						
							tmp = y - chip2[offset2];
							ck_n = ck_c = ck_z = 1;
						
						break;
						
						case CPX:
						
							tmp = x - chip2[offset2];
							ck_n = ck_c = ck_z = 1;
						
						break;
						
					}//end switch;
					
					
					switch(ir)
					{
						case BRK:
							run = 0;
						
						break;
						
						case JSR:
						
						break;
						
						case RTI:
						
						break;
						
						case RTS:
						
						break;
						
						case PHP:
						
						break;
						
						case PLP:
							//tmp =
							c = tmp & 0x01;
							tmp >>= 1;
							z = tmp & 0x01;
							tmp >>= 1;
							i = tmp & 0x01;
							tmp >>= 1;
							d = tmp & 0x01;
							tmp >>= 1;
							b = tmp & 0x01;
							tmp >>= 2;
							v = tmp & 0x01;
							tmp >>= 1;
							n = tmp & 0x01;
						
						break;
						
						case PHA:
						
						break;
						
						case PLA:
						
						break;
						
						case DEY:
							y--;
						break;
						
						case TAY:
							y = a;
						break;
						
						case INY:
							y++;
						break;
						
						case CLC:
							c = 0;
						break;
						
						case SEC:
							c = 1;
						break;
						
						case CLI:
						 	i = 0;
						break;
						
						case SEI:
							i = 1;
						break;
						
						case TYA:
							a = y;
						break;
						
						case CLV:
							v = 0;
						break;
						
						case CLD:
							d = 0;
						break;
						
						case SED:
							d = 1;
						break;
						
						
					}//end switch.
					
					
					
					
					
				}//end else //cc == 00.
				
	        }//end else.
	
			
			//Check to see if flags need to be set.
			if (ck_n == 1)
			{
				if ((tmp & 0x80) != 0)
				{
					n = 1;
				}
				else
				{
					n = 0;
				}
			}
			else if (ck_c == 1)
			{
				if ((tmp & 0x100) != 0)
				{
					c = 1;
				}
				else
				{
					c = 0;
				}
			}
			else if (ck_z == 1)
			{
				if ((tmp & 0xff) == 0)
				{
					z = 1;
				}
				else
				{
					z = 0;
				}
			}//end else/if.
	
			
			ck_n = ck_v = ck_c = ck_z = 0;
			
			pc = pc + (offset - oldOffset);
		
		}//end while.
		
    } //end method.

    public static void main(String[] args)
    {

        EMU6502 emu = new EMU6502();
		emu.rom = new {-87,5,-115,6,-32,0,6,7};
        emu.run();
		

		
    }
} //end class.

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
