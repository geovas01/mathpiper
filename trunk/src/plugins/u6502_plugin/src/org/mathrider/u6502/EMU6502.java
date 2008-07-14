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
From http://axis.llx.com/~nparker/a2/opcodes.html.

Most instructions that explicitly reference memory locations have bit patterns of the form aaabbbcc. The aaa and cc bits determine the opcode, and the bbb bits determine the addressing mode.

{{{cc = 01
Instructions with cc = 01 are the most regular, and are therefore considered first. The aaa bits determine the opcode as follows:
aaa	opcode
000	ORA
001	AND
010	EOR
011	ADC
100	STA
101	LDA
110	CMP
111	SBC

And the addressing mode (bbb) bits:
bbb	addressing mode
000	(zero page,X)
001	zero page
010	#immediate
011	absolute
100	(zero page),Y
101	zero page,X
110	absolute,Y
111	absolute,X

Putting it all together:
  	ORA 	AND 	EOR 	ADC 	STA 	LDA 	CMP 	SBC
(zp,X) 	01 	21 	41 	61 	81 	A1 	C1 	E1
zp 	05 	25 	45 	65 	85 	A5 	C5 	E5
# 	09 	29 	49 	69 	  	A9 	C9 	E9
abs 	0D 	2D 	4D 	6D 	8D 	AD 	CD 	ED
(zp),Y 	11 	31 	51 	71 	91 	B1 	D1 	F1
zp,X 	15 	35 	55 	75 	95 	B5 	D5 	F5
abs,Y 	19 	39 	59 	79 	99 	B9 	D9 	F9
abs,X 	1D 	3D 	5D 	7D 	9D 	BD 	DD 	FD

The only irregularity is the absence of the nonsensical immediate STA instruction.
}}}

{{{ cc = 10
Next we consider the cc = 10 instructions. These have a completely different set of opcodes:
aaa	opcode
000	ASL
001	ROL                                                                                                          
010	LSR
011	ROR
100	STX
101	LDX
110	DEC
111	INC

The addressing modes are similar to the 01 case, but not quite the same:
bbb	addressing mode
000	#immediate
001	zero page
010	accumulator
011	absolute
101	zero page,Y (Note: was zero page,X)
111	absolute,X

Note that bbb = 100 and 110 are missing. Also, with STX and LDX, "zero page,X" addressing becomes "zero page,Y", and with LDX, "absolute,X" becomes "absolute,Y".

These fit together like this:
  	ASL 	ROL 	LSR 	ROR 	STX 	LDX 	DEC 	INC
# 	  	  	  	  	  	A2 	  	 
zp 	06 	26 	46 	66 	86 	A6 	C6 	E6
A 	0A 	2A 	4A 	6A 	  	  	  	 
abs 	0E 	2E 	4E 	6E 	8E 	AE 	CE 	EE
zp,X/zp,Y 	16 	36 	56 	76 	96 	B6 	D6 	F6
abs,X/abs,Y 	1E 	3E 	5E 	7E 	  	BE 	DE 	FE

Most of the gaps in this table are easy to understand. Immediate mode makes no sense for any instruction other than LDX, and accumulator mode for DEC and INC didn't appear until the 65C02. The slots that "STX A" and "LDX A" would occupy are taken by TXA and TAX respectively, which is exactly what one would expect. The only inexplicable gap is the absence of a "STX abs,Y" instruction.
}}}



{{{ cc = 00
Next, the cc = 00 instructions. Again, the opcodes are different:
aaa	opcode
001	BIT
010	JMP
011	JMP (abs)
100	STY
101	LDY
110	CPY
111	CPX

It's debatable whether the JMP instructions belong in this list...I've included them because they do seem to fit, provided one considers the indirect JMP a separate opcode rather than a different addressing mode of the absolute JMP.

The addressing modes are the same as the 10 case, except that accumulator mode is missing.
bbb	addressing mode
000	#immediate
001	zero page
011	absolute
101	zero page,X
111	absolute,X

And here's how they fit together:
  	BIT 	JMP 	JMP() 	STY 	LDY 	CPY 	CPX
# 	  	  	  	  	A0 	C0 	E0
zp 	24 	  	  	84 	A4 	C4 	E4
abs 	2C 	4C 	6C 	8C 	AC 	CC 	EC
zp,X 	  	  	  	94 	B4 	  	 
abs,X 	  	  	  	  	BC 	  	 

Some of the gaps in this table are understandable (e.g. the lack of an immediate mode for JMP, JMP(), and STY), but others are not (e.g. the absence of "zp,X" for CPY and CPX, and the absence of "abs,X" for STY, CPY, and CPX). Note that if accumulator mode (bbb = 010) were available, "LDY A" would be A8, which falls in the slot occupied by TAY, but the pattern breaks down elsewhere--TYA is 98, rather than 88, which we would expect it to be if it corresponded to the nonexistant "STY A".


No instructions have the form aaabbb11.


The conditional branch instructions all have the form xxy10000. The flag indicated by xx is compared with y, and the branch is taken if they are equal.
xx	flag
00	negative
01	overflow
10	carry
11	zero

This gives the following branches:
BPL	BMI	BVC	BVS	BCC	BCS BNE	BEQ
10	30	50	70	90	B0 	D0	F0


The remaining instructions are probably best considered simply by listing them. Here are the interrupt and subroutine instructions:
BRK	JSR abs	RTI	RTS
00	20	40	60

(JSR is the only absolute-addressing instruction that doesn't fit the aaabbbcc pattern.)

Other single-byte instructions:
PHP 	PLP 	PHA 	PLA 	DEY 	TAY 	INY 	INX
08 	28 	48 	68 	88 	A8 	C8 	E8
CLC 	SEC 	CLI 	SEI 	TYA 	CLV 	CLD 	SED
18 	38 	58 	78 	98 	B8 	D8 	F8
TXA 	TXS 	TAX 	TSX 	DEX 	NOP
8A 	9A 	AA 	BA 	CA 	EA

}}}

*/

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
	static final int ZERO_PAGE_X_01 = 0;
	static final int ZERO_PAGE_01 = 1;
	static final int IMMEDIATE_01 = 2;
	static final int ABSOLUTE_01 = 3;
	static final int INDIRECT_Y_01 = 4;
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
	static final int ZERO_PAGE_Y_10_00 = 5;
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
    public int sp = 0x01ff;
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
	public int[] chip3;
	
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
	public boolean skipCalculatePCFlag;
	public boolean singleByteInstructionFlag;
	
	

	
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
			
			chip = (int[]) memory[block];
		//System.out.println("offset:" + offset);
			ir = chip[offset++];
		//System.out.printf("ir: %2x\n\n",ir);

			
		//System.out.printf("ir %x\n", ir);
			
	        cc = ir & 0x03;
	        bbb = (ir & 0x1c) >> 2;
	        aaa = (ir & 0xe0) >> 5;
			
	        if (cc == 01)
	        {
				
				switch(bbb)
				{
					case ZERO_PAGE_X_01:
					break;
					
					case ZERO_PAGE_01:
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
						chip2 = (int[]) memory[block2];
					break;
						
					case INDIRECT_Y_01:
						operand1 = chip[offset++];
						chip3 = (int[]) memory[0];
						access = ((chip3[operand1 + 1] << 8) | chip3[operand1]) + y;
						block2 = (access & 0xe000) >> 13;
						offset2 = (access & 0x1fff);
						chip2 = (int[]) memory[block2];
					break;
					
					case INDIRECT_X_01:       
						operand1 = chip[offset++];
						chip3 = (int[]) memory[0];
						access = ((chip3[operand1 + 1] << 8) + chip3[operand1] + x);
						block2 = (access & 0xe000) >> 13;
						offset2 = (access & 0x1fff);
						chip2 = (int[]) memory[block2];
					break;
					
					case ABSOLUTE_Y_01:
						operand1 = chip[offset++];
						operand2 = chip[offset++];
						access = ((operand2 << 8) | operand1) + y;
						block2 = (access & 0xe000) >> 13;
						offset2 = (access & 0x1fff);
						chip2 = (int[]) memory[block2];
					break;
					
					case ABSOLUTE_X_01:
						operand1 = chip[offset++];
						operand2 = chip[offset++];
						access = ((operand2 << 8) | operand1) + x;
						block2 = (access & 0xe000) >> 13;
						offset2 = (access & 0x1fff);
						chip2 = (int[]) memory[block2];
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
	        else //cc == 10 or 00.  *******************
	        {
				singleByteInstructionFlag = true;
					switch(ir)
					{
						case BRK:
							run = 0;
							skipCalculatePCFlag = true;
						break;
						
						case JSR: //Note: implement.
						
						break;
						
						case RTI: //Note: implement.
						
						break;
						
						case RTS: //Note: implement.
						
						break;
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

						case PHP:
							tmp = 0;
							
							tmp |= n;
							tmp <<= 1;
							tmp |= v;
							tmp <<= 2;
							tmp |= b;
							tmp <<= 1;
							tmp |= d;
							tmp <<= 1;
							tmp |= i;
							tmp <<= 1;
							tmp |= z;
							tmp <<= 1;
							tmp |= c;
							
							chip = (int[]) memory[0];
							chip[sp] = tmp;
							sp--; //Note: Perhaps place error checking here.
						break;
						
						case PLP:
							chip = (int[]) memory[0];
							sp++; //Note: Perhaps place error checking here.
							tmp = chip[sp];
							
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
							chip = (int[]) memory[0];
							chip[sp] = a;
							sp--; //Note: Perhaps place error checking here.
						break;
						
						case PLA:
							chip = (int[]) memory[0];
							sp++; //Note: Perhaps place error checking here.
							a = chip[sp];
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
						
						case INX:
							x++;
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
						
						default:
							singleByteInstructionFlag = false;
						break;
					}//end switch.

				if(!singleByteInstructionFlag)
				{
			
					switch(bbb)
					{
						case IMMEDIATE_10_00:
							chip2 = chip;
							offset2 = offset++;
						break;
						
						case INDIRECT_10_00:
							operand1 = chip[offset++];
							chip3 = (int[]) memory[0];
							access = ((chip3[operand1 + 1] << 8) + chip3[operand1]);
							block2 = (access & 0xe000) >> 13;
							offset2 = (access & 0x1fff);
							chip2 = (int[]) memory[block2];
						break;
						
						case ACCUMULATOR_10:
							accumulatorFlag = 1;
						break;
						
						case ZERO_PAGE_Y_10_00:
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
							chip2 = (int[]) memory[block2];
							
						break;
						
						case ABSOLUTE_X_10_00:
							operand1 = chip[offset++];
							operand2 = chip[offset++];
							access = ((operand2 << 8) | operand1) + x;
							block2 = (access & 0xe000) >> 13;
							offset2 = (access & 0x1fff);
							chip2 = (int[]) memory[block2];
						break;			
					
					}//end switch.
				
				             
					if (cc == 2) //cc == 10.
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
						
					}//end if cc = 2.
				
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
							skipCalculatePCFlag = true;
						
						break;
						
						case JMP_IND_ABS:
						
							operand1 = chip2[offset2];
							operand2 = chip2[offset2+1];
							
							pc = ((operand2 << 8) | operand1);
							skipCalculatePCFlag = true;
							
							
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
					
					
				}

									}


				
	        }//end else.//cc == 10 or 00.  *******************
	
			
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
			
			if(!skipCalculatePCFlag) 
			{
				pc = pc + (offset - oldOffset);
			}
			else
			{
				skipCalculatePCFlag = false;
			}
		
		}//end while.
		
    } //end method.

    public static void main(String[] args)
    {

        EMU6502 emu = new EMU6502();
		emu.rom = new int[5];

      	emu.rom[0] = 0xA0;
      	emu.rom[1] = 0x01;
      	emu.rom[2] = 0xB9;
      	emu.rom[3] = 0x06;
	 
        emu.run();
		

		
    }
} //end class.

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
