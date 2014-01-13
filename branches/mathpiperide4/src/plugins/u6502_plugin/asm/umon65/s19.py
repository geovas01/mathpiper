#!/usr/bin/python -O

# python s19.py umon65.s19

import os
import sys

#dirpath = "/tmp/6502Em/asm/"
dirpath = os.getcwd()

filename = sys.argv[-1]
#filename="umon65muvium.s19"

s19file = open( dirpath +"/"+ filename,'r' )

	
lines = s19file.readlines()

bytes = []

startaddress = ""
firstaddress = 1

for line in lines:
	#print line
	
	if line[0] != '\n':
	
		recordtype = line[1]
		bytecount = int( line[2:4], 16 )
		
		if recordtype == "1" and bytecount > 3:
			
		
			if firstaddress == 1:
				startaddress = line[4:8]   
				firstaddress = 0
		
			index = 8
			count = 0
			while count <= bytecount-3-1:
				byte = line[index:index+2]
				#print byte	

				bytes.extend( str(int(byte, 16)) + ",");
				index = index + 2
				count = count + 1
		

        	
#Remove last comma.
del bytes[-1]



hexsourcefile = open( dirpath +"/" + "HexSource.java",'w' )




hexsourcefile.write("".join( bytes ) + "};")


			
			
hexsourcefile.close()

print "HexSource.java file created"

