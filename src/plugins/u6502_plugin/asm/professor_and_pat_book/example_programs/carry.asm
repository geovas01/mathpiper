	org 0200h
	
number1 dbt 11111111b
number2 dbt 00000001b

	org 0205h
	
	lda number1
	clc
	adc number2
	
	brk
	
	end
	
