import unittest
import org.mathrider.u6502.EMU6502 as EMU6502

class TestEMU6502(unittest.TestCase): #junit.framework.TestCase, 
	def setUp(self):
		"Obtain an instance of the emulator."
		self.emu = EMU6502()
		
	def testLDA(self):
		
		mode = "LDA immediate mode."
		source_code = r""" 
	org e000h
	lda #03d
	brk
	
	end
    	
		"""
		#loads19(source_code)
		#self.emu.run()
		
		#self.assertEqual(emu.a, 3, mode)
		#self.assertEqual(emu.z, 0, mode)
		

		mode = "LDA absolute mode."
		source_code = r""" 
	org e000h
	lda data
	brk
	
data dbt 04d
	end
    	
		"""
		
		
	def loads19(self, source_code):
		self.emu.run()
		
		self.assertEqual(emu.a, 4, mode)
		self.assertEqual(emu.z, 0, mode)
		
		
		
		
		
	def loads19(self, source_code):
		
		if os.path.exists(self.filename+".asm"):
			os.remove(self.filename+".asm")
			
		if os.path.exists(self.filename+".lst"):
			os.remove(self.filename+".lst")
		
		if os.path.exists(self.filename+".sym"):
			os.remove(self.filename+".sym")
		
		test_file = open(self.filename+".asm",'w+' )

		test_file.write(source_code)
		test_file.close()
		
		
		command = self.dirpath+"/uasm65 "+self.filename+".asm"
		exit_status = os.system(command)
		
		#Read symbol table.
		symfile = open(self.dirpath +"/"+ self.filename+".sym",'r' )
		lines = symfile.readlines()
		symfile.close()
		self.symbol_table = {}
		for line in lines:
			line = line.split()
			#print line
			#print line[1],line[2]
			self.symbol_table[line[1].lower()] = int(line[2],16) & 0x1fff
		
		
		
		s19file = open(self.dirpath +"/"+ self.filename+".s19",'r' )
		lines = s19file.readlines()
		s19file.close()
		
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
		print ''.join(java_code)

		
		
		#Uncomment to print code that can be pasted into the emulator.
		"""
		x = 0;
		for each in bytes:
			print "rom[%d] = %d" % (x,each)
			x += 1
		"""
		
		#print self.emu.rom
		self.emu.rom = array(bytes,'b')

		
		
		
def suite():
	suite = unittest.makeSuite(TestEMU6502)
	return suite
		
if __name__ == '__main__':
	unittest.main()
	
# :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
