#!/usr/bin/python -O

import re
import os
import sys

class UASM65:
	
	def __init__(self, asmfile):
		self.asmfile = asmfile
		
#{{{ variables
		#python variables.
		self.optable = {}
		self.symboltable = {}
		self.matchnumber = re.compile( r"^\t*[\n|;]") 

		self.inputStream = None

		self.error = "none"

		self.line_character = None
		self.temp_add_mode = None
		self.lst_filename = ""                                       
		self.source_line = None
		self.hold_label = None
		self.hold_operator = None
		self.hold_operand = None
		self.hold_add_mode = None

		self.line_index = None
		self.sym_tbl_index = None
		self.x = None
		self.op_tbl_index = None
		self.end_flag = None

		self.error_index = None
		self.source_line_number = None
		self.return_code = None
		self.symbol_index = None
		self.location_counter = None #
		self.hold_location = None
		self.temp_converted_number = None

		self.source_file_pointer = None
		self.lst_file_ptr = None

		self.no_source_flag = None
		self.no_lc_flag = None
		self.no_obj_flag = None
		self.hold_obj_1 = None
		self.hold_obj_2 = None
		self.hold_obj_3 = None

		self.no_line_num_flag = None
		self.pass2_flag = None
		self.print_error_index = None
		self.lst_flag = None
		self.s_record_index = None
		self.code_index = None
		self.record_length = None
		self.source_file_name = ""
		self.operand_index = None

		self.EOF = -1
		
		#}}}
		
#{{{ error messages
		error_messages = [
		"",
		"Invalid Character in label.",
		"Symbol not defined.",
		"Missing END directive or no CR after END.",
		"Redefinition of a symbol.",
		"",
		"Operand expected.",
		"Operator expected.",
		"Value out of range.",
		"Branch out of range.",
		"",
		"",
		"Illegal digit in number.",
		"Empty string not allowed",
		"",
		"",
		"Invalid operand.",
		"Invalid operator.",
		"Label too long.",
		"Invalid character in operator.",
		"",
		"Invalid character in operand.",
		"Operand too long.",
		"Missing trailing quote mark.",
		"Missing number base indicator.",
		"",
		"",
		"",
		"",
		"",
		"Invalid addressing mode for operator.",
		"",
		"Empty character not allowed."
		]
		#}}}
		
	
#{{{ Initialize operator table.
	def initialize_operator_table(self):
		#optablefile = open( dirpath + "op_table.dat",'r' )
		#optablestring = optablefile.read()
		optablestring = """
LON IMP 0 0 0
LOF IMP 0 0 0
ORG DIR	0 0 0
*   IMP 0 0 0
EQU DIR 0 0 0
DBT DIR 0 0 0
DWD DIR	0 0 0
END IMP 0 0 0
ADC IMM 69 2 2
ADC ABS 6D 3 4
ADC ABX 7D 3 4
ADC ABY 79 3 4
ADC IXR 61 2 6
ADC IRX 71 2 5
AND IMM 29 2 2
AND ABS 2D 3 4
AND ABX 3D 3 4
AND ABY 39 3 4
AND IXR 21 2 6
AND IRX 31 2 5
ASL ACC 0A 1 2
ASL ABS 0E 3 6
ASL ABX 1E 3 7
BCC REL 90 2 2
BCS REL B0 2 2
BEQ REL F0 2 2
BIT ABS 2C 3 4
BMI REL 30 2 2
BNE REL D0 2 2
BPL REL 10 2 2
BRK IMP 00 1 7
BVC REL 50 2 2
BVS REL 70 2 2
CLC IMP 18 1 2
CLD IMP D8 1 2
CLI IMP 58 1 2
CLV IMP B8 1 2
CMP IMM C9 2 2
CMP ABS CD 3 4
CMP ABX DD 3 4
CMP ABY D9 3 4
CMP IXR C1 2 6
CMP IRX D1 2 5
CPX IMM E0 2 2
CPX ABS EC 3 4
CPY IMM C0 2 2
CPY ABS CC 3 4
DEC ABS CE 3 6
DEC ABX DE 3 7
DEX IMP CA 1 2
DEY IMP 88 1 2
EOR IMM 49 2 2
EOR ABS 4D 3 4
EOR ABX 5D 3 4
EOR ABY 59 3 4
EOR IXR 41 2 6
EOR IRX 51 2 5
INC ABS EE 3 6
INC ABX FE 3 7
INX IMP E8 1 2
INY IMP C8 1 2
JMP ABS 4C 3 3
JMP IND 6C 3 5
JSR ABS 20 3 6
LDA IMM A9 2 2
LDA ABS AD 3 4
LDA ABX BD 3 4
LDA ABY B9 3 4
LDA IXR A1 2 6
LDA IRX B1 2 5
LDX IMM A2 2 2
LDX ABS AE 3 4
LDX ABY BE 3 4
LDY IMM A0 2 2
LDY ABS AC 3 4
LDY ABX BC 3 4
LSR ACC 4A 1 2
LSR ABS 4E 3 6
LSR ABX 5E 3 7
NOP IMP EA 1 2
ORA IMM 09 2 2
ORA ABS 0D 3 4
ORA ABX 1D 3 4
ORA ABY 19 3 4
ORA IXR 01 2 6
ORA IRX 11 2 5
PHA IMP 48 1 3
PHP IMP 08 1 3
PLA IMP 68 1 4
PLP IMP 28 1 4
ROL ACC 2A 1 2
ROL ABS 2E 3 6
ROL ABX 3E 3 7
ROR ACC 6A 1 2
ROR ABS 6E 3 6
ROR ABX 7E 3 7
RTI IMP 40 1 6
RTS IMP 60 1 6
SBC IMM E9 2 2
SBC ABS ED 3 4
SBC ABX FD 3 4
SBC ABY F9 3 4
SBC IXR E1 2 6
SBC IRX F1 2 5
SEC IMP 38 1 2
SED IMP F8 1 2
SEI IMP 78 1 2
STA ABS 8D 3 4
STA ABX 9D 3 5
STA ABY 99 3 5
STA IXR 81 2 6
STA IRX 91 2 6
STX ABS 8E 3 4
STY ABS 8C 3 4
TAX IMP AA 1 2
TAY IMP A8 1 2
TSX IMP BA 1 2
TXA IMP 8A 1 2
TXS IMP 9A 1 2
TYA IMP 98 1 2
XXX XXX 00 0 0
"""

		
		optablelist = optablestring.splitlines()
		optablelist = optablelist[1:]
		for line in optablelist:
		
			parse = line.split()
			
			operatorstring = parse[0].lower()
			addmodestring = parse[1].lower()
			opcodestring = parse[2]
			bytestring = parse[3]
			
			if operatorstring in self.optable.keys():
				addmode = self.optable[ operatorstring ]
				addmode[ addmodestring] = {"opcode":opcodestring, "bytes": int(bytestring) }
			else:
				addmode = { addmodestring: {"opcode":opcodestring, "bytes": int(bytestring) } }
				self.optable[ operatorstring ] = addmode
				
	#}}}
	
	
#{{{ initialize
	def initialize(self):
		
		initialize_operator_table()
	
		self.lst_flag=1
		self.print_error_index = 1
		self.s_record_index = 0
		self.code_index=0
		self.location_counter = 0 #
		self.error_index = 0
		self.sym_tbl_index=0
		self.source_line_number=0
		self.end_flag=0
		self.line_index = 0
		self.no_source_flag = 0
		self.no_lc_flag = 0
		self.no_obj_flag = 0
		self.no_line_num_flag = 0
		self.hold_obj_1=-1
		self.hold_obj_2=-1
		self.hold_obj_3=-1
		
	
		#wipe_line();
	#error_table[0].line_number = 0; Note: might need to add replacement code here. 
	#}}}
	
	
	
	
#{{{ pass1
	def pass1(self):    
		pass2_flag = 0
		lst_flag = 1
		#strcpy(symbol_table[1].label,"XXX");
		
		#Open lst file.
		lst_filename = source_filename.split('.')[0]
		lst_filename += ".lst"
		self.lst_file_ptr = open( lst_filename,'w' )
		
		
		lines = asmfile.readlines()
		print lines
		
		for line in lines:
			
			if ( not line.startswith( ';' )) and (matchnumber.search( line )== None ): #Skip blank lines.
				print line,
				
				parse = line.split()
				
				
				#If the line does not start with a label, insert an empty string at position 0.
				if not( ( line[0] >= 'A' and line[0] <= 'Z' ) or ( line[0] >= 'a' and line[0] <= 'z' )):
					parse.insert(0,'')
				
				#print parse
					
				if len(parse) >= 2:
					
		
					operator = parse[1].lower()
		
					if operator == 'org':
						programcounter = int( parse[2][0:-1], 16 )
						#print programcounter
						
					elif parse[0] != '':
						symboltable[ parse[0] ] = programcounter
					
					elif operator == 'dwd':
						programcounter = programcounter + 2
					elif operator == 'dbt':
						programcounter = programcounter + 1
					elif operator == 'equ':
						pass
					else:
						
						label = parse[0].lower()
						
						if len(parse) == 2:
							operand = None
						else:
							operand = parse[2].lower()
						
						#addressing_mode = get_addressing_mode(operand)
						
						if operator == 'jmp':
							
							operand = parse[2]
							
						
							if operand.startswith( '(' ):
								addressingmode = 'ind'
							else:
								addressingmode = 'abs'
							
						
							byte = self.optable[operator][addressingmode]['bytes']
						
							programcounter = programcounter + byte
							
						print "\nOperator:", operator, "Operand:", operand
		self.lst_file_ptr.close()
		return 1
		
		
	#}}}
	
	
	def get_addressing_mode(self,operand):
		pass
	
	
	


#dirpath = ""
#dirpath = os.getcwd()

#source_filename = sys.argv[-1]
source_filename="/ted/checkouts/mathrider/src/plugins/u6502_plugin/src/scripts/test.asm"

#Note: add source filename checking code here.

asmfile = open( source_filename,'r' )

asem = UASM65(asmfile)
asem.initialize()



if asem.pass1():
	

	#create_sym_file()
	#		
	#if pass2():
	#	convert_sr_to_ascii()
	#	fclose(source_file_pointer);
	#	fclose(lst_file_ptr);
    #
	
	asmfile.close()
	
	
					



	
	
# :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=custom:collapseFolds=1:

