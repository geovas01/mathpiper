#!/usr/bin/python -O

import re
import os
import sys

matchnumber = re.compile( r"^\t*[\n|;]") 

dirpath = ""
#dirpath = os.getcwd()

#filename = sys.argv[-1]
filename="umon65.asm"

asmfile = open( 'umon65.asm','r' )

programcounter = 0
symboltable = {}
optable = {}


optablefile = open( dirpath + "op_table.dat",'r' )
optablestring = optablefile.read()
optablefile.close()

optablelist = optablestring.splitlines()
for line in optablelist:
	parse = line.split()
	
	operatorstring = parse[0].lower()
	addmodestring = parse[1].lower()
	opcodestring = parse[2]
	bytestring = parse[3]
	
	if operatorstring in optable.keys():
		addmode = optable[ operatorstring ]
		addmode[ addmodestring] = {"opcode":opcodestring, "bytes": int(bytestring) }
	else:
		addmode = { addmodestring: {"opcode":opcodestring, "bytes": int(bytestring) } }
		optable[ operatorstring ] = addmode


	
lines = asmfile.readlines()

for line in lines:
	
	if ( not line.startswith( ';' )) and (matchnumber.search( line )== None ):
		#print line,
		
		parse = line.split()
		if not( ( line[0] >= 'A' and line[0] <= 'Z' ) or ( line[0] >= 'a' and line[0] <= 'z' )):
			parse.insert(0,'')
		
		print parse
		
		if len(parse) >= 2:
			operator = parse[1]
			if operator == 'org':
				programcounter = int( parse[2][0:-1], 16 )
				print programcounter
				
			elif parse[0] != '':
				symboltable[ parse[0] ] = programcounter
			
			elif operator == 'dwd':
				programcounter = programcounter + 2
			elif operator == 'dbt':
				programcounter = programcounter + 1
			else:
				
				if operator == 'jmp':
					
					operand = parse[2]
					
				
					if operand.startswith( '(' ):
						addressingmode = 'ind'
					else:
						addressingmode = 'abs'
					
				
					byte = optable[operator][addressingmode]['bytes']
				
					programcounter = programcounter + byte
			

