import unittest
import org.mathrider.u6502.EMU6502 as EMU6502
import org.mathrider.u6502.UASM65 as UASM65
import java.lang.System as System

class TestEMU6502(unittest.TestCase): #junit.framework.TestCase, 
	def setUp(self):
		"Obtain an instance of the emulator."
		self.emu = EMU6502()
		self.asm = UASM65()
		

		
		
	def NtestLDA(self):
		
#---------------------------------------------------
		mode = "LDA immediate mode."
		source_code = r""" 
	org e000h
	lda #03d
	brk
	
	end
    	
"""		
		self.runasm(source_code)
		self.assertEqual(self.emu.a, 3, mode)
		self.assertEqual(self.emu.z, 0, mode)
		
#---------------------------------------------------
		mode = "LDA absolute mode."
		source_code = r""" 
	org e000h
	lda data
	brk
	
data dbt 04d
	end
    	
"""
		self.runasm(source_code)
		self.assertEqual(self.emu.a, 4, mode)
		self.assertEqual(self.emu.z, 0, mode)
		
#---------------------------------------------------
		mode = "LDA absolute x mode."
		source_code = r""" 
	org e000h
	ldx #1d
	lda data,x
	brk
	
data dbt 00d,05d
	end
    	
"""
		self.runasm(source_code)
		self.assertEqual(self.emu.a, 5, mode)
		self.assertEqual(self.emu.z, 0, mode)
		
#---------------------------------------------------
		mode = "LDA absolute y mode."
		source_code = r""" 
	org e000h
	ldy #1d
	lda data,y
	brk
	
data dbt 00d,05d
	end
    	
"""
		self.runasm(source_code)
		self.assertEqual(self.emu.a, 5, mode)
		self.assertEqual(self.emu.z, 0, mode)		
		
#---------------------------------------------------
		mode = "LDA absolute y mode."
		source_code = r""" 
	org e000h
	ldy #1d
	lda data,y
	brk
	
data dbt 00d,05d
	end
    	
"""
		self.runasm(source_code)
		self.assertEqual(self.emu.a, 5, mode)
		self.assertEqual(self.emu.z, 0, mode)			
		
#---------------------------------------------------
		mode = "LDA indexed indirect mode."
		source_code = r""" 
	org e000h
	lda #data<
	sta 0000h
	lda #data>
	sta 0001h
	ldy #1d
	lda (0000h),y
	brk
	
data dbt 00d,05d
	end
    	
"""
		self.runasm(source_code)
		self.assertEqual(self.emu.a, 5, mode)
		self.assertEqual(self.emu.z, 0, mode)					
		
#---------------------------------------------------
		mode = "LDA indirect indexed mode."
		source_code = r""" 
	org e000h
	lda #data<
	sta 0000h
	lda #data>
	sta 0001h
	ldy #1d
	lda (0000h),y
	brk
	
data dbt 00d,05d
	end
    	
"""
		self.runasm(source_code)
		self.assertEqual(self.emu.a, 5, mode)
		self.assertEqual(self.emu.z, 0, mode)			
		


	def NtestJMP(self):
		
#---------------------------------------------------
		mode = "JMP absolute mode."
		source_code = r""" 
	org e000h
	jmp target
	brk
target *
	brk
	
	end
    	
"""		
		self.runasm(source_code)
		self.assertEqual(self.emu.pc, self.symbol_table["TARGET"], mode)
		
#---------------------------------------------------
		mode = "JMP indirect mode."
		source_code = r""" 
	org e000h
	lda #target<
	sta 0000h
	lda #target>
	sta 0001h
	jmp (0000h)
	brk
target *
	brk
	
	end
    	
"""		
		self.runasm(source_code)
		self.assertEqual(self.emu.pc, self.symbol_table["TARGET"], mode)
		
		
		
	def NtestStack(self):
		
#---------------------------------------------------
		mode = "PHA."
		source_code = r""" 
	org e000h
	lda #02h
	pha
	brk
	
	end
    	
"""		
		self.runasm(source_code)
		self.assertEqual(self.emu.memory[0][0x1ff], 2, mode)		
		
		

#---------------------------------------------------
		mode = "PLA."
		source_code = r""" 
	org e000h
	lda #02h
	pha
	pla
	brk
	
	end
    	
"""		
		self.runasm(source_code)
		self.assertEqual(self.emu.a, 2, mode)
		
		

#---------------------------------------------------
		mode = "PLP."
		source_code = r""" 
	org e000h
	lda #10000011b
	pha
	plp
	brk
	
	end
    	
"""		
		self.runasm(source_code)
		self.assertEqual(self.emu.n, 1, mode)		
		self.assertEqual(self.emu.v, 0, mode)
		self.assertEqual(self.emu.b, 0, mode)
		self.assertEqual(self.emu.d, 0, mode)
		self.assertEqual(self.emu.i, 0, mode)
		self.assertEqual(self.emu.z, 1, mode)
		self.assertEqual(self.emu.c, 1, mode)
		
#---------------------------------------------------
		mode = "JSR/RTS"
		source_code = r""" 
	org e000h
	lda #00h
	jsr SetA
	brk
SetA *
	lda #02h
	rts
	
	end
    	
"""		
		self.runasm(source_code)
		self.assertEqual(self.emu.a, 2, mode)
		
		
		
	def testUMON65(self):
		
#---------------------------------------------------
		mode = "UMON65."
		file = open("umon65muvium.uasm",'r')
		source_code = file.read();
		self.runasm(source_code)
		#self.assertEqual(self.emu.pc, self.symbol_table["TARGET"], mode)
		
		
		
		
		
		
	def runasm(self, source_code):
		
		output = self.asm.assemble(source_code)
		
		self.lst = output[0]
		self.s19 = output[1]
		self.symbol_table = output[2]
		
		lines = self.s19.splitlines(1)
		
		#print "XXXXXX",lines
		
		
		bytes = []
		
		startaddress = ""
		firstaddress = 1
		#convertescape = { r"\u000A":r"\n", r"\u0009":r"\t",r"\u000D":r"\r", r"\u000C":r"\f", r"\u0008":r"\b", r"\u005C":r"\\" }
			
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

						index = index + 2
						count = count + 1
				
				
						bytes.append(  int(byte,16 ) )    

		java_code = []
		java_code.append("{")
		for byte in bytes:
			if byte > 127:
				byte = (byte - 128) + -128
			java_code.append(str(byte))
			java_code.append(",")
		del java_code[-1]
		java_code.append("}")
		#print ''.join(java_code)

		
		
		#Uncomment to print code that can be pasted into the emulator.
		#x = 0;
		#for each in bytes:
		#	object_code = "emu.rom[%d] = 0x%02X;" % (x,each)
		#	System.out.println(object_code)
		#	x += 1
		#	
		
		#Uncomment to print desired output.
		System.out.println(self.lst) #Print .lst code.
		#System.out.println(self.s19) #Print .s19 code. 
		#System.out.println(self.symbol_table) #Print symbol table.
		#System.out.println(bytes) #Print raw object code.
		
		#print self.emu.rom
		#self.emu.rom = array(bytes,'b')
		self.emu.rom = bytes;
		#Note: self.emu.run()
		
		

		
		
		
def suite():
	suite = unittest.makeSuite(TestEMU6502)
	return suite
		
if __name__ == '__main__':
	unittest.main()
	
# :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
