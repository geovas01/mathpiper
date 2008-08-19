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

public class EMU6502 implements Runnable
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
	public int[] randomMemory;

	public int resetVector;
	public int nonMaskableInterruptVector;

	public Object[] memory;
	public IOChip[] ioChips;

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
	public boolean singleByteInstructionFlag;
	




	public EMU6502()
	{
		super();

		ram = new int[8192];
		randomMemory = new int[8192];
		//rom = new int[1000];
		memory = new Object[8];
		ioChips = new IOChip[16];


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



		//Place chips into memory.
		memory[0] = ram;
		memory[1] = randomMemory;
		memory[2] = randomMemory;
		memory[3] = randomMemory;
		memory[4] = randomMemory;
		ioChips[0] = new EMU6551();
		ioChips[1] = new EMURandomIOChip();
		ioChips[2] = new EMURandomIOChip();
		ioChips[3] = new EMURandomIOChip();
		ioChips[4] = new EMURandomIOChip();
		ioChips[5] = new EMURandomIOChip();
		ioChips[6] = new EMURandomIOChip();
		ioChips[7] = new EMURandomIOChip();
		ioChips[8] = new EMURandomIOChip();
		ioChips[9] = new EMURandomIOChip();
		ioChips[10] = new EMURandomIOChip();
		ioChips[11] = new EMURandomIOChip();
		ioChips[12] = new EMURandomIOChip();
		ioChips[13] = new EMURandomIOChip();
		ioChips[14] = new EMURandomIOChip();
		ioChips[15] = new EMURandomIOChip();
		
		memory[5] = new int[8192];
		memory[6] = randomMemory;
		memory[7] = rom;

		resetVector = ((((int[])memory[7])[0x1ffc]) << 8) | (((int[])memory[7])[0x1ffd]);
		nonMaskableInterruptVector = ((((int[])memory[7])[0x1ffe]) << 8) | (((int[])memory[7])[0x1fff]);

		sp = 0x01ff;
		pc = resetVector;
		offset = (pc & 0x1fff);
		oldOffset = offset;

		while(run == 1)
		{
			block = (pc & 0xe000) >> 13;

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
					if(block2 == 5)
					{
						int bank = offset2 >> 9;
						IOChip ioChip = ioChips[bank];
						ioChip.write(offset2,a);
					}
					else
					{
						chip2[offset2] = a;
					}
					break;

				case LDA:
					if(block2 == 5)
					{
						int bank = offset2 >> 9;
						IOChip ioChip = ioChips[bank];
						tmp = a = ioChip.read(offset2);
					}
					else
					{
						tmp = a = chip2[offset2];
					}

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
					//run = 0;

					//Note: not finished yet.
					//ALU.B = true; ram.write16( stack , pc );
					b = 1;

					//Push pc.
					chip = (int[]) memory[0];
					pc = pc + ((pc & 0x1fff) + offset);
					chip[sp] = (pc >> 8);
					sp--;
					chip[sp] = (pc) & 0xff;
					sp--;

					//stack-=2; ALU.B = true; ram.write( stack--, (byte) alu.getStatus() );
					//Push status.
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


					//pc = RAM.brkirqAddress;
					pc = nonMaskableInterruptVector;
					oldOffset = (pc & 0x1fff);

					continue;

				case RTI: //Note: implement.

					break;

				case RTS:
					chip = (int[]) memory[0];
					sp++; //Note: Perhaps place error checking here.
					pc = (chip[sp]);
					sp++; //Note: Perhaps place error checking here.
					pc = (pc | (chip[sp] << 8)) + 1;
					oldOffset = (pc & 0x1fff);
					continue;
				case TXA:
					tmp = a = x;
					ck_n = ck_z = 1;
					break;

				case TXS:
					sp = x | 0x100;
					break;

				case TAX:
					tmp = x = a;
					ck_n = ck_z = 1;
					break;

				case TSX:
					tmp = x = sp & 0xff;
					ck_n = ck_z = 1;
					break;

				case DEX:
					tmp = x = (x - 1) & 0xff;
					ck_n = ck_z = 1;
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
					tmp = a = chip[sp];
					ck_n = ck_z = 1;
					break;

				case DEY:
					tmp = y = (y - 1) & 0xff;
					ck_n = ck_z = 1;
					break;

				case TAY:
					tmp = y = a;
					ck_n = ck_z = 1;
					break;

				case INY:
					tmp = y = (y + 1) & 0xff;
					ck_n = ck_z = 1;
					break;

				case INX:
					tmp = x = (x + 1) & 0xff;
					ck_n = ck_z = 1;
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
					tmp = a = y;
					ck_n = ck_z = 1;
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
							if((operand1 & 0x80) > 0)
							{
								operand1 = operand1 | 0xffffff00;
							}

							offset = offset + operand1;

						}

						//No need to perform the rest of the main loop if a branch was encountered.
						pc = pc + (offset - oldOffset);//Note: check.
						oldOffset = (pc & 0x1fff);
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

							ck_n = ck_z = 1;

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

							ck_n = ck_z = 1;

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

							ck_n = ck_z = 1;

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

							ck_n = ck_z = 1;

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
							tmp = (tmp - 1) & 0xff;
							tmp = chip2[offset2] = tmp;

							ck_n = ck_z = 1;
							break;

						case INC:
							tmp = chip2[offset2];
							tmp = (tmp + 1) & 0xff;
							tmp = chip2[offset2] = tmp;

							ck_n = ck_z = 1;
							break;
						}//end switch;

					}//end if cc = 2.

					else //cc == 00.
					{
						if(ir == JSR) //JSR
						{
							//Code to get around JSR not following aaabbbcc pattern.
							operand1 = chip[offset -1];
							operand2 = chip[offset++];
							access = (operand2 << 8) | operand1;
							//block2 = (access & 0xe000) >> 13;
							//offset2 = (access & 0x1fff);
							//chip2 = (int[]) memory[block2];

							pc = pc + 2;
							chip = (int[]) memory[0];
							chip[sp] = (pc >> 8);
							sp--;
							chip[sp] = pc & 0xff;
							sp--;
							pc = access;
							oldOffset = (pc & 0x1fff);
							continue;
						}
						else
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

								ck_n = ck_z = ck_v = 1;

								break;

							case JMP_ABS:
								pc = access;
								oldOffset = (pc & 0x1fff);
								continue;

							case JMP_IND_ABS:

								operand1 = chip2[offset2];
								operand2 = chip2[offset2+1];

								pc = ((operand2 << 8) | operand1);
								oldOffset = (pc & 0x1fff);
								continue;

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

			if (ck_c == 1)
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

			if (ck_z == 1)
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
			oldOffset = offset;
			//pc = pc + ((pc & 0x1fff) + offset);


		}//end while.

	} //end method.

	public static void main(String[] args)
	{

		EMU6502 emu = new EMU6502();
		emu.rom = new int[0x2000];

		int[] code = {76, 27, 224, 76, 159, 243, 76, 97, 243, 76, 132, 243, 76, 70, 243, 76, 150, 242, 76, 242, 242, 76, 46, 234, 76, 112, 224, 162, 255, 154, 216, 32, 38, 243, 76, 37, 224, 162, 0, 160, 244, 32, 70, 243, 0, 201, 2, 208, 19, 41, 240, 74, 74, 74, 24, 105, 192, 141, 9, 0, 169, 0, 141, 8, 0, 108, 8, 0, 76, 71, 224, 160, 0, 140, 42, 2, 162, 100, 200, 208, 253, 202, 208, 250, 238, 42, 2, 173, 42, 2, 76, 76, 224, 32, 105, 224, 32, 219, 224, 32, 51, 225, 76, 93, 224, 162, 81, 160, 244, 32, 70, 243, 160, 0, 140, 68, 2, 140, 179, 2, 140, 69, 2, 140, 89, 2, 140, 109, 2, 160, 0, 169, 0, 153, 139, 2, 200, 192, 39, 208, 248, 160, 0, 32, 132, 243, 201, 0, 240, 249, 201, 8, 208, 3, 76, 161, 224, 201, 127, 208, 21, 136, 48, 13, 169, 32, 32, 159, 243, 169, 8, 32, 159, 243, 76, 143, 224, 160, 0, 76, 143, 224, 201, 10, 240, 13, 153, 139, 2, 200, 192, 39, 208, 205, 160, 39, 76, 143, 224, 136, 185, 139, 2, 201, 13, 240, 1, 200, 169, 0, 153, 139, 2, 96, 160, 255, 76, 248, 224, 160, 0, 185, 139, 2, 240, 80, 201, 63, 48, 75, 201, 90, 16, 3, 56, 176, 8, 201, 97, 48, 64, 201, 122, 16, 60, 141, 179, 2, 200, 192, 20, 240, 52, 185, 139, 2, 201, 0, 240, 46, 201, 32, 240, 240, 174, 68, 2, 138, 24, 105, 20, 141, 68, 2, 185, 139, 2, 201, 32, 240, 16, 201, 0, 240, 12, 157, 69, 2, 232, 200, 192, 40, 240, 11, 76, 18, 225, 169, 0, 157, 69, 2, 76, 248, 224, 96, 96, 56, 173, 179, 2, 32, 161, 242, 201, 108, 208, 6, 32, 56, 233, 76, 198, 225, 201, 100, 208, 6, 32, 34, 231, 76, 198, 225, 201, 114, 208, 6, 32, 219, 234, 76, 198, 225, 201, 103, 208, 6, 32, 199, 232, 76, 198, 225, 201, 101, 208, 6, 32, 32, 232, 76, 198, 225, 201, 102, 208, 6, 32, 134, 232, 76, 198, 225, 201, 117, 208, 6, 32, 100, 239, 76, 198, 225, 201, 116, 208, 6, 32, 51, 237, 76, 198, 225, 201, 98, 208, 6, 32, 186, 229, 76, 198, 225, 201, 115, 208, 6, 32, 162, 236, 76, 198, 225, 201, 109, 208, 6, 32, 111, 234, 76, 198, 225, 201, 97, 208, 6, 32, 49, 226, 76, 198, 225, 201, 104, 208, 6, 32, 48, 233, 76, 198, 225, 201, 63, 208, 6, 32, 48, 233, 76, 198, 225, 176, 9, 162, 176, 160, 245, 32, 70, 243, 24, 96, 56, 96, 120, 141, 42, 2, 104, 72, 41, 16, 208, 6, 173, 42, 2, 108, 2, 0, 108, 4, 0, 104, 141, 49, 2, 173, 42, 2, 141, 45, 2, 142, 46, 2, 140, 47, 2, 186, 142, 48, 2, 104, 56, 233, 1, 141, 43, 2, 104, 233, 0, 141, 44, 2, 160, 0, 140, 69, 2, 32, 219, 234, 173, 31, 2, 201, 1, 208, 3, 76, 223, 238, 160, 3, 162, 6, 185, 21, 2, 201, 1, 208, 5, 185, 25, 2, 129, 18, 136, 202, 202, 16, 239, 76, 93, 224, 162, 0, 32, 119, 242, 176, 3, 76, 39, 227, 142, 8, 0, 140, 9, 0, 162, 85, 160, 244, 32, 70, 243, 32, 185, 242, 32, 112, 224, 32, 214, 224, 173, 69, 2, 201, 0, 240, 4, 201, 32, 208, 3, 76, 41, 227, 160, 0, 185, 69, 2, 32, 173, 242, 153, 69, 2, 200, 192, 3, 208, 242, 32, 43, 227, 176, 40, 32, 185, 242, 169, 32, 32, 159, 243, 162, 69, 160, 2, 32, 70, 243, 169, 32, 32, 159, 243, 162, 89, 160, 2, 32, 70, 243, 169, 32, 32, 159, 243, 169, 63, 32, 159, 243, 76, 65, 226, 32, 99, 227, 176, 3, 76, 116, 226, 32, 88, 228, 176, 3, 76, 116, 226, 32, 120, 228, 176, 3, 76, 116, 226, 32, 185, 242, 160, 0, 173, 0, 2, 145, 8, 32, 242, 242, 32, 110, 242, 169, 32, 32, 159, 243, 173, 3, 2, 240, 40, 173, 1, 2, 145, 8, 32, 242, 242, 32, 110, 242, 169, 32, 32, 159, 243, 173, 4, 2, 240, 30, 173, 2, 2, 145, 8, 32, 242, 242, 32, 110, 242, 169, 32, 32, 159, 243, 76, 12, 227, 169, 32, 32, 159, 243, 32, 159, 243, 32, 159, 243, 169, 32, 32, 159, 243, 32, 159, 243, 32, 159, 243, 169, 32, 32, 159, 243, 162, 69, 160, 2, 32, 70, 243, 169, 32, 32, 159, 243, 162, 89, 160, 2, 32, 70, 243, 76, 65, 226, 24, 96, 56, 96, 162, 182, 142, 10, 0, 162, 247, 142, 11, 0, 160, 0, 177, 10, 217, 69, 2, 240, 25, 173, 10, 0, 24, 105, 10, 141, 10, 0, 144, 3, 238, 11, 0, 160, 4, 177, 10, 201, 90, 240, 11, 76, 53, 227, 200, 192, 3, 240, 5, 76, 55, 227, 24, 96, 56, 96, 160, 0, 185, 89, 2, 201, 0, 208, 8, 169, 80, 141, 5, 2, 76, 86, 228, 201, 35, 208, 8, 169, 77, 141, 5, 2, 76, 86, 228, 201, 65, 240, 7, 201, 97, 240, 3, 76, 162, 227, 200, 185, 89, 2, 201, 0, 240, 7, 201, 32, 240, 3, 76, 162, 227, 169, 67, 141, 5, 2, 76, 86, 228, 160, 6, 177, 10, 201, 76, 208, 6, 141, 5, 2, 76, 86, 228, 160, 0, 185, 89, 2, 201, 40, 208, 3, 76, 252, 227, 160, 0, 185, 89, 2, 201, 44, 240, 8, 201, 0, 240, 43, 200, 76, 190, 227, 200, 185, 89, 2, 201, 88, 240, 15, 201, 120, 240, 11, 201, 89, 240, 15, 201, 121, 240, 11, 76, 84, 228, 169, 88, 141, 5, 2, 76, 86, 228, 169, 89, 141, 5, 2, 76, 86, 228, 169, 83, 141, 5, 2, 76, 86, 228, 200, 185, 89, 2, 201, 44, 240, 11, 201, 41, 240, 30, 192, 11, 208, 240, 76, 84, 228, 200, 185, 89, 2, 201, 88, 240, 7, 201, 120, 240, 3, 76, 84, 228, 169, 82, 141, 5, 2, 76, 86, 228, 200, 185, 89, 2, 201, 0, 240, 22, 201, 44, 240, 3, 76, 84, 228, 200, 185, 89, 2, 201, 89, 240, 15, 201, 121, 240, 11, 76, 84, 228, 169, 68, 141, 5, 2, 76, 86, 228, 169, 73, 141, 5, 2, 76, 86, 228, 24, 96, 56, 96, 160, 6, 177, 10, 205, 5, 2, 240, 21, 173, 10, 0, 24, 105, 10, 141, 10, 0, 144, 3, 238, 11, 0, 32, 53, 227, 176, 228, 24, 96, 56, 96, 169, 0, 141, 3, 2, 141, 4, 2, 160, 7, 177, 10, 141, 0, 2, 173, 5, 2, 201, 80, 240, 39, 201, 67, 240, 38, 201, 77, 240, 37, 201, 83, 240, 60, 201, 88, 240, 56, 201, 89, 240, 52, 201, 73, 240, 88, 201, 82, 240, 84, 201, 68, 240, 80, 201, 76, 240, 36, 76, 145, 229, 76, 147, 229, 76, 147, 229, 32, 149, 229, 176, 3, 76, 145, 229, 32, 208, 242, 176, 3, 76, 145, 229, 141, 1, 2, 169, 1, 141, 3, 2, 76, 147, 229, 32, 149, 229, 176, 3, 76, 145, 229, 32, 119, 242, 176, 3, 76, 145, 229, 173, 5, 2, 201, 76, 240, 72, 142, 1, 2, 140, 2, 2, 169, 1, 141, 3, 2, 141, 4, 2, 76, 147, 229, 32, 149, 229, 176, 3, 76, 145, 229, 32, 119, 242, 176, 3, 76, 145, 229, 173, 5, 2, 201, 68, 240, 15, 192, 0, 208, 120, 142, 1, 2, 169, 1, 141, 3, 2, 76, 147, 229, 142, 1, 2, 140, 2, 2, 169, 1, 141, 3, 2, 141, 4, 2, 76, 147, 229, 142, 17, 2, 140, 18, 2, 173, 8, 0, 141, 19, 2, 173, 9, 0, 141, 20, 2, 160, 8, 177, 10, 24, 109, 19, 2, 141, 19, 2, 144, 3, 238, 20, 2, 56, 173, 17, 2, 237, 19, 2, 141, 17, 2, 173, 18, 2, 237, 20, 2, 141, 18, 2, 173, 18, 2, 201, 255, 240, 14, 201, 0, 208, 28, 173, 17, 2, 201, 128, 16, 21, 76, 134, 229, 173, 17, 2, 201, 128, 48, 11, 141, 1, 2, 169, 1, 141, 3, 2, 76, 147, 229, 24, 96, 56, 96, 162, 20, 160, 0, 189, 69, 2, 217, 222, 243, 240, 23, 217, 239, 243, 240, 18, 200, 192, 16, 240, 3, 76, 156, 229, 232, 224, 31, 240, 3, 76, 151, 229, 24, 96, 56, 96, 173, 69, 2, 201, 43, 240, 11, 201, 45, 240, 10, 201, 63, 240, 9, 76, 218, 230, 76, 70, 230, 76, 2, 230, 162, 85, 160, 244, 32, 70, 243, 160, 255, 162, 255, 200, 232, 185, 18, 0, 141, 8, 0, 200, 185, 18, 0, 141, 9, 0, 189, 21, 2, 240, 3, 32, 185, 242, 192, 7, 208, 229, 162, 85, 160, 244, 32, 70, 243, 76, 218, 230, 173, 89, 2, 201, 0, 240, 10, 162, 20, 32, 119, 242, 176, 16, 76, 218, 230, 160, 3, 169, 0, 153, 21, 2, 136, 16, 248, 76, 218, 230, 142, 17, 2, 140, 18, 2, 160, 3, 162, 6, 32, 219, 230, 176, 15, 136, 202, 202, 16, 246, 162, 70, 160, 245, 32, 70, 243, 76, 218, 230, 169, 0, 153, 21, 2, 76, 218, 230, 173, 89, 2, 201, 0, 240, 7, 162, 20, 32, 119, 242, 176, 3, 76, 218, 230, 142, 17, 2, 140, 18, 2, 160, 3, 162, 6, 185, 21, 2, 201, 0, 240, 5, 32, 219, 230, 176, 231, 136, 202, 202, 16, 239, 160, 0, 185, 21, 2, 201, 0, 240, 15, 200, 192, 4, 208, 244, 162, 30, 160, 245, 32, 70, 243, 76, 218, 230, 140, 29, 2, 169, 1, 153, 21, 2, 192, 0, 208, 15, 173, 17, 2, 141, 18, 0, 173, 18, 2, 141, 19, 0, 76, 218, 230, 192, 1, 208, 15, 173, 17, 2, 141, 20, 0, 173, 18, 2, 141, 21, 0, 76, 218, 230, 192, 2, 208, 15, 173, 17, 2, 141, 22, 0, 173, 18, 2, 141, 23, 0, 76, 218, 230, 173, 17, 2, 141, 24, 0, 173, 18, 2, 141, 25, 0, 76, 218, 230, 96, 192, 0, 208, 11, 173, 18, 2, 205, 19, 0, 240, 44, 76, 32, 231, 192, 1, 208, 11, 173, 18, 2, 205, 21, 0, 240, 29, 76, 32, 231, 192, 2, 208, 11, 173, 18, 2, 205, 23, 0, 240, 14, 76, 32, 231, 173, 18, 2, 205, 25, 0, 240, 3, 76, 32, 231, 173, 17, 2, 221, 18, 0, 240, 3, 76, 32, 231, 56, 96, 24, 96, 162, 0, 189, 69, 2, 201, 0, 240, 58, 32, 208, 242, 176, 3, 76, 230, 231, 141, 9, 0, 232, 32, 208, 242, 176, 3, 76, 230, 231, 141, 8, 0, 162, 20, 189, 69, 2, 201, 0, 240, 26, 32, 208, 242, 176, 3, 76, 230, 231, 141, 67, 2, 232, 32, 208, 242, 176, 3, 76, 230, 231, 141, 66, 2, 76, 124, 231, 173, 9, 0, 141, 67, 2, 173, 8, 0, 141, 66, 2, 24, 105, 15, 141, 66, 2, 144, 3, 238, 67, 2, 169, 13, 32, 159, 243, 169, 10, 32, 159, 243, 162, 0, 160, 0, 32, 185, 242, 177, 8, 32, 10, 232, 32, 242, 242, 232, 224, 16, 208, 3, 32, 242, 231, 224, 8, 208, 18, 169, 32, 32, 159, 243, 169, 45, 32, 159, 243, 169, 32, 32, 159, 243, 76, 184, 231, 169, 32, 32, 159, 243, 173, 8, 0, 205, 66, 2, 208, 8, 173, 9, 0, 205, 67, 2, 240, 32, 238, 8, 0, 208, 3, 238, 9, 0, 224, 16, 208, 15, 169, 10, 32, 159, 243, 169, 13, 32, 159, 243, 32, 185, 242, 162, 0, 76, 141, 231, 24, 96, 238, 8, 0, 208, 3, 238, 9, 0, 56, 96, 169, 32, 32, 159, 243, 169, 32, 32, 159, 243, 162, 0, 189, 50, 2, 32, 159, 243, 232, 224, 16, 208, 245, 96, 72, 201, 32, 48, 10, 201, 127, 16, 6, 157, 50, 2, 76, 30, 232, 169, 46, 157, 50, 2, 104, 96, 173, 69, 2, 201, 0, 208, 3, 76, 78, 232, 173, 89, 2, 201, 0, 208, 3, 76, 78, 232, 162, 0, 32, 119, 242, 176, 3, 76, 78, 232, 142, 8, 0, 140, 9, 0, 162, 20, 32, 82, 232, 144, 3, 76, 80, 232, 24, 96, 56, 96, 160, 0, 140, 181, 2, 189, 69, 2, 240, 40, 32, 208, 242, 176, 3, 76, 132, 232, 145, 8, 238, 8, 0, 208, 3, 238, 9, 0, 238, 181, 2, 173, 181, 2, 201, 10, 240, 10, 232, 189, 69, 2, 240, 4, 232, 76, 87, 232, 56, 96, 24, 96, 162, 0, 32, 119, 242, 176, 3, 76, 195, 232, 142, 8, 0, 140, 9, 0, 162, 20, 32, 119, 242, 176, 3, 76, 195, 232, 142, 66, 2, 140, 67, 2, 162, 40, 32, 82, 232, 144, 22, 173, 67, 2, 205, 9, 0, 240, 3, 76, 166, 232, 173, 66, 2, 205, 8, 0, 240, 5, 76, 166, 232, 24, 96, 56, 96, 173, 69, 2, 201, 0, 240, 16, 162, 0, 32, 119, 242, 176, 3, 76, 44, 233, 142, 43, 2, 140, 44, 2, 174, 43, 2, 142, 17, 2, 172, 44, 2, 140, 18, 2, 160, 3, 162, 6, 185, 21, 2, 201, 0, 240, 5, 32, 219, 230, 176, 8, 136, 202, 202, 16, 239, 76, 12, 233, 162, 110, 160, 245, 32, 70, 243, 76, 46, 233, 160, 3, 162, 6, 185, 21, 2, 201, 1, 208, 9, 161, 18, 153, 25, 2, 169, 0, 129, 18, 136, 202, 202, 16, 235, 104, 104, 104, 104, 108, 43, 2, 24, 96, 56, 96, 162, 180, 160, 245, 32, 70, 243, 96, 162, 88, 160, 244, 32, 70, 243, 32, 132, 243, 201, 83, 208, 249, 32, 132, 243, 141, 135, 2, 201, 48, 208, 8, 32, 81, 234, 144, 37, 76, 119, 233, 201, 49, 208, 8, 32, 91, 234, 144, 25, 76, 119, 233, 201, 57, 208, 18, 32, 101, 234, 144, 13, 162, 161, 160, 244, 32, 70, 243, 76, 131, 233, 76, 63, 233, 162, 129, 160, 244, 32, 70, 243, 56, 96, 32, 132, 243, 56, 96, 169, 0, 141, 133, 2, 141, 134, 2, 32, 248, 233, 141, 132, 2, 32, 31, 234, 170, 202, 202, 202, 138, 141, 129, 2, 32, 248, 233, 141, 9, 0, 32, 31, 234, 32, 248, 233, 141, 8, 0, 32, 31, 234, 96, 173, 129, 2, 240, 11, 32, 248, 233, 32, 31, 234, 206, 129, 2, 208, 245, 96, 173, 129, 2, 240, 23, 160, 0, 32, 248, 233, 32, 31, 234, 145, 8, 238, 8, 0, 208, 3, 238, 9, 0, 206, 129, 2, 208, 235, 96, 173, 133, 2, 73, 255, 141, 131, 2, 32, 248, 233, 205, 131, 2, 240, 4, 24, 76, 247, 233, 56, 96, 152, 72, 32, 132, 243, 32, 46, 234, 144, 28, 10, 10, 10, 10, 41, 240, 141, 130, 2, 32, 132, 243, 32, 46, 234, 144, 11, 13, 130, 2, 141, 130, 2, 104, 168, 173, 130, 2, 96, 72, 24, 109, 133, 2, 141, 133, 2, 144, 3, 238, 134, 2, 104, 96, 142, 180, 2, 162, 0, 221, 222, 243, 240, 19, 221, 239, 243, 240, 14, 232, 224, 16, 240, 3, 76, 51, 234, 169, 0, 24, 76, 80, 234, 138, 56, 174, 180, 2, 96, 32, 136, 233, 32, 180, 233, 32, 226, 233, 96, 32, 136, 233, 32, 197, 233, 32, 226, 233, 96, 32, 136, 233, 32, 180, 233, 32, 226, 233, 96, 173, 69, 2, 201, 0, 240, 7, 162, 0, 32, 119, 242, 176, 3, 76, 218, 234, 142, 8, 0, 140, 9, 0, 162, 20, 32, 119, 242, 176, 3, 76, 218, 234, 142, 66, 2, 140, 67, 2, 238, 66, 2, 208, 3, 238, 67, 2, 162, 40, 32, 119, 242, 176, 3, 76, 218, 234, 142, 10, 0, 140, 11, 0, 160, 0, 177, 8, 145, 10, 238, 10, 0, 208, 3, 238, 11, 0, 238, 8, 0, 208, 3, 238, 9, 0, 173, 8, 0, 205, 66, 2, 240, 3, 76, 176, 234, 173, 9, 0, 205, 67, 2, 240, 3, 76, 176, 234, 96, 72, 138, 72, 152, 72, 8, 173, 69, 2, 201, 0, 240, 3, 76, 85, 235, 162, 195, 160, 244, 32, 70, 243, 160, 3, 32, 150, 242, 173, 44, 2, 32, 242, 242, 173, 43, 2, 32, 242, 242, 160, 9, 32, 150, 242, 173, 45, 2, 32, 242, 242, 160, 9, 32, 150, 242, 173, 46, 2, 32, 242, 242, 160, 8, 32, 150, 242, 173, 47, 2, 32, 242, 242, 160, 8, 32, 150, 242, 173, 48, 2, 32, 242, 242, 160, 7, 32, 150, 242, 173, 49, 2, 162, 8, 42, 176, 10, 72, 169, 48, 32, 159, 243, 104, 76, 77, 235, 72, 169, 49, 32, 159, 243, 104, 234, 202, 208, 232, 76, 154, 236, 234, 169, 13, 32, 159, 243, 169, 10, 32, 159, 243, 173, 69, 2, 32, 161, 242, 201, 112, 208, 60, 173, 44, 2, 32, 242, 242, 173, 43, 2, 32, 242, 242, 32, 142, 242, 32, 112, 224, 32, 214, 224, 173, 69, 2, 201, 0, 208, 3, 76, 154, 236, 162, 0, 32, 208, 242, 176, 3, 76, 154, 236, 168, 232, 32, 208, 242, 176, 3, 76, 154, 236, 141, 43, 2, 140, 44, 2, 76, 154, 236, 201, 97, 208, 41, 173, 45, 2, 32, 242, 242, 32, 142, 242, 32, 112, 224, 32, 214, 224, 173, 69, 2, 201, 0, 208, 3, 76, 154, 236, 162, 0, 32, 208, 242, 176, 3, 76, 154, 236, 141, 45, 2, 76, 154, 236, 201, 120, 208, 41, 173, 46, 2, 32, 242, 242, 32, 142, 242, 32, 112, 224, 32, 214, 224, 173, 69, 2, 201, 0, 208, 3, 76, 154, 236, 162, 0, 32, 208, 242, 176, 3, 76, 154, 236, 141, 46, 2, 76, 154, 236, 201, 121, 208, 41, 173, 47, 2, 32, 242, 242, 32, 142, 242, 32, 112, 224, 32, 214, 224, 173, 69, 2, 201, 0, 208, 3, 76, 154, 236, 162, 0, 32, 208, 242, 176, 3, 76, 154, 236, 141, 47, 2, 76, 154, 236, 201, 115, 240, 3, 76, 150, 236, 173, 70, 2, 32, 161, 242, 201, 112, 240, 7, 201, 114, 240, 44, 76, 150, 236, 173, 48, 2, 32, 242, 242, 32, 142, 242, 32, 112, 224, 32, 214, 224, 173, 69, 2, 201, 0, 208, 3, 76, 154, 236, 162, 0, 32, 208, 242, 176, 3, 76, 154, 236, 141, 48, 2, 76, 154, 236, 173, 49, 2, 32, 242, 242, 32, 142, 242, 32, 112, 224, 32, 214, 224, 173, 69, 2, 201, 0, 208, 3, 76, 154, 236, 162, 0, 32, 208, 242, 176, 3, 76, 154, 236, 141, 49, 2, 76, 154, 236, 24, 76, 155, 236, 56, 40, 104, 168, 104, 170, 104, 96, 173, 69, 2, 201, 0, 240, 7, 162, 0, 32, 119, 242, 176, 3, 76, 50, 237, 142, 10, 0, 140, 11, 0, 162, 20, 32, 119, 242, 176, 3, 76, 50, 237, 142, 66, 2, 140, 67, 2, 238, 66, 2, 208, 3, 238, 67, 2, 162, 40, 169, 6, 141, 8, 0, 169, 2, 141, 9, 0, 32, 82, 232, 144, 80, 173, 181, 2, 141, 16, 2, 173, 10, 0, 141, 8, 0, 173, 11, 0, 141, 9, 0, 162, 85, 160, 244, 32, 70, 243, 162, 0, 160, 0, 177, 8, 221, 6, 2, 240, 25, 32, 110, 242, 173, 8, 0, 205, 66, 2, 240, 3, 76, 251, 236, 173, 9, 0, 205, 67, 2, 240, 22, 76, 251, 236, 200, 232, 236, 16, 2, 240, 3, 76, 255, 236, 32, 185, 242, 32, 110, 242, 76, 251, 236, 96, 173, 69, 2, 201, 0, 240, 23, 162, 0, 32, 119, 242, 176, 3, 76, 48, 239, 142, 43, 2, 140, 44, 2, 173, 89, 2, 201, 0, 208, 8, 160, 1, 140, 36, 2, 76, 102, 237, 162, 20, 32, 208, 242, 176, 3, 76, 48, 239, 141, 36, 2, 104, 104, 104, 104, 172, 43, 2, 140, 8, 0, 172, 44, 2, 140, 9, 0, 160, 0, 177, 8, 141, 41, 2, 32, 52, 239, 176, 3, 76, 48, 239, 160, 8, 177, 10, 141, 32, 2, 160, 1, 173, 41, 2, 201, 96, 208, 40, 169, 2, 141, 35, 2, 104, 141, 16, 0, 104, 141, 17, 0, 72, 173, 16, 0, 72, 238, 16, 0, 208, 3, 238, 17, 0, 160, 0, 177, 16, 141, 34, 2, 169, 0, 145, 16, 76, 201, 238, 201, 76, 208, 30, 169, 2, 141, 35, 2, 177, 8, 141, 16, 0, 200, 177, 8, 141, 17, 0, 160, 0, 177, 16, 141, 34, 2, 169, 0, 145, 16, 76, 201, 238, 201, 108, 208, 43, 169, 2, 141, 35, 2, 177, 8, 141, 12, 0, 200, 177, 8, 141, 13, 0, 160, 0, 177, 12, 141, 16, 0, 200, 177, 12, 141, 17, 0, 160, 0, 177, 16, 141, 34, 2, 169, 0, 145, 16, 76, 201, 238, 201, 32, 208, 30, 169, 2, 141, 35, 2, 177, 8, 141, 16, 0, 200, 177, 8, 141, 17, 0, 160, 0, 177, 16, 141, 34, 2, 169, 0, 145, 16, 76, 201, 238, 160, 0, 177, 10, 201, 66, 208, 8, 160, 1, 177, 10, 201, 73, 208, 3, 76, 166, 238, 169, 1, 141, 35, 2, 24, 173, 8, 0, 109, 32, 2, 141, 16, 0, 173, 9, 0, 105, 0, 141, 17, 0, 160, 1, 177, 8, 141, 30, 2, 16, 32, 206, 30, 2, 173, 30, 2, 73, 255, 141, 30, 2, 56, 173, 16, 0, 237, 30, 2, 141, 16, 0, 173, 17, 0, 233, 0, 141, 17, 0, 76, 152, 238, 24, 173, 16, 0, 109, 30, 2, 141, 16, 0, 173, 17, 0, 105, 0, 141, 17, 0, 76, 152, 238, 160, 0, 177, 16, 141, 34, 2, 169, 0, 145, 16, 76, 171, 238, 169, 0, 141, 35, 2, 172, 32, 2, 177, 8, 141, 33, 2, 24, 173, 8, 0, 109, 32, 2, 141, 14, 0, 173, 9, 0, 105, 0, 141, 15, 0, 169, 0, 145, 8, 169, 1, 141, 31, 2, 173, 49, 2, 72, 173, 45, 2, 174, 46, 2, 172, 47, 2, 40, 108, 43, 2, 169, 0, 141, 31, 2, 160, 0, 173, 35, 2, 201, 0, 240, 17, 201, 1, 240, 8, 173, 34, 2, 145, 16, 76, 3, 239, 173, 34, 2, 145, 16, 173, 33, 2, 145, 14, 172, 43, 2, 140, 8, 0, 172, 44, 2, 140, 9, 0, 162, 85, 160, 244, 32, 70, 243, 173, 8, 0, 141, 66, 2, 173, 9, 0, 141, 67, 2, 32, 179, 239, 206, 36, 2, 240, 3, 76, 106, 237, 76, 93, 224, 24, 96, 56, 96, 162, 182, 142, 10, 0, 162, 247, 142, 11, 0, 160, 4, 177, 10, 201, 90, 240, 28, 160, 7, 177, 10, 205, 41, 2, 240, 17, 173, 10, 0, 24, 105, 10, 141, 10, 0, 144, 3, 238, 11, 0, 76, 62, 239, 56, 96, 24, 96, 173, 69, 2, 201, 0, 240, 23, 162, 0, 32, 119, 242, 176, 3, 76, 88, 242, 142, 8, 0, 140, 9, 0, 173, 89, 2, 201, 0, 208, 33, 173, 8, 0, 141, 66, 2, 173, 9, 0, 141, 67, 2, 24, 173, 8, 0, 105, 20, 141, 66, 2, 144, 7, 174, 67, 2, 232, 142, 67, 2, 76, 179, 239, 162, 20, 32, 119, 242, 176, 3, 76, 88, 242, 142, 66, 2, 140, 67, 2, 173, 67, 2, 205, 9, 0, 240, 5, 144, 14, 76, 206, 239, 173, 66, 2, 205, 8, 0, 144, 3, 76, 206, 239, 76, 90, 242, 160, 0, 177, 8, 141, 41, 2, 32, 52, 239, 176, 29, 162, 85, 160, 244, 32, 70, 243, 32, 185, 242, 173, 41, 2, 32, 242, 242, 162, 18, 160, 245, 32, 70, 243, 32, 110, 242, 76, 179, 239, 162, 85, 160, 244, 32, 70, 243, 32, 185, 242, 32, 110, 242, 160, 6, 177, 10, 201, 80, 208, 3, 76, 81, 240, 201, 77, 208, 3, 76, 120, 240, 201, 83, 208, 3, 76, 171, 240, 201, 88, 208, 3, 76, 171, 240, 201, 89, 208, 3, 76, 171, 240, 201, 82, 208, 3, 76, 200, 241, 201, 76, 208, 3, 76, 21, 241, 201, 67, 208, 3, 76, 98, 240, 201, 68, 208, 3, 76, 10, 242, 201, 73, 208, 3, 76, 134, 241, 76, 218, 239, 173, 41, 2, 32, 242, 242, 160, 8, 32, 150, 242, 32, 92, 242, 76, 179, 239, 173, 41, 2, 32, 242, 242, 160, 8, 32, 150, 242, 32, 92, 242, 169, 65, 32, 159, 243, 76, 179, 239, 160, 0, 173, 41, 2, 32, 242, 242, 169, 32, 32, 159, 243, 177, 8, 141, 39, 2, 32, 242, 242, 32, 110, 242, 160, 5, 32, 150, 242, 32, 92, 242, 169, 35, 32, 159, 243, 173, 39, 2, 32, 242, 242, 169, 104, 32, 159, 243, 76, 179, 239, 160, 0, 173, 41, 2, 32, 242, 242, 169, 32, 32, 159, 243, 177, 8, 141, 39, 2, 32, 242, 242, 32, 110, 242, 169, 32, 32, 159, 243, 177, 8, 141, 40, 2, 32, 242, 242, 32, 110, 242, 160, 2, 32, 150, 242, 32, 92, 242, 173, 40, 2, 32, 242, 242, 173, 39, 2, 32, 242, 242, 169, 104, 32, 159, 243, 160, 6, 177, 10, 201, 88, 240, 7, 201, 89, 240, 16, 76, 179, 239, 169, 44, 32, 159, 243, 169, 88, 32, 159, 243, 76, 179, 239, 169, 44, 32, 159, 243, 169, 89, 32, 159, 243, 76, 179, 239, 160, 0, 173, 41, 2, 32, 242, 242, 169, 32, 32, 159, 243, 177, 8, 141, 39, 2, 32, 242, 242, 32, 110, 242, 160, 5, 32, 150, 242, 32, 92, 242, 173, 8, 0, 141, 37, 2, 173, 9, 0, 141, 38, 2, 173, 39, 2, 48, 18, 173, 37, 2, 24, 109, 39, 2, 141, 37, 2, 144, 32, 238, 38, 2, 76, 114, 241, 206, 39, 2, 173, 39, 2, 73, 255, 141, 39, 2, 173, 37, 2, 56, 237, 39, 2, 141, 37, 2, 176, 3, 206, 38, 2, 173, 38, 2, 32, 242, 242, 173, 37, 2, 32, 242, 242, 169, 104, 32, 159, 243, 76, 179, 239, 160, 0, 173, 41, 2, 32, 242, 242, 169, 32, 32, 159, 243, 177, 8, 141, 39, 2, 32, 242, 242, 32, 110, 242, 160, 5, 32, 150, 242, 32, 92, 242, 169, 40, 32, 159, 243, 173, 39, 2, 32, 242, 242, 169, 104, 32, 159, 243, 169, 41, 32, 159, 243, 169, 44, 32, 159, 243, 169, 89, 32, 159, 243, 76, 179, 239, 160, 0, 173, 41, 2, 32, 242, 242, 169, 32, 32, 159, 243, 177, 8, 141, 39, 2, 32, 242, 242, 32, 110, 242, 160, 5, 32, 150, 242, 32, 92, 242, 169, 40, 32, 159, 243, 173, 39, 2, 32, 242, 242, 169, 104, 32, 159, 243, 169, 44, 32, 159, 243, 169, 88, 32, 159, 243, 169, 41, 32, 159, 243, 76, 179, 239, 160, 0, 173, 41, 2, 32, 242, 242, 169, 32, 32, 159, 243, 177, 8, 141, 39, 2, 32, 242, 242, 32, 110, 242, 169, 32, 32, 159, 243, 177, 8, 141, 40, 2, 32, 242, 242, 32, 110, 242, 160, 2, 32, 150, 242, 32, 92, 242, 169, 40, 32, 159, 243, 173, 40, 2, 32, 242, 242, 173, 39, 2, 32, 242, 242, 169, 104, 32, 159, 243, 169, 41, 32, 159, 243, 76, 179, 239, 24, 96, 56, 96, 160, 0, 177, 10, 32, 159, 243, 200, 192, 3, 208, 246, 169, 32, 32, 159, 243, 96, 238, 8, 0, 208, 3, 238, 9, 0, 96, 32, 208, 242, 176, 3, 76, 140, 242, 168, 232, 32, 208, 242, 176, 3, 76, 140, 242, 170, 56, 96, 24, 96, 162, 13, 160, 245, 32, 70, 243, 96, 72, 169, 32, 32, 159, 243, 136, 208, 248, 104, 96, 201, 65, 48, 7, 201, 91, 16, 3, 24, 105, 32, 96, 201, 97, 48, 7, 201, 123, 16, 3, 56, 233, 32, 96, 173, 9, 0, 32, 242, 242, 173, 8, 0, 32, 242, 242, 169, 32, 32, 159, 243, 169, 32, 32, 159, 243, 96, 189, 69, 2, 32, 46, 234, 144, 25, 10, 10, 10, 10, 41, 240, 141, 130, 2, 232, 189, 69, 2, 32, 46, 234, 144, 7, 13, 130, 2, 141, 130, 2, 56, 96, 141, 130, 2, 74, 74, 74, 74, 41, 15, 201, 10, 16, 5, 9, 48, 76, 9, 243, 56, 233, 9, 9, 64, 32, 159, 243, 173, 130, 2, 41, 15, 201, 10, 16, 5, 9, 48, 76, 31, 243, 56, 233, 9, 9, 64, 32, 159, 243, 173, 130, 2, 96, 169, 230, 141, 4, 0, 169, 225, 141, 5, 0, 169, 0, 141, 31, 2, 169, 0, 141, 21, 2, 141, 22, 2, 141, 23, 2, 141, 24, 2, 169, 0, 96, 142, 6, 0, 140, 7, 0, 160, 0, 177, 6, 240, 14, 32, 159, 243, 238, 6, 0, 208, 3, 238, 7, 0, 76, 78, 243, 96, 8, 138, 72, 152, 72, 173, 1, 160, 41, 8, 240, 9, 173, 0, 160, 141, 180, 2, 76, 123, 243, 169, 0, 141, 180, 2, 104, 168, 104, 170, 40, 173, 180, 2, 96, 8, 138, 72, 152, 72, 173, 1, 160, 41, 8, 240, 249, 173, 0, 160, 141, 180, 2, 104, 168, 104, 170, 40, 173, 180, 2, 96, 141, 180, 2, 8, 152, 72, 138, 72, 173, 1, 160, 41, 16, 240, 249, 173, 180, 2, 141, 0, 160, 104, 170, 104, 168, 40, 173, 180, 2, 96, 72, 138, 72, 152, 72, 169, 0, 141, 136, 2, 169, 16, 141, 137, 2, 206, 136, 2, 208, 251, 206, 137, 2, 208, 246, 104, 168, 104, 170, 104, 96, 22, 11, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 0, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102, 0, 10, 13, 10, 85, 77, 79, 78, 54, 53, 32, 86, 49, 46, 49, 49, 97, 32, 45, 32, 85, 110, 100, 101, 114, 115, 116, 97, 110, 100, 97, 98, 108, 101, 32, 77, 111, 110, 105, 116, 111, 114, 32, 102, 111, 114, 32, 116, 104, 101, 32, 54, 53, 48, 48, 32, 115, 101, 114, 105, 101, 115, 32, 109, 105, 99, 114, 111, 112, 114, 111, 99, 101, 115, 115, 111, 114, 115, 46, 10, 13, 0, 10, 13, 45, 0, 10, 13, 0, 10, 13, 83, 101, 110, 100, 32, 83, 32, 114, 101, 99, 111, 114, 100, 115, 32, 119, 104, 101, 110, 32, 121, 111, 117, 32, 97, 114, 101, 32, 114, 101, 97, 100, 121, 46, 46, 46, 10, 13, 0, 10, 13, 7, 69, 114, 114, 111, 114, 32, 76, 111, 97, 100, 105, 110, 103, 32, 83, 32, 82, 101, 99, 111, 114, 100, 115, 46, 46, 46, 10, 13, 0, 10, 13, 7, 83, 32, 114, 101, 99, 111, 114, 100, 115, 32, 115, 117, 99, 99, 101, 115, 115, 102, 117, 108, 108, 121, 32, 108, 111, 97, 100, 101, 100, 46, 0, 10, 13, 10, 80, 103, 109, 67, 110, 116, 114, 40, 80, 67, 41, 32, 32, 65, 99, 99, 117, 109, 40, 65, 67, 41, 32, 32, 88, 82, 101, 103, 40, 88, 82, 41, 32, 32, 89, 82, 101, 103, 40, 89, 82, 41, 32, 32, 83, 116, 107, 80, 116, 114, 40, 83, 80, 41, 32, 32, 78, 86, 45, 66, 68, 73, 90, 67, 40, 83, 82, 41, 10, 13, 0, 10, 13, 32, 58, 0, 32, 32, 32, 32, 32, 32, 32, 32, 63, 63, 63, 0, 10, 13, 65, 108, 108, 32, 98, 114, 101, 97, 107, 112, 111, 105, 110, 116, 115, 32, 97, 114, 101, 32, 99, 117, 114, 114, 101, 110, 116, 108, 121, 32, 105, 110, 32, 117, 115, 101, 46, 0, 10, 13, 78, 111, 32, 98, 114, 101, 97, 107, 112, 111, 105, 110, 116, 32, 101, 120, 105, 115, 116, 115, 32, 97, 116, 32, 116, 104, 105, 115, 32, 97, 100, 100, 114, 101, 115, 115, 46, 0, 10, 13, 89, 111, 117, 32, 99, 97, 110, 110, 111, 116, 32, 71, 79, 32, 97, 116, 32, 97, 32, 98, 114, 101, 97, 107, 112, 111, 105, 110, 116, 101, 100, 32, 97, 100, 100, 114, 101, 115, 115, 44, 32, 84, 82, 65, 67, 69, 32, 112, 97, 115, 116, 32, 105, 116, 32, 116, 104, 101, 110, 32, 71, 79, 46, 0, 10, 13, 63, 0, 10, 13, 10, 65, 115, 115, 101, 109, 98, 108, 101, 32, 32, 32, 32, 32, 32, 32, 65, 32, 115, 116, 97, 114, 116, 95, 97, 100, 100, 114, 101, 115, 115, 10, 13, 66, 114, 101, 97, 107, 112, 111, 105, 110, 116, 32, 32, 32, 32, 32, 66, 32, 40, 43, 44, 45, 44, 63, 41, 32, 97, 100, 100, 114, 101, 115, 115, 10, 13, 68, 117, 109, 112, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 68, 32, 91, 115, 116, 97, 114, 116, 95, 97, 100, 100, 114, 101, 115, 115, 32, 91, 101, 110, 100, 95, 97, 100, 100, 114, 101, 115, 115, 93, 93, 10, 13, 69, 110, 116, 101, 114, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 69, 32, 97, 100, 100, 114, 101, 115, 115, 32, 108, 105, 115, 116, 10, 13, 70, 105, 108, 108, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 70, 32, 115, 116, 97, 114, 116, 95, 97, 100, 100, 114, 101, 115, 115, 32, 101, 110, 100, 95, 97, 100, 100, 114, 101, 115, 115, 32, 108, 105, 115, 116, 10, 13, 71, 111, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 71, 32, 91, 115, 116, 97, 114, 116, 95, 97, 100, 100, 114, 101, 115, 115, 93, 10, 13, 72, 101, 108, 112, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 72, 32, 111, 114, 32, 63, 10, 13, 76, 111, 97, 100, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 76, 10, 13, 77, 111, 118, 101, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 77, 32, 115, 116, 97, 114, 116, 95, 97, 100, 100, 114, 101, 115, 115, 32, 101, 110, 100, 95, 97, 100, 100, 114, 101, 115, 115, 32, 100, 101, 115, 116, 105, 110, 97, 116, 105, 111, 110, 95, 97, 100, 100, 114, 101, 115, 115, 10, 13, 82, 101, 103, 105, 115, 116, 101, 114, 32, 32, 32, 32, 32, 32, 32, 82, 32, 91, 80, 67, 44, 65, 67, 44, 88, 82, 44, 89, 82, 44, 83, 80, 44, 83, 82, 93, 10, 13, 83, 101, 97, 114, 99, 104, 32, 32, 32, 32, 32, 32, 32, 32, 32, 83, 32, 115, 116, 97, 114, 116, 95, 97, 100, 100, 114, 101, 115, 115, 32, 101, 110, 100, 95, 97, 100, 100, 114, 101, 115, 115, 32, 108, 105, 115, 116, 10, 13, 84, 114, 97, 99, 101, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 84, 32, 91, 115, 116, 97, 114, 116, 95, 97, 100, 100, 114, 101, 115, 115, 32, 91, 118, 97, 108, 117, 101, 93, 93, 10, 13, 85, 110, 97, 115, 115, 101, 109, 98, 108, 101, 32, 32, 32, 32, 32, 85, 32, 91, 115, 116, 97, 114, 116, 95, 97, 100, 100, 114, 101, 115, 115, 32, 91, 101, 110, 100, 95, 97, 100, 100, 114, 101, 115, 115, 93, 93, 10, 13, 0, 65, 68, 67, 32, 73, 77, 77, 105, 2, 2, 65, 68, 67, 32, 65, 66, 83, 109, 3, 4, 65, 68, 67, 32, 65, 66, 88, 125, 3, 4, 65, 68, 67, 32, 65, 66, 89, 121, 3, 4, 65, 68, 67, 32, 73, 73, 82, 97, 2, 6, 65, 68, 67, 32, 73, 82, 73, 113, 2, 5, 65, 78, 68, 32, 73, 77, 77, 41, 2, 2, 65, 78, 68, 32, 65, 66, 83, 45, 3, 4, 65, 78, 68, 32, 65, 66, 88, 61, 3, 4, 65, 78, 68, 32, 65, 66, 89, 57, 3, 4, 65, 78, 68, 32, 73, 73, 82, 33, 2, 6, 65, 78, 68, 32, 73, 82, 73, 49, 2, 5, 65, 83, 76, 32, 65, 67, 67, 10, 1, 2, 65, 83, 76, 32, 65, 66, 83, 14, 3, 6, 65, 83, 76, 32, 65, 66, 88, 30, 3, 7, 66, 67, 67, 32, 82, 69, 76, 144, 2, 2, 66, 67, 83, 32, 82, 69, 76, 176, 2, 2, 66, 69, 81, 32, 82, 69, 76, 240, 2, 2, 66, 73, 84, 32, 65, 66, 83, 44, 3, 4, 66, 77, 73, 32, 82, 69, 76, 48, 2, 2, 66, 78, 69, 32, 82, 69, 76, 208, 2, 2, 66, 80, 76, 32, 82, 69, 76, 16, 2, 2, 66, 82, 75, 32, 73, 77, 80, 0, 1, 7, 66, 86, 67, 32, 82, 69, 76, 80, 2, 2, 66, 86, 83, 32, 82, 69, 76, 112, 2, 2, 67, 76, 67, 32, 73, 77, 80, 24, 1, 2, 67, 76, 68, 32, 73, 77, 80, 216, 1, 2, 67, 76, 73, 32, 73, 77, 80, 88, 1, 2, 67, 76, 86, 32, 73, 77, 80, 184, 1, 2, 67, 77, 80, 32, 73, 77, 77, 201, 2, 2, 67, 77, 80, 32, 65, 66, 83, 205, 3, 4, 67, 77, 80, 32, 65, 66, 88, 221, 3, 4, 67, 77, 80, 32, 65, 66, 89, 217, 3, 4, 67, 77, 80, 32, 73, 73, 82, 193, 2, 6, 67, 77, 80, 32, 73, 82, 73, 209, 2, 5, 67, 80, 88, 32, 73, 77, 77, 224, 2, 2, 67, 80, 88, 32, 65, 66, 83, 236, 3, 4, 67, 80, 89, 32, 73, 77, 77, 192, 2, 2, 67, 80, 89, 32, 65, 66, 83, 204, 3, 4, 68, 69, 67, 32, 65, 66, 83, 206, 3, 6, 68, 69, 67, 32, 65, 66, 88, 222, 3, 7, 68, 69, 88, 32, 73, 77, 80, 202, 1, 2, 68, 69, 89, 32, 73, 77, 80, 136, 1, 2, 69, 79, 82, 32, 73, 77, 77, 73, 2, 2, 69, 79, 82, 32, 65, 66, 83, 77, 3, 4, 69, 79, 82, 32, 65, 66, 88, 93, 3, 4, 69, 79, 82, 32, 65, 66, 89, 89, 3, 4, 69, 79, 82, 32, 73, 73, 82, 65, 2, 6, 69, 79, 82, 32, 73, 82, 73, 81, 2, 5, 73, 78, 67, 32, 65, 66, 83, 238, 3, 6, 73, 78, 67, 32, 65, 66, 88, 254, 3, 7, 73, 78, 88, 32, 73, 77, 80, 232, 1, 2, 73, 78, 89, 32, 73, 77, 80, 200, 1, 2, 74, 77, 80, 32, 65, 66, 83, 76, 3, 3, 74, 77, 80, 32, 73, 78, 68, 108, 3, 5, 74, 83, 82, 32, 65, 66, 83, 32, 3, 6, 76, 68, 65, 32, 73, 77, 77, 169, 2, 2, 76, 68, 65, 32, 65, 66, 83, 173, 3, 4, 76, 68, 65, 32, 65, 66, 88, 189, 3, 4, 76, 68, 65, 32, 65, 66, 89, 185, 3, 4, 76, 68, 65, 32, 73, 73, 82, 161, 2, 6, 76, 68, 65, 32, 73, 82, 73, 177, 2, 5, 76, 68, 88, 32, 73, 77, 77, 162, 2, 2, 76, 68, 88, 32, 65, 66, 83, 174, 3, 4, 76, 68, 88, 32, 65, 66, 89, 190, 3, 4, 76, 68, 89, 32, 73, 77, 77, 160, 2, 2, 76, 68, 89, 32, 65, 66, 83, 172, 3, 4, 76, 68, 89, 32, 65, 66, 88, 188, 3, 4, 76, 83, 82, 32, 65, 67, 67, 74, 1, 2, 76, 83, 82, 32, 65, 66, 83, 78, 3, 6, 76, 83, 82, 32, 65, 66, 88, 94, 3, 7, 78, 79, 80, 32, 73, 77, 80, 234, 1, 2, 79, 82, 65, 32, 73, 77, 77, 9, 2, 2, 79, 82, 65, 32, 65, 66, 83, 13, 3, 4, 79, 82, 65, 32, 65, 66, 88, 29, 3, 4, 79, 82, 65, 32, 65, 66, 89, 25, 3, 4, 79, 82, 65, 32, 73, 73, 82, 1, 2, 6, 79, 82, 65, 32, 73, 82, 73, 17, 2, 5, 80, 72, 65, 32, 73, 77, 80, 72, 1, 3, 80, 72, 80, 32, 73, 77, 80, 8, 1, 3, 80, 76, 65, 32, 73, 77, 80, 104, 1, 4, 80, 76, 80, 32, 73, 77, 80, 40, 1, 4, 82, 79, 76, 32, 65, 67, 67, 42, 1, 2, 82, 79, 76, 32, 65, 66, 83, 46, 3, 6, 82, 79, 76, 32, 65, 66, 88, 62, 3, 7, 82, 79, 82, 32, 65, 67, 67, 106, 1, 2, 82, 79, 82, 32, 65, 66, 83, 110, 3, 6, 82, 79, 82, 32, 65, 66, 88, 126, 3, 7, 82, 84, 73, 32, 73, 77, 80, 64, 1, 6, 82, 84, 83, 32, 73, 77, 80, 96, 1, 6, 83, 66, 67, 32, 73, 77, 77, 233, 2, 2, 83, 66, 67, 32, 65, 66, 83, 237, 3, 4, 83, 66, 67, 32, 65, 66, 88, 253, 3, 4, 83, 66, 67, 32, 65, 66, 89, 249, 3, 4, 83, 66, 67, 32, 73, 73, 82, 225, 2, 6, 83, 66, 67, 32, 73, 82, 73, 241, 2, 5, 83, 69, 67, 32, 73, 77, 80, 56, 1, 2, 83, 69, 68, 32, 73, 77, 80, 248, 1, 2, 83, 69, 73, 32, 73, 77, 80, 120, 1, 2, 83, 84, 65, 32, 65, 66, 83, 141, 3, 4, 83, 84, 65, 32, 65, 66, 88, 157, 3, 5, 83, 84, 65, 32, 65, 66, 89, 153, 3, 5, 83, 84, 65, 32, 73, 73, 82, 129, 2, 6, 83, 84, 65, 32, 73, 82, 73, 145, 2, 6, 83, 84, 88, 32, 65, 66, 83, 142, 3, 4, 83, 84, 89, 32, 65, 66, 83, 140, 3, 4, 84, 65, 88, 32, 73, 77, 80, 170, 1, 2, 84, 65, 89, 32, 73, 77, 80, 168, 1, 2, 84, 83, 88, 32, 73, 77, 80, 186, 1, 2, 84, 88, 65, 32, 73, 77, 80, 138, 1, 2, 84, 88, 83, 32, 73, 77, 80, 154, 1, 2, 84, 89, 65, 32, 73, 77, 80, 152, 1, 2, 90, 90, 90, 32, 90, 90, 90, 0, 0, 0, 0, 224, 211, 225};






		for(int x = 0; x < (code.length - 4); x++)
		{
			emu.rom[x] = code[x];
		}

		//Reset vector.
		emu.rom[0x1ffc] = code[code.length-3];
		emu.rom[0x1ffd] = code[code.length-4];

		//Maskable interrupt vector.
		emu.rom[0x1ffe] = code[code.length-1];
		emu.rom[0x1fff] = code[code.length-2];
		
		
		
		//Create random chip image.
		java.util.Random rnd = new java.util.Random( 8379374 );
		for(int x = 0; x <= 0x1fff; x++)
		{
			emu.randomMemory[x] = rnd.nextInt(256);
		}

		//new Thread(emu,"U6502").start();
		emu.run();



	}
} //end class.

/*
[76, 27, 16, 76, 155, 35, 76, 93, 35, 76, 128, 35, 76, 66, 35, 76, 148, 34, 76, 240, 34, 76, 46, 26, 76, 112, 16, 162, 255, 154, 216, 32, 34, 35, 76, 37, 16, 162, 252, 160, 35, 32, 66, 35, 0, 201, 2, 208, 19, 41, 240, 74, 74, 74, 24, 105, 192, 141, 9, 0, 169, 0, 141, 8, 0, 108, 8, 0, 76, 71, 16, 160, 0, 140, 68, 0, 162, 100, 200, 208, 253, 202, 208, 250, 238, 68, 0, 173, 68, 0, 76, 76, 16, 32, 105, 16, 32, 219, 16, 32, 51, 17, 76, 93, 16, 162, 77, 160, 36, 32, 66, 35, 160, 0, 140, 94, 0, 140, 205, 0, 140, 95, 0, 140, 115, 0, 140, 135, 0, 160, 0, 169, 0, 153, 165, 0, 200, 192, 39, 208, 248, 160, 0, 32, 128, 35, 201, 0, 240, 249, 201, 8, 208, 3, 76, 161, 16, 201, 127, 208, 21, 136, 48, 13, 169, 32, 32, 155, 35, 169, 8, 32, 155, 35, 76, 143, 16, 160, 0, 76, 143, 16, 201, 10, 240, 13, 153, 165, 0, 200, 192, 39, 208, 205, 160, 39, 76, 143, 16, 136, 185, 165, 0, 201, 13, 240, 1, 200, 169, 0, 153, 165, 0, 96, 160, 255, 76, 248, 16, 160, 0, 185, 165, 0, 240, 80, 201, 63, 48, 75, 201, 90, 16, 3, 56, 176, 8, 201, 97, 48, 64, 201, 122, 16, 60, 141, 205, 0, 200, 192, 20, 240, 52, 185, 165, 0, 201, 0, 240, 46, 201, 32, 240, 240, 174, 94, 0, 138, 24, 105, 20, 141, 94, 0, 185, 165, 0, 201, 32, 240, 16, 201, 0, 240, 12, 157, 95, 0, 232, 200, 192, 40, 240, 11, 76, 18, 17, 169, 0, 157, 95, 0, 76, 248, 16, 96, 96, 56, 173, 205, 0, 32, 159, 34, 201, 108, 208, 6, 32, 56, 25, 76, 198, 17, 201, 100, 208, 6, 32, 34, 23, 76, 198, 17, 201, 114, 208, 6, 32, 219, 26, 76, 198, 17, 201, 103, 208, 6, 32, 199, 24, 76, 198, 17, 201, 101, 208, 6, 32, 32, 24, 76, 198, 17, 201, 102, 208, 6, 32, 134, 24, 76, 198, 17, 201, 117, 208, 6, 32, 98, 31, 76, 198, 17, 201, 116, 208, 6, 32, 49, 29, 76, 198, 17, 201, 98, 208, 6, 32, 186, 21, 76, 198, 17, 201, 115, 208, 6, 32, 160, 28, 76, 198, 17, 201, 109, 208, 6, 32, 111, 26, 76, 198, 17, 201, 97, 208, 6, 32, 49, 18, 76, 198, 17, 201, 104, 208, 6, 32, 48, 25, 76, 198, 17, 201, 63, 208, 6, 32, 48, 25, 76, 198, 17, 176, 9, 162, 172, 160, 37, 32, 66, 35, 24, 96, 56, 96, 120, 141, 68, 0, 104, 72, 41, 16, 208, 6, 173, 68, 0, 108, 2, 0, 108, 4, 0, 104, 141, 75, 0, 173, 68, 0, 141, 71, 0, 142, 72, 0, 140, 73, 0, 186, 142, 74, 0, 104, 56, 233, 1, 141, 69, 0, 104, 233, 0, 141, 70, 0, 160, 0, 140, 95, 0, 32, 219, 26, 173, 57, 0, 201, 1, 208, 3, 76, 221, 30, 160, 3, 162, 6, 185, 47, 0, 201, 1, 208, 5, 185, 51, 0, 129, 18, 136, 202, 202, 16, 239, 76, 93, 16, 162, 0, 32, 117, 34, 176, 3, 76, 39, 19, 142, 8, 0, 140, 9, 0, 162, 81, 160, 36, 32, 66, 35, 32, 183, 34, 32, 112, 16, 32, 214, 16, 173, 95, 0, 201, 0, 240, 4, 201, 32, 208, 3, 76, 41, 19, 160, 0, 185, 95, 0, 32, 171, 34, 153, 95, 0, 200, 192, 3, 208, 242, 32, 43, 19, 176, 40, 32, 183, 34, 169, 32, 32, 155, 35, 162, 95, 160, 0, 32, 66, 35, 169, 32, 32, 155, 35, 162, 115, 160, 0, 32, 66, 35, 169, 32, 32, 155, 35, 169, 63, 32, 155, 35, 76, 65, 18, 32, 99, 19, 176, 3, 76, 116, 18, 32, 88, 20, 176, 3, 76, 116, 18, 32, 120, 20, 176, 3, 76, 116, 18, 32, 183, 34, 160, 0, 173, 26, 0, 145, 8, 32, 240, 34, 32, 108, 34, 169, 32, 32, 155, 35, 173, 29, 0, 240, 40, 173, 27, 0, 145, 8, 32, 240, 34, 32, 108, 34, 169, 32, 32, 155, 35, 173, 30, 0, 240, 30, 173, 28, 0, 145, 8, 32, 240, 34, 32, 108, 34, 169, 32, 32, 155, 35, 76, 12, 19, 169, 32, 32, 155, 35, 32, 155, 35, 32, 155, 35, 169, 32, 32, 155, 35, 32, 155, 35, 32, 155, 35, 169, 32, 32, 155, 35, 162, 95, 160, 0, 32, 66, 35, 169, 32, 32, 155, 35, 162, 115, 160, 0, 32, 66, 35, 76, 65, 18, 24, 96, 56, 96, 162, 178, 142, 10, 0, 162, 39, 142, 11, 0, 160, 0, 177, 10, 217, 95, 0, 240, 25, 173, 10, 0, 24, 105, 10, 141, 10, 0, 144, 3, 238, 11, 0, 160, 4, 177, 10, 201, 90, 240, 11, 76, 53, 19, 200, 192, 3, 240, 5, 76, 55, 19, 24, 96, 56, 96, 160, 0, 185, 115, 0, 201, 0, 208, 8, 169, 80, 141, 31, 0, 76, 86, 20, 201, 35, 208, 8, 169, 77, 141, 31, 0, 76, 86, 20, 201, 65, 240, 7, 201, 97, 240, 3, 76, 162, 19, 200, 185, 115, 0, 201, 0, 240, 7, 201, 32, 240, 3, 76, 162, 19, 169, 67, 141, 31, 0, 76, 86, 20, 160, 6, 177, 10, 201, 76, 208, 6, 141, 31, 0, 76, 86, 20, 160, 0, 185, 115, 0, 201, 40, 208, 3, 76, 252, 19, 160, 0, 185, 115, 0, 201, 44, 240, 8, 201, 0, 240, 43, 200, 76, 190, 19, 200, 185, 115, 0, 201, 88, 240, 15, 201, 120, 240, 11, 201, 89, 240, 15, 201, 121, 240, 11, 76, 84, 20, 169, 88, 141, 31, 0, 76, 86, 20, 169, 89, 141, 31, 0, 76, 86, 20, 169, 83, 141, 31, 0, 76, 86, 20, 200, 185, 115, 0, 201, 44, 240, 11, 201, 41, 240, 30, 192, 11, 208, 240, 76, 84, 20, 200, 185, 115, 0, 201, 88, 240, 7, 201, 120, 240, 3, 76, 84, 20, 169, 82, 141, 31, 0, 76, 86, 20, 200, 185, 115, 0, 201, 0, 240, 22, 201, 44, 240, 3, 76, 84, 20, 200, 185, 115, 0, 201, 89, 240, 15, 201, 121, 240, 11, 76, 84, 20, 169, 68, 141, 31, 0, 76, 86, 20, 169, 73, 141, 31, 0, 76, 86, 20, 24, 96, 56, 96, 160, 6, 177, 10, 205, 31, 0, 240, 21, 173, 10, 0, 24, 105, 10, 141, 10, 0, 144, 3, 238, 11, 0, 32, 53, 19, 176, 228, 24, 96, 56, 96, 169, 0, 141, 29, 0, 141, 30, 0, 160, 7, 177, 10, 141, 26, 0, 173, 31, 0, 201, 80, 240, 39, 201, 67, 240, 38, 201, 77, 240, 37, 201, 83, 240, 60, 201, 88, 240, 56, 201, 89, 240, 52, 201, 73, 240, 88, 201, 82, 240, 84, 201, 68, 240, 80, 201, 76, 240, 36, 76, 145, 21, 76, 147, 21, 76, 147, 21, 32, 149, 21, 176, 3, 76, 145, 21, 32, 206, 34, 176, 3, 76, 145, 21, 141, 27, 0, 169, 1, 141, 29, 0, 76, 147, 21, 32, 149, 21, 176, 3, 76, 145, 21, 32, 117, 34, 176, 3, 76, 145, 21, 173, 31, 0, 201, 76, 240, 72, 142, 27, 0, 140, 28, 0, 169, 1, 141, 29, 0, 141, 30, 0, 76, 147, 21, 32, 149, 21, 176, 3, 76, 145, 21, 32, 117, 34, 176, 3, 76, 145, 21, 173, 31, 0, 201, 68, 240, 15, 192, 0, 208, 120, 142, 27, 0, 169, 1, 141, 29, 0, 76, 147, 21, 142, 27, 0, 140, 28, 0, 169, 1, 141, 29, 0, 141, 30, 0, 76, 147, 21, 142, 43, 0, 140, 44, 0, 173, 8, 0, 141, 45, 0, 173, 9, 0, 141, 46, 0, 160, 8, 177, 10, 24, 109, 45, 0, 141, 45, 0, 144, 3, 238, 46, 0, 56, 173, 43, 0, 237, 45, 0, 141, 43, 0, 173, 44, 0, 237, 46, 0, 141, 44, 0, 173, 44, 0, 201, 255, 240, 14, 201, 0, 208, 28, 173, 43, 0, 201, 128, 16, 21, 76, 134, 21, 173, 43, 0, 201, 128, 48, 11, 141, 27, 0, 169, 1, 141, 29, 0, 76, 147, 21, 24, 96, 56, 96, 162, 20, 160, 0, 189, 95, 0, 217, 218, 35, 240, 23, 217, 235, 35, 240, 18, 200, 192, 16, 240, 3, 76, 156, 21, 232, 224, 31, 240, 3, 76, 151, 21, 24, 96, 56, 96, 173, 95, 0, 201, 43, 240, 11, 201, 45, 240, 10, 201, 63, 240, 9, 76, 218, 22, 76, 70, 22, 76, 2, 22, 162, 81, 160, 36, 32, 66, 35, 160, 255, 162, 255, 200, 232, 185, 18, 0, 141, 8, 0, 200, 185, 18, 0, 141, 9, 0, 189, 47, 0, 240, 3, 32, 183, 34, 192, 7, 208, 229, 162, 81, 160, 36, 32, 66, 35, 76, 218, 22, 173, 115, 0, 201, 0, 240, 10, 162, 20, 32, 117, 34, 176, 16, 76, 218, 22, 160, 3, 169, 0, 153, 47, 0, 136, 16, 248, 76, 218, 22, 142, 43, 0, 140, 44, 0, 160, 3, 162, 6, 32, 219, 22, 176, 15, 136, 202, 202, 16, 246, 162, 66, 160, 37, 32, 66, 35, 76, 218, 22, 169, 0, 153, 47, 0, 76, 218, 22, 173, 115, 0, 201, 0, 240, 7, 162, 20, 32, 117, 34, 176, 3, 76, 218, 22, 142, 43, 0, 140, 44, 0, 160, 3, 162, 6, 185, 47, 0, 201, 0, 240, 5, 32, 219, 22, 176, 231, 136, 202, 202, 16, 239, 160, 0, 185, 47, 0, 201, 0, 240, 15, 200, 192, 4, 208, 244, 162, 26, 160, 37, 32, 66, 35, 76, 218, 22, 140, 55, 0, 169, 1, 153, 47, 0, 192, 0, 208, 15, 173, 43, 0, 141, 18, 0, 173, 44, 0, 141, 19, 0, 76, 218, 22, 192, 1, 208, 15, 173, 43, 0, 141, 20, 0, 173, 44, 0, 141, 21, 0, 76, 218, 22, 192, 2, 208, 15, 173, 43, 0, 141, 22, 0, 173, 44, 0, 141, 23, 0, 76, 218, 22, 173, 43, 0, 141, 24, 0, 173, 44, 0, 141, 25, 0, 76, 218, 22, 96, 192, 0, 208, 11, 173, 44, 0, 205, 19, 0, 240, 44, 76, 32, 23, 192, 1, 208, 11, 173, 44, 0, 205, 21, 0, 240, 29, 76, 32, 23, 192, 2, 208, 11, 173, 44, 0, 205, 23, 0, 240, 14, 76, 32, 23, 173, 44, 0, 205, 25, 0, 240, 3, 76, 32, 23, 173, 43, 0, 221, 18, 0, 240, 3, 76, 32, 23, 56, 96, 24, 96, 162, 0, 189, 95, 0, 201, 0, 240, 58, 32, 206, 34, 176, 3, 76, 230, 23, 141, 9, 0, 232, 32, 206, 34, 176, 3, 76, 230, 23, 141, 8, 0, 162, 20, 189, 95, 0, 201, 0, 240, 26, 32, 206, 34, 176, 3, 76, 230, 23, 141, 93, 0, 232, 32, 206, 34, 176, 3, 76, 230, 23, 141, 92, 0, 76, 124, 23, 173, 9, 0, 141, 93, 0, 173, 8, 0, 141, 92, 0, 24, 105, 15, 141, 92, 0, 144, 3, 238, 93, 0, 169, 13, 32, 155, 35, 169, 10, 32, 155, 35, 162, 0, 160, 0, 32, 183, 34, 177, 8, 32
, 10, 24, 32, 240, 34, 232, 224, 16, 208, 3, 32, 242, 23, 224, 8, 208, 18, 169, 32, 32, 155, 35, 169, 45, 32, 155, 35, 169, 32, 32, 155, 35, 76, 184, 23, 169, 32, 32, 155, 35, 173, 8, 0, 205, 92, 0, 208, 8, 173, 9, 0, 205, 93, 0, 240, 32, 238, 8, 0, 208, 3, 238, 9, 0, 224, 16, 208, 15, 169, 10, 32, 155, 35, 169, 13, 32, 155, 35, 32, 183, 34, 162, 0, 76, 141, 23, 24, 96, 238, 8, 0, 208, 3, 238, 9, 0, 56, 96, 169, 32, 32, 155, 35, 169, 32, 32, 155, 35, 162, 0, 189, 76, 0, 32, 155, 35, 232, 224, 16, 208, 245, 96, 72, 201, 32, 48, 10, 201, 127, 16, 6, 157, 76, 0, 76, 30, 24, 169, 46, 157, 76, 0, 104, 96, 173, 95, 0, 201, 0, 208, 3, 76, 78, 24, 173, 115, 0, 201, 0, 208, 3, 76, 78, 24, 162, 0, 32, 117, 34, 176, 3, 76, 78, 24, 142, 8, 0, 140, 9, 0, 162, 20, 32, 82, 24, 144, 3, 76, 80, 24, 24, 96, 56, 96, 160, 0, 140, 207, 0, 189, 95, 0, 240, 40, 32, 206, 34, 176, 3, 76, 132, 24, 145, 8, 238, 8, 0, 208, 3, 238, 9, 0, 238, 207, 0, 173, 207, 0, 201, 10, 240, 10, 232, 189, 95, 0, 240, 4, 232, 76, 87, 24, 56, 96, 24, 96, 162, 0, 32, 117, 34, 176, 3, 76, 195, 24, 142, 8, 0, 140, 9, 0, 162, 20, 32, 117, 34, 176, 3, 76, 195, 24, 142, 92, 0, 140, 93, 0, 162, 40, 32, 82, 24, 144, 22, 173, 93, 0, 205, 9, 0, 240, 3, 76, 166, 24, 173, 92, 0, 205, 8, 0, 240, 5, 76, 166, 24, 24, 96, 56, 96, 173, 95, 0, 201, 0, 240, 16, 162, 0, 32, 117, 34, 176, 3, 76, 44, 25, 142, 69, 0, 140, 70, 0, 174, 69, 0, 142, 43, 0, 172, 70, 0, 140, 44, 0, 160, 3, 162, 6, 185, 47, 0, 201, 0, 240, 5, 32, 219, 22, 176, 8, 136, 202, 202, 16, 239, 76, 12, 25, 162, 106, 160, 37, 32, 66, 35, 76, 46, 25, 160, 3, 162, 6, 185, 47, 0, 201, 1, 208, 9, 161, 18, 153, 51, 0, 169, 0, 129, 18, 136, 202, 202, 16, 235, 104, 104, 104, 104, 108, 69, 0, 24, 96, 56, 96, 162, 176, 160, 37, 32, 66, 35, 96, 162, 84, 160, 36, 32, 66, 35, 32, 128, 35, 201, 83, 208, 249, 32, 128, 35, 141, 161, 0, 201, 48, 208, 8, 32, 81, 26, 144, 37, 76, 119, 25, 201, 49, 208, 8, 32, 91, 26, 144, 25, 76, 119, 25, 201, 57, 208, 18, 32, 101, 26, 144, 13, 162, 157, 160, 36, 32, 66, 35, 76, 131, 25, 76, 63, 25, 162, 125, 160, 36, 32, 66, 35, 56, 96, 32, 128, 35, 56, 96, 169, 0, 141, 159, 0, 141, 160, 0, 32, 248, 25, 141, 158, 0, 32, 31, 26, 170, 202, 202, 202, 138, 141, 155, 0, 32, 248, 25, 141, 9, 0, 32, 31, 26, 32, 248, 25, 141, 8, 0, 32, 31, 26, 96, 173, 155, 0, 240, 11, 32, 248, 25, 32, 31, 26, 206, 155, 0, 208, 245, 96, 173, 155, 0, 240, 23, 160, 0, 32, 248, 25, 32, 31, 26, 145, 8, 238, 8, 0, 208, 3, 238, 9, 0, 206, 155, 0, 208, 235, 96, 173, 159, 0, 73, 255, 141, 157, 0, 32, 248, 25, 205, 157, 0, 240, 4, 24, 76, 247, 25, 56, 96, 152, 72, 32, 128, 35, 32, 46, 26, 144, 28, 10, 10, 10, 10, 41, 240, 141, 156, 0, 32, 128, 35, 32, 46, 26, 144, 11, 13, 156, 0, 141, 156, 0, 104, 168, 173, 156, 0, 96, 72, 24, 109, 159, 0, 141, 159, 0, 144, 3, 238, 160, 0, 104, 96, 142, 206, 0, 162, 0, 221, 218, 35, 240, 19, 221, 235, 35, 240, 14, 232, 224, 16, 240, 3, 76, 51, 26, 169, 0, 24, 76, 80, 26, 138, 56, 174, 206, 0, 96, 32, 136, 25, 32, 180, 25, 32, 226, 25, 96, 32, 136, 25, 32, 197, 25, 32, 226, 25, 96, 32, 136, 25, 32, 180, 25, 32, 226, 25, 96, 173, 95, 0, 201, 0, 240, 7, 162, 0, 32, 117, 34, 176, 3, 76, 218, 26, 142, 8, 0, 140, 9, 0, 162, 20, 32, 117, 34, 176, 3, 76, 218, 26, 142, 92, 0, 140, 93, 0, 238, 92, 0, 208, 3, 238, 93, 0, 162, 40, 32, 117, 34, 176, 3, 76, 218, 26, 142, 10, 0, 140, 11, 0, 160, 0, 177, 8, 145, 10, 238, 10, 0, 208, 3, 238, 11, 0, 238, 8, 0, 208, 3, 238, 9, 0, 173, 8, 0, 205, 92, 0, 240, 3, 76, 176, 26, 173, 9, 0, 205, 93, 0, 240, 3, 76, 176, 26, 96, 72, 138, 72, 152, 72, 8, 173, 95, 0, 201, 0, 240, 3, 76, 83, 27, 162, 191, 160, 36, 32, 66, 35, 160, 3, 32, 148, 34, 173, 70, 0, 32, 240, 34, 173, 69, 0, 32, 240, 34, 160, 9, 32, 148, 34, 173, 71, 0, 32, 240, 34, 160, 9, 32, 148, 34, 173, 72, 0, 32, 240, 34, 160, 8, 32, 148, 34, 173, 73, 0, 32, 240, 34, 160, 8, 32, 148, 34, 173, 74, 0, 32, 240, 34, 160, 7, 32, 148, 34, 173, 75, 0, 162, 8, 42, 176, 10, 72, 169, 48, 32, 155, 35, 104, 76, 77, 27, 72, 169, 49, 32, 155, 35, 104, 202, 208, 233, 76, 152, 28, 169, 13, 32, 155, 35, 169, 10, 32, 155, 35, 173, 95, 0, 32, 159, 34, 201, 112, 208, 60, 173, 70, 0, 32, 240, 34, 173, 69, 0, 32, 240, 34, 32, 140, 34, 32, 112, 16, 32, 214, 16, 173, 95, 0, 201, 0, 208, 3, 76, 152, 28, 162, 0, 32, 206, 34, 176, 3, 76, 152, 28, 168, 232, 32, 206, 34, 176, 3, 76, 152, 28, 141, 69, 0, 140, 70, 0, 76, 152, 28, 201, 97, 208, 41, 173, 71, 0, 32, 240, 34, 32, 140, 34, 32, 112, 16, 32, 214, 16, 173, 95, 0, 201, 0, 208, 3, 76, 152, 28, 162, 0, 32, 206, 34, 176, 3, 76, 152, 28, 141, 71, 0, 76, 152, 28, 201, 120, 208, 41, 173, 72, 0, 32, 240, 34, 32, 140, 34, 32, 112, 16, 32, 214, 16, 173, 95, 0, 201, 0, 208, 3, 76, 152, 28, 162, 0, 32, 206, 34, 176, 3, 76, 152, 28, 141, 72, 0, 76, 152, 28, 201, 121, 208, 41, 173, 73, 0, 32, 240, 34, 32, 140, 34, 32, 112, 16, 32, 214, 16, 173, 95, 0, 201, 0, 208, 3, 76, 152, 28, 162, 0, 32, 206, 34, 176, 3, 76, 152, 28, 141, 73, 0, 76, 152, 28, 201, 115, 240, 3, 76, 148, 28, 173, 96, 0, 32, 159, 34, 201, 112, 240, 7, 201, 114, 240, 44, 76, 148, 28, 173, 74, 0, 32, 240, 34, 32, 140, 34, 32, 112, 16, 32, 214, 16, 173, 95, 0, 201, 0, 208, 3, 76, 152, 28, 162, 0, 32, 206, 34, 176, 3, 76, 152, 28, 141, 74, 0, 76, 152, 28, 173, 75, 0, 32, 240, 34, 32, 140, 34, 32, 112, 16, 32, 214, 16, 173, 95, 0, 201, 0, 208, 3, 76, 152, 28, 162, 0, 32, 206, 34, 176, 3, 76, 152, 28, 141, 75, 0, 76, 152, 28, 24, 76, 153, 28, 56, 40, 104, 168, 104, 170, 104, 96, 173, 95, 0, 201, 0, 240, 7, 162, 0, 32, 117, 34, 176, 3, 76, 48, 29, 142, 10, 0, 140, 11, 0, 162, 20, 32, 117, 34, 176, 3, 76, 48, 29, 142, 92, 0, 140, 93, 0, 238, 92, 0, 208, 3, 238, 93, 0, 162, 40, 169, 32, 141, 8, 0, 169, 0, 141, 9, 0, 32, 82, 24, 144, 80, 173, 207, 0, 141, 42, 0, 173, 10, 0, 141, 8, 0, 173, 11, 0, 141, 9, 0, 162, 81, 160, 36, 32, 66, 35, 162, 0, 160, 0, 177, 8, 221, 32, 0, 240, 25, 32, 108, 34, 173, 8, 0, 205, 92, 0, 240, 3, 76, 249, 28, 173, 9, 0, 205, 93, 0, 240, 22, 76, 249, 28, 200, 232, 236, 42, 0, 240, 3, 76, 253, 28, 32, 183, 34, 32, 108, 34, 76, 249, 28, 96, 173, 95, 0, 201, 0, 240, 23, 162, 0, 32, 117, 34, 176, 3, 76, 46, 31, 142, 69, 0, 140, 70, 0, 173, 115, 0, 201, 0, 208, 8, 160, 1, 140, 62, 0, 76, 100, 29, 162, 20, 32, 206, 34, 176, 3, 76, 46, 31, 141, 62, 0, 104, 104, 104, 104, 172, 69, 0, 140, 8, 0, 172, 70, 0, 140, 9, 0, 160, 0, 177, 8, 141, 67, 0, 32, 50, 31, 176, 3, 76, 46, 31, 160, 8, 177, 10, 141, 58, 0, 160, 1, 173, 67, 0, 201, 96, 208, 40, 169, 2, 141, 61, 0, 104, 141, 16, 0, 104, 141, 17, 0, 72, 173, 16, 0, 72, 238, 16, 0, 208, 3, 238, 17, 0, 160, 0, 177, 16, 141, 60, 0, 169, 0, 145, 16, 76, 199, 30, 201, 76, 208, 30, 169, 2, 141, 61, 0, 177, 8, 141, 16, 0, 200, 177, 8, 141, 17, 0, 160, 0, 177, 16, 141, 60, 0, 169, 0, 145, 16, 76, 199, 30, 201, 108, 208, 43, 169, 2, 141, 61, 0, 177, 8, 141, 12, 0, 200, 177, 8, 141, 13, 0, 160, 0, 177, 12, 141, 16, 0, 200, 177, 12, 141, 17, 0, 160, 0, 177, 16, 141, 60, 0, 169, 0, 145, 16, 76, 199, 30, 201, 32, 208, 30, 169, 2, 141, 61, 0, 177, 8, 141, 16, 0, 200, 177, 8, 141, 17, 0, 160, 0, 177, 16, 141, 60, 0, 169, 0, 145, 16, 76, 199, 30, 160, 0, 177, 10, 201, 66, 208, 8, 160, 1, 177, 10, 201, 73, 208, 3, 76, 164, 30, 169, 1, 141, 61, 0, 24, 173, 8, 0, 109, 58, 0, 141, 16, 0, 173, 9, 0, 105, 0, 141, 17, 0, 160, 1, 177, 8, 141, 56, 0, 16, 32, 206, 56, 0, 173, 56, 0, 73, 255, 141, 56, 0, 56, 173, 16, 0, 237, 56, 0, 141, 16, 0, 173, 17, 0, 233, 0, 141, 17, 0, 76, 150, 30, 24, 173, 16, 0, 109, 56, 0, 141, 16, 0, 173, 17, 0, 105, 0, 141, 17, 0, 76, 150, 30, 160, 0, 177, 16, 141, 60, 0, 169, 0, 145, 16, 76, 169, 30, 169, 0, 141, 61, 0, 172, 58, 0, 177, 8, 141, 59, 0, 24, 173, 8, 0, 109, 58, 0, 141, 14, 0, 173, 9, 0, 105, 0, 141, 15, 0, 169, 0, 145, 8, 169, 1, 141, 57, 0, 173, 75, 0, 72, 173, 71, 0, 174, 72, 0, 172, 73, 0, 40, 108, 69, 0, 169, 0, 141, 57, 0, 160, 0, 173, 61, 0, 201, 0, 240, 17, 201, 1, 240, 8, 173, 60, 0, 145, 16, 76, 1, 31, 173, 60, 0, 145, 16, 173, 59, 0, 145, 14, 172, 69, 0, 140, 8, 0, 172, 70, 0, 140, 9, 0, 162, 81, 160, 36, 32, 66, 35, 173, 8, 0, 141, 92, 0, 173, 9, 0, 141, 93, 0, 32, 177, 31, 206, 62, 0, 240, 3, 76, 104, 29, 76, 93, 16, 24, 96, 56, 96, 162, 178, 142, 10, 0, 162, 39, 142, 11, 0
, 160, 4, 177, 10, 201, 90, 240, 28, 160, 7, 177, 10, 205, 67, 0, 240, 17, 173, 10, 0, 24, 105, 10, 141, 10, 0, 144, 3, 238, 11, 0, 76, 60, 31, 56, 96, 24, 96, 173, 95, 0, 201, 0, 240, 23, 162, 0, 32, 117, 34, 176, 3, 76, 86, 34, 142, 8, 0, 140, 9, 0, 173, 115, 0, 201, 0, 208, 33, 173, 8, 0, 141, 92, 0, 173, 9, 0, 141, 93, 0, 24, 173, 8, 0, 105, 20, 141, 92, 0, 144, 7, 174, 93, 0, 232, 142, 93, 0, 76, 177, 31, 162, 20, 32, 117, 34, 176, 3, 76, 86, 34, 142, 92, 0, 140, 93, 0, 173, 93, 0, 205, 9, 0, 240, 5, 144, 14, 76, 204, 31, 173, 92, 0, 205, 8, 0, 144, 3, 76, 204, 31, 76, 88, 34, 160, 0, 177, 8, 141, 67, 0, 32, 50, 31, 176, 29, 162, 81, 160, 36, 32, 66, 35, 32, 183, 34, 173, 67, 0, 32, 240, 34, 162, 14, 160, 37, 32, 66, 35, 32, 108, 34, 76, 177, 31, 162, 81, 160, 36, 32, 66, 35, 32, 183, 34, 32, 108, 34, 160, 6, 177, 10, 201, 80, 208, 3, 76, 79, 32, 201, 77, 208, 3, 76, 118, 32, 201, 83, 208, 3, 76, 169, 32, 201, 88, 208, 3, 76, 169, 32, 201, 89, 208, 3, 76, 169, 32, 201, 82, 208, 3, 76, 198, 33, 201, 76, 208, 3, 76, 19, 33, 201, 67, 208, 3, 76, 96, 32, 201, 68, 208, 3, 76, 8, 34, 201, 73, 208, 3, 76, 132, 33, 76, 216, 31, 173, 67, 0, 32, 240, 34, 160, 8, 32, 148, 34, 32, 90, 34, 76, 177, 31, 173, 67, 0, 32, 240, 34, 160, 8, 32, 148, 34, 32, 90, 34, 169, 65, 32, 155, 35, 76, 177, 31, 160, 0, 173, 67, 0, 32, 240, 34, 169, 32, 32, 155, 35, 177, 8, 141, 65, 0, 32, 240, 34, 32, 108, 34, 160, 5, 32, 148, 34, 32, 90, 34, 169, 35, 32, 155, 35, 173, 65, 0, 32, 240, 34, 169, 104, 32, 155, 35, 76, 177, 31, 160, 0, 173, 67, 0, 32, 240, 34, 169, 32, 32, 155, 35, 177, 8, 141, 65, 0, 32, 240, 34, 32, 108, 34, 169, 32, 32, 155, 35, 177, 8, 141, 66, 0, 32, 240, 34, 32, 108, 34, 160, 2, 32, 148, 34, 32, 90, 34, 173, 66, 0, 32, 240, 34, 173, 65, 0, 32, 240, 34, 169, 104, 32, 155, 35, 160, 6, 177, 10, 201, 88, 240, 7, 201, 89, 240, 16, 76, 177, 31, 169, 44, 32, 155, 35, 169, 88, 32, 155, 35, 76, 177, 31, 169, 44, 32, 155, 35, 169, 89, 32, 155, 35, 76, 177, 31, 160, 0, 173, 67, 0, 32, 240, 34, 169, 32, 32, 155, 35, 177, 8, 141, 65, 0, 32, 240, 34, 32, 108, 34, 160, 5, 32, 148, 34, 32, 90, 34, 173, 8, 0, 141, 63, 0, 173, 9, 0, 141, 64, 0, 173, 65, 0, 48, 18, 173, 63, 0, 24, 109, 65, 0, 141, 63, 0, 144, 32, 238, 64, 0, 76, 112, 33, 206, 65, 0, 173, 65, 0, 73, 255, 141, 65, 0, 173, 63, 0, 56, 237, 65, 0, 141, 63, 0, 176, 3, 206, 64, 0, 173, 64, 0, 32, 240, 34, 173, 63, 0, 32, 240, 34, 169, 104, 32, 155, 35, 76, 177, 31, 160, 0, 173, 67, 0, 32, 240, 34, 169, 32, 32, 155, 35, 177, 8, 141, 65, 0, 32, 240, 34, 32, 108, 34, 160, 5, 32, 148, 34, 32, 90, 34, 169, 40, 32, 155, 35, 173, 65, 0, 32, 240, 34, 169, 104, 32, 155, 35, 169, 41, 32, 155, 35, 169, 44, 32, 155, 35, 169, 89, 32, 155, 35, 76, 177, 31, 160, 0, 173, 67, 0, 32, 240, 34, 169, 32, 32, 155, 35, 177, 8, 141, 65, 0, 32, 240, 34, 32, 108, 34, 160, 5, 32, 148, 34, 32, 90, 34, 169, 40, 32, 155, 35, 173, 65, 0, 32, 240, 34, 169, 104, 32, 155, 35, 169, 44, 32, 155, 35, 169, 88, 32, 155, 35, 169, 41, 32, 155, 35, 76, 177, 31, 160, 0, 173, 67, 0, 32, 240, 34, 169, 32, 32, 155, 35, 177, 8, 141, 65, 0, 32, 240, 34, 32, 108, 34, 169, 32, 32, 155, 35, 177, 8, 141, 66, 0, 32, 240, 34, 32, 108, 34, 160, 2, 32, 148, 34, 32, 90, 34, 169, 40, 32, 155, 35, 173, 66, 0, 32, 240, 34, 173, 65, 0, 32, 240, 34, 169, 104, 32, 155, 35, 169, 41, 32, 155, 35, 76, 177, 31, 24, 96, 56, 96, 160, 0, 177, 10, 32, 155, 35, 200, 192, 3, 208, 246, 169, 32, 32, 155, 35, 96, 238, 8, 0, 208, 3, 238, 9, 0, 96, 32, 206, 34, 176, 3, 76, 138, 34, 168, 232, 32, 206, 34, 176, 3, 76, 138, 34, 170, 56, 96, 24, 96, 162, 9, 160, 37, 32, 66, 35, 96, 72, 169, 32, 32, 155, 35, 136, 208, 248, 104, 96, 201, 65, 48, 7, 201, 91, 16, 3, 24, 105, 32, 96, 201, 97, 48, 7, 201, 123, 16, 3, 56, 233, 32, 96, 173, 9, 0, 32, 240, 34, 173, 8, 0, 32, 240, 34, 169, 32, 32, 155, 35, 169, 32, 32, 155, 35, 96, 189, 95, 0, 32, 46, 26, 144, 25, 10, 10, 10, 10, 41, 240, 141, 156, 0, 232, 189, 95, 0, 32, 46, 26, 144, 7, 13, 156, 0, 141, 156, 0, 56, 96, 141, 156, 0, 74, 74, 74, 74, 41, 15, 201, 10, 16, 5, 9, 48, 76, 6, 35, 233, 9, 9, 64, 32, 155, 35, 173, 156, 0, 41, 15, 201, 10, 16, 5, 9, 48, 76, 27, 35, 233, 9, 9, 64, 32, 155, 35, 173, 156, 0, 96, 169, 230, 141, 4, 0, 169, 17, 141, 5, 0, 169, 0, 141, 57, 0, 169, 0, 141, 47, 0, 141, 48, 0, 141, 49, 0, 141, 50, 0, 169, 0, 96, 142, 6, 0, 140, 7, 0, 160, 0, 177, 6, 240, 14, 32, 155, 35, 238, 6, 0, 208, 3, 238, 7, 0, 76, 74, 35, 96, 8, 138, 72, 152, 72, 173, 5, 64, 41, 8, 240, 9, 173, 4, 64, 141, 206, 0, 76, 119, 35, 169, 0, 141, 206, 0, 104, 168, 104, 170, 40, 173, 206, 0, 96, 8, 138, 72, 152, 72, 173, 5, 64, 41, 8, 240, 249, 173, 4, 64, 141, 206, 0, 104, 168, 104, 170, 40, 173, 206, 0, 96, 141, 206, 0, 8, 152, 72, 138, 72, 173, 5, 64, 41, 16, 240, 249, 173, 206, 0, 141, 4, 64, 104, 170, 104, 168, 40, 173, 206, 0, 96, 72, 138, 72, 152, 72, 169, 0, 141, 162, 0, 169, 16, 141, 163, 0, 206, 162, 0, 208, 251, 206, 163, 0, 208, 246, 104, 168, 104, 170, 104, 96, 22, 11, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 0, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102, 0, 10, 13, 10, 85, 77, 79, 78, 54, 53, 32, 86, 49, 46, 49, 49, 97, 32, 45, 32, 85, 110, 100, 101, 114, 115, 116, 97, 110, 100, 97, 98, 108, 101, 32, 77, 111, 110, 105, 116, 111, 114, 32, 102, 111, 114, 32, 116, 104, 101, 32, 54, 53, 48, 48, 32, 115, 101, 114, 105, 101, 115, 32, 109, 105, 99, 114, 111, 112, 114, 111, 99, 101, 115, 115, 111, 114, 115, 46, 10, 13, 0, 10, 13, 45, 0, 10, 13, 0, 10, 13, 83, 101, 110, 100, 32, 83, 32, 114, 101, 99, 111, 114, 100, 115, 32, 119, 104, 101, 110, 32, 121, 111, 117, 32, 97, 114, 101, 32, 114, 101, 97, 100, 121, 46, 46, 46, 10, 13, 0, 10, 13, 7, 69, 114, 114, 111, 114, 32, 76, 111, 97, 100, 105, 110, 103, 32, 83, 32, 82, 101, 99, 111, 114, 100, 115, 46, 46, 46, 10, 13, 0, 10, 13, 7, 83, 32, 114, 101, 99, 111, 114, 100, 115, 32, 115, 117, 99, 99, 101, 115, 115, 102, 117, 108, 108, 121, 32, 108, 111, 97, 100, 101, 100, 46, 0, 10, 13, 10, 80, 103, 109, 67, 110, 116, 114, 40, 80, 67, 41, 32, 32, 65, 99, 99, 117, 109, 40, 65, 67, 41, 32, 32, 88, 82, 101, 103, 40, 88, 82, 41, 32, 32, 89, 82, 101, 103, 40, 89, 82, 41, 32, 32, 83, 116, 107, 80, 116, 114, 40, 83, 80, 41, 32, 32, 78, 86, 45, 66, 68, 73, 90, 67, 40, 83, 82, 41, 10, 13, 0, 10, 13, 32, 58, 0, 32, 32, 32, 32, 32, 32, 32, 32, 63, 63, 63, 0, 10, 13, 65, 108, 108, 32, 98, 114, 101, 97, 107, 112, 111, 105, 110, 116, 115, 32, 97, 114, 101, 32, 99, 117, 114, 114, 101, 110, 116, 108, 121, 32, 105, 110, 32, 117, 115, 101, 46, 0, 10, 13, 78, 111, 32, 98, 114, 101, 97, 107, 112, 111, 105, 110, 116, 32, 101, 120, 105, 115, 116, 115, 32, 97, 116, 32, 116, 104, 105, 115, 32, 97, 100, 100, 114, 101, 115, 115, 46, 0, 10, 13, 89, 111, 117, 32, 99, 97, 110, 110, 111, 116, 32, 71, 79, 32, 97, 116, 32, 97, 32, 98, 114, 101, 97, 107, 112, 111, 105, 110, 116, 101, 100, 32, 97, 100, 100, 114, 101, 115, 115, 44, 32, 84, 82, 65, 67, 69, 32, 112, 97, 115, 116, 32, 105, 116, 32, 116, 104, 101, 110, 32, 71, 79, 46, 0, 10, 13, 63, 0, 10, 13, 10, 65, 115, 115, 101, 109, 98, 108, 101, 32, 32, 32, 32, 32, 32, 32, 65, 32, 115, 116, 97, 114, 116, 95, 97, 100, 100, 114, 101, 115, 115, 10, 13, 66, 114, 101, 97, 107, 112, 111, 105, 110, 116, 32, 32, 32, 32, 32, 66, 32, 40, 43, 44, 45, 44, 63, 41, 32, 97, 100, 100, 114, 101, 115, 115, 10, 13, 68, 117, 109, 112, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 68, 32, 91, 115, 116, 97, 114, 116, 95, 97, 100, 100, 114, 101, 115, 115, 32, 91, 101, 110, 100, 95, 97, 100, 100, 114, 101, 115, 115, 93, 93, 10, 13, 69, 110, 116, 101, 114, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 69, 32, 97, 100, 100, 114, 101, 115, 115, 32, 108, 105, 115, 116, 10, 13, 70, 105, 108, 108, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 70, 32, 115, 116, 97, 114, 116, 95, 97, 100, 100, 114, 101, 115, 115, 32, 101, 110, 100, 95, 97, 100, 100, 114, 101, 115, 115, 32, 108, 105, 115, 116, 10, 13, 71, 111, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 71, 32, 91, 115, 116, 97, 114, 116, 95, 97, 100, 100, 114, 101, 115, 115, 93, 10, 13, 72, 101, 108, 112, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 72, 32, 111, 114, 32, 63, 10, 13, 76, 111, 97, 100, 32, 32, 32, 32, 32, 32
, 32, 32, 32, 32, 32, 76, 10, 13, 77, 111, 118, 101, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 77, 32, 115, 116, 97, 114, 116, 95, 97, 100, 100, 114, 101, 115, 115, 32, 101, 110, 100, 95, 97, 100, 100, 114, 101, 115, 115, 32, 100, 101, 115, 116, 105, 110, 97, 116, 105, 111, 110, 95, 97, 100, 100, 114, 101, 115, 115, 10, 13, 82, 101, 103, 105, 115, 116, 101, 114, 32, 32, 32, 32, 32, 32, 32, 82, 32, 91, 80, 67, 44, 65, 67, 44, 88, 82, 44, 89, 82, 44, 83, 80, 44, 83, 82, 93, 10, 13, 83, 101, 97, 114, 99, 104, 32, 32, 32, 32, 32, 32, 32, 32, 32, 83, 32, 115, 116, 97, 114, 116, 95, 97, 100, 100, 114, 101, 115, 115, 32, 101, 110, 100, 95, 97, 100, 100, 114, 101, 115, 115, 32, 108, 105, 115, 116, 10, 13, 84, 114, 97, 99, 101, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 84, 32, 91, 115, 116, 97, 114, 116, 95, 97, 100, 100, 114, 101, 115, 115, 32, 91, 118, 97, 108, 117, 101, 93, 93, 10, 13, 85, 110, 97, 115, 115, 101, 109, 98, 108, 101, 32, 32, 32, 32, 32, 85, 32, 91, 115, 116, 97, 114, 116, 95, 97, 100, 100, 114, 101, 115, 115, 32, 91, 101, 110, 100, 95, 97, 100, 100, 114, 101, 115, 115, 93, 93, 10, 13, 0, 65, 68, 67, 32, 73, 77, 77, 105, 2, 2, 65, 68, 67, 32, 65, 66, 83, 109, 3, 4, 65, 68, 67, 32, 65, 66, 88, 125, 3, 4, 65, 68, 67, 32, 65, 66, 89, 121, 3, 4, 65, 68, 67, 32, 73, 73, 82, 97, 2, 6, 65, 68, 67, 32, 73, 82, 73, 113, 2, 5, 65, 78, 68, 32, 73, 77, 77, 41, 2, 2, 65, 78, 68, 32, 65, 66, 83, 45, 3, 4, 65, 78, 68, 32, 65, 66, 88, 61, 3, 4, 65, 78, 68, 32, 65, 66, 89, 57, 3, 4, 65, 78, 68, 32, 73, 73, 82, 33, 2, 6, 65, 78, 68, 32, 73, 82, 73, 49, 2, 5, 65, 83, 76, 32, 65, 67, 67, 10, 1, 2, 65, 83, 76, 32, 65, 66, 83, 14, 3, 6, 65, 83, 76, 32, 65, 66, 88, 30, 3, 7, 66, 67, 67, 32, 82, 69, 76, 144, 2, 2, 66, 67, 83, 32, 82, 69, 76, 176, 2, 2, 66, 69, 81, 32, 82, 69, 76, 240, 2, 2, 66, 73, 84, 32, 65, 66, 83, 44, 3, 4, 66, 77, 73, 32, 82, 69, 76, 48, 2, 2, 66, 78, 69, 32, 82, 69, 76, 208, 2, 2, 66, 80, 76, 32, 82, 69, 76, 16, 2, 2, 66, 82, 75, 32, 73, 77, 80, 0, 1, 7, 66, 86, 67, 32, 82, 69, 76, 80, 2, 2, 66, 86, 83, 32, 82, 69, 76, 112, 2, 2, 67, 76, 67, 32, 73, 77, 80, 24, 1, 2, 67, 76, 68, 32, 73, 77, 80, 216, 1, 2, 67, 76, 73, 32, 73, 77, 80, 88, 1, 2, 67, 76, 86, 32, 73, 77, 80, 184, 1, 2, 67, 77, 80, 32, 73, 77, 77, 201, 2, 2, 67, 77, 80, 32, 65, 66, 83, 205, 3, 4, 67, 77, 80, 32, 65, 66, 88, 221, 3, 4, 67, 77, 80, 32, 65, 66, 89, 217, 3, 4, 67, 77, 80, 32, 73, 73, 82, 193, 2, 6, 67, 77, 80, 32, 73, 82, 73, 209, 2, 5, 67, 80, 88, 32, 73, 77, 77, 224, 2, 2, 67, 80, 88, 32, 65, 66, 83, 236, 3, 4, 67, 80, 89, 32, 73, 77, 77, 192, 2, 2, 67, 80, 89, 32, 65, 66, 83, 204, 3, 4, 68, 69, 67, 32, 65, 66, 83, 206, 3, 6, 68, 69, 67, 32, 65, 66, 88, 222, 3, 7, 68, 69, 88, 32, 73, 77, 80, 202, 1, 2, 68, 69, 89, 32, 73, 77, 80, 136, 1, 2, 69, 79, 82, 32, 73, 77, 77, 73, 2, 2, 69, 79, 82, 32, 65, 66, 83, 77, 3, 4, 69, 79, 82, 32, 65, 66, 88, 93, 3, 4, 69, 79, 82, 32, 65, 66, 89, 89, 3, 4, 69, 79, 82, 32, 73, 73, 82, 65, 2, 6, 69, 79, 82, 32, 73, 82, 73, 81, 2, 5, 73, 78, 67, 32, 65, 66, 83, 238, 3, 6, 73, 78, 67, 32, 65, 66, 88, 254, 3, 7, 73, 78, 88, 32, 73, 77, 80, 232, 1, 2, 73, 78, 89, 32, 73, 77, 80, 200, 1, 2, 74, 77, 80, 32, 65, 66, 83, 76, 3, 3, 74, 77, 80, 32, 73, 78, 68, 108, 3, 5, 74, 83, 82, 32, 65, 66, 83, 32, 3, 6, 76, 68, 65, 32, 73, 77, 77, 169, 2, 2, 76, 68, 65, 32, 65, 66, 83, 173, 3, 4, 76, 68, 65, 32, 65, 66, 88, 189, 3, 4, 76, 68, 65, 32, 65, 66, 89, 185, 3, 4, 76, 68, 65, 32, 73, 73, 82, 161, 2, 6, 76, 68, 65, 32, 73, 82, 73, 177, 2, 5, 76, 68, 88, 32, 73, 77, 77, 162, 2, 2, 76, 68, 88, 32, 65, 66, 83, 174, 3, 4, 76, 68, 88, 32, 65, 66, 89, 190, 3, 4, 76, 68, 89, 32, 73, 77, 77, 160, 2, 2, 76, 68, 89, 32, 65, 66, 83, 172, 3, 4, 76, 68, 89, 32, 65, 66, 88, 188, 3, 4, 76, 83, 82, 32, 65, 67, 67, 74, 1, 2, 76, 83, 82, 32, 65, 66, 83, 78, 3, 6, 76, 83, 82, 32, 65, 66, 88, 94, 3, 7, 78, 79, 80, 32, 73, 77, 80, 234, 1, 2, 79, 82, 65, 32, 73, 77, 77, 9, 2, 2, 79, 82, 65, 32, 65, 66, 83, 13, 3, 4, 79, 82, 65, 32, 65, 66, 88, 29, 3, 4, 79, 82, 65, 32, 65, 66, 89, 25, 3, 4, 79, 82, 65, 32, 73, 73, 82, 1, 2, 6, 79, 82, 65, 32, 73, 82, 73, 17, 2, 5, 80, 72, 65, 32, 73, 77, 80, 72, 1, 3, 80, 72, 80, 32, 73, 77, 80, 8, 1, 3, 80, 76, 65, 32, 73, 77, 80, 104, 1, 4, 80, 76, 80, 32, 73, 77, 80, 40, 1, 4, 82, 79, 76, 32, 65, 67, 67, 42, 1, 2, 82, 79, 76, 32, 65, 66, 83, 46, 3, 6, 82, 79, 76, 32, 65, 66, 88, 62, 3, 7, 82, 79, 82, 32, 65, 67, 67, 106, 1, 2, 82, 79, 82, 32, 65, 66, 83, 110, 3, 6, 82, 79, 82, 32, 65, 66, 88, 126, 3, 7, 82, 84, 73, 32, 73, 77, 80, 64, 1, 6, 82, 84, 83, 32, 73, 77, 80, 96, 1, 6, 83, 66, 67, 32, 73, 77, 77, 233, 2, 2, 83, 66, 67, 32, 65, 66, 83, 237, 3, 4, 83, 66, 67, 32, 65, 66, 88, 253, 3, 4, 83, 66, 67, 32, 65, 66, 89, 249, 3, 4, 83, 66, 67, 32, 73, 73, 82, 225, 2, 6, 83, 66, 67, 32, 73, 82, 73, 241, 2, 5, 83, 69, 67, 32, 73, 77, 80, 56, 1, 2, 83, 69, 68, 32, 73, 77, 80, 248, 1, 2, 83, 69, 73, 32, 73, 77, 80, 120, 1, 2, 83, 84, 65, 32, 65, 66, 83, 141, 3, 4, 83, 84, 65, 32, 65, 66, 88, 157, 3, 5, 83, 84, 65, 32, 65, 66, 89, 153, 3, 5, 83, 84, 65, 32, 73, 73, 82, 129, 2, 6, 83, 84, 65, 32, 73, 82, 73, 145, 2, 6, 83, 84, 88, 32, 65, 66, 83, 142, 3, 4, 83, 84, 89, 32, 65, 66, 83, 140, 3, 4, 84, 65, 88, 32, 73, 77, 80, 170, 1, 2, 84, 65, 89, 32, 73, 77, 80, 168, 1, 2, 84, 83, 88, 32, 73, 77, 80, 186, 1, 2, 84, 88, 65, 32, 73, 77, 80, 138, 1, 2, 84, 88, 83, 32, 73, 77, 80, 154, 1, 2, 84, 89, 65, 32, 73, 77, 80, 152, 1, 2, 90, 90, 90, 32, 90, 90, 90, 0, 0, 0]
*/

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
