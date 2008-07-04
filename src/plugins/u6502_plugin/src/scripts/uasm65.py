#!/usr/bin/python -O

#Notes: 
#+Add error detection to number conversions (like in org, etc.).
#+Don't use end directive anymore.
#+Make all labels end with a colon (:).


import re
import os
import sys

# Do the right thing with boolean values for all known Python versions.
try:
	True, False
except NameError:
	(True, False) = (1, 0)
  

class UASM65:
	
	def __init__(self, asmfile):
		self.asmfile = asmfile
		
#{{{ variables
		#python variables.
		self.optable = {}
		self.symboltable = {}
		self.matchnumber = re.compile( r"^[; \t]*[\n]")
		self.matchlabel = re.compile( r"^[a-zA-Z][a-zA-Z0-9_\$]*")
		self.match_immediate_mode = re.compile( r"#[a-fA-F0-9]+[bdh<>]")
		self.match_absolute_mode = re.compile( r"")
		self.match_absolute_indexed_mode = re.compile( r"^[; \t]*[\n]")
		self.match_accumulator_mode = re.compile( r"[aA]")
		self.match_indirect_indexed_mode = re.compile( r"^[; \t]*[\n]")
		self.match_indexed_indirect_mode = re.compile( r"^[; \t]*[\n]")
		self.match_indirect_mode = re.compile( r"^[; \t]*[\n]")

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
		self.error_messages = [
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
		
		self.initialize_operator_table()
	
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
	
	
#{{{ log_error

#{{{ convert_to_number
	def convert_to_number(self, number_str):
		result = True;
		try:
			if number_str[-1] == 'b':
				number = int( self.hold_operand[0:-1], 2 )
			elif number_str[-1] == 'd':
				number = int( self.hold_operand[0:-1], 10 )
			elif number_str[-1] == 'h':
				number = int( self.hold_operand[0:-1], 16 )
			else:
				self.log_error(24) #Missing number base indicator.
				result = False
		except ValuError:
			self.log_error(12)
			result = False
		return result,number
#}}}

#{{{log_error
	def log_error(self, error_number):
		print "ERROR:",self.error_messages[error_number]
#}}}

#{{{print_line
	def print_line(self):
		pass
#}}}
	
#{{{ pass1
	def pass1(self):
		
		self.pass2_flag = 0
		self.lst_flag = 1
		#strcpy(symbol_table[1].label,"XXX");
		
		#Open lst file.
		lst_filename = source_filename.split('.')[0]
		lst_filename += ".lst"
		self.lst_file_ptr = open( lst_filename,'w' )
		
		
		lines = asmfile.readlines()
		#print lines
		
		for line in lines:
			print "------------------------------------------------------------"
			
			if self.matchnumber.search( line ) != None :
				pass
			else:
				print line,
				
				parse = line.split()
				#If the line does not start with a label, insert an empty string at position 0.
				if not( ( line[0] >= 'A' and line[0] <= 'Z' ) or ( line[0] >= 'a' and line[0] <= 'z' )):
					parse.insert(0,'')
				
				print "IIIIIIIIIIII",len(parse)
				if len(parse) <= 2:
					self.hold_operand = ""
				else:
					self.hold_operand = parse[2].lower()
				
				
				
				print "\nParse:",parse
					
				if len(parse) >= 2: # This line has more than
					
					self.hold_operator = parse[1].lower()
		
					if self.hold_operator == 'lon':
						self.lst_flag = True
					elif self.hold_operator == 'lof':
						self.lst_flag = False
					elif self.hold_operator == 'org':
						(result,temp_converted_number) = self.convert_to_number(self.hold_operand)
						if (result == True):
							if temp_converted_number >= 0 and temp_converted_number <= 65535:
								self.location_counter = temp_converted_number
								self.no_obj_flag = 1
								self.print_line()
								return(True)
							else:
								self.log_error(8)
								return(False)
						else:
							self.log_error(16)
							return(False)

					
					elif self.hold_operator == '*':
						pass
					elif self.hold_operator == 'equ':
						self.no_obj_flag = True
						print_line();
					elif self.hold_operator == '*':
						pass
					elif self.hold_operator == 'dbt':
						if '"' in self.hold_operand:
							self.process_ascii_characters()
						elif '(' in self.hold_operand and ')' in self.hold_operand:
							self.process_byte_duplicates()
						elif ',' in self.hold_operand:
							self.process_bytes()
						elif '?' in self.hold_operand:
							self.hold_obj_1 = 0
							self.print_line()
							self.location_counter += 1
						elif ('d' in self.hold_operand or 'b' in self.hold_operator or 'h' in self.hold_operator) and self.hold_operator[0] == '#':
							self.local_index = 0 #Note?
							self.operand_index = 0 #Note ?
							ascii_hold = 
					
					elif parse[0] != '':
						label = parse[0]
						if self.matchlabel.search( label ) == None:
							self.log_error(1) #Invalid character in label;
							continue;
							
						self.symboltable[ label ] = self.location_counter
					


					else: #Determine addressing mode of operator.
						try:
							operator_data = self.optable[self.hold_operator]
						except KeyError:
							self.log_error(17)
							continue
						
						if self.hold_operator in ["bcc","bcs","beq","bmi","bne","bne","bpl","bvc","bvs"]:
							self.location_counter += 2
							continue
							
						elif self.match_immediate_mode.search( self.hold_operand ) != None:
							self.location_counter += 2
							continue
							
						elif self.match_accumulator_mode.search( self.hold_operand ) != None:
							self.location_counter += 1
							continue
						

#						#addressing_mode = get_addressing_mode(self.hold_operand)
#						
#						if self.hold_operator == 'jmp':
#							
#							self.hold_operand = parse[2]
#							
#						
#							if self.hold_operand.startswith( '(' ):
#								addressingmode = 'ind'
#							else:
#								addressingmode = 'abs'
#							
#						
#							byte = self.optable[self.hold_operator][addressingmode]['bytes']
#						
#							self.location_counter = self.location_counter + byte
#							
#						print "\nOperator:", self.hold_operator, "Operand:", self.hold_operand

			
		self.lst_file_ptr.close()
		
		print "Symbol table:",self.symboltable
		return 1
		
		
	#}}}
	
	
	def get_addressing_mode(self,hold_operand):
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
	pass

	#create_sym_file()
	#		
	#if pass2():
	#	convert_sr_to_ascii()
	#	fclose(source_file_pointer);
	#	fclose(lst_file_ptr);
    #
	
asmfile.close()
	
	
					



	
	
# :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=custom:collapseFolds=1:

